# Epic Fight - Better Lock On

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db8141b838ce93a7d54591  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Epic Fight - Better Lock On
- **Arquivo JAR:** `betterlockon-2.0.8-neoforge.jar`
- **Versão 1.21.1:** `2.0.8-neoforge`
- **Categoria:** RPG; QoL; Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/better-lock-on
- **Função:** Extensão client-side do lock-on do Epic Fight: indicador de alvo/vida, troca lateral de alvo, aquisição automática do alvo próximo e melhorias de câmera/FOV/transições.
- **Dependências:** Integração funcional com Epic Fight; pack usa Epic Fight 21.17.3.1. A release pública é 2.0.8, enquanto o metadata runtime físico declara 2.0.8-neoforge.
- **Compatibilidade/Riscos:** Conflitos possíveis com outros mods que alteram câmera, targeting, primeira pessoa, dodge/rotation ou HUD. Lock visual não pode se tornar authority de hit/dano. Validar mudanças de API com Epic Fight 21.17.3.1.
- **Sobreposição:** Complementa o lock-on do Epic Fight. Pode sobrepor HUD/câmera/target selection com mods de câmera ou combat QoL, mas não substitui o combat pipeline do Epic Fight.
- **Observações:** Runtime preservado como 2.0.8-neoforge. Recursos documentados na linha 2.x: lock icon/health, target switch por mouse, auto-unlock/death retarget, nearest-target acquisition, team filtering, camera transition e dynamic FOV.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `betterlockon-2.0.8-neoforge.jar`, mod id `betterlockon`, runtime `2.0.8-neoforge` e SHA-1 94926350c5500857097c993e64259a35c93c8eae. CurseForge/Modrinth oficiais sustentam a release pública 2.0.8; Epic Fight físico é 21.17.3.1.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, Better Lock On 2.0.8-neoforge foi revalidado contra o snapshot físico atual e Epic Fight 21.17.3.1; a presença continua documental e não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Better Lock On 2.0.8-neoforge físico/release confirmado; targeting/HUD/camera lifecycle e boundary de authority com Epic Fight 21.17.3.1 preservados. Runtime QA não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `betterlockon-2.0.8-neoforge.jar`, mod id `betterlockon`, runtime **`2.0.8-neoforge`**. O arquivo público é apresentado como 2.0.8, mas o catálogo preserva o metadata runtime completo. O pack usa Epic Fight `21.17.3.1`.

## 1. Papel e autoridade
Better Lock On é uma extensão **client-side de targeting/câmera/HUD** para o lock-on do Epic Fight. Ele melhora seleção e acompanhamento visual do alvo, mas **Epic Fight continua authority** de combat state, ataque, hit detection, animações e damage settlement.

Lock visual não autoriza dano, alcance ou hostilidade por si só.

## 2. Indicador de alvo e vida
O projeto documenta um ícone de lock e exibição de vida do inimigo. A apresentação pode ser configurável em aspectos como cor, transparência e tamanho.

Regras:
- HP exibido deve refletir state sincronizado, não virar fonte de verdade;
- ausência do ícone não significa perda de entidade no servidor;
- HUD não deve executar callbacks de dano.

## 3. Aquisição de alvo
Quando não há alvo travado, o botão de lock pode selecionar automaticamente o alvo válido mais próximo em vez de exigir que a mira esteja exatamente sobre a entidade. Isso altera **seleção de target no cliente**, não a lista de entidades que o servidor considera válidas para combate.

Filtros de elegibilidade precisam respeitar regras do mod e não promover entidades aliadas/inválidas a alvos só por proximidade.

## 4. Troca lateral de alvo
Com lock ativo, a linha 2.x permite alternar entre alvos usando movimentos/botões de mouse documentados pelo projeto. Isso exige ordenação determinística dos candidatos em torno da câmera.

Riscos: trocar rapidamente de alvo durante ataque/dodge pode produzir desync visual se um callback de câmera for confundido com combat state.

## 5. Morte e retarget
O projeto documenta unlock/retarget automático quando o alvo morre. State seguro:
- alvo morto/inválido é removido do lock;
- novo alvo, quando escolhido, é apenas referência visual até o combat provider aceitar ações subsequentes;
- nenhum evento de morte deve ser processado duas vezes pela troca de lock.

## 6. Team filtering
A linha 2.x inclui exclusão de entidades do mesmo time entre os candidatos ao lock. Essa regra deve ser tratada como filtro de targeting visual; friendly-fire real continua sendo decidido pelo combat/provider/server rules.

## 7. Câmera, transições e FOV
Changelogs 2.x documentam melhorias de:
- transição dinâmica da câmera;
- dynamic FOV;
- comportamento com inimigos grandes;
- primeira pessoa/sprint em cenários específicos;
- rotação/direção durante dodge e lock.

Essas funções são altamente suscetíveis a sobreposição com outros mods de câmera e first-person rendering.

## 8. Relação com Epic Fight
Epic Fight 21.17.3.1 permanece authority de:
- combat mode;
- attack animation;
- hitboxes/hit detection;
- target combat capability quando aplicável;
- dodge/combat movement;
- damage settlement.

Better Lock On deve observar e apresentar esse state, não executar ataque vanilla em paralelo.

## 9. Client/server
O projeto é essencialmente client-side. Consequências:
- HUD/câmera/seleção visual ficam no cliente;
- servidor não pode confiar no target client-side como prova suficiente de alcance ou hit;
- mods próprios não devem usar `betterlockon` como dependência server-side sem API comprovada;
- dedicated server não deve carregar classes exclusivamente de câmera/render.

## 10. Lifecycle do lock
Validar transições:
- entrar/sair de combat mode;
- target spawn/despawn;
- target death;
- target sair de render range;
- dimension change;
- player death/respawn;
- logout/reconnect;
- troca rápida entre mobs;
- first-person ↔ third-person;
- abrir menus/pausar quando aplicável.

Referência de target inválida deve ser descartada, não mantida por entity id stale.

## 11. Multiplayer
Em multiplayer, outro jogador/mob pode mudar posição, time, vida ou existência entre frames. O lock precisa tolerar state stale e se corrigir a partir da sincronização real. Não deve permitir client spoofing de dano só porque uma entidade permanece marcada visualmente.

## 12. Riscos
1. Camera mod e Better Lock On escrevendo yaw/pitch no mesmo frame.
2. Target selection discordar do target combatível no servidor.
3. Lock preso em entidade morta/descarregada.
4. Dynamic FOV conflitante com sprint/zoom/shader/camera mods.
5. Dodge direction/rotation sendo aplicado duas vezes.
6. HUD de vida duplicado com outros target frames.
7. Version drift com Epic Fight 21.17.3.1.

## 13. Matriz de testes
1. Lock em mob pequeno, grande e boss.
2. Troca de alvo esquerda/direita com vários candidatos.
3. Morte do alvo e retarget automático.
4. Mesmo time/aliado não selecionado conforme regra documentada.
5. First-person/third-person, sprint e dodge.
6. Multiplayer com latência e alvo saindo de range/chunk.
7. Death/respawn/dimension change do jogador.
8. Coexistência com mods atuais de câmera/first-person/HUD.
9. Confirmar que hit/dano continuam exactly-once no Epic Fight.

## 14. Evidência
- modlist física atual: Better Lock On 2.0.8-neoforge + Epic Fight 21.17.3.1;
- CurseForge oficial da release 2.0.8 NeoForge 1.21.1;
- documentação/changelogs 2.x sobre lock icon, health, target switching, nearest acquisition, death retarget, team filtering, camera transition e dynamic FOV.

> 🎯 Ownership canônico: Better Lock On = target presentation/selection/camera no cliente; Epic Fight = combate real. Uma entidade travada visualmente não equivale a hit autorizado.