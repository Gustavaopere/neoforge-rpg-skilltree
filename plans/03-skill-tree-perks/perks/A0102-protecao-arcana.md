# A0102 — Proteção Arcana

**Estado Chat 1:** DESIGN APROVADO  
**Runtime atual:** consumer genérico ainda deve ser implementado; availability fail-closed até binding real.  
**Notion:** https://app.notion.com/p/3c569db9f0db8106b28dd2036ec65d51

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY ↔ ARCANE.
- Ramo: Mitigação por Tipo — Mágico.
- Camada: 2; função: Ponte.
- Ranks: 4; custo 1 PP/rank.
- Pré-requisitos: A0088 Constituição ≥ 2 ranks + acesso real ao corredor ARCANE + Gateway VITALITY.

## Contrato congelado

Cada rank concede **+2% de redução de dano mágico genérico**, até **+8%**. A0102 aplica uma única contribuição genérica por `DamageContainer`/root. A classificação primária é `DamageSource.is(Tags.DamageTypes.IS_MAGIC)` / `neoforge:is_magic`; adapters versionados podem mapear fontes provider-native que não publiquem corretamente a tag, sempre por identidade causal explícita.

A0102 **não** é Arcane Resistance, Corruption Resistance, resistência de escola ou resistência elemental específica. Se um provider possuir resistência específica independente, a composição ocorre no pipeline do owner; aliases da mesma resistência genérica não podem gerar double-dip.

`ARCANE_BACKLASH`, `BLOOD_MAGIC_COST`, resource costs e hazards terminalmente excluídos não são elegíveis.

## Provider, versões, hook e authority

- NeoForge atual: `21.1.248`.
- Iron's Spells 'n Spellbooks: `1.21.1-3.16.3`.
- Ars Nouveau canônico da modlist/guia: `5.13.1`.
- Consumer: RPG-owned `DamageMitigationResolver` em `LivingDamageEvent.Pre`.
- Black Arcana continua owner de Arcane/Corruption Resistance e Backlash. O forecast server-authored de Arcane Resistance é read-only/presentation e **nunca** authority de A0102.

A fixture atual do repositório ainda fixa Ars `5.13.0` em `gradle.properties`; isso é drift de fixture/build, não licença para rebaixar o design canônico a 5.13.0.

## Causalidade, deduplicação e anti-abuso

- uma contribuição A0102 por root;
- classifier resolve antes do reducer;
- adapters duplicados não podem aplicar novamente a mesma resistência genérica;
- spell visual, caster, item, namespace ou nome de school não classificam a fonte por heurística;
- fonte modded desconhecida falha fechado;
- Backlash/custos não geram crítico, sustain, Mastery ou autoria ofensiva por A0102.

## Availability e fail-closed

Sem consumer RPG-owned instalado e sem classifier seguro, o node é indisponível/não comprável. Purchase deve falhar antes de gasto de PP. Provider ausente ou versão fora do contrato desativa apenas a rota correspondente; não existe bônus mágico genérico inventado.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: `ProgressionService`/Stage 04 governa gateway e compra; A0102 só consome o pipeline defensivo.
- Black Arcana: fornece resistência/hazard próprios e forecast read-only; **NÃO DEVE SER INTEGRADO** como reducer genérico.
- Enshrouded: Shroud/MagicResistance permanecem provider-owned; não viram A0102 por tema.
- Volcanoes: hazards ambientais não viram magia genérica.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS.
2. Integração global — PASS, um resolver defensivo.
3. Qualidade/identidade — PASS, resistência mágica genérica distinta de canais específicos.
4. Topologia — PASS, ponte VITALITY↔ARCANE.
5. Especializações — PASS/N/A; não cria grant paralelo.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS com unknown fail-closed.

Authority, versões, causalidade, dedup, anti-abuso, fallback, availability, lifecycle e pipeline estão definidos sem substituição genérica de provider ausente.

## Pendências para Chat 2

- `P-A0102-01`: implementar classifier mágico genérico (`neoforge:is_magic` + adapters explícitos) e consumer once/root.
- `P-A0102-02`: implementar availability server-authoritative e purchase fail-closed.
- `P-A0102-03`: reconciliar a fixture Ars do projeto de `5.13.0` para o contrato canônico `5.13.1`, validando API real; se divergir, registrar evidência/fail-closed, não redesenhar.
- `P-A0102-04`: testes de dedup/aliases, provider absent/present, Backlash/resource-cost exclusions e composição com resistências específicas.

## Testes exigidos ao Chat 3

Validar uma aplicação/root, fontes vanilla tagueadas, adapters Iron's/Ars realmente causais, provider/version gates, Black Arcana excluído do reducer genérico, purchase sem gasto quando indisponível, rank/respec/reload, multiplayer isolation, NeoForge GameTests, build, JAR e dedicated-server smoke.

## Atualização de implementação — Chat 2 (2026-09-02)

**Estado:** `CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

- `P-A0102-01` e `P-A0102-02` foram resolvidas com classifier conservador: a fonte precisa ser `neoforge:is_magic`, não técnica e possuir atacante `LivingEntity` hostil causal explícito. Self, attackerless terminal, resource-cost e roots desconhecidos não são promovidos por heurística.
- Black Arcana continua fora do reducer genérico; nenhum ID/tag interno de Backlash foi inventado. `ARCANE_BACKLASH` terminal/sem autoria ofensiva continua inelegível pelo fail-closed causal.
- `P-A0102-03` foi resolvida: a fixture Ars Nouveau foi alinhada ao contrato canônico `5.13.1`.
- A0102 foi retirada de `UNAVAILABLE_NODE` somente após esse endurecimento. `P-A0102-04` permanece para validação final do Chat 3, incluindo provider present/absent, aliases e resource-cost exclusions.
- Chat 2 não executou a bateria final, não declarou `IMPLEMENTAÇÃO CONFIRMADA` e não fez merge.
