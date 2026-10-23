# Enhanced AI

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#252**: JAR `enhancedai-4.2.3.0.jar`, mod id `enhancedai`, runtime `4.2.3.0`, SHA-1 `763e60f869129a37cb7659e1a25bd3f50f6bd9ea`.

## Propriedades do registro

- **Mod:** Enhanced AI
- **Arquivo JAR:** `enhancedai-4.2.3.0.jar`
- **Versão 1.21.1:** `4.2.3.0`
- **Categoria:** Mobs, RPG
- **Função:** Reformula e amplia comportamentos de IA de mobs, sobretudo hostis vanilla, com módulos configuráveis de perseguição, combate, anti-cheese, breaching/mining/sniping e outras respostas ao ambiente.
- **Dependências:** InsaneLib 2.4.33.0 está fisicamente presente e é a biblioteca exigida pela linha publicada do Enhanced AI. Runtime físico: Enhanced AI 4.2.3.0 / NeoForge 1.21.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Altera goals/comportamentos e pode aumentar dificuldade e custo de CPU. Modded mobs não devem ser assumidos como afetados automaticamente; cruzar com Epic Fight/entity patches e outros AI mods por goal duplication, pathfinding excessivo, block interaction inesperado e double-processing. A upstream 4.2.4.0 altera probabilidades do MPR Data Pack conforme profundidade/lua cheia e corrige módulos de combat/targeting/swimming, portanto merece regressão de dificuldade e AI state.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enhanced-ai
- **Procedência:** modlist física atual confirma `enhancedai-4.2.3.0.jar`, mod id `enhancedai`, runtime 4.2.3.0, SHA-1 `763e60f869129a37cb7659e1a25bd3f50f6bd9ea` e InsaneLib físico. CurseForge oficial revalidado em 01/10/2026 mostra 4.2.4.0 como única release 1.21.1 posterior a 4.2.3.0.
- **Observações:** Runtime físico 4.2.3.0. A upstream 4.2.4.0 corrige Blow up on death, Fisher e Better Drowning Swimming Up, limita teleport de targets por Enderman ao follow range e muda o MPR Data Pack para equipment/enchants escalarem com profundidade e lua cheia; não está instalada.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece Enhanced AI 4.2.3.0. A única release posterior 4.2.4.0 foi comparada e seus deltas relevantes foram incorporados abaixo.
- **Decisão:** Sem decisão
- **Sobreposição:** Enhanced AI muda comportamento/IA; AI-Improvements reduz custo de processamento de IA. Epic Fight pode patchar entidades/combate. Essas superfícies se cruzam, mas não são equivalentes; validar ownership de goals e combat state.
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `enhancedai-4.2.3.0.jar` · mod id `enhancedai` · versão `4.2.3.0` · NeoForge 1.21.1. **InsaneLib 2.4.33.0** está presente. O changelog oficial específico da build física 4.2.3.0 foi revalidado em 12/09/2026 e é separado abaixo do feature set geral do projeto.
## 1. Papel no modpack
Enhanced AI modifica a lógica comportamental de mobs para torná-los menos previsíveis e mais difíceis de explorar com estratégias simples. O projeto descreve o objetivo por exemplos como **Creepers breaching, Zombies mining e Skeletons sniping**, além de vários módulos configuráveis de reação/combate.
## 2. Authority / ownership
- **Minecraft/entity provider:** entidade, atributos-base, navigation e goals originais.
- **Enhanced AI:** goals/adaptações comportamentais que injeta e seus critérios/configuração.
- **Epic Fight:** combat/animation/entity-patch state quando um mob é patchado por esse framework.
- **AI-Improvements:** otimização de processamento; não define a dificuldade comportamental do Enhanced AI.
Não duplicar um mesmo goal/ataque em KubeJS ou mod próprio apenas porque o comportamento visual parece semelhante.
## 3. Escopo documentado
A documentação pública da linha atual lista módulos/áreas como:
- Anti-Cheese;
- Explosion Avoidance;
- Break Anger;
- Fishers;
- Custom Mob Fleeing;
- Pearlers;
- Biters;
- Item Disruption;
- Melee Attacking;
- Spawning;
- além das assinaturas de breaching/mining/sniping citadas pelo projeto.
Esses nomes representam superfícies configuráveis do projeto. Esta ficha não inventa valores, chances, ranges ou entidades específicas não confirmadas para 4.2.3.0.
Na build física **4.2.3.0**, o changelog oficial confirma especificamente quatro deltas: mobs podem pathfind livremente sobre rails mesmo sem já estarem sobre um rail; Miner mobs podem minerar usando main hand e off-hand por meio da data key `offhand`, cujo default documentado é `true`; Pearler AI volta a ser aplicada mesmo quando a entidade não está equipando pearls; e Fisher AI deixa de encerrar quando perde line of sight do alvo. Esses quatro pontos são regression gates da build instalada, não extrapolações de versões anteriores.
## 4. Vanilla vs modded mobs
A documentação atual informa que, desde a linha v3, o foco padrão é **mobs vanilla**. Entidades modded podem ser incluídas em features relevantes por **Entity Type Tags**, conforme a documentação do projeto.
Consequência operacional: presença de Alex's Mobs, Cataclysm ou outro mob mod não prova que todo mob desses projetos recebe Enhanced AI. A tag/config efetiva é a autoridade.
## 5. Configuração
O projeto é fortemente configurável e apresenta o preset padrão como um aumento moderado de dificuldade, podendo ser ajustado para mais ou menos agressividade.
Antes de balancear o pack, auditar o arquivo de configuração gerado pela build física; não assumir defaults de uma versão anterior. Mudanças de config alteram gameplay e devem ser tratadas como decisão de pack.
## 6. Entity Type Tags
Tags de entity type são a superfície apropriada para opt-in/eligibility de mobs modded quando suportado por cada feature. Isso reduz a necessidade de hardcode por mod id.
Ao customizar, usar ResourceLocations reais e testar `/reload`/restart conforme o mecanismo de dados da build; uma tag malformada pode simplesmente deixar uma integração inativa sem crash evidente.
## 7. Perseguição e anti-cheese
O objetivo do mod inclui reduzir estratégias em que o jogador explora limitações previsíveis da IA. Isso pode mudar navigation, seleção de alvo e resposta ao ambiente.
Não confundir melhora de perseguição com aumento de atributos: velocidade, dano ou alcance devem ser atribuídos apenas quando o módulo/config correspondente realmente o fizer.
## 8. Interação com ambiente
Features como breaching/mining implicam que certos mobs podem responder a obstáculos de forma diferente do vanilla. Em um modpack com construções complexas, claims e máquinas, isso precisa de QA de proteção de blocos e de pathfinding.
Não afirmar que qualquer bloco modded é quebrável sem teste/config/tag correspondente.
## 9. Combate e Epic Fight
Epic Fight 21.17.3.1 está presente e pode substituir/patchar a camada de combate/animação de entidades. Enhanced AI e Epic Fight podem coexistir em níveis diferentes: um decide intenção/goal, o outro pode decidir moveset/combat patch.
O risco é dois providers tentarem controlar o mesmo ataque, target ou movement state. Testar mobs vanilla e mobs explicitamente patchados pelo Epic Fight.
## 10. AI-Improvements
`AI-Improvements 0.5.3` também está instalado. Seu papel é reduzir trabalho/custo de IA, enquanto Enhanced AI adiciona/expande comportamento. Não são duplicatas.
Regressão de performance deve medir TPS/CPU com os dois ativos, porque mais goals podem compensar parte do ganho de otimização.
## 11. Client / Server
Decisões de target, movement, block interaction, ataques e spawn são **server-authoritative**. Cliente renderiza o resultado e não deve decidir se um goal pode executar.
Dedicated server é o cenário de referência para validar compatibilidade; efeitos puramente visuais não devem ser usados como prova de execução da AI.
## 12. Lifecycle
Validar:
- spawn/despawn;
- aquisição/perda de alvo;
- chunk unload/reload;
- server restart;
- dimension change/portal quando aplicável;
- alteração de dificuldade;
- datapack/tag reload quando suportado;
- mudança de config;
- entidade entrar/sair de combate Epic Fight;
- mob persistente após relog dos jogadores.
## 13. Multiplayer
Com vários jogadores, target switching e pathfinding precisam convergir no servidor. Um mob não deve executar duas ações equivalentes por cada observador/client.
Testar dois jogadores atraindo o mesmo mob, logout do alvo, distância extrema e retorno ao chunk.
## 14. Riscos
1. goal duplicado com outro AI/combat mod;
2. pathfinding excessivo aumentar CPU;
3. mob modded incluído por tag sem suportar o comportamento esperado;
4. block breaking/mining afetar construção modded indevidamente;
5. target stale após logout/dimension change;
6. Epic Fight e Enhanced AI disputarem ataque/movement state;
7. AI-Improvements alterar timing e expor race/regressão;
8. config antiga ser reutilizada com semântica diferente;
9. spawn/behavior module ampliar dificuldade além do planejado;
10. regressão específica de 4.2.3.0 em rail pathfinding, off-hand mining, Pearler eligibility ou Fisher line-of-sight.
## 15. Matriz de testes
1. Dedicated server boot com InsaneLib 2.4.33.0.
2. Zombies, Creepers e Skeletons em obstáculos/combate controlado.
3. Anti-cheese em arena reproduzível.
4. Dois jogadores alternando aggro.
5. Logout do alvo durante perseguição.
6. Chunk unload/reload com mob persistente.
7. Mob vanilla patchado pelo Epic Fight.
8. Mob modded somente após opt-in explícito por tag/config.
9. Testar bloco vanilla e bloco modded diante de features de interação ambiental.
10. Medir TPS/CPU com Enhanced AI + AI-Improvements.
11. Restart e revalidação de config/tags.
12. Atualização futura de Enhanced AI em instância de teste antes de promover ao pack.
**Esta catalogação não afirma que esses testes foram executados.**
## 16. Evidências
- modlist física atual: JAR/mod id/versão 4.2.3.0, InsaneLib 2.4.33.0, AI-Improvements 0.5.3 e Epic Fight 21.17.3.1;
- CurseForge oficial da 4.2.3.0: rail pathfinding, off-hand mining via `offhand`, Pearler AI e Fisher line-of-sight;
- CurseForge/GitHub oficiais do Enhanced AI: objetivo, feature set e configuração;
- wiki/projeto: foco vanilla desde v3 e opt-in de mobs modded via Entity Type Tags.
> **Boundary canônico:** Enhanced AI é authority apenas dos **comportamentos que injeta**. O entity provider mantém a entidade-base e Epic Fight mantém seu próprio combat framework quando aplicável.

## 17. Atualização upstream 4.2.4.0 — não instalada

A autoridade física continua em **Enhanced AI 4.2.3.0**. O CurseForge oficial publica **4.2.4.0** para NeoForge 1.21.1 em 22/09/2026, sem release intermediária 1.21.1 entre 4.2.3.0 e 4.2.4.0.

### Deltas oficiais
- corrige **Blow up on death** que não funcionava corretamente (issue #250);
- no **MPR Data Pack**, chance de mobs equiparem armor — tentativa de full set e chance por peça — passa a escalar com profundidade até **+20%** no fundo do mundo, substituindo o bônus fixo abaixo de light level 12;
- bônus de full moon para essa chance sobe de **+10% para +20%**;
- a mesma escala de profundidade e lua cheia passa a valer para chance de equipment (weapons/tools/armor) vir **enchanted**;
- Iron e Diamond armor ficam relativamente mais prováveis que materiais fracos no subterrâneo ou full moon;
- Enderman deixa de teleportar targets para além do próprio **follow range**;
- corrige Fisher mobs que deixavam de atacar e passavam a ignorar line of sight;
- corrige **Better Drowning Swimming Up** que não fazia o mob nadar para cima.

### Impacto para o pack
As alterações do MPR Data Pack mudam a curva efetiva de dificuldade por profundidade e ciclo lunar; isso precisa ser tratado como mudança de balanceamento, não apenas bugfix. Os fixes de Fisher/Enderman/drowning tocam targeting/navigation e podem cruzar Epic Fight, mob patches e otimizações de IA presentes no pack.

### Gate de promoção 4.2.3.0 → 4.2.4.0
- [ ] Blow up on death dispara exatamente uma vez e não duplica damage/explosion hooks.
- [ ] Equipment chance é amostrada em superfície, profundidades intermediárias e fundo do mundo.
- [ ] Full moon aplica o bônus esperado sem double-count com depth scaling.
- [ ] Enchantment chance segue a mesma regra documentada.
- [ ] Iron/Diamond distribution é comparada em cenário underground/full moon.
- [ ] Enderman não move target além do follow range e não deixa target/navigation stale.
- [ ] Fisher mobs respeitam line of sight e retomam/cessam ataque corretamente.
- [ ] Better Drowning Swimming Up produz deslocamento vertical correto.
- [ ] Epic Fight + Enhanced AI não duplicam attack/target state nos mobs afetados.
- [ ] TPS/CPU é comparado com AI-Improvements durante cenário multi-mob.

Fonte upstream: CurseForge Enhanced AI 4.2.4.0, file ID 8946964, NeoForge 1.21.1. Nenhum teste acima foi executado nesta atualização documental.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Enhanced AI
- **Último candidato 1.21.1 / NeoForge localizado:** **4.2.4.0 (NeoForge 1.21.1)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Validar blow-up-on-death, equipment/enchant scaling por profundidade/lua, Enderman follow range, Fisher LOS, drowning swim-up, interação com Epic Fight/AI-Improvements e performance server-side.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/enhanced-ai

## Auditoria completa de releases 4.2.3.0 → 4.2.5.0 — 23/10/2026

**Baseline top-level preservada:** `enhancedai-4.2.3.0.jar`, posição física #252, NeoForge 1.21.1. O CurseForge oficial **Enhanced AI** (projeto **515276**, Insane96) publica **exatamente duas releases posteriores dessa linha**: **4.2.4.0, 22/09/2026, file 8946964** e **4.2.5.0, 20/10/2026, file 9271399**, ambas Release para **NeoForge 1.21.1**. A versão 4.2.5.0 substitui o candidato incompleto 4.2.4.0 do índice anterior.

### 4.2.4.0 — 22/09/2026
O changelog da publicação exata foi anteriormente discriminado na seção 17 deste dossier: corrige Blow up on Death, Enderman follow range, Fisher line of sight/attack e Better Drowning Swimming Up. Ajusta **MPR Data Pack** (chance de armor/equipment e enchantment escalando com profundidade até +20%, bônus de lua cheia +20%, materiais iron/diamond relativamente mais favorecidos subterrâneo/lua cheia). Fonte: https://www.curseforge.com/minecraft/mc-mods/enhanced-ai/files/8946964 .

### 4.2.5.0 — 20/10/2026
A página de release **`enhancedai-4.2.5.0.jar`** contém um extenso changelog cujo primeiro cabeçalho interno está escrito **`2.4.5.0`**; isso é **erro tipográfico no texto do changelog**, não um artefato `2.4.5.0` instalado. Filename/loader/file ID explícitos identificam a versão correta **4.2.5.0**.

Mudanças relevantes do changelog do autor, separadas por subsistemas:

- **Healing:** a feature anteriormente limitada ao Snow Golem foi ampliada para **Healed by Snowballs**, potencialmente elegendo outras entidades conforme regra/config.
- **Shielding:** chance de block agora respeita direção frontal, comportamento de vanilla shields, também projectiles/explosions, exceto piercing arrows. Fix offhand substituído indevidamente (por exemplo, pickaxe de miner); hurt shields devem aplicar enchants/propriedades relevantes do portador.
- **Skeleton AI:** evita wandering durante shooting; prioridade do goal de shoot comparável a vanilla (sol/locos como wolves podem interromper ataque); modded bows voltam a funcionar.
- **Targeting:** Better Hurt by Target preserva ordem de prioridades e timing de retaliation; Creepers/Endermen retaliam apropriadamente; `Prevent infighting` não aplica probabilidade invertida; animals mantêm `HurtByTargetGoal`. Better Nearby Targeting respeita custom vanilla goals e goals de outros mods (Polar Bears, Witches, Foxes, Bees) em vez de substituí-los; troca de alvo e perda de percepção deixam de apagar alvo novo cedo demais.
- **Teleport to Target:** destino de teleporte, colisão em paredes, não rerrolar chance em reload; entidades temporariamente ocultas reaparecem e não ficam flutuando/unloaded com objetivos suspensos.
- **Riding/Pick Up and Throw:** escolher montaria e sorteio de chance não deve rerrolar a cada load/chunk unload, o que alterava estatísticas em mundos de longa duração.
- **Actual Blindness:** previne crash ao consultar visibilidade sem entidade olhando (ex. End Crystal/Dragon), e corrige multiplicador duplo de follow range e penalty não removida no expiration.
- **Leaders / reinforcements:** chance de reforços passa a usar damage antes da redução por armor/enchant; `Charge per spawn` não inverte e não aplica uma vez apenas.
- **Warden:** taxa de atualização de anger deixa de variar indevidamente quando Darkness Range está habilitado; previne crash com wardens existentes e volta a permitir sonic boom em entidades previamente carregadas.
- **Witches / Dark Art:** potion selection não buffa inimigos indevidamente; ritual é marcado e executado uma vez, mesmo com interrupção/reload; não deixa vilarejos invulneráveis/tags residuais, egg na mão ou particles ausentes.
- **Drowned/Fisher:** dismount de drowned vivo/morto e cancelamento de goals; evita mob sem dano após unload enquanto afogava alguém; limita Sun Resistant time; corrige velocidade excessiva de recolher vara dos Fisher e interrupção inadequada do goal ao iniciar em alcance.
- **Item disruption:** manipula o slot realmente selecionado, não o primeiro item idêntico do inventário, e bloqueia uso do item interrompido.
- **Outros:** Patrol não deixa mobs presos em waypoint impossível; tempos de detecção de mobs presos em Parkour/Spider Destuck e Creeper Breaching corrigidos; Teleport Anti-Cheese não teleporta em alcance melee; Vehicle Anti-Cheese não bloqueia dismount; forced-explosion Creeper deixa de inflar se perde target; breaching Creepers retêm target 15 s sob Mobs > Targeting off (antes 3 s); Angry Creepers data não reaplicada a cada load.

**Fonte primária detalhada:** https://www.curseforge.com/minecraft/mc-mods/enhanced-ai/files/9271399 (ID 9271399, 20/10/2026; confirmar página exata, não título interno com typo). **Categorias oficiais atuais**: somente `Mobs`, em acordo com a pasta física.

**Impactos novos e testes de risco:** (a) regras de Shielding podem afetar balanceamento de Epic Fight, projetéis de outros mods e shields com encantamentos; (b) goals de alvo/retaliation não devem sobrescrever AI de entidades de addons; (c) não rerrolar chance ao reiniciar ou descarregar chunk é relevante para saves longos; (d) Warden/Witch/Ender Dragon/Drowned requerem casos reproduzíveis e verificação de fix em servidor dedicado; (e) MPR balance em 4.2.4.0 altera equipamento de mobs em lua cheia/altitude; (f) comparar TPS, CPU e aggro com AI-Improvements 0.5.3 e Epic Fight 21.17.3.1 do snapshot.

**Conclusão da pesquisa de releases:** `4.2.3.0 → 4.2.4.0 → 4.2.5.0` **revisadas individualmente** com changelogs primários; versão física continua 4.2.3.0 e **não houve testes runtime ou troca de JAR**. A documentação pode sair da fila de pesquisas de atualização; QA/instalação eventual é outra tarefa.
