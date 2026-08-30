# Matriz de Integração Cruzada — Projetos Próprios

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81ddb34bfc4c085fe023

Esta matriz existe para impedir **dupla autoridade**. Uma relação só pode ser tratada como provider operacional quando produtor e consumidor possuem boundary real compatível no estado auditado.

## 1. RPG Skill Tree → Black Arcana

**Objetivo de design:** RPG fornecer requisitos/perks/Mastery e, futuramente, contributions de Arcane/Corruption Resistance.

**Estado auditado:** a Integration Layer geral Black Arcana ↔ RPG é canônica, mas o provider específico de hazard descrito em `Black-Arcana/plans/05a-arcane-danger/10-rpg-skilltree-integration.md` permanece planejado/aberto no snapshot formal.

**Authority:**

- RPG: progressão, perks, atributos e Mastery;
- Black Arcana: Arcane Danger, resistance snapshots, Corruption/Strain/Backlash.

**Regra:** o RPG adapter deve registrar contributions através da API Black Arcana. Black Arcana não deve ler attachment/storage interno do RPG. Ausência/incompatibilidade = contribuição zero/fail-closed para a parcela dependente.

## 2. Black Arcana → RPG Skill Tree

**Objetivo de design:** perks/gates baseados em casts perigosos, danger tiers, domínios ou eventos causais.

**Estado:** Arcana Core e provenance/Backlash possuem contratos reais, mas o Stage 05 genérico do RPG não prova automaticamente um hook de Mastery para todo evento Black Arcana.

**Risco de duplicação:** contar o mesmo cast por:

1. cast request;
2. nominal damage;
3. confirmed damage;
4. Backlash;
5. evento auxiliar de provider.

Somente o evento causal definido pelo dossiê da perk pode conceder progressão. `ARCANE_BACKLASH` concede **zero** Mastery/offensive credit.

## 3. Enshrouded → RPG Skill Tree

**Objetivo:** perks de exploração, resistência, ecologia corrompida e progressão ligadas ao Shroud/Flame.

**Estado:** `ShroudQuery`, Exposure, entity corruption e `FlamePassageQuery` são boundaries canônicos do Enshrouded. Uma bridge RPG dedicada não está fechada no Stage 08 Enshrouded.

**Conduta:** o Chat 1 pode desenhar uma perk que consome query pública real, mas não deve inventar uma persistência/adapter que o provider ainda não oferece. Descobertas de core/ritual precisam identidade deduplicável. Exposição contínua não gera Mastery por tick.

## 4. RPG Skill Tree → Enshrouded

**Objetivo:** perks modificarem capacidades do jogador sem sequestrar a progressão Flame/Shroud.

**Estado:** nenhum contrato auditado concede ao RPG direito genérico de escrever diretamente:

- Flame Level;
- Passage Level;
- Shroud state;
- purification lifecycle.

**Regra:** Flame/Shroud continuam Enshrouded-owned. Qualquer futura resistência/ward vinda do RPG precisa entrar por provider/boundary do Enshrouded. Sem isso, a parcela fica fail-closed.

## 5. Volcanoes → RPG Skill Tree

**Objetivo:** perks de geologia, prospecção, tectônica, vulcanismo, Atmosphere, respiração, gases e pressão.

**Estado:** os sistemas ambientais do Volcanoes são canônicos. O adapter RPG deve ser verificado por perk/boundary antes de implementação.

**Candidatos de design com base real:**

- `GeologicalDepositSource`;
- identidade persistente de depósito;
- estados/eventos ambientais discretos quando expostos;
- `AtmosphereState`/sampling;
- pressure/protection services quando houver extension point consumível.

**Anti-abuso:** sem Mastery por tick, distância viajada, throughput de máquina, permanência em gás/pressão/calor ou rebuild spam.

## 6. RPG Skill Tree → Volcanoes

**Objetivo:** perks alterarem resposta/tolerância/eficiência do jogador.

**Estado:** o RPG não possui direito genérico de escrita direta em:

- Atmosphere;
- tectonic stress;
- eruption lifecycle;
- DepositRegistry;
- protected volumes;
- equipment consumption.

**Regra:** modificador só entra quando Volcanoes expõe provider/extension point compatível. Não criar segunda Atmosphere, segundo body-temperature state, segundo equipment consumer ou segundo eruption scheduler.

## 7. Volcanoes ↔ Enshrouded

**Estado atual:** não existe acoplamento ambiental implícito canônico.

- Shroud não é gás/poluição/`AtmosphereState`;
- Atmosphere não é Shroud severity;
- Shroud exposure não é falta de O₂ ou pressão;
- SO₂/particulado não aumentam Shroud por definição.

Uma futura bridge pode ter valor temático, mas precisa definir direção, authority e boundary explícitos. Até lá, a integração é **NÃO APLICÁVEL** para efeitos automáticos.

## 8. Black Arcana ↔ Enshrouded

**Estado atual:** sistemas semanticamente separados.

- Black Arcana Corruption ≠ Enshrouded Shroud/Exposure;
- Black Arcana Arcane Resistance ≠ Enshrouded mob `MagicResistanceService`;
- Flame Passage/Flame Ward não fornecem Arcane Resistance automaticamente;
- Enshrouded Madness ≠ Arcane Strain.

**Bridge futura:** provider explícito apenas. Sem bridge, contribuição = zero.

## 9. Black Arcana ↔ Volcanoes

**Estado atual:** Black Arcana exclui implicitamente stats ambientais do Volcanoes de Arcane Resistance. Não há conversão automática.

Uma perk híbrida pode exigir simultaneamente uma condição física do Volcanoes e um estado do Black Arcana, mas cada projeto conserva sua autoridade. Pressão, temperatura, O₂, SO₂, toxicidade e tectonic stress não viram Arcane Resistance por inferência.

## 10. RNS ↔ Volcanoes ↔ RPG

**Estado:** parcial/fail-closed para ownership físico de minério.

Volcanoes identifica contexto hidrotermal de iron/copper/gold quando a causalidade vulcânica é comprovada. RNS permanece autoridade de worldgen físico/prospecção enquanto placement determinístico Volcanoes não for provado.

**Perks:** podem usar descoberta/prospecção de forma read-only e deduplicada, mas não devem conceder bônus baseado na falsa premissa de que o Volcanoes já possui os veios físicos RNS.

## 11. Enshrouded magic resistance ↔ sistemas mágicos

`MagicResistanceService` é o reducer do Enshrouded para mobs corrompidos. Adapters futuros de Ars/Iron's ou outros providers produzem classificação/evidência e não aplicam uma segunda redução.

Uma perk não deve alterar o mesmo dano em dois reducers independentes.

## 12. Technomancer ↔ Create / AE2 / Oritech

A subtree Technomancer do RPG é canônica e possui gateways específicos para os três ecossistemas. Isso **não** significa que todo o plano genérico `RPG Stage 06 Create/AE2/Oritech` esteja fechado.

Perks de Technomancer podem depender dos gates/subtree realmente materializados. Hooks profundos de máquina/energia/logística ainda precisam da API real do provider correspondente.

## 13. Black Arcana Equipment ↔ RPG Itemization / Curios

Black Arcana já possui infraestrutura parcial de equipment profiles/set bonuses. O RPG Stage 11 de itemização e o Black Arcana 05A.07 Curios permanecem, no snapshot formal, não fechados integralmente.

Não unir os dois sistemas por antecipação. Qualquer future bridge deve declarar quem calcula Item Power/affix e quem calcula Arcane/Corruption Resistance/containment.

## 14. Tabela rápida de decisão

| Relação | Status auditado | Authority | Conduta da perk |
|---|---|---|---|
| RPG → Black Arcana hazard resistance | PLANEJADO no 05A.10 | BA hazard / RPG progress | design futuro; implementação pending/fail-closed |
| BA Backlash → RPG Mastery | PROIBIDO | Black Arcana | nunca conceder Mastery/proc/sustain |
| Enshrouded Shroud/Flame → RPG | boundaries Enshrouded canônicos; bridge RPG dedicada não fechada | Enshrouded | usar query real; dedup milestones; não tick |
| RPG → Flame/Passage/Shroud state | sem authority de escrita genérica | Enshrouded | não escrever diretamente |
| Volcanoes environment → RPG | systems canônicos; adapter por perk a verificar | Volcanoes | consultar hook real; sem Mastery contínua |
| RPG → Volcanoes world state | sem authority genérica | Volcanoes | somente provider explícito |
| BA Corruption ↔ Enshrouded Shroud | SEPARADOS | cada projeto | nenhuma conversão implícita |
| BA Arcane Resistance ← Volcanoes stats | EXCLUÍDO por default | Black Arcana | contribuição zero sem bridge |
| Volcanoes RNS mineral ownership | PARCIAL / fail-closed físico | RNS worldgen/prospecting até prova | não declarar ore ownership Volcanoes |
| Enshrouded magic resistance | CANÔNICO | Enshrouded `MagicResistanceService` | adapter classifica; não reduzir duas vezes |
| Technomancer gateways | CANÔNICO nas subtrees | RPG para gate; provider externo para máquina/recurso | não extrapolar gateway para API inexistente |
| BA equipment set bonus | PARCIAL dentro de 05A ativo | Black Arcana | usar somente contracts já presentes; não declarar Stage completo |

## 15. Contrato obrigatório para perk híbrida

Se uma perk toca dois ou mais projetos próprios, o dossiê deve declarar:

1. **pipeline principal**;
2. provider que conserva a authority de cada recurso/estado;
3. consumers/adapters secundários;
4. hook/evento causal único;
5. identidade de deduplicação;
6. ordem de settlement quando houver dano/custo/reward;
7. fallback permitido;
8. fail-closed quando uma bridge opcional estiver ausente/incompatível;
9. teste de ausência do provider opcional;
10. teste que prove ausência de double-processing.

Integração temática nunca é suficiente para criar uma segunda fonte de verdade.
