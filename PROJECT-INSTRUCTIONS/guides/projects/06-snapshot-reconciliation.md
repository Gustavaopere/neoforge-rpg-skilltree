# Snapshot reconciliation — 2026-08-30

Esta nota registra a janela de auditoria usada para construir os dossiês de projetos próprios e os deltas posteriores já reconciliados.

## Baseline atual para o próximo delta

- RPG Skill Tree: `main@f448aa0b4f9df400011873e9ad26771209876ad4`.
- Volcanoes: `main@602e0188c123ac8531d3413a5630daa22e3d761f`.
- Enshrouded: `main@77552a3d7f089a47908c109f5f8c19aff8a0f97d`.
- Black Arcana: `main@07263ae9bad12eba6ed500992991faa36ad598b2`.

Esses SHAs são checkpoints de comparação, não congelamento de verdade. `main` e `plans/STATUS.md` frescos prevalecem em todo novo lote.

## RPG Skill Tree

A auditoria inicial dos dossiês começou em `main@e49a1fa651abecfe096adb03c822482fcf9c3e7b`. Durante a preparação inicial a `main` avançou até `55463a195f8c3a87436399f71db19f29c8e85488`; depois a PR #227 fechou a documentação de projetos próprios em `f448aa0b4f9df400011873e9ad26771209876ad4`.

A reconciliação anterior detectou avanço no Stage 03.06: os PRs #222/#223 implementaram e registraram o gate de drift do catálogo/wiki em CI. O arquivo `06-content-wiki-generation.md` continua aberto como plano integral, portanto somente esse subcomponente comprovado pode ser tratado como canônico; o Stage 03.06 inteiro não foi promovido.

## Volcanoes — delta `1d0da7ae... → 602e0188...`

O snapshot extenso original foi produzido em `1d0da7ae7f19e06f60390fdeb0835720e2e40f1b`. A `main` avançou 30 commits até `602e0188c123ac8531d3413a5630daa22e3d761f`.

Mudanças relevantes para perks:

- PR #79 tornou canônica a identidade hidrotermal exata e a migração de world-upgrade: shield/fissure → iron, stratovolcano → copper, caldera → gold somente quando a causalidade vulcânica é comprovada; tectonic-only permanece generic.
- PR #80 tornou canônico um produtor físico bounded/determinístico de Cu/Fe/Au em host natural, com prova explícita de realização física e recovery/rollback testados.
- Assim, a frase do snapshot antigo de que Volcanoes ainda não demonstrou placement físico próprio desses três metais está **superada**.
- RNS, porém, continua authority de prospecção e de native metal worldgen até o handoff seletivo de ownership ser implementado e aceito. A lifecycle bridge de produção permanece desabilitada/fail-closed; tin/nickel/zinc/silver continuam RNS-owned.
- Stage 06 permanece **PARCIAL**, pois `plans/06-integrations/05-rns.md` continua aberto precisamente nessa transferência seletiva de authority.

## Enshrouded — delta `de145be... → 77552a3d...`

O snapshot extenso original foi produzido em `de145be720f7f500f55e060982693312ed7f7bc3`. A `main` avançou 46 commits até `77552a3d7f089a47908c109f5f8c19aff8a0f97d`.

Mudanças relevantes para perks:

- Stage 05 — Flame Progression está agora **COMPLETO E CANÔNICO**, incluindo `✅-03-sanctuary.md`. Sanctuary/Flame Ward não deve mais ser descrito como apenas planejado.
- PR #41 / merge `77552a3d7f089a47908c109f5f8c19aff8a0f97d` tornou **06.01 Story State** um subcomponente canônico: estado narrativo server-global versionado, owner-scoped por `ProgressionOwner`, encounter UUID estável, transições one-way `AVAILABLE → ACTIVE → DEFEATED|ABORTED`, defeat/reward issuance idempotentes e recovery fail-closed de encounter ACTIVE órfão após restart.
- Isso **não promove Stage 06 inteiro**. Boss provider, first manifestation, Lich Skull/reward path e demais tasks continuam dependentes de seus próprios fechamentos.

## Black Arcana

Não houve avanço em relação ao snapshot auditado `07263ae9bad12eba6ed500992991faa36ad598b2` durante esta reconciliação.

## Regra de uso — delta obrigatório

O SHA anotado em cada dossiê representa a base usada para sua análise extensa. Quando `main` avançar, o Chat 1 deve:

1. consultar `plans/STATUS.md` fresco;
2. comparar o novo SHA contra este baseline;
3. identificar somente planos/subsistemas alterados;
4. inspecionar código/testes/CI quando necessário para provar estado/hook;
5. atualizar o status do subcomponente sem promover o Stage inteiro indevidamente;
6. **extrair toda capacidade jogável nova ou semanticamente alterada, ainda que nenhuma perk atual a mencione**;
7. classificá-la pela [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md) antes de fechar o lote.

Essa atualização incremental evita reauditoria histórica integral e, ao mesmo tempo, impede que capacidades novas de projetos em desenvolvimento passem despercebidas.
