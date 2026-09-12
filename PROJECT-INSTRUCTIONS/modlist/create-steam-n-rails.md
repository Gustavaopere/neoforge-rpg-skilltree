# Create: Steam 'n' Rails 1.21.1

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81328636f4e453095757
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Steam 'n' Rails 1.21.1
- **Arquivo JAR:** `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar`
- **Versão 1.21.1:** 0.3.0-beta.2+neoforge-mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Automação
- **Função:** Port/addon de Create: Steam 'n' Rails com expansão de trens, bogeys, sinais, estações e conteúdo ferroviário.
- **Dependências:** Create 1.21.1/6.x. Projeto é port NeoForge 1.21.1 da linha Steam 'n' Rails; Create permanece authority do train graph/schedules/stations base.
- **Sobreposição:** Expande o sistema ferroviário Create; não substitui Create base. Sobreposição com outros addons de tracks deve ser avaliada por feature/recipe/track ID.
- **Compatibilidade/Riscos:** Port comunitário Beta. Riscos: Create API drift, bogey render/alignment, Jukebox Minecart lifecycle, recipe/tag drift, overlap com outros track addons e client/server mismatch. 0.2.1 Release permanece apenas fallback de teste, não downgrade automático.
- **Observações:** Runtime 0.3.0-beta.2+neoforge-mc1.21.1. Linha 0.3.0 contém fixes posteriores à 0.2.1, incluindo startup com Create moderno, Jukebox Minecart, recipes e bogeys.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + publicação/changelog do port Steam 'n' Rails NeoForge 0.3.0-beta.2 + Create físico atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/steam-n-rails-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Steam 'n' Rails NeoForge 0.3.0-beta.2 reconstruído: port boundary, bogeys/railway expansion, Create 6.x compat, Jukebox Minecart/recipes, lifecycle, riscos e testes; Manter preservado.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em Manter. A linha beta 0.3.0 possui correções concretas posteriores à Release 0.2.1, inclusive compatibilidade de startup com Create recente; risco de prerelease documentado.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar`, mod id `railways`, versão `0.3.0-beta.2+neoforge-mc1.21.1`, NeoForge 1.21.1. Esta é a linha comunitária NeoForge de Create: Steam 'n' Rails para o stack Create 6.x do pack. A decisão anterior **Manter** é preservada: a build é Beta, mas contém correções relevantes posteriores à linha estável 0.2.1. Create continua sendo a authority do sistema ferroviário-base.

## 1. Identidade e papel
- **Mod:** Create: Steam 'n' Rails 1.21.1.
- **JAR:** `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar`.
- **Mod id:** `railways`.
- **Runtime:** `0.3.0-beta.2+neoforge-mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Papel:** expansão ferroviária para Create, adicionando bogeys, variantes/infraestrutura de trilhos e recursos operacionais/decorativos relacionados a trens.
- **Decisão:** Manter.

## 2. Port comunitário e boundary
A linha instalada é um port NeoForge 1.21.1 do ecossistema Steam 'n' Rails. Isso exige separar três autoridades:
- Create → train graph, schedules, stations, contraption/train state base;
- Steam 'n' Rails → conteúdo/behavior ferroviário adicional;
- port NeoForge → adaptação da linha ao loader/versão atual.
Bug de trem-base não deve ser atribuído automaticamente ao addon.

## 3. Bogeys
Steam 'n' Rails amplia a variedade e apresentação de bogeys. Bogey é superfície crítica porque participa de assembly/render do trem e precisa permanecer alinhado à orientação/trilho.
Testar variantes, mudança de direção, curvas, portals/dimension transitions suportadas pelo Create e montagem/desmontagem.

## 4. Infraestrutura ferroviária
O projeto expande o ecossistema de tracks, sinalização e elementos de estação/linha. O catálogo deve tratar esses elementos como extensões do sistema de trens do Create, não como uma segunda engine ferroviária.
Recipes e tags precisam ser revalidados após update de Create ou do port.

## 5. Conteúdo operacional e decorativo
A linha Steam 'n' Rails tradicionalmente combina elementos funcionais e decorativos de ferrovia. Uma peça visual não deve ser interpretada como novo state de sinal/rota sem documentação específica.
Em automações, testar somente blocks que realmente expõem comportamento/kinetics/redstone.

## 6. Jukebox Minecart
A linha 0.3.0 inclui correções para problemas/crashes/softlocks ligados a Jukebox Minecart reportados durante o port. Isso deve permanecer regression gate por envolver entidade/minecart, áudio e lifecycle.
Testar spawn, reprodução/parada, chunk unload, trem/minecart state e remoção do veículo.

## 7. Compatibilidade com Create moderno
Releases da linha 0.3.0 corrigiram startup contra versões modernas do Create, incluindo a geração 6.0.x usada pelo pack. Isso sustenta a permanência na linha beta atual em vez de downgrade automático para 0.2.1.
Ainda assim, cada update de Create deve disparar smoke de registration, recipes, bogeys e trains.

## 8. Beta.2
A build física é `0.3.0-beta.2`. O changelog público da linha registra refinamentos/fixes de bogey, incluindo referência incorreta de textura radial e alinhamentos de menu em builds próximas.
Como o detalhe por target nem sempre é publicado isoladamente, esta ficha não atribui a beta.2 um conjunto maior de mudanças sem prova textual específica.

## 9. Stable fallback versus instalada
Havia uma 0.2.1 marcada Release para 1.21.1, enquanto 0.3.0-beta.2 é Beta. A decisão `Manter` já foi tomada porque a 0.3.0 contém fixes necessários para o stack Create atual.
A existência de fallback estável não é motivo para downgrade enquanto o runtime instalado estiver funcional; só deve entrar em ação se regressão concreta da beta justificar teste comparativo.

## 10. Trains e graph authority
Schedule, navigation, stations e train graph continuam sob Create. Steam 'n' Rails pode adicionar peças que interagem com o sistema, mas não deve duplicar ownership do graph.
Quando um trem não encontra rota, diagnosticar track graph/points antes de culpar uma textura/bogey addon.

## 11. Recipes e tags
Ports podem sofrer drift de recipes/tags entre upstream e target NeoForge. Validar JEI, crafting e tags de materiais após `/reload`/update.
Recipe visível no cliente precisa existir de forma equivalente no servidor.

## 12. Client/server e multiplayer
Train state é server-authoritative. Cliente renderiza bogeys, áudio e UI. Testar dois clientes observando o mesmo trem, chunk unload, reconnect e restart.
Não tolerar versão divergente de addon entre cliente/servidor em ambiente auditado.

## 13. Relação com outros addons de tracks
O pack contém outros complementos de Create/trilhos. Sobreposição deve ser classificada por peça/feature, não apenas pelo fato de ambos adicionarem tracks.
Testar recipes duplicados, track material IDs, compat com track-placement UI e train graph.

## 14. Performance
Mais variantes e elementos de train render podem aumentar custo em estações grandes. Medir cenário real com múltiplos trens/bogeys, não inferir peso a partir da quantidade de conteúdo.

## 15. Lifecycle
Cobrir assembly/disassembly, save/restart, chunk unload/reload, schedule edits, dimension travel quando suportado, train derailing/removal e resource reload.
Bogey/render state não pode ficar órfão após o train entity/state mudar.

## 16. Riscos
1. **Beta port:** maior risco de regressão que release estável.
2. **Create API drift:** mudanças de train/bogey internals.
3. **Bogey render/alignment:** regressões conhecidas da linha.
4. **Jukebox Minecart lifecycle:** crash/softlock histórico.
5. **Recipe/tag drift:** upstream vs NeoForge port.
6. **Train graph attribution:** addon confundido com engine-base.
7. **Addon overlap:** tracks/decoração duplicados com outros providers.
8. **Client/server mismatch:** entidades/trains divergentes.

## 17. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Create atual + Railways beta.2.
- [ ] Bogey representativo monta e renderiza em reta/curva.
- [ ] Trem com bogey addon salva/recarrega sem desalinhamento.
- [ ] Station/signal/track elements representativos integram ao graph.
- [ ] Jukebox Minecart não reproduz crash/softlock da lineage.
- [ ] Recipes/tags aparecem e craftam server-side.
- [ ] Dois clientes observam o mesmo train state.
- [ ] Chunk unload/reload não duplica/remove entidades ferroviárias.
- [ ] Outros track addons não criam recipe/graph conflict evidente.
- [ ] Resource reload não deixa textura/model de bogey stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins common/NeoForge.
- Catálogo/pesquisa oficial do port: 0.3.0-beta.2 é a build NeoForge 1.21.1 instalada; linha 0.3.0 contém correções de compatibilidade com Create moderno, Jukebox Minecart, recipes e bogeys.
- Create 6.0.10 permanece base física do stack ferroviário.
- **Limite:** detalhes de cada feature upstream não foram copiados indiscriminadamente para o port; somente superfícies sustentadas pela documentação/lineage atual foram usadas.
