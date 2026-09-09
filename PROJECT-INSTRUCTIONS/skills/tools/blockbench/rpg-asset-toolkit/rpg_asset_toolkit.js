(function (root, factory) {
  'use strict';
  const api = factory();
  if (typeof module === 'object' && module.exports) module.exports = api;
  if (root && root.Plugin && root.Action && root.MenuBar && root.Blockbench) api.registerBlockbenchPlugin(root);
})(typeof globalThis !== 'undefined' ? globalThis : this, function () {
  'use strict';

  const modules = {
    "core/project-model/project_model.js": function(module, exports, require) {
      'use strict';
      
      function finiteVector3(value) {
        return Array.isArray(value) && value.length >= 3 && value.slice(0, 3).every(Number.isFinite);
      }
      
      function isPowerOfTwo(value) {
        return Number.isInteger(value) && value > 0 && (value & (value - 1)) === 0;
      }
      
      function normalizedName(value) {
        return typeof value === 'string' ? value.trim() : '';
      }
      
      function normalizeTextureRef(value) {
        if (typeof value !== 'string') return null;
        const trimmed = value.trim();
        if (!trimmed) return null;
        return trimmed.startsWith('#') ? trimmed.slice(1) : trimmed;
      }
      
      function parentObject(value) {
        return value && typeof value === 'object' ? value : null;
      }
      
      function hasOwn(value, key) {
        return !!value && typeof value === 'object' && Object.prototype.hasOwnProperty.call(value, key);
      }
      
      function isCubeLike(element) {
        return !!element && typeof element === 'object' && (
          hasOwn(element, 'to') || hasOwn(element, 'faces') || hasOwn(element, 'box_uv')
          || hasOwn(element, 'inflate') || hasOwn(element, 'autouv')
        );
      }
      
      function isLocatorLike(element) {
        return !!element && typeof element === 'object'
          && hasOwn(element, 'from')
          && !isCubeLike(element)
          && !hasOwn(element, 'vertices');
      }
      
      function computeBounds(elements) {
        const valid = (elements || []).filter((element) => isCubeLike(element) && finiteVector3(element?.from) && finiteVector3(element?.to));
        if (!valid.length) return null;
        const min = [Infinity, Infinity, Infinity];
        const max = [-Infinity, -Infinity, -Infinity];
        for (const element of valid) {
          for (let axis = 0; axis < 3; axis += 1) {
            min[axis] = Math.min(min[axis], element.from[axis], element.to[axis]);
            max[axis] = Math.max(max[axis], element.from[axis], element.to[axis]);
          }
        }
        return {min, max, span: max.map((value, axis) => value - min[axis])};
      }
      
      module.exports = {
        finiteVector3,
        isPowerOfTwo,
        normalizedName,
        normalizeTextureRef,
        parentObject,
        hasOwn,
        isCubeLike,
        isLocatorLike,
        computeBounds,
      };
    },
    "core/validator/validator.js": function(module, exports, require) {
      'use strict';
      
      const {
        finiteVector3,
        isPowerOfTwo,
        normalizedName,
        normalizeTextureRef,
        parentObject,
        isCubeLike,
        isLocatorLike,
        computeBounds,
      } = require('../project-model/project_model.js');
      
      function issue(severity, code, message, context) {
        return {severity, code, message, context: context || null};
      }
      
      function summarize(issues, bounds, counts = {}) {
        const errors = issues.filter((item) => item.severity === 'error');
        const warnings = issues.filter((item) => item.severity !== 'error');
        return {issues, errors, warnings, bounds, counts};
      }
      
      function validateProject(project, profile = {}) {
        const issues = [];
        if (!project || typeof project !== 'object') {
          issues.push(issue('error', 'NO_PROJECT', 'No Blockbench model project is open.'));
          return summarize(issues, null);
        }
      
        const groups = Array.isArray(project.groups) ? project.groups : [];
        const elements = Array.isArray(project.elements) ? project.elements : [];
        const textures = Array.isArray(project.textures) ? project.textures : [];
        const animations = Array.isArray(project.animations) ? project.animations : [];
        const cubeElements = elements.filter(isCubeLike);
        const locatorElements = elements.filter(isLocatorLike);
      
        if (!normalizedName(project.save_path)) {
          issues.push(issue('warning', 'UNSAVED_SOURCE', 'Project has no save_path. Preserve a project-owned .bbmodel source before final approval.'));
        } else if (!project.save_path.toLowerCase().endsWith('.bbmodel')) {
          issues.push(issue('warning', 'SOURCE_NOT_BBMODEL', `Project source path does not end in .bbmodel: ${project.save_path}`));
        }
        if (cubeElements.length > 0 && groups.length === 0) {
          issues.push(issue('warning', 'NO_GROUPS', 'Model has renderable cube elements but no groups/bones. Animated GeckoLib assets require intentional hierarchy.'));
        }
      
        const groupsByUuid = new Map();
        const groupsByName = new Map();
        for (const group of groups) {
          const name = normalizedName(group?.name);
          const uuid = normalizedName(group?.uuid);
          if (uuid) groupsByUuid.set(uuid, group);
          if (!name) {
            issues.push(issue('error', 'EMPTY_BONE_NAME', 'A group/bone has no name.'));
          } else {
            const canonical = name.toLowerCase();
            if (groupsByName.has(canonical)) issues.push(issue('error', 'DUPLICATE_BONE_NAME', `Duplicate group/bone name: "${name}".`));
            else groupsByName.set(canonical, group);
            if (!/^[a-z0-9_]+$/.test(name)) issues.push(issue('warning', 'BONE_NAME_STYLE', `Bone "${name}" is not lower_snake_case.`));
          }
          if (!finiteVector3(group?.origin)) issues.push(issue('error', 'INVALID_BONE_PIVOT', `Bone "${name || '<unnamed>'}" has a malformed/non-finite origin.`));
        }
      
        for (const group of groups) {
          const seen = new Set();
          let cursor = group;
          while (cursor && typeof cursor === 'object') {
            const key = normalizedName(cursor.uuid) || cursor;
            if (seen.has(key)) {
              issues.push(issue('error', 'BONE_PARENT_CYCLE', `Bone "${normalizedName(group.name) || '<unnamed>'}" participates in a parent cycle.`));
              break;
            }
            seen.add(key);
            cursor = parentObject(cursor.parent);
          }
        }
      
        const textureRefs = new Set();
        const textureNames = new Map();
        for (const texture of textures) {
          const name = normalizedName(texture?.name) || '<unnamed>';
          const uuid = normalizedName(texture?.uuid);
          if (uuid) textureRefs.add(uuid);
          textureRefs.add(name);
          const canonical = name.toLowerCase();
          if (textureNames.has(canonical)) issues.push(issue('warning', 'DUPLICATE_TEXTURE_NAME', `Duplicate texture name: "${name}".`));
          else textureNames.set(canonical, texture);
          const width = texture?.width;
          const height = texture?.height;
          if (!Number.isFinite(width) || !Number.isFinite(height) || width <= 0 || height <= 0) {
            issues.push(issue('error', 'INVALID_TEXTURE_SIZE', `Texture "${name}" has invalid dimensions.`));
          } else if (!isPowerOfTwo(width) || !isPowerOfTwo(height)) {
            issues.push(issue('warning', 'NON_POWER_OF_TWO_TEXTURE', `Texture "${name}" is ${width}x${height}; review provider/project requirements.`));
          }
        }
      
        const elementNames = new Map();
        for (const element of elements) {
          const name = normalizedName(element?.name) || '<unnamed>';
          if (name !== '<unnamed>') {
            const canonical = name.toLowerCase();
            if (elementNames.has(canonical)) issues.push(issue('warning', 'DUPLICATE_ELEMENT_NAME', `Duplicate element name: "${name}".`));
            else elementNames.set(canonical, element);
          }
      
          if (isLocatorLike(element)) {
            if (!finiteVector3(element.from)) issues.push(issue('error', 'INVALID_LOCATOR_POSITION', `Locator "${name}" has a malformed/non-finite from position.`));
            if (!parentObject(element.parent)) issues.push(issue('warning', 'UNGROUPED_LOCATOR', `Locator "${name}" is not attached to an object-backed group/bone.`));
            continue;
          }
      
          if (!isCubeLike(element)) continue;
      
          if (!finiteVector3(element?.from) || !finiteVector3(element?.to)) {
            issues.push(issue('error', 'INVALID_ELEMENT_BOUNDS', `Cube element "${name}" has malformed/non-finite from/to bounds.`));
            continue;
          }
          if (element.origin !== undefined && !finiteVector3(element.origin)) issues.push(issue('error', 'INVALID_ELEMENT_PIVOT', `Cube element "${name}" has malformed/non-finite origin.`));
          const size = [0, 1, 2].map((axis) => element.to[axis] - element.from[axis]);
          if (size.some((value) => value < 0)) issues.push(issue('error', 'NEGATIVE_ELEMENT_SIZE', `Cube element "${name}" has negative size on at least one axis.`));
          else if (size.some((value) => value === 0)) issues.push(issue('warning', 'ZERO_ELEMENT_SIZE', `Cube element "${name}" has zero thickness on at least one axis.`));
          if (!parentObject(element.parent)) issues.push(issue('warning', 'UNGROUPED_ELEMENT', `Cube element "${name}" is not attached to an object-backed group/bone.`));
      
          const faces = element?.faces && typeof element.faces === 'object' ? Object.values(element.faces) : [];
          for (const face of faces) {
            if (!face || face.enabled === false) continue;
            const ref = normalizeTextureRef(face.texture);
            if (!ref || !textureRefs.has(ref)) issues.push(issue('error', 'MISSING_FACE_TEXTURE', `Cube element "${name}" has an enabled face with an unresolved texture reference.`));
            if (face.uv !== undefined && (!Array.isArray(face.uv) || face.uv.length < 4 || !face.uv.slice(0, 4).every(Number.isFinite))) issues.push(issue('error', 'INVALID_FACE_UV', `Cube element "${name}" has an enabled face with malformed UV coordinates.`));
          }
        }
        if (cubeElements.length > 0 && textures.length === 0) issues.push(issue('error', 'NO_TEXTURES', 'Model has renderable cube elements but no project texture.'));
      
        const animationNames = new Map();
        for (const animation of animations) {
          const name = normalizedName(animation?.name);
          if (!name) issues.push(issue('error', 'EMPTY_ANIMATION_NAME', 'An animation has no name.'));
          else {
            const canonical = name.toLowerCase();
            if (animationNames.has(canonical)) issues.push(issue('error', 'DUPLICATE_ANIMATION_NAME', `Duplicate animation name: "${name}".`));
            else animationNames.set(canonical, animation);
          }
          if (!Number.isFinite(animation?.length) || animation.length <= 0) issues.push(issue('warning', 'INVALID_ANIMATION_LENGTH', `Animation "${name || '<unnamed>'}" has zero, negative, or non-finite length.`));
          if (!['once', 'hold', 'loop'].includes(animation?.loop)) issues.push(issue('warning', 'UNKNOWN_LOOP_MODE', `Animation "${name || '<unnamed>'}" has unexpected loop mode "${animation?.loop}".`));
          const animators = animation?.animators && typeof animation.animators === 'object' ? animation.animators : {};
          for (const target of Object.keys(animators)) {
            if (!groupsByUuid.has(target)) issues.push(issue('warning', 'UNKNOWN_ANIMATOR_TARGET', `Animation "${name || '<unnamed>'}" targets unknown bone UUID "${target}".`));
          }
        }
      
        for (const required of Array.isArray(profile.requiredBones) ? profile.requiredBones : []) {
          const canonical = normalizedName(required).toLowerCase();
          if (canonical && !groupsByName.has(canonical)) issues.push(issue('error', 'MISSING_REQUIRED_BONE', `Required contract bone "${required}" is missing.`));
        }
        for (const required of Array.isArray(profile.requiredAnimations) ? profile.requiredAnimations : []) {
          const canonical = normalizedName(required).toLowerCase();
          if (canonical && !animationNames.has(canonical)) issues.push(issue('error', 'MISSING_REQUIRED_ANIMATION', `Required contract animation "${required}" is missing.`));
        }
      
        const bounds = computeBounds(cubeElements);
        if (bounds && Array.isArray(profile.maxSpan) && profile.maxSpan.length >= 3) {
          for (let axis = 0; axis < 3; axis += 1) {
            const limit = profile.maxSpan[axis];
            if (Number.isFinite(limit) && limit >= 0 && bounds.span[axis] > limit) {
              issues.push(issue('error', 'MODEL_SPAN_EXCEEDED', `Model span ${bounds.span.join(' x ')} exceeds contract maxSpan ${profile.maxSpan.slice(0, 3).join(' x ')}.`));
              break;
            }
          }
        }
      
        return summarize(issues, bounds, {
          groupCount: groups.length,
          elementCount: elements.length,
          cubeCount: cubeElements.length,
          locatorCount: locatorElements.length,
          textureCount: textures.length,
          animationCount: animations.length,
        });
      }
      
      module.exports = {validateProject, issue, summarize};
    },
    "core/contract-profile/contract_profile.js": function(module, exports, require) {
      'use strict';
      
      function parseProfileJson(text) {
        const value = typeof text === 'string' ? text.trim() : '';
        if (!value) return {};
        const parsed = JSON.parse(value);
        if (!parsed || typeof parsed !== 'object' || Array.isArray(parsed)) throw new Error('Profile must be a JSON object.');
        return parsed;
      }
      
      module.exports = {parseProfileJson};
    },
    "core/report/report.js": function(module, exports, require) {
      'use strict';
      
      function formatReport(result) {
        const lines = [`Structural QA: ${result.errors.length} error(s), ${result.warnings.length} warning(s).`];
        if (result.bounds) lines.push(`Bounds span: ${result.bounds.span.join(' x ')}.`);
        if (result.counts) lines.push(`Bones: ${result.counts.groupCount || 0}; elements: ${result.counts.elementCount || 0} (cubes: ${result.counts.cubeCount || 0}, locators: ${result.counts.locatorCount || 0}); textures: ${result.counts.textureCount || 0}; animations: ${result.counts.animationCount || 0}.`);
        const limited = result.issues.slice(0, 50);
        for (const item of limited) lines.push(`[${item.severity.toUpperCase()}] ${item.code}: ${item.message}`);
        if (result.issues.length > limited.length) lines.push(`... ${result.issues.length - limited.length} additional issue(s) omitted.`);
        lines.push('Texel-density/aesthetic approval still requires the asset contract and in-game visual QA.');
        return lines.join('\n');
      }
      
      module.exports = {formatReport};
    },
    "core/extension-registry/extension_registry.js": function(module, exports, require) {
      'use strict';
      
      const EXTENSION_STATES = Object.freeze([
        'REQUIRED_PROFILE', 'PREFERRED', 'OPTIONAL', 'EXPERIMENTAL', 'DEV_ONLY',
        'HUMAN_ONLY', 'AUDIT_REQUIRED', 'BLOCKED_LEGACY', 'INCOMPATIBLE_EDITOR',
        'LOADER_MISMATCH', 'PROVIDER_ABSENT', 'NOT_RUNTIME_AUTHORITY', 'DISABLED_BY_DEFAULT',
      ]);
      
      const BLOCKING_CLASSIFICATIONS = new Set([
        'HUMAN_ONLY', 'AUDIT_REQUIRED', 'BLOCKED_LEGACY', 'INCOMPATIBLE_EDITOR',
        'LOADER_MISMATCH', 'PROVIDER_ABSENT',
      ]);
      
      const EXTENSION_CATALOG = Object.freeze({
        geckolib: Object.freeze({
          pluginId: 'geckolib',
          title: 'GeckoLib Models & Animations',
          pluginVersion: '4.2.5',
          classification: 'REQUIRED_PROFILE',
          blockbenchCompatibility: Object.freeze({minInclusive: '5.0.0', maxExclusive: '6.0.0'}),
          mcpPolicy: 'ALLOWLIST',
          providerFamily: 'geckolib4',
        }),
        animation_utils: Object.freeze({
          pluginId: 'animation_utils',
          title: 'GeckoLib Animation Utils',
          pluginVersion: '4.1.3',
          classification: 'BLOCKED_LEGACY',
          mcpPolicy: 'NEVER',
          providerFamily: 'geckolib4',
        }),
        azurelib_utils: Object.freeze({
          pluginId: 'azurelib_utils',
          title: 'AzureLib Animator',
          pluginVersion: '2.1.5',
          classification: 'REQUIRED_PROFILE',
          blockbenchCompatibility: Object.freeze({auditedExact: Object.freeze(['5.1.6'])}),
          mcpPolicy: 'ALLOWLIST',
          providerFamily: 'azurelib',
        }),
        cem_template_loader: Object.freeze({
          pluginId: 'cem_template_loader',
          title: 'CEM Template Loader',
          pluginVersion: '9.2.0',
          classification: 'REQUIRED_PROFILE',
          blockbenchCompatibility: Object.freeze({minInclusive: '5.0.0'}),
          mcpPolicy: 'ALLOWLIST',
          providerFamily: 'emf_cem',
        }),
        emf_animation_addon: Object.freeze({
          pluginId: 'emf_animation_addon',
          title: 'EMF Animation Addon',
          pluginVersion: '1.0.5',
          classification: 'PREFERRED',
          blockbenchCompatibility: Object.freeze({minInclusive: '4.9.0'}),
          mcpPolicy: 'ALLOWLIST',
          providerFamily: 'emf_cem',
        }),
        animated_java: Object.freeze({
          pluginId: 'animated_java',
          title: 'Animated Java',
          pluginVersion: '1.10.2',
          classification: 'OPTIONAL',
          blockbenchCompatibility: Object.freeze({minInclusive: '5.1.4'}),
          mcpPolicy: 'ALLOWLIST',
          providerFamily: 'animated_java',
        }),
      });
      
      function numericVersion(value) {
        if (typeof value !== 'string' || !/^\d+(?:\.\d+)*$/.test(value.trim())) return null;
        return value.trim().split('.').map((part) => Number.parseInt(part, 10));
      }
      
      function compareNumericVersions(a, b) {
        const left = numericVersion(a);
        const right = numericVersion(b);
        if (!left || !right) return null;
        const length = Math.max(left.length, right.length);
        for (let index = 0; index < length; index += 1) {
          const delta = (left[index] || 0) - (right[index] || 0);
          if (delta !== 0) return delta < 0 ? -1 : 1;
        }
        return 0;
      }
      
      function blockbenchCompatible(definition, blockbenchVersion) {
        const compatibility = definition && definition.blockbenchCompatibility;
        if (!compatibility) return true;
        if (typeof blockbenchVersion !== 'string' || !blockbenchVersion.trim()) return false;
        if (Array.isArray(compatibility.auditedExact)) return compatibility.auditedExact.includes(blockbenchVersion.trim());
        if (compatibility.minInclusive) {
          const comparison = compareNumericVersions(blockbenchVersion.trim(), compatibility.minInclusive);
          if (comparison === null || comparison < 0) return false;
        }
        if (compatibility.maxExclusive) {
          const comparison = compareNumericVersions(blockbenchVersion.trim(), compatibility.maxExclusive);
          if (comparison === null || comparison >= 0) return false;
        }
        return true;
      }
      
      function getExtensionDefinition(pluginId) {
        return typeof pluginId === 'string' ? EXTENSION_CATALOG[pluginId] || null : null;
      }
      
      function evaluateExtension(definition, session = {}) {
        if (!definition || typeof definition !== 'object' || typeof definition.pluginId !== 'string') {
          return {installed: false, compatible: false, versionMatch: false, mcpAllowed: false, reasons: ['INVALID_EXTENSION_DEFINITION']};
        }
      
        const reasons = [];
        const installedExtensions = Array.isArray(session.installedExtensions) ? session.installedExtensions : [];
        const installed = installedExtensions.find((entry) => entry && entry.pluginId === definition.pluginId) || null;
        const isInstalled = !!installed;
        if (!isInstalled) reasons.push('NOT_INSTALLED');
      
        const versionMatch = !!installed && typeof installed.pluginVersion === 'string'
          && installed.pluginVersion === definition.pluginVersion;
        if (isInstalled && !versionMatch) reasons.push('EXTENSION_VERSION_MISMATCH');
      
        const compatible = blockbenchCompatible(definition, session.blockbenchVersion);
        if (!compatible) reasons.push('BLOCKBENCH_VERSION_INCOMPATIBLE');
      
        const classificationBlocksMcp = BLOCKING_CLASSIFICATIONS.has(definition.classification) || definition.mcpPolicy === 'NEVER';
        if (classificationBlocksMcp) reasons.push('CLASSIFICATION_BLOCKS_MCP');
      
        const authorizedIds = Array.isArray(session.mcpAuthorizedExtensionIds) ? session.mcpAuthorizedExtensionIds : [];
        const authorized = authorizedIds.includes(definition.pluginId);
        if (!authorized) reasons.push('NOT_MCP_AUTHORIZED');
      
        const mcpAllowed = isInstalled && versionMatch && compatible && authorized
          && definition.mcpPolicy === 'ALLOWLIST' && !classificationBlocksMcp;
      
        return {installed: isInstalled, compatible, versionMatch, mcpAllowed, reasons: [...new Set(reasons)]};
      }
      
      module.exports = {
        EXTENSION_STATES,
        EXTENSION_CATALOG,
        getExtensionDefinition,
        evaluateExtension,
        compareNumericVersions,
      };
    },
    "core/provider-profile/physical_provider_snapshot.js": function(module, exports, require) {
      'use strict';
      
      // Physical modlist authority snapshot checked 2026-09-08 (595 top-level mods).
      // Presence is not API proof, runtime-health proof, or MCP authorization.
      const CURRENT_PHYSICAL_PROVIDER_SNAPSHOT = Object.freeze({
        geckolib: Object.freeze({modId: 'geckolib', version: '4.9.2', presence: 'PRESENT', health: 'UNPROVEN'}),
        azurelib: Object.freeze({modId: 'azurelib', version: '3.1.11', presence: 'PRESENT', health: 'UNPROVEN'}),
        entity_model_features: Object.freeze({modId: 'entity_model_features', version: '3.3.5', presence: 'PRESENT', health: 'UNPROVEN'}),
        photon: Object.freeze({modId: 'photon', version: '2.2.6.a', presence: 'PRESENT', health: 'KNOWN_RUNTIME_RISK'}),
        lodestone: Object.freeze({modId: 'lodestone', version: '1.8.2', presence: 'PRESENT', health: 'UNPROVEN'}),
        particle_effects: Object.freeze({modId: 'particle_effects', version: '1.5.0+1.21.1+neoforge', presence: 'PRESENT', health: 'PRESENTATION_ONLY'}),
        aaa_particles: Object.freeze({modId: null, version: null, presence: 'ABSENT', health: 'UNAVAILABLE'}),
        aaa_particles_world: Object.freeze({modId: null, version: null, presence: 'ABSENT', health: 'UNAVAILABLE'}),
      });
      
      const SNAPSHOT_METADATA = Object.freeze({
        checkedDate: '2026-09-08',
        topLevelModCount: 595,
        authority: 'physical-modlist',
      });
      
      module.exports = {CURRENT_PHYSICAL_PROVIDER_SNAPSHOT, SNAPSHOT_METADATA};
    },
    "core/provider-profile/provider_profiles.js": function(module, exports, require) {
      'use strict';
      
      const {getExtensionDefinition, evaluateExtension} = require('../extension-registry/extension_registry.js');
      
      function frozenProfile(value) {
        const result = {...value};
        result.capabilities = Object.freeze([...(value.capabilities || [])]);
        result.requiredExtensions = Object.freeze([...(value.requiredExtensions || [])]);
        if (value.requiredProvider) result.requiredProvider = Object.freeze({...value.requiredProvider, exactVersions: Object.freeze([...(value.requiredProvider.exactVersions || [])])});
        return Object.freeze(result);
      }
      
      const PROVIDER_PROFILES = Object.freeze([
        frozenProfile({
          id: 'java_block_item', family: 'java_block_item', authority: 'Minecraft Java block/item model runtime',
          requiredProvider: null, requiredExtensions: [], capabilities: ['model', 'uv', 'texture', 'display_transforms'],
        }),
        ...['entity', 'item', 'block', 'armor'].map((kind) => frozenProfile({
          id: `geckolib4_${kind}`, family: 'geckolib4', authority: 'GeckoLib 4 runtime', assetKind: kind,
          requiredProvider: {modId: 'geckolib', exactVersions: ['4.9.2']}, requiredExtensions: ['geckolib'],
          capabilities: ['model', 'rig', 'animation', 'effect_keyframe_timing', 'provider_export_handoff'],
        })),
        ...['entity', 'item', 'block', 'armor'].map((kind) => frozenProfile({
          id: `azurelib_${kind}`, family: 'azurelib', authority: 'AzureLib runtime', assetKind: kind,
          requiredProvider: {modId: 'azurelib', exactVersions: ['3.1.11']}, requiredExtensions: ['azurelib_utils'],
          capabilities: ['model', 'rig', 'animation', 'custom_easing_authoring', 'provider_export_handoff'],
        })),
        frozenProfile({
          id: 'emf_cem_entity', family: 'emf_cem', authority: 'Entity Model Features resource-pack/CEM runtime', assetKind: 'entity',
          requiredProvider: {modId: 'entity_model_features', exactVersions: ['3.3.5']}, requiredExtensions: ['cem_template_loader'],
          capabilities: ['model', 'resource_pack_cem', 'animation_authoring', 'provider_export_handoff'],
        }),
        frozenProfile({
          id: 'animated_java_display_entities', family: 'animated_java', authority: 'Animated Java display-entity datapack/resource-pack export pipeline', assetKind: 'display_entities',
          requiredProvider: null, requiredExtensions: ['animated_java'],
          capabilities: ['model', 'rig', 'animation', 'locator', 'variant', 'display_entity_export_handoff'],
        }),
      ]);
      
      const PROFILE_BY_ID = new Map(PROVIDER_PROFILES.map((profile) => [profile.id, profile]));
      
      function listProviderProfiles() {
        return [...PROVIDER_PROFILES];
      }
      
      function getProviderProfile(profileId) {
        return typeof profileId === 'string' ? PROFILE_BY_ID.get(profileId) || null : null;
      }
      
      function physicalProviderEntry(physicalProviders, modId) {
        if (!physicalProviders || typeof physicalProviders !== 'object') return null;
        if (Array.isArray(physicalProviders)) return physicalProviders.find((entry) => entry && entry.modId === modId) || null;
        return physicalProviders[modId] || null;
      }
      
      function resolveProviderProfile(profileId, context = {}) {
        const profile = getProviderProfile(profileId);
        if (!profile) return {status: 'UNAVAILABLE', profile: null, reasons: ['UNKNOWN_PROFILE']};
      
        const reasons = [];
        if (profile.requiredProvider) {
          const actual = physicalProviderEntry(context.physicalProviders, profile.requiredProvider.modId);
          if (!actual || actual.presence !== 'PRESENT') {
            reasons.push('PROVIDER_ABSENT');
          } else {
            if (!profile.requiredProvider.exactVersions.includes(actual.version)) reasons.push('PROVIDER_VERSION_UNSUPPORTED');
            if (actual.health === 'KNOWN_RUNTIME_RISK') reasons.push('PROVIDER_RUNTIME_RISK');
          }
        }
      
        for (const extensionId of profile.requiredExtensions) {
          const definition = getExtensionDefinition(extensionId);
          if (!definition) {
            reasons.push('REQUIRED_EXTENSION_UNAVAILABLE');
            continue;
          }
          const result = evaluateExtension(definition, context);
          if (!result.mcpAllowed) reasons.push('REQUIRED_EXTENSION_UNAVAILABLE');
        }
      
        return {status: reasons.length ? 'UNAVAILABLE' : 'AVAILABLE', profile, reasons: [...new Set(reasons)]};
      }
      
      function canConvertProfile(sourceProfileId, targetProfileId) {
        const source = getProviderProfile(sourceProfileId);
        const target = getProviderProfile(targetProfileId);
        if (!source || !target) return false;
        return source.id === target.id;
      }
      
      module.exports = {
        PROVIDER_PROFILES,
        listProviderProfiles,
        getProviderProfile,
        resolveProviderProfile,
        canConvertProfile,
      };
    },
    "core/index.js": function(module, exports, require) {
      'use strict';
      
      const projectModel = require('./project-model/project_model.js');
      const validator = require('./validator/validator.js');
      const contractProfile = require('./contract-profile/contract_profile.js');
      const report = require('./report/report.js');
      const extensions = require('./extension-registry/extension_registry.js');
      const providers = require('./provider-profile/provider_profiles.js');
      const physical = require('./provider-profile/physical_provider_snapshot.js');
      
      module.exports = Object.assign(
        {},
        projectModel,
        validator,
        contractProfile,
        report,
        extensions,
        providers,
        physical,
      );
    },
    "blockbench-plugin/plugin_adapter.js": function(module, exports, require) {
      'use strict';
      
      const core = require('../core/index.js');
      
      function registerBlockbenchPlugin(bb) {
        let auditAction = null;
        let profileAction = null;
      
        function show(result) {
          bb.Blockbench.showMessageBox({
            title: 'RPG Asset Toolkit',
            icon: result.errors.length ? 'error' : 'check_circle',
            message: core.formatReport(result),
            buttons: ['OK'],
          });
        }
      
        bb.Plugin.register('rpg_asset_toolkit', {
          title: 'RPG Asset Toolkit',
          author: 'Gustavaopere',
          description: 'Read-only structural and provider-aware contract QA for project-owned Minecraft assets.',
          icon: 'fact_check',
          version: '0.2.0',
          variant: 'both',
          tags: ['Minecraft: Java Edition'],
          onload() {
            auditAction = new bb.Action('rpg_asset_toolkit_validate', {
              name: 'Validate RPG Asset',
              description: 'Run read-only structural checks on the active Blockbench project.',
              icon: 'fact_check',
              click() { show(core.validateProject(bb.Blockbench.Project, {})); },
            });
            profileAction = new bb.Action('rpg_asset_toolkit_validate_profile', {
              name: 'Validate RPG Asset Against Contract Profile',
              description: 'Run the same checks plus optional required bones/animations/maxSpan from JSON.',
              icon: 'rule',
              click() {
                bb.Blockbench.textPrompt('RPG Asset Contract Profile (JSON)', '{}', (text) => {
                  try { show(core.validateProject(bb.Blockbench.Project, core.parseProfileJson(text))); }
                  catch (error) {
                    bb.Blockbench.showMessageBox({
                      title: 'RPG Asset Toolkit — Invalid Profile',
                      icon: 'error',
                      message: String(error && error.message ? error.message : error),
                      buttons: ['OK'],
                    });
                  }
                });
              },
            });
            bb.MenuBar.menus.tools.addAction(auditAction);
            bb.MenuBar.menus.tools.addAction(profileAction);
          },
          onunload() {
            if (auditAction) auditAction.delete();
            if (profileAction) profileAction.delete();
            auditAction = null;
            profileAction = null;
          },
        });
      }
      
      module.exports = {registerBlockbenchPlugin};
    }
  };
  const cache = Object.create(null);

  function normalizeModuleId(value) {
    const output = [];
    for (const segment of value.split('/')) {
      if (!segment || segment === '.') continue;
      if (segment === '..') {
        if (!output.length) throw new Error('RPG Asset Toolkit module path escaped bundle root.');
        output.pop();
      } else output.push(segment);
    }
    return output.join('/');
  }

  function resolveModuleId(fromId, request) {
    if (typeof request !== 'string' || !request.startsWith('.')) throw new Error('RPG Asset Toolkit bundle forbids external module loading.');
    const base = fromId.split('/');
    base.pop();
    const resolved = normalizeModuleId(base.concat(request.split('/')).join('/'));
    return resolved.endsWith('.js') ? resolved : resolved + '.js';
  }

  function loadModule(id) {
    if (cache[id]) return cache[id].exports;
    const factory = modules[id];
    if (!factory) throw new Error('RPG Asset Toolkit bundle module not found: ' + id);
    const module = {exports: {}};
    cache[id] = module;
    factory(module, module.exports, (request) => loadModule(resolveModuleId(id, request)));
    return module.exports;
  }

  const core = loadModule('core/index.js');
  const blockbenchPlugin = loadModule('blockbench-plugin/plugin_adapter.js');
  return Object.assign({}, core, blockbenchPlugin);
});
