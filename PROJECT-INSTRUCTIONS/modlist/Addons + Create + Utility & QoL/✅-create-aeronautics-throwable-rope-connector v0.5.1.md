# Create Aeronautics: Throwable Rope Connector — 0.5.1

## Propriedades vigentes — 27/11/2026

- **Mod:** Create Aeronautics: Throwable Rope Connector
- **Arquivo JAR instalado (declarado):** `create_aeronautics_throwable_rope_connector-0.5.1.jar`
- **Versão instalada declarada:** `0.5.1` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #130 (preservada).
- **Categoria CurseForge / pasta:** Addons + Create + Utility & QoL.
- **Função:** Lançamento remoto de rope connectors, acoplamento e redocking de veículos Create Aeronautics.
- **Atualização/Status:** `0.4.3` → `0.5.1`.
- **Estado no pack:** instalação declarada pelo usuário, sem nova leitura física.
- **Estado da pesquisa:** Auditoria incremental com ressalvas explícitas de atribuição/publicação; prefixo histórico preservado, não implica certificação granular integral do delta. Sem homologação de runtime.
- **Dependências / compatibilidade / riscos:** README 0.5.0: NeoForge >=21.1.228, Create >=6.0.10, Aeronautics bundled >=1.3.0, Sable conforme Aeronautics. Autor testou combinação Create 6.0.10/Aeronautics 1.3.2/Sable 2.0.6/NeoForge 21.1.228, não é QA local. Índice Create 6.0.10, Aeronautics 1.3.3, Sable 2.0.5; testar claim permissions, redocking, munição, transforms, automation e save migration de config.
- **Fonte:** [publicação ou catálogo oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-throwable-rope-connector/files/all).
- **Procedência:** declaração expressa do usuário e distribuição oficial; fontes complementares e limitações descritas abaixo. Não atribuir hashes nem metadata do novo JAR sem inspeção.

## Auditoria incremental e reconciliação

0.5.0 — 17/11/2026, Release: [source 8aa7592](https://github.com/sprocketaudio/Create-Aeronautics-Throwable-Rope-Connector/commit/8aa759218ea1c4ba8c9e841085c5638442973c65), [README versionado](https://github.com/sprocketaudio/Create-Aeronautics-Throwable-Rope-Connector/blob/8aa759218ea1c4ba8c9e841085c5638442973c65/README.md). Introduz redocking a âncoras existentes desconectadas usando Rope Couplings normais como segundo tipo de munição; handheld seleciona âncora no coupling, mounted conecta automaticamente e consome um coupling. Mounted usa clique esquerdo para disparar, direito para desconectar preservando âncora, Sprint+direito para removê-la se permitido, Sneak para sair. Marcador vermelho recebe pulso de disparo com última mira; âmbar desconecta. Aceita stack único e automação de itens; falhas não consomem munição por padrão. Jade opcional mostra munição/conexão; FTB Chunks opcional protege placement/removal. A opção mountedAnchorReleaseMode foi retirada; release normal sempre preserva âncora.
- 0.5.1 — 23/11/2026, Release: [source b2d2ae4](https://github.com/sprocketaudio/Create-Aeronautics-Throwable-Rope-Connector/commit/b2d2ae45fa9c6a9f8a36809c8996b5857bb7bd71) corrige rotação do launcher mounted; gradle.properties confirma versão 0.5.1 e Minecraft 1.21.1. Catálogo oficial confirma ambos os artefatos, ficha individual [9781672](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-throwable-rope-connector/files/9781672) não abriu; deltas sustentados pelo source, sem atribuir inspeção binária.

## Histórico documental preservado

> Os dados abaixo correspondem aos snapshots e auditorias anteriores. Menções a versão física, atual, candidata, não instalada, hashes e dependências são históricas; as propriedades vigentes prevalecem.

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#130**: JAR `create_aeronautics_throwable_rope_connector-0.4.3.jar`, mod id `create_aeronautics_throwable_rope_connector`, runtime `0.4.3`, SHA-1 `63fcbbc960461d42a59b417665fb938c79919514`.

## Propriedades do registro

- **Mod:** Create Aeronautics: Throwable Rope Connector
- **Arquivo JAR:** create_aeronautics_throwable_rope_connector-0.4.3.jar
- **Versão 1.21.1:** 0.4.3
- **Categoria:** QoL, Tecnologia
- **Função:** Adiciona lançamento/colocação remota de rope connectors e recursos de acoplamento/docking para veículos do Create Aeronautics/Simulated.
- **Dependências:** Upstream 0.4.3 exige Minecraft 1.21.1, NeoForge 21.1.228+ e Create Aeronautics 1.3.0+. O pack físico atual usa NeoForge 21.1.250 + Create Aeronautics 1.3.2; Sable 2.0.5 é a base de sublevels/física do stack.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: frame/aiming incorreto em ships móveis, projectile/placement desync, coupling órfão, duplicate connector, target unload e API drift Aeronautics/Sable. 0.4.3 é regression gate específico para mounted ship-to-ship aiming.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-throwable-rope-connector
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_aeronautics_throwable_rope_connector` 0.4.3 + CurseForge/Modrinth oficiais da release 0.4.3 revalidados em 20/09/2026.
- **Observações:** JAR físico `create_aeronautics_throwable_rope_connector-0.4.3.jar`, mod id `create_aeronautics_throwable_rope_connector`, runtime 0.4.3. Release NeoForge 1.21.1 de 10/08/2026, Client & Server; 0.4.3 corrige aiming do Mounted Rope Launcher em contraptions Sable, especialmente ship-to-ship. Config oficial separa throwing/launcher/mounted/general/visual e suporta automação redstone opcional.
- **Atualização/Status:** 22/11/2026 — solicitação do usuário conferida: CurseForge file 8618590 continua a última distribuição oficial NeoForge 1.21.1, `create_aeronautics_throwable_rope_connector-0.4.3.jar`, versão `0.4.3`. Nenhuma versão posterior compatível a incorporar; dossier e nome permanecem corretos. Confirmação de instalação é declaração do usuário; o JAR local não foi inspecionado.
- **Decisão:** Sem decisão
- **Sobreposição:** Climbable Ropes trata escalada; este addon trata lançamento remoto e acoplamento de Rope Connector para veículos físicos. Funções complementares.

> 🪢 **ESCOPO CANÔNICO.** Runtime físico 0.4.3. O addon permite lançar/posicionar Rope Connectors a distância e preparar Rope Couplings para operações de winch/docking em veículos Create Aeronautics.

## 1. Authority
Aeronautics/Sable continuam owners da física e dos sublevels. O addon owns o fluxo de lançamento/placement; Rope Connector/Coupling resultante continua obedecendo às regras do provider físico.
## 2. Superfícies de gameplay
O projeto documenta três ferramentas principais: Throwable Rope Connector, Rope Connector Launcher e Mounted Rope Launcher. Em um lançamento válido, é colocado um Rope Connector normal e o jogador recebe/gera o Rope Coupling vinculado, pronto para uso com Rope Winch.
A documentação oficial atual também expõe configurações separadas para throwing, launcher, mounted, general e visual, além de automação redstone opcional do Mounted Rope Launcher. Ranges configuráveis não podem exceder o `max_rope_range` do Create Simulated; esse limite continua pertencendo ao provider físico.
## 3. Mounted launcher e 0.4.3
A correção exata 0.4.3 trata aiming do Mounted Rope Launcher em contraptions Sable, com destaque para disparos ship-to-ship. Isso torna transformação de coordenadas/orientação entre dois frames móveis o principal regression gate da build.
## 4. Placement e vínculo
Um disparo não deve criar dois connectors nem dois couplings. O vínculo precisa apontar ao connector correto e ser invalidado de forma segura se alvo/ship desaparecer, descarregar ou for desmontado.
## 5. Client/server e multiplayer
Trajectory/aim preview podem ser client-facing; spawn/placement, consumo do item, geração do coupling e vínculo são server-authoritative. Dois jogadores disparando para o mesmo ponto devem produzir estados independentes e determinísticos.
## 6. Lifecycle
Testar disparo em ship parado/móvel, chunk unload, target unload, reconnect, restart e disassembly. Um coupling persistido não pode apontar silenciosamente para entidade/sublevel reciclado.
## 7. Riscos
1. Aim usa world frame em vez de ship frame.
2. Projectile aparece correto no cliente mas coloca connector em posição divergente.
3. Connector/coupling duplica em retry ou lag.
4. Coupling fica órfão após unload/disassembly.
5. Ship-to-ship motion causa overshoot ou placement no frame errado.
6. API drift Aeronautics/Sable.
## 8. Matriz de testes
- [ ] Dedicated server inicia com 0.4.3 + Aeronautics 1.3.2 + Sable 2.0.5.
- [ ] Throwable Connector coloca exatamente um connector e vínculo correto.
- [ ] Hand launcher funciona entre world→ship e ship→world.
- [ ] Mounted launcher funciona ship→ship em movimento.
- [ ] Coupling sobrevive reconnect/restart quando alvo continua válido.
- [ ] Alvo removido invalida vínculo sem dupe/crash.
- [ ] Dois jogadores não compartilham coupling state indevidamente.
Nenhum teste foi marcado como aprovado.
## 9. Evidências e limite
A Release 0.4.3, as três superfícies de lançamento, o fix de mounted aiming, os grupos de config e a automação redstone opcional são sustentados pela publicação oficial. O upstream exige NeoForge 21.1.228+ e Create Aeronautics 1.3.0+; o pack físico usa NeoForge 21.1.250 e Aeronautics 1.3.2, acima desses floors. Fórmula exata de trajetória e internals de serialization não foram pinados; permanecem fail-closed.
