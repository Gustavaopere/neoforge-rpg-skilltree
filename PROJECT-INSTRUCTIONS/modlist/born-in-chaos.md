# Born in Chaos

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db818b9c28fadccfabc4cc  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Born in Chaos
- **Arquivo JAR:** `born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar`
- **Versão 1.21.1:** `1.7.6`
- **Categoria:** Mobs; RPG; Exploração; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/born-in-chaos
- **Função:** Expansão de sobrevivência/horror com grande variedade de mobs hostis e minibosses, estruturas, equipamentos, blocos, achievements e sistemas como Naughtiness/Krampus.
- **Dependências:** GeckoLib é dependência requerida oficialmente; pack usa GeckoLib 4.9.2. Better Combat é apenas dependência opcional no projeto.
- **Compatibilidade/Riscos:** Grande impacto em spawn/dificuldade/worldgen. Riscos: densidade somada a outros mob mods, Naughtiness/Krampus em multiplayer, disarm/freeze/crowd control, GeckoLib side/render e configs de spawn. A 1.7.6 é a última build atual antes do rewrite 2.0 anunciado pelo autor.
- **Sobreposição:** Compartilha eixo de mobs/bosses/estruturas com outros providers, mas possui entidades, sistemas e loot próprios. Avaliar por spawn density, progressão e CC real, não por temática.
- **Observações:** 1.7.6 NeoForge 1.21.1, Client & Server, GeckoLib required. Changelog 1.7.6 corrige breeding season do Thornshell Crab. O autor anunciou rewrite completo para 2.0 e pausa de updates da linha atual. Naughtiness/Krampus, Missionary, equipamentos e estruturas são superfícies oficialmente documentadas.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Born in Chaos 1.7.6 + documentação/changelogs oficiais do projeto + GeckoLib 4.9.2 físico.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, Born in Chaos 1.7.6 foi revalidado contra a modlist física atual e GeckoLib 4.9.2; a presença instalada e o estado de manutenção da linha 1.7.6 não foram convertidos em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Born in Chaos 1.7.6 físico confirmado; Naughtiness/Krampus, world-age gates, structures/equipment, GeckoLib side/lifecycle e maintenance boundary até rewrite 2.0 preservados. Runtime QA não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `born_in_chaos_[Neoforge]_1.21.1_1.7.6.jar`, mod id `born_in_chaos_v1`, runtime `1.7.6`, NeoForge 1.21.1. GeckoLib é dependência requerida; o pack possui GeckoLib `4.9.2`. A linha 1.7.6 continua sendo a build atual enquanto o autor reescreve o mod para uma futura 2.0.

## 1. Papel e autoridade
Born in Chaos é um provider amplo de **hostilidade, survival horror, mobs/minibosses, estruturas, equipamentos e sistemas de ameaça**. O mod é authority do spawn/AI/loot/efeitos de suas criaturas e de sistemas próprios como Naughtiness/Krampus.

Outros mods de scaling ou combat não devem reaplicar atributos/efeitos sem deduplicação.

## 2. Escopo oficial da build
A página oficial define o mod como expansão com:
- muitos mobs agressivos com propriedades próprias;
- estruturas;
- equipamentos;
- blocos;
- achievements;
- elementos de Adventure/RPG e worldgen.

A 1.7.6 é um mod grande sem source versionado público auditado nesta etapa; portanto não se publica uma contagem inventada de todas as entidades/blocos/items.

## 3. Progressão temporal e ameaças
Diversas criaturas/encounters do projeto usam condições de mundo/tempo. Exemplo documentado: **Missionary** aparece apenas a partir do décimo dia e pode teleportar, invocar undead, disparar projéteis mágicos e usar golpe de área; suas abilities podem ser bloqueadas por Magic Depletion/Nightmare Scythe conforme documentação oficial.

Regras:
- world age é server-authoritative;
- reload não deve recontar dias;
- spawn gate temporal deve considerar o mundo/servidor correto;
- mods de scaling externos não devem duplicar a progressão temporal nativa.

## 4. Naughtiness Mechanics
Sistema oficial inspirado em DST:
- ações consideradas ruins aumentam Naughtiness, especialmente matar animais/villagers e, com peso maior, babies/creatures sem benefício prático;
- fertilizar plantas/taming podem reduzir pontos;
- thresholds disparam aviso/chegada de Krampus Henchmen e, no máximo, Krampus;
- matar Krampus reduz Naughtiness para 15% do máximo conforme documentação da linha do sistema;
- existe gamerule para desativar a mecânica na linha moderna.

Naughtiness é domínio próprio; não converter automaticamente em karma/reputation de outro mod.

## 5. Krampus Henchman
Inimigo rápido de biomas frios ou do sistema de Naughtiness. Pode ser invocado por Krampus e tem chance de **derrubar a arma da mão** do jogador. Ao receber dano, alterna comportamento de fuga/reengage.

Integrações de inventory/combat devem assegurar que disarm é processado uma vez e sincronizado pelo servidor.

## 6. Krampus
Miniboss raro em biomas frios após o décimo dia ou forçado por Naughtiness alto. Comportamentos documentados:
- ataques fortes podem derrubar itens da mão;
- bloquear golpe forte causa dano significativo ao escudo;
- invoca Henchmen;
- em baixa vida pode gerar **Snow Storm**;
- Snow Storm acumula congelamento/slow e dano de frost;
- dropa Krampus Horn/Bag.

Phase/threshold de Snow Storm deve ser server-authoritative e não disparar novamente por animation event client-side.

## 7. Naughtiness items
Superfícies oficialmente documentadas incluem:
- **Evilometer**: mostra/representa Naughtiness;
- **Krampus's Bag**: loot aleatório, com integração opcional a outros mods em linhas atuais;
- **Creepy Cookies With Milk** / **Spiritual Gingerbread**: alteram Naughtiness e possuem efeitos próprios;
- **Birch Branches**: dano relacionado ao nível de Naughtiness do jogador-alvo na linha documentada.

UI/texture do Evilometer observa state; não é authority dos pontos.

## 8. Frost/ice equipment
Conteúdo documentado inclui:
- Stormcaller's Horn: cria tempestade de neve ao redor do usuário e entra em cooldown;
- Permafrost Shard: material de crafting/drop;
- Frostbitten Blade e Icy Sweetness: aplicam Bone Chilling/frostbite e possuem ability de splash em versões modernas;
- Elixir of Ice Barrier: aumenta resistência e congela agressores progressivamente;
- Nightmare Armor possui sinergias com cooldown/duração de Snow Storm em linhas documentadas.

Esses procs/cooldowns devem settlement exatamente uma vez no servidor.

## 9. Missionary e outros minibosses
Missionary é exemplo confirmado de miniboss condicionado por idade do mundo, com summon/teleport/projectiles/CC. O mod contém outras ameaças/minibosses e criaturas com counters próprios; sem source auditado da 1.7.6, a ficha não inventa um roster completo nem fórmulas de cada entidade.

Para integrações, usar registry IDs/runtime real quando o mob concreto for necessário.

## 10. Spawn e biomas modded
A documentação do projeto enfatiza grande variedade de mobs e uso de categorias/condições de ambiente, permitindo coexistência em mundo modded. Em pack de alta densidade:
- medir spawn weights reais;
- evitar um segundo global spawn injector para mobs Born in Chaos;
- preservar mob caps e biome eligibility do provider;
- testar world age + biome gate juntos.

## 11. Estruturas
Born in Chaos adiciona estruturas próprias relacionadas à exploração/ameaças. Worldgen externo deve tratar essas structures como provider-native:
- não regenerar em chunk load;
- loot pertence ao loot table/provider;
- spacing/density deve ser avaliado com Cataclysm/BOMD/Bosses'Rise/outros structure mods.

## 12. Equipamentos, blocos e achievements
O projeto registra weapons/armor/tools, blocks e achievements próprios. Sem inventário binário completo nesta etapa, a ficha mantém o contrato operacional:
- item abilities e cooldowns no servidor;
- GeckoLib/model/render no cliente;
- achievements/advancements exatamente uma vez;
- recipes/loot alterados por datapack/KubeJS apenas quando explicitamente necessário.

## 13. Compatibilidade GeckoLib
GeckoLib é required dependency e o pack usa 4.9.2. Render/animation são client-side; entity state/AI/hit settlement são server-side.

Animation marker não deve ser usado por integração externa para aplicar dano novamente se o mob já resolveu o ataque.

## 14. Better Combat opcional
O projeto lista Better Combat como integração opcional. O pack usa Epic Fight como eixo de combate, portanto a existência de compat opcional com Better Combat **não prova compat automática com Epic Fight**.

Born in Chaos deve ser testado com Epic Fight para hitboxes, weapon animations, disarm, knockback, CC e miniboss movesets.

## 15. Release 1.7.6 e manutenção
A 1.7.6, de 17/06/2026, corrige especificamente a **breeding season do Thornshell Crab**. O autor também informa que o mod está sendo completamente reescrito em código manual para uma futura versão 2.0 e que a linha atual ficará sem updates até então.

Consequência para o pack: 1.7.6 é estável no sentido de versão publicada atual, mas bugs remanescentes podem permanecer sem correção incremental rápida.

## 16. Client/server e lifecycle
Validar:
- entity spawn/despawn;
- world day gates;
- Naughtiness save/sync/reconnect;
- Krampus threshold e repeat behavior;
- death/respawn;
- dimension change;
- chunk unload/reload durante miniboss fight;
- GeckoLib resource reload;
- equipment cooldown/effects em multiplayer.

## 17. Riscos
1. Spawn density excessiva em conjunto com outros mob mods.
2. Naughtiness duplicada por eventos externos de kill/tame/grow.
3. Disarm mover/drop item duas vezes.
4. Freeze/CC empilhar fora do esperado com outros systems.
5. World-age gates discordarem após migration/restart.
6. Animation callback duplicar damage.
7. Structure density/loot progression saturar exploração.
8. Linha 1.7.6 permanecer com bug conhecido enquanto rewrite 2.0 está em andamento.

## 18. Matriz de testes
1. Dedicated server boot com GeckoLib 4.9.2.
2. Mundo novo: primeiros 10+ dias, verificar gates temporais.
3. Naughtiness: ganho, redução, warnings, Henchmen e Krampus.
4. Krampus: disarm, shield damage, minions, Snow Storm e death reset.
5. Missionary: spawn gate, summons, teleport e Magic Depletion interaction.
6. Frost equipment/Stormcaller: cooldown, freeze e multiplayer ownership.
7. Thornshell Crab breeding na 1.7.6.
8. Epic Fight ativo: mobs e weapons sem double-hit/animation desync.
9. Chunk unload/restart durante miniboss.
10. Benchmark de spawn density com outros mob providers.

## 19. Evidência
- modlist física atual: Born in Chaos 1.7.6 + GeckoLib 4.9.2;
- CurseForge oficial: escopo mobs/structures/equipment/blocks/achievements;
- relações oficiais: GeckoLib required, Better Combat opcional;
- documentação oficial de Missionary, Krampus/Henchmen e Naughtiness;
- changelog 1.7.6: fix da breeding season do Thornshell Crab + anúncio de rewrite 2.0.

> ☠️ Exaustividade proporcional: a ficha documenta sistemas e criaturas oficialmente descritos sem fabricar uma contagem de registry para um JAR grande cujo source 1.7.6 não foi auditado publicamente nesta etapa.
