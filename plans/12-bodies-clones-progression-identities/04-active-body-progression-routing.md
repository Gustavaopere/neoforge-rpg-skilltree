# 12.04 — Roteamento da progressão pelo corpo ativo

## Objetivo

Fazer todo sistema RPG que hoje pensa em "progressão do player" resolver a progressão através do `activeBodyId`, sem trocar o UUID real da conta.

## Regra de acesso

Nenhum consumidor relevante deve ler storage de nível/perks diretamente. Introduzir uma fachada canônica:

```text
ActiveProgressionContext resolve(ServerPlayer player)
```

que retorna:

```text
ownerUuid
activeBodyId
level
xp
attributes
skillTree
classes
masteries
specializations
revision
```

## Migração dos call sites

Auditar e migrar, em ordem:

1. level/XP;
2. award de pontos;
3. compra e runtime de perks;
4. atributos canônicos;
5. classes emergentes;
6. masteries;
7. especializações;
8. combat/magic hooks;
9. quest hooks que dependem do nível do personagem;
10. UI/network sync;
11. world/mob scaling.

## Corpo original

Enquanto Stage 12 ainda não estiver inicializado para um jogador, o resolver deve expor um perfil compatível com o estado legado. O primeiro registro transforma esse estado existente no Corpo Original sem reset.

## Corpo novo

`BodyProfileFactory.fresh(...)` deve chamar APIs canônicas de defaults do RPG. Não hardcodar `level = 1` em múltiplos lugares.

## Atributos derivados

Ao trocar:

- remover projeções e modificadores derivados do corpo anterior;
- carregar estado persistido do corpo alvo;
- recomputar atributos derivados;
- recomputar classes emergentes;
- reconstruir caches de perks/masteries;
- sincronizar somente depois do estado ficar consistente.

Nunca persistir valores derivados como se fossem fonte de verdade quando puderem ser recalculados.

## Eventos

Expor eventos server-side com ordem documentada:

```text
BodySwitchPreEvent
BodySourceCapturedEvent
BodyTargetApplyingEvent
BodySwitchPostEvent
```

Eventos `Pre` podem rejeitar com motivo localizado. Eventos `Post` não podem alterar ownership da transação já confirmada.

## Compatibilidade com código legado

Durante migração, getters antigos podem delegar ao resolver ativo, mas devem ser marcados para remoção futura. Evitar dois storages de progressão divergentes.

## Critérios de aceite

- level/perks/classes refletem imediatamente o corpo ativo;
- Corpo A e Corpo B evoluem independentemente;
- ganhar XP em B não altera A;
- voltar a A restaura exatamente seu estado;
- nenhuma integração precisa falsificar UUID do player;
- caches são invalidados/reconstruídos uma vez por troca, não a cada tick.