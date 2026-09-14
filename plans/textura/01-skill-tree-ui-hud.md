# 01 — Skill Tree UI / HUD

## Origem

Apresentação extraída de `plans/07-data-network-ui/05-skill-tree-ui.md`.

O Stage 07 continua dono de snapshots, input funcional, requests de compra/respec, validação e authority. Este arquivo define apenas apresentação.

## Objetivo

Tornar a árvore de 512 nós navegável, legível e semanticamente clara sem usar aparência como autoridade de gameplay.

## Superfícies

### Canvas da árvore

- pan e zoom devem continuar legíveis nas escalas de GUI suportadas pelo cliente;
- layout deve preservar leitura de relações sem esconder requisitos;
- conexões precisam diferenciar relações relevantes sem exigir leitura de ID técnico;
- o enquadramento deve evitar que gateways/capstones desapareçam visualmente entre nós comuns.

A implementação funcional de pan/zoom/hit-testing permanece no Stage 07; aqui pertencem sensação visual, escala, acabamento e legibilidade.

### Estados visuais de nó

A engenharia fornece o estado. A apresentação deve possuir tratamento distinguível, quando aplicável, para:

- disponível/comprável;
- adquirido;
- bloqueado por requisito;
- bloqueado por pontos/custo;
- gateway;
- confluência/bridge;
- capstone;
- indisponível por provider/gate;
- seleção/hover/foco.

Não depender somente de cor. Forma, ícone, borda, padrão, label ou outro sinal redundante deve existir quando estados puderem ser confundidos.

### Tooltip

A engenharia fornece nome localizado, descrição, rank, custo, requisitos, efeito e motivo de recusa. Textura define:

- hierarquia tipográfica;
- agrupamento visual;
- ícones/badges;
- espaçamento e quebra;
- apresentação de requisitos satisfeitos/não satisfeitos;
- versão compacta/expandida se necessária;
- comportamento visual de hover/foco.

### Feedback de compra/respec

A apresentação deve tornar claro:

- sucesso confirmado pelo servidor;
- request pendente, se houver estado explícito;
- negação com motivo presentation-safe;
- alteração de pontos/rank depois da resposta autoritativa.

Nenhuma animação de sucesso pode antecipar uma confirmação server-side de modo a parecer que o unlock já ocorreu.

## HUD relacionado à progressão

Não existe nesta auditoria um HUD normativo completo para a skill tree. Qualquer HUD futuro deve consumir estados já expostos pela engenharia e ser especificado aqui antes de produzir assets.

## Assets esperados

Podem existir, quando o design visual for aprovado:

- frames de nós;
- ícones de categorias/gateways/capstones;
- conectores/linhas;
- backgrounds e ornamentação;
- indicadores de custo/pontos;
- sprites de hover/selected/locked;
- motion de transição estritamente cosmético.

Nenhum asset específico, paleta ou estilo foi inventado nesta migração.

## Acessibilidade

- estados importantes não dependem apenas de cor;
- texto não pode ser sacrificado por ornamentação;
- tooltip deve permanecer dentro da viewport ou possuir estratégia visual segura;
- foco de teclado deve ser visível quando a stack funcional o oferecer;
- zoom não pode tornar requisitos semanticamente ambíguos.

## Handoff mínimo da engenharia

Antes da produção final de assets, exigir os estados reais, campos presentation-safe, tipos de conexão e ações disponíveis no Stage 07. O renderer nunca concede unlock localmente.

## Acceptance visual

O jogador deve conseguir identificar visualmente por que um nó está bloqueado, quais relações importam e qual foi o resultado confirmado de compra/respec, sem depender de ID técnico ou somente de cor.
