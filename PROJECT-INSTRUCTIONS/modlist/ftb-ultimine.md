# FTB Ultimine — 2101.1.15

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81a3aa8be4de4cd9c02d  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FTB Ultimine
- **Arquivo JAR:** `ftb-ultimine-neoforge-2101.1.15.jar`
- **Versão 1.21.1:** `2101.1.15`
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/FTBTeam/FTB-Ultimine/tree/1.21.1/main
- **Função:** Vein/shape mining e colheita em múltiplos blocos, com seleção extensível, custos de exhaustion/XP/cooldown, validação de ferramenta, preview client e liquidação server-authoritative.
- **Dependências:** FTB Library; source 2101.1.15 usa baseline FTB Library 2101.1.28, Architectury 13.0.6 e NeoForge 21.1.203. Integrações upstream incluem FTB Ranks, FTB EZ Crystals e Agricraft quando presentes.
- **Compatibilidade/Riscos:** Riscos: seleção client divergente do servidor, bypass de claims, custo/durability incorreto em quebra parcial, merge tags amplas, custom handlers duplicados, crop dupe e XP overflow. 2101.1.15 corrige overflow para níveis >=15466.
- **Sobreposição:** Pode se cruzar com outros vein miners/harvesters, mas Ultimine deve ser a única authority de sua seleção e liquidação. FTB Chunks continua authority de proteção e pode negar blocos individualmente.
- **Observações:** Defaults auditados: max_blocks 64; exhaustion_per_block 20; experience_per_block 0; require_tool false; require_valid_tool_for_block false; cooldown 0. Client preview/shape feedback não é autoridade de quebra.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial FTBTeam/FTB-Ultimine branch 1.21.1/main exatamente em 2101.1.15 + changelog oficial 2101.1.x.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FTB Ultimine 2101.1.15 source-pinned; shapes, configs, costs, attributes, selection/break handlers, client/server, protection interactions, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `ftb-ultimine-neoforge-2101.1.15.jar`, mod id `ftbultimine`, Minecraft 1.21.1 / NeoForge. O branch oficial `FTBTeam/FTB-Ultimine:1.21.1/main` declara exatamente `mod_version=2101.1.15`. O servidor é authority de seleção válida, custos e quebra; preview/shape feedback client-side não decide o resultado.

## 1. Identidade e versão
- **Mod:** FTB Ultimine.
- **JAR físico:** `ftb-ultimine-neoforge-2101.1.15.jar`.
- **Mod id:** `ftbultimine`.
- **Versão instalada:** `2101.1.15`.
- **Source pin:** branch `1.21.1/main`, exatamente 2101.1.15.
- **Baselines upstream:** Minecraft 1.21.1, NeoForge 21.1.203, Architectury 13.0.6 e FTB Library 2101.1.28.

## 2. Papel no modpack
FTB Ultimine implementa quebra/colheita em múltiplos blocos a partir de uma ação causal do jogador. Ele seleciona conjuntos por shape/handler, valida ferramenta e custos, aplica exhaustion/XP/cooldown conforme config e permite extensões por registries/API. Não deve ser confundido com miner automático: a ação parte do jogador e a liquidação deve acontecer no servidor.

## 3. Shapes e seleção
O source pinado contém shapes como `Shapeless`, `SmallShapeless`, `LargeShapeless`, `VeinMiner`, `MiningTunnel`, `SmallTunnel`, `LargeTunnel`, `EscapeTunnel`, `SmallSquare`, `SingleRow`, `SingleColumn` e base `Shaped`. Há registries próprios para block selection e block breaking, além de custom handlers introduzidos na linha 2101.1.x.
O client pode pré-visualizar o conjunto, mas a lista final quebrável precisa ser revalidada no servidor no momento da operação.

## 4. Config server — `ftbultimine-server`
Defaults confirmados no source 2101.1.15:

### Features
- `right_click_axe = true`;
- `right_click_shovel = true`;
- `right_click_hoe = true`;
- `right_click_harvesting = true`;
- `crystals = true`;
- `single_crop_harvesting = true`.

### Custos e limites
- `max_blocks = 64`, com range até 32768;
- `exhaustion_per_block = 20.0`;
- `experience_per_block = 0.0`;
- `require_tool = false`;
- `require_valid_tool_for_block = false`;
- `ultimine_cooldown = 0`.

### Outros
- `prevent_tool_break = 0`;
- `cancel_on_block_break_fail = false`;
- merge tags incluem `minecraft:base_stone_overworld`, `c:ores/*` e `forge:ores/*`;
- shaped merge tags default para `*`.
Mudanças de config limpam caches de tags e sincronizam cooldown; o source emite warning para `max_blocks` muito alto (>8192).

## 5. Config client — `ftbultimine-client`
Defaults auditados:
- `render_blocks = true`;
- blur de preview ligado, 2 passos;
- intervalos de render 1/2;
- `render_lines = false`;
- feedback de shape em `PREVIEW`, opacity 50;
- transition time 10.0;
- `invert_shape_scroll = false`;
- node quantity visível e inactive quantity escondida;
- `panel_scale = 100`;
- `double_tap_activation = true`, janela `250 ms`.
Esses valores são apresentação/input local; não devem conceder permissão server-side.

## 6. Attributes, ranks e extensibilidade
A 2101.1.13 adicionou modifiers/attributes:
- `ftbultimine:max_blocks_modifier`;
- `ftbultimine:cooldown_modifier`;
- `ftbultimine:exhaustion_modifier`;
- `ftbultimine:experience_modifier`.
O source também integra FTB Ranks para nós como `ftbultimine.max_blocks`. Custom block selection e block breaking handlers permitem que outros mods adaptem seleção/liquidação sem duplicar o core de Ultimine.

## 7. Tool validity, crops e compatibilidade
A 2101.1.12 adicionou `Require Valid Tool` e blacklist `ftbultimine:single_crop_harvesting_blacklist`. A linha 2101.1.x também inclui compat com FTB EZ Crystals, Agricraft e correção para climbing rope do Farmer's Delight. Essas integrações devem ser smoke-tested apenas quando o consumidor correspondente estiver no runtime.

## 8. XP, exhaustion e tool durability
A versão instalada 2101.1.15 corrige overflow aritmético em jogadores com níveis de XP muito altos (>=15466), porque o total XP vanilla é um inteiro de 32 bits. Isso torna cálculo de custo um risco real. Cada bloco adicional deve liquidar custo/durability uma única vez; preview ou retries não podem duplicar ou pular cobrança.

## 9. Client / server e lifecycle
- **Servidor:** valida seleção, ferramenta, permissões, custos, break handlers e resultados.
- **Cliente:** input, shape cycling, overlay/preview e feedback.
Testar login/relogin, mudança de dimensão, alteração de config, mudança de tool durante seleção, cancelamento, chunk unload e falha parcial no meio do conjunto. Cooldown state não pode ficar preso após death/reconnect.

## 10. Multiplayer e proteções
Em área claimada, FTB Chunks ou outro sistema de proteção pode negar parte da seleção. Ultimine deve respeitar a negação por bloco e a configuração `cancel_on_block_break_fail`, sem contornar fake-player/claim rules nem quebrar além do autorizado. Dois jogadores operando sobre o mesmo conjunto precisam convergir para um único world state.

## 11. Integrações concretas do pack
- **FTB Library 2101.1.35:** UI/config/networking.
- **FTB Teams/Chunks:** podem fornecer contexto de equipe/proteção por consumidor, mas não são ownership do algoritmo Ultimine.
- **FTB XMod Compat 21.1.11:** bridges específicas do ecossistema; validar apenas módulos realmente aplicáveis.
- **Farmer's Delight** está no pack; single-crop/right-click harvesting e climbing-rope fixes são regression gates relevantes.

## 12. Riscos técnicos
- seleção client diferente da validada no servidor;
- quebra parcial cobrando custos incorretos;
- bypass de claims/protection;
- merge tags amplas agregando blocos inesperados;
- `max_blocks` elevado causando custo de CPU/rede;
- overflow/underflow em XP/modifiers;
- tool durability aplicada mais ou menos vezes que blocos realmente quebrados;
- custom handler executar junto do handler padrão;
- crop harvesting duplicar drops/replant;
- cooldown stale após reconnect/death.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server boot com Ultimine 2101.1.15 + Library 2101.1.35.
- [ ] Cada shape seleciona apenas blocos esperados.
- [ ] `max_blocks=64` e modifiers/ranks respeitados.
- [ ] Require Tool / Require Valid Tool on/off.
- [ ] Tool quase quebrando com `prevent_tool_break` configurado.
- [ ] XP/exhaustion por bloco, inclusive jogador de XP extremamente alto.
- [ ] Falha de break parcial com `cancel_on_block_break_fail` on/off.
- [ ] Claims FTB Chunks negando parte da seleção.
- [ ] Single crop harvesting com Farmer's Delight e blacklist tag.
- [ ] Custom selection/break handler executa exatamente uma vez.
- [ ] Chunk unload/reload, death e reconnect durante cooldown.
- [ ] Dois jogadores mirando o mesmo veio sem dupe de drops.

## 14. Evidências e limites
**Source primário pinado:** `FTBTeam/FTB-Ultimine`, branch `1.21.1/main`, exatamente 2101.1.15.
**Classes auditadas:** configs server/client, registries de selection/breaking, shapes e changelog 2101.1.x.
**Modlist física:** confirma JAR e matriz FTB atual.
**Não foram executados testes de runtime nesta catalogação.**
