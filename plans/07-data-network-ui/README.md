# 07 — Data, Network & UI

Carregar dados com validação forte e apresentar progressão no cliente sem transferir authority de gameplay para a interface.

Ordem: schemas → reload/snapshots → protocolo → sync do jogador → tela da árvore → localização/acessibilidade.

## 07.05 — Skill Tree UI

[`05-skill-tree-ui.md`](05-skill-tree-ui.md) é o plano canônico de apresentação do redesign aprovado em 2026-09-12.

A arquitetura visual anterior — Árvore 2 em 11 domínios radiais com classes no cinturão externo — foi **substituída**. O produto final passa a ser:

- Árvore 1 para atributos/base;
- Árvore 2 como **uma única malha conectada com 23 regiões/galhos de classe**;
- fronteiras porosas, corredores e shared nodes entre classes próximas;
- Classe de Origem destacando o ponto inicial e afinidade de custo, sem bloquear o restante da árvore;
- Class Gateways concedendo identidades secundárias somente após requisitos reais;
- Árvore 3 com especializações class-centric e nodes apenas quando existe topologia server-authoritative real;
- 512 nodes como baseline histórico, não teto de produto.

O contrato de gameplay correspondente está em `plans/04-classes-masteries-specializations/07-class-oriented-progression-overhaul.md`.

## Gates

### D001

A decisão Passive Skill Tree versus custom UI continua bloqueante para implementação integral. Antes de decidir, o vertical slice precisa provar:

- duas regiões de classe vizinhas;
- shared node;
- Class Gateway;
- specialization gateway real;
- custo dinâmico;
- requisito de atributo-base;
- purchase/respec;
- pan/zoom;
- sync/reload server-authoritative.

### Pesquisa Mine and Slash

Antes de fechar nosso formato de authoring, concluir o spike clean-room sobre `TalentTree`, `TalentGrid`, `SkillTreeScreen`, `TalentsScreen`, `PerkButton`, Ascendancy, persistência, networking e performance. A abordagem grade → parser → grafo é referência técnica, não código a ser copiado.

## Authority

A UI nunca pode criar node, classe, especialização, custo, requisito, forma ou unlock que não exista no estado/catálogo autoritativo. Presença de provider vem do runtime/modlist corrente; AE2/Oritech/Identity2 removidos não permanecem hardcoded na interface.