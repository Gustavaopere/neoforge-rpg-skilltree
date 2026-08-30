# A0002 — Treino com Espadas II

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db8113af7ef99d93dfe751
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0002
- **Nome:** Treino com Espadas II
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Ritmo e Velocidade
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Baixo
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0001 Treino com Espadas I ≥ 2 ranks.
- **Pré-requisitos:** A0001.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge.
- **Efeito:** +2% de velocidade/ritmo efetivo com espadas por rank, até +6%, respeitando os limites do moveset/provider.
- **Escalonamento:** 2% por rank; máximo de 3 ranks.
- **Gate:** Gateway `epic_sword` acessível + A0001 ≥ 2 ranks; o gateway pertence à Árvore Exterior.
- **Hook:** Epic Fight 21.17.3.1, categoria de espada + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente utilizar o valor de forma server-authoritative.
- **Fallback:** se o provider não expuser um modificador server-authoritative e estável de cadência/ritmo para espadas, esta parcela fica inativa. Não converter para stamina, movimento, dano ou aceleração de animação por mixin/heurística.
- **Regra:** usar apenas atributo/estado estável do provider correspondente ao efeito; fallback não pode mudar a identidade da perk.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0001 ≥ 2 ranks é requisito explícito e semanticamente coerente.
2. **Integração global — PASS.** Não usa stamina como substituto de ritmo e não cria recurso paralelo.
3. **Qualidade/identidade — PASS COMO PROGRESSÃO BASAL.** Amplia a disciplina de espada por cadência real do moveset, não por bônus genérico alternativo.
4. **Topologia — PASS.** Camada 2 sucede o treinamento basal e exige investimento prévio real.
5. **Especializações — PASS.** Continua na Árvore Exterior e não simula classe/especialista.
6. **PT-BR — PASS.** Texto jogador em português; nomes técnicos preservados somente no hook.
7. **Notion completo — PASS.** Campos necessários estão explícitos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight é o provider pertinente e a perk falha fechada quando não há sinal estável.

## Contrato técnico esperado

- Fórmula de família: `+0,02 × rank(A0002)` sobre a cadência/attack speed nativa suportada.
- Não tocar em stamina, velocidade de movimento, dano ou duração de animação como substituição.
- Aplicar apenas quando a arma for classificada como espada pelo provider.
- Server-authoritative no estado efetivo; cliente pode refletir o valor para apresentação sem criar autoridade paralela.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.rhythmBonus(...)` mapeia `WeaponFamily.SWORD -> A0002` e calcula +2% por rank.
- `A0001A0020EpicFightHooks.onAttackSpeed(...)` usa `ModifyAttackSpeedEvent` e multiplica o attack speed por `1 + bonus`.
- O adapter verifica a família fornecida pela capability do Epic Fight.
- O código não converte a perk para stamina/dano quando o hook não se aplica.
- O bloco possui testes de contrato e policy A0001–A0020.

## Pendências técnicas

Nenhuma pendência específica identificada nesta leitura. Isso não substitui CI/GameTest da implementação; significa apenas que não foi encontrada divergência concreta entre o contrato canônico e o caminho de código auditado.

## Testes obrigatórios

- [x] coeficiente/rank representado em `NotionCombatPerkRules`;
- [x] adapter de `ModifyAttackSpeedEvent` presente;
- [x] fail-closed por ausência de família/hook;
- [ ] manter teste de integração com versão exata do Epic Fight em CI/dedicated server sempre que o provider for atualizado.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependência, gate, hook, fallback e efeito persistem sem drift.
- **Mutação no Notion neste ciclo:** não necessária; o registro já contém o contrato corrigido e completo.
- **Provider/versão:** `Epic Fight 21.17.3.1` continua pinado no projeto e o adapter atual usa `ModifyAttackSpeedEvent` com classificação provider-native da arma.
- **Fail-closed:** se a cadência real deixar de ser exposta de forma estável/server-authoritative, A0002 fica inativa; nunca converter para stamina, movimento, dano ou aceleração artificial de animação.
- **Deduplicação/integrações:** armas de addons só entram se a capability do Epic Fight as classificar como espada; nenhuma bridge cria um segundo modificador concorrente.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Hook provider-native implementado e versionado para Epic Fight 21.17.3.1.
- [x] Gate/ranks/dependência preservados no contrato runtime/modelo.
- [x] FAIL-CLOSED preservado sem fallback que mude a identidade da perk.
- [x] Nenhuma segunda stamina, dano ou animação paralela foi criada.
- [x] Testes de ruleset/policy presentes.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma no provider/versionamento atual.
