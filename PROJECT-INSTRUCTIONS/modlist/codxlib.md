# CodxLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8178b93ada7bf210a520
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** CodxLib
- **Arquivo JAR:** `codxlib-1.6.0-neoforge+1.21.1.jar`
- **Versão 1.21.1:** 1.6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada do ecossistema Codx, com serviços comuns e infraestrutura de settings/menu usada por mods consumidores; no pack atual, Alex's Mobs Continued e Alex's Caves Continued são consumers confirmados.
- **Dependências:** Biblioteca Client & Server; necessidade consumer-driven. Consumers físicos confirmados: Alex's Mobs Continued 2.1.11 (exige CodxLib 1.6.0+ no catálogo auditado) e Alex's Caves Continued 1.0.9. Não substituir por Citadel/AzureLib/GeckoLib por similaridade.
- **Sobreposição:** Library própria do ecossistema Codx. Coexistência com Citadel, AzureLib, GeckoLib e outras APIs não implica redundância; cada consumer compila contra contratos específicos.
- **Compatibilidade/Riscos:** Riscos principais: remover com consumer ativo, version/ABI drift, classloading lateral e regressões de shared services/menu. Na 1.6.0, validar especialmente settings numéricos via +/- e o novo hook de páginas customizadas usado por consumers; atualização deve ser testada com Alex's Mobs Continued e demais consumers Codx.
- **Observações:** JAR físico atual `codxlib-1.6.0-neoforge+1.21.1.jar`, mod id `codxlib`, runtime 1.6.0. O changelog oficial 1.6.0 confirma correção dos +/- de settings numéricos, novo hook para página própria por setting e nova ordenação de settings não agrupados; Alex's Mobs Continued usa o hook no editor de spawn group size.
- **Procedência:** modlist.txt física atual de 08/09/2026 + runtime `codxlib` 1.6.0 + CurseForge oficial CodxLib 1.6.0/changelog aplicável à linha multiloader + consumers Continued já auditados no pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/codxlib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — CodxLib 1.6.0, consumers Continued, fix de settings numéricos, hook de página customizada, lifecycle e version drift confirmados no QC global #101. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, o dossiê havia sido reconstruído na então build 1.5.1. Em 09/09/2026, foi reconciliado à build física 1.6.0 e aos deltas oficiais da release, sem converter dependência técnica em decisão curatorial.
- **Data da última decisão:**

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
