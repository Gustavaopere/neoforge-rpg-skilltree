# 10.04 — Descoberta, progresso e recompensas

## Objetivo

Implementar um sistema de descoberta server-authoritative que transforme o Compêndio em progressão de exploração, sem permitir farm duplicado, spoof do cliente ou perda de progresso quando o catálogo mudar.

## Contrato implementado

- `DiscoveryState` define progressão monotônica `UNKNOWN -> SEEN -> STUDIED -> MASTERED`.
- `DiscoveryRecord` e `DiscoveryProgress` mantêm estado imutável por `CompendiumEntryId`, primeiro tempo/origem, variantes, objetivos e reward claims.
- `DiscoveryRuntime` recebe apenas sinais já validados pelo servidor e produz transições determinísticas/idempotentes.
- `DiscoveryInspectionValidator` exige identidade observada correta, distância válida, ferramenta exigida e estado server-side válido; não existe rota que aceite um “descobri X” do cliente como fato.
- `DiscoveryProgressCodec` v1 é determinístico, bounded e fail-closed; o decode não depende do catálogo carregado, portanto registros de conteúdo temporariamente removido sobrevivem ao save/load.
- `ModAttachments.COMPENDIUM_DISCOVERY` persiste o progresso individual por jogador e usa `copyOnDeath()`.
- `CompendiumDiscoveryRewardBridge` encaminha `CHARACTER_XP` para `ProgressionReward`/Core runtime usando reward IDs idempotentes. Tipos ainda não implementados falham explicitamente em vez de produzir comportamento parcial.
- `DiscoveryCompletionService` deriva totais globais, por categoria e por namespace usando apenas o catálogo carregado e respeitando exclusões.
- `CompendiumDiscoveryEvents` alimenta o runtime com eventos server-side confiáveis para derrota de entidade, interação com entidade, entrada/login em dimensão e observação de bioma com amostragem a cada 100 ticks.
- O Stage 10.04 não executa scan global/próximo de entidades por tick.

## Checklist de fechamento

- [x] eventos repetidos não duplicam progresso nem recompensa one-shot;
- [x] progressão nunca regride de estado;
- [x] variante/objetivo novo pode avançar sem redescobrir a espécie incorretamente;
- [x] identidade, trigger e inspeção forjados são rejeitados;
- [x] recompensa usa chave estável/idempotente compatível com o Core progression reward runtime;
- [x] progresso é persistido por jogador e preservado em morte, relog e troca de dimensão;
- [x] jogadores distintos mantêm progresso independente;
- [x] conteúdo ausente do catálogo não é apagado do save;
- [x] codec rejeita versão incompatível, truncamento, trailing bytes, IDs inválidos e payload acima dos limites;
- [x] denominadores de conclusão ignoram entradas explicitamente excluídas e não contam tombstones como catálogo atual;
- [x] feeds genéricos de runtime são server-authoritative e não fazem entity scan por tick.

## Escopo deliberadamente posterior

Compartilhamento/party continua opt-in e pertence a um estágio posterior. Recompensas adicionais como XP vanilla, mastery, commands, advancements/quests e itens exigem definição explícita/adapters próprios; não são simuladas pelo 10.04. Gatilhos especializados de domesticação, reprodução, fotografia, flora, estruturas e outros domínios serão adicionados nos respectivos subplanos. Versionamento final de rede e migrações globais de IDs continuam no 10.13.

## Evidência

- PR de implementação: **#71**.
- Head final reconciliado antes do merge: `2ff994bc6496ec51f705078b00cebfb25887da65`.
- Merge na `main`: `8fdfff0c518fa40099b9459e279118cdbef1b2fc`.
- CI focal pré-merge: `33200839682` / Compendium Discovery #37 — GREEN.
- CI completo pré-merge: `33200839670` / RPG Skill Tree #920 — GREEN, incluindo NeoForge build, JAR verification e dedicated-server smoke.
- CI focal pós-merge: `33201053431` / Compendium Discovery #38 — GREEN.
- CI completo pós-merge: `33201053442` / RPG Skill Tree #921 — GREEN, incluindo NeoForge build, JAR verification e dedicated-server smoke.

## Acceptance

**Acceptance: satisfied.** Descoberta, progresso e recompensas possuem boundary server-authoritative, persistência individual, idempotência, preservação de conteúdo removido e testes independentes da UI final.
