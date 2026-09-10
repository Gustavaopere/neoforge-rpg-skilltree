# Malum

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8177a460e1d28e63ea3e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Malum
- **Arquivo JAR:** `malum-1.21.1-1.8.2.jar`
- **Versão 1.21.1:** 1.8.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Sistema completo de Spirit Arcana: coleta/manipulação de espíritos, Encyclopedia Arcana, Spirit Infusion/Focusing, Runeworking, Soul Binding, Spirit Rites, Geasa, Soul Ward, staffs, equipamentos, progressão, worldgen e integrações mágicas. A build 1.8.2 registra recipe types, attachments, registries e hooks próprios; Malum é a autoridade sobre sua economia de espíritos e progressão.
- **Dependências:** Lodestone 1.8.2+ e Curios API conforme distribuição/ecossistema da build; integração opcional explícita com Iron's Spells 'n Spellbooks. Wayward Attributes aparece no build de desenvolvimento da 1.8.2, mas não foi promovido a hard dependency apenas por essa evidência.
- **Sobreposição:** Sobreposição funcional parcial com outros sistemas mágicos/RPG do pack em dano mágico, atributos, defesa, loot/progressão e recursos; não é redundância direta. Malum mantém authority sobre spirits, Spirit Arcana, Soul Ward, Geasa, rites e recipes próprios; Iron's/Curios/outros providers continuam autoridade sobre seus recursos e apenas integram por hooks confirmados.
- **Compatibilidade/Riscos:** Integração de código confirmada com Iron's Spells para Soul Damage/spirit drops; Curios e Lodestone integram a infraestrutura. Riscos principais: duplicação/alteração de spirit drops, stacking de Soul Ward/Geasa/Avarice com atributos globais, listeners duplicados em BlockDropsEvent de Spellwoven Sprites, drift de recipes/datapacks, estado stale em attachments após relog/death/dimension change, conflito de mixins em LivingEntity/player/explosion/render e regressões de desempenho no Weeping Well/Parallel World.
- **Observações:** Runtime físico 1.8.2. Source oficial pinado ao commit 03b743a37f3eeb0cc7f4364f0730e1f135f78408. Configs e mudanças 1.8.2 documentadas no corpo, incluindo hide_recipes, codex drop, Soul Ward, staff charge, Avarice e renderParallelWorld. Não projetar mudanças da linha 1.9.x nesta build.
- **Procedência:** modlist.txt física atual de 08/09/2026 + JAR metadata exposta pela modlist + CurseForge/changelog oficial da 1.8.2 + source oficial SammySemicolon/Malum-Mod pinado ao commit 03b743a37f3eeb0cc7f4364f0730e1f135f78408.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/malum
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.8.2 pinado; Spirit Arcana, 7 recipe types, attachments persistentes/sincronizados, integração com Iron's, Soul Ward/Geasa/Avarice, worldgen, mixins, lifecycle, riscos e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🔮 **Escopo canônico desta ficha:** `malum-1.21.1-1.8.2.jar`, exatamente o JAR presente na modlist física. O source oficial foi pinado ao commit `03b743a37f3eeb0cc7f4364f0730e1f135f78408`, cujo `gradle.properties` declara `mod_version=1.8.2`, Minecraft 1.21.1 e NeoForge 21.1.213. Mudanças da linha 1.9.x não são projetadas sobre esta build.

## 1. Identidade, versão e autoridade
- **Mod:** Malum.
- **JAR no pack:** `malum-1.21.1-1.8.2.jar`.
- **Versão física:** 1.8.2 para Minecraft 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21 no projeto da build.
- **Mod ID:** `malum`.
- **Descrição do próprio source:** mod de magia sombria focado em magia de alma e espíritos.
- **Upstream:** `SammySemicolon/Malum-Mod`.
- **Commit pinado:** `03b743a37f3eeb0cc7f4364f0730e1f135f78408` — 1.8.2.

## 2. Papel funcional no pack
Malum é um sistema mágico/progressivo completo, não apenas um conjunto de itens. A linha 1.8.2 registra blocos, block entities, itens, entidades, efeitos, partículas, sons, containers, atributos, recipes, worldgen, estruturas, attachments, eventos de mundo, tipos de espírito, componentes de encantamento, Spirit Rites e Geasa.
Sua progressão temática gira em torno de **Spirit Arcana**: obtenção e manipulação de espíritos, conhecimento via Encyclopedia Arcana, Spirit Infusion, Spirit Focusing, Runeworking, Soul Binding, ritos/totens, equipamentos e efeitos próprios.

## 3. Progressão e Encyclopedia Arcana
A configuração comum confirma dois comportamentos centrais:
- `hide_recipes=true`: por padrão, parte do conteúdo ligado a uma feature é ocultada; o comentário do próprio código afirma que essa é a forma pretendida de jogar o mod.
- `enableCodexDrop=true`: um dos primeiros inimigos mortos-vivos abatidos pelo jogador pode dropar a **Encyclopedia Arcana**.
Logo, a Encyclopedia não deve ser tratada como livro meramente decorativo: ela faz parte do onboarding/progressão prevista pelo mod. Alterar `hide_recipes` ou distribuir o codex por quests muda materialmente a curva de descoberta.

## 4. Sistemas de crafting/magia registrados na 1.8.2
`MalumRecipeTypes.java` do commit exato registra sete recipe types:
- `spirit_infusion`;
- `runeworking`;
- `soul_binding`;
- `spirit_focusing`;
- `unchained_transmutation`;
- `spirit_repair`;
- `void_favor`.
Isso confirma que a progressão possui processos próprios e não depende apenas de crafting table. Recipes/datapacks que alterem esses tipos interferem diretamente na progressão de Malum.

### Spirit Infusion
É um dos processos mágicos centrais do mod e transforma materiais usando a infraestrutura de Spirit Arcana. O projeto o registra como recipe type próprio, portanto receitas são parte da camada data-driven e devem ser auditadas após alterações de datapack.

### Spirit Focusing
Também possui recipe type próprio. A documentação pública da build descreve o uso de Impetus/Spirit Focusing para transformar Spirit Crystals em matéria física, integrando obtenção de espíritos à produção de recursos.

### Runeworking, Soul Binding e Spirit Repair
São processos independentes no registry 1.8.2. Devem ser tratados como superfícies de compatibilidade para JEI/datapacks e para qualquer mod próprio que pretenda gatear progressão.

## 5. Espíritos, Soul Damage e drops
A configuração 1.8.2 expõe regras que afetam geração/apresentação de espíritos:
- `noFancySpirits=false`: por padrão, espíritos não são reduzidos a simples forma de item.
- `lameSpawners=false`: spawners não criam mobs "soulless" por padrão.
- `defaultSpiritValues=true`: entidades sem JSON específico de espírito usam dados padrão da categoria.
A existência de dados default por categoria significa que mobs de outros mods podem participar do ecossistema de spirit drops sem necessariamente possuir um JSON dedicado. Isso amplia a integração com uma modlist grande, mas exige teste de balanceamento para criaturas de alta frequência/alto poder.

## 6. Integração explícita com Iron's Spells 'n Spellbooks
A 1.8.2 contém compatibilidade inicializada em `MalumMod` e dois toggles comuns específicos:
- `ironsSpellbooksPlayerSpiritDrops=true`: dano mágico do Iron's causado por jogador pode contar como **Soul Damage**;
- `ironsSpellbooksNonPlayerSpiritDrops=true`: o mesmo pode valer para dano mágico do Iron's causado por não-jogadores.
Essa é uma integração de código/configuração confirmada. Em um pack com Iron's Spells, abates mágicos podem participar da lógica de obtenção de espíritos de Malum; isso deve ser testado com spells de player, summons/entidades e fontes indiretas.

## 7. Soul Ward, Malignant Aegis e defesa
A configuração comum da build define:
- `soulWardPhysical=0.7`: multiplicador de dano físico enquanto Soul Ward está ativo;
- `soulWardMagic=0.1`: multiplicador de dano mágico enquanto Soul Ward está ativo;
- `soulWardRate=100` ticks: tempo base por ponto de recuperação de Soul Ward;
- `malignantAegisRate=200` ticks: tempo base por ponto de recuperação de Malignant Aegis.
Também existe `magicDamageReducedArmorPiercing=true`: tipos de dano marcados em `malum:bypasses_half_armor` perfuram apenas metade da armadura, em vez de ignorá-la totalmente, e a armadura não recebe dano de durabilidade conforme o comentário do config.
Esses valores são knobs reais de balanceamento e devem ser considerados junto de sistemas globais de atributos, armaduras, dificuldade e magia do pack.

## 8. Staff abilities e recarga
`staffChargeRate=100` ticks define o tempo base para recuperar um segmento de carga de staff. A 1.8.2 também persiste/sincroniza `StaffAbilityData` por attachment, portanto carga/habilidades não são apenas feedback visual.

## 9. Persistência e sincronização via NeoForge Attachments
`MalumAttachmentTypes.java` da 1.8.2 registra estado serializável próprio para vários subsistemas:
- `living_soul_info` — serializado e `copyOnDeath`;
- `projectile_soul_info` — serializado;
- `geas_soul_info` — serializado, sincronizado e `copyOnDeath`;
- `avarice_mark` — serializado e sincronizado;
- `cached_spirit_drops` — serializado;
- `progression_data` — serializado e `copyOnDeath`;
- `curio_data` — serializado e sincronizado;
- `soul_ward` — serializado e sincronizado;
- `staff_abilities` — serializado e sincronizado;
- `wind_tunnel_info` — serializado e sincronizado;
- `weeping_well_info` — serializado;
- `touch_of_darkness` — serializado;
- `malignant_influence` — serializado e sincronizado.
Consequência: relog, morte/respawn e sincronização cliente-servidor fazem parte do contrato técnico da progressão. Sistemas de KubeJS/mods próprios que alterem jogador/atributos não devem sobrescrever esses estados sem integração consciente.

## 10. Totens, Spirit Rites e Geasa
A inicialização da 1.8.2 registra `MalumSpiritRiteEffectTypes`, `MalumSpiritRiteTypes` e `MalumGeasEffectTypes` como registries próprios. A documentação oficial descreve Spirit Runes/Totem Base e combinações de espíritos como base para efeitos de totem.
Geasa são outra camada de modificadores/condições. Na 1.8.2, o changelog altera especificamente vários Geasa, mostrando que eles participam ativamente de balanceamento:
- **Lone Druid:** passa a conceder Healing Received;
- **High Priest:** downside passa a desacelerar em vez de causar dano e há rebalanceamento de alcance;
- **Prospector:** concede Avarice; Avarice do Prospector cura;
- **Pyromaniac → Blastweaver:** renomeado, amplia explosões, aplica Avarice próximo a explosões e aumenta dano de explosão recebido como downside.

## 11. Avarice e Curios — mudanças da 1.8.2
O changelog oficial da build instalada registra:
- Belt of the Prospector: 50% de chance de aplicar Avarice a blocos valiosos;
- Avarice: +10% de chance de Fortune por stack, até 100%;
- Avarice deixa de proteger blocos valiosos de explosões.
Isso interfere diretamente em mineração/economia. Em um pack com muitos minérios e modificadores de Fortune, a combinação deve ser testada para evitar multiplicação excessiva de recursos.

## 12. Healing Rite e eventos de bloco — mudanças da 1.8.2
- **Healing Rite:** agora só dispara quando a cura realmente produz efeito; evita consumo/ativação sem benefício efetivo.
- **Spellwoven Sprites:** passaram a quebrar blocos como o jogador e a emitir o evento NeoForge `BlockDropsEvent`, com o jogador como breaker.
O segundo ponto é especialmente relevante para compatibilidade: mods/listeners que alteram drops via `BlockDropsEvent` podem agora enxergar a quebra causada por Spellwoven Sprites como quebra atribuída ao jogador. Isso muda interação com mineração, loot, claims e progressão baseada em eventos.

## 13. Worldgen e conteúdo ambiental
A inicialização registra feature types, structure types e structure piece types próprios. A documentação do projeto apresenta elementos ambientais como Runewood Trees, Blight, recursos/minérios e estruturas ligadas à progressão.
Como existe worldgen próprio, remoção/atualização do mod após criação do mundo deve ser tratada com cautela. O dossiê não presume regeneração retroativa de conteúdo em chunks já gerados.

## 14. Mixins e superfície de conflito
`malum.mixins.json` da 1.8.2 declara 14 mixins comuns e 10 client-side. Os alvos incluem, entre outros:
- atributos;
- block behavior;
- entidades/living entities/player;
- explosions;
- food data;
- item stacks;
- projectiles/fishing hook;
- mob effects;
- renderização de Elytra/item-in-hand/font/creative inventory/client player.
A configuração usa `required=true` e `defaultRequire=1`. Isso amplia a superfície de compatibilidade com mods que também alteram combate, atributos, explosões, player lifecycle ou renderização. Conflito, porém, só deve ser declarado quando houver evidência de log/mixin audit; a coexistência de alvos não prova incompatibilidade.

## 15. Configuração client-side
A 1.8.2 expõe:
- `bookTheme=DEFAULT`;
- `scrollDirection=false` para a Encyclopedia;
- offsets HUD de Soul Ward/Malignant Aegis: `shieldX=0`, `shieldY=47`;
- `scarfLength=30`;
- `scarfOpacity=0.4` em primeira pessoa;
- `renderParallelWorld=true`, com comentário explícito para desabilitar se causar problemas de framerate.
A 1.8.2 também corrigiu lag substancial do novo renderer do **Weeping Well** em alguns sistemas e corrigiu ordenação de render do **Wind Tunnel**. Em investigação de FPS, esses sistemas merecem isolamento antes de atribuir o problema genericamente a shaders.

## 16. Dependências e integrações confirmadas
- **Lodestone:** dependência central no source; a 1.8.2 compila contra Lodestone `1.8.2.523` e declara range a partir de 1.8.2 no projeto.
- **Curios API:** integrado e listado como required na distribuição oficial.
- **Wayward Attributes:** o build da 1.8.2 inclui a biblioteca em compile/runtime de desenvolvimento; a modlist física também contém Wayward Attributes. Sem metadata do JAR aberto nesta sessão, não reclassificar essa evidência como dependência obrigatória de distribuição apenas com base no Gradle.
- **Iron's Spells 'n Spellbooks:** integração opcional explícita e configurável.
- **Farmer's Delight / Create / Tetra / AttributeLib:** existem classes/rotinas de compat inicializadas no código; isso não significa que todos sejam dependências obrigatórias.
- **KubeJS:** o source possui integração/plugin e dependência de ambiente de desenvolvimento/runtime de teste; não tratar essa linha do Gradle, isoladamente, como hard dependency para o usuário final.

## 17. Riscos operacionais no pack
1. **Economia de spirits:** `defaultSpiritValues=true` pode estender spirit drops a entidades modded; testar farms e mobs muito comuns.
2. **Avarice/Fortune:** pode amplificar mineração em combinação com outros sistemas de Fortune/atributos.
3. **Defesa:** Soul Ward/Malignant Aegis interagem com dano físico/mágico e podem empilhar com armaduras/perks.
4. **Iron's:** dano mágico contar como Soul Damage altera aquisição de spirits; testar sources indiretas e summons.
5. **Persistência:** progression/geasa/soul ward/staff data usam attachments; relog e respawn precisam de teste.
6. **Mixins:** alto número de intervenções em player/combat/explosion/client aumenta necessidade de inspeção de logs quando houver conflito.
7. **Worldgen:** atualização/remoção no meio de um mundo pode deixar conteúdo antigo em chunks já gerados.
8. **Renderer:** Parallel World pode ser desligado por config se houver impacto de FPS; Weeping Well teve fix específico de performance em 1.8.2.

## 18. Matriz mínima de testes para este pack
- **Progressão inicial:** matar undeads até obter a Encyclopedia e validar conteúdo oculto/desbloqueio com `hide_recipes=true`.
- **Spirit drops:** testar mob vanilla e pelo menos um mob de outro mod sem JSON específico.
- **Iron's:** abate com spell do jogador e fonte não-player; validar spirit drop conforme os toggles.
- **Soul Ward/Aegis:** validar mitigação, regeneração e sincronização HUD em servidor dedicado.
- **Staff:** consumir/recuperar cargas e relogar.
- **Attachments:** relogar e morrer/respawnar observando `progression_data`, Geasa e estados marcados `copyOnDeath`.
- **Avarice:** testar bloco valioso/Fortune e explosão; verificar multiplicação econômica.
- **Spellwoven Sprite:** quebrar bloco e verificar listeners/drops que dependem de `BlockDropsEvent`.
- **Worldgen:** localizar conteúdo Malum em mundo novo e confirmar ausência de erros de placement/structure.
- **Performance:** testar Weeping Well/Parallel World e observar FPS/frametime; desabilitar `renderParallelWorld` apenas como teste controlado.
- **Recipe viewers/datapacks:** confirmar os sete recipe types usados pelo pack após `/reload`/restart apropriado.

## 19. Evidências consultadas
- Modlist física: `malum-1.21.1-1.8.2.jar`.
- CurseForge oficial do Malum e changelog da release 1.8.2.
- Source oficial: https://github.com/SammySemicolon/Malum-Mod
- Commit pinado 1.8.2: https://github.com/SammySemicolon/Malum-Mod/commit/03b743a37f3eeb0cc7f4364f0730e1f135f78408
- `gradle.properties` 1.8.2: MC 1.21.1, NeoForge 21.1.213, Lodestone 1.8.2.523 e demais versões de desenvolvimento.
- `MalumMod.java`: registries e inicialização de compats.
- `CommonConfig.java` / `ClientConfig.java`: defaults e knobs de gameplay/render.
- `MalumRecipeTypes.java`: sete processos mágicos registrados.
- `MalumAttachmentTypes.java`: persistência/sincronização de progressão e estados.
- `malum.mixins.json`: superfície de mixins comum/client.

> ✅ **Authority forte:** o código foi pinado exatamente à 1.8.2 instalada. Mudanças posteriores da 1.9.x não foram usadas para redefinir comportamento desta ficha.
