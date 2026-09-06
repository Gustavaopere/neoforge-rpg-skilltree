[← Índice do guia](README.md)

# 20. Scripting e compatibilidade técnica — KubeJS e EMF

Este capítulo cobre três mods presentes na modlist atual que são importantes para **integração**, mas não devem ser confundidos com sistemas mágicos autônomos.

## Iron's Spellbooks KubeJS — 4.0.3

`irons_spells_js-4.0.3.jar`

**KubeJS Iron's Spells** expõe partes relevantes de Iron's Spells 'n Spellbooks para scripts KubeJS. A documentação oficial lista criação de **custom spells**, **custom spell schools**, atributos de escola, itens especiais como staves/spellbooks/magic swords, event handlers (por exemplo mudança de mana), receitas de Alchemist Cauldron, integração com EntityJS para mobs que castam spells e suporte ProbeJS.

Isso transforma KubeJS em uma camada legítima para conteúdo data/script-driven do pack. Porém, a autoridade mecânica continua sendo Iron's: um script deve registrar/alterar conteúdo pelos pontos expostos pela bridge, não simular casting com eventos genéricos de dano. Para perks, uma spell criada em KubeJS deve entrar na mesma causalidade de casting do Iron's sempre que a bridge permitir.

A build 4.0.3 é NeoForge 1.21.1. O changelog público informa port para KubeJS 7.2. **Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs-irons-spells) · [Modrinth 4.0.3](https://modrinth.com/mod/isskjs/version/4.0.3) · [Wiki KubeJS](https://kubejs.com/wiki/addons/irons-spells) · [source](https://github.com/sentwayfarer/irons_spells_js).

## KubeJS Ars Nouveau — 1.3.2

`kubejsarsnouveau-1.3.2.jar`

**KubeJS Ars Nouveau** adiciona suporte KubeJS para receitas do Ars Nouveau. O projeto oficial o descreve como bridge para criação/manipulação de receitas Ars por scripts; portanto, é especialmente útil para unificação de progressão, gating, troca de ingredientes e compatibilidade com materiais de outros mods sem forkar o Ars.

A versão 1.3.2 é a release NeoForge 1.21.1 instalada e foi atualizada para a linha moderna de KubeJS. Essa bridge deve ser preferida para **receitas Ars** quando ela cobre o tipo necessário. Ela não equivale à API de casting/glyphs do Ars e não deve ser usada como prova de que qualquer comportamento de spell pode ser interceptado por KubeJS.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs-ars-nouveau) · [Wiki](https://wiki.latvian.dev/books/kubejs/page/kubejs-ars-nouveau) · [source](https://github.com/BobVarioa/kjsarsnouveau). A indexação pública encontrada no Modrinth não confirma uma build 1.21.1 equivalente; por isso o guia não inventa um link de versão Modrinth.

## EMF Compat: Iron's Spells — 2.0.0

`emf_compat_iron_spells_1.21.1_2.0.0.jar`

**EMF Compat: Iron's Spells 'n Spellbooks** é uma compatibilidade **client-side de animação**. Ela corrige poses de casting do Iron's quando o jogador usa modelos animados por Entity Model Features (EMF), permitindo que a animação de spell controle os braços enquanto outras partes do corpo continuam sob o modelo/animação EMF. O projeto cita compatibilidade com Fresh Animations Player Extension e outros resource packs de player model.

Para integração, esse mod é **presentation-only**: não deve ser usado para detectar cast, mana, cooldown, hit ou autoridade de spell. Se uma perk depende de animação, o estado de gameplay deve vir do provider (Iron's) e o compat apenas representar esse estado visualmente.

**Fontes:** [Modrinth](https://modrinth.com/mod/emf-compat-irons-spells-n-spellbooks) · [source monorepo](https://github.com/victorkozhokin/emf-compat).

## Regras de uso no projeto integrado

- KubeJS é excelente para **conteúdo configurável e recipes**, mas não substitui hooks server-authoritative quando a mecânica exige causalidade de combate/casting.
- Bridges KubeJS devem falhar fechado se o addon/provider não estiver carregado ou a API esperada mudar.
- EMF/Fresh Animations são clientes de apresentação; nunca derivar regra de gameplay de pose/render.
- Quando scripts e Java puderem executar a mesma integração, escolher **um pipeline canônico** e deduplicar o outro.
