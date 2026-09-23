# Create Aeronautics: Transmission & Linkage

## Propriedades do registro

- **Mod:** Create Aeronautics: Transmission & Linkage
- **Arquivo JAR:** create_aeronautics_transmission_linkage-0.2.8.jar
- **Versão 1.21.1:** 0.2.8
- **Categoria:** Tecnologia, Automação
- **Função:** Adiciona juntas, conversores cinéticos, hastes hidráulicas e interfaces para transmitir movimento/força entre estruturas físicas móveis do Create Aeronautics.
- **Dependências:** Create 6.0.10 + Create Aeronautics 1.3.2 + Sable 2.0.5 compõem o stack físico relevante.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: constraint/frame mismatch, kinetic double-count, stale linkage após unload, rod/joint state órfão, torque/stress divergence e API drift Aeronautics/Sable/Create. 0.2.8 tem regression gate explícito em vehicle unloading.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-transmission-linkage
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `aeronautics_utility_objects` 0.2.8 + CurseForge/Modrinth oficiais Create Aeronautics: Transmission & Linkage 0.2.8 revalidados em 20/09/2026.
- **Observações:** JAR físico `create_aeronautics_transmission_linkage-0.2.8.jar`, mod id `aeronautics_utility_objects`, runtime 0.2.8. O texto antigo que citava runtime 0.2.7 foi corrigido. Release NeoForge 1.21.1 de 02/09/2026; 0.2.8 melhora estabilidade durante vehicle unloading.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — registro histórico do lote físico #131 na snapshot então vigente; posição física atual #132: create_aeronautics_transmission_linkage-0.2.8.jar / 0.2.8 confirmados; joints/rods/bearings, cross-body kinetics e estabilidade durante vehicle unloading permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa a transmissão cinética do Create entre estruturas físicas móveis; não substitui shafts/cogwheels do Create nem a física Aeronautics.

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico 0.2.8. Transmission & Linkage acrescenta componentes para conectar, restringir e transmitir movimento entre estruturas físicas Create Aeronautics.

## 1. Authority
Create mantém authority de kinetic network/stress; Aeronautics/Sable mantêm authority de bodies, transforms e constraints. O addon cria interfaces entre essas superfícies, sem substituir nenhum provider.
## 2. Componentes publicados
O projeto lista universal joints, hydraulic rods, hinge heads, pneumatic regulators e kinetic conversion bearings. São componentes de linkage/transmission entre estruturas móveis e devem preservar a diferença entre transformação física e velocidade/rotação Create.
## 3. Cross-body kinetics
Transferir rotação entre dois bodies móveis exige resolver orientação e vínculo no frame correto. A rede Create não pode receber duas fontes equivalentes por reconstrução indevida do linkage, nem manter stress/capacity fantasma após a conexão ser removida.
## 4. Constraints e movimento
Joints/rods/hinges precisam manter anchors válidos quando veículo se move. Desmontar, descarregar ou destruir um dos lados deve invalidar o vínculo com segurança. Um client render correto não prova que a constraint server-side está íntegra.
## 5. Unload stability na 0.2.8
O changelog exato 0.2.8 registra melhoria de estabilidade durante **vehicle unloading**. Esse ponto é regression gate central: sair de range, descarregar chunks, reconnect e restart não podem gerar crash, teleport, linkage órfão ou energia cinética duplicada.
## 6. Multiplayer
Criação/remoção de vínculo, settings de regulator e state de bearing precisam ser server-authoritative. Dois jogadores editando o mesmo linkage não devem criar duas constraints ou redes divergentes.
## 7. Riscos
1. Frame mismatch desloca anchors.
2. Kinetic network recebe stress/rotation duplicados.
3. Link permanece após body unload/removal.
4. Rod/hinge salva state parcial.
5. Reconnect restaura constraint duas vezes.
6. Version drift Create/Aeronautics/Sable.
## 8. Matriz de testes
- [ ] Dedicated server inicia com 0.2.8 + Create 6.0.10 + Aeronautics 1.3.2.
- [ ] Universal Joint transfere rotação entre bodies móveis.
- [ ] Rod/hinge mantém anchors sob movimento e rotação.
- [ ] Remover um lado desmonta linkage sem state órfão.
- [ ] Vehicle unload/reload não crasha nem duplica constraint.
- [ ] Restart preserva apenas vínculos válidos.
- [ ] Stress/rotation não são double-counted.
- [ ] Multiplayer converge para um único linkage state.
Nenhum teste foi marcado como aprovado.
## 9. Evidências e limite
A publicação oficial confirma 0.2.8, Client & Server, os principais componentes e a melhoria de unloading. Fórmulas internas de torque/constraint não foram pinadas e não são reimplementadas nesta ficha.
