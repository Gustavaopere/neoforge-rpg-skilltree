# Create Aeronautics: Automated Logistics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812794efecf1b99a1ff1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Automated Logistics
- **Arquivo JAR:** `create_aeronautics_automated_logistics-0.6.2.jar`
- **Versão 1.21.1:** 0.6.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Automatiza rotas logísticas de veículos/contraptions do Create Aeronautics, permitindo deslocamento e operações de docking sem controle manual contínuo.
- **Dependências:** Create Aeronautics 1.3.2 está fisicamente presente; Sable é a base de sublevels/restore usada pelo stack. Integrações físicas relevantes: JourneyMap 6.0.7, Tom's Simple Storage 2.4.2 e Sophisticated Storage 1.5.91. Simurail é compat experimental, não tratado como hard dependency.
- **Sobreposição:** Não é duplicata de Create: Advanced Logistics; este mod atua especificamente em rotas logísticas de veículos Aeronautics.
- **Compatibilidade/Riscos:** Riscos: restore pendente/duplicado, route resume após restart, dock queue race, force-loading/performance, cargo dupe/loss e API drift Sable/Aeronautics/storage. 0.6.2 corrige scheduled routes que falhavam quando Sable ainda não havia restaurado o veículo.
- **Observações:** JAR físico `create_aeronautics_automated_logistics-0.6.2.jar`, mod id `create_aeronautics_automated_logistics`, runtime 0.6.2. Release oficial NeoForge 1.21.1 de 17/08/2026; adiciona filtros do Logistics Terminal e opção de force-load para veículos Simurail automatizados.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create Aeronautics: Automated Logistics 0.6.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; recorded routes, Transponder/schedules, docking, unloaded travel, vehicle restore, cargo endpoints e force-loading catalogados para 0.6.2.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🛩️ **ESCOPO CANÔNICO.** Runtime físico: `create_aeronautics_automated_logistics-0.6.2.jar`, mod id `create_aeronautics_automated_logistics`, versão `0.6.2`. Automatiza rotas gravadas de veículos Create Aeronautics, docking e transferência logística, inclusive enquanto o veículo está unloaded.

## 1. Modelo de rota
O fluxo oficial é **recorded-route playback**, não pathfinding aéreo autônomo: o jogador voa cada trecho entre estações, grava a rota no Ship Transponder e monta um schedule; depois o veículo repete os trechos.
Isso é importante para authority: a trajetória gravada é o contrato operacional, não uma rota recalculada dinamicamente por IA.

## 2. Stations, Transponder e schedule
Airship Stations definem destinos; o Transponder identifica o veículo, armazena/usa seus route legs e schedule. O schedule pode aguardar condições, repetir paradas e coordenar docking. Docks ocupados entram em fila em vez de permitir sobreposição física de veículos.

## 3. Unloaded travel e restauração
Veículos ativos continuam progredindo quando os chunks não estão carregados e podem reaparecer para docking físico/cargo transfer. Route progress e schedules são documentados como persistentes através de chunk unload, game reload e server restart.
A 0.6.2 adiciona status **Restoring Vehicle** e logging específico de tentativa/sucesso/falha de restauração. Também corrige scheduled routes que não continuavam quando Sable ainda não havia restaurado o veículo após viagem unloaded.

## 4. Force loading 0.6.2
A build adiciona server config para manter veículos Simurail automatizados ativos force-loaded via Sable durante toda a rota gravada; upstream informa que a opção vem habilitada por padrão. Isso é superfície de performance, não garantia de que todo veículo deva ser mantido loaded.

## 5. Logistics Terminal
O Logistics Terminal oferece mapa próprio da rede com stations, rotas e veículos ativos. 0.6.2 adiciona filtros `All`, `Active` e `Attention`. A integração também suporta JourneyMap para markers, mas o terminal não depende de mapa externo para operar.
JourneyMap 6.0.7 está fisicamente presente no pack, portanto a integração visual deve entrar no smoke test.

## 6. Cargo e storage
O projeto documenta integração de cargo com storage vanilla/Create e vários providers externos. Relevante ao pack atual: **Tom's Simple Storage 2.4.2** é suportado por Connector/interface/proxy/inventory endpoints; Sophisticated Storage também está presente. A bridge não se torna owner desses inventários: deve transferir exatamente a quantidade acordada entre endpoints.

## 7. Simurail experimental
0.6.x possui compat experimental com Simurail Stations/rotas ferroviárias gravadas, separadas das networks de airships. Não é rail-aware pathfinding nem substituto de dispatch nativo futuro. A modlist física atual não foi usada aqui para afirmar Simurail como provider obrigatório.

## 8. Authority e multiplayer
Aeronautics/Sable continuam owners do body/sublevel físico; Automated Logistics owns route/schedule orchestration. Cargo providers own inventories. O servidor deve validar route state, docking, ownership/permissão e transferência; mapa/UI cliente não é authority.

## 9. Riscos
1. Vehicle restoration fica preso em estado pendente.
2. Route resume duas vezes após restart e duplica movimento/transferência.
3. Dock queue libera dois veículos simultaneamente.
4. Force-loading amplia custo de server e física.
5. Cargo dupe/loss entre storage endpoints.
6. Route gravada torna-se inválida após alteração de world/terrain/station.
7. Sable/Aeronautics API drift quebra restore/docking.
8. Tom's/Sophisticated endpoint muda de API ou fica parcialmente loaded.

## 10. Boundary para quests/perks
“Rota iniciada” não equivale a entrega concluída. Crédito deve ocorrer apenas após docking/transfer/objetivo final confirmado e deduplicável. Progresso unloaded precisa ser reconciliado server-side antes de conceder recompensa.

## 11. Matriz de testes
- [ ] Dedicated server inicia com 0.6.2 + Aeronautics 1.3.2.
- [ ] Rota gravada repete estação→estação e persiste após restart.
- [ ] Veículo unloaded retorna para docking sem teleporte/duplicação incorretos.
- [ ] Dois veículos disputando dock entram em fila corretamente.
- [ ] `Restoring Vehicle` converge para success/failure sem loop infinito.
- [ ] Cargo Tom's Simple Storage transfere quantidade exata.
- [ ] JourneyMap markers acompanham estado sem virar authority.
- [ ] Force-load configurado não excede política de performance esperada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limite
CurseForge/Modrinth oficiais confirmam 0.6.2, recorded routes, stations/transponders, unloaded travel, Logistics Terminal, cargo integrations, Simurail experimental e fixes de restauração. Config efetiva do servidor e rotas reais do mundo não foram lidas; não presumir que toda opção/default permaneça inalterada após configuração do pack.
