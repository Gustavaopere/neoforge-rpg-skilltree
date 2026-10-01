# Just Enough Items

## Propriedades do registro

- **Mod:** Just Enough Items
- **Arquivo JAR:** jei-1.21.1-neoforge-19.56.0.440.jar
- **Versão 1.21.1:** 19.56.0.440
- **Categoria:** QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1
- **Função:** Infraestrutura central de visualização e indexação de itens/ingredientes, recipes e usos, com busca/bookmarks e API de plugins usada por numerosos addons de informação/compatibilidade do pack.
- **Dependências:** NeoForge 1.21.1; pack físico usa NeoForge 21.1.250. O pack contém múltiplas integrações/plugins JEI, portanto updates da linha Beta devem ser testados em conjunto.
- **Compatibilidade/Riscos:** Build física 19.56.0.440 é Beta oficial. Riscos: JEI plugin API drift, recipe/ingredient reload mismatch, bookmark/search UI state, plugins compilados contra builds anteriores, client/server divergence e atualização Beta isolada. Upstream avançou até 19.57.0.450 com MezzConfig GUI integration, bookmark/focus fixes, tooltip lazy caching, proxy refresh e outras mudanças de UI/config.
- **Sobreposição:** Pode coexistir com viewers alternativos em alguns ambientes, mas este pack possui vários plugins especificamente JEI. Não substituir por EMI/REI sem auditorar todo o dependency/plugin graph e a paridade de recipes/categories.
- **Observações:** Runtime físico 19.56.0.440. A sequência publicada depois é **19.56.0.441 → 19.57.0.444 → .445 → .446 → .447 → .448 → .449 → .450**; não há builds públicas .442/.443 na lista 1.21.1. A .450 é a latest NeoForge 1.21.1 em 29/09/2026.
- **Procedência:** modlist física atual + CurseForge/Modrinth oficiais JEI 19.56.0.441 e 19.57.0.444–450 + source `mezz/JustEnoughItems` branch 1.21.1. A autoridade instalada permanece 19.56.0.440.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — JEI físico permanece 19.56.0.440 / NeoForge 21.1.250. Todas as builds publicadas posteriores até **19.57.0.450** foram enumeradas e os deltas materiais registrados abaixo.
- **Histórico da decisão:** 2026-09-06 — presença do JEI aprovada; Manter. Snapshot da época usava 19.53.0.425 Beta e registrava 19.51.0.418 como stable. 2026-09-10 — modlist física atualizada para 19.53.0.426; release oficial Beta confirmada e pesquisa revalidada contra o source line 19.53.0/NeoForge 21.1.248. Decisão Manter preservada; nenhum downgrade/troca física foi executado.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #349: JAR `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, runtime `19.56.0.440`, SHA-1 `8f18e13b4cc84d2140642ec2411514b7f5b0883b`.

<callout icon="📚" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, versão `19.56.0.440`. A distribuição oficial continua no canal **Beta** para NeoForge 1.21.1. O pack usa NeoForge **21.1.250**. **Decisão ****`Manter`**** preservada.**
</callout>
## 1. Papel e authority
Just Enough Items é a infraestrutura central de descoberta de ingredientes, recipes e usos do pack. JEI indexa e apresenta dados e oferece API para plugins; não é owner da recipe de gameplay. O recipe manager/datapack/provider real continua authority sobre ingredientes, outputs, machines e condições.
## 2. Canal da build física
A versão **19.56.0.440** é a build física instalada e uma Beta oficial para NeoForge 1.21.1. O canal Beta permanece um risco operacional: plugins JEI do pack devem ser testados em conjunto.
Em 16/09/2026 foi publicada **19.56.0.441**, também Beta, posterior à instalação atual. Ela não é tratada como runtime do pack: fica apenas registrada como update disponível até a modlist física mudar.
## 3. Source line e NeoForge
O pack físico usa NeoForge **21.1.250** e JEI 19.56.0.440. A antiga pinagem documental da branch 19.53.0/NeoForge 21.1.248 não é promovida para a build atual sem novo source/tag pin exato.
Nesta revalidação, nenhum changelog específico da build `.440` foi localizado. Portanto a atualização documental é conservadora: identidade, canal, loader físico e version gate são atualizados, sem inventar fixes internos.
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
Por estar em Beta, 19.56.0.440 deve ser tratada como version gate mais estrito. Não atualizar JEI isoladamente sem confirmar os plugins do pack. O loader físico NeoForge 21.1.250 está registrado, mas a ausência de source/tag pin exato para .440 nesta auditoria mantém o comportamento version-sensitive fail-closed.
## 13. Version-specific limit
A listagem oficial confirma a build 19.56.0.440, mas nenhum changelog específico do build `.440` foi localizado nesta auditoria. Portanto **nenhum fix específico é atribuído à .440** sem evidência.
A build **19.56.0.441** é numericamente posterior e oficial, porém não está instalada. O catálogo permanece em 19.56.0.440 pela autoridade física.
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
- [ ] Cliente + dedicated server iniciam com JEI 19.56.0.440 e NeoForge 21.1.250.
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
- **Modlist física de 17/09/2026:** `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, runtime 19.56.0.440 e NeoForge 21.1.250.
- **Distribuição oficial:** 19.56.0.440 Beta para NeoForge 1.21.1; 19.56.0.441 Beta foi publicada depois e não está instalada.
- **Limite:** changelog específico da .440 não foi localizado; não foram inventadas diferenças funcionais.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.


## 17. Histórico upstream 19.56.0.441 → 19.57.0.450 — não instalado
A autoridade física continua em **JEI 19.56.0.440**. Não existem builds públicas 1.21.1 `.442` ou `.443` na sequência consultada.

### 19.56.0.441 — 16/09/2026
- adiciona damage-bar ingredients ao debug plugin;
- corrige item decorations renderizando **atrás de tooltips** (#4479);
- corrige crash de **client config registration durante datagen** (#4491).

### 19.57.0.444 — 20/09/2026
- atualiza MezzConfig/MezzConfig GUI e adiciona integração opcional com a GUI;
- mostra key mappings, runtime toggles e sorting orders na config screen;
- adiciona controles combinados de grid alignment;
- lê mod icons da loader metadata;
- passa a rastrear plugins que registram ingredient types;
- compartilha controles entre ingredient/bookmark lists;
- atualiza tooling/development dependencies e ForgeGradle 7.

### 19.57.0.445 — 20/09/2026
Mudanças materiais publicadas incluem:
- corrige typing da **search focus key**;
- corrige traduções de bookmark-output config e **legacy config migration**;
- corrige controles JEI escurecidos na recipe screen;
- mantém paths de configuração de Realms estáveis;
- prepara/cacha tooltip slot candidates de modo **lazy conforme ficam visíveis**, reduzindo trabalho de UIs grandes;
- prioriza input no transfer de recipe bookmark (#4490);
- migra assets de UI para o GUI atlas do Minecraft;
- preserva ordem de focused ingredients e corrige recipes focus-linked escondidas;
- adiciona recipes de **fireworks**;
- atualiza recipes após **proxy server switch** (#4478).

### 19.57.0.446 — 21/09/2026
- declara metadata compatível para **MezzConfigGui opcional**;
- atualiza release notifier tooling;
- corrige **drag de bookmarks entre páginas** (#4193/#4493).

### 19.57.0.447 — 22/09/2026
- passa a **sugerir MezzConfig GUI** para configuração in-game.

### 19.57.0.448 e 19.57.0.449 — 24/09/2026
As duas builds são publicadas na linha 1.21.1. O source/release lineage entre elas é predominantemente tooling de publicação: update do Jenkins release notifier para 0.3.0 e depois **0.3.1**. Não projeto isso como mudança de recipe/gameplay. A .449 registra explicitamente o notifier 0.3.1.

### 19.57.0.450 — 29/09/2026
A alteração de source que leva à .450 corrige **abertura da MezzConfig GUI no Fabric sem Mod Menu**. Como a build NeoForge recebe o mesmo número, ela continua sendo o teto distribuído para 1.21.1, mas esse fix específico é platform-scoped e não é tratado como regressão NeoForge comprovada.

### Impacto para o pack
O salto 19.56.0.440→19.57.0.450 é principalmente UI/config/index lifecycle, mas JEI tem grande fan-out de plugins. Os fixes de proxy refresh, focus/bookmarks e lazy tooltip preparation são particularmente relevantes em modpack grande. A integração MezzConfigGui precisa coexistir com MezzConfig 0.5.9+ já documentado sem criar duas authorities de config.

Gate de promoção: client + dedicated server; todos os plugins JEI principais; search focus key; bookmark drag/transfer e várias páginas; legacy config migration; MezzConfigGui presente/ausente; recipe focus order; proxy/reconnect recipe refresh; `/reload`; KubeJS/datapacks; large-tooltip stress; fireworks recipes; GUI/resource reload.

Fontes upstream: CurseForge 19.56.0.441 e file list 19.57.0.444–450; Modrinth release notes 19.57.0.444–449; source `mezz/JustEnoughItems` branch 1.21.1.