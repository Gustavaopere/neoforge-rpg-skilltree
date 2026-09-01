# A0074 — Primeiro Sangue

## Estado

- **Design:** APROVADO após correção causal reservation→commit em 2026-08-31.
- **Notion:** `3c569db9-f0db-8173-b2ac-eb0a2bd12cc8`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

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

## Implementação Chat 2 — 2026-09-01

- `A0061A0080CombatState` recebeu reserva explícita `OPENER`/`FINISHER`, commit e rollback;
- o PRE registra apenas reserva/histórico pendente bounded; `lastAttackAt` definitivo só é atualizado no POST confirmado;
- Epic Fight POST positivo commita opener/finisher; zero/ineligibilidade executa rollback;
- projéteis físicos recebem commit/rollback no subscriber pós-dano dedicado;
- janela 4 s e cooldown 12 s permanecem target-scoped e só são consumidos após dano efetivo;
- pending hit possui retenção bounded de 1 s para evitar estado órfão quando a cadeia PRE→POST não conclui;
- death/removal e lifecycle do ator/rank efetivo limpam estado transitório.

## Pendências para Chat 3

- validar bordas de vida `>=85%`, idle `>=8 s`, janela 4 s e cooldown 12 s;
- validar que opener não recebe o bônus e que apenas root distinto pode consumir;
- validar cancelamento/dano zero/expiração, concorrência e projéteis simultâneos;
- validar cleanup por morte/removal/unload, logout/dimensão/respawn e rank loss/respec/rules reload;
- validar que procs/DoT/summons/fake players/reflexão não alteram histórico nem consomem a janela.

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

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
