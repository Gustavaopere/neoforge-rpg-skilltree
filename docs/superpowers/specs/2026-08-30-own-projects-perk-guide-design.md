# Design — Guia canônico dos projetos próprios para auditoria de perks

Data: 2026-08-30

## Objetivo

Garantir que o Chat 1 — Auditoria, Design e Integração das Perks consiga identificar corretamente, sem inferência improvisada, quais sistemas de `neoforge-rpg-skilltree`, `Volcanoes`, `Enshrouded` e `Black-Arcana` podem ou não ser usados por perks.

O problema atual não é falta de informação nos repositórios; é dispersão. Os detalhes estão nos `plans/`, no código, em `STATUS.md`, em contratos de integração e em evidências de CI. Copiar descrições integrais para os três guias existentes criaria três fontes redundantes e sujeitas a divergência.

## Decisão arquitetural

Criar uma quarta coleção canônica dentro de `plans/03-skill-tree-perks/guides/`, chamada `projects/`, e manter os três guias existentes como recortes por eixo.

Estrutura proposta:

```text
plans/03-skill-tree-perks/guides/
├── gameplay/
├── magic/
├── technology/
└── projects/
    ├── README.md
    ├── 01-rpg-skill-tree.md
    ├── 02-volcanoes.md
    ├── 03-enshrouded.md
    ├── 04-black-arcana.md
    └── 05-cross-project-integration-matrix.md
```

Os quatro dossiês em `projects/` são a fonte única para descrever o que cada projeto próprio faz. Os guias gameplay/magic/technology não duplicam essa descrição; recebem apenas um apêndice final de consumo, com os sistemas relevantes daquele eixo e links explícitos para o dossiê canônico.

## Por que esta solução é preferível

1. Evita três cópias divergentes da mesma mecânica.
2. Permite documentar cada projeto por completo, inclusive sistemas que atravessam gameplay, magia e tecnologia.
3. Permite que o Chat 1 leia uma fonte única para decidir provider, integração, gate e disponibilidade técnica.
4. Mantém os três guias úteis como classificação temática, sem transformá-los em catálogos duplicados.
5. Torna atualização futura simples: quando um projeto muda, atualiza-se um dossiê canônico e, se necessário, apenas o mapa de relevância dos três guias.

## Contrato obrigatório de status

Toda mecânica ou integração dos quatro dossiês recebe um estado explícito. Estados permitidos:

- `IMPLEMENTADO E CANÔNICO`: presente em `main`, com contrato/runtime real e evidência suficiente para ser tratado como provider disponível.
- `IMPLEMENTADO PARCIALMENTE`: parte do contrato está em `main`, mas existem componentes pendentes; o dossiê deve dizer exatamente quais.
- `PREPARATÓRIO / NÃO CANÔNICO`: existe trabalho de branch/plano/protótipo, mas o Chat 1 não pode tratá-lo como provider disponível.
- `PLANEJADO`: existe apenas como plano/contrato futuro.
- `BLOQUEADO / FAIL-CLOSED`: existe intenção ou boundary, mas não há hook/API segura para conceder o efeito.
- `NÃO APLICÁVEL`: o projeto não deve ser integrado naquele eixo sem um novo contrato explícito.

Nenhum item `PLANEJADO`, `PREPARATÓRIO / NÃO CANÔNICO` ou `BLOQUEADO / FAIL-CLOSED` pode ser apresentado como capacidade runtime disponível.

## Conteúdo obrigatório por projeto

Cada um dos quatro dossiês deve conter:

1. identidade e papel do projeto no modpack;
2. versão/plataforma alvo;
3. mapa completo de subsistemas;
4. autoridade de cada subsistema;
5. estado real de cada subsistema;
6. contratos públicos ou boundaries relevantes;
7. recursos e estados que perks podem consultar ou modificar;
8. eventos/hooks/provenance utilizáveis quando confirmados;
9. integrações já existentes com mods externos e com os outros três projetos;
10. regras de ownership para impedir duplicação de autoridade;
11. fallbacks e fail-closed reais;
12. pontos explicitamente proibidos para perks;
13. oportunidades de perks por categoria;
14. riscos de anti-abuse, double-processing e mastery farming;
15. testes/evidências relevantes;
16. fontes exatas no repositório (`plans/`, código, status, PR/CI quando necessário);
17. lacunas ainda não verificadas.

O texto deve distinguir sempre `fonte confirmada`, `inferência de integração` e `proposta futura`. Inferências não podem ser promovidas silenciosamente a fatos.

## Escopo dos quatro dossiês

### RPG Skill Tree

Cobrir pelo menos:

- estado canônico de jogador;
- level, XP, Core Progression Points e atributos;
- perks, árvores, classes emergentes e masteries;
- serviços canônicos de progressão e idempotência;
- modificadores e recomputação sem drift;
- combate melee/projectile/magic/heal/summon;
- world scaling, territory/area level, entity level, rarity, archetypes e effective stats;
- adapters de Epic Fight, Iron's, Ars, Goety/Malum/Eidolon, Apothic Attributes e tecnologia;
- quest/progression hooks;
- itemização universal, ranks, Item Power, prefix/suffix/infix e integração com equipamento;
- Compêndio Natural e cartografia somente onde expõem progressão/hook relevante;
- corpos/clones/identidades, incluindo construção tecnológica e transmigração, marcando claramente o que ainda é planejado.

### Volcanoes

Cobrir pelo menos:

- geologia/estratigrafia;
- depósitos/prospecção;
- tectônica, stress e terremotos;
- sites vulcânicos e câmaras magmáticas;
- lava, erupções, ash, bombs e pyroclastics;
- geotermia, hot springs, geysers, fumaroles e depósitos hidrotermais;
- `AtmosphereState`, gases, particulados, respiração, oxigênio e poluição;
- pressão atmosférica e hidrostática;
- ambientes selados;
- capacidades modulares de proteção/equipamento;
- integrações com Create, Aeronautics/Sable, Cold Sweat, Destroy, RNS, worldgen e MineColonies;
- diferenças entre o que o Volcanoes é autoridade e o que permanece autoridade do mod host.

### Enshrouded

Cobrir pelo menos:

- Shroud state, cores, expansão e queries;
- corrupção/materialização de terreno e purificação;
- exposure reserve;
- madness;
- Deadly/Red Shroud e Red Sludge;
- corrupted ecology, hostility buffs e magic resistance;
- Flame level, passage level, altar, ritual e Sanctuary;
- Lich/story contracts;
- client presentation apenas como não-autoritativa;
- integrações mágicas e de claims/teams/JourneyMap/necromancy flavor;
- separação explícita entre Shroud/Flame e sistemas de corrupção de outros projetos.

### Black Arcana

Cobrir pelo menos:

- cast request/execution;
- resource-cost providers;
- targeting/effect runtime;
- cooldown/persistence;
- integração Iron's/Ars/Eidolon/Malum/RPG;
- world-effect safety, rollback, budgets e PvP/boss protection;
- casting UX como camada não-autoritativa;
- Arcane Danger tiers e hazard sessions;
- Arcane Resistance;
- Corruption Resistance;
- Arcane Strain;
- Arcane Backlash e causal provenance;
- equipment/Curios e public API conforme estado real;
- spell profiles e domínios mágicos;
- rituals;
- progression/knowledge/mastery gates/balance caps;
- integração com o RPG sem feedback loop de mastery/backlash.

## Matriz cruzada

`05-cross-project-integration-matrix.md` deve registrar relações entre os quatro projetos. Cada linha possui:

- produtor/authority;
- consumidor;
- recurso/estado/evento;
- direção da integração;
- status real;
- hook/API/contract;
- fallback;
- fail-closed;
- impacto potencial em perks;
- risco de dupla autoridade.

Exemplos de relações obrigatórias a auditar:

- RPG -> Black Arcana: providers de Arcane/Corruption Resistance e gates/mastery;
- Enshrouded -> RPG: Flame/Shroud/progression gates somente por boundary público, quando existir;
- Volcanoes -> RPG: ambiente, calor, atmosfera, pressão, hazards e marcos discretos, sem mastery por tick;
- Black Arcana -> RPG: cast/hazard/damage provenance sem backlash alimentar mastery;
- Black Arcana <-> Enshrouded: corrupção permanece semanticamente separada até bridge explícita;
- Volcanoes <-> Enshrouded: nenhum acoplamento implícito entre atmosfera/Shroud sem contrato explícito.

## Apêndices dos três guias

Adicionar um último arquivo em cada coleção existente:

- Gameplay: `14-projetos-proprios.md` ou próximo número real disponível.
- Magic: `18-projetos-proprios.md` ou próximo número real disponível.
- Technology: `20-projetos-proprios.md` ou próximo número real disponível.

A numeração deve ser calculada a partir do estado real do diretório, não presumida.

Cada apêndice deve:

- declarar que os dossiês de `../projects/` são canônicos;
- listar apenas os subsistemas pertinentes ao eixo;
- apontar o estado e o dossiê de origem;
- registrar o que o Chat 1 deve considerar ao auditar perks daquele eixo;
- proibir inferir hooks a partir de nomes, planos ou namespaces.

## Notion

No Notion, a mesma arquitetura deve ser preservada conceitualmente:

- criar uma página canônica `Guia Completo — Projetos Próprios do Modpack`;
- criar quatro subpáginas/dossiês e uma matriz cruzada;
- acrescentar ao final dos três guias atuais um bloco `Projetos próprios do modpack` que referencia essa página e contém o recorte daquele eixo;
- manter Notion como fonte editorial canônica, com GitHub como snapshot operacional versionado;
- após cada escrita, realizar re-fetch e validar persistência/conteúdo.

Se o conector disponível não permitir a hierarquia exata, preservar a relação através de links explícitos e IDs de página; não duplicar o conteúdo integral.

## Alteração do protocolo do Chat 1

O protocolo operacional do Chat 1 deve ser atualizado para exigir, antes de fechar qualquer perk:

1. critérios obrigatórios;
2. gameplay completo;
3. magic completo;
4. technology completo;
5. `guides/projects/` completo;
6. matriz cruzada dos quatro projetos;
7. dossiê individual da perk;
8. provider/API real da versão usada, quando aplicável.

O Chat 1 não pode considerar um dos quatro projetos “coberto” apenas porque apareceu em um dos três guias temáticos.

## Regra para perks

Para cada perk, o Chat 1 deve classificar qualquer relação com os quatro projetos em uma destas categorias:

- provider principal;
- provider secundário/bridge;
- gate/dependência;
- recurso consumido;
- recurso produzido;
- progressão/mastery;
- ambiente/hazard;
- equipamento/itemização;
- apenas leitura;
- não aplicável;
- bloqueado/fail-closed.

A perk só pode usar um recurso como disponível quando o dossiê do projeto o marca como `IMPLEMENTADO E CANÔNICO` ou quando o contrato da própria perk explicita corretamente uma dependência futura/bloqueada sem fingir runtime presente.

## Verificação

A implementação documental deverá ser validada por:

- comparação de cada dossiê com `plans/STATUS.md` do projeto correspondente;
- leitura dos arquivos de plano por subsistema, não apenas README;
- inspeção do código/contratos onde a disponibilidade de hook não puder ser provada apenas pelo plano;
- checagem de links e ausência de referências quebradas;
- busca por estados contraditórios (`complete` vs `planned`, etc.);
- re-fetch do Notion após writes;
- diff review no GitHub;
- CI/reviews do PR;
- merge e confirmação da `main`.

## Critério de conclusão

O trabalho está concluído quando:

- os quatro projetos possuem dossiês completos e rastreáveis;
- a matriz cruzada existe;
- os três guias apontam para a fonte canônica e contêm recorte correto por eixo;
- o protocolo do Chat 1 exige leitura dessa nova fonte;
- Notion e GitHub representam a mesma arquitetura editorial;
- não há mecânica planejada apresentada como implementada;
- não há integração inventada;
- o PR está verde, revisado e mergeado;
- a `main` pós-merge é confirmada.
