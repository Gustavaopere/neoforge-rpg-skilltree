# Stage 11.16.B — Evidência de implementação do adapter Productive Metalworks

## Estado

**EM IMPLEMENTAÇÃO — PR #447**

Base da branch: `main@b092592507640efe99bf5e7819ccd65cbd7e24a0`.

Escopo deliberadamente limitado ao bloco `16.B — Adapter Productive Metalworks` do plano `16-blacksmith-productivemetalworks.md`. `16.C+` permanece fora deste PR.

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

## Implementação deste PR

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

## TDD

Commit RED: `eadddc94e6faf632f82d0a126ef1a5c2fe337c9f`.

O primeiro build observável executou `1126` testes e falhou em exatamente três contratos novos:

- Productive Metalworks ainda ausente de `OptionalIntegrations`;
- `ProductiveMetalworksVersionContract` inexistente;
- recipe de prova inexistente.

A falha foi intencional e antecedeu o código de produção.

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

Pendente enquanto a PR #447 estiver aberta:

- [ ] JUnit completo verde;
- [ ] NeoForge JUnit adapter tests verdes;
- [ ] GameTests verdes;
- [ ] build NeoForge verde;
- [ ] dedicated-server smoke verde;
- [ ] workflows obrigatórios verdes;
- [ ] merge na `main`;
- [ ] confirmação do SHA final da `main`.
