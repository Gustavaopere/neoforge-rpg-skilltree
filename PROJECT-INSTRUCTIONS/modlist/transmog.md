# Transmog

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db81099952f7728d84657c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `transmog-neoforge-1.6.0+1.21.1.jar`, mod id `transmog`, runtime `1.6.0`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Transmog 1.6.0 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Transmog
- **Arquivo JAR:** `transmog-neoforge-1.6.0+1.21.1.jar`
- **Versão 1.21.1:** 1.6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** RPG, Visual, QoL
- **Função:** Sistema cosmético de transmogrification: altera a aparência renderizada de armor, weapons, tools e outros itens sem modificar o comportamento/stats do item alvo, usando Transmogrification Table e Void Fragment.
- **Dependências:** NeoForge 1.21.1. A release 1.6.0 não lista dependências externas obrigatórias. Compatibilidade visual precisa ser testada com renderers/mods do pack, especialmente Epic Fight e equipamentos customizados.
- **Sobreposição:** Pode sobrepor UX/render com outros sistemas cosméticos, mas não substitui itemização ou atributos. Conflito deve ser tratado no renderer/preservação de metadata.
- **Compatibilidade/Riscos:** Renderer mismatch com modelos 3D/custom, perda do data cosmético em repair/smithing/crafting, multiplayer visual desync e risco de behavior leakage se outro mod usar aparência em vez do item real. Void Fragment torna item invisível apenas visualmente.
- **Observações:** mod id `transmog`; runtime 1.6.0 Release específica de NeoForge 1.21.1. Decisão Manter preservada. Appearance item não é consumido; target mantém comportamento/stats.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficiais Transmog 1.6.0 NeoForge 1.21.1 + source oficial Hidoni/Transmog. Decisão Manter preservada; nenhum teste visual, de persistence ou multiplayer foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/transmog/files/6010596 ; https://github.com/Hidoni/Transmog
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Transmog 1.6.0 permanece exatamente instalado; Table/Void Fragment, appearance ownership, modded renderers/Epic Fight, persistence, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `transmog-neoforge-1.6.0+1.21.1.jar`, mod id `transmog`, versão `1.6.0`. Transmog separa **aparência** de **comportamento/stats**: o item alvo continua sendo o item real para gameplay, mas pode renderizar como outro item — ou ficar invisível — por meio da Transmogrification Table.

## 1. Identidade, versão e decisão
- **Mod:** Transmog.
- **JAR:** `transmog-neoforge-1.6.0+1.21.1.jar`.
- **Mod id:** `transmog`.
- **Versão:** `1.6.0`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal:** Release.
- **Release exata:** 21/12/2024; changelog: melhorias adicionais de compatibilidade com outros mods.
- **Decisão vigente:** **Manter**; preservada.
- **Dependências externas obrigatórias listadas:** nenhuma na página da release 1.6.0.

## 2. Authority e ownership
Transmog é authority somente da **aparência cosmética associada ao item**. O item alvo continua sendo authority de:
- atributos;
- dano;
- durabilidade;
- enchantments;
- capabilities/components de gameplay;
- ações de uso;
- requisitos de equipamento.

Assim, uma espada visualmente transformada em outro objeto deve continuar se comportando como a espada real. Sistemas de combate não devem usar aparência para decidir stats.

## 3. Transmogrification Table
O bloco principal é a **Transmogrification Table**. Sua UI oficial possui três slots:
1. **Fuel:** Amethyst Shards carregam a mesa; cada ação consome um terço da carga.
2. **Target:** item cujo visual será alterado.
3. **Appearance:** item que define a aparência e **não é consumido** no processo.

Essa separação entre target e appearance é central para evitar perda do item cosmético e preservar o item funcional.

## 4. Void Fragment
O **Void Fragment** tem duas funções publicadas:
- é necessário para craftar a Transmogrification Table;
- colocado no Appearance Slot, torna o item alvo **visualmente oculto/invisível**.

Invisibilidade visual não deve remover hitbox, equipamento, stats ou existência lógica do item.

## 5. Aplicação de aparência
Para a maioria dos itens, o conteúdo do Appearance Slot é usado diretamente como aparência do Target.

O projeto afirma compatibilidade ampla com itens modded e suporte não restrito a armor: weapons, tools e outros itens também podem ser transmogrificados.

Isso cria grande superfície de renderer compatibility no pack; “suportado em princípio” não elimina necessidade de teste para modelos customizados.

## 6. Transmog encadeado
Se o Appearance Slot recebe um item **já transmogrificado**, o target herda a transmogrification existente, e não simplesmente a aparência original daquele item.

Testar encadeamento para evitar ciclos, perda de component cosmético ou crescimento indevido de NBT/data components.

## 7. Remoção do transmog
Para remover a transmogrification, o item alvo é colocado na mesa **sem item de aparência**.

A operação deve restaurar a renderização nativa sem alterar stats/components do target.

## 8. Consumo de combustível
Cada Amethyst Shard fornece carga para ações da mesa e cada transmog consome uma fração definida pelo upstream como um terço.

Testar atomicidade: se a operação falhar por item incompatível/state inválido, combustível e target não devem ser consumidos parcialmente de forma incorreta.

## 9. Compatibilidade com itens modded
O projeto declara “fully compatible with items from mods”, mas 1.6.0 também traz changelog genérico de melhoria de compatibilidade, indicando que rendering modded é superfície real de bugs.

No pack, regression tests prioritários incluem:
- armaduras com modelos 3D/custom;
- weapons com animação Epic Fight;
- Curios/slots especiais quando o item participa de rendering próprio;
- items com custom model data/data components;
- first-person e third-person renderers.

Não assumir que toda geometria externa respeitará transmog sem teste.

## 10. Epic Fight e combate
Epic Fight `21.17.3.1` está presente. Transmog deve alterar apenas o visual, enquanto Epic Fight continua usando weapon category/capability/animation do **item real**.

Testar especialmente aparência de arma pertencente a categoria diferente para confirmar que animação, alcance e attack behavior não migram junto com o visual.

## 11. Armor e equipamentos
Para armor, o risco é discrepância entre slot lógico e modelo exibido. Validar:
- armor points/enchantments permanecem do target;
- aparência corresponde ao appearance item;
- custom armor models não desaparecem ou duplicam layers;
- armor stands e outros render contexts exibem state consistente.

## 12. Client / server e multiplayer
O servidor precisa preservar o item real e o data state de transmog. Clientes renderizam a aparência associada.

Em multiplayer:
- outro jogador deve ver a mesma aparência;
- reconnect deve preservar transmog;
- trocar item de slot/dropar/armazenar não deve remover o visual;
- cliente sem state atualizado não pode alterar o item real.

## 13. Lifecycle e persistência
Validar:
- aplicar transmog;
- inventory move/drop/pickup;
- container storage;
- equip/unequip;
- death/recovery via Corail Tombstone;
- relog/server restart;
- repair/enchant/anvil/smithing quando permitido;
- cloning/crafting paths que copiem data components;
- remover transmog depois de múltiplas operações.

## 14. Riscos técnicos
1. **Renderer mismatch:** custom 3D models e animation systems podem ignorar/substituir aparência.
2. **Behavior leakage:** nenhum stat/comportamento do appearance item deve migrar.
3. **Component loss:** repair/smithing/crafting pode descartar data cosmético se outro mod reescrever stack.
4. **Hidden item ambiguity:** Void Fragment pode ocultar equipamento importante visualmente sem removê-lo logicamente.
5. **Multiplayer desync:** aparência precisa sincronizar sem cliente alterar gameplay state.
6. **Cross-mod rendering:** Epic Fight, armor layers, Curios e resource packs precisam regression visual.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Transmog 1.6.0.
- [ ] Amethyst carrega a mesa e consumo por ação é coerente.
- [ ] Target mantém stats/durability/enchantments após transmog.
- [ ] Appearance item não é consumido.
- [ ] Void Fragment oculta visual sem remover funcionalidade.
- [ ] Item já transmogrificado transfere sua aparência conforme upstream.
- [ ] Remover transmog restaura render nativo.
- [ ] Arma Epic Fight mantém categoria/animação do target real.
- [ ] Armor custom/modded renderiza sem layer duplicada ou missing model.
- [ ] Drop/container/relog/restart preserva state.
- [ ] Corail Tombstone recupera item transmogrificado sem perder aparência.
- [ ] Outro cliente vê a mesma aparência no multiplayer.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 16. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e stack de rendering/combat.
- CurseForge/Modrinth oficiais Transmog: release NeoForge 1.6.0 para 1.21.1, Client & Server e objetivo de alterar aparência sem comportamento/stats.
- README/source oficial Hidoni/Transmog: Void Fragment, Transmogrification Table, Amethyst fuel, Target/Appearance slots, appearance não consumida, invisibilidade, transmog encadeado e remoção.
- CurseForge da release 1.6.0 não lista dependência externa obrigatória.

## 17. Revalidação física — 11/09/2026
A modlist física continua contendo `transmog-neoforge-1.6.0+1.21.1.jar`, mod id `transmog`, versão `1.6.0`. A release NeoForge 1.21.1 permanece 1.6.0 e a decisão **Manter** continua preservada.

A separação entre appearance e gameplay state continua sendo o contrato operacional central. Nenhum teste com Epic Fight, armaduras 3D, Void Fragment, smithing/repair, Corail Tombstone ou sincronização multiplayer foi executado nesta recatalogação.
