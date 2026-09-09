'use strict';

const core = require('../core/index.js');

function registerBlockbenchPlugin(bb) {
  let auditAction = null;
  let profileAction = null;
  let bridgeConnectAction = null;
  let bridgeDisconnectAction = null;
  let bridgeStatusAction = null;
  let bridgeRuntime = null;

  function show(result) {
    bb.Blockbench.showMessageBox({
      title: 'RPG Asset Toolkit',
      icon: result.errors.length ? 'error' : 'check_circle',
      message: core.formatReport(result),
      buttons: ['OK'],
    });
  }

  function showBridgeError(error) {
    const code = error && typeof error.code === 'string' ? error.code : 'BRIDGE_ERROR';
    const detail = error && typeof error.message === 'string' ? error.message : code;
    bb.Blockbench.showMessageBox({
      title: 'RPG Asset Toolkit — Live Bridge',
      icon: 'error',
      message: `${code}: ${detail}`.slice(0, 1024),
      buttons: ['OK'],
    });
  }

  function addToolAction(action) {
    bb.MenuBar.menus.tools.addAction(action);
    return action;
  }

  bb.Plugin.register('rpg_asset_toolkit', {
    title: 'RPG Asset Toolkit',
    author: 'Gustavaopere',
    description: 'Read-only structural and provider-aware contract QA with an optional authenticated desktop-local MCP Live Bridge.',
    icon: 'fact_check',
    version: '0.3.0',
    min_version: '5.1.6',
    variant: 'both',
    tags: ['Minecraft: Java Edition'],
    onload() {
      auditAction = addToolAction(new bb.Action('rpg_asset_toolkit_validate', {
        name: 'Validate RPG Asset',
        description: 'Run read-only structural checks on the active Blockbench project.',
        icon: 'fact_check',
        click() { show(core.validateProject(bb.Blockbench.Project, {})); },
      }));
      profileAction = addToolAction(new bb.Action('rpg_asset_toolkit_validate_profile', {
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
      }));

      if (bb.Blockbench.isWeb === false) {
        bridgeConnectAction = addToolAction(new bb.Action('rpg_asset_toolkit_live_bridge_connect', {
          name: 'Connect RPG Asset MCP (Read-only)',
          description: 'Connect this desktop Blockbench session to the authenticated numeric-loopback RPG Asset MCP sidecar.',
          icon: 'link',
          click() {
            bb.Blockbench.textPrompt('RPG Asset MCP Connection Descriptor (JSON)', '{}', async (text) => {
              try {
                if (bridgeRuntime) await bridgeRuntime.connection.disconnect();
                const liveBridge = require('./live_bridge_adapter.js');
                const runtime = liveBridge.createBlockbenchLiveBridgeRuntime(bb, text);
                await runtime.connection.connect();
                bridgeRuntime = runtime;
                bb.Blockbench.showQuickMessage?.('RPG Asset MCP read-only bridge connected', 2500);
              } catch (error) {
                bridgeRuntime = null;
                showBridgeError(error);
              }
            });
          },
        }));

        bridgeDisconnectAction = addToolAction(new bb.Action('rpg_asset_toolkit_live_bridge_disconnect', {
          name: 'Disconnect RPG Asset MCP',
          description: 'Disconnect the current read-only local bridge session.',
          icon: 'link_off',
          async click() {
            try {
              if (bridgeRuntime) await bridgeRuntime.connection.disconnect();
              bridgeRuntime = null;
              bb.Blockbench.showQuickMessage?.('RPG Asset MCP bridge disconnected', 2000);
            } catch (error) {
              bridgeRuntime = null;
              showBridgeError(error);
            }
          },
        }));

        bridgeStatusAction = addToolAction(new bb.Action('rpg_asset_toolkit_live_bridge_status', {
          name: 'RPG Asset MCP Bridge Status',
          description: 'Show non-secret connection state for the local read-only bridge.',
          icon: 'info',
          click() {
            const status = bridgeRuntime ? bridgeRuntime.connection.status() : {connected: false, generation: 0, capabilities: []};
            bb.Blockbench.showMessageBox({
              title: 'RPG Asset Toolkit — Live Bridge Status',
              icon: status.connected ? 'check_circle' : 'info',
              message: JSON.stringify(status, null, 2),
              buttons: ['OK'],
            });
          },
        }));
      }
    },
    onunload() {
      if (bridgeRuntime) {
        try { void bridgeRuntime.connection.disconnect(); } catch (_) { /* best effort during plugin unload */ }
      }
      bridgeRuntime = null;
      for (const action of [auditAction, profileAction, bridgeConnectAction, bridgeDisconnectAction, bridgeStatusAction]) {
        if (action) action.delete();
      }
      auditAction = null;
      profileAction = null;
      bridgeConnectAction = null;
      bridgeDisconnectAction = null;
      bridgeStatusAction = null;
    },
  });
}

module.exports = {registerBlockbenchPlugin};
