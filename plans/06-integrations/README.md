# 06 — Integrations

Integrar mods externos por adapters opcionais pequenos, testáveis e semanticamente corretos, sem acoplar o RPG Core às APIs externas. O redesign de 2026-09-12 também cria dois subsistemas first-party que substituem assumptions antigos: **Form Engine** e **Economy V2**.

Authority física corrente: modlist 2026-09-08, 595 entradas, NeoForge 21.1.248. Presença/versão física prevalece sobre os snapshots históricos desta pasta.

## Contratos já concluídos

- `✅-01-adapter-contract.md` — contrato provider-neutral concluído; presença/capability/diagnostics e fail-closed permanecem a base para integrações externas.
- `✅-02-epicfight.md` — Epic Fight `21.17.3.1` permanece authority do hit no contrato validado.
- `✅-10-minecolonies-battle-mages.md` — Battle Mages × Iron's concluído, preservando MineColonies como authority do cidadão/AI e Iron's como authority do cast.
- `✅-11-minecolonies-economy.md` — Economy V1 concluída: ledger virtual por colônia, `MINT`/`RETIRE`, persistência, replay protection, capacidade econômica e inflação/deflação.

## Planos reabertos/redesenhados pela modlist corrente

### 06.06 — First-Party Form Engine

[`06-identity-morphs.md`](06-identity-morphs.md) mantém o nome histórico do arquivo para continuidade documental, mas o conteúdo agora especifica o **Form Engine first-party**.

Identity/Identity2 e Woodwalkers não estão na modlist física atual. Portanto:

- Druid e Metamorph deixam de depender desses providers;
- Druid recebe formas naturais/bestiais e assimilação primal exclusiva;
- Metamorph recebe catálogo corporal mais amplo, inclusive formas naturais + humanoid/monster/undead/aberrant quando aprovadas;
- Entity/NBT/AI externo nunca é copiado cegamente para o jogador;
- Alex's Mobs Continued, Alex's Caves Continued, Ice and Fire e outros providers entram por adapters auditados, não por heurística.

### 06.12 — Economy V2

[`12-economy-v2.md`](12-economy-v2.md) expande a Economy V1 para:

- player/colony/business/escrow accounts;
- moeda física conservada contra o ledger;
- wallets;
- banco e transferências;
- vendors e compra/venda;
- salários e impostos;
- market/auction;
- comércio entre colônias em fase posterior.

MineColonies físico atual é `1.1.1381-1.21.1-snapshot`; os testes provider-present antigos de Economy V1 usaram builds anteriores, então **1.1.1381 precisa ser revalidado antes da expansão**.

Lightman's Currency e Create: Numismatics são referências de arquitetura/código licenciado, não dependencies instaladas. Reuso literal exige provenance/licença registrada e não pode criar uma segunda monetary authority.

## Drift tecnológico obrigatório

AE2 e Oritech foram removidos da modlist física atual. Qualquer plano/teste antigo que os trate como provider ativo deve ser migrado ou ficar fail-closed; não substituir silenciosamente por Tom's Simple Storage/Create.

## Regra permanente

Provider-native first → bridge comprovada → integração própria → fallback seguro. Nenhum provider ausente é simulado por bônus genérico e nenhum teste histórico obriga a reintroduzir um mod removido.