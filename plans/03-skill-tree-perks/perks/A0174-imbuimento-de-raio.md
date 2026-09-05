# A0174 — Imbuimento de Raio

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A perk inteira permanece indisponível enquanto não existirem os boundaries canônicos para armar a janela a partir de uma magia LIGHTNING direta e anexar o componente derivado ao mesmo outcome melee. O fallback somente de movimento é proibido porque mudaria a identidade da bridge.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db811690caeb58b9025020`.

## Contrato de gameplay

- ARCANE ↔ MARTIAL/AGILITY; camada 5; Ponte; 3 ranks; 1 PP/rank.
- Dependências: A0170 ≥2 + mastery canônica ≥20 na família melee efetivamente usada.
- Uma conjuração LIGHTNING direta elegível do próprio jogador abre janela de 100 ticks (5 s).
- Durante a janela, movimento: +2% / +4% / +6% por rank por meio de um modificador transitório único, atualizado/substituído; nunca stacka por hit ou recast.
- Cada `direct_melee_outcome` elegível durante a janela recebe um componente LIGHTNING igual a 3% / 6% / 9% do dano-base canônico.
- Fórmula: `added_lightning = base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements × (0.03 × rank)`.
- Um único componente A0174 por `outcome_id`.

## Famílias melee elegíveis

Ativas quando sua mastery canônica for ≥20:

- `epic_sword`;
- `epic_axe`;
- `epic_spear`, somente contato melee;
- `epic_dagger`;
- `epic_hammer`;
- `combat_mace`;
- `combat_scythe`.

`combat_fist` permanece fail-closed até a lane/especialização real existir conforme P-0032; quando existir, deve exigir arma fist/knuckle e mastery `combat:fist` ≥20. **Mão vazia nunca conta.**

Arco, besta, flecha/virote, lança arremessada/projétil, DoT, summon, automação, fake player e componentes derivados são inelegíveis para a parcela ofensiva.

## Blockers canônicos

1. `DIRECT_MAGIC_OUTCOME_V1` — prova a conjuração LIGHTNING direta, autoria e identidade da ação que arma a janela;
2. `DERIVED_DAMAGE_COMPONENT_V1` — anexa o componente LIGHTNING ao outcome melee pai sem criar segundo hit/DamageSource.

A0170 também está indisponível enquanto `DIRECT_MAGIC_OUTCOME_V1` faltar, fechando a dependency closure hoje.

## Pipeline futuro obrigatório

`direct LIGHTNING cast -> DIRECT_MAGIC_OUTCOME_V1 -> armar/refresh estado A0174 por 100t -> direct_melee_outcome elegível -> calcular base canônica -> DERIVED_DAMAGE_COMPONENT_V1(LIGHTNING, 3/6/9%) no mesmo outcome -> pipeline pai continua`.

O componente deve herdar autoria e crítico do outcome pai; ele não cria nova rolagem crítica nem novo evento de proc/Mastery.

## Movimento

O bônus de movimento só existe como parte da perk completa aprovada. Sem os blockers ofensivos, não há fallback parcial. Quando a perk estiver disponível:

- um modifier UUID/identity estável;
- rank altera o valor, não cria múltiplos modifiers;
- recast substitui/atualiza duração;
- remoção em expiração, respec/logout conforme lifecycle canônico.

## Deduplicação e anti-abuso

- máximo um componente por `outcome_id`;
- componente derivado não gera Mastery/proc/retrigger;
- nenhum segundo `hurt`, DamageSource ou crítico;
- projectile-mode de spear é inelegível;
- fake player/summon/automação não herdam autoria;
- janela não é estendida por hits, somente por novo cast LIGHTNING elegível.

## Fail-closed

Enquanto blockers/dependências faltarem:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP em gates e permanece reembolsável/migrável;
- nenhum movimento parcial;
- nenhum `+dano melee` genérico;
- nenhum dano LIGHTNING em segundo hit.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não criar `DERIVED_DAMAGE_COMPONENT_V1` local para esta perk e não habilitar a metade de movimento isoladamente.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto indisponível;
2. rank legado = 0 PP e reembolsável/migrável;
3. nenhum fallback de movimento;
4. famílias melee allowlisted e mastery da própria lane ≥20;
5. spear projétil/arcos/bestas/mão vazia negativos;
6. futuro componente = 3/6/9% da base canônica correta;
7. mesmo `outcome_id`, sem segundo DamageSource/crítico/proc/Mastery;
8. movimento +2/4/6% com modifier único;
9. janela fixa 100t e recast substitui/refresh;
10. provider mismatch/lifecycle/multiplayer fail-safe.
