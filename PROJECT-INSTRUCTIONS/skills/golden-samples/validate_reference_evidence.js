'use strict';

const fs = require('node:fs');
const path = require('node:path');

const ROOT = __dirname;
const UNRESOLVED = 'UNRESOLVED';
const PENDING = 'PENDING';

function fail(message) {
  throw new Error(`Golden Samples evidence validation failed: ${message}`);
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

function normalizeToken(value) {
  return String(value || '').replace(/[*`~]/g, '').trim();
}

function parseTableRow(line) {
  const trimmed = line.trim();
  if (!trimmed.startsWith('|') || !trimmed.endsWith('|')) return null;
  return trimmed.slice(1, -1).split('|').map(normalizeToken);
}

function isSeparatorRow(cells) {
  return cells.every((cell) => /^:?-{3,}:?$/.test(cell));
}

function tableRows(relative) {
  const file = path.join(ROOT, relative);
  if (!fs.existsSync(file)) fail(`missing guarded table file ${relative}`);
  return fs.readFileSync(file, 'utf8')
    .split(/\r?\n/)
    .map((line, index) => ({line, lineNumber: index + 1, cells: parseTableRow(line)}))
    .filter((entry) => entry.cells && entry.cells.length && !isSeparatorRow(entry.cells));
}

function requireExactCell(relative, row, index, expected, label) {
  const actual = normalizeToken(row.cells[index]);
  if (actual !== expected) {
    fail(`${relative}:${row.lineNumber} ${label} must remain exactly ${expected}; got "${actual || '<empty>'}"`);
  }
}

function requireUnresolvedCell(relative, row, index, label) {
  const actual = normalizeToken(row.cells[index]);
  if (!new RegExp(`\\b${UNRESOLVED}\\b`).test(actual)) {
    fail(`${relative}:${row.lineNumber} ${label} must remain explicitly ${UNRESOLVED}; got "${actual || '<empty>'}"`);
  }
}

function requireRowsByKey(relative, headerKey, contracts) {
  const rows = tableRows(relative);
  const dataRows = rows.filter((row) => row.cells[0] !== headerKey);
  for (const contract of contracts) {
    const row = dataRows.find((candidate) => candidate.cells[0] === contract.key);
    if (!row) fail(`${relative} missing guarded row ${contract.key}`);
    for (const index of contract.exactUnresolved || []) requireExactCell(relative, row, index, UNRESOLVED, `${contract.key} cell ${index + 1}`);
    for (const index of contract.containsUnresolved || []) requireUnresolvedCell(relative, row, index, `${contract.key} cell ${index + 1}`);
    for (const [index, expected] of Object.entries(contract.exact || {})) requireExactCell(relative, row, Number(index), expected, `${contract.key} cell ${Number(index) + 1}`);
  }
}

requireRowsByKey('model-asset/MODEL-CONTRACT.md', 'Bone', [
  {key: 'root', exactUnresolved: [3]},
  {key: 'cast_hand', exactUnresolved: [3]},
  {key: 'vfx_anchor', exactUnresolved: [3]},
]);

requireRowsByKey('spell/VFX-BRIEF.md', 'Candidate', [
  {key: 'Photon 2.2.6.a', exactUnresolved: [2, 3]},
  {key: 'AAA Particles 2.2.3', exactUnresolved: [2, 3]},
  {key: 'AAA Particles World 2.0.0', exactUnresolved: [2, 3]},
]);

requireRowsByKey('spell/AUDIO-CUE-SHEET.md', 'Phase', [
  {key: 'Anticipation', exactUnresolved: [3], containsUnresolved: [2, 4], exact: {5: PENDING}},
  {key: 'Release', exactUnresolved: [3], containsUnresolved: [4], exact: {5: PENDING}},
  {key: 'Travel', exactUnresolved: [3], containsUnresolved: [4], exact: {5: PENDING}},
  {key: 'Impact', exactUnresolved: [3], containsUnresolved: [4], exact: {5: PENDING}},
  {key: 'Decay', exactUnresolved: [3], exact: {5: PENDING}},
]);

for (const [relative, headerKey] of [
  ['spell/VISUAL-QA.md', 'Check'],
  ['spell/VFX-QA.md', 'Area'],
  ['spell/AUDIO-QA.md', 'Check'],
]) {
  const rows = tableRows(relative).filter((row) => row.cells[0] !== headerKey);
  if (!rows.length) fail(`${relative} has no guarded QA rows`);
  for (const row of rows) {
    requireExactCell(relative, row, 1, PENDING, `${row.cells[0]} status`);
    requireUnresolvedCell(relative, row, 2, `${row.cells[0]} evidence`);
  }
}

const markdownFiles = walk(ROOT).filter((file) => file.toLowerCase().endsWith('.md'));
if (!markdownFiles.length) fail('no Markdown files found in Golden Samples corpus');

for (const file of markdownFiles) {
  const relative = path.relative(ROOT, file);
  const lines = fs.readFileSync(file, 'utf8').split(/\r?\n/);

  for (let index = 0; index < lines.length; index += 1) {
    const line = lines[index];
    const lineNumber = index + 1;
    const normalizedLine = normalizeToken(line);

    const cells = parseTableRow(line);
    if (cells) {
      for (const cell of cells) {
        if (/^PASS\b/i.test(cell)) fail(`${relative}:${lineNumber} claims unsupported table status "${cell}"`);
      }
    }

    const colonStatus = normalizedLine.match(/:\s*PASS\b[^\n]*/i);
    if (colonStatus) fail(`${relative}:${lineNumber} claims unsupported acceptance status "${colonStatus[0]}"`);

    const namedStatus = normalizedLine.match(/\b(?:status|state|result|resultado|acceptance|aceita(?:ç|c)[aã]o)\s*=\s*PASS\b[^\n]*/i);
    if (namedStatus) fail(`${relative}:${lineNumber} claims unsupported named status "${namedStatus[0]}"`);

    const proseAcceptance = normalizedLine.match(/\b(?:acceptance|aceita(?:ç|c)[aã]o|qa)\b(?:\s+\S+){0,6}\s+(?:remains?|remain|is|are|permanece|continuam?|fica|continua)\s+PASS\b/i);
    if (proseAcceptance) fail(`${relative}:${lineNumber} claims unsupported prose acceptance "${proseAcceptance[0]}"`);
  }
}

console.log(`OK: Golden Samples evidence gate scanned ${markdownFiles.length} Markdown file(s), guarded table cells independently, and found no unsupported PASS-form acceptance claim`);
