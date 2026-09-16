# Northstar Redux

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `Northstar-0.6.5+1.21.1.jar`
- **Versão 1.21.1:** 0.6.5+1.21.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Exploração
- **Função:** Addon espacial do ecossistema Create com rockets, transição orbital/dimensional, telescope/seleção de planetas, rendering espacial e sistemas ambientais/tecnológicos associados.
- **Dependências:** Create + GeckoLib requeridos pela linha/source oficial. Addons como Northstar Curios Compat são integrações separadas.
- **Sobreposição:** Creating Space compartilha a camada espacial, mas coexistência é decisão deliberada; não tratar como duplicata exata. Create continua authority de kinetic/contraption system.
- **Compatibilidade/Riscos:** Runtime 0.6.5. Permanecem riscos em rocket/orbit transition, Return Ticket state, telescope/render/camera, atmosphere/oxygen, Create/GeckoLib drift, Sable/zero-g e coexistência com Creating Space.
- **Observações:** Runtime físico 0.6.5+1.21.1, publicado em 09/09/2026. A antiga 0.6.4 e o commit `2abd30b71470d168b18a132172b5120f15fbfe61` permanecem histórico técnico do telescope/rocket baseline; 0.6.5 deixou de ser update candidate e agora é a build instalada.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Northstar 0.6.5+1.21.1 + source/commit 0.6.4 previamente auditados. Nenhum runtime test executado nesta reauditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-northstar
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 0.6.4 para 0.6.5+1.21.1; o antigo bloco de update candidate foi promovido e reconciliado ao runtime instalado. Certificação pendente de QC/re-fetch final.
- **Histórico da decisão:** Em 22/08/2026 o usuário definiu manter Northstar Redux junto de Creating Space para substituir Stellaris e evitar solução espacial excessivamente tecnológica. Decisão preservada.
- **Data da última decisão:** 2026-08-22

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `Northstar-0.6.5+1.21.1.jar`, mod id `northstar`, versão `0.6.5+1.21.1`, NeoForge 1.21.1. Northstar Redux é o provider de seu conteúdo espacial, rockets, planets, atmosphere e telescope; Create permanece authority da infraestrutura cinética/contraptions.

## 1. Identidade, versão e decisão
- **Mod:** Northstar Redux.
- **JAR físico:** `Northstar-0.6.5+1.21.1.jar`.
- **Mod id:** `northstar`.
- **Runtime:** `0.6.5+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** Astronauts of Create / Northstar Redux.
- **Ambiente:** Client & Server.
- **Licença:** MIT.
- **Papel:** exploração planetária, rockets, telescope/rendering espacial e sistemas ambientais/tecnológicos associados.
- **Decisão:** **Manter**.

A coexistência com Creating Space continua decisão explícita do pack, não acidente da modlist.

## 2. Dependências e ownership
A linha oficial exige **Create** e **GeckoLib**. Bridges como Northstar Curios Compat permanecem projetos separados. Create mantém kinetic system/contraptions; Northstar mantém rocket, destino/orbit transition, atmosphere/oxygen, telescope e conteúdo espacial próprio. Integrações não devem duplicar esses states.

## 3. Baseline histórico 0.6.4 — rockets e orbit transition
A 0.6.4 corrigiu rockets que podiam parar ao se aproximar da órbita por baixo. Esse histórico continua relevante ao regression da 0.6.5: decolagem, aproximação orbital, chegada/transferência, retorno e restart/reconnect em viagem precisam permanecer estáveis.

## 4. Return Tickets
A 0.6.4 corrigiu Return Tickets para retornar ao ponto da decolagem anterior quando jogador permanece na mesma dimensão. Estado posicional/dimensional pode ficar stale, apontar para destino inseguro ou interagir com outros teleports; isso continua gate no runtime atual.

## 5. Telescope e seleção de planetas
O commit `2abd30b714...` da 0.6.4 corrigiu bounds/zoom da `TelescopeScreen`: margem visível, zoom inicial/velocidade, escala/drag e planetas escondidos; também houve fix de seleção com no-op renderer. A 0.6.5 mantém telescope como superfície crítica e adiciona Ponder para **Interplanetary Navigator**. UI é client-facing, mas destino válido precisa estar coerente com dados server/world.

## 6. Rendering espacial e environment
O baseline 0.6.4 corrigiu third-person zoom após dimension change, `daytime_star_brightness`, stars/planets durante chuva e visibilidade configurável. A 0.6.5 adiciona/fixa outras superfícies ambientais e de renderer, portanto dimension transition, weather/atmosphere e resource rendering continuam regression gates.

## 7. Atualização instalada — 0.6.5
A release 0.6.5+1.21.1, publicada em 09/09/2026, deixou de ser candidata e é o runtime físico. Mudanças publicadas incluem:
- retomada/ajustes de suporte a **Create 5** na codebase compatível;
- traduções **Português-BR** e **Turco**;
- **oxidizer tank opcional** para combustion engine;
- Ponders para **Combustion Engine**, **Oxygen Filler**, **Rocket Thruster**, **Interplanetary Navigator** e **Auto Lander**;
- config para **auto-relight**;
- redstone podendo desabilitar engine;
- limites/ajustes de velocidade do Oxygen Filler;
- suporte Sable para punching de sublevels em **zero-g**;
- iron spacesuits tintáveis;
- atmosphere definition aceitando múltiplos fluids;
- fixes em brilho de planetas, hand rendering, sealed-area sandstorms, temperatura `ultrawarm`, fluid placement, rocket picking/contraptions, combustion engine model/rotation, recipes/namespaces e outras regressões da linha.

Essas mudanças agora pertencem ao runtime instalado e precisam de QA correspondente.

## 8. Atmosphere, oxygen e múltiplos fluids
A 0.6.5 amplia definição de atmosphere para múltiplos fluids e toca Oxygen Filler/engines. Isso aumenta o risco de config/data drift e de scripts assumirem um único fluid. Atmosphere/oxygen permanecem authority do Northstar; quests/perks devem usar dados/IDs reais da build.

## 9. Create/Sable e contraptions
Create continua provider cinético; Northstar acrescenta máquinas/rocket. A 0.6.5 corrige/ajusta rocket picking de contraptions, engine rotation/model e integra zero-g/Sable. Testes devem separar state de contraption Create do state espacial Northstar para evitar dupes, desync ou objetos presos entre sublevels/dimensões.

## 10. Client/server e lifecycle
Server-authoritative: viagem/dimensão, posição/destino, machines/rocket e efeitos ambientais com gameplay. Client-facing: telescope, star/planet rendering, camera, Ponders e GeckoLib rendering. Eventos críticos: launch, orbit transition, dimension change, relog/restart, Return Ticket, atmosphere/oxygen e resource reload.

## 11. Coexistência com Creating Space
Northstar + Creating Space permanecem deliberadamente juntos. Cada mod mantém destinations/machines/progression próprios; quests podem conectá-los, mas não podem presumir equivalência de oxygen, rocket, atmosphere ou planet state sem bridge explícita.

## 12. Addons e integrações
Northstar Curios Compat e outros addons relacionados permanecem authorities próprias. Uma bridge instalada não autoriza atribuir sua feature ao mod base.

## 13. Riscos do runtime 0.6.5
1. rocket/orbit lifecycle e contraption pickup;
2. Return Ticket state;
3. telescope/Interplanetary Navigator UI e destination validity;
4. camera/render após dimension change;
5. atmosphere com múltiplos fluids;
6. oxygen filler speed/state;
7. engine redstone/auto-relight/oxidizer behavior;
8. Create/GeckoLib/Sable drift;
9. sealed-area weather/sandstorm e ultrawarm temperature;
10. coexistência de providers espaciais sem ownership claro.

## 14. Matriz de testes — 0.6.5 instalado
- [ ] Dedicated server/client iniciam com Northstar 0.6.5 + Create + GeckoLib atuais.
- [ ] Telescope zoom/drag/bounds e seleção funcionam.
- [ ] Ponders novos carregam sem missing assets/recipes.
- [ ] Rocket decola, pega contraption e atravessa orbit transition sem stall/dupe.
- [ ] Dimension change preserva camera/render correto.
- [ ] Return Ticket retorna ao ponto válido esperado.
- [ ] Star/planet brightness/weather respeitam config/dimensão.
- [ ] Atmosphere com múltiplos fluids carrega/processa sem erro.
- [ ] Oxygen Filler respeita limits/config.
- [ ] Combustion Engine: oxidizer opcional, rotation/model, redstone disable e auto-relight.
- [ ] Sable zero-g/sublevel punching não corrompe state.
- [ ] Reconnect/restart após viagem conserva posição/state seguro.
- [ ] Creating Space coexiste sem confundir destinations/quests/recipes.

**Nenhum teste foi executado nesta reauditoria documental.**

## 15. Evidências e limites
- modlist física de 16/09/2026: `Northstar-0.6.5+1.21.1.jar`;
- CurseForge oficial 0.6.5+1.21.1 de 09/09/2026;
- source oficial `Astronauts-of-Create/Northstar-Redux` e commit 0.6.4 `2abd30b71470d168b18a132172b5120f15fbfe61` preservados como baseline histórico;
- dossiê anterior sobre rockets, tickets, telescope, rendering, lifecycle, Creating Space e addons preservado;
- configs físicas específicas de atmosphere/oxygen não foram lidas e runtime não foi exercitado.

## 16. Reauditoria física — 16/09/2026
0.6.5 foi promovida de update candidate para runtime físico confirmado. A decisão `Manter` permanece. Nenhum teste de rocket, orbit, renderer, atmosphere, oxygen, Sable, Create ou multiplayer foi executado.