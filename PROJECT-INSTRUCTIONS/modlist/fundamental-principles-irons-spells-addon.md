# Fundamental Principles - Iron's Spells Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8162bc4be3ba27ea3f62
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Fundamental Principles - Iron's Spells Addon
- **Arquivo JAR:** `ypfundamentals-1.1.7.1.jar`
- **Versão 1.21.1:** `1.1.7.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — source pin 1.1.7.1 preservado; catálogo 15/15, 13 Principles, authority, lifecycle e QA consolidados.
- **Categoria:** Magia; RPG
- **Compatibilidade/Riscos:** Alta sobreposição transversal de progressão/gating. Não duplicar Principle XP, exhaustion, passivas, recasts ou spell settlement. Blockers source-level existentes permanecem fail-closed; interop com Iron's 3.16.3 requer runtime QA.
- **Decisão:** Sem decisão
- **Dependências:** Iron's Spells 3.16.3 + Ace's Spell Utils 1.2.7.2 + GeckoLib 4.9.2 no pack; source 1.1.7.1 foi compilado contra Iron's 3.16.2.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/fundamental-principles
- **Função:** Addon de Iron's Spells que adiciona progressão por 13 Principles classificadas automaticamente nos spells, leveling por casting, spell exhaustion, passivos por categoria, progressão de spellbooks, novos spells e mobs voltados a combate mágico PvE/PvP.
- **Histórico da decisão:** vazio
- **Observações:** JAR físico `ypfundamentals-1.1.7.1.jar`, mod id `ypfundamentals`, runtime 1.1.7.1. Metadata físico exibe `Ypsilon's Fundamentalism`; projeto oficial é Fundamental Principles. Source pin a9b8f8222fb2a800edece8c1568984bd2a764fc2 preservado. Iron's 3.16.3 exige runtime QA.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source pin ypsilonM/FundamentalPrinciples-1.21.1@a9b8f8222fb2a800edece8c1568984bd2a764fc2 + CurseForge oficial + auditoria Black Arcana existente.
- **Sobreposição:** Sobreposição alta e transversal: classifica spells de todos os providers por 13 Principles e liquida XP, níveis, fatigue/passivas, targeting/range/recast/teleport/healing/potentiation. Black Arcana não deve criar ledger paralelo nem reaplicar passivas.
- **Data da última decisão:** 2026-09-09

> ✅ **Auditoria canônica 1.1.7.1 — 2026-09-07**
>
> Source pin exato: `ypsilonM/FundamentalPrinciples-1.21.1@a9b8f8222fb2a800edece8c1568984bd2a764fc2` (`final 1.1.7.1`). O registry possui **15 spells ativos** e a Wiki do Black Arcana documenta 15/15 individualmente na PR #74.
>
> Os **13 Principles** são um sistema transversal separado: o addon analisa o `SpellRegistry` global por ASM, gera categorias, mantém XP/nível 0–20 e aplica passivas/unlocks. Black Arcana deve observar esse settlement, não criar segundo ledger.
>
> Principais QA gates estáticos: Lapsus usa `BlockPos location` na instância singleton da spell; Laceration exibe 40% reduced healing mas o handler produz 52% de redução sem Blight; Burning Spirit nível 1 calcula pulse damage 0; Pyrokinesis identifica magic projectiles de fogo por nome de classe contendo `FIRE`; Saeptum usa domain lifecycle do Ace's Spell Utils com barrier/world restoration e region ticket.
>
> Source 1.1.7.1 compila contra Iron's 3.16.2; o pack usa 3.16.3, então comportamento sensível a API permanece em runtime QA.

## Fechamento operacional — lote #583

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
