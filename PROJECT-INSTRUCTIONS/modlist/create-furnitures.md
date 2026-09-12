# Create: Furnitures

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818c88e3db02efd1a6a2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Furnitures
- **Arquivo JAR:** `create_furnitures-1.1.2-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Tecnologia
- **Função:** Addon decorativo/funcional de Create com tables, chairs, stools, pillars, variantes em casings/cores e assentos funcionais.
- **Dependências:** Create 6.0.10 físico. Upstream declara compatibilidade com Create 5/6 em Minecraft 1.20.1/1.21.1.
- **Sobreposição:** Compartilha espaço decorativo/assentos com Immersive Furniture e [Let's Do] Furniture fisicamente presentes. Sobreposição é por conteúdo/estética/recipes; não há evidência de duplicidade técnica integral.
- **Compatibilidade/Riscos:** Riscos centrais: seat/mount state órfão, multiplayer occupant desync, dismount inseguro, regressão de FPS em Bridge Structures, custo de render/modelos e overlap de recipes com outros furniture mods.
- **Observações:** JAR físico `create_furnitures-1.1.2-neoforge-1.21.1.jar`, mod id `create_furnitures`, runtime 1.1.2; a modlist marca o artefato como MCreator mod. Projeto upstream: Create : New Furnitures. Release 1.1.2 corrige FPS em Bridge Structures.
- **Procedência:** modlist.txt física atual de 09/09/2026 + metadata runtime + CurseForge oficial Create : New Furnitures 1.1.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-new-furnitures
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 1.1.2; furniture families, assentos, pillars, Stone Cutter recipes, Bridge Structures/FPS regression, lifecycle e multiplayer catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🪑 **Identidade física confirmada:** `create_furnitures-1.1.2-neoforge-1.21.1.jar`, mod id `create_furnitures`, runtime `1.1.2`, NeoForge 1.21.1. A modlist identifica o artefato como **MCreator mod**; a publicação oficial chama o projeto de **Create : New Furnitures**.

## 1. Papel e authority
Create: Furnitures é um addon decorativo/funcional inspirado na estética do Create. Ele owns seus móveis, modelos, recipes e comportamento de assento. **Create 6.0.10** permanece owner dos casings/materiais Create usados nas receitas e de qualquer infraestrutura Create referenciada; o addon não substitui máquinas ou kinetics do Create.

## 2. Famílias de furniture publicadas
O upstream confirma **tables, chairs e stools**. As mesas/cadeiras usam quatro famílias visuais derivadas de materiais Create, incluindo Andesite Casing, Copper Casing, Railway Casing e Brass Casing. O projeto também informa quatro cores para os tipos de furniture.
A ficha não converte essa descrição em contagem de registry sem source/JAR dump específico.

## 3. Chairs e seat behavior
As chairs são funcionais: jogadores podem sentar nelas. Isso cria state de montagem/seat além do visual. A seat entity/implementation interna não foi publicada com source pin na evidência disponível; portanto não inventar entity ID, packet ou classe.
O servidor deve permanecer authority de mount/dismount e posição do jogador.

## 4. Stools
Stools são outra família de assento/decor. Devem ser tratados como conteúdo próprio do addon. Se também permitirem seat em todos os variants, o comportamento precisa ser validado no runtime; a descrição pública confirma a família, mas não detalha cada variante funcional individualmente.

## 5. Pillars
O projeto publica **Small Andesite Pillar, Small Brass Pillar e Tall Andesite Pillar**. São conteúdo arquitetônico/decorativo, não componentes cinéticos por default. Não inferir stress transmission ou função estrutural apenas pela estética Create.

## 6. Recipes e Stone Cutter
O upstream informa que os móveis usam **Create Casings no Stone Cutter**. Isso integra a progressão material ao Create sem transferir ownership dos casings. Datapacks/KubeJS podem alterar recipes; o recipe efetivamente carregado é authority.

## 7. “Assembly Line” — limite da evidência
A descrição de projeto afirma compatibilidade com maquinaria avançada do Create, citando **Assembly Line**. Esse texto é amplo e não especifica process type, recipe ID ou máquina exata para a build 1.1.2. Portanto esta ficha não inventa uma cadeia automatizada concreta a partir dessa frase de marketing.

## 8. Bridge Structures na 1.1.2
O changelog exato da build 1.1.2 registra duas mudanças: **“Fix FPS on Bridge Structures”** e adição de uma descrição para explicar como bridge structures funcionam.
O upstream consultado não detalha suficientemente a implementação de “Bridge Structures”. O fato confirmado é que essa superfície existia e tinha regressão de FPS. Rendering/performance nela é regression gate obrigatório, sem inferir worldgen, entidade ou algoritmo específico.

## 9. Create 5 / Create 6
A página do projeto declara compatibilidade com **Create 5 e Create 6** em Minecraft 1.20.1/1.21.1. O pack usa Create 6.0.10. Isso é compatibilidade declarada, não prova de ausência de regressões com a combinação exata do modpack.

## 10. Sobreposição decorativa no pack
O pack também contém outras famílias de furniture, como **Immersive Furniture** e **[Let's Do] Furniture**. A sobreposição é majoritariamente de função decorativa/assentos e deve ser avaliada por blocos, estética, recipes e custo real; não há base para declarar duplicidade técnica integral.

## 11. Client/server e multiplayer
Models/textures são client-facing. Placement, recipe consumption e seat/mount state são server-authoritative. Dois jogadores não podem ocupar de forma incoerente o mesmo assento quando o provider só permite um occupant.

## 12. Lifecycle de assentos
Testar sit→dismount, block break enquanto ocupado, chunk unload, teleport, death, dimension change e restart. O sistema não pode deixar player preso em mount invisível, seat órfão ou duplicar occupant state.

## 13. Performance e rendering
A própria 1.1.2 é um fix de FPS para Bridge Structures. O pack grande e visualmente pesado aumenta a relevância desse gate. Medir client FPS/frame time perto de múltiplas structures/móveis; não transformar o changelog em garantia de performance para o pack atual.

## 14. Riscos
1. Seat entity/state fica órfão após quebra/unload.
2. Dois players ocupam o mesmo assento por desync.
3. Dismount posiciona o jogador dentro de bloco.
4. Bridge Structures reproduzem queda de FPS corrigida na 1.1.2.
5. Grande quantidade de models/furniture aumenta custo de render/chunk rebuild.
6. Stonecutter recipes colidem com outro addon decorativo.
7. Marketing de “Assembly Line” é interpretado indevidamente como integração técnica não comprovada.
8. Create version drift altera casing/recipe assumptions.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Furnitures 1.1.2 + Create 6.0.10.
- [ ] Tables/chairs/stools e pillars aparecem uma única vez e usam recipes válidas.
- [ ] Chairs permitem sit/dismount sem ghost mount.
- [ ] Quebrar assento ocupado libera o jogador com segurança.
- [ ] Chunk unload/reload e restart não deixam seat órfão.
- [ ] Multiplayer mantém occupant state coerente.
- [ ] Stone Cutter consome casing/material exatamente uma vez.
- [ ] Bridge Structures não reproduzem regressão severa de FPS da versão anterior.
- [ ] Grande conjunto de furniture não causa regressão de render anormal no cenário de teste.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
A modlist física confirma JAR, mod id `create_furnitures`, runtime 1.1.2 e origem MCreator. CurseForge oficial confirma NeoForge 1.21.1, conteúdo tables/chairs/stools/pillars, quatro materiais/cores, recipes via Stone Cutter e o changelog 1.1.2 de Bridge Structures. Não foi localizado source público matching da build; internals de seat, bridge e registries permanecem fail-closed.

> 🔒 Boundary canônico: **o addon owns furniture/seat behavior; Create fornece estética/materiais base**. A principal superfície técnica além de decoração é lifecycle de assentos, seguida pela regressão de FPS em Bridge Structures corrigida na 1.1.2.