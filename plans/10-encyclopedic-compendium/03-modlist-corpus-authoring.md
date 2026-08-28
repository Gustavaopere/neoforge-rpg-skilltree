# 10.03 — Corpus da modlist e authoring enciclopédico

## Objetivo

Converter a modlist real do pack em uma matriz verificável de conteúdo enciclopédico e, depois, em entradas completas em PT-BR. O objetivo não é cadastrar “mods”; é cadastrar os elementos do mundo que o jogador encontra.

## Fonte de verdade

Antes de escrever qualquer entrada em massa:

1. obter a modlist vigente do usuário;
2. normalizar `mod_id`, versão e função do JAR;
3. ignorar bibliotecas, APIs, compat-only e addons que não acrescentem alvo enciclopédico próprio;
4. iniciar um runtime de teste com o pack/alvo e enumerar registries relevantes;
5. cruzar modlist -> registries -> documentação oficial/código do provider;
6. só então autorar descrição, mecânicas e relações.

A modlist muda; portanto nomes/versionamentos encontrados durante o planejamento são sementes, não uma lista congelada.

## Famílias já confirmadas como prioritárias

A matriz deve começar por conteúdo vanilla e por providers de mundo/entidades já observados na modlist recente, incluindo, quando continuarem presentes na modlist usada na execução:

- `alexsmobs` — fauna e criaturas;
- Alex's Caves Neo — criaturas, biomas/cavernas, flora e recursos próprios;
- `betterend` e `betternether` — biomas, flora, criaturas/recursos quando existentes;
- YUNG's Better Dungeons, Strongholds, Mineshafts, Nether Fortresses, Ocean Monuments, Witch Huts, Desert Temples, Jungle Temples e End Island — estruturas;
- `cataclysm` — hostis, bosses e estruturas associadas;
- ecossistemas de biomas/árvores presentes no pack, incluindo Dynamic Trees e addons ativos;
- demais mods de fauna, worldgen, estruturas, agricultura, magia natural e exploração encontrados na modlist vigente.

Essa relação não autoriza inventar IDs concretos. Cada mob, árvore, planta, bioma e estrutura entra somente depois de ser confirmado no registry/documentação da versão instalada.

## Matriz de cobertura

Criar durante a implementação um inventário verificável, preferencialmente em `generated/encyclopedia-inventory.json` ou relatório equivalente produzido por script/validator, contendo por provider:

- mod id e versão observada;
- entities registradas;
- biomes registradas;
- structures registradas;
- blocos/itens candidatos a flora/recursos;
- entrada existente?;
- categoria atribuída;
- status editorial: `CURATED`, `EXCLUDED_WITH_REASON`, `UNRESOLVED`;
- fonte usada para fatos não inferíveis do registry.

O release gate não pode aceitar `UNRESOLVED` para providers declarados “cobertos”.

## Critério para incluir/excluir

### Incluir por padrão

- mobs/entidades vivas encontráveis;
- bosses;
- árvores, plantas, fungos e cultivos com presença no mundo;
- biomas/dimensões exploráveis;
- estruturas geradas;
- recursos naturais cuja descoberta tenha valor real para exploração/progressão.

### Excluir por padrão

- bibliotecas/API;
- blocos puramente decorativos sem valor de descoberta;
- cada variante técnica de bloco de construção;
- máquinas/peças Create sem função de guia de campo;
- itens intermediários sem contexto de exploração;
- entidades internas, marcadores, projectiles e helpers invisíveis.

Toda exclusão em massa precisa de regra documentada; exceções podem ser curadas manualmente.

## Template editorial por categoria

### Fauna/hostis/bosses

- nome e resumo;
- classificação;
- onde aparece;
- comportamento e hostilidade;
- atributos/ameaças relevantes;
- reprodução/domesticação quando aplicável;
- variantes;
- drops/recursos;
- relações com biomas/estruturas;
- observações mecânicas verificadas.

### Flora

- tipo biológico/funcional;
- ambiente e substrato;
- crescimento/propagação quando verificável;
- sazonalidade/clima quando o provider realmente modelar isso;
- colheita e recursos;
- usos relevantes;
- relações com biomas.

### Estruturas

- tipo e função;
- dimensões/biomas onde pode gerar;
- características reconhecíveis;
- ameaças e habitantes;
- recursos/loot em termos seguros e verificados;
- estruturas/biomas relacionados.

### Biomas/ambientes

- dimensão;
- clima/ambiente quando exposto;
- fauna, flora e estruturas relacionadas;
- perigos e recursos;
- notas de exploração.

## Qualidade das descrições

- Texto próprio em PT-BR, sem copiar parágrafos de wiki/CurseForge.
- Fatos mecânicos devem ser rastreáveis a código, documentação oficial ou comportamento testado da versão instalada.
- Não transformar a descrição em propaganda do mod ou nota de integração.
- Não afirmar spawn, loot, dano, tameabilidade, reprodução ou requisito que não foi confirmado.
- Conteúdo variável por config deve dizer que é configurável ou omitir número absoluto quando necessário.

## Automação de authoring

Planejar um validador/script que liste targets sem entrada e entradas sem target. Ele pode acelerar o inventário, mas **não deve gerar prosa factual por inferência**. Geração automática serve para skeleton, IDs, nomes traduzíveis e relações de registry; a descrição final é curada.

## Testes/validação

- relatório modlist -> provider -> registry é determinístico;
- nenhuma biblioteca é contada como “conteúdo coberto” por quantidade;
- entradas `CURATED` apontam para target existente na configuração de teste;
- exclusões têm motivo;
- provider removido não deixa target quebrado no catálogo ativo;
- cobertura pode ser comparada entre duas versões da modlist para mostrar additions/removals.

## Acceptance

- [ ] Existe matriz canônica da modlist vigente para conteúdo enciclopédico.
- [ ] Fauna, flora, biomas e estruturas dos providers cobertos estão enumerados por registry.
- [ ] Cada target é `CURATED`, `EXCLUDED_WITH_REASON` ou explicitamente bloqueia o gate como `UNRESOLVED`.
- [ ] Descrições completas seguem template por categoria e fatos verificáveis.
- [ ] Nenhum conteúdo é inventado para preencher lacuna de documentação.
