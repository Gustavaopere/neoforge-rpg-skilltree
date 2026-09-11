# Particular Reforged

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81249ab4c1e0b296cc0b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `particular-1.21.1-NeoForge-1.5.7.jar`, mod id `particular`, runtime `1.5.7`, mixins `particular.mixins.json`, `particular.neoforge.mixins.json`, `particular.sable.mixins.json`, `particular.tfc.mixins.json`; Sable `2.0.5` e Create Aeronautics `1.3.2` confirmados; TFC ausente como top-level
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Particular 1.5.7 e Sable/Aeronautics estão presentes; o mixin TFC existe dentro do JAR, mas não há TFC top-level. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Particular Reforged
- **Arquivo JAR:** `particular-1.21.1-NeoForge-1.5.7.jar`
- **Versão 1.21.1:** 1.5.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Camada client-side de ambience/particles ambientais, com splashes, wakes e outros efeitos contextuais; 1.5.7 inclui integração/fix específico para Sable ships/boats.
- **Dependências:** Cliente NeoForge 1.21.1. Sable é integração opcional relevante e está presente no pack; nenhuma hard dependency externa adicional foi confirmada para esta build.
- **Sobreposição:** Complementa Particle Rain e Particle Effects em outro domínio visual; não substitui física Sable, clima lógico ou MobEffects.
- **Compatibilidade/Riscos:** Riscos: Sable splash/wake regression, particle density com Particle Rain/Effects, PartiCull, shaders/blending e cave-dust exclude list. Mixin TFC empacotado não prova TFC instalado.
- **Observações:** Runtime 1.5.7, file ID 8662697, Release 16/08/2026. Sable ships deixam foam wake ao navegar e splashes só devem ocorrer ao cair/entrar na água; toggle `enabledEffects.sableSplashes` disponível.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais da release 1.5.7 e mixin metadata física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/particular-reforged/files/8662697
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Particular Reforged 1.5.7 reconstruído: ambience, Sable splash/wake fix, cave dust, mixins, shader/culling boundaries, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `particular-1.21.1-NeoForge-1.5.7.jar`, mod id `particular`, versão `1.5.7`, NeoForge 1.21.1. Particular Reforged é uma camada visual de ambience/particles. A release instalada possui correção **específica para Sable ships/boats**: cruising deixa foam wake e splashes deixam de disparar continuamente, ocorrendo quando a ship realmente cai na água.

## 1. Identidade e papel
- **Mod:** Particular Reforged.
- **JAR físico:** `particular-1.21.1-NeoForge-1.5.7.jar`.
- **Mod id:** `particular`.
- **Runtime:** `1.5.7`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente funcional publicado:** Client-side.
- **Licença:** LGPLv3.
- **Papel:** enriquecer ambience com efeitos visuais contextuais, especialmente partículas ambientais e interações com água/mundo.
- **Decisão:** Sem decisão.

## 2. Boundary: visual, não gameplay
Particular controla efeitos de apresentação. Ele não deve receber ownership de:
- física de água;
- movimento real de ships/boats;
- damage/fall state;
- clima lógico;
- estado de entidades.

Uma splash ou foam wake é consequência visual de um evento; não deve ser usada como fonte de verdade do evento.

## 3. Escopo visual publicado
O projeto descreve muitos efeitos artesanais de ambience. Exemplos documentados incluem 3D water splashes, cascades/waterfalls, ripples/spray e outros feedbacks ambientais.

O efeito concreto depende de contexto e config. Esta ficha não inventa uma contagem total de particles nem assume todos os efeitos habilitados no config local.

## 4. Water splashes
A documentação do projeto descreve splashes 3D que podem acompanhar cor da água e variar visualmente com entidade/queda.

Isso cria uma superfície de composição com:
- mobs grandes/pequenos;
- movimento rápido;
- Sable ships;
- shaders/transparência;
- fluid mods.

O evento funcional continua pertencendo à entidade/physics provider.

## 5. Delta exato 1.5.7 — Sable ships/boats
A release 1.5.7 corrige o comportamento em Sable:
- ships e boats não devem mais **spam splashes** apenas por navegar;
- splash ocorre quando a ship efetivamente cai/entra na água;
- cruising deixa uma **foam wake** em vez do spam anterior;
- a config adiciona `enabledEffects.sableSplashes` para desligar splashes/wakes de Sable.

Como o pack possui Sable 2.0.5 e Create Aeronautics, esse delta é diretamente relevante ao runtime atual.

## 6. Cave dust exclude biomes
A 1.5.7 também corrige a lista de **exclude biomes** do cave dust para efetivamente funcionar.

Testar:
- biome explicitamente excluído;
- biome permitido;
- fronteiras de bioma;
- worldgen modded BWG/Terralith.

Config de exclusão é apresentação; não altera a classificação do bioma em si.

## 7. Mixin surfaces físicas
A modlist física registra mixins:
- `particular.mixins.json`;
- `particular.neoforge.mixins.json`;
- `particular.sable.mixins.json`;
- `particular.tfc.mixins.json`.

A presença de um mixin config TFC no JAR **não significa TFC instalado**. É código de compatibilidade empacotado; a authority de presença continua sendo a modlist top-level, onde TFC não está presente atualmente.

## 8. Sable integration ownership
Particular 1.5.7 conhece Sable para rendering de splash/wake, mas não controla:
- ship physics;
- SubLevel coordinates;
- propulsion;
- collision;
- buoyancy.

Se uma ship se move errado, investigar Sable/Aeronautics antes de atribuir problema ao visual. Se move certo mas splash/wake é incorreto, Particular é superfície relevante.

## 9. Relação com Particle Rain e Particle Effects
- **Particle Rain:** weather/atmosphere particles.
- **Particle Effects:** MobEffect particles.
- **Particular:** ambient/world interaction effects.
- **PartiCull:** redução dinâmica de particles por performance.

As quatro camadas podem coexistir. O risco é densidade, blending e culling, não equivalência funcional.

## 10. Iris/shaders
Shaders podem mudar water color, transparency, depth e blending. Como Particular desenha efeitos em água/ambiente, testar:
- splash color;
- foam wake visibility;
- cave dust;
- cascades/ripples;
- shader on/off.

Visual discrepante não altera o estado funcional do mundo.

## 11. Client lifecycle
Eventos importantes:
- resource reload;
- dimension change;
- Sable ship assembly/disassembly;
- entrar/sair de água;
- shader toggle/reload;
- config toggle `sableSplashes`;
- relog.

Particles temporárias devem desaparecer e não ficar órfãs após transição.

## 12. Riscos
1. **Sable splash spam regression:** fix central da 1.5.7.
2. **Foam wake alignment:** efeito pode ficar deslocado em SubLevel/movement edge cases.
3. **Particle density:** soma com Particle Rain/Effects.
4. **PartiCull:** culling pode reduzir ambience em FPS baixo.
5. **Shaders:** blending/water tint podem alterar legibilidade.
6. **Cave dust config:** exclude list precisa funcionar em biomas modded.
7. **Compat code confusion:** mixin TFC empacotado não prova mod instalado.
8. **Performance:** water-heavy scenes/ships podem gerar muitos efeitos.

## 13. Matriz de testes
- [ ] Cliente NeoForge 1.21.1 inicia com Particular 1.5.7.
- [ ] Splash vanilla em queda na água aparece uma vez e com posição correta.
- [ ] Sable ship cruising não spamma splashes e produz foam wake coerente.
- [ ] Sable ship caindo na água produz splash apenas no evento apropriado.
- [ ] `enabledEffects.sableSplashes=false` desliga splash/wake da integração sem quebrar ship.
- [ ] Cave dust não aparece em biome excluído e aparece em biome permitido.
- [ ] BWG/Terralith biome borders não deixam cave dust stale.
- [ ] Shader on/off preserva splash/wake legível.
- [ ] PartiCull sob stress reduz particles sem deixar effects presos após recovery.
- [ ] Resource reload/dimension change/ship disassembly limpam particles temporárias.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR, mod id/runtime e quatro mixin configs, inclusive Sable/TFC compat empacotada.
- CurseForge oficial: project 1219053, file ID 8662697, Release NeoForge 1.21.1 de 16/08/2026.
- Modrinth oficial: ambiente client-side e changelog 1.5.7.
- Changelog 1.5.7: fix Sable ship/boat splash spam, foam wake, `enabledEffects.sableSplashes` e cave-dust exclude list.
- **Limite:** config local e lista completa de efeitos habilitados não foram lidas; presença de mixin compat não foi confundida com presença top-level do mod alvo.
