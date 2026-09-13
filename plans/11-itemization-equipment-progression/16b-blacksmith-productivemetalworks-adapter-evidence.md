# Stage 11.16.B — Evidência de implementação do adapter Productive Metalworks

## Estado

**VALIDAÇÃO TÉCNICA CONFIRMADA — SINCRONIZAÇÃO FINAL COM MAIN EM VALIDAÇÃO**

Implementação validada no head `89e361b61ab08068711d8f2b5a8ef43f837ea0ba`, combinado pelo GitHub com `main@f23afcf14adfd5936991487980037f280683fb43` no merge-ref `a7709f67c625335b1c5b054e3c702d766fb14cf7`.

Durante o fechamento, a `main` avançou para `a0c1948c67b71cfff65b29a2c3b0ecea411bcd85` com a PR #446 (`feat(masteries): integrate Create engineering milestones`). Como essa integração tocou `OptionalIntegrations`, `RpgSkillTreeMod` e o smoke de providers opcionais, a PR #447 foi reconciliada explicitamente para preservar **CREATE e PRODUCTIVE_METALWORKS** no mesmo bootstrap/matriz canônica. O último CI deve validar essa combinação exata antes do merge.

Escopo deliberadamente limitado ao bloco `16.B — Adapter Productive Metalworks` do plano `16-blacksmith-productivemetalworks.md`. `16.C+` permanece fora desta PR e não foi iniciado.

## Ambiente auditado

- Minecraft `1.21.1`;
- NeoForge `21.1.248`;
- Java `21`;
- modlist do projeto em 2026-09-06: `607` entradas top-level;
- Productive Metalworks instalado: `productivemetalworks-1.21.1-1.15.1.jar` / runtime `1.21.1-1.15.1`;
- upstream Productive Metalworks `dev-1.21.1@6a39bb88626635844210e26f247135d371552cbd`.

## Autoridade preservada

Productive Metalworks continua autoridade para Foundry, calor/combustível, molten fluids, alloying, Casting Table/Basin/Tap e consumo de fluido no casting.

O Blacksmith adiciona somente:

- identidade dos casts próprios;
- identidade física das peças;
- Data Components de `BlacksmithPartState`;
- proveniência de provider/recipe;
- gate exato de compatibilidade.

Nenhuma classe `cy.jdkdigital.productivemetalworks.*` é importada pelo domínio Blacksmith ou pelo boundary opcional. O adapter depende do contrato data-driven `productivemetalworks:item_casting` e de metadata NeoForge.

## Evidência upstream do hook

No snapshot auditado:

1. `ItemCastingRecipe` serializa `result` com `ItemStack.CODEC` e `getResultItem(...)` devolve `result.copy()`;
2. `CastingBlockEntity` obtém esse `ItemStack` do recipe e o coloca diretamente no slot de saída;
3. `consume_cast=false` preserva o cast no ciclo normal do provider;
4. recipes oficiais usam `c:molten_iron` e a unidade canônica de ingot de ferro é `90` mB.

Consequência: Data Components customizados podem viajar no próprio `ItemStack result` sem interceptar internals da Foundry.

## Implementação desta PR

- `OptionalIntegrations.Provider.PRODUCTIVE_METALWORKS`;
- `ProductiveMetalworksVersionContract` aceitando exclusivamente `1.21.1-1.15.1`;
- `ExactModVersionCondition` + registry `rpgskilltree:exact_mod_version`;
- `BlacksmithItems.BLADE_CAST`;
- `BlacksmithItems.BLADE`;
- recipe `rpgskilltree:blacksmith/productivemetalworks/iron_blade`;
- resultado persistindo `rpgskilltree:blacksmith_part_state` com:
  - `part_definition_id=rpgskilltree:blade`;
  - `material_profile_id=rpgskilltree:iron`;
  - `process_state=CAST`;
  - `workmanship_input=0.0`;
  - proveniência do Productive Metalworks e do recipe;
- `consume_cast=false`;
- diagnóstico de runtime para provider presente com versão suportada ou incompatível.

## Fail-closed

A recipe possui condição NeoForge `rpgskilltree:exact_mod_version`. Ela só é carregada quando `productivemetalworks` está presente com versão exata `1.21.1-1.15.1`.

Provider ausente ou versão divergente não habilita recipe alternativo, não cria segunda Foundry e não converte o recurso em crafting vanilla substituto.

O servidor core-only também valida que `productivemetalworks=absent` é um estado legítimo do catálogo opcional e continua proibindo `ClassNotFoundException` / `NoClassDefFoundError` de adapters opcionais.

## TDD

Commit RED: `eadddc94e6faf632f82d0a126ef1a5c2fe337c9f`.

O primeiro build observável executou `1126` testes e falhou em exatamente três contratos novos:

- Productive Metalworks ainda ausente de `OptionalIntegrations`;
- `ProductiveMetalworksVersionContract` inexistente;
- recipe de prova inexistente.

A falha foi intencional e antecedeu o código de produção.

## Regressão encontrada e corrigida durante a validação

O primeiro head funcional chegou ao dedicated-server smoke com todas as etapas anteriores verdes, mas o smoke falhou após o servidor subir.

A causa raiz não era o serializer do Productive Metalworks nem o registro de `ICondition`: `scripts/verify-optional-provider-smoke.py` ainda mantinha a matriz canônica antiga de providers e terminava em `minecolonies`. Depois da inclusão de `PRODUCTIVE_METALWORKS`, o resumo correto passou a conter também `productivemetalworks=absent`, e o verificador rejeitava esse novo conjunto.

Correção aplicada:

- matriz do smoke atualizada com `productivemetalworks` na mesma ordem canônica de `OptionalIntegrations.Provider`;
- teste de regressão adicionado ao `ProductiveMetalworksAdapterContractTest`;
- workflow diagnóstico temporário usado na investigação e removido antes da validação final.

O rerun oficial `RPG Skill Tree CI` `34056616163` passou integralmente, inclusive `NeoForge dedicated-server smoke test`.

## Sincronização concorrente com Create

A PR #446 adicionou `OptionalIntegrations.Provider.CREATE`, seu bootstrap de Mastery e a entrada `create` no smoke core-only. A reconciliação da #447 preserva os dois lados:

- `CREATE` permanece na posição introduzida pela #446, após `COLD_SWEAT`;
- `PRODUCTIVE_METALWORKS` permanece no catálogo opcional, após `MINECOLONIES`;
- o smoke espera ambos na mesma ordem de `OptionalIntegrations.Provider`;
- o `RpgSkillTreeMod` preserva integralmente o bootstrap/diagnóstico Create e adiciona o bootstrap Blacksmith Productive Metalworks sem substituir o primeiro.

Nenhum arquivo ou comportamento da integração Create foi deliberadamente removido para resolver o conflito.

## Evidência de provider ausente e provider presente

No head validado antes do avanço concorrente da main:

- core-only dedicated server: verde com Productive Metalworks ausente e adapter fail-closed;
- `Foundation Optional Integrations` `34056616189`: verde;
- `Volcanoes Full Pack Compatibility Acceptance` `34056616198`: verde com o runtime completo do pack;
- full-pack exact-host GameTests: verdes;
- full-pack save/reload smoke: verde.

Isso cobre os dois limites relevantes da 16.B: provider opcional ausente e pack real com provider instalado. A combinação final com Create requer novo CI exato antes do merge.

## Matriz de CI do head de implementação validado

Todos os `25` workflows de pull request observados para `89e361b61ab08068711d8f2b5a8ef43f837ea0ba` concluíram com `success`, incluindo:

- `RPG Skill Tree CI` `34056616163`;
- `Foundation Optional Integrations` `34056616189`;
- `Foundation Bootstrap Contract` `34056616233`;
- `Foundation Diagnostics Contract` `34056616199`;
- `Stage 06.01 Adapter Contract` `34056616193`;
- `CodeQL Security` `34056616281`;
- `SonarQube Cloud` `34056616209`;
- `Volcanoes Full Pack Compatibility Acceptance` `34056616198`;
- `Volcanoes Worldgen Compatibility Matrix` `34056616231`;
- `Volcanoes Consolidated Release Readiness` `34056616164`.

Dentro do `RPG Skill Tree CI` passaram JUnit, NeoForge-loaded JUnit, NeoForge GameTests, provider-present GameTests, validações de dados/runtime, build NeoForge, verificação do JAR e dedicated-server smoke.

## Decisões que permanecem para 16.C+

- catálogo completo de materiais e aliases;
- catálogo completo de `PartDefinition` e quantidades finais de fluido por geometria;
- obtainability/cadeia final de fabricação de todos os casts;
- resolver de stats;
- workmanship e tratamentos;
- assembly server-authoritative;
- inicialização Stage 11 do equipamento final;
- projeção Apotheosis/Epic Fight;
- salvamento/reparo;
- armor;
- UX e modelos finais.

O `iron_blade` deste bloco é uma **prova de integração do serializer/casting**, usando uma unidade canônica de ingot (`90 mB`). Esse valor não congela o balanceamento final de `PartDefinition.requiredFluidUnits` do catálogo 16.C.

## Validação final

- [x] JUnit completo verde no head de implementação validado;
- [x] NeoForge JUnit adapter tests verdes no head de implementação validado;
- [x] GameTests verdes no head de implementação validado;
- [x] build NeoForge verde no head de implementação validado;
- [x] dedicated-server smoke verde no head de implementação validado;
- [x] full-pack exact-host GameTests verdes no head de implementação validado;
- [x] full-pack save/reload smoke verde no head de implementação validado;
- [x] CodeQL verde no head de implementação validado;
- [x] SonarQube verde no head de implementação validado;
- [x] matriz completa de 25 workflows do head de implementação verde;
- [ ] CI final da combinação com `main@a0c1948c67b71cfff65b29a2c3b0ecea411bcd85` verde;
- [ ] merge na `main`;
- [ ] confirmação do SHA final da `main`.

Nenhum bloco 16.C+ deve ser iniciado automaticamente após o fechamento desta PR.
