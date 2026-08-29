# 12.06 — World scaling e atualização local de mobs

## Objetivo

Fazer o Stage 02 usar a progressão do corpo ativo sem destruir os baselines próprios do mundo.

## Resultado esperado

Exemplo:

```text
Corpo A = nível 300
spawn/Overworld inicial -> mobs novos ~300+ conforme regras do Stage 02

switch para Corpo B = nível 1
spawn/Overworld inicial -> mobs novos voltam ao patamar inicial
Nether/estrutura perigosa -> continuam acima de 1 por baseline próprio

switch de volta para A
novos spawns voltam a considerar nível 300
```

## Fórmula conceitual

O nível efetivo de uma entidade não deve ser simplesmente `playerLevel`.

```text
progressionContribution = activeBodyLevel/context
baseline = max(
  territorialBaseline,
  dimensionBaseline,
  structureBaseline,
  mobTypeBaseline,
  encounterBaseline
)

spawnLevel = combine(baseline, progressionContribution, difficultyRules)
```

A fórmula numérica definitiva continua pertencendo ao Stage 02. O Stage 12 apenas muda a fonte de `progressionContribution` para o corpo ativo.

## Mobs já existentes

Não varrer o mundo inteiro e reescrever nível de todos os mobs ao trocar de corpo.

Categorias:

### Persistentes — nunca reescalar automaticamente

- bosses;
- mobs nomeados;
- pets/tamed;
- NPCs persistentes;
- entidades de quests;
- entidades explicitamente marcadas para persistência;
- mobs cujo nível já é parte de uma identidade persistente relevante.

### Comuns e transitórios

Após troca de corpo, pode haver uma **atualização ecológica local configurável**:

- invalidar caches de progressão/spawn;
- novos mobs usam imediatamente o corpo novo;
- opcionalmente permitir despawn natural/acelerado de hostis comuns fora de combate em raio limitado;
- nunca deletar entidade em combate, persistente, nomeada ou com dono;
- nunca fazer sweep global de chunks.

## Solo-first

Como o uso primário é singleplayer, o resolver pode usar diretamente o corpo ativo do único jogador relevante. Ainda assim, a API não deve quebrar servidor com múltiplos players: encounters devem seguir a política canônica existente para múltiplos jogadores próximos.

## Exploits

Bloquear exploração óbvia:

- não permitir trocar corpo em combate se isso resetar encounter/boss de forma abusiva;
- encounters travados podem registrar `encounterLevel` ao iniciar;
- trocar para corpo baixo não deve nerfar boss já engajado;
- recompensas já determinadas não podem rerrolar pela troca.

## Eventos e cache

No `BodySwitchPostEvent`:

1. invalidar `PlayerProgressionSnapshot`;
2. publicar nova revision;
3. invalidar cache de scaling dependente do player;
4. notificar spawn/encounter services;
5. executar apenas refresh local permitido.

## Testes obrigatórios

- nível 300 -> corpo nível 1 no spawn;
- corpo nível 1 no Nether mantém baseline de Nether;
- boss ativo não perde nível;
- mob persistente não é reescrito;
- mob comum novo usa corpo novo;
- voltar ao nível 300 restaura contribution alta;
- troca repetida não deixa cache preso no corpo anterior;
- nenhum O(n chunks do mundo) é executado.

## Critérios de aceite

- world scaling depende do corpo ativo;
- baseline natural continua independente do corpo;
- resposta à troca é perceptível sem varredura global;
- encounters não são exploráveis por body swap;
- comportamento multiplayer continua definido, mesmo sendo otimizado para solo.