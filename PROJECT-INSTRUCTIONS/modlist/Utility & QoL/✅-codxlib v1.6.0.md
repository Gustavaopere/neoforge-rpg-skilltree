# CodxLib

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#101**: JAR `codxlib-1.6.0-neoforge+1.21.1.jar`, mod id `codxlib`, runtime `1.6.0`, SHA-1 `5934c440dd8b4633ca2465dfe94ecfdbe5ad8e89`.

## Propriedades do registro

- **Mod:** CodxLib
- **Arquivo JAR:** `codxlib-1.6.0-neoforge+1.21.1.jar`
- **Versão 1.21.1:** `1.6.0`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/codxlib
- **Função:** Biblioteca compartilhada do ecossistema Codx, com serviços comuns e infraestrutura de settings/menu usada por mods consumidores; no pack atual, Alex's Mobs Continued e Alex's Caves Continued são consumers confirmados.
- **Dependências:** Biblioteca Client & Server; necessidade consumer-driven. Consumers físicos confirmados: Alex's Mobs Continued 2.1.11 (exige CodxLib 1.6.0+ no catálogo auditado) e Alex's Caves Continued 1.0.9. Não substituir por Citadel/AzureLib/GeckoLib por similaridade.
- **Compatibilidade/Riscos:** Library consumer-driven; riscos de API/ABI drift, linkage, classloading client-only, shared handlers e settings/menu contracts. As notas upstream 1.6.1 afirmam explicitamente que, fora da nova linha Minecraft 26.3, os demais artefatos são byte-for-byte iguais à 1.6.0.
- **Sobreposição:** Library própria do ecossistema Codx. Coexistência com Citadel, AzureLib, GeckoLib e outras APIs não implica redundância; cada consumer compila contra contratos específicos.
- **Observações:** Runtime físico permanece CodxLib 1.6.0. A release line 1.6.1 adiciona suporte Minecraft 26.3; para as demais versões, o autor declara zero mudanças de API/comportamento e artefatos byte-for-byte idênticos à 1.6.0.
- **Procedência:** modlist física atual + CurseForge oficial CodxLib 1.6.0 + release notes upstream 1.6.1. Consumers Continued permanecem o regression graph real do pack.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, o dossiê havia sido reconstruído na então build 1.5.1. Em 09/09/2026, foi reconciliado à build física 1.6.0 e aos deltas oficiais da release, sem converter dependência técnica em decisão curatorial.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — CodxLib físico permanece 1.6.0. A upstream 1.6.1 foi comparada: para a linha deste pack não há delta funcional/API; as notas oficiais dizem que versões não-26.3 são byte-for-byte idênticas à 1.6.0.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs
> ✅ Versão física confirmada: `codxlib-1.6.0-neoforge+1.21.1.jar`, mod id `codxlib`, runtime `1.6.0`, NeoForge 1.21.1. CodxLib é uma **library de suporte**, não um provider de gameplay autônomo.
## 1. Papel e authority
CodxLib centraliza código/serviços comuns usados por mods Codx. O consumer continua authority de mobs, itens, AI, spawns, configs e demais gameplay que utiliza a library.
No pack atual, **Alex's Mobs Continued** é consumidor confirmado; não atribuir suas entidades ou mecânicas ao CodxLib.
## 2. Consumer-driven necessity
A presença da library é determinada pelo dependency graph dos consumers. Regras operacionais:
- não remover enquanto consumer ativo a exigir;
- não substituir por outra library com função parecida;
- não atualizar isoladamente sem smoke-test dos consumers;
- diagnosticar erros sempre com versão da library + versão do consumer.
## 3. Release 1.6.0 — deltas versionados
A modlist física atual instala **CodxLib 1.6.0** para NeoForge 1.21.1. O changelog oficial da linha 1.6.0 declara que as mesmas notas se aplicam aos loaders suportados e confirma:
- correção dos botões `+`/`-` de settings numéricos no chest menu, que antes adicionavam o novo valor ao antigo e podiam levar rapidamente o setting ao máximo;
- novo hook para um mod consumidor fornecer uma página própria atrás de um setting, permitindo UI customizada para listas/texto antes read-only no chest menu;
- settings não agrupados em uma categoria passam a aparecer antes dos botões de grupos.
O próprio upstream cita **Alex's Mobs Continued** usando o novo hook no editor de spawn group size. O changelog 1.5.1 permanece apenas como histórico da versão anterior, não como descrição da build instalada.
## 4. Shared services
A página oficial descreve CodxLib como supporting library que oferece shared services aos mods Codx. Sem source pin granular do JAR 1.21.1 nesta etapa, o catálogo não congela nomes de classes/métodos.
Integrações próprias devem consumir a API realmente compilada pelo consumer ou inspecionar o JAR/source correspondente antes de codificar contra símbolos concretos.
## 5. Relação com consumers Continued
**Alex's Mobs Continued 2.1.11** é consumer confirmado, exige CodxLib 1.6.0+ no catálogo auditado e usa o novo hook de settings da 1.6.0 no editor de spawn group size. **Alex's Caves Continued 1.0.9** também usa CodxLib no stack físico atual.
A separação de ownership é obrigatória:
- CodxLib: infraestrutura compartilhada, settings/menu e serviços comuns;
- Alex's Mobs Continued: mobs, behavior, loot, spawn e conteúdo final;
- Alex's Caves Continued: cave biomes, mobs, worldgen e conteúdo final.
Remover a library pode quebrar consumers; isso não transforma CodxLib em owner do gameplay.
## 6. Client/server
A distribuição é Client & Server. Shared/common services podem ser carregados em ambos os lados; qualquer render/UI específica de consumer continua client-side.
Dedicated server não deve precisar carregar classes gráficas apenas porque um consumer usa a library.
## 7. Lifecycle
Validar bootstrap, registro dos consumers, client join, world load, datapack/resource reload quando consumers usarem essas superfícies, disconnect/reconnect e server restart.
Shared caches/handlers não podem duplicar registration após reload ou reconexão.
## 8. Version drift
Sintomas possíveis de incompatibilidade incluem linkage errors, missing classes/methods, falha de bootstrap do consumer, menu/settings incorretos ou comportamento parcial. Na 1.6.0, o novo hook de páginas customizadas de settings amplia a superfície consumer-facing; regressões devem ser diagnosticadas com **CodxLib 1.6.0 + consumer exato + fluxo de menu/settings**, sem atribuir causa automaticamente à library ou ao consumer isoladamente.
## 9. Riscos
1. Remover CodxLib com consumer ativo.
2. Atualizar library e consumer fora de faixa compatível.
3. Confundir library com provider de gameplay.
4. Classloading client-only em dedicated server.
5. Shared handler/cache duplicado em lifecycle.
6. Supor APIs concretas sem source/JAR pin da build.
## 10. Matriz de testes
1. Dedicated server boot com consumers Codx atuais.
2. Client boot/join sem linkage errors.
3. Smoke-test de Alex's Mobs Continued 2.1.11: registry/spawn básico + editor de spawn group size no chest menu.
4. Smoke-test de Alex's Caves Continued 1.0.9: bootstrap/registry básico sem linkage errors.
5. Botões `+`/`-` de setting numérico substituem o valor corretamente e não saltam ao máximo — regressão 1.6.0.
6. Disconnect/reconnect e restart sem duplicate registration.
7. Resource/datapack reload conforme superfícies dos consumers.
8. Atualização futura: testar library + consumers em conjunto.
## 11. Evidência
- modlist física atual de 08/09/2026: `codxlib-1.6.0-neoforge+1.21.1.jar`, runtime 1.6.0;
- CurseForge oficial: supporting library/shared services, Client & Server, linha 1.6.0 publicada;
- changelog oficial 1.6.0: fix de settings numéricos no chest menu, novo hook para página própria de setting e nova ordenação de settings não agrupados;
- catálogo atual: Alex's Mobs Continued 2.1.11 e Alex's Caves Continued 1.0.9 como consumers confirmados.
> 📚 Boundary canônico: CodxLib fornece **infraestrutura**. Gameplay e registries finais permanecem sob authority dos mods Codx consumidores.


## 12. Upstream 1.6.1 — sem delta funcional para 1.21.1
As notas oficiais de **CodxLib 1.6.1** informam que a release adiciona suporte ao **Minecraft 26.3**.

O próprio autor registra que **todas as outras versões são byte-for-byte iguais à 1.6.0**, sem mudanças de API nem de comportamento, e que não há motivo para atualizar quando não se joga em 26.3.

Consequência para este pack: a autoridade física continua **CodxLib 1.6.0** e não existe mudança funcional a incorporar no runtime Minecraft 1.21.1. O único gate de atualização permanece compatibilidade binária com Alex's Mobs Continued/Alex's Caves Continued e demais consumers reais.

Fonte upstream: release notes oficiais CodxLib 1.6.1.