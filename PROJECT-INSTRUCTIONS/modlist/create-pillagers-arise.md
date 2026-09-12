# Create: Pillagers Arise

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817c94d1e9a4b01e0ab5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Pillagers Arise
- **Arquivo JAR:** `create_pillagers_arise NeoForge 1.21.1-132.36.jar`
- **Versão 1.21.1:** 132.36
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração, Mobs
- **Função:** Addon de estruturas para Create que adiciona 10 estruturas defendidas por pillagers, combinando worldgen, encounters, loot e estética/tecnologia Create.
- **Dependências:** Create. Release 132.36 é NeoForge 1.21.1, Client & Server. Nenhuma dependência obrigatória adicional foi inferida sem metadata versionada do JAR.
- **Sobreposição:** Sobrepõe domínio de estruturas/raids/pillagers com outros worldgen mods, mas não é substituição automática. Conflito deve ser avaliado por spacing, biomes, encounter density, loot e colisão física de estruturas.
- **Compatibilidade/Riscos:** Worldgen/structure provider. Riscos em densidade/spacing, jigsaw/template loading, loot, mob density, estrutura gerada em chunks existentes e sobreposição espacial/temática com outros structure mods. A página pública da 132.36 contém changelog rotulado 132.34 com fix de jigsaw NBT e sistema de config de estruturas; tratar esse texto com ressalva de rótulo.
- **Observações:** mod id `create_pillagers_arise`; runtime 132.36; JAR contém espaço no filename conforme modlist física. Projeto anuncia 10 estruturas defendidas por pillagers. A página de versão 132.36 exibe texto de changelog com heading `132.34`, citando fix de jigsaw template NBT e config para enable/disable/spawn rates; não atribuir isso exclusivamente à 132.36 sem source pin adicional.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_pillagers_arise` 132.36 + CurseForge/Modrinth oficiais da release 132.36 e descrição do projeto.
- **Fonte:** https://modrinth.com/mod/create-pillager-arise/version/dSpuz2DS
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — 10-structure/worldgen authority, pillager encounter/loot boundaries, config/spacing, jigsaw/template risk, existing-world lifecycle e limite de evidência 132.36 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Pillagers Arise 132.36 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. O histórico antigo de TFC foi descartado como contexto não operacional; presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🏰 Versão física confirmada: `create_pillagers_arise NeoForge 1.21.1-132.36.jar`, mod id `create_pillagers_arise`, runtime `132.36`, NeoForge 1.21.1. O projeto declara **10 novas estruturas defendidas por pillagers** e requer Create.

## 1. Papel e authority
Create: Pillagers Arise é um provider de **worldgen/structures + encounters**. Ele decide suas estruturas, templates, spawn/config e conteúdo associado; Create fornece o universo visual/tecnológico consumido pelo addon, mas não decide onde as estruturas do addon geram.

## 2. Escopo confirmado — 10 estruturas
O upstream anuncia **10 estruturas novas**. A fonte pública consultada não expõe uma lista versionada completa dos dez nomes/IDs para 132.36; portanto esta ficha não inventa nomenclatura, biome filters ou loot individual.
Para integração structure-by-structure, usar JAR/source/data files exatos da 132.36.

## 3. Pillager encounters
As estruturas são defendidas por pillagers. Spawn/AI/damage dos mobs continuam sob authority Minecraft/mods que alteram essas entidades; o addon define o encounter/contexto da estrutura.
Mods de AI/RPG podem alterar dificuldade final, mas não devem duplicar spawn apenas porque detectam a estrutura.

## 4. Worldgen placement
Structure placement é server/worldgen state. Biome eligibility, spacing, separation e template pools precisam ser resolvidos pelos data/configs da build.
Outros structure mods podem coexistir; conflito real exige sobreposição de placement, densidade excessiva ou geração inválida, não semelhança temática.

## 5. Jigsaw/templates
A página pública da versão 132.36 contém um texto de changelog cujo heading menciona **132.34**, informando fix para geração de structures para que jigsaw structures carreguem seus **NBT templates** corretamente.
Como o rótulo interno do changelog não coincide com a versão da página, esta ficha registra o fix como **evidência da linha recente**, mas não afirma que ele foi introduzido exclusivamente na 132.36.

## 6. Structure config
O mesmo texto público descreve sistema de configuração para **habilitar/desabilitar estruturas e ajustar spawn rates**. Essa é a policy de worldgen do provider.
Valores exatos/defaults não são congelados sem leitura da config/data física atual.

## 7. Loot
O projeto menciona loot nas estruturas. Loot tables são data-driven e server-authoritative; scripts/quests devem observar o resultado final e não rolar uma segunda recompensa para o mesmo chest/encounter sem policy explícita.

## 8. Create-themed blocks e decoração
As estruturas usam estética/tecnologia Create. Um block Create colocado pelo template continua pertencendo ao Create e deve carregar seu block state/NBT corretamente.
Structure generation não pode clonar inventories ou block entities com state inválido ao instanciar templates.

## 9. Chunks existentes versus novos
Mudanças de worldgen só afetam regiões/chunks onde a estrutura ainda pode ser gerada. Atualizar config ou versão não deve ser interpretado como retrofit garantido em chunks já gerados.
Pregen deve ser executado somente após estabilizar a configuração definitiva de structures.

## 10. Densidade e exploração
Com muitos mods de estruturas, spawn rates cumulativos podem reduzir distância entre encounters e alterar progression/loot economy. A análise deve medir densidade no mundo real, não inferir conflito pela quantidade de mods instalados.

## 11. Client/server
Placement, mobs, loot e chests são server/worldgen. Models/textures e efeitos visuais são client-facing. Dedicated server precisa conseguir gerar templates sem dependência de classes gráficas.

## 12. Lifecycle
Validar server boot, geração de chunks novos, locate/structure search quando aplicável, jigsaw/template loading, chunk unload/reload, server restart e alterações de config em mundo de teste.
Mudança de spawn rate não deve corromper structures já existentes.

## 13. Riscos
1. Jigsaw/template NBT falhar e gerar estrutura parcial.
2. Spawn rates somados a outros mods criarem densidade excessiva.
3. Pillagers spawnarem duplicados por hooks externos.
4. Loot ser rolado duas vezes.
5. Template com block entity/inventory inválido.
6. Config change ser esperada retroativamente em chunks antigos.
7. Pregen congelar uma configuração intermediária.
8. Changelog 132.34 ser atribuído indevidamente como delta exclusivo da 132.36.

## 14. Matriz de testes
1. Dedicated server boot com Create.
2. Gerar chunks novos suficientes para encontrar amostra das structures.
3. Confirmar templates/jigsaw sem partes ausentes.
4. Pillager encounter e mob attribution.
5. Loot container: abrir/restart sem reroll indevido.
6. Desabilitar uma estrutura em mundo de teste e gerar chunks novos.
7. Ajustar spawn rate e comparar somente regiões novas.
8. Estrutura em chunk border.
9. Coexistência com outros grandes structure mods.
10. Pregen pequena após config estabilizada.

## 15. Evidência
- modlist física 08/09/2026: runtime 132.36;
- CurseForge/Modrinth oficiais: NeoForge 1.21.1, Client & Server, Create required, 10 structures defended by pillagers;
- página pública 132.36: texto de changelog rotulado 132.34 citando jigsaw NBT template fix e structure config de enable/disable/spawn rates;
- ausência de catálogo versionado dos 10 IDs na fonte pública tratada fail-closed.

> 🔒 Boundary canônico: **Pillagers Arise decide worldgen/encounter de suas estruturas; Create continua owner dos blocks/primitives usados pelos templates**. Config e loot são server-side e não devem ser liquidados duas vezes.