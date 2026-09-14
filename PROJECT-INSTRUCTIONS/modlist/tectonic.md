# Tectonic

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db815d8473fb92e531c761
- **Estado no pack:** Integrado ao Github
- **Autoridade física:** `tectonic-3.0.26-neoforge-21.1.jar`, mod id `tectonic`, runtime `3.0.26`; Lithostitched `1.8.0+beta6` presente
- **Auditoria de migração Notion → GitHub:** 2026-09-14

## Propriedades do banco

- **Mod:** Tectonic
- **Arquivo JAR:** `tectonic-3.0.26-neoforge-21.1.jar`
- **Versão 1.21.1:** 3.0.26
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Terrain shaper do Overworld que amplia geometria macro de montanhas, vales, canyons, wetlands e transições de relevo; não é um catálogo de biomas equivalente a Terralith/BWG.
- **Dependências:** Lithostitched é hard dependency oficial; pack instala lithostitched 1.8.0+beta6 NeoForge 21.1. Runtime Tectonic 3.0.26 para NeoForge 1.21.1.
- **Sobreposição:** Parcial com outros terrain shapers; não é equivalente a structure mods ou a expansões de conteúdo de biomas.
- **Compatibilidade/Riscos:** Worldgen seams, structures em relevo extremo, custo de chunkgen e composição com Terralith/BWG/Streams Reflowing. 3.0.26 backporta config improvements e corrige mountain jaggedness reduzida desde 3.0.23.
- **Observações:** mod id `tectonic`; runtime 3.0.26. O JAR contém `apollib-1.1.5-neoforge-21.1.jar` em `META-INF/jarjar`; dentro dele há `json5-java-3.0.0.jar` em `META-INF/jars`. Ambos permanecem componentes embarcados, não entradas top-level. Config física não foi lida.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + Modrinth oficial Tectonic 3.0.26 NeoForge 1.21.1 revalidado em 13/09/2026 + Lithostitched 1.8.0+beta6 físico + inventário do JAR registrando Apollib 1.1.5 e json5-java 3.0.0 embarcados. Nenhum teste runtime foi executado.
- **Fonte:** https://modrinth.com/datapack/tectonic/version/3.0.26-neoforge-21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Tectonic 3.0.26 permanece exatamente instalado e continua a release NeoForge 1.21.1 aplicável. Apollib 1.1.5 e json5-java 3.0.0 foram confirmados como componentes internos, sem ordinal próprio.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> ⛰️ **ESCOPO CANÔNICO.** Runtime físico: `tectonic-3.0.26-neoforge-21.1.jar`, mod id `tectonic`, versão `3.0.26`. Tectonic é **terrain shaping** do Overworld: amplia escala e variedade do relevo sem ser um catálogo de blocos/biomas equivalente a Terralith.

## 1. Identidade e requisitos
- **Mod:** Tectonic.
- **JAR:** `tectonic-3.0.26-neoforge-21.1.jar`.
- **Mod id:** `tectonic`.
- **Versão:** `3.0.26`.
- **Minecraft/loader:** 1.21.1 NeoForge.
- **Ambiente:** server-side / Client & Server.
- **Dependência obrigatória publicada:** Lithostitched; pack possui `lithostitched-1.8.0+beta6-neoforge-21.1.jar`.

## 2. Authority e ownership
Tectonic controla principalmente a **geometria macro do terreno**: montanhas, vales, canyons, wetlands, encostas e transições. Biome providers continuam responsáveis por quais biomas existem e pelo conteúdo deles.

Terralith e outros biome mods podem coexistir sobre esse relevo; a composição final deve ser testada por seed, não inferida apenas por compatibilidade declarada.

## 3. Geração de relevo
O projeto visa terrenos maiores e mais variados que o vanilla, com formações de escala ampla. Como terrain shaper, seus efeitos surgem durante chunk generation e ficam gravados no mundo.

Mudança de versão/config não reescreve chunks antigos automaticamente.

## 4. Configuração
A 3.0.26 backporta melhorias no arquivo de config para 1.21.1. A config física do pack não foi lida; não afirmar parâmetros locais.

Mudanças de config que afetam terrain devem ser avaliadas em seed/chunks novos e podem criar seams com regiões antigas.

## 5. Release 3.0.26
Changelog exato:
- backport das melhorias de config para 1.21.1;
- correção da redução indevida de **mountain jaggedness** introduzida desde 3.0.23.

Esse relevo serrilhado é regression gate visual/worldgen do runtime instalado.

## 6. Client / server
Worldgen é authoritative no servidor/integrated server. Cliente apenas recebe chunks já gerados. O mod é publicado como server-side, embora possa existir em ambos os lados.

## 7. Lifecycle
Validar:
- criação de mundo novo;
- pregeneration com Chunky;
- exploração de chunks novos;
- restart antes/depois de gerar novas regiões;
- atualização de config em cópia de teste;
- fronteiras entre chunks gerados com versões/configs diferentes.

## 8. Integrações concretas no pack
- **Lithostitched 1.8.0+beta6:** hard dependency física.
- **Terralith 2.6.2:** biome/terrain content complementar; testar montanhas, Skylands e cave/biome placement sobre Tectonic.
- **Streams Reflowing 2.13.1:** lê relevo para hidrologia; drainage e rios dependem da geometria final.
- **Oh The Biomes We've Gone:** biome provider adicional; não é terrain shaper equivalente.
- **Distant Horizons:** LODs devem refletir corretamente relevo extremo e transições.
- **Volcanoes/projetos próprios:** qualquer detecção que use características de terreno precisa ser testada contra a topografia efetiva.

## 9. Riscos técnicos
1. **Worldgen seams** após update/config change.
2. **Structure placement** em relevo extremo pode ficar suspenso, enterrado ou em slope inadequado.
3. **Hydrology interaction** com Streams Reflowing pode criar carving complexo.
4. **Biome composition:** compatibilidade declarada não garante distribuição estética ideal com vários providers.
5. **Chunkgen cost:** relevo complexo + outros worldgen aumenta custo de geração.
6. **Jaggedness regression:** 3.0.26 corrige redução observada desde 3.0.23; manter smoke test.

## 10. Matriz de testes
- [ ] Dedicated server gera mundo novo com Tectonic 3.0.26 + Lithostitched.
- [ ] Montanhas/vales/canyons apresentam escala e continuidade esperadas.
- [ ] Mountain jaggedness não permanece reduzida como na regressão anterior.
- [ ] Terralith/BWG biomes aparecem em relevo coerente.
- [ ] Streams Reflowing drena sem cursos subindo/rompendo terreno criticamente.
- [ ] Structures vanilla/modded assentam de forma aceitável.
- [ ] Distant Horizons representa relevo sem LOD gaps críticos.
- [ ] Chunky pregen conclui sem crash.
- [ ] Old/new chunk border após mudança de config é avaliada em cópia de teste.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 11. Evidências
- Modlist física canônica 08/09/2026: JAR/versão e Lithostitched presente.
- Modrinth oficial 3.0.26-neoforge-21.1: 1.21.1, hard dependency Lithostitched e changelog da config/jaggedness.

## 12. Revalidação física — 11/09/2026
A modlist atual mantém exatamente `tectonic-3.0.26-neoforge-21.1.jar`, mod id `tectonic`, versão `3.0.26`, com Lithostitched `1.8.0+beta6` presente. A release oficial `3.0.26-neoforge-21.1` continua sendo a build NeoForge 1.21.1 relevante.

Config, seed generation, structure placement e old/new chunk seams não foram testados nesta recatalogação. O fix de mountain jaggedness/config da 3.0.26 permanece regression gate documental.

## 13. Revalidação física — 13/09/2026
O runtime físico permanece Tectonic 3.0.26 para NeoForge 1.21.1 e a publicação aplicável localizada continua nessa versão. O inventário físico confirma Apollib 1.1.5 embarcado e, dentro dele, json5-java 3.0.0; ambos são componentes internos sem ordinal próprio. Nenhum teste de seed, pregen, old/new chunk seams ou jaggedness foi executado.