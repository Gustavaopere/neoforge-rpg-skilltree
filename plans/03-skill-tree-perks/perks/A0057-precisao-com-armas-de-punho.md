# A0057 — Precisão com Armas de Punho

## Estado

- **Design:** APROVADO após correção de classificação/provenance.
- **Notion:** `3c569db9-f0db-81e5-a161-e615da182f4e`.
- **Runtime:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. O resolver crítico FIST está presente e a infraestrutura A0055 (`combat:fist`/`combat_fist`) já foi fechada na linha predecessora.

## Contrato canônico

- A0055 ≥1 + gateway `combat_fist`.
- +3% chance crítica FIST por rank, máximo +9%.
- Classificação FIST/knuckle provider-native/versionada e ataque direto do jogador.
- Uma única resolução crítica/root action; Apothic, se usado, integra o mesmo resolver.
- Sem categoria segura, fail-closed; `rpgskilltree:fist_weapons` não é fallback canônico.
- Perda de A0055/gateway por rank loss/respec/rules reload remove elegibilidade imediatamente; nenhum crítico FIST de A0057 pode sobreviver como rota órfã.

## Evidência runtime

`A0041A0060EpicFightHooks.onCriticalHit(...)` e `rootAction(...)` usam categoria `fist`/`knuckle`, correlacionam crítico provider-native e chamam o serviço crítico canônico. A mesma root action é reutilizada no damage pipeline quando a correlação de `CriticalHitEvent` está disponível, impedindo segunda rolagem A0057 dentro do mesmo ataque causal.

A infraestrutura FIST de A0055 já possui Mastery `combat:fist` e gateway `combat_fist`. `A0041A0060RuntimeState.ranks(...)` usa ranks efetivos e reconcilia estados descendentes quando os pré-requisitos deixam de valer.

## Pendências para Chat 2

- **RESOLVIDAS herdadas de A0055:** `combat:fist` e `combat_fist` materializados.
- A regressão de uma única rolagem, direct-player provenance e provider-present fica para Chat 3.

## Implementação Chat 2 — PR #386

- [x] Hook crítico FIST/knuckle presente.
- [x] Resolver crítico canônico único preservado.
- [x] Gate A0055/`combat_fist` alcançável.
- [x] Fail-closed para categoria não mapeada.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** uma rolagem/root e provider-critical.
- [ ] **VALIDAÇÃO CHAT 3:** direct-player/provider-present/absent.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/build/smoke/CI de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

`ARCANE_BACKLASH`, dano de companion Mobstein, proc/follow-up derivado e callback visual Punchy são inelegíveis. Ataque FIST direto do jogador contra entidades dos próprios projetos continua universalmente elegível.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/código** | A0055 ≥1 + `combat_fist`; infraestrutura A0055 está materializada. |
| 2. Integração global | **PASS** | Crítico passa pelo resolver canônico único; Backlash, companions e procs não herdam autoria; nenhum recurso paralelo é criado. |
| 3. Qualidade e identidade | **PASS** | Node incremental de precisão do ramo FIST; bônus pequeno é função de caminho e não simula Notable/Capstone. |
| 4. Ramificação, distância e topologia | **PASS** | Camada coerente após A0055 no ramo FIST publicado. |
| 5. Especializações | **PASS** | Subdisciplina MARTIAL/ARMAS_DE_PUNHO; não invade magia/tecnologia nem cria classe por mod. |
| 6. PT-BR | **PASS** | Nome/efeito/requisitos em PT-BR; termos técnicos mantidos apenas na documentação. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra corrigidos e re-fetch confirmando persistência; sem drift posterior. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Apothic quando aplicável, WoM, Punchy, own-projects e Mobstein foram dispostos; sem bridge artificial. |

Os critérios técnicos cumulativos passam no design; código presente e aguardando validação final do Chat 3.

## Notion

Hook/Fallback/Regra corrigidos; re-fetch PASS em 2026-08-30.