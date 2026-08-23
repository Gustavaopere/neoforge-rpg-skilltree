# 7. PLANO MESTRE

## Fase 0 — Congelar e estabilizar a baseline

**Objetivo**

Criar uma base reproduzível antes de qualquer alteração arquitetural.

**Pré-requisitos**

Nenhum.

**Arquivos/módulos afetados**

- `gradle/wrapper/**`;
- `.github/workflows/alpha2-build.yml`;
- scripts de geração;
- testes;
- documentação/ADRs;
- PR #5 e branch `feat/a0001-a0109`.

**Implementação necessária**

- Adicionar Gradle Wrapper fixado na versão validada.
- Trocar comandos de CI para `./gradlew`.
- Adicionar `git diff --exit-code` após os geradores.
- Tornar o workflow executável em qualquer branch/PR relevante.
- Registrar ADRs sobre:
  - classe emergente;
  - especializações;
  - moedas;
  - Passive Skill Tree;
  - estado persistido versus derivado.
- Separar no PR #5:
  - correções de persistência;
  - taxonomia;
  - morph;
  - conteúdo gerado.
- Não mesclar #5 enquanto testes e geração não estiverem verdes.
- Rebasear ou reconstruir a branch de ranked/OR prerequisites após o bundle de definições.

**Testes necessários**

- Pipeline atual completo.
- Duas execuções consecutivas dos geradores.
- Working tree limpo após ambas.
- Build via wrapper.
- Dedicated server smoke.

**Critério objetivo de conclusão**

- Clone limpo executa todos os comandos sem Gradle instalado globalmente.
- Geradores não deixam diff.
- PR de fundação não contém outputs stale.
- ADRs das decisões bloqueantes aprovados.

**Riscos**

- O PR #5 mistura decisões independentes.
- Regeneração altera centenas de arquivos.
- Branches antigas podem introduzir semânticas incompatíveis.

**Dependências posteriores**

Todas as demais fases.

---

## Fase 1 — Segurança de saves e reconciliação

**Objetivo**

Eliminar perda de progressão e falhas de login/reload.

**Pré-requisitos**

Fase 0.

**Arquivos/módulos afetados**

- `core/ProgressionService.java`;
- `core/ProgressionState.java`;
- `core/ProgressionStateCodec.java`;
- `runtime/ProgressionAttachmentSerializer.java`;
- `runtime/PlayerProgressionRuntime.java`;
- testes de progressão e fixtures.

**Implementação necessária**

- Preservar especializações que não pertençam a concessões de nós.
- Criar reconciliação administrativa de nós órfãos.
- Não chamar o respec voluntário para um nó sem definição.
- Definir política de reembolso:
  - custo registrado no save;
  - tabela de migração;
  - ou quarentena sem reembolso automático.
- Adicionar versão semântica.
- Separar protocolo de rede de formato persistente.
- Fazer migrations idempotentes.
- Preservar IDs desconhecidos.
- Substituir enums persistidos extensíveis por IDs ou wrapper tolerante.
- Tornar overflow explícito.
- Substituir exceção do limite de 32 iterações por diagnóstico e estado seguro.
- Registrar falhas de decode/reconciliação com contexto administrável.

**Testes necessários**

- Fixtures v1, v2, v3 e v4.
- Round-trip determinístico.
- Save truncado/corrompido.
- Campo desconhecido.
- Enum/ID desconhecido.
- Nó removido.
- Addon instalado e depois removido.
- Especialização externa preservada.
- Migration executada duas vezes.
- Morte com `copyOnDeath`.
- Logout/relogin.
- Reload com jogador online.

**Critério objetivo de conclusão**

Nenhum save válido das versões suportadas perde progressão ou impede login. Todo ID desconhecido segue política documentada e testada.

**Riscos**

- Reembolso pode duplicar pontos.
- Uma migration incorreta pode ser irreversível.
- Reuso futuro de IDs antigos pode reativar dados órfãos.

**Dependências posteriores**

Bundle de definições, classes, integrações e lançamento público.

---

## Fase 2 — Fonte única de definições

**Objetivo**

Eliminar divergência entre catálogos e entre cliente/servidor.

**Pré-requisitos**

Fase 1.

**Arquivos/módulos afetados**

- `runtime/data/*Reloader.java`;
- `runtime/data/*Catalog.java`;
- `core/NodeAccessRequirement.java`;
- recursos em `data/rpgskilltree/**`;
- `runtime/client/Client*Catalog.java`;
- payload de catálogo;
- scripts de export.

**Implementação necessária**

- Criar `ProgressionDefinitionBundle`.
- Introduzir codecs por definição.
- Normalizar todos os IDs para `ResourceLocation`.
- Cross-validar:
  - nós;
  - arestas;
  - efeitos;
  - classes;
  - especializações;
  - choices;
  - discoveries;
  - mastery;
  - moedas;
  - gateways.
- Compilar e publicar o bundle atomicamente.
- Manter bundle anterior se reload falhar.
- Calcular hash/revisão.
- Criar projeção read-only para o cliente.
- Remover regras de acesso duplicadas de `assets`.
- Sincronizar/reconciliar jogadores online após commit.
- Fazer o cliente invalidar cache quando revisão mudar.
- Corrigir diferença 578/568.
- Integrar requisitos ranked/OR apenas sobre esse modelo comum.

**Testes necessários**

- Referência inexistente.
- Ciclo inválido.
- ID duplicado.
- Colisão entre namespaces.
- Reload parcialmente inválido.
- Cliente com catálogo antigo.
- Servidor com datapack sobrescrevendo conteúdo.
- Requisitos compostos.
- Projeção cliente equivalente ao servidor.

**Critério objetivo de conclusão**

Dada a mesma revisão do bundle, servidor e cliente calculam a mesma apresentação. O servidor permanece autoridade e nenhum arquivo de `assets` contém regras autoritativas de gameplay.

**Riscos**

- Payload de catálogo pode ficar grande.
- Datapacks existentes podem usar IDs não namespaced.
- Migração dos scripts pode gerar diffs extensos.

**Dependências posteriores**

UI, addons, classes, mastery e gateways.

---

## Fase 3 — Atributos, efeitos, sync e runtime

**Objetivo**

Garantir que efeitos sejam compatíveis com 1.21.1, verificáveis e eficientes.

**Pré-requisitos**

Fase 2.

**Arquivos/módulos afetados**

- `CanonicalStat*`;
- `ModifierResolver`;
- `NodeEffectResolver`;
- `AttributeNodeEffectRuntime`;
- JSONs de `node_effects`;
- `PlayerProgressionRuntime`;
- payloads de sync;
- eventos de login/reload/death.

**Implementação necessária**

- Corrigir as 34 ocorrências de atributos 1.21.2.
- Fazer efeitos apontarem para canonical stats.
- Resolver canonical stats para bindings ativos.
- Falhar no carregamento para atributo obrigatório inexistente.
- Permitir binding opcional somente quando explicitamente marcado.
- Reaplicar modifiers apenas quando ranks ou bundle mudarem.
- Fazer sync incremental ou consolidado por tick.
- Evitar full sync em cada award.
- Limpar cache do cliente no disconnect.
- Reconciliar effects em login, respawn, reload e mudança de modpack.
- Adicionar diagnostics para efeitos ignorados.
- Definir orçamento de packet size e frequência.

**Testes necessários**

- Cada uma das 119 entradas resolve no registry correto.
- Todos os sete atributos vanilla corrigidos em jogador real.
- Compra, upgrade e respec.
- Relog.
- Morte.
- Reload.
- Addon ausente/presente.
- Modifier duplicado.
- Dois eventos de mastery no mesmo tick.
- Cliente recebe estado consolidado.

**Critério objetivo de conclusão**

Todos os efeitos declarados são aplicados ou rejeitados com erro de validação; nenhum desaparece silenciosamente. Eventos sem mudança de rank não reconstroem todos os modifiers.

**Riscos**

- Alteração de IDs pode modificar balanceamento real.
- Bindings externos variam por versão.
- Sync incremental pode introduzir ordem incorreta se não houver revisão.

**Dependências posteriores**

Balanceamento, integrações e expansão de conteúdo.

---

## Fase 4 — Modelo definitivo de progressão

**Objetivo**

Unificar classes, híbridos, especializações, mastery e moedas.

**Pré-requisitos**

Fases 1 a 3 e ADRs da Fase 0.

**Arquivos/módulos afetados**

- `InvestmentState`;
- `NodeInvestment`;
- `ArchetypeResolver`;
- `ClassProgressionState`;
- `Specialization*`;
- `Mastery*`;
- `TreeUnlock*`;
- JSONs de classes/archetypes/specializations/tree unlocks;
- codec/migrations.

**Implementação necessária**

- Vincular nós a contribuições namespaced.
- Derivar classes primária/secundária do investimento.
- Definir empates e especificidade.
- Implementar híbridos sem lock.
- Separar:
  - labels derivados;
  - choices;
  - achievements sticky;
  - especializações concedidas.
- Criar ledgers de pontos por árvore.
- Definir gateway como combinação de:
  - discovery;
  - mastery;
  - requisito de nó;
  - custo de acesso, se aplicável.
- Usar `MasteryAward.sourceId`.
- Centralizar anti-farm.
- Tornar todas as lanes extensíveis por namespace.
- Migrar classes antigas conforme taxonomia aprovada.
- Remover ou integrar abstrações que continuarem mortas.

**Testes necessários**

- Classe primária/secundária.
- Empate.
- Híbrido.
- Respec alterando classe emergente.
- Choice persistente sobrevivendo ao respec.
- Especialização derivada removida.
- Especialização externa preservada.
- Gateways.
- Mastery sem pontos suficientes.
- Pontos específicos não confundidos com pontos gerais.
- Migrações dos estados atuais.

**Critério objetivo de conclusão**

Um único modelo explica, de forma determinística, classe, especialização, acesso e currency de qualquer jogador. Não existe segunda fonte de verdade persistida.

**Riscos**

- Mudança perceptível para saves existentes.
- Taxonomia ainda não decidida.
- Conteúdo atual assume classes hardcoded.

**Dependências posteriores**

UI final, Create, Morph e conteúdo avançado.

---

## Fase 5 — UI e decisão Passive Skill Tree

**Objetivo**

Escolher conscientemente o motor de apresentação e provar um vertical slice.

**Pré-requisitos**

Fases 2 a 4.

**Arquivos/módulos afetados**

- `runtime/client/RpgSkillTreeScreen.java`;
- `ClientTreeLayout`;
- assets de árvore;
- eventual adapter para Passive Skill Tree;
- gateway/portal de uma subárvore.

**Implementação necessária**

Primeiro, resolver a decisão:

### Opção A — Passive Skill Tree

- Auditar versão disponível para Minecraft/NeoForge 1.21.1.
- Confirmar API, licença e estabilidade.
- Implementar apenas:
  - árvore principal mínima;
  - um portal;
  - uma subárvore;
  - compra e respec;
  - sync.
- Não migrar 512 nós antes de esse slice funcionar.

### Opção B — UI própria

- Formalizar que ela é parte permanente do core.
- Adicionar culling, cache e índice espacial.
- Separar layout visual de regras.
- Garantir todos os nós server-side acessíveis pela UI.
- Localizar textos hardcoded.
- Implementar navegação de subárvores e acessibilidade básica.

**Testes necessários**

- Resoluções e GUI scales.
- Mouse/teclado.
- 500+ nós.
- Revisão de catálogo mudando.
- Cliente reconectando.
- Compra permitida/negada.
- Dedicated server sem classes client.
- Gateway para uma subárvore real.

**Critério objetivo de conclusão**

Uma árvore principal e uma subárvore formam um fluxo completo, sincronizado e jogável sem regras locais divergentes.

**Riscos**

- A API do Passive pode não ser estável/compatível.
- Migrar conteúdo cedo pode criar lock-in.
- UI própria requer manutenção de performance e acessibilidade.

**Dependências posteriores**

Expansão de árvores e polimento.

---

## Fase 6 — SPI de integrações e vertical slice mágico

**Objetivo**

Provar que integrações opcionais podem crescer sem transformar o entrypoint em monólito.

**Pré-requisitos**

Fases 2 a 5.

**Arquivos/módulos afetados**

- `core/IntegrationAdapter.java`;
- novo contrato de actions/outcomes;
- `runtime/compat/*`;
- `RpgSkillTreeMod`;
- `neoforge.mods.toml`;
- CI matrix.

**Implementação necessária**

- Definir integração por descriptor/factory.
- Isolar classloading.
- Declarar dependências opcionais e versões.
- Centralizar filtros de fake/creative/spectator.
- Centralizar anti-farm.
- Exigir outcome confirmado.
- Provar um fluxo completo de cada:
  - Iron’s;
  - Ars;
  - Epic Fight.
- Expor uma capability read-only somente se houver consumidor externo.
- Remover fallback silencioso que concede mastery.
- Fazer cada adapter registrar capabilities/events apenas quando compatível.

**Testes necessários**

Matriz:

- core only;
- core + Iron’s;
- core + Ars;
- core + Epic Fight;
- core + os três;
- provider removido de save existente;
- dedicated server;
- cliente conectado;
- ação válida e inválida;
- creative/fake player;
- evento duplicado.

**Critério objetivo de conclusão**

Adicionar ou remover qualquer um dos três providers não quebra startup, save, catálogo ou UI. Cada integração produz mastery apenas após ação confirmada.

**Riscos**

- APIs externas mudam entre versões.
- Artefatos compile-only podem não representar runtime real.
- Múltiplos providers podem disputar canonical stats.

**Dependências posteriores**

Create, Malum, Goety, Eidolon, Morph e outros sistemas RPG.

---

## Fase 7 — Integrações avançadas e progressão especializada

**Objetivo**

Completar providers restantes sobre contratos já estáveis.

**Pré-requisitos**

Fase 6.

**Arquivos/módulos afetados**

- `compat/goety`;
- `compat/malum`;
- `compat/eidolon`;
- `compat/identity2`;
- novos adapters Create/AE2/Oritech/Curios;
- mining provenance;
- subárvores especializadas.

**Implementação necessária**

- Goety: correlação por action/spell ID, não somente janela temporal.
- Malum: remover award aproximado após falha reflexiva.
- Eidolon: lifecycle explícito, limpeza de mapas e menos polling NBT.
- Identity: usar API pública e versão verificada; mixin somente como último recurso.
- Druid/Morph: categorias data-driven e permissões server-side.
- Create: primeiro vertical slice confirmado antes de liberar gateway.
- AE2/Oritech: somente após confirmar APIs 1.21.1 e eventos confiáveis.
- Curios: Data Component no item + API/capability do slot, com resize/ejection testados.
- Mining: mover provenance para escopo por chunk e tratar máquinas/movimento.
- Nunca expor subárvore cujo mastery não possa ser adquirido.

**Testes necessários**

Uma suíte dedicada por provider, incluindo:

- startup ausente/presente;
- ação confirmada;
- ação cancelada;
- reload;
- cleanup;
- fake/creative;
- uninstall;
- múltiplas ações no mesmo tick;
- automação/máquinas quando aplicável.

**Critério objetivo de conclusão**

Cada gateway publicado possui ao menos uma rota jogável, testada e documentada de progressão.

**Riscos**

- Reflection e mixins sensíveis à versão.
- Eventos de provider podem representar intenção.
- Sistemas automatizados exigem política de ownership.

**Dependências posteriores**

Conteúdo final.

---

## Fase 8 — Industrializar testes, datagen e CI

**Objetivo**

Transformar os gates acumulados em uma pipeline sustentável.

**Pré-requisitos**

As fases anteriores já devem ter criado testes de regressão locais.

**Arquivos/módulos afetados**

- `src/test`;
- eventual `src/testmod`;
- `build.gradle`;
- workflows;
- scripts;
- datagen providers.

**Implementação necessária**

- Migrar testes core para JUnit ou gerar auto-descoberta.
- Fazer `./gradlew test` executá-los.
- Adicionar GameTests.
- Adicionar `runGameTestServer`.
- Adicionar client smoke.
- Manter dedicated smoke.
- Adicionar matrizes opcionais selecionadas.
- Adicionar fixtures de save.
- Adicionar verificador dos registries efetivos.
- Decidir:
  - manter Python como generator oficial;
  - ou migrar gradualmente para `GatherDataEvent`.
- Não manter dois pipelines autoritativos.
- Criar workflow de release separado.
- Atualizar ações com warnings de runtime/depreciação.
- Publicar logs e relatórios de teste.

**Testes necessários**

A própria matriz completa, incluindo reexecução determinística.

**Critério objetivo de conclusão**

Um PR não pode ser mesclado com:

- teste omitido;
- generated data stale;
- servidor quebrado;
- regra cliente/servidor divergente;
- atributo inexistente;
- save incompatível;
- integração opcional quebrando startup.

**Riscos**

- Matriz completa pode ficar cara.
- GameTests podem ser flaky sem isolamento.
- Testes com mods externos exigem cache e licenças válidas.

**Dependências posteriores**

Release e expansão em escala.

---

## Fase 9 — Expansão de conteúdo e balanceamento

**Objetivo**

Adicionar volume somente sobre fundações comprovadas.

**Pré-requisitos**

Fases 0 a 8.

**Arquivos/módulos afetados**

- definitions bundle;
- árvores;
- traduções;
- efeitos;
- documentação;
- changelog.

**Implementação necessária**

- Expandir uma família por milestone.
- Validar todos os gateways.
- Balancear XP, mastery e custos.
- Testar builds híbridos.
- Documentar migrations.
- Criar changelog coerente com versão.
- Atualizar `SOURCES.md` com versão, commit e licença das referências.

**Testes necessários**

- progressão completa do início ao gateway;
- builds puros e híbridos;
- respec;
- reload;
- multiplayer;
- regressão de performance;
- dedicated server;
- modpack completo.

**Critério objetivo de conclusão**

Cada conteúdo novo possui rota de obtenção, efeito verificável, UI acessível, save compatível e teste automatizado.

**Riscos**

- Balanceamento sem telemetria.
- Combinações exponenciais de mods.
- Árvores enormes degradando UX.
