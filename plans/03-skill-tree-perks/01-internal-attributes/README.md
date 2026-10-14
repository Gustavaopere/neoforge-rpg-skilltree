# 01 — Internal Attributes — Ponto 0

O sistema tem seis atributos canônicos **compartilhados entre todas as árvores**, independentes de P/S/T. Ranks são ilimitados no RPG Core; seus efeitos não podem crescer linearmente para sempre.

**Contrato de investimento e balanceamento:** [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Código | Atributo | Papel principal |
|---|---|---|
| I0001 | [Força](./I0001-strength.md) | impacto físico, esforço muscular e tarefas manuais |
| I0002 | [Constituição](./I0002-constitution.md) | saúde corporal e resistência a desgaste |
| I0003 | [Agilidade](./I0003-agility.md) | movimento, cadência corporal e entrega de projéteis |
| I0004 | [Inteligência](./I0004-intelligence.md) | reserva de magia, regeneração e potência de habilidades mágicas |
| I0005 | [Determinação](./I0005-determination.md) | disciplina, constância, recuperação e resistência a interrupções |
| I0006 | [Carisma](./I0006-charisma.md) | negociação, liderança de aliados e cura prestada |

**Status:** desenho v0.1 com fórmulas explícitas, **não implementado em gameplay**. Cada atributo concede três trilhas de bônus por marcos com retornos decrescentes, fonte de verdade em `AttributeRanks`. Gastos de rank seguem `AttributeRankCostPolicy`. O preço proposto e os marcos serão confrontados com o orçamento real de Core Points.

**Requisito de perk:** `INTELLIGENCE >= 20`, por exemplo, é mínimo **não consumível**, não gasto de atributos. O runtime ainda precisa acrescentar esse gate a `NodeAccessRequirement`.

**Regra anti-duplicação:** não criar segunda barra de mana, stamina, HP ou stats específicos. Mods opcionais usam adapters versionados e fail-closed, sem efeito fictício se o provider não suportar o hook.

**Referências:** [BG3 — Abilities](https://bg3.wiki/wiki/Abilities), [Path of Exile — Attributes](https://www.poewiki.net/wiki/Attribute), [Elden Ring — Vigor](https://eldenring.wiki.gg/wiki/Vigor). Esses jogos inspiram papéis e soft caps, **não fornecem coeficientes aprovados para Minecraft**.


## Progresso de implementação — 14/10/2026

- Cálculo matemático dos 18 efeitos: `AttributeInvestmentBonuses` (puro, `long` e limites finitos).
- Quatro efeitos conectados ao Minecraft 1.21.1 vanilla: Força/resistência a empurrão, Constituição/vida máxima, Agilidade/velocidade de movimento e Agilidade/velocidade de ataque, via `InvestedAttributeEffectsRuntime`. **Implementados no código, ainda dependentes de validação GameTest/servidor e cross-mod.**
- Outros 14: bloqueados ou pendentes de source/API/hook equivalente na versão instalada; jamais simulados na UI nem por sistema paralelo.
- Requisitos de `requiredAttributes` nos nós usam nomes canônicos em maiúsculas (por exemplo `{"INTELLIGENCE":20,"AGILITY":15}`), número inteiro positivo `long`; verificação e reconciliação no servidor via `NodeAccessResolver` e `ProgressionService` (sem consumo de ranks). Perda do mínimo no respec usa política existente de remover/refundir ranks dos nós passivos.
- **Economia preservada:** `UnitAttributeRankCostPolicy`, 1 Core Point por rank, sem instalar a curva proposta `1+floor((novoRank−1)/50)`; alterar a economia sem migração afetaria custo/reembolso de saves.
- Documentos/versões físicos da modlist são apenas evidência de instalação declarada; nenhuma API de mod externo foi considerada certificada sem examinar o JAR da mesma release.
