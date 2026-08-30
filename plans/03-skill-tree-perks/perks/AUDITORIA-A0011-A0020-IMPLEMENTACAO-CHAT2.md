# Auditoria de Implementação — Chat 2 — A0011–A0020

Data do ciclo: **2026-08-30 (America/Sao_Paulo)**.

## Escopo

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **Responsabilidade:** implementar, testar e fechar tecnicamente o design já aprovado pelo Chat 1, sem redesenhar perks.
- **Providers principais:** Epic Fight `21.17.3.1`; para A0012, Cold Sweat **exatamente `2.4.2`**; Minecraft/NeoForge 1.21.1.

Fontes operacionais: protocolo consolidado do Chat 2; critérios obrigatórios consolidados; `STATUS.md`; dez dossiês A0011–A0020; auditoria V3 e PR de design #219; runtime/testes reais do repositório. Os três guias consolidados permanecem referência de integração conforme o protocolo, mas não foram re-auditados integralmente pelo Chat 2 porque essa responsabilidade pertence ao Chat 1.

## Resultado por perk

| Código | Contrato implementado | Provider/fallback | Estado pré-merge |
|---|---|---|---|
| A0011 | Ruptura de Guarda: 40 Fúria, gasto 20, impacto/pressão e penetração | guarda/postura Epic Fight; fallback físico estrito penetração-only | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0012 | Frenesi: CORE→exhaustion→benefício, pico, Queda de Ritmo | Cold Sweat exato 2.4.2; falha de versão/API/escrita = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0013 | +3% dano/rank com lança | família Epic Fight; desconhecida = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0014 | +2% cadência/rank de lança | `ModifyAttackSpeedEvent`; sem hook seguro = inativa | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0015 | +3% crítico/rank de lança | um único pipeline crítico/root action | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0016 | Controle de Distância em 70–100% do alcance efetivo | alcance/miss/stagger provider-native; ausência de alcance = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0017 | janela + consumo + impacto/pressão | fallback canônico; redução de deslocamento omitida sem receipt ofensivo | IMPLEMENTAÇÃO VALIDADA EM CI / FALLBACK |
| A0018 | crossing, 3 cargas, janela, +15% dano, +40% impacto/pressão, lockout | amostragem server-side + Epic Fight | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0019 | +3% dano/rank com adaga | família Epic Fight; desconhecida = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0020 | +2% cadência/rank de adaga | `ModifyAttackSpeedEvent`; sem hook seguro = inativa | IMPLEMENTAÇÃO VALIDADA EM CI |

## Correções de runtime exigidas pelo Chat 1

### P-A0012-01 — versão exata do Cold Sweat

O bridge usava `startsWith("2.4.2")`, o que aceitava indevidamente versões não auditadas como `2.4.20`. O contrato agora usa igualdade exata com `SUPPORTED_VERSION = "2.4.2"`.

TDD:

- **RED:** CI #2028, com falha JUnit exatamente no novo contrato de versão.
- **GREEN:** `ColdSweatFrenzyBridgeTest` confirma `2.4.2` e rejeita `2.4.20`, `2.4.2.1`, prerelease, versão anterior e `null`.

### P-A0012-02 — diagnóstico bounded

Falhas reflexivas/invocação eram silenciosas. O bridge agora possui diagnóstico one-shot por chave/classe de falha:

- incompatibilidade de versão;
- falha de resolução da API `Temperature`/`Trait.CORE`;
- falha de invocação da escrita CORE.

`RpgSkillTreeMod` executa probe de compatibilidade no bootstrap quando Cold Sweat está presente. O diagnóstico usa `RuntimeDiagnostics`/`Category.COMPAT`; o bridge continua fail-closed em todos os casos.

TDD:

- **RED:** CI #2033, falha JUnit exatamente porque o gate bounded ainda não existia.
- **GREEN:** `ColdSweatFrenzyBridgeTest.diagnosticGateEmitsOnlyOncePerFailureClass` confirma uma emissão por chave e supressão das repetições.

## Regra transacional de A0012 confirmada

`A0001A0020EpicFightHooks.payFrenzyBodyCost(...)` mantém a ordem causal exigida:

1. valida AXE + A0012 + Fury ≥75 + bridge operacional;
2. evita cobrança se A0011 reduziria a Fury abaixo de 75 antes de existir benefício;
3. deduplica `A0012:body-cost` por root action;
4. tenta primeiro `ColdSweatFrenzyBridge.addCoreHeat(..., 1.5)`;
5. somente após sucesso aplica exhaustion `0.015`;
6. somente o receipt `frenzyBodyCostPaid=true` autoriza baseline/pico no policy;
7. pico gasta 40 Fúria somente após o receipt corporal confirmado.

Não há recurso térmico paralelo e exhaustion não é transformado em sede.

## Regressões de caracterização do lote

`A0011A0020ImplementationContractJUnitTest` adiciona regressões explícitas para:

- A0013/A0014/A0015: dano, cadência e crítico vinculados exclusivamente a SPEAR;
- A0019/A0020: dano/cadência vinculados exclusivamente a DAGGER;
- ausência de crítico de adaga A0021 dentro deste lote;
- A0017: janela/consumo/impacto do fallback sem inventar dano ou penetração;
- A0018: consumo de 3 cargas, +15% dano, +40% impacto/pressão e lockout de 8 s por alvo.

A cobertura existente em `A0001A0020CombatPolicyTest` continua validando A0011, transação A0012, Queda de Ritmo, A0016, A0017 e A0018 em maior profundidade.

## Evidência de CI antes do closeout documental

No HEAD de runtime/testes `bda08ca9748ad16d3352d0872f753976731424f8`, os **9 workflows** associados à PR #224 concluíram com `success`, inclusive **RPG Skill Tree CI #2036**. O pipeline validou:

- Core tests;
- JUnit 5;
- NeoForge GameTests;
- wiki drift/coverage e validadores;
- build NeoForge;
- built-JAR verification;
- dedicated-server smoke.

As alterações posteriores dos dossiês, desta auditoria e de `STATUS.md` são closeout documental. O HEAD final documental precisa passar novamente por CI completo antes do merge.

## Fallback/fail-closed remanescente

### P-A0017-01 — componente de deslocamento ofensivo

- **Estado:** ABERTA / NÃO BLOQUEANTE / FAIL-CLOSED CORRETO.
- **Contrato aprovado:** A0017 funciona com janela + impacto/pressão.
- **Componente omitido:** redução de deslocamento ofensivo 20%/30%.
- **Motivo:** Epic Fight 21.17.3.1 não possui receipt comprovado no adapter auditado que identifique causalmente a mesma ação como corrida/investida/movimento ofensivo e exponha ponto seguro para modular somente seu deslocamento.
- **Proibição:** `deltaMovement`, velocidade vanilla, animação ou proximidade temporal não podem autorizar reescrita de movimento.
- **Retorno ao Chat 1:** somente se surgir API/evento provider-native real que permita revisar esse componente; enquanto isso, manter o fallback é o comportamento aprovado.

## Checklist técnico consolidado

- [x] Provider-native first preservado.
- [x] Versões auditadas respeitadas; Cold Sweat A0012 agora é comparação exata.
- [x] Fail-closed não foi convertido em bônus genérico.
- [x] Pipeline crítico único preservado em A0015.
- [x] Deduplicação por root action/receipt preservada.
- [x] Autoria/causalidade server-side preservadas.
- [x] Nenhum recurso paralelo/gratuito criado.
- [x] A0017 não ganhou heurística de movimento.
- [x] Testes unitários/JUnit/GameTests e dedicated-server smoke verdes no candidato de runtime.
- [x] Conteúdo player-facing permanece PT-BR.
- [x] NeoVitae não foi introduzido.

## Gate final

A0011–A0020 só mudam de `IMPLEMENTAÇÃO VALIDADA EM CI` para **`IMPLEMENTAÇÃO CONFIRMADA`** quando:

1. o HEAD final documental da PR #224 ficar completamente verde;
2. não houver review real pendente;
3. a PR #224 for mergeada;
4. a `main` pós-merge for buscada e o SHA final confirmado.

Após o merge, o Chat 2 deve **PARAR** e não iniciar A0021–A0030 automaticamente.
