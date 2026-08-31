# A0112 — Auto-Manutenção

## Estado
**DESIGN APROVADO — FAIL-CLOSED / NÃO COMPRÁVEL pela cadeia A0111→A0110/P-0036.**

## Contrato
Notable ENGINEERING, 3 ranks. Após ≥200 ticks sem causar/receber dano hostil, permite no máximo um ciclo bem-sucedido por jogador a cada 600/480/360 ticks. Candidatos: somente equipamentos tecnológicos danificados, reparáveis e atualmente em mãos/equipados/posição ativa exposta por adapter. Escolher deterministicamente o menor `durability_remaining/durability_max`; consumir recurso nativo antes do reparo.

## Invariantes
Inventário armazenado não é escaneado. Não existe recurso universal de reparo. Quantidade reparada e custo pertencem ao provider. Falha de débito cancela reparo e não inicia cooldown.

## Provider
Oritech 1.2.11 e demais famílias somente por adapter de posição + reparabilidade + custo atômico. Protection Pixel mantém manutenção própria.

## Chat 2
Availability deve herdar todos os gates dos predecessores. Testar empate determinístico, relog/dimensão, custo insuficiente, item quebrado/unbreakable e ausência de adapter.