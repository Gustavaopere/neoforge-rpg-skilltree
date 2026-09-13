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

### Testes

```bash
python -m unittest discover -s historia/tools/tests -v
```

A ferramenta usa apenas a biblioteca padrão do Python e não requer API, conta, assinatura ou acesso de rede.

## Limites

O validador não decide se uma relação narrativa é correta. Ele só verifica integridade estrutural de IDs/referências. Causalidade, chronology, knowledge, providers, spoilers e agência continuam exigindo revisão editorial.
