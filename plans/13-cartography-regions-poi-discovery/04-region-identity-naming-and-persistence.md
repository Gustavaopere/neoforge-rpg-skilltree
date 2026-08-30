# 13.04 — Identidade, nomes e persistência de regiões

## Objetivo

Dar identidade RPG estável às regiões sem fazer nomes mudarem após reload, expansão da fronteira ou atualização de datapack.

## Region identity

Cada região materializada recebe:

- `regionId` persistente;
- dimensão;
- tipo semântico principal;
- subtipo/traits opcionais;
- `nameIdentity` persistente;
- aliases de IDs absorvidos em merges;
- versão do classificador que a originou;
- dados mínimos para reconstrução/reconciliação.

## Nomeação

Suportar três fontes, por prioridade:

1. nome explícito definido por datapack/quest/world authoring;
2. nome persistente gerado deterministicamente por tabelas temáticas;
3. fallback localizado baseado no tipo (`Floresta`, `Deserto`, `Cordilheira`, etc.).

Exemplos de gramática data-driven:

```text
FLORESTA → Floresta de {topônimo}
DESERTO → Deserto de {topônimo}
MONTANHA → Serra de {topônimo}
VULCANICO → Campos Vulcânicos de {topônimo}
```

O topônimo não deve ser regenerado quando a região ganha chunks. Persistir identidade/seed ou o token nominal necessário.

## Nomes próprios e localização

- texto estrutural fica em lang keys PT-BR;
- topônimos podem ser nomes próprios persistentes;
- permitir override por resource/data pack;
- não armazenar tradução final quando uma chave localizada for suficiente;
- ferramentas admin podem renomear região sem alterar `regionId`.

## Persistência

Usar storage versionado, com escrita atômica/reconciliável e sem acoplar ao formato interno do JourneyMap.

Deve sobreviver a:

- save/reload;
- restart do servidor;
- expansão incremental;
- troca de corpo;
- ausência/presença posterior de JourneyMap;
- mudança de versão do schema.

## Duplicidade e vizinhança

O gerador deve reduzir nomes idênticos em regiões próximas quando possível, mas a ausência de candidato único nunca pode impedir criação da região. Identidade técnica sempre é o `regionId`, não o nome visível.

## Acceptance

- mesma região mantém ID e nome após reload;
- expansão não rerrola nome;
- rename admin preserva referências de quests/intel;
- merge mantém aliases válidos;
- nomes e descritores próprios do sistema aparecem em PT-BR.