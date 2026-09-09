'use strict';

function fail(code, message) {
  const error = new Error(`${code}: ${message}`);
  error.code = code;
  throw error;
}

function array(value) {
  return Array.isArray(value) ? value : [];
}

function createBlockbenchUvTextureAdapter(bb) {
  const project = bb?.Blockbench?.Project;
  if (!project || typeof project !== 'object') fail('NO_PROJECT', 'No Blockbench project is open.');
  if (bb.Blockbench.isWeb !== false) fail('DESKTOP_REQUIRED', 'UV/texture mutations require desktop Blockbench.');
  if (typeof bb.Cube !== 'function' || typeof bb.Texture !== 'function') {
    fail('BLOCKBENCH_API_UNAVAILABLE', 'Cube and Texture constructors are required.');
  }
  if (!bb.Undo || ['initEdit', 'finishEdit', 'cancelEdit'].some((name) => typeof bb.Undo[name] !== 'function')) {
    fail('BLOCKBENCH_API_UNAVAILABLE', 'Blockbench Undo API is incomplete.');
  }

  let transactionOpen = false;
  let prepared = null;
  const dirtyTextures = new Set();

  function cubes() {
    return Array.isArray(bb.Cube.all) ? bb.Cube.all : array(project.elements).filter((entry) => entry instanceof bb.Cube);
  }

  function textures() {
    return Array.isArray(bb.Texture.all) ? bb.Texture.all : array(project.textures);
  }

  function requireCube(cubeId) {
    const cube = cubes().find((entry) => entry && entry.uuid === cubeId);
    if (!cube) fail('CUBE_NOT_FOUND', `Cube "${cubeId}" does not exist.`);
    return cube;
  }

  function requireTexture(textureId) {
    const texture = textures().find((entry) => entry && (entry.uuid === textureId || entry.id === textureId));
    if (!texture) fail('TEXTURE_NOT_FOUND', `Texture "${textureId}" does not exist.`);
    return texture;
  }

  function requireFace(cube, faceName) {
    const face = cube && cube.faces && cube.faces[faceName];
    if (!face || typeof face.extend !== 'function') fail('FACE_NOT_FOUND', `Cube "${cube?.uuid || '<unknown>'}" does not expose face "${faceName}".`);
    return face;
  }

  function requireCubeUvApi(cube) {
    if (typeof cube.setUVMode !== 'function' || typeof cube.extend !== 'function') {
      fail('BLOCKBENCH_API_UNAVAILABLE', `Cube "${cube.uuid}" does not expose the Blockbench 5.1.6 UV API.`);
    }
  }

  function requireEditableTexture(texture) {
    if (texture.layers_enabled === true) {
      fail('LAYERED_TEXTURE_UNSUPPORTED', `Texture "${texture.uuid || texture.id}" uses layers; PR4 requires an explicit future layer target.`);
    }
    const ctx = texture.ctx;
    if (!texture.canvas || !ctx || typeof ctx.getImageData !== 'function' || typeof ctx.putImageData !== 'function') {
      fail('BLOCKBENCH_API_UNAVAILABLE', `Texture "${texture.uuid || texture.id}" does not expose a mutable 2D canvas.`);
    }
    if (typeof texture.updateChangesAfterEdit !== 'function') {
      fail('BLOCKBENCH_API_UNAVAILABLE', `Texture "${texture.uuid || texture.id}" cannot publish canvas edits.`);
    }
    if (!Number.isSafeInteger(texture.width) || texture.width < 1 || !Number.isSafeInteger(texture.height) || texture.height < 1) {
      fail('INVALID_TEXTURE_DIMENSIONS', `Texture "${texture.uuid || texture.id}" has invalid dimensions.`);
    }
    return texture;
  }

  function regionFor(operation) {
    if (operation.type === 'texture_replace_palette') return operation.region;
    return operation;
  }

  function requireRegionInBounds(texture, operation) {
    const region = regionFor(operation);
    if (!region || !Number.isSafeInteger(region.x) || !Number.isSafeInteger(region.y)
      || !Number.isSafeInteger(region.width) || !Number.isSafeInteger(region.height)
      || region.x < 0 || region.y < 0 || region.width < 1 || region.height < 1
      || region.x > texture.width - region.width || region.y > texture.height - region.height) {
      fail('PIXEL_REGION_OUT_OF_BOUNDS', `Pixel region is outside texture "${texture.uuid || texture.id}" bounds ${texture.width}x${texture.height}.`);
    }
  }

  function addUnique(target, value) {
    if (!target.includes(value)) target.push(value);
  }

  function getRevision() {
    const {createProjectSnapshot} = require('../live-bridge/project_snapshot.js');
    return createProjectSnapshot(project).projectRevision;
  }

  function preflight(operations) {
    if (!Array.isArray(operations)) fail('INVALID_UV_TEXTURE_MUTATIONS', 'operations must be an array.');
    const touchedCubes = [];
    const touchedTextures = [];

    for (const operation of operations) {
      switch (operation.type) {
        case 'set_face_uv': {
          const cube = requireCube(operation.cubeId);
          requireFace(cube, operation.face);
          addUnique(touchedCubes, cube);
          break;
        }
        case 'set_face_texture': {
          const cube = requireCube(operation.cubeId);
          requireFace(cube, operation.face);
          requireTexture(operation.textureId);
          addUnique(touchedCubes, cube);
          break;
        }
        case 'set_box_uv': {
          const cube = requireCube(operation.cubeId);
          requireCubeUvApi(cube);
          addUnique(touchedCubes, cube);
          break;
        }
        case 'texture_fill_rect':
        case 'texture_replace_palette': {
          const texture = requireEditableTexture(requireTexture(operation.textureId));
          requireRegionInBounds(texture, operation);
          addUnique(touchedTextures, texture);
          break;
        }
        default:
          fail('UNSUPPORTED_UV_TEXTURE_MUTATION', `Mutation "${operation.type}" is not supported by the Blockbench adapter.`);
      }
    }

    prepared = Object.freeze({
      cubes: Object.freeze(touchedCubes.slice()),
      textures: Object.freeze(touchedTextures.slice()),
    });
    return true;
  }

  function undoAspects() {
    if (!prepared) fail('PREFLIGHT_REQUIRED', 'UV/texture batch must preflight before opening an Undo transaction.');
    const aspects = {};
    if (prepared.cubes.length) aspects.elements = prepared.cubes.slice();
    if (prepared.textures.length) {
      aspects.textures = prepared.textures.slice();
      aspects.bitmap = true;
    }
    return aspects;
  }

  function beginTransaction() {
    if (transactionOpen) fail('TRANSACTION_ALREADY_OPEN', 'A Blockbench Undo transaction is already open.');
    bb.Undo.initEdit(undoAspects());
    dirtyTextures.clear();
    transactionOpen = true;
  }

  function applyFill(texture, operation) {
    const image = texture.ctx.getImageData(operation.x, operation.y, operation.width, operation.height);
    for (let index = 0; index < image.data.length; index += 4) {
      image.data[index] = operation.color[0];
      image.data[index + 1] = operation.color[1];
      image.data[index + 2] = operation.color[2];
      image.data[index + 3] = operation.color[3];
    }
    texture.ctx.putImageData(image, operation.x, operation.y);
  }

  function colorKey(color) {
    return (((color[0] * 256 + color[1]) * 256 + color[2]) * 256 + color[3]);
  }

  function applyPalette(texture, operation) {
    const {x, y, width, height} = operation.region;
    const image = texture.ctx.getImageData(x, y, width, height);
    const replacements = new Map(operation.replacements.map((entry) => [colorKey(entry.from), entry.to]));
    for (let index = 0; index < image.data.length; index += 4) {
      const replacement = replacements.get(colorKey(image.data.subarray(index, index + 4)));
      if (!replacement) continue;
      image.data[index] = replacement[0];
      image.data[index + 1] = replacement[1];
      image.data[index + 2] = replacement[2];
      image.data[index + 3] = replacement[3];
    }
    texture.ctx.putImageData(image, x, y);
  }

  function applyOperation(operation) {
    if (!transactionOpen) fail('NO_ACTIVE_TRANSACTION', 'No Blockbench Undo transaction is open.');
    switch (operation.type) {
      case 'set_face_uv': {
        const cube = requireCube(operation.cubeId);
        requireFace(cube, operation.face).extend({uv: operation.uv.slice()});
        bb.Canvas?.updateUV?.(cube);
        return cube.uuid;
      }
      case 'set_face_texture': {
        const cube = requireCube(operation.cubeId);
        const texture = requireTexture(operation.textureId);
        requireFace(cube, operation.face).extend({texture: texture.uuid});
        bb.Canvas?.updateFaces?.(cube);
        return cube.uuid;
      }
      case 'set_box_uv': {
        const cube = requireCube(operation.cubeId);
        requireCubeUvApi(cube);
        cube.setUVMode(operation.enabled);
        cube.extend({uv_offset: operation.offset.slice()});
        bb.Canvas?.updateUV?.(cube);
        return cube.uuid;
      }
      case 'texture_fill_rect': {
        const texture = requireEditableTexture(requireTexture(operation.textureId));
        applyFill(texture, operation);
        dirtyTextures.add(texture);
        return texture.uuid || texture.id;
      }
      case 'texture_replace_palette': {
        const texture = requireEditableTexture(requireTexture(operation.textureId));
        applyPalette(texture, operation);
        dirtyTextures.add(texture);
        return texture.uuid || texture.id;
      }
      default:
        fail('UNSUPPORTED_UV_TEXTURE_MUTATION', `Mutation "${operation.type}" is not supported by the Blockbench adapter.`);
    }
  }

  function finishTransaction(label) {
    if (!transactionOpen) fail('NO_ACTIVE_TRANSACTION', 'No Blockbench Undo transaction is open.');
    for (const texture of dirtyTextures) texture.updateChangesAfterEdit();
    bb.Undo.finishEdit(label, undoAspects());
    transactionOpen = false;
    prepared = null;
    dirtyTextures.clear();
  }

  function cancelTransaction(revert) {
    if (!transactionOpen) {
      prepared = null;
      dirtyTextures.clear();
      return;
    }
    try {
      bb.Undo.cancelEdit(revert === true);
    } finally {
      transactionOpen = false;
      prepared = null;
      dirtyTextures.clear();
    }
  }

  return Object.freeze({
    getRevision,
    preflight,
    beginTransaction,
    applyOperation,
    finishTransaction,
    cancelTransaction,
  });
}

module.exports = {createBlockbenchUvTextureAdapter};
