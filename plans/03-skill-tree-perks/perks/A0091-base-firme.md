# A0091 — Base Firme

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO FECHADO.
- **Implementação:** preparatória/parcial já existe na `main`, mas **não é IMPLEMENTAÇÃO CONFIRMADA**.
- **Notion:** `3c569db9-f0db-8123-a26b-d937658a3173`; fetch fresco no ciclo A0091–A0100.
- **Domínio:** VITALITY.
- **Árvore:** Principal — VITALITY.
- **Ramo:** Estabilidade Corporal.
- **Camada:** 1.
- **Função:** Ramo / fundamento exterior.
- **Ranks:** 5; custo 1 PP/rank.

## Contrato canônico

- Gateway VITALITY desbloqueado; sem classe obrigatória.
- Cada rank acrescenta **+0,03** a `minecraft:generic.knockback_resistance`, até **+0,15** no rank 5.
- Operação canônica: `ADD_FLAT` via `AttributeNodeEffectRuntime`, com `effectId` estável.
- O valor final continua sujeito ao contrato do atributo/provider; A0091 não concede imunidade total.
- A0091 não modifica `epicfight:impact`, `epicfight:stun_armor`, postura, stagger, resistência à interrupção, Armor ou Toughness.

## Provider, authority e pipeline

- **Owner do atributo:** Minecraft/NeoForge `Attributes.KNOCKBACK_RESISTANCE`.
- **Consumer:** RPG Skill Tree `AttributeNodeEffectRuntime`.
- Epic Fight 21.17.3.1 só é pertinente quando encaminhar a grandeza vanilla equivalente; seus atributos de Impact/Stun Armor não são substitutos.
- Pipeline único: `ProgressionState -> NodeEffectRuntime.refresh -> AttributeNodeEffectRuntime`.
- Rank loss/respec/reload deve remover/recompor o mesmo modifier, sem stacking ou drift.

## Integração com projetos próprios

- RPG Skill Tree: usa apenas o runtime canônico de node effects; não escreve attachment diretamente.
- Volcanoes: `NÃO APLICÁVEL`; pressão/atmosfera não são knockback resistance.
- Enshrouded: `NÃO APLICÁVEL`; Shroud/Exposure não são repulsão.
- Black Arcana: `NÃO APLICÁVEL`; Arcane/Corruption Resistance não substituem knockback resistance.

## Fallback / fail-closed

- Fallback semântico: atributo vanilla `minecraft:generic.knockback_resistance`.
- Se o target de atributo não puder ser resolvido, a contribuição do node falha fechado; não converter para Stun Armor, Armor, Toughness ou redução de dano.
- Deslocamentos especiais marcados como inevitáveis, teleporte, agarrões e movimentos roteirizados não são anulados por este node.

## Evidência atual e handoff para o Chat 2

- `data/rpgskilltree/node_effects/a0081_a0100.json` já contém A0091 com `ADD_FLAT`, `amountPerRank=0.03`.
- `A0081A0100CombatPolicy.knockbackResistanceDelta` também limita a contribuição própria a 0,15, mas a authority de efeito persistente deve continuar no node-effect runtime.
- O Chat 2 deve reconciliar aquisição/gateway e garantir que a fórmula auxiliar não crie segundo modifier/pipeline.

## Deduplicação e anti-abuso

- Um único `effectId` estável por A0091.
- Refresh idêntico é idempotente.
- Não há Mastery, recurso consumível ou producer temporal.
- Não gerar resistência por tick, hit ou evento; é efeito derivado exclusivamente do rank.

## Testes obrigatórios para o Chat 3

1. rank 0–5 produz 0 / 0,03 / 0,06 / 0,09 / 0,12 / 0,15 no modifier próprio;
2. refresh repetido não empilha;
3. rank loss/respec/remove elimina o modifier anterior;
4. reload válido recompõe sem drift; reload inválido preserva revisão anterior;
5. A0091 não altera `epicfight:stun_armor`, Impact, Armor ou Toughness;
6. gateway VITALITY e custo/rank bloqueiam aquisição indevida;
7. provider/attribute ausente falha fechado sem bônus substituto;
8. dedicated-server/GameTest confirma aplicação server-authoritative.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY obrigatório. |
| Integração global | PASS | Atributo vanilla, sem duplicar defesa Epic Fight. |
| Qualidade/identidade | PASS | Fundação clara de estabilidade corporal. |
| Topologia | PASS | Camada 1 VITALITY. |
| Especializações | PASS | `VITALITY/STABILITY`, sem classe obrigatória. |
| PT-BR | PASS | Nome/efeito em PT-BR. |
| Notion | PASS | Campos essenciais preenchidos; fetch fresco. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Minecraft/NeoForge owner; RPG consumer canônico. |

## Checklist de implementação

- [x] Design aprovado pelo Chat 1
- [ ] Hook/gateway reconciliado pelo Chat 2
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: testes unitários
- [ ] VALIDAÇÃO CHAT 3: GameTests/provider-present/absent
- [ ] VALIDAÇÃO CHAT 3: build NeoForge
- [ ] VALIDAÇÃO CHAT 3: dedicated-server smoke
- [ ] VALIDAÇÃO CHAT 3: CI GREEN
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
