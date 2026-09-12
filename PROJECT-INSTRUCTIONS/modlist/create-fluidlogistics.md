# Create FluidLogistics — 1.2.9

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d569db9f0db81968bc3fabc54343567  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create FluidLogistics
- **Arquivo JAR:** `fluidlogistics-1.2.9.jar`
- **Versão 1.21.1:** `1.2.9`
- **Categoria:** Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic
- **Função:** Expande o sistema logístico do Create 6 para fluidos: empacotamento, endereçamento, transporte, armazenamento multi-fluido, requesters, hatches, pumps e ferramentas auxiliares.
- **Dependências:** Create 6.0.10 no pack. A build 1.2.9 é addon Client & Server para o sistema logístico do Create; nenhuma hard dependency adicional é afirmada sem metadata do JAR/source exato.
- **Compatibilidade/Riscos:** Build física 1.2.9 é Beta para NeoForge 1.21.1. Riscos principais: roteamento/endereço incorreto de fluid packages, duplicação/perda em packager↔unpackager/transporter, multi-fluid state stale, automação infinita indevida e drift com Create 6.0.10. Testar requesters, tanks, schematics e unload/restart.
- **Sobreposição:** Complementa a logística nativa do Create com fluid packages e infraestrutura multi-fluido. Pode se cruzar com outros addons de logística/fluidos, mas não é redundância automática; comparar por rota/handler concreto.
- **Observações:** JAR físico `fluidlogistics-1.2.9.jar`, mod id `fluidlogistics`, runtime 1.2.9. A página do projeto lista 1.2.0 como Release principal, mas publica 1.2.9 como Beta mais recente para 1.21.1; o runtime físico prevalece.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create: FluidLogistics 1.2.9/1.2.0 + catálogo de itens e dicas oficiais.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — ficha mínima de reconciliação substituída por dossiê técnico da build física 1.2.9; fluid logistics, package addressing, Create 6, lifecycle e riscos catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 💧 **ESCOPO CANÔNICO.** Runtime físico: `fluidlogistics-1.2.9.jar`, mod id `fluidlogistics`, versão `1.2.9`, NeoForge 1.21.1. Create: FluidLogistics estende a logística do Create 6 para transporte e endereçamento de **fluidos**. A build instalada é Beta; não assumir estabilidade equivalente à Release 1.2.0.

## 1. Superfície funcional confirmada
A documentação oficial lista Fluid Package, Fluid Packager/Unpackager, Fluid Transporter/Smart Hopper, Fluid Factory Gauge, Copper Frogport/Phantom Chain, Faucet/Smart Faucet, Fluid Hatch, Multi-Fluid Access Port, Multi-Fluid Tanks horizontal/vertical, Fluid Pump, Infinite Fluid Tank, Copper Bucket/Basin, Mechanical Fluid Gun, Fluid Schematic/Copper Schematicannon e utilitários associados.

## 2. Logística e endereçamento
O princípio do addon é transportar fluido dentro da infraestrutura de package/address do Create, em vez de tratar todo fluxo como pipe direto. O Redstone Requester pode solicitar fluido a partir de um item que o contenha; a documentação também permite usar Clipboard para definir endereço em packager/repackager.

Integrações próprias devem tratar endereço, quantidade e conteúdo do package como state autoritativo do provider; não inferir entrega concluída apenas por animação ou saída visual.

## 3. Armazenamento multi-fluido
Multi-Fluid Tank e Multi-Fluid Access Port ampliam a superfície de storage. Pontos de QA: capacidade, seleção do fluido, inserção/extração parcial, vizinhos com múltiplos handlers, unload/reload e persistência NBT/components.

Infinite Fluid Tank existe no conteúdo do mod; sua presença não significa que qualquer fluido seja automaticamente infinito. Receitas/configuração efetivas continuam authority do runtime/datapack.

## 4. Create 6 e automação
O pack usa Create 6.0.10. A integração precisa ser testada com packagers, gauges, package routing, schematics e contraptions atuais. Outros addons de fluidos/logística podem tocar os mesmos handlers, mas isso é overlap de processo, não motivo suficiente para remoção.

## 5. Client/server e lifecycle
O projeto é Client & Server. O servidor deve validar conteúdo/quantidade/endereço e mutações de tank/package; o cliente representa GUI, modelos e feedback. Validar criação de mundo, dedicated server boot, chunk unload, restart, reconexão e movimentação de contraptions quando um bloco compatível participar delas.

## 6. Riscos
1. **Dupe/loss:** package consumido e fluido inserido em ordens divergentes.
2. **Address mismatch:** request roteado para destino errado ou loop de entrega.
3. **Multi-fluid stale state:** GUI/handler mostra fluido diferente do server state.
4. **Infinite route:** tanque/receita/config cria fonte ilimitada não pretendida.
5. **Create drift:** mudanças em logística do Create 6 quebram integração do addon.
6. **Beta regressions:** 1.2.9 não deve ser tratada como Release estável apenas por ser a mais recente.

## 7. Boundary para quests/perks
Entregar um Fluid Package só deve contar como milestone se houver evento/state causal deduplicável. Não conceder progresso por tick de tank, movimento visual do package ou polling de GUI. Produção e consumo de fluido permanecem authorities dos respectivos providers.

## 8. Matriz de testes
- [ ] Dedicated server inicia com FluidLogistics 1.2.9 + Create 6.0.10.
- [ ] Packager→transport→unpackager conserva tipo e quantidade exatamente uma vez.
- [ ] Redstone Requester solicita o fluido correto e não cria loop.
- [ ] Clipboard altera endereço e persiste após restart.
- [ ] Multi-Fluid Tank mantém contents após unload/restart.
- [ ] Hatches/pumps respeitam handlers modded sem dupe/loss.
- [ ] Schematics não duplicam conteúdo/state de inventories ou tanks.
- [ ] Multiplayer simultâneo converge para um único state server-authoritative.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limitação
- Modlist física: `fluidlogistics-1.2.9.jar`, mod id `fluidlogistics`, runtime 1.2.9.
- CurseForge oficial: addon de fluid logistics para Create 6; build 1.2.9 Beta para NeoForge 1.21.1 e catálogo funcional acima.
- Não foram decompilados handlers, packet IDs ou schemas internos da 1.2.9; hooks programáticos exigem inspeção do JAR/source antes de depender de internals.