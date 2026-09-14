# Auditoria de binding geográfico — Região dos Ecos

## Estado
AUDITORIA MECÂNICA / SEM BINDING CANÔNICO.

## Alvo
`LOC-0001` — Região dos Ecos.

Este documento é auxiliar. O registro primário do local continua em `LOC-0001-regiao-dos-ecos.md`.

## Resultado atual
**O binding deve permanecer `NÃO FIXADO`.**

Já existe evidência runtime reproduzível para uma pilha de compatibilidade limitada, mas ela não representa a modlist canônica completa. Portanto, ainda não há base para escolher dimensão, bioma, estrutura ou provider físico de `LOC-0001`.

A decisão exige cadeia de evidência reproduzível entre runtime, registry ID, provider, requisitos narrativos, snapshot físico completo e decisão editorial.

## Fontes e authority
- `historia/07-locais/LOC-0001-regiao-dos-ecos.md` — função narrativa e requisitos físicos mínimos;
- `historia/11-ia-e-autoria/11-contrato-geografia-compendio.md` — contrato de binding tardio;
- `docs/compendium/INVENTORY.md` — pipeline de coleta do pack carregado e limites de autoridade das coletas parciais;
- `generated/compendium/runtime-registry-inventory.json` e `coverage-report.{json,md}` — authority runtime quando realmente produzidos para o snapshot em uso;
- artifact de CI `volcanoes-full-pack-compatibility-9a4840cd4516e401d0a21cab0fd1f043902bebba`, run `34848925658`, digest `sha256:58a9b69ae5b952e3172dc166243e9f577bfee8e3845964c2b001f54fa4e1e10c` — evidência parcial da pilha de compatibilidade Volcanoes;
- Grimoire/TTRPG.bot — authority de lore estruturada quando houver fatos geográficos previamente registrados.

## Evidência runtime parcial já disponível
A PR técnica #540 passou a publicar uma coleta auditável no workflow historicamente chamado `Volcanoes Full Pack Compatibility Acceptance`.

A própria authority técnica registra que esse runtime é **deliberadamente limitado** à pilha de compatibilidade necessária para Volcanoes. O arquivo `compendium-inventory-scope.json` produzido no artifact declara:

- `scope_id=volcanoes_compatibility_acceptance_stack`;
- `complete_modpack_inventory=false`.

Consequências para esta auditoria:
- presença de um registry ID nessa coleta é evidência positiva de que o ID/provider existiu naquele runtime executado;
- ausência de um registry ID nessa coleta **não** é evidência de ausência na modlist física completa;
- o artifact não pode ser tratado como censo integral de biomas, dimensões ou estruturas disponíveis ao projeto;
- a coleta não fecha, sozinha, o gate de binding de `LOC-0001`;
- nenhum candidato deve ser descartado por não aparecer nessa pilha limitada;
- fatos narrativos/geográficos continuam fora da autoridade do artifact.

## Fatos confirmados no estado editorial atual
- `LOC-0001` é uma região narrativa, não um ponto único;
- deve permitir descoberta por investigação e por exploração independente;
- não pode depender exclusivamente de `NPC-0003` para acesso;
- `EVD-0003` não exige uma estrutura registrável específica;
- `FAC-0001` pode receber relatos da região sem ter vigilância total ou jurisdição automática;
- nenhum bioma, dimensão, clima, relevo, geologia, estrutura, mob ou recurso foi canonizado para a região.

## Gate para candidatos
Um candidato só entra na shortlist editorial quando houver:

1. registry ID exato no formato aplicável (`BIOME:namespace:id`, `DIMENSION:namespace:id`, `STRUCTURE:namespace:id`);
2. prova de existência no runtime/Compêndio correspondente ao snapshot físico relevante;
3. provider e versão identificados;
4. propriedades de worldgen necessárias obtidas de fonte verificável;
5. comparação requisito por requisito contra `LOC-0001`;
6. lista explícita de fatos desconhecidos e riscos de drift;
7. classificação da cobertura runtime (`completa` ou `parcial`) para impedir inferência negativa inválida;
8. estado `CANDIDATO`, nunca `VINCULADO`, até decisão editorial e reconciliação com o Grimoire.

## Matriz por candidato
| Campo | Exigência |
| --- | --- |
| Registry ID | obrigatório e exato |
| Tipo | BIOME / DIMENSION / STRUCTURE |
| Provider | vanilla/mod responsável |
| Versão | snapshot físico relevante |
| Fonte runtime | Compêndio, registry dump ou provider verificável |
| Escopo da coleta | completo / parcial + `scope_id` quando disponível |
| Fatos verificados | somente propriedades comprovadas |
| Fatos desconhecidos | explicitamente listados |
| Compatibilidade com `LOC-0001` | requisito por requisito |
| Conflitos | acesso, worldgen, persistência, quest, multiplayer etc. |
| Estado | CANDIDATO / DESCARTADO / VINCULADO |
| Decisão editorial | proveniência da decisão |

## Estado dos candidatos
Lista editorial atual: **vazia por ausência de inventário runtime da modlist canônica completa**.

Isso não significa ausência de opções. A coleta Volcanoes já demonstra que o mecanismo de inventário e evidência funciona e pode fornecer fatos positivos dentro de seu escopo; ela não possui autoridade de completude para definir a shortlist final desta região.

## Decisões mantidas
- dimensão: PENDENTE;
- bioma: PENDENTE;
- estrutura obrigatória: NENHUMA neste estado;
- provider físico: PENDENTE;
- coordenadas: NÃO FIXADAS;
- nenhum fato físico adicional deve ser inferido de nome, estética, screenshot ou afinidade temática.

## Próxima ação técnica segura
Para avançar o binding sem inventar geografia:

1. executar o coletor com `RPGSKILLTREE_COMPENDIUM_INVENTORY=1` numa instância que carregue o snapshot canônico completo da modlist;
2. preservar `runtime-registry-inventory.json`, fingerprint, modlist correspondente e relatório de cobertura;
3. validar explicitamente que a coleta representa o pack completo, em vez de reutilizar `complete_modpack_inventory=false` como se fosse completo;
4. filtrar somente `BIOME`, `DIMENSION` e `STRUCTURE` realmente presentes;
5. construir shortlist curta usando a matriz acima e propriedades verificadas dos providers;
6. reconciliar fatos geográficos com o Grimoire;
7. somente então alterar o dossiê primário para `VINCULADO`.

## Regra de encerramento
Esta auditoria não fecha por escolher “o bioma que combina” nem porque uma pilha de CI parcial produziu um registry dump. Fecha somente quando a decisão puder ser reproduzida do **snapshot físico completo** até o registro editorial.
