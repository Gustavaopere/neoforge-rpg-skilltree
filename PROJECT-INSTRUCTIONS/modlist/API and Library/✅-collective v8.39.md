# Collective

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#103**: JAR `collective-1.21.1-8.39.jar`, mod id `collective`, runtime `8.39`, SHA-1 `b1153f03c97bccaa6bc11d6199f07f16ef4318ff`.

## Propriedades do registro

- **Mod:** Collective
- **Arquivo JAR:** `collective-1.21.1-8.39.jar`
- **Versão 1.21.1:** `8.39`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/collective
- **Função:** Biblioteca compartilhada com código comum usado pelos mods do ecossistema Serilum; não adiciona um sistema jogável autônomo.
- **Dependências:** Library Client & Server. Necessidade determinada pelos mods Serilum instalados que declaram Collective; não remover nem substituir por outra API enquanto consumers ativos dependerem dela.
- **Compatibilidade/Riscos:** Shared library consumer-driven. Riscos: API/version drift, classloading client-only, duplicate callbacks e consumer attribution. Upstream 8.40 altera somente formatação/indentação de source e não publica mudança funcional.
- **Sobreposição:** Biblioteca específica de Serilum. Coexistência com outras utility/config/network libraries não implica redundância binária; consumers usam contracts próprios.
- **Observações:** Runtime físico permanece Collective 8.39. Upstream publicou **8.40** para Minecraft 1.21.1 em 21/09/2026; o changelog registra apenas normalização da indentação dos source files para tabs, sem delta funcional.
- **Procedência:** modlist física atual + CurseForge oficial Collective 8.39/8.40. A autoridade instalada permanece `collective-1.21.1-8.39.jar`.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Collective 8.39 foi reconfirmado na modlist física e reconstruído como library do ecossistema Serilum. A presença por dependência não foi convertida em decisão curatorial.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Collective físico permanece 8.39. A release 8.40 foi comparada e não contém mudança funcional publicada.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs
> ✅ Versão física confirmada: `collective-1.21.1-8.39.jar`, mod id `collective`, runtime `8.39`, Minecraft 1.21.1. Collective é a **shared library dos mods Serilum**.
## 1. Papel e authority
Collective concentra código comum reutilizado por mods Serilum. O consumer continua authority de qualquer gameplay, comando, configuração ou evento final.
A library não deve ser catalogada como feature autônoma apenas porque participa da implementação de vários mods.
## 2. Dependency graph
A necessidade é consumer-driven. Remover Collective com um consumer que a declara pode impedir bootstrap ou provocar linkage errors.
Outra biblioteca com helpers semelhantes não substitui Collective sem portar o consumer.
## 3. Release 8.39
A release física `1.21.1-8.39` atualiza `MessageFunctions` para lidar melhor com mensagens client-only. Esse é o delta oficial documentado para a build.
Não inferir mudanças de gameplay, registries ou APIs não descritas pelo changelog.
## 4. Mensagens e side boundary
Helpers de mensagens podem ser usados por consumers para feedback de chat/UI. Uma mensagem exibida no cliente não deve liquidar gameplay nem autorizar ação server-side.
Consumers precisam separar notification/presentation do state final do servidor.
## 5. Client/server
Collective é publicado como Client & Server. Common helpers podem existir em ambos os lados, mas caminhos client-only precisam permanecer protegidos contra classloading em dedicated server.
O fix 8.39 torna essa fronteira especialmente relevante para QA.
## 6. Config e utilities
Collective compartilha utilities entre muitos mods Serilum, mas cada consumer define sua própria configuração e semântica. Não há um config global do pack que deva ser tratado como authority de todos os consumers apenas por estar na library.
## 7. Lifecycle
Validar bootstrap dos consumers, world join, disconnect/reconnect, config load/reload quando aplicável e server restart. Shared callbacks/helpers não devem registrar duas vezes nem manter referência a mundo/client anterior.
## 8. Version drift
Sintomas típicos: missing method/class, erro de loader, consumer que abre mas perde feature, ou mensagem executada no side errado. Diagnóstico deve registrar Collective 8.39 e o consumer exato.
## 9. Riscos
1. Remover com consumer ativo.
2. Atualizar library fora da faixa esperada por consumer.
3. Client-only message path carregar em servidor.
4. Helper compartilhado duplicar callback após lifecycle.
5. Confundir utility da library com gameplay authority.
## 10. Matriz de testes
1. Dedicated server boot com todos consumers Serilum atuais.
2. Client join sem linkage errors.
3. Smoke-test de consumidores que emitem mensagens client-only.
4. Disconnect/reconnect sem duplicate callbacks/messages.
5. Config save/reload de consumidores selecionados.
6. Atualização futura: smoke-test conjunto de library + consumers.
## 11. Evidência
- modlist física atual: Collective 8.39;
- CurseForge oficial: shared library com common code para mods Serilum, Client & Server;
- release 1.21.1-8.39/File ID 8341460;
- changelog 8.39: melhoria de `MessageFunctions` para client-only messages.
> 📚 Boundary canônico: Collective fornece **código compartilhado**; cada mod Serilum consumidor mantém authority de sua própria feature.


## 12. Atualização upstream 8.40 — não instalada
A release **Collective 8.40** para Minecraft 1.21.1 foi publicada em 21/09/2026.

Changelog oficial: **normalização da indentação de todos os source files para tabs**.

Não há mudança funcional, API ou gameplay publicada para esta release. Portanto o dossiê mantém 8.39 como runtime físico e registra 8.40 apenas para rastreabilidade de versão.

Gate de eventual promoção: boot com consumers Serilum, linkage, mensagens client-only, reconnect e callbacks; nenhum comportamento novo é esperado pela release note.

Fonte upstream: CurseForge file ID 8940150, Collective 8.40.

## 13. Atualização upstream 8.41 — não instalada
Depois da 8.40 sem delta funcional, o upstream publicou **Collective 8.41** para Minecraft 1.21.1 em 02/10/2026.

O changelog desta release registra atualização das **informações/metadados do mod** em `mods.toml` e `fabric.mod.json`. Não foi publicada mudança de API, callback, networking, config ou gameplay associada à 8.41.

**Delta completo 8.39 → 8.41:**
- 8.40: normalização de indentação do source para tabs;
- 8.41: atualização de metadata do mod.

A autoridade física permanece **Collective 8.39**. A 8.41 é a mais recente upstream, mas não há benefício funcional documentado que justifique tratá-la como correção obrigatória; eventual promoção ainda deve passar pelo smoke-test de consumers Serilum.

Fonte upstream: CurseForge Collective 8.41, release NeoForge 1.21.1 de 02/10/2026.

## 14. Atualizações Collective 8.42 → 8.43 — 10/10/2026

**Físico:** `collective-1.21.1-8.39.jar` / 8.39. A cadeia já documentada inclui **8.40 (21/09, alteração de indentação do source)** e **8.41 (02/10, atualização de `mods.toml`/`fabric.mod.json`)**. A listagem oficial confirma releases adicionais **8.42 (04/10)** e **8.43 (10/10)** para **Minecraft 1.21.1**, com distribuição conjunta Fabric/Forge/NeoForge; **8.43 é a última da linha verificável nesta data**.

### 8.42
Publicação do artefato 1.21.1 confirmada, porém **não foi isolada a nota técnica específica da 8.42**; não presumir ausência de alterações ou atribuir automaticamente mudanças da 8.43 à 8.42.

### 8.43
O changelog publicado pelo autor para a versão 8.43 (também veiculada em builds de outras versões de Minecraft) menciona:
- adição de traduções do **Daily Quest**;
- helper **`MessageFunctions.getTranslatableMessageComponent`**;
- correção **Forge/NeoForge de packets ocasionalmente não registrados quando mods carregam simultaneamente**.

**Limite de evidência:** a versão 8.43 é confirmadamente publicada para MC 1.21.1, porém as notas acima foram consultadas no texto da série 8.43 em plataforma multicarga. **Verificar correspondência no JAR/changelog exato de `collective-1.21.1-8.43.jar` antes de garantir que todos os deltas estão presentes**. O registro de packets em carregamento paralelo representa impacto de confiabilidade de network/loader, não nova mecânica de gameplay da library.

**Cadeia integral:** 8.39 → 8.40 → 8.41 → 8.42 → 8.43. Testes: bootstrap NeoForge21.1.250, consumidores Serilum e seus packet handlers, handshake/multiplayer com load concorrente, mensagens traduzíveis, config/reload, client/dedicated server e regressão das mensagens client-only corrigidas na 8.39.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/collective/files/all?gameVersionTypeId=5&page=1 ; https://www.curseforge.com/minecraft/mc-mods/collective/files/9119660 (changelog 8.43 multiversão).

**Estado:** upstream 8.43; físico 8.39, sem testes locais.
