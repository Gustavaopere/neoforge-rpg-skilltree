# 13.03 — Segmentação incremental de regiões e fronteiras

## Objetivo

Construir regiões contínuas a partir de chunks observados/gerados sem escanear o mundo inteiro e sem depender de geometria armazenada pelo JourneyMap.

## Estratégia

Usar células espaciais preferencialmente baseadas em chunk. Quando um chunk entra no domínio observável:

1. obter o biome/tipo dominante necessário à cartografia;
2. classificar pela taxonomia do 13.02;
3. consultar células adjacentes já indexadas;
4. anexar à região compatível ou criar nova `RegionInstance`;
5. quando duas ilhas previamente separadas se conectarem, executar merge transacional/determinístico;
6. atualizar somente fronteiras afetadas.

## Identidade e merge

Merge de regiões não pode rerrolar nome arbitrariamente. Definir regra determinística para escolher o `regionId` sobrevivente, por exemplo identidade mais antiga/canônica, com aliases dos IDs absorvidos para migração de intel/quests.

Split por mudança de taxonomia também exige aliases e migração explícita; não deve acontecer em hot path sem versionamento.

## Fronteiras

A representação interna deve ser compacta e adequada a queries espaciais. A representação visual pode ser simplificada:

- borda derivada de células ocupadas;
- simplificação/polygonização bounded;
- LOD por zoom no renderer;
- nenhum polygon complexo recalculado a cada frame;
- cache invalidado somente quando região muda.

## Barreiras e conectividade

Regras data-driven podem impedir merge mesmo entre famílias similares quando houver:

- dimensão diferente;
- massa oceânica/rios importantes;
- barreira territorial especial;
- regra de worldgen/estrutura;
- distância/gap acima do permitido.

Não presumir que todos os chunks do mesmo `RegionType` no mundo pertencem à mesma região.

## Jobs

Processamento pesado deve ser:

- incremental;
- bounded por tick/job;
- cancelável/reentrante;
- sem chunk force-load apenas para completar mapa;
- persistente o suficiente para continuar após restart quando necessário.

## Acceptance

- nenhum teste permite scan da dimensão inteira;
- região cresce conforme novos chunks são observados;
- merge preserva identidade via alias;
- fronteira é determinística para a mesma entrada;
- grande região não causa recomputação quadrática;
- chunks não carregados não são forçados apenas por cartografia.