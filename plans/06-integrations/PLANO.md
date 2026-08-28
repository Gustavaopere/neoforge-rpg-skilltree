# PLANO — 06 Integrations

Estado: **EM ANDAMENTO**.

## Objetivo

Integrar mods externos por adapters opcionais pequenos, testáveis e semanticamente corretos, sem acoplar o RPG Core às APIs externas.

## Dependências

00 Foundation, 01 Core, 04 Masteries e 05 hooks.

## Etapas de implementação

### 1 — Registry/contrato de adapters
- [ ] detectar presença do mod sem classloading prematuro;
- [ ] declarar capabilities/eventos semânticos;
- [ ] fallback neutro quando ausente.

### 2 — Epic Fight
- [ ] stamina/regen/impact e eventos de combate;
- [ ] impedir fallback vanilla duplicado;
- [ ] testes de ataque uma vez.

### 3 — Iron's Spells 'n Spellbooks
- [ ] casting mastery e Mage;
- [ ] atributos/escolas/gating;
- [ ] confirmar eventos de cast válido/cancelado;
- [ ] não promover candidatos históricos sem runtime.

### 4 — Ars Nouveau
- [ ] casting mastery e Sorcerer;
- [ ] spellcraft/especializações;
- [ ] contrato conjunto com Iron's para stats genéricas.

### 5 — Goety, Malum e Eidolon
- [ ] Goety: ações confirmadas/Soul Energy;
- [ ] Malum: harvesting/reaping e atributos próprios;
- [ ] Eidolon: receita de Crucible concluída;
- [ ] idempotência por evento.

### 6 — Morphs / Identity2
- [ ] permissões de Druid e Metamorph;
- [ ] categorias natural/humanoid/monster/aberrant;
- [ ] blacklist de entidades técnicas/indevidas.

### 7 — Apothic Attributes
- [ ] resolver atributos somente quando disponíveis;
- [ ] ausência não causa crash;
- [ ] boss integration continua separada até prova própria.

### 8 — Create / AE2 / Oritech
- [ ] decidir se cada ecossistema terá adapter runtime;
- [ ] se sim, escolher eventos semanticamente estáveis e mastery/recompensas;
- [ ] se não, manter somente especializações/gateways data-driven;
- [ ] não confundir nomes de perks com efeito em máquinas.

## Matriz de validação

- [ ] cada integração sozinha;
- [ ] cada mod ausente;
- [ ] combinações críticas;
- [ ] dedicated server;
- [ ] nenhuma progressão duplicada.

## Definição de concluído

Todos adapters do escopo possuem presença segura, semântica confirmada e testes; então `PLANO-✅.md`.