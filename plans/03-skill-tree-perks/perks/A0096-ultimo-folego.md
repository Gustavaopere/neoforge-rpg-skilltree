# A0096 — Último Fôlego

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO FECHADO.
- **Implementação:** fórmula preparatória existe, mas depende do classifier físico ainda não materializado; **não confirmado**.
- **Notion:** `3c569db9-f0db-81a4-962f-c0e807dda1af`; corrigido e re-fetch confirmado no ciclo A0091–A0100.
- **Domínio:** VITALITY; Camada 3; Ramo Sobrevivência em Baixa Vida.
- **Ranks:** 3; custo 1 PP/rank.
- **Dependência:** A0092 Resistência Física ≥2 + Gateway VITALITY.

## Contrato canônico

- Se a vida **imediatamente antes do impacto** estiver abaixo de 30% da vida máxima, dano hostil físico elegível recebe +4% de redução por rank, até 12%.
- Um hit iniciado em ≥30% não ganha o benefício só porque o próprio hit derrubará o jogador abaixo do limiar.
- A0096 usa exatamente o classifier `rpgskilltree:physical` de A0092; não mantém tag/classifier próprio.
- A composição é multiplicativa e ordenada: `dano × (1 − A0092) × (1 − A0096)`.

## Provider / authority

- Minecraft/NeoForge: snapshot de `health/max_health`, DamageSource/DamageType e evento incoming.
- RPG Skill Tree: classifier físico e pipeline defensivo únicos.
- Black Arcana `BLOOD_MAGIC_COST` pode reduzir a vida real e, portanto, alterar o estado futuro de baixa vida, mas o custo em si nunca é mitigado por A0096.
- `ARCANE_BACKLASH` não é convertido para dano físico/hostil por este node.
- Volcanoes/Enshrouded hazards não entram sem classificação física explícita; nenhum adapter é aprovado neste lote.

## Hostilidade causal

- O dano beneficiado precisa ter atacante causal `LivingEntity`, diferente do jogador e não aliado; PvP não aliado conta.
- Não restringir a `instanceof Enemy`; mobs hostis/modded com autoria causal não podem ser excluídos apenas pela classe Java.
- Ambiente/self/resource-cost não recebe A0096 mesmo que seja físico/classificado.

## Fallback / fail-closed

- Enquanto A0092 não materializar `rpgskilltree:physical`, A0096 não cria classifier alternativo e fica sem cobertura física real.
- Fontes modded desconhecidas permanecem fail-closed.
- Não medir vida depois do impacto, não usar previsão client-side e não transformar o efeito em redução universal.

## Evidência atual e pendências para Chat 2

- `A0081A0100CombatPolicy.physicalDamageMultiplier` já usa snapshot pré-impacto e composição multiplicativa A0092→A0096.
- `A0081A0100CombatEvents` chama essa fórmula, mas o classifier `PHYSICAL_DAMAGE` ainda depende da tag ausente.
- O classifier atual de hostilidade do runtime restringe `(Enemy || Player)` e diverge do contrato causal do Notion.
- **P-A0096-01:** herdar/materializar o classifier de A0092; sem segunda tag.
- **P-A0096-02:** compartilhar o classificador causal de hostilidade A0096/A0097, sem `Enemy` como requisito.
- **P-A0096-03:** preservar snapshot pré-impacto e garantir uma aplicação por evento/root.

## Deduplicação / anti-abuso

- Um evento causal = uma aplicação.
- A0092 e A0096 são contributors do mesmo pipeline, não eventos independentes.
- Sem Mastery, producer ou estado persistente próprio.

## Testes obrigatórios Chat 3

1. borda 29,999% ativa; 30,000% e acima não ativa;
2. hit iniciado acima de 30% não ativa retroativamente;
3. rank 1–3 aplica 4/8/12% uma vez;
4. A0092+A0096 compõem multiplicativamente, não aditivamente;
5. BLOOD_MAGIC_COST pode alterar vida futura mas nunca é mitigado;
6. dano físico ambiental sem atacante causal não recebe A0096;
7. atacante modded `LivingEntity` não aliado, mesmo sem `Enemy`, pode satisfazer hostilidade quando a fonte física é classificada;
8. fonte modded sem classifier continua fail-closed;
9. GameTest/dedicated-server cobre snapshot real pré-impacto.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0092≥2 + VITALITY. |
| Integração global | PASS | Baixa vida real sem mitigar custos corporais/mágicos. |
| Qualidade/identidade | PASS | Defesa condicional de emergência. |
| Topologia | PASS | Camada 3 após resistência física. |
| Especializações | PASS | `VITALITY/LOW_HEALTH_DEFENSE`. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Corrigido + re-fetch. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Classifier físico compartilhado + snapshot server-side. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0096-01 classifier compartilhado implementado
- [ ] P-A0096-02 hostilidade causal reconciliada
- [ ] P-A0096-03 snapshot/dedup reconciliados
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/boundaries
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
