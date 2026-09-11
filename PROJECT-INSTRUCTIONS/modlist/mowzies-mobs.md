# Mowzie's Mobs

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81278422ed953a3d5b3c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `mowziesmobs-1.21.1-1.8.2.jar`, mod id `mowziesmobs`, runtime `1.8.2`, mixin `mowziesmobs.mixins.json`; GeckoLib `4.9.2`, Integrated Mowzie's Mobs `1.3.0` e Mowzie's Cataclysm `1.2.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o provider e o stack citado estão confirmados.

## Propriedades do banco

- **Mod:** Mowzie's Mobs
- **Arquivo JAR:** `mowziesmobs-1.21.1-1.8.2.jar`
- **Versão 1.21.1:** 1.8.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, Exploração, RPG, Worldgen
- **Função:** Provider de criaturas/bosses de fantasia com AI/encounters próprios, estruturas e rewards mágicos: Foliaath, Wroughtnaut, Frostmaw, Grottol, Lantern, Naga, Umvuthana/Umvuthi, Tongbi/Bluff e Bilokosa/Elokosa na linha atual.
- **Dependências:** GeckoLib — Required Dependency oficial. Pack possui também Integrated Mowzie's Mobs 1.3.0 e Mowzie's Cataclysm 1.2.2 como integrações separadas; não são dependencies do provider base salvo relação própria.
- **Sobreposição:** Compartilha mobs/bosses/exploração com outros providers, mas encounters/rewards são próprios. Integrated Mowzie's Mobs altera estruturas e Mowzie's Cataclysm fornece locators; ambos devem preservar ownership do provider base.
- **Compatibilidade/Riscos:** Boss/mob/worldgen provider com GeckoLib required. Riscos: worldgen/biome-tag drift, Integrated Mowzie's Mobs 1.3.0, locator bridge #418, ability/NBT lifecycle, boss reward power e combat-mod interactions. 1.8.2 corrige `/data merge` ability wipe e Foliaath relog.
- **Observações:** Runtime 1.8.2, file ID 7760267, Release 15/03/2026. Changelog exato inclui ability interruption apenas com dano `>=3`, Wrought Chamber data version, `/data merge` fix, Foliaath relog fix e Elokosa paws fechando no cooldown.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/FAQ oficiais da release 1.8.2 + relations oficiais. Source público não foi tratado como pin byte-equivalente da 1.8.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs/files/7760267 | https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs | https://www.curseforge.com/minecraft/mc-mods/mowzies-mobs/relations/dependencies
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Mowzie's Mobs 1.8.2 reconstruído: roster/encounters, rewards, geomancy, Bilokosa/Elokosa, worldgen/biome tags, changelog exato, GeckoLib, integrations, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `mowziesmobs-1.21.1-1.8.2.jar`, mod id `mowziesmobs`, versão `1.8.2`. A release exata é o CurseForge file ID `7760267`, publicada em 15/03/2026 para NeoForge 1.21.1. GeckoLib é required. O source público `BobMowzie/MowziesMobs-Public` não foi estabelecido como correspondência exata à 1.8.2; gameplay/release atual são sustentados prioritariamente pela publicação oficial.

## 1. Identidade e papel
- **Mod:** Mowzie's Mobs.
- **JAR físico:** `mowziesmobs-1.21.1-1.8.2.jar`.
- **Mod id:** `mowziesmobs`.
- **Runtime:** `1.8.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor principal:** bobmowzie / equipe Mowzie's Mobs.
- **CurseForge project ID:** 250498; file ID 7760267.
- **Ambiente:** Client & Server.
- **Licença:** Custom License.
- **Papel no pack:** provider de encounters/bosses e criaturas de fantasia com AI própria, estruturas, animações fluidas e recompensas que concedem gear/habilidades mágicas.

## 2. Dependência obrigatória
A relation oficial lista **GeckoLib** como Required Dependency. O projeto também declara explicitamente que versões modernas exigem GeckoLib.

GeckoLib fornece infraestrutura de animação; Mowzie's Mobs continua authority da AI, encounters, mobs, itens, estruturas e regras de progressão.

## 3. Filosofia de encounter
A descrição oficial enfatiza criaturas com um **hook** de gameplay: cada mob deve mudar a forma de interação do jogador, e não apenas possuir modelo/HP diferentes.

Exemplos publicados:
- Grottol é uma perseguição/recompensa, resiste a ranged, pode usar minecart para fugir e burrow/desaparecer;
- Wroughtnaut possui fraqueza específica em vez de ser vencido por spam de dano;
- Naga pode ser derrubado com timing de ranged durante ataques;
- Tongbi transforma a progressão em parkour/teste de geomancy.

Isso torna o mod altamente relevante para balanceamento de combate e quests.

## 4. Foliaath
- Monstro vegetal de jungle que se disfarça como fern.
- Emerge quando a presa se aproxima e exige abordagem diferente de melee convencional.
- Dropa **Foliaath Seeds**.
- Seeds podem ser plantadas em grass/dirt; baby Foliaath é alimentado periodicamente com meat ao longo de dois dias até amadurecer.
- Adulto cultivado continua hostil; criação não equivale a tame.

A 1.8.2 corrige um problema visual em que baby Foliaaths apareciam como infants novamente após relog, tornando relog/save uma regressão direta desta release.

## 5. Ferrous Wroughtnaut
- Heavy armored encounter encontrado em Wrought Chamber subterrânea.
- Possui uma fraqueza/abertura específica para receber dano.
- Drops publicados: **Wrought Helm** e **Axe of a Thousand Metals**.
- Axe possui ataque amplo no right click e shockwave de atração no shift-right-click; o projeto afirma que helm/axe não quebram.

A 1.8.2 atualiza a **data version da Wroughtnaut chamber**, portanto geração/estrutura e existing-world migration são pontos obrigatórios de teste.

## 6. Frostmaw
- Grande criatura rara de áreas nevadas.
- Permanece dormindo até ser provocada/encountered conforme lógica do mod.
- Usa velocidade elevada e ice breath.
- Guarda **Ice Crystal**, recompensa que permite canalizar energia de gelo.

O projeto informa que Frostmaw está entre os conteúdos associados à geração de mundo, exigindo chunks novos quando o mod é adicionado a mundo existente.

## 7. Grottol
- Criatura cristalina subterrânea, tratada como “living ore”.
- Fonte rara de diamonds.
- Muito rápida, foge do jogador, pode entrar em minecarts e se enterrar/desaparecer.
- Exige pickaxe de iron ou melhor para penetrar a carapaça.
- A documentação descreve interação com Silk Touch/Fortune no contexto de sua identidade de minério vivo.

## 8. Lantern
- Criatura luminosa associada à copa de dark/roofed forests.
- Seu material luminoso pode ser consumido e fornece iluminação temporária conforme descrição oficial.
- É conteúdo atmosférico/utilitário, não boss.

## 9. Naga
- Serpente/drake voador de coastal cliffs/biomas costeiros adequados.
- Alta mobilidade e ataque com acid venom.
- Ranged bem temporizado durante preparação do ataque pode derrubá-lo do ar.
- Naga fangs participam de antidotes/preventatives contra poison segundo o guia oficial.

## 10. Umvuthana / Abavuthana
- Packs hostis da savanna.
- Queimam/scorch o solo ao caminhar conforme documentação.
- Ataques do pack são coordenados, alternando investidas.
- Um **Umvuthana Raptor** lidera grupos e usa talons maiores para bloquear ataques.
- Podem dropar masks vestíveis com pequenos buffs.

## 11. Umvuthi, the Sunbird
- Encounter/boss estacionário em um grove de savanna.
- Usa heliomancy: sunstrikes, solar flares e beams.
- Pode criar Umvuthana followers, incluindo healers, durante combate.
- Recompensa publicada: **Sol Visage**, capaz de dar vida a masks e criar seguidores.
- A documentação distingue esse poder da heliomancy inata de Umvuthi.

A linha 1.8 adicionou novo boss bar para Umvuthi; a 1.8.2 herda esse conteúdo.

## 12. Tongbi, the Sculptor e Monastery
- **Tongbi** é mestre de geomancy encontrado no courtyard de Earthrend Monastery.
- O encounter principal é um **parkour trial**, não apenas boss fight.
- Completar o teste concede **Earthrend Gauntlet**, artefato de earth magic.
- Monasteries ficam associados a regiões montanhosas e contêm **Bluffs**.

Quests do pack devem creditar conclusão real do trial/obtenção do reward, não mera chegada à estrutura.

## 13. Bluff
- Elemental de earth magic que infesta Earthrend Monasteries.
- Dropa **Bluff Rods**.
- Rods servem para reparar geomancy items e iniciar o teste do Sculptor.

Isso cria uma cadeia de progressão structure → mob drop → trial → geomancy reward.

## 14. Bilokosa / Elokosa Howler
Conteúdo recente da linha 1.8:
- pequenos Bilokosa aparecem no alto da jungle canopy e são descritos como criaturas amaldiçoadas;
- à noite transformam-se em formas perigosas que perseguem/saltam sobre presas;
- packs são liderados por **Elokosa Howler**, alpha com tail scythe e follow-up attacks;
- Elokosa pode dropar uma **paw** mágica;
- o efeito negativo espalhado pela paw depende da **fase da lua** quando a criatura morre.

A 1.8.2 altera visual/estado dessas paws: elas agora fecham quando estão em cooldown.

## 15. Ability lifecycle e fix 1.8.2
A release 1.8.2 contém mudanças importantes de lifecycle:
- dano só interrompe mob abilities se o dano recebido for **>= 3**;
- corrige mob abilities sendo apagadas após comando `/data merge`;
- melhora movimento na água de certas mobs;
- corrige subtitle groupings;
- corrige baby Foliaath após relog;
- atualiza Wroughtnaut chamber data version.

Esses fixes mostram que ability state, entity NBT/data merge, render/relog e structure data são superfícies reais da build instalada.

## 16. Worldgen e biome tags
O FAQ oficial informa que Mowzie's Mobs usa **Biome Tags de datapacks** para determinar locais de spawn, permitindo que biomas modded funcionem quando corretamente taggeados.

Também informa que algumas criaturas/encounters — como Wroughtnaut e Frostmaw e conteúdo equivalente de estrutura — dependem de world generation, de modo que mundos preexistentes podem exigir exploração de chunks novos.

No pack, isso se cruza diretamente com worldgen amplo e com **Integrated Mowzie's Mobs 1.3.0**.

## 17. Integrated Mowzie's Mobs 1.3.0
A modlist física contém IMM, que revampa e integra estruturas de Mowzie's Mobs. Ele também depende de vários providers como Create, Supplementaries e Amendments.

Mowzie's Mobs continua authority das criaturas/encounters. IMM é authority das alterações de integração/worldgen que acrescenta. Ao diagnosticar estrutura ausente ou relocada, separar o provider base do overhaul de estruturas.

## 18. Mowzie's Cataclysm 1.2.2
O bridge está instalado e exige Mowzie's Mobs + Cataclysm. Ele adiciona quatro Eyes destinados a localizar bosses Mowzie.

Isso cria uma integração direta de progressão/localização. Toda atualização de Mowzie's Mobs/IMM deve retestar os quatro locators do bridge.

## 19. Outras integrações físicas
A modlist também contém páginas/mods relacionados, incluindo:
- GTBC's Geomancy Plus, ligado ao equipamento/geomancy de Mowzie;
- resource support Excalibur para Mowzie's Mobs;
- outros mods de worldgen/integration que podem alterar estrutura/spawn.

Esses consumers/addons não transferem ownership do conteúdo base.

## 20. Client/server, multiplayer e persistência
O projeto é Client & Server. Separação prática:
- server: AI, damage, spawn, boss state, drops, structures, ability authority;
- client: GeckoLib animations, particles, boss presentation, rendering de mobs/items.

Fixes da 1.8.2 demonstram estado de abilities e entity data sensível a relog/`/data merge`. Integrações próprias devem usar hooks reais em vez de editar NBT indiscriminadamente.

## 21. Conteúdo planejado não é instalado
A página oficial mantém uma seção extensa de future plans/suggestions. Esses conceitos **não devem ser catalogados como conteúdo atual**. Pet Nagas, futuras criaturas/ordens/drakes e outras ideias permanecem roadmap até release/JAR comprovarem implementação.

## 22. Riscos
1. **Worldgen density/override** com Integrated Mowzie's Mobs e outros structure mods.
2. **Biome tags:** tag incorreta em bioma modded impede spawn ou desloca distribuição.
3. **Boss progression:** locators/addons podem permitir acesso cedo a rewards fortes.
4. **Ability interruption:** mods de combate/dano precisam respeitar o threshold alterado da 1.8.2.
5. **Entity data mutation:** `/data merge` já causou perda de abilities antes do fix.
6. **Relog rendering:** Foliaath teve correção direta nessa superfície.
7. **Wrought Chamber migration/data version:** testar mundos existentes.
8. **GeckoLib:** hard dependency e rendering/animation provider.
9. **Reward power:** Ice Crystal, Earthrend Gauntlet, Sol Visage e Axe podem interagir com sistema global de atributos/perks.
10. **Source limitation:** source público não foi estabelecido como exato 1.8.2; não inventar registry IDs/classes.

## 23. Matriz de validação
### Boot/dependency
- [ ] Cliente + dedicated server iniciam com Mowzie's Mobs 1.8.2 e GeckoLib atual.

### Worldgen
- [ ] Wrought Chamber aparece em chunks novos e seu data version não causa erro.
- [ ] Frostmaw/Umvuthi/Monastery e outros encounters worldgen aparecem em condições adequadas.
- [ ] Biomas modded com tags corretas recebem spawns esperados.
- [ ] Existing world encontra conteúdo ao explorar chunks novos.
- [ ] Integrated Mowzie's Mobs 1.3.0 não elimina/duplica estruturas de forma indevida.

### Combat/abilities
- [ ] Wroughtnaut só recebe dano conforme seu hook e entrega rewards uma vez.
- [ ] Frostmaw ice breath/encounter e Ice Crystal funcionam.
- [ ] Naga ranged-grounding e poison/antidote loop funcionam.
- [ ] Umvuthana packs coordenam ataques sem AI loop.
- [ ] Umvuthi boss fight e followers sincronizam em multiplayer.
- [ ] Dano <3 e >=3 verifica regra de interruption da 1.8.2.

### Geomancy
- [ ] Bluff dropa recurso necessário.
- [ ] Bluff Rod inicia Sculptor trial.
- [ ] Tongbi parkour conclui e entrega Earthrend Gauntlet uma vez.
- [ ] GTBC's Geomancy Plus, se aplicado ao item, não duplica atributos/effects.

### Bilokosa/Elokosa
- [ ] Transformação day/night ocorre corretamente.
- [ ] Howler lidera/combat sem desync.
- [ ] Paw reflete moon phase e fecha em cooldown.

### Persistência
- [ ] `/data merge` não apaga mob abilities.
- [ ] Relog não regride baby Foliaath visual/state.
- [ ] Chunk unload/reload mantém boss/ability state esperado.
- [ ] Restart completo não duplica rewards/structures.
- [ ] Dois clientes observam mesma ability/boss state.

### Bridges
- [ ] Quatro Eyes de Mowzie's Cataclysm localizam os encounters corretos com IMM ativo.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 24. Evidências e limites
- Modlist física: `mowziesmobs-1.21.1-1.8.2.jar`, mod id/version e `mowziesmobs.mixins.json`.
- CurseForge oficial: project 250498, file 7760267, Release NeoForge 1.21.1 de 15/03/2026, Client & Server.
- Relations oficiais: GeckoLib required.
- Página/FAQ oficiais: roster e comportamento de Foliaath, Wroughtnaut, Frostmaw, Grottol, Lantern, Naga, Umvuthana, Umvuthi, Tongbi, Bluff e Bilokosa/Elokosa; biome tags/worldgen guidance.
- Changelog exato 1.8.2: paws cooldown, subtitles, water movement, ability interruption threshold, Wrought Chamber data version, `/data merge` ability fix e Foliaath relog fix.
- **Limite:** source público não foi demonstrado como correspondência exata de 1.8.2; classes/IDs internos não foram inventados.
