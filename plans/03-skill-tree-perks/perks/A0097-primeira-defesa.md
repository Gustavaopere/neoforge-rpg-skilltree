# A0097 — Primeira Defesa

## Estado

- **Chat 1:** DESIGN APROVADO após hardening causal.
- **Implementação:** runtime preparatório diverge do contrato em consumo PRE e hostilidade; **não confirmado**.
- **Notion:** `3c569db9-f0db-814d-a37e-c21276901665`; corrigido e re-fetch confirmado.
- **Domínio:** VITALITY; Camada 2; Ramo Defesa de Abertura.
- **Ranks:** 3; custo 1 PP/rank.
- **Dependência:** A0088 Constituição ≥1 + Gateway VITALITY.

## Contrato canônico

- Depois de **200 ticks / 10 s sem dano hostil elegível efetivamente recebido**, o próximo dano hostil elegível recebe redução de 5% por rank, até 15%.
- O benefício é consumível e exige **reservation→commit**.
- PRE (`LivingIncomingDamageEvent`) pode reservar a preparação para o root/evento causal e aplicar o multiplicador ao valor que seguirá pelo pipeline.
- Apenas `LivingDamageEvent.Post` com dano efetivo >0 commita o consumo e reinicia a janela de 200 ticks.
- Cancelamento ou dano zero descarta a reserva sem consumir preparação nem reiniciar timer.

## Hostilidade causal

- Atacante causal precisa ser `LivingEntity`, diferente do jogador e não aliado.
- PvP não aliado conta.
- **Não exigir `instanceof Enemy`.** Classe Java, target AI, aggro visual, proximidade e animação não são authority.
- Ambiente, self-damage, aliados e resource costs não reservam/consomem/reiniciam A0097.

## Provider / authority

- Minecraft/NeoForge: eventos de dano e autoria causal.
- RPG Skill Tree: state/timer/reservation server-authoritative.
- Providers externos só ampliam cobertura quando entregam autoria equivalente; não criam outro timer.
- Black Arcana `ARCANE_BACKLASH`/`BLOOD_MAGIC_COST`, Volcanoes hazards e Enshrouded environment não são hits hostis elegíveis sem attacker causal permitido.

## Lifecycle

- Estado transitório e reservas devem ser limpos em death, logout, respawn, dimension change e server stop.
- Rank loss/respec/rules reload que remova A0097 ou A0088 também deve limpar preparação/reservas.
- Dedup por identidade causal/root: callbacks repetidos do mesmo resultado não podem consumir duas vezes.

## Divergências do runtime atual — handoff Chat 2

- `A0081A0100DefenseState.consumeOpeningDefense` atualiza imediatamente o timestamp durante PRE.
- `A0081A0100CombatEvents.applyIncomingDefense` chama esse consumo ainda em `LivingIncomingDamageEvent`; dano posterior zerado/cancelado pode gastar preparação fantasma.
- `hostile(...)` exige `(target instanceof Enemy || target instanceof Player)`, contrariando o contrato causal mais amplo.

### Pendências Chat 2

- **P-A0097-01:** implementar reserva bounded por actor/root/event e commit somente no `LivingDamageEvent.Post` com dano >0; rollback em cancel/zero.
- **P-A0097-02:** reconciliar hostilidade para `LivingEntity` causal não-self/não-ally; remover `Enemy` como requisito de elegibilidade.
- **P-A0097-03:** lifecycle completo em rank loss/respec/rules reload além dos eventos de entidade/jogador.

## Testes obrigatórios Chat 3

1. 199 ticks não arma; 200 ticks arma;
2. PRE válido reduz dano, mas zero/cancel não consome preparação;
3. POST positivo consome exatamente uma vez e reinicia timer;
4. callback duplicado/root duplicado não consome duas vezes;
5. mob modded `LivingEntity` não aliado sem `Enemy` conta quando causador real;
6. aliado/self/environment/resource-cost não reserva nem reinicia;
7. PvP não aliado conta;
8. death/logout/respawn/dimension/rank loss/respec/rules reload limpam state/reserva;
9. multiplayer separa atores e roots;
10. dedicated-server/GameTest cobre PRE→POST real.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0088≥1 + VITALITY. |
| Integração global | PASS | Autoria causal separa hazards/resource costs. |
| Qualidade/identidade | PASS | Defesa de abertura com janela real. |
| Topologia | PASS | Camada 2. |
| Especializações | PASS | VITALITY defensivo universal. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Hardening persistido. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | NeoForge causal + state RPG. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0097-01 reservation→commit implementado
- [ ] P-A0097-02 classifier hostil reconciliado
- [ ] P-A0097-03 lifecycle completo implementado
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/multiplayer/lifecycle
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
