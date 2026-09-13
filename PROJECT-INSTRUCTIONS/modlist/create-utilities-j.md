# Create Utilities J

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8121b4e7d2b6178aa921
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Utilities J
- **Arquivo JAR:** `Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar`
- **Versão 1.21.1:** 0.3.4+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Continuação mantida de Create Utilities que adiciona utilidades cinéticas e redes Void para rotação, itens, fluidos e energia, além de Gearcube/L-shaped Gearbox e materiais/decorativos próprios, integrada diretamente às APIs de Create/NeoForge.
- **Dependências:** Metadata/source 1.21.1 requer Minecraft 1.21.1, NeoForge, Create 6.0.0+ e Ponder. O branch da release compila contra Create 6.0.10; o pack físico possui Create 6.0.10. Mod é Client & Server.
- **Sobreposição:** É a continuação JSI instalada do Create Utilities. O port não oficial `createutilities-0.3.7+mc1.21.1.jar` foi removido historicamente; não manter duas implementações com o mesmo domínio/mod id. Create continua authority da kinetic network base; Utilities J estende-a com seus Void Links/storage próprios.
- **Compatibilidade/Riscos:** Riscos principais: frequency/network key collision, stale Void Link state, cross-dimension kinetic propagation, shared persistent storage de item/fluid/FE, capability invalidation, serialization de ItemStack vazio e packet desync. A 0.3.4 migra networking para NeoForge payload API, saved data/capabilities e corrige crash de frequency serialization com ItemStack vazio.
- **Observações:** mod id `createutilities`; runtime 0.3.4+1.21.1. Branch oficial 1.21.1 registra `void_motor`, `void_chest`, `void_tank`, `void_battery`, `gearcube`, `lshaped_gearbox`, Void Steel/decorativos e items `void_steel_ingot`, `void_steel_sheet`, `polished_amethyst`, `graviton_tube`. 0.3.4: port NeoForge 1.21.1, payload API, saved data/capability migration, creative tab restaurada e fix de empty ItemStack frequency serialization.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level (NeoForge + 594 JARs top-level) + runtime `createutilities` 0.3.4+1.21.1 + CurseForge oficial da release 0.3.4+1.21.1 + branch GitHub oficial `1.21.1` da JSI Team para registries, capabilities, persistence, networking e metadata de dependências.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-utilities-j
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio reconstruído; Void Motor/Chest/Tank/Battery networks, Void Link/frequency state, Gearcube/L-shaped Gearbox, NeoForge capabilities, persistence/networking e delta 0.3.4+1.21.1 catalogados.
- **Histórico da decisão:** Este é o port/continuação mantido no pack. O outro JAR `createutilities-0.3.7+mc1.21.1.jar` foi removido em 15/08 para evitar manter duas implementações do mesmo mod. Create Utilities J segue presente nas modlists posteriores.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> 🕳️ Versão física confirmada: `Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar`, mod id `createutilities`, runtime `0.3.4+1.21.1`, NeoForge 1.21.1. Esta é a **continuação JSI instalada** de Create Utilities; o port não oficial alternativo não deve coexistir como segunda implementação do mesmo domínio.

## 1. Papel e authority
Create Utilities J estende Create com uma camada de **Void Links** e utilidades cinéticas/storage. O addon controla seus próprios links, storages persistentes, materiais e blocos; **Create 6.0.10** continua authority da kinetic network/stress e das primitives que Utilities J reutiliza.
O source oficial do branch 1.21.1 registra explicitamente Void Motor, Void Chest, Void Tank, Void Battery, Gearcube, L-shaped Gearbox e conteúdo Void Steel/decorativo.

## 2. Dependências e runtime
A metadata oficial do branch 1.21.1 declara:
- Minecraft 1.21.1;
- NeoForge;
- Create `6.0.0+`;
- Ponder.
O build da linha 1.21.1 compila contra **Create 6.0.10**, versão que também está fisicamente instalada no pack. A distribuição é Client & Server.

## 3. Modelo de Void Link
Os blocos Void usam `VoidLinkBehaviour` com três slots de configuração. O source define os slots 0 e 1 como **frequencies** e o slot 2 como **owner**.
Interação server-side permite definir cada frequency com o ItemStack segurado pelo jogador e alternar ownership no terceiro slot. Assim, o par de frequencies + owner participa da identidade/isolamento da rede; integrações não devem criar um segundo keying paralelo.

## 4. Frequencies e ItemStack
As frequencies são configuradas por ItemStack, não por texto livre. A release 0.3.4 corrige especificamente **crash de serialização quando uma frequency continha ItemStack vazio**.
Regression gate: limpar/substituir uma frequency precisa produzir state válido, persistível e comparável sem crash nem associação acidental com outra rede.

## 5. Ownership
O terceiro Void Link slot alterna owner usando o `GameProfile` do jogador. O handler verifica `canInteract(player)` antes de alterar state.
Ownership precisa permanecer server-authoritative e persistir corretamente entre reconnect/restart. Não usar apenas render da cabeça/slot como prova de autorização.

## 6. Void Motor
`VoidMotorTileEntity` é um `KineticBlockEntity`. Ele adiciona posições da sua Void network às propagation locations e só propaga rotação para outro Void Motor quando os network keys coincidem.
Ao conectar à rede, marca update de speed; ao desconectar, detach kinetics/remove source. Portanto o Void Motor é uma **ponte cinética remota**, mas Create continua decidindo a rotação final da kinetic network.

## 7. Cross-dimension e rede cinética
Como o Void Motor abstrai links por network key em vez de depender apenas de adjacência física, cross-dimension/network lifecycle é superfície de risco central.
Não assumir funcionamento entre dimensões apenas por conceito; smoke-test deve confirmar a build física. Em unload/restart, uma ponta ausente não pode deixar source fantasma nem velocidade stale.

## 8. Void Chest
`VoidChestTileEntity` resolve seu storage server-side em `VoidChestInventoriesData.computeStorageIfAbsent(link.getNetworkKey())`. Isso significa que blocos com a mesma key consultam o **mesmo storage persistente lógico**.
O bloco registra `Capabilities.ItemHandler.BLOCK` e também um `MountedItemStorageType`, portanto precisa ser validado tanto estacionário quanto montado em contraptions Create.

## 9. Void Tank
`VoidTankTileEntity` usa `VoidTanksData` server-side e storage cliente espelhado para apresentação. O bloco expõe `Capabilities.FluidHandler.BLOCK` e fornece tooltip via Create goggles.
Transferência de fluido deve ocorrer uma vez no storage compartilhado; dois endpoints da mesma key não representam dois tanques independentes para contabilização econômica.

## 10. Void Battery
`VoidBatteryTileEntity` resolve a bateria por network key através de `VoidBatteryData` e expõe `Capabilities.EnergyStorage.BLOCK`. Goggles mostram energia armazenada e capacidade.
A authority do saldo FE está no storage lógico da rede. Bridges elétricas não devem somar energia de cada bloco visual como se fossem baterias físicas independentes quando compartilham a mesma key.

## 11. NeoForge capabilities — 0.3.4
A classe principal registra explicitamente capabilities NeoForge para:
- Item Handler → Void Chest;
- Fluid Handler → Void Tank;
- Energy Storage → Void Battery.
A release 0.3.4 também registra migração de **capability registration** para o runtime 1.21.1. Addons externos devem consumir essas capabilities atuais em vez de adapters de Forge antigo/NBT paralelo.

## 12. Persistent SavedData
A classe principal mantém `VoidChestInventoriesData`, `VoidTanksData` e `VoidBatteryData`, e a release 0.3.4 declara migração de **saved data** para 1.21.1.
Esses dados são server-authoritative e precisam sobreviver restart sem duplicar storage ou perder o vínculo frequency/owner. Mudança de format é migration surface crítica em update futuro.

## 13. Networking — 0.3.4
O changelog oficial declara migração do networking para a **NeoForge payload API**. No source 1.21.1, `CUPackets` registra packets e há payloads dedicados de update para Void Tank e Void Battery.
Packet é sync/intent, não authority do saldo. Reenvio, atraso ou ordem diferente não pode aplicar uma segunda mutação econômica no storage persistente.

## 14. Gearcube
`GearcubeBlock` é um `KineticBlock` que expõe shaft para todas as faces e usa `GearboxBlockEntity`. É uma utility de distribuição cinética compacta.
A rotação continua resolvida pelo Create; Gearcube não cria um orçamento de stress separado.

## 15. L-shaped Gearbox
O source registra `lshaped_gearbox` com connected textures/casing connectivity do Create. É uma variação de routing cinético do addon.
Compatibilidade deve ser testada com shafts/casings e contraptions, especialmente após updates do Create que alterem propagation/visual API.

## 16. Registries de blocos
O branch 1.21.1 registra, entre outros:
- `void_steel_block`;
- `void_steel_scaffolding`;
- `void_steel_ladder`;
- `void_steel_bars`;
- `void_casing`;
- `void_motor`;
- `void_chest`;
- `void_tank`;
- `void_battery`;
- `gearcube`;
- `lshaped_gearbox`;
- `amethyst_tiles`;
- `small_amethyst_tiles`.
Esse inventário vem do source oficial 1.21.1; recipes/tags finais continuam dependentes dos data files da build.

## 17. Registries de items
`CUItems` registra explicitamente:
- `void_steel_ingot`;
- `void_steel_sheet`;
- `polished_amethyst`;
- `graviton_tube`.
Não inferir stats, recipe costs ou usos adicionais além do que os recipes/runtime confirmarem.

## 18. Creative tab e Ponder
A release 0.3.4 **restaura a creative tab do Create Utilities J**. O source também registra componentes Ponder para suas utilities.
Creative tab/Ponder são discovery/documentation surfaces; não controlam storage, frequency ou kinetic state.

## 19. Mixins
Embora o JAR físico declare `createutilitiesj.mixins.json`, o arquivo do branch 1.21.1 contém arrays de mixins/client vazios. Portanto esta build não deve ser descrita como dependente de patches invasivos específicos apenas pela presença do config file.
Isso reduz uma classe de conflitos, mas não elimina API drift com Create/NeoForge.

## 20. Relação com o port não oficial
O catálogo possui histórico de outro port de Create Utilities, mas o pack atual mantém **Create Utilities J 0.3.4+1.21.1**. O antigo `createutilities-0.3.7+mc1.21.1.jar` foi removido anteriormente.
Como ambos cobrem o mesmo domínio e podem compartilhar mod id/registries conceitualmente equivalentes, não reinstalar o outro port em paralelo sem uma análise explícita de IDs e save compatibility.

## 21. Client/server e multiplayer
Persistent storage, frequency/owner, capabilities e kinetic connections são common/server-authoritative. Rendering de Void Links, goggles, screens e animations são client-facing.
Dois jogadores alterando frequencies/owner ou acessando o mesmo Void Chest/Tank/Battery devem convergir para um único state server-side.

## 22. Lifecycle
Validar:
- placement/removal de cada Void block;
- definição/limpeza de frequencies;
- toggle de owner;
- chunk unload/reload;
- dimension travel;
- server restart;
- Create contraption assembly/disassembly com Void Chest;
- capability invalidation/re-resolution;
- client reconnect;
- mudança de frequency enquanto endpoints existem;
- update futuro de saved data/network protocol.

## 23. Riscos técnicos
1. ItemStack vazio em frequency causar serialization crash — regression 0.3.4.
2. Duas networks diferentes colidirem por key/frequency incorreta.
3. Ownership stale permitir ou bloquear interação indevidamente.
4. Void Motor manter source/speed fantasma após unload.
5. Void Chest duplicar inventory entre SavedData e block state.
6. Void Tank creditar/debitar fluido duas vezes via endpoints compartilhados.
7. Void Battery ser contabilizada por bloco em vez de por storage lógico.
8. Capability antiga continuar referenciada após block removal/assembly.
9. Packet update divergir do persistent state.
10. Port alternativo ser reinstalado e gerar conflito de mod id/registries/save.

## 24. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + Utilities J 0.3.4.
2. Definir duas frequencies e owner; restart preserva key/state.
3. Limpar uma frequency para ItemStack vazio sem crash.
4. Dois Void Motors mesma key: rotação propagada; key diferente: isolada.
5. Void Motor com unload/reload e teste cross-dimension controlado.
6. Dois Void Chests mesma key: inventory único, sem dupe sob acesso concorrente.
7. Void Chest montado em contraption e desmontado sem clone/loss.
8. Dois Void Tanks mesma key: saldo de fluido único e capability correta.
9. Dois Void Batteries mesma key: FE único e goggles coerentes.
10. Capability automation externa de item/fluid/FE.
11. Reconnect de dois clientes e mudança concorrente de owner/frequency.
12. Gearcube e L-shaped Gearbox em network com stress/overstress normal do Create.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 25. Evidência
- modlist física atual: `Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar`, mod id `createutilities`, runtime `0.3.4+1.21.1`;
- CurseForge oficial: continuação mantida, Client & Server, release 0.3.4+1.21.1 e changelog de port/networking/saved data/capabilities/creative tab/serialization;
- source oficial branch `1.21.1`: registries, `VoidLinkSlot`, `VoidLinkHandler`, Void Motor, Void Chest/Tank/Battery, NeoForge capabilities, networking e dependency metadata;
- source 1.21.1 requer Create 6.0.0+ e builda contra Create 6.0.10.

> 🔒 Boundary canônico: **Utilities J controla Void Links e storages compartilhados; Create continua controlando a kinetic network base; NeoForge capabilities são a interface de item/fluid/energia**. Frequencies, owner e saldos persistentes devem existir em uma única authority server-side.
