# Kotlin for Forge

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8116bbbcf716e1f870e4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — outer JAR `5.12.0-all` e runtime `kotlinforforge 5.12.0` confirmados via JarJar interno
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Kotlin for Forge
- **Arquivo JAR:** `kotlinforforge-5.12.0-all.jar`
- **Versão 1.21.1:** 5.12.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Language loader/runtime Kotlin para Forge/NeoForge; fornece provider `kotlinforforge`, stdlib/reflection, serialization, coroutines e utilitários para mods Kotlin consumidores.
- **Dependências:** NeoForge 1.21.1. O artefato `-all.jar` embarca KFF 5.12.0 + Kotlin 2.4.0 + kotlinx coroutines/serialization 1.11.0; consumers dependem do language provider.
- **Sobreposição:** Não substitui GroovyModLoader nem outro language provider; cada loader atende seu ecossistema. Bibliotecas Kotlin internas são componentes embarcados, não entradas top-level.
- **Compatibilidade/Riscos:** Infraestrutura de bootstrap com fan-out alto. Riscos: provider não descoberto, ABI/linkage Kotlin, coroutine fora da server thread, serialization drift e JarJar skew. Issues upstream #160/#162 relatam problemas em 5.12.0/1.21.1, inclusive ambiente Create 6.0.10; não são conflito causal comprovado.
- **Observações:** Outer JAR físico não expõe metadata na linha top-level, porém `/META-INF/jarjar/thedarkcolour.kffmod-5.12.0.jar` confirma mod id `kotlinforforge`, nome `Kotlin For Forge` e runtime 5.12.0. Campo de versão corrigido com essa evidência.
- **Procedência:** modlist.txt física atual + metadata JarJar interna do artefato `kotlinforforge-5.12.0-all.jar` + CurseForge file ID 8335665 + repositório/changelog oficial KotlinForForge.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kotlin-for-forge/files/8335665
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — versão runtime reconciliada via kffmod JarJar; language loader, componentes Kotlin/kotlinx, lifecycle, side, risks e testes catalogados para 5.12.0.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Artefato físico: `kotlinforforge-5.12.0-all.jar`. O outer JAR não expõe mod metadata na linha principal da modlist, mas contém `META-INF/jarjar/thedarkcolour.kffmod-5.12.0.jar`, que confirma mod id `kotlinforforge`, nome `Kotlin For Forge` e versão `5.12.0`.

## 1. Identidade e version pin
CurseForge file ID 8335665 confirma `kotlinforforge-5.12.0-all.jar`, release 5.12.0 de 28/06/2026, compatível com Minecraft 1.21.1 e NeoForge. A modlist física é mais informativa que o outer manifest: a identidade runtime está no componente JarJar `kffmod` 5.12.0.

## 2. Papel no modpack
Kotlin for Forge é um **language loader/runtime**. Sua responsabilidade é permitir que mods escritos em Kotlin sejam carregados no ecossistema Forge/NeoForge e fornecer bibliotecas Kotlin necessárias em runtime. Ele não adiciona conteúdo de gameplay por si.

## 3. Authority / ownership
KFF é authority do language-provider Kotlin e dos bridges/utilitários que oferece ao loader. O mod Kotlin consumidor continua authority de seus próprios registries, eventos, configs e state. Não duplicar lifecycle ou state do consumer dentro de KFF.

## 4. Componentes embarcados confirmados no JAR físico
O `-all.jar` contém, como JarJar/artefatos internos e portanto **não top-level**:
- `thedarkcolour.kfflib-5.12.0.jar`;
- `thedarkcolour.kfflang-5.12.0.jar`;
- `thedarkcolour.kffmod-5.12.0.jar`;
- Kotlin stdlib `2.4.0`, incluindo jdk7/jdk8;
- Kotlin reflection `2.4.0`;
- kotlinx coroutines core/jdk8 `1.11.0`;
- kotlinx serialization core/json `1.11.0`.
Esses componentes pertencem ao host KFF e não devem virar entradas independentes da modlist.

## 5. Contratos de linguagem documentados
O projeto oficial descreve `KotlinLanguageLoader` para permitir object declarations como alvos `@Mod`, `AutoKotlinEventBusSubscriber` para object declarations em `@EventBusSubscriber`, além de utilitários/constants e das bibliotecas stdlib/reflection/serialization/coroutines. A documentação atual do projeto mantém `modLoader="kotlinforforge"` como identificador do language provider; integração de código deve sempre conferir a branch/linha compatível antes de copiar exemplos de 6.x para 5.12.0.

## 6. Lifecycle e classloading
KFF participa do bootstrap de mods **antes** do gameplay: se o language provider não for descoberto ou estiver incompatível, mods Kotlin dependentes falham já no carregamento. Isso torna atualização/remoção um risco de fan-out alto. Coroutines iniciadas por consumers continuam responsabilidade desses consumers quanto a escopo, cancelamento, thread e acesso server-thread-safe ao Minecraft.

## 7. Client / server
A release 5.12.0 é **Client & Server**. O runtime Kotlin é neutro quanto a authority; o consumer deve respeitar os lados NeoForge. Coroutines, event subscribers ou reflection não tornam operações de mundo thread-safe nem autorizam mutação client-side.

## 8. Compatibilidade com NeoForge 1.21.1
O histórico 5.x mostra correções específicas para mudanças de language loader/FancyModLoader em NeoForge, inclusive correções na linha 5.9 para 1.21.1. O pack usa NeoForge 21.1.248 e KFF 5.12.0; essa combinação precisa ser validada em cold boot, não presumida apenas pelo range publicado.
Há issues upstream abertas (#160 e #162, junho/2026) relatando falha de descoberta do provider 5.12.0 e, em outro ambiente 1.21.1, falhas envolvendo Create 6.0.10. São **relatos não resolvidos, não prova de incompatibilidade causal**. Como o pack também usa Create 6.0.10, entram como risco de regressão a observar, não como conflito confirmado.

## 9. Persistência e dados
KFF não deve possuir save state de gameplay. A persistência relevante é a dos consumers Kotlin. Atualizar stdlib/serialization pode, porém, alterar ABI ou comportamento de serializers usados por consumers; formatos persistidos próprios desses mods precisam de regressão quando houver update de KFF.

## 10. Riscos técnicos
1. **Language-provider discovery failure** impede mods Kotlin de carregar.
2. **Binary/ABI drift** entre Kotlin compiler/runtime e consumer.
3. **Coroutine misuse**: mutação de Minecraft fora da server thread.
4. **Serialization drift** em consumers que persistem dados próprios.
5. **Classloading/side leakage** em consumers.
6. **Fan-out de update:** um único upgrade de KFF pode quebrar vários mods.
7. **JarJar duplication/version skew** se outro mod embarcar versões incompatíveis das mesmas libs.

## 11. Matriz de testes
- [ ] Dedicated server cold boot reconhece `kotlinforforge` 5.12.0.
- [ ] Todos os mods Kotlin consumidores carregam sem missing language provider.
- [ ] Client conecta ao servidor sem versão/handshake inconsistente.
- [ ] Não aparecem `NoSuchMethodError`, `NoClassDefFoundError` ou linkage errors Kotlin/kotlinx.
- [ ] Coroutines de consumers não mutam world state fora da server thread.
- [ ] Restart/reload preserva dados dos consumers Kotlin.
- [ ] Create 6.0.10 e seus addons carregam normalmente com KFF 5.12.0 no pack atual.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- modlist física: outer `-all.jar`, componentes JarJar e versões Kotlin/kotlinx, `kffmod` com mod id/version runtime;
- CurseForge oficial: file ID 8335665, release 5.12.0, MC 1.21.1, Forge/NeoForge, Client & Server;
- repositório/changelog oficial: função do language loader, contratos Kotlin e histórico 5.x;
- issues #160/#162: apenas sinal de risco upstream, explicitamente não tratado como incompatibilidade comprovada.
A lista exata de mods consumidores de KFF nesta instância não foi inferida do inventário; para remoção, auditar metadata individual dos JARs dependentes.
