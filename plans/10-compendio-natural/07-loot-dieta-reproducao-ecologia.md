# 10.07 — Loot, dieta, reprodução e ecologia

## Objetivo

Enriquecer entradas com relações de gameplay verificáveis: drops, alimentos, itens de atração, reprodução, domesticação e ecologia. O sistema deve separar o que vem diretamente do jogo do que é editorial/inferido.

## Regras de confiança

- Loot deve vir de loot tables/APIs/runtime controlado, não de listas copiadas de wikis sem validação.
- Dieta/atração/reprodução devem usar tags, predicates/APIs ou adapters quando o comportamento não for introspectável genericamente.
- Relações ecológicas como predador/presa só são exibidas como fato quando existe comportamento ou documentação confiável; sem isso, ficam ausentes.
- Valores condicionais devem mostrar contexto: Looting, variante, biome, dificuldade, equipamento, player kill etc.

## Plano

### A — Resolver loot sem efeitos colaterais

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/CompendiumLootProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootSummary.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootConditionSummary.java
```

- [ ] resolver a loot table associada ao tipo quando o contrato for estável;
- [ ] nunca executar comandos/functions/efeitos arbitrários apenas para gerar documentação;
- [ ] sumarizar pools/entries suportadas;
- [ ] informar item, quantidade/range e chance somente quando matematicamente resolvível;
- [ ] quando chance depender de contexto complexo, mostrar `condicional` em vez de número falso;
- [ ] indicar efeito de Looting apenas se derivável;
- [ ] esconder tabelas vazias/indisponíveis de maneira clara.

### B — XP de morte

- [ ] distinguir XP vanilla base de recompensas do RPG;
- [ ] não prometer XP exato quando a entidade calcula valor dinamicamente por instância/contexto;
- [ ] adapter pode fornecer fórmula ou faixa com proveniência;
- [ ] bosses/custom mobs recebem dados próprios somente se verificados.

### C — Alimentos e atração

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/FoodRelationProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/TemptationRelationProvider.java
```

Capturar quando possível:

- alimento aceito;
- item que inicia reprodução;
- item que cura pet;
- item que atrai/tempt;
- food tag/predicate de origem.

Não assumir que `isFood` significa automaticamente reprodução ou atração.

### D — Reprodução

Informações desejadas:

- pode reproduzir;
- itens/condições necessários;
- idade/requisito adulto;
- cooldown;
- gestação quando um mod implementa sistema próprio e expõe API;
- resultado/filhote;
- herança/genes apenas por adapter confiável;
- incompatibilidades/sexo quando um mod realmente modela isso.

TFC/Animal Husbandry/Animal Wellness ou outros sistemas complexos presentes no pack devem receber adapter separado se a API permitir, sem hard dependency.

### E — Domesticação

Informações desejadas:

- domesticável ou não;
- item/método quando verificável;
- dono atual em inspeção de instância;
- comandos/estados relevantes;
- montaria/inventário apenas quando a entidade expõe esse comportamento.

### F — Ecologia de gameplay

Relações candidatas:

- predador -> presa;
- medo/evitação;
- polinização;
- habitat;
- hostilidade a grupos específicos;
- interação com plantas/blocos;
- relação com estrutura/ninho/colmeia;
- comportamento diurno/noturno quando verificável.

Cada relação deve carregar `FactSource` e `FactConfidence`.

### G — Cache e invalidação

- [ ] summaries de loot são construídos em reload/snapshot, não por frame;
- [ ] datapack reload invalida apenas cache dependente de dados;
- [ ] dados contextuais de instância não entram no cache global;
- [ ] recipe/loot registry reload não deixa facts obsoletos publicados.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/loot/LootSummaryTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/FoodRelationProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/BreedingProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/EcologyRelationTest.java
```

Casos obrigatórios:

- [ ] loot simples com item fixo;
- [ ] loot com range;
- [ ] loot condicionado a Looting/player kill;
- [ ] condição não suportada vira `condicional`, não chance inventada;
- [ ] animal com alimento/reprodução vanilla;
- [ ] entidade domesticável;
- [ ] mod opcional de husbandry presente/ausente;
- [ ] reload altera loot e o snapshot novo reflete a mudança;
- [ ] relações editoriais sem fonte falham validação quando marcadas como `EXACT`.

## Acceptance

O subplano fecha quando loot, dieta, reprodução, domesticação e relações ecológicas puderem enriquecer páginas sem executar efeitos perigosos nem apresentar inferências como fatos confirmados.
