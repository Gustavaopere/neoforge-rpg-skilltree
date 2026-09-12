# KubeJSCurios — 1.0.4

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db816eaa34efe63102d9a2  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** KubeJSCurios
- **Arquivo JAR:** `kubejs_curios_neoforge_1.21.1-1.0.4.jar`
- **Versão 1.21.1:** `1.0.4`
- **Categoria:** Compat; Automação; RPG
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-curios/files/6825089
- **Função:** Integra Curios ao KubeJS, expondo comportamento de curios items, atributos/loot/context checks e registro de renderers customizados por scripts.
- **Dependências:** KubeJS 2101.7.2-build.374 + Curios 9.5.1+1.21.1 estão fisicamente presentes. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Riscos: duplicate attribute modifiers, looting/fortune stacking, renderer/pose conflicts, client authority leakage e API drift KubeJS/Curios. Scripts reais precisam ser auditados antes de atribuir comportamento concreto.
- **Sobreposição:** Complementa Curios/KubeJS; não cria slots independentes. Pode interagir com outros addons de acessórios/modifiers e animation/render stack.
- **Observações:** JAR físico `kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod id `kubejs_curios`, runtime 1.0.4. Release NeoForge 1.21.1 de 29/07/2025.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial KubeJS Curios 1.0.4 e documentação pública do projeto.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Curios item hooks, attributes/loot, renderer client-side, lifecycle e risks catalogados para 1.0.4.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 💍 **ESCOPO CANÔNICO.** Runtime físico: `kubejs_curios_neoforge_1.21.1-1.0.4.jar`, mod id `kubejs_curios`, versão `1.0.4`. É uma integração Curios ↔ KubeJS; não cria um inventário de acessórios independente.

## 1. Superfície de item Curios
A documentação oficial mostra builders/hooks KubeJS para comportamento de curios items: modificadores de atributos, regras de drop, fortune/looting adjustments e checks contextuais como neutralidade de piglins, powdered snow e Enderman mask.
Essas funções operam sobre o Curios provider e o item/script; não transferem ownership dos slots para KubeJSCurios.

## 2. Renderização client-side
`CuriosJSEvents.registerRenderer` permite registrar/remover renderers customizados para items. O callback recebe contexto de render, pose stack, entity/slot context e buffers. Isso é estritamente apresentação; nunca deve conceder stats ou gameplay state.

## 3. Curios authority
Curios 9.5.1+1.21.1 está fisicamente presente. Curios continua authority de slots, equip/unequip e inventory state. KubeJSCurios expõe esse contrato a scripts e adiciona callbacks/renderer glue.

## 4. Script lifecycle
Render registration pertence ao client lifecycle e pode ser reconstruído em reload. Comportamentos autoritativos de item precisam executar no side adequado. Handlers devem ser limpos/recarregados sem duplicação.

## 5. Atributos e loot
Scripts podem modificar atributos e influenciar looting/fortune por item equipado. Esses hooks são economicamente sensíveis: duas integrações aplicando o mesmo bônus podem produzir stacking inesperado. UUID/identity de modifiers deve permanecer estável quando a API assim exigir.

## 6. Riscos
1. Renderer custom quebra pose/animation stack.
2. Client callback usado como gameplay authority.
3. Attribute modifier duplica após equip/reload.
4. Looting/fortune stacking altera economia.
5. Script assume slot inexistente ou Curios API diferente.
6. Update KubeJS/Curios quebra wrappers.

## 7. Boundary para quests/perks
Item equipado deve ser lido pelo state Curios server-side; render visível não prova equip. Perks não devem replicar modifiers já aplicados pelo script/Curios item.

## 8. Matriz de testes
- [ ] Dedicated server inicia com KubeJSCurios 1.0.4 + KubeJS build.374 + Curios 9.5.1.
- [ ] Equip/unequip atualiza state uma vez.
- [ ] Modificadores não acumulam após relog/reload.
- [ ] Fortune/looting respeitam contexto e não double-stack.
- [ ] Renderer custom aparece apenas client-side e não quebra dedicated server.
- [ ] Pose permanece correta com animation mods do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limite
CurseForge oficial confirma Release 1.0.4 NeoForge 1.21.1 e documenta hooks de item/atributos/loot e `CuriosJSEvents.registerRenderer`. Scripts reais do pack não foram lidos; nenhum trinket/renderer custom é atribuído ao runtime sem localizar o script.
