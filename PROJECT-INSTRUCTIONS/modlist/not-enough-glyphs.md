# Not Enough Glyphs

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8140a55df34b399ba6a4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `not_enough_glyphs-1.21.1-4.6.1.jar`, mod id `not_enough_glyphs`, runtime `4.6.1`, mixin `not_enough_glyphs.mixins.json`; Ars Nouveau `5.13.1`; Sauce Library `0.0.42.89` confirmado somente em `META-INF/jarjar/` com `sauce.mixins.json`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Not Enough Glyphs 4.6.1, Ars Nouveau 5.13.1 e Sauce 0.0.42.89 embarcado estão confirmados.

## Propriedades do banco

- **Mod:** Not Enough Glyphs
- **Arquivo JAR:** `not_enough_glyphs-1.21.1-4.6.1.jar`
- **Versão 1.21.1:** 4.6.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Ars Nouveau com novos glyphs/forms/filters, Contingencies, SpellBinder para até 25 spells, Book Covers e repacks compatíveis que se desabilitam quando o addon original está presente.
- **Dependências:** Ars Nouveau — Required Dependency oficial. SauceLib — Embedded Library; JAR físico contém Sauce 0.0.42.89 em META-INF/jarjar, não top-level.
- **Sobreposição:** Pode replicar glyphs de addons antigos, mas os repacks foram projetados para auto-disable quando o original existe. Avaliar duplicação por glyph/efeito concreto, não pelo domínio Ars geral.
- **Compatibilidade/Riscos:** Ars addon com alto potencial combinatório. Riscos: Trail/Plane multiplicando spell resolution, contingency lifecycle, SpellBinder/namespace migration, repack duplication e embedded Sauce drift. Ars Nouveau físico 5.13.1.
- **Observações:** Runtime 4.6.1. Mixin físico `not_enough_glyphs.mixins.json`; Sauce 0.0.42.89 embarcado com `sauce.mixins.json`. Linha 4.6 migrou namespaces de Book Covers e usa Sauce 0.0.42 para Ars 5.12+.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais + documentação pública da linha 4.6.1 + JAR embedded metadata físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/not-enough-glyphs
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Not Enough Glyphs 4.6.1 reconstruído: Ars/Sauce dependencies, Trail/Plow/Plane, Contingencies, SpellBinder, Book Covers, repacks/auto-disable, migrations, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `not_enough_glyphs-1.21.1-4.6.1.jar`, mod id `not_enough_glyphs`, versão `4.6.1`, NeoForge 1.21.1. Ars Nouveau é Required Dependency. O JAR embute `sauce-1.21.1-0.0.42.89.jar` em `META-INF/jarjar`; Sauce Library pertence ao host e **não** é entrada top-level. O addon amplia o vocabulário de spellcraft e adiciona SpellBinder/Book Covers, exigindo atenção a persistência e migração de dados.

## 1. Identidade e papel
- **Mod:** Not Enough Glyphs.
- **JAR físico:** `not_enough_glyphs-1.21.1-4.6.1.jar`.
- **Mod id:** `not_enough_glyphs`.
- **Runtime:** `4.6.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Alexth99.
- **Licença:** LGPLv3.
- **Ambiente:** Client & Server.
- **Papel:** addon de Ars Nouveau que adiciona glyphs/forms/filters, spellcraft utilitário e uma camada própria de armazenamento/seleção de spells via SpellBinder.

## 2. Dependências e bibliotecas embarcadas
A relação oficial lista:
- **Ars Nouveau — Required Dependency**;
- **SauceLib — Embedded Library**.

O pack físico possui Ars Nouveau `5.13.1`.

Dentro do JAR existe `META-INF/jarjar/sauce-1.21.1-0.0.42.89.jar`, mod id `sauce`, versão `0.0.42.89`, com `sauce.mixins.json`.

Pelo protocolo:
- Sauce não recebe ordinal próprio;
- versionamento/risco fica sob Not Enough Glyphs;
- erro em Sauce pode aparecer no stacktrace, mas pertence à composição embarcada do host.

## 3. Ownership no ecossistema Ars Nouveau
Ars Nouveau continua authority de:
- spell recipe/framework base;
- mana/casting;
- glyph registry/framework;
- spellbook base e seus contratos.

Not Enough Glyphs adiciona conteúdo e comportamentos sobre essa infraestrutura. Integrações próprias devem referenciar glyph IDs reais da build e não duplicar engine de spell resolution.

## 4. Glyph Trail / Echoing Projectile
O projeto documenta um glyph de **Trail / Echoing Projectile** que resolve o spell ao longo da trajetória múltiplas vezes.

Essa forma é tecnicamente sensível porque pode multiplicar resoluções de efeitos. Riscos:
- alta frequência de entidades/particles;
- dano ou efeitos repetidos mais vezes do que esperado;
- combinação com AoE/linger levando a crescimento de custo;
- server tick pressure em spells complexos.

O número exato de resoluções/configuração deve ser obtido do runtime/config, não inferido.

## 5. Plow
O glyph **Plow** acrescenta ação utilitária de spellcraft ligada ao trabalho de terreno. Como qualquer glyph que modifica blocos, o servidor deve ser authority de cada alteração.

Testes precisam cobrir proteção/claims, blocos modded e combinação com augments sensíveis. Esta ficha não inventa tags de blocos ou raio.

## 6. Plane
**Plane** é uma forma não repetitiva semelhante em intenção a linger, voltada a criar áreas planas/círculos/cilindros/quadrados vazados conforme a composição do spell.

Superfície de risco:
- custo cresce com tamanho/augments;
- grande quantidade de posições pode ser processada por cast;
- spell de bloco/dano aplicado em Plane pode afetar área extensa.

Em server público, limits/configs devem ser considerados antes de liberar combinações irrestritas.

## 7. Contingencies
Na linha 1.21.1+, **Contingencies** armazenam o spell seguinte por uma duração de efeito e o disparam quando a condição correspondente acontece.

O projeto documenta princípios importantes:
- mana é cobrada no cast inicial;
- apenas **uma contingency ativa por vez**;
- o efeito fica aguardando o gatilho.

Isso cria estado temporário por jogador e exige testes em death, logout/reconnect, dimension change e efeito expirando sem disparo.

## 8. SpellBinder
O **SpellBinder** é alternativa ao spellbook para organizar spells. A documentação atual informa capacidade para até **25 spells**, armazenados como caster tomes/parchments dentro do sistema do binder.

Diferentemente de um menu puramente visual, o binder representa dados do jogador/item e deve conservar:
- lista de spells;
- seleção atual;
- ordem/slots;
- upgrades/covers aplicados quando houver;
- migração ao atualizar versão.

## 9. Book Threads / Book Covers
A linha recente renomeou **Book Threads → Book Covers** e moveu namespaces/conteúdo associado. O projeto inclui covers/threads com funções como:
- **Focus** — variantes para Shapers/Summoning e Elemental quando Ars Elemental está carregado;
- **Randomize**;
- **Slow Power**;
- **Cheap Damage**;
- **Sharp Pages**;
- **Hard Cover**.

Esses nomes descrevem famílias publicadas; operações numéricas exatas só devem ser catalogadas a partir dos dados/configs reais.

## 10. Repacked glyphs e auto-disable
O addon republica/reimplementa glyphs inspirados em addons antigos/ausentes. A documentação informa que **repacks se desabilitam quando o addon original correspondente está presente**, inclusive para preservar comportamentos customizados do original.

Famílias publicadas incluem:
- TMG: Ray, Chain, Reverse/Redirect Direction e vários filters;
- Omega: propagators derivados de forms vanilla + Flatten;
- Elemental: Arc/Homing Projectile e propagators;
- Trinkets: Filter Self / Not Self.

Isso reduz duplicação, mas exige teste com o conjunto Ars real do pack para garantir que apenas uma implementation fique ativa.

## 11. Linha 4.5–4.6 e migração
Notas recentes da linha atual incluem:
- **4.6.0:** Sauce 0.0.42 para Ars Nouveau 5.12+ e migração de namespaces dos Book Covers do namespace Ars Nouveau para NEG; a migração é descrita como destinada a ser lossless, mas backup é recomendado;
- **4.5.1:** configs para boost de stuffed-crush/foodXplosion e blacklist de entities para Ride; Random filter movido para namespace Ars Controle;
- **4.5.0:** proteção temporária contra world break em upgrade de SpellBinder, além de Ride/Feed/Random filter;
- **4.4.0:** Book Threads renomeados para Book Covers e SpellBinders passam a abrir Glyph Unlock Screen.

Essas notas são lineage atual, não “mudanças exclusivas de 4.6.1” se o changelog não as atribui diretamente à subversão .1.

## 12. Config/data e reload
O addon possui conteúdo data-driven/configurável em várias superfícies. Pontos sensíveis:
- glyph IDs/namespaces;
- entity blacklists;
- comportamento de damage/food glyphs;
- binder/covers;
- compat self-disable.

`/reload` e resource reload devem manter registry/recipes/visualização coerentes. Não alterar IDs de datapack/KubeJS por memória de versões antigas.

## 13. Client/server e multiplayer
Server authority:
- cast resolution;
- mana/cost;
- dano/efeitos;
- block modification;
- contingency trigger;
- binder data quando sincronizada.

Client-facing:
- glyph UI/icons;
- SpellBinder/Glyph Unlock screens;
- particles/rendering.

Dois clientes precisam observar efeitos equivalentes sem duplicar trigger ou cast.

## 14. Integrações no pack
O pack contém Ars Nouveau 5.13.1 e vários addons Ars. Portanto avaliar:
- glyphs que o NEG auto-desabilita na presença do original;
- Ars Elemental para Focus Elemental e conteúdo sobreposto;
- KubeJS/quests que referenciem namespaces antigos;
- limites de spell complexity/performance.

## 15. Riscos
1. **Spell amplification/performance:** Trail/Plane podem multiplicar resoluções.
2. **Contingency lifecycle:** logout/death/dimension pode deixar estado incorreto se houver regressão.
3. **SpellBinder migration:** atualizações já exigiram proteção contra world break.
4. **Namespace migration:** Book Covers e Random filter tiveram mudanças de namespace.
5. **Duplicate repacks:** original + repack precisa auto-disable corretamente.
6. **Embedded Sauce drift:** library embarcada deve permanecer coerente com Ars 5.13.1.
7. **Block-affecting glyphs:** permissões/claims devem ser respeitadas.
8. **Power creep:** muitos novos forms/filters ampliam drasticamente combinações de Ars.

## 16. Matriz de testes
- [ ] Dedicated server + cliente iniciam com Ars Nouveau 5.13.1 + NEG 4.6.1.
- [ ] Sauce 0.0.42.89 carrega apenas como embedded component, sem duplicate mod conflict.
- [ ] Trail resolve spell de teste múltiplas vezes sem runaway entities/ticks.
- [ ] Plane executa área moderada e grande com limites aceitáveis.
- [ ] Plow respeita blocos/claims válidos.
- [ ] Contingency ativa uma por vez, cobra mana no momento correto e dispara uma vez.
- [ ] Logout/reconnect/death/dimension limpam ou preservam contingency conforme contrato real, sem duplo trigger.
- [ ] SpellBinder conserva até 25 spells, seleção e covers após relog/restart.
- [ ] Atualização/migração de Book Covers não perde conteúdo do binder.
- [ ] Addon original presente desabilita repack correspondente sem duplicate glyph ID.
- [ ] `/reload` mantém glyphs/configs/namespaces estáveis.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física: `not_enough_glyphs-1.21.1-4.6.1.jar`, mod id/version, `not_enough_glyphs.mixins.json` e Sauce 0.0.42.89 embarcado.
- CurseForge oficial: project 1023517, Release NeoForge 1.21.1 de 23/06/2026, Ars Nouveau required e SauceLib embedded.
- Página oficial: Trail, Plow, Plane, Contingencies, SpellBinder, Book Covers e repacked glyphs.
- Lineage 4.4–4.6: migrations/configs/compat descritas acima.
- **Limite:** valores numéricos, registry IDs e comportamento interno não publicados não foram inventados; consultar JAR/data/config antes de scripting.
