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
| Redução da duração de controle nocivo | BLOQUEADO — falta classificação nativa segura de duração/efeitos elegíveis |
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


### Validação negativa de `mapDuration` — 14/10/2026 (GameTest executado)

Foi preparado o adapter experimental de Shulker/Levitação com `MobEffectEvent.Added`, primeira aplicação e source vanilla, em commits exploratórios `81d596c`, `1f0a638` e `2c61458`. A suíte de cobertura com GameTests **reprovou de forma reproduzível** no workflow [SonarQube #38659105405](https://github.com/Gustavaopere/neoforge-rpg-skilltree/actions/runs/38659105405): caso `firstShulkerlevitationisshortenedonceandonlyonce`, rank 8, **esperado 199 ticks, observado 200**. O ponto de falha mostrou que `MobEffectInstance.mapDuration(Int2IntFunction)` **devolve** a duração transformada, mas **não altera** a instância original. Não é um setter; ignorar esse retorno produzia gameplay inexistente.

**Ação corretiva:** o adapter, GameTest experimental e registro de evento foram revertidos no commit `52e1861`; a árvore de código voltou exatamente à árvore do último SHA aprovado `34065c8`. Esse resultado proíbe reintroduzir a estratégia `mapDuration` como mutação de duração. A fonte NeoForge 1.21.1 confirma que `MobEffectEvent.Added` é postado antes do armazenamento normal e que `forceAddEffect` usa outro caminho, mas não fornece alteração mutável garantida da instância. Possíveis alternativas (mixin local, hook em origem/projétil ou API explícita) exigem contrato separado, GameTest físico com efeito aplicado e avaliação de compatibilidade. **DETERMINATION_CONTROL_DURATION segue BLOQUEADO; não contar o experimento como implementação de gameplay.**


### Controle nocivo — integração restrita Shulker/Levitação APROVADA (14/10/2026 BRT)

**Situação atual prevalece sobre os registros históricos de bloqueio e da tentativa malsucedida com `mapDuration` acima:** `DETERMINATION_CONTROL_DURATION` agora tem um **handler efetivo**, limitado a Levitação inicial com origem vanilla identificada. A contribuição **não** foi implementada genericamente contra qualquer crowd control, boss, spell ou debuff; esse suporte segue excluído.

**Contratos de runtime:** `InvestedDeterminationControlEvents` (NeoForge `MobEffectEvent.Added` no `LOWEST` e `PlayerTickEvent.Post` no servidor) armazena recibo temporário em `WeakHashMap<ServerPlayer, ExpiryReceipt>` com **referência à instância nativa**, duração residual-alvo e rank canônico na aplicação. Só aceita classe exata `Shulker` ou `ShulkerBullet` cuja entidade dona seja classe exata `Shulker`, mundo idêntico, `ServerPlayer` real, sobrevivente, rank `DETERMINATION >= 8`, duração finita e primeira aplicação (`oldEffectInstance == null`). Calcula `shortened = max(1, floor(originalTicks * (1 - bonus(rank))))` e o cutoff pela duração remanescente `originalTicks - shortened`. O efeito **não** sofre `setDuration`, `mapDuration`, `addEffect` recursivo ou substituição de instância: somente quando o mesmo efeito atinge o cutoff e o rank não mudou ocorre `removeEffect(MobEffects.LEVITATION)`, preservando limpeza nativa. Refresh ou respec invalida o recibo; mapa limpo no evento `ServerStoppedEvent`. Se outra implementação cancelar a remoção nativa, não força outro caminho.

**Testes e evidência:** `DeterminationControlExpiryGameTests` (3 GameTests) exercita primeira aplicação de Shulker, efeito preservado até o tick anterior, corte no tick correto, origem `Zombie` excluída, respec durante o efeito, extensão/reapplication, `ShulkerBullet` com proprietário vanilla, órfão, `Long.MAX_VALUE` e Core ausente. Um primeiro TDD testou contra handler inativo; a falha de efeito 200 vs 199 ticks foi registrada na compatibilidade Create Sable `#38668916448`. A implementação veio no commit `e963643`. Testes adicionais de projétil/rank `5da4079`; fixture `d68e041` insere `MobEffectInstance` no mapa nativo do jogador desconectado de GameTest para não disparar callbacks de rede MineColonies; em produção, o evento real é usado. **HEAD `d68e041`: 26/26 SUCCESS, Sonar 80,8% de cobertura de código novo, 0 issues novos, 0 hotspots e 0% duplicação**. Testes do modpack real, PVP, logout, extensões de duração entre eventos e reabertura do servidor exigem QA físico.

**Nota de contagem:** esta é a **17ª das 18 contribuições com alguma integração de gameplay**. A única sem hook permanece desconto de Carisma em aldeões; conforme projeto, a presença de uma bridge limitada não implica cobertura universal ou certificação de produção. Mantenha PR #920 **Draft, sem merge**.
