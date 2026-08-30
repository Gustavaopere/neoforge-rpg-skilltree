# CHAT 3 — Auditoria das perks mergeadas — A0011–A0020

## Escopo

- **Lote:** A0011–A0020, exatamente 10 perks consecutivas.
- **Base inicial auditada:** `main@2e6cf57d5c12630d55280d1c4ff0177f536dce96`.
- **PR de correção:** #250 — `fix(perks): audit A0011-A0020 causal commits`.
- **Método:** leitura integral dos 10 dossiês antes da inspeção do runtime; comparação do contrato aprovado com o código mergeado; correção apenas de divergências técnicas comprovadas, sem redesenhar perks.

## Resultado por perk

| Perk | Resultado Chat 3 | Pendência | Estado |
|---|---|---|---|
| A0011 | Ruptura/fallback corretos, mas custo de 20 Fúria era irreversível no PRE. | `P-A0011-02` | RESOLVIDA na #250 |
| A0012 | Frenesi/Pico e boundary Cold Sweat permanecem conforme contrato; a transação PRE é explicitamente parte do design aprovado. | Nenhuma nova. | OK |
| A0013 | Classificação/bonus SPEAR permanecem provider-native/fail-closed. | Nenhuma nova. | OK |
| A0014 | Attack-speed SPEAR continua no provider previsto. | Nenhuma nova. | OK |
| A0015 | Crítico SPEAR permanece no resolver canônico. | Nenhuma nova. | OK |
| A0016 | Controle de Distância/gain-loss permanecem corretos; ordem com consumidores foi endurecida. | Nenhuma exclusiva. | OK |
| A0017 | Fallback janela + impacto correto, porém janela + 1 carga eram consumidas no PRE. | `P-A0017-02` resolvida; `P-A0017-01` continua fail-closed não bloqueante. | OK NO FALLBACK |
| A0018 | Crossing/bônus corretos, porém janela + 3 cargas + lockout eram consumidos no PRE. | `P-A0018-01` | RESOLVIDA na #250 |
| A0019 | Classificação DAGGER permanece provider-native/fail-closed. | Nenhuma nova. | OK |
| A0020 | Attack-speed DAGGER continua no provider previsto. | Nenhuma nova. | OK |

## P-A0011-02 — commit causal de Ruptura de Guarda

### Defeito

`A0001A0020CombatPolicy.beforeHit(...)` consumia 20 Fúria assim que o PRE qualificava A0011. Se o provider resolvesse o ataque sem dano efetivo, o gasto permanecia.

### Correção

- PRE reserva o custo por `rootActionId` e aplica somente os modifiers necessários ao cálculo.
- A reserva é transitória/bounded e reduz a Fúria **efetivamente disponível** para A0012 no mesmo PRE.
- POST `direct && hostile && actualDamage` commita exatamente 20 Fúria uma vez.
- POST inválido descarta a reserva sem gasto.
- Commit A0011 ocorre antes do ganho A0010 no mesmo hit.
- A0012 não foi redesenhada: Pico/custo corporal permanecem no PRE conforme o contrato aprovado.

## P-A0017-02 — commit causal de Interceptação

### Defeito

A0017 removia a janela e consumia 1 Controle de Distância no PRE. Um golpe cancelado/zerado podia perder a oportunidade e a carga.

### Correção

- PRE verifica e reserva janela + 1 carga por root action.
- POST confirmado commita janela + carga.
- POST inválido descarta a reserva; a janela continua disponível enquanto seu prazo original não expirou.
- Reserva por alvo impede uso simultâneo da mesma janela/carga.
- O custo é commitado antes do ganho A0016 no mesmo hit.

### Pendência preservada

`P-A0017-01` continua **ABERTA / NÃO BLOQUEANTE / FAIL-CLOSED CORRETO**: redução de deslocamento ofensivo só pode ser ligada quando existir receipt Epic Fight provider-native causal e seguro. Não usar `deltaMovement` genérico, velocidade vanilla ou heurística.

## P-A0018-01 — commit causal da Linha de Interceptação

### Defeito

A0018 removia a janela, consumia 3 cargas e iniciava o lockout de 8 s no PRE. Cancelamento/dano zero podia criar consumo e lockout fantasmas.

### Correção

- PRE reserva janela + 3 cargas por root action.
- POST confirmado remove a janela, consome 3 cargas e inicia o lockout.
- POST inválido descarta a reserva e não inicia lockout.
- Cargas reservadas não podem financiar outra root action/linha simultânea.
- Reserva expira de forma bounded e também deixa de valer quando a própria janela expira.
- Commit A0018 acontece antes do ganho A0016 no mesmo hit.
- Prioridade do capstone é preservada: A0018 elegível não pode cair para A0017 no mesmo PRE por falha de claim/reserva.

## TDD e evidência

### RED

- Commit: `64e4abd9eacc45caf7f4af67b4015be9d7ef4bf9`.
- `RPG Skill Tree CI` **#2256**.
- JUnit: **123 testes, exatamente 3 falhas**, correspondentes a A0011, A0017 e A0018.
- Core histórico anterior ao JUnit permaneceu verde.

### GREEN de código

- HEAD: `1698bdc518f84ae99da6a9f6da1a78ad5b9f3923`.
- `RPG Skill Tree CI` **#2269**: **SUCCESS**.
- Verificados: core, JUnit 5, NeoForge GameTests, Compendium, validações de dados/árvore/runtime/providers, NeoForge build, JAR e dedicated-server smoke.
- Nove workflows auxiliares do mesmo HEAD também ficaram verdes.

## Estado do lote antes do fechamento documental

- **Pendências acionáveis encontradas:** 3.
- **Pendências acionáveis corrigidas:** 3.
- **Pendência externa preservada:** `P-A0017-01`, não bloqueante e corretamente fail-closed.
- **Perks alteradas:** A0011, A0017, A0018.
- **Perks sem nova pendência acionável:** A0012–A0016, A0019–A0020.
- **Próximo passo:** reconciliar contra a `main` corrente, atualizar `STATUS.md`/índice sem apagar lotes concorrentes, CI final do HEAD documental, merge e confirmação da `main`.
- **Regra de ciclo:** após merge, PARAR; A0021+ não é iniciado por este Chat 3 sem novo comando do usuário.
