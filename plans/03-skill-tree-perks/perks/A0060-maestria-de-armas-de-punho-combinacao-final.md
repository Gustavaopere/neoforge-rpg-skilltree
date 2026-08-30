# A0060 — Maestria de Armas de Punho — Combinação Final

## Estado

- **Design:** APROVADO; fail-closed já estava correto e lifecycle foi explicitado após review.
- **Notion:** `3c569db9-f0db-813c-a90b-d92ed2f1ed75`.
- **Runtime:** NÃO CONFIRMADO; capstone permanece inativo por falta de heavy/finalizer receipt.

## Contrato canônico

- A0058 ≥2 + A0059 ≥1 + `combat:fist` ≥80 + gateway `combat_fist`.
- Em 5 Sequências, o próximo heavy/finalizer FIST confirmado consome todas as cargas e recebe +18% dano físico elegível e +25% Impact.
- Se acertar alvo hostil válido, pode recuperar 15% da soma de Stamina **realmente debitada** nas cinco ações que geraram a sequência, somente por receipts causais pós-consumo.
- Sem receipt de Stamina, omitir só a restituição; nunca estimar por barra, config, hunger/exhaustion ou animation timing.
- Cooldown 8/7/6 s para Mastery 80/90/100.
- Rank loss/respec/rules reload que invalide A0060 limpa cooldown/reserva específicos do capstone; Sequência pertence a A0058 e segue o lifecycle/reconciliação daquele owner.

## Evidência runtime

`beforeFistHeavy(...)` possui matemática de A0060, cooldown e fallback de Stamina em `0.0`, explicitamente porque não há receipt causal seguro. O adapter Epic Fight não chama essa rota enquanto não existir heavy/finalizer receipt inequívoco.

O segundo review da PR #249 tornou explícita a necessidade de limpar estados transientes por perda de rank/pré-requisito. Portanto, mesmo depois de o heavy receipt existir, cooldown/reservas de A0060 não podem atravessar respec/reload e reaparecer numa futura recompra.

## Pendências para Chat 2

- **P-A0060-01:** integrar heavy/finalizer receipt provider-native e liberar o capstone apenas então.
- **P-A0060-02:** manter restituição de Stamina fail-closed até existir ledger causal pós-consumo por cada uma das cinco ações; cada receipt só pode ser reclamado uma vez.
- **P-A0060-03:** A0060 usa a ledger única `combat:fist`; corrigir producer/architecture de A0055 antes de considerar gate 80 alcançável.
- **P-A0060-04:** limpar cooldown/reserva própria de A0060 em rank loss, respec ou rules reload que invalide o terminal; Sequência continua sob lifecycle A0058.

## Boundaries

Backlash, procs, summons/companions e hazards não geram Sequência, heavy receipt ou Stamina ledger. Punchy é visual/compat.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0058 ≥2 + A0059 ≥1 + `combat:fist` 80 + gateway; sem heavy receipt o capstone não ativa/consome e sem infrastructure A0055 o gate não é alcançável. |
| 2. Integração global | **PASS** | Stamina permanece recurso real do Epic Fight e só usa receipts pós-consumo; hunger/exhaustion/mana não substituem; dano/Impact seguem pipelines canônicos. |
| 3. Qualidade e identidade | **PASS** | Capstone conclui a fantasia de combo: Sequência máxima + finalizador confirmado + dano/Impact e refund causal opcional; não é aumento numérico banal. |
| 4. Ramificação, distância e topologia | **PASS no design** | Camada 4 terminal com dependências convergentes A0058/A0059 e Mastery 80; posição é coerente com Capstone. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/ARMAS_DE_PUNHO`; só satisfaz Gate C por mapeamento explícito e não cria classe automática. |
| 6. PT-BR | **PASS** | Nome, efeito, requisitos e mensagens conceituais em PT-BR; IDs/API técnicos permanecem em inglês no dossiê. |
| 7. Notion completo | **PASS** | Campos pertinentes completos; lifecycle de cooldown/reserva foi adicionado após review e re-fetch confirmou persistência em 2026-08-30. |
| 8. NeoVitae | **PASS** | Nenhuma dependência residual. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/RPG/WoM/Punchy e own-projects/Mobstein foram avaliados; sem provider seguro de heavy/Stamina receipt, componentes permanecem fail-closed. |

Os 18 critérios técnicos cumulativos passam **no design**; heavy/finalizer e Stamina refund não são fingidos como disponíveis e permanecem bloqueados até receipts causais reais.

## Notion

Fetch fresco inicial sem drift. Após segundo review da PR #249, `Fallback` e `Regra` receberam lifecycle obrigatório de cooldown/reserva em rank loss/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.
