# Bosses'Rise

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81708464c12d7d7b5123
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Bosses'Rise
- **Arquivo JAR:** `block_factorys_bosses-2.1.2-neo-1.21.1.jar`
- **Versão 1.21.1:** 2.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, RPG, Worldgen, Exploração
- **Função:** Conteúdo Souls-like com 5 bosses multifase, arenas/dungeons próprias, mobs regionais, equipamento/recompensas, cinematics, roll/config e estruturas protegidas.
- **Dependências:** GeckoLib é required content na release NeoForge atual; pack usa GeckoLib 4.9.2, acima do requisito publicado 4.8.4.
- **Sobreposição:** Sobreposição temática e de densidade de encontros com outros boss mods; não há duplicação técnica automática. Auditar structures, loot progression e dificuldade por encontro.
- **Compatibilidade/Riscos:** Sobreposição de bosses/worldgen/dificuldade com Cataclysm, Mowzie's Mobs e BOMD; riscos em boss structure density, cinematic state, multiplayer AoE, dedicated-server GeckoLib/particle rendering e Distant Horizons. 2.1.2 melhora compat Better Combat e corrige DH/Dragon Tower.
- **Observações:** Bosses confirmados publicamente: Ashlord/Infernal Dragon, Helvar/Underworld Knight, Skor/Yeti, Sirok/Sandworm e Kraken. 2.1.x adiciona Kraken content, decorações navais, server-side config, structure protection, boss loot tags e fixes multiplayer/cinematics.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Bosses'Rise 2.1.2 + changelogs oficiais 2.0.10–2.1.2 + GeckoLib 4.9.2 físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bossesrise
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Bosses'Rise 2.1.2 físico/release confirmado; 5 bosses, arenas, server config, cinematics, Distant Horizons/Dragon Tower e multiplayer risks preservados. Runtime QA não executado.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `block_factorys_bosses-2.1.2-neo-1.21.1.jar`, mod id `block_factorys_bosses`, runtime `2.1.2`, NeoForge 1.21.1. A release atual exige **GeckoLib 4.8.4+**; o pack possui GeckoLib `4.9.2`.

## 1. Papel e autoridade
Bosses'Rise adiciona encontros **Souls-like** com bosses multifase, arenas próprias, loot/equipamentos, mobs regionais e cinematics. O mod é authority de seus bosses, fases, estruturas, recompensas e abilities próprias.

Epic Fight/Better Combat/outros combat mods podem alterar a apresentação ou pipeline de ataque do jogador, mas não devem reexecutar boss attacks, loot ou phase transitions.

## 2. Boss roster confirmado — 5 encontros
A documentação oficial afirma **5 bosses**. Evidência pública atual permite identificar:
1. **Ashlord, the Infernal Dragon**;
2. **Helvar, the Underworld Knight**;
3. **Skor, the Yeti**;
4. **Sirok, the Sandworm**;
5. **Kraken**.

O projeto descreve cada boss com arena própria, moveset distinto e mudanças de fase. A ficha não inventa nome próprio adicional para o Kraken porque a evidência consultada não o fornece.

## 3. Fases e combat state
Bosses multifase exigem state machine server-authoritative. Regras:
- transição de fase ocorre uma vez ao atingir a condição prevista;
- cinematic/VFX não deve disparar a transição novamente;
- reconnect/chunk unload não pode restaurar fase anterior indevidamente;
- damage immunity temporária precisa limpar no momento correto.

Changelogs anteriores confirmam correção de Skor ficando preso como imune a dano, evidenciando que phase/immunity state é uma superfície real de risco.

## 4. Ashlord — Infernal Dragon
Ashlord é o boss dragão/infernal do projeto. Changelogs atuais citam:
- correção de ataques que não usavam corretamente o attack damage configurado em versões anteriores;
- 2.1.2 corrige aparência/glow no escuro;
- **Dragon Tower** é estrutura associada ao conteúdo do dragão.

Attack damage deve vir da config/provider do boss, não ser reescrito por integração global sem deduplicação.

## 5. Helvar — Underworld Knight
Helvar é o Underworld Knight. Changelogs oficiais confirmam que a entidade participa de targeting/hostility normal e já recebeu correções para ser reconhecida como inimigo viável por outras entidades.

Isso é relevante para mods de AI/targeting: não criar target goal paralelo apenas para “fazer o boss funcionar” se o provider já expõe hostility correta.

## 6. Skor — Yeti
Skor é o Yeti. A linha 2.1.2 cita **Skor Gauntlet** e corrige ability que empurrava todos os jogadores próximos em vez de somente o usuário.

Contrato multiplayer:
- item/ability deve manter owner correto;
- AoE de uso próprio não pode usar seleção global de players por engano;
- boss state e item ability são sistemas distintos apesar da mesma temática.

## 7. Sirok — Sandworm
Sirok é o Sandworm. Changelogs oficiais confirmam:
- comportamento de emergir do solo;
- correções de render após emergir;
- ataques ligados ao attack damage configurado;
- **Sandworm Gauntlet**, classificado na 2.1.2 também na tag comum de ranged weapon.

Movimento subterrâneo/emergence exige validar collision, chunk borders e target tracking em multiplayer.

## 8. Kraken
Kraken foi incorporado à linha 2.1.x junto de conteúdo naval e arena/encounter próprio. Changelogs confirmam dedicated-server fixes durante a luta e conteúdo de loot/abilities como **Undying Tentacle**.

A 2.1.0 adicionou também decoração naval associada ao conteúdo:
- dry/wet planks com variantes;
- ship guardrail fence e variante diagonal;
- piles de coins/planks;
- cannonball;
- ship lantern;
- rope roll;
- ship steering wheel;
- nets escaláveis;
- ship anchor;
- stack of crates;
- Kraken Tooth.

Esses itens/blocos/entidades decorativas integram a arena/tema, mas não devem ser contados como bosses adicionais.

## 9. Arenas e estruturas protegidas
O projeto gera bosses em estruturas próprias. A linha 2.0.13 adicionou tag `block_factorys_bosses:protection_exempt` para identificar blocos que podem ser colocados/quebrados dentro de estruturas protegidas.

Regras de integração:
- claim/protection mods precisam compor com essa proteção sem criar bypass global;
- gravestones e utilidades devem ser testadas dentro das arenas;
- estrutura não deve ser regenerada em chunk load;
- loot containers pertencem à estrutura/provider.

## 10. Dragon Tower e Distant Horizons
A 2.1.2 corrige erros logados quando **Distant Horizons gera chunks contendo Dragon Tower**. O pack possui Distant Horizons 3.2.0-b, portanto essa compatibilidade é diretamente relevante.

Testar:
- worldgen normal;
- DH distant generation;
- entrada posterior no chunk em full detail;
- ausência de duplicate structure/loot.

## 11. Loot e equipamentos
O projeto oferece dedicated equipment/rewards para bosses. A linha 2.1.1 mudou o glow de boss drops para depender da tag `#block_factorys_bosses:glowing_loot`.

Consequência: glow é data/tag-driven e não deve ser hardcodado por nome de item em compat própria.

Itens/abilities publicamente confirmados nos changelogs recentes incluem:
- Undying Tentacle;
- Skor Gauntlet;
- Sandworm Gauntlet;
- Kraken Tooth.

A ficha não afirma que esta lista é o registry completo de equipamentos.

## 12. Roll e configuração
A linha 2.1.0 moveu configuração comum para **server-side config**, com overrides por mundo e opções de atributos dos bosses/roll. Isso corrige casos em que players podiam receber roll mesmo em servidor que o desativava.

Regra: servidor é authority de habilitação, boss attributes e roll eligibility. Cliente pode renderizar input/cooldown, mas não conceder a ability.

## 13. Cinematics
Bosses'Rise possui sistema de cinematics para encontros. Releases recentes:
- reworkaram o sistema por performance;
- corrigiram cinematics que não iniciavam/terminavam corretamente;
- corrigiram casos dependentes de framerate baixo.

Cinematic deve ser presentation/state sync, não authority de phase settlement. Um cliente com FPS baixo não pode travar o boss state do servidor.

## 14. Particles e GeckoLib
O mod usa GeckoLib para animações; 2.1.2 otimiza particle rendering e reduz risco de concurrent modification durante model/particle loading. Versões anteriores corrigiram crashes relacionados a GeckoLib e Kraken em dedicated servers.

Regras:
- animation callbacks não devem duplicar damage;
- particles são visual;
- entity/boss state é servidor;
- dedicated server precisa evitar classloading visual indevido.

## 15. Compatibilidade de combate
A 2.1.2 melhora explicitamente compatibilidade com **Better Combat**. O pack utiliza Epic Fight como combat provider principal; não inferir que Better Combat compat implica compat automática com Epic Fight.

Para Epic Fight, testar hits, dodge/roll, weapon animations e boss telegraphs em runtime real.

## 16. Multiplayer
Pontos críticos:
- arena com vários jogadores;
- target swap;
- boss phase compartilhada;
- abilities de item afetando apenas owner/alvos válidos;
- death/respawn durante encounter;
- player entrando depois da cinematic;
- boss loot e advancements exatamente uma vez por regra do provider.

## 17. Worldgen e densidade de bosses
Como cada boss possui arena/dungeon, o mod compete pelo espaço de estruturas com Cataclysm, Mowzie's Mobs, Bosses of Mass Destruction e outros providers de exploração do pack.

Sobreposição temática não exige remoção. O que deve ser medido é:
- spacing/separation;
- densidade por região;
- loot progression;
- dificuldade relativa;
- geração em chunks já explorados vs novos.

## 18. Riscos
1. Phase transition duplicada.
2. Boss immunity persistente após transição/reload.
3. Cinematic client-side bloquear state server-side.
4. Ability de item afetar todos players por owner incorreto.
5. GeckoLib/particle classloading em dedicated server.
6. Distant Horizons gerar/logar estruturas de forma divergente.
7. Loot/advancement duplicado por evento externo.
8. Structure protection conflitar com claims/gravestones.
9. Epic Fight alterar timing/hit settlement de modo incompatível.

## 19. Matriz de testes
1. Dedicated server boot com GeckoLib 4.9.2.
2. Encontrar/gerar as cinco arenas em mundo de teste.
3. Cada boss: phase transition, death, loot e restart durante luta.
4. Dois ou mais jogadores no encounter.
5. Skor/Undying Tentacle abilities: apenas owner/alvos esperados.
6. Sirok emergence em chunk border.
7. Kraken em dedicated server.
8. Dragon Tower com Distant Horizons ativo.
9. Cinematics em FPS baixo/alto e reconnect.
10. Structure protection com gravestone/claim providers atuais.
11. Epic Fight: attacks/dodge/hit windows sem double-damage.
12. Server config/override por mundo para roll e boss attributes.

## 20. Evidência
- modlist física atual: Bosses'Rise 2.1.2 e GeckoLib 4.9.2;
- CurseForge/Modrinth oficiais 2.1.2;
- descrição oficial: 5 bosses, arenas, phases, loot e mobs;
- changelogs oficiais 2.0.10–2.1.2 para Ashlord, Helvar, Skor, Sirok, Kraken, structure protection, server configs, cinematics, loot tags, Distant Horizons e multiplayer fixes.

> 👑 Conteúdo confirmado: cinco encounters — Ashlord, Helvar, Skor, Sirok e Kraken — com arenas e progressão próprias. A ficha enumera nomes/itens apenas onde a documentação oficial os confirma e não fabrica registry counts ausentes.
