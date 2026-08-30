# A0020 — Treino com Adagas II

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db81f68362fba14a2483d6
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0020
- **Nome:** Treino com Adagas II
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Adagas
- **Ramo:** Mobilidade e Fluxo
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0019 Treino com Adagas I ≥ 2 ranks.
- **Pré-requisitos:** A0019.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge.
- **Efeito:** +2% de velocidade/ritmo efetivo com adagas por rank, até +6%, respeitando o moveset/provider.
- **Escalonamento:** 2% por rank; máximo de 3 ranks.
- **Gate:** Gateway `epic_dagger` acessível + A0019 ≥ 2 ranks; gateway da Árvore Exterior.
- **Hook:** Epic Fight 21.17.3.1, categoria de adaga + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente usar esse valor server-authoritatively.
- **Fallback:** sem modificador server-authoritative e estável de cadência/ritmo, a parcela fica inativa. Não converter para stamina, movimento, dano ou alteração de animação por mixin/heurística.
- **Regra:** usar apenas estado/atributo estável correspondente ao efeito; fallback não altera a identidade da perk.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0019 ≥ 2 ranks está representado na definição/topologia.
2. **Integração global — PASS.** Ritmo usa o evento próprio do Epic Fight sem interferir em stamina ou movimento.
3. **Qualidade/identidade — PASS COMO PROGRESSÃO BASAL.** Melhora cadência real da disciplina de adagas.
4. **Topologia — PASS.** Camada 2 sucede o fundamento A0019 e conduz aos Notables posteriores.
5. **Especializações — PASS.** Permanece na Árvore Exterior.
6. **PT-BR — PASS.** Texto de jogador em português.
7. **Notion completo — PASS.** Dependência, hook e fail-closed definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight é provider pertinente; efeito fica inativo sem sinal de cadência seguro.

## Contrato técnico esperado

- Fórmula: `+0,02 × rank(A0020)` na cadência/attack speed nativa da adaga.
- Aplicar somente quando família for DAGGER.
- Não substituir por stamina, movimento, dano ou manipulação de animação.
- Resultado efetivo server-authoritative.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.rhythmBonus(...)` mapeia `WeaponFamily.DAGGER -> A0020` e calcula +2% por rank.
- `A0001A0020EpicFightHooks.onAttackSpeed(...)` usa `ModifyAttackSpeedEvent` e aplica `attackSpeed × (1 + bonus)`.
- `family(...)` reconhece `dagger` provider-native.
- `CombatPerkTreeModel` exige A0019 ≥ 2 ranks para A0020.

## Pendências técnicas

Nenhuma divergência específica identificada nesta leitura. A semântica de `ModifyAttackSpeedEvent` deve ser revalidada quando o Epic Fight mudar de versão.

## Testes obrigatórios

- [x] coeficiente/rank no ruleset;
- [x] adapter `ModifyAttackSpeedEvent` presente;
- [x] classificação DAGGER provider-native;
- [x] fail-closed quando a família/hook não existir;
- [ ] revalidar integração e dedicated-server smoke após atualização do provider.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA sem mutação no Notion; nenhum dos providers retroauditados altera a cadência provider-native da adaga.
- **RPG Skill Tree:** rank/perk continua consumer da cadência Epic Fight, sem segundo modificador semântico.
- **Volcanoes / Enshrouded / Black Arcana:** NÃO DEVE SER INTEGRADO; hazards, Shroud/Flame e Arcane Danger não são attack speed de adaga.
- **Mobstein 5.4.4:** companions ressuscitados não herdam A0020 do dono e não fornecem hook de cadência ao jogador.
- **Notion:** re-fetch em 2026-08-30 sem drift; nenhuma mutação artificial.
- **Fail-closed:** se o provider deixar de expor cadência server-authoritative, A0020 fica inativa em vez de mudar de identidade.
- **Chat 2:** nenhuma bridge nova; preservar família DAGGER e evento provider-native.
