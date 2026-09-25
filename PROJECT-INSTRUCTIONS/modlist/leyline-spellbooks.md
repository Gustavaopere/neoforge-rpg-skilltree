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
- **Compatibilidade/Riscos:** Sem source público exato localizado. Riscos: worldgen density/overlap, rift/wave duplication após reload, portal persistence, reward/XP duplication, spell-provider drift com Iron's 3.16.3 e efeitos temporais em multiplayer.
- **Sobreposição:** Expande Iron's Spells; Iron's permanece authority de mana/cast/cooldown e spell engine. Leyline é authority de seus pillars, rifts, encounters, spells/conteúdo e rewards próprios.
- **Observações:** Release 1.0.3 confirmada para 1.21.1. Conteúdo nominal documentado oficialmente foi catalogado; classes, registry IDs e contagens internas não foram inventados sem source correspondente.
- **Procedência:** modlist.txt física reconferida em 13/09/2026 + CurseForge oficial Leylines 1.0.3 NeoForge 1.21.1, ainda a release mais recente localizada + descrição oficial do conteúdo; source público exato continua não localizado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Leyline Spellbooks 1.0.3/JAR físico reconfirmado; 1.0.3 permanece a release NeoForge 1.21.1 mais recente localizada. Pillars/rifts, wave encounters, spells, rewards, Iron's boundary e source gap preservados.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #373: JAR `leylines-1.0.3.jar`, mod id `leylines`, runtime `1.0.3`, SHA-1 `dfa6908731f432905caaaa1e53b4aedeaa26ed59`.

<callout icon="🔮" color="purple_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `leylines-1.0.3.jar`, mod id `leylines`, versão `1.0.3`. É um addon de **Iron's Spells 'n Spellbooks** que introduz magia Leyline, estruturas/pillars de exploração, Leyline Rifts, encontros em ondas, spells e recompensas próprias.
</callout>
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
