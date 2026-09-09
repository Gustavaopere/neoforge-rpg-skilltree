const test = require('node:test');
const assert = require('node:assert/strict');
const vm = require('node:vm');
const bundleBuilder = require('./build_toolkit_bundle.js');

const REQUIRED_LIVE_MODULES = [
  'live-bridge/protocol.js',
  'live-bridge/project_snapshot.js',
  'live-bridge/fingerprint.js',
  'live-bridge/read_only_router.js',
  'live-bridge/blockbench_bridge_client.js',
  'blockbench-plugin/live_bridge_adapter.js',
];

test('standalone bundle includes the approved live bridge modules and only the explicit safe native crypto module', () => {
  for (const moduleId of REQUIRED_LIVE_MODULES) {
    assert.ok(bundleBuilder.SOURCE_MODULES.includes(moduleId), `bundle missing ${moduleId}`);
  }
  assert.deepEqual(bundleBuilder.NATIVE_MODULE_ALLOWLIST, ['node:crypto']);
  assert.equal(bundleBuilder.validateExternalRequires(), true);
});

test('bundle generator rejects native imports outside the explicit allowlist', () => {
  assert.equal(bundleBuilder.isAllowedNativeModule('node:crypto'), true);
  for (const moduleId of ['fs', 'node:fs', 'process', 'child_process', 'https', 'net', 'tls', 'electron']) {
    assert.equal(bundleBuilder.isAllowedNativeModule(moduleId), false, `unexpected native allowlist entry: ${moduleId}`);
  }
});

test('web Blockbench can load the both-variant toolkit without native require or live bridge actions', () => {
  const actionIds = [];
  let metadata = null;
  class FakeAction {
    constructor(id) { this.id = id; actionIds.push(id); }
    delete() {}
  }
  const context = {
    module: {exports: {}},
    Plugin: {
      register(id, definition) {
        assert.equal(id, 'rpg_asset_toolkit');
        metadata = definition;
        definition.onload();
      },
    },
    Action: FakeAction,
    MenuBar: {menus: {tools: {addAction() {}}}},
    Blockbench: {
      isWeb: true,
      version: '5.1.6',
      Project: null,
      showMessageBox() {},
      textPrompt() {},
    },
    Plugins: {installed: []},
    console,
    setTimeout,
    clearTimeout,
    setInterval,
    clearInterval,
    TextEncoder,
    TextDecoder,
  };
  vm.runInNewContext(bundleBuilder.buildBundle(), context, {filename: 'rpg_asset_toolkit.generated.js'});
  assert.ok(metadata);
  assert.equal(metadata.variant, 'both');
  assert.equal(typeof context.module.exports.createBridgeConnection, 'function');
  assert.equal(typeof context.module.exports.buildBridgeUrl, 'function');
  assert.ok(actionIds.includes('rpg_asset_toolkit_validate'));
  assert.ok(actionIds.includes('rpg_asset_toolkit_validate_profile'));
  assert.equal(actionIds.some((id) => id.includes('live_bridge')), false);
});
