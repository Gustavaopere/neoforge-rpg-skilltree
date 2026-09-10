# Create Contraption Terminals

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81369e87f12ddd34706a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Contraption Terminals
- **Arquivo JAR:** `createcontraptionterminals-1.21-1.4.0.jar`
- **Versão 1.21.1:** 1.4.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Armazenamento, Tecnologia
- **Função:** Permite utilizar terminais do Tom's Simple Storage diretamente em contraptions do Create.
- **Dependências:** Pack físico: Create 6.0.10 + Tom's Simple Storage 2.4.2. A 1.4.0 corrige explicitamente compatibilidade/crash com versões mais novas do Tom's.
- **Sobreposição:** Bridge específico Create↔Tom's para terminals em contraptions; não substitui Tom's storage nem é um sistema completo de autocrafting. Vault detector evita contagem incorreta de multiblocks.
- **Compatibilidade/Riscos:** Riscos: crash/API drift com Tom's 2.4.2; Vault double-count; slot/free-slot regressão; inventory reference stale após disassembly; menu em movimento/chunk unload; concurrent extract/craft dupe; contraptions antigas sem reassembly.
- **Observações:** JAR/mod id/runtime 1.4.0 confirmados. Dependência física corrigida de Tom's 2.4.1 para **2.4.2**. Upstream exige reassembly de contraptions antigas e 1.4.0 corrige slot/free-slot counts.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/README/changelog oficiais Create Contraption Terminals 1.4.0 + Tom's 2.4.2 físico.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-contraption-terminals
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.4.0 com contraption inventory authority, Storage/Crafting Terminal, Vault detection, reassembly, Tom's 2.4.2 fixes, lifecycle e concurrency catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 📦 **Identidade física confirmada:** `createcontraptionterminals-1.21-1.4.0.jar`, mod id `createcontraptionterminals`, runtime `1.4.0`. O pack usa Tom's Simple Storage **2.4.2**, não 2.4.1 como constava na ficha antiga.

## 1. Papel e authority
Create Contraption Terminals é uma bridge entre **Tom's Simple Storage** e **Create** que permite usar Storage/Crafting Terminals sobre contraptions montadas e acessar o inventário agregado da própria contraption. Tom's continua owner da lógica de terminal/storage; Create continua owner da contraption; a bridge owns a adaptação entre os dois contexts.

## 2. Dependências concretas
O pack contém Create 6.0.10 e `toms_storage-1.21-2.4.2.jar`, runtime 2.4.2. A 1.4.0 existe explicitamente para corrigir crash com versões mais novas de Tom's Storage, portanto 2.4.2 é uma combinação que precisa de smoke-test direto.

## 3. Terminais em contraptions
A função central é permitir que terminais Tom's montados numa contraption acessem **o inventário inteiro da contraption assembled**. O inventory view precisa refletir containers efetivamente capturados no assembly, não inventários estacionários externos por proximidade.

## 4. Storage Terminal
Storage Terminal deve listar/agregar os stacks da contraption e permitir operações válidas conforme as regras Tom's. Counts, slot/free-slot metrics e search/filter são presentation derivada do inventory state real.
O servidor é authority da quantidade; UI local não pode criar/extrair item além do disponível.

## 5. Crafting Terminal
Crafting Terminal adiciona crafting sobre a mesma visão de storage. Inputs precisam ser retirados uma única vez do inventory agregado e output entregue uma única vez ao jogador/storage conforme a semântica do provider.
Fechar UI, mover contraption ou desassemblar durante craft não pode duplicar ingredients/output.

## 6. Reassembly obrigatório após instalação
O README oficial registra que contraptions existentes com terminais precisam ser **reassembled** após instalar o mod. Isso indica que a bridge injeta/captura state no assembly.
Mundos atualizados precisam de procedimento consciente: desassemble/reassemble em ambiente controlado antes de concluir que um terminal antigo está quebrado.

## 7. Vault multiblock detector
A versão 1.3.0 adicionou detector para **Vault multiblock**. Isso evita tratar partes do mesmo vault como inventories independentes ou contar storage incorretamente.
A 1.4.0 deve preservar esse comportamento; vaults grandes são regression gate para duplicate counting.

## 8. Fix 1.4.0 — Tom's Storage recente
O changelog oficial 1.4.0 corrige crash com versões mais novas de Tom's Simple Storage. Como o pack usa 2.4.2, esse fix é diretamente relevante.
Boot, assembly e abertura dos dois terminais precisam ser testados sem NoSuchMethod/API-drift crash.

## 9. Fix 1.4.0 — slot counts
A mesma build corrige **slot count e free slot count**. Esses números alimentam UI/logic e podem impactar decisões de inserção.
Regression gate: used/free slots precisam bater com containers/vaults reais após insert/extract e após reassembly.

## 10. Contraption inventory boundary
O inventário acessível é o inventário da contraption assembled. Ao desassemblar, o terminal volta ao contexto estacionário normal do Tom's/blocks do mundo conforme o provider.
Não manter referência stale ao antigo contraption inventory depois de disassembly.

## 11. Movement e network state
Contraptions podem se mover, parar, atravessar chunks e ser descarregadas. Terminal/session precisa seguir a identidade da contraption, não coordenadas mundiais antigas.
Packets de menu devem validar que a contraption e o terminal ainda existem e que o jogador mantém contexto válido.

## 12. Inventory mutation concorrente
Funis, deployers, belts, vault interfaces e outro jogador podem alterar o inventário enquanto um terminal está aberto. Counts/listing precisam convergir e operações precisam falhar de forma segura quando o stack já foi consumido por outro actor.

## 13. Tom's Simple Storage no pack
Tom's 2.4.2 é a solução de storage mantida no pack. Esta bridge não substitui Inventory Connector, terminals ou crafting do mod-base; ela apenas estende esses terminals para a superfície de contraption.
Config/recipes/semântica do Tom's permanecem sob o provider.

## 14. Relação com autocrafting/logística
A bridge dá acesso a storage em contraption, mas não deve ser descrita como sistema completo de autocrafting Create. Qualquer envio automático a máquinas/recipes depende de outros componentes/mods e deve ser catalogado separadamente.

## 15. Client/server
Menu/render/search são client-facing. Inventory identity, counts, insert/extract, craft consumption e contraption state são server-authoritative.
Cliente com listagem stale deve receber rejeição/resync, nunca item grátis.

## 16. Multiplayer
Dois players podem abrir terminals da mesma contraption. Extrações/crafts concorrentes devem serializar contra um único inventory state.
Permissões externas/claims continuam sendo responsabilidade de seus providers; esta bridge não deve ignorá-las por estar numa contraption.

## 17. Lifecycle
Testar assembly/reassembly, terminal open/close em movimento, stop/start, chunk boundary, unload, disassembly com GUI aberta, vault resize, restart com contraption, player disconnect e simultaneous users.

## 18. Riscos
1. Crash com Tom's 2.4.2 apesar do fix 1.4.0.
2. Slot/free-slot count ainda diverge com Vault multiblock.
3. Mesmo vault é contado várias vezes.
4. Terminal mantém referência a inventory antigo após disassembly.
5. Contraption move e menu continua apontando para coordinates stale.
6. Crafting Terminal consome ingredients duas vezes em concorrência.
7. Dois clientes extraem o mesmo stack.
8. Reassembly não é executado em contraption criada antes do mod/update.
9. Unload/restart deixa sessão/menu órfão.
10. Atualização Tom's/Create altera interfaces esperadas pela bridge.

## 19. Matriz de testes
- [ ] Dedicated server inicia com bridge 1.4.0 + Tom's 2.4.2 + Create 6.0.10.
- [ ] Contraption nova com Storage Terminal acessa todos os inventories capturados.
- [ ] Crafting Terminal consome e entrega itens exatamente uma vez.
- [ ] Contraption antiga é desassemblada/reassembled e volta a funcionar.
- [ ] Vault multiblock conta capacidade/slots uma única vez.
- [ ] Slot count/free slot count acompanham insert/extract conforme fix 1.4.0.
- [ ] Movimento/chunk crossing não quebra o menu.
- [ ] Disassembly com GUI aberta invalida sessão sem dupe/loss.
- [ ] Dois players concorrentes não extraem/craftam o mesmo recurso duas vezes.
- [ ] Restart preserva inventory e permite novo acesso após reload.
- [ ] Tom's base continua funcionando fora de contraptions.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma JAR/runtime 1.4.0 e Tom's 2.4.2. CurseForge/README oficiais confirmam acesso ao inventário inteiro da contraption e necessidade de reassembly. O changelog oficial confirma Vault detector em 1.3.0 e, em 1.4.0, fixes para Tom's recente, mod version e slot/free-slot counts. Internals não necessários para essas guarantees permanecem fail-closed.

> 🔒 **Boundary canônico:** Create owns lifecycle/identidade da contraption; Tom's owns storage/terminal semantics; esta bridge conecta o terminal ao inventory capturado. Counts e crafts só são válidos quando derivados do state server-side atual da contraption.
