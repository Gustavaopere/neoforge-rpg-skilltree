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
- **Compatibilidade/Riscos:** Riscos: regressão de mixin com JEI (#92, corrigida em 8.6.3); overlay divergir do server state; `Image is not allocated` regression; Requester undo/recipe transfer duplicar ação; tooltip/provider conflict; shader/post-process conflicts; Aeronautics body stale; Create internals drift. A upstream 8.6.4 refatora goggle tooltips para registry extensível; 8.6.5 corrige tooltip fade e completa a navegação Aeronautics #97; 8.6.6 corrige o crash de dedicated server do issue #102 causado por carregamento de `LocalPlayer` no lado servidor.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles
- **Procedência:** modlist física atual confirma `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar` / 8.6.3. A auditoria anterior estabeleceu pin matching do branch oficial à 8.6.3 naquele momento; o branch `1.21.1/NeoForge` avançou depois para as releases 8.6.4/8.6.5 e não é mais um pin exato do JAR instalado. CurseForge 8.6.4/8.6.5/8.6.6 e GitHub PRs #98/#99 + issues #97/#102 foram revalidados em 03/10/2026.
- **Observações:** Runtime físico permanece 8.6.3 Beta. Upstream posterior publicou 8.6.4, 8.6.5 e 8.6.6 como Releases: 8.6.4 incorpora traduções e uma nova registry API para composição de goggle tooltips; 8.6.5 corrige tooltip fade e adiciona/fecha a feature #97 de distância para navigation items do Aeronautics; 8.6.6 corrige o invalid-dist crash de dedicated server do issue #102.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece 8.6.3 Beta. A sequência 8.6.4 → 8.6.5 → 8.6.6 foi auditada; 8.6.6 é a latest 1.21.1 e corrige o crash de dedicated server rastreado no issue #102.
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
A modlist física confirma JAR/mod id/runtime 8.6.3. Na auditoria da build instalada, o branch oficial `1.21.1/NeoForge` estava pin-matching 8.6.3 e sustentava Client-side, Java 21, NeoForge 21.1.250 e Create mínimo 6.0.10; esse branch avançou posteriormente para 8.6.4/8.6.5/8.6.6 e já não é usado como pin exato do JAR físico. O pack usa JEI 19.56.0.440; o fix #92 da 8.6.3 permanece lineage relevante para o mixin failure causado por atualização do JEI. Os fixes documentados na 8.5.3 para EMI/Requester/Image/tooltip permanecem como lineage herdada. Features/fixes 8.6.4/8.6.5/8.6.6 são registrados apenas como upstream não instalado.
> 🔒 **Boundary canônico:** Cyber Goggles owns apenas assistência/visualização/UX no cliente. Create, logistics providers e Aeronautics/Sable continuam authority do state mostrado. Se o overlay e o servidor divergem, o servidor/provider vence.

## 26. Atualizações upstream 8.6.4 → 8.6.6 — não instaladas

A autoridade física continua em **Create: Cyber Goggles 8.6.3 Beta**. A listagem oficial 1.21.1/NeoForge publica depois **8.6.4** (23/09/2026), **8.6.5** (26/09/2026) e **8.6.6** (02/10/2026), todas como **Release**.

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

### 8.6.6 — dedicated server / issue #102

- Release NeoForge 1.21.1 publicada em 02/10/2026, CurseForge file ID `9034839`.
- O changelog oficial resume a release como **“Fix #102”**.
- O issue #102 documenta que a 8.6.5 podia impedir o startup de dedicated server ao tentar carregar a classe client-only `net.minecraft.client.player.LocalPlayer` em dist `DEDICATED_SERVER`.
- O fix upstream move/segrega o registro afetado para o lado apropriado, eliminando esse caminho inválido de classloading.
- Embora Cyber Goggles continue sendo um mod de função essencialmente client-side, esta correção é relevante para pack/server hygiene porque uma instalação acidental no servidor não deve provocar esse crash específico.

### Gate de promoção 8.6.3 → 8.6.6
- [ ] Cliente 8.6.6 conecta ao servidor sem transformar Cyber Goggles em requisito server-side.
- [ ] Dedicated server com 8.6.6 presente não reproduz o `LocalPlayer` invalid-dist crash do issue #102.
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

Fontes upstream: CurseForge 8.6.4 file ID 8952040, 8.6.5 file ID 8980429 e 8.6.6 file ID 9034839; GitHub oficial `ForgeStove/CreateCyberGoggles` PR #98, PR #99 e issues #97/#102. Nenhum teste acima foi executado nesta atualização documental.

## 27. Atualizações upstream 8.6.7 → 8.6.9 — 10/10/2026

A autoridade física continua **8.6.3**, `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`, com Create 6.0.10. O histórico de **8.6.4 → 8.6.5 → 8.6.6** está preservado na seção 26 (tooltip registry, fade/Aeronautics navigation e fix dedicated-server).

- **8.6.7 — 05/10/2026 (CurseForge file 9067133):** introduz pré-visualização tridimensional de schematics, com painel giratório e zoom junto à Schematic Table e preview por Alt-hover no tooltip do item. Informação de apresentação, sem alterar o blueprint autoritativo. [Fonte](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9067133).
- **8.6.8 — 05/10/2026 (CurseForge file 9068331):** autor registra **Fix #100** e **mudança na estrutura de configurações**. O número da issue, isoladamente, não comprova o sintoma. A modificação de config exige verificar migração de opções e defaults antigos.
- **8.6.9 — 06/10/2026 (CurseForge file 9081110):** otimiza interação de **fill** e altera o **zoom default do preview**, afetando UI/QoL e uso client-side.

**Cadeia completa:** 8.6.3 → 8.6.4 → 8.6.5 → 8.6.6 → **8.6.7 → 8.6.8 → 8.6.9**.

**Regressões:** Create 6.0.10, JEI 19.56.0.440, config antiga→nova, tooltip registry, requester/fill behavior, overlays, preview zoom, tecla/click, Resource Packs, Sable/Aeronautics e dedicated server (mesmo o addon sendo client-side) sem classloading indevido. Nenhum teste foi executado aqui.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/all?version=1.21.1 ; https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9068331 ; https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9081110

## 28. Auditoria incremental — 20/11/2026

Base física histórica: 8.6.3 Beta (#179), sem confirmação de substituição posterior. O catálogo [NeoForge](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/all?gameVersionTypeId=6&page=1&pageSize=20&showAlphaFiles=hide) confirma a sequência 8.6.4–8.6.9 já documentada; a lacuna de 8.6.7 foi corrigida acima. Novos arquivos para Minecraft 1.21.1 / Create 6.0.10:

- **8.7.0 — 26/10/2026, Release:** campos de quantidade aceitam expressões; multiplicador global ajusta os valores da tela; opção abrevia grandes contagens. Overlay de forças agrupa e limita setas para reduzir custo de renderização. Preview de filtros passa a ser somente leitura, com registro extensível de telas de preview e correção da propagação indevida de caracteres. [Changelog](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9335247).
- **8.8.0 — 11/11/2026, Beta:** visualização e cálculo de vazão em componentes de fluidos; localização de telas de configuração dos mods relacionados ao Create; correções de tooltip de table cloth e melhorias de renderização. A tela de reposição utiliza framework compartilhado de UI; bloqueio de configs restaurado; corrigidos erros de packets no fechamento do Redstone Requester. [Changelog](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9553576).
- **8.8.1:** distribuição NeoForge 1.21.1 identificada pelo [file 9660524](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9660524); notas não recuperadas. A cadeia após 8.8.0 ainda não está integralmente reconciliada; não promover nem considerar a auditoria encerrada.

**Relevância:** as mudanças afetam UX de schematics, filtros, interpretação de vazão e overlays, integração de previews e lifecycle de UI/packets, não transferem ownership de Create para o cliente. QA: validar quantidades inválidas/limites, preview read-only, vazão contra valores reais do Create, agrupamento de forças, resource reload, bloqueio de configurações, fechamento da tela de requester, compatibilidade JEI e ausência do addon no dedicated server. Nenhum teste executado. A release estável identificada é 8.7.0; não confundir Beta nem versão candidata com instalação.
## 29. Auditoria incremental — 20/11/2026: 8.8.1 e source posterior

A [publicação 8.8.1 Beta de 17/11/2026](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9660524) corresponde ao NeoForge 1.21.1 / Create 6.0.10 e contém changelog oficial, fechando a lacuna da seção 28:
- Nova configuração permite caminhar durante sneak sobre contraptions móveis, sem transferir autoridade de física para o cliente.
- Nova configuração faz jogador e câmera acompanharem a rotação da contraption; regressões a verificar: câmera, input, desmontagem e integração com Sable/Aeronautics.
- Correção de internacionalização das configurações; conferir opção traduzida e fallback.

**Cadeia posterior no código oficial:** [bump para 8.8.2 em 19/11/2026](https://github.com/ForgeStove/CreateCyberGoggles/commit/16ca27f9b37e1eca76ef299f94b3f12a766d2525), precedido por commits que corrigem desvio incorreto da câmera em contraptions, otimizam escalonamento de quantidades e proteção de bordas da estrutura móvel. Há também [bump para 8.8.3 em 20/11](https://github.com/ForgeStove/CreateCyberGoggles/commit/029932f68622c8e941c5d1bb95fc1b2cb5c6c81e), com atualização de biblioteca de configuração. O `gradle.properties` da branch `1.21.1/NeoForge` declara modVersion 8.8.3, loader 21.1.265 e Create mínimo 6.0.10. **Bump do source não certifica publicação do JAR**: a correspondência das builds 8.8.2 e 8.8.3 com releases oficiais CurseForge/Modrinth ainda não foi verificada; catálogo secundário aponta 8.8.2, mas não substitui source de distribuição.

**Status: PENDENTE.** Atualização 8.8.1 documentada; confirmar publicação final de 8.8.2/8.8.3, changelogs por build, sem confundir código dev com arquivo distribuído. Versão física e nome do dossier continuam 8.6.3; nenhum teste ou JAR foi alterado.

## Auditoria incremental parcial — 21/11/2026 — código-fonte 8.8.2/8.8.3, sem publicação oficial confirmada

**Autoridade física:** #179, `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`, versão **8.6.3**. Projeto oficial [Create: Cyber Goggles](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles), CurseForge 1233804, fonte `ForgeStove/CreateCyberGoggles`, branch **`1.21.1/NeoForge`**. Categorias **Create + Utility & QoL**, já corretas na pasta. O último arquivo do CurseForge **inequivocamente recuperado nesta revisão** continua [8.8.1 Beta, 17/11/2026, File ID 9660524](https://www.curseforge.com/minecraft/mc-mods/create-cyber-goggles/files/9660524).

**Atribuição detalhada de deltas ao versionamento do *source*, não ao binário:**

- **8.8.2 — source de 19/11/2026, [commit de version bump `16ca27f9`](https://github.com/ForgeStove/CreateCyberGoggles/commit/16ca27f9b37e1eca76ef299f94b3f12a766d2525):** altera simultaneamente `modVersion=8.8.1 → 8.8.2` e `CHANGELOG.md` para três notas exatas do autor. (1) **Corrige desvio incorreto da câmera sobre contraptions**: mudança de código identificada no [`ContraptionRotationEvents`, commit `36e675a3`](https://github.com/ForgeStove/CreateCyberGoggles/commit/36e675a3e1107bb5ff4a8700096be9eeb7b80700). (2) **Otimiza o multiplicador de quantidades**: o [commit `7cab60f9`](https://github.com/ForgeStove/CreateCyberGoggles/commit/7cab60f9f50c715861f26432ab7e6c9cd6be139d) ajusta posicionamento de botões em Redstone Requester/Stock Keeper e impede a apresentação do botão global na tela de restocker para a qual não se aplica. (3) **Aprimora proteção de bordas de contraptions móveis**, com [`ContraptionRotationTracker` e `PlayerEdgeProtectionMixin`, commit `cac5a51f`](https://github.com/ForgeStove/CreateCyberGoggles/commit/cac5a51fbeac072e0724bcda0747f410c2209cf9), incluindo cálculo no referencial local da contraption e verificação de suporte para sneak. Essas mudanças são relevantes à UX de navegação e risco de queda; não inferir proteção universal nem regras de colisão server-authoritative.
- **8.8.3 — source de 20/11/2026, [commit de version bump `029932f6`](https://github.com/ForgeStove/CreateCyberGoggles/commit/029932f68622c8e941c5d1bb95fc1b2cb5c6c81e):** muda `modVersion=8.8.2 → 8.8.3` e o changelog para **“Update flexconfig”**. O [commit preparatório `75149e60`](https://github.com/ForgeStove/CreateCyberGoggles/commit/75149e60d8f247bb04f5f34b78d13f723d0a229d) altera **`flexConfigVersion=0.2.2 → 0.3.0`** e adequa a UI de Replenish Screen/Scroll Pane à API da biblioteca. Esta é alteração de integração técnica e pode afetar renderização, scroll e lifecycle das telas; não afirmar que configurações antigas foram migradas automaticamente ou que esse artefato foi distribuído.

**Distinção indispensável:** um catálogo secundário [ModDex](https://moddex.gg/mod/create-cyber-goggles) relata **8.8.2** como versão mais recente, mas a **página do arquivo NeoForge 1.21.1 correspondente ainda não foi confirmada diretamente no CurseForge nem no Modrinth nesta execução**. Também não há publicação oficial da **8.8.3** comprovada. Version bumps do repositório fornecem changelogs *por versão de código*, mas **não demonstram que os JARs 8.8.2/8.8.3 foram disponibilizados**. Não escolher arbitrariamente 8.8.2 nem 8.8.3 como versão candidata publicada.

**Gates:** conferir versões das dependências/metadata dos binários que venham a ser publicados; a branch atual referencia loader NeoForge 21.1.265, enquanto a autoridade do projeto é 21.1.248 — *diferença de build alvo não prova incompatibilidade de runtime*, mas exige inspeção do `neoforge.mods.toml` antes de recomendar promoção. Smoke-test em cópia com Create 6.0.10 e Aeronautics/Sable, giro da câmera e de input, bordas inclinadas e dimension changes, multiplicador e UI do requester/replenish, FlexConfig 0.3.0, saves e dedicated server. **Nenhum QA executado.**

**Status: PENDENTE de confirmar e enumerar as publicações 8.8.2/8.8.3 na plataforma oficial**, embora os deltas upstream por version bump estejam auditados. Basename, JAR, pasta e versão física **8.6.3** preservados. Não retirar nº 109 do Bloco de Notas.

## Fechamento da cadeia de distribuições 8.6.3 → 8.8.3 — 21/11/2026

**Evidência nova e primária:** a plataforma oficial [Modrinth do mesmo autor, versão NeoForge 1.21.1 8.8.2](https://modrinth.com/mod/create-cyber-goggles/version/1.21.1-8.8.2-neoforge) e [versão **8.8.3**](https://modrinth.com/mod/create-cyber-goggles/version/1.21.1-8.8.3-neoforge) publica **efetivamente os JARs Beta para Minecraft 1.21.1**, com versões `1.21.1-8.8.2-neoforge` e `1.21.1-8.8.3-neoforge`. A versão **8.8.3** foi publicada em **20/11/2026** (metadata do [Modrinth API, projeto `TlQAWQCY`](https://api.modrinth.com/v2/project/create-cyber-goggles/version), version ID `pbHCUgry`), e a **8.8.2** em **19/11/2026** (version ID `YJuqUfUJ`). São distribuições oficiais verificadas, **não mais meros version bumps no source**. A documentação das versões 8.6.4/5/6/7/8/9, 8.7.0, 8.8.0 e 8.8.1 permanece nas seções 26–29; não repetir suas alterações como se fossem novas na 8.8.3.

- **8.8.2 (Beta, NeoForge 1.21.1):** o [changelog próprio da build publicada](https://modrinth.com/mod/create-cyber-goggles/version/1.21.1-8.8.2-neoforge) lista exatamente **correção de desvio indevido da câmera em contraptions**, **otimização de quantidade/escalonamento** e **otimização de proteção dinâmica contra bordas**. Isso **corrobora as notas do source** já auditadas na seção precedente e resolve a lacuna de correspondência entre versão anunciada e publicação oficial. Impactos: UX de contraptions, segurança visual em movimento, controles de quantidades do Requester/Stock Keeper; não presumir solução universal para quedas nem alteração de colisão server-authoritative.
- **8.8.3 (Beta, NeoForge 1.21.1):** o [changelog próprio publicado](https://modrinth.com/mod/create-cyber-goggles/version/1.21.1-8.8.3-neoforge) atribui somente **“Update flexconfig”**. O [source pinado, `gradle.properties`](https://github.com/ForgeStove/CreateCyberGoggles/blob/029932f68622c8e941c5d1bb95fc1b2cb5c6c81e/gradle.properties) fixa **FlexConfig `0.3.0`** (anterior `0.2.2`), e [`build.gradle.kts` na mesma revisão](https://github.com/ForgeStove/CreateCyberGoggles/blob/029932f68622c8e941c5d1bb95fc1b2cb5c6c81e/build.gradle.kts) declara **`jarJar` da biblioteca**. Ajustes internos de UI da Replenish Screen/Scroll Pane já estão atribuídos na seção precedente. Não criar entrada top-level de FlexConfig na modlist apenas por ser dependência embarcada.

**Conciliação de loader:** o build pinado de 8.8.3 usa **NeoForge `21.1.265`** como versão de desenvolvimento, enquanto a instalação histórica do pack usa **`21.1.248`**. O [template de metadata de dependências do autor](https://github.com/ForgeStove/CreateCyberGoggles/blob/029932f68622c8e941c5d1bb95fc1b2cb5c6c81e/src/main/templates/META-INF/neoforge.mods.toml) declara `neoforge`, `minecraft` e `create` como `required`, mas **não declara `versionRange` explícito**. Isso **não comprova** nem incompatibilidade nem sucesso de boot no NeoForge 21.1.248. O artefato final e seu metadata transformado, a biblioteca embarcada e suas APIs devem ser inspecionados antes de um upgrade físico. O source usa Create **6.0.10** como versão mínima de referência.

**Resultado:** **auditoria documental das releases 8.6.4 a 8.8.3 concluída**, com as publicações oficiais e changelogs de cada versão relevante identificados e as correções anteriores preservadas. As declarações nas seções 28/29 de que 8.8.2 e 8.8.3 não tinham JAR confirmado passam a ser **históricas e ficam superadas por esta evidência**. **Versão instalada permanece 8.6.3**, JAR `CreateCyberGoggles-1.21.1-8.6.3-NeoForge.jar`, posição #179. **Sem alteração de JAR, versão no nome, pasta ou configurações e sem QA/runtime**. Depois de re-fetch da persistência, **item 109 pode sair somente da lista de auditorias documentais**; qualquer decisão de instalar 8.8.3 continua sujeita ao gate de loader/FlexConfig e testes in-game.

