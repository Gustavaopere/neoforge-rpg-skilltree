'use strict';

const test = require('node:test');
const assert = require('node:assert/strict');

const {registerBlockbenchPlugin} = require('./blockbench-plugin/plugin_adapter.js');

function makeProject(name = 'picker_project') {
  return {
    name,
    format: {id: 'free'},
    groups: [],
    elements: [],
    textures: [],
    animations: [],
    selected_elements: [],
    texture_width: 16,
    texture_height: 16,
  };
}

function mockBlockbench({isWeb = false, pickedFiles = null} = {}) {
  const actions = new Map();
  const messages = [];
  const imports = [];
  const undo = [];
  let pluginDefinition = null;
  const project = makeProject();
  const files = pickedFiles || [{
    name: 'approved.png',
    path: '/private/user/approved.png',
    content: '/private/user/approved.png',
  }];

  class Action {
    constructor(id, config) {
      this.id = id;
      Object.assign(this, config);
    }
    delete() {
      actions.delete(this.id);
    }
  }

  class Cube {
    static all = [];
  }

  class Texture {
    static all = project.textures;
    static getAllExtensions() { return ['png']; }

    constructor(data = {}) {
      this.uuid = `tex-${Math.random().toString(16).slice(2)}`;
      this.id = String(Texture.all.length);
      this.name = data.name || '';
      this.width = 0;
      this.height = 0;
      this.internal = data.internal !== false;
      this.layers_enabled = false;
      this.source = '';
      this.saved = false;
      this.canvas = {
        width: 16,
        height: 16,
        toDataURL() { return `data:image/png;base64,blank-${this.width}x${this.height}`; },
      };
      this.ctx = {
        clearRect() {},
        getImageData() { return {data: new Uint8ClampedArray(4), width: 1, height: 1}; },
        putImageData() {},
      };
    }

    fromFile(file) {
      this.name = file.name;
      this.source = file.content || file.path || '';
      queueMicrotask(() => {
        this.width = 8;
        this.height = 8;
        this.canvas.width = 8;
        this.canvas.height = 8;
        if (typeof this.load_callback === 'function') this.load_callback(this);
      });
      return this;
    }

    fromDataURL(dataUrl) {
      this.source = dataUrl;
      this.width = this.canvas.width;
      this.height = this.canvas.height;
      return this;
    }

    add(undoPoint, uvSizeFromResolution) {
      if (!Texture.all.includes(this)) Texture.all.push(this);
      return this;
    }

    updateChangesAfterEdit() {}
  }

  const Blockbench = {
    Project: project,
    isWeb,
    version: '5.1.6',
    import(options, callback) {
      imports.push(options);
      callback(files);
    },
    showMessageBox(options) {
      messages.push(options);
    },
    showQuickMessage(message) {
      messages.push({title: 'quick', message});
    },
    textPrompt(title, initial, callback) {
      if (title === 'RPG UV/Texture Mutation Batch (JSON)') {
        const approvalMessage = messages.find((entry) => /texture-import-approval-\d+/.test(String(entry.message || '')));
        assert.ok(approvalMessage, 'picker must surface an opaque approval id before the batch prompt');
        const approvalId = String(approvalMessage.message).match(/texture-import-approval-\d+/)[0];
        const payload = JSON.parse(initial);
        payload.dryRun = false;
        payload.operations = [{type: 'texture_import_approved', approvalId}];
        callback(JSON.stringify(payload));
      }
    },
  };

  const bb = {
    Blockbench,
    Plugin: {
      register(id, definition) {
        assert.equal(id, 'rpg_asset_toolkit');
        pluginDefinition = definition;
      },
    },
    Action,
    MenuBar: {
      menus: {
        tools: {
          addAction(action) {
            actions.set(action.id, action);
          },
        },
      },
    },
    Cube,
    Texture,
    Undo: {
      initEdit(aspects) { undo.push(['begin', aspects]); },
      finishEdit(label, aspects) { undo.push(['finish', label, aspects]); },
      cancelEdit(revert) { undo.push(['cancel', revert]); },
    },
  };

  return {
    bb,
    project,
    actions,
    messages,
    imports,
    undo,
    get pluginDefinition() { return pluginDefinition; },
  };
}

test('desktop plugin picker creates an opaque project-local approval usable by the next UV/texture batch', async () => {
  const env = mockBlockbench();
  registerBlockbenchPlugin(env.bb);
  assert.ok(env.pluginDefinition);
  env.pluginDefinition.onload();

  const approveAction = env.actions.get('rpg_asset_toolkit_approve_texture_import');
  assert.ok(approveAction, 'desktop plugin must register an explicit local texture import approval action');

  approveAction.click();
  assert.equal(env.imports.length, 1);
  assert.equal(env.imports[0].resource_id, 'texture');
  assert.equal(env.imports[0].readtype, 'image');
  assert.equal(env.imports[0].multiple, false);
  assert.deepEqual(env.imports[0].extensions, ['png']);

  await new Promise((resolve) => setImmediate(resolve));

  const serializedMessages = JSON.stringify(env.messages);
  assert.match(serializedMessages, /texture-import-approval-\d+/);
  assert.equal(serializedMessages.includes('/private/user/approved.png'), false, 'local path must not be surfaced by the approval UI');

  const batchAction = env.actions.get('rpg_asset_toolkit_uv_texture_batch');
  assert.ok(batchAction);
  batchAction.click();

  assert.equal(env.project.textures.length, 1, 'approved picker file must remain usable by the following batch action');
  assert.equal(env.project.textures[0].name, 'approved.png');
  assert.equal(env.undo.filter(([kind]) => kind === 'begin').length, 1);
  assert.equal(env.undo.filter(([kind]) => kind === 'finish').length, 1);
});

test('web plugin does not expose the desktop-local texture import approval picker', () => {
  const env = mockBlockbench({isWeb: true});
  registerBlockbenchPlugin(env.bb);
  env.pluginDefinition.onload();

  assert.equal(env.actions.has('rpg_asset_toolkit_approve_texture_import'), false);
});
