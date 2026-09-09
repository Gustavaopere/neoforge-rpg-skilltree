'use strict';

const fs = require('node:fs');
const path = require('node:path');

const ROOT = __dirname;
const SOURCE_MODULES = Object.freeze([
  'core/project-model/project_model.js',
  'core/validator/validator.js',
  'core/contract-profile/contract_profile.js',
  'core/report/report.js',
  'core/extension-registry/extension_registry.js',
  'core/provider-profile/physical_provider_snapshot.js',
  'core/provider-profile/provider_profiles.js',
  'core/index.js',
  'blockbench-plugin/plugin_adapter.js',
]);

function indent(source, spaces) {
  const prefix = ' '.repeat(spaces);
  return source.replace(/\r\n/g, '\n').replace(/\s+$/, '').split('\n').map((line) => `${prefix}${line}`).join('\n');
}

function buildBundle() {
  const moduleEntries = SOURCE_MODULES.map((id) => {
    const source = fs.readFileSync(path.join(ROOT, id), 'utf8');
    return `    ${JSON.stringify(id)}: function(module, exports, require) {\n${indent(source, 6)}\n    }`;
  }).join(',\n');

  return `(function (root, factory) {\n  'use strict';\n  const api = factory();\n  if (typeof module === 'object' && module.exports) module.exports = api;\n  if (root && root.Plugin && root.Action && root.MenuBar && root.Blockbench) api.registerBlockbenchPlugin(root);\n})(typeof globalThis !== 'undefined' ? globalThis : this, function () {\n  'use strict';\n\n  const modules = {\n${moduleEntries}\n  };\n  const cache = Object.create(null);\n\n  function normalizeModuleId(value) {\n    const output = [];\n    for (const segment of value.split('/')) {\n      if (!segment || segment === '.') continue;\n      if (segment === '..') {\n        if (!output.length) throw new Error('RPG Asset Toolkit module path escaped bundle root.');\n        output.pop();\n      } else output.push(segment);\n    }\n    return output.join('/');\n  }\n\n  function resolveModuleId(fromId, request) {\n    if (typeof request !== 'string' || !request.startsWith('.')) throw new Error('RPG Asset Toolkit bundle forbids external module loading.');\n    const base = fromId.split('/');\n    base.pop();\n    const resolved = normalizeModuleId(base.concat(request.split('/')).join('/'));\n    return resolved.endsWith('.js') ? resolved : resolved + '.js';\n  }\n\n  function loadModule(id) {\n    if (cache[id]) return cache[id].exports;\n    const factory = modules[id];\n    if (!factory) throw new Error('RPG Asset Toolkit bundle module not found: ' + id);\n    const module = {exports: {}};\n    cache[id] = module;\n    factory(module, module.exports, (request) => loadModule(resolveModuleId(id, request)));\n    return module.exports;\n  }\n\n  const core = loadModule('core/index.js');\n  const blockbenchPlugin = loadModule('blockbench-plugin/plugin_adapter.js');\n  return Object.assign({}, core, blockbenchPlugin);\n});\n`;
}

function checkBundle() {
  const target = path.join(ROOT, 'rpg_asset_toolkit.js');
  const current = fs.existsSync(target) ? fs.readFileSync(target, 'utf8') : '';
  const expected = buildBundle();
  if (current !== expected) {
    process.stderr.write('rpg_asset_toolkit.js is stale; run node build_toolkit_bundle.js --write\n');
    return false;
  }
  return true;
}

if (require.main === module) {
  const mode = process.argv[2] || '--check';
  if (mode === '--write') {
    fs.writeFileSync(path.join(ROOT, 'rpg_asset_toolkit.js'), buildBundle(), 'utf8');
  } else if (mode === '--check') {
    if (!checkBundle()) process.exitCode = 1;
  } else {
    process.stderr.write(`unknown mode: ${mode}\n`);
    process.exitCode = 2;
  }
}

module.exports = {SOURCE_MODULES, buildBundle, checkBundle};
