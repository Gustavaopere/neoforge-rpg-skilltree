# SuperMartijn642's Core Lib

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#535**: JAR `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`, mod id `supermartijn642corelib`, runtime `1.1.24`, SHA-1 `e77709df2499085d6576584b883a33285b2996b9`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** SuperMartijn642's Core Lib
- **Arquivo JAR:** `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`
- **Versão 1.21.1:** 1.1.24
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Core library reutilizável para mods consumidores, com infraestrutura compartilhada de GUIs/widgets, blocks/BlockEntities e network packets, reduzindo diferenças entre versões/loaders.
- **Dependências:** NeoForge 1.21.1. Biblioteca consumida por outros mods; consumers causais ainda não foram exaustivamente mapeados. Pack possui ImmediatelyFast 1.6.13, integração relevante porque Core Lib 1.1.23b+ inclui workaround de GUI.
- **Sobreposição:** Infraestrutura, sem conteúdo independente relevante.
- **Compatibilidade/Riscos:** Hidden dependencies e API/version drift podem impedir boot. 1.1.24 corrige área de hover de CustomSlot; 1.1.23b adicionou workaround para GUIs com ImmediatelyFast, presente no pack. Testar GUI, networking e BlockEntity lifecycle dos consumers.
- **Observações:** mod id `supermartijn642corelib`; runtime 1.1.24; Client & Server. Sem gameplay autônomo. Decisão Sem decisão preservada até mapear consumers reais.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + Modrinth oficial Core Lib 1.1.24 revalidado em 13/09/2026 + ImmediatelyFast 1.6.13 físico. Nenhum teste runtime foi executado.
- **Fonte:** https://modrinth.com/mod/supermartijn642s-core-lib/version/1.1.24-neoforge-mc1.21 ; https://www.curseforge.com/minecraft/mc-mods/supermartijn642s-core-lib
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 1.1.24; CurseForge publicou Core Lib 1.1.24a para NeoForge 1.21/1.21.1 em 21/09/2026.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`, mod id `supermartijn642corelib`, versão `1.1.24`. É uma **core library**: fornece implementações reutilizáveis de UI, blocos/BlockEntities e networking para mods consumidores; não adiciona um loop de gameplay próprio.
## 1. Identidade, versão e papel
- **Mod:** SuperMartijn642's Core Lib.
- **JAR físico:** `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`.
- **Mod id:** `supermartijn642corelib`.
- **Versão:** `1.1.24`.
- **Compatibilidade oficial:** Minecraft 1.21–1.21.1, NeoForge, Client & Server.
- **Decisão vigente:** Sem decisão; preservada.
- **Papel:** reduzir duplicação entre versões/loaders oferecendo implementações comuns para subsistemas recorrentes de mods.
## 2. Authority e ownership
Core Lib é authority apenas da **infraestrutura que seus consumidores chamam**. O significado de cada GUI, bloco, packet ou regra continua pertencendo ao mod consumidor.
Uma falha em um consumidor pode atravessar código da biblioteca sem que isso prove que Core Lib é a origem semântica do bug. Diagnóstico deve identificar primeiro qual consumer invocou a API.
## 3. Superfícies funcionais publicadas
A descrição oficial confirma implementações básicas compartilhadas para:
- GUIs e widgets;
- blocks;
- tile/block entities;
- network packets;
- abstrações reutilizáveis entre versões do Minecraft e loaders.
A ficha não inventa classes adicionais, registries ou APIs não confirmadas para o runtime 1.1.24.
## 4. UI e widgets
A release 1.1.24 corrige especificamente a área de hover de `CustomSlot`, que estava dois pixels maior do que deveria.
Isto torna GUIs de consumidores um regression gate direto:
- hover/click deve corresponder à área real do slot;
- slots adjacentes não podem capturar interação incorreta;
- scaling/resolução não deve deslocar hitboxes lógicas.
## 5. Integração com ImmediatelyFast
A mudança 1.1.23b, incorporada à 1.1.24, adicionou workaround para GUIs que não renderizavam corretamente quando **ImmediatelyFast** estava presente.
O pack instala `ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`; portanto esta não é integração hipotética. Qualquer atualização de Core Lib ou ImmediatelyFast deve manter smoke test de GUIs dos consumidores.
## 6. Blocks e BlockEntities
A biblioteca publica bases comuns para blocks e block/tile entities. Isso significa que consumers podem herdar lifecycle, helpers ou comportamento técnico compartilhado, enquanto:
- registry id pertence ao consumidor;
- dados persistentes pertencem ao consumidor;
- tick/gameplay logic continua definida pelo consumidor.
Testar load/save e chunk unload/reload em consumers que utilizem BlockEntities baseadas na biblioteca.
## 7. Networking
Core Lib fornece infraestrutura para network packets. Em multiplayer:
- servidor continua authority de gameplay state;
- packets precisam validar direction/contexto;
- cliente não pode promover state local a regra do servidor;
- versões incompatíveis de library/consumer podem falhar durante load ou comunicação.
A ficha não afirma protocolo concreto sem consumer/source pin específico.
## 8. Cross-version / cross-loader abstraction
O objetivo explícito do projeto é manter código semelhante entre diferentes versões do Minecraft e loaders. Essa camada de abstração é útil aos autores, mas cria risco operacional de **API drift**: um consumer compilado para determinada linha pode exigir exatamente métodos/semântica daquela library.
Não substituir por outra “core lib” só porque o papel conceitual parece semelhante.
## 9. Lifecycle
Validar:
- startup client e dedicated server;
- registro de consumers sem missing classes/methods;
- abertura/fechamento de GUIs;
- reload de recursos quando GUI/modelos dependem disso;
- save/reload de BlockEntities de consumers;
- reconnect multiplayer;
- atualização conjunta library + consumer.
## 10. Integrações concretas no pack
- **ImmediatelyFast 1.6.13:** workaround de GUI incorporado desde 1.1.23b.
- **SuperMartijn642's Config Lib 1.1.8:** biblioteca separada; não é substituta nem prova de dependência mútua. Config Lib trata config; Core Lib trata infraestrutura geral.
- Mods do ecossistema SuperMartijn642 podem depender da Core Lib; este lote não resolveu exaustivamente todos os consumers causais.
## 11. Riscos técnicos
1. **Hidden dependency:** consumer não mapeado pode impedir boot se a lib for removida.
2. **Binary/API mismatch:** atualizar lib sem consumer compatível pode gerar `NoSuchMethodError`, load failure ou comportamento quebrado.
3. **GUI regression:** `CustomSlot` e workaround ImmediatelyFast mostram que rendering/hitboxes são superfície ativa.
4. **Networking mismatch:** versões divergentes podem causar packet/state inconsistentes.
5. **Wrong ownership diagnosis:** stack trace na Core Lib não prova que ela define a regra de gameplay que falhou.
## 12. Matriz de testes
- [ ] Cliente e dedicated server iniciam com Core Lib 1.1.24.
- [ ] Consumers reais são identificados antes de qualquer remoção.
- [ ] GUIs de consumers abrem sem crash ou render ausente.
- [ ] Hover/click de `CustomSlot` coincide com a área visual.
- [ ] ImmediatelyFast ativo não quebra GUI de consumers.
- [ ] BlockEntities de consumers persistem após chunk unload/reload e restart.
- [ ] Multiplayer reconecta sem packet/protocol error.
- [ ] Atualização da library em cópia de teste não quebra consumers compilados.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 13. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e ImmediatelyFast presente.
- Modrinth oficial 1.1.24: NeoForge 1.21–1.21.1, Client & Server; fix de `CustomSlot`; workaround ImmediatelyFast herdado de 1.1.23b.
- CurseForge oficial: escopo de GUIs, blocks, block/tile entities e network packets.
## 14. Revalidação física — 11/09/2026
A modlist atual mantém `supermartijn642corelib-1.1.24-neoforge-mc1.21.jar`, mod id `supermartijn642corelib`, runtime `1.1.24`. A release oficial continua cobrindo Minecraft 1.21–1.21.1 em NeoForge e preserva o fix de `CustomSlot` e o workaround para GUIs com ImmediatelyFast incorporado desde 1.1.23b.
Nenhum consumer adicional foi promovido a dependência causal sem evidência direta. GUI, networking e BlockEntity lifecycle continuam sem teste runtime nesta recatalogação.
## 15. Revalidação física — 13/09/2026
Runtime físico preservado em SuperMartijn642's Core Lib 1.1.24 para NeoForge 1.21.1. A publicação aplicável localizada permanece 1.1.24. O fix de área de hover de CustomSlot e o workaround para GUIs com ImmediatelyFast continuam regression gates; nenhum teste runtime foi executado.

## 16. Atualização upstream 1.1.24a — não instalada
A modlist física continua em **1.1.24**. A release **1.1.24a** para NeoForge 1.21/1.21.1 foi publicada em 21/09/2026.

Delta publicado: `TextComponents#block` passa a permitir que o próprio bloco crie o text component, em vez de sempre derivar o texto da translation key.

É uma mudança de API/biblioteca, não uma feature de gameplay. Consumers que dependam de labels/text components de blocos podem apresentar texto diferente após a atualização; o regression gate deve cobrir GUIs, tooltips, nomes de blocos e qualquer consumer que use essa helper.

Fonte upstream: https://www.curseforge.com/minecraft/mc-mods/supermartijn642s-core-lib/files/8943316
