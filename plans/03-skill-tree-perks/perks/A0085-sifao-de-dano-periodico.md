# A0085 — Sifão de Dano Periódico

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability/autoria em 2026-08-31.
- **Notion:** `3c569db9-f0db-8148-9fef-e7b4a708330b`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** coeficiente existe no core, mas não há producer de autoria persistente + pulse identity; A0085 fica **indisponível/não comprável**.

## Contrato canônico

- Gateway OCCULT + ao menos uma fonte de DoT reconhecida + adapter seguro.
- 3 ranks: 0,35% / 0,70% / 1,05% do dano periódico pós-mitigação.
- Na aplicação do efeito é registrado um `applicationId`, owner jogador, alvo e source key. Cada tick elegível possui `pulseId` causal e resolve no máximo uma vez no `SustainResolver`.
- Cap global compartilhado: 3% da vida máxima/20 ticks; overkill e missing health continuam limitando pagamento.

## Receipt obrigatório

O adapter por provider deve produzir equivalente a:

`PeriodicSustainReceipt(applicationId, pulseId, ownerPlayerUuid, targetUuid, providerSourceKey, postMitigationDamage)`.

`owner` do summon, proximidade do jogador, efeito aplicado por área, namespace ou último atacante não substituem esse receipt.

## Cobertura de providers

- Goety 3.1.4: somente DoT cuja aplicação direta pelo jogador e pulsos possam ser correlacionados; dano de servos/summons não herda autoria.
- Malum 1.8.2: efeitos spirit/occult entram apenas por aplicação e pulse provider-native comprovadas.
- Eidolon: Repraised 0.5.0.2: mesma exigência de autoria persistente.
- Iron's 3.16.3 / Ars Nouveau 5.13.1: um hit direto pertence a A0083/A0084; somente efeitos explicitamente periódicos e com owner persistente podem entrar em A0085.
- Vampirism 1.10.12: heal nativo só entra se correlacionável ao mesmo pulso.
- Black Arcana `ARCANE_BACKLASH`, Enshrouded/Shroud, Volcanoes hazards, fogo/lava/ambiente e máquinas/turrets/fake players: inelegíveis.

## Lifecycle

Ledger de application/pulse deve ser bounded e removida em expiração do efeito, morte/remoção/unload do alvo, logout/dimensão/respawn do ator, rank loss, respec, rules reload e shutdown. Reaplicação deve criar/renovar identidade conforme semântica provider-native sem ressuscitar pulses antigos.

## Evidência runtime

`A0081A0100CombatPolicy` contém A0085, mas `A0081A0100CombatEvents` não captura dano periódico nem mantém owner ledger. Logo nenhuma fonte atual pode chegar ao `SustainResolver` com as provas exigidas.

## Pendências para Chat 2

- **P-A0085-01 BLOQUEANTE:** unavailable-node invariant até existir ao menos um adapter com owner persistente + application/pulse identity.
- **P-A0085-02:** definir interface canônica de receipt e integrar providers um a um; sem heurística por namespace.
- **P-A0085-03:** lifecycle bounded de aplicações/pulsos e dedup de refresh/reapply/multi-target.
- **P-A0085-04:** excluir summons/companions/hazards/Backlash/tech damage e impedir owner inheritance indireto.
- **P-A0085-05:** testes DoT direto vs summon, expiração/unload, multi-target, duplicate pulse, native heal, cap e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | OCCULT + fonte DoT + receipt causal. |
| Integração global | PASS | um SustainResolver, sem bucket paralelo. |
| Qualidade/identidade | PASS | sustain de DoT autorado, não de ambiente/summons. |
| Topologia | PASS | OCCULT/SUSTAIN, Camada 4. |
| Especializações | PASS | PP por mapeamento semântico. |
| PT-BR | PASS | termos de aplicação/pulso documentados. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Goety/Malum/Eidolon/Iron's/Ars somente com receipt real. |

Os 18 critérios passam **no design** porque ausência de producer bloqueia aquisição em vez de gerar no-op.