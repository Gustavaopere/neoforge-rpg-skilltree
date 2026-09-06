# A0067 — Firmeza Ofensiva

## Estado

- **Design:** APROVADO após correção de disponibilidade/fail-closed em 2026-08-31.
- **Notion:** `3c569db9-f0db-8153-9b28-c383e9fde302`; Gate, Hook, Fallback, Provider/Mods e Regra corrigidos; re-fetch pós-escrita PASS.
- **Runtime observado:** IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA; node estruturalmente indisponível e ranks persistidos legados são mascarados no boundary server-side.

## Contrato canônico

- Gateway MARTIAL + A0066 Impacto Marcial ≥ 1 rank.
- 4 ranks, 1 ponto por rank quando o binding existir.
- +4% de resistência à interrupção durante ataque por rank, máximo próprio de +16%.
- O efeito só pode existir durante a janela server-authoritative de um ataque físico elegível.
- **Estado atual:** sem lifetime seguro de attack window + cleanup provider-native, A0067 deve ficar indisponível/não comprável.

## Provider / authority / boundary

- Epic Fight 21.17.3.1 é o provider candidato.
- `EpicFightAttributes.STUN_ARMOR` só pode ser usado como backend transitório se um boundary provar início/fim/cancelamento da janela ofensiva e permitir remoção determinística.
- Weapons of Miracles 2.0.176 só pode participar em ação/skill concreta que exponha boundary compatível e deduplicável.
- RPG Skill Tree não pode manter STUN_ARMOR permanente nem inferir janela por timing visual.

## Evidência runtime

`A0061A0080CombatPolicy.offensiveInterruptionResistanceFraction(...)` contém somente a matemática. A PR #391 adiciona `A0067` a `CombatPerkAvailabilityRuntime.isCatalogCodeAvailable(...) = false` e faz `A0061A0080RuntimeState.ranks(...)` passar os ranks persistidos por `CombatPerkAvailabilityRuntime.effectiveRanks(...)`, eliminando ghost rank ativo no boundary server-side.

## Fallback e fail-closed

- Sem binding seguro, **node indisponível/não comprável**.
- Não aceitar silent no-op purchase, rank fantasma ou gasto de ponto sem efeito.
- Não converter para knockback resistance, redução de dano, STUN_ARMOR permanente, super armor global ou imunidade a controle.

## Lifecycle obrigatório quando houver hook

Qualquer modificador transitório deve ser removido em término do ataque, cancelamento, stagger, morte, logout, troca de dimensão, respawn, respec/rank loss e rules reload que invalide o node.

## Validação Chat 3

- `P-A0067-01` BLOQUEANTE: **RESOLVIDA na PR #391** pelo unavailable-node invariant em `CombatPerkAvailabilityRuntime`.
- Ranks persistidos legados são mascarados por `effectiveRanks(...)` antes das policies runtime; não há rank fantasma ativo.
- O teste `A0061A0070Chat3CoverageJUnitTest.a0067IsStructurallyUnavailableAndLegacyRankIsMasked` prova catálogo indisponível, rank efetivo zero e ausência do efeito.
- O teste `runtimeRanksMasksUnavailablePersistedRankAtServerBoundary` prova o masking também através de `A0061A0080RuntimeState.ranks(...)`.
- `P-A0067-02` permanece uma expansão futura condicionada a receipt provider-native estável; não bloqueia o estado fail-closed atual.
- `P-A0067-03` fica encerrada para os invariants de indisponibilidade/masking auditados. Nenhuma STUN_ARMOR permanente foi introduzida.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS no design | A0066 ≥1; disponibilidade do binding é gate técnico obrigatório. |
| 2. Integração global | PASS | Não confunde interrupção com knockback/dano/defesa global. |
| 3. Qualidade/identidade | PASS | Defesa ofensiva contextual, não atributo genérico permanente. |
| 4. Topologia | PASS | Camada 3, `MARTIAL/CORE_CONTROL`. |
| 5. Especializações | PASS | Universal MARTIAL; WoM apenas por boundary concreto. |
| 6. PT-BR | PASS | Contrato player-facing em PT-BR. |
| 7. Notion completo | PASS após correção | Persistência confirmada por re-fetch. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight/WoM avaliados; sem hook seguro, node indisponível. |

Os 18 critérios técnicos cumulativos passam **no estado fail-closed implementado e validado pelo Chat 3**. A perk não é confirmada como jogável enquanto não surgir um binding ofensivo provider-native seguro; o comportamento atual é o contrato correto.