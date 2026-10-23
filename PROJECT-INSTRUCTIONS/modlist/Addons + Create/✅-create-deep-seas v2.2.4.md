# Create Deep Seas

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#162**: JAR `create_submarine-2.2.4.jar`, mod id `create_submarine`, runtime `2.2.4`, SHA-1 `fb4902c2ca3463ea3016efbae7011408eb008d89`.

## Propriedades do registro

- **Mod:** Create Deep Seas
- **Arquivo JAR:** create_submarine-2.2.4.jar
- **Versão 1.21.1:** 2.2.4
- **Categoria:** Tecnologia, Exploração
- **Função:** Addon submarino/aquático para o stack Create Aeronautics/Sable, com sistemas técnicos de vessel/water-culling e conteúdo de exploração/Abyss na linha pública do projeto.
- **Dependências:** Pack físico: Create 6.0.10 + Create Aeronautics 1.3.2 + Sable 2.0.5; Copycats+ 3.0.9 é diretamente relevante aos fixes de cascade crash 2.2.4.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** 2.2.4 corrige crashes críticos de dedicated server por FlowingFluidMixin/client stripping e referência client-only em packet comum. A linha 3.x amplia drasticamente boat/high-seas, flooding, buoyancy e water-culling; 3.1–3.3 corrigem crashes/threading/FPS/Sodium holes. Riscos remanescentes: vessel/physics desync, water-culling stale, chunk/dimension lifecycle, drift Sable e overlap potencial com Deep Seas - Lava Fix.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_submarine` 2.2.4 + CurseForge oficial revalidado em 03/10/2026, que publica a sequência `3.0.0 → 3.1.0 → 3.2.0 → 3.3.0`; source oficial `MaxCreateMC/Create-Deep-Seas` usado para o changelog técnico da linha 3.x.
- **Observações:** JAR/mod id/runtime 2.2.4 confirmados. Upstream atual é 3.3.0 (02/10/2026). O salto 2.2.4→3.x é estrutural: High Seas/boats/sails/wind/engines, novo flooding/buoyancy/water rendering e múltiplos hotfixes de estabilidade/performance.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece `create_submarine-2.2.4.jar` / 2.2.4. Foram percorridas todas as releases posteriores `3.0.0 → 3.1.0 → 3.2.0 → 3.3.0`; 3.3.0 é a latest 1.21.1, sem promoção do version pin físico.
- **Decisão:** Sem decisão
- **Sobreposição:** Nicho submarino sobre Aeronautics/Sable. O addon separado Create: Deep Seas - Lava Fix é potencial overlap de patch e deve ser auditado separadamente; não foi classificado aqui como redundante.

# Dossiê operacional — padrão Alex's Mobs
> 🌊 **Identidade física confirmada:** `create_submarine-2.2.4.jar`, mod id `create_submarine`, runtime `2.2.4`, NeoForge 1.21.1. A release física continua Client & Server. O source atual avançou para a linha 3.x e é usado apenas para documentar updates upstream, nunca para reescrever a identidade do JAR instalado.

## 1. Papel e authority
Create Deep Seas adiciona navegação/submarinos ao stack físico baseado em **Create Aeronautics/Sable**. O addon owns seus blocos/sistemas submarinos, lógica aquática e conteúdo de exploração associado. Create Aeronautics/Sable continuam owners da infraestrutura física/moving contraptions que o addon consome.
Não tratar Create Deep Seas como engine física independente.
## 2. Estrutura pública do projeto
O source público organiza o projeto em superfícies `Create Submarine`, `Create Abyss` e `Create High Seas`, além de conteúdo relacionado a dimensão/abyss. Como o source `main` está em 2.2.3, esses nomes são evidência arquitetural da linha, não prova de cada registry entry do JAR 2.2.4.
## 3. Create Submarine
A documentação pública descreve Create Submarine como a parte técnica voltada a submarinos/boats e integração com Create Aeronautics. O addon fornece blocos e sistemas para construir/operar veículos aquáticos sobre a infraestrutura física do stack.
Assemblies, forças, docking e movimento precisam ser resolvidos pelo runtime real, sem reproduzir state de física em scripts externos.
## 4. Water culling
O source público descreve um **water culling system** próprio. Essa superfície é sensível porque render/occlusion aquática pode divergir da simulação física ou do state de fluido do servidor.
Culling é presentation/client-facing; não deve ser authority para volume de água, colisão, pressão ou estado de vessel.
## 5. Create Abyss e exploração
A arquitetura pública inclui módulo/conteúdo `create_abyss`, com dimensão, blocos e peixes relacionados ao Abyss. World/dimension state é server-authoritative. Teleporte, unload e retorno ao Overworld não podem abandonar vessel/contraption state ou duplicar entidades.
Parâmetros de geração, IDs de bioma e tabelas de spawn não são inferidos nesta ficha sem pin 2.2.4.
## 6. Fix crítico da 2.2.4 — dedicated server
O changelog oficial de 17/06/2026 corrige um crash que impedia dedicated servers de iniciar por causa de `FlowingFluidMixin` combinado com stripping de código client-side.
Esse é um regression gate obrigatório: boot de dedicated server com o pack completo precisa ocorrer sem classloading de código client-only nessa superfície.
## 7. Fix crítico da 2.2.4 — packet/render boundary
A mesma release corrige referência de uma classe client-only (`SubLevelCrackRenderer`) dentro de um packet comum. O erro causava crash do Mixin preprocessor e falha em cascata de mods dependentes, explicitamente incluindo **Copycats+** e **Create Aeronautics** no changelog.
Isso define um boundary claro: packets/common code não podem depender de renderer client-only.
## 8. Stack físico pertinente
O pack atual contém:
- Create Aeronautics 1.3.2;
- Sable 2.0.5;
- Copycats+ 3.0.9;
- Create 6.0.10.
Logo os dois cenários de cascata citados upstream são relevantes diretamente ao pack atual, embora o changelog indique que foram corrigidos na 2.2.4.
## 9. Divergência source ↔ JAR
O JAR físico/publicado no pack é 2.2.4, enquanto o repositório oficial avançou posteriormente para a linha 3.x. Por isso classes/IDs/valores da 3.x são tratados abaixo como **upstream não instalado** e não são transportados automaticamente para o runtime 2.2.4.
## 10. Create: Deep Seas - Lava Fix
O pack possui também um addon separado **Create: Deep Seas - Lava Fix**. Sua necessidade deve ser auditada na própria posição física, porque a 2.2.4 já contém fixes importantes de fluid/mixin/server.
Nesta página não se presume que o Lava Fix seja redundante nem necessário; apenas se registra a superfície potencial de overlap.
## 11. Client/server
Physics authority, vessel state, dimension state e gameplay devem convergir no servidor. Water culling/render e efeitos visuais são client-facing.
A 2.2.4 existe justamente para reforçar esse separation boundary; qualquer nova referência client-only em common packet é regressão grave.
## 12. Multiplayer
Dois clientes precisam observar posição/orientação do mesmo submarine de forma convergente. Entrada concorrente, piloto desconectando, passenger state e reconexão não podem gerar vessel duplicado, teleporte fantasma ou controle persistente de usuário ausente.
## 13. Chunk lifecycle
Submarinos podem atravessar chunk boundaries e coexistir com sublevels/contraptions móveis. Testar unload/reload, restart com vessel parado/em movimento e chunks parcialmente carregados.
Caches de fluid/culling e physics attachments não podem sobreviver apontando para world/contraption já descarregado.
## 14. Dimension lifecycle
Entrar/sair de dimensões, morrer/relogar durante exploração Abyss e reiniciar o servidor devem manter ownership/position coerentes. Se vessels não forem suportados em determinada transição, o runtime deve falhar de forma segura, não duplicar state.
## 15. Sobreposição com outros veículos
O pack contém diversas soluções Create/Aeronautics/veiculares. Create Deep Seas tem niche submarino/aquático; overlap funcional deve ser avaliado por veículo e control surface, não pelo simples fato de múltiplos mods moverem contraptions.
## 16. Riscos
1. Dedicated server crash por regressão do `FlowingFluidMixin`.
2. Common packet volta a referenciar classe client-only.
3. Crash em cascata envolvendo Copycats+/Aeronautics.
4. Water culling fica stale após assemble/disassemble/unload.
5. Vessel position diverge entre server e clientes.
6. Contraption/sublevel perde state ao cruzar chunks.
7. Dimension change duplica ou perde vessel/passenger.
8. Sable/Create Aeronautics drift entre o runtime 2.2.4 e a arquitetura 3.x.
9. Lava Fix externo duplica patch já resolvido ou conflita com 2.2.4.
10. Dois sistemas de input/physics aplicam movimento concorrente.
## 17. Matriz de testes
- [ ] Dedicated server inicia com Deep Seas 2.2.4 + Create Aeronautics 1.3.2 + Sable 2.0.5 + Copycats+ 3.0.9.
- [ ] Não ocorre crash de `FlowingFluidMixin` no boot.
- [ ] Não há referência client-only em common networking durante conexão/join.
- [ ] Submarine pode ser construído/assemblado conforme runtime real.
- [ ] Pilotagem aquática permanece server-authoritative com dois clientes.
- [ ] Water culling acompanha assemble/disassemble sem água/render fantasma.
- [ ] Chunk unload/reload preserva ou reconstrói vessel state corretamente.
- [ ] Restart com submarine existente não duplica/perde contraption.
- [ ] Passenger/pilot disconnect libera controle corretamente.
- [ ] Transições envolvendo conteúdo Abyss não deixam state órfão.
- [ ] Copycats+ e Aeronautics carregam sem cascade crash.
- [ ] Deep Seas - Lava Fix é testado separadamente contra 2.2.4 antes de qualquer decisão de remoção.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 18. Evidências e limites
A modlist física confirma JAR/mod id/runtime 2.2.4 e mixins próprios. A publicação oficial confirma release NeoForge 1.21.1, Client & Server e os dois fixes críticos de dedicated server/network-client boundary. O source oficial atual confirma a evolução 3.x, mas essas internals permanecem upstream não instaladas; internals não explicitamente confirmados para 2.2.4 continuam fail-closed.
> 🔒 **Boundary canônico:** Deep Seas owns a camada submarina/aquática; Aeronautics/Sable own a infraestrutura física subjacente. Common/server code não pode depender de render client-only, e o JAR 2.2.4 prevalece sobre o source 2.2.3 para identidade/versionamento.

## 19. Atualizações upstream 3.0.0 → 3.3.0 — não instaladas

A autoridade física permanece em **Create Deep Seas 2.2.4**. O CurseForge para NeoForge 1.21.1 publicou em 02/10/2026, em ordem, **3.0.0 → 3.1.0 → 3.2.0 → 3.3.0**. Não existe release intermediária entre 2.2.4 e 3.0.0.

### 3.0.0 — baseline da série 3.x / expansão High Seas

O changelog source atual não fornece um bloco detalhado sob o heading literal `[3.0.0]`; por isso esta ficha não inventa um mapping feature→file mais preciso do que o publicado. O histórico imediatamente associado à nova série registra uma expansão estrutural que passa a incluir:

- sistemas **High Seas** para boats/ships: vento, sails, furling, Wind Vane e integração Create Goggles;
- propulsion/control: **Boat Engine**, Helm, Rudder, Anchor, Oars, Seaglide e Buoys;
- hydrodynamic buoyancy e dry boat holds;
- water occlusion/culling compartilhado entre boats e submarines;
- progressive flooding, peso da água e efeitos de breach/implosion;
- render/shader integration com Sodium/Iris;
- configuração própria de High Seas para wind/sails/engine/oars/anchor/client effects.

Isso faz da série 3.x um update de arquitetura/gameplay, não um simples hotfix. O próprio CurseForge mostra que o JAR cresce de aproximadamente 1.8 MB na 2.2.4 para 7.7 MB na 3.x.

### 3.1.0 — hotfix

Correções materiais:
- elimina **triangle holes** na água em boats/holds sem piso, exigindo hull block abaixo para classificar hold;
- corrige crash **"Batching sections"** com grandes camadas de Ballast Tanks ao limitar a busca de connected texture à área segura do chunk builder;
- corrige world freeze ao entrar em mundo com submarine: hull cache compartilhado client/integrated-server passa a ser thread-safe;
- reduz forte queda de FPS do **Onboard Computer**, redesenhando blueprint apenas quando necessário;
- corrige water cubes/render em waterlogged blocks.

### 3.2.0

- **Progressive Flooding passa a ser experimental e desligado por padrão.** Sem ele, breach abaixo da waterline volta ao comportamento pré-3.0: o cômodo abre para o mar de uma vez; em boats, qualquer abertura em contato com o mar marca o hold como flooded e remove lift.
- corrige mar aparecendo ao redor de seats/slabs/stairs em rafts baixos;
- reduz stutter de submarinos oxygenated ao evitar rebuild excessivo de chunk sections;
- adiciona plugin de mixin para remover compats de Copycats+/Sodium/Iris/Distant Horizons quando o provider opcional não está instalado, eliminando warnings de classloading falsos;
- corrige water-culling stale após substituição de bloco.

### 3.3.0 — latest

- corrige **FPS collapse em ships rápidos e selados**: sem Sodium, rebuilds de chunk section desnecessários são pulados; com Sodium, só sections com conteúdo relevante são reconstruídas;
- corrige **ship-shaped holes no mar após join com Sodium**, rastreando sections temporariamente culled pelo fallback e reconstruindo-as quando o pixel-perfect culling entra;
- Boat Engine colocado com sneak passa a inverter orientação, alinhado a directional blocks/Create.

### Impacto no pack

O pack usa Create Aeronautics/Sable, Sodium-related rendering stack e várias integrações de vehicles. Portanto o salto 3.x precisa ser tratado como migração técnica:
- altera boat/submarine physics e flooding;
- toca render/culling/chunk rebuild;
- adiciona novas control surfaces;
- pode mudar necessidade/compatibilidade do addon **Deep Seas - Lava Fix**;
- exige world-copy test antes de atualizar um save real.

### Gate de promoção 2.2.4 → 3.3.0

- [ ] Fazer backup/cópia do mundo e testar upgrade direto 2.2.4→3.3.0.
- [ ] Dedicated server boot com Create 6.0.10 + Aeronautics/Sable stack atual.
- [ ] Submarine 2.2.4 existente carrega sem data loss.
- [ ] Boat/submarine assemble, pilot, save/restart e unload/reload.
- [ ] Progressive Flooding default OFF e toggle experimental funcionam conforme esperado.
- [ ] Breach/repair/flooding não duplica/remove blocks/fluids.
- [ ] Ballast Tanks grandes não reproduzem “Batching sections” crash.
- [ ] Join/rejoin com submarine não congela world/cache.
- [ ] Onboard Computer não reproduz regressão severa de FPS.
- [ ] Sodium: nenhum triangle/water cube/ship-shaped hole persistente.
- [ ] Fast sealed ship não força rebuild excessivo de chunks.
- [ ] Revalidar Deep Seas - Lava Fix contra 3.3.0 antes de mantê-lo/removê-lo.
- [ ] Revalidar Create Aeronautics/Sable bridges e vehicle controls.
- [ ] Confirmar recipes/configs novos sem reset indevido de configuração existente.

Fontes upstream: CurseForge Create Deep Seas 1.21.1, sequência 3.0.0→3.3.0; source oficial `MaxCreateMC/Create-Deep-Seas` `changelog.md`. Nenhum teste acima foi executado nesta atualização documental.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Create Deep Seas [BETA]
- **Último candidato 1.21.1 / NeoForge localizado:** **3.3.0 (Release estável, 02/10); 3.4.1 ALPHA-2 (17/10)**
- **Estado:** **3.3.0 CANDIDATO PENDENTE; 3.4.1 ALPHA-2 BLOQUEADO**
- **Gate de promoção:** Projeto experimental com Create/world content: validar recipes/machines/fluids, world/save, contraptions e dedicated server; não promover alpha.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas

## Auditoria integral das releases experimentais — 23/10/2026 (3.3.0 → 3.4.5)

**Autoridade física histórica:** `create_submarine-2.2.4.jar`, mod ID `create_submarine`, runtime `2.2.4`, #162 da modlist física. **Projeto:** *Create Deep Seas [BETA]* por MaxCreateMC, CurseForge **1533137**, NeoForge 1.21.1, categorias oficiais **Addons + Create** (diretório atual). A série **3.0.0 → 3.1.0 → 3.2.0 → 3.3.0** já foi analisada anteriormente neste dossier. Verificação de 23/10 identifica **seis releases experimentais posteriores** na mesma linha: **3.4.0 Alpha (16/10) → 3.4.1 Alpha (17/10) → 3.4.2 Alpha (18/10) → 3.4.3 Beta (18/10) → 3.4.4 Beta (18/10) → 3.4.5 Beta (22/10)**. A **última Release estável permanece 3.3.0 (02/10)**; **3.4.5 é a candidata mais recente, mas Beta**. O projeto contém builds Alpha que **não aparecem quando o filtro CurseForge oculta alphas**: verificar os artefatos por file ID, em vez de concluir que 3.4.0/3.4.1/3.4.2 não existem.

### 3.4.0 — Alpha, file ID 9216182 (16/10)

**Pressão e estrutura:** três modelos configuráveis (`pressureModel`) — **Classic**, **Hybrid** (padrão, escala por profundidade e forma do casco), **Realistic** (solver estrutural experimental). **Girder Beams** entre blocos do casco reforçam a estrutura em cerca de **60%** e podem ceder com aviso de 4 s; **Pressure Goggles** mostram mapas de pressão por cor (Absolute/Distribution/Off). `liquidDensities` aplica pressões diferentes aos líquidos (lava com pressão 3× maior), e `lavaBurnsHull` pode incendiar cascos inflamáveis em lava.

**Conteúdo:** alarmes personalizados em `.ogg` a partir de `config/create_submarine/alarms` com timing, volume e transmissão servidor→cliente; comando de operador **`/submarine repair`**; botão de config em pause/title; Oxygen Diffuser aceita fluidos da tag `#c:oxygen`; receita de Anchor; `seaglideFlightSpeed` ao usar Elytra; disco musical com Water Breathing nas proximidades; velas triangulares/fore-and-aft, **Halyards** via rope e Rope Winch, Ponders para sail/rudder; **Diving Chestplate e Leggings**, com suporte ao Copper Backtank no inventário; **limite de pressão do mergulhador** (120 m normal; 280 m com traje completo; morte 60 m após o limite, com HUD/aviso). Submarinos acoplados passam a seguir um líder de ballast controlado pelo Onboard Computer. Melhorias de culling e fog com shaders Iris.

**Mudanças de contrato:** **Sodium passa a ser requisito de cliente** nesta série; Lithostitched deixa de vir embarcado (ainda pode ser necessário para oceanos mais profundos e, se existir top-level no pack, manter sua entrada independente); Sail renomeada Ship Sail, velas funcionam fora da água, culling passa a cobrir todos os líquidos, layouts e vazamentos em partial blocks refinados. **Removidos:** furl manual de velas (usar halyard) e propulsão por water wheel em barcos.

**Correções importantes:** GPU sem OpenGL 4.0 tessellation, congelamento em join de grandes ships, quedas de FPS/frames em hulls e rafts, vazamentos visuais, drops/receitas de Engine/Anchor/Buoy/Decompression Chamber/Oar, lag de servidor, **duplicação em Oxygen Diffuser/Electrolyzer**, duplicação de combustível de Boat Engine, memória de servidor com muitos ships, controlos no dedicated server, interações com TV e radar de Create e persistência de compartimentos reparados.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9216182 .

### 3.4.1 — Alpha, file ID 9231952 (17/10)

**Vortex Engine:** novo renderer **cliente** para iluminação colorida, configurável nos gráficos ou `create_submarine-vortex.toml`, desativa-se com shader pack **Iris** ativo; aceita configurações de cores por `assets/<namespace>/vortex/glow/*.json`. Integra luz de Sonar, Industrial Alarm e Submarine Staff; estilos de luz **volumes/flood/vanilla/hybrid/pixel**, comandos `/vortex style light`, `/vortex mist` e `/vortex profiler` (relatórios em `vortex-profiles/`); shafts de sol e caustics sob água, névoa luminosa, partículas marinhas e wakes bioluminescentes. Opções gráficas separadas para Deep Seas, High Seas e Vortex no menu de vídeo. **Veil lights e a opção `veilLights` são removidas**, substituídas pelo Vortex Engine.

**Performance/rede:** Ballast Tank, Oxygen Diffuser, Water Thruster e Electrolyzer deixam de emitir updates a jogadores próximos todo tick; flooding agrupado por ship/tick, partículas de implosion limitadas a 256, análise do casco agendada em prioridade baixa.

**Solver Realistic:** calibragem contra pressão uniforme, ruptura sob tração para vidro/obsidian/amethyst/calcite/terracotta; conexão estrutural de slabs/stairs baseada em contatos reais. Isso modifica o comportamento de cascos existentes sob o modo experimental e não deve ser aplicado sem testes.

**Fixes críticos da própria 3.4.0:** leaves de árvores intactas decaying, lentidão geral de mineração/drops inexistentes para netherrack/blackstone/basalt, tags de logs e mineable não carregadas, loot table de Create Abyss inválida, verificações de ships carregando/gerando chunks distantes, lag com muitos ships, análises de casco reiniciando em loop, luz block updates presas em lentidão e Sonar exibindo terrain antigo.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9231952 .

### 3.4.2 — Alpha, file ID 9241368 (18/10)

- Pode desligar o billowing de velas com Iris na página **High Seas** das configurações gráficas.
- Corrige shaders pesados (ex.: IterationRP) sendo desativados por falta de texture units: culling só reserva unidades nos programas que as usam.
- Corrige luzes Vortex apagando irregularmente em bordas de fog; blocos vizinhos a sail distorcidos por shaders com terrain programs sem UV/texture coordinates.
- Fechar tela de Sonar não deixa terrain scan ativo em segundo plano; uma análise de casco não deve ser recacheada após remoção do navio.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9241368 .

### 3.4.3 — Beta, file ID 9243537 (18/10)

- Reduz pela metade o brilho dos blocos luminosos no Vortex; nova opção `brightness` (**10–200%**). Controles adicionais: **Light Reach, Light Saturation, Animated Lights, Bloom Strength, Glow in Water e Sunlight Under Water**.
- Corrige flashes/degraus luminosos no estilo **vanilla** ao redor de tochas, fogo e plantas luminosas; textos do modo fotossensível deixam de referir-se ao antigo Veil.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9243537 .

### 3.4.4 — Beta, file ID 9246826 (18/10)

- Por padrão, Vortex agora ilumina apenas blocos de **Create Deep Seas, Create High Seas e Create Abyss**, além das fontes móveis **Sonar/Industrial Alarm**; tochas, lanternas e demais fontes de Minecraft/outros mods mantêm iluminação vanilla. **Reduz a superfície e o custo do renderer**; a possibilidade de reativar iluminação de outros blocos aparece posteriormente na 3.4.5.
- Espalhamento da luz só nas proximidades de fontes, não em todo o volume de 128 blocos; evita reconstruir/enviar grid de blocos sem iluminação/água relevante e calcula bloom apenas quando ativado.
- Corrige custo indevido de GPU mesmo em mundo plano sem água ou fontes luminosas, além de evitar renderizar caustics/plankton e passes fullscreen fora da água.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9246826 .

### 3.4.5 — Beta, file ID 9290742 (22/10) — candidata experimental mais recente

- Casco de barcos/submarinos passa a **ocluir luz colorida** Vortex (inclusive Sonar), mas vidro pode transmiti-la; stained glass e panes filtram a cor; tinted glass bloqueia a luz.
- Blocos transparentes (água, vidro, gelo etc.) passam a receber cor luminosa na superfície. Nova opção `allBlocks` (**desligada por padrão**) permite luz colorida de tochas, lanternas, Sea Lanterns e blocos de outros mods. Preserva tonalidade da fonte, com seleção de cores a partir das texturas do modelo.
- Corrige profundidade do **Onboard Computer travada perto de 400 m**, quando o casco deixava de receber pressão adicional abaixo dessa profundidade (#126).
- Corrige **água ao redor de hold seco drenando permanentemente**, gerando buracos no mar e cubos azuis em casco rompido; buracos já presentes em mundos existentes voltam a ser preenchidos quando a água vizinha recebe updates (#124/#126).
- Corrige trilhas visuais de blocos luminosos dinâmicos atrás de ships em movimento; Onboard Computer com tela fantasma/diagrama deslocado após remoção e reload; crash com **Submarine Propeller 2×2**.
- Corrige modo pixel Vortex falhando em placas **NVIDIA** e névoa verde intensa com shaders Complementary/BSL/Bliss/Photon em GPUs com poucas unidades de textura.
- Quedas de água (waterfalls) deixam de contar como centenas de metros de profundidade para ships ou esmagar jogadores instantaneamente ao nadar para cima; somente água parada contribui para cálculo vertical.
- Nova configuração de servidor **`diverPressure`** permite desligar o dano de pressão no jogador.
- **Remoções da 3.4.5:** wakes bioluminescentes de jogadores/mobs (`wakeStyle`), iluminação da Submarine Staff segurada e fontes luminosas de blocos isolados lançados como physics objects. Estas funções estavam descritas na 3.4.1; **não se deve tratá-las como ainda presentes na última candidata**.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas/files/9290742 .

### Avaliação para o modpack e protocolo de promoção

1. **Separar estabilidade do canal de testes.** **3.3.0 é a última Release, 3.4.5 é Beta**. A instalação física segue **2.2.4**. Não substituir o JAR do modpack sem campanha de QA de save e de dependency compatibility.
2. **Dependências/embarcados:** a linha 3.4 muda o contrato do cliente para **Sodium obrigatório**; Lithostitched deixa de ser embarcado na 3.4.0, mas o Lithostitched top-level instalado deve permanecer entidade própria. As mudanças de `META-INF/jarjar` não promovem bibliotecas internas a entradas top-level.
3. **Server authority:** pressão, dano por profundidade, hull integrity, flooded state, oxygen, ballast, crafting, rotas e persisting ship/crew state pertencem ao servidor; Vortex light e overlays são cliente. Testar comunicação/caches sem updates redundantes.
4. **World safety:** testar cópia do mundo 2.2.4 com boats/submarinos existentes, comparar `pressureModel` Classic/Hybrid/Realistic, transições em lava/água, chunk load/restart, pressão além de **400 m**, eventos de implosion/repair, antigos buracos de água e limitações de worldgen.
5. **Anti-dupe:** verificar Oxygen Diffuser/Electrolyzer, fuel/Boat Engine, crafts e drops, repair/flooding, Onboard Computer e integração com Aeronautics/Sable/Deep Seas - Lava Fix. Controle de itens/fluids deve conservar quantidades.
6. **Performance e gráficos:** profiling com/sem Iris e Sodium, shaders pesados, GPUs com poucas unidades de textura, Vortex styles e `allBlocks`, server tick/MSPT em muitos ships, chunk rebuild, GPU idle em mundo plano, fog/shader da última Beta; não inventar números de FPS.
7. **QA de jogador e terceiros:** checar mergulho, armadura/backtank, `diverPressure`, quests/perks de risco, Halyards, sailing e water wheel removida, oclusão de luz, resource reload e suporte a players fotossensíveis (respeitar opções).
8. **Smoke tests:** dedicated server Java 21 / NeoForge 1.21.1, dois jogadores, reboot, unload/reload de ships, diferentes oceanos, Create 6.0.10/Aeronautics 1.3.2/Sable 2.0.5, crash logs, conflitos com mod de shaders/GUI/performace.

**Conclusão documental:** a sequência **2.2.4 → 3.0.0 → 3.1.0 → 3.2.0 → 3.3.0 → 3.4.0 → 3.4.1 → 3.4.2 → 3.4.3 → 3.4.4 → 3.4.5** está agora descrita, com os seis changelogs experimentais NeoForge 1.21.1 separados e links diretos aos artefatos. A pesquisa das publicações até **22/10/2026** pode ser **retirada da fila documental**, mas a adoção, verificação de binários/dependências e compatibilidade do save **não estão aprovadas/testadas**. O mod instalado é **2.2.4**, o arquivo `.md` continua **✅-create-deep-seas v2.2.4.md**. Reabrir a pesquisa se surgir release posterior à 3.4.5.
