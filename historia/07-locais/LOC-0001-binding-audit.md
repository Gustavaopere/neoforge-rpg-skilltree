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
- artifact de CI `volcanoes-full-pack-compatibility-9a4840cd4516e401d0a21cab0fd1f043902bebba`, run `34848925658`, digest `sha256:58a9b69ae5b952e3172dc166243e9f577bfee8e3845964c2b001f54fa4e1e10c` — origem da evidência parcial da pilha de compatibilidade Volcanoes;
- `docs/compendium/evidence/run-34848925658/` — cópia durável, reconstruível e verificada por SHA-256 do inventory e do scope extraídos desse artifact;
- modlist física/JAR + documentação/source do provider — authority separada para atribuição de ownership e versão de uma entrada de registry;
- Grimoire/TTRPG.bot — authority de lore estruturada quando houver fatos geográficos previamente registrados.

## Evidência runtime parcial já disponível
A PR técnica #540 passou a publicar uma coleta auditável no workflow historicamente chamado `Volcanoes Full Pack Compatibility Acceptance`.

A própria authority técnica registra que esse runtime é **deliberadamente limitado** à pilha de compatibilidade necessária para Volcanoes. O arquivo `compendium-inventory-scope.json` produzido no artifact declara:

- `scope_id=volcanoes_compatibility_acceptance_stack`;
- `complete_modpack_inventory=false`;
- `entry_count=928`;
- `loaded_mod_count=36`;
- `runtime_fingerprint_sha256=344d844d8be6b4890aa83018337ed7bb3e37ddc7c541ca0b28b6d6995114e463`.

Como o artifact de Actions possui retenção finita, os bytes relevantes foram preservados em `docs/compendium/evidence/run-34848925658/`. O `README.md` desse diretório registra provenance, hashes dos arquivos originais e procedimento de reconstrução.

Consequências para esta auditoria:
- presença de um registry ID nessa coleta é evidência positiva de que **a entrada existiu naquele runtime executado**;
- o dump, isoladamente, **não prova ownership de provider nem versão** para entradas arbitrárias; namespace pode não equivaler ao mod que forneceu o conteúdo, especialmente com datapacks/bridges;
- provider e versão precisam ser confirmados separadamente pela modlist física/JAR e por documentação/source verificável antes de satisfazer o gate correspondente;
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
3. provider e versão **identificados por evidência independente da mera inferência de namespace**;
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
| Provider | vanilla/mod responsável, verificado separadamente do namespace quando necessário |
| Versão | snapshot físico relevante, derivado da modlist/JAR ou source verificável |
| Fonte runtime | Compêndio/registry dump para presença da entrada |
| Fonte de ownership | modlist/JAR/documentação/source do provider |
| Escopo da coleta | completo / parcial + `scope_id` quando disponível |
| Fatos verificados | somente propriedades comprovadas |
| Fatos desconhecidos | explicitamente listados |
| Compatibilidade com `LOC-0001` | requisito por requisito |
| Conflitos | acesso, worldgen, persistência, quest, multiplayer etc. |
| Estado | CANDIDATO / DESCARTADO / VINCULADO |
| Decisão editorial | proveniência da decisão |

## Estado dos candidatos
Lista editorial atual: **vazia por ausência de inventário runtime da modlist canônica completa**.

Isso não significa ausência de opções. A coleta Volcanoes já demonstra que o mecanismo de inventário e evidência funciona e pode fornecer fatos positivos de presença dentro de seu escopo; ela não possui autoridade de completude nem de ownership universal para definir a shortlist final desta região.

## Decisões mantidas
- dimensão: PENDENTE;
- bioma: PENDENTE;
- estrutura obrigatória: NENHUMA neste estado;
- provider físico: PENDENTE;
- coordenadas: NÃO FIXADAS;
- nenhum fato físico adicional deve ser inferido de nome, estética, screenshot, namespace ou afinidade temática.

## Próxima ação técnica segura
Para avançar o binding sem inventar geografia:

1. executar o coletor com `RPGSKILLTREE_COMPENDIUM_INVENTORY=1` numa instância que carregue o snapshot canônico completo da modlist;
2. preservar `runtime-registry-inventory.json`, fingerprint, modlist correspondente e relatório de cobertura em storage durável/versionado;
3. validar explicitamente que a coleta representa o pack completo, em vez de reutilizar `complete_modpack_inventory=false` como se fosse completo;
4. filtrar somente `BIOME`, `DIMENSION` e `STRUCTURE` realmente presentes;
5. para cada candidato, verificar ownership/provider e versão separadamente contra a modlist/JAR/source quando o dump não for suficiente;
6. construir shortlist curta usando a matriz acima e propriedades verificadas dos providers;
7. reconciliar fatos geográficos com o Grimoire;
8. somente então alterar o dossiê primário para `VINCULADO`.

## Regra de encerramento
Esta auditoria não fecha por escolher “o bioma que combina” nem porque uma pilha de CI parcial produziu um registry dump. Fecha somente quando a decisão puder ser reproduzida do **snapshot físico completo**, com presença runtime e ownership verificadas em seus domínios corretos, até o registro editorial.
