# 06.11 — MineColonies Economy — reconciliação da V1 com 1.1.1376

> **Data da reconciliação:** 2026-09-06  
> **Minecraft:** 1.21.1  
> **Loader:** NeoForge  
> **Java:** 21  
> **Branch/PR:** `feat/minecolonies-economy` / PR #415  
> **Estado funcional da V1:** IMPLEMENTADA E VALIDADA no escopo descrito abaixo; merge ainda não realizado.

## Estado atual

A implementação V1 da economia MineColonies está **PRESENTE EM CÓDIGO E VALIDADA** nesta branch. O documento histórico `11-minecolonies-economy.md` nasceu como especificação ampla de design; ele não deve ser interpretado como afirmação de que todos os itens originalmente desejados pertencem à V1.

A V1 implementada cobre:

- identidade econômica persistente por colônia;
- `ColonyEconomyState` server-authoritative;
- ledger monetário virtual canônico;
- `MINT` e `RETIRE` com conservação estrita;
- deduplicação por `transactionId` e `causalKey` persistentes;
- retenção bounded de identidades de replay e fail-closed ao atingir o limite;
- emissão/retirada usando inteiros exatos (`long`), sem arredondamento monetário silencioso;
- capacidade econômica `Q` derivada somente de sinais MineColonies auditados/read-only;
- oferta monetária `M`, equilíbrio, índice de preços, inflação/deflação e convergência bounded;
- binding estável entre economia e identidade MineColonies;
- arquivamento do estado econômico quando a colônia deixa de existir;
- scheduler bounded;
- adapter MineColonies read-only para capacidade e permissões;
- snapshots e preflight estritamente read-only;
- intents de emissão/retirada validados no servidor;
- persistência em `SavedData` global no Overworld com schema estrito/fail-closed;
- GameTests provider-free e provider-present para o contrato implementado;
- validação explícita das invariantes fail-closed de `EconomyParameters` e `EconomyPreflight`.

## Reconciliação de versão

### Fonte originalmente auditada

A auditoria técnica profunda usada para escolher hooks/APIs foi feita contra:

- MineColonies `1.1.1375-1.21.1-snapshot`;
- upstream `ldtteam/minecolonies`, branch `version/1.21`;
- commit auditado `a8022f703d80be3a0931f0d6cc34b229563ef713`.

### Runtime atualmente instalado no modpack

A modlist e o registro atual do Notion foram reconciliados em 2026-09-06 e apontam:

- `minecolonies-1.1.1376-1.21.1-snapshot.jar`;
- versão runtime `1.1.1376-1.21.1-snapshot`.

O contrato de versão do adapter aceita explicitamente `1.1.1375-1.21.1-snapshot` e `1.1.1376-1.21.1-snapshot`; qualquer versão fora da allowlist continua **FAIL-CLOSED**.

### Evidência de compatibilidade 1375 → 1376

Foi comparado o snapshot upstream auditado com o estado mais recente disponível da branch `version/1.21`. O delta observado após `a8022f703d80be3a0931f0d6cc34b229563ef713` ficou concentrado em integrações/recursos que não alteram os contratos MineColonies consumidos pela economia V1, incluindo alterações em JourneyMap, blueprints/NBT e recursos/manuais. Não foi observada alteração pertinente nas APIs de colônia, buildings, permissões ou work orders usadas pelo adapter V1.

Isso sustenta a allowlist de compatibilidade para 1.1.1376, mas **não substitui prova binária do JAR exato instalado**.

### Limitação de evidência da build 1.1.1376

O SHA upstream exato correspondente ao binário `1.1.1376-1.21.1-snapshot.jar` não foi demonstrado nesta execução. Também não foi resolvido, apenas com as fontes internas/GitHub autorizadas, o identificador CurseMaven exato do artefato 1.1.1376.

Por isso:

- o workflow provider-present validado continua instalando a build MineColonies 1.1.1375 previamente auditada;
- não se afirma que o binário 1.1.1376 foi executado pelo CI desta PR;
- a compatibilidade 1.1.1376 é sustentada por allowlist + inspeção de delta upstream, não por execução binária do JAR atual;
- a validação provider-present contra o JAR 1.1.1376 exato permanece uma pendência de evidência, não uma licença para alterar hooks silenciosamente.

## Boundary e authority

MineColonies continua autoridade de:

- identidade da colônia;
- owner/officers e permissões;
- cidadãos;
- jobs;
- buildings e níveis;
- construção/upgrade;
- requests de materiais;
- Warehouse/Couriers/logística;
- research;
- work orders e demais estados nativos.

RPG Skill Tree é autoridade apenas da camada econômica criada pela integração:

- ledger monetário virtual;
- oferta emitida/retirada e oferta efetiva;
- política de emissão/retirada V1;
- capacidade econômica derivada;
- índice de preços e métricas macroeconômicas;
- snapshots/preflight próprios.

Nenhum código V1 escreve diretamente nos internals de MineColonies para fabricar citizens, buildings, work orders, recursos ou progressão.

## Pipelines deliberadamente FAIL-CLOSED / fora da V1

### 1. Cobrança/refund de construção e upgrade

**FAIL-CLOSED.** A auditoria de 1.1.1375 não encontrou um hook público e transacional seguro imediatamente antes da ação irreversível de construção/upgrade que permita adicionar custo monetário sem risco de cobrança tardia, material parcialmente consumido ou refund duplicado.

Consequência: a V1 não cobra moeda em construções/upgrades MineColonies. Intents dessa natureza permanecem `UNSUPPORTED_KIND`/fail-closed; não existe cobrança pós-fato.

### 2. UI custom dentro do Town Hall

**FAIL-CLOSED.** Não foi identificado registry/extensibility point público estável para adicionar a aba econômica planejada ao Town Hall sem mixin/override de UI frágil.

Consequência: a integração não injeta UI no Town Hall apenas para satisfazer o mockup de design. Snapshot e preflight existem como contratos read-only e podem alimentar uma UI futura quando houver superfície segura.

### 3. Moeda física / Create: Numismatics

**FORA DA V1 E AUSENTE DA MODLIST ATUAL.** A modlist/Notion atuais não contêm Create: Numismatics. Portanto a V1 não depende dele, não cria bridge automática e não mantém item físico como segunda autoridade monetária.

### 4. Salários, wallets de cidadãos e consumo

**FORA DA V1.** Não inferir economia individual de cidadãos a partir do saldo macroeconômico da colônia.

### 5. Impostos completos, comércio intercolônia e câmbio

**FORA DA V1.** O schema e a identidade econômica foram desenhados para não bloquear expansão futura, mas não existe runtime de FX/trade/tax settlement completo nesta entrega.

### 6. Banco/Tesouro físico custom

**FORA DA V1.** Não foi criado building custom MineColonies nesta entrega. Qualquer Banco futuro deverá respeitar registry, research, schematics/style packs, BlockUI e authority do provider auditados na versão então instalada.

## Invariantes de segurança preservados

1. Cliente nunca escolhe UUID monetário autoritativo.
2. Toda mutation monetária passa pelo ledger canônico.
3. Replay do mesmo evento não cria nova moeda.
4. `M_effective = issued - retired` conserva exatamente segundo o estado persistido.
5. Restore inválido/oversized falha fechado.
6. Preflight/snapshot não consome nem produz recursos.
7. Falta de provider, binding inválido ou versão não permitida desativa a parcela dependente.
8. A economia não substitui custo material, construção, logística ou IA nativos.
9. Capacidade econômica é derivada read-only; não é autorização para mutar MineColonies.
10. Ausência de Create: Numismatics não muda a identidade da V1 para bônus genérico ou item monetário fictício.
11. Configuração econômica inválida é rejeitada pelas invariantes de `EconomyParameters`.
12. `EconomyPreflight` rejeita supply/capacidade/preços inválidos e qualquer projeção incoerente com o estado-fonte.

## Validações executadas

### Execução Sonar/coverage de validação da PR

Run temporário de validação isolada da PR #415:

- workflow run: `34035354295`;
- HEAD analisado: `6a099695a6348adb36a05e2080930a5ae7ea2512`;
- provider-free GameTests: **SUCCESS**;
- Battle Mage provider GameTests: **SUCCESS**;
- instalação do stack auditado MineColonies 1.1.1375: **SUCCESS**;
- MineColonies provider GameTests: **SUCCESS**;
- análise SonarQube da PR #415 com `sonar.pullrequest.key=415`: **SUCCESS**;
- Quality Gate: **SUCCESS**;
- consulta de métricas da PR: **SUCCESS**.

Evidência Sonar preservada no artifact `sonar-pr-415-validation-6a099695a6348adb36a05e2080930a5ae7ea2512`:

```text
new_coverage=80.93406593406593
new_lines_to_cover=1242
new_uncovered_lines=194
new_conditions_to_cover=578
new_uncovered_conditions=153
```

O gate de cobertura de código novo, antes bloqueado em 79,2%, passou sem reduzir o threshold e sem excluir produção. A correção veio de testes adicionais das invariantes fail-closed de `EconomyParameters` e `EconomyPreflight`.

### Estado de CI

A validação acima prova o contrato funcional e o Quality Gate do HEAD indicado. Ela **não é ainda a evidência final pós-sync**: a branch deve ser sincronizada com a `main` corrente e a matriz aplicável deve ser reexecutada antes do merge.

Portanto, neste ponto documental:

- V1 funcional implementada: **SIM**;
- provider-free validado: **SIM**;
- provider MineColonies 1.1.1375 validado: **SIM**;
- Sonar Quality Gate da PR validado: **SIM**;
- provider exato MineColonies 1.1.1376 validado: **NÃO — pendência de evidência**;
- sync final com `main`: **AINDA NÃO REGISTRADO NESTA REVISÃO**;
- merge: **NÃO**.

## Pendência externa registrada

Sem abrir navegador externo/TinyFish nesta execução, não foi possível demonstrar o identificador de distribuição do JAR exato `minecolonies-1.1.1376-1.21.1-snapshot.jar` para substituir a lane provider-present 1.1.1375 no workflow. Uma auditoria que tenha acesso autorizado à origem exata do artefato deve:

1. resolver o artefato 1.1.1376 exato;
2. executar a matriz provider-present contra ele;
3. comparar os contratos consumidos pelo adapter;
4. manter fail-closed se qualquer hook relevante tiver mudado.

Essa pendência é de **prova de versão**, não de redesign da V1.

## Critério de fechamento restante

Antes de mergear a PR #415 ainda é obrigatório:

1. reconciliar a documentação canônica do Stage 06.11 com este estado real;
2. sincronizar `feat/minecolonies-economy` com a `main` corrente;
3. preservar as mudanças permanentes de coverage/provider MineColonies ao resolver conflitos de CI;
4. remover workflows exclusivamente temporários de diagnóstico/validação;
5. executar/reconfirmar os checks finais aplicáveis no HEAD sincronizado;
6. mergear somente com PR mergeável e evidência verde;
7. confirmar o SHA da `main` pós-merge.
