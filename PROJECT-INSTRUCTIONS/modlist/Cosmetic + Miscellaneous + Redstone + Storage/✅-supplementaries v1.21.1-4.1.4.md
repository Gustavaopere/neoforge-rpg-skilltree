# Supplementaries — 1.21.1-4.1.4 (versão declarada instalada em 22/11/2026)

> **Estado vigente — 22/11/2026.** `1.21.1-3.9.9` → **`1.21.1-4.1.4`**, JAR oficial `supplementaries-1.21.1-4.1.4-neoforge.jar`; atualização informada como instalada pelo usuário e registrada no GitHub. [Publicação CurseForge](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9724360). A instalação física não foi relida nem testada neste chat.

> **Requisitos e notas técnicas:** Cadeia 4.0.0–4.1.4 auditada nas seções existentes: Pigñata, Grouper, Whimsy/Carousel, Slate/Blackboard, Redstone Lantern, canhão com passageiro, flags 2D, remoção de armor trims por Soap, tags de recursos desligados; 4.1.3 lista fixes não detalhados e 4.1.4 remove um warning não especificado. Moonlight Lib/Amendments aguardam sincronização em seus lotes. Uma dependência que ainda conste como antiga no índice pertence a lote posterior; não impede esta atualização documental.


> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#536**: JAR `supplementaries-1.21.1-3.9.9-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.9`, SHA-1 `06ed05381a5653d90398dc6ebf3ad1154add6374`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Supplementaries
- **Arquivo JAR instalado (declaração do usuário):** `supplementaries-1.21.1-4.1.4-neoforge.jar` — arquivo oficial [CurseForge](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9724360).
- **Versão instalada declarada — Minecraft 1.21.1 / NeoForge:** `1.21.1-4.1.4` (anterior `1.21.1-3.9.9`).
- **Estado no pack:** `1.21.1-4.1.4` informada como instalada pelo usuário em 22/11/2026; sincronização documental no GitHub. O JAR local novo não foi lido diretamente.
- **Estado da pesquisa:** sequência de releases já auditada nas seções deste dossier; versão/filename alvo confirmados no CurseForge. Sem afirmar que o runtime foi testado.
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual, Automação
- **Função:** Grande expansão vanilla+ com storage/display, decoração, iluminação, ferramentas e mecanismos de redstone/automação como Jar, Faucet, Pulley, Cannon, Rope, Urn e Soap, amplamente configuráveis.
- **Dependências:** Moonlight Lib é obrigatória; pack instala Moonlight 1.21.1-3.6.3. Runtime Client & Server. Integrações opcionais/data-driven dependem do stack presente.
- **Sobreposição:** Sobreposição parcial com Amendments, Blocks You Need e outros vanilla+; Sable Physics Compat declara suporte a Supplementaries.
- **Compatibilidade/Riscos:** Catálogo amplo e feature gates encadeados. Testar Pulley com Sable/Create, Faucet/storage com capabilities, Cannon/claims, Urn/worldgen e resource packs. O runtime físico 3.9.9 preserva como regression gates os fixes publicados para primeiro pull do Pulley, Cannon Boat range, Flag renderer/config e Hourglass load.
- **Observações (versão vigente declarada):** `supplementaries-1.21.1-4.1.4-neoforge.jar`, `1.21.1-4.1.4`. Metadados antigos, SHA e trechos “não instalado” nas auditorias pretéritas são históricos, anteriores à declaração atual.
- **Procedência:** modlist física antiga usada para posição histórica; atualização declarada em 22/11/2026. [Distribuição oficial](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9724360); nome do JAR extraído da publicação oficial; nenhuma inspeção local do novo JAR.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/8852720
- **Atualização/Status:** 22/11/2026 — `1.21.1-3.9.9` → `1.21.1-4.1.4`, por pedido do usuário; header, nome de dossier e índice versionados. Cadeia 4.0.0–4.1.4 auditada nas seções existentes: Pigñata, Grouper, Whimsy/Carousel, Slate/Blackboard, Redstone Lantern, canhão com passageiro, flags 2D, remoção de armor trims por Soap, tags de recursos desligados; 4.1.3 lista fixes não detalhados e 4.1.4 remove um warning não especificado. Moonlight Lib/Amendments aguardam sincronização em seus lotes.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `supplementaries-1.21.1-3.9.9-neoforge.jar`, mod id `supplementaries`, versão `1.21.1-3.9.9`. Supplementaries é um grande **vanilla+ funcional/decorativo**, com mais de 200 blocos/itens descritos pelo projeto e configuração granular que permite desabilitar grande parte do catálogo individualmente.
## 1. Identidade, versão e papel
- **Mod:** Supplementaries.
- **JAR físico:** `supplementaries-1.21.1-3.9.9-neoforge.jar`.
- **Mod id:** `supplementaries`.
- **Versão:** `1.21.1-3.9.9`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Dependência principal:** Moonlight Lib; o pack possui `moonlight-1.21.1-3.6.3-neoforge.jar`.
- **Decisão:** Sem decisão; preservada.
## 2. Authority e ownership
Supplementaries é authority de seus próprios blocos, itens, entidades/projéteis e mecânicas vanilla+.
Moonlight fornece infraestrutura compartilhada; Amendments altera/expande comportamentos de conteúdo vanilla e não deve ser confundido com Supplementaries. Outros mods podem fornecer compats ou retexturas sem assumir ownership das mecânicas base.
## 3. Escopo funcional
O projeto organiza o conteúdo em três grandes famílias:
- **storage/display:** Sack, Safe/Key, Jar/Tinted Jar, Item Shelf, Book Pile, Crystal Display, Hat Stand, Quiver, Lunch Basket, presents e Urn;
- **redstone/utilidade/automação:** Bellows, Crank, Turn Table, Faucet, Pulley, Relayer, Speaker, Redstone Illuminator, Sliding Block, Cog Block, Clock, Spring Launcher, Pedestal e Wind Vane;
- **decoração/iluminação/ferramentas:** sconces/candelabras, Fire Pit, lamps, rope/bunting/awnings, signs, blackboards, planters, timber frames, doors/trapdoors, Cannon/Cannonball, Slingshot, bombs, Rope Arrow, Hourglass, Wrench, Flute, Soap, Antique Ink, mapas e outros utilitários.
A ficha não transforma a lista pública em contagem exata de registries sem source pin da release 3.9.8.
## 4. Jars e storage interativo
Jars/Tinted Jars armazenam diferentes conteúdos e são parte importante da identidade do mod. A documentação pública inclui uso com fluidos, cookies e pequenos mobs/objetos em contextos suportados.
Testes devem cobrir inserção/extração, quebra/recuperação, NBT/components persistentes e interoperabilidade com hoppers/pipes quando exposta.
## 5. Rope, pulley e estruturas móveis
Rope é infraestrutura interna para outras features. O source 1.21.1 mostra que desabilitar rope pode desabilitar também recursos dependentes como pulleys, buntings, rope arrows e compat blocks.
A configuração de Pulley possui modo de **continuous retraction**, no qual a cadeia retrai em vários ticks usando moving-piston entities; múltiplos pulleys podem cooperar para puxar uma estrutura ampla quando configurado.
No pack com Sable/Create, testar pulleys e estruturas móveis para evitar conflito de ownership físico.
## 6. Faucet e transferência
Faucet é mecanismo de transferência/derramamento. O source expõe opções como:
- permitir derrubar itens;
- permitir preencher inventários de entidades abaixo.
A semântica efetiva depende da config. Interações com tanks/containers de outros mods devem validar capability/transaction sem dupe/loss.
## 7. Cannon, cannon boats e projéteis
Supplementaries possui Cannon/Cannonball e Cannon Boat. Configs publicadas controlam fuse, cooldown, fire power, recoil, TNT behavior e break radius/power scaling.
A release física 3.9.9 publica o fix de **shoot range do cannon boat**, tornando este comportamento regression gate da build instalada.
## 8. Urns, geração e loot
Urn possui configuração própria, incluindo chance de gerar critter a partir de tag e opção de cave urns que requer world reload.
Isso cria duas superfícies distintas:
- bloco/loot em runtime;
- geração de mundo, que não deve ser avaliada em chunks antigos como se fosse regenerada automaticamente.
## 9. Soap e limpeza data-driven
Soap pode limpar blocos/itens e possui config para desabilitar cleaning em-world sem remover o item/recipes. O source 1.21.1 também possui blacklist e mapeamentos especiais de blocos a serem limpos.
Como o pack é grande, compat data-driven deve ser testada com blocos modded para evitar transformações indevidas.
## 10. Configuração e feature gates
A linha 1.21.1 usa config comum sincronizada baseada em Moonlight. Muitos features têm toggles independentes e subopções.
Consequências operacionais:
- “mod instalado” não significa que cada feature esteja habilitada;
- algumas opções exigem world reload;
- clients precisam receber regras sincronizadas do servidor;
- desabilitar um feature-pai pode afetar features dependentes.
A configuração física do pack não foi lida neste lote.
## 11. Release 3.9.9 e baseline 3.9.8
A 3.9.8 já registrava como regression gates:
- correção da animação do **Pulley no primeiro pull**;
- correção do **shoot range do Cannon Boat**;
- correção do renderer do item de **Flags** ligado à config de banners;
- alteração do método de load do **Hourglass**.
A página oficial da release física 3.9.9 também publica esses mesmos quatro itens em **What's new**. Portanto, no dossiê atual eles permanecem regression gates do runtime 3.9.9, sem inferir um delta funcional adicional não publicado entre 3.9.8 e 3.9.9.
## 12. Client / server e multiplayer
- Servidor: placement, inventories, transfers, redstone, projectile/damage e state de blocks/entities.
- Cliente: modelos, animações, GUIs e efeitos.
- Config COMMON_SYNCED precisa manter feature availability consistente.
- Dois jogadores interagindo com mesmo storage/mechanism não podem duplicar state.
## 13. Integrações concretas no pack
- **Moonlight Lib 3.6.3:** dependency física principal.
- **Amendments 2.1.10:** ecossistema próximo e sobreposição vanilla+ parcial; não é duplicação integral.
- **Sable Physics Compat:** metadata do pack registra suporte a Supplementaries; testar objetos móveis/physics suportados.
- **Create 6.0.10:** sobreposição parcial em mecanismos e automação; testar Faucet/Pulley/Cannon próximos a contraptions sem assumir integração universal.
- **Excalibur Supplementaries Support** e `Supplementaries Compat` estão catalogados separadamente; são camadas visual/data, não authority das mecânicas.
- **Subtle Effects:** integração visual explícita com Enderman Head de Supplementaries em feature de shader; não altera lógica do item.
## 14. Riscos técnicos
1. **Catálogo amplo:** maior superfície de recipe/tag/model/loot conflicts.
2. **Feature dependency graph:** desabilitar Rope ou outro parent pode desligar recursos relacionados.
3. **Moving structures:** Pulley/Sable/Create podem disputar assumptions de movimento/collision.
4. **Transfer duplication:** Faucet/storage com capabilities externas exige teste transacional.
5. **Projectile balance:** Cannon/Cannon Boat interagem com proteção, claims e outros sistemas de dano.
6. **Worldgen:** urns/flax e outros recursos gerados exigem seed/chunk novo para validação.
7. **HUD/model/config:** 3.9.9 publica o fix de renderer de flags; resource packs e Excalibur precisam regression visual.
8. **Performance:** muitos BlockEntities/animated mechanisms juntos podem elevar custo de render/tick.
## 15. Matriz de testes
- [ ] Dedicated server boot com Supplementaries 3.9.9 + Moonlight 3.6.3.
- [ ] Feature gates sincronizam e desabilitam apenas o escopo esperado.
- [ ] Jar/storage preserva conteúdo após break/relog/restart.
- [ ] Pulley anima corretamente já no primeiro pull.
- [ ] Continuous/cooperative pulley sem dupe/collision corruption.
- [ ] Faucet transfere/derrama sem dupe/loss em containers vanilla e modded.
- [ ] Cannon/Cannon Boat range, recoil, cooldown e projectile state em multiplayer.
- [ ] Urn/cave generation em chunk novo; world reload option aplicada corretamente.
- [ ] Soap não transforma blocos fora da regra configurada.
- [ ] Flag item renderer respeita config e resource pack Excalibur.
- [ ] Hourglass persiste state após save/restart.
- [ ] Sable/Create interaction em mecanismos móveis.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 16. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão, Moonlight e stack local.
- CurseForge oficial Supplementaries 3.9.9, File ID 8852720: release NeoForge 1.21.1 atual, ambiente e changelog.
- Página oficial do projeto: catálogo funcional e mais de 200 blocos/itens.
- Source oficial branch 1.21.1: `CommonConfigs`, feature gates e parâmetros de Rope/Pulley/Faucet/Cannon/Urn/Soap. O branch é usado para arquitetura; não é tratado como commit pin exato do binário 3.9.8.
## 17. Revalidação física — 11/09/2026
O JAR canônico continua sendo `supplementaries-1.21.1-3.9.8-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.8`, com Moonlight 3.6.3 presente. O dossiê 3.9.8 continua tecnicamente aplicável ao runtime físico.
**Atualização upstream:** a file list oficial agora contém `supplementaries-1.21.1-3.9.9-neoforge.jar`, publicada em 10/09/2026. Esta auditoria não altera a versão instalada nem atribui à 3.9.9 comportamento adicional além do que a publicação suporta. Nenhum teste/config migration foi executado.
## 18. Revalidação física e upstream — 13/09/2026
O runtime físico permanece Supplementaries 3.9.8. A release NeoForge 1.21.1 mais recente localizada é 3.9.9, publicada em 10/09/2026; o JAR do pack não foi alterado. O inventário físico também confirma Sable Companion 1.6.0 embarcado em `META-INF/jarjar`, subordinado ao host e sem ordinal próprio. Nenhum teste de atualização, config migration, Pulley, Cannon Boat, Flags ou Hourglass foi executado nesta revalidação.
## 19. Reconciliação física — 27/09/2026
A autoridade física atual passou a `supplementaries-1.21.1-3.9.9-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.9`, SHA-1 `06ed05381a5653d90398dc6ebf3ad1154add6374`. A publicação oficial NeoForge 1.21.1 é o File ID 8852720, de 10/09/2026. O changelog publicado para 3.9.9 mantém como deltas explícitos os fixes de primeiro pull do Pulley, alcance do Cannon Boat, renderer de Flags ligado à configuração de banners e método de load do Hourglass. `sable-companion-common-1.21.1-1.6.0.jar` permanece JarJar interno e não recebe ordinal top-level. Nenhum teste runtime ou config migration foi executado nesta reconciliação.

## Reauditoria de publicação e compatibilidade — 13/10/2026

**Projeto:** Supplementaries. **Última publicação oficial NeoForge 1.21.1 identificada:** `4.0.1`; artefato de referência: `supplementaries-1.21.1-4.0.1-neoforge`. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/all.

Linha pública NeoForge 1.21.1 entrou em 4.0.0 (11/10) e recebeu 4.0.1 (11/10). A passagem 3.9.9→4.0.1 pode alterar comportamento/persistência de blocos, automation/redstone, dynamic registration/resources e compat com Moonlight/Amendments: **não é segura por simples correspondência de Minecraft/loader**. Changelog granular 4.0.1 não isolado nesta rodada, por isso nenhuma mecânica inédita específica é inventada. Gate: coexistência Amendments, Moonlight 3.7.2, generated resource packs, redstone/hoppers, items/block entities, placements, config/server sync, recipes/datapacks e load de mundos com decoração/contraptions.

**Autoridade física:** estado histórico do JAR documentado neste dossiê e `modlist(1).txt` permanece intacto; novo dump do pack, hashes, metadata e testes runtime não foram fornecidos. **Decisão:** candidato upstream (ou revalidação sem release nova), não uma atualização instalada. **Filename:** preservar prefixo ✅ e versão física histórica no nome até evidência da instalação.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Supplementaries
- **Último candidato 1.21.1 / NeoForge localizado:** **4.0.2 (Release, 16/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Salto major: testar Moonlight, Amendments, Polytone, block entities/redstone/storage, recipes, generated resources, world migration e dedicated server.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/8852720

## Lote 21–30 — histórico cumulativo 3.9.9 → 4.0.2 (22/10/2026)

**Físico:** `1.21.1-3.9.9` NeoForge. **Linha nova:** 4.0.0 (file 9139628, 11/10), 4.0.1 (file 9140379, 11/10) e 4.0.2 (file 9211734, 16/10), todos com JAR **NeoForge 1.21.1 Release**. O changelog extenso de 4.0.0/4.0.1 repete recursos cumulativos; não presumir que foram todos introduzidos duas vezes.

| Release | Publicação e mudanças com impacto |
|---|---|
| 4.0.0 | Introduz **Pigñata** pendurável com leash, bloco redstone **Grouper** para comparar blocos/entidades dos lados e emitir sinal, jogador pode entrar em cannon usando sneak+empty hand, blackboards 3×3 editáveis em GUI, leash amarrável em ropes, sete melodias de flute, sons wind vane/plunderer, revisão de naval raids e compatibilidade Polytone para **cores de fluidos**. |
| 4.0.1 | Changelog repete a maior parte do elenco da 4.0.0 e declara compat com **Polytone/Polylight para luzes coloridas**, sem isolar claramente outras correções entre os dois JARs. Não inferir que o 4.0.1 adiciona novamente Pigñata/Grouper. |
| 4.0.2 | Amplia a integração com **Polytone colored lights**; corrige **dupe com pulley**. Este fix afeta diretamente stacks persistentes e logística de rope/pulley. |

**QA de migração:** blocos/redstone/GUI novos, Grouper em circuitos, cannon/chunk travel, ropes/pulley com inventários e duplicação, blackboard multiblock persistente, naval raid/AI, Amendments + Moonlight generated resource pack + Polytone, sinal de luz/shaders, multiplayer e servidor dedicado. **Versão física 3.9.9 preservada.** Sem inspeção do JAR, testes ou aprovação de mudança major.

**Fontes (artefatos NeoForge exatos):** https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9139628 ; https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9140379 ; https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9211734 .


## Auditoria de releases NeoForge 1.21.1 — 4.0.2 → 4.1.2 (17/11/2026)

**Autoridade física preservada:** Supplementaries na posição **#536** da modlist instalada, arquivo top-level `supplementaries-1.21.1-3.9.9-neoforge.jar`, versão efetiva **1.21.1-3.9.9**, SHA-1 físico anteriormente conciliado `06ed05381a5653d90398dc6ebf3ad1154add6374`. A versão **4.1.2**, publicada em **15/11/2026**, é **candidata upstream**, não está instalada e não foi homologada em runtime.

**Projeto oficial:** [Supplementaries, CurseForge 412082](https://www.curseforge.com/minecraft/mc-mods/supplementaries), autor MehVahdJukaar. Categorias temáticas atuais confirmadas diretamente na página de release: **Cosmetic + Miscellaneous + Redstone + Storage**; diretório já correto, sem necessidade de movimentação ou renomeação.

### Sequência integral após 3.9.9, com rastreabilidade

| Release NeoForge 1.21.1 | Data oficial | File ID / fonte | Resultado da revisão individual |
|---|---|---|---|
| **4.0.0** | 11/10/2026 | [9139628](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9139628) | Já auditada no intervalo anterior; introduz Pigñata, Grouper, entrada de jogador em cannon, blackboard 3×3 e outras mecânicas |
| **4.0.1** | 11/10/2026 | [9140379](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9140379) | Já auditada; nota majoritariamente cumulativa, inclui compatibilidade Polytone/Polylight; não reapresentar recursos 4.0.0 como inéditos |
| **4.0.2** | 16/10/2026 | [9211734](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9211734) | Já auditada; mais integração de luzes coloridas Polytone e **correção de duplicação por Pulley** |
| **4.1.0** | 13/11/2026 | [9597653](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9597653) | Página oficial individual examinada; publica o conjunto de recursos 4.1 descrito abaixo |
| **4.1.1** | 13/11/2026 | [9600719](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9600719) | Página oficial individual examinada; **repete literalmente o mesmo elenco de novidades de 4.1.0**, sem delta incremental isolado no changelog |
| **4.1.2** | 15/11/2026 | [9632829](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9632829) | Página oficial individual examinada; repete o elenco 4.1.0/4.1.1 e acrescenta **correção de pistões explicitamente Fabric**, **não aplicável automaticamente ao NeoForge** |

**Exaustividade do catálogo:** o índice oficial CurseForge para [Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/all?version=1.21.1) e a listagem geral atual apresentam, entre **4.0.2 e 4.1.2**, exatamente as publicações **4.1.0 → 4.1.1 → 4.1.2** no loader NeoForge; não há na listagem atual uma **4.0.3–4.0.x** publicada para este alvo. As seis releases pós-3.9.9 foram tratadas individualmente neste dossiê e na auditoria precedente. A conclusão é do **catálogo documental público verificado**, não prova matemática de inexistência de algum arquivo privado ou retirado.

### Mudanças funcionais relevantes da série 4.1 (notas oficiais cumulativas)

- **Redstone Lantern** adicionada; exige revisar sinais, iluminação e conflito de recipes quando presente no mundo.
- **Cannons** recebem animação própria ao disparar e melhorias; jogador disparado pode **atingir e causar dano a entidades atravessadas**; **Pigñatas** podem ser arremessadas; novo **advancement do cannon**. Testar dano, permissões/claims e interação em servidor, inclusive com efeitos de combate.
- Novos encantamentos **Whimsy** e **Carousel** para **Quivers** e **Lunch Baskets**; verificar disponibilidade, balanceamento e interações com o sistema de enchantments do pack.
- Integração condicional do efeito **Slimed** com **mud balls do Environmental** (somente se a integração correspondente estiver presente); quando *vanilla backport* está ativo, todos os **Copper Sconces** passam a ser registrados.
- **Soap** passa a remover **armor trims**, alteração destrutiva de cosmético que pede teste de intenção/consumo do item e preservação de outros componentes.
- Nova opção de configuração para renderização **2D de Flags**; **Ash** deixa de apagar fogo ao pousar; **tags dos recursos desabilitados** passam a ser desativadas junto ao respectivo toggle. Essa última mudança pode afetar receitas, tags e datapacks que dependam dos itens/blocos desligados.
- O autor menciona várias correções menores sem especificá-las; **não deduzir classes, bugs ou efeito prático não publicados**. O changelog 4.1.2 menciona um *piston fix* **Fabric**, que não deve ser vendido como fix comprovado no NeoForge.

**Precisão entre releases:** as notas 4.1.0 e 4.1.1 repetem a mesma lista, e 4.1.2 apenas a estende com um item explicitamente Fabric; por isso é **impossível atribuir mudanças de código exclusivas à transição 4.1.0→4.1.1 ou 4.1.1→4.1.2 sem comparação dos JARs/source**. O resultado verificável é a **revisão completa de cada publicação e seus changelogs autorais**, não a afirmação de ausência comprovada de alterações internas. Essa limitação não impede o registro documental das novidades acumuladas.

### Matriz de homologação para futura atualização física

- [ ] Verificar atualização concomitante de **Moonlight Lib** e **Amendments** à versão efetivamente exigida pela release candidata, sem presumir que a dependência histórica 3.6.3 é compatível.
- [ ] Validar backups e carregamento de mundos com Pigñata, Grouper, Blackboard, Redstone Lantern, Pulley, Cannon e inventários; conferir IDs, recipes e sincronização.
- [ ] Ensaiar damage/proteção/claims do **cannon com passageiro** e Pigñata em servidor, evitando bypass de PvP e de proteções.
- [ ] Verificar **Whimsy/Carousel**, remoção de trims por Soap, sinal redstone, Flag 2D, Ash e os feature toggles que retiram tags.
- [ ] Regressões com **Polytone** (luzes coloridas), **Create/Sable** (contraptions, Pulley), render/resource packs e usuário multiplayer.
- [ ] Conferir JAR candidato, metadata e SHA e executar smoke client/server antes de promover a versão instalada no dossiê/índice.

**Estado documental:** auditoria das **seis releases posteriores à instalação 3.9.9 até a candidata 4.1.2** concluída com limites explícitos de notas cumulativas. **Não significa upgrade instalado nem testes aprovados.** O basename `✅-supplementaries v1.21.1-3.9.9.md`, o JAR físico e a categoria real permanecem inalterados.

## Auditoria incremental upstream — Supplementaries 4.1.3 → 4.1.4 (22/11/2026; não instaladas)

A baseline física continua #536 `supplementaries-1.21.1-3.9.9-neoforge.jar` / `1.21.1-3.9.9`. As seis releases entre **3.9.9 e 4.1.2** já foram individualmente revisadas nas seções anteriores; **não repetir** seus changelogs cumulativos como se fossem novidades desta revisão.

- **4.1.3 — 18/11/2026**, [arquivo oficial CurseForge 9687485, NeoForge/Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9687485), `supplementaries-1.21.1-4.1.3-neoforge.jar`. O changelog público discrimina apenas **“fixes”**, sem nomear comportamento, classe, bug, impacto ou mod específico. **Não** é possível concluir quais bugs foram corrigidos ou assumir apenas bugs sem efeito em gameplay; falta fonte mais detalhada ou diff binário/source pinado.
- **4.1.4 — 20/11/2026**, [arquivo oficial CurseForge 9724360, NeoForge/Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9724360), `supplementaries-1.21.1-4.1.4-neoforge.jar`. A nota específica é **“removed a warn”**. Sem localização/mensagem do warning e diff versionado, não inferir correção de crash, alteração de gameplay, configuração ou remoção de feature.

A cronologia pública pós-4.1.2 para o alvo contém **4.1.3 e 4.1.4**, ambas registradas sem inventar deltas ocultos. **QA/upgrade condicionado** à compatibilidade atual de Moonlight Lib/Amendments, especialmente dados de itens/blocos e world saves do Supplementaries 4.1, tags, Soap/removal de trims, canhões e sinais redstone descritos anteriormente. Não houve download/diff dos JARs 4.1.3/4.1.4, instalação ou teste. **Baseline e categorias oficiais preservadas.**

## Sincronização da versão instalada declarada — 22/11/2026

**Versão anterior:** `1.21.1-3.9.9`.

**Versão vigente declarada:** `1.21.1-4.1.4`; artefato oficial `supplementaries-1.21.1-4.1.4-neoforge.jar`.

**Releases e mudanças:** Cadeia 4.0.0–4.1.4 auditada nas seções existentes: Pigñata, Grouper, Whimsy/Carousel, Slate/Blackboard, Redstone Lantern, canhão com passageiro, flags 2D, remoção de armor trims por Soap, tags de recursos desligados; 4.1.3 lista fixes não detalhados e 4.1.4 remove um warning não especificado. Moonlight Lib/Amendments aguardam sincronização em seus lotes.

**Escopo e fonte:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/supplementaries/files/9724360). A confirmação de instalação provém da declaração do usuário, não de leitura do diretório local. Seções anteriores sobre versões “não instaladas” retratam apenas o snapshot precedente; não afirmar QA de cliente/servidor.
