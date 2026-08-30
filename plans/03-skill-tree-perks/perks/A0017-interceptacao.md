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
- `beforeHit(...)` consome janela + 1 Controle de Distância, deduplica por `A0017:consume` e aplica impacto/pressão quando disponível.
- O próprio policy contém comentário explícito de que a redução de deslocamento ofensivo está omitida até o provider fornecer receipt nativo reconhecido.
- `A0001A0020CombatPolicyTest` verifica janela, consumo e impacto, além da direção geométrica de aproximação.

## Pendências técnicas

### P-A0017-01 — redução de deslocamento ofensivo aguardando receipt provider-native

- **Severidade:** não bloqueante para o fallback; bloqueante apenas para o componente completo.
- **Estado:** ABERTA / FAIL-CLOSED CORRETO.
- **Causa:** não há, no adapter auditado do Epic Fight 21.17.3.1, receipt comprovado que identifique a mesma ação como corrida/investida/movimento ofensivo e permita modular apenas seu deslocamento.
- **Estado atual:** a perk opera exatamente no fallback canônico: janela + impacto/pressão, sem reescrever movimento.
- **Correção esperada:** integrar somente API/evento provider-native causal; se não existir, manter o componente omitido.

## Testes obrigatórios

- [x] detecção geométrica de aproximação;
- [x] janela de 2 s por alvo;
- [x] consumo de 1 Controle de Distância;
- [x] impacto/pressão 20%/35%;
- [x] fallback sem alteração de movimento;
- [ ] teste do componente de deslocamento somente se surgir receipt provider-native válido;
- [ ] dedicated-server smoke após eventual integração.

## Chat 2 — implementação, testes e merge — PR #224

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI / FALLBACK CANÔNICO`; torna-se `IMPLEMENTAÇÃO CONFIRMADA / FALLBACK CANÔNICO` após merge da PR #224.

- [x] Transição fora→dentro + aproximação geométrica abre janela de 2 s por alvo.
- [x] Próximo hit direto SPEAR consome 1 Controle de Distância com deduplicação.
- [x] Impacto/pressão ×1,20/×1,35 implementados.
- [x] `deltaMovement` é usado somente para detectar aproximação geométrica; nunca para autorizar ou reescrever movimento ofensivo.
- [x] Fallback aprovado pelo Chat 1 preservado: janela + impacto/pressão, sem componente de deslocamento.
- [x] Regressão JUnit confirma consumo e que A0017 não inventa dano nem penetração.
- [x] `RPG Skill Tree CI` #2036 GREEN no SHA `bda08ca9748ad16d3352d0872f753976731424f8`.
- [x] JUnit, NeoForge GameTests, build, built-JAR verification e dedicated-server smoke verdes.
- [ ] **P-A0017-01:** redução de deslocamento ofensivo continua omitida até surgir receipt provider-native causal e ponto seguro de modificação.

**Risco/fail-closed:** implementar redução por velocidade vanilla, `deltaMovement`, animação ou proximidade temporal alteraria o contrato e é proibido. Este ponto deve voltar ao Chat 1 somente se um novo receipt/provider real tornar possível revisar o contrato; até lá o fallback atual é o comportamento aprovado.
