# 11.13 — Localização pt-BR, tooltip e apresentação

## Objetivo

Garantir que toda informação própria da itemização seja apresentada em português do Brasil, sem persistir texto traduzido e sem depender da qualidade de localização de mods externos.

## Passo a passo

### A — Chaves próprias

Todo Rank, família, modifier, efeito, erro, mensagem e label do Stage 11 deve possuir chave estável, por exemplo:

```text
rank.rpgskilltree.common
modifier.rpgskilltree.mana_regeneration
itemization.rpgskilltree.family.prefix
itemization.rpgskilltree.locked_roll
```

IDs técnicos permanecem em inglês/namespace quando necessário; somente apresentação é localizada.

### B — Aliases de integrações

Quando o RPG exibir atributos externos, usar aliases próprios para nomes conhecidos e auditados:

- Regeneração de Mana;
- Mana Máxima;
- Poder Mágico;
- Velocidade de Ataque;
- Redução de Recarga;
- Perfuração de Armadura;
- Roubo de Vida;
- demais termos confirmados durante cada adapter.

Não renomear internamente IDs externos nem inventar tradução para conceito desconhecido sem confirmar sua semântica.

### C — Formato de tooltip

Estrutura base planejada:

```text
[RANK] Nome do Item
Poder do Item: N

Prefixos — X/5
• Nome
  efeito localizado

Sufixos — Y/5
• Nome
  efeito localizado

Infixos — Z/5
• Nome
  descrição localizada

Identidade RPG: Permanente
Os modificadores deste item não podem ser rerrolados.
```

### D — Nome do item

Não concatenar 15 modifiers no nome. Rank pode aparecer como badge/cor/prefixo visual, mantendo o nome base localizado pelo próprio item/mod.

### E — Formatação brasileira

- [ ] percentuais e números apresentados com formatação adequada ao locale quando possível;
- [ ] duração/unidades consistentes;
- [ ] valores positivos/negativos semanticamente claros;
- [ ] não exibir IDs/keys no modo normal.

### F — Tooltip avançado

Com tecla/modo avançado, permitir diagnóstico opcional:

- item ID;
- instance ID abreviado;
- Poder original;
- source;
- schema version;
- provider do modifier;
- IDs técnicos quando necessário.

Informação debug não substitui a apresentação pt-BR normal.

### G — Validator de localização

CI deve falhar se:

- uma chave própria usada pelo Stage 11 não existir em `pt_br`;
- tooltip próprio renderizar chave crua;
- corpus/definitions declararem localization key inexistente;
- strings próprias conhecidas forem hardcoded fora do mecanismo aprovado.

Termos ingleses vindos de UI externa que o RPG não controla devem ser distinguidos no relatório; não mascarar como falha própria sem capacidade de corrigir.

### H — Acessibilidade e legibilidade

- [ ] não depender apenas de cor para Rank;
- [ ] linhas longas quebram corretamente;
- [ ] tooltip não excede tela sem estratégia de paginação/compactação;
- [ ] Shift/Alt para detalhes avançados quando necessário;
- [ ] ícones, se usados, têm texto equivalente.

## Testes previstos

- snapshot de tooltip em pt-BR para todos os Ranks/famílias;
- modifier externo com alias próprio;
- modifier desconhecido com fallback legível;
- nenhuma chave crua;
- locale/decimal/percentual;
- tooltip com 5/5/5 continua navegável.

## Acceptance

O jogador usando `pt_br` consegue compreender Rank, Poder, Prefixos, Sufixos, Infixos, efeitos e recusas sem encontrar inglês ou chaves técnicas produzidas pelo Stage 11, e o CI impede regressão de cobertura.
