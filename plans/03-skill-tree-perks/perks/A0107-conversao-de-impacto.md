# A0107 — Conversão de Impacto

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0093 está indisponível e P-0035 não é canônico na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db813b82c4f57469727e4d

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY ↔ MARTIAL.
- Ramo: Guarda e Estamina.
- Camada: 4; função: Notable.
- Ranks: 1; custo 2 PP.
- Pré-requisitos: A0093 Guarda Econômica ≥3 + A0095 Tenacidade ≥3 + Gateway VITALITY + provider simultâneo de pressão/interrupção e Stamina nativa.

## Contrato congelado

Quando — e somente quando — existir adapter provider-native compatível, A0107 poderá converter **até 35%** da pressão de impacto/interrupção elegível recebida em custo de Stamina nativa.

O adapter deve expor uma equivalência determinística e versionada `impact_pressure -> stamina_cost`. O RPG Skill Tree **não define taxa universal**. Para cada evento, o adapter calcula a maior parcela conversível ≤35% e o custo exato correspondente. A parcela de pressão só é removida se o custo puder ser debitado integralmente de modo atômico.

Stamina insuficiente, quote ausente ou provider incompatível preserva 100% da pressão original.

## Blockers atuais e availability transitiva

1. A0093 é formalmente `UNAVAILABLE_NODE`; portanto A0107 é indisponível por predecessor.
2. A infraestrutura histórica P-0035/`ImpactStaminaBridge` existe apenas no draft PR #15, não na `main` canônica.
3. Um P-0035 isolado nunca pode bypassar A0093 nem a availability do predecessor.

Estado atual obrigatório: **não comprável e sem gasto de PP**.

## Provider, hook e authority

Epic Fight `21.17.3.1` pode ser owner de Stamina/pressão somente através de adapter versionado realmente compatível. Contrato futuro: receipt real de pressão/interrupção + `ImpactStaminaBridge.quote(...)` + reserva/débito atômico de Stamina + redução da pressão somente após confirmação.

É proibido usar 1:1, percentual da barra, hunger/exhaustion, animação, polling de delta de Stamina, refund pós-fato ou bridge histórica isolada como autorização.

## Causalidade, deduplicação e anti-abuso

- um evento de pressão é convertido no máximo uma vez;
- custo e redução formam uma única transação causal;
- falha no débito deixa o evento original intacto;
- nenhum custo de Stamina pode permanecer sem a redução correspondente;
- provider/version mismatch falha fechado e não cria fallback vanilla.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: ProgressionService governa predecessor/gateway; draft #15 não é capability canônica.
- Volcanoes/Enshrouded/Black Arcana: nenhum fornece equivalência impact→Stamina; `NÃO DEVE SER INTEGRADO`.
- Nenhum sistema de peso/pressão ambiental substitui pressão de guarda/interrupção do combat provider.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS com availability transitiva explícita.
2. Integração global — PASS em design; consumer ausente corretamente fail-closed.
3. Qualidade/identidade — PASS, conversão real e não redução gratuita.
4. Topologia — PASS, ponte VITALITY↔MARTIAL.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS; nenhum falso provider promovido.

Causalidade, dedup, anti-abuso, atomicidade, fallback, versões e purchase fail-closed estão congelados.

## Pendências para Chat 2

- `P-A0107-01`: materializar availability transitiva: enquanto A0093 estiver indisponível, A0107 deve falhar compra antes do gasto.
- `P-A0107-02`: não integrar/copiar P-0035 do PR #15 como atalho. Se a API real atual divergir do contrato, registrar evidência e manter fail-closed.
- `P-A0107-03`: se A0093/P-0035 se tornarem canônicos durante o ciclo, implementar quote+debit+pressure reduction como uma única transação e validar versão exata; caso contrário, manter apenas o unavailable contract.

## Testes exigidos ao Chat 3

No estado atual: purchase indisponível sem gasto, predecessor transitivo, draft #15 não habilita node, provider absent/version mismatch, nenhum fallback 1:1/refund/polling. Se o adapter for materializado: atomicidade, insuficiência de Stamina, once/event, rollback e dedicated provider-present acceptance. Sempre: GameTests/build/JAR/dedicated-server smoke aplicáveis.
