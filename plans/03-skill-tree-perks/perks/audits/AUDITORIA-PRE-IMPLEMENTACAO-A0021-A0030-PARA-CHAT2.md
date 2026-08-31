# Auditoria pré-implementação — A0021–A0030 — Handoff para Chat 2

## Responsabilidade deste ciclo

Este documento é **auditoria/especificação**, não implementação.

- Lote exato: **A0021–A0030**.
- Quantidade: **10 perks consecutivas**.
- Este chat identifica e especifica pendências.
- **Chat 2 implementa** as pendências técnicas abaixo.
- **Chat 3 executa o fechamento/merge** conforme o fluxo atualizado pelo usuário.
- Nenhuma alteração de runtime/teste de implementação pertence a esta entrega.
- A0031+ está fora de escopo.

## Resultado 10/10

| Perk | Resultado da auditoria | Handoff |
|---|---|---|
| A0021 — Precisão com Adagas | nenhuma nova pendência técnica acionável | nenhum |
| A0022 — Ritmo das Sombras | nenhuma nova pendência técnica acionável | nenhum |
| A0023 — Ataque ao Ponto Cego | consumo/cooldown causalmente prematuros | `P-A0023-01` para Chat 2 |
| A0024 — Dança das Sombras | ativação e one-shot consumidos no PRE | `P-A0024-01` para Chat 2 |
| A0025 — Treino com Martelos I | nenhuma nova pendência técnica acionável | nenhum |
| A0026 — Treino com Martelos II | nenhuma nova pendência técnica acionável | nenhum |
| A0027 — Precisão com Martelos | nenhuma nova pendência técnica acionável | nenhum |
| A0028 — Abalo Crescente | blocker provider já conhecido permanece correto | `P-A0028-01` permanece aberta/fail-closed |
| A0029 — Quebra de Postura | blocker provider + consumo PRE latente | `P-A0029-01` permanece; implementar `P-A0029-02` |
| A0030 — Golpe Demolidor | blocker provider + consumo de janela PRE latente | `P-A0030-01` permanece; implementar `P-A0030-02` |

## P-A0023-01 — ABERTA PARA CHAT 2

### Defeito

A0023 debita 2 Fluxo e inicia cooldown por alvo no PRE, antes de o outcome confirmar dano efetivo. Cancelamento ou dano zero pode produzir perda/cooldown fantasma.

### Contrato de implementação

1. PRE valida arma/família, flank/rear, cooldown e disponibilidade de ≥2 Fluxo.
2. PRE **reserva**, mas não debita, exatamente 2 Fluxo por `rootActionId`.
3. Recursos já reservados não podem financiar outro root concorrente.
4. POST direto + hostil + dano efetivo >0 commita 2 Fluxo e inicia cooldown.
5. POST zero/cancelado/inválido libera a reserva e não inicia cooldown.
6. No mesmo hit, o consumer A0023 deve ocorrer antes do producer A0022: com 4 Fluxo, `4 → 2 → 3`.
7. Cleanup bounded para reservas abandonadas é obrigatório.

### Testes mínimos

- PRE não destrutivo;
- POST válido consome/inicia cooldown;
- POST zero/cancelado não consome/não inicia cooldown;
- dois roots concorrentes não usam os mesmos 2 Fluxo;
- ordem consumer→producer com A0022.

## P-A0024-01 — ABERTA PARA CHAT 2

### Defeito

A0024 consome 4 Fluxo e ativa Dança no PRE; o primeiro benefício lateral/traseiro também é consumido no PRE. Um hit sem dano efetivo pode gastar a ativação/one-shot.

### Contrato de implementação

1. PRE reserva 4 Fluxo + intenção de ativação por root; não ativa Dança ainda.
2. POST válido commita o gasto e ativa Dança pelo tempo canônico.
3. O primeiro bônus lateral/traseiro usa reserva separada por root e só é consumido após dano efetivo.
4. POST zero/cancelado libera reservas sem gastar Fluxo, ativação ou one-shot.
5. O mesmo recurso reservado não pode financiar A0023/A0024 concorrente.
6. No hit de ativação, A0024 commita antes de A0022 produzir: `4 → 0 → 1`.
7. A redução de stamina continua condicionada a receipt causal seguro; ausência do receipt omite apenas esse componente.

### Testes mínimos

- ativação PRE não destrutiva;
- ativação POST válida;
- cancelamento/zero preserva 4 Fluxo;
- one-shot lateral/traseiro preservado em zero/cancelamento;
- consumo único por ativação;
- ordem A0024→A0022.

## P-A0028-01 — PERMANECE ABERTA / FAIL-CLOSED

Epic Fight 21.17.3.1 não fornece receipt causal separado e seguro de guard/posture pressure para A0028. Não inferir por dano, Armor, stagger genérico, animação, tooltip ou efeito de Simply Swords.

## P-A0029-01 — PERMANECE ABERTA / BLOQUEANTE

Não existe heavy receipt inequívoco comprovado. `shouldChargeWeapon()`, combo/Weapon Innate, dano alto, arma lenta, impacto, charge time estimado, armor sunder/ignore e traits Simply não podem ser promovidos a heavy receipt.

## P-A0029-02 — ABERTA PARA CHAT 2

### Defeito

O código latente de A0029 consome 3 Abalos no PRE quando `heavyConfirmed=true`. Hoje a rota real permanece fail-closed por `P-A0029-01`, mas o defeito seria ativado imediatamente quando um provider futuro passasse a fornecer heavy receipt correto.

### Contrato de implementação

1. PRE reserva exatamente 3 Abalos preexistentes por actor/target/root.
2. POST válido commita apenas as cargas reservadas.
3. POST zero/cancelado libera sem consumo.
4. Commit A0029 ocorre antes de A0028 gerar Abalo do mesmo hit: `3 → 0 → 1`.
5. Corrigir este sequencing **não** resolve nem relaxa `P-A0029-01`.

### Testes mínimos

- PRE preserva 3 Abalos;
- POST válido consome 3;
- zero/cancelado preserva;
- concorrência de roots;
- ordem A0029→A0028;
- rota real continua fail-closed quando `heavyConfirmed=false`.

## P-A0030-01 — PERMANECE ABERTA / BLOQUEANTE

Faltam guard-break causal attacker-side correlacionado à mesma ação HAMMER e heavy receipt seguro. É proibido inferir por `BLOCKED`, stamina, som, animação, Armor, stun, dano ou Simply Swords sunder.

## P-A0030-02 — ABERTA PARA CHAT 2

### Defeito

O código latente de A0030 consome Janela Demolidora no PRE. Um hit cancelado/zerado pode destruir a oportunidade armada.

### Contrato de implementação

1. PRE apenas reserva a Janela Demolidora por actor/target/root.
2. POST direto + hostil + dano efetivo >0 consome a janela.
3. POST zero/cancelado preserva a janela.
4. Root concorrente não pode consumir reserva pertencente a outro root.
5. Corrigir este sequencing **não** resolve nem relaxa `P-A0030-01`.

### Testes mínimos

- PRE preserva janela;
- POST válido consome;
- zero/cancelado preserva;
- concorrência/dedup;
- sem guard-break/heavy receipts a perk continua fail-closed.

## Evidência de reprodução

Durante a auditoria houve uma reprodução transitória em branch de trabalho:

- **CI #2302:** 136 testes, exatamente 4 falhas correspondentes a A0023, A0024, A0029 e A0030 no comportamento PRE antigo.

Uma implementação experimental foi usada apenas para validar a hipótese e a ordem consumer→producer. Após a atualização do fluxo de responsabilidades, ela foi **descartada integralmente** da branch canônica. Este documento preserva somente o diagnóstico e o contrato de implementação para o Chat 2.

## Critério de aceite do Chat 2

O Chat 2 deve:

1. implementar somente os contratos acima, sem redesign;
2. usar TDD RED→GREEN;
3. preservar provider-native first/fail-closed;
4. não inferir heavy, guard pressure ou guard-break;
5. manter consumer→producer no mesmo root;
6. atualizar os dossiês e este tracker com evidência do código realmente implementado;
7. entregar a implementação para o fluxo de fechamento do Chat 3.

## Regra de parada

Este ciclo de auditoria termina com o handoff documentado. **Nenhum merge deve ser executado por este chat e A0031+ não deve ser iniciado automaticamente.**
