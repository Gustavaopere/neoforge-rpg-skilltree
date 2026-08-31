# Narrative Auditor — contrato editorial e CI

O `Narrative Auditor` é a camada determinística de integridade da fonte editorial `historia/`.

Ele não decide se uma história é boa e não substitui revisão narrativa. Seu papel é impedir que o corpus cresça com erros estruturais objetivos e sinalizar pontos que merecem revisão humana/assistida.

## Execução

Modo padrão, seguro para logs e relatórios compartilhados com o jogador:

```bash
python3 scripts/narrative_auditor.py --root historia
```

Modo editorial detalhado, que pode revelar IDs, caminhos e contexto de spoilers:

```bash
python3 scripts/narrative_auditor.py --root historia --reveal
```

Modo estrito opcional, útil apenas em auditorias editoriais específicas:

```bash
python3 scripts/narrative_auditor.py --root historia --reveal --strict-warnings
```

O CI normal NÃO usa `--reveal` nem `--strict-warnings`.

## Erros bloqueantes

Estes estados quebram a integridade objetiva da fonte e retornam código de saída não zero:

- `DUPLICATE_ID` — o mesmo ID estável foi declarado mais de uma vez;
- `UNRESOLVED_REFERENCE` — um registro referencia explicitamente um ID estável que não existe no corpus;
- `FILENAME_ID_MISMATCH` — o ID declarado no nome do arquivo diverge do ID do título H1;
- `MISSING_HEADING_ID` — um arquivo com nome que declara ID estável não possui esse contrato no H1.

## Alertas editoriais não bloqueantes

Alertas não derrubam CI porque podem representar trabalho legítimo em andamento ou decisões editoriais conscientes:

- `MISSING_RECOMMENDED_SECTION` — seção recomendada ausente;
- `PLACEHOLDER_MARKER` — marcador editorial pendente;
- `ORPHAN_ENTRY` — registro sem referência de entrada por outro registro estável;
- `NPC_MOTIVATION_GAP` — NPC sem seção explícita de objetivos;
- `QUEST_SOFT_LOCK_REVIEW` — quest sem seção explícita de anti-soft-lock;
- `QUEST_IDEMPOTENCY_REVIEW` — quest sem seção explícita de deduplicação/idempotência;
- `QUEST_ENTRY_POINT_REVIEW` — quest sem seção explícita de entradas alternativas.

Esses alertas formam backlog de revisão. Eles só se tornam bloqueantes se uma regra futura conseguir provar objetivamente que o estado é inválido.

## IDs reconhecidos

O auditor reconhece os namespaces editoriais:

- `HIST-####`
- `ARC-####`
- `NPC-####`
- `QST-####`
- `FAC-####`
- `SET-####`
- `LOC-####`
- `EVT-####`
- `EVD-####`
- `RUM-####`
- `DOC-####`
- `END-####`

Arquivos em `historia/templates/` não entram no corpus canônico auditado.

## Política sem spoilers

Por padrão o CLI revela somente:

- quantidade de registros auditados;
- quantidade de erros e alertas;
- códigos das regras e respectivas contagens.

Ele não imprime títulos, nomes de personagens, IDs narrativos, arquivos, linhas, relações ou conteúdo das descobertas.

`--reveal` existe exclusivamente para edição e depuração. Relatórios destinados ao jogador devem continuar no modo padrão.

## Camada subjetiva futura

Crítica de clichês, motivação, voz, pacing, agência, redundância dramática e qualidade de escolhas pode usar IA/skills de autoria, mas permanece uma camada editorial separada.

Nenhum parecer subjetivo de LLM deve bloquear CI por si só. Antes de promover uma regra a gate, ela precisa ser convertida em contrato verificável, determinístico e coberto por testes.

## Testes

O contrato do auditor é coberto por `scripts/test_narrative_auditor.py` e executado pelo workflow `.github/workflows/narrative-auditor.yml`.

O workflow roda quando `historia/**`, o auditor, seus testes ou o próprio workflow mudam. A sequência obrigatória é:

1. testar o auditor;
2. auditar o corpus canônico;
3. falhar somente em erros estruturais bloqueantes.
