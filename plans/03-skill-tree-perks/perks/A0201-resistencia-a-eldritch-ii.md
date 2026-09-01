# A0201 — Resistência a Eldritch II

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-8160-92fa-ce7c7eb81a53; Gate, Fallback, Hook e Regra corrigidos; re-fetch PASS.
- **Runtime observado:** não há correlação estável de hostile ELDRITCH outcomes. A0201 é **UNAVAILABLE_NODE/não comprável** e herda a indisponibilidade de A0200.

## Contrato canônico

- 1 rank; exige A0200 ≥3.
- Primeiro hostile_direct_damage_outcome ELDRITCH confirmado com dano final positivo arma RPG_ELDRITCH_ANCHOR por 120 ticks.
- A recarga de 240 ticks começa no armamento.
- O próximo outcome_id hostil ELDRITCH **distinto** multiplica somente seu componente ELDRITCH por ×0,80 e consome a Âncora.
- Se esse mesmo segundo outcome criar/renovar um estado hostil ELDRITCH allowlisted, removível e modificável, a duração-base nativa recebe ×0,85 uma vez.
- O parcel de duração é opcional por capability; a mitigação causal é o núcleo obrigatório.

## Transação e deduplicação

O armamento só ocorre após commit do primeiro outcome. O consumo é reservado no segundo outcome e commitado somente se ele continuar elegível, não for cancelado e preservar componente positivo. Rollback conserva a Âncora. O outcome armador nunca consome/rearma a própria Âncora e callbacks duplicados não geram crédito.

## Availability e fallback

Sem A0200 disponível ou sem IDs estáveis para distinguir dois outcomes, A0201 inteira fica indisponível. Não aproximar por próximo callback, tempo, DamageSource semelhante, namespace ou debuff genérico. Se apenas o adapter de estado faltar, preservar a mitigação e omitir somente ×0,85.

## Providers e autoridade

- RPG Skill Tree: Anchor state, cooldown, transação e DamageMitigationResolver.
- Iron's 3.16.3 + Discerning The Eldritch 1.4.3 + Deeper and Darker: Spellbooks 1.3.3: apenas outcomes/estados explicitamente mapeados na versão.
- Black Arcana: Arcane/Corruption Resistance e forecast não são outcomes ELDRITCH.
- Outros providers: N/A até publicar identidade causal compatível.

## Lifecycle

Limpar Âncora, reservas, cooldown derivado inválido e dedup em morte, logout, troca de dimensão, rank loss, respec, rules reload e perda de A0200. Reload compatível pode preservar cooldown persistido somente se o contrato de migração declarar isso.

## Pendências para Chat 2

- **P-A0201-01 BLOQUEANTE:** availability transitiva A0200→A0201.
- **P-A0201-02 BLOQUEANTE:** outcome ledger estável e distinção armador/consumidor.
- **P-A0201-03:** reservation→commit/rollback para mitigação e consumo.
- **P-A0201-04:** adapter allowlisted de duração-base, opcional por provider.
- **P-A0201-05:** lifecycle e testes de cancelamento, duplicação, janela e cooldown.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0200≥3 disponível; transitive fail-closed. |
| Integração global | PASS | Anchor é mitigador transitório distinto do bucket base. |
| Qualidade/identidade | PASS | defesa reativa por dois outcomes distintos. |
| Topologia | PASS | notable defensivo exterior. |
| Especializações | PASS | PP bridge segue política sem dupla contagem. |
| PT-BR | PASS | Âncora e recarga descritas sem ambiguidade. |
| Notion | PASS após correção | gravação e re-fetch confirmados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | estados opcionais por adapter real. |

Os 18 critérios passam **no design**, com correlação causal e rollback obrigatórios.
