# Create: Extra Copycats — 1.0.2

> **Runtime físico confirmado:** `extra_copycats-1.0.2.jar` · mod id `extra_copycats` · versão `1.0.2` · NeoForge 1.21.1 · Client & Server. Base física: **Create 6.0.10** + **Create: Copycats+ 3.0.9+mc.1.21.1-neoforge**.

## 1. Papel no modpack
Extra Copycats é um addon pequeno do ecossistema Create/Copycats que adiciona novas formas de **copycat blocks**: blocos cuja geometria é própria, mas cuja aparência/material pode copiar outro bloco compatível.

## 2. Authority / ownership
- **Create/Copycats:** framework de copycat material/state e integração base.
- **Extra Copycats:** shapes/blocos adicionais e suas interações específicas.
- **Bloco copiado:** aparência/material source, não ownership da geometria Extra Copycats.

Não duplicar block state/material storage em integração externa.

## 3. Copycat Collapsible
O projeto documenta o **Copycat Collapsible**, uma forma ajustável que pode ser comprimida/expandida com Wrench para preencher painéis finos/gaps. A interação publicada inclui operações distintas de push/pull e rotação/mirror conforme click/modifier.

## 4. Copycat Cabinet Door
O projeto também documenta uma **Copycat Cabinet Door**, porta 1×1 fina com hinge que adota a aparência do material atribuído. Ela pertence ao addon; não confundir com doors normais cuja textura foi apenas trocada.

## 5. 1.0.2 — Copycat Collapsible Grid
A release **1.0.2** adiciona **Copycat Collapsible Grid**. Como o JAR físico é exatamente 1.0.2, essa forma faz parte da linha de conteúdo relevante desta instalação.

## 6. Relação com Copycats+ 3.0.9
Copycats+ é o provider amplo de shapes/framework no pack. Extra Copycats depende desse ecossistema e pode adicionar formas que eventualmente também apareçam upstream em versão futura.

Não classificar redundância apenas pelo nome: comparar registry IDs e comportamento real antes de remover.

## 7. Relação com Aero Copycats / Copycat Wing
O pack possui Aero Copycats e Create Aeronautics: Copycat Wing em outras posições. Esses addons tratam integração aeronautics/mass/wing behavior de copycats, não necessariamente os mesmos shapes de Extra Copycats.

Uma forma Extra Copycats usada em airship só ganha semantics aeronautics se o addon correspondente reconhecer o block/state.

## 8. Material copying
Copycat blocks precisam preservar o material copiado em placement, wrenching, save/reload, schematic/contraption e multiplayer. O material visual não deve substituir propriedades lógicas do shape sem contrato explícito.

## 9. Wrench interactions
Collapsible/door/grid usam interaction state próprio. Create Wrench é a superfície normal de edição. Inputs devem ser server-authoritative para que dois clients vejam mesma espessura/orientação/material.

## 10. Schematics / Create
O pack usa ferramentas de schematic e muitos addons Create. Copycat state precisa serializar corretamente em schematics/clipboard/cannon quando suportado. O Schematic Checker já possui lógica específica para copycat materials no pack, então estes blocos entram no regression matrix.

## 11. Client / Server
**Servidor:** block state, material assignment, orientation/shape parameters e interactions.

**Cliente:** baked model/render do material copiado.

Renderer não pode ser authority do material persistido.

## 12. Lifecycle
Validar placement, material apply/remove, wrench edits, break/re-place, chunk unload/reload, server restart, schematic save/place, contraption assembly quando permitido e update Create/Copycats+.

## 13. Multiplayer
Dois jogadores editando o mesmo copycat precisam convergir para um único state. Material/shape update deve sincronizar sem ghost model. Permissions/claims devem tratar a interação como block mutation normal.

## 14. Riscos
1. shape duplicado por nova versão do Copycats+;
2. registry/state format drift;
3. material visual não persistir;
4. wrench modifier conflitar com outro interaction mod;
5. schematic perder material ou shape parameter;
6. contraption render diferente do world block;
7. Aero Copycats não reconhecer novo shape;
8. culling/model bounds incorretos em espessuras extremas;
9. client/server desync em edição rápida;
10. update Create 6.x quebrar copycat API.

## 15. Matriz de testes
1. Collapsible com vários materiais.
2. Push/pull até limites de shape.
3. Rotate/mirror.
4. Cabinet Door open/close e hinge/orientation.
5. Collapsible Grid 1.0.2.
6. Break/re-place + chunk reload/restart.
7. Schematic save/place e Schematic Checker.
8. Contraption/airship com cada shape, quando aceito pelo stack.
9. Dois jogadores editando o mesmo bloco.
10. Shader/resource-pack rendering com materiais transparentes/emissive quando aplicável.

**Esta catalogação não afirma que esses testes foram executados.**

## 16. Evidências
- modlist física canônica: JAR/mod id/version/hash + Create 6.0.10/Copycats+ 3.0.9;
- página oficial Extra Copycats: Collapsible, Cabinet Door e interações;
- release 1.0.2: Copycat Collapsible Grid;
- arquivo oficial NeoForge 1.21.1 corresponde exatamente ao filename físico apesar de naming inconsistente em algumas listagens.

> **Boundary canônico:** Extra Copycats é authority dos **shapes adicionais e seu state**; Create/Copycats permanece o framework de material/copycat.