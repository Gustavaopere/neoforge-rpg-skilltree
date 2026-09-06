# AUDITORIA — A0071–A0080

## Registro do lote

- **INÍCIO:** A0071
- **FIM:** A0080
- **Quantidade:** 10 perks consecutivas.
- **Responsabilidade:** Chat 1 — auditoria, design e integração; nenhum gameplay/runtime foi implementado neste ciclo.
- **Minecraft:** NeoForge 1.21.1
- **Java:** 21
- **Base RPG auditada:** `main@4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a`.
- **Resultado:** **LOTE FECHADO NO DESIGN**, com defects runtime e nodes indisponíveis explicitamente catalogados para Chat 2.

## Fontes obrigatórias

Foram relidos/cruzados integralmente neste ciclo:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo permanente do Chat 1;
- Notion canônico A0071–A0080, 10/10 páginas individualmente;
- runtime A0061–A0080 e providers pertinentes.

## Gate de delta dos projetos próprios

Baselines do lote anterior comparados contra `main` fresco antes da primeira perk:

| Projeto | Baseline anterior | `main` fresco | Disposição |
|---|---|---|---|
| RPG Skill Tree | `6ed628864199e74af23e6234d126959829f3c968` | `4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a` | **NÃO DEVE SER INTEGRADO / SEM DELTA JOGÁVEL**: diferença é exclusivamente PR #298 de documentação/auditoria A0061–A0070; nenhuma capacidade gameplay nova. |
| Volcanoes | `a47bb868de9b4846d8ae9afb94374f9672ab381e` | `bbb273d61984e2c9bb84e8f8a56668ae7e315532` | **NÃO DEVE SER INTEGRADO**: único delta é hardening de proveniência/licenças/third-party e documentação, sem capacidade jogável nova. |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` | mesmo SHA | **SEM DELTA**. |
| Black Arcana | `526d8196087c863e9df64051d5d39d88c3050856` | mesmo SHA | **SEM DELTA**. |

Nenhum delta exige uma 11ª perk neste lote. Novos baselines operacionais após esta disposição: RPG `4cde1cf...`, Volcanoes `bbb273d...`, Enshrouded `391ea82...`, Black Arcana `526d819...`.

## Resumo perk por perk

| Código | Perk | Design | Runtime observado | Handoff principal |
|---|---|---|---|---|
| A0071 | Dano contra Elites | **APROVADA** | classificador BOSS/ELITE presente | testar Apothic e BOSS > ELITE; externos sem ID ficam fail-closed |
| A0072 | Retaliação | **APROVADA após availability** | hook POST correto, mas cadeia não adquirível | A0067 indisponível → A0072 indisponível/não comprável |
| A0073 | Janela de Execução | **APROVADA após reservation→commit** | PRE consome/arma cedo demais | mover arm/consume/cooldown para POST confirmado; Stamina refund por receipt |
| A0074 | Primeiro Sangue | **APROVADA após reservation→commit** | PRE atualiza/consome cedo demais | last-attack/opener/consume/cooldown somente por commit confirmado |
| A0075 | Ritmo Sustentado | **APROVADA EM FAIL-CLOSED** | matemática existe; adapter não prova tradeoff térmico | node indisponível/não comprável até STAMINA_REGEN + Cold Sweat thermal + exhaustion estarem todos operacionais |
| A0076 | Postura Agressiva | **APROVADA após boundary de ativação** | slot/cooldown existem, caller/input não | implementar keybind remapeável + payload serverbound + availability |
| A0077 | Postura Cautelosa | **APROVADA após availability** | slot puro existe | herda bloqueio A0067 e binding de stance A0076 |
| A0078 | Ataque em Movimento | **APROVADA** | sprint vanilla server-side presente | ParCool extra só por receipt real; excluir forced/passive movement |
| A0079 | Ataque Estacionário | **APROVADA após hardening** | detector presente, invalidation incompleta | propagar forced transitions para mount/vehicle/contraption/belt/forced movement |
| A0080 | Golpe de Oportunidade | **APROVADA EM FAIL-CLOSED** | policy pura existe; no dodge-success producer | indisponível até receipt de ataque realmente evitado; consumo também reservation→commit |

## Correções no Notion

### A0072

Availability de A0067 propagada explicitamente. Fallback não pode liberar A0072 quando a dependência obrigatória é indisponível.

### A0073 e A0074

Ambas foram convertidas em contratos explícitos de **reservation→commit**:

- PRE prepara/reserva e pode aplicar multiplicador reversível;
- POST com dano direto hostil efetivo >0 commita arm/consume/cooldown;
- cancelamento ou dano zero faz rollback;
- estados por alvo exigem cleanup bounded e reconciliação de rank/respec/rules reload.

### A0075

O contrato é all-or-nothing. `STAMINA_REGEN` provider-native existe, mas o adapter atual não prova a contribuição térmica metabólica causal do Cold Sweat exigida pelo próprio design. A perk agora é explicitamente indisponível/não comprável até os três bindings obrigatórios existirem.

### A0076 / A0077

Foi fechado o boundary de ativação que faltava: RPG Skill Tree possui um slot `MARTIAL_STANCE` e deve possuir um controle remapeável `Alternar Postura Marcial` que envie intenção por payload serverbound. O servidor é authority de ranks, availability, cooldown e transição atômica. A0076 fica indisponível até o binding existir; A0077 também herda A0067 indisponível.

### A0079

O Notion agora exige propagação explícita de forced transitions para o `StationaryStateService`; amostrar sempre com `forcedTransition=false` não satisfaz o contrato para mount/vehicle/contraption/belt/forced movement.

### A0080

Availability endurecida: dodge executado não é dodge-success. O node só pode ser adquirido quando um receipt server-authoritative provar `dodgeActionId/avoidedAttackId` de ataque hostil efetivamente evitado. Epic Fight/ParCool/Epic ParCool devem deduplicar pelo mesmo ataque evitado. O golpe consumidor também usa reservation→POST commit.

**Notion alterado:** 8/10 — A0072, A0073, A0074, A0075, A0076, A0077, A0079, A0080.

**Sem mutação funcional:** A0071, A0078.

**Re-fetch pós-escrita:** 8/8 PASS em 2026-08-31.

## Matriz dos nove eixos

| Eixo | Resultado do lote | Evidência |
|---|---|---|
| 1. Dependências/gates | ✅ | availability transitiva A0067→A0072/A0077; bridges AGILITY/VITALITY sem border hopping; bindings técnicos tornam nodes indisponíveis quando ausentes. |
| 2. Integrações globais | ✅ | Stamina, Cold Sweat, exhaustion, thirst, physical resistance, movement e dodge permanecem eixos/provider authorities distintos. |
| 3. Qualidade/identidade | ✅ | elite, retaliation, execution, opening, sustain, stances, movement, stationary e dodge-success possuem identidades separadas. |
| 4. Topologia | ✅ | A0071/A0072/A0078/A0079 camada 2; A0073–A0077/A0080 camada 3; bridges têm PP policy explícita. |
| 5. Especializações | ✅ | nenhuma perk cria classe; PP_REGION/bridge policy preservadas. |
| 6. PT-BR | ✅ | nomes, efeitos e controle `Alternar Postura Marcial` em PT-BR; IDs/API permanecem técnicos. |
| 7. Notion | ✅ | 10/10 fetched; 8 páginas corrigidas e re-fetched. |
| 8. NeoVitae | ✅ | ausente. |
| 9. Providers | ✅ | Epic Fight, ParCool/Epic ParCool, Cold Sweat, Simply Swords, Apothic e projetos próprios classificados; ausência de receipt/binding falha fechado. |

## Checklist técnica consolidada — 18 critérios

1. Hooks reais ou fail-closed/unavailable explícito.
2. Provider-native first preservado.
3. Nenhuma mecânica de provider inventada.
4. Fail-closed explícito em A0072/A0075/A0076/A0077/A0080 e extensões não provadas A0071/A0078.
5. Fallbacks preservam identidade.
6. Nenhuma Mastery por tick/spam neste lote.
7. Dedup por root/action/avoidedAttack/evento causal onde aplicável.
8. Autoria exige jogador real e source direta.
9. Pipelines canônicos: target class, physical damage, Impact, Stamina, stance, movement e stationary não são duplicados.
10. Sem custo/recurso fictício.
11. Sem geração gratuita de Stamina/recursos: refund A0073 exige receipt pós-consumo.
12. Bridges/read-only permanecem read-only; client input é intenção, não authority.
13. Versões sensíveis registradas: Epic Fight 21.17.3.1, ParCool 4.0.0.3, Epic ParCool 21.0.0, Cold Sweat 2.4.2, Simply Swords 1.70.2.
14. Ranks/custos/camadas coerentes com catálogo/testes estruturais.
15. Dependências semanticamente coerentes e availability transitiva.
16. Sem sobreposição indevida: BOSS > ELITE; stance exclusiva; dodge/movement distintos; thirst ≠ exhaustion.
17. Dossiês fecham Hook/Gate/Fallback/Regra para Chat 2 implementar sem redesign.
18. 8 mutações Notion verificadas por re-fetch.

**Resultado:** 18/18 satisfeitos no design.

## Handoffs bloqueantes para Chat 2

1. `P-A0072-01` — unavailable A0067 → A0072.
2. `P-A0073-01` — reservation→POST commit para arm/consume/cooldown; `P-A0073-02` Stamina receipt; lifecycle/dedup.
3. `P-A0074-01` — reservation→POST commit para last-attack/opener/consume/cooldown; lifecycle.
4. `P-A0075-01` — unavailable-node invariant; `P-A0075-02` Cold Sweat metabolic boundary; all-or-nothing providers.
5. `P-A0076-01` — control/payload stance + availability; atomic transition/lifecycle.
6. `P-A0077-01` — availability A0067 + stance binding.
7. `P-A0079-01` — forced-transition receipts/invalidation.
8. `P-A0080-01` — unavailable até dodge-success receipt; `P-A0080-02` dedup provider; `P-A0080-03` reservation→commit do hit consumidor.
9. Testes transversais para A0071/A0078 e bridges PP.

## Fechamento

A0071–A0080 estão suficientemente especificadas para o Chat 2 implementar/corrigir sem redesign. Nenhuma perk A0081+ foi iniciada neste ciclo. O lote só é operacionalmente encerrado após PR, CI verde, merge e confirmação da `main`.