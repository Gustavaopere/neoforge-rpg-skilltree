# Create FluidLogistics

## Propriedades do registro

- **Mod:** Create FluidLogistics
- **Arquivo JAR:** `fluidlogistics-1.3.0-mc1.21.1.jar`
- **Versão 1.21.1:** `1.3.0`
- **Categoria:** Tecnologia, Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic
- **Função:** Expande o sistema logístico do Create 6 para fluidos: empacotamento, endereçamento, transporte, armazenamento multi-fluido, requesters, hatches, pumps e ferramentas auxiliares.
- **Dependências:** Create 6.0.10 no pack. A build 1.3.0 é addon Client & Server para o sistema logístico do Create; nenhuma hard dependency adicional é afirmada sem metadata do JAR/source exato.
- **Compatibilidade/Riscos:** Build física 1.3.0 é Release para NeoForge 1.21.1. Riscos principais: roteamento/endereço incorreto de fluid packages, duplicação/perda em packager↔unpackager/transporter, multi-fluid state stale, automação infinita indevida, paralelismo do Mechanical Fluid Gun e drift com Create 6.0.10. Testar requesters, tanks, schematics, bulk cooling e unload/restart.
- **Sobreposição:** Complementa a logística nativa do Create com fluid packages e infraestrutura multi-fluido. Pode se cruzar com outros addons de logística/fluidos, mas não é redundância automática; comparar por rota/handler concreto.
- **Observações:** JAR físico `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, runtime 1.3.0. Release oficial NeoForge 1.21.1 de 16/09/2026. Delta 1.3.0: optimize package fluid rendering; Mechanical Fluid Gun processa itens em paralelo; fix do disable blaze cooler conversion; fix de empty bucket extra no bulk cooling.
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, runtime `1.3.0` e SHA-1 `011435bbefe9ab979719fa138252d52261837d33`. A versão física não mudou nesta rodada; as evidências técnicas já registradas permanecem preservadas.
- **Histórico da decisão:** 2026-09-18 — runtime físico atualizado para 1.3.0; nenhuma decisão curatorial nova.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #285: Create FluidLogistics 1.3.0 reconfirmado; nenhuma mudança de versão física nesta rodada.

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #285: JAR `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, runtime `1.3.0`, SHA-1 `011435bbefe9ab979719fa138252d52261837d33`.

<callout icon="💧" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, versão `1.3.0`, NeoForge 1.21.1. Create: FluidLogistics estende a logística do Create 6 para transporte e endereçamento de **fluidos**. A build instalada é Release oficial publicada em 16/09/2026.
</callout>

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

### Delta 1.3.0

A release física 1.3.0 registra quatro mudanças oficiais: otimização do rendering de package fluid; processamento paralelo de itens no Mechanical Fluid Gun; correção do toggle que desabilita blaze cooler conversion; e correção de bulk cooling que produzia um empty bucket extra. Esses quatro pontos passam a ser regression gates explícitos desta versão.

## 5. Client/server e lifecycle

O projeto é Client & Server. O servidor deve validar conteúdo/quantidade/endereço e mutações de tank/package; o cliente representa GUI, modelos e feedback. Validar criação de mundo, dedicated server boot, chunk unload, restart, reconexão e movimentação de contraptions quando um bloco compatível participar delas.

## 6. Riscos

1. **Dupe/loss:** package consumido e fluido inserido em ordens divergentes.
2. **Address mismatch:** request roteado para destino errado ou loop de entrega.
3. **Multi-fluid stale state:** GUI/handler mostra fluido diferente do server state.
4. **Infinite route:** tanque/receita/config cria fonte ilimitada não pretendida.
5. **Create drift:** mudanças em logística do Create 6 quebram integração do addon.
6. **Parallel-processing regression:** o Mechanical Fluid Gun não pode processar o mesmo item duas vezes nem divergir do state server-authoritative.
7. **Cooling/container conservation:** blaze cooler conversion e bulk cooling não podem ignorar config nem criar bucket extra.

## 7. Boundary para quests/perks

Entregar um Fluid Package só deve contar como milestone se houver evento/state causal deduplicável. Não conceder progresso por tick de tank, movimento visual do package ou polling de GUI. Produção e consumo de fluido permanecem authorities dos respectivos providers.

## 8. Matriz de testes

- [ ] Dedicated server inicia com FluidLogistics 1.3.0 + Create 6.0.10.
- [ ] Packager→transport→unpackager conserva tipo e quantidade exatamente uma vez.
- [ ] Redstone Requester solicita o fluido correto e não cria loop.
- [ ] Clipboard altera endereço e persiste após restart.
- [ ] Multi-Fluid Tank mantém contents após unload/restart.
- [ ] Hatches/pumps respeitam handlers modded sem dupe/loss.
- [ ] Schematics não duplicam conteúdo/state de inventories ou tanks.
- [ ] Mechanical Fluid Gun processa itens em paralelo sem double-consume/double-output.
- [ ] Desabilitar blaze cooler conversion é respeitado no runtime.
- [ ] Bulk cooling conserva buckets exatamente, sem empty bucket extra.
- [ ] Package-fluid rendering otimizado não diverge do conteúdo real do package.
- [ ] Multiplayer simultâneo converge para um único state server-authoritative.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limitação

- Modlist física de 18/09/2026: `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, runtime 1.3.0.
- CurseForge oficial: release 1.3.0 para NeoForge 1.21.1 de 16/09/2026, com os quatro deltas documentados acima.
- Não foram decompilados handlers, packet IDs ou schemas internos da 1.3.0; hooks programáticos exigem inspeção do JAR/source antes de depender de internals.
