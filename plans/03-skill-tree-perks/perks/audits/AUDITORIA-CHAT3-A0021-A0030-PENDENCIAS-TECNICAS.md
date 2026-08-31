# CHAT 3 — Auditoria das perks mergeadas — A0021–A0030

## Escopo

- **Lote:** A0021–A0030, exatamente 10 perks consecutivas.
- **Base inicial auditada:** `main@9958caaabebff95bfbbd0a226ca571e5bfe5316c`.
- **PR de correção:** #315 — `fix(perks): audit A0021-A0030 causal commits`.
- **Método:** leitura dos 10 dossiês e da auditoria Chat 2; comparação do contrato aprovado com o runtime já mergeado; TDD RED antes da mudança de produção; correção somente de divergências técnicas comprovadas, sem redesenhar perks e sem fabricar receipts provider-native ausentes.

## Resultado por perk

| Perk | Resultado Chat 3 | Pendência | Estado |
|---|---|---|---|
| A0021 | Crítico DAGGER permanece no resolvedor canônico e sem segunda rolagem. | Nenhuma nova. | OK |
| A0022 | Fluxo, idle decay, stagger forte e reposition server-authoritative permanecem conforme contrato. | Nenhuma nova. | OK |
| A0023 | Fluxo + cooldown eram consumidos/iniciados no PRE. | `P-A0023-01` | RESOLVIDA na #315 |
| A0024 | 4 Fluxo, ativação da Dança e primeiro hit especial podiam ser consumidos no PRE. | `P-A0024-01` | RESOLVIDA na #315 |
| A0025 | HAMMER provider-native e Mastery por discovery finita permanecem corretos. | Nenhuma nova. | OK |
| A0026 | Attack-speed HAMMER permanece no provider previsto. | Nenhuma nova. | OK |
| A0027 | Crítico HAMMER permanece no resolver canônico. | Nenhuma nova. | OK |
| A0028 | Abalo continua seguro; guard-pressure provider-native segue indisponível. | `P-A0028-01` | PARCIAL / FAIL-CLOSED CORRETO |
| A0029 | Core consumiria 3 Abalo no PRE assim que heavy receipt seguro existir. | `P-A0029-02` resolvida; `P-A0029-01` externa preservada. | FAIL-CLOSED CORRETO |
| A0030 | Core consumiria Janela Demolidora no PRE quando guard-break/heavy seguros existirem. | `P-A0030-02` resolvida; `P-A0030-01` externa preservada. | FAIL-CLOSED CORRETO |

## P-A0023-01 — commit causal de Ataque ao Ponto Cego

### Defeito

`A0021A0040CombatPolicy.beforeHit(...)` consumia 2 Fluxo e iniciava o cooldown por alvo antes de o provider confirmar dano efetivo. Um golpe cancelado ou zerado podia deixar gasto/cooldown fantasma.

### Correção

- PRE reserva 2 Fluxo por `rootActionId` e aplica somente os modifiers necessários ao cálculo.
- Fluxo reservado é descontado da disponibilidade para roots concorrentes, sem alterar o valor persistente/transitório antes do commit.
- POST `direct && hostile && actualDamage` consome exatamente 2 Fluxo e inicia o cooldown por alvo.
- POST inválido descarta a reserva sem gasto/cooldown.
- Commit consumidor acontece antes do ganho A0022 do mesmo hit.

## P-A0024-01 — commit causal de Dança das Sombras

### Defeito

A ativação podia consumir 4 Fluxo, armar a Dança e queimar o primeiro bônus de hit no PRE. Cancelamento/dano zero podia produzir perda de recurso/benefício sem ataque efetivo.

### Correção

- PRE reserva ativação por `rootActionId`; os 4 Fluxo permanecem intactos até POST.
- Se o hit ativador é lateral/traseiro, ele recebe o pacote de cálculo no PRE, mas o benefício único só é marcado como consumido no commit.
- Dança e benefício de movimento só ficam ativos após POST confirmado.
- POST inválido descarta a reserva e preserva Fluxo/janela/primeiro-hit enquanto os prazos originais ainda forem válidos.
- Hit de uma Dança já ativa também usa reserva; cancelamento não queima o benefício único.
- Custo de 4 Fluxo é commitado antes do ganho A0022 do próprio hit.

## P-A0029-02 — commit causal latente de Quebra de Postura

### Defeito

O caminho de core consumia 3 Abalo no PRE quando `heavyConfirmed=true`. Hoje o adapter real mantém `heavyConfirmed=false`, portanto o defeito estava mascarado pelo fail-closed; passaria a ser observável assim que um receipt seguro fosse ligado.

### Correção

- PRE reserva 3 Abalo por root action; não remove as cargas.
- POST confirmado consome exatamente 3 cargas.
- POST inválido faz rollback.
- Cargas reservadas não financiam outra root action concorrente.
- Commit consumidor ocorre antes do ganho A0028 do mesmo hit.

### Pendência preservada

`P-A0029-01` continua **ABERTA / BLOQUEANTE PARA IMPLEMENTAÇÃO CONFIRMADA / FAIL-CLOSED CORRETO**: Epic Fight 21.17.3.1 não expõe heavy receipt inequívoco. `shouldChargeWeapon`, animação, dano, arma lenta, impacto e charge-time estimado continuam proibidos como substitutos.

## P-A0030-02 — commit causal latente de Golpe Demolidor

### Defeito

`beforeHit(...)` removia a Janela Demolidora no PRE. Uma tentativa heavy cancelada/zerada poderia consumir a janela sem dano efetivo assim que os receipts exigidos fossem ligados.

### Correção

- PRE reserva a janela por `rootActionId` e alvo; a janela original não é removida.
- POST confirmado consome a janela exatamente uma vez.
- POST inválido descarta a reserva e mantém a janela enquanto seu prazo original ainda estiver ativo.
- Reserva por alvo impede consumo simultâneo por roots concorrentes.

### Pendência preservada

`P-A0030-01` continua **ABERTA / BLOQUEANTE PARA IMPLEMENTAÇÃO CONFIRMADA / FAIL-CLOSED CORRETO**: faltam guard-break causal attacker-side e heavy receipt seguro no provider auditado. Nenhuma inferência por stamina, som, animação, stun, Armor ou dano foi adicionada.

## Adapter Epic Fight

- `DealDamageEvent.Post` agora sempre encerra a transação PRE correspondente quando existe `PendingHit`.
- Dano efetivo/hostilidade válidos fazem commit; `modifiedDamage <= 0` ou alvo que deixou de ser hostil fazem rollback das reservas A0023/A0024/A0029/A0030.
- A0028/A0029/A0030 não receberam novos receipts sintéticos.

## TDD e evidência

### RED

- Commit de testes: `53f469c3f8943b1b011a306e8b6a497256d3a778`.
- `RPG Skill Tree CI` #2656 / run `33393390999`.
- Core histórico permaneceu verde; JUnit falhou após a introdução dos testes causais, confirmando divergência no comportamento mergeado.

### Iteração de correção

- Estado/Policy/adapter migrados para reservation→commit.
- Um erro de fixture no novo teste A0023 foi identificado pelo CI: o teste criava 2 Fluxo, mas esperava 4. A expectativa foi corrigida sem alterar produção no commit `704921491a92ea57fe73e0fd94493fab7d88a4d4`.

### GREEN pré-fechamento documental

- HEAD validado: `e32d72bb1280b667c12057bfe1f17cdfbfad0b57`.
- `RPG Skill Tree CI` #2691 / run `33399375858`: **SUCCESS**.
- Verificados com sucesso: Core tests, JUnit 5, NeoForge GameTests, Compendium, validações de dados/árvore/runtime/providers, NeoForge build, JAR e dedicated-server smoke.
- Workflows auxiliares do mesmo HEAD: Bootstrap, Diagnostics, Optional Integrations, Discovery, Ecology, World, Entities, Flora e Editorial concluídos com sucesso.
- O commit documental/status final ainda deve receber sua própria rodada de CI antes do merge.

## Estado de fechamento

- **Pendências internas acionáveis encontradas:** 4.
- **Pendências internas corrigidas:** 4.
- **Blockers provider-native preservados:** `P-A0028-01`, `P-A0029-01`, `P-A0030-01`.
- **Perks com código alterado causalmente:** A0023, A0024, A0029, A0030.
- **Perks sem nova pendência acionável:** A0021, A0022, A0025, A0026, A0027, A0028.
- **A0031+:** fora do escopo deste ciclo.
- **Regra de ciclo:** após CI verde, merge e confirmação de `main`, PARAR.
