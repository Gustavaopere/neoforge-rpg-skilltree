<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# Ecossistema Vampirism

## Vampirism — 1.10.12

`Vampirism-1.21-1.10.12.jar`
**Vampirism** adiciona duas progressões sobrenaturais completas: **vampiro** e **vampire hunter**. A infecção vampírica pode começar por mordida ou sangue injetado e evolui para uma transformação permanente. Vampiros substituem a fome comum por uma economia de **sangue**, obtido de animais e villagers; alimentar-se sem matar permite que a vítima regenere sangue, enquanto níveis mais altos também permitem converter humanos em vampiros.
A progressão vampírica usa **rituais e níveis**. Subir de nível aumenta poder físico e concede skill points para habilidades como night vision, transformação em morcego, teleporte e outras capacidades sobrenaturais. Em contrapartida, o sol causa dano e o avanço do vampiro faz surgirem hunters mais perigosos. Coffins, equipamentos e estruturas próprias participam dessa rotina noturna.
A rota de **hunter** possui arsenal, coleta, combate, exploração e árvore de habilidades própria, incluindo técnicas voltadas especificamente a enfrentar vampiros. O worldgen inclui Vampire Forest, vampire barons, estruturas e aldeias defendidas por hunters. Vilas podem entrar no conflito de facções e ser controladas por vampiros ou caçadores, enquanto a força de certos mobs escala em função do nível dos jogadores próximos.

## Bloodlines — 1.21-3.0.9

`bloodlines-1.21-3.0.9.jar`
**Bloodlines** adiciona uma camada de especialização sobre as facções de Vampirism. Vampiros e hunters podem entrar em **linhagens** específicas seguindo a quest de ingresso correspondente; uma vez dentro, a linhagem abre uma árvore própria de habilidades e regras que alteram a forma como aquela facção progride e luta.
Os ranks de bloodline podem conceder novas skills e também impor penalidades ou condições próprias, fazendo a escolha representar um arquétipo mecânico e não apenas um título. O addon possui rotas e sistemas temáticos diferentes por linhagem, incluindo conteúdo específico de hunters e vampiros. Também existe uma saída formal do sistema: a **Purity Injection** permite abandonar a bloodline e retornar à progressão sem linhagem.

## Vampiric Ageing — 1.4.21

`vampiricageing-1.21-1.4.21.jar`
**Vampiric Ageing** adiciona **Age Ranks** e evolução de longo prazo para vampiros, hunters e, quando Werewolves está presente, lobisomens. Por padrão, a progressão vampírica começa depois dos níveis altos do Vampirism e o jogador aumenta sua idade drenando sangue; o progresso pode ser consultado em Coffins. O método de envelhecimento é configurável e pode ser baseado em tempo, infecções, drenagem de sangue ou caça a outras facções.
Subir de Age Rank fortalece atributos existentes e libera capacidades adicionais. Hunters e werewolves possuem suas próprias variantes de ageing, com modificadores e poderes específicos; a linha atual inclui, por exemplo, percepção de entidades invisíveis por ações como **Wise Eye/Superior Senses**, além de parâmetros para regeneração, mineração, dano contra facções e força de leap dos lobisomens.
Quase toda a mecânica é configurável: requisitos de rank, método de evolução, perdas em morte e multiplicadores de poder podem ser ajustados ou desativados. Assim, Ageing funciona como uma camada posterior ao leveling normal das facções.

## Werewolves — 2.0.3.3

`Werewolves-1.21-2.0.3.3.jar`
**Werewolves** adiciona uma terceira facção sobrenatural ao ecossistema Vampirism. O jogador pode viver em forma humana durante o dia e assumir sua força bestial à noite; a condição altera alimentação, combate, resistências e vulnerabilidades. Werewolves se alimentam de **carne fresca**, recebem proteção natural da pelagem contra dano elevado e são particularmente vulneráveis a **armas de prata**.
A progressão permite escolher especializações diferentes, desde foco direto em combate até caminhos mais voltados a sobrevivência ou manutenção de características humanas. Transformação, habilidades e equipamentos próprios fazem o estado de lobisomem funcionar como uma progressão persistente, não como buff temporário.
O addon também modifica o mundo com conteúdo específico da facção, incluindo o biome **Werewolf Heaven**, onde lobisomens podem expressar sua força mesmo durante o dia. A build instalada `2.0.3.3` é a release NeoForge 1.21.1 atual.

## Vampirism Integrations — 1.10.2

`vampirism_integrations-1.21.1-1.10.2.jar`
**Vampirism Integrations** concentra compatibilidades externas que não ficam no core do Vampirism. Ferramentas de informação como **HWYLA/WAILA/WTHIT/Jade** podem exibir nível de sangue, informações de garlic e estado vampírico das criaturas por meio dessa camada.
A integração com **MCA/MCA Reborn** permite alimentar-se de villagers desse ecossistema, cria versões vampíricas/conversões e adapta comportamento de aldeias e villagers à lógica de mordidas e facções. Outras bridges tratam reconhecimento de vampiros como undead/holy targets, biomas onde a luz solar deve se comportar de modo especial e compatibilidades de crafting.
Para sistemas de sobrevivência, há integração com **Survive/Cold Sweat** que altera resistência térmica de vampiros e pode suprimir sede. Também existem pontos de integração para CraftTweaker editar receitas próprias do Vampirism e para mods de aldeias/guards respeitarem facções. A versão `1.10.2` é a release NeoForge 1.21.1 instalada.

## Vampirism Iron's Spells Compatibility — 0.0.9

`vampire_spells_addon-neoforge-1.21.1-0.0.9.jar`
**Vampirism Iron's Spells Compatibility** conecta a economia de sangue do Vampirism às escolas **Blood** e **Holy** de Iron's Spells. Para vampiros, Ray of Siphoning e Devour podem restaurar sangue com base no dano de vida realmente entregue ao alvo, depois de absorção e demais etapas de processamento, sem contar overkill e respeitando a capacidade máxima de sangue.
Nos spells Blood que consomem mana, um vampiro normalmente paga mana primeiro; se não houver mana suficiente, o addon pode realizar um pagamento atômico com sangue em vez de consumir parcialmente recursos. Há também configuração para usar sangue como recurso principal nesses casts. Spells Blood lançados por vampiros recebem um multiplicador próprio de cooldown — por padrão `2/3`, equivalente a cooldown 1,5× menor — enquanto Ray mantém comportamento especial.
A escola Holy passa a reconhecer vampiros como alvos incompatíveis com healing comum: dano Holy contra NPCs vampiros é amplificado, healing Holy pode ferir vampiros em vez de curá-los e utility spells Holy podem causar dano e cancelar o cast quando o próprio caster é vampiro. As regras são configuradas no serverconfig por mundo.
---
**Guias relacionados:** [⚙️ Mods de Tecnologia](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff) · [⚔️ Gameplay e Sistemas](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6)
