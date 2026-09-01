# A0100 — Anti-Crítico

## Estado

- **Chat 1:** DESIGN APROVADO / `UNAVAILABLE_NODE` FAIL-CLOSED.
- **Notion:** `3c569db9-f0db-8184-bbc8-eb9162cc3d1b`; fetch fresco confirmado.
- **Domínio:** VITALITY; Camada 2; Função Ramo.
- **Ranks:** 4; custo 1 PP/rank.
- **Dependências:** A0090 Têmpera ≥2 + Gateway VITALITY.

## Contrato canônico

- A0100 só atua quando o mesmo evento causal recebido fornece, server-side: `legitimatelyCritical=true`, `baseDamage` e `additionalCriticalDamage` separados.
- Reduz apenas a parcela adicional crítica em 4% por rank, até 16% relativos sobre essa parcela.
- Fórmula: `final = baseDamage + additionalCriticalDamage × (1 − 0.04 × rank)` antes das demais etapas defensivas canônicas aplicáveis.
- Golpe comum mantém `baseDamage + additionalCriticalDamage` sem redução de A0100.

## Fail-closed estrutural / aquisição

- Nenhum pipeline incoming auditado expõe atualmente classificação crítica + decomposição base/parcela adicional para o mesmo evento.
- Portanto A0100 permanece `UNAVAILABLE_NODE`: não pode ser comprado nem ranqueado enquanto o binding obrigatório estiver ausente.
- O purchase server-side deve fornecer `requirementsSatisfied=false` para A0100 nesse estado, rejeitando antes de qualquer gasto de PP.
- Manter apenas `FAIL_CLOSED_A0100=true` no runtime de dano não é suficiente se o node continuar comprável.

## Proibições

- Não estimar parcela crítica a partir do dano final, multiplicador típico, diferença entre hits, animação, som ou HUD.
- Não inferir crítico recebido a partir dos atributos ofensivos de critical chance/damage do atacante.
- O resolvedor crítico ofensivo do próprio RPG não prova incoming critical.
- Não substituir por redução universal, physical resistance, armor/toughness ou redução total do golpe.

## Provider / authority

- RPG Skill Tree: consumer apenas quando existir receipt incoming decomposed real; authority do purchase gate enquanto indisponível.
- Epic Fight 21.17.3.1 e Apothic Attributes 2.10.1: só podem participar por adapter explícito/versionado que forneça classificação e decomposição causal do dano recebido.
- Pufferfish's Attributes 0.8.3 não é provider presumido.
- Provider ausente/incompatível/inconclusivo mantém fail-closed.

## Evidência atual e divergência preparatória

- `A0081A0100CombatPolicy.antiCriticalDamage(...)` já modela a fórmula pura e exige `legitimatelyCritical` + `decomposedByCausalResolver`.
- `A0081A0100CombatEvents` mantém `FAIL_CLOSED_A0100=true` e não aplica branch incoming crítica.
- Isso é correto como proteção runtime, mas ainda não prova indisponibilidade no purchase path.
- **P-A0100-01:** integrar disponibilidade ao gate autoritativo de compra (`requirementsSatisfied=false` sem binding real).
- **P-A0100-02:** preservar ausência de heurística; nenhum adapter deve ser inventado.
- **P-A0100-03:** quando/SE existir provider real, deduplicar por root/evento e reduzir apenas a parcela adicional.

## Dedup / ordem / anti-abuso

- Um único receipt causal por dano recebido pode alimentar A0100.
- A parcela crítica adicional é reduzida no máximo uma vez.
- O resultado de A0100 entra no pipeline antes das demais etapas defensivas previstas, sem reaplicar classificação.
- Cancelamento/dano zero não produz side-effect nem estado persistente.
- A0100 é stateless; lifecycle não deve criar ledger próprio.

## Testes obrigatórios Chat 3

1. provider/binding ausente: purchase rejeitado sem gasto de PP/rank;
2. fórmula pura preserva base e reduz somente extra em 4/8/12/16%;
3. `legitimatelyCritical=false` não reduz;
4. sem decomposição causal não reduz;
5. heurísticas por dano final/multiplicador/animação não existem;
6. callbacks duplicados não reduzem a parcela duas vezes;
7. A0090≥2 + Gateway VITALITY continuam obrigatórios quando o node futuramente estiver disponível;
8. Epic Fight/Apothic ausente ou versão incompatível permanece fail-closed;
9. nenhum dano comum recebe redução universal;
10. build/dedicated-server/CI com provider absent fixture.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0090≥2 + VITALITY; availability adicional obrigatória. |
| Integração global | PASS | Não reutiliza crítico ofensivo como incoming. |
| Qualidade/identidade | PASS | Anti-pico crítico específico. |
| Topologia | PASS | VITALITY/ANTI_BURST. |
| Especializações | PASS | PP conta apenas conforme mapeamento semântico futuro. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Fail-closed explícito. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS condicionado | Nenhum receipt incoming decomposed confirmado; node indisponível. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0100-01 purchase fail-closed implementado pelo Chat 2
- [ ] P-A0100-02 heurísticas ausentes comprovadas
- [ ] P-A0100-03 adapter somente se houver receipt real
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: purchase rejection/fórmula/dedup
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
