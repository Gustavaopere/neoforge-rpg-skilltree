# Auditoria de binding geográfico — Região dos Ecos

## Estado
AUDITORIA MECÂNICA / SEM BINDING CANÔNICO.

## Alvo
`LOC-0001` — Região dos Ecos.

Este documento é auxiliar. O registro primário do local continua em `LOC-0001-regiao-dos-ecos.md`.

## Pergunta
Existe evidência suficiente no repositório atual para fixar dimensão, bioma, estrutura ou provider físico da Região dos Ecos sem inventar propriedades de worldgen?

## Resultado atual
**NÃO. O binding deve permanecer `NÃO FIXADO`.**

A decisão não decorre de ausência de opções no modpack. Ela decorre da ausência, nesta auditoria versionada, de uma cadeia de evidência completa que ligue um registry ID real aos requisitos físicos mínimos já declarados para `LOC-0001` e aos fatos narrativos aceitos da campanha.

## Fontes verificadas

- `historia/07-locais/LOC-0001-regiao-dos-ecos.md` — define função narrativa, discovery, invariantes e requisitos físicos mínimos sem selecionar worldgen;
- `historia/11-ia-e-autoria/11-contrato-geografia-compendio.md` — exige registry ID real, fatos verificados e binding tardio;
- `docs/compendium/INVENTORY.md` — documenta o coletor runtime e os produtos derivados em `generated/compendium/`, incluindo `runtime-registry-inventory.json` e `coverage-report.{json,md}`;
- árvore versionada auditada em `main@f3637a09b1479ab8d60fa60e916f1ad539a3ab7d` — não contém um snapshot `generated/compendium/` que possa ser usado como catálogo autoritativo de registry IDs do pack carregado.

## Evidência da árvore versionada

A inspeção Git da árvore exata de `main@f3637a09b1479ab8d60fa60e916f1ad539a3ab7d` confirmou:

- `generated/` contém somente `main-tree-layout.json`; não existe subárvore `generated/compendium/` nesse commit;
- `PROJECT-INSTRUCTIONS/modlist/` existe e contém os dossiês versionados dos mods;
- nenhum snapshot bruto `.txt` da modlist foi localizado nessa subárvore versionada;
- portanto os dossiês de mod podem orientar auditoria de provider, mas não substituem o `runtime-registry-inventory.json` nem criam registry IDs por inferência.

Esse resultado é evidência de **ausência do artefato versionado no commit auditado**, não prova de que o artefato nunca tenha sido gerado em uma instância local ou CI.

## O que já está comprovado narrativamente

Sem depender de provider específico, `LOC-0001` exige:

- uma área regional, não um ponto único;
- descoberta por investigação ou exploração independente;
- possibilidade de múltiplos vestígios/localizações internas;
- acesso possível sem teleporte narrativo obrigatório;
- pelo menos uma rota de exploração que não dependa de `NPC-0003`;
- capacidade de o estado do mundo mudar antes/depois da visita sem reset narrativo da região;
- representação de `EVD-0003` sem exigir uma estrutura registrável específica.

Esses itens são requisitos editoriais. Eles não provam clima, relevo, geologia, vegetação, raridade, dimensão, estrutura, mobs ou recursos.

## Gate para criar candidatos

Um candidato só pode entrar na lista quando houver, para o mesmo snapshot relevante:

1. registry ID exato no formato `BIOME:namespace:id`, `DIMENSION:namespace:id` ou `STRUCTURE:namespace:id` quando aplicável;
2. prova de que o ID existe no runtime/catalogação do Compêndio;
3. provider e versão correspondentes ao snapshot físico atual do modpack;
4. fatos de worldgen necessários obtidos de fonte verificável, e não deduzidos do nome do bioma;
5. comparação explícita contra os requisitos mínimos de `LOC-0001`;
6. indicação de fatos ainda desconhecidos e riscos de drift;
7. estado `CANDIDATO`, nunca `VINCULADO`, até a decisão editorial e a reconciliação com a Campaign Bible quando aplicável.

## Matriz de evidência para cada candidato

| Campo | Exigência |
| --- | --- |
| Registry ID | obrigatório e exato |
| Tipo | BIOME / DIMENSION / STRUCTURE |
| Provider | mod/vanilla responsável |
| Versão | proveniente da modlist física relevante |
| Fonte runtime | Compêndio, registry dump ou código/provider verificável |
| Fatos verificados | somente propriedades realmente comprovadas |
| Fatos desconhecidos | explicitamente listados |
| Compatibilidade com `LOC-0001` | requisito por requisito |
| Conflitos | worldgen, acesso, estrutura, quest, persistência, multiplayer, etc. |
| Estado | CANDIDATO / DESCARTADO / VINCULADO |
| Decisão editorial | referência/proveniência da decisão, quando existir |

## Evidência ainda necessária

### Catálogo runtime
Gerar ou recuperar o relatório correspondente ao snapshot corrente usando o pipeline oficial do Compêndio, em vez de reconstruir IDs por memória ou por pesquisa visual.

`docs/compendium/INVENTORY.md` registra a coleta opt-in do pack realmente carregado em `generated/compendium/runtime-registry-inventory.json` e a geração de `generated/compendium/coverage-report.{json,md}`. Esses produtos são derivados do runtime e não devem ser tratados como presentes/versionados no Git apenas porque o pipeline existe.

### Modlist física
No momento de avaliar candidatos concretos, consultar o snapshot físico mais recente aceito pelo pipeline do Compêndio e não congelar versões históricas descritas em documentação anterior. Os dossiês de `PROJECT-INSTRUCTIONS/modlist/` continuam úteis para capability/provider, mas não substituem o snapshot bruto requerido para fingerprint/drift.

### Provider/worldgen
Para propriedades que influenciem gameplay — acesso, raridade, geração de estruturas, condições ambientais ou recursos — consultar o provider correspondente. Nome traduzido, screenshot ou estética não são prova.

### Campaign Bible
Antes de promover candidato a `VINCULADO`, reconciliar qualquer fato geográfico já registrado no Grimoire/TTRPG.bot. A ausência de acesso ao Grimoire nesta sessão não autoriza substituir essa etapa.

## Decisões explícitas desta auditoria

- dimensão: continua PENDENTE;
- bioma: continua PENDENTE;
- estrutura obrigatória: continua NENHUMA neste estado;
- provider físico: continua PENDENTE;
- coordenadas: continuam NÃO FIXADAS;
- lista de candidatos: **VAZIA POR EVIDÊNCIA INSUFICIENTE**, não por ausência de possíveis biomas;
- nenhum fato de clima, relevo, geologia, vegetação, mobs ou recursos foi acrescentado.

## Próxima ação técnica segura

Quando houver acesso ao ambiente que gera a cobertura runtime do Compêndio:

1. produzir `generated/compendium/runtime-registry-inventory.json` no pack realmente carregado e gerar a cobertura correspondente à modlist atual;
2. filtrar somente IDs realmente existentes;
3. construir uma lista curta de candidatos com a matriz acima;
4. revisar os candidatos contra `LOC-0001` e o provider real;
5. reconciliar com o Grimoire;
6. somente então alterar `LOC-0001-regiao-dos-ecos.md` para `VINCULADO`.

## Regra de encerramento
Esta auditoria não deve ser considerada concluída por escolher “o bioma que combina”. Ela só fecha quando existe uma cadeia de evidência reproduzível do runtime até a decisão editorial.
