'use strict';

const test = require('node:test');
const assert = require('node:assert/strict');
const toolkit = require('./core/index.js');

function targetState() {
  return {
    textureId: 'tex-main',
    pixelWidth: 16,
    pixelHeight: 16,
    uvWidth: 16,
    uvHeight: 16,
    faces: [
      {cubeId: 'cube-a', face: 'north', uv: [8, 0, 12, 4], boxUv: false},
      {cubeId: 'cube-b', face: 'south', uv: [0, 8, 4, 12], boxUv: false},
    ],
  };
}

function makeAdapter() {
  let revision = 'rev-1';
  const mutations = [];
  return {
    mutations,
    getRevision() {
      return revision;
    },
    inspectPackTarget(textureId) {
      assert.equal(textureId, 'tex-main');
      return targetState();
    },
    preflightPack(preview) {
      mutations.push(['preflight', preview.confirmationToken]);
      return true;
    },
    beginTransaction(label) {
      mutations.push(['begin', label]);
    },
    applyPack(preview) {
      mutations.push(['apply', preview.moves.length]);
      revision = 'rev-2';
      return ['tex-main', 'cube-a', 'cube-b'];
    },
    finishTransaction(label) {
      mutations.push(['finish', label]);
    },
    cancelTransaction(revert) {
      mutations.push(['cancel', revert]);
    },
  };
}

test('UV pack preview is deterministic and apply requires its exact revision-bound confirmation token', () => {
  assert.equal(typeof toolkit.previewUvPack, 'function', 'previewUvPack must be exported by the core');
  assert.equal(typeof toolkit.applyUvPack, 'function', 'applyUvPack must be exported by the core');

  const adapter = makeAdapter();
  const request = {
    expectedRevision: 'rev-1',
    textureId: 'tex-main',
    scope: 'texture',
    padding: 1,
    label: 'Pack UV texture',
  };

  const preview = toolkit.previewUvPack(adapter, request);
  assert.deepEqual(toolkit.previewUvPack(adapter, request), preview, 'same state and request must produce the same preview');
  assert.equal(preview.ok, true);
  assert.equal(preview.dryRun, true);
  assert.equal(preview.beforeRevision, 'rev-1');
  assert.equal(preview.textureId, 'tex-main');
  assert.equal(preview.scope, 'texture');
  assert.deepEqual(preview.atlas, {width: 16, height: 16, padding: 1});
  assert.equal(preview.moves.length, 2);
  assert.ok(preview.moves.some((move) => !move.oldUv.every((value, index) => value === move.newUv[index])), 'preview must describe at least one UV change');
  for (const move of preview.moves) {
    assert.equal(move.sourcePixels.width, move.destinationPixels.width);
    assert.equal(move.sourcePixels.height, move.destinationPixels.height);
  }
  assert.equal(preview.copiedPixels, 32);
  assert.match(preview.confirmationToken, /^uvpack:v1:/);
  assert.deepEqual(adapter.mutations, [], 'preview must not mutate or open Undo');

  assert.throws(
    () => toolkit.applyUvPack(adapter, {...request, confirmationToken: 'uvpack:v1:wrong'}),
    (error) => error && error.code === 'UV_PACK_CONFIRMATION_MISMATCH',
  );
  assert.deepEqual(adapter.mutations, [], 'bad confirmation must fail before preflight or Undo');

  const applied = toolkit.applyUvPack(adapter, {...request, confirmationToken: preview.confirmationToken});
  assert.equal(applied.ok, true);
  assert.equal(applied.dryRun, false);
  assert.equal(applied.beforeRevision, 'rev-1');
  assert.equal(applied.afterRevision, 'rev-2');
  assert.equal(applied.applied, 2);
  assert.deepEqual(applied.changedIds, ['tex-main', 'cube-a', 'cube-b']);
  assert.deepEqual(adapter.mutations.map((entry) => entry[0]), ['preflight', 'begin', 'apply', 'finish']);
});
