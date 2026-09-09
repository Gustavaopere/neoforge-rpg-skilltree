# Backported Spells: Iron's Spells x Vanilla Backport — runtime 0.1.0 / JAR 0.1.2

> ⚠️ Autoridade física: arquivo `backportedspellbooks-0.1.2.jar`. Entretanto, o metadata carregado declara mod id `backportedspellbooks`, runtime name `Backported Spellbooks` e versão **`0.1.0`**. A divergência JAR 0.1.2 ↔ runtime 0.1.0 é preservada; `Versão 1.21.1` continua usando o metadata runtime.

## 1. Papel e autoridade
Backported Spells é um addon de **Iron's Spells 'n Spellbooks** que leva conteúdo inspirado em backports/recursos vanilla futuros para o sistema mágico do Iron's. Iron's permanece authority de spell casting, mana, cooldown, spell schools, scrolls e damage pipeline. Este addon é authority apenas dos spells/equipamentos/recursos que adiciona.

## 2. Conteúdo confirmado pela release pública 0.1.2
A página/changelog oficial da build cujo arquivo é 0.1.2 confirma:

### Equipamentos — 3
- **Miasmic Staff** — staff ligado a Nature/Hydro com habilidade especial.
- **Quicksilver Spellbook** — spellbook com foco em velocidade de casting e regeneração.
- **Slime Boots** — anulam fall damage e adicionam bounce.

### Spells — 4
- **Slime Aspect**
- **Sulfur Clouds**
- **Sulfur Bomb**
- **Sulfur Release**

### Sulfur Caves — 2 ores/recursos
- **Corroded Fossils**
- **Quicksilver**

Esses nomes são release-confirmed. Fórmulas de dano, custo, cooldown, níveis, crafting e registry IDs não são publicados aqui sem source/JAR inspection correspondente e não foram inventados.

## 3. Divergência de versionamento
O nome físico do JAR indica 0.1.2, mas o metadata runtime indica 0.1.0. Regras operacionais:
- presença = JAR 0.1.2;
- versão runtime/catalog field = 0.1.0;
- conteúdo 0.1.2 acima é sustentado pelo changelog oficial associado ao arquivo;
- não declarar que o código interno “é 0.1.2” sem metadata/tag binária que resolva a inconsistência.

Essa divergência deve permanecer visível para debugging e dependency checks.

## 4. Spells e causalidade
Os quatro spells pertencem ao pipeline Iron's. Integrações próprias devem observar:
- caster/owner original;
- mana/cooldown cobrados pelo provider;
- damage attribution/friendly-fire do Iron's;
- exactly-once application do efeito.

Não reaplicar dano/efeito porque outro sistema detectou o cast.

## 5. Equipamentos

### Miasmic Staff
É staff próprio do addon. Qualquer habilidade especial deve permanecer provider-native; não criar segundo proc externo sem confirmar hook.

### Quicksilver Spellbook
Foco documentado em casting/regen. Modificadores devem ser lidos do item/provider real e removidos ao desequipar; não hardcodar valores a partir da descrição pública.

### Slime Boots
A release confirma negação de fall damage e bounce. Riscos: interação com outros movement/fall-damage providers, double-bounce e imunidade duplicada.

## 6. Recursos de Sulfur Caves
Corroded Fossils e Quicksilver são conteúdo associado à temática Sulfur Caves da release. A ficha não presume worldgen, biome placement ou hard dependency específica de Vanilla Backport além do que foi confirmado publicamente. Se geração for datapack/biome-modifier, a authority deve permanecer no provider real.

## 7. Dependências
Iron's Spells 'n Spellbooks é dependência funcional do addon. O projeto é tematicamente ligado a Vanilla Backport, mas **hard dependency exata de Vanilla Backport não foi confirmada pela evidência pública usada**; portanto não é declarada como requisito obrigatório nesta ficha.

## 8. Client/server e lifecycle
- cast, dano, efeitos, fall-damage immunity/bounce e modifiers de equipamento são server-authoritative;
- modelos/particles/tooltips são cliente;
- equip/unequip, death, respawn, dimension change e logout não podem deixar modifiers ou state de bounce órfãos;
- spells com entidades/áreas precisam manter caster attribution após chunk transition.

## 9. Riscos
1. JAR/runtime version mismatch 0.1.2 ↔ 0.1.0.
2. Spell hooks duplicando dano/efeito com outros addons Iron's.
3. Slime Boots competindo com ParCool/Artifacts/outros fall/movement providers.
4. Quicksilver modifiers persistindo após unequip.
5. Dependência temática confundida com hard dependency.
6. Loot/worldgen de Sulfur content duplicado por integração externa.

## 10. Matriz de testes
1. Dedicated server boot com Iron's instalado.
2. Confirmar os quatro spells no registry/runtime e suas rotas de aquisição.
3. Mana/cooldown/damage exactly-once.
4. Staff/spellbook equip/unequip/death/respawn.
5. Slime Boots: fall, bounce, sneak/landing e interação com outros movement providers.
6. Sulfur resources: geração/loot sem duplicação, somente se presentes no runtime.
7. Verificar logs/dependency resolution usando metadata 0.1.0 apesar do filename 0.1.2.

## 11. Evidência
- modlist física atual: `backportedspellbooks-0.1.2.jar`;
- metadata runtime: `backportedspellbooks` / `0.1.0`;
- CurseForge oficial e changelog do arquivo 0.1.2 com 3 equipamentos, 4 spells e 2 recursos de Sulfur Caves.

> 🔒 Fail-closed: o catálogo não resolve artificialmente a divergência de versão e não inventa números/registry IDs ausentes. Isso preserva o valor da ficha para troubleshooting real.
