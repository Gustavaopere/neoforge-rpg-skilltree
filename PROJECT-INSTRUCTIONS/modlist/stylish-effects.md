# Stylish Effects

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ffae89c30c2db8fa1f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `StylishEffects-v21.1.3-1.21.1-NeoForge.jar`, mod id `stylisheffects`, runtime `21.1.3`; Puzzles Lib 21.1.60 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Stylish Effects 21.1.3 e Puzzles Lib 21.1.60 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Stylish Effects
- **Arquivo JAR:** `StylishEffects-v21.1.3-1.21.1-NeoForge.jar`
- **Versão 1.21.1:** 21.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual
- **Função:** Overhaul client-side da exibição de status effects no HUD e menus, com widgets compactos, timers, amplifiers, tooltips e layout configurável; não altera a lógica dos efeitos.
- **Dependências:** Puzzles Lib é dependency do projeto e está presente fisicamente como `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`. Runtime Stylish Effects 21.1.3 é client-side.
- **Sobreposição:** Sobreposição de interface apenas com outros mods que redesenham status effects/HUD.
- **Compatibilidade/Riscos:** Riscos: overlap com outros HUDs/containers, config extrema e timer/widget stale. 21.1.3 corrige nomes longos ultrapassando backgrounds e amplifiers exibidos um nível acima do vanilla; ambos são regression gates.
- **Observações:** mod id `stylisheffects`; runtime 21.1.3; client-only. Decisão Sem decisão preservada. A UI representa MobEffect state; duração/amplifier/gameplay permanecem sob authority vanilla/mod provider.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Stylish Effects 21.1.3 + Puzzles Lib 21.1.60 físico. Dossiê de 08/09 preservado; nenhuma inspeção visual/runtime foi executada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/stylish-effects ; https://www.curseforge.com/minecraft/mc-mods/stylish-effects/files/8319771
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Stylish Effects 21.1.3 permanece exatamente instalado; HUD/menu widgets, config, Puzzles Lib dependency e fixes de long-name/amplifier permanecem coerentes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `StylishEffects-v21.1.3-1.21.1-NeoForge.jar`, mod id `stylisheffects`, versão `21.1.3`. Stylish Effects é um **overhaul client-side da apresentação de status effects**; não altera duração, amplificador ou lógica dos efeitos.

## 1. Identidade, versão e decisão
- **Mod:** Stylish Effects.
- **JAR físico:** `StylishEffects-v21.1.3-1.21.1-NeoForge.jar`.
- **Mod id:** `stylisheffects`.
- **Versão:** `21.1.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Canal:** release estável oficial de 25/06/2026.
- **Decisão:** Sem decisão; preservada.

## 2. Dependências
- **Puzzles Lib** é dependency do projeto.
- O pack contém `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`.
- Forge Config Screens aparece upstream como integração/opção em linhas do projeto, mas não deve ser inferido como requisito do runtime físico sem necessidade explícita.

## 3. Authority e ownership
Stylish Effects controla somente **layout/rendering dos widgets de Mob Effects**.
- Minecraft/mod provider do efeito continua authority do `MobEffectInstance`, duração, amplificador e regras.
- O cliente recebe esse state e decide como exibi-lo.
- Uma diferença visual não deve ser diagnosticada como alteração de gameplay até o state real ser verificado.

## 4. Overhaul do HUD
O projeto substitui a lista compacta vanilla de efeitos no HUD por widgets mais ricos/compactos, com:
- ícone;
- nome;
- timer;
- amplifier/nível;
- modos/tamanhos de widget configuráveis.

O objetivo é tornar muitos efeitos simultâneos legíveis sem depender da tela de inventário.

## 5. Efeitos em menus/containers
A mesma representação pode aparecer no inventário e em **outros menus/containers**, não apenas no inventário vanilla.

Isso amplia a superfície de compatibilidade para GUIs modded: widgets não devem sobrepor slots, abas, recipe viewers ou painéis laterais.

## 6. Tooltips e descrição
Ao passar o mouse sobre widgets, o mod fornece informações do efeito. O projeto também pode enriquecer essa superfície quando **Just Enough Effect Descriptions** está presente.

Essa integração é apresentação; a ausência do addon não altera o efeito lógico.

## 7. Configuração visual
Opções publicadas incluem, conforme modo:
- lado/posição do conjunto de widgets;
- quantidade de rows/columns;
- alpha/transparência;
- cores de texto;
- custom positions;
- escolha do renderer/widget ou desativação da superfície.

A configuração física do cliente não foi lida; nenhum layout específico é afirmado como ativo.

## 8. Release 21.1.3
Changelog específico 21.1.3:
- corrige nomes de efeitos longos ultrapassando o background do widget no inventário;
- corrige amplifiers sendo exibidos **um nível acima** do valor vanilla.

O segundo fix é especialmente importante: UI deve espelhar o amplifier real, não reinterpretá-lo.

## 9. Client / server e multiplayer
- Client-only: servidor não precisa de Stylish Effects para aplicar/sincronizar efeitos.
- Em multiplayer, dois clientes podem usar layouts diferentes e ainda receber o mesmo state de efeito.
- Dedicated server não deve depender desta biblioteca visual.
- Efeito aplicado/removido pelo servidor deve atualizar widget imediatamente sem state stale.

## 10. Lifecycle
Validar:
- login com nenhum efeito;
- aplicação/remoção de um efeito;
- muitos efeitos simultâneos;
- efeito com nome longo/localizado;
- amplifier alto;
- duração curta/longa/infinita quando provider suportar;
- abrir inventário e containers modded;
- GUI scale/resolução;
- resource/language reload;
- relog e troca de dimensão.

## 11. Integrações concretas no pack
- **Puzzles Lib 21.1.60:** dependency física.
- **Particle Effects:** representa efeitos por partículas; Stylish Effects representa o mesmo state por HUD. São complementares.
- **Subtle Effects:** outro provider visual; não controla status-effect HUD.
- **Obscure Tooltips / Better Advanced Tooltips:** podem alterar tooltips, mas em superfícies diferentes; hover de effect widgets precisa de regressão de layout.
- Mods de magia/RPG adicionam muitos status effects e amplifiers; são stress test natural do widget.

## 12. Sobreposição
Sobreposição relevante é exclusivamente com mods que redesenham **status-effect HUD/inventory widgets**. Não é redundância com mods que alteram partículas, efeitos lógicos ou atributos.

Se duas UIs tentarem esconder/substituir a mesma listagem vanilla, testar prioridade e duplicação visual.

## 13. Riscos técnicos
1. **HUD overlap:** action bars, boss bars, minimaps ou RPG HUDs podem disputar espaço.
2. **Container overlap:** menus modded podem não reservar a região usada pelos widgets.
3. **Amplifier display:** 21.1.3 corrige off-by-one; manter regression gate.
4. **Long names/localization:** também corrigido em 21.1.3; testar idiomas/textos longos.
5. **Stale duration:** timer deve acompanhar packet/state real após relog/dimension change.
6. **Config edge cases:** rows/columns/custom position extremos podem empurrar widgets para fora da tela.

## 14. Matriz de testes
- [ ] Cliente NeoForge inicia com Stylish Effects 21.1.3 + Puzzles Lib.
- [ ] Dedicated server inicia sem Stylish Effects.
- [ ] Efeito vanilla nível I/II/alto mostra amplifier exatamente como vanilla.
- [ ] Nome de efeito longo não ultrapassa background.
- [ ] Muitos efeitos simultâneos permanecem legíveis.
- [ ] Inventário e containers modded sem overlap crítico.
- [ ] GUI scale mínimo/máximo usado no pack.
- [ ] Efeito aplicado/removido no servidor atualiza HUD imediatamente.
- [ ] Relog/dimension change sem timer/widget stale.
- [ ] Particle Effects/Subtle Effects ativos não duplicam HUD.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 15. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e Puzzles Lib presente.
- CurseForge/Modrinth oficiais: Stylish Effects 21.1.3 NeoForge 1.21.1, ambiente Client e função de status-effect display overhaul.
- Changelog oficial 21.1.3: long-name overflow e amplifier off-by-one fixes.

## 16. Revalidação física — 11/09/2026
O runtime físico continua exatamente `StylishEffects-v21.1.3-1.21.1-NeoForge.jar`, mod id `stylisheffects`, versão `21.1.3`, com Puzzles Lib `21.1.60` presente.

Os fixes 21.1.3 para **nomes longos extrapolando o background** e **amplifier exibido um nível acima** permanecem regression gates. A decisão continua **Sem decisão** e nenhum teste visual/runtime foi executado nesta recatalogação.
