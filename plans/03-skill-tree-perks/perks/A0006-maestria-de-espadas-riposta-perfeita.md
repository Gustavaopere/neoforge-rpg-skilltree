# A0006 — Maestria de Espadas — Riposta Perfeita

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência bloqueante identificada:** nenhuma nesta auditoria documental.
- **Cobertura técnica parcial conhecida:** o adapter auditado comprova esquiva (`ON_DODGE`) como defesa técnica; aparo/guarda perfeita não foram evidenciados como receipts independentes neste bloco.
- **Notion:** https://app.notion.com/p/3c569db9f0db81aeaae1db665043dc71
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0006
- **Nome:** Maestria de Espadas — Riposta Perfeita
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Duelista — Ímpeto
- **Camada:** 4
- **Função na Árvore:** Capstone
- **Tier:** Grande
- **Faixa de Poder:** Alto
- **Ranks Máx.:** 1
- **Custo por Rank:** 2
- **Dependências Obrigatórias:** A0004 Ritmo do Duelista + A0005 Abertura de Guarda + maestria de espadas (`epicfight:sword`) ≥ 80. Ponte/rota alternativa não substitui esses requisitos.
- **Pré-requisitos:** A0004 + A0005.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — Ímpeto e pipeline crítico canônico.
- **Efeito:** ao atingir 5 de Ímpeto, uma defesa técnica confirmada — aparo, guarda perfeita ou esquiva que realmente evitou ataque elegível — prepara Riposta Perfeita por 3 s. O próximo acerto direto de espada consome todo o Ímpeto, recebe +20% de dano crítico elegível e +20% de impacto/pressão de guarda, e não gera Ímpeto no mesmo resultado. Recarga de 10 s.
- **Escalonamento:** 1 rank. Maestria ≥ 90 reduz cooldown para 9 s; ≥ 100 para 8 s. Coeficientes não aumentam.
- **Gate:** Gateway `epic_sword` acessível + A0004 + A0005 + mastery de espada ≥ 80.
- **Hook:** registro de Ímpeto + evento confirmado de defesa técnica + próximo acerto direto de espada; profundidade de proc e deduplicação obrigatórias.
- **Fallback:** se o provider não expuser nenhum evento confiável de aparo, guarda perfeita ou esquiva, o capstone fica indisponível naquele provider; não fabricar defesa por heurística.
- **Regra:** não ativa por bloqueio passivo, invulnerabilidade, auto-dano ou spam de esquiva sem ameaça real. `TERMINAL_EXTERIOR: MARTIAL/ESPADAS`. Só satisfaz Gate C de especialista quando mapeado explicitamente; fundamentos e ≥100 Passive Points continuam obrigatórios. Respec deve impedir refund que invalide especialista dependente até as perks de Especialista serem devolvidas.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** Exige as duas Notables antecedentes e mastery específica, além do gateway.
2. **Integração global — PASS.** Reutiliza Ímpeto e crítico canônicos; não cria recurso paralelo nem segunda resolução crítica.
3. **Qualidade/identidade — PASS.** Capstone de execução técnica com preparação, janela curta, consumo total e cooldown; impacto compatível com posição terminal.
4. **Topologia — PASS.** Camada 4 fecha o ramo Duelista e exige progressão real anterior.
5. **Especializações — PASS.** É terminal exterior, não uma especialização automática; só alimenta especialista por mapeamento semântico explícito.
6. **PT-BR — PASS.** Nome, estado e efeito de jogador estão em português.
7. **Notion completo — PASS.** Dependências, mastery, janela, consumo, cooldown, escalonamento, fallback e respec estão especificados.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight fornece um receipt técnico comprovado (`ON_DODGE`); outros tipos de defesa só podem ser adicionados com API/evento real da versão exata.

## Contrato técnico esperado

- Só arma com `momentum == 5` e defesa técnica confirmada.
- Janela armada: 3.000 ms.
- Cooldown: 10.000/9.000/8.000 ms conforme mastery <90 / ≥90 / ≥100, respeitando gate mínimo 80.
- Próximo hit direto de espada consome 5 de Ímpeto.
- +20% sobre componente de dano crítico elegível; não transformar hit não-crítico em crítico por esta perk.
- +20% impacto/pressão apenas quando o hook nativo existir.
- O mesmo hit consumidor não pode gerar Ímpeto.
- Deduplicação por ação e limpeza de estado transitório obrigatórias.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.riposteCooldownMillis(...)` implementa 10/9/8 s.
- `A0001A0020CombatPolicy.onConfirmedTechnicalDefense(...)` exige espada, A0006, 5 de Ímpeto e cooldown pronto; então arma Riposta por 3 s.
- `A0001A0020EpicFightHooks.onDodge(...)` fornece receipt provider-native e passa a mastery real de `epicfight:sword`.
- `A0001A0020CombatPolicy.beforeHit(...)` consome a janela e 5 de Ímpeto, aplica +20% de dano quando o resultado é crítico, +20% de impacto/pressão quando disponível e ativa `suppressMomentumGain`.
- A deduplicação usa `claimOnce(..., "A0006:consume", ...)`.
- Testes de contrato/policy do bloco A0001–A0020 cobrem a infraestrutura usada pela perk.

## Pendências técnicas

Nenhuma pendência bloqueante foi identificada para o caminho comprovado de esquiva do Epic Fight 21.17.3.1.

### Nota de cobertura — aparo e guarda perfeita

- **Estado:** NÃO BLOQUEANTE / EXPANSÃO CONDICIONAL.
- O design aceita qualquer defesa técnica confiável dentre aparo, guarda perfeita ou esquiva; `ON_DODGE` fornece pelo menos um caminho provider-native verificável.
- Não adicionar aparo/guarda perfeita por heurística. Somente integrar se a versão exata do provider expuser receipt público suficientemente causal.

## Testes obrigatórios

- [x] cálculo de cooldown por mastery no ruleset;
- [x] armamento por defesa técnica e consumo no policy;
- [x] supressão de ganho de Ímpeto no hit consumidor;
- [x] integração `ON_DODGE` presente;
- [ ] adicionar testes específicos caso novos receipts de aparo/guarda perfeita sejam integrados;
- [ ] revalidar dedicated-server smoke quando o adapter Epic Fight mudar.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; gate, dependências, janela, consumo, cooldown, escalonamento e fallback permanecem alinhados.
- **Mutação no Notion neste ciclo:** não necessária.
- **Receipt comprovado:** `ON_DODGE` fornece o caminho provider-native mínimo de defesa técnica para Epic Fight 21.17.3.1.
- **Fail-closed parcial aprovado:** aparo ou guarda perfeita só podem ser acrescentados se houver receipt público, causal e versionado. Não inferir defesa técnica por blocking passivo, invulnerabilidade, animação ou proximidade temporal.
- **Pipeline único:** o bônus de dano só amplifica componente crítico elegível; A0006 não cria crítico próprio e o hit consumidor não pode regenerar Ímpeto.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010; a expansão futura de receipts de defesa não é bloqueio para a rota comprovada atual.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Receipt técnico `ON_DODGE` comprovado e provider-native.
- [x] Janela de 3 s e cooldown 10/9/8 s implementados.
- [x] Consumo atômico de 5 Ímpeto implementado.
- [x] +20% de dano crítico elegível e +20% de impacto somente quando disponível.
- [x] `suppressMomentumGain` impede regenerar Ímpeto no mesmo hit.
- [x] Deduplicação por root action implementada.
- [x] Regressão explícita `A0001A0010ImplementationContractJUnitTest` adicionada na PR #221.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Fallback/fail-closed:** aparo/guarda perfeita adicionais permanecem inativos até existir receipt público causal e versionado; isso é fallback legítimo aprovado, não pendência bloqueante.
