<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 9. MineColonies e civilização

## MineColonies — runtime 1.1.1374-1.21.1-snapshot

`minecolonies-1.1.1374-1.21.1-snapshot.jar`
**MineColonies** é um sistema completo de **construção e gerenciamento de civilização**. O jogador funda uma colônia, escolhe estilos de construção, posiciona edifícios e recebe cidadãos com necessidades, atributos e profissões que passam a executar tarefas reais no mundo.
Builders constroem e evoluem estruturas; miners, foresters, farmers, fishermen e outros produtores obtêm recursos; crafters transformam materiais; **Warehouse + Couriers** formam a espinha dorsal logística; guards e barracks defendem a população. A colônia possui também **Research**, hospitalidade/saúde, moradia, educação e cadeias de trabalho dependentes umas das outras.
A progressão exige equilibrar população, comida, materiais, rotas de entrega, capacidade produtiva e defesa. Raids e ameaças externas tornam segurança parte do sistema, enquanto edifícios superiores desbloqueiam trabalhadores e eficiência adicionais.
A build instalada é `1.1.1374-1.21.1-snapshot` e depende do stack Structurize, Multi-Piston, BlockUI e Domum Ornamentum. Os addons Compatibility 3.56 e Tweaks 3.33 publicam alvo MineColonies 1.1.1368; essa diferença permanece registrada como **acoplamento de versões**, sem alterar a identidade dos JARs atuais.

## Epic Fight: Epicfied (Epic Colonies) — 21.0.8

`EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar`
**Epic Colonies** é a bridge MineColonies ↔ Epic Fight. Ela adapta cidadãos, guards, raiders e outras entidades da colônia ao **modelo animado e à linguagem visual de combate do Epic Fight**, aplicando modelos, expressões e animações compatíveis ao invés de deixar os NPCs presos ao renderer vanilla durante o Battle Mode.
Isso faz os confrontos da colônia coexistirem visualmente com o overhaul de combate usado pelo jogador. O filename da build `21.0.8` registra explicitamente alvo `EFM-21.16.4`, enquanto o core atualmente instalado é Epic Fight `21.17.3.1`; o guia preserva essa diferença de alvo como informação de coupling.

## MineColonies Compatibility — 3.56

`MineColonies_Compatibility-1.21.1-3.56.jar`
**Compatibility addon for MineColonies** amplia o conjunto de itens e sistemas externos que os cidadãos conseguem usar de forma funcional. O addon adiciona adapters para **ferramentas, alimentos, profissões e redes/logística de outros mods**, permitindo que workers reconheçam recursos que o MineColonies base não conheceria sozinho.
A linha do projeto inclui integrações condicionais conforme os mods presentes, portanto não cria cópias desses sistemas: traduz itens e mecânicas externas para as necessidades de colonists. A build instalada `3.56` é beta NeoForge 1.21.1 publicada em 13/08/2026 e foi construída tendo MineColonies `1.1.1368` como alvo, enquanto o pack executa `1.1.1374-snapshot`; a divergência fica documentada como acoplamento de versão.

## MineColonies Tweaks — 3.33

`MineColonies_Tweaks-1.21.1-3.33.jar`
**Tweaks addon for MineColonies** expande configuração e regras internas da colônia. Entre os recursos documentados estão **probabilidade de colônias abandonadas no worldgen, delays de construção/destruição, configuração de crops, controle de mourning, imunidade/thorns de raiders e chance de resurrection**, além de comandos administrativos próprios.
O addon também amplia ferramentas/itens reconhecidos por cidadãos e ajusta diferentes comportamentos de workers. A build `3.33` é beta NeoForge 1.21.1 de 08/08/2026 e publica alvo MineColonies `1.1.1368`; o core instalado é `1.1.1374-snapshot`, por isso as duas versões permanecem explicitamente separadas.

## Let's Do addon for MineColonies — 2.1

`MineColonies_LetsDo-1.21.1-2.1.jar`
**Let's Do addon for MineColonies** conecta a colônia aos módulos rurais/culinários da família **[Let's Do]**. O objetivo é fazer recursos, alimentos e interações desses mods serem reconhecidos pelos sistemas de workers e compatibilidade do MineColonies, em vez de existirem como itens externos que os cidadãos ignoram.
A bridge depende do MineColonies e dos addons Compatibility/Tweaks correspondentes, além dos módulos Let's Do suportados. A release `2.1` é estável para NeoForge 1.21.1; como o ecossistema de addons foi publicado contra uma linha anterior do core, o guia mantém a diferença em relação ao snapshot `1.1.1374` instalado.

## Towntalk — 1.2.0

`towntalk-1.2.0.jar`
**TownTalk** adiciona **chatter contextual aos colonists do MineColonies**, fazendo cidadãos emitirem falas relacionadas à vida da cidade e às situações em que se encontram. A função é dar identidade social à população e tornar a colônia menos silenciosa, sem alterar a cadeia produtiva ou substituir o sistema de jobs.
Como addon de ambientação funcional ligado aos NPCs, ele depende do MineColonies para existir. A build instalada é `1.2.0` para a linha 1.21.1.
