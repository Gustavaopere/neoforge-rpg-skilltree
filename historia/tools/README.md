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

Na raiz do repositório:

```bash
python historia/tools/validate_story.py
```

Por padrão, IDs duplicados retornam erro. Referências ainda não resolvidas são exibidas como aviso porque o projeto pode mencionar conteúdo planejado antes do arquivo correspondente existir.

Para tratar referências não resolvidas como erro:

```bash
python historia/tools/validate_story.py --strict-references
```

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

## Testes

```bash
python -m unittest discover -s historia/tools/tests -v
```

As ferramentas usam apenas a biblioteca padrão do Python e não requerem API, conta, assinatura ou acesso de rede.

## Limites

Os validadores não decidem se uma relação narrativa, fala ou consequência é correta. Eles verificam integridade estrutural. Causalidade, cronologia, knowledge, providers, spoilers, voz e agência continuam exigindo revisão editorial.
