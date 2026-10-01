# Sophisticated Backpacks Create Integration

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#517**: JAR `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar`, mod id `sophisticatedbackpackscreateintegration`, runtime `0.2.0`, SHA-1 `9a0ff3064fc1328165bc77fc1409a6adf2d9a8fa`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Backpacks Create Integration
- **Arquivo JAR:** `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar`
- **Versão 1.21.1:** 0.2.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Armazenamento
- **Função:** Full-featured bridge that keeps Sophisticated Backpacks storage/upgrades functional on Create moving contraptions and adds linked-storage support in the installed 0.2.0 line.
- **Dependências:** Required stack physically satisfied: Sophisticated Backpacks 3.26.3, Sophisticated Core 1.5.1 and Create 6.0.10.
- **Sobreposição:** Não cria storage alternativo; adapta o backpack existente a contraptions Create e linked-storage semantics.
- **Compatibilidade/Riscos:** Bridge stateful Create↔Sophisticated Backpacks. Riscos: inventory/upgrades duplication on assembly, stale contraption capability, linked-storage split-brain, movement/position-sensitive upgrade errors, chunk unload and concurrent access. 0.2.0 exact delta adds linked storage.
- **Observações:** JAR físico `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar`, runtime 0.2.0. Exact changelog: linked storage added to SB Create integration.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge oficial Sophisticated Backpacks Create Integration 0.2.0.168 + providers físicos Create 6.0.10 / Backpacks 3.26.3 / Core 1.5.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-create-integration/files/8833909
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 0.2.0; CurseForge publicou 0.2.1.171 para NeoForge 1.21.1 em 27/09/2026, atualizando a integração de linked storage para o Core mais recente.
- **Histórico da decisão:** Mantido para integração Sophisticated Backpacks ↔ Create. Em 11/09/2026 a ficha foi corrigida para o runtime físico 0.2.0.168; referências antigas a 0.1.8 são históricas e não descrevem mais a build instalada.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedbackpackscreateintegration-1.21.1-0.2.0.168.jar`, mod id `sophisticatedbackpackscreateintegration`, versão `0.2.0`, NeoForge 1.21.1. É a bridge oficial do stack Sophisticated para manter **Backpacks funcionais em contraptions Create**.

## 1. Identidade e dependências
- **Mod:** Sophisticated Backpacks Create Integration.
- **Versão:** `0.2.0`.
- **Required físicos:** Sophisticated Backpacks `3.26.3`, Sophisticated Core `1.5.1`, Create `6.0.10`.
- **Decisão:** **Manter**.
## 2. Ownership
Sophisticated Backpacks continua owner do conteúdo, upgrades e settings da mochila. Create continua owner de assembly/movement/contraption lifecycle. A integração adapta o acesso ao storage no contexto móvel.
Ela não deve copiar o conteúdo da mochila para um inventário paralelo da contraption.
## 3. Backpacks em movimento
O projeto descreve integração full-featured: storage features/upgrades devem continuar utilizáveis quando o backpack faz parte de uma contraption.
Isso exige resolver o contexto móvel sem perder a identidade persistente do backpack.
## 4. Assembly
Ao montar a contraption, exatamente um state funcional deve sobreviver. O block/item original não pode continuar acessível como storage independente se o conteúdo foi transferido ao contraption state.
Assembly cancelado/falho deve fazer rollback sem dupe/loss.
## 5. Disassembly
Ao desmontar, conteúdo, upgrades e settings precisam retornar ao backpack no mundo exatamente uma vez.
Posição final diferente da origem não pode gerar uma segunda cópia ou abandonar capability na contraption removida.
## 6. Upgrade behavior
A promessa upstream é manter funcionalidades Sophisticated no contexto Create. Upgrades que dependem de inventory state devem continuar operando sobre o mesmo conteúdo canônico.
Upgrades sensíveis a posição/mundo precisam usar o contexto atual da contraption, não coordenada antiga do bloco.
## 7. Dynamic context
Contraptions podem transladar, rotacionar e cruzar chunks. Qualquer operation que consulta world/position deve resolver o frame correto.
Cache de posição antiga pode causar pickup/interação no local errado ou acesso a chunk já descarregado.
## 8. Linked storage — delta exato 0.2.0
A build física `0.2.0.168` adiciona **linked storage** à integração Sophisticated Backpacks/Create.
Esse é o principal delta exato atribuído à versão. Link identity precisa permanecer única durante move/assembly/restart.
## 9. Split-brain prevention
Linked storage não pode permitir duas autoridades independentes sobre o mesmo inventário. Se duas references apontam para o mesmo backpack, ambas precisam convergir ao mesmo state server-side.
Desconectar/breakar um endpoint não pode clonar o inventário.
## 10. Chunk lifecycle
Contraption e linked storage podem cruzar unload boundaries. Validar:
- chunk source unload;
- destination load;
- contraption unload/reload;
- server restart com contraption persistida.
Capabilities/references stale devem ser invalidadas.
## 11. Multiplayer
Dois players acessando storage ligado/móvel não podem sobrescrever stacks por snapshots concorrentes. O servidor deve serializar/validar operações contra o state atual.
Permissões/ownership do backpack continuam sob os providers correspondentes.
## 12. Client / server
Servidor decide inventory, upgrades, linked identity e mutations. Cliente só apresenta GUI/render/input.
Abrir GUI em contraption não pode criar client-side mirror usado como authority.
## 13. Relação com outros compats
O pack também contém Create: Sophisticated Backpacks Compat, além desta integração oficial. A presença de dois compats exige avaliar **escopo por feature/recipe/API**, não presumir redundância pelo nome.
Esta ficha cobre especificamente a integração oficial Sophisticated com contraptions e linked storage.
## 14. Riscos técnicos
1. Assembly dupe/loss.
2. Capability stale após movimento.
3. Position-sensitive upgrade usando coordenada antiga.
4. Linked-storage split-brain.
5. Chunk unload invalidando reference sem cleanup.
6. Concurrent multiplayer write.
7. Create API drift.
8. Core/Backpacks ABI drift.
## 15. Matriz de testes
- [ ] Boot com Create 6.0.10 + Backpacks 3.26.3 + Core 1.5.1 + integration 0.2.0.
- [ ] Backpack com conteúdo monta em contraption sem dupe/loss.
- [ ] Upgrades/settings persistem durante movimento.
- [ ] Disassembly restaura exatamente um storage com state íntegro.
- [ ] Position-sensitive upgrade usa posição atual da contraption.
- [ ] Contraption cruza chunks e mantém inventory.
- [ ] Save/restart preserva backpack montado.
- [ ] Linked storage funciona em contraption — regression 0.2.0.
- [ ] Break/unlink não duplica conteúdo.
- [ ] Dois players acessando simultaneamente não causam lost update.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 16. Evidências e limites
- Modlist física atual: integration 0.2.0.168 + providers exatos.
- CurseForge oficial: full-featured integration com moving contraptions e exact delta linked storage.
- **Limite:** detalhes internos de cada upgrade não foram presumidos sem teste/source; o contrato funcional foi documentado no nível publicado pelo projeto.

## 17. Atualização upstream 0.2.1.171 — não instalada
O pack continua em **0.2.0.168 / runtime 0.2.0**. A release **0.2.1.171** para NeoForge 1.21.1 foi publicada em 27/09/2026.

O changelog contém uma mudança funcional: **updated linked storage integration for latest Core**.

Isso é relevante porque linked storage atravessa estado de inventário/backpack e APIs do Sophisticated Core. Uma promoção deve ser coordenada com a versão de **Sophisticated Core** instalada/atualizada, evitando misturar uma bridge nova com contratos antigos do Core.

Gate de regressão: backpack em contraption, linked storage, unload/reload da contraption, transferência de itens, save/restart e upgrades que consultam storage compartilhado.

Fonte upstream: https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-create-integration/files/8985949
