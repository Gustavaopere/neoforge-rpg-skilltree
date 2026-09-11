# Create: Fantasizing Again

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db819b98b3dd263c32c930
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Fantasizing Again
- **Arquivo JAR:** `create_fantasizing-1.21.1-1.2.0-b3.jar`
- **Versão 1.21.1:** 1.2.0-b3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Automação
- **Função:** Addon Create de alto impacto com engines de stress, ferramentas de construção/destruição em lote, crates/Fluid Barrel, item transfer, Chromatic content, Powder Snow fluid e recipes de recursos raros.
- **Dependências:** Create 6.0.10 físico; NeoForge 1.21.1. O addon usa a infraestrutura cinética/stress do Create e registra seus próprios engines/storage/tools/recipes.
- **Sobreposição:** Sobreposição funcional parcial com outros addons Create. Interação concreta com Create: Deep Dark 3.0.2 pela copy recipe de Echo Shard e pela captura de Warden via Sculk Engine; avaliar progressão/custo real, não inferir incompatibilidade.
- **Compatibilidade/Riscos:** Beta mantida por fixes concretos. Riscos: regressão de dupe no Fluid Barrel, NPE/estado de Crate, stress excessivo, Block Placer pesado/bypass de proteção, Transporter dupe/loss, Warden/Echo overlap com Create: Deep Dark, Powder Snow capability duplicada e novas regressões da linha Beta.
- **Observações:** JAR físico `create_fantasizing-1.21.1-1.2.0-b3.jar`, mod id `create_fantasizing`, runtime 1.2.0-b3. CurseForge: CreateFantasizing 1.2.0-beta3, NeoForge 1.21.1, Client & Server, 06/08/2026. Beta3 corrige fluid duplication no Fluid Barrel e NPE do Crate.
- **Procedência:** modlist.txt física atual de 09/09/2026 + CurseForge oficial da beta3 + repositório oficial Ironnoob73/Create-FantasizingAgain, branch main contemporâneo, para engines/config/storage/tools.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fantasizing-again
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 1.2.0-b3; fixes de Fluid Barrel/Crate, engines, Block Placer, Transporter, Powder Snow capability, recipes raras, config e lifecycle catalogados.
- **Histórico da decisão:** 2026-09-06 — decisão Manter confirmada e pesquisa fechada. A beta3 foi preferida conscientemente por fixes de duplicação/NPE; não é beta mantida apenas por ser mais nova.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> ⚙️ **Identidade física confirmada:** `create_fantasizing-1.21.1-1.2.0-b3.jar`, mod id `create_fantasizing`, runtime `1.2.0-b3`. CurseForge identifica a mesma build como **CreateFantasizing 1.2.0-beta3**, Beta NeoForge 1.21.1, Client & Server, publicada em 06/08/2026.

## 1. Papel e authority
Create: Fantasizing Again é um addon de alto impacto para Create: adiciona fontes de stress, ferramentas de construção/destruição, storage, transferência de itens, fluid behavior e recipes próprias. **Create 6.0.10** permanece owner da rede cinética/stress e contraptions; Fantasizing owns seus engines, blocks, tools, containers, configs e receitas.

## 2. Estado da build — Beta deliberada
A versão física é **1.2.0-b3**, não a Release 1.1.3. A beta3 corrige dois defeitos de integridade importantes: **fluid duplication no Fluid Barrel** e um **NPE causado pelo Crate**. A decisão formal `Manter` permanece preservada; o canal Beta deve ser tratado como regression risk, não como motivo automático para remoção.

## 3. Engines e stress
O source atual da linha 1.2 registra/configura **Hydraulic Engine, Wind Engine, Sculk Engine e Yin Yang Engine**. A config server-side define stress fornecido por cada engine; defaults atuais no source são 8192 para Hydraulic/Wind/Sculk e 32768 para Yin Yang.
Esses valores são balance settings do addon. Integrações não devem criar uma segunda contabilização de stress nem considerar esses defaults imutáveis sem ler a config runtime.

## 4. Sculk Engine e Warden
A config possui opção `sculk_engine_frame_catch_warden`, permitindo que o Sculk Engine Frame capture Warden e se transforme em Sculk Engine. Isso toca diretamente a progressão Deep Dark do pack e precisa ser avaliado junto a Create: Deep Dark, mas ownership permanece separado.
Capturar/remover Warden deve ser server-authoritative e não pode duplicar entity, loot ou engine state após unload/restart.

## 5. Block Placer
O **Block Placer** é uma ferramenta de alcance elevado capaz de colocar e destruir blocos. O source expõe configurações de hardness máxima, range, brushes, cooldown, batch size e suporte a Infinity/Fortune/Silk Touch.
O default de range no source é 48 blocos e o batch size default é 256 blocos por server tick. Esses números tornam a ferramenta superfície relevante de performance, claims e balanceamento; a config runtime do pack ainda precisa ser lida antes de tratá-los como política ativa.

## 6. Tree Cutter
O **Tree Cutter** derruba a árvore inteira; a descrição pública registra que os blocos caem verticalmente. Isso amplia muito a atomicidade de uma ação de ferramenta: proteção/claims, drops e durability devem ser processados consistentemente para todos os blocos afetados, sem bypass por operação em lote.

## 7. Transporter
O **Transporter** transfere itens entre containers. O addon não deve tornar-se owner dos inventories externos: origem e destino continuam authorities de seus próprios contents. Transferência precisa ser exactly-once, inclusive em container cheio, unload e multiplayer concorrente.

## 8. Crates
O **Crate** pode combinar-se ao longo de três eixos e possui capacidade ajustável e outras funções. A linha 1.2 também adiciona/ajusta Brass Crate e Sturdy Crate, suporte a menus em crates montados em contraptions e controle da relação primary/secondary via Wrench.
A beta3 corrige um NPE de Crate; montagem, split/merge, contraption e reload são regression gates obrigatórios.

## 9. Fluid Barrel
O **Fluid Barrel** é storage de fluido e a beta3 corrige explicitamente duplicação. Portanto qualquer transferência fill/drain, break/place, contraption, pipe connection ou save/load precisa manter conservação estrita de volume e tipo.
Não marcar o bug como resolvido no pack sem smoke-test da combinação física completa.

## 10. Powder Snow como fluido
O projeto registra Powder Snow como fluido e a config permite desativar capabilities de Powder Snow Bucket/Cauldron quando outro mod também implementa essa superfície. O comentário do próprio source alerta que essa opção deve ser desligada em caso de provider concorrente e requer restart.
Isso é uma boundary explícita: não permitir duas capabilities para o mesmo container sem validar prioridade/semântica.

## 11. Chromatic content
O projeto inclui **Chromatic Compound**, Refined Radiance/Shadow Steel-related tunnel behavior e `Chromatic Tunnel`. A config permite silenciar partículas/som durante processing. Essas superfícies são conteúdo próprio; Create continua owner do transporte/process pipeline base.

## 12. Recipes de recursos raros
A linha 1.2 adicionou copy recipes para **Heart of the Sea, Heavy Core e Echo Shard**, além de chance de Stray dropar Zinc Ingot e chocolate obtível infinitamente segundo a documentação pública.
Essas rotas são de alto risco para progressão. No pack, Echo Shard também alimenta Create: Deep Dark; qualquer copy recipe precisa ser avaliada por custo líquido para não trivializar Warden/Deep Dark.

## 13. Config e reload
O source confirma três specs de config e reload handlers. Entre os parâmetros estão engine stress, Warden capture, Block Placer power/range/brush limits/cooldown/batch, enchantment behavior, Powder Snow capability e Chromatic Tunnel silent processing.
Algumas opções exigem restart; outras são recarregadas. Não assumir hot-reload universal.

## 14. Client/server e multiplayer
Menus, particles e previews são client-facing; block placement/destruction, inventory transfer, fluid storage, stress generation, crate topology e Warden capture são server-authoritative. Operações em lote precisam respeitar claims/permissões e evitar dupe sob latência.

## 15. Riscos
1. Regressão de Fluid Barrel volta a duplicar fluido.
2. Crate NPE reaparece em merge/contraption/menu.
3. Engines fornecem stress excessivo ou double-countado.
4. Block Placer causa pico de tick por batch/range ou bypassa proteção.
5. Infinity/Fortune/Silk Touch geram output incompatível com rules do pack.
6. Transporter duplica/perde itens em retries.
7. Warden capture conflita com loot/progressão de Create: Deep Dark.
8. Copy recipes trivializam Heart of the Sea, Heavy Core ou Echo Shard.
9. Dois providers registram Powder Snow capability simultaneamente.
10. Crates/Fluid Barrel perdem state em contraption/unload/restart.
11. Canal Beta introduz regressão não coberta pela Release 1.1.3.

## 16. Matriz de testes
- [ ] Dedicated server inicia com 1.2.0-b3 + Create 6.0.10.
- [ ] Fluid Barrel fill/drain/break/place não duplica fluido.
- [ ] Crate simples, double/multi-axis e contraption não geram NPE.
- [ ] Stress de cada engine corresponde à config sem double-count.
- [ ] Sculk Engine Frame captura Warden exatamente uma vez quando habilitado.
- [ ] Block Placer respeita hardness/range/batch e proteções do servidor.
- [ ] Tree Cutter processa árvore sem dupe/drop loss.
- [ ] Transporter faz transferência exactly-once com destino cheio e sob unload.
- [ ] Powder Snow capability não é registrada em duplicidade com outro provider.
- [ ] Copy recipes de Echo/Heart/Heavy Core não formam loops de progressão.
- [ ] Restart e `/reload` mantêm crate/fluid/config state coerente.
- [ ] Multiplayer concorrente não duplica inventory/fluid/block operations.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
A modlist física confirma o JAR e runtime `1.2.0-b3`. CurseForge oficial confirma Beta, Client & Server e os fixes beta3. O repositório oficial `Ironnoob73/Create-FantasizingAgain`, branch `main` contemporâneo à publicação, confirma engines, Crate/Fluid Barrel, Block Placer, configs e Powder Snow capability. O branch não é tag imutável do binário; classes/valores são evidência da linha atual, enquanto comportamento final deve ser validado no runtime físico.

> 🔒 Boundary canônico: **Fantasizing owns seus engines/tools/storage/recipes; Create owns stress e infraestrutura cinética; inventories/entities externos permanecem sob seus providers**. Integridade de fluidos, crates e operações em lote é o gate principal da beta3.