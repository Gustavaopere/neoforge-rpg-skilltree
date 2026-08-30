# 14.07 — Máquinas Create funcionais dentro dos blueprints

## Objetivo

Distinguir estética industrial de mecanismo real. Quando um prédio declarar componentes Create funcionais, o layout deve respeitar conexões físicas e o runtime deve saber quais partes são decorativas e quais são operacionais.

## FunctionalGraph

Markers podem declarar papéis como:

```text
KINETIC_INPUT
KINETIC_OUTPUT
FLUID_INPUT
FLUID_OUTPUT
HEAT_CORE
MAINTENANCE_ACCESS
CONTROL_POINT
INVENTORY_INPUT
INVENTORY_OUTPUT
```

O graph não tenta simular Create; apenas descreve a intenção necessária para validação e integração.

## Regras

- shafts/cogs orientados precisam formar o caminho declarado;
- componentes que dependem de RPM/stress devem ser testados com Create real antes de serem chamados funcionais;
- conexão visual sem função é marcada `DECORATIVE`;
- o `Generator Core` do Stage 19 permanece autoridade da produção térmica RPG; Create pode fornecer mecânica, bombas, transporte e visualização onde o contrato real permitir;
- ausência de Create torna o blueprint dependente indisponível, não vanillaizado.

## Testes

- graph desconectado falha validação;
- orientations compatíveis;
- markers sobrevivem export/rotação;
- provider-present smoke confirma mecanismo escolhido;
- core-only não carrega classes Create.

## Acceptance

Uma máquina descrita como funcional possui caminho verificável e teste provider-present; não é apenas um conjunto de blocos bonitos.