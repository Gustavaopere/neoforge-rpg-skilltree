# Foundation Plan — Optional Integration Safety

**Goal:** permitir instalar o RPG Skill Tree sem transformar mods compatíveis em dependências duras acidentais.

- [x] Centralizar detecção de mods/capabilities.
- [x] Evitar tipos externos em assinaturas carregadas quando o mod estiver ausente.
- [x] Isolar adapters por integração.
- [x] Definir fallback neutro para ausência de cada provider.
- [x] Validar que ausência de um mod não remove funcionalidade do core sem necessidade.

## Contrato implementado

A detecção runtime dos providers opcionais compilados pelo projeto é centralizada em `runtime/compat/OptionalIntegrations`. O bootstrap comum não consulta mais `ModList` diretamente: ele usa identidades estáveis para `irons_spellbooks`, `ars_nouveau`, `epicfight`, `goety`, `malum`, `eidolon` e `identity2`, e publica uma única linha bounded com presença/versão para diagnóstico.

A lista central é mantida em paridade com as dependências `type="optional"` de `neoforge.mods.toml`. A ausência de um provider resulta em estado `absent` e simplesmente impede o registro do adapter correspondente; nenhum serviço obrigatório do core é removido por essa ausência.

Tipos/APIs externos permanecem confinados em `runtime/compat/<provider>/`. Durante a auditoria foi encontrado um caso real fora dessa fronteira: `IdentityProgressionMixin`. Ele foi movido para `runtime/compat/identity2/mixin` e recebeu um gate early-startup via `Identity2MixinPlugin` (`IMixinConfigPlugin`). O plugin não referencia tipos do Minecraft nem da API Identity2; ele verifica somente a presença do recurso `.class` do target antes da aplicação do mixin. Com Identity2 ausente, o target não é resolvido e não produz `ClassNotFoundException`.

O dedicated-server smoke core-only valida explicitamente a matriz dos sete providers como ausentes e falha se o log contiver `ClassNotFoundException` ou `NoClassDefFoundError`. Compatibilidade de **provider presente**, versões suportadas e comportamento específico continuam pertencendo aos respectivos subplanos de integração; este fechamento não inventa nem certifica ranges de versões externas.

## Evidência de verificação

- TDD RED: `Foundation Optional Integrations` run `33230185322` falhou no head inicial porque o registry central ainda não existia.
- Durante o primeiro candidato GREEN, o GameTest revelou `ClassNotFoundException` real para `net.Gabou.identity2.identity.IdentityProgression`; a causa foi corrigida estruturalmente com o gate de Mixin, não filtrada/ignorada.
- Head funcional final: `32983072878bdea36a3e3391bde4c587ff66aa16`.
- Pré-merge: `Foundation Bootstrap Contract` `33230687091` GREEN e `Foundation Optional Integrations` `33230687135` GREEN.
- Pré-merge: `RPG Skill Tree CI` `33230687113` GREEN completo, incluindo Core, JUnit 5, NeoForge GameTests, validators, build, JAR e dedicated-server smoke.
- Integração funcional: PR #113 mergeado em `main@4f48fefa15477023ce2dcb9d56c36b586a6b16ea`.
- Pós-merge: `Foundation Bootstrap Contract` `33230834923` GREEN e `Foundation Optional Integrations` `33230834955` GREEN.
- Pós-merge: `RPG Skill Tree CI` `33230834856` GREEN completo. O dedicated-server smoke registrou `Optional provider smoke: PASS`, os sete providers como `=absent`, `Classloading errors: none` e `Dedicated-server smoke test: PASS`; o JAR foi validado e publicado como artefato, e o status final do commit foi publicado como sucesso.

**Acceptance: satisfied.** O runtime core-only inicia sem `ClassNotFoundException`/`NoClassDefFoundError`, preserva funcionalidade de core e mantém cada integração opcional atrás de fronteiras explícitas de presença/classloading.