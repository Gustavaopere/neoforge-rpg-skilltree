# 11.05 — Schema de Prefixos, Sufixos e Infixos

## Objetivo

Definir um catálogo data-driven de modificadores com semântica clara, compatibilidade por categoria e valores persistíveis.

## Semântica

- **Prefixos:** ofensivo, produtividade, dano, velocidade, perfuração, poder mágico/escola e eficiência ativa.
- **Sufixos:** defesa, vida, mana, regeneração, resistências, sustain, combustível/energia e utilidade defensiva.
- **Infixos:** habilidades condicionais, procs, respostas a eventos, sinergias e comportamentos especiais.

## Passo a passo

### A — Definição de modificador

Cada definição deve declarar:

```text
id
family
allowedCategories
forbiddenCategories
weight
exclusiveGroups
value/scaling definition
runtime effect kind
localization keys
diagnostic metadata
```

### B — Compatibilidade

- [ ] modifier só entra no pool quando é válido para as categorias do item;
- [ ] suporte a tags, aliases e adapters;
- [ ] fallback universal suficiente para garantir 1..5 em cada família;
- [ ] pool incapaz de satisfazer a contagem falha visivelmente no reload/diagnóstico, sem repetir modifier silenciosamente.

### C — Duplicação e exclusão

Por padrão, o mesmo modifier ID não se repete no mesmo item. `exclusiveGroups` impedem combinações contraditórias ou semanticamente duplicadas.

### D — Roll persistido

Persistir os parâmetros necessários para que atualizar datapack não altere retroativamente a potência de um item já gerado, salvo migração explícita.

### E — Sistemas externos

Affixes externos podem ser adaptados para uma família, mas não se tornam fonte de rank/contagem. Threads, gems, encantamentos e outros sistemas independentes não devem ser convertidos automaticamente.

## Testes previstos

- filtro por categoria;
- exclusive group;
- duplicação proibida;
- pool insuficiente detectado;
- roll round-trip;
- definição removida tratada sem apagar identidade.

## Acceptance

Existe um único schema carregável/validável para as três famílias e qualquer item elegível possui pools coerentes, diagnosticáveis e suficientes para a geração exigida.
