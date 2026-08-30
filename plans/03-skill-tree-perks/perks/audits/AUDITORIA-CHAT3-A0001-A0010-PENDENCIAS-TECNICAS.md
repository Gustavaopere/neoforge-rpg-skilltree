# CHAT 3 — Auditoria das perks mergeadas — A0001–A0010

## Escopo

- **Lote:** A0001–A0010, exatamente 10 perks consecutivas.
- **Base auditada inicialmente:** `main@d1c29b1acca488f14e0741073f90502621a5ed39`.
- **Base reconciliada antes do merge:** `main@5e9dd777722014596641cb77d7be5c51df410e4e`, após merge da PR #243.
- **PR de correção:** #244 — `fix(perks): audit A0001-A0010 causal commits`.
- **Método:** leitura integral dos 10 dossiês mergeados antes da inspeção do runtime; comparação do contrato aprovado com a implementação real; correção somente de pendências técnicas comprovadas, sem redesenho.

## Resultado por perk

| Perk | Resultado da auditoria Chat 3 | Pendência técnica | Estado |
|---|---|---|---|
| A0001 | Contrato de dano SWORD e gate continuam coerentes com a `main`. | Nenhuma nova. | OK |
| A0002 | Bônus de velocidade/ritmo continua no pipeline previsto. | Nenhuma nova. | OK |
| A0003 | Crítico continua usando resolução canônica/deduplicada. | Nenhuma nova. | OK |
| A0004 | Ímpeto, ganho/perda/decay e deduplicação permanecem coerentes. | Nenhuma nova. | OK |
| A0005 | A lógica de elegibilidade/fallback estava correta, mas o consumo de recurso era prematuro. | `P-A0005-02`: 2 Ímpetos + cooldown eram commitados no PRE antes de dano efetivo. | RESOLVIDA na #244 |
| A0006 | Janela, cooldown, receipt `ON_DODGE` e bônus estavam presentes, mas o consumo era prematuro. | `P-A0006-01`: Riposta + 5 Ímpetos eram consumidos no PRE antes de dano efetivo. | RESOLVIDA na #244 |
| A0007 | Contrato de dano AXE continua coerente. | Nenhuma nova. | OK |
| A0008 | Bônus de velocidade/ritmo AXE continua coerente. | Nenhuma nova. | OK |
| A0009 | Crítico AXE continua no pipeline canônico. | Nenhuma nova. | OK |
| A0010 | Fúria exige hit direto/hostil/com dano e mantém deduplicação por ação raiz; multiplicador de troca de alvo preservado. | Nenhuma nova. | OK |

## P-A0005-02 — commit causal de Abertura de Guarda

### Defeito encontrado

`A0001A0020CombatPolicy.beforeHit(...)` consumia 2 de Ímpeto e iniciava imediatamente o cooldown por alvo assim que o PRE qualificava A0005. O adapter só descobre posteriormente, no POST, se houve dano efetivo. Um golpe cancelado ou reduzido a zero podia portanto deixar gasto/cooldown fantasma.

### Correção

- PRE continua responsável somente pela qualificação e pelos modificadores necessários ao golpe.
- O estado cria uma preparação transitória por `rootActionId`, com validade limitada.
- `afterConfirmedHit(...)` efetiva a transação apenas após `direct && hostile && actualDamage`.
- O commit confirmado consome exatamente 2 de Ímpeto e inicia o cooldown de 6 s por alvo.
- Resultado sem dano não produz mutação irreversível.
- O mesmo `rootActionId` não pode efetivar a transação duas vezes.

## P-A0006-01 — commit causal de Riposta Perfeita

### Defeito encontrado

A0006 consumia `riposteUntil` e 5 de Ímpeto em PRE. Se o golpe não chegasse a dano efetivo, a oportunidade armada era destruída sem o “próximo acerto direto de espada” exigido pelo contrato.

### Correção

- PRE apenas prepara o consumo da Riposta por `rootActionId` e aplica os modificadores elegíveis ao cálculo do golpe.
- POST confirmado efetiva atomicamente a janela e os 5 de Ímpeto.
- A supressão de ganho de A0004 no mesmo resultado passa a depender do commit POST real de `RIPOSTE`, impedindo supressão fantasma.
- Ataque cancelado/sem dano não gasta os 5 Ímpetos e não consome a janela armada.

## TDD e evidência de CI

### RED

- Commit de regressão: `7587aa9093fb5a78ed1b87f8bdc526e469f65205`.
- `RPG Skill Tree CI` **#2193**.
- Resultado esperado: **120 testes, exatamente 2 falhas**.
- Falhas:
  - `a0005DefersMomentumSpendAndCooldownUntilConfirmedDamagePost`;
  - `a0006DefersRiposteAndFiveMomentumSpendUntilConfirmedDamagePost`.
- Isso demonstrou que a implementação histórica ainda fazia mutações irreversíveis no PRE.

### Transição

- Após mover o comportamento de produção para PRE-prepare/POST-commit, `RPG Skill Tree CI` **#2202** chegou ao core e falhou somente porque o teste legado ainda exigia “A0005 consome no PRE”.
- A expectativa histórica foi atualizada para o contrato causal atual; não houve erro de compilação da infraestrutura nova.

### GREEN de código

- HEAD de código: `cc7ba795437943a962cdb5e33cd350f92d0ac123`.
- `RPG Skill Tree CI` **#2203**: **SUCCESS**.
- Verificados com sucesso: core tests, JUnit 5, NeoForge GameTests, validações de dados/runtime/providers, NeoForge build, JAR e dedicated-server smoke.

### GREEN do HEAD documental pré-reconciliação

- HEAD: `d1539b71fd210349fcf991bc551151216c7ded1b`.
- `RPG Skill Tree CI` **#2220**: **SUCCESS**.
- O job `verify` concluiu com sucesso todos os passos até `NeoForge dedicated-server smoke test`.
- Os nove workflows auxiliares associados ao mesmo HEAD também concluíram com `success`.

### Reconciliação concorrente com a PR #243

- A PR #243 avançou a `main` para `5e9dd777722014596641cb77d7be5c51df410e4e` e reorganizou auditorias em `perks/audits/`, além de atualizar `STATUS.md` para A0001–A0050.
- A PR #244 tornou-se temporariamente não mergeável por conflito documental em `STATUS.md`.
- O merge commit `f110492c68e67efcb4848fbee99dc522f7cf0307` foi criado com dois pais: o HEAD Chat 3 e a `main` da #243.
- A árvore reconciliada usa a `main` como base e sobrepõe somente os blobs validados de A0005, A0006, policy, estado e testes do Chat 3.
- O relatório Chat 3 foi movido para `perks/audits/` para respeitar a convenção introduzida pela #243.
- A reconciliação não altera os contratos nem os dossiês A0041–A0050.

## Pontos explicitamente fail-closed / não bloqueantes

### A0006 — aparo e guarda perfeita

- `ON_DODGE` é o receipt provider-native comprovado na versão auditada do Epic Fight e mantém a perk funcional.
- Aparo e guarda perfeita adicionais continuam **NÃO BLOQUEANTES / EXPANSÃO CONDICIONAL**.
- Não criar inferência por blocking passivo, animação, invulnerabilidade ou proximidade temporal.
- Só integrar outro caminho quando existir receipt público causal e versionado comprovado.

### Gate de versão Epic Fight

- Existe um helper interno `A0001A0020EpicFightHooks.supportsVersion(...)` baseado em prefixo.
- Isso **não é pendência ativa de gameplay**, porque o registro real do provider passa por `EpicFightVersionContract.supportsVersion(...)`, que exige igualdade exata com `21.17.3.1`.
- Trata-se de inconsistência de manutenção interna; o boundary efetivamente usado permanece fail-closed e exato.

## Conclusão do lote

- **Pendências técnicas acionáveis encontradas:** 2.
- **Pendências técnicas acionáveis corrigidas:** 2.
- **Perks alteradas:** A0005 e A0006.
- **Perks sem nova pendência acionável:** A0001–A0004 e A0007–A0010.
- **Dependências externas bloqueantes:** nenhuma neste lote.
- **Pendência não bloqueante mantida:** expansão futura de aparo/guarda perfeita de A0006, somente mediante receipt causal público e versionado.
- **Gate pré-merge comprovado:** CI #2220 integralmente verde no HEAD pré-reconciliação; a reconciliação deve obter CI verde próprio antes do merge.
- **Regra de ciclo:** após merge da PR #244 e confirmação da `main`, este Chat 3 deve parar. A0011–A0020 não deve ser iniciado automaticamente.
