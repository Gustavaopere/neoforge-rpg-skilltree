# Comportamento técnico e regras internas

> **Snapshot documentado:** `main` em 28/08/2026. Esta página explica mecanismos internos que alteram o gameplay ou ajudam a interpretar corretamente a wiki.

## Autoridade do servidor

Progressão, requisitos, compra de nós, estados persistentes, masteries e efeitos são tratados como dados autoritativos do servidor. O cliente recebe snapshots/payloads para apresentação e interação, mas não deve conseguir criar um estado válido apenas alterando a UI.

Essa divisão é especialmente importante em modpacks com datapacks customizados: a verdade de gameplay é o catálogo carregado pelo servidor.

## Dados dirigidos por datapack

O projeto separa código de runtime da definição de conteúdo. Sob `data/rpgskilltree/` existem catálogos para árvore, classes, especializações, efeitos, regras, progressão, recompensas e outros domínios.

A intenção arquitetural é permitir reload transacional: um novo snapshot deve ser validado antes de substituir o estado publicado. Para a wiki, isso significa que valores podem mudar por datapack mesmo sem alteração Java.

## IDs namespaced

Entidades de progressão usam IDs namespaced/canônicos. Isso reduz colisões entre conteúdo próprio e integrações e é também a base para migração de conteúdo persistido.

A documentação evita abreviar IDs quando a distinção puder gerar ambiguidade.

## Ordem de efeitos

O sistema de efeitos de nós usa operações com ordem previsível. A composição não deve depender da ordem casual de carregamento. A sequência conceitual é:

1. `ADD_FLAT`;
2. `ADD_PERCENT_BASE`;
3. `MULTIPLY_TOTAL`;
4. `OVERRIDE`.

Isso importa para interpretar builds com múltiplos modificadores sobre o mesmo atributo.

## Mastery é prática, não apenas posse

Mastery é concedida a partir de ações reconhecidas por policies específicas. Diversas integrações evitam creditar simples intenção de uso:

- Goety confirma gasto de Soul Energy;
- comandos de servos são confirmados pelo estado posterior;
- Eidolon exige resultado/ritual confirmado;
- ações derivadas de proc podem ser ignoradas por `procDepth`.

Veja [Interações ocultas](HIDDEN_INTERACTIONS.md) para os casos concretos.

## Persistência do jogador

O projeto usa Data Attachments para o estado persistente de progressão. Serialização/codec e runtime de progressão mantêm a fronteira entre estado de domínio e armazenamento NeoForge.

A arquitetura exige cuidado com versões e IDs desconhecidos: conteúdo persistido não deve ser descartado silenciosamente apenas porque um datapack/mod opcional deixou de estar presente temporariamente.

## Sincronização

A sincronização envia ao cliente o estado necessário para UI e feedback. Alterar XP, mastery, classe ou perks no servidor pode exigir atualização do snapshot e reaplicação/refresh de efeitos derivados.

Isso também explica uma limitação de performance registrada no projeto: alguns caminhos de mutação atuais recalculam/sincronizam mais do que o ideal. Consulte [Limitações conhecidas](KNOWN_LIMITATIONS.md).

## Arquitetura semântica da árvore

A árvore visual não é apenas uma lista plana de 512 IDs. O projeto possui catálogo de arquitetura com árvores, domínios, branches/gateways e tipos semânticos como `main`, `specialization`, `hybrid` e `provider`.

Isso permite que um nó seja mecanicamente importante por acesso/requisito mesmo quando não possui um modificador numérico próprio.

## Classes, arquétipos e especializações são camadas diferentes

- **nó/perk:** unidade adquirida na árvore;
- **mastery:** prática acumulada em uma lane;
- **arquétipo:** reconhecimento emergente de padrão de progressão;
- **classe:** identidade persistente governada por regras;
- **especialização:** camada mais estreita/provedora dentro da build.

Misturar essas camadas é uma fonte comum de interpretação errada da árvore.

## Boss rewards

As recompensas de chefes são data-driven e passam por loader/runtime próprio. O fato de outro mod classificar uma entidade como boss não garante reconhecimento; é necessário existir correspondência nas regras/dados carregados.

## Compendium interno

O código de compendium possui modelos para:

- categorias;
- entradas/fatos;
- relações;
- descoberta/visibilidade;
- proveniência;
- cobertura e inventário.

No snapshot atual, porém, o conteúdo distribuído em `compendium/entries` ainda é mínimo. A infraestrutura não deve ser confundida com conteúdo finalizado.

## Onde conferir a implementação

- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/java/dev/gustavopere/rpgskilltree/compendium/`
- `src/main/resources/data/rpgskilltree/`
- `AGENTS.md`
