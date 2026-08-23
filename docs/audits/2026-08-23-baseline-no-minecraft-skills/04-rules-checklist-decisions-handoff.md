# REGRAS PERMANENTES PARA O PROJETO

1. O alvo é Minecraft 1.21.1 + NeoForge 21.1.x. Nenhuma API ou ID de 1.21.2+ entra sem comprovação explícita de compatibilidade.
2. O servidor é a única autoridade de progressão. O cliente solicita uma ação; nunca envia saldo, rank ou resultado calculado como verdade.
3. Todo ID extensível é `ResourceLocation`. Strings livres e enums fechados não são contratos de addon.
4. Regras de gameplay vêm de uma única definição efetiva no servidor. O cliente recebe uma projeção versionada.
5. Data Attachment pertence ao estado persistente do jogador; Data Component pertence a `ItemStack`; Capability serve para consulta de comportamento/API, não para duplicar persistência.
6. Toda alteração de schema exige migration idempotente, fixture de save e teste de upgrade.
7. Formato de disco, schema semântico e protocolo de rede têm versões separadas.
8. IDs desconhecidos devem ser preservados, reconciliados ou colocados em quarentena. Nunca descartar silenciosamente.
9. Reload de dados é transacional. Ou o bundle inteiro entra, ou o bundle anterior permanece ativo.
10. Reload bem-sucedido reconcilia jogadores, effects e projeção do cliente.
11. Classe emergente é derivada de investimento. Apenas choices deliberadas e achievements sticky podem ser autoridade persistida.
12. Especializações concedidas por nó são derivadas; especializações escolhidas seguem política persistente explícita.
13. Mastery XP e pontos de compra são moedas diferentes.
14. Subárvores especializadas usam ledger próprio, salvo exceção explícita e documentada.
15. Nenhum gateway é publicado antes de existir uma fonte jogável de progresso.
16. Efeitos apontam para canonical stats; bindings determinam a API concreta do modpack.
17. Attribute/modifier inexistente é erro de validação ou optional binding declarado — nunca silêncio.
18. Attribute modifier IDs são estáveis, namespaced e idempotentes.
19. Integrações concedem progresso apenas após outcome confirmado.
20. Regras de fake player, creative, spectator, cooldown e anti-farm são centralizadas.
21. Mods opcionais não podem ter classes carregadas quando ausentes.
22. Toda integração declara versão compatível e possui teste ausente/presente.
23. Reflection ou mixin opcional exige:
    - versão confirmada;
    - fallback seguro;
    - diagnóstico;
    - teste runtime.
24. Código client-only nunca pode ser carregado em dedicated server.
25. Toda correção de bug começa com teste de regressão que falha pelo motivo correto.
26. Todo PR roda generators e exige `git diff --exit-code`.
27. Todo teste criado deve ser auto-descoberto; não depender de lista manual esquecível.
28. Não adicionar conteúdo em massa enquanto houver bloqueador de persistência, sync ou atributos.
29. Não fazer refatoração cosmética em core estável durante milestones de fundação.
30. Falha de integração deve desabilitar a integração, não conceder progresso aproximado.
31. Toda fonte externa deve registrar URL, versão/commit, licença e finalidade.
32. Documentação e changelog devem refletir exatamente a versão publicada.

---

# CHECKLIST ANTES DE MERGE

## Build e baseline

- [ ] Branch atualizada com `main`.
- [ ] Working tree limpo antes da verificação.
- [ ] Java 21.
- [ ] Gradle Wrapper usado após Fase 0.
- [ ] `./gradlew --no-daemon clean build` passa.
- [ ] JAR contém `META-INF/neoforge.mods.toml`.
- [ ] JAR contém o entrypoint principal.
- [ ] Nenhum warning novo de API incompatível com 1.21.1.

Enquanto o wrapper ainda não existir, o comando reproduzido pelo CI é:

```bash
gradle --no-daemon build
```

## Geração e dados

- [ ] Executar todos os geradores:

```bash
python3 scripts/generate-tree-skeleton.py
python3 scripts/export-passive-tree.py
python3 scripts/export-client-tree.py
python3 scripts/export-client-classes.py
python3 scripts/export-client-choices.py
python3 scripts/generate-node-effects.py
python3 scripts/generate-warlock-subtree.py
python3 scripts/generate-morph-subtrees.py
```

- [ ] Executar validadores:

```bash
python3 scripts/validate-data.py
python3 scripts/validate-client-tree.py
python3 scripts/validate-node-effects.py
python3 scripts/validate-passive-export.py
python3 scripts/verify-runtime-contract.py
```

- [ ] `git diff --check` passa.
- [ ] `git diff --exit-code` passa.
- [ ] Todos os IDs são namespaced.
- [ ] Cross-references resolvem.
- [ ] Nenhum atributo 1.21.2+ foi usado.
- [ ] Cliente e servidor usam a mesma revisão de definições.
- [ ] `runData` passa quando houver provider nativo.

## Testes

- [ ] `./gradlew test` passa após migração para JUnit.
- [ ] Até lá, `bash scripts/test-core.sh` passa.
- [ ] Teste novo foi realmente descoberto/executado.
- [ ] Testes de regressão cobrem a alteração.
- [ ] GameTests passam quando aplicável.
- [ ] `runGameTestServer` passa.
- [ ] Fixtures de save antigas continuam legíveis.

## Persistência

- [ ] Login com save da versão anterior.
- [ ] Logout/relogin.
- [ ] Morte/respawn.
- [ ] Nó removido.
- [ ] Mod opcional removido.
- [ ] Reload com jogador online.
- [ ] Nenhuma especialização externa apagada.
- [ ] Pontos não são duplicados nem perdidos.
- [ ] Migration executada duas vezes permanece idempotente.
- [ ] Falha de decode possui diagnóstico e política segura.

## Networking e sync

- [ ] Cliente não consegue comprar sem saldo/requisito.
- [ ] Cliente não envia estado autoritativo.
- [ ] Payloads respeitam limites.
- [ ] Rejeições não provocam spam de full sync.
- [ ] Catálogo e estado possuem revisão coerente.
- [ ] Cache é limpo ao desconectar.
- [ ] Dois eventos no mesmo tick são consolidados corretamente.
- [ ] Rate limiting foi considerado.

## Client/server separation

- [ ] Dedicated server inicia sem mods opcionais.
- [ ] Nenhuma classe de `runtime/client` é carregada no servidor.
- [ ] Cliente inicia e abre UI.
- [ ] Todos os nós acessíveis pelo servidor aparecem quando deveriam.
- [ ] A UI não contém regra autoritativa divergente.
- [ ] Textos novos são localizados.

## Atributos e efeitos

- [ ] Cada effect resolve para registry/API real no alvo 1.21.1.
- [ ] Compra aplica modifier uma vez.
- [ ] Upgrade atualiza corretamente.
- [ ] Respec remove.
- [ ] Relog não duplica.
- [ ] Reload não deixa modifier órfão.
- [ ] Provider ausente segue optional binding documentado.

## Integrações opcionais

- [ ] Core-only.
- [ ] Provider isolado.
- [ ] Modpack combinado relevante.
- [ ] Provider removido após save.
- [ ] Evento confirmado, não apenas intenção.
- [ ] Fake player.
- [ ] Creative.
- [ ] Spectator.
- [ ] Evento duplicado.
- [ ] Falha da API não concede mastery.
- [ ] Dependência opcional e version range declarados.

## Performance

- [ ] Nenhum full rebuild em evento que não muda efeitos.
- [ ] UI testada na escala máxima atual.
- [ ] Reload tem tempo e memória aceitáveis.
- [ ] Estruturas estáticas possuem cleanup.
- [ ] Provenance de mining não cresce sem limite.
- [ ] Polling/NBT repetitivo justificado e medido.

## Documentação

- [ ] README reflete a implementação real.
- [ ] Changelog corresponde à versão.
- [ ] ADR atualizado.
- [ ] Migration documentada.
- [ ] Fonte externa com versão/commit/licença.
- [ ] Nenhuma documentação externa inacessível é a única fonte de verdade.

---

# DECISÕES PENDENTES

1. **Passive Skill Tree ou UI própria permanente?**
   Deve ser resolvido com um vertical slice em NeoForge 1.21.1, não por preferência abstrata.
2. **Classes pagas são sticky ou todas as classes são derivadas?**
   O modelo atual mistura os dois.
3. **Quais identidades são classe, especialização, gateway badge ou achievement?**
4. **Política de reembolso para nó removido.**
   Reembolso automático exige custo histórico confiável.
5. **Especialização escolhida pode ser removida por respec?**
6. **Como pontos específicos de subárvore são concedidos?**
   Por mastery thresholds, quests, uso ou combinação.
7. **Política global de creative/fake player.**
8. **Contribuição de party, pet, summon, máquina e automação para mastery.**
9. **Datagen nativo ou Python oficial?**
   Não manter ambos como fontes independentes.
10. **Quais versões exatas de cada mod opcional formarão a matriz suportada?**
11. **API pública:** capability, service registry próprio ou ambos.
12. **Semântica de datapack override:** permitir substituição completa, composição ou apenas adição namespaced.

---

# HANDOFF PARA CHAT NORMAL

O texto abaixo é autocontido e pode ser entregue diretamente a outro ChatGPT.

---

## Contexto

O projeto é `Gustavaopere/neoforge-rpg-skilltree`, um mod para Minecraft 1.21.1 com NeoForge 21.1.248 e Java 21. A baseline auditada foi o commit:

```text
31377faa79685565b683923e9d8e2e62db073c92
```

Versão declarada:

```text
1.0.0-alpha.6-dev
```

O projeto possui um core Java majoritariamente imutável, runtime NeoForge, Data Attachment por jogador, networking autoritativo, UI própria e integrações opcionais parciais.

Não reescreva o projeto. Preserve o core puro e corrija as fundações antes de adicionar conteúdo.

## Arquivos principais

```text
src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java

src/main/java/dev/gustavopere/rpgskilltree/core/ProgressionState.java
src/main/java/dev/gustavopere/rpgskilltree/core/ProgressionStateCodec.java
src/main/java/dev/gustavopere/rpgskilltree/core/ProgressionService.java
src/main/java/dev/gustavopere/rpgskilltree/core/ArchetypeResolver.java
src/main/java/dev/gustavopere/rpgskilltree/core/InvestmentState.java
src/main/java/dev/gustavopere/rpgskilltree/core/SpecializationResolver.java
src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalStatCatalog.java
src/main/java/dev/gustavopere/rpgskilltree/core/ModifierResolver.java
src/main/java/dev/gustavopere/rpgskilltree/core/MasteryAwardService.java

src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionAttachmentSerializer.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java

src/main/java/dev/gustavopere/rpgskilltree/runtime/data/*
src/main/java/dev/gustavopere/rpgskilltree/runtime/network/*
src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/AttributeNodeEffectRuntime.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/client/RpgSkillTreeScreen.java
src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/*

src/main/resources/data/rpgskilltree/**
src/main/resources/assets/rpgskilltree/**
.github/workflows/alpha2-build.yml
scripts/test-core.sh
```

## Estado atual

Funcionam em nível de core/build:

- XP;
- pontos passivos;
- compra/respec;
- mastery persistido;
- boss first-credit;
- discoveries;
- choices;
- attachment;
- codec v1–v4;
- payloads server-authoritative;
- dedicated server core;
- geradores e validadores.

Parcial:

- classes emergentes;
- especializações;
- canonical stats;
- gateways;
- árvore semântica;
- progressão por uso;
- integrações opcionais;
- UI de subárvores.

Ausente:

- Passive Skill Tree real;
- Create/AE2/Oritech;
- Curios runtime;
- GameTests;
- JUnit integrado ao Gradle;
- definition bundle transacional;
- catálogo servidor→cliente;
- moedas específicas;
- matriz de providers.

## Bugs prioritários

### 1. Specialization data loss

`ProgressionService.reconcileNodeSpecializations` reconstrói de vazio e apaga especializações externas. Preserve as especializações que não são concedidas por nós.

### 2. Unknown node reconciliation

Um nó aprendido sem definição não pode ser removido porque o respec normal exige todas as definições. Crie caminho administrativo separado e teste uninstall/rename.

### 3. IDs de atributos errados

Há 34 ocorrências de IDs de 1.21.2:

```text
minecraft:armor
minecraft:attack_damage
minecraft:attack_speed
minecraft:knockback_resistance
minecraft:luck
minecraft:max_health
minecraft:movement_speed
```

No Minecraft 1.21.1 use os IDs com `generic.`, por exemplo:

```text
minecraft:generic.armor
minecraft:generic.attack_damage
minecraft:generic.attack_speed
minecraft:generic.knockback_resistance
minecraft:generic.luck
minecraft:generic.max_health
minecraft:generic.movement_speed
```

### 4. Cliente e servidor divergem

Servidor tem 578 nós e cliente 568. O cliente ignora requisitos mais novos. Não mantenha regras de gameplay em `assets`; projete o catálogo efetivo do servidor.

### 5. PR #5 vermelho

O PR #5 tenta corrigir fundações, mas:

- o teste ainda espera apagar especialização externa;
- geradores deixam muitos arquivos stale;
- CI usa `git diff --check`, não `git diff --exit-code`.

Não mesclar sem resolver.

## Arquitetura a implementar

Crie um único:

```text
ProgressionDefinitionBundle
```

Ele deve conter nós, grafo, requisitos, efeitos, canonical stats, classes, arquétipos, especializações, mastery lanes, moedas, gateways e projeção de cliente.

Reload:

```text
decode → normalize → cross-validate → compile → atomic commit
```

Se falhar, mantenha o bundle anterior.

Todos os IDs extensíveis devem ser `ResourceLocation`.

Persistir fatos, não classes deriváveis. Derivar classe primária/secundária/híbrida dos investimentos nos nós.

Separar:

- mastery XP;
- pontos gerais;
- pontos específicos por subárvore.

## NeoForge 1.21.1

Respeite estes padrões:

- Java 21.
- Data Attachment para estado persistente do jogador.
- Data Component para estado de ItemStack.
- Capability apenas para API/query de comportamento entre mods.
- `RegisterPayloadHandlersEvent`/`PayloadRegistrar` para rede.
- servidor revalida toda ação;
- `RegisterGameTestsEvent` ou anotações de GameTest;
- `GatherDataEvent` se migrar para datagen nativo;
- dependências opcionais declaradas como opcionais no mod metadata;
- nunca copiar APIs/atributos de 1.21.2+.

## Ordem das próximas tarefas

1. Adicionar Gradle Wrapper e `git diff --exit-code`.
2. Criar testes de regressão dos dois bugs de reconciliação.
3. Corrigir preservação de especializações.
4. Corrigir reconciliação de nós órfãos.
5. Separar versão de formato, schema e protocolo.
6. Implementar migrations idempotentes e fixtures.
7. Criar bundle transacional.
8. Migrar IDs para `ResourceLocation`.
9. Criar projeção servidor→cliente.
10. Corrigir atributos 1.21.1.
11. Ligar canonical stats ao runtime.
12. Otimizar reaplicação de effects e sync.
13. Decidir modelo de classes/especializações/moedas.
14. Implementar modelo emergente.
15. Decidir Passive Skill Tree versus UI própria.
16. Provar um vertical slice.
17. Criar SPI de integrações.
18. Testar Iron’s/Ars/Epic isoladamente.
19. Harden Goety/Malum/Eidolon/Identity.
20. Implementar Create e demais providers.
21. Industrializar JUnit, GameTests e matrizes.
22. Somente então expandir conteúdo.

## Comandos atuais

```bash
python3 scripts/generate-tree-skeleton.py
python3 scripts/export-passive-tree.py
python3 scripts/export-client-tree.py
python3 scripts/export-client-classes.py
python3 scripts/export-client-choices.py
python3 scripts/generate-node-effects.py
python3 scripts/generate-warlock-subtree.py
python3 scripts/generate-morph-subtrees.py

bash scripts/test-core.sh

python3 scripts/validate-data.py
python3 scripts/validate-client-tree.py
python3 scripts/validate-node-effects.py
python3 scripts/validate-passive-export.py
python3 scripts/verify-runtime-contract.py

git diff --check
git diff --exit-code

gradle --no-daemon build
gradle --no-daemon runServer
```

Depois de adicionar o wrapper:

```bash
./gradlew --no-daemon clean build
./gradlew test
./gradlew runGameTestServer
./gradlew runServer
./gradlew runData
```

## Regras que não podem ser quebradas

- servidor é autoridade;
- IDs extensíveis são namespaced;
- não apagar dados desconhecidos;
- toda migration é idempotente e testada;
- gameplay não depende do JSON local do cliente;
- reload é atômico;
- mastery e pontos são moedas diferentes;
- integração só premia outcome confirmado;
- provider opcional nunca quebra core-only;
- nenhum atributo/API de versão posterior;
- bug fix começa por teste;
- geradores devem deixar tree limpo;
- nenhum novo bloco grande de conteúdo antes das fundações.

## Principais riscos

- perda de especializações;
- falha de login por nó removido;
- atributos silenciosamente inativos;
- PR de fundação inconsistente;
- cliente exibindo regras erradas;
- saves sem recovery;
- integrations concedendo mastery por intenção;
- mixin do Identity quebrando por versão;
- mineração crescendo save indefinidamente;
- full sync/rebuild em hot paths;
- gateways impossíveis;
- testes não descobertos;
- CI construindo conteúdo gerado diferente do commit.

---

# FERRAMENTAS UTILIZADAS

- **GitHub plugin/MCP:** inspeção do repositório, branches, commits, arquivos, PRs, issues, workflows, jobs e logs. Também foi usado para conferir o código-fonte oficial do NeoForge no branch `1.21.1`.
- **DeepWiki:** mapa inicial da arquitetura e localização de subsistemas. As afirmações foram verificadas no código real porque o índice secundário superestimou alguns componentes.
- **Context7:** tentativa de confirmar documentação NeoForge. Uma consulta retornou conteúdo de 1.21.10 dentro do contexto pretendido de 1.21.1; esse resultado foi rejeitado para decisões sensíveis à versão.
- **Documentação oficial NeoForge 1.21.1:** Data Attachments, Data Components, networking, mod metadata, resources/datagen e GameTests.
- **Fontes oficiais da Mojang:** confirmação de que a remoção dos prefixos de atributos ocorreu em 1.21.2.
- **Inspeção local read-only:** clone temporário, `rg`, inventário, contagens, análise estática, execução dos geradores e validadores.
- **GitHub Actions do próprio projeto:** evidência de build Java 21/Gradle 8.14, JAR e dedicated-server smoke no commit auditado.
- **Metodologias Superpowers:** brainstorming arquitetural, investigação por causa-raiz, planejamento por dependências, TDD e verificação antes de conclusões.

As skills citadas no pedido — `minecraft-modding`, `minecraft-mod-dev`, `minecraft-testing` e `minecraft-ci-release` — não estavam instaladas nem disponíveis nesta sessão. Portanto, não foram listadas falsamente como utilizadas; as decisões de NeoForge 1.21.1 foram confirmadas diretamente nas fontes oficiais e no código do projeto.
