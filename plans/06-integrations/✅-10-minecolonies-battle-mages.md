# Integrations Complete — 06.10 MineColonies Battle Mages × Iron's Spellbooks

**Status:** CONCLUÍDO / IMPLEMENTADO / VALIDADO / MERGED.

**Minecraft:** 1.21.1  
**Loader:** NeoForge  
**Java:** 21  
**Providers validados:** MineColonies `1.1.1375-1.21.1-snapshot` e Iron's Spells 'n Spellbooks `3.16.3`

## Fechamento canônico

O subplano 06.10 foi implementado pela PR #288 e integrado à `main` em 2026-09-05.

- head funcional final sincronizado: `3f53945c5cda5e25a498d4decc0b728c335697e8`;
- merge da PR #288: `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`;
- `main` pós-merge confirmada no mesmo SHA `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`;
- o contrato de design original permanece preservado verbatim em `plans/06-integrations/archive/10-minecolonies-battle-mages-plan.md`.

## Contrato efetivamente implementado

A implementação mantém as authorities aprovadas:

- MineColonies continua authority do cidadão, colônia, job/guard AI, target, inventário, lifecycle e relações de proteção;
- Iron's continua authority dos spells, `SpellData`, `ISpellContainer`, `MagicData`, mana, cooldown e cast lifecycle;
- o spellbook real do cidadão é o loadout autoritativo; a bridge não cria repertório paralelo;
- identidade de spell circula como `ResourceLocation`;
- seleção é determinística por prioridade, `bookIndex` e spell ID;
- casting usa o pipeline provider-native do Iron's com `CastSource.MOB`;
- não existe mana, cooldown, dano ou projectile pipeline paralelos;
- casts autônomos de cidadãos não concedem Mastery/RPG XP ao jogador;
- friendly fire e footprints de área não comprovados falham fechado;
- world-effect spells permanecem bloqueados até handler seguro explícito;
- remoção/troca do livro invalida imediatamente o loadout e cancela contexto inválido;
- lifecycle de morte/unload/troca de job não cria execução duplicada;
- ausência ou versão incompatível de provider é tratada por optional integration/version gate fail-closed;
- Epic Fight/Epic Colonies/EFIS permanecem compatibilidade opcional, nunca segunda authority de cast.

## Critérios de aceite

- [x] MineColonies `1.1.1375-1.21.1-snapshot` auditado pela build exata usada no pack.
- [x] GuardType/job extension usa API/seam estável comprovado, sem mixin frágil como authority primária.
- [x] Cidadão real da colônia é o caster.
- [x] Spellbook real do Iron's é o loadout autoritativo.
- [x] Spells e níveis são lidos do `ISpellContainer` sem cópia/mutação indevida.
- [x] Iron's `MagicData` é usado; não existe mana paralela.
- [x] Cast lifecycle é provider-native e usa `CastSource.MOB`.
- [x] Os quatro target modes iniciais possuem policy/testes de segurança ou permanecem explicitamente sem profile quando não comprovados.
- [x] Friendly fire fail-closed está validado.
- [x] World-effect spells são bloqueados até handler seguro.
- [x] Remover/trocar o livro altera imediatamente o loadout.
- [x] Morte/unload/troca de job não duplica livro nem efeito.
- [x] Cast autônomo não concede Mastery/RPG XP ao jogador.
- [x] Optional classloading funciona com providers ausentes e mismatch de versão falha fechado.
- [x] Compatibilidade com o stack Epic Fight/MineColonies presente no pack foi testada.
- [x] JUnit GREEN.
- [x] NeoForge GameTests GREEN.
- [x] NeoForge build GREEN.
- [x] Dedicated-server smoke GREEN.
- [x] CI GREEN.
- [x] PR revisada, mergeada e `main` pós-merge confirmada.

## Evidência de verificação

### Head final antes do merge

`3f53945c5cda5e25a498d4decc0b728c335697e8`

- **RPG Skill Tree CI** — run `33985252526`, #3451 — GREEN completo. Incluiu Core, JUnit 5, NeoForge JUnit adapter tests, NeoForge GameTests provider-free, Battle Mage provider-present GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke.
- **Battle Mage Epic Compatibility** — run `33985252632`, #34 — GREEN. A lane provider-present/Epic executou a matriz Battle Mage sem double-cast ou hard dependency opcional.
- **SonarQube Cloud** — run `33985252475`, #687 — GREEN / Quality Gate PASSED. Métricas publicadas na PR #288: `80.3% Coverage on New Code`, `0 Security Hotspots`, `0.0% Duplication on New Code`.
- **CodeQL Security** — run `33985252691`, #484 — GREEN.
- **Volcanoes Full Pack Compatibility Acceptance** — run `33985252481`, #639 — GREEN no head sincronizado, eliminando o failure transitório anterior de download externo.
- Todos os review threads da PR #288 foram resolvidos antes do merge.

### Merge e main

- PR #288: MERGED em `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`.
- `main` imediatamente pós-merge: `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`.
- O merge possui como parents `076af17122e9f2e6ba5f1ee7a0f2d144eb3fd93d` e `3f53945c5cda5e25a498d4decc0b728c335697e8`, preservando a main concorrente e o head validado da feature.

## Resultado para o jogador

O jogador prepara um spellbook real do Iron's, entrega esse item a um cidadão configurado como **Mago de Batalha**, e o cidadão usa somente os spells realmente inscritos no livro pela infraestrutura de combate MineColonies. Trocar o livro troca o repertório; retirar o livro remove a capacidade mágica. O custo e o poder permanecem materializados em equipamento mágico real, sem bônus oculto ou segundo sistema de magia.

**Acceptance: satisfied.**
