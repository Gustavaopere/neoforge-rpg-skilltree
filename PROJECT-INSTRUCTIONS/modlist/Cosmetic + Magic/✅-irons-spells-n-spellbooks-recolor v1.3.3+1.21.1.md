# Iron's Spells 'n Spellbooks: Recolor

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#472**: JAR `recolor_tablet-1.3.3+1.21.1.jar`, mod id `recolor_tablet`, runtime `1.3.3+1.21.1`, SHA-1 `f3806de891b04d554d05c279808b057fdf7bdab5`. Iron's Spells 'n Spellbooks está fisicamente em `1.21.1-3.16.3`; referências históricas da auditoria anterior foram reconciliadas nas seções operacionais.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack:** Integrado ao Github
- **Autoridade física:** `recolor_tablet-1.3.3+1.21.1.jar`, mod id `recolor_tablet`, runtime `1.3.3+1.21.1`
- **Nota de autoridade física externa:** Iron's Spells 'n Spellbooks está em `1.21.1-3.16.3` na modlist atual; referências históricas a 3.15.1 não prevalecem sobre a autoridade física.
- **Auditoria de migração Notion → GitHub:** 2026-09-14

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks: Recolor
- **Arquivo JAR:** `recolor_tablet-1.3.3+1.21.1.jar`
- **Versão 1.21.1:** 1.3.3+1.21.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, QoL
- **Função:** Addon visual/QoL para Iron's Spells 'n Spellbooks que permite personalizar por jogador a tonalidade de efeitos de spells/escolas sem assumir autoridade sobre dano, mana, cooldown ou progressão.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3 é o provider-base físico atual. Integrações relevantes no pack: GTBC's Geomancy Plus 1.1.0, Hazen N Stuff 1.4.0.14 e T.O Magic n' Extras 4.4.0.1.
- **Sobreposição:** Sobreposição apenas visual com outros render/effect mods. Iron's continua authority de spells/mana/cooldown/dano; Recolor controla somente apresentação de cor compatível.
- **Compatibilidade/Riscos:** Riscos: state bleed entre jogadores, desync client/server, mixin/render conflict, addon/version drift e persistência/reset incorretos. A build física 1.3.3 adiciona recolor para Infernal Devastator Gyro Slash e Ichor/Golden Shower e corrige alguns crash cases; manter regressão das integrações Geomancy/Hazen/T.O e do gameplay authority do Iron's.
- **Observações:** Runtime físico reconciliado para 1.3.3+1.21.1 pela modlist atual. A release oficial 1.3.3 de 10/09/2026 é a build instalada e latest NeoForge 1.21.1 localizada; corrige alguns crashes e amplia recolor para Gyro Slash do Infernal Devastator e Ichor de Golden Shower.
- **Procedência:** modlist física atual confirma `recolor_tablet-1.3.3+1.21.1.jar`, runtime 1.3.3+1.21.1 e Iron's Spells 3.16.3; CurseForge oficial file ID 8852186 confirma a release 1.3.3 e seu changelog. Reconciliado em 01/10/2026.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks-recolor
- **Atualização/Status:** RECONCILIADO EM 01/10/2026 — o corpo antigo ainda tratava 1.3.3 como version drift, mas a autoridade física atual já está em 1.3.3+1.21.1. Identidade, dependência-base e changelog foram atualizados para o runtime efetivamente instalado.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> 🎨 **ESCOPO CANÔNICO.** Runtime físico instalado: `recolor_tablet-1.3.3+1.21.1.jar`, mod id `recolor_tablet`, versão `1.3.3+1.21.1`, NeoForge 1.21.1. O mod personaliza visualmente a cor dos efeitos de spells sem assumir authority sobre dano, mana, cooldown, escola ou progressão.

## 1. Identidade e papel
- **Mod:** Iron's Spells 'n Spellbooks: Recolor.
- **JAR:** `recolor_tablet-1.3.3+1.21.1.jar`.
- **Mod id:** `recolor_tablet`.
- **Versão instalada:** `1.3.3+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Tipo:** addon/QoL visual para Iron's Spells 'n Spellbooks.
- **Provider-base presente:** Iron's Spells 'n Spellbooks 3.16.3.

## 2. Papel no modpack
O addon fornece o **Recolor Tablet** e uma interface de personalização por escola/elemento. O fluxo documentado consiste em vincular/usar o tablet, abrir a seleção de escolas e escolher uma tonalidade por meio de color wheel. A cor escolhida é uma apresentação do spell; não é um segundo sistema de magia.
O objetivo operacional no pack é permitir identidade visual por jogador sem alterar o balanceamento do spell provider.

## 3. Autoridade e ownership
- **Iron's Spells 'n Spellbooks:** continua autoridade de spells, schools, mana, cooldowns, cast e dano.
- **Recolor:** autoridade apenas do estado de cor/tom aplicado às apresentações compatíveis.
- **Addons de escola/spell:** continuam donos de seus próprios spells/conteúdo; Recolor apenas fornece adaptação visual quando compatível.
Nenhum sistema próprio deve duplicar mana, cooldown ou cálculo de dano com base na cor escolhida.

## 4. Superfície técnica confirmada
O JAR físico declara três mixin configs: `mixins.recolor_tablet.json`, `mixins.recolor_tablet.geomancy.json` e `mixins.recolor_tablet.hazennstuff.json`.
Isso confirma superfície de integração dedicada no binário para:
- núcleo do Recolor;
- **GTBC's Geomancy Plus**;
- **Hazen N Stuff**.
A documentação oficial atual também anuncia compatibilidade com **T.O Magic n' Extras**. O mod está presente no pack, mas o mecanismo específico dessa compatibilidade não foi inferido a partir de nomes de mixin ausentes.

## 5. Estado visual por jogador
A documentação oficial descreve a seleção de cor como individual por jogador e visível aos demais. Portanto o valor precisa ser sincronizado de forma que outro cliente renderize o spell com a escolha do caster correto.
Isso cria duas responsabilidades distintas:
- persistir/obter a seleção de cor do jogador certo;
- aplicar essa seleção apenas ao render/efeito visual compatível.
Não foi presumido o formato interno de persistência sem source exato da build 1.3.3 instalada.

## 6. Escolas, grupos e administração
A documentação upstream atual expõe operações administrativas de recolor, consulta, reset, banimento de recolor e grupos. A build instalada agora coincide com a release 1.3.3; comandos ainda precisam ser validados no runtime antes de virar contrato de integração.

## 7. Integrações concretas no pack
- **Iron's Spells 3.16.3:** dependência funcional principal e autoridade do sistema de magia.
- **GTBC's Geomancy Plus 1.1.0:** integração específica evidenciada pelo mixin config do JAR.
- **Hazen N Stuff 1.4.0.14:** integração específica evidenciada pelo mixin config do JAR.
- **T.O Magic n' Extras 4.4.0.1:** compatibilidade anunciada oficialmente; presença física confirmada, implementação exata não inferida.

## 8. Client / server
A funcionalidade tem componente visual client-side, mas seleção por jogador e visibilidade para terceiros implicam sincronização multiplayer. O servidor não deve aceitar que uma escolha puramente local altere gameplay do spell.
O teste correto precisa distinguir falha de renderização de falha de estado/sync.

## 9. Lifecycle
Validar:
- criação/uso/vínculo do tablet;
- alteração e reset de cor;
- logout/login;
- morte/respawn;
- troca de dimensão;
- reconexão de outro cliente que já precisa enxergar a cor escolhida;
- reload de resources;
- remoção/adição de addon compatível.
A seleção não deve migrar para jogador incorreto nem duplicar listeners após reconexão.

## 10. Multiplayer
Dois jogadores com cores diferentes devem poder lançar o mesmo spell simultaneamente sem vazamento de estado entre casters. Um cliente recém-conectado deve receber a representação correta de jogadores já configurados. O servidor deve continuar authority do cast e de qualquer efeito funcional.

## 11. Reconciliação de versão
A modlist física atual instala **1.3.3+1.21.1**, que coincide com a release oficial NeoForge 1.21.1 de 10/09/2026. O version drift histórico 1.3.2→1.3.3 está resolvido fisicamente.
O delta físico agora ativo é: correções de alguns crash cases, recolor da variante Gyro Slash do Infernal Devastator e recolor do Ichor de Golden Shower.

## 12. Riscos técnicos
1. **State bleed:** cor de um jogador aplicada a outro caster.
2. **Client/server desync:** local vê uma cor e terceiros veem outra.
3. **Addon drift:** escola/spell adicional muda IDs ou renderer hooks.
4. **Mixin conflict:** outro visual mod intercepta a mesma superfície de spell rendering.
5. **Gameplay leakage:** recolor não pode alterar damage/mana/cooldown por acidente.
6. **Documentation/API drift:** documentação corrente pode evoluir além da build 1.3.3; não assumir comandos futuros sem verificação.
7. **Reset/persistence:** cor antiga reaparece ou é perdida após relog/restart.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Recolor 1.3.3 + Iron's 3.16.3.
- [ ] Tablet abre a UI e altera uma escola suportada.
- [ ] Reset devolve a aparência padrão.
- [ ] Dois jogadores usam cores distintas sem state bleed.
- [ ] Terceiro cliente observa corretamente a cor de ambos.
- [ ] Relog, respawn e dimension change preservam/limpam somente conforme comportamento da build.
- [ ] Spell de Iron's mantém exatamente o mesmo dano, mana e cooldown antes/depois do recolor.
- [ ] Geomancy Plus continua funcional com integração ativa.
- [ ] Hazen N Stuff continua funcional com integração ativa.
- [ ] T.O Magic n' Extras é testado sem presumir hook interno específico.
- [ ] Resource reload não deixa shader/particle/model state stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física canônica de 10/09/2026: JAR, mod id, versão e três mixin configs.
- Publicação oficial: build 1.3.3 para NeoForge 1.21.1, file ID 8852186; changelog com crash fixes, Gyro Slash e Golden Shower/Ichor.
- Projeto oficial atual: comportamento de color wheel/visibilidade por jogador e lista de compatibilidades.
- A build 1.3.3 coincide com o runtime físico atual.
- **Limite:** source/tag exato da 1.3.3 não foi decompilado nesta rodada para inventariar classes, packets ou formato de save; o changelog oficial e a modlist física sustentam a reconciliação.
## 15. Reconciliação física 1.3.3 — 01/10/2026

A modlist física atual confirma `recolor_tablet-1.3.3+1.21.1.jar`, versão `1.3.3+1.21.1`, SHA-1 `f3806de891b04d554d05c279808b057fdf7bdab5`. Portanto o filename GitHub já estava correto, mas o corpo ainda estava ancorado na antiga 1.3.2 e foi reconciliado.

Delta oficial 1.3.3:
- corrige alguns crash cases;
- a variante **Gyro Slash** do Infernal Devastator passa a respeitar recolor;
- **Ichor** de Golden Shower passa a respeitar recolor.

Gate de regressão do runtime atual:
- [ ] Gyro Slash usa a cor do caster sem state bleed.
- [ ] Golden Shower/Ichor usa a cor correta para observadores multiplayer.
- [ ] Recolor continua sem alterar damage/mana/cooldown.
- [ ] Geomancy/Hazen/T.O integrations continuam carregando sem mixin crash.

Fonte: CurseForge Recolor 1.3.3, file ID 8852186.
