# 05 — Combat & Magic Hooks

Estado: **EM ANDAMENTO**.

## Objetivo
Aplicar perks a melee, projéteis, magia, cura e summons por contratos canônicos, evitando dupla aplicação quando outros mods também alteram o evento.

## Regras
- escolher um ponto canônico por cálculo;
- distinguir dano base, multiplicador aditivo/multiplicativo e dano final;
- compatibilidade com Epic Fight não pode também executar o fallback vanilla no mesmo hit;
- magia genérica não deve ser apresentada como suporte nominal a cada spell.

## Critérios de aceite
- [ ] exatamente uma rolagem/aplicação por ataque onde o contrato exigir;
- [ ] source/owner de summons preservado;
- [ ] casts cancelados não concedem mastery;
- [ ] testes cobrem vanilla + mods instalados individualmente e em conjunto.