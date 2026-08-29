# 11.06 — Pipeline determinístico de geração

## Objetivo

Construir uma única operação de geração usada por qualquer origem de equipamento, sem reroll e sem pipelines paralelos por mod.

## Passo a passo

### A — Contexto de geração

Definir `ItemizationContext` contendo somente fatos necessários:

- source;
- jogador/entidade relevante quando permitido;
- nível de área/entidade resolvido por serviços canônicos;
- registry/item ID;
- dimensão/estrutura apenas quando necessária à policy;
- seed/nonce autoritativa.

### B — Sequência canônica

```text
classificar item
-> verificar elegibilidade
-> verificar identidade existente
-> resolver ItemPower
-> rolar Rank
-> rolar contagem Prefixos 1..5
-> rolar contagem Sufixos 1..5
-> rolar contagem Infixos 1..5
-> selecionar modifiers válidos sem repetição/conflito
-> rolar parâmetros
-> persistir identidade atomicamente
-> publicar evento/snapshot pós-persistência
```

### C — Random determinístico

Derivar streams/salts separados para rank, cada contagem, seleção e valores. Alterar uma etapa não deve deslocar acidentalmente todos os rolls seguintes.

### D — Atomicidade

Construir e validar o estado completo antes de persistir. Falha de pool/codec/policy não pode deixar identidade parcial.

### E — Reload

Reload recompila pools/curvas para futuras gerações. Identidades existentes não rerrolam. Definição removida exige fallback/migração diagnosticada, nunca substituição aleatória silenciosa.

### F — API pública

Expor queries como `isItemized`/snapshot imutável e mutation autoritativa equivalente a `ensureItemized`. Não expor API survival de `reroll`.

## Testes previstos

- mesma seed/contexto -> mesmo resultado;
- streams independentes;
- geração exatamente uma vez;
- falha antes do commit não deixa estado parcial;
- reload não muda item existente;
- reentrância não duplica modifiers.

## Acceptance

Toda origem usa o mesmo gerador determinístico, atômico e idempotente, e nenhum subsistema consegue gerar novamente uma identidade persistida.
