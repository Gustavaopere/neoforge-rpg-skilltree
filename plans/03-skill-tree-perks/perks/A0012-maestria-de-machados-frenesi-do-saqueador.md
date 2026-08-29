# A0012 — Maestria de Machados — Frenesi do Saqueador

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** CONTRATO/COEFICIENTES PRESENTES; efeito de runtime deliberadamente FAIL-CLOSED.
- **Implementação integral:** NÃO IMPLEMENTADA.
- **Notion:** https://app.notion.com/p/3c569db9f0db81f6806cf743fda053f5
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0012
- **Nome:** Maestria de Machados — Frenesi do Saqueador
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Saqueador — Frenesi
- **Camada:** 4
- **Função na Árvore:** Capstone
- **Tier:** Grande
- **Faixa de Poder:** Alto
- **Ranks Máx.:** 1
- **Custo por Rank:** 2
- **Dependências Obrigatórias:** A0010 + A0011 + maestria `epicfight:axe` ≥ 80; ponte/rota alternativa não substitui requisitos.
- **Pré-requisitos:** A0010 + A0011.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree + Cold Sweat 2.4.2 para temperatura corporal + Minecraft/NeoForge para hunger/exhaustion; Thirst Was Reclaimed 3.0.4 somente se adapter versionado expuser custo hídrico causal da própria ação.
- **Efeito:** ao alcançar 75 de Fúria usando machado, entra em Frenesi enquanto permanecer ≥75. Golpes diretos válidos recebem +10% de impacto e só aproveitam varredura/multi-hit que já exista no ataque/provider. Cada ação ofensiva válida em Frenesi aplica ×1,25 ao parcel térmico/metabólico causal quando disponível e ×1,15 ao hunger/exhaustion causado pela própria atividade. Sede recebe ×1,15 apenas com receipt hídrico causal. Em 100 de Fúria, o próximo golpe pesado inequivocamente confirmado pode consumir 40 para +20% de impacto e +40% de pressão de guarda. Sair abaixo de 75 encerra Frenesi e aplica Queda de Ritmo por 6 s, reduzindo em 15% a recuperação canônica de estamina.
- **Escalonamento:** 1 rank. Maestria ≥90 reduz Queda de Ritmo para 5 s; ≥100 para 4 s. Limiar 75, gasto 40 e coeficientes não aumentam.
- **Gate:** Gateway `epic_axe` + A0010 + A0011 + mastery de machados ≥80; terminal da Árvore Exterior.
- **Hook:** Fúria + golpe direto/heavy confirmado + impacto/guarda provider-native + Cold Sweat somente por parcel térmico causal + hunger/exhaustion causal do Minecraft/NeoForge + Thirst Was Reclaimed somente por adapter causal próprio.
- **Fallback:** omitir componente de impacto/guarda sem hook seguro; nunca fabricar varredura. Sem parcel térmico ou hunger/exhaustion causal, Frenesi fica inativo, pois o benefício não pode existir sem tradeoff. Ausência de receipt hídrico omite só sede. Sem confirmação segura de golpe pesado, não oferecer gasto de 40.
- **Regra:** Cold Sweat continua owner da temperatura corporal; Minecraft/NeoForge, de hunger/exhaustion; TWR só participa por receipt hídrico causal. Não criar recurso térmico/metabólico paralelo. `TERMINAL_EXTERIOR: MARTIAL/MACHADOS`; especialista exige mapeamento explícito + fundamentos + ≥100 Passive Points. Respec deve proteger dependências de especialista.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS NO DESIGN / PRESENTE NA TOPOLOGIA.** A0010, A0011 e mastery ≥80 estão modelados.
2. **Integração global — PASS NO DESIGN, FAIL-CLOSED NO RUNTIME.** O contrato respeita ownership de Fúria, Cold Sweat, hunger/exhaustion, sede e stamina; a bridge causal ainda não existe.
3. **Qualidade e identidade — PASS.** Capstone transforma o ciclo de Fúria em estado ofensivo de alto risco com custo corporal real e fase de recuperação.
4. **Topologia — PASS.** Fecha os dois ramos de machados na camada 4.
5. **Especializações — PASS.** Terminal exterior com Gate C explícito, sem desbloqueio automático de especialista.
6. **PT-BR — PASS.** Estados e efeitos de jogador em português.
7. **Preenchimento do Notion — PASS.** Limiar, custos, owners, causalidade, fallback e respec estão definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura da modlist — PASS NO DESIGN / BLOQUEADA TECNICAMENTE.** Integrações exigidas foram especificadas por versão e por ownership; o runtime ainda não possui as bridges causais.

## Contrato técnico esperado

- Frenesi só pode existir com `fury >= 75` e todos os tradeoffs obrigatórios causalmente observáveis.
- Baseline ofensivo: +10% de impacto; nenhum alvo extra é criado.
- Atividade em Frenesi: ×1,25 no parcel térmico/metabólico causal e ×1,15 no hunger/exhaustion causal.
- Sede ×1,15 apenas se um receipt da mesma ação existir; exhaustion nunca vira proxy de sede.
- Em 100 Fúria, golpe pesado confirmado pode consumir 40 e receber +20% impacto e +40% pressão de guarda.
- Ao cruzar de ≥75 para <75, aplicar Queda de Ritmo por 6/5/4 s conforme mastery, reduzindo recuperação canônica de stamina em 15%.
- Uma ação não pode gerar múltiplos custos/benefícios por callbacks sobrepostos.
- Sem causalidade completa do tradeoff obrigatório, baseline fica inativo.

## Evidência encontrada na `main`

- `NotionCombatPerkCatalog` registra A0012 e os hooks declarados de impacto, atividade térmica, exhaustion e water cost.
- `NotionCombatPerkRules.frenzyBaselineAvailable(...)` exige simultaneamente impacto + thermal + exhaustion.
- `frenzyThirstSurchargeAvailable(...)` exige receipt hídrico separado.
- `frenzyDropDurationMillis(...)` contém 6/5/4 s conforme mastery.
- `A0001A0020CombatPolicy` contém comentário explícito de que os benefícios baseline de A0012 estão deliberadamente ausentes até uma bridge provar os tradeoffs da mesma ação.
- `A0001A0020CombatPolicyTest.frenzyBaselineFailsClosedWithoutCausalTradeoffs()` testa o fail-closed e proíbe inferir sede de exhaustion.
- Buscas na `main` por `cold_sweat` e `thirstwasreclaimed` não localizaram adapter runtime de A0012; apenas contrato/catalogação.

## Pendências técnicas

### P-A0012-01 — bridge causal de Frenesi ausente

- **Severidade:** bloqueante para o efeito principal.
- **Estado:** ABERTA.
- **Necessário:** correlacionar uma ação ofensiva de machado com parcel térmico corporal causal do Cold Sweat e custo real de hunger/exhaustion do Minecraft/NeoForge.
- **Fail-closed atual:** correto; sem esses receipts, Frenesi não deve conceder o benefício baseline.

### P-A0012-02 — surcharge de sede TWR ausente

- **Severidade:** média/opcional por contrato.
- **Estado:** ABERTA.
- **Necessário:** adapter versionado para Thirst Was Reclaimed 3.0.4 que exponha custo hídrico causal da mesma ação.
- **Fail-closed:** omitir somente sede; jamais derivar de exhaustion, polling ou delta de barra.

### P-A0012-03 — golpe pesado a 100 Fúria não implementado

- **Severidade:** alta.
- **Estado:** ABERTA.
- **Necessário:** receipt inequívoco de golpe pesado, gasto atômico de 40 de Fúria e modificadores +20% impacto/+40% pressão naquele golpe.

### P-A0012-04 — Queda de Ritmo não implementada

- **Severidade:** alta.
- **Estado:** ABERTA.
- **Necessário:** detectar transição de Frenesi ≥75 → <75 e aplicar modificador de −15% à **recuperação canônica de stamina**, não a attack speed ou movimento, por 6/5/4 s.

## Testes obrigatórios

- [x] fail-closed do baseline sem thermal/exhaustion;
- [x] proibição de inferência exhaustion→sede;
- [x] duração 6/5/4 s representada no ruleset;
- [ ] RED/GREEN da bridge Cold Sweat + hunger/exhaustion;
- [ ] teste de correlação por ação e anti-duplicação;
- [ ] RED/GREEN do golpe pesado em 100 Fúria;
- [ ] RED/GREEN da Queda de Ritmo sobre recuperação de stamina;
- [ ] teste do adapter TWR caso a versão exponha receipt utilizável;
- [ ] GameTest e dedicated-server smoke com as integrações opcionais presentes/ausentes.
