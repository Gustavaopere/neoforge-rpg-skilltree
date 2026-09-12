# Puddles & Floods

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8188addec05b6257f8a3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `puddleflood-1.1.5+1.21.1-neoforge.jar`, mod id `puddleflood`, runtime `1.1.5`; Forgified Fabric API presente no snapshot físico
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Puddles & Floods 1.1.5 está presente como build NeoForge nativa e FFAPI está presente como dependência. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Puddles & Floods
- **Arquivo JAR:** `puddleflood-1.1.5+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Clima, QoL, Worldgen
- **Função:** Adiciona puddles detalhadas que aparecem com chuva, conectam-se entre si e permitem que margens de rios pareçam transbordar visualmente durante condições úmidas.
- **Dependências:** Forgified Fabric API é Required Dependency da build NeoForge 1.21.1. Sinytra Connector NÃO é requisito desta build nativa NeoForge.
- **Sobreposição:** Complementa clima/precipitação superficial; não substitui Ecliptic Seasons, worldgen principal ou sistema de temperatura.
- **Compatibilidade/Riscos:** Riscos: FFAPI↔Connector confusion, client/server divergence, puddle buildup, water-height seams, shader artifacts, chunk boundaries, config loading e trigger duplication. A maioria dos recursos pode funcionar client-side, mas não foi generalizado para todos.
- **Observações:** Release 1.1.5 NeoForge 1.21.1 de 08/07/2026. Changelog: fix de config em Forge e opção de desabilitar elevação da água para conectar puddles, além de suporte/traduções.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais Puddles & Floods 1.1.5 + presença física de Forgified Fabric API.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/puddles-floods
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Puddles & Floods 1.1.5 reconstruído: puddle triggers/connections, flooding, evaporation, water-height option, shaders, FFAPI/native-NeoForge boundary, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `puddleflood-1.1.5+1.21.1-neoforge.jar`, mod id `puddleflood`, versão `1.1.5`, **build NeoForge nativa** para 1.21.1. O projeto requer **Forgified Fabric API** no NeoForge; isso não implica Sinytra Connector. O mod cria puddles conectáveis e flooding visual/dinâmico em condições úmidas.

## 1. Identidade e papel
- **Mod:** Puddles & Floods.
- **JAR:** `puddleflood-1.1.5+1.21.1-neoforge.jar`.
- **Mod id:** `puddleflood`.
- **Runtime:** `1.1.5`.
- **Loader/jogo:** NeoForge 1.21.1 nativo.
- **Licença:** MIT.
- **Papel:** puddles dinâmicas durante eventos de água/chuva e aparência de alagamento em áreas baixas/margens.
- **Decisão:** Sem decisão.

## 2. Puddle block e conexão visual
O projeto adiciona um bloco de puddle detalhado que pode sobrepor visualmente blocos adjacentes e conectar-se a outras puddles. A presentation deve acompanhar a distribuição funcional sem z-fighting/artefatos excessivos.

## 3. Gatilhos de formação
A documentação publica múltiplos gatilhos:
- chuva sobre o solo;
- bloco abaixo de drip;
- água fluindo sobre bloco;
- splash potion atingindo bloco;
- quebra de cauldron cheio;
- derretimento de neve.

Cada gatilho deve ser testado separadamente para evitar spawn duplicado ou puddle persistente sem causa.

## 4. Flooding em áreas baixas
Puddles próximas podem se combinar visualmente/espacialmente em vales, rios, costas e outras áreas baixas, produzindo efeito de flooding.

Isso não deve ser interpretado automaticamente como simulação hidrológica completa ou alteração permanente do worldgen.

## 5. Coleta e evaporação
Quantidade/coverage, raio ao redor do jogador e velocidades de acumulação/evaporação são configuráveis. Esses parâmetros controlam densidade e custo runtime.

Testar chuva iniciando/parando, sol/tempo seco e movimento do jogador entre regiões para evitar acúmulo infinito ou popping intenso.

## 6. Elevação de água para conexão
A 1.1.5 adiciona opção para **desabilitar raising water height to connect to puddles**. Esse é um regression/config gate direto, especialmente perto de rios/coasts onde alterar visualmente a altura pode produzir seams com shaders ou blocos adjacentes.

## 7. Shaders
O projeto expõe opção relacionada à água de shader packs. Com Iris/shaders, validar reflexão/transparência, depth e transições sem assumir que todo shader use o mesmo pipeline.

## 8. Forgified Fabric API ≠ Sinytra Connector
A publicação NeoForge exige **Forgified Fabric API**. O JAR é nativo NeoForge; portanto FFAPI é uma dependency/API, não evidência de que o mod esteja sendo executado através do Sinytra Connector.

O pack mantém FFAPI top-level, satisfazendo o requisito publicado.

## 9. Client/server boundary
A documentação informa que **a maior parte dos recursos pode funcionar client-side sem o servidor instalar o mod**. Isso não autoriza afirmar que 100% do state é client-only.

Ao usar em servidor modded, verificar quais aspectos são puramente visuais e quais dependem de sync/block state. Não usar puddle visual como trigger de gameplay/quest sem confirmar authority.

## 10. Configuração
Config pode ser acessada via comando `/puddleflood` ou menu de mods conforme publicação. Parâmetros relevantes incluem coverage durante chuva/storm, range de spawn, velocidade de coleta/evaporação, shader water e opção de elevar água para conexão.

Valores locais não foram lidos neste lote.

## 11. Release 1.1.5
A release instalada foi publicada em 08/07/2026. Changelog inclui:
- suporte a versões Minecraft mais novas na mesma família de release;
- **fix de config não carregando em Forge**;
- opção para desabilitar elevação de água ao conectar puddles;
- traduções chinesas.

Para esta build NeoForge, tratar config loading e water-height option como regression gates.

## 12. Relação com clima/worldgen
Ecliptic Seasons/clima determinam condições sazonais/meteorológicas em seu próprio domínio; Puddles & Floods reage a água/chuva e produz apresentação/estado superficial. Não substitui sistema de temperatura, estação ou worldgen principal.

Em BWG/Terralith/Tectonic, testar apenas o resultado em superfícies/biomas, sem atribuir geração do terreno ao mod.

## 13. Lifecycle e performance
Cobrir:
- início/fim de chuva;
- chunk unload/reload;
- dimension change;
- reconnect;
- shader toggle/reload;
- config changes;
- grandes áreas com alta coverage.

Raio/coverage altos podem aumentar quantidade de puddles/updates; medir em chuva intensa.

## 14. Riscos
1. **FFAPI dependency confusion:** confundir API requerida com Connector.
2. **Client/server divergence:** presentation local não deve virar gameplay authority.
3. **Puddle buildup:** evaporação/config inadequada gera densidade excessiva.
4. **Water-height seams:** conexão visual altera nível de forma estranha.
5. **Shader artifacts:** depth/reflection/transparency.
6. **Chunk boundaries:** puddles/flood visual ficam cortados/stale.
7. **Config loading:** regressão citada na 1.1.5.
8. **Trigger duplication:** múltiplas causas criam puddle repetida no mesmo local.

## 15. Matriz de testes
- [ ] Cliente inicia com Puddles & Floods 1.1.5 + FFAPI; servidor conforme modo adotado.
- [ ] Chuva gera puddles dentro de coverage/range configurados.
- [ ] Drip, flowing water, splash potion, cauldron break e snow melt geram apenas quando esperado.
- [ ] Puddles conectam sem duplicação/z-fighting.
- [ ] Evaporação reduz state após condições secas.
- [ ] Opção de raising water height liga/desliga comportamento corretamente.
- [ ] River/coast flooding não deixa seams graves.
- [ ] Shader on/off mantém água visualmente coerente.
- [ ] Chunk unload/reload/reconnect não duplica puddles.
- [ ] FFAPI presente satisfaz dependency sem necessidade de Sinytra Connector.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: `puddleflood-1.1.5+1.21.1-neoforge.jar`, mod id/runtime e FFAPI top-level presente.
- CurseForge/Modrinth oficiais: Release NeoForge nativa 1.21.1 1.1.5 de 08/07/2026; Forgified Fabric API required no alvo NeoForge.
- Descrição oficial: puddles, triggers, flooding, config e shader option.
- Changelog 1.1.5: config fix e opção de desabilitar raising water height.
- **Limite:** config local e divisão exata client/server de cada feature não foram inspecionadas; “most features can work client-side” não foi extrapolado para todas.
