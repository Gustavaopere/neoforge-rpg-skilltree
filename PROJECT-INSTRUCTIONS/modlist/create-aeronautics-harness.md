# Create Aeronautics: Harness

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817a8a97c4d0189c61ed
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Harness
- **Arquivo JAR:** `CreateAeronauticsHarness-1.21.1-1.0.1.jar`
- **Versão 1.21.1:** 1.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL, Exploração
- **Função:** Adiciona um harness para montar/carregar objetos e contraptions do Create Aeronautics nas costas do jogador; possui integração com Create Big Cannons para configurações transportáveis de canhões.
- **Dependências:** Pack físico: Create Aeronautics 1.3.2 + Sable 2.0.5; Create Big Cannons 5.11.7 integra concretamente com Harness 1.0.1. Source matching exige Aeronautics 1.3.0+.
- **Sobreposição:** Não é jetpack nem tether de segurança genérico; é sistema de carga/constraint de objetos físicos. CBC 5.11.7 amplia a superfície com disparo/recoil enquanto carregado.
- **Compatibilidade/Riscos:** Riscos: physics joint stale após logout/death/unload; packet duplicado; sublevel/range drift; object dupe/loss; CBC recoil concorrente com harness motors; API drift Aeronautics 1.3.2/Sable 2.0.5.
- **Observações:** JAR/mod id/runtime 1.0.1 confirmados. Source matching `mumu17-git/CreateAeronauticsHarness` confirma HarnessBlockEntity, sessions por UUID, server physics constraints, cleanup e packets.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial Harness 1.0.1 + source oficial matching + stack Aeronautics/Sable/CBC físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-harness
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.1 com HarnessBlockEntity, UUID sessions, Sable constraints, packets/keybind, CBC 5.11.7 integration, cleanup/lifecycle e regression matrix catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🪢 **Identidade física e source matching confirmados:** `CreateAeronauticsHarness-1.21.1-1.0.1.jar`, mod id `ca_harness`, runtime `1.0.1`. O source matching exige Create Aeronautics 1.3.0+ e confirma integração direta com Sable/Simulated physics.

## 1. Papel e authority
Create Aeronautics: Harness adiciona um sistema para o jogador carregar/interagir com objetos físicos de Create Aeronautics por meio de um harness. O addon owns sessão de uso, vínculo jogador↔harness, keybind/câmera e constraints auxiliares. **Sable/Simulated/Aeronautics** continuam owners da física do objeto/sublevel.

## 2. Uso publicado
A documentação oficial descreve equip/uso por **Shift + Right-Click** sobre o objeto/harness e tecla de soltura padrão **H**, rebindable. O objetivo não é voo pessoal: é prender/carregar um objeto físico junto ao jogador.

## 3. HarnessBlockEntity
O source matching confirma `HarnessBlockEntity` derivado da infraestrutura de Handle do Simulated. Ele mantém um mapa de UUID de jogadores para constraints e executa lógica no physics tick server-side.
Isso torna o block entity e o servidor authority do vínculo; câmera/render local não bastam para manter o objeto preso.

## 4. Sessão por UUID
A implementação mantém jogadores por UUID e limpa registros quando o jogador deixa de existir ou está morto/morrendo. Ao remover a sessão, o joint físico correspondente é removido.
Logout, death e unload são gates obrigatórios contra harness fantasma.

## 5. Physics constraint
Durante o physics tick, o addon cria/remove constraints Sable e usa motors lineares/angulares para aproximar o objeto da posição/orientação desejada em relação ao jogador.
Stiffness, damping e força máxima são derivados da configuração física do Simulated no source; esta ficha não congela valores de config do pack sem leitura do runtime configurado.

## 6. Alcance
O source valida distância contra o alcance de interação do jogador com margem adicional e limita `scrollDistance` a 2.5. Isso evita aceitar targets arbitrariamente distantes apenas por packet/input cliente.
O servidor continua sendo authority da distância válida.

## 7. Condições do jogador
A constraint considera estados como chão, movimento vertical, água, voo e climbable, além de sublevel acompanhado. Mudanças rápidas entre esses estados precisam remover/reconstruir a constraint sem impulso extremo ou state órfão.

## 8. Cleanup
`remove()` remove todos os joints e limpa o mapa de jogadores. O mesmo princípio precisa valer em block break, contraption destruction, chunk unload e restart.
Qualquer `PhysicsConstraintHandle` stale é risco de crash, força fantasma ou leak de physics state.

## 9. Client/server e packets
O projeto possui packets separados para atualizar player/harness e componentes client-side para câmera/keybind. Input/câmera são client-facing; criação/removal de sessão e efeito físico devem ser validados server-side.
Packet repetido não pode criar dois joints para o mesmo jogador.

## 10. Relação com Create Aeronautics/Sable
O pack contém Create Aeronautics 1.3.2 e Sable 2.0.5. O source 1.0.1 foi desenvolvido com Aeronautics 1.3.0, Sable 2.0.3, Simulated 1.3.0 e Sable Companion 1.6.0.
O pack está em revisões posteriores de Aeronautics/Sable; validar API/physics behavior diretamente.

## 11. Create Big Cannons — feature 1.0.1
O changelog oficial da 1.0.1 adiciona compatibilidade com **Create: Big Cannons**. A página pública descreve possibilidade de disparar um canhão carregado pelo harness como um “mega launcher”.
CBC 5.11.7 está instalado, portanto essa integração é concreta.

## 12. Recoil e artilharia
CBC 5.11.7 também possui integração Sable/recoil. Disparar uma arma presa pelo harness pode aplicar forças simultaneamente por CBC e pela constraint do harness.
O teste precisa provar que recoil não duplica, não explode a constraint e não teleporta jogador/objeto.

## 13. Persistência
O vínculo ativo é runtime physics state, não deve ser presumido persistente através de restart se o provider não o serializa. O comportamento correto em restart precisa ser fail-safe: nenhuma constraint órfã e nenhum objeto duplicado.

## 14. Multiplayer
Múltiplos jogadores podem tentar interagir com o mesmo harness/objeto. Ownership e sessão devem impedir controle concorrente incoerente, especialmente se um jogador desconecta enquanto outro inicia interação.

## 15. Lifecycle
Testar attach, ajuste de distância, soltura, jump/water/climb/flying, mudança de sublevel, morte, logout, dimension, chunk unload, block removal, restart e disparo CBC durante uso.

## 16. Riscos
1. Constraint permanece após logout/death.
2. Packet duplicado cria dois joints.
3. Harness é removido sem remover physics handle.
4. Mudança de sublevel deixa constraint apontando para body antigo.
5. Distância client-side força target além do alcance autorizado.
6. Scroll/range produz NaN ou posição inválida.
7. CBC recoil e harness motors aplicam forças incompatíveis.
8. Objeto carregado duplica/desaparece em unload/restart.
9. Camera mixin diverge do state server-side.
10. Aeronautics/Sable 2.0.5 drift quebra constraint lifecycle.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Harness 1.0.1 + Aeronautics 1.3.2 + Sable 2.0.5.
- [ ] Shift+Right-Click inicia uma única sessão válida.
- [ ] H/rebind encerra a sessão e remove o joint.
- [ ] Scroll distance respeita o limite e não permite target remoto indevido.
- [ ] Jump/água/climb/flying não deixam constraint stale.
- [ ] Logout/death limpam UUID/joint imediatamente.
- [ ] Break/remove do harness limpa todos os handles.
- [ ] Chunk unload/reload não duplica objeto nem constraint.
- [ ] Dois jogadores não controlam o mesmo state de forma concorrente.
- [ ] CBC 5.11.7 dispara pelo harness sem recoil duplicado/crash.
- [ ] Restart não restaura physics handle órfão.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
A modlist física confirma JAR/mod id/runtime. A publicação oficial confirma Client & Server, carrying workflow e compat CBC adicionada em 1.0.1. O source matching confirma HarnessBlockEntity, sessões por UUID, cleanup, packets e constraints Sable/Simulated. Não foram inventados stats, capacidade de massa ou políticas de persistência não demonstradas.

> 🔒 **Boundary canônico:** Harness owns a sessão jogador↔objeto; Sable/Simulated/Aeronautics own o corpo e a simulação. Todo joint deve existir uma única vez e ser removido quando a sessão deixa de ser válida.
