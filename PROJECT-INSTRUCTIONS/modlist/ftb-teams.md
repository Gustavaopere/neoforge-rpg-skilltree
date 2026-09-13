# FTB Teams — 2101.1.11

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81e8a90ad014058f1e89  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FTB Teams
- **Arquivo JAR:** `ftb-teams-neoforge-2101.1.11.jar`
- **Versão 1.21.1:** `2101.1.11`
- **Categoria:** Biblioteca; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/FTBTeam/FTB-Teams/tree/1.21.1/main
- **Função:** Autoridade de equipes/parties do ecossistema FTB: personal teams, membership, owner/officers, propriedades, convites, team chat, team stages, limited lives e persistência SNBT.
- **Dependências:** FTB Library; source 2101.1.11 usa baseline FTB Library 2101.1.30, Architectury 13.0.8 e NeoForge 21.1.209. O pack instala FTB Library 2101.1.35.
- **Compatibilidade/Riscos:** Riscos: membership duplicado/cached por consumidores, races em convites/owner transfer, limited lives com death systems, edição externa de SNBT e ABI drift. FTB Chunks/Quests devem consumir a equipe efetiva da API em vez de manter ownership paralelo.
- **Sobreposição:** Não é substituto de FTB Chunks/Quests: Teams é authority de identidade/membership; Chunks mantém claims/protection e Quests mantém quest state. Evitar uma segunda fonte de verdade de party/owner.
- **Observações:** Persistência confirmada em `<world>/ftbteams/ftbteams.snbt` + arquivos SNBT por team/type. `max_party_size=0` e `limited_lives=0` por default. Team properties e Team Stage foram auditados no source.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial FTBTeam/FTB-Teams branch 1.21.1/main exatamente em 2101.1.11 + changelog oficial 2101.1.x.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FTB Teams 2101.1.11 source-pinned; team model, persistência SNBT, propriedades, limited lives, party limits, API, lifecycle, multiplayer, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `ftb-teams-neoforge-2101.1.11.jar`, mod id `ftbteams`, Minecraft 1.21.1 / NeoForge. O branch oficial `FTBTeam/FTB-Teams:1.21.1/main` declara exatamente `mod_version=2101.1.11`. FTB Teams é autoridade de **identidade, membership e propriedades de equipe/party**; FTB Chunks, Quests e outros consumidores mantêm autoridade sobre seus próprios dados.

## 1. Identidade e versão
- **Mod:** FTB Teams.
- **JAR físico:** `ftb-teams-neoforge-2101.1.11.jar`.
- **Mod id:** `ftbteams`.
- **Versão:** `2101.1.11`.
- **Source pin:** `1.21.1/main`, exatamente 2101.1.11.
- **Baselines upstream:** Minecraft 1.21.1, NeoForge 21.1.209, Architectury 13.0.8 e FTB Library 2101.1.30. O pack usa FTB Library 2101.1.35, acima desse baseline.

## 2. Papel no modpack
FTB Teams fornece o modelo compartilhado de times usado pelo ecossistema FTB: personal team por jogador, parties, ownership, members/officers, propriedades, convites, chat redirecionado, estágios e persistência. Outros mods devem referenciar a equipe efetiva exposta pela API em vez de manter uma segunda cópia de membership.

## 3. Persistência e lifecycle
`TeamManagerImpl` confirma persistência server-side no diretório `ftbteams` do mundo:
- arquivo raiz `<world>/ftbteams/ftbteams.snbt`;
- subdiretórios por `TeamType`;
- arquivos `.snbt` individuais por equipe;
- mapas internos de equipes conhecidas, personal teams e equipe efetiva do jogador.
No primeiro acesso/login, o manager cria personal team quando necessário. `saveNow()` grava o state raiz e as equipes e dispara evento de save. Há eventos explícitos de manager carregado/salvo. Integrações próprias não devem escrever esses SNBTs em paralelo.

## 4. Team model e ownership
A equipe efetiva de um jogador pode ser sua personal team ou uma party. Mudanças de membership alteram essa resolução. Ownership e officer/member roles são relevantes para mutações administrativas. Quando um owner precisa ser substituído em regras de limited lives, a implementação tenta promover outro membro, com preferência por officers.

## 5. Propriedades de equipe
`TeamProperties` confirma propriedades built-in:
- `display_name`;
- `description`;
- `color`;
- `free_to_join`;
- `max_msg_history_size`, default `1000`;
- `team_stages`, conjunto oculto e não editável diretamente pelo jogador;
- `lives_remaining`, também oculto/não editável diretamente.
O sistema é extensível via `TeamCollectPropertiesEvent`; consumers podem registrar propriedades adicionais sem tomar ownership do core team state.

## 6. Config server — `ftbteams-server`
O source define defaults de modpack em `<instance>/config/ftbteams-server.snbt` e override por mundo em `<world>/serverconfig/ftbteams-server.snbt`.
- `limited_lives = 0`: sistema desligado. Quando >0, death de membro reduz vidas da party; ao chegar a zero, o membro que morre é removido e uma party sem vidas não pode convidar novos membros.
- `max_party_size = 0`: ilimitado. Se reduzido abaixo do tamanho de uma party existente, a party não é destruída; apenas novos convites ficam bloqueados até voltar ao limite.
A release 2101.1.11 adicionou `/ftbteams serverconfig` para acesso ao fluxo de configuração.

## 7. API e extensibilidade
A 2101.1.11 introduziu `FTBTeamsAPI.api().addPartyCreationValidator`, substituindo gradualmente o setter antigo de validator. Releases 2101.1.x também adicionaram expanded property system, Team Stage built-in, comandos force-add/force-remove e NBT editing via FTB Library. Preferir APIs/eventos públicos a mixins sobre internals de team membership.

## 8. Team chat
A linha atual possui toggle per-player para redirecionamento de mensagens ao team chat. Esse estado não deve ser confundido com permissão de membership nem usado como prova de que o jogador pertence à party exibida no client.

## 9. Client / server e multiplayer
- **Servidor:** authority de criação de equipes, convites, roles, membership, lives, stages e persistência.
- **Cliente:** telas, feedback e edição permitida de propriedades.
Em multiplayer, testar convites concorrentes, owner saindo/morrendo, officer promotion, party cheia, jogador mudando de party e reconexão. Consumers como FTB Chunks devem reagir à equipe efetiva atual e invalidar caches derivados quando membership muda.

## 10. Integrações concretas do pack
- **FTB Library 2101.1.35:** UI/config/SNBT/networking compartilhados.
- **FTB Chunks 2101.1.22:** usa teams como autoridade de ownership de claims/force-load.
- **FTB Quests 2101.1.34:** pode usar team/party para compartilhamento de progresso conforme configuração própria.
- **FTB XMod Compat 21.1.11:** bridges do ecossistema FTB; não altera ownership do TeamManager.
Não presumir integração de qualquer outro mod apenas por exibir grupos/parties.

## 11. Riscos técnicos
- state duplicado de membership em outro mod;
- cache de equipe efetiva não invalidado após join/leave;
- race em convites/owner transfer;
- edição direta dos arquivos SNBT fora do manager;
- mudança de `max_party_size` com parties já acima do limite;
- limited lives interagindo com death/respawn de outros mods;
- ABI drift entre Teams e FTB Library/consumidores;
- client UI tratando dados locais como authority.

## 12. Matriz de testes obrigatória
- [ ] Dedicated server boot com Teams 2101.1.11 + Library 2101.1.35.
- [ ] Criação automática de personal team e persistência após restart.
- [ ] Criar party, convidar, aceitar, sair e reconectar.
- [ ] Owner/officer/member permissions e transferência de owner.
- [ ] `max_party_size` zero e valor limitado, inclusive party já acima do novo limite.
- [ ] `limited_lives` em teste controlado: decremento, expulsão e promoção de owner.
- [ ] `team_stages` persistem e sincronizam sem edição client indevida.
- [ ] FTB Chunks muda ownership/permissões corretamente após mudança de party.
- [ ] FTB Quests atualiza contexto de team progress após membership change.
- [ ] Save/reload dos `.snbt` sem equipes órfãs ou duplicadas.

## 13. Evidências e limites
**Source primário pinado:** `FTBTeam/FTB-Teams`, branch `1.21.1/main`, exatamente 2101.1.11.
**Classes auditadas:** `TeamManagerImpl`, `ServerConfig`, `TeamProperties` e changelog da linha 2101.1.x.
**Modlist física:** confirma o JAR instalado e as versões dos consumidores FTB atuais.
**Não foram executados testes de runtime nesta catalogação.**
