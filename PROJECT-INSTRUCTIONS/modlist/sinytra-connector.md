# Sinytra Connector

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8103a9acee25c6616051  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Sinytra Connector
- **Arquivo JAR:** `connector-2.0.0-beta.17+1.21.1-full.jar`
- **Versão 1.21.1:** `2.0.0-beta.17+1.21.1`
- **Categoria:** Compat; Biblioteca
- **Decisão:** Opcional
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sinytra-connector/files/8654089
- **Função:** Camada de compatibilidade que permite carregar uma parcela de mods Fabric em NeoForge por transformação/adaptação de classes, mappings, Mixins/APIs e serviços de loader necessários ao runtime.
- **Dependências:** NeoForge 1.21.1 e stack de compatibilidade exigido pelos consumers Fabric reais. Connector Extras depende do Connector. Forgified Fabric API pode complementar consumers Fabric, mas sua presença isolada não prova necessidade do Connector porque pode ter consumers NeoForge nativos.
- **Compatibilidade/Riscos:** Camada complexa de transformação/mapeamento; compat não é garantida para todo mod Fabric. Riscos: Mixins incompatíveis, APIs multiloader divergentes, mappings/aliases, duplicate modules, cache stale e mods híbridos confundidos com consumers Connector. Beta.17 altera signature stripping, module names, adapters, Class Tweakers, fluid aliases e localização do cache.
- **Sobreposição:** Connector Extras amplia bridges específicas; Forgified Fabric API fornece APIs Fabric portadas; nenhum deles substitui a transformação geral do Connector. Mods híbridos NeoForge/Fabric podem não precisar do Connector.
- **Observações:** Host físico `connector-2.0.0-beta.17+1.21.1-full.jar`. A linha top-level do host não expõe mod id/name/version, porém o próprio host embarca `/META-INF/jarjar/org.sinytra.connector-2.0.0-beta.17+1.21.1-mod.jar`, cuja metadata declara mod id `connector` e runtime `2.0.0-beta.17+1.21.1`; o campo de versão usa essa evidência física interna, não inferência do filename.
- **Procedência:** modlist.txt física atual de 08/09/2026 com 595 top-levels + metadata física jar-in-jar do host (`org.sinytra.connector-2.0.0-beta.17+1.21.1-mod.jar`, mod id `connector`, runtime 2.0.0-beta.17+1.21.1) + publicação oficial File ID 8654089 Beta 17 + documentação/FAQ/source oficiais Sinytra Connector.
- **Histórico da decisão:** Em 06/09/2026 foi registrada decisão formal `Opcional`: manter Sinytra Connector apenas se houver consumer Fabric real que dependa dessa camada. A presença de Distant Horizons híbrido ou Forgified Fabric API não prova essa necessidade. Antes de remover, executar boot A/B em cópia do perfil e verificar logs/consumers transformados. Em 08/09/2026, a decisão foi preservada enquanto a linha top-level não expunha runtime version; em 09/09/2026, a metadata jar-in-jar do próprio bundle resolveu fisicamente o módulo `connector` como `2.0.0-beta.17+1.21.1`.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Sinytra Connector 2.0.0-beta.17+1.21.1 confirmado pela metadata do módulo jar-in-jar do host full.jar; transformation/mappings/Mixins, Beta.17 deltas, cache/lifecycle e necessidade condicional confirmados no QC global #105. Decisão Opcional preservada; runtime QA não executado.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🔀 Host físico confirmado: `connector-2.0.0-beta.17+1.21.1-full.jar`, publicação oficial **Beta 17 / File ID 8654089** para NeoForge 1.21.1. Embora a linha top-level do parser não exponha `mod version`, o próprio host contém `/META-INF/jarjar/org.sinytra.connector-2.0.0-beta.17+1.21.1-mod.jar`, cuja metadata declara mod id `connector` e runtime `2.0.0-beta.17+1.21.1`. Portanto essa é a versão runtime fisicamente confirmada do módulo Sinytra Connector; o filename do host deixa de ser a única evidência.

## 1. Papel e authority
Sinytra Connector é uma camada de compatibilidade destinada a executar uma parcela de mods **Fabric em NeoForge**. Ele transforma/adapta expectativas de loader, mappings, Mixins e APIs para o ambiente NeoForge.
O mod Fabric continua authority de seu gameplay; Connector é authority da camada de adaptação que permite o carregamento quando suportado.

## 2. Compatibilidade não é universal
A documentação oficial é explícita em que executar mods de outro loader envolve limites. Mixins, APIs exclusivas, assumptions de loader e diferenças do runtime NeoForge podem impedir compatibilidade mesmo quando o mod inicia.
Portanto “Fabric mod” não significa automaticamente “funciona via Connector”. Cada consumer precisa de prova de boot e comportamento.

## 3. Consumers reais
A necessidade do Connector é determinada pelos **mods Fabric que efetivamente dependem dele** no perfil atual. JAR híbrido que já possui NeoForge nativo não prova necessidade de tradução.
Do mesmo modo, Forgified Fabric API pode existir por consumers NeoForge nativos e não deve ser usada como proxy para dizer que Connector é obrigatório.

## 4. Transformação e mappings
Connector atua em class transformation e remapeamento para reconciliar expectativas Fabric com NeoForge. Essa camada é sensível a:
- nomes/mappings de classes e membros;
- Mixins e targets;
- módulos/libraries embarcadas;
- APIs esperadas pelo consumer;
- versão de Minecraft/NeoForge.

Falha deve ser diagnosticada pelo stack trace e consumer, não por tentativa aleatória de trocar mods.

## 5. Mixins e patches
Mods Fabric frequentemente usam Mixins. Connector pode adaptar parte desse fluxo, mas Mixins que dependem de bytecode/layout exclusivo do Fabric ou de alterações já feitas pelo NeoForge podem falhar ou produzir comportamento divergente.
Um Mixin aplicado sem crash ainda precisa de QA funcional; sucesso de transformação não prova paridade de gameplay.

## 6. APIs multiloader e ports
A FAQ oficial distingue APIs compartilhadas/multiloader de ports independentes. Dois projetos com a mesma finalidade podem ter implementações e contracts diferentes.
Não substituir automaticamente uma dependência Fabric por uma library NeoForge “equivalente” sem bridge oficialmente suportada.

## 7. Beta 17 — deltas confirmados
O changelog da publicação Beta 17 registra:
- remoção/strip de signatures de libraries;
- module names forçados a serem únicos;
- atualização de adapters;
- suporte a **Class Tweakers**;
- resolução de alias namespaces para mappings de tipos de fluid;
- movimentação do cache para fora da pasta `mods`.

Esses pontos são regression gates da build publicada.

## 8. Cache
Connector mantém cache de transformação. Beta 17 move esse cache para fora da pasta de mods. Após troca de mod/version, cache stale pode mascarar diagnóstico ou conservar transformação antiga.
Testes A/B devem controlar cache conforme orientação do projeto, sem apagar dados arbitrariamente do mundo.

## 9. Libraries, module names e inventário embarcado
Signature stripping e unique module names mostram que libraries embarcadas são uma superfície real de compatibilidade. Duplicatas/module collisions podem causar linkage/classpath failures.
O bundle físico `full.jar` contém ao menos dois componentes relevantes à identidade desta ficha:
- `org.sinytra.connector-2.0.0-beta.17+1.21.1-mod.jar`: módulo runtime, mod id `connector`, versão `2.0.0-beta.17+1.21.1`;
- `runtime-1.0.0+1.21.1.jar`: helper/runtime embarcado do bundle.

Ambos permanecem **jar-in-jar do host** e não viram entradas top-level independentes. A versão do primeiro, porém, é metadata física válida para preencher o runtime do Sinytra Connector.

## 10. Fluid aliases
A Beta 17 inclui ajuste de alias namespaces ligado a tipos de fluid. Esse é contract de compatibilidade/mapping, não um novo sistema de fluidos.
Create, TFC, Forge/NeoForge capabilities e outros providers continuam authorities de seus tanks/fluids; Connector apenas adapta a expectativa do consumer Fabric.

## 11. Connector Extras
Connector Extras é addon separado que amplia bridges específicas como energia, recipe viewers, configs e TerraBlender. Ele não substitui o Connector.
Se A/B demonstrar que não existe consumer Fabric que precise do Connector, a necessidade do Extras também deve ser reavaliada; a decisão formal de ambos é `Opcional`.

## 12. Forgified Fabric API
FFAPI oferece APIs Fabric portadas ao ambiente Forge/NeoForge e pode ser parte do stack Connector. Porém sua presença não equivale a prova de consumer Connector.
Remover Connector não autoriza remover FFAPI sem dependency graph próprio.

## 13. Client/server
Connector pode atuar nos dois lados conforme os consumers. A compat precisa respeitar side declarations do mod original e do target NeoForge.
Código client-only de um mod Fabric não pode ser carregado no dedicated server apenas por transformação; state de gameplay continua server-authoritative quando o consumer assim define.

## 14. Lifecycle
Validar bootstrap/transform cache, registry, datapack/resource reload, client join, disconnect/reconnect e server restart. Ao atualizar consumer/Connector, regenerar/revalidar transformação conforme o fluxo upstream.
Não assumir que um mundo que abre depois da troca está livre de incompatibilidades de save/data.

## 15. A/B obrigatório antes de decisão de remoção
Como o catálogo já registrou Connector como `Opcional`, a forma correta de resolver necessidade é teste controlado em **cópia do perfil**:
1. inventariar consumers Fabric efetivamente carregados;
2. registrar baseline de boot/log;
3. remover Connector + Extras apenas na cópia;
4. comparar mods ausentes/falhas/behavior;
5. manter FFAPI se consumers NeoForge ainda a exigirem.

A/B não deve ser executado no perfil produtivo sem backup.

## 16. Riscos
1. Consumer Fabric incompatível apesar de transformar.
2. Mixin aplicar no target errado ou divergir do comportamento esperado.
3. Mapping/alias incompatível.
4. Cache stale após update.
5. Duplicate module/library no classpath.
6. JAR híbrido ser contado erroneamente como consumer Connector.
7. FFAPI ser removida por associação incorreta.
8. Connector Extras registrar bridge desnecessária.
9. Build Beta introduzir regressão de lifecycle.

## 17. Matriz de testes
1. Dedicated server boot com stack atual.
2. Enumerar consumers Fabric realmente transformados pelo Connector.
3. Client join e smoke-test de cada consumer relevante.
4. Verificar logs de Mixins/mappings/module collisions.
5. Resource/datapack reload.
6. Restart/reconnect com cache controlado.
7. Consumer que usa fluid/API bridge após Beta 17.
8. Atualizar/remover um consumer em cópia e validar cache regeneration.
9. Boot A/B sem Connector + Extras em cópia do perfil.
10. Confirmar quais dependências permanecem necessárias sem Connector.

## 18. Evidência
- modlist física atual: host `connector-2.0.0-beta.17+1.21.1-full.jar` presente;
- metadata física interna: módulo `connector` runtime `2.0.0-beta.17+1.21.1` + helper `runtime-1.0.0+1.21.1.jar` embarcados;
- CurseForge oficial File ID 8654089 / Beta 17;
- documentação/FAQ oficiais: objetivo de executar mods Fabric em NeoForge e limites de compatibilidade;
- changelog Beta 17: signatures, module names, adapters, Class Tweakers, fluid aliases e cache;
- decisão histórica 06/09/2026: `Opcional`, condicionada a consumer Fabric real.

> 🔒 Fail-closed revisado: a linha top-level do bundle não expõe `mod version`, mas a **metadata jar-in-jar do próprio host** declara o módulo `connector` como `2.0.0-beta.17+1.21.1`. O campo de versão é preenchido a partir dessa evidência física, não por inferência do filename.
