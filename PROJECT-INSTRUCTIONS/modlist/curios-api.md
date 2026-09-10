# Curios API

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81d2af10e886c0c99344  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Curios API
- **Arquivo JAR:** `curios-neoforge-9.5.1+1.21.1.jar`
- **Versão 1.21.1:** `9.5.1+1.21.1`
- **Categoria:** Biblioteca; RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/curios/files/6529130
- **Função:** API flexível de acessórios/equipamentos que permite a mods consumidores definir e usar slots adicionais, consultar itens equipados e integrar regras de equip/unequip sem substituir os slots vanilla por um inventário paralelo próprio do Curios.
- **Dependências:** NeoForge 1.21.1. Curios é infraestrutura Client & Server e sua necessidade no pack é transversal: vários mods e bridges a consomem. Slots concretos, regras e efeitos pertencem aos consumers, não ao Curios por padrão.
- **Compatibilidade/Riscos:** Dependência transversal e stateful de equipamentos. Riscos: version drift com consumers, slot definitions divergentes após datapack/config reload, equip/unequip hooks duplicados, sync client/server e world-upgrade migrations. A 9.5.1 corrige crash ao carregar mundo após update vindo de 9.4.2+1.21.1 ou inferior.
- **Sobreposição:** Não substitui os equipamentos dos mods consumidores nem Curios-style systems sem port explícito. Pode coexistir com outros slot APIs; conflito real exige dois providers disputando o mesmo equipamento/state ou bridge duplicando eventos.
- **Observações:** mod id `curios`; runtime 9.5.1+1.21.1. Curios é API/library Client & Server. A release 9.5.1 corrige crash em world load após atualizar de 9.4.2+1.21.1 ou abaixo (#520). Não hardcodar uma lista universal de slots: consumers definem a topologia aplicável.
- **Procedência:** Modlist física canônica de 08/09/2026, 595 top-levels + runtime 9.5.1+1.21.1 + CurseForge oficial Curios API File ID 6529130 e changelog 9.5.1.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Curios API 9.5.1+1.21.1 foi reconfirmado como `Instalado` na modlist física de 595; o dossiê Alex já aplicado foi preservado e necessidade técnica por consumers não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — slot/equipment authority, consumer-defined topology, equip/unequip/query contracts, sync/lifecycle, migration e regressão 9.5.1 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 💍 Versão física confirmada: `curios-neoforge-9.5.1+1.21.1.jar`, mod id `curios`, runtime `9.5.1+1.21.1`, NeoForge 1.21.1. Curios é uma **API de acessórios/equipamentos**, não um provider de progressão próprio.

## 1. Papel e authority
Curios oferece infraestrutura para mods consumidores criarem/consultarem equipamentos em slots adicionais. O consumer define o item, efeito, regra de equipagem e semântica final; Curios fornece o contract comum de armazenamento/consulta/eventos.

Não atribuir ao Curios stats, cooldowns ou progressão de um anel/charm/back item que pertencem ao provider do item.

## 2. Topologia de slots consumer-defined
A API é flexível/expansível: **não existe uma lista universal obrigatória de slots** válida para todo pack. Consumers e data/config podem expor os tipos necessários.

Integrações próprias devem descobrir a topologia efetiva do runtime em vez de hardcodar `ring`, `necklace`, `back` ou qualquer nome por convenção.

## 3. Equip/unequip
Equipar e remover acessórios é state de inventário/equipamento que precisa settlement uma única vez no servidor/common. Consumers podem reagir a transitions; listeners externos não devem reaplicar o mesmo atributo/efeito após um evento já processado pelo provider.

Retry, reconnect ou slot rebuild não pode duplicar item nem efeito.

## 4. Consulta de acessórios
Mods podem consultar itens equipados via Curios para aplicar comportamento próprio. A consulta é infraestrutura; a decisão de que um item concede voo, mana, armor, spell bonus ou outra feature pertence ao consumer.

Caching externo precisa ser invalidado em equip/unequip, death/clone, dimension transition e mudanças de slot topology.

## 5. Atributos e efeitos
Acessórios frequentemente adicionam modifiers ou effects. Curios organiza o contexto de equipagem, mas o modifier final deve ser registrado/removido pelo consumer exatamente uma vez.

Não contar um cosmetic slot ou cópia renderizada como segundo equipamento funcional.

## 6. Sync client/server
Inventário/slot state que afeta gameplay deve ser coerente com o servidor. UI/render do slot é apresentação.

Em multiplayer, cliente não pode fabricar um accessory state apenas alterando GUI local; server state precisa convergir para todos os observers/consumers relevantes.

## 7. Datapack/config lifecycle
Topologia/regras de slots podem depender da configuração e do stack do pack. Reload deve reconstruir o que a versão/consumer suportar sem perder itens ou manter slots stale.

Qualquer remoção/renomeação de slot em pack existente deve ser testada em cópia do mundo antes de produção.

## 8. Upgrade migration — 9.5.1
O changelog exato da build física corrige **crash no world load após atualizar de `9.4.2+1.21.1` ou inferior**.

Esse é o regression gate principal da 9.5.1: mundos com state Curios anterior precisam carregar sem crash e preservar inventário/equipamentos conforme o contract da migração.

## 9. Consumers concretos do pack
O catálogo já contém integrações/consumers que dependem ou usam Curios, como Artifacts e compatibilidades específicas de outros providers. Essas presenças demonstram a transversalidade da API, mas não autorizam inferir que todo mod RPG use Curios.

Dependency graph e metadata de cada consumer continuam sendo a prova de necessidade.

## 10. Coexistência com outros slot systems
Outro mod pode possuir slots funcionais próprios. Coexistência não é duplicação automática.

Conflito real ocorre quando dois systems tentam armazenar/sincronizar o mesmo item ou disparar os mesmos effects como se fossem o mesmo slot. Bridges devem escolher uma authority e adaptar, não espelhar indefinidamente em ambos.

## 11. Client/server
Curios é Client & Server. Inventory/equip state e effects de gameplay são common/server; screen, icons e render são client-side.

Dedicated server não deve depender de classes gráficas para registrar/consultar slots.

## 12. Lifecycle
Validar player login, equip/unequip, death/respawn, dimension change, disconnect/reconnect, world restart, datapack/config reload e update de versão.

Itens equipados não podem sumir, duplicar ou reaparecer em slot antigo após topology migration.

## 13. Riscos
1. Remover Curios com consumer ativo.
2. Hardcodar slot que não existe no runtime.
3. Double modifier/effect por dois listeners.
4. Item perdido ao remover/renomear slot.
5. Client UI divergir do inventory server-side.
6. Cache externo stale após equip/reload.
7. Update de versão quebrar world migration.
8. Outro slot API ser tratado como substituto binário.

## 14. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join e abertura de interface Curios/consumer.
3. Equip/unequip de acessórios de diferentes providers.
4. Verificar modifiers/effects exatamente uma vez.
5. Death/respawn com accessories equipados.
6. Dimension change/reconnect/restart preservando state.
7. Reload de datapack/config com topology estável.
8. Cópia de mundo de versão Curios <=9.4.2 → 9.5.1, regression #520.
9. Multiplayer: dois jogadores com loadouts distintos sem state leak.
10. Coexistência com outro slot system/bridge sem dupe.

## 15. Evidência
- modlist física atual: Curios API 9.5.1+1.21.1;
- CurseForge oficial: flexible/expandable accessory/equipment API, Client & Server;
- release/File ID 6529130 para NeoForge 1.21.1;
- changelog 9.5.1: fix de crash no world load ao atualizar de 9.4.2+1.21.1 ou inferior.

> 🔗 Boundary canônico: **Curios organiza slots/equipamento; consumers definem os itens, regras e efeitos**. A topologia deve ser descoberta do runtime, não inventada.
