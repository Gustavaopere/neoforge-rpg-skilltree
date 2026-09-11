# Toxony

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81989ccdfc7d4d3ccd06
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `toxony-0.10.7.jar`, mod id `toxony`, runtime `0.10.7`; JEI 19.53.0.426, Vampirism 1.10.13, Iron's Spells 3.16.3 e Curios 9.5.1 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Toxony 0.10.7 e as integrações físicas citadas estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Toxony
- **Arquivo JAR:** `toxony-0.10.7.jar`
- **Versão 1.21.1:** 0.10.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Magia
- **Função:** Sistema RPG/alquímico Beta de Toxicity, toxinas, mutagens semipermanentes, oils para armas, monster-hunting equipment, plantas/materiais e estruturas, com integrações explícitas de JEI/Vampirism/Iron's/Curios.
- **Dependências:** NeoForge 1.21.1. Integrações fisicamente relevantes no pack: JEI 19.53.0.426, Vampirism 1.10.13, Iron's Spells 3.16.3 e Curios 9.5.1.
- **Sobreposição:** Sobreposição temática com alquimia/RPG, mas Toxicity, mutagens e oils são mecânicas próprias. Evitar double-spell-power/double-damage com bridges existentes.
- **Compatibilidade/Riscos:** Beta-only. Riscos principais: persistent mutation modifiers, attribute/damage stacking com Iron's/Epic Fight/Vampirism, oils em armas modded, Curios state e densidade de worldgen. Não inventar thresholds/fórmulas de Toxicity sem source/runtime pin.
- **Observações:** mod id `toxony`; runtime 0.10.7 Beta, atual para NeoForge 1.21.1. Lost Journal é onboarding oficial. Linha 1.21.1 permanece Beta; ausência de Release estável equivalente não é motivo para downgrade automático.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Toxony 0.10.7 Beta para NeoForge 1.21.1 + stack físico JEI/Vampirism/Iron's Spells/Curios. Dossiê de 09/09 preservado; Toxicity, mutagens, oils e worldgen não foram testados em runtime.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/toxony
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Toxony 0.10.7 permanece exatamente instalado e continua a Beta 1.21.1 relevante; Toxicity, mutagens, oils, gear/worldgen, JEI/Vampirism/Iron's/Curios, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `toxony-0.10.7.jar`, mod id `toxony`, versão `0.10.7`. Toxony é um sistema RPG/alquímico em **Beta** centrado em Toxicity, toxinas, mutagens semipermanentes, oils aplicáveis a armas, caça a monstros, equipamentos, plantas/materiais e estruturas. A Beta 0.10.7 é a build pública atual para NeoForge 1.21.1; não existe Release estável 1.21.1 equivalente para substituir automaticamente.

## 1. Identidade, versão e maturidade
- **Mod:** Toxony.
- **JAR:** `toxony-0.10.7.jar`.
- **Mod id:** `toxony`.
- **Versão:** `0.10.7`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal:** Beta.
- **Publicação da build:** 12/06/2026; é a versão 1.21.1 mais recente listada oficialmente.

## 2. Authority e ownership
Toxony é authority de seu **Toxicity state**, mutagens, toxinas, oils, materiais, equipamentos e worldgen próprio. Iron's Spells, Vampirism e Curios continuam authority dos próprios sistemas; as bridges de Toxony apenas aplicam efeitos/atributos compatíveis quando suportados.

Não fundir Toxicity com mana, fome, sede, stamina ou corruption de outros sistemas do pack.

## 3. Onboarding — Lost Journal
O upstream orienta o jogador a começar pelo **Lost Journal**, descrito como guia para o conteúdo do mod.

Esse item é a referência in-game mais apropriada para recipes/progressão da build instalada. JEI complementa recipes, mas o Journal pode comunicar lógica que não cabe em uma receita simples.

## 4. Sistema de Toxicity
A descrição oficial define Toxicity como um sistema que monitora a composição tóxica do personagem — comparado informalmente pelo autor a mana, porém mais perigoso.

Operacionalmente, tratar Toxicity como recurso/estado próprio do Toxony. Sem source pin desta build, esta ficha **não inventa escala, thresholds, decay, penalties ou fórmulas**.

Testar persistence, death/respawn, relog e efeitos de consumo/aplicação de toxinas no servidor.

## 5. Toxinas e alquimia
O mod adiciona plantas, materiais e blocos usados para fabricar itens especiais ligados a venenos/alquimia. O tema inclui decoração de **copper alchemy**.

A cadeia exata deve ser lida no Lost Journal/JEI da 0.10.7; recipes de versões anteriores podem ter mudado ao longo da Beta.

## 6. Mutagens
Mutagens concedem **buffs semipermanentes** ao personagem. A linha 0.9.x recebeu um Mutagens Rework e adicionou compatibilidade explícita com Iron's Spells.

Como mutagens alteram o jogador de forma persistente ou duradoura, validar:
- aplicação e remoção;
- stacking;
- death/respawn;
- relog/restart;
- compatibilidade com attribute systems do pack;
- rollback/cleanse conforme recursos disponíveis no runtime.

## 7. Oils e weaponization
Toxony adiciona **oils** que podem ser aplicados a armas e também usados como formas de weaponization próprias.

Isto cruza com grande quantidade de armas/modificadores no pack. Testar se aplicação de oil:
- preserva components/enchantments do item;
- não duplica ao reparar/reforjar;
- interage uma única vez por ataque;
- respeita weapons modded suportadas pelo contrato do mod.

A ficha não afirma duração/dano sem evidência específica da 0.10.7.

## 8. Armas, ferramentas, armaduras e progressão material
O projeto confirma:
- armas e ferramentas únicas;
- armaduras para encontrar/fabricar;
- arsenal voltado a caça de monstros;
- uma alternativa ao Netherite obtível no Overworld.

A linha histórica introduziu **Silver Steel**, mas recipes/stats exatos devem ser validados no runtime 0.10.7 antes de balanceamento.

## 9. Worldgen
Toxony adiciona plantas e **special structures** ao mundo. Portanto a presença do mod afeta exploração e chunk generation, não apenas inventário do jogador.

Validar em chunks novos e contra o stack massivo de worldgen do pack. Remover/alterar o mod não reescreve estruturas já geradas.

## 10. Integração com JEI
Compatibilidade com **Just Enough Items** é explicitamente publicada. O pack possui JEI `19.53.0.426`.

JEI deve exibir recipes/uses úteis, mas não substitui o Lost Journal para mechanics não-recipe. Testar categorias e recipe transfer sem assumir cobertura total de todas as transformações.

## 11. Integração com Vampirism
O upstream documenta que **silver weapons causam mais dano a vampires/werewolves** de Vampirism. O pack possui Vampirism `1.10.13`.

Esta é integração concreta. Regression test obrigatório:
- alvo vampire/werewolf reconhecido;
- bônus aplicado uma única vez;
- Epic Fight/other damage modifiers não duplicam o multiplicador;
- dano server-authoritative.

## 12. Integração com Iron's Spells 'n Spellbooks
O projeto documenta que determinados mutagens concedem **School Spell Power**, com suporte introduzido na linha Mutagens Rework. O pack possui Iron's Spells `3.16.3`.

Validar que o atributo é registrado/reconhecido na versão atual do Iron's, persiste corretamente e não deixa modifiers órfãos após remover/alterar mutagen.

## 13. Integração com Curios
O **Toxicity Gauge** pode ser equipado no slot **Charm** de Curios. O pack possui Curios `9.5.1`.

O Gauge é superfície de visualização/equipamento do sistema; não deve se tornar uma segunda authority de Toxicity. Testar equip/unequip, death recovery e render/tooltip com outros charm items.

## 14. Client / server e multiplayer
- **Servidor:** Toxicity, mutagens, damage/effects, worldgen e regras persistentes.
- **Cliente:** HUD/tooltip/modelos/Journal e feedback.

Em multiplayer, cliente não pode definir localmente Toxicity ou mutagens. Mudanças precisam sincronizar para todos os observadores relevantes e sobreviver a reconnect.

## 15. Lifecycle
Validar:
- criação/login de player;
- aplicar/remover mutagen;
- consumir/aplicar toxin/oil;
- death/respawn;
- relog/server restart;
- equipar Toxicity Gauge;
- atacar entities Vampirism;
- lançar spells com School Spell Power alterado;
- gerar estruturas/plants em chunks novos;
- upgrade futuro entre Betas em cópia de teste.

## 16. Riscos técnicos
1. **Beta-only:** mudanças de balanceamento/data format podem ocorrer entre builds.
2. **Persistent mutation state:** modifiers órfãos ou duplicados são risco em update/remove.
3. **Attribute stacking:** Iron's/Epic Fight/outros RPG systems podem amplificar builds além do previsto.
4. **Weapon component compatibility:** oils sobre armas modded exigem smoke tests.
5. **Damage stacking:** Vampirism + Epic Fight + enchantments podem multiplicar bônus mais de uma vez.
6. **Worldgen density:** novas estruturas/plants competem com muitos providers.
7. **Journal/recipe drift:** guias externos de versões anteriores podem estar desatualizados.

## 17. Matriz de testes
- [ ] Dedicated server boot com Toxony 0.10.7.
- [ ] Lost Journal abre e orienta sem missing entries.
- [ ] Toxicity muda e persiste após relog/restart.
- [ ] Mutagen aplica/remove sem modifier duplicado ou órfão.
- [ ] Death/respawn preserva o state esperado do runtime.
- [ ] Oil aplica a arma vanilla e a uma arma modded suportada sem perder components.
- [ ] Silver weapon aplica bônus correto contra Vampirism sem double-hit modifier.
- [ ] Mutagen de Iron's altera School Spell Power e remove modifier corretamente.
- [ ] Toxicity Gauge funciona no Curios Charm slot.
- [ ] JEI mostra recipes principais da 0.10.7.
- [ ] Estruturas/plants aparecem em chunks novos sem worldgen crash.
- [ ] Dois clientes observam state/effects coerentes do mesmo jogador.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 18. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e integrations presentes.
- CurseForge oficial Toxony: Beta 0.10.7 para NeoForge 1.21.1; Lost Journal, Toxicity, plantas/materiais, mutagens, weapons/tools, oils, armors, alternativa a Netherite, structures e copper alchemy.
- CurseForge oficial: compat explícita com JEI, Vampirism, Iron's Spells e Curios; changelogs históricos usados somente para a origem dessas bridges, não para inferir valores atuais não publicados.

## 19. Revalidação física — 11/09/2026
A modlist física mantém exatamente `toxony-0.10.7.jar`, mod id `toxony`, versão `0.10.7`. A file list oficial continua tratando 0.10.7 como a Beta NeoForge 1.21.1 relevante; não há release estável 1.21.1 equivalente que substitua automaticamente este runtime.

As bridges com JEI, Vampirism, Iron's Spells e Curios continuam tecnicamente relevantes ao stack. Nenhuma fórmula de Toxicity, mutagen modifier, oil behavior ou estrutura foi inferida além das fontes, e nenhum teste runtime foi executado nesta recatalogação.
