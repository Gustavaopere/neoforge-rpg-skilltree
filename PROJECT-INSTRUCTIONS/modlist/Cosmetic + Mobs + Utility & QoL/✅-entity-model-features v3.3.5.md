# Entity Model Features

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#254**: JAR `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, SHA-1 `e78060b9a01bf41b5628bd45ce5ac741f68f092c`.

## Propriedades do registro

- **Mod:** Entity Model Features
- **Arquivo JAR:** `entity_model_features-3.3.5-1.21-neoforge.jar`
- **Versão 1.21.1:** `3.3.5`
- **Categoria:** Visual
- **Função:** Implementa Custom Entity Models no formato OptiFine/CEM para resource packs, incluindo modelos `.jem`/`.jpm`, animações, random models, player models e suporte a entidades/block entities compatíveis.
- **Dependências:** Entity Texture Features 7.2.1 é a dependência física requerida; runtime também inclui Entity Sound Features 0.8.2, EntityCulling 1.10.5 e EMF Compat Core/Create/Iron's Spells 2.0.0. Para os fixes completos da linha EMF 3.3.7–3.3.8, o upstream pede ETF atualizado; ETF 7.2.4 é a latest 1.21.1 localizada, enquanto o pack permanece em 7.2.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Client-only e sensível ao render/model pipeline. Runtime físico 3.3.5; upstream avançou 3.3.6→3.3.7→3.3.8→3.3.9. 3.3.8 introduziu um launch issue específico em 1.21.1/1.20.1, corrigido em 3.3.9; não promover para 3.3.8 isoladamente. Fixes de third-party render cancellation e shoulder parrots pedem ETF atualizado para cobertura completa.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/entity-model-features
- **Procedência:** modlist física atual confirma EMF 3.3.5 + ETF 7.2.1. CurseForge oficial revalidado em 02/10/2026 confirma a cadeia NeoForge 1.21.1 3.3.6, 3.3.7, 3.3.8 e 3.3.9; ETF 7.2.4 é a latest 1.21.1 e corrige shoulder parrots afetando EMF.
- **Observações:** Runtime físico permanece EMF 3.3.5. A promotion baseline segura da cadeia posterior é 3.3.9, não 3.3.8. Os deltas relevantes incluem limits de animation compilation, model variation/render-state fixes, texture overrides, arrow/trident ground state, player shoulder-parrot attachment/animation e launch fix para 1.21.1.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 02/10/2026 — EMF físico permanece 3.3.5. Releases 3.3.6→3.3.9 foram percorridas em ordem; 3.3.9 é o baseline mínimo da cadeia por corrigir o launch regression introduzido pela 3.3.8 em 1.21.1.
- **Decisão:** Sem decisão
- **Sobreposição:** EMF controla modelos/animações CEM. ETF controla texturas/regras; ESF controla sons. Fresh Animations/resource packs consomem essas capacidades. EMF Compat preserva poses de outros mods; CPM/EME são providers visuais distintos e exigem precedence.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `entity_model_features-3.3.5-1.21-neoforge.jar` · mod id `entity_model_features` · versão `3.3.5` · NeoForge 1.21.1 · **client-side**.
## 1. Papel no modpack
Entity Model Features (EMF) implementa o sistema **Custom Entity Models (CEM)** de resource packs em estilo OptiFine sem exigir OptiFine. Ele é o provider de modelos/animações CEM do stack visual.
## 2. Dependência ETF
O projeto declara **Entity Texture Features (ETF)** como required. ETF 7.2.1 está instalado e fornece, entre outras coisas, random-property/config/texture support usado pelo EMF.
Não remover ETF tratando-o como addon opcional enquanto EMF permanecer.
## 3. Formatos CEM
A documentação oficial cobre:
- `.jem` para entity models;
- `.jpm` para model parts;
- CEM animations;
- random models;
- modelos de entities e block entities capturados pelo model loader.
EMF também aceita diretórios/recursos próprios além da compatibilidade OptiFine conforme sua documentação.
A build física **3.3.5** possui dois fixes explícitos no changelog oficial: corrige layer models quebrados pela 3.3.4 quando múltiplas entidades do mesmo tipo estão presentes e corrige fallbacks do wool undercoat de baby sheep em versões anteriores a 26.1. Esses fixes pertencem à versão instalada e entram na regressão local de modelos em grupo/fallbacks.
## 4. Player models
Player CEM é suportado e pode ser animado/variado. Isso coloca EMF na mesma superfície visual de CPM, Easy Model Entities e animation bridges presentes no pack.
O player gameplay state continua pertencendo ao servidor/provider; EMF apenas transforma sua apresentação.
## 5. Modded entities
EMF pode suportar entidades modded cujas model factories entram pelo entity model loader esperado. Mods que usam render/model frameworks totalmente próprios podem não ser capturados.
O recurso de **model export** da GUI é a forma upstream de descobrir se um modelo é compatível e quais part names/pivots são válidos; não adivinhar nomes de parts pelo código vanilla.
## 6. Model export
A GUI permite exportar modelos compatíveis para `[MC_DIRECTORY]/emf/export/` e também possui ferramentas para descobrir/exportar modelos desconhecidos. Esse output é ferramenta de autoria/debug, não state persistente de gameplay.
## 7. Animation expressions
Além da sintaxe CEM/OptiFine, EMF adiciona variáveis e funções de animação próprias, incluindo estados como climbing/blocking/crawling, distância/fluid depth e helpers como `keyframe()`/`keyframeloop()` e easing functions.
Expressions são executadas no cliente e devem permanecer determinísticas/seguras para render; não usá-las para decidir dano, cooldown ou AI.
## 8. EMFAnimationApi
O projeto expõe `EMFAnimationApi` para outros mods registrarem funções/variáveis de animation. Essa é a superfície preferida para extensão quando suficiente; mixins em internals do renderer elevam o risco de version drift.
## 9. Stack EMF Compat local
O pack instala:
- EMF Compat Core 2.0.0;
- EMF Compat: Create 2.0.0;
- EMF Compat: Iron's Spells 2.0.0.
Esses modules capturam/restauram poses para impedir que EMF apague animações de outros providers. EMF continua dono apenas da camada CEM.
## 10. ETF / ESF
- **ETF 7.2.1:** random/emissive/custom textures e skin features;
- **ESF 0.8.2:** regras de som por properties e utilidades que podem conversar com ETF/EMF.
Os três formam um stack, mas cada um tem ownership distinto.
## 11. EntityCulling
EntityCulling 1.10.5 está instalado e é recomendado upstream para reduzir custo de renderização, especialmente em packs de animação pesados. Culling não deve alterar animation state; apenas decidir se o modelo precisa ser desenhado naquele frame.
## 12. OptiFine parity e limites
EMF busca alta paridade com OptiFine CEM, mas a documentação lista diferenças/ausências, incluindo **sprites não suportados** e possíveis diferenças em algumas animation variables, sobretudo para block entities/non-living entities.
Não assumir equivalência total de qualquer pack CEM sem teste.
## 13. Incompatibilidades documentadas
O projeto lista como incompatíveis:
- OptiFine;
- OptiFabric;
- dorianpb's CEM.
Enhanced Block Entities e Physics Mod possuem apenas workarounds/compatibilidade limitada em determinadas superfícies.
## 14. Client / Server
Todo model baking, animation expression, resource loading e render é client-side. Dedicated server não deve depender de EMF para validar entity state.
Servidor envia o state normal da entidade; cada cliente escolhe/renderiza o modelo conforme seus resources/config.
## 15. Lifecycle
Validar:
- resource-pack load/reload;
- troca de pack;
- world join/rejoin;
- entity spawn/despawn;
- player respawn;
- dimension change;
- model export;
- first/third person;
- início/fim de animação de consumers;
- update de EMF/ETF/consumer.
## 16. Multiplayer
Clientes podem usar resource packs diferentes e ainda compartilhar o mesmo gameplay state. Outros players devem ser animados a partir do state sincronizado; uma diferença visual não pode produzir divergence server-side.
## 17. Riscos
1. model part name/pivot drift;
2. resource pack CEM incompatível;
3. animation expression inválida;
4. pose aplicada duas vezes por EMF + outro animation provider;
5. Core/consumer de EMF Compat em versão divergente;
6. ETF ausente ou incompatível;
7. resource reload deixar cache/model stale;
8. first/third person divergirem;
9. shader/render framework revelar z-fighting/transparency issues;
10. model modded usar renderer não capturado pelo EMF;
11. animation-heavy pack aumentar frame time;
12. atribuir gameplay authority ao renderer.
## 18. Matriz de testes
1. Cliente com EMF 3.3.5 + ETF 7.2.1.
2. Resource pack CEM vanilla conhecido.
3. Fresh Animations/player animation pack usado pelo perfil real.
4. Modelo de entidade modded exportável.
5. Player CEM em first/third person.
6. EMF Compat Create e Iron's Spells separadamente.
7. CPM/Easy Model Entities coexistindo em cenários controlados.
8. Resource reload e troca de pack durante sessão.
9. EntityCulling ligado/desligado em cena pesada.
10. Multiplayer observando outro player/entity animado.
**Esta catalogação não afirma que esses testes foram executados.**
## 19. Evidências
- modlist física atual: JAR/mod id/version + ETF/ESF/EntityCulling/EMF Compat instalados;
- CurseForge oficial da 3.3.5: fix de layer models com múltiplas entidades do mesmo tipo e fix de baby sheep wool undercoat fallback;
- GitHub oficial `Entity_Model_Features` / `FEATURES.md`: CEM, formats, player/modded support, export, animation variables/functions, API e incompatibilidades;
- CurseForge/Modrinth oficiais: client-side e requirement ETF.
> **Boundary canônico:** EMF é authority da **representação CEM/model animation**. Ele não possui authority sobre AI, combate, spell cast ou entity state do servidor.

## 20. Atualizações upstream 3.3.6 → 3.3.9 — não instaladas

A autoridade física continua em **EMF 3.3.5**. Para NeoForge 1.21.1, a sequência posterior é **3.3.6 → 3.3.7 → 3.3.8 → 3.3.9**.

### 3.3.6
- corrige o limite superior da matemática compilada por ASM para animações grandes, evitando `MethodTooLargeException`;
- corrige caso em que a primeira model variation de cada frame podia quebrar;
- corrige export de modelos de villagers.

### 3.3.7
- corrige texture override quando o model não declara todas as parts ou usa `attach=true` com o mesmo override nas parts declaradas;
- corrige third-party mixins que cancelavam certos renders e contaminavam animation values de renders seguintes; o upstream pede ETF atualizado para o fix completo;
- corrige `is_in_ground` para arrows/tridents no chão, antes funcionando apenas em paredes/tetos;
- corrige localização Spanish Argentina.

O changelog geral também cita variáveis quebradas apenas em **1.21.9+**; esse subitem não é atribuído ao runtime Minecraft 1.21.1.

### 3.3.8
- adiciona attachment points `parrot_left` e `parrot_right` para controlar shoulder parrots no player model, inclusive por animação;
- adiciona opção de ajuste automático dos parrots à posição do shoulder em custom player animations;
- variável `id` dos parrots passa a retornar `0` no ombro esquerdo e `1` no direito;
- corrige `head_yaw` e `head_pitch` dos shoulder parrots, também exigindo ETF atualizado para cobertura completa.

O fix `is_on_shoulder` descrito pelo upstream é específico de **1.21.9+** e não é promovido como delta de 1.21.1.

### 3.3.9 — baseline obrigatório
- corrige um **launch issue em Minecraft 1.21.1 e 1.20.1 introduzido pela 3.3.8**.

Consequência operacional: **não atualizar o pack para EMF 3.3.8**. Se esta cadeia for promovida, usar no mínimo 3.3.9.

### ETF coordenado
O pack possui ETF **7.2.1**. A linha 1.21.1 já possui ETF **7.2.4**, cujo changelog corrige shoulder parrots que afetavam EMF e adiciona properties `usingShaders` e `resourcepack`. Como EMF 3.3.7/3.3.8 explicitamente pedem ETF atualizado para fixes completos, testar a promoção como par EMF+ETF, não EMF isolado.

### Gate de promoção 3.3.5 → 3.3.9
- [ ] EMF 3.3.9 + ETF compatível iniciam sem o launch regression da 3.3.8.
- [ ] Resource packs CEM/Fresh Animations reais carregam após resource reload.
- [ ] Model variations não contaminam a primeira entidade/frame.
- [ ] Third-party render cancellation não deixa animation values stale no render seguinte.
- [ ] Texture overrides com `attach=true` e parts incompletas funcionam.
- [ ] Arrow/trident no chão reporta `is_in_ground` corretamente.
- [ ] Shoulder parrots acompanham custom player model/animation; left/right `id` permanece determinístico.
- [ ] `head_yaw`/`head_pitch` dos parrots funcionam com ETF atualizado.
- [ ] EMF Compat Create/Iron's Spells e EntityCulling continuam sem model/pose conflict.
- [ ] Shader profile real do pack é testado junto ao novo ETF.

Fontes upstream: CurseForge EMF NeoForge 1.21.1 releases 3.3.6–3.3.9; 3.3.9 file ID 8909425. ETF 7.2.4 NeoForge 1.21.1 file ID 8908931. Nenhum teste acima foi executado nesta atualização documental.

## 21. Atualizações EMF 3.3.10 → 3.3.11 — 10/10/2026

**Físico:** entity_model_features-3.3.5-1.21-neoforge.jar / 3.3.5 e ETF 7.2.1. O dossiê anterior cobre **3.3.6 → 3.3.7 → 3.3.8 → 3.3.9**, incluindo a regressão de launch da 3.3.8 para 1.21.1 e seu fix em 3.3.9.

### 3.3.10 — October 2026
- Corrige animações de entidades dentro de GUI (por exemplo paper dolls).
- Otimiza carregamento/compilação de animações grandes, com potencial de diminuir duração de resource reload/primeiro load.
- Corrige cubes ausentes em modelos forçados ao vanilla e modelos com attach=true.
- Changelog também menciona is_in_item_frame e mixin crash restritos a Minecraft 1.21.9+, **não extrapolar esses fixes para 1.21.1**.

### 3.3.11 — October 2026
- Corrige keyframeloop() não funcionando ao usar compiled maths.
- Reduz log spam em falha de modelo menor porém comum.

**Cadeia completa:** 3.3.5 → .6 → .7 → .8 (launch regression) → .9 (fix) → .10 → .11. Se houver atualização, escolher versão mais recente 3.3.11, e **coordenar com ETF atualizado**, que também possui cadeia upstream própria (item #56 desta rodada). Sem confundir presença de client-side shader/resource pack com server gameplay state.

**QA:** boot client com EMF/ETF, EMF Compat Create/Iron's/Core, Fresh Animations packs, GUI-entities, modelos vanilla forced, attach cubes, keyframeloop compilado, logs, mem/performance reload F3+T, shoulder parrots, shader/culling e observers multiplayer. Não tratar melhoria upstream como medição do pack.

**Fonte primária:** https://github.com/Traben-0/Entity_Model_Features/blob/master/CHANGELOG.MD ; https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/all?version=1.21.1

**Estado:** 3.3.11 upstream não instalado; último físico continua 3.3.5.

## 21. Atualização EMF 3.3.10 → 3.3.11 — 10/10/2026

**Autoridade física:** `entity_model_features-3.3.5-1.21-neoforge.jar` / 3.3.5, com **ETF 7.2.1** físico. A seção 20 já registra a sequência **3.3.6 → 3.3.7 → 3.3.8 → 3.3.9** e os bugs críticos. O CurseForge publica agora **3.3.10 (04/10)** e **3.3.11 (05/10)** também para NeoForge **Minecraft 1.21.1**, nome de distribuição `3.3.11-neoforge-1.21`.

### 3.3.10 — alterações do changelog oficial
- Corrige problemas em **entidades animadas em GUIs**, incluindo paper-doll mods.
- **Otimiza carregamento/compilação de animações**, para reduzir custo de load inicial e resource reload com animações grandes; a melhoria não é benchmark comprovado nesta instância.
- Corrige **cubos ausentes ao forçar model vanilla** ou com `attach=true`.
- O fix de `is_in_item_frame` sempre falso e o mixin crash mencionado na mesma versão são **específicos de Minecraft 1.21.9+ / 1.21.9–1.21.10**, sem evidência de que afetem 1.21.1.

### 3.3.11 — correções
- Corrige **`keyframeloop()` com matemática compilada**, importante para scripts de animação OptiFine/CEM complexos.
- Reduz logs repetitivos de um **problema comum mas menor em modelos**.

**Cadeia integral da versão física:** 3.3.5 → 3.3.6 → 3.3.7 → 3.3.8 → 3.3.9 → **3.3.10 → 3.3.11**. A 3.3.8 causou regressão de **boot em 1.21.1** resolvida na 3.3.9; **não promover 3.3.8 isoladamente**. Para obter toda a correção de shoulder parrots/third-party render, promover em conjunto com ETF compatível, verificando requisitos do ETF na rodada #56.

**Gates:** resource packs Fresh Animations/Excalibur e custom CEM, paper-doll GUI, models forçados vanilla/attach, loops de animação e `keyframeloop()`, mass reload F3+T, client boot, ETF 7.2.4+ conforme manifest, render mods Entity Culling 1.10.5/1.11.3, Iris shaders e consumo de CPU em cenas com muitas entidades. Não confundir carregamento do dossiê ou CI com teste visual.

**Fontes:** https://github.com/Traben-0/Entity_Model_Features/blob/master/CHANGELOG.MD ; https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/all?version=1.21

**Estado:** 3.3.11 upstream, **3.3.5 fisicamente comprovada**; testes de runtime pendentes.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** [EMF] Entity Model Features [Fabric & Forge]
- **Último candidato 1.21.1 / NeoForge localizado:** **3.3.11 (NeoForge 1.21.1, 05/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Testar com ETF, EMF Compat Core, Fresh Animations/resource packs, ImmediatelyFast, shaders e reload; manter estritamente client-side quando aplicável.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/entity-model-features

## Consolidação oficial das releases 3.3.6–3.3.12 — 23/10/2026

**Autoridade física preservada:** `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, posição #254 na modlist física histórica. **Projeto oficial:** [EMF] Entity Model Features [Fabric & Forge], CurseForge **844662**, mantenedor **Traben**. Categorias oficiais **Cosmetic + Mobs + Utility & QoL**, exatamente o diretório presente; o projeto lista ambiente **Client**. **Última Release na combinação NeoForge 1.21.1 em 23/10/2026: `3.3.12-neoforge-1.21`, de 15/10/2026** (não 3.3.11). O nome de distribuição mantém `1.21`, mas a página oficial marca explicitamente o artefato como compatível com **Minecraft 1.21 e 1.21.1**.

### Matriz de publicações, sem inventar versões não distribuídas

| Release NeoForge 1.21.1 | Data publicada | Mudanças declaradas no changelog oficial |
|---|---|---|
| **3.3.6** | 16/09 | Corrige limites de tamanho da matemática ASM compilada (`MethodTooLargeException`), falha da primeira variação de modelo a cada frame e exportação de modelos de villagers. A nota também cita suporte a Minecraft 26.3, **alvo diferente**. |
| **3.3.7** | 17/09 | Corrige override de textura quando partes não são todas declaradas ou se usa `attach=true`; impede que outros mixins interrompendo renders contaminem valores de animação em renders seguintes (**requer ETF atualizado para correção completa**); corrige `is_in_ground` para flechas/tridentes caídos no chão; corrige tradução es_AR. Outras variáveis de item/GUI do changelog têm limitação **Minecraft 1.21.9+**. |
| **3.3.8** | 18/09 | Adiciona `parrot_left`/`parrot_right` e attachment points animáveis para papagaios nos ombros; opcionalmente ajusta sua posição ao modelo animado do jogador; ID determinístico ombro esquerdo = 0, direito = 1; corrige `head_yaw`/`head_pitch` de papagaios (também requer ETF atualizado). Fix `is_on_shoulder` é indicado para **1.21.9+**. **Esta versão introduziu regressão de lançamento em 1.21.1**, como reconhecido na 3.3.9. |
| **3.3.9** | 18/09 | Corrige **falha de inicialização em Minecraft 1.21.1/1.20.1 introduzida pela 3.3.8**; versão 3.3.8 não deve ser considerada destino seguro isolado para o pack. |
| **3.3.10** | 04/10 | Corrige entidades animadas na GUI (inclusive paper dolls), otimiza carregamento de animações grandes e resource reload, recupera cubos ausentes em modelos forçados a vanilla ou `attach=true`. Fix de `is_in_item_frame` e mixin crash mencionados na mesma nota são específicos de **1.21.9–1.21.10**, não atribuídos à 1.21.1. |
| **3.3.11** | 05/10 | Corrige `keyframeloop()` quando a matemática é compilada e reduz log spam causado por erro pequeno porém frequente de modelo. |
| **3.3.12** | 15/10 | **Otimizações adicionais em animações ASM**, declaradas pelo autor como aumento perceptível de FPS em animações grandes. O changelog **não traz percentuais, métricas ou promessa de desempenho do modpack**; não inventar benchmark. |

**Fontes primárias de escopo e sequência:** catálogo oficial filtrado https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/all?version=1.21.1 ; changelog versionado do projeto https://github.com/Traben-0/Entity_Model_Features/blob/master/CHANGELOG.MD ; artefatos exatos para NeoForge 1.21.1: 3.3.6 https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/8892054 , 3.3.7 https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/8900925 , 3.3.9 https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/8909425 , 3.3.11 https://www.curseforge.com/minecraft/mc-mods/entity-model-features/files/9064942 . A listagem inclui também a versão 3.3.12 para NeoForge 1.21.1.

### Impacto de dependências e compatibilidade com o pack

- **Entity Texture Features (ETF)** permanece fisicamente documentado na versão **7.2.1**, enquanto o dossier anterior aponta **7.2.4** como atualização disponível. As correções completas de render cancel/shoulder parrots exigem **ETF atualizado** segundo o changelog EMF; consultar a versão/manifesto real do ETF candidato antes de atualizar conjuntamente, **sem alterar sua ficha aqui**.
- O snapshot contém EMF Compat Core/Create/Iron's Spells, EntityCulling e recursos CEM/OptiFine. Os modelos e animações (inclusive Fresh Animations) passam por renderização client-side; a presença do mod no servidor não demonstra ganho funcional nem requer mudança de autoridade da entidade.
- A otimização 3.3.12 mira animações ASM grandes. Resultado de FPS, possível efeito com shaders, performance de resource reload e consumo de memória são **hipóteses de teste**, não medições feitas nesta revisão.

### Gates de promoção (não executados)
- [ ] Fazer backup de configs e resource packs; confirmar no JAR candidato NeoForge 1.21.1 o mod id/metadata, dependência ETF e outras relações.
- [ ] Iniciar cliente com EMF 3.3.12, ETF coordenado, EntityCulling e EMF Compat Core/Create/Iron's Spells. Verificar ambiente servidor separado sem introduzir carregamento indevido de classes cliente.
- [ ] Exercitar Fresh Animations e CEM `.jem`/`.jpm`, shoulder parrots, custom player models, `attach=true`, primeira variação por frame, ciclos `keyframeloop()` e modelos vanilla forçados.
- [ ] Verificar Paper Doll/inventory GUIs, texture overrides, tridentes/flechas caídos, mixins de terceiros que cancelam renderizações e ausência de animation state stale.
- [ ] Comparar um perfil 3.3.5 vs 3.3.12 com mesmas configs, cenas com animações ASM grandes, resource reload F3+T e shaders habilitados/desabilitados. Registrar FPS/frame time e logs reais, sem estimativas inventadas.
- [ ] Verificar regressão de boot da 3.3.8 resolvida na 3.3.9, multiplayer observers e eventuais conflicts com mods de first person/battle renderer.

**Resultado:** todas as **sete releases posteriores à 3.3.5** (3.3.6–3.3.12) estão discriminadas por versão com changelogs oficiais e limites específicos de Minecraft. A última publicação foi atualizada para **3.3.12**. **Pesquisa do histórico upstream concluída para o lote; item 76 pode ser retirado da fila de investigação de releases**. Isso **não valida nem promove** o JAR 3.3.12 no modpack; a instalação, adequação do ETF e QA gráfico ainda exigem testes específicos. O filename `✅-entity-model-features v3.3.5.md`, versão e JAR físico permanecem intactos.
