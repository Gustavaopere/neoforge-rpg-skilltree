# Auditoria delta — Simply Swords stack — A0021–A0030

## Escopo

Terceiro sublote exato da reauditoria A0001–A0050 contra Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated Simply Swords 1.4.0, Simply Swords: Cataclysm 1.0.2 e Simply Tooltips 0.1.5, usando Epic Fight Compat 1.1.0 apenas como adapter de classificação/moveset.

## Regra transversal

- Não criar perks nominais de Simply Swords.
- A árvore consome somente a família Epic Fight server-side (`DAGGER`/`HAMMER`) e a causalidade do root direto do jogador.
- Implicits, Runic Powers, Awakening, sockets/gem powers, Unique abilities e traits Cataclysm permanecem provider-owned.
- Derived/ability hits não viram novos roots MARTIAL por associação ao item/owner.
- Simply Tooltips é apresentação e não fornece authority mecânica.

## Matriz A0021–A0030

| Perk | Disposição | Resultado |
|---|---|---|
| A0021 — Precisão com Adagas | COBERTA POR SISTEMA UNIVERSAL | Dagger/Sai/Simply More entram se Epic Fight Compat resolver `DAGGER`; crítico segue resolver canônico único. Backstab/ability do provider não cria segundo crítico. |
| A0022 — Ritmo das Sombras | COBERTA POR SISTEMA UNIVERSAL | Fluxo só por root direto DAGGER e receipts já aprovados. Procs/extra hits/abilities Simply não concedem cargas extras nem renovam estado. |
| A0023 — Ataque ao Ponto Cego | CORRIGIDA | Backstab Implicit pode coexistir no mesmo root direto, mas não é rerrolado/reaplicado pelo RPG e não cria novo consumo de Fluxo. Notion corrigido. |
| A0024 — Dança das Sombras | COBERTA POR SISTEMA UNIVERSAL | Janela continua RPG-owned e só é consumida por root DAGGER direto. Ability/derived hit Simply não arma nem consome nova janela. |
| A0025 — Treino com Martelos I | COBERTA POR SISTEMA UNIVERSAL | Hammer/Greathammer entram apenas se Epic Fight Compat resolver `HAMMER`; sunder não classifica a arma e não concede Mastery adicional. |
| A0026 — Treino com Martelos II | COBERTA POR SISTEMA UNIVERSAL | Cadência permanece Epic Fight; attack-speed proc provider-owned não é recalculado nem retriggerado pelo RPG. |
| A0027 — Precisão com Martelos | COBERTA POR SISTEMA UNIVERSAL | Uma única resolução crítica por root HAMMER; sunder/ability/trait não cria crítico paralelo. |
| A0028 — Abalo Crescente | CORRIGIDA | Armor sunder/ignore Simply não é guard/posture pressure, Abalo nem receipt substituto. `P-A0028-01` permanece aberta. |
| A0029 — Quebra de Postura | CORRIGIDA | Sunder/stun/queda de Armor/ability não satisfaz heavy, guard pressure, guard break ou stamina receipt. `P-A0029-01` permanece aberta. |
| A0030 — Golpe Demolidor | CORRIGIDA | Sunder/traits não constituem guard-break/heavy nem abrem/consomem Janela Demolidora. `P-A0030-01` permanece aberta. |

## Notion

- Fetch fresco: 10/10 por dossiê/catálogo do lote; quatro páginas exigiram mutação semântica explícita.
- Mutadas: A0023, A0028, A0029 e A0030.
- Campos: `Provider/Mods`, `Hook`, `Fallback`, `Regra`.
- Re-fetch pós-escrita: **4/4 PASS**.
- A0021, A0022, A0024, A0025, A0026 e A0027 não exigiram mutação funcional porque os contratos provider-native/direct-root já cobrem o stack novo.

## Pendências preservadas

- `P-A0028-01`: guard/posture pressure causal continua ausente; Simply armor sunder não fecha o gap.
- `P-A0029-01`: heavy receipt inequívoco continua ausente; Simply effects não são proxy.
- `P-A0030-01`: guard-break causal attacker-side + heavy receipt continuam ausentes.
- `P-SIMPLY-A0001-50-01`: acceptance provider-present final deve provar classificação `DAGGER`/`HAMMER` das armas reais e ausência de double-root/double-dip.
- `P-SIMPLY-ALPHA-01`: efeitos específicos não comprovados da build Simply More 1.3.0 ALPHA continuam fail-closed.

## Resultado

**A0021–A0030: 10/10 reauditoradas; 4 contratos corrigidos no Notion; nenhuma nova perk necessária.** A integração correta é universal por família Epic Fight com ownership estrito dos efeitos Simply.