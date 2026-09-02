# A0109 — Fortaleza Ambulante

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED DUPLO.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0108 está indisponível e não existe provider aprovado de encumbrance corporal do jogador.  
**Notion:** https://app.notion.com/p/3c569db9f0db81a5bdcbdb7fc11c5b56

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY ↔ SURVIVAL/ENGINEERING.
- Ramo: Fortaleza Pesada.
- Camada: 6; função: Capstone.
- Ranks: 1; custo 3 PP.
- Pré-requisitos: A0108 Pele de Pedra + A0091 Base Firme ≥3 + Gateway VITALITY + provider real de encumbrance corporal com estados semanticamente mapeáveis a `HEAVY_LOAD`/`EXTREME_LOAD` + provider de regeneração de Stamina modificável.

## Contrato congelado

O contrato permanece congelado, porém inativo:

| Estado corporal provider-native | Redução física | Knockback Resistance | Regeneração natural de Stamina |
|---|---:|---:|---:|
| `HEAVY_LOAD` | +4% | +0,10 | −10% |
| `EXTREME_LOAD` | +8% | +0,20 | −20% |

A0109 **não define** thresholds de peso, inventário, armadura, quantidade de itens ou massa. Os estados precisam vir da semântica nativa de um futuro provider corporal aprovado e de adapter versionado explícito.

## Blockers atuais

1. A0108 está `UNAVAILABLE_NODE` por A0100; A0109 herda o bloqueio.
2. A modlist/runtime atual não possui provider aprovado de **encumbrance corporal do jogador**.
3. Epic Fight pode fornecer Stamina/regeneração somente depois que existir um estado corporal real; Stamina não cria `HEAVY_LOAD`.

Estado obrigatório hoje: **indisponível/não comprável, sem gasto de PP**.

## Provider, hook e authority futura

Quando existir provider real, um adapter server-authoritative normaliza apenas os estados nativos para `HEAVY_LOAD`/`EXTREME_LOAD`; então aplica atomicamente:

- contribuição física no `DamageMitigationResolver`;
- modifier de `minecraft:generic.knockback_resistance`;
- modifier da regeneração de Stamina provider-native.

A mudança de estágio corporal deve reconciliar os três efeitos na mesma transação lógica.

## O que NÃO é provider de encumbrance corporal

- Create Aeronautics/Weight e massa de contraption;
- massa/estado Sable ou veículo;
- quantidade de slots/itens no inventário;
- Armor/Toughness;
- velocidade do jogador;
- Protection Pixel `2.2.1`.

Nenhum desses sinais pode ser combinadoado para inventar `HEAVY_LOAD`/`EXTREME_LOAD`.

## Causalidade, deduplicação, lifecycle e anti-abuso

- um único estágio corporal ativo por jogador;
- transition entre estados remove/reconcilia valores antigos antes de aplicar novos;
- uma contribuição física e um modifier KB por ator/root conforme o pipeline;
- Stamina penalty não pode existir sem os benefícios correspondentes nem vice-versa;
- login/logout/dimensão/rank loss/respec/rules reload e perda do provider precisam convergir para estado seguro;
- provider ausente/incompatível remove disponibilidade, nunca sintetiza carga.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: ProgressionService governa predecessor/gateway; nenhum body-weight provider canônico existe.
- Volcanoes: pressão/equipamento/veículos não são encumbrance corporal.
- Enshrouded: Shroud/Exposure não são load state.
- Black Arcana: equipamento/resistance snapshots não são load state.
- Create/Sable physics mede contraptions, não o corpo do jogador.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS com dois blockers explícitos.
2. Integração global — PASS em design; não inventa provider.
3. Qualidade/identidade — PASS, fortaleza dependente de carga corporal real.
4. Topologia — PASS, terminal VITALITY↔SURVIVAL/ENGINEERING.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS; falsas fontes de peso explicitamente rejeitadas.

Authority, atomicidade, availability transitiva, lifecycle, dedup, fallback e anti-abuso estão congelados.

## Pendências para Chat 2

- `P-A0109-01`: implementar availability transitiva de A0108 e ausência de body-encumbrance provider; purchase deve falhar antes do gasto.
- `P-A0109-02`: não inferir carga por Weight/Create/Sable/inventário/Armor/velocidade/Protection Pixel.
- `P-A0109-03`: não implementar thresholds próprios. Somente preparar interface/consumer latente se continuar fail-closed e não inventar semântica externa.
- `P-A0109-04`: se surgir provider real durante o ciclo, registrar evidência de versão/API e devolver ao Chat 1 se a semântica exigir redesign.

## Testes exigidos ao Chat 3

No estado atual: A0108 unavailable + provider corporal ausente → A0109 unavailable sem gasto, todas as falsas fontes de encumbrance rejeitadas. Se adapter real existir futuramente: dois estados, transição atômica, exact modifiers, provider loss, respec/reload/multiplayer, GameTests, build, JAR e dedicated-server smoke.
