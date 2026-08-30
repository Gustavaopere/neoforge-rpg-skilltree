# A0056 — Treino com Armas de Punho II

## Estado

- **Design:** APROVADO; sem mutação funcional no Notion nesta reauditoria.
- **Notion:** `3c569db9-f0db-81c2-a2ce-fe2a2fa8714c`.
- **Runtime:** caminho de attack speed presente; aquisição depende do fechamento de A0055.

## Contrato canônico

- A0055 ≥2 + gateway `combat_fist`.
- +2% de ritmo efetivo FIST por rank, máximo +6%.
- Usar apenas `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`ModifyAttackSpeedEvent` quando o moveset FIST/knuckle realmente consumir o valor server-authoritative.
- Sem contrato de cadência seguro, omitir o componente; não converter para Stamina, movimento, dano ou alteração direta de animação.
- Se A0055/gateway for invalidado por rank loss/respec/rules reload, A0056 deixa de ser elegível imediatamente; nenhum estado descendente pode persistir por bypass.

## Evidência runtime

`A0041A0060EpicFightHooks.onAttackSpeed(...)` aplica o bônus somente quando a capability Epic Fight é FIST/knuckle. O efeito está alinhado ao contrato. A árvore, porém, não pode ser considerada plenamente adquirível enquanto `combat:fist`/`combat_fist` de A0055 estiverem desalinhados no producer/architecture.

## Pendências para Chat 2

- Herdadas de A0055: `P-A0055-01` e `P-A0055-02`.
- Revalidar `ModifyAttackSpeedEvent` no moveset FIST real e dedicated-server/provider-present.
- Confirmar que purchase/rank reconciliation remove elegibilidade de A0056 quando A0055/gateway deixar de ser válido.

## Provider→árvore

Volcanoes, Enshrouded, Black Arcana, Mobstein e Punchy não fornecem cadência FIST.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0055 ≥2 + `combat_fist`; toda indisponibilidade de A0055 é herdada e não há rota alternativa. |
| 2. Integração global | **PASS** | Usa attack-speed real do Epic Fight; não converte para Stamina, movimento, dano, hunger ou outro recurso. |
| 3. Qualidade e identidade | **PASS** | Ranked training incremental coerente com função de caminho; bônus pequeno não é rotulado como Notable/Capstone. |
| 4. Ramificação, distância e topologia | **PASS no design** | Segue diretamente A0055 no ramo FIST; topology runtime depende da publicação de `combat_fist`, já catalogada. |
| 5. Especializações | **PASS** | Mantém-se subdisciplina MARTIAL/ARMAS_DE_PUNHO, sem criar classe automática de provider. |
| 6. PT-BR | **PASS** | Nome, efeito e requisitos em PT-BR; API/IDs técnicos permanecem em inglês. |
| 7. Notion completo | **PASS** | Fetch fresco sem drift e campos pertinentes completos; nenhuma escrita adicional foi necessária. |
| 8. NeoVitae | **PASS** | Nenhuma referência residual. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight é provider de cadence; own-projects, Mobstein e Punchy foram avaliados e corretamente não promovidos a provider dessa mecânica. |

Os 18 critérios técnicos cumulativos passam **no design**; a implementação depende apenas do fechamento estrutural de A0055 e da prova provider-present do moveset real.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.
