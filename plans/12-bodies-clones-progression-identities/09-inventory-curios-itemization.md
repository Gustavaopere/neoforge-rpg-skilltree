# 12.09 — Inventário, Curios e Itemização

## Objetivo

Definir como itens acompanham corpos sem duplicação e sem quebrar a identidade persistente do Stage 11.

## Regra principal

Por padrão, inventário é `BODY_LOCAL`.

Inclui:

- main inventory;
- hotbar;
- armor;
- offhand;
- Curios/equipment slots externos integrados;
- containers pessoais explicitamente classificados como corporais.

Ao armazenar Corpo A, seus itens ficam no snapshot A. Ao ativar Corpo B, aparecem somente os itens do snapshot B.

## Semântica de movimento, não cópia

A troca deve ser equivalente a:

```text
capturar itens ativos -> persistir source -> limpar projeção ativa -> aplicar itens target
```

Nunca:

```text
copiar itens source -> target
```

Snapshots devem usar cópia defensiva somente como mecanismo de persistência/rollback, com ownership transacional que impeça duas versões utilizáveis do mesmo conjunto.

## Stage 11

A identidade de item pertence ao `ItemStack`:

- `instanceId`;
- seed;
- Rank;
- Poder do Item;
- Prefixos;
- Sufixos;
- Infixos;
- gems/sockets/encantamentos/componentes externos.

Troca de corpo não chama geração de itemização e não rerrola nada.

## Itens no mundo

ItemEntities, baús, máquinas, storage networks e containers do mundo são `ACCOUNT/WORLD_GLOBAL` por padrão. O corpo ativo pode acessá-los normalmente conforme regras do modpack.

Isso significa que o jogador pode deliberadamente transferir um equipamento entre corpos deixando-o em armazenamento compartilhado. Isso é permitido como regra de mundo, não duplicação.

## Shared stash opcional

Se futuramente quisermos conveniência explícita, implementar um **Armazém da Alma**/shared stash como sistema separado, com slots próprios e ownership da conta. Não misturar implicitamente os inventários dos corpos.

## Curios

Criar provider específico que:

- enumera slots reais via API;
- captura conteúdo e metadados necessários;
- limpa/aplica de forma atômica;
- preserva ItemStacks exatamente;
- lida com slots adicionados/removidos entre versões;
- fail-closed se a topologia de slots mudar de forma incompatível.

## Mochilas e inventários internos

Uma mochila é um ItemStack corporal se estiver no inventário do corpo. Seu conteúdo interno via DataComponent/NBT viaja junto com o item; não deve existir segundo snapshot manual que duplique conteúdo.

## Soulbound e itens que retornam ao jogador

Mods que implementam soulbound podem interferir com morte/troca. Criar testes/adapters para impedir que um item armazenado no Corpo A seja teleportado para Corpo B apenas por owner UUID.

Quando não houver API segura, registrar incompatibilidade e bloquear troca durante estado ambíguo em vez de arriscar duplicação.

## Equipamento ativo e atributos

Antes de aplicar target:

- remover modificadores/projeções do equipamento source;
- aplicar ItemStacks target;
- deixar Minecraft/Curios/providers reconstruírem atributos;
- somente depois recomputar atributos RPG derivados.

## Testes obrigatórios

- A com espada, B vazio;
- A armazenado -> B não recebe espada;
- B coloca item em baú global -> A pode retirá-lo depois;
- item Stage 11 mantém `instanceId`/rolls ao trocar;
- Curios não duplica nem desaparece;
- backpack com conteúdo não duplica conteúdo interno;
- crash no meio da troca recupera um único owner lógico para cada ItemStack.

## Critérios de aceite

- inventários corporais são independentes;
- storage do mundo permanece compartilhado;
- Stage 11 é preservado byte/semanticamente;
- Curios possui adapter explícito;
- nenhuma operação normal duplica ItemStack.