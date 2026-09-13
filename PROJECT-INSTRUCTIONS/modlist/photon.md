# Photon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81439ce7d558a56047d8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `photon-neoforge-1.21.1-2.2.6.a-all.jar`, mod id `photon`, runtime `2.2.6.a`, mixin `photon.mixins.json`; KilaGraph 21.1.0.14 confirmado como JarJar interno do host Photon
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Photon 2.2.6.a está presente e KilaGraph 21.1.0.14 aparece apenas como dependência JarJar interna. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Photon
- **Arquivo JAR:** `photon-neoforge-1.21.1-2.2.6.a-all.jar`
- **Versão 1.21.1:** 2.2.6.a
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Biblioteca, Visual
- **Função:** Framework/editor avançado de VFX e partículas, com sistemas de partículas/trails e criação visual de efeitos para mods consumidores.
- **Dependências:** NeoForge 1.21.1 e ecossistema LDLib. KilaGraph 21.1.0.14 está embutido no JAR como JarJar e permanece sob o host Photon.
- **Sobreposição:** Não é particle pack decorativo comum; fornece infraestrutura/editor para VFX consumidos por outros mods. Sobreposição principal é custo/rendering com outros VFX, não gameplay.
- **Compatibilidade/Riscos:** Framework VFX Client & Server. Riscos: consumer/API drift, glTF asset loading, dedicated-server classloading, transform space em Sable/SubLevels, overdraw e interação com PartiCull/shaders. KilaGraph embedded não é top-level.
- **Observações:** Runtime 2.2.6.a, file ID 8824095, Release 06/09/2026. Delta exato: bump da versão de LDLib e fix do glTF loader. Referências antigas a 2.2.4/2.2.5 são históricas.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Photon 2.2.6.a + documentação oficial do framework/editor VFX + hierarquia JarJar física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/photon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Photon 2.2.6.a reconstruído: VFX/particle/trail framework, glTF loader, LDLib/KilaGraph embedded, lifecycle, performance, riscos e testes; decisão Manter preservada.
- **Histórico da decisão:** 2026-09-06 — decisão Manter confirmada; pesquisa fechada. Detectada atualização direta para Photon 2.2.6.a Release. Não alterar `Arquivo JAR`/versão no Notion até a pasta de mods e uma nova modlist confirmarem a atualização.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `photon-neoforge-1.21.1-2.2.6.a-all.jar`, mod id `photon`, versão `2.2.6.a`, NeoForge 1.21.1. Photon é um framework/editor de VFX para partículas e trails consumidos por mods e datapacks; a decisão anterior **Manter** é preservada. O JAR embute KilaGraph 21.1.0.14 como JarJar interno, não como entrada top-level.

## 1. Identidade e papel
- **Mod:** Photon.
- **JAR físico:** `photon-neoforge-1.21.1-2.2.6.a-all.jar`.
- **Mod id:** `photon`.
- **Runtime:** `2.2.6.a`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente publicado:** Client & Server.
- **Papel:** framework/editor de efeitos visuais inspirado em ferramentas de VFX, oferecendo sistemas de partículas, trails e construção visual de efeitos.
- **Decisão:** Manter — decisão curatorial anterior preservada.

## 2. Authority: framework VFX
Photon fornece infraestrutura para definir, editar e reproduzir efeitos. Ele não é authority do gameplay que dispara esses efeitos.

Se um spell, máquina ou entidade usa Photon:
- o consumer decide quando o evento acontece;
- o servidor/consumer decide dano, cooldown, state e regras;
- Photon materializa a representação VFX conforme o efeito definido.

Logo ausência de partícula não deve ser usada como prova de ausência do evento lógico.

## 3. Particle system
O projeto publica um sistema de partículas configurável para autores criarem VFX modded. Superfícies relevantes incluem emissão, transformação, cor/tamanho ao longo do tempo e composição de múltiplos componentes visuais.

A ficha não inventa nomes de nodes, campos ou registries além dos documentados; efeitos concretos pertencem aos consumers/resources que os fornecem.

## 4. Trail system
Photon também fornece trails para efeitos contínuos ou ligados a movimento. Trails são particularmente sensíveis a:
- teleports;
- dimension changes;
- entity despawn;
- velocity muito alta;
- SubLevels/contraptions móveis;
- cleanup após o consumer encerrar o efeito.

Um trail órfão é problema visual/lifecycle, não persistência do gameplay correspondente.

## 5. Editor e autoria
A proposta do Photon inclui criação/edição visual de VFX. Isso torna arquivos de efeito parte do pipeline de conteúdo do pack.

Para projetos próprios, versionar os assets junto do consumer que os usa e evitar editar IDs compartilhados sem ownership claro. Alterações visuais precisam ser testadas em resource reload e cold boot.

## 6. Release 2.2.6.a
A build exata instalada é uma **Release** NeoForge 1.21.1 publicada em 06/09/2026.

O changelog específico de `2.2.6.a` registra:
- atualização da versão de LDLib;
- correção do **glTF loader**.

A release 2.2.6 imediatamente anterior também registrou correções de object space em nodes de Transform/View Direction; isso é lineage, não delta exclusivo do sufixo `.a`.

## 7. glTF loader
O fix do glTF loader é um regression gate para efeitos que carregam modelos/assets glTF. Testar:
- asset válido;
- asset ausente/malformado;
- reload;
- dimension/reconnect;
- múltiplas instâncias simultâneas.

Falha de asset deve degradar de forma controlada e não quebrar o state funcional do consumer.

## 8. LDLib e KilaGraph
Photon usa o ecossistema LDLib e o artefato físico inclui `kilagraph-neoforge-1.21.1-21.1.0.14.jar` sob `META-INF/jarjar`.

KilaGraph é **embedded** do host Photon nesta instalação:
- não recebe ordinal próprio;
- não recebe página top-level separada;
- não deve ser removido/manualmente atualizado isoladamente sem evidência de que o host suporta isso.

## 9. Client/server e networking
Photon é publicado para Client & Server porque consumers podem sincronizar a reprodução de efeitos ou referenciar assets em eventos de gameplay. O servidor, porém, não deve depender do frame/render local para validar state.

Testar dedicated server para classloading: renderer/editor/client classes não podem vazar para paths server-only.

## 10. Composição com o stack visual
O pack possui Particle Effects, Particle Rain, Particular, PartiCull, shaders/Iris e vários mods de spells/combate.

Riscos concretos:
- overdraw/densidade de partículas;
- PartiCull ocultando feedback;
- shader mudando blending/depth;
- dois consumers emitindo VFX equivalentes para o mesmo evento;
- efeitos em Sable/SubLevels usando transform errado.

## 11. Performance
Photon é infraestrutura capaz de produzir efeitos leves ou caros dependendo do conteúdo. Avaliar custo por efeito, não rotular o framework inteiro como pesado.

Benchmark representativo deve medir:
- spawn burst intenso;
- trails longos;
- muitos emitters simultâneos;
- glTF em cena;
- shader on/off;
- cleanup após encerrar o efeito.

## 12. Riscos
1. **Consumer/API drift:** efeito ou mod compilado para outra versão de Photon/LDLib.
2. **glTF loader:** regressão central corrigida em 2.2.6.a.
3. **Embedded dependency:** KilaGraph interno confundido com mod top-level.
4. **Dedicated-server classloading:** renderer/editor chamado no servidor.
5. **VFX overload:** consumer pode criar densidade excessiva.
6. **Culling/shaders:** feedback visual pode desaparecer ou renderizar incorretamente.
7. **Transform space:** efeitos ligados a entidades/contraptions podem usar espaço incorreto.
8. **Asset lifecycle:** reload/update pode deixar referências stale.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Photon 2.2.6.a.
- [ ] Um efeito simples de partículas inicia/encerra sem particle órfã.
- [ ] Trail ligado a entidade acompanha movement e limpa no despawn.
- [ ] glTF válido carrega; asset inválido falha de forma controlada.
- [ ] Resource reload reconstrói assets sem duplicação/stale state.
- [ ] Dimension change/reconnect limpam efeitos temporários.
- [ ] Efeito em entidade dentro de Sable/SubLevel mantém transform coerente.
- [ ] PartiCull/shader stack não torna telegraph crítico ilegível.
- [ ] Stress com múltiplos emitters mantém desempenho aceitável.
- [ ] Embedded KilaGraph resolve sem aparecer como mod top-level duplicado.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR, mod id/runtime, `photon.mixins.json` e KilaGraph 21.1.0.14 embutido.
- CurseForge oficial: project 871522, file ID 8824095, Release NeoForge 1.21.1 de 06/09/2026.
- Changelog 2.2.6.a: bump de LDLib e fix do glTF loader.
- Documentação oficial: framework/editor VFX com particle/trail systems para consumers.
- **Limite:** consumers específicos, graphs/nodes concretos e assets VFX do pack não foram atribuídos sem dependency graph/resource audit individual.
