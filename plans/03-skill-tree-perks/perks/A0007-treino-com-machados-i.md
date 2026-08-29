# A0007 — Treino com Machados I

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Implementação integral:** PENDENTE de completar o fallback configurável declarado no Notion.
- **Notion:** https://app.notion.com/p/3c569db9f0db81db9d9fe826285f88b3
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0007
- **Nome:** Treino com Machados I
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Varredura e Pressão
- **Camada:** 1
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** nenhuma.
- **Pré-requisitos:** Gateway de disciplina de Machados (`epic_axe`).
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** +3% de dano com machados por rank, até +9%.
- **Escalonamento:** 3% por rank; máximo de 3 ranks.
- **Gate:** nível 8 + maestria de machados (`epicfight:axe`) ≥ 60 + Gateway `epic_axe` desbloqueado. Gateway da Árvore Exterior.
- **Hook:** categoria de arma machado + dano corpo a corpo direto normalizado.
- **Fallback:** tag configurável `rpgskilltree:axes`; aplicar uma única vez por golpe elegível e nunca duplicar a classificação do Epic Fight.
- **Regra:** bônus específico maior que dano universal. `FUNDAMENTO_EXTERIOR: MACHADOS`. Pode compor `SPECIALIST_FUNDAMENTALS`, mas não desbloqueia especialista sozinho; proximidade visual/border hopping não substituem gates semânticos.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** Nível, mastery e gateway formam bloqueio explícito e não circular.
2. **Integração global — PASS.** Usa o pipeline de dano marcial canônico e não cria recurso paralelo.
3. **Qualidade/identidade — PASS COMO FUNDAMENTO.** Bônus numérico é compatível com node basal explicitamente marcado como fundamento exterior.
4. **Topologia — PASS.** Camada 1/custo baixo após gateway são coerentes.
5. **Especializações — PASS.** Não é Especialista; pode servir de fundamento quando mapeado semanticamente.
6. **PT-BR — PASS.** Texto jogador em português.
7. **Notion completo — PASS.** Campos necessários estão definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS COM FALLBACK PENDENTE.** Epic Fight cobre o provider principal; compat externa deve entrar por classificação segura ou tag, sem duplicar bônus.

## Contrato técnico esperado

- Aplicar apenas a ataque direto classificado como machado.
- Fórmula: `1 + 0,03 × rank(A0007)`.
- Epic Fight tem precedência de classificação; fallback por tag só entra quando o provider não classificar.
- Uma aplicação por ação elegível, server-authoritative.
- Não aplicar a dano periódico, proc secundário ou autoria insegura.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.baseDamageMultiplier(...)` mapeia `WeaponFamily.AXE -> A0007` e soma +3% por rank.
- `A0001A0020CombatPolicy.beforeHit(...)` aplica o multiplicador a hits diretos/hostis.
- `A0001A0020EpicFightHooks.onDamagePre(...)` classifica a arma via capability do Epic Fight e anexa o multiplicador ao `EpicFightDamageSource`.
- O bloco possui testes de contrato/policy A0001–A0020.

## Pendências técnicas

### P-A0007-01 — fallback `rpgskilltree:axes` não localizado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** busca na `main` localiza `rpgskilltree:axes` apenas no checklist documental antigo, não em código/runtime/data.
- **Impacto:** o caminho Epic Fight está presente, mas o fallback canônico para armas externas inequivocamente classificáveis não está demonstrado.
- **Correção esperada:** criar tag/configuração real e adapter fallback usado somente na ausência da classificação provider-native, com deduplicação explícita.
- **Fail-closed:** sem classificação segura, não conceder o bônus.

## Testes obrigatórios

- [x] coeficiente/rank presente no ruleset;
- [x] policy de dano direto presente;
- [x] integração Epic Fight PRE de dano presente;
- [ ] teste do fallback por tag `rpgskilltree:axes`;
- [ ] teste de deduplicação provider + tag após implementação;
- [ ] dedicated-server smoke da futura alteração.
