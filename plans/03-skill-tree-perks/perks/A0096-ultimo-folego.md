# A0096 — Último Fôlego

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO FECHADO.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Implementação:** classifier físico compartilhado, hostilidade causal e snapshot pré-impacto estão presentes; **não é IMPLEMENTAÇÃO CONFIRMADA**.
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

- A0096 herda exclusivamente `rpgskilltree:physical`; não cria classifier alternativo.
- Fontes modded desconhecidas permanecem fail-closed.
- Não medir vida depois do impacto, não usar previsão client-side e não transformar o efeito em redução universal.

## Evidência após Chat 2

- O classifier `rpgskilltree:physical` materializado por A0092 é reutilizado diretamente por A0096; não existe segunda tag.
- O runtime defensivo compartilha o classificador causal de hostilidade baseado em `LivingEntity` não-self/não-ally, removendo o drift `Enemy || Player` identificado pelo Chat 1.
- `A0081A0100CombatPolicy.physicalDamageMultiplier` preserva snapshot pré-impacto e composição multiplicativa A0092→A0096.
- O efeito é resolvido no mesmo pipeline incoming, evitando contributor/evento paralelo.
- O Chat 2 **não executou** testes de borda, GameTests, build NeoForge, dedicated-server smoke ou CI.

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
- [x] Classifier compartilhado implementado
- [x] Hostilidade causal reconciliada
- [x] Snapshot/dedup estrutural reconciliados
- [x] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/boundaries
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
