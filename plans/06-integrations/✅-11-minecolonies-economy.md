# 06.11 — MineColonies Economy

> **Status:** V1 IMPLEMENTADA, VALIDADA, INTEGRADA E AUDITADA — PR #415 MERGED  
> **Minecraft:** 1.21.1  
> **Loader:** NeoForge  
> **Java:** 21  
> **Runtime do modpack:** MineColonies `1.1.1376-1.21.1-snapshot`  
> **Provider executado na CI desta entrega:** MineColonies `1.1.1375-1.21.1-snapshot`

O plano histórico que originou esta implementação foi preservado em `archive/11-minecolonies-economy-plan.md`. Este arquivo descreve o contrato efetivamente entregue pela V1 e não deve ser interpretado como promessa de funcionalidades deliberadamente adiadas ou bloqueadas por falta de hook seguro.

## Objetivo entregue

A V1 adiciona uma camada econômica opcional, server-authoritative e persistente por colônia sem substituir as autoridades nativas do MineColonies.

O runtime entregue cobre:

- identidade econômica estável por colônia;
- ledger monetário virtual canônico;
- emissão (`MINT`) e retirada permanente (`RETIRE`);
- conservação estrita da oferta monetária usando `long` e aritmética exata;
- deduplicação/idempotência persistente por `transactionId` e `causalKey`;
- retenção bounded de identidades de replay e rejeição fail-closed ao atingir limites;
- capacidade econômica `Q` derivada apenas de sinais MineColonies auditados e read-only;
- oferta monetária `M`, equilíbrio, índice de preços, inflação/deflação e convergência bounded;
- binding persistente `(dimension, nativeColonyId, ownerUuid, townHallPos)`;
- arquivamento do estado quando a colônia deixa de existir;
- scheduler periódico bounded/round-robin;
- snapshots e preflight estritamente read-only;
- autorização server-side para intents de emissão/retirada;
- cache cliente somente leitura;
- persistência global em `SavedData` do Overworld com schema estrito e restore fail-closed;
- testes provider-free e provider-present do contrato implementado.

## Authority

MineColonies continua sendo a única authority para:

- identidade/lifecycle da colônia;
- owner/officers e permissions;
- cidadãos;
- jobs;
- buildings e níveis;
- construção e upgrades;
- work orders;
- material requests;
- Warehouse/Couriers/logística;
- research;
- demais estados nativos.

O RPG Skill Tree é authority somente para:

- identidade econômica própria vinculada à colônia;
- ledger e oferta monetária virtual;
- emissão/retirada V1;
- capacidade econômica derivada;
- índice de preços e métricas macroeconômicas;
- snapshots/preflight próprios.

Nenhum caminho V1 cria ou altera citizens, buildings, work orders, requests, research ou logística do MineColonies.

## Modelo monetário implementado

A moeda da V1 é virtual e autoritativa no ledger.

A relação fundamental preservada é:

```text
M_effective = issuedSupply - retiredSupply
```

Toda mutação monetária passa pelo serviço canônico de ledger. O cliente nunca escolhe UUID monetário autoritativo e não existe segunda autoridade baseada em item físico.

Kinds suportados na V1:

- `MINT`;
- `RETIRE`.

Kinds que dependem de integração transacional ainda não comprovada permanecem rejeitados/fail-closed, incluindo cobrança/refund de construção.

## Capacidade econômica e inflação

`Q` é derivado read-only de sinais reais disponíveis no MineColonies auditado. O adapter não usa heurística por nome de bloco nem escreve de volta no provider.

O runtime calcula um equilíbrio monetário a partir de `Q`, compara esse equilíbrio com `M` e atualiza o índice de preços por convergência bounded. Os parâmetros são validados no servidor; combinações inválidas são rejeitadas em vez de degradar silenciosamente para defaults inseguros.

O princípio econômico preservado pela implementação é:

> mais moeda sem crescimento correspondente de capacidade pressiona o índice de preços; crescimento de capacidade permite sustentar maior oferta monetária.

## Persistência, replay e segurança

O estado econômico é persistido server-side com schema versionado e validação estrita.

Invariantes relevantes:

- supply nunca pode ficar negativo;
- `retiredSupply <= issuedSupply`;
- arithmetic overflow falha fechado;
- replay do mesmo `transactionId`/`causalKey` não cria nova moeda;
- restore inválido ou oversized é rejeitado;
- retention overflow de identidades idempotentes não é truncado silenciosamente;
- preflight e snapshot não mutam estado;
- ausência do provider ou versão não permitida desativa a parcela dependente sem quebrar o core provider-free.

## MineColonies adapter

A auditoria profunda original foi feita contra MineColonies `1.1.1375-1.21.1-snapshot`, upstream `ldtteam/minecolonies` branch `version/1.21`, commit `a8022f703d80be3a0931f0d6cc34b229563ef713`.

A modlist e o Notion atuais usam `minecolonies-1.1.1376-1.21.1-snapshot.jar`. O contrato de versão aceita explicitamente 1.1.1375 e 1.1.1376 e permanece fail-closed fora da allowlist.

A inspeção do delta upstream após o snapshot auditado não identificou mudança pertinente nos contracts de colony/buildings/permissions consumidos pela V1. Entretanto, o binário exato 1.1.1376 ainda não foi executado nesta CI; essa diferença de evidência está documentada em `11-minecolonies-economy-reconciliation-1.1.1376.md`.

## Networking V1

O protocolo econômico é server-authoritative e cobre:

- snapshot request/result;
- mint preflight;
- mint intent;
- retire intent.

O servidor resolve contexto, binding, permission e identidade econômica antes de executar qualquer mutação.

## Seams deliberadamente FAIL-CLOSED

### Construção/upgrade monetário

**NÃO IMPLEMENTADO NA V1.** A auditoria não encontrou hook público, transacional e seguro imediatamente antes da ação irreversível de construção/upgrade que permita cobrar moeda sem risco de cobrança tardia, consumo parcial de materiais ou refund duplicado.

`CONSTRUCTION_CHARGE`/`REFUND` permanecem `UNSUPPORTED_KIND` onde aplicável. Não foi introduzida cobrança pós-fato, cancelamento frágil ou acesso a internals para simular esse contrato.

### UI custom dentro do Town Hall

**NÃO IMPLEMENTADA NA V1.** Não foi identificado extensibility point público estável para inserir a aba econômica planejada sem mixin/override invasivo de BlockUI/Town Hall.

A ausência dessa UI não autoriza bypass de authority: os serviços de snapshot/preflight/intents continuam server-side e podem sustentar uma interface futura quando existir seam seguro.

### Moeda física / Create: Numismatics

**FORA DA V1.** Create: Numismatics não está presente na modlist/Notion atuais. A V1 não depende do mod, não cria bridge automática e não usa item físico como autoridade paralela ao ledger.

### Citizen economy, impostos completos, Banco e FX

**FORA DA V1:** wallets/salários/consumo de cidadãos, settlement tributário completo, Banco/Tesouro custom, comércio entre colônias, câmbio e reservas estrangeiras.

## Validação final e merge

A branch foi sincronizada com a `main` e validada no HEAD final `55387a4a8246337fabf6697c9105ac9d10729998`.

A matriz final associada ao HEAD fechou **24/24 workflows em `success`**, sem `failure`, `cancelled` ou workflow pendente. Evidências principais:

- RPG Skill Tree CI `34046743733` / run #3814 — GREEN completo, incluindo JUnit 5, NeoForge JUnit adapters, NeoForge GameTests, Battle Mage provider-present GameTests, NeoForge build, verificação do JAR e dedicated-server smoke;
- SonarQube Cloud `34046743697` / run #1050 — GREEN, incluindo provider-free coverage, Battle Mage provider coverage, Ars Nouveau provider coverage, instalação do stack MineColonies auditado 1.1.1375, MineColonies provider coverage e Quality Gate;
- Volcanoes Full Pack Compatibility Acceptance `34046743729` / run #1002 — GREEN, incluindo full-pack exact-host GameTests e save/reload smoke;
- Battle Mage Epic Compatibility `34046743728` / run #370 — GREEN;
- CodeQL Security `34046743731` / run #847 — GREEN;
- os demais workflows disparados pelo mesmo HEAD também concluíram `success`, totalizando 24/24.

A PR #415 foi mergeada em **2026-09-06**. Merge commit: `ce00a228535974043476528b9af47179ce665b21`.

A validação provider-present do binário exato MineColonies 1.1.1376 continua sendo uma pendência de evidência, não um requisito retroativamente declarado como satisfeito: a lane executada nesta entrega usa a build auditada 1.1.1375.

## Acceptance V1

- [x] core econômico independente de MineColonies;
- [x] ledger único e server-authoritative;
- [x] `MINT`/`RETIRE` idempotentes;
- [x] conservação e arithmetic overflow fail-closed;
- [x] capacidade econômica `Q` derivada read-only;
- [x] inflação/deflação determinística e bounded;
- [x] persistência/reload e schema estrito;
- [x] replay protection persistente e bounded;
- [x] binding/lifecycle por colônia;
- [x] provider ausente não quebra o core;
- [x] provider presente 1.1.1375 validado em GameTests;
- [x] 1.1.1376 explicitamente allowlisted com inspeção de delta upstream;
- [x] permission/authority server-side;
- [x] snapshots/preflight read-only;
- [x] Quality Gate Sonar final GREEN;
- [x] matriz final executada no HEAD sincronizado com `main`;
- [x] CI GREEN final do PR;
- [x] PR #415 mergeada e `main` confirmada no merge commit;
- [ ] executar provider-present contra o binário exato 1.1.1376 quando a origem/distribution ID puder ser demonstrada sem navegador externo/TinyFish.

**Acceptance V1: satisfied**, com a pendência de evidência 1.1.1376 explicitamente fora do conjunto de gates já provados pela entrega.

## Evidência complementar

- `11-minecolonies-economy-audit-1.1.1375.md` — auditoria de API/hooks/provider.
- `11-minecolonies-economy-ui-audit-1.1.1375.md` — auditoria da extensão de UI e motivo do fail-closed.
- `11-minecolonies-economy-reconciliation-1.1.1376.md` — reconciliação da build atual da modlist/Notion e limites da evidência 1.1.1376.
- `archive/11-minecolonies-economy-plan.md` — especificação histórica anterior à implementação.
