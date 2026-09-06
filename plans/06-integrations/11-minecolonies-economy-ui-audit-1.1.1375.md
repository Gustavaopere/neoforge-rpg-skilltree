# 06.11 — Auditoria de seam UI do Town Hall

**Data:** 2026-09-05  
**MineColonies:** `1.1.1375-1.21.1-snapshot`  
**Commit upstream auditado:** `a8022f703d80be3a0931f0d6cc34b229563ef713`  
**Resultado:** `TOWN_HALL_UI=FAIL_CLOSED`

## Escopo

Determinar se a V1 pode adicionar uma superfície econômica ao Town Hall usando somente API/extensão pública e estável, sem mixin, substituição invasiva de recursos ou dependência em classes internas do MineColonies.

## Evidência da build exata

`com.minecolonies.core.client.gui.townhall.AbstractWindowTownHall` é uma classe `core`, não API. O construtor registra diretamente as páginas conhecidas:

- actions;
- info;
- permissions;
- citizens;
- stats/happiness;
- settings;
- alliances.

Os handlers são criados diretamente com `new WindowMainPage(...)`, `new WindowInfoPage(...)`, `new WindowPermissionsPage(...)`, etc. Não existe nesse boundary um registry, callback ou coleção pública de páginas adicionais.

O layout compartilhado é resolvido em `minecolonies:gui/townhall/...`, e as páginas do Town Hall reutilizam recursos XML do namespace do MineColonies. `WindowConstants` publica IDs/constantes de controles, mas não fornece mecanismo de registro de nova aba/página.

A busca nas superfícies `com.minecolonies.api` encontrou registries públicos para extensões de buildings/conteúdo, porém nenhum registry/API para anexar páginas ao `AbstractWindowTownHall` ou interceptar a construção da sidebar. BlockUI fornece primitives para janelas próprias; isso não equivale a um seam público para modificar uma janela já construída pelo MineColonies.

## Alternativas rejeitadas

1. **Mixin/injeção em `AbstractWindowTownHall`** — depende de classe `core.client.gui`; proibido pelo contrato V1.
2. **Override do XML `minecolonies:gui/townhall/windowtownhall.xml`** — substitui recurso de namespace alheio, cria conflito de load order/resource pack e é especialmente frágil com addons que alterem GUI.
3. **Patch/redirect de `registerButton(...)`** — novamente depende de internals e ordem de construção da GUI.
4. **Trocar a GUI nativa do Town Hall por uma GUI própria** — viola provider-native first e duplicaria responsabilidade do MineColonies.
5. **Afirmar que building-extension registry é GUI-extension registry** — falso; as superfícies auditadas têm finalidade distinta.

## Decisão V1

`TOWN_HALL_UI=FAIL_CLOSED`.

A V1 mantém:

- snapshot S2C;
- mint preflight C2S/S2C;
- mint/retire intents C2S;
- cache client read-only;
- authority, permissions e mutations exclusivamente no servidor.

Mas **não registra uma aba econômica no Town Hall** nesta entrega. A existência do networking não autoriza expor uma GUI via internals frágeis.

Uma UI futura pode ser aprovada se uma versão do MineColonies/BlockUI oferecer seam público de extensão do Town Hall, ou se o design aprovar explicitamente uma superfície própria separada do Town Hall. Essa decisão exige nova auditoria e não será inferida silenciosamente.
