# Skill Tree Plan — Effect Resolution

**Status:** concluído e validado.

**Goal:** resolver efeitos inline, packs externos e comportamentos de forma determinística.

- [x] Definir precedência/composição entre `bonuses`, `node_effects` e handlers comportamentais.
- [x] Gerar IDs estáveis de modifiers por nó/rank/origem.
- [x] Aplicar efeitos exatamente uma vez após login/reload/compra.
- [x] Remover efeito quando requisito deixa de valer.
- [x] Proteger efeitos de mods opcionais quando o provider estiver ausente.
- [x] Testar operações flat, percent-base e multiply-total.

**Acceptance:** a mesma build sempre resolve o mesmo conjunto final de efeitos, sem acumulação.

## Contrato canônico fechado

### Precedência e autoridade

A ordem canônica é:

1. `bonuses` inline da Passive Skill Tree são somente apresentação/exportação e **não são autoridade de gameplay**;
2. `node_effects.attributes` é a autoridade server-side para modificadores numéricos persistentes derivados de perks;
3. handlers de `node_effects.behaviors` reconciliam comportamentos server-side depois dos modificadores numéricos, sempre a partir do mesmo `ProgressionState` autoritativo.

`skills/*.json` com `bonuses` não vazio falha fechado no loader. Isso impede a existência de dois motores concorrentes para o mesmo bônus.

### Identidade estável

`NodeEffectIdPolicy` gera fallback IDs determinísticos e Java-puros quando `effectId` é omitido. A origem do resource/datapack participa da identidade, impedindo colisões silenciosas entre packs independentes.

A identidade persistente de modifier usa **origem + nó + alvo lógico/operação**, enquanto o rank atual participa do valor/estado resolvido. O rank é deliberadamente **não incorporado ao ID persistente**: gerar um ID diferente por rank deixaria modifiers antigos órfãos e permitiria acumulação ao subir/descer rank. Assim, mudança de rank atualiza o mesmo efeito estável em vez de criar outro.

`effectId` explícito continua sendo preservado. IDs duplicados, inclusive colisões entre efeitos de atributo e comportamento, invalidam atomicamente o candidato.

### Publicação e reload

`PreparedSkillTreeData` e `SkillTreeDataSnapshot` carregam efeitos de atributo e comportamento dentro da mesma publicação autoritativa. `SkillTreeDataCatalog.publish(...)` valida a unidade completa antes de instalar os projections live.

Efeitos de atributo mantêm `clearableAttributeEffects` históricos para remover modifiers que desapareceram ou mudaram no datapack. Efeitos comportamentais são reconciliados contra o conjunto realmente aplicado na sessão do jogador, portanto um behavior removido do catálogo gera remoção do estado previamente aplicado.

Reload inválido preserva a revisão anterior. Reload válido publica a nova revisão e executa `NodeEffectRuntime.refresh(...)` para todos os jogadores online.

### Runtime único

`NodeEffectRuntime` é o boundary canônico de efeitos:

```text
ProgressionState autoritativo
        ↓
NodeEffectRuntime.refresh
        ├── AttributeNodeEffectRuntime
        └── BehaviorNodeEffectRuntime
```

`PlayerProgressionRuntime` usa esse boundary após commits de progressão aceitos e também o executa na reconciliação de login/reconnect mesmo quando o estado persistido não mudou. Compra, respec, reconciliação de nós inválidos e reload convergem na mesma rota.

Logout invalida a sessão comportamental do jogador e `ServerStoppedEvent` limpa todas as sessões em memória.

### Modificadores de atributo

O runtime mapeia explicitamente:

- `ADD_FLAT` → `AttributeModifier.Operation.ADD_VALUE`;
- `ADD_PERCENT_BASE` → `AttributeModifier.Operation.ADD_MULTIPLIED_BASE`;
- `MULTIPLY_TOTAL` → `AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL`;
- `OVERRIDE` → rejeitado para node attribute effects.

Antes de reaplicar, modifiers clearable antigos são removidos. A aplicação usa `addOrUpdateTransientModifier`, de modo que refresh repetido com a mesma build não empilha o mesmo efeito.

Mudanças de `max_health` preservam a proporção de vida já existente.

### Behaviors e providers opcionais

`NodeBehaviorHandlerRegistry` registra handlers explicitamente e rejeita registro duplicado. `BehaviorNodeEffectRuntime` mantém o conjunto aplicado por sessão de `ServerPlayer` e usa `NodeBehaviorEffectReconciler` para produzir somente as remoções/aplicações necessárias.

Garantias:

- refresh idêntico não reaplica behavior;
- mudança de rank remove o estado anterior e aplica uma vez o novo rank;
- perk removida/inválida remove o behavior uma vez;
- nova instância de `ServerPlayer` com o mesmo UUID recebe aplicação fresca;
- handler ausente ou provider opcional indisponível é fail-soft e não derruba o servidor.

Atributos pertencentes a mods opcionais também são fail-soft: registry target ou player attribute ausente produz diagnóstico estruturado e o efeito é ignorado.

## Cobertura automatizada

Cobertura principal adicionada:

- `EffectResolutionContractJUnitTest`;
- `InlineBonusAuthorityJUnitTest`;
- `NodeEffectDataContractJUnitTest`;
- `BehaviorEffectPublicationJUnitTest`;
- `BehaviorEffectReconciliationJUnitTest`;
- `NodeBehaviorHandlerRegistryJUnitTest`;
- `BehaviorNodeEffectGameTests`;
- `AttributeModifierGameTests`;
- `scripts/verify-node-effect-runtime.py` no CI canônico.

O GameTest de atributos executa sequencialmente as três operações sobre um `AttributeInstance` real:

- `ADD_FLAT`: valor absoluto por rank;
- dois `ADD_PERCENT_BASE` de `+10%`: resultado `1,20×` da base;
- dois `MULTIPLY_TOTAL` de `+10%`: resultado `1,21×` da base.

Cada caso também cobre refresh repetido sem stacking, remoção até o baseline e re-aplicação determinística.

## Evidência TDD / CI

Ciclos relevantes preservados no histórico do PR #194:

- CI #1751 / `33281481352` — RED: contratos de source/ID/behavior ainda ausentes;
- CI #1760 / `33281596242` — boundary arquitetural detectou dependência Minecraft indevida no `core`; corrigido para contrato Java-puro;
- CI #1766 / `33281676649` — Core/JUnit GREEN após correção do boundary;
- CI #1771 / `33281856043` — RED: `bonuses` inline não vazio ainda era aceito;
- CI #1772 / `33281973981` — GREEN do fail-closed de `bonuses`;
- CI #1774 / `33282051299` — RED: candidato ainda não transportava behaviors;
- CI #1805 — RED: executor comportamental ainda ausente;
- CI #1808 / `33285886378` — GREEN completo do executor comportamental;
- CI #1812 / `33286041273` — RED: invalidação de lifecycle ainda ausente;
- CI #1814 / `33286116594` — GREEN completo de lifecycle;
- CI #1816 / `33286250057` — RED arquitetural: facade unificado ainda ausente;
- CI #1827 / `33286697624` — GREEN completo do facade unificado e validators;
- CI #1845 / `33287243581` — GREEN completo da aceitação final, incluindo as três operações de modifier, Core, JUnit, NeoForge GameTests, validators, build, verificação do JAR e dedicated-server smoke. Os workflows Foundation/Compendium associados ao mesmo head também fecharam GREEN.

## Resultado

Stage 03.04 fechado funcionalmente. O sistema possui uma única fronteira autoritativa para efeitos de perks, identidade estável sem stacking por rank/reload/reconnect, cleanup determinístico, publicação atômica e integração fail-soft para providers opcionais.
