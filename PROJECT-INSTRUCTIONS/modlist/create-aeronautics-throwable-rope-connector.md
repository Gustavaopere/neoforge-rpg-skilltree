# Create Aeronautics: Throwable Rope Connector

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815da45ffb4ac6829bf5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Throwable Rope Connector
- **Arquivo JAR:** `create_aeronautics_throwable_rope_connector-0.4.3.jar`
- **Versão 1.21.1:** 0.4.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Tecnologia
- **Função:** Adiciona lançamento/colocação remota de rope connectors e recursos de acoplamento/docking para veículos do Create Aeronautics/Simulated.
- **Dependências:** Create Aeronautics 1.3.2 físico; Sable 2.0.5 é a base de sublevels/física do stack. O resultado usa Rope Connector/Coupling do ecossistema Aeronautics/Simulated.
- **Sobreposição:** Climbable Ropes trata escalada; este addon trata lançamento remoto e acoplamento de Rope Connector para veículos físicos. Funções complementares.
- **Compatibilidade/Riscos:** Riscos: frame/aiming incorreto em ships móveis, projectile/placement desync, coupling órfão, duplicate connector, target unload e API drift Aeronautics/Sable. 0.4.3 é regression gate específico para mounted ship-to-ship aiming.
- **Observações:** JAR físico `create_aeronautics_throwable_rope_connector-0.4.3.jar`, mod id `create_aeronautics_throwable_rope_connector`, runtime 0.4.3. Release NeoForge 1.21.1 de 10/08/2026, Client & Server; 0.4.3 corrige aiming do Mounted Rope Launcher em contraptions Sable, especialmente ship-to-ship.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create Aeronautics: Throwable Rope Connector 0.4.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-throwable-rope-connector
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; throw/launcher flow, Rope Coupling, mounted aiming, ship-to-ship, authority, lifecycle e regressões catalogados para 0.4.3.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🪢 **ESCOPO CANÔNICO.** Runtime físico 0.4.3. O addon permite lançar/posicionar Rope Connectors a distância e preparar Rope Couplings para operações de winch/docking em veículos Create Aeronautics.

## 1. Authority
Aeronautics/Sable continuam owners da física e dos sublevels. O addon owns o fluxo de lançamento/placement; Rope Connector/Coupling resultante continua obedecendo às regras do provider físico.

## 2. Superfícies de gameplay
O projeto documenta três ferramentas principais: Throwable Rope Connector, Rope Connector Launcher e Mounted Rope Launcher. Em um lançamento válido, é colocado um Rope Connector normal e o jogador recebe/gera o Rope Coupling vinculado, pronto para uso com Rope Winch.

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
A Release 0.4.3, as três superfícies de lançamento e o fix de mounted aiming são sustentados pela publicação oficial. Fórmula exata de trajetória e internals de serialization não foram pinados; permanecem fail-closed.
