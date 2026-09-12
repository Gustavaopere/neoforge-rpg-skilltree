# Easy Model Entities

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db814d915ef3b0d105e25e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Easy Model Entities
- **Arquivo JAR:** `easy_model_entities-neoforge-1.21.1-2.3.0.jar`
- **Versão 1.21.1:**
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, QoL
- **Função:** Framework para transformar modelos Blockbench em entidades/model entities controláveis sem Java customizado, com comandos/API de servidor para model, texture, animation, opacity, light e estado renderizado sincronizado.
- **Dependências:** NeoForge 1.21.1. A build física é identificável como 2.3.0 pelo filename e publicação oficial, porém a coluna `mod version` da modlist física está vazia. Integra visualmente com o stack de modelos/renderers; EMF 3.3.5 e ETF 7.2.1 estão presentes.
- **Sobreposição:** Sobreposição visual parcial com Entity Model Features 3.3.5, Entity Texture Features 7.2.1, Customizable Player Models e outros renderers. EME é provider de suas próprias model entities/state; não deve aplicar transforms de outro provider duas vezes.
- **Compatibilidade/Riscos:** Riscos de render transform duplicado com EMF/ETF/outros model systems, culling bounds incorreto, opacity/light state stale, animation state divergente, shader hurt/death overlay, texture override inválido e confundir versão do filename com metadata runtime. 2.2.x/2.3.0 corrigem serializers, culling e overlays.
- **Observações:** Fail-closed: `2.3.0` é versão do filename/publicação oficial, não valor extraído da coluna de versão da modlist. 2.3.0 adiciona/get/set/clear de opacity/light, texture query→get e persistência/sync desses estados; 2.1+ possui animation/model API pública.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `easy_model_entities-neoforge-1.21.1-2.3.0.jar`, mod id `easy_model_entities` e campo físico de versão vazio. Publicação oficial File 8706558/linha 2.3.0 sustenta a identificação da release e seu changelog.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/8706558
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — filename/release 2.3.0 com metadata física de versão vazia; model/entity state, animation, texture/opacity/light API, sync, render interop, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **Autoridade física:** `easy_model_entities-neoforge-1.21.1-2.3.0.jar` · mod id `easy_model_entities` · NeoForge 1.21.1. **A coluna `mod version` da modlist física está vazia.** O identificador `2.3.0` vem do filename e da publicação oficial correspondente, não de metadata runtime extraída.

## 1. Papel no modpack
Easy Model Entities (EME) fornece uma camada para usar modelos criados no Blockbench como model entities/objetos renderizados controláveis sem exigir que cada modelo seja implementado como uma classe Java própria. A linha atual expõe comandos e API de servidor para escolher modelo, textura, animação e propriedades visuais sincronizadas.

## 2. Authority / ownership
- **EME:** state próprio de model entity, model/profile selecionado, texture override, animation state, opacity, light override e opções de render que registra.
- **Minecraft/NeoForge:** entidade/world state, save lifecycle e networking base.
- **Outros render systems:** seus próprios transforms/textures/models quando atuam em entidades compatíveis.

Não aplicar duas vezes a mesma pose/model transform por empilhar providers sem precedence.

## 3. Versionamento fail-closed
A modlist física confirma o JAR `easy_model_entities-neoforge-1.21.1-2.3.0.jar` e mod id `easy_model_entities`, porém deixa o campo de versão vazio.

A publicação oficial para NeoForge 1.21.1 identifica esse arquivo como release 2.3.0. Nesta ficha, portanto, `2.3.0` é **identificação filename/publicação**, não leitura da metadata física.

## 4. Model/profile pipeline
A linha 2.1+ possui API pública estável para trabalhar com model/profile definitions e validações/migrations correspondentes. O objetivo operacional é separar o arquivo/modelo visual do state persistente da entidade.

Integração própria deve passar pelos contratos públicos do mod em vez de editar internals ou NBT/SNBT presumido sem source pinado.

## 5. Animation state
A linha 2.1 adiciona/estabiliza animações derivadas de clips Blockbench, com operações de set/play/stop/restart e state salvo/sincronizado.

Animation state deve ser server-owned quando persiste ou afeta comportamento compartilhado; a execução visual no cliente precisa refletir esse state, não criar outra fonte de verdade.

## 6. Texture override
A API/comandos suportam alteração de texture override. Em 2.3.0 o comando de consulta foi renomeado de `texture query` para `texture get`, e foi adicionada chamada de API de servidor para ler o override atual.

URLs/identificadores de textura inválidos precisam falhar de forma controlada; não assumir que um recurso remoto existe só porque foi salvo no state.

## 7. Opacity
A 2.3.0 adiciona `display opacity set/clear/get` e state de opacity salvo/sincronizado. Também existe default em render options.

Opacity é apresentação; não deve tornar a entidade logicamente inexistente, sem colisão ou invulnerável a menos que outro sistema faça isso explicitamente.

## 8. Light override
A 2.3.0 também adiciona `display light set/clear/get`. O light override é parte da apresentação/model rendering e é persistido/sincronizado.

Não confundir light override visual com light level real do bloco/mundo ou regras server-side de spawn.

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
- Easy NPC `7.11.0` pelo filename/publicação.

Esses sistemas não são automaticamente incompatíveis, mas todos podem tocar model/texture/pose surfaces. Precedence precisa ser testada por entidade/modelo concreto.

## 13. Relação com Easy NPC
Easy NPC 7.11.0 possui mudanças específicas para humanoid NPCs ignorarem custom player models/animations/poses em determinadas rotas, reduzindo interferência com model systems. Isso não prova uma integração EME universal; apenas cria uma superfície de regressão relevante quando ambos são usados para apresentação de NPCs.

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
10. tratar `2.3.0` como metadata física quando a coluna está vazia;
11. API drift entre exemplos antigos e 2.3.0;
12. model state ser usado indevidamente como gameplay authority.

## 18. Matriz de testes
1. Dedicated server boot com EME + EMF + ETF.
2. Spawn de model entity simples e modelo grande.
3. Trocar modelo e texture; relog/restart.
4. Animation play/stop/restart e reconnect.
5. Opacity 0/intermediária/1 e clear.
6. Light override mínimo/máximo e clear.
7. Hurt/death overlay com e sem shader.
8. Culling em distância/ângulos extremos.
9. Dois clientes vendo o mesmo state.
10. Resource reload sem perder state.
11. Easy NPC humanoid + model stack em cenário controlado.
12. Confirmar que visual light/opacity não alteram light level/collision gameplay.

**Esta catalogação não afirma que esses testes foram executados.**

## 19. Evidências
- modlist física canônica de 08/09/2026: filename, mod id e campo de versão vazio;
- publicação oficial Easy Model Entities para NeoForge 1.21.1: arquivo 2.3.0;
- changelogs 2.1.0–2.3.0: API/model/animation, serializers/culling/render fixes, texture get, opacity/light e sync.

> **Boundary canônico:** EME controla o **state de apresentação/model entity que registra**. Não transformar aparência em uma segunda autoridade de gameplay.
