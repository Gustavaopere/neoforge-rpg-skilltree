# Sound Physics Aeronautics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815bbbbff21447141e88
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sound-physics-remastered-neoforge-1.4.0.1.jar`, mod id `sound_physics_remastered`, runtime name `Sound Physics Aeronautics`, runtime `1.4.0.1`; Create 6.0.10, Aeronautics 1.3.2, Sable 2.0.5 e Distant Horizons 3.2.0-b presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o fork 1.4.0.1 e o stack Create/Aeronautics/Sable/Distant Horizons citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sound Physics Aeronautics
- **Arquivo JAR:** `sound-physics-remastered-neoforge-1.4.0.1.jar`
- **Versão 1.21.1:** 1.4.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Performance
- **Função:** Fork acústico de Sound Physics Remastered para Create Aeronautics/Sable: calcula oclusão/reverberação em sublevels móveis, propeller/thruster/train audio de longo alcance, Doppler e integração opcional com LODs do Distant Horizons.
- **Dependências:** Required upstream: Create e Create: Aeronautics; pack: Create 6.0.10 e Aeronautics 1.3.2. Sable 2.0.5 é o stack físico de sublevels usado pelo ecossistema. Distant Horizons 3.2.0-b está presente e satisfaz o mínimo 3.1.2-b publicado para a integração opcional de far-sound occlusion.
- **Sobreposição:** Substitui o Sound Physics Remastered base no pack. Sounds 2.4.22 adiciona/seleciona eventos sonoros; Sound Physics Aeronautics processa propagação/oclusão/ambiente desses sons, portanto são camadas complementares.
- **Compatibilidade/Riscos:** Fork Beta de Sound Physics Remastered adaptado a Create Aeronautics/Sable. Não deve coexistir com o SPR original. Riscos: custo acústico em muitas fontes, transform/sublevel stale, falsos indoor↔outdoor, long-range/DH occlusion, Doppler extremo, multipath e drift de prerelease. 1.4.0.1 é hotfix explícito para Aeronautics 1.3.2, exatamente o provider físico.
- **Observações:** Top-level físico `sound-physics-remastered-neoforge-1.4.0.1.jar`, mod id `sound_physics_remastered`, runtime name `Sound Physics Aeronautics`, versão 1.4.0.1. O filename preserva a lineage SPR, mas o provider instalado é o fork Aeronautics; não há segunda cópia top-level do SPR base.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sound Physics Aeronautics 1.4.0/1.4.0.1 + stack físico Create 6.0.10, Aeronautics 1.3.2, Sable 2.0.5 e Distant Horizons 3.2.0-b.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sound-physics-aeronautics
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sound Physics Aeronautics 1.4.0.1 reconstruído: moving-sublevel acoustics, propeller grouping, long-range sound, DH occlusion, Doppler, topology continuity, exact 1.4.0.1 Aero 1.3.2 hotfix, lifecycle, riscos e testes.
- **Histórico da decisão:** Substituiu o Sound Physics Remastered base, removido em 15/08/2026. A identidade do fork Sound Physics Aeronautics foi preservada na reconciliação de 30/08/2026 mesmo com a mudança do filename top-level para `sound-physics-remastered-neoforge-1.4.0.1.jar`.
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sound-physics-remastered-neoforge-1.4.0.1.jar`, mod id `sound_physics_remastered`, runtime name `Sound Physics Aeronautics`, versão `1.4.0.1`, NeoForge 1.21.1. É o **fork Beta de Sound Physics Remastered adaptado ao stack Create Aeronautics/Sable** e substitui o provider base no pack.

## 1. Identidade, versão e maturidade
- **Mod:** Sound Physics Aeronautics.
- **JAR:** `sound-physics-remastered-neoforge-1.4.0.1.jar`.
- **Mod id:** `sound_physics_remastered`.
- **Versão:** `1.4.0.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente publicado:** Client & Server.
- **Decisão vigente:** **Manter**.

O filename e mod id preservam a lineage do Sound Physics Remastered, mas a identidade runtime e a publicação oficial são do fork **Sound Physics Aeronautics**.

## 2. Replacement, não instalação paralela
O projeto instrui explicitamente a **não instalar Sound Physics Remastered junto com Sound Physics Aeronautics**. Deve-se usar um ou outro.

A modlist física contém somente este top-level `sound-physics-remastered-neoforge-1.4.0.1.jar`; portanto não há segundo provider acústico SPR concorrente.

## 3. Authority e ownership
- **Sound Physics Aeronautics:** cálculo/apresentação acústica, oclusão, ambiente, multipath, long-range sound e Doppler de sua camada.
- **Create Aeronautics/Sable:** physics body, sublevels, transforms, posição e movimento reais das estruturas.
- **Create:** máquinas/contraptions e sources correspondentes.
- **Sounds/outros sound mods:** podem escolher/adicionar eventos; não se tornam authority da propagação acústica.

O fork deve consumir a geometria/movimento dos providers, sem criar state físico paralelo.

## 4. Acústica em sublevels móveis
A principal diferença para o SPR base é considerar **geometria rotacionada e móvel de sublevels** durante verificações acústicas.

Hull, paredes e partes de uma aeronave/contraption podem contribuir para oclusão e sensação de interior/exterior mesmo quando não estão em coordenadas estáticas vanilla.

Transform stale pode produzir som abafado no lugar errado ou ignorar uma parede que já se moveu.

## 5. Interior, exterior e hull muffling
O fork busca preservar continuidade acústica enquanto o listener ou a source está dentro/fora de uma craft móvel.

Mudanças de topologia, assembly/disassembly ou abertura/fechamento de partes não devem provocar alternância falsa repetitiva entre perfis indoor/outdoor.

A linha 1.4.0 inclui trabalho específico para melhorar **enclosure continuity** durante mudanças de geometria/contraption.

## 6. Sources e listener em movimento
O sistema precisa lidar tanto com source móvel quanto com listener móvel. Exemplos concretos do ecossistema incluem machinery, propellers, thrusters e outros sons posicionais ligados à craft.

A posição acústica efetiva deve acompanhar o transform atual da sublevel; cache da world position antiga é regression gate.

## 7. Propellers de longo alcance
O upstream documenta que sons vanilla normalmente ficam limitados a cerca de **48 blocos**, enquanto o fork pode escalar sons de propeller para alcance de até **1024 blocos**, dependendo de tamanho/velocidade e configuração.

Isso é feature acústica, não chunk-loading authority. Long range não deve forçar gameplay/chunks indevidamente apenas para tocar áudio.

## 8. Distant Horizons
A integração opcional com **Distant Horizons** pode usar LOD geometry para oclusão de fontes distantes como propellers, thrusters e trains. O projeto publica requisito de DH `3.1.2-b` ou superior para esse path.

O pack contém Distant Horizons `3.2.0-b`, satisfazendo o mínimo publicado. A efetiva ativação/configuração local da integração não foi lida.

## 9. Doppler
O fork oferece **Doppler effect** para sources posicionais em movimento, configurável.

Velocidade relativa extrema, teleport, contraption snap ou transform discontinuity não devem gerar pitch impossível/stuck. O efeito continua apresentação acústica; não altera velocidade física da craft.

## 10. Sound policy layer
O projeto adiciona regras/policies para compatibilidade mais ampla com sound sources de mods. O objetivo é decidir quais sons devem receber paths especiais ou evitar comportamento inadequado.

Uma policy de compatibilidade não transfere ownership do evento sonoro original ao fork.

## 11. Otimizações da 1.4.0
A 1.4.0 reorganiza avaliações acústicas para reduzir trabalho duplicado e **reutilizar resultados equivalentes**.

Para propellers, o upstream passa a organizar a acústica por **grupos físicos de propeller**: loops relacionados podem compartilhar uma avaliação ambiental/multipath autoritativa sem perder sua apresentação sonora individual.

O ganho real no pack precisa ser medido; não é tratado como benchmark já aprovado.

## 12. Multipath e continuidade
A 1.4.0 também endurece o caminho de rendering multipath e a continuidade durante alterações de geometria/topologia.

Regression gates: eco/reverb/occlusion não devem saltar violentamente quando a craft muda de orientação, cruza chunk ou atualiza sua montagem.

## 13. Speed-of-sound experimental
A linha 1.4.0 adiciona groundwork experimental para atraso por **speed of sound** e apresentação de **sonic boom**.

O upstream declara esse caminho **desabilitado por padrão/experimental**. Não é tratado como feature ativa do pack sem config/runtime evidence.

## 14. Delta exato 1.4.0.1
A build física `1.4.0.1` é um **hotfix para Create Aeronautics 1.3.2**, com intenção de compatibilidade também com releases menores futuras da linha Aero.

O pack usa exatamente **Create Aeronautics 1.3.2**, tornando esse hotfix um regression gate direto e material.

## 15. Dependências físicas
Upstream required:
- **Create** — pack `6.0.10`;
- **Create: Aeronautics** — pack `1.3.2`.

Stack físico associado:
- **Sable 2.0.5** — sublevels/physics usados pelo ecossistema;
- **Distant Horizons 3.2.0-b** — integração opcional de far-sound occlusion.

Não inventar hard dependency adicional além da relação publicada.

## 16. Relação com Sounds 2.4.22
`Sounds` adiciona/substitui eventos e feedbacks sonoros de UI, blocks e actions. **Sound Physics Aeronautics** atua depois/ao redor do evento, calculando sua propagação/oclusão/ambiente.

Logo, coexistência é conceitualmente complementar. Duplicação real só existe se dois providers de propagation physics tentarem assumir o mesmo path, como SPR base + este fork.

## 17. Client / server boundary
A publicação marca o mod Client & Server. A percepção final do áudio é local ao cliente, enquanto positions/transforms/world geometry vêm do state compartilhado pelos providers.

Esta auditoria não inventa qual cálculo interno roda em cada lado sem source exato. Dedicated-server boot e multiplayer continuam na matriz porque a distribuição oficial inclui ambos os lados.

## 18. Lifecycle
Validar:
- world join/disconnect;
- assembly/disassembly de craft;
- source/listener entrando e saindo da sublevel;
- rotação/translação rápida;
- propeller start/stop e mudança de velocidade;
- mudança de hull/topology;
- chunk crossing/unload/reload;
- server restart;
- resource/config reload quando suportado;
- Distant Horizons LOD load/unload.

## 19. Multiplayer
Cada cliente deve ouvir a mesma craft segundo seu próprio listener e a posição server-synced da source. Dois observers em posições diferentes podem legitimamente receber acústica diferente.

O que não pode divergir é a identidade/posição real da craft/source; áudio local não deve alterar gameplay state.

## 20. Riscos técnicos
1. **Provider duplication:** instalar SPR base junto deste fork.
2. **Beta drift:** API/behavior muda em prerelease.
3. **Acoustic cost:** muitas sources/propellers elevam custo.
4. **Transform stale:** source/hull calculado em posição antiga.
5. **False indoor/outdoor:** topology update quebra enclosure continuity.
6. **Multipath artifact:** reflexões/occlusion instáveis.
7. **Long-range overhead:** alcance alto processa sources irrelevantes.
8. **DH mismatch:** LOD occlusion usa geometry incompleta/stale.
9. **Doppler edge case:** pitch extremo após movimento descontínuo.
10. **Aero compatibility:** regression do hotfix 1.4.0.1 com 1.3.2.

## 21. Matriz de testes
- [ ] Cliente e dedicated server iniciam com 1.4.0.1 + Create 6.0.10 + Aero 1.3.2.
- [ ] Não há segunda cópia do Sound Physics Remastered base.
- [ ] Som externo é abafado corretamente por hull de craft móvel.
- [ ] Listener dentro/fora mantém transição acústica estável.
- [ ] Source em sublevel acompanha rotação/translação sem position lag.
- [ ] Propeller long-range respeita alcance/config sem tocar de forma absurda.
- [ ] Multiple propellers não provocam custo acústico desproporcional.
- [ ] Distant Horizons 3.2.0-b participa da far occlusion quando integração está ativa.
- [ ] Doppler responde a aproximação/afastamento sem pitch preso.
- [ ] Assembly/disassembly e topology change não causam falso indoor/outdoor.
- [ ] Chunk crossing/restart preservam comportamento acústico.
- [ ] Experimental speed-of-sound/sonic-boom permanece inativo salvo config deliberada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
- Modlist física de 11/09/2026: JAR/runtime e stack Create/Aero/Sable/DH.
- CurseForge oficial Sound Physics Aeronautics: fork, requirements, sublevel acoustics, long-range sound, DH, Doppler e aviso de não coexistência com SPR.
- Changelog 1.4.0: grouping/reuse, enclosure continuity, multipath e features experimentais.
- Changelog 1.4.0.1: hotfix exato para Aeronautics 1.3.2.
- **Limite:** configs acústicas locais e performance real não foram medidas; o canal permanece Beta e os testes runtime seguem pendentes.
