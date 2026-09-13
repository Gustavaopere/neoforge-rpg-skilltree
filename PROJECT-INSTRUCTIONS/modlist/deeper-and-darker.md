# Deeper and Darker

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81dc95dbe9816520688e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Deeper and Darker
- **Arquivo JAR:** `deeperdarker-neoforge-1.21.1-1.4.1.jar`
- **Versão 1.21.1:** 1.4.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração, Mobs
- **Função:** Expansão do Deep Dark que adiciona a dimensão Otherside, quatro biomas, Ancient Temple, novos mobs, mais de cem blocos, equipamentos/itens, Sculk Transmitter, efeitos/enchantments e progressão de exploração própria.
- **Dependências:** NeoForge 1.21.1; build Client & Server. `darkermagic-1.3.3-1.21.1-ver.b.jar` / Deeper and Darker: Spellbooks é top-level separado e precede esta entrada na ordem física atual; não é tratado como dependência obrigatória de Deeper and Darker sem metadata explícita.
- **Sobreposição:** Sobreposição temática com outros mods de dimensões/exploração não implica substituição. Deeper and Darker owns Otherside, seus biomas/estruturas/mobs e progression loop; addons como DarkerMagic devem integrar sem duplicar essa authority.
- **Compatibilidade/Riscos:** Worldgen/dimension provider amplo. Riscos em portal placement, Otherside generation, Ancient Temple/loot, Sculk Transmitter com containers modded, Soul Elytra/slot bridges, entity spawning/rendering e Crystallized Amber generation. A 1.4.1 corrige server crash causado por Crystallized Amber gerado incorretamente.
- **Observações:** mod id `deeperdarker`; runtime 1.4.1. Otherside possui Deeplands, Echoing Forest, Overcast Columns e Blooming Caverns; Ancient Temple, 8 mobs incluindo miniboss, Warden/Resonarium gear, Soul Elytra, Sculk Transmitter e Sculk Omen. 1.4.1 é minor fix sobre 1.4 e corrige crash server-side de Crystallized Amber mal gerado.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `deeperdarker-neoforge-1.21.1-1.4.1.jar` / runtime 1.4.1. Comportamento e regressões: Modrinth/CurseForge oficiais Deeper and Darker 1.4/1.4.1 e changelog/release oficial KyaniteMods.
- **Fonte:** https://modrinth.com/mod/deeperdarker/version/TuD0Zvi3
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — Otherside/worldgen/portal authority, biomes/structures/mobs/progression, Sculk Transmitter, equipment, config/lifecycle, modded-container integration e regressão 1.4.1 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Deeper and Darker 1.4.1 foi reconfirmado fisicamente e reconstruído ao padrão técnico. A presença de uma dimensão/progressão completa no pack não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🕳️ Versão física confirmada: `deeperdarker-neoforge-1.21.1-1.4.1.jar`, mod id `deeperdarker`, runtime `1.4.1`, NeoForge 1.21.1, Client & Server. É um **provider completo de dimensão/worldgen/progressão**, não apenas um addon visual do Deep Dark.

## 1. Papel e authority
Deeper and Darker expande o Deep Dark e controla a dimensão **Otherside**, seus biomas, estruturas, mobs, blocos, loot e equipamentos. Addons externos podem consumir essa camada, mas não devem registrar uma segunda Otherside ou duplicar seus progression gates.

## 2. Entrada na Otherside
A documentação oficial liga a progressão à derrota do Warden e ao **Heart of the Deep**, usado para acessar a Otherside. Portal creation/placement deve ser tratado como world state server-side.
A 1.4 melhora a geração de novos portais para buscar spawns mais expostos e adiciona uma transition screen entre Otherside e Overworld; a tela é apresentação, enquanto destination/portal state pertence ao servidor/worldgen.

## 3. Biomas
A Otherside possui quatro biomas oficiais na linha atual:
- Deeplands;
- Echoing Forest;
- Overcast Columns;
- Blooming Caverns.
A 1.4 reequilibra sua distribuição para tamanhos aproximadamente uniformes e altera geração para áreas mais abertas. Integrações de worldgen não devem impor novamente parâmetros antigos de 1.3.

## 4. Ancient Temple
Ancient Temples são estruturas de progressão importantes da dimensão e possuem loot próprio. A 1.4 adiciona/ajusta conteúdo ligado a Ancient Compass, Ancient Vases, Sculk Omen e enchantments.
Structure placement, loot e fake-vase outcomes devem settlement uma única vez; scripts externos não devem rolar loot novamente após o provider já ter decidido o resultado.

## 5. Sculk Omen e Ancient Vases
A 1.4 adiciona **Sculk Omen**, obtido quando o jogador entra em Ancient Temple com Bad Omen. Esse efeito aumenta a chance de Ancient Vases serem falsas, podendo gerar mais Sculk Leeches/Stalkers.
O cálculo de chance e spawn pertence ao provider/config. Quests/perks devem observar o resultado, não fazer segunda rolagem paralela.

## 6. Mobs
O projeto oficial anuncia **8 mobs novos, incluindo um miniboss**. A ficha não congela uma enumeração completa de IDs porque o scope oficial público varia entre páginas/releases e o JAR não foi enumerado diretamente nesta etapa.
AI, spawn, damage, drops e tame state de mobs como Sculk Snapper/Stalker/Angler Fish pertencem ao mod. Integrações RPG devem aplicar modifiers ao state final, evitando double damage/loot.

## 7. Ancient Vases e mobs hostis
Histórico da linha 1.3/1.4 confirma Sculk Leeches, Stalkers e lógica específica de Ancient Vases. Player-placed vases e generated vases não devem ser confundidos: behavior/spawn precisa respeitar a lógica do provider.

## 8. Blocos e materiais
O mod adiciona mais de cem blocos, incluindo famílias como **gloomslate**, **sculk stone**, wood/vegetation da Otherside e novos blocos funcionais/decorativos.
Tags, drops, hardness e crafting pertencem aos registries/datapacks do mod; não atribuir equivalência automática a materiais TFC/Create apenas por nome ou aparência.

## 9. Lite e iluminação
A 1.4 adiciona o bloco **Lite**, que emite luz e possui variantes craftáveis. Light level real é block state/world property do provider, distinto de emissive-only rendering de resource packs.
QA deve separar light engine real de shaders/CTM/emissive visuals.

## 10. Sculk Transmitter
A 1.4 faz grande atualização no **Sculk Transmitter**: pode linkar com containers modded, não ficando restrito a vanilla storage; também recebe opções de dye.
Essa compat amplia a superfície com inventories do pack. Transfer/link state deve apontar para um único container authority e não espelhar itens em dois inventories.

## 11. Containers modded
Como Transmitters agora suportam containers modded, testar Sophisticated/AE2/Create storage surfaces somente quando o block/container real expuser contract compatível. A página oficial não garante compat individual com todos os containers do pack.
Falha deve ser registrada por container/provider específico, não como incompatibilidade global.

## 12. Equipment e Resonarium
A linha 1.4 melhora a forma de obter **Resonarium armor/tools** e mantém Warden-themed equipment. Smithing/loot/recipe progression pertencem ao mod e seus datapacks.
Scripts KubeJS não devem substituir recipes oficiais sem decisão explícita; upgrades precisam ser testados against JEI/recipe manager real.

## 13. Soul Elytra
Deeper and Darker inclui **Soul Elytra**. Como o pack possui Curios/slot-related mods e outras elytra integrations, evitar equip state duplicado.
Existe histórico de issue da 1.4 envolvendo Elytra Slot e field de cooldown; tratar como compatibility regression evidence, não como prova de falha universal da 1.4.1 sem reproduzir no pack atual.

## 14. Sonorous Staff e enchantments
A 1.4 adiciona **Volume** e **Reverberation** para o Sonorous Staff e torna outros enchantments obtíveis por rotas específicas. Catalysis/Sculk Smite/Volume/Reverberation continuam sob loot/enchant authority do mod/Minecraft.
Integrações mágicas devem observar dano/range final e não adicionar uma segunda aplicação do mesmo enchant effect.

## 15. Ancient Compass
A 1.4 adiciona **Ancient Compass** para apontar a Ancient Temples. O compass é ferramenta de localização; não deve gerar/relocalizar estrutura que já foi decidida pelo worldgen.
Em mundos existentes, validar comportamento em regiões/chunks gerados antes e depois da atualização.

## 16. Configuração
A linha moderna de Deeper and Darker possui configurações para aspectos como chance de fake Ancient Vase, cooldown da Soul Elytra e portal sizing. Valores exatos não são congelados sem leitura da config física atual.
Config server/common é authority de gameplay; alterações devem ser versionadas junto do pack e testadas após restart/reload conforme suporte do mod.

## 17. Worldgen e chunk lifecycle
A Otherside é dimension/worldgen provider. Validar chunk generation, structures, biome distribution, portal destination, chunk unload/reload e server restart.
Não pré-gerar grandes áreas com Chunky até o stack worldgen definitivo estar estabilizado, pois chunks já gerados materializam a configuração daquele momento.

## 18. Crystallized Amber — fix 1.4.1
A release exata **1.4.1** é um minor update que corrige **server crash causado por blocos Crystallized Amber gerados incorretamente**.
Regression gate obrigatório: gerar chunks novos contendo Crystallized Amber, salvar/reabrir e manter dedicated server estável.

## 19. Multiplayer
Dimension travel, portals, structures, mob spawn, loot, inventory e effects são server-authoritative. Transition screen, models, particles e ambient presentation são client-side.
Dois jogadores abrindo/lootando a mesma estrutura/container não podem receber settlement duplicado por race/retry.

## 20. Relação com o addon separado
`darkermagic-1.3.3-1.21.1-ver.b.jar` / Deeper and Darker: Spellbooks é **top-level separado** na modlist e não pertence a esta ficha. Na ordem física atual ele precede Deeper and Darker neste lote. Ele deve consumir conteúdo/IDs do provider sem transferir ownership da Otherside ou dos mobs base.

## 21. Riscos
1. Portal gerar em posição inválida/claustrofóbica.
2. Worldgen antigo e novo criar seams em mundos existentes.
3. Ancient Vase/loot rolar duas vezes por script externo.
4. Sculk Omen chance ser reaplicada por outro system.
5. Sculk Transmitter duplicar item/state ao integrar container modded.
6. Soul Elytra entrar em dois slot systems.
7. Crystallized Amber gerar state inválido — regression 1.4.1.
8. Mob spawn/AI receber double modification.
9. Chunk pregen congelar uma configuração intermediária.
10. Addon Spellbooks ser confundido com conteúdo interno do provider.

## 22. Matriz de testes
1. Dedicated server boot com 1.4.1.
2. Obter Heart of the Deep e criar/usar portal em mundo de teste.
3. Travel Overworld↔Otherside e validar transition/return portal.
4. Gerar cada um dos 4 biomas em chunks novos.
5. Ancient Temple + Ancient Compass + loot/vases.
6. Sculk Omen com/sem Bad Omen e verificar uma única rolagem de vase/spawn.
7. Sculk Transmitter com vanilla container e containers modded selecionados.
8. Resonarium/Warden equipment via recipes/loot reais.
9. Soul Elytra com slot/elytra integrations do pack sem duplicate equip/cooldown.
10. Sonorous Staff + enchantments e multiplayer damage settlement.
11. Crystallized Amber generation + restart — regression 1.4.1.
12. Chunk unload/reload/server restart na Otherside.
13. Chunky pregen pequena somente após worldgen estabilizado.
14. DarkerMagic presente como addon separado, sem registry/ownership collision.

## 23. Evidência
- modlist física atual: Deeper and Darker 1.4.1;
- CurseForge/Modrinth oficiais: Otherside, mobs, blocks, equipment, Client & Server;
- descrição oficial: 4 biomas, Ancient Temple, 100+ blocks, 8 mobs, Warden gear/Soul Elytra/Sculk Transmitter;
- release 1.4: Otherside overhaul, Sculk Omen, Ancient Compass, Transmitter modded containers, Lite, enchantments e portal changes;
- release 1.4.1: fix de server crash por Crystallized Amber mal gerado.

> 🌌 Boundary canônico: **Deeper and Darker owns a Otherside e sua progressão/worldgen**. Addons e scripts devem integrar sem duplicar portal, loot, mobs, storage ou equipment settlement.