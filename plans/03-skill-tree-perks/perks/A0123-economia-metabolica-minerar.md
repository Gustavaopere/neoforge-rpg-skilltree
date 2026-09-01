# A0123 — Economia Metabólica: Minerar

**Estado Chat 1:** DESIGN APROVADO APÓS HARDENING DE AVAILABILITY.  
**Runtime atual:** `UNAVAILABLE_NODE`; `BodyCostResolver`/P-0037 não existe na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db8183b868e4e930a12e27

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Esforço Profissional — Mineração; camada 2; função: Ponte.
- 4 ranks; 1 PP/rank.
- Gate: Gateway SURVIVAL + corredor MINING real + `BodyCostResolver` METABOLIC disponível.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, a parcela positiva de `FoodData` exhaustion realmente atribuída à quebra manual classificada como MINING. O teto agregado de economia METABOLIC é **30% por evento**.

A perk economiza custo corporal; não é reward por bloco. Portanto um bloco recolocado não é automaticamente inelegível se a quebra manual real gerar o mesmo custo corporal. Em contraste, Create/Oritech/máquina/fake player sem débito corporal do jogador são inelegíveis.

## Pipeline e causalidade

`manual break action_id -> classificação MINING -> quote/receipt METABOLIC positivo da mesma ação -> agregar eficiências -> cap 30% -> commit FoodData/provider uma vez`.

Usar o custo real observado/cotado. É proibido hard-codear um valor de exhaustion ou fabricar custo a partir de tempo minerando, número de blocos, dureza ou desgaste da ferramenta.

Vein mining/tree-felling/bulk break pode classificar a raiz, mas blocos derivados não geram novos receipts sem débito corporal próprio comprovado.

## Availability / fail-closed

Sem P-0037/`BodyCostResolver` com boundary seguro, A0123 é não comprável; compra falha antes do gasto; allocation legado vale 0 PP e permanece reembolsável/migrável. Refund posterior de hunger não substitui o binding.

Quando o resolver global existir, uma quebra específica sem receipt causal apenas não recebe o benefício.

## Projetos próprios

- RPG Skill Tree: owner futuro do resolver/availability.
- Volcanoes: mineração geológica/prospecção não cria automaticamente custo corporal; somente a quebra manual com receipt real entra.
- Enshrouded/Black Arcana: sem relação provider legítima.

## Pendências para Chat 2

- `P-A0123-01` — implementar `UNAVAILABLE_NODE` enquanto P-0037/BodyCostResolver não existir.
- `P-A0123-02` — boundary causal de quebra manual e FoodData quote/commit; sem seam seguro, manter fail-closed.
- `P-A0123-03` — classificador MINING explícito, dedup root/bulk e exclusão de automação/fake player.
- `P-A0123-04` — composição METABOLIC sob cap compartilhado 30%, uma cobrança por action_id, lifecycle/rules reload.

## Testes exigidos ao Chat 3

Purchase fail-before-spend; PP legado 0; manual break; placed block com custo real; máquina/automation/fake player; bulk/vein dedup; zero/canceled cost; custo observado sem hard-code; cap 30%; provider absent; respec/reload/multiplayer; GameTests/build/JAR/dedicated-server smoke.
