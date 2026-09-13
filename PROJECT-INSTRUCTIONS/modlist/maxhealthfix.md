# MaxHealthFix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81899337d9479d8b7903
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** MaxHealthFix
- **Arquivo JAR:** `maxhealthfix-neoforge-1.21.1-21.1.4.jar`
- **Versão 1.21.1:** 21.1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Bugfix de lifecycle/persistência para MC-17876: preserva vida atual salva acima do máximo temporariamente conhecido durante carregamento, posterga a restauração para o fim do primeiro tick e corrige respawn/recriação de ServerPlayer. Não cria atributos nem bônus; providers de max_health mantêm authority.
- **Dependências:** OBRIGATÓRIAS na source-line NeoForge 1.21.1: NeoForge, Minecraft `[1.21.1,1.22)`, Bookshelf `[21.1,21.2)` e Prickle `[21.1,21.2)`, side BOTH. Pack: NeoForge 21.1.248, Bookshelf 21.1.81 e Prickle 21.1.11 — ranges satisfeitos.
- **Sobreposição:** Não é equivalente a AttributeFix. AttributeFix corrige ranges de atributos; MaxHealthFix corrige perda/clamp da vida corrente durante load/respawn. Sobreposição somente com outros patches que tentem restaurar/normalizar health no mesmo lifecycle ou regras de respawn que imponham vida diferente.
- **Compatibilidade/Riscos:** Complementar a AttributeFix; relevante para qualquer provider de max_health. Riscos: collision/version drift em mixins de LivingEntity/PlayerList, provider de max_health aplicado tarde demais, restore competindo com regras customizadas de respawn/vida, double-fix, client/server mismatch e ordem com Curios/Apothic/RPG. Source 1.21.1 correlaciona fortemente com 21.1.4 por data/changelog, sem prova byte-a-byte do JAR.
- **Observações:** JAR físico 21.1.4 confirmado. MixinLivingEntity priority 9001 captura Health NBT > max atual e restaura one-shot no TAIL do tick; MixinPlayerList prepara restore no respawn. Config confirmada: mod_enabled=true. Head da branch 1.21.1: 29441700803bf3044933b52d475083de50714a70, alinhado ao changelog 21.1.4.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial file 5824111/release 21.1.4 + source oficial Darkhax-Minecraft/Max-Health-Fix branch 1.21.1, head 29441700803bf3044933b52d475083de50714a70 + arquivos de mixin/config/dependencies auditados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/max-health-fix
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 21.1.4 reconciliada; MC-17876, restore point NBT/tick, respawn NeoForge, config Prickle, hard dependencies, client/server boundary, lifecycle, riscos e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🩺 **Escopo canônico desta ficha:** `maxhealthfix-neoforge-1.21.1-21.1.4.jar`, mod id `maxhealthfix`, exatamente o JAR top-level presente na modlist física. A release oficial 21.1.4 para NeoForge 1.21.1 foi publicada em 19/10/2024. O head oficial da branch `1.21.1`, commit `29441700803bf3044933b52d475083de50714a70`, é do mesmo dia e contém “Disable logo blur”; seu parent imediato `a9725ef05bd76ba55522c78629fc44f030ed5f93` contém “Fix project validation”, os dois itens do changelog 21.1.4. Isso fornece forte correspondência de source-line, mas não constitui prova criptográfica de que o binário instalado foi compilado exatamente desse commit.

## 1. Identidade, versão e autoridade
- **Mod:** MaxHealthFix.
- **JAR físico:** `maxhealthfix-neoforge-1.21.1-21.1.4.jar`.
- **Versão instalada:** `21.1.4`.
- **Mod ID:** `maxhealthfix`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java do projeto:** 21.
- **Upstream:** `Darkhax-Minecraft/Max-Health-Fix`.
- **Branch auditada:** `1.21.1`.
- **Source-line pin:** `29441700803bf3044933b52d475083de50714a70`.
- **Authority de presença/versão:** modlist física. Ela também registra os mixin configs `maxhealthfix.neoforge.mixins.json` e `maxhealthfix.mixins.json`.
- **Papel:** bugfix de lifecycle/persistência de vida atual quando a entidade ou o jogador possui `max_health` acima do valor base que o vanilla conhece no instante inicial de carregamento/recriação.

## 2. Problema que o mod corrige — MC-17876
O upstream descreve o alvo como o bug **MC-17876**: jogadores com mais de 20 pontos de vida podem ter a vida atual reduzida ao valor vanilla durante logout/reload. O comentário do source explica a causa operacional usada pelo mod: durante `LivingEntity.readAdditionalSaveData`, o vanilla lê `Health` antes de todos os modificadores que recompõem a vida máxima estarem efetivamente disponíveis; nesse momento o valor salvo pode ser maior que `getMaxHealth()` e ser clampado.
Exemplo conceitual sustentado pelo README: um jogador com equipamento que fornece +10 de vida máxima pode sair com 25 de vida e voltar com 20 se o valor salvo for clampado antes da recomposição dos atributos.
MaxHealthFix não cria vida máxima, atributos nem bônus. Ele preserva/restaura a **vida atual salva** após dar tempo para os providers de atributos/equipamentos recomporem o `max_health` correto.

## 3. Authority / ownership no modpack
A separação de responsabilidades é importante neste pack:
- **Minecraft/NeoForge e providers de atributos/equipamentos** continuam autoridade sobre `max_health` e seus modificadores.
- Mods como **Apothic Attributes**, sistemas RPG, armaduras, Curios e outros providers podem alterar a vida máxima; MaxHealthFix não substitui nem recalcula esses bônus.
- **MaxHealthFix** é autoridade apenas sobre seu mecanismo corretivo temporário de restore point durante load/respawn.
- **AttributeFix** tem responsabilidade diferente: corrige/expande limites de `RangedAttribute`. Ele não substitui o restore de vida atual feito aqui.
Qualquer mod próprio que altere vida máxima deve aplicar seus modificadores de forma determinística e deixar MaxHealthFix apenas preservar o valor corrente; não deve duplicar esse restore por conta própria sem necessidade comprovada.

## 4. Conteúdo registrado
A branch 1.21.1 auditada não apresenta sistema de conteúdo jogável e a busca por `DeferredRegister` não retornou registradores. O entrypoint NeoForge apenas inicializa a camada comum/configuração.
Não foram identificados na superfície auditada:
- itens próprios;
- blocos ou block entities;
- entidades registradas;
- efeitos, enchantments ou atributos próprios;
- recipes/loot/worldgen;
- menus, telas ou keybinds;
- packets de gameplay próprios.
O mod é um **patch comportamental** por mixins, não um content mod. Se uma futura versão passar a registrar conteúdo, esta conclusão deve ser revalidada para essa versão; ela não deve ser projetada para além da 21.1.4/linha 1.21.1 auditada.

## 5. Inicialização e configuração
`NeoForgeMod` é anotado com `@Mod(Constants.MOD_ID)` e seu construtor chama `MaxHealthFixMod.getInstance().init()`.
`MaxHealthFixMod` mantém uma instância singleton e carrega a configuração por `Prickle` usando `ConfigManager.load(Constants.MOD_ID, new Config())`. Uma segunda chamada explícita de `init()` depois da inicialização lança `IllegalStateException`, impedindo dupla inicialização pela própria API interna.
A classe `Config` expõe um único knob confirmado:
- `mod_enabled = true` — determina se a correção será aplicada.
O formato/caminho físico final do arquivo de configuração não foi inferido nesta ficha; o source confirma o provider `Prickle`, mas não é necessário inventar uma localização de arquivo que não foi validada no runtime da instância.

## 6. Mixin comum — `LivingEntity`
`maxhealthfix.mixins.json` é `required=true`, `defaultRequire=1` e registra `MixinLivingEntity`. O mixin é aplicado a `LivingEntity` com **priority 9001**.

### Captura do restore point
Em `readAdditionalSaveData(CompoundTag)`, no `HEAD`, o mod:
1. verifica se a tag contém `Health` numérico;
2. lê `savedHealth`;
3. se `savedHealth > getMaxHealth()` **e** `savedHealth > 0`, armazena o valor em `maxhealthfix$restorePoint`.
Portanto ele não guarda qualquer valor de vida indiscriminadamente. O restore point só é armado quando a vida persistida é positiva e excede o máximo conhecido naquele instante inicial.

### Restauração diferida
Em `LivingEntity.tick()`, no `TAIL`, se existe restore point:
1. exige `mod_enabled=true`;
2. exige restore point positivo;
3. exige restore point maior que a vida atual;
4. chama `setHealth(restorePoint)`;
5. limpa o restore point para `null` independentemente de ter restaurado ou não.
A correção é, portanto, **one-shot**: existe uma janela de um tick para permitir que modificadores de vida máxima de equipamento/mods sejam recompostos antes de restaurar a vida salva.

## 7. Por que o restore é atrasado até o fim do tick
O histórico oficial do projeto documenta que versões anteriores restauravam cedo demais para fontes modded, incluindo sistemas do tipo Curios/Trinkets. A estratégia atual posterga a restauração para o fim do tick para dar espaço a esses providers.
Isso é particularmente relevante neste modpack, onde a vida máxima pode ser composta por múltiplas fontes. O contrato correto é: provider de atributo recompõe seu modificador; MaxHealthFix observa/restaura a vida corrente depois, sem assumir a autoria do bônus.

## 8. Mixin NeoForge — `PlayerList` / respawn
`maxhealthfix.neoforge.mixins.json` também é `required=true`, `defaultRequire=1` e registra `MixinPlayerList` específico da plataforma NeoForge.
O mixin injeta em `PlayerList.respawn(ServerPlayer, boolean, Entity.RemovalReason)` no ponto em que o novo `ServerPlayer` receberia `setHealth`.
Para o novo jogador:
- se o jogador antigo está morto/morrendo, o restore point recebe `player.getMaxHealth()`;
- caso contrário, recebe `player.getHealth()`.
Logo, a recriação de jogador não usa uma constante 20 como valor corretivo. Em morte, a intenção é respawnar com a vida máxima anterior; em recriações não causadas por morte que passem por esse método, preservar a vida corrente anterior.
O upstream também documentou historicamente que a correção de respawn resolve o retorno do End ao Overworld porque esse fluxo recria o jogador internamente. Isso **não autoriza presumir** que toda troca de dimensão existente no pack passa por `PlayerList.respawn`; cada transporte especial/modded deve ser testado.

## 9. Persistência e lifecycle
O mod não cria um `SavedData`, attachment ou capability persistente próprio para o restore point. `maxhealthfix$restorePoint` é um campo temporário adicionado por mixin à instância de `LivingEntity`.
Superfícies de lifecycle relevantes:
- **save → logout → login:** o valor `Health` persistido é observado em `readAdditionalSaveData`;
- **restart do servidor/mundo:** mesma lógica de leitura NBT precisa preservar vida > base;
- **primeiro tick após load:** ponto efetivo da restauração one-shot;
- **death/respawn:** `PlayerList` prepara restore point para o novo player;
- **recriação de player não-morte:** preserva a vida corrente anterior quando o fluxo passa pelo método alvo;
- **dimension transition:** deve ser validado por tipo de transição; não presumir cobertura universal;
- **equip/unequip:** o mod não é authority sobre o modificador, mas a ordem em que o provider recompõe `max_health` determina se o restore poderá ser aplicado sem clamp.

## 10. Client / Server e sincronização
`gradle.properties` marca `mod_client_only=false`, e o `neoforge.mods.toml` declara suas dependências com `side = BOTH`. O patch comum mira `LivingEntity`; o patch NeoForge mira `PlayerList`, uma classe server-side de gerenciamento de jogadores.
Não há renderer, tela ou HUD próprio identificado. A vida relevante para gameplay continua server-authoritative; o cliente apenas recebe a sincronização normal do estado de entidade/jogador e qualquer HUD de terceiros a representa.
Como os mixin configs são `required=true`, falha de aplicação dos targets/injections é um risco de inicialização e deve ser tratada como incompatibilidade real quando aparecer em log, em vez de ser silenciosamente ignorada.

## 11. Dependências obrigatórias confirmadas
O template `neoforge.mods.toml` da branch 1.21.1 declara como `required`, `side=BOTH`:
- **NeoForge** — build de desenvolvimento 21.1.61 como baseline da branch; o runtime físico do pack usa NeoForge 21.1.248;
- **Minecraft** `[1.21.1, 1.22)`;
- **Bookshelf** `[21.1, 21.2)`;
- **Prickle** `[21.1, 21.2)`.
A modlist física atual contém:
- `bookshelf-neoforge-1.21.1-21.1.81.jar`;
- `prickle-neoforge-1.21.1-21.1.11.jar`.
Ambos satisfazem os ranges declarados pela source-line auditada.

## 12. Integrações concretas com esta modlist

### AttributeFix
Complementar, não duplicado. AttributeFix altera limites aceitáveis de atributos; MaxHealthFix lida com o valor de vida corrente que poderia ser clampado durante lifecycle.

### Providers de `max_health`
Apothic Attributes, equipamentos/RPG e outros sistemas que concedam vida máxima são consumidores indiretos do benefício do fix: seus valores precisam existir a tempo do restore. Isso é coexistência sobre o atributo vanilla, não API de integração explícita.

### Curios/equipamentos
O histórico upstream cita explicitamente o problema de fontes modded como Curios/Trinkets ao justificar a restauração no fim do tick. Como Curios está presente no pack, essa ordem de aplicação é operacionalmente relevante.

### HUDs de vida
Mods visuais/HUD, como barras que representam valores elevados de vida, não são providers de MaxHealthFix. Devem apenas refletir o estado final sincronizado; discrepância visual após login é um teste de sincronização/HUD, não evidência automática de que o restore server-side falhou.

## 13. Riscos técnicos
- **Mixin collision / version drift:** `LivingEntity.readAdditionalSaveData`, `LivingEntity.tick` e `PlayerList.respawn` são pontos sensíveis em pack grande. Outro mixin alterando assinatura/ordem pode quebrar injection ou semântica.
- **Priority 9001:** `MixinLivingEntity` usa prioridade alta; interações com outros mixins no mesmo método devem ser avaliadas por log/bytecode quando houver comportamento anômalo.
- **Provider tardio demais:** se um mod recompuser `max_health` somente depois do TAIL do primeiro tick, `setHealth(restorePoint)` ainda poderá ser clampado ao máximo então disponível. A ficha não presume que um tick resolve todo provider possível.
- **Alteração legítima de vida na janela:** se outro sistema reduzir a vida intencionalmente entre a leitura e o TAIL do primeiro tick, o restore condicionado a `restorePoint > currentHealth` pode elevar novamente o valor. Em load normal essa janela é curta, mas mods próprios não devem inserir dano/normalização de vida nesse ponto sem teste.
- **Respawn com regras customizadas:** o mixin prepara vida máxima anterior quando o jogador morreu. Mods que deliberadamente exigem respawn parcial/1 HP podem competir com essa política se usarem o mesmo lifecycle.
- **Recriação modded de player:** teleports/dimensões que não passam pelo método alvo não recebem automaticamente a proteção de `MixinPlayerList`.
- **Config desabilitada:** o restore point ainda pode ser capturado, porém é descartado no TAIL sem chamar `setHealth`; testes devem distinguir “mod carregado” de “fix habilitado”.
- **Client/server mismatch:** por declarar dependências `BOTH` e carregar mixins comuns, não assumir que instalar apenas no servidor é um modo suportado sem validar metadata/runtime de conexão.
- **Double-fix:** um mod próprio tentando implementar a mesma restauração pode produzir heal indevido ou competição de ordem. Evitar duplicação enquanto MaxHealthFix estiver instalado.

## 14. Multiplayer
O restore point é armazenado na própria instância da entidade, não em mapa global por UUID. Isso reduz risco de estado cruzado entre jogadores.
Ainda assim, multiplayer deve validar:
- dois ou mais jogadores com máximos diferentes entrando simultaneamente;
- respawn simultâneo;
- players com diferentes fontes de `max_health`/Curios;
- reconexão após restart;
- ausência de heal duplicado ou transferência de estado entre entidades.
Não há evidência de fila global, cache compartilhado ou contador de restauração que precise ser sincronizado entre jogadores.

## 15. Matriz de testes obrigatória
- **Dedicated server boot:** iniciar NeoForge 21.1.248 com MaxHealthFix, Bookshelf e Prickle presentes; verificar ausência de erro de mixin/dependency.
- **Login/relogin acima de 20:** sair com `max_health > 20` e vida atual >20; relogar e confirmar preservação do valor salvo dentro do máximo recomposto.
- **Restart completo:** repetir após save + parada + reinício do servidor, não apenas reconexão.
- **Vida abaixo de 20:** confirmar que o fix não produz heal artificial em um valor que não armaria restore point.
- **Equipamento provider:** testar bônus de vida por equipamento/Curios presente no logout e confirmar que o máximo volta antes/até o restore.
- **Equip/unequip limítrofe:** alterar a fonte de max health próximo ao logout/relogin e verificar clamp/normalização coerente.
- **Death/respawn:** morrer com `max_health > 20`; confirmar que o novo player recebe vida coerente com o máximo anterior quando a fonte permanece válida.
- **Respawn com perda de equipamento:** testar regras em que a fonte do bônus não persiste; confirmar que `setHealth` respeita o máximo efetivamente existente e não cria over-health persistente.
- **End → Overworld:** validar o fluxo conhecido de recriação do player.
- **Outras dimensões/modded teleports:** testar separadamente; não assumir cobertura só por End funcionar.
- **Config `mod_enabled=false`:** reproduzir caso acima de 20 e confirmar que a correção deixa de ser aplicada.
- **Multiplayer:** dois jogadores com valores diferentes, relog e respawn simultâneos; exatamente um restore por entidade.
- **HUD/client sync:** confirmar que barras/GUI refletem a vida server-side restaurada sem valor stale.
- **Interação com AttributeFix/Apothic/RPG:** máximos altos e extremos devem ser testados para ordem, clamp e ausência de overflow/comportamento inválido.

## 16. Evidências consultadas
- Modlist física atual: `maxhealthfix-neoforge-1.21.1-21.1.4.jar`, mod id `maxhealthfix`, runtime `21.1.4`, mixins `maxhealthfix.neoforge.mixins.json` e `maxhealthfix.mixins.json`.
- Hashes registrados pela modlist: Modrinth `56243df705c150933642f7108b0d882322147d63`; CurseForge `1435868219`.
- CurseForge oficial: release 21.1.4 NeoForge/MC 1.21.1, arquivo 5824111, publicada em 19/10/2024; changelog “Disable logo blur” e “Fix project validation”.
- Source oficial: https://github.com/Darkhax-Minecraft/Max-Health-Fix, branch `1.21.1`.
- Source-line head: https://github.com/Darkhax-Minecraft/Max-Health-Fix/commit/29441700803bf3044933b52d475083de50714a70 — “Disable logo blur”.
- Parent imediato: https://github.com/Darkhax-Minecraft/Max-Health-Fix/commit/a9725ef05bd76ba55522c78629fc44f030ed5f93 — “Fix project validation”.
- Arquivos auditados: `gradle.properties`, `neoforge.mods.toml`, `Config.java`, `MaxHealthFixMod.java`, `NeoForgeMod.java`, `MixinLivingEntity.java`, `MixinPlayerList.java`, `maxhealthfix.mixins.json`, `maxhealthfix.neoforge.mixins.json` e README oficial.
- **Limitação:** não foi executado decompile/hash do JAR físico nesta etapa para demonstrar correspondência byte-a-byte com o commit; a equivalência source-line é sustentada pela versão/plataforma, data e changelog coincidentes, não por prova binária direta.
