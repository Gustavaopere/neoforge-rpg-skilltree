# Create Aeronautics: Weight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81209916c62c8845a3ff
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Weight
- **Arquivo JAR:** `weight-1.2.0.jar`
- **Versão 1.21.1:** 1.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Adiciona contribuições de massa física ao stack Sable/Aeronautics para players/entities, inventory/cargo, containers, fluid tanks e armor/equipment conforme config/tags; não é encumbrance RPG.
- **Dependências:** Sable/Create Aeronautics no pack. Build 1.2.0 Client & Server; várias superfícies novas são configuráveis e parte vem desabilitada por padrão.
- **Sobreposição:** Complementa a simulação de massa do stack físico. Não deve ser confundido com stamina/encumbrance nem duplicado por outro mass provider sem composição explícita.
- **Compatibilidade/Riscos:** Riscos: double-count de container+cargo, stale mass após inventory/fluid/armor changes, custo de recomputação, modded handlers e confusão semântica com encumbrance. Config real deve ser lida antes de assumir features ativas.
- **Observações:** Mod id `weight`, runtime 1.2.0. A release adiciona JSON hot-reload (`item_weights.json`, `entity_weights.json`), tags de tanks/ignored containers e `/weight reload`/debug.
- **Procedência:** modlist.txt física atual de 08/09/2026 + Modrinth oficial Create Aeronautics: Weight 1.2.0 + Guia Tecnologia.
- **Fonte:** https://modrinth.com/mod/create-aeronautics-weight
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; cargo/entity/armor mass, tags, hot-reload, config e performance boundaries catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

> ⚖️ **ESCOPO CANÔNICO.** Runtime físico: `weight-1.2.0.jar`, mod id `weight`, versão `1.2.0`. Create Aeronautics: Weight adiciona **massa física a entidades e carga dentro do stack Sable/Aeronautics**. Não é um sistema de encumbrance do jogador e não cria segunda física paralela; ele fornece contribuições de massa consumidas pelo physics stack.

## 1. Função confirmada
A release oficial 1.2.0 é uma atualização grande que adiciona suporte configurável para:
- peso do inventário carregado;
- peso do conteúdo de containers;
- peso de fluid tanks por tag;
- peso de armadura derivado de protection points;
- peso do equipamento de mobs;
- três tiers de peso por tags comuns para suporte automático a conteúdo modded.

Várias dessas superfícies são **desabilitadas por padrão** segundo o changelog oficial; não presumir que estejam ativas sem ler a config real.

## 2. Authority e ownership
- **Sable/Aeronautics:** physics engine, transforms, rigid-body behavior e resposta da contraption.
- **Weight:** mapeamento/contribuição de massa para entidades, players, inventory/cargo e equipamentos suportados.
- **Inventário/containers/fluids:** continuam authorities do conteúdo real.

Não espelhar inventário nem recalcular a física completa em outro mod.

## 3. Item/entity weight data
A 1.2.0 documenta arquivos JSON hot-reload:
- `item_weights.json`;
- `entity_weights.json`.

Isso permite mapear massas sem hardcode de cada mod. Integrações próprias devem preferir essa superfície de dados quando o objetivo for configurar massa, evitando mixins duplicados em item/entity consumers.

## 4. Tags e conteúdo modded
A build adiciona tags como:
- `#weight:tanks`;
- `#weight:ignored_containers`;
além de tiers comuns de peso para suporte automático de conteúdo de outros mods.

Tags devem ser tratadas como classificação do provider Weight; não inferir massa mecânica apenas pelo material visual do item/bloco.

## 5. Cargo e containers
Quando habilitado, o conteúdo de inventories/containers pode contribuir para a massa final da estrutura.
Riscos de integração:
- double-count do container + conteúdo;
- mudança de massa sem atualização após insert/extract;
- container modded expondo handler não convencional;
- mass spikes ao mover stacks grandes.

Mutation de inventário continua pertencendo ao inventory provider; Weight só deve observar/derivar massa.

## 6. Armor e entities
A build pode derivar peso de armor a partir de protection points e contabilizar gear de mobs.
Isso é massa física, não penalidade de stamina/movement do jogador por si só. Não converter automaticamente esse valor em encumbrance de RPG ou debuff sem design explícito.

## 7. Configuração e comandos
A 1.2.0 amplia a configuração de 7 para 22 opções e separa escalas de player/cargo. Também adiciona:
- `/weight reload`;
- saída de `/weight debug` enriquecida.

Essas ferramentas são úteis para validar mapeamentos e hot-reload sem reiniciar o servidor.

## 8. Client / server e lifecycle
A release é Client & Server. A massa que afeta physics deve ser server-authoritative; cliente pode apresentar debug/feedback.
Lifecycle crítico:
- insert/extract de inventory;
- equip/unequip armor;
- mob gear changes;
- fluid tank fill/drain;
- hot-reload JSON/config;
- assembly/disassembly;
- chunk unload/reload;
- server restart.

## 9. Boundary para perks/RPG
- Não usar massa de contraption como “peso carregado do personagem” por inferência.
- Não conceder Mastery por massa total, throughput ou permanência em veículo pesado.
- Se uma perk precisar reagir à massa física, consultar o provider real ou Sable state comprovado.
- Sem hook seguro, comportamento provider-specific = **FAIL-CLOSED**.

## 10. Riscos
1. **Double-count:** container/cargo contados duas vezes.
2. **Stale mass:** inventory/armor/fluid mudou e massa não atualizou.
3. **Config ambiguity:** features desabilitadas por padrão tratadas como ativas.
4. **Performance:** recomputação em estruturas com muitos inventories/entities.
5. **Modded handler mismatch:** container especial não segue assumptions comuns.
6. **Semantic leakage:** massa física confundida com encumbrance RPG.

## 11. Matriz de testes
- [ ] Dedicated server inicia com Weight 1.2.0 + Sable/Aeronautics atuais.
- [ ] Player/entity básico adiciona massa coerente à contraption.
- [ ] Inventory weight ligado/desligado respeita config.
- [ ] Container contents atualizam massa exactly-once após insert/extract.
- [ ] Fluid tanks classificados por tag atualizam massa ao fill/drain.
- [ ] Armor equip/unequip altera massa somente quando feature estiver ativa.
- [ ] `/weight reload` aplica JSON novo sem restart e sem duplicar entries.
- [ ] `/weight debug` reflete provider state atual.
- [ ] Assembly/disassembly e chunk reload não deixam massa stale.
- [ ] Stress test com muitos inventories/entities não causa regressão severa.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências
- **Modlist física 08/09/2026:** `weight-1.2.0.jar`, runtime 1.2.0; Sable/Aeronautics presentes.
- Modrinth oficial da release 1.2.0: inventory/container/fluid tank/armor/mob gear weights, tags comuns, JSON hot-reload, 22 config options, `/weight reload` e debug ampliado.
- Guia Tecnologia: addon de massa Sable/Aeronautics, não encumbrance do jogador.

## 13. Limitação
A config efetiva do pack não foi lida nesta etapa; portanto nenhuma superfície opcional é marcada como ativa sem teste/config real.
