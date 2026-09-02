# Auditoria Chat 3 — A0031–A0040 — pendências técnicas, testes e fechamento

Data: 2026-09-02  
Lote exato: **A0031–A0040**  
PR oficial: **#359**  
Branch: `feat/chat2-a0031-a0040-retro-implementation`

## Escopo e regra de fechamento

Esta auditoria cobre exclusivamente A0031–A0040. O Chat 3 continuou a branch/PR oficial do Chat 2, revisou o runtime contra os dez dossiês aprovados, resolveu/confirmou pendências técnicas sem alterar identidade, topologia, gates, authority ou semântica essencial, sincronizou a branch com a `main` corrente e executou a bateria NeoForge/CI. A0041+ não foi iniciada.

## Reconciliação com a main

O HEAD anterior da PR #359 era `ee600298df6791c13be7a327442ed6be5dbb8d75` e estava 125 commits atrás de `main@5213d068a91c95f45b9e119dec0be0636abc426d`.

O GitHub materializou o merge de teste exato `8cf156294c7dd5922f6138a108a544f3ddeeddea`, com pais `5213d068a91c95f45b9e119dec0be0636abc426d` e `ee600298df6791c13be7a327442ed6be5dbb8d75`. Como o HEAD antigo da PR é pai desse commit, a branch oficial foi avançada por fast-forward, sem force-push e sem resolução manual de conflito.

## Findings históricos da PR #359 e estado atual

O review automatizado antigo da #359 apontou três riscos no commit `e0786cb908`:

1. teste histórico de A0035 ainda esperava consumo/Sunder no PRE;
2. testes de Mastery ainda esperavam XP repetível de MACE/SCYTHE;
3. o bridge posterior A0061–A0080 poderia perder o fallback da maça vanilla após remoção da tag paralela.

No HEAD sincronizado os três findings estão fechados:

- A0035 é reservation→POST commit e rollback em cancel/zero é coberto por regressão;
- MACE/SCYTHE usam discovery finita +10 por tipo hostil inédito, com 6 tipos→60 e MACE 8→80;
- `A0061A0080EpicFightHooks.physicalMelee(...)` usa capability/categoria Epic Fight e fallback apenas para identidade exata `Items.MACE`; teste dedicado cobre esse downstream consumer.

## Evidência de testes no HEAD sincronizado

`RPG Skill Tree CI` **#3361**, run **33657496252**, HEAD `8cf156294c7dd5922f6138a108a544f3ddeeddea`: **SUCCESS**.

Passaram no mesmo SHA:

- Gradle wrapper contract;
- geração de dados;
- Core tests;
- wiki generator + drift;
- main-tree content coverage;
- JUnit 5;
- NeoForge JUnit adapter tests (`testJunit`);
- NeoForge GameTests;
- compendium/provenance/inventory/model/parser/runtime/editorial tests;
- data/client/node/passive/runtime/attribute/provider validations;
- generated data drift/diff sanity;
- NeoForge build;
- built JAR verification;
- NeoForge dedicated-server smoke.

Os gates `verifyPlainJUnitExecution` e `verifyNeoForgeJUnitExecution` falham explicitamente se `test` ou `testJunit` terminarem `NO-SOURCE`; portanto o verde acima representa execução real das duas suítes.

## Matriz final do lote

| Perk | Resultado Chat 3 | Pendência remanescente |
|---|---|---|
| A0031 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0032 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0033 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0034 | **IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO** | guard/posture extras seguem fail-closed, não bloqueantes |
| A0035 | **IMPLEMENTAÇÃO CONFIRMADA no contrato genérico** | `P-A0035-01`: Witherstein específico sem registry/tag versionado; fail-closed não bloqueante |
| A0036 | **NÃO CONFIRMADA / FAIL-CLOSED CORRETO** | `P-A0036-01`: Epic Fight 21.17.3.1 não oferece heavy receipt inequívoco |
| A0037 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0038 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0039 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |
| A0040 | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma |

## Contratos técnicos validados

### A0031/A0037 — classificação e Mastery

- MACE externa: somente category/capability Epic Fight ou mapping explicitamente versionado; fallback vanilla somente `minecraft:mace`.
- SCYTHE: somente provider-native/mapping explícito; sem fallback por hoe, nome, aparência ou tag paralela.
- HAMMER/MACE/SCYTHE usam discovery finita e replay-safe; dano repetido não produz Mastery de gate.

### A0035 — transaction boundary

- PRE reserva três cargas de Trauma por root; não consome nem marca Sundered.
- POST com dano real commita exatamente três cargas e só então cria Sundered/modifier de Armor.
- cancelamento/dano zero libera a reserva e preserva Trauma.
- roots concorrentes não duplicam consumo.

### A0036 — fail-closed e consumer latente

- Sunder precisa preexistir ao root atual; o mesmo hit que cria A0035 não satisfaz A0036.
- cooldown só inicia no POST confirmado.
- consumer latente de Descompasso aplica movement e outgoing damage somente para o tag físico canônico; boss-half, duração e cleanup foram testados.
- o adapter real continua `heavyConfirmed=false`; animação, dano alto, Impact, arma lenta, combo ou `shouldChargeWeapon` não foram promovidos a receipt.

### A0040 — lifecycle bounded

- Marca exige hit direto SCYTHE elegível e crossing real ≥50%→<50% depois de marcada.
- pruning periódico remove marks expiradas sem depender de nova consulta ao target UUID.
- lifecycle/expiry/dedup permanecem bounded.

## Regressões transversais preservadas

- causalidade A0023 já mergeada continua POST-safe;
- `ARCANE_BACKLASH`, companion-owned damage e hazards não herdam autoria MARTIAL;
- removed tags MACE/SCYTHE não deixaram consumer posterior quebrado;
- provider ausente/desconhecido permanece fail-closed.

## Critério de merge

Este arquivo registra o fechamento técnico do lote. Após este commit documental, o HEAD final da PR #359 deve executar novamente os checks aplicáveis. Somente com CI verde no HEAD documental, PR mergeável e base fresca o Chat 3 fará o merge e confirmará a `main` pós-merge. A0036 continua explicitamente não operacional até existir heavy receipt seguro.
