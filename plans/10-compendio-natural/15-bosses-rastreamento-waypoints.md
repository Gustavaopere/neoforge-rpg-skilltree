# 10.15 — Bosses, rastreamento e waypoints — contrato funcional

## Fronteira

A apresentação de habitat, ação `Procurar / Seguir`, Boss Checklist, waypoints/overlays e HUD foi separada para `../textura/06-cartografia-waypoints-bosses.md`.

Este arquivo permanece dono da semântica, dados, tracking, anti-cheat, persistência, progressão de bosses e integração funcional com mapas.

## Objetivo

1. permitir que o jogador consulte informação verificável de onde encontrar criatura descoberta e selecione uma espécie para rastreamento quando houver alvo legítimo;
2. incorporar bosses/minibosses ao mesmo domínio do Compêndio, com progresso server-authoritative e sem criar catálogo paralelo.

## Dependências internas

Consumir contratos existentes:

- `10.04` — descoberta/progresso;
- `10.05` — catálogo universal de entidades;
- `10.07` — loot/ecologia;
- `10.08` — biomas/estruturas/dimensões;
- `10.09` — browser/page model e client boundary;
- `10.11` — adapters;
- `10.13` — persistência/rede/cache/projeção autorizada;
- Stage 08 — quest hooks quando aplicável;
- Stage 13 — cartografia/renderer quando houver integração de mapa.

Nenhum dado deve ser duplicado em segundo catálogo.

## A — Dados de encontro

Para criatura descoberta, o domínio pode fornecer quando verificável:

- dimensão;
- biomas;
- habitat/ambiente;
- estruturas relacionadas;
- faixa de altura quando comprovada;
- horário/luminosidade/clima ou outras condições comprovadas;
- condições especiais de spawn;
- método de invocação quando aplicável;
- mod/namespace de origem.

Prioridade de fonte:

1. registries/tags/runtime verificável;
2. contratos públicos do provider;
3. datapack/override curado;
4. corpus editorial com proveniência explícita.

Ausência de dado não autoriza invenção. A forma visual desses campos pertence a Textura.

## B — Tracking `Procurar / Seguir`

Tipos semânticos distintos:

### Habitat conhecido

Região/bioma/estrutura/dimensão compatível com condições conhecidas. Não significa que uma entidade esteja naquela coordenada.

### Último avistamento

Posição histórica somente quando o sistema realmente a persistir e o jogador estiver autorizado a recebê-la.

### Entidade atualmente localizada

Só existe quando o servidor conhece e autoriza a posição da instância. Deve ser temporário/configurável conforme contrato; o Compêndio não vira radar global por default.

Política funcional:

- primeira versão pode limitar a um alvo principal por jogador;
- trocar alvo substitui/reconcilia o waypoint próprio anterior;
- cancelar remove o waypoint próprio;
- mudança de dimensão preserva/suspende conforme regra definida;
- nenhum scan ilimitado ou geração massiva de chunks;
- busca usa orçamento/raio/política configurável quando implementada;
- sem localização válida, o domínio fornece apenas habitat, sem fabricar coordenada.

## C — Boundary de waypoints/mapa

Fronteira conceitual:

```text
CompendiumWaypointService
  createOrUpdate(...)
  remove(...)
  isAvailable()
```

O core não depende de um mod específico de mapa. Adapter concreto só pode usar API confirmada da versão física instalada.

Sem provider de mapa:

- tracking pode continuar selecionado no domínio;
- dimensão/posição/região autorizadas continuam disponíveis ao client model;
- nenhum startup é bloqueado;
- fallback de apresentação pertence a `../textura/06-cartografia-waypoints-bosses.md`.

## D — Bosses e minibosses

Boss é a mesma entrada de fauna com camada adicional de progressão; não criar segunda enciclopédia.

Estados mínimos por jogador:

```text
UNKNOWN
DISCOVERED
NOT_DEFEATED
DEFEATED
```

Metadados opcionais podem incluir:

- `optional`;
- ordem de progressão;
- dependências/pré-requisitos;
- dimensão;
- estrutura/arena;
- método de spawn/invocação;
- itens necessários;
- drops/recompensas;
- mod de origem.

Derrota é server-authoritative e só registra resultado confirmado atribuível ao jogador/equipe conforme política multiplayer.

## E — Classificação de boss

Usar evidência estável, como:

- tags canônicas;
- metadata/API pública de provider;
- integrações verificadas;
- overrides/datapacks curados.

Vida, nome de classe ou boss bar sozinhos não são authority suficiente.

Override deve permitir adicionar/remover classificação, marcar opcional, ajustar ordem/dependências e complementar informação de encontro.

## F — Ordem/progresso

Ordem final aceita dados explícitos; não derivar exclusivamente de dificuldade automática. Modelo conceitual continua:

```text
BossProgressionEntry {
  entityId
  optional
  order
  prerequisiteBosses[]
  encounterHints[]
}
```

A forma concreta deve reutilizar IDs/estruturas canônicas do projeto. A apresentação de progresso fica em Textura.

## G — Multiplayer

Antes do fechamento final, definir explicitamente:

- descoberta individual ou compartilhada conforme regra do RPG;
- derrota individual/participação/equipe conforme policy;
- jogador não recebe derrota apenas por estar online na mesma dimensão;
- dedupe impede múltiplos registros/recompensas do mesmo evento;
- waypoints de busca são pessoais por default.

## H — Persistência

Persistir somente fatos necessários, reconciliados com `DiscoveryRecord`/10.13.

Modelos conceituais:

```text
BossProgress {
  discoveredBossIds[]
  defeatedBossIds[]
}

CreatureTrackingState {
  trackedEntityId?
  targetKind?
  dimensionId?
  targetPosition?
  source?
}
```

Conteúdo removido não pode corromper save; IDs desconhecidos seguem política de preservação/reconciliação versionada.

## I — Testes mínimos funcionais

Tracking:

- habitat conhecido produz alvo compatível quando a policy conseguir resolvê-lo;
- sem coordenada válida não há waypoint inventado;
- troca/cancelamento reconcilia marker próprio;
- dimensão incompatível é tratada;
- provider de mapa ausente não quebra client/server;
- provider incompatível falha de modo diagnosticável/fail-soft;
- nenhum scan pesado por tick/frame.

Bosses:

- boss vanilla configurado/identificado entra no domínio correto;
- boss modded só entra por provider/tag/override confiável;
- entidade comum não vira boss por heurística fraca;
- derrota confirmada atualiza exatamente uma vez;
- morte sem atribuição válida não concede progresso;
- optional não bloqueia conclusão quando a policy assim definir;
- ordem/dependências determinísticas;
- remoção de mod não corrompe progresso;
- dedicated server funciona sem classes client-only;
- cliente recebe somente projeção autorizada.

## Definition of Done funcional

- [ ] dados verificáveis de encontro disponíveis às entradas elegíveis;
- [ ] tracking funcional sem fabricar precisão;
- [ ] integração de waypoint desacoplada de provider específico;
- [ ] ausência de mapa/provider é fail-soft;
- [ ] habitat, último avistamento e posição exata permanecem semanticamente distintos;
- [ ] classificação de bosses usa evidência confiável + override;
- [ ] progresso de boss server-authoritative, persistente e idempotente;
- [ ] ordem/pré-requisitos data-driven quando aplicável;
- [ ] multiplayer possui regra explícita de atribuição;
- [ ] optional-mod/provider absence passa testes;
- [ ] dedicated server/client boundaries passam sem vazamento client-only;
- [ ] custos de busca são limitados.

O Definition of Done visual correspondente vive em `../textura/06-cartografia-waypoints-bosses.md` e não altera estes critérios funcionais.
