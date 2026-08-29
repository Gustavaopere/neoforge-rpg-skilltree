# A0004 — Ritmo do Duelista

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE, com uma lacuna de adapter identificada.
- **Notion:** https://app.notion.com/p/3c569db9f0db81aeb549d2500a67c0f4
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0004
- **Nome:** Ritmo do Duelista
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Duelista — Ímpeto
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 1
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0003 Precisão com Espadas ≥ 2 ranks; rota lateral não substitui a dependência.
- **Pré-requisitos:** A0003.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree.
- **Efeito:** acertos diretos limpos com espada geram 1 de Ímpeto, até 5. Aparo, riposta ou esquiva que realmente evitou ameaça podem gerar 1 carga quando houver confirmação segura. Ataque de espada iniciado e encerrado sem alvo elegível remove 1; stagger/desequilíbrio pesado hostil remove 2; após 5 s sem ganho elegível, perde 1 por segundo até 0.
- **Escalonamento:** 1 rank; Ímpeto é estado transitório MARTIAL com cap 5. Ganho elegível reinicia o timer de 5 s; perdas não o reiniciam.
- **Gate:** Gateway `epic_sword` + A0003 ≥ 2 ranks.
- **Hook:** acerto direto server-authoritative + término confirmado de ataque sem hit elegível + defesa técnica confirmada quando disponível + stagger/impacto pesado hostil + relógio server-side do último ganho.
- **Fallback:** sem hook seguro de aparo/esquiva, apenas acertos diretos limpos geram Ímpeto. Se não houver forma segura de confirmar miss, omitir somente a perda por erro; nunca inferir esquiva por distância ou premiar bloqueio passivo.
- **Regra:** uma ocorrência não pode gerar duas cargas por adapters sobrepostos. Auto-dano, alvo de treino, AFK e callbacks duplicados não contam. Ímpeto é limpo em morte, logout e troca de dimensão; troca de arma não gera nem renova o estado.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0003 ≥ 2 ranks é requisito explícito.
2. **Integração global — PASS.** Ímpeto é estado canônico MARTIAL e não duplica stamina do Epic Fight.
3. **Qualidade/identidade — PASS.** Notable muda comportamento: recompensa sequência limpa, defesa técnica e manutenção de ritmo.
4. **Topologia — PASS.** Camada 3 e entrada após precisão são coerentes com ramo Duelista.
5. **Especializações — PASS.** É identidade de subdisciplina exterior, sem promover Epic Fight a classe automática.
6. **PT-BR — PASS.** Nome, recurso e regras do jogador em português.
7. **Notion completo — PASS.** Geração, perdas, decay, limpeza e deduplicação estão especificados.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS COM LACUNA TÉCNICA.** Epic Fight fornece hit, dodge e miss confirmados; stagger pesado ainda precisa de receipt/adapter comprovado.

## Contrato técnico esperado

- Estado por jogador: `momentum ∈ [0,5]`.
- Ganho normal: +1 por hit direto/hostil/confirmado de espada.
- Defesa técnica segura: +1, uma vez por evento.
- Miss confirmado: -1.
- Stagger pesado hostil confirmado: -2.
- Inatividade: depois de 5 s sem ganho, -1/s até 0.
- Limpeza em morte, logout e mudança de dimensão.
- Toda mutação deve ser server-authoritative e deduplicada por ação/evento.

## Evidência encontrada na `main`

- `A0001A0020CombatPolicy.afterConfirmedHit(...)` concede +1 de Ímpeto com `claimOnce`.
- `onConfirmedTechnicalDefense(...)` concede carga de defesa técnica.
- `onConfirmedMiss(...)` remove 1 carga.
- `onConfirmedHostileHeavyStagger(...)` existe no policy e remove 2 cargas.
- `A0001A0020EpicFightHooks` conecta `ON_DODGE` à defesa técnica e `ATTACK_PHASE_END` à confirmação de miss.
- Tick do Epic Fight chama a atualização do estado transitório/decay.
- Morte, logout, troca de dimensão e respawn limpam estado no adapter.

## Pendências técnicas

### P-A0004-01 — receipt de stagger pesado não conectado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Evidência:** `onConfirmedHostileHeavyStagger(...)` aparece no policy, mas a busca na `main` não encontrou chamada por adapter/runtime.
- **Impacto:** a perda canônica de 2 cargas por stagger pesado hostil não está demonstrada em runtime.
- **Correção esperada:** localizar evento/API pública do Epic Fight 21.17.3.1 que confirme stagger/impacto pesado hostil e conectar apenas quando a causalidade for segura.
- **Fail-closed:** não inferir stagger por knockback, distância, animação ou dano bruto.

### Cobertura não bloqueante de defesa

O adapter auditado confirma `ON_DODGE`. Aparo/guarda perfeita/riposta não foram encontrados como receipts independentes neste bloco. Isso não bloqueia a identidade porque o próprio contrato aceita usar apenas sinais de defesa técnica comprováveis, mas novos hooks só podem ser adicionados com evidência provider-native.

## Testes obrigatórios

- [x] ganho por hit e deduplicação no policy;
- [x] perda por miss no policy/adapter;
- [x] decay e cap do estado transitório;
- [x] defesa via `ON_DODGE` conectada;
- [ ] teste de integração do futuro receipt de stagger pesado;
- [ ] dedicated-server smoke após essa integração.
