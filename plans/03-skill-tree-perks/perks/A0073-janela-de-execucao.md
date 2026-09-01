# A0073 — Janela de Execução

## Estado

- **Design:** APROVADO após correção causal reservation→commit em 2026-08-31.
- **Notion:** `3c569db9-f0db-8172-8de3-dcb8dffa2819`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

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

## Implementação Chat 2 — 2026-09-01

- state causal recebeu reservation/commit/rollback separados para opener e finisher;
- Epic Fight PRE mantém somente `PendingHit`/reserva reversível; POST positivo arma/consome e inicia cooldown, enquanto zero/cancel faz rollback;
- projectile físico usa PRE canônico já existente + `A0073A0080ProjectileCommitEvents` para commit/rollback pós-dano;
- reservas pendentes possuem retenção bounded de 1 s para impedir vazamento quando um PRE não receber POST;
- death/removal, logout, dimensão, respawn, server stop e mudança de rank efetivo limpam estado transitório;
- bônus de Impact continua somente onde o provider oferece Impact seguro;
- **refund de Stamina permanece 0** porque não existe receipt causal pós-consumo seguro da mesma ação; não há polling, estimativa por barra, hunger ou exhaustion;
- execute nativo de Simply Swords permanece provider-native e não foi substituído.

## Pendências para Chat 3

- validar opener abaixo de 20%, janela 3 s, finisher root distinto, +18%/+9% boss e cooldown 8 s somente após POST confirmado;
- validar cancelamento/dano zero/expiração e concorrência de raízes, inclusive projéteis simultâneos;
- validar que ausência de Stamina receipt produz refund exatamente zero;
- validar cleanup por target unload/death e actor lifecycle/rank loss/respec/rules reload;
- validar ausência de duplicação com Simply Swords/native execute e callbacks múltiplos.

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

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
