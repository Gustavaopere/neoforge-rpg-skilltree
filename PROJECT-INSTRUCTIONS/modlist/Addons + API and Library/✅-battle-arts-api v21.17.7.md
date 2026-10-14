# Battle Arts API

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#65**: `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`, mod id `battle_arts_api`, runtime `21.17.7`, SHA-1 `f3386cb8e67351f39cd8bda5cd0a0801bfebc732`.

> **Reauditoria física — 19/09/2026.** JAR top-level reconfirmado na modlist física atual: `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`, versão `21.17.7`. Paridade Notion → GitHub revalidada; URL da própria página Notion removida.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física usada:** `modlist(1).txt` — 586 mods top-level
- **Auditoria de migração Notion → GitHub:** 2026-09-19

## Propriedades do banco

- **Mod:** Battle Arts API
- **Arquivo JAR:** battle_arts_api-21.17.7-mc1.21.1-neoforge.jar
- **Versão 1.21.1:** 21.17.7
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, RPG
- **Função:** API extensível do ecossistema Battle Arts/Epic Fight para Battle Styles, Combat Arts e Proficiencies, sem substituir o pipeline base de combate.
- **Dependências:** Ecossistema Epic Fight/Battle Arts; pack confirma Epic Fight 21.17.3.1. Consumidor top-level Battle Arts separado não foi confirmado nesta checagem física específica.
- **Sobreposição:** API complementar; não substitui Epic Fight.
- **Compatibilidade/Riscos:** Double-hit/damage, desync de style/proficiency, schema datapack incompatível e callbacks de animação virando authority são os principais riscos. Linha 21.17.x renomeou `slash_modifier` para `damage_attribute_modifier`. Release 21.17.7 foi testada upstream contra NeoForge 21.1.219; o último inventário físico histórico do pack (#001, 29/09/2026) registra NeoForge **21.1.250**, exigindo smoke/regression sem presumir incompatibilidade.
- **Observações:** API 21.17.7. Battle Styles, Combat Arts e Proficiencies são superfícies oficiais; não tratar como combat engine independente.
- **Procedência:** modlist.txt física consultada em 11/09/2026 (NeoForge 21.1.248 naquele snapshot) + CurseForge/changelogs oficiais Battle Arts API 21.17.7 + Epic Fight 21.17.3.1 físico; o inventário histórico mais recente (#001, 29/09/2026) registra **NeoForge 21.1.250**. Fonte histórica antiga não prevalece sobre o inventário posterior. Reconciliação final: JAR/runtime permanecem exatamente `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar` / `21.17.7`; drift de ambiente testado continua como gate de QA, não divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/battle-arts-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #65: `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar` / `21.17.7` conferidos contra a modlist atual; Battle Styles/Combat Arts/Proficiencies, schema 21.17.x e boundary Epic Fight preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Battle Arts API 21.17.7 e seus contratos de Battle Styles, Combat Arts e Proficiencies no ecossistema Epic Fight. Em 09/09/2026, a release foi revalidada e o delta entre NeoForge testado 21.1.219 e runtime **então usado** 21.1.248 foi registrado como gate de QA, sem converter instalação em decisão de manter/remover. O registro desse evento histórico não indica a versão atual: a snapshot posterior lista 21.1.250.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`, mod id `battle_arts_api`, runtime `21.17.7`. O pack também possui Epic Fight `21.17.3.1`; a API pertence ao ecossistema Battle Arts/Epic Fight e não é um combat provider independente.

## 1. Papel e autoridade
Battle Arts API é a camada extensível separada do projeto Battle Arts para permitir que outros mods/datapacks definam **Battle Styles, Combat Arts e Proficiencies**. Ela fornece contratos de integração; **Epic Fight continua authority do pipeline base de combate/animação** quando suas superfícies são usadas.

A API não deve receber dano, stamina ou hit settlement paralelo criado pelo modpack.

## 2. Battle Styles
Battle Styles representam modos/estilos de combate consumidos pelo ecossistema. Uma integração segura deve tratar o style como estado provider-native e não duplicar pose, animation set ou modifiers em um sistema externo.

Mudança de style precisa ser sincronizada e validada pelo servidor quando afeta gameplay.

## 3. Combat Arts
Combat Arts são extensões/ações de combate registráveis por consumidores. Regras de causalidade:
- input do cliente solicita a ação;
- provider valida estado/cooldown/requisitos;
- hit/damage é resolvido uma vez pelo pipeline canônico;
- animação e VFX não podem causar um segundo settlement.

## 4. Proficiencies
Proficiencies são outra superfície declarada da API para especialização/compatibilidade. Elas devem permanecer fonte de dados do provider/consumer; o RPG Skill Tree não deve inventar uma proficiência paralela com o mesmo significado sem bridge explícita.

## 5. Data-driven/API evolution
A linha 21.17.x teve mudança documentada de nomenclatura `slash_modifier` → `damage_attribute_modifier` na 21.17.6. Isso é um risco real para datapacks/consumidores que ficaram presos ao nome antigo. A versão física 21.17.7 deve ser validada com dados escritos para a mesma família de schema.

## 6. Relação com Epic Fight
O último inventário histórico registra Epic Fight 21.17.3.1 e NeoForge **21.1.250**. A release Battle Arts API 21.17.7 declara testes upstream em NeoForge **21.1.219** e Minecraft 1.21.1; o delta não demonstra incompatibilidade, mas exige smoke/regression no ambiente físico real. Battle Arts API não substitui:
- attack animation state;
- hit detection;
- damage settlement;
- entity combat capability/state;
- synchronization base do Epic Fight.

Qualquer Combat Art que interfira nesses pontos deve utilizar hooks do provider, não reexecutar ataque vanilla em paralelo.

## 7. Estado físico do ecossistema no pack
A busca física confirma Battle Arts API e Epic Fight. A tabela histórica de 587 posições contém somente **Battle Arts - API #065** ao filtrar pelo nome `Battle Arts`; não há entrada top-level separada com esse nome. Isso não prova que a biblioteca não possua consumidores (podem existir dependências indiretas/recursos internos), nem autoriza removê-la sem grafo de dependências e teste. Sua necessidade deve ser rastreada por consumidores reais, não assumida apenas pelo nome.

## 8. Client/server e multiplayer
- style/proficiency/eligibility/cooldown e hit settlement são servidor quando afetam gameplay;
- input, HUD e animação são cliente/apresentação;
- troca de arma/style durante animação precisa resultar em uma única state transition válida;
- dois packets ou animation callbacks não podem executar a mesma art duas vezes.

## 9. Lifecycle
Validar login/relogin, death/respawn, dimension change, equip/unequip, troca de battle style, reload de datapack e reconnect durante Combat Art. State persistente precisa usar owner correto e limpar referências de entidade/arma inválidas.

## 10. Riscos
1. Double-hit/double-damage ao combinar callback da art com pipeline Epic Fight.
2. Datapack usando chave antiga de modifier.
3. Style/proficiency desync cliente-servidor.
4. Consumidor assumindo versão de Epic Fight diferente da física.
5. Animation event virando authority de gameplay.
6. API instalada sem consumidor real ser confundida com conteúdo jogável.

## 11. Matriz de testes
1. Dedicated server boot com Epic Fight 21.17.3.1.
2. Datapack/schema de 21.17.7 sem chaves obsoletas.
3. Troca de Battle Style em multiplayer.
4. Combat Art executa exatamente um hit/settlement por janela válida.
5. Cancelamento, weapon swap, death e dimension change durante art.
6. Proficiency/state não persiste no jogador errado após respawn/reconnect.

## 12. Evidência
- modlist física atual: Battle Arts API 21.17.7 e Epic Fight 21.17.3.1;
- CurseForge oficial Battle Arts API;
- descrição oficial das superfícies Battle Styles, Combat Arts e Proficiencies;
- changelog 21.17.6 da linha atual registrando a migração de modifier.

> ⚔️ Ownership canônico: Battle Arts API = contratos/extensões; Epic Fight = pipeline base de combate. O catálogo não promove a API a segundo combat engine.


## 13. Revalidação de loader e release oficial — 13/10/2026

**Estado físico histórico mais recente que pôde ser consultado:** modloader **NeoForge #001 `21.1.250`** (entrada de 29/09/2026), Battle Arts API **#065 `battle_arts_api-21.17.7-mc1.21.1-neoforge.jar`** (runtime `21.17.7`) e Epic Fight **#260 `21.17.3.1`**. A snapshot tem **587 entradas top-level incluindo o modloader**. Não se realizou nova inspeção dos arquivos físicos em 13/10/2026; a autoridade consultada é a última tabela documentada.

**Versão publicada upstream:** o [catálogo oficial de arquivos CurseForge](https://www.curseforge.com/minecraft/mc-mods/battle-arts-api/files/all) apresenta `21.17.7-mc1.21.1-neoforge` como **latest release identificada para NeoForge/Minecraft 1.21.1**. O [arquivo oficial exato, File ID `8094994`, publicado em 15/05/2026](https://www.curseforge.com/minecraft/mc-mods/battle-arts-api/files/8094994) nomeia o mesmo JAR e confirma teste upstream contra **NeoForge 21.1.219 / Minecraft 1.21.1**. Portanto **não há upgrade de Battle Arts API a promover** por essa pesquisa.

**Changelog exato da versão:** a 21.17.7 declara a correção de **ícones de skillbooks ausentes em razão de atualização do `mod_id`**. Este fix do upstream é distinto do histórico 21.17.6 (`slash_modifier` → `damage_attribute_modifier`); documentar ambos sem misturar os dois changelogs ou inventar nomes/IDs de registries que não foram extraídos da release. O [CurseForge classifica a distribuição como Client & Server e lista Epic Fight como dependência obrigatória](https://www.curseforge.com/minecraft/mc-mods/battle-arts-api/relations/dependencies). Não assumir que os dois módulos são servidores independentes de combate nem que há um mod `Battle Arts` top-level separado sem inspecionar os JARs.

**Desvio de versões:** a diferença `NeoForge 21.1.219` (testado pelo autor) → `21.1.250` (pack) **não é demonstração de quebra ABI**. Registros anteriores que citam `21.1.248` permanecem como história de 09–11/09/2026, **não como estado atual**. O ownership é Epic Fight para combate base e Battle Arts API para suas superfícies de extensão.

**QA futuro, não executado nesta auditoria:** re-inspecionar o JAR e hash físicos; validar bytecode/metadata de dependência do Epic Fight; boot cliente e servidor dedicado com NeoForge 21.1.250; UI/ícones dos skillbooks; save/load, troca de estilo, skillbook/proficiency, datapack schema `damage_attribute_modifier` sem `slash_modifier` legado, relog/multiplayer e execução única de danos/callbacks. Manter **`21.17.7` como versão instalada histórica**, sem trocar JAR, atualizar o ordinal físico ou declarar teste runtime bem-sucedido.

**Decisão documental:** corrigir o desvio **21.1.248→21.1.250** apenas nas afirmações de estado corrente e preservar o registro histórico com data. Nenhum componente adicionado, removido ou alterado fisicamente.
