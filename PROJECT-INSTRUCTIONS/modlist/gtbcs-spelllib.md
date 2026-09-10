# GTBC's SpellLib — 2.2.0-1.21.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db819290c8d6c6924a178c  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** GTBC's SpellLib
- **Arquivo JAR:** `gtbcs_spell_lib-2.2.0-1.21.1.jar`
- **Versão 1.21.1:** `2.2.0-1.21.1`
- **Categoria:** Biblioteca; Magia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/gtbcs-spelllib/files/8824651
- **Função:** Biblioteca/API compartilhada dos addons de Iron's Spells do GameTechBC, fornecendo attributes, helpers de summons/loot, keep-inventory localizado, painting framework, Treasure Pouch, data managers e utilidades comuns.
- **Dependências:** Biblioteca do ecossistema GTBC/Iron's. GTBC's Geomancy Plus 1.1.0-1.21.1 é consumer físico confirmado; Iron's Spells 'n Spellbooks 3.16.3 está presente no pack.
- **Compatibilidade/Riscos:** Riscos: ABI drift entre SpellLib e consumers, attribute stacking/double-application, keep-inventory interferindo com death/Curios/XP systems, Treasure Pouch dupe, data-component/NBT migration, summon helper ownership e mixin conflict. 2.2.0 adiciona Healing Received, Damage Taken e Summon Health.
- **Sobreposição:** Não substitui Iron's Spells nem outra biblioteca geral. É API específica dos addons GTBC; qualquer attribute/helper deve ter um único owner sem duplicar lógica de death, summons, loot ou attribute scaling de outros providers.
- **Observações:** Corrigido drift antigo: runtime atual é 2.2.0-1.21.1, não 2.1.0. 2.0.0 introduziu Elemental Permeability, Localized Keep Inventory, painting framework e Treasure Pouch; 2.1.0 expandiu keep-inventory e loot pouch helpers; 2.2.0 adicionou três attributes novos.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial SpellLib 2.2.0 NeoForge file 8824651 + changelogs oficiais 1.5.0/2.0.0/2.1.0/2.2.0 para superfícies acumuladas. Source público exato não localizado nesta auditoria.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — GTBC's SpellLib 2.2.0; API/attributes, keep-inventory, Treasure Pouch, paintings, summon/data helpers, lifecycle, multiplayer, riscos e testes catalogados; source exato indisponível.
- **Data da última decisão:** 2026-08-30

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `gtbcs_spell_lib-2.2.0-1.21.1.jar`, mod id `gtbcs_spell_lib`, versão `2.2.0-1.21.1`, NeoForge 1.21.1. A release oficial file `8824651`, publicada em 06/09/2026, é a authority da versão. Não foi localizado source público exato auditável; a ficha usa somente changelogs/documentação oficiais e mantém classes/registries internos não publicados em modo fail-closed.

## 1. Papel no modpack
GTBC's SpellLib é a biblioteca/API compartilhada dos addons de Iron's Spells do GameTechBC. O projeto a descreve como armazenamento de código comum e helpers para desenvolvimento de addons ISS. Ela não constitui uma linha de gameplay independente; consumers como **GTBC's Geomancy Plus** usam suas abstrações.

## 2. Authority / ownership
- **Iron's Spells 'n Spellbooks:** framework de spells, mana, schools e attributes-base do ecossistema.
- **SpellLib:** helpers/APIs/attributes e componentes compartilhados registrados pela própria biblioteca.
- **Consumers GTBC:** continuam authority de seus spells, itens, entidades e regras específicas.
Não duplicar state de consumer dentro de outra bridge só porque SpellLib expõe helper equivalente.

## 3. Versão física e consumer confirmado
A modlist atual confirma:
- SpellLib `2.2.0-1.21.1`;
- Iron's Spells `1.21.1-3.16.3`;
- GTBC's Geomancy Plus `1.1.0-1.21.1`.
Geomancy Plus é consumer físico confirmado. Outros consumers devem ser relacionados somente quando sua metadata/release também confirmar dependência.

## 4. Attributes acumulados confirmados
A linha pública recente adiciona attributes compartilhados:
- **Elemental Permeability** — introduzido na 2.0.0 como atributo percentual com base 1.0 para interação elemental;
- **Healing Received** — adicionado na 2.2.0;
- **Damage Taken** — adicionado na 2.2.0;
- **Summon Health** — adicionado na 2.2.0.
IDs de registry e ranges exatos dos três novos attributes não são inventados sem source/JAR enumeration.

## 5. Risco de attribute stacking
Attributes comuns podem ser consumidos por múltiplos addons. Se dois systems aplicarem o mesmo modifier por equip/reload/login, o resultado pode ser empilhado ou removido incorretamente. Toda integração própria deve registrar owner, UUID/key ou outro identificador efetivo conforme API real, depois validar exactly-once em equip/relog.

## 6. Localized Keep Inventory
A 2.0.0 introduziu um sistema de **keep inventory localizado**: entidades compatíveis podem preservar inventário/XP/Curios de jogadores que morrem dentro de uma área definida pelo provider. A 2.1.0 renomeou a interface pública de `IKeepInventoryBoss` para `IKeepInventoryEntity`, ampliou o scan range máximo de 64 para 128 e adicionou condições per-player e opção de preservar experiência.
Isso é uma superfície crítica de death lifecycle e não deve concorrer silenciosamente com gamerule keepInventory ou outros mods de corpse/death recovery.

## 7. Curios e death settlement
O sistema documentado pode preservar slots Curios, inclusive cosméticos, e cancelar drops de XP enquanto ativo. Logo, death settlement precisa ocorrer uma única vez e no provider correto. Duplicação pode causar item dupe, XP dupe ou itens desaparecidos se dois mods tentarem manter/devolver o mesmo inventário.

## 8. Treasure Pouch
A 2.0.0 adicionou **Treasure Pouch**, um container de loot single-use que entrega seu conteúdo ao jogador; na linha 1.21.1 usa data component dedicado. A 2.1.0 adicionou helper `GSLGeneralUtils#grantLootInPouch`, que rola uma loot table e entrega os resultados em uma pouch.
A pouch deve ser consumida e liquidada exactly-once; reconnect, inventory-full e reward integrations são regression gates.

## 9. Custom Painting System
A 2.0.0 introduziu framework de paintings customizados independente da seleção aleatória vanilla, em que cada painting corresponde a imagem/tamanho definidos pelo consumer. Esse subsistema é conteúdo/render support; não deve ser confundido com authority sobre blocks/entities de outro mod.
Resource reload e asset path drift são os riscos principais desse domínio.

## 10. Summon helpers
A linha 1.5.0 registrou atualização de `SummonCheckHelper`, e a 2.2.0 adiciona `Summon Health`. Isso confirma que summons são uma superfície comum da biblioteca. Ownership, cap e lifecycle da entidade invocada continuam pertencendo ao consumer/Iron's; helper de library não deve criar segundo ledger de summon state.

## 11. Data managers e components/NBT
A 1.5.0 atualizou data managers para incluir o nome da API em NBT/components e migrou uso de APIs modernas de `ResourceLocation`. Em 1.21.1, Treasure Pouch usa data component dedicado. Isso torna data migration/version drift relevante quando a biblioteca é atualizada mantendo consumers antigos.

## 12. API de keep-inventory — 2.1.0
A 2.1.0 adicionou:
- `IKeepInventoryEntity#keepInventoryFor` para condição extra por jogador;
- `IKeepInventoryEntity#shouldKeepExperience`;
- aumento de `GSLKeepInventory.MAX_SCAN_RANGE` de 64 para 128;
- `GSLGeneralUtils#grantLootInPouch`.
Esses nomes são suportados pelo changelog oficial 2.1.0; internals além disso não são extrapolados.

## 13. Client / server
A distribuição é **Client & Server**.
- **Servidor:** attributes que alteram gameplay, keep-inventory/death settlement, loot/pouch contents, summon ownership e data authoritative.
- **Cliente:** painting/assets, presentation e qualquer UI/visual do consumer.
Nenhuma decisão de preservar inventário, aplicar damage/healing scalar ou conceder loot deve depender apenas do cliente.

## 14. Lifecycle
Validar login/relogin, death/respawn, dimension change, equip/unequip, summon/despawn, server restart, datapack/loot reload e resource reload. Attribute modifiers e keep-inventory context não podem ficar stale após troca de dimensão ou owner death.

## 15. Multiplayer e concorrência
Cenários sensíveis:
- dois jogadores morrendo dentro da mesma área keep-inventory;
- múltiplas entidades implementando keep-inventory com áreas sobrepostas;
- duas sources modificando Healing Received/Damage Taken;
- duas summons do mesmo owner recebendo Summon Health;
- múltiplos rewards concedendo Treasure Pouch simultaneamente.
O resultado deve ser determinístico e idempotente.

## 16. Integração com Geomancy Plus
Geomancy Plus 1.1.0 está instalado e depende do ecossistema SpellLib/Iron's. Atualizar SpellLib de forma unilateral pode alterar attributes/helpers usados pelo addon. A combinação física atual deve ser tratada como compat matrix; release recente de SpellLib não garante automaticamente que consumer antigo usa todos os APIs novos.

## 17. Source / version provenance
O projeto possui distribuição pública e changelogs detalhados, mas source público exato não foi localizado nesta auditoria. Por isso:
- nomes de APIs citados acima só são registrados quando aparecem explicitamente em changelog oficial;
- registry IDs, serializers, mixins, packet classes e config keys não são inventados;
- qualquer integração de código próprio deve compilar/testar contra o artefato físico 2.2.0.

## 18. Riscos técnicos
- ABI drift entre SpellLib 2.2.0 e consumers antigos;
- modifier de attribute aplicado/removido mais de uma vez;
- Damage Taken/Healing Received causando double-scaling com outro mod;
- Summon Health aplicado a entidade/owner errado;
- keep-inventory sobreposto com gamerule/corpse/death mods;
- Curios ou XP preservados/devolvidos duas vezes;
- Treasure Pouch aberta/recompensada mais de uma vez;
- data component/NBT migration quebrar conteúdo antigo;
- loot-table reload alterar pouch outcome sem version gate;
- painting asset/cache stale após resource reload;
- mixin conflict do JAR físico com outros death/attribute hooks;
- source/API ausente levar integração externa a depender de internals especulativos.

## 19. Matriz de testes obrigatória
- [ ] Dedicated server boot com SpellLib 2.2.0 + Iron's 3.16.3 + Geomancy 1.1.0.
- [ ] Healing Received modifica cura uma única vez no consumer que o usa.
- [ ] Damage Taken modifica dano uma única vez sem double-scaling.
- [ ] Summon Health aplica ao summon/owner correto e limpa no despawn.
- [ ] Keep-inventory localizado preserva inventário/XP/Curios somente quando condição real é atendida.
- [ ] Duas entidades keep-inventory com áreas sobrepostas não duplicam settlement.
- [ ] Death/relog/restart não duplica ou perde itens/XP.
- [ ] Treasure Pouch é single-use e entrega conteúdo exatamente uma vez, inclusive inventory-full/relog.
- [ ] `grantLootInPouch` respeita a loot table e não duplica reward.
- [ ] Data components/NBT persistem após save/reload e atualização controlada.
- [ ] Custom paintings recarregam após resource reload sem asset/cache stale.
- [ ] Geomancy Plus continua carregando sem `NoSuchMethodError`/linkage error.
- [ ] Atualização futura de SpellLib é bloqueada até regression test dos consumers físicos.

## 20. Evidências e limites
- **Modlist física:** `gtbcs_spell_lib-2.2.0-1.21.1.jar`, mod id e consumers/dependências presentes.
- **Release oficial 2.2.0 file 8824651:** Healing Received, Damage Taken e Summon Health.
- **Changelog 2.1.0:** rename para `IKeepInventoryEntity`, scan range 128, condições per-player/XP e `grantLootInPouch`.
- **Changelog 2.0.0:** Elemental Permeability, Localized Keep Inventory, custom painting framework e Treasure Pouch/data component 1.21.1.
- **Changelog 1.5.0:** atualização para ISS 3.15, SummonCheckHelper, data managers e APIs modernas de resource location.
- **Limite crítico:** source público exato 2.2.0 não foi localizado; registries/classes não citados oficialmente e internals permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
