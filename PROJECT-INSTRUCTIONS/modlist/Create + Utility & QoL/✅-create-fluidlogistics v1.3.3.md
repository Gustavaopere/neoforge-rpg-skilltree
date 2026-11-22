# Create: FluidLogistics — 1.3.3 (instalação declarada em 22/11/2026)

> **Versão vigente declarada pelo usuário:** 1.3.0 → **1.3.3**, arquivo oficial `fluidlogistics-1.3.3-mc1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9712748)). O novo JAR local e seu metadata/SHA não foram inspecionados nesta sessão.

> **Deltas da cadeia de publicações:** 1.3.1 e 1.3.2 estão auditadas; 1.3.3 permite eixos compartilhados por Oscillating Steam Engines, atualiza flywheel do Snow Golem Freezer, corrige repackaging conjunto de fluidos+itens, filtragem de fluid packages, áudio de Stock Keeper e drops de filtros dos Multi-Fluid Access Ports.

> **Dependências pendentes:** Create 6.0.10 na baseline do modpack; outros addons de logística e seus requisitos permanecem a conciliar; sem hard minimum novo inventado.

> **Compatibilidade e QA:** Preservação de volumes, repackaging e filtros, energia cinética, estoque, sync, unload/restart e multiplayer ainda não testados.

> **Leitura do histórico:** referências antigas a versão 'física atual', 'instalada' ou 'não instalada' abaixo descrevem o snapshot e a auditoria anterior, não o estado documental declarado agora. A posição física 285 e o JAR antigo `fluidlogistics-1.3.0-mc1.21.1.jar` são mantidos como rastreabilidade.


## Propriedades do registro

- **Mod:** Create FluidLogistics
- **Arquivo JAR instalado (declarado pelo usuário):** `fluidlogistics-1.3.3-mc1.21.1.jar` — publicado no [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9712748); arquivo local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.3.3` (antes `1.3.0`; metadata da nova build não extraído).
- **Categoria:** Tecnologia, Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** versão-alvo e artefato oficial confirmados; publicações intermediárias documentadas nas seções históricas; binário e QA pendentes.
- **Estado no pack:** `1.3.3` instalado segundo declaração do usuário; documentação sincronizada, sem inspeção local.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic
- **Função:** Expande o sistema logístico do Create 6 para fluidos: empacotamento, endereçamento, transporte, armazenamento multi-fluido, requesters, hatches, pumps e ferramentas auxiliares.
- **Dependências:** Create 6.0.10 na baseline do modpack; outros addons de logística e seus requisitos permanecem a conciliar; sem hard minimum novo inventado.
- **Compatibilidade/Riscos:** Preservação de volumes, repackaging e filtros, energia cinética, estoque, sync, unload/restart e multiplayer ainda não testados.
- **Sobreposição:** Complementa a logística nativa do Create com fluid packages e infraestrutura multi-fluido. Pode se cruzar com outros addons de logística/fluidos, mas não é redundância automática; comparar por rota/handler concreto.
- **Observações vigentes:** arquivo publicado `fluidlogistics-1.3.3-mc1.21.1.jar` declarado instalado, sem SHA local; snapshot 1.3.0 apenas histórico.
- **Procedência:** posição histórica #285, declaração de atualização em 22/11/2026 e [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9712748); nenhum arquivo local novo foi lido.
- **Histórico da decisão:** 2026-09-18 — runtime físico atualizado para 1.3.0; nenhuma decisão curatorial nova.
- **Atualização/Status:** 22/11/2026 — `1.3.0` → `1.3.3`; 1.3.1 e 1.3.2 estão auditadas; 1.3.3 permite eixos compartilhados por Oscillating Steam Engines, atualiza flywheel do Snow Golem Freezer, corrige repackaging conjunto de fluidos+itens, filtragem de fluid packages, áudio de Stock Keeper e drops de filtros dos Multi-Fluid Access Ports.

> **Snapshot físico histórico — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #285: JAR `fluidlogistics-1.3.0-mc1.21.1.jar`, mod id `fluidlogistics`, runtime `1.3.0`, SHA-1 `011435bbefe9ab979719fa138252d52261837d33`.

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

## 10. Revalidação upstream — 02/10/2026

O arquivo físico continua em `fluidlogistics-1.3.0-mc1.21.1.jar` / `1.3.0`. A listagem oficial do CurseForge para Minecraft 1.21.1 continua apontando **1.3.0** como release mais recente (file ID `8894053`), portanto não existe sequência intermediária posterior à versão já documentada para incorporar neste ciclo.

Os quatro deltas da 1.3.0 permanecem os mesmos já registrados no dossiê: otimização do render de fluid packages, processamento paralelo no Mechanical Fluid Gun, correção do toggle de Blaze Cooler conversion e correção do empty bucket extra em bulk cooling. Nenhum teste runtime adicional foi executado nesta revalidação documental.

## Auditoria incremental — 20/11/2026 — 1.3.1 e 1.3.2

**Autoridade física histórica:** #285, `fluidlogistics-1.3.0-mc1.21.1.jar` (1.3.0). O [catálogo oficial CurseForge 1475079](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/all) consultado identifica as releases NeoForge 1.21.1 1.3.1 (30/09/2026) e 1.3.2 (27/10/2026) após 1.3.0 de 16/09. As distribuições Forge 1.20.1 e JARs de sources não são entradas adicionais de runtime. Categorias Create + Utility & QoL preservadas. Projeto distinto de Create: Fluid.

### 1.3.1 — 30/09/2026

O [changelog do commit de release 1.3.1](https://github.com/yision1/CreateFluidLogistics/blob/d85aaa2db5f06610a03cdbb5e6a53b348b188da5/CHANGELOG.md) do mantenedor registra:
- Flow Meter (medidor de fluxo), Redstone Fluid Valve (válvula controlada por redstone) e Potato Server (não inferir função ou receita além do nome).
- Fluid Packagers passam a extrair mel de colmeias.
- Corrige sinal redstone do Stock Link que não reduzia a prioridade de Fluid Packager.

São mudanças relevantes para automação e seleção de destinos; testar conservação de fluido, prioridade sob pedidos concorrentes, extração de mel e operação dos novos componentes. A existência e plataforma da release foram verificadas no catálogo CurseForge; a nota técnica vem do commit de release oficial, não de diff byte a byte do JAR.

### [1.3.2 — 27/10/2026, arquivo 9340866](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9340866)

- Adiciona Oscillating Steam Engine e Package List Filter; introduz Snow Golem Freezer em substituição ao Blaze Cooler. Validar recipes, referências de schematics e comportamento de saves existentes; a nota não especifica a migração de IDs.
- Hand pointer mostra localização de Stock Links em chunks descarregados; isto não implica que force-load seja efetuado.
- Altera armazenamento de fluido em packages, regras de conexão do Copper Frogport e aumenta limite do alvo de reposição do Fluid Factory Gauge. Validar rotas, inserção/extração e migração do estado persistente; não inventar novo teto numérico.
- Ajusta cálculo de TPS do Potato Server, adiciona idiomas e corrige conversão indevida de Fluid Gauge em Item Gauge após primeira colocação, além de loading/sync de configs.

**Gates:** testar upgrades de save/schematic e configurações, conservação de volumes, reabastecimento e prioridades, Frost/Blaze Cooler migration, novos dispositivos, reload/restart, links descarregados e Create 6.0.10 com addons de logística presentes. A documentação das duas releases está reconciliada; não foi determinada equivalência binária dos artefatos nem realizada instalação ou QA. A versão física permanece 1.3.0.

## Auditoria incremental — 21/11/2026 — Create: FluidLogistics 1.3.3 (NeoForge 1.21.1)

**Versão física preservada:** posição #285 no snapshot `modlist(1).txt`, `fluidlogistics-1.3.0-mc1.21.1.jar` / 1.3.0. A cadeia 1.3.1 (30/09) e 1.3.2 (27/10) foi auditada individualmente na seção anterior. A publicação posterior [Create: FluidLogistic 1.3.3 — CurseForge file ID 9712748](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9712748) é **Release de 20/11/2026** para **NeoForge 1.21.1** (arquivo publicado `fluidlogistics-1.3.3-mc1.21.1.jar`). Trata-se do projeto oficial CurseForge 1475079, de yision1, com categorias temáticas **Create + Utility & QoL**, sem mudança da pasta documental.

### Delta oficial, individualizado da 1.3.3
1. **Oscillating Steam Engines:** passam a poder compartilhar um mesmo eixo (*shaft*). Verificar torque/rotação em montagem com múltiplos motores e se o estado dos componentes é preservado após chunk unload/reload. O changelog não estabelece novos números de geração.
2. **Snow Golem Freezer:** recebe renderização de *flywheel*, com impacto de apresentação/representação da máquina; não presumir nova receita ou alteração térmica por causa do renderer.
3. **Repackager:** corrige o comportamento que não combinava *fluid packages* e *item packages* em um mesmo pacote. Importante para logística híbrida e integridade dos pedidos, sobretudo com múltiplos destinos e repackaging concorrente.
4. **Package List Filter:** corrige falha de correspondência ao examinar fluidos **contidos dentro de packages**. Verificar filtros positivos/negativos, pedidos mistos e rejeição correta, sem extrair fluidos apenas para inspeção de UI.
5. **Stock Keeper screen:** restaura sons ausentes ao ajustar pedidos de fluido. É correção de feedback de interface, sem evidência de alteração do pedido no servidor.
6. **Multi-Fluid Access Ports:** corrige perda do drop de filtros quando o bloco é quebrado. Verificar drops/estado do filtro em survival e após reinício, sem duplicar conteúdo.

### Aplicação ao modpack e limites
O efeito prático mais importante é a correção de **repackaging misto**, **match de filtro de fluidos** e **devolução de filtros**. A mudança de eixo e o renderer precisam de validação com Create 6.0.10. O changelog oficial não apresenta novas dependências nem alteração de schema ou migração de saves; portanto, esses aspectos permanecem desconhecidos, em vez de serem declarados preservados.

**Cadeia documental auditada:** 1.3.0 instalada → 1.3.1 → 1.3.2 → **1.3.3** (última release verificada em 21/11/2026). Conferido o changelog da versão-alvo; nenhuma release intermediária adicional identificada entre 1.3.2 e 1.3.3 no catálogo consultado. **Auditoria documental concluída até 1.3.3; upgrade físico e QA in-game NÃO executados.** Não alterar nome do dossier, JAR ou versão instalada na tabela.

**Fonte primária:** [CurseForge, arquivo 9712748, versão 1.3.3 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/create-fluidlogistic/files/9712748) — seção *What's new*, seis itens explicitamente atribuídos à publicação de 20/11/2026.
