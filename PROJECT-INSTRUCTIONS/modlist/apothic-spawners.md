# Apothic Spawners

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e9a4ced4c8d90d4375
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apothic Spawners
- **Arquivo JAR:** `ApothicSpawners-1.21.1-1.4.0.jar`
- **Versão 1.21.1:** 1.4.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** RPG, Automação
- **Função:** Módulo oficial do ecossistema Apothic para mob spawners: coleta/configuração, stats namespaced, Spawner Modifiers data-driven, presets add/set, blacklist de entidades, tracking de modificação por jogador, Echoing e regras de despawn/spawn.
- **Dependências:** Placebo 9.9.2 + ecossistema Apotheosis/Apothic atual. Integrações com Apothic Enchanting e outros spawn providers permanecem explícitas e não convertem máquinas externas em spawners Apothic.
- **Sobreposição:** Não é equivalente a Mechanical Spawners ou máquinas externas de spawning. Apothic Spawners modifica spawners e seus stats dentro do ecossistema Apothic; bridges externas precisam ser explícitas.
- **Compatibilidade/Riscos:** Risco de multiplicação de XP/drops com Echoing e outros loot/progression hooks; não tratar outros spawners/máquinas como equivalentes sem bridge. Respeitar tag `apothic_spawners:blacklisted_from_spawners`. 1.4.0 adiciona cascade explosion em spawners atingidos por explosão; testar farms densas.
- **Observações:** mod id: `apothic_spawners`; runtime 1.4.0. O corpo da página registra stats/modifiers, add/set, Silk Touch, blacklist, despawn, Echoing, explosões, anti-abuso e matriz de teste.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/source/changelog oficiais Apothic Spawners 1.4.0 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ApothicSpawners-1.21.1-1.4.0.jar` / `1.4.0`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-spawners
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #36: `ApothicSpawners-1.21.1-1.4.0.jar` / `1.4.0` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita contra o changelog oficial 1.21 até 1.4.0, incluindo tracking de modificação, Echoing, blacklist tag, despawn delay e cascade explosion.
- **Data da última decisão:** 2026-09-07

> 🧿 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta página documenta `ApothicSpawners-1.21.1-1.4.0.jar`, mod id `apothic_spawners`, no runtime NeoForge 1.21.1. O catálogo foi reconstruído contra o source/data da linha 1.21: **16 stats canônicos, 16 families de modifier recipes, coleta/persistência, blacklist, despawn, Echoing, explosões, integração e anti-abuso**.

## 1. Identidade, versão e authority
- **Mod:** Apothic Spawners.
- **Runtime:** `1.4.0`.
- **JAR:** `ApothicSpawners-1.21.1-1.4.0.jar`.
- **Mod id:** `apothic_spawners`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** provider do domínio Apothic de mob spawners: stats, modifiers, coleta, persistência e regras especiais.
- **Authority:** stats/blacklists/estado do próprio spawner pertencem a este módulo. Máquinas de spawning externas continuam providers separados.
- **Decisão:** **Manter**.

## 2. Catálogo completo de stats registrados — 16
O source da linha 1.21 separa stats vanilla-like e stats avançados. Os nomes abaixo são as superfícies canônicas que modifiers/data podem alterar.

### 2.1 Stats base/vanilla — 6
1. **Minimum Spawn Delay** — controla o menor intervalo entre ativações.
2. **Maximum Spawn Delay** — controla o maior intervalo entre ativações.
3. **Spawn Count** — quantidade tentada/produzida por ciclo segundo as regras do spawner.
4. **Max Nearby Entities** — limite de entidades próximas antes de bloquear novas ativações.
5. **Required Player Range** — distância exigida do jogador para ativação normal.
6. **Spawn Range** — alcance espacial das tentativas de spawn.

### 2.2 Stats avançados — 10
1. **Initial Health** — controla a condição de vida inicial das entidades geradas conforme o stat configurado.
2. **Ignore Players** — permite operar sem depender da presença convencional de jogador quando habilitado.
3. **Ignore Conditions** — ignora condições normais de spawn cobertas pelo módulo.
4. **Redstone Control** — adiciona controle por redstone ao funcionamento do spawner.
5. **Ignore Light** — ignora restrições de luminosidade pertinentes.
6. **No AI** — entidades geradas podem sair sem IA conforme a regra do stat.
7. **Silent** — entidades geradas podem receber estado silencioso.
8. **Youthful** — controla geração em estado jovem/baby quando aplicável ao entity type.
9. **Burning** — entidades podem ser geradas em chamas.
10. **Echoing** — multiplica/reexecuta recompensas do mob conforme o sistema do módulo, incluindo **drops e XP** na linha atual.

> **Importante:** o catálogo acima prova a superfície dos stats. Valores/caps finais são data/config-driven e não devem ser inventados a partir de versões antigas.

## 3. Modifier recipes — 16 families correspondentes
O data/source da linha 1.21 expõe recipes/modifiers com os seguintes IDs funcionais:
- `burning`;
- `echoing`;
- `ignore_conditions`;
- `ignore_light`;
- `ignore_players`;
- `initial_health`;
- `max_delay`;
- `max_nearby`;
- `min_delay`;
- `no_ai`;
- `player_range`;
- `redstone_control`;
- `silent`;
- `spawn_count`;
- `spawn_range`;
- `youthful`.

Cada modifier existe para alterar um stat correspondente por item/recipe/data definidos pelo provider. **Esta ficha não fixa ingrediente de uma build histórica sem confirmação do JSON da versão instalada**; para automação/perk, o contrato importante é o stat/modifier real, não uma receita presumida.

## 4. Operações `add` e `set`
`StatModifier` suporta pelo menos dois modos de operação:
- **`add`** — modificação relativa/incremental;
- **`set`** — define um preset/valor alvo conforme a regra de dados.

Essa distinção importa para integração:
- não suponha que todo uso é “+1”;
- datapacks podem criar presets;
- um observer deve ler o estado resultante, não inferir pelo item aplicado;
- replay do mesmo evento não pode gerar Mastery indefinidamente.

## 5. Echoing — drops e experiência
Mudança relevante da linha instalada:
- na `1.3.0`, o max level default de **Echoing** foi reduzido de 5 para 3;
- Echoing passou a se aplicar também à **experiência do mob**, além de drops.

### Consequência no pack
Apotheosis, loot modifiers, Additional/Pufferfish attributes, perks de XP e sistemas de recompensa podem tocar o mesmo resultado. **Nunca multiplicar XP/drop novamente só porque o mob veio de spawner.** A origem precisa ser deduplicada pelo pipeline real.

## 6. Tracking de spawner modificado pelo jogador
Desde a linha 1.3.x, o módulo rastreia se o spawner foi **modificado pelo jogador**.
- quebrar com Silk Touch também entra nesse estado;
- isso permite distinguir spawners naturais/virgens de blocos já manipulados;
- uma perk de descoberta/comissionamento deve usar uma identidade persistente ou milestone, não “quebrou/colocou novamente”.

## 7. Silk Touch e coleta
A coleta do spawner preserva o domínio do módulo e possui comportamento configurável.

Uma opção pode fazer o spawner:
- perder a entidade armazenada ao ser quebrado com Silk Touch;
- preservar os demais stats;
- no modo de spawner vazio, resetar spawn delay para reduzir incompatibilidades de stacking.

**Não usar NBT superficial como única identidade de novidade.** Break/place/reload não deve transformar o mesmo spawner em “novo” para progressão.

## 8. Entity blacklist
A blacklist de entidades foi migrada para a tag:
`apothic_spawners:blacklisted_from_spawners`

Todo caminho que troca o mob-alvo deve respeitá-la, incluindo:
- spawn eggs quando cobertos pelo stack;
- Capturing/itens equivalentes;
- **Occult Ender Lead** do Apothic Enchanting;
- qualquer adapter próprio.

Provider ausente ou target blacklistado = **fail-closed**. Não trocar para entidade genérica como fallback.

## 9. Despawn de entidades geradas
O módulo incorporou funcionalidade de despawn timer. A linha `1.3.4` corrigiu a aplicação de **Entity Despawn Delay** para afetar somente entidades efetivamente criadas por spawner, evitando interferência indevida em natural spawns.

Ao investigar comportamento de entidade, distinguir:
1. natural spawn;
2. estrutura/event spawn;
3. fake-player/machine spawn;
4. spawner Apothic.

Não atribuir ao módulo uma mudança global de despawn sem provar a origem.

## 10. Burning, Youthful, No AI e Silent — efeitos de spawn
Esses stats alteram o **estado da entidade produzida**, não uma aura global do spawner:
- Burning → entidade nasce em chamas conforme regra;
- Youthful → forma jovem quando suportada;
- No AI → IA desabilitada;
- Silent → entidade silenciosa.

Integrações com mobs modded precisam testar suporte sem presumir que toda entidade possui baby state ou aceita todas as flags de forma equivalente.

## 11. Ignore Players / Conditions / Light
Esses stats alteram gates de operação/spawn. Em farms e servidores grandes eles podem aumentar muito o número de entidades geradas.
- **Ignore Players** não deve ser lido como “chunkloader”.
- **Ignore Conditions** não prova que claims/protection podem ser ignorados.
- **Ignore Light** trata luz, não todos os spawn predicates possíveis.

## 12. Redstone Control
Adiciona uma superfície real de controle do spawner. Para automação:
- redstone é trigger de máquina, não autoria automática de um jogador;
- toggle repetitivo não é milestone de Mastery;
- reload/restart precisa preservar o state sem gerar eventos falsos.

## 13. Cascade explosion — versão 1.4.0
A versão instalada `1.4.0` backporta mudanças da linha 2.0.0:
- spawners atingidos por explosão podem provocar **explosão em cascata**;
- houve correção de comparação float em stats percentuais.

### Integrações sensíveis no pack
Create Big Cannons, explosivos industriais, mobs explosivos e combate em farms podem acionar esse comportamento. Testar bases com vários spawners próximos para avaliar dano e chain reaction.

## 14. Relação com Apothic Enchanting
O **Occult Ender Lead** pertence ao Apothic Enchanting, mas pode mudar a entidade de um spawner.

Boundary:
- item/interação = Enchanting;
- target/entity restriction = tag/authority de Spawners;
- stats/persistência = Spawners.

Nenhum addon deve contornar a blacklist por ser “o dono do item”.

## 15. Relação com outros sistemas de spawning do pack
### Mechanical/industrial spawners
Máquinas de outros mods são providers próprios. Não recebem automaticamente os 16 stats Apothic.

### Mob farms vanilla
Uma farm baseada em natural spawn não é spawner Apothic.

### Summons / servants / familiars
Invocações mágicas possuem ownership e lifecycle próprios e não devem ser classificadas como output de spawner apenas por criarem entidades.

### World Scaling RPG
Se uma entidade recebe scaling ao nascer, persistência/rarity/affixes precisam ser idempotentes. Recarregar a entidade do spawner não pode rerrolar progressão indefinidamente.

## 16. Anti-abuso / Mastery
Proibido conceder Mastery por:
- cada tick do spawner;
- cada mob autônomo produzido sem autoria causal;
- retirar/recolocar o mesmo spawner;
- reaplicar o mesmo modifier repetidamente;
- ligar/desligar redstone em loop;
- throughput/XP contínuo de farm.

Milestones legítimos, se houver design aprovado, precisam usar ledger/identidade: primeira aplicação real de uma família de modifier, primeira configuração distinta, primeiro spawner com arquitetura nova etc.

## 17. Lifecycle e persistência
Validar:
- save/load do bloco;
- Silk Touch;
- chunk unload/reload;
- servidor restart;
- mudança de entity target;
- modifiers `add/set`;
- config reload/datapack reload quando suportado;
- desmontagem/movimento por sistemas físicos somente quando bridge real existir.

## 18. Riscos específicos
1. XP/drop multiplicados por Echoing + outro modifier.
2. Spawner rebuild farming.
3. Blacklist bypass por item/bridge externo.
4. NoAI/Ignore Light/Ignore Conditions alterando densidade/performance.
5. Cascade explosion destruindo farms em cadeia.
6. Youthful aplicado a entity type sem baby state.
7. Confundir Ignore Players com chunkloading.
8. Natural spawn erroneamente tratado como spawner-generated.
9. World scaling/affix reroll em unload/reload.

## 19. Matriz de validação exaustiva
### Stats/modifiers
- exercitar os 16 stats individualmente;
- testar `add` e `set`;
- testar min/max delay coerentes;
- Spawn Count/Max Nearby/Player Range/Spawn Range;
- Initial Health;
- Ignore Players/Conditions/Light;
- Redstone Control;
- No AI/Silent/Youthful/Burning/Echoing.

### Persistência
- break/place com e sem Silk Touch;
- config de spawner vazio;
- save/load/chunk unload/restart;
- player-modified tracking.

### Entidades/recompensa
- mob vanilla e modded;
- blacklist permitida/bloqueada;
- Echoing drops + XP;
- despawn de spawner mob versus natural mob;
- world scaling/affix idempotente.

### Segurança
- explosion única e cadeia de múltiplos spawners;
- dedicated server sob carga;
- nenhum dupe de item/XP;
- nenhum Mastery por automação autônoma.

## 20. Fontes
- Source oficial `Shadows-of-Fire/Apothic-Spawners`, branch `1.21`.
- Data/recipes e catálogo de stats do source da linha 1.21.
- Changelog oficial 1.21 até `1.4.0`.
- CurseForge oficial.
- Modlist física do projeto — authority da versão instalada.
