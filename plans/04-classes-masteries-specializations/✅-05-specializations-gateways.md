# Specializations Complete — Gateways and Progression

**Goal:** abrir especializações de magia, combate e tecnologia com requisitos explícitos e seguros.

- [x] Validar as 25 definições atuais e seus IDs.
- [x] Resolver mod/provider exigido quando aplicável.
- [x] Bloquear/desbloquear gateway de forma derivada, não persistência redundante quando possível.
- [x] Revogar estado derivado após respec que quebre requisito.
- [x] Diferenciar “especialização existe” de “adapter runtime completo existe”.

## Runtime contract

- As 25 definições em `data/rpgskilltree/specializations` preservam agora o `provider` declarado no datapack dentro de `SpecializationDefinition`; o contrato atual cobre 9 Iron's Spellbooks, 6 Ars Nouveau, 3 Epic Fight, 4 Create, 2 Oritech e 1 AE2.
- `SpecializationCatalog` mantém separadas a existência da definição e a disponibilidade runtime. `SpecializationAvailability` expõe `providerLoaded`, `runtimeAdapterComplete` e `gatewayAvailable = providerLoaded && runtimeAdapterComplete`.
- `SpecializationReloader` resolve presença do provider pela build NeoForge atual (`ModList`) e aplica uma policy explícita de capacidade de adapter. Provider carregado, por si só, não torna um gateway disponível.
- A policy atual reconhece `rpgskilltree`, `irons_spellbooks` e `ars_nouveau` como adapters completos desta camada. Epic Fight só é considerado completo quando a versão instalada satisfaz exatamente `EpicFightVersionContract` (`21.17.3.1`), o mesmo gate que autoriza o registro dos hooks. `create`, `ae2`, `oritech` e providers desconhecidos permanecem fail-closed até uma integração runtime equivalente existir; isso impede gateways de prometer mecânica não registrada.
- `SpecializationResolver` continua avaliando classe elegível, Mastery e tags, mas também retorna bloqueadores distintos para provider ausente e adapter incompleto. A sobrecarga provider-agnostic existente foi preservada para contratos puros/legados.
- `PlayerProgressionRuntime.reconcileDerivedState` reconstrói especializações concedidas por nós usando `SpecializationCatalog::gatewayAvailable`. Assim, um gateway só materializa estado derivado quando o nó está ativo e o provider/adapter atual está disponível.
- Perda do provider, versão Epic Fight não suportada ou regressão da capacidade do adapter remove somente a especialização derivada; o nó aprendido e a Mastery conquistada são preservados. O respec já existente remove o nó/reconcilia e, consequentemente, revoga o grant derivado sem criar uma segunda persistência.
- A camada não sintetiza `InvestmentState` live nem inventa pesos/tags para as especializações que dependem desse modelo. Esses contratos continuam sendo avaliados pelas APIs puras existentes até a fonte canônica de investimento do Stage 04.01 estar disponível.

## TDD e verificação

- RED: PR #307 / `RPG Skill Tree CI` run #2574 (`33387496709`) chegou ao gerador semântico e falhou em `compileTestJava` exatamente porque ainda não existiam `SpecializationAvailability`, `providerId`, o seam de loader, availability no catálogo, policy de adapter e overloads de resolução/reconciliação exigidos pelo teste.
- `SpecializationGatewayAvailabilityJUnitTest` cobre os 25 IDs/providers canônicos, distinção definição/provider/adapter, fail-closed do resolver, unlock/revogação pelo caminho live de reconciliação e o gate de versão de Epic Fight.
- `SpecializationReconciliationTest` continua cobrindo preservação de especializações migradas, revogação de grant inativo e restauração de grant ativo.
- O primeiro head funcional completou Core, wiki drift, cobertura da árvore, JUnit 5, NeoForge GameTests e todos os validators antes de uma execução ser cancelada externamente durante `NeoForge build`; a verificação final completa deve ser feita no head documental final antes do merge.

**Acceptance:** satisfied para o contrato de gateways/provider desta etapa. Gateways não anunciam integração mecânica de provider ausente, versão incompatível ou adapter incompleto, e a disponibilidade é derivada da build/runtime atual em vez de ser persistida como verdade paralela.
