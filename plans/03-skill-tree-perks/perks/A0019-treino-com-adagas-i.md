# A0019 — Treino com Adagas I

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE para classificação Epic Fight e bônus de dano.
- **Implementação integral:** PENDENTE do fallback configurável por tag declarado no Notion.
- **Notion:** https://app.notion.com/p/3c569db9f0db81348e3eda212ea05d78
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0019
- **Nome:** Treino com Adagas I
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Adagas
- **Ramo:** Mobilidade e Fluxo
- **Camada:** 1
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** nenhuma.
- **Pré-requisitos:** Gateway de disciplina de Adagas (`epic_dagger`).
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** +3% de dano com adagas por rank, até +9%.
- **Escalonamento:** 3% por rank; máximo de 3 ranks.
- **Gate:** nível 8 + maestria de adagas (`epicfight:dagger`) ≥60 + Gateway `epic_dagger` desbloqueado. Gateway da Árvore Exterior.
- **Hook:** categoria de arma adaga + dano corpo a corpo direto normalizado.
- **Fallback:** tag configurável `rpgskilltree:daggers`; sem duplicar classificação do Epic Fight.
- **Regra:** bônus específico maior que dano universal. `FUNDAMENTO_EXTERIOR: ADAGAS`. Pode compor `SPECIALIST_FUNDAMENTALS` quando explicitamente mapeado, mas não desbloqueia especialista sozinho; proximidade visual/border hopping não substituem gates semânticos.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** Root exige nível 8, mastery 60 e gateway de adagas.
2. **Integração global — PASS.** Usa classificação e pipeline marcial já existentes; não cria mobilidade artificial nesta perk basal.
3. **Qualidade e identidade — PASS COMO FUNDAMENTO.** É o investimento inicial da disciplina de adagas.
4. **Topologia — PASS.** Camada 1 abre A0020/A0021 e os ramos posteriores.
5. **Especializações — PASS.** Fundamento exterior; não é especialista.
6. **PT-BR — PASS.** Texto de jogador em português.
7. **Preenchimento do Notion — PASS.** Campos canônicos necessários estão preenchidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura da modlist — PASS COM FALLBACK PENDENTE.** Epic Fight cobre a família principal; tag externa ainda não existe na `main`.

## Contrato técnico esperado

- Aplicar em hit direto/hostil atribuível ao jogador e classificado como adaga.
- Fórmula: `1 + 0,03 × rank(A0019)`.
- Epic Fight tem precedência de classificação; fallback por tag só entra quando o provider não classificar.
- Uma aplicação por ação elegível.
- Procs/dano periódico/autoria insegura não recebem o bônus.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.baseDamageMultiplier(...)` mapeia `WeaponFamily.DAGGER -> A0019`.
- `A0001A0020EpicFightHooks.family(...)` mapeia categoria `dagger` para `WeaponFamily.DAGGER`.
- `onDamagePre(...)` aplica o multiplicador provider-independente ao `EpicFightDamageSource`.
- `CombatPerkTreeModel` configura A0019 como root da família de adagas com nível 8, mastery `epicfight:dagger` 60 e gateway `epic_dagger`.

## Pendências técnicas

### P-A0019-01 — fallback `rpgskilltree:daggers` não localizado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** busca na `main` por `rpgskilltree:daggers` não retornou runtime, data tag ou configuração.
- **Impacto:** armas externas inequivocamente tratáveis como adaga, mas não classificadas pelo provider, não possuem o fallback canônico.
- **Correção esperada:** materializar tag/configuração e classificação fallback com precedência provider-native e deduplicação.
- **Fail-closed:** sem classificação segura, não conceder o bônus.

## Testes obrigatórios

- [x] coeficiente/rank no ruleset;
- [x] classificação `dagger` provider-native;
- [x] integração PRE de dano;
- [ ] teste de fallback por `rpgskilltree:daggers`;
- [ ] teste de deduplicação provider + fallback;
- [ ] dedicated-server smoke após implementação.
