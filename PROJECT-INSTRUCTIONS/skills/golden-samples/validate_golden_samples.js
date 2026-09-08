'use strict';

const fs = require('node:fs');
const path = require('node:path');
const toolkit = require('../tools/blockbench/rpg-asset-toolkit/rpg_asset_toolkit.js');

const ROOT = __dirname;
const MODEL_ID = 'golden_reference_focus_relic';
const SPELL_ID = 'golden_reference_arcane_bolt';
const REFERENCE_MARKER = 'REFERENCE-ONLY';
const UNRESOLVED_MARKER = 'UNRESOLVED';

function fail(message) {
  throw new Error(`Golden Samples validation failed: ${message}`);
}

function readJson(relative) {
  return JSON.parse(fs.readFileSync(path.join(ROOT, relative), 'utf8'));
}

function readText(relative) {
  return fs.readFileSync(path.join(ROOT, relative), 'utf8');
}

function walk(dir) {
  const out = [];
  for (const entry of fs.readdirSync(dir, {withFileTypes: true})) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) out.push(...walk(full));
    else out.push(full);
  }
  return out;
}

const fixture = readJson('model-asset/validator-project-fixture.json');
const profile = readJson('model-asset/TOOLKIT-PROFILE.json');

if (fixture.name !== MODEL_ID) fail(`fixture name must be ${MODEL_ID}`);
if (profile.assetId !== MODEL_ID || profile.referenceOnly !== true) fail('Toolkit profile must identify the reference-only model asset');

const result = toolkit.validateProject(fixture, profile);
if (result.errors.length) fail(toolkit.formatReport(result));
if (!result.counts || !Number.isInteger(result.counts.cubeCount) || result.counts.cubeCount < 1) fail('fixture must exercise at least one cube-like element');
if (!Number.isInteger(result.counts.locatorCount) || result.counts.locatorCount < 1) fail('fixture must exercise at least one Locator-like element');
if (!result.bounds || !result.bounds.span.every(Number.isFinite)) fail('fixture must produce finite cube bounds');

const warningCodes = result.warnings.map((item) => item.code).sort();
if (warningCodes.length !== 1 || warningCodes[0] !== 'UNSAVED_SOURCE') {
  fail(`fixture warnings must be exactly UNSAVED_SOURCE while native .bbmodel is intentionally absent; got ${warningCodes.join(', ') || '<none>'}`);
}

const focusCore = fixture.elements.find((element) => element && element.name === 'focus_core');
if (!focusCore || !Array.isArray(focusCore.from) || !Array.isArray(focusCore.to)) fail('fixture must keep focus_core as cube-like geometry');
if (!focusCore.parent || focusCore.parent.uuid !== 'bone-root') fail('focus_core must remain attached to root');

const spellMuzzle = fixture.elements.find((element) => element && element.name === 'spell_muzzle');
if (!spellMuzzle || !Array.isArray(spellMuzzle.from)) fail('fixture must contain the spell_muzzle Locator-like attachment');
if (Object.prototype.hasOwnProperty.call(spellMuzzle, 'to') || Object.prototype.hasOwnProperty.call(spellMuzzle, 'faces')) fail('spell_muzzle must remain Locator-like, not cube-like');
if (!spellMuzzle.parent || spellMuzzle.parent.uuid !== 'bone-vfx') fail('spell_muzzle must remain attached to vfx_anchor');

for (const bone of ['root', 'cast_hand', 'vfx_anchor']) {
  if (!Array.isArray(profile.requiredBones) || !profile.requiredBones.includes(bone)) fail(`profile missing required bone ${bone}`);
}
for (const animation of [
  'animation.golden_reference_focus_relic.idle',
  'animation.golden_reference_focus_relic.cast',
]) {
  if (!Array.isArray(profile.requiredAnimations) || !profile.requiredAnimations.includes(animation)) fail(`profile missing required animation ${animation}`);
}
if (!Array.isArray(profile.maxSpan) || profile.maxSpan.length < 3 || !profile.maxSpan.slice(0, 3).every(Number.isFinite)) fail('profile must keep a finite asset-specific maxSpan');

const allFiles = walk(ROOT);
const markdownFiles = allFiles.filter((file) => file.endsWith('.md'));
if (markdownFiles.length < 10) fail('expected complete model/spell markdown corpus');
for (const file of markdownFiles) {
  const text = fs.readFileSync(file, 'utf8');
  const label = path.relative(ROOT, file);
  if (!text.includes(REFERENCE_MARKER)) fail(`${label} missing ${REFERENCE_MARKER} safety marker`);
  if (path.basename(file) !== 'README.md' && !text.includes(UNRESOLVED_MARKER)) fail(`${label} missing ${UNRESOLVED_MARKER} fail-closed marker`);
}

for (const relative of [
  'model-asset/ASSET-BRIEF.md',
  'model-asset/MODEL-CONTRACT.md',
  'model-asset/ANIMATION-BRIEF.md',
]) {
  if (!readText(relative).includes(MODEL_ID)) fail(`${relative} missing model reference ID`);
}
for (const relative of [
  'spell/SPELL-BRIEF.md',
  'spell/SPELL-PRESENTATION.md',
  'spell/VFX-BRIEF.md',
  'spell/AUDIO-CUE-SHEET.md',
  'spell/VISUAL-QA.md',
  'spell/VFX-QA.md',
  'spell/AUDIO-QA.md',
]) {
  if (!readText(relative).includes(SPELL_ID)) fail(`${relative} missing spell reference ID`);
}

const spellPresentation = readText('spell/SPELL-PRESENTATION.md');
for (const phase of ['Anticipation', 'Release', 'Travel / Active', 'Impact / Resolve', 'Decay']) {
  if (!spellPresentation.includes(phase)) fail(`spell presentation missing lifecycle phase ${phase}`);
}
if (!spellPresentation.includes('server') || !spellPresentation.includes('authoritative')) fail('spell presentation must preserve server-authority language');

const vfxBrief = readText('spell/VFX-BRIEF.md');
for (const providerToken of ['Photon 2.2.6.a', 'AAA Particles 2.2.3', 'AAA Particles World 2.0.0']) {
  if (!vfxBrief.includes(providerToken)) fail(`VFX brief missing verified candidate token ${providerToken}`);
}

for (const qaRelative of ['spell/VISUAL-QA.md', 'spell/VFX-QA.md', 'spell/AUDIO-QA.md']) {
  const qa = readText(qaRelative);
  if (!qa.includes('PENDING')) fail(`${qaRelative} must demonstrate evidence-gated PENDING state`);
  if (/\|\s*PASS\s*\|/i.test(qa)) fail(`${qaRelative} must not claim PASS without real evidence`);
}

const forbiddenArtifacts = allFiles.filter((file) => /\.(bbmodel|png|ogg)$/i.test(file));
if (forbiddenArtifacts.length) fail(`native/binary art assets are out of scope until exact source evidence exists: ${forbiddenArtifacts.join(', ')}`);

if (!String(fixture.fixtureType || '').includes('NOT a Blockbench .bbmodel')) fail('fixture must explicitly deny native .bbmodel semantics');

console.log(`OK: Golden Samples validated; ${result.counts.cubeCount} cube(s), ${result.counts.locatorCount} locator(s), ${result.errors.length} Toolkit error(s), expected warning ${warningCodes[0]}`);
