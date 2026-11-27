# Moonlight Lib — 1.21.1-3.8.5

## Propriedades vigentes — 27/11/2026

- **Mod:** Moonlight Lib
- **Arquivo JAR instalado (declarado):** `moonlight-1.21.1-3.8.5-neoforge.jar`
- **Versão instalada declarada:** `1.21.1-3.8.5` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #407 (preservada).
- **Categoria CurseForge / pasta:** API and Library.
- **Função:** Biblioteca transversal de registries, assets/data packs dinâmicos, AI/trades de villagers, animação e configs.
- **Atualização/Status:** `1.21.1-3.6.4` → `1.21.1-3.8.5`.
- **Estado no pack:** instalação declarada pelo usuário, sem leitura física nova.
- **Estado da pesquisa:** publicações identificadas e deltas disponíveis auditados/reutilizados; limites de evidência explicitados, sem certificação de runtime.
- **Dependências / compatibilidade / riscos:** Amendments 1.21-2.3.1 e Supplementaries 1.21.1-4.1.4 já registrados: Moonlight sincronizado, mínimos exatos desses consumers não verificados aqui. Every Compat e CodecUI embarcada precisam de revalidação; não alegar versão nested nova. Testar datapacks, trades, resources, tags, plugins e configs; sem QA.
- **Fonte:** [publicação oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/selene/files/9780727).
- **Procedência:** declaração expressa do usuário e distribuição oficial; não inferir hashes nem metadata do JAR novo.

## Auditoria incremental e reconciliação

Histórico já individualiza 3.6.5/.6/.7/.8/.9, 3.7.0/.1/.2 (esta última dois fixes genéricos), 3.8.0 (models, defineVec2, tags condicionais), 3.8.1 (imagens em configs), 3.8.2 (registro de plugins), 3.8.3 (09/11, lifecycle/leaks da tela de mundos). Catálogo 1.21.1 mostra 3.8.4 (22/11) apenas Fabric, não contar como publicação NeoForge. 3.8.5 — 23/11, Release NeoForge 1.21.1: correções adicionais em trades, e problema de inclusão de loot tables em datapack dinâmico. O changelog não discrimina o contrato específico de trades. Não misturar com 4.x de MC 26.x.

## Histórico documental preservado

> O conteúdo abaixo corresponde aos snapshots e auditorias anteriores. Menções a versão física, atual, candidata, não instalada, hashes e dependências são históricas; as propriedades vigentes prevalecem.

## Propriedades do registro

- **Mod:** Moonlight Lib
- **Arquivo JAR:** moonlight-1.21.1-3.6.4-neoforge.jar
- **Versão 1.21.1:** 1.21.1-3.6.4
- **Categoria:** Biblioteca
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/selene/files/8850087
- **Função:** Biblioteca para dynamic assets/resources/data packs, registries/dynamic registration, villagers/trades, map markers, item animations e utilidades compartilhadas por consumers.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Supplementaries; Amendments também está presente no ecossistema. `codecui-neoforge-1.21.1-1.3.6.jar` é biblioteca embarcada do host, não top-level.
- **Compatibilidade/Riscos:** Library transversal Client & Server. Riscos: ABI drift, dynamic registration/codec mismatch, resource/datapack reload, mixin overlap e consumer attribution. Supplementaries é consumer confirmado. CodecUI 1.3.6 é embedded JarJar. A 3.6.4 corrige um problema de concorrência, que passa a ser regression gate do runtime atual.
- **Sobreposição:** Não substituível por outras libraries genéricas sem adaptação dos consumers; APIs/registries são contratos específicos.
- **Observações:** Runtime físico 1.21.1-3.6.4. A release 3.6.4 NeoForge 1.21.1, file 8850087, foi publicada em 10/09/2026 e corrige um problema de concorrência. CodecUI 1.3.6 permanece JarJar interno do JAR instalado.
- **Procedência:** modlist física atual anexada em 16/09/2026 + CurseForge oficial Moonlight 3.6.4 NeoForge 1.21.1 (file 8850087, 10/09/2026) + source oficial já auditado + consumers físicos. O version gate 3.6.3→3.6.4 registrado em 13/09 foi resolvido pela troca física.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Moonlight físico permanece 1.21.1-3.6.4. Foram revisadas as releases **3.6.5 → 3.6.6 → 3.6.7 → 3.6.8**; 3.6.8 é a latest NeoForge 1.21.1 localizada no CurseForge.
- **Histórico da decisão:** 2026-08-26 — atualizado de 3.4.1 para runtime 1.21.1-3.5.0 e classificado como Dependência após confirmação de Supplementaries instalado como consumidor. 2026-08-28 — atualizado para 1.21.1-3.5.2. 2026-09-06 — atualizado para 1.21.1-3.6.1. 2026-09-07 — atualizado para 1.21.1-3.6.3; decisão Dependência preservada. 2026-09-13 — upstream 3.6.4 registrado como atualização disponível. 2026-09-16 — modlist física confirma 1.21.1-3.6.4; version gate encerrado e decisão Dependência preservada.
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #407: JAR `moonlight-1.21.1-3.6.4-neoforge.jar`, mod id `moonlight`, runtime `1.21.1-3.6.4`, SHA-1 `04a9656a7085dcc751ac58ff0e7faadee5c2def9`.
<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico atual: `moonlight-1.21.1-3.6.4-neoforge.jar`, mod id `moonlight`, versão literal `1.21.1-3.6.4`. A release oficial exata é o CurseForge file ID `8850087`, publicada em 10/09/2026. O JAR continua embutindo `codecui-neoforge-1.21.1-1.3.6.jar` em `META-INF/jarjar`; CodecUI pertence ao host e não é entrada top-level independente.
</callout>
## 1. Identidade e papel
- **Mod:** Moonlight Lib / Selene.
- **JAR físico:** `moonlight-1.21.1-3.6.4-neoforge.jar`.
- **Mod id:** `moonlight`.
- **Runtime:** `1.21.1-3.6.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** MehVahdJukaar.
- **CurseForge project ID:** 499980; file ID 8850087.
- **Ambiente:** Client & Server.
- **Decisão:** **Dependência**.
- **Consumer confirmado:** Supplementaries; o pack também contém Amendments, outro mod do mesmo ecossistema.
## 2. Escopo da biblioteca
Moonlight é uma biblioteca especializada usada por mods de MehVahdJukaar e outros consumers. A documentação oficial cita infraestrutura para:
- criação/manipulação dinâmica de assets e texturas;
- runtime resource/data packs;
- registries/data registries próprios;
- dynamic registration;
- utilidades cross-loader;
- villagers/AI e trades data-driven;
- map markers;
- animações de item em primeira/terceira pessoa;
- outras abstrações compartilhadas.
Essas features são **APIs da biblioteca**. A presença de uma API não prova que todo consumer do pack usa todos os módulos.
## 3. Ownership e consumers
Moonlight não deve receber ownership do gameplay final de Supplementaries/Amendments. Ela fornece infraestrutura; os consumers continuam authority de seus blocos, recipes, configurações e sistemas.
Enquanto consumers que exigem Moonlight estiverem instalados, remover a biblioteca isoladamente pode impedir boot ou quebrar registries/resources.
## 4. Dynamic assets e resource packs
Uma das superfícies mais importantes é geração/manipulação dinâmica de recursos. Isso pode incluir assets produzidos em runtime a partir de dados de consumers.
Consequências:
- resource reload é teste obrigatório;
- cache de textures/models deve ser invalidado corretamente;
- resource packs do usuário podem alterar inputs de geração;
- falhas podem se manifestar no consumer mesmo tendo origem na library layer.
## 5. Data packs, registries e dynamic registration
Moonlight fornece primitives para runtime data packs e registros dinâmicos. Em um pack grande, isso é uma superfície de alta sensibilidade porque IDs/ordem/codec precisam ser consistentes entre cliente e servidor.
Não usar APIs internas de Moonlight como fonte paralela de verdade para dados de um consumer. Integrações próprias devem falar com a API pública apropriada e preservar ownership do mod que registra o conteúdo.
## 6. Villagers, trades e map markers
A documentação oficial cita utilidades de villagers AI, trades data-driven e map markers. Esses módulos podem ser consumidos por mods para implementar comportamento próprio.
A ficha não atribui qualquer trade/map marker específico a Moonlight sem identificar qual consumer o registra. Para debugging, distinguir infraestrutura Moonlight de dados fornecidos pelo mod consumidor.
## 7. Mixins
A modlist física atual registra:
- `moonlight.mixins.json`;
- `moonlight-common.mixins.json`.
Logo há transformações no runtime além de helpers passivos. Sem pin byte-equivalente de source desta release nesta etapa, alvos/métodos específicos não são inventados.
## 8. CodecUI embarcado
O JAR contém `codecui-neoforge-1.21.1-1.3.6.jar`, id `codecui`, versão `1.21.1-1.3.6`, sob `META-INF/jarjar`.
Regra de catálogo:
- não criar registro top-level para esta cópia embarcada de CodecUI;
- não contar esta cópia como mod top-level separado;
- documentar risco/versionamento sob Moonlight;
- qualquer erro de classloading desta cópia de CodecUI deve ser triado como componente embarcado do host.
## 9. Changelog 3.6.3 e atualização física 3.6.4
A release 3.6.3 anteriormente instalada registrava:
- API para adicionar itens suportados pelo loom;
- melhorias de configuração.
Em 13/09/2026, o Notion registrou corretamente a 3.6.4 apenas como version gate externo: `moonlight-1.21.1-3.6.4-neoforge.jar`, file ID `8850087`, publicada em 10/09/2026, com changelog público limitado à correção de um problema de concorrência. A modlist física atual de 16/09/2026 confirma que essa build passou a ser a instalada. Portanto:
- instalado = **1.21.1-3.6.4**;
- o antigo estado “3.6.4 disponível, não aplicada” é histórico e não descreve mais o pack;
- não são projetadas outras mudanças internas além do que a fonte oficial publica para 3.6.4.
## 10. Client/server e sync
CurseForge classifica Moonlight como Client & Server. Dependendo da API usada pelo consumer, existem superfícies client-facing e server-authoritative.
Testes importantes:
- registry/data pack consistency;
- resource reload client-side;
- server restart;
- reconnect;
- consumers que usam data-driven trades/map markers;
- config sincronizada quando aplicável.
## 11. Compatibilidade no pack
### Supplementaries
Consumer confirmado e central do ecossistema. A modlist física atual contém `supplementaries-1.21.1-3.9.9-neoforge.jar`. Deve passar smoke test após updates de Moonlight.
### Amendments
A modlist física atual contém `amendments-1.21-2.1.10-neoforge.jar`. Por pertencer ao mesmo ecossistema e historicamente consumir Moonlight, é uma superfície de regressão relevante; se a dependency precisa ser provada para uma decisão futura, conferir o manifest da versão física.
### Outros libraries
Moonlight não é intercambiável com Architectury, Balm, MonoLib etc. Bibliotecas podem oferecer conceitos semelhantes, mas consumers importam APIs e IDs específicos.
## 12. Riscos
1. **ABI drift** entre Moonlight e consumers.
2. **Dynamic registration** pode gerar registry/codec mismatch.
3. **Resource reload** pode quebrar assets dinâmicos ou caches.
4. **Data pack lifecycle** pode produzir dados stale/duplicados após reload.
5. **Client/server mismatch** em registries/dados de consumers.
6. **Mixin overlap** com outros mods de infra/render/data.
7. **Embedded CodecUI** não deve ser removido/catalogado isoladamente.
8. **Config changes** introduzidas na 3.6.3 precisam permanecer válidas após o upgrade.
9. **Concurrency regression:** a 3.6.4 corrige um problema de concorrência; paths concorrentes usados por consumers devem integrar o smoke test.
10. **Consumer attribution:** stacktrace Moonlight não prova que a library seja causa-raiz.
## 13. Matriz de testes
- [ ] Dedicated server inicia com Moonlight 3.6.4 + Supplementaries atual.
- [ ] Cliente conecta sem registry mismatch.
- [ ] Supplementaries registra conteúdo/recipes/config sem missing classes.
- [ ] `/reload` conclui e recursos/data driven permanecem consistentes.
- [ ] Resource reload do cliente reconstrói assets dinâmicos sem textura/modelo ausente.
- [ ] Restart do servidor mantém dados persistentes dos consumers.
- [ ] Alterações de config relevantes não são resetadas silenciosamente.
- [ ] Funções de loom suportadas por consumers continuam válidas quando utilizadas.
- [ ] Amendments e demais consumers do ecossistema passam smoke test.
- [ ] Dois clientes recebem resultados equivalentes de conteúdo data-driven.
- [ ] Cenários concorrentes acionados pelos consumers não reproduzem a falha corrigida na 3.6.4.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limites
- **Modlist física atual:** `moonlight-1.21.1-3.6.4-neoforge.jar`, mod id/version, dois mixin configs e CodecUI 1.3.6 embarcado.
- **CurseForge oficial:** Moonlight Lib file ID 8850087, Release NeoForge 1.21.1 de 10/09/2026; changelog da 3.6.4 = correção de concurrency issue.
- **Histórico preservado:** 3.6.3/file ID 8821083, API para loom-supported items e config improvements, e o version gate registrado em 13/09/2026.
- **Source oficial:** `MehVahdJukaar/Moonlight`.
- **Consumers físicos:** Supplementaries 3.9.9; Amendments 2.1.10 também está presente no ecossistema.
- **Limite:** source exato byte-equivalente do JAR 3.6.4 não foi pinado nesta etapa; internals não são inferidos além das APIs publicamente documentadas e do changelog oficial.

## 15. Histórico upstream 3.6.4 → 3.6.8 — não instalado
A autoridade física continua em **Moonlight Lib 3.6.4**. Foram publicadas quatro releases posteriores para NeoForge 1.21.1.

### 3.6.5 — 17/09/2026
- adiciona `Burnt` à blacklist da **automatic wood detection**;
- amplia suporte para **Codec config screen**.

### 3.6.6 — 20/09/2026
- corrige um **rare concurrency issue**;
- corrige um problema com **fluid buckets**.

### 3.6.7 — 21/09/2026
- corrige outro **concurrency issue**.

### 3.6.8 — 21/09/2026
- corrige uma interação específica entre **No Man's Land + ModernFix**.

Impacto para o pack: 3.6.5 pode alterar auto-discovery de famílias de madeira em consumers; 3.6.6/3.6.7 reforçam paths concorrentes já classificados como risco; 3.6.6 toca fluid-container semantics; 3.6.8 é compat específica e só deve ser tratada como relevante se ambos os providers estiverem presentes.

Gate de promoção 3.6.4→3.6.8: Supplementaries/Amendments boot, automatic wood detection, config screens/CodecUI, fluid buckets, `/reload`, resource reload, dois clientes concorrentes, dynamic packs, reconnect, e cenário No Man's Land + ModernFix apenas quando essa combinação existir fisicamente.

Fontes upstream: CurseForge files 8905916 (3.6.5), 8929425 (3.6.6), 8940361 (3.6.7) e 8941470 (3.6.8), NeoForge 1.21.1.

## 16. Atualizações upstream 3.6.9 → 3.7.1 — 10/10/2026

**Físico:** moonlight-1.21.1-3.6.4-neoforge.jar, com CodecUI 1.3.6 embarcado. O histórico prévio deste dossiê cobre 3.6.5 → 3.6.6 → 3.6.7 → 3.6.8. O novo intervalo público NeoForge 1.21.1 é:

- **3.6.9 (24/09, file 8963246):** melhorias de configurações, novos sanity checks para facilitar diagnóstico e fixes diversos não especificados; não atribuir mecânicas sem evidência.
- **3.7.0 (26/09, file 8981395):** API para adicionar conteúdo aos slots da Cartography Table, incluindo ícones.
- **3.7.1 (05/10):** detecção de WoodType ancient_root do The Undergarden (issue #967); mudança só é funcional quando o correspondente provider está presente.

**Cadeia completa:** 3.6.4 → .5 → .6 → .7 → .8 → .9 → 3.7.0 → 3.7.1. Moonlight é biblioteca de dinâmica de assets/registries/codec configs. Testar acoplamento com Supplementaries, Amendments, wood families, CodecUI/JarJar, client/server, reloads concorrentes, Cartography Table, fluid buckets, salva/reabre e consumo por resource packs antes de promover isoladamente.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/selene/files/8963246 ; https://www.curseforge.com/minecraft/mc-mods/selene/files/8981395 ; https://www.curseforge.com/minecraft/mc-mods/selene/files/all?version=1.21.1

**Estado:** 3.7.1 disponível upstream; JAR 3.6.4 continua físico. Sem runtime QA executado.

## Atualização upstream e gate de manutenção — 11/10/2026

**Upstream NeoForge 1.21.1:** **3.7.2**, release de 10/10/2026 (`moonlight-1.21.1-3.7.2-neoforge`); JAR histórico permanece **3.6.4**. A cadeia 3.6.5–3.7.1 está detalhada acima; não inventar deltas da 3.7.2 sem consultar changelog granular. **Gate transversal:** Supplementaries, Amendments 2.1.11, Polytone 5.0.5, CodecUI, dynamic generated assets, registries/wood types, Cartography Table, configs, resource/datapack reload, shader and resource-pack order, client+dedicated-server, save/restart. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/selene/files/all?version=1.21.1

> **Limite:** revisão documental baseada no último snapshot físico; nenhuma instalação de JAR ou validação in-game nesta execução.

## Auditoria incremental de 18/11/2026 — 3.7.2 a 3.8.3

**Autoridade física:** #407, `moonlight-1.21.1-3.6.4-neoforge.jar`, mod ID `moonlight`, runtime literal `1.21.1-3.6.4`. Projeto oficial Moonlight Lib (Selene), CurseForge 499980, categoria **API and Library**. Última release identificada: **3.8.3**, de 09/11/2026, para NeoForge 1.21.1. Não misturar com 4.x de Minecraft 26.x. Histórico anterior já documenta 3.6.5–3.7.1.

| Release | Data / arquivo oficial | Delta e relevância |
|---|---|---|
| **3.7.2** | 10/10/2026, [9122548](https://www.curseforge.com/minecraft/mc-mods/selene/files/9122548/additional-files) | Autor relata duas correções menores, sem identificar subsistemas. Não inferir nova API ou mecânica; nota pública consultada integralmente. |
| **3.8.0** | 03/11/2026, [9426244](https://www.curseforge.com/minecraft/mc-mods/selene/files/9426244) | Corrige modelos customizados no NeoForge, acrescenta suporte de configuração `defineVec2` e leitura de condições NeoForge em tags. Relevante para resource/data packs, geração de conteúdo e condições de carregamento. |
| **3.8.1** | 04/11/2026, [9436226](https://www.curseforge.com/minecraft/mc-mods/selene/files/9436226) | Configurações passam a suportar imagens; corrige problemas de config não detalhados. Regressar telas e preservação de valores; não atribuir mudança de gameplay automática. |
| **3.8.2** | 05/11/2026, [9458248](https://www.curseforge.com/minecraft/mc-mods/selene/files/9458248) | Introduz registro de plugins, explicitamente requerido por Every Compat; confirmar presença e requisitos da versão exata do consumer antes de promover isoladamente. |
| **3.8.3** | 09/11/2026, [9519534](https://www.curseforge.com/minecraft/mc-mods/selene/files/9519534) | Corrige lifecycle e pequenos vazamentos restritos à tela de mundos; não implica otimização geral do servidor. |

**Cadeia documental fechada:** 3.6.5 → 3.6.6 → 3.6.7 → 3.6.8 → 3.6.9 → 3.7.0 → 3.7.1 → 3.7.2 → 3.8.0 → 3.8.1 → 3.8.2 → 3.8.3, conforme [listagem de arquivos 1.21.1](https://www.curseforge.com/minecraft/mc-mods/selene/files/all?page=1&version=1.21.1). Para a 3.7.2, o changelog publicado não identifica os dois fixes; nenhuma implicação funcional adicional é atribuída. O gate de compatibilidade permanece: Supplementaries 3.9.9 no inventário (4.1.2 apenas candidata), Amendments, Every Compat se presente, CodecUI embarcada, reload de recursos e de dados, tags condicionais, models, configs com imagens, registro único de plugins e dedicated server. Não alterar a composição dos JarJar sem inspeção. Nenhum JAR, config ou teste em jogo foi alterado.
