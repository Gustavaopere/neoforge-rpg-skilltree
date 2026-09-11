# Supplementaries

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8109ad48f13e5650602f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `supplementaries-1.21.1-3.9.8-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.8`; Moonlight Lib 3.6.3 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Supplementaries 3.9.8 e Moonlight Lib 3.6.3 estão presentes. A página também registra a 3.9.9 de 10/09/2026 apenas como atualização upstream disponível; isso não altera a versão física canônica. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Supplementaries
- **Arquivo JAR:** `supplementaries-1.21.1-3.9.8-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-3.9.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual, Automação
- **Função:** Grande expansão vanilla+ com storage/display, decoração, iluminação, ferramentas e mecanismos de redstone/automação como Jar, Faucet, Pulley, Cannon, Rope, Urn e Soap, amplamente configuráveis.
- **Dependências:** Moonlight Lib é obrigatória; pack instala Moonlight 1.21.1-3.6.3. Runtime Client & Server. Integrações opcionais/data-driven dependem do stack presente.
- **Sobreposição:** Sobreposição parcial com Amendments, Blocks You Need e outros vanilla+; Sable Physics Compat declara suporte a Supplementaries.
- **Compatibilidade/Riscos:** Catálogo amplo e feature gates encadeados. Testar Pulley com Sable/Create, Faucet/storage com capabilities, Cannon/claims, Urn/worldgen e resource packs. 3.9.8 corrige primeiro pull do Pulley, Cannon Boat range, Flag renderer/config e Hourglass load.
- **Observações:** mod id `supplementaries`; runtime físico 1.21.1-3.9.8. Existe 3.9.9 para NeoForge 1.21.1 publicada em 10/09/2026; registrar como atualização disponível, sem substituir a versão canônica até o JAR físico mudar. Config física não foi lida; presença do mod não prova todos os features habilitados.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Supplementaries 3.9.8 File ID 8821187 + file list oficial 1.21.1 mostrando 3.9.9 File ID 8852720 publicada em 10/09/2026. Dossiê de 09/09 preservado; config local não foi lida.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/8821187 ; https://github.com/MehVahdJukaar/Supplementaries
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Supplementaries 3.9.8 permanece o runtime físico. Dossiê de catálogo funcional/config/feature gates preservado. ATUALIZAÇÃO DISPONÍVEL: 3.9.9 NeoForge 1.21.1 publicada em 10/09/2026; não altera a autoridade física 3.9.8.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `supplementaries-1.21.1-3.9.8-neoforge.jar`, mod id `supplementaries`, versão `1.21.1-3.9.8`. Supplementaries é um grande **vanilla+ funcional/decorativo**, com mais de 200 blocos/itens descritos pelo projeto e configuração granular que permite desabilitar grande parte do catálogo individualmente.

## 1. Identidade, versão e papel
- **Mod:** Supplementaries.
- **JAR físico:** `supplementaries-1.21.1-3.9.8-neoforge.jar`.
- **Mod id:** `supplementaries`.
- **Versão:** `1.21.1-3.9.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Dependência principal:** Moonlight Lib; o pack possui `moonlight-1.21.1-3.6.3-neoforge.jar`.
- **Decisão:** Sem decisão; preservada.

## 2. Authority e ownership
Supplementaries é authority de seus próprios blocos, itens, entidades/projéteis e mecânicas vanilla+.

Moonlight fornece infraestrutura compartilhada; Amendments altera/expande comportamentos de conteúdo vanilla e não deve ser confundido com Supplementaries. Outros mods podem fornecer compats ou retexturas sem assumir ownership das mecânicas base.

## 3. Escopo funcional
O projeto organiza o conteúdo em três grandes famílias:
- **storage/display:** Sack, Safe/Key, Jar/Tinted Jar, Item Shelf, Book Pile, Crystal Display, Hat Stand, Quiver, Lunch Basket, presents e Urn;
- **redstone/utilidade/automação:** Bellows, Crank, Turn Table, Faucet, Pulley, Relayer, Speaker, Redstone Illuminator, Sliding Block, Cog Block, Clock, Spring Launcher, Pedestal e Wind Vane;
- **decoração/iluminação/ferramentas:** sconces/candelabras, Fire Pit, lamps, rope/bunting/awnings, signs, blackboards, planters, timber frames, doors/trapdoors, Cannon/Cannonball, Slingshot, bombs, Rope Arrow, Hourglass, Wrench, Flute, Soap, Antique Ink, mapas e outros utilitários.

A ficha não transforma a lista pública em contagem exata de registries sem source pin da release 3.9.8.

## 4. Jars e storage interativo
Jars/Tinted Jars armazenam diferentes conteúdos e são parte importante da identidade do mod. A documentação pública inclui uso com fluidos, cookies e pequenos mobs/objetos em contextos suportados.

Testes devem cobrir inserção/extração, quebra/recuperação, NBT/components persistentes e interoperabilidade com hoppers/pipes quando exposta.

## 5. Rope, pulley e estruturas móveis
Rope é infraestrutura interna para outras features. O source 1.21.1 mostra que desabilitar rope pode desabilitar também recursos dependentes como pulleys, buntings, rope arrows e compat blocks.

A configuração de Pulley possui modo de **continuous retraction**, no qual a cadeia retrai em vários ticks usando moving-piston entities; múltiplos pulleys podem cooperar para puxar uma estrutura ampla quando configurado.

No pack com Sable/Create, testar pulleys e estruturas móveis para evitar conflito de ownership físico.

## 6. Faucet e transferência
Faucet é mecanismo de transferência/derramamento. O source expõe opções como:
- permitir derrubar itens;
- permitir preencher inventários de entidades abaixo.

A semântica efetiva depende da config. Interações com tanks/containers de outros mods devem validar capability/transaction sem dupe/loss.

## 7. Cannon, cannon boats e projéteis
Supplementaries possui Cannon/Cannonball e Cannon Boat. Configs publicadas controlam fuse, cooldown, fire power, recoil, TNT behavior e break radius/power scaling.

A release 3.9.8 corrige especificamente **shoot range do cannon boat**, tornando este comportamento regression gate da build instalada.

## 8. Urns, geração e loot
Urn possui configuração própria, incluindo chance de gerar critter a partir de tag e opção de cave urns que requer world reload.

Isso cria duas superfícies distintas:
- bloco/loot em runtime;
- geração de mundo, que não deve ser avaliada em chunks antigos como se fosse regenerada automaticamente.

## 9. Soap e limpeza data-driven
Soap pode limpar blocos/itens e possui config para desabilitar cleaning em-world sem remover o item/recipes. O source 1.21.1 também possui blacklist e mapeamentos especiais de blocos a serem limpos.

Como o pack é grande, compat data-driven deve ser testada com blocos modded para evitar transformações indevidas.

## 10. Configuração e feature gates
A linha 1.21.1 usa config comum sincronizada baseada em Moonlight. Muitos features têm toggles independentes e subopções.

Consequências operacionais:
- “mod instalado” não significa que cada feature esteja habilitada;
- algumas opções exigem world reload;
- clients precisam receber regras sincronizadas do servidor;
- desabilitar um feature-pai pode afetar features dependentes.

A configuração física do pack não foi lida neste lote.

## 11. Release 3.9.8
Changelog oficial da build física:
- corrige animação do **Pulley no primeiro pull**;
- corrige **shoot range do Cannon Boat**;
- corrige renderer do item de **Flags** ligado à config de banners;
- altera método de load do **Hourglass**.

Esses quatro pontos são regression gates específicos da 3.9.8.

## 12. Client / server e multiplayer
- Servidor: placement, inventories, transfers, redstone, projectile/damage e state de blocks/entities.
- Cliente: modelos, animações, GUIs e efeitos.
- Config COMMON_SYNCED precisa manter feature availability consistente.
- Dois jogadores interagindo com mesmo storage/mechanism não podem duplicar state.

## 13. Integrações concretas no pack
- **Moonlight Lib 3.6.3:** dependency física principal.
- **Amendments 2.1.10:** ecossistema próximo e sobreposição vanilla+ parcial; não é duplicação integral.
- **Sable Physics Compat:** metadata do pack registra suporte a Supplementaries; testar objetos móveis/physics suportados.
- **Create 6.0.10:** sobreposição parcial em mecanismos e automação; testar Faucet/Pulley/Cannon próximos a contraptions sem assumir integração universal.
- **Excalibur Supplementaries Support** e `Supplementaries Compat` estão catalogados separadamente; são camadas visual/data, não authority das mecânicas.
- **Subtle Effects:** integração visual explícita com Enderman Head de Supplementaries em feature de shader; não altera lógica do item.

## 14. Riscos técnicos
1. **Catálogo amplo:** maior superfície de recipe/tag/model/loot conflicts.
2. **Feature dependency graph:** desabilitar Rope ou outro parent pode desligar recursos relacionados.
3. **Moving structures:** Pulley/Sable/Create podem disputar assumptions de movimento/collision.
4. **Transfer duplication:** Faucet/storage com capabilities externas exige teste transacional.
5. **Projectile balance:** Cannon/Cannon Boat interagem com proteção, claims e outros sistemas de dano.
6. **Worldgen:** urns/flax e outros recursos gerados exigem seed/chunk novo para validação.
7. **HUD/model/config:** 3.9.8 corrige renderer de flags; resource packs e Excalibur precisam regression visual.
8. **Performance:** muitos BlockEntities/animated mechanisms juntos podem elevar custo de render/tick.

## 15. Matriz de testes
- [ ] Dedicated server boot com Supplementaries 3.9.8 + Moonlight 3.6.3.
- [ ] Feature gates sincronizam e desabilitam apenas o escopo esperado.
- [ ] Jar/storage preserva conteúdo após break/relog/restart.
- [ ] Pulley anima corretamente já no primeiro pull.
- [ ] Continuous/cooperative pulley sem dupe/collision corruption.
- [ ] Faucet transfere/derrama sem dupe/loss em containers vanilla e modded.
- [ ] Cannon/Cannon Boat range, recoil, cooldown e projectile state em multiplayer.
- [ ] Urn/cave generation em chunk novo; world reload option aplicada corretamente.
- [ ] Soap não transforma blocos fora da regra configurada.
- [ ] Flag item renderer respeita config e resource pack Excalibur.
- [ ] Hourglass persiste state após save/restart.
- [ ] Sable/Create interaction em mecanismos móveis.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 16. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão, Moonlight e stack local.
- CurseForge oficial Supplementaries 3.9.8: release exata, ambiente e changelog.
- Página oficial do projeto: catálogo funcional e mais de 200 blocos/itens.
- Source oficial branch 1.21.1: `CommonConfigs`, feature gates e parâmetros de Rope/Pulley/Faucet/Cannon/Urn/Soap. O branch é usado para arquitetura; não é tratado como commit pin exato do binário 3.9.8.

## 17. Revalidação física — 11/09/2026
O JAR canônico continua sendo `supplementaries-1.21.1-3.9.8-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.8`, com Moonlight 3.6.3 presente. O dossiê 3.9.8 continua tecnicamente aplicável ao runtime físico.

**Atualização upstream:** a file list oficial agora contém `supplementaries-1.21.1-3.9.9-neoforge.jar`, publicada em 10/09/2026. Esta auditoria não altera a versão instalada nem atribui à 3.9.9 comportamento adicional além do que a publicação suporta. Nenhum teste/config migration foi executado.
