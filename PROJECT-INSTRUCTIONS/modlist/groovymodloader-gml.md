# GroovyModLoader (GML) — 6.0.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81ad9716c1348caac8fd  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** GroovyModLoader (GML)
- **Arquivo JAR:** `gml-6.0.2.jar`
- **Versão 1.21.1:** `6.0.2`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/GroovyMC/GroovyModLoader/tree/1.21.x
- **Função:** Language provider NeoForge para carregar mods escritos em Groovy, fornecendo bootstrap, runtime Groovy e acesso aos event buses.
- **Dependências:** NeoForge 1.21/1.21.1. O host incorpora CommonGroovyLibrary, GML Core/GroovyBundler, Groovy 4.0.24, OpenSesame, Jackson e módulos Groovy via JarJar.
- **Compatibilidade/Riscos:** LoaderVersion/metadata incompatível, classloader/metaclass/linkage errors, JiJ de versões concorrentes de Groovy, duplicate event registration e classloading client-only no servidor. README cita 4.0.19, mas o JAR físico incorpora 4.0.24.
- **Sobreposição:** Não substitui Kotlin for Forge, Rhino/KubeJS ou outros language providers. Cada runtime atende consumers distintos; remoção exige mapear metadata dos mods dependentes.
- **Observações:** `gml-6.0.2.jar` source-pinned pela branch 1.21.x. As 24 entradas nested entre CGL/Jankson/GML Core/GroovyBundler/Groovy/OpenSesame/Jackson são internas e não são mods top-level.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial GroovyMC/GroovyModLoader branch 1.21.x exatamente em 6.0.2 + distribuição oficial.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — GML 6.0.2 source-pinned; Groovy bootstrap, metadata/event buses, 24 nested JarJar entries, runtime Groovy 4.0.24, side/lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `gml-6.0.2.jar`, mod id `gml`, versão `6.0.2`. O branch oficial `GroovyMC/GroovyModLoader:1.21.x` possui `version.properties` com `version=6.0.2`, portanto a versão está source-pinned. O JAR físico incorpora o runtime Groovy e bibliotecas auxiliares via JarJar; essas entradas pertencem ao host e não viram mods top-level.

## 1. Identidade e função
GroovyModLoader é um **language provider do NeoForge para mods escritos em Groovy**. Ele permite que um mod declare `gml` como loader e inicialize uma classe principal Groovy em vez de depender apenas do language provider Java padrão.

## 2. Source pin e contrato de carregamento
O branch `1.21.x` declara exatamente GML 6.0.2. A documentação do mesmo branch especifica:
- dependency Maven `org.groovymc.gml:gml`;
- `gml` como loader em metadata;
- classe principal com construtor sem argumentos;
- anotação `@GMod` com mod id;
- acesso a mod bus e Forge/NeoForge event bus por propriedades expostas pelo loader;
- `BaseGMod` como suporte adicional a IDE/`@CompileStatic`.

## 3. Authority
GML é authority apenas do **bootstrap/runtime Groovy**. O mod consumer continua authority de registries, eventos, gameplay, configs e rede que implementa. Um crash durante discovery de um mod Groovy pode ser causado pelo consumer, por bytecode/Groovy incompatível ou pelo provider; stack trace e mod metadata devem definir a causa.

## 4. Runtime embarcado no JAR físico
A modlist física mostra, dentro de `gml-6.0.2.jar`, os seguintes componentes JarJar/nested principais:
- CommonGroovyLibrary `0.5.1`;
- Jankson `1.2.3`;
- `gml-core` `7.0.3`;
- GroovyBundler `2.1.7`;
- Apache Groovy `4.0.24`;
- OpenSesame core/groovy/compile `0.5.13`;
- Groovy modules `datetime`, `macro`, `xml`, `ginq`, `typecheckers`, `toml`, `templates`, `macro-library`, `contracts`, `nio`, `dateutil`, `json` — todos `4.0.24`;
- Jackson TOML/core/annotations/databind `2.18.1`.
São dependências internas do host, não páginas top-level separadas.

## 5. README versus runtime físico
O README do branch 1.21.x ainda afirma Groovy `4.0.19`, mas o JAR físico enumera módulos Groovy `4.0.24`. Para runtime, **a modlist/JAR físico prevalece**. O README é usado para API/conceito; a versão efetivamente embarcada é 4.0.24.

## 6. Groovy modules e conflito de JiJ
A documentação oficial lista módulos Groovy providos pelo GML e adverte para não JarJar/JiJ versões diferentes dos módulos que o próprio GML já fornece, pois isso pode causar conflitos. Consumers podem incluir módulos adicionais não fornecidos, mas não devem sombrear arbitrariamente as versões já embarcadas.

## 7. Event buses e lifecycle
Mods Groovy podem registrar handlers no mod bus e no event bus durante construção/bootstrap. O lifecycle crítico inclui mod discovery, construção da classe principal, registration events, common/client/server setup e unload/shutdown do jogo. Falha de metaclass/classloader durante essa fase deve impedir carregamento limpo, não deixar registro parcial silencioso.

## 8. Client / server
A distribuição oficial é Client & Server. O provider pode carregar consumers common/client/server; cada consumer ainda precisa isolar classes exclusivas de cliente. GML não torna automaticamente seguro acessar Minecraft client em dedicated server.

## 9. Compatibilidade com outros language providers
GML não substitui Kotlin for Forge nem Rhino/KubeJS. Cada provider atende linguagem/runtime diferente. A coexistência é normal; risco surge quando um consumer empacota bibliotecas compartilhadas incompatíveis ou declara loader/version incorretos.

## 10. Atualização e ABI
Atualizar GML pode trocar Groovy/runtime/JarJar mesmo que o consumer JAR permaneça igual. Mods Groovy compilados com assumptions diferentes de AST transforms, metaprogramming ou bibliotecas podem falhar em bootstrap. Toda atualização deve ser tratada como mudança de runtime, não como biblioteca passiva.

## 11. Riscos técnicos
- remover GML enquanto algum consumer usa loader `gml`;
- metadata `loaderVersion` incompatível;
- consumer sem construtor compatível ou `@GMod` incorreto;
- duplicate/different Groovy modules via JiJ;
- conflito de Groovy/Jackson/OpenSesame classpath;
- documentação stale de Groovy 4.0.19 ser tratada como runtime apesar do JAR 4.0.24;
- event handler registrado mais de uma vez;
- classloading client-only em dedicated server;
- linkage/metaclass error após atualização de GML;
- nested jars catalogados erroneamente como mods top-level.

## 12. Matriz de testes obrigatória
- [ ] Dedicated server boot com GML 6.0.2 e todos os consumers Groovy presentes.
- [ ] Client boot e conexão sem classloader/metaclass errors.
- [ ] Enumerar consumers que declaram `gml` antes de considerar remoção.
- [ ] Verificar runtime Groovy físico 4.0.24 nos logs/classpath quando necessário.
- [ ] Consumer registra eventos uma única vez.
- [ ] Consumer client-only não carrega classes no servidor.
- [ ] Atualização/reload não duplica event listeners.
- [ ] Nenhum consumer empacota versão concorrente de módulos Groovy já fornecidos.
- [ ] Erro de consumer identifica mod causal e não é atribuído genericamente ao provider.

## 13. Evidências e limites
- **Source primário:** `GroovyMC/GroovyModLoader`, branch `1.21.x`, `version.properties=6.0.2`.
- **Release oficial:** `gml-6.0.2.jar`, NeoForge 1.21/1.21.1.
- **Modlist física:** confirma todo o nesting JarJar e Groovy 4.0.24 efetivamente embarcado.
- **Limite:** esta ficha não infere quais mods do pack usam GML sem checar metadata individual de cada consumer.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
