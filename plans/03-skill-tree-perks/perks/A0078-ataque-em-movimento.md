# A0078 — Ataque em Movimento

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-8113-8673-e9135aa4d84d`; fetch fresco em 2026-08-31 sem drift funcional.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- MARTIAL + A0064 Ritmo de Combate ≥2 + acesso semântico canônico ao corredor AGILITY.
- 3 ranks, 1 ponto/rank.
- +4% de dano físico direto por rank enquanto houver locomoção autopropelida elegível, máximo +12%.
- Bridge PP: por padrão não conta para threshold puro de MARTIAL nem AGILITY; Specialist pode whitelistar para no máximo um.

## Provider / boundary

- Fallback seguro atual: `ServerPlayer.isSprinting()`.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0 só entram quando adapter provar estado real server-authoritative de locomoção ativa do próprio jogador.
- Knockback, queda, mount/vehicle, Sable/Create contraption, belt, grappling ou outro deslocamento externo não contam.

## Implementação Chat 2 — 2026-09-01

- o caminho canônico de hit físico usa `player.isSprinting()` no servidor para preencher `HitFacts.sprinting`;
- a policy aplica A0078 apenas no root físico direto elegível, com `effectiveRanks` reconciliado;
- nenhuma extensão ParCool/Epic ParCool foi inventada sem receipt server-authoritative;
- deslocamento externo não é inferido por delta de posição, animação ou câmera;
- transporte/forced movement permanece separado do sinal de sprint e das invalidações de A0079.

## Pendências para Chat 3

- validar sprint vanilla provider-present e ausência de bônus quando `isSprinting()==false`;
- validar knockback, queda, mount/vehicle, Create/Sable transport e demais deslocamentos passivos sem falso positivo;
- validar uma única aplicação por root físico;
- validar bridge PP MARTIAL↔AGILITY sem dupla contagem/border hopping;
- manter ParCool/Epic ParCool fail-closed até existir receipt real, versionado e deduplicável.

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

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
