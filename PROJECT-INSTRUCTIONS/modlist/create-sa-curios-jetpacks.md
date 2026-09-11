# Create SA Curios Jetpacks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81598f20ed4314797b1c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create SA Curios Jetpacks
- **Arquivo JAR:** `create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar`
- **Versão 1.21.1:** 1.2.22
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL, Tecnologia
- **Função:** Bridge CSA↔Curios para jetpacks no slot body, refill tanks no belt/inventory, render e atributos opcionais, preservando o comportamento normal do chest slot sob Create Stuff 'N Additions.
- **Dependências:** Pack físico: Create Stuff 'N Additions `create-stuff-additions1.21.1_v2.1.4b.jar` (runtime `2.1.4.`), Curios API 9.5.1+1.21.1 e Create 6.0.10. Publicação 1.2.4 foi testada com CSA 2.1.4a.
- **Sobreposição:** Não substitui CSA nem Curios. CSA owns voo/tanks/recursos; Curios owns slots; a bridge amplia descoberta/refill/render. Testar especificamente o host físico CSA 2.1.4b, posterior ao 2.1.4a documentado na publicação.
- **Compatibilidade/Riscos:** Riscos centrais: regressão de refill no Curios body; CSA 2.1.4b ser posterior ao 2.1.4a testado upstream; storage novo + tags legadas double-count; belt+inventory tanks debitarem juntos; chest/body concorrentes; armor stats stackarem; render/equip desync; drift 1.2.4↔1.2.22↔source público.
- **Observações:** JAR físico `create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar`, mod id `create_sa_curios_jetpacks`, runtime metadata `1.2.22`. Filename/publicação oficial = 1.2.4. Não normalizar. 1.2.4 corrige body-slot refill com CSA recente e adiciona suporte ao storage 2.1.4a mantendo tags legadas.
- **Procedência:** modlist.txt física atual de 09/09/2026 + metadata runtime 1.2.22 + publicação oficial 1.2.4 + repositório público LittleFoeAppears/create-sa-curios-jetpacks usado apenas como evidência de arquitetura, pois seu versionamento não pinna o JAR físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stuff-additions-curios-jetpacks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo; divergência filename/publicação 1.2.4 ↔ runtime 1.2.22 preservada; body/belt Curios, refill, storage novo/legado, armor stats, render, lifecycle e riscos catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🎒 **Identidade física confirmada:** `create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar`, mod id `create_sa_curios_jetpacks`, runtime metadata **`1.2.22`**. O filename/publicação oficial rotula a build como **1.2.4**; essa divergência é preservada e não normalizada.

## 1. Papel e authority
Create SA Curios Jetpacks é uma **bridge de compatibilidade** entre Create Stuff 'N Additions (CSA) e Curios. Ela não cria um sistema de jetpack concorrente: CSA continua owner dos jetpacks, tanques, combustível/água e voo; Curios owns slots/equip state; a bridge adapta descoberta, refill, render e atributos quando esses itens estão em slots Curios.

## 2. Dependências concretas no pack
O pack contém Create Stuff 'N Additions `create-stuff-additions1.21.1_v2.1.4b.jar`, mod id `create_sa`, runtime literal `2.1.4.`; Curios API 1.21.1 está presente no stack; Create 6.0.10 é a infraestrutura Create do pack.
A publicação 1.2.4 da bridge foi testada contra **CSA 2.1.4a**. O host físico do pack é **2.1.4b**, uma revisão posterior; isso exige regression test, não autoriza declarar incompatibilidade.

## 3. Curios `body` slot
A bridge permite que jetpacks CSA equipados no slot Curios `body` sejam reconhecidos para voo. O comportamento normal do jetpack no vanilla chest slot permanece sob CSA.
O source público atual também explicita que, quando há jetpack CSA no chest slot normal, a bridge evita interferir com o handling normal do provider.

## 4. Curios `belt` slot e refill tanks
Tanques CSA podem ser usados no slot Curios `belt` como fontes de refill. O upstream documenta que belt tanks podem abastecer jetpacks no body slot e também jetpacks usados no chest slot normal.
A escolha/consumo da fonte precisa ser determinística quando há múltiplos tanques compatíveis.

## 5. Fix principal da publicação 1.2.4
O changelog oficial da build rotulada **1.2.4** corrige jetpacks no Curios body slot que deixavam de ser reabastecidos corretamente com versões recentes de Create Stuff 'N Additions.
Esse fix é o regression gate principal: o pack usa CSA 2.1.4b, portanto body-slot refill precisa ser validado diretamente na combinação física.

## 6. Nova fluid storage do CSA 2.1.4a+
A publicação 1.2.4 declara suporte ao novo modelo de armazenamento de fluido introduzido/alterado no CSA 2.1.4a. A bridge mantém também suporte a dados legados como `tagStock`, `tagFuel` e `tagWater`.
Isso cria uma migration boundary: leitura/escrita não pode duplicar recurso ao enxergar simultaneamente storage novo e tags antigas.

## 7. Inventory tanks
O changelog 1.2.4 também confirma que **tanques no inventário** podem reabastecer jetpacks no body slot. O provider CSA continua owner da capacidade/recurso; a bridge apenas amplia a localização em que a fonte é encontrada.
A ordem entre belt tank e inventory tank deve ser testada para evitar double refill ou double debit.

## 8. Recursos por tipo de jetpack
O source público da bridge documenta a seguinte matriz de recursos para os jetpacks CSA reconhecidos:
- Andesite Jetpack: combustível;
- Copper Jetpack: água;
- Brass Jetpack: combustível + água;
- Netherite Jetpack: combustível + água.

Essa matriz é suporte da bridge aos IDs CSA reconhecidos; consumo e voo continuam definidos pelo provider CSA.

## 9. IDs CSA reconhecidos no source público
O README/source público atual lista jetpacks `create_sa:brass_jetpack_chestplate`, `create_sa:andesite_jetpack_chestplate`, `create_sa:copper_jetpack_chestplate` e `create_sa:netherite_jetpack_chestplate`, além das famílias de filling/fueling tanks em namespace `create_sa`.
O source público não é pin-matching da metadata física 1.2.22; os IDs são usados como evidência de arquitetura da bridge, não como prova byte-a-byte do JAR instalado.

## 10. Basin refill no source público atual
A linha pública atual documenta refill de tanks ao clicar em basins Create: água para Filling Tanks, lava para Fueling Tanks, consumindo 1000 mB e adicionando stock conforme o comportamento descrito pelo projeto.
Como essa feature vem de source que não corresponde exatamente ao versionamento físico, ela deve ser validada no runtime antes de ser tratada como política garantida da build instalada.

## 11. Armor stats opcionais
A bridge possui opção para jetpacks equipados via Curios fornecerem os atributos base de chestplate. O source público atual limita isso a **armor**, **armor toughness** e **knockback resistance**.
Jetpack em Curios não é transformado em vanilla chest armor completo: enchantments e hooks de outros mods podem responder de forma diferente.

## 12. Interação com chest armor
O source público expõe dois modos: atributos do jetpack apenas quando o chest slot está vazio, ou **stack com chest armor**. O segundo modo pode somar proteção com uma chestplate normal e é um ponto explícito de balanceamento.
Integrações de RPG/atributos não devem adicionar novamente esses mesmos modifiers sem checar o state real.

## 13. Render e visibilidade Curios
A bridge renderiza jetpack/tank quando a visibilidade do slot Curios está habilitada. Render é client-facing; a presença visual não é authority de equip state, combustível ou voo.
Clientes devem convergir sobre item equipado/visibility sem criar jetpack visual fantasma após relog ou troca de slot.

## 14. Multiplayer
O projeto declara instalação necessária em **servidor/host e todos os clientes**. Slot state, refill e consumo devem ser resolvidos server-side; render/config UI permanecem client-facing onde aplicável.
Troca simultânea de body/belt/chest/inventory durante refill não pode produzir item ou recurso duplicado.

## 15. Divergência de versionamento
Há três identificadores relevantes e todos devem permanecer explícitos:
- filename: `...-1.2.4.jar`;
- publicação oficial: 1.2.4;
- runtime metadata física: **1.2.22**.

Além disso, o repositório público atual possui README que gera `create_sa_curios_jetpacks-1.2.18.jar` e `gradle.properties` desatualizado em outro número. Portanto source atual não é pin confiável para a versão física; JAR/runtime é authority.

## 16. Lifecycle e ownership de recurso
Testar body↔chest, belt↔inventory, equip/unequip, death, respawn, dimension, relog e restart. Jetpack e tank precisam manter um único state de recurso; a bridge não pode copiar fuel/water ao mover o item entre inventários/slots.

## 17. Riscos
1. Regressão do body-slot refill corrigida na publicação 1.2.4.
2. CSA 2.1.4b diverge da versão 2.1.4a testada upstream.
3. Storage novo + tags legadas resultam em double-count de água/fuel/stock.
4. Belt e inventory tank debitam/refillam no mesmo tick.
5. Chest e body jetpack são tratados como duas fontes simultâneas.
6. Armor stats empilham com chest armor além do balanceamento desejado.
7. Enchantment/modded armor hook espera vanilla chest slot e falha em Curios.
8. Render diverge do equip state real.
9. Relog/death deixa Curios state fantasma ou perde item.
10. Version drift 1.2.4↔runtime 1.2.22↔source público impede atribuir internals sem validação.

## 18. Matriz de testes
- [ ] Dedicated server inicia com bridge + CSA 2.1.4b + Curios + Create 6.0.10.
- [ ] Andesite/Copper/Brass jetpack no body slot voam usando apenas os recursos válidos.
- [ ] Body-slot jetpack é reabastecido por belt tank na combinação CSA 2.1.4b.
- [ ] Inventory tank reabastece body-slot jetpack sem double debit.
- [ ] Belt + inventory tanks simultâneos não duplicam refill.
- [ ] Storage novo e tags legadas não contam o mesmo recurso duas vezes.
- [ ] Chest-slot jetpack continua sob handling normal do CSA.
- [ ] Trocar jetpack body↔chest preserva recurso/state.
- [ ] Armor stats respeitam o modo configurado sem double modifiers.
- [ ] Enchantments/modded armor hooks não são presumidos equivalentes ao chest slot.
- [ ] Relog/death/dimension preservam item e recurso sem ghost state.
- [ ] Dois clientes veem render/slots coerentes com o servidor.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
A modlist física confirma JAR, mod id e runtime **1.2.22**. A publicação oficial 1.2.4 confirma o fix de body-slot refill, suporte ao storage CSA 2.1.4a, refill por belt/inventory tanks e compatibilidade legada. O repositório público `LittleFoeAppears/create-sa-curios-jetpacks` confirma arquitetura body/belt, armor config, render e IDs CSA suportados, mas seu versionamento público não pinna o JAR físico; internals são tratados como evidência de linha, não equivalência binária.

> 🔒 **Boundary canônico:** CSA owns jetpacks/tanks/recursos/voo; Curios owns slots; esta bridge owns apenas a compatibilidade entre essas superfícies. Qualquer refill deve conservar um único resource state e executar uma única vez.