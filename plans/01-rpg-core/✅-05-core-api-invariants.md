# RPG Core Plan — Internal API and Invariants

**Goal:** oferecer contratos estáveis para todos os estágios posteriores.

- [x] Separar queries de commands/mutations.
- [x] Expor snapshots imutáveis para UI/scaling/adapters.
- [x] Documentar invariantes de level/XP/mastery/unlocks.
- [x] Impedir dependência do core em classes de UI ou mods opcionais.
- [x] Cobrir limites de XP, rank, pontos e IDs em testes.

**Acceptance:** consumidores usam API explícita e mudanças internas de storage não vazam para integrações.

## Contrato interno canônico

### Queries

- `CoreProgressionQueryService.snapshot(...)` é a projeção somente-leitura do estado de level/XP/Core Points/atributos/regras.
- `CanonicalPlayerQueryService.snapshot(...)` compõe a visão somente-leitura do envelope RPG completo.
- `CoreProgressionQuerySnapshot` e `CanonicalPlayerSnapshot` são os tipos de projeção destinados a UI, scaling e adapters; consumidores não precisam conhecer attachments, codecs ou payloads de rede.
- `CanonicalPlayerQueryServiceTest.snapshotDoesNotExposeLegacyProgressionAuthorities()` impede que `ProgressionState` ou `PassivePointLedger` voltem a aparecer como autoridade pública no snapshot canônico.
- A fronteira NeoForge de consulta permanece `CanonicalPlayerQueryRuntime`, já protegida por `verify-canonical-player-runtime.py`: observação não materializa migration, não escreve attachments e não sincroniza rede como efeito colateral.

### Commands / mutations

- `CoreProgressionMutationService` é a fronteira pura para XP, rollback privilegiado, Core Point transactions e expansão do orçamento principal.
- `AttributeRankMutationService` faz compra/refund de ranks atomicamente com o ledger compartilhado.
- Persistência e sincronização são efeitos posteriores da runtime boundary; snapshots não são objetos mutáveis de comando.

## Invariantes estáveis

- **Level:** começa em `0`, nunca é negativo e o teto `Long.MAX_VALUE` é apenas limite técnico da representação; não existe gameplay level cap finito no core.
- **XP:** grants normais nunca aceitam valor negativo; rollback é uma operação privilegiada separada. `xpIntoLevel` nunca é negativo nem pode alcançar o custo do próximo nível. Aritmética acumulada usa `BigInteger` e falha explicitamente quando o estado não pode mais ser representado.
- **Core Points:** créditos, spends e refunds são transações positivas e identificáveis. Repetir a mesma identidade/payload é idempotente; reutilizar a identidade com outro payload falha fechado. Alocação/refund não pode criar saldo ou allocation inválidos.
- **Attribute ranks:** são `long`, nunca negativos, usam aritmética checked, não permitem refund acima do rank atual e expõem cópias imutáveis.
- **Mastery:** lane/source/replay IDs são não vazios; XP é não negativo e awards são positivos. Replay keys opcionais deduplicam emissões auxiliares e rejeitam payload conflitante. O limite de receipts recentes é técnico e não é cap de mastery.
- **Unlocks e IDs persistidos:** class IDs, node IDs e discovery keys são não vazios. Estados públicos fazem defensive copy de mapas/sets; callers não podem alterar progressão por referência compartilhada.
- **Stable IDs:** renomes persistidos exigem alias/migration; integrações não devem inferir identidade por display name.

## Barreira de dependências

`scripts/verify-core-api-boundary.py` percorre todo `src/main/java/dev/gustavopere/rpgskilltree/core/` e permite apenas imports do JDK ou do próprio pacote `core`. Isso torna regressão de arquitetura um erro de CI: classes Minecraft/NeoForge, runtime/client/UI/network e APIs de mods opcionais não podem entrar no modelo puro.

O mesmo verificador exige explicitamente as superfícies de query, mutation e snapshot acima e confirma que `CoreApiInvariantTest` continua fazendo parte da suíte core.

## Cobertura de limites

- `InfiniteProgressionFoundationTest`, `CharacterXpRollbackTest` e `CoreProgressionQueryServiceTest`: XP/level, grandes valores, regras incompatíveis e teto técnico.
- `AttributeRanksTest` e `AttributeRankMutationServiceTest`: ranks negativos, overflow, refund/custo e IDs canônicos de atributos.
- `CorePointEconomyTest` e `CoreProgressionMutationServiceTest`: saldo, allocation, refund, duplicate/conflicting transaction IDs e overflow.
- `MasteryRuntimeCoreTest` e `MasteryAwardIdempotencyTest`: acumulação, replay-safe idempotency e conflito de replay identity.
- `CanonicalPlayerQueryServiceTest`: snapshot canônico sem vazamento das autoridades de storage legadas.
- `CoreApiInvariantTest`: IDs vazios, valores negativos essenciais, defensive copies e impossibilidade de mutar coleções públicas de mastery/classes/nodes/discoveries/attributes.

## Interpretação do acceptance

Consumidores podem depender das projeções e serviços explícitos acima sem conhecer a representação persistida. A barreira de imports torna a direção de dependência verificável; os testes de snapshots e coleções tornam o contrato observacional imutável; e as mutation services concentram as mudanças de estado com validação fail-closed.

## Verificação de fechamento

- Implementação integrada pelo PR #131.
- Merge canônico: `fd2879c1c7375ab006cafb022f10bd8700f2da9c`.
- O head `9cfe75564686192b3c63d55ef4c9865b31aba79d` passou no `RPG Skill Tree CI` run `33244812701`, incluindo Core tests, JUnit 5, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke; os workflows Foundation/Compendium associados também fecharam GREEN.

**Acceptance: satisfied.**
