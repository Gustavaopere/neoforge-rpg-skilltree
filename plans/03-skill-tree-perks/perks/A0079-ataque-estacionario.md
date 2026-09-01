# A0079 — Ataque Estacionário

## Estado

- **Design:** APROVADO após hardening do boundary do detector em 2026-08-31.
- **Notion:** `3c569db9-f0db-818c-bbf0-e5918a79c25b`; Hook/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- MARTIAL + A0061 Força Aplicada ≥2 + acesso semântico ao corredor VITALITY.
- 3 ranks, 1 ponto/rank.
- Após 30 ticks consecutivos com path length 3D ≤0,10 bloco, +5% dano físico direto por rank, máximo +15%, enquanto o estado permanecer válido.
- Bridge PP: pontos não contam por padrão para MARTIAL ou VITALITY; whitelist de Specialist em no máximo um threshold.

## Boundary único

`StationaryStateService` é o detector exclusivo. Teleporte, troca de dimensão, mount/vehicle transition, contraption/belt e deslocamento forçado identificado invalidam imediatamente, mesmo se o delta cair dentro de 0,10 bloco. Nenhuma perk mantém threshold/detector paralelo.

## Implementação Chat 2 — 2026-09-01

- sampling canônico continua centralizado em `StationaryStateService`;
- tick server-side passa `player.isPassenger()` como forced transition;
- teleport e knockback possuem invalidação explícita;
- `MartialStanceRuntime.reconcile(...)` também invalida stationarity quando detecta transporte externo;
- `A0079ForcedMovementCompat` faz gates exatos para Create 6.0.10 e Sable 2.0.5 e falha fechado em versão desconhecida, linkage error ou exceção;
- compat classes específicas ficam isoladas e só são carregadas depois do gate do provider;
- Create reconhece belt ativo por `BeltBlockEntity`; Sable reconhece jogador contido em sublevel por `Sable.HELPER.getContaining(player)`;
- quando Epic Fight não é o owner do tick, o sampler fallback existente de A0081–A0100 continua sendo o único sampler; o subscriber geral A0076–A0079 apenas invalida, evitando dupla contagem de ticks.

## Pendências para Chat 3

- validar exatamente 30 ticks e path length total 3D `<=0,10`, inclusive reset ao ultrapassar o limiar;
- validar invalidation imediata por teleport, knockback, mount/passenger, Create belt/contraption e Sable sublevel;
- validar providers ausentes, presentes na versão exata e presentes em versão divergente/fail-closed;
- validar ausência de sampler duplicado com/sem Epic Fight;
- validar lifecycle e bridge PP MARTIAL↔VITALITY sem dupla contagem/border hopping.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0061≥2 + VITALITY semantic gate. |
| Integração global | PASS | um detector server-side, sem duplicar movimento/veículos. |
| Qualidade/identidade | PASS | compromisso posicional verificável. |
| Topologia | PASS | Camada 2, `MARTIAL_VITALITY_BRIDGE`. |
| Especializações | PASS | bridge PP explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | NeoForge/RPG + gates Create/Sable; movimento externo só por invalidation real. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
