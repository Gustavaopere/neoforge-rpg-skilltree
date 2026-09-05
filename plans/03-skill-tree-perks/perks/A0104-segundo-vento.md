# A0104 — Segundo Vento

**Estado Chat 1:** DESIGN APROVADO.  
**Runtime atual:** scheduler/state consumer ainda deve ser implementado; availability fail-closed até binding completo.  
**Notion:** https://app.notion.com/p/3c569db9f0db81a3b4cccdd738981bbf

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY.
- Ramo: Sobrevivência em Baixa Vida.
- Camada: 4; função: Notable.
- Ranks: 1; custo 2 PP.
- Pré-requisitos: A0096 Último Fôlego = 3 ranks + Gateway VITALITY.

## Contrato congelado

Um dano **direto, hostil, elegível e confirmado** que faça a vida cruzar estritamente de `>25%` para `<25%` da vida máxima agenda cinco pulsos de cura de **2,4% da vida máxima** nos ticks `+20/+40/+60/+80/+100`, total potencial de 12% em 5 s.

Cada novo dano direto hostil elegível confirmado durante a janela cancela **exatamente o próximo pulso ainda não pago**. Pulsos cancelados são perdidos. Um root pode registrar no máximo um cancelamento. A recarga de **60 s** começa na ativação.

O crossing é estrito: exatamente 25% não satisfaz `>25` nem `<25`; ficar abaixo de 25% não rearma. Após cooldown, o jogador precisa voltar acima e cruzar novamente.

## Provider, hook e authority

NeoForge `21.1.248` + RPG Skill Tree. O crossing usa `LivingDamageEvent.Post`: exigir perda real de vida >0, reconstruir a vida imediatamente anterior como `healthAfter + healthLost` e testar `preRatio > 0.25 && postRatio < 0.25`.

A cura usa o pipeline canônico de healing received; não cria pipeline paralelo de sustain/vampirismo.

## Causalidade, deduplicação e anti-abuso

- atacante causal deve ser hostil pelo classificador compartilhado;
- dano ambiental, self, resource cost, `BLOOD_MAGIC_COST`, zero damage e mera permanência abaixo do limiar não ativam nem cancelam;
- um `DamageContainer`/root cancela no máximo um pulso;
- scheduler é server-side e usa `gameTime`, não tempo de cliente;
- cura não gera sustain/vampirismo recursivo nem ultrapassa max health;
- dano simultâneo/duplicado não pode consumir múltiplos pulsos pelo mesmo root.

## Lifecycle e fail-closed

Schedule, cancelamentos pendentes e cooldown precisam de storage canônico/reconciliação definida. Rank loss, respec e rules reload limpam estado incompatível. Logout/dimensão/reload devem preservar ou reconciliar conforme storage server-authoritative, sem duplicar pulsos.

Sem scheduler/state consumer completo, o node é indisponível/não comprável e nenhum PP pode ser gasto em rank sem efeito.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: ProgressionService governa aquisição; healing pipeline existente recebe os pulsos.
- Enshrouded/Volcanoes: hazards sem atacante hostil causal não disparam/cancelam.
- Black Arcana: `ARCANE_BACKLASH` e custos ficam fora.
- Nenhum projeto próprio fornece um segundo scheduler ou uma segunda definição de hostilidade.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS.
2. Integração global — PASS, Post confirmado + healing pipeline canônico.
3. Qualidade/identidade — PASS, recuperação parcelada vulnerável a novos hits.
4. Topologia — PASS, low-health VITALITY.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS com hazards/custos excluídos.

Causalidade, dedup, anti-abuso, fallback, authority, gate, lifecycle, cooldown e fail-closed estão explícitos.

## Pendências para Chat 2

- `P-A0104-01`: implementar crossing estrito em `LivingDamageEvent.Post` e scheduler de 5 pulsos por gameTime.
- `P-A0104-02`: implementar ledger bounded de cancelamentos, uma marca/root e consumo do próximo pulso pendente.
- `P-A0104-03`: integrar cura ao pipeline canônico e bloquear recursão/overheal.
- `P-A0104-04`: availability + persistência/reconciliação de cooldown/schedule em login/logout/dimensão/rank loss/respec/rules reload.

## Testes exigidos ao Chat 3

Crossing acima→abaixo, exatamente 25%, zero/cancelled/environmental/self/resource cost, cinco pulsos sem hits, cancelamentos múltiplos em roots distintos, duplicate-root dedup, logout/reload/respec, cooldown e rearme, multiplayer isolation, GameTests, build, JAR e dedicated-server smoke.

## Atualização de implementação — Chat 2 (2026-09-02)

**Estado:** `CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

- `P-A0104-01` e `P-A0104-02` foram implementadas em `A0101A0110DefenseState`/`A0101A0110DefenseRuntime`: crossing estrito no `Post`, cinco pulsos em gameTime e cancelamento bounded do próximo pulso, uma vez/root.
- `P-A0104-03` usa `ServerPlayer.heal`, limitado naturalmente à vida máxima e sem criar receipt ofensivo/sustain adicional.
- `P-A0104-04` foi fechada no escopo de implementação: o deadline do cooldown é persistido no attachment canônico do jogador, cujo schema foi migrado de v1 para v2; schedules/receipts ativos permanecem transitórios e são reconciliados/limpos em boundaries para impedir duplicação após reload.
- A0104 foi retirada de `UNAVAILABLE_NODE` somente depois da persistência anti-reset. O Chat 3 ainda deve validar restart real, logout/death/dimensão/respec/rules reload e o comportamento dos pulsos.
- Chat 2 não executou a bateria final, não declarou `IMPLEMENTAÇÃO CONFIRMADA` e não fez merge.
