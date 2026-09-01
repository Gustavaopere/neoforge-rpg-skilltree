# A0076 — Postura Agressiva

## Estado

- **Design:** APROVADO após fechamento do boundary de ativação em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a3-b3b3-f3901dbb0937`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- MARTIAL + A0061 Força Aplicada ≥3 + A0064 Ritmo de Combate ≥1.
- 1 rank, custo 1.
- Enquanto `AGGRESSIVE`: +8% dano físico elegível e −5% resistência física.
- Ocupa exclusivamente `MARTIAL_STANCE`; cooldown de troca 1,5 s.

## Boundary de ativação fechado

RPG Skill Tree é owner da stance e do comando. O controle remapeável `Alternar Postura Marcial` envia somente intenção por payload serverbound. O servidor valida ranks, disponibilidade e cooldown, então efetiva a transição atômica:

- se só A0076 estiver disponível: `NONE ↔ AGGRESSIVE`;
- quando A0076 e A0077 estiverem legitimamente disponíveis: `NONE → AGGRESSIVE → CAUTIOUS → NONE`.

Cliente nunca é authority. A stance nativa de Epic Fight não substitui o slot RPG e só pode coexistir por adapter explícito sem duplicação.

## Implementação Chat 2 — 2026-09-01

- `MartialStanceIntentPayload` foi criado como payload serverbound de intenção;
- `ClientKeyMappings` expõe controle remapeável para alternar postura;
- `ModNetworking` registra o payload e o servidor delega a transição a `MartialStanceRuntime`;
- `MartialStanceRuntime` valida `effectiveRanks`, availability, exclusividade e cooldown de 1,5 s;
- com A0077 mascarada por A0067, o ciclo efetivo atual é `NONE ↔ AGGRESSIVE`;
- dano físico de saída aplica +8% pela policy canônica; dano físico recebido aplica o tradeoff −5% de resistência no boundary `rpgskilltree:physical`;
- stance é reconciliada/limpa quando ranks deixam de ser efetivos e nos lifecycles já ligados ao runtime;
- nenhuma potion effect, client flag ou stance nativa de outro provider substitui `MARTIAL_STANCE`.

## Pendências para Chat 3

- validar payload spoofado, spam, rank ausente, cooldown e authority servidor em multiplayer;
- validar aplicação única de +8% dano e −5% resistência física, sem confundir Armor/Stun Armor/magic resistance;
- validar limpeza sem resíduos em morte/respawn/logout/dimensão/rank loss/respec/rules reload;
- normalizar a tradução do keybind em `lang` se o review exigir chave de localização em vez de literal funcional.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | ranks + binding de ativação server-authoritative. |
| Integração global | PASS | resistência física não é Armor/magic/Shroud. |
| Qualidade/identidade | PASS | stance de risco ofensivo. |
| Topologia | PASS | Camada 3, `MARTIAL/POSTURE`. |
| Especializações | PASS | região de posturas explícita. |
| PT-BR | PASS funcional | controle exposto em PT-BR; localização pode ser normalizada no Chat 3. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | RPG authority; Epic Fight apenas coexistência explícita. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
