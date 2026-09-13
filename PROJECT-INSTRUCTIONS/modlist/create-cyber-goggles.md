# Create: Cyber Goggles

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816a8306c1e61679b27c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Cyber Goggles
- **Arquivo JAR:** `CreateCyberGoggles-1.21.1-8.5.3-NeoForge.jar`
- **Versão 1.21.1:** 8.5.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual, Tecnologia
- **Função:** Mod client-side de assistência para Create, com goggles e recursos de informação/visualização/QoL voltados a máquinas e redes Create.
- **Dependências:** Client-side; source matching exige Create mínimo 6.0.10. Pack usa Create 6.0.10. Integrações concretas relevantes incluem Aeronautics 1.3.2/Sable 2.0.5, JEI 19.53.0.426, Jade 15.10.6 e CEI 2.5.3b; EMI/AE2 não estão top-level.
- **Sobreposição:** Não é equivalente a Create Goggles/equipamentos físicos: Cyber Goggles é assistência client-side. Pode sobrepor UI/overlays de Jade/JEI/outros, mas não owns gameplay state.
- **Compatibilidade/Riscos:** Riscos: overlay divergir do server state; `Image is not allocated` regression; Requester undo/recipe transfer duplicar ação; tooltip dedup incorreto; shader/post-process conflicts; Aeronautics body stale; Create 6.0.10 vs source-dev 6.0.11 internals.
- **Observações:** JAR `CreateCyberGoggles-1.21.1-8.5.3-NeoForge.jar`, mod id `create_cyber_goggles`, runtime 8.5.3. A antiga observação runtime 8.3.15 foi corrigida. Source branch `1.21.1/NeoForge` é pin-matching 8.5.3.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog 8.5.3 + repositório oficial ForgeStove/CreateCyberGoggles branch matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 8.5.3 com goggles/tooltips/overlays, Aeronautics force HUD, requester/recipe transfer, image lifecycle, client-only boundary e regressões catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> 🥽 **Identidade física e source matching confirmados:** `CreateCyberGoggles-1.21.1-8.5.3-NeoForge.jar`, mod id `create_cyber_goggles`, runtime `8.5.3`. O branch oficial `1.21.1/NeoForge` declara exatamente mod 8.5.3, Java 21, NeoForge 21.1.248 e Create mínimo 6.0.10.

## 1. Papel e authority
Create: Cyber Goggles é um addon **estritamente client-side** de assistência para Create. Ele owns overlays, tooltips, post-processing, visualização de informação e atalhos de UI; **não deve se tornar authority de inventory, kinetics, stress, recipes, contraptions ou requests server-side**.

## 2. Compatibilidade física principal
O pack usa Create 6.0.10, exatamente o mínimo declarado pelo source 8.5.3. O source foi desenvolvido contra Create 6.0.11, portanto a linha é compatível por requisito publicado, mas features que dependem de internals específicos precisam ser smoke-tested na 6.0.10 física.

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
O README confirma recipe transfer via viewer para Stock Keeper/Redstone Requester. O pack contém JEI 19.53.0.426. EMI não aparece como JAR top-level atual, embora o source 8.5.3 tenha integração EMI disponível.
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

## 16. Integrações source matching
O `gradle.properties` 8.5.3 inclui ambientes de desenvolvimento para Jade, Create Enchantment Industry, Create Dragons Plus, Create Fluid Logistics, Sophisticated Core, Ars Nouveau, AE2 e Thirst, além de Aeronautics/Sable.
Isso prova superfícies de desenvolvimento/compatibilidade, não garante que todas estejam habilitadas ou presentes no pack. Na ficha, integração runtime só é tratada como concreta quando a modlist confirma o provider.

## 17. Ars Nouveau/Jade/CEI no pack
O pack contém Ars Nouveau 5.13.1, Jade 15.10.6 e Create: Enchantment Industry 2.5.3b, além de várias superfícies Create. Esses providers tornam relevante smoke-test visual/tooltips, mas não autorizam inventar uma feature específica se ela não estiver documentada no README/source consultado.

## 18. Applied Energistics — não ativo
O source de desenvolvimento inclui AE2, mas a modlist física atual não contém Applied Energistics 2. Portanto qualquer compat AE2 da linha não é considerada ativa neste pack.

## 19. Client-only boundary
A descrição oficial/source define o mod como client-side. Dedicated server deve iniciar sem o JAR e o state lógico do mundo deve ser idêntico entre clientes com e sem Cyber Goggles.
Se uma feature aparentemente exige servidor com o mod instalado, isso deve ser investigado como possível dependency/hook externo, não assumido como design normal.

## 20. Config
As features principais podem ser habilitadas/desabilitadas individualmente. Config controla presentation e assistência; ela não deve alterar os valores server-authoritative que são exibidos.
Profiles/config reload precisam remover overlays/hooks desativados sem restart quando suportado, ou declarar restart quando exigido pelo runtime.

## 21. Multiplayer
Dois players podem ter configs diferentes e ainda receber o mesmo state do servidor. Um pode usar overlays avançados e outro não; isso não deve alterar recipes, requests, inventory ou physics.

## 22. Riscos
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

## 23. Matriz de testes
- [ ] Dedicated server inicia sem Cyber Goggles instalado.
- [ ] Cliente 8.5.3 conecta ao servidor Create 6.0.10 sem protocol requirement próprio.
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

## 24. Evidências e limites
A modlist física confirma JAR/mod id/runtime 8.5.3. O source oficial branch `1.21.1/NeoForge` é pin-matching e confirma Client-side, Java 21, NeoForge 21.1.248 e Create mínimo 6.0.10. README matching lista as famílias de features e Aeronautics overlays. O changelog 8.5.3 confirma EMI/Requester/Image/tooltip fixes. Nenhuma feature upstream foi marcada como ativa sem provider físico correspondente.

> 🔒 **Boundary canônico:** Cyber Goggles owns apenas assistência/visualização/UX no cliente. Create, logistics providers e Aeronautics/Sable continuam authority do state mostrado. Se o overlay e o servidor divergem, o servidor/provider vence.