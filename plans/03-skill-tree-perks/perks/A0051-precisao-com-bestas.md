# A0051 — Precisão com Bestas

## Estado

- **Design:** APROVADO após reauditoria provider→árvore e correção de provenance de lançamento.
- **Notion:** `3c569db9-f0db-8135-903d-db954d9f8087`.
- **Runtime:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. O bônus crítico CROSSBOW agora exige receipt de lançamento correlacionado; projétil derivado/reemitido sem launch receipt não recebe o bônus A0051.

## Contrato canônico

- A0049 ≥1 + gateway `epic_crossbow`.
- +3% de chance crítica por rank, máximo +9%.
- Somente projétil físico CROSSBOW causalmente atribuído ao `ServerPlayer` **e correlacionado a um lançamento CROSSBOW confirmado**.
- Vanilla `CrossbowItem`/subclasse classifica a arma, mas `owner + CrossbowItem` isoladamente não prova provenance do projectile/root.
- Externos exigem capability/categoria provider-native ou mapping versionado explícito e receipt de lançamento correspondente.
- Uma única resolução crítica/root action; Apothic, quando usado como backend, participa da mesma resolução.
- `ARCANE_BACKLASH`, spell projectiles, ricochetes/derivados, projéteis reemitidos sem launch receipt, fake players e projéteis de allies/bodyguards Mobstein são inelegíveis.

## Auditoria técnica

O adapter classifica vanilla por `CrossbowItem` e preserva owner real, mas o review da PR #249 identificou um gap: quando um add-on cria `AbstractArrow` derivado com owner jogador e metadata de besta sem `ArrowLooseEvent` correlacionado, o bridge pode sintetizar um pending launch neutro e ainda chegar ao resolver crítico. O Chat 2 fechou esse gap exigindo `PendingLaunch.launchConfirmed`: sem correlação válida, a parcela de chance crítica de A0051 é removida antes do resolver canônico, preservando apenas crítico nativo/outros efeitos independentes realmente aplicáveis.

O antigo fallback documental `rpgskilltree:crossbows` permanece removido: tag paralela não governada não é classificador canônico.

A alcançabilidade de A0049 já possui producer finite-discovery e ledger canônica `epicfight:crossbow` na linha predecessora; o Chat 2 não criou segunda Mastery.

### Provider→árvore

- RPG Skill Tree: resolver crítico, launch/root correlation e dedup são authority canônica.
- Black Arcana: Backlash terminal não é projectile/root CROSSBOW.
- Enshrouded/Volcanoes: não classificam projétil nem crítico.
- Mobstein 5.4.4: projectile de companion permanece Mobstein-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal quando a provenance CROSSBOW for real.

## Pendências para Chat 2

- **RESOLVIDA P-A0051-01:** launch receipt CROSSBOW server-authoritative passou a ser obrigatório para a parcela A0051 do crítico.
- **RESOLVIDAS herdadas P-A0049-01/02:** producer finite-discovery e namespace único `epicfight:crossbow` já existem na linha predecessora usada por esta branch.
- A validação provider-present/absent, derivado sem receipt e resolução crítica única permanece reservada ao Chat 3.

## Implementação Chat 2 — PR #386

- [x] Hook implementado sobre `ArrowLooseEvent → PendingLaunch confirmado → EntityJoinLevelEvent`.
- [x] Gate/provenance implementados para a parcela crítica A0051.
- [x] Provider-native / resolver crítico canônico preservado.
- [x] Fallback/fail-closed implementado para projectile sem launch receipt.
- [x] Deduplicação/root compartilhado preservados para Multishot correlacionado.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/JUnit e regressões provider-present/absent.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests / integração de lançamento real.
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke.
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0049 ≥1 + gateway `epic_crossbow`; a linha predecessora já fornece a Mastery/gateway necessários. |
| 2. Integração global | **PASS** | Usa o resolver crítico canônico; não lê/escreve Shroud, Arcane state, hazards, Stamina ou recursos paralelos. Backlash/companions/derived projectiles ficam fora. |
| 3. Qualidade e identidade | **PASS** | É o node incremental de precisão do ramo CROSSBOW, com bônus pequeno e função de caminho; não se apresenta como Notable/Capstone e não duplica uma segunda rolagem. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 2 após A0049 no gateway de Bestas; sem teleporte de região ou dependência circular. |
| 5. Especializações | **PASS** | Permanece ramo MARTIAL/BESTAS universal; não transforma Epic Fight/WoM em classe e não invade especialização mágica/tecnológica. |
| 6. PT-BR | **PASS** | Nome/efeito/requisitos visíveis em PT-BR; IDs, classes e hooks técnicos permanecem em inglês apenas na documentação técnica. |
| 7. Notion completo | **PASS** | Campos pertinentes preenchidos; `Hook`, `Fallback` e `Regra` endurecidos para launch provenance e re-fetch pós-review confirmado em 2026-08-30. |
| 8. NeoVitae | **PASS** | Nenhuma dependência, provider ou fallback NeoVitae. |
| 9. Cobertura modlist/providers | **PASS** | RPG, Epic Fight, Apothic/WoM quando aplicáveis, Black Arcana, Enshrouded, Volcanoes e Mobstein foram dispostos; não há bridge temática inventada. |

Os 18 critérios técnicos cumulativos permanecem satisfeitos **no design**. O Chat 2 materializou o hook aprovado, mas a confirmação final depende das validações do Chat 3.

## Notion

`Hook`, `Fallback` e `Regra` foram corrigidos inicialmente e novamente após o segundo review da PR #249 para exigir launch provenance completa; re-fetch pós-review PASS em 2026-08-30.