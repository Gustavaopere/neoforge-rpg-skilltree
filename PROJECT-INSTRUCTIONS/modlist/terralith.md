# Terralith

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816c9058d4b952e98791
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Terralith_1.21.x_v2.6.2.jar`, mod id `terralith`, runtime `2.6.2`; Lithostitched 1.8.0+beta6 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Terralith 2.6.2 e Lithostitched 1.8.0+beta6 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Terralith
- **Arquivo JAR:** `Terralith_1.21.x_v2.6.2.jar`
- **Versão 1.21.1:** 2.6.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Overhaul data-driven do Overworld com quase 100 biomas, terreno/cavernas, Skylands e estruturas usando majoritariamente blocos vanilla; 2.6.2 adiciona config e world types alternativos.
- **Dependências:** Lithostitched tornou-se hard dependency na 2.6.2; pack instala lithostitched 1.8.0+beta6 NeoForge 21.1. Dynamic Trees - Terralith é bridge separada presente.
- **Sobreposição:** Compartilha a camada de terreno/biomas com outros providers atuais, mas sua geração e conjunto de paisagens são próprios.
- **Compatibilidade/Riscos:** Worldgen seams/uninstall, provider competition, structure collisions, Skylands/LOD performance e alterações de spawn/mobcap. Testar Tectonic, Streams Reflowing, Dynamic Trees, Distant Horizons e structure mods. Config física não lida.
- **Observações:** mod id `terralith`; runtime 2.6.2. Decisão Sem decisão preservada. Não assumir que todos os toggles 2.6.2 estão ativos sem ler a config local.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Terralith 2.6.2 File ID 8222737 + file list 1.21.1 confirmando 2.6.2 como release NeoForge dessa versão + Lithostitched 1.8.0+beta6 físico. Dossiê de 09/09 preservado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/terralith/files/8222737 ; https://modrinth.com/datapack/terralith
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Terralith 2.6.2 permanece exatamente instalado e continua sendo a release NeoForge 1.21.1 relevante; biomas/terrain/Skylands/structures, Lithostitched, config/world types, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Terralith_1.21.x_v2.6.2.jar`, mod id `terralith`, versão `2.6.2`. Terralith é um grande **overhaul data-driven do Overworld**, com quase 100 biomas, terreno/cavernas e estruturas usando majoritariamente blocos vanilla.

## 1. Identidade e requisitos
- **Mod:** Terralith.
- **JAR:** `Terralith_1.21.x_v2.6.2.jar`.
- **Mod id:** `terralith`.
- **Versão:** `2.6.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server / worldgen server-authoritative.
- **Hard dependency desde 2.6.2:** Lithostitched; pack possui `lithostitched-1.8.0+beta6-neoforge-21.1.jar`.
- **Decisão:** Sem decisão; preservada.

## 2. Authority e ownership
Terralith é authority de seus biomas, noise/worldgen data, caves, terrain slabs, Skylands e custom structures quando habilitados.

Tectonic pode remodelar a macrogeometria e Streams Reflowing pode adicionar hidrologia, mas não assumem ownership dos biomas Terralith.

## 3. Biomas e paisagens
O projeto anuncia **quase 100 novos biomas** entre realismo e fantasia leve, usando principalmente blocos vanilla. Exemplos confirmados em assets/ecossistema incluem alpine/highlands, canyons, mesas, volcanic regions, Skylands, cold/warm rivers e variantes sazonais/raras.

Não converter a descrição “almost 100” em contagem exata de registries sem pin do datapack interno.

## 4. Terrain, caves e structures
Terralith não é apenas biome palette: também altera terreno/cavernas e adiciona estruturas imersivas. Em 2.6.2, custom structures podem ser desativadas via config.

Structure placement precisa ser validado com Tectonic e outros structure mods para evitar enterramento, floating ou sobreposição inadequada.

## 5. Configuração introduzida/backportada em 2.6.2
A release 2.6.2 para 1.21.1 inclui config capaz de togglar:
- intro message;
- recipe changes;
- custom structures;
- vanilla stone generation;
- Skylands;
- terrain slabs.

A configuração física do pack não foi lida; não afirmar quais toggles estão ativos.

## 6. World types
2.6.2 adiciona compatibilidade com:
- Superflat;
- Single Biome;
- Large Biomes;
- Amplified.

Também adiciona dois world types:
- **Amplified + Large Biomes**;
- **Oops! All Skylands**.

Isso amplia a superfície de testes além do Overworld default.

## 7. Skylands
Skylands são parte relevante da identidade do Terralith. A 2.6.2 permite desativá-las via config e inclui fixes para:
- structures modded gerando sob Skylands;
- Serene Seasons afetando Skylands indevidamente;
- ajustes em Autumn/Spring Skylands.

No pack com Distant Horizons/shaders, sombras/LOD e verticalidade precisam de regressão visual e de performance.

## 8. Spawns e ecologia alterados na 2.6.2
Mudanças publicadas incluem:
- remove Endermen de Underground Jungle para evitar ultrapassar mobcap;
- substitui Skeletons por Strays em Frostfire Caves;
- adiciona spawn natural de Camels em desert biomes para alinhar comportamento posterior do vanilla;
- remove Axolotls de biomas incorretos;
- desabilita Zombie Sieges em rare island biomes.

Essas mudanças mostram que Terralith também influencia ecologia/spawn via dados de biome, não apenas estética.

## 9. Lifecycle de worldgen
Worldgen é persistente por chunk. Validar:
- mundo novo;
- pregeneration;
- chunk load/unload;
- server restart;
- mudança de config;
- atualização/removal em cópia de teste;
- fronteiras de chunks antigos/novos.

O próprio intro message 2.6.2 passou a alertar sobre uninstall; remover Terralith de mundo existente não deve ser tratado como operação neutra.

## 10. Integrações concretas no pack
- **Lithostitched 1.8.0+beta6:** hard dependency física.
- **Tectonic 3.0.26:** terrain shaper complementar; testar distribuição/terrain extrema.
- **Dynamic Trees - Terralith:** bridge instalada para árvores dinâmicas nos biomas Terralith.
- **Streams Reflowing 2.13.1:** hidrologia baseada no terreno/biomas finais.
- **Ecliptic Seasons Bundles:** possui cobertura Terralith catalogada separadamente.
- **Distant Horizons/Iris:** Skylands e relevo vertical são stress test de LOD/shader.
- Outros structure mods: custom structures Terralith devem coexistir sem collision excessiva.

## 11. Riscos técnicos
1. **Worldgen seams** após update/config change.
2. **Uninstall risk:** remoção em mundo existente pode quebrar referências/geração futura.
3. **Provider competition:** vários biome/terrain mods podem alterar frequência e estética final.
4. **Structure collision:** terrain/structures de múltiplos mods podem se sobrepor.
5. **Skylands performance:** LODs/shadows podem elevar custo visual.
6. **Spawn ecology:** mudanças de mobcap/spawn precisam coexistir com grandes mob packs.
7. **Config/world type:** world types alternativos podem expor assumptions de outros mods.
8. **Fzzy config warning upstream:** existe report 1.21.1 sobre formato de mod properties; tratar como observação upstream, não bug local confirmado.

## 12. Matriz de testes
- [ ] Mundo novo default com Terralith 2.6.2 + Lithostitched.
- [ ] Superflat/Single Biome/Large Biomes/Amplified abrem conforme suporte publicado.
- [ ] Oops! All Skylands e Amplified + Large Biomes criam mundo sem crash.
- [ ] Toggles de structures/Skylands/stone/terrain slabs funcionam em cópia de teste.
- [ ] Dynamic Trees substitui/adapta árvores Terralith conforme esperado.
- [ ] Tectonic + Terralith não produz terrain/biome seams críticos.
- [ ] Streams Reflowing drena corretamente em biomas Terralith.
- [ ] Modded structures não aparecem indevidamente sob Skylands.
- [ ] Distant Horizons/Iris renderizam Skylands sem artifacts críticos.
- [ ] Mob spawns citados na 2.6.2 correspondem ao esperado.
- [ ] Pregeneration completa sem missing registry/worldgen errors.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 13. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão, Lithostitched e integrations presentes.
- CurseForge oficial `v2.6.2 ~ Neoforge Mod 1.21.1`: arquivo exato, hard dependency, config/world types e changelog.
- Modrinth oficial Terralith: escopo de quase 100 biomas/terrain/structures.

## 14. Revalidação física — 11/09/2026
O runtime físico continua `Terralith_1.21.x_v2.6.2.jar`, mod id `terralith`, versão `2.6.2`. A file list oficial filtrada para Minecraft 1.21.1 mantém **2.6.2** como release NeoForge dessa versão; releases numericamente posteriores pertencem a linhas de Minecraft mais novas e não substituem automaticamente o runtime 1.21.1.

Lithostitched permanece presente. Config local, world types, Skylands, structure placement e spawn ecology não foram testados nesta recatalogação; a matriz permanece desmarcada.
