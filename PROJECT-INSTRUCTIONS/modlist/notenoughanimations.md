# NotEnoughAnimations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813aa07be32009f97a70
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`, mod id `notenoughanimations`, runtime `1.12.4`, mixin `notenoughanimations.mixins.json`; TRansition `1.0.21` e TRender `1.0.15` confirmados como JARs embarcados sob `META-INF/jars`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, NotEnoughAnimations 1.12.4 e os componentes embarcados citados estão confirmados.

## Propriedades do banco

- **Mod:** NotEnoughAnimations
- **Arquivo JAR:** `notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`
- **Versão 1.21.1:** 1.12.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Mod client-side que amplia animações visuais do jogador em terceira pessoa para eating/drinking, maps, shield, bow/crossbow, boats, horse reins, compass/clock e outras ações configuráveis.
- **Dependências:** Cliente NeoForge 1.21.1. TRansition 1.0.21 e TRender 1.0.15 estão embarcados no JAR sob META-INF/jars e pertencem ao host.
- **Sobreposição:** Compartilha presentation layer com Epic Fight, First Person Model, CPM, Punchy e animation APIs, mas não é equivalente a esses sistemas. Conflitos devem ser tratados por pose/feature específica.
- **Compatibilidade/Riscos:** Client-only visual. Riscos: pose/armature overlap com Epic Fight/CPM/player-animation mods, offhand/dual-wield render, mounts e config layering. TRansition 1.0.21 e TRender 1.0.15 são embedded, não top-level.
- **Observações:** Runtime 1.12.4, file ID 8274908, Release NeoForge 1.21.1 de 18/06/2026. Embedded: TRansition 1.0.21 + TRender 1.0.15. Não altera authority server-side de gameplay.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 1.12.4 + documentação oficial de features + metadata física dos embedded jars.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/not-enough-animations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — NotEnoughAnimations 1.12.4 reconstruído: animações third-person, client-only boundary, config por feature, compat com Epic Fight/First Person/CPM, embedded TRansition/TRender, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`, mod id `notenoughanimations`, versão `1.12.4`, NeoForge 1.21.1. É um mod **client-side e visual** que leva ações normalmente vistas em primeira pessoa para uma representação de terceira pessoa mais completa. O JAR embute `TRansition 1.0.21` e `TRender 1.0.15` sob `META-INF/jars`; ambos pertencem ao host e não são entradas top-level.

## 1. Identidade e papel
- **Mod:** NotEnoughAnimations.
- **JAR físico:** `notenoughanimations-neoforge-1.12.4-mc1.21.1.jar`.
- **Mod id:** `notenoughanimations`.
- **Runtime:** `1.12.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** tr7zw.
- **CurseForge project ID:** 433760.
- **Environment:** Client.
- **Papel:** ampliar animações visuais do player em terceira pessoa para refletir ações que o vanilla mostra de forma limitada ou apenas em primeira pessoa.

## 2. Boundary: visual, não gameplay
NotEnoughAnimations não deve ser tratado como provider de combate, stamina, movimento ou inventário. Ele altera **apresentação do player**.

Consequências:
- o servidor não depende das animações para validar ação;
- uma animação visível não prova que o server aceitou um uso/ataque;
- mods como Epic Fight continuam authority do combat state;
- First Person Model/Punchy e outras camadas visuais mantêm seus próprios domínios.

## 3. Relação com First-Person Mod
O autor descreve o projeto como expansão especialmente útil com **First-Person Mod**, mas ele funciona standalone.

O objetivo é tornar o modelo visível do jogador mais representativo, inclusive para outros jogadores em terceira pessoa. Isso pode melhorar coerência entre primeira/terceira pessoa sem transformar o mod em requisito server-side.

## 4. Eating e drinking
O mod adiciona representação de **comer/beber** em terceira pessoa. O estado funcional do item continua sendo definido pelo jogo/mod do alimento.

Riscos de apresentação:
- animação persistindo após cancelamento;
- item incorreto na mão;
- conflito com animação customizada de food/drink de outro mod;
- diferença entre mão principal/offhand.

## 5. Maps
A documentação destaca animação de mapas em terceira pessoa e observa que o conteúdo do mapa só pode ser apresentado se o **cliente já recebeu esses dados**.

Isso é uma boundary de informação importante: NotEnoughAnimations não concede conhecimento novo do mapa nem contorna sincronização do servidor; apenas representa visualmente dados já disponíveis ao cliente.

## 6. Shield e orientação corporal
O mod melhora postura/orientação do corpo ao **bloquear com shield**. Em stacks com combat animation mods, essa superfície pode sobrepor transforms do torso/braços.

A autoridade de bloquear dano permanece no sistema de combate/server. Aqui a preocupação é pose coerente.

## 7. Bow/crossbow e offhand
Existe comportamento para esconder/ajustar a visualização do **offhand** durante animações de duas mãos com bow/crossbow.

Isso evita clipping visual, mas pode conflitar com:
- dual-wield presentation;
- custom weapon renderers;
- Epic Fight battle animations;
- gun mods que reutilizam mão/offhand de forma própria.

## 8. Boats e horse reins
A documentação inclui:
- animação de **rowing** em boats;
- postura/representação de **horse reins**.

Essas features são locais ao render/animation state. Movimento real do veículo/mount continua server-authoritative.

## 9. Compass/clock e item-looking
O mod adiciona comportamento de olhar para compass/clock e permite configurar mais itens para esse tipo de apresentação.

Isso pode ser usado por outros locators visuais, mas não muda a lógica de alvo. Por exemplo, Nature's Compass continua authority da busca de biome; NEA apenas poderia participar da pose visual caso configurado/compatível.

## 10. Configuração individual
O projeto informa que as features podem ser **habilitadas/desabilitadas individualmente** via configuração in-game.

Isso é importante para resolver conflitos sem remover o mod inteiro. Quando uma animação conflitar:
1. identificar a ação concreta;
2. desativar apenas a feature correspondente;
3. testar novamente com a camada de animação dominante.

## 11. Bibliotecas embarcadas
A modlist física mostra:
- `TRansition-1.0.21-1.21.1-neoforge-SNAPSHOT.jar` — mod id `transition`, versão 1.0.21;
- `TRender-1.0.15-1.21.1-neoforge-SNAPSHOT.jar` — mod id `trender`, versão 1.0.15.

Ambos estão em `META-INF/jars` do host NotEnoughAnimations.

Regra de catálogo:
- não criar páginas top-level;
- não contar como dois mods extras;
- qualquer incompatibilidade de versão desses componentes é registrada sob NEA.

## 12. Release 1.12.4
O changelog 1.12.4 é principalmente de **targets futuros**: adiciona builds/targets para Fabric 26.2.x e NeoForge 26.1.x, com nota de que NeoForge 26.2 estava quebrado naquele contexto.

Essas notas não alteram o runtime 1.21.1 instalado. Portanto não são usadas para inventar novas features da build 1.21.1.

## 13. Compatibilidade no pack
Superfícies relevantes:
- Epic Fight — combat poses/armatures;
- First Person Model — primeira pessoa/modelo completo;
- Customizable Player Models — modelos customizados;
- player animation libraries/mods;
- Punchy — primeira pessoa;
- mounts/vehicles;
- weapon mods que controlam braços/offhand.

Nenhuma incompatibilidade geral foi confirmada. O tratamento correto é por ação/pose específica.

## 14. Client/server e multiplayer
O projeto é Client-only e declara compatibilidade com servidores vanilla/terceiros por ser visual.

Em multiplayer:
- cada cliente renderiza sua cópia/estado recebido;
- não confiar na pose como validação de gameplay;
- diferenças de configuração podem fazer dois jogadores verem animações diferentes sem alterar o estado real.

## 15. Riscos
1. **Pose conflict** com Epic Fight/CPM/outros player animation systems.
2. **Offhand hiding** incompatível com dual wield ou gun renderers.
3. **Mount animation** desalinhada com modelos/escala customizados.
4. **Item-use cancellation:** animação pode precisar encerrar exatamente com o uso real.
5. **Config layering:** múltiplos mods podem controlar a mesma pose.
6. **Embedded TRansition/TRender:** não atualizar/remover isoladamente.
7. **Visual desync perception:** pose diferente não significa desync real de server state.

## 16. Matriz de testes
- [ ] Cliente NeoForge 1.21.1 inicia com NEA 1.12.4 e stack atual de animação.
- [ ] Eating/drinking inicia e termina sem pose presa.
- [ ] Map pose mostra apenas conteúdo já disponível ao cliente.
- [ ] Shield blocking alinha braços/torso em terceira pessoa.
- [ ] Bow/crossbow esconde/posiciona offhand sem clipping indevido.
- [ ] Boat rowing acompanha movimento sem jitter.
- [ ] Horse reins não quebram postura com mounts/escala atuais.
- [ ] Compass/clock/item-looking respeita config.
- [ ] Epic Fight battle mode não produz armature/pose incompatível em ações principais.
- [ ] First Person Model/CPM/Punchy coexistem nas perspectivas correspondentes.
- [ ] Desabilitar uma feature na config resolve apenas aquela camada sem quebrar outras animações.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física: JAR/mod id/runtime e `notenoughanimations.mixins.json`.
- Embedded físico: TRansition 1.0.21 e TRender 1.0.15 sob `META-INF/jars`.
- CurseForge oficial: project 433760, file ID 8274908, Release NeoForge 1.21.1 de 18/06/2026, Environment Client.
- Documentação oficial: eating/drinking, maps, shield, offhand bow/crossbow, boats, horse reins, compass/clock e config individual.
- **Limite:** nenhuma animação é tratada como authority de gameplay; transforms/classes internas não foram inventados sem necessidade.
