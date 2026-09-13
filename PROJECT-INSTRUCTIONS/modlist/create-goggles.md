# Create Goggles

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8143828ef3436282cd23
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Goggles
- **Arquivo JAR:** `creategoggles-1.21.1-6.1.1-[NEOFORGE].jar`
- **Versão 1.21.1:** 6.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Tecnologia, Visual
- **Função:** Adiciona variantes de equipamentos Create como Goggle Helmets e Armored Backtanks, combinando visualização de goggles com peças de armadura/equipamento.
- **Dependências:** Create + Architectury; pack físico usa Create 6.0.10 e Architectury 13.0.11. Source matching 6.1.1 foi desenvolvido contra revisões anteriores e exige smoke-test. JEI é opcional para discovery.
- **Sobreposição:** Não duplica Cyber Goggles 8.5.3: Create Goggles adiciona equipamento físico; Cyber Goggles é assistência client-side. Armored Backtanks devem ser testados com Curios Backtank/Jetpack bridges para evitar double-count de recurso.
- **Compatibilidade/Riscos:** Riscos: helmet durability/attributes/components lost in conversion; Crusher destructive removal; Smithing duplication; modded helmet compatibility; Armored Backtank double-count with Curios bridges; stress-impact regression; Beta crash/API drift; stale equip state.
- **Observações:** JAR literal `creategoggles-1.21.1-6.1.1-[NEOFORGE].jar`, mod id `creategoggles`, runtime 6.1.1. Source matching branch 1.21.1 confirma tag `creategoggles:goggle` e recipe type `creategoggles:crafting_nbt`. Build oficial é Beta.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial 6.1.1 NeoForge + source oficial Robocraft999/CreateGoggles branch 1.21.1 matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-goggles
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê Beta 6.1.1 com Goggle Helmets, Armored Backtanks, tag/crafting_nbt compat, Smithing/Crusher removal, durability/stress fixes, Curios/backtank overlap e data preservation catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🥽 **Identidade física e source matching confirmados:** `creategoggles-1.21.1-6.1.1-[NEOFORGE].jar`, mod id `creategoggles`, runtime `6.1.1`. O branch oficial `1.21.1` declara exatamente 6.1.1. A build NeoForge 1.21.1 é classificada como **Beta**.

## 1. Papel e authority
Create Goggles adiciona **Goggle Helmets** e **Armored Backtanks**, combinando funcionalidade dos goggles Create com equipamento físico/armadura. O addon owns recipes, tags e data usados para reconhecer essas combinações; Create continua owner da informação exibida pelos goggles e da semântica base de backtank.

## 2. Dependências concretas
O source 6.1.1 declara Minecraft 1.21.1 e Architectury; o pack contém **Architectury 13.0.11** e Create 6.0.10. A branch foi desenvolvida com Create 6.0.6/Architectury 13.0.8, portanto o pack usa revisões posteriores e requer smoke-test de API/render/equipment.

## 3. Goggle Helmets
O projeto permite combinar helmet com Create Goggles para manter a função informativa dos goggles junto da proteção do helmet. A autoridade do armor item original precisa ser preservada; o addon não deve substituir atributos/enchants por uma cópia incompleta.

## 4. Vanilla/Create helmet combinations
A documentação pública descreve combinação shapeless de helmets vanilla/Create com goggles para produzir variantes de Goggle Helmet. Recipes efetivamente carregadas são authority para a lista exata de helmets suportados na build.

## 5. Smithing com modded helmets
Há uma rota de Smithing em que **Any Helmet + Goggles** recebe data/NBT para funcionar como goggle helmet. Isso permite compatibilidade mais genérica com helmets de mods. O item resultante precisa preservar data components, durability, attributes e enchantments do helmet de entrada.

## 6. Goggle tag para mod creators
O README source matching expõe suporte por tag `creategoggles:goggle` para helmets custom exibirem informação de goggles. Tags são a extensão pública preferida; mods externos não devem copiar internals/mixins para obter o mesmo efeito.

## 7. Recipe custom `crafting_nbt`
O source documenta recipe type `creategoggles:crafting_nbt` para produzir custom goggle helmets preservando data. Isso evidencia que NBT/components fazem parte da arquitetura de compatibilidade e precisam ser testados em upgrade de Minecraft/NeoForge.

## 8. Remoção por Crusher
A documentação pública permite remover goggles via **Crusher**, mas alerta que enchantments são perdidos nessa rota. Isso é comportamento destrutivo intencional e precisa ser visível ao jogador; não tratar como recipe reversível sem custo.

## 9. Modifier Remover
Também existe um **Modifier Remover** associado a Brass Shears/Smithing para remover a modificação por rota dedicada. O state retornado deve corresponder ao helmet base e não gerar cópia adicional de goggles/helmet.

## 10. Armored Backtanks
Create Goggles adiciona variantes de **Armored Backtanks**, combinando equipamento de armadura com a utilidade do backtank Create. Air capacity, equipment state e armor properties efetivos vêm do runtime/provider; não são inventados valores numéricos aqui.

## 11. Backtank ecosystem do pack
O pack contém Create: Curios Backtank 1.0.1, Create Jetpack 5.2.1, Create Jetpack Curios 1.2.0 e Create SA Curios Jetpacks. Essas bridges podem tocar slots/backtank detection, mas não são duplicatas automáticas de Armored Backtanks. O gate é reconhecimento simultâneo sem double-count de air/equipment.

## 12. Relação com Cyber Goggles
Create: Cyber Goggles 8.5.3 é client-side e amplia overlays/QoL. Create Goggles adiciona **equipamento físico**. Ambos podem coexistir: um helmet reconhecido como goggles pode acionar information overlays que Cyber Goggles melhora, sem que os mods tenham o mesmo papel.

## 13. Durability — fix 6.1.1
O changelog exato 6.1.1 corrige helmets que não tinham durability. Esse é um regression gate de inventory/data: uso/dano precisa reduzir durability conforme o helmet base e persistir após relog, death e recipe transforms.

## 14. Stress impact — fix 6.1.1
A 6.1.1 também corrige um problema de **breaking stress impact**. A publicação não detalha toda a causa; portanto a ficha não inventa mecanismo. O teste deve verificar que equip/itens do addon não corrompem cálculo/registro de stress Create.

## 15. Crash fix — 6.1.1
A release registra ainda um game crash fix sem causa detalhada suficiente. O baseline deve incluir startup, equip, recipe conversion e client/server join; nenhuma causa específica é atribuída sem source/changelog mais preciso.

## 16. Beta maturity
A classificação Beta é um risco de maturidade, não de identidade. O mod está fisicamente instalado e source-matched em 6.1.1, mas updates de Create/Architectury, recipes de helmets modded e slots devem receber smoke-tests mais rigorosos.

## 17. Data preservation
Smithing/crafting/removal precisam preservar o que deve sobreviver: custom name, durability, enchantments e components do helmet, exceto perdas explicitamente documentadas como a rota Crusher. Qualquer perda adicional é regressão.

## 18. Client/server
Armor stats, durability, equipment state e recipe results são server-authoritative. Goggle information/tooltips/render são client-facing. Cliente não pode obter armor/goggle function por alterar apenas tag visual local.

## 19. Multiplayer/lifecycle
Equip, unequip, death, respawn, Curios/chest-slot movement, smithing, crusher, restart e dimension change devem manter um único equipment state. Dois systems de backtank não podem somar ar duas vezes para o mesmo item.

## 20. Riscos
1. Goggle Helmet perde durability/attributes após conversion.
2. Crusher remove goggles mas perde data além do comportamento documentado.
3. Smithing duplica helmet/goggles ou strip components.
4. Modded helmet tag ativa goggles sem preservar armor semantics.
5. Armored Backtank é contado duas vezes por Curios bridges.
6. Stress-impact regression reaparece.
7. Create 6.0.10/Architectury 13.0.11 API drift quebra 6.1.1.
8. Beta build reproduz crash em equip/recipe/join.
9. Cyber Goggles e Create Goggles geram overlay duplicado, embora gameplay ownership permaneça distinto.
10. Death/relog deixa goggle function ativa sem equipamento.

## 21. Matriz de testes
- [ ] Dedicated server inicia com Create Goggles 6.1.1 + Create 6.0.10 + Architectury 13.0.11.
- [ ] Goggle Helmets exibem informação Create e mantêm armor stats.
- [ ] Durability reduz/persiste conforme fix 6.1.1.
- [ ] Smithing preserva name, enchantments, durability e components.
- [ ] Crusher removal produz a perda de enchantments documentada, sem dupe de items.
- [ ] Modifier Remover retorna estado esperado sem duplicação.
- [ ] `creategoggles:goggle` reconhece helmet custom válido.
- [ ] Armored Backtank fornece função correta sem double-count com Curios bridges.
- [ ] Stress network/Create não sofre regressão do bug corrigido.
- [ ] Cyber Goggles 8.5.3 coexiste sem state funcional duplicado.
- [ ] Death/relog/slot move removem e restauram funções corretamente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
A modlist confirma filename/mod id/runtime 6.1.1 e Architectury 13.0.11. O source matching branch 1.21.1 confirma versão, tag pública e recipe `crafting_nbt`. A publicação oficial confirma Goggle Helmets, Armored Backtanks, Smithing/Crusher/Modifier Remover e fixes 6.1.1 de crash, durability e stress impact. Valores de armor/air não foram inventados.

> 🔒 **Boundary canônico:** Create Goggles transforma equipamento real preservando sua identidade; informação de goggles é derivada desse equipamento. Toda conversão deve preservar data salvo perda explicitamente documentada.
