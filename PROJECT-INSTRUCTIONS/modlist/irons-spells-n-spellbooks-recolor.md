# Iron's Spells 'n Spellbooks: Recolor

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db8155923dc65dd14d29e5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente; Iron's Spells físico atual = `1.21.1-3.16.3`
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks: Recolor
- **Arquivo JAR:** `recolor_tablet-1.3.2+1.21.1.jar`
- **Versão 1.21.1:** 1.3.2+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, QoL
- **Função:** Addon visual/QoL para Iron's Spells 'n Spellbooks que permite personalizar por jogador a tonalidade de efeitos de spells/escolas sem assumir autoridade sobre dano, mana, cooldown ou progressão.
- **Dependências:** Iron's Spells 'n Spellbooks 3.15.1 é provider-base presente. Integrações atuais relevantes no pack: GTBC's Geomancy Plus 1.1.0, Hazen N Stuff 1.4.0.14 e T.O Magic n' Extras 4.4.0.1.
- **Sobreposição:** Sobreposição apenas visual com outros render/effect mods. Iron's continua authority de spells/mana/cooldown/dano; Recolor controla somente apresentação de cor compatível.
- **Compatibilidade/Riscos:** Riscos: state bleed entre jogadores, desync client/server, mixin/render conflict, addon/version drift e persistência/reset incorretos. JAR confirma mixins dedicados para Geomancy e Hazen; T.O é compatibilidade publicada, sem implementação interna inferida.
- **Observações:** Runtime físico permanece 1.3.2+1.21.1. Upstream já publicou 1.3.3 em 10/09/2026; registrado como version drift, sem upgrade automático. Documentação atual pode conter comandos posteriores à build instalada.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + metadata/mixin configs do JAR + publicação oficial da build 1.3.2 + documentação oficial atual do projeto. Binário instalado prevalece sobre a release upstream 1.3.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks-recolor
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Recolor 1.3.2 reconstruído: tablet/color state por jogador, ownership visual, integrações Geomancy/Hazen/T.O, multiplayer, version drift 1.3.3, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

> **Divergência física registrada:** a ficha-fonte acima referencia Iron's Spells `3.15.1` como provider-base. A modlist física mais recente efetivamente disponível nesta execução contém `irons_spellbooks-1.21.1-3.16.3.jar` / runtime `1.21.1-3.16.3`. O texto-fonte é preservado sem ser promovido a fato físico atual.

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico instalado: `recolor_tablet-1.3.2+1.21.1.jar`, mod id `recolor_tablet`, versão `1.3.2+1.21.1`, NeoForge 1.21.1. O mod personaliza visualmente a cor dos efeitos de spells do ecossistema Iron's Spells sem assumir autoridade sobre dano, mana, cooldown, escola ou progressão. O upstream já publicou 1.3.3; esta ficha permanece ancorada no JAR 1.3.2 efetivamente instalado.

## 1. Identidade e papel
- **Mod:** Iron's Spells 'n Spellbooks: Recolor.
- **JAR:** `recolor_tablet-1.3.2+1.21.1.jar`.
- **Mod id:** `recolor_tablet`.
- **Versão instalada:** `1.3.2+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Tipo:** addon/QoL visual para Iron's Spells 'n Spellbooks.
- **Provider-base presente:** Iron's Spells 'n Spellbooks 3.15.1.

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
Não foi presumido o formato interno de persistência sem source exato da 1.3.2.

## 6. Escolas, grupos e administração
A documentação upstream atual expõe operações administrativas de recolor, consulta, reset, banimento de recolor e grupos. Como a documentação acompanha uma release mais nova que o JAR instalado, esta ficha **não declara que cada comando atual existe necessariamente na 1.3.2**. O que deve ser validado no runtime é a superfície efetivamente registrada por essa build.

## 7. Integrações concretas no pack
- **Iron's Spells 3.15.1:** dependência funcional principal e autoridade do sistema de magia.
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

## 11. Version drift
A modlist física instala **1.3.2**, release oficial de NeoForge 1.21.1. O upstream já possui **1.3.3** publicada em 10/09/2026.
Isso é registrado como **version drift**, não como autorização de upgrade. A decisão de atualizar exige comparação de changelog e teste separado.

## 12. Riscos técnicos
1. **State bleed:** cor de um jogador aplicada a outro caster.
2. **Client/server desync:** local vê uma cor e terceiros veem outra.
3. **Addon drift:** escola/spell adicional muda IDs ou renderer hooks.
4. **Mixin conflict:** outro visual mod intercepta a mesma superfície de spell rendering.
5. **Gameplay leakage:** recolor não pode alterar damage/mana/cooldown por acidente.
6. **Version drift:** documentação corrente pode descrever comandos/integrações posteriores à 1.3.2.
7. **Reset/persistence:** cor antiga reaparece ou é perdida após relog/restart.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Recolor 1.3.2 + Iron's 3.15.1.
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
- Publicação oficial: build 1.3.2 para NeoForge 1.21.1 e documentação do Recolor Tablet.
- Projeto oficial atual: comportamento de color wheel/visibilidade por jogador e lista de compatibilidades.
- Publicação atual upstream: 1.3.3 posterior à instalada.
- **Limite:** source/tag exato da 1.3.2 não foi usado para inventariar classes, packets ou formato de save; comandos da documentação atual não foram atribuídos automaticamente ao binário 1.3.2.
