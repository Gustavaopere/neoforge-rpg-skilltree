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
if (!result.counts || result.counts.cubeCount < 1) fail('fixture must exercise at least one cube-like element');
if (result.counts.locatorCount < 1) fail('fixture must exercise at least one Locator-like element');
if (!result.bounds || !result.bounds.span.every(Number.isFinite)) fail('fixture must produce finite cube bounds');

for (const bone of ['root', 'cast_hand', 'vfx_anchor']) {
  if (!profile.requiredBones.includes(bone)) fail(`profile missing required bone ${bone}`);
}
for (const animation of [
  'animation.golden_reference_focus_relic.idle',
  'animation.golden_reference_focus_relic.cast',
]) {
  if (!profile.requiredAnimations.includes(animation)) fail(`profile missing required animation ${animation}`);
}

const markdownFiles = walk(ROOT).filter((file) => file.endsWith('.md'));
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

const forbiddenBbmodels = walk(ROOT).filter((file) => file.toLowerCase().endsWith('.bbmodel'));
if (forbiddenBbmodels.length) fail(`native .bbmodel is out of scope until exact schema/exporter verification: ${forbiddenBbmodels.join(', ')}`);

if (!String(fixture.fixtureType || '').includes('NOT a Blockbench .bbmodel')) fail('fixture must explicitly deny native .bbmodel semantics');

console.log(`OK: Golden Samples validated; ${result.counts.cubeCount} cube(s), ${result.counts.locatorCount} locator(s), ${result.errors.length} Toolkit error(s)`);
