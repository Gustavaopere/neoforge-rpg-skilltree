# Epic Fight - Pehkui Incompatibility FIX

## Propriedades do registro

- **Mod:** Epic Fight - Pehkui Incompatibility FIX
- **Arquivo JAR:** `epicfightpehkuiincompatibilityfix-1.0.2.jar`
- **Versão 1.21.1:** `1.0.2`
- **Categoria:** Compat, RPG
- **Função:** Patch client-side que corrige o desalinhamento de braços/eye height do player model Epic Fight quando Pehkui altera escala/dimensão.
- **Dependências:** Funcionalmente Epic Fight 21.17.3.1 + Pehkui 3.8.3+1.21-neoforge, ambos fisicamente presentes. O projeto não precisa possuir gameplay state próprio.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Patch estreito de rendering. Regression gates: first/third person, battle-mode transition, respawn/dimension change, scale extrema e coexistência com outras bridges de player model. Se Epic Fight corrigir nativamente, pode surgir double-fix/offset duplicado; retestar sem o addon após updates.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-fight-pehkui-fix
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `epicfightpehkuiincompatibilityfix-1.0.2.jar`, mod id `epicfightpehkuiincompatibilityfix`, runtime 1.0.2 e SHA-1 `76eb6471698ce24d6fbdfcdd4b04397e96d401aa`. CurseForge oficial revalidado em 21/09/2026 mantém 1.0.2 como latest release NeoForge 1.21.1.
- **Observações:** Projeto oficial descreve o problema como arm height não acompanhando corretamente dimension/scale Pehkui no Epic Fight e classifica a correção como tiny client compatibility fix. Sua necessidade deve ser reavaliada a cada update do Epic Fight.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — registro histórico do lote físico #263 na snapshot então vigente; posição física atual #264: `epicfightpehkuiincompatibilityfix-1.0.2.jar` / runtime `1.0.2` reconfirmados na snapshot física então vigente de 587 entradas top-level incluindo o modloader. CurseForge oficial mantém 1.0.2 como latest release NeoForge 1.21.1 e Environment Client.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa Pehkui e Epic Fight em um único transform visual. Não substitui nenhum mod-base e pode se tornar redundante apenas se o Epic Fight incorporar a mesma correção.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `epicfightpehkuiincompatibilityfix-1.0.2.jar` · mod id `epicfightpehkuiincompatibilityfix` · versão `1.0.2` · NeoForge 1.21.1 · client-side. Runtime base: **Epic Fight 21.17.3.1 + Pehkui 3.8.3+1.21-neoforge**.
## 1. Papel no modpack
Patch estreito de compatibilidade visual entre Epic Fight e Pehkui. Corrige o caso em que mudanças de escala/dimensão do player deixam a altura/posição dos braços do Epic Fight desalinhada em relação aos olhos/câmera do jogador.
## 2. Authority / ownership
- **Pehkui:** escala/dimensões do player e transform lógico associado.
- **Epic Fight:** player model/armature/animations em battle mode.
- **Fix:** correção client-side do transform/altura dos braços diante da escala atual.
Não possui authority sobre hitbox, damage, stamina ou cálculo de escala server-side.
## 3. Condição que justifica o patch
A descrição oficial aponta a incompatibilidade ao alterar **dimension ou scale** via Pehkui: Epic Fight não acompanha corretamente a altura dos braços. O fix mantém os braços alinhados ao nível dos olhos do player.
## 4. Side boundary
O projeto é explicitamente **Client**. A correção existe na apresentação do player/câmera. O servidor continua authority da dimensão/escala e de toda a lógica de combate.
## 5. First/third person
Como a correção toca braço/câmera, first-person e third-person são regression gates distintos. O pack também contém First-person Model e outras camadas de player rendering; testar transform sem aplicar offsets duas vezes.
## 6. Epic Fight battle mode
O bug relevante deve ser testado com battle mode ligado/desligado e durante transição. Quando Epic Fight deixa de controlar o player model, o patch não deve deixar offset residual.
## 7. Pehkui lifecycle
Escala pode mudar em runtime. O fix deve acompanhar scale up/down, relog, respawn, dimension change e qualquer provider que modifique Pehkui sem depender de restart para recalcular braços.
## 8. Obsolescência potencial
O autor declara que o patch será mantido até o Epic Fight corrigir a incompatibilidade nativamente. Portanto, em atualização futura do Epic Fight, **double-fix** é um risco real: antes de manter o patch, reproduzir o bug sem ele.
## 9. Multiplayer
Cada cliente corrige seu rendering local. Outros jogadores devem continuar recebendo escala/state normal; o patch não deve enviar mutation de gameplay nem alterar hitbox remotamente.
## 10. Riscos
1. Epic Fight incorporar a correção nativamente e o addon aplicar offset em dobro;
2. Pehkui mudar API/transform semantics;
3. first-person e third-person divergirem;
4. battle mode toggle deixar transform residual;
5. outra bridge de player model aplicar o mesmo ajuste;
6. camera eye-height correta mas arm hit/renderer visual incorreto;
7. respawn/dimension change perder recalculação;
8. escala extrema expor clipping ou culling.
## 11. Matriz de testes
1. Scale 1.0 baseline.
2. Player menor e maior via Pehkui.
3. Battle mode on/off em cada escala.
4. First-person e third-person.
5. Sprint/attack/dodge/guard com escala alterada.
6. Death/respawn.
7. Dimension change.
8. Relog mantendo escala.
9. Multiplayer observando player escalado.
10. Após update de Epic Fight: testar sem o fix antes de promovê-lo.
**Esta catalogação não afirma que esses testes foram executados.**
## 12. Evidências
- modlist física canônica: JAR/mod id/version/hash + Epic Fight/Pehkui atuais;
- página oficial do fix: client-only, função de manter arm height alinhada aos eyes quando scale/dimension muda, e caráter temporário até correção upstream.
> **Boundary canônico:** esta modificação corrige **render transform de braços**. Pehkui permanece authority de escala e Epic Fight do combat/player animation framework.
