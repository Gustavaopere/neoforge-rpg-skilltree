# Epic Fight x Curios Compat — runtime 1.4 / artifact 2.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81d5b310cef1a4264243  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Epic Fight x Curios Compat
- **Arquivo JAR:** `Epic Fight x Curios Compat 2.2.jar`
- **Versão 1.21.1:** `1.4`
- **Categoria:** Compat; RPG; Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat
- **Função:** Bridge de renderização/equipamento entre Epic Fight e Curios que corrige/anexa visualmente itens em slots Curios ao modelo/renderer Epic Fight e mantém compatibilidade com acessórios/back-slot de mods suportados.
- **Dependências:** Epic Fight 21.17.3.1 + Curios API 9.5.1+1.21.1 estão fisicamente presentes. Providers locais relevantes à matriz publicada incluem Supplementaries 3.9.8, Sophisticated Backpacks 3.26.2 e Iron's Spells 3.16.3.
- **Compatibilidade/Riscos:** Divergência legítima: filename/publicação 2.2, metadata runtime 1.4. A matriz pública 1.21.1 localizada cita Epic Fight 21.16.1, enquanto o pack usa 21.17.3.1; isso é version-drift gate. Riscos de accessory transform duplicado, slot não reconhecido, book/backpack/quiver clipping, stale Curios state e conflito com outros armor/player render bridges.
- **Sobreposição:** Não substitui Curios ou Epic Fight. Atua na camada de attachment/render de equipamentos Curios no modelo Epic Fight; outras armor/player render bridges podem tocar os mesmos transforms e exigem precedence.
- **Observações:** SHA-1 físico `3b13ee4590d3765249419c6f320f461cbf1c8f6d` identifica exatamente o arquivo CurseForge 7865987 publicado como `Epic Fight x Curios Compat 2.2.jar`; o metadata interno continua `1.4` e não deve ser normalizado para 2.2. Índices públicos secundários classificam o arquivo como client-only; side oficial não foi reextraído neste ciclo.
- **Procedência:** Modlist física canônica de 08/09/2026 confirma `Epic Fight x Curios Compat 2.2.jar`, mod id `epicfight_curios_compat`, metadata version 1.4 e SHA-1 3b13ee4590d3765249419c6f320f461cbf1c8f6d. O hash liga o artefato ao CurseForge file 7865987; matriz pública do projeto foi usada apenas para escopo de slots/providers.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — EpicFight x Curios Compat artifact 2.2 / metadata 1.4; Curios slot rendering, local supported consumers, version drift, lifecycle, risks and tests cataloged.
- **Data da última decisão:** 2026-08-26

> **Autoridade física:** `Epic Fight x Curios Compat 2.2.jar` · mod id `epicfight_curios_compat` · **metadata runtime `1.4`**. O filename/publicação é `2.2`; essa divergência é legítima e deve permanecer explícita. SHA-1: `3b13ee4590d3765249419c6f320f461cbf1c8f6d`.

## 1. Papel no modpack
Epic Fight x Curios Compat é uma bridge de **equip/render**: itens equipados em slots Curios precisam acompanhar o modelo/armature/pose do Epic Fight em vez de permanecer presos ao renderer vanilla ou desaparecer/clipping durante animações.

## 2. Authority / ownership
- **Curios:** slot topology, equip/unequip e ItemStack real.
- **Epic Fight:** player armature/animation/combat renderer.
- **Compat:** tradução/attachment visual entre slot Curios e renderer Epic Fight.

O compat não deve copiar inventário Curios nem criar um segundo equipamento lógico.

## 3. Divergência de versão
A modlist física lê metadata `1.4`, enquanto o arquivo é publicado como `Epic Fight x Curios Compat 2.2.jar`. O SHA-1 físico corresponde ao CurseForge file 7865987.

Regra canônica: **não normalizar 1.4 para 2.2 nem o contrário**. Um identifica metadata interna; o outro, release/filename.

## 4. Stack base local
O pack usa:
- Epic Fight `21.17.3.1`;
- Curios API `9.5.1+1.21.1`.

A bridge precisa acompanhar alterações de armature/render do Epic Fight e slot/render contracts do Curios.

## 5. Matriz pública de slots/providers
A descrição pública localizada para a linha 1.21.1 lista suporte a providers como Supplementaries, Sophisticated Backpacks, Iron's Spells e outros backpack/back-slot mods.

No pack estão confirmados:
- Supplementaries `3.9.8`;
- Sophisticated Backpacks `3.26.2`;
- Iron's Spells `3.16.3`.

Não tratar os demais providers da lista como ativos sem presença física.

## 6. Supplementaries
A compat publicada declara suporte completo à linha Supplementaries. O caso clássico é item/quiver/back attachment que precisa acompanhar a transformação corporal do Epic Fight.

O ItemStack e o slot continuam pertencendo ao Curios/Supplementaries; a bridge apenas corrige apresentação.

## 7. Sophisticated Backpacks
Backpacks grandes são uma superfície de clipping/attachment relevante porque ocupam o torso/costas enquanto Epic Fight modifica pose e armature.

Regression gate: idle, sprint, attack, dodge, guard, death e troca de backpack sem transform residual.

## 8. Iron's Spells
A matriz publicada marca suporte específico a spellbooks/itens de back slot da família Iron's Spells. O pack também possui EFIS e EMF Compat: Iron's Spells, então a mesma região visual pode ser tocada por várias bridges.

Isso exige precedence de transform, não duplicação de equip state.

## 9. Drift Epic Fight 21.17.3.1
A matriz pública localizada para 1.21.1 cita **Epic Fight 21.16.1**. O runtime do pack é 21.17.3.1, portanto existe version drift real.

Não há evidência de incompatibilidade comprovada, mas toda mudança do renderer/armature é regression gate obrigatório.

## 10. Side boundary
Índices de arquivo secundários classificam o artefato como client-only, coerente com sua função de render. Neste ciclo a metadata oficial de side do CurseForge não foi reextraída de forma independente; portanto não usar essa classificação para remover o JAR do servidor sem teste/metadata do próprio artefato.

Gameplay/equipment state continua server-authoritative via Curios/provider.

## 11. Lifecycle
Validar:
- equip/unequip;
- troca de slot/item;
- battle mode on/off;
- attack/dodge/guard;
- death/respawn;
- dimension change;
- reconnect;
- resource/model reload;
- update Epic Fight;
- update Curios/provider do item.

## 12. Multiplayer
Outro jogador deve enxergar o accessory/backpack na posição correta a partir do state Curios sincronizado. O compat não pode exigir que o observador recrie inventário ou enviar mutation de equip baseada em render.

## 13. Riscos
1. item renderizado duas vezes;
2. item desaparecer em battle mode;
3. attachment point incorreto;
4. torso/backpack clipping;
5. spellbook/quiver transform competir com outra bridge;
6. Curios slot desconhecido;
7. equip state stale após relog;
8. Epic Fight armature/API drift 21.16.1→21.17.3.1;
9. metadata 1.4 ser confundida com release 2.2;
10. assumir client-only sem validar distribuição real do pack.

## 14. Matriz de testes
1. Epic Fight 21.17.3.1 + Curios 9.5.1 boot/connect.
2. Equip/unequip item Curios simples.
3. Supplementaries back/quiver item suportado.
4. Sophisticated Backpack em idle/sprint/attack/dodge.
5. Iron's Spellbook nas poses de cast.
6. EFIS + EMF Compat: Iron's Spells carregados simultaneamente.
7. Battle mode toggle repetido.
8. Death/respawn e reconnect com item equipado.
9. Outro jogador observando os acessórios.
10. Update smoke-test do Epic Fight antes de promover nova build.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica: filename 2.2, mod id, metadata 1.4, hash e versões Epic Fight/Curios/providers;
- SHA-1 físico → CurseForge file 7865987;
- descrição/matriz pública do projeto: correção de render Curios e providers/slots suportados em 1.21.1.

> **Boundary canônico:** Curios continua authority do **equipamento**; Epic Fight do **modelo/combat animation**; esta bridge apenas resolve o attachment/render entre ambos.