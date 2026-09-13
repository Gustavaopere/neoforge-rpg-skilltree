# Relações, Memória e Continuidade de Identidade

## Estado editorial
RASCUNHO

## Escopo
Contrato editorial geral para relações multidimensionais, memória social e continuidade após morte/retorno. A aceitação deste documento não fixa relações concretas de NPCs nem inventa estados de continuidade; cada caso exige fatos/evidência próprios.

## Autoridade técnica
Este contrato editorial espelha, sem substituir:

- `plans/08-quests-progression-hooks/11-npc-memory-relationships-companions.md`;
- `plans/08-quests-progression-hooks/26-death-resurrection-identity-continuity.md`.

Ranges numéricos, clamps, persistence, schema, networking e mutações de runtime pertencem ao Narrative Core. Este arquivo não inventa valores nem formatos técnicos adicionais.

## Regra principal
Relação não é uma única barra de reputação.

Para atores narrativos persistentes, as dimensões mínimas são:

- `affection` — vínculo afetivo, simpatia ou apego;
- `trust` — expectativa de honestidade, confiabilidade e previsibilidade;
- `respect` — reconhecimento de competência, coragem, autoridade ou princípios;
- `fear` — percepção de ameaça, risco ou capacidade de causar dano;
- `dependency` — grau de dependência prática, social, econômica, emocional ou estratégica;
- `ideological_alignment` — proximidade ou conflito entre valores, objetivos ou visão de mundo.

Uma ação pode aumentar uma dimensão e reduzir outra. Nenhuma dimensão é sinônimo universal de amizade, lealdade, moralidade ou aprovação.

## Direcionalidade
Relações são `ator origem -> ator alvo`.

`A -> B` não implica `B -> A`.

Um NPC pode respeitar o jogador e não confiar nele; o jogador pode depender de uma instituição que o teme; duas pessoas podem manter afeto e conflito ideológico ao mesmo tempo.

## Proveniência de mudanças
Mudanças relevantes de relação precisam de causa rastreável.

Preferir referência a:

- `EVT-####` quando existir evento canônico correspondente;
- escolha/quest/ação com identificador persistente quando o runtime fornecer esse fato;
- grievance/debt/favor ledger quando a consequência for obrigação, ofensa, favor ou dívida persistente.

Não alterar relação apenas porque uma cena “pede” determinada emoção.

## Grievance, debt e favor
Grievance, dívida e favor não devem ser comprimidos dentro das seis dimensões.

Exemplos:

- um NPC pode manter `trust` e ainda possuir grievance legítimo;
- dívida não significa affection;
- medo não implica respeito;
- alinhamento ideológico não elimina conflito pessoal;
- um favor pode existir mesmo entre rivais.

Quando o runtime possuir ledger próprio para esses fatos, o conteúdo deve referenciá-lo em vez de duplicar a informação em prosa como segunda autoridade.

## Memória
Memórias importantes devem apontar para fatos/eventos identificáveis, não funcionar como cópias livres e ilimitadas de texto.

Separar:

- o evento realmente ocorrido;
- quem presenciou ou recebeu informação;
- o que o ator lembra;
- o que o ator acredita ter acontecido;
- o que o ator sabe com evidência suficiente;
- como essa memória influencia relação, decisão ou diálogo.

Memória e knowledge não são equivalentes. Um ator pode lembrar de um relato falso; pode conhecer um fato por documento sem tê-lo presenciado; pode esquecer parte de um evento sem que o evento deixe de ter ocorrido.

## Instituições e grupos
Relação de um membro não se propaga automaticamente para facção, assentamento ou instituição inteira.

Da mesma forma:

- relação com `FAC-####` não concede automaticamente a mesma relação com todos os seus membros;
- opinião pública não é média automática das relações individuais;
- cargo institucional não fornece knowledge global;
- mudança de governante não apaga dívidas, grievances ou memória institucional sem causa explícita.

## Diálogos
Diálogos podem consultar relações como contexto de apresentação, desde que o runtime exponha essas variáveis de forma autorizada.

Relação pode alterar:

- tom;
- disposição para compartilhar informação;
- preço, favor ou ajuda quando a mecânica real suportar;
- disponibilidade de certas respostas;
- disposição para acompanhar, proteger, confrontar ou evitar outro ator.

Relação não pode mudar a verdade dos fatos nem conceder conhecimento inexistente.

## Morte, retorno e continuidade
Morte permanece fato histórico.

Se um ator retorna, não restaurar automaticamente o snapshot pré-morte de relação, memória ou knowledge. Aplicar o `Identity Continuity Record` do Stage 08.

Quando o caso exigir continuidade detalhada, manter independentes:

- `body_continuity`;
- `memory_continuity`;
- `personality_continuity`;
- `knowledge_continuity`;
- `relationship_continuity`;
- `legal_identity`;
- `social_identity`;
- `self_identity`;
- `provider_origin`;
- `return_event_id`;
- evidence/knowledge sobre o retorno.

Esses campos não provam metafísica universal. Continuidade de corpo, memória, reconhecimento jurídico, aceitação social e autorreconhecimento podem divergir legitimamente.

O conteúdo pode, conforme evidência e regra de continuidade:

- preservar algumas dimensões relacionais;
- reduzir ou alterar outras;
- manter grievances/debts anteriores;
- criar fear/grievance relacionado ao retorno;
- exigir reconstrução gradual de vínculo;
- manter memória parcial ou ausente;
- produzir `legal_identity` diferente de `social_identity`;
- permitir `self_identity` diferente da identidade atribuída por família, governo, clero, academia ou facções.

`alive=true` não significa “a mesma relação de antes”. Família reconhecer alguém não obriga o Estado/instituição a restaurar seu status jurídico, e reconhecimento jurídico não obriga aceitação social nem autorreconhecimento.

## Companions
Loyalty de companion não substitui as dimensões de relação.

Se existir um valor específico de loyalty no runtime/provider, ele deve ter contrato próprio e proveniência clara. Não presumir que Easy NPC ou outro provider oferece memória social ou loyalty apenas por representar o NPC físico.

## Autoria editorial
Arquivos de NPC podem registrar relações iniciais ou esperadas apenas quando há evidência canônica suficiente.

Quando a relação ainda não existe, registrar como desconhecida/não estabelecida em vez de preencher valores para completar template.

Para mudanças propostas, descrever preferencialmente:

- ator origem;
- ator alvo;
- fato/evento causador;
- dimensões potencialmente afetadas;
- direction da mudança quando editorialmente decidida;
- grievance/debt/favor relacionado, se houver;
- memory/knowledge necessários;
- condições que impedem ou transformam a reação.

Não congelar números editoriais sem contrato técnico que os defina.

## QA
Antes de aceitar uma mudança relacional importante, verificar:

- [ ] origem e alvo estão claros;
- [ ] a reação tem causa/proveniência;
- [ ] dimensões não foram colapsadas em “gostou/não gostou”;
- [ ] knowledge/memória necessária realmente existe;
- [ ] grievance/debt/favor não foi duplicado como simples score;
- [ ] facção/instituição não herdou relação individual automaticamente;
- [ ] morte/retorno respeita continuidade de identidade;
- [ ] legal/social/self identity permanecem independentes quando aplicável;
- [ ] nenhuma metafísica universal foi inferida de simples retorno corporal;
- [ ] nenhuma mecânica de provider foi inventada para justificar a consequência.
