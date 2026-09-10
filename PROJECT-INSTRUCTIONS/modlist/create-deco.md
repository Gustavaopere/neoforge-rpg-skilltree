# Create Deco

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817abb7ef0ad8aa4a590
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Deco
- **Arquivo JAR:** `createdeco-2.1.3.jar`
- **Versão 1.21.1:** 2.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Tecnologia
- **Função:** Grande addon decorativo industrial para Create, com blocos, estruturas e variações estéticas coerentes com o estilo do mod-base.
- **Dependências:** Create obrigatório; pack físico usa Create 6.0.10. A 2.1.3 adiciona tags de compatibilidade Sable e o pack contém Sable 2.0.5.
- **Sobreposição:** Sobreposição principalmente estética com outros addons decorativos Create; não é duplicata técnica automática. Delta 2.1.3 adiciona Sable/decoration tags e hitbox funcional em Catwalk Stairs.
- **Compatibilidade/Riscos:** Riscos: Catwalk Stair hitbox regression; Cage Lamp placement; decoration/Sable tag drift; contraption state loss; connected geometry/model reload; alto volume de variantes. Source público 1.21-Neo consultado está em 2.1.2, não 2.1.3.
- **Observações:** JAR/mod id/runtime 2.1.3 confirmados. Release 2.1.3 é Client & Server. Source branch 1.21-Neo está em 2.1.2 e foi usado apenas como evidência arquitetural, não como equivalência binária.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + publicação oficial Create Deco 2.1.3 + repositório talrey/CreateDeco branch 1.21-Neo com divergência de versão explicitamente preservada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deco
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.1.3 com famílias decorativas, Cage Lamps, Catwalk collision, decoration/Sable tags, contraption lifecycle e source-version boundary catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🏭 **Identidade física confirmada:** `createdeco-2.1.3.jar`, mod id `createdeco`, runtime `2.1.3`, NeoForge 1.21.1. A release 2.1.3 é Client & Server. O branch público 1.21-Neo consultado ainda declara 2.1.2, portanto não é tratado como source binário exato para 2.1.3.

## 1. Papel e authority
Create Deco é uma expansão predominantemente decorativa/arquitetônica para fábricas, estações e infraestrutura Create. O addon é autoridade de seus próprios blocos, variantes, block states, recipes e tags; Create continua autoridade da cinética e de contraptions nas quais esses blocos eventualmente sejam usados.

## 2. Famílias de materiais
A documentação oficial organiza grande parte do conteúdo em seis materiais recorrentes: **Andesite, Zinc, Iron, Industrial Iron, Copper e Brass**. Essas famílias multiplicam variantes decorativas, mas não devem ser interpretadas como seis sistemas mecânicos distintos.

## 3. Conteúdo estrutural publicado
A superfície oficial inclui **Support Blocks, Support Wedges, Train Hulls, Catwalks, Catwalk Railings e Catwalk Stairs**. São componentes de construção/ambientação industrial e ferroviária; qualquer propriedade especial além de geometria, colisão e recipe fica fail-closed quando não documentada.

## 4. Iluminação
**Cage Lamps** existem em quatro cores de luz. A 2.1.3 altera/reverte comportamento de placement para torná-lo novamente mais permissivo. Placement, orientação e light state devem persistir após reload sem blocos-fantasma ou posições inválidas.

## 5. Painéis, janelas e barreiras
A documentação também lista **Sheet Metal, Windows/panes, Bars, opaque paneled bars e Chain Link Fences**. O risco técnico principal é colisão/occlusion/render e conectividade visual, não um sistema de automação próprio.

## 6. Coins, Doors e Ladders
Há **Coin Stacks/coin items, Doors e Ladders** dentro do catálogo publicado. Coin items são conteúdo do addon; não devem ser tratados como moeda econômica global sem integração explícita de outro provider.

## 7. Catwalk Stair hitboxes — delta 2.1.3
A release 2.1.3 adiciona hitboxes às railings de Catwalk Stairs. Esse delta é funcional: colisão, pathing e interação do jogador precisam corresponder ao modelo, inclusive após rotate/mirror ou uso em estruturas grandes.

## 8. Tags de decoração — delta 2.1.3
A 2.1.3 passa a taguear cada tipo de decoração para melhor compatibilidade com datapacks e busca no JEI. Scripts e datapacks devem preferir tags quando o objetivo for uma família semântica inteira, evitando listas manuais frágeis de IDs.

## 9. Sable compatibility — delta 2.1.3
A release adiciona tags para compatibilidade com **Sable**. O pack contém Sable 2.0.5, então essa integração é concreta. Tags físicas devem ser resolvidas pelo provider Sable; não inventar massa, densidade ou coeficientes específicos sem dados matching.

## 10. Contraptions e trains
Blocos decorativos podem aparecer em contraptions/trains Create. Assemble/disassemble precisa preservar block state, orientação, material/variante e quaisquer propriedades de conexão. O fato de ser decorativo não elimina risco de state loss em estruturas móveis.

## 11. Datapacks e recipe viewers
Tags ampliadas tornam datapacks e JEI superfícies relevantes, mas JEI é apenas descoberta/apresentação. Recipe Manager e registries do servidor são autoridade do conteúdo carregado.

## 12. Client/server
Colisão, placement, block state e recipes são state comum/server-authoritative. Modelos, connected textures e efeitos de luz são client-facing. Cliente não pode usar divergência de modelo/hitbox para criar posição válida que o servidor rejeite.

## 13. Lifecycle
Validar placement, break, rotate/mirror, schematic placement, assemble/disassemble, chunk unload/reload, restart e resource reload. Em especial, Cage Lamps e Catwalk Stairs precisam cobrir os deltas exatos da 2.1.3.

## 14. Sobreposição
Há forte sobreposição estética com Bells & Whistles, Design n' Decor, Chipped e outros blocos industriais, mas isso não prova redundância técnica. A decisão de remoção seria curatorial/visual e deve considerar palettes/recipes concretos, não apenas quantidade de blocos.

## 15. Riscos
1. Catwalk Stair railing sem hitbox ou com colisão divergente do modelo.
2. Cage Lamp placement regressa para regra antiga indesejada.
3. Tags 2.1.3 incompletas quebram datapacks/JEI search.
4. Sable interpreta tags de forma incompatível após update.
5. Contraption perde orientation/variant.
6. Connected geometry fica stale após neighbor update.
7. Resource reload deixa modelos ausentes.
8. Alto volume de variantes aumenta custo de registro/model bake.
9. Scripts usam IDs individuais e quebram quando uma família muda.

## 16. Matriz de testes
- [ ] Dedicated server inicia com Create Deco 2.1.3 + Create 6.0.10.
- [ ] Catwalk Stairs possuem railing collision conforme 2.1.3.
- [ ] Cage Lamps podem ser posicionadas conforme regra atual e persistem após restart.
- [ ] Famílias decorativas aparecem corretamente em tags/JEI.
- [ ] Sable 2.0.5 reconhece as tags fornecidas sem crash.
- [ ] Blocos em contraption preservam state após assemble/disassemble.
- [ ] Doors/ladders/fences mantêm colisão e conectividade.
- [ ] Resource reload não produz missing models.
- [ ] Datapack usando family tags resolve os membros esperados.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
A modlist física confirma JAR/mod id/runtime 2.1.3. A publicação oficial confirma NeoForge 1.21.1, Client & Server, famílias de blocos e os deltas 2.1.3 de Cage Lamps, Sable tags, decoration tags e Catwalk Stair hitboxes. O branch público consultado está em 2.1.2; internals específicos de 2.1.3 permanecem fail-closed.

> 🔒 **Boundary canônico:** Create Deco fornece geometria, variantes e tags decorativas; Create/Sable continuam donos de cinética e física. O delta 2.1.3 deve ser validado principalmente em colisão, placement e tags.
