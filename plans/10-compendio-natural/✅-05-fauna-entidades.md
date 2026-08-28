# 10.05 — Fauna, criaturas e análise de entidades

## Objetivo

Construir a camada técnica de entidades do Compêndio: qualquer `EntityType` registrado recebe uma página base útil automaticamente, enquanto inspeção de instância, dados especiais e scaling permanecem separados dos fatos universais da espécie.

## Contrato implementado

- `RuntimeEntityCatalogCollector` enumera `BuiltInRegistries.ENTITY_TYPE` e produz uma `CompendiumEntry` genérica para cada ID registrado sem construir arbitrariamente entidades apenas para obter metadata.
- `EntityRegistryDescriptor`, `EntityRegistryProvider`, `EntitySpeciesFacts` e `EntitySpeciesEntryFactory` mantêm identidade, namespace/mod de origem, translation key, `MobCategory`, categorias de gameplay, dimensões e atributos-base disponíveis em `DefaultAttributes`.
- Atributos ausentes são omitidos em vez de inventados como zero. Tipos técnicos registrados com hitbox zero-dimensional são aceitos; dimensões negativas, NaN ou infinitas continuam inválidas.
- `RuntimeCompendiumEntityCatalog` compara o catálogo candidato 1:1 com `ENTITY_TYPE` por `EntityCatalogCoverage` e só publica o snapshot depois da validação integral. A publicação ocorre uma vez em `ServerStartedEvent`, sem scan de entidades por tick.
- `EntityInstanceInspector` e `RuntimeEntityInstanceInspector` formam uma superfície separada de **instância atual**, com política server-side de distância máxima e line-of-sight, fatos whitelisted, estruturas imutáveis e sem serialização de NBT arbitrário.
- A inspeção de instância cobre, quando tecnicamente aplicável, vida atual/máxima, atributos presentes, idade/bebê, efeitos ativos, tame/owner/sit, estado de reprodução, AI desativada, invulnerabilidade, silêncio e leash.
- `EntityVariantSnapshot`, `EntitySpecialInspector`, `VanillaEntitySpecialInspectors` e `RuntimeVanillaEntitySpecialInspector` tratam especiais vanilla por adapters pequenos e fail-soft. Há branches públicas e tipadas para horse, panda, villager, bee, dolphin, goat e wandering trader; entidades sem adapter continuam com a página genérica funcional.
- Os adapters especiais não usam reflection, `CompoundTag`, `getPersistentData` ou classes client-only. Variantes são lidas apenas quando existe contrato público estável da entidade observada.
- `RpgEntityScalingCompendiumProvider` e `RuntimeRpgEntityScalingCompendiumAdapter` consomem o `EntityScalingSnapshot` canônico já persistido. Nível, raridade e arquétipo do RPG são expostos em seção `rpg_scaling`, separada de `base_stats`, sem recalcular o scaling dentro do Compêndio.
- O smoke do dedicated server aguarda tanto a publicação do catálogo quanto o relatório runtime do Compêndio, eliminando a corrida entre handlers de `ServerStartedEvent` e provando a inicialização real em ambiente server-only.

## Checklist de fechamento

- [x] todo `EntityType` registrado possui página técnica base e cobertura registry↔catálogo verificável;
- [x] metadata genérica é coletada por registry/default attributes sem spawn arbitrário de entidades;
- [x] fatos estáticos de espécie são separados dos fatos da instância atual;
- [x] atributos opcionais/ausentes e entidades modded não quebram o fallback genérico;
- [x] hitboxes técnicas zero-dimensionais são preservadas sem afrouxar validação para valores inválidos;
- [x] variantes/especiais usam contrato tipado e adapters fail-soft sem exigir instanciar todas as variantes;
- [x] inspeção exige política server-side de distância/line-of-sight e não expõe NBT arbitrário;
- [x] dados de tame/owner, bebê/idade, efeitos e estados contextuais suportados permanecem explicitamente dados de instância;
- [x] scaling do RPG usa `EntityScalingSnapshot` canônico e não mistura HP/atributos efetivos com estatística universal da espécie;
- [x] catálogo de entidades é validado integralmente e publicado atomicamente no startup, sem scan por tick;
- [x] ausência de adapter específico mantém a página base funcional;
- [x] runtime do dedicated server não depende de classes client-only;
- [x] regressões possuem gate focal próprio em `Compendium Entities CI` além do CI completo NeoForge.

## Escopo deliberadamente posterior

O 10.05 fecha a **camada técnica** de fauna/entidades, não todo o conteúdo editorial ou toda a UI do Compêndio. Catálogo completo de variantes conhecidas com progresso `n / total`, renderização/preview 3D e apresentação final pertencem ao 10.09. Corpus editorial pt-BR e textos curados pertencem ao 10.10. Relações de dieta, reprodução, loot e ecologia pertencem ao 10.07; biomas/estruturas/dimensões, ao 10.08. Adapters de mods que escondam dados em APIs próprias serão adicionados somente quando detectados e verificados, pelo contrato de extensibilidade do 10.11. Campos especiais adicionais sem API pública estável — por exemplo detalhes avançados de bosses, job sites/restocks/schedules ou relações internas específicas de mods — não são inferidos por reflection/NBT.

A inspeção atual fornece o modelo e a validação server-side; protocolo/UI final de solicitação e apresentação permanece no estágio de rede/UI correspondente. Isso não altera o Acceptance deste subplano, que exige página base universal, distinção espécie×instância e compatibilidade fail-soft de providers especiais.

## Evidência

- PR de implementação: **#76**.
- Head final pré-merge: `02fee92c58e536f48f45630a7e71e777879255ce`.
- Merge de implementação na `main`: `33360ba2a44148ddce2d4f8c825066985eee9fb6`.
- CI focal pré-merge: `33212594468` / Compendium Entities #37 — GREEN.
- CI de descoberta pré-merge: `33212594475` / Compendium Discovery #110 — GREEN.
- CI completo pré-merge: `33212594473` / RPG Skill Tree #993 — GREEN, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.
- CI focal pós-merge: `33212930323` / Compendium Entities #38 — GREEN.
- CI de descoberta pós-merge: `33212930426` / Compendium Discovery #115 — GREEN.
- CI completo pós-merge: `33212930354` / RPG Skill Tree #998 — GREEN, incluindo todos os validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final do commit.
- Durante TDD/debugging, o smoke #987 revelou um `EntityType` técnico com dimensão zero; o focused CI #35 reproduziu a falha antes da correção e a regressão permanece coberta.

## Acceptance

**Acceptance: satisfied.** Todo `EntityType` registrado possui página técnica base validada contra o registry; fatos da instância atual são separados da espécie; scaling do RPG não contamina estatística-base; e adapters especiais permanecem opcionais, server-safe e fail-soft.
