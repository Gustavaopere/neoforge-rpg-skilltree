# A0010 — Pressão do Carrasco

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE para o caminho Epic Fight.
- **Implementação integral:** PENDENTE de demonstrar/implementar o fallback genérico declarado no Notion.
- **Notion:** https://app.notion.com/p/3c569db9f0db81fd9b1bcb501b7745ba
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0010
- **Nome:** Pressão do Carrasco
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Fúria e Pressão
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 2
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0009 Precisão com Machados ≥ 2 ranks.
- **Pré-requisitos:** A0009.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — serviço canônico de Fúria.
- **Efeito:** cada acerto corpo a corpo direto e válido com machado contra inimigo hostil gera 8 de Fúria como ganho-base. O multiplicador do rank é aplicado primeiro; se o alvo for diferente do último alvo hostil legitimamente atingido, aplicar depois ×1,5. Auto-dano, alvo passivo/de treino, entidade invulnerável, tentativa sem dano confirmado, proc secundário e ação sem autoria real não geram Fúria. Fúria é limitada a 100.
- **Escalonamento:** 2 ranks. Rank 1: 8,8 normal e 13,2 na troca legítima de alvo. Rank 2: 9,6 normal e 14,4 na troca. O multiplicador de troca permanece ×1,5.
- **Gate:** Gateway `epic_axe` acessível + A0009 ≥ 2 ranks; gateway da Árvore Exterior.
- **Hook:** resultado server-authoritative de dano corpo a corpo direto com machado + autoria real + registro do último alvo hostil legitimamente atingido + serviço canônico de Fúria.
- **Fallback:** sem evento específico do Epic Fight, usar apenas dano corpo a corpo direto confirmado com item inequivocamente classificado como machado. Se autoria/classificação não forem seguras, não gerar Fúria; nunca conceder por tentativa de ataque.
- **Regra:** uma única concessão de Fúria por resultado ofensivo elegível. Ordem: ganho-base 8 → multiplicador de rank → multiplicador de troca de alvo → clamp em 100. Procs, callbacks duplicados, fake players e alvos de treino não contam.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0009 ≥ 2 ranks e gateway do ramo são requisitos explícitos.
2. **Integração global — PASS.** Usa o serviço canônico de Fúria; não cria stamina, mana ou recurso concorrente.
3. **Qualidade/identidade — PASS.** Notable incentiva agressão legítima e alternância de alvo, com anti-abuso explícito e recurso próprio do ramo.
4. **Topologia — PASS.** Camada 3 sucede o ramo crítico de machados e prepara gasto futuro de Fúria.
5. **Especializações — PASS.** Permanece na Árvore Exterior e não transforma provider em classe.
6. **PT-BR — PASS.** Nome, recurso e regras de jogador em português.
7. **Notion completo — PASS.** Base, ordem matemática, ranks, target switch, cap, autoria, anti-proc e fallback estão especificados.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS COM FALLBACK PENDENTE.** Epic Fight possui caminho provider-native; o contrato prevê degradação segura para hit direto inequivocamente classificado quando o provider específico não existir.

## Contrato técnico esperado

- Só gerar Fúria em hit direto, hostil, com dano efetivamente confirmado e autoria real do jogador.
- `base = 8`.
- Rank 1: `base × 1,10`; rank 2: `base × 1,20`.
- Se o alvo atual for diferente do último alvo hostil legitimamente atingido, multiplicar o resultado por `1,50`.
- Clamp final em 100.
- Registrar mudança de alvo apenas a partir de resultado ofensivo legítimo.
- Uma concessão por `rootActionId`.
- Fake player, proc, alvo de treino/passivo, invulnerável ou hit sem dano confirmado: zero ganho.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.axeFuryGain(...)` implementa 8 × 1,10/1,20 e depois ×1,50 quando `switchedTarget=true`.
- `NotionCombatPerkRules.FURY_CAP` é 100.
- `A0001A0020CombatPolicy.afterConfirmedHit(...)` só prossegue após `direct`, `hostile` e `actualDamage`; para machado + A0010 usa `claimOnce`, consulta `switchedAxeTarget(...)` e chama `addFury(...)`.
- `A0001A0020EpicFightHooks.onDamagePost(...)` só encaminha o hit quando `modifiedDamage > 0`, mantém autoria pelo `ServerPlayer` e exige alvo hostil.
- A infraestrutura de eligibility do adapter exclui caminhos sem jogador server-authoritative e o projeto possui testes de contrato/policy do bloco A0001–A0020.

## Pendências técnicas

### P-A0010-01 — fallback genérico sem evento Epic Fight não localizado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** a busca por A0010 na `main` localiza catálogo/tree/policy/testes e o adapter Epic Fight compartilhado, mas não um adapter genérico separado capaz de receber dano melee direto confirmado quando o evento específico do Epic Fight não estiver disponível.
- **Impacto:** o caminho principal para Epic Fight está presente, porém a degradação segura descrita pelo Notion não está demonstrada para outros casos inequivocamente classificados como machado.
- **Correção esperada:** integrar o pipeline normalizado de dano melee do RPG Skill Tree ou outro receipt público comprovável, reutilizando a mesma deduplicação/root action e a mesma classificação canônica; não criar segunda concessão quando Epic Fight já tratou a ação.
- **Fail-closed:** se autoria, dano confirmado ou classificação não forem comprováveis, não conceder Fúria.

## Testes obrigatórios

- [x] matemática de ganho por rank/target switch no ruleset;
- [x] deduplicação e mutação de Fúria no policy;
- [x] confirmação de dano no POST do Epic Fight;
- [x] cap canônico de Fúria representado;
- [ ] teste RED/GREEN do fallback genérico quando implementado;
- [ ] teste de deduplicação entre fallback e Epic Fight;
- [ ] dedicated-server smoke da futura integração.
