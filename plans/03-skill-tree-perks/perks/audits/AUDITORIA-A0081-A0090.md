# AUDITORIA — CHAT 1 — A0081–A0090

Data: 2026-08-31  
Escopo: **exatamente 10 perks consecutivas, A0081–A0090**.  
Responsabilidade: auditoria/design; nenhum runtime alterado neste Chat 1.

## 1. Fontes obrigatórias

Foram aplicados integralmente os critérios de `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, o protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`, os três guias consolidados de Gameplay/Sistemas, Magia e Tecnologia e o guia de Projetos Próprios.

Regra de cobertura aplicada em dois sentidos:

1. `perk → provider`: descobrir todos os providers pertinentes ao contrato;
2. `provider → árvore`: percorrer o universo dos três guias e verificar se alguma capacidade real deveria integrar alguma perk do lote.

Considerar todos os mods **não** significa forçar integração. Provider sem relação causal é classificado como N/A/excluído; provider pertinente sem hook seguro fica fail-closed.

## 2. Gate de delta dos projetos próprios

Baselines do lote anterior:

- RPG Skill Tree: `877120acf4f20a693e971282e8fca35bef72c6e7`.
- Volcanoes: `bbb273d61984e2c9bb84e8f8a56668ae7e315532`.
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128`.
- Black Arcana: `526d8196087c863e9df64051d5d39d88c3050856`.

Freshness de abertura A0081:

- RPG Skill Tree: `d20e7d666b627615f4af26dffb7c794b9a0b0fbd` — delta posterior à #304 é narrativa/história, sem nova capability de perks.
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c` — Stage 07 release/hardening/proveniência; nenhuma nova mecânica de gameplay para o lote.
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128` — sem delta.
- Black Arcana: `710077da89da5eb4418d3ac676e148849727ff07` — hardening de Stage 05A/Backlash/snapshot; não cria sustain ofensivo.

Decisão: nenhum delta próprio adiciona nova perk ao lote nem autoriza bypass de provider. Black Arcana reforça que `ARCANE_BACKLASH` continua hazard terminal e não pode gerar vampirismo/sifão.

## 3. Resultado por perk

| Código | Perk | Design | Estado runtime auditado | Decisão principal |
|---|---|---|---|---|
| A0081 | Recuperação de Combate | APROVADO EM FAIL-CLOSED | Core presente; estruturalmente indisponível | herda availability de A0075; sem compra enquanto A0075 estiver indisponível |
| A0082 | Vampirismo de Arma | APROVADO | backend físico presente; native dedup incompleto | armas comuns comprovadas funcionam; Ignitium fail-closed até correlação do heal nativo |
| A0083 | Vampirismo Mágico | APROVADO EM FAIL-CLOSED | coeficiente presente; sem producer magic | indisponível/não comprável até adapter DIRECT_MAGIC seguro |
| A0084 | Sifão Elemental | APROVADO EM FAIL-CLOSED | coeficiente presente; sem producer elemental | indisponível/não comprável até adapter element+root seguro |
| A0085 | Sifão de Dano Periódico | APROVADO EM FAIL-CLOSED | coeficiente presente; sem owner/pulse ledger | indisponível/não comprável até application+pulse receipt |
| A0086 | Vampirismo Universal | APROVADO EM FAIL-CLOSED | convergência core presente | availability transitiva de A0083/A0085; não bypassa classificadores ausentes |
| A0087 | Sede de Sangue | APROVADO EM FAIL-CLOSED | `BloodThirstService` presente com `BodyProvider(null)` | indisponível/não comprável; A0075/A0081 + Cold Sweat/exhaustion obrigatórios |
| A0088 | Constituição | APROVADO | binding vanilla presente | `MAX_HEALTH` + health-ratio preservation real |
| A0089 | Couro Endurecido | APROVADO | binding vanilla presente | `ARMOR` relativo; zero continua zero |
| A0090 | Têmpera | APROVADO | binding vanilla presente | `ARMOR_TOUGHNESS` relativo; não confundir com STUN_ARMOR |

## 4. Notion

Fetch fresco: **10/10**.

Páginas mutadas: **7/10** — A0081, A0082, A0083, A0084, A0085, A0086, A0087.

Re-fetch pós-escrita: **7/7 PASS**.

Sem mutação funcional: A0088, A0089, A0090.

Correções principais:

- availability transitiva A0075→A0081→A0087;
- unavailable-node explícito para A0083/A0084/A0085 enquanto nenhum producer seguro existe;
- availability transitiva A0083/A0085→A0086;
- Ignitium source-specific fail-closed em A0082 até native heal correlation exata;
- A0087 alterada de mera perk “inativa” para **indisponível/não comprável** quando heat/exhaustion obrigatórios faltam;
- A0087 preserva o contrato geral de +8% `healing received`, não somente sustain.

## 5. Pipeline de sustain canônico

`SustainResolver` é o único bucket para A0082–A0087 quando a cura for vampirismo/sifão:

- uma claim por root/pulse;
- maior coeficiente elegível;
- native heal correlacionado é contabilizado primeiro;
- cap móvel de 3% da vida máxima / 20 ticks;
- clipping por vida real do alvo e missing health;
- sinal nativo ambíguo falha fechado;
- sem carry-over.

A0081 é explicitamente separada: `CombatRecoveryService`, snapshot diferido e até quatro parcelas. A cura de A0081 não alimenta `SustainResolver`.

## 6. Provider coverage — Gameplay/Sistemas

### Epic Fight 21.17.3.1

Pertinente a A0081/A0082/A0087 para classificação de ações marcial/weapon quando o receipt existir. Não é owner de lifesteal Simply nem de health/armor/toughness.

### Simply Swords 1.70.2 / Simply More 1.3.0 ALPHA / Integrated Simply Swords 1.4.0

- A0082/A0087 podem cobrir hits de arma comprovados.
- Implicits, Runic Powers, Uniques, Awakening e efeitos próprios permanecem provider-native.
- Ability/proc não vira `direct weapon damage` só porque há arma equipada.
- Simply More ALPHA: conteúdo sem efeito funcional comprovado permanece sem semântica inventada.

### Simply Swords: Cataclysm 1.0.2

Ignitium/Blazing Brand possui lifesteal nativo. A0082/A0087 devem contabilizar a cura nativa no mesmo root. Runtime atual usa `NativeCorrelation.NONE`, portanto roots com esse lifesteal ficam fail-closed até adapter exato.

### Cold Sweat 2.4.2 / Thirst Was Reclaimed 3.0.4

A0087: Cold Sweat é owner do eixo térmico e vanilla é owner de exhaustion. Ambos são obrigatórios/all-or-nothing. Thirst é eixo hídrico separado e opcional apenas com causal receipt para a mesma atividade. Exhaustion nunca prova hydration.

### Apotheosis/Apothic/Pufferfish's Attributes

A0088–A0090 compõem com modifiers externos pela pilha vanilla. Pufferfish's Attributes não é promovido a owner genérico de health/armor/sustain pela presença do mod. Afixos/raridades não são reimplementados.

## 7. Provider coverage — Magia

### Iron's Spells 'n Spellbooks 3.16.3

Provider-native possui `io.redspace.ironsspellbooks.damage.SpellDamageSource` com `spell()`, entidade causadora/direta, school/damage type e `getLifestealPercent()`. É um caminho concreto para Chat 2 implementar A0083 e parte de A0084 sem heurística. Native lifesteal >0 ainda exige correlação da cura final antes de pagar Skill Tree.

### Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1

A0083/A0084 exigem contexto/source da versão instalada que prove caster/root e, para A0084, elemento. `ars_nouveau:spell`, namespace, VFX ou glyph isolado não bastam. Addons só herdam integração quando preservam essa provenance/classificação.

### Goety 3.1.4 / Malum 1.8.2 / Eidolon: Repraised 0.5.0.2

Candidatos de A0085 somente quando a aplicação registra owner jogador e cada pulso possui identidade causal. Summons/minions/servos continuam excluídos.

### Vampirism 1.10.12

Somente heal/lifesteal provider-native concreto e correlacionável entra no mesmo bucket. Economia de sangue, facção ou recurso não é lifesteal presumido.

## 8. Provider coverage — Tecnologia

Nenhum mod tecnológico dos guias é owner positivo de A0081–A0090. A cobertura provider→árvore é, portanto, **exclusiva/negativa** neste lote:

- damage de máquina, turret, automation, fake-player, contraption ou hazard industrial não herda sustain do jogador;
- Create/TFMG e afins não transformam dano indireto em weapon/magic/DoT elegível por autoria de construção;
- A0088–A0090 continuam atributos do jogador, não stats de máquina/equipamento industrial.

Conclusão: `NÃO DEVE SER INTEGRADO` sem futuro receipt semântico explícito.

## 9. Projetos próprios e hazards

- Black Arcana: `ARCANE_BACKLASH` e `BLOOD_MAGIC_COST` não ativam A0083–A0087.
- Enshrouded: Shroud/Exposure/Madness/environment não viram dano ofensivo do jogador.
- Volcanoes: lava, calor, gás, pressão e hazards/geologia não geram sustain.
- Mobstein/companions: owner indireto não transfere lifesteal ao jogador.

## 10. Pendências destinadas ao Chat 2

1. `P-A0081-01` **BLOQUEANTE** — unavailable A0075→A0081; no-op purchase proibido.
2. `P-A0081-02/-03` — dependency lifecycle e provenance melee real.
3. `P-A0082-01` **BLOQUEANTE POR FONTE** — native heal correlation de Ignitium; excluir roots enquanto ausente.
4. `P-A0082-02/-04` — weapon provenance, dedup e testes provider-present.
5. `P-A0083-01` **BLOQUEANTE** — unavailable até DIRECT_MAGIC producer.
6. `P-A0083-02/-04` — Iron's/Ars adapters + native lifesteal dedup.
7. `P-A0084-01` **BLOQUEANTE** — unavailable até ELEMENT producer.
8. `P-A0084-02/-04` — school/element mapping explícito + dedup A0083/A0084.
9. `P-A0085-01` **BLOQUEANTE** — unavailable até owner+application+pulse receipt.
10. `P-A0085-02/-04` — interface de receipt, lifecycle, anti-summon/hazard.
11. `P-A0086-01` **BLOQUEANTE** — availability transitiva; sem bypass universal.
12. `P-A0086-02/-03` — max coefficient e universal 1% somente para root já elegível.
13. `P-A0087-01` **BLOQUEANTE** — unavailable A0075/A0081 + BodyProvider real.
14. `P-A0087-02/-03` — Cold Sweat+exhaustion all-or-nothing; hydration causal opcional.
15. `P-A0087-04` **BLOQUEANTE DE CONFORMIDADE** — +8% healing received geral exatamente uma vez; não estreitar silenciosamente para SustainResolver.
16. `P-A0087-05` — native heal dedup/lifecycle.
17. `P-A0088-01/-03` — composição MAX_HEALTH + health ratio + idempotência.
18. `P-A0089-01/-03` — ARMOR composition/zero/lifecycle, sem STUN_ARMOR.
19. `P-A0090-01/-03` — TOUGHNESS composition/dependency/lifecycle, sem eixos paralelos.
20. `P-A0081-90-TEST-01` — harness/GameTests transversal: availability, source provenance, native dedup, magic/element/DoT receipts, cap, lifecycle, attributes, multiplayer e dedicated server.

## 11. Nove eixos / 18 critérios

Todos os 10 dossiês possuem os nove eixos individualizados. Resultado do lote: **PASS no design**, usando `FAIL-CLOSED / UNAVAILABLE_NODE` como resultado correto quando API/runtime atual não prova binding obrigatório.

Nenhuma perk é aprovada por matemática isolada, namespace, VFX, nome de item, owner indireto ou simples presença de mod.

## 12. Encerramento do design

O lote A0081–A0090 está suficientemente especificado para implementação sem redesign. Runtime não foi alterado pelo Chat 1. O fechamento operacional ainda exige review, CI GREEN, merge e confirmação da `main`; após isso o Chat 1 deve parar e A0091+ só poderá iniciar por novo comando do usuário.