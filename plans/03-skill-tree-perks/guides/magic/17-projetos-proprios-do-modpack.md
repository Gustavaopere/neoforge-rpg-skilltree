# 17. Projetos próprios do modpack — integração mágica canônica

Os quatro projetos próprios integram a auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo registra somente o recorte **mágico**. Para declarar provider/hook, o Chat 1 deve consultar o dossiê completo correspondente.

## Black Arcana — provider mágico próprio principal

[Dossiê completo](../projects/04-black-arcana.md)

Black Arcana é um sistema mágico próprio, não apenas um addon de Iron's/Ars.

- **Canônico:** Arcana Core server-authoritative, resource/cost provider, targeting/effects, cooldown/persistence, data-driven content, Integration Layer com Iron's/Ars/Eidolon/Malum/RPG e World Safety.
- **Casting & UX parcial:** código mergeado; QA manual visual/input ainda não fechada no status auditado.
- **Arcane Danger parcial com componentes reais:** Danger Model, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash.
- Arcane Resistance não é generic magic resistance; Corruption não é Shroud; Strain não é mana.
- Backlash usa dano realmente confirmado e provenance própria; não recursa, não crita, não lifesteala, não gera proc ofensivo nem Mastery.
- Equipment set bonus infrastructure está em `main`, mas não representa fechamento completo de 05A.06.
- RPG hazard provider 05A.10, Rituals, Spell Domains e Progression/Balance não devem ser promovidos além do estado realmente comprovado.

## RPG Skill Tree — progressão, perks e gates mágicos

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- O RPG conserva autoridade de Level/XP/attributes/perk ranks e do runtime de efeitos da árvore.
- A API pública read-only pode expor level, Mastery, classes, especializações, perk ranks e attribute ranks para consumers externos.
- Stage 06 possui Iron's e Goety/Malum/Eidolon formalmente fechados; outras bridges gerais permanecem mistas/abertas.
- Os planos genéricos de `magic-pipeline`, summons e healing/support não são prova automática de hook disponível.
- Uma perk envolvendo Black Arcana, Iron's, Ars, Malum, Eidolon ou Goety deve indicar qual sistema possui a autoridade do cast/recurso e qual parte do RPG apenas fornece gate/effect.

## Enshrouded — Shroud/Flame sem duplicar Black Arcana

[Dossiê completo](../projects/03-enshrouded.md)

- `MagicResistanceService` de mobs corrompidos é canônico e permanece único reducer; adapters mágicos fornecem classificação/evidência, não segunda redução.
- Flame State, Flame Altar e Level 1 Ritual são canônicos; Sanctuary continua aberto.
- Stage 08 de integrações com Ars/Iron's/Goety/Malum/Eidolon é **PLANEJADO** no snapshot auditado.
- Shroud/Exposure/Madness não são Black Arcana Corruption, Arcane Resistance ou Strain.
- Nenhuma conversão automática é permitida.

## Volcanoes — provider ambiental, não sistema mágico

[Dossiê completo](../projects/02-volcanoes.md)

Volcanoes pode fornecer contexto físico para uma perk mágica híbrida — calor, Atmosphere, gases, pressão, geologia, vulcanismo — mas esses valores continuam ambientais.

Não transformar O₂, SO₂, pressão, temperatura ou tectonic stress em mana/Arcane Resistance por inferência. Uma interação exige bridge explícita e preserva a authority do Volcanoes para a grandeza física.

## Regra de pipeline para perk mágica híbrida

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Uma perk híbrida deve escolher **um pipeline principal de cast/dano**. Os demais sistemas são providers/consumers auxiliares. Deduplicar:

- custo;
- cast identity;
- dano confirmado;
- Mastery;
- proc;
- sustain.

Sem bridge real, o componente dependente permanece pending/fail-closed em vez de receber comportamento genérico inventado.
