# 10.09 — Mods opcionais, registries e adapters específicos

## Objetivo

Cobrir um modpack grande sem transformar cada provider em dependência compilada. O Compêndio deve resolver a maior parte do conteúdo por registries/tags/dados vanilla-NeoForge e criar adapter dedicado somente quando uma mecânica importante não puder ser representada genericamente.

## Princípio arquitetural

A ordem de preferência é:

1. registry/holder/tag vanilla-NeoForge;
2. dados JSON curados do Compêndio;
3. eventos genéricos NeoForge;
4. reflexão/API opcional cuidadosamente isolada quando realmente necessária;
5. dependência `compileOnly`/adapter dedicado apenas com contrato estável comprovado.

Nunca importar classe de mod opcional em caminho common carregado incondicionalmente.

## Arquivos previstos

Base genérica:

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/integration/EncyclopediaProviderGate.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/integration/EncyclopediaTargetResolver.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/integration/EncyclopediaAdapter.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/integration/EncyclopediaAdapterRegistry.java`

Adapters específicos só quando aprovados, em classes separadas pelo provider e carregadas depois de confirmar `ModList.isLoaded(modId)`/contrato equivalente.

## O que deve funcionar genericamente

Sem adapter específico, o sistema deve conseguir:

- confirmar `EntityType`, `Block`, `Item`, `Biome` e `Structure` por `ResourceLocation`;
- renderizar target quando o renderer vanilla/provider funciona pelo registry normal;
- mapear encounter/kill/interact básicos de entities;
- mapear break/use/pickup de flora/recursos quando a entry declarar relação;
- detectar biome/dimension;
- detectar presença em structure com API vanilla;
- filtrar provider ausente;
- mostrar origem pelo mod id/metadata.

Isso cobre boa parte de Alex's Mobs Continued, Better End/Nether, YUNG structures, Cataclysm e outros conteúdos sem conhecer suas classes internas.

## Quando um adapter é justificável

Somente se houver feature enciclopédica valiosa que não possa ser verificada genericamente, por exemplo:

- variant/subspecies guardada em attachment/capability/API do provider;
- domesticação/reprodução custom sem evento vanilla observável;
- boss identity não refletida por `EntityType`;
- flora dinâmica cujo “species” real não corresponde ao block registry visível;
- estrutura virtual/procedural sem holder vanilla adequado;
- mecânica de estudo específica e estável do provider.

O adapter não é necessário apenas para escrever uma descrição do mod.

## Dynamic Trees e flora equivalente

Árvores dinâmicas merecem auditoria específica porque espécie, branch, leaves e rooty soil podem não mapear 1:1 para um único block alvo.

Antes de implementar adapter:

1. confirmar API da versão instalada;
2. identificar ID estável de species;
3. decidir se a entry representa espécie lógica ou blocos físicos;
4. garantir que addons de species (BetterEnd/BetterNether/Ars/etc.) sejam descobertos sem lista hardcoded;
5. fallback seguro quando Dynamic Trees não estiver instalado.

Se a API não for estável, preferir entry por recurso/registry verificável a mixin frágil.

## Providers de estruturas

YUNG's e outros worldgen mods devem preferir registry de `Structure`. O Compêndio não deve depender de classes internas de cada estrutura quando o holder já resolve o ID.

Quando um mod apenas substitui/overhauls estrutura vanilla mantendo ID ou tag semântica, definir claramente se existe uma entry única ou variante/provider-specific. Evitar duplicar “Stronghold” duas vezes só porque o gerador mudou.

## Mods que adicionam bosses/entidades

Para Cataclysm e outros providers de bosses:

- target por `EntityType` quando possível;
- reuse do pipeline de boss existente para defeat/study;
- adapter específico somente para identidade/phase se necessário;
- não hardcode stats que mudam com difficulty/config sem runtime/fonte.

## Compatibilidade com ausência

Testar matriz mínima:

- nenhum provider opcional instalado;
- somente um provider alvo instalado;
- provider + addon de worldgen/species;
- remoção do provider após save com descobertas;
- retorno do provider;
- dedicated server sem classes client-only do provider.

## Diagnostics

Em startup/reload, logar em nível apropriado:

- quantas entries do provider foram ativadas;
- quantas foram omitidas por ausência;
- adapters específicos efetivamente ativados;
- target quebrado de provider presente como erro de authoring;
- nunca spammar uma linha por tick/entry em operação normal.

## Testes

- provider absent não carrega classe adapter;
- registry resolver resolve target genérico;
- provider presente + target ausente falha catalog validation;
- addons acrescentam entries sem alterar código base quando só usam registries/dados;
- adapter dedicado só é selecionado para mod id suportado;
- fallback genérico continua operando sem adapter;
- tests/smoke de dedicated server com optional mods ausentes.

## Acceptance

- [ ] Cobertura normal não exige dependência compilada de cada mod da modlist.
- [ ] Registry/data-driven é a via padrão.
- [ ] Adapters específicos têm justificativa mecânica documentada.
- [ ] Dynamic Trees/espécies têm contrato de identidade auditado antes de integração.
- [ ] Estruturas usam holder/registry quando possível.
- [ ] Ausência de qualquer provider opcional não quebra client nem dedicated server.
