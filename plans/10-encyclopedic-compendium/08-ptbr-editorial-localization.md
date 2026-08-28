# 10.08 — PT-BR, padrão editorial e localização

## Objetivo

Fazer do português do Brasil o idioma canônico player-facing do Compêndio e eliminar texto hardcoded/inconsistente na interface, sem confundir IDs técnicos com conteúdo editorial.

## Estado atual relevante

O projeto já possui `src/main/resources/assets/rpgskilltree/lang/pt_br.json` com textos da árvore de habilidades. O Stage 10 deve ampliar esse contrato em vez de criar um sistema paralelo de strings.

## Arquivos previstos

Alterar/criar principalmente:

- `src/main/resources/assets/rpgskilltree/lang/pt_br.json`
- `src/main/resources/assets/rpgskilltree/lang/en_us.json` apenas como fallback técnico quando necessário para convenções do jogo/distribuição;
- dados em `src/main/resources/data/rpgskilltree/encyclopedia/` referenciando translation keys;
- `scripts/` ou validator Java já usado pelo projeto para detectar translation-key drift;
- testes/validators de cobertura de localização.

## Regra canônica

Todo texto que o jogador encontra no Compêndio deve ter versão PT-BR completa:

- título da tela;
- categorias e filtros;
- estados locked/discovered/studied;
- mensagens de descoberta;
- nomes editoriais de entries quando o nome do provider não estiver adequadamente traduzido;
- resumos;
- seções;
- labels de stats/loot/ecologia;
- erros/empty states;
- tooltips e controles.

Nenhuma entry pode ser considerada `CURATED` se suas chaves PT-BR obrigatórias faltarem.

## Chaves

Usar prefixos previsíveis, por exemplo:

```text
screen.rpgskilltree.compendium.*
encyclopedia.rpgskilltree.category.*
encyclopedia.rpgskilltree.entry.<namespace>.<path>.title
encyclopedia.rpgskilltree.entry.<namespace>.<path>.summary
encyclopedia.rpgskilltree.entry.<namespace>.<path>.section.<name>
```

O formato final deve ser suficientemente estável para scripts de cobertura e não depender de posição/índice num array.

## Nomes vindos de outros mods

Para targets externos:

1. usar o nome traduzido do próprio target se ele tiver tradução PT-BR adequada no resource stack;
2. se não houver ou for editorialmente inadequado, fornecer override próprio na entry;
3. nunca usar registry path (`komodo_dragon`) como nome normal da UI;
4. namespace/mod id pode aparecer em metadado “Origem”, não como substituto do nome.

## Estilo editorial

As descrições devem ser:

- informativas e funcionais;
- escritas em português brasileiro natural;
- consistentes em terminologia;
- específicas ao comportamento da versão suportada;
- sem propaganda, changelog ou opinião de curadoria;
- sem “este mod adiciona...” como estrutura repetitiva da descrição;
- sem lore inventada para preencher ausência de documentação.

Quando houver lore oficial do próprio conteúdo, separá-la de mecânica factual se isso reduzir ambiguidade.

## Vocabulário controlado

Criar convenção antes do corpus grande para termos recorrentes, por exemplo:

- `vida máxima`;
- `armadura`;
- `dano de ataque`;
- `velocidade de movimento`;
- `hostil`, `neutro`, `passivo`;
- `domesticável`, `montável`, `reproduzível` somente quando confirmado;
- `bioma`, `dimensão`, `estrutura`, `variante`, `drop`/`recompensa` com escolha terminológica consistente.

Não traduzir nomes próprios consagrados arbitrariamente quando o próprio pack/provider os mantém em inglês e não há tradução oficial desejada.

## Números e dados variáveis

- não cristalizar número que muda por config/difficulty sem indicar contexto;
- dados runtime devem ser formatados pela UI, não duplicados em prosa;
- unidades/percentuais seguem formatação consistente;
- valores desconhecidos ficam ausentes.

## Fallback `en_us`

O usuário quer o mod em PT-BR; ainda assim, `en_us.json` pode conter fallback mínimo para evitar translation keys cruas em ambientes que não carreguem PT-BR. Isso não reduz o gate: `pt_br` permanece obrigatório e editorialmente canônico.

## Validação automática

Criar gate que:

- extrai todas as translation keys referenciadas por entries/UI;
- falha se faltar key em `pt_br.json`;
- detecta key órfã opcionalmente em modo warning;
- detecta strings player-facing hardcoded em classes do Compêndio quando tecnicamente viável;
- reporta entry + key faltante;
- valida placeholders `%s`/argumentos compatíveis.

## Revisão editorial

Para cada lote de conteúdo:

1. validar fatos contra fonte/runtime;
2. revisar PT-BR;
3. revisar consistência terminológica;
4. verificar se texto não vaza informação gated;
5. marcar entry `CURATED` somente depois disso.

## Acceptance

- [ ] `pt_br` cobre 100% do texto player-facing do Compêndio.
- [ ] Não há registry paths aparecendo como nome normal por falta de tradução.
- [ ] O corpus segue vocabulário e template editorial consistentes.
- [ ] Validator falha em translation key obrigatória ausente.
- [ ] Texto factual e dados variáveis não entram em contradição por duplicação.
- [ ] `en_us` é tratado como fallback, não como idioma editorial principal.
