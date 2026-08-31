# Auditoria consolidada — delta Simply Swords — A0001–A0050

## Estado

**Reauditoria de design concluída: 50/50 perks.**

Este ciclo especial foi executado em cinco sublotes exatos de 10, mantendo uma única branch/PR e um único merge final conforme instrução do usuário:

1. A0001–A0010;
2. A0011–A0020;
3. A0021–A0030;
4. A0031–A0040;
5. A0041–A0050.

Nenhuma perk A0051+ faz parte deste ciclo.

## Stack auditado

- Simply Swords `1.70.2`;
- Simply More `1.3.0 ALPHA`;
- Integrated Simply Swords `1.4.0`;
- Simply Swords: Cataclysm `1.0.2`;
- Simply Tooltips `0.1.5`;
- Epic Fight Compat `1.1.0` como adapter de classificação/moveset.

## Regra canônica resultante

Não existem “perks de Simply Swords”. O stack novo amplia o conjunto de armas que pode cair em famílias MARTIAL existentes, mas somente quando Epic Fight/Epic Fight Compat expõe uma classificação server-side inequívoca.

A árvore consome a família/root MARTIAL. Simply Swords/Simply More/Cataclysm continuam authority de:

- Implicits;
- Runic Powers;
- Awakening;
- Unique abilities;
- sockets/gem powers;
- traits Cataclysm;
- efeitos derivados/ability hits do próprio provider.

O RPG Skill Tree não rerrola, reaplica, escala novamente, dispara nem converte esses efeitos. Um proc/ability/derived hit não vira novo `rootActionId` MARTIAL apenas porque o jogador é owner da arma.

Simply Tooltips é estritamente apresentação e **NÃO DEVE SER INTEGRADO** como authority mecânica.

## Cobertura por família

| Intervalo | Família | Resultado para o stack Simply |
|---|---|---|
| A0001–A0006 | SWORD | Cobertura universal se Epic Fight Compat resolver SWORD. |
| A0007–A0012 | AXE | Cobertura universal se resolver AXE. |
| A0013–A0018 | SPEAR | Cobertura universal se resolver SPEAR; não inferir pelo tipo Simply. |
| A0019–A0024 | DAGGER | Cobertura universal se resolver DAGGER; backstab permanece provider-owned. |
| A0025–A0030 | HAMMER | Cobertura universal se resolver HAMMER; armor sunder não é guard/heavy receipt. |
| A0031–A0036 | MACE | Armas externas/Simply More apenas por MACE seguro/mapping versionado. |
| A0037–A0042 | SCYTHE | Cobertura universal se resolver SCYTHE; execute permanece provider-owned. |
| A0043–A0048 | BOW | **NÃO DEVE SER INTEGRADO** — stack novo não fornece família BOW. |
| A0049–A0050 | CROSSBOW | **NÃO DEVE SER INTEGRADO** — stack novo não fornece família CROSSBOW. |

## Notion

- Perks auditadas: **50/50**.
- Páginas que exigiram mutação semântica: **19/50**.
- Re-fetch pós-escrita: **19/19 PASS**.

### Mutadas

- A0001–A0010: 10/10;
- A0023;
- A0028;
- A0029;
- A0030;
- A0035;
- A0036;
- A0040;
- A0041;
- A0042.

### Sem mutação funcional

A0011–A0022, A0024–A0027, A0031–A0034, A0037–A0039 e A0043–A0050 não precisaram de alteração de efeito/gate. Seus contratos provider-native/direct-root ou sua ausência de relação com o stack já eram suficientes.

## Overlaps que exigiram boundary explícito

### Backstab × A0023

O backstab Implicit de Dagger/Sai pode coexistir no mesmo root direto com Ataque ao Ponto Cego, mas cada sistema aplica sua parcela uma vez. A0023 não rerrola/reaplica o Implicit e derived hits não consomem Fluxo novamente.

### Armor sunder × A0028/A0029/A0030

Armor sunder/ignore do stack Simply não é:

- Abalo;
- guard/posture pressure;
- heavy receipt;
- guard-break receipt;
- stamina-cost receipt.

Portanto não fecha `P-A0028-01`, `P-A0029-01` ou `P-A0030-01`.

### MACE/Simply More × A0035/A0036

Pernach/arma Simply More só entra com classificação MACE segura. Debuff provider-owned não conta como Trauma nem Armadura Fendida RPG, não satisfaz Sundered preexistente e não é heavy receipt. O artifact alpha permanece fail-closed para comportamento específico não comprovado.

### Execute de Scythe × A0040/A0041/A0042

O execute Implicit permanece provider-owned:

- não aplica/duplica Marca da Ceifa;
- não cria segundo Corte de Ceifa;
- não cria automaticamente `eligible_kill`.

Se o provider provar que a morte por execute pertence ao mesmo root direto SCYTHE, esse único abate pode ser submetido uma vez ao serviço anti-abuso de A0042. Sem correlação inequívoca, fail-closed.

## Pendências/handoffs

### P-SIMPLY-A0001-50-01 — acceptance provider-present

Chat 2 deve validar com os JARs exatos instalados que as armas relevantes recebem as famílias esperadas via Epic Fight/Epic Fight Compat e que o pipeline não produz double-root/double-dip. Cobrir pelo menos SWORD, AXE, SPEAR, DAGGER, HAMMER, MACE e SCYTHE quando houver mapping real.

### P-SIMPLY-A0006-01 — Deflect não é defesa técnica automática

Deflect de Claymore/Longsword permanece fail-closed como gatilho de A0004/A0006 até existir receipt causal público, versionado e server-authoritative. Não inferir aparo/guarda perfeita por redução de dano, tooltip ou animação.

### P-SIMPLY-ALPHA-01 — Simply More 1.3.0 ALPHA

Tipo/Unique/Implicit específico não comprovado no artifact instalado não pode ser presumido por documentação de outra versão ou pelo nome do item. Família/effect desconhecido = fail-closed.

As pendências técnicas históricas de A0001–A0050 continuam válidas. A chegada do stack Simply não é justificativa para considerar resolvidos heavy, guard-pressure, guard-break, lifecycle, Mastery ou availability gaps já catalogados.

## Arquivos por sublote

- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0001-A0010.md`;
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0011-A0020.md`;
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0021-A0030.md`;
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0031-A0040.md`;
- `AUDITORIA-DELTA-SIMPLY-SWORDS-A0041-A0050.md`.

## Resultado final de design

**Nenhuma nova perk é necessária por causa desses cinco mods.** As perks existentes A0001–A0042 devem cobrir armas Simply quando a família Epic Fight correspondente for realmente resolvida, preservando ownership dos efeitos do provider. A0043–A0050 não devem integrar o stack Simply. O único trabalho futuro decorrente desta reauditoria é acceptance/compatibilidade e eventuais adapters causais comprovados; não redesenho de árvore.