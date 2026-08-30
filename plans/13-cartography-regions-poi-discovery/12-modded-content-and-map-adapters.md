# 13.12 — Conteúdo modded e adapters de mapas/fontes externas

## Objetivo

Cobrir a modlist real sem criar integração hardcoded para cada mod e manter a arquitetura aberta para outras interfaces cartográficas.

## Biomas e estruturas modded

A ordem de integração deve ser:

1. registries/tags padrão;
2. dados do Stage 10;
3. datapack overrides do RPG/modpack;
4. adapters pequenos somente quando o mod possui semântica impossível de inferir genericamente;
5. fallback seguro.

Nunca exigir que um mod declare integração conosco para aparecer genericamente.

## PoiProvider / RegionMetadataProvider

Fornecer SPI opcional para mods que criam locais fora de registries padrão ou precisam fornecer metadados especiais.

Um provider deve poder, dentro de contrato bounded:

- registrar/atualizar/remover POI;
- fornecer categoria/traits;
- fornecer bounds/anchor sem revelar ao cliente;
- sinalizar mudança física relevante;
- declarar provenance/source mod.

Provider não decide intel do jogador.

## Map renderers

JourneyMap é renderer primário previsto, mas o domínio deve suportar interface como:

```text
CartographyRenderer
├── JourneyMapAdapter
├── FutureXaeroAdapter
└── NullRenderer
```

Não implementar outros mapas sem necessidade; apenas manter o boundary arquitetural.

## Compass/discovery sources

Nature's Compass e Explorer's Compass podem futuramente atuar como `DiscoveryProvider` se houver hook/API compatível.

**Compass to Map não deve ser copiado.** Seu código upstream consultado é All Rights Reserved e proíbe reutilização em projeto público sem autorização escrita. Podemos reproduzir independentemente a ideia funcional “resultado de busca autorizado → intel/marker” usando APIs próprias dos mods ou eventos públicos.

## MapFrontiers

MapFrontiers pode ser usado como referência de UX/representação de frontiers. Seu código MIT permite derivação sob as condições da licença, mas não há obrigação de copiar. Preferir implementação própria quando simples; se qualquer trecho substancial for adaptado:

- registrar commit/tag upstream;
- registrar arquivos/classes de origem;
- manter copyright + aviso MIT exigidos;
- adicionar entrada detalhada em `THIRD_PARTY_NOTICES.md`.

## Compatibilidade de versões

Adapters devem declarar:

- mod ID;
- range de versão validado;
- capability detectada;
- fallback quando versão não suportada;
- nenhuma referência direta a classes opcionais fora de boundary seguro.

## Acceptance

- conteúdo desconhecido continua catalogável;
- ausência de qualquer mod opcional não impede startup;
- adapter incompatível desabilita com diagnóstico, não crasha;
- nenhum código de Compass to Map é incorporado sem permissão;
- qualquer derivação MIT possui provenance verificável.