# AUDITORIA — A0061–A0070

## Registro do lote

- **INÍCIO:** A0061
- **FIM:** A0070
- **Responsabilidade:** Chat 1 — auditoria, design e integração; nenhuma implementação de gameplay foi criada neste ciclo.
- **Minecraft:** NeoForge 1.21.1
- **Java:** 21
- **Estado inicial:** A0001–A0060 já formalmente fechadas no `STATUS.md`; A0061–A0070 ainda sem auditoria/dossiês formais.
- **Resultado:** **LOTE FECHADO NO DESIGN**, com A0067 aprovada em fail-closed estrutural e A0070 aprovada com cobertura parcial de adapters explicitamente fail-closed.

## Fontes obrigatórias lidas/cruzadas

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` consolidado.
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md` consolidado, incluindo Epic Fight, Apothic/Pufferfish, Simply Swords e Mobstein.
- `GUIA-COMPLETO-MODS-DE-MAGIA.md` consolidado.
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md` consolidado.
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md` consolidado.
- Notion canônico: 10 páginas A0061–A0070 buscadas individualmente.
- Código/runtime atual do RPG Skill Tree e deltas frescos dos quatro projetos próprios.

## Gate de delta dos projetos próprios

Baselines anteriores e SHAs frescos foram comparados antes da primeira perk:

| Projeto | Baseline anterior | `main` fresco auditado | Resultado do delta |
|---|---|---|---|
| RPG Skill Tree | `f448aa0b4f9df400011873e9ad26771209876ad4` | `6ed628864199e74af23e6234d126959829f3c968` | runtime A0061–A0080 e projectile bridge cobertos pelo lote/sistema universal; avanços paralelos não viram provider artificial. |
| Volcanoes | `602e0188c123ac8531d3413a5630daa22e3d761f` | `a47bb868de9b4846d8ae9afb94374f9672ab381e` | RNS/hidrotermal permanece provider-native; hardening não é perk MARTIAL. |
| Enshrouded | `77552a3d7f089a47908c109f5f8c19aff8a0f97d` | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` | Stage 06/Lich avançou; `enshrouded:shroud_lich` cria bridge read-only legítima para A0070. Story/reward/ritual permanecem Enshrouded-owned. |
| Black Arcana | `07263ae9bad12eba6ed500992991faa36ad598b2` | `526d8196087c863e9df64051d5d39d88c3050856` | hardening Arcane Danger permanece progressão nativa; Backlash/hazard arcano não é dano físico direto MARTIAL. |

A matriz completa e os novos baselines estão em `guides/projects/12-capability-delta-coverage.md`.

## Resumo perk por perk

| Código | Nome | Design final | Runtime observado | Decisão principal / pendência Chat 2 |
|---|---|---|---|---|
| A0061 | Força Aplicada | **APROVADA** | código presente melee + projectile | validar deduplicação/root e Simply Swords provider-native. |
| A0062 | Golpe Preciso | **APROVADA** | código presente no crítico canônico | provar uma única rolagem entre NeoForge/Epic Fight/projectile. |
| A0063 | Impacto Crítico | **APROVADA** | código presente sobre critical result | provar uma única aplicação do crit damage. |
| A0064 | Ritmo de Combate | **APROVADA** | código presente em `ModifyAttackSpeedEvent` | provider-present; sem fallback de animação. |
| A0065 | Penetração Física | **APROVADA** | código presente Epic Fight + projectile | não duplicar armor negation/pierce/Simply Swords armor ignore. |
| A0066 | Impacto Marcial | **APROVADA** | melee Epic Fight presente; projectile fail-closed | preservar ausência de Impact onde o provider não expõe receipt. |
| A0067 | Firmeza Ofensiva | **APROVADA EM FAIL-CLOSED** após correção | matemática existe, binding seguro ausente | **P-A0067-01:** node deve ser indisponível/não comprável até existir lifetime ofensivo seguro. |
| A0068 | Dano contra Feridos | **APROVADA** | código presente | snapshot pré-impacto <35%; sem retroatividade. |
| A0069 | Dano contra Íntegros | **APROVADA** | código presente | snapshot pré-impacto >85%; dano anterior real remove elegibilidade. |
| A0070 | Dano contra Chefes | **APROVADA** após correção | tag vanilla/Cataclysm + Apothic presentes; Enshrouded ainda precisa adapter | **P-A0070-01:** exact `enshrouded:shroud_lich`; demais mods ficam fail-closed até IDs reais. |

## Correções feitas no Notion

### A0067 — Firmeza Ofensiva

Problema encontrado: o texto anterior aceitava semanticamente STUN_ARMOR como backend transitório, mas o adapter real A0061–A0080 declara que a versão auditada não prova uma offensive stun-armor window segura. Isso criava risco de node comprável sem efeito.

Correção persistida e re-fetched:

- `Gate`: binding server-authoritative da attack window tornou-se requisito de disponibilidade.
- `Hook`: estado atual marcado explicitamente `FAIL-CLOSED`.
- `Fallback`: node **indisponível/não comprável**, sem silent no-op purchase.
- `Provider/Mods`: STUN_ARMOR rebaixado a backend candidato, não contrato já disponível.
- `Regra`: lifecycle/cleanup futuro obrigatório e proibição de super armor global.

### A0070 — Dano contra Chefes

Problema encontrado: a página listava vários providers externos como se já estivessem classificados, enquanto o runtime atual só provava tag vanilla/Cataclysm + markers Apothic; Enshrouded já possuía registry identity verificável nova.

Correção persistida e re-fetched:

- cobertura comprovada separada de candidatos ainda não provados;
- exact identity `enshrouded:shroud_lich` registrada como bridge read-only;
- Mowzie's Mobs, Legendary Monsters, Born in Chaos e Mobstein 5.4.4 marcados fail-closed até registry IDs/adapters exatos;
- bossbar/nome/tamanho/max health/estrutura explicitamente proibidos como heurística;
- authority de Story/fase/reward/ritual do Enshrouded preservada.

## Matriz dos nove eixos do lote

| Critério | Status do lote | Evidência / decisão |
|---|---|---|
| 1. Dependências e bloqueios | ✅ | Gates A0061–A0070 revisados; A0067 ganhou availability gate obrigatório. |
| 2. Integrações globais/modlist/corpo/recursos | ✅ | Dano/crítico/ritmo/penetração/Impact não criam recursos paralelos; Black Arcana/Volcanoes não são convertidos artificialmente em MARTIAL. |
| 3. Qualidade/identidade | ✅ | Foundations pequenos onde apropriado; condicionais de execução/abertura/boss possuem identidade distinta. |
| 4. Ramificação/distância/topologia | ✅ | A0061/A0062/A0064 camada 1; A0063/A0065/A0066/A0068/A0069/A0070 camada 2; A0067 camada 3; regiões PP semanticamente registradas. |
| 5. Especializações | ✅ | Nodes permanecem MARTIAL universais; provider não vira classe; Specialists só contam regiões explicitamente mapeadas. |
| 6. PT-BR | ✅ | Nomes, efeitos e requisitos player-facing em PT-BR; IDs/API permanecem técnicos. |
| 7. Notion completo | ✅ | 10 páginas fetched; A0067/A0070 corrigidas e re-fetched. |
| 8. NeoVitae removido | ✅ | Nenhuma dependência residual no lote. |
| 9. Cobertura modlist/providers | ✅ | Epic Fight, WoM, Apothic, Pufferfish, Simply Swords, Mobstein e quatro projetos próprios foram classificados; ausência de hook/ID gera fail-closed. |

## Checklist técnica consolidada — 18 critérios

1. Efeitos correspondem a hooks/grandezas reais ou ficam fail-closed.
2. Provider-native first preservado.
3. Nenhuma mecânica do provider foi inventada.
4. Fail-closed explícito em A0066 projectile, A0067 e cobertura externa A0070.
5. Fallbacks preservam identidade.
6. Nenhuma destas perks gera Mastery por tick/spam.
7. Não há farm/rebuild aplicável; deduplicação é por root action/evento.
8. Autoria causal exige jogador real e dano direto.
9. Crítico/dano/penetração/Impact usam pipelines canônicos sem segunda resolução da mesma contribuição.
10. Nenhum recurso/custo fictício foi criado.
11. Nenhuma geração gratuita de recurso/output/mastery.
12. Classificação/read-only de boss permanece read-only.
13. Versões sensíveis registradas: Epic Fight 21.17.3.1, Apothic Attributes 2.10.1, Simply Swords 1.70.2, WoM 2.0.176 e demais providers conforme modlist reconciliada.
14. Função/camada/ranks/custos coerentes.
15. Dependências semanticamente corretas.
16. Sem sobreposição indevida entre ramos/providers.
17. Dossiês especificam Hook/Gate/Fallback/Regra para implementação sem redesign.
18. Alterações Notion A0067/A0070 foram verificadas por re-fetch.

**Resultado:** 18/18 satisfeitos no design, com pendências técnicas explicitamente fail-closed quando o binding real ainda não existe.

## Regras que o Chat 2 deve respeitar

- A0067 não pode consumir ponto enquanto indisponível.
- A0070 não pode promover boss externo por heurística; adapter deve usar exact registry identity/tag/marker provado.
- `enshrouded:shroud_lich` é leitura de identidade apenas; não tocar Story/reward/fase/arena/ritual.
- A0066 não ganha Impact sintético em projectile.
- A0062/A0063 compartilham uma única resolução crítica; nunca segunda rolagem/evento.
- A0065 não transforma armor pierce/negation, armor ignore provider-native e shred em uma única grandeza indistinta.
- Simply Swords Implicits/Awakening/Runic Powers continuam provider-native e não são reemitidos.

## Fechamento do Chat 1

A0061–A0070 estão suficientemente especificadas para o Chat 2 implementar/corrigir **sem redesenhar**. O lote só será declarado mergeado após PR, CI verde e confirmação da `main`; nenhuma perk A0071+ deve ser iniciada neste ciclo.