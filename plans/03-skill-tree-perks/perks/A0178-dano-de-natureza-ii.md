# A0178 — Dano de Natureza II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A identidade desta notable é um combo NATURE spell→spell dependente de um estado de controle real, identificável e modificável pelo provider. Slowness, vegetação ou semântica de “root” não substituem esse contrato.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81199f9ff958796a3123`.

## Contrato de gameplay

- ARCANE/NATURE; camada 5; Notable; 1 rank; 2 PP.
- Dependências: A0177 rank ≥3 + Nature Mastery ≥20.
- Um primeiro spell NATURE direto deve aplicar um `nature_control_state_id` explicitamente allowlisted e modificável.
- Abre janela por alvo de 120 ticks (6 s).
- Um segundo spell NATURE **direto e de `spell_id` diferente** contra o mesmo alvo dentro da janela recebe `×1,15` no componente NATURE direto.
- O mesmo commit pode prolongar **somente o estado preparado** em até 20 ticks (1 s), sem ultrapassar duração/cap nativo exposto pelo provider.
- Cooldown interno: 140 ticks (7 s), iniciado apenas no consumo bem-sucedido.

## Blockers canônicos

1. `DIRECT_MAGIC_OUTCOME_V1` — caster/owner, `action_id/outcome_id`, DIRECT vs derived, `spell_id`, alvo e elemento NATURE;
2. `NATURE_CONTROL_RECEIPT_V1` — prova `state_id`, alvo, origem, instância, duração atual, duração/cap máximo e capacidade segura de modificação.

Iron's 3.16.3 possui `RootSpell`/`RootEntity`, mas isso **não** transforma genericamente qualquer root/ride state em `nature_control_state_id`. Adapter explícito é obrigatório.

## Pipeline futuro obrigatório

`primeiro direct NATURE spell -> DIRECT_MAGIC_OUTCOME_V1 -> provider control adapter cria receipt allowlisted -> armar janela alvo/state/spell por 120t -> segundo direct NATURE spell diferente -> validar mesmo alvo + estado ainda válido -> ×1,15 no componente NATURE -> estender state ≤20t sem exceder cap nativo -> commit atômico -> iniciar CD 140t`.

## Regras de identidade

- mesma `spell_id` não ativa;
- estado precisa existir realmente e ser modificável;
- o bônus não cria novo estado de controle;
- somente o estado preparado pode ser estendido;
- extensão não ultrapassa o máximo nativo;
- se o state expirar/remover antes do segundo spell, a janela não concede benefício.

## Exclusões

Não inferir controle NATURE por:

- Slowness/imobilização genérica;
- veneno;
- vegetação/folhas/vinhas próximas;
- entidade montada/ride state sem adapter;
- partícula/cor/nome textual;
- summon/minion;
- DoT/derived outcome;
- automação/fake player.

## Deduplicação e anti-abuso

- uma janela/receipt por combinação causal aprovada;
- um commit A0178 por `outcome_id`;
- cooldown só começa no commit elegível;
- reaplicações/ticks de controle não concedem Mastery;
- não estender estado por mais de 20t nem além do cap provider;
- logout/reload/dimensão devem limpar ou restaurar estado apenas se o lifecycle canônico possuir identidade suficiente.

## Fail-closed

Enquanto blockers/dependência faltarem:

- compra falha antes do gasto;
- rank legado unavailable vale 0 PP e permanece reembolsável/migrável;
- não degradar para ×1,15 em qualquer alvo lento;
- não criar `root/snare` universal.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não mapear `RootSpell` automaticamente, não usar Slowness como substituto e não criar receipt local específico da perk.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable rank = 0 PP e reembolsável/migrável;
3. primeiro spell apenas prepara, não recebe combo;
4. mesma `spell_id` negativa;
5. spell diferente no mesmo alvo dentro de 120t = ×1,15;
6. outro alvo/expired state/expired window negativos;
7. extensão ≤20t e nunca acima do cap nativo;
8. cooldown 140t só após commit;
9. Slowness/poison/vegetação/RootEntity sem adapter negativos;
10. dedup/lifecycle/provider mismatch fail-closed.
