# Iron's Apothic

## Propriedades do registro

- **Mod:** Iron's Apothic
- **Arquivo JAR:** `irons_apothic-2.2.2.jar`
- **Versão 1.21.1:** `2.2.2`
- **Categoria:** Compat, Magia, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/muon-rw/Apotheosis-Irons-Spells
- **Função:** Compatibilidade bidirecional entre Apotheosis/Apothic e Iron's Spells, tornando staffs/spellbooks reforjáveis e adicionando affixes/gems ligados a escolas, spell power/level/mana e disparo de spells por eventos de combate/cura.
- **Dependências:** Apotheosis 8.6.0+ pela linha 2.2.1; pack físico: Apotheosis 8.8.0, Apothic Enchanting 1.6.2, Apothic Spawners 1.4.0, Apothic Attributes 2.10.1, Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Curios 9.5.1+1.21.1. Compat 2.2.2 toca GTBC's Geomancy, Cataclysm Spellbooks e Alshanex's Familiars, todos presentes.
- **Compatibilidade/Riscos:** Bridge profunda entre Apotheosis/Apothic e Iron's. Riscos: recursive proc loops, free-cast duplicado, cooldown/target state incorreto, school/affix stacking excessivo, optional-school registry drift, Curios/equipment lifecycle, FakePlayer/TargetEntityCastData edges e version drift dos providers.
- **Sobreposição:** Não substitui Apotheosis nem Iron's; injeta o sistema Apothic de reforging/affixes no conteúdo mágico de Iron's. Pode amplificar outros sistemas de atributos/gems/Curios, portanto balance e exactly-once procs exigem validação.
- **Observações:** Runtime físico 2.2.2. Changelog 2.2.2 adiciona Geo school affixes + Quaking Jasper, remove Sand school compat e remove Harmonic após mudanças dos providers.
- **Procedência:** modlist física de 17/09/2026 + release/changelog oficial Apotheosis x Iron's Spellbooks Compat 2.2.2 + source oficial muon-rw/Apotheosis-Irons-Spells.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — Iron's Apothic atualizado para 2.2.2; deltas Geo/Quaking Jasper, Sand e Harmonic incorporados; matriz física atual dos providers preservada.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #339: JAR `irons_apothic-2.2.2.jar`, mod id `irons_apothic`, runtime `2.2.2`, SHA-1 `e8d646f7aad9811ddcefed838d71d14b53573554`.

<callout icon="🔮" color="purple_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `irons_apothic-2.2.2.jar`, mod id `irons_apothic`, versão `2.2.2`. A ficha permanece release/source-grounded na linha oficial, com os deltas 2.2.2 registrados separadamente dos comportamentos herdados de 2.2.0/2.2.1.
</callout>
## 1. Papel e authority
Iron's Apothic integra o sistema de reforging/affixes/gems de Apotheosis/Apothic com **Iron's Spells 'n Spellbooks**. Iron's continua authority de spells, mana, escolas e casting; Apotheosis/Apothic continuam authority de reforging, affixes e gem mechanics; a bridge é authority somente das categorias, filtros e gatilhos que conectam os dois sistemas.
## 2. Matriz física e version gate
A linha 2.2.1 elevou o gate mínimo para Apotheosis 8.6.0; a 2.2.2 mantém essa base e adiciona/ajusta compatibilidades de escolas. O pack físico usa Apotheosis 8.8.0, Apothic Enchanting 1.6.2, Apothic Spawners 1.4.0, Apothic Attributes 2.10.1, Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Curios 9.5.1+1.21.1. Também estão presentes Cataclysm Spellbooks 1.1.14-1.21, GTBC's Geomancy Plus 1.1.0-1.21.1 e Alshanex's Familiars 1.21.1_v4.0.3, todos relevantes aos ajustes da 2.2.2.
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
A 2.2.2 altera explicitamente integrações de escolas/addons:
- adiciona **Geo school affixes** e a **Quaking Jasper gem** para GTBC's Geomancy;
- remove a integração da **Sand school** para acompanhar Cataclysm Spellbooks 1.1.13+;
- remove o affix **Harmonic** porque Alshanex's Familiars removeu o spell **Guardian Angel**.
Os providers correspondentes estão presentes no pack atual. Optional-school integration deve resolver por registry real; ausência/renome de escola não pode quebrar o carregamento global.
## 12. Releases 2.2.1 e 2.2.2
A 2.2.1 atualizou a integração para exigir **Apotheosis 8.6.0+**. A 2.2.2, instalada atualmente, concentra-se nas compatibilidades de escolas descritas acima: Geo/Quaking Jasper adicionados, Sand removida e Harmonic removido após mudança do provider. Outros fixes não são atribuídos à 2.2.2 sem evidência.
## 13. Client / server
Affix rolls, atributos, cooldowns, casts, damage/heal, loot e item state são server-authoritative. Cliente renderiza tooltips, gem/affix presentation e spell visuals. Prediction/animation não pode disparar uma segunda execução do affix.
## 14. Lifecycle e multiplayer
Validar reforging, equip/unequip, gem insertion/removal quando aplicável, cast triggers, cooldown persistence, death/respawn, reconnect, server restart e dois jogadores usando o mesmo tipo de affix. Cooldown/target state deve permanecer por item/player conforme contrato real e nunca vazar entre entidades.
## 15. Riscos técnicos
- recursive spell/affix proc loop;
- free spell cast aplicado duas vezes;
- cooldown compartilhado ou resetado incorretamente;
- target routing atingir entity errada;
- school registry drift em addon opcional, especialmente Geo/Sand após mudanças dos providers;
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
- [ ] Geo school affixes e Quaking Jasper resolvem com GTBC's Geomancy presente.
- [ ] Sand school removida não deixa registry reference stale com Cataclysm Spellbooks atual.
- [ ] Harmonic removido não deixa referência ao Guardian Angel ausente em Alshanex's Familiars.
- [ ] Equip/unequip/death/relog não acumulam modifiers.
- [ ] Update futuro de Apotheosis/Iron's é bloqueado até smoke test conjunto.
## 17. Evidências e limites
- **Modlist física:** `irons_apothic-2.2.2.jar`, mod id `irons_apothic`, versão 2.2.2 e versões atuais dos providers.
- **Release/changelog oficial 2.2.2:** Geo school affixes + Quaking Jasper para GTBC's Geomancy; remoção de Sand school para acompanhar Cataclysm Spellbooks; remoção de Harmonic após retirada de Guardian Angel em Alshanex's Familiars.
- **Linha 2.2.1:** requirement Apotheosis 8.6.0+ e funcionalidades acumuladas 2.2.0 usadas como provenance da linha.
- **Limite:** valores numéricos de affixes/gems e tabelas completas de registries não foram inventados quando não pinados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
