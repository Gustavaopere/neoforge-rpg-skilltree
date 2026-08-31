# Matriz de Cobertura e Delta de Capacidades — Projetos Próprios

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81ee99a7e465e8333251

Os projetos próprios estão em desenvolvimento contínuo. O Chat 1 deve provar, em cada lote, os dois sentidos da auditoria: **perk → provider** e **provider → árvore**. A presença de um plano, HUD, branch ou componente parcial não promove um sistema inteiro a runtime disponível.

## 1. Gate obrigatório por lote

Antes de fechar cada lote exato de 10 perks:

1. buscar `main` e `plans/STATUS.md` frescos de RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana;
2. comparar cada SHA com o último baseline reconciliado;
3. identificar capacidades jogáveis novas ou semanticamente alteradas;
4. classificar cada capacidade como `COBERTA POR PERK EXISTENTE`, `PERK PRÓPRIA`, `ESPECIALIZAÇÃO`, `BRIDGE`, `COBERTO POR SISTEMA UNIVERSAL`, `PROGRESSÃO NATIVA AUTORITATIVA`, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`;
5. registrar authority, boundary, causalidade, deduplicação, fallback e fail-closed quando aplicável;
6. não avançar o baseline enquanto qualquer linha detectada estiver sem disposição explícita.

Detectar uma lacuna não transforma um lote de 10 em 11 perks. A necessidade fica registrada para ciclo posterior.

## 2. Regras permanentes de authority

- Volcanoes conserva authority de geologia, depósitos, Atmosphere, respiração/gases, pressão, proteção e integrações ambientais já fechadas.
- Enshrouded conserva authority de Shroud, Exposure, Flame Passage/Ward/Sanctuary, ecologia corrompida e Story/Lich conforme cada estágio canônico.
- Black Arcana conserva authority de casts, Arcane Danger, Arcane Resistance, Corruption Resistance, Arcane Strain e Backlash.
- RPG Skill Tree conserva authority de progressão, node effects, critical/root-action resolution e demais serviços canônicos próprios.
- Integração temática não cria bridge. Shroud ≠ Corruption; Arcane Strain ≠ Exposure; Atmosphere ≠ Shroud; bossbar ≠ identidade BOSS.
- Client/HUD não é autoridade de gameplay.

## 3. Capacidades que exigem rastreamento explícito

### Volcanoes

Atmosphere/O₂; respiração e consumo de ar; gases/particulados; pressão atmosférica/hidrostática; volumes/equipment de proteção; geologia/depósitos; tectônica/terremotos; vulcanismo/geotermia; bridges ambientais/tecnológicas.

### Enshrouded

Shroud severity/query; Exposure/Deadly/Red/Madness; Flame Level/Passage/Ward/Sanctuary/rituais; Corrupted Ecology/MagicResistanceService; Lich/Story e rewards apenas conforme runtime canônico.

### Black Arcana

Arcane Resistance; Corruption Resistance; Arcane Strain; Backlash/provenance; danger profiles/tiers; resource/cost pipeline; equipment contributions/containment; rituais/domínios quando canônicos.

### RPG Skill Tree

Novos atributos/recursos/boundaries públicos; classes/masteries/subtrees; world scaling; itemização; corpos/identidades; compêndio/cartografia somente no estado real de `main`.

## 4. Baseline histórico reconciliado — 2026-08-30

| Projeto | Baseline anterior | Disposição relevante registrada naquele ciclo |
|---|---|---|
| RPG Skill Tree | `f448aa0b4f9df400011873e9ad26771209876ad4` | documentação canônica dos projetos próprios integrada; sem capacidade pendente daquele delta. |
| Volcanoes | `602e0188c123ac8531d3413a5630daa22e3d761f` | produtor hidrotermal bounded de Cu/Fe/Au permaneceu Volcanoes-owned; handoff seletivo RNS ainda era `SEM HOOK SEGURO`. |
| Enshrouded | `77552a3d7f089a47908c109f5f8c19aff8a0f97d` | Sanctuary/Flame Ward = `PROGRESSÃO NATIVA AUTORITATIVA`; Story State 06.01 idem. |
| Black Arcana | `07263ae9bad12eba6ed500992991faa36ad598b2` | sem avanço relevante naquele snapshot. |

Os registros acima são históricos. O baseline operacional mais novo está na seção 6.

## 5. Delta do lote A0061–A0070 — 2026-08-31

Fetch fresco realizado antes da primeira perk do lote:

- RPG Skill Tree: `main@6ed628864199e74af23e6234d126959829f3c968`;
- Volcanoes: `main@a47bb868de9b4846d8ae9afb94374f9672ab381e`;
- Enshrouded: `main@391ea82203d30cb392a3397f92e2a3cbe7fb6128`;
- Black Arcana: `main@526d8196087c863e9df64051d5d39d88c3050856`.

| Projeto | Capacidade nova/alterada desde o baseline | Estado real / evidência | Decisão principal | Perk(s)/ação | Boundary / authority | Fail-closed |
|---|---|---|---|---|---|---|
| RPG Skill Tree | runtime geral A0061–A0080 para dano físico, penetração, Impact, condições de vida e classificação BOSS/ELITE | CANÔNICO no código de `main@6ed6288...`: `A0061A0080CombatPolicy`, `A0061A0080EpicFightHooks`, `MartialTargetClassifier` | **COBERTA POR PERK EXISTENTE** | A0061–A0070 neste lote; A0071+ permanece fora do lote | root action físico e resolvedores canônicos do RPG; uma contribuição por identidade | rotas sem provider/binding real permanecem zero; A0067 explicitamente sem binding de janela ofensiva |
| RPG Skill Tree | bridge de projéteis físicos reaproveitando A0061–A0080 | CANÔNICO no projectile runtime; hits de bow/crossbow com provenance correlacionada | **COBERTO POR SISTEMA UNIVERSAL** | A0061/A0062/A0063/A0065/A0068/A0069/A0070 podem consumir; A0066 não inventa Impact em projectile | projectile/root provenance canônica; provider do projétil mantém mechanics nativas | sem receipt físico/Impact seguro, parcela dependente não aplica |
| RPG Skill Tree | avanços paralelos de Compêndio/itemização/classes fora do contrato dessas dez perks | estado misto conforme `plans/STATUS.md`; não alteram o root physical contract A0061–A0070 | **NÃO DEVE SER INTEGRADO** neste lote | nenhuma perk adicional | subsistemas próprios preservam authority | não usar plano/feature não pertinente como atalho para o lote |
| Volcanoes | coexistência hidrotermal/RNS amadurecida: Volcanoes mantém corpos hidrotermais bounded/authoritative e integração de prospecção sem transferir genericamente worldgen | CANÔNICO/PARCIAL conforme `main@a47bb86...` e `plans/STATUS.md` | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma A0061–A0070; perks geológicas futuras só por boundary read-only | Volcanoes-owned deposit/geology; RNS continua authority do worldgen nativo que lhe pertence | perks não produzem minério, não escrevem ownership e não inferem depósito |
| Volcanoes | hardening/performance/world-upgrade/admin avançados | infraestrutura, não nova capacidade de combate MARTIAL | **NÃO DEVE SER INTEGRADO** | nenhuma | infrastructure provider-owned | N/A |
| Enshrouded | Stage 06 Lich & Story avançou de Story State parcial para boss provider, manifestação, Lich Skull/reward e ritual canônicos | CANÔNICO em `main@391ea82...`; `enshrouded:shroud_lich` é registry identity nativa | **BRIDGE** apenas para identidade BOSS read-only de A0070 | A0070 pode classificar somente `enshrouded:shroud_lich` | RPG lê registry identity; Enshrouded conserva manifestação, arena, fases, Exposure, morte, Story, reward e ritual | sem exact identity/adapter, A0070 não aplica; bossbar/fase não são prova |
| Enshrouded | Lich Skull, reward issuance, ritual e Story lifecycle | CANÔNICO no Stage 06 atual | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma mutação A0070 | serviços Story/reward/ritual do Enshrouded | RPG não concede reward, não avança Story e não replica ledger |
| Enshrouded | HUD de Exposure/Shroud | client experience canônica, read-only | **NÃO DEVE SER INTEGRADO** como gameplay provider | nenhuma | client presentation only | HUD nunca autoriza dano/gate/Story |
| Black Arcana | hardening de Arcane Danger, inclusive gateway protegido para dano arcano hostil e regressões de persistence/fail-closed | CANÔNICO em `main@526d819...` | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma perk MARTIAL nova | Black Arcana mantém Arcane Danger/Backlash/protection authority | `ARCANE_BACKLASH`, hazards e dano arcano não viram “ataque físico direto do jogador” para A0061–A0070 |
| Black Arcana | release/provenance/hardening documental | infraestrutura | **NÃO DEVE SER INTEGRADO** | nenhuma | project hardening | N/A |

### Resultado provider → árvore

- Nenhuma capacidade detectada exige uma 11ª perk no lote A0061–A0070.
- O único bridge novo diretamente pertinente é **Enshrouded Shroud Lich → classificação BOSS read-only de A0070**.
- A0067 permanece `SEM HOOK SEGURO` no runtime atual e, por isso, o design exige node indisponível/não comprável até o binding existir.
- Volcanoes e Black Arcana não ganham integração MARTIAL artificial apenas por possuírem hazards/dano.

## 6. Baseline operacional após disposição completa do delta — 2026-08-31

| Projeto | Baseline para o próximo delta | Observação |
|---|---|---|
| RPG Skill Tree | `6ed628864199e74af23e6234d126959829f3c968` | snapshot fresco de gameplay usado para o lote; o PR de auditoria deste lote é documental e não cria nova capacidade jogável por si. |
| Volcanoes | `a47bb868de9b4846d8ae9afb94374f9672ab381e` | delta RNS/hardening classificado integralmente acima. |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` | Stage 06/Lich + HUD classificados integralmente; bridge A0070 limitada à registry identity. |
| Black Arcana | `526d8196087c863e9df64051d5d39d88c3050856` | hardening Arcane Danger classificado; nenhuma falsa integração MARTIAL. |

O próximo Chat 1 deve comparar `main` fresco contra estes SHAs e registrar `SEM DELTA RELEVANTE` quando a diferença for apenas documental/merge sem nova capacidade jogável.