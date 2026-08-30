# Auditoria retroativa de integração — A0031–A0040

## Escopo

- **Lote exato:** A0031–A0040, 10 perks consecutivas.
- **Maças:** A0031–A0036.
- **Foices:** A0037–A0040.
- **Providers retroauditados:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4, além dos providers canônicos do contrato (Minecraft/NeoForge, Epic Fight 21.17.3.1, Weapons of Miracles/Epic Fight Compat quando explicitamente classificados).
- **Fora de escopo:** A0041+ e implementação runtime neste Chat 1.

## Fontes

- Critérios obrigatórios e guias consolidados anexados.
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md` e matriz provider→árvore.
- Catálogo Mestre Notion com fetch fresco individual A0031–A0040.
- Runtime real do bloco A0021–A0040.
- `main` fresca dos quatro projetos próprios e Mobstein 5.4.4 documentado nos guias/CurseForge.

## Delta provider→árvore

### RPG Skill Tree

- `main` avançou durante o ciclo para `492a4d28ee4b57a7e43645f623c4d07c08ac3361` por Stage 11.01 de itemização.
- Stage 11.01 é **PROGRESSÃO NATIVA AUTORITATIVA** do domínio de itemização: identidade persistente, rank, ItemPower, PREFIX/SUFFIX/INFIX, rolls, query e mutation authority.
- O próprio contrato afirma que projeções de atributos/efeitos ficam para subplanos posteriores.
- **Disposição para A0031–A0040:** `SEM HOOK SEGURO` para integração direta agora. Nenhuma perk lê `RolledModifier`/`ItemizationSnapshot` para inventar dano, crítico, cadence, Trauma ou Marca. Quando projeções futuras existirem, deverão convergir pelos atributos/pipelines canônicos sem double-processing.
- Runtime A0021–A0040 continua authority dos estados Trauma/Sunder/Reaping e da deduplicação de root action.

### Volcanoes

- `main@7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`; sem delta novo desde o lote anterior.
- Prospeção/hazards/geologia são `NÃO DEVE SER INTEGRADO` às perks MARTIAL A0031–A0040.
- Temperatura, pressão, gases, O₂ e geologia não classificam MACE/SCYTHE, proteção física, heavy receipt, crítico ou Marca.

### Enshrouded

- `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`; sem delta novo pertinente.
- Shroud, Exposure, Madness, Flame, Story/Sanctuary e MagicResistance permanecem authorities próprias.
- Esses estados não são Armor física, heavy receipt, critical receipt ou classificação de arma.

### Black Arcana

- `main@73c14ce55ff918bb8a81daeb99a352607ef11064`; sem delta MARTIAL novo.
- `ARCANE_BACKLASH` permanece terminal: não crita, não gera Mastery/Trauma/Marca e não ativa capstones.
- Arcane Resistance/Corruption Resistance/Arcane Strain não qualificam proteção física de A0034/A0035.

### Mobstein 5.4.4

- Ressurreição, corpses/órgãos, experimentos, allies/bodyguards, estruturas e Witherstein permanecem Mobstein-owned.
- Ataques diretos do jogador contra entidades Mobstein são `COBERTO POR SISTEMA UNIVERSAL` quando a família/receipt MARTIAL é válido.
- Companion-owned damage não herda Mastery, crítico, Trauma, Sunder, heavy receipt ou Marca do dono.
- Witherstein é oficialmente documentado como boss, mas as fontes auditadas não provaram registry id nem membership em `Tags.EntityTypes.BOSSES` na 5.4.4. A atenuação de boss de A0035/A0036 para ele fica `SEM HOOK SEGURO` até verificação versionada; não inferir por nome.

## Matriz do lote

| Perk | Resultado | Boundary/pendência principal |
|---|---|---|
| A0031 | APROVADA após correção | `P-A0031-01`: remover tag MACE paralela; `P-A0031-02`: Mastery anti-farm |
| A0032 | APROVADA | cadence provider-native; depende da família MACE segura |
| A0033 | APROVADA + boundary | crítico único; Backlash/companions inelegíveis |
| A0034 | APROVADA | fallback Armor física funcional; estados mágicos/ambientais não qualificam |
| A0035 | APROVADA | modifier Armor físico funcional; `P-A0035-01` boss Mobstein classification |
| A0036 | APROVADA / não confirmada | `P-A0036-01` heavy receipt; `P-A0036-02` aplicar Descompasso; mastery anti-farm |
| A0037 | APROVADA após correção | `P-A0037-01`: remover tag SCYTHE paralela; `P-A0037-02`: Mastery anti-farm |
| A0038 | APROVADA | cadence provider-native; depende de família SCYTHE segura |
| A0039 | APROVADA + boundary | crítico único; Backlash/companions inelegíveis |
| A0040 | APROVADA | Marca/maturação existentes; design já exclui procs/companions/duplicatas |

## Correção sistêmica — famílias e Mastery

### MACE

- Maça vanilla usa identidade exata `minecraft:mace`; não precisa da tag `rpgskilltree:maces`.
- Armas externas só entram por categoria/capability `mace` ou mapping versionado explícito.
- `combat:mace`: +10 uma única vez por tipo hostil inédito persistido em `DiscoveryProgress`; 60 = 6 tipos, 80 = 8 tipos.

### SCYTHE

- Não existe fallback vanilla por aparência/enxada.
- Apenas categoria/capability `scythe` ou mapping versionado explícito.
- `rpgskilltree:scythes` deixa de ser classificador; o tag atual estar vazio não o torna boundary canônico.
- `combat:scythe`: +10 uma única vez por tipo hostil inédito; 60 = 6 tipos.

O runtime atual `A0021A0040MasteryPolicy` ainda usa `CONFIRMED_HIT_XP=3` para MACE/SCYTHE; Chat 2 deve reconciliar.

## Pendências destinadas ao Chat 2

### P-A0031-01 — classificação MACE
Remover a tag paralela como classificador. Preservar `minecraft:mace` por identidade exata e externos por provider-native/mapping seguro.

### P-A0031-02 — Mastery MACE anti-farm
Substituir 3 XP/hit por `DiscoveryProgress` +10 por tipo hostil inédito; gate60=6, terminal A0036 gate80=8.

### P-A0035-01 — Witherstein/boss classification
Verificar Mobstein 5.4.4. Se `Tags.EntityTypes.BOSSES` não cobrir Witherstein, só adicionar mapping versionado com registry id realmente comprovado. Até lá, não declarar boss-half confirmado para Witherstein.

### P-A0036-01 — heavy receipt MACE
Adapter atual envia `heavy=false`. Integrar receipt inequívoco provider-native; sem heurística de dano/animação/arma lenta.

### P-A0036-02 — Descompasso runtime
Policy produz `applyBonebreaker`, mas não existe caller no adapter. Aplicar −8% dano físico causado e −10% movement por 3 s, boss half, cooldown 12/11/10 e lifecycle seguro. Sem ambos os debuffs, A0036 permanece fail-closed.

### P-A0037-01 — classificação SCYTHE
Remover/desativar tag paralela. Somente Epic Fight/mapping explícito; unknown = fail-closed.

### P-A0037-02 — Mastery SCYTHE anti-farm
Substituir 3 XP/hit por `DiscoveryProgress` +10 por tipo hostil inédito; gate60=6.

## A0034/A0035 — proteção física

O runtime atual usa `target.getArmorValue() > 0` para A0034, exatamente o fallback canônico. A0035 aplica modifier transient em `Attributes.ARMOR`, com expiry e cleanup. Rotas adicionais de guard/posture só podem ser ativadas quando provider-native; não são blockers do fallback Armor.

## A0040 — Marca da Ceifa

- O runtime aplica a Marca por hit direto SCYTHE deduplicado.
- Marca nova aplicada abaixo de 50% começa imatura, coerente com “cair abaixo de 50% enquanto marcada”.
- `LivingDamageEvent.Post` atualiza a maturidade para qualquer dano real posterior, sem transferir autoria desse dano para a perk.
- Reapply renova uma única marca jogador→alvo; não duplica.

## Notion

- Fetch fresco: A0031–A0040 = 10/10.
- Alteradas e re-fetched: A0031, A0033, A0034, A0035, A0036, A0037, A0039 = **7/7 PASS**.
- Sem mutação: A0032, A0038, A0040; contratos já suficientes.

## Nove eixos / 18 critérios

- Dependências/gates: PASS no design.
- Integração global/authority: PASS com boundaries explícitos.
- Identidade/topologia/especialização/PT-BR: PASS.
- Notion + re-fetch: PASS.
- NeoVitae: ausente.
- Provider→árvore: PASS com `SEM HOOK SEGURO` onde apropriado.
- Critérios técnicos: provider-native first, no mecânica inventada, server authority, causal root action, dedup, recurso único, crítico único, Mastery anti-farm, lifecycle, actor→target isolation, fallback sem mudar identidade e implementabilidade/testabilidade foram aplicados.

## Fechamento

**A0031–A0040 — LOTE FECHADO NO DESIGN.**

Chat 2 deve implementar/corrigir somente os contratos e pendências acima. **A0041+ não foi iniciada.**
