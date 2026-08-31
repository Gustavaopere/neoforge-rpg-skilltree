# A0005 — Abertura de Guarda

## Status e proveniência

- **Design:** APROVADO após correção canônica.
- **Código relevante:** PRESENTE com fallback corrigido nesta auditoria.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db816cb407cc16ebe41066
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- Requer A0002 ≥2 + A0004 e pelo menos 3 Ímpeto.
- Mesmo alvo após sequência limpa; consome 2 Ímpeto; cooldown 6 s/alvo.
- Defesa provider-native observável e ativa: até +12% penetração física e +8% impacto/pressão de guarda no golpe consumidor.
- Se guarda/postura **não for observável**, somente defesa física server-side comprovável pode qualificar o fallback; nesse caso há apenas penetração, nunca impacto/pressão inventados.
- Se o provider observa explicitamente que o alvo não está defendendo, Armor não é atalho para ativar A0005.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0002/A0004 obrigatórios.
2. **Integração global:** PASS — consome Ímpeto e usa `IMPACT`/`ARMOR_NEGATION` nativos.
3. **Identidade:** PASS — janela ofensiva condicionada a execução e defesa real.
4. **Topologia:** PASS — Notable camada 3.
5. **Especializações:** PASS — permanece exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após correção da ambiguidade de fallback.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — Epic Fight principal; fallback só com prova física server-side.

## Evidência técnica

- `NotionCombatPerkRules`: threshold 3, custo 2, penetração 0,12, impacto 1,08, cooldown 6 s.
- `A0001A0020CombatPolicy.beforeHit`: distingue `nativeDefense` de `armorFallback`.
- Rota nativa exige guarda/postura real; rota fallback exige hook defensivo indisponível + Armor comprovada + penetração disponível.
- Fallback não aplica impacto/pressão.
- `A0001A0020CombatPolicyTest.openingFallbackRequiresConfirmedArmorAndOmitsImpact` cobre alvo observavelmente desprotegido e fallback estrito.

## Pendências

**Nenhuma bloqueante.** A antiga P-A0005-01 foi resolvida no design e no policy.

## Testes

- [x] consumo e cooldown por alvo;
- [x] defesa nativa;
- [x] rejeição quando o provider observa ausência de guarda;
- [x] fallback de penetração-only por defesa física comprovável;
- [x] ausência de impacto no fallback;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependências, threshold, custo, cooldown, hook e fallback permanecem persistidos sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Provider-native first:** quando guarda/postura é observável, somente estado defensivo real qualifica; alvo explicitamente não defendendo não pode usar Armor como atalho.
- **Fallback aprovado:** quando guarda/postura não é observável, defesa física server-side comprovável permite somente a parcela de penetração. Impacto/pressão de guarda ficam omitidos.
- **Fail-closed:** sem defesa observável nem defesa física comprovável, a perk não ativa. Não inferir guarda por vida, aparência, animação ou dano recebido.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Gate, threshold, custo e cooldown por alvo implementados.
- [x] Rota provider-native de defesa implementada.
- [x] Fallback Armor-only preserva somente penetração e exige defesa física comprovável.
- [x] Alvo observavelmente não defendendo não ativa via Armor.
- [x] Deduplicação/pipeline único implementados.
- [x] Regressão do fallback estrito presente.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; authority de Ímpeto, cooldown e deduplicação permanece no RPG. Epic Fight fornece o estado de guarda/postura e os atributos físicos do golpe.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO`; pressão atmosférica/hidrostática, protection equipment, gases ou hazards não são guarda física do alvo e não qualificam A0005.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO`; Shroud/Exposure e `MagicResistanceService` não são guarda/postura nem defesa física elegível para o fallback.
- **Black Arcana:** boundary de exclusão explícito. `Arcane Resistance`, `Corruption Resistance`, `Arcane Strain` e `ARCANE_BACKLASH` não são evidência de defesa física/guarda e não podem ativar o fallback. Ataque direto contra entidade Black Arcana pode usar A0005 apenas quando a defesa física real exigida estiver comprovada.
- **Mobstein 5.4.4:** ataque direto do jogador pode abrir guarda de um alvo se o contrato físico real for satisfeito; ataque de ally/bodyguard ressuscitado não consome Ímpeto do dono nem abre guarda em seu nome.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos nesta retroauditoria; re-fetch confirmou persistência.
- **Fail-closed:** ausência de guarda/postura ou defesa física comprovável continua inativa; nunca usar resistência arcana, Shroud, aparência, vida ou ownership de companion como proxy.
- **Estado histórico:** implementação da #221 já mergeada; retroauditoria não altera runtime.

## Chat 3 — auditoria pós-merge e correção causal — PR #244

- **Pendência encontrada:** `P-A0005-02` — o runtime mergeado consumia 2 de Ímpeto e iniciava o cooldown de 6 s no `beforeHit`/PRE, antes da confirmação de dano efetivo. Um ataque posteriormente cancelado ou zerado podia deixar gasto e cooldown fantasma.
- **Causa técnica:** a primeira implementação tratava a elegibilidade PRE como commit definitivo, embora o contrato sistêmico atual exija mutação irreversível somente no POST confirmado.
- **Correção:** o PRE agora apenas prepara uma transação transitória e limitada por `rootActionId`; `afterConfirmedHit` só efetiva 2 de Ímpeto + cooldown após `direct && hostile && actualDamage`. Sem POST válido não existe gasto/cooldown: a preparação é bounded e removida por descarte explícito quando o policy recebe resultado inválido ou por limpeza/expiração transitória.
- **Deduplicação:** o commit é associado à mesma ação raiz; uma preparação só pode ser efetivada uma vez.
- **TDD RED:** `RPG Skill Tree CI` #2193 falhou intencionalmente em `a0005DefersMomentumSpendAndCooldownUntilConfirmedDamagePost`, provando o defeito antigo.
- **TDD GREEN:** `RPG Skill Tree CI` #2203 ficou verde no HEAD `cc7ba795437943a962cdb5e33cd350f92d0ac123`, incluindo core, JUnit, NeoForge GameTests, build, JAR e dedicated-server smoke.
- **Estado da pendência:** `P-A0005-02 RESOLVIDA` na PR #244; confirmação definitiva ocorre com o merge desta PR na `main`.

## Reauditoria delta Simply Swords — 2026-08-30

- **Cobertura:** armas Simply entram somente pela classificação `SWORD` do Epic Fight Compat; o material/provider não é prova de guarda.
- **Anti-double-dip:** armor ignore/sunder, Deflect, Bleed, ability hit, gem power e traits Simply não são receipts de A0005 e não são reexecutados pelo RPG.
- **Penetração:** se o mesmo root já possuir ignore/penetration provider-native, A0005 aplica somente sua parcela canônica uma vez e preserva caps/ordem globais.
- **Derived hits:** ability/proc separado não reabre A0005 nem consome Ímpeto novamente.
- **Simply Tooltips:** `NÃO DEVE SER INTEGRADO`; Simply More Unique não comprovada permanece FAIL-CLOSED.
- **Notion:** provider/hook/fallback/regra atualizados e re-fetch PASS.
- **Runtime:** inalterado; Chat 2 deve acrescentar regressão provider-present quando integrar o stack.
