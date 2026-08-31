# Delta de Capacidades — A0071–A0080

Este arquivo complementa `12-capability-delta-coverage.md` para o ciclo Chat 1 A0071–A0080 e registra a disposição completa antes de avançar os baselines.

## Fetch fresco antes da primeira perk — 2026-08-31

| Projeto | Baseline anterior | `main` fresco | Delta observado | Classificação | Disposição |
|---|---|---|---|---|---|
| RPG Skill Tree | `6ed628864199e74af23e6234d126959829f3c968` | `4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a` | somente PR #298: dossiês/auditoria/status/delta A0061–A0070 | **NÃO DEVE SER INTEGRADO / SEM DELTA JOGÁVEL** | nenhuma capacidade gameplay nova; A0071–A0080 são auditadas contra o runtime já existente em `main`. |
| Volcanoes | `a47bb868de9b4846d8ae9afb94374f9672ab381e` | `bbb273d61984e2c9bb84e8f8a56668ae7e315532` | hardening de proveniência/licenças/third-party e documentação | **NÃO DEVE SER INTEGRADO** | infraestrutura editorial/release não cria provider MARTIAL nem nova capacidade jogável. |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` | mesmo SHA | nenhum commit novo | **SEM DELTA** | manter authority/boundaries já reconciliados; Shroud Lich continua bridge read-only de A0070, fora do presente lote. |
| Black Arcana | `526d8196087c863e9df64051d5d39d88c3050856` | mesmo SHA | nenhum commit novo | **SEM DELTA** | manter Arcane Danger/Backlash/Resistance provider-native; nenhum dano/custo arcano vira ação MARTIAL deste lote. |

## Provider → árvore

Nenhuma capacidade detectada exige uma 11ª perk. As capacidades jogáveis relevantes do runtime A0071–A0080 já pertencem às dez perks deste lote ou permanecem fail-closed por ausência de binding/receipt:

- classificação ELITE → A0071;
- dano hostil recebido → A0072, mas availability depende de A0067;
- execução/abertura em dois roots → A0073/A0074, com reservation→commit obrigatório;
- stamina/thermal/exhaustion sustentados → A0075, `SEM HOOK SEGURO` para o conjunto all-or-nothing atual;
- slot `MARTIAL_STANCE` → A0076/A0077; state puro existe, binding input/payload ainda não;
- sprint/movimento legítimo → A0078;
- `StationaryStateService` → A0079, com invalidation forçada ainda parcial;
- dodge-success causal → A0080, `SEM HOOK SEGURO` no adapter atual.

## Baseline operacional para o próximo delta

Os baselines abaixo só avançam porque todo delta acima recebeu disposição explícita:

| Projeto | Novo baseline |
|---|---|
| RPG Skill Tree | `4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a` |
| Volcanoes | `bbb273d61984e2c9bb84e8f8a56668ae7e315532` |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` |
| Black Arcana | `526d8196087c863e9df64051d5d39d88c3050856` |

O próximo Chat 1 deve comparar `main` fresco contra estes SHAs antes de iniciar a perk seguinte.