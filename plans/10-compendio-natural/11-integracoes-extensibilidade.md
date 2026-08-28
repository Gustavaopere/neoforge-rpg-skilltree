# 10.11 — Integrações, adapters e extensibilidade

## Objetivo

Permitir que o Compêndio enriqueça conteúdo de mods opcionais e converse com quests/RPG sem transformar o projeto em um emaranhado de hard dependencies.

## Regra central

O catálogo base deve funcionar somente com Minecraft + NeoForge + o próprio mod. Toda integração externa é opcional, isolada e **fail-soft**.

## Arquitetura prevista

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/integration/
  CompendiumIntegration.java
  CompendiumIntegrationRegistry.java
  rpg/
  ftbquests/
  kubejs/
  exposure/
  jade/
  dynamic_trees/
  tfc/
  modspecific/
```

Cada adapter deve declarar:

- mod id alvo;
- requisito de versão/API quando necessário;
- capabilities fornecidas;
- comportamento quando ausente;
- testes de presença/ausência.

## Plano

### A — Integração com o próprio RPG

Criar contratos para:

- conceder XP/recompensa de descoberta via API canônica;
- mostrar level/rarity/affixes efetivos de uma instância sem confundir com stat base;
- usar gates/perks para desbloquear seções apenas quando isso for uma decisão de design explícita;
- fornecer condições de quest do Stage 08 sem acoplar o Compêndio diretamente a implementações internas;
- expor eventos públicos `entry discovered`, `entry studied`, `category completed`.

Não permitir que uma UI client-only conceda progressão.

### B — FTB Quests

Se FTB Quests estiver presente e a API da versão alvo permitir:

- [ ] condition/task para descobrir entrada;
- [ ] condition/task para completar categoria;
- [ ] reward/hook para estudar criatura/estrutura;
- [ ] IDs do Compêndio como configuração data-driven;
- [ ] diagnostics claros para ID inexistente;
- [ ] ausência de FTB Quests não carrega nenhuma classe do mod.

Se a API não for estável o suficiente, oferecer primeiro hooks genéricos do Stage 08 e deixar FTB Quests para adapter dedicado posterior.

### C — KubeJS

Expor API pequena e versionada, se KubeJS estiver presente:

- consultar entry por ID;
- adicionar categoria/alias/relação;
- fornecer/override editorial controlado;
- registrar discovery criterion permitido;
- consultar progresso de jogador no servidor;
- disparar recompensa somente através do runtime server-authoritative.

Proibir mutação arbitrária do catálogo depois da publicação do snapshot fora de reload.

### D — Exposure/fotografia

Se Exposure estiver presente:

- [ ] reconhecer fotografia válida de entidade/entrada por API/evento confiável;
- [ ] validar resultado no servidor;
- [ ] usar fotografia como gatilho configurável `SEEN`/`STUDIED`;
- [ ] não parsear screenshot/imagem por heurística frágil;
- [ ] funcionar normalmente sem Exposure.

### E — Jade/WTHIT/overlays

Integração opcional pode fornecer atalho contextual:

- `Abrir no Compêndio` para entidade/bloco identificável;
- status de descoberta;
- informação resumida, sem duplicar toda a página.

Não copiar overlays de terceiros nem fazer o Compêndio depender deles.

### F — Dynamic Trees e TFC

Adapters especializados seguem o 10.06:

- Dynamic Trees fornece mapeamento de família/espécie quando API pública permitir;
- TFC fornece clima, flora/agro/fauna especializada apenas por contrato estável;
- versões não suportadas devem degradar para catálogo genérico.

### G — Mods com dados zoológicos/biológicos próprios

Criar adapter somente quando houver ganho real que o provider genérico não consegue obter.

Processo obrigatório:

1. conteúdo aparece como `AUTO` primeiro;
2. relatório identifica facts ausentes;
3. confirmar API/eventos públicos;
4. criar adapter mínimo;
5. adicionar teste presença/ausência;
6. promover cobertura para `ADAPTER`/`CURATED`.

Não criar adapter nominal apenas para exibir nome/logo do mod.

### H — Datapacks/resource packs de terceiros

Documentar schema público para:

- categorias;
- aliases;
- textos editoriais;
- relações;
- discovery policies;
- visibility policies;
- overrides com prioridade explícita.

Um pack inválido deve rejeitar o reload do Compêndio ou apenas o fragmento de forma determinística, sem deixar estado meio-publicado.

### I — API pública

Arquivos previstos:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumApi.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumEvents.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumProviderRegistration.java
```

A API deve ser pequena; internals de UI/cache/save não são API pública.

## Matriz de compatibilidade mínima

Testar em jobs/perfis separados quando viável:

- base sem integrações;
- + TFC;
- + Dynamic Trees;
- + FTB Quests;
- + KubeJS;
- + Exposure;
- combinação representativa do modpack completo;
- remoção de cada integração de um save existente.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/IntegrationPresenceMatrixTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/CompendiumApiContractTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/RpgCompendiumBridgeTest.java
```

- [ ] nenhuma referência de classe opcional é resolvida quando mod ausente;
- [ ] adapter falhando é diagnosticado sem corromper catálogo base;
- [ ] eventos de descoberta são idempotentes;
- [ ] scripts não burlam autoridade server-side;
- [ ] reload de datapack/KubeJS produz snapshot atômico.

## Acceptance

O subplano fecha quando as integrações necessárias ao pack estiverem isoladas, testadas em presença/ausência e a API pública permitir extensão sem expor internals frágeis.
