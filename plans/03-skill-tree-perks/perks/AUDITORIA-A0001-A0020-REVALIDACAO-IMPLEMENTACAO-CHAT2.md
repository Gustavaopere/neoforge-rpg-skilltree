# Chat 2 — Revalidação de implementação A0001–A0020 após retroauditoria provider→árvore

## Escopo

- **Lote 1:** A0001–A0010 — 10 perks consecutivas.
- **Lote 2:** A0011–A0020 — 10 perks consecutivas.
- **Total revalidado:** 20 perks.
- **PR de implementação/revalidação:** #234 — `test(perks): revalidate A0001-A0020 provider boundaries`.
- **Base de design atualizada:** `main@0087ef7e513664454b3d54cb70a9c3f24ec46e84`, após as regras provider→árvore das PRs #229/#233.
- **Regra:** nenhum redesign neste Chat 2; somente implementação, correção técnica, regressões, documentação e merge do contrato já aprovado pelo Chat 1.

## Resultado por perk

| Perk | Estado da revalidação Chat 2 | Provider/fallback relevante | Pendência |
|---|---|---|---|
| A0001 | VALIDADA | Epic Fight sword provider-native; família desconhecida fail-closed | nenhuma |
| A0002 | VALIDADA | `ModifyAttackSpeedEvent`; sem conversão para stamina/movimento/dano | nenhuma |
| A0003 | VALIDADA | crítico canônico único; provenance direta | nenhuma |
| A0004 | VALIDADA | Ímpeto somente por ação direta/receipts técnicos reconhecidos | nenhuma |
| A0005 | VALIDADA | guarda/postura nativa; Armor apenas penetração-only quando hook indisponível | nenhuma |
| A0006 | VALIDADA | `ON_DODGE` como defesa técnica comprovada; demais defesas não comprovadas fail-closed | nenhuma bloqueante |
| A0007 | VALIDADA | Epic Fight axe provider-native; família desconhecida fail-closed | nenhuma |
| A0008 | VALIDADA | `ModifyAttackSpeedEvent` | nenhuma |
| A0009 | VALIDADA | crítico canônico único; provenance direta | nenhuma |
| A0010 | VALIDADA | Fúria somente em dano efetivo direto hostil do jogador | nenhuma |
| A0011 | VALIDADA | guarda/postura física; fallback Armor penetração-only | nenhuma |
| A0012 | VALIDADA | Cold Sweat `CORE` 2.4.2 exato + Epic Fight 21.17.3.1 exato | nenhuma bloqueante |
| A0013 | VALIDADA | SPEAR provider-native | nenhuma |
| A0014 | VALIDADA | `ModifyAttackSpeedEvent` SPEAR | nenhuma |
| A0015 | VALIDADA | crítico canônico único SPEAR | nenhuma |
| A0016 | VALIDADA | alcance/crossing server-side, receipts Epic Fight | nenhuma |
| A0017 | VALIDADA NO FALLBACK CANÔNICO | janela + impacto/pressão; deslocamento ofensivo omitido | P-A0017-01 não bloqueante / fail-closed correto |
| A0018 | VALIDADA | crossing + janela + lockout por alvo | nenhuma |
| A0019 | VALIDADA | DAGGER provider-native | nenhuma |
| A0020 | VALIDADA | `ModifyAttackSpeedEvent` DAGGER | nenhuma |

## Correções técnicas introduzidas pela revalidação

### 1. Cold Sweat — versão exata para A0012

O bridge de Frenesi deixa de aceitar versão por prefixo e passa a aceitar exclusivamente `2.4.2`.

Rejeitados explicitamente nos testes:

- `2.4.20`;
- `2.4.2.1`;
- `2.4.2-beta`;
- versões anteriores/diferentes;
- `null`.

A ausência/incompatibilidade do provider mantém A0012 fail-closed.

### 2. Cold Sweat — diagnóstico bounded

`ColdSweatFrenzyBridge` passa a emitir diagnóstico one-shot para:

- versão incompatível;
- falha de resolução da API `Temperature.Trait.CORE`;
- falha de invocação da escrita CORE.

O diagnóstico não transforma erro em fallback permissivo e não cria spam. A falha continua desativando a parcela dependente de A0012.

### 3. Epic Fight — versão exata

Foi criado `EpicFightVersionContract` com suporte exclusivo a `21.17.3.1`.

O bootstrap agora valida a versão **antes** de registrar:

- `EpicFightProgressionHooks`;
- `A0001A0020EpicFightHooks`;
- hooks cumulativos Epic Fight usados pelo runtime.

Portanto Mastery/progression também não fica ativa silenciosamente em uma versão não auditada como `21.17.3.10`.

### 4. Provenance marcial — defesa em profundidade no policy

`A0001A0020CombatPolicy.beforeHit` agora retorna pacote completamente neutro quando:

- `direct == false`; ou
- `hostile == false`.

Isso inclui `criticalChanceBonus = 0` e impede que um futuro adapter mal configurado deixe companions, summons, `ARCANE_BACKLASH`, hazards ou alvo não hostil atravessarem parte do pacote marcial mesmo que o adapter externo falhe em filtrar.

O adapter Epic Fight continua mantendo sua primeira camada de prova causal: `ServerPlayer`, entidade direta igual ao jogador, alvo hostil e família provider-native.

## Boundaries provider→árvore revalidados

### Black Arcana

- `ARCANE_BACKLASH` é dano terminal, não ação marcial direta.
- Não recebe crítico de A0003/A0009/A0015.
- Não gera Ímpeto, Fúria ou Controle de Distância.
- Não abre/consome Riposta ou Janelas de Interceptação.
- Arcane Danger/Arcane Resistance/Arcane Strain não são guarda/postura física.

### Mobstein 5.4.4

- Ally/bodyguard/ressuscitado permanece owner de sua própria ação.
- Dano do companion não herda autoria marcial do jogador proprietário.
- Ataque direto do próprio jogador contra mobs/bosses Mobstein continua elegível normalmente.

### Enshrouded

- Shroud, Exposure, Madness, Flame e Story não viram defesa física, CORE, crítico ou receipt MARTIAL.

### Volcanoes

- Atmosphere, gases, pressão e prospecção não viram defesa/receipt MARTIAL.
- A0012 não chama Volcanoes nem reaplica/deduz temperatura ambiental.
- Quando Volcanoes compõe temperatura pelo bridge ambiental, a authority corporal continua sendo Cold Sweat; A0012 grava apenas seu próprio +1,5 CORE causal.

## A0017 — fail-closed preservado

P-A0017-01 permanece aberta de forma legítima:

- `deltaMovement` pode ser usado somente para detectar aproximação geométrica e abrir a janela;
- não existe receipt auditado de Epic Fight 21.17.3.1 que identifique a mesma ação do alvo como movimento ofensivo próprio e forneça ponto seguro para reduzir apenas esse deslocamento;
- portanto a redução de 20%/30% permanece omitida;
- janela, consumo de Controle de Distância e impacto/pressão 20%/35% permanecem o fallback canônico implementado.

Nenhum provider novo da retroauditoria fornece substituto legítimo para esse receipt.

## Evidência de testes — ciclo TDD

### Cold Sweat version gate

RED comprovou aceitação incorreta de versões derivadas; GREEN após igualdade exata `2.4.2`.

### Epic Fight version gate

A primeira tentativa de teste acoplada ao hook foi descartada como RED inválido por `NoClassDefFoundError`. A política foi isolada em `EpicFightVersionContract`; o RED válido então comprovou ausência do contrato e o GREEN confirmou a versão exata `21.17.3.1`.

### Diagnóstico bounded

RED: `shouldEmitDiagnostic` inexistente. GREEN: primeira ocorrência por chave aceita, repetição rejeitada, chave distinta aceita.

### Provenance

RED no commit `fd28ac025b3eb26236715197050065e1e5fb4aa1`:

- 105 testes;
- 2 falhas;
- ambas exclusivamente nos novos testes de provenance;
- hit indireto e alvo não hostil ainda recebiam `criticalChanceBonus = 0.09`.

GREEN no commit `17c216457546e9f6849dc50144d8ee364893331c` após tornar o pacote inválido completamente neutro.

## CI de implementação antes do fechamento documental

`RPG Skill Tree CI #2130` — **SUCCESS** no commit `17c216457546e9f6849dc50144d8ee364893331c`.

Passaram explicitamente:

- Core tests;
- JUnit 5;
- NeoForge GameTests;
- testes de provenance/compendium;
- validações de dados/runtime/providers;
- NeoForge build;
- verificação do JAR;
- dedicated-server smoke.

Os 9 workflows auxiliares também concluíram com `success`, totalizando **10/10 workflows verdes** naquele código. O HEAD documental posterior deve repetir o gate completo antes do merge.

## Estado para fechamento da PR #234

- Design: **APROVADO pelo Chat 1**.
- Código: **PRESENTE e revalidado**.
- Implementação A0001–A0020: **VALIDADA EM CI**.
- P-A0017-01: **não bloqueante / fail-closed correto**.
- Ponto que precise retornar ao Chat 1: **nenhum**.
- Próximo lote A0021+: **fora de escopo e não iniciado**.

A confirmação definitiva de implementação desta revalidação é dada pelo merge da PR #234 e pela confirmação da `main` pós-merge.
