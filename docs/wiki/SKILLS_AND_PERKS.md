# Skills e perks

## Modelo data-driven

Os 512 nós da árvore principal ficam em `src/main/resources/data/rpgskilltree/skills/main/`. Cada JSON é a fonte canônica para o próprio ID, posição/estrutura e campos declarados. O layout é gerado por `scripts/generate-tree-skeleton.py` e auditável em `generated/main-tree-layout.json`.

Os efeitos de atributo são uma camada separada em `src/main/resources/data/rpgskilltree/node_effects/`; a revisão atual possui 119 entradas declaradas nesses packs. Outros comportamentos podem existir em handlers runtime e não devem ser inferidos apenas pelo nome do nó.

## Famílias da árvore principal

Core, Martial, Vitality, Healing, Arcane, Engineering, Mining, Survival, Summoning, Occult, Logistics, Agility, Bridges híbridas e Keystones.

## Como ler uma perk

Ao documentar ou alterar um nó, verifique em conjunto:

- ID estável do nó;
- família/árvore e posição;
- custo/rank quando aplicável;
- pré-requisitos e gateways;
- efeito em `node_effects` quando houver;
- handler runtime quando o efeito não for puramente atributo;
- compatibilidade opcional;
- reversibilidade no respec.

## Perks cross-mod

Uma perk pode alterar um atributo externo diretamente, como `irons_spellbooks:spell_power`, `epicfight:stamina` ou atributos de Malum. Isso comprova integração com aquele **atributo**, mas não autoriza inferir suporte nominal a toda ação, spell ou máquina daquele mod.

Exemplo: `technomancer/create_resonance` possui hoje um efeito real sobre `irons_spellbooks:spell_power`; o nome `create_resonance` não prova, sozinho, que uma máquina Create dispara esse efeito ou concede mastery.

## Fontes canônicas

- Inventário/estrutura: `skills/main` + `generated/main-tree-layout.json`.
- Efeitos de atributo: `node_effects/*.json`.
- Gating e comportamento: regras/loaders/handlers runtime.

Consulte [PERK_CATALOG.md](PERK_CATALOG.md) para todos os IDs da árvore principal e [EFFECT_CATALOG.md](EFFECT_CATALOG.md) para os efeitos de atributo atualmente declarados.