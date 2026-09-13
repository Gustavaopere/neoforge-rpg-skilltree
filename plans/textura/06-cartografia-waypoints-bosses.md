# 06 — Cartografia, waypoints e Boss Checklist

## Origem

Apresentação extraída de:

- `plans/13-cartography-regions-poi-discovery/07-journeymap-renderer-adapter.md`;
- `plans/10-compendio-natural/15-bosses-rastreamento-waypoints.md`.

Stage 13 continua dono dos dados cartográficos permitidos, anti-cheat, IDs, reconciliação e adapters. Stage 10 continua dono de classificação/progresso de bosses, tracking, persistência e política de waypoint. Textura define somente apresentação.

## Mapas e overlays

Quando o renderer/provider suportar a primitive necessária, a apresentação pode materializar:

- labels de região;
- limites/frontiers/overlays de regiões descobertas;
- markers/waypoints de POIs localizados;
- áreas aproximadas de busca;
- ícones por categoria;
- estados visuais de quest, visitado, concluído e estado físico conhecido.

O provider pode não oferecer todas as primitives. A engenharia decide a representação técnica suportada; Textura define o estilo visual dessa representação.

## Anti-cheat visual

A camada de apresentação recebe somente a projeção filtrada. Ela não mascara coordenada real em metadata, não lê records secretos e não reconstrói informação mais precisa do que a autorizada.

## Sem provider de mapa

Quando a engenharia informar que não há renderer de mapa disponível, a apresentação pode oferecer fallback no Compêndio/HUD somente com os dados autorizados, por exemplo dimensão, direção ou região aproximada.

Nenhum HUD concreto foi especificado nesta migração; sua composição futura deve ser definida aqui antes da produção de assets.

## Tracking de criatura

A página do Compêndio pode apresentar:

- habitat verificável;
- dimensão/biomas/estruturas/condições fornecidos pelo Stage 10;
- ação “Procurar / Seguir” quando a engenharia declarar o alvo elegível;
- distinção visual entre habitat conhecido, último avistamento e posição exata autorizada.

Esses três conceitos não podem compartilhar uma apresentação que sugira precisão inexistente.

## Boss Checklist

A seção visual de Bosses deve suportar os estados e dados já definidos pelo Stage 10:

- desconhecido/descoberto;
- pendente/não derrotado;
- derrotado;
- opcional quando aplicável;
- progresso agregado;
- ordenação e pré-requisitos fornecidos pelo domínio;
- filtros por dimensão/mod quando disponíveis.

Textura define cards, badges, ícones, barras/indicadores de progresso, hierarquia e layout. A derrota continua server-authoritative.

## Página de boss

A apresentação deve poder acomodar, quando o Stage 10 fornecer:

- status;
- posição na progressão;
- pré-requisitos;
- dimensão/bioma/estrutura/arena;
- como encontrar/invocar;
- itens/requisitos;
- drops/recompensas;
- variantes/fases autorizadas;
- ação de tracking.

Não duplicar a ficha normal da entidade; a camada Bosses é uma apresentação complementar do mesmo registro canônico.

## Ícones e estilo

Ícones de regiões, POIs, categorias, tracking e bosses pertencem a esta authority. Nenhuma iconografia, paleta ou asset específico foi inventado nesta migração.

## Acceptance visual

O jogador consegue distinguir informação aproximada de localização exata, reconhecer estado de descoberta/derrota e operar tracking sem a UI sugerir acesso a dados que o servidor não autorizou.
