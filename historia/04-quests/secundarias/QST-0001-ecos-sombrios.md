# QST-0001 — Ecos Sombrios

## Estado editorial
CANÔNICO DE REFERÊNCIA / QUEST-TESTE

## Tipo
Oportunidade secundária de investigação.

## Premissa player-safe
Em estágio avançado, um mago ligado à corte pode detectar indícios de atividade sombria em determinada região e decidir ou não envolver o jogador.

## Participantes
- `NPC-0001` Severin
- `NPC-0003` Iren Valmor — mago ligado à corte; sua ficha permanece rascunho estruturado até consolidação editorial.

## Contexto institucional
- `FAC-0001` Corte de Pedra Clara — instituição à qual Iren está ligado;
- `SET-0001` Pedra Clara — assentamento que ancora a Corte.

A região investigada não precisa estar dentro de `SET-0001`. A Corte pode receber relatos de sua zona de influência sem possuir vigilância, jurisdição ou presença física total sobre ela.

## Matriz mínima obrigatória
A implementação/authoring deve suportar, no mínimo:

1. requisitos nunca satisfeitos;
2. requisitos satisfeitos sem o jogador descobrir a oportunidade;
3. rumor parcial sem investigação;
4. oferta formal recusada;
5. oferta aceita e ignorada;
6. investigação sem encontro com o alvo;
7. encontro antes da missão;
8. encontro e não intervenção;
9. diálogo/negociação;
10. hostilidade;
11. morte;
12. resolução por terceiros;
13. deslocamento/transformação da situação antes da chegada;
14. descoberta retrospectiva apenas das consequências.

## Regra de knowledge
Iren não conhece automaticamente a identidade, localização, capacidades ou motivações de Severin. O estado que permite a investigação precisa apontar para rumor, testemunha, evidência ou observação concreta. O mesmo vale para qualquer outro ator que entre no caso.

`FAC-0001` não herda automaticamente todo knowledge de `NPC-0003`, e `NPC-0003` não herda automaticamente todo registro da instituição.

## Âncoras de descoberta/evidência
- `EVD-0001` — relatos desencontrados: pode iniciar interesse ou investigação, mas não prova causa/culpa;
- `EVD-0002` — registro cartográfico da corte: pode revelar concentração espacial/temporal de ocorrências, com viés de coleta explícito;
- `EVD-0003` — vestígio de ocupação oculta: pode sustentar investigação de campo, mas não identifica ocupante nem provider.

Essas peças são **rotas possíveis**, não uma sequência obrigatória. O jogador pode chegar por subconjuntos diferentes, encontrar `EVD-0003` antes de qualquer oferta, reconstruir parte do padrão independentemente ou nunca descobrir nenhuma delas. Perda/destruição de uma peça não deve criar soft-lock se outra rota semanticamente válida ainda existir.

Nenhuma peça isolada autoriza `NPC-0003` ou outra instituição a declarar `NPC-0001` culpado, conhecido ou localizado sem uma cadeia causal adicional.

## Lifecycle editorial
O mapeamento de availability/discovery/engagement/resolution, progressão autônoma, reconciliação de entrada antecipada e discovery retrospectiva está em `QST-0001-lifecycle.md`.

## Diálogos relacionados
- `DLG-0002` — calibração de voz/evidência de `NPC-0003`; não é evento canônico;
- `DLG-0003` — rascunho de oferta de investigação, condicionado ao estado real de knowledge/lifecycle.

## Regra de journal
Não mostrar “missão perdida” se o jogador nunca soube que a oportunidade existia.

## Regra de mundo
O conteúdo não congela esperando o jogador. Progressão autônoma deve ser explicitamente declarada, causal e determinística para diagnóstico.

## Spoilers
Motivações verdadeiras, outcomes preferenciais, responsáveis, evidências e consequências concretas devem viver em arquivos de arco/evidência separados para preservar descoberta em gameplay.
