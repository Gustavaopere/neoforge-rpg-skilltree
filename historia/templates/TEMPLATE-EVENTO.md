# EVT-#### — Título

## Estado editorial
RASCUNHO

## Tipo
Milestone | transição | decisão | conflito | descoberta | política | crise | morte/retorno | outro

## Escopo
- ator/entidade principal afetada:
- settlement/facção/região, se aplicável:
- alcance narrativo:

## Cronologia
- logical time/ordem narrativa:
- eventos que precisam ocorrer ANTES:
- eventos que podem ocorrer DEPOIS:
- relação temporal ainda não decidida:

Usar relações cronológicas apenas quando houver causalidade/ordem realmente necessária. Não inventar timestamp absoluto por conveniência.

## Localização
- local narrativo `LOC-####`, se aplicável:
- assentamento `SET-####`, se aplicável:
- binding físico verificado, se necessário:

## Gatilhos/precondições
- gatilho causal:
- precondições:
- condições que impedem/cancelam:
- o que NÃO constitui causa suficiente:

## Atores
- ator(es) causal(is):
- alvos/afetados:
- participantes não causais:

Não tratar presença do jogador como causa automática.

## Causalidade
Descrever por que o evento ocorre e quais fatos/ações o sustentam. Separar causa, correlação e consequência.

## Observadores/testemunhas
- testemunhas diretas:
- observadores indiretos:
- atores que NÃO sabem automaticamente:

## Knowledge gerado
- fatos potencialmente aprendidos:
- por quem:
- por qual canal:
- incerteza/limites:

## Evidências relacionadas
Referenciar `EVD-####` quando o evento produzir, destruir, alterar ou depender de evidência.

## Facts/estado persistente
Fatos, estados ou registros que o Narrative Core poderá persistir/derivar. Texto editorial não muta save sozinho.

## Consequências imediatas

## Consequências atrasadas
- trigger causal futuro:
- condições de transformação/cancelamento:
- callbacks possíveis:

## Progressão autônoma
Declarar atores/causas capazes de produzir este evento sem participação do jogador. Não usar apenas “tempo passou” quando nenhuma causa estiver definida.

## Repetição/idempotência
- pode repetir:
- replay/dedup key conceitual:
- quando múltiplas ocorrências são eventos distintos:
- quando devem ser coalescidas:

Estados contínuos não devem gerar evento por tick; registrar transições/milestones narrativamente relevantes.

## Retenção histórica
Declarar se o evento é milestone que jamais deve ser apagado da história, ou registro repetitivo que pode ser resumido/coalescido pelo runtime.

## Providers
- provider/capability real necessária:
- fonte técnica:
- fallback quando provider for opcional/indisponível:

Lore não cria capability mecânica.

## Relações
- NPCs:
- facções:
- assentamentos:
- locais:
- quests/arcos:
- evidências:
- outros eventos:

## Invariantes

## QA
- [ ] há ator/causa quando o evento exige causalidade;
- [ ] actor, target e testemunha não foram confundidos;
- [ ] cronologia usa BEFORE/AFTER apenas quando necessário;
- [ ] knowledge possui canal/proveniência;
- [ ] evento contínuo não virou spam por tick;
- [ ] repetição/dedup/coalescing foram considerados;
- [ ] milestone histórico não será apagado por conveniência;
- [ ] provider capability foi comprovada ou ficou não vinculada;
- [ ] presença/ausência do jogador não foi usada como causa implícita.

## Spoilers internos
