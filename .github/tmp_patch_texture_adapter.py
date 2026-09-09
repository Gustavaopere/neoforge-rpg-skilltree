from pathlib import Path

path = Path('PROJECT-INSTRUCTIONS/skills/tools/blockbench/rpg-asset-toolkit/blockbench-plugin/uv_texture_adapter.js')
text = path.read_text()


def replace_once(old, new):
    global text
    count = text.count(old)
    if count != 1:
        raise SystemExit(f'expected exactly one occurrence of {old[:80]!r}; found {count}')
    text = text.replace(old, new, 1)


def replace_between(start_marker, end_marker, replacement):
    global text
    start = text.index(start_marker)
    end = text.index(end_marker, start)
    text = text[:start] + replacement + text[end:]


replace_once(
    "'use strict';\n\nfunction fail",
    "'use strict';\n\nconst {MAX_TEXTURE_PIXELS_PER_BATCH} = require('../core/uv-texture/uv_texture_engine.js');\n\nfunction fail",
)

replace_once(
    "  let transactionOpen = false;\n  let prepared = null;\n  const dirtyTextures = new Set();\n",
    "  let transactionOpen = false;\n  let prepared = null;\n  const dirtyTextures = new Set();\n  let approvalSequence = 0;\n  const approvedTextureImports = new Map();\n  const transactionConsumedApprovals = [];\n",
)

replace_once(
    """  function textures() {
    return Array.isArray(bb.Texture.all) ? bb.Texture.all : array(project.textures);
  }

  function requireCube""",
    """  function textures() {
    return Array.isArray(bb.Texture.all) ? bb.Texture.all : array(project.textures);
  }

  function normalizedTextureName(value) {
    return typeof value === 'string' ? value.trim().toLowerCase() : '';
  }

  function requireTextureDimensions(width, height, context) {
    if (!Number.isSafeInteger(width) || width < 1 || !Number.isSafeInteger(height) || height < 1) {
      fail('INVALID_TEXTURE_DIMENSIONS', `${context} must use positive safe integer dimensions.`);
    }
    return Object.freeze({width, height});
  }

  function texturePixelArea(width, height, context) {
    const area = width * height;
    if (!Number.isSafeInteger(area) || area > MAX_TEXTURE_PIXELS_PER_BATCH) {
      fail('TEXTURE_PIXEL_BUDGET_EXCEEDED', `${context} may write ${area} pixels; maximum is ${MAX_TEXTURE_PIXELS_PER_BATCH}.`);
    }
    return area;
  }

  function requireTextureNameAvailable(name, reservedNames) {
    const normalized = normalizedTextureName(name);
    if (!normalized) fail('INVALID_TEXTURE_NAME', 'Texture name must contain non-whitespace characters.');
    const occupied = reservedNames || new Set(textures().map((texture) => normalizedTextureName(texture?.name)).filter(Boolean));
    if (occupied.has(normalized)) fail('TEXTURE_NAME_COLLISION', `Texture name \"${name}\" already exists in the active project or batch.`);
    return normalized;
  }

  function requireApprovedTextureImport(approvalId) {
    const approval = approvedTextureImports.get(approvalId);
    if (!approval) fail('TEXTURE_IMPORT_APPROVAL_NOT_FOUND', `Approved local texture import \"${approvalId}\" is unavailable or already consumed.`);
    return approval;
  }

  function approveTextureImport(file, dimensions) {
    if (!file || typeof file !== 'object') fail('INVALID_TEXTURE_IMPORT_FILE', 'Approved texture import must originate from a Blockbench file result.');
    const name = typeof file.name === 'string' ? file.name.trim() : '';
    if (!name) fail('INVALID_TEXTURE_IMPORT_FILE', 'Approved texture import requires a file name.');
    const {width, height} = requireTextureDimensions(dimensions?.width, dimensions?.height, 'Approved texture import');
    texturePixelArea(width, height, `Approved texture import \"${name}\"`);
    requireTextureNameAvailable(name);

    const approvedFile = {name};
    for (const field of ['path', 'content', 'browser_file']) {
      if (Object.prototype.hasOwnProperty.call(file, field)) approvedFile[field] = file[field];
    }

    approvalSequence += 1;
    const approvalId = `texture-import-approval-${approvalSequence}`;
    approvedTextureImports.set(approvalId, Object.freeze({
      approvalId,
      name,
      width,
      height,
      file: Object.freeze(approvedFile),
    }));
    return approvalId;
  }

  function requireCube""",
)

replace_between(
    "  function preflight(operations) {",
    "\n  function preflightPack(preview) {",
    """  function preflight(operations) {
    if (!Array.isArray(operations)) fail('INVALID_UV_TEXTURE_MUTATIONS', 'operations must be an array.');
    const touchedCubes = [];
    const touchedTextures = [];
    const createdTextures = [];
    const approvedImportIds = [];
    const reservedTextureNames = new Set(textures().map((texture) => normalizedTextureName(texture?.name)).filter(Boolean));
    let pixelWrites = 0;
    let expectsNewTextures = false;

    function chargePixels(count, context) {
      pixelWrites += count;
      if (!Number.isSafeInteger(pixelWrites) || pixelWrites > MAX_TEXTURE_PIXELS_PER_BATCH) {
        fail('TEXTURE_PIXEL_BUDGET_EXCEEDED', `${context} raises the batch pixel budget to ${pixelWrites}; maximum is ${MAX_TEXTURE_PIXELS_PER_BATCH}.`);
      }
    }

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
          const region = regionFor(operation);
          chargePixels(region.width * region.height, `Mutation \"${operation.type}\"`);
          addUnique(touchedTextures, texture);
          break;
        }
        case 'texture_create': {
          const {width, height} = requireTextureDimensions(operation.width, operation.height, 'texture_create');
          const normalized = requireTextureNameAvailable(operation.name, reservedTextureNames);
          reservedTextureNames.add(normalized);
          chargePixels(texturePixelArea(width, height, `texture_create \"${operation.name}\"`), `texture_create \"${operation.name}\"`);
          expectsNewTextures = true;
          break;
        }
        case 'texture_import_approved': {
          const approval = requireApprovedTextureImport(operation.approvalId);
          if (approvedImportIds.includes(operation.approvalId)) {
            fail('TEXTURE_IMPORT_APPROVAL_REUSED', `Approval \"${operation.approvalId}\" may appear only once in a batch.`);
          }
          const normalized = requireTextureNameAvailable(approval.name, reservedTextureNames);
          reservedTextureNames.add(normalized);
          chargePixels(texturePixelArea(approval.width, approval.height, `Approved texture import \"${approval.name}\"`), `Approved texture import \"${approval.name}\"`);
          approvedImportIds.push(operation.approvalId);
          expectsNewTextures = true;
          break;
        }
        default:
          fail('UNSUPPORTED_UV_TEXTURE_MUTATION', `Mutation \"${operation.type}\" is not supported by the Blockbench adapter.`);
      }
    }

    prepared = Object.freeze({
      cubes: Object.freeze(touchedCubes.slice()),
      textures: Object.freeze(touchedTextures.slice()),
      createdTextures,
      approvedImportIds: Object.freeze(approvedImportIds.slice()),
      expectsNewTextures,
    });
    return true;
  }
""",
)

replace_between(
    "  function undoAspects() {",
    "\n  function beginTransaction() {",
    """  function undoAspects() {
    if (!prepared) fail('PREFLIGHT_REQUIRED', 'UV/texture batch must preflight before opening an Undo transaction.');
    const aspects = {};
    if (prepared.cubes.length) aspects.elements = prepared.cubes.slice();

    const transactionTextures = prepared.textures.slice();
    for (const texture of prepared.createdTextures || []) addUnique(transactionTextures, texture);
    if (transactionTextures.length || prepared.expectsNewTextures) aspects.textures = transactionTextures;
    if (prepared.textures.length) aspects.bitmap = true;
    return aspects;
  }
""",
)

replace_between(
    "  function beginTransaction() {",
    "\n  function applyFill(texture, operation) {",
    """  function beginTransaction() {
    if (transactionOpen) fail('TRANSACTION_ALREADY_OPEN', 'A Blockbench Undo transaction is already open.');
    bb.Undo.initEdit(undoAspects());
    dirtyTextures.clear();
    transactionConsumedApprovals.length = 0;
    transactionOpen = true;
  }
""",
)

replace_between(
    "  function applyOperation(operation) {",
    "\n  function applyPack(preview) {",
    """  function applyOperation(operation) {
    if (!transactionOpen) fail('NO_ACTIVE_TRANSACTION', 'No Blockbench Undo transaction is open.');
    switch (operation.type) {
      case 'set_face_uv': {
        const cube = requireCube(operation.cubeId);
        requireFace(cube, operation.face).extend({uv: operation.uv.slice()});
        return cube.uuid;
      }
      case 'set_face_texture': {
        const cube = requireCube(operation.cubeId);
        const texture = requireTexture(operation.textureId);
        requireFace(cube, operation.face).extend({texture: texture.uuid});
        return cube.uuid;
      }
      case 'set_box_uv': {
        const cube = requireCube(operation.cubeId);
        requireCubeUvApi(cube);
        cube.setUVMode(operation.enabled);
        cube.extend({uv_offset: operation.offset.slice()});
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
      case 'texture_create': {
        if (!prepared?.expectsNewTextures) fail('PREFLIGHT_REQUIRED', 'texture_create requires the exact preflighted batch.');
        const texture = new bb.Texture({name: operation.name, internal: true});
        if (!texture.canvas || typeof texture.canvas.toDataURL !== 'function'
          || typeof texture.fromDataURL !== 'function' || typeof texture.add !== 'function') {
          fail('BLOCKBENCH_API_UNAVAILABLE', 'Texture creation requires canvas.toDataURL(), Texture.fromDataURL(), and Texture.add().');
        }
        texture.width = operation.width;
        texture.height = operation.height;
        texture.uv_width = operation.width;
        texture.uv_height = operation.height;
        texture.canvas.width = operation.width;
        texture.canvas.height = operation.height;
        if (texture.ctx && typeof texture.ctx.clearRect === 'function') {
          texture.ctx.clearRect(0, 0, operation.width, operation.height);
        }
        const dataUrl = texture.canvas.toDataURL();
        texture.fromDataURL(dataUrl);
        texture.add(false, true);
        prepared.createdTextures.push(texture);
        return texture.uuid || texture.id;
      }
      case 'texture_import_approved': {
        if (!prepared?.approvedImportIds?.includes(operation.approvalId)) {
          fail('PREFLIGHT_REQUIRED', `Texture import approval \"${operation.approvalId}\" was not part of the preflighted batch.`);
        }
        const approval = requireApprovedTextureImport(operation.approvalId);
        const texture = new bb.Texture({name: approval.name});
        if (typeof texture.fromFile !== 'function' || typeof texture.add !== 'function') {
          fail('BLOCKBENCH_API_UNAVAILABLE', 'Approved texture import requires Texture.fromFile() and Texture.add().');
        }
        texture.fromFile(approval.file);
        texture.add(false, true);
        prepared.createdTextures.push(texture);
        approvedTextureImports.delete(operation.approvalId);
        transactionConsumedApprovals.push([operation.approvalId, approval]);
        return texture.uuid || texture.id;
      }
      default:
        fail('UNSUPPORTED_UV_TEXTURE_MUTATION', `Mutation \"${operation.type}\" is not supported by the Blockbench adapter.`);
    }
  }
""",
)

replace_between(
    "  function finishTransaction(label) {",
    "\n  function cancelTransaction(revert) {",
    """  function finishTransaction(label) {
    if (!transactionOpen) fail('NO_ACTIVE_TRANSACTION', 'No Blockbench Undo transaction is open.');
    for (const texture of dirtyTextures) texture.updateChangesAfterEdit();
    bb.Undo.finishEdit(label, undoAspects());
    transactionOpen = false;
    prepared = null;
    dirtyTextures.clear();
    transactionConsumedApprovals.length = 0;
  }
""",
)

replace_between(
    "  function cancelTransaction(revert) {",
    "\n  return Object.freeze({",
    """  function cancelTransaction(revert) {
    function restoreConsumedApprovals() {
      for (const [approvalId, approval] of transactionConsumedApprovals) {
        if (!approvedTextureImports.has(approvalId)) approvedTextureImports.set(approvalId, approval);
      }
      transactionConsumedApprovals.length = 0;
    }

    if (!transactionOpen) {
      restoreConsumedApprovals();
      prepared = null;
      dirtyTextures.clear();
      return;
    }
    try {
      bb.Undo.cancelEdit(revert === true);
    } finally {
      restoreConsumedApprovals();
      transactionOpen = false;
      prepared = null;
      dirtyTextures.clear();
    }
  }
""",
)

replace_once(
    "  return Object.freeze({\n    getRevision,\n",
    "  return Object.freeze({\n    getRevision,\n    approveTextureImport,\n",
)

path.write_text(text)
