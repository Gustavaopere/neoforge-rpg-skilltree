# 02 — Arcos

Subpastas editoriais previstas:
- `principais/`
- `regionais/`
- `faccoes/`
- `pessoais/`

Cada arco deve usar `ARC-####`, declarar entradas alternativas, estados de descoberta, participantes, dependências de mundo e critérios de transformação/encerramento.

Um arco não precisa ser conhecido pelo jogador para existir.

## Relação com as 14 eras

A macroestrutura planejada de quatorze eras organiza a progressão possível do mundo, mas **não representa uma sequência rígida de quatorze capítulos** e não exige automaticamente um `ARC-####` por era.

As eras podem:
- sobrepor problemas e sistemas;
- ser alcançadas cedo ou tarde;
- ter conteúdo ignorado ou nunca descoberto;
- conter múltiplos arcos independentes;
- não possuir arco próprio quando a progressão ocorrer por eventos, quests, exploração ou mudanças sistêmicas;
- reaparecer por callbacks muito depois de sua primeira relevância.

Não criar arcos apenas para preencher a contagem 1–14.

## Gate para criar um arco central

Antes de alocar um novo `ARC-####` central:

1. confirmar que existe um problema/tensão narrativa real, não apenas “chegou a era do mod X”;
2. pesquisar `historia/`, branches/PRs e IDs relacionados;
3. consultar Grimoire/TTRPG.bot para Campaign Bible/lore estruturada quando acessível;
4. se a decisão depender de lore central que só o Grimoire pode resolver e ele estiver indisponível, manter a criação `BLOQUEADA/FAIL-CLOSED`;
5. verificar providers/capabilities reais quando o arco depender de mecânica concreta;
6. preferir problemas com múltiplas soluções sistêmicas quando o gameplay permitir;
7. usar `TEMPLATE-ARCO.md` e considerar SIM/NÃO/ANTES/DEPOIS, prior-event reconciliation, progressão autônoma, death fallback e failure-forward.

A ausência de um arco não é um erro de cobertura. É preferível manter uma era sem `ARC-####` do que inventar conflito, facção, NPC ou segredo apenas para preencher estrutura.
