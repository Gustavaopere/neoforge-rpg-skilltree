# Stage 11.16.C — Evidência do catálogo de materiais Blacksmith

## Estado

**IMPLEMENTAÇÃO 16.C PRESENTE — VALIDAÇÃO FINAL DA PR #455 EM ANDAMENTO**

Escopo deliberadamente limitado ao bloco `16.C — Catálogo de materiais` de `16-blacksmith-productivemetalworks.md`. O bloco `16.D — Peças e composição` não faz parte desta PR e não foi iniciado.

## Ambiente reconciliado

A implementação foi reconciliada novamente no início desta fase contra a modlist corrente e o Notion do projeto.

Providers relevantes confirmados:

- Productive Metalworks: `productivemetalworks-1.21.1-1.15.1.jar`, runtime `1.21.1-1.15.1`;
- Create: `create-1.21.1-6.0.10.jar`, runtime `6.0.10`;
- Create: The Factory Must Grow Community: `tfmg-1.21.1-1.2.4b-community.jar`, runtime `1.2.4b-community`;
- Create Metallurgy: `createmetallurgy-1.0.3-1.21.1.jar`, runtime `1.0.3`;
- Create: Metalwork: `createmetalwork-2.0.0.jar`, runtime `2.0.0`;
- Almost Unified: `almostunified-neoforge-1.21.1-1.4.2.jar`, runtime `1.21.1-1.4.2`.

Create Metallurgy e Create: Metalwork foram incluídos na auditoria porque continuam presentes na modlist atual, mesmo existindo registros históricos de possível remoção/sobreposição. A modlist corrente prevaleceu sobre histórico antigo.

## Evidência upstream

Productive Metalworks foi auditado no snapshot `JDKDigital/productivemetalworks@6a39bb88626635844210e26f247135d371552cbd` da linha `dev-1.21.1`.

O provider possui rotas/tag boundaries comprovados para o conjunto adotado, incluindo:

- `c:ingots/iron` ↔ `c:molten_iron`;
- `c:ingots/copper` ↔ `c:molten_copper`;
- `c:ingots/gold` ↔ `c:molten_gold`;
- `c:ingots/netherite` ↔ `c:molten_netherite`;
- `c:ingots/zinc` ↔ `c:molten_zinc`;
- `c:ingots/brass` ↔ `c:molten_brass`;
- `c:ingots/steel` ↔ `c:molten_steel`;
- `c:ingots/aluminum` ↔ `c:molten_aluminum`;
- `c:ingots/lead` ↔ `c:molten_lead`;
- `c:ingots/nickel` ↔ `c:molten_nickel`;
- `c:ingots/constantan` ↔ `c:molten_constantan`.

A release/tag `v1.2.4b` do TFMG Community foi confrontada com o JAR instalado e comprova os itens sólidos usados pelo catálogo: `tfmg:steel_ingot`, `tfmg:aluminum_ingot`, `tfmg:lead_ingot`, `tfmg:nickel_ingot` e `tfmg:constantan_ingot`.

Create fornece diretamente `create:zinc_ingot` e `create:brass_ingot`.

Create Metallurgy também registra uma variante própria de steel e possui fluidos adicionais. Isso não cria uma segunda identidade física Blacksmith: o perfil `rpgskilltree:steel` aceita a tag comum `c:ingots/steel`; o item concreto TFMG permanece o repair ingredient inicial do perfil. Alterar a preferência global do Almost Unified não cria um segundo perfil `steel`.

## Catálogo inicial aprovado pela 16.C

| Perfil Blacksmith | Provider primário | Item concreto/repair | Tag de item | Tag molten PM |
|---|---|---|---|---|
| `rpgskilltree:iron` | `minecraft` | `minecraft:iron_ingot` | `c:ingots/iron` | `c:molten_iron` |
| `rpgskilltree:copper` | `minecraft` | `minecraft:copper_ingot` | `c:ingots/copper` | `c:molten_copper` |
| `rpgskilltree:gold` | `minecraft` | `minecraft:gold_ingot` | `c:ingots/gold` | `c:molten_gold` |
| `rpgskilltree:netherite` | `minecraft` | `minecraft:netherite_ingot` | `c:ingots/netherite` | `c:molten_netherite` |
| `rpgskilltree:zinc` | `create` | `create:zinc_ingot` | `c:ingots/zinc` | `c:molten_zinc` |
| `rpgskilltree:brass` | `create` | `create:brass_ingot` | `c:ingots/brass` | `c:molten_brass` |
| `rpgskilltree:steel` | `tfmg` | `tfmg:steel_ingot` | `c:ingots/steel` | `c:molten_steel` |
| `rpgskilltree:aluminum` | `tfmg` | `tfmg:aluminum_ingot` | `c:ingots/aluminum` | `c:molten_aluminum` |
| `rpgskilltree:lead` | `tfmg` | `tfmg:lead_ingot` | `c:ingots/lead` | `c:molten_lead` |
| `rpgskilltree:nickel` | `tfmg` | `tfmg:nickel_ingot` | `c:ingots/nickel` | `c:molten_nickel` |
| `rpgskilltree:constantan` | `tfmg` | `tfmg:constantan_ingot` | `c:ingots/constantan` | `c:molten_constantan` |

Todos usam `materialClass=rpgskilltree:metal`, schema `1` e somente famílias metálicas: `BLADE`, `GUARD`, `TOOL_HEAD`, `SPEAR_HEAD`, `HAMMER_HEAD`, `ARMOR_PLATE`.

## Materiais deliberadamente fail-closed

Os seguintes materiais NÃO entram no catálogo 16.C:

- `bronze`: Productive Metalworks conhece molten bronze, mas esta auditoria não congelou um provider sólido/canônico do pack para o perfil inicial;
- `cast_iron`: TFMG possui item sólido, mas o Productive Metalworks auditado não expõe o boundary `molten_cast_iron` esperado;
- `tungsten`: Create Metallurgy possui o material, mas Productive Metalworks auditado não expõe `molten_tungsten`;
- `obdurium`: Create Metallurgy possui o material, mas Productive Metalworks auditado não expõe `molten_obdurium`;
- `lithium`: TFMG/Create Metallurgy possuem cadeia própria, mas Productive Metalworks auditado não expõe `molten_lithium`.

A ausência é intencional. 16.C não inventa conversão de fluido, não cria recipe alternativa secreta e não usa a fundição de outro provider como fallback do pipeline Productive Metalworks.

## Unificação

A autoridade de identidade física é o perfil Blacksmith, e não a variante de ingot escolhida por outro mod.

Regras implementadas:

1. `itemTags` usam `c:ingots/<material>` para aceitar equivalentes reais;
2. `moltenFluidTags` usam `c:molten_<material>` para falar com o boundary Productive Metalworks;
3. `itemIds` mantém o item primário auditado;
4. `repairIngredient` mantém um item concreto e determinístico;
5. não há duplicação `tfmg_steel` versus `createmetallurgy_steel` no domínio Blacksmith.

Almost Unified permanece livre para unificar receitas do pack; suas prioridades não são tratadas como authority da identidade Blacksmith.

## Mechanical profiles

Os fatores mecânicos não são valores declarados pelos providers. São coeficientes de balanceamento pertencentes ao Blacksmith, com baseline `iron = 1.0` e faixa inicial explicitamente bounded entre `0.25` e `1.35`.

Cada perfil declara todos os nove eixos:

- durability;
- attack damage;
- attack speed;
- mining speed;
- armor;
- armor toughness;
- knockback resistance;
- enchantability;
- mass.

A 16.C apenas congela o catálogo e os coeficientes. A projeção desses fatores por slot/assembly pertence à 16.D+ e não é realizada nesta PR.

## Traits

Todos os `traits` permanecem vazios.

Isso é deliberado: nenhum efeito de material é criado apenas por fantasia/nome. Um trait futuro exige hook mecânico real, boundary/authority, stacking/cap e teste; sem isso permanece `UNSUPPORTED`/fail-closed.

## Implementação

- `BlacksmithMaterialCatalog` fornece snapshot imutável e query por `ResourceLocation`;
- IDs duplicados são rejeitados;
- lookup ausente via `require(...)` falha explicitamente;
- `BlacksmithMaterialProfiles.auditedPackCatalog()` expõe o snapshot pack-audited;
- nenhuma classe Java de Create, TFMG, Create Metallurgy ou Productive Metalworks é importada pelo domínio;
- integração é feita somente por IDs/tags data-driven.

## TDD

RED foi criado antes do código de produção.

O workflow observável compilou `main` normalmente e falhou em `compileTestJava` com `cannot find symbol` para exatamente os novos boundaries ainda ausentes:

- `BlacksmithMaterialCatalog`;
- `BlacksmithMaterialProfiles`.

A mensagem apareceu dentro do passo de geração/check da wiki porque esse gerador invoca Gradle e compila os testes; o log confirma que não se tratava de drift de conteúdo da wiki.

GREEN implementa somente o mínimo necessário para satisfazer o contrato 16.C, seguido da ampliação auditada para zinc após a reconciliação de Create/Create Metallurgy.

## Critérios de fechamento

- [x] modlist + Notion reconciliados nesta fase;
- [x] providers/tags/fluidos do catálogo inicial auditados;
- [x] unificação por tags comuns sem duplicar identidade de material;
- [x] mechanical profiles explícitos e bounded;
- [x] repair ingredients concretos;
- [x] traits fail-closed sem hook inventado;
- [x] catálogo/query boundary imutável;
- [x] teste RED observado antes da produção;
- [ ] JUnit final verde;
- [ ] NeoForge/build/smokes finais verdes;
- [ ] matriz de workflows da PR verde;
- [ ] merge na `main`;
- [ ] `main` pós-merge confirmada.

Nenhum trabalho da 16.D deve ser iniciado automaticamente após o fechamento desta PR.
