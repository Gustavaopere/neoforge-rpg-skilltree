# YUNG's Better Jungle Temples

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a98f0bca063c327818
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG's Better Jungle Temples
- **Arquivo JAR:** `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui/expande jungle temples por estruturas maiores com desafios, traps e loot.
- **Dependências:** YUNG's API 5.1.8. Targets de compat presentes: Create 6.0.10, Supplementaries 3.9.8 e Alex's Mobs Continued 2.1.11 (mod id `alexsmobs`).
- **Sobreposição:** Redesign específico de Jungle Temple. Compat pieces reutilizam conteúdo de outros mods, mas não transferem ownership da structure.
- **Compatibilidade/Riscos:** Riscos: special-piece version drift, missing block/entity em pieces condicionais, loot/density e quest duplicada por confundir piece com structure root.
- **Observações:** Mod id `betterjungletemples`, runtime `1.21.1-NeoForge-3.1.2`. Upstream possui special pieces opcionais para Create, Supplementaries e Alex's Mobs; todos os targets estão presentes no pack, mas runtime selection ainda precisa de smoke.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Better Jungle Temples NeoForge 3.1.2 + targets de compat atuais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-jungle-temples-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — layout, puzzles/traps, built-in Create/Supplementaries/Alex's Mobs compat e quest boundary catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🌴 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`, mod id `betterjungletemples`, versão `1.21.1-NeoForge-3.1.2`. Redesenha Jungle Temples com layout, traps, puzzles e loot próprios.

## 1. Conteúdo confirmado
O projeto substitui o templo vanilla por uma estrutura totalmente redesenhada com novos desafios, traps, puzzles e loot.

## 2. Built-in compatibility
O upstream documenta peças especiais opcionais para **Create**, **Supplementaries** e **Alex's Mobs**. No pack atual, Create 6.0.10, Supplementaries 3.9.8 e Alex's Mobs Continued 2.1.11/mod id `alexsmobs` estão presentes.
A presença desses mods não prova que toda peça opcional foi realmente selecionada/gerada no runtime atual. Essa superfície deve ser observada em worldgen smoke.

## 3. Authority
Better Jungle Temples é authority da structure/layout e de suas pieces/loot. Create/Supplementaries/Alex's Mobs continuam authorities de seus próprios blocos/entidades quando usados por pieces compatíveis.

## 4. Boundary para quests/perks
Discovery/completion deve usar structure identity e causalidade real. Encontrar uma piece de Create/Alex's Mobs dentro do templo não transfere ownership da estrutura para esse mod nem deve gerar dois milestones.

## 5. Lifecycle
Validar chunk generation, save/restart, structure piece loading, loot generation, mob/entity spawn de pieces opcionais e datapack/config reload quando aplicável.

## 6. Riscos
- pieces opcionais quebradas por version drift dos mods-alvo;
- loot inflation;
- structure intersection/density;
- entity/block references ausentes em dedicated server;
- quest duplicate por reconhecer piece em vez da structure root.

## 7. Matriz de testes
- [ ] Dedicated server gera Better Jungle Temple 3.1.2.
- [ ] Layout/traps/puzzles funcionam sem missing pieces.
- [ ] Create 6.0.10 special pieces aparecem quando elegíveis sem registry errors.
- [ ] Supplementaries/Alex's Mobs pieces não causam missing block/entity.
- [ ] Loot gera uma vez e persiste normalmente.
- [ ] Quest discovery usa identity da structure root.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 8. Limitação
Não foram enumeradas as pools/pieces condicionais da 3.1.2; integrações específicas devem inspecionar resources/JAR antes de depender de piece IDs.