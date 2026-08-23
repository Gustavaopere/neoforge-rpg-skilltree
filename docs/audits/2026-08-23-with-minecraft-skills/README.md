# Auditoria B — Com Minecraft Skills

Data: **23/08/2026**  
Branch auditada: **`main`**  
Commit auditado: **`87a8ef224af52e1a613bce892a5f3e6732691466`**

## Status deste material

Este diretório é um **registro histórico de auditoria**, não a especificação canônica isolada do projeto.

Esta auditoria foi executada depois do merge da fundação do sistema e utilizou efetivamente as skills `minecraft-modding`, `minecraft-mod-dev`, `minecraft-testing` e `minecraft-ci-release`, além de Superpowers, GitHub, DeepWiki e fontes NeoForge 1.21.1.

Ela deve ser lida como **Auditoria B** e comparada com a Auditoria A, pois os snapshots não são iguais: o commit B está 66 commits à frente do snapshot A e inclui o merge da fundação do sistema.

## Conteúdo

- [`FULL_AUDIT.md`](./FULL_AUDIT.md) — texto integral da auditoria recebida, preservado sem transformá-lo automaticamente em regra canônica.

## Mudanças relevantes entre os snapshots A e B

Entre `31377faa79685565b683923e9d8e2e62db073c92` e `87a8ef224af52e1a613bce892a5f3e6732691466` houve 66 commits. O intervalo inclui, entre outros:

- resolução de Primary/Secondary Classes e specificity score;
- loaders de archetypes, specializations e tree unlocks;
- migração semântica de Industrialist, Logistician e Prospector para especializações;
- preservação explícita dessas especializações durante reconcile;
- testes de compatibilidade v1–v4;
- ecologia/morph data-driven e integração opcional Identity 2;
- remoção de arquétipos/classes legados específicos.

Portanto, diferenças de contagem e alguns achados da Auditoria A são históricos, não contradições metodológicas.

## Próxima etapa

Comparar A × B, verificar os achados críticos no `main` atual e produzir uma consolidação que classifique cada ponto como:

- confirmado e ainda aberto;
- confirmado, mas já corrigido entre snapshots;
- parcialmente corrigido;
- exclusivo da Auditoria B;
- decisão de arquitetura ainda pendente.

Somente a consolidação verificada deve alimentar `AGENTS.md`, `MASTER_PLAN.md`, `TESTING.md` e ADRs canônicos.