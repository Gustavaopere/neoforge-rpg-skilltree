# Ars Nouveau: Two-Way Portals

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812c98a9f6a1d1fe8153
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Nouveau: Two-Way Portals
- **Arquivo JAR:** `ars_two_way_portals-2.0.0.jar`
- **Versão 1.21.1:** 2.0.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Compat
- **Função:** Estende portais/warp do Ars Nouveau com pares bidirecionais permanentes, identidade de endpoints, frame tracking, cooldown e bridge opcional com Immersive Portals.
- **Dependências:** Ars Nouveau 5.13.1. Immersive Portals 6.0.7 está fisicamente presente no snapshot atual, mas a integração continua provider-gated; ausência/incompatibilidade do provider deve falhar sem fallback fictício.
- **Sobreposição:** Teleporte bidirecional específico do Ars; não equivale a portais gerais do pack.
- **Compatibilidade/Riscos:** Risco de double-teleport, stale pair após unload/restart, conflitos em PortalBlock/warp hooks e classloading opcional de Immersive Portals. Portal Nullify deve remover só um endpoint.
- **Observações:** mod id `ars_two_way_portals`, runtime 2.0.0. Immersive Portals está presente fisicamente, mas presença não equivale a runtime QA aprovado.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source/release oficial Ars Nouveau: Two-Way Portals 2.0.0 + provider físico Immersive Portals 6.0.7 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_two_way_portals-2.0.0.jar` / `2.0.0`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-nouveau-two-way-portals
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #51: `ars_two_way_portals-2.0.0.jar` / `2.0.0` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria técnica confirmou o JAR 2.0.0, o escopo de portais bidirecionais, o pair lifecycle e os riscos de integração. A presença física no pack não foi convertida automaticamente em decisão de manter, remover ou tornar opcional.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `ars_two_way_portals-2.0.0.jar`, mod id `ars_two_way_portals`, versão `2.0.0`. A release 2.0.0 e o source público confirmam a implementação específica de portais pareados e a integração opcional com Immersive Portals.

## 1. Papel e autoridade
Addon especializado em estender os portais/warp do Ars Nouveau para **pares permanentes bidirecionais** sem substituir os portais vanilla do Ars. Ars Nouveau permanece provider dos conceitos de warp/portal e itens base; este addon é autoridade apenas da **identidade do par, endpoints, proteção de frame, cooldown e regras de pair lifecycle** que adiciona.

## 2. Itens registrados — 2
- `double_sided_stable_warp_scroll`
- `portal_nullify_scroll`

Ambos usam stack size 1 no source auditado.

### Double-Sided Stable Warp Scroll
É um item próprio; o Stable Warp Scroll normal do Ars não é substituído. O scroll pode registrar destino e criar pares ligados em frames compatíveis. Também conserva usos para portais temporários/one-way quando o fluxo escolhido assim determina.

### Portal Nullify Scroll
Remove **somente o endpoint atingido** de um par e deixa o endpoint remanescente funcionando como portal one-way. A ficha não trata isso como destruição global do par.

## 3. Componentes/sistemas confirmados no source
- `PortalPairService`: ownership do vínculo entre endpoints.
- `PortalCooldown`: controle anti-reentrada.
- `PortalFrameHooks`: tracking/proteção de frame.
- `PortalNullifier`: lógica de nulificação de endpoint.
- `RegularPortalIntegration`: fluxo Ars regular.
- `ImmersivePortalIntegration`: bridge opcional.
- `PortalConfig`: flags/configuração do comportamento.
- mixins/hook points em `PortalBlock`, `BlockUtil`, `EffectBreak`, `EffectLaunch` e `EffectLeap` para preservar regras de frame e interação de spells.

## 4. Fluxo de portal permanente
1. O scroll bidirecional registra/transporta o destino.
2. Um frame compatível recebe a criação do endpoint.
3. O serviço cria uma identidade compartilhada do par e mantém as duas extremidades associadas.
4. Teleporte regular respeita proteção e cooldown antes de mover a entidade.
5. Destruição normal de um frame/endpoint de par pode remover o par conforme a regra de pair lifecycle; **Portal Nullify Scroll é a exceção intencional**, deixando o outro lado one-way.

Não criar bridge paralela que também teleporte a entidade após o addon já aceitar o portal.

## 5. Frames, rotação e ferramentas
- Frames verticais e horizontais são suportados no fluxo documentado.
- Dominion Wand pode rotacionar pares ligados quando `canRotatePortal` permite.
- Há dois modos de interação documentados: rotação normal e rotação com transformação de gravity habilitada para faces vinculadas.
- Leap em borda rastreada é tratado especialmente: o addon pode remover/substituir bloco de frame mantendo o tracking correto em vez de destruir imediatamente o portal.
- Break por Ars/Touch/Projectile e mineração com Silk Touch possuem hooks para preservar semântica/drop do frame protegido.
- Launch é bloqueado para blocos de frame rastreados quando mover o bloco quebraria a identidade do par.

## 6. Cooldown, causalidade e anti-duplicação
Portais regulares pareados mantêm cooldown de reentrada documentado de **3 segundos**. O objetivo é impedir ping-pong imediato e múltiplas células de portal dispararem warp repetido da mesma entidade. O sistema rejeita entidades protegidas antes do warp quando necessário.

**Regra do pack:** um teleporte aceito pelo `PortalPairService` deve gerar uma única mudança de posição/dimensão. Não reaplicar teleporte em listeners globais.

## 7. Integração opcional com Immersive Portals
Quando o provider Immersive Portals está disponível e a integração está habilitada, o addon pode criar um par do provider Immersive; o gesto alternativo documentado permite escolher o portal Ars regular. O fluxo Immersive não deve herdar automaticamente o cooldown do par Ars se o provider utiliza lifecycle próprio.

Fail-closed: ausência/incompatibilidade do provider opcional não deve impedir a funcionalidade regular do Ars nem carregar classes opcionais prematuramente.

## 8. Persistência e lifecycle
Estados que precisam sobreviver corretamente:
- identidade do par;
- endpoint A/B e dimensão;
- frame rastreado;
- estado de nulificação/one-way;
- cooldown por entidade quando aplicável.

Testar save/reload, chunk unload, endpoint descarregado, restart de dedicated server, portal cross-dimension, quebra de frame, rotação, nulificação e reconstrução. Dados órfãos devem falhar fechados, não teleportar para coordenada obsoleta.

## 9. Client/server e multiplayer
- Pair creation, frame mutation, cooldown e teleport são server-authoritative.
- Render/efeitos visuais são client-side e não podem determinar endpoint válido.
- Dois jogadores usando/alterando o mesmo par simultaneamente exigem operação atômica ou validação final do estado do par.
- Fake players/automação não devem contornar proteção de frame sem evidência explícita de suporte.

## 10. Riscos no modpack
1. Conflito com qualquer outro addon que reescreva `PortalBlock`/warp do Ars.
2. Duplo teleporte quando outro sistema observa mudança de dimensão/posição e tenta repetir a ação.
3. Stale pair após chunk unload/restart.
4. Classe opcional de Immersive Portals carregada sem provider.
5. Nulificação transformada indevidamente em deleção dos dois endpoints.
6. Interações Break/Leap/Launch com frames produzirem drop/movimento duplicado.

## 11. Evidência
- Modlist física atual: `ars_two_way_portals-2.0.0.jar`.
- Release oficial 2.0.0.
- Source `Astrologic-Git/ars-nouveau-two-way-portals`: `ModItems`, `PortalPairService`, `PortalCooldown`, `PortalFrameHooks`, `PortalNullifier`, integrações regular/Immersive e mixins auditados.

> 🧭 Ownership canônico: Ars Nouveau = primitives de portal/warp; Two-Way Portals = par bidirecional e seu lifecycle; Immersive Portals = engine espacial apenas quando a bridge opcional está ativa.
