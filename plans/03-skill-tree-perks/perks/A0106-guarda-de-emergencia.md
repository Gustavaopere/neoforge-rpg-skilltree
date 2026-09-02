# A0106 — Guarda de Emergência

**Estado Chat 1:** DESIGN APROVADO após correção do boundary.  
**Runtime atual:** hook NeoForge existe; resolver/state específico ainda deve ser implementado pelo Chat 2.  
**Notion:** https://app.notion.com/p/3c569db9f0db813c8479df204149bf19

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY.
- Ramo: Anti-Pico de Dano.
- Camada: 5; função: Capstone.
- Ranks: 1; custo 3 PP.
- Pré-requisitos obrigatórios: A0104 Segundo Vento + A0105 Casca Reativa + A0095 Tenacidade ≥3 + Gateway VITALITY. Rotas alternativas não substituem esses requisitos.

## Contrato congelado

Em `LivingDamageEvent.Pre`, depois das reduções que devem preceder A0106, se o dano de vida projetado satisfizer `health - newDamage < 0.15 * maxHealth` e a recarga estiver livre, A0106 ativa **imediatamente**.

O golpe gatilho e todo evento hostil elegível nos próximos **60 ticks / 3 s** recebem `newDamage *= 0.65`, exatamente uma vez/evento. A ativação cria **1 token de Salvaguarda Fatal**. Depois da redução, se `newDamage >= health`, consumir o token e limitar o dano para `max(0, health - 1)`, deixando exatamente 1 de vida.

Cooldown: **180 s / 3600 ticks**, iniciado na ativação. O token não reaparece durante os 3 s; após ser gasto, a redução de 35% continua até o fim da janela.

## Ordem canônica do pipeline

A ordem não fica para o Chat 2 decidir:

1. reductions vanilla anteriores ao boundary `LivingDamageEvent.Pre`;
2. reducers RPG gerais/tipados que devam anteceder o emergency gate — A0092/A0096 e A0101/A0102/A0103, além de A0108/A0109 quando algum dia estiverem adquiríveis;
3. calcular o threshold de A0106 sobre o `newDamage` resultante;
4. se ativada/janela ativa, aplicar 0,65 uma única vez;
5. por último, se ainda letal e houver token, consumir e aplicar o clamp para 1 HP;
6. seguir o pipeline NeoForge normal. Não prever nem reescrever estágios futuros de absorption.

A0105 altera Armor/Toughness e por isso já participa upstream no estágio vanilla correspondente; A0106 não reaplica esses modifiers.

## Provider, hook e authority

NeoForge `21.1.248` + RPG Skill Tree. `LivingDamageEvent.Pre` é o boundary autoritativo comprovado antes da perda final de vida. Epic Fight só contribui quando sua fonte chega ao mesmo `DamageSource` causal.

P-0034 deixa de ser blocker de API: a pendência atual é implementar o resolver/state do contrato acima, não procurar um provider externo de “pre-morte”.

## Causalidade, deduplicação e exclusões

- uma aplicação A0106 por evento/root;
- somente dano hostil elegível;
- bypass/inevitável explicitamente excluído, `/kill`, Void/final kill, `BLOOD_MAGIC_COST`, self/resource costs e morte fisiológica ficam fora;
- não ressuscitar em Post/death e não restaurar vida depois do fato;
- o golpe gatilho pode consumir imediatamente a única Salvaguarda;
- não confundir o clamp com invulnerabilidade.

## Lifecycle e fail-closed

Cooldown/state são server-authoritative e precisam persistir/reconciliar segundo o storage canônico. Morte/logout/dimensão não podem resetar o cooldown como exploit. Rank loss/respec/rules reload removem estado que não pode mais existir sem apagar cooldown de modo abusável quando o contrato persistido exigir preservação.

Sem resolver/state completo, o node permanece indisponível/não comprável.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: único owner do reducer/state e da aquisição.
- Black Arcana: emergency protection própria e Backlash continuam canais independentes; não são token A0106.
- Enshrouded/Volcanoes: hazards não ganham elegibilidade por tema; somente a classificação causal explícita do dano decide.
- Nenhum projeto próprio cria segundo death-prevention ledger.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS.
2. Integração global — PASS, ordem do DamageMitigationResolver congelada.
3. Qualidade/identidade — PASS, capstone anti-pico com token único.
4. Topologia — PASS.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS.

Authority, ordem, causalidade, dedup, anti-abuso, exclusões, lifecycle, cooldown e fail-closed estão explícitos.

## Pendências para Chat 2

- `P-A0106-01`: implementar threshold/ativação/reducer/token no `LivingDamageEvent.Pre` exatamente na ordem congelada.
- `P-A0106-02`: implementar janela 60 ticks + cooldown 3600 ticks + storage/reconciliação anti-reset.
- `P-A0106-03`: implementar exclusions/bypass policy e once/event dedup.
- `P-A0106-04`: availability/purchase fail-closed até o consumer completo.

## Testes exigidos ao Chat 3

Threshold estrito abaixo de 15%, exatamente 15%, golpe gatilho reduzido, lethal clamp para 1 HP, token único, janela após token, cooldown, exclusions, interação com reducers anteriores, ausência de resurrection/absorption prediction, death/logout/dimension/respec/reload anti-reset, multiplayer, GameTests, build, JAR e dedicated-server smoke.
