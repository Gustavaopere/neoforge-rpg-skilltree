# A0073 — Janela de Execução

## Estado

- **Design:** APROVADO após correção causal reservation→commit em 2026-08-31.
- **Notion:** `3c569db9-f0db-8172-8de3-dcb8dffa2819`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** NÃO CONFORME no lifecycle atual: arm/consume/cooldown acontecem no PRE; Chat 2 deve corrigir sem redesenho.

## Contrato canônico

- Gateway MARTIAL + A0068 Dano contra Feridos ≥ 2 ranks.
- 1 rank, custo 2.
- Primeiro golpe físico direto confirmado contra alvo hostil abaixo de 20% arma `Exposto à Execução` por 3 s, sem bônus.
- Segundo root físico direto distinto recebe +18% dano e +20% Impact quando suportado; bosses recebem metade do bônus de dano (+9%).
- Cooldown 8 s por alvo somente após consumo confirmado.
- Se o segundo golpe matar, 10% da Stamina realmente debitada por aquela ação pode ser restituída exclusivamente por receipt pós-consumo causal; sem receipt, refund zero.

## Reservation → commit

- Opener: PRE apenas prepara; POST com dano direto hostil efetivo >0 arma o estado.
- Finisher: PRE reserva estado e pode aplicar multiplicadores; POST com dano >0 commita consumo + cooldown. Cancelamento/dano zero faz rollback.
- Refund de Stamina ocorre somente depois do commit e de receipt pós-consumo exato, claim-once.

## Providers

Epic Fight 21.17.3.1 fornece Impact quando a ação concreta expõe essa grandeza. Simply Swords 1.70.2 mantém execute/Implicits/Runic Powers/Awakening provider-native; eles não criam ou consomem o estado da perk.

## Pendências para Chat 2

- **P-A0073-01 BLOQUEANTE:** mover arm/consume/cooldown para reservation→POST commit; zero/cancel rollback.
- **P-A0073-02:** Stamina refund somente por receipt causal pós-consumo da mesma ação; sem receipt = 0.
- **P-A0073-03:** cleanup bounded por alvo/ator em morte, removal/unload, logout/dimensão/respawn, rank loss/respec/rules reload.
- **P-A0073-04:** dedup de Simply Swords/native execute e callbacks múltiplos.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0068≥2 e alvo <20% no opener confirmado. |
| Integração global | PASS | Stamina permanece Epic Fight-owned; Simply provider-native. |
| Qualidade/identidade | PASS | Execução em dois tempos, não instant kill. |
| Topologia | PASS | Camada 3, `MARTIAL/EXECUTION`. |
| Especializações | PASS | região de execução explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Epic Fight/Simply/RPG delimitados. |

Os 18 critérios técnicos passam **no design**; runtime atual requer correção causal pelo Chat 2.