# Auditoria de binding geográfico — Região dos Ecos

## Estado
AUDITORIA MECÂNICA / SEM BINDING CANÔNICO.

## Alvo
`LOC-0001` — Região dos Ecos.

Este documento é auxiliar. O registro primário do local continua em `LOC-0001-regiao-dos-ecos.md`.

## Resultado atual
**O binding deve permanecer `NÃO FIXADO`.**

A existência de muitos biomas/providers no modpack não é suficiente para escolher dimensão, bioma, estrutura ou provider físico. A decisão exige cadeia de evidência reproduzível entre runtime, registry ID, provider, requisitos narrativos e decisão editorial.

## Fontes e authority
- `historia/07-locais/LOC-0001-regiao-dos-ecos.md` — função narrativa e requisitos físicos mínimos;
- `historia/11-ia-e-autoria/11-contrato-geografia-compendio.md` — contrato de binding tardio;
- `docs/compendium/INVENTORY.md` — pipeline de coleta do pack carregado;
- `generated/compendium/runtime-registry-inventory.json` e `coverage-report.{json,md}` — authority runtime quando realmente produzidos para o snapshot em uso;
- Grimoire/TTRPG.bot — authority de lore estruturada quando houver fatos geográficos previamente registrados.

## Fatos confirmados no estado editorial atual
- `LOC-0001` é uma região narrativa, não um ponto único;
- deve permitir descoberta por investigação e por exploração independente;
- não pode depender exclusivamente de `NPC-0003` para acesso;
- `EVD-0003` não exige uma estrutura registrável específica;
- `FAC-0001` pode receber relatos da região sem ter vigilância total ou jurisdição automática;
- nenhum bioma, dimensão, clima, relevo, geologia, estrutura, mob ou recurso foi canonizado para a região.

## Gate para candidatos
Um candidato só entra na auditoria quando houver:

1. registry ID exato no formato aplicável (`BIOME:namespace:id`, `DIMENSION:namespace:id`, `STRUCTURE:namespace:id`);
2. prova de existência no runtime/Compêndio correspondente ao snapshot físico relevante;
3. provider e versão identificados;
4. propriedades de worldgen necessárias obtidas de fonte verificável;
5. comparação requisito por requisito contra `LOC-0001`;
6. lista explícita de fatos desconhecidos e riscos de drift;
7. estado `CANDIDATO`, nunca `VINCULADO`, até decisão editorial e reconciliação com o Grimoire.

## Matriz por candidato
| Campo | Exigência |
| --- | --- |
| Registry ID | obrigatório e exato |
| Tipo | BIOME / DIMENSION / STRUCTURE |
| Provider | vanilla/mod responsável |
| Versão | snapshot físico relevante |
| Fonte runtime | Compêndio, registry dump ou provider verificável |
| Fatos verificados | somente propriedades comprovadas |
| Fatos desconhecidos | explicitamente listados |
| Compatibilidade com `LOC-0001` | requisito por requisito |
| Conflitos | acesso, worldgen, persistência, quest, multiplayer etc. |
| Estado | CANDIDATO / DESCARTADO / VINCULADO |
| Decisão editorial | proveniência da decisão |

## Estado dos candidatos
Lista atual: **vazia por ausência de cadeia de evidência runtime completa**, não por ausência presumida de opções no modpack.

## Decisões mantidas
- dimensão: PENDENTE;
- bioma: PENDENTE;
- estrutura obrigatória: NENHUMA neste estado;
- provider físico: PENDENTE;
- coordenadas: NÃO FIXADAS;
- nenhum fato físico adicional deve ser inferido de nome, estética, screenshot ou afinidade temática.

## Próxima ação técnica segura
Quando o snapshot runtime do Compêndio estiver disponível:

1. produzir/recuperar `runtime-registry-inventory.json` e `coverage-report` do pack realmente carregado;
2. filtrar apenas IDs existentes;
3. construir uma shortlist curta usando a matriz acima;
4. revisar cada candidato contra o provider e contra `LOC-0001`;
5. reconciliar fatos geográficos com o Grimoire;
6. somente então alterar o dossiê primário para `VINCULADO`.

## Regra de encerramento
Esta auditoria não fecha por escolher “o bioma que combina”. Fecha somente quando a decisão puder ser reproduzida do runtime até o registro editorial.
