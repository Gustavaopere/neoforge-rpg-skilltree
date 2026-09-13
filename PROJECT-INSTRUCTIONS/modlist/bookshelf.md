# Bookshelf

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a4b909c528fd5efe4f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Bookshelf
- **Arquivo JAR:** `bookshelf-neoforge-1.21.1-21.1.81.jar`
- **Versão 1.21.1:** 21.1.81
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada Darkhax com frameworks, utilidades e infraestrutura reutilizável para mods consumidores; não adiciona um sistema de gameplay próprio.
- **Dependências:** Biblioteca estrutural; necessidade determinada pelos mods que declaram Bookshelf. Não remover por coexistir com outras APIs.
- **Sobreposição:** Biblioteca técnica. Similaridade funcional com outras libraries não implica substituição binária; cada consumer determina sua necessidade.
- **Compatibilidade/Riscos:** Riscos principais são version drift com consumidores, mixins/common lifecycle e utilidades de update/config/data sendo carregadas no lado errado. 21.1.81 corrige fallback update checker bloqueando a main thread.
- **Observações:** 21.1.81 é release NeoForge 1.21.1 de 10/02/2026. Changelog oficial: correção do fallback update checker que podia bloquear a main thread. O projeto se define como coleção de código, frameworks, utilities e resources reutilizáveis.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Bookshelf 21.1.81 + source oficial Darkhax-Minecraft/Bookshelf branch 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bookshelf
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Bookshelf 21.1.81 físico/release confirmado; library/consumer contracts, lifecycle/side e fix do fallback update checker bloqueando main thread preservados. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria reconfirmou Bookshelf 21.1.81 como biblioteca/framework de infraestrutura e preservou os riscos de version drift, observabilidade e classloading. A presença física não foi convertida em decisão de manter/remover.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `bookshelf-neoforge-1.21.1-21.1.81.jar`, mod id `bookshelf`, runtime `21.1.81`, NeoForge 1.21.1. Bookshelf é uma **library**, não um provider de gameplay. A necessidade é determinada pelos consumidores que compilam/rodam contra sua API.

## 1. Papel e autoridade
Bookshelf reúne código, frameworks, utilidades e recursos compartilhados usados por muitos mods. O consumer continua authority da funcionalidade final; Bookshelf fornece infraestrutura comum.

Não atribuir a Bookshelf mobs, equipamentos, progressão ou worldgen só porque um consumer usa sua API.

## 2. Contrato de dependência
Library mods reduzem duplicação de código entre projetos, mas criam vínculo de versão/API. Regras para o pack:
- não remover enquanto houver consumer declarado;
- não substituir por outra library “equivalente” sem portar o consumer;
- atualizar junto de consumers sensíveis quando houver breaking changes;
- bootstrap deve ocorrer uma vez e em ordem compatível com NeoForge.

## 3. Frameworks e utilities
A descrição oficial é deliberadamente ampla: Bookshelf contém frameworks/utilities acumulados pelo autor para uso recorrente. Nesta auditoria não se inventa uma lista de APIs internas que não foi version-pinned integralmente.

O catálogo trata como superfícies de risco confirmadas:
- registry/bootstrap helpers usados por consumers;
- data/config/utilities compartilhadas;
- networking/event helpers quando um consumer os usa;
- update-check infrastructure;
- mixins declarados pela build NeoForge.

Detalhes de classe/método permanecem consumer-specific até inspeção source-level do caso concreto.

## 4. Release 21.1.81
O changelog oficial da 21.1.81 registra correção de um **fallback update checker que podia bloquear a main thread**.

Isso é importante operacionalmente porque:
- update checking não pode travar tick/render thread;
- falha de rede deve ser não-fatal;
- jogo offline deve iniciar normalmente;
- qualquer consumer que exponha update status deve tolerar ausência/timeout.

## 5. Update checking
Mesmo quando a library fornece infraestrutura de update check, resultado remoto é informativo. Não deve:
- impedir world load;
- bloquear dedicated server startup;
- alterar gameplay state;
- baixar/executar conteúdo automaticamente por inferência.

A 21.1.81 deve ser validada especificamente contra regressão de blocking.

## 6. Client/server
Bookshelf é infraestrutura comum. Algumas utilities podem ser common, outras client-only. Regras:
- consumer deve respeitar dist/side da API usada;
- classes de UI/render não podem vazar para dedicated server;
- state de gameplay do consumer permanece server-authoritative;
- cache client-side não substitui dado sincronizado do consumer.

## 7. Lifecycle
Pontos críticos:
1. mod construction/bootstrap;
2. registry setup;
3. common/client setup;
4. server start/stop;
5. datapack/resource reload quando utilities do consumer participam;
6. config load/reload;
7. update check em online/offline/timeout;
8. world join/leave.

Library state não deve sobreviver entre mundos/servers quando o consumer espera contexto isolado.

## 8. Version drift
O principal risco de uma library madura é consumer compilado contra versão diferente. Sintomas possíveis:
- `NoSuchMethodError`/`NoClassDefFoundError`;
- mixin target incompatível;
- registry/bootstrap order divergente;
- mudança semântica de helper sem erro de compilação no JAR já distribuído.

Não diagnosticar “Bookshelf bug” sem identificar qual consumer e qual surface falhou.

## 9. Observabilidade
Para troubleshooting, registrar:
- Bookshelf 21.1.81 efetivamente carregada;
- consumer envolvido;
- fase do lifecycle do erro;
- lado cliente/servidor;
- stack trace original;
- presença de versões duplicadas/embedded.

Evitar mascarar causa com catch genérico em compat própria.

## 10. Riscos
1. Remover library com consumer ativo.
2. Atualizar isoladamente e quebrar ABI/API.
3. Update checker bloquear thread principal por regressão.
4. Utility client-only carregada no servidor.
5. Mixin da library competir com outro coremod.
6. Consumer usar cache/shared state fora do lifecycle correto.

## 11. Matriz de testes
1. Dedicated server boot com o pack completo.
2. Client boot e entrada em mundo/servidor.
3. Offline/sem rede: nenhuma trava do update checker.
4. Restart repetido sem state vazando.
5. Resource/datapack reload dos consumers relevantes.
6. Verificar logs por linkage errors ligados a Bookshelf.
7. Após qualquer update: smoke-test dos consumers que declaram Bookshelf.

## 12. Evidência
- modlist física atual: Bookshelf 21.1.81;
- CurseForge oficial: library de código/frameworks/utilities/resources;
- source oficial Darkhax-Minecraft/Bookshelf branch 1.21.1;
- changelog 21.1.81: fix do fallback update checker bloqueando a main thread.

> 📚 Exaustividade proporcional: Bookshelf é library. A ficha cataloga contracts de dependência, lifecycle, side e a regressão concreta da 21.1.81 sem fabricar gameplay ou APIs internas não auditadas.
