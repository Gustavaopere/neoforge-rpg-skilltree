# Fundamental Principles - Iron's Spells Addon

> **Autoridade física atual — 28/09/2026.** Ordem física **#575**: JAR `ypfundamentals-1.1.7.1.jar`, mod id `ypfundamentals`, runtime `1.1.7.1`, SHA-1 `a2855c253202a8fcc2925fcf5b1bf86d64fbe4da`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Fundamental Principles - Iron's Spells Addon
- **Arquivo JAR:** `ypfundamentals-1.1.7.1.jar`
- **Versão 1.21.1:** 1.1.7.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells que adiciona progressão por 13 Principles classificadas automaticamente nos spells, leveling por casting, spell exhaustion, passivos por categoria, progressão de spellbooks, novos spells e mobs voltados a combate mágico PvE/PvP.
- **Dependências:** Iron's Spells 3.16.3 + Ace's Spell Utils 1.2.7.2 + GeckoLib 4.9.2 no pack; source 1.1.7.1 foi compilado contra Iron's 3.16.2.
- **Sobreposição:** Sobreposição alta e transversal: classifica spells de todos os providers por 13 Principles e liquida XP, níveis, fatigue/passivas, targeting/range/recast/teleport/healing/potentiation. Black Arcana não deve criar ledger paralelo nem reaplicar passivas.
- **Compatibilidade/Riscos:** Alta sobreposição transversal de progressão/gating. Não duplicar Principle XP, exhaustion, passivas, recasts ou spell settlement. Blockers source-level existentes permanecem fail-closed; interop com Iron's 3.16.3 requer runtime QA.
- **Observações:** JAR físico `ypfundamentals-1.1.7.1.jar`, mod id `ypfundamentals`, runtime 1.1.7.1. Metadata físico exibe `Ypsilon's Fundamentalism`; distribuição oficial atual usa o nome Fundamental Principles e publica a mesma versão 1.1.7.1. O filename oficial de distribuição é diferente do filename físico do pack; isso não constitui segundo mod. Source pin `a9b8f8222fb2a800edece8c1568984bd2a764fc2` preservado. Iron's 3.16.3 exige runtime QA.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + source pin 1.1.7.1 + CurseForge/Modrinth oficiais Fundamental Principles 1.1.7.1 + Iron's Spells 3.16.3, Ace's Spell Utils 1.2.7.2 e GeckoLib 4.9.2. Nenhum runtime QA foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/fundamental-principles
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Fundamental Principles / Ypsilon's Fundamentalism 1.1.7.1 permanece exatamente instalado e atual; source pin, catálogo 15/15, 13 Principles e runtime gate Iron's 3.16.3 preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> ✅ **Auditoria canônica 1.1.7.1 — 2026-09-07**
> Source pin exato: `ypsilonM/FundamentalPrinciples-1.21.1@a9b8f8222fb2a800edece8c1568984bd2a764fc2` (`final 1.1.7.1`). O registry possui **15 spells ativos** e a Wiki do Black Arcana documenta 15/15 individualmente na PR #74.
> Os **13 Principles** são um sistema transversal separado: o addon analisa o `SpellRegistry` global por ASM, gera categorias, mantém XP/nível 0–20 e aplica passivas/unlocks. Black Arcana deve observar esse settlement, não criar segundo ledger.
> Principais QA gates estáticos: Lapsus usa `BlockPos location` na instância singleton da spell; Laceration exibe 40% reduced healing mas o handler produz 52% de redução sem Blight; Burning Spirit nível 1 calcula pulse damage 0; Pyrokinesis identifica magic projectiles de fogo por nome de classe contendo `FIRE`; Saeptum usa domain lifecycle do Ace's Spell Utils com barrier/world restoration e region ticket.
> Source 1.1.7.1 compila contra Iron's 3.16.2; o pack usa 3.16.3, então comportamento sensível a API permanece em runtime QA.
## Fechamento operacional — auditoria histórica
### 1. Identidade física e nomenclatura
A modlist física vigente confirma `ypfundamentals-1.1.7.1.jar`, mod id `ypfundamentals`, runtime `1.1.7.1`. O nome exibido no metadata físico é **Ypsilon's Fundamentalism**, enquanto o projeto/distribuição oficial é **Fundamental Principles - Iron's Spells Addon**. Essa diferença de display name não representa dois mods distintos.
### 2. Authority consolidada
- **Iron's Spells 3.16.3**: authority de spell registry, mana, casting, school power e cooldown base.
- **Fundamental Principles**: authority das 13 Principles, classificação global, XP/níveis 0–20, exhaustion/passivas/unlocks e seus 15 spells próprios.
- **Ace's Spell Utils 1.2.7.2**: infrastructure consumida por mechanics como domains/barriers.
- **GeckoLib 4.9.2**: animation/runtime dependency quando aplicável.
Não criar ledger paralelo de Principle XP, não reaplicar passivas e não usar animação/VFX como prova causal de cast bem-sucedido.
### 3. Boundary para RPG Skill Tree / Black Arcana
A classificação automática por ASM atravessa spells de outros providers. Logo, uma spell de outro addon pode participar da progressão de Principles sem deixar de pertencer ao provider original. Mastery externa deve tratar o cast como **uma única cadeia causal**: cast provider → classificação Principle → settlement provider. Não contabilizar novamente cada projectile, recast, tick de exhaustion ou passive pulse.
### 4. Lifecycle crítico
Validar login/relog, death/respawn, dimension change, server restart, spell registry reload, troca de spellbook, cast interrompido e concorrência multiplayer. State persistente de Principles deve sobreviver apenas conforme contrato do addon; modifiers/passivas não podem ficar órfãos após mudança de condição.
### 5. Riscos source-level já conhecidos
Permanecem os blockers documentados na auditoria pinada: `Lapsus` com location em singleton, divergência tooltip/handler de Laceration, pulse 0 em Burning Spirit lvl1, classificação de Pyrokinesis por nome de classe contendo FIRE e lifecycle complexo de Saeptum. O source 1.1.7.1 compila contra Iron's 3.16.2, enquanto o pack usa 3.16.3; qualquer integração sensível a API permanece fail-closed até runtime QA.
### 6. Matriz de testes
- [ ] Dedicated server inicia com Fundamental Principles 1.1.7.1 + Iron's 3.16.3 + Ace's Spell Utils 1.2.7.2.
- [ ] Registry fecha 15/15 spells sem erro.
- [ ] 13 Principles classificam spells sem duplicar XP.
- [ ] Cast manual concede settlement exatamente uma vez.
- [ ] Recast/projectile/effect derivado não gera novo crédito indevido.
- [ ] Exhaustion/passivas não duplicam modifier após relog/respawn.
- [ ] Lapsus funciona corretamente com dois jogadores simultâneos.
- [ ] Saeptum limpa barrier/domain/chunk ticket em cancel, unload e restart.
- [ ] Interop com Iron's 3.16.3 não produz linkage/mixin regressions.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
### 7. Estado
**SOURCE-PINNED 1.1.7.1 / CATÁLOGO 15/15 + 13 PRINCIPLES PRESERVADO / DOSSIÊ OPERACIONAL COMPLETO / RUNTIME QA PENDENTE.**
## Revalidação física — 11/09/2026
A modlist física continua contendo `ypfundamentals-1.1.7.1.jar`, mod id `ypfundamentals`, versão `1.1.7.1`. O projeto oficial mantém **Fundamental Principles 1.1.7.1** como a release 1.21.1 atual, publicada em 02/08/2026; o filename de distribuição oficial difere do filename físico usado no pack, mas a identidade/runtime e o source pin auditado permanecem coerentes.
O pack continua com Iron's Spells `3.16.3`, Ace's Spell Utils `1.2.7.2` e GeckoLib `4.9.2`. Como o source 1.1.7.1 foi compilado contra Iron's `3.16.2`, o version gate permanece runtime QA, não incompatibilidade presumida. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum cast, Principle XP, exhaustion, Saeptum/domain, multiplayer ou linkage test foi executado nesta recatalogação.
## Revalidação física e upstream — 13/09/2026
A posição física atual é #582. Fundamental Principles 1.1.7.1 permanece a release NeoForge 1.21.1 atual. O source pin, catálogo 15/15, 13 Principles e o gate de runtime com Iron's Spells 3.16.3 foram preservados. Nenhum runtime QA foi executado.
