# Alex's Delight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819d9b43c31ae012bab8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alex's Delight
- **Arquivo JAR:** `alexsdelight-1.6.jar`
- **Versão 1.21.1:** 1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Compat
- **Função:** Bridge culinária Alex's Mobs ↔ Farmer's Delight: converte drops/recursos de `alexsmobs` em ingredientes, pratos e receitas do ecossistema Farmer's Delight.
- **Dependências:** Relações upstream: Alex's Mobs, Citadel e Farmer's Delight. Pack atual: Alex's Mobs Continued 2.1.11 sob mod ID `alexsmobs`, Citadel 2.7.1 e Farmer's Delight 1.3.4. Compatibilidade do Continued deve ser validada por recipes/tags/registries.
- **Sobreposição:** Complementa outros addons Farmer's Delight, mas sua função é específica para conteúdo de `alexsmobs`; não duplica o framework culinário-base.
- **Compatibilidade/Riscos:** Principal risco é data compatibility com Alex's Mobs Continued 2.1.11: mesmo mod ID/conteúdo-base não garante todos os registry names/tags esperados. Farmer's Delight físico atual é 1.3.4; validar recipes, Cutting Board, Cooking Pot, JEI e `/reload`.
- **Observações:** Sem decisão final preservada. Validar JEI, Cutting Board, Cooking Pot, drops e `/reload`; não presumir compatibilidade apenas porque Continued mantém o mod ID `alexsmobs`.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Alex's Delight 1.6 + dossiê técnico existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alexs-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — bridge culinária, Alex's Mobs Continued 2.1.11, Citadel 2.7.1, Farmer's Delight 1.3.4 e recipe/tag QA reconciliados no QC global #20.
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
O risco principal é data compatibility entre Alex's Delight 1.6 e Alex's Mobs Continued 2.1.11: o Continued preserva o mesmo mod ID e conteúdo-base, mas uma mudança de registry name/tag pode quebrar recipes silenciosamente. A versão física do Farmer's Delight é **1.3.4**, superior ao 1.3.3 ainda descrito em alguns trechos antigos do guia; a modlist física é a autoridade atual.

## Limites
Não altera IA, spawn ou drops-base de Alex's Mobs fora das integrações próprias. Não substitui Farmer's Delight e não é uma cozinha completa independente.

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
