# A0011 — Ruptura de Guarda

## Status e proveniência

- **Design:** APROVADO após correção canônica.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db812bb55bf30113d24b9a
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- Requer A0008 ≥2 + A0009 ≥1 e pelo menos 40 Fúria.
- Hit direto de machado contra guarda/postura real pode gastar 20 Fúria.
- Rank 1: +20% impacto/pressão e até 6% penetração; rank 2: +35% e até 10%.
- Se guarda/postura não for observável, somente defesa física server-side comprovável autoriza fallback de penetração-only.
- A antiga condição de “alvo classificado como pesado” foi removida: não havia provider obrigatório com classificação segura e qualquer aproximação por vida/tamanho/knockback seria heurística proibida.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0008/A0009 + gateway.
2. **Integração global:** PASS — consome Fúria canônica e usa `IMPACT`/`ARMOR_NEGATION`.
3. **Identidade:** PASS — gasto deliberado para romper defesa real.
4. **Topologia:** PASS — Notable camada 3.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção da condição não implementável.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — provider-native primeiro, fallback físico estrito.

## Evidência técnica

- `NotionCombatPerkRules`: threshold 40, custo 20, impacto 1,20/1,35, penetração 0,06/0,10.
- `A0001A0020CombatPolicy.beforeHit`: `nativeDefense` ou `armorFallback`; uma única claim `A0011:spend`.
- Se defesa é observável e ausente, Armor não ativa a perk.
- Fallback por Armor só existe quando o adapter não consegue observar guarda/postura e aplica somente penetração.
- `A0001A0020CombatPolicyTest.ruptureUsesNativeDefenseOrStrictArmorFallback` cobre as três rotas.

## Pendências

**Nenhuma bloqueante.** A antiga P-A0011-01 deixou de existir porque a condição de alvo pesado foi removida do design canônico em vez de ser substituída por heurística.

## Testes

- [x] valores e gasto de Fúria;
- [x] defesa nativa;
- [x] rejeição de alvo observavelmente desprotegido;
- [x] fallback de penetração-only;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA com boundary retroativo explícito; o efeito, ranks, gate e providers principais não mudaram.
- **RPG Skill Tree:** continua authority exclusiva da Fúria e da deduplicação/consumo da perk; uma ação causal pode gastar Fúria uma única vez.
- **Black Arcana / Enshrouded:** Arcane Resistance, Corruption Resistance, Arcane Strain, `ARCANE_BACKLASH`, Shroud, Exposure e Madness não qualificam como guarda/postura nem defesa física do fallback.
- **Mobstein 5.4.4:** dano de ally/bodyguard ressuscitado permanece Mobstein-owned e não pode gastar Fúria do dono nem ativar A0011 em seu nome; ataque direto do jogador contra entidade Mobstein continua normal quando o receipt Epic Fight é válido.
- **Volcanoes:** NÃO DEVE SER INTEGRADO a A0011; Atmosphere, pressão, gases, calor e prospecção não são defesa física/guarda da perk.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch confirmou persistência.
- **Chat 2:** revalidar apenas guards/provenance contra essas exclusões; não criar bridge nova nem converter resistência ambiental/arcana em defesa física.

## Chat 2 — revalidação de implementação — PR #237

- [x] Hook/gate existentes preservados conforme o contrato aprovado.
- [x] Provider-native Epic Fight `21.17.3.1` protegido por gate de versão exata.
- [x] Guarda/postura mágica ou ambiental não é aceita como defesa física.
- [x] Fallback de Armor continua restrito a penetração-only quando guarda/postura não é observável.
- [x] Deduplicação e gasto de Fúria permanecem por root action.
- [x] Provenance indireta/companion/magia é fail-closed no policy antes de qualquer bônus marcial.
- [x] Regressões JUnit e NeoForge GameTests verdes no CI #2147 no mesmo HEAD revalidado.
- [x] Build, JAR e dedicated-server smoke verdes no CI #2147 no mesmo HEAD revalidado.

**Estado Chat 2:** `IMPLEMENTAÇÃO VALIDADA EM CI`; confirmação definitiva ocorre com o merge da PR #237 na `main`.

## Chat 3 — auditoria pós-merge e correção causal — PR #250

### P-A0011-02 — gasto de Fúria prematuro no PRE

- **Pendência encontrada:** o runtime mergeado consumia 20 de Fúria dentro de `beforeHit(...)`, antes de existir confirmação de dano efetivo. Um ataque cancelado ou reduzido a zero podia perder Fúria sem produzir o golpe consumidor.
- **Causa técnica:** elegibilidade PRE era tratada como commit irreversível.
- **Correção:** o PRE agora cria uma reserva transitória bounded por `rootActionId`; os modificadores de Ruptura são calculados normalmente, mas o débito de 20 Fúria só ocorre no POST após `direct && hostile && actualDamage`.
- **Interação A0012:** a reserva reduz a **Fúria efetivamente disponível** para avaliar Frenesi/Pico no mesmo PRE, preservando a ordem sistêmica histórica sem alterar a Fúria bruta antes do hit confirmado. A0012 continua com sua transação PRE conforme o contrato aprovado e não foi redesenhada.
- **Ordem no POST:** o commit de A0011 ocorre antes do ganho de Fúria de A0010 no mesmo hit; portanto o custo não pode ser pago com Fúria criada pelo próprio golpe.
- **Deduplicação:** um `rootActionId` só pode efetivar o débito uma vez; POST duplicado não duplica consumo.
- **TDD RED:** `RPG Skill Tree CI` #2256, commit `64e4abd9eacc45caf7f4af67b4015be9d7ef4bf9`, executou 123 testes com exatamente 3 falhas novas, incluindo `a0011DefersFurySpendUntilConfirmedDamageAndReservesCostForFrenzyGate`.
- **TDD GREEN de código:** `RPG Skill Tree CI` #2269 no HEAD `1698bdc518f84ae99da6a9f6da1a78ad5b9f3923` ficou verde em core, JUnit, NeoForge GameTests, validações, build, JAR e dedicated-server smoke; nove workflows auxiliares também ficaram verdes.
- **Estado:** `P-A0011-02 RESOLVIDA` na PR #250; confirmação definitiva ocorre com o merge na `main`.
