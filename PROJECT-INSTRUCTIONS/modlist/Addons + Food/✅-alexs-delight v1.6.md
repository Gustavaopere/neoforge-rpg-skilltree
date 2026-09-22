# Alex's Delight

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#20**: `alexsdelight-1.6.jar`, mod id `alexsdelight`, runtime `1.6`, SHA-1 `19e8d1e255c431b706e79412be92d8448c668f4c`.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alex's Delight
- **Arquivo JAR:** `alexsdelight-1.6.jar`
- **Versão 1.21.1:** 1.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Compat
- **Função:** Bridge culinária Alex's Mobs ↔ Farmer's Delight: converte drops/recursos de `alexsmobs` em ingredientes, pratos e receitas do ecossistema Farmer's Delight.
- **Dependências:** Relações upstream: Alex's Mobs, Citadel e Farmer's Delight. Pack atual: Alex's Mobs Continued 2.1.13 sob mod ID `alexsmobs`, Citadel 2.7.1 e Farmer's Delight 1.3.4. Compatibilidade do Continued deve ser validada por recipes/tags/registries.
- **Sobreposição:** Complementa outros addons Farmer's Delight, mas sua função é específica para conteúdo de `alexsmobs`; não duplica o framework culinário-base.
- **Compatibilidade/Riscos:** Principal risco é data compatibility com Alex's Mobs Continued 2.1.13: mesmo mod ID/conteúdo-base não garante todos os registry names/tags esperados. Farmer's Delight físico atual é 1.3.4; validar recipes, Cutting Board, Cooking Pot, JEI e `/reload`.
- **Observações:** Mesmo mod ID `alexsmobs` não basta para declarar compatibilidade total com Continued. Validar recipes/tags/registry IDs, JEI, Cutting Board, Cooking Pot, `/reload` e recipe sync em dedicated server.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial Alex's Delight 1.6. Artefato `alexsdelight-1.6.jar`, runtime `1.6`, SHA-1 `19e8d1e255c431b706e79412be92d8448c668f4c`; providers físicos relevantes: Alex's Mobs Continued 2.1.13, Citadel 2.7.1 e Farmer's Delight 1.3.4.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alexs-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — ownership culinário, provider-version boundary, reload/recipe sync, client/server lifecycle, multiplayer e fingerprint físico documentados; compatibilidade com Continued permanece validável por data/runtime, não presumida.
- **Histórico da decisão:**
- **Data da última decisão:**

## Escopo e papel
**Alex's Delight** é a integração culinária entre **Alex's Mobs** e **Farmer's Delight**. Ela transforma drops e recursos de criaturas `alexsmobs` em ingredientes, pratos e receitas do ecossistema Farmer's Delight, conectando fauna e cozinha sem criar um terceiro framework alimentar.

## Runtime e autoridade
- JAR físico: `alexsdelight-1.6.jar`.
- Mod ID: `alexsdelight`.
- Runtime: `1.6`.
- Build instalada: NeoForge 1.21.1.

## Dependências
A página de relações do projeto publica como requeridos **Alex's Mobs, Citadel e Farmer's Delight**. No pack físico atual estão presentes:
- `alexsmobs-2.1.11-neoforge+1.21.1.jar` — Alex's Mobs Continued, mantendo o mod ID `alexsmobs`;
- `citadel-2.7.1-1.21.1.jar`;
- `FarmersDelight-1.21.1-1.3.4.jar`.

O pack, portanto, oferece os IDs/sistemas esperados; porém a relação upstream aponta para o projeto original Alex's Mobs, enquanto o pack usa o Continued. Compatibilidade funcional deve ser validada por recipes/tags/registries, não presumida apenas pelo nome.

## Integrações no pack
A bridge faz drops de criaturas ganharem uso culinário através de Cutting Board, Cooking Pot e recipes/ingredients do Farmer's Delight. Ela complementa outros addons culinários do pack, mas seu domínio é especificamente conteúdo oriundo de `alexsmobs`.

## Compatibilidade, sobreposição e riscos
O risco principal é data compatibility entre Alex's Delight 1.6 e Alex's Mobs Continued 2.1.13: o Continued preserva o mesmo mod ID e conteúdo-base, mas uma mudança de registry name/tag pode quebrar recipes silenciosamente. A versão física do Farmer's Delight é **1.3.4**, superior ao 1.3.3 ainda descrito em alguns trechos antigos do guia; a modlist física é a autoridade atual.

## Limites
Não altera IA, spawn ou drops-base de Alex's Mobs fora das integrações próprias. Não substitui Farmer's Delight e não é uma cozinha completa independente.


## Ownership técnico e superfície de integração
- **Ownership primário:** recipes/ingredientes e uso culinário de recursos do namespace `alexsmobs` dentro do ecossistema Farmer's Delight. Não possui IA, spawn ou cozinha-base.
- **Mod ID físico:** `alexsdelight`.
- O inventário físico não expôs mixin config para este JAR. Isso é compatível com uma integração fortemente orientada a conteúdo/registries, mas **não prova** que toda implementação seja exclusivamente datapack/data-driven.
- A compatibilidade relevante é semântica: os registry IDs/tags/drops esperados pelo addon precisam existir no **Alex's Mobs Continued 2.1.13** instalado.
## Dependências e versão cruzada
A publicação do addon aponta para Alex's Mobs, Citadel e Farmer's Delight. No snapshot físico atual do pack os providers correspondentes são Alex's Mobs Continued `2.1.13` sob mod ID `alexsmobs`, Citadel `2.7.1` e Farmer's Delight `1.3.4`. O mesmo mod ID reduz a barreira de descoberta, mas **não garante** que todos os recipes/tags da bridge continuem válidos após um port/update.
## Configuração, dados e reload
A principal superfície operacional são recipes, ingredients, tags e registries. A instância deve ser testada com `/reload` e JEI/recipe viewers porque erros de data podem aparecer sem crash imediato. Não foi confirmada uma configuração de usuário própria do Alex's Delight neste lote.
## Client/server, lifecycle e multiplayer
Recipes e conteúdo culinário precisam ser idênticos entre servidor e cliente. Lifecycle: registry/datapack load → `/reload` → recipe sync → aquisição de drops `alexsmobs` → Cutting Board/Cooking Pot/crafting → save/reconnect. Em dedicated server, validar que clientes recebem a recipe set correta e não exibem ghost recipes impossíveis de executar.
## Fingerprint físico
- JAR: `alexsdelight-1.6.jar`
- Runtime: `1.6`
- SHA-1: `19e8d1e255c431b706e79412be92d8448c668f4c`


## Reconciliação física atual — 18/09/2026
A modlist física mais recente (`modlist(1).txt`, 16/09/2026) mantém Alex's Delight `1.6`, Citadel `2.7.1` e Farmer's Delight `1.3.4`, mas Alex's Mobs Continued avançou de `2.1.11` para `2.1.13`. O mod ID continua `alexsmobs`; ainda assim, recipes/tags/registry IDs devem ser validados contra `2.1.13` e não se presume compatibilidade apenas pelo ID.

## Testes recomendados
1. Abrir JEI e procurar todos os ingredients/foods do Alex's Delight sem missing recipes.
2. Testar Cutting Board e Cooking Pot com materiais vindos de `alexsmobs` Continued.
3. Validar drops necessários em criaturas correspondentes.
4. Executar `/reload` e conferir ausência de erros de recipe/tag/registry.
5. Dedicated-server smoke com crafting/cooking e sincronização de recipes.
6. Repetir após qualquer update de Alex's Mobs Continued ou Farmer's Delight.

## Evidências
- [CurseForge oficial — Alex's Delight](https://www.curseforge.com/minecraft/mc-mods/alexs-delight)
- Modlist física atual e guia consolidado de Gameplay/Sistemas.
