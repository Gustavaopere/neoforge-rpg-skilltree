# ParCool!

## Propriedades do registro

- **Mod:** ParCool!
- **Arquivo JAR:** ParCool-1.21.1-4.0.0.3.jar
- **Versão 1.21.1:** 4.0.0.3
- **Categoria:** QoL, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/parcool
- **Função:** Sistema de parkour/movimento avançado com ações como vault, wall movement, slides, agarrões e outras técnicas de mobilidade.
- **Dependências:** NeoForge 1.21.1; linha upstream 1.21.1-v4 usa Curios, presente fisicamente como curios-neoforge-9.5.1+1.21.1.jar. Epic Parcool NÃO está presente na modlist física atual.
- **Compatibilidade/Riscos:** Riscos prioritários: issue upstream com Sable/Create Aeronautics, coexistência direta com Epic Fight sem bridge dedicada, wall-run edge cases, input/pose e server movement authority. Não tratar reports upstream como bug local sem reprodução. As upstream 4.0.0.4→4.0.0.6 ampliam movement/input/config/UI surfaces; 4.0.0.4 corrige erro de sincronização ao mudar de dimensão e 4.0.0.6 acrescenta dodge em oito direções e registry-match check na conexão.
- **Sobreposição:** ParCool é mobility/parkour; Epic Fight é combat system. Sem bridge física atual, qualquer integração de stamina/action precisa ser comprovada em runtime.
- **Observações:** Runtime físico 4.0.0.3, Alpha. Upstream posterior: 4.0.0.4 adiciona Long Jump/skilltree state e ações while-sneaking e corrige synchronization ao entrar em outras dimensões; 4.0.0.5 adiciona novos keybinds/input config/full-screen GUIs e ajustes em Dodge/Horizontal Wall Run; 4.0.0.6 Beta adiciona Setting UI, dodge em oito direções e registry-match check client/server. A latest pública 1.21.1 localizada é 4.0.0.6 e não está instalada.
- **Procedência:** modlist(1).txt física reconferida + source/tracker oficial `alRex-U/ParCool` branch `1.21.1-v4`; changelogs oficiais `v_4.0.0.4.md`, `v_4.0.0.5.md` e `v_4.0.0.6.md`, além da listagem CurseForge 1.21.1, foram revalidados em 03/10/2026.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece ParCool 4.0.0.3. As versões 4.0.0.4, 4.0.0.5 e 4.0.0.6 foram comparadas em sequência; deltas relevantes de movement, dimensão, keybind/config, GUI e registry-match multiplayer foram incorporados abaixo.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #433: JAR `ParCool-1.21.1-4.0.0.3.jar`, mod id `parcool`, runtime `4.0.0.3`, SHA-1 `b783696d24092e3745c9390e991eab9351f418b3`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ParCool-1.21.1-4.0.0.3.jar`, mod id `parcool`, versão `4.0.0.3`, NeoForge 1.21.1. ParCool é um sistema de parkour/mobilidade avançada — não um sistema de combate. A modlist atual contém Curios, Epic Fight, Sable e Create Aeronautics, mas **não contém Epic Parcool**; referências antigas a essa bridge foram removidas desta ficha.
</callout>
## 1. Identidade e papel
- **Mod:** ParCool!.
- **JAR físico:** `ParCool-1.21.1-4.0.0.3.jar`.
- **Mod id:** `parcool`.
- **Runtime:** `4.0.0.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** alRex-U / ParCool.
- **Licença:** LGPLv3.
- **Papel:** adicionar ações de parkour e traversal ao player, inspiradas em sistemas como Smart Moving.
- **Decisão:** Sem decisão.
## 2. Ações de mobilidade publicadas
A documentação upstream descreve um conjunto amplo de movimentos, incluindo exemplos como:
- grabbing/clinging em bordas;
- corrida mais rápida;
- roll;
- backflip;
- wall jump;
- cat leap;
- outras ações de parkour ligadas a contexto/input.
A ficha não inventa a lista integral nem keybinds/defaults sem config/runtime da 4.0.0.3.
## 3. Boundary: mobilidade, não combate
ParCool é authority do movimento/parkour que adiciona. Epic Fight continua authority do combat state, attacks, guard/dodge próprios e animação de combate.
Um salto, wall run ou roll de ParCool não deve ser usado como substituto automático de uma dodge/combat action do Epic Fight sem bridge explícita.
## 4. Dependências e modlist atual
A linha 1.21.1-v4 usa **Curios** no ecossistema upstream; o pack físico contém `curios-neoforge-9.5.1+1.21.1.jar`.
A modlist atual também contém:
- `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`;
- `sable-neoforge-1.21.1-2.0.5.jar`;
- `create-aeronautics-bundled-1.21.1-1.3.2.jar`.
**Epic Parcool não está presente na modlist física atual.** Portanto nenhuma integração Epic Fight↔ParCool é presumida por addon dedicado nesta instalação.
## 5. Input e action arbitration
Parkour adiciona novos gestos sobre movement input já usado por sprint, jump, sneak, combat e camera mods.
Testar:
- conflito de keybinds;
- prioridade quando duas ações são elegíveis;
- cancelamento ao entrar em GUI/mount;
- input durante battle mode;
- transição de primeira para terceira pessoa.
A ficha não atribui keybindings específicos sem ler a config local.
## 6. Wall movement
Wall movement/wall jump são superfícies centrais do mod e dependem de geometria, colisão e estado do player.
Há report upstream recente da linha 4.0.0.2 sobre **vertical Wall Run só ativando em paredes de até cerca de 5 blocos**. O pack está em 4.0.0.3, então isso é apenas regression pointer — não bug confirmado da build instalada.
Testar paredes baixas/altas, blocos completos, corners e superfícies modded.
## 7. Roll, backflip e cat leap
Essas ações alteram deslocamento e animation timing. Interseções relevantes:
- Epic Fight battle mode;
- player animation/render mods;
- fall damage/landing rules;
- stamina systems quando outro provider tenta cobrar recurso pela mesma ação.
Sem bridge física atual, não presumir que stamina do Epic Fight é consumida automaticamente pelo ParCool.
## 8. Sable / Create Aeronautics — risco upstream
O tracker upstream possui issue aberta de **incompatibilidade com Sable e Create Aeronautics**. Como ambos estão instalados no pack, isso é um regression gate prioritário.
A issue não prova que a combinação física atual reproduz o problema; registrar como risco significa testar:
- parkour dentro/sobre contraptions móveis;
- mudança de referencial/local coordinates;
- agarrar/wall actions em blocos de SubLevel;
- desmontar/embarcar sem velocity/position corrupta.
## 9. Epic Fight sem bridge dedicada
O pack contém Epic Fight, mas não Epic Parcool. Logo coexistência deve ser validada diretamente:
- battle mode + sprint/jump;
- roll/parkour durante attack recovery;
- armature/pose reset;
- knockback e airborne states;
- stamina não sendo cobrada/duplicada indevidamente.
Se surgir incompatibilidade, a solução não deve presumir uma bridge ausente.
## 10. Client/server e autoridade
Posição/movimento válido precisam convergir ao server state. O cliente pode antecipar animation/camera, mas não deve manter velocity/pose inválida após correção do servidor.
Dedicated server e multiplayer devem ser testados com:
- latência;
- repeated wall actions;
- dimension change;
- respawn;
- mounts/contraptions.
## 11. Persistência e configuração
Configurações de ações/keybinds podem ser client-facing, enquanto regras que afetam movimento precisam produzir resultado compatível com servidor.
Não atribuir comportamento ao default upstream sem conferir os arquivos físicos da instância. Após update, comparar configs para options renomeadas ou defaults alterados.
## 12. Riscos
1. **Sable/Aeronautics incompatibility:** issue upstream aberta e stack presente.
2. **Epic Fight arbitration:** sem bridge dedicada instalada.
3. **Wall-run edge cases:** geometria/altura podem expor regressões.
4. **Input conflicts:** várias actions usam sprint/jump/sneak/contexto.
5. **Animation pose:** roll/backflip/wall actions podem colidir com armatures/render mods.
6. **Movement authority:** lag pode causar rubber-banding se client/server divergem.
7. **Stamina ownership:** não cobrar Epic Fight stamina automaticamente sem integração comprovada.
8. **Config drift:** update pode alterar ação/defaults.
## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com ParCool 4.0.0.3 + Curios atual.
- [ ] Sprint/roll/backflip/wall jump/cat leap representativos funcionam sem input preso.
- [ ] Wall run é testado em paredes de alturas diferentes, inclusive acima de 5 blocos.
- [ ] Epic Fight battle mode coexistindo não causa pose/velocity travada.
- [ ] Stamina Epic Fight não é consumida/duplicada sem integração explícita.
- [ ] Sable SubLevel/contraption: wall action, jump, grab e landing preservam posição correta.
- [ ] Create Aeronautics ship em movimento não produz teleport/rubber-band grave.
- [ ] Mount/GUI/dimension/death cancelam ações de forma segura.
- [ ] Latência moderada não duplica movimento nem deixa state divergente.
- [ ] First/third person e player animation stack não ficam com pose stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limites
- Modlist física: `ParCool-1.21.1-4.0.0.3.jar`, mod id/runtime e `parcool.mixins.json`.
- Source oficial `alRex-U/ParCool`: parkour actions e linha 1.21.1-v4.
- Modlist atual: Curios, Epic Fight, Sable e Create Aeronautics presentes; **Epic Parcool ausente**.
- Tracker upstream: issue aberta Sable/Create Aeronautics e report próximo à build instalada sobre vertical wall run.
- **Limite:** reports de issue não foram promovidos a bugs locais sem reprodução; keybinds, fórmulas e lista integral de ações não foram inventados sem config/JAR auditados.

## 15. Atualizações upstream 4.0.0.4 → 4.0.0.6 — não instaladas

A autoridade física continua em **ParCool 4.0.0.3**. O histórico oficial foi percorrido por **4.0.0.4 → 4.0.0.5 → 4.0.0.6**; a 4.0.0.6 foi publicada no CurseForge para NeoForge 1.21.1 como **Beta** em 02/10/2026.

### 4.0.0.4
Novas superfícies:
- **Long Jump**;
- visualização do estado das abilities na Skilltree GUI;
- **Hang On**, **Hang Down** e **Ride Zipline** passam a poder ser executados enquanto o player está agachado.

Correções:
- corrige erro de **sincronização ao entrar em outras dimensões**;
- inclui o fix rastreado como issue #509.

O fix de dimensão é prioritário porque esta ficha já trata server movement authority e dimension change como lifecycle crítico.

### 4.0.0.5
Novidades:
- keybindings dedicados para **Fast Run / Fast Swim**;
- keybind para **Enable / Disable ParCool**;
- keybind para abrir a **Skilltree** sem o ParCool Guide;
- configuração de input para action keybindings;
- Skilltree e ParCool Guide com modo **Full Screen**.

Mudanças de movimento:
- Dodge recebe animation speed default maior e passa a ser permitido durante Fast Run;
- Horizontal Wall Run reduz gradualmente a velocidade Y no início;
- ajustes de comportamento de configuração.

Também inclui o bugfix #511.

### 4.0.0.6 — Beta — 02/10/2026

- Adiciona **Setting UI**, aberta pela tecla de configuração.
- **Dodge** passa a funcionar em **oito direções**, ampliando a superfície de input/movimento e animação.
- O keybind antes descrito como **Open Skilltree** passa a **Open Setting / Skilltree**.
- O design da Skilltree UI foi alterado e o acesso via shift-click no ParCool Guide deixou de ser o caminho usado.
- Adiciona **verificação de registry match na conexão entre client e server**. Esse é um boundary importante para multiplayer: mismatch de registries deve ser detectado explicitamente em vez de permitir state incompatível.
- Após a tag/release, o source recebeu ainda um bugfix para evitar envio de packet desnecessário para todos os players; esse commit está no histórico pós-version bump e não é atribuído ao JAR 4.0.0.6 sem evidência de que foi incluído no artefato publicado.

### Gate de promoção 4.0.0.3 → 4.0.0.6
- [ ] Dimension transfer durante/fora de ação não deixa movement/action state dessincronizado.
- [ ] Long Jump é liquidado uma vez pelo state autoritativo e não duplica velocity sob latency.
- [ ] Hang On/Hang Down/Ride Zipline while sneaking não conflitam com sneak de Epic Fight/outros mods.
- [ ] Novos keybinds não colidem com Epic Fight, camera ou Create/Aeronautics controls.
- [ ] Toggle Enable/Disable limpa action/pose state imediatamente.
- [ ] Dodge durante Fast Run não aplica impulso/animação duas vezes.
- [ ] Horizontal Wall Run mantém trajetória previsível e server/client convergem.
- [ ] Full Screen Skilltree/Guide devolvem input/focus corretamente ao fechar.
- [ ] Sable SubLevel e Create Aeronautics continuam sem teleport/rubber-band grave.
- [ ] Config antiga 4.0.0.3 migra/carrega sem reset silencioso de regras importantes.

Fontes upstream: `alRex-U/ParCool`, changelogs 4.0.0.4/4.0.0.5/4.0.0.6; CurseForge confirma ParCool 4.0.0.6 Beta para NeoForge 1.21.1 em 02/10/2026. Nenhum teste acima foi executado nesta atualização documental.

## 16. Atualização upstream 4.0.1.0 → 4.0.1.2 — 10/10/2026

A autoridade física continua em **ParCool 4.0.0.3** (`ParCool-1.21.1-4.0.0.3.jar`). A seção 15 já registra o intervalo **4.0.0.4 → 4.0.0.5 → 4.0.0.6**. Depois, as releases oficiais **4.0.1.0, 4.0.1.1 e 4.0.1.2** para 1.21.1 foram publicadas em 04, 05 e 09/10/2026:

### 4.0.1.0 (file 9059241)
- Adiciona config de primeira pessoa para animações, **`enable_first_person_view_animation`**.
- Corrige **Grapple** se comportando como se o jogador estivesse no ar mesmo em fluido.
- Registra correção #520 no changelog; sem inferir a natureza da issue a partir apenas do número.

### 4.0.1.1 (file 9068414)
- O changelog da 4.0.1.1 registra **duas entradas distintas**: `Crash on boot of server` e `#521`. A primeira diz respeito a código client-only no bootstrap; a segunda é uma recursão de capacidades Curios/Epic Fight reproduzida em **Minecraft 1.20.1 Forge**, não em NeoForge 1.21.1. A seção 18 separa a procedência e os gates; nenhuma falha local foi reproduzida.
 
### 4.0.1.2 (file 9106851)
- Nova opção para **desativar learning de cada ação separadamente**.
- Reduz **custo default de learning** das ações. Mudança que afeta progressão/balanceamento, scripts e quests que assumem o custo anterior.

**Cadeia integral do runtime até latest 1.21.1:** 4.0.0.3 → .4 → .5 → .6 → **4.0.1.0 → 4.0.1.1 → 4.0.1.2**.

**Gate:** boot dedicated server; migração de configs; actions learn/unlearn/cost; oito direções de Dodge e skilltree; grapple em água/lava; animation first-person com Epic Fight/First-person Model; registry match, mudança de dimensão, Sable/Aeronautics; teclado/multiplayer; abandono/retomada de mundo. Não executar promoção sem testes.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/parcool/files/9059241 ; https://www.curseforge.com/minecraft/mc-mods/parcool/files/9068414 ; https://www.curseforge.com/minecraft/mc-mods/parcool/files/9106851

## Atualização upstream e gate de manutenção — 11/10/2026

**Revalidação:** o dossiê já apresenta a cadeia upstream NeoForge 1.21.1 até **4.0.1.2**, release 09/10/2026. O último JAR histórico permanece **4.0.0.3**. **Impacto cumulativo essencial:** 4.0.1.1 corrige crash de boot dedicado e 4.0.1.2 adiciona desativação individual de learning de ações e altera custos padrão; efeitos de progressão não podem ser atribuídos como instalados. **Gate:** config migration, parkour learning/custos, oito dodge directions, slide/grapple em fluido, Epic Fight animation precedence, Sable/Aeronautics physics, Curios 9.5.1, mundos persistentes e reboot de servidor. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/parcool/files/all

> **Estado:** atualização documental sem qualquer substituição de JAR, edição da autoridade física ou teste in-game nesta execução.


## 17. Contrato source da stamina com Thirst Was Fixed 2.1.6/2.2.0 — 13/10/2026

**Autoridade instalada histórica, ainda sem JAR novo inspecionado:** ParCool `4.0.0.3`, `ParCool-1.21.1-4.0.0.3.jar`, mod ID `parcool`, #433, SHA-1 do inventário `b783696d24092e3745c9390e991eab9351f418b3`; NeoForge #001 `21.1.250`; Thirst Was Fixed #542 `2.1.6`; Reclaimed #543 `3.0.5`. Nenhum boot, config física ou hash novo foi obtido nesta auditoria.

### Evidência versionada para exatamente Minecraft 1.21.1 / NeoForge

A [branch oficial `1.21.1-v4` no commit `5116ae71574428ba63e162f7e39a0f2c5e1d641c` de 30/08/2026](https://github.com/alRex-U/ParCool/tree/5116ae71574428ba63e162f7e39a0f2c5e1d641c) declara `mod_version=4.0.0.3`, `minecraft_version=1.21.1`, `neoforge_version=21.1.248` e `neoforge_version_range=[21.1.248,)` no `gradle.properties`. Assim, o NeoForge `21.1.250` histórico satisfaz **apenas o requisito numérico**. O arquivo [`src/main/java/com/alrex/parcool/api/ParCoolAttributes.java` desse commit](https://github.com/alRex-U/ParCool/blob/5116ae71574428ba63e162f7e39a0f2c5e1d641c/src/main/java/com/alrex/parcool/api/ParCoolAttributes.java) define `STAMINA_RECOVERY` como `DeferredHolder<Attribute, Attribute>`, registrando o atributo com ID de registro `parcool:parcool.stamina_recovery` (namespace `parcool`, path literal `parcool.stamina_recovery`) e chave de descrição `attribute.name.parcool.stamina_recovery`, com default `10` e intervalo `1..10000` na implementação source. O mesmo commit importa `ParCoolAttributes` a partir do entrypoint `ParCool.java`.

**Resultado da árvore completa:** a Git tree oficial deste commit, com `truncated=false` e 881 entradas, possui `api/ParCoolAttributes.java` mas não `com/alrex/parcool/api/Attributes.java`. Isso compara paths de **source**, não classes do JAR distribuído. É importante ter usado especificamente o commit 1.21.1-v4: o commit `8f480f8973612d8a6bfb4759c212112b97ba7f5e`, também chamado 4.0.0.3, pertence à linha principal Forge Minecraft **1.19.2** e não é autoridade binária para o pack 1.21.1.

### Incompatibilidade nominal condicionada da bridge instalada

O [`ParCoolHelper.java` do Thirst Was Fixed source 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/ParCoolHelper.java) acessa `com.alrex.parcool.api.Attributes.STAMINA_RECOVERY` em ambos os blocos de bônus/penalidade. Esse owner **não foi encontrado no source do ParCool 4.0.0.3 correspondente ao MC 1.21.1**, enquanto o campo existe sob `ParCoolAttributes`. Trata-se de **desalinhamento nominal observado entre dois snapshots de source**, com risco plausível de falha de linkage ao executar caminhos de modificação da stamina, **não de crash reproduzido**. É obrigatório ler os binários e a config antes de classificar falha real.

O [`Config.java` da versão 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/Config.java) define `Stamina Bonus Requirement=0` e `Stamina Penalty Requirement=0` por padrão, e `ParCoolHelper.applyModifiers` protege cada acesso ao owner de atributo por um `if (requirement != 0)`. **Somente os defaults source são conhecidos; a configuração efetiva do pack é desconhecida.** Não afirmar que os caminhos nunca executam, nem alterá-los silenciosamente.

O [source do Thirst Was Fixed 2.2.0, commit `47fb0e7063f9e906aa05da492a938ad45f39cb8c`](https://github.com/Partonetrain/thirstwasfixed/tree/47fb0e7063f9e906aa05da492a938ad45f39cb8c) mudou para `ParCoolAttributes.STAMINA_RECOVERY`, presente já na implementação source ParCool 4.0.0.3. Portanto a **incompatibilidade nominal específica** é corrigida no source futuro do addon, mas **isso NÃO certifica uma atualização 2.2.0 ou a ABI/runtime no pack**: o source TWF 2.2.0 usa NeoForge 21.1.256 para desenvolvimento e dependency ParCool 4.0.1.2, ao passo que o loader/ParCool históricos são 21.1.250/4.0.0.3. Uma troca isolada pode expor outras mudanças.

### Regression gate e decisão

1. Inspecionar `ParCool-1.21.1-4.0.0.3.jar` e `thirstwasfixed-2.1.6.jar` reais, conferindo META-INF, IDs, classes `api/Attributes` / `api/ParCoolAttributes`, owners no constant pool, assinatura de `STAMINA_RECOVERY` e hashes.
2. Ler o arquivo server/startup config do Thirst Was Fixed e registrar separadamente `Stamina Bonus Requirement`, `Stamina Penalty Requirement`; usar uma **cópia** para investigar valores não nulos, sem alterar produção.
3. Em dedicated server, comparar baseline com ambos thresholds desativados e os testes em staging com thresholds não nulos; monitorar `NoClassDefFoundError`, `NoSuchFieldError`, `MixinApplyError` ou execução correta e garantir um único modifier de `stamina_bonus/stamina_penalty`.
4. Verificar ownership de stamina entre ParCool, Thirst Was Fixed e qualquer outro provider, sync de atributos cliente-servidor, login/morte/dimensão/restart, Epic Fight, movimentos em Sable/Create Aeronautics e custos de ação.
5. Avaliar separadamente a opção Thirst Was Fixed 2.2.0 somente após o **JAR** estar publicado, inspecionado e testado; não confundir mudança de source com aprovação.

**Decisão:** estado **PENDENTE DE BINÁRIO E RUNTIME**. Manter ParCool 4.0.0.3 (#433), Thirst Was Fixed 2.1.6 (#542), seus dossiês/filenames e as linhas físicas do inventário. Não recomendar atualização ou ativação de bônus de stamina sem evidência.


## 18. Auditoria de código — Curios/Epic Fight #521 versus boot dedicado 4.0.1.1 (13/10/2026)

**Autoridade física histórica (não reinspecionada):** ParCool #433 `ParCool-1.21.1-4.0.0.3.jar`, runtime 4.0.0.3; Curios `curios-neoforge-9.5.1+1.21.1.jar`; Epic Fight `21.17.3.1`; Minecraft 1.21.1 / NeoForge 21.1.250. A [página oficial de versões 1.21.1](https://www.curseforge.com/minecraft/mc-mods/parcool/files/all?gameVersionTypeId=6&page=1) publica **4.0.1.2** (09/10/2026) como última release 1.21.1 localizada. As versões 4.0.1.1/4.0.1.2 **não foram instaladas** nesta auditoria.

### 18.1. #521: falha reproduzida em ambiente DIFERENTE do pack

A [issue oficial #521](https://github.com/alRex-U/ParCool/issues/521), aberta em 04/10/2026 e fechada em 05/10/2026, relata `StackOverflowError` por inicialização recursiva de capacidades em **Minecraft 1.20.1 / Forge 47.4.23 / ParCool 4.0.1.0 / Epic Fight 20.14.17**. O caminho documentado pelo autor do report é `TraceurGlovesItem.initCapabilities` → `CuriosManager.initEquipAbleCapabilities` → `EquipAbleCuriosWrapper.<init>` → consulta de modificadores do `ItemStack` → `ItemAttributeModifierEvent` do Forge → consulta de capabilities pelo Epic Fight → reentrada em `initCapabilities`. **Essa reprodução NÃO constitui evidência de crash no perfil NeoForge 1.21.1 deste pack.**

**Correspondência concreta em source 1.21.1:** no [commit oficial ParCool 4.0.0.3 / branch `1.21.1-v4` `5116ae71574428ba63e162f7e39a0f2c5e1d641c`](https://github.com/alRex-U/ParCool/blob/5116ae71574428ba63e162f7e39a0f2c5e1d641c/src/main/java/com/alrex/parcool/extern/curios/capability/EquipAbleCuriosWrapper.java), o construtor `EquipAbleCuriosWrapper(ItemStack)` consulta `itemStack.getAttributeModifiers().modifiers()` durante a construção da wrapper `ICurio` e filtra por `equipAble.getEquipmentSlot()`. Trata-se do **mesmo padrão de consulta antecipada potencialmente reentrante** descrito na issue, mas sob APIs e eventos NeoForge diferentes. O risco é estático/cross-loader, não uma observação de falha física.

A [implementação source 4.0.1.1 / MC 1.21.1 no commit `efc8f3c6887dd8f817a88f4031a7858b37834548`](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/src/main/java/com/alrex/parcool/extern/curios/capability/EquipAbleCuriosWrapper.java) troca a chamada antecipada por `itemStack.getItem().getDefaultAttributeModifiers(itemStack).modifiers()`, mantendo o filtro pelo equipamento. Isso confirma a **alteração estrutural da consulta no source 1.21.1**, não valida por si só o binário distribuído ou garante equivalência completa de modificadores gerados por outros mods no runtime.

### 18.2. Crash no boot de servidor: outro problema e outra correção

A [release oficial ParCool 4.0.1.1 NeoForge 1.21.1, File ID 9068414](https://www.curseforge.com/minecraft/mc-mods/parcool/files/9068414) e o [changelog v_4.0.1.1.md](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/changelog/v_4.0.1.1.md) listam separadamente `Crash on boot of server` e `#521`. O diff oficial 1.21.1 nesse commit acrescenta `@OnlyIn(Dist.CLIENT)` a `AbstractLocalStamina.get(LocalPlayer)` no [arquivo da API de stamina](https://github.com/alRex-U/ParCool/blob/efc8f3c6887dd8f817a88f4031a7858b37834548/src/main/java/com/alrex/parcool/api/stamina/AbstractLocalStamina.java).

**Controle de introdução:** o [source 4.0.1.0/MC 1.21.1 (`b7b0529d0e85820d1b4847c19623f046f7a299da`)](https://github.com/alRex-U/ParCool/blob/b7b0529d0e85820d1b4847c19623f046f7a299da/src/main/java/com/alrex/parcool/api/stamina/AbstractLocalStamina.java) já possui o método com parâmetro `LocalPlayer`, porém ainda sem `@OnlyIn`. **O source histórico 4.0.0.3 não tem esse método/import client-only**. Logo, não se pode atribuir o problema de boot desse método especificamente ao JAR físico 4.0.0.3 por leitura do changelog 4.0.1.1. Também não há prova de que essa anotação seja a única condição para um boot bem-sucedido.

### 18.3. Contrato de testes e decisão

1. **Inspeção binária:** conferir hash e bytecode de `ParCool-1.21.1-4.0.0.3.jar` e candidatos 4.0.1.1/4.0.1.2, mais Curios/Epic Fight físicos; confirmar a chamada efetiva em `EquipAbleCuriosWrapper`, métodos client-only e dependências.
2. **Reentrada Curios:** com pack isolado, testar equip/unequip/reatribuição de **Traceur Gloves** via slot vanilla e Curios, inventário, player join, death, dimension change e restart, com/sem Epic Fight battle mode. Monitorar `StackOverflowError`, recursion no `ItemAttributeModifierEvent`, perda/duplicação de attribute modifiers e sincronização em dedicated server.
3. **Cenário de outras fontes de atributos:** após upgrade em staging, verificar se a mudança para `getDefaultAttributeModifiers` preserva na prática as interações pretendidas com modificadores dinâmicos de Epic Fight/Apotheosis/outros providers; não afirmar perda efetiva sem reproduzir.
4. **Dedicated server:** comparar boot headless no baseline 4.0.0.3 e candidato 4.0.1.2 (4.0.1.1 já inclui a anotação source), com logs de `ClassNotFoundException`, `NoClassDefFoundError` e referências a `net.minecraft.client`; não atribuir a uma versão um bug observado em outra.
5. **Regressão cruzada:** manter o gate independente Thirst Was Fixed 2.1.6 ↔ ParCool 4.0.0.3 (owner `api.Attributes` versus `api.ParCoolAttributes` no source) e não fazer duas mudanças físicas simultâneas antes de baseline comparável.

**Decisão:** **MANTER #433 em 4.0.0.3 no inventário histórico; atualização para 4.0.1.2 é CANDIDATA PENDENTE DE TESTE.** Esta seção é documental: não prova crash no pack, não altera JAR, configuração, world ou server.


## 19. Gate de addon ausente — Epic ParCool 21.0.0 e API ParCool 4.0.x (13/10/2026)

**Estado documentado:** o inventário físico histórico com 587 posições contém ParCool #433 4.0.0.3 e Epic Fight #260 21.17.3.1, **não** o `epicparcool`. O dossiê do [Epic Fight #260](../Adventure%20and%20RPG%20%2B%20API%20and%20Library%20%2B%20Armor%2C%20Tools%2C%20and%20Weapons/%E2%9C%85-epic-fight%20v21.17.3.1.md) foi corrigido para remover duas afirmações históricas de bridge instalada.

**Fonte oficial específica:** [Epic ParCool 21.0.0, NeoForge 1.21.1, 19/01/2026](https://www.curseforge.com/minecraft/mc-mods/official-epic-fight-x-parcool/files/7490217), source [Antikythera-Studios/epicparcool, `1.21.1`, commit `b1f703e7ed911bd2704cd566b8aa511723b8b78a`](https://github.com/Antikythera-Studios/epicparcool/tree/b1f703e7ed911bd2704cd566b8aa511723b8b78a): compila com `ParCool-3.4.3.0.jar` e declara dependency `[3.4.2.0,)`, sem excluir a linha 4.0. O [mixin `ParCoolMixinActionProcessor`](https://github.com/Antikythera-Studios/epicparcool/blob/b1f703e7ed911bd2704cd566b8aa511723b8b78a/src/main/java/com/yesman/epicparcool/mixin/ParCoolMixinActionProcessor.java) referencia `common.attachment.common.Parkourability` e descriptors da API 3.4.x; o [source do nosso ParCool nominal 4.0.0.3 / MC1.21.1](https://github.com/alRex-U/ParCool/tree/5116ae71574428ba63e162f7e39a0f2c5e1d641c) reorganizou esses contratos. [Issues upstream #18](https://github.com/Antikythera-Studios/epicparcool/issues/18) e [#19](https://github.com/Antikythera-Studios/epicparcool/issues/19) documentam incompatibilidade de carregamento/mixins com ParCool 4.0.0.x; #19 também relata warning de mixin client-only em servidor com ParCool 3.4.3.3, situação distinta.

**Alternativa não equivalente:** há outro projeto publicado, [\[NEOFORGE\] Epic Fight X Parcool 1.0.0 (10/08/2026)](https://www.curseforge.com/minecraft/mc-mods/epic-fight-x-parcool/files/all), cujo código/JAR não foi comparado. Não transferir conclusões entre projetos nem instalá-lo por similaridade de nome.

**Gate de inclusão futura:** comparar classes/mixin targets/versions reais, dependências Epic Fight e ParCool, NeoForge 21.1.250, server headless, camadas de animação/dodge, stamina (Epic Fight vs ParCool), skills, inputs, Curios/Traceur Gloves, save/restart, dimensão e multiplayer; registrar hashes e testar rollback. **Decisão: Epic ParCool oficial 21.0.0 BLOQUEADO no stack 4.0.0.3 por mismatch de source; outro projeto NÃO VERIFICADO; nenhum addon instalado.** Nenhum problema atribuído ao baseline sem bridge.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** ParCool! ~ Minecraft Parkour ~
- **Último candidato 1.21.1 / NeoForge localizado:** **4.0.1.6 (Release NeoForge 1.21.1, 16/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Revalidar com ThirstWasFixed 2.2.1, Epic Fight/Curios, stamina attributes, learning costs/config migration, movement authority, dimensão, Sable/Aeronautics e dedicated boot.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/parcool

