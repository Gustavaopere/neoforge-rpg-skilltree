# A0012 — Maestria de Machados — Frenesi do Saqueador

## Status e proveniência

- **Design:** APROVADO após correção canônica e re-fetch.
- **Código relevante:** IMPLEMENTADO nesta auditoria.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81f6806cf743fda053f5
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica corrigida

- **Função:** Capstone de Machados; 1 rank, custo 2.
- **Gate:** `epic_axe` + A0010 + A0011 + `epicfight:axe` ≥80.
- **Providers:** Epic Fight 21.17.3.1 + Fúria canônica do RPG Skill Tree + Cold Sweat 2.4.2 `CORE` + exhaustion do Minecraft/NeoForge.
- **Frenesi:** ativo enquanto Fúria ≥75 e o bridge versionado do Cold Sweat estiver operacional.
- **Transação causal:** em `DELIVER_DAMAGE_PRE` direto, hostil e elegível, o runtime deve primeiro aplicar +1,5 em Cold Sweat `Temperature.Trait.CORE`; somente após sucesso aplica +0,015 exhaustion e autoriza o pacote ofensivo.
- **Baseline:** após o pagamento corporal confirmado no mesmo PRE, o golpe recebe total +10% de impacto. A perk nunca fabrica sweep/multihit.
- **Pico:** em Fúria 100, após o mesmo pagamento corporal confirmado, o próximo PRE elegível pode gastar atomicamente 40 Fúria para total +20% de impacto e, quando guarda/postura provider-native está ativa, total +40% de pressão de guarda. O pico substitui o +10% basal naquele golpe.
- **Queda de Ritmo:** transição de ≥75 para <75 com o bridge CORE operacional aplica −15% em `epicfight:stamina_regen` por 6 s; mastery ≥90 reduz para 5 s; ≥100 para 4 s.
- **Fail-closed:** se Cold Sweat estiver ausente, incompatível, a API `CORE` não resolver ou a escrita CORE falhar, nenhum benefício de Frenesi nem gasto do pico ocorre naquele evento. Não existe recurso térmico paralelo nem inferência exhaustion→sede.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0010/A0011/mastery 80.
2. **Integração global:** PASS — Fúria, Cold Sweat CORE, exhaustion vanilla e stamina Epic Fight mantêm seus owners.
3. **Identidade:** PASS — capstone de alto risco com pressão, custo corporal e recovery penalty.
4. **Topologia:** PASS — terminal camada 4 convergindo os ramos de machados.
5. **Especializações:** PASS — `TERMINAL_EXTERIOR: MARTIAL/MACHADOS`; Gate C apenas quando mapeado explicitamente.
6. **PT-BR:** PASS.
7. **Notion:** PASS após re-fetch da transação causal PRE.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS/FALLBACK — Cold Sweat 2.4.2 é integrado por API pública e versionada; falha da escrita CORE desativa somente A0012 naquele evento.

## Evidência técnica

- `NotionCombatPerkRules`: threshold 75, pico 100, gasto 40, impacto 1,10/1,20, pressão 1,40, `CORE` 1,5, exhaustion 0,015 e stamina regen −0,15.
- `ColdSweatFrenzyBridge`: exige exatamente Cold Sweat `2.4.2`, resolve `Temperature.add(..., Trait.CORE, ...)` e retorna sucesso/falha da escrita real.
- `A0001A0020EpicFightHooks.onDamagePre`: valida o mesmo evento server-authoritative, paga CORE primeiro, aplica exhaustion somente após sucesso e só então informa ao policy que o custo corporal foi pago.
- `A0001A0020CombatPolicy.beforeHit`: baseline/pico exigem `frenzyBodyCostPaid`; o gasto de 40 Fúria do pico acontece somente depois desse receipt.
- A0011 continua com precedência abaixo do pico; o adapter não cobra o custo corporal de A0012 se o gasto de A0011 derrubaria a Fúria abaixo de 75 antes de qualquer benefício de Frenesi.
- `NotionCombatPerkState.updateFrenzyState`: somente é alimentado como aprendido/operacional quando o bridge Cold Sweat CORE está disponível, impedindo Queda de Ritmo fictícia sem provider.
- `A0001A0020EpicFightHooks.refreshRhythmDrop`: modificador transitório `ADD_MULTIPLIED_TOTAL` em `EpicFightAttributes.STAMINA_REGEN`.
- lifecycle remove o modificador e limpa estado transitório.

## Pendências

**Nenhuma bloqueante dentro do contrato corrigido.** Thirst Was Reclaimed não participa de A0012: exhaustion não é proxy de sede. Novas integrações só poderão ser adicionadas com contrato versionado e causal.

## Testes

- [x] fail-closed sem receipt de custo corporal;
- [x] baseline +10% somente com custo pago;
- [x] pico em 100 Fúria e gasto de 40 após receipt;
- [x] pico substitui, não multiplica, baseline;
- [x] Queda de Ritmo 6/5/4 s;
- [x] estado de Frenesi não é armado sem bridge CORE operacional;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Chat 2 — implementação, testes e merge — PR #224

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #224.

- [x] P-A0012-01 resolvida: `supportsVersion` aceita somente a versão auditada exata `2.4.2`; `2.4.20`, `2.4.2.1`, prereleases e outras versões ficam fail-closed.
- [x] P-A0012-02 resolvida: incompatibilidade de versão, falha de resolução da API e falha de invocação possuem diagnóstico bounded one-shot por classe/chave de falha.
- [x] Probe de compatibilidade executado na inicialização quando Cold Sweat está presente.
- [x] Falha de versão/API/escrita continua fail-closed e não autoriza benefício, exhaustion ou gasto do pico.
- [x] CORE +1,5 é pago antes de exhaustion +0,015 e antes do pacote ofensivo no mesmo PRE.
- [x] Pico só gasta 40 Fúria depois do receipt corporal confirmado.
- [x] Queda de Ritmo continua usando `epicfight:stamina_regen`, sem stamina paralela.
- [x] Nenhuma inferência de sede foi adicionada.
- [x] RED P-A0012-01: CI #2028 falhou no JUnit exatamente no contrato de versão.
- [x] RED P-A0012-02: CI #2033 falhou no JUnit exatamente no contrato de diagnóstico bounded.
- [x] `ColdSweatFrenzyBridgeTest` cobre versão exata e deduplicação do gate diagnóstico.
- [x] `RPG Skill Tree CI` #2036 GREEN no SHA `bda08ca9748ad16d3352d0872f753976731424f8`.
- [x] JUnit, NeoForge GameTests, build, built-JAR verification e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma bloqueante para A0012. As duas pendências técnicas entregues pelo Chat 1 foram fechadas sem redesenho.
