# Cupboard mod

## Propriedades do registro

- **Mod:** Cupboard mod
- **Arquivo JAR:** `cupboard-1.21.1-4.1.jar`
- **Versão 1.21.1:** `4.1`
- **Categoria:** Biblioteca, QoL
- **Função:** Biblioteca/utilitário compartilhado que fornece frameworks e helpers para mods consumidores, incluindo configuração JSON e rotas de diagnóstico/proteção usadas pelo ecossistema do autor.
- **Dependências:** Library Client & Server; sua necessidade é consumer-driven. Não remover enquanto consumidores instalados a exigirem. Nenhuma dependência externa obrigatória adicional foi inferida para a build 4.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos em parse/persistência de configs JSON, logging de erros, chunk-load diagnostics e proteções de state inválido. A 4.1 altera apenas formatação de line breaks para legibilidade e declara compatibilidade com configs antigos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cupboard
- **Procedência:** snapshot física de 11/09/2026 — 595 entradas totais incluindo o modloader + runtime `cupboard` 4.1 + CurseForge oficial revalidado em 12/09/2026; `cupboard-1.21.1-4.1.jar` de 27/08/2026 continua a release NeoForge 1.21.1 mais recente.
- **Observações:** mod id `cupboard`; runtime 4.1. Changelog 4.1: line breaks mais legíveis em config, sem afetar configs antigos. A documentação da library inclui JSON config framework, stacktraces completos, logging de command/sync chunk-load errors e proteções contra rotações inválidas de entidades.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — registro histórico do lote físico #212 na snapshot então vigente; posição física atual #210: cupboard-1.21.1-4.1.jar / runtime 4.1 reconfirmados como latest Release NeoForge 1.21.1; config/logging/protection helpers e o delta 4.1 de legibilidade de line breaks permanecem atuais.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cupboard 4.1 foi reconfirmado como `Instalado` na modlist física de 595; o dossiê Alex já aplicado foi preservado e necessidade consumer-driven não foi convertida em decisão curatorial.
- **Sobreposição:** Biblioteca técnica específica; pode coexistir com outras config/logging/core libraries. Similaridade de utilidades não autoriza substituir Cupboard sem portar seus consumidores.

# Dossiê operacional — padrão Alex's Mobs
> 🗄️ Versão física confirmada: `cupboard-1.21.1-4.1.jar`, mod id `cupboard`, runtime `4.1`, NeoForge 1.21.1. Cupboard é uma **library/utilitário compartilhado**, não um sistema jogável autônomo.
## 1. Papel e authority
Cupboard fornece frameworks/helpers reutilizados por mods consumidores. O consumer permanece authority de seus configs, comandos, chunks, entidades e gameplay final.
## 2. JSON config framework
A documentação do projeto expõe infraestrutura para configurações JSON. O schema/defaults/semântica pertencem a cada consumer.
Parse/save deve preservar valores válidos e reportar erro de forma diagnosticável; não preencher silenciosamente config inválida com comportamento inventado.
## 3. Release 4.1 — legibilidade
O changelog oficial da build física registra uma mudança deliberadamente pequena: **line breaks mais legíveis para config**, sem afetar configs antigos.
Regression gate: um config produzido por versão anterior deve continuar carregando e manter os mesmos valores/semântica depois de atualizar para 4.1.
## 4. Stacktraces e diagnóstico
Cupboard possui utilities para preservar/exibir stacktraces completos em superfícies de erro. Logging detalhado é observabilidade; não deve ser confundido com correção automática da causa.
Ao reportar erro de consumer, preservar stack trace original e identificar o mod chamador antes de atribuir culpa à library.
## 5. Command/sync chunk-load diagnostics
A documentação existente do projeto registra rotas para logar erros relacionados a command/sync chunk loading. Em packs grandes, isso é útil para separar falha de consumer/world state de simples lag.
Logging não autoriza forçar chunk loading indefinidamente nem criar tickets permanentes por conta própria.
## 6. Proteções de entidade
Cupboard inclui proteções/utilities contra estados de rotação inválidos de entidades. Essa camada é defensiva; AI/movement/physics final continuam no entity provider/servidor.
Não usar a presença da proteção para mascarar continuamente um producer que grava valores inválidos sem investigar a origem.
## 7. Consumer-driven necessity
A library só deve ser removida depois de mapear consumers. Outra library de config/logging não é substituto binário automático.
Atualização isolada precisa de smoke-test do conjunto real que importa Cupboard.
## 8. Client/server
Cupboard é Client & Server. Config/common diagnostics podem operar em ambos; qualquer screen/feedback visual de consumers permanece client-side.
Dedicated server não deve carregar UI apenas para usar config/logging common.
## 9. Lifecycle
Validar config load/save, startup de consumers, world join, command execution, chunk load/unload, entity load e server restart.
Caches/config objects não devem duplicar registration nem manter state obsoleto depois de reload/restart quando a API espera reconstrução.
## 10. Version drift
Sintomas possíveis:
- consumer não carregar;
- config não parsear;
- formatting change ser interpretado como conteúdo diferente;
- log/stacktrace truncado;
- entity state inválido persistir;
- chunk-load helper divergir do contract esperado.
## 11. Riscos
1. Remover com consumer ativo.
2. Atualizar e quebrar contract de consumer.
3. Config antigo ser reescrito incorretamente.
4. Logging excessivo ocultar o primeiro erro causal.
5. Helper de chunk loading ser usado como ticket permanente indevido.
6. Sanitização de rotação esconder bug upstream recorrente.
7. Client-only code entrar em dedicated server.
## 12. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join sem linkage errors.
3. Abrir/carregar configs antigos após 4.1.
4. Salvar config e verificar apenas mudança de legibilidade, não de valor.
5. Erro controlado em consumer produzindo stacktrace completo.
6. Command/chunk-load diagnostic em mundo de teste.
7. Entidade com state inválido em cenário controlado sem crash/corrupção.
8. Restart e reload sem duplicated handlers/configs.
## 13. Evidência
- modlist física atual: Cupboard 4.1;
- CurseForge oficial: frameworks/utilities, Client & Server;
- release 4.1/File ID 8749806: line breaks de config mais legíveis e compatibilidade com configs antigos;
- documentação da entrada/projeto: JSON configs, stacktrace/error diagnostics, chunk-load logging e entity protections.
> 🔧 Boundary canônico: Cupboard fornece **infraestrutura defensiva/configuração**; comportamento final permanece nos consumers.
