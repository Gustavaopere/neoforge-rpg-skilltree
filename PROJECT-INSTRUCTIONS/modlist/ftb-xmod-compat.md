# FTB XMod Compat — 21.1.11

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81a19fe4d48f9f9ab592  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FTB XMod Compat
- **Arquivo JAR:** `ftb-xmod-compat-neoforge-21.1.11.jar`
- **Versão 1.21.1:** `21.1.11`
- **Categoria:** Compat; QoL
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ftb-xmod-compat
- **Função:** Módulo oficial de compatibilidade cruzada do ecossistema FTB. Detecta mods FTB e não-FTB presentes e habilita integrações condicionais; sozinho não adiciona gameplay útil. Na linha 1.21.1 documentada, integra FTB Quests com KubeJS, Game Stages e JEI/REI.
- **Dependências:** Todas as integrações são soft dependencies segundo o projeto oficial. O mod inicia mesmo sem os alvos, mas só produz efeito quando os mods correspondentes estão presentes. No pack físico atual estão presentes FTB Quests 2101.1.34, KubeJS 2101.7.2-build.374 e JEI 19.53.0.426.
- **Compatibilidade/Riscos:** Risco principal é detecção condicional/API drift entre FTB Quests, KubeJS, JEI/REI e outros alvos. A release 21.1.11 corrige crash de startup quando TooManyRecipeViewers se apresenta como JEI, demonstrando sensibilidade à identificação de recipe viewers. Validar ausência de double-registration, eventos duplicados, stage ownership ambíguo e client/server classloading.
- **Sobreposição:** Não substitui FTB Quests, KubeJS, Game Stages, JEI ou REI. Sua sobreposição é apenas de integração; bridges duplicadas ou mods externos que façam a mesma conexão podem causar eventos/handlers duplicados.
- **Observações:** JAR físico: ftb-xmod-compat-neoforge-21.1.11.jar; mod id: ftbxmodcompat; versão 21.1.11. CurseForge publica exatamente a release NeoForge 1.21.1 em 15/08/2026. Não tratar o mod como provider de quests, stages ou recipes: ele apenas conecta providers existentes.
- **Procedência:** modlist.txt física atual + CurseForge oficial FTB XMod Compat + descrição oficial do projeto + changelog oficial da linha 21.1.11; revalidado em 09/09/2026.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — bridge condicional FTB↔mods externos, FTB Quests/KubeJS/Game Stages/JEI-REI, soft dependencies, authority, lifecycle, regressão TMRV 21.1.11 e matriz de testes catalogados.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** O runtime físico é `ftb-xmod-compat-neoforge-21.1.11.jar`, mod id `ftbxmodcompat`, em NeoForge 1.21.1. FTB XMod Compat é uma bridge oficial: detecta combinações de mods e ativa integrações; não é provider de quests, stages, recipes ou progressão por si só.

## 1. Identidade, versão e papel
- **Mod:** FTB XMod Compat.
- **JAR físico:** `ftb-xmod-compat-neoforge-21.1.11.jar`.
- **Versão instalada:** `21.1.11`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Mod id físico/catalogado:** `ftbxmodcompat`.
- **Distribuição oficial:** release NeoForge 1.21.1 publicada em 15/08/2026.
- **Papel:** centralizar integrações entre mods FTB e mods externos sem obrigar cada projeto FTB a duplicar bridges.

## 2. Authority e ownership
FTB XMod Compat não deve adquirir authority sobre sistemas dos mods que conecta.
- **FTB Quests** continua authority de quest graph, tasks, rewards e quest state.
- **KubeJS** continua authority de scripts e eventos KubeJS.
- **Game Stages**, quando usado, continua provider de stages.
- **JEI/REI** continuam providers de recipe/item display.
A bridge apenas adapta chamadas, eventos e apresentação entre esses providers. Implementações próprias do modpack não devem criar uma terceira cópia de quest state ou stage state só para “acompanhar” a integração.

## 3. Dependências e ativação condicional
A documentação oficial afirma que **todas as dependências de integração são soft dependencies**. Isso significa:
- o mod pode carregar mesmo sem outros mods presentes;
- sem pares compatíveis ele simplesmente não faz trabalho útil;
- cada integração deve ser tratada como condicional à presença dos providers envolvidos.
No snapshot físico atual estão presentes, entre outros alvos relevantes:
- `ftb-quests-neoforge-2101.1.34.jar`;
- `kubejs-neoforge-2101.7.2-build.374.jar`;
- `jei-1.21.1-neoforge-19.53.0.426.jar`.
Não foi localizado Game Stages como top-level nesta checagem física específica, portanto o caminho KubeJS é o relevante para stages no pack atual.

## 4. Integração FTB Quests ↔ KubeJS
Quando KubeJS está presente, a documentação oficial registra duas superfícies:
- FTB Quests dispara eventos KubeJS quando determinados eventos de quests ocorrem;
- FTB Quests usa KubeJS como implementação de game stages.
Consequência de ownership: scripts podem reagir ao estado de quests, mas não devem duplicar a resolução interna de tasks/rewards. Stage mutation precisa convergir para um único estado observável pelos consumidores.

## 5. Integração FTB Quests ↔ Game Stages
Se Game Stages estiver presente **e KubeJS não estiver**, FTB Quests pode usar Game Stages como implementação de stages e reagir a eventos de stage add/remove/edit rechecando stage tasks existentes.
No pack físico auditado, KubeJS está presente. Portanto esta rota é documentada como capacidade do mod, não como rota ativa confirmada desta instância.

## 6. Integração FTB Quests ↔ JEI/REI
Quando JEI ou REI está presente, FTB Quests pode usar o recipe viewer para:
- exibir recipes relacionadas a item tasks com item rewards;
- mostrar itens que podem sair de loot crates configuradas;
- mostrar recipes de itens diretamente no painel da quest.
No pack atual JEI está presente. Essa integração é de apresentação/navegação; não transfere recipe authority para FTB Quests nem para XMod Compat.

## 7. Fallback de stages
A documentação oficial registra um fallback quando **nem KubeJS nem Game Stages** estão carregados: FTB Quests usa uma implementação interna baseada em tags string associadas ao jogador. O próprio projeto a descreve como funcional, porém muito limitada.
Como KubeJS está presente fisicamente, não assumir que esse fallback esteja ativo no runtime atual.

## 8. Release 21.1.11 e regressão relevante
A 21.1.11 é a release física atual da linha NeoForge 1.21.1. O changelog registra correção de **startup crash quando TooManyRecipeViewers está presente e se apresenta como JEI**.
Esse fix é evidência concreta de que recipe-viewer detection é uma superfície sensível. Mesmo sem TMRV top-level localizado no snapshot atual, o caso deve permanecer como regression gate para futuras mudanças do stack de recipe viewers.

## 9. Client/server e lifecycle
O projeto é publicado para **Client & Server**.
Pontos de lifecycle que precisam permanecer seguros:
- mod discovery durante startup sem hard-fail por ausência de integrações opcionais;
- registro condicional de handlers apenas uma vez;
- login/relogin sem duplicar listeners de stage/quest;
- reload de scripts KubeJS sem produzir handlers duplicados;
- alteração de stage disparando rechecagem de tasks somente pelo caminho correto;
- recipe-viewer discovery sem classloading de classes ausentes ou masquerading incompatível.

## 10. Multiplayer
O estado de quests e stages é por jogador/team conforme os providers correspondentes. A bridge não deve criar um segundo estado paralelo.
Regression gates:
- dois jogadores em estados de quest diferentes não podem compartilhar stage por erro de cache;
- eventos KubeJS devem preservar o jogador/team causal correto;
- reconexão não deve reaplicar rewards ou reemitir side effects não idempotentes por duplicação de listener.

## 11. Integrações concretas no pack
- **FTB Quests 2101.1.34:** consumer direto das integrações documentadas.
- **KubeJS 2101.7.2-build.374:** provider de scripts e, no caminho documentado, implementação de stages usada por FTB Quests.
- **JEI 19.53.0.426:** recipe viewer presente fisicamente e usado pela superfície de display quando detectado.
Outros mods FTB podem receber integrações em versões/source além do conjunto descrito publicamente nesta página; sem pin exato do source 21.1.11, esta ficha não inventa módulos adicionais.

## 12. Riscos técnicos
- double-registration de listeners/eventos;
- stage state duplicado entre KubeJS, Game Stages e fallback interno;
- recipe-viewer masquerading/class detection causando crash;
- classloading client/server de integração opcional ausente;
- handler stale após script reload;
- reward/task side effect duplicado após reconnect ou evento repetido;
- version drift entre FTB Quests, KubeJS e XMod Compat.

## 13. Matriz de testes
- [ ] Dedicated server boot com o stack físico atual.
- [ ] Client join/rejoin sem duplicar listeners.
- [ ] Quest task disparando evento KubeJS exatamente uma vez.
- [ ] Add/remove de stage refletindo corretamente em stage tasks.
- [ ] JEI abrindo recipes a partir do painel de quest sem crash.
- [ ] Reload de scripts sem double handlers.
- [ ] Dois jogadores/teams com progressão distinta sem vazamento de state.
- [ ] Ausência de reward duplication após reconnect.
- [ ] Regression test com recipe viewer alternativo/masquerading antes de qualquer troca do stack.
Nenhum desses testes foi marcado como executado nesta catalogação.

## 14. Evidências e limites
**Confirmado por modlist física:** JAR, mod id, versão 21.1.11 e presença de FTB Quests, KubeJS e JEI.
**Confirmado pela documentação/CurseForge oficial:** finalidade de bridge, soft dependencies, integrações FTB Quests↔KubeJS, Game Stages e JEI/REI, fallback de stages, ambiente Client & Server e release NeoForge 21.1.11.
**Confirmado pelo changelog 21.1.11:** fix de crash quando TooManyRecipeViewers se apresenta como JEI.
**Limite:** source/commit exato do binário 21.1.11 não foi pinado nesta auditoria; portanto não são inventadas classes, métodos, eventos internos ou integrações adicionais não sustentadas pela documentação pública.
