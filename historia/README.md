# História — Fonte Editorial da Campanha

Esta pasta é a fonte editorial versionada da campanha narrativa do RPG Skill Tree.

Ela responde **o que existe e pode acontecer no mundo**. Os contratos técnicos que explicam **como o engine executa, persiste, valida e deduplica** essas histórias continuam em `plans/08-quests-progression-hooks/`.

## Regra principal

O jogador não deve precisar ler esta pasta para jogar. O conteúdo pode conter spoilers, identidades ocultas, agendas, consequências, alternativas e fatos que o personagem talvez nunca descubra.

Quando a história for produzida em chat, o padrão é **MODO SEM SPOILERS**: o agente pode editar estes arquivos e informar apenas escopo, quantidade, estado editorial e validações. Não resumir segredos, culpados, soluções, reviravoltas, finais ou rotas não descobertas, salvo pedido explícito do usuário.

## Estrutura

- `00-canone/` — regras, cronologia e fatos estabilizados.
- `01-historia-do-mundo/` — macro-história e passado.
- `02-arcos/` — arcos principais, regionais, de facção e pessoais.
- `03-npcs/` — um dossiê por NPC narrativamente relevante.
- `04-quests/` — uma especificação por quest/oportunidade narrativa.
- `05-faccoes/` — facções, instituições, ideologias e agendas.
- `06-assentamentos/` — cidades/colônias e seus estados políticos/sociais.
- `07-locais/` — lugares narrativos e seus segredos/estados.
- `08-eventos/` — eventos históricos, emergentes e recorrentes.
- `09-rumores-documentos-e-evidencias/` — canais de descoberta e conhecimento.
- `10-finais-e-epilogos/` — resoluções e combinações de epílogo.
- `11-ia-e-autoria/` — protocolo de autoria assistida, auditor narrativo e IA in-game opcional.
- `templates/` — formatos canônicos para novos registros.

## IDs estáveis

- História/macroevento: `HIST-####`
- Arco: `ARC-####`
- NPC: `NPC-####`
- Quest/oportunidade: `QST-####`
- Facção: `FAC-####`
- Assentamento: `SET-####`
- Local: `LOC-####`
- Evento: `EVT-####`
- Rumor/evidência/documento: `EVD-####`
- Final/epílogo: `END-####`

IDs não devem ser reciclados depois que entrarem em `main`.

## Auditoria automática

O corpus é protegido pelo `Narrative Auditor` em `scripts/narrative_auditor.py`.

O auditor bloqueia erros estruturais objetivos — como IDs duplicados, referências explícitas quebradas e divergência entre nome de arquivo e ID canônico — e mantém questões editoriais subjetivas como alertas não bloqueantes.

A saída padrão é **spoiler-safe**: não revela nomes, IDs narrativos, caminhos ou conteúdo. Detalhes só aparecem com `--reveal`, destinado a edição/depuração.

Contrato completo: `11-ia-e-autoria/04-narrative-auditor.md`.

## Princípios obrigatórios

1. O mundo não espera o jogador.
2. Conteúdo pode nunca ser desbloqueado, descoberto, oferecido ou concluído.
3. Recusar, ignorar, chegar cedo, chegar tarde, falhar, matar, salvar ou nunca conhecer alguém são estados legítimos.
4. NPCs e facções não são oniscientes; conhecimento exige origem, testemunha ou evidência.
5. Morte permanece fato histórico mesmo após retorno.
6. Ressurreição não implica automaticamente continuidade total de corpo, memória, personalidade, relações ou identidade social.
7. Mods/providers preservam suas autoridades mecânicas; integração temática não funde sistemas.
8. A história deve oferecer problemas com múltiplas soluções sistêmicas, não quests que apenas mandam usar um mod específico.
9. Conteúdo emergente não pode contradizer fatos canônicos nem inventar capacidades inexistentes dos providers.
10. Tudo player-facing deve ser PT-BR.

## Relação com o Stage 08

Referências técnicas principais:

- `plans/08-quests-progression-hooks/07-narrative-society-master-plan.md`
- `plans/08-quests-progression-hooks/09-event-ledger-chronology.md`
- `plans/08-quests-progression-hooks/10-knowledge-secrets-witnesses-evidence.md`
- `plans/08-quests-progression-hooks/11-npc-memory-relationships-companions.md`
- `plans/08-quests-progression-hooks/25-opportunity-discovery-lifecycle.md`
- `plans/08-quests-progression-hooks/26-death-resurrection-identity-continuity.md`

Esta pasta nunca substitui esses contratos de runtime.