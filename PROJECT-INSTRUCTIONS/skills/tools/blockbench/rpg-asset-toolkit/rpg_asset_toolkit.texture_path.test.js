'use strict';

const test = require('node:test');
const assert = require('node:assert/strict');
const toolkit = require('./core/index.js');

test('external texture without a path is rejected', () => {
  const result = toolkit.validateProject({
    name: 'texture_path_contract',
    save_path: '/tmp/texture_path_contract.bbmodel',
    groups: [],
    elements: [],
    textures: [{
      name: 'external.png',
      uuid: 'tex-external',
      width: 16,
      height: 16,
      internal: false,
      path: '',
    }],
    animations: [],
  }, {});

  assert.ok(result.errors.some((item) => item.code === 'MISSING_EXTERNAL_TEXTURE_PATH'));
});
