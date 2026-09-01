# A0204 — Maestria de Eldritch

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-8111-8a0d-d79e176154dd; dependency closure, mastery lane, availability e Gate A/B/C corrigidos; re-fetch PASS.
- **Runtime observado:** A0203 e as rotas profundas não estão disponíveis, e a lane ELDRITCH exata de addon não é válida no catálogo atual. A0204 é **UNAVAILABLE_NODE/não comprável**.

## Contrato canônico

- Capstone exterior, 1 rank, custo 3.
- A compra exige A0203, eldritch_mastery_lane_id exata ≥80 e uma rota profunda: A0199=1, A0201=1 ou A0202≥2.
- A0204 não concede dano, resistência, recurso, estado, sanidade, corrupção, duração ou mitigação.
- Possuir A0204 satisfaz **somente Gate C** da Specialist Eldritch.
- A Specialist abre apenas com Gate A dos fundamentos, Gate B ≥100 PP válidos em SPECIALIST_REGION:ELDRITCH e Gate C A0204.

## Mastery e availability

Não existe “Eldritch Mastery” agregada. O gate usa uma lane exata de SchoolType, allowlisted e aceita pelo MasteryLaneCatalog. Enquanto a normalização namespace/path dos addons não for reconciliada ou qualquer dependency closure estiver indisponível, o terminal não pode ser comprado.

## Specialist e respec seguro

SpecialistGateResolver publica somente terminal_id=ARCANE/ELDRITCH e reavalia Gate A/B/C em compra, login, respec, migração e mudança de capability. PP de bridge não contam simultaneamente em múltiplas regiões sem mapping explícito.

Enquanto qualquer perk interna da Specialist estiver possuída:

- reembolsar primeiro a Specialist;
- bloquear refund de A0204;
- bloquear refund de fundamentos/dependências;
- bloquear redução de Gate B abaixo de 100.

## Providers

- RPG Skill Tree: AvailabilityResolver, MasteryLaneCatalog, PP regions e SpecialistGateResolver.
- Iron's/Discerning/Deeper Darker: somente a lane exata quando o catálogo suportar seu school id.
- Black Arcana e demais mods: não possuem authority sobre o gate da Specialist.

## Pendências para Chat 2

- **P-A0204-01 BLOQUEANTE:** unavailable-node transitivo da cadeia A0198/A0199/A0201–A0203.
- **P-A0204-02 BLOQUEANTE:** lane ELDRITCH exata e catálogo de addon corrigido.
- **P-A0204-03:** publicar terminal ARCANE/ELDRITCH no SpecialistGateResolver.
- **P-A0204-04:** respec seguro e reavaliação por capability/reload.
- **P-A0204-05:** testes Gate A/B/C, PP=99/100, border hopping e dependency loss.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | dependency closure completa + lane 80. |
| Integração global | PASS | terminal publica apenas Gate C. |
| Qualidade/identidade | PASS | conclusão de domínio, sem bônus oculto. |
| Topologia | PASS | capstone exterior ARCANE/ELDRITCH. |
| Especializações | PASS | Gate A/B/C e respec seguro explícitos. |
| PT-BR | PASS | Maestria nomeia terminal, não ledger agregada. |
| Notion | PASS após correção | gravação e re-fetch confirmados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | gate permanece authority do RPG. |

Os 18 critérios passam **no design**; o terminal indisponível não pode ser comprado como no-op.
