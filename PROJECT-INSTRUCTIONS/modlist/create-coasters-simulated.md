# Create: Coasters Simulated

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db8128a6e2e81099a28a71
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create: Coasters Simulated
- **Arquivo JAR:** `simulatedcoasters-0.1.5.jar`
- **Versão 1.21.1:** 0.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia
- **Função:** Addon Create/Sable para roller coasters físicas: tracks/anchorpoints, loops/inversions/jumps, chain lifts, carts/linking, rivets e balloons em sublevels físicos.
- **Dependências:** Obrigatórias upstream e satisfeitas: Create 6.0.10+ e Sable 2.0.5. Create Aeronautics 1.3.2 está fisicamente presente e continua optional dependency upstream, não requisito de boot.
- **Sobreposição:**
- **Compatibilidade/Riscos:** Riscos principais permanecem: sublevel disappearance/duplication, cart-link snapping, track preview lag, chain-lift rotational conflict e chunk/sublevel desync. 0.1.5 corrige glue entre riveted sublevels, self-glue, balloons e preview inválido, além de FPS/cart-link/snap improvements.
- **Observações:** mod id `simulatedcoasters`; runtime 0.1.5. Dossiê anterior já estava no padrão técnico; nesta rodada foi revalidado sem reescrita destrutiva. Planned features upstream continuam fora do escopo factual.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais Create: Coasters Simulated 0.1.5; dossiê técnico original preservado e revalidado.
- **Fonte:** https://modrinth.com/mod/create-coasters-simulated ; https://modrinth.com/mod/create-coasters-simulated/version/PGhQoSBq ; https://www.curseforge.com/minecraft/mc-mods/create-coasters-simulated/files/8655742
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Coasters Simulated 0.1.5 preservado: tracks/physics, carts/linking, rivets/sublevels, balloons, chain lifts, Create/Sable/Aeronautics boundaries, lifecycle, riscos e testes continuam coerentes com o runtime físico atual.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `simulatedcoasters-0.1.5.jar`, mod id `simulatedcoasters`, versão `0.1.5`. Create: Coasters Simulated adiciona **montanhas-russas físicas sobre Create + Sable**, com tracks, carts, linking, chain lifts, rivets e balloons; Create Aeronautics é opcional.

## 1. Identidade, versão e papel
- **Mod:** Create: Coasters Simulated.
- **JAR físico:** `simulatedcoasters-0.1.5.jar`.
- **Mod id:** `simulatedcoasters`.
- **Versão instalada:** `0.1.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** adicionar roller coasters com física, curvas livres, loops/inversions, chain lifts, jumps e carts físicos ao ecossistema Create.

## 2. Dependências e stack físico
Dependências publicadas para a linha inicial/current:
- **Create 6.0.10+ para 1.21.1** — obrigatório.
- **Sable** — obrigatório; biblioteca/sistema físico usado pelo mod.
- **Create Aeronautics** — opcional, não exigido para o mod iniciar.

No pack atual:
- Create `6.0.10` está presente;
- Sable `2.0.5` está presente;
- Create Aeronautics `1.3.2` está presente.

Portanto o stack físico satisfaz os requisitos e também ativa um cenário de compatibilidade opcional relevante.

## 3. Authority e ownership
- **Coasters Simulated:** tracks, carts, links, rivets, balloons e regras próprias da coaster.
- **Sable:** física/sublevels usados pelas estruturas móveis.
- **Create:** rotação, mecânicas de Create e integração de chain lift/input quando aplicável.
- **Aeronautics:** mecanismos aéreos/sublevel ecosystem, mas não dependency obrigatória da coaster.

A ficha não atribui ao addon authority geral sobre contraptions Create ou physics Sable fora de seus objetos.

## 4. Conteúdo funcional confirmado
A documentação oficial lista como features existentes:
- **Roller Coaster Tracks / Track Anchorpoint Blocks**;
- suporte a **loops, inversions, chain lifts e jumps**;
- **dyable tracks**;
- **carts** e **cart linking**;
- **rivets** que mantêm sublevels unidos;
- **balloons** como utilitário para mover assembled contraptions.

O projeto descreve a física como gravity-driven: chain lifts elevam os carts e a gravidade conduz o restante do percurso.

## 5. Track geometry e placement
Os tracks permitem curvas e geometrias próprias de coaster, incluindo loops/inversions. Changelogs anteriores e 0.1.5 mostram que o placement preview e o snapping são partes sensíveis do sistema:
- 0.1.5 ampliou o ângulo em que carts fazem snap para tracks;
- preview de placement inválido agora abandona segmentos absurdamente longos para evitar lag intenso;
- versões anteriores trabalharam smoothing/contexto de curvas, inclusive círculos/90° turns.

Esses comportamentos devem ser tratados como UX/geometry do addon, não como rail vanilla/Create train track equivalence.

## 6. Carts e linking
Carts são objetos físicos próprios e podem ser linked. Na 0.1.5:
- cart links ficaram ligeiramente mais resistentes a snap/break;
- outline visual dos cart blocks foi melhorado;
- angle de snap para track foi ampliado.

O link deve sobreviver ao percurso normal sem converter dois carts em state duplicado ou perder um membro do conjunto após chunk transition.

## 7. Rivets e sublevels
Rivets mantêm sublevels/partes físicas unidos. A 0.1.5 corrige dois casos críticos:
- tentar colar **dois sublevels rivetados separados** não deve mais fazer o sublevel desaparecer;
- um sublevel rivetado não pode ser colado a si mesmo.

A 0.1.4 também corrigiu crash ao destruir rivet que segurava apenas weightless blocks. Esses itens mostram que lifecycle de assembly/desassembly é área de alto risco.

## 8. Balloons
Balloons são utilitários para movimentar assembled contraptions/sublevels. A 0.1.5 corrige caso em que balloons caíam no chão em vez de **pop** corretamente.

Versões anteriores também ajustaram stack size, tether point e remoção com wrench. Não extrapolar estes objetos como sistema geral de buoyancy do Sable; o escopo documentado é utilitário do addon.

## 9. Chain lifts e Create rotational input
Chain lifts fornecem ganho de energia/altura aos carts, após o qual a gravidade assume. O changelog 0.1.4 cita comportamento de mismatched rotation: o input vizinho mais fraco é destruído em vez do anchorpoint. Isso demonstra integração com torque/rotação Create e requer regressão com redes rotacionais complexas.

## 10. Client / server e multiplayer
O projeto é Client & Server porque:
- geometria, carts, links e sublevel state precisam de autoridade compartilhada/servidor;
- o cliente renderiza tracks, outlines e movimento;
- multiplayer deve manter posição, velocidade, linking e assembly consistentes para todos os observadores.

Não usar posição visual client-only como única prova de state físico correto.

## 11. Lifecycle
Validar:
- placement/edit de tracks;
- construção de loops/inversions/jumps;
- spawn/placement de carts;
- snap do cart ao track;
- link/unlink de carts;
- chain lift start/stop e input rotacional conflitante;
- rivet attach/detach;
- glue entre sublevels válidos e inválidos;
- balloons attach/remove/pop;
- chunk unload/reload;
- save/restart;
- dimension/relog;
- colisão/descarrilamento/jump e recuperação.

## 12. Integrações concretas no pack
- **Create 6.0.10:** requisito obrigatório e provider de rotação/mecânicas Create.
- **Sable 2.0.5:** requisito físico obrigatório.
- **Create Aeronautics 1.3.2:** opcional upstream, mas presente; testar sublevel coexistence, rivets/glue e transporte.
- **ServerCore:** pode alterar entity/chunk processing; carts físicos precisam continuar tickando corretamente.
- **Iris / performance mods:** 0.1.5 possui melhorias críticas de FPS e placement preview; testar rendering/performance em layouts grandes.

## 13. Riscos técnicos
1. **Early-release complexity:** apesar de o autor considerar o mod relativamente estável, a página mantém aviso de bugs/performance esperáveis nas primeiras semanas.
2. **Sublevel disappearance/duplication:** área historicamente corrigida em 0.1.5.
3. **Cart link snapping:** física extrema/jumps podem romper links ou desyncar composição.
4. **Track preview lag:** segmentos inválidos enormes já causaram custo intenso; 0.1.5 adiciona bailout.
5. **Rotational conflict:** chain lift sob inputs incompatíveis pode destruir componente e precisa de teste determinístico.
6. **Chunk boundaries:** carts rápidos/linkados e sublevels precisam sobreviver a unload/load.
7. **Aeronautics interaction:** dois sistemas que operam sublevels/physics podem criar ownership ambiguities se glue/rivet for usado fora do fluxo esperado.

## 14. Matriz de testes
- [ ] Dedicated server boot com Create 6.0.10 + Sable 2.0.5 + Coasters 0.1.5.
- [ ] Aeronautics removido em ambiente de teste: Coasters continua iniciando, confirmando optional dependency.
- [ ] Track reto, curva, loop, inversion e jump.
- [ ] Chain lift com rotação correta e rotação conflitante.
- [ ] Cart snap em diferentes ângulos.
- [ ] Dois+ carts linked em loop/jump sem snap indevido.
- [ ] Rivet attach/destroy, inclusive weightless blocks.
- [ ] Glue entre dois riveted sublevels sem desaparecimento.
- [ ] Tentativa de self-glue é rejeitada sem corrupção.
- [ ] Balloons attach/remove/pop corretamente.
- [ ] Invalid absurdly-long track preview não gera lag severo.
- [ ] Chunk unload/reload e server restart preservam track/cart/link/sublevel state.
- [ ] Multiplayer com dois observadores sem position/link desync.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 15. Evidências
- Modlist física canônica de 08/09/2026: JAR/mod id/versões do stack.
- Modrinth oficial: requisitos Create 6.0.10+, Sable obrigatório e Aeronautics opcional; feature list.
- CurseForge/Modrinth release 0.1.5: FPS, cart outlines/links/snap angle, riveted sublevels, balloons e placement-preview fixes.

## 16. Revalidação física — 11/09/2026
A modlist física atual continua contendo exatamente `simulatedcoasters-0.1.5.jar`, com mod id `simulatedcoasters` e runtime `0.1.5`. O stack obrigatório permanece Create `6.0.10` + Sable `2.0.5`; Create Aeronautics `1.3.2` continua presente como integração opcional.

Nenhuma evidência desta revalidação exige reescrever o dossiê anterior: as boundaries de authority, os fixes 0.1.5 e a matriz de testes permanecem válidos. Os testes continuam **não executados** nesta auditoria documental.
