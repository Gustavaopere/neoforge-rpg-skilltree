# A0079 — Ataque Estacionário

## Estado

- **Design:** APROVADO após hardening do boundary do detector em 2026-08-31.
- **Notion:** `3c569db9-f0db-818c-bbf0-e5918a79c25b`; Hook/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** IMPLEMENTAÇÃO PARCIAL: `StationaryStateService` e bônus existem, mas o sampler atual não propaga todas as invalidações forçadas exigidas.

## Contrato canônico

- MARTIAL + A0061 Força Aplicada ≥2 + acesso semântico ao corredor VITALITY.
- 3 ranks, 1 ponto/rank.
- Após 30 ticks consecutivos com path length 3D ≤0,10 bloco, +5% dano físico direto por rank, máximo +15%, enquanto o estado permanecer válido.
- Bridge PP: pontos não contam por padrão para MARTIAL ou VITALITY; whitelist de Specialist em no máximo um threshold.

## Boundary único

`StationaryStateService` é o detector exclusivo. Teleporte, troca de dimensão, mount/vehicle transition, contraption/belt e deslocamento forçado identificado devem invalidar imediatamente, mesmo se o delta cair dentro de 0,10 bloco. Nenhuma perk pode manter threshold/detector paralelo.

## Evidência runtime

O service implementa `forcedTransition` e threshold canônico. Contudo `A0061A0080EpicFightHooks.onServerTick` atualmente chama `sample(..., false)` para todos os ticks; dimensão é limpa separadamente, mas mount/vehicle/contraption/forced movement ainda precisam receipts/invalidation apropriados.

## Fallback e anti-abuso

Não aproximar “parado” por velocidade client-side, animação ou input. Movimento externo não pode contar como preparação estacionária. Sem receipt de forced transition em uma integração específica, essa rota deve invalidar/falhar fechado, não ser tratada como estacionária por tolerância local.

## Pendências para Chat 2

- **P-A0079-01:** propagar invalidações forçadas para teleport, mount/vehicle, contraption/belt e deslocamentos provider-identificados; não usar sempre `false`.
- **P-A0079-02:** testes 30 ticks/0,10 bloco, reset imediato, lifecycle e multiplayer.
- **P-A0079-03:** testar bridge PP MARTIAL↔VITALITY sem dupla contagem/border hopping.

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
| Providers | PASS | NeoForge/RPG; movimento externo só por invalidation real. |

Os 18 critérios passam **no design**; runtime atual tem pendência de cobertura de invalidation.