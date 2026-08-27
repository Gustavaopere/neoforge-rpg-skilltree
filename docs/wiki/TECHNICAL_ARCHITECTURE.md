# Arquitetura técnica

Pacote Java canônico: `dev.gustavopere.rpgskilltree`.

## Camadas

1. **Core RPG** — identidade, XP/level, atributos, mastery e serviços.
2. **Data** — loaders de classes, árvores, skills, specializations, progression, boss rewards e node effects.
3. **Gameplay hooks** — combate, magia, mobs, world scaling e eventos semânticos.
4. **Integrations** — adapters opcionais por mod.
5. **Network/UI** — projeção do estado autorizado pelo servidor para o cliente.

## Invariantes

- servidor decide unlocks e progressão;
- IDs data-driven persistidos são tratados como API de save;
- um evento não deve gerar progressão por mais de uma rota;
- adapters opcionais não podem introduzir classloading inseguro;
- efeitos devem poder ser removidos/recalculados sem acumulação fantasma.

## Dados principais

`src/main/resources/data/rpgskilltree/` contém, entre outros, catálogos de archetypes, classes, class choices, boss rewards, node effects/rules, progression, trees, skills e specializations.

## Documentação técnica histórica

`docs/specs/` preserva decisões e desenhos de implementação. Use-a para contexto, mas confronte sempre com `src/` e `plans/STATUS.md` antes de assumir que uma feature já está operacional.