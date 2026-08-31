# Auditoria Chat 3 — A0031–A0040 — pendências técnicas, testes e fechamento

Data: 2026-08-31  
Lote exato: **A0031–A0040**  
PR: **#252**  
Branch: `chat2/a0031-a0040-implementation`

## Escopo e regra de fechamento

Esta auditoria cobre exclusivamente A0031–A0040. O Chat 3 revisou implementação contra os dez dossiês aprovados, reconciliou a branch com a `main`, corrigiu problemas técnicos sem alterar identidade/topologia/gates/semântica de design, executou regressões e a bateria NeoForge/CI e preservou fail-closed onde o provider não oferece receipt inequívoco. A0041+ não foi iniciada.

## Diagnóstico inicial

A PR #252 havia sido implementada antes das correções causais A0021–A0030 posteriormente mergeadas. O diff antigo reintroduziria consumo irreversível em PRE para regras já endurecidas e continha gaps próprios do lote:

- MACE/SCYTHE ainda possuíam classificadores paralelos `rpgskilltree:maces`/`rpgskilltree:scythes`;
- `combat:mace`/`combat:scythe` ainda podiam receber XP repetível por hit;
- A0035 consumia Trauma e marcava Sundered no PRE;
- A0036 podia ficar causalmente vulnerável ao mesmo root que criasse Sundered e não possuía runtime real de Descompasso;
- A0040 retinha marks expiradas de targets que não voltassem a ser consultados;
- testes históricos ainda codificavam a semântica pré-lote.

## TDD e trilha RED → GREEN

### RED controlado

- CI #2770: árvore reconciliada com `main` + regressões Chat 3 falhou porque os seams `prepareSunder`/`prepareBonebreaker` ainda não existiam na árvore integrada. Isso confirmou que o commit transacional não estava presente após a reconciliação.
- CI antigo da #252 também havia falhado em `A0021A0040MasteryPolicyTest` porque o teste ainda exigia MACE como “future lot untouched” com XP repetível.
- CI #2799: após reintrodução controlada da implementação sobre a `main`, o core chegou até o teste histórico de MACE e falhou porque ele ainda esperava `Sundered` no PRE; produção anterior já não correspondia mais à expectativa antiga.
- CI #2804: Core/wiki/coverage passaram e JUnit atingiu a regressão Chat 3. A única falha foi uma fixture de rollback A0035 que tentava preparar Sunder em outro target sem 3 Trauma. O teste foi corrigido para provar liberação da reserva no target original, sem alterar produção.

### GREEN comportamental

HEAD comportamental validada antes do fechamento documental: `ca29482dbeeb488e5a823ac2428b44ec3f4b33fb`.

`RPG Skill Tree CI` **#2806 — GREEN**:

- Gradle wrapper contract;
- geração de dados;
- Core tests;
- wiki generator + drift;
- content coverage;
- JUnit 5;
- NeoForge GameTests;
- compendium/provenance/inventory/model/parser/runtime/editorial tests;
- data/client/node/passive/runtime/attribute/provider validations;
- generated data drift;
- NeoForge build;
- built JAR verification;
- NeoForge dedicated-server smoke.

`SonarQube Cloud` **#41 — GREEN** na mesma HEAD comportamental.

## Correções técnicas aplicadas

### A0031 / A0037 — família e Mastery

- Removidos `src/main/resources/data/rpgskilltree/tags/item/maces.json` e `scythes.json`.
- MACE externa: somente Epic Fight category/capability `mace` ou mapping explicitamente versionado; fallback NeoForge somente `Items.MACE`.
- SCYTHE: somente Epic Fight category/capability `scythe` ou mapping explicitamente versionado; sem fallback vanilla/tag/nome/aparência.
- `A0021A0040MasteryPolicy`: HAMMER/MACE/SCYTHE usam discovery finita; +10 uma vez por tipo hostil inédito, replay-safe; hits repetidos = 0 XP.
- `A0021A0040MasteryHooks`: producer único baseado em tipo hostil distinto e `DiscoveryProgress`.

### A0035 — transaction boundary

- PRE prepara `PendingSunder` e reserva as 3 cargas; não consome Trauma nem marca Sundered.
- POST confirmado consome exatamente 3 Trauma e marca Sundered.
- hit cancelado/dano zero chama rollback explícito e libera a reserva sem aguardar TTL.
- modifier `Attributes.ARMOR` só é aplicado quando o commit POST é confirmado.
- boss-half genérico via `Tags.EntityTypes.BOSSES` preservado.

### A0036 — causalidade e Descompasso

- `preexistingSunder` é capturado antes da preparação A0035; o mesmo root nunca cria Sunder e ativa Quebra-Ossos.
- `PendingBonebreaker` usa PRE reservation e POST commit; cooldown só começa após hit confirmado.
- Descompasso possui movement modifier transitório e redução de outgoing damage somente para DamageTypes do tag canônico `rpgskilltree:physical`.
- expiry/cleanup implementados e boss-half preservado.
- `combat:mace` anti-farm fecha o gate 80 como oito tipos hostis distintos.

### A0040 — lifecycle bounded

- `pruneExpiredReapingMarks(now)` remove marks expiradas sem depender de novo lookup do target UUID.
- pruning periódico server-side a cada 1 s de game time.
- death/player lifecycle cleanup existente preservado.
- crossing ≥50% → <50% e idempotência da mesma mark preservados.

## Pendências externas preservadas fail-closed

### P-A0035-01 — Mobstein / Witherstein

Fontes públicas atuais confirmam Witherstein como boss do Mobstein, mas a auditoria não obteve registry id canônico nem prova de membership em `Tags.EntityTypes.BOSSES`. Portanto não foi criado mapping por nome/namespace/aparência. O boss-half genérico funciona quando o target realmente possui o tag canônico; extensão específica Witherstein permanece `SEM HOOK SEGURO`.

### P-A0036-01 — heavy receipt Epic Fight

Inspeção da API pública Epic Fight 1.21.1:

- `DealDamageEvent` não expõe flag heavy inequívoca;
- `EpicFightDamageSource.shouldChargeWeapon()` não foi aceito: `PlayerPatch` define `chargeWeapon` a partir de combo attack animation/variável de combo, o que não prova semanticamente o heavy receipt exigido;
- nenhuma heurística por animação, damage magnitude, weapon speed, stun, impact ou combo foi introduzida.

Consequência: A0036 continua **NÃO CONFIRMADA / FAIL-CLOSED CORRETO** até existir receipt provider-native/versionado inequívoco. A infraestrutura interna de Descompasso/causalidade está implementada e testada, mas não autoriza declarar a perk operacional.

## Matriz final do lote

| Perk | Resultado Chat 3 | Pendência remanescente |
|---|---|---|
| A0031 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0032 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0033 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0034 | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO | rotas extras de guard/posture continuam fail-closed, não bloqueantes |
| A0035 | IMPLEMENTAÇÃO CONFIRMADA no contrato genérico | `P-A0035-01` provider-specific Witherstein sem hook seguro, não bloqueia contrato genérico |
| A0036 | NÃO CONFIRMADA / FAIL-CLOSED CORRETO | `P-A0036-01` heavy receipt inequívoco ausente |
| A0037 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0038 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0039 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |
| A0040 | IMPLEMENTAÇÃO CONFIRMADA para merge | nenhuma |

## Testes específicos adicionados/atualizados

- `A0031A0040Chat3RegressionJUnitTest`
  - preserva causalidade A0023 já mergeada;
  - rollback A0035 libera reserva e preserva Trauma/Sundered;
  - rollback A0036 não inicia cooldown e libera reserva.
- `A0031A0040ImplementationContractJUnitTest`
  - Mastery MACE/SCYTHE finita por tipo;
  - A0035 PRE→POST commit;
  - preexistência de Sunder e cooldown POST de A0036;
  - boss-half A0035;
  - pruning bounded A0040.
- `A0021A0040CombatPolicyTest` atualizado para semântica transaction-safe sem regredir Dagger/Hammer.
- `A0021A0040MasteryPolicyTest` atualizado para HAMMER/MACE/SCYTHE finite discovery.

## Critério de merge

Após este fechamento documental, a PR #252 deve executar novamente a CI sobre a HEAD final. Só com `RPG Skill Tree CI` e checks aplicáveis verdes, PR não-draft/mergeable e diff final revisado o Chat 3 realizará o merge e confirmará o SHA da `main`.
