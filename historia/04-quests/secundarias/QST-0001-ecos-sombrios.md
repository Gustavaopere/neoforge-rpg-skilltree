# QST-0001 — Ecos Sombrios

## Estado editorial
CANÔNICO DE REFERÊNCIA / QUEST-TESTE

## Tipo
Oportunidade secundária de investigação.

## Premissa player-safe
Em estágio avançado, um mago ligado à corte pode reunir indícios suficientes de ocorrências incomuns por canais rastreáveis e decidir ou não envolver o jogador.

## Participantes confirmados
- `NPC-0003` Iren Valmor — mago ligado à corte; sua ficha permanece rascunho estruturado até consolidação editorial.

## Proveniência editorial legada
Este dossiê foi escrito originalmente enquanto Severin ainda funcionava como placeholder editorial do cenário técnico. Esse placeholder foi aposentado na migração de 2026-09-15 e não produz qualquer aresta ativa para Aren.

A provenance completa da substituição permanece em `historia/11-ia-e-autoria/15-migracao-npc-0001-severin-aren-2026-09-15.md`. Qualquer vínculo ficcional concreto entre Aren e esta quest exige aprovação própria; o histórico de autoria não é evidência in-universe nem relação de grafo ativa.

## Contexto institucional e geográfico
- `FAC-0001` Corte de Pedra Clara — instituição à qual Iren está ligado;
- `SET-0001` Pedra Clara — assentamento que ancora a Corte;
- `LOC-0001` Região dos Ecos — região editorial da investigação, ainda sem binding de bioma/worldgen.

`LOC-0001` não precisa estar dentro de `SET-0001`. A Corte pode receber relatos de sua zona de influência sem possuir vigilância, jurisdição ou presença física total sobre ela. O binding final de worldgen deve seguir o contrato `11-contrato-geografia-compendio.md` e o catálogo real do Compêndio.

## Matriz mínima obrigatória
A implementação/authoring deve suportar, no mínimo:

1. requisitos nunca satisfeitos;
2. requisitos satisfeitos sem o jogador descobrir a oportunidade;
3. rumor parcial sem investigação;
4. oferta formal recusada;
5. oferta aceita e ignorada;
6. investigação sem encontro com o sujeito/alvo relevante;
7. encontro antes da missão;
8. encontro e não intervenção;
9. diálogo/negociação;
10. hostilidade;
11. morte;
12. resolução por terceiros;
13. deslocamento/transformação da situação antes da chegada;
14. descoberta retrospectiva apenas das consequências.

## Regra de knowledge
Iren não conhece automaticamente identidade, localização, capacidades ou motivações de qualquer sujeito/alvo associado às ocorrências investigadas. O estado que permite a investigação precisa apontar para rumor, testemunha, evidência ou observação concreta. O mesmo vale para qualquer outro ator que entre no caso.

`FAC-0001` não herda automaticamente todo knowledge de `NPC-0003`, e `NPC-0003` não herda automaticamente todo registro da instituição.

## Âncoras de descoberta/evidência
- `EVD-0001` — relatos desencontrados: pode iniciar interesse ou investigação, mas não prova causa/culpa;
- `EVD-0002` — registro cartográfico da corte: pode revelar concentração espacial/temporal de ocorrências, com viés de coleta explícito;
- `EVD-0003` — vestígio de ocupação oculta: pode sustentar investigação de campo em `LOC-0001`, mas não identifica ocupante nem provider.

Essas peças são **rotas possíveis**, não uma sequência obrigatória. O jogador pode chegar por subconjuntos diferentes, encontrar `EVD-0003` antes de qualquer oferta, reconstruir parte do padrão independentemente ou nunca descobrir nenhuma delas. Perda/destruição de uma peça não deve criar soft-lock se outra rota semanticamente válida ainda existir.

Nenhuma peça isolada autoriza `NPC-0003` ou outra instituição a declarar qualquer pessoa culpada, conhecida ou localizada sem uma cadeia causal adicional.

## Lifecycle editorial
O mapeamento de availability/discovery/engagement/resolution, progressão autônoma, reconciliação de entrada antecipada e discovery retrospectiva está em `QST-0001-lifecycle.md`.

## Diálogos relacionados
- `DLG-0002` — calibração de voz/evidência de `NPC-0003`; não é evento canônico;
- `DLG-0003` — rascunho de oferta de investigação, condicionado ao estado real de knowledge/lifecycle;
- `DLG-0001` — calibração legada de Severin; preservada apenas como histórico editorial e **não** utilizável como voz de Aren ou como prova de vínculo de Aren com esta quest.

## Regra de journal
Não mostrar “missão perdida” se o jogador nunca soube que a oportunidade existia.

## Regra de mundo
O conteúdo não congela esperando o jogador. Progressão autônoma deve ser explicitamente declarada, causal e determinística para diagnóstico.

## Spoilers
Motivações verdadeiras, outcomes preferenciais, responsáveis, evidências e consequências concretas devem viver em arquivos de arco/evidência separados para preservar descoberta em gameplay.
