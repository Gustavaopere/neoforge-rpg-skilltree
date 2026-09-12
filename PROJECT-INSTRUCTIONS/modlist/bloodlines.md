# Bloodlines

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db814a89adfb017bfacc71
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Bloodlines
- **Arquivo JAR:** `bloodlines-1.21-3.0.9.jar`
- **Versão 1.21.1:** 1.21-3.0.9
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG
- **Função:** Addon estrutural de Vampirism com 5 bloodlines, 5 skill trees, 101 skills registradas, 29 actions, 22 tasks, ranks próprios, perk wallet e domínio Gravebound Souls/Phylactery.
- **Dependências:** Vampirism 1.10.13; NeoForge 1.21.1. A presença física do Vampirism 1.10.13 foi reconfirmada na modlist atual.
- **Sobreposição:** Estende diretamente Vampirism; BloodlineManager, Vampirism blood e Gravebound Souls/Phylactery permanecem domínios distintos. Não duplicar em sistemas próprios nem converter automaticamente para mana/soul-energy genérica.
- **Compatibilidade/Riscos:** Source-pinned 3.0.9 com runtime QA ainda pendente. Riscos auditados incluem dual skill-point gate, costs/wallet, movement/side, Mist Form, Possession, Souls/Phylactery e interop com addons Vampirism. Não duplicar BloodlineManager ou recursos provider-native.
- **Observações:** Source pin exato c8fd517d204d09dfcb9a544c17d7df87755eaa5c. 5 bloodlines: Noble, Zealot, Ectotherm, Bloodknight, Gravebound. Source 3.0.9 define Heinous Elixir = 15 s. Source audit não equivale a runtime QA.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source oficial TheDrOfDoctoring/bloodlines pin `c8fd517d204d09dfcb9a544c17d7df87755eaa5c` + catálogo source-level já auditado. Reconciliação final: JAR/runtime permanecem exatamente `bloodlines-1.21-3.0.9.jar` / `1.21-3.0.9`; decisão `Manter` e gates de runtime QA permanecem inalterados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bloodlines
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #81: `bloodlines-1.21-3.0.9.jar` / `1.21-3.0.9` conferidos contra a modlist atual; source pin `c8fd517d204d09dfcb9a544c17d7df87755eaa5c`, 5 bloodlines/101 skills/29 actions/22 tasks, BloodlineManager e decisão `Manter` preservados.
- **Histórico da decisão:** 07/09/2026: auditoria granular source-level 3.0.9 concluída no catálogo Black Arcana; manter instalado e preservar autoridade provider-native. Runtime QA permanece pendente.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física reconfirmada: `bloodlines-1.21-3.0.9.jar`, mod id `bloodlines`, runtime `1.21-3.0.9`, com Vampirism `1.10.13` presente. Esta página preserva a auditoria source-pinned já concluída no commit `c8fd517d204d09dfcb9a544c17d7df87755eaa5c`. **Source audit completo não equivale a runtime QA confirmado**; os findings abaixo permanecem gates de teste, não correções automáticas.

## Escopo canônico desta ficha
Bloodlines é authority de identidade/rank/wallet/state das bloodlines por meio do `BloodlineManager`; Vampirism continua authority do blood/faction/base skill systems; Gravebound Souls/Phylactery formam domínio próprio. Integrações do pack devem consumir essas authorities sem criar uma segunda bloodline tree, blood bar ou soul ledger.

## Auditoria técnica 3.0.9 — 07/09/2026

> 🔬 **Estado:** source-pinned na build instalada. Catálogo granular de source concluído; runtime QA e interop com addons ainda pendentes.

### Autoridade auditada
- JAR instalado: `bloodlines-1.21-3.0.9.jar`
- versão/runtime: `1.21-3.0.9`
- Minecraft: `1.21.1`
- loader: NeoForge
- dependência base: Vampirism `1.10.13`
- source oficial auditado: `TheDrOfDoctoring/bloodlines`
- pin exato: `c8fd517d204d09dfcb9a544c17d7df87755eaa5c`

### Inventário source-level confirmado

| Superfície | Quantidade |
| --- | ---: |
| Bloodlines | 5 |
| Skill trees | 5 |
| ISkill registrations | 101 |
| IAction registrations | 29 |
| Tasks | 22 |
| Ranks | 4 por bloodline |
| Mob effects próprios no core | 4 |

Bloodlines confirmadas: **Noble**, **Zealot**, **Ectotherm**, **Bloodknight** e **Gravebound**. As quatro primeiras pertencem à facção Vampire; Gravebound pertence à Hunter.

### Autoridade de estado
`BloodlineManager` é attachment NeoForge serializado e `copyOnDeath`, responsável por identidade, rank, wallet de perk points, state específico, limpeza de skills, atributos, locks e sync. Não deve ser substituído por scoreboard, tag ou cópia de NBT do Black Arcana.

Bloodline perk points são recurso próprio. Vampire blood continua sendo autoridade do Vampirism. Gravebound Souls e Phylactery formam um terceiro domínio separado; não devem ser convertidos automaticamente em Goety Soul Energy, Malum spirits ou recurso genérico do Black Arcana.

### Findings que exigem runtime QA
- possível dual gate entre Bloodline perk points e skill points normais do Vampirism;
- wallet Bloodlines contabiliza 1 por skill habilitada mesmo quando algumas skills expõem custo base 2–3;
- `MaxPerkUnlocker` apresenta mapeamento de nomes min/max invertido no codec;
- perk tasks não-Gravebound não exibem cap equivalente ao limite Gravebound de 15 task points;
- Ectotherm contém referência cruzada a `ZEALOT_POISONED_STRIKE`;
- Shadowwalk define config de distância própria, mas a action auditada usa a distância do Teleport do Vampirism;
- Sanguine Infusion, Blood Hunt e Daywalker debitam 2 blood por intervalo embora comentários da config descrevam 1;
- Sorcerous Strike possui config específica de Wither 8 s, mas o hook usa a duração geral 10 s; além disso, sua reachability de survival não foi provada;
- Devour Soul pode retornar sucesso da action para LivingEntity que não foi efetivamente devorado;
- Mist Form usa comparação estrita: ter exatamente o custo de Souls ainda cai na rota de morte;
- Wall Climb altera movimento no lado cliente no método auditado;
- source 3.0.9 define **Heinous Elixir = 15 s**, divergindo de prosa pública que descreve 30 s.

### Regras de integração Black Arcana
- provider-native first;
- exatamente um settlement por blood/task/action/devour;
- não criar segundo BloodlineManager, blood bar, Soul meter ou action timer;
- respeitar faction, Bloodline, rank, parent-node, sibling e skill gates;
- não usar action activation como prova de conclusão quando o provider possui settlement posterior;
- movimento, teleporte, Mist Form e Possession permanecem fail-closed até dedicated-server QA;
- divergências estáticas acima são gates de QA, não correções automáticas.

### Estado de fechamento
O catálogo granular de source do Bloodlines 3.0.9 está concluído para registries, trees, skills, actions, tasks, progressão, joins/leaving, Bloodline points, Vampirism blood, Gravebound Souls/Phylactery, lifecycle, mixins de alta relevância e contrato de integração. **RUNTIME QA CONFIRMED não foi concedido.**
