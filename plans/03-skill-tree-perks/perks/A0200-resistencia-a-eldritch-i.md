# A0200 — Resistência a Eldritch I

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-814f-91ff-c69842ed2ec0; Dependências, Gate, Fallback, Hook, Provider/Mods e Regra corrigidos; re-fetch PASS.
- **Runtime observado:** não há classificador hostil ELDRITCH nem binding RPG_ELDRITCH_RESISTANCE na main auditada. A0200 é **UNAVAILABLE_NODE/não comprável**.
- **Dependência adiantada:** A0198 está fora do recorte já fechado pelos outros chats; sua rota permanece bloqueada. O Gateway VITALITY não substitui capability de provider.

## Contrato canônico

- Até 4 ranks: +4% / +8% / +12% / +16%.
- A contribuição entra uma única vez no bucket RPG_ELDRITCH_RESISTANCE.
- O bucket mitiga somente o componente hostil explicitamente classificado ELDRITCH.
- Mitigadores realmente distintos compõem multiplicativamente depois do bucket; não existe cap defensivo global implícito.
- Arcane Resistance, Corruption Resistance, curse resistance, Strain, Backlash, Soul Energy e estados ocultistas continuam eixos separados.

## Gate e availability

A compra exige:

1. A0198 ≥1 legitimamente disponível **ou** Gateway VITALITY ativo;
2. adapter versionado que publique componente hostil ELDRITCH;
3. binding server-side no DamageMitigationResolver.

Sem 2 e 3, o node inteiro fica indisponível. A rota VITALITY permite topologia defensiva, mas nunca autoriza gastar ponto em um efeito no-op. A rota A0198 só abre quando a própria A0198 for concluída.

## Providers e autoridade

- RPG Skill Tree: availability, rank, bucket canônico e aplicação única.
- Iron's 3.16.3, Goety 3.1.4, Malum 1.8.2 e Eidolon 0.5.0.2: candidatos somente para outcomes concretos versionados.
- Black Arcana: a integração atual de hazard/progression/mastery e o forecast de Arcane Resistance não publicam BLACK_ARCANA_ELDRITCH_OUTCOME. Arcane/Corruption Resistance não alimentam A0200.
- Enshrouded e Volcanoes: hazards próprios; não são Eldritch por proximidade temática.
- Mods tecnológicos: N/A; dano de máquina, fake player ou automação não ganha classificação ELDRITCH.

## Hook e lifecycle

O AvailabilityResolver reavalia adapter e binding em login, reload de datapack/regras, mudança de providers, respec e perda de dependência. Em outcome elegível, o DamageMitigationResolver classifica uma vez, agrega A0200 ao bucket e aplica o bucket uma vez somente ao componente ELDRITCH.

## Pendências para Chat 2

- **P-A0200-01 BLOQUEANTE:** implementar invariant UNAVAILABLE_NODE no purchase/gate.
- **P-A0200-02 BLOQUEANTE:** classificador hostile ELDRITCH versionado com outcome/component identity.
- **P-A0200-03:** binding único RPG_ELDRITCH_RESISTANCE no DamageMitigationResolver.
- **P-A0200-04:** dependency lifecycle de A0198/VITALITY e provider reload.
- **P-A0200-05:** testes provider-present/absent, mixed components, dedup e buckets distintos.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0198 ou VITALITY mais capability real; upstream permanece fechado. |
| Integração global | PASS | bucket defensivo único no resolver canônico. |
| Qualidade/identidade | PASS | defesa específica contra ELDRITCH, sem equivalência temática. |
| Topologia | PASS | ponte VITALITY/OCCULT-ARCANE, não terminal. |
| Especializações | PASS | PP bridge não conta em múltiplos thresholds sem whitelist. |
| PT-BR | PASS | Resistência a Eldritch preservada como eixo próprio. |
| Notion | PASS após correção | gravação e re-fetch confirmados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | integra somente outcomes versionados; demais são N/A/fail-closed. |

Os 18 critérios passam **no design**; availability impede compra inútil.
