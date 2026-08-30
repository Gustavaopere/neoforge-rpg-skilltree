# 13.03 — Segmentação incremental de regiões e fronteiras

## Objetivo

Construir regiões contínuas a partir de áreas observadas/geradas sem escanear o mundo inteiro e sem depender de geometria armazenada pelo JourneyMap.

## Estratégia espacial

A unidade canônica **não pode ser um único biome dominante por chunk**, porque Minecraft 1.21.1 permite biomas diferentes no mesmo X/Z em alturas distintas, incluindo cavernas sob um biome de superfície.

Usar uma representação estratificada:

- `SurfaceCell`: célula 2D por chunk para a camada cartográfica de superfície;
- `SubterraneanCell`: célula 3D/por seção vertical somente quando uma camada subterrânea relevante for efetivamente observada;
- outras camadas verticais especiais podem ser acrescentadas por dados/adapters sem alterar a identidade da superfície.

Uma `RegionInstance` declara sua `RegionLayer` (`SURFACE`, `SUBTERRANEAN` ou outra registrada). Regiões de camadas diferentes nunca são fundidas apenas porque compartilham X/Z.

Quando uma área entra no domínio observável:

1. amostrar/classificar a superfície usando apenas posições representativas da superfície observada;
2. identificar seções verticais efetivamente visitadas/observadas que possuam biome/tipo semanticamente distinto e relevante;
3. materializar somente as `SubterraneanCell` necessárias — sem varrer toda a coluna nem force-loadar seções;
4. classificar cada célula pela taxonomia do 13.02;
5. consultar células adjacentes da **mesma camada espacial**;
6. anexar à região compatível ou criar nova `RegionInstance`;
7. quando duas ilhas previamente separadas da mesma camada se conectarem, executar merge transacional/determinístico;
8. atualizar somente fronteiras/volumes afetados.

Queries de localização usam `(dimension, x, y, z)` quando o contexto possui altura. Uma query sem Y é explicitamente uma query de superfície e não pode retornar silenciosamente uma região subterrânea.

## Identidade e merge

Merge de regiões não pode rerrolar nome arbitrariamente. Definir regra determinística para escolher o `regionId` sobrevivente, por exemplo identidade mais antiga/canônica, com aliases dos IDs absorvidos para migração de intel/quests.

Split por mudança de taxonomia também exige aliases e migração explícita; não deve acontecer em hot path sem versionamento.

## Fronteiras e volumes

A representação interna deve ser compacta e adequada a queries espaciais.

Para `SURFACE`:

- borda derivada de células ocupadas;
- simplificação/polygonização bounded;
- LOD por zoom no renderer.

Para `SUBTERRANEAN`:

- volume/section-set interno preserva a dimensão Y;
- o renderer pode projetar somente a camada/andar atualmente autorizado ou resumir a região subterrânea por marcador/overlay específico;
- nunca colapsar o volume subterrâneo em uma classificação chunk-wide que substitua a superfície.

Em qualquer camada:

- nenhum polígono/volume complexo é recalculado a cada frame;
- cache é invalidado somente quando a região correspondente muda.

## Barreiras e conectividade

Regras data-driven podem impedir merge mesmo entre famílias similares quando houver:

- dimensão diferente;
- `RegionLayer` diferente;
- massa oceânica/rios importantes;
- barreira territorial especial;
- regra de worldgen/estrutura;
- distância/gap acima do permitido.

Não presumir que todas as células do mesmo `RegionType` no mundo pertencem à mesma região.

## Jobs

Processamento pesado deve ser:

- incremental;
- bounded por tick/job;
- cancelável/reentrante;
- sem chunk force-load apenas para completar mapa;
- persistente o suficiente para continuar após restart quando necessário.

## Acceptance

- nenhum teste permite scan da dimensão inteira ou da coluna vertical inteira apenas para cartografia;
- região de superfície cresce conforme novos chunks são observados;
- cave biome e surface biome no mesmo X/Z permanecem em regiões/camadas distintas;
- query com Y resolve corretamente `SUBTERRANEAN` versus `SURFACE`;
- query sem Y é explicitamente de superfície;
- merge preserva identidade via alias;
- fronteira/volume é determinístico para a mesma entrada;
- grande região não causa recomputação quadrática;
- chunks/seções não carregados não são forçados apenas por cartografia.