# Auditoria de delta Simply Swords — A0001–A0010

## Escopo

- **INÍCIO:** A0001
- **FIM:** A0010
- **Estado de entrada:** lote previamente fechado/implementado, reaberto somente para cobertura da modlist nova.
- **Base inicial do RPG Skill Tree:** `main@0a4aa6adb91572a34d90f51969217f875c803644`.
- **Runtime alterado por este Chat 1:** **NÃO**.
- **A0011+:** não iniciado neste ciclo.

Esta reauditoria é dirigida pelo delta de cinco JARs adicionados ao pack:

1. Simply Swords — `simplyswords-neoforge-1.70.2-1.21.1.jar`;
2. Simply More — `simplymore-forge-1.3.0_alpha.jar`;
3. Integrated Simply Swords — `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar`;
4. Simply Swords: Cataclysm — `simplycataclysm-1.0.2+1.21.1+neoforge.jar`;
5. Simply Tooltips — `SimplyTooltips-neoforge-0.1.5.jar`.

Também foi considerado `Epic Fight - Mod Compat 1.1.0`, pois é a bridge instalada que atribui presets/capabilities Epic Fight a Simply Swords/Simply More quando existe mapeamento limpo.

## Fontes obrigatórias

Foram cruzados os critérios consolidados, os quatro guias completos, o protocolo Chat 1, os dez dossiês A0001–A0010, o `STATUS.md`, a implementação Epic Fight atual e as dez páginas canônicas do Notion.

### Delta dos projetos próprios

Fetch fresco antes do fechamento:

| Projeto | `main` fresco | Disposição para A0001–A0010 |
|---|---|---|
| RPG Skill Tree | `0a4aa6adb91572a34d90f51969217f875c803644` | `SEM DELTA RELEVANTE` além desta própria reauditoria de provider. O runtime MARTIAL continua usando capability/eventos Epic Fight e não possui adapter Simply direto. |
| Volcanoes | `8fa06118baa6460ffc865933d5ebd5b85eacdcb4` | `NÃO DEVE SER INTEGRADO` ao lote de espadas/machados; avanços de Hardening não criam boundary MARTIAL. |
| Enshrouded | `368f30c710246580e47e262462118f8b9e4a03ea` | `NÃO DEVE SER INTEGRADO` a estas perks; avanço Lich/Story não cria classificação de arma nem root MARTIAL. |
| Black Arcana | `73c14ce55ff918bb8a81daeb99a352607ef11064` | `SEM DELTA RELEVANTE` para SWORD/AXE; Arcane Danger/RPG integration não altera a regra de que `ARCANE_BACKLASH` é terminal e não herda proc/crítico/Mastery MARTIAL. |

Nenhum baseline é usado para promover capacidade planejada como runtime disponível.

## Authority do stack Simply

| Capacidade | Authority | Disposição provider → árvore |
|---|---|---|
| Weapon type/família Simply | Simply Swords / addon que registra o tipo; preset/capability de combate pelo Epic Fight Compat | **COBERTO POR SISTEMA UNIVERSAL** quando a capability Epic Fight resolve a família canônica da perk. |
| Implicit rolado | Simply Swords / addon | **PROVIDER-NATIVE**; pode coexistir com a perk, mas não é rerrolado/reexecutado pelo RPG. |
| Runic Power / Greater Runic Power | Simply Swords | **PROGRESSÃO NATIVA AUTORITATIVA**. |
| Awakening / Runic Forge / ability unlock | Simply Swords | **PROGRESSÃO NATIVA AUTORITATIVA**; RPG não concede níveis nem antecipa unlock. |
| Sockets / gem powers | Simply Swords | **PROGRESSÃO/ESTADO NATIVO AUTORITATIVO**; helpers já escalados não recebem novo scaling RPG. |
| Simply More weapon types | Simply More + Simply Swords | **COBERTO POR SISTEMA UNIVERSAL** quando Epic Fight resolver a família. |
| Simply More Unique específica | Simply More 1.3.0 ALPHA | **SEM HOOK SEGURO** quando o efeito exato não puder ser provado no artifact instalado; filename alpha é insuficiente para inferir revisão/Unique. |
| Integrated Simply Swords | material/tier do mod de origem + família Simply | **COBERTO POR SISTEMA UNIVERSAL**; não cria perk nominal. |
| Cataclysm material families | Simply Swords: Cataclysm + Simply Swords | **COBERTO POR SISTEMA UNIVERSAL** por família; traits especiais permanecem addon-owned. |
| Cursium Accursed Rage | Simply Swords: Cataclysm | **PROVIDER-NATIVE**; não duplicar stacks/dano. |
| Ignitium Blazing Brand/lifesteal | Simply Swords: Cataclysm | **PROVIDER-NATIVE**; não contornar normalização por attack speed nem transformar heal em proc RPG. |
| Witherite Mecha Pulse/Smite | Simply Swords: Cataclysm | **PROVIDER-NATIVE**; não criar charge/cooldown/stun/regen paralelo. |
| Simply Tooltips | Simply Tooltips | **NÃO DEVE SER INTEGRADO** mecanicamente. Tooltip é apresentação, nunca authority de estado/classificação. |

### Regra transversal

Epic Fight/Epic Fight Compat são authority de **moveset/preset/capability de combate**, não de Implicit, Awakening, Runic Power, Unique ability, gem power ou trait Cataclysm. Inversamente, possuir um `weaponType` Simply não autoriza o RPG a inferir uma família Epic Fight por namespace, nome, modelo, material ou tooltip. Se a capability não provar a família requerida, a perk permanece fail-closed.

Uma ação direta do jogador deve continuar tendo **um único root MARTIAL**. Bleed, double damage, double strike, execute, ability damage, gem power, chain/AoE e demais resultados derivados não se tornam novos roots apenas porque vieram de uma arma do jogador.

## Decisão por perk

| Perk | Resultado do delta |
|---|---|
| A0001 Treino com Espadas I | **CORRIGIDA NO DESIGN.** Armas Simply recebem o bônus somente quando Epic Fight Compat resolve `SWORD`; provider-derived hits não recebem A0001 novamente. |
| A0002 Treino com Espadas II | **CORRIGIDA NO DESIGN.** `ModifyAttackSpeedEvent` continua o boundary; proc nativo de attack speed compõe pelo valor efetivo uma vez, sem retrigger/recalc. |
| A0003 Precisão com Espadas | **CORRIGIDA NO DESIGN.** Katana/double-strike/execute/abilities/gems não abrem nova rolagem crítica MARTIAL; Awakening/gem scaling não é duplicado. |
| A0004 Ritmo do Duelista | **CORRIGIDA NO DESIGN.** no máximo 1 Ímpeto/root direto; fan-out/AoE/proc/ability não geram cargas extras. Deflect Simply não é defesa técnica sem receipt causal próprio. |
| A0005 Abertura de Guarda | **CORRIGIDA NO DESIGN.** armor-ignore/sunder/Deflect/traits não são proof de guarda; A0005 aplica somente sua parcela canônica e não reexecuta Implicit. |
| A0006 Riposta Perfeita | **CORRIGIDA NO DESIGN.** arma Simply pode consumir Riposta via `SWORD`, mas Deflect permanece fail-closed como gatilho técnico sem adapter causal. RPG não opera Awakening/Unique ability. |
| A0007 Treino com Machados I | **CORRIGIDA NO DESIGN.** armas Simply entram só quando resolvidas como `AXE`; Bleed/procs não criam roots adicionais. |
| A0008 Treino com Machados II | **CORRIGIDA NO DESIGN.** cadência segue Epic Fight; o RPG não retriggera proc de attack speed nem bypassa normalização Cataclysm baseada em attack speed. |
| A0009 Precisão com Machados | **CORRIGIDA NO DESIGN.** Bleed/ability/gem/Cursium/Ignitium/Witherite derived effects não recebem nova resolução crítica. |
| A0010 Pressão do Carrasco | **CORRIGIDA NO DESIGN.** Fúria exige `direct + hostile + actualDamage + AXE + autoria`; Bleed/Accursed Rage/Blazing Brand/Mecha Pulse/Smite/lifesteal/stun/regen não concedem Fúria separadamente. |

Nenhum coeficiente, rank, custo, gate, topologia ou dependência A0001–A0010 precisou ser alterado. O delta é de **provider coverage, authority, causalidade, deduplicação e fail-closed**.

## Notion

Fetch fresco foi executado em A0001–A0010 antes da escrita. As **10/10 páginas** receberam correções em `Provider/Mods`, `Hook`, `Fallback` e `Regra` compatíveis com a responsabilidade de cada perk. Após a escrita foram feitos **10/10 re-fetchs**, todos PASS, confirmando persistência.

## Nove eixos obrigatórios

| Eixo | Estado do lote após o delta |
|---|---|
| 1. Dependências e bloqueios | ✅ PASS — inalterados e semanticamente válidos. |
| 2. Integrações globais/modlist | ✅ PASS — stack Simply integrado sem duplicar pipelines provider-native. |
| 3. Qualidade/identidade | ✅ PASS — nenhuma perk foi convertida em bônus genérico ou em proxy de Implicit. |
| 4. Ramificação/distância/topologia | ✅ PASS — nenhuma mudança topológica necessária. |
| 5. Especializações | ✅ PASS — weapon types Simply são cobertura universal; não viram classes/perks nominais. |
| 6. PT-BR | ✅ PASS — texto player-facing existente preservado; novos termos técnicos ficam no contrato. |
| 7. Notion completo | ✅ PASS — 10/10 corrigidas + 10/10 re-fetched. |
| 8. NeoVitae | ✅ PASS — ausente. |
| 9. Cobertura modlist/providers | ✅ PASS — cinco novos mods receberam disposição explícita e Epic Fight Compat teve authority separada. |

## Checklist técnica consolidada — 18 critérios

1. Efeitos reais: PASS; nenhuma mecânica Simply foi inventada.
2. Provider-native first: PASS; Implicits/Awakening/Runic/gems/Uniques/traits permanecem no provider.
3. Sem mecânica inventada: PASS.
4. Fail-closed: PASS para família não resolvida, Deflect sem receipt e Simply More Unique não comprovada.
5. Fallback preserva identidade: PASS.
6. Mastery por feitos discretos: N/A neste delta; nenhuma nova fonte de Mastery criada.
7. Anti-farm/rebuild: N/A adicional; nenhuma progressão nova criada.
8. Atribuição causal: PASS; somente root direto do jogador alimenta estas perks.
9. Pipeline canônico único: PASS; crítico/Ímpeto/Fúria permanecem deduplicados.
10. Custos/recursos reais: PASS; Ímpeto/Fúria continuam RPG-owned e recursos Simply não são clonados.
11. Sem geração gratuita: PASS.
12. Read-only: N/A.
13. Versionamento explícito: PASS para Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated 1.4.0, Cataclysm 1.0.2, Simply Tooltips 0.1.5, Epic Fight Compat 1.1.0 e Epic Fight 21.17.3.1.
14. Coerência estrutural: PASS; topologia/ranks/custos inalterados.
15. Dependências semanticamente corretas: PASS.
16. Sem sobreposição indevida: PASS; efeitos Simply não concedem progressão em múltiplos roots.
17. Implementabilidade: PASS no design; boundaries e fail-closed estão explícitos.
18. Verificação pós-escrita: PASS 10/10 no Notion.

## Pendências para Chat 2

### `P-SIMPLY-A0001-10-01` — prova provider-present do stack

Adicionar teste/acceptance com os artifacts exatos instalados para provar que Simply Swords 1.70.2 + Simply More 1.3.0 ALPHA + Epic Fight Compat 1.1.0 entregam as categorias Epic Fight esperadas para amostras `SWORD`/`AXE` sem transformar Implicit/ability/gem/trait derived damage em novo root MARTIAL. Integrated Simply Swords e Simply Swords: Cataclysm devem entrar na matriz quando houver amostra representativa disponível.

**Comportamento até a prova:** o runtime existente continua provider-native/fail-closed; este Chat 1 não cria heurística nem adapter Simply direto.

### `P-SIMPLY-A0006-01` — Deflect como defesa técnica

O Deflect de Claymore/Longsword não deve armar A0004/A0006 por inferência. Integrar apenas se a versão exata expuser receipt público causal capaz de provar a defesa técnica e deduplicá-la do pipeline Epic Fight.

**Comportamento atual:** fail-closed para Deflect; `ON_DODGE` continua rota técnica comprovada e suficiente para A0006.

### `P-SIMPLY-ALPHA-01` — Uniques de Simply More

O filename `simplymore-forge-1.3.0_alpha.jar` não prova qual revisão alpha/qual implementação de cada Unique está presente. Qualquer integração nominal com Unique específica deve validar classe/efeito no artifact local antes de ser habilitada.

**Comportamento atual:** `SEM HOOK SEGURO / FAIL-CLOSED` para comportamento específico não provado.

## Conclusão do ciclo

**A0001–A0010: LOTE REAUDITADO E FECHADO NO DESIGN para o delta Simply Swords.**

Os novos mods não exigem novas perks nominais neste recorte. Eles exigem que as perks de arma existentes reconheçam a separação de authority: **Epic Fight/Epic Fight Compat classificam a ação marcial; Simply Swords e addons conservam seus Implicits/progressões/traits; o RPG processa somente seu root canônico e seus próprios recursos.**

A0011–A0050 permanecem fora deste ciclo e deverão ser reavaliadas nos próximos lotes exatos de 10.