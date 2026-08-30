# 10.10 — Corpus editorial pt-BR e descrições completas

## Objetivo

Produzir o conteúdo textual do Compêndio em **português brasileiro**, com descrições completas, úteis e verificáveis para o conteúdo do modpack, sem depender de tradução automática em runtime e sem inventar fatos.

Este subplano é o responsável por transformar o catálogo técnico em uma enciclopédia realmente legível.

## Regra de idioma

- todo texto próprio do projeto exibido ao jogador deve existir em `pt_br.json` ou no corpus pt-BR correspondente;
- nomes fornecidos por mods externos usam a localização pt-BR do próprio mod quando ela existir;
- se o mod externo não fornecer pt-BR, o Compêndio pode oferecer alias/nome editorial pt-BR próprio, mantendo o ID técnico visível em modo avançado;
- inglês não deve aparecer como fallback silencioso em textos próprios do Compêndio;
- termos técnicos inevitáveis devem ser traduzidos ou explicados em pt-BR.

## Hierarquia de fontes factuais

Para escrever uma descrição, usar nesta ordem de preferência:

1. dados do registry/runtime/datapack do próprio jogo/mod;
2. documentação oficial do mod;
3. código-fonte/API oficial quando necessário para confirmar comportamento;
4. changelog/wiki oficial do autor;
5. fonte comunitária somente quando não houver fonte primária e o fato puder ser confirmado independentemente.

Nunca tratar texto promocional ou wiki comunitária não verificada como autoridade única para estatística mecânica.

## Proveniência por parágrafo/fato

O corpus editorial deverá aceitar metadata de origem, sem necessariamente exibi-la no modo normal.

Estrutura conceitual:

```json
{
  "entry": "ENTITY:modid:creature",
  "language": "pt_br",
  "title": "Nome em português",
  "summary": "Resumo original em pt-BR.",
  "sections": {
    "biology": {"text": "...", "sources": ["..."]},
    "behavior": {"text": "...", "sources": ["..."]},
    "habitat": {"text": "...", "sources": ["..."]}
  }
}
```

Não copiar trechos extensos de documentação externa. O texto do Compêndio deve ser redação própria baseada nos fatos confirmados.

## Ficha editorial por tipo

### A — Fauna e criaturas

Cada entidade `CURATED` deve receber, conforme aplicável:

1. **Resumo** — o que é e qual papel tem no jogo/mod;
2. **Classificação** — animal real, criatura fantástica, morto-vivo, construto, boss etc.;
3. **Aparência e variantes** — somente diferenças relevantes/confirmadas;
4. **Habitat e ocorrência** — biome/dimensão/estrutura;
5. **Comportamento** — passivo/neutro/hostil, padrões especiais verificáveis;
6. **Alimentação e atração**;
7. **Reprodução** — item/condição/cooldown/filhote quando houver;
8. **Domesticação** — se e como ocorre;
9. **Combate** — capacidades e riscos sem substituir os stats técnicos;
10. **Drops e utilidade** — resumo, com tabela técnica separada;
11. **Ecologia/interações** — relações com outras espécies/blocos quando comprovadas;
12. **Curiosidades mecânicas** — apenas fatos úteis e verificáveis;
13. **Fonte/mod de origem**.

Para animais reais, texto biológico do mundo real só entra quando acrescentar valor e for cientificamente correto; separar claramente biologia real de mecânica de Minecraft.

### B — Árvores

Cada árvore `CURATED` deve receber:

1. espécie/nome;
2. mod de origem;
3. aparência/identificação;
4. biomas/habitat;
5. clima/solo quando o sistema do mod realmente usa esses parâmetros;
6. sapling/propágulo;
7. crescimento;
8. tronco/folhas/frutos/produtos;
9. usos principais;
10. mapeamento Dynamic Trees, se houver;
11. variações/subespécies;
12. relações com cultivos/fauna quando verificáveis.

### C — Plantas, fungos e cultivos

Ficha:

1. identificação;
2. habitat ou método de cultivo;
3. solo/substrato;
4. luz/água/clima/estação quando aplicável;
5. estágios de crescimento;
6. colheita/drops;
7. replantio/propagação;
8. usos culinários/alquímicos/industriais em nível resumido;
9. compatibilidades relevantes.

### D — Biomas

Ficha:

1. visão geral;
2. dimensão;
3. características ambientais;
4. terreno/vegetação;
5. fauna associada;
6. flora/árvores;
7. estruturas relevantes;
8. recursos naturais importantes;
9. perigos/condições especiais;
10. mod de origem.

### E — Estruturas

Ficha:

1. o que é;
2. mod de origem;
3. dimensão/biomas de geração;
4. aparência/função;
5. habitantes/mobs/bosses quando confirmados;
6. perigos/mecânicas;
7. loot relevante sem spoilers excessivos configuráveis;
8. variantes;
9. relação com progressão/quests quando existir;
10. observações de worldgen sem prometer coordenada/chance falsa.

### F — Dimensões

Ficha:

1. conceito geral;
2. forma de acesso somente quando confirmada;
3. biomas;
4. fauna;
5. flora;
6. estruturas;
7. recursos;
8. condições ambientais;
9. progressão relacionada;
10. mod de origem.

## Plano de produção do corpus

### Passo 1 — Gerar backlog exato a partir do 10.02

Criar:

```text
generated/compendium/editorial-backlog.json
generated/compendium/editorial-backlog.md
```

Cada entrada terá:

```text
entry_id
source_mod
kind
coverage
priority
ptbr_name_status
summary_status
full_description_status
source_status
review_status
```

Estado implementado:

- [x] `scripts/compendium/editorial_backlog.py` deriva a fila diretamente do `coverage-report.json` do 10.02;
- [x] JSON e Markdown são gerados com os campos editoriais obrigatórios, sem gerar prosa ou promover inferências a fatos;
- [x] `ERROR` fica bloqueado e `IGNORED` fica `NOT_REQUIRED`, ambos fail-closed; inclusive linhas runtime malformadas preservadas pelo 10.02 com `inventory_key` sintético continuam visíveis como `ERROR`/`BLOCKED` em vez de desaparecer;
- [x] reruns normais de `generate_inventory.py` reutilizam automaticamente o `editorial-backlog.json` já existente no diretório de saída, preservando estados de revisão e órfãos; `--previous-editorial-backlog` permite selecionar explicitamente outra base anterior;
- [x] `scripts/compendium/generate_inventory.py` produz os artefatos do inventário/cobertura e `editorial-backlog.json/.md` em uma única execução;
- [x] prioridades especiais podem ser informadas por override explícito com motivo e somente para IDs presentes no runtime.

### Passo 2 — Priorizar conteúdo

Ordem inicial:

1. vanilla relevante como referência de qualidade;
2. criaturas/biomas/estruturas de progressão e bosses;
3. fauna e flora de exploração frequente;
4. TFC e sistemas ambientais/agro centrais;
5. grandes mods de biomas/dimensões;
6. suites de estruturas;
7. conteúdo menor/decorativo ainda relevante;
8. entradas raras/administrativas.

A prioridade não altera o gate final: toda entrada que deveria ser curada deve terminar revisada ou explicitamente permanecer `AUTO` com justificativa.

A priorização automática atualmente é deliberadamente conservadora: vanilla, TFC, biomas/dimensões, estruturas e demais entradas recebem faixas determinísticas; boss/progressão e outros casos semanticamente especiais **não são inferidos pelo nome do ID** e exigem override explícito com justificativa até existir uma fonte factual própria para essa classificação.

### Passo 3 — Pacotes por namespace

Organizar dados em arquivos pequenos por mod/tipo para revisão e merge incremental, por exemplo:

```text
src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities.json
src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/terrafirmacraft/trees.json
src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/alexscaves/entities.json
src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/yungs/structures.json
```

O nome exato dos namespaces será gerado da modlist/runtime; não criar diretório nominal para mod ausente.

### Passo 4 — Validação factual

Criar posteriormente:

```text
scripts/compendium/validate_editorial_corpus.py
scripts/compendium/editorial_coverage.py
```

Validar:

- [ ] ID existe ou está marcado como conteúdo legado/opcional;
- [ ] locale é `pt_br`;
- [ ] toda entrada curada tem resumo;
- [ ] seção que afirma stat mecânico aponta para dado técnico, não repete número hardcoded sem necessidade;
- [ ] links internos referenciam IDs válidos;
- [ ] fontes obrigatórias estão preenchidas;
- [ ] texto não contém placeholders/TODO;
- [ ] nenhuma descrição foi gerada como “fato” sem revisão.

### Passo 5 — Revisão linguística

Checklist:

- [ ] português brasileiro natural;
- [ ] ortografia e concordância;
- [ ] nomenclatura consistente;
- [ ] termos do Minecraft traduzidos de forma uniforme;
- [ ] nomes próprios oficiais preservados quando necessário;
- [ ] frases não confundem lore com mecânica;
- [ ] descrição não promete comportamento dependente de versão/config como universal.

## Cobertura da modlist

O corpus não será fechado com uma seleção manual pequena. O relatório do 10.02 deve alimentar esta fila e permitir responder por namespace:

```text
Total de entradas relevantes
AUTO com resumo genérico
CURATED completo
ADAPTER aguardando dado
IGNORED com motivo
ERROR
```

`ERROR > 0` bloqueia release do Stage 10.

A implementação deve percorrer os mobs, árvores, cultivos, biomas, estruturas e dimensões da modlist atual e produzir descrição completa conforme a ficha aplicável, em lotes revisáveis. Mods adicionados depois recebem página `AUTO` imediatamente e entram no backlog editorial do próximo refresh.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/PtBrLocalizationCompletenessTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/EditorialCorpusSchemaTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/EditorialReferenceIntegrityTest.java
```

Infraestrutura de backlog já coberta por:

```text
scripts/compendium/test_editorial_backlog.py
scripts/compendium/test_inventory_modlist.py
```

Casos obrigatórios:

- [ ] nenhuma chave própria da UI sem `pt_br`;
- [ ] entrada `CURATED` sem resumo falha;
- [ ] referência para ID inexistente falha ou exige marcação opcional explícita;
- [ ] placeholder `TODO`/`TBD` bloqueia corpus final;
- [x] drift/rerun preserva progresso editorial e entradas órfãs em vez de resetar ou apagar silenciosamente;
- [x] entrada `ERROR` malformada preservada pelo 10.02 permanece no backlog como `BLOCKED`;
- [ ] textos técnicos usam valores do provider quando o valor puder mudar por config/runtime.

## Estado atual

O **pipeline de backlog editorial** está implementado e automatizado, mas o subplano 10.10 permanece aberto. Ainda faltam o schema/carga dos pacotes editoriais por namespace, os validadores factuais do corpus, a produção/revisão do conteúdo pt-BR e o fechamento da cobertura real do modpack.

## Acceptance

O subplano fecha quando o pipeline editorial pt-BR estiver implementado, o backlog derivado do modpack tiver sido processado segundo a política de cobertura e todas as entradas entregues como `CURATED` tiverem texto completo, revisado e com proveniência suficiente.
