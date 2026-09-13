# Sable mob ragdoll corpses

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db81eb9891f54fd0143123
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `mob_ragdoll_corpse-1.1.5.jar`, mod id `mob_ragdoll_corpse`, runtime `1.1.5`; Sable `2.0.5`, Sable Ragdolls `0.7.5`, Ragdoll Reactions `0.7.0` e Sable Ragdolls Patch `1.9` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion usa a expressão `modlist.txt física atual`; a authority física efetivamente disponível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o stack Sable/Ragdolls/Corpse acima está confirmado.
- A release física e o CurseForge identificam o runtime como **1.1.5**, mas o source oficial auditado no commit `c5ae380ca4ee197ccdfd7c5130b613c60cf20f99`, da mesma data da release, ainda declara **1.1.1** em `gradle.properties` e `neoforge.mods.toml`. Esse source é usado apenas como evidência arquitetural/comportamental próxima da release, não como prova de identidade binária exata de 1.1.5.

## Propriedades do banco

- **Mod:** Sable mob ragdoll corpses
- **Arquivo JAR:** `mob_ragdoll_corpse-1.1.5.jar`
- **Versão 1.21.1:** 1.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Mobs, Visual, QoL
- **Função:** Converte dano fatal de mobs em cadáveres físicos ragdoll persistentes por tempo controlado, usando Sable/Sable Ragdolls; inclui física de impacto/knockback, persistência especial de tamed mobs, lista de entidades ignoradas, compat opcional com TaCZ e blood particles client-side.
- **Dependências:** Obrigatórias: Sable (source próximo declara `>=2.0.3`) e Sable Ragdolls / `sable_player_ragdoll` (`>=0.7.5`). Ragdoll Reactions é opcional. TaCZ aparece como integração opcional no source; não é hard dependency.
- **Sobreposição:** Compartilha death/ragdoll lifecycle com Sable Ragdolls, Ragdoll Reactions e Sable Ragdolls Patch. O patch físico possui `sable_player_ragdoll_patch.corpse.mixins.json`. Não há conflito presumido; é uma superfície de integração obrigatória para teste.
- **Compatibilidade/Riscos:** Required: Sable e Sable Ragdolls; Ragdoll Reactions é opcional. O pack instala Sable 2.0.5, Sable Ragdolls 0.7.5, Ragdoll Reactions 0.7.0 e Sable Ragdolls Patch 1.9 com mixin corpse-specific. Risco principal: interceptação de dano fatal/death flow, persistência de corpses e física pós-morte.
- **Observações:** JAR físico `mob_ragdoll_corpse-1.1.5.jar`, mod id `mob_ragdoll_corpse`, runtime 1.1.5. CurseForge file ID 8485385, Release de 22/07/2026. Changelog: knockback tweaks, TaCZ bullet knockback reduzido em 50%, maior resistência a kinetic damage e blood particles client-side default off. Source próximo usa attachments/timers e RagdollAPI, mas ainda declara versão 1.1.1.
- **Procedência:** modlist.txt física atual + CurseForge oficial de 1.1.5 + relations oficiais + repositório oficial auditado no commit c5ae380ca4ee197ccdfd7c5130b613c60cf20f99, explicitamente divergente em version metadata.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ragdoll-mob-corpses/files/8485385 | https://github.com/trangiaan13052025-cloud/Ragdoll_mobCorpse/commit/c5ae380ca4ee197ccdfd7c5130b613c60cf20f99
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — runtime 1.1.5 confirmado pelo CurseForge file ID 8485385. Source oficial auditado em commit de 22/07/2026, porém ainda rotulado 1.1.1; divergência documentada e claims internas limitadas ao que o source/release sustentam.
- **Histórico da decisão:** 2026-09-10 — ficha reconstruída no padrão Alex's Mobs. Release 1.1.5 e dependências oficiais confirmadas. Source oficial de 22/07/2026 auditado com divergência de metadata 1.1.1; a divergência foi preservada em vez de tratar o source como pin binário exato.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DESTA FICHA.** O runtime físico é `mob_ragdoll_corpse-1.1.5.jar`, mod id `mob_ragdoll_corpse`, versão `1.1.5`. A release oficial exata é o CurseForge file ID `8485385`, de 22/07/2026. O repositório oficial foi auditado em um commit da mesma data (`c5ae380ca4ee197ccdfd7c5130b613c60cf20f99`), mas tanto `gradle.properties` quanto `neoforge.mods.toml` ainda declaram `1.1.1`. Portanto, esse source é usado para arquitetura/comportamento observável, **não** como prova de identidade binária exata de 1.1.5. Onde release e source divergem, esta ficha preserva explicitamente a divergência.

## 1. Identidade, versão e authority
- **Nome no runtime:** Sable mob ragdoll corpses.
- **Projeto público:** Ragdoll mob corpses.
- **JAR físico:** `mob_ragdoll_corpse-1.1.5.jar`.
- **Mod id:** `mob_ragdoll_corpse`.
- **Runtime:** `1.1.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **CurseForge:** project ID `1586005`, file ID `8485385`, Release, 22/07/2026.
- **Ambiente oficial:** Client & Server.
- **Licença:** MIT.
- **Autor público:** TotallyNotAndy.
- **Papel no pack:** converter morte de mobs em cadáveres ragdoll físicos que permanecem no mundo por um período, integrados ao stack Sable.

## 2. Dependências e stack físico
A relação oficial da release 1.1.5 lista:
- **Sable** — Required Dependency.
- **Sable: Ragdolls** — Required Dependency.
- **Ragdoll Reactions** — Optional Dependency.

O source auditado explicita ranges mais precisos:
- `sable` **>=2.0.3**.
- `sable_player_ragdoll` **>=0.7.5**.
- `tacz` como dependency opcional.
- NeoForge **>=21.1.233** e Minecraft `1.21.1` naquele estado de source.

O pack físico satisfaz os requisitos centrais:
- `sable-neoforge-1.21.1-2.0.5.jar` — Sable 2.0.5.
- `sable_player_ragdoll-1.21.1-0.7.5.jar` — Sable Ragdolls 0.7.5.
- `ragdoll_reactions-1.21.1-0.7.0.jar` — integração opcional presente.
- `sable_player_ragdoll_patch-1.21.1-1.9.jar` — patch presente e sua modlist física declara `sable_player_ragdoll_patch.corpse.mixins.json`, isto é, possui superfície explícita voltada a corpse.

## 3. O que a release 1.1.5 declara ter alterado
O changelog oficial do file ID 8485385 registra:
- ajustes em knockback physics;
- redução de 50% da força de knockback de bullets do TaCZ;
- cadáveres mais resistentes a kinetic damage;
- adição de client settings;
- blood particles simples, desligadas por padrão.

Esses itens são authority de release para 1.1.5. O source próximo é consistente com eles em várias superfícies, mas continua identificado internamente como 1.1.1 e, por isso, não substitui a release como authority de versão.

## 4. Modelo de cadáver/ragdoll no source oficial auditado
A classe `ragdollOnDeath` mostra que o mod não cria apenas um efeito visual temporário. Ele intercepta dano fatal de `Mob`, mantém a entidade no mundo e a converte para um estado de corpse/ragdoll usando a API de Sable Player Ragdoll.

Fluxo observado no source:
1. `LivingDamageEvent.Pre` recebe o dano.
2. O fluxo de corpse é processado no lado server.
3. Apenas entidades que são `Mob` entram nesse caminho.
4. IDs configurados como ignorados e damage types ignorados saem do fluxo.
5. Apenas dano fatal inicia a transição.
6. Se a entidade já está marcada como corpse, o fluxo evita uma segunda conversão.
7. O dano fatal é neutralizado para permitir que o corpo continue representado pela própria mob convertida.
8. A mob fica com AI desabilitada, silent e marcada para persistência.
9. A API `RagdollAPI` do Sable Player Ragdoll lança a entidade em ragdoll com duração efetivamente indefinida no provider, enquanto o addon mantém sua própria lógica de lifetime/remoção.

**Ownership:** o addon controla a semântica de corpse e timer; Sable Player Ragdoll fornece a camada de ragdoll/physics usada para a entidade.

## 5. Lifetime, remoção e persistência
O source auditado declara:
- lifetime base de `(5 * 60) * 20` ticks = **6000 ticks, aproximadamente 5 minutos**.
- entidades tratadas como short-lived usam metade do timer em sua lógica.
- se a morte não tiver um Player como source entity, existe override de `20 * 30` = **600 ticks, aproximadamente 30 segundos**.
- mobs tamed recebem `persistent_ragdoll = true`.
- no tick de mob, entidades tamed são excluídas da rotina comum de decremento/remoção, consistente com corpse persistente de companheiros.
- para corpses não persistentes, o contador é decrementado e, ao expirar, a lógica leva a entidade ao término do estado/ciclo de morte.

### Reentrada no mundo/chunk
Em `EntityJoinLevelEvent`, se uma mob está marcada com attachment de corpse mas não está atualmente em ragdoll, o server chama novamente a API para relançá-la como ragdoll. Isso é evidência forte de que o estado de corpse foi projetado para sobreviver a reentrada/reload da entidade e restaurar sua representação física.

Esta ficha não transforma isso em promessa absoluta de persistência em todos os cenários de save sem runtime test; registra a intenção/implementação observada e exige validação prática.

## 6. Attachments e estado
A inicialização do mod registra **entity attachments** e partículas. O source usa attachments/flags para controlar estados como corpse, lifetime e persistência. Essa escolha é operacionalmente importante porque:
- a própria entidade continua sendo o suporte do cadáver;
- chunk unload/rejoin precisa restaurar ragdoll a partir do estado persistido;
- outros mods que esperam remoção imediata da mob após morte podem encontrar uma entidade ainda presente e marcada de forma especial.

Não atribuir nomes de NBT externos ou contrato público de capability sem source/API explícito; para integrações próprias, usar os attachments/APIs reais do código instalado.

## 7. Física de impacto e knockback
O source auditado implementa cálculo de força de ragdoll conforme a origem do dano:
- projéteis/direct entities podem influenciar direção/força inicial;
- ataques corpo a corpo de Player usam direção de olhar/movimento e consideram enchantments como **Knockback** e **Sharpness** na composição observada;
- há caminho opcional para TaCZ.

Também existe tratamento de `FLY_INTO_WALL` quando a entidade já está em corpse: o dano dessa superfície cinética é reduzido para uma fração do valor original no source auditado. Isso é consistente com o changelog 1.1.5 de tornar corpses mais resistentes a kinetic damage, mas a equivalência numérica exata entre o source rotulado 1.1.1 e o binário 1.1.5 não é presumida além do que o changelog confirma.

## 8. TaCZ opcional
O mod detecta `tacz` opcionalmente no source. A release 1.1.5 menciona especificamente redução de 50% da força de knockback de bullets TaCZ.

Consequências:
- TaCZ **não é hard dependency**.
- quando presente, mortes por armas TaCZ precisam de teste próprio porque participam do cálculo de ragdoll.
- ausência de TaCZ não deve impedir o carregamento normal do mod.

## 9. Blood particles e client config
O source possui `clientConfig` com uma opção booleana de blood effects, default **false**. A release 1.1.5 descreve blood particles simples e também afirma que ficam desligadas por padrão.

O efeito de bleeding/partículas é client-side na apresentação. O source só cria as partículas quando a configuração client está habilitada. A janela temporal observada para o flag de bleeding cobre a parte inicial do lifetime do corpse, mas detalhes visuais exatos devem ser validados no binário 1.1.5.

Quando `cloth_config` está carregado, o source registra uma config screen no client. Cloth Config não foi promovido aqui a hard dependency do mod: o próprio código condiciona a UI à presença dele.

## 10. Lista de entidades ignoradas
`serverConfig` define `ignoredEntityIds`:
- lista de IDs de mobs que devem ser ignorados para corpse creation quando causam bug;
- entradas são validadas como `ResourceLocation`.

O default do source auditado é uma lista contendo `minecraft:`. Como essa string é uma particularidade do estado de source e a versão interna continua 1.1.1, o valor efetivamente serializado no config do pack deve ser lido do arquivo/runtime antes de automatizar alterações.

Essa opção é a primeira ferramenta para excluir uma mob modded problemática sem remover todo o sistema de corpses.

## 11. Tipos de morte e prevenção de duplicidade
A lógica observada inclui guardas para:
- só processar fatal damage;
- evitar reprocessar uma entidade já corpse;
- respeitar entity IDs ignorados;
- respeitar damage types ignorados pelo fluxo;
- diferenciar source Player de outras origens para lifetime;
- tratar colisão/kinetic damage em corpses de forma especial.

Isso reduz risco de corpse duplicado, mas a compatibilidade com mods que cancelam/reexecutam death events ainda precisa de teste em runtime.

## 12. Interação com o ecossistema Sable do pack
### Sable 2.0.5
É dependency obrigatória e está acima do mínimo 2.0.3 observado no source.

### Sable Ragdolls 0.7.5
É dependency obrigatória e está exatamente no mínimo declarado pelo source. A corpse layer chama sua `RagdollAPI` diretamente.

### Ragdoll Reactions 0.7.0
É listado oficialmente como dependency opcional do projeto e está instalado. Deve ser testado porque acrescenta comportamento sobre ragdolls, mas sua presença não é necessária para a funcionalidade base de corpse.

### Sable Ragdolls Patch 1.9
A modlist física mostra um mixin set `sable_player_ragdoll_patch.corpse.mixins.json`. Isso é evidência objetiva de que o patch possui código específico para o domínio corpse. A ficha não presume quais bugs ele corrige sem auditar seu source; considera-o uma integração de alto valor para teste conjunto.

## 13. Client/server e multiplayer
CurseForge classifica o projeto como **Client & Server** e o manifesto auditado marca Sable/Sable Player Ragdoll em `BOTH`. A conversão de corpse ocorre server-side; a física/apresentação precisa permanecer sincronizada para os clientes.

Superfícies de multiplayer:
- todos os clientes devem ver a mesma entidade como corpse;
- timer/remoção deve ser authoritative no server;
- chunk unload/rejoin precisa restaurar ragdoll consistentemente;
- partículas de sangue são configuração client-side e podem diferir visualmente sem alterar o estado do corpse.

## 14. Sobreposição semântica com sistemas de morte
Este mod mantém a própria mob após dano fatal em vez de seguir imediatamente o fluxo padrão de remoção. Qualquer mod que:
- injete em `LivingDamageEvent`/death flow;
- conte kills na remoção da entidade;
- gere cadáver próprio;
- altere loot/deathTime;
- teleporte/remove mobs mortas;
- aplique física própria pós-morte;

pode compartilhar superfície técnica.

Isso é uma **classe de risco**, não uma incompatibilidade confirmada.

## 15. Riscos técnicos
- **Source/version drift:** source próximo da release continua rotulado 1.1.1; detalhes internos são evidência arquitetural, não identidade exata do binário 1.1.5.
- **Death-flow interception:** dano fatal é transformado em estado de corpse; mods que esperam morte/remoção imediata podem divergir.
- **Persistência:** tamed corpses e rejoin logic precisam ser validados em save/restart/chunk unload.
- **Physics:** knockback, wall collision e outras forças podem interagir com mods de combate/physics.
- **Sable patch:** existe patch corpse-specific instalado; mudanças futuras em qualquer parte do stack podem alterar injection points.
- **TaCZ:** compat opcional específica deve ser testada quando TaCZ estiver presente.
- **Volume de entidades:** corpses permanecem por tempo relevante, aumentando entidades ativas/persistidas em cenas de combate massivo.
- **Tamed mobs:** persistência prolongada de companion corpses pode acumular entidades se não houver fluxo esperado de limpeza/interação.

## 16. Matriz de validação obrigatória
> Itens abaixo são testes requeridos, não resultados já aprovados.

### Boot/dependências
- Servidor dedicado com Sable 2.0.5 + Sable Ragdolls 0.7.5.
- Confirmar ausência de dependency/mixin/API error.
- Cliente conectar e visualizar ragdolls.

### Morte básica
- Matar cow/zombie/hostile/passive por melee.
- Confirmar exatamente um corpse por morte.
- Verificar AI disabled/silence/comportamento físico observado.
- Esperar lifetime e confirmar limpeza.

### Origem de dano
- Player melee.
- Player projectile.
- mob/environmental source sem Player.
- kinetic/fly-into-wall sobre corpse.
- TaCZ bullet quando TaCZ estiver presente.

### Lifetime/persistência
- Medir corpse normal próximo de 5 min conforme source.
- Morte sem Player e observar override de ~30 s conforme source.
- Tamed mob death e persistência esperada.
- Chunk unload/reload.
- sair/entrar no mundo.
- restart completo do servidor.
- troca de dimensão quando reproduzível.

### Config
- `ignoredEntityIds` com uma entidade vanilla e uma modded problemática em mundo de teste.
- blood effects OFF e ON em clients distintos.
- config screen quando Cloth Config estiver disponível.

### Stack Sable
- Com `ragdoll_reactions-0.7.0` ativo.
- Com `sable_player_ragdoll_patch-1.9` ativo.
- múltiplos corpses simultâneos.
- corpse sendo empurrado/afetado por physics/reactions.

### Multiplayer/performance
- Dois clientes observando o mesmo corpse.
- combate com muitos mobs e criação em massa de corpses.
- verificar limpeza após timers para evitar acumulação.

## 17. Evidências e limites
- **Authority física exata:** `mob_ragdoll_corpse-1.1.5.jar` na modlist atual.
- **CurseForge oficial:** project 1586005, file 8485385, 1.1.5 Release, NeoForge 1.21.1, 22/07/2026.
- **Changelog 1.1.5:** knockback tweaks, TaCZ bullet knockback -50%, maior durabilidade a kinetic damage e client setting de blood particles default off.
- **Relations oficiais:** Sable + Sable Ragdolls required; Ragdoll Reactions optional.
- **Source oficial auditado:** `trangiaan13052025-cloud/Ragdoll_mobCorpse`, commit `c5ae380ca4ee197ccdfd7c5130b613c60cf20f99` de 22/07/2026.
- **Divergência obrigatória:** esse source ainda declara `version="1.1.1"`; não é chamado nesta ficha de source exato do binário 1.1.5.
- **Source próximo:** confirma architecture de corpse, attachments, timers, tamed persistence, ignoredEntityIds, blood config, TaCZ optional path e uso direto da RagdollAPI.
- **Limite:** valores/comportamentos não corroborados pela release ou pelo source próximo são tratados como testes, não como fatos do binário 1.1.5.
