# Aeronautics Player Tilt

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db819f8258dad94c9de2ba
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Aeronautics Player Tilt
- **Arquivo JAR:** `aero_player_tilt-0.1.3.jar`
- **Versão 1.21.1:** `0.1.3`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê de body/hitbox/gravity frame, Camera Sync dependency, Epic Fight/Pehkui/ragdoll risks e multiplayer lifecycle confirmado no QC global #11.
- **Categoria:** Compat; Visual
- **Compatibilidade/Riscos:** Projeto muito recente e parcialmente experimental. Toca player model, hitbox, collision e gravity frame; alto risco de composição com Epic Fight, Pehkui, ragdoll/player animation e mods que pressupõem AABB vertical vanilla. Nenhuma incompatibilidade formal específica foi publicada. Testar server authority e anticheat/collision logic após updates.
- **Decisão:** Manter
- **Dependências:** Required para a release 0.1.3: Sable e Aeronautics Camera Sync 1.4.0+. Create Aeronautics é o alvo funcional principal, mas não aparece como uma das duas dependências diretas da release; chega pelo stack de Camera Sync/Aeronautics. No pack, Sable 2.0.5 e Camera Sync 1.4.0 estão presentes.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/aeronautics-player-tilt
- **Função:** Inclina corpo e hitbox do jogador para o frame do deck Sable/Aeronautics e corrige a direção efetiva da gravidade em saltos sobre decks inclinados; possui opção experimental para mobs e dropped items acompanharem o deck.
- **Histórico da decisão:** vazio
- **Observações:** Dossiê aprofundado concluído em 07/09/2026. JAR físico e índice atualizado confirmam 0.1.3 (06/09/2026), mais novo que o snapshot CurseForge inicialmente indexado em 0.1.1. Dependências diretas corrigidas para Sable + Camera Sync 1.4.0+.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Aeronautics Player Tilt 0.1.3 + dossiê técnico existente.
- **Sobreposição:** Complementa, não substitui, Aeronautics Camera Sync. Camera Sync fornece orientação de câmera/aim; Player Tilt aplica orientação ao corpo/hitbox/gravidade. Pehkui toca escala da hitbox e Epic Fight/ragdolls tocam pose/modelo, criando superfícies compartilhadas sem prova de incompatibilidade dura.
- **Data da última decisão:** 2026-09-06

## Dossiê técnico de compatibilidade

### 1. Identidade auditada
- **Tipo:** compat física/visual para Sable/Aeronautics.
- **JAR físico:** `aero_player_tilt-0.1.3.jar`.
- **Runtime:** `0.1.3`.
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
**Required para a release 0.1.3**
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
6. Pehkui com escala <1 e >1;
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
- **Modlist física:** 0.1.3 instalado.
- **Índice de release atualizado:** 0.1.3 para NeoForge 1.21.1 em 06/09/2026, com duas dependências required: Sable + Camera Sync.
- **Upstream project description:** body/hitbox tilt, gravity correction e mobs/items experimentais.
- **Análise do pack:** Epic Fight/Pehkui/ragdoll classificados como superfícies de integração a testar, não incompatibilidades formais.
