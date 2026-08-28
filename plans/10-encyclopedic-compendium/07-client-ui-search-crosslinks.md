# 10.07 — Interface cliente, pesquisa, filtros e crosslinks

## Objetivo

Entregar uma interface própria de Compêndio que funcione como guia de campo/bestiário sem misturar sua navegação com o canvas da árvore de habilidades. A UI deve continuar coerente visualmente com o RPG Skill Tree e respeitar GUI scale/resoluções menores.

## Arquivos previstos

Criar/alterar principalmente:

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/EncyclopediaScreen.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientEncyclopediaState.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientEncyclopediaIndex.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/EncyclopediaEntryView.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientKeyMappings.java`
- opcionalmente pequeno botão/atalho em `RpgSkillTreeScreen`, sem acoplar os dois states.
- `src/main/resources/assets/rpgskilltree/textures/gui/encyclopedia/*` apenas para recursos próprios necessários.

## Entrada na UI

Registrar `key.rpgskilltree.open_compendium` na mesma categoria de key mappings. O keybind deve ser configurável e o default final deve ser escolhido após checar colisões do pack; não sobrescrever binding de JourneyMap/outros mods deliberadamente.

Pode existir um botão “Compêndio” na UI do RPG como atalho secundário, mas a tela continua classe/fluxo próprios.

## Layout base

Em resolução normal:

- barra superior: título, progresso de coleção e pesquisa;
- coluna esquerda: categorias/subcategorias;
- coluna central: lista/grid de entries filtradas;
- painel direito: entrada selecionada;
- tabs/seções dentro da entrada quando o conteúdo exceder uma página.

Em GUI scale alta ou janela estreita, colapsar para dois painéis ou navegação sequencial em vez de cortar texto/controles.

## Estados visuais

### Unknown

- mostrar silhueta/placeholder genérico quando a própria existência da entrada pode aparecer na coleção;
- alternativamente ocultar completamente conforme `visibilityPolicy`;
- não revelar título, descrição, biome ou loot via tooltip/pesquisa.

### Discovered

- nome, icon/render, resumo e seções básicas liberadas;
- seção avançada locked mostra indicação localizada sem expor conteúdo.

### Studied

- todas as seções autorizadas liberadas.

## Rendering por alvo

- entities: render 3D seguro com rotação/zoom limitados, sem criar entidade server-side;
- blocks/items/flora: `ItemStack`/block item ou render hint próprio;
- biomas: icon/representative block ou imagem própria quando existir;
- estruturas: ícone/arte própria ou preview previamente produzido; nunca tentar renderizar chunks/structure template inteiro por frame.

Se um provider usar entidade com renderer que falha sem world/context, capturar fallback visual sem derrubar a tela inteira.

## Pesquisa

`ClientEncyclopediaIndex` deve normalizar texto para PT-BR:

- lowercase locale-safe;
- remoção opcional de diacríticos para busca (`dragao` encontra `dragão`);
- título;
- aliases editoriais;
- tags;
- categoria/subcategoria;
- provider quando o usuário filtrar por origem.

A pesquisa respeita visibilidade. Texto locked não entra no índice antes do unlock correspondente.

## Filtros

Mínimo:

- categoria;
- descoberto / não descoberto / estudado;
- dimensão;
- provider/mod de origem;
- tags relevantes (`boss`, `domesticavel`, `aquatico`, `arvore`, `estrutura`, etc.) quando presentes.

Não hardcode lista de mods: providers vêm do catálogo ativo.

## Crosslinks

Seções podem linkar entries relacionadas:

- criatura -> bioma;
- criatura -> estrutura;
- flora -> bioma;
- estrutura -> boss/hostis;
- bioma -> fauna/flora/estruturas.

Click navega sem fechar a tela e mantém back-stack. Crosslink para entrada locked respeita política de visibilidade e não vaza nome.

## Progresso da coleção

Exibir progresso significativo por categoria e total:

- discovered / entries ativas;
- studied / entries que suportam estudo.

Providers ausentes não contam no denominador. Entries explicitamente excluídas da coleção também não contam.

## Acessibilidade/UX

- nenhuma informação essencial apenas por cor;
- tooltips localizados;
- foco de teclado na busca;
- scroll previsível;
- Escape retorna à tela anterior;
- texto quebra linha em largura real do painel;
- respeitar GUI scale;
- evitar animações pesadas como requisito funcional.

## Testes

A maior parte da UI é client runtime, mas extrair lógica testável:

- pesquisa com/sem acento;
- locked text não aparece em resultados;
- ordenação determinística;
- filtros compostos;
- back-stack de crosslinks como modelo puro se aplicável;
- progresso ignora provider ausente;
- fallback quando target visual não resolve.

Fazer smoke manual/client test em pelo menos GUI scale 2, 3 e 4, janela 854x480 e resolução desktop comum.

## Acceptance

- [ ] Existe tela própria e key mapping configurável.
- [ ] UI não vaza conteúdo locked em nome, tooltip, busca ou crosslink.
- [ ] Pesquisa PT-BR tolera ausência de acento.
- [ ] Categorias, filtros e crosslinks funcionam com catálogo dinâmico.
- [ ] Layout permanece utilizável em GUI scale alto/resolução baixa.
- [ ] Render failure de uma entry não derruba o Compêndio inteiro.
