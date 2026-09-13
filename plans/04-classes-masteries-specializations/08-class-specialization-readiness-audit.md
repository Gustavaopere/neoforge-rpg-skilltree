# 04.08 — Auditoria Final de Readiness — 23 Classes, Multiclasse e Tree 3

> **Status:** DESIGN APROVADO / PRONTO PARA IMPLEMENTAÇÃO ESTRUTURAL.  
> **Baseline reconciliado:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Authority física:** modlist 2026-09-08, 595 entradas, NeoForge 21.1.248.  
> **Escopo:** fecha as decisões de classe, multiclasse, custo, atributos, disposição das 25 definitions legadas e catálogo-alvo da Tree 3. Não implementa runtime nem fecha perks individuais.

## 1. Resultado da auditoria

O catálogo atual de 23 classes permanece como inventário canônico de identidades. Nenhuma 24ª classe é adicionada neste ciclo. As 23 classes continuam possuindo região própria na Árvore 2, porém a árvore é **um único grafo conectado**, com regiões porosas e nodes compartilhados.

O modelo final separa:

- **Classe de Origem:** identidade escolhida no início da progressão RPG;
- **classe secundária:** identidade formal conquistada por `Class Gateway`;
- **classe de confluência:** identidade avançada que exige investimento comprovado em duas famílias adjacentes/compatíveis;
- **afinidade de node:** metadata explícita que permite compartilhar uma perk entre várias regiões sem duplicar gameplay ID;
- **disciplina/provider mastery:** requisito técnico/mecânico que não equivale automaticamente a especialização de personagem;
- **Tree 3:** especializações de identidade de classe, escolhidas por gateways próprios.

Os 11 `ProgressionDomain` históricos deixam de ser topologia player-facing. Permanecem como taxonomia interna fechada/compatibilidade enquanto forem úteis ao runtime e à migração.

---

## 2. Escolha inicial e multiclasse

### 2.1 Classe de Origem

O jogador escolhe exatamente **uma Classe de Origem** quando entra no sistema RPG.

A origem é imutável salvo procedimento administrativo/migração explícito. Ela define:

- anchor inicial da Árvore 2;
- afinidade econômica de origem;
- uma `origin root` gratuita, não reembolsável e com `pressure_weight = 0`;
- eventual habilidade signature somente quando houver contrato first-party/provider-native comprovado.

A escolha de origem nunca entrega spell, item, recipe, spellbook ou capability de provider externo que o jogador ainda não conquistou pelo gameplay nativo daquele provider.

### 2.2 Classes secundárias

Comprar perks na região de outra classe **não concede essa classe**.

Uma segunda ou terceira identidade só é obtida por `Class Gateway` explícito. Não existe `maxClasses` global.

O soft cap é produzido por:

- distância real no grafo;
- custo progressivo global da Árvore 2;
- requisitos de atributos permanentes da Árvore 1;
- contribuição mínima explícita na região/família;
- Mastery quando a identidade depender de prática real;
- provider gate somente quando a classe realmente depende daquele provider.

Objetivo de balance: por volta do level 300, duas classes formais profundas já devem representar investimento pesado; uma terceira continua possível em princípio, porém excepcionalmente cara. O sistema não força esse resultado por limite rígido.

---

## 3. Economia fechada da Árvore 2

Árvore 1 e Árvore 2 continuam usando `Core Progression Points`.

Não existe mais hard budget separado de pontos alocáveis na Árvore 2. O limite é econômico, não um cap arbitrário.

### 3.1 Custo base por role

| Role | `baseCost` por rank |
|---|---:|
| path / minor / ranked passive | 1 |
| notable | 2 |
| keystone | 3 |
| class gateway | 4 |
| capstone | 4 |

### 3.2 Pressão global

Cada rank pago da Árvore 2 com `pressure_weight = 1` aumenta o contador global `P` em 1.

```text
pressureTier = floor(P / 20)
rawCost      = baseCost + pressureTier
```

A `origin root` gratuita possui `pressure_weight = 0`. Conteúdo explicitamente marcado como não econômico só pode usar peso 0 quando houver justificativa de migração/sistema; perks normais não podem escapar da pressão.

### 3.3 Desconto da origem

Se o node declarar afinidade explícita com a Classe de Origem imutável:

```text
originDiscount = rawCost >= 2 ? 1 : 0
effectiveCost  = max(1, rawCost - originDiscount)
```

Classes secundárias não concedem novos descontos. Isso mantém significado econômico para a origem sem bloquear crossover.

### 3.4 Refund

O custo efetivamente pago por rank é persistido conforme ADR 004. Respec devolve o valor histórico realmente pago; nunca recalcula o refund pelo custo atual.

Após um refund, a pressão global é recalculada a partir dos ranks atualmente alocados para compras futuras. O valor histórico de ranks já comprados não é reescrito. Isso impede arbitragem por comprar caro/refundar sob outra regra.

### 3.5 Consequência esperada

Com nodes base 1, antes de notables/gateways e antes de gastar na Árvore 1, 100 ranks pagos sem desconto já consomem aproximadamente 300 pontos; com afinidade de origem em todos esses ranks, aproximadamente 220. Na árvore real haverá custos-base maiores e investimento obrigatório em atributos, portanto o level 300 não recebe acesso barato a múltiplas classes profundas.

Esses valores são contrato inicial de implementação. Telemetria/playtest pode gerar revisão versionada posterior, preservando historical paid cost.

---

## 4. Atributos permanentes usados como gates

Somente os seis atributos-base aprovados da Árvore 1 podem satisfazer thresholds de classe/perk:

1. Força;
2. Constituição;
3. Agilidade;
4. Inteligência;
5. Determinação;
6. Carisma.

Equipamento, potion, buff, forma, spell temporário ou modifier externo **não** satisfaz requisito de gateway/perk de atributo-base.

A consulta de requisito usa o investimento canônico permanente da Árvore 1.

---

## 5. Regras universais de Class Gateway

Todo Class Gateway exige caminho conectado até o gateway e requisitos data-driven explícitos. Posição visual ou nome não inferem requisito.

### 5.1 Classe de origem adquirida secundariamente

Para uma classe que também pode ser escolhida como origem:

- cumprir os thresholds de atributos da matriz desta auditoria;
- possuir pelo menos **12 unidades de contribuição** explícita daquela classe;
- cumprir Mastery/provider somente quando indicado na matriz;
- pagar o gateway de role `class gateway`.

Cada rank comprado contribui conforme `class_affinity` explícita do node. Shared nodes podem contribuir para mais de uma classe quando isso for declarado nos dados.

### 5.2 Classe de confluência

Para classe marcada `CONFLUENCE_ONLY`:

- cumprir os thresholds de atributos da matriz;
- possuir pelo menos **8 unidades de contribuição de cada lado parental** indicado;
- cumprir Mastery/provider quando indicado;
- pagar o gateway.

Um node compartilhado pode contribuir para ambos os lados somente quando sua metadata declarar explicitamente ambas as afinidades; nenhuma contribuição é inferida por coordenada.

### 5.3 Arcanist

Arcanist é a confluência arcana provider-neutral avançada:

- Inteligência >= 40;
- >= 8 contribuições em duas famílias mágicas distintas;
- evidência de prática em duas famílias de Mastery mágica distintas;
- nenhum mod único é requisito absoluto da identidade.

---

## 6. Política de signature/origin root

Toda origem recebe uma root identitária segura. Active ability só é concedida quando a authority está comprovada.

- **Druid:** `Wild Shape I` pelo First-Party Form Engine.
- **Metamorph:** acesso básico first-party a formas autorizadas pelo Form Engine.
- **Warlock:** acesso ao fluxo de seleção de pacto já pertencente ao RPG Skill Tree; não concede efeitos de pacto não escolhidos.
- **Mage:** não recebe spell/spellbook do Iron's gratuitamente.
- **Sorcerer:** não recebe glyph/spellbook/progressão do Ars gratuitamente.
- **demais origens:** root/passivo identitário; integração ativa específica só entra quando seu contrato real for implementado/auditado.

---

## 7. Matriz final das 23 classes

`Origem = SIM` significa selecionável no início e também adquirível depois. `Origem = NÃO` significa classe de confluência adquirida pela própria árvore.

| Classe | Origem | Gate de atributos | Pais / contribuição | Identidade não substituível | Vizinhança principal | Authority/provider |
|---|---|---|---|---|---|---|
| Warrior | SIM | Força 30 | 12 Warrior | combate físico amplo | Guardian, Duelist, Paladin, Spellblade | core + Epic Fight quando efeito exigir |
| Guardian | SIM | Constituição 30, Determinação 20 | 12 Guardian | defesa, estabilidade, proteção | Warrior, Paladin, Priest, Survivor | core + Epic Fight quando efeito exigir |
| Rogue | SIM | Agilidade 30 | 12 Rogue | mobilidade, oportunidade, evasão | Duelist, Survivor, Beastmaster, Metamorph | core + Epic Fight/ParCool quando efeito exigir |
| Duelist | NÃO | Agilidade 30, Força 25 | 8 Rogue + 8 Warrior | precisão/duelo entre agilidade e martial | Rogue, Warrior, Spellblade | core/Epic Fight opcional por efeito |
| Mage | SIM | Inteligência 30 | 12 Mage + `irons:casting >= 60` para aquisição secundária | spellcraft de Iron's aprendido legitimamente | Sorcerer, Arcanist, Spellblade, Cleric, Warlock | Iron's é requisito da identidade Mage ativa |
| Sorcerer | SIM | Inteligência 30 | 12 Sorcerer + `ars:casting >= 60` para aquisição secundária | spellcraft modular/intrínseco Ars | Mage, Arcanist, Warlock, Technomancer, Cleric | Ars Nouveau é requisito da identidade Sorcerer ativa |
| Arcanist | NÃO | Inteligência 40 | 8 + 8 de duas famílias mágicas + duas famílias de Mastery | teoria/eficiência arcana provider-neutral | Mage, Sorcerer, Warlock, Cleric, Geomancer, Technomancer | nenhum provider único |
| Warlock | SIM | Inteligência 25, Determinação 30 | 12 Warlock | pactos e poder oculto contratado | Occultist, Necromancer, Mage, Sorcerer, Arcanist, Metamorph | pact system first-party; providers ocultos são adapters |
| Priest | SIM | Determinação 30, Constituição 15 | 12 Priest | fé/suporte/milagres | Cleric, Paladin, Guardian, Druid | core; Iron's Holy/Paladin Spells quando usados |
| Cleric | NÃO | Determinação 30, Inteligência 25 | 8 Priest + 8 Arcane/Support | suporte sagrado com prática arcana | Priest, Paladin, Mage, Sorcerer, Druid | core + providers explícitos por efeito |
| Paladin | NÃO | Força 30, Determinação 30, Constituição 20 | 8 Martial/Guardian + 8 Priest/Holy | guerreiro sagrado resistente | Warrior, Guardian, Priest, Cleric | core + providers explícitos por efeito |
| Druid | SIM | Determinação 25, Carisma 25, Constituição 20 | 12 Druid | Wild Shape + natureza + Primal Trait Imprinting | Beastmaster, Summoner, Survivor, Priest, Geomancer, Metamorph | First-Party Form Engine; creature adapters opcionais |
| Beastmaster | SIM | Carisma 30, Agilidade 20 | 12 Beastmaster | combate ativo com companions/animais | Druid, Summoner, Rogue, Survivor | provider-neutral; companion adapters opcionais |
| Summoner | SIM | Carisma 30, Inteligência 20 | 12 Summoner | quantidade/controle/qualidade de summons | Beastmaster, Druid, Necromancer, Warlock, Sorcerer | provider-neutral; Ars/Goety/familiars por adapter |
| Necromancer | SIM | Determinação 25, Inteligência 25 | 12 Necromancer | mortos, ossos, almas e necromancia | Summoner, Occultist, Warlock, Priest boundary | provider-neutral; Goety/Iron's/etc. por adapter |
| Occultist | SIM | Determinação 30, Inteligência 20 | 12 Occultist | rituais, espíritos e eldritch systems | Warlock, Necromancer, Metamorph | provider-neutral; Malum/Eidolon/Goety por adapter |
| Metamorph | SIM | Constituição 25, Agilidade 25, Determinação 20 | 12 Metamorph | transformação corporal ampla | Druid, Rogue, Occultist, Beastmaster | First-Party Form Engine |
| Survivor | SIM | Constituição 30, Determinação 20 | 12 Survivor | resistência ambiental/exploração | Druid, Beastmaster, Guardian, Rogue, Miner | core; Cold Sweat/thirst/nutrition por adapter |
| Engineer | SIM | Inteligência 30, Força 15 | 12 Engineer | engenharia/automação física | Miner, Technomancer, Survivor | core; Create é principal provider atual, não hard dependency do boot |
| Miner | SIM | Força 30, Constituição 20 | 12 Miner | mineração/prospecção/deep extraction | Engineer, Geomancer, Survivor, Warrior | core; TFC/Oritech removidos não são requirements |
| Geomancer | NÃO | Inteligência 30, Força 25, Determinação 20 | 8 Miner + 8 Magic/Nature | terra/rocha/leyline via mineração + magia | Miner, Druid, Arcanist, Mage/Sorcerer | core + GTBC Geomancy Plus quando integração exigir; sem TFC |
| Technomancer | NÃO | Inteligência 35, Força 15 | 8 Engineer + 8 Arcane | integração real entre engenharia e magia | Engineer, Sorcerer, Mage, Arcanist | Create + Ars Creo/Ars Technica/Create Wizardry são candidatos atuais; validar adapter exato; sem AE2/Oritech |
| Spellblade | NÃO | Força 25, Agilidade 20, Inteligência 30 | 8 Martial + 8 Arcane | combate corpo-a-corpo que aplica spellcraft | Warrior, Duelist, Mage, Sorcerer, Arcanist | core + providers mágicos/combat explícitos por efeito |

### 7.1 Contagem

- 16 classes selecionáveis como origem;
- 7 classes `CONFLUENCE_ONLY`;
- 23 regiões player-facing na mesma Árvore 2;
- 0 hard cap de classes.

Classes de confluência: Arcanist, Cleric, Duelist, Geomancer, Paladin, Spellblade e Technomancer.

---

## 8. Disposição final das 25 specialization definitions legadas

As 25 definitions atuais deixam de significar automaticamente “25 especializações de personagem”. Seus IDs continuam legíveis para save/migração e seus providers/masteries continuam aproveitáveis quando válidos.

### 8.1 Ars Nouveau — 6 `SHARED_DISCIPLINE`

- `ars_amplification`
- `ars_aoe`
- `ars_control`
- `ars_duration`
- `ars_projectile`
- `ars_summoning`

São disciplinas de composição/mastery compartilháveis. Podem ser requisitos internos de várias Tree 3, mas não são, isoladamente, identidades finais de personagem.

### 8.2 Iron's — 9 `SHARED_SCHOOL`

- `irons_blood`
- `irons_eldritch`
- `irons_ender`
- `irons_evocation`
- `irons_fire`
- `irons_holy`
- `irons_ice`
- `irons_lightning`
- `irons_nature`

Continuam schools/masteries provider-native e podem alimentar várias classes/especializações. Não são nove subclasses obrigatórias de Mage.

### 8.3 Epic Fight — 3 `SHARED_COMBAT_DISCIPLINE`

- `epic_heavy`
- `epic_ranged`
- `epic_sword`

Representam style/weapon mastery compartilhável entre classes compatíveis. Não são Tree 3 finais por si só.

### 8.4 Create — 4

- `create_kinetics` -> `SHARED_TECH_DISCIPLINE`;
- `create_automation` -> `PROVIDER_GATE_PREDECESSOR` para especializações tecnológicas;
- `create_aeronautics` -> `PROVIDER_GATE_PREDECESSOR`;
- `create_artillery` -> `PROVIDER_GATE_PREDECESSOR`.

Automation/Aeronautics/Artillery podem alimentar as novas Tree 3 de Engineer/Technomancer, mas a antiga definition sozinha não concede a nova identidade automaticamente.

### 8.5 Providers removidos — 3 `RETIRED_PROVIDER_REMOVED`

- `ae2_networks`;
- `oritech_mining`;
- `oritech_power`.

Regras:

- nunca ficam compráveis/ativas sem provider;
- o ID histórico permanece reconhecível para migração/diagnóstico;
- não existe substituição silenciosa por Tom's Storage, Create ou outro mod;
- estado legado é reconciliado por alias/migration/quarantine conforme o caso.

---

## 9. Tree 3 — catálogo final de 60 identidades

Tree 3 passa a ser class-centric. Não existe exigência de igual número por classe.

| Classe | Especializações Tree 3 |
|---|---|
| Arcanist | `arcane_savant`, `leyline_theorist` |
| Beastmaster | `packmaster`, `mythic_bond` |
| Cleric | `battle_chaplain`, `oracle` |
| Druid | `wild_avatar`, `primal_legacy`, `grove_sage` |
| Duelist | `fencer`, `blade_dancer` |
| Engineer | `automation_architect`, `aeronaut`, `artillerist` |
| Geomancer | `stonecaller`, `leyline_shaper` |
| Guardian | `bulwark`, `juggernaut` |
| Mage | `elementalist`, `archmage` |
| Metamorph | `chimera`, `monster_mimic`, `perfect_impostor` |
| Miner | `prospector`, `deep_delver` |
| Necromancer | `bone_weaver`, `soul_shepherd`, `death_ascendant` |
| Occultist | `ritualist`, `spirit_binder`, `eldritch_seer` |
| Paladin | `oathkeeper`, `templar`, `inquisitor` |
| Priest | `hierophant`, `miracle_worker`, `exorcist` |
| Rogue | `assassin`, `trickster`, `shadowrunner` |
| Sorcerer | `spellweaver`, `elemental_sculptor`, `wild_arcana` |
| Spellblade | `rune_knight`, `arcane_duelist`, `elemental_blade` |
| Summoner | `familiar_master`, `legion_master`, `spirit_conjurer` |
| Survivor | `pathfinder`, `frontier_warden` |
| Technomancer | `arcane_automator`, `spell_engineer`, `aetheric_mechanist` |
| Warlock | `pact_lord`, `blood_binder`, `void_caller` |
| Warrior | `berserker`, `weapon_master`, `warlord` |

Total: **60 especializações de identidade**.

### 9.1 Restrições semânticas importantes

- `primal_legacy` é o caminho exclusivo do Druid que manifesta traits de formas no corpo humano.
- Metamorph pode usar formas naturais e monstruosas, mas `chimera` combina/adapta capacidades **durante transformação**; não duplica `Primal Trait Imprinting` humano.
- os cinco pactos atuais do Warlock permanecem choices internas/exclusivas do sistema de pactos, não viram cinco especializações Tree 3.
- `mythic_bond` só usa criaturas de Ice and Fire/Alex's/etc. depois de adapter comprovado da versão física.
- `aeronaut`/`artillerist`/`automation_architect` são provider-gated quando dependem de Create/addons; ausência do provider falha fechado.
- schools do Iron's, disciplinas Ars e styles Epic Fight são requisitos/disciplinas compartilhadas e podem aparecer dentro de várias especializações sem clonar authority.

---

## 10. Specialist Points — regra fechada

Tree 3 usa moeda separada da Árvore 1/2.

`Specialist Points` não são entregues passivamente por Character Level. Eles são concedidos exclusivamente por **milestones explícitos e data-driven** vinculados a uma especialização/classe.

Cada milestone declara:

- `specialization_id`;
- quantidade de SP;
- classe formal exigida;
- Mastery/thresholds exigidos;
- provider requirements opcionais;
- quest/advancement/discovery opcional;
- provenance estável.

SP é contabilizado por especialização/provenance; não pode ser farmado em uma especialização e gasto em outra.

Ativar o gateway de uma especialização concede **0 SP por padrão**. Um grant só existe se um milestone explícito o declarar.

Respec devolve SP para a mesma especialização. Provider temporariamente ausente desabilita efeitos/gates dependentes, mas não converte SP histórico em moeda genérica.

Não existe hard cap global de número de especializações. Mutual exclusivity só existe quando um `choice_group` explícito exigir isso. Portanto multiclass pode sustentar múltiplas Tree 3 sem um `maxSpecializations` artificial.

---

## 11. Migração e compatibilidade

### 11.1 Classes

- IDs atuais das 23 classes são preservados;
- `required_completed_domains` antigo vira input de migração, não authority nova;
- bridge price fixo antigo deixa de ser regra player-facing após migração para gateways físicos;
- proveniência de bridge antiga nunca é descartada silenciosamente;
- classe de origem nova é persistida explicitamente e não é inferida de classe antiga.

Para saves legados sem origem explícita:

1. se houver exatamente uma classe primária legada válida e ela for origin-selectable, ela pode ser proposta pela migration rule explícita daquele schema;
2. se houver ambiguidade, não escolher origem silenciosamente; exigir seleção do jogador ou regra administrativa explícita;
3. confluence-only nunca é inventada como origem automática.

### 11.2 Specializations legadas

As 25 IDs antigas são lidas como `legacy discipline/provenance`. Elas não auto-concedem uma das 60 Tree 3 novas.

Migração para nova Tree 3 só ocorre quando existir uma mapping rule 1:1 auditada. Sem equivalência, o estado antigo é preservado/quarentenado conforme ADR 004/007 e o jogador não perde dados silenciosamente.

### 11.3 Providers removidos

AE2/Oritech/TFC/Identity2/Woodwalkers não são reintroduzidos para manter contrato antigo. Referências antigas precisam ser removidas ou tratadas como legacy migration inputs.

---

## 12. Provider baseline relevante para as classes

O RPG Core não adquire hard dependency de boot em provider de gameplay externo. Providers permanecem opcionais do ponto de vista de classloading, com gates fail-closed.

No modpack físico atual, integrações relevantes incluem Iron's, Ars Nouveau, Epic Fight, Create/ecossistema, Goety, Malum, Eidolon, GTBC Geomancy Plus, Alex's Mobs/Caves Continued, Ice and Fire, Cold Sweat, Thirst Was Reclaimed, Nutritional Balance, Vampirism/Werewolves e outros auditados.

Uma classe pode exigir provider para ficar **ativa** quando sua identidade o exige — Mage/Iron's e Sorcerer/Ars são os casos baseline — sem transformar esse provider em hard dependency de inicialização do RPG Core.

Provider ausente:

- não quebra startup/save;
- bloqueia conteúdo que depende dele;
- não produz node comprável no-op;
- não é substituído silenciosamente.

---

## 13. Dependências de implementação já conhecidas

- a implementação Sorcerer/Ars precisa reconciliar o trabalho já aberto da PR #472, sem duplicá-lo;
- Druid/Metamorph executam o First-Party Form Engine de `plans/06-integrations/06-identity-morphs.md`;
- Engineer/Technomancer não podem reutilizar assumptions AE2/Oritech removidos;
- Geomancer não pode reutilizar assumptions TFC removido;
- perks concretas continuam no protocolo Chat 1 -> Chat 2 -> Chat 3 em lotes exatos de 10.

---

## 14. Readiness gate

Após esta auditoria, Chat 2 pode implementar **infraestrutura estrutural** sem decidir design:

- Origin Class persistence;
- class affinities;
- attribute requirements;
- progressive Tree 2 cost/provenance;
- Class Gateways;
- migration boundaries;
- specialist identity/provenance/SP milestone schemas;
- class-centric Tree 3 gateway model;
- custom UI/authoring foundations conforme ADR 001.

Ainda não é permitido inventar conteúdo de perk, provider hook, form capability ou efeito específico sem o respectivo dossiê/auditoria. Esses trabalhos continuam usando os gates próprios do projeto.

**Estado final deste documento: DESIGN APROVADO / READY FOR STRUCTURAL IMPLEMENTATION.**
