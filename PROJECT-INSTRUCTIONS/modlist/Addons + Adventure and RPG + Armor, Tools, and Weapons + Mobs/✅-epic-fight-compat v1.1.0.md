# Epic Fight Compat

## Propriedades do registro

- **Mod:** Epic Fight Compat
- **Arquivo JAR:** `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`
- **Versão 1.21.1:** `1.1.0`
- **Categoria:** Compat, RPG
- **Função:** Compat pack modular/data-driven que aplica entity patches, atributos e weapon capabilities do Epic Fight a entidades e armas de mods externos quando há preset/archetype compatível.
- **Dependências:** Epic Fight 21.17.3.1 é a dependência required. Targets são opcionais e ativados por mod ID; o snapshot físico histórico confirma **oito** alvos: Iron's Spells 3.16.3, Simply Swords 1.70.2, Simply More 1.3.0_alpha, Mowzie's Mobs 1.8.2, Quark 4.1-484, **Bosses'Rise 2.1.2 (`block_factorys_bosses`, #079)**, Supplementaries 3.9.9 e Farmer's Delight 1.3.4. O módulo Bosses'Rise é **somente weapon capabilities**; nenhum boss entity patch é declarado por esse módulo.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: preset genérico aplicado a entidade/boss com behavior próprio, capability/regex de arma incorreta, atributos Epic Fight ausentes, resource ID drift e disputa com bridges especializadas como EFIS/Epic Colonies. Source usa ativação condicional e failure isolation; ainda requer regressão com Epic Fight 21.17.3.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-fight-compat
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`, mod id `epicfightcompat`, runtime 1.1.0 e SHA-1 `73322bae5887cad40835bee6f7cbf425ffbb8ffb`. CurseForge oficial revalidado em 21/09/2026 mantém 1.1.0 como latest release NeoForge 1.21.1.
- **Observações:** CompatRegistry 1.21.1 contém 17 módulos alvo. **Oito dos 17 targets** possuem correspondência top-level no snapshot: Iron's Spellbooks, Simply Swords, Simply More, Mowzie's Mobs, Quark, **Bosses'Rise (Block Factory's Bosses)**, Supplementaries e Farmer's Delight. A ativação real requer conferência dos JARs e runtime. Módulos ausentes não são tratados como ativos.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #262: `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` / runtime `1.1.0` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. CurseForge oficial mantém 1.1.0 como latest release NeoForge 1.21.1.
- **Decisão:** Sem decisão
- **Sobreposição:** Pode cruzar com patches dedicados do ecossistema Epic Fight. Bridges especializadas devem preservar ownership específico; não assumir que o compat genérico substitui EFIS, Epic Colonies, Curios Compat ou Epic ParCool.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #263: JAR `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`, mod id `epicfightcompat`, runtime `1.1.0`, SHA-1 `73322bae5887cad40835bee6f7cbf425ffbb8ffb`.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` · mod id `epicfightcompat` · versão `1.1.0` · NeoForge 1.21.1 · Client & Server.
## 1. Papel no modpack
Epic Fight Compat é um **compat pack modular e data-driven** que aplica patches de entidade e weapon capabilities do Epic Fight a conteúdo de outros mods quando existe analogia funcional segura.
## 2. Princípio de compatibilidade upstream
O source 1.21.1 explicita uma política conservadora: entidade semelhante a um mob vanilla já suportado pode reutilizar seu preset; criatura/boss exótico com animações próprias deve permanecer intacto. Para armas, só se atribui preset quando o archetype do Epic Fight é natural; staves/wands/exóticos podem permanecer no comportamento original/fallback.
## 3. Entity patches
Patches data-driven usam recursos `data/<target_modid>/epicfight_mobpatch/<entity>.json`. Um preset referencia um EntityType já suportado pelo Epic Fight. O compat também adiciona atributos Epic Fight às entidades externas via `EntityAttributeModificationEvent` quando necessário.
## 4. Weapon capabilities
Armas são mapeadas por `data/<modid>/capabilities/weapons/<item>.json` ou regexes de `item_keyword`. Presets oficiais incluem categorias como sword, axe, greatsword, longsword, dagger, spear, tachi, bow e crossbow. JSON explícito tem precedência sobre regex/fallback.
## 5. Activation modular
`CompatRegistry` só ativa um módulo quando `ModList` detecta o mod alvo. Falha de um módulo é capturada/logada para não impedir os demais. Os targets são optional; **Epic Fight é a dependência required**.
## 6. Módulos presentes no source 1.21.1
O registry atual lista: Variants & Ventures, Guard Villagers, Eternal Nether, Illager Invasion, It Takes a Pillage, Iron's Spellbooks, Simply Swords, Simply More, Mowzie's Mobs, Darker Depths, Quark, Block Factory's Bosses, Supplementaries, ReArm, Farmer's Delight, Hearth & Harvest e Mutant Monsters.
## 7. Subconjunto fisicamente ativo neste pack
A modlist confirma pelo menos estes targets presentes:
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`;
- Simply Swords `1.70.2-1.21.1`;
- Simply More `1.3.0_alpha`;
- Mowzie's Mobs `1.8.2`;
- Quark `4.1-484`;
- Supplementaries `1.21.1-3.9.9`;
- Farmer's Delight `1.3.4`;
- **Bosses'Rise `block_factorys_bosses` `2.1.2` (#079)** — weapons only, sem substituir os rigs/IA/ataques exclusivos dos bosses.
Os demais módulos do registry não são tratados como ativos sem presença física correspondente.
## 8. Relação com bridges especializadas
EFIS, Epic Colonies, Curios Compat e outros patches dedicados podem tocar subsets mais específicos. **Epic ParCool não está instalado**; não tratá-lo como bridge ativa do pack. O compat geral não deve ganhar precedence automaticamente quando uma bridge especializada preserva renderer/behavior que um preset vanilla destruiria.
## 9. Client / Server
**Server/common:** entity patch state, attributes, weapon capability e combat settlement.
**Client:** armature/renderer/animation derivados do patch.
Data packs precisam existir de forma coerente no servidor/pack; renderer nunca decide damage.
## 10. Lifecycle
Validar startup com targets ausentes/presentes, datapack reload, weapon capability reload, entity spawn, chunk reload, equip/troca de arma, battle mode toggle, death/respawn e updates de qualquer target.
## 11. Multiplayer / idempotência
O mesmo item/entity não pode receber dois mappings incompatíveis por compat packs diferentes. Damage/stun deve liquidar uma vez. Clients observadores apenas renderizam o patch sincronizado.
## 12. Riscos
1. target mod atualizar IDs/classes;
2. preset funcionalmente inadequado;
3. patch genérico sobrescrever animação especial de boss;
4. atributos Epic Fight faltarem numa entidade externa;
5. JSON de capability apontar item inexistente;
6. regex mapear arma errada;
7. bridge especializada disputar a mesma entidade/item;
8. logs de resources de targets ausentes;
9. datapack reload deixar capability stale;
10. versão 1.1.0 ficar atrás de mudanças do Epic Fight 21.17.3.1.
## 13. Matriz de testes
1. Boot dedicado com todos os targets físicos atuais.
2. Entidade de cada módulo ativo representativo.
3. Arma Simply Swords/Simply More de archetypes diferentes.
4. Iron's Spellbooks: arma compatível vs casting item que deve permanecer próprio.
5. Mowzie boss/exótico confirmando que não recebe patch genérico indevido.
6. Quark/Supplementaries/Farmer's Delight mappings relevantes.
7. `/reload` e reteste de capabilities.
8. Coexistência com EFIS e Epic Colonies.
9. Dois jogadores atacando a mesma entidade.
10. Smoke-test após update Epic Fight/target mod.
**Esta catalogação não afirma que esses testes foram executados.**
## 14. Evidências
- modlist física canônica: JAR/version/hash e targets instalados;
- source oficial `payangar-dev/epicfight-mod-compat` branch 1.21.1: `CompatRegistry`, arquitetura modular e paths data-driven;
- documentação interna do projeto: política de presets, entity attributes, weapon capability precedence e failure isolation.
> **Boundary canônico:** Epic Fight Compat adapta **entidades/armas externas ao contrato Epic Fight**; ele não substitui os providers dos mods alvo nem deve forçar compat onde a analogia é ruim.


## 15. Reconciliação exata dos 17 módulos — Bosses'Rise é o oitavo alvo presente (13/10/2026)

**Correção auditável:** a descrição anterior contava **sete** módulos opcionais com targets físicos, omitindo Bosses'Rise embora o snapshot #079 tenha `block_factorys_bosses-2.1.2-neo-1.21.1.jar` e mod ID `block_factorys_bosses`. O código oficial do [`CompatRegistry` na tag `v1.1.0`](https://github.com/payangar-dev/epicfight-mod-compat/blob/v1.1.0/src/main/java/com/payangar/epicfightcompat/compat/CompatRegistry.java) declara **17** módulos, e `forEachActive` só chama o módulo se `ModList.isLoaded(requiredModId())`; a presença de um target é **condição de seleção**, não prova de execução bem-sucedida dos mappings.

**Oito correspondências com o inventário histórico de 587 linhas (mod ID obrigatório no source → item físico):**

| Target no source | Físico histórico | Papel |
|---|---|---|
| `irons_spellbooks` | Iron's Spells #342 3.16.3 | capabilities de armas |
| `simplyswords` | Simply Swords #503 1.70.2 | capabilities de armas |
| `simplymore` | Simply More #502 1.3.0_alpha | capabilities de armas |
| `mowziesmobs` | Mowzie's Mobs #411 1.8.2 | integração específica dos recursos publicados |
| `quark` | Quark #468 4.1-484 | recurso dedicado da integração |
| **`block_factorys_bosses`** | **Bosses'Rise #079 2.1.2** | **somente armas e escudos, sem entity patches de bosses** |
| `supplementaries` | Supplementaries #536 3.9.9 | integração de entidades/recursos quando prevista |
| `farmersdelight` | Farmer's Delight #276 1.3.4 | facas e archetypes de arma |

Os outros **nove** targets no `CompatRegistry` — Variants & Ventures, Guard Villagers, Eternal Nether, Illager Invasion, It Takes a Pillage (`takesapillage`), Darker Depths, ReArm, Hearth & Harvest e Mutant Monsters — **não aparecem como JARs top-level próprios no snapshot consultado**. Não contar add-ons de nomes semelhantes como substitutes sem confirmação de mod ID.

**Contrato especial de Bosses'Rise:** o [`BlockFactorysBossesModule.java` da tag `v1.1.0`](https://github.com/payangar-dev/epicfight-mod-compat/blob/v1.1.0/src/main/java/com/payangar/epicfightcompat/compat/blockfactorysbosses/BlockFactorysBossesModule.java) retorna `requiredModId() = "block_factorys_bosses"` e implementa apenas um módulo Java *no-op* para recursos JSON. O [source `v1.1.0`](https://github.com/payangar-dev/epicfight-mod-compat/tree/v1.1.0/src/main/resources/data/block_factorys_bosses/capabilities/weapons) disponibiliza **oito JSONs** de capabilities: `warrior_sword` (sword), `dagger` (dagger), `knight_sword` (longsword), `large_sword` (greatsword), `pirate_saber` (sword), `kraken_trident` (trident), `dragon_guard_shield` e `enhanced_shield` (shield). O código **não injeta patches de entidade nos chefes Bosses'Rise**; preserva rigs e ataques exclusivos do mod original. `ice_gauntlet` é deixado ao fallback do `ShieldItem`; `sandworm_gauntlet` e `undying_tentacle` são excluídos da compat explícita para não sobrescrever suas ações GeckoLib.

**Versão e procedência:** a [release oficial CurseForge 1.1.0 NeoForge 1.21.1, File ID 8097273 (16/05/2026)](https://www.curseforge.com/minecraft/mc-mods/epic-fight-mod-compat/files/8097273) confirma em seu changelog a adição `feat(blockfactorysbosses): add Epic Fight weapon compat for Bosses'Rise`. A tag GitHub usada corresponde nominalmente à release **v1.1.0**, mas o `gradle.properties` nela mantém `mod_version=0.1.0` e o build-dependency `Epic Fight 21.17.2`; isso **não reclassifica a metadata física 1.1.0** nem comprova identidade byte-for-byte do JAR. Pack físico histórico: Epic Fight 21.17.3.1; compatibility drift requer teste.

**Matriz de regressão específica (NÃO EXECUTADA):** conferir hashes e bytecode do JAR compat 1.1.0 e Bosses'Rise 2.1.2; validar cada um dos oito mapeamentos no battle mode, inclusive guard/shield, durabilidade, sincronização e motion; testar ataques especiais/GeckoLib para `sandworm_gauntlet` e `undying_tentacle` sem perda de comportamento; verificar que Ashlord/Helvar/Skor/Sirok/Kraken **não** recebem entity patch genérico nem têm hit/cinematic/phase processados duas vezes; recarregar datapacks, trocar arma, death/relog, dimensão, dedicated server e multiplayer. Comparar com Epic Fight 21.17.3.1 e com outros addons de capabilities antes de qualquer atualização.

**Decisão:** **CORRIGIR CONTAGEM DOCUMENTAL (7→8), MANTER JAR 1.1.0 E BOSSes'Rise 2.1.2.** A presença física vem do inventário histórico, os oito arquivos vêm do source upstream; runtime real e instalação presente ainda exigem inspeção binária. Não instalar mods ou adicionar novas linhas físicas.
