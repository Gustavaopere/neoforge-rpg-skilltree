# I0005 — Determinação

- **ID runtime:** `AttributeId.DETERMINATION`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** disciplina, constância, recuperação e resistência a interrupções
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Recuperação de stamina | 0,4% | 5 ranks | 0,96 | 10% | 2,172% | 5,58% |
| Redução da duração de controle nocivo | 0,4% | 8 ranks | 0,96 | 10% | 1,153% | 3,873% |
| Recuperação de cooldown elegível | 0,2% | 10 ranks | 0,96 | 5% | 0,576% | 1,676% |

## Contrato de cada trilha

### 1. Recuperação de stamina

Melhora relativa da regeneração já permitida pelo provedor de stamina, sem criar uma barra própria.

**Boundary e evidência pendente:** Epic Fight 21.17.3.1, somente com evento/API real da release auditada.

### 2. Redução da duração de controle nocivo

Reduz discretamente duração de efeitos nocivos de controle elegíveis na aplicação, nunca tempos de benefícios.

**Boundary e evidência pendente:** Effects/tags vanilla e bridges auditadas; não encurtar timers de boss ou status sem ownership.

### 3. Recuperação de cooldown elegível

Redução pequena em tempo de recarga de habilidades para as quais há modificação segura.

**Boundary e evidência pendente:** irons_spellbooks:cooldown_reduction apenas para Iron's, outras Abilities somente com bridge específica.

## Fronteiras e sinergias

- **Não deve fazer:** Não é Percepção do BG3, não revela minérios ou estruturas, nem garante imunidade a crowd control.
- **Sinergias pretendidas:** MARTIAL, SURVIVAL, ARCANE e habilidades canalizadas; complementa sem substituir eficiência de mana da INT.
- **Investimento como gate:** perks P/S/T podem pedir `DETERMINATION >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Determinação.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Recuperação de stamina | IMPLEMENTADO NO CÓDIGO / CI NOVO PENDENTE — atributo nativo `epicfight:stamina_regen`, operação `ADD_MULTIPLIED_TOTAL` com contribuição relativa de no máximo 10%, gate exato Epic Fight `21.17.3.1`, ID transitório removível; ParCool permanece independente |
| Redução da duração de controle nocivo | IMPLEMENTADO NO CÓDIGO **somente para Levitação inicial causada por Shulker vanilla**: `MobEffectEvent.Added` + `MobEffects.LEVITATION` + origem exata `Shulker`/projétil de Shulker; não cobre reapplications, `forceAddEffect`, comandos ou addons; CI novo pendente |
| Recuperação de cooldown elegível | IMPLEMENTADO NO CÓDIGO / CI PENDENTE — somente cooldown de spells Iron's 3.16.3 (`SPELLBOOK` e `SWORD`), evento `SpellCooldownAddedEvent.Pre`, redução relativa até 5%, arredondamento para tick inteiro e mínimo positivo de 1 tick; demais providers continuam bloqueados |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.


### Contrato exato de cooldown Iron's — evidência primária (14/10/2026)

- **Release alvo:** Iron's Spells 'n Spellbooks `1.21.1-3.16.3` da modlist; source público `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`, `gradle.properties` indica a mesma versão. O JAR instalado no pack ainda precisa de validação binária.
- **Hook confirmado:** `io.redspace.ironsspellbooks.api.events.SpellCooldownAddedEvent.Pre` oferece `getEffectiveCooldown()`, `setEffectiveCooldown(int)` e `getCastSource()`. Em `capabilities/magic/MagicManager.java`, Iron's calcula previamente o cooldown com `(2 - Utils.softCapFormula(attribute cooldown_reduction))` e publica `Pre` **antes** de atualizar seu próprio `PlayerCooldowns` e emitir `SyncCooldownPacket`.
- **Decisão de boundary:** modificar o cooldown final pelo evento `Pre`, e **não** aplicar simultaneamente um modifier em `irons_spellbooks:cooldown_reduction` — essa duplicação alteraria a redução nativa duas vezes. O nosso bônus se aplica a essa recarga já calculada, até 5%, com arredondamento para o tick mais próximo.
- **Elegibilidade:** o próprio `CastSource.respectsCooldown()` da versão 3.16.3 só autoriza `SPELLBOOK` e `SWORD`; `SCROLL`, `COMMAND`, `MOB` e `NONE` são excluídos. Usuários falsos, criativo, espectador e clients não passam no servidor. A integração só é registrada com mod presente, provider ativo e versão exata.
- **Segurança:** um cooldown positivo nunca resulta em zero; cooldown inicialmente zero permanece zero. A ausência de Core snapshot corresponde a rank 0. Não se cria contador, barra, storage ou packet próprios. Ranks ilimitados mantêm o limite teórico de contribuição.
- **Validação:** `IronsCooldownInvestmentPolicyJUnitTest` valida pontos de corte, semântica discreta, limites e rank extremo; `IronsCooldownInvestmentGameTests` cobre o evento real e o Core snapshot quando Iron's está presente. Teste com outros mods e multiplayer do modpack completo continua pendente.


### Regeneração nativa de stamina — Epic Fight 21.17.3.1

- **Inventário da versão:** `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`, `epicfight`; dependência imutável `maven.modrinth:vu3NZ5Ma:8HHhJt6i` registrada no `gradle.properties` do projeto. [Release 21.17.3.1](https://modrinth.com/mod/epic-fight/version/21.17.3.1-mc1.21.1-neoforge) disponibiliza o próprio JAR de sources da release exata. O pacote físico de produção ainda não foi montado/testado integralmente.
- **Fonte exploratória detalhada:** [Epic Fight 1.21.1, commit `a78aa24b72e90a9d09f5fd61925e4369d117abaf`](https://github.com/Antikythera-Studios/epicfight/tree/a78aa24b72e90a9d09f5fd61925e4369d117abaf) declara `mod=21.17.3`, **não a micro-release 21.17.3.1**. `registry/entries/EpicFightAttributes.java` registra `stamina_regen` com base 1.0 e adiciona o atributo a `EntityType.PLAYER`; `world/capabilities/entitypatch/player/PlayerPatch.java` usa `getAttributeValue(EpicFightAttributes.STAMINA_REGEN)` no cálculo de regeneração. A release 21.17.3.1 documenta um ajuste na dependência Simply Tooltips, mas isso não substitui confirmação runtime.
- **Boundary implementado:** `EpicFightInvestmentAttributesRuntime` usa lookup por `ResourceLocation epicfight:stamina_regen`, sem carregar classes do Epic Fight na ausência do mod; `EpicFightInvestmentAttributePolicy.operational` exige versão exata `21.17.3.1`. Cada refresh remove o modifier `rpgskilltree:investment/epicfight/determination_stamina_regen` e, se a versão e rank forem elegíveis, reinsere `ADD_MULTIPLIED_TOTAL` com ganho da curva de Determinação (limite 10%). Não manipula `PlayerPatch`, `setStamina`, barra, regen por tick ou ParCool.
- **Provas automatizadas:** `EpicFightInvestmentAttributePolicyJUnitTest` cobre ausência/versão errada/limites extremos e mapeamento nativo; `EpicFightInvestmentAttributeGameTests` testa o atributo real, idempotência e remoção quando Epic Fight está no runtime. Teste de compatibilidade `battle-mage-epic-compat.yml` instala Epic Fight 21.17.3.1, Epic Colonies 21.0.8 e EFIS 3.1.0; **resultado do novo CI pendente neste commit**. Sem autorização para declarar compatibilidade física com todos os addons nem gameplay final da stamina.


### Auditoria da redução de controles — 14/10/2026 (sem implementação de gameplay)

Na fonte NeoForge 1.21.1 (`neoforged/NeoForge`, branch `1.21.1`, `MobEffectEvent.java` e `LivingEntity.java.patch`), `MobEffectEvent.Applicable` expõe a instância candidata em `canMobEffectBeApplied` e `MobEffectEvent.Added` expõe a instância recebida **antes** de seu merge para o fluxo comum `LivingEntity.addEffect`. O fluxo `forceAddEffect` passa por `Applicable` e não pelo mesmo evento `Added`. O Minecraft 1.21.1 fornece `MobEffectInstance.mapDuration(...)`, mas mutar indiscriminadamente a instância recebida pode alterar objetos reutilizados, repetir a redução ou interferir em efeitos ocultos/renovações. A categoria `HARMFUL` por si só não distingue controle de dano periódico, buffs invertidos, boss mechanics ou efeitos de addons.

**Decisão:** manter `DETERMINATION_CONTROL_DURATION` como valor calculado, mas sem handler ligado ao gameplay. Antes de desbloquear, exigir uma allowlist estreita de IDs e fontes, política para instâncias compartilhadas/efeitos ocultos e reapplication, cobrir tanto `addEffect` como `forceAddEffect` ou documentar exclusões, e executar GameTests com tempos, amplificadores, reapplication, buffs, bosses e runtime físico. Não introduzir mutação em `Applicable` nem refazer `addEffect()` dentro de `Added` sem essa prova. Fonte: `https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/event/entity/living/MobEffectEvent.java` e `https://github.com/neoforged/NeoForge/blob/1.21.1/patches/net/minecraft/world/entity/LivingEntity.java.patch`.


### Incremento 16 — Levitação inicial causada por Shulker vanilla (14/10/2026)

**Escopo de gameplay comprovável:** `InvestedDeterminationControlEvents` faz uma allowlist estrita em `MobEffectEvent.Added` somente para `MobEffects.LEVITATION`, primeira aplicação (sem `getOldEffectInstance()`), fonte direta da classe exata `Shulker` ou `ShulkerBullet` de um `Shulker` vanilla (todos no mesmo `ServerLevel` do alvo `ServerPlayer`). Rejeita fonte ausente, zombie/qualquer outro mob, efeitos benéficos, outras condições negativas, clientes, efeitos já ativos e duração infinita. Não registra tick próprio nem reproduz `addEffect` ou `forceAddEffect`.

**Cálculo:** a duração **da instância entrante** é alterada com `MobEffectInstance.mapDuration`, apenas se finita/positiva e acima de 1 tick, usando `Math.max(1, floor(duração × (1 - bonus)))`. Primeiro marco no rank 8: redução teórica de 0,4% (em 200 ticks, arredonda para 199). O teto de contribuição é 10%, não imunidade. O evento tem proteção fraca por identidade contra multiplicação dupla; o rank é consultado exclusivamente em `CoreProgressionState.attributeRanks()`. O amplificador e efeitos existentes não são modificados.

**Exclusões explícitas:** eventos do fluxo `forceAddEffect` que não disparam `Added`; reaplicações/merge de um efeito ativo; fonte da magia, addon ou comandos; levitação de outras fontes; controle de movimento por atributos e APIs externas; efeitos ocultos ou objetos compartilhados. O benefício é **parcial** por uma única fonte e um único efeito. Não extrapolar para “redução de qualquer debuff”.

**Testes:** `DeterminationControlInvestmentGameTests` constrói `MobEffectEvent.Added` e `MobEffectInstance` reais, valida ranks 0/7/8/30/300/`Long.MAX_VALUE`, aplicação única, amplificador, ausência de rank Core, respec, fonte desconhecida, efeitos distintos, duração infinita e reapplication. O teste não substitui um disparo físico de shulker nem certifica todos os caminhos de `addEffect`/modpack. **CI do commit pendente**, preservar PR Draft. Fonte: NeoForge 1.21.1 `MobEffectEvent.Added` e Minecraft 1.21.1 `MobEffectInstance.mapDuration`.
