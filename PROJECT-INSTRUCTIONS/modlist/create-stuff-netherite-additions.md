# Create Stuff & Netherite Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8132a980ead401792298
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Stuff & Netherite Additions
- **Arquivo JAR:** `create_sna-1.2-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Addon de progressão Netherite para Create Stuff 'N Additions, com Netherite Jetpack, Netherite Exoskeleton, Netherite Flywheel/Engine e compatibilidade com fueling/filling tanks.
- **Dependências:** Create Stuff 'N Additions é o host funcional; pack físico usa CSA v2.1.4b (runtime `2.1.4.`) + Create 6.0.10. Sem source matching público para pin de requisitos internos adicionais.
- **Sobreposição:** Extende CSA, não o substitui. Interage com a bridge Create SA Curios Jetpacks apenas se o item real for reconhecido no runtime; não atribuir IDs `create_sa:*` a este JAR sem registry/source comprobatório. Rotas externas de Netherite podem reduzir o custo do tier.
- **Compatibilidade/Riscos:** Riscos centrais: regressão fireproof em Jetpack/Exoskeleton/Flywheel; fueling/filling tanks não reconhecerem tier Netherite; dupe/loss de recurso; referências Auronite stale; Netherite Flywheel receber função cinética inventada; Curios bridge não reconhecer o item real; recipes de Netherite baratearem progressão; drift do CSA.
- **Observações:** JAR físico `create_sna-1.2-neoforge-1.21.1.jar`, mod id físico `create_nj`, runtime 1.2. Filename `create_sna` não substitui o mod id. Release 1.2 corrige itens Netherite não fireproof e compatibilidade com fueling/filling tanks.
- **Procedência:** modlist.txt física atual de 09/09/2026 + metadata runtime + publicação oficial Create Stuff & Netherite Additions 1.2. Não foi localizado source público matching; internals permanecem fail-closed.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-netherite-additions
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 1.2; tiers Netherite, fireproof fix, fueling/filling tank compatibility, recipes/progressão, lifecycle, boundaries com CSA/Curios e riscos catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🛡️ **Identidade física confirmada:** `create_sna-1.2-neoforge-1.21.1.jar`, mod id físico **`create_nj`**, runtime `1.2`, NeoForge 1.21.1. O filename usa `create_sna`, mas esse prefixo não substitui o mod id declarado pelo JAR.

## 1. Papel e authority
Create Stuff & Netherite Additions é um addon de progressão para **Create Stuff 'N Additions (CSA)**. Ele adiciona tiers Netherite derivados do ecossistema CSA. CSA continua owner das mecânicas-base de jetpacks/exoskeletons, fueling/filling e gadgets relacionados; este addon owns seus itens Netherite, recipes e propriedades específicas.

## 2. Dependência funcional em CSA
O mod não é substituto de Create Stuff 'N Additions. Sua função é estender a progressão existente com conteúdo Netherite. O pack físico contém CSA `create-stuff-additions1.21.1_v2.1.4b.jar`, mod id `create_sa`, runtime literal `2.1.4.`.
Integrações devem manter separado o namespace/ownership do host `create_sa` e deste addon, cujo mod id físico é `create_nj`.

## 3. Netherite Jetpack
A documentação oficial confirma um **Netherite Jetpack** mais forte como parte principal do addon. Ele deve ser tratado como tier derivado do sistema CSA, não como novo sistema de voo independente.
Fuel/water storage, refill e flight behavior precisam ser validados contra o host CSA efetivamente instalado; não inferir capacidade, velocidade ou consumo sem source/runtime comprovado.

## 4. Netherite Exoskeleton
A linha 1.1 adicionou **Netherite Exoskeleton**, mantido na 1.2. Ele amplia o tier de equipamento do CSA. A ficha não inventa atributos ou bonuses específicos sem registry/config matching; efeitos efetivos permanecem authority do runtime.

## 5. Netherite Flywheel
O projeto inclui **Netherite Flywheel**. O changelog 1.1 o descreve como **“Only Deco/Ingredient”**. Portanto não atribuir geração, transmissão especial de stress ou vantagem cinética só pelo nome/material.
Quando usado como ingrediente, recipes carregadas são authority; quando colocado como decoração, não presumir função mecânica adicional.

## 6. Netherite Engine
A galeria oficial mostra recipe de **Netherite Engine**, indicando outro componente do tier Netherite. Sem source matching, esta auditoria não inventa registry ID, stats ou comportamento cinético específico. A integração deve observar item/bloco/recipe efetivamente carregado.

## 7. Fireproof — fix 1.2
O changelog exato da versão 1.2 corrige **Netherite Items (Jetpack, Exoskeleton, Flywheel) not being fireproof**. Isso torna resistência a fogo/lava/despawn por fire um regression gate desta build.
“Fireproof” aqui é propriedade dos itens corrigidos; não assumir imunidade do jogador, do combustível interno ou de entidades associadas sem evidência separada.

## 8. Fueling/Filling tank compatibility — fix 1.2
A 1.2 também corrige **fueling/filling tank compatibility**. Como o addon depende da infraestrutura CSA, refill dos itens Netherite precisa interoperar com os tanks/armazenamento da versão física do host.
A correção deve ser testada em ambos os recursos esperados pelo equipamento; não marcar como resolvida no pack apenas pelo changelog.

## 9. Mudança histórica 1.1
A 1.1 adicionou Netherite Exoskeleton e Netherite Flywheel, removeu **Auronite** e reformulou recipes. Isso importa para mundos/configs antigos: recipes/scripts que ainda referenciem Auronite ou outputs antigos podem ficar stale após atualização.
No pack atual, o estado carregado da versão 1.2 é authority; referências legadas não devem ser recriadas automaticamente.

## 10. Recipes e progressão
O addon usa Netherite como tier de upgrade/produção associado ao CSA. Essa progressão pode se sobrepor a outros mods que facilitam Netherite ou criam upgrades equivalentes. Avaliar custo líquido por recipe efetivamente carregada, não apenas por nome do tier.
Create: Deep Dark 3.0.2, por exemplo, possui rota Create de Netherite com double yield; isso pode reduzir indiretamente o custo dos equipamentos deste addon sem constituir incompatibilidade técnica.

## 11. Relação com Create SA Curios Jetpacks
O pack também contém **Create SA Curios Jetpacks**. Essa bridge reconhece jetpacks CSA em Curios e documenta suporte a um `create_sa:netherite_jetpack_chestplate` na sua própria linha de source.
Como este addon possui mod id físico `create_nj` e não há registry dump/source matching que prove identidade entre esse ID e o Netherite Jetpack deste JAR, esta ficha **não atribui** aquele registry ID ao conteúdo de Create Stuff & Netherite Additions. A compatibilidade deve ser validada pelo item real no runtime.

## 12. Client/server e multiplayer
Models/render são client-facing. Crafting, item properties, fire resistance, refill/fueling state e uso do equipamento devem ser server-authoritative. Em multiplayer, um item movido entre slots/inventários não pode duplicar resource state ou perder propriedades Netherite.

## 13. Lifecycle de equipamento
Testar craft/upgrade, equip/unequip, refill, uso, break/drop, death, lava/fire exposure, item entity em fogo, dimension change, relog e restart. O equipamento precisa preservar componentes/recursos e continuar reconhecido pelo host CSA após reload.

## 14. Data e recipe reload
Recipes podem ser alteradas por datapacks/KubeJS. `/reload` deve substituir recipe state sem manter rotas Auronite/antigas ou outputs duplicados. Recipe viewers não são authority da execução.

## 15. Riscos
1. Regressão 1.2: Jetpack/Exoskeleton/Flywheel deixam de ser fireproof.
2. Fueling/filling tanks não reconhecem o tier Netherite.
3. Refill duplica ou perde fuel/water ao atravessar addon↔CSA.
4. Scripts antigos ainda referenciam Auronite ou recipes pré-1.1.
5. Netherite Flywheel recebe função cinética inexistente por integração externa.
6. Registry ID do Netherite Jetpack é inferido incorretamente a partir de outro mod/bridge.
7. Curios bridge não reconhece o item real deste addon ou o trata como item CSA diferente.
8. Recipes de Netherite do restante do pack tornam o tier barato demais.
9. Upgrade/crafting perde components ou resource state.
10. Version drift do CSA altera tank/storage API usada pela compatibilidade.

## 16. Matriz de testes
- [ ] Dedicated server inicia com addon 1.2 + CSA 2.1.4b + Create 6.0.10.
- [ ] Netherite Jetpack é craftável/obtenível pela recipe efetivamente carregada.
- [ ] Netherite Exoskeleton funciona como tier próprio sem atributos duplicados.
- [ ] Netherite Flywheel permanece Deco/Ingredient conforme upstream, sem função cinética inventada.
- [ ] Jetpack/Exoskeleton/Flywheel sobrevivem à exposição de item a fogo conforme o fix 1.2.
- [ ] Fueling tank abastece equipamento compatível sem dupe/loss.
- [ ] Filling tank abastece equipamento compatível sem dupe/loss.
- [ ] Refill continua válido após relog/restart.
- [ ] Curios bridge reconhece apenas os itens realmente suportados no runtime.
- [ ] Scripts/datapacks não possuem referências Auronite stale.
- [ ] `/reload` mantém recipes coerentes e únicas.
- [ ] Progressão Netherite não cria loop/custo trivial com outras rotas do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
A modlist física confirma `create_sna-1.2-neoforge-1.21.1.jar`, mod id **`create_nj`** e runtime 1.2. A publicação oficial confirma Release NeoForge 1.21.1, Client & Server, Netherite Jetpack/Exoskeleton/Flywheel/Engine e os fixes 1.2 de fireproof e fueling/filling tank compatibility. Não foi localizado source público matching da build; classes, registry IDs, capacidades e stats internos permanecem fail-closed.

> 🔒 **Boundary canônico:** este addon owns os tiers Netherite que adiciona; CSA owns as mecânicas-base de jetpack/exoskeleton e tanks. Filename `create_sna` não autoriza substituir o mod id físico `create_nj` nem inferir IDs do namespace `create_sa`.