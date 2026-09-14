# 04 — Itemização: tooltips e identidade visual

## Origem

Apresentação extraída de `plans/11-itemization-equipment-progression/13-ptbr-localization-ui.md`.

O Stage 11 continua dono de Rank, Poder do Item, Prefixos/Sufixos/Infixos, valores, aliases semânticos, localization keys e validators. Textura não altera a identidade persistida nem a potência do item.

## Objetivo

Apresentar a identidade RPG de equipamentos com hierarquia clara, sem transformar 15 modificadores em ruído visual e sem depender apenas de cor para Rank.

## Hierarquia de tooltip

A apresentação deve suportar os campos semânticos fornecidos pelo Stage 11:

- Rank;
- nome base localizado;
- Poder do Item;
- Prefixos e seus efeitos;
- Sufixos e seus efeitos;
- Infixos e suas descrições;
- indicação de identidade permanente/sem reroll quando aplicável;
- metadata avançada somente em modo permitido.

A ordem visual, espaçamento, grupos, ícones e badges pertencem aqui; valores e textos resolvidos pertencem à engenharia/localização.

## Rank

Rank pode possuir badge, moldura, ícone, padrão, prefixo visual ou cor. A solução final não pode depender só de cor e não deve concatenar todos os modifiers ao nome do item.

Nenhuma paleta específica foi definida nesta migração.

## Densidade

Tooltips extremos, inclusive itens com 5/5/5 modificadores, precisam de estratégia visual para continuar navegáveis:

- agrupamento por família;
- wrapping controlado;
- seções compactáveis/pagináveis somente se a implementação funcional suportar;
- detalhes avançados sob modifier key quando o Stage 11 expuser o estado correspondente;
- largura/altura sem clipping de tela.

## Ícones

Ícones de família, Rank ou efeito podem ser usados apenas como reforço. Informação necessária deve continuar disponível em texto ou outra pista equivalente.

## Debug avançado

Quando a engenharia fornecer item ID, instance ID abreviado, source, schema version, provider ou IDs técnicos em modo avançado, Textura os apresenta em camada separada do tooltip normal.

## Formatação

A engenharia entrega números, unidades, sinais e locale semanticamente corretos. Textura garante alinhamento, legibilidade e distinção de positivo/negativo sem redefinir o valor.

## Acceptance visual

Um item com alta densidade de modifiers continua compreensível, Rank é distinguível sem depender apenas de cor e o jogador não encontra IDs/keys crus no fluxo normal produzido pelo RPG.
