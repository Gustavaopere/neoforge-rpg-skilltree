# Easy Model Entities

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#238**: JAR `easy_model_entities-neoforge-1.21.1-2.4.0.jar`, mod id `easy_model_entities`, runtime metadata **vazia**, SHA-1 `f5bc1927a5b69cc0fc1ed04a9f047e12365cb9a1`.

## Propriedades do registro

- **Mod:** Easy Model Entities
- **Arquivo JAR:** `easy_model_entities-neoforge-1.21.1-2.4.0.jar`
- **Categoria:** Visual, QoL
- **Função:** Framework para transformar modelos Blockbench em entidades/model entities controláveis sem Java customizado, com comandos/API de servidor para model, texture, animation, opacity, light e estado renderizado sincronizado.
- **Dependências:** NeoForge 1.21.1. A build física é identificável como 2.4.0 pelo filename e publicação oficial, porém a coluna `mod version` da modlist física está vazia. Integra visualmente com o stack de modelos/renderers; EMF 3.3.5 e ETF 7.2.1 estão presentes.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de render transform duplicado com EMF/ETF/outros model systems, culling bounds incorreto, opacity/light state stale, animation state divergente, shader hurt/death overlay, texture override inválido e confundir versão do filename com metadata runtime. 2.2.x/2.3.0 corrigem serializers, culling e overlays.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/8706558
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `easy_model_entities-neoforge-1.21.1-2.4.0.jar`, mod id `easy_model_entities`, SHA-1 `f5bc1927a5b69cc0fc1ed04a9f047e12365cb9a1` e campo de versão runtime vazio. A publicação/changelog oficial 2.4.0 fornece a identidade da build e seus deltas.
- **Observações:** Fail-closed preservado: a propriedade de versão runtime permanece sem valor porque a metadata física não declara versão. O artefato instalado é agora 2.4.0; esta build adiciona sequences de animação, `playAnimationSequence`, blend emissive, hand-item rendering/anchors e corrige clips explícitos com `body_type: static`/`animation.mode: none`.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #237: artefato físico atualizado para `easy_model_entities-neoforge-1.21.1-2.4.0.jar`. A coluna `mod version` da modlist continua vazia; `2.4.0` é identidade do filename/build/publicação, não metadata runtime inferida.
- **Decisão:** Manter
- **Sobreposição:** Sobreposição visual parcial com Entity Model Features 3.3.5, Entity Texture Features 7.2.1, Customizable Player Models e outros renderers. EME é provider de suas próprias model entities/state; não deve aplicar transforms de outro provider duas vezes.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Autoridade física:** `easy_model_entities-neoforge-1.21.1-2.4.0.jar` · mod id `easy_model_entities` · NeoForge 1.21.1. **A coluna ****`mod version`**** da modlist física está vazia.** O identificador `2.4.0` vem do filename/build e da publicação oficial correspondente, não de metadata runtime extraída.
## 1. Papel no modpack
Easy Model Entities (EME) fornece uma camada para usar modelos criados no Blockbench como model entities/objetos renderizados controláveis sem exigir que cada modelo seja implementado como uma classe Java própria. A linha atual expõe comandos e API de servidor para escolher modelo, textura, animação e propriedades visuais sincronizadas.
## 2. Authority / ownership
- **EME:** state próprio de model entity, model/profile selecionado, texture override, animation state, opacity, light override e opções de render que registra.
- **Minecraft/NeoForge:** entidade/world state, save lifecycle e networking base.
- **Outros render systems:** seus próprios transforms/textures/models quando atuam em entidades compatíveis.
Não aplicar duas vezes a mesma pose/model transform por empilhar providers sem precedence.
## 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_model_entities-neoforge-1.21.1-2.4.0.jar` e mod id `easy_model_entities`, porém deixa o campo de versão runtime vazio.
A publicação oficial para NeoForge 1.21.1 identifica esse arquivo como release 2.4.0. Nesta ficha, portanto, `2.4.0` é **identificação filename/build/publicação**, não leitura de metadata runtime.
A build física 2.4.0 adiciona sequences de animação de até 16 clips e API `playAnimationSequence`, blend `emissive`, renderização opt-in de itens nas mãos e anchors/fallbacks para modelos sem hand bones, além de corrigir clips explicitamente selecionados quando `body_type: static` ou `animation.mode: none`.
## 4. Model/profile pipeline
A linha 2.1+ possui API pública estável para trabalhar com model/profile definitions e validações/migrations correspondentes. O objetivo operacional é separar o arquivo/modelo visual do state persistente da entidade.
Integração própria deve passar pelos contratos públicos do mod em vez de editar internals ou NBT/SNBT presumido sem source pinado.
## 5. Animation state
A linha 2.1 adiciona/estabiliza animações derivadas de clips Blockbench, com operações de set/play/stop/restart e state salvo/sincronizado.
Na build física 2.4.0, sequences permitem encadear até 16 clips e a API expõe `playAnimationSequence`. A correção de clips explícitos com `body_type: static` ou `animation.mode: none` torna essas combinações um regression gate direto.
Animation state deve ser server-owned quando persiste ou afeta comportamento compartilhado; a execução visual no cliente precisa refletir esse state, não criar outra fonte de verdade.
## 6. Texture override
A API/comandos suportam alteração de texture override. Em 2.3.0 o comando de consulta foi renomeado de `texture query` para `texture get`, e foi adicionada chamada de API de servidor para ler o override atual.
URLs/identificadores de textura inválidos precisam falhar de forma controlada; não assumir que um recurso remoto existe só porque foi salvo no state.
## 7. Opacity
A 2.3.0 adiciona `display opacity set/clear/get` e state de opacity salvo/sincronizado. Também existe default em render options.
Opacity é apresentação; não deve tornar a entidade logicamente inexistente, sem colisão ou invulnerável a menos que outro sistema faça isso explicitamente.
## 8. Light override, emissive e hand items
A 2.3.0 adiciona `display light set/clear/get`. O light override é parte da apresentação/model rendering e é persistido/sincronizado.
A build física 2.4.0 acrescenta blend de textura `emissive` e renderização opt-in de itens nas mãos, com part names/anchors e fallback para modelos sem hand bones. Essas superfícies são visuais e precisam ser testadas com shaders/model providers do pack.
Não confundir light override/emissive visual com light level real do bloco/mundo ou regras server-side de spawn.
## 9. Overlays de dano/morte
A 2.3.0 corrige overlays de hurt/death em modelos renderizados por outros mods. A linha 2.2 já havia tratado problemas de shader/flash overlays e transparência.
Isso torna dano/morte + shader stack uma regressão concreta, especialmente quando outros model providers participam do mesmo entity render.
## 10. Entity data serializers
A 2.2.0 corrige IDs de serializers Forge/NeoForge dependentes da ordem de carga. Essa categoria de bug é crítica porque mismatch de serializer pode provocar desync ou crash aparentemente não relacionado ao modelo.
Regression gate: dedicated server + dois clientes + relog/restart com model state persistente.
## 11. Culling / bounds
A linha 2.2 corrige culling bounds e melhora compatibilidade com EntityCulling. Modelos Blockbench podem exceder a bounding box visual padrão; culling incorreto pode fazer o modelo desaparecer fora de ângulos específicos.
Hitbox gameplay continua pertencendo ao entity provider; visual bounds não devem redefinir colisão por inferência.
## 12. Stack visual local
A modlist atual contém:
- Entity Model Features `3.3.5`;
- Entity Texture Features `7.2.1`;
- Customizable Player Models `0.6.27a`;
- Easy NPC `7.12.1` pelo filename/build/publicação, com metadata runtime não declarada.
Esses sistemas não são automaticamente incompatíveis, mas todos podem tocar model/texture/pose surfaces. Precedence precisa ser testada por entidade/modelo concreto.
## 13. Relação com Easy NPC
Easy NPC 7.12.1 herda os ajustes para humanoid NPCs ignorarem custom player models/animations/poses em determinadas rotas e, na linha 7.12.x, a integração opcional com Easy Model Entities exige **EME 2.4.0+**. O pack atual satisfaz esse piso pelo artefato físico 2.4.0.
Isso não prova compatibilidade visual universal; continua sendo uma superfície de regressão relevante quando ambos são usados para apresentação de NPCs.
## 14. Client / Server
- state persistente de model/texture/animation/opacity/light: precisa ser sincronizado e validado pelo lado que o mod define como authority;
- render/model baking/texture/shader: client-facing;
- comandos/API que alteram state compartilhado: executar com validação no servidor;
- renderer nunca deve ser exigido para dedicated-server boot.
## 15. Lifecycle
Validar:
- criação/spawn da model entity;
- model set/change;
- texture set/get/clear;
- animation play/stop/restart;
- opacity/light set/get/clear;
- chunk unload/reload;
- save/restart;
- death/removal;
- resource reload;
- reconnect multiplayer;
- mudança de dimensão quando a entidade pode atravessar portais.
## 16. Multiplayer / idempotência
Alterar model state por comando/API deve produzir um único commit. Dois clientes não podem disputar o mesmo state e deixar valores diferentes localmente. Reconnect deve reconstruir texture/animation/opacity/light a partir do state sincronizado.
## 17. Riscos
1. aplicar model transform de dois providers;
2. texture override inválido/stale;
3. animation state divergir após relog;
4. opacity/light não persistirem;
5. hurt/death overlay incorreto com shaders;
6. culling bounds cortarem modelo grande;
7. serializer mismatch/order regression;
8. transparent faces renderizarem incorretamente;
9. renderer client-only carregar no dedicated server;
10. tratar `2.4.0` como metadata runtime quando a coluna está vazia;
11. API drift entre exemplos antigos e 2.4.0;
12. model state ser usado indevidamente como gameplay authority.
## 18. Matriz de testes
1. Dedicated server boot com EME + EMF + ETF.
2. Spawn de model entity simples e modelo grande.
3. Trocar modelo e texture; relog/restart.
4. Animation play/stop/restart, sequence de múltiplos clips e reconnect.
5. Opacity 0/intermediária/1 e clear.
6. Light override mínimo/máximo e clear; emissive blend e hand-item anchors/fallbacks.
7. Hurt/death overlay com e sem shader.
8. Culling em distância/ângulos extremos.
9. Dois clientes vendo o mesmo state.
10. Resource reload sem perder state.
11. Easy NPC humanoid + model stack em cenário controlado.
12. Confirmar que visual light/opacity não alteram light level/collision gameplay.
**Esta catalogação não afirma que esses testes foram executados.**
## 19. Evidências
- modlist física atual de 21/09/2026: `easy_model_entities-neoforge-1.21.1-2.4.0.jar`, mod id `easy_model_entities`, SHA-1 `f5bc1927a5b69cc0fc1ed04a9f047e12365cb9a1` e campo de versão runtime vazio;
- publicação oficial Easy Model Entities para NeoForge 1.21.1: artefato físico 2.4.0;
- changelogs 2.1.0–2.4.0: API/model/animation, serializers/culling/render fixes, texture get, opacity/light/sync e, na 2.4.0, animation sequences, emissive blend e hand-item anchors.
> **Boundary canônico:** EME controla o **state de apresentação/model entity que registra**. Não transformar aparência em uma segunda autoridade de gameplay.
