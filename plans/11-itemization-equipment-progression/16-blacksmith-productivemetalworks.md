# 11.16 — Blacksmith: equipamentos modulares sobre Productive Metalworks

## Status

**PLANEJADO — contrato de design para implementação futura.**

Ambiente-alvo:

- Minecraft `1.21.1`;
- NeoForge `21.1.x`;
- Java `21`;
- modlist anexada ao projeto em 2026-09-05/06: **607 mods**;
- `productivemetalworks-1.21.1-1.15.1.jar` — Productive Metalworks `1.21.1-1.15.1`;
- `productive-metalworks-kubejs-addon-1.0.0.jar` — Productive Metalworks KubeJS Addon `1.0.0`;
- Silent Gear / Silent Gear Metalworks / Silent Lib: **ausentes da modlist atual** e portanto não fazem parte desta arquitetura.

Este plano deve ser implementado **dentro do domínio de Itemização do RPG Skill Tree**, e não como uma segunda itemização concorrente.

---

## 1. Decisão arquitetural

O Blacksmith **não recria a smeltery/foundry do Tinkers Construct**.

A análise do Productive Metalworks 1.21.1 mostra que ele já cobre a metade física da metalurgia:

```text
item/minério
→ Foundry
→ metal/material líquido
→ alloying
→ Foundry Tap
→ Casting Table / Casting Basin
→ cast
→ item de saída
```

O código `MetalworksRegistrator` da branch `dev-1.21.1` registra a Foundry, tanks, drains, capacitors, taps, Casting Table/Basin, heating coils, fluidos e os casts base de ingot, nugget, gem, gear, rod e plate.

O próprio Productive Metalworks deliberadamente **não é um sistema de ferramentas e armas modulares**. O Blacksmith existe para preencher somente a lacuna posterior ao casting:

```text
Productive Metalworks
→ peça física produzida por casting
→ propriedades do material
→ tratamento/acabamento
→ montagem de peças
→ equipamento final modular
→ Stage 11 Itemization
→ encantamentos / Apotheosis / gems / sockets
```

### Authority congelada

| Domínio | Authority |
|---|---|
| Foundry multiblock | Productive Metalworks |
| combustível/calor da Foundry | Productive Metalworks |
| molten fluids suportados | Productive Metalworks/provider do material |
| alloying dentro da Foundry | Productive Metalworks |
| Casting Table/Basin/Tap | Productive Metalworks |
| consumo de fluido no casting | Productive Metalworks |
| casts e peças de equipamento adicionados pelo projeto | Blacksmith |
| identidade/composição das peças | Blacksmith |
| perfil mecânico de material para gear modular | Blacksmith, referenciando materiais reais dos providers |
| montagem do equipamento | Blacksmith |
| workmanship/tratamentos do equipamento | Blacksmith |
| identidade RPG, Rank, Item Power, Prefixos/Sufixos/Infixos | Stage 11 Itemization |
| enchanting | Minecraft/Apothic Enchanting/provider correspondente |
| Apotheosis affixes/gems/sockets | Apotheosis/Apothic + adapter do Stage 11.09 |
| moveset/Battle Mode/stamina quando aplicável | Epic Fight + adapters comprovados |
| Mastery/perks | RPG Skill Tree, por serviços canônicos |

**Regra:** integração não transfere authority. O Blacksmith nunca escreve diretamente internals da Foundry, nunca cria segunda barra de calor e nunca rerrola a identidade RPG.

---

## 2. Evidência técnica do Productive Metalworks

Fontes auditadas para este design:

- projeto oficial: <https://github.com/JDKDigital/productivemetalworks>;
- branch 1.21.1: `dev-1.21.1`;
- `MetalworksRegistrator.java`;
- `ItemCastingRecipe.java`;
- `changelog.txt` da linha 1.21.1;
- página oficial do Productive Metalworks no CurseForge.

### 2.1 Casting é suficientemente genérico para peças Blacksmith

`ItemCastingRecipe` trabalha com quatro dados centrais:

```text
cast
fluid
result
consume_cast
```

O `result` é um `ItemStack`. Portanto o contrato de integração preferido é **usar receitas de item casting do próprio Productive Metalworks para produzir as peças Blacksmith**, em vez de interceptar a Foundry ou duplicar o casting.

O changelog `1.21.1-1.10.0` registra explicitamente a remoção do limite sobre quais itens podem ser usados como cast na Casting Table. Isso permite casts próprios do Blacksmith sem substituir a máquina.

### 2.2 Regra de versão

O adapter deve ser validado inicialmente contra `1.21.1-1.15.1`. Mudança de versão que altere serializers, recipe types, capabilities, casting lifecycle ou data maps exige nova auditoria. Em drift incompatível, o adapter fica **fail-closed**; não cria crafting alternativo secreto para manter o recurso funcionando.

### 2.3 KubeJS

O `Productive Metalworks KubeJS Addon 1.0.0` pode ser usado para experimentação, geração de datapack ou manutenção de conteúdo do modpack, mas **não deve ser hard dependency do runtime Blacksmith** se o contrato puder ser expresso diretamente por recipes/data packs/NeoForge. O runtime principal deve depender apenas das APIs realmente necessárias.

---

## 3. Não objetivos

O Blacksmith **NÃO** deve:

- criar outra Foundry;
- criar outro tanque de molten metal;
- criar outro Foundry Tap;
- criar outra Casting Table/Basin equivalente;
- registrar duplicatas de fluidos molten já autoritativos;
- recriar alloying já realizado pelo Productive Metalworks;
- implementar um clone completo de Tinkers Construct;
- depender de Silent Gear;
- criar uma segunda raridade de item concorrente ao `ItemRank` do Stage 11;
- criar Prefixos/Sufixos/Infixos próprios;
- implementar outro sistema de gems/sockets;
- rerrolar itemização ao reparar, encantar, trocar gem, mover inventário ou relogar;
- criar segunda stamina, segundo sistema de combate ou moveset próprio sem adapter real;
- conceder Mastery por tick, throughput automático da Foundry ou produção AFK;
- inventar traits sem hook mecânico real.

---

## 4. Pipeline canônico de fabricação

```text
[1] matéria-prima real
    ↓
[2] Productive Metalworks: melting
    ↓
[3] Productive Metalworks: alloying, se aplicável
    ↓
[4] Productive Metalworks: casting usando cast Blacksmith
    ↓
[5] BlacksmithPartStack
    ↓
[6] shaping / tratamento / acabamento Blacksmith, quando a peça exigir
    ↓
[7] montagem server-authoritative
    ↓
[8] BlacksmithComposition persistida no equipamento final
    ↓
[9] ItemizationMutationAuthority.initialize(...) exatamente uma vez
    ↓
[10] projeção de stats do material + workmanship
    ↓
[11] enchanting / Apotheosis / gems / sockets como camadas externas permitidas
```

### Invariante crítica

**As peças não recebem Rank/Poder do Item/Prefixos/Sufixos/Infixos.** Elas carregam somente identidade física Blacksmith. A itemização RPG nasce no **equipamento final**, uma única vez. Isso evita rolar três identidades RPG numa espada de três peças e depois gerar uma quarta identidade ao montá-la.

---

## 5. Modelo de dados Blacksmith

O domínio deve ser data-driven e versionado.

### 5.1 `BlacksmithMaterialProfile`

Representa como um material real contribui para equipamento modular.

Campos mínimos planejados:

```text
id
schemaVersion
sourceMod
itemTags / itemIds permitidos
moltenFluidTags / moltenFluidIds quando aplicável
materialClass
repairIngredient
mechanicalProfile
allowedPartFamilies
traits[]
displayKey
```

`mechanicalProfile` deve expor fatores explícitos e bounded, por exemplo:

```text
durabilityFactor
attackDamageFactor
attackSpeedFactor
miningSpeedFactor
armorFactor
armorToughnessFactor
knockbackResistanceFactor
enchantabilityContribution
massFactor
```

Nem todo material usa todos os campos. Valores incompatíveis com determinada família são ignorados pelo resolver, não convertidos em bônus arbitrário.

### 5.2 Material não é provider inventado

O perfil Blacksmith referencia **um material que realmente existe no pack**. Antes de criar o catálogo de produção, a implementação deve reconciliar a modlist corrente e identificar o provider canônico de cada material.

Se ferro, aço ou bronze possuem várias variantes modded equivalentes, usar tags/unificação quando seguro. `Almost Unified` ou compatibilidade de receitas pode decidir o item preferido, mas não muda a identidade física pretendida do material.

Não congelar neste plano uma lista histórica de metais. O catálogo inicial deve ser produzido a partir da **modlist vigente no momento da implementação** e das receitas/fluidos realmente disponíveis.

### 5.3 `PartDefinition`

Define uma geometria/função de peça, não um material específico.

Campos:

```text
id
partFamily
castable
castItem
requiredFluidUnits
allowedMaterialClasses
statWeights
processRequirements
modelKey
displayKey
```

Famílias iniciais previstas:

- `BLADE`;
- `GUARD`;
- `GRIP`;
- `TOOL_HEAD`;
- `HANDLE`;
- `SPEAR_HEAD`;
- `HAMMER_HEAD`;
- `ARMOR_PLATE`;
- `ARMOR_LINING`;
- `BINDING`.

A implementação pode iniciar com subconjunto menor; a schema deve suportar expansão sem criar um item registrado por combinação material × peça.

### 5.4 `BlacksmithPartState`

Persistido no stack da peça:

```text
partDefinitionId
materialProfileId
schemaVersion
processState
workmanshipInput
provenance
```

`provenance` deve permitir diagnóstico de qual recipe/provider originou a peça sem transformar a proveniência em autoridade de stats.

### 5.5 Estratégia de itens

Preferir **um item registrado por forma/família de peça**, com material e estado em data components, em vez de criar `steel_blade`, `bronze_blade`, `iron_blade` etc. para cada combinação.

Exemplo:

```text
blacksmith:blade
  component material = blacksmith:steel

blacksmith:blade
  component material = blacksmith:bronze
```

A viabilidade exata de serializar o componente no `ItemStack result` das receitas Productive Metalworks deve ser provada por teste no primeiro lote de implementação. Se o serializer do provider limitar componentes customizados, usar recipe generation/adapter documentado — nunca criar uma segunda Casting Table como fallback.

### 5.6 `AssemblyDefinition`

Define como peças se tornam equipamento.

```text
id
outputArchetype
requiredSlots[]
optionalSlots[]
compatibilityRules
baseStats
statResolver
modelDefinition
providerTags
```

Exemplo:

```text
modular_sword
├── blade: BLADE
├── guard: GUARD
└── grip: GRIP
```

### 5.7 `BlacksmithComposition`

Persistida no equipamento final e imutável quanto às peças estruturais no MVP:

```text
assemblyDefinitionId
parts[]
resolvedMaterialIds[]
workmanship
processSummary
schemaVersion
```

A composição física é separada de:

- `ItemizationIdentity`;
- `ItemRank`;
- `ItemPower`;
- Prefixos/Sufixos/Infixos;
- enchantments;
- gems/sockets.

---

## 6. Stats por material e por peça

A resolução de stats precisa ser determinística, server-authoritative e idempotente.

### 6.1 Regra de contribuição

Cada `AssemblyDefinition` possui base stats. Cada slot de peça declara pesos de contribuição.

Exemplo conceitual para espada:

```text
BLADE
- alta contribuição para damage
- alta contribuição para durability
- contribuição moderada para mass/attack speed

GUARD
- baixa contribuição para damage
- contribuição para durability/defesa/estabilidade quando houver hook real
- contribuição de mass

GRIP
- contribuição alta para attack speed/controle
- contribuição moderada para durability
- contribuição baixa para damage
```

O resolver não deve usar uma média cega de todos os materiais. O material da lâmina precisa importar mais para dano que o material do cabo.

### 6.2 Projeção no Minecraft

Onde possível, projetar o resultado em mecanismos vanilla/NeoForge por stack:

- max durability;
- attribute modifiers;
- tool/mining behavior;
- repair ingredients;
- enchantability;
- armor/toughness/knockback resistance;
- tags/categorias necessárias para compatibilidade.

Quando uma propriedade não puder ser expressa por stack de maneira segura, o item customizado pode resolver a composição em runtime, sempre a partir do `BlacksmithComposition` persistido e sem criar um segundo estado derivado mutável.

### 6.3 Caps

Toda curva material → stat deve possuir limites de segurança data-driven. Multiplicadores extremos de mods externos não podem gerar dano, durabilidade, velocidade ou armadura não bounded por simples combinação de peças.

---

## 7. Traits de materiais

Traits são permitidos, mas **não são obrigatórios** para todo material.

Contrato:

- trait precisa ter `ResourceLocation` estável;
- precisa declarar hook real;
- precisa declarar event boundary e authority;
- não pode duplicar efeito de Prefixo/Sufixo/Infixo do Stage 11;
- não pode duplicar enchantment ou gem equivalente sem regra explícita;
- precisa declarar stacking/cap;
- precisa ser determinístico a partir da composição;
- se o hook não existir, o trait é `UNSUPPORTED`/fail-closed, não vira `+5% dano` genérico.

Exemplos de fantasia como “resiste a corrosão”, “conduz magia” ou “retém fio” **não entram em produção apenas pelo nome**. Primeiro deve existir uma mecânica consumidora real no pack.

---

## 8. Workmanship — qualidade do trabalho do ferreiro

Workmanship é uma propriedade da fabricação e **não é `ItemRank`**.

Persistir algo equivalente a:

```text
workmanshipScore
workmanshipBand
```

Faixas player-facing podem usar terminologia própria, por exemplo:

- Execução Imperfeita;
- Execução Padrão;
- Execução Precisa;
- Execução Magistral;
- Obra-prima de Forja.

Não usar `Comum/Raro/Épico/Lendário` para workmanship, pois esses nomes pertencem ao Rank do Stage 11.

### 8.1 Cálculo

O score deve resultar de fatores reais e discretos:

- recipe/process concluído;
- qualidade/estado das peças;
- tratamentos aplicados;
- bônus de perks/masteries autorizados;
- ferramentas/estação quando possuírem contrato real.

Nunca gerar qualidade por “ficar parado perto da forja”, tempo online ou spam de abrir/fechar interface.

### 8.2 Relação com stats

Workmanship pode aplicar uma curva bounded sobre os stats físicos resolvidos. Os números finais são balanceamento data-driven e devem ser fechados por testes; o plano não autoriza multiplicadores ilimitados.

---

## 9. Tratamentos e acabamento

O MVP não simula uma segunda temperatura metalúrgica contínua.

A temperatura da Foundry continua pertencendo ao Productive Metalworks. Blacksmith pode representar **estados discretos de processo** após o casting:

```text
CAST
→ SHAPED
→ HEAT_TREATED
→ FINISHED
```

Tratamentos opcionais podem incluir têmpera, revenimento, afiação, polimento ou equivalentes **somente como recipes/transições explícitas** com ingredientes/condições reais.

### Regras

- nenhuma transição acontece por tick AFK;
- cada recipe consome exatamente os ingredientes declarados;
- nenhum tratamento duplica metal;
- aplicar o mesmo tratamento novamente deve ser idempotente ou explicitamente rejeitado;
- processo inválido não destrói silenciosamente a composição;
- não assumir que a temperatura atual da Foundry pode ser lida como “temperatura da lâmina” sem API comprovada.

---

## 10. Casts próprios

Adicionar casts reutilizáveis para as peças metálicas necessárias.

Candidatos iniciais:

- Blade Cast;
- Guard Cast;
- Pickaxe Head Cast;
- Axe Head Cast;
- Shovel Head Cast;
- Hoe Head Cast;
- Spear Head Cast;
- Hammer Head Cast;
- Armor Plate Cast.

Cada cast deve usar o `item_casting` do Productive Metalworks.

Padrão:

```text
cast = blacksmith:<shape>_cast
fluid = tag/fluido molten do material
result = peça Blacksmith com material/proveniência
consume_cast = false
```

`consume_cast=true` só pode existir para casts descartáveis explicitamente projetados.

### Conteúdo não metálico

Grip, handle, lining e bindings podem vir de crafting/processos próprios do material. Não forçar madeira, couro, fibra ou materiais equivalentes a passarem pela Foundry.

---

## 11. Estação Blacksmith

Adicionar **uma estação própria de montagem/acabamento**, não outra fundição.

Nome de design: `Blacksmith Workbench` / **Bancada de Ferreiro**.

Responsabilidades:

- validar peças compatíveis;
- aplicar tratamentos discretos que pertençam ao Blacksmith;
- visualizar preview de composição;
- montar o equipamento final;
- mostrar material de cada peça e contribuição prevista;
- executar a transação final de maneira atômica.

Não é responsabilidade da bancada:

- derreter metal;
- armazenar molten fluids;
- alloying;
- substituir Casting Table/Basin;
- encantar;
- reforging Apotheosis.

### Atomicidade

Montagem final deve ser `validate → resolve → commit`. Se qualquer etapa falhar, não consumir metade das peças nem gerar equipamento parcial.

---

## 12. Equipamentos e rollout

### Fase 1 — ferramentas/armas vanilla-equivalentes

Primeiro fechamento deve cobrir arquétipos com contratos Minecraft claros:

- sword;
- axe;
- pickaxe;
- shovel;
- hoe.

Objetivo: provar casting, composição, durabilidade, atributos, mineração, repair e enchanting sem depender de movesets exóticos.

### Fase 2 — armas adicionais

- spear;
- hammer/warhammer;
- outros arquétipos apenas quando o Epic Fight/compat possuir classificação segura.

Sem mapping limpo, o item não recebe moveset inventado. O adapter fica fail-closed ou usa somente um preset genérico comprovadamente correto.

### Fase 3 — armaduras

- helmet;
- chestplate;
- leggings;
- boots;
- plates + lining/binding conforme schema.

Armaduras entram somente após o resolver provar armor, toughness, durability, enchanting e renderização sem duplicação de modifiers.

---

## 13. Epic Fight

A modlist atual possui Epic Fight `21.17.3.1`.

Regras:

- Blacksmith não é authority de Battle Mode;
- Blacksmith não cria uma segunda stamina;
- attack speed/damage vanilla do item podem ser derivados da composição;
- weight/stamina/impact/armor negation do Epic Fight só são alterados se existir API/adapter concreto para a versão instalada;
- armas vanilla-equivalentes devem preservar tags/categorias que permitem classificação previsível;
- novos archetypes exigem teste de moveset, animação, hitbox e dedicated server;
- ausência de adapter desabilita somente a integração Epic Fight dependente, nunca o item físico nem a itemização base.

---

## 14. Integração com Stage 11 Itemization

O Blacksmith é **upstream físico** da itemização RPG.

```text
BlacksmithComposition
→ equipamento final
→ Stage 11 generation boundary
→ ItemizationIdentity + ItemPower + ItemRank + modifiers RPG
```

### Regras obrigatórias

- `ItemizationMutationAuthority.initialize(...)` no máximo uma vez por nova instância final;
- a composição física não é rerrolada pela itemização;
- o Rank não reescreve materiais;
- Prefixos/Sufixos/Infixos não alteram provenance das peças;
- reparo preserva `ItemizationIdentity` e `BlacksmithComposition`;
- encantamento preserva ambos;
- socket/gem preserva ambos;
- save/load, drop/pickup, container e dimension change preservam ambos.

### Part replacement

No MVP, **não existe troca estrutural livre de peça em equipamento já montado**. Isso evita usar uma arma com rolls RPG excelentes como recipiente permanente para trocar materiais sem custo/identidade.

Se desmontagem/reconstrução for adicionada futuramente, a política padrão é gerar **nova instância final e nova ItemizationIdentity**, salvo aprovação explícita de uma operação de evolução que preserve identidade.

---

## 15. Enchanting e Apotheosis

O objetivo é permitir que equipamento Blacksmith continue participando do ecossistema de encantamento do pack.

### 15.1 Vanilla/Apothic Enchanting

O equipamento final deve expor categoria, tags e enchantability compatíveis com seu arquétipo. Material e workmanship podem contribuir para enchantability somente por resolver determinístico e bounded.

Testar:

- enchanting table/provider instalado;
- anvil;
- livros encantados;
- repair;
- remoção/transferência de encantamento quando o pack fornecer essa mecânica.

### 15.2 Apotheosis

A integração é responsabilidade do **Stage 11.09**, não de um segundo sistema dentro do Blacksmith.

Blacksmith deve fornecer equipamento classificável. O adapter 11.09 deve validar, na versão exata instalada:

- loot/gear category;
- affixes;
- reforging;
- sockets;
- gems;
- salvaging quando pertinente.

### Invariantes

- reforge nunca troca material de lâmina/cabo/placa;
- reforge nunca altera workmanship;
- reforge nunca rerrola `ItemizationIdentity`;
- socket/gem nunca dispara nova montagem;
- se Apotheosis não reconhecer determinada classe dinâmica de item, corrigir por category/data map/adapter documentado; não criar item duplicado só para passar na Forge.

---

## 16. Repair, salvage, desmontagem e anti-dupe

### 16.1 Repair

Repair deve usar material/ingrediente autorizado pelo `BlacksmithMaterialProfile` e preservar:

- composição;
- workmanship;
- Stage 11 identity;
- enchantments;
- gems/sockets permitidos pelo provider.

### 16.2 Não derreter equipamento composto diretamente no MVP

Uma espada pode possuir aço + bronze + madeira. Se o recipe de melting do provider produzir apenas um output fluido por operação, tentar “devolver tudo” diretamente é terreno para perda ambígua ou duplicação.

Política MVP:

- equipamento final modular **não recebe recipe genérica de melting**;
- salvaging/desmontagem passa por pipeline explícito;
- peças recuperadas podem ser recicladas individualmente quando houver recipe segura;
- retorno de material é menor ou igual ao input econômico original;
- nenhuma rota `montar → desmontar → derreter → recastar` pode aumentar recursos.

### 16.3 Stage 11.12

Quando o salvaging universal do Stage 11 for implementado, ele deve reconhecer BlacksmithComposition e preservar a regra de uma única autoridade econômica. Não executar salvaging do Stage 11 e desmontagem Blacksmith em paralelo sobre o mesmo item.

---

## 17. Data packs e diretórios

Estrutura sugerida:

```text
data/<namespace>/blacksmith/materials/*.json
data/<namespace>/blacksmith/parts/*.json
data/<namespace>/blacksmith/assemblies/*.json
data/<namespace>/blacksmith/treatments/*.json
data/<namespace>/blacksmith/traits/*.json
data/<namespace>/recipes/productivemetalworks/...  # recipes de casting geradas/curadas
```

Requisitos de reload:

- validar IDs duplicados;
- validar referências inexistentes;
- validar material sem provider/tag;
- validar part slot impossível;
- validar assembly sem saída;
- validar curva fora de caps;
- validar trait sem handler;
- reload inválido preserva snapshot anterior válido;
- nenhuma reconciliação muda composição de equipamentos já existentes sem migração de schema explícita.

---

## 18. Rendering, tooltip e UX

Tooltip precisa mostrar claramente duas camadas diferentes:

```text
COMPOSIÇÃO BLACKSMITH
Lâmina: Aço
Guarda: Bronze
Empunhadura: Carvalho
Execução: Magistral
Tratamento: ...

ITEMIZAÇÃO RPG
Rank: ...
Poder do Item: ...
Prefixos: ...
Sufixos: ...
Infixos: ...

OUTROS SISTEMAS
Encantamentos
Sockets/Gems
```

Isso impede que o jogador confunda material, workmanship, Rank e affix.

Todo texto próprio deve possuir `pt_br` completo. IDs técnicos permanecem em inglês estável.

### Visual das peças

A implementação deve evitar um modelo/item registrado por material. Preferir composição visual data-driven, tint/model layers ou renderer equivalente. A técnica final deve ser escolhida após prova em 1.21.1, sem tornar renderer client-side authority do material.

---

## 19. API pública e integração com perks/Mastery

Expor boundaries próprios, sem permitir escrita direta nos componentes.

Candidatos:

```text
BlacksmithCompositionQueryService
BlacksmithMaterialCatalog
BlacksmithAssemblyCatalog
BlacksmithCraftSnapshot
BlacksmithCraftCompletedEvent
```

`BlacksmithCraftCompletedEvent` só existe após commit server-side bem-sucedido.

### Mastery

Se perks/masteries de metalurgia consumirem o sistema:

- uma fabricação causal = no máximo um evento elegível;
- deduplicar por `operationId`/identidade de output;
- não recompensar tick de Foundry;
- não recompensar FE/t, fluid throughput ou quantidade de casts por segundo;
- automação sem player causal não concede Mastery por padrão;
- preferir milestones: primeiro material legítimo, primeira família de peça, primeiro assembly distinto, primeiro tratamento aprovado;
- Mastery é gravada pelo serviço canônico do RPG Skill Tree, nunca por uma barra Blacksmith paralela.

As perks existentes de especialização metalúrgica precisam ser auditadas posteriormente contra estes boundaries; este plano **não altera silenciosamente contratos de perks já aprovados**.

---

## 20. Optional-mod boundary e fail-closed

O pacote de domínio Stage 11 não pode importar classes do Productive Metalworks.

Adapter sugerido:

```text
dev.gustavopere.rpgskilltree.itemization.compat.productivemetalworks
```

ou outra fronteira de integração equivalente já padronizada pelo Stage 06/11 no momento da implementação.

### PM ausente

- RPG Skill Tree inicia normalmente;
- Stage 11 continua funcionando para outros equipamentos;
- Blacksmith casting fica indisponível;
- nenhum fallback cria Foundry própria;
- diagnostics informa provider ausente.

### PM incompatível

Se recipe serializer/type/capability esperado não existir:

- desabilitar o adapter;
- registrar diagnóstico estruturado;
- não consumir materiais;
- não produzir peça por crafting alternativo oculto.

---

## 21. Estratégia de implementação

### 16.A — Contratos e schema

- [ ] tipos `MaterialProfile`, `PartDefinition`, `PartState`, `AssemblyDefinition`, `BlacksmithComposition`;
- [ ] codecs/data components;
- [ ] validators;
- [ ] immutable/read-only query boundary;
- [ ] migration/schema version.

### 16.B — Adapter Productive Metalworks

- [ ] gate exato para `1.21.1-1.15.1` inicialmente;
- [ ] casts Blacksmith;
- [ ] receitas `item_casting`;
- [ ] prova de result stack com material/proveniência;
- [ ] consume/preserve cast;
- [ ] optional classloading boundary.

### 16.C — Catálogo de materiais

- [ ] reconciliar modlist corrente;
- [ ] identificar providers/tags/fluidos reais;
- [ ] unificação;
- [ ] mechanical profiles;
- [ ] repair ingredients;
- [ ] traits somente com hooks reais.

### 16.D — Peças e composição

- [ ] peças metálicas castáveis;
- [ ] peças não metálicas por recipe apropriada;
- [ ] stat weights por slot;
- [ ] resolver determinístico/caps;
- [ ] tooltips de preview.

### 16.E — Workmanship e tratamentos

- [ ] score/faixas distintas de Rank;
- [ ] shaping/treatment/finish recipes;
- [ ] idempotência;
- [ ] hooks para perks sem gerar recursos.

### 16.F — Bancada e assembly

- [ ] menu server-authoritative;
- [ ] transação atômica;
- [ ] preview não mutável;
- [ ] output final com composition persistida.

### 16.G — Ferramentas/armas MVP

- [ ] sword;
- [ ] axe;
- [ ] pickaxe;
- [ ] shovel;
- [ ] hoe;
- [ ] vanilla tags/repair/enchantability;
- [ ] Epic Fight smoke para categorias relevantes.

### 16.H — Stage 11

- [ ] nova gear é classificada pelo 11.02;
- [ ] identidade nasce uma única vez pelo generation pipeline;
- [ ] modifiers físicos e RPG não double-apply;
- [ ] save/load/copy/evolution preservam contratos.

### 16.I — Enchanting/Apotheosis

- [ ] enchanting/anvil;
- [ ] Apothic Enchanting;
- [ ] category/affix;
- [ ] reforge sem reroll físico/RPG;
- [ ] sockets/gems;
- [ ] ausência do provider fail-soft.

### 16.J — Repair/salvage

- [ ] repair por material;
- [ ] salvaging único;
- [ ] desmontagem segura;
- [ ] recycling sem dupe.

### 16.K — Armaduras e arquétipos avançados

- [ ] armor composition;
- [ ] renderer;
- [ ] Epic Fight/custom weapon adapters somente quando comprovados.

### 16.L — UX, JEI e documentação

- [ ] recipes aparecem corretamente;
- [ ] casts e materiais pesquisáveis;
- [ ] tooltip PT-BR;
- [ ] guia in-game/ponder quando tecnicamente apropriado;
- [ ] diagnostics para material/provider inválido.

---

## 22. Matriz mínima de testes

### Unitários

- material profile codec/validation;
- contribution weights;
- deterministic stat resolution;
- workmanship caps;
- assembly compatibility;
- invalid trait/material/part fail-closed;
- schema migration;
- immutable query snapshots.

### Productive Metalworks provider-present

- Foundry/casting aceita cada cast Blacksmith MVP;
- fluido correto produz peça/material correto;
- fluido incorreto não produz;
- quantidade consumida é exata;
- cast reutilizável é preservado;
- cast consumível, quando existir, é consumido uma vez;
- automação não duplica output;
- reload/chunk reload não duplica peça.

### Assembly

- peças válidas geram exatamente um output;
- peças inválidas não são consumidas;
- composição final persiste;
- mesma composição resolve mesmos stats;
- output recebe Stage 11 identity uma única vez;
- falha entre validate/commit não cria item órfão.

### Lifecycle

- save/load;
- drop/pickup;
- container;
- dimension change;
- repair;
- enchanting;
- smithing permitido;
- copy real segue `ItemizationIdentityPolicy`.

### Apotheosis

- enchanting funciona;
- affix/category quando suportado;
- reforge não altera material/workmanship/ItemizationIdentity;
- gems/sockets não rerrolam;
- sem double modifier.

### Epic Fight

- sword/axe vanilla-like reconhecidos corretamente;
- atributos não são aplicados duas vezes;
- ausência de adapter não quebra servidor;
- armas avançadas sem mapping ficam fail-closed para integração específica.

### Anti-dupe

- cast extraction durante operação;
- hopper/capability automation;
- montagem/desmontagem;
- repair loop;
- salvage loop;
- melting de peças recuperadas;
- copy stack;
- death/drop/pickup;
- server restart.

### Matriz de ambiente

- Productive Metalworks presente;
- Productive Metalworks ausente;
- Apotheosis presente/ausente;
- Epic Fight presente/ausente;
- dedicated-server smoke;
- datapack reload;
- build NeoForge;
- JAR verification;
- CI completo.

---

## 23. Performance

Proibido:

- varrer registry inteiro por tick;
- recalcular composição a cada frame como authority;
- consultar Foundry globalmente;
- procurar todos os jogadores para atribuir autoria;
- reconstruir catálogo a cada tooltip.

Preferir:

- snapshot imutável de catálogo por reload;
- stats materializados/reconciliados em fronteiras de lifecycle;
- caches bounded derivados de componentes persistidos;
- events discretos de craft/assembly;
- client apenas renderiza snapshot sincronizado.

---

## 24. Migração e compatibilidade futura

Cada estado persistido possui `schemaVersion`.

Mudança de fórmula não deve reescrever silenciosamente equipamentos existentes se isso alterar sua identidade material. Definir explicitamente por versão se:

1. o item mantém valores materializados antigos; ou
2. ocorre migração determinística e auditável.

Nunca reinterpretar `materialProfileId` removido como outro material “parecido”. ID ausente gera estado degradado/diagnóstico e fail-closed para mutações destrutivas.

---

## 25. Dependências internas do Stage 11

11.16 deve reutilizar, não substituir:

- `11.01` — domínio, invariantes e authority;
- `11.02` — classificação de equipamentos;
- `11.03` — identidade/persistência;
- `11.04` — Rank/Item Power;
- `11.06` — generation pipeline;
- `11.07` — modifier runtime;
- `11.08` — crafting/machine outputs;
- `11.09` — Apotheosis;
- `11.11` — equipamentos tecnológicos/adapters quando houver interseção;
- `11.12` — salvaging;
- `11.13` — PT-BR/UI;
- `11.14` — migration;
- `11.15` — testing/performance/hardening.

A implementação completa de 11.16 deve ocorrer depois que as fronteiras necessárias desses subplanos existirem ou trabalhar em branch sem declarar fechamento até os contratos upstream estarem canônicos.

---

## 26. Definition of Done

- [ ] nenhum sistema físico já fornecido pelo Productive Metalworks foi duplicado;
- [ ] adapter validado contra a versão exata instalada;
- [ ] casts Blacksmith usam o casting do provider;
- [ ] peças persistem part/material/provenance sem item explosion por material;
- [ ] catálogo de materiais deriva da modlist/provider reais;
- [ ] stats são determinísticos, bounded e ponderados por função da peça;
- [ ] traits sem hook real são rejeitados;
- [ ] workmanship é mecanicamente e visualmente distinto de ItemRank;
- [ ] assembly é atômico e server-authoritative;
- [ ] peças não recebem itemização RPG;
- [ ] equipamento final recebe ItemizationIdentity uma única vez;
- [ ] repair/enchant/gem/socket não rerrolam composição nem itemização;
- [ ] integração Apotheosis é validada pelo 11.09;
- [ ] integração Epic Fight não inventa moveset/stamina;
- [ ] salvaging/recycling não duplica recursos;
- [ ] optional-mod boundaries passam sem classloading indevido;
- [ ] PT-BR completo;
- [ ] datapack reload seguro;
- [ ] unit tests verdes;
- [ ] NeoForge GameTests verdes;
- [ ] provider-present integration tests verdes;
- [ ] build NeoForge verde;
- [ ] dedicated-server smoke verde;
- [ ] CI GREEN;
- [ ] documentação e status reconciliados.

## Acceptance

**SATISFIED somente quando** o RPG Skill Tree conseguir produzir equipamento modular a partir da metalurgia real do Productive Metalworks sem recriar a Foundry, preservando a composição física do item, integrando-a ao Stage 11 uma única vez e mantendo enchanting/Apotheosis/Epic Fight como providers externos com authority explícita, testes de anti-duplicação e comportamento fail-closed.
