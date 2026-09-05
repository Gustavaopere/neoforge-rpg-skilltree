# A0152 — Potência Crítica Mágica

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE**, transitivo de A0151.

Além do producer mágico direto, esta perk exige correlação estável de ação/spell e estado crítico por ação. Esses contracts ainda não existem na `main`.

## Contrato

- ARCANE; camada 4; Notable; 1 rank; 2 PP.
- Pré-requisitos: A0151 ≥3 + A0144 ≥3 + Gateway ARCANE.
- Crítico mágico direto elegível arma `Ressonância Crítica` por 100 ticks, uma vez por `action_id`.
- CD interno de 160 ticks inicia ao armar.
- Próxima ação mágica direta elegível com `spell_key` diferente pode consumir a janela no commit.
- Todos os outcomes diretos dessa ação recebem +8 pp de chance; críticos recebem +0,20 no multiplicador.

## Authority e evidência

Ars 5.13.1 expõe `SpellCastEvent` e `SpellContext`; attachments do contexto podem carregar um future `action_id`, e spell parts possuem identidade registral para `spell_key`. Iron's expõe eventos de cast/damage, mas não foi provado contexto genérico que siga todos os outcomes derivados de uma mesma ação.

O CriticalService atual cacheia decisão booleana por root e não publica `MAGIC_CRITICAL_ACTION_STATE_V1`.

## Availability

Exige A0151 capability-eligible + `MAGIC_ACTION_CORRELATION_V1` + `MAGIC_CRITICAL_ACTION_STATE_V1`. Ausência de qualquer contrato torna o node indisponível.

## Transação

Armar é state creation; consumo da janela ocorre somente quando a segunda ação distinta produz outcome direto confirmado. Cancelamento/ausência de outcome faz rollback do candidate. A perk não pode se auto-rearmar com a própria ação consumidora.

## Exclusões

- mesma `spell_key`;
- derived/DoT/summon/automation;
- comparação por nome traduzido/display text;
- consumo em pre-cast sem outcome;
- múltiplas janelas para outcomes irmãos da mesma ação.

## Handoff Chat 2

Não implementar como contador por tick ou por DamageSource solto. Preservar action correlation única e lifecycle bounded/persistente conforme contrato.

## Testes Chat 3

1. indisponibilidade transitiva de A0151;
2. janela exatamente 100 ticks e lockout 160 ticks;
3. mesma spell não consome; spell diferente consome no commit;
4. múltiplos outcomes diretos da ação compartilham o mesmo bônus;
5. cancelamento/zero outcome rollback;
6. no self-rearm, dedup, logout/restart/respec/rules reload.