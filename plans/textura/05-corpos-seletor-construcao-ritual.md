# 05 — Corpos: seletor, construção e ritual

## Origem

Apresentação extraída de `plans/12-bodies-clones-progression-identities/12-ui-selector-ptbr.md`.

O Stage 12 continua dono de ownership, read model, estados de corpo, request de troca, validação server-side, construção/transmigração e localization keys.

## Seletor de corpos

A apresentação deve conseguir mostrar, somente a partir do read model autorizado:

- nome/apelido;
- tipo de corpo;
- estado funcional;
- nível RPG;
- classe/especialização quando disponível;
- localização/âncora quando autorizada;
- vida resumida quando fornecida;
- última ativação quando fornecida;
- avisos de incompatibilidade/provider.

Textura define cards/listas, hierarquia, badges, ícones e organização. Ela não decide quais corpos aparecem nem se uma troca é válida.

## Estados visuais

Devem existir tratamentos distinguíveis para os estados funcionais entregues pelo Stage 12, como corpo ativo, em construção, pronto/armazenado, recuperação necessária e destruído quando a configuração permitir sua exibição.

Não depender apenas de cor. A ação oferecida na interface sempre vem da disponibilidade funcional calculada pela engenharia.

## Ação de troca

A apresentação pode usar copy como “Transferir Consciência” ou “Trocar de Corpo” de acordo com as localization keys aprovadas. O clique envia intenção; somente a resposta server-side confirma a troca.

Operações destrutivas ou irreversíveis devem receber confirmação visual explícita quando a engenharia classificar a ação dessa forma.

## Construção tecnológica

A tela pode apresentar:

- progresso;
- recursos faltantes;
- energia/custo quando o read model fornecer;
- corpo em construção;
- estado persistente como pausado/pronto.

Layout, gauges, ícones e identidade visual tecnológica pertencem aqui. Cálculo de recursos, energia e progresso permanece no Stage 12/provider.

## Transmigração ritual

A superfície ritual pode ter identidade visual distinta da construção tecnológica, mas deve consumir os mesmos estados internos quando a engenharia os compartilha. Tema visual diferente não autoriza duplicar protocolo, persistência ou authority.

## Termos técnicos

`BodyProfile`, `bodyId`, enums internos e classes Java não são elementos de apresentação normal. Debug autorizado pode exibi-los em camada separada quando o Stage 12 fornecer esses campos.

## Acessibilidade

- estados distinguíveis sem somente cor;
- copy curto e descritivo;
- suporte visual à escala de GUI;
- foco/seleção visíveis quando a stack funcional os fornecer;
- confirmação explícita visual para ações classificadas como destrutivas.

## Acceptance visual

O jogador distingue rapidamente qual corpo está ativo, quais estão disponíveis e por que um corpo não pode ser ativado, sem receber informação de outro owner e sem a interface aparentar autoridade que pertence ao servidor.
