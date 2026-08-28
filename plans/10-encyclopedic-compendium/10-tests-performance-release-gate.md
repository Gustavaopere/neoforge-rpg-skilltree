# 10.10 — Testes, performance, drift e gate de conclusão

## Objetivo

Fechar o Compêndio somente quando schema, conteúdo, descoberta, saves, networking, UI e cobertura da modlist estiverem verificáveis e não introduzirem custo descontrolado em um pack grande.

## Integração com o Stage 09

Este arquivo define gates específicos do Compêndio; eles devem ser incorporados à matriz geral de `09-hardening-release` antes de release estável. Não marcar este subplano como concluído só porque unit tests passam.

## Matriz de testes

### Core

Cobrir:

- schema/IDs/taxonomia;
- discovery key policy;
- transitions UNKNOWN -> DISCOVERED -> STUDIED;
- idempotência/rewards;
- aliases/migrations;
- filtros de visibilidade;
- search/index puro quando aplicável.

### Data reload

Cobrir:

- catálogo válido;
- duplicate IDs;
- target inexistente;
- provider ausente;
- crosslinks;
- translation keys;
- snapshot atômico;
- reload inválido preservando último snapshot.

### Persistência

Cobrir:

- codec round-trip;
- save pre-Stage-10;
- relog;
- death/respawn conforme regra canônica;
- dimension change;
- restart de servidor;
- provider removido/retornado;
- aliases.

### Networking

Cobrir:

- codec bounded;
- login sync;
- reload sync;
- nova descoberta;
- forged client request rejeitado/irrelevante;
- snapshot grande perto do teto;
- disconnect/reconnect.

### Client/UI

Cobrir lógica extraída de:

- busca com diacríticos;
- filtros;
- locked content;
- crosslinks;
- progress denominator;
- fallback visual.

Fazer smoke manual/reproduzível da tela com várias GUI scales e corpus grande.

### Optional mods

Matriz mínima automatizada/CI onde viável:

- vanilla + rpgskilltree sem providers;
- providers-chave presentes em ambiente de integração permitido;
- ausência individual de adapters compilados opcionais;
- dedicated server sem classes client-only;
- reload com provider entries filtradas.

## GameTests/runtime smoke

Quando NeoForge GameTest for apropriado, adicionar casos para:

- observar entity e registrar descoberta;
- kill idempotente;
- biome discovery mapping;
- entrada em structure;
- flora break/interaction;
- reload não apaga discoveries.

Se um caso depender de provider externo instável, manter fixture/fake registry para core e deixar smoke específico fora do gate principal com justificativa.

## Budgets de performance

Medir com corpus equivalente ou superior ao pack real.

### Proibido

- iterar todas as entries por frame;
- varrer todas as entities do level a cada tick por jogador;
- procurar structures radialmente a cada tick;
- reconstruir índice de busca a cada tecla sem cache incremental/adequado;
- ressincronizar catálogo inteiro em cada discovery;
- instanciar entity pesada continuamente apenas para lista offscreen.

### Metas iniciais a medir, não assumir

Registrar métricas de:

- tempo de reload do catálogo;
- tamanho do snapshot/network;
- memória do catálogo/índice client-side;
- tempo médio/p95 de pesquisa em corpus completo;
- custo do sampling de discovery por jogador;
- custo de abrir/renderizar tela com centenas/milhares de entries.

O gate final deve congelar budgets com base nas medições do hardware/CI disponível; números arbitrários não viram acceptance sem baseline.

## Cobertura e drift

Criar validator que compare:

```text
modlist/provider inventory
        -> registries
        -> encyclopedia entries
        -> pt_br keys
        -> crosslinks
```

Falhar CI/release quando:

- entry ativa aponta target inexistente;
- target obrigatório coberto fica `UNRESOLVED`;
- key PT-BR obrigatória falta;
- crosslink obrigatório quebra;
- alias de migration referencia destino inexistente;
- snapshot excede limite de protocolo;
- corpus gerado e arquivos committed divergem quando houver geração determinística.

## Gate editorial

Para declarar um provider coberto:

- 100% dos targets incluíveis enumerados;
- cada target `CURATED` ou `EXCLUDED_WITH_REASON`;
- nenhuma afirmação mecânica sem fonte/verificação registrada no processo de authoring;
- PT-BR revisado;
- no placeholders do tipo TODO/??? em conteúdo visível.

## Gate funcional final

Cenário mínimo de aceitação em servidor dedicado:

1. iniciar save antigo sem Stage 10;
2. login do jogador;
3. abrir Compêndio;
4. descobrir criatura por observação;
5. estudar alvo com método configurado;
6. descobrir flora;
7. entrar em bioma e estrutura;
8. derrotar hostile/boss testável;
9. pesquisar e navegar crosslinks;
10. reiniciar servidor;
11. confirmar persistência;
12. reload de datapack válido;
13. reload inválido preserva snapshot anterior;
14. remover provider opcional em fixture, carregar save e confirmar ausência segura;
15. restaurar provider e confirmar retorno do progresso.

## Checklist de conclusão do Stage 10

- [ ] Todos os subplanos 01-09 estão `✅-` e integrados.
- [ ] Unit tests, validators, build e dedicated-server smoke passam.
- [ ] Matriz de modlist/corpus não contém `UNRESOLVED` para providers declarados cobertos.
- [ ] `pt_br` tem 100% de cobertura player-facing.
- [ ] Saves antigos e provider removal foram testados.
- [ ] Networking respeita limites medidos.
- [ ] Performance foi perfilada com corpus representativo.
- [ ] Documentação de jogador do Compêndio foi atualizada em `wiki/` a partir da implementação real, não antecipada.
- [ ] Acceptance do Stage 09 incorpora os gates relevantes.

## Acceptance

Somente satisfeito quando toda a matriz acima estiver comprovada por testes/validators/smokes e não existirem blockers encobertos em `PENDING.md` para o escopo do Compêndio.
