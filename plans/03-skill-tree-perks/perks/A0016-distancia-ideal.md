# A0016 — Distância Ideal

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE para faixa ideal, ganho, expiração e perda por miss; PARCIAL para perdas por stagger pesado.
- **Notion:** https://app.notion.com/p/3c569db9f0db81c790c3d12c7669eca0
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0016
- **Nome:** Distância Ideal
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Controle de Distância
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 2
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0015 Precisão com Lanças ≥ 2 ranks.
- **Pré-requisitos:** A0015.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** hit direto de lança entre 70% e 100% do alcance efetivo gera 1 Controle de Distância, até 3. Hit abaixo de 70% não gera. Ataque iniciado e confirmado como miss remove 1 quando houver receipt seguro. Stagger/impacto pesado hostil remove 1.
- **Escalonamento:** 2 ranks. Rank 1: estado inteiro expira 5 s após o último ganho; rank 2: 7 s. Ganho renova duração; perdas não. Cap fixo 3.
- **Gate:** Gateway `epic_spear` + A0015 ≥ 2 ranks; pertence à Árvore Exterior.
- **Hook:** hit confirmado + distância real atacante→alvo no impacto + alcance efetivo/canônico + miss confirmado + stagger pesado hostil + estado transitório.
- **Fallback:** sem alcance dinâmico, usar somente alcance canônico explicitamente resolvido para categoria/item. Sem alcance seguro, geração fica inativa. Sem receipt de miss, omitir somente a perda por erro. Nunca inferir alcance pela animação.
- **Regra:** estado pertence ao jogador, pode ser gasto por perks posteriores, só ganhos renovam duração e é limpo em morte/logout/troca de dimensão; trocar arma não cria nem renova cargas.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0015 ≥ 2 é reproduzido pela definição/topologia.
2. **Integração global — PASS.** Estado transitório único `Controle de Distância`; usa alcance provider-native e ciclo de vida server-side.
3. **Qualidade/identidade — PASS.** Notable de posicionamento com faixa geométrica real, decay e penalidades de execução.
4. **Topologia — PASS.** Camada 3 prepara diretamente A0018 e complementa A0017.
5. **Especializações — PASS.** Permanece exterior.
6. **PT-BR — PASS.** Nome/recurso em português.
7. **Notion completo — PASS.** Faixa, cap, duração, perdas, fallback e limpeza definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS COM LACUNA.** Epic Fight fornece alcance e miss confirmável; perda por stagger pesado ainda não tem adapter localizado.

## Contrato técnico esperado

- `ideal = distance/effectiveReach ∈ [0,70;1,00]`.
- Cada hit direto, hostil e com dano real na faixa: +1, cap 3.
- Rank 1: expiração compartilhada em 5 s; rank 2: 7 s.
- Novo ganho reinicia a expiração; consumo/perda não reinicia.
- Miss confirmado: −1.
- Stagger pesado hostil confirmado: −1.
- Estado limpo nos eventos de ciclo de vida relevantes.

## Evidência encontrada na `main`

- `NotionCombatPerkRules` define cap 3, faixa 0,70–1,00 e duração 5/7 s.
- `A0001A0020CombatPolicy.isIdealSpearRange(...)` valida a fração com limites inclusivos.
- `A0001A0020EpicFightHooks.onDamagePre(...)` calcula `effectiveReach = entityInteractionRange + capability.getReach()` e registra `idealSpearRange`.
- `afterConfirmedHit(...)` adiciona carga somente em hit SPEAR ideal e deduplica com `A0016:gain`.
- `ATTACK_PHASE_END` com lista real de hits vazia chama `onConfirmedMiss(...)` e remove 1 carga.
- `NotionCombatPerkState` implementa cap, expiração compartilhada, consumo e limpeza.
- `A0001A0020CombatPolicyTest` verifica 70%/100% como inclusivos e abaixo de 70% como inelegível.

## Pendências técnicas

### P-A0016-01 — perda por stagger pesado sem caller runtime

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** `A0001A0020CombatPolicy.onConfirmedHostileHeavyStagger(...)` contém a perda de 1 Controle de Distância, mas não foi localizado adapter/caller na `main`.
- **Impacto:** essa penalidade canônica não ocorre no runtime comprovado.
- **Correção esperada:** conectar receipt público/provider-native que prove stagger/impacto pesado hostil e chamar o policy uma única vez por ocorrência.
- **Fail-closed:** não inferir stagger por knockback, velocidade, dano bruto ou animação.

### Nota de fallback de alcance

O caminho Epic Fight tem alcance dinâmico comprovado. Não foi localizado adapter separado de alcance canônico por item/categoria fora do provider; isso só se torna necessário onde a capability nativa não existir.

## Testes obrigatórios

- [x] cap e duração 5/7 s;
- [x] limites geométricos 70–100%;
- [x] ganho por hit confirmado e deduplicação;
- [x] perda por miss confirmado;
- [x] limpeza de estado transitório;
- [ ] teste RED/GREEN do receipt de stagger pesado;
- [ ] dedicated-server smoke após integração desse receipt.
