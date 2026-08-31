# 08.07 — Narrative & Society Core — plano mestre

## Objetivo

Transformar o Stage 08 de simples hooks de quests em uma plataforma narrativa sistêmica capaz de sustentar uma campanha longa, não linear e reativa, inspirada em CRPGs como Baldur's Gate 3, sem transformar nenhum framework externo de quests/NPC em autoridade de gameplay.

A campanha deve aceitar, como casos normais:

- **SIM:** aceitar missão, condição, pacto, responsabilidade ou consequência;
- **NÃO:** recusar sem quebrar a história; o mundo pode seguir sem o jogador;
- **ANTES:** o jogador pode cumprir, descobrir, matar, salvar, construir, visitar ou aprender antes de a missão correspondente existir;
- **DEPOIS:** consequências podem surgir horas ou dezenas de horas depois e reinterpretar decisões anteriores;
- sucesso, sucesso parcial, sucesso com custo, fracasso, fracasso produtivo, abandono, resolução por terceiros, resolução antecipada, irrelevância posterior e conversão em outra missão;
- NPCs importantes podem morrer sem quebrar a campanha;
- bosses podem morrer antes de receber sua quest;
- itens narrativos não precisam ser magicamente indestrutíveis: vender, perder, destruir ou usar de outra maneira gera rota alternativa quando o conteúdo exigir;
- facções/NPCs só sabem o que poderiam legitimamente saber.

## Autoridade canônica

`Narrative & Society Core` pertence ao RPG Skill Tree e será autoridade server-side para:

- World Narrative State;
- Event Ledger e cronologia;
- flags e facts tipados;
- conhecimento por ator;
- segredos, testemunhas e evidências;
- relações pessoais multidimensionais;
- facções e reputações;
- ideologias e posições temáticas;
- instituições e influência política;
- assentamentos e estados sociais derivados;
- leis/políticas narrativas;
- opinião pública e estabilidade;
- consequências atrasadas;
- decisões, resoluções e histórico de quests;
- estados globais da campanha;
- epílogos combinatórios.

Providers externos continuam autoridade de seus próprios domínios. Narrativa pode relacionar sistemas por causa/efeito, mas nunca fundi-los semanticamente sem bridge real: Shroud/Exposure != Black Arcana Corruption/Strain; Atmosphere/pressão != resistência arcana; Source != mana != Soul Energy; MineColonies happiness != opinião pública do Narrative Core.

## Stack externa escolhida

Ver `DEPENDENCIAS-NARRATIVA.md`.

- Easy NPC: rosto/interação/diálogo.
- FTB Quests: diário/UI de acompanhamento.
- MineColonies: colônia física e cidadãos.
- KubeJS: authoring, protótipos e glue controlado.

Todos os adapters devem ser opcionais para o jar base.

---

# Arquitetura

```text
RPG Skill Tree
└── Narrative & Society Core
    ├── Narrative State
    ├── Event Ledger + Chronology
    ├── Knowledge / Secrets / Witnesses / Evidence
    ├── NPC Memory & Relationships
    ├── Factions / Ideologies / Institutions
    ├── Settlements / Laws / Public Opinion / Stability
    ├── Consequence Scheduler
    ├── Choice Resolution Engine
    ├── Campaign / Era State
    └── Read-only Authoring/Diagnostics API

Adapters opcionais
├── Easy NPC
├── FTB Quests
├── MineColonies
├── KubeJS
└── providers do modpack
```

## Regra: estado narrativo não é scoreboard

Scoreboards/tags/comandos podem ser portas de interoperabilidade, mas não constituem o storage canônico. A escala prevista inclui centenas ou milhares de facts, múltiplos atores, conhecimento parcial, relações multidimensionais e cronologia. O save precisa ser versionado, tipado e migrável.

---

# Modelo de estado

## 1. Event Ledger

Eventos relevantes recebem ID estável, timestamp lógico, dimensão/local quando aplicável, ator causal, alvos e payload mínimo.

Exemplos:

- `first_copper_worked`
- `first_colony_founded`
- `first_volcano_discovered`
- `first_eruption_survived`
- `first_shroud_entry`
- `first_black_arcana_cast`
- `first_corpse_revived`
- `first_vampire_feeding`
- `first_create_factory`
- `first_me_network`
- `first_airship`
- `first_planet_visited`

O runtime precisa responder relações temporais como `A BEFORE B`, `A AFTER B`, `A NEVER`, além de first/last/count quando semanticamente permitido.

Eventos contínuos não devem explodir o ledger. Usar milestones, state transitions e coalescing.

## 2. Facts

Facts representam verdades atuais ou históricas, não necessariamente eventos:

- `severin.status = HIDDEN`
- `settlement.necromancy_law = FORBIDDEN`
- `stonefall.evacuated = PARTIAL`
- `player.vampire_status_known_by_clergy = true`

Facts possuem scope explícito: world, player, settlement, faction, NPC, quest/arc, region ou provider-bound.

## 3. Knowledge

Distinguir:

- ouviu falar;
- viu;
- possui evidência;
- participou;
- causou;
- deduziu/confirmou.

Knowledge é por ator/grupo; não existe onisciência global automática.

## 4. Evidence

Evidência pode ser física, testemunhal, documental, mágica ou sistêmica. A autoridade não é o item visual em si, e sim um identity record que pode referenciar item/entity/POI quando necessário.

## 5. Relationships

Não usar uma única barra de amizade. Perfil mínimo proposto:

- affection;
- trust;
- respect;
- fear;
- dependency;
- ideological_alignment;
- grievance ledger limitado;
- favor/debt ledger limitado.

Valores derivados precisam de clamps e regras de decay/eventos discretos, não tick spam.

## 6. Factions

Facções possuem:

- reputação do jogador;
- relação facção↔facção;
- valores/ideologias;
- hostilidade política;
- knowledge compartilhado somente quando propagado;
- instituições/territórios associados quando aplicável.

## 7. Institutions

Instituições são blocos de poder dentro de um assentamento/facção, por exemplo:

- clero;
- militares;
- academia;
- mercadores;
- guildas;
- conselho;
- círculo arcano;
- hunters;
- casas vampíricas.

Cada uma possui influence e posições temáticas. Não precisam corresponder 1:1 a mods.

## 8. Settlement Society

Assentamentos narrativos consultam o provider físico quando existir, especialmente MineColonies, mas conservam estado social próprio:

- forma de governo;
- legitimidade/authority do jogador;
- estabilidade;
- medo;
- confiança pública;
- tolerâncias temáticas;
- influência institucional;
- leis/políticas;
- crises ativas;
- memória de eventos locais.

Opinião pública não duplica `happiness` do MineColonies. Um adapter pode usar happiness como sinal entre vários, sem substituir nenhum dos dois estados.

## 9. Laws / Policies

Leis são data-driven e tipadas. Exemplos:

- necromancia: `FORBIDDEN | LICENSED | PERMITTED | ENCOURAGED`;
- vampirismo: `EXECUTION | EXILE | REGULATED | TOLERATED | PROTECTED`;
- black_arcana: `FORBIDDEN | RESTRICTED_RESEARCH | LICENSED | OPEN`;
- corpse_experimentation;
- industrial_pollution;
- nuclear_research;
- shroud_research;
- religious_freedom;
- conscription.

Não codificar uma moral universal. Consequências derivam de atores, instituições, história e condições reais.

## 10. Consequence Scheduler

Consequências podem ser:

- imediatas;
- após duração mínima/máxima;
- ao entrar em região;
- ao reencontrar ator;
- ao ocorrer outro evento;
- probabilísticas apenas quando seed/determinismo e design justificarem;
- canceláveis/transformáveis por novos fatos.

Scheduler persiste e é idempotente. Logout/restart não deve duplicar consequência.

---

# Motor SIM / NÃO / ANTES / DEPOIS

Toda quest/beat significativo deve declarar:

1. preconditions;
2. discovery conditions;
3. alternate entry conditions;
4. prior-event reconciliation;
5. choices;
6. immediate mutations;
7. scheduled consequences;
8. failure-forward routes;
9. NPC death fallback;
10. objective-already-completed route;
11. knowledge/evidence propagation;
12. resolution taxonomy;
13. cleanup/idempotency key.

## Resoluções canônicas

- `SUCCESS`
- `SUCCESS_WITH_COST`
- `PARTIAL_SUCCESS`
- `FAILURE`
- `PRODUCTIVE_FAILURE`
- `ABANDONED`
- `RESOLVED_BY_OTHERS`
- `PRE_RESOLVED`
- `OBSOLETE`
- `TRANSFORMED`

---

# Sistema social exemplar — Severin, o necromante

Este cenário é acceptance exemplar do Stage 08 narrativo.

## Descoberta

O jogador encontra Severin, um pesquisador/necromante. O conteúdo específico que ele domina deve respeitar providers reais e não tratar Goety, Malum, Eidolon, Mobstein e Black Arcana como o mesmo sistema só porque compartilham tema sombrio.

O jogador pode:

- rejeitá-lo;
- aceitá-lo;
- atacá-lo;
- denunciá-lo;
- ajudá-lo sem levá-lo à colônia;
- tê-lo encontrado depois de já utilizar necromancia;
- já possuir uma colônia com leis relevantes;
- não possuir colônia alguma.

## Ao levá-lo à colônia

O runtime consulta, conforme disponível:

- governo;
- lei de necromancia;
- influence do clero;
- influence militar/academia;
- histórico local com undead/necromancia;
- reputação do jogador;
- authority/fear/trust;
- conhecimento público sobre Severin.

Se a necromancia for rejeitada, rotas possíveis incluem:

- expulsá-lo;
- defendê-lo publicamente;
- escondê-lo;
- instalá-lo fora das muralhas;
- criar licença/exceção;
- mudar a lei;
- negociar com clero;
- usar favor/dívida;
- intimidar;
- aceitar julgamento;
- abandonar a cidade com Severin;
- impor decisão e arriscar crise/guerra civil.

## Esconder Severin

`severin.status = HIDDEN` evita conhecimento público automático, mas altera a relação pessoal conforme personalidade/ideologia. Exemplo de efeito de conteúdo, não valor hardcoded universal:

- loyalty/trust pode permanecer;
- respect pode cair porque ele se sente usado/vergonzosamente oculto;
- dependency pode subir;
- grievance `kept_hidden_by_player` é registrada.

## Descoberta por testemunha

Um cidadão encontra Severin. Criar:

- evento de descoberta;
- knowledge para a testemunha;
- opcional evidence record;
- possível consequência de propagação.

A cidade inteira **não** aprende instantaneamente.

A testemunha pode:

- denunciar;
- guardar segredo;
- chantagear;
- investigar;
- tentar matar Severin;
- tornar-se aliada dele.

## Teocracia

Se o governo/instituição religiosa possuir autoridade suficiente e a lei proibir necromancia, o clero pode iniciar investigação/julgamento. Dependendo de authority do jogador e legislação, pode:

- exigir exílio;
- confiscar grimórios;
- banir Severin;
- ordenar execução;
- penalizar o jogador;
- tentar remover o próprio jogador da liderança.

Se Severin já salvou a cidade, houver provas de benefício, dívida política, apoio militar ou população favorável, rotas adicionais surgem.

## Valor do dilema

Severin deve oferecer conteúdo realmente valioso e exclusivo dentro dos providers permitidos: conhecimento, research, quest access, interpretação de evidência, ritual/permissão ou outro unlock auditado. Não criar escolha moral falsa por recompensa banal.

Acceptance: o mesmo NPC precisa produzir estados drasticamente diferentes sem scripts monolíticos duplicados para cada governo.

---

# Macro-história da campanha

A narrativa base é **Crônicas da Concordância Quebrada**.

## Passado

### A Concordância

Rede histórica de sociedades que cooperavam apesar de usar sistemas diferentes de tecnologia, magia e ciência. Sua grande conquista foi compreender que os sistemas do mundo eram distintos e precisavam de boundaries: Source não era mana; Soul Energy não era spirit; geologia não era magia; Shroud não era corrupção arcana.

### Projeto Continuidade

Projeto criado para preservar a civilização diante de múltiplas ameaças. Engenheiros, magos, arquivistas, necromantes e pesquisadores discordaram sobre o que significava preservar uma civilização, uma pessoa ou um corpo.

### A Ruptura

O colapso não possui uma causa única simples. Diversos sistemas falharam/interagiram no mesmo período: crises geológicas, industriais, dimensionais, arcanas e sociais. A história não deve transformar todos os hazards do pack em uma energia única fictícia.

## Presente

A civilização perdeu conhecimento e voltou a níveis tecnológicos muito inferiores em muitas regiões. A progressão do jogador representa redescoberta/reconstrução, não invenção de tudo pela primeira vez.

## Eras temáticas, não capítulos rígidos

1. **Pedra, fome e frio** — sobrevivência, TFC, temperatura, sede, nutrição, fauna, Compêndio.
2. **Uma cidade pode morrer** — MineColonies, agricultura, população, governo, raids, política local.
3. **As engrenagens voltam a girar** — Create, metalurgia, ferrovias, indústria inicial.
4. **Não existe “a magia”** — Ars, Iron's, Hexalia e tradições independentes.
5. **O problema da morte** — Goety, Malum, Eidolon, Mobstein e demais providers sem equivalência automática.
6. **Sangue na fronteira** — Vampirism/hunters e conflitos de identidade/política.
7. **A terra respira** — Volcanoes, geologia, tectônica, Atmosphere, gases e pressão.
8. **O Shroud** — Enshrouded, Exposure, Flame, Sanctuary e Story State.
9. **Black Arcana** — magia perigosa, Arcane Danger, custo e responsabilidade.
10. **Ruínas de coisas maiores** — bosses, dungeons, Deep Dark, End, Cataclysm e exploração.
11. **A cidade-máquina** — AE2, Oritech, Create avançado, TFMG, Destroy, energia/nuclear.
12. **O céu passa a ser território** — Sable, Aeronautics, veículos, trens e estruturas móveis.
13. **O céu não é o fim** — exploração espacial e descoberta de história extraplanetária quando providers reais permitirem.
14. **A Segunda Concordância** — síntese política/cultural construída a partir do histórico real do mundo.

A ordem é maleável. O sistema deve reconciliar descobertas antecipadas e não bloquear conteúdo só para preservar uma sequência artificial.

---

# Epílogos combinatórios

Não criar apenas 2–8 finais. Manter eixos independentes, por exemplo:

- civilização: federação / centralização / cidades independentes / nomadismo;
- tecnologia: descentralizada / industrial / automatizada / militarizada;
- magia: aberta / regulada / restrita;
- necromancia: aceita / regulada / proibida;
- vampirismo: hunters dominantes / vampiros dominantes / tratado / equilíbrio hostil;
- ecologia: preservada / gerenciada / explorada / colapsada;
- Shroud: contido / recuado / avançando / adaptação;
- Black Arcana: proibida / controlada / institucionalizada;
- espaço: limitado / colonização / expansão / êxodo.

O epílogo é composto por facts e history, com conflitos validados para evitar combinações semanticamente impossíveis.

---

# Conteúdo potencialmente infinito

A campanha principal é finita, mas o world state deve permitir geração/authoring contínuo de:

- crises sazonais;
- crises geológicas;
- problemas agrícolas;
- disputas políticas;
- conflitos de facções;
- eventos vampíricos/ocultos;
- novos focos Shroud;
- exploração de estruturas/regiões;
- crises industriais;
- expansões futuras adicionadas por datapack sem resetar o mundo.

“Narrativa infinita” significa capacidade de extensão e world simulation, não procedural text sem controle de qualidade.

---

# Integração com os mods do pack

## Regra geral

Mods jogáveis entram como providers, contextos, soluções, consequências ou conteúdo. Bibliotecas, performance, render/compat e tooltips não precisam receber lore própria.

## Provider-native first

- MineColonies: consultar cidadãos/colônia/raids/happiness quando APIs reais existirem; não duplicar sua simulação física.
- Volcanoes: usar Atmosphere/geologia/pressão/tectônica reais, sem transformar hazards em flags inventadas.
- Enshrouded: usar Shroud/Exposure/Flame/Story boundaries reais e manter separação de Black Arcana.
- Black Arcana: usar cast/danger/corruption/strain apenas pelos boundaries reais.
- Ars/Iron's/Goety/Malum/Eidolon/Vampirism/etc.: adaptar eventos e progressões sem substituir suas autoridades nativas.
- RPG Skill Tree: usar APIs canônicas de progressão existentes para condições; mutations passam pelos services canônicos.

## Taxonomia de integration events

Cada adapter declara:

- provider/version;
- event/query real;
- causal actor;
- dedup key;
- narrative event emitted;
- payload permitido;
- fallback;
- fail-closed behavior;
- se é discovery, knowledge, consequence, choice context ou progression gate.

---

# Data-driven authoring

Conteúdo narrativo deve ser externalizável por datapack/data files quando possível:

- facts schema;
- event definitions;
- actors/NPC identities;
- factions;
- ideologies;
- institutions;
- laws;
- settlement profiles;
- relationships initial state;
- consequence definitions;
- quest/beat definitions;
- dialogue condition exports;
- epilogue fragments;
- localization PT-BR.

Java implementa engine, invariants, persistence, adapters e complex logic; conteúdo específico não deve exigir recompilar o mod.

---

# Segurança, performance e multiplayer

- server-authoritative;
- SavedData/attachments versionados conforme scope;
- nenhum scan global por tick;
- evaluation reativa por eventos e índices;
- propagação social bounded por budget;
- scheduler heap/indexed, não varredura de todas consequências a cada tick;
- multiplayer define ownership/scope para decisões pessoais vs settlement/world;
- reconnect/restart mantém timers e one-shot semantics;
- migrations explícitas;
- reload inválido preserva snapshot anterior;
- diagnóstico de condições e rotas para authoring;
- dedicated-server obrigatório.

---

# Sequência de implementação

Os subplanos posteriores detalham esta ordem:

1. domínio, invariantes e authority;
2. event ledger/chronology;
3. knowledge/secrets/witnesses/evidence;
4. NPC memory/relationships;
5. factions/ideologies/institutions;
6. settlements/governance/laws/public opinion;
7. social propagation/unrest;
8. choice/consequence engine;
9. Easy NPC adapter;
10. FTB Quests journal adapter;
11. MineColonies adapter;
12. KubeJS authoring adapter;
13. provider integration taxonomy;
14. campaign/eras/epilogues;
15. schemas/localization/content pack;
16. sync/UI/diagnostics;
17. migrations/performance/GameTests/dedicated-server.

## Definition of Done do Stage narrativo

A implementação só pode ser chamada de pronta quando o cenário Severin e pelo menos uma cadeia macro de campanha provarem:

- rotas SIM/NÃO/ANTES/DEPOIS;
- conhecimento não onisciente;
- testemunha e propagação;
- decisão social diferente em dois governos distintos;
- NPC morto sem soft-lock;
- objetivo pré-resolvido sem duplicar reward;
- consequência após restart;
- FTB Quests refletindo estado sem ser authority;
- Easy NPC mostrando diálogo condicionado pelo Core;
- MineColonies ausente não quebrando o jar;
- KubeJS ausente não quebrando o jar;
- idempotência e migrations;
- multiplayer e dedicated-server verdes.
