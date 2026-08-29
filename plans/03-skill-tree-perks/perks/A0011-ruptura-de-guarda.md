# A0011 — Ruptura de Guarda

## Status e proveniência

- **Design:** APROVADO/FECHADO pelo fluxo de auditoria do Notion.
- **Código relevante em `main`:** PRESENTE para guarda/postura e fallback por Armor canônico.
- **Implementação integral:** PENDENTE de representar a elegibilidade canônica de alvo classificado como pesado.
- **Notion:** https://app.notion.com/p/3c569db9f0db812bb55bf30113d24b9a
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0011
- **Nome:** Ruptura de Guarda
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Quebra de Guarda
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 2
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0008 Treino com Machados II ≥ 2 ranks + A0009 Precisão com Machados ≥ 1 rank.
- **Pré-requisitos:** A0008 + A0009.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — serviço canônico de Fúria.
- **Efeito:** com pelo menos 40 de Fúria, golpe direto de machado contra alvo em guarda, com postura ativa ou classificado como pesado pode consumir 20 de Fúria. Rank 1: +20% de pressão de guarda/impacto e até +6% de penetração física elegível. Rank 2: +35% de pressão e até +10% de penetração. Não ativa contra alvo sem defesa relevante apenas para obter penetração gratuita.
- **Escalonamento:** 2 ranks; consumo fixo de 20 de Fúria.
- **Gate:** Gateway `epic_axe` acessível + A0008 ≥ 2 ranks + A0009 ≥ 1 rank; pertence à Árvore Exterior.
- **Hook:** Fúria atual + hit direto confirmado de machado + guarda/postura segura; `IMPACT`/`ARMOR_NEGATION` para componentes correspondentes. Sem guarda/postura, Armor canônico ou redução física explícita e segura pode qualificar o fallback.
- **Fallback:** sem postura/guarda, somente defesa física server-side comprovável qualifica. Armor canônico > 0 ou redução física explícita > 0 permitem apenas penetração. Armor 0/ausência de redução não qualificam. Não usar vida, aparência, dano recebido ou limiar heurístico.
- **Regra:** gasto deliberado de Fúria; não atravessa invulnerabilidade, não se aplica a procs e rota lateral não substitui dependências. Sem guarda/postura, não fabricar pressão/impacto.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** A0008/A0009 são reproduzidos pelo catálogo e pela topologia do `CombatPerkTreeModel`.
2. **Integração global — PASS.** Consome Fúria canônica de A0010 e usa componentes nativos de impacto/armor negation.
3. **Qualidade e identidade — PASS.** É um gasto condicional de recurso para romper defesa comprovada, não um bônus passivo genérico.
4. **Topologia — PASS.** Camada 3 conecta ritmo/crítico de machados e converge com A0010 antes do capstone A0012.
5. **Especializações — PASS.** Continua Árvore Exterior; não cria classe automática.
6. **PT-BR — PASS.** Nome, recurso e descrição de jogador em português.
7. **Preenchimento do Notion — PASS.** Condição, valores, fallback, anti-heurística e dependências estão explícitos.
8. **NeoVitae — PASS.** Nenhuma referência/dependência localizada.
9. **Cobertura da modlist — PASS COM LACUNA.** Epic Fight fornece guarda/postura/impacto/penetração; o caso sem provider usa Armor seguro. Falta representar a terceira condição canônica: alvo classificado como pesado.

## Contrato técnico esperado

- Requer rank 1 ou 2 de A0011 e Fúria pré-gasto ≥ 40.
- Consome 20 de Fúria apenas após elegibilidade comprovada e `claimOnce` da ação.
- Rank 1: impacto/pressão ×1,20 e penetração 0,06.
- Rank 2: impacto/pressão ×1,35 e penetração 0,10.
- Guarda/postura real permite impacto + penetração quando os hooks existirem.
- Fallback Armor/redução física permite apenas penetração.
- Alvo classificado como pesado deve poder qualificar a ativação quando houver classificação segura.
- Sem qualquer defesa/classificação relevante, não consumir Fúria e não conceder penetração.

## Evidência encontrada na `main`

- `NotionCombatPerkRules` define `A0011_MIN_FURY=40`, `A0011_FURY_COST=20`, multiplicadores 1,20/1,35 e penetração 0,06/0,10.
- `A0001A0020CombatPolicy.beforeHit(...)` exige proteção elegível, deduplica por `A0011:spend`, consome Fúria e separa corretamente pressão de guarda de penetração.
- O fallback por `armorProtected` mantém apenas penetração quando `relevantGuardOrPosture=false`.
- `A0001A0020EpicFightHooks.onDamagePre(...)` fornece `defended` por blocking/stun shield, Armor canônico por `getArmorValue()`, e hooks de impacto/penetração.
- `A0001A0020CombatPolicyTest.ruptureSpendsFuryOnlyForDefendedOrArmoredTargets()` verifica guarda, alvo desprotegido e fallback por Armor.

## Pendências técnicas

### P-A0011-01 — elegibilidade de alvo pesado não representada

- **Severidade:** média.
- **Estado:** ABERTA.
- **Causa:** `HitFacts` possui `heavyAttack`, mas esse campo descreve o ataque do jogador (`source.shouldChargeWeapon()`); A0011 não recebe fato separado que diga que **o alvo** foi classificado como pesado. O policy usa apenas `relevantGuardOrPosture || armorProtected`.
- **Impacto:** alvos pesados sem guarda/postura e sem Armor podem não ativar A0011 apesar da especificação canônica.
- **Correção esperada:** introduzir fato canônico de classificação defensiva/peso do alvo proveniente de API/provider confiável e incorporá-lo à elegibilidade sem converter isso em penetração gratuita para alvos comuns.
- **Fail-closed:** não inferir alvo pesado por vida máxima, tamanho visual, knockback ou dano recebido.

## Testes obrigatórios

- [x] valores por rank e consumo de Fúria no ruleset;
- [x] guarda/postura provider-native;
- [x] fallback por Armor canônico;
- [x] ausência de gasto em alvo desprotegido;
- [ ] teste RED/GREEN para classificação segura de alvo pesado;
- [ ] dedicated-server smoke quando essa classificação for integrada.
