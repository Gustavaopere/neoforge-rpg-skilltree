# A0099 — Defesa Estacionária

## Estado

- **Chat 1:** DESIGN APROVADO / BRIDGE CONTRATUAL FECHADA COM DEPENDÊNCIA TÉCNICA TRANSVERSAL.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-81e9-97e9-fd9303618c3a`; corrigido e re-fetch confirmado.
- **Domínio:** VITALITY ↔ MARTIAL; Camada 2; Função Ponte.
- **Ranks:** 3; custo 1 PP/rank.
- **Dependências:** A0089 Couro Endurecido ≥2 + Gateway VITALITY + acesso real ao corredor MARTIAL.

## Contrato canônico

- Após o `StationaryStateService` confirmar **30 ticks consecutivos** de estado estacionário canônico, A0099 concede +4% de redução de dano hostil elegível por rank, máximo +12%, enquanto o estado permanecer válido.
- A0099 não mede posição por conta própria. Reutiliza exatamente o mesmo detector canônico de A0079.
- O detector usa comprimento total do caminho 3D server-side ≤0,10 bloco durante a janela; exceder 0,10 reinicia preparação.
- Teleporte, troca de dimensão, transição mount/vehicle ou deslocamento forçado identificado invalidam imediatamente.

## Dependência técnica transversal

- **P-A0079-02** é fechada estruturalmente para A0099 pela reutilização do único `StationaryStateService` e pelo boundary compartilhado de invalidação de movimento forçado.
- A0099 não possui detector paralelo, threshold alternativo ou compensação local.
- A prova efetiva de todos os contextos de forced movement permanece obrigatória no Chat 3.

## Provider / authority

- Minecraft/NeoForge: posição server-side e lifecycle do jogador.
- RPG Skill Tree / `StationaryStateService`: authority exclusiva do estado estacionário compartilhado.
- Epic Fight 21.17.3.1: apenas contexto de combate quando aplicável; não é authority de posição.
- MARTIAL é corredor semântico/topológico, não provider de movimento.

## Bridge PP / confluências

- `PP_REGION: VITALITY_MARTIAL_BRIDGE`.
- Por padrão, pontos A0099 não contam para threshold puro de 100 PP de VITALITY nem MARTIAL.
- Specialist pode whitelistar para no máximo **um** threshold, nunca ambos.
- A0099 não cobra, persiste nem reembolsa custo de confluência/classe. Stage 04.02 / `ProgressionService` é authority exclusiva da provenance de bridges pagas.
- Acesso geométrico não substitui fundamentos, investimento semântico nem terminal.

## Dano elegível / causalidade

- O bônus só compõe no pipeline defensivo canônico sobre dano hostil elegível efetivamente recebido.
- Ambiente, self-damage, custos de recurso e aliados não ganham classificação hostil por inferência.
- A0099 não cria tag de dano própria nem transforma redução condicional em resistência universal.
- Cada evento causal aplica A0099 no máximo uma vez.

## Evidência após Chat 2

- `StationaryStateService` continua a única authority para A0079+A0099, com `REQUIRED_TICKS=30` e `MAX_PATH_LENGTH=0.10`.
- O owner defensivo consulta `A0061A0080RuntimeState.stationary().isStationary(actor)`; não foi criado detector paralelo para A0099.
- O boundary compartilhado propaga invalidações conhecidas de forced movement para `StationaryStateService.invalidate(actor)` e usa o mesmo `actorId(player)` do state owner.
- O classifier de dano hostil elegível é compartilhado com A0096/A0097, sem requisito `Enemy`-only.
- A bridge PP permanece no mecanismo de progressão já existente; não há segundo ledger Stage 04.02.
- O Chat 2 **não executou** unit tests, GameTests de forced movement/lifecycle, build NeoForge, dedicated-server smoke ou CI.

## Dedup / lifecycle / anti-abuso

- Um único `StationaryStateService` por actor/servidor para A0079+A0099.
- Logout, morte, respawn, troca de dimensão e server stop limpam/invalidate state conforme lifecycle canônico.
- Forced movement não pode manter preparação por falta de amostragem quando houver receipt conhecido.
- Múltiplos callbacks do mesmo dano não podem multiplicar o bônus.

## Testes obrigatórios Chat 3

1. 30 ticks dentro de path 3D ≤0,10 ativam; 29 não ativam;
2. ultrapassar 0,10 reinicia preparação;
3. teleporte, dimensão, mount/vehicle transition e forced movement invalidam imediatamente;
4. A0079 e A0099 compartilham exatamente o mesmo detector/state;
5. dano hostil elegível recebe 4/8/12%; ambiente/self/ally/resource cost não recebe;
6. callbacks duplicados não aplicam duas vezes;
7. purchase exige A0089≥2 + gateways/corredor;
8. bridge PP não conta simultaneamente para VITALITY e MARTIAL;
9. Stage04.02 provenance/cobrança/refund permanece inalterado;
10. GameTests/dedicated-server cobrindo lifecycle e forced movement.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0089≥2 + VITALITY/MARTIAL semantic access. |
| Integração global | PASS condicionado | Reutiliza A0079; validação efetiva de forced movement fica para Chat 3. |
| Qualidade/identidade | PASS | Defesa plantada distinta de knockback/stun/armor. |
| Topologia | PASS | Bridge VITALITY↔MARTIAL. |
| Especializações | PASS | PP policy sem double threshold. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Dependência transversal explicitada e persistida. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | State owner interno único; Epic Fight não é falsamente usado como posição. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [x] P-A0099-01 / P-A0079-02 fechado estruturalmente pelo Chat 2
- [x] Classifier hostil compartilhado implementado
- [x] Bridge PP preservada sem ledger paralelo
- [x] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/lifecycle/forced movement
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
