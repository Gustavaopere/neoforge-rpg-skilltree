# Migração editorial de NPC-0001 — Severin → Aren — 2026-09-15

## Estado
DECISÃO EDITORIAL REGISTRADA / NÃO É EVENTO IN-UNIVERSE.

## Objetivo
Registrar a resolução do conflito de identidade entre a representação GitHub `NPC-0001 — Severin` e a entidade Aren da Campaign Bible, sem transformar a reconciliação editorial em fato ficcional não suportado.

## Evidência considerada
- `NPC-0001` já era o ID estável usado pelo cenário de referência do Narrative & Society Core;
- Severin surgiu no GitHub como personagem/caso de aceitação técnico-editorial e recebeu posteriormente dossiê, ficha de autoria, asset brief e diálogo de calibração;
- a reconciliação de 2026-09-14 registrada no GitHub não encontrou entidade `Severin` no Grimoire/TTRPG.bot;
- a mesma reconciliação encontrou Aren ativo, UUID `e9a83c1b-4bd9-49c8-8b79-ad41011383cd`, descrito como promoção canônica do antigo arco `P-N`, associado a Goety/necromancia e com função útil, controversa e politicamente difícil;
- os documentos visuais/de voz de Severin estavam em estado de rascunho/experimental e não constituíam assets finais nem prova de identidade de Aren.

## Decisão
1. `NPC-0001` continua sendo o ID estável.
2. A identidade editorial ativa de `NPC-0001` passa a ser **Aren**.
3. `Severin` deixa de ser entidade canônica ativa no GitHub e passa a identificar somente material editorial legado do cenário técnico anterior.
4. A migração é tratada como **retcon/substituição editorial**, não como alias, pseudônimo, nome antigo, duplicata ou evento ocorrido no mundo.
5. Nenhuma voz, aparência, idade, roupa, motivação, segredo, relação ou decisão anteriormente escrita para Severin é transferida automaticamente para Aren.
6. Contratos sistêmicos independentes da identidade — knowledge, relações multidimensionais, autonomia, Event Ledger, morte/retorno e continuidade de identidade — continuam aplicáveis a `NPC-0001` como a qualquer NPC.
7. Referências históricas de `QST-0001` a `NPC-0001` são preservadas somente para rastreabilidade. Elas não confirmam Aren como participante/alvo/responsável da quest.
8. `DLG-0001`, a ficha de autoria e o asset brief de Severin permanecem como material legado, explicitamente não autorizado como voz/aparência de Aren.

## Não decidido por esta migração
- biografia completa de Aren;
- aparência, voz ou asset final de Aren;
- localização, cargo, facção, relações específicas ou agenda de Aren;
- vínculo ficcional de Aren com `QST-0001`;
- spells, rituais, itens ou capabilities concretas de Goety;
- qualquer explicação in-universe para o nome Severin.

## Efeito sobre arquivos
- entidade ativa: `historia/03-npcs/principais/NPC-0001-aren.md`;
- antigo dossiê de Severin: preservado como registro legado e sem redeclarar `NPC-0001` como entidade;
- autoria/asset/dialogue de Severin: preservados como material legado, não promovível para Aren;
- `QST-0001`: deve distinguir participante confirmado de referência estrutural legada;
- `STATUS.md` e backlog: conflito de identidade deixa de ser pendência; possíveis vínculos narrativos/visuais de Aren permanecem sujeitos a provenance própria.

## Regra de interpretação futura
Encontrar o texto “Severin” em histórico Git, PRs, arquivos legados ou cenários técnicos não constitui evidência de personagem adicional e não autoriza criar uma segunda entidade. Encontrar `NPC-0001` em conteúdo escrito antes desta migração também não prova que Aren participou daquele conteúdo: a data/proveniência da referência deve ser considerada.

## Gate de conclusão
A migração editorial está concluída quando:
- existir apenas uma declaração ativa de entidade `NPC-0001`, nomeada Aren;
- materiais de Severin estiverem marcados como legado;
- `QST-0001` não afirmar Aren como participante por herança automática;
- backlog/status não mantiverem o conflito como aberto;
- validators/inventory da campanha passarem sem referências órfãs ou duplicidade de declaração.
