'use strict';

const fs = require('node:fs');
const path = require('node:path');

const ROOT = __dirname;

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

function normalizeStatusToken(value) {
  return String(value || '').replace(/[*_`~]/g, '').trim();
}

const markdownFiles = walk(ROOT).filter((file) => file.toLowerCase().endsWith('.md'));
if (!markdownFiles.length) fail('no Markdown files found in Golden Samples corpus');

for (const file of markdownFiles) {
  const relative = path.relative(ROOT, file);
  const lines = fs.readFileSync(file, 'utf8').split(/\r?\n/);

  for (let index = 0; index < lines.length; index += 1) {
    const line = lines[index];
    const lineNumber = index + 1;

    if (line.includes('|')) {
      const cells = line.split('|').map(normalizeStatusToken);
      for (const cell of cells) {
        if (/^PASS\b/i.test(cell)) {
          fail(`${relative}:${lineNumber} claims unsupported table status "${cell}"`);
        }
      }
    }

    const colonStatus = line.match(/:\s*([*_`~]*\s*PASS\b[^\n]*)/i);
    if (colonStatus) {
      fail(`${relative}:${lineNumber} claims unsupported acceptance status "${normalizeStatusToken(colonStatus[1])}"`);
    }

    const namedStatus = line.match(/\b(?:status|state|result|resultado|acceptance|aceita(?:ç|c)[aã]o)\s*=\s*([*_`~]*\s*PASS\b[^\n]*)/i);
    if (namedStatus) {
      fail(`${relative}:${lineNumber} claims unsupported named status "${normalizeStatusToken(namedStatus[1])}"`);
    }
  }
}

console.log(`OK: Golden Samples evidence gate scanned ${markdownFiles.length} Markdown file(s) with no unsupported PASS-form acceptance claim`);
