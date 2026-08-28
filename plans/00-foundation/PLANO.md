# PLANO — 00 Foundation

Estado: **EM ANDAMENTO / base existente**.

## Por que este estágio existe

Foundation define as garantias que todos os outros estágios assumem: bootstrap NeoForge previsível, separação client/server, configuração, logging, testes e carregamento seguro com mods opcionais ausentes.

## Resultado esperado

Uma base Minecraft 1.21.1 / NeoForge / Java 21 em que novas features possam ser adicionadas sem criar dependências circulares, classloading client-only no servidor ou contratos paralelos.

## Dependências

Nenhuma etapa funcional do RPG deve contornar esta camada.

## Etapas de implementação

### 1 — Congelar ambiente e metadados
- [ ] conferir versões canônicas de Minecraft, NeoForge, Java e Gradle;
- [ ] alinhar `gradle.properties`, metadata do mod e CI;
- [ ] documentar dependências obrigatórias versus opcionais.

### 2 — Organizar bootstrap e limites de pacote
- [ ] manter inicialização comum sem referências client-only;
- [ ] isolar registro client, telas e keybinds;
- [ ] garantir ordem determinística de registries/listeners.

### 3 — Configuração e IDs
- [ ] centralizar defaults/configs de gameplay;
- [ ] validar ranges de configuração na carga;
- [ ] formalizar convenção de `ResourceLocation` e IDs persistidos.

### 4 — Integrações opcionais
- [ ] impedir classloading de classes externas quando o mod não estiver presente;
- [ ] centralizar detecção/capabilities de adapters;
- [ ] manter fallback do core independente.

### 5 — Diagnóstico
- [ ] padronizar logging de bootstrap, reload e falhas de dados;
- [ ] mensagens devem identificar mod/arquivo/ID problemático sem spam por tick.

### 6 — Baseline de testes
- [ ] unit tests básicos;
- [ ] build NeoForge;
- [ ] dedicated-server smoke;
- [ ] validação de ausência de client classes no servidor.

## Migração e compatibilidade

Mudanças de namespace/ID exigem plano de migração antes de serem aceitas. Foundation não deve alterar saves silenciosamente.

## Critérios de aceite

- [ ] testes e build verdes;
- [ ] dedicated server inicia limpo;
- [ ] mods opcionais podem estar ausentes sem crash;
- [ ] separação client/server comprovada;
- [ ] versões e dependências correspondem ao artefato real.

## Definição de concluído

Quando todos os checks estiverem satisfeitos e integrados na `main`, renomear este arquivo para `PLANO-✅.md` e atualizar `plans/STATUS.md`.