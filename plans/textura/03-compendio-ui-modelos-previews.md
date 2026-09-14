# 03 — Compêndio Natural: UI, modelos e previews

## Origem

Apresentação extraída de `plans/10-compendio-natural/09-ui-modelo3d-notas.md`.

O Stage 10 continua dono de catálogo, descoberta, snapshot autorizado, segurança de renderer, persistência de notas, filtros semânticos, relações e protocolos. Esta companion define como esses estados podem ser apresentados.

## Navegação principal

A organização visual deve suportar as categorias já definidas pelo Compêndio, incluindo Fauna, Flora, Árvores, Cultivos, Biomas, Estruturas, Dimensões, Descobertas e Favoritos/Notas. A seção Bosses é tratada em `06-cartografia-waypoints-bosses.md`.

Pesquisa e filtros devem permanecer legíveis para catálogos grandes. A engenharia decide indexação, virtualização e quais filtros existem; Textura decide composição, densidade, affordances e hierarquia visual.

## Lista e página de entrada

A apresentação deve acomodar:

- cabeçalho com nome localizado, origem e estado de descoberta quando permitido;
- preview/modelo ou fallback;
- resumo e seções de conteúdo autorizadas;
- relações navegáveis;
- notas/favoritos;
- metadata administrativa somente em modo permitido pela engenharia.

Nenhuma aba/painel deve ser criado apenas para exibir `N/A` quando o estágio de origem não fornece conteúdo.

## Preview 3D de entidades

A engenharia continua responsável por segurança, construção isolada, quarantine/fallback e proibição de AI/loot/sounds/particles/side effects.

Textura é responsável por:

- enquadramento;
- escala visual;
- câmera/rotação/zoom de apresentação;
- background/plate do preview;
- controles e affordances;
- fallback visual quando o renderer seguro não está disponível.

A camada visual não pode contornar blacklist, quarantine ou ausência de adapter seguro.

## Flora, árvores, blocos e estruturas

Quando a engenharia fornecer representação segura:

- itens/blocos podem ser apresentados com enquadramento consistente;
- estrutura/bioma/dimensão podem usar ícone, metadata visual ou screenshot próprio somente quando existir asset/proveniência aprovada;
- ausência de asset próprio usa fallback, não uma imagem inventada;
- renderização 3D adicional exige seam técnico aprovado antes da criação do asset dependente.

## Variantes

Um seletor visual de variantes só pode existir quando o Stage 10 projetar para o cliente a lista autorizada de variantes descobertas. Textura não enumera registry completo para preencher UI e não revela variantes secretas.

## Notas, favoritos e recentes

A persistência e o limite funcional pertencem ao Stage 10. A apresentação pode definir:

- painel/editor de nota;
- indicação clara quando a nota for apenas da sessão atual;
- affordance de favorito;
- histórico recente limitado quando o modelo funcional o fornecer;
- foco e prioridade visual que não interfiram na navegação principal.

## Proveniência/debug

Quando o modo avançado for permitido, a apresentação pode expor metadata técnica fornecida pelo Stage 10 em uma camada visual separada do fluxo survival. Não poluir a leitura normal com IDs técnicos.

## Escalas e validação

Validar visualmente, quando a superfície estiver materializada:

- escalas de GUI relevantes;
- resolução compacta, wide e ultrawide;
- clipping/wrapping;
- 1.000+ entradas com viewport funcional já fornecida pela engenharia, observando fluidez real sem transformar percepção em benchmark inventado;
- preview vanilla e modded seguro;
- fallback visível para renderer falho;
- navegação por teclado/mouse conforme estados funcionais existentes.

## Assets potenciais

Ícones, frames, tabs, backgrounds, cards, placeholders, badges, screenshots próprios e outros assets podem ser produzidos após aprovação visual. Nenhuma paleta, textura ou modelo específico foi definido nesta migração.

## Acceptance visual

O jogador deve conseguir pesquisar, navegar e compreender uma entrada sem IDs crus, sem vazamento de conteúdo oculto e com fallback visual claro quando um preview não puder ser produzido com segurança.
