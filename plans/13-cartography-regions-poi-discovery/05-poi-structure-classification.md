# 13.05 — Classificação de POIs e estruturas

## Objetivo

Transformar estruturas vanilla/modded e locais especiais em POIs semânticos que quests e mapa possam reutilizar.

## Categorias iniciais

O catálogo deve ser data-driven e extensível, com famílias como:

- `VILA`;
- `CIDADE`;
- `POSTO`;
- `TORRE_MAGICA`;
- `TEMPLO`;
- `RUINA`;
- `DUNGEON`;
- `FORTALEZA`;
- `CASTELO`;
- `ACAMPAMENTO`;
- `CAVERNA_ESPECIAL`;
- `MINA`;
- `LABORATORIO`;
- `ESTRUTURA_TECNOLOGICA`;
- `SANTUARIO`;
- `PORTAL`;
- `BOSS_ARENA`;
- `LOCAL_DE_QUEST`;
- `POI_GENERICO`.

## Classificador

Ordem recomendada:

1. override explícito por structure/resource ID;
2. tags de estruturas confiáveis;
3. adapter específico de mod quando necessário;
4. regras de namespace/padrão somente como fallback;
5. categoria genérica segura.

Uma estrutura pode ter categoria principal + traits, por exemplo:

```text
mod:wizard_tower
category = TORRE_MAGICA
traits = [ARCANO, HOSTIL, QUEST_ELIGIBLE]
```

## Identidade física

`poiId` deve ser derivado/persistido de forma estável a partir da instância física da estrutura, não apenas do registry ID. Duas vilas do mesmo tipo são POIs diferentes.

Registrar quando disponível:

- structure start / anchor;
- bounds;
- dimensão;
- source registry key;
- mod de origem;
- regionId relacionado;
- estado físico.

## Estruturas não registradas

Alguns mods criam locais por blocos, entidades ou lógica própria em vez de `StructureStart`. O domínio deve aceitar `PoiProvider` opcional para registrar POIs externos sem quebrar a classificação geral.

## Estado físico

Separar descoberta de estado real. Exemplos:

- `INTACTO`;
- `DANIFICADO`;
- `DESTRUIDO`;
- `OCUPADO`;
- `DOMINADO`;
- `ABANDONADO`.

Esses estados só existem quando houver evidência/integração confiável; não inferir destruição escaneando blocos arbitrariamente a cada tick.

## Quest eligibility

POIs podem declarar tags como:

- `quest_eligible`;
- `repeatable_eligible`;
- `unique_landmark`;
- `boss_site`;
- `safe_settlement`;
- `dangerous_site`.

Quests escolhem por categoria/tags e regras espaciais, não por nomes de mods hardcoded sempre que possível.

## Acceptance

- estruturas vanilla recebem POIs distintos e estáveis;
- duas estruturas iguais em posições diferentes não colidem;
- estrutura modded desconhecida recebe fallback sem crash;
- provider opcional pode cadastrar local não-StructureStart;
- nenhum POI oculto precisa ser enviado ao cliente para existir no servidor.