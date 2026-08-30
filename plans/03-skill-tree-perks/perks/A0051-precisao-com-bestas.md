# A0051 — Precisão com Bestas

## Estado

- **Design:** APROVADO após reauditoria provider→árvore e correção de provenance de lançamento.
- **Notion:** `3c569db9-f0db-8135-903d-db954d9f8087`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL; o crítico físico existe, mas precisa exigir launch receipt CROSSBOW confirmado e a linha continua dependente dos blockers de Mastery/alcançabilidade de A0049.

## Contrato canônico

- A0049 ≥1 + gateway `epic_crossbow`.
- +3% de chance crítica por rank, máximo +9%.
- Somente projétil físico CROSSBOW causalmente atribuído ao `ServerPlayer` **e correlacionado a um lançamento CROSSBOW confirmado**.
- Vanilla `CrossbowItem`/subclasse classifica a arma, mas `owner + CrossbowItem` isoladamente não prova provenance do projectile/root.
- Externos exigem capability/categoria provider-native ou mapping versionado explícito e receipt de lançamento correspondente.
- Uma única resolução crítica/root action; Apothic, quando usado como backend, participa da mesma resolução.
- `ARCANE_BACKLASH`, spell projectiles, ricochetes/derivados, projéteis reemitidos sem launch receipt, fake players e projéteis de allies/bodyguards Mobstein são inelegíveis.

## Auditoria técnica

O adapter classifica vanilla por `CrossbowItem` e preserva owner real, mas o review da PR #249 identificou um gap: quando um add-on cria `AbstractArrow` derivado com owner jogador e metadata de besta sem `ArrowLooseEvent` correlacionado, o bridge pode sintetizar um pending launch neutro e ainda chegar ao resolver crítico. Portanto, o caminho não pode ser tratado como plenamente coerente até exigir provenance `ServerPlayer → launch CROSSBOW confirmado → projectile correlacionado → impacto`.

O antigo fallback documental `rpgskilltree:crossbows` permanece removido: tag paralela não governada não é classificador canônico.

A0051 herda integralmente a alcançabilidade de A0049. Corrigir somente o namespace `combat:crossbow` ↔ `epicfight:crossbow` não basta: enquanto `P-A0049-01` não existir no runtime, jogador novo não possui producer finite-discovery para alcançar Mastery CROSSBOW 60 e não chega legitimamente a A0051/A0052–A0054.

### Provider→árvore

- RPG Skill Tree: resolver crítico, launch/root correlation e dedup são authority canônica.
- Black Arcana: Backlash terminal não é projectile/root CROSSBOW.
- Enshrouded/Volcanoes: não classificam projétil nem crítico.
- Mobstein 5.4.4: projectile de companion permanece Mobstein-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal quando a provenance CROSSBOW for real.

## Pendências para Chat 2

- **P-A0051-01:** exigir launch receipt CROSSBOW server-authoritative antes de aplicar bônus crítico; projectile derivado/reemitido sem correlação de lançamento fica fail-closed mesmo com owner jogador e metadata `CrossbowItem`.
- **Herdada P-A0049-01:** implementar producer finite-discovery da Mastery canônica `epicfight:crossbow`, deduplicado por tipo hostil inédito; 6 tipos → 60, 8 → 80.
- **Herdada P-A0049-02:** reconciliar `combat:crossbow` do architecture catalog com `epicfight:crossbow`; não criar duas Masteries.
- Adicionar/regredir teste provider-present/absent para classificação CROSSBOW, projectile derivado sem launch receipt, produção de Mastery, gate A0049→A0051 e resolução crítica única.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0049 ≥1 + gateway `epic_crossbow`; blockers `P-A0049-01/02` impedem considerar a progressão runtime alcançável antes da correção. |
| 2. Integração global | **PASS** | Usa o resolver crítico canônico; não lê/escreve Shroud, Arcane state, hazards, Stamina ou recursos paralelos. Backlash/companions/derived projectiles ficam fora. |
| 3. Qualidade e identidade | **PASS** | É o node incremental de precisão do ramo CROSSBOW, com bônus pequeno e função de caminho; não se apresenta como Notable/Capstone e não duplica uma segunda rolagem. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 2 após A0049 no gateway de Bestas; sem teleporte de região ou dependência circular. |
| 5. Especializações | **PASS** | Permanece ramo MARTIAL/BESTAS universal; não transforma Epic Fight/WoM em classe e não invade especialização mágica/tecnológica. |
| 6. PT-BR | **PASS** | Nome/efeito/requisitos visíveis em PT-BR; IDs, classes e hooks técnicos permanecem em inglês apenas na documentação técnica. |
| 7. Notion completo | **PASS** | Campos pertinentes preenchidos; `Hook`, `Fallback` e `Regra` endurecidos para launch provenance e re-fetch pós-review confirmado em 2026-08-30. |
| 8. NeoVitae | **PASS** | Nenhuma dependência, provider ou fallback NeoVitae. |
| 9. Cobertura modlist/providers | **PASS** | RPG, Epic Fight, Apothic/WoM quando aplicáveis, Black Arcana, Enshrouded, Volcanoes e Mobstein foram dispostos; não há bridge temática inventada. |

Os 18 critérios técnicos cumulativos permanecem satisfeitos **no design**, com os gaps de implementação acima explicitamente fail-closed e destinados ao Chat 2; nenhum deles é ocultado como implementação confirmada.

## Notion

`Hook`, `Fallback` e `Regra` foram corrigidos inicialmente e novamente após o segundo review da PR #249 para exigir launch provenance completa; re-fetch pós-review PASS em 2026-08-30.
