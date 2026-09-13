# Create: Schematic Checker

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81648b91c8e313e6a338
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Schematic Checker
- **Arquivo JAR:** `createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.27.45-6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Performance, QoL
- **Função:** Camada de segurança para schematics do Create: inspeciona uploads, bloqueia ou sanitiza NBT malicioso, exploits de duplicação/creative items/crash/lag e dados perigosos de addons antes da impressão no mundo.
- **Dependências:** Minecraft + Create. A build NeoForge 1.21.1 é direcionada ao Create 6.0.x. TorqueAPI 1.2.2 está embutida no JAR e não deve ser tratada como top-level separado.
- **Sobreposição:** Não duplica Fast Schematic Cannon ou Pattern Schematics: CSC valida/sanitiza segurança e compatibilidade dos schematics; os outros alteram velocidade, criação ou uso. Bridges/reparos devem manter uma única regra efetiva para cada exploit.
- **Compatibilidade/Riscos:** Interfere deliberadamente no fluxo de upload/scan/print de schematics. Regras excessivas podem remover conteúdo válido; regras frouxas podem deixar exploits. 2.27.45 desabilita por padrão o repair Quark fluidlogged-lava e corrige Copycats layers com consumed-item air para restaurar material requirement.
- **Observações:** JAR `createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`; runtime `2.27.45-6.0`; Client & Server. Config principal `config/CSC/config.toml`, regras locais `config/CSC/user_rule.json`; scan é assíncrono e pode manter logs/backups. 2.27.45: Quark fluidlogged-lava repair off por padrão; Copycats air consumed-item repair.
- **Procedência:** Modlist física canônica de 08/09/2026, 595 top-levels + runtime `createschematicchecker` 2.27.45-6.0 + CurseForge/Modrinth oficiais da release 2.27.45 e documentação oficial do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-schematicchecker
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — schematic security/sanitization authority, async scan, local/cloud rules, audit backups, Create/addon exploit coverage e regressões 2.27.45 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Schematic Checker 2.27.45-6.0 foi reconfirmado como `Instalado` na modlist física de 595 top-levels e reconstruído ao padrão técnico. Presença/benefício de segurança não foram convertidos em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🛡️ Versão física confirmada: `createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`, runtime `2.27.45-6.0`, NeoForge 1.21.1/Create 6.0.x. CSC é uma **camada de segurança e sanitização de schematics**.

## 1. Papel e authority
Create: Schematic Checker intercepta o fluxo de upload/validação de schematics antes que dados potencialmente maliciosos sejam impressos no mundo. Create continua authority do formato/printing; CSC decide se NBT, blocks, items e entities do upload passam, são sanitizados ou bloqueados conforme suas regras.

## 2. Scan assíncrono
O projeto declara que o scan é executado de forma assíncrona para não bloquear a main thread do servidor. O resultado do scan precisa ser associado ao upload correto; retries ou tasks atrasadas não podem aprovar/rejeitar outro schematic por engano.

## 3. Sanitização configurável
CSC pode sanitizar campos NBT, tags, blocks, items e entities configurados. Sanitização deve ser determinística e auditável: o schematic aceito precisa refletir exatamente as transformações autorizadas pelas regras ativas.

## 4. Config principal
Config oficial: `config/CSC/config.toml`. Opções documentadas incluem `core.Enable`, `core.BanBlock`, `core.BanTag`, `core.KillEntity`, whitelist de mod IDs, backup e controles online. Alterações podem ser recarregadas com `/csc reload`.

## 5. Regras locais
Regras customizadas podem ser definidas em `config/CSC/user_rule.json`. Esse arquivo é policy do servidor; não deve ser substituído silenciosamente por defaults após update.

## 6. Regras online
O projeto suporta sync opcional de regras online quando habilitado pelo operador. Auto-update é documentado como desabilitado por padrão. Mudança remota deve ser tratada como alteração de policy e auditada antes de abrir uploads públicos.

## 7. Backups e auditoria
CSC pode manter logs e backups opcionais de schematics enviados. Esses artefatos são evidência de diagnóstico; não são um segundo sistema de world state e não devem ser reimportados automaticamente sem nova validação.

## 8. Cobertura Create
A documentação oficial lista validação de belts, chain drives, mechanical arms, filters, fluid tanks, clipboards e outros block entities de alto risco. O objetivo é impedir manipulações de comprimento, targets, NBT, creative properties, lag/crash e duplication.

## 9. Addons
A cobertura inclui exploits específicos de addons, como Create: Enchantment Industry, Crafts & Additions, Integrated Farming e Create Big Cannons. Cada fix deve permanecer condicionado à estrutura real do provider; update de addon exige regression test.

## 10. Copycats — 2.27.45
A 2.27.45 corrige schematic layers de Copycats com `consumed-item` igual a air, restaurando o material requirement exibido. Regression gate: printing não pode produzir material grátis nem recusar uma layer válida já reparada.

## 11. Quark — 2.27.45
A mesma build deixa **desabilitado por padrão** o repair de duplicação ligado a fluidlogged lava do Quark; operador ainda pode ativá-lo na config. Não tratar o repair como sempre ativo.

## 12. Packet guards e Create 6
A linha recente também possui guards para pacotes/ações de Create 6. Esses guards são segurança, não gameplay. Um guard não deve bloquear ação legítima por descriptor/version drift sem log que permita diagnosticar a regra acionada.

## 13. Whitelist de mods
Whitelist de IDs pode restringir quais mods preservam NBT em schematics. Em pack grande, isso é forte: um addon legítimo não listado pode perder dados necessários. Atualizações da modlist precisam ser cruzadas com essa policy.

## 14. Client/server e multiplayer
A proteção efetiva é server-side/common; clientes podem enviar schematics, mas não decidem o resultado da validação. Em multiplayer, cada upload deve manter owner/contexto e resultado isolados.

## 15. Lifecycle
Validar startup, `/csc reload`, upload simultâneo, server shutdown durante scans, update de Create/addons, restore de backup em ambiente isolado e world restart.

## 16. Riscos
1. Regra permissiva deixar exploit conhecido.
2. Regra agressiva remover NBT legítimo.
3. Scan assíncrono associar resultado ao upload errado.
4. Rule cache ficar stale após reload.
5. Whitelist bloquear addon novo válido.
6. Backup ser confundido com schematic aprovado.
7. Quark repair ser assumido ativo quando está off.
8. Copycats voltar a permitir material gratuito.

## 17. Matriz de testes
1. Dedicated server boot com Create 6.0.x.
2. Upload de schematic vanilla/Create válido.
3. BanBlock/BanTag/KillEntity em cópia de teste.
4. `/csc reload` alterando regra sem restart.
5. Dois uploads simultâneos sem cruzar resultados.
6. Copycats consumed-item air — regression 2.27.45.
7. Quark repair off/on conforme config.
8. CBC/addons instalados com NBT legítimo.
9. Shutdown enquanto scan está pendente.
10. Logs/backups produzidos sem auto-reimport.

## 18. Evidência
- modlist física 08/09/2026: 2.27.45-6.0;
- CurseForge/Modrinth oficiais: security scan/sanitization, async scan, configs, rules e auditing;
- changelog 2.27.45: Quark repair disabled by default e Copycats material-requirement repair.

> 🔒 Boundary canônico: **Create define o schematic/printing; CSC decide se o payload é seguro o bastante para prosseguir**. Nenhum upload cliente deve contornar a decisão server-side.
