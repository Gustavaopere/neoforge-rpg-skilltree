# A0013 — Treino com Lanças I

## Status e proveniência

- **Design:** APROVADO/FECHADO pelo fluxo de auditoria do Notion.
- **Código relevante em `main`:** PRESENTE para classificação Epic Fight e bônus de dano.
- **Implementação integral:** PENDENTE do fallback configurável por tag declarado no Notion.
- **Notion:** https://app.notion.com/p/3c569db9f0db816cb2f6dd938fbd7562
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0013
- **Nome:** Treino com Lanças I
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Alcance e Controle de Distância
- **Camada:** 1
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** nenhuma.
- **Pré-requisitos:** Gateway de disciplina de Lanças (`epic_spear`).
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** +3% de dano com lanças por rank, até +9%.
- **Escalonamento:** 3% por rank; máximo de 3 ranks.
- **Gate:** nível 8 + maestria de lanças (`epicfight:spear`) ≥ 60 + Gateway `epic_spear` desbloqueado. O gateway pertence à Árvore Exterior.
- **Hook:** categoria de arma lança + dano corpo a corpo direto normalizado.
- **Fallback:** tag configurável `rpgskilltree:spears`; não alterar alcance físico sem API estável.
- **Regra:** bônus específico maior que dano universal. `FUNDAMENTO_EXTERIOR: LANÇAS`. Pode compor `SPECIALIST_FUNDAMENTALS` quando mapeado explicitamente, mas não desbloqueia especialista sozinho; proximidade visual/border hopping não substituem gates semânticos.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** Root da disciplina exige nível, mastery e gateway sem circularidade.
2. **Integração global — PASS.** Usa o pipeline de dano marcial canônico e a classificação provider-native; não cria alcance paralelo.
3. **Qualidade e identidade — PASS COMO FUNDAMENTO.** É node basal explícito da disciplina de lanças.
4. **Topologia — PASS.** Camada 1 abre A0014/A0015 e conduz aos ramos de Controle de Distância/Interceptação.
5. **Especializações — PASS.** Fundamento exterior, sem desbloqueio automático de especialista.
6. **PT-BR — PASS.** Texto de jogador em português; IDs técnicos preservados.
7. **Preenchimento do Notion — PASS.** Campos canônicos necessários estão preenchidos.
8. **NeoVitae — PASS.** Nenhuma referência/dependência localizada.
9. **Cobertura da modlist — PASS COM FALLBACK PENDENTE.** Epic Fight cobre a classificação principal; compatibilidade externa prevista por tag ainda não está materializada.

## Contrato técnico esperado

- Aplicar apenas em hit direto, hostil e atribuível ao jogador com arma classificada como lança.
- Fórmula: `1 + 0,03 × rank(A0013)`.
- Classificação Epic Fight tem precedência; fallback por tag só pode ser usado quando o provider não classificar.
- Uma única aplicação por ação elegível.
- Não modificar alcance físico por esta perk.
- Dano periódico, proc secundário ou autoria insegura não recebem o bônus.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.baseDamageMultiplier(...)` mapeia `WeaponFamily.SPEAR -> A0013` e aplica +3% por rank.
- `A0001A0020EpicFightHooks.family(...)` mapeia a categoria `spear` para `WeaponFamily.SPEAR`.
- `onDamagePre(...)` envia a família ao policy e anexa o multiplicador ao `EpicFightDamageSource`.
- `CombatPerkTreeModel` configura A0013 como root da família de lanças com nível 8, mastery `epicfight:spear` 60 e gateway `epic_spear`.

## Pendências técnicas

### P-A0013-01 — fallback `rpgskilltree:spears` não localizado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** busca na `main` por `rpgskilltree:spears` não retornou runtime, data tag ou configuração.
- **Impacto:** armas externas não reconhecidas pela capability do Epic Fight não possuem o fallback canônico documentado.
- **Correção esperada:** criar tag/configuração real e adapter de classificação fallback, usado somente quando o provider principal não classificar, com deduplicação explícita.
- **Fail-closed:** sem classificação segura, não conceder o bônus.

## Testes obrigatórios

- [x] coeficiente/rank no ruleset;
- [x] classificação `spear` provider-native;
- [x] integração PRE de dano;
- [ ] teste do fallback `rpgskilltree:spears`;
- [ ] teste de deduplicação provider + tag;
- [ ] dedicated-server smoke após implementação do fallback.
