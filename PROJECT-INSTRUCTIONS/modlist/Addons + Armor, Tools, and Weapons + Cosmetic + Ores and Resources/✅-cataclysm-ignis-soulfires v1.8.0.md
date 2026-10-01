# Cataclysm: Ignis Soulfires

## Propriedades do registro

- **Mod:** Cataclysm: Ignis Soulfires
- **Arquivo JAR:** `ignissoulfires-1.8.0.jar`
- **Versão 1.21.1:** `1.8.0`
- **Categoria:** RPG, Compat
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a build física Ignis Soulfires 1.8.0 foi reconstruída ao padrão técnico com base nas fontes oficiais. A data anterior sem decisão formal foi removida para evitar tratar presença como decisão curatorial.
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cataclysm-ignis-soulfires
- **Função:** Expansão de Cataclysm centrada em Souled Ignitium: materiais, ferramentas arremessáveis, upgrades, armas, armor, horse armor, Bulwark defensivo e abilities/configuração próprias.
- **Dependências:** L_Ender's Cataclysm + Lionfish API + Curios API, conforme página oficial da linha 1.8.0. Ignis Soulfires: Spellbooks é expansão separada, não hard dependency.
- **Compatibilidade/Riscos:** Addon stateful de tools/armor/abilities com configs client/common; riscos de double ability/damage, Tree Cap/prospecting overlap, Bulwark input state, horse armor lifecycle e client-classloading em dedicated server. Upstream 1.8.1 corrige precisamente crashes de servidor por client-only code.
- **Sobreposição:** Complementa Cataclysm com conteúdo `ignissoulfires`; não substitui Cataclysm. Pode cruzar Epic Fight/combat bridges, tree-felling e prospecting providers, exigindo exactly-once behavior.
- **Observações:** Runtime físico permanece 1.8.0. Upstream publicou **1.8.1** para NeoForge 1.21.1 em 15/09/2026; ela não está instalada.
- **Procedência:** modlist física atual + CurseForge/Modrinth Ignis Soulfires 1.8.0/1.8.1. A autoridade física continua `ignissoulfires-1.8.0.jar`.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Ignis Soulfires físico permanece 1.8.0. A release **1.8.1** foi revisada integralmente e registrada abaixo.

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #323: JAR `ignissoulfires-1.8.0.jar`, mod id `ignissoulfires`, runtime `1.8.0`, SHA-1 `a6f1c8cfe673aaff17f081ee9546e3600b4c72f1`.

# Dossiê operacional — padrão Alex's Mobs
<callout icon="✅" color="green_bg">
	Versão física confirmada: `ignissoulfires-1.8.0.jar`, mod id `ignissoulfires`, runtime `1.8.0`, NeoForge 1.21.1. É uma expansão de Cataclysm centrada em **Souled Ignitium**, equipamentos, ferramentas arremessáveis, armor/horse armor e habilidades inspiradas no Ignis.
</callout>
## 1. Papel e authority
Ignis Soulfires adiciona conteúdo derivado/inspirado em Cataclysm, mas mantém seus próprios materiais, itens e efeitos. Cataclysm continua authority dos bosses/entidades e mecânicas originais; Ignis Soulfires é authority do conteúdo `ignissoulfires` que estende esse ecossistema.
Integrações não devem reaplicar habilidades do item porque ele visualmente deriva de Ignitium/Cataclysm.
## 2. Dependências confirmadas
A página oficial declara três requisitos:
- L_Ender's Cataclysm;
- Lionfish API;
- Curios API.
São dependências funcionais/publicadas da linha atual. A expansão oficial **Ignis Soulfires: Spellbooks** é um addon separado, não dependência necessária para esta ficha.
## 3. Progressão material
A superfície publicada mostra uma cadeia com **Soul Essence**, **Souled Powder**, **Souled Ignitium**, upgrade de Souled Ignitium, trim material e Souled Blazing Grips.
A ficha não inventa recipes/quantidades não publicadas. O importante para integração é manter a cadeia do provider como authority e não gerar Souled Ignitium por uma recipe externa paralela sem decisão explícita.
## 4. Tool sets
O projeto possui tool sets de **Ignitium** e **Souled Ignitium**. A galeria oficial descreve ambos como ferramentas arremessáveis; Souled Ignitium recebe habilidades adicionais.
Na 1.8.0, propriedades de Ignitium/Souled Ignitium tools passaram a ser configuráveis em vez de hardcoded. Portanto dano, velocidade, tier e comportamento não devem ser copiados como constantes por integrações externas.
## 5. Weapons e upgrades
A documentação mostra armas que podem ser **upgraded to Souled Ignitium with new abilities**. Isso é progressão provider-owned: recipe/upgrade valida item elegível, material e resultado uma vez.
Combat bridges/Epic Fight devem observar o ataque final do item e evitar disparar novamente a ability apenas porque houve um hit convertido em animation event.
## 6. Souled Ignitium Armor
O set Souled Ignitium melhora Ignitium armor e possui habilidades próprias. A 1.8.0 aumenta toughness e knockback resistance e torna suas propriedades configuráveis.
Histórico confirmado da linha inclui utilidades como regeneração em lava no chestplate e comportamento de movement/campfires nas leggings; como esses recursos antecedem 1.8.0 e a release atual altera configuração/valores, qualquer número deve ser lido da build/config, não congelado no catálogo.
## 7. Horse Armor — 1.8.0
A 1.8.0 adiciona:
- Netherite Horse Armor;
- Ignitium Horse Armor;
- Souled Ignitium Horse Armor.
Ignitium Horse Armor recebe fire protection, lava walking e retaliation. Souled Ignitium Horse Armor adiciona healing e maior mounted movement sobre lava. A própria release também corrige aplicação dos valores configurados da Souled Ignitium Horse Armor.
## 8. Bulwark of the Soul Flame
O projeto publica **Bulwark of the Soul Flame**, uma barreira defensiva deployable. A 1.8.0 melhora placement para usar o interaction range real do jogador e centraliza walls em espaços confinados; também corrige state temporário de input que podia permanecer após disconnect.
Placement é server-authoritative. Uma compat de reach não deve recalcular distância por valor fixo, pois a release passou a respeitar o alcance real.
## 9. Configuração expandida — 1.8.0
A release adiciona configuração ampla para:
- weapons;
- tools;
- armor;
- abilities;
- Tree Cap;
- prospecting;
- tool tiers.
Também adiciona **client config separado** e config screen para opções exclusivamente client-side. Gameplay config precisa permanecer no lado servidor/common; overlay/visual options pertencem ao cliente.
## 10. Tree Cap e prospecting
A 1.8.0 torna limites/comportamento de Tree Cap configuráveis e permite configurar range, activation time e disponibilidade do prospecting.
Essas utilities podem cruzar outros mods de tree-felling/mining/prospecting; não executar dois handlers sobre o mesmo block break sem exatamente uma authority definida.
## 11. Thrown tool particles e apresentação
Thrown Ignitium/Souled Ignitium tools ganham flame/soul-flame particles e melhorias de rendering. Partículas são apresentação; projectile/impact/ability continuam gameplay state do servidor.
A 1.8.0 também controla visibilidade do tool status overlay pela client config e integra Creative Tab Layouts para organização do creative tab.
## 12. Advancement tree
A release adiciona uma **advancement tree temática** para progressão Ignis Soulfires e tool interactions. Advancement observation não deve ser usado como segunda concessão de item/ability sem verificar reward real definido pelo provider.
## 13. Client/server e multiplayer
- item stats, upgrades, abilities, healing, retaliation, lava movement, prospecting, Tree Cap e Bulwark placement: server/common;
- models, trim palette, particles, overlay/config screen: client-side.
Em multiplayer, state temporário de abilities precisa ser limpo em disconnect/death; a 1.8.0 explicitamente corrige um leak de input temporário do Bulwark após disconnect.
## 14. Lifecycle
Validar equip/unequip, mount/dismount, lava enter/exit, thrown tool spawn/despawn, item pickup, death/respawn, disconnect/reconnect, chunk unload, config reload/restart e advancement progression. Habilidades não podem permanecer depois que o item/armor deixou de estar ativo.
## 15. Riscos
1. Double ability/damage via combat bridge.
2. Tree Cap duplicar block breaks/drops com outro felling mod.
3. Prospecting duplicado ou range divergente com outro scanner.
4. Bulwark placement usar reach fixo em vez do alcance real.
5. Temporary input state vazar após disconnect.
6. Horse armor modifiers não limpar ao desmontar/trocar armor.
7. Client config ser tratada como gameplay authority.
8. Hardcodar stats que agora são configuráveis.
## 16. Matriz de testes
1. Dedicated server boot com Cataclysm + Lionfish API + Curios.
2. Craft/upgrade chain de Souled Ignitium sem dupe.
3. Ignitium/Souled tools: throw, impact, recovery e particles.
4. Armor equip/unequip + lava behavior.
5. Horse armor: fire/lava/retaliation/healing/mounted movement.
6. Bulwark em alcance normal/aumentado e espaços 1×2.
7. Disconnect durante input do Bulwark — regressão 1.8.0.
8. Tree Cap junto de outro tree-felling provider.
9. Prospecting range/activation/config.
10. Client config/overlay sem mudar server state.
11. Multiplayer com dois jogadores usando abilities simultâneas.
## 17. Evidência
- modlist física atual: Ignis Soulfires 1.8.0;
- CurseForge/Modrinth oficiais da release 1.8.0;
- descrição oficial: Souled Ignitium materials, tools, weapons, armor, Bulwark e horse armor;
- changelog 1.8.0: horse armor, config expansion, advancement tree, particles, tool/armor changes, Bulwark fixes e disconnect-state fix;
- dependências oficiais: Cataclysm, Lionfish API e Curios API.
<callout icon="🔥" color="blue_bg">
	Boundary canônico: Cataclysm fornece a base temática/original; Ignis Soulfires controla **seus** materiais/equipamentos/abilities. Integrações devem observar o state final do provider, não duplicar efeitos pelo tema Ignitium.
</callout>


## 18. Atualização upstream 1.8.1 — não instalada
A autoridade física continua em **Ignis Soulfires 1.8.0**. A release **1.8.1** foi publicada em 15/09/2026.

Fixes oficiais:
- corrige **dedicated server crashes** causados por código client-only sendo carregado no servidor;
- refatora client access de **config screens, keybinds e tooltip input**.

Impacto: é um fix de classloading/side boundary de alta prioridade para servidor dedicado. Nenhuma das superfícies de config/keybind/tooltip deve ser referenciada em common/server paths durante bootstrap.

Gate de promoção 1.8.0→1.8.1: dedicated server cold boot; sem cliente conectado; config load; join/reconnect; keybinds/tooltip no cliente; Bulwark input; Tree Cap/prospecting; thrown tools; armor/horse armor; resource reload.

Fonte upstream: CurseForge file ID 8890501, `ignissoulfires-1.8.1.jar`.