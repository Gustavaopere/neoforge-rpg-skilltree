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
