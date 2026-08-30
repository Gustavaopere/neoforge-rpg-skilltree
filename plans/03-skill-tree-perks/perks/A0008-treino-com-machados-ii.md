# A0008 — Treino com Machados II

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db8176ade1e7bfe92eb2d5
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0008
- **Nome:** Treino com Machados II
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Varredura e Pressão
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0007 Treino com Machados I ≥ 2 ranks.
- **Pré-requisitos:** A0007.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge.
- **Efeito:** +2% de velocidade/ritmo efetivo com machados por rank, até +6%, respeitando o moveset/provider.
- **Escalonamento:** 2% por rank; máximo de 3 ranks.
- **Gate:** Gateway `epic_axe` acessível + A0007 ≥ 2 ranks; gateway da Árvore Exterior.
- **Hook:** Epic Fight 21.17.3.1, categoria de machado + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente usar esse valor server-authoritatively.
- **Fallback:** sem modificador server-authoritative e estável de cadência/ritmo, esta parcela fica inativa. Não converter para stamina, movimento, dano ou aceleração de animação por mixin/heurística.
- **Regra:** usar somente estado/atributo estável correspondente ao próprio efeito; fallback não altera a identidade da perk.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** Exige investimento real em A0007.
2. **Integração global — PASS.** Não substitui ritmo por stamina ou outro recurso compartilhado.
3. **Qualidade/identidade — PASS COMO PROGRESSÃO BASAL.** Atua sobre cadência real do moveset em vez de bônus genérico alternativo.
4. **Topologia — PASS.** Camada 2 sucede o fundamento de machados.
5. **Especializações — PASS.** Continua na Árvore Exterior sem criar classe automática.
6. **PT-BR — PASS.** Texto de jogador em português; nomes técnicos limitados ao hook.
7. **Notion completo — PASS.** Campos necessários preenchidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight é o provider pertinente e o efeito falha fechado quando o sinal de ritmo não é seguro.

## Contrato técnico esperado

- Fórmula: `+0,02 × rank(A0008)` sobre attack speed/cadência nativa suportada.
- Aplicar somente quando arma for classificada como machado pelo provider.
- Não tocar em stamina, movimento, dano ou duração de animação como substituto.
- Server-authoritative para resultado efetivo.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.rhythmBonus(...)` mapeia `WeaponFamily.AXE -> A0008` e calcula +2% por rank.
- `A0001A0020EpicFightHooks.onAttackSpeed(...)` usa `ModifyAttackSpeedEvent` e multiplica o valor por `1 + bonus`.
- A família é obtida da capability provider-native do Epic Fight.
- Ausência de família segura simplesmente não aplica a perk.
- O bloco possui testes de contrato e policy A0001–A0020.

## Pendências técnicas

Nenhuma divergência específica foi identificada nesta leitura. Mudanças futuras no provider devem revalidar que `ModifyAttackSpeedEvent` continua representando a cadência real do moveset na versão instalada.

## Testes obrigatórios

- [x] coeficiente/rank presente no ruleset;
- [x] adapter de `ModifyAttackSpeedEvent` presente;
- [x] fail-closed por ausência de classificação/hook;
- [ ] revalidar integração e dedicated-server smoke após atualização do Epic Fight.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependência, gate, efeito, hook e fallback permanecem sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Provider/versão:** Epic Fight 21.17.3.1 e `ModifyAttackSpeedEvent` continuam sendo o contrato implementável auditado para cadência de machado.
- **Fail-closed:** se o evento/atributo deixar de refletir cadência real de forma server-authoritative, A0008 fica inativa; não pode virar stamina, dano, movimento ou modificação frágil de animação.
- **Deduplicação:** addons de armas participam somente pela classificação provider-native compartilhada do Epic Fight.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] `ModifyAttackSpeedEvent` provider-native implementado.
- [x] Gate/ranks/dependência preservados.
- [x] Família exclusivamente provider-native.
- [x] FAIL-CLOSED sem conversão para stamina/movimento/dano/animação.
- [x] Testes de ruleset/policy presentes.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma no provider/versionamento atual.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; rank/gate seguem authority do RPG e cadência permanece authority do evento Epic Fight.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO`; hazards, pressão, Atmosphere e geologia não alteram cadência de machado.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO`; Shroud/Exposure/Flame/Story não fornecem attack-speed marcial.
- **Black Arcana:** somente o ataque direto Epic Fight do jogador recebe A0008; casts/Backlash não recebem nem disparam o modificador.
- **Mobstein 5.4.4:** ataque direto do jogador contra mobs/bosses é coberto universalmente; ataques dos ressuscitados seguem o próprio Mobstein e não herdam cadência do dono.
- **Fallback/fail-closed:** nenhum bridge novo. Sem família `AXE` + evento de cadência seguro para a ação do jogador, a perk fica inativa.
- **Notion:** nenhuma mutação necessária nesta perk.
- **Estado histórico:** implementação da #221 já mergeada; sem alteração runtime.
