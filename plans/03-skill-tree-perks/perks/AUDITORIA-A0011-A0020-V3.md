# Auditoria Chat 1 V3 — A0011–A0020

Data do ciclo: **2026-08-30 (America/Sao_Paulo)**.

## Escopo formal

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** exatamente 10 perks consecutivas.
- **A0001–A0010:** lote V3 anterior, já fechado pela PR #217; não reaberto.
- **A0021+:** fora do escopo; não iniciado neste ciclo.
- **Protocolo:** `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md` anexado ao projeto.

## Fontes obrigatórias usadas

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` anexado ao projeto;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- Catálogo Mestre — Atributos e Passivos no Notion;
- dez dossiês A0011–A0020;
- `STATUS.md` e auditoria histórica `AUDITORIA-A0001-A0020.md`;
- código real de `main@fc6686725369cd703169ca59bde69a3a0ee80dc3`;
- Epic Fight 21.17.3.1 e Cold Sweat 2.4.2 conforme contratos/versionamento do projeto.

Os três guias consolidados são o snapshot operacional da modlist 28.08.26. Não foi necessário reler os capítulos partidos no GitHub porque não surgiu evidência de snapshot posterior ou contradição com o lote.

## Re-fetch fresco do Notion

A0011–A0020 foram buscadas novamente individualmente no Catálogo Mestre durante este ciclo. Os dez registros persistidos correspondem aos contratos já corrigidos e aos dossiês. **Nenhuma mutação adicional foi necessária**: não houve drift, campo contraditório ou design incompleto que justificasse escrever novamente apenas para alterar timestamp.

| Código | Re-fetch | Mutação no ciclo | Resultado |
|---|---|---|---|
| A0011 | PASS | não | APROVADA |
| A0012 | PASS | não | APROVADA / FAIL-CLOSED por evento |
| A0013 | PASS | não | APROVADA |
| A0014 | PASS | não | APROVADA |
| A0015 | PASS | não | APROVADA |
| A0016 | PASS | não | APROVADA |
| A0017 | PASS | não | APROVADA / FALLBACK LEGÍTIMO |
| A0018 | PASS | não | APROVADA |
| A0019 | PASS | não | APROVADA |
| A0020 | PASS | não | APROVADA |

## Matriz dos nove eixos — A0011–A0020

| Código | Gates | Integração global | Identidade | Topologia | Especialização | PT-BR | Notion | NeoVitae | Modlist/providers | Resultado |
|---|---|---|---|---|---|---|---|---|---|---|
| A0011 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0012 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS/FALLBACK | APROVADA |
| A0013 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0014 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0015 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0016 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0017 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS/FALLBACK | APROVADA |
| A0018 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0019 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0020 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |

## Aplicação dos 18 critérios técnicos

1. **Efeito real:** PASS — todos os componentes ativos possuem facts/hooks concretos; componente ofensivo de deslocamento de A0017 permanece omitido sem receipt.
2. **Provider-native first:** PASS — Epic Fight é owner de famílias, cadência, impacto, armor negation, stagger, reach e stamina; Cold Sweat é owner de `CORE`.
3. **Sem mecânica inventada:** PASS — sem “alvo pesado”, sem alcance por animação, sem movimento ofensivo inferido e sem temperatura paralela.
4. **Fail-closed:** PASS — família/cadência/reach/Cold Sweat e A0017 degradam por omissão segura.
5. **Fallback preserva identidade:** PASS — A0011 mantém penetração-only quando guarda não é observável; A0017 mantém interceptação+impacto sem fabricar controle de movimento.
6. **Mastery discreta:** PASS — `EpicFightProgressionHooks` usa milestones persistentes por tipo hostil/skill, não dano repetitivo.
7. **Anti-farm/rebuild:** PASS — `DiscoveryProgress` impede repetição infinita dos mesmos milestones.
8. **Autoria causal:** PASS — fake player/creative/spectator são rejeitados; hits precisam de autoria real/dano confirmado.
9. **Pipeline canônico:** PASS — crítico A0015 usa o mesmo resolvedor; estados/custos usam services únicos.
10. **Custos reais:** PASS — Fúria, Cold Sweat `CORE`, exhaustion vanilla e `epicfight:stamina_regen` são recursos reais de seus owners.
11. **Sem geração gratuita:** PASS — nenhum output/recurso é fabricado; Fúria/Controle de Distância dependem de ações elegíveis e deduplicadas.
12. **Read-only:** N/A justificado — nenhuma perk do lote é de telemetria/planejamento read-only.
13. **Versão exata:** PASS — Epic Fight `21.17.3.1`; Cold Sweat bridge fixa `2.4.2`.
14. **Coerência estrutural:** PASS — A0012/A0018 são capstones, A0011/A0016/A0017 Notables, fundamentos/ranks/custos coerentes.
15. **Dependências semânticas:** PASS — `CombatPerkTreeModel` representa convergências e mastery 80 dos terminais.
16. **Sem sobreposição indevida:** PASS — stamina não é duplicada com ParCool; crítico não ganha segunda rolagem; providers de atributos não substituem o contrato marcial.
17. **Implementável:** PASS — todos os dossiês explicitam hooks/gates/fallbacks/testes; A0017 explicita o componente que não pode ser implementado sem nova evidência.
18. **Pós-escrita:** PASS/N/A — não houve escrita no Notion neste ciclo porque 10/10 re-fetches já mostraram o design persistido correto; correções anteriores permanecem confirmadas.

## Evidência técnica da `main`

### Gates e topologia

`CombatPerkTreeModel` modela:

- Machados A0007–A0012 com `epic_axe`, mastery 60 na raiz e 80 em A0012;
- Lanças A0013–A0018 com `epic_spear`, mastery 60 na raiz e 80 em A0018;
- Adagas A0019–A0024 com `epic_dagger`, mastery 60 na raiz;
- dependências de rank do Notion convertidas em gates reais, sem border hopping.

### Epic Fight 21.17.3.1

`A0001A0020EpicFightHooks` declara `SUPPORTED_VERSION_PREFIX = "21.17.3.1"` e registra `DELIVER_DAMAGE_PRE/POST`, `MODIFY_ATTACK_SPEED`, `ON_DODGE`, `ON_STUNNED`, `ATTACK_PHASE_END` e `TICK_EPICFIGHT_MODE`.

- famílias vêm de `CapabilityItem.getWeaponCategory()`; desconhecida = fail-closed;
- reach de lança usa `entityInteractionRange + capability.getReach()`;
- miss de lança usa phase-end sem entidades realmente atingidas;
- stagger forte aceita somente `LONG`, `KNOCKDOWN` e `NEUTRALIZE` com fonte hostil;
- hits POST exigem dano modificado > 0 e alvo hostil;
- attack speed usa `ModifyAttackSpeedEvent`;
- crítico compartilha root action canônica.

### A0011

`A0001A0020CombatPolicy` só usa Armor como fallback quando guarda/postura **não é observável**; se o provider observa alvo não defendendo, Armor não é atalho. O gasto de Fúria é deduplicado por `A0011:spend`.

### A0012

`ColdSweatFrenzyBridge` suporta exatamente Cold Sweat 2.4.2 e resolve `Temperature.add(LivingEntity, Trait.CORE, double)` de forma fail-closed. No mesmo PRE, `payFrenzyBodyCost` aplica CORE antes de exhaustion. Somente o receipt positivo autoriza baseline/pico. Queda de Ritmo usa `EpicFightAttributes.STAMINA_REGEN`.

### A0016–A0018

- faixa ideal = 70–100% do alcance efetivo;
- Controle de Distância é estado transient server-authoritative, cap 3, duração 5/7 s;
- A0017 usa movimento apenas para detectar aproximação geométrica e abrir janela;
- o próprio policy omite redução de deslocamento ofensivo até existir receipt provider-native;
- A0018 usa crossing fora→dentro, 3 cargas, janela 3/3,5/4 s e lockout de 8 s por alvo.

### Mastery/anti-abuso

`EpicFightProgressionHooks`:

- hit comum repetido não dá XP continuamente;
- cada categoria de arma ganha milestone somente uma vez por tipo hostil;
- guard usa tipo hostil distinto e skill de stamina realmente pagável;
- skills não-guard são únicas por `skillId`;
- dodge bem-sucedido é milestone único;
- fake players, creative e spectator são rejeitados.

### NeoVitae

Busca em `src/` para `NeoVitae` retorna **0 ocorrências**. Referências documentais que aparecem em buscas gerais são declarações históricas/proibições, não provider ativo.

## Cobertura dos três guias

### Gameplay e sistemas

- **Epic Fight 21.17.3.1:** provider central do lote.
- **Weapons of Miracles 2.0.176 / Epic Fight Compat 1.1.0:** armas externas só participam quando terminam classificadas pelas capabilities do Epic Fight; não criam famílias paralelas.
- **ParCool 4.0.0.2 + Epic ParCool:** não criar segunda stamina; A0012 atua no atributo real `epicfight:stamina_regen`.
- **Pufferfish's Attributes 0.8.3 / Apothic Attributes 2.10.1 / Additional Attributes:** não autorizam segunda rolagem crítica nem duplicação da penetração por-hit do contrato.
- **Cold Sweat 2.4.2:** owner da temperatura corporal de A0012; Ecliptic Seasons não substitui temperatura corporal e Thirst Was Reclaimed não é proxy de exhaustion.

### Magia

Nenhum sistema mágico é provider direto de família de machado/lança/adaga, Fúria ou Controle de Distância deste lote. Fundamental Principles, Ars 'n' Spells e bridges de Iron's permanecem autoritativos em seus próprios recursos/progressões e não devem ser conectados artificialmente a A0011–A0020.

### Tecnologia

- **Weight 1.2.0:** massa de contraptions Aeronautics/Sable, não encumbrance do jogador; não pertinente às perks deste lote.
- **Create: Protection Pixel 2.2.1:** provider periférico explicitamente rechecado. Suas peças têm efeitos próprios de armadura, mas não fornecem família Epic Fight, Fúria, Controle de Distância nem receipt seguro de movimento ofensivo para A0017. Classificação: **NÃO PERTINENTE AOS EFEITOS DESTE LOTE / não criar integração nominal artificial**.
- stack Sable/Ragdoll: física corporal/apresentação; não constitui receipt de corrida/investida Epic Fight para habilitar a parcela omitida de A0017.

## Pendências e fail-closed aprovados

### P-A0017-01 — redução de deslocamento ofensivo

**ABERTA / NÃO BLOQUEIA O DESIGN FECHADO.**

Não foi comprovado um receipt Epic Fight 21.17.3.1 que identifique a mesma ação como movimento ofensivo e permita modular somente seu deslocamento. A0017 fica aprovada no fallback canônico: janela de Interceptação + impacto/pressão; a redução de deslocamento permanece inativa.

O Chat 2 não pode resolver isso com `deltaMovement`, velocidade vanilla, animação ou heurística. Se aparecer API/provider novo, o ponto deve voltar ao Chat 1 antes de ativar o componente.

### A0012 — fail-closed por evento

Cold Sweat ausente/incompatível/API não resolvida/escrita CORE falha → nenhum bônus de Frenesi, nenhum gasto de pico e nenhuma Queda de Ritmo fictícia naquele contexto.

## Testes obrigatórios para implementação/manutenção

- A0011: defesa nativa, alvo observavelmente sem defesa e fallback Armor-only;
- A0012: bridge CORE fail-closed, ordem causal CORE→exhaustion→benefício, baseline/pico/gasto, Queda de Ritmo 6/5/4;
- A0013/A0019: família provider-native e categoria desconhecida fail-closed;
- A0014/A0020: `ModifyAttackSpeedEvent` sem substituir por stamina/movimento/dano;
- A0015: uma única resolução crítica/root action;
- A0016: 70–100%, cap, 5/7 s, miss e stagger forte;
- A0017: aproximação, janela 2 s, consumo, impacto; nenhuma modificação genérica de movimento;
- A0018: 3 cargas, janela 3/3,5/4, +15%, +40%, lockout 8 s;
- Mastery: hit repetido não concede XP; milestones finitos e atribuíveis;
- CI, NeoForge build, GameTests e dedicated-server smoke antes do merge do closeout.

## Conclusão

**A0011–A0020 = 10/10 APROVADAS no design.**

Não há bloqueio de design. A0017 permanece deliberadamente em fallback canônico para sua parcela de deslocamento; A0012 permanece fail-closed quando o custo corporal real não puder ser confirmado. Esses estados são parte do design aprovado, não autorização para heurísticas.

Este lote só recebe a conclusão operacional **LOTE FECHADO** após a PR deste closeout ficar verde, ser mergeada na `main` e a `main` pós-merge ser confirmada. O ciclo deve parar em A0020.