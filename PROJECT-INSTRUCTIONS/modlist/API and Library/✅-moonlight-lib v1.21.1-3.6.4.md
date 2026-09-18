# Moonlight Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física atual:** `modlist(1).txt` anexada em 16/09/2026 — JAR top-level confirmado fisicamente
- **Data da reauditoria:** 2026-09-16

## Propriedades do banco

- **Mod:** Moonlight Lib
- **Arquivo JAR:** `moonlight-1.21.1-3.6.4-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-3.6.4
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca para dynamic assets/resources/data packs, registries/dynamic registration, villagers/trades, map markers, item animations e utilidades compartilhadas por consumers.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Supplementaries; Amendments também está presente no ecossistema. `codecui-neoforge-1.21.1-1.3.6.jar` é biblioteca embarcada do host, não top-level.
- **Sobreposição:** Não substituível por outras libraries genéricas sem adaptação dos consumers; APIs/registries são contratos específicos.
- **Compatibilidade/Riscos:** Library transversal Client & Server. Riscos: ABI drift, dynamic registration/codec mismatch, resource/datapack reload, mixin overlap e consumer attribution. Supplementaries é consumer confirmado. CodecUI 1.3.6 é embedded JarJar. A 3.6.4 corrige um problema de concorrência, que passa a ser regression gate do runtime atual.
- **Observações:** Runtime físico `1.21.1-3.6.4`, CurseForge file ID `8850087`, Release NeoForge 1.21.1 de 10/09/2026. O changelog público da 3.6.4 registra correção de um problema de concorrência. A 3.6.3 anterior havia adicionado API para loom-supported items e melhorias de configuração. CodecUI 1.3.6 permanece embutido via JarJar.
- **Procedência:** modlist física atual anexada em 16/09/2026 + CurseForge oficial da release 3.6.4 + source oficial MehVahdJukaar/Moonlight + consumers físicos. O version gate registrado no Notion em 13/09/2026 foi resolvido pela troca física 3.6.3 → 3.6.4.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/selene/files/8850087 | https://github.com/MehVahdJukaar/Moonlight
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 16/09/2026 — Moonlight físico 1.21.1-3.6.4 confirmado; o update que em 13/09 constava apenas como disponível agora está efetivamente instalado. APIs dinâmicas, resources/data/registries, consumers, mixins, CodecUI JarJar, changelogs 3.6.3/3.6.4, riscos e testes permanecem catalogados.
- **Histórico da decisão:** 2026-08-26 — atualizado de 3.4.1 para runtime 1.21.1-3.5.0 e classificado como Dependência após confirmação de Supplementaries instalado como consumidor. 2026-08-28 — atualizado para 1.21.1-3.5.2. 2026-09-06 — atualizado para 1.21.1-3.6.1. 2026-09-07 — atualizado para `moonlight-1.21.1-3.6.3-neoforge.jar`, runtime `1.21.1-3.6.3`; decisão Dependência preservada. 2026-09-13 — upstream 3.6.4 registrado como atualização disponível, ainda não aplicada. 2026-09-16 — modlist física confirma `moonlight-1.21.1-3.6.4-neoforge.jar`; version gate encerrado e decisão Dependência preservada.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico atual: `moonlight-1.21.1-3.6.4-neoforge.jar`, mod id `moonlight`, versão literal `1.21.1-3.6.4`. A release oficial exata é o CurseForge file ID `8850087`, publicada em 10/09/2026. O JAR continua embutindo `codecui-neoforge-1.21.1-1.3.6.jar` em `META-INF/jarjar`; CodecUI pertence ao host e não é entrada top-level independente.

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
- **Histórico preservado do Notion:** 3.6.3/file ID 8821083, API para loom-supported items e config improvements, e o version gate registrado em 13/09/2026.
- **Source oficial:** `MehVahdJukaar/Moonlight`.
- **Consumers físicos:** Supplementaries 3.9.9; Amendments 2.1.10 também está presente no ecossistema.
- **Limite:** source exato byte-equivalente do JAR 3.6.4 não foi pinado nesta etapa; internals não são inferidos além das APIs publicamente documentadas e do changelog oficial.
