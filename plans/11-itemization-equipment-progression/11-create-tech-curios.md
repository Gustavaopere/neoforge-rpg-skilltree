# 11.11 — Create, equipamentos tecnológicos, mobilidade e Curios

## Objetivo

Cobrir equipamentos que normalmente escapam de sistemas de loot tradicionais: jetpacks, planadores, ferramentas tecnológicas, equipamentos Create/addons e wearables Curios.

## Passo a passo

### A — Create e addons

Fazer auditoria fresca das versões instaladas e identificar equipamentos realmente utilizáveis pelo jogador. Prioridades iniciais:

- Create Jetpack e variantes/compat de Curios;
- goggles e equipamentos vestíveis;
- ferramentas/armas Create quando existirem;
- equipamentos de mobilidade de Aeronautics/addons;
- armas/equipamentos tecnológicos adicionados por addons.

Não assumir que todo item `create:*` é equipamento.

### B — Categorias tecnológicas

Planejar pools específicos para capacidades reais, por exemplo:

- velocidade/controle de voo;
- eficiência de combustível/energia;
- capacidade;
- resistência/armadura;
- estabilidade/mobilidade;
- interações condicionais enquanto voando/usando energia.

Não conceder stats sem efeito real no item.

### C — Jetpacks

Contrato esperado:

- podem receber Rank/Poder e 1..5/1..5/1..5;
- modifiers de voo só entram quando adapter consegue produzir efeito real;
- equipar no slot normal ou Curios preserva a mesma identidade;
- combustível/energia continua governado pelo mod de origem;
- retirar/recarregar combustível não rerrola.

### D — Curios

- [ ] descobrir slot/type por API estável quando disponível;
- [ ] categorias por função e não apenas pelo nome do slot;
- [ ] pools de anel/colar/amuleto/wearable genérico;
- [ ] mudança de slot não duplica modifier;
- [ ] múltiplos Curios respeitam caps/exclusive groups globais quando necessário.

### E — Equipamento tecnológico genérico

Criar provider/fallback para itens de outros mods com energia, combustível, voo ou uso ativo, sem hardcode por item. Adapter específico só quando a capacidade genérica não for suficiente.

### F — Outputs de máquinas

Reutilizar o pipeline do 11.08. O adapter tecnológico classifica/aplica capacidades; não cria segundo gerador.

## Testes previstos

- jetpack itemizado e equipado em todos os slots suportados;
- modifier de voo/eficiência produz efeito real;
- combustível/energia não altera identidade;
- Curios múltiplos sem stacking duplicado;
- Create ausente não causa classloading;
- equipamento tecnológico desconhecido usa fallback seguro.

## Acceptance

Equipamentos tecnológicos, de mobilidade e Curios deixam de ser exceções da progressão e entram no mesmo sistema universal sem inventar efeitos que a API do item não consegue suportar.
