# Integrações

## Epic Fight — IMPLEMENTED

Há integração runtime e regras de node effects associadas. O objetivo principal é fazer o pipeline de combate RPG coexistir com o pipeline do Epic Fight sem aplicar perks duas vezes no mesmo ataque.

## Iron's Spellbooks — IMPLEMENTED

A integração confirmada conecta progressão Arcana ao mod. O runtime trata gating/acesso, autorização de inscrição permanente baseada em mastery + identidade de Mage e ganho de mastery por casts confirmados via spellbook/scroll.

Não foi comprovado, no adapter auditado, um bônus direto por nome de spell. Ideias antigas como `Echo Cast` e `Overchannel` permanecem especificação/candidatas enquanto não houver implementação correspondente.

## Ars Nouveau — IMPLEMENTED

Existe integração runtime dedicada. A wiki trata efeitos específicos conservadoramente: somente comportamentos comprovados no adapter ou nos node effects devem ser nomeados; bônus mágicos genéricos continuam descritos como hooks genéricos.

## Goety — IMPLEMENTED

A integração usa progressão Occult e eventos reais do mod. `occult_000`/`occult_001` participam de modificadores relacionados a Soul Energy (redução de custo/aumento de ganho); Warlock e Necromancer acrescentam multiplicadores, incluindo benefício de Necromancer relacionado a summons. Casts, mortes atribuídas a servants e comandos confirmados podem alimentar mastery conforme o adapter.

## Malum — IMPLEMENTED

O adapter concede progresso/mastery a partir de colheita/reaping de espíritos confirmada, evitando premiar simples tentativas sem resultado.

## Eidolon: Repraised — IMPLEMENTED

O adapter observa conclusão real de receitas do Crucible e registra progresso/mastery; descoberta da primeira conclusão também é rastreada pelo contrato auditado.

## Identity2 — PARTIAL/VERIFY

Há desenho e contratos de identidade no projeto, mas esta edição da wiki não atribui efeitos nominais sem revalidar o runtime exato.

## Create — SPEC/DATA

Há material de especialização/progressão no projeto. Adapter runtime dedicado não foi comprovado nesta revisão; portanto não é correto prometer que ações de Create já concedem mastery ou recebem perks específicas.

## Applied Energistics 2 — SPEC/DATA

Mesma política de Create: definição/progressão existe, mas runtime dedicado precisa de prova antes de ser anunciado como integração completa.