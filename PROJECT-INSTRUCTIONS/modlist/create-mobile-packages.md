# Create: Mobile Packages

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fdb133df39034a0381
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Mobile Packages
- **Arquivo JAR:** `create_mobile_packages-1.21.1-0.7.7.jar`
- **Versão 1.21.1:** 0.7.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia, QoL
- **Função:** Estende o High Logistics do Create com Logistics Networks próprias, Bee Ports/Robo Bees, Portable Stock Ticker, Mobile Packager e entrega/retirada móvel de packages diretamente ao jogador.
- **Dependências:** NeoForge 21.1.206+ e Create 6.0.9+ para a linha 1.21.1; o pack usa Create 6.0.10. `create_factory_abstractions-1.21.1-1.6.0.jar` está embarcado via jar-in-jar no host e não é top-level.
- **Sobreposição:** Extende High Logistics/Create packages com mobilidade e redes próprias. Create continua authority das packages/stock primitives; Mobile Packages controla Bee Ports, Robo Bees, membership e ferramentas móveis.
- **Compatibilidade/Riscos:** Stateful logistics/network mod. Riscos: membership/ownership, packages duplicados, Robo Bee state, chunk unload, remote requests, trash-slot delivery, BeePort filters e Factory Abstractions version drift. 0.7.7 corrige categories do Portable Stock Ticker após restart e sync chunk load no BeePort unload.
- **Observações:** mod id `create_mobile_packages`; runtime 0.7.7. Recursos oficiais: Logistics Network, Bee Port/Robo Bee, Portable Stock Ticker, Mobile Packager e comandos `/cmp`. 0.7.7 atualiza embedded Factory Abstractions para 1.6.0 e adiciona FilterMode ao BeePort.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_mobile_packages` 0.7.7 + CurseForge/Modrinth oficiais da release 0.7.7 e descrição técnica do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-mobile-packages/files/8502329
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — logistics-network authority, Bee Port/Robo Bee, Portable Stock Ticker, Mobile Packager, membership/security, admin commands, lifecycle e regressões 0.7.7 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Mobile Packages 0.7.7 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A presença no stack logístico não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 📦 Versão física confirmada: `create_mobile_packages-1.21.1-0.7.7.jar`, mod id `create_mobile_packages`, runtime `0.7.7`. O addon amplia **Create High Logistics** com redes, Bee Ports/Robo Bees e ferramentas móveis de request/package management.

## 1. Papel e authority
Create: Mobile Packages fornece uma camada móvel em cima do sistema de packages/stock do Create. **Create continua authority das packages e stock primitives**; Mobile Packages controla Logistics Networks, Bee Ports, Robo Bees, membership e seus dispositivos móveis.

## 2. Logistics Network
O núcleo do addon é uma **Logistics Network**. Bee Ports pertencem a uma rede, players podem participar e as entregas são limitadas ao contexto dessa rede.
Network ID, owner e membership são state persistente e devem ser tratados server-side; UI é apenas controle/apresentação.

## 3. Bee Port
Bee Ports ligam o mundo físico à rede logística. Um port novo pode criar ou integrar rede conforme fluxo do mod. A 0.7.7 passa a mostrar nome do BeePort quando possível em vez de apenas coordenadas.

## 4. Robo Bee
Robo Bees transportam packages/itens entre ports e players da mesma rede. O bee é um carrier, não uma segunda cópia do inventory source: retirada deve comprometer o item uma vez, e entrega deve finalizar uma vez.
Chunk unload ou target indisponível não pode materializar cópia no destino mantendo o original na origem.

## 5. Portable Stock Ticker
O **Portable Stock Ticker** permite solicitar itens remotamente da Create Logistics Network. O projeto documenta também envio de itens por Trash Slots para endereço específico via Robo Bee, crafting dentro da interface e sync de busca com JEI.
A 0.7.7 corrige perda das categories do ticker após restart/hotkey, tornando persistência/reconnect um regression gate.

## 6. Mobile Packager
O **Mobile Packager** permite empacotar até 9 stacks em package endereçado e editar conteúdo de package existente via Sneak + Use. Editar package precisa preservar atomicidade: conteúdo removido/adicionado não pode existir simultaneamente no package e no inventário do jogador.

## 7. Membership e ownership
A 0.7.7 corrige owners para serem members da própria network por padrão e melhora screens de membership/connection. Membership controla acesso operacional; não deve ser inferido apenas porque um player conhece UUID/endereço da rede.

## 8. BeePort FilterMode
A 0.7.7 adiciona **FilterMode** ao BeePort para controlar o que hoppers, chutes, funnels etc. podem inserir/exportar: todos os itens, apenas packages ou apenas bees conforme opção.
Automação Create externa deve respeitar o filtro server-side, sem contornar via capability paralela.

## 9. Chunk lifecycle
A 0.7.7 corrige `BeePortBlockEntity.onChunkUnloaded()` que carregava chunk sincronicamente. Isso torna unload/reload um regression gate de performance e correctness.
Nenhum logistics tick deve reabrir chunk descarregado apenas para manter referência stale.

## 10. Factory Abstractions embedded
O JAR hospeda `create_factory_abstractions-1.21.1-1.6.0.jar` como componente embedded. A 0.7.7 atualiza essa cópia para resolver incompatibilidade documentada.
Pela regra canônica, não criar top-level separado para essa library embarcada.

## 11. Admin commands
O projeto documenta comandos `/cmp` para listar redes, adicionar/remover members, limpar Robo Bees e, na 0.7.7, renomear network. Esses comandos são administração server-side e precisam de permission checks adequados.

## 12. Create integration
Stock Links, packages, funnels/chutes e demais primitives Create continuam sob authority do Create. Mobile Packages referencia/transporta esses objetos; não deve recalcular stock como inventário próprio independente.

## 13. Client/server e multiplayer
Network ownership, membership, package contents, stock requests e delivery são server-authoritative. Screens, synchronized search e bee rendering são client-facing.
Dois players pedindo o mesmo estoque concorrentemente precisam receber settlement coerente com a disponibilidade real.

## 14. Lifecycle
Validar create/join/leave network, port placement/removal, chunk unload, Robo Bee em trânsito, player disconnect, restart, dimension transitions permitidas, package edit e permission changes.

## 15. Riscos
1. Package/item duplicado entre source e carrier.
2. Robo Bee órfão após chunk unload.
3. Network membership/owner perdido no restart.
4. Remote request exceder estoque por concorrência.
5. Trash Slots enviarem item ao endereço errado.
6. BeePort FilterMode ser ignorado por automation.
7. Categories do Portable Stock Ticker sumirem — regression 0.7.7.
8. Embedded Factory Abstractions ser tratado como top-level.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Criar network e adicionar/remover segundo player.
3. Bee Port + Robo Bee: delivery e pickup exatamente uma vez.
4. Portable Stock Ticker: request, search e crafting.
5. Trash Slot → endereço específico.
6. Restart preservando ticker categories e network membership.
7. Mobile Packager: criar e editar package sem dupe.
8. BeePort FilterMode com hopper/chute/funnel.
9. Chunk unload enquanto Robo Bee/Port ativos.
10. Dois players concorrendo pelo mesmo estoque.
11. `/cmp` com permissions/admin.

## 17. Evidência
- modlist física 08/09/2026: Mobile Packages 0.7.7;
- CurseForge/Modrinth oficiais: Create 6.0.9+, NeoForge 21.1.206+, Logistics Network, Bee Ports, Robo Bees, Portable Stock Ticker e Mobile Packager;
- changelog 0.7.7: ticker categories persistence, BeePort unload fix, owner membership, screens, FilterMode, rename command e Factory Abstractions 1.6.0.

> 🔒 Boundary canônico: **Create mantém o estoque/package base; Mobile Packages controla rede, acesso e transporte móvel**. Item algum pode existir simultaneamente em origem, carrier e destino.