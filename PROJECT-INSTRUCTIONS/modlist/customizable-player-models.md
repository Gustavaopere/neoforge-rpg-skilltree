# Customizable Player Models

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8117bd32d98703e8dc1f  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Customizable Player Models
- **Arquivo JAR:** `CustomPlayerModels-1.21-0.6.27a.jar`
- **Versão 1.21.1:** `0.6.27a`
- **Categoria:** Visual; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/custom-player-models/files/8375633
- **Função:** Framework/avatar system Client & Server que permite criar, carregar e sincronizar modelos, texturas e animações customizadas de jogador por meio de editor e runtime próprios.
- **Dependências:** Base CPM. CPM OSC Compat presente no pack depende desta base para disparar animações/valores por OSC/VMC. Outras integrações visuais podem interagir com o render/animation pipeline, mas não substituem o storage/runtime do CPM.
- **Compatibilidade/Riscos:** Interage fortemente com player rendering, animações, primeira pessoa, VR/ViveCraft e mods que substituem modelo/pose do jogador. Riscos: double-transform/double-animation, model sync divergente, asset/model inválido, lifecycle de troca de avatar e privacy/visibility em multiplayer. 0.6.27a corrige animação ao ficar em escada e crash com ViveCraft novo.
- **Sobreposição:** Pode sobrepor visualmente outros player-model/animation mods, mas não é automaticamente redundante. CPM owns seu avatar/model/animation state; integrações devem evitar aplicar duas transforms/poses ao mesmo bone/layer sem precedence.
- **Observações:** mod id `cpm`; runtime 0.6.27a. Changelog 0.6.27a: alphabetical sorting em file choosers, fix de standing-on-ladder animations (#985) e fix de crash com versões novas do ViveCraft (#992). Ports 26.x do mesmo changelog não são tratados como mudança funcional da build 1.21.1.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `CustomPlayerModels-1.21-0.6.27a.jar` / runtime 0.6.27a. Comportamento e regressões: CurseForge oficial Customizable Player Models 0.6.27a para NeoForge 1.21/1.21.1 e changelog oficial.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Customizable Player Models 0.6.27a foi reconfirmado na modlist física canônica de 595 top-levels e mantido como provider de avatar/model/animação. A presença visual no pack não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — avatar/model/animation authority, editor/assets, sync/lifecycle, render interoperability, OSC bridge boundary e regressões 0.6.27a catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🧍 Versão física confirmada: `CustomPlayerModels-1.21-0.6.27a.jar`, mod id `cpm`, runtime `0.6.27a`, NeoForge 1.21/1.21.1. CPM é o **provider de avatar/model/animação**; CPM OSC Compat é apenas uma bridge de input.

## 1. Papel e authority
Customizable Player Models fornece editor e runtime próprios para modelos, texturas e animações customizadas de jogador. O avatar CPM é um sistema visual/stateful próprio; não deve ser confundido com armor stats, Curios slots ou atributos RPG.

## 2. Editor
O editor permite construir/organizar a aparência e animações do avatar. Arquivos/modelos produzidos pelo editor precisam ser validados pelo runtime correspondente antes de uso em multiplayer.

A 0.6.27a adiciona ordenação alfabética aos file choosers, mudança puramente de UX do editor.

## 3. Models e bones
CPM pode substituir/estender o modelo padrão do jogador com partes customizadas. Transformações de bones são apresentação; não devem alterar hitbox, alcance ou colisão server-side sem outro provider explícito.

Mods externos que também alteram pose/model precisam definir precedence para não aplicar transforms duas vezes.

## 4. Texturas e assets
Texturas/assets pertencem ao avatar CPM e devem ser carregados/invalidated conforme lifecycle do modelo/resource system. Asset ausente ou corrompido deve falhar de modo visual/diagnosticável, não alterar gameplay state.

## 5. Animações
CPM executa animações definidas no modelo. Trigger, pose e value layers são authority do runtime CPM; bridges externas podem disparar valores, mas não devem duplicar a execução.

A 0.6.27a corrige **standing-on-ladder animations** (#985), tornando escadas um regression gate concreto.

## 6. CPM OSC Compat
O pack possui CPM OSC Compat 1.7.2. A separação é obrigatória:
- CPM: avatar, modelo, animações e value layers;
- CPM OSC Compat: recebe OSC/VMC por UDP e aciona parâmetros CPM.

Remover a bridge não remove o avatar system; remover CPM quebra a bridge.

## 7. ViveCraft
A 0.6.27a corrige crash com versões mais novas do **ViveCraft** (#992). Isso comprova interação real com VR/player-pose rendering.

VR pose/hand transforms precisam coexistir sem double-transform ou crash no client render lifecycle.

## 8. First-person e outros animation mods
Mods de primeira pessoa, player animation, Epic Fight ou camera/render podem tocar a mesma apresentação do jogador. Conflito deve ser provado no render final: limbs duplicados, pose sobreposta, clipping ou transform aplicada duas vezes.

Não desativar automaticamente uma stack inteira por mera semelhança funcional.

## 9. Multiplayer sync
CPM é Client & Server. Model/avatar state que precisa ser visto por outros jogadores deve sincronizar conforme o protocol do mod; cada cliente renderiza apresentação recebida.

Servidor não deve aceitar payload arbitrário como gameplay action. Avatar state permanece visual salvo contracts específicos.

## 10. Visibility e privacy
Em multiplayer, outros jogadores podem receber/visualizar avatar CPM conforme configurações/protocolo. Assets customizados devem ser tratados como conteúdo compartilhado do avatar, não como dado de gameplay.

Não assumir anonimização ou limitação de distribuição além do que o upstream documenta.

## 11. Lifecycle
Validar criação/carregamento de avatar, troca de modelo, world join, reconnect, dimension change, death/respawn, resource/model reload e client restart.

Referências a animações/bones de avatar anterior não podem sobreviver indevidamente à troca.

## 12. Version drift
Sintomas possíveis:
- model não carregar;
- animação ausente/quebrada;
- crash no renderer;
- incompatibilidade com bridge OSC;
- ViveCraft/first-person mod regressar;
- sync de avatar falhar entre clientes.

Atualização deve ser smoke-tested com CPM OSC Compat e demais render/animation mods do perfil.

## 13. Riscos
1. Double-transform de player model.
2. Pose conflitante com Epic Fight/VR/first-person.
3. Model/asset inválido causar crash client.
4. Animation trigger duplicado por duas bridges.
5. State de avatar stale após troca/reconnect.
6. Sync multiplayer divergente.
7. Escada regressar — fix #985.
8. ViveCraft regressar — fix #992.

## 14. Matriz de testes
1. Client + dedicated server com CPM.
2. Criar/carregar avatar simples no editor/runtime.
3. Trocar avatar e reconnect sem state stale.
4. Animação normal e standing-on-ladder — regression #985.
5. ViveCraft atual, se usado, sem crash — regression #992.
6. CPM OSC Compat acionando trigger/value exactly once.
7. First-person/player animation/Epic Fight coexistindo sem double pose.
8. Multiplayer: segundo cliente vê avatar/state correto.
9. Resource/model reload e client restart.
10. Modelo inválido em cópia de teste: erro controlado, sem corrupção de mundo.

## 15. Evidência
- modlist física atual: CPM 0.6.27a;
- CurseForge oficial: custom avatar editor, Client & Server, NeoForge 1.21/1.21.1;
- changelog 0.6.27a: file chooser sorting, ladder animation fix #985 e ViveCraft crash fix #992;
- CPM OSC Compat 1.7.2 catalogado separadamente como bridge de input.

> 🎭 Boundary canônico: **CPM controla o avatar e suas animações; outras bridges apenas fornecem input ou camadas de render**. Gameplay server-side permanece fora dessa authority visual.
