# A0074 — Primeiro Sangue

## Estado

- **Design:** APROVADO após correção causal reservation→commit em 2026-08-31.
- **Notion:** `3c569db9-f0db-8173-b2ac-eb0a2bd12cc8`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** NÃO CONFORME no lifecycle atual: last-attack/arm/consume são mutados no PRE; Chat 2 deve corrigir.

## Contrato canônico

- Gateway MARTIAL + A0069 Dano contra Íntegros ≥ 2 ranks.
- 1 rank, custo 2.
- Alvo hostil com ≥85% da vida antes do impacto e sem hit direto confirmado do jogador nos últimos 8 s: o opener confirmado arma `Vantagem Inicial` por 4 s sem bônus.
- Próximo root físico direto distinto recebe +10% dano e +20% Impact quando suportado.
- Após consumo confirmado, cooldown 12 s por alvo.

## Reservation → commit

- Histórico `lastAttackAt` e arm do opener são atualizados somente no POST com dano direto hostil efetivo >0.
- Segundo hit: PRE apenas reserva a janela/aplica multiplicadores; POST confirmado commita consumo e cooldown.
- Cancelamento/dano zero faz rollback e não altera histórico/cooldown de forma irreversível.

## Providers e authority

Minecraft/NeoForge fornece vida; Epic Fight fornece root/Impact quando aplicável; RPG Skill Tree possui o estado. Dano de terceiros pode remover elegibilidade antes do opener. Procs, DoT, summons, fake players e reflexão não contam.

## Pendências para Chat 2

- **P-A0074-01 BLOQUEANTE:** migrar opener/consume/cooldown para reservation→POST commit.
- **P-A0074-02:** lifecycle bounded de estado por alvo e histórico em death/removal/unload + actor lifecycle/rank loss/respec/rules reload.
- **P-A0074-03:** testes de borda ≥85%, idle 8 s, cooldown 12 s, cancel/zero e root distinto.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0069≥2 + vida/idle reais. |
| Integração global | PASS | Usa vida canônica; sem estado duplicado de provider. |
| Qualidade/identidade | PASS | abertura em dois passos. |
| Topologia | PASS | Camada 3, `MARTIAL/OPENING`. |
| Especializações | PASS | região de abertura explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | NeoForge/Epic Fight/RPG delimitados. |

Os 18 critérios passam **no design**; implementação atual necessita correção causal.