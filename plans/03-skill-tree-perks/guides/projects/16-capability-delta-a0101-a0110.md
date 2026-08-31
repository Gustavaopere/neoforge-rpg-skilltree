# 16 — Capability Delta — A0101–A0110

Data de reconciliação: 2026-08-31.

Este suplemento executa o gate obrigatório **provider → árvore** para o lote A0101–A0110. O checkpoint imediatamente anterior está no lote A0091–A0100, PR #326, arquivo `15-capability-delta-a0091-a0100.md`; como essa PR ainda não está integrada na `main` no momento deste fechamento, este arquivo não duplica seus dossiês e usa explicitamente seus baselines promovidos como origem de comparação.

## Baselines anteriores — lote A0091–A0100

| Projeto | Baseline promovido na PR #326 |
|---|---|
| RPG Skill Tree | `cb95a527fa3b6138d674c74a09dc32d58885d523` |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `6642d4ed14bbae2a771075ca466e6749ac8f7fb8` |
| Black Arcana | `462c5c4af403629a7092129cf7f3070472f03e59` |

## Heads frescos auditados

| Projeto | `main` fresco | Disposição para A0101–A0110 |
|---|---|---|
| RPG Skill Tree | `2e1c5b62f89d2311eb645882e3547944d0f68869` | progressão/classes/gateways continuam authority; avanços posteriores não criam hook ausente de impacto/encumbrance/durabilidade |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | `SEM DELTA`; hazards ambientais continuam provider-owned |
| Enshrouded | `5a25b03a23ae81c111bbe1d5c23f85d8abd066ec` | Stage 07.03 áudio/partículas client-side; `NÃO DEVE SER INTEGRADO` como authority de perk |
| Black Arcana | `e89df6dc2c204c269d8f1811c6b3f309644c864a` | forecast server-authored de Arcane Resistance é read-only; reforça separação de A0102, não cria reducer genérico |

A verificação usou `main` e `plans/STATUS.md` frescos dos quatro projetos. Estados preparatórios/branches não foram promovidos por nome ou intenção.

## RPG Skill Tree

Desde `cb95a527...`, a `main` consolidou ainda mais a authority live de progressão/classes e especializações, além de trabalho paralelo do Compendium e infraestrutura Sonar/JaCoCo. O avanço final até `2e1c5b6...` inclui cobertura do hook de datapack sync, sem alteração de gameplay de perk.

### Disposição

- `ProgressionService`/Stage 04 continua **PROGRESSÃO NATIVA AUTORITATIVA** para gateway, predecessor, compra, respec e derived state. A0101–A0110 não criam segundo ledger de classe/bridge.
- Availability transitiva de A0107/A0108/A0109 deve usar a mesma authority; fórmula local não pode tornar predecessor indisponível comprável.
- `DamageMitigationResolver` é o pipeline canônico a estender para A0101/A0102/A0103/A0106 e, quando adquiríveis, A0108/A0109; não criar cadeia defensiva paralela.
- O repositório ainda fixa Ars Nouveau `5.13.0` em `gradle.properties`, enquanto modlist/guia e design Notion de A0102 fixam `5.13.1`. Classificação: **DRIFT DE FIXTURE/BUILD**, pendência Chat 2; não rebaixa a versão canônica do design.
- Nenhum avanço fecha A0093/A0100, P-0035 como capability canônica, body encumbrance ou P-0036.

## Volcanoes

Não houve avanço desde o baseline anterior. `plans/STATUS.md` confirma os estágios canônicos de Volcanoes/Atmosphere/Pressure/Integrations/Hardening e seus boundaries próprios.

### Disposição

- pressão, gases, lava, calor e proteção ambiental permanecem authority do Volcanoes/integrações correspondentes;
- não entram genericamente em A0103 por tema;
- equipamentos de pressão/respiração não são ferramentas manuais elegíveis de A0110 por default;
- massa/veículo não fornece encumbrance corporal de A0109.

Classificação: **SEM DELTA / NÃO DEVE SER INTEGRADO** onde a relação seria somente temática.

## Enshrouded

O delta de `6642d4e...` para `5a25b03...` fecha Stage 07.03 áudio/partículas e documentação de Client Experience. O status fresco marca Stage 07 em 3/4 e Stage 08 Integrations ainda não implementado.

### Disposição

- áudio, partículas, fog e config client-side não são gameplay authority;
- Shroud/Exposure/Madness permanecem sistemas próprios e não entram no allowlist A0103;
- nenhum novo provider de incoming physical, encumbrance corporal, impact→Stamina ou durability seam surgiu.

Classificação: **NÃO DEVE SER INTEGRADO AO LOTE**.

## Black Arcana

O delta de `462c5c4...` para `e89df6d...` adiciona um forecast server-authored de Arcane Resistance para apresentação contextual. O próprio status declara que ele espelha apenas providers side-effect-free, falha fechado e nunca vira cast authority; Stage 05A continua ativo/parcial.

### Disposição

- Arcane Resistance/Corruption Resistance continuam canais provider-owned;
- o forecast é read-only e **não** alimenta A0102 como resistência mágica genérica;
- `ARCANE_BACKLASH` e `BLOOD_MAGIC_COST` continuam excluídos de A0102/A0104/A0105/A0106;
- não fornece encumbrance corporal ou seam de durabilidade.

Classificação: **PROVIDER NATIVO AUTORITATIVO, SOMENTE LEITURA PARA PRESENTATION; NÃO DEVE SER CONVERTIDO EM REDUCER A0102**.

## Matriz provider → árvore

| Capacidade detectada | Estado | Cobertura na árvore | Decisão |
|---|---|---|---|
| Progression/gateway/class authority RPG | canônico | sistema universal | usar authority existente; nenhuma perk duplica ledger |
| Damage mitigation RPG-owned | infraestrutura canônica | A0092/A0096 e futuras A0101/A0102/A0103/A0106/A0108/A0109 | estender um único resolver, once/root |
| Ars fixture 5.13.0 vs design 5.13.1 | drift de build | A0102 | Chat 2 reconcilia fixture/API; fail-closed se incompatível |
| Volcanoes pressure/gas/heat/hazards | canônico | sistemas próprios | não classificar em A0103 por analogia |
| Enshrouded audio/particles | canônico client-side | apresentação própria | não integrar como gameplay |
| Enshrouded Shroud/Exposure | canônico | sistema próprio | não classificar em A0103 |
| Black Arcana Arcane Resistance forecast | parcial/canônico read-only | apresentação/hazard próprio | não integrar como reducer A0102 |
| Impact→Stamina P-0035 draft | preparatório/não canônico | A0107 | não habilita node; A0093 continua blocker |
| Player-body encumbrance | AUSENTE | A0109 | `SEM HOOK SEGURO`; node unavailable |
| Durability post-Unbreaking/pre-write seam | AUSENTE | A0110 | P-0036 bloqueante; node unavailable |

## Matriz perk → provider

| Perk | Provider/pipeline principal | Secundários permitidos | Fail-closed principal |
|---|---|---|---|
| A0101 | NeoForge DamageSource + RPG mitigation | Epic Fight adapter causal | consumer/classifier ausente |
| A0102 | `neoforge:is_magic` + RPG mitigation | Iron's/Ars adapters versionados | source unknown/version mismatch |
| A0103 | allowlist vanilla + RPG mitigation | adapter específico por DamageType | fora do allowlist |
| A0104 | NeoForge `LivingDamageEvent.Post` + RPG scheduler/healing | nenhum necessário | scheduler/state ausente |
| A0105 | NeoForge Post + RPG attribute runtime | root receipt provider-native | state/attribute consumer ausente |
| A0106 | NeoForge Pre + RPG mitigation/state | Epic Fight apenas por DamageSource causal | consumer/state ausente |
| A0107 | futuro Epic Fight impact/Stamina adapter | RPG transaction | A0093 + P-0035 não canônico |
| A0108 | RPG physical mitigation + movement attribute | Epic Fight classification | A0100 unavailable |
| A0109 | futuro body-encumbrance + Stamina provider | RPG mitigation/KB | A0108 unavailable + provider ausente |
| A0110 | futuro durability seam provider-native | RPG RNG/purchase | P-0036 sem hook seguro |

## Baselines promovidos para o próximo gate

Após a disposição acima, os próximos checkpoints documentais tornam-se:

- RPG Skill Tree: `2e1c5b62f89d2311eb645882e3547944d0f68869`
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `5a25b03a23ae81c111bbe1d5c23f85d8abd066ec`
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`

Esses SHAs são checkpoints de comparação, não promoção de conteúdo preparatório. No próximo lote, `main` + `plans/STATUS.md` frescos prevalecem novamente.
