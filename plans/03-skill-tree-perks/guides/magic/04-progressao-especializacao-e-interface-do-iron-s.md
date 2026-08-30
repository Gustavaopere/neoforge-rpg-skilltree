<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 4. Progressão, especialização e interface do Iron's

## Iron's Spells 'n Spellbooks: Recolor — 1.2.4+1.21.1

`recolor_tablet-1.2.4+1.21.1.jar`
**Recolor** adiciona o **Recolor Tablet**, usado para personalizar a tonalidade visual dos spells do Iron's por escola de magia. Depois de vinculado, o tablet abre uma lista das escolas; selecionar uma delas leva a uma color wheel que altera o hue dos efeitos correspondentes, e a opção de reset restaura a aparência original.
A modificação é visual e não altera dano, custo de mana, cooldown ou progressão das magias. A build instalada é `1.2.4+1.21.1`.

## Fundamental Principles - Iron's Spells Addon — 1.1.7.1

`ypfundamentals-1.1.7.1.jar`
**Fundamental Principles** adiciona uma camada de **progressão, categorização e balanceamento** sobre os spells do Iron's. Os feitiços são classificados automaticamente em **13 Principles** segundo seu comportamento — concentração de mana, projéteis, summons, percepção, targeting, repetição de casts, teleporte, status effects, AoE, movimento, cura, imbuement e estabilidade estrutural. Como a classificação é automática, spells de outros addons podem participar do mesmo sistema quando suas características são reconhecidas.
Cada Principle possui **níveis de 0 a 20** e ganha experiência pelo uso de spells pertencentes à categoria. Os níveis alteram spell power e liberam passivos próprios, como mana máxima, precisão de projéteis, cooldown de summons, targeting distance, chance de cargas extras, duração de efeitos, área, cast movement speed e bônus ligados a armas. A tela de Principles acompanha essa progressão separadamente.
O addon também adiciona **Spell Exhaustion**: casts acumulam fadiga e níveis altos de exhaustion reduzem a eficiência mágica até o jogador se recuperar. A **Remedium's Law** faz healing spells consumirem recursos naturais do corpo, como food/saturation, em vez de tratarem cura como simples conversão direta de mana. Spellbooks possuem progressão própria por tiers/covers, aumentando slots e poder conforme evoluem, e a linha atual inclui Mana Reinforcement, novos spells e mobs.
A página pública chama a release de `fundamental_principles_v_1.1.7.1.jar`, mas o próprio File Details oficial informa **`ypfundamentals-1.1.7.1.jar`** como filename real; portanto o JAR da modlist corresponde exatamente à release 1.1.7.1.

## Spell Codex / Specs — 1.6.5

`specs_irons_spellbooks-1.6.5.jar`
**Spell Codex** transforma a lista de spells do Iron's em um **sistema de descoberta e progressão por Codex**. Em vez de tratar todos os feitiços disponíveis apenas como scrolls soltos, organiza spells, tiers, requisitos e desbloqueios em uma interface dedicada, permitindo acompanhar o que já foi descoberto e o que ainda depende de progressão.
O addon é integrado à **Spell Actionbar**, de modo que progressão e execução de spells compartilham a mesma camada de interface. A versão `1.6.5` também permite casting de spells **imbuídos em armas**, com keybind dedicado e slot de HUD próprio; esses casts podem seguir regras específicas separadas da descoberta convencional do Codex.
O sistema é configurável e atua sobre o catálogo de spells do Iron's e de addons reconhecidos, servindo como camada de organização para uma instalação com muitas escolas e feitiços.

## Spell Actionbar — 1.1.4

`spell_actionbar-1.1.4.jar`
**Spell Actionbar** adiciona uma barra de ação dedicada ao Iron's Spells acima da hotbar. A base possui **três slots para scrolls** e pode ser expandida de acordo com spellbooks equipados, permitindo selecionar e lançar habilidades por hotkeys sem abrir o inventário ou a interface do spellbook durante o combate.
A HUD mostra ícones, cooldowns, mana e keybinds associados aos spells. Scrolls colocados na actionbar funcionam como referências de habilidade e **não são consumidos** ao lançar o feitiço.
Além do uso independente, a Actionbar serve como superfície de execução para o Spell Codex e para recursos adicionais como spells imbuídos em armas, concentrando seleção, estado e feedback de casting em uma única interface.
