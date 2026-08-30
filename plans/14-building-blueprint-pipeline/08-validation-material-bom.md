# 14.08 — Validação estrutural e Bill of Materials

## Objetivo

Detectar erro antes de entregar o schematic ou iniciar construção.

## Validadores

- todos os registry IDs existem no target pack;
- block states e propriedades são válidos;
- bounds respeitados;
- portas/corredores obrigatórios não bloqueados;
- markers estão em posição válida;
- connectors externos alcançam a borda/endpoint esperado;
- functional graph coerente;
- nenhum bloco proibido por policy;
- volume/quantidade não excede budget configurado.

## BOM

Gerar contagem por:

- item/block ID;
- provider/mod;
- papel semântico;
- nível total;
- delta entre upgrades.

Itens sem forma direta de colocação devem ser tratados por regra do exporter/build system, não estimados silenciosamente.

## Materiais modded

O BOM usa IDs reais. Tags podem ajudar procurement, mas não substituem a contagem da versão concreta aprovada.

## Testes

- contagem exata em fixture conhecida;
- rotação não muda quantidade;
- upgrade delta correto;
- bloco inexistente falha;
- marker obstruído falha;
- BOM serializa de modo determinístico.

## Acceptance

Todo blueprint aprovado possui manifest e BOM reproduzíveis, sem block ID faltante ou componente funcional estruturalmente inválido.