# 08 — Quest, Narrative & Progression Hooks

Permitir quests, NPCs e progressão narrativa consultarem/concederem progresso por APIs estáveis sem transformar FTB Quests, Easy NPC, MineColonies, KubeJS ou qualquer outro mod externo em dependência rígida do RPG Core.

O Stage 08 agora possui duas camadas:

1. **Quest-facing foundation** — query API, rewards, condições, idempotência, adapters e diagnostics;
2. **Narrative & Society Core** — estado narrativo persistente, cronologia, conhecimento parcial, relações, facções, governo, leis, opinião pública, consequências e campanha não linear.

## Perfil narrativo externo escolhido

Ver [`DEPENDENCIAS-NARRATIVA.md`](DEPENDENCIAS-NARRATIVA.md).

- Easy NPC Bundle — NPCs/diálogos/interação;
- FTB Quests — diário/UI;
- MineColonies — sociedade física/colônia;
- KubeJS — authoring/protótipos/glue.

Eles são requeridos para o perfil completo da campanha quando a feature correspondente for usada, porém o jar base continua tratando integrações por adapters opcionais.

## Ordem de implementação

### Fundação original

1. `✅-01-public-query-api.md` — API pública de consulta;
2. `02-progression-rewards.md` — rewards/mutations canônicas;
3. `03-data-driven-conditions.md` — condições declarativas;
4. `04-idempotency-ledger.md` — replay/idempotência de rewards/hooks;
5. `05-ftbquests-npc-adapters.md` — política comum dos adapters externos;
6. `06-authoring-diagnostics.md` — ferramentas básicas de authoring.

### Narrative & Society Core

7. `07-narrative-society-master-plan.md` — visão mestre, campanha, arquitetura e acceptance Severin;
8. `08-narrative-domain-authority.md` — domínio/invariantes/authority;
9. `09-event-ledger-chronology.md` — Event Ledger e ANTES/DEPOIS;
10. `10-knowledge-secrets-witnesses-evidence.md` — conhecimento não onisciente;
11. `11-npc-memory-relationships-companions.md` — memória e relações multidimensionais;
12. `12-factions-ideologies-institutions-reputation.md` — facções/instituições/ideologias;
13. `13-settlements-governance-laws-public-opinion.md` — governos, leis e opinião pública;
14. `14-social-propagation-unrest-consequences.md` — propagação, tensão e consequências;
15. `15-choice-consequence-engine.md` — SIM/NÃO/ANTES/DEPOIS e failure-forward;
16. `16-easy-npc-adapter.md` — NPC/diálogo;
17. `17-ftb-quests-journal-adapter.md` — journal/UI;
18. `18-minecolonies-society-adapter.md` — colônia/cidadãos;
19. `19-kubejs-authoring-adapter.md` — scripting/authoring;
20. `20-provider-integration-matrix.md` — integração narrativa do modpack;
21. `21-campaign-eras-epilogues.md` — Crônicas da Concordância Quebrada, eras e finais;
22. `22-data-schemas-localization-content-pack.md` — conteúdo data-driven/PT-BR;
23. `23-network-ui-authoring-diagnostics.md` — sync seguro e debugging;
24. `24-hardening-migrations-performance-tests.md` — migrations, performance, GameTests e dedicated server.

## Princípios permanentes

- FTB Quests é diário/UI, não authority.
- Easy NPC é personagem/diálogo, não storage de história.
- MineColonies continua authority da colônia física; Narrative Core não duplica sua simulação.
- KubeJS é authoring/protótipo; não banco de dados canônico.
- Uma causa, uma mutation/ledger entry canônica.
- NPC/boss encontrado ou morto antecipadamente não deve quebrar a campanha.
- `SIM`, `NÃO`, `ANTES` e `DEPOIS` são rotas de primeira classe.
- Knowledge é por ator; nenhuma facção sabe magicamente tudo.
- Failure-forward é requisito estrutural.
- Provider-native first: narrativa não funde recursos/mecânicas distintas apenas por semelhança temática.
- Todo conteúdo player-facing oficial deve possuir PT-BR.
