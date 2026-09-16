# Petrolpark's Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `petrolpark-1.21.1-1.5.11.jar`
- **Versão 1.21.1:** 1.5.11
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Tecnologia
- **Função:** Biblioteca/framework do ecossistema Petrolpark usada por addons Create e consumers como Destroy para APIs e sistemas compartilhados.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Destroy `0.4.3`, cujo manifest do port 1.21.1 exige Petrolpark `[1.5.0,1.6.0)`; a física `1.5.11` continua dentro dessa faixa formal.
- **Sobreposição:** Library específica do ecossistema Petrolpark; não é substituível por libraries Create genéricas. É load-bearing enquanto Destroy permanecer.
- **Compatibilidade/Riscos:** A compat estática Destroy↔Petrolpark permanece comprovada pelo range `[1.5.0,1.6.0)`, mas runtime/linkage continua QA pendente. Riscos em API/ABI, JEI, data propagation, recipe scanning, Create optional surfaces e beta churn.
- **Observações:** JAR físico `petrolpark-1.21.1-1.5.11.jar`, mod id `petrolpark`, runtime 1.5.11. Release oficial Beta NeoForge 1.21.1 de 14/09/2026, file ID 8878365. Changelog 1.5.11: **Update to latest JEI**. JEI físico atual: `19.56.0.440`.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Petrolpark 1.5.11 + source read-only do port Destroy `NHblock714/Destroy@1.21.1-neo`, já auditado, com `versionRange="[1.5.0,1.6.0)"`. Nenhum runtime/linkage test executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/petrolpark-library
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 1.5.10 para 1.5.11; range Destroy continua satisfeito e changelog 1.5.11 novamente altera a superfície JEI. Certificação pendente de QC/re-fetch final.
- **Histórico da decisão:** 2026-08-26 — classificado Dependência por Destroy. 2026-09-11 — source exato confirmou `[1.5.0,1.6.0)` e Estado da pesquisa passou a Verificado; runtime QA permaneceu pendente.
- **Data da última decisão:** 2026-09-10

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `petrolpark-1.21.1-1.5.11.jar`, mod id `petrolpark`, versão `1.5.11`, NeoForge 1.21.1. Petrolpark é framework Client & Server de APIs/data systems compartilhados. **Destroy 0.4.3 é consumer físico confirmado** e aceita formalmente `[1.5.0,1.6.0)`, portanto `1.5.11` satisfaz o contract estático; isso não equivale a teste runtime.

## 1. Identidade e papel
- Projeto Petrolpark, ambiente Client & Server, licença All Rights Reserved.
- Canal da build física 1.5.11: **Beta**.
- Papel: APIs, sistemas data-driven e código compartilhado para consumers.
- Decisão: **Dependência**; Estado da pesquisa: **Verificado** no nível documental/source.

## 2. Consumer confirmado: Destroy
A modlist contém `destroy-1.21.1-0.4.3.jar`. Ownership preservado:
- Destroy: química, machines, recipes e progressão próprias;
- Petrolpark: infraestrutura compartilhada.

O source exato do port 1.21.1 registra `petrolpark_version=1.5.0` e manifest com dependência obrigatória `[1.5.0,1.6.0)`. A 1.5.11 está dentro da faixa. Stacktrace em Petrolpark pode ainda ser consequência de consumer/API mismatch; remover a library isoladamente não é seguro.

## 3. Escopo de APIs publicado
O dossiê do Notion registra sistemas da library como:
- Contaminants;
- Decaying Items / Ageing Recipes;
- teams/team-bound data;
- loot-table modifications;
- Recycling;
- Recipe Books/gating;
- Item Compression;
- Advanced Ingredients/Recipes;
- Shops;
- compat recipe deserializers;
- loot number providers/conditions/functions;
- badges/creative-tab helpers;
- wood compat;
- eventos adicionais;
- client rendering/sprite helpers.

Nem todo consumer usa toda API; disponibilidade não prova ativação.

## 4. Integração opcional Create e shared features
Petrolpark é fortemente integrada ao Create, mas não depende dele; features específicas são condicionais. Com Create presente, APIs publicadas incluem exemplos como splined tubular blocks, Ponder instructions, off-grid tiling blocks e remember-placer behavior. Shared content condicionado por dependents pode incluir Redstone Programmer, Basin Lid/lidded basin recipes e Extrusion Die. Atribuição a Destroy 0.4.3 requer uso real, não apenas existência da API.

## 5. Contaminants, decay e recipes
Contaminants podem acompanhar ItemStacks/FluidStacks e propagar por crafting/smelting/processos Create; precisam evitar duplicação/perda em transfer/processamento. Decaying Items/Ageing Recipes exigem save/restart, chunk unload, inventories modded e time jumps. Recycling/Compression/Recipe Books e advanced recipes podem escanear recipe graph e criar custo de startup/reload, ambiguidades de tags/ingredients e divergência JEI↔server.

## 6. Histórico 1.5.10 e atualização 1.5.11
A 1.5.10 (07/09/2026) tinha changelog exato **Update to latest JEI**. A física atual **1.5.11**, Beta, publicada em 14/09/2026, volta a registrar como bug fix **Update to latest JEI**. Portanto JEI continua regression surface direta, agora contra JEI físico `19.56.0.440`.

O churn rápido da linha 1.5.x justifica version pinning/smoke tests, mas não prova instabilidade por si só.

## 7. JarJars internos
A ficha anterior comprovou no host 1.5.10 JarJars como Commons Math 3.6.1, Flywheel 1.0.6, Ponder 1.0.82+mc1.21.1, Registrate 1.3.0+67 e Sable Companion 1.6.0. **O interior do novo JAR 1.5.11 não foi re-inspecionado nesta passagem**; essas versões internas permanecem evidência histórica do 1.5.10 e não são promovidas automaticamente como metadata atual.

## 8. Client/server e lifecycle
Superfícies críticas: registry/init, networking usado pelos consumers, datapack/recipe reload, persistence de contaminants/decay/team data, JEI integration e shared Create content quando ativado. Mutação funcional permanece server-authoritative.

## 9. Riscos
1. consumer/library runtime drift apesar do range formal;
2. beta/API churn;
3. JEI API — delta explícito 1.5.11;
4. data propagation de contaminants/decay;
5. recipe scanning em graph grande;
6. Create API drift;
7. attribution error em stacktrace;
8. removal breakage de Destroy;
9. assumir JarJars 1.5.10 como idênticos no 1.5.11.

## 10. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Petrolpark 1.5.11 + Destroy 0.4.3.
- [ ] Destroy registra sem missing class/method/service.
- [ ] JEI 19.56.0.440 abre categories/recipes do consumer sem API error.
- [ ] `/reload` preserva recipes/data/consumers.
- [ ] Processos Destroy representativos terminam sem state loss.
- [ ] Contaminants/decay, quando usados, persistem e propagam conforme config.
- [ ] Shared Create features usadas por consumers funcionam com Create atual.
- [ ] Restart/reconnect não duplica team/recipe/book state.

**Nenhum teste foi executado nesta reauditoria documental.**

## 11. Evidências e limites
- física atual: Petrolpark 1.5.11, Destroy 0.4.3 e JEI 19.56.0.440;
- CurseForge: 1.5.11 Beta NeoForge 1.21.1, file ID 8878365, changelog JEI;
- source Destroy: `[1.5.0,1.6.0)`;
- documentação da library e escopo migrado do Notion preservados;
- **limite:** runtime/linkage e JarJars internos do novo host não foram testados/re-inspecionados.

## 12. Reauditoria física — 16/09/2026
Runtime atualizado de 1.5.10 para 1.5.11. `Dependência` e `Verificado` permanecem sustentados documentalmente. Nenhum boot/linkage/JEI/chemistry test foi executado.