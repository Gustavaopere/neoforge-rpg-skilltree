# A0095 — Tenacidade

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO CORRIGIDO.
- **Notion:** `3c569db9-f0db-8171-8724-d67458cc1603`; fetch fresco no ciclo.
- **Domínio:** VITALITY; Camada 2; Ramo Estabilidade Corporal.
- **Ranks:** 5; custo 1 PP/rank.
- **Dependência canônica:** **A0091 Base Firme ≥2**. A dependência antiga de A0094 foi removida por ser semanticamente errada e transitivamente indisponível.

## Contrato canônico

- +0,25 de `epicfight:stun_armor` flat por rank: +0,25 / +0,50 / +0,75 / +1,00 / +1,25.
- A redução de stun/interrupção resultante continua sendo calculada pela fórmula nativa do Epic Fight; A0095 não cria percentual próprio.
- Operação: `ADD_FLAT` via `AttributeNodeEffectRuntime`, `effectId` estável.

## Provider / authority

- **Epic Fight 21.17.3.1**: owner do atributo Stun Armor e da sua semântica/fórmula.
- RPG Skill Tree: consumer que adiciona modifier data-driven.
- Não confundir com `minecraft:knockback_resistance`, `epicfight:impact`, Armor, Toughness, guard ou redução de dano.
- Evidência upstream/pack confirma Stun Armor como atributo defensivo real; o design usa a grandeza nativa, não uma aproximação.

## Fallback / availability

- Epic Fight ausente, versão incompatível ou atributo não resolvido => **UNAVAILABLE_NODE**; sem compra/rank/gasto.
- Não substituir por knockback resistance ou multiplicador genérico de interrupção.

## Divergências concretas do código preparatório — handoff Chat 2

- `NotionCombatPerkCatalog` ainda declara dependências `A0091≥2` **e `A0094≥1`**: está desatualizado.
- `A0081A0100NotionContractTest` também espera a dependência antiga.
- `A0081A0100CombatPolicy.interruptionMultiplier` implementa semântica antiga de −3% por rank e precisa deixar de ser authority para A0095.
- `A0081A0100CombatEvents` marca A0095 fail-closed inteiro, embora o atributo provider-native exista; o design atualizado exige node-effect real quando Epic Fight 21.17.3.1 estiver presente.
- `a0081_a0100.json` ainda não contém o modifier `epicfight:stun_armor`.

### Pendências Chat 2

- **P-A0095-01:** reconciliar catálogo/tree/testes para dependência única A0091≥2.
- **P-A0095-02:** adicionar node effect `epicfight:stun_armor`, `ADD_FLAT`, 0,25/rank.
- **P-A0095-03:** remover/desautorizar reducer genérico `interruptionMultiplier` e o fail-closed global antigo; availability deve depender do provider/atributo real.

## Dedup / lifecycle

- Um `effectId` estável.
- Refresh idempotente, remoção correta em rank loss/respec/reload/attribute disappearance.
- Nenhum efeito por tick/hit; nenhum Mastery.

## Testes obrigatórios Chat 3

1. dependência A0095 = apenas A0091≥2 + VITALITY; A0094 não bloqueia;
2. ranks 1–5 produzem +0,25…+1,25 Stun Armor;
3. provider 21.17.3.1 presente: modifier aplicado uma vez;
4. provider ausente/incompatível: node não comprável e nenhum gasto;
5. refresh/respec/reload não empilham nem deixam modifier órfão;
6. A0095 não altera knockback, Impact, Armor/Toughness, dano ou guard stamina;
7. regressão prova que o antigo −3% `interruptionMultiplier` não é usado como segundo pipeline;
8. dedicated-server/GameTest provider-present/absent.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS após correção | A0091≥2; A0094 removida. |
| Integração global | PASS | Stun Armor nativo, sem reducers paralelos. |
| Qualidade/identidade | PASS | Tenacidade contra stun via stat real. |
| Topologia | PASS | Continuação natural de Base Firme. |
| Especializações | PASS | VITALITY/STABILITY. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Contrato corrigido/fresco. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Epic Fight atributo real + RPG node effects. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0095-01 catálogo/dependência corrigidos pelo Chat 2
- [ ] P-A0095-02 node effect Stun Armor implementado
- [ ] P-A0095-03 pipeline antigo removido/reconciliado
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/provider/GameTests
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
