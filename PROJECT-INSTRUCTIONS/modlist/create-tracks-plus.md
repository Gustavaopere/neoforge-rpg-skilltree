# Create Tracks+

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db815c839bd8600dc60f6a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Tracks+
- **Arquivo JAR:** `tracks_plus-1.0.6b6.jar`
- **Versão 1.21.1:** 1.0.6b6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Compat
- **Função:** Fork de Create:Tracks para Create Aeronautics/Sable que adiciona tracks/lagartas físicas, aparência inspirada em Trackwork, high-clearance suspension, mounts modificados e correções de drops.
- **Dependências:** Create 6.0.10 + Create Aeronautics 1.3.2 + Sable 2.0.5 no runtime atual. Incompatível com Create:Tracks original, que não está presente na modlist física.
- **Sobreposição:** Substitui funcionalmente Create:Tracks original e adota linguagem semelhante a Trackwork; o original não está presente na modlist atual.
- **Compatibilidade/Riscos:** Beta 1.0.6b6 muito recente; riscos de physics instability, version coupling Create/Aeronautics/Sable, drops em destruição, chunk desync e interação com True Impact. Release 1.0.5 permanece fallback de estabilidade, não downgrade automático.
- **Observações:** mod id `tracks`; runtime 1.0.6b6 Beta de 05/09/2026. Decisão Manter preservada. Não há changelog público detalhado da beta6 suficiente para inventar mudanças adicionais. 1.0.5 é última Release estável 1.21.1.
- **Procedência:** Runtime/JAR e stack: modlist física canônica 08/09/2026. Fork/features/canais: CurseForge oficial Create:Tracks+. Create:Tracks original usado somente como contexto upstream.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-track
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Create:Tracks+ 1.0.6b6; fork/ownership, suspension, mounts, drops, Sable/Aeronautics/True Impact, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-06 — decisão de presença fechada em Manter. A build beta6 continua registrada porque é o JAR fisicamente instalado; 1.0.5 é fallback Release recomendado caso a beta apresente regressão.
- **Data da última decisão:** 2026-09-06

> 🛞 **ESCOPO CANÔNICO.** Runtime físico: `tracks_plus-1.0.6b6.jar`, mod id `tracks`, versão `1.0.6b6`. Create:Tracks+ é um **fork do Create:Tracks** voltado a Create Aeronautics/Sable, com tracks para veículos físicos, aparência/estrutura inspirada em Trackwork e suspensão de maior curso. A build instalada é Beta; a última Release estável 1.21.1 é 1.0.5.

## 1. Identidade, versão e decisão
- **Mod:** Create:Tracks+.
- **JAR:** `tracks_plus-1.0.6b6.jar`.
- **Mod id:** `tracks`.
- **Versão:** `1.0.6b6`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal:** Beta, publicada em 05/09/2026.
- **Última Release estável:** 1.0.5, 30/05/2026.
- **Decisão vigente:** **Manter**; preservada.

## 2. Provenance e relação com Create:Tracks
O projeto declara ser fork do Create:Tracks de qwxon. Ele **não é compatível com o Create:Tracks original** e orienta substituí-lo.
A modlist física atual contém apenas `tracks_plus-1.0.6b6.jar`; não contém o JAR original `tracks-neoforge-*`. Portanto não existe duplicata ativa deste par no runtime atual.

## 3. Authority e ownership
- **Create 6.0.10:** base mecânica/contraption/kinetics.
- **Create Aeronautics 1.3.2 + Sable 2.0.5:** física/estruturas móveis do stack.
- **Tracks+:** track mounts, tracks/lagartas, suspensão e comportamento específico de locomotion que adiciona.
Tracks+ não deve ser documentado como physics engine independente nem como substituto de Sable.

## 4. Tracks para veículos físicos
O propósito publicado é adicionar tracks para Create Aeronautics no estilo de Trackwork. O conjunto permite construir veículos rastreados/lagartas que interagem com terreno em vez de serem apenas decoração.
A mecânica exata de forças é dependente da integração física; valores de torque, grip ou impulse não são afirmados sem source/config pin da beta6.

## 5. Aparência e estrutura estilo Trackwork
O fork altera aparência e estrutura das tracks para se aproximar da linguagem visual do Trackwork, preservando features consideradas úteis do Create:Tracks.
Essa similaridade visual não torna Trackwork uma dependency: o projeto apenas usa o estilo/conceito como referência.

## 6. High-clearance suspension
O upstream declara uma **high-clearance suspension** semelhante à de Trackwork. O objetivo operacional é permitir maior curso/acomodação a terreno irregular.
Regression tests devem cobrir:
- compressão/extensão em obstáculos;
- slopes e degraus;
- contato após airtime;
- veículo muito pesado;
- múltiplos mounts no mesmo lado;
- estrutura física acima/ao redor do mount.

## 7. Track mounts e power source
O fork declara que modificou os **track mounts para bypass the power source**. Isso é uma mudança arquitetural em relação ao projeto de origem e precisa ser testada no stack Create atual.
Não inferir geração de energia gratuita: a frase upstream descreve a relação do mount com a passagem/fonte de potência, não uma regra de energia universal.

## 8. Drops e destruição
O projeto também declara ter corrigido os **drop items ao destruir** os componentes. Em veículo físico, break/dismantle é superfície de dupe/loss.
Testar quebra em estado montado/desmontado, Creative/Survival e destruição causada por colisão/True Impact quando aplicável.

## 9. Beta 1.0.6b6 vs Release 1.0.5
A página de arquivos confirma:
- 1.0.5 = Release estável 1.21.1;
- 1.0.6beta / 1.0.6beta6 = linha Beta mais nova;
- beta6 é o JAR físico do pack.
Não foi localizado changelog público detalhado específico da **beta6** suficiente para afirmar alterações internas adicionais. Assim, o dossiê não inventa diferenças além do escopo geral publicado.
A 1.0.5 é registrada como **fallback de estabilidade**, não como downgrade obrigatório.

## 10. Client / server e multiplayer
Physics/vehicle state deve permanecer server-authoritative. Cliente renderiza track geometry/suspension e recebe transforms.
Em multiplayer validar:
- mesmo veículo observado por dois clientes;
- input do piloto e motion state;
- track collision/contact;
- chunk transitions;
- desync visual de suspensão;
- desmontagem após disconnect do piloto.

## 11. Integrações concretas no pack
- **Create 6.0.10:** base.
- **Create Aeronautics 1.3.2:** estrutura/vehicle context.
- **Sable 2.0.5:** physics runtime.
- **Sable: True Impact 0.5.7-delta:** impactos podem danificar veículo/terrain; testar break/drop de tracks sob colisão.
- **Create: Coasters Simulated:** compartilha stack físico, mas é outro tipo de veículo/mecânica; não é substituto.

## 12. Riscos técnicos
1. **Beta recente:** beta6 foi publicada em 05/09/2026.
2. **Physics instability:** suspensão/contacts podem oscilar, clipping ou aplicar impulses extremos.
3. **Version coupling:** Create/Aeronautics/Sable evoluem rapidamente.
4. **Drop duplication/loss:** destruição de mounts em physics state é superfície crítica.
5. **Original mod incompatibility:** Create:Tracks original não pode coexistir.
6. **Chunk boundary/desync:** veículos atravessando chunks podem perder contact state visual/lógico.
7. **True Impact interaction:** dano estrutural pode quebrar tracks durante simulação física.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Tracks+ beta6 + Create 6.0.10 + Aeronautics 1.3.2 + Sable 2.0.5.
- [ ] Create:Tracks original permanece ausente.
- [ ] Veículo rastreado monta e se move sem physics explosion.
- [ ] High-clearance suspension atravessa degrau/slope/irregularidade.
- [ ] Track mantém contato após compressão/extensão repetida.
- [ ] Mount behavior não cria fonte de energia indevida.
- [ ] Break em Survival devolve drops corretos uma única vez.
- [ ] Assemble/disassemble preserva tracks sem perda/dupe.
- [ ] Dois clientes observam suspension/motion coerentes.
- [ ] Chunk crossing não deixa track entity/mount órfão.
- [ ] True Impact quebrando componente não duplica drop nem corrompe vehicle state.
- [ ] Se beta6 regressar, 1.0.5 é testada em cópia separada como fallback.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica 08/09/2026: beta6, Create 6.0.10, Aeronautics 1.3.2 e Sable 2.0.5; Create:Tracks original ausente.
- CurseForge oficial Create:Tracks+: fork notice, Trackwork-like appearance/structure, high-clearance suspension, track-mount change e drop fix; beta6 de 05/09/2026 e Release 1.0.5.
- CurseForge Create:Tracks original usado apenas para contexto do projeto de origem, não para atribuir mecanicamente todos os recursos do original ao fork.