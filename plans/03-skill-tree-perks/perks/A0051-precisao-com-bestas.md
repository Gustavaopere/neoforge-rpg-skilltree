# A0051 — Precisão com Bestas

## Estado

- **Design:** APROVADO após reauditoria provider→árvore e correção de provenance de lançamento.
- **Notion:** `3c569db9-f0db-8135-903d-db954d9f8087`.
- **Runtime:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3** para o contrato disponível no provider atual. O bônus crítico CROSSBOW exige receipt de lançamento correlacionado; projétil derivado/reemitido sem launch receipt não recebe a parcela A0051.

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

A alcançabilidade de A0049 já possui producer finite-discovery e ledger canônica `epicfight:crossbow` na linha predecessora; não existe segunda Mastery.

### Provider→árvore

- RPG Skill Tree: resolver crítico, launch/root correlation e dedup são authority canônica.
- Black Arcana: Backlash terminal não é projectile/root CROSSBOW.
- Enshrouded/Volcanoes: não classificam projétil nem crítico.
- Mobstein 5.4.4: projectile de companion permanece Mobstein-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal quando a provenance CROSSBOW for real.

## Pendências técnicas

- **RESOLVIDA P-A0051-01:** launch receipt CROSSBOW server-authoritative obrigatório para a parcela A0051 do crítico.
- **RESOLVIDAS herdadas P-A0049-01/02:** producer finite-discovery e namespace único `epicfight:crossbow` já existem na linha predecessora.
- Nenhuma pendência bloqueante restante neste lote.

## Implementação e validação

- [x] Hook implementado sobre `ArrowLooseEvent → PendingLaunch confirmado → EntityJoinLevelEvent`.
- [x] Gate/provenance implementados para a parcela crítica A0051.
- [x] Provider-native / resolver crítico canônico preservado.
- [x] Fallback/fail-closed implementado para projectile sem launch receipt.
- [x] Deduplicação/root compartilhado preservados para Multishot correlacionado.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressões no workflow do lote.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge GameTests e adapter tests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS** | A0049 ≥1 + gateway `epic_crossbow`; a linha predecessora fornece a Mastery/gateway necessários. |
| 2. Integração global | **PASS** | Usa o resolver crítico canônico; não lê/escreve Shroud, Arcane state, hazards, Stamina ou recursos paralelos. Backlash/companions/derived projectiles ficam fora. |
| 3. Qualidade e identidade | **PASS** | Node incremental de precisão CROSSBOW; não duplica segunda rolagem. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 2 após A0049 no gateway de Bestas. |
| 5. Especializações | **PASS** | MARTIAL/BESTAS universal; não transforma provider em classe. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra endurecidos e persistência já confirmada no fechamento de design. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Providers e boundaries pertinentes dispostos sem bridge temática inventada. |

## Notion

`Hook`, `Fallback` e `Regra` foram corrigidos inicialmente e novamente após o segundo review da PR #249 para exigir launch provenance completa; re-fetch pós-review PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

Revisão do código contra o dossiê e bateria automatizada real concluídas. O HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af` passou JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke. O node fica **IMPLEMENTAÇÃO CONFIRMADA**; o merge só ocorre após o HEAD documental final também obter CI verde.