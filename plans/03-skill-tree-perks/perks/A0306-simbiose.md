# A0306 — Simbiose

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183→A0304.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db81b68828cd3ec1d3ec4c

## Contrato
Companheiro natural próprio elegível recebe, por rank, +4% dano (+4/+8/+12%) e mitigação própria de dano recebido 2% (×0,98/×0,96/×0,94). Benefícios existem somente enquanto categoria natural e owner único continuarem válidos.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0304≥1 + companion `NATURAL_COMPANION` próprio. Gate C A0183 e A0304 estão indisponíveis; compra fail-before-spend. Legacy unavailable = 0 PP em gates e reembolsável/migrável.

## Ownership/classifier
A tag planejada `rpgskilltree:natural_companion` não existe na `main`, nem existe ownership adapter universal. São necessários `NATURAL_COMPANION_CLASSIFIER_V1` e `COMPANION_OWNER_RECEIPT_V1` com owner único estável. Vanilla tame, Animal Husbandry/Animal Wellness, Ars familiars, Iron's summons e outros entram somente por adapter explícito.

`ProgressionOwner` do Enshrouded Stage 08.04 é authority de discovery Shroud e não pode ser reutilizado como ownership genérico de companions.

## Pipelines/dedup
Ofensivo e defensivo usam os pipelines canônicos correspondentes, uma vez por companion/outcome. Adapters classificam/normalizam; não aplicam bônus em listeners paralelos.

## Fallback
Sem classifier/owner seguro, companion recebe 0 benefício. Após abertura da closure, se apenas um componente tiver boundary seguro, pode-se omitir somente o outro sem inventar backend.

## Testes Chat 3
1. fail-before-spend A0183/A0304;
2. owner próprio vs outro owner/sem owner/owner ambíguo;
3. classifier natural positivo/negativo;
4. tame status/equipe/proximidade não bastam;
5. +dano e mitigação uma vez por outcome;
6. mudança de owner/category remove efeito imediatamente;
7. Enshrouded ProgressionOwner não interfere;
8. provider absent/reload/respec/multiplayer/dedicated server.

## Handoff Chat 2
Não generalizar ownership de um provider para todos os companions. Sem owner receipt + classifier, manter indisponível.