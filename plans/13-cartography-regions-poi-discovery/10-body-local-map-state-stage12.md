# 13.10 — Estado cartográfico por corpo e integração com Stage 12

## Objetivo

Garantir que múltiplos corpos do mesmo jogador compartilhem o mesmo mundo físico sem compartilhar automaticamente conhecimento, spoilers ou progresso de exploração.

## Regra canônica

Por padrão:

```text
RegionRecord/PoiRecord físico = WORLD_GLOBAL
MapIntelState                = BODY_LOCAL
```

Exemplo:

```text
Corpo A
- conhece Floresta de Valen
- visitou Vila de Oakheart
- sabe posição exata da Torre do Mago

Corpo B recém-criado
- mundo físico é o mesmo
- não recebe automaticamente os três conhecimentos acima
- pode redescobrir a região e os locais por sua própria jornada
```

Isso é coerente com o Stage 12: Corpo B funciona como nova jornada RPG dentro do mesmo save.

## Configuração opcional

Permitir política explícita:

- `BODY_LOCAL` — padrão;
- `ACCOUNT_GLOBAL` — conhecimento compartilhado entre todos os corpos do owner;
- eventualmente perfis híbridos por tipo de intel, se houver caso de design aprovado.

Não migrar de um modo para outro silenciosamente. Mudança de configuração precisa de política clara de merge/cópia de intel.

## Troca de corpo

A transação de troca do Stage 12 deve gerar invalidation/reconcile cartográfico após o novo body estar ativo:

1. salvar estado de A;
2. ativar B;
3. resolver `MapIntelProjection(B)`;
4. invalidar caches client-side de A;
5. remover markers/overlays não autorizados para B;
6. adicionar/atualizar os autorizados de B.

A remoção deve acontecer mesmo quando JourneyMap mantém seus próprios arquivos/cache locais.

## Quest state

Quest/intel precisa respeitar o escopo definido no Stage 12. Se uma quest for `BODY_LOCAL`, seus markers são do corpo. Quests explicitamente globais podem gerar intel global somente quando o design disser isso.

## Segurança contra vazamento

Testar especialmente:

- troca A → B sem logout;
- troca A → B → A;
- relog no corpo B;
- reinício do cliente;
- dimensão diferente;
- marker de quest ativo em A;
- POI secreto descoberto em A.

Nenhum cache transitório pode revelar dados de A a B.

## Acceptance

- Corpo B novo recebe mapa sem segredos de A no modo padrão;
- voltar a A restaura exatamente a projeção de A;
- nenhuma identidade física é duplicada por corpo;
- modo `ACCOUNT_GLOBAL`, se habilitado, possui migração/testes explícitos;
- JourneyMap/cache não quebra isolamento corporal.