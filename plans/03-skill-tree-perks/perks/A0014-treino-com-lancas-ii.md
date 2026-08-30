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
- [ ] revalidar integração e dedicated-server smoke após atualização do Epic Fight.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependência, efeito, gate, hook e fallback persistem sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Hook confirmado:** Epic Fight 21.17.3.1 `ModifyAttackSpeedEvent` + família SPEAR provider-native.
- **Fail-closed:** se o provider deixar de expor cadência efetiva estável/server-authoritative, A0014 fica inativa; não virar stamina, movimento, dano ou manipulação de animação.
- **Deduplicação:** addons de armas participam apenas pela mesma capability Epic Fight; não registrar segundo modificador concorrente de ritmo.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.