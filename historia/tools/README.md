# Ferramentas locais de autoria

Ferramentas gratuitas, locais e sem dependências externas para apoiar a manutenção de `historia/`.

## `validate_story.py`

Valida IDs estáveis e referências entre arquivos Markdown da história.

### O que verifica

- IDs declarados nos títulos principais (`HIST-####`, `ARC-####`, `NPC-####`, `QST-####`, `FAC-####`, `SET-####`, `LOC-####`, `EVT-####`, `EVD-####`, `END-####`, `DLG-####`);
- duplicidade de IDs declarados;
- referências a IDs ainda não declarados;
- ignora placeholders de templates como `NPC-####`.

### Uso

```bash
python historia/tools/validate_story.py
python historia/tools/validate_story.py --strict-references
```

Por padrão, IDs duplicados retornam erro. Referências ainda não resolvidas são aviso; `--strict-references` transforma essas referências em erro.

## `validate_dialogues.py`

Valida a estrutura editorial dos arquivos `DLG-####` dentro de `historia/12-dialogos/`.

### O que verifica

- título `DLG-####` válido;
- presença e conteúdo das categorias essenciais: estado editorial, participantes, contexto, precondições, knowledge, relações/estado, abertura/entrada, ramos, saídas, intents, referências, invariantes, notas de voz, QA e spoilers internos;
- aceita variações semânticas de heading como `Precondições editoriais`, `Knowledge exigido de ...` e `Entrada padrão`;
- detecta IDs placeholder como `NPC-####` em diálogo real;
- exige ao menos um checkbox na seção de QA;
- ignora arquivos que não sejam `DLG-####*.md`.

### Uso

```bash
python historia/tools/validate_dialogues.py
```

O lint é estrutural. Ele não avalia qualidade literária e não obriga todos os diálogos a usarem exatamente os mesmos títulos de seção.

## `story_inventory.py`

Gera um inventário de leitura dos registros versionados da campanha.

### O que extrai

- ID estável;
- tipo (`NPC`, `QST`, `FAC`, `LOC`, etc.);
- título;
- primeiro valor declarado em `## Estado editorial`;
- caminho do arquivo;
- referências a outros IDs, deduplicadas;
- resumo por tipo e estado editorial.

### Uso

Relatório Markdown no terminal:

```bash
python historia/tools/story_inventory.py
```

Saída JSON para consumo por ferramentas/agentes:

```bash
python historia/tools/story_inventory.py --format json
```

O inventário é read-only: não altera lore, IDs nem estado editorial.

## Testes

```bash
python -m unittest discover -s historia/tools/tests -v
```

Os três módulos possuem 21 testes unitários no total no estado atual: 7 para IDs/referências, 7 para diálogos e 7 para inventário. As ferramentas usam somente a biblioteca padrão do Python e não requerem API, conta, assinatura ou acesso de rede.

## Limites

As ferramentas não decidem se uma relação narrativa, fala ou consequência é correta. Elas verificam integridade/estrutura ou apresentam o inventário. Causalidade, cronologia, knowledge, providers, spoilers, voz e agência continuam exigindo revisão editorial.
