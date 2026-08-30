# A0059 — Quebra de Ritmo

## Estado

- **Design:** APROVADO; fail-closed já estava correto no Notion.
- **Notion:** `3c569db9-f0db-8105-9107-d95706ba3486`.
- **Runtime:** NÃO CONFIRMADO; ativação permanece fail-closed por falta de heavy/finalizer receipt.

## Contrato canônico

- A0058 ≥1 + A0056 ≥2 + gateway `combat_fist`.
- Com ≥3 Sequência, heavy/finalizer inequivocamente reconhecido pode consumir 3 cargas.
- Rank 1/2: +25%/+40% pressão de guarda e +10%/+15% Impact naquele golpe.
- Penalidade −8% movimento por 2 s somente após quebra de guarda/postura realmente confirmada.
- Sem heavy/finalizer seguro, não ativa nem consome.
- Se heavy existir mas guard/posture não, pode manter apenas Impact quando semanticamente disponível.
- Se A0059/A0058/A0056/gateway for invalidado por rank loss, respec ou rules reload, qualquer reserva/estado próprio de ativação deve ser descartado; Sequência é reconciliada pelo owner A0058.

## Evidência runtime

`A0041A0060CombatPolicy.beforeFistHeavy(...)` implementa matemática/consumo, mas `A0041A0060EpicFightHooks` declara explicitamente A0059/A0060 fail-closed e não chama a policy porque não há receipt server-authoritative inequívoco de heavy/finalizer para a ação concreta. Também não há caminho de aplicação da redução de movimento após guard break.

## Pendências para Chat 2

- **P-A0059-01:** integrar heavy/finalizer receipt provider-native seguro; não usar dano, animação, Punchy ou timing heurístico.
- **P-A0059-02:** quando houver ativação, integrar guard-break receipt real e aplicar −8% movimento por 2 s com lifecycle seguro; sem guard break, omitir essa parcela.
- **P-A0059-03:** limpar qualquer reserva/estado próprio de A0059 em rank loss, respec ou rules reload que invalide a perk; Sequência deve seguir o lifecycle de A0058.
- Herdadas de A0055/A0058: gateway/Mastery FIST, lifecycle da Sequência e reset por heavy impact.

## Boundaries

`ARCANE_BACKLASH`, hazard, companion-owned attack e estados Shroud/Arcane não qualificam heavy/finalizer, guard break ou Impact FIST.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0058 ≥1 + A0056 ≥2 + `combat_fist`; sem heavy/finalizer receipt a ativação fica fail-closed e não consome Sequência. |
| 2. Integração global | **PASS** | Heavy/guard-break/Impact permanecem provider-native; não usa dano alto, Backlash, Shroud, hazard ou companion como heurística. |
| 3. Qualidade e identidade | **PASS** | Notable condicionado a Sequência + golpe especial, alterando decisão de timing e pressão de guarda; não é bônus plano. |
| 4. Ramificação, distância e topologia | **PASS no design** | Convergência A0058 + A0056 no ramo FIST é coerente; depende de `combat_fist` sem atalho. |
| 5. Especializações | **PASS** | Permanece MARTIAL/ARMAS_DE_PUNHO e não invade magia/tecnologia; guard pressure só existe quando provider real sustentar a semântica. |
| 6. PT-BR | **PASS** | Nome, efeito, bloqueios e requisitos em PT-BR; nomes de API/IDs restritos ao texto técnico. |
| 7. Notion completo | **PASS** | Fetch fresco sem drift; Gate/Hook/Fallback/Regra já expressam fail-closed correto e não exigiram mutação funcional. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/RPG/WoM quando pertinente e boundaries de Punchy, own-projects e Mobstein foram auditados; nenhum provider inexistente foi inventado. |

Os 18 critérios técnicos cumulativos passam **no design** porque a ausência de heavy/finalizer/guard-break seguro resulta em fail-closed explícito; implementação só será confirmada quando os receipts reais existirem e forem testados.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética necessária nesta reauditoria.
