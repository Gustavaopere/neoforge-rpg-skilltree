'use strict';

const fs = require('node:fs');
const os = require('node:os');
const path = require('node:path');

const DEFAULT_ROOT = path.resolve(__dirname, '..', 'golden-samples');
const CANONICAL_STATUS = 'REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.';

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
    // CommonMark backslash escapes are forbidden by validateRoot before normalization. Keep
    // decoding here only so synthetic helper inputs retain rendered-text semantics.
    .replace(/\\([!"#$%&'()*+,\-.\/:;<=>?@\[\]\\^_`{|}~])/g, '$1')
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

function collectRenderedStatusDeclarations(source) {
  const declarations = [];
  const lines = String(source || '').split(/\r?\n/);
  for (let index = 0; index < lines.length; index += 1) {
    const normalized = normalizeRenderedText(lines[index]);
    const pattern = /\bStatus\s*:/gi;
    let match = null;
    while ((match = pattern.exec(normalized)) !== null) {
      declarations.push({
        lineNumber: index + 1,
        value: normalized.slice(match.index + match[0].length).trim(),
      });
    }
  }
  return declarations;
}

function validateRoot(root) {
  if (!fs.existsSync(root)) fail(`missing Golden Samples root ${root}`);
  const markdownFiles = walk(root).filter((file) => file.toLowerCase().endsWith('.md'));
  if (!markdownFiles.length) fail('no Markdown files found');

  const wrapper = `[\\[\\(\\{<"'“‘]*`;
  const statusAfterSeparator = new RegExp(`\\b(?:acceptance|aceita(?:ç|c)[aã]o|status|state|result|resultado|qa)\\b[^:=\\n]{0,80}[:=]\\s*${wrapper}\\s*PASS\\b`, 'i');
  const proseStatus = new RegExp(`\\b(?:acceptance|aceita(?:ç|c)[aã]o|qa)\\b(?:\\s+\\S+){0,6}\\s+(?:(?:has|have|had)\\s+been|remains?|remained|remain|is|are|was|were|permanece|continuam?|continuou|continuaram|fica|ficou|continua)\\s*${wrapper}\\s*PASS\\b`, 'i');
  const tablePass = new RegExp(`^\\s*${wrapper}\\s*PASS\\b`, 'i');
  const rawHtmlOpener = /<(?:!|\?|\/?[A-Za-z])/;
  const blockquoteContainer = /^\s*(?:(?:[-+*]|\d+[.)])\s+)*>\s?/;
  const commonmarkEscape = /\\[!"#$%&'()*+,\-.\/:;<=>?@\[\]\\^_`{|}~]/;
  const encodedBackslash = /&#(?:0*92|x0*5c);/i;

  for (const file of markdownFiles) {
    const relative = path.relative(root, file).split(path.sep).join('/');
    const source = fs.readFileSync(file, 'utf8');

    // HTML character references are decoded during rendered-text normalization. A numeric
    // reference for backslash can therefore become a CommonMark escape only after the
    // source-level escape check has already run, changing link/delimiter parsing and hiding
    // fabricated guarded values. Reject every decimal/hex spelling of U+005C fail-closed.
    if (encodedBackslash.test(source)) {
      fail(`${relative} uses an HTML character reference that decodes to backslash; entity-encoded backslashes are forbidden because they can create CommonMark escapes after source-level guarded-evidence validation`);
    }

    // The reference-only corpus has no legitimate need for CommonMark punctuation escapes.
    // Reject them before any rendered-text normalization so an escaped delimiter cannot be
    // decoded into active Markdown syntax and hide fabricated guarded evidence.
    if (commonmarkEscape.test(source)) {
      fail(`${relative} uses a CommonMark backslash escape; punctuation escapes are forbidden in the reference-only corpus because they can change Markdown parsing before guarded-evidence validation`);
    }

    // Fail closed on every raw CommonMark HTML opener, including unterminated comments,
    // declarations/CDATA, processing instructions, and ordinary opening/closing elements.
    // The reference-only corpus has no need for raw HTML; entity-escaped text remains allowed.
    if (rawHtmlOpener.test(source)) {
      fail(`${relative} uses raw HTML syntax; raw HTML is forbidden in the reference-only corpus because invisible or block-producing constructs can hide guarded evidence`);
    }

    const lines = source.split(/\r?\n/);
    for (let index = 0; index < lines.length; index += 1) {
      // Blockquotes are forbidden even when nested after one or more list-item markers.
      if (blockquoteContainer.test(lines[index])) {
        fail(`${relative}:${index + 1} uses a Markdown blockquote; blockquotes are forbidden because container prefixes can mask rendered acceptance/status text`);
      }

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

    // Markdown soft line breaks inside one paragraph render as whitespace. Scan the rendered
    // paragraph too so `acceptance:\nPASS` cannot evade the line-oriented checks above.
    const paragraphs = source.split(/\r?\n\s*\r?\n/);
    for (let index = 0; index < paragraphs.length; index += 1) {
      const renderedParagraph = normalizeRenderedText(paragraphs[index].replace(/\r?\n/g, ' '));
      if (statusAfterSeparator.test(renderedParagraph) || proseStatus.test(renderedParagraph)) {
        fail(`${relative}:paragraph-${index + 1} claims unsupported PASS acceptance/status across a rendered soft break`);
      }
    }

    // `Status:` is a corpus boundary, not ordinary prose. Count every rendered occurrence,
    // regardless of Markdown container syntax (heading/list/task-list/emphasis), and require
    // exactly one canonical declaration. This also rejects multiple declarations on one line.
    const statusDeclarations = collectRenderedStatusDeclarations(source);
    if (statusDeclarations.length !== 1) {
      fail(`${relative} must contain exactly one rendered Status: declaration; found ${statusDeclarations.length}`);
    }
    const status = statusDeclarations[0];
    if (status.value !== CANONICAL_STATUS) {
      fail(`${relative}:${status.lineNumber} rendered Status value must be exactly "${CANONICAL_STATUS}"; got "${status.value || '<empty>'}"`);
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
    expectFailure('rendered-break-duplicate', tmp, /raw HTML/i);

    fs.writeFileSync(file, 'Native editor acceptance: [PASS]\n', 'utf8');
    expectFailure('bracketed-pass', tmp, /unsupported PASS/i);

    fs.writeFileSync(file, '| Check | (PASS) | evidence |\n', 'utf8');
    expectFailure('table-wrapped-pass', tmp, /wrapped PASS table/i);

    fs.writeFileSync(file, 'Native editor acceptance:\nPASS\n', 'utf8');
    expectFailure('soft-break-pass', tmp, /soft break/i);

    fs.writeFileSync(file, '> Native editor acceptance:\n> PASS\n', 'utf8');
    expectFailure('blockquote-soft-break-pass', tmp, /Markdown blockquote/i);

    fs.writeFileSync(file, '- > Native editor acceptance:\n  > PASS\n', 'utf8');
    expectFailure('list-nested-blockquote-pass', tmp, /Markdown blockquote/i);

    fs.writeFileSync(file, '- Runtime consumer/class: `UNRESOLVED`</div><div>- Runtime consumer/class: com.example.FabricatedRenderer\n', 'utf8');
    expectFailure('html-block-duplicate', tmp, /raw HTML/i);

    fs.writeFileSync(file, 'Native editor acceptance: PA<!-- invisible -->SS\n', 'utf8');
    expectFailure('html-comment-pass', tmp, /raw HTML/i);

    fs.writeFileSync(file, '<!-- unterminated\nStatus: REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.\n', 'utf8');
    expectFailure('unterminated-html-comment', tmp, /raw HTML/i);

    fs.writeFileSync(file, '<!doctype html>\n', 'utf8');
    expectFailure('html-declaration', tmp, /raw HTML/i);

    fs.writeFileSync(file, '<?probe?>\n', 'utf8');
    expectFailure('html-processing-instruction', tmp, /raw HTML/i);

    fs.writeFileSync(file, '<![CDATA[probe]]>\n', 'utf8');
    expectFailure('html-cdata', tmp, /raw HTML/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n## Status: PRODUCTION READY — runtime registration and shipped gameplay.\n`, 'utf8');
    expectFailure('heading-status-duplicate', tmp, /exactly one rendered Status/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS} Status: PRODUCTION READY — duplicate on one rendered line.\n`, 'utf8');
    expectFailure('same-line-status-duplicate', tmp, /exactly one rendered Status/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n## Status\\: PRODUCTION READY — escaped-colon rendered contradiction.\n`, 'utf8');
    expectFailure('escaped-heading-status-duplicate', tmp, /CommonMark backslash escape/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n| root | purpose | pivot | [UNRESOLVED]\\(com.example.FabricatedRenderer) |\n`, 'utf8');
    expectFailure('escaped-link-delimiter-guard', tmp, /CommonMark backslash escape/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n| root | purpose | pivot | [UNRESOLVED]&#92;(com.example.FabricatedRenderer) |\n`, 'utf8');
    expectFailure('entity-decimal-backslash-guard', tmp, /entity-encoded backslashes/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n| root | purpose | pivot | [UNRESOLVED]&#x5C;(com.example.FabricatedRenderer) |\n`, 'utf8');
    expectFailure('entity-hex-backslash-guard', tmp, /entity-encoded backslashes/i);

    fs.writeFileSync(file, `Status: ${CANONICAL_STATUS}\n| root | purpose | pivot | [UNRESOLVED]&#40;com.example.FabricatedRenderer&#41; |\n`, 'utf8');
    expectFailure('entity-parentheses-link-guard', tmp, /Markdown-active HTML entity/i);
  } finally {
    fs.rmSync(tmp, {recursive: true, force: true});
  }
  console.log('OK: rendered-edge self-tests passed');
}

if (process.argv.includes('--self-test')) runSelfTest();
else {
  validateRoot(DEFAULT_ROOT);
  console.log('OK: Golden Samples rendered-edge gate found one canonical rendered Status per Markdown file and no entity-encoded/CommonMark backslash escapes, raw HTML/blockquotes, hidden rendered breaks, soft-break PASS claims, or wrapped PASS status');
}
