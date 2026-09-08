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
