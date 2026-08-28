# PLANO — 04 Classes, Masteries & Specializations

Estado: **EM ANDAMENTO**.

## Objetivo

Resolver classes emergentes de maneira determinística, medir prática real por mastery e abrir especializações/gateways sem locks arbitrários ou progressão duplicada.

## Base atual

Há 23 definições de classe e 25 especializações data-driven auditadas.

## Dependências

01 RPG Core, 03 Skill Tree e eventos semânticos de 05/06.

## Etapas de implementação

### 1 — Resolver de classes
- [ ] calcular classes a partir de domínios/nós/masteries;
- [ ] suportar pure, hybrid, hybrid_abnormal e provider_identity;
- [ ] resultado independente da ordem de eventos.

### 2 — Confluências
- [ ] validar classes adjacentes sem bridge extra;
- [ ] validar confluências distantes e custo de 10 quando definido;
- [ ] mensagens de requisito claras.

### 3 — Mastery
- [ ] catálogo canônico de mastery IDs;
- [ ] ações confirmadas concedem progresso uma vez;
- [ ] eventos cancelados/tentativas falhas não concedem mastery;
- [ ] caps/curvas e persistência.

### 4 — Provider identities
- [ ] Mage = `arcane_000` + mastery Iron's conforme dados;
- [ ] Sorcerer = `arcane_000` + mastery Ars conforme dados;
- [ ] requisitos data-driven, sem hardcode divergente.

### 5 — Especializações e gateways
- [ ] validar requisitos e mod presente quando necessário;
- [ ] desbloqueio/revogação coerente após respec;
- [ ] não anunciar runtime de integração que só exista em dados.

### 6 — Árvores próprias
- [ ] Technomancer, Warlock, Druid e Metamorph mantêm nomes/descrições/rules alinhados aos dados;
- [ ] pactos do Warlock são mutuamente coerentes e refazíveis conforme regra;
- [ ] morph permissions de Druid/Metamorph seguem categorias e blacklist.

## Testes

- [ ] cada uma das 23 classes em limite abaixo/exato/acima;
- [ ] combinações simultâneas;
- [ ] mastery idempotente;
- [ ] respec recalcula identidades;
- [ ] mods externos ausentes.

## Definição de concluído

Resolver determinístico, masteries confiáveis, 23 classes e especializações do escopo validadas; então `PLANO-✅.md`.