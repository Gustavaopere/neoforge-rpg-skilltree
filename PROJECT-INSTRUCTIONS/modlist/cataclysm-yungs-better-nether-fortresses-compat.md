# Cataclysm x YUNG's Better Nether Fortresses Compat — 1.21.1

> ✅ Versão física confirmada: `cataclysmfortresses-1.21.1-NeoForge.jar`, mod id `cataclysmfortresses`, runtime `1.21.1`. É uma **bridge server-side mínima**, não um mod de conteúdo independente.

## 1. Papel exato
A finalidade oficial é permitir que **Ignited Berserkers** do L_Ender's Cataclysm possam aparecer nas estruturas de **YUNG's Better Nether Fortresses** depois que Ignis foi derrotado.

A bridge não adiciona uma nova fortaleza, boss, mob, biome ou loot table.

## 2. Implementação declarada pelo autor
O próprio projeto descreve a implementação como simples: adicionar a Better Fortress à tag de estruturas de Cataclysm **`#berserker_spawn`**.

Isso significa que a authority continua dividida assim:
- Cataclysm: mob, condição pós-Ignis e lógica de spawn;
- YUNG's Better Nether Fortresses: estrutura;
- compat: associação da estrutura à tag esperada pelo Cataclysm.

## 3. Dependências físicas
Required:
- L_Ender's Cataclysm;
- YUNG's Better Nether Fortresses;
- dependências desses providers.

No pack atual:
- Cataclysm `3.33`;
- YUNG's Better Nether Fortresses `1.21.1-NeoForge-3.1.5`.

Se qualquer provider for removido, a necessidade desta bridge deve ser reavaliada.

## 4. Pós-Ignis
A página oficial diz explicitamente que os Ignited Berserkers aparecem **após derrotar Ignis**. A bridge não deve implementar uma segunda flag de progressão; ela apenas amplia a superfície de estrutura elegível para a lógica já existente do Cataclysm.

Quest/RPG integration deve consultar o advancement/state real do provider se precisar dessa progressão.

## 5. Datapack/tag semantics
Como a compat é essencialmente data/tag-oriented, um datapack do modpack precisa **mesclar** a tag, não sobrescrever a lista sem intenção.

Riscos:
- `replace: true` em datapack externo remover a Better Fortress ou outras estruturas válidas;
- namespace/tag mudar em versão futura do Cataclysm;
- structure ID de YUNG mudar em update;
- data reload deixar tag final diferente do startup.

## 6. Server-only
O projeto classifica o ambiente como **Server**. Não há razão funcional para carregar UI/render/assets de gameplay no cliente para a bridge.

Isso reduz o surface area, mas não elimina a necessidade de testar dedicated server e datapack reload.

## 7. Lifecycle
Validar:
1. server bootstrap com os dois providers;
2. registry/tag load;
3. datapack reload;
4. mundo antes de derrotar Ignis;
5. mundo depois de derrotar Ignis;
6. Better Fortress em chunks novos e existentes;
7. server restart depois da progressão.

A tag deve continuar resolvendo a structure e a condição de Cataclysm deve manter authority.

## 8. O que a bridge NÃO faz
Não atribuir a este mod:
- nova AI de Ignited Berserker;
- alteração de stats/drop do mob;
- geração de Better Fortress;
- spawn antes de Ignis;
- loot injection;
- compat geral entre todos mobs Cataclysm e todas estruturas YUNG.

A função documentada é específica ao `berserker_spawn`.

## 9. Riscos
1. Tag ID mudar.
2. Datapack externo sobrescrever a tag.
3. Um provider ser removido e a bridge ficar órfã.
4. Condição pós-Ignis ser duplicada por script externo.
5. Spawn density ser ajustada por outro mod e atribuído erroneamente à bridge.

## 10. Matriz de testes
1. Dedicated server boot com Cataclysm 3.33 e Better Nether Fortresses 3.1.5.
2. Antes de Ignis: validar ausência conforme regra provider.
3. Depois de Ignis: visitar Better Fortress e confirmar eligibility do Berserker.
4. Datapack reload preservando tag.
5. Verificar tag final via datapack/registry tooling, sem `replace` acidental.
6. Restart após derrotar Ignis.
7. Ausência da bridge em ambiente de controle para provar o efeito específico quando necessário.

## 11. Evidência
- modlist física atual: `cataclysmfortresses-1.21.1-NeoForge.jar`;
- CurseForge oficial: Ignited Berserkers em YUNG's Better Nether Fortresses após Ignis;
- descrição oficial da implementação: adição da Better Fortress à tag `#berserker_spawn`;
- providers físicos presentes nas versões registradas acima.

> 🔗 Exaustividade proporcional: esta compat é pequena. **Completo** aqui significa documentar precisamente a única bridge que ela faz, seus owners, reload/dependency risks e não inventar conteúdo inexistente.
