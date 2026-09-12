# Miner's Delight +

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c9b9b5e1eb3b9cadda
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Miner's Delight `1.4.5`, Farmer's Delight `1.3.4` e Lodestone `1.8.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Miner's Delight +
- **Arquivo JAR:** `minersdelight-1.21.1-1.4.5.jar`
- **Versão 1.21.1:** 1.4.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Comida
- **Função:** Addon de Farmer's Delight focado em culinária de mineração/cavernas: Copper Pot/Cup, Cave Carrots, Gossypium, ingredientes derivados de mobs/fishing e Sticky Basket com extração por hopper e comportamento de adesão.
- **Dependências:** OBRIGATÓRIAS em 1.4.5: Lodestone >=1.7.0 e Farmer's Delight >=1.3; ambas side BOTH no neoforge.mods.toml do source exato.
- **Sobreposição:** Compartilha ecossistema culinário com Farmer's Delight e seus addons, mas não é redundância simples. Interseções técnicas: recipes/tags, knives/loot de mobs, fishing, crops de caverna e automação por hopper no Sticky Basket.
- **Compatibilidade/Riscos:** Hard dependencies: Farmer's Delight e Lodestone. Sobreposição principal em recipes/foods e loot de bats, silverfish, arthropods/squids; Sticky Basket toca hopper, movimento de entidade e waterlogging. A página oficial separa features 1.20+ de 1.19; não transportar detalhes antigos sem runtime/source.
- **Observações:** JAR físico `minersdelight-1.21.1-1.4.5.jar`, mod id `minersdelight`, runtime 1.4.5. CurseForge file ID 8011049, 29/04/2026, Release. Source exato usa Java 21/Minecraft 1.21.1 e Lodestone 1.7.2.391 no build. Project page ainda traz aviso genérico de beta/instabilidade.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais de 1.4.5 + repositório oficial SammySemicolon/MinersDelight pinado no commit 82196658505f84fc877b580583882e57f55c2e34.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/miners-delight-plus/files/8011049 | https://modrinth.com/mod/miners-delight/version/1.4.5 | https://github.com/SammySemicolon/MinersDelight/commit/82196658505f84fc877b580583882e57f55c2e34
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê reconstruído para 1.4.5 com release oficial file ID 8011049 e source 1.21 pinado no commit 8219665. Dependências corrigidas: Farmer's Delight + Lodestone. Changelog exato: missing texture fix.
- **Histórico da decisão:** 2026-09-10 — ficha reconstruída no padrão Alex's Mobs. Corrigida dependência omitida de Lodestone e pinado source exato da linha 1.21. Hipótese anterior de Corrupted Ore/Silk Touch não foi sustentada por evidência 1.4.5 e foi removida.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DESTA FICHA.** O runtime físico é `minersdelight-1.21.1-1.4.5.jar`, mod id `minersdelight`, versão `1.4.5`. A release NeoForge 1.21.1 é o CurseForge file ID `8011049`, publicada em 29/04/2026. O código da linha 1.21 foi auditado no commit oficial `82196658505f84fc877b580583882e57f55c2e34`, correspondente à atualização de versão 1.4.5. O changelog exato de 1.4.5 é apenas **“missing texture fix”**; alterações atribuídas a outras versões não são promovidas para esta release sem evidência.

## 1. Identidade, versão e authority
- **Mod:** Miner's Delight + / Miner's Delight.
- **JAR físico:** `minersdelight-1.21.1-1.4.5.jar`.
- **Mod id:** `minersdelight`.
- **Runtime:** `1.4.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **CurseForge:** project ID `689630`, file ID `8011049`, release de 29/04/2026.
- **Ambiente oficial:** Client & Server.
- **Licença publicada:** All Rights Reserved.
- **Source oficial auditado:** `SammySemicolon/MinersDelight`, branch 1.21, commit `82196658505f84fc877b580583882e57f55c2e34`.
- **Papel no pack:** extensão gastronômica de Farmer's Delight orientada à exploração subterrânea, mineração, ingredientes de cavernas e utilitários de cozinha/armazenamento.

## 2. Dependências obrigatórias de 1.4.5
O `neoforge.mods.toml` do source exato confirma duas dependências obrigatórias, ambas em `BOTH`:
- **Lodestone:** range `[1.7.0,)`.
- **Farmer's Delight:** range `[1.3,)`.

O build da linha auditada usa Minecraft 1.21.1, Java 21 e Lodestone `1.7.2.391`. Portanto, a ficha antiga que listava apenas Farmer's Delight estava incompleta: **Lodestone também é hard dependency**.

## 3. Escopo funcional
O projeto oficial define Miner's Delight como addon de Farmer's Delight que torna conteúdo gastronômico mais acessível durante mineração. O foco 1.20+ publicado pelo autor inclui utensílios de cobre, crop/flower subterrâneos, novos ingredientes associados a mobs de cavernas e água e um bloco utilitário com comportamento físico próprio.

A página do projeto mantém um aviso genérico de “Beta Version”, mas o artefato 1.4.5 específico está classificado como **Release** no CurseForge e no Modrinth. Isso não elimina a recomendação do autor de testar o modpack e manter backups; apenas separa status do projeto de status do arquivo.

## 4. Copper Pot e Copper Cup
### Copper Pot
- Utensílio de cooking menor que o Cooking Pot convencional de Farmer's Delight.
- Na documentação 1.20+, é especializado em refeições rápidas.
- Stews e soups preparados nele são servidos em **Copper Cups**.

### Copper Cup
- Recipiente semelhante a bucket, mas não aceita lava.
- A documentação 1.20+ descreve porções de metade do tamanho e consumo aproximadamente duas vezes mais rápido, voltadas a compartilhamento e pausas curtas de refeição.

A ficha não transfere automaticamente para 1.21.1 detalhes exclusivos da seção 1.19 da página oficial — por exemplo, efeitos específicos descritos naquela seção — quando a seção 1.20+ não os repete.

## 5. Cave Carrots e Gossypium
### Cave Carrots
- Crop próprio encontrado em cavernas na forma selvagem.
- Pode ser colhido e replantado.
- O texto oficial apresenta-o como alternativa vegetal com sabor semelhante a carne, integrando a progressão culinária subterrânea.

### Gossypium
- Flor associada a wild cave carrots.
- Pode florescer nesse contexto.
- É utilizada para produzir string.

Esses elementos fazem o mod participar não apenas de recipes, mas também de aquisição de recursos/crops no mundo. Spawn/generation exatos e regras de crescimento devem ser tomados dos dados/runtime quando necessários para automação; esta ficha não inventa taxas ou biome tags que não foram auditadas.

## 6. Ingredientes vindos de mobs e ferramentas
A documentação oficial 1.20+ confirma conteúdo culinário ligado a:
- **Bats:** snack obtido nas cavernas, com knife participando da obtenção.
- **Silverfish eggs:** ingrediente raro/difícil de obter e associado ao ecossistema de silverfish.
- **Arthropods:** insect meat obtido com participação de knife.
- **Squids:** conteúdo de fishing/food; o texto recomenda finalização com knife para preservar melhor a carne.

Isso cria superfície compartilhada com qualquer mod que altere loot de bat, silverfish, arthropods, squid, fishing ou semântica de knives. Não há conflito confirmado no pack por si só; é uma área de teste.

## 7. Sticky Basket
O bloco **Sticky Basket** é descrito como variante funcional do basket de Farmer's Delight:
- itens colocados nele só podem ser retirados por hoppers;
- entidades em contato ficam limitadas a pular apenas meio bloco;
- pode funcionar como donation basket ou armadilha simples;
- quando waterlogged, perde a aderência, permitindo a saída da entidade presa.

Essa combinação envolve inventário/automation, colisão/movimento de entidades e waterlogging. Deve ser testada em servidor, não apenas em singleplayer.

## 8. Integração com Farmer's Delight
Miner's Delight é explicitamente um addon de Farmer's Delight, e a dependência é hard requirement no manifesto. O contrato operacional inclui:
- uso do ecossistema culinário/utensílios do provider;
- knives nas cadeias de obtenção de ingredientes;
- basket como referência de comportamento para Sticky Basket;
- recipes/foods próprios integrados ao domínio gastronômico do addon base.

**Ownership:** Farmer's Delight continua sendo o provider da infraestrutura culinária base. Miner's Delight adiciona conteúdo temático e comportamentos próprios; não deve ser usado como authority para regras internas do Farmer's Delight.

## 9. Lodestone
Lodestone é hard dependency no source 1.4.5 (`[1.7.0,)`). A ficha não infere quais subsistemas visuais/técnicos de Lodestone são usados internamente sem necessidade; a informação operacional relevante é que remover Lodestone ou usar versão fora do range impede o contrato suportado de carregamento.

## 10. Client/server e multiplayer
As plataformas oficiais classificam 1.4.5 como **Client and Server**, e o manifesto marca as dependências em `BOTH`. O mod possui conteúdo que afeta mundo, blocks, crops, loot/food e interação de entidades, portanto deve existir em ambos os lados do ambiente normal do pack.

Áreas particularmente sensíveis a sincronização:
- inventário e extração do Sticky Basket;
- cooking/serving no Copper Pot/Cup;
- crescimento/colheita de crops;
- mob/fishing drops;
- recipe/tag reload.

## 11. Persistência e dados
O source exato confirma um mod NeoForge normal com assets/data próprios e integração a dependencies; contudo esta auditoria não atribui formato de save proprietário nem networking específico sem evidência concreta. Estados persistidos de containers/crops devem ser validados no runtime em vez de inferidos por convenção.

Para datapacks/KubeJS, recipes, tags e loot devem ser referenciados pelos IDs realmente presentes em 1.4.5. Não transportar nomes de 1.19/backport como se fossem garantidos na linha 1.21.

## 12. Changelog exato de 1.4.5
Tanto CurseForge quanto Modrinth registram para a versão 1.4.5:
- **`missing texture fix`**.

Uma hipótese anterior desta auditoria sobre mudança de **Corrupted Ore/Silk Touch** foi procurada no source/changelog exatos e **não foi confirmada**. Ela foi deliberadamente omitida do catálogo técnico em vez de preencher a lacuna por inferência.

## 13. Compatibilidade e sobreposição no pack
### Outros addons de Farmer's Delight
A sobreposição é principalmente temática e de catálogo: vários addons podem acrescentar foods, ingredients, recipes e utensílios ao mesmo ecossistema. Isso não torna Miner's Delight redundante, pois seu escopo específico é cave/mining food e seus blocos/crops têm comportamento próprio.

### Loot de mobs e fishing
Como o mod adiciona obtenção culinária envolvendo bats, silverfish, arthropods e squids, qualquer outro mod/datapack que substitua ou injete nessas loot tables pode alterar rendimento ou duplicar drops. Testar a composição real de loot do pack.

### Automação
Sticky Basket depende de hopper para extração, o que pode interagir com sistemas logísticos. O comportamento documentado deve ser preservado: automação válida não significa que players devam conseguir retirar o conteúdo por qualquer rota de UI.

## 14. Riscos técnicos
- **Hard dependency esquecida:** Lodestone é obrigatória; removê-la quebra a linha suportada de 1.4.5.
- **Recipe/loot overlap:** addons culinários e datapacks podem alterar recipes/tags/loot nas mesmas superfícies.
- **World acquisition:** wild cave carrots devem ser confirmados em chunks/estruturas da geração real do pack.
- **Sticky Basket:** colisão, waterlogging e extração por hopper são comportamentos que podem regredir com mods de movimento/physics/automation.
- **Documentação multi-versão:** a página oficial mantém seções separadas 1.20+ e 1.19; não misturar regras da seção antiga com 1.21.1.
- **Status “Beta” do projeto:** o autor ainda alerta para instabilidade mesmo que o arquivo 1.4.5 seja marcado Release.

## 15. Matriz de validação obrigatória
> A lista abaixo é uma matriz de testes requerida; não significa que os testes já passaram.

### Boot/dependências
- Boot de servidor dedicado com Farmer's Delight e Lodestone presentes.
- Confirmar ausência de missing-mod/version-range error.
- Entrar com cliente com o mesmo conjunto de mods.

### Cooking
- Craft/obtenção do Copper Pot.
- Preparar recipe suportada no Copper Pot.
- Servir em Copper Cup.
- Consumir porção e conferir comportamento de container/stack.
- Quebrar/recolocar utensílio/container relevante e verificar estado conforme runtime.

### Crops
- Encontrar wild cave carrots em mundo/chunks compatíveis.
- Colher e replantar.
- Validar crescimento.
- Obter Gossypium e produzir string pela cadeia real de recipes.
- Save/restart com crops plantadas.

### Mob drops/fishing
- Bat com e sem knife.
- Silverfish sob rotas documentadas de obtenção de eggs.
- Arthropods com knife.
- Squid/glow squid e fishing onde aplicável.
- Conferir duplicidade/remoção causada por outros datapacks de loot do pack.

### Sticky Basket
- Inserir itens.
- Tentar extração prevista por hopper.
- Testar interação do player sem assumir que deve extrair.
- Colocar entidade em contato e medir limitação de salto.
- Waterlog e confirmar perda de adesão.

### Data/multiplayer
- `/reload` com recipes/tags sem erro.
- Dois clientes interagindo com cooking/container.
- Restart completo do servidor.
- Validar que itens/blocos não perdem estado relevante.

## 16. Evidências e limites
- **Autoridade física:** `minersdelight-1.21.1-1.4.5.jar` na modlist atual.
- **CurseForge oficial:** file ID `8011049`, Release, NeoForge 1.21/1.21.1, 29/04/2026.
- **Modrinth oficial:** 1.4.5, Release, NeoForge, Client and Server, Lodestone + Farmer's Delight required, changelog `missing texture fix`.
- **Source oficial:** branch 1.21 no repositório `SammySemicolon/MinersDelight`.
- **Pin auditado:** `82196658505f84fc877b580583882e57f55c2e34`.
- **Manifesto do source exato:** Lodestone `[1.7.0,)`, Farmer's Delight `[1.3,)`, ambos `BOTH`.
- **Página oficial:** fonte das features 1.20+ descritas nesta ficha.
- **Limite:** detalhes internos não verificados no source específico não foram inventados. Em especial, a alegação Corrupted Ore/Silk Touch não foi confirmada para 1.4.5 e não integra o dossiê.
