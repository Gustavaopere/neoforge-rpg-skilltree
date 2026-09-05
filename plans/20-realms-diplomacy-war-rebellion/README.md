# 20 — Reinos, Diplomacia, Guerra, Vassalagem e Rebelião

## Objetivo

Adicionar uma camada geopolítica **singleplayer-first** acima das colônias. O jogador pode formar um realm, integrar múltiplas colônias, criar títulos/vassalos, negociar, guerrear e enfrentar oposição interna. Reinos NPC existem sem exigir dezenas de MineColonies completas rodando fora da tela.

## Realm acima de Colony

Não reutilizar/abusar do `owner UUID` do MineColonies para representar reino. Criar domínio próprio:

```text
RealmRecord
├── realmId
├── ruler/government
├── member colonies
├── capital
├── titles/vassals
├── treasury references
├── diplomacy
└── military/political state
```

MineColonies continua autoridade de cada colony real.

## NPC realms

Usar simulação agregada/offscreen. Um realm NPC pode ter settlements abstratos com população, economia, força, governo e relações sem chunks, citizens e block entities permanentemente carregados.

## Guerra

Combate materializado reutiliza guards/raiders e o Stage 02.06: combatentes recebem scaling completo; civis não viram soldados. Conquista/occupation altera records territoriais apenas após outcomes comprovados.

## Política interna

Escada canônica:

```text
descontentamento
→ protesto
→ greve
→ sabotagem
→ motim/distúrbio
→ insurreição/rebelião
```

Escalada depende de condições persistentes, thresholds/histerese e decisões; não RNG puro por tick.

## Ordem

1. `01-realm-colony-vassal-domain.md`
2. `02-titles-counties-duchies-kingdoms.md`
3. `03-diplomacy-relations-and-treaties.md`
4. `04-war-raids-sieges-and-occupation.md`
5. `05-vassalage-tribute-and-conquest.md`
6. `06-singleplayer-npc-realm-simulation.md`
7. `07-abstract-offscreen-settlements.md`
8. `08-espionage.md`
9. `09-discontent-strikes-riots-rebellion.md`
10. `10-tests-save-performance-ai.md`

## Definition of Done

Um jogador cria realm com mais de uma colony, mantém relações com realm NPC abstrato, assina tratado, cobra/paga tributo, entra em guerra, resolve outcome sem corrupção territorial, e pode enfrentar greve/rebelião causada por condições socioeconômicas reais.