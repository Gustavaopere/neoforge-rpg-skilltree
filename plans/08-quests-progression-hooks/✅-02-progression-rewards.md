# Quest Hooks Plan — Canonical Rewards

**Goal:** permitir recompensas de quest sem criar um segundo sistema de progressão.

- [x] Conceder XP por serviço canônico.
- [x] Conceder mastery somente quando a regra narrativa permitir e por API explícita.
- [x] Conceder pontos/unlocks apenas por comandos autorizados.
- [x] Validar caps e IDs antes da mutação.
- [x] Emitir sync/eventos normais do core após reward.

## Contrato implementado

- XP, Core Progression Points e Main Perk Budget continuam usando `ProgressionRewardService` e a mutation boundary canônica do Core; Stage 08.02 não cria storage, ledger ou pipeline paralelo.
- Recompensas Core repetidas continuam protegidas por `ProgressionRewardClaims`; class, specialization e perk unlocks permanecem atrás de seus resolvers/comandos autoritativos e não foram convertidos em um reward genérico.
- `RpgQuestProgressionApi.applyAuthorizedMasteryReward(ServerPlayer, MasteryAward)` é a boundary explícita para mastery depois que a camada narrativa server-side autorizou a concessão.
- `QuestMasteryRewardPolicy` rejeita lane fora de `MasteryLaneCatalog` e exige `MasteryAward.replaySafe(...)`, de modo que adapters narrativos não possam inventar lanes nem emitir mastery repetível sem identidade estável.
- A façade delega exclusivamente para `PlayerProgressionRuntime.awardMastery(...)`; esse runtime mantém reconciliação canônica, persistência, mutation event, owner sync e refresh normal de efeitos. A API pública não escreve attachment diretamente.
- `scripts/verify-quest-runtime.py` protege a delegação e falha se a façade passar a acessar storage interno, mutar mastery diretamente ou introduzir atalhos genéricos para class/perk unlock.
- `QuestMasteryRewardPolicyJUnitTest` cobre aceitação de reward canônico/replay-safe e rejeições fail-closed antes do runtime.
- `A0031A0040EpicFightQuestRewardCoverageJUnitTest` executa a rota válida quest → mastery no JUnit carregado do NeoForge com uma lane canônica Epic Fight, verificando a delegação para o runtime compartilhado e a query de retorno sem mock frágil no JUnit plain.
- `QuestProgressionSnapshot.CONTRACT_VERSION` / `RpgQuestProgressionApi.CONTRACT_VERSION` permanecem em `1`: a nova boundary é aditiva e não altera semanticamente os contratos existentes de `query`, `evaluate` ou `applyReward`.

## Evidência

- TDD RED: commit `74a76cd9c2551e0832137213e2053ba107ae7a62`; RPG Skill Tree CI `33982433941` falhou em `Core tests` com os símbolos ainda inexistentes de `QuestMasteryRewardPolicy`, demonstrando que o teste exigia a nova boundary antes da implementação.
- Durante hardening, o JUnit plain demonstrou que mockar/instrumentar `ServerPlayer` fora do ambiente NeoForge era inválido; os testes fail-closed foram mantidos sem esse mock e a rota válida foi movida para o JUnit NeoForge já agregado ao JaCoCo.
- Um candidato funcional chegou a 78,6% de Coverage on New Code no SonarQube Cloud; nenhum threshold/configuração foi relaxado. A cobertura faltante foi obtida executando a rota válida real da façade no `testJunit` carregado.
- Head funcional final pré-documentação: `cdc0e8ed9326cd7f3c45e7b2c0fc461318ebf122`.
  - RPG Skill Tree CI `33984367404` / run #3444: GREEN completo, incluindo Core, JUnit 5, NeoForge-loaded JUnit, NeoForge GameTests, validators, build, JAR verification e dedicated-server smoke.
  - SonarQube Cloud `33984367363` / run #680: GREEN; Quality Gate PASSED, **100,0% Coverage on New Code**, 0 New issues, 0 Security Hotspots e 0,0% Duplication on New Code.
  - CodeQL Security `33984367319` / run #477: GREEN, sem novos alerts no código alterado pela PR #405.

**Acceptance: satisfied.** Uma recompensa de quest autorizada usa as mesmas mutation boundaries do Core; mastery chega ao mesmo `PlayerProgressionRuntime.awardMastery(...)`, preservando replay safety, reconciliação, persistência, eventos e sync canônicos, sem pipeline paralelo.