# Create: Ender Transmission

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81178b40e5a47eabad92
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Ender Transmission
- **Arquivo JAR:** `createendertransmission-2.1.1-1.21.1.jar`
- **Versão 1.21.1:** 2.1.1-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação, Armazenamento, Compat
- **Função:** Adiciona transmissão de energia, itens e fluidos sem limite prático de distância, inclusive entre dimensões, além de chunk loader alimentado por potência cinética.
- **Dependências:** Create obrigatório; pack físico usa Create 6.0.10. Publicação oficial é NeoForge 1.21.1 Client & Server. Source matching não foi localizado nesta auditoria.
- **Sobreposição:** Relacionado a outras logísticas ender/wireless, mas adiciona explicitamente transmissão de energia, itens, fluidos e chunk loading cinético. Equivalência exige cobertura concreta desses quatro domínios.
- **Compatibilidade/Riscos:** Riscos: remote item/fluid/energy dupe/loss; endpoint stale; cross-dimension restart; orphan chunk tickets; kinetic-cost bypass; concurrent transfer; claims/permission bypass; Create API drift.
- **Observações:** JAR `createendertransmission-2.1.1-1.21.1.jar`, mod id `createendertransmission`, runtime 2.1.1-1.21.1. Changelog 2.1.1 cita fixes #7/#9 via PR #23 sem detalhe causal suficiente; causa permanece fail-closed.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + publicação/changelog oficiais Create: Ender Transmission 2.1.1. Internals não pinados por source matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-ender-transmission
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.1.1 com Energy/Item/Fluid Transmitters, cross-dimension endpoint authority, kinetic Chunk Loader, conservation, ticket lifecycle e fail-closed fixes #7/#9 catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> 🟣 **Identidade física confirmada:** `createendertransmission-2.1.1-1.21.1.jar`, mod id `createendertransmission`, runtime `2.1.1-1.21.1`. A publicação oficial corresponde à linha 2.1.1 para NeoForge 1.21.1, Client & Server. Source matching não foi localizado nesta auditoria; internals permanecem fail-closed.

## 1. Papel e authority
Create: Ender Transmission adiciona transporte remoto de **energia, itens e fluidos** e um **Chunk Loader** alimentado por potência cinética. O addon owns seus endpoints, vínculo remoto e ticketing de chunk; Create continua owner de RPM/stress e os providers de energia/item/fluid continuam owners do conteúdo transferido.

## 2. Energy Transmitter
O projeto publica um **Energy Transmitter** para mover energia remotamente. A unidade/capability concreta deve seguir o provider runtime; esta ficha não assume taxa, buffer ou API FE específica além do que o bloco realmente expõe.
Toda transferência precisa debitar origem e creditar destino exatamente uma vez.

## 3. Item Transmitter
O **Item Transmitter** move stacks entre endpoints sem depender de distância prática. Inserção parcial, destino cheio, stack com data components e concorrência precisam respeitar o inventory handler real e devolver remainder corretamente.

## 4. Fluid Transmitter
O **Fluid Transmitter** move fluidos remotamente. Tipo, amount e components precisam permanecer idênticos; destino incompatível ou cheio deve rejeitar transfer sem apagar ou duplicar fluido.

## 5. Distância e dimensões
A documentação afirma que distância e dimensão não impedem as transmissões. Isso torna identidade do endpoint mais importante que proximidade/chunk local. O servidor precisa resolver o par correto e impedir vínculos stale após remoção/recriação de bloco.

## 6. Chunk Loader cinético
O **Chunk Loader** mantém chunks carregados usando potência cinética Create. Criar/remover ticket precisa depender do estado válido da máquina e ser limpo em break, unload, dimension change e shutdown.
Sem energia cinética válida, o comportamento deve seguir a implementação do provider; não inventar duração de grace period.

## 7. Conservation entre chunks
Item/fluid/energy remote transfer cruza boundaries de chunk/dimensão. Uma operação interrompida por unload/restart não pode ser reaplicada integralmente na retomada se já debitou a origem. Idempotência/conservation são gates principais.

## 8. Endpoint lifecycle
Break, wrench/move, schematic replacement e mudança de configuração podem invalidar um endpoint. Referências persistentes devem ser revalidadas antes de cada operação para evitar envio a posição reutilizada por outro bloco.

## 9. Chunk ticket cleanup
Chunk loading é risco de performance e leak. Remover o loader, parar o servidor ou invalidar sua fonte de potência deve liberar tickets conforme o provider. Ticket órfão mantém região carregada indefinidamente e mascara bugs de lifecycle.

## 10. Multiplayer e ownership
Em servidor multiplayer, dois jogadores podem interagir com endpoints ao mesmo tempo. O addon não deve contornar claims/permissões de providers externos apenas porque a transferência é remota. A política exata de acesso fica sob os mods de proteção instalados.

## 11. Relação com outras logísticas remotas
O pack possui outras soluções ender/wireless/logísticas. Create: Ender Transmission só é redundante quando outro sistema cobre concretamente os mesmos três domínios e chunk loading com custo/política equivalente. Nome “Ender” por si só não prova substituição.

## 12. Create kinetic authority
Se transmissores/loader exigem potência Create, stress network e speed vêm do Create. O addon não deve gerar potência nem manter funcionamento funcional apenas por animação local de eixo.

## 13. Delta 2.1.1
O changelog público da build 2.1.1 registra correções para issues #7 e #9 via PR #23, mas a publicação consultada não detalha suficientemente a causa. Portanto nenhum comportamento específico é atribuído a esses fixes sem source/issue evidence matching.

## 14. Client/server
Transferência, endpoint identity, capability mutation e chunk tickets são server-authoritative. Animações/GUI são client-facing. Cliente não pode solicitar quantidade superior à validada pelo handler de origem/destino.

## 15. Restart/dimension lifecycle
Restart com endpoint ativo, destino em outra dimensão e loader mantendo chunk são cenários obrigatórios. Ao reiniciar, referências precisam ser reconstruídas sem transfer fantasma e sem ticket duplicado.

## 16. Riscos
1. Item transfer debitado/creditado duas vezes em unload.
2. Fluid amount perdido em destino parcialmente cheio.
3. Energy transmitter cria energia por race/retry.
4. Endpoint stale aponta para bloco novo na mesma posição.
5. Cross-dimension link não reconecta após restart.
6. Chunk loader mantém ticket órfão.
7. Chunk loader opera sem custo cinético esperado.
8. Dois endpoints/processos disputam o mesmo inventory sem atomicidade.
9. Claims/permissões são bypassados por acesso remoto.
10. Update Create altera kinetic capability esperada.
11. Fixes #7/#9 são descritos incorretamente sem evidence.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Ender Transmission 2.1.1-1.21.1 + Create 6.0.10.
- [ ] Item transfer conserva stacks e data components.
- [ ] Destino de item cheio devolve remainder sem loss/dupe.
- [ ] Fluid transfer conserva tipo/amount.
- [ ] Energy transfer conserva saldo do provider.
- [ ] Cross-dimension transfer funciona após restart.
- [ ] Break/recreate de endpoint invalida link antigo com segurança.
- [ ] Chunk Loader cria e remove tickets corretamente.
- [ ] Remover potência cinética não deixa funcionamento/ticket stale.
- [ ] Unload/reload no meio de transferência não duplica operação.
- [ ] Multiplayer concorrente mantém conservation.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
A modlist física confirma JAR/mod id/runtime. A publicação oficial confirma Energy/Item/Fluid Transmitters, Chunk Loader cinético e transmissão sem limitação prática de distância/dimensão. O changelog 2.1.1 confirma fixes #7/#9, mas não sua causa. Sem source matching, algoritmos de pairing, taxas, buffers e serialização ficam fail-closed.

> 🔒 **Boundary canônico:** Ender Transmission owns o vínculo remoto e chunk loading; inventories, fluids, energia e kinetics continuam sob seus providers. Toda transferência deve ser conservativa e todo ticket deve possuir lifecycle explícito.
