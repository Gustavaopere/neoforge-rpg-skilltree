# Auditoria Chat 1 V3 — A0011–A0020

Data do ciclo: **2026-08-30 (America/Sao_Paulo)**.

## Escopo formal

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** exatamente 10 perks consecutivas.
- **A0001–A0010:** lote anterior, fechado pela PR #217; não reaberto.
- **A0021+:** fora do escopo; não iniciado.
- **Protocolo:** `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

## Fontes obrigatórias

Foram usados integralmente os arquivos consolidados anexados ao projeto: critérios, Gameplay e Sistemas, Mods de Magia, Mods de Tecnologia e protocolo do Chat 1. Também foram usados Catálogo Mestre do Notion, dez dossiês, `STATUS.md`, auditoria histórica, código/testes da `main@fc6686725369cd703169ca59bde69a3a0ee80dc3` e providers exatos pertinentes.

## Re-fetch fresco do Notion

A0011–A0020 foram buscadas novamente individualmente. Os 10 registros persistidos correspondem ao design canônico dos dossiês. **Nenhuma mutação adicional foi necessária** porque não houve drift ou contradição de design.

| Código | Notion | Design | Estado técnico relevante |
|---|---|---|---|
| A0011 | PASS | APROVADA | provider-native/fallback físico estrito |
| A0012 | PASS | APROVADA | implementação pendente P-A0012-01/02 |
| A0013 | PASS | APROVADA | classificação SPEAR fail-closed |
| A0014 | PASS | APROVADA | cadência provider-native |
| A0015 | PASS | APROVADA | crítico canônico único |
| A0016 | PASS | APROVADA | reach/miss/stagger provider-native |
| A0017 | PASS | APROVADA | fallback legítimo; P-A0017-01 |
| A0018 | PASS | APROVADA | crossing/janela/lockout |
| A0019 | PASS | APROVADA | classificação DAGGER fail-closed |
| A0020 | PASS | APROVADA | cadência provider-native |

## Matriz dos nove eixos

| Código | Gates | Integração | Identidade | Topologia | Especialização | PT-BR | Notion | NeoVitae | Providers | Design |
|---|---|---|---|---|---|---|---|---|---|---|
| A0011 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0012 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS NO DESIGN | APROVADA |
| A0013 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0014 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0015 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0016 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0017 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS/FALLBACK | APROVADA |
| A0018 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0019 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0020 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |

## 18 critérios técnicos — decisão

1. **Efeito real:** PASS; A0017 omite deslocamento sem receipt.
2. **Provider-native first:** PASS.
3. **Sem mecânica inventada:** PASS.
4. **Fail-closed:** PASS no design.
5. **Fallback preserva identidade:** PASS.
6. **Mastery discreta:** PASS — milestones persistentes.
7. **Anti-farm/rebuild:** PASS — `DiscoveryProgress`.
8. **Autoria causal:** PASS — fake player/creative/spectator rejeitados.
9. **Pipeline canônico:** PASS — crítico/estados/custos únicos.
10. **Custos reais:** PASS — Fúria, Cold Sweat CORE, exhaustion e stamina reais.
11. **Sem geração gratuita:** PASS.
12. **Read-only:** N/A justificado.
13. **Versionamento explícito:** **PASS NO DESIGN / PENDENTE NA IMPLEMENTAÇÃO DE A0012**. O contrato exige Cold Sweat **exatamente 2.4.2**, mas o runtime atual usa `startsWith("2.4.2")`, o que pode aceitar uma versão distinta como `2.4.20`. Isso é P-A0012-01 e bloqueia `IMPLEMENTAÇÃO CONFIRMADA` até correção.
14. **Coerência estrutural:** PASS.
15. **Dependências semânticas:** PASS.
16. **Sem sobreposição indevida:** PASS.
17. **Implementável:** PASS no design; A0012/A0017 documentam pendências reais sem heurística.
18. **Pós-escrita:** PASS/N/A — nenhuma escrita Notion necessária neste ciclo; 10/10 re-fetches confirmaram persistência.

## Evidência técnica

### Gates/topologia

`CombatPerkTreeModel` representa `epic_axe`, `epic_spear`, `epic_dagger`, ranks das dependências e mastery 80 para A0012/A0018. Não há border hopping semântico.

### Epic Fight 21.17.3.1

`A0001A0020EpicFightHooks` registra PRE/POST, attack speed, dodge, stunned, phase-end e tick. Famílias vêm de `CapabilityItem`; reach de lança usa `entityInteractionRange + capability.getReach()`; miss usa phase-end; stagger forte aceita `LONG`, `KNOCKDOWN`, `NEUTRALIZE`; hits POST exigem dano >0; crítico compartilha root action.

### A0011

Armor só qualifica fallback quando guarda/postura não é observável. Quando o provider observa alvo sem guarda, Armor não é atalho. Gasto de Fúria é deduplicado.

### A0012 — design correto, runtime com hardening pendente

O design exige Cold Sweat **2.4.2 exato**, `Temperature.Trait.CORE`, ordem CORE→exhaustion→benefício e fail-closed. `payFrenzyBodyCost` já preserva a ordem causal.

O review da PR #219 encontrou duas pendências reais:

- **P-A0012-01 — versão:** `ColdSweatFrenzyBridge.supportsVersion()` usa `startsWith("2.4.2")`; isso não é validação exata/segment-aware e pode aceitar versão não auditada. Chat 2 deve corrigir sem ampliar a faixa suportada silenciosamente.
- **P-A0012-02 — diagnóstico:** `resolve()`/`addCoreHeat()` engolem falhas de reflection/invocação e retornam indisponível sem diagnóstico. O fail-closed é mecanicamente seguro, mas precisa de diagnóstico bounded/rate-limited ou de inicialização para troubleshooting, sem spam por tick/hit.

As duas pendências **bloqueiam IMPLEMENTAÇÃO CONFIRMADA de A0012**, não o design fechado pelo Chat 1.

### A0016–A0018

Faixa ideal 70–100%; Controle de Distância é transient server-authoritative; A0017 usa `deltaMovement` apenas para aproximação geométrica e não para reescrever movimento; A0018 usa crossing, 3 cargas e lockout por alvo.

### Mastery

`EpicFightProgressionHooks` concede milestones uma vez por tipo hostil/skill; guard exige skill pagável e alvo hostil; dodge é milestone único; fake players/creative/spectator são rejeitados. Dano repetido não gera XP contínua.

### NeoVitae

Busca em `src/` retorna **0 ocorrências**. Referências documentais são proibições/histórico, não provider ativo.

## Cobertura dos três guias

- **Epic Fight 21.17.3.1:** provider central.
- **Weapons of Miracles/Epic Fight Compat:** armas externas só entram quando a capability Epic Fight as classifica.
- **ParCool/Epic ParCool:** não criar segunda stamina.
- **Pufferfish's Attributes 0.8.3 / Apothic Attributes 2.10.1 / Additional Attributes:** não autorizam segunda rolagem crítica nem pipeline paralelo de penetração por-hit.
- **Cold Sweat 2.4.2:** owner de temperatura corporal; Ecliptic Seasons não substitui CORE e sede não é proxy de exhaustion.
- **Magia:** nenhum provider mágico é fonte direta de família/Fúria/Controle de Distância deste lote; não integrar artificialmente.
- **Weight 1.2.0:** massa de contraptions, não encumbrance do jogador.
- **Protection Pixel 2.2.1:** rechecado; não fornece família Epic Fight, Fúria, Controle de Distância nem receipt de movimento ofensivo para A0017. Não criar integração nominal artificial.
- **Sable/Ragdoll:** física/apresentação não constitui receipt ofensivo Epic Fight.

## Pendências para Chat 2

### P-A0012-01 — comparação de versão

Corrigir o match de Cold Sweat para versão exata/segment-aware de 2.4.2. Versão não auditada deve deixar o bridge indisponível.

### P-A0012-02 — diagnóstico bounded

Adicionar diagnóstico bounded para incompatibilidade de versão, símbolo ausente e falha de invocação, preservando fail-closed e evitando spam.

### P-A0017-01 — deslocamento ofensivo

Não há receipt provider-native comprovado para modular somente o deslocamento da ação ofensiva. Manter janela + impacto/pressão e **não** usar `deltaMovement`/velocidade/animação como autorização. Se surgir API nova, retornar ao Chat 1 antes de ativar o componente.

## Testes obrigatórios

Além da matriz existente, Chat 2 deve adicionar/regredir especificamente:

- A0012: match exato/segment-aware, rejeição de `2.4.20`/versões não auditadas e diagnóstico bounded sem spam;
- A0012: ordem CORE→exhaustion→benefício, baseline/pico/gasto, Queda de Ritmo 6/5/4;
- A0017: nenhuma alteração genérica de movimento;
- demais testes de famílias, crítico, reach, miss, stagger, crossing e lockout;
- GameTests, NeoForge build e dedicated-server smoke.

## Conclusão

**A0011–A0020 = 10/10 APROVADAS NO DESIGN.**

Há três pendências técnicas reais a respeitar: P-A0012-01 e P-A0012-02 bloqueiam `IMPLEMENTAÇÃO CONFIRMADA` de A0012; P-A0017-01 mantém apenas a parcela de deslocamento em fail-closed. Nenhuma delas autoriza redesenho silencioso.

O closeout documental pode ser mergeado como fechamento do **Chat 1**, desde que a PR fique verde e os reviews sejam resolvidos. O Chat 2 deve corrigir as pendências de implementação. Após merge/confirmar `main`, este ciclo para em A0020.