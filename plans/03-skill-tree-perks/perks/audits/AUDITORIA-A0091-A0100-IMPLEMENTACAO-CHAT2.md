# AUDITORIA — CHAT 2 — IMPLEMENTAÇÃO A0091–A0100

Data: 2026-09-01  
Escopo: **exatamente 10 perks consecutivas, A0091–A0100**.  
Responsabilidade: implementação do contrato aprovado pelo Chat 1. **Sem bateria final de testes, sem `IMPLEMENTAÇÃO CONFIRMADA` e sem merge neste Chat 2.**

## 1. Linha operacional e branch

- Repositório: `Gustavaopere/neoforge-rpg-skilltree`.
- Branch/PR continuada do Chat 1: `docs/chat1-a0091-a0100-audit`, PR #326.
- Base operacional correta deste lote: HEAD da implementação A0081–A0090, PR #372, `a7d9c7b9a2df6589720b91ac68d463f15bfc4a52`.
- A branch A0091–A0100 é descendente direta desse SHA; o compare registrou `ahead`, `behind_by=0` e merge-base igual a `a7d9c7...` antes do fechamento documental.
- A cadeia operacional deve permanecer: **#355 A0071–A0080 → #372 A0081–A0090 → #326 A0091–A0100**. O Chat 3 deve validar/mergear os predecessores e retargetar/reconciliar conforme necessário.
- A0101+ não faz parte deste ciclo e não foi iniciado.

## 2. Estado por perk após implementação

| Código | Perk | Estado Chat 2 | Evidência/decisão implementada |
|---|---|---|---|
| A0091 | Base Firme | CÓDIGO PRESENTE | Mantido um único node-effect `minecraft:generic.knockback_resistance`, `ADD_FLAT`, +0,03/rank até +0,15; sem segundo modifier/pipeline. |
| A0092 | Resistência Física | CÓDIGO PRESENTE | `rpgskilltree:physical` materializada com o seed de 17 `DamageType` aprovado; classifier único no pipeline incoming; fontes modded desconhecidas continuam fail-closed. |
| A0093 | Guarda Econômica | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE`/effective rank zero/purchase rejection enquanto não existir hook causal seguro de custo real de stamina da guarda; nenhum refund/heurística foi criado. |
| A0094 | Recuperação de Guarda | CÓDIGO PRESENTE EM FAIL-CLOSED | Availability transitiva de A0093 e indisponibilidade própria pela ausência de `GUARD_BREAK + recovery` correlacionados; nenhuma proxy por animação/knockback/stamina. |
| A0095 | Tenacidade | CÓDIGO PRESENTE | Dependência stale A0094 removida; binding provider-native `epicfight:stun_armor`, `ADD_FLAT`, +0,25/rank; reducer genérico antigo desautorizado/removido. |
| A0096 | Último Fôlego | CÓDIGO PRESENTE | Reutiliza exclusivamente `rpgskilltree:physical`; snapshot pré-impacto; A0092→A0096 multiplicativos; hostilidade causal por `LivingEntity` não-self/não-ally, sem `Enemy`-only. |
| A0097 | Primeira Defesa | CÓDIGO PRESENTE | Reserva no PRE e commit somente no POST com dano efetivo >0; zero/cancel faz rollback; estado bounded e lifecycle integrados ao runtime. |
| A0098 | Defesa em Movimento | CÓDIGO PRESENTE | Sprint vanilla server-authoritative preservado; boundary único de forced/passive movement impede ativação por deslocamento externo; ParCool extra continua fail-closed sem receipt real. |
| A0099 | Defesa Estacionária | CÓDIGO PRESENTE | Reutiliza exclusivamente A0079/`StationaryStateService`; forced invalidation transversal encaminhada pelo mesmo serviço; sem detector paralelo. |
| A0100 | Anti-Crítico | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE` enquanto não existir receipt incoming com `legitimatelyCritical + baseDamage + additionalCriticalDamage`; nenhuma heurística ou redução universal substituta. |

## 3. Pendências do Chat 1 consumidas

### A0091
- `P-A0091-01`: authority persistente permanece no `AttributeNodeEffectRuntime`; nenhuma segunda aplicação foi adicionada.

### A0092
- `P-A0092-01`: tag física materializada com seed canônico.
- `P-A0092-02`: classifier único preservado no incoming pipeline; adapters não reaplicam multiplicador.
- `P-A0092-03`: fontes modded desconhecidas continuam fail-closed; nenhuma inferência por namespace/item/animação.

### A0093
- `P-A0093-01`: indisponibilidade integrada ao purchase/effective-rank path.
- `P-A0093-02`: nenhum mixin interno/refund sintético foi introduzido; provider permanece authority.

### A0094
- `P-A0094-01`: availability transitiva A0093→A0094 implementada.
- `P-A0094-02`: ausência do segundo binding mantém o node indisponível, sem adapter inventado.

### A0095
- `P-A0095-01`: catálogo/teste reconciliados para dependência única A0091≥2.
- `P-A0095-02`: node effect `epicfight:stun_armor`, +0,25/rank, implementado.
- `P-A0095-03`: reducer genérico antigo deixou de ser authority; attribute/provider real é o único caminho positivo.

### A0096
- `P-A0096-01`: classifier compartilhado com A0092, sem segunda tag.
- `P-A0096-02`: hostilidade causal compartilhada e sem `Enemy` como requisito.
- `P-A0096-03`: snapshot pré-impacto e uma composição por evento preservados.

### A0097
- `P-A0097-01`: reservation→commit PRE/POST implementado; zero/cancel não consome preparação.
- `P-A0097-02`: hostilidade causal reconciliada.
- `P-A0097-03`: limpeza integrada a lifecycle/rank snapshot efetivo.

### A0098
- `P-A0098-01`: classifier de movimento autopropelido/forced movement consolidado para o fallback seguro.
- `P-A0098-02`: ParCool/Epic ParCool continuam sem cobertura extra na ausência de receipt real.
- `P-A0098-03`: nenhum ledger de confluência foi duplicado; Stage 04.02 continua authority.

### A0099
- `P-A0099-01` / `P-A0079-02`: A0099 reutiliza o mesmo `StationaryStateService`; forced invalidation converge no serviço único.
- `P-A0099-02`: classifier hostil compartilhado, sem `Enemy`-only.
- `P-A0099-03`: bridge PP não toca cobrança/provenance Stage 04.02.

### A0100
- `P-A0100-01`: purchase/effective rank ficam indisponíveis sem binding real.
- `P-A0100-02`: nenhuma heurística foi adicionada.
- `P-A0100-03`: permanece condição futura; não existe adapter a deduplicar enquanto não houver receipt provider-native suficiente.

## 4. Authority, causalidade e deduplicação

- `LivingIncomingDamageEvent` continua o boundary de preparação/aplicação das defesas recebidas; `LivingDamageEvent.Post` é o commit de A0097 quando o dano final é efetivamente positivo.
- A0092/A0096/A0097/A0098/A0099 compõem no mesmo pipeline defensivo, sem reducers paralelos.
- A0092 e A0096 usam o mesmo classifier físico; A0096 não cria tag própria.
- Ambiente, self-damage, aliados e resource-costs não ganham hostilidade por aparência, namespace ou classe `Enemy`.
- A0097 usa estado/reservas bounded e não commita em cancelamento/zero damage.
- A0079/A0099 compartilham um único serviço de stationarity.
- A0093/A0094/A0100 não aceitam compra/rank efetivo quando o provider binding obrigatório está ausente.
- A0095 aplica somente o atributo provider-native de Stun Armor e não converte a perk em knockback/Armor/Toughness/redução universal.

## 5. Fail-closed preservado

- **A0093:** sem hook causal de custo de guarda = indisponível.
- **A0094:** predecessor indisponível e sem receipt/recovery hook = indisponível.
- **A0095:** Epic Fight ausente/incompatível ou atributo não resolvido = indisponível, sem substitute stat.
- **A0098:** ações extras ParCool/Epic ParCool sem state receipt = omitidas; fallback vanilla seguro permanece.
- **A0100:** sem decomposição crítica incoming = indisponível.

Nenhum desses casos foi substituído por bônus genérico, inferência visual, refund pós-consumo ou heurística de dano final.

## 6. Validação obrigatória reservada ao Chat 3

O Chat 3 deve criar/completar e executar a bateria pertinente, incluindo no mínimo:

1. A0091 ranks 0–5, modifier único, respec/rank loss/reload e ausência de drift.
2. A0092 os 17 `DamageType` seed e exclusões fire/magic/wither/sonic/explosion; source modded desconhecida fail-closed; uma aplicação/evento.
3. A0093/A0094/A0100: `UNAVAILABLE_NODE`, rank efetivo zero e tentativa de compra sem gasto de PP.
4. A0095: Epic Fight **21.17.3.1** presente/ausente/version mismatch, `epicfight:stun_armor` +0,25…+1,25 e ausência do reducer genérico antigo.
5. A0096: borda 29,999%/30,000%, snapshot pré-impacto, A0092×A0096 multiplicativo e atacante causal modded `LivingEntity` não aliado.
6. A0097: 199/200 ticks, PRE reserve, POST positivo commit, zero/cancel rollback, callbacks/roots concorrentes, multiplayer e lifecycle.
7. A0098: sprint vanilla 3/6/9%; walking e forced/passive movement não ativam; mount/vehicle/knockback/fall/contraption/belt/grappling; ParCool extra fail-closed sem receipt.
8. A0099: 29/30 ticks, path 3D ≤0,10, reset acima do limite, teleport/dimension/passenger/forced movement; provar que A0079 e A0099 compartilham state.
9. A0100: ausência de provider não reduz dano comum/crítico por heurística; fórmula pura reduz somente parcela adicional em fixture causal futura.
10. Unit tests, NeoForge GameTests/integration fixtures aplicáveis, build NeoForge, verificação do JAR, dedicated-server smoke e CI.

## 7. Estado de fechamento do Chat 2

- Os dez dossiês A0091–A0100 foram atualizados para o estado real pós-implementação.
- Esta auditoria registra o handoff técnico sem redesenho.
- `STATUS.md` deve refletir as dez perks como código presente, com A0093/A0094/A0100 explicitamente fail-closed.
- O Chat 2 **não executa nem usa a bateria final para declarar sucesso**.
- `IMPLEMENTAÇÃO CONFIRMADA`: **NÃO**.
- CI GREEN final: **NÃO declarado pelo Chat 2**.
- Merge: **NÃO**.

**Estado formal do lote:** `CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

A0101+ não foi iniciado.