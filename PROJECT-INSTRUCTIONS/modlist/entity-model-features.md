# Entity Model Features

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81de8e31ef28386819c1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Entity Model Features
- **Arquivo JAR:** `entity_model_features-3.3.5-1.21-neoforge.jar`
- **Versão 1.21.1:** 3.3.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Implementa Custom Entity Models no formato OptiFine/CEM para resource packs, incluindo modelos `.jem`/`.jpm`, animações, random models, player models e suporte a entidades/block entities compatíveis.
- **Dependências:** Entity Texture Features 7.2.1 é dependência funcional requerida pelo projeto e está instalada. Runtime local também inclui Entity Sound Features 0.8.2, EntityCulling 1.10.5 e EMF Compat Core/Create/Iron's Spells 2.0.0.
- **Sobreposição:** EMF controla modelos/animações CEM. ETF controla texturas/regras; ESF controla sons. Fresh Animations/resource packs consomem essas capacidades. EMF Compat preserva poses de outros mods; CPM/EME são providers visuais distintos e exigem precedence.
- **Compatibilidade/Riscos:** Client-only e sensível ao render/model pipeline. Incompatível com OptiFine/OptiFabric e dorianpb's CEM; recursos de Physics/Enhanced Block Entities têm apenas compatibilidade limitada/workarounds. Riscos locais: pose ownership com CPM/Easy Model Entities/EMF Compat, model part drift após update de mods, resource-pack errors, animation conflicts e custo de packs pesados.
- **Observações:** Runtime físico é 3.3.5; referência antiga 3.2.4 foi removida. O projeto suporta CEM, player CEM, modded models quando passam pelo entity model loader, model export e `EMFAnimationApi`; sprites OptiFine não são suportados. Esta ficha usa documentação oficial atual sem projetar changelog não pinado sobre 3.3.5.
- **Procedência:** Modlist física canônica de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, versão 3.3.5 e SHA-1 e78060b9a01bf41b5628bd45ce5ac741f68f092c. GitHub oficial/FEATURES.md sustenta formatos, animation API e compatibilidade.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/entity-model-features
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — EMF 3.3.5; CEM/JEM/JPM, animations/random models, player/modded models, export/API, ETF dependency, compat stack, lifecycle, risks and tests cataloged.
- **Histórico da decisão:**
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
- modlist física canônica: JAR/mod id/version/hash + ETF/ESF/EntityCulling/EMF Compat instalados;
- GitHub oficial `Entity_Model_Features` / `FEATURES.md`: CEM, formats, player/modded support, export, animation variables/functions, API e incompatibilidades;
- CurseForge/Modrinth oficiais: client-side e requirement ETF.

> **Boundary canônico:** EMF é authority da **representação CEM/model animation**. Ele não possui authority sobre AI, combate, spell cast ou entity state do servidor.
