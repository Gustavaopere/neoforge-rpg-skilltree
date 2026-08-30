# A0018 — Maestria de Lanças — Linha de Interceptação

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db814b9f8bc5f70f9ba617
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

## Especificação canônica do Notion

- **Código:** A0018
- **Nome:** Maestria de Lanças — Linha de Interceptação
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Lanças
- **Ramo:** Lanceiro — Interceptação
- **Camada:** 4
- **Função na Árvore:** Capstone
- **Tier:** Grande
- **Faixa de Poder:** Alto
- **Ranks Máx.:** 1
- **Custo por Rank:** 2
- **Dependências Obrigatórias:** A0016 Distância Ideal + A0017 Interceptação + maestria de lanças ≥ 80. Ponte/rota alternativa não substitui requisitos.
- **Pré-requisitos:** A0016 + A0017.
- **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree.
- **Efeito:** com 3 cargas de Controle de Distância, quando inimigo cruza de fora para dentro da faixa ideal, abre Janela de Interceptação. O próximo hit direto de lança dentro da janela consome todas as cargas, recebe +15% de dano físico elegível e +40% de impacto/pressão de guarda. O mesmo alvo não pode gerar nova janela por 8 s.
- **Escalonamento:** 1 rank. Maestria ≥80: janela 3 s; ≥90: 3,5 s; ≥100: 4 s. Coeficientes não aumentam.
- **Gate:** Gateway `epic_spear` + A0016 + A0017 + mastery `epicfight:spear` ≥80; terminal da Árvore Exterior.
- **Hook:** registro de distância por alvo + Controle de Distância + hit direto de lança + deduplicação por alvo.
- **Fallback:** se a integração não detectar diretamente o cruzamento, usar comparação server-side da distância do alvo entre atualizações recentes. Se nem isso for confiável, capstone indisponível.
- **Regra:** não move o jogador automaticamente, não puxa alvo e não cria ataque automático. `TERMINAL_EXTERIOR: MARTIAL/LANÇAS`; especialista só por mapeamento explícito + fundamentos + ≥100 Passive Points. Respec protege dependências de especialista.

## Auditoria obrigatória — 9 eixos

1. **Dependências, bloqueios e gates — PASS.** A0016/A0017 e mastery ≥80 estão modelados no catálogo/topologia.
2. **Integração global — PASS.** Reutiliza Controle de Distância, alcance e impacto existentes sem criar automação de combate.
3. **Qualidade e identidade — PASS.** Capstone de leitura de distância: exige 3 cargas, cruzamento espacial, janela curta, consumo total e lockout por alvo.
4. **Topologia — PASS.** Fecha os dois Notables de lança na camada 4.
5. **Especializações — PASS.** Terminal exterior com regras explícitas de Gate C/respec.
6. **PT-BR — PASS.** Nome, estado e janela em português.
7. **Preenchimento do Notion — PASS.** Cargas, janela, mastery, dano, impacto e lockout estão definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura da modlist — PASS.** O caminho Epic Fight + amostragem server-side fornece os fatos necessários sem automação artificial.

## Contrato técnico esperado

- Requer A0018, mastery ≥80 e 3 Controle de Distância.
- Abrir somente na transição comprovada fora→dentro da faixa ideal.
- Janela: 3.000/3.500/4.000 ms para mastery 80–89/90–99/≥100.
- Próximo hit direto de lança contra o alvo da janela consome 3 cargas.
- Dano físico elegível ×1,15.
- Impacto/pressão ×1,40 somente quando hook disponível.
- Lockout de 8.000 ms por alvo após consumo da janela.
- Não criar movimento, pull, root ou ataque automático.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.interceptionMasteryWindowMillis(...)` implementa 3/3,5/4 s e `A0018_TARGET_LOCKOUT_MILLIS=8_000`.
- `NotionCombatPerkState.recordSpearRange(...)` mantém estado dentro/fora por alvo e arma A0018 apenas em transição `false -> true`, com 3 cargas e lockout pronto.
- `consumeLineWindow(...)` remove a janela e inicia lockout de 8 s.
- `A0001A0020CombatPolicy.beforeHit(...)` consome 3 cargas, deduplica por `A0018:consume`, multiplica dano por 1,15 e impacto/pressão por 1,40 quando disponível.
- `onEpicFightTick(...)` fornece amostragem server-side de distância/alcance e mastery real de `epicfight:spear`.
- `A0001A0020CombatPolicyTest.spearWindowsConsumeDistanceControlAndApplyTargetLockout()` verifica consumo, dano, impacto e lockout.

## Pendências técnicas

Nenhuma divergência específica foi identificada no caminho auditado. Se a obtenção de alcance/distância mudar em versão futura do provider, o cruzamento precisa ser revalidado antes de manter a perk ativa.

## Testes obrigatórios

- [x] janela 3/3,5/4 s;
- [x] requisito de 3 cargas;
- [x] consumo total das cargas;
- [x] dano +15% e impacto +40%;
- [x] lockout de 8 s por alvo;
- [x] detecção server-side fora→dentro;
- [x] GameTest/dedicated server revalidados no CI #2130 para a versão exata atual.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA com boundary causal retroativa; janela, dano e lockout permanecem inalterados.
- **RPG Skill Tree:** authority de Controle de Distância, janela e lockout por alvo; deduplicação continua por identidade causal única.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não abre/consome Janela de Interceptação nem recebe os bônus do capstone.
- **Mobstein 5.4.4:** ataques de allies/bodyguards ressuscitados não abrem/consomem a janela em nome do dono; ataques diretos do jogador contra entidades Mobstein continuam elegíveis quando cumprem o contrato.
- **Volcanoes / Enshrouded:** NÃO DEVE SER INTEGRADO ao crossing da faixa; hazards, pressão, Shroud/Exposure/Story não fornecem crossing ou hit de lança.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch confirmou persistência.
- **Fail-closed:** sem cruzamento server-side confiável, o capstone permanece indisponível; nenhum dos novos providers é fallback implícito.
- **Chat 2:** preservar direct-player provenance, deduplicação e lockout; não adicionar bridges temáticas.

## Chat 2 — revalidação de implementação — PR #234

- [x] Gate A0016 + A0017 + mastery ≥80 preservado.
- [x] Crossing server-side, janela 3/3,5/4 s, consumo de 3 cargas e lockout de 8 s preservados.
- [x] Dano +15% e impacto/pressão +40% continuam condicionados ao hit direto elegível.
- [x] `ARCANE_BACKLASH`, companions e demais fontes indiretas não abrem/consomem a janela.
- [x] Deduplicação por root action/alvo e fail-closed de crossing continuam ativos.
- [x] Regressões JUnit e NeoForge GameTests verdes no CI #2130.
- [x] Build, JAR e dedicated-server smoke verdes no CI #2130.

**Estado Chat 2:** `IMPLEMENTAÇÃO VALIDADA EM CI`; confirmação definitiva ocorre com o merge da PR #234 na `main`.
