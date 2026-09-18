# Aeronautics Player Tilt

> **Reauditoria física — 18/09/2026.** Versão catalogada atual: `0.2.0`. O conteúdo abaixo preserva a página Notion reconciliada; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Aeronautics Player Tilt
- **Arquivo JAR:** aero_player_tilt-0.2.0.jar
- **Versão 1.21.1:** 0.2.0
- **Categoria:** Compat; Visual
- **Função:** Inclina corpo/hitbox do jogador ao frame Sable/Aeronautics, corrige gravity frame em decks inclinados e, na 0.2.0, adiciona Magnetic Boots beta, Sticky Tilt e opção de manter câmera nivelada enquanto o corpo inclina.
- **Dependências:** Required: Sable e Aeronautics Camera Sync 1.4.0+. No pack físico atual: Sable 2.0.5 e Camera Sync 1.4.0. Create Aeronautics é o alvo funcional, não uma terceira hard dependency publicada.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Toca player model, hitbox, collision e gravity frame; 0.2.0 acrescenta wall/ceiling gravity via Magnetic Boots e sticky tilt. Alto risco de composição com Epic Fight, Pehkui, ragdoll/player animation, anticheat/collision e mods que assumem AABB/gravity vanilla. Server config precisa permanecer authority das features experimentais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/aeronautics-player-tilt
- **Procedência:** modlist física atual + CurseForge oficial Aeronautics Player Tilt 0.2.0, file ID 8882320, 14/09/2026; dependências oficiais Sable + Aeronautics Camera Sync 1.4.0+.
- **Observações:** Runtime físico 0.2.0, release NeoForge 1.21.1 de 14/09/2026. Adiciona `allowMagneticBoots` e `allowStickyTilt` server-side, Rotate Camera toggle, infinity stop para Max Tilt Threshold e ajustes de UI/config.
- **Atualização/Status:** READITADO EM 18/09/2026 — runtime físico atualizado de 0.1.3 para 0.2.0; magnetic boots, sticky tilt, camera rotation toggle e server gates incorporados.
- **Decisão:** Manter
- **Histórico da decisão:** 
- **Sobreposição:** Complementa, não substitui, Aeronautics Camera Sync. Camera Sync fornece orientação de câmera/aim; Player Tilt aplica orientação ao corpo/hitbox/gravidade. Pehkui toca escala da hitbox e Epic Fight/ragdolls tocam pose/modelo, criando superfícies compartilhadas sem prova de incompatibilidade dura.
- **Data da última decisão:** 2026-09-06

## Dossiê técnico de compatibilidade
### 1. Identidade auditada
- **Tipo:** compat física/visual para Sable/Aeronautics.
- **JAR físico:** `aero_player_tilt-0.2.0.jar`.
- **Runtime:** `0.2.0`.
- **Ambiente:** cliente e servidor.
- **Maturidade:** projeto recente; recursos para mobs/dropped items são descritos como experimentais.
### 2. Descrição técnica detalhada
Aeronautics Player Tilt aplica ao corpo do jogador e à sua hitbox a orientação local do deck em que ele está. Sem esse tipo de correção, a câmera pode acompanhar uma contraption inclinada enquanto o corpo/colisão continua vertical em coordenadas globais, produzindo incoerências visuais e físicas.
O mod também altera o tratamento da gravidade relativa ao deck para que um salto sobre uma plataforma inclinada retorne ao ponto esperado do frame local, em vez de derivar como aconteceria sob a gravidade global vanilla. A documentação ainda descreve suporte experimental para inclinar mobs e dropped items por configuração de mundo.
### 3. Superfícies tocadas
- player model/pose;
- hitbox/collision;
- frame de gravidade do jogador sobre sublevel;
- jumping sobre deck inclinado;
- orientação de entidades experimentais;
- integração direta com Camera Sync;
- estado cliente↔servidor de corpo/collision.
### 4. Dependências
**Required na linha atual 0.2.0**
- Sable.
- Aeronautics Camera Sync `1.4.0+`.
**Alvo funcional**
- Create Aeronautics é o ecossistema em que o recurso é usado, mas não deve ser registrado como terceira hard dependency direta da release sem metadata que o declare. O stack chega a Aeronautics por Sable/Camera Sync e pela própria utilização em contraptions.
### 5. Incompatibilidades declaradas
Nenhuma incompatibilidade formal específica foi publicada nas fontes upstream consultadas.
### 6. Sobreposição e riscos no pack
**Aeronautics Camera Sync:** dependência e complemento deliberado. Camera Sync transforma câmera/aim; Player Tilt transforma corpo/hitbox/gravity. Não são alternativas.
**Pehkui:** altera dimensões/escala da entidade. Player Tilt altera orientação da hitbox. A combinação é tecnicamente sensível porque ambos afetam collision geometry; testar escala diferente de 1.0 em decks rotacionados.
**Epic Fight:** modifica pose, animação e lógica corporal de combate. Verificar Battle Mode em decks inclinados, ataques, dodge e hit detection.
**Ragdoll/player animation:** podem substituir ou interpolar transforms do modelo. O risco mais provável é desacordo visual entre modelo e hitbox, especialmente em quedas, morte, revive ou animações especiais.
**Passagens estreitas:** o próprio propósito do tilted hitbox permite atravessar geometria que um AABB vertical não atravessaria. Isso deve ser validado contra collision server-side para evitar desync ou clipping abusável.
### 7. Matriz mínima de validação
1. ficar parado em deck inclinado;
2. andar por gap inclinado de 1 bloco;
3. saltar durante pitch/roll;
4. testar rotação rápida e mudança de deck;
5. Epic Fight Battle Mode/dodge/attack;
6. Pehkui com escala \<1 e \>1;
7. ragdoll/morte/revive sobre contraption;
8. mobs/dropped items experimentais ligados/desligados;
9. multiplayer com cliente+servidor;
10. relog/dimension change mantendo estado limpo.
### 8. O que não faz
- não substitui Camera Sync;
- não controla sustentação/forças da aeronave;
- não é um mod genérico de gravidade para todo o mundo;
- não prova compatibilidade automática com mods que alteram hitbox/pose;
- Create Aeronautics não é registrado aqui como hard dependency direta sem relation específica.
### 9. Evidência
- **Modlist física:** 0.2.0 instalado.
- **Release atual:** 0.2.0 para NeoForge 1.21.1 em 14/09/2026; dependências publicadas permanecem Sable + Camera Sync 1.4.0+.
- **Upstream project description:** body/hitbox tilt, gravity correction e mobs/items experimentais.
- **Análise do pack:** Epic Fight/Pehkui/ragdoll classificados como superfícies de integração a testar, não incompatibilidades formais.
### 10. Atualização instalada — 0.2.0
A release 0.2.0 adiciona novas superfícies de movimento/configuração:
- **Magnetic Boots \[Beta\]:** qualquer face pode se tornar floor, incluindo paredes e teto; novo jump no ar solta o jogador;
- **Sticky Tilt:** evita deslizar de um deck inclinado;
- **Rotate Camera:** permite manter a câmera nivelada enquanto corpo/hitbox continuam inclinando;
- server config `allowMagneticBoots` e `allowStickyTilt` pode desativar as features globalmente.
Mudanças de configuração/UI:
- Max Tilt Threshold ganha stop em infinity;
- settings sob switch desligado ficam ocultos;
- settings bloqueados pelo servidor ficam travados no menu;
- aba `World rules` passa a `Experimental`;
- comentários do config foram reescritos.
Regression gates adicionais:
- [ ] magnetic boots em floor/wall/ceiling respeitam collision server-side;
- [ ] detach por jump não mantém gravity frame stale;
- [ ] sticky tilt não impede movimento legítimo nem cria desync;
- [ ] `allowMagneticBoots`/`allowStickyTilt` do servidor prevalecem sobre cliente;
- [ ] Rotate Camera altera apenas presentation/camera, sem separar aim/hitbox authority indevidamente.
