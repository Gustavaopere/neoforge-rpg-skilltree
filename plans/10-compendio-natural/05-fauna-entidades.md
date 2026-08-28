# 10.05 — Fauna, criaturas e análise de entidades

## Objetivo

Construir a camada técnica de entidades do Compêndio: qualquer entidade viva relevante deve receber uma página útil automaticamente, e adapters/corpus curado devem enriquecer espécies especiais sem quebrar suporte genérico.

## Dados mínimos por entidade

Quando tecnicamente disponíveis e verificáveis, a página deve conseguir apresentar:

### Identidade

- nome localizado em pt-BR;
- nome do mod de origem;
- `ResourceLocation` técnico;
- categoria enciclopédica;
- categoria vanilla de mob quando aplicável;
- variantes conhecidas/descobertas.

### Estatísticas

- vida máxima/base;
- vida atual somente em contexto de inspeção de uma instância;
- armadura;
- armor toughness quando aplicável;
- dano/attack damage quando o atributo existir;
- velocidade de movimento;
- velocidade de voo/nado apenas quando houver fonte confiável;
- knockback resistance;
- attack knockback;
- follow range;
- step height/jump quando acessível por contrato estável;
- largura/altura da hitbox;
- eye height quando útil;
- categoria/mob cap quando verificável;
- XP de morte quando resolvível sem executar efeitos colaterais.

Valores derivados de uma instância devem ser rotulados como **instância atual**, não como valor universal da espécie.

### Estado contextual de uma instância

Inspirado na utilidade técnica do Biology Dictionary, mas separado dos fatos de espécie:

- bebê/adulto e idade;
- efeitos ativos;
- dono/tame state;
- in love/breeding cooldown;
- AI ativa/desativada quando legível;
- persistência/despawn state;
- invulnerabilidade;
- silêncio;
- portal cooldown;
- leash state;
- target/anger somente se seguro e útil;
- dados especiais vanilla por tipo, por provider.

## Providers especiais vanilla

Não poluir `EntityProvider` genérico com `instanceof` infinito. Criar providers pequenos e testáveis para dados especiais, por exemplo:

- cavalo: variante, markings, domesticação e atributos relevantes;
- panda: genes quando expostos com segurança;
- aldeão: tipo, profissão, nível, workstation/job site, restock e schedule quando verificáveis;
- abelha: colmeia/hive relation e estado relevante;
- golfinho: moisture quando aplicável;
- cabra: screaming variant;
- wandering trader: despawn timer quando exposto;
- pets: owner/tame/sit state;
- bosses: barra/boss flags e dados próprios somente por adapter confiável.

## Plano

### A — Provider genérico de entidade

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityRegistryProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/LivingEntityAttributeProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityDimensionsProvider.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityInstanceInspector.java
```

- [ ] gerar página base para todo `EntityType` classificável;
- [ ] não instanciar arbitrariamente entidades perigosas apenas para coletar metadata se houver alternativa por registry/default attributes;
- [ ] cachear fatos de espécie;
- [ ] separar fatos estáticos de inspeção runtime de uma instância;
- [ ] tolerar entidade de mod com atributos não padrão.

### B — Classificação zoológica de gameplay

A classificação deve ser editorial/data-driven e não fingir taxonomia científica quando o mod não a define.

Categorias possíveis:

```text
passivo
neutro
hostil
boss
npc
animal_domesticavel
animal_criavel
aquatico
voador
artropode
morto_vivo
fantastico
construto
outro
```

Para animais reais, o corpus pt-BR pode acrescentar grupo zoológico real somente quando isso for factual. Criaturas fantásticas ficam em categorias de gameplay/lore do próprio mod.

### C — Variantes

- [ ] distinguir espécie de variante;
- [ ] registrar variantes por ID/enum/NBT apenas quando o contrato for estável;
- [ ] mostrar progresso `n descobertas / total conhecido` quando o total for enumerável;
- [ ] renderizar variantes sem exigir spawn físico de todas no mundo;
- [ ] permitir adapter por mod quando variantes usam dados customizados.

### D — Mobs do modpack

O inventário do 10.02 gera a lista exata. Para cada `ENTITY` relevante:

1. página `AUTO` obrigatória;
2. classificação de cobertura;
3. tentativa de facts genéricos;
4. adapter se o mod esconder dados relevantes em APIs próprias;
5. texto `CURATED` pt-BR para conteúdo de alta prioridade;
6. relações com bioma/estrutura/dimensão/loot/ecologia quando verificáveis.

Famílias de conteúdo como vanilla, Alex's Mobs/Alex's Caves, Aquaculture, Cataclysm, Illage and Spillage/Respillaged, TerraFirmaCraft, dimensões e outros mods detectados devem ser processadas pelo mesmo pipeline, não por telas separadas.

### E — Compatibilidade com scaling do próprio RPG

O projeto já possui world/entity scaling. A enciclopédia deve distinguir:

- **valor base da espécie**;
- **valor efetivo da instância atual** após level/rarity/affixes/modificadores;
- modificadores conhecidos do próprio RPG.

Nunca apresentar HP efetivo de uma criatura escalada como se fosse estatística universal do tipo.

Arquivos de integração previstos:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/integration/rpg/RpgEntityScalingCompendiumProvider.java
```

### F — Segurança de inspeção

- [ ] distância máxima validada pelo servidor;
- [ ] line-of-sight/política configurável quando necessário;
- [ ] não enviar NBT arbitrário de entidades ao cliente;
- [ ] whitelistar fatos serializados;
- [ ] dados sensíveis/administrativos nunca entram no packet comum.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityRegistryProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/LivingEntityAttributeProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityVariantProviderTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityInstanceInspectorTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/RpgScalingFactsTest.java
```

Casos obrigatórios:

- [ ] mob vanilla simples;
- [ ] mob sem todos os atributos padrão;
- [ ] boss;
- [ ] animal bebê/adulto;
- [ ] pet domesticado;
- [ ] variante;
- [ ] entidade modded opcional;
- [ ] entidade com scaling do RPG;
- [ ] ausência de adapter específico mantém página base funcional;
- [ ] dedicated server não carrega classes client-only.

## Acceptance

O subplano fecha quando qualquer entidade relevante registrada possui página técnica base, instância inspecionada é distinguida de espécie e providers especiais não comprometem compatibilidade opcional.
