# Ice And Fire: Dread Land

## Propriedades do registro

- **Mod:** Ice And Fire: Dread Land
- **Arquivo JAR:** `iceandfire_dreadland-0.1.2.jar`
- **Versão 1.21.1:** `0.1.2`
- **Categoria:** Worldgen, Exploração, Mobs, RPG
- **Decisão:** Opcional
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/iceandfire-dreadland
- **Função:** Addon IAF CE early-alpha com dimensão Dread Land em quatro realms, churches/dungeons, Dread Shards, realm keys, portal e progressão de aventura própria.
- **Dependências:** Ice And Fire Community Edition 2.1.2 + Jupiter 2.3.7, ambos presentes fisicamente conforme exigência oficial.
- **Compatibilidade/Riscos:** EARLY ALPHA: worldgen/save drift, portal/return path, structures/gates incompletos, key/reward dupe/loss, dependency drift e multiplayer progression. A documentação contém contradição sobre completion de Dread Queen/Black Frost; runtime deve decidir.
- **Sobreposição:** Expande especificamente Ice And Fire CE com dimensão/progressão. Não substitui outros worldgens dimensionais. O risco principal é maturidade/world-state, não mera sobreposição temática.
- **Observações:** Main quest oficial atualmente termina na chegada à Dreadland. Docs primeiro dizem Dread Queen/other bosses incompletos e depois dizem Dread Queen + Black Frost concluídos; contradição preservada sem reconciliação artificial.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Ice And Fire: Dread Land 0.1.2 para NeoForge 1.21.1 + documentação oficial de realms/progression/portal e Development Status Early Alpha + revalidação em 12/09/2026. Source exato 0.1.2 não auditado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Dread Land 0.1.2/JAR físicos reconfirmados; 0.1.2 permanece a release 1.21.1 localizada. O arquivo continua classificado como Beta enquanto a documentação mantém Development Status Early Alpha; decisão Opcional e contradição documental sobre bosses foram preservadas.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #318: JAR `iceandfire_dreadland-0.1.2.jar`, mod id `iceandfire_dreadland`, runtime `0.1.2`, SHA-1 `1790b22e21c69485d582b9da50174339a9dc994c`.

<callout icon="⚠️" color="red_bg">
	**MATURIDADE: EARLY ALPHA.** Runtime físico `iceandfire_dreadland-0.1.2.jar`, mod id `iceandfire_dreadland`, versão `0.1.2`, NeoForge 1.21.1. A distribuição classifica o arquivo como Beta, mas a própria documentação declara **Development Status: Early Alpha** e informa conteúdo/bosses/creatures ainda incompletos. A decisão `Opcional` é preservada.
</callout>
## 1. Papel e authority
Dread Land é addon de **Ice And Fire Community Edition** que adiciona uma dimensão de aventura própria, estruturas, dungeons, chaves, portal e uma cadeia de progressão. Ice and Fire CE continua authority dos dragões e estruturas-base reutilizadas; Dread Land é owner de sua dimensão, keys, churches/dungeons, Dread progression e conteúdo exclusivo.
## 2. Dependências físicas
A documentação exige Ice And Fire CE + Jupiter. O pack contém **Ice And Fire CE 2.1.2** e **Jupiter 2.3.7**. A combinação é fisicamente válida, mas maturidade alpha significa que compatibilidade de boot não equivale a estabilidade de world state.
## 3. Quatro regiões da dimensão
A documentação oficial descreve quatro realms/áreas: **Iceland, Fireland, Lightning e Dreadland**. Cada uma possui terrain, structures e creatures próprios; Lightning gera thunderstorms naturalmente. Mudanças de generator/biome/structure data em updates podem alterar somente chunks novos e criar fronteiras visuais em mundos existentes.
## 4. Churches e dungeons
Há três churches temáticas — Ice, Fire e Lightning — com defesa contra Dread creatures e dungeons abaixo delas. A progressão usa o ambiente construído como gate; structure placement, entrances e loot precisam ser testados em worldgen real, não apenas por presença de registry.
## 5. Dread Shards e Gravemaster's Key
O fluxo publicado pede defender as churches, obter **Dread Shards** e trocá-los com pastors por **Gravemaster's Key**. Reward/trade settlement deve ser server-authoritative e exactly-once. Quests externas não devem conceder novamente shard/key por observar o mesmo kill/trade sem deduplicação.
## 6. Realm keys e dragons
Os dungeons levam a desafios com Ice/Fire/Lightning Dragons, concedendo **Iceland Key, Fireland Key e Lightning Key**. As três são combinadas para formar a **Dreadland Key**. O addon integra progressão própria ao combat provider do IAF; death/retry/reconnect não pode duplicar keys nem perder unlock já liquidado.
## 7. Portal e Mausoleum integration
O projeto documenta um **Dread Portal** em frame 5×7 de Dreadstone Bricks ativado com Dreadland Key. A progressão principal também conduz ao **Mausoleum** do Ice and Fire CE, usando Gravemaster's Key para acessar a área profunda/portal. Portal activation precisa validar frame/key no servidor e evitar consumo/activation duplicado por lag.
## 8. Limite atual da main quest
A documentação afirma que a linha principal jogável atualmente **termina ao entrar na Dreadland**. Conteúdo posterior está em desenvolvimento. Quests do modpack não devem construir gates obrigatórios depois desse ponto como se fossem conteúdo estabilizado da 0.1.2.
## 9. Inconsistência upstream sobre bosses
A mesma página contém afirmações conflitantes: primeiro declara que **Dread Queen boss fight, outros bosses e creatures não estão completos**; depois afirma que Dread Queen e sua mount **Black Frost** estão confirmadas como concluídas enquanto os outros três bosses ainda nem começaram design. Esta ficha **não reconcilia a contradição**. Runtime testing/JAR inspection é necessário antes de tratar qualquer boss como conteúdo concluído.
## 10. Client / server
A distribuição é Client & Server. Servidor deve ser authority de dimension travel, portal activation, key/reward state, trades, boss/entity state, structures e worldgen. Cliente apresenta assets, particles, sounds, UI e dimensão. Mismatch de dimension/registry data entre lados é hard failure.
## 11. Lifecycle
Validar world creation, first dimension entry, return portal/path, player death dentro da dimensão, reconnect, dimension transfer, chunk unload, structure discovery, boss encounter, key acquisition, server restart e update de versão. Um update alpha pode mudar generator/structures/IDs e exige backup antes de abrir o mundo principal.
## 12. Multiplayer
Dois jogadores podem defender a mesma church, lutar contra o mesmo dragon/boss e disputar reward/gates. Shard/key ownership, pastor trades e portal consumption precisam ser determinísticos. World progression compartilhada não deve accidentalmente conceder personal unlock duplicado nem bloquear jogadores posteriores.
## 13. Riscos técnicos
- early-alpha worldgen/data drift;
- dimensão/portal impedir retorno ou gerar state inválido;
- structure placement incompleto/entrance inacessível;
- keys/rewards duplicados ou perdidos em retry/reconnect;
- dependency/API drift com IAF CE/Jupiter;
- boss/content documentation inconsistente;
- chunks antigos e novos divergirem após update;
- quest pack exigir conteúdo ainda não implementado;
- save incompatibility após mudanças alpha;
- multiplayer progression attribution incorreta.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com Dread Land 0.1.2 + IAF CE 2.1.2 + Jupiter 2.3.7.
- [ ] Mundo novo gera Iceland/Fireland/Lightning/Dreadland conforme esperado.
- [ ] Três churches e dungeons têm entrada/progressão utilizável.
- [ ] Dread Shards e pastor trade concedem Gravemaster's Key uma vez.
- [ ] Ice/Fire/Lightning dragon encounters concedem realm keys sem dupe/loss.
- [ ] Craft/obtenção da Dreadland Key funciona e persiste em relog.
- [ ] Portal 5×7 ativa uma vez, permite entrada e caminho de retorno seguro.
- [ ] Mausoleum integration não quebra estrutura-base do IAF CE.
- [ ] Verificar runtime real de Dread Queen/Black Frost antes de criar quest obrigatória.
- [ ] Death/reconnect/restart dentro da dimensão não corrompem player/world state.
- [ ] Backup/cópia de mundo antes de qualquer update 0.1.x.
## 15. Decisão operacional
**Opcional / ambiente de teste.** A razão não é incompatibilidade conhecida com a modlist física, mas maturidade declarada pelo próprio upstream. Para pack principal conservador, não transformar a progressão de Dread Land em requisito irremovível até que dimension/worldgen/boss lifecycle seja validado e o upstream estabilize o conteúdo.
## 16. Evidências e limites
- **Modlist física:** `iceandfire_dreadland-0.1.2.jar`; IAF CE 2.1.2 e Jupiter 2.3.7 presentes.
- **CurseForge oficial:** 0.1.2 Beta, NeoForge 1.21.1, Client & Server, status textual Early Alpha, 4 realms, churches/dungeons, keys, portal e quest flow.
- **Conflito documental preservado:** Dread Queen/Black Frost são descritos de modo inconsistente na mesma documentação.
- **Limite:** source exato 0.1.2 não foi localizado/auditado; registry IDs, generator internals e boss completion real permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
