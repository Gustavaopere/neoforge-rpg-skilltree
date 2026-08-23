# Auditoria Consolidada — A × B

Data da consolidação: **23/08/2026**

## Fontes históricas

- **Auditoria A:** snapshot `31377faa79685565b683923e9d8e2e62db073c92`, produzida sem as Minecraft Skills especializadas.
- **Auditoria B:** snapshot `87a8ef224af52e1a613bce892a5f3e6732691466`, produzida com `minecraft-modding`, `minecraft-mod-dev`, `minecraft-testing`, `minecraft-ci-release`, Superpowers, GitHub, DeepWiki e fontes NeoForge 1.21.1.

O snapshot B está **66 commits à frente** do snapshot A. Esse intervalo inclui o merge da fundação do sistema, loaders modernos de archetypes/specializations/tree unlocks, a migração semântica de Industrialist/Logistician/Prospector e a camada de morph ecology/Identity 2. As diferenças de contagem e alguns bloqueadores da A refletem evolução real do código, não necessariamente desacordo entre auditorias.

Depois do snapshot B, o `main` recebeu somente documentação de auditoria. Portanto, para código/runtime, o snapshot B continua representando o estado atual durante esta consolidação.

---

# Veredito consolidado

O projeto **não deve ser reescrito**. A fundação aproveitável é clara e foi confirmada por ambas as auditorias:

- core Java majoritariamente puro e imutável;
- servidor como autoridade;
- Data Attachment como persistência do jogador;
- C2S baseado em intenção/IDs;
- integrações separadas por `compat` e `compileOnly`;
- build e dedicated-server smoke verdes;
- geração/validação estrutural já existente.

Ao mesmo tempo, o projeto **não está pronto para expansão massiva de conteúdo**. As duas auditorias convergem nos mesmos eixos fundacionais: save/reconcile, fonte única de regras, atributos canônicos, sync/performance, dedupe, testes reais e definição final da progressão emergente.

A Auditoria B acrescentou dois defeitos P0 específicos de Minecraft 1.21.1 que foram verificados diretamente no `main` e devem ser tratados imediatamente: caminho incorreto da tag de bosses e IDs vanilla de atributos de versão posterior.

---

# Verificação direta no `main`

Os seguintes pontos foram rechecados após receber as duas auditorias.

## CONFIRMADO — tag de bosses está no diretório plural incorreto

Existe atualmente:

```text
src/main/resources/data/rpgskilltree/tags/entity_types/bosses.json
```

Para a linha 1.21 o registry/tag folder correspondente deve ser singular (`entity_type`). O arquivo atual também lista entidades `cataclysm:*` como valores obrigatórios, apesar de Cataclysm não estar declarado como dependência obrigatória.

**Status:** aberto, P0.

## CONFIRMADO — 34 ocorrências de atributos vanilla com IDs posteriores

`src/main/resources/data/rpgskilltree/node_effects/main.json` contém **34 ocorrências** entre:

```text
minecraft:armor
minecraft:attack_damage
minecraft:attack_speed
minecraft:knockback_resistance
minecraft:luck
minecraft:max_health
minecraft:movement_speed
```

Outras subárvores já usam formas `minecraft:generic.*`, confirmando inconsistência interna. No alvo 1.21.1, os efeitos principais precisam usar os IDs válidos dessa versão.

**Status:** aberto, P0.

## CONFIRMADO — efeito com target inexistente é ignorado silenciosamente

`AttributeNodeEffectRuntime.refresh(...)` faz lookup no `BuiltInRegistries.ATTRIBUTE` e executa `continue` quando o holder ou a instance não existem. Assim, uma compra pode consumir pontos e não produzir efeito nem erro acionável.

**Status:** aberto, P0/P1.

## CONFIRMADO — cliente carrega regras de gameplay pelo classpath

Há `getResourceAsStream` em:

- `ClientClassCatalog`;
- `ClientChoiceCatalog`;
- `ClientTreeLayout`.

Isso mantém regras/representações locais fora do lifecycle de datapack/reload do servidor e confirma o risco de UI divergente.

**Status:** aberto, P0/P1.

## CONFIRMADO — reconcile de nó removido continua em dead-end

`ProgressionService.reconcileInvalidNodes(...)` considera inválido um nó cuja definição não existe e, para removê-lo, chama `respecNode(...)`. O respec normal depende das definições existentes. Logo, remoção/rename de nó ainda pode quebrar a reconciliação.

**Status:** aberto, P0.

## PARCIALMENTE CORRIGIDO — preservação de especializações

A Auditoria A apontou que `reconcileNodeSpecializations` reconstruía tudo do zero e apagava especializações externas. Entre os snapshots, o merge da fundação passou a preservar explicitamente as três IDs vindas da migração semântica:

- `industrialist`;
- `logistician`;
- `prospector`.

Isso resolve a migração conhecida, mas **não resolve proveniência genérica**. O estado ainda não distingue de forma persistida especializações concedidas por nó, mastery, escolha ou integração externa.

**Status:** parcialmente corrigido; remover como P0 genérico, manter provenance como trabalho de schema.

## OBSOLETO — “PR #5 não integrável”

Esse bloqueador da Auditoria A era correto no snapshot A, mas deixou de ser atual: a fundação foi corrigida/ajustada e mesclada em `87a8ef2...`, com CI verde.

**Status:** histórico; não deve aparecer no plano atual como blocker.

## CONFIRMADO — full attribute refresh + full owner sync em mutações frequentes

`PlayerProgressionRuntime.set(...)` sempre:

1. escreve o attachment;
2. chama `AttributeNodeEffectRuntime.refresh(...)`;
3. chama `ModNetworking.syncToOwner(...)`.

`applyXp(...)` e `awardMastery(...)` terminam em `set(...)`. Portanto XP/mastery frequentes recompõem atributos e sincronizam o estado completo mesmo quando ranks/efeitos não mudaram.

**Status:** aberto, P1 de performance/arquitetura.

## CONFIRMADO — ProcGuard/sourceId não formam dedupe de runtime

`ProcGuard` aparece no core/testes, mas não está conectado aos adapters de runtime. Os adapters atuais criam `ActionOrigin(..., 0)` repetidamente. Não há fingerprint/dedupe compartilhado entre eventos vanilla/provider/mixin.

**Status:** aberto, P1.

## CONFIRMADO — CI não detecta drift dos geradores

O workflow atual executa:

```bash
git diff --check
```

mas não `git diff --exit-code`. O primeiro verifica whitespace, não se os geradores alteraram o conteúdo commitado.

**Status:** aberto, P1 de CI.

## CONFIRMADO — Gradle Wrapper ausente

Não existem `gradlew` nem `gradle/wrapper/gradle-wrapper.properties` no repositório atual.

**Status:** aberto, baseline/reprodutibilidade.

## CONFIRMADO — testes runtime ainda insuficientes

Há testes Java próprios e validadores, mas não JUnit integrado ao Gradle nem GameTests cobrindo attachment, reload, atributos, networking, respawn e providers reais.

**Status:** aberto.

## CONFIRMADO — providers opcionais não estão declarados no metadata

`neoforge.mods.toml` declara somente NeoForge e Minecraft. As integrações compile-only não possuem blocos opcionais com faixa de versão no metadata.

**Status:** aberto; corrigir junto ao Provider SPI/matriz suportada.

## CONFIRMADO — keybind usa evento bruto, não `consumeClick`

`ClientKeyMappings` compara diretamente `InputEvent.Key` com o valor da tecla e abre a UI em `GLFW_PRESS`. O padrão de `KeyMapping.consumeClick()` no client tick é mais robusto e deve ser adotado quando a camada cliente for revisada.

**Status:** aberto, baixa prioridade em comparação com os P0.

---

# Convergências de alta confiança entre A e B

As duas auditorias concordam, e a inspeção atual não contradiz, que:

1. O servidor deve continuar sendo a única autoridade de progressão.
2. Data Attachment é apropriado para estado persistente do jogador; não migrar tudo para capabilities.
3. O core Java puro deve ser preservado.
4. O save precisa de evolução segura para remoção/rename/custo histórico e política de recovery.
5. As definições devem convergir para um snapshot/bundle único, cross-validado e publicado atomicamente.
6. Cliente e servidor não podem manter regras autoritativas independentes.
7. Canonical stats precisam controlar o runtime real, não apenas existir como abstração de core.
8. Efeitos/provider ausentes não podem virar silent no-op comprável.
9. XP/mastery frequentes não devem provocar rebuild total de atributos e full sync.
10. O sistema precisa de ação semântica + dedupe/anti-farm central.
11. Mastery e pontos de compra devem permanecer conceitos separados.
12. Classes emergentes devem ser derivadas de investimento, preservando builds híbridos.
13. Especializações precisam de proveniência clara antes de regras genéricas de reconcile.
14. UI/Passive Skill Tree deve ser decidido por vertical slice, não por reescrita antecipada.
15. Integrações opcionais precisam ser testadas presentes e ausentes.
16. GameTests/JUnit/runtime tests são necessários; validadores estruturais não bastam.
17. Geradores precisam de gate determinístico (`git diff --exit-code`).
18. Conteúdo em massa deve esperar as fundações.

---

# Diferenças explicadas pela evolução do código

## Classes e especializações

A Auditoria A observou mais classes/archetypes. Entre os snapshots, Industrialist, Logistician e Prospector foram removidos de `classes/` e migrados semanticamente para especializações; alguns archetypes legados também foram removidos. A Auditoria B portanto descreve um estado posterior e deve ser usada para a taxonomia atual.

## Specialization reconcile

A crítica ampla da A foi parcialmente endereçada no merge. A consolidação mantém apenas o problema ainda real: falta de provenance genérica.

## Morph/Identity

A B auditou código posterior com Identity 2 compile-only, ecologia data-driven e hostility memory. Para o estado atual, a B supersede a A nessa área, mantendo os riscos de mixin/API drift e a decisão sobre persistência da hostilidade.

## PR de fundação

A observação da A sobre PR vermelho é histórica. O merge posterior passou e não é blocker atual.

---

# Achados exclusivos relevantes da Auditoria B

Estes pontos não apareceram com a mesma precisão na A e foram aceitos após verificação:

- tag `entity_types` → `entity_type`;
- entradas Cataclysm precisam ser opcionais ou a dependência precisa ser declarada;
- key mapping deve migrar para o lifecycle normal de `KeyMapping`/`consumeClick`;
- necessidade explícita de `ProgressionState v5` com `paidCost`, currency, provenance e rules version;
- `ProgressionRulesSnapshot` como nome/contrato preferido do bundle atômico;
- `SemanticAction` com fingerprint como fronteira de integração;
- providers opcionais precisam de gameplay-safety, não apenas classloading-safety;
- arquivo de licença/release readiness precisa ser formalizado antes de beta.

---

# Ordem consolidada de execução

## Fase 0 — Baseline reproduzível e P0 de versão

- adicionar Gradle Wrapper 8.14;
- integrar JUnit 5 e preparar GameTests;
- CI com `./gradlew` e `git diff --exit-code`;
- corrigir tag de bosses e optional entries;
- corrigir os 34 attribute IDs para 1.21.1;
- transformar attribute target inexistente em erro/indisponibilidade explícita;
- adicionar regressões antes das correções.

## Fase 1 — Persistência e reconciliação segura

- projetar `ProgressionState v5`;
- allocation com rank, paidCost, currency, sourceTree/provenance e rulesVersion;
- migração v1–v5 idempotente;
- política de nó removido/renomeado/maxRank reduzido;
- refund baseado no custo efetivamente pago;
- provenance de especialização;
- recovery/quarantine administrativa de save inválido.

## Fase 2 — `ProgressionRulesSnapshot` e autoridade servidor→cliente

- parse e cross-validation de todos os catálogos;
- commit atômico e last-known-good;
- revision/hash;
- reconcile de jogadores online;
- client rules view sanitizada;
- remover regras gameplay-critical de `getResourceAsStream`/assets locais.

## Fase 3 — Canonical stats e runtime de efeitos

- canonical stat como única entrada de efeito;
- bindings vanilla/providers;
- stacking groups, ordem e caps;
- policy provider missing;
- modifier IDs determinísticos;
- cleanup/reload/respec sem órfãos.

## Fase 4 — Pipeline de mutação, sync, segurança e dedupe

- `ProgressionMutationService`;
- dirty reasons;
- refresh somente quando efeitos mudarem;
- sync coalescido por tick;
- rate limit C2S;
- `SemanticAction` + fingerprint + `procDepth`;
- dedupe/anti-farm compartilhado.

## Fase 5 — Progressão emergente jogável

- contributions dos nós;
- Primary + Secondary + híbridos derivados;
- eliminar gradualmente o runtime legado quando os sinais canônicos existirem;
- mastery XP e specialist points separados;
- gateways reais;
- provenance de choices/specializations/unlocks.

## Fase 6 — UI/engine

- decidir Passive Skill Tree versus engine próprio por vertical slice;
- UI lê snapshot do servidor;
- gateways, breadcrumbs, mastery, currencies, provider unavailable;
- corrigir key mapping;
- profiling antes de otimizações maiores.

## Fase 7 — Provider SPI

- optional metadata/version ranges;
- normalize events em `SemanticAction`;
- matriz core-only/provider presente;
- hardening Iron’s, Ars, Epic Fight, Goety, Malum, Eidolon, Identity;
- fail-visible para reflection/mixins críticos.

## Fase 8 — Novas integrações e conteúdo

- completar primeiro integrações já iniciadas;
- depois Create;
- depois AE2/Oritech e demais providers;
- nenhum gateway sem rota real de progresso.

## Fase 9 — Beta/release

- release workflow;
- license explícita;
- migration guide/backup;
- matriz suportada;
- profiling/playtest multiplayer;
- nenhuma falha P0/P1 aberta.

---

# Decisões ainda abertas

A consolidação não inventa respostas para estes pontos:

1. Passive Skill Tree externo ou UI própria permanente.
2. Quais providers são obrigatórios versus verdadeiramente opcionais.
3. `ProgressionDomain` fechado ou IDs namespaced extensíveis.
4. Schema exato do v5.
5. Política de nós desconhecidos/aliases/quarantine.
6. Política econômica para mudanças retroativas de datapack.
7. Proveniência e persistência de especializações escolhidas/externas.
8. Persistência da hostility memory do Morph.
9. Um JAR único ou companion integrations no futuro.
10. Semântica de “uso significativo” para Create/automação.
11. Caps globais dos canonical stats.
12. Datagen NeoForge versus Python como fonte oficial.
13. API pública futura para outros mods.

Esses pontos devem virar ADRs antes da implementação correspondente.

---

# Promoção para documentação canônica

Com esta consolidação, os seguintes documentos podem ser tratados como fonte operacional daqui para frente:

- `/AGENTS.md` — invariantes e instruções para agentes;
- `/docs/MASTER_PLAN.md` — ordem de execução consolidada;
- `/docs/TESTING.md` — estratégia/gates de teste;
- `/docs/decisions/README.md` — decisões ainda abertas.

As Auditorias A e B permanecem preservadas como evidência histórica e não devem ser apagadas.