# Iron's Apothic

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818f8949e6dfbde96b41
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iron's Apothic
- **Arquivo JAR:** `irons_apothic-2.2.1.jar`
- **Versão 1.21.1:** 2.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia, RPG
- **Função:** Compatibilidade bidirecional entre Apotheosis/Apothic e Iron's Spells, tornando staffs/spellbooks reforjáveis e adicionando affixes/gems ligados a escolas, spell power/level/mana e disparo de spells por eventos de combate/cura.
- **Dependências:** Source 2.2.1 requer Apotheosis 8.6.0+ e integra Apothic Enchanting/Spawners/Attributes, Placebo, Iron's Spells, Iron's Lib e Curios. Pack físico: Apotheosis 8.8.0, Apothic Enchanting 1.6.2, Apothic Spawners 1.4.0, Apothic Attributes 2.10.1, Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Curios 9.5.1+1.21.1.
- **Sobreposição:** Não substitui Apotheosis nem Iron's; injeta o sistema Apothic de reforging/affixes no conteúdo mágico de Iron's. Pode amplificar outros sistemas de atributos/gems/Curios, portanto balance e exactly-once procs exigem validação.
- **Compatibilidade/Riscos:** Bridge profunda entre Apotheosis/Apothic e Iron's. Riscos: recursive proc loops, free-cast duplicado, cooldown/target state incorreto, school/affix stacking excessivo, optional-school registry drift, Curios/equipment lifecycle, FakePlayer/TargetEntityCastData edges e version drift dos providers.
- **Observações:** Source exato declara mod_version 2.2.1. Changelog 2.2.1 atualiza e passa a exigir Apotheosis 8.6.0. A linha 2.2.0 reescreveu spell-cast utilities para target routing e adicionou mais built-in cast affixes/compatibilidades.
- **Procedência:** modlist.txt física atual + CurseForge oficial Iron's Apothic 2.2.1 file 8480849 + source oficial muon-rw/Apotheosis-Irons-Spells com mod_version 2.2.1 + changelogs 2.2.0/2.2.1.
- **Fonte:** https://github.com/muon-rw/Apotheosis-Irons-Spells
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Iron's Apothic 2.2.1 source-pinned; reforge categories, school-filtered affixes, spell-effect/cast affixes, free-cast cooldown/target routing, gems, optional schools, 2.2.1 Apotheosis gate, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `irons_apothic-2.2.1.jar`, mod id `irons_apothic`, versão `2.2.1`. O source oficial `muon-rw/Apotheosis-Irons-Spells` declara exatamente `mod_version=2.2.1`; esta ficha é source-pinned.

## 1. Papel e authority
Iron's Apothic integra o sistema de reforging/affixes/gems de Apotheosis/Apothic com **Iron's Spells 'n Spellbooks**. Iron's continua authority de spells, mana, escolas e casting; Apotheosis/Apothic continuam authority de reforging, affixes e gem mechanics; a bridge é authority somente das categorias, filtros e gatilhos que conectam os dois sistemas.

## 2. Matriz física e version gate
O source 2.2.1 exige Apotheosis 8.6.0+ e foi desenvolvido com Iron's Spells 3.16.2, Iron's Lib 2.1.0, Curios 9.5.1 e versões Apothic da época. O pack usa Apotheosis 8.8.0, Apothic Enchanting 1.6.2, Apothic Spawners 1.4.0, Apothic Attributes 2.10.1, Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Curios 9.5.1+1.21.1. É uma matriz plausível, mas ainda precisa de runtime regression test.

## 3. Reforge/loot categories
A documentação publica duas categorias relevantes: **Staffs**, abrangendo itens que implementam o contrato de casting de Iron's, e **reforgeable Spellbooks**. Isso permite que equipamento mágico entre no pipeline Apothic sem recriar o item-base. A bridge não deve substituir durability, spell slots ou dados próprios do item Iron's.

## 4. Attribute Affixes por escola
Há affixes filtrados por escola que concedem bônus percentuais de Spell Power. O sistema suporta múltiplas escolas e upgrade orbs conforme documentação. O cálculo final deve ocorrer uma vez na pilha de atributos; outro mod não deve reaplicar o mesmo bônus ao reconhecer a mesma escola.

## 5. Spell Effect Affixes
O addon oferece gatilhos ligados a **spell heal/damage**, aplicáveis ao usuário ou alvo conforme o affix. O evento de spell continua originado em Iron's; o affix reage ao evento final. Double-hook em damage/heal pode gerar procs duplicados ou ciclos recursivos.

## 6. Cast Spell Affixes
Há affixes capazes de lançar spells sem custo de mana, com cooldown próprio, em gatilhos como melee/ranged/spell damage, healing ou receber dano, dependendo do equipamento. O cast disparado deve ser processado exatamente uma vez e não pode reentrar indefinidamente no próprio gatilho.

## 7. Target routing — linha 2.2.0
A 2.2.0 reescreveu utilities de spell casting para apontar spells corretamente para targets, inclusive spells que originalmente não suportavam entity targeting. Também corrigiu um `ClassCastException` raro relacionado a `TargetEntityCastData`. Esses pontos são regression gates da linha instalada.

## 8. Spell Level e Mana Cost affixes
A documentação inclui affixes filtrados por escola para **Spell Level** e **Mana Cost**. Eles compõem com atributos/perks externos do pack; o risco principal é stacking excessivo ou ordem de cálculo inesperada. O resultado final deve respeitar caps/regras do provider real, não valores duplicados por integração própria.

## 9. Telepathic e drops
O affix **Telepathic** para staffs move drops de kills mágicas para o usuário conforme documentação pública. Esse comportamento toca item-drop ownership e pode interagir com magnetismo/loot collection. A transferência precisa ocorrer exatamente uma vez e sem clonar drops.

## 10. Gems temáticas
Iron's Apothic adiciona gems relacionadas ao ecossistema mágico. Gems e affixes do addon entram no pipeline Apothic; não devem ser confundidos com o sistema independente de Iron's Gems 'n Jewelry, que possui seus próprios materiais/patterns/attributes.

## 11. Escolas e addons opcionais
O projeto inclui compatibilidades para escolas de addons, incluindo conteúdo de Cataclysm Spellbooks e HazenTouveLib. Ambos têm presença física no pack. Optional-school integration deve resolver por registry real; ausência/renome de escola não pode quebrar o carregamento global.

## 12. Release 2.2.1
A mudança version-specific publicada da 2.2.1 é **atualizar para e exigir Apotheosis 8.6.0**. O pack está em 8.8.0. Não se atribuem outros fixes à 2.2.1 sem evidência.

## 13. Client / server
Affix rolls, atributos, cooldowns, casts, damage/heal, loot e item state são server-authoritative. Cliente renderiza tooltips, gem/affix presentation e spell visuals. Prediction/animation não pode disparar uma segunda execução do affix.

## 14. Lifecycle e multiplayer
Validar reforging, equip/unequip, gem insertion/removal quando aplicável, cast triggers, cooldown persistence, death/respawn, reconnect, server restart e dois jogadores usando o mesmo tipo de affix. Cooldown/target state deve permanecer por item/player conforme contrato real e nunca vazar entre entidades.

## 15. Riscos técnicos
- recursive spell/affix proc loop;
- free spell cast aplicado duas vezes;
- cooldown compartilhado ou resetado incorretamente;
- target routing atingir entity errada;
- school registry drift em addon opcional;
- Spell Power/Level/Mana Cost stacking excessivo;
- Telepathic duplicar ou perder drops;
- Curios/equipment modifier não limpar em unequip/relog;
- FakePlayer/automação atingir path não previsto;
- update de Apotheosis/Iron's alterar ABI sem update da bridge.

## 16. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com a matriz física atual.
- [ ] Staffs e spellbooks elegíveis entram no reforging correto.
- [ ] School-filtered Spell Power aplica uma única vez.
- [ ] Spell Effect Affix reage a heal/damage sem recursive proc.
- [ ] Cast Spell Affix consome zero mana somente quando previsto e respeita cooldown próprio.
- [ ] Target routing funciona para alvo válido e não atinge entity errada.
- [ ] Spell Level/Mana Cost affixes compõem sem bypass de caps/regras.
- [ ] Telepathic não duplica drops em multiplayer/magnetism stack.
- [ ] Optional schools físicas resolvem sem missing registry.
- [ ] Equip/unequip/death/relog não acumulam modifiers.
- [ ] Update futuro de Apotheosis/Iron's é bloqueado até smoke test conjunto.

## 17. Evidências e limites
- **Modlist física:** JAR/mod id/version e versões dos providers atuais.
- **Source oficial:** `mod_version=2.2.1`, dependencies e arquitetura de compat.
- **CurseForge/changelog:** requirement Apotheosis 8.6.0 na 2.2.1; funcionalidades acumuladas 2.2.0 usadas como provenance da linha.
- **Limite:** valores numéricos de affixes/gems e tabelas completas de registries não foram inventados quando não pinados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
