# A0012 — Maestria de Machados — Frenesi do Saqueador

## Status e proveniência

- **Design:** APROVADO após correção canônica e re-fetch.
- **Código relevante:** PRESENTE, mas com duas pendências de hardening identificadas no review da PR #219.
- **Implementação:** **NÃO CONFIRMADA** até P-A0012-01 e P-A0012-02 serem corrigidas pelo Chat 2.
- **Notion:** https://app.notion.com/p/3c569db9f0db81f6806cf743fda053f5
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica corrigida

- **Função:** Capstone de Machados; 1 rank, custo 2.
- **Gate:** `epic_axe` + A0010 + A0011 + `epicfight:axe` ≥80.
- **Providers:** Epic Fight 21.17.3.1 + Fúria canônica do RPG Skill Tree + Cold Sweat **exatamente 2.4.2** `CORE` + exhaustion do Minecraft/NeoForge.
- **Frenesi:** ativo enquanto Fúria ≥75 e o bridge validado do Cold Sweat estiver operacional.
- **Transação causal:** em `DELIVER_DAMAGE_PRE` direto, hostil e elegível, o runtime deve primeiro aplicar +1,5 em Cold Sweat `Temperature.Trait.CORE`; somente após sucesso aplica +0,015 exhaustion e autoriza o pacote ofensivo.
- **Baseline:** após o pagamento corporal confirmado no mesmo PRE, o golpe recebe total +10% de impacto. A perk nunca fabrica sweep/multihit.
- **Pico:** em Fúria 100, após o mesmo pagamento corporal confirmado, o próximo PRE elegível pode gastar atomicamente 40 Fúria para total +20% de impacto e, quando guarda/postura provider-native está ativa, total +40% de pressão de guarda. O pico substitui o +10% basal naquele golpe.
- **Queda de Ritmo:** transição de ≥75 para <75 com o bridge CORE operacional aplica −15% em `epicfight:stamina_regen` por 6 s; mastery ≥90 reduz para 5 s; ≥100 para 4 s.
- **Fail-closed:** se Cold Sweat estiver ausente, a versão não for exatamente a suportada, a API `CORE` não resolver ou a escrita CORE falhar, nenhum benefício de Frenesi nem gasto do pico ocorre naquele evento. Não existe recurso térmico paralelo nem inferência exhaustion→sede.
- **Diagnóstico obrigatório:** incompatibilidade de versão/API ou falha de resolução/invocação deve gerar diagnóstico bounded/visível o bastante para explicar por que a perk ficou fail-closed, sem spam por tick/evento.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0010/A0011/mastery 80.
2. **Integração global:** PASS — Fúria, Cold Sweat CORE, exhaustion vanilla e stamina Epic Fight mantêm seus owners.
3. **Identidade:** PASS — capstone de alto risco com pressão, custo corporal e recovery penalty.
4. **Topologia:** PASS — terminal camada 4 convergindo os ramos de machados.
5. **Especializações:** PASS — `TERMINAL_EXTERIOR: MARTIAL/MACHADOS`; Gate C apenas quando mapeado explicitamente.
6. **PT-BR:** PASS.
7. **Notion:** PASS após re-fetch da transação causal PRE.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS NO DESIGN / IMPLEMENTAÇÃO PENDENTE — contrato exige Cold Sweat 2.4.2 exato e fail-closed diagnosticável.

## Evidência técnica

- `NotionCombatPerkRules`: threshold 75, pico 100, gasto 40, impacto 1,10/1,20, pressão 1,40, `CORE` 1,5, exhaustion 0,015 e stamina regen −0,15.
- `ColdSweatFrenzyBridge`: resolve `Temperature.add(..., Trait.CORE, ...)` e retorna sucesso/falha da escrita real.
- **Review #219:** o runtime atual usa `startsWith("2.4.2")`, que também aceitaria versões distintas como `2.4.20`; isso não satisfaz o contrato de versão exata.
- **Review #219:** `resolve()`/`addCoreHeat()` atualmente engolem falhas de reflection/invocação sem diagnóstico bounded; o fail-closed funciona mecanicamente, mas fica opaco para diagnóstico.
- `A0001A0020EpicFightHooks.onDamagePre`: valida o mesmo evento server-authoritative, paga CORE primeiro, aplica exhaustion somente após sucesso e só então informa ao policy que o custo corporal foi pago.
- `A0001A0020CombatPolicy.beforeHit`: baseline/pico exigem `frenzyBodyCostPaid`; o gasto de 40 Fúria do pico acontece somente depois desse receipt.
- A0011 continua com precedência abaixo do pico; o adapter não cobra o custo corporal de A0012 se o gasto de A0011 derrubaria a Fúria abaixo de 75 antes de qualquer benefício de Frenesi.
- `NotionCombatPerkState.updateFrenzyState`: somente é alimentado como aprendido/operacional quando o bridge Cold Sweat CORE está disponível, impedindo Queda de Ritmo fictícia sem provider.
- `A0001A0020EpicFightHooks.refreshRhythmDrop`: modificador transitório `ADD_MULTIPLIED_TOTAL` em `EpicFightAttributes.STAMINA_REGEN`.
- lifecycle remove o modificador e limpa estado transitório.

## Pendências técnicas reais para o Chat 2

### P-A0012-01 — validação exata da versão Cold Sweat

- **Severidade:** bloqueia `IMPLEMENTAÇÃO CONFIRMADA`, não o design.
- **Problema:** `supportsVersion()` usa `startsWith("2.4.2")`; versões distintas como `2.4.20` podem passar indevidamente.
- **Correção:** comparação exata ou segment-aware que aceite somente a identidade explicitamente validada pelo contrato (Cold Sweat 2.4.2), salvo nova auditoria que documente uma faixa maior.
- **Fail-closed esperado:** versão não validada → bridge indisponível → nenhum benefício/custo de A0012.

### P-A0012-02 — diagnóstico bounded das falhas do bridge

- **Severidade:** bloqueia `IMPLEMENTAÇÃO CONFIRMADA`, não o design.
- **Problema:** falhas de resolução/reflection/invocação são convertidas silenciosamente em `false`.
- **Correção:** registrar diagnóstico bounded (por inicialização/estado de disponibilidade ou rate-limited, nunca por tick/hit) informando versão incompatível, símbolo ausente ou falha de invocação suficiente para troubleshooting.
- **Fail-closed atual:** seguro mecanicamente; deve ser preservado enquanto o diagnóstico é adicionado.

Thirst Was Reclaimed continua fora de A0012: exhaustion não é proxy de sede.

## Testes

- [x] fail-closed sem receipt de custo corporal;
- [x] baseline +10% somente com custo pago;
- [x] pico em 100 Fúria e gasto de 40 após receipt;
- [x] pico substitui, não multiplica, baseline;
- [x] Queda de Ritmo 6/5/4 s;
- [x] estado de Frenesi não é armado sem bridge CORE operacional;
- [ ] teste de versão exata/segment-aware — P-A0012-01;
- [ ] teste de diagnóstico bounded sem spam — P-A0012-02;
- [ ] dedicated-server smoke após correção das duas pendências.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; a transação causal completa permanece persistida sem divergência.
- **Mutação no Notion neste ciclo:** não necessária; o design já exige provider/versionamento real e fail-closed.
- **Versões/owners do DESIGN:** Epic Fight 21.17.3.1; Cold Sweat exatamente 2.4.2 é owner da temperatura `CORE`; Minecraft/NeoForge é owner de exhaustion; Epic Fight é owner de `stamina_regen`.
- **Ordem obrigatória:** validar PRE elegível → validar provider/version/API → aplicar +1,5 CORE → aplicar +0,015 exhaustion → autorizar baseline/pico → gastar 40 Fúria se pico.
- **Integração global:** não criar segunda temperatura, não converter exhaustion em sede e não duplicar stamina com ParCool/Epic ParCool.
- **Resultado de design:** **APROVADA / FECHADA** no lote A0011–A0020.
- **Resultado de implementação:** **PENDENTE** de P-A0012-01 e P-A0012-02; o Chat 2 deve corrigi-las sem redesenhar a perk.