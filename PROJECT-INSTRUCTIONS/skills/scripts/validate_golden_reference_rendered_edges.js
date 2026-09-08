'use strict';

const fs = require('node:fs');
const os = require('node:os');
const path = require('node:path');

const DEFAULT_ROOT = path.resolve(__dirname, '..', 'golden-samples');

function fail(message) {
  throw new Error(`Golden Samples rendered-edge validation failed: ${message}`);
}

function decodeBasicHtmlEntities(value) {
  const named = {amp: '&', apos: "'", gt: '>', lt: '<', nbsp: ' ', quot: '"'};
  return String(value || '').replace(/&(#x[0-9a-f]+|#\d+|[a-z][a-z0-9]+);/gi, (entity, body) => {
    if (body[0] === '#') {
      const hex = body[1] && body[1].toLowerCase() === 'x';
      const digits = hex ? body.slice(2) : body.slice(1);
      const codePoint = Number.parseInt(digits, hex ? 16 : 10);
      if (!Number.isInteger(codePoint) || codePoint < 0 || codePoint > 0x10ffff) fail(`invalid HTML entity ${entity}`);
      return String.fromCodePoint(codePoint);
    }
    const decoded = named[body.toLowerCase()];
    if (decoded === undefined) fail(`unsupported HTML entity ${entity}`);
    return decoded;
  });
}

function normalizeRenderedText(value) {
  return decodeBasicHtmlEntities(value)
    .replace(/!?\[([^\]]+)\]\([^)]+\)/g, '$1')
    .replace(/!?\[([^\]]+)\]\[[^\]]*\]/g, '$1')
    .replace(/<\/?[A-Za-z][^>]*>/g, '')
    .replace(/(^|[\s([{:;>\-])_{1,3}(?=\S)/g, '$1')
    .replace(/(\S)_{1,3}(?=$|[\s)\]}:;,.!?\-])/g, '$1')
    .replace(/[*`]/g, '')
    .trim();
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

function validateRoot(root) {
  if (!fs.existsSync(root)) fail(`missing Golden Samples root ${root}`);
  const markdownFiles = walk(root).filter((file) => file.toLowerCase().endsWith('.md'));
  if (!markdownFiles.length) fail('no Markdown files found');

  const wrapper = `[\\[\\(\\{<"'“‘]*`;
  const statusAfterSeparator = new RegExp(`\\b(?:acceptance|aceita(?:ç|c)[aã]o|status|state|result|resultado|qa)\\b[^:=\\n]{0,80}[:=]\\s*${wrapper}\\s*PASS\\b`, 'i');
  const proseStatus = new RegExp(`\\b(?:acceptance|aceita(?:ç|c)[aã]o|qa)\\b(?:\\s+\\S+){0,6}\\s+(?:(?:has|have|had)\\s+been|remains?|remained|remain|is|are|was|were|permanece|continuam?|continuou|continuaram|fica|ficou|continua)\\s*${wrapper}\\s*PASS\\b`, 'i');
  const tablePass = new RegExp(`^\\s*${wrapper}\\s*PASS\\b`, 'i');

  for (const file of markdownFiles) {
    const relative = path.relative(root, file).split(path.sep).join('/');
    const source = fs.readFileSync(file, 'utf8');

    // Rendered line breaks can hide a second guarded declaration on one physical source line.
    // Canonical reference docs must use physical newlines instead of HTML <br> tags.
    if (/<\s*br\b[^>]*>/i.test(source)) {
      fail(`${relative} uses HTML <br>; physical newlines are required so guarded declarations cannot be hidden across rendered breaks`);
    }

    const lines = source.split(/\r?\n/);
    for (let index = 0; index < lines.length; index += 1) {
      const normalized = normalizeRenderedText(lines[index]);
      if (statusAfterSeparator.test(normalized) || proseStatus.test(normalized)) {
        fail(`${relative}:${index + 1} claims unsupported PASS acceptance/status through punctuation or prose wrapping`);
      }

      const trimmed = lines[index].trim();
      if (trimmed.startsWith('|') && trimmed.endsWith('|')) {
        for (const rawCell of trimmed.slice(1, -1).split('|')) {
          if (tablePass.test(normalizeRenderedText(rawCell))) {
            fail(`${relative}:${index + 1} claims unsupported wrapped PASS table status`);
          }
        }
      }
    }
  }
}

function expectFailure(name, root, pattern) {
  let message = '';
  try {
    validateRoot(root);
  } catch (error) {
    message = String(error && error.message || error);
  }
  if (!message || !pattern.test(message)) fail(`self-test ${name} did not fail as expected; got ${message || '<success>'}`);
}

function runSelfTest() {
  const tmp = fs.mkdtempSync(path.join(os.tmpdir(), 'golden-rendered-edge-'));
  try {
    const file = path.join(tmp, 'sample.md');
    fs.writeFileSync(file, 'Status: REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.\nFinal visual acceptance remains PENDING.\nDo not mark PASS from preview alone.\n', 'utf8');
    validateRoot(tmp);

    fs.writeFileSync(file, '- Runtime consumer/class: `UNRESOLVED`<br>- Runtime consumer/class: com.example.FabricatedRenderer\n', 'utf8');
    expectFailure('rendered-break-duplicate', tmp, /HTML <br>/i);

    fs.writeFileSync(file, 'Native editor acceptance: [PASS]\n', 'utf8');
    expectFailure('bracketed-pass', tmp, /unsupported PASS/i);

    fs.writeFileSync(file, '| Check | (PASS) | evidence |\n', 'utf8');
    expectFailure('table-wrapped-pass', tmp, /wrapped PASS table/i);
  } finally {
    fs.rmSync(tmp, {recursive: true, force: true});
  }
  console.log('OK: rendered-edge self-tests passed');
}

if (process.argv.includes('--self-test')) runSelfTest();
else {
  validateRoot(DEFAULT_ROOT);
  console.log('OK: Golden Samples rendered-edge gate found no hidden rendered breaks or wrapped PASS status');
}
