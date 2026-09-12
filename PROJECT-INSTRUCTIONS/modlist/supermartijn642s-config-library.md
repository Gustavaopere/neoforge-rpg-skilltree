# SuperMartijn642's Config Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81eea97ae98be16d6d14
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`, mod id `supermartijn642configlib`, runtime `1.1.8`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, SuperMartijn642's Config Library 1.1.8 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** SuperMartijn642's Config Library
- **Arquivo JAR:** `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`
- **Versão 1.21.1:** 1.1.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca de configuração para outros mods: define/persiste/recarrega valores via ModConfigBuilder, separa client/server/common, suporta categorias/comentários/ranges e sincroniza SERVER/COMMON com clientes por padrão.
- **Dependências:** Biblioteca sem gameplay autônomo. A build física 1.1.8 NeoForge mc1.21 é oficialmente compatível com a linha Minecraft 1.21, incluindo 1.21.1. Consumidores causais específicos ainda não foram exaustivamente resolvidos neste lote.
- **Sobreposição:** Infraestrutura de config, não substitui mods funcionais.
- **Compatibilidade/Riscos:** Riscos: hidden dependencies, API/version drift, sync server-client stale, restart semantics e config inválida. Não remover por 'redundância' com outras config libs: consumidores compilam contra APIs específicas. Mapear consumidores antes de decisão.
- **Observações:** mod id `supermartijn642configlib`; runtime 1.1.8. Decisão Sem decisão preservada porque consumidores individuais não foram resolvidos causalmente neste lote. COMMON/SERVER sync por default pode ser desativado por consumidor com `dontSync()`.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth oficial `1.1.8-neoforge-mc1.21`, compatível com Minecraft 1.21–1.21.8. Dossiê de 08/09 preservado; consumers causais específicos seguem não exaustivamente resolvidos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/supermartijn642s-config-lib ; https://modrinth.com/mod/supermartijn642s-config-lib/version/1.1.8-neoforge-mc1.21
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — SuperMartijn642's Config Library 1.1.8 permanece exatamente instalada; ModConfigBuilder, reload/restart, CLIENT/SERVER/COMMON sync, lifecycle, ownership, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`, mod id `supermartijn642configlib`, versão `1.1.8`. SuperMartijn642's Config Lib é **infraestrutura de configuração para outros mods**, sem gameplay autônomo: declara, persiste, recarrega e sincroniza valores de config.

## 1. Identidade, versão e decisão
- **Mod:** SuperMartijn642's Config Library.
- **JAR físico:** `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`.
- **Mod id:** `supermartijn642configlib`.
- **Versão:** `1.1.8`.
- **Loader/jogo:** NeoForge; build `mc1.21` oficialmente compatível com a linha Minecraft 1.21, incluindo 1.21.1.
- **Ambiente:** Client & Server / uso conforme consumidor.
- **Decisão:** Sem decisão; preservada porque consumidores causais ainda não foram exaustivamente resolvidos neste lote.

## 2. Papel arquitetural
A biblioteca permite que um mod defina sua configuração uma vez e delegue ao Config Lib:
- criação/persistência do arquivo;
- reload dos valores;
- separação de escopos client/server/common;
- sincronização de valores apropriados com clientes;
- validação/limites de tipos numéricos;
- categorias e comentários.

O conteúdo de cada config continua pertencendo ao **mod consumidor**. A biblioteca é framework, não authority semântica do valor.

## 3. ModConfigBuilder
A API publicada usa `ModConfigBuilder` como superfície principal.
Fluxo geral:
1. criar builder para o tipo de config;
2. adicionar comentários/categorias;
3. definir valores;
4. guardar os `Supplier`s retornados;
5. finalizar com `build()`;
6. ler os valores pelos suppliers enquanto a biblioteca cuida de reload/sync.

Esse lifecycle evita que cada mod implemente parsing/persistência/sincronização do zero.

## 4. Tipos de valores
A documentação exemplifica definição de:
- boolean;
- integer;
- double;
- enum.

Para integers/doubles, a API suporta limites mínimo/máximo. A validação deve rejeitar ou normalizar valores inválidos conforme a implementação, sem deixar config corrompida silenciosamente.

A versão 1.1.8 da linha da biblioteca também inclui correção histórica para doubles em TOML aceitarem integers quando semanticamente válidos; para a build MC1.21 o changelog publicado a apresenta como release inicial dessa linha, portanto não atribuir esse fix como mudança nova específica de 1.21 sem source pin adicional.

## 5. Comments e categorias
Superfícies publicadas:
- `comment(...)` para documentar entry;
- `push(String)` / `pop()` para hierarquia de categorias;
- `categoryComment(String)` para documentar categoria ativa.

Esses metadados são parte da legibilidade/maintenance da config e não devem ser apagados por tooling externo que reescreva TOML sem preservar estrutura.

## 6. Reload semantics
Por padrão, a documentação afirma que valores são **recarregados entre world loads**.

Um consumidor pode marcar valor com `gameRestart()` antes da definição para exigir que ele só seja reavaliado após reinicialização do Minecraft.

Operacionalmente, isso cria duas classes:
- valores hot/reloadable no ciclo normal da biblioteca;
- valores cuja aplicação efetiva exige restart.

Não assumir que editar arquivo em runtime atualiza qualquer opção; isso depende da declaração do consumidor.

## 7. Client / Server / Common configs
A biblioteca suporta valores definidos para contextos distintos:
- **CLIENT:** preference/state local do cliente;
- **SERVER:** regra de servidor;
- **COMMON:** configuração compartilhada/conceitualmente comum conforme consumidor.

A classificação exata de cada option pertence ao mod que registra a config. Config Lib apenas implementa o mecanismo.

## 8. Sincronização server→client
A documentação publica que valores em configs **COMMON ou SERVER são sincronizados com clientes por padrão**.

O consumidor pode chamar `dontSync()` para impedir sync de um valor específico.

Consequências:
- cliente não deve usar valor local stale quando o servidor enviou regra sincronizada;
- reconnect deve reconstruir state sincronizado;
- disconnect deve limpar/retornar ao contexto local de forma segura;
- ausência de entries sincronizáveis não deve causar incompatibilidade de conexão por si só.

## 9. Authority e ownership
- **Mod consumidor:** authority do nome, default, range, significado e efeito de cada config.
- **Config Lib:** authority do framework de storage/reload/sync registrado via sua API.
- **Servidor:** authority dos SERVER/COMMON values sincronizados durante sessão quando o consumidor assim os registra.
- **Cliente:** authority apenas de CLIENT values e apresentação local permitida.

Não diagnosticar “Config Lib mudou gameplay” sem identificar primeiro qual consumidor registrou o valor que mudou.

## 10. Persistência e lifecycle
Validar para qualquer consumidor crítico:
- primeiro boot gera config válida;
- editar valor offline e reiniciar;
- world load/unload;
- singleplayer world A → menu → world B;
- dedicated server restart;
- cliente conecta/desconecta de servidores com valores distintos;
- common/server sync substitui valor local apenas durante contexto correto;
- `gameRestart()` não aplica prematuramente;
- config inválida/out-of-range produz comportamento controlado.

## 11. Multiplayer
O risco principal é **divergência de configuração**:
- servidor e clientes precisam concordar nos valores sincronizados;
- cliente não pode sobrescrever SERVER state por arquivo local;
- troca de servidor não pode reter valor do servidor anterior;
- protocolo/version mismatch da biblioteca/consumidor pode impedir handshake ou gerar state incorreto.

A biblioteca deve ser tratada como infraestrutura de networking quando consumidores usam sync, mesmo sem gameplay próprio.

## 12. Consumidores no pack
A modlist física confirma a biblioteca, mas **este lote não executou uma resolução completa de todos os JARs que declaram supermartijn642configlib como dependência**.

Portanto:
- não classificar como inútil por não ter conteúdo próprio;
- não remover até mapear consumidores reais ou testar startup sem ela;
- um consumidor pode depender da API apenas para config e falhar cedo no mod loading se a biblioteca faltar.

A decisão permanece Sem decisão, em vez de virar Dependência sem evidência causal individualizada.

## 13. Sobreposição
Não é substituto de Fzzy Config, Cloth Config, Forge/NeoForge config ou bibliotecas de UI apenas porque todas lidam com “config”. Cada consumidor compila contra uma API específica.

Ter múltiplas config libraries no pack é normal quando mods diferentes as exigem; redundância conceitual não implica removibilidade.

## 14. Riscos técnicos
1. **Hidden dependency:** consumidor não mapeado pode impedir boot se a biblioteca for removida.
2. **Version/API drift:** atualizar library sem consumidores compatíveis pode quebrar load/reflection/method calls.
3. **Sync mismatch:** SERVER/COMMON stale no cliente pode causar UI ou comportamento divergente.
4. **Restart semantics:** opção marcada `gameRestart()` pode parecer “não funcionar” até reiniciar.
5. **Config corruption:** edição manual inválida precisa de fallback/error claro.
6. **Wrong ownership diagnosis:** bug em valor de um consumidor pode ser atribuído incorretamente à biblioteca.
7. **Cross-server state:** valor sincronizado não deve vazar entre sessões.

## 15. Matriz de testes
- [ ] NeoForge 1.21.1 inicia com Config Lib 1.1.8.
- [ ] Consumidores reais são identificados antes de qualquer decisão de remoção.
- [ ] Config de consumidor gera arquivo no primeiro boot.
- [ ] Boolean/int/double/enum são carregados corretamente.
- [ ] Min/max rejeitam ou tratam valor fora do range conforme API.
- [ ] Categoria/comment permanecem legíveis após reload.
- [ ] Valor reloadable muda no lifecycle esperado.
- [ ] Valor `gameRestart()` só aplica após restart.
- [ ] SERVER/COMMON sincronizam para cliente quando registrados para sync.
- [ ] `dontSync()` preserva ausência de sincronização esperada.
- [ ] Troca entre dois servidores não retém config sincronizada antiga.
- [ ] Remoção da library em cópia de teste identifica consumidores via loader error sem tocar no mundo principal.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 16. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- Modrinth oficial: release `1.1.8-neoforge-mc1.21`, NeoForge, compatível com linha 1.21 e ambientes client/server.
- CurseForge/Modrinth oficiais: `ModConfigBuilder`, reload, `gameRestart`, `dontSync`, categorias/comments, sync de COMMON/SERVER e `build()`.

## 17. Revalidação física — 11/09/2026
A modlist física continua contendo exatamente `supermartijn642configlib-1.1.8-neoforge-mc1.21.jar`, mod id `supermartijn642configlib`, runtime `1.1.8`. A release oficial `1.1.8-neoforge-mc1.21` continua compatível com Minecraft 1.21–1.21.8 e NeoForge.

Nenhum consumer causal adicional foi inferido sem evidência. Sync COMMON/SERVER, `dontSync()`, `gameRestart()` e lifecycle continuam documentados como contratos da biblioteca; nenhum teste runtime foi executado.
