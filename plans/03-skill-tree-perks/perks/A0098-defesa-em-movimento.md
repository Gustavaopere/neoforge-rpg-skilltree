# A0098 — Defesa em Movimento

## Estado

- **Chat 1:** DESIGN APROVADO / BRIDGE CONTRATUAL FECHADA.
- **Notion:** `3c569db9-f0db-81d1-9530-c1c2aa50e07a`; corrigido e re-fetch confirmado.
- **Domínio:** VITALITY ↔ AGILITY; Camada 2; Função Ponte.
- **Ranks:** 3; custo 1 PP/rank.
- **Dependências:** A0088 Constituição ≥2 + Gateway VITALITY + acesso real ao corredor AGILITY.

## Contrato canônico

- Enquanto o jogador estiver em **locomoção autopropelida server-authoritative** elegível, recebe +3% de redução de dano hostil elegível por rank, até +9%.
- Fallback seguro atual: `ServerPlayer.isSprinting()` para sprint vanilla.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0 só ampliam cobertura quando adapter real/versionado expuser estado mecânico server-side de locomoção autopropelida.
- Animação, câmera e velocidade isolada não bastam.

## Exclusões obrigatórias

- Knockback/repulsão, queda, mount/vehicle, Create/Sable contraption, belt/esteira, grappling hook e deslocamento externo/forçado não ativam por simples delta de posição.
- Se um desses sistemas coexistir com `isSprinting=true`, o runtime deve respeitar o classifier canônico de movimento autopropelido e impedir falso positivo quando houver prova de forced/passive movement.
- A ausência de receipt ParCool mantém somente a cobertura vanilla; não inferir parkour por animação.

## Provider / authority

- Minecraft/NeoForge: sprint vanilla server-side.
- ParCool/Epic ParCool: owners de ações próprias apenas quando um adapter seguro existir.
- RPG Skill Tree: consumer defensivo e authority da política de bridge PP.
- Epic Fight pode fornecer contexto de combate, não authority de posição por si só.

## Bridge PP / confluências

- `PP_REGION: VITALITY_AGILITY_BRIDGE`.
- Por padrão, pontos A0098 não contam para threshold puro de 100 PP de VITALITY nem AGILITY.
- Specialist pode whitelistá-los para **no máximo um** threshold, nunca ambos.
- O node A0098 não cobra, persiste nem reembolsa custo de confluência/classe. Stage 04.02 / `ProgressionService` é authority exclusiva da provenance de bridges pagas.
- Geometria não habilita border hopping nem substitui fundamentos/terminais.

## Evidência atual e pendências Chat 2

- `A0081A0100CombatPolicy.movingDefenseMultiplier` aceita booleano `authoritativeSelfPropelledSprint`.
- `A0081A0100CombatEvents` atualmente passa diretamente `player.isSprinting()`; isso cobre o fallback vanilla, mas não prova exclusão transversal de todos os forced/passive movement contexts.
- **P-A0098-01:** consolidar/reutilizar classifier de movimento autopropelido e provar exclusões de mount/vehicle/contraption/belt/knockback/grappling quando pertinentes.
- **P-A0098-02:** ParCool/Epic ParCool permanecem fail-closed sem receipt real; não criar heurística.
- **P-A0098-03:** implementar/validar política de bridge PP sem interferir no ledger de confluência Stage 04.02.

## Dedup / lifecycle

- A0098 é stateless no dano: uma verificação do estado canônico por evento recebido.
- Não acumular múltiplos adapters de movimento; providers convergem para um boolean/classifier canônico.
- Nenhum Mastery ou recurso produzido.

## Testes obrigatórios Chat 3

1. sprint vanilla server-side ativa 3/6/9%; walking não ativa;
2. knockback/queda/mount/vehicle/contraption/belt/grappling não ativam por deslocamento externo;
3. provider ParCool ausente: fallback vanilla continua e nada é inferido;
4. provider ParCool fixture sem receipt seguro: fail-closed para ações extras;
5. adapter futuro explícito não duplica o bônus com sprint do mesmo estado causal;
6. bridge PP não conta para ambos os thresholds;
7. purchase exige A0088≥2 + gateways/corredor;
8. Stage04.02 provenance/cobrança/refund permanece inalterado;
9. dedicated-server/GameTest movement contexts.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0088≥2 + VITALITY/AGILITY semantic access. |
| Integração global | PASS | Distingue autopropulsão de veículos/physics. |
| Qualidade/identidade | PASS | Defesa móvel condicionada. |
| Topologia | PASS | Bridge real VITALITY↔AGILITY. |
| Especializações | PASS | PP policy sem double threshold. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Bridge authority atualizada e persistida. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | vanilla seguro; ParCool fail-closed sem receipt. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0098-01 classifier/exclusões implementados pelo Chat 2
- [ ] P-A0098-02 provider extras fail-closed preservado
- [ ] P-A0098-03 bridge PP implementada/validada
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/provider contexts
- [ ] VALIDAÇÃO CHAT 3: build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
