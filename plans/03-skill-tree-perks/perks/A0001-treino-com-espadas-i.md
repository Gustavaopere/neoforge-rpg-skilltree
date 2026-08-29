# A0001 — Treino com Espadas I

## Status e proveniência

- **Design:** APROVADO/FECHADO pelo fluxo de auditoria do Notion.
- **Código relevante em `main`:** PRESENTE.
- **Implementação integral:** PENDENTE de completar o fallback configurável declarado no Notion.
- **Notion:** https://app.notion.com/p/3c569db9f0db8165adfcc38d24e537f1
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0001
- **Nome:** Treino com Espadas I
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Ritmo e Velocidade
- **Camada:** 1
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** nenhuma.
- **Pré-requisitos:** Gateway de disciplina de Espadas (`epic_sword`).
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** +3% de dano com espadas por rank, até +9%.
- **Escalonamento:** 3% por rank; máximo de 3 ranks.
- **Gate:** nível 8 + maestria de espadas (`epicfight:sword`) ≥ 60 + Gateway `epic_sword` desbloqueado. O gateway pertence à Árvore Exterior, não a uma Árvore de Especialista.
- **Hook:** categoria de arma espada + evento normalizado de dano corpo a corpo do RPG Skill Tree.
- **Fallback:** tag configurável `rpgskilltree:swords` quando a categoria do provider não estiver disponível; nunca duplicar o bônus quando Epic Fight já classificou a arma.
- **Regra:** bônus específico maior que dano universal. `FUNDAMENTO_EXTERIOR: ESPADAS`. Pode compor `SPECIALIST_FUNDAMENTALS`, mas não desbloqueia especialista sozinho e border hopping nunca substitui gates semânticos.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** O acesso exige nível, mastery e gateway sem dependência circular.
2. **Integração global — PASS.** É dano marcial específico e usa o pipeline canônico; não cria stamina, recurso corporal ou sistema paralelo.
3. **Qualidade e identidade — PASS COMO FUNDAMENTO.** Embora numérica, é uma perk basal de disciplina explicitamente marcada como `FUNDAMENTO_EXTERIOR`; não ocupa posição de Notable/Keystone/Capstone.
4. **Topologia — PASS.** Camada 1 e custo baixo são coerentes com o primeiro investimento após o gateway de espadas.
5. **Especializações — PASS.** Não é especialista; pode ser fundamento explicitamente referenciado por especialistas sem conceder acesso sozinho.
6. **PT-BR — PASS.** Nome e efeito destinados ao jogador estão em português; IDs técnicos permanecem em inglês.
7. **Preenchimento do Notion — PASS.** Campos necessários estão preenchidos e implementáveis.
8. **NeoVitae — PASS.** Não há dependência ou referência ao legado NeoVitae.
9. **Cobertura da modlist — PASS COM FALLBACK PENDENTE.** Epic Fight é o provider principal; armas externas devem entrar pela classificação do provider ou pela tag configurável, sem criar perks duplicadas por mod.

## Contrato técnico esperado

- Aplicar o multiplicador apenas a ataques diretos classificados como espada.
- Fórmula: `1 + 0,03 × rank(A0001)`.
- Resolver classificação uma única vez por ação; Epic Fight tem precedência sobre fallback por tag.
- Manter processamento server-authoritative.
- Não aplicar em dano periódico, proc secundário ou fonte não atribuível ao jogador.
- Não criar segundo pipeline de dano.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.baseDamageMultiplier(...)` mapeia `WeaponFamily.SWORD -> A0001` e aplica +3% por rank.
- `A0001A0020CombatPolicy.beforeHit(...)` calcula o multiplicador para hit direto/hostil.
- `A0001A0020EpicFightHooks.onDamagePre(...)` classifica a arma pela capability do Epic Fight e anexa o modificador ao `EpicFightDamageSource`.
- O bloco possui `A0001A0020NotionContractTest` e `A0001A0020CombatPolicyTest`.

## Pendências técnicas

### P-A0001-01 — fallback `rpgskilltree:swords` não localizado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** busca na `main` encontra a string `rpgskilltree:swords` apenas no checklist documental antigo, não em código/runtime/data.
- **Impacto:** o caminho Epic Fight funciona, mas a degradação canônica para armas inequivocamente classificáveis fora do provider não está demonstrada.
- **Correção esperada:** implementar uma tag de item/configuração real e um adapter fallback que só seja usado quando a classificação do provider não existir, com deduplicação para impedir aplicação dupla.
- **Fail-closed até corrigir:** arma sem classificação segura não recebe o bônus.

## Testes obrigatórios

- [x] contrato de coeficiente/rank no bloco A0001–A0020;
- [x] policy de dano direto;
- [x] integração Epic Fight via PRE de dano existente;
- [ ] teste do fallback por tag configurável;
- [ ] teste de deduplicação provider + tag após implementação do fallback;
- [ ] dedicated-server smoke da alteração quando o fallback for implementado.
