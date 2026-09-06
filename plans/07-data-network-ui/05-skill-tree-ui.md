# 07.05 — Skill Tree UI — Cosmograma Unificado, Constelações de Classe e Árvores Simbólicas

> **Status:** PLANEJADO / ABERTO — contrato de design aprovado para implementação futura.  
> **Baseline de planejamento:** `main@4d7aa9577571ee302c44b3384a984ffda4c742ae`.  
> **Plataforma:** Minecraft 1.21.1, NeoForge 21.1.x, Java 21.  
> **Para agentes de implementação:** executar em TDD, preservar autoridade server-side e tratar este arquivo como contrato do Stage 07.05. Não criar nodes, classes, especializações, recursos ou efeitos de gameplay apenas para preencher uma forma visual.

## 1. Objetivo

Transformar a progressão do RPG Skill Tree em um **único cosmograma navegável**, legível do macro ao micro, no qual a própria geometria da árvore comunica a identidade da build:

1. **Árvore 1 — Atributos** ocupa o núcleo central;
2. **Árvore 2 — Perks Principais** forma o grande corpo radial intermediário com os 11 domínios canônicos;
3. **Classes emergentes** aparecem como constelações/âncoras na periferia da Árvore 2, sem virar escolha inicial nem lock;
4. **Árvore 3 — Especialistas** ocupa as extremidades e cada especialização é desenhada por seus próprios nodes/conexões como um símbolo reconhecível — chama, gota, pentagrama, floco, engrenagem, asa, circuito, arco etc.;
5. zoom, LOD, culling e estados visuais permitem navegar uma árvore muito grande sem esconder requisitos nem comprometer performance;
6. nenhuma decisão visual concede progressão localmente: compra, respec, gates, classes, Mastery, disponibilidade de provider e especializações continuam server-authoritative.

A tela deve produzir a sensação de um grande mapa astral/arcano de progressão: à distância o jogador identifica **formas e regiões**; ao aproximar, identifica **classes, especializações, rotas e gateways**; em zoom próximo, manipula **nodes, ranks, custos, requisitos e tooltips**.

---

## 2. Fontes de verdade e regra de precedência

Este plano é de **UI/layout**, não redefine gameplay.

Ordem obrigatória de autoridade durante a implementação:

1. estado e catálogos server-authoritative carregados pelo runtime atual;
2. `src/main/resources/data/rpgskilltree/classes/` para classes realmente existentes;
3. `src/main/resources/data/rpgskilltree/specializations/` para especializações realmente existentes;
4. árvore principal, `node_rules`, skill definitions, tree architecture, topology validators e demais catálogos canônicos;
5. `plans/04-classes-masteries-specializations/` para contratos já fechados de classes/subtrees/gateways;
6. Documento Mestre no Notion para decisões-alvo ainda não materializadas;
7. este arquivo para **composição visual, geometria, navegação e apresentação**.

A modlist anexada em 2026-09-06 possui **607 mods** e é mais recente que os snapshots de 2026-08-30 usados pelos guias consolidados. Portanto:

- a UI não deve hardcodar presença de provider a partir dos guias;
- versões e disponibilidade devem vir do runtime/catálogo sincronizado;
- uma atualização de modlist não exige redesenhar a tela se IDs canônicos permanecem estáveis;
- provider ausente/incompatível deve refletir o mesmo fail-closed do servidor, nunca ser “compensado” visualmente por unlock local.

---

## 3. Estado real do baseline que o plano deve preservar

### 3.1 Árvore principal

A arquitetura canônica atual declara 11 domínios:

- `MARTIAL`
- `AGILITY`
- `VITALITY`
- `HEALING`
- `ARCANE`
- `ENGINEERING`
- `MINING`
- `SURVIVAL`
- `SUMMONING`
- `OCCULT`
- `LOGISTICS`

A árvore principal possui contrato estrutural de **512 nodes**, root canônico, posições e conexões validadas. Este plano **não autoriza regenerar a topologia para deixá-la bonita**. A camada cosmográfica deve consumir a topologia existente e apenas projetá-la visualmente.

### 3.2 Classes materializadas na `main`

No baseline deste plano existem **23 definições de classe**:

`Arcanist`, `Beastmaster`, `Cleric`, `Druid`, `Duelist`, `Engineer`, `Geomancer`, `Guardian`, `Mage`, `Metamorph`, `Miner`, `Necromancer`, `Occultist`, `Paladin`, `Priest`, `Rogue`, `Sorcerer`, `Spellblade`, `Summoner`, `Survivor`, `Technomancer`, `Warlock` e `Warrior`.

O Documento Mestre mantém como alvo adicional:

- **Ranger/Hunter** — classe planejada, ainda não deve ser mostrada como adquirível enquanto não existir no catálogo server-side;
- **Death Knight** — classe planejada/condicional, só deve aparecer como classe interativa quando sua definição e integração Martial + Occult existirem de fato.

Assim, o **catálogo visual alvo cobre 25 identidades de classe**, mas a tela só materializa como interativa a interseção com o catálogo recebido do servidor.

Conceitos antigos já demovidos de classe não voltam a ser classe pela UI: `Industrialist`, `Prospector` e `Logistician` permanecem lanes/especializações conforme o Documento Mestre.

### 3.3 Especializações materializadas na `main`

No baseline existem **25 IDs de especialização**:

- `ae2_networks`
- `ars_amplification`
- `ars_aoe`
- `ars_control`
- `ars_duration`
- `ars_projectile`
- `ars_summoning`
- `create_aeronautics`
- `create_artillery`
- `create_automation`
- `create_kinetics`
- `epic_heavy`
- `epic_ranged`
- `epic_sword`
- `irons_blood`
- `irons_eldritch`
- `irons_ender`
- `irons_evocation`
- `irons_fire`
- `irons_holy`
- `irons_ice`
- `irons_lightning`
- `irons_nature`
- `oritech_mining`
- `oritech_power`

O renderer nunca inferirá que um nome temático é uma especialização real. Exemplo: o template visual `water_drop` pode existir desde o primeiro release do renderer para permitir uma futura Aquamancia, mas **não existe `Aquamante` interativo** enquanto o servidor não fornecer um ID canônico correspondente.

### 3.4 Subtrees dedicadas já materializadas

Quatro classes possuem subtrees dedicadas verificadas no contrato atual e devem receber representação simbólica fiel:

- `Technomancer`: 17 nodes;
- `Warlock`: 18 nodes;
- `Druid`: 11 nodes;
- `Metamorph`: 10 nodes.

A UI deve preservar exatamente seus IDs, requisitos, escolhas e semântica de respec. Geometria nunca altera dependência nem exclusividade.

---

## 4. Composição global do cosmograma

### 4.1 Camadas radiais

A cena usa quatro bandas lógicas, em coordenadas de mundo da UI e independentes da resolução física do monitor:

```text
R0 — núcleo                         Árvore 1 / Atributos
R1 — corpo radial                   Árvore 2 / 11 domínios / 512 nodes
R2 — cinturão de constelações       Classes emergentes e subtrees dedicadas
R3 — coroa externa                  Árvore 3 / especializações simbólicas
```

Não são quatro sistemas de progressão. São quatro **camadas de apresentação de um estado canônico único**.

### 4.2 Regra de identidade única

Cada node, classe e especialização possui uma única identidade canônica.

É proibido:

- duplicar um node comprável em dois lugares como duas instâncias independentes;
- mostrar duas cópias de `irons_holy` porque Priest e Paladin podem alcançá-lo;
- criar um segundo rank visual para o mesmo node;
- representar uma classe compartilhada por “clones” diferentes por domínio.

Quando várias classes se ligam à mesma especialização, existe **um único glifo de especialização** e múltiplas bridges visuais até ele.

### 4.3 Regra de topologia antes da estética

Pipeline obrigatório:

```text
catálogos + topologia server-authoritative
                ↓
        SceneAssembler
                ↓
   identidade / função / gates
                ↓
    CosmogramPlacementResolver
                ↓
 ShapeTemplate / coordenadas visuais
                ↓
             Renderer
```

Nunca o inverso. Um pentagrama não pode criar uma quinta dependência; uma chama não pode mover o capstone para outro caminho se isso altera a ordem de aquisição.

### 4.4 Âncoras dos 11 domínios

O corpo radial da Árvore 2 continua organizado pelos domínios existentes. O layout cosmográfico deriva um `DomainAnchor(angle, radius)` para cada domínio a partir do blueprint/layout canônico, não de uma enum duplicada dentro da tela.

Classes de um único domínio ficam próximas ao arco desse domínio. Classes híbridas ficam no bissetor ponderado dos domínios que as formam. Classes de três domínios usam centróide angular e banda radial externa para evitar cruzamentos com classes simples.

---

## 5. Gramática visual dos nodes

A função mecânica do node deve ser reconhecível sem depender apenas de cor.

| Função | Forma base | Peso visual | Regra |
|---|---|---:|---|
| caminho / small passive | círculo pequeno | 1× | conexão e rank legíveis em zoom próximo |
| ranked passive | círculo com anéis | 1.15× | anéis representam ranks possíveis, não ranks inventados |
| bridge | losango/elo | 1.1× | sempre preserva custo/rota real |
| gateway | hexágono/portal | 1.4× | ponto de entrada para classe/especialização/sistema |
| notable | círculo ornamentado | 1.5× | identidade mecânica mais forte |
| keystone | polígono/runa | 1.8× | regra de gameplay relevante |
| capstone | emblema maior | 2.2× | landmark final do ramo/subtree |

Acessibilidade exige diferenças de forma/contorno além de cor.

---

## 6. Estados visuais obrigatórios

Cada entidade visual possui um estado derivado do snapshot sincronizado.

### 6.1 Nodes

- `UNKNOWN/HIDDEN`: somente quando a regra de descoberta do servidor realmente permite ocultação;
- `VISIBLE_LOCKED`: conhecido, requisitos incompletos;
- `AVAILABLE`: comprável pelo estado atual;
- `INVESTED_PARTIAL`: rank > 0 e < max;
- `INVESTED_MAX`: rank máximo;
- `INVALIDATED/RECONCILING`: estado transitório somente enquanto snapshot muda, nunca persistido como gameplay;
- `PROVIDER_UNAVAILABLE`: funcionalidade existe em dados, mas gate runtime está fail-closed.

### 6.2 Classes

- inexistente no catálogo: **não renderizar como classe ativa**;
- conhecida, não elegível: constelação apagada + requisitos;
- elegível/emergente: contorno ativo;
- atualmente satisfeita: símbolo de classe aceso;
- perdida após respec: atualização imediata a partir do novo snapshot;
- multiclass: todas as identidades satisfeitas permanecem acesas simultaneamente.

### 6.3 Especializações

Usar exatamente a distinção server-side entre definição e disponibilidade. A UI deve poder apresentar:

- definição desconhecida/inexistente;
- definição conhecida, provider ausente;
- provider presente, adapter incompleto/incompatível;
- gateway disponível, requisitos ainda não satisfeitos;
- gateway disponível e elegível;
- investida parcialmente;
- completa.

A forma inteira pode iluminar progressivamente conforme nodes reais são adquiridos, mas essa iluminação é só derivação visual.

---

## 7. Biblioteca canônica de formas da Árvore 3

Os próprios nodes e arestas formam o símbolo. Background art pode reforçar a silhueta, mas nunca substituir a estrutura interativa.

Cada `ShapeTemplate` define:

- `id` estável;
- `outlinePath` normalizado em `[-1, +1]`;
- `skeletonLanes` para acomodar caminhos reais;
- `landmarks` semânticos (`gateway`, `notable`, `keystone`, `capstone`, `branch_tip`, `hub`);
- simetria permitida;
- rotação preferida;
- aspect ratio;
- densidade máxima antes de subdividir uma lane;
- margem de hitbox;
- versão do schema visual.

### 7.1 Mapeamento obrigatório das 25 especializações atuais

| ID canônico | Template visual | Silhueta |
|---|---|---|
| `ae2_networks` | `network_hex` | hexágono/circuito interligado |
| `ars_amplification` | `amplification_star` | estrela crescente/raios convergentes |
| `ars_aoe` | `concentric_rings` | círculos de área concêntricos |
| `ars_control` | `control_knot` | nó/runa de contenção |
| `ars_duration` | `hourglass_loop` | ampulheta/loop temporal |
| `ars_projectile` | `arcane_projectile` | projétil/seta arcana |
| `ars_summoning` | `summoning_circle` | círculo de invocação |
| `create_aeronautics` | `wing_propeller` | asa/hélice |
| `create_artillery` | `artillery_reticle` | mira/canhão estilizado |
| `create_automation` | `automation_loop` | loop de linha de montagem |
| `create_kinetics` | `gear` | engrenagem |
| `epic_heavy` | `heavy_hammer` | martelo/cabeça pesada |
| `epic_ranged` | `bow_arrow` | arco e flecha |
| `epic_sword` | `sword_blade` | lâmina vertical |
| `irons_blood` | `blood_drop` | gota de sangue |
| `irons_eldritch` | `eldritch_pentagram` | pentagrama/estrela abissal |
| `irons_ender` | `ender_eye_portal` | olho/portal Ender |
| `irons_evocation` | `evocation_burst` | explosão/runa radial |
| `irons_fire` | `flame` | chama/fagulha |
| `irons_holy` | `holy_halo` | halo/selo solar |
| `irons_ice` | `snowflake` | floco de neve |
| `irons_lightning` | `lightning_bolt` | raio |
| `irons_nature` | `leaf_branch` | folha/ramo |
| `oritech_mining` | `mining_crystal` | cristal/ponta de picareta |
| `oritech_power` | `power_coil` | bobina/raio industrial |

### 7.2 Templates antecipatórios permitidos

É permitido incluir templates visuais não ligados a gameplay atual para evitar refazer o motor de layout futuramente, por exemplo:

- `water_drop` — futura fantasia aquática/Aquamancia;
- `wind_spiral` — vento/aeromancia;
- `skull_ossuary` — necromancia;
- `shield` — defesa;
- `claw` — Beastmaster;
- `mask` — Metamorph;
- `mountain_crystal` — Geomancer.

Regra rígida: **template sem binding canônico não cria entrada na UI, gateway, classe ou especialização.**

---

## 8. Catálogo visual completo das classes

A geometria abaixo é **macrovisual**. Ela não altera o conteúdo da classe. Cada classe recebe uma constelação própria; quando ainda não existe subtree dedicada, a constelação projeta seus caminhos/gates reais sem criar nodes novos.

### 8.1 Warrior

- Estado no baseline: classe presente.
- Âncora principal: `MARTIAL`.
- Silhueta macro: **espada larga / chevron de avanço**.
- Organização visual: tronco central representa compromisso Martial; ramos laterais recebem categorias de arma elegíveis.
- Especializações atuais que podem aparecer se o resolver autorizar: `epic_sword`, `epic_heavy`; outras ficam condicionadas ao catálogo real.
- Capstone visual, se existir no contrato da classe: ponta superior da lâmina.
- Regra: não substituir skillbooks/movesets do Epic Fight por nodes visuais inventados.

### 8.2 Guardian

- Estado: classe presente.
- Âncoras: `MARTIAL + VITALITY`.
- Silhueta macro: **escudo segmentado**.
- Ramos: guarda/impacto/estabilidade de um lado; resistência/vida do outro, somente quando os nodes reais existirem.
- Especializações compartilhadas são conectadas por bridges, nunca clonadas.
- A borda do escudo é ideal para Notables; centro para Keystone/Capstone se o contrato real os possuir.

### 8.3 Duelist

- Estado: classe presente.
- Âncoras: `MARTIAL + AGILITY`.
- Silhueta macro: **duas lâminas cruzadas / X estreito**.
- O cruzamento representa a confluência entre precisão/timing e Martial.
- `epic_sword` pode se conectar se elegível; o renderer não presume esse gate.
- Ramos opostos devem manter leitura bilateral e evitar sobreposição de hitboxes no centro.

### 8.4 Rogue

- Estado: classe presente.
- Âncora principal: `AGILITY`, com bridges para identidades secundárias quando o servidor declarar.
- Silhueta macro: **adaga / losango quebrado**.
- Layout favorece caminhos estreitos e bifurcações rápidas sem alterar custo real.
- Especializações Ender ou ranged só ligam quando o resolver server-side efetivamente permitir.

### 8.5 Ranger/Hunter

- Estado: **alvo planejado; ausente do catálogo baseline**.
- Âncoras-alvo: `AGILITY + SURVIVAL`.
- Silhueta macro: **arco tensionado com flecha**.
- `epic_ranged` é o glifo natural de conexão quando houver classe/gate real.
- Enquanto a classe não existir no servidor, não há card interativo, caminho comprável ou falsa indicação de unlock.

### 8.6 Arcanist

- Estado: classe presente.
- Âncora: `ARCANE` profundo.
- Silhueta macro: **círculo arcano com runas concêntricas**.
- Deve funcionar como grande hub para escolas/semânticas mágicas sem duplicá-las.
- Especializações Iron e Ars ficam em glifos próprios e podem compartilhar conexão com Mage/Sorcerer/Cleric conforme o resolver.

### 8.7 Mage

- Estado: classe presente.
- Âncora: `ARCANE`.
- Silhueta macro: **estrela arcana multifacetada**.
- Cada ponta é um corredor visual possível para uma escola/especialização real; número de pontas não define número de escolas.
- Evitar hardcode “uma ponta = um provider”. O catálogo define o que existe.

### 8.8 Sorcerer

- Estado: classe presente.
- Âncora: `ARCANE` com foco ofensivo/intensidade conforme os gates reais.
- Silhueta macro: **espiral/cometa**.
- Especializações como `ars_amplification`, `ars_aoe` ou escolas ofensivas podem orbitar o arco se elegíveis.
- A espiral é apresentação; não cria stacking, carga ou recurso próprio.

### 8.9 Priest

- Estado: classe presente.
- Âncora: `HEALING/HOLY` conforme requisito real.
- Silhueta macro: **halo radiante / círculo aberto**.
- `irons_holy` é um glifo externo compartilhável, não propriedade exclusiva da classe.
- Ramos de suporte devem ficar visualmente legíveis sem sugerir que abrir o halo concede aura automaticamente.

### 8.10 Cleric

- Estado: classe presente.
- Âncoras: `HEALING + ARCANE/HOLY`.
- Silhueta macro: **selo sagrado / cálice geométrico**.
- `irons_holy` conecta pela região superior do selo.
- Bridges com Paladin e Priest permanecem arestas canônicas ou projeções visuais das relações reais; nunca atalhos compráveis novos.

### 8.11 Paladin

- Estado: classe presente.
- Âncoras: `MARTIAL + VITALITY + HEALING/HOLY` conforme definição real.
- Silhueta macro: **escudo solar com lâmina central**.
- `irons_holy` pode ser conectado de um lado e especialização de arma do outro quando elegíveis.
- A composição deve tornar evidente a identidade híbrida sem fundir Holy e defesa física no mesmo recurso.

### 8.12 Engineer

- Estado: classe presente.
- Âncora: `ENGINEERING`.
- Silhueta macro: **engrenagem técnica com trilhas de circuito**.
- Especializações atuais potencialmente relacionadas por gate real: `create_kinetics`, `create_automation`, `create_aeronautics`, `create_artillery`, `oritech_power`, `ae2_networks`.
- A UI deve mostrar provider unavailable/adapter incomplete conforme snapshot; não prometer integração porque o ícone existe.

### 8.13 Miner

- Estado: classe presente.
- Âncora: `MINING`.
- Silhueta macro: **picareta/cristal estratificado**.
- `oritech_mining` pode ocupar cristal externo quando o resolver autorizar.
- `Prospector` permanece lane/especialização, não classe.
- Geologia/prospecção só aparecem quando há provider/boundary canônico; UI não inventa scanner.

### 8.14 Survivor

- Estado: classe presente.
- Âncora: `SURVIVAL`.
- Silhueta macro: **bússola/fogueira em rosa dos ventos**.
- Setores visuais podem receber temperatura, sede, nutrição, exploração ou outras lanes somente se existirem como nodes/gates reais.
- Estados corporais permanecem nos providers canônicos; o cosmograma não cria barra paralela.

### 8.15 Summoner

- Estado: classe presente.
- Âncora: `SUMMONING`.
- Silhueta macro: **círculo de invocação com órbitas de minions**.
- Identidade deliberadamente provider-agnostic.
- `ars_summoning` é um glifo atual possível; Goety/outros providers continuam com ownership/recurso nativo.
- Uma órbita não equivale a slot de summon salvo se o servidor disser isso.

### 8.16 Beastmaster

- Estado: classe presente.
- Âncoras: `SURVIVAL + SUMMONING`.
- Silhueta macro: **pata/garra formada por cinco clusters**.
- Clusters refletem categorias reais de companion quando existirem; não criar cinco tipos mecânicos só porque a pata tem cinco pontas.
- `irons_nature` pode conectar visualmente quando elegível, mas companion animal não vira spell summon por associação estética.

### 8.17 Druid

- Estado: classe presente; subtree dedicada atual com 11 nodes.
- Âncoras: `SURVIVAL/NATURE`, com integrações de `HEALING/SUMMONING` quando reais.
- Silhueta macro: **árvore/folha ramificada**.
- Os 11 nodes atuais devem ser encaixados sem mudar suas dependências.
- `irons_nature` e `ars_summoning` podem ligar como especializações externas se os gates reais permitirem.
- Wild Shape e permissões de forma continuam server-authoritative.

### 8.18 Occultist

- Estado: classe presente.
- Âncora: `OCCULT` profundo.
- Silhueta macro: **pentagrama/círculo ritual multi-canal**.
- `irons_eldritch` e `irons_blood` são glifos atuais possíveis, mas Soul, Spirit, Blood e demais recursos não podem ser fundidos visualmente em um único recurso.
- O centro do pentagrama é um landmark de classe, não um ritual executável pela UI.

### 8.19 Warlock

- Estado: classe presente; subtree dedicada atual com 18 nodes.
- Âncoras: `ARCANE + OCCULT`.
- Silhueta macro: **selo de pacto de cinco vértices**.
- Os cinco caminhos de pacto canônicos ocupam cinco braços visuais, preservando o grupo de exclusividade e a capacidade definida pelo servidor.
- `irons_blood`/`irons_eldritch` podem aparecer como satélites externos se elegíveis.
- A forma não autoriza selecionar múltiplos pactos além do limite real.

### 8.20 Necromancer

- Estado: classe presente.
- Âncoras: `SUMMONING + OCCULT`.
- Silhueta macro: **crânio/ossuário com coroa de minions**.
- O crânio é composto por lanes reais de undead/servant/command quando disponíveis.
- Possíveis conexões a `ars_summoning`, `irons_blood` e `irons_eldritch` dependem do resolver e não são inferidas pelo renderer.
- Nenhuma associação visual transforma Mobstein, Goety, Malum ou outro provider em coproprietário automático da mesma necromancia.

### 8.21 Spellblade

- Estado: classe presente.
- Âncoras: `ARCANE + MARTIAL`.
- Silhueta macro: **espada atravessando círculo arcano**.
- A metade da lâmina nasce de Martial; o círculo nasce de Arcane.
- Especializações de arma e magia podem se ligar simultaneamente quando o servidor autorizar.
- Eventos de spell-on-melee/procs permanecem deduplicados no pipeline canônico; a UI não simula proc como autoridade.

### 8.22 Technomancer

- Estado: classe presente; subtree dedicada atual com 17 nodes.
- Âncoras: `ARCANE + ENGINEERING`, com gateways tecnológicos canônicos próprios.
- Silhueta macro: **triângulo/triskelion técnico**.
- Três braços obrigatórios do layout refletem os gateways dedicados já existentes: Create Kinetics, AE2 Networks e Oritech Power.
- O capstone `triune_core`, enquanto permanecer canônico, ocupa o hub central/final da figura conforme a topologia real.
- Nenhum braço é considerado disponível só por provider instalado; usar disponibilidade runtime.

### 8.23 Geomancer

- Estado: classe presente.
- Âncoras: `ARCANE + MINING`.
- Silhueta macro: **montanha/cristal facetado**.
- Conexões possíveis a Nature/Mining dependem de gates reais.
- A UI não promove geologia, Volcanoes, RNS ou Oritech a uma única authority só porque compartilham a estética de pedra/minério.

### 8.24 Metamorph

- Estado: classe presente; subtree dedicada atual com 10 nodes.
- Âncoras: `AGILITY + OCCULT`/Assimilation conforme contrato real.
- Silhueta macro: **máscara dupla / hélice de transformação**.
- Categorias de forma ficam em lobos separados quando o catálogo atual as expõe; natural/humanoid/monstrous/aberrant permanecem distinguíveis das blacklists.
- Boss/technical blacklist continua autoridade server-side.
- A UI não duplica Identity2 nem concede morph localmente.

### 8.25 Death Knight

- Estado: **alvo planejado/condicional; ausente do catálogo baseline**.
- Âncoras-alvo: `MARTIAL + OCCULT`.
- Silhueta macro: **espada negra atravessando coroa/crânio quebrado**.
- Especializações Blood/Eldritch podem ser ligadas futuramente somente após existir definição/gates reais.
- Até isso ocorrer, nenhuma constelação interativa deve ser materializada no cliente.

---

## 9. Classes, especializações compartilhadas e prevenção de duplicação visual

Uma especialização pode servir a múltiplas classes. Exemplos de composição visual permitida, sem assumir gate:

- `irons_holy` perto do encontro Priest/Cleric/Paladin;
- `irons_fire` entre Arcane e possíveis rotas Spellblade/Mage/Sorcerer;
- `irons_nature` entre Druid/Beastmaster/Geomancer;
- `irons_blood` entre Warlock/Necromancer/Death Knight quando as classes reais autorizarem;
- `epic_ranged` junto de Ranger/Hunter/Agility quando a classe existir;
- `create_artillery` entre Engineer e caminhos Martial tecnológicos;
- `ae2_networks` entre Engineering/Logistics e Technomancer.

Regra de renderização:

```text
1 specialization id
      ↓
1 specialization glyph instance
      ↓
0..N visual bridges from eligible class/domain anchors
```

Nunca `N classes → N cópias compráveis da mesma specialization`.

---

## 10. Modelo de dados visual

Os dados de gameplay continuam em `data/`. Os metadados puramente visuais devem viver em `assets/`, separados para impedir que uma resource pack/client config se torne autoridade de gameplay.

### 10.1 Estrutura alvo

```text
src/main/resources/assets/rpgskilltree/tree_ui/
├── shape_templates/
│   ├── flame.json
│   ├── blood_drop.json
│   ├── eldritch_pentagram.json
│   ├── snowflake.json
│   ├── gear.json
│   └── ...
├── class_visuals/
│   ├── warrior.json
│   ├── guardian.json
│   └── ...
├── specialization_visuals/
│   ├── irons_fire.json
│   ├── irons_blood.json
│   └── ...
└── themes/
    └── default.json
```

### 10.2 `ShapeTemplate`

Schema lógico:

```json
{
  "schemaVersion": 1,
  "id": "rpgskilltree:flame",
  "aspectRatio": 0.72,
  "preferredRotationDeg": 0.0,
  "outline": [[0.0,1.0],[0.32,0.55],[0.52,0.1],[0.0,-1.0],[-0.52,0.1],[-0.32,0.55]],
  "landmarks": {
    "gateway": [0.0,-1.0],
    "hub": [0.0,-0.05],
    "capstone": [0.0,1.0]
  },
  "lanes": [
    {"id":"left","from":"gateway","to":"hub","curve":-0.35},
    {"id":"right","from":"gateway","to":"hub","curve":0.35},
    {"id":"crown","from":"hub","to":"capstone","curve":0.0}
  ]
}
```

Os pontos do exemplo são geometria inicial do template, não coordenadas de node de gameplay. O gerador distribui os nodes reais pelas lanes.

### 10.3 `ClassVisualDefinition`

```json
{
  "schemaVersion": 1,
  "classId": "rpgskilltree:technomancer",
  "shape": "rpgskilltree:technomancer_triskelion",
  "preferredDomains": ["ARCANE", "ENGINEERING"],
  "placementBand": "CLASS_BELT",
  "rotationPolicy": "RADIAL_OUTWARD"
}
```

Nenhum campo desse arquivo pode conter `requiredMastery`, `cost`, `effect`, `providerLoaded` ou regra de unlock.

### 10.4 `SpecializationVisualDefinition`

```json
{
  "schemaVersion": 1,
  "specializationId": "rpgskilltree:irons_fire",
  "shape": "rpgskilltree:flame",
  "placementBand": "SPECIALIST_CROWN",
  "rotationPolicy": "RADIAL_OUTWARD"
}
```

Se `specializationId` não existir no snapshot server-side, essa definição permanece inerte.

---

## 11. Gerador determinístico de layout simbólico

### 11.1 Entrada

- IDs e topologia reais;
- função estrutural dos nodes;
- coordenadas/âncoras da árvore principal;
- class definitions disponíveis;
- specialization definitions disponíveis;
- availability/gates sincronizados;
- templates visuais;
- viewport independente de resolução.

### 11.2 Saída

`LayoutSnapshot` imutável com:

- posição global de cada node;
- bounds de cada classe/especialização;
- curvas de edge;
- z-order;
- hit regions;
- LOD group;
- canonical ID → visual instance map.

### 11.3 Determinismo

Mesmos dados + mesma versão de template = mesmas coordenadas.

É proibido usar `Random` sem seed estável. Se jitter for necessário para evitar aparência excessivamente mecânica, a seed é derivada do `ResourceLocation` canônico e da `schemaVersion`.

### 11.4 Fases do algoritmo

1. reservar R0 para Árvore 1;
2. importar posições validadas da Árvore 2;
3. calcular anchors dos 11 domínios;
4. calcular anchors de classe por combinação de domínio;
5. resolver colisões entre class bounds em R2 sem mudar relação semântica;
6. colocar glyphs das especializações em R3 pela média ponderada de seus anchors elegíveis;
7. encaixar subgraph real no `ShapeTemplate` usando landmarks e lanes;
8. rotear bridges evitando atravessar hitboxes centrais;
9. validar overlaps proibidos;
10. congelar `LayoutSnapshot`.

### 11.5 Shape fitting

Prioridade de placement dentro da forma:

1. Gateway real → landmark `gateway`;
2. Capstone real → `capstone`;
3. Keystones/Notables → landmarks de alta saliência;
4. ramos e passives → skeleton lanes;
5. nodes restantes → distribuição por arc-length preservando ordem topológica.

Se a quantidade de nodes não couber na forma, o algoritmo aumenta escala ou densidade dentro de limites definidos; **não remove node nem altera aresta**.

---

## 12. Colisão, roteamento de arestas e legibilidade

### 12.1 Regras geométricas mínimas

- hitbox de node nunca sobrepõe outra hitbox na mesma camada interativa;
- labels não cobrem nodes em zoom próximo;
- class bounds podem se interpenetrar visualmente apenas se os nodes continuarem separáveis;
- edges não devem atravessar o centro de node não relacionado quando existe rota alternativa;
- bridge compartilhada deve ser distinguível de aresta de pré-requisito;
- glyphs externos mantêm margem mínima proporcional ao zoom base.

### 12.2 Roteamento

Usar curvas quadráticas/cúbicas determinísticas com pontos de controle derivados de anchors. Não usar pathfinding por pixel a cada frame.

Arestas são pré-calculadas no `LayoutSnapshot` e reprocessadas apenas em reload estrutural, mudança de UI scale que invalide bounds, ou troca de resource pack visual.

---

## 13. Zoom, LOD e viewport culling

### 13.1 Níveis de detalhe

**LOD 0 — visão total**

- formas de classes e especializações;
- nomes de domínios;
- grandes gateways/capstones;
- sem ícones pequenos/labels de passives.

**LOD 1 — visão regional**

- classe/especialização selecionada;
- Notables/Keystones/Gateways;
- caminhos principais;
- indicadores de requisito resumidos.

**LOD 2 — visão operacional**

- todos os nodes no viewport;
- ranks;
- custos;
- conexões completas;
- hover/focus.

**LOD 3 — inspeção**

- tooltip completa;
- efeitos e requisitos detalhados;
- provider/gate/fail-closed legível;
- botão/ação de compra ou respec apenas quando o protocolo já existir e o servidor autorizar.

### 13.2 Culling

Não desenhar ícones, textos, partículas ou edge decorations fora do viewport expandido. Bounds de classes/especializações podem usar coarse culling antes de testar nodes individuais.

### 13.3 Alvos de performance

Em hardware capaz de rodar o modpack:

- pan/zoom deve permanecer responsivo com a árvore completa carregada;
- nenhum cálculo O(N²) por frame sobre todos os nodes;
- layout estrutural não roda a cada frame;
- texto e ícones usam caches apropriados;
- partículas temáticas são limitadas por budget e desaparecem em LOD distante/reduced motion.

Benchmarks exatos devem ser fixados durante implementação com o profiler do ambiente real; este plano proíbe apenas regressões arquiteturais óbvias como full-graph layout por frame.

---

## 14. Interação

### 14.1 Input

Suportar:

- pan por drag;
- zoom por wheel/controles equivalentes;
- click/focus em node;
- double-click ou ação explícita para centralizar seleção;
- busca por nome/ID localizado;
- breadcrumbs: `Árvore → Domínio → Classe → Especialização → Node`;
- atalhos de “voltar ao centro”, “minha build”, “classe ativa” e “node rastreado”.

### 14.2 Compra

Fluxo obrigatório:

```text
click no node
  ↓
cliente mostra intenção + snapshot local
  ↓
request existente/futuro para servidor
  ↓
servidor revalida estado autoritativo
  ↓
aceita ou rejeita
  ↓
novo snapshot sincronizado
  ↓
UI anima somente o resultado confirmado
```

Não aplicar rank otimista como verdade persistente.

### 14.3 Respec

Preview de respec pode simular visualmente nós afetados, mas deve ser rotulado como preview e nunca mutar catálogo/snapshot autoritativo. Confirmação final passa pelo servidor.

---

## 15. Tooltips e explicabilidade

Toda compra negada precisa ser explicável.

Tooltip operacional deve suportar, conforme dados disponíveis:

- nome PT-BR;
- descrição;
- rank atual/máximo;
- custo;
- efeito por rank;
- requisitos de nodes/ranks;
- requisito de domínio/investimento;
- Mastery necessária;
- classe/especialização exigida;
- provider;
- estado do adapter quando isso for informação apropriada ao jogador;
- motivo de bloqueio;
- efeito de respec/dependentes.

Mensagem técnica como `adapter incomplete` pode ter tradução player-facing (“Integração indisponível nesta versão”) e detalhe técnico opcional em modo debug.

---

## 16. Busca, filtros e leitura de uma árvore colossal

Filtros não removem gameplay; apenas reduzem destaque.

Filtros mínimos:

- domínio;
- classe;
- especialização;
- função do node;
- disponível agora;
- investido;
- bloqueado;
- provider;
- texto livre.

Resultados de busca devem:

1. localizar canonical ID;
2. selecionar a única instância visual;
3. centralizar/zoom adequado;
4. destacar caminho de requisitos até ela.

---

## 17. Animação e linguagem temática

Animação é feedback, não autoridade.

Exemplos permitidos:

- `irons_fire`: linhas investidas pulsam como chama;
- `irons_blood`: gota recebe pulso lento;
- `irons_ice`: brilho percorre braços do floco;
- `irons_lightning`: descarga breve apenas ao confirmar alteração de estado;
- `create_kinetics`: engrenagem gira lentamente em LOD próximo;
- `ae2_networks`: pulsos percorrem trilhas;
- `irons_eldritch`: runas oscilam sutilmente;
- classe completa: contorno macro acende após snapshot confirmado.

Reduced motion deve trocar movimento por brilho/contraste estático.

---

## 18. Acessibilidade

Obrigatório:

- PT-BR como localização player-facing principal do projeto;
- nenhuma informação crítica dependente exclusivamente de cor;
- contraste configurável;
- escala de UI sem cortar o cosmograma;
- navegação por teclado/controle entre nodes adjacentes;
- focus ring distinto de hover;
- reduced motion;
- opção de reduzir/desativar partículas;
- descrições textuais para símbolos de classe/especialização;
- zoom mínimo/máximo com limites seguros;
- tooltips que permanecem dentro da tela física.

---

## 19. Arquitetura de código alvo

O baseline não possui uma implementação `client/tree` consolidada. O Stage 07.05 deve introduzir uma camada cliente isolada, sem importar attachments internos nem mutar runtime server-side.

Estrutura alvo:

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

Pacotes/nomenclatura podem ser ajustados somente para aderir a uma convenção cliente já introduzida por Stage 07.01–07.04 antes da implementação. As responsabilidades acima devem permanecer separadas.

---

## 20. Boundary de rede e snapshot

A UI consome DTO/snapshot sincronizado; ela não lê diretamente:

- `CanonicalPlayerAttachmentData` mutável;
- SavedData interna;
- registry interno de provider como autoridade local;
- `ModList` para decidir unlock;
- classes internas de integrations para fingir disponibilidade.

O snapshot de UI precisa representar pelo menos:

- revisão/version do catálogo;
- ranks de nodes;
- pontos disponíveis;
- classes satisfeitas/elegíveis;
- especializações conhecidas e disponibilidade;
- Mastery necessária para explicabilidade;
- blocking reasons normalizados;
- dados necessários para tooltips.

Se Stage 07.03/07.04 definir DTO equivalente, 07.05 deve consumi-lo em vez de criar protocolo paralelo.

---

## 21. Fail-closed visual

Erros de dado visual nunca podem abrir gameplay.

### 21.1 Template ausente

Fallback: `generic_constellation` com layout radial simples, preservando todos os nodes/arestas. Registrar diagnóstico. Não esconder node.

### 21.2 Binding visual para ID inexistente

Ignorar binding visual e registrar diagnóstico de resource pack. Não criar entidade fictícia.

### 21.3 Classe/especialização existente sem visual dedicado

Usar `generic_constellation`; gameplay permanece acessível.

### 21.4 Snapshot incompatível/desatualizado

Bloquear mutações client-side e solicitar/aguardar resync conforme protocolo Stage 07.03/07.04. Nunca assumir disponibilidade.

### 21.5 Resource reload inválido

Manter último catálogo visual válido, de forma análoga a reload atômico seguro; não destruir a tela no meio da sessão se houver snapshot visual anterior válido.

---

## 22. Validadores obrigatórios

Criar validadores separados de gameplay:

### 22.1 `ShapeTemplateValidator`

Rejeitar:

- IDs duplicados;
- outline não finito;
- landmark fora do bounds normalizado;
- lane referenciando landmark inexistente;
- aspect ratio <= 0;
- NaN/Infinity;
- schemaVersion não suportada.

### 22.2 `VisualBindingValidator`

Garantir:

- todo binding usa canonical ID sintaticamente válido;
- nenhum arquivo visual contém campo de gameplay proibido;
- especialização visual inexistente no baseline é aceita apenas como template sem binding ativo;
- classe planejada não vira definição de gameplay.

### 22.3 `LayoutSnapshotValidator`

Garantir:

- um canonical ID interativo → uma instância visual;
- nenhuma hitbox de node interativo se sobrepõe;
- todos os nodes do snapshot estão presentes;
- nenhuma aresta de gameplay é perdida;
- coordenadas finitas;
- bounds finitos;
- resultado determinístico.

### 22.4 `CanonicalVisualCoverageTest`

No baseline atual deve exigir:

- 23/23 classes reais com visual dedicado ou fallback explicitamente testado;
- 25/25 especializações reais com binding visual dedicado;
- 25 identidades de classe no catálogo de design, sendo Ranger/Hunter e Death Knight marcadas não interativas até surgirem server-side;
- paridade dos 11 domínios;
- nenhum `Industrialist`, `Prospector` ou `Logistician` reintroduzido como classe.

---

## 23. Plano de implementação TDD

### Task 1 — Modelo visual e loaders

**Produz:** schemas/modelos puros para templates, class visuals e specialization visuals.

- [ ] escrever testes RED para parse válido/inválido e campos proibidos;
- [ ] implementar records/classes imutáveis;
- [ ] implementar loaders de resource assets;
- [ ] implementar validação atômica e fallback para último snapshot válido;
- [ ] cobrir resource reload inválido;
- [ ] commit isolado.

### Task 2 — Biblioteca de templates

**Produz:** todos os templates necessários às 25 especializações baseline e 25 classes do catálogo de design.

- [ ] teste RED de cobertura por ID;
- [ ] adicionar templates listados nas seções 7 e 8;
- [ ] adicionar `generic_constellation` obrigatório;
- [ ] validar determinismo de landmarks/lanes;
- [ ] confirmar que `water_drop`/outros templates antecipatórios não criam binding ativo;
- [ ] commit isolado.

### Task 3 — Scene assembler read-only

**Produz:** `SkillTreeScene` a partir do snapshot/catálogos server-authoritative.

- [ ] teste RED provando que IDs inexistentes não são materializados;
- [ ] teste RED provando uma única instância por canonical ID;
- [ ] implementar assembler;
- [ ] integrar classes e specializations sem consultar `ModList` como authority;
- [ ] testar multiclass e specialization compartilhada;
- [ ] commit isolado.

### Task 4 — Cosmogram layout engine

**Produz:** R0/R1/R2/R3, domain anchors, class anchors e specialist crown.

- [ ] teste RED de determinismo;
- [ ] teste RED de 11 domain anchors;
- [ ] implementar bandas e placement resolver;
- [ ] implementar solver bounded de colisão entre clusters;
- [ ] garantir que Ranger/Hunter/Death Knight ausentes não ocupam hitbox interativa;
- [ ] commit isolado.

### Task 5 — Symbolic subgraph layouter

**Produz:** nodes/edges formando a silhueta de cada template.

- [ ] RED para gateway/capstone landmarks;
- [ ] RED para preservação de todas as arestas/nodes;
- [ ] implementar lane fitting por arc-length/topological order;
- [ ] implementar scale-up quando densidade excede limite;
- [ ] validar as quatro subtrees dedicadas sem mudar topologia;
- [ ] commit isolado.

### Task 6 — Edge routing e bridges compartilhadas

**Produz:** conexões legíveis sem duplicar especialização.

- [ ] RED para `irons_holy` compartilhável por múltiplas classes com uma única instância;
- [ ] RED para evitar edge atravessando node quando rota bounded existe;
- [ ] implementar curvas pré-calculadas;
- [ ] distinguir prerequisite edge, visual class link e bridge canônica;
- [ ] commit isolado.

### Task 7 — Renderer com LOD/culling

**Produz:** renderização completa da cena.

- [ ] RED de política LOD pura;
- [ ] implementar coarse cluster culling e fine node culling;
- [ ] implementar node/edge/cluster renderer;
- [ ] implementar cache de texto/ícones onde seguro;
- [ ] garantir que partículas não existam em LOD distante;
- [ ] commit isolado.

### Task 8 — Pan, zoom, hit testing e seleção

**Produz:** navegação estável.

- [ ] testes puros de camera bounds/zoom;
- [ ] implementar `TreeCamera`;
- [ ] implementar spatial index/hit tester;
- [ ] implementar seleção/focus;
- [ ] testar UI scale e resoluções múltiplas;
- [ ] commit isolado.

### Task 9 — Tooltips, blocking reasons e compra server-authoritative

**Produz:** explicabilidade sem autoridade client-side.

- [ ] RED para bloqueio por requisito/provider/adapter;
- [ ] implementar tooltip model;
- [ ] conectar request de compra ao protocolo canônico Stage 07.03/07.04;
- [ ] garantir que animação de unlock só ocorre após snapshot confirmado;
- [ ] testar rejeição server-side e stale snapshot;
- [ ] commit isolado.

### Task 10 — Busca, filtros e breadcrumbs

**Produz:** navegação rápida em árvore gigante.

- [ ] RED para canonical ID único e busca PT-BR;
- [ ] implementar índice de busca;
- [ ] implementar filtros não destrutivos;
- [ ] implementar highlight do caminho de requisitos;
- [ ] implementar breadcrumbs;
- [ ] commit isolado.

### Task 11 — Acessibilidade e reduced motion

**Produz:** interação equivalente sem depender de cor/animação.

- [ ] testes de políticas de shape/contrast quando automatizáveis;
- [ ] navegação por teclado/controle;
- [ ] focus ring;
- [ ] reduced motion;
- [ ] particle toggle;
- [ ] tooltips bounded pela tela;
- [ ] commit isolado.

### Task 12 — Animações temáticas

**Produz:** feedback visual por especialização sem alterar gameplay.

- [ ] implementar animações somente sobre `VisualState` confirmado;
- [ ] budget de partículas;
- [ ] nenhuma animação atualiza rank/gate;
- [ ] cobrir fallback estático em reduced motion;
- [ ] commit isolado.

### Task 13 — Matriz completa das classes

**Produz:** auditoria automática de cobertura de todas as identidades da seção 8.

- [ ] testar as 23 classes presentes;
- [ ] testar Ranger/Hunter e Death Knight como design-only enquanto ausentes;
- [ ] testar Technomancer/Warlock/Druid/Metamorph contra suas subtrees reais;
- [ ] testar que classes demovidas não reaparecem;
- [ ] testar especializações compartilhadas sem clone;
- [ ] commit isolado.

### Task 14 — Performance e regressão visual

**Produz:** evidência de que o cosmograma completo é utilizável.

- [ ] cenário sintético com árvore principal + todas as classes/especializações disponíveis;
- [ ] medir custo de layout separado do frame render;
- [ ] provar ausência de layout O(N²) por frame;
- [ ] validar culling em zoom distante;
- [ ] capturar screenshots de referência em resoluções/UI scales definidas na suíte;
- [ ] commit isolado.

### Task 15 — Integração, GameTests/build/smoke e fechamento

**Produz:** Stage 07.05 validado.

- [ ] JUnit de modelos/layout/validators GREEN;
- [ ] GameTests aplicáveis ao snapshot/rede e autoridade server-side GREEN;
- [ ] validators de datapack/tree existentes GREEN;
- [ ] build NeoForge GREEN;
- [ ] JAR verificado;
- [ ] dedicated-server smoke GREEN — nenhuma classe cliente deve ser carregada no servidor dedicado;
- [ ] CI GREEN;
- [ ] documentação/`plans/STATUS.md` atualizados com SHA/run reais;
- [ ] merge somente após reviews e checks obrigatórios.

---

## 24. Testes de aceitação obrigatórios

### 24.1 Integridade estrutural

- árvore principal continua com a mesma identidade/topologia canônica;
- cosmograma contém todos os nodes recebidos do snapshot;
- nenhum node interativo duplicado;
- nenhuma edge de prerequisite perdida;
- subtrees existentes mantêm seus counts/IDs enquanto o baseline não mudar.

### 24.2 Classes

- 23 classes baseline aparecem quando o servidor as fornece;
- multiclass simultâneo não causa conflito de seleção;
- Ranger/Hunter e Death Knight não aparecem como compráveis antes de existir definição server-side;
- perda de requisito após respec atualiza visual após snapshot;
- class visual ausente cai para fallback sem bloquear gameplay.

### 24.3 Especializações

- 25 especializações baseline possuem shape binding;
- provider ausente não gera unlock visual falso;
- adapter incompatível/incompleto preserva fail-closed;
- uma especialização ligada a várias classes continua sendo uma única instância;
- template antecipatório sem ID runtime não aparece.

### 24.4 Autoridade

- cliente não consegue conceder rank manipulando `VisualState`;
- clique de compra rejeitado pelo servidor volta ao estado sincronizado;
- stale snapshot não permite compra local;
- resource pack visual malicioso não altera custo/requisito/provider.

### 24.5 UX

- pan/zoom funcionam em diferentes UI scales;
- tooltip explica por que node está bloqueado;
- busca centraliza o node correto;
- LOD total mostra formas reconhecíveis;
- LOD próximo mostra nodes individuais;
- reduced motion elimina animações essenciais sem perder informação.

### 24.6 Dedicated server

- nenhuma classe de renderer/screen é classloaded no dedicated server;
- resources visuais não são requisito para resolver progressão;
- ausência de assets cliente não altera gameplay server-side.

---

## 25. Critérios de aceite finais do Stage 07.05

O Stage 07.05 só pode ser marcado concluído quando:

- [ ] Árvore 1 está representada no núcleo sem conflitar com sua autoridade de progressão;
- [ ] Árvore 2 preserva integralmente a topologia principal canônica;
- [ ] os 11 domínios são legíveis no corpo radial;
- [ ] todas as classes reais do catálogo recebem constelação visual;
- [ ] o catálogo alvo de 25 classes está documentado e classes ainda não existentes permanecem não interativas;
- [ ] as 25 especializações baseline possuem forma dedicada;
- [ ] os próprios nodes/edges formam as silhuetas simbólicas;
- [ ] Technomancer, Warlock, Druid e Metamorph preservam suas subtrees reais;
- [ ] especializações compartilhadas não são duplicadas;
- [ ] pan, zoom, LOD, culling, busca, filtros e breadcrumbs estão funcionais;
- [ ] tooltips explicam requisitos e bloqueios;
- [ ] provider/adapter fail-closed é refletido corretamente;
- [ ] cliente não possui autoridade de unlock;
- [ ] acessibilidade/reduced motion existem;
- [ ] regressão visual e performance foram verificadas;
- [ ] JUnit/GameTests aplicáveis/build/dedicated-server smoke/CI estão verdes;
- [ ] `plans/STATUS.md` registra evidência real e SHA final após merge.

---

## 26. Fora de escopo e proibições

Este plano **não autoriza**:

- criar perks novas para preencher silhuetas;
- redesenhar as 512 perks principais;
- alterar efeitos, custos, Mastery, gates ou providers;
- promover um mod a classe;
- transformar `Industrialist`, `Prospector` ou `Logistician` novamente em classes;
- implementar Aquamante apenas porque existe `water_drop`;
- tratar Ranger/Hunter ou Death Knight como implementados antes do catálogo server-side;
- duplicar progressão nativa de Epic Fight, Iron's, Ars, Goety, Vampirism, Identity2, Create, AE2, Oritech ou qualquer provider;
- introduzir segundo sistema de mana, stamina, sangue, temperatura, Soul, Source, energia ou outro recurso;
- fazer resource pack modificar gameplay;
- esconder fail-closed com bônus genérico;
- usar client-only state como autoridade.

---

## 27. Resultado visual esperado

Em zoom distante, o jogador deve reconhecer uma composição semelhante a um mapa astral:

```text
                    [especializações simbólicas]
             chama  floco  halo  gota  engrenagem ...
                         \   |   /
                   [constelações de classe]
                Warrior / Mage / Druid / etc.
                       \         /
                 [ÁRVORE 2 — 11 DOMÍNIOS]
                         \
                    [ÁRVORE 1]
                      ATRIBUTOS
```

Ao aproximar, cada símbolo deixa de ser apenas desenho e revela que **o desenho é feito pelos próprios nodes e conexões reais**.

Esse é o contrato central do Stage 07.05: **a fantasia visual deve emergir da progressão canônica, nunca substituí-la.**
