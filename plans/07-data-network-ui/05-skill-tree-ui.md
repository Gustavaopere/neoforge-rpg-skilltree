# 07.05 — Skill Tree UI — Cosmograma Unificado, Constelações de Classe e Árvores Simbólicas

> **Status:** PLANEJADO / ABERTO — design-alvo completo; execução bloqueada pelos gates desta seção.  
> **Baseline auditado para este plano:** `main@4d7aa9577571ee302c44b3384a984ffda4c742ae`.  
> **Plataforma:** Minecraft 1.21.1, NeoForge 21.1.x, Java 21.  
> **Escopo:** UI/layout/navegação. Este plano não cria gameplay por estética.

## 0. Gates arquiteturais que vêm ANTES da implementação completa

Este arquivo registra o **design final desejado** da interface para que a visão não se perca, mas ele **não autoriza pular o `docs/MASTER_PLAN.md` nem as ADRs abertas**.

### 0.1 D001 é bloqueante

`docs/decisions/README.md` mantém **D001 — Tree engine: Passive Skill Tree versus permanent custom UI — OPEN** e o `docs/MASTER_PLAN.md` exige um vertical slice antes de migrar a experiência inteira de 512 nodes.

Portanto a ordem normativa é:

1. concluir os pré-requisitos de autoridade server→client exigidos pelas fases anteriores do Master Plan;
2. construir **um único vertical slice comparável**:
   - main tree;
   - um gateway real;
   - uma subtree real;
   - purchase;
   - respec;
   - rule-revision sync;
   - multiplayer/server authority;
3. avaliar, para NeoForge 1.21.1, o candidato Passive Skill Tree disponível versus custom UI em:
   - API;
   - licença;
   - capacidade de layout customizado;
   - sync;
   - maintainability;
   - performance;
   - UX;
4. registrar ADR resolvendo D001;
5. **só depois** executar a implementação integral descrita neste arquivo.

Se D001 selecionar uma implementação Passive Skill Tree extensível, o cosmograma deste plano deve ser implementado **sobre a API/extensões que a engine realmente permitir**. Se D001 selecionar custom UI, a arquitetura cliente descrita nas seções posteriores vira a direção permanente.

O design visual é canônico como **alvo de produto**; a escolha da engine continua condicionada à D001.

### 0.2 Não migrar IDs incidentalmente neste Stage

As definições atuais usam strings como:

- `class_id: "technomancer"`;
- `specialization_id: "irons_fire"`.

Os loaders atuais preservam essas identidades. Portanto `ClassVisualDefinition.classId` e `SpecializationVisualDefinition.specializationId` devem usar **exatamente a representação de ID projetada pelo snapshot server-side daquela revisão**.

No baseline atual, os exemplos corretos são `technomancer` e `irons_fire`, sem prefixar `rpgskilltree:` por conta própria.

IDs dos **assets puramente visuais** podem e devem ser namespaced, por exemplo `rpgskilltree:flame`.

Se uma fase/ADR posterior migrar class/specialization IDs para `ResourceLocation`, essa migração deve acontecer primeiro no contrato persistido/server/network, com aliases e fixtures. 07.05 então apenas passa a consumir a nova projeção; a UI não inaugura a migração sozinha.

### 0.3 Árvore 3 exige topologia real

As 25 definições atuais em `specializations/*.json` descrevem identidade, provider, mastery/gateway/eligibility, mas **não constituem 25 grafos compráveis de nodes/edges**.

Logo existem dois estados visuais diferentes:

- **especialização sem subtree materializada:** pode possuir emblema/silhueta temática, gateway, status, tooltip e bridges visuais, mas NÃO fake nodes;
- **especialização com subtree server-authoritative materializada:** seus nodes/edges reais podem ser distribuídos pelo template para formar o símbolo.

A meta do usuário — “a própria árvore da especialização formar uma chama, gota, pentagrama etc.” — continua sendo o **resultado final obrigatório**, porém a UI só pode alcançá-lo depois que a fase de gameplay adequada materializar a topologia real daquela especialização.

Este plano define a geometria e o contrato de consumo; **não inventa a topologia**.

---

## 1. Objetivo de produto

Transformar a progressão do RPG Skill Tree em um **único cosmograma navegável**, legível do macro ao micro:

1. **Árvore 1 — Atributos** no núcleo central;
2. **Árvore 2 — Perks Principais** como corpo radial intermediário com os 11 domínios canônicos;
3. **Classes emergentes** como constelações/âncoras na periferia da Árvore 2;
4. **Árvore 3 — Especialistas** nas extremidades;
5. quando uma especialização possuir subtree real, seus próprios nodes e edges devem compor uma silhueta temática reconhecível;
6. quando ainda não possuir subtree, mostrar apenas o emblema/gateway/status real, sem fingir nodes;
7. classes, multiclass, ranks, Mastery, provider availability, purchase e respec permanecem server-authoritative.

Em zoom distante o jogador lê **formas e territórios**. Em zoom médio lê **classes, gateways e especializações**. Em zoom próximo lê **nodes, ranks, custos, requisitos e tooltips**.

---

## 2. Fontes de verdade e precedência

A implementação deve obedecer, nesta ordem:

1. snapshot/regras server-authoritative da revisão atual;
2. class definitions e specialization definitions realmente publicadas pelo servidor;
3. grafos reais de main tree/class subtrees/specialist subtrees;
4. `plans/04-classes-masteries-specializations/` e contratos já fechados;
5. `docs/MASTER_PLAN.md`, ADRs e `AGENTS.md` para ordem de execução/invariantes;
6. Documento Mestre no Notion para design-alvo ainda não materializado;
7. este arquivo para composição visual/layout/UX.

### 2.1 Modlist atual

A modlist anexada em 2026-09-06 registra **607 mods**, sendo mais recente que os snapshots de 2026-08-30 dos guias consolidados.

Consequências:

- nenhuma disponibilidade de provider é hardcoded na UI a partir dos guias;
- versão/presença/capability vêm da projeção server-side;
- drift de modlist não exige redesenho quando IDs canônicos permanecem estáveis;
- provider ausente/incompatível reflete o fail-closed do runtime.

---

## 3. Estado real do baseline

### 3.1 Main tree

A arquitetura atual possui 11 domínios:

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT`, `LOGISTICS`.

O contrato existente valida uma main tree de **512 nodes**. 07.05 não redesenha essa topologia para caber numa figura bonita.

### 3.2 Classes reais

Existem 23 class definitions na `main` do baseline:

1. Arcanist
2. Beastmaster
3. Cleric
4. Druid
5. Duelist
6. Engineer
7. Geomancer
8. Guardian
9. Mage
10. Metamorph
11. Miner
12. Necromancer
13. Occultist
14. Paladin
15. Priest
16. Rogue
17. Sorcerer
18. Spellblade
19. Summoner
20. Survivor
21. Technomancer
22. Warlock
23. Warrior

O design consolidado ainda prevê:

24. Ranger/Hunter — planejada;
25. Death Knight — planejada/condicional.

Essas duas só aparecem como classe interativa quando existirem no catálogo server-side.

### 3.3 Taxonomia que NÃO deve voltar a ser classe por causa da UI

- Industrialist → lane/especialização;
- Prospector → lane/especialização;
- Logistician → lane/especialização.

`Artificer` não é contado nas 25 acima porque D014 continua aberta. 07.05 deve tratar Artificer como **sem binding de classe** até a ADR decidir se é classe, especialização, gateway/provider identity ou artefato de compatibilidade.

### 3.4 Especializações atuais

Existem 25 specialization definitions:

`ae2_networks`, `ars_amplification`, `ars_aoe`, `ars_control`, `ars_duration`, `ars_projectile`, `ars_summoning`, `create_aeronautics`, `create_artillery`, `create_automation`, `create_kinetics`, `epic_heavy`, `epic_ranged`, `epic_sword`, `irons_blood`, `irons_eldritch`, `irons_ender`, `irons_evocation`, `irons_fire`, `irons_holy`, `irons_ice`, `irons_lightning`, `irons_nature`, `oritech_mining`, `oritech_power`.

No baseline, essas definições **não equivalem a 25 subtrees compráveis**. Esta distinção é obrigatória em todo o renderer.

### 3.5 Subtrees de classe materializadas

O contrato atual já materializa subtrees dedicadas para:

- Technomancer — 17 nodes;
- Warlock — 18 nodes;
- Druid — 11 nodes;
- Metamorph — 10 nodes.

Essas podem ser o primeiro material real para testar layout simbólico sem inventar gameplay.

---

## 4. Composição espacial final

### 4.1 Bandas

```text
R0  Núcleo                  Árvore 1 — Atributos
R1  Corpo radial            Árvore 2 — Perks Principais / 11 domínios
R2  Cinturão de classes     Constelações de classes emergentes
R3  Coroa externa           Árvore 3 — Especialistas
```

Essas bandas são apresentação de um estado único, não quatro progressões concorrentes.

### 4.2 Identidade única

Regra:

```text
1 canonical gameplay id = 1 interactive visual instance
```

É proibido duplicar uma especialização porque várias classes apontam para ela. Múltiplas classes usam bridges até o mesmo glifo.

### 4.3 Posição de classe

A posição da classe é calculada **a partir de `required_completed_domains` e demais metadados server-projected da definição atual**, não a partir do texto temático deste documento.

A tabela de classes abaixo descreve a fantasia visual. Em caso de drift, o catálogo server-side sempre vence.

Classes de domínio único ficam próximas ao arco daquele domínio. Híbridas ficam no bissetor/centróide dos domínios realmente exigidos. O solver pode mudar raio/ângulo fino para evitar colisão, sem sugerir requisitos falsos.

---

## 5. Gramática dos nodes

| Função real | Forma visual | Regra |
|---|---|---|
| Small/path | círculo pequeno | caminho legível |
| Ranked Passive | círculo com anéis | anéis refletem ranks reais |
| Bridge | elo/losango | não cria atalho |
| Gateway | hexágono/portal | entrada real |
| Notable | medalhão | maior saliência |
| Keystone | runa/polígono | regra relevante |
| Capstone | emblema grande | final real da subtree |

A informação crítica nunca depende apenas de cor.

---

## 6. Catálogo visual COMPLETO de classes

Todos os posicionamentos finais são derivados do servidor. “Vizinhança temática” abaixo serve apenas à forma macro e ao roteamento visual.

### 6.1 Warrior

- Baseline: presente.
- Forma macro: **espada larga / chevron de avanço**.
- Vizinhança temática: Martial.
- Specialist links: qualquer arma/especialização realmente elegível; `epic_sword`/`epic_heavy` são candidatos naturais, mas o renderer não inventa o gate.
- Nodes reais sempre preservam topologia/custo.

### 6.2 Guardian

- Baseline: presente.
- Forma: **escudo segmentado**.
- Fantasia: defesa física, estabilidade e Vitality/Martial conforme definição real.
- Notables podem ocupar a borda; Keystone/Capstone podem ocupar o centro somente quando a topologia disser isso.

### 6.3 Duelist

- Baseline: presente.
- Forma: **duas lâminas cruzadas**.
- Fantasia: Martial + Agility.
- O cruzamento visual representa confluência, não um node gratuito.

### 6.4 Rogue

- Baseline: presente.
- Forma: **adaga / losango quebrado**.
- Fantasia: mobilidade, precisão, oportunidade.
- Ender/ranged só conectam se o servidor autorizar.

### 6.5 Ranger/Hunter

- Baseline: planejada, ausente do catálogo.
- Forma-alvo: **arco tensionado com flecha**.
- Fantasia consolidada: Agility + Survival.
- `epic_ranged` pode ser satélite quando a classe existir e o gate real permitir.
- Antes disso, não existe área comprável para a classe.

### 6.6 Arcanist

- Baseline: presente.
- Forma: **círculo arcano concêntrico**.
- Função visual: hub para caminhos mágicos sem duplicar escolas.
- Schools/glyph semantics ficam em glifos próprios.

### 6.7 Mage

- Baseline: presente.
- Forma: **estrela arcana multifacetada**.
- Pontas são lanes visuais, não número fixo de escolas/providers.

### 6.8 Sorcerer

- Baseline: presente.
- Forma: **espiral/cometa**.
- `ars_amplification`, `ars_aoe` e escolas ofensivas podem orbitar somente se elegíveis.
- A espiral não cria carga/recurso novo.

### 6.9 Priest

- Baseline: presente.
- Forma: **halo radiante**.
- Holy/support aparecem por gates reais.
- `irons_holy` é compartilhável, não propriedade exclusiva de Priest.

### 6.10 Cleric

- Baseline: presente.
- Forma: **selo sagrado / cálice geométrico**.
- Bridges com Priest/Paladin representam relações reais, nunca atalhos novos.

### 6.11 Paladin

- Baseline: presente.
- Forma: **escudo solar com lâmina central**.
- Pode ligar Martial e Holy quando o snapshot permitir.
- Resistência física, Holy e healing continuam semanticamente distintos.

### 6.12 Engineer

- Baseline: presente.
- Forma: **engrenagem com trilhas de circuito**.
- Satélites possíveis conforme gates reais: Create, AE2, Oritech e outros specialist IDs existentes.
- Provider presente não equivale a adapter disponível.

### 6.13 Miner

- Baseline: presente.
- Forma: **picareta/cristal estratificado**.
- `oritech_mining` pode conectar quando elegível.
- Prospector continua lane/especialização.
- Nenhum scanner/geologia é inferido visualmente.

### 6.14 Survivor

- Baseline: presente.
- Forma: **bússola/fogueira em rosa dos ventos**.
- Setores podem representar lanes reais de temperatura/sede/nutrição/exploração quando existirem.
- UI nunca cria segundo estado corporal.

### 6.15 Summoner

- Baseline: presente.
- Forma: **círculo de invocação com órbitas**.
- Provider-agnostic.
- `ars_summoning` pode conectar quando elegível.
- Uma órbita não implica slot real de summon.

### 6.16 Beastmaster

- Baseline: presente.
- **Definição atual verificada:** `required_completed_domains = [AGILITY, SUMMONING]`.
- Forma: **pata/garra formada por clusters**.
- A posição final deve ser derivada dessas exigências sincronizadas; a associação temática antiga com Survival não pode deslocar a classe para um setor que contradiga o servidor.
- Companion animal não vira spell summon por semelhança estética.

### 6.17 Druid

- Baseline: presente; subtree real de 11 nodes.
- Forma: **árvore/folha ramificada**.
- É candidata prioritária ao vertical slice de shape fitting se a engine escolhida suportar o mesmo contrato.
- Wild Shape/permissões continuam server-authoritative.

### 6.18 Occultist

- Baseline: presente.
- Forma: **pentagrama/círculo ritual multi-canal**.
- Blood/Soul/Spirit/Eldritch permanecem recursos/semânticas distintos.
- O centro do pentagrama é landmark visual, não ritual executável.

### 6.19 Warlock

- Baseline: presente; subtree real de 18 nodes.
- Forma: **selo de pacto de cinco vértices**.
- Os cinco braços podem receber as escolhas de pacto reais, preservando exclusividade/capacidade do servidor.
- Blood/Eldritch podem ficar como satélites se elegíveis.

### 6.20 Necromancer

- Baseline: presente.
- Forma: **crânio/ossuário com coroa de minions**.
- Ars/Goety/Mobstein/Malum/Black Arcana ou outros providers só se relacionam quando existe boundary/gate real.
- Sem copropriedade automática por tema “necromancia”.

### 6.21 Spellblade

- Baseline: presente.
- Forma: **espada atravessando círculo arcano**.
- Identidade híbrida Martial + Arcane conforme definição real.
- Proc/spell/melee continua deduplicado no runtime; UI não simula autoridade.

### 6.22 Technomancer

- Baseline: presente; subtree real de 17 nodes.
- Definição atual verificada: `ARCANE + ENGINEERING`.
- Forma: **triskelion/triângulo técnico**.
- Os três braços podem refletir os gateways reais Create Kinetics, AE2 Networks e Oritech Power.
- `triune_core` permanece no landmark apropriado somente enquanto for o capstone real.

### 6.23 Geomancer

- Baseline: presente.
- Forma: **montanha/cristal facetado**.
- Arcane/Mining/Nature/geologia só conectam conforme a definição e providers reais.
- Não fundir Volcanoes, RNS e Oritech em uma authority fictícia.

### 6.24 Metamorph

- Baseline: presente; subtree real de 10 nodes.
- Forma: **máscara dupla / hélice de transformação**.
- Lobos podem separar categorias de forma reais quando o servidor as expuser.
- Identity2/morph unlock e blacklist continuam server-authoritative.

### 6.25 Death Knight

- Baseline: planejada/condicional, ausente do catálogo.
- Forma-alvo: **espada negra atravessando coroa/crânio quebrado**.
- Fantasia consolidada: Martial + Occult.
- Blood/Eldritch só conectam após definição/gates reais.
- Sem classe interativa antes disso.

### 6.26 Artificer — decisão aberta, não 26ª classe automática

- D014 continua OPEN.
- Não criar `class_visuals/artificer.json` enquanto a ADR não promover Artificer a classe.
- Se virar especialização/gateway, usar o catálogo correspondente.
- Se virar classe, adicionar visual e teste de cobertura no mesmo commit que materializar a definição server-side.

---

## 7. Mapeamento visual das 25 especializações atuais

Todos os 25 IDs atuais recebem **emblema visual dedicado**. Node-shaped glyph só é ativado quando o snapshot fornecer topologia comprável daquela especialização.

| specialization_id | shape asset | leitura visual |
|---|---|---|
| `ae2_networks` | `rpgskilltree:network_hex` | circuito/hexágono |
| `ars_amplification` | `rpgskilltree:amplification_star` | estrela crescente |
| `ars_aoe` | `rpgskilltree:concentric_rings` | anéis de área |
| `ars_control` | `rpgskilltree:control_knot` | nó/runa de contenção |
| `ars_duration` | `rpgskilltree:hourglass_loop` | ampulheta/loop |
| `ars_projectile` | `rpgskilltree:arcane_projectile` | seta/projétil |
| `ars_summoning` | `rpgskilltree:summoning_circle` | círculo de invocação |
| `create_aeronautics` | `rpgskilltree:wing_propeller` | asa/hélice |
| `create_artillery` | `rpgskilltree:artillery_reticle` | mira/canhão |
| `create_automation` | `rpgskilltree:automation_loop` | linha de montagem |
| `create_kinetics` | `rpgskilltree:gear` | engrenagem |
| `epic_heavy` | `rpgskilltree:heavy_hammer` | martelo/anvil |
| `epic_ranged` | `rpgskilltree:bow_arrow` | arco e flecha |
| `epic_sword` | `rpgskilltree:sword_blade` | lâmina |
| `irons_blood` | `rpgskilltree:blood_drop` | gota de sangue / Hemomancia |
| `irons_eldritch` | `rpgskilltree:eldritch_pentagram` | pentagrama/estrela abissal |
| `irons_ender` | `rpgskilltree:ender_eye_portal` | olho/portal |
| `irons_evocation` | `rpgskilltree:evocation_burst` | explosão/runa radial |
| `irons_fire` | `rpgskilltree:flame` | chama/fagulha / Piromancia |
| `irons_holy` | `rpgskilltree:holy_halo` | halo/selo solar |
| `irons_ice` | `rpgskilltree:snowflake` | floco de neve |
| `irons_lightning` | `rpgskilltree:lightning_bolt` | raio |
| `irons_nature` | `rpgskilltree:leaf_branch` | folha/ramo |
| `oritech_mining` | `rpgskilltree:mining_crystal` | cristal/picareta |
| `oritech_power` | `rpgskilltree:power_coil` | bobina/raio industrial |

### 7.1 Aquamancia e outros templates futuros

O engine visual pode incluir shape templates sem binding ativo, por exemplo:

- `rpgskilltree:water_drop` — futura fantasia de água/Aquamancia;
- `rpgskilltree:wind_spiral` — vento;
- `rpgskilltree:skull_ossuary` — necromancia;
- `rpgskilltree:shield` — defesa;
- `rpgskilltree:claw` — Beastmaster;
- `rpgskilltree:mask` — Metamorph.

Regra: **shape template não cria specialization definition**. Enquanto não houver ID canônico, não há gateway, tooltip de progressão, points nem interação.

---

## 8. Pré-requisito server-side para a Árvore 3 completa

A experiência final de “nodes formando a figura” depende de topologia real. Essa topologia pertence à fase de gameplay/classes/masteries/specializations, não ao renderer.

Para cada especialização que ganhar árvore própria, o snapshot server-side deve fornecer no mínimo:

- `specialization_id` estável;
- `tree_id` estável;
- root/gateway;
- node IDs;
- edges/requisitos;
- ranks/custos/moeda;
- node roles;
- effects/gates;
- respec semantics;
- provenance;
- provider availability;
- revision.

A UI consome isso read-only.

### 8.1 Estado de fallback enquanto a topologia não existe

```text
specialization definition exists
        ↓
visual emblem + gateway/status + requirements
        ↓
NO fake passive nodes
```

### 8.2 Estado final quando a topologia existe

```text
specialization definition + specialist tree graph
        ↓
ShapeTemplate fitter
        ↓
real nodes/edges form flame/drop/pentagram/etc.
```

Assim o plano preserva a ambição visual sem violar a regra “não criar perk para preencher desenho”.

---

## 9. ShapeTemplate

Cada template puramente visual define:

- namespaced visual `id`;
- outline normalizado;
- skeleton lanes;
- landmarks `gateway`, `hub`, `notable`, `keystone`, `capstone`, `branch_tip`;
- aspect ratio;
- preferred rotation;
- symmetry hints;
- hitbox margin;
- visual schema version.

Exemplo visual-only:

```json
{
  "schemaVersion": 1,
  "id": "rpgskilltree:flame",
  "aspectRatio": 0.72,
  "preferredRotationDeg": 0.0,
  "landmarks": {
    "gateway": [0.0, -1.0],
    "hub": [0.0, -0.05],
    "capstone": [0.0, 1.0]
  }
}
```

Landmarks não são nodes. Eles apenas recebem nodes reais quando o grafo tiver função compatível.

---

## 10. Bindings visuais e política de ID

Os metadados puramente visuais vivem em `assets`, não em datapack autoritativo.

Estrutura alvo se D001 selecionar custom UI ou uma engine que aceite esses assets:

```text
src/main/resources/assets/rpgskilltree/tree_ui/
├── shape_templates/
├── class_visuals/
├── specialization_visuals/
└── themes/
```

### 10.1 Class binding — baseline atual

```json
{
  "schemaVersion": 1,
  "classId": "technomancer",
  "shape": "rpgskilltree:technomancer_triskelion",
  "placementBand": "CLASS_BELT",
  "rotationPolicy": "RADIAL_OUTWARD"
}
```

### 10.2 Specialization binding — baseline atual

```json
{
  "schemaVersion": 1,
  "specializationId": "irons_fire",
  "shape": "rpgskilltree:flame",
  "placementBand": "SPECIALIST_CROWN",
  "rotationPolicy": "RADIAL_OUTWARD"
}
```

`classId`/`specializationId` são comparados contra a projeção server-side exata. `shape` é asset ID namespaced.

### 10.3 Proibição de campos de gameplay em assets

Arquivos de visual não podem definir:

- custo;
- rank;
- mastery necessária;
- required nodes;
- providerLoaded;
- adapterComplete;
- effect;
- currency;
- unlock.

---

## 11. Layout determinístico

### 11.1 Pipeline

```text
server-projected rules + player snapshot
              ↓
        SceneAssembler
              ↓
 canonical identities / graph / states
              ↓
   CosmogramPlacementResolver
              ↓
  Shape fitting when real graph exists
              ↓
          LayoutSnapshot
              ↓
            Renderer
```

### 11.2 Fases

1. reservar R0 para a representação de atributos;
2. importar/derivar o layout da main tree sem mudar topologia;
3. calcular anchors dos 11 domínios;
4. calcular class anchors a partir das exigências reais da classe;
5. resolver colisões macro de clusters sem alterar semântica;
6. colocar specializations na coroa externa;
7. se houver specialist subtree real, fazer shape fitting;
8. se não houver, renderizar emblema/gateway sem fake nodes;
9. rotear bridges visuais;
10. congelar `LayoutSnapshot`.

### 11.3 Determinismo

Mesma revisão de regras + mesmos assets = mesmas coordenadas.

Jitter estético, se existir, usa seed derivada de ID canônico + visual schema version. Nunca RNG por frame.

### 11.4 Shape fitting de grafo real

Ordem de landmark:

1. gateway real → `gateway`;
2. capstone real → `capstone`;
3. keystones/notables → landmarks de alta saliência;
4. branches/passives → skeleton lanes;
5. restantes → arc-length/topological order.

Se não couber, aumentar escala/spacing dentro dos bounds do cluster. Nunca remover node/edge.

---

## 12. Especializações compartilhadas

Uma specialization pode conectar várias classes.

Contrato:

```text
1 specialization_id
       ↓
1 visual glyph/emblem instance
       ↓
0..N visual links from eligible class/domain anchors
```

Exemplos temáticos possíveis, sempre condicionados ao resolver real:

- Holy perto de Priest/Cleric/Paladin;
- Nature perto de Druid/Beastmaster/Geomancer;
- Blood perto de Warlock/Necromancer/futuro Death Knight;
- ranged perto de Agility/futuro Ranger;
- artillery perto de Engineering/Martial tech paths.

Nenhuma relação temática cria gate.

---

## 13. Estados visuais

### 13.1 Node

- hidden somente quando a regra autoritativa permitir;
- visible locked;
- available;
- invested partial;
- invested max;
- provider unavailable;
- stale/reconciling apenas como estado transitório de UI.

### 13.2 Classe

- inexistente no catálogo → não interativa/não materializada;
- existente mas requisitos faltando → constelação apagada + motivos;
- elegível/satisfeita → contorno/símbolo ativo;
- multiclass → todas as identidades satisfeitas podem ficar ativas simultaneamente;
- respec → atualizar somente após novo snapshot.

### 13.3 Specialization

- definition absent;
- provider absent;
- adapter unavailable/incompatible;
- gateway locked;
- gateway available;
- specialization granted/active;
- subtree partially invested quando topologia existir;
- subtree complete quando topologia existir.

---

## 14. Zoom e LOD

### LOD 0 — cosmograma completo

- silhuetas macro;
- domínios;
- classes;
- emblemas de especializações;
- gateways/capstones mais importantes.

### LOD 1 — região

- cluster selecionado;
- notable/keystone/gateway;
- caminhos principais;
- requirements resumidos.

### LOD 2 — operacional

- todos os nodes realmente existentes no viewport;
- ranks/custos/conexões;
- hover/focus.

### LOD 3 — inspeção

- tooltip completa;
- provider/gate/fail-closed;
- dependentes de respec;
- ações de purchase/respec via protocolo server-side.

LOD não esconde uma exigência quando o usuário entra na inspeção do node.

---

## 15. Interação e autoridade

### 15.1 Purchase

```text
client selects canonical id
  ↓
C2S intent
  ↓
server revalidates current rules/state
  ↓
accept/reject
  ↓
new authoritative snapshot/revision
  ↓
UI animates confirmed result
```

Sem rank otimista persistido.

### 15.2 Respec

Preview pode ser local e explicitamente marcado como simulação. Confirmação/mutação fica no servidor.

### 15.3 Busca

Busca localiza canonical ID único e centraliza a instância visual correspondente. Não cria cópia temporária interativa.

### 15.4 Filtros

Domínio, classe, specialization, função, disponível, investido, bloqueado, provider e texto livre. Filtro reduz destaque, não altera regras.

### 15.5 Breadcrumbs

`Árvore → Domínio → Classe → Especialização → Node`, omitindo níveis que não existirem para aquele caminho.

---

## 16. Tooltips e explicabilidade

Quando disponível no snapshot/projeção, mostrar:

- nome PT-BR;
- descrição;
- rank atual/máximo;
- custo/moeda;
- efeito;
- required nodes/ranks;
- domínio/investment requirements;
- mastery;
- class/specialization requirement;
- provider state;
- motivo normalizado de bloqueio;
- dependentes afetados por respec.

“Adapter incompatível” pode ser traduzido para mensagem player-facing e detalhado em modo debug.

---

## 17. Acessibilidade

Obrigatório:

- PT-BR player-facing;
- forma/contorno além de cor;
- escala de UI;
- teclado/controle;
- focus ring;
- reduced motion;
- opção de reduzir/desligar partículas;
- contraste adequado;
- tooltip contida na tela;
- descrição textual de emblemas;
- zoom mínimo/máximo seguro.

---

## 18. Animações

Animação é derivada de estado confirmado.

Exemplos:

- Fire/flame: pulso de chama;
- Blood/drop: pulso lento;
- Ice/snowflake: brilho radial;
- Lightning: descarga breve na confirmação;
- Create kinetics/gear: rotação lenta;
- AE2/network: pulsos em trilhas;
- Eldritch: oscilação de runas.

Reduced motion substitui movimento por contraste/brilho estático.

---

## 19. Performance — medir antes de otimizar

`docs/MASTER_PLAN.md` determina que viewport culling/spatial indexes só devem ser adicionados se profiling justificar.

### 19.1 Baseline obrigatório ANTES das otimizações

O primeiro renderer funcional do vertical slice e depois da cena representativa deve começar com estruturas simples e mensuráveis.

Medir separadamente:

- scene assembly;
- layout rebuild;
- frame render;
- hit testing;
- tooltip/search;
- 512+ main nodes;
- main + class clusters + specialization emblems;
- diferentes UI scales/resoluções.

### 19.2 Otimizações condicionais

Somente se o profiling mostrar necessidade:

- coarse cluster culling;
- fine node culling;
- spatial index para hit testing;
- edge mesh/cache especializado;
- text/icon caches adicionais.

LOD semântico pode existir desde o início por UX, mas não justifica automaticamente uma estrutura de otimização complexa.

### 19.3 Invariantes

Mesmo antes do profiling:

- layout estrutural não deve ser recomputado deliberadamente a cada frame;
- não introduzir algoritmo O(N²) por frame sem justificativa;
- nenhuma otimização pode mudar estado de gameplay.

---

## 20. Fail-closed visual

### Template ausente

Usar `generic_constellation`; manter todos os nodes reais acessíveis; registrar diagnóstico.

### Binding para ID inexistente

Ignorar binding; não criar entidade fictícia.

### Specialization sem subtree

Mostrar emblema/gateway/status; não gerar fake nodes.

### Snapshot incompatível/stale

Bloquear mutação até resync/protocolo válido. Nunca assumir unlock.

### Resource reload visual inválido

Manter último catálogo visual válido quando houver; não afetar gameplay.

---

## 21. Arquitetura de código — somente após D001

Se D001 escolher custom UI, a divisão de responsabilidades recomendada é:

```text
src/main/java/dev/gustavopere/rpgskilltree/client/tree/
├── SkillTreeScreen.java
├── SkillTreeSceneAssembler.java
├── model/
│   ├── SkillTreeScene.java
│   ├── VisualNode.java
│   ├── VisualEdge.java
│   ├── VisualCluster.java
│   ├── VisualState.java
│   └── LayoutSnapshot.java
├── layout/
│   ├── CosmogramLayoutEngine.java
│   ├── DomainAnchorResolver.java
│   ├── ClassConstellationResolver.java
│   ├── SpecializationGlyphResolver.java
│   ├── SymbolicSubgraphLayouter.java
│   └── EdgeRouter.java
├── data/
│   ├── ShapeTemplate.java
│   ├── ShapeTemplateLoader.java
│   ├── ClassVisualDefinition.java
│   ├── SpecializationVisualDefinition.java
│   └── TreeUiVisualCatalog.java
├── render/
│   ├── TreeSceneRenderer.java
│   ├── EdgeRenderer.java
│   ├── NodeRenderer.java
│   ├── ClusterRenderer.java
│   └── LodPolicy.java
└── interaction/
    ├── TreeCamera.java
    ├── TreeHitTester.java
    ├── TreeSelectionModel.java
    ├── TreeSearchIndex.java
    └── TreeTooltipModel.java
```

Se D001 escolher uma external engine, essas **responsabilidades** continuam úteis, mas os arquivos/classes devem ser reduzidos/adaptados ao extension model real em vez de criar uma segunda UI paralela.

Client-only classes nunca podem classload no dedicated server.

---

## 22. Boundary de snapshot/rede

07.05 consome DTO/projeção da revisão server-side e não lê como authority:

- attachment mutável;
- SavedData interna;
- `ModList` para unlock;
- provider class interna;
- classpath data concorrente ao server snapshot.

A projeção de UI deve carregar, conforme definido pelas fases de network/sync:

- rules revision/hash;
- nodes/edges necessários;
- ranks/pontos;
- class state;
- specialization state/availability;
- mastery/requisitos necessários para explicação;
- blocking reasons.

Se Stage 07.03/07.04 já fornecer DTO equivalente, 07.05 deve reutilizá-lo.

---

## 23. Validadores

### 23.1 `ShapeTemplateValidator`

Rejeitar:

- duplicate visual ID;
- NaN/Infinity;
- aspect ratio <= 0;
- landmark inexistente;
- lane inválida;
- schemaVersion incompatível.

### 23.2 `VisualBindingValidator`

Garantir:

- class/specialization binding usa a forma de ID da projeção server-side daquela revisão;
- visual asset não contém regra de gameplay;
- Artificer não aparece como class binding enquanto D014 estiver aberta;
- Ranger/Hunter e Death Knight não viram gameplay definition por asset;
- template antecipatório sem binding é inerte.

### 23.3 `LayoutSnapshotValidator`

Garantir:

- um gameplay ID interativo → uma instância visual;
- todos os nodes/edges recebidos estão representados;
- coordenadas/bounds finitos;
- resultado determinístico;
- nenhuma hitbox interativa indecidível.

### 23.4 Coverage

No baseline:

- 23/23 classes reais com visual dedicado ou fallback testado;
- Ranger/Hunter e Death Knight documentadas como design-only até aparecerem no server snapshot;
- Artificer não contado como classe;
- 25/25 specialization definitions com emblema/binding visual dedicado;
- node-shaped specialist glyph exigido **apenas** quando specialist topology existir;
- 11/11 domínios reconhecidos;
- Industrialist/Prospector/Logistician não reintroduzidos como classes.

---

## 24. Plano de implementação TDD e ordem correta

### Task 0 — Vertical slice e D001

**Pré-requisito da implementação integral.**

- [ ] selecionar uma subtree real para o slice (preferir uma das quatro já materializadas);
- [ ] provar main → gateway → subtree;
- [ ] provar purchase/respec;
- [ ] provar rule revision sync;
- [ ] provar multiplayer/server authority;
- [ ] avaliar Passive Skill Tree candidate API/license/limitations;
- [ ] avaliar custom UI slice equivalente;
- [ ] registrar medições/UX/maintenance trade-offs;
- [ ] criar ADR D001;
- [ ] parar se D001 ainda não estiver resolvida.

### Task 1 — Modelo visual mínimo da engine escolhida

- [ ] RED: binding visual não altera gameplay;
- [ ] RED: current IDs `technomancer`/`irons_fire` resolvem sem namespace inventado;
- [ ] RED: visual ID `rpgskilltree:flame` continua namespaced;
- [ ] implementar shape/binding model mínimo;
- [ ] implementar `generic_constellation`;
- [ ] commit isolado.

### Task 2 — Scene projection read-only

- [ ] RED: ID ausente não materializa entidade interativa;
- [ ] RED: uma specialization compartilhada tem uma única visual instance;
- [ ] montar scene a partir da projeção server-side;
- [ ] nenhuma consulta a `ModList` como authority de unlock;
- [ ] commit isolado.

### Task 3 — Layout base R0/R1/R2/R3

- [ ] RED: 11 domain anchors;
- [ ] RED: determinismo;
- [ ] class anchor derivado de `required_completed_domains`;
- [ ] regressão explícita para Beastmaster = AGILITY + SUMMONING no baseline;
- [ ] classes ausentes não recebem hitbox interativa;
- [ ] commit isolado.

### Task 4 — Performance baseline ANTES de culling/index

- [ ] renderizar vertical slice sem otimizações prematuras;
- [ ] medir 512+ nodes com implementação simples;
- [ ] medir hit testing linear;
- [ ] medir scene/layout rebuild separado do frame;
- [ ] registrar profiler evidence;
- [ ] decidir quais otimizações da Task 9 são justificadas;
- [ ] commit apenas de harness/medição quando aplicável.

### Task 5 — Class constellation visuals

- [ ] RED: cobertura das 23 classes atuais;
- [ ] implementar as formas da seção 6;
- [ ] Ranger/Hunter e Death Knight continuam não interativas enquanto ausentes;
- [ ] Artificer continua sem binding de classe enquanto D014 aberta;
- [ ] Industrialist/Prospector/Logistician não reaparecem como classes;
- [ ] commit isolado.

### Task 6 — Specialization emblems sem fake topology

- [ ] RED: 25/25 specialization definitions recebem shape binding;
- [ ] RED: specialization sem graph possui zero fake passive nodes;
- [ ] implementar emblemas/gate/status;
- [ ] implementar `water_drop` e demais templates futuros somente como assets inertes quando desejado;
- [ ] commit isolado.

### Task 7 — Symbolic subgraph layout para topologias REAIS

Executar somente para subtrees já reais ou specialist trees materializadas por fase server-side.

- [ ] RED: gateway/capstone recebem landmarks sem mudar dependências;
- [ ] RED: todos os nodes e edges preservados;
- [ ] implementar lane fitting/scale;
- [ ] aplicar primeiro às subtrees reais selecionadas;
- [ ] habilitar Tree 3 node-shaped glyph por specialization apenas quando seu `tree_id`/graph real estiver no snapshot;
- [ ] commit isolado.

### Task 8 — Renderer e LOD funcional

- [ ] renderer de cluster/edge/node/emblem;
- [ ] LOD semântico para legibilidade;
- [ ] nenhuma animação altera estado;
- [ ] iniciar sem culling complexo se Task 4 não o justificar;
- [ ] commit isolado.

### Task 9 — Otimizações condicionadas ao profiling

Executar apenas itens apoiados pela Task 4/novo profiling:

- [ ] coarse cluster culling, se necessário;
- [ ] fine node culling, se necessário;
- [ ] spatial index, se hit test linear for insuficiente;
- [ ] edge/text/icon caches adicionais, se evidência justificar;
- [ ] registrar antes/depois;
- [ ] commit isolado.

### Task 10 — Camera, input, seleção e hit testing

- [ ] testes de pan/zoom bounds;
- [ ] normal `KeyMapping` consumption;
- [ ] mouse/keyboard/controller;
- [ ] hit testing simples ou indexado conforme Task 9;
- [ ] focus/centralize;
- [ ] commit isolado.

### Task 11 — Tooltips, blocking reasons e server intents

- [ ] RED: provider absent/adapter incompatible/requisite missing;
- [ ] tooltip PT-BR;
- [ ] purchase/respec intent usa protocolo canônico;
- [ ] animação de confirmação só depois de snapshot server-side;
- [ ] stale revision falha fechado;
- [ ] commit isolado.

### Task 12 — Busca, filtros e breadcrumbs

- [ ] RED: busca retorna canonical ID único;
- [ ] busca PT-BR;
- [ ] filtros não mudam gameplay;
- [ ] path highlight;
- [ ] breadcrumbs;
- [ ] commit isolado.

### Task 13 — Acessibilidade e motion policy

- [ ] teclado/controle;
- [ ] focus ring;
- [ ] reduced motion;
- [ ] particle toggle;
- [ ] non-color cues;
- [ ] tooltip screen bounds;
- [ ] commit isolado.

### Task 14 — Animação temática

- [ ] somente sobre `VisualState` confirmado;
- [ ] budget de efeitos baseado no profiling;
- [ ] fallback estático reduced-motion;
- [ ] nenhuma animação concede rank/unlock;
- [ ] commit isolado.

### Task 15 — Specialist topology expansion — trilha coordenada, não invenção de UI

Esta Task não autoriza o cliente a desenhar fake perks. Para cada especialização que receber árvore própria em uma fase de gameplay:

- [ ] design/auditoria da subtree server-side;
- [ ] graph + costs + currency + effects + gates + provenance;
- [ ] testes de purchase/respec/reload;
- [ ] client projection inclui o novo graph;
- [ ] 07.05 passa a usar automaticamente shape fitting para aquele ID;
- [ ] nenhuma mudança manual de renderer por quantidade de specializations.

### Task 16 — Acceptance e regressão

- [ ] JUnit/core/client-model tests GREEN;
- [ ] GameTests aplicáveis GREEN;
- [ ] current tree validators GREEN;
- [ ] GUI scales/resolutions;
- [ ] screenshot/manual regression;
- [ ] rule revision/reconnect;
- [ ] provider unavailable;
- [ ] 512+ node profiling;
- [ ] NeoForge build GREEN;
- [ ] JAR verification GREEN;
- [ ] dedicated-server smoke GREEN;
- [ ] CI GREEN;
- [ ] `plans/STATUS.md`/ADR/documentação atualizados com evidência real;
- [ ] merge e confirmação da `main`.

---

## 25. Matriz de testes obrigatória

### Estrutura

- main graph continua completo;
- nenhum node/edge real perdido;
- nenhuma instância interativa duplicada;
- layout determinístico.

### IDs

- current unnamespaced class/specialization IDs resolvem;
- visual asset IDs namespaced resolvem;
- futuro ID migration exige fixtures/aliases antes de mudar bindings.

### Classes

- 23 baseline classes;
- Beastmaster anchor deriva AGILITY + SUMMONING no baseline;
- multiclass simultâneo;
- class lost after respec;
- Ranger/Hunter e Death Knight ausentes não compráveis;
- Artificer não vira classe antes da D014.

### Specializations

- 25 current definitions possuem emblema;
- nenhuma recebe fake nodes sem graph;
- provider absent/adapter fail-closed;
- shared specialization = uma visual instance;
- future template without binding = invisível/inativo.

### Subtrees

- Technomancer 17, Warlock 18, Druid 11, Metamorph 10 preservadas enquanto o baseline correspondente não mudar;
- node-shaped symbolic layout não muda dependency/exclusivity/cost;
- novos specialist graphs entram sem mudança hardcoded de renderer.

### Authority

- resource pack malicioso não muda custo/gate;
- local VisualState não concede rank;
- rejected purchase retorna ao snapshot server-side;
- stale revision bloqueia mutation.

### Client/server safety

- client-only classes não classload no dedicated server;
- falta de visual asset não quebra gameplay;
- server não depende de shape templates.

### UX

- pan/zoom;
- UI scale;
- keyboard/mouse/controller;
- tooltip explica bloqueio;
- busca/filtros;
- reduced motion;
- screenshot regression.

---

## 26. Critérios de conclusão em camadas

### 26.1 Plano documentado

Concluído quando este arquivo e o índice do Stage 07 estiverem mergeados. Isso **não significa Stage 07.05 implementado**.

### 26.2 Vertical slice / D001

Concluído quando a comparação Passive Skill Tree vs custom UI for feita com slice real e ADR D001 estiver ACCEPTED.

### 26.3 Core cosmogram UI

Concluído quando a engine escolhida apresentar:

- Árvore 1;
- Árvore 2;
- 11 domínios;
- classes reais como constelações;
- 25 specialization definitions atuais como emblemas/gateways/status;
- subtrees reais navegáveis;
- pan/zoom/search/tooltips/accessibility;
- autoridade server-side comprovada.

### 26.4 Resultado final da Árvore 3 simbólica

Concluído somente quando, para cada especialização que o design de gameplay decidir que possui árvore própria:

- a specialist topology real existir server-side;
- seus nodes/edges forem projetados ao cliente;
- o shape fitter usar esses nodes/edges reais;
- a silhueta temática resultar da topologia real;
- não houver fake perk/decorative node confundido com gameplay.

É neste nível que `irons_fire` pode literalmente formar uma chama, `irons_blood` uma gota, `irons_eldritch` um pentagrama etc., desde que suas árvores tenham sido materializadas canonicamente.

---

## 27. Proibições

07.05 não pode:

- pular D001;
- migrar 512 nodes antes do vertical slice;
- criar perk para preencher shape;
- criar fake specialist node;
- alterar cost/effect/mastery/provider/gate;
- namespacificar class/specialization ID incidentalmente;
- promover mod a classe;
- reintroduzir Industrialist/Prospector/Logistician como classes;
- decidir Artificer silenciosamente;
- implementar Aquamante porque existe `water_drop`;
- mostrar Ranger/Hunter/Death Knight como runtime antes do servidor;
- duplicar uma specialization por classe;
- criar segunda mana/stamina/blood/temperature/Soul/Source/energy;
- usar client state como authority;
- implementar culling/spatial indexes apenas por suposição de performance;
- carregar código client-only em dedicated server.

---

## 28. Resultado visual final

```text
                    ÁRVORE 3 — ESPECIALISTAS
          chama • floco • halo • gota • engrenagem • ...
                       \      |      /
                    CONSTELAÇÕES
          Warrior • Mage • Druid • Engineer • ...
                       \      |      /
                ÁRVORE 2 — 11 DOMÍNIOS
                         \   /
                    ÁRVORE 1
                     ATRIBUTOS
```

A regra que define o projeto continua sendo:

> **a fantasia visual deve emergir da progressão canônica; nunca substituir a progressão canônica.**
