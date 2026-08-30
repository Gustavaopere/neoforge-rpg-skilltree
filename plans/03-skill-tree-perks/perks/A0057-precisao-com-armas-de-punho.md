# A0057 — Precisão com Armas de Punho

## Estado

- **Design:** APROVADO após correção de classificação/provenance.
- **Notion:** `3c569db9-f0db-81e5-a161-e615da182f4e`.
- **Runtime:** resolver crítico FIST presente; aquisição depende do fechamento de A0055.

## Contrato canônico

- A0055 ≥1 + gateway `combat_fist`.
- +3% chance crítica FIST por rank, máximo +9%.
- Classificação FIST/knuckle provider-native/versionada e ataque direto do jogador.
- Uma única resolução crítica/root action; Apothic, se usado, integra o mesmo resolver.
- Sem categoria segura, fail-closed; `rpgskilltree:fist_weapons` não é fallback canônico.
- Perda de A0055/gateway por rank loss/respec/rules reload remove elegibilidade imediatamente; nenhum crítico FIST de A0057 pode sobreviver como rota órfã.

## Evidência runtime

`A0041A0060EpicFightHooks.onCriticalHit(...)` e `rootAction(...)` usam categoria `fist`/`knuckle`, correlacionam crítico provider-native e chamam o serviço crítico canônico. O pipeline é tecnicamente coerente; a disponibilidade da linha depende da Mastery/gateway A0055.

## Pendências para Chat 2

- Herdadas de A0055: reconciliar `combat:fist` e `combat_fist`.
- Reforçar regressão de uma única rolagem, direct-player provenance e rank/gateway reconciliation.

## Boundaries

`ARCANE_BACKLASH`, dano de companion Mobstein, proc/follow-up derivado e callback visual Punchy são inelegíveis. Ataque FIST direto do jogador contra entidades dos próprios projetos continua universalmente elegível.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0055 ≥1 + `combat_fist`; indisponibilidade de A0055 bloqueia aquisição/ativação sem bypass. |
| 2. Integração global | **PASS** | Crítico passa pelo resolver canônico único; Backlash, companions e procs não herdam autoria; nenhum recurso paralelo é criado. |
| 3. Qualidade e identidade | **PASS** | Node incremental de precisão do ramo FIST; bônus pequeno é função de caminho e não simula Notable/Capstone. |
| 4. Ramificação, distância e topologia | **PASS no design** | Camada coerente após A0055 no ramo FIST; architecture `combat_fist` pendente é blocker runtime conhecido. |
| 5. Especializações | **PASS** | Subdisciplina MARTIAL/ARMAS_DE_PUNHO; não invade magia/tecnologia nem cria classe por mod. |
| 6. PT-BR | **PASS** | Nome/efeito/requisitos em PT-BR; termos técnicos mantidos apenas na documentação. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra corrigidos e re-fetch confirmando persistência; sem drift posterior. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Apothic quando aplicável, WoM, Punchy, own-projects e Mobstein foram dispostos; sem bridge artificial. |

Os 18 critérios técnicos cumulativos passam **no design**; a única dependência de fechamento é a infraestrutura FIST de A0055 e sua prova de runtime.

## Notion

Hook/Fallback/Regra corrigidos; re-fetch PASS em 2026-08-30.
