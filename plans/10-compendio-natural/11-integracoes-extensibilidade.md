# 10.11 — Integrações, adapters e extensibilidade

## Objetivo

Permitir que o Compêndio enriqueça conteúdo de mods opcionais e converse com quests/RPG sem transformar o projeto em um emaranhado de hard dependencies.

## Regra central

O catálogo base deve funcionar somente com Minecraft + NeoForge + o próprio mod. Toda integração externa é opcional, isolada e **fail-soft**.

Além disso, **adapter não é requisito para que conteúdo modded exista no catálogo**. O pipeline genérico deve descobrir primeiro todo conteúdo suportado por registries/classes/tags/providers estáveis; adapters entram apenas para enriquecer ou corrigir fatos que não podem ser obtidos com segurança pelo contrato genérico.

### Contrato AUTO → ADAPTER/CURATED

Para qualquer mod novo ou conteúdo ainda não integrado nominalmente:

1. o conteúdo entra primeiro como `AUTO` quando o registry/provider genérico consegue identificá-lo;
2. a página técnica base permanece funcional sem adapter específico;
3. o relatório de cobertura identifica facts ausentes, ambíguos ou não resolvíveis;
4. somente então se avalia adapter nominal;
5. `ADAPTER` adiciona fatos tecnicamente comprovados por API/evento/provider estável;
6. `CURATED` adiciona texto editorial pt-BR e relações curadas com proveniência;
7. falha ou ausência do adapter degrada novamente para `AUTO` sempre que a página base ainda puder ser produzida;
8. nenhum adapter pode apagar uma entrada `AUTO` válida apenas porque o enriquecimento falhou.

Esse contrato se aplica a conteúdo vanilla e modded da mesma forma; namespace externo não é motivo para exigir cadastro manual.

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
- quais facts adiciona sobre a página `AUTO`;
- quais facts continuam desconhecidos quando o adapter não está disponível;
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
- versões não suportadas devem degradar para catálogo genérico;
- ausência do adapter especializado não deve remover a entrada base que já puder ser descoberta genericamente.

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

Exemplos de facts que justificam adapter quando não forem acessíveis genericamente:

- variantes internas não representadas por IDs/tags estáveis;
- dietas, domesticação, reprodução ou genética específicas;
- tabelas de spawn/habitat expostas somente por API própria;
- estados especiais de boss/NPC;
- famílias/espécies compostas de árvores;
- dados climáticos/agronômicos especializados;
- relações ecológicas ou loot que o provider genérico não consegue resolver com segurança.

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
- remoção de cada integração de um save existente;
- mod de conteúdo sem adapter nominal, provando que suas entradas registry-first continuam aparecendo como `AUTO`.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/IntegrationPresenceMatrixTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/CompendiumApiContractTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/integration/RpgCompendiumBridgeTest.java
```

- [ ] nenhuma referência de classe opcional é resolvida quando mod ausente;
- [ ] adapter falhando é diagnosticado sem corromper catálogo base;
- [ ] adapter ausente não remove entrada `AUTO` válida;
- [ ] mod suportado pelos registries aparece sem cadastro manual de seus IDs;
- [ ] eventos de descoberta são idempotentes;
- [ ] scripts não burlam autoridade server-side;
- [ ] reload de datapack/KubeJS produz snapshot atômico.

## Acceptance

O subplano fecha quando as integrações necessárias ao pack estiverem isoladas, testadas em presença/ausência, a API pública permitir extensão sem expor internals frágeis e estiver provado que **conteúdo modded suportado continua entrando automaticamente como `AUTO` mesmo sem adapter nominal**.
