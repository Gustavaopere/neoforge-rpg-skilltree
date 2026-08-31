# A0092 — Resistência Física

## Estado

- **Design:** APROVADO após hardening de classificação em 2026-08-31.
- **Notion:** `3c569db9-f0db-8100-b1b3-da080ac98ef8`; mutado e re-fetch PASS.
- **Runtime observado:** aplicação defensiva já existe em `LivingIncomingDamageEvent`, condicionada à tag `rpgskilltree:physical`.

## Contrato canônico

- Gateway VITALITY + A0089 Couro Endurecido ≥2.
- 4 ranks: −2% de dano físico elegível por rank, máximo próprio −8%.
- A classificação física é causal/data-driven; não é inferida por arma equipada, namespace, VFX ou aparência.
- A0092 não substitui Armor/Toughness nem cria resistência universal.

## Evidência runtime

`A0081A0100CombatEvents` consulta a `TagKey<DamageType> rpgskilltree:physical` no PRE de dano recebido. O seed atual inclui `player_attack`, `mob_attack`, `mob_attack_no_aggro`, `arrow`, `trident`, `thrown` e `sting`. Modded damage fora da tag não é automaticamente físico.

## Cobertura de providers

- Minecraft/NeoForge: owner do evento e dos DamageTypes vanilla classificados.
- Epic Fight e mods de combate só entram quando sua fonte preserva/declara classificação física segura no mesmo contrato.
- Apothic/Simply e equipamentos podem alterar o dano upstream, mas não recebem classificação por presença do mod.
- Magic/Shroud/Arcane Backlash, hazards ambientais, máquinas e resource costs ficam fora salvo adapter semântico explícito.

## Pendências para Chat 2

- **P-A0092-01:** testar tag física vanilla, dano modded não classificado e adapters explícitos sem double-application.
- **P-A0092-02:** testar composição com Armor/Toughness, cap −8%, fontes não físicas e lifecycle de rank.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | VITALITY + A0089≥2. |
| Integração global | PASS | pipeline defensivo único. |
| Qualidade/identidade | PASS | resistência física explícita, não universal. |
| Topologia | PASS | VITALITY/PHYSICAL_RESISTANCE. |
| Especializações | PASS | PP por semântica de resistência física. |
| PT-BR | PASS | classificação e teto explícitos. |
| Notion | PASS | mutação + re-fetch PASS. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | tag/adapter explícito; desconhecido fail-closed. |

Os 18 critérios passam no design com classificação física governada.