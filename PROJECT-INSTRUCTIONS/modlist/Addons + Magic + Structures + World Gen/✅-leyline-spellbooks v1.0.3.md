# Leyline Spellbooks

## Propriedades do registro

- **Mod:** Leyline Spellbooks
- **Arquivo JAR:** leylines-1.0.3.jar
- **Versão 1.21.1:** 1.0.3
- **Categoria:** Magia, RPG, Exploração, Worldgen
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/leyline-spellbooks
- **Função:** Addon de Iron's Spells com escola Leyline, pillars noturnos, Leyline Rifts com encontros em ondas, spells próprios, Codex/Staff e recompensas temáticas.
- **Dependências:** Iron's Spells 'n Spellbooks é dependência obrigatória; runtime físico usa 3.16.3. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Sem source público exato localizado. A release 1.0.3 documenta arena/fracasso: abandono >60 blocos derruba o Rift, mob a >40 blocos é recolocado na arena, morte implica derrota sem loot e boss bar é limpa em death/respawn/logout/dimensão. Riscos adicionais: worldgen density/overlap, rift/wave duplication após reload, portal persistence, reward/XP duplication, spell-provider drift com Iron's 3.16.3 e efeitos temporais em multiplayer.
- **Sobreposição:** Expande Iron's Spells; Iron's permanece authority de mana/cast/cooldown e spell engine. Leyline é authority de seus pillars, rifts, encounters, spells/conteúdo e rewards próprios.
- **Observações:** Release 1.0.3 confirmada para 1.21.1. Conteúdo nominal documentado oficialmente foi catalogado; classes, registry IDs e contagens internas não foram inventados sem source correspondente.
- **Procedência:** Snapshot físico 25/09/2026 + CurseForge oficial de Leylines **File ID 8565076** (release 1.0.3 NeoForge 1.21.1, 02/08/2026), descrição oficial e changelog de arena/encounter da própria 1.0.3; source público exato continua não localizado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/10/2026 — snapshot histórico 25/09/2026 registra Leyline Spellbooks **1.0.3**; CurseForge File **8565076** continua última release NeoForge 1.21.1. Changelog 1.0.3 de fail rules, leash 40 blocos, abandon 60 blocos, boss bar e comando OP `spawnpillar` auditado na seção 16. Sem source/bytecode exato ou QA runtime executado.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #373: JAR `leylines-1.0.3.jar`, mod id `leylines`, runtime `1.0.3`, SHA-1 `dfa6908731f432905caaaa1e53b4aedeaa26ed59`.

> **ESCOPO CANÔNICO.** Último snapshot físico verificado (25/09/2026): `leylines-1.0.3.jar`, mod ID `leylines`, versão 1.0.3. É addon de Iron’s Spells com Leyline magic, pillars noturnos, Leyline Rifts com ondas, spells e recompensas. A auditoria de 14/10/2026 acrescenta as regras de arena/falha da release oficial 1.0.3, sem inspecionar novamente o binário instalado.
## 1. Identidade e autoridade de versão
A modlist física e a publicação oficial convergem em `leylines-1.0.3.jar`, release para NeoForge 1.21.1. A página oficial declara Iron's Spells 'n Spellbooks como requisito. Não foi localizado source público correspondente à 1.0.3 nesta auditoria; por isso classes, IDs internos e registries não são presumidos.
## 2. Papel e ownership
Leyline Spellbooks expande o ecossistema de Iron's com uma escola temática própria e um loop exploração→pillar→rift→combate→recompensa. Iron's continua authority do spell engine, mana, cast lifecycle, cooldowns e infraestrutura base; Leyline Spellbooks é authority apenas do conteúdo que adiciona e de seu world/event loop.
## 3. Leyline pillars
A descrição oficial informa que pillars de Leyline surgem durante a noite. O jogador encontra e carrega esses pontos para abrir um Leyline Rift. Spawn/generation, charge state e eligibility para abertura precisam ser decididos pelo servidor. Render/partículas não provam que o pillar esteja carregado.
## 4. Leyline Rifts e encontros em ondas
Rifts iniciam encontros wave-based. A publicação menciona variações como amethyst surges, void tears e chaotic echoes. O lifecycle crítico é open→waves→collapse/completion→reward. Relog ou chunk unload não pode reiniciar ondas concluídas nem gerar duas recompensas para o mesmo rift.
## 5. Recompensas e progressão
Completar os encontros concede loot temático, experiência e chance de um **Ley Crystal** raro. Loot/XP devem ser concedidos exatamente uma vez pela conclusão server-side. Quests ou scripts externos devem escutar o evento causal adequado e não duplicar a recompensa simplesmente ao detectar mobs mortos ou o fechamento visual do rift.
## 6. Conteúdo mágico documentado
A página oficial lista spells como **Blink Step**, **Rift Gate**, **Chrono Tether**, **Temporal Stutter**, **Fissure** e **Anchor Recall**, além de Beam, Ley Blast, Eclipse e outros. Também documenta **Leyline Codex** e **Ley Staff**. Sem source exato, não são afirmados registry IDs, valores, níveis máximos ou fórmulas não publicados.
## 7. Rift Gate e persistência espacial
Rift Gate cria dois portais ligados segundo a descrição oficial. Pairing, endpoint validity, dimension/chunk state e cleanup precisam permanecer server-authoritative. Testar especialmente logout entre endpoints, chunk unload e remoção/expiração para evitar portal órfão ou duplicado.
## 8. Spells temporais e targeting
Chrono Tether/Temporal Stutter indicam efeitos de controle temporal/movimento. O efeito real aplicado a entidades deve vir do servidor/Iron's. Client animation, slowdown visual ou particles não devem ser usados por integrações próprias como prova de efeito confirmado.
## 9. Integração com Iron's 3.16.3
O pack físico contém Iron's Spells 'n Spellbooks 3.16.3. A publicação confirma a dependência funcional, mas sem source exato da 1.0.3 não foi possível demonstrar a API revision contra a qual o addon foi compilado. Compatibilidade prática precisa ser testada em startup, registry, casting, loot e multiplayer.
## 10. Worldgen no pack amplo
O mod adiciona conteúdo de worldgen/structures a um pack já denso. Pillars precisam coexistir com Tectonic/WorldWeaver, BetterEnd/BetterNether e múltiplos mods de estruturas sem densidade anormal, overlap impossível ou geração quebrada. Essa é avaliação runtime; não se infere conflito apenas pela presença conjunta.
## 11. Client / server boundary
Servidor: generation, pillar charge, rift state, wave spawning/completion, damage/effects, spell acceptance, loot, XP e item ownership. Cliente: modelos, GUI, particles e outros sinais visuais. Iron's permanece o provider do cast/mana/cooldown base.
## 12. Lifecycle e multiplayer
Validar night spawn, charging, rift open/close, save/restart, chunk unload, player death/relog durante wave e entrada de vários jogadores no mesmo encontro. O rift deve manter um único state compartilhado e não produzir wave/reward por cliente.
## 13. Riscos técnicos
1. **Rift duplication** após unload/relog.
2. **Reward/XP duplication** por múltiplos completion listeners.
3. **Worldgen density/overlap** com o stack de structures/biomes.
4. **Portal endpoint orphaning** em Rift Gate.
5. **Spell-provider drift** com Iron's 3.16.3.
6. **Temporal-effect desync** em multiplayer.
7. **Registry/ID uncertainty** devido ao source gap.
8. **Quest double-credit** se kill/visual closure substituir completion real.
## 14. Matriz de testes
- [ ] Dedicated server inicia com Leyline Spellbooks 1.0.3 + Iron's 3.16.3.
- [ ] Pillars surgem segundo as condições publicadas sem densidade anormal.
- [ ] Charge state persiste conforme esperado após chunk unload/reload.
- [ ] Um Rift executa cada wave uma única vez.
- [ ] Relog/restart durante encounter não duplica mobs ou recompensa.
- [ ] Loot, XP e Ley Crystal são concedidos somente pelo completion real.
- [ ] Rift Gate cria/limpa endpoints sem portais órfãos.
- [ ] Spells listados registram e castam sem missing IDs/API errors.
- [ ] Temporal spells mantêm resultado consistente entre clientes.
- [ ] Quests/addons não duplicam reward ou mastery.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
A ficha usa a modlist física e a página/release oficial 1.0.3, incluindo a descrição nominal de pillars, rifts, encounters, rewards, Codex/Staff e spells. Nenhum repositório público correspondente à build 1.0.3 foi localizado; consequentemente detalhes internos não publicados permanecem desconhecidos e não foram preenchidos por inferência.

## 16. Reauditoria de Leylines 1.0.3: contratos de arena, derrota, comando OP e limites — 14/10/2026

### 16.1. Versão oficial, snapshot e ausência de source

- **Físico histórico #373 (25/09/2026):** `leylines-1.0.3.jar`, mod ID `leylines`, versão `1.0.3`, SHA-1 `dfa6908731f432905caaaa1e53b4aedeaa26ed59`. Nenhum novo JAR foi aberto e nenhum SHA atual foi calculado; a metadata real do arquivo em uso em outubro permanece não revalidada.
- **Última publicação compatível verificada:** [CurseForge **File ID 8565076**](https://www.curseforge.com/minecraft/mc-mods/leylines-irons-spells-n-spellbooks-addon/files/8565076), **02/08/2026**, release `1.0.3`, filename `leylines-1.0.3.jar`, **Minecraft 1.21.1, NeoForge**, ambiente **Client & Server**. O [índice oficial](https://www.curseforge.com/minecraft/mc-mods/leylines-irons-spells-n-spellbooks-addon/files/all) mostra quatro versões (`1.0.0`–`1.0.3`) nessa mesma linha, sem release posterior localizada em 14/10/2026. Filename coincide com o snapshot; **identidade binária com o File ID não verificada por hash**.
- **Licença:** [projeto oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/leylines-irons-spells-n-spellbooks-addon) declara **All Rights Reserved**; não pressupor autorização de redistribuição/derivados por constar no modpack. **Source oficial público correspondente à 1.0.3 não localizado**. Logo, classes, mixins, registries, IDs, codecs, network packets e algoritmos internos não podem ser descritos como source-verified.
- **Hard dependency explicitamente publicada:** **Iron's Spells 'n Spellbooks**; no snapshot **#342 `irons_spellbooks-1.21.1-3.16.3.jar`**, mais NeoForge MC 1.21.1. O range exato de compatibilidade de Iron's e dependências transitivas deve ser inspecionado em `neoforge.mods.toml` do binário 1.0.3 antes de trocar qualquer provider.

### 16.2. Changelog 1.0.3: regras concretas do encontro — fonte autoritativa de publicação

O [changelog oficial da release 1.0.3](https://www.curseforge.com/minecraft/mc-mods/leylines-irons-spells-n-spellbooks-addon/files/8565076) declara:

| Evento | Regra confirmada pelo autor | Consequência arquitetural/teste |
| --- | --- | --- |
| **Morte durante Rift ativo** | Morrer durante o encontro causa **falha imediata**, não um clear bem-sucedido; **nenhuma recompensa de conclusão nem Ley Crystal**. | O evento de morte do jogador não pode disparar `rift_complete`/recompensa/mastery; não tratar desaparecimento de UI ou mobs como vitória |
| **Boss bar** | Barra de waves limpa em **morte, respawn, logout e troca de dimensão**. | Limpeza de UI no cliente não é garantia de conclusão no servidor; testar reentrada/dimension transition para stale overlay |
| **Abandono do encontro** | Distância **maior que 60 blocos** de um Rift ativo provoca **colapso imediato**. | Validar boundary de arena, teleport, movimento, Elytra/portal e falha sem reward; não confundir com 60 blocos inclusivos |
| **Leash de mobs de waves** | Mobs da arena que se afastam **mais de 40 blocos** do Rift são puxados de volta. | Testar pathfinding, mob ownership, teleport de retorno, desync MP e prevenção de boss-cheese |
| **Persistência da wave** | Mobs de wave não desaparecem simplesmente porque o participante morre brevemente; evita falso clear. | Verificar que mob persistente não significa encounter concluído, nem mobs/recompensas duplicados após rejoin/unload |
| **Comando administrativo** | `/leylines spawnpillar` (permission **nível 2**) posiciona pillar **temporário** aleatoriamente próximo; `/leylines spawnpillar here` tenta primeiro a coluna atual. | Comando **para OP/debug**; não tratá-lo como garantia de geração natural nem liberar a usuários comuns; usar só em mundo de teste |

**Nota de precisão:** os limiares **40**/**60 blocos**, o tipo de falha, a limpeza da UI e a sintaxe do comando são claims **do changelog oficial 1.0.3**, não descompilação do JAR. Não derivar persistência cross-restart, algoritmo de seleção de mob, escala de ondas ou permissão adicional sem runtime/source exato.

### 16.3. Worldgen, spells e lifecycle no pack

A [descrição oficial do autor](https://www.curseforge.com/minecraft/mc-mods/leylines-irons-spells-n-spellbooks-addon) confirma o loop **pillar noturno → carregamento → rift → ondas → derrota/completion → XP/loot/Ley Crystal**, com variantes de rifts `amethyst surges`, `void tears` e `chaotic echoes`. Nomeia **Leyline Codex**, **Ley Staff**, e spells **Blink Step, Rift Gate, Chrono Tether, Temporal Stutter, Fissure, Anchor Recall, Beam, Ley Blast e Eclipse**. Isso é uma descrição **nominal**; não basta para inferir `ResourceLocation` exata, tier, cooldown, dano, duration, registry ID ou internals.

**Ownership:** Iron's #342 permanece provider de casting, spell engine e mana/cooldown; Leylines #373 define seu próprio rift/arena/reward loop. O servidor deve ser autoridade sobre conclusão/fracasso, charge state, loot, mob encounters, teleport e spell effects. Integrações de RPG Skill Tree/quests devem creditar mastery/recompensa apenas a um **resultado server-authoritative confirmado**, distinguindo `death`, `abandon`, `wave_leash` e `completed`. A nomenclatura desses estados é descritiva, **não são eventos/API identificados no JAR**. Uma perk de mobilidade que salte mais de 60 blocos é candidata a provocar derrota por abandono; o efeito real de qualquer skill deve ser validado in-game.

### 16.4. QA ainda não executado — promoção fail-closed

- [ ] Reabrir `leylines-1.0.3.jar` atual: SHA-1/SHA-256, metadata de NeoForge, `mod_version`, mixins, required deps, tags, registries, structures e configs. Confirmar os hashes e o match com File ID `8565076`.
- [ ] Cold boot de client e dedicated server com Iron's `1.21.1-3.16.3`, mais integração real com resource/data packs da instância. Sem source, não declarar API estável.
- [ ] Em perfil/mundo de teste, gerar pillars **naturalmente à noite** e pela rota **`/leylines spawnpillar`/`... here`** com OP nível 2, verificando que usuários não-OP não invocam o comando.
- [ ] Comparar bossbar/state/XP/loot em encounter vencido, jogador morto, jogador que sai a **60 vs 61 blocos**, logout/respawn/dimension change, garantindo **zero crystal/recompensa** em falha.
- [ ] Mover mobs para **40 vs 41 blocos** e verificar reposicionamento sem spawn extra, loot ou AI loop; depois matar jogador durante wave e confirmar que a persistência dos mobs não cria false-clear nem entitlement de recompensa.
- [ ] Reproduzir relog, restart, unload/reload de chunks e vários jogadores no mesmo Rift; certificar um único state do encounter e um único pay-out, sem duplicação de wave ou XP.
- [ ] Testar `Rift Gate` com dois endpoints, destruição/descarregamento de chunk, portal em dimensão, reload, cleanup e permissions; e spells de tempo/movimento sem client-side desync.
- [ ] Verificar worldgen density de pillars/structures em chunks novos e coexistência com biomas/estruturas do pack, sem atribuir conflitos inexistentes.
- [ ] Para futuras perks, citar **Leylines #373 1.0.3** + **Iron's #342 3.16.3**, inspecionar eventos reais do JAR e não usar partícula/bossbar como prova de vitória, cooldown ou grant de loot.

**Decisão registrada:** **Sem decisão**, preservada. A versão 1.0.3 continua documentada como instalada no snapshot e mais recente para MC 1.21.1; nenhum update físico recomendado apenas por data. Modlist, JARs, scripts, configs, datapacks, shaders, resource packs e saves **inalterados**. Nenhum teste runtime foi executado nesta reauditoria documental.
