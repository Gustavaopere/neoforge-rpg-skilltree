# Create: Integrated Farming

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81ea9219ff5a8a9d8776  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Integrated Farming
- **Arquivo JAR:** `create-integrated-farming-1.4.1b.jar`
- **Versão 1.21.1:** `1.4.1b`
- **Categoria:** Automação; Comida; Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-integrated-farming/files/8811301
- **Função:** Expande a automação agrícola do Create com máquinas e integrações especializadas para colheita, pesca, coleta de produtos animais, compostagem e compatibilidade com crops/containers de outros mods.
- **Dependências:** Create 6.0.10 + Create: Dragons Plus. Farmer's Delight é fortemente recomendado pelo projeto; integrações adicionais são condicionais aos mods alvo realmente presentes.
- **Compatibilidade/Riscos:** Interage com harvesting, crops modded, fishing loot, moving Sable sublevels, Mechanical Arms, compostagem e integrações agrícolas. A 1.4.1b corrige crash ao gerar catches de Fishing/Lava Fishing Nets com Tide 2.1.1; validar esse caminho se Tide estiver ativo.
- **Sobreposição:** Complementa Create com automação agrícola específica. Create controla primitives cinéticas/logísticas; providers agrícolas controlam seus crops/loot/containers; Integrated Farming adapta essas authorities sem substituí-las.
- **Observações:** mod id `create_integrated_farming`; runtime 1.4.1b. A ficha antiga ainda citava runtime 1.3.3b; corrigido em 08/09/2026. 1.4.1b é Release NeoForge 1.21.1 para Create 6.0.10 e hotfix de compatibilidade com Tide 2.1.1.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_integrated_farming` 1.4.1b + CurseForge/Modrinth oficiais da release 1.4.1b e descrição técnica do projeto.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Integrated Farming 1.4.1b foi reconfirmado como `Instalado`, a referência residual 1.3.3b foi corrigida e a ficha reconstruída ao padrão técnico. Presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — farming/fishing automation, harvesting authority, Arm/Spout/Sable integrations, lifecycle, config/data boundaries e regressão 1.4.1b catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🌾 Versão física confirmada: `create-integrated-farming-1.4.1b.jar`, mod id `create_integrated_farming`, runtime `1.4.1b`, NeoForge 1.21.1. A build é feita para **Create 6.0.10** e expande a automação agrícola sem substituir a authority dos crops/loot dos mods integrados.

## 1. Papel e authority
Create: Integrated Farming adiciona appliances e bridges de agricultura/pesca ao ecossistema Create. **Create** continua authority de rotação, Mechanical Arms, Spouts e contraptions; cada mod agrícola continua authority de seus crops, drops, maturity rules e containers; Integrated Farming decide apenas suas máquinas e regras de adaptação.

## 2. Vacuum Harvester
O projeto documenta o **Vacuum Harvester**, um harvester rotacional em área que coleta e replanta crops maduros dentro de seu alcance. A maturidade precisa ser resolvida pelo crop/provider real; integração externa não deve colher novamente o mesmo block state após o addon já ter realizado o settlement.

## 3. Fishing Net
A **Fishing Net** funciona como componente de contraption capaz de pescar e também capturar pequenas criaturas aquáticas suportadas. Loot e entidade capturada devem resultar de uma única execução; outro fishing automation não deve rolar a mesma captura em paralelo.

## 4. Lava Fishing Net
A **Lava Fishing Net** estende a automação de pesca para lava quando integrações compatíveis estão presentes, incluindo Tide, Nether Depths Upgrade, Starcatch ou Confluence conforme documentação atual. A elegibilidade depende do mod alvo realmente instalado.

A build 1.4.1b corrige especificamente crash quando Fishing Nets ou Lava Fishing Nets geram catches com **Tide 2.1.1**.

## 5. Poultry Roosts
Poultry Roosts automatizam produtos de aves suportadas. O projeto lista integrações com galinhas vanilla/Vanilla Backport e, condicionalmente, patos/gansos/perus de outros mods. Produção, cooldown e item gerado devem seguir a entidade/provider correspondente; não assumir suporte a qualquer ave por tag genérica.

## 6. Harvester compatibility
Mechanical Harvesters e Vacuum Harvesters possuem regras especiais para crops suportados, incluindo vanilla, tomates de Farmer's Delight, mushroom colonies, tall crops e outras integrações publicadas. O ponto técnico é **maturity-aware harvesting**: o addon deve preservar replant/state e não reduzir um crop multi-bloco a uma quebra vanilla cega.

## 7. Expanded crop integrations
A documentação atual inclui regras dedicadas para crops de Cultural Delights, Hearth and Harvest, Windswept, Festive Delight, Nether's Exoticism e outros providers. Essas integrações são condicionais; não registrar como dependência obrigatória nem como conteúdo do próprio Integrated Farming.

## 8. Mechanical Arm harvesting e logistics
Mechanical Arms podem ganhar targets agrícolas específicos, como harvesting de crops ou interação com Baskets/Crab Traps quando os mods correspondentes existem. **O Arm controla transporte/interação; o container/crop provider controla validade e state final**.

Retries ou dois Arms concorrentes não podem duplicar harvest/output.

## 9. Spout composting
Spouts podem acelerar processos de compostagem suportados, como Organic Compost de Farmer's Delight e Leteos Compost de My Nether's Delight. O addon adapta a aplicação do fluido/processo; o item/block provider continua authority do estado de compostagem.

## 10. Create: Enchantable Machinery
Quando Create: Enchantable Machinery está presente, seu Enchantable Harvester pode usar as mesmas regras especiais de crop harvesting. Essa bridge deve reutilizar a decisão de maturidade em vez de manter duas implementações concorrentes.

## 11. Sable e moving sublevels
Com **Sable**, máquinas suportadas como Spouts, Vacuum Harvesters e Fishing Nets podem interagir entre parent world e sublevels móveis/intersecting. Capability/block references precisam ser resolvidas no espaço correto e invalidadas ao assembly/disassembly para evitar phantom harvesting ou dupe.

## 12. Simulated Series
Com Create Simulated Series, a documentação descreve Auger Shaft coletando output de painéis conectados/copla-nares de nets. O output do painel precisa ter ownership único; múltiplos collectors não podem consumir a mesma captura duas vezes.

## 13. Client/server e lifecycle
Harvest, loot, replant, entity capture e inventory mutations são server/common. Models, Ponder e feedback são client-facing. Validar chunk unload/reload, contraption assembly, datapack/recipe reload, optional-mod load conditions, reconnect e server restart.

## 14. Riscos
1. Crop colhido duas vezes por handlers concorrentes.
2. Replant incorreto em crop multi-stage/multi-block.
3. Fishing loot ou entidade duplicada.
4. Tide 2.1.1 regression em nets.
5. Optional integration classloading sem provider.
6. Sable sublevel resolver block/inventory do mundo errado.
7. Poultry production duplicada.
8. Arm/Spout operar em state stale após reload.

## 15. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + Dragons Plus.
2. Vacuum Harvester em crops vanilla e modded realmente instalados.
3. Replant e maturity em crops multi-stage/multi-block.
4. Fishing Net: item loot + pequena criatura aquática.
5. Lava Fishing Net com provider compatível; Tide 2.1.1 como regression gate quando presente.
6. Poultry Roost com cada integração de ave realmente instalada.
7. Mechanical Arm harvesting/logistics sem double settlement.
8. Spout composting.
9. Sable: parent world ↔ moving sublevel e sublevel ↔ sublevel.
10. Restart/chunk reload/datapack reload.

## 16. Evidência
- modlist física 08/09/2026: `create-integrated-farming-1.4.1b.jar`, runtime 1.4.1b;
- CurseForge/Modrinth oficiais: farming appliances, Vacuum Harvester, Fishing/Lava Fishing Nets, Poultry Roosts, crop/Arm/Spout integrations e Sable compatibility;
- release 1.4.1b: fix de crash de catches com Tide 2.1.1.

> 🔒 Boundary canônico: **Integrated Farming automatiza a interação; o crop, loot, entidade ou container original continua decidindo seu state e conteúdo**.
