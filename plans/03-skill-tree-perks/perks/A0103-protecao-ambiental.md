# A0103 — Proteção Ambiental

## Estado

- **Design:** APROVADO após hardening em 2026-08-31.
- **Notion:** `3c569db9-f0db-811f-bc81-dc88b3784913`; corrigido e verificado pós-escrita.
- **Runtime:** consumer/allowlist ainda precisam ser implementados; availability fail-closed até lá.

## Contrato canônico

- Gateway VITALITY + A0088 Constituição ≥2 + acesso real ao corredor SURVIVAL.
- 4 ranks, +2% por rank, teto próprio +8%.
- Só fontes ambientais externas, não elementais, explicitamente allowlisted.
- Exclui temperatura, fogo/calor/frio, hidratação, fome, afogamento, sufocamento, Void/kill, Shroud/Corruption, gases/pressão com proteção própria e custos fisiológicos.
- Não existe heurística `source.getEntity()==null`.

## Boundary de implementação

Criar tag data-driven `rpgskilltree:environmental` ou equivalente governado pelo RPG e consumir em `LivingDamageEvent.Pre` pelo mesmo `DamageMitigationResolver`. Cada DamageType entra apenas após auditoria explícita; adapters de providers podem mapear uma fonte concreta, nunca uma categoria temática inteira.

## Cobertura de providers

- Minecraft/NeoForge: pipeline/allowlist inicial.
- Cold Sweat e Thirst: temperatura/hidratação continuam owner próprio e ficam fora.
- Volcanoes: pressão, gases, calor e geologia mantêm suas proteções sem redução genérica presumida.
- Enshrouded: Shroud/Exposure/Corruption continuam próprios.
- Fonte ambiental também física pode compor A0092 somente se ambas as classificações explícitas forem verdadeiras.

## Pendências para Chat 2

- `P-A0103-01`: materializar allowlist inicial de DamageTypes revisados e consumer no Pre.
- `P-A0103-02`: testes negativos de fisiologia/elemento/Volcanoes/Enshrouded e ausência-de-atacante heurística.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Constituição + corredor SURVIVAL. |
| Integração global | PASS | preserva owners ambientais. |
| Qualidade/identidade | PASS | categoria allowlisted, não universal. |
| Topologia | PASS | bridge VITALITY↔SURVIVAL. |
| Especializações | PASS | bridge PP governada. |
| PT-BR | PASS | exclusões explícitas. |
| Notion | PASS | hardening persistido. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | owner por categoria preservado. |

Os 18 critérios passam no design; nenhuma fonte nova entra por inferência.