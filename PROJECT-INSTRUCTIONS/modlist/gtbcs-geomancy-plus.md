# GTBC's Geomancy Plus — 1.1.0-1.21.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db811f9806c5e0035a1bba  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** GTBC's Geomancy Plus
- **Arquivo JAR:** `gtbcs_geomancy_plus-1.1.0-1.21.1.jar`
- **Versão 1.21.1:** `1.1.0-1.21.1`
- **Categoria:** Magia; RPG; Compat
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/gtbcs-geomancy-plus/files/7041615
- **Função:** Addon de Iron's Spells 'n Spellbooks + Mowzie's Mobs que introduz a escola Geo, spells/itens próprios e integra equipamentos/chefes de Mowzie's à progressão mágica do Iron's.
- **Dependências:** Iron's Spells 'n Spellbooks 1.21.1-3.16.3 + Mowzie's Mobs 1.8.2 + GTBC's SpellLib 2.2.0-1.21.1, todos presentes fisicamente.
- **Compatibilidade/Riscos:** Riscos: cast-time reduction causando animation desync em Petrivise/Tremor Spike/Pillar, school/attribute drift após update de Iron's ou SpellLib, bonus Geo duplicado em equipamentos Mowzie, loot/progression de Umvuthi duplicado e mixin conflict. Release 1.1.0 corrige/desativa redução de cast time nos três spells citados.
- **Sobreposição:** Sobrepõe tematicamente outras magias de terra/geomancia, inclusive Goety, mas a authority da escola Geo/spells deste addon permanece no ecossistema Iron's/GTBC. Não unificar schools, mana, attributes ou settlement por semelhança temática.
- **Observações:** Baseline público da linha 1.1.0: 10 spells Geo próprios, 2 spells Holy adicionais e reclassificação de Earthquake/Stomp para Geo. 1.1.0 adiciona Geo Spell Power a Earthrend Gauntlet e Sculptor Staff e documenta Solar Beam/Solar Storm como obtidos via Umvuthi.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial Geomancy Plus 1.1.0 NeoForge file 7041615 + descrição/changelog oficial; conteúdo-base cruzado com release 1.0.0 e declaração 1.1.0 de port 100% da linha 1.20.1. Source público exato não localizado.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — GTBC's Geomancy Plus 1.1.0; escola Geo, spells/equipamentos, Iron's/Mowzie/SpellLib authority, cast-time desync fixes, progression, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `gtbcs_geomancy_plus-1.1.0-1.21.1.jar`, mod id `gtbcs_geomancy_plus`, versão `1.1.0-1.21.1`, NeoForge 1.21.1. A release oficial file `7041615` declara que a build 1.21.1 é um **port 100% da versão 1.20.1** da linha 1.1.0. Não foi localizado source público exato auditável; classes, registry IDs e internals permanecem fail-closed.

## 1. Papel no modpack
GTBC's Geomancy Plus é addon de **Iron's Spells 'n Spellbooks** e **Mowzie's Mobs**. Ele introduz a escola **Geo**, expande o catálogo de spells do Iron's com magia de terra/pedra e adiciona conteúdo inspirado em bosses/abilities de Mowzie's Mobs.

## 2. Authority / ownership
- **Iron's Spells 'n Spellbooks:** mana, spell framework, spell school infrastructure, casting/cooldown/attributes-base.
- **Mowzie's Mobs:** bosses, entidades, equipamentos e conteúdo-base usados como inspiração/integração.
- **GTBC's SpellLib:** API/utilidades compartilhadas do autor.
- **Geomancy Plus:** escola Geo e conteúdo/spells/equipamentos/bridges que registra.
Não criar uma terceira authority para mana, cast settlement ou boss state.

## 3. Dependências físicas atuais
A modlist confirma:
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`;
- Mowzie's Mobs `1.8.2`;
- GTBC's SpellLib `2.2.0-1.21.1`;
- Geomancy Plus `1.1.0-1.21.1`.
Essas quatro versões formam a matriz real do pack e devem ser regression-tested juntas.

## 4. Escola Geo e conteúdo-base público
A release pública original da linha lista 10 spells Geo próprios:
- Chunker;
- Eroding Boulder;
- Fissure;
- Seismic Surf;
- Tremor Spike;
- Tremor Step;
- Geo Conductor;
- Pillar of the Resounding Earth;
- Dripstone Bolt;
- Petrivise.
Também lista **Solar Beam** e **Solar Storm** como spells Holy adicionais.
Separadamente, **Earthquake** e **Stomp** do Iron's são reclassificados para a escola Geo. Essa mudança de school pode exigir atenção a configs/data antigas do Iron's.

## 5. Itens/equipamentos publicamente confirmados
O baseline publicado inclui:
- Geo Rune;
- Geo Upgrade Orb;
- Bluff Instruction Manual;
- Codex of Shattered Horizons;
- The Stonecaller.
IDs de registry, recipes e stats não são afirmados sem source/JAR enumeration exata.

## 6. Integração com Mowzie's Mobs
A linha documenta bônus mágicos para equipamento de Mowzie's. Na 1.1.0, **Earthrend Gauntlet** e **Sculptor Staff** receberam bônus de **Geo Spell Power**. A documentação também registra que Geomancer armor de Mowzie's fornece bônus mágicos na integração.
Esses bonuses devem ser aplicados por um único provider/attribute path para evitar stacking duplicado por outro addon.

## 7. Umvuthi / progressão de Solar spells
A 1.1.0 atualizou localization para indicar que **Solar Beam** e **Solar Storm** são obtidos derrotando **Umvuthi**. Isso torna loot/progression do boss uma boundary real. Quest ou integração externa não deve conceder o mesmo unlock novamente por observar o kill se o addon já o liquidou pelo mecanismo próprio.

## 8. Cast-time reduction e animation desync
A release 1.1.0 altera **Petrivise**, **Tremor Spike** e **Pillar of the Resounding Earth** para não respeitarem o atributo de redução de cast time. A justificativa oficial é evitar **animation desync**, usando abordagem equivalente à adotada em Flaming Strike no Iron's.
Esse é um contrato version-sensitive importante: mods próprios/perks não devem forçar redução de cast time nesses spells ignorando a regra do provider.

## 9. Mecânicas públicas dos Geo spells
A documentação oficial descreve os spells como combinação de projéteis/terrain-like attacks, fissuras, spikes, mobilidade sobre boulder, efeitos em sprint e estruturas/pillars temporárias. O state real, dano, duração, radius e scaling permanecem provider-native e não são hardcoded nesta ficha sem source/JAR exato.

## 10. Geo Conductor e Pillar
Geo Conductor e Pillar of the Resounding Earth formam uma interação de ressonância documentada. Geo Conductor beneficia aliados próximos com bonuses defensivos; o Pillar emite shockwaves e interage com entidades Geo. Os detalhes numéricos são omitidos aqui porque o source exato não foi pinado.
Regra de integração: observar o spell/efeito real do provider em vez de tentar recriar ressonância por polling externo.

## 11. Client / server
A distribuição é **Client & Server**.
- **Servidor:** cast admission, mana/cooldown, damage/effects, summons/temporary entities, progression/loot e attributes que afetam gameplay.
- **Cliente:** animations, particles, models, sounds e UI/spell presentation.
A correção de cast-time desync demonstra que apresentação client e settlement server precisam permanecer sincronizados.

## 12. Data/configuration
A mudança de Earthquake/Stomp para escola Geo é data-sensitive e a documentação original alertava que uma reset/revisão da config server do Iron's poderia ser necessária para aplicar corretamente a mudança. Antes de qualquer automação de config, validar os arquivos efetivamente gerados na instância 1.21.1.
Sem source exato, tags/recipes/config keys concretas não são inventadas.

## 13. Lifecycle
Validar login/relogin, spell loadout, death/respawn, dimension change, datapack/config reload quando suportado, boss kill/unlock e entity/spell despawn. Spells com pillars/boulders/temporary entities não devem deixar state órfão após chunk unload ou caster disconnect.

## 14. Multiplayer
Dois jogadores podem usar spells de área e interagir com o mesmo boss/Geo construct. Damage, debuffs, ally bonuses e unlock attribution precisam permanecer vinculados ao caster/player correto. Effects não devem ser duplicados por Iron's + Geomancy + SpellLib handlers simultâneos.

## 15. Sobreposição com outros sistemas mágicos
Goety e outros mods do pack também possuem geomancia/terra. Isso é **sobreposição temática**, não identidade de sistema. Geo Spell Power, Iron's mana, school membership e spell settlement continuam pertencendo ao stack Iron's/GTBC; Goety Soul Energy/Focuses permanecem separados.

## 16. Riscos técnicos
- redução de cast time reintroduzindo animation desync nos três spells corrigidos;
- update de Iron's mudando school/attribute contracts;
- SpellLib 2.2.0 alterar API/attributes esperados pelo addon antigo 1.1.0;
- Geo Spell Power aplicado duas vezes a Earthrend Gauntlet/Sculptor Staff;
- loot/unlock de Umvuthi duplicado por quests/scripts;
- Earthquake/Stomp continuarem com school/config antiga em mundo existente;
- mixin conflict com addons que alteram os mesmos spells/equipment;
- temporary Geo entities persistirem após chunk unload/caster disconnect;
- client/server mismatch de spell data/assets;
- documentação mais recente 2.0.0/1.20.1 ser confundida com conteúdo da build 1.1.0/1.21.1.

## 17. Matriz de testes obrigatória
- [ ] Dedicated server boot com Geomancy 1.1.0 + Iron's 3.16.3 + Mowzie 1.8.2 + SpellLib 2.2.0.
- [ ] Escola Geo aparece e Earthquake/Stomp estão classificados conforme runtime real.
- [ ] 10 spells Geo públicos e Solar Beam/Solar Storm carregam sem registry/data errors.
- [ ] Petrivise/Tremor Spike/Pillar ignoram cast-time reduction conforme 1.1.0 e não desincronizam animação.
- [ ] Earthrend Gauntlet e Sculptor Staff recebem Geo Spell Power uma única vez.
- [ ] Geomancer armor não duplica bônus com outros compat layers.
- [ ] Umvuthi progression/loot concede Solar spells pelo caminho esperado sem duplicação.
- [ ] Geo Conductor/Pillar ally/enemy resolution funciona corretamente em multiplayer.
- [ ] Chunk unload/reload limpa temporary spell entities sem ghost state.
- [ ] Death/relog/dimension change não preservam effects indevidos.
- [ ] Atualização de Iron's ou SpellLib é testada antes de substituir versão no pack.

## 18. Evidências e limites
- **Modlist física:** JAR, mod id, versão e dependências instaladas.
- **Release oficial 1.1.0 file 7041615:** port NeoForge 1.21.1, correções de cast time/translation/creative tab e bonuses a Earthrend Gauntlet/Sculptor Staff.
- **Descrição/release pública da linha:** escola Geo, 10 Geo spells, 2 Holy spells, Earthquake/Stomp reclassificados e itens/equipamentos publicados.
- **Limite crítico:** source público exato da build 1.1.0 não foi localizado; registry IDs, classes, methods, numerical stats e configs exatas permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
