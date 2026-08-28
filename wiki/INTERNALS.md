# Comportamento técnico relevante ao gameplay

> **Snapshot:** `main`, 28/08/2026.

## Servidor autoritativo

Progressão, requisitos, compra de nós, masteries e efeitos persistentes pertencem ao servidor. A UI recebe/sugere ações, mas não é autoridade para validá-las.

## Conteúdo data-driven

`data/rpgskilltree/` contém catálogos de árvore, classes, especializações, efeitos, regras, progressão, boss rewards, compendium e outras famílias. Reloads devem validar um snapshot antes de publicá-lo, então datapacks podem alterar conteúdo sem mover toda a lógica para Java.

## IDs canônicos

Progressão usa IDs namespaced. Isso reduz colisões e permite migração/preservação de estado quando conteúdo opcional muda.

## Ordem de composição de efeitos

A ordem conceitual verificada é:

1. `ADD_FLAT`;
2. `ADD_PERCENT_BASE`;
3. `MULTIPLY_TOTAL`;
4. `OVERRIDE`.

Por isso duas perks sobre o mesmo atributo não dependem simplesmente da ordem em que os arquivos foram encontrados.

## Mastery representa prática confirmada

Policies convertem ações observadas em `MasteryAward`. Integrações podem exigir confirmação posterior: Goety confirma gasto/ordem, Eidolon confirma conclusão e `procDepth` evita recursão em ações derivadas.

## Camadas diferentes

- **perk/nó:** unidade da árvore;
- **mastery:** prática em uma lane;
- **arquétipo:** padrão emergente;
- **classe:** identidade governada por regras;
- **especialização:** camada mais estreita/provedora.

Um gateway pode ser importante sem possuir bônus numérico próprio.

## Persistência

O estado do jogador é armazenado por Data Attachments/codec. A arquitetura procura preservar IDs desconhecidos em vez de apagar silenciosamente progresso apenas porque um provider/datapack ficou temporariamente ausente.

## Sincronização

O cliente recebe snapshots para árvore/feedback. O servidor continua sendo a fonte de verdade após reload, mudança de XP, mastery ou classe.

## Boss rewards

Recompensas são data-driven. Uma entidade ser “boss” visualmente ou em outro mod não basta; precisa existir correspondência reconhecida pelas regras carregadas.

## Compendium

Há infraestrutura para categorias, fatos/entradas, relações, descoberta, visibilidade, proveniência e cobertura. O conteúdo distribuído ainda é mínimo; infraestrutura de compendium não equivale a enciclopédia in-game completa.

## Fontes

- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/java/dev/gustavopere/rpgskilltree/compendium/`
- `src/main/resources/data/rpgskilltree/`
- `AGENTS.md`
