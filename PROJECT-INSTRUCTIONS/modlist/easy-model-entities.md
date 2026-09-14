# Easy Model Entities

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db814d915ef3b0d105e25e
- **Baseline física pré-update:** `easy_model_entities-neoforge-1.21.1-2.3.0.jar`; mod id `easy_model_entities`; campo `mod version` vazio na modlist física
- **Artefato alvo selecionado no CurseForge:** `easy_model_entities-neoforge-1.21.1-2.4.0.jar` — file `8847524`, Release, publicado em 10/09/2026
- **Data da atualização documental GitHub:** 2026-09-14

> **VERSIONAMENTO FAIL-CLOSED.** O número `2.4.0` identifica o **arquivo/release alvo**. A baseline física 2.3.0 tinha o campo interno de versão vazio na modlist fornecida; portanto este dossier não inventa a metadata runtime da 2.4.0 antes de inspecionar o novo JAR. Após instalação, reextrair `neoforge.mods.toml`/metadata e confirmar se a coluna continua vazia ou foi corrigida.

## Propriedades equivalentes do catálogo

- **Mod:** Easy Model Entities
- **Arquivo JAR alvo:** `easy_model_entities-neoforge-1.21.1-2.4.0.jar`
- **Versão de artefato alvo:** 2.4.0
- **Versão runtime/metadata alvo:** não verificada; não preencher por inferência
- **Baseline física auditada:** filename/publicação 2.3.0, metadata de versão vazia
- **Estado da pesquisa:** Verificado documentalmente; metadata/binário/runtime da 2.4.0 pendentes
- **Decisão:** Manter
- **Categoria:** Visual, QoL
- **Função:** Framework para transformar modelos Blockbench em entidades/model entities controláveis sem Java customizado, com comandos/API de servidor para model, texture, animation, opacity, light e estado renderizado sincronizado.
- **Dependências:** NeoForge 1.21.1. Integra visualmente com o stack de modelos/renderers; EMF 3.3.5 e ETF 7.2.1 estão presentes na baseline.
- **Sobreposição:** Sobreposição visual parcial com Entity Model Features, Entity Texture Features, Customizable Player Models e outros renderers. EME é provider de suas próprias model entities/state; não aplicar transforms de outro provider duas vezes.
- **Compatibilidade/Riscos:** Riscos de render transform duplicado, culling bounds, opacity/light state stale, animation state divergente, shader hurt/death overlay, texture override inválido, hand-item anchor incorreto e confundir filename/release com metadata runtime.
- **Observações:** 2.4.0 adiciona animation sequences de até 16 clips, API `playAnimationSequence`, blend `emissive`, render opt-in de itens nas mãos e anchors/fallbacks para modelos sem hand bones; também corrige clips explicitamente setados/tocados quando `body_type: static` ou `animation.mode: none`.
- **Procedência:** modlist.txt física baseline 2.3.0 + CurseForge oficial file 8847524/2.4.0 + changelog oficial 2.4.0 + documentação 2.1–2.3 já auditada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-model-entities
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para artefato alvo 2.4.0; sequence API, emissive, hand-item rendering/anchors e fix static/none integrados. Metadata runtime continua fail-closed até inspeção física.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **Autoridade física baseline:** `easy_model_entities-neoforge-1.21.1-2.3.0.jar` · mod id `easy_model_entities` · NeoForge 1.21.1 · coluna `mod version` vazia. **Alvo documental:** `easy_model_entities-neoforge-1.21.1-2.4.0.jar`. O identificador `2.4.0` é file/release version até que o JAR alvo seja inspecionado.

## 1. Papel no modpack
Easy Model Entities (EME) fornece uma camada para usar modelos criados no Blockbench como model entities/objetos renderizados controláveis sem exigir que cada modelo seja implementado como uma classe Java própria. A linha atual expõe comandos e API de servidor para escolher modelo, textura, animação e propriedades visuais sincronizadas.

## 2. Authority / ownership
- **EME:** state próprio de model entity, model/profile selecionado, texture override, animation state, opacity, light override, render options e anchors que registra.
- **Minecraft/NeoForge:** entidade/world state, save lifecycle e networking base.
- **Outros render systems:** seus próprios transforms/textures/models quando atuam em entidades compatíveis.

Não aplicar duas vezes a mesma pose/model transform por empilhar providers sem precedence.

## 3. Versionamento fail-closed
A modlist física confirma o JAR 2.3.0 e mod id `easy_model_entities`, porém deixa o campo de versão vazio. A publicação oficial identifica esse arquivo como 2.3.0.

O alvo 2.4.0 é confirmado pelo arquivo/release oficial `easy_model_entities-neoforge-1.21.1-2.4.0.jar`, file 8847524. **Não há ainda leitura da metadata interna desse JAR neste ciclo.**

Após atualização:
- confirmar mod id e version string reais;
- confirmar mixins/dependencies;
- reconciliar se a metadata vazia foi mantida/corrigida;
- não preencher o campo runtime por filename se o JAR continuar omitindo versão.

## 4. Model/profile pipeline
A linha 2.1+ possui API pública para trabalhar com model/profile definitions e validações/migrations correspondentes. O objetivo operacional é separar o arquivo/modelo visual do state persistente da entidade.

Integração própria deve passar pelos contratos públicos do mod em vez de editar internals ou NBT/SNBT presumido sem source pinado.

## 5. Animation state e sequences
A linha 2.1 estabilizou animações derivadas de clips Blockbench, com operações de set/play/stop/restart e state salvo/sincronizado.

A **2.4.0** adiciona:
- comando `animation play <target> sequence "<clips>"`;
- encadeamento de **até 16 clips**;
- API `playAnimationSequence` disponível nas superfícies client/server publicadas;
- envio da cadeia em **uma request** em vez de exigir múltiplas chamadas independentes.

Animation state deve continuar seguindo a authority definida pelo mod. Uma sequence não deve disparar commits duplicados por clip nem divergir entre clientes.

## 6. Fix de clips explícitos — 2.4.0
A 2.4.0 corrige um caso em que `body_type: static` e `animation.mode: none` ignoravam um clip explicitamente tocado/setado.

Regression gate:
- modelo com `body_type: static` + clip explicitamente solicitado;
- modelo com `animation.mode: none` + clip explicitamente solicitado;
- sequência contendo esses estados;
- reconnect/restart preservando somente o state que deve persistir.

Não generalizar o fix para outros animation bugs não citados pelo changelog.

## 7. Texture override
A API/comandos suportam alteração de texture override. Em 2.3.0 o comando de consulta foi renomeado de `texture query` para `texture get`, e foi adicionada chamada de API de servidor para ler o override atual.

URLs/identificadores de textura inválidos precisam falhar de forma controlada; não assumir que um recurso remoto existe só porque foi salvo no state.

## 8. Emissive blend — 2.4.0
A 2.4.0 adiciona blend de textura **`emissive`**:
- renderização fullbright;
- suporte a transparência;
- slots emissive documentados como nunca sujeitos a backface culling.

Esse recurso é presentation-only. Fullbright visual não altera light level real do mundo, spawn rules, visão de mobs ou gameplay salvo.

Regredir emissive com shaders, transparência, resource reload e modelos complexos para evitar overdraw/face-order artifacts.

## 9. Opacity
A 2.3.0 adicionou `display opacity set/clear/get` e state salvo/sincronizado. Opacity é apresentação; não deve tornar a entidade logicamente inexistente, sem colisão ou invulnerável a menos que outro sistema faça isso explicitamente.

## 10. Light override
A 2.3.0 adicionou `display light set/clear/get`. O light override é parte da apresentação/model rendering e é persistido/sincronizado.

Não confundir light override visual com light level real do bloco/mundo ou regras server-side de spawn.

## 11. Hand-item rendering — 2.4.0
A 2.4.0 adiciona **renderização de itens nas mãos via render options**, com comportamento **opt-in**: fica desligado salvo quando um mod/consumer a habilita.

Também passa a reconhecer nomes de parts:
- `left_item`;
- `right_item`;
- `left_hand`;
- `right_hand`.

Se o modelo não possui hand bone reconhecido, a 2.4.0 adiciona fallback automático de anchor usando arm/head/body conforme disponibilidade.

Boundary:
- item renderizado continua sendo apresentação do item/state real;
- anchor não pode duplicar o item real nem criar segunda equip authority;
- consumer deve habilitar a feature explicitamente.

## 12. Overlays de dano/morte
A 2.3.0 corrige overlays de hurt/death em modelos renderizados por outros mods. A linha 2.2 já havia tratado problemas de shader/flash overlays e transparência.

Isso continua regression gate, especialmente com o novo emissive blend e outros model providers.

## 13. Entity data serializers
A 2.2.0 corrigiu IDs de serializers Forge/NeoForge dependentes da ordem de carga. Essa categoria de bug é crítica porque mismatch de serializer pode provocar desync ou crash aparentemente não relacionado ao modelo.

Regression gate: dedicated server + dois clientes + relog/restart com model state persistente.

## 14. Culling / bounds
A linha 2.2 corrige culling bounds e melhora compatibilidade com EntityCulling. Modelos Blockbench podem exceder a bounding box visual padrão; culling incorreto pode fazer o modelo desaparecer fora de ângulos específicos.

Hitbox gameplay continua pertencendo ao entity provider; visual bounds não devem redefinir colisão por inferência.

## 15. Stack visual local
A modlist baseline contém:
- Entity Model Features `3.3.5`;
- Entity Texture Features `7.2.1`;
- Customizable Player Models `0.6.27a`;
- Easy NPC `7.11.0` pelo filename/publicação.

Esses sistemas não são automaticamente incompatíveis, mas todos podem tocar model/texture/pose surfaces. Precedence precisa ser testada por entidade/modelo concreto.

## 16. Relação com Easy NPC
Easy NPC 7.11.0 possui mudanças específicas para humanoid NPCs ignorarem custom player models/animations/poses em determinadas rotas. A linha Easy NPC posterior também torna EME 2.4.0+ relevante para integrações opcionais atuais.

Isso não prova integração universal; somente eleva prioridade de regressão quando EME 2.4.0 e Easy NPC atualizado estiverem simultaneamente instalados.

## 17. Client / Server
- state persistente de model/texture/animation/opacity/light: sincronizado/validado conforme authority do mod;
- animation sequence: request/state precisa convergir entre clientes;
- render/model baking/texture/emissive/hand items/shader: client-facing;
- comandos/API que alteram state compartilhado: validação server-side;
- renderer nunca deve ser exigido para dedicated-server boot.

## 18. Lifecycle
Validar:
- criação/spawn da model entity;
- model set/change;
- texture set/get/clear;
- animation play/stop/restart;
- sequence de até 16 clips;
- opacity/light set/get/clear;
- emissive on/off/resource reload;
- hand-item anchors/fallbacks;
- chunk unload/reload;
- save/restart;
- death/removal;
- reconnect multiplayer;
- mudança de dimensão quando aplicável.

## 19. Multiplayer / idempotência
Alterar model state por comando/API deve produzir um único commit. Dois clientes não podem disputar o mesmo state e deixar valores diferentes localmente. Reconnect deve reconstruir texture/animation/opacity/light e sequence state conforme contrato real.

`playAnimationSequence` precisa enviar/executar a cadeia sem múltiplos commits inesperados ou ordem divergente.

## 20. Riscos
1. aplicar model transform de dois providers;
2. texture override inválido/stale;
3. animation/sequence state divergir após relog;
4. opacity/light não persistirem;
5. hurt/death overlay incorreto com shaders;
6. emissive + transparência causar artifacts/overdraw;
7. hand-item anchor errado/duplicado;
8. culling bounds cortarem modelo grande;
9. serializer mismatch/order regression;
10. renderer client-only carregar no dedicated server;
11. tratar `2.4.0` como metadata física sem inspeção do novo JAR;
12. model state ser usado indevidamente como gameplay authority;
13. consumer habilitar hand-item rendering sem part/fallback adequado.

## 21. Matriz de testes
1. Nova modlist física confirma `easy_model_entities-neoforge-1.21.1-2.4.0.jar`.
2. Extrair metadata interna e registrar o version string real do alvo.
3. Dedicated server boot com EME + EMF + ETF.
4. Spawn de model entity simples e modelo grande.
5. Trocar modelo e texture; relog/restart.
6. Animation play/stop/restart e reconnect.
7. Sequence de 2, 16 e >16 clips: aceitar/rejeitar conforme contrato sem desync.
8. `body_type: static` + clip explícito funciona — fix 2.4.0.
9. `animation.mode: none` + clip explícito funciona — fix 2.4.0.
10. Emissive fullbright/transparência com shader on/off e resource reload.
11. Hand-item rendering permanece off por default e funciona quando opt-in.
12. `left_item/right_item/left_hand/right_hand` resolvem anchors corretos.
13. Modelo sem hand bone usa fallback sem crash/posição impossível.
14. Opacity 0/intermediária/1 e clear.
15. Light override mínimo/máximo e clear.
16. Hurt/death overlay com e sem shader.
17. Culling em distância/ângulos extremos.
18. Dois clientes vendo o mesmo state.
19. Easy NPC + model stack em cenário controlado.
20. Confirmar que emissive/light/opacity/hand-item presentation não cria autoridade de gameplay.

**Esta catalogação não afirma que esses testes foram executados.**

## 22. Evidências
- modlist física baseline: filename 2.3.0, mod id e campo de versão vazio;
- CurseForge oficial: `easy_model_entities-neoforge-1.21.1-2.4.0.jar`, file 8847524, Release, 10/09/2026;
- changelog 2.4.0: fix `body_type: static`/`animation.mode: none`, sequences até 16 clips, `playAnimationSequence`, blend emissive, hand-item rendering opt-in, part names e anchor fallback;
- changelogs 2.1.0–2.3.0: API/model/animation, serializers/culling/render fixes, texture get, opacity/light e sync.

> **Boundary canônico:** EME controla o **state de apresentação/model entity que registra**. O novo arquivo 2.4.0 está documentado como alvo, mas seu version string interno só será declarado após inspeção física.