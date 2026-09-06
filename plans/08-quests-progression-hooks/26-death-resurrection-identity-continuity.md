# 08.26 — Death, Resurrection & Identity Continuity

## Goal
Tratar morte e retorno como estados narrativos de primeira classe. Diferentes providers podem reconstruir corpo, reanimar, restaurar consciência, preservar memória ou afetar identidade de formas distintas; o Narrative Core não deve assumir que qualquer “ressurreição” devolve automaticamente a mesma pessoa intacta.

## Regra de authority
Cada mod/provider continua autoridade sobre sua mecânica real. O Narrative Core apenas registra fatos observáveis e consequências narrativas.

Em especial, Mobstein deve ser tratado como provider de ressurreição/reconstrução corporal e experimentação conforme os hooks reais da versão instalada. Isso NÃO cria equivalência automática com Goety, Malum, Eidolon, Black Arcana, Enshrouded ou qualquer conceito de alma/espírito de outro provider.

## Identity Continuity Record
Um ator narrativo que morreu e retornou deve poder registrar separadamente:
- `body_continuity` — continuidade/reconstrução do corpo físico;
- `memory_continuity` — memória preservada, parcial, ausente ou alterada;
- `personality_continuity` — traços/comportamento reconhecíveis ou modificados;
- `knowledge_continuity` — conhecimento anterior preservado ou perdido;
- `relationship_continuity` — como relações anteriores são recuperadas/reinterpretadas;
- `legal_identity` — como assentamento/facção reconhece juridicamente o retornado;
- `social_identity` — como população, família, clero, academia e facções o reconhecem;
- `self_identity` — como o próprio ator se reconhece, quando o conteúdo o suporta;
- `provider_origin` — método/provider que causou o retorno;
- `return_event_id` — evento canônico da volta;
- evidence/knowledge sobre o que realmente aconteceu.

Esses campos não afirmam metafísica universal. “Alma original presente” só pode virar fact se um provider/bridge real fornecer evidência canônica suficiente.

## Estados exemplares
Sem impor implementação exata aos providers, o Narrative Core precisa suportar resultados como:
- corpo restaurado e identidade amplamente contínua;
- corpo reconstruído com memória parcial;
- corpo reconstruído sem memória anterior;
- memórias presentes, mas personalidade alterada;
- consciência funcional cuja continuidade pessoal é contestada;
- reanimado útil como aliado, mas não reconhecido socialmente como a pessoa morta;
- retorno que a família aceita e o clero rejeita;
- retorno que o jogador aceita e o próprio retornado considera uma nova identidade.

## Mobstein exemplar
Se um NPC importante morrer e for trazido de volta via Mobstein, não usar automaticamente `npc.dead = false` e restaurar tudo.

Fluxo mínimo:
1. morte gera `death_event_id` e snapshot narrativo necessário;
2. provider Mobstein executa seu processo real;
3. adapter valida a entidade/resultado real e registra `return_event_id`;
4. Narrative Core cria/atualiza o Identity Continuity Record;
5. memória, relações e knowledge são reconciliados pelas regras do conteúdo/provider, nunca por cópia cega;
6. atores que testemunham o retorno recebem knowledge apropriado;
7. família, clero, academia, autoridades e facções podem reagir diferentemente;
8. quests dependentes do NPC usam o estado de continuidade, não apenas `alive=true`.

## Consequências sistêmicas possíveis
- parentes podem demonstrar afeto, medo ou rejeição;
- parceiro/companheiro pode reconhecer ou questionar a pessoa;
- herança/cargo político pode permanecer transferido, criando disputa jurídica;
- clero pode considerar o ato proibido mesmo quando o retornado ajuda a cidade;
- academia pode exigir estudo/isolamento;
- necromantes podem interpretar o resultado de forma distinta de pesquisadores de Mobstein;
- memória parcial pode fechar respostas de diálogo que o NPC morto conhecia;
- novas experiências após retorno podem criar personalidade/relationship trajectory diferente;
- morte anterior continua existindo no Event Ledger; ressurreição nunca apaga o fato histórico.

## Quest design
Conteúdo que depende de um NPC deve poder declarar condições como:
- `NPC_ALIVE`;
- `NPC_DEAD`;
- `NPC_RETURNED`;
- `BODY_CONTINUITY >= threshold`;
- `MEMORY_CONTINUITY == PARTIAL`;
- `LEGAL_IDENTITY == DISPUTED`;
- `KNOWS(actor, return_event)`;
- `RETURNED_BY_PROVIDER(mobstein)`;
- `DEATH BEFORE RETURN`;
- `RETURN BEFORE QUEST_DISCOVERY`.

## Relações e memória
Relações não devem simplesmente voltar aos valores pré-morte. O conteúdo pode escolher políticas como:
- preservar dimensões selecionadas;
- reduzir trust/affection por estranhamento;
- manter grievance/debt históricos;
- criar grievance/fear específico do retorno;
- exigir reconhecimento/reconstrução de vínculo;
- preservar memórias somente quando `memory_continuity` permitir.

## Recompensas e exploits
Ressuscitar não pode duplicar recompensa, favor, loot, morte contabilizada, milestone, boss defeat ou quest completion. `death_event_id`, `return_event_id` e reward/idempotency ledger devem impedir farming narrativo.

## Multiplayer
Atores podem discordar sobre a identidade do retornado. Knowledge/evidence continua por ator; não existe verdade social global automática só porque o servidor sabe o Identity Continuity Record.

## Acceptance exemplar — “ele voltou, mas diferente”
1. NPC morre e a campanha registra a morte.
2. Jogador usa Mobstein para trazê-lo de volta.
3. O NPC volta funcional, porém com continuidade definida pelo conteúdo/provider em vez de restauração total automática.
4. Uma quest que precisava apenas do corpo vivo pode reabrir/transformar.
5. Uma quest que dependia de memória específica pode continuar indisponível se essa memória não retornou.
6. Família pode reconhecê-lo, enquanto o clero o considera uma violação da lei.
7. O retornado pode lembrar do jogador mas ter `respect`, `fear` ou alinhamento alterados.
8. Outro jogador que não presenciou o retorno não recebe conhecimento automático.
9. Matar e ressuscitar repetidamente não duplica progressão/recompensas.
10. O histórico preserva `death -> return` e permite ANTES/DEPOIS em conteúdo futuro.
