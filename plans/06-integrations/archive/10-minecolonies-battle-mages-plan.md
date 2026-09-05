# 06.10 — MineColonies Battle Mages × Iron's Spellbooks

**Status:** PLANEJADO / ABERTO. Este arquivo descreve o contrato de implementação; ele não marca runtime como disponível.

**Minecraft:** 1.21.1  
**Loader:** NeoForge  
**Java:** 21  
**RPG Skill Tree:** adapter opcional dentro do Stage 06 — Integrations

## Objetivo

Adicionar **Magos de Batalha** como uma força militar real da colônia MineColonies. O jogador equipa um cidadão com um **spellbook verdadeiro do Iron's Spells 'n Spellbooks** e o cidadão usa, em combate, os feitiços que realmente estão inscritos naquele livro.

A unidade deve ser deliberadamente forte e cara:

- o spellbook continua sendo um `ItemStack` real e fica comprometido com o cidadão enquanto ele estiver equipado;
- o RPG Skill Tree não cria, copia, melhora ou substitui spellbooks;
- os níveis dos feitiços são exatamente os `SpellData` existentes no livro;
- a preparação do livro continua usando a progressão/economia nativa do Iron's, inclusive scrolls, inscrição, loot e raridade;
- mana, cast time, cooldown, spell power, escola e efeitos continuam pertencendo ao Iron's;
- a colônia precisa proteger e abastecer um recurso de alto valor em vez de receber magia gratuita.

A fantasia é de **artilharia arcana de elite da colônia**, não de um guarda vanilla que recebeu partículas ou dano mágico genérico.

---

## Baseline de providers

Antes de implementar, o executor deve revalidar a modlist e o JAR exato instalado. O baseline documentado em 2026-08-30 é:

- MineColonies: `minecolonies-1.1.1375-1.21.1-snapshot.jar`;
- Iron's Spells 'n Spellbooks: `irons_spellbooks-1.21.1-3.16.3.jar`;
- RPG Skill Tree: NeoForge 1.21.1 / Java 21;
- Epic Fight: `21.17.3.1` no pack atual, apenas para compatibilidade; não é authority do cast;
- integrações visuais/de combate já presentes no pack, como Epic Colonies, Epic Fight × MineColonies, EFIS e Epic Fight × Iron's Spells Mobs, são consumers/compat opcionais e não podem virar dependência obrigatória deste contrato.

### Evidência técnica já confirmada

O RPG já possui integração canônica com Iron's para casts e atributos em `✅-03-irons-spellbooks.md`. `IronsSpellbookProgressionEvents` somente concede Mastery quando o caster é `ServerPlayer` e a fonte é `SPELLBOOK` ou `SCROLL`; casts de cidadãos não devem entrar nessa lane.

No Iron's 1.21, `ISpellContainer` expõe os spells ativos e seus `SpellData` diretamente do `ItemStack`. O framework de mobs do Iron's usa `MagicData`, `IMagicEntity`, `SpellData` e `CastSource.MOB` para conjuração server-side. Isso prova que existe semântica provider-native para **mob casting**, mas não autoriza copiar a classe de entidade do Iron's para um cidadão MineColonies.

O código atual de `Scroll` usa `Player`/`ServerPlayer` para `CastSource.SCROLL` e para consumir a pilha após o cast. Portanto, **cast direto de scroll solto por cidadão fica fail-closed no escopo inicial**. Não será criada uma imitação de `Scroll.use()` para NPC.

O upstream atual do MineColonies expõe registries de `JobEntry` e `GuardType`. Porém a implementação deve provar que o JAR exato `1.1.1375-1.21.1-snapshot` mantém esse seam antes de registrar o tipo de guarda. A branch upstream móvel não substitui a prova da build instalada.

---

# Autoridades e invariantes

## 1. MineColonies continua authority da colônia e do cidadão

MineColonies conserva ownership de:

- identidade/UUID do cidadão;
- emprego e vínculo com building;
- guard duty, patrol, follow e target hostil;
- pathfinding e distância operacional;
- inventário do cidadão;
- requests/logística;
- equipamento e troca de item;
- morte, respawn/replacement e lifecycle do worker;
- relações de colônia, aliados, permissões e raids.

O RPG Skill Tree **não** cria uma entidade paralela de “mago da colônia”, não duplica cidadão, não substitui o guard AI inteiro e não mantém um inventário arcano separado.

## 2. Iron's continua authority da magia

Iron's conserva ownership de:

- registry e identidade do spell;
- `SpellData` e nível do spell;
- conteúdo e slots do spellbook;
- `MagicData`;
- mana e regeneração;
- cast time;
- cooldown/recast quando aplicável;
- `checkPreCastConditions`;
- targeting data exigido pelo spell;
- spell power e atributos por escola;
- execução, projéteis, summons, efeitos, partículas e sons;
- callbacks `onServerPreCast`, `onCast`, `onServerCastTick` e `onServerCastComplete`.

O adapter não cria `int mana`, mapa próprio de cooldown, cópia de `SpellData`, segundo projectile pipeline ou dano substituto.

## 3. RPG Skill Tree é somente orquestrador da bridge

O RPG Skill Tree pode:

1. reconhecer o cidadão como Mago de Batalha;
2. encontrar o spellbook real no inventário/equipamento autorizado;
3. ler os `SpellData` reais via `ISpellContainer`;
4. escolher, entre os spells do livro, um spell suportado e seguro para o contexto atual;
5. fornecer ao Iron's o caster, alvo/contexto e `CastSource.MOB`;
6. manter metadados mínimos de IA/deduplicação que não pertencem aos providers;
7. registrar diagnostics, telemetria técnica e testes.

Ele não pode conceder ao cidadão um spell que não esteja no livro.

---

# Gameplay final

## Recrutamento e emprego

O alvo arquitetural é registrar um `GuardType` opcional:

`rpgskilltree:battle_mage`

Esse tipo deve reutilizar a infraestrutura real de guarda do MineColonies sempre que a API exata permitir: assignment, patrol/follow/guard, building, target selection, leveling e inventário permanecem nativos.

### Gate de implementação do GuardType

A implementação só prossegue quando o JAR `1.1.1375-1.21.1-snapshot` provar:

- registry público/compatível para `GuardType` ou extensão equivalente;
- forma segura de associar o tipo a um guard worker/building;
- hook estável para participar do combat AI sem substituir classes internas inteiras.

Se qualquer um desses três pontos não existir na build exata, o feature fica **INATIVO / FAIL-CLOSED** e volta para revisão de design. Não será criado automaticamente um “guard type falso” via reflection/mixin frágil.

## Equipamento obrigatório

Um Mago de Batalha só possui casting ativo quando carrega um `ItemStack` que:

- é um `ISpellContainer` real do Iron's;
- possui pelo menos um spell ativo;
- está no slot/local de inventário aceito pelo adapter MineColonies;
- continua pertencendo ao inventário real do cidadão.

O livro não é serializado para uma lista paralela. O loadout é relido quando o `ItemStack` muda, e qualquer cache é apenas derivado e descartável.

### Quando o livro é removido

- novos casts mágicos param imediatamente;
- cast em andamento é cancelado pela lifecycle segura do Iron's quando a remoção invalida o contexto;
- nenhuma cópia do spellbook ou dos spells permanece no cidadão;
- o cidadão continua sendo um worker MineColonies, mas sem capacidade mágica até receber outro livro.

## Scrolls e custo de preparação

No escopo inicial, **scroll solto não é munição direta do NPC** porque o path provider-native atual de `Scroll` é player-bound.

O custo de scrolls continua existindo de forma real: o jogador prepara/inscreve o spellbook pelo fluxo normal do Iron's e então entrega esse livro valioso ao cidadão. O RPG Skill Tree não fornece atalho para inscrição e não ignora as regras já existentes de progressão de Iron's no próprio RPG.

Uma extensão futura `06.10B — Battle Mage Reserve Scrolls` só poderá permitir scroll consumível em combate se a versão instalada expuser um contrato seguro para caster não-player com:

- consumo exatamente uma vez;
- cancelamento sem consumir;
- `CastSource.SCROLL` legítimo;
- sem mana/cooldown inventados;
- persistência segura em unload/death;
- nenhum bypass do spell-learning/inscription pipeline.

Até essa prova existir, esse caminho fica desabilitado.

---

# Escolha de feitiços

## Regra central

**O livro é a lista de capacidades.** O adapter nunca mantém uma lista de spells “do Mago de Batalha” que substitua o conteúdo do item.

Fluxo:

```text
MineColonies seleciona ameaça/alvo
        ↓
BattleMageLoadoutResolver lê o ItemStack real
        ↓
ISpellContainer.get(book) → SpellData ativos
        ↓
BattleMageSpellPolicy filtra somente spells suportados no contexto
        ↓
BattleMageCombatController escolhe 1 candidato
        ↓
Iron's pre-cast / MagicData / CastSource.MOB
        ↓
Iron's executa o spell
```

## Por que existe uma policy de suporte

Nem todo spell foi escrito para qualquer `LivingEntity` externa. Há spells de:

- alvo hostil;
- self-buff;
- cura/suporte;
- teleporte/dash;
- canalização contínua;
- summons;
- criação/modificação de mundo;
- comportamento que depende de aiming data específico.

Tentar “fazer qualquer spell funcionar” por heurística produziria casts incorretos, friendly fire, bypass de claims ou crashes.

### `BattleMageSpellProfile`

A bridge terá profiles data-driven por spell ID. O estado padrão de um spell desconhecido é `UNSUPPORTED`.

Schema mínimo planejado:

```json
{
  "spell": "irons_spellbooks:fireball",
  "target_mode": "HOSTILE_ENTITY",
  "priority": 70,
  "min_range": 4.0,
  "max_range": 28.0,
  "friendly_fire_radius": 4.0,
  "world_effect": false
}
```

`target_mode` permitido no primeiro contrato:

- `HOSTILE_ENTITY`;
- `SELF`;
- `ALLY_ENTITY`;
- `HOSTILE_AREA`.

Spells que exigirem semântica adicional recebem profile explícito e handler próprio. O adapter não deduz segurança pelo nome do spell.

## Prioridade tática inicial

A seleção deve ser determinística e bounded:

1. se o cidadão está em condição crítica, tentar spell `SELF` de defesa/cura compatível;
2. se houver aliado MineColonies ferido em contexto seguro, tentar `ALLY_ENTITY`;
3. se houver alvo válido, avaliar `HOSTILE_ENTITY` e `HOSTILE_AREA`;
4. rejeitar candidato fora de alcance, sem linha de visão quando exigida, sem profile, sem target data segura ou que falhe no pre-cast do Iron's;
5. entre candidatos válidos, ordenar por `priority`, depois posição do spell no livro e ID para desempate estável.

Não existe reroll aleatório por tick.

## Friendly fire

Antes de um cast de área, o adapter deve consultar relações reais do MineColonies e aplicar o `friendly_fire_radius` do profile.

Se um cidadão/aliado protegido estiver na zona de risco e o spell não for comprovadamente ally-safe, o cast é rejeitado. Rejeição não consome mana, item ou cooldown.

---

# Mana, cooldown e ciclo de cast

## `MagicData` real

A implementação deve reutilizar o `MagicData` do Iron's para o cidadão. O código do Iron's demonstra que entities não-player podem participar do pipeline mágico, mas a forma exata de anexar/persistir/sincronizar esse estado em `EntityCitizen` deve ser provada contra 3.16.3.

Se `MagicData` seguro para `EntityCitizen` não puder ser obtido sem copiar internals instáveis, o casting fica fail-closed. **Não criar `BattleMageManaData`.**

## Mana

- custo vem do spell do Iron's;
- regeneração usa atributos/regras do Iron's;
- o adapter não zera custo;
- o adapter não restaura mana após cast;
- sem mana suficiente, o spell não é escolhido/concluído.

## Cooldown

O adapter não inventa um cooldown que substitui o provider. Quando o framework de mob casting do Iron's usa scheduler/goal cooldown em vez do player cooldown manager, a bridge deve preservar a semântica comprovada da versão 3.16.3.

Pode existir um **think interval** interno da IA para performance, mas ele não é chamado de spell cooldown e não reduz o cooldown real do provider.

## Cast lifecycle

Um cast válido deve executar uma única sequência canônica:

```text
precondition/target setup
→ initiate
→ server pre-cast
→ cast ticks, se aplicável
→ onCast
→ server cast complete
```

Morte, unload, troca de job, perda do livro ou perda do alvo devem cancelar/encerrar o cast sem repetir `onCast` e sem deixar estado de casting órfão.

---

# Poder e balanceamento

## Fonte do poder

O Mago de Batalha é forte porque o jogador investe em **gear mágico real**:

- spellbook de maior capacidade/raridade;
- spells de nível maior já inscritos;
- atributos do próprio spellbook;
- armadura/acessórios compatíveis quando MineColonies permitir equipar com segurança;
- nível/atributos do cidadão que forem legitimamente convertidos em modifiers do Iron's.

A bridge não aumenta o nível de `SpellData` e não transforma um spell comum em lendário.

## Progressão do cidadão

O MineColonies continua autoridade do nível do worker. Se o balanceamento ligar worker level a atributos arcanos, os modifiers devem ser estáveis e derivados, por exemplo:

- modifier de max mana;
- modifier de mana regen;
- modifier de spell power;
- modifier de cast time/cooldown somente se o atributo existir e aceitar o valor.

Cada modifier terá ID estável `rpgskilltree:minecolonies_battle_mage/...` e será recomposto idempotentemente. Troca de job remove os modifiers.

Nenhum rank do cidadão altera o `SpellData` dentro do livro.

## Limite operacional

O primeiro release não adiciona multiplicador secreto de dano. O teto de poder é o spellbook real + atributos Iron's reais + progressão legítima do cidadão.

Isso mantém a unidade cara sem introduzir um segundo sistema de magia.

---

# Integração com RPG Skill Tree

## Mastery e XP

**Casts autônomos do Mago de Batalha não concedem Mastery, RPG XP ou pontos ao jogador.**

Razões:

- o ator causal do cast é o cidadão;
- o jogador não pode ganhar `irons:casting` enquanto fica AFK perto da colônia;
- o mesmo spell não pode alimentar duas vezes o pipeline canônico.

O evento existente `IronsSpellbookProgressionEvents` já filtra por `ServerPlayer`; a implementação deve manter e testar essa barreira.

## Perks futuras

Perks podem futuramente modificar a eficiência do Mago de Batalha apenas por handlers explícitos e sem escrever no storage do MineColonies ou Iron's diretamente. Este subplano **não cria perks novas automaticamente**.

## Narrative Core

O Stage 08 pode futuramente observar eventos discretos como:

- primeiro Mago de Batalha contratado;
- defesa bem-sucedida da colônia com magia;
- morte de um Mago de Batalha importante.

Mas Narrative Core é consumer. Ele não escolhe spell, não controla mana e não altera a guard AI.

---

# MineColonies: inventário, requests e morte

## Inventário

O spellbook deve permanecer no inventário/equipment real do cidadão. O adapter guarda apenas uma referência derivada/fingerprint para detectar mudança, nunca um clone autoritativo do item.

## Requests/logística

Após provar o seam exato da versão instalada, registrar um requirement/equipment type MineColonies para “Livro de Feitiços” apenas se a API permitir predicate seguro por `ISpellContainer`.

O request deve aceitar um livro já preparado; não pode criar nem inscrever spells automaticamente.

Se o request system não puder preservar corretamente componentes do `ItemStack`, a entrega automática fica desabilitada e o livro deve ser fornecido manualmente pelo fluxo de inventário do MineColonies. Casting continua funcional; logística mágica não ganha um sistema paralelo.

## Morte do cidadão

A morte segue a política nativa do MineColonies para inventário/equipamento.

Acceptance obrigatório:

- livro não duplica;
- livro não é recriado pelo RPG;
- se MineColonies dropa/recupera o item, o RPG apenas observa esse resultado;
- cast em andamento termina sem duplicar projétil/efeito;
- novo cidadão não herda um clone do loadout do morto.

---

# Claims, proteção e world effects

Spells que alteram blocos, criam estruturas temporárias, teleportam ou possuem efeito de mundo não são liberados apenas porque estão no livro.

Para habilitar um spell desse grupo, o profile precisa registrar `world_effect: true` e o handler deve provar:

1. compatibilidade com claims/protection do MineColonies;
2. respeito às políticas globais de world effects do pack quando pertinentes;
3. ausência de grief contra a própria colônia;
4. rollback/lifecycle seguro quando o provider cria estado temporário.

Sem essa prova, o spell continua no livro mas o Mago de Batalha não o seleciona.

---

# Epic Fight e addons presentes no pack

Epic Fight não é requisito funcional do Mago de Batalha.

Quando Epic Fight/Epic Colonies/EFIS/Epic Fight × Iron's Spells Mobs estiverem presentes:

- eles podem adaptar animação/render/combat presentation;
- o RPG não chama seus handlers para executar o spell uma segunda vez;
- Mixins de guard AI devem ser testados juntos para evitar conflito de state machine;
- ausência desses addons não desativa o casting MineColonies × Iron's.

A integração deve manter **um cast, um pipeline**.

---

# Arquitetura de código planejada

Os nomes abaixo são o contrato alvo para a implementação; podem ser refinados apenas se a API exata exigir mudança documentada.

## Bootstrap e versão

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/MineColoniesVersionContract.java`
  - fixa a build suportada;
  - rejeita versão desconhecida em vez de tentar reflection permissiva.

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/BattleMageIntegrationBootstrap.java`
  - registra a bridge somente com MineColonies + Iron's presentes e versões aceitas;
  - publica diagnostic de `ACTIVE`, `ABSENT`, `UNSUPPORTED_VERSION` ou `FAILED_CLOSED`.

- `OptionalIntegrations.Provider.MINECOLONIES`
  - adiciona somente identidade/presença/versão; sem tipos MineColonies no core boundary.

## Guard/job adapter

- `.../minecolonies/battlemage/BattleMageGuardRegistration.java`
  - registra `rpgskilltree:battle_mage` pelo registry público confirmado;
  - não substitui Knight/Ranger globalmente.

- `.../minecolonies/battlemage/BattleMageCitizenResolver.java`
  - verifica se o cidadão pertence ao guard type correto;
  - resolve colony/ally/hostile identity sem scans globais.

## Loadout

- `.../minecolonies/battlemage/BattleMageLoadoutResolver.java`
  - encontra o spellbook real;
  - valida `ISpellContainer`;
  - retorna snapshot read-only dos `SpellData` ativos + identidade do ItemStack;
  - nunca muta o livro.

## Casting

- `.../minecolonies/battlemage/IronsCitizenMagicBridge.java`
  - única fronteira que toca `MagicData`/cast API do Iron's para cidadão;
  - inicia/cancela/encerra cast;
  - mantém `CastSource.MOB`;
  - não concede Mastery.

- `.../minecolonies/battlemage/BattleMageCombatController.java`
  - consome target atual do MineColonies;
  - roda em cadence bounded;
  - seleciona um único spell;
  - impede reentrada/double-cast.

## Profiles data-driven

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/data/BattleMageSpellProfileReloader.java`
- `src/main/resources/data/rpgskilltree/battle_mage_spell_profiles/*.json`

O reload deve validar schema, IDs, ranges e duplicatas. Reload inválido preserva a última revisão válida.

## Localização

Todo conteúdo player-facing próprio será pt-BR:

- `Mago de Batalha`;
- mensagens de livro ausente/incompatível;
- estado de spell não suportado quando exibido;
- diagnostics administrativos podem manter IDs técnicos em inglês.

---

# Sequência de implementação

## Fase 1 — Provar boundaries exatos

1. adicionar dependência `compileOnly`/dev runtime do MineColonies `1.1.1375-1.21.1-snapshot` sem transformar o provider em hard dependency do JAR final;
2. inspecionar o JAR/source correspondente e congelar os signatures usados de `GuardType`, `JobEntry`, inventory/equipment, guard AI e target selection;
3. provar `MagicData` + casting de Iron's 3.16.3 em `EntityCitizen` sem custom mana;
4. criar testes RED para version gate, provider absence e unsupported-version fail-closed;
5. somente então materializar o bootstrap.

## Fase 2 — GuardType e loadout real

1. registrar `rpgskilltree:battle_mage` pelo seam público;
2. provar assignment e persistence em uma colônia real de GameTest/integration fixture;
3. detectar spellbook real no inventário;
4. ler `ISpellContainer.get(book).getActiveSpells()`;
5. remover livro e provar que o loadout zera sem cópia residual.

## Fase 3 — Primeiro cast provider-native

1. profile de um spell core Iron's simples e comprovado;
2. target hostil fornecido pela guard AI;
3. pre-cast real do Iron's;
4. cast com `CastSource.MOB`;
5. mana/cast lifecycle real;
6. provar ausência de Mastery do jogador;
7. provar exatamente um efeito/projétil.

## Fase 4 — Policy tática e segurança

1. profiles `HOSTILE_ENTITY`, `SELF`, `ALLY_ENTITY`, `HOSTILE_AREA`;
2. range/line-of-sight quando aplicável;
3. friendly-fire guard;
4. unsupported spell fail-closed;
5. spell de mundo fail-closed por padrão;
6. deterministic priority e anti-spam.

## Fase 5 — Inventário/logística/lifecycle

1. request de spellbook somente se a API preservar o ItemStack completo;
2. troca de livro em runtime;
3. unload/reload;
4. morte do cidadão;
5. troca de job;
6. book return/drop sem duplicação;
7. multi-colony e multiplayer.

## Fase 6 — Compatibilidade do pack

Matriz mínima:

- MineColonies + Iron's + RPG;
- sem Iron's;
- sem MineColonies;
- Iron's presente / MineColonies versão não suportada;
- MineColonies + Iron's + Epic Fight;
- MineColonies + Iron's + Epic Colonies / Epic Fight × MineColonies;
- MineColonies + Iron's + EFIS / Epic Fight × Iron's Spells Mobs;
- dedicated server sem cliente conectado;
- reload de datapack durante cidadão online.

---

# Testes obrigatórios

## Unit/JUnit

- `BattleMageSpellPolicyTest`
  - spell desconhecido = unsupported;
  - prioridade determinística;
  - profile inválido rejeitado;
  - aliado na área bloqueia AoE inseguro.

- `BattleMageLoadoutResolverTest`
  - livro vazio;
  - livro com múltiplos `SpellData`;
  - troca de livro;
  - nenhum clone/mutação do container.

- `MineColoniesVersionContractTest`
  - aceita somente baseline explicitamente suportado;
  - versão nova/desconhecida falha fechado.

- `BattleMageCausalityTest`
  - cast de cidadão não gera `irons:casting`;
  - player cast continua gerando a lane existente uma vez.

## NeoForge GameTests / integração

- cidadão Battle Mage recebe livro → vê exatamente os spells do livro;
- livro A e livro B produzem loadouts diferentes sem reiniciar servidor;
- spell não presente nunca é lançado;
- cast válido consome mana real do Iron's;
- cast sem mana não executa;
- cast cancelado não duplica efeito;
- morte durante cast não duplica efeito nem livro;
- unload/reload não repete cast completo;
- AoE não dispara com aliado na zona de risco;
- spell unsupported é ignorado sem crash;
- ausência de um provider não causa classloading error.

## Dedicated-server smoke

O log deve registrar o estado do adapter e confirmar:

- zero `ClassNotFoundException` quando MineColonies ou Iron's estão ausentes;
- nenhum client class carregado server-side;
- version gate explícito;
- zero loop de erro por cidadão/tick.

---

# Performance

A IA deve ser bounded:

- nenhuma varredura global de cidadãos/entidades;
- usar target já resolvido pelo MineColonies quando possível;
- busca de aliados limitada ao raio necessário do spell candidato;
- loadout cache invalidado por mudança do ItemStack, não reconstruído integralmente a cada tick;
- spell decision cadence configurada em ticks e desacoplada do cooldown do provider;
- nenhum scan de registry por cidadão a cada tick.

---

# Anti-abuso, deduplicação e causalidade

1. um cast confirmado = uma execução do pipeline Iron's;
2. nenhum cast de NPC concede Mastery/XP ao jogador;
3. nenhuma inscrição automática gratuita;
4. nenhuma cópia de book/scroll/spell;
5. nenhuma restauração de mana por troca rápida de job/livro;
6. unload/reload não reseta custo para duplicar cast;
7. morte/recontratação não duplica equipamento;
8. unsupported spell não vira bônus genérico de dano;
9. ausência de adapter de animação não executa cast alternativo;
10. profiles só descrevem targeting/safety, nunca redefinem dano/custo/nível do spell.

---

# Critérios de aceite do subplano

Este arquivo só poderá virar `✅-10-minecolonies-battle-mages.md` quando todos os itens abaixo estiverem comprovados:

- [ ] MineColonies `1.1.1375-1.21.1-snapshot` auditado pela build exata usada no pack.
- [ ] GuardType/job extension usa API/seam estável comprovado, sem mixin frágil como authority primária.
- [ ] Cidadão real da colônia é o caster.
- [ ] Spellbook real do Iron's é o loadout autoritativo.
- [ ] Spells e níveis são lidos do `ISpellContainer` sem cópia/mutação indevida.
- [ ] Iron's `MagicData` é usado; não existe mana paralela.
- [ ] Cast lifecycle é provider-native e usa `CastSource.MOB`.
- [ ] Pelo menos os quatro target modes iniciais possuem testes de segurança ou ficam explicitamente sem profile.
- [ ] Friendly fire fail-closed está validado.
- [ ] World-effect spells são bloqueados até handler seguro.
- [ ] Remover/trocar o livro altera imediatamente o loadout.
- [ ] Morte/unload/troca de job não duplica livro nem efeito.
- [ ] Cast autônomo não concede Mastery/RPG XP ao jogador.
- [ ] Optional classloading funciona com cada provider ausente.
- [ ] Compatibilidade com o stack Epic Fight/MineColonies presente no pack foi testada.
- [ ] JUnit GREEN.
- [ ] NeoForge GameTests GREEN.
- [ ] NeoForge build GREEN.
- [ ] Dedicated-server smoke GREEN.
- [ ] CI GREEN.
- [ ] PR revisada, mergeada e `main` pós-merge confirmada.

---

# Não fazer

- não transformar o spellbook em simples “item-gate” e dar uma lista fixa de spells por código;
- não lançar dano mágico genérico no lugar do spell real;
- não criar mana/cooldown próprios;
- não copiar `AbstractSpellCastingMob` ou assets do Iron's;
- não subclassificar `EntityCitizen` de forma incompatível com MineColonies;
- não bypassar claims para permitir spells destrutivos;
- não conceder Mastery por tick, por posse do livro ou por cast autônomo;
- não consumir scroll manualmente fingindo `CastSource.SCROLL` enquanto o provider continuar player-bound;
- não usar Epic Fight como segunda authority de cast;
- não promover este plano a implementado porque um prototype funcionou com um único spell.

---

# Resultado esperado para o jogador

O jogador monta um spellbook real — investindo scrolls, progressão, loot e materiais — e entrega esse item a um cidadão configurado como **Mago de Batalha**. O cidadão passa a patrulhar/proteger a colônia pela infraestrutura MineColonies e, quando encontra uma ameaça, escolhe apenas entre os feitiços realmente inscritos no livro. Trocar o livro muda o repertório. Tirar o livro remove a magia. Um livro melhor torna a unidade melhor porque o equipamento é melhor, não porque o RPG inventou um bônus oculto.

A unidade pode ficar extremamente poderosa, mas o poder está materializado em equipamento mágico de alto valor que permanece comprometido com o cidadão e sujeito ao risco/lifecycle real da colônia.
