# A0017 — Interceptação

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE no fallback canônico de janela + impacto/pressão.
- **Componente provider-native de redução de deslocamento:** INATIVO por ausência de receipt ofensivo seguro comprovado.
- **Notion:** https://app.notion.com/p/3c569db9f0db8120ae36dd2d84001d5e
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0017
- **Nome:** Interceptação
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Interceptação
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 2
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0014 Treino com Lanças II ≥ 2 ranks + A0015 Precisão com Lanças ≥ 1 rank.
- **Pré-requisitos:** A0014 + A0015.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree.
- **Efeito:** quando inimigo entra na faixa ideal da lança avançando em direção ao jogador, o próximo golpe direto de lança em até 2 s pode consumir 1 Controle de Distância e receber +20%/+35% de impacto e pressão de guarda. Somente quando o provider confirmar corrida/investida ou movimento ofensivo com deslocamento próprio, o mesmo golpe reduz em 20%/30% o deslocamento ofensivo reconhecido, sem enraizamento.
- **Escalonamento:** rank 1: +20% impacto e −20% deslocamento ofensivo; rank 2: +35% e −30%.
- **Gate:** Gateway `epic_spear` + A0014 ≥ 2 + A0015 ≥ 1; gateway da Árvore Exterior.
- **Hook:** entrada na faixa ideal + aproximação hostil mensurável para abrir janela + hit confirmado de lança + consumo de Controle de Distância; redução de deslocamento exige movimento ofensivo nativo reconhecido.
- **Fallback:** sem estado nativo confiável de corrida/investida/deslocamento ofensivo, manter somente janela + impacto/pressão. Não usar velocidade vanilla/genérica para reescrever movimento.
- **Regra:** aproximação geométrica pode abrir a janela; somente movimento ofensivo provider-native pode ter deslocamento reduzido. Não cria stun, root ou cancelamento gratuito.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** A0014/A0015 são obrigatórios e representados na topologia.
2. **Integração global — PASS.** Consome Controle de Distância canônico e usa impacto do provider; não cria controle de movimento paralelo.
3. **Qualidade e identidade — PASS.** Notable de defesa ativa por posicionamento, janela curta e gasto de recurso.
4. **Topologia — PASS.** Converge ritmo + precisão e alimenta A0018 junto de A0016.
5. **Especializações — PASS.** Árvore Exterior, sem classe automática.
6. **PT-BR — PASS.** Nome, recurso e comportamento de jogador em português.
7. **Preenchimento do Notion — PASS.** Janela, gasto, ranks, regra geométrica e restrição provider-native estão explícitos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura da modlist — PASS EM FALLBACK CANÔNICO.** A parte segura funciona com Epic Fight; a redução de deslocamento está corretamente omitida até existir receipt nativo.

## Contrato técnico esperado

- Detectar transição fora→dentro da faixa ideal de alvo hostil avançando geometricamente.
- Abrir janela de 2.000 ms por alvo.
- Próximo hit direto de lança na janela consome 1 Controle de Distância.
- Rank 1: impacto/pressão ×1,20; rank 2: ×1,35.
- Redução de deslocamento 20%/30% só pode ocorrer se o provider confirmar que a ação do alvo é movimento ofensivo próprio e expuser ponto seguro de modificação.
- Nunca usar `deltaMovement` genérico como autorização para reescrever movimento; ele serve apenas à aproximação geométrica que abre a janela.

## Evidência encontrada na `main`

- `NotionCombatPerkRules` define janela de 2 s, impacto 1,20/1,35 e multiplicador de deslocamento 0,80/0,70.
- `A0001A0020EpicFightHooks.onEpicFightTick(...)` mede faixa ideal e usa `target.getDeltaMovement()` apenas para determinar aproximação geométrica via `isAdvancingToward(...)`.
- `NotionCombatPerkState.recordSpearRange(...)` abre a janela A0017 na transição fora→dentro somente quando `targetAdvancing=true`.
- O pipeline PRE prepara o consumo por `rootActionId`; janela e 1 Controle de Distância só são efetivamente consumidos no POST confirmado.
- O próprio policy contém comentário explícito de que a redução de deslocamento ofensivo está omitida até o provider fornecer receipt nativo reconhecido.
- `A0001A0020CombatPolicyTest` e `A0011A0020CausalCommitJUnitTest` verificam janela, consumo causal, impacto e direção geométrica de aproximação.

## Pendências técnicas

### P-A0017-01 — redução de deslocamento ofensivo aguardando receipt provider-native

- **Severidade:** não bloqueante para o fallback; bloqueante apenas para o componente completo.
- **Estado:** ABERTA / FAIL-CLOSED CORRETO.
- **Causa:** não há, no adapter auditado do Epic Fight 21.17.3.1, receipt comprovado que identifique a mesma ação como corrida/investida/movimento ofensivo e permita modular apenas seu deslocamento.
- **Estado atual:** a perk opera exatamente no fallback canônico: janela + impacto/pressão, sem reescrever movimento.
- **Correção esperada:** integrar somente API/evento provider-native causal; se não existir, manter o componente omitido.

### P-A0017-02 — consumo prematuro de janela/Controle de Distância

- **Estado:** RESOLVIDA na PR #250; confirmação definitiva após merge.
- **Defeito:** o runtime mergeado removia a janela de Interceptação e consumia 1 Controle de Distância no PRE, antes de confirmar dano efetivo. Cancelamento/dano zero podia destruir a oportunidade e o recurso.
- **Correção:** PRE agora só reserva a janela e a carga por `rootActionId`; POST válido commita janela + 1 carga. POST sem dano descarta a reserva e preserva a janela enquanto ainda estiver dentro do tempo original.
- **Concorrência/dedup:** reservas são bounded e impedem que outra root action use a mesma janela/carga reservada para o mesmo alvo.
- **Ordem com A0016:** A0017 commita o custo antes do ganho de Controle de Distância de A0016 no mesmo POST; a própria carga criada pelo golpe não pode financiar o consumo.
- **TDD RED:** CI #2256, commit `64e4abd9eacc45caf7f4af67b4015be9d7ef4bf9`, falhou em `a0017DefersWindowAndDistanceControlConsumptionUntilConfirmedDamage`.
- **TDD GREEN:** CI #2269, HEAD `1698bdc518f84ae99da6a9f6da1a78ad5b9f3923`, verde em JUnit, GameTests, build/JAR e server smoke; nove auxiliares verdes.

## Testes obrigatórios

- [x] detecção geométrica de aproximação;
- [x] janela de 2 s por alvo;
- [x] consumo de 1 Controle de Distância somente após hit confirmado;
- [x] cancelamento/dano zero preserva janela/carga;
- [x] impacto/pressão 20%/35%;
- [x] fallback sem alteração de movimento;
- [ ] teste do componente de deslocamento somente se surgir receipt provider-native válido;
- [x] dedicated-server smoke do fallback canônico no CI #2269.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA; P-A0017-01 permanece o fail-closed correto e nenhum projeto próprio/Mobstein fornece substituto legítimo para o receipt ofensivo faltante.
- **RPG Skill Tree:** janela, Controle de Distância, deduplicação e consumo continuam no pipeline MARTIAL canônico.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não abre, consome nem aciona Interceptação.
- **Mobstein 5.4.4:** movimento/ataque de ally/bodyguard ressuscitado permanece Mobstein-owned e não é ação do jogador; combate direto do jogador contra entidade Mobstein continua normal.
- **Volcanoes / Enshrouded:** hazards, pressão, Shroud, Exposure, Flame ou Story não são receipt de corrida/investida Epic Fight. Associação temática com avanço/perigo não autoriza reescrita de movimento.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch confirmou persistência.
- **Fail-closed:** componente de redução de deslocamento continua omitido até API/evento provider-native causal; janela + impacto/pressão permanecem o fallback canônico aprovado.
- **Chat 2:** não usar `deltaMovement`, velocidade vanilla ou estado de outro provider como autorização para a redução de deslocamento.

## Chat 2 — revalidação de implementação — PR #237

- [x] Gate A0014 ≥2 + A0015 ≥1 e janela de 2 s preservados.
- [x] Aproximação geométrica usa movimento somente para abrir janela, nunca para reescrever deslocamento.
- [x] Janela + consumo + impacto/pressão do fallback canônico estão implementados e deduplicados.
- [x] `ARCANE_BACKLASH`, companions e outros providers não são receipts ofensivos substitutos.
- [x] P-A0017-01 permanece explicitamente `FAIL-CLOSED CORRETO`; nenhuma heurística foi adicionada.
- [x] Regressões JUnit e NeoForge GameTests verdes no CI #2147 no mesmo HEAD revalidado.
- [x] Build, JAR e dedicated-server smoke verdes no CI #2147 no mesmo HEAD revalidado.
- [ ] **PENDÊNCIA NÃO BLOQUEANTE P-A0017-01:** redução de deslocamento só poderá ser implementada se surgir receipt ofensivo provider-native causal na versão auditada.

**Estado Chat 2:** `IMPLEMENTAÇÃO VALIDADA EM CI NO FALLBACK CANÔNICO`; a pendência acima não bloqueia o merge porque sua omissão é parte do contrato aprovado. Confirmação definitiva ocorre com o merge da PR #237.
