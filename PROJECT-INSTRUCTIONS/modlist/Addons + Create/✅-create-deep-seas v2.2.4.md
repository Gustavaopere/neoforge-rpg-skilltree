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
