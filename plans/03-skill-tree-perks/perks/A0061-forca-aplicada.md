# A0061 — Força Aplicada

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-817a-85a8-e60b99bf7f47`; fetch fresco em 2026-08-31 sem drift após a auditoria.
- **Runtime observado:** CÓDIGO PRESENTE para melee Epic Fight e projéteis físicos canônicos; confirmação de implementação pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL da árvore principal desbloqueado; sem classe obrigatória.
- 5 ranks, 1 ponto por rank.
- +2% de dano físico direto elegível por rank, máximo próprio de +10%.
- Aplica uma única contribuição no pipeline físico canônico do RPG Skill Tree.
- Melee e projétil físico só entram quando a fonte é classificada server-side e causalmente atribuída ao jogador.
- Não existe cap global oculto além do teto do próprio node.

## Provider / authority / boundary

- **Authority do dano/ação:** provider que produziu o root action físico; Epic Fight 21.17.3.1 quando a ação é Epic Fight, Minecraft/NeoForge para o fallback aplicável e Simply Swords/Simply More/addons apenas como owners da arma/efeito provider-native.
- **Consumer/modificador:** RPG Skill Tree, no resolvedor físico canônico.
- **Boundary:** evento server-authoritative de dano físico direto elegível; `A0061A0080CombatPolicy.beforePhysicalHit(...)` é a regra pura compartilhada.
- **Pipeline proibido:** A0061 não reemite Implicit, Runic Power, Awakening, Unique, socket/gem ou trait de Simply Swords e não cria segundo hit.

## Evidência runtime

`A0061A0080EpicFightHooks` aplica a contribuição geral no PRE do Epic Fight apenas para jogador real, alvo hostil e arma física classificada. `A0041A0060ProjectileEvents` reutiliza a mesma policy em hits de arco/besta causalmente correlacionados. Projéteis/efeitos derivados sem provenance segura ficam fora.

## Fallback e fail-closed

- `minecraft:generic.attack_damage` pode fornecer base de melee quando semanticamente correto, mas não é substituto universal para projéteis.
- Sem classificação segura de dano físico direto, contribuição A0061 = zero.
- Nunca converter ausência de adapter em bônus genérico separado.

## Anti-abuso, causalidade e deduplicação

- Uma ação causal recebe A0061 uma vez.
- Summons, companions, fake players, reflexão, dano periódico, ambiente, hazards e procs encadeados não herdam A0061 do jogador.
- Não há Mastery gerada por A0061.

## Pendências para Chat 2

- **P-A0061-01:** validar provider-present melee + projectile em GameTests/regressão transversal e provar que o mesmo root action não recebe a contribuição geral duas vezes quando bridges coexistem.
- **P-A0061-02:** manter Simply Swords provider-native; nenhum efeito nativo pode ser reexecutado pela árvore.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | Gateway MARTIAL é o gate único e coerente. |
| 2. Integração global | PASS | Usa apenas dano físico direto; não toca mana, stamina, temperatura, hazards ou recursos paralelos. |
| 3. Qualidade/identidade | PASS | Small/ranked foundation de dano, adequado à função de caminho. |
| 4. Topologia | PASS | Camada 1, região `MARTIAL/CORE_OFFENSE`. |
| 5. Especializações | PASS | Universal MARTIAL; não cria classe nem invade especialização. |
| 6. PT-BR | PASS | Nome e contrato player-facing em PT-BR. |
| 7. Notion completo | PASS | Campos pertinentes completos e fetch fresco confirmado. |
| 8. NeoVitae | PASS | Nenhuma referência ativa. |
| 9. Cobertura providers | PASS | Epic Fight/Minecraft/Simply Swords e projeto próprio foram cruzados; rotas não comprovadas falham fechado. |

Os 18 critérios técnicos cumulativos passam **no design**. Implementação só pode ser declarada confirmada após os testes e fechamento do Chat 2.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- O pipeline melee Epic Fight + projectile físico já presente foi preservado; nenhum segundo pipeline foi criado e Simply Swords/Simply More continuam provider-native.
- A frase histórica acima sobre “confirmação” não reflete a divisão operacional atual: `IMPLEMENTAÇÃO CONFIRMADA` é exclusiva do Chat 3.

### Checklist de implementação

- [x] Design aprovado pelo Chat 1
- [x] Hook/consumer canônico presente
- [x] Gate/dependências preservados
- [x] Provider-native preservado
- [x] Fallback/fail-closed preservado
- [x] Seam de deduplicação/root presente no pipeline existente
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** uma contribuição A0061 por root, melee + projectile
- [ ] **VALIDAÇÃO CHAT 3:** Simply Swords sem reemissão de efeitos nativos
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests/integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA
