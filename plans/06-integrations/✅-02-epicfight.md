# Integrations Complete — Epic Fight

**Goal:** integrar combate e atributos Epic Fight sem duplicar o pipeline vanilla.

- [x] Confirmar eventos autoritativos de ataque/hit.
- [x] Aplicar stamina, stamina regen e impact definidos pelos node effects.
- [x] Impedir fallback vanilla simultâneo.
- [x] Preservar autoria para perks once-per-hit.
- [x] Testar com Epic Fight presente e ausente.

## Runtime contract

- O provider auditado é `epicfight` `21.17.3.1`; `EpicFightVersionContract` e o adapter comum do Stage 06.01 ativam o runtime somente nessa versão exata e falham fechado nas demais.
- `EpicFightProgressionHooks` publica no `DELIVER_DAMAGE_PRE` uma autoria causal opaca por `DamageSource` + target. `AuthoritativeHitAttributionBridge` é provider-neutral e não importa tipos Epic Fight, mantendo o core classload-safe quando o mod está ausente.
- `A0081A0100CombatEvents` consulta essa autoria antes de criar seu root `sustain/...`; portanto um dano Epic Fight observado também pela lane NeoForge reutiliza o root provider em vez de criar autoria concorrente. Sem autoria provider, o fallback NeoForge permanece disponível.
- O callback `DELIVER_DAMAGE_POST` descarta a correlação depois que o pipeline de dano já pôde ser observado, e o server-stop limpa o estado residual. A chave externa é fraca e a correlação é separada por target.
- O fallback vanilla específico de `A0021A0040EpicFightHooks` continua rejeitando qualquer arma que o capability/category do Epic Fight tenha classificado; provider e fallback não executam simultaneamente para a mesma arma.
- `data/rpgskilltree/node_effects/epicfight.json` materializa os três targets canônicos exigidos por este estágio: `epicfight:stamina`, `epicfight:stamina_regen` e `epicfight:impact`. `AttributeNodeEffectRuntime` resolve os registries reais e não inventa substitutos quando um target não existe.
- Hooks provider-specific continuam confinados a `runtime/compat/epicfight`; o boundary comum de autoria aceita apenas `Object`, `UUID` e IDs internos.

## TDD e verificação

- RED inicial da PR #466: run `34111541437` falhou em `compileTestJava` com 9 referências ausentes a `EpicFightIntegrationContract` antes da produção existir.
- Uma implementação concorrente na PR #465 expôs um segundo RED útil no run `34137950747`: `A0081A0100HitAuthorityJUnitTest` exigia `resolveOutgoingRootActionId(...)`, ainda inexistente. A #465 foi fechada sem merge e a regressão foi consolidada na #466 junto do wiring provider-native completo.
- `EpicFightIntegrationContractJUnitTest` cobre presença/ausência, versão exata, exclusão mútua provider/fallback e verifica o recurso real `epicfight.json`.
- `AuthoritativeHitAttributionBridgeJUnitTest` cobre first-writer authority, reutilização, isolamento por target, descarte e fail-closed de publicação inválida.
- `A0081A0100HitAuthorityJUnitTest` prova que o observer NeoForge reutiliza o root Epic Fight e preserva o fallback quando não há autoria provider.
- Stage 06.02 focal GREEN no head de código: run `34138716592`.
- RPG Skill Tree CI GREEN no head de código: run `34138716413`, incluindo JUnit, NeoForge GameTests, validators, build/JAR e dedicated-server smoke.
- CodeQL GREEN: run `34138716602`.
- Battle Mage Epic Compatibility GREEN com stack Epic presente: run `34138716598`.
- A PR só pode ser mergeada após a matriz do head documental final também permanecer GREEN; o SHA final de merge é confirmado na `main` após o merge.

**Acceptance:** um golpe Epic Fight possui uma única autoria causal compartilhável pelo runtime RPG; observers NeoForge não criam root concorrente quando essa autoria existe, o fallback continua neutro sem provider e os três atributos Epic Fight requeridos estão materializados e validados.

**Estética:** N/A. Este estágio é integração server/common sem nova superfície player-facing.
