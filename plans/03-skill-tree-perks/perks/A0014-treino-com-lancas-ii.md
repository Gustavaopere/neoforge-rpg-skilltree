# A0014 — Treino com Lanças II

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db81e285e3dc8d51f6bba7
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0014
- **Nome:** Treino com Lanças II
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Alcance e Controle de Distância
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0013 Treino com Lanças I ≥ 2 ranks.
- **Pré-requisitos:** A0013.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge.
- **Efeito:** +2% de velocidade/ritmo efetivo com lanças por rank, até +6%, respeitando o moveset/provider.
- **Escalonamento:** 2% por rank; máximo de 3 ranks.
- **Gate:** Gateway `epic_spear` acessível + A0013 ≥ 2 ranks; gateway da Árvore Exterior.
- **Hook:** Epic Fight 21.17.3.1, categoria de lança + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente usar esse valor de forma server-authoritative.
- **Fallback:** sem modificador server-authoritative e estável de cadência/ritmo, esta parcela fica inativa. Não converter para stamina, movimento, dano ou alteração de animação por heurística/mixin frágil.
- **Regra:** usar somente atributo/estado estável correspondente ao efeito; fallback não muda a identidade da perk.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0013 ≥ 2 ranks é requisito explícito na definição e topologia.
2. **Integração global — PASS.** Ritmo usa o atributo/evento do Epic Fight e não interfere em stamina ou movimento.
3. **Qualidade/identidade — PASS COMO PROGRESSÃO BASAL.** Amplia a disciplina por cadência real do moveset.
4. **Topologia — PASS.** Camada 2 sucede A0013 e alimenta A0017.
5. **Especializações — PASS.** Continua Árvore Exterior.
6. **PT-BR — PASS.** Texto jogador em português.
7. **Notion completo — PASS.** Dependência, hook e fail-closed estão explícitos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight é o provider pertinente; ausência de sinal estável desativa o componente em vez de substituí-lo.

## Contrato técnico esperado

- Fórmula: `+0,02 × rank(A0014)` sobre cadência provider-native da lança.
- Só aplicar quando a família for SPEAR.
- Não converter para stamina, movimento, dano ou tempo de animação.
- Resultado efetivo deve permanecer server-authoritative.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.rhythmBonus(...)` mapeia `WeaponFamily.SPEAR -> A0014` e calcula +2% por rank.
- `A0001A0020EpicFightHooks.onAttackSpeed(...)` usa `ModifyAttackSpeedEvent` e multiplica o valor por `1 + bonus`.
- A família é resolvida pela capability nativa do Epic Fight.
- `CombatPerkTreeModel` exige A0013 ≥ 2 ranks pela definição canônica.

## Pendências técnicas

Nenhuma divergência específica identificada nesta leitura. A validade do evento deve ser reavaliada se a versão exata do Epic Fight mudar.

## Testes obrigatórios

- [x] coeficiente/rank no ruleset;
- [x] adapter `ModifyAttackSpeedEvent` presente;
- [x] classificação SPEAR provider-native;
- [x] fail-closed por ausência de família/hook;
- [x] revalidação de integração e dedicated-server smoke no CI #2147 para Epic Fight `21.17.3.1`.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA sem mutação no Notion; nenhuma capacidade nova dos cinco providers altera o contrato de cadência.
- **RPG Skill Tree:** apenas aplica o rank adquirido; a cadência efetiva continua resolvida pelo Epic Fight provider-native.
- **Volcanoes / Enshrouded / Black Arcana:** NÃO DEVE SER INTEGRADO; hazards, Shroud/Flame e Arcane Danger não substituem attack speed/cadência da lança.
- **Mobstein 5.4.4:** não fornece cadência marcial do jogador; companions não herdam A0014 do dono.
- **Notion:** re-fetch em 2026-08-30 sem drift; nenhuma mutação artificial.
- **Fail-closed:** se o moveset/provider não expuser cadência estável, a perk fica inativa em vez de virar stamina, movimento, dano ou animação.
- **Chat 2:** nenhuma nova implementação além de preservar a boundary existente.

## Chat 2 — revalidação de implementação — PR #237

- [x] Gate A0013 ≥2 e coeficiente +2%/rank preservados.
- [x] `ModifyAttackSpeedEvent` continua o único hook semântico de cadência.
- [x] Epic Fight é aceito somente em `21.17.3.1` exato.
- [x] Sem família/hook seguro, o efeito permanece fail-closed; não migra para stamina, movimento, dano ou animação.
- [x] Companions e fontes indiretas não herdam a perk do dono.
- [x] Regressões JUnit e NeoForge GameTests verdes no CI #2147 no mesmo HEAD revalidado.
- [x] Build, JAR e dedicated-server smoke verdes no CI #2147 no mesmo HEAD revalidado.

**Estado Chat 2:** `IMPLEMENTAÇÃO VALIDADA EM CI`; confirmação definitiva ocorre com o merge da PR #237 na `main`.
