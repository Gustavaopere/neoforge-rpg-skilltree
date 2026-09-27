# Rhino

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#482**: JAR `rhino-2101.2.8-build.91.jar`, mod id `rhino`, runtime `2101.2.8-build.91`, SHA-1 `56eab1d464ed0811c64040c64bd047df5cbc3aad`. KubeJS físico atual é `2101.7.2-build.377`; o corpo histórico do Notion preserva build.374.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Rhino
- **Arquivo JAR:** `rhino-2101.2.8-build.91.jar`
- **Versão 1.21.1:** 2101.2.8-build.91
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Compat
- **Função:** Engine/runtime JavaScript baseado em Mozilla Rhino, usado pelo KubeJS para executar scripts ES6 sem depender do Nashorn removido do Java moderno.
- **Dependências:** Consumer causal confirmado: KubeJS 2101.7.2-build.374 declara Rhino como required content. KubeJS Create 2101.3.1-build.18 e Iron's Spellbooks KubeJS 4.0.3 ampliam a superfície de consumo.
- **Sobreposição:** Não substitui GroovyModLoader nem Kotlin for Forge; são runtimes de linguagens diferentes.
- **Compatibilidade/Riscos:** Build Beta oficial, mas dependência concreta do KubeJS físico. Riscos: runtime/API drift, context/global leak após reload, side leakage, addon binding drift e falha em cascata de scripts/consumers. Não remover enquanto KubeJS permanecer.
- **Observações:** Correção da ficha antiga: KubeJS NÃO está removido na modlist atual; `kubejs-neoforge-2101.7.2-build.374.jar` está presente e confirma Rhino build.91 como required content.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação oficial Rhino build.91 + dossiê KubeJS build.374 já auditado, que confirma a dependência requerida.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/rhino
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Rhino 2101.2.8-build.91/JAR físico reconfirmado; build.91 continua sendo a build Beta multiversão mais recente localizada e permanece load-bearing por KubeJS 2101.7.2-build.374. A autoridade do pack continua sendo o JAR físico 1.21.1, sem downgrade automático para releases antigas.
- **Histórico da decisão:** 2026-09-10 — Sem decisão → Dependência. KubeJS 2101.7.2-build.374 está fisicamente presente e declara Rhino como required content; remoção isolada deixa de ser opção segura.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `rhino-2101.2.8-build.91.jar`, mod id `rhino`, versão `2101.2.8-build.91`. Rhino é a engine JavaScript usada pelo KubeJS atual do pack. A modlist física também contém `kubejs-neoforge-2101.7.2-build.374.jar`, cuja publicação declara Rhino como required content. Portanto, nesta instância, Rhino é **dependência concreta e load-bearing**, não uma biblioteca sem consumidor confirmado.

## 1. Identidade e papel
- **Mod:** Rhino.
- **JAR:** `rhino-2101.2.8-build.91.jar`.
- **Mod id:** `rhino`.
- **Versão instalada:** `2101.2.8-build.91`.
- **Loader/jogo:** linha 1.21, usada no runtime NeoForge 1.21.1 do pack.
- **Canal upstream atual:** Beta.
- **Ambiente publicado:** Client & Server.
- **Papel:** engine/runtime JavaScript para mods, principalmente KubeJS.

## 2. O que Rhino fornece
O projeto é um fork modificado da biblioteca Mozilla Rhino. Ele fornece uma engine JavaScript com suporte ES6 para execução em mods e não depende do antigo Nashorn embutido no Java, removido a partir do Java 15.

No pack Java 21, isso torna Rhino a camada de linguagem usada pelo KubeJS em vez de depender de uma engine JavaScript do próprio JDK.

## 3. Dependência causal confirmada
A modlist física atual contém:
- `kubejs-neoforge-2101.7.2-build.374.jar`;
- `kubejs-create-neoforge-2101.3.1-build.18.jar`;
- `irons_spells_js-4.0.3.jar` e outros consumers do ecossistema KubeJS.

A ficha canônica do KubeJS confirma que a build 374 declara **Rhino + Better Advanced Tooltips como required content**. Consequência: enquanto KubeJS permanecer instalado, Rhino não deve ser removido ou versionado isoladamente.

## 4. Autoridade e ownership
- **Rhino:** parsing/execution/runtime JavaScript e bridge de linguagem exposta aos consumers.
- **KubeJS:** lifecycle de scripts, eventos, recipes, registries e bindings Minecraft/mods.
- **Mods providers:** continuam authority de seus próprios sistemas; scripts apenas invocam APIs expostas.

Rhino não deve ser tratado como owner de recipe, item, spell, world state ou evento Minecraft só porque executa o código JavaScript que os manipula.

## 5. Relação com Java 21
A justificativa técnica do Rhino moderno é independente do Nashorn. Isso é especialmente relevante no runtime do pack, Java 21: scripts KubeJS não devem depender de uma engine JavaScript removida do JDK.

Problemas de script precisam ser triados entre três camadas distintas: sintaxe/runtime Rhino, bindings KubeJS e API do mod consumidor.

## 6. Canal Beta e versionamento
A build física `2101.2.8-build.91` está publicada como Beta. Isso representa maturidade upstream, não dúvida sobre identidade ou presença.

O catálogo não fará downgrade automático para uma build Release mais antiga: a versão física é authority e deve ser validada contra o KubeJS 7.2 instalado.

## 7. Client / Server
Rhino é usado em ambos os ambientes conforme o consumer. O fato de a engine existir no cliente não autoriza scripts client-side a criar state funcional de servidor.

Mutação de mundo/player/recipes autoritativos continua sob o lifecycle server-side do KubeJS e dos providers correspondentes.

## 8. Lifecycle com KubeJS
Rhino participa de:
- inicialização da engine durante bootstrap do consumer;
- avaliação de scripts de startup/server/client conforme o KubeJS;
- reload de server scripts quando suportado pelo KubeJS;
- encerramento/recriação de contextos após restart.

Um reload não deve deixar contextos, globals ou handlers antigos ativos em paralelo com a nova avaliação.

## 9. Erros e observabilidade
Falhas podem aparecer como syntax/runtime exception, método Java inexistente, binding incompatível ou exceção do provider chamado. O log precisa ser lido preservando essa distinção.

Uma exception em JavaScript não implica automaticamente bug no mod provider; da mesma forma, um `NoSuchMethodError` pode indicar addon/KubeJS/Rhino API drift em vez de erro de script.

## 10. Integrações concretas no pack
- **KubeJS 2101.7.2-build.374:** consumer obrigatório confirmado.
- **KubeJS Create 2101.3.1-build.18:** extensão do ecossistema de scripting.
- **Iron's Spellbooks KubeJS 4.0.3:** outro consumer do stack KubeJS.

Esses mods ampliam a superfície de regressão de qualquer alteração em Rhino/KubeJS.

## 11. Sobreposição
Rhino não substitui GroovyModLoader nem Kotlin for Forge:
- Rhino/KubeJS atende JavaScript;
- GroovyModLoader atende Groovy;
- Kotlin for Forge fornece runtime Kotlin para mods compilados nessa linguagem.

São runtimes/linguagens diferentes e podem coexistir.

## 12. Riscos técnicos
1. **Runtime API drift:** KubeJS compilado/testado contra outra revisão de Rhino.
2. **Script context leak:** contexts/globals antigos sobrevivem a reload/restart.
3. **Side leakage:** script/binding client-only acessado no dedicated server.
4. **Addon drift:** consumer KubeJS chama API removida ou alterada.
5. **Error attribution:** exception Rhino confundida com bug do provider.
6. **Beta migration:** mudança interna entre builds altera edge cases de parsing/interoperabilidade.
7. **Mass failure:** quebra do runtime afeta vários addons/scripts ao mesmo tempo.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Rhino build.91 + KubeJS build.374.
- [ ] Cliente inicia e conecta sem linkage/classloading error.
- [ ] Scripts `startup_scripts` são avaliados uma única vez por startup.
- [ ] Scripts `server_scripts` executam e `/reload` permanece idempotente.
- [ ] Scripts `client_scripts` não vazam mutações funcionais para o servidor.
- [ ] KubeJS Create resolve seus bindings sem erro de runtime.
- [ ] Iron's Spellbooks KubeJS resolve bindings sem `NoSuchMethodError`/missing class relacionado à engine.
- [ ] Duas recargas consecutivas não duplicam listeners ou globals funcionais.
- [ ] Restart completo não reutiliza contextos JavaScript stale.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física canônica de 10/09/2026: Rhino build.91, KubeJS build.374 e consumers KubeJS presentes.
- Publicação oficial Rhino: fork de Mozilla Rhino, suporte ES6, independência de Nashorn e papel como library usada por KubeJS.
- Ficha canônica KubeJS já auditada: Rhino é required content da build instalada.
- **Limite:** a API interna exata da build.91 não foi inventariada classe a classe; o dossiê documenta o contrato operacional confirmado, não símbolos não pinados.
