# Create: Cyber Goggles

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#179**: JAR `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`, mod id `create_cyber_goggles`, runtime `8.6.3`, SHA-1 `7f27bcf2fa179135fcca6a5a61c8b120cba76b84`.

## Propriedades do registro

- **Mod:** Create: Cyber Goggles
- **Arquivo JAR:** `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`
- **Versão 1.21.1:** `8.6.3`
- **Categoria:** QoL, Visual, Tecnologia
- **Função:** Mod client-side de assistência para Create, com goggles e recursos de informação/visualização/QoL voltados a máquinas e redes Create.
- **Dependências:** Client-side sobre Create 6.0.10; pack físico usa NeoForge 21.1.250 e JEI 19.56.0.440. Source branch 8.6.3 declara Create mínimo 6.0.10 e usa JEI 19.56.0.439 no ambiente de desenvolvimento.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: regressão de mixin com JEI (#92, corrigida em 8.6.3); overlay divergir do server state; `Image is not allocated` regression; Requester undo/recipe transfer duplicar ação; tooltip/provider conflict; shader/post-process conflicts; Aeronautics body stale; Create internals drift. A upstream 8.6.4 refatora goggle tooltips para registry extensível justamente para reduzir overwrite conflicts; 8.6.5 corrige tooltip fade e completa a navegação Aeronautics #97.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles
- **Procedência:** modlist física atual confirma `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar` / 8.6.3. A auditoria anterior estabeleceu pin matching do branch oficial à 8.6.3 naquele momento; o branch `1.21.1/NeoForge` avançou depois para as releases 8.6.4/8.6.5 e não é mais um pin exato do JAR instalado. CurseForge 8.6.4/8.6.5 e GitHub PRs #98/#99 + issue #97 foram revalidados em 01/10/2026.
- **Observações:** Runtime físico permanece 8.6.3 Beta. Upstream posterior publicou 8.6.4 e 8.6.5 como Releases: 8.6.4 incorpora traduções e uma nova registry API para composição de goggle tooltips; 8.6.5 corrige tooltip fade e adiciona/fecha a feature #97 de distância para navigation items do Aeronautics.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 8.6.3 Beta. As releases 8.6.4 e 8.6.5 foram auditadas em ordem e seus deltas de tooltip extensibility/compatibilidade e Aeronautics UX foram incorporados abaixo.
- **Decisão:** Sem decisão
- **Sobreposição:** Não é equivalente a Create Goggles/equipamentos físicos: Cyber Goggles é assistência client-side. Pode sobrepor UI/overlays de Jade/JEI/outros, mas não owns gameplay state.
- **Data da última decisão:** 2026-09-20

# Dossiê operacional — padrão Alex's Mobs
> 🥽 **Identidade física e source matching confirmados:** `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`, mod id `create_cyber_goggles`, runtime `8.6.3`. O branch oficial `1.21.1/NeoForge` declara exatamente mod 8.6.3, Java 21, NeoForge 21.1.250 e Create mínimo 6.0.10. A build instalada é Beta.
## 1. Papel e authority
Create: Cyber Goggles é um addon **estritamente client-side** de assistência para Create. Ele owns overlays, tooltips, post-processing, visualização de informação e atalhos de UI; **não deve se tornar authority de inventory, kinetics, stress, recipes, contraptions ou requests server-side**.
## 2. Compatibilidade física principal
O pack usa Create 6.0.10, exatamente o mínimo declarado pelo source 8.6.3. O source foi desenvolvido contra Create 6.0.11-300, portanto a linha é compatível por requisito publicado, mas features que dependem de internals específicos precisam ser smoke-tested na 6.0.10 física.
## 3. Goggles — informação avançada
O README matching confirma informação ampliada de **rotation speed, stress e fluid flow**, com maior precisão numérica configurável e opção para ocultar informações estáticas.
Esses valores são apresentação de state existente. Qualquer discrepância com servidor/Create deve ser tratada como bug de overlay, nunca como alteração automática do state real.
## 4. Factory Gauge e Store UX
O addon melhora interação/visualização de Factory Gauge e informações de Table Cloth Store. Essas features mexem em ergonomia e presentation, não em ownership de logística/economia.
Ação enviada pelo cliente ainda precisa ser validada pelo provider/server correspondente.
## 5. Tooltips expandidos
O source documenta tooltips extras para containers, fluid containers, ender chest, toolbox, clipboard, map, backtank, diving boots, wrench, linked controller, filters, packages, placards, table cloth, depot, deployer, millstone, crushing controller, redstone requester e factory gauge.
A lista é ampla; cada tooltip deve ler dados existentes sem copiar/mutar inventory/NBT.
## 6. Item info overlay
Pode renderizar informações de item diretamente sobre blocks, com tema e cores configuráveis. É uma superfície puramente visual; ocultar/mostrar overlay não muda capacidade, conteúdo ou recipe.
## 7. Drafting View
A **Drafting View** aplica pixelização/outline pós-processado à cena com escala e cor configuráveis. Shader/framebuffer lifecycle é crítico: resource reload, resize e troca de mundo precisam recriar recursos sem `Image is not allocated` ou leak.
## 8. Outliner
O addon renderiza analog boxes/connection lines com configuração de cor e delay. Essas linhas são visualização derivada; não devem manter referências a blocks/contraptions descarregadas.
## 9. Aeronautics — integração ativa
O README matching possui seção Aeronautics que pode sempre mostrar **mass/friction** e um **force overlay** com gravity, lift, drag, levitation, balloon lift, propulsion, magnetic force e center of mass, além de HUD de massa/força.
O pack contém Create Aeronautics 1.3.2 e Sable 2.0.5; portanto essa integração é concreta. Os valores exibidos não substituem a physics authority do Sable/Aeronautics.
## 10. Misc/Create UX
A linha 8.5.3 inclui melhorias para wrench/chain conveyor, contagem de stacks no estilo Create, correção de blueprint name, recursive blueprint scan, edit boxes extensas, quick request actions, preview filter e stress network display.
Cada feature precisa permanecer client-side e não duplicar packet/action quando o usuário usa atalhos.
## 11. Stock Keeper/Redstone Requester recipe transfer
O README confirma recipe transfer via viewer para Stock Keeper/Redstone Requester. O pack contém JEI 19.56.0.440. EMI não aparece como JAR top-level atual, embora a linha atual tenha integração EMI disponível.
Recipe transfer é conveniência de preenchimento/request; Recipe Manager e logistics provider continuam authority.
## 12. Delta 8.5.3 — EMI
O changelog 8.5.3 adiciona compatibilidade EMI ao Redstone Requester. Como EMI não está presente top-level na modlist atual, esse delta é upstream disponível, mas não integration runtime ativa confirmada.
Não considerar `emi-bridge` embarcado em outro mod equivalente à presença do EMI principal.
## 13. Delta 8.5.3 — Requester undo
A 8.5.3 corrige a **state machine do botão Undo do Redstone Requester**. Regression gate: sequência request→undo→novo request precisa refletir exatamente o state do provider sem repetição/ação fantasma.
Como o addon é client-side, qualquer request real continua dependendo do servidor/mod-alvo aceitar a ação.
## 14. Delta 8.5.3 — image allocation crash
A 8.5.3 corrige crashes do tipo **`Image is not allocated`**. Isso aponta diretamente para lifecycle de imagens/render targets.
Resource reload, resize, shader toggle, world change e close/reopen de screens são testes prioritários desta versão.
## 15. Delta 8.5.3 — duplicated tooltip content
A build remove deduplicação genérica de linhas de tooltip e adiciona uma interface para resolver conteúdo duplicado de maneira controlada. Isso reduz o risco de remover linhas legítimas apenas porque o texto coincide.
Regression gate: tooltip não duplica conteúdo próprio do Cyber Goggles e também não suprime linhas válidas de outros mods.
## 16. Estado de atualização da linha 1.21.1
O runtime físico atual é **8.6.3**, publicado no CurseForge em 14/09/2026 como **Beta** para NeoForge 1.21.1. O projeto havia publicado 8.6.0/8.6.1 como Betas após a release estável 8.5.3; a instalação atual avançou para a linha Beta 8.6.3. Portanto preservar a distinção entre **versão instalada = 8.6.3** e **canal de estabilidade = Beta**. O filename do catálogo deve seguir a build física instalada, não a última release marcada Stable.
## 17. Integrações source matching
O `gradle.properties` atual 8.6.3 inclui ambientes de desenvolvimento para Jade, Create Enchantment Industry, Create Dragons Plus, Create Fluid Logistics, Create Phantom, Create Mobile Packages, Sophisticated Core, Ars Nouveau, AE2 e Thirst, além de Aeronautics/Sable. Ele declara Java 21, NeoForge 21.1.250, Create mínimo 6.0.10 e JEI 19.56.0.439 no ambiente de desenvolvimento.
Isso prova superfícies de desenvolvimento/compatibilidade, não garante que todas estejam habilitadas ou presentes no pack. Na ficha, integração runtime só é tratada como concreta quando a modlist confirma o provider.
## 18. Ars Nouveau/Jade/CEI no pack
O pack contém Ars Nouveau 5.13.1, Jade 15.10.6 e Create: Enchantment Industry 2.5.3b, além de várias superfícies Create. Esses providers tornam relevante smoke-test visual/tooltips, mas não autorizam inventar uma feature específica se ela não estiver documentada no README/source consultado.
## 19. Applied Energistics — não ativo
O source de desenvolvimento inclui AE2, mas a modlist física atual não contém Applied Energistics 2. Portanto qualquer compat AE2 da linha não é considerada ativa neste pack.
## 20. Client-only boundary
A descrição oficial/source define o mod como client-side. Dedicated server deve iniciar sem o JAR e o state lógico do mundo deve ser idêntico entre clientes com e sem Cyber Goggles.
Se uma feature aparentemente exige servidor com o mod instalado, isso deve ser investigado como possível dependency/hook externo, não assumido como design normal.
## 21. Config
As features principais podem ser habilitadas/desabilitadas individualmente. Config controla presentation e assistência; ela não deve alterar os valores server-authoritative que são exibidos.
Profiles/config reload precisam remover overlays/hooks desativados sem restart quando suportado, ou declarar restart quando exigido pelo runtime.
## 22. Multiplayer
Dois players podem ter configs diferentes e ainda receber o mesmo state do servidor. Um pode usar overlays avançados e outro não; isso não deve alterar recipes, requests, inventory ou physics.
## 23. Riscos
1. Overlay mostra speed/stress/flow incorretos e induz troubleshooting errado.
2. Resource reload/resize reproduz `Image is not allocated`.
3. Requester Undo envia ação duplicada/stale.
4. Recipe transfer envia request duas vezes.
5. Tooltip dedup remove linha válida ou deixa duplicata.
6. Aeronautics force overlay mantém physics body stale após unload.
7. Post-processing conflita com shader/render stack.
8. Create 6.0.10 difere do 6.0.11 usado no source dev.
9. Client-only mod passa a ser exigido indevidamente pelo servidor.
10. Integração upstream é tratada como ativa sem provider físico.
11. Atualização de JEI voltar a quebrar o mixin de Cyber Goggles, regressão coberta pelo issue #92 e fix 8.6.3.
## 24. Matriz de testes
- [ ] Dedicated server inicia sem Cyber Goggles instalado.
- [ ] Cliente 8.6.3 conecta ao servidor Create 6.0.10 sem protocol requirement próprio.
- [ ] Boot do cliente com JEI 19.56.0.440 não reproduz o mixin apply failure do issue #92.
- [ ] Speed/stress/flow overlay confere com valores Create reais.
- [ ] Resource reload, resize e shader toggle não reproduzem `Image is not allocated`.
- [ ] Tooltips não duplicam nem removem conteúdo legítimo.
- [ ] Redstone Requester request→undo funciona conforme fix 8.5.3.
- [ ] JEI recipe transfer gera uma única ação/request válida.
- [ ] Ausência de EMI não causa classloading/crash da compat opcional.
- [ ] Aeronautics mass/friction/force overlay acompanha o body real após assemble/unload.
- [ ] Dois clientes com configs diferentes veem o mesmo gameplay state.
- [ ] Desabilitar módulos remove overlays/hooks correspondentes sem state residual.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 25. Evidências e limites
A modlist física confirma JAR/mod id/runtime 8.6.3. Na auditoria da build instalada, o branch oficial `1.21.1/NeoForge` estava pin-matching 8.6.3 e sustentava Client-side, Java 21, NeoForge 21.1.250 e Create mínimo 6.0.10; esse branch avançou posteriormente para 8.6.4/8.6.5 e já não é usado como pin exato do JAR físico. O pack usa JEI 19.56.0.440; o fix #92 da 8.6.3 permanece lineage relevante para o mixin failure causado por atualização do JEI. Os fixes documentados na 8.5.3 para EMI/Requester/Image/tooltip permanecem como lineage herdada. Features 8.6.4/8.6.5 são registradas apenas como upstream não instalado.
> 🔒 **Boundary canônico:** Cyber Goggles owns apenas assistência/visualização/UX no cliente. Create, logistics providers e Aeronautics/Sable continuam authority do state mostrado. Se o overlay e o servidor divergem, o servidor/provider vence.

## 26. Atualizações upstream 8.6.4 → 8.6.5 — não instaladas

A autoridade física continua em **Create: Cyber Goggles 8.6.3 Beta**. A listagem oficial 1.21.1/NeoForge publica depois **8.6.4** (23/09/2026) e **8.6.5** (26/09/2026), ambas como **Release**.

### 8.6.4 — composição de goggle tooltips e extensão por terceiros
O changelog público resume a release como merge das PRs **#98** e **#99**.

- **PR #98:** novas traduções via Crowdin; impacto predominantemente de localization.
- **PR #99:** mudança arquitetural importante na superfície de tooltips. O upstream substitui a dependência de múltiplos class-level overwrites de `addToGoggleTooltip` por um registro público **`GoggleTooltip`**.
  - mods terceiros podem registrar entries sem criar seu próprio overwrite/mixin para essa superfície;
  - `registerBefore` / `registerAfter` suportam anchors nomeados;
  - anchor ausente degrada para append + warning;
  - os 11 conteúdos internos de Cyber Goggles passam a usar o mesmo registry;
  - conteúdo nativo do Create é representado por um anchor reservado `create:native` quando necessário para preservar ordering;
  - exception de uma entry de terceiro é isolada com warning para não derrubar as demais entries;
  - o upstream declara que conteúdo, ordem visual e config toggles existentes foram preservados e registra regressão in-game contra 11 block entities.

Impacto para o pack: isso reduz uma superfície real de **mixin overwrite conflict** com outros mods que também ampliem goggles/Jade-like information. O benefício só deve ser considerado ativo depois da promoção da build; o runtime 8.6.3 continua usando a arquitetura anterior.

A PR também registra bootstrap comum para que o caminho de coleta por Jade possa acessar entries. Isso não deve ser reinterpretado como mudança de requisito de instalação: o CurseForge continua classificando Cyber Goggles como **Client**.

### 8.6.5 — tooltip lifecycle e Aeronautics navigation
O changelog oficial:
- corrige problema de **tooltip fading out**;
- completa a issue **#97**.

A issue #97 solicita que navigation items do Aeronautics mostrem a **distância entre o player e o destino** no tooltip, inclusive quando vistos em containers/storage UIs. No pack, onde Aeronautics/Sable estão presentes, essa é uma integration surface concreta de UX, mas permanece presentation-only.

### Gate de promoção 8.6.3 → 8.6.5
- [ ] Cliente 8.6.5 conecta ao servidor sem transformar Cyber Goggles em requisito server-side.
- [ ] Tooltips internos continuam com conteúdo e ordering equivalentes aos de 8.6.3.
- [ ] Dois providers que estendem a mesma goggle tooltip coexistem sem overwrite skip/conflict.
- [ ] `registerBefore/After` respeitam anchors; anchor inexistente não quebra a tooltip.
- [ ] Exception de provider terceiro é isolada e não suprime conteúdo nativo/CCG.
- [ ] Config toggle desabilita apenas a entry correspondente e não interfere em terceiros.
- [ ] Jade/tooltip collection não causa classloading indevido em dedicated server.
- [ ] Tooltip fade não reaparece em hover prolongado/troca rápida de alvo.
- [ ] Aeronautics navigation item mostra distância correta em inventory/container/storage e atualiza ao player se mover.
- [ ] JEI 19.56.0.440 continua sem o mixin failure corrigido na 8.6.3.
- [ ] Drafting View/resource reload/shader lifecycle continua sem `Image is not allocated`.

Fontes upstream: CurseForge 8.6.4 file ID 8952040 e 8.6.5 file ID 8980429; GitHub oficial `ForgeStove/CreateCyberGoggles` PR #98, PR #99 e issue #97. Nenhum teste acima foi executado nesta atualização documental.
