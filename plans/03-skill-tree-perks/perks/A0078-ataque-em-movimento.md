# A0078 — Ataque em Movimento

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-8113-8673-e9135aa4d84d`; fetch fresco em 2026-08-31 sem drift funcional.
- **Runtime observado:** CÓDIGO PRESENTE para sprint vanilla server-side; extensões ParCool permanecem fail-closed sem receipt específico.

## Contrato canônico

- MARTIAL + A0064 Ritmo de Combate ≥2 + acesso semântico canônico ao corredor AGILITY.
- 3 ranks, 1 ponto/rank.
- +4% de dano físico direto por rank enquanto houver locomoção autopropelida elegível, máximo +12%.
- Bridge PP: por padrão não conta para threshold puro de MARTIAL nem AGILITY; Specialist pode whitelistar para no máximo um.

## Provider / boundary

- Fallback seguro atual: `ServerPlayer.isSprinting()`.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0 só entram quando adapter provar estado real server-authoritative de locomoção ativa do próprio jogador.
- Knockback, queda, mount/vehicle, Sable/Create contraption, belt, grappling ou outro deslocamento externo não contam.

## Evidência runtime

`A0061A0080EpicFightHooks` constrói `HitFacts.sprinting` de `player.isSprinting()` e a policy aplica A0078 uma vez no root físico. Nenhum estado ParCool é inferido por câmera/animação/velocidade.

## Fallback e anti-abuso

Sem provider de movimento adicional, usar apenas sprint vanilla. Não inferir por delta de posição. Dano indireto/procs/summons/fake players permanecem inelegíveis.

## Pendências para Chat 2

- **P-A0078-01:** validar sprint provider-present e exclusão de forced/passive movement.
- **P-A0078-02:** ParCool/Epic ParCool só podem ampliar cobertura com receipt real, versionado e deduplicado; ausência continua fail-closed.
- **P-A0078-03:** testar policy de bridge PP MARTIAL↔AGILITY e impossibilidade de border hopping.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0064≥2 + AGILITY semantic gate. |
| Integração global | PASS | movimento provider-native; veículos não viram movimento do jogador. |
| Qualidade/identidade | PASS | mobilidade ofensiva condicionada. |
| Topologia | PASS | Camada 2, `MARTIAL_AGILITY_BRIDGE`. |
| Especializações | PASS | bridge PP explícita e não dupla. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS | Fetch fresco. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | vanilla seguro; ParCool fail-closed sem receipt. |

Os 18 critérios passam **no design**.