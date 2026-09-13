# Jupiter

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bd84f9c1112401b025
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR e consumers documentados confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Jupiter
- **Arquivo JAR:** `jupiter-2.3.7-1.21.1-neoforge.jar`
- **Versão 1.21.1:** 2.3.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca de configuração V2 com telas in-game, entradas tipadas/custom codec, sincronização automática cliente↔servidor sob permissões e adaptação de configs Forge/NeoForge e Cloth para consumers.
- **Dependências:** NeoForge 1.21.1. A própria release 2.3.7 não lista dependência adicional NeoForge. Consumers físicos confirmados documentalmente no pack incluem Ice And Fire Community Edition 2.1.2 e Ice And Fire: Dread Land 0.1.2.
- **Sobreposição:** Não substitui genericamente Cloth Config/Fzzy Config; pode carregar/adaptar configurações desses ecossistemas, mas consumers que dependem de Jupiter exigem sua API/schema. Remoção só após mapear todos os dependentes.
- **Compatibilidade/Riscos:** Jupiter V2 é incompatível com mods baseados em Jupiter V1 segundo o projeto. Riscos: config schema/API drift, sync server/client incorreto, permission leakage, extra-config load duplicado/stale e consumers compilados para linha incompatível. 2.3.7 corrige LAN permission pass e extra-config loading/warning spam.
- **Observações:** 2.3.7 corrige LAN permission pass, problemas de tradução Cloth e erro/spam ao carregar extra configs. O projeto documenta config screens, auto-sync com dedicated server mediante permissão, tipos primitivos/listas/custom codecs e Extra Config System para (Neo)Forge configs e Cloth Config.
- **Procedência:** modlist.txt física atual + CurseForge oficial Jupiter 2.3.7 NeoForge 1.21.1 file 7738312, com sources jar + repositório oficial IAFEnvoy/Jupiter para arquitetura V2/config sync + consumers físicos documentados Ice And Fire CE/Dread Land.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jupiter/files/7738312
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Jupiter 2.3.7 release/source-published; V2 config/sync, permissions, custom codecs, Extra Config System, IAF CE/Dread Land consumers, 2.3.7 fixes, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `jupiter-2.3.7-1.21.1-neoforge.jar`, mod id `jupiter`, versão `2.3.7`. CurseForge file 7738312 é Release NeoForge 1.21.1 e publica sources JAR. O projeto alerta que **Jupiter V2 não é compatível com consumers baseados em V1**.

## 1. Papel e authority
Jupiter é uma library de configuração. Ela controla framework, serialização/apresentação e sincronização das configs que consumers registram por sua API; cada consumer continua authority do significado gameplay de suas opções.

## 2. Config screens
A documentação V2 oferece telas in-game para editar configurações. UI é um frontend: alterações com efeito server-side precisam passar pelo contrato de permissão/sync e não podem ser consideradas aplicadas só porque o widget mudou localmente.

## 3. Auto sync
Jupiter pode sincronizar configs com dedicated server quando o cliente conecta e possui as permissões adequadas. O servidor deve permanecer authority das opções server-relevant; um cliente não autorizado não pode sobrescrever regras por packet/UI.

## 4. Entradas e codecs
O framework documenta suporte a `int`, `double`, `string`, listas e entradas com codec customizado. Custom codecs ampliam o schema, mas aumentam risco de incompatibilidade se o consumer muda tipo/formato entre versões.

## 5. Extra Config System
Jupiter também pode carregar configs de outros sistemas e expô-las com seus recursos. O projeto documenta suporte a configs (Neo)Forge e Cloth Config API. Isso é adaptação, não substituição automática dessas libraries nem prova de que qualquer config do pack está sendo intermediada por Jupiter.

## 6. Permissões
Dedicated-server config possui controle de permission. A 2.3.7 corrige especificamente **LAN permission pass**, então hosted LAN, integrated server e dedicated server devem ser tratados como regression paths separados.

## 7. Consumers físicos
Ice And Fire Community Edition 2.1.2 e Ice And Fire: Dread Land 0.1.2 documentam uso de Jupiter. Por isso a library não pode ser removida com base em ausência de conteúdo visível; é parte do bootstrap/config stack desses consumers.

## 8. Release 2.3.7
Mudanças publicadas: fix de LAN permission pass; fix de problemas de tradução Cloth; fix de erro ao carregar extra config e warning spam. Não atribuir outras mudanças à build sem source/changelog específico.

## 9. Client / server
Config UI é client-side; config authoritativa, permission checks e opções sincronizadas podem ser server-side. A mesma chave pode ter escopo diferente conforme o consumer. Não assumir escopo de uma opção sem documentação do mod que a registra.

## 10. Lifecycle
Validar construction, config parse, first join, sync, alteração permitida/negada, integrated/LAN/dedicated server, reconnect, server restart e migration após update. Extra-config adapters não devem registrar a mesma config duas vezes.

## 11. Version boundary V1/V2
O README oficial é explícito sobre incompatibilidade entre Jupiter V2 e consumers baseados em V1. Atualizar library sem conferir consumer major/API é um risco de startup/linkage, mesmo que o arquivo carregue no mesmo Minecraft.

## 12. Riscos técnicos
- consumer V1 carregado com Jupiter V2;
- schema/codec drift;
- config server-side alterada por cliente sem permission;
- sync stale após reconnect;
- extra config carregada duas vezes;
- warning/error mascarar config não aplicada;
- migration perder/defaultar valor;
- UI traduzida incorretamente induzir configuração errada.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com Jupiter 2.3.7.
- [ ] Ice And Fire CE e Dread Land carregam suas configs sem linkage/schema error.
- [ ] Cliente autorizado sincroniza/edita somente opções permitidas.
- [ ] Cliente sem permissão não altera config server-side.
- [ ] LAN exerce o fix de permission pass da 2.3.7.
- [ ] Cloth translation não exibe keys quebradas na amostra usada.
- [ ] Extra Config System não gera loading error/warning spam regressivo.
- [ ] Reconnect recebe state atual, não cache antigo.
- [ ] Restart persiste configs conforme contrato do consumer.
- [ ] Upgrade futuro mantém major/API compatível com consumers.

## 14. Evidências e limites
- **Modlist física:** JAR/mod id/version e consumers presentes.
- **CurseForge oficial:** file 7738312, Release NeoForge 1.21.1, sources jar e fixes 2.3.7.
- **GitHub oficial:** arquitetura V2, auto-sync, permissions, codecs e Extra Config System.
- **Limite:** arquivos de configuração reais do pack não foram fornecidos; nenhum valor específico é afirmado ativo.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
