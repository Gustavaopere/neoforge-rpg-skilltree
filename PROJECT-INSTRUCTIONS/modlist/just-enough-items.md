# Just Enough Items

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819685eeea6f5bb3c3cf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JEI `19.53.0.426` e NeoForge `21.1.248` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Just Enough Items
- **Arquivo JAR:** `jei-1.21.1-neoforge-19.53.0.426.jar`
- **Versão 1.21.1:** 19.53.0.426
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** QoL
- **Função:** Infraestrutura central de visualização e indexação de itens/ingredientes, recipes e usos, com busca/bookmarks e API de plugins usada por numerosos addons de informação/compatibilidade do pack.
- **Dependências:** NeoForge 1.21.1. Source branch 1.21.1 currently targets NeoForge 21.1.248 and declares minimum 21.1.238; the pack uses exactly NeoForge 21.1.248. Physical JEI plugins include JEED 2.3.2 and multiple other JEI integrations in the modlist.
- **Sobreposição:** Pode coexistir com viewers alternativos em alguns ambientes, mas este pack possui vários plugins especificamente JEI. Não substituir por EMI/REI sem auditorar todo o dependency/plugin graph e a paridade de recipes/categories.
- **Compatibilidade/Riscos:** Installed 19.53.0.426 is an official Beta, not the stable channel. Risks: JEI plugin API drift, recipe/ingredient data reload mismatch, bookmark/search UI state, plugins compiled against older JEI, client/server recipe divergence and upgrading beta independently. The stable 1.21.1 main file remains a distinct 19.51.x line in official listing.
- **Observações:** Current physical runtime is 19.53.0.426. The prior 19.53.0.425 reference is historical only. Official CurseForge lists 19.53.0.426 as Beta while 19.51.0.418 remains the stable main file for 1.21.1 in the consulted listing. Provenance is now verified; channel choice remains an operational risk, not an identity uncertainty.
- **Procedência:** modlist.txt física atual + CurseForge official listing confirming 19.53.0.426 NeoForge 1.21.1 Beta uploaded 07/09/2026 + official mezz/JustEnoughItems branch 1.21.1 with specificationVersion 19.53.0 and NeoForge 21.1.248. No build-426-specific changelog was surfaced, so fixes are not invented.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — JEI 19.53.0.426 official Beta verified; ingredient/recipe/use/search/bookmark/plugin API, NeoForge 21.1.248 source line, physical addon stack, client/server boundaries, beta/stable channel distinction, lifecycle, risks and tests cataloged. Decision Manter preserved.
- **Histórico da decisão:** 2026-09-06 — presença do JEI aprovada; Manter. Snapshot da época usava 19.53.0.425 Beta e registrava 19.51.0.418 como stable. 2026-09-10 — modlist física atualizada para 19.53.0.426; release oficial Beta confirmada e pesquisa revalidada contra o source line 19.53.0/NeoForge 21.1.248. Decisão Manter preservada; nenhum downgrade/troca física foi executado.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `jei-1.21.1-neoforge-19.53.0.426.jar`, mod id `jei`, versão `19.53.0.426`. O CurseForge oficial confirma esta build como **Beta NeoForge 1.21.1**, publicada em 07/09/2026. A branch source `1.21.1` declara specification line `19.53.0` e usa NeoForge **21.1.248**, exatamente o loader físico do pack. **Decisão `Manter` preservada.**

## 1. Papel e authority
Just Enough Items é a infraestrutura central de descoberta de ingredientes, recipes e usos do pack. JEI indexa e apresenta dados e oferece API para plugins; não é owner da recipe de gameplay. O recipe manager/datapack/provider real continua authority sobre ingredientes, outputs, machines e condições.

## 2. Canal da build física
A versão **19.53.0.426 é oficial**, portanto a antiga incerteza de provenance foi encerrada. Porém ela é publicada como **Beta**, não Release estável. No listing oficial consultado, a main stable 1.21.1 permanece na linha 19.51.x. Isso é um risco de canal/maturidade, não razão para adulterar a versão física registrada.

## 3. Source line e NeoForge
A branch `mezz/JustEnoughItems:1.21.1` usa Minecraft 1.21.1, Java 21, specificationVersion 19.53.0 e NeoForge 21.1.248, com range mínimo NeoForge 21.1.238. O pack usa NeoForge 21.1.248, portanto a linha source está alinhada ao loader físico. O build number `.426` não foi pinado por changelog/source tag nesta auditoria.

## 4. Ingredient list e busca
JEI mantém uma lista pesquisável de ingredientes registrados e interfaces de filtragem. Search/index state é client UI/cache; a existência visual de um item no painel não significa que ele seja craftável, obtível ou permitido pela progressão atual.

## 5. Recipes e usos
A função central é navegar recipe categories e usos de ingredientes. Plugins podem adicionar categories/handlers para máquinas modded. O resultado exibido deve refletir o data state carregado; KubeJS/datapacks e configs podem alterar recipes sem mudar o JAR JEI.

## 6. Bookmarks e UI state
Bookmarks e preferências de interface são convenience state do cliente. Não devem ser usados por quests/progressão como prova de descoberta ou obtenção. Mudança de modlist pode deixar bookmark apontando para item removido; o cliente deve degradar sem quebrar world state.

## 7. Plugin API
Numerosos mods registram integração JEI por API. Registration order, ingredient types, recipe types/categories e GUI handlers são contracts version-sensitive. Um plugin compilado contra API anterior pode falhar mesmo quando o gameplay do mod-base continua funcionando.

## 8. Integrações físicas do pack
O pack contém **JEED 2.3.2** e outras integrações JEI específicas, além de Create e vários content mods com recipe categories. Isso torna JEI infraestrutura operacional, não apenas um overlay opcional. Qualquer substituição por outro viewer exige auditar plugin graph e cobertura das categories antes de remover JEI.

## 9. Relação com KubeJS/datapacks
Recipes podem ser adicionadas/removidas/redefinidas por KubeJS ou datapacks. JEI deve refletir o recipe set final, mas não controla o settlement do craft. Após mudança/reload, stale display precisa ser tratado como cache/UI até verificar o recipe manager authoritative.

## 10. Client / server
JEI tem forte componente client-side de UI/index, mas o projeto é distribuído Client & Server e pode consumir dados sincronizados. O servidor continua authority de recipes e inventários reais. Um recipe visível no cliente não autoriza craft se o servidor não possuir/aceitar a mesma definição.

## 11. Lifecycle e reload
Validar client boot, plugin registration, world join, recipe sync, resource/data reload, KubeJS reload quando aplicável, language/resource-pack change e reconnect. Índices precisam ser reconstruídos sem duplicar categories ou manter entries removidas.

## 12. Beta risk
Por estar em Beta, 19.53.0.426 deve ser tratada como version gate mais estrito. Não atualizar JEI isoladamente sem confirmar os plugins do pack. O fato de a branch source estar alinhada ao NeoForge 21.1.248 reduz incerteza de loader, mas não elimina regressões de API/UI.

## 13. Version-specific limit
A listagem oficial confirma a build 19.53.0.426, porém um changelog específico do build `.426` não foi localizado/surfaced nesta auditoria. Portanto **nenhum fix específico é atribuído à .426** sem evidência. A diferença para .425 permanece fail-closed fora da identidade/canal publicados.

## 14. Riscos técnicos
- plugin API drift entre builds beta;
- category/recipe registration duplicada;
- stale recipe/ingredient index após reload;
- client/server recipe divergence;
- KubeJS/data change não refletida na UI;
- plugin de mod removido ainda manter reference inválida;
- bookmark/search state apontar para ingredient ausente;
- substituir JEI sem paridade dos addons específicos;
- tratar Beta como stable ou assumir fixes não publicados.

## 15. Matriz de testes obrigatória
- [ ] Cliente + dedicated server iniciam com JEI 19.53.0.426 e NeoForge 21.1.248.
- [ ] Ingredient list/search abre sem crash e encontra amostra vanilla/modded.
- [ ] Recipes e usos de Create/containers modded aparecem nas categories corretas.
- [ ] JEED 2.3.2 registra sem API error.
- [ ] Outros plugins JEI físicos registram sem duplicate/NoSuchMethod errors.
- [ ] KubeJS/datapack recipe change é refletida após lifecycle suportado.
- [ ] Recipe exibida corresponde ao servidor e craft real quando aplicável.
- [ ] Resource/language reload não duplica entries/categories.
- [ ] Bookmark de item removido/alterado degrada sem crash.
- [ ] Reconnect reconstrói dados sem estado stale crítico.
- [ ] Upgrade futuro da Beta é bloqueado até smoke test do plugin graph.

## 16. Evidências e limites
- **Modlist física:** JAR/mod id/runtime 19.53.0.426.
- **CurseForge oficial:** 19.53.0.426 NeoForge 1.21.1, canal Beta, publicada 07/09/2026; stable main file separada na linha 19.51.x.
- **Source oficial:** branch `1.21.1`, specificationVersion 19.53.0, Java 21 e NeoForge 21.1.248.
- **Limite:** build-specific changelog `.426` não foi localizado; não foram inventadas diferenças em relação à .425.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
