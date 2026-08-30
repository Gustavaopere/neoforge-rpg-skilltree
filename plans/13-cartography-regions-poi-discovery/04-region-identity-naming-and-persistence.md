# 13.04 — Identidade, nomes e persistência de regiões

## Objetivo

Dar identidade RPG estável às regiões sem fazer nomes mudarem após reload, expansão da fronteira ou atualização de datapack, mantendo o armazenamento bounded e recuperável em mundos de longa duração.

## Region identity

Cada região materializada recebe:

- `regionId` persistente;
- dimensão;
- `RegionLayer`;
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

O topônimo não deve ser regenerado quando a região ganha células. Persistir identidade/seed ou o token nominal necessário.

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

## Ciclo de vida e limites de armazenamento

Persistência não pode crescer indefinidamente apenas porque o mundo é antigo. Separar o armazenamento por dimensão e por shard/região espacial bounded, evitando um único `SavedData` monolítico.

Política mínima:

- células de geometria são armazenadas de forma compacta por shard e podem ser reconstruídas a partir de dados canônicos já materializados quando seguro;
- `SurfaceCell` e `SubterraneanCell` usam codificação compacta/runs/bitsets ou equivalente, nunca um objeto pesado por bloco;
- aliases de merge são achatados (`A -> C`, nunca cadeias longas `A -> B -> C`) durante compactação;
- alias que não é referenciado por intel, quest, POI, migration journal ou outra identidade persistente pode ser removido após uma janela/versionamento de retenção explicitamente configurado;
- tombstones de conteúdo removido guardam somente o mínimo para impedir ressurreição/duplicação e podem ser compactados após a janela de migração suportada;
- POIs obsoletos sem referência externa entram em estado tombstone e depois podem ser podados; POIs ainda referenciados por quest/intel nunca são apagados silenciosamente;
- caches derivados de fronteira/polígono/LOD não são autoridade persistente e podem ser descartados/recalculados;
- journals de migração/reconciliação são bounded e truncados somente após checkpoint atômico confirmado;
- manutenção/compaction ocorre em jobs bounded, nunca em um scan global síncrono no login ou tick.

Definir métricas e limites configuráveis para:

- bytes/células por shard;
- aliases ativos;
- tombstones;
- POIs ativos/obsoletos;
- tempo máximo de job de compactação por tick;
- tamanho máximo antes de emitir diagnóstico/admin warning.

Quando um limite rígido for atingido, o sistema falha fechado para **nova materialização não essencial**, preservando dados já persistidos; nunca apaga automaticamente intel/quest/identidade ativa para liberar espaço.

## Duplicidade e vizinhança

O gerador deve reduzir nomes idênticos em regiões próximas quando possível, mas a ausência de candidato único nunca pode impedir criação da região. Identidade técnica sempre é o `regionId`, não o nome visível.

## Acceptance

- mesma região mantém ID e nome após reload;
- expansão não rerrola nome;
- rename admin preserva referências de quests/intel;
- merge mantém aliases válidos e compactação achata cadeias;
- cave/surface shards preservam `RegionLayer`;
- tombstone/alias/POI possuem política explícita de retenção e poda segura;
- caches derivados podem ser descartados sem perder autoridade;
- testes de mundo explorado por longa duração demonstram crescimento bounded por shard e compaction incremental;
- nenhum job de manutenção executa full-world scan síncrono;
- nomes e descritores próprios do sistema aparecem em PT-BR.