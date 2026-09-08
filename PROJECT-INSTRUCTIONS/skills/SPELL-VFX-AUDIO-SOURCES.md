# Spell / VFX / Audio Source Audit — 2026-09-07

Esta página registra as evidências usadas pelas skills `minecraft-spell-vfx-engineering` e `minecraft-audio-design`. Ela não promove documentação externa acima da modlist/JAR física.

## 1. Autoridade física do modpack

Snapshot auditado `modlist.txt` de 2026-09-07:

- `photon-neoforge-1.21.1-2.2.6.a-all.jar` — Photon `2.2.6.a`;
- `aaa_particles-neoforge-1.21.1-2.2.3.jar` — AAA Particles `2.2.3`;
- `aaa_particles_world-neoforge-1.21.1-2.0.0.jar` — AAA Particles: World `2.0.0`;
- `geckolib-neoforge-1.21.1-4.9.2.jar` — GeckoLib 4 `4.9.2`;
- `irons_spellbooks-1.21.1-3.16.3.jar` — Iron's Spells 'n Spellbooks `1.21.1-3.16.3`.

### Divergência documental registrada

`GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS-CORRIGIDO.md` ainda descreve Photon `2.2.4`. Isso está desatualizado em relação ao JAR físico `2.2.6.a`; para presença/versão, a modlist física prevalece.

## 2. Photon 2

Fontes:

- https://github.com/Low-Drag-MC/Photon
- https://github.com/Low-Drag-MC/Photon/blob/1.21/LICENSE
- https://www.curseforge.com/minecraft/mc-mods/photon/files/8824095

Evidência confirmada:

- linha NeoForge Minecraft 1.21.1;
- release física/publicada `2.2.6.a` em 2026-09-06;
- editor/runtime oferece partículas, trails, beams, timeline, materiais/shader graph, post-processing, meshes/models e FX packs;
- o README expõe integração Java, mas qualquer classe/signature usada no projeto deve ser reconfirmada contra a revisão/JAR exata instalada;
- o `LICENSE` da branch 1.21 declara CC BY-NC-SA 4.0 para o mod e contém condições específicas de redistribuição/ports;
- o próprio `LICENSE` declara que conteúdo criado usando as ferramentas/APIs do mod não é automaticamente coberto pela licença do Photon.

### Nota de metadata

Há metadata pública divergente em superfícies secundárias (por exemplo, `gradle.properties` visto em revisão recente pode exibir outra string de licença). Para decisões de licença, usar o arquivo `LICENSE` da revisão realmente consumida e registrar sua SHA quando redistribuição/fork/modificação estiver em escopo.

## 3. AAA Particles / Effekseer

Fontes:

- https://www.curseforge.com/minecraft/mc-mods/aaa-particles
- https://www.curseforge.com/minecraft/mc-mods/aaa-particles-world
- https://effekseer.github.io/en/documentation.html

Evidência confirmada:

- AAA Particles é descrito pelo projeto como um loader de Effekseer para Minecraft;
- release NeoForge 1.21.1 `2.2.3` é a versão física atual do pack;
- AAA Particles: World `2.0.0` substitui determinados VFX do Minecraft por efeitos Effekseer e é client-side;
- Effekseer possui editor/documentação para partículas, parent-child effects, ribbons/tracks, modelos 3D, F-curves, distortion e materiais;
- as APIs Java do AAA mudaram ao longo da linha 2.x; não reutilizar snippets antigos sem inspeção da versão física.

## 4. Iron's Spells 'n Spellbooks como referência de qualidade

Fontes:

- https://github.com/iron431/irons-spells-n-spellbooks/tree/1.21
- https://github.com/iron431/irons-spells-n-spellbooks/blob/1.21/LATEST_CHANGES.MD

Evidência confirmada na linha 1.21:

- a base atual do projeto usa VFX como parte do polish de spells, não apenas partículas incidentais;
- `LATEST_CHANGES.MD` registra VFX/damage effects/cast effects em vários spells e impact effects + sound effects para Magic Missile;
- commits recentes também registram trabalho específico de cast VFX, impact particles e cast sound.

Isso é referência de composição e qualidade, não autorização para copiar assets, código ou signatures.

## 5. Regra de implementação

Provider-native first:

1. confirmar o owner real do gameplay;
2. verificar se o provider já dispara animação/VFX/som adequado;
3. evitar duplicação de presentation listeners;
4. usar Photon ou AAA/Effekseer somente quando a necessidade project-owned justificar o backend e a versão/API estiver comprovada;
5. fallback deve ser explícito, seguro e não alterar a semântica de gameplay.

## 6. Pendências deliberadas

- nenhuma API Java específica do Photon/AAA é congelada neste documento;
- nenhuma assinatura de Iron's é congelada por analogia;
- budgets numéricos de partículas/áudio não são inventados sem profiling do cliente real;
- assets de terceiros continuam sujeitos a auditoria de licença/proveniência individual.
