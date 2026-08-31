# Auditoria Chat 1 — A0101–A0110

> Este arquivo registra o fechamento parcial inicial do lote que foi posteriormente ampliado pelo usuário para A0101–A0150. O fechamento canônico do ciclo passa a ser a auditoria consolidada A0101–A0150; este documento permanece como evidência da primeira tranche já auditada.

## Resultado resumido

- A0101–A0106: design aprovado com boundaries NeoForge/RPG implementáveis.
- A0107–A0110: design aprovado em fail-closed estrutural; aquisição deve permanecer indisponível enquanto as capabilities exigidas não existirem.
- Nenhuma perk pode aceitar PP em estado no-op.

## Achados técnicos principais

### Damage pipeline NeoForge 1.21.1

`LivingIncomingDamageEvent` ocorre após invulnerability checks e antes das reduções. `LivingDamageEvent.Pre` ocorre após armor/magic/mob-effect reductions e antes da perda de vida; `LivingDamageEvent.Post` representa dano efetivamente aplicado. Esse pipeline permite um `DamageMitigationResolver` RPG-owned sem depender de um provider externo hipotético.

### Durabilidade

`ItemStack#hurtAndBreak` chama `Item#damageItem` antes de `EnchantmentHelper.processDurabilityChange`; portanto A0110 não pode usar `damageItem` como boundary pós-Unbreaking. Sem adapter posterior à prevenção e anterior ao decremento, A0110 permanece fail-closed.

### Dependências transitivas

- A0107 herda a indisponibilidade de A0093 e ainda requer bridge segura de impacto→stamina.
- A0108 herda A0100, que não possui critical-received authority suficiente no runtime atual.
- A0109 herda A0108/A0100 e continua sem provider de encumbrance corporal.

## Projetos próprios — delta da tranche

- RPG Skill Tree: avanço concorrente em A0021–A0030/Mastery/Compêndio não cria consumer defensivo novo para este range.
- Volcanoes: sem delta pertinente desde o baseline reconciliado da tranche.
- Enshrouded: avanço observado em áudio/partículas client-side; sem nova capability de progressão para este range.
- Black Arcana: forecast server-authored/read-only de Arcane Resistance; permanece distinto de resistência mágica genérica A0102 e não se torna authority de mitigação.

## Regra para implementação

O Chat 2 deve implementar exatamente os contratos aprovados. Capability ausente implica `availability=false`/fail-closed; não redesenhar para bônus genérico.
