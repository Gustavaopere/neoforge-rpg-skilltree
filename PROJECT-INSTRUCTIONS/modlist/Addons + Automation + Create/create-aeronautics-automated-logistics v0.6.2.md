# Create Aeronautics: Automated Logistics

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#128**: JAR `create_aeronautics_automated_logistics-0.6.2.jar`, mod id `create_aeronautics_automated_logistics`, runtime `0.6.2`, SHA-1 `1954f5c4f72620942851241bd18217197ed59b46`.

## Propriedades do registro

- **Mod:** Create Aeronautics: Automated Logistics
- **Arquivo JAR:** create_aeronautics_automated_logistics-0.6.2.jar
- **Versão 1.21.1 instalada:** 0.6.2
- **Versão oficial mais recente disponível (NeoForge 1.21.1; 25/10/2026):** 0.7.0 (14/10/2026), ainda não instalada no inventário físico.
- **Categoria:** Tecnologia, Automação
- **Função:** Automatiza rotas logísticas de veículos/contraptions do Create Aeronautics, permitindo deslocamento e operações de docking sem controle manual contínuo.
- **Dependências:** Create Aeronautics 1.3.2 está fisicamente presente; Sable é a base de sublevels/restore usada pelo stack. Integrações físicas relevantes: JourneyMap 6.0.8, Tom's Simple Storage 2.4.2 e Sophisticated Storage 1.5.91. Simurail é compat experimental, não tratado como hard dependency.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: restore pendente/duplicado, route resume após restart, dock queue race, force-loading/performance, cargo dupe/loss e API drift Sable/Aeronautics/storage. 0.6.2 corrige scheduled routes que falhavam quando Sable ainda não havia restaurado o veículo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_aeronautics_automated_logistics` 0.6.2 + CurseForge/Modrinth oficiais da release 0.6.2 já auditados. Revalidação física/documental em 20/09/2026.
- **Observações:** JAR físico `create_aeronautics_automated_logistics-0.6.2.jar`, mod id `create_aeronautics_automated_logistics`, runtime 0.6.2. Release oficial NeoForge 1.21.1 de 17/08/2026; adiciona filtros do Logistics Terminal e opção de force-load para veículos Simurail automatizados.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #127: create_aeronautics_automated_logistics-0.6.2.jar / 0.6.2 confirmados; filtros do terminal, force-load opcional, Restoring Vehicle e fix de scheduled routes após unloaded travel permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Não é duplicata de Create: Advanced Logistics; este mod atua especificamente em rotas logísticas de veículos Aeronautics.

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
JourneyMap 6.0.8 consta do inventário atual, portanto a integração visual deve entrar no smoke test.
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

## Revalidação de presença e upstream — 02/10/2026

- A autoridade física disponível já contém `create_aeronautics_automated_logistics-0.6.2.jar`, mod id `create_aeronautics_automated_logistics`, runtime `0.6.2`, SHA-1 `1954f5c4f72620942851241bd18217197ed59b46`.
- Portanto, a menção do usuário a este mod entre as novas adições **não cria uma segunda entrada** e não altera a contagem física histórica de 587.
- **Registro histórico de 02/10/2026:** naquele momento, o CurseForge apontava `0.6.2` como release mais recente para Minecraft 1.21.1 / NeoForge (17/08/2026). A publicação de `0.7.0` em 14/10/2026 superou esse registro.
- **Na data desta revalidação histórica**, não havia release posterior a 0.6.2; a revisão do intervalo 0.6.2 → 0.7.0 consta na seção de 25/10/2026.
- O conteúdo operacional deste dossiê foi preservado; não houve promoção de versão nem criação de dossier duplicado.

Fonte upstream revalidada:
- https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics
- https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics/files/all?version=1.21.1

## Estado de certificação — 02/10/2026

- A busca do workspace e a consulta estruturada da base Notion não recuperaram a ficha canônica correspondente a **Create Aeronautics: Automated Logistics** nesta rodada.
- A presença/JAR/versão física está resolvida pela autoridade local e o upstream permanece em 0.6.2, mas a nota histórica de certificação permanece separada da atualização técnica atual.
- Pelo protocolo fail-closed do projeto, o filename fica **SEM `✅-`** até que a ficha canônica possa ser re-fetched e comparada integralmente.

## Atualização técnica/documental — 25/10/2026: release oficial 0.7.0

> **Não confundir disponível com instalado.** O snapshot físico mantém **#128**, JAR `create_aeronautics_automated_logistics-0.6.2.jar`, runtime `0.6.2`, mod id `create_aeronautics_automated_logistics` e SHA-1 `1954f5c4f72620942851241bd18217197ed59b46`. A nova **release upstream 0.7.0** (NeoForge/Minecraft 1.21.1; **14/10/2026**) é somente alvo para pesquisa e QA. Não houve instalação, teste em mundo real, recertificação nem mudança de nome ou pasta do dossier.

### Fontes e artefatos

- Projeto oficial CurseForge: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics
- Changelog oficial, arquivo CurseForge **9184782**, projeto **1537420**: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics/files/9184782
- Releases oficiais NeoForge 1.21.1: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-automated-logistics/files/all?version=1.21.1
- Versões oficiais no Modrinth: https://modrinth.com/mod/create-aeronautics-automated-logistics ; código/bugs: https://github.com/sprocketaudio/Create-Aeronautics-Automated-Logistics
- Artefato 0.7.0: `create_aeronautics_automated_logistics-0.7.0.jar`, Modrinth `ESwTkggq`, **1.766.088 bytes**; SHA-1 `0da40850bdb4f3eb18340e6adb466d2af5fba50f`; SHA-512 `7a3e7e2942bf076e545dc7abf258b0e014244bf14aae84134f31d4e27b8d50da525ff41d23eaebcd24483c6f1206f269931038b18e7be04606fccc6c752ef103`.
- Baseline 0.6.2: Modrinth `EgPi4wq9`, **1.567.622 bytes**, SHA-1 `1954f5c4f72620942851241bd18217197ed59b46`.
- Em comparação binária anterior por caminho e SHA-256 dos arquivos internos dos JARs oficiais: **457 → 516 arquivos regulares; +70 novos, −11 removidos, 107 modificados e 339 idênticos**. Diff estrutural não é teste funcional.

### Changelog 0.6.2 → 0.7.0: o que mudou

**Transferências, estado de cargo e docking**
- Condições de item/fluid agora incluem conteúdo temporariamente retido dentro de **Docking Connectors**; não considerar descarga concluída enquanto o buffer ainda contém itens ou fluido.
- Condições novas de espera de cargo exigem, **por padrão, resultado estável durante 2 segundos**. A regra reduz falsas conclusões por oscilações de transferência.
- Melhor entrega de buffers no momento de fechamento da conexão física; remover conectores elimina vínculos obsoletos de doca e estado redstone. Links de carga cujo storage carregado deixou de ser suportado são limpos.
- Docas continuam **reservadas por veículos parados, pausados ou em falha** dentro da zona protegida. Ocupação e reserva persistem após reload/restauração; conexões de docking podem ser reparadas no reload.
- Retomada de veículo parado mantém a área de saída reservada até liberação física efetiva, evitando dupla ocupação da doca. Melhorias quando o conector é desmontado/reconstruído durante a restauração.

**Observabilidade, mapas, permissões**
- **Create Display Link** ligado a **Airship Station**: contagem inbound/outbound (inclusive filas), veículos presentes, status livre/aproximando/ocupado/partindo e comprimento da fila; visão filtrada por embarcação mostra trajeto/próxima estação/status.
- Seletor de embarcações respeita propriedade, autorização e integração opcional com **FTB Teams**. Com **Jade**, tooltips mostram nome de estação e veículo com transponder.
- Marcadores de veículo agora também em **Xaero's Minimap / World Map**, além de FTB Chunks e JourneyMap; toggles individuais para integrações e configuração compartilhada de escala dos ícones.
- **Logistics Terminal**: melhoria do mapa 3D/de mesa, linhas, cores de trajetos, brilho e prioridade visual de ícones sobre rotas/estações.

**Novos alvos de Cargo Link e animações**
- Suporte a **Create: Connected Item Silos** (inclusive multibloco vertical), **Create: Iron Vaults** e **Create Propulsion: Simulated Platinum Tanks**; só integrar provedores presentes e com capability/endpoint válido.
- Animação de **Mechanical Bearing**, aceleração/desaceleração visual de Small Propeller e Propeller Bearing e compatibilidade Simurail bogey antigo/atual. São **efeitos visuais**, não propulsão física adicional.

**Limites reais da release**
- Fundamentos internos de **Redstone Drive** (Drive Modules, calibração e orientação de rota) aparecem na build, porém **Advanced Transponder e Drive Module NÃO estão disponíveis publicamente na 0.7.0**. Não criar receitas, quests, perks nem integrações esperando esses recursos.
- O sistema continua baseado em **repetição de rota gravada**; não há pathfinding, reroute automático, autopilot de controle livre ou colisão off-screen garantida.

### Compatibilidade com o inventário disponível

| Componente | Referência publicada pelo autor / metadata | Snapshot físico do pack | Situação |
|---|---|---|---|
| Minecraft | 1.21.1 | 1.21.1 | Coincide |
| NeoForge | metadata `>=21.1.229`; testes 21.1.229 | 21.1.250 | Requisito numérico atendido; QA pendente |
| Create | `>=6.0.10`; testes 6.0.10 | 6.0.10 | Coincide |
| Create Aeronautics / `aeronautics_bundled` | `>=1.3.0`; testes 1.3.0 | 1.3.2 | Requisito numérico atendido |
| **Sable** | **testado com 2.0.6** | **2.0.5** | **Divergência importante; requer QA** |
| **Create: Simulated** | **testado com 1.3.0** | JAR top-level não identificado inequivocamente | **A confirmar; não confundir com Propulsion: Simulated** |
| Create: Connected | testado com 1.3.3 | 1.3.3 | Integração de silos |
| Create Propulsion: Simulated | testado com 1.1.5 | 1.1.5 | Integração de Platinum Tanks |
| Jade | testado com 15.10.6 | 15.10.6 | Integração de nomes/tooltips |
| FTB Chunks / FTB Teams | opcionais 2101.1.22 / 2101.1.11 | 2101.1.22 / 2101.1.11 | Coincidem |
| JourneyMap | referência opcional 6.0.9 | 6.0.8 | Divergência da referência de testes |
| Tom's Simple Storage | referência opcional 2.4.2 | 2.4.2 | Coincide |

Também há testes upstream com **Create: Iron Vaults 1.2.0**, **Simurail 0.1.0**, **Xaero's Minimap 26.6.0** e **Xaero's World Map 1.47.0**; não presumir que estejam instalados. A metadata NeoForge do próprio JAR é autoridade para requisitos declarados; a lista de testes upstream **não implica que todas as versões citadas sejam hard dependencies**.

### Contratos para desenvolvermos mods e perks

1. **Authority no servidor:** posição/restauração de veículo, rota/schedule, estado de dock, permissão (uso versus controle administrativo), transferência e conclusão só podem ser validados em estado server-side. Displays/mapas não certificam uma entrega.
2. **Recompensa e anti-dupe:** quests, XP e perks não devem progredir ao começar a rota ou detectar chegada; exigir confirmação idempotente de docking e cargo transfer (incluindo buffers dos conectores), com deduplicação por operação/veículo/parada.
3. **Reinício e chunk unload:** preservar IDs/eventos e reconciliar journal após restauro; não executar duas vezes os efeitos de recompensa, transferência ou liberação de fila. Veículos paused/faulted ainda ocupam a doca.
4. **Storage:** checar capabilities de item/fluid em endpoints carregados, multiblocos e proxy; detectar/remover links órfãos e tratar endpoints incompatíveis sem perder ou duplicar cargo. Dar atenção a Connected, Tom's e Sophisticated.
5. **ACL e multiplayer:** validar proprietário, grupo/equipe e distinção entre `use` (chegar/operar rota) e `control` (alterar links e administração). Nunca confiar em cliente para autorizar comando.
6. **Futuras APIs:** evitar depender de classes internas/temporárias de Redstone Drive; desenvolver em torno de contratos públicos verificáveis, quando disponíveis.
7. **Performance:** controlar force-loading, filas e restauro; coletar logs por subsistema (rota, dock, cargo, restore e sincronização).

### QA necessário para atualizar o JAR físico (não executado)

- [ ] Backup do mundo/config e snapshot exato do pack, hashes e estado dos veículos.
- [ ] Conferir **Sable 2.0.5 ↔ 2.0.6** e a identidade/presença de **Create: Simulated 1.3.0**; rodar smoke test dedicado NeoForge 21.1.250.
- [ ] Repetir rota com chunk unload, restart e restauração; verificar progresso único e sem loops.
- [ ] Disputar uma doca com dois veículos; pausar/falhar no dock e só liberar próximo após clearance real.
- [ ] Testar buffer de conector e janela estável de 2 segundos, tanto item como fluido; sem perdas/duplicações.
- [ ] Desmontar/recriar conectores e relogar; verificar links, redstone e cargo.
- [ ] Verificar Display Links, permissões de FTB Teams, tooltips Jade, JourneyMap/FTB Chunks e Xaero somente quando presente.
- [ ] Testar Cargo Links de providers presentes, incluindo silos verticais e Platinum Tanks; Iron Vaults apenas quando confirmado.
- [ ] Medir custo de chunks ativos, docking, força de carregamento e efeitos de bearing/propeller.
- [ ] Validar integração de quests/perks com confirmação idempotente server-side.
- [ ] Nenhuma linha deste checklist deve ser marcada aprovada sem teste real no perfil.

**Conclusão documental:** changelog 0.6.2 → 0.7.0 registrado para engenharia dos nossos mods. **JAR instalado 0.6.2, upgrade físico não executado, QA pendente, certificação/`✅-` e diretório inalterados.**
