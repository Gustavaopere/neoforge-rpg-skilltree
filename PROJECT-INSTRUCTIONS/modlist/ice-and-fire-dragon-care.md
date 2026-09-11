# Ice And Fire: Dragon Care

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8152939af182e93f7c8d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Ice And Fire: Dragon Care
- **Arquivo JAR:** `Ice and Fire - Dragon Care-1.3.1 - 1.21.1v.jar`
- **Versão 1.21.1:** 1.3.1 - 1.21.1v
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, RPG, QoL
- **Função:** Addon de husbandry para dragões Ice and Fire CE: bonding, alimentação/tratamento, coleta veterinária não letal, limpeza/QTE, Dragon Phone, Ash systems, farming, loot e estruturas.
- **Dependências:** Ice And Fire Community Edition 2.1.2. Source 1.3.1 usa NeoForge 21.1.248 e declara compatibilidade universal com IAF CE 2.x; GeckoLib é opcional conforme o renderer da build IAF.
- **Sobreposição:** Expande cuidado de dragões IAF; não substitui Ice And Fire CE nem transfere tame/growth/combat authority. Outros husbandry mods não são equivalentes sem bridge explícita.
- **Compatibilidade/Riscos:** IAF CE 2.1.2 é explicitamente suportado. Riscos: renderer autodetection/mixin Gecko-Tabula, bond/owner persistence, harvest double-grant, Dragon Phone stale UUID, texture cache, Ash double effect, worldgen/loot/recipe-condition drift e conflito com outros addons de dragão.
- **Observações:** JAR físico traz mixins common, GeckoLib, worldgen e Tabula. Source confirma items/blocks/effects/sounds/loot modifiers/recipe conditions/Dragon Phone data components. Release 1.3.1 suporta explicitamente IAF CE 2.1.2.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial OrionTheDragon/DragonCare módulo Addon exatamente em 1.3.1 + release/changelog oficial file 8777050 + documentação oficial de features.
- **Fonte:** https://github.com/OrionTheDragon/DragonCare
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Dragon Care 1.3.1 source-pinned; IAF CE 2.1.2 compatibility, bonding, veterinary harvest, cleaning/render paths, Dragon Phone, Ash/worldgen/loot, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Ice and Fire - Dragon Care-1.3.1 - 1.21.1v.jar`, mod id `dragoncare`, versão `1.3.1 - 1.21.1v`. O source oficial `OrionTheDragon/DragonCare`, módulo `Addon`, declara exatamente essa versão e NeoForge `21.1.248`, igual ao loader físico do pack. A release 1.3.1 declara suporte explícito ao Ice and Fire CE 2.1.2 instalado.

## 1. Papel e authority
Dragon Care é addon de husbandry para dragões domesticados do **Ice And Fire Community Edition**. Ele é authority de bonding/cuidado, ferramentas veterinárias, limpeza, Dragon Phone, Ash Poisoning e conteúdo próprio; Ice and Fire CE permanece authority da entidade dragão, tame/owner-base, crescimento, combate e recursos originais.

## 2. Compatibilidade 1.3.1 com Ice and Fire CE
A release 1.3.1 foi feita para compatibilidade universal com builds oficiais IAF CE 2.x de 1.21.1, incluindo explicitamente **2.1.2**. O addon detecta em runtime se a classe de dragão usa o render stack Tabula/Uranus ou GeckoLib e seleciona a integração correspondente. GeckoLib é opcional para Dragon Care em si e só é necessário quando a build IAF escolhida o exige.

## 3. Mixins e superfícies físicas
O JAR físico declara `dragoncare.mixins.json`, `dragoncare-gecko.mixins.json`, `dragoncare-worldgen.mixins.json` e `dragoncare-tabula.mixins.json`. Isso confirma patches separados para common behavior, GeckoLib, worldgen e Tabula. Falha em um compatibility path não deve ser mascarada como defeito do outro renderer.

## 4. Registries source-pinned
O source 1.3.1 expõe registries próprios para items, blocks, effects, sounds, global loot modifiers, recipe conditions e data components do Dragon Phone. O addon portanto participa de registry sync, loot/data e item serialization; não é somente AI tweak.

## 5. Bonding e cuidado prolongado
O projeto descreve bonding progressivo obtido por alimentação, tratamento e interação. Bond level pode conceder efeitos/recompensas ao par jogador-dragão. Esse vínculo precisa permanecer associado ao owner/dragão correto em relog, dimension transfer, chunk unload e server restart; uma bridge externa não deve manter segundo ledger paralelo de bond.

## 6. Coleta não letal de recursos
Dragon Blood Syringe e Scale Shears permitem obtenção de sangue/escamas sem matar o dragão; Dragon Painkillers integra o cuidado veterinário. A coleta é transacional: validar cooldown/estado/alvo antes de conceder item. Clique repetido, lag ou replay de pacote não pode duplicar recurso nem aplicar custo duas vezes.

## 7. Limpeza e Dragon Brush
Dragões domesticados acumulam sujeira e podem ser limpos com Dragon Brush por uma interação em formato de minigame/QTE. A apresentação visual usa blending dinâmico de textura e cache client-side. O servidor deve decidir o state lógico de limpeza/bond reward; o cliente apenas apresenta textura/input. Resource reload ou troca do renderer não deve deixar textura de sujeira stale.

## 8. Dragon Fruit e conteúdo de farming
O addon adiciona Dragon Fruit e farming associado. Recipes/crops/loot continuam pertencendo ao addon. Integrações agrícolas do pack não devem presumir compatibilidade só porque o item é crop-like; tags e recipes precisam ser confirmadas no runtime/data real.

## 9. Dragon Phone
Dragon Phone é dispositivo de tracking/HUD que apresenta dragões do jogador, nomes, estágio de crescimento, índice e distância. O source confirma data components dedicados para o sistema. O cliente não deve descobrir/alterar ownership por conta própria: a lista precisa refletir state authoritative do servidor e invalidar entradas quando um dragão morre, muda de owner ou deixa de ser válido.

## 10. Ash Poisoning
O projeto inclui Ash Poisoning, Ash Sensor e Ash Tablets. O effect/state deve ser aplicado e removido no servidor; HUD/feedback é client-side. Sensores e remédios não devem provocar cura/dano duas vezes por tick/event duplicado.

## 11. Estruturas e worldgen
Dragon Care adiciona estruturas como ruined hunter camps e abandoned guild halls. O mixin worldgen físico confirma intervenção nessa superfície. Mudanças de versão/config de structure generation precisam ser validadas em mundos novos/chunks não gerados; não assumir retrofit em regiões existentes.

## 12. Loot modifiers e recipe conditions
O source registra Global Loot Modifiers e recipe conditions, portanto disponibilidade de itens pode depender de loot injection e condições de compatibilidade. `/reload` deve ser regression-tested; falha de codec/condition pode remover recipes/loot silenciosamente do gameplay esperado.

## 13. Client / server
A distribuição é Client & Server. Servidor é authority de bond, resource harvesting, effects, item grants, tracking ownership e worldgen. Cliente é responsável por Dragon Phone HUD, QTE/presentation, texture blending e renderer-specific patches. Os dois lados precisam usar compatível registry/data version.

## 14. Lifecycle e multiplayer
Validar tame/owner inheritance, bonding, feeding/treatment, blood/scale collection, cleaning, dragon death, owner logout, dragon chunk unload, dimension transfer, server restart, resource reload e IAF renderer detection. Em multiplayer, dois jogadores interagindo com o mesmo dragão não podem receber o mesmo recurso/reward por uma única janela de coleta.

## 15. Integrações concretas no pack
- **Ice And Fire CE 2.1.2:** provider-base e versão explicitamente suportada pela 1.3.1.
- **GeckoLib 4.9.2:** presente; IAF CE 2.1.2 usa a linha GeckoLib e Dragon Care possui mixin dedicado.
- **Uranus 3.0-beta.1:** presente no pack; pertence ao ecossistema/compatibilidade IAF, mas a 1.3.1 detecta o renderer pela classe real em vez de assumir versão.
Outros animal/husbandry mods não recebem ownership sobre dragões IAF sem integração explícita.

## 16. Riscos técnicos
- update de IAF CE alterar classe/renderer e quebrar autodetection;
- bond/owner state stale após reconnect/dimension change;
- coleta de blood/scales duplicada por lag/retry;
- Dragon Phone manter referência a dragon UUID inválido;
- texture cache de sujeira persistir após reload;
- Gecko/Tabula mixin path errado carregar no ambiente incorreto;
- worldgen/loot modifier causar data errors;
- efeito Ash ser processado mais de uma vez;
- dois addons modificarem a mesma entidade dragão sem prioridade clara.

## 17. Matriz de testes obrigatória
- [ ] Dedicated server boot com Dragon Care 1.3.1 + IAF CE 2.1.2.
- [ ] Detectar corretamente o renderer usado pelos dragões da build atual.
- [ ] Bond cresce/persiste por dragão e owner em relog/restart.
- [ ] Blood Syringe/Scale Shears concedem recurso exatamente uma vez por ação válida.
- [ ] Painkillers/feeding/treatment alteram apenas o dragão alvo.
- [ ] Brush/QTE limpa e atualiza textura sem cache stale após F3+T/reconnect.
- [ ] Dragon Phone lista apenas dragões válidos do owner e atualiza distância/stage.
- [ ] Ash Poisoning/Sensor/Tablets funcionam sem double effect.
- [ ] Estruturas novas geram em chunks novos sem conflito crítico.
- [ ] Loot modifiers/recipe conditions sobrevivem a `/reload`.
- [ ] Dois jogadores no mesmo dragão não duplicam harvest/reward.

## 18. Evidências e limites
- **Modlist física:** JAR, mod id, versão e quatro mixin configs; IAF CE 2.1.2/GeckoLib/Uranus presentes.
- **Source oficial:** módulo 1.21.1 em `OrionTheDragon/DragonCare`, `mod_version=1.3.1 - 1.21.1v`, NeoForge 21.1.248 e registries citados.
- **Release oficial 1.3.1:** compatibilidade explícita com IAF CE 2.1.2 e autodetection Tabula/Uranus vs GeckoLib.
- **Documentação oficial:** bonding, veterinary tools, cleaning, Dragon Fruit, Dragon Phone, Ash systems e structures.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
