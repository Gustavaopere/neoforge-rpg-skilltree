# AUDITORIA — A0071–A0080

## Registro do lote

- **INÍCIO:** A0071
- **FIM:** A0080
- **Quantidade:** 10 perks consecutivas.
- **Minecraft:** NeoForge 1.21.1
- **Java:** 21
- **Chat 1:** design/auditoria fechados e revalidados retroativamente em 2026-09-01.
- **Chat 2:** implementação concluída na branch `feat/chat2-a0071-a0080-implementation`.
- **Estado do lote:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Importante:** A0072, A0075, A0077 e A0080 permanecem corretamente indisponíveis/fail-closed por contrato; “código presente” não significa node utilizável.
- **Chat 2 não executou** bateria final de testes, GameTests, build NeoForge, dedicated-server smoke ou CI final e **não declara `IMPLEMENTAÇÃO CONFIRMADA`**.

## Fontes obrigatórias

Foram cruzados no fechamento do design e novamente na reauditoria retroativa:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolos permanentes Chat 1/Chat 2;
- Notion canônico A0071–A0080, 10/10;
- dossiês A0071–A0080;
- runtime A0061–A0080 e providers pertinentes;
- delta fresco dos projetos próprios registrado em `REAUDITORIA-CHAT1-A0071-A0080-2026-09-01.md`.

## Reauditoria retroativa 2026-09-01

A passagem retroativa confirmou que o design A0071–A0080 não precisa ser reaberto. As oito mutações históricas do Notion continuam persistidas e A0071/A0078 continuam sem drift funcional.

Mudanças externas detectadas depois do fechamento original:

- Volcanoes foi consolidado no próprio RPG Skill Tree; a integração Volcanoes→Cold Sweat projeta calor ambiental `WORLD` e **não** resolve o receipt metabólico causal de A0075;
- Create/Sable reforçam a necessidade de invalidar A0079 em transporte externo, sem alterar seu contrato;
- Enshrouded ganhou provider opcional Ars Zero Lich; isso é delta relevante para A0070/classificação de BOSS, não redesign de A0071–A0080;
- Black Arcana avançou em Arcane Danger/Resistance/Backlash e QA, sem transferir authority para as perks MARTIAL deste lote.

## Resumo perk por perk — fechamento Chat 2

| Código | Perk | Design | Estado após Chat 2 | Handoff principal para Chat 3 |
|---|---|---|---|---|
| A0071 | Dano contra Elites | APROVADA | **CÓDIGO PRESENTE** | provider-present Apothic + BOSS>ELITE + externos fail-closed |
| A0072 | Retaliação | APROVADA após availability | **CÓDIGO PRESENTE EM FAIL-CLOSED** | validar A0067→A0072 unavailable, refresh/dedup/exclusões |
| A0073 | Janela de Execução | APROVADA após reservation→commit | **CÓDIGO PRESENTE** | POST commit/rollback, concorrência, boss half-bonus, Stamina refund=0 sem receipt |
| A0074 | Primeiro Sangue | APROVADA após reservation→commit | **CÓDIGO PRESENTE** | opener/history/consume POST, bordas 85%/8s/4s/12s, concorrência |
| A0075 | Ritmo Sustentado | APROVADA EM FAIL-CLOSED | **CÓDIGO PRESENTE EM FAIL-CLOSED** | compra recusada, benefício parcial zero, thermal receipt ainda ausente |
| A0076 | Postura Agressiva | APROVADA após boundary | **CÓDIGO PRESENTE** | payload/authority/cooldown/exclusividade/cleanup/resistência física |
| A0077 | Postura Cautelosa | APROVADA após availability | **CÓDIGO PRESENTE EM FAIL-CLOSED** | A0067 unavailable + stance binding, nenhum resíduo |
| A0078 | Ataque em Movimento | APROVADA | **CÓDIGO PRESENTE** | sprint vanilla server-side, forced/passive movement, bridge PP |
| A0079 | Ataque Estacionário | APROVADA após hardening | **CÓDIGO PRESENTE** | 30 ticks/0,10, teleport/knockback/passenger/Create/Sable, provider mismatch |
| A0080 | Golpe de Oportunidade | APROVADA EM FAIL-CLOSED | **CÓDIGO PRESENTE EM FAIL-CLOSED** | node unavailable sem dodge-success receipt; consumer latente reservation→commit |

## Implementação transversal do Chat 2

### Availability / purchase fail-closed

`CombatPerkAvailabilityRuntime` introduz availability explícita para nodes cujo contrato não pode operar com segurança. No estado deste lote:

- A0067 — indisponível;
- A0072 — indisponível por dependência A0067;
- A0075 — indisponível por ausência do binding térmico causal obrigatório;
- A0077 — indisponível por A0067;
- A0080 — indisponível por ausência de dodge-success receipt.

`effectiveRanks` mascara ranks persistidos sem apagá-los. `NodePurchaseRequestProcessor` e o overload server-authoritative de `PlayerProgressionRuntime.purchaseNode(...)` recusam nodes indisponíveis antes da mutação de pontos.

`NodePurchaseResult.Status.UNAVAILABLE_NODE` fornece resultado explícito em vez de silent no-op purchase.

### Lifecycle / reconciliation

`A0061A0080RuntimeState` usa ranks efetivos e limpa estado transitório quando a snapshot de ranks efetivos muda. Logout, respawn, dimensão, morte/server-stop e targets removidos/dead recebem cleanup pelas bridges pertinentes.

### A0073/A0074/A0080 — causalidade

`A0061A0080CombatState` passou a modelar reservations e commits separados:

- execution opener/finisher;
- first-blood opener/finisher;
- opportunity consumer.

PRE mantém somente informação reversível/reservada; POST positivo confirma transições. Cancelamento/zero executa rollback. Pending hit possui retenção bounded de 1 s para impedir vazamento quando um PRE não completa a cadeia.

Epic Fight usa `PendingHit` por source/target como reserva de root. Projectile físico reutiliza o PRE canônico existente e `A0073A0080ProjectileCommitEvents` como boundary de commit/rollback pós-dano. A estratégia é conservadora sob impactos simultâneos: não deve produzir bônus duplicado; cenário de concorrência precisa ser exercitado pelo Chat 3.

### A0076/A0077 — MARTIAL_STANCE

Foram implementados:

- `MartialStanceIntentPayload` serverbound;
- key mapping de `Alternar Postura Marcial`;
- registro de payload no `ModNetworking`;
- `MartialStanceRuntime` server-authoritative;
- ciclo/availability/cooldown/cleanup;
- aplicação de dano físico de saída e resistência física de entrada no resolver físico, sem mapear para Armor/Stun Armor/resistências mágicas.

A0077 continua não ativável porque A0067 permanece indisponível.

### A0079 — forced movement

Foram adicionados gates exatos e adapters isolados:

- Create 6.0.10 — belt ativo via `BeltBlockEntity`;
- Sable 2.0.5 — sublevel/containing via `Sable.HELPER`;
- passenger, teleport e knockback invalidam stationarity;
- version mismatch/linkage failure falham fechado;
- fallback sem Epic Fight não duplica o sampler existente de A0081–A0100.

## Revisão estática adicional antes do handoff

- `NodePurchaseResult` contém o novo enum no único switch exaustivo conhecido do tipo; não foi encontrado outro switch exaustivo em `Status` que precise de novo case.
- `ModNetworking` foi versionado de `4` para `5` juntamente com o novo payload de stance; não foi encontrado outro hardcode de versão de rede no código indexado.
- `scripts/verify-a0061-a0080-runtime.py` ainda valida invariantes estruturais históricas; o Chat 3 deve executá-lo/ajustá-lo somente se a validação real demonstrar que alguma expectativa textual ficou obsoleta. Chat 2 não usa o script como substituto de build/teste.
- O keybind funciona com literal PT-BR; normalização para chave `lang` é melhoria de apresentação que o Chat 3 pode aplicar sem mudar semântica.

## Matriz dos nove eixos

| Eixo | Resultado do lote | Evidência |
|---|---|---|
| 1. Dependências/gates | ✅ no contrato/código | availability transitiva e effective ranks implementados. |
| 2. Integrações globais | ✅ | Stamina, Cold Sweat, exhaustion, physical resistance, movement e dodge preservam authority. |
| 3. Qualidade/identidade | ✅ | nenhum fallback genérico substitui a identidade das perks. |
| 4. Topologia | ✅ | contratos de camada/bridge permanecem os aprovados. |
| 5. Especializações | ✅ | PP regions/bridge policy não foram redesenhadas. |
| 6. PT-BR | ✅ funcional | conteúdo player-facing preservado; keybind pode receber chave `lang` no Chat 3. |
| 7. Notion | ✅ | 10/10 re-fetched na reauditoria; nenhuma nova mutação necessária. |
| 8. NeoVitae | ✅ | ausente. |
| 9. Providers | ✅ no desenho técnico | gates exatos/fail-closed; validação provider-present pertence ao Chat 3. |

## Checklist técnica consolidada — estado Chat 2

1. Hooks reais ou fail-closed/unavailable explícito — implementado.
2. Provider-native first — preservado.
3. Nenhuma mecânica de provider inventada — preservado.
4. Fail-closed — A0072/A0075/A0077/A0080 e extensões não provadas.
5. Fallbacks preservam identidade — sim.
6. Sem Mastery por tick/spam neste lote — sim.
7. Dedup/root/action — state e reservations presentes; concorrência final para Chat 3.
8. Autoria jogador real/source direta — preservada nos bridges.
9. Pipelines canônicos — sem duplicar Stamina/Impact/stance/stationary.
10. Sem custo/recurso fictício — sim.
11. Refund A0073 sem receipt = 0 — implementado por ausência deliberada de refund.
12. Cliente envia intenção de stance, servidor é authority — implementado.
13. Versões sensíveis — Epic Fight 21.17.3.1, Cold Sweat 2.4.2, Create 6.0.10, Sable 2.0.5; ParCool/Epic ParCool permanecem sem extensão inventada.
14. Ranks/custos/camadas — não alterados.
15. Availability transitiva — implementada nos nodes bloqueados deste lote.
16. BOSS > ELITE / stance exclusiva / movement ≠ dodge — preservado.
17. Dossiês — 10/10 atualizados para handoff Chat 3.
18. Notion — nenhuma nova mutação necessária após reauditoria.

## Pendências obrigatórias para Chat 3

- criar/completar testes explícitos de reservation→commit/rollback de A0073/A0074/A0080;
- validar concorrência de roots/projéteis e ausência de duplicação;
- validar unavailable-node purchase pelos caminhos server-authoritative;
- validar provider-present/absent/mismatch de Create/Sable e Apothic;
- validar stance multiplayer/spoof/spam/cooldown/cleanup;
- validar stationarity 30 ticks/0,10 e todos os forced movements;
- executar unit tests, GameTests pertinentes, build NeoForge, dedicated-server smoke e CI aplicáveis;
- corrigir somente falhas técnicas que não exijam redesign;
- só então declarar `IMPLEMENTAÇÃO CONFIRMADA`, obter CI GREEN, fazer merge e confirmar `main`.

## Fechamento Chat 2

**A0071–A0080: CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3.**

A0072/A0075/A0077/A0080 continuam intencionalmente fail-closed. O Chat 2 para neste lote e não inicia A0081+.
