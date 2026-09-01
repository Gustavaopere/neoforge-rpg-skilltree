# A0202 — Imbuimento de Eldritch

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-813d-a75f-df237582ef8f; dependências, lanes, Gate, Fallback, Hook, Provider/Mods e Regra corrigidos; re-fetch PASS.
- **Runtime observado:** faltam producer ELDRITCH, HealingResolver geral e hook de componente derivado. O catálogo também rejeita hoje a forma de school de addon normalizada como namespace/escola. A0202 é **UNAVAILABLE_NODE/não comprável**.
- **Dependência adiantada:** A0198 ≥2 continua fechada.

## Contrato canônico

- 3 ranks; após ação/conjuração ELDRITCH direta concluída, janela única de 120 ticks.
- Direct melee outcome elegível adiciona 4% / 8% / 12% da base canônica pré-mitigação, pré-crítico e pré-componentes adicionados.
- O parcel é um único derived_component:ELDRITCH no outcome pai.
- Durante a janela, cura efetiva recebida usa ×0,98 / ×0,96 / ×0,94.
- Benefício e penalidade de cura são inseparáveis. Recast válido apenas renova a expiração.

## Mastery e lanes canônicas

“Eldritch Mastery” não é uma ledger agregada. O gate deve consultar uma eldritch_mastery_lane_id exata derivada de SchoolType.getId(), allowlisted e aceita pelo MasteryLaneCatalog.

O runtime Iron's normaliza escola base como path e escola de addon como namespace/path. Entretanto MasteryLaneCatalog.ironsDiscipline aceita atualmente somente token sem barra. Até reconciliar o catálogo e mapear os IDs reais de Discerning/Deeper Darker, o gate ELDRITCH não é alcançável.

Lanes melee aceitas:

- epicfight:sword;
- epicfight:axe;
- epicfight:spear somente em contato;
- epicfight:dagger;
- epicfight:heavy;
- combat:mace;
- combat:scythe;
- combat:fist somente quando sua cadeia estiver válida.

IDs de gateway como epic_sword não são IDs de mastery.

## Hook e anti-recursão

Somente commit de cast/outcome bem-sucedido abre a janela; tentativa ou cancelamento não. O HealingResolver aplica a penalidade uma vez. O derived component herda action_id, outcome_id e a decisão crítica do pai; não cria DamageSource, nova crítica, Mastery, sustain, Ruptura, proc ou segunda resolução.

## Providers

- Iron's 3.16.3 + Discerning 1.4.3 + Deeper Darker Spellbooks 1.3.3: school/action IDs exatos, sem inferência temática.
- Epic Fight 21.17.3.1 e ledgers combat:* canônicas: classificação melee e mastery.
- Weapons of Miracles 2.0.176: somente arma concreta com mapping versionado.
- Black Arcana atual: não publica outcome ELDRITCH para este contrato.
- Tecnologia, summons, ranged e hazards: N/A/excluídos.

## Pendências para Chat 2

- **P-A0202-01 BLOQUEANTE:** dependency closure de A0198 e availability.
- **P-A0202-02 BLOQUEANTE:** reconciliar school addon namespace/path no MasteryLaneCatalog e publicar mapping exato.
- **P-A0202-03 BLOQUEANTE:** HealingResolver geral e autoritativo.
- **P-A0202-04:** producer ELDRITCH pós-commit e direct melee outcome/component hook.
- **P-A0202-05:** lanes canônicas, combat:fist transitivo, dedup e lifecycle.
- **P-A0202-06:** testes all-or-nothing, recast, cancelamento e anti-proc.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0198, lane ELDRITCH exata e lane melee real. |
| Integração global | PASS | componente derivado no outcome pai + HealingResolver. |
| Qualidade/identidade | PASS | imbuimento híbrido com tradeoff inseparável. |
| Topologia | PASS | ponte ARCANE/OCCULT↔MARTIAL. |
| Especializações | PASS | PP bridge sem dupla contagem; mastery school-specific. |
| PT-BR | PASS | Imbuimento e penalidade de cura explícitos. |
| Notion | PASS após correção | IDs e availability regravados e relidos. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | integra somente school/action/lane concretos. |

Os 18 critérios passam **no design**; a incompatibilidade de lane addon é blocker registrado, não um alias inventado.
