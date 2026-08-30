# Status dos Dossiês de Perks

A fonte canônica de design permanece o Notion. O protocolo do Chat 1 trabalha em **lotes exatos de 10 perks** e somente formaliza `LOTE FECHADO` após dossiês/auditoria/status, PR verde, merge e confirmação da `main`.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0002 | Treino com Espadas II | APROVADO/FECHADO Chat 1 V3 | Presente | nenhuma |
| A0003 | Precisão com Espadas | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO/FECHADO Chat 1 V3 | Presente; hit, dodge, miss, decay e stagger forte provider-native | nenhuma |
| A0005 | Abertura de Guarda | APROVADO/FECHADO Chat 1 V3 | Presente; defesa nativa ou fallback estrito de penetração | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO/FECHADO Chat 1 V3 | Presente; defesa técnica confirmada, janela, cooldown e dedup | nenhuma |
| A0007 | Treino com Machados I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0008 | Treino com Machados II | APROVADO/FECHADO Chat 1 V3 | Presente | nenhuma |
| A0009 | Precisão com Machados | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO/FECHADO Chat 1 V3 | Presente no receipt server-authoritative; demais rotas fail-closed | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO/FECHADO Chat 1 V3 | Presente; sem heurística de “alvo pesado” | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO/FECHADO NO DESIGN Chat 1 V3 | Contrato causal correto; runtime requer hardening | **P-A0012-01 versão exata + P-A0012-02 diagnóstico bounded bloqueiam IMPLEMENTAÇÃO CONFIRMADA** |
| A0013 | Treino com Lanças I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO/FECHADO Chat 1 V3 | Presente via `ModifyAttackSpeedEvent` | nenhuma |
| A0015 | Precisão com Lanças | APROVADO/FECHADO Chat 1 V3 | Presente; crítico no pipeline canônico único | nenhuma |
| A0016 | Distância Ideal | APROVADO/FECHADO Chat 1 V3 | Presente; reach, hit, miss, expiração e stagger provider-native | nenhuma |
| A0017 | Interceptação | APROVADO/FECHADO EM FALLBACK Chat 1 V3 | Janela + impacto/pressão ativos; redução de deslocamento omitida | P-A0017-01 não bloqueia design; componente permanece fail-closed |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO/FECHADO Chat 1 V3 | Presente; crossing, consumo, janela e lockout por alvo | nenhuma |
| A0019 | Treino com Adagas I | APROVADO/FECHADO Chat 1 V3 | Presente; classificação provider-native e fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO/FECHADO Chat 1 V3 | Presente via `ModifyAttackSpeedEvent` | nenhuma |

## Regras sistêmicas consolidadas

- **Provider-native first:** famílias vêm das capabilities do Epic Fight; ausência de classificação segura = fail-closed.
- **Crítico:** uma única resolução canônica/root action; providers/bridges não criam segunda rolagem.
- **Stagger forte:** `ON_STUNNED`; apenas `LONG`, `KNOCKDOWN`, `NEUTRALIZE` com fonte hostil.
- **A0012:** design exige Cold Sweat **exatamente 2.4.2** `CORE`, seguido de exhaustion e só então benefício; runtime atual precisa corrigir match de versão e diagnóstico antes de `IMPLEMENTAÇÃO CONFIRMADA`.
- **Mastery Epic Fight:** milestones persistentes por tipo hostil/skill; dano repetido, fake player, AFK e spam não geram Mastery.
- **A0017:** aproximação geométrica abre janela; redução de deslocamento exige receipt ofensivo provider-native e permanece omitida sem ele.
- **NeoVitae:** busca em `src/` = zero ocorrências.

## Evidência comum

- `NotionCombatPerkRules`, `CombatPerkTreeModel`, `A0001A0020CombatPolicy`, `NotionCombatPerkState`, `A0001A0020CriticalService`.
- `A0001A0020EpicFightHooks`, `ColdSweatFrenzyBridge`, `EpicFightProgressionHooks`.
- `A0001A0020CombatPolicyTest` e CI/GameTests do projeto.

## Chat 1 V3 — A0001–A0010

**Estado operacional:** `LOTE FECHADO`.

- PR **#217** mergeada com CI verde.
- `main` pós-merge confirmada em `fc6686725369cd703169ca59bde69a3a0ee80dc3` antes do ciclo seguinte.

## Chat 1 V3 — ciclo exato A0011–A0020

**Estado de design:** `LOTE FECHADO NO DESIGN` — conclusão operacional do Chat 1 depende do merge deste closeout.

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **A0021+:** fora do escopo; não iniciado.
- **Re-fetch Notion:** 10/10 PASS em 2026-08-30; 0 mutações adicionais.
- **Dossiês:** 10/10 atualizados.
- **Auditoria exata:** `AUDITORIA-A0011-A0020-V3.md`.
- **Design:** 10/10 APROVADAS pelos nove eixos e 18 critérios no nível de contrato.
- **A0012:** P-A0012-01 e P-A0012-02 **bloqueiam IMPLEMENTAÇÃO CONFIRMADA**, não o design. Chat 2 deve corrigir validação exata/segment-aware da versão Cold Sweat 2.4.2 e adicionar diagnóstico bounded do bridge, preservando fail-closed.
- **A0017:** P-A0017-01 mantém somente a parcela de deslocamento em fail-closed; janela + impacto/pressão são o fallback aprovado.
- **Cobertura periférica:** Weapons of Miracles/Epic Fight Compat somente via capability; Protection Pixel sem hook pertinente; Weight 1.2.0 não é encumbrance; stacks mágicos não devem ser conectados artificialmente.
- **Regra para Chat 2:** não redesenhar; corrigir P-A0012-01/02, preservar A0017 fail-closed e executar testes/CI dedicados.

Após PR verde, merge e confirmação da `main`, este ciclo do Chat 1 passa a **`LOTE FECHADO`** e deve **PARAR em A0020**.