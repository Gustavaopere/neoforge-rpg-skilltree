import { McpServer } from '@modelcontextprotocol/server';
import { serveStdio } from '@modelcontextprotocol/server/stdio';
import * as z from 'zod/v4';
import { createRequire } from 'node:module';
import { fileURLToPath } from 'node:url';

const require = createRequire(import.meta.url);
const { TOOL_NAMES, TOOL_DESCRIPTIONS } = require('../tool_manifest.cjs');

const emptySchema = z.object({}).strict();
const animationSchema = z.object({
  animationId: z.string().min(1).max(256).optional(),
  name: z.string().min(1).max(256).optional(),
}).strict().refine((value) => Boolean(value.animationId || value.name), 'animationId or name is required');
const pluginSchema = z.object({pluginId: z.string().min(1).max(128)}).strict();
const profileIdSchema = z.object({profileId: z.string().min(1).max(128)}).strict();
const contractSchema = z.object({
  profile: z.object({
    requiredBones: z.array(z.string().min(1).max(128)).max(256).optional(),
    requiredAnimations: z.array(z.string().min(1).max(256)).max(256).optional(),
    maxSpan: z.tuple([z.number().finite().nonnegative(), z.number().finite().nonnegative(), z.number().finite().nonnegative()]).optional(),
  }).strict(),
}).strict();

function schemaFor(name) {
  if (name === 'blockbench.get_animation') return animationSchema;
  if (name === 'blockbench.validate_contract') return contractSchema;
  if (name === 'blockbench.extensions.get' || name === 'blockbench.extensions.check_compatibility') return pluginSchema;
  if (name === 'blockbench.profiles.get' || name === 'blockbench.profiles.resolve_for_asset') return profileIdSchema;
  return emptySchema;
}

export function createServer(options = {}) {
  const callBridge = typeof options.callBridge === 'function'
    ? options.callBridge
    : async () => { throw new Error('BRIDGE_UNAVAILABLE'); };
  const server = new McpServer({name: 'rpg-asset-mcp', version: '0.1.0'});
  for (const name of TOOL_NAMES) {
    server.registerTool(
      name,
      {
        description: TOOL_DESCRIPTIONS[name],
        inputSchema: schemaFor(name),
        annotations: {readOnlyHint: true, destructiveHint: false, idempotentHint: true},
      },
      async (args) => {
        const result = await callBridge(name, args || {});
        return {
          content: [{type: 'text', text: JSON.stringify(result)}],
          structuredContent: result && typeof result === 'object' && !Array.isArray(result) ? result : {value: result},
        };
      },
    );
  }
  return server;
}

export function startStdio(options = {}) {
  return serveStdio(() => createServer(options), {legacy: 'reject'});
}

if (process.argv[1] && fileURLToPath(import.meta.url) === process.argv[1]) {
  startStdio();
  console.error('RPG Asset MCP sidecar running on stdio; Blockbench bridge must authenticate separately.');
}
