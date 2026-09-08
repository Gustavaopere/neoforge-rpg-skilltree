# Create: Enchantment Industry

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8187aa5dda55e65eee9a  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Enchantment Industry
- **Arquivo JAR:** `create-enchantment-industry-2.5.3b.jar`
- **Versão 1.21.1:** `2.5.3b`
- **Categoria:** Tecnologia; Automação; Magia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-enchantment-industry
- **Função:** Addon de Create para industrializar experiência e encantamentos: Liquid Experience, máquinas de enchanting/forging/grinding/printing, repair com Mending e automações/integrações de Apotheosis/Apothic Enchanting.
- **Dependências:** Create 6.0.10 e Create: Dragons Plus para a linha 2.x. Apothic Enchanting é integração condicional; na build 2.5.3b a integração requer 1.6.1+ para o caminho corrigido do Infuser.
- **Compatibilidade/Riscos:** Economia sensível de XP/enchantments. Riscos em conversão de XP, hyper-enchant acima de caps, mending, salvage/affix integration, recipe reload e double-processing com outros sistemas de enchant. 2.5.3b corrige crash do Infuser com Apothic Enchanting 1.6.1+.
- **Sobreposição:** Pode cruzar Apothic Enchanting, vanilla enchanting e outras rotas de XP, mas possui processos próprios. Create continua authority das primitives de automação; CEI controla sua economia Liquid Experience/máquinas.
- **Observações:** mod id `create_enchantment_industry`; runtime 2.5.3b; build Beta NeoForge 1.21.1. JAR embarca `conditional-mixin-neoforge-0.6.4.jar` em `/META-INF/jarjar/`, não top-level. 2.5.3b é hotfix do Infuser/Apothic Enchanting.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime 2.5.3b + CurseForge/Modrinth oficiais da build 2.5.3b para Create 6.0.10 + documentação oficial da linha 2.x.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Enchantment Industry 2.5.3b foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A antiga data sem decisão formal foi removida; presença/uso não foram convertidos em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — liquid-XP/enchant authority, machines, hyper-enchanting, printer/mending flows, Apotheosis integration, lifecycle e regressões 2.5.3b catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✨ Versão física confirmada: `create-enchantment-industry-2.5.3b.jar`, mod id `create_enchantment_industry`, runtime `2.5.3b`, NeoForge 1.21.1. É uma **Beta** construída para Create 6.0.10 e focada em XP/enchanting industrial.

## 1. Papel e authority
Create: Enchantment Industry transforma experiência e encantamento em processos automatizáveis pelo Create. **CEI é authority de Liquid Experience e de suas máquinas/recipes**; Minecraft/Apothic Enchanting continuam authority de enchantments/affixes que o addon consome ou integra.

## 2. Liquid Experience
Liquid Experience é a forma de experiência de alta densidade usada pelo addon para armazenamento/transporte e alimentação de processos. Conversão player↔fluid precisa preservar saldo; bridges externas não podem debitar XP e também creditar fluid por uma segunda rota.

## 3. Experience Hatch
Experience Hatch permite interação entre o jogador e tanks de experiência. O servidor deve liquidar o saldo final; UI/animation não pode autorizar retirada/deposito duplicado por retry.

## 4. Mechanical Grindstone
Mechanical Grindstone automatiza grindstone e inclui função adicional de sanding conforme recipes registrados. Disenchant output/XP deve ser decidido uma única vez.

## 5. Blaze Enchanter e Blaze Forger
A linha 2.x usa Blaze Enchanter como enchanting table inteligente e Blaze Forger como anvil-like automation. Input, cost e output precisam respeitar o enchant/item state real; outro automation mod não deve executar o mesmo craft em paralelo.

## 6. Printer
Printer pode copiar conteúdo suportado, incluindo written books, enchanted books, name tags, train schedules e outros tipos documentados. A cópia deve respeitar recipe/material cost; não usar como cloning genérico de NBT fora do contract.

## 7. Experience Lantern
Experience Lantern absorve XP e pode operar em contraptions. XP ownership precisa sobreviver a assembly/disassembly sem existir simultaneamente no world e no mounted state.

## 8. Deployer/Crushing Wheel tweaks
Kills por Deployer/Crushing Wheel podem gerar Experience Nuggets. Combat/death attribution deve settlement exatamente uma vez e coexistir com player-kill/loot hooks de outros mods.

## 9. Hyper-enchanting
O addon oferece mecanismo de **hyper-enchanting** capaz de ultrapassar caps normais quando as condições do sistema são satisfeitas. Essa é uma superfície de balanceamento crítica com Apothic Enchanting/attributes.

Não aumentar caps por outro script sem decidir qual provider é authority.

## 10. Mending on belt
Itens com Mending podem ser reparados em fluxo de belt usando Spout + Liquid Experience. O mesmo ponto de durability não pode ser reparado e cobrado por dois listeners simultâneos.

## 11. Apotheosis / Apothic Enchanting
A linha atual inclui integração com o ecossistema Apotheosis, com funcionalidades como Bulk Salvaging, Brass/Crafting Bookshelves, Infuser, Gem Cutter e Affix Augmentor conforme a documentação do projeto.

A build **2.5.3b** é especificamente um hotfix para crash do **Infuser** com Apothic Enchanting 1.6.1+.

## 12. conditional-mixin embedded
O JAR contém `conditional-mixin-neoforge-0.6.4.jar` em `/META-INF/jarjar/`. É library interna usada para compat condicional e não constitui top-level da modlist.

## 13. Recipe/data lifecycle
CEI cria/converte recipes e integrações conforme mods presentes. Datapack/recipe reload deve reconstruir caches sem duplicar categories ou recipes e sem manter referência a enchant/affix registry antigo.

## 14. Client/server
XP balance, fluid amounts, enchanting costs, repair, machine inventories e outputs são server-authoritative. JEI/Ponder/screens/render são client-facing.

## 15. Riscos
1. Conversão XP↔fluid não conservar saldo.
2. Disenchant/recovery de XP duplicado.
3. Hyper-enchant aplicar nível além da política esperada.
4. Mending consumir XP duas vezes.
5. Apotheosis salvage/affix operation duplicada.
6. Infuser crash/regression com Apothic Enchanting.
7. Recipe caches duplicados após reload.
8. Experience Lantern duplicar state em contraption.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10 e stack enchant atual.
2. Player XP→Liquid Experience→player e conservation check.
3. Mechanical Grindstone/disenchant exatamente uma vez.
4. Blaze Enchanter/Forger em recipes normais e inválidos.
5. Printer com cada tipo suportado selecionado.
6. Experience Lantern estacionária e em contraption.
7. Mending on belt: durability e XP debit únicos.
8. Hyper-enchant com caps/Apothic Enchanting.
9. Infuser com Apothic Enchanting 1.6.1+ — regression 2.5.3b.
10. Datapack/recipe reload e restart.

## 17. Evidência
- modlist física 08/09/2026: CEI 2.5.3b;
- CurseForge oficial: build Beta para Create 1.21.1-6.0.10;
- documentação oficial: Liquid Experience, Hatch, Grindstone, Enchanter, Forger, Printer, Lantern, hyper-enchanting, Mending e Apotheosis integration;
- changelog 2.5.3b: fix de crash do Infuser com Apothic Enchanting 1.6.1+.

> 🔒 Boundary canônico: **CEI controla a industrialização de XP/enchanting que ele adiciona**; Create executa a automação, e os providers de enchant/affix continuam decidindo a semântica dos encantamentos.
