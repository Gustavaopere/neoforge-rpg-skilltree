# AUDITORIA CHAT 3 — A0061–A0070 — VALIDAÇÃO, PENDÊNCIAS E MERGE

## Escopo

- Lote exato: **A0061–A0070**.
- PR operacional: **#391** (`feat/chat2-a0061-a0070-stacked-handoff` → `main`).
- Plataforma: NeoForge 1.21.1 / Java 21.
- Nenhuma perk A0071+ foi iniciada neste ciclo.
- Objetivo deste registro: validar o código recebido do Chat 2 contra os dossiês aprovados, resolver pendências técnicas que não exigem redesign, registrar fail-closed/fallback e deixar evidência para CI/merge.

## Estado técnico validado

| Perk | Resultado Chat 3 | Pendência residual |
|---|---|---|
| A0061 — Força Aplicada | IMPLEMENTAÇÃO CONFIRMADA no pipeline físico canônico auditado | nenhuma bloqueante; Simply Swords permanece provider-native |
| A0062 — Golpe Preciso | IMPLEMENTAÇÃO CONFIRMADA no resolvedor crítico canônico | adapters futuros devem convergir no mesmo resolver |
| A0063 — Impacto Crítico | IMPLEMENTAÇÃO CONFIRMADA após resolução crítica canônica | nenhuma bloqueante |
| A0064 — Ritmo de Combate | IMPLEMENTAÇÃO CONFIRMADA onde há binding semântico de attack speed | famílias sem equivalência segura continuam fail-closed |
| A0065 — Penetração Física | IMPLEMENTAÇÃO CONFIRMADA com contribuição independente | providers sem backend seguro continuam fail-closed |
| A0066 — Impacto Marcial | IMPLEMENTAÇÃO CONFIRMADA para melee Epic Fight; FAIL-CLOSED CONFIRMADO fora de receipt de Impact | não fabricar Impact para projectile/provider sem receipt |
| A0067 — Firmeza Ofensiva | IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA | perk não é jogável enquanto Epic Fight não fornecer lifetime ofensivo seguro; isso não é blocker de merge porque o node é indisponível |
| A0068 — Dano contra Feridos | IMPLEMENTAÇÃO CONFIRMADA com borda estrita `< 35%` pré-impacto | nenhuma bloqueante |
| A0069 — Dano contra Íntegros | IMPLEMENTAÇÃO CONFIRMADA com borda estrita `> 85%` pré-impacto | nenhuma bloqueante |
| A0070 — Dano contra Chefes | IMPLEMENTAÇÃO CONFIRMADA para vanilla/Cataclysm/Apothic + `enshrouded:shroud_lich` opcional | demais providers externos permanecem fail-closed até IDs/adapters exatos |

## Correções técnicas materializadas pelo Chat 3

### A0067 — unavailable-node invariant

A superfície auditada do Epic Fight 21.17.3.1 não prova um lifecycle provider-native seguro para a janela ofensiva necessária por A0067. Implementar timers, STUN_ARMOR permanente ou heurística visual mudaria a semântica aprovada e exigiria redesign.

A PR #391 corrige isso sem redesign:

- `CombatPerkAvailabilityRuntime.isCatalogCodeAvailable("A0067")` retorna `false`;
- purchase/gate reaproveita a disponibilidade estrutural canônica;
- `A0061A0080RuntimeState.ranks(...)` passa ranks persistidos por `CombatPerkAvailabilityRuntime.effectiveRanks(...)`;
- ranks legados de A0067 permanecem no storage apenas para recovery/refund, mas são rank efetivo `0` no gameplay;
- nenhum STUN_ARMOR permanente, knockback resistance, dano defensivo genérico ou super armor foi introduzido.

Testes específicos:

- `a0067IsStructurallyUnavailableAndLegacyRankIsMasked`;
- `runtimeRanksMasksUnavailablePersistedRankAtServerBoundary`.

### A0070 — identidade Enshrouded

A PR #391 adiciona à tag canônica `data/rpgskilltree/tags/entity_type/bosses.json`:

- `enshrouded:shroud_lich`;
- `required: false`.

O bridge é estritamente read-only. A RPG Skill Tree não usa bossbar, nome, tamanho, vida máxima, arena ou fase como heurística e não grava Story, reward, ritual, death marker ou qualquer estado do Enshrouded.

Mowzie's Mobs, Legendary Monsters, Born in Chaos e Mobstein continuam **fail-closed** até registry IDs/adapters exatos serem comprovados.

### Cobertura de regras puras A0061–A0070

`A0061A0070Chat3CoverageJUnitTest` adiciona regressões para:

- indisponibilidade estrutural e masking de A0067;
- boundary server-side de ranks efetivos;
- borda estrita A0068 em `0.35` e A0069 em `0.85`;
- precedência **BOSS > ELITE** sem double-stack A0070+A0071;
- A0063 somente após `canonicalCritical=true`;
- independência entre dano-base A0061, penetração A0065 e Impact A0066.

## Authority, causalidade, deduplicação e anti-abuso

- Provider-native first permanece obrigatório.
- Dano físico elegível exige root causal direto do jogador real.
- Crítico é resolvido uma vez por root; A0063 não cria novo crítico ou segundo hit.
- Penetração A0065 não vira armor shred persistente e não duplica armor ignore provider-native.
- Impact A0066 não é traduzido para knockback/stun/dano quando o provider não entrega receipt equivalente.
- A0068/A0069 usam snapshot de vida pré-impacto; o hit atual não retroage sobre a elegibilidade.
- BOSS domina ELITE; A0070 e A0071 não acumulam por dupla classificação do mesmo alvo/root.
- Summons, fake players, hazards, reflexão, DOT e procs derivados não herdam contribuição MARTIAL sem provenance aprovada.

## Fail-closed e fallback

O lote não depende de fallback semântico inventado. Onde a API/provider não fornece boundary equivalente, a contribuição fica zero ou o node fica indisponível. Em particular:

- A0066 projectile: sem Impact sintético;
- A0067: node indisponível até lifetime ofensivo seguro;
- A0070 providers ainda não classificados: sem promoção por heurística.

## CI e evidência de fechamento

Antes das alterações documentais deste fechamento, o HEAD de correção do lote passou pelos check-runs da PR #391 sem failures; SonarQube, anteriormente vermelho, foi corrigido e ficou GREEN, e CodeQL concluiu com sucesso.

Como este documento e os dossiês alteram o HEAD, a regra de fechamento é: **revalidar todos os checks no HEAD documental final antes de declarar CI GREEN definitivo e antes do merge**. O SHA final e o estado pós-merge devem ser registrados na PR/STATUS após essa revalidação.

## Decisão Chat 3

- Nenhum problema encontrado exige redesign do Chat 1.
- As correções necessárias de A0067/A0070 são compatíveis com o contrato aprovado e foram materializadas na mesma PR.
- A0061–A0070 podem avançar para `IMPLEMENTAÇÃO CONFIRMADA` conforme os estados individualizados acima, condicionado apenas à revalidação CI do HEAD documental final.
- Não iniciar A0071+ neste ciclo.