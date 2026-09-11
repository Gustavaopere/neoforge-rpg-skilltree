# Create: Curios Backtank

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c469db9f0db811397b5f53faa7706f2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Curios Backtank
- **Arquivo JAR:** `create_curios_backtank-neoforge-1.21.1-1.0.1.jar`
- **Versão 1.21.1:** 1.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, QoL, Tecnologia
- **Função:** Bridge de equipagem que permite usar os backtanks base do Create em slot Curios. Create permanece authority do backtank/pressurized air e Curios do slot; não adiciona um segundo sistema de ar.
- **Dependências:** Obrigatórias segundo relações oficiais: Create + Curios API. Runtime físico: Create 6.0.10 + Curios API 9.5.1+1.21.1. Release 1.0.1 é Client & Server.
- **Sobreposição:** Overlap funcional direto com Create: Curios Jetpack & Backtank 1.2.0 instalado, cujo upstream também habilita backtanks base em Curios. Create Goggles 6.1.1 instalado publica Backtank Support via Curios. Tratar como possível redundância/duplo hook; não como incompatibilidade provada.
- **Compatibilidade/Riscos:** Riscos: duplicate integration/lookup, double-consumption de pressurized air, ghost equipment, state loss ao mover Curios↔chest, render duplicado e semântica de armor não documentada. Pack também instala Create: Jetpack Curios 1.2.0 e Create Goggles 6.1.1, ambos com suporte publicado a backtanks em Curios; overlap confirmado, conflito não presumido.
- **Observações:** mod id `create_curios_backtank`; runtime 1.0.1. JAR físico sem mixin configs listados. Release oficial NeoForge 1.21.1 de 26/04/2026; changelog: updated to neoforge 1.21.1. Não foi localizado source público versionado do próprio mod nesta auditoria.
- **Procedência:** modlist.txt física atual de 09/09/2026 (594 JARs top-level) + metadata runtime + CurseForge/Modrinth oficiais da build 1.0.1 + relações oficiais Create/Curios + source oficial Create 6.0.10 para contrato do backtank + verificação dos overlaps físicos Jetpack Curios/Create Goggles.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-curios-backtank
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio reconstruído; bridge Create↔Curios, pressurized-air authority, lifecycle/equip state, client/server, overlap com Jetpack Curios/Create Goggles e matriz de testes catalogados para 1.0.1.
- **Histórico da decisão:** O mod foi recolocado em 22/08/2026 e a decisão formal permanece Manter. A justificativa histórica então registrada dizia que Curios Backtank e Jetpack Curios não eram duplicatas. Na auditoria de 09/09/2026, o upstream atual de Create: Curios Jetpack & Backtank 1.2.0 confirmou suporte também aos backtanks base do Create, portanto há overlap funcional direto. A decisão Manter foi preservada e não foi alterada automaticamente; a redundância deve ser reavaliada separadamente.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> 🎒 **Identidade física confirmada:** `create_curios_backtank-neoforge-1.21.1-1.0.1.jar`, mod id `create_curios_backtank`, runtime `1.0.1`, NeoForge 1.21.1. A release oficial de 26/04/2026 é Client & Server e declara como função permitir usar o backtank do Create em um slot Curios.

## 1. Papel e authority
Create: Curios Backtank é uma **bridge de equipagem**, não um novo sistema de ar comprimido. **Create 6.0.10** continua owner dos Copper/Netherite Backtanks, do saldo de pressurized air, do enchimento cinético, dos consumidores de ar e de toda a lógica de gameplay do backtank. **Curios API 9.5.1+1.21.1** continua owner da infraestrutura de slots/equipamento Curios.
O addon apenas torna o backtank elegível/operável via Curios. Não deve existir um segundo saldo de ar, um segundo backtank lógico ou uma segunda autoridade de consumo.

## 2. Dependências obrigatórias
As relações oficiais da build confirmam duas dependências requeridas:
- **Create**;
- **Curios API (Forge/NeoForge)**.
No pack físico atual estão instalados **Create 6.0.10** e **Curios API 9.5.1+1.21.1**. A publicação 1.0.1 não lista biblioteca adicional obrigatória.

## 3. Função exata da bridge
A descrição oficial é deliberadamente pequena: permitir colocar/puxar o **backtank do Create para um slot Curios**. A ficha não inventa novos itens, blocos, recipes, atributos, keybinds ou configs próprios porque nenhuma dessas superfícies foi confirmada para 1.0.1.
O JAR físico também não lista mixin config no inventário da modlist. Isso reduz a superfície de patch detectada, mas não prova ausência de event handlers, Curios registration ou outras integrações internas.

## 4. Contrato do Backtank pertencente ao Create
No source oficial do Create 6.0.10, Copper Backtank e Netherite Backtank são **pressurized-air sources** e também itens de chest armor. Os tooltips oficiais definem o backtank como um wearable tank que fornece **Pressurized Air** a equipamentos que exigem esse recurso; quando colocado e alimentado por cinética, acumula ar em função da velocidade de rotação.
Essas regras continuam pertencendo ao Create mesmo quando o item está equipado por Curios. A bridge não deve recalcular capacidade, enchimento ou custo de ar.

## 5. Consumidores de ar e lookup de equipamento
Create usa o backtank como fonte para equipamentos que exigem pressurized air, incluindo o ecossistema de diving gear e outras ferramentas Create. O ponto crítico desta bridge é fazer o backtank equipado via Curios ser encontrado pelo caminho de lookup que os consumidores esperam.
A documentação pública do projeto 1.0.1 não expõe classes/métodos de lookup nem source pin. Portanto não congelar API, event, capability ou método específico; validar comportamento observável no runtime.

## 6. Slot de peitoral versus Curios
O valor funcional do addon é permitir usar o backtank fora do slot vanilla de peitoral e, assim, manter uma chestplate equipada simultaneamente. Entretanto, a publicação exata de Curios Backtank não documenta de forma suficiente **quais atributos defensivos do backtank continuam ou deixam de valer quando ele está em Curios**.
Isso deve permanecer fail-closed: não assumir armor points, toughness, enchantment protection ou qualquer outro benefício defensivo sem teste da build 1.0.1.

## 7. Estado de ar e persistência
O saldo de ar pertence ao próprio item/state do Create. Mover o mesmo stack entre inventário, chest slot e Curios não pode resetar, duplicar ou criar duas cópias do saldo.
Lifecycle crítico:
- equipar/retirar do Curios;
- mover Curios ↔ chest/inventory;
- relog;
- death/respawn;
- dimension change;
- server restart;
- esgotar o ar;
- recarregar o mesmo backtank quando colocado e voltar a equipá-lo.
Em todas essas transições deve existir um único ItemStack autoritativo e um único saldo de pressurized air.

## 8. Client/server
A release é oficialmente **Client & Server**. Equip state, consumo de ar, validade do backtank e efeitos que dependem de ar precisam convergir no servidor. Render do backtank, slot UI e overlays são client-facing.
A presença visual do item no slot Curios não é prova de que o servidor reconheceu o backtank como fonte de ar; smoke-tests devem confirmar ambos os lados.

## 9. Multiplayer
Dois jogadores podem usar backtanks independentes em Curios. O state de um jogador não pode ser lido, consumido ou renderizado como se pertencesse ao outro.
Equip/unequip sob latência, respawn e reconnect não devem gerar item ghost, duplicação de stack, saldo de ar duplicado ou consumer procurando o slot antigo por um tick permanente.

## 10. Sobreposição direta — Create: Curios Jetpack & Backtank
A modlist física atual também instala **`create_jetpack_curios-1.2.0-neoforge-1.21.1.jar`**. O upstream dessa build declara explicitamente que ela fornece Curios compatibility não apenas ao Create Jetpack, mas também aos **backtanks do Create base**, permitindo funcionamento no back slot Curios.
Portanto existe **sobreposição funcional direta** com Create: Curios Backtank 1.0.1. A antiga distinção histórica “um cuida de backtank, outro só de jetpack” não descreve mais corretamente o escopo publicado da versão atual de Jetpack Curios.
Isso não prova incompatibilidade nem autoriza remoção automática: pode haver simples registration redundante/idempotente. Mas é uma superfície real de duplicidade que deve ser testada e considerada em futura decisão curatorial.

## 11. Sobreposição adicional — Create Goggles
O pack físico instala **Create Goggles 6.1.1**. A documentação atual desse projeto informa suporte opcional a Curios e **Backtank Support** na linha moderna.
Isso cria uma terceira superfície potencial de reconhecimento de backtank em Curios. Sem source pin das três builds em conjunto, não afirmar conflito; registrar como risco de registration/lookup/render redundante e validar empiricamente.

## 12. Relação com Curios API
Curios é o provider de slots e equipamento. Create: Curios Backtank não deve copiar inventário Curios nem manter um cache independente do slot.
Alterações de slot, invalidação de capability/context e sync de equipamento devem ser observadas do provider. Scripts próprios não devem manter uma segunda lista de “backtanks equipados” por jogador.

## 13. Ausência de conteúdo próprio confirmado
Para 1.0.1 não foram confirmados:
- blocos próprios;
- novos backtanks;
- recipes próprios;
- configs próprias;
- enchantments;
- attributes;
- keybinds;
- worldgen;
- mobs/entities.
A função confirmada é a bridge de equipagem. Não aumentar artificialmente o escopo da ficha.

## 14. Riscos técnicos
1. **Duplicate integration:** Curios Backtank + Jetpack Curios + Create Goggles registram/descobrem o mesmo backtank por caminhos paralelos.
2. **Air double-consumption:** dois hooks reconhecem o mesmo stack e debitam pressurized air duas vezes.
3. **Air not found:** render mostra o item em Curios, mas consumer Create procura apenas o caminho vanilla.
4. **Ghost equipment:** stack permanece logicamente em dois slots após move/retry.
5. **State loss/dupe:** saldo de ar reseta ou duplica ao mover Curios ↔ chest/inventory.
6. **Armor semantics:** benefícios defensivos podem divergir do esperado quando o item não ocupa chest slot; comportamento exato não está documentado pelo projeto.
7. **Render overlap:** múltiplos addons tentam renderizar o mesmo backtank no jogador.
8. **Lifecycle desync:** death/relog/dimension change deixa lookup de slot stale.
9. **Version drift:** mudanças em Curios equip API ou Create backtank lookup quebram a bridge pequena sem erro evidente no boot.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Curios Backtank 1.0.1 + Create 6.0.10 + Curios 9.5.1.
- [ ] Copper Backtank pode ser equipado via Curios e coexistir com chestplate.
- [ ] Netherite Backtank pode ser equipado via Curios.
- [ ] Backtank Curios é reconhecido por ao menos um consumer real de pressurized air do Create.
- [ ] Consumo de ar ocorre **exatamente uma vez** por uso/tick esperado.
- [ ] Mover Curios ↔ chest ↔ inventory preserva exatamente o mesmo saldo de ar.
- [ ] Backtank vazio deixa de alimentar consumers sem ghost state.
- [ ] Colocar/recarregar cineticamente e reequipar preserva o novo saldo.
- [ ] Death/respawn, relog, dimension change e restart não duplicam item/state.
- [ ] Multiplayer com dois jogadores mantém backtanks e saldos independentes.
- [ ] Com `create_jetpack_curios-1.2.0` ativo, não ocorre double-consumption, duplicate slot ou render duplo.
- [ ] Com Create Goggles 6.1.1 ativo, não ocorre segundo lookup/render conflitante.
- [ ] Verificar separadamente se armor/protection do backtank é ou não aplicado quando equipado via Curios; não presumir resultado.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- modlist física atual: `create_curios_backtank-neoforge-1.21.1-1.0.1.jar`, mod id `create_curios_backtank`, runtime `1.0.1`, sem mixin configs listados;
- CurseForge oficial da build 1.0.1: NeoForge 1.21.1, Release, Client & Server, file ID 7994676 e changelog “updated to neoforge 1.21.1”;
- relações oficiais CurseForge: Create + Curios API como dependências requeridas;
- Modrinth oficial: mesmo escopo de equipar backtank em Curios;
- source oficial Create 6.0.10: Copper/Netherite Backtanks são `PRESSURIZED_AIR_SOURCES` e chest armor; tooltips oficiais confirmam fornecimento/acúmulo de pressurized air;
- modlist física: `create_jetpack_curios` 1.2.0 e Create Goggles 6.1.1 instalados;
- upstream de Create: Curios Jetpack & Backtank 1.2.0 confirma suporte também aos backtanks base; upstream Create Goggles publica Curios Backtank Support.
Não foi localizado source público versionado do próprio Create: Curios Backtank 1.0.1 nesta auditoria. Internals de slot registration, event hooks e lookup permanecem fail-closed.

> 🔒 Boundary canônico: **Create controla backtank e pressurized air; Curios controla o slot; Curios Backtank apenas conecta os dois**. No pack atual há outras duas integrações capazes de tocar o mesmo domínio, então exactly-once e state único são os principais gates de compatibilidade.