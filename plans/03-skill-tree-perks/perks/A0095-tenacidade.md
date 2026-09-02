# A0095 — Tenacidade

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO CORRIGIDO.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-8171-8724-d67458cc1603`; fetch fresco no ciclo.
- **Domínio:** VITALITY; Camada 2; Ramo Estabilidade Corporal.
- **Ranks:** 5; custo 1 PP/rank.
- **Dependência canônica:** **A0091 Base Firme ≥2**. A dependência antiga de A0094 foi removida.

## Contrato canônico

- +0,25 de `epicfight:stun_armor` flat por rank: +0,25 / +0,50 / +0,75 / +1,00 / +1,25.
- A redução de stun/interrupção resultante continua sendo calculada pela fórmula nativa do Epic Fight; A0095 não cria percentual próprio.
- Operação: `ADD_FLAT` via `AttributeNodeEffectRuntime`, `effectId` estável.

## Provider / authority

- **Epic Fight 21.17.3.1**: owner do atributo Stun Armor e da sua semântica/fórmula.
- RPG Skill Tree: consumer que adiciona modifier data-driven.
- Não confundir com `minecraft:knockback_resistance`, `epicfight:impact`, Armor, Toughness, guard ou redução de dano.
- O design usa a grandeza nativa do provider, não uma aproximação.

## Fallback / availability

- Epic Fight ausente, versão incompatível ou atributo não resolvido => **UNAVAILABLE_NODE**; sem efeito/rank efetivo.
- Não substituir por knockback resistance ou multiplicador genérico de interrupção.
- A prova de rejeição de compra sem gasto em todos os cenários fica para o Chat 3.

## Evidência após Chat 2

- O catálogo/tree e o contrato estrutural foram reconciliados para dependência única **A0091≥2**; A0094 não é mais requisito.
- `a0081_a0100.json` contém o binding provider-native `epicfight:stun_armor`, `ADD_FLAT`, `amountPerRank=0.25`, com `effectId` estável.
- O antigo pipeline genérico `interruptionMultiplier` deixou de ser authority de A0095; busca estática final não encontrou referência stale.
- O gate de availability depende do provider/atributo nativo, preservando fail-closed quando o contrato não puder ser resolvido.
- O teste estrutural stale que ainda esperava A0094 foi corrigido no commit `aa793f562470145168d5cd964b49c03e806d0204`.
- O teste de policy stale foi reconciliado no commit `cbe8de4c59983512fdc3d44f9155669c40b3d2a1`.
- O Chat 2 **não executou** unit tests, GameTests, build NeoForge, dedicated-server smoke ou CI.

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
- [x] Catálogo/dependência corrigidos pelo Chat 2
- [x] Node effect Stun Armor implementado
- [x] Pipeline antigo removido/reconciliado
- [x] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/provider/GameTests
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
