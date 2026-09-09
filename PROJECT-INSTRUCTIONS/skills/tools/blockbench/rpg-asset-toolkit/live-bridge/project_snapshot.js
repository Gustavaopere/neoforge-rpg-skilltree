'use strict';

const crypto = require('node:crypto');

function vec(value) { return Array.isArray(value) ? value.slice(0, 3).map((entry) => Number.isFinite(entry) ? entry : null) : null; }
function parentRef(value) { return value && typeof value === 'object' ? (value.uuid || value.name || null) : (typeof value === 'string' ? value : null); }
function sourceFile(savePath) {
  if (typeof savePath !== 'string' || !savePath) return null;
  return savePath.replace(/\\/g, '/').split('/').filter(Boolean).pop() || null;
}
function canonical(value) {
  if (Array.isArray(value)) return value.map(canonical);
  if (value && typeof value === 'object') return Object.fromEntries(Object.keys(value).sort().map((key) => [key, canonical(value[key])]));
  return value;
}
function hashRevision(value) { return `sha256:${crypto.createHash('sha256').update(JSON.stringify(canonical(value))).digest('hex')}`; }

function groupSnapshot(group) {
  return {name: group?.name || null, uuid: group?.uuid || null, origin: vec(group?.origin), parent: parentRef(group?.parent)};
}
function faceSnapshot(face) { return {enabled: face?.enabled !== false, texture: typeof face?.texture === 'string' ? face.texture : null, uv: Array.isArray(face?.uv) ? face.uv.slice(0, 4) : null}; }
function elementSnapshot(element) {
  const faces = element?.faces && typeof element.faces === 'object' ? Object.fromEntries(Object.keys(element.faces).sort().map((key) => [key, faceSnapshot(element.faces[key])])) : {};
  return {name: element?.name || null, uuid: element?.uuid || null, from: vec(element?.from), to: vec(element?.to), origin: vec(element?.origin), parent: parentRef(element?.parent), faces};
}
function textureSnapshot(texture) { return {name: texture?.name || null, uuid: texture?.uuid || null, width: Number.isFinite(texture?.width) ? texture.width : null, height: Number.isFinite(texture?.height) ? texture.height : null}; }
function animationSnapshot(animation) {
  return {name: animation?.name || null, uuid: animation?.uuid || null, length: Number.isFinite(animation?.length) ? animation.length : null, loop: animation?.loop || null, animatorTargets: animation?.animators && typeof animation.animators === 'object' ? Object.keys(animation.animators).sort() : []};
}

function createProjectSnapshot(project) {
  const input = project && typeof project === 'object' ? project : {};
  const body = {
    name: input.name || null,
    projectFormat: input.format && typeof input.format === 'object' ? input.format.id || null : input.format || null,
    sourceFile: sourceFile(input.save_path),
    saved: typeof input.save_path === 'string' && input.save_path.length > 0,
    groups: (Array.isArray(input.groups) ? input.groups : []).map(groupSnapshot),
    elements: (Array.isArray(input.elements) ? input.elements : []).map(elementSnapshot),
    textures: (Array.isArray(input.textures) ? input.textures : []).map(textureSnapshot),
    animations: (Array.isArray(input.animations) ? input.animations : []).map(animationSnapshot),
    selection: (Array.isArray(input.selected_elements) ? input.selected_elements : []).map((entry) => entry?.uuid || entry?.name || null).filter(Boolean),
  };
  return Object.freeze({...body, projectRevision: hashRevision(body)});
}

module.exports = {createProjectSnapshot, hashRevision};
