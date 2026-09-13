# 10.15 — Boss Checklist, rastreamento de criaturas e waypoints

## Objetivo

Expandir o Compêndio Natural com duas funções de exploração/progressão diretamente ligadas às entradas de fauna:

1. permitir que o jogador consulte **onde encontrar uma criatura descoberta** e selecione essa espécie para **Procurar / Seguir**, recebendo orientação e marcador no mapa quando houver informação suficiente;
2. incorporar um **Boss Checklist** dentro do próprio Compêndio, reunindo bosses e minibosses vanilla/modded, progresso de descoberta/derrota e informações de encontro em uma única interface.

A referência conceitual é combinar o fluxo de descoberta do Field Guide, a ficha enciclopédica do Biology Dictionary e a progressão organizada do Boss Checklist, sem transformar esses mods em dependências obrigatórias.

---

## Dependências internas

Este subplano deve consumir contratos já existentes em vez de recriá-los:

- `10.04` — descoberta e progresso por jogador;
- `10.05` — catálogo universal de entidades e classificação técnica;
- `10.07` — loot/ecologia quando disponível;
- `10.08` — biomas, estruturas e dimensões;
- `10.09` — UI, pesquisa, filtros e página de entrada;
- `10.11` — adapters e extensibilidade para mods opcionais;
- `10.13` — persistência, rede, cache e projeção autorizada ao cliente;
- Stage 08 — quests/progression hooks quando rastrear ou derrotar criaturas produzir objetivos/recompensas.

Nenhum desses dados deve ser duplicado em um segundo catálogo independente.

---

# A — Onde encontrar uma criatura

A página de uma criatura descoberta deve poder apresentar, quando os dados forem verificáveis:

- dimensão;
- biomas;
- habitat/ambiente;
- estruturas relacionadas;
- faixa de altura quando relevante e verificável;
- horário, luminosidade, clima ou outras condições de spawn quando disponíveis;
- condições especiais de aparição;
- método de invocação/summon quando aplicável;
- mod/namespace de origem.

A ausência de um desses dados não deve gerar informação inventada. A UI omite o campo ou indica que a condição não está disponível.

## Origem dos dados

Prioridade:

1. registries/tags/dados runtime verificáveis;
2. contratos públicos do provider/mod;
3. datapack/override curado pelo modpack;
4. corpus editorial com proveniência explícita.

Adapters enriquecem o resultado, mas a página base continua funcional sem adapter específico.

---

# B — Procurar / Seguir criatura

Cada entrada de fauna elegível poderá oferecer a ação **Procurar / Seguir**.

Fluxo pretendido:

```text
descobrir criatura
→ abrir entrada no Compêndio
→ consultar habitat/spawn
→ selecionar Procurar / Seguir
→ obter alvo de busca/waypoint
→ viajar até a região
→ procurar a criatura
```

## Alvo de busca

O sistema deve distinguir explicitamente:

### 1. Habitat conhecido

Um bioma, estrutura, dimensão ou região compatível com as condições conhecidas da espécie.

O marcador significa **"procure nesta região"**, não "há uma entidade exatamente nesta coordenada".

### 2. Último avistamento conhecido

Se o jogador já observou aquela espécie e o sistema persistir a posição do encontro, o Compêndio pode oferecer um marcador de **último avistamento**, identificado como histórico.

### 3. Entidade atualmente localizada

Só pode existir quando o servidor realmente conhece e autoriza a posição daquela instância. Esse modo deve ser explícito, temporário e configurável; a implementação padrão não deve transformar o Compêndio em radar permanente de entidades fora da informação legitimamente disponível ao jogador.

## Política de busca

- um jogador pode ter um alvo principal de criatura por vez na primeira versão;
- trocar o alvo substitui/atualiza o waypoint anterior criado pelo Compêndio;
- cancelar a busca remove o waypoint próprio do Compêndio;
- mudar de dimensão deve preservar ou suspender o alvo conforme a dimensão exigida;
- o sistema não deve procurar chunks indefinidamente nem forçar geração massiva apenas para achar um habitat;
- buscas devem ter orçamento/raio/política configurável;
- se nenhuma localização válida for conhecida, a UI mantém as informações de habitat sem fabricar uma coordenada.

---

# C — Integração com mapa e waypoints

Criar uma fronteira própria de integração, conceitualmente equivalente a:

```text
CompendiumWaypointService
  createOrUpdate(...)
  remove(...)
  isAvailable()
```

O core do Compêndio não deve depender diretamente de um mod específico de mapa.

Providers/adapters opcionais podem integrar o alvo com a solução de mapa/minimap/waypoints adotada pelo modpack.

Fallback obrigatório quando nenhum provider de mapa estiver presente:

- manter o alvo selecionado no Compêndio;
- mostrar dimensão e posição/região quando existirem;
- permitir orientação interna simples no HUD/Compêndio futuramente, sem quebrar startup.

A integração concreta só deve usar API confirmada para a versão física instalada no modpack.

---

# D — Boss Checklist integrado

Bosses e minibosses são entradas normais de fauna, mas recebem uma camada adicional de progressão.

Não deve existir uma segunda enciclopédia separada só para bosses.

## Estados mínimos por jogador

```text
UNKNOWN
DISCOVERED
NOT_DEFEATED
DEFEATED
```

Metadados adicionais podem incluir:

- `optional`;
- ordem de progressão;
- dependências/pré-requisitos;
- dimensão;
- estrutura/arena;
- método de spawn/invocação;
- itens necessários;
- drops/recompensas;
- mod de origem.

A derrota é server-authoritative e deve registrar somente um resultado confirmado atribuível ao jogador/equipe conforme a política multiplayer definida.

---

# E — Descoberta automática de bosses e minibosses

O sistema deve tentar classificar bosses automaticamente usando somente evidência estável, por exemplo:

- tags canônicas do projeto/modpack;
- metadata/API pública de providers;
- integrações verificadas;
- overrides/datapacks curados.

Heurísticas frágeis, como somente quantidade de vida, nome da classe ou presença de boss bar, não devem ser a única autoridade.

Quando a classificação automática não for confiável, o modpack deve poder:

- adicionar uma entidade como boss/miniboss;
- remover uma classificação incorreta;
- marcar como opcional;
- alterar ordem;
- definir dependências;
- complementar texto e condições de encontro.

---

# F — Ordem e progresso de bosses

A UI deve oferecer uma seção/filtro **Bosses** com:

- total conhecido;
- total derrotado;
- percentual/progresso;
- bosses pendentes;
- bosses opcionais;
- filtro por dimensão/mod;
- busca por nome;
- ordenação configurável de progressão.

A ordem não deve ser calculada exclusivamente por "dificuldade automática". Modpacks frequentemente alteram dano, vida, receitas, requisitos, dimensões e progressão; portanto, a ordem final deve aceitar dados/overrides explícitos.

Exemplo:

```text
BossProgressionEntry {
  entityId
  optional
  order
  prerequisiteBosses[]
  encounterHints[]
}
```

Esse modelo é conceitual; a implementação final deve reutilizar IDs e estruturas canônicas já existentes no projeto.

---

# G — Página de boss

Quando uma entrada for classificada como boss/miniboss, sua página normal do Compêndio deve ganhar uma seção de progressão contendo, quando disponível:

- status: desconhecido / descoberto / não derrotado / derrotado;
- posição na progressão;
- pré-requisitos;
- dimensão;
- bioma/estrutura/arena;
- como encontrar;
- como invocar;
- itens/requisitos necessários;
- drops e recompensas;
- variantes/fases quando houver dados verificáveis;
- ação **Procurar / Seguir** quando existir alvo de busca válido.

A página deve continuar exibindo os dados normais de entidade já fornecidos pelo 10.05/10.07/10.08.

---

# H — Multiplayer

Definir explicitamente a política antes da implementação final:

- descoberta pode permanecer individual por jogador;
- derrota pode ser individual, por participação confirmada ou compartilhada por equipe/party, conforme regra do RPG;
- um jogador não deve receber derrota apenas por estar online na mesma dimensão;
- dedupe deve impedir múltiplos registros/recompensas para o mesmo evento confirmado;
- waypoints de busca são pessoais por padrão e não devem ser enviados globalmente sem ação explícita.

---

# I — Persistência

Persistir somente fatos necessários, com IDs namespaced e migração versionada.

Exemplos de estado por jogador:

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

A forma exata deve ser reconciliada com `DiscoveryRecord` e o contrato do 10.13 para evitar duplicação de estado.

Conteúdo removido de um mod não deve corromper o save; IDs desconhecidos precisam de política de preservação/reconciliação compatível com os invariantes gerais do RPG.

---

# J — UI prevista

Na tela principal do Compêndio:

```text
Compêndio Natural
├── Fauna
├── Bosses
├── Flora
├── Árvores
├── Cultivos
├── Biomas
├── Estruturas
├── Dimensões
├── Descobertas
└── Favoritos/Notas
```

Na página de criatura:

```text
Habitat
- Dimensão
- Biomas
- Estruturas
- Condições

[ Procurar / Seguir ]
```

Na página de boss:

```text
Boss
Status: Derrotado / Pendente
Progressão: ...
Pré-requisitos: ...
Como encontrar/invocar: ...
Drops: ...

[ Procurar / Seguir ]
```

---

# K — Testes mínimos

## Rastreamento

- espécie descoberta com habitat conhecido produz alvo válido;
- espécie sem coordenada válida não inventa waypoint;
- troca de alvo remove/substitui marcador próprio;
- cancelamento limpa marcador;
- dimensão incompatível é tratada corretamente;
- provider de mapa ausente não quebra client/server;
- provider incompatível falha de forma diagnosticável e fail-soft;
- nenhum scan pesado ocorre a cada tick/frame.

## Boss Checklist

- boss vanilla configurado/identificado aparece na seção correta;
- boss modded aparece quando provider/tag/override o classifica;
- entidade comum não vira boss por heurística fraca;
- derrota confirmada atualiza exatamente uma vez;
- morte sem atribuição válida não concede progresso indevido;
- boss opcional não bloqueia conclusão obrigatória quando a política assim definir;
- ordem e dependências via datapack/override são determinísticas;
- remoção de mod não corrompe progresso persistido;
- dedicated server funciona sem classes client-only;
- cliente recebe somente a projeção de progresso autorizada do próprio jogador.

---

## Definition of Done

Este subplano fecha quando:

- [ ] criaturas descobertas exibem informações verificáveis de onde podem ser encontradas;
- [ ] existe ação funcional **Procurar / Seguir** para entradas elegíveis;
- [ ] o alvo pode gerar/remover waypoint através de uma integração desacoplada de provider específico;
- [ ] ausência de mapa/provider mantém fallback funcional e não quebra startup;
- [ ] habitat/região, último avistamento e posição exata de entidade são semanticamente distintos;
- [ ] bosses/minibosses possuem classificação automática quando há evidência confiável e override quando não há;
- [ ] progresso de boss é server-authoritative, persistente e idempotente;
- [ ] a seção Bosses mostra pendentes, derrotados, opcionais e progresso agregado;
- [ ] ordem/pré-requisitos são configuráveis por dados;
- [ ] a página do boss integra encontro/spawn/invocação, loot e rastreamento sem duplicar a ficha da entidade;
- [ ] multiplayer possui regra explícita de atribuição de derrota;
- [ ] optional-mod/provider absence passa nos testes aplicáveis;
- [ ] dedicated server e client smoke passam sem vazamento de classes client-only;
- [ ] custos de busca/waypoint são limitados e não provocam geração/scan ilimitado de mundo.
