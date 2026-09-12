# Amplified Nether

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81dfa4bff9137a79a15f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Amplified Nether
- **Arquivo JAR:** `Amplified_Nether_26.2_v1.2.16.jar`
- **Versão 1.21.1:** 1.2.16
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Overhaul server-side/worldgen do Nether que eleva a dimensão para 256 blocos e aplica terreno amplificado, montanhas e distribuição tridimensional de biomas. Não adiciona novos biomas, mobs, itens ou estruturas; altera a geometria onde conteúdo vanilla/modded é colocado.
- **Dependências:** Nenhuma hard dependency externa relevante localizada além do loader/worldgen vanilla. Compatível em princípio com muitos biome/structure mods, mas precisa de validação por composição.
- **Sobreposição:** Sobrepõe worldgen do Nether na camada de relevo/altura; Better Nether adiciona biomas/conteúdo e pode coexistir. Mods de estruturas continuam inserindo estruturas sobre esse terreno. Não substitui content mods do Nether.
- **Compatibilidade/Riscos:** Incompatibilidade formal upstream com Incendium. Incendium não foi localizado na modlist top-level atual. Better Nether está instalado e o upstream diz que geralmente é compatível, mas isso não dispensa teste. Mundo existente: não adicionar a um Nether já gerado sem reset/novo mundo, pois a mudança de altura/terrain causa seams e inconsistência. Estruturas como YUNG's Better Nether Fortresses e outras do pack devem ser validadas quanto a altura, placement e acessibilidade.
- **Observações:** A regra de migração é crítica: testar em Nether novo/resetado. Não usar chunk existente como validação de worldgen novo. A incompatibilidade com Incendium é formal upstream; coexistência com outros mods deve ser classificada por evidência, não por categoria.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficiais Amplified Nether 1.2.16 e fontes já auditadas no dossiê. Reconciliação final: JAR permanece `Amplified_Nether_26.2_v1.2.16.jar` e runtime `1.2.16`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/amplified-nether
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #27: `Amplified_Nether_26.2_v1.2.16.jar` / `1.2.16` conferidos contra a modlist atual; marcador `26.2` do filename preservado sem confundi-lo com a versão do mod; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `Amplified_Nether_26.2_v1.2.16.jar`, versão do mod **1.2.16** para Minecraft 1.21.1. O `26.2` no filename não torna o JAR incompatível com 1.21.1. Este mod altera **terreno/altura/biome placement do Nether**, não adiciona conteúdo novo.

## 1. O que ele altera
Amplified Nether expande o espaço vertical do Nether para **256 blocos** e reconstrói a geração de terreno com relevo muito mais amplificado. O objetivo é transformar o Nether de uma dimensão relativamente comprimida em um espaço vertical com grandes montanhas, vales, massas rochosas e biomas distribuídos tridimensionalmente.
A consequência prática é que qualquer conteúdo que dependa de coordenadas Y, terrain surface, cavern volume, biome distribution ou estrutura posicionada no Nether passa a operar sobre um espaço diferente do vanilla.

## 2. O que NÃO adiciona
O projeto upstream é explícito: Amplified Nether não adiciona por conta própria:
- novos biomas;
- novos mobs;
- novos itens;
- novos blocos de progressão;
- novas estruturas.
Quando aparecem biomas/estruturas/mobs adicionais no Nether desta instância, a authority é outro mod. Amplified Nether fornece **o terreno onde eles são colocados**.

## 3. Altura e geometria
A mudança para 256 blocos de altura útil altera:
- quantidade de superfície/volume disponível;
- distribuição vertical de cavernas e terrain;
- distância entre regiões navegáveis;
- frequência percebida de estruturas por área explorada;
- rotas de portal e acessibilidade;
- custo de exploração/render/chunk generation.
Mods que assumem faixas Y próximas do Nether vanilla podem continuar funcionando tecnicamente, mas gerar conteúdo em posições pouco acessíveis ou com distribuição visual diferente.

## 4. Biomas tridimensionais
Os biomas continuam pertencendo ao registry dos providers de biome, mas sua colocação ocorre sobre terreno tridimensional mais extenso. Isso é especialmente importante com **Better Nether**, que adiciona conteúdo/biomas, porque a existência do biome não garante que a distribuição final tenha a frequência esperada.

## 5. Compatibilidade formal: Incendium
O upstream declara **Incendium como incompatível**. Essa é incompatibilidade formal e deve permanecer registrada mesmo quando o mod não está instalado.
Na modlist física atual, **Incendium não foi localizado como top-level**. Logo:
- a incompatibilidade existe upstream;
- ela não é conflito ativo desta instância no snapshot atual;
- se Incendium entrar no futuro, não tratar a combinação como “talvez compatível” sem nova evidência.

## 6. Better Nether
O upstream indica que Amplified Nether é normalmente compatível com **Better Nether**. O pack instala Better Nether 21.0.26.
Isso significa compatibilidade arquitetural provável, não garantia de balanceamento. Validar:
- todos os biomas Better Nether;
- vegetation/feature placement;
- estruturas/POIs;
- altura e acessibilidade;
- ausência de biome holes ou terrain seams.

## 7. Estruturas presentes no pack
O pack também usa estruturas do Nether, incluindo **YUNG's Better Nether Fortresses 3.1.5** e compat relacionada a Cataclysm. Amplified Nether não substitui essas estruturas, mas o terreno ampliado pode mudar:
- Y de placement;
- exposição/enterramento;
- distância vertical até rotas transitáveis;
- volume disponível em volta da estrutura;
- percepção de frequência.
A estrutura deve ser encontrada e percorrida em **Nether gerado depois da instalação**, não validada em chunks antigos.

## 8. Portais
Portais continuam usando regras do Minecraft/mods de portal, mas terrain drasticamente diferente pode fazer o destino correspondente surgir:
- dentro de massa rochosa;
- em altura extrema;
- próximo a precipício/lava;
- muito acima/abaixo de uma rota desejada.
Qualquer sistema custom que normalize spawn de portal precisa ser testado com a faixa vertical ampliada.

## 9. Existing world / migração
Esta é uma das regras mais importantes da ficha: **não adicionar Amplified Nether a um mundo cujo Nether já foi explorado e considerar o resultado válido**.
Chunks antigos preservam worldgen anterior; chunks novos usam o novo terrain. A fronteira pode gerar seams/paredões e experiência incoerente. Para validação limpa:
- usar mundo novo; ou
- resetar a dimensão Nether de forma controlada e com backup.
A ficha não recomenda apagar dimensão automaticamente; registra apenas o requisito técnico de geração limpa.

## 10. Server-side
O projeto é adequado a uso server-side/worldgen. O servidor define chunks/biomes/terrain; clientes recebem o mundo resultante. Recursos visuais de outros mods ainda podem exigir instalação client-side separadamente.

## 11. Sobreposição funcional
### Better Nether
Complementar: Better Nether fornece conteúdo/biomes; Amplified Nether fornece terreno amplificado.
### Structure mods
Complementares, mas competem por espaço/placement e podem depender de heightmaps/tags.
### Outros terrain overhauls do Nether
Se outro mod também substitui noise settings/dimension generator, a chance de incompatibilidade estrutural é muito maior. Não assumir merge automático de dois terrain providers.

## 12. Riscos específicos do pack
1. **Chunk seams** em mundo existente.
2. **Estruturas inacessíveis** por Y/terrain incomum.
3. **Portal destinations perigosos**.
4. **Biome distribution** diferente do esperado por Better Nether.
5. **Performance de worldgen** em Nether muito mais volumoso.
6. **Mods com Y hardcoded** podem gerar loot/ores/structures fora de contexto.
7. **Navigation** de mobs e física em paredes/precipícios maiores.
8. **Map/waypoint mods** podem representar a dimensão normalmente, mas exploração fica mais vertical e precisa de testes de teleport/marker.

## 13. Matriz de validação
1. Criar mundo novo ou Nether resetado para teste.
2. Gerar pelo menos vários milhares de blocos em múltiplas direções.
3. Confirmar teto/faixa vertical ampliada e ausência de chunk seams.
4. Encontrar todos os principais biomas vanilla do Nether.
5. Encontrar amostra representativa de biomas Better Nether.
6. Validar vegetação/features de Better Nether em terrain amplificado.
7. Encontrar YUNG's Better Nether Fortress e percorrer toda a estrutura.
8. Testar compat Cataclysm ↔ fortress no contexto real.
9. Encontrar outras estruturas Nether do pack e registrar Y de spawn.
10. Criar portais Overworld↔Nether em diferentes coordenadas e alturas.
11. Verificar spawn de mobs em áreas abertas, cavernas e biomas modded.
12. Verificar ores/resources de mods com distribuição vertical.
13. Dedicated server: pregeneration/chunk generation e TPS.
14. Client: render distance/chunk loading e uso de memória em vistas verticais grandes.
15. Confirmar ausência física de Incendium; se aparecer no futuro, bloquear compat até nova auditoria.

## 14. Versão 1.2.16
A release `v1.2.16` é a build reconhecida pela publicação para a linha 1.21.x. O filename inclui marcador `26.2`, mas a própria release publica suporte que inclui versões 1.21.x; portanto o runtime físico atual é válido para 1.21.1.
Não corrigir o filename manualmente nem inferir que “26.2” é `modVersion`.

## 15. Regras para outros chats
- Nunca atribuir mobs/biomes/structures extras a Amplified Nether.
- Worldgen testado em chunk antigo **não** valida o mod.
- Se uma estrutura “sumiu”, primeiro checar height/placement e biome/structure tags antes de declarar incompatibilidade.
- Se um mod depender de faixa Y específica, revisar source/config desse consumidor.
- Incendium é incompatibilidade upstream formal; Better Nether é coexistência suportada, mas deve ser runtime-tested.

## 16. Fontes e confiança
**Authority física:** modlist de 07/09/2026.
**Upstream:** [CurseForge — Amplified Nether](https://www.curseforge.com/minecraft/mc-mods/amplified-nether) e publicação oficial/Modrinth da release 1.2.16.
**Projeto:** guia de gameplay/worldgen e modlist atual para Better Nether/YUNG's.
**Confiança:** alta para altura, papel de terrain, ausência de conteúdo novo e incompatibilidade Incendium. Distribuição final de biomes/estruturas neste pack exige geração runtime porque depende da composição inteira do worldgen.
