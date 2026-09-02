# A0105 — Casca Reativa

**Estado Chat 1:** DESIGN APROVADO.  
**Runtime atual:** state/attribute lifecycle ainda deve ser implementado; availability fail-closed até binding completo.  
**Notion:** https://app.notion.com/p/3c569db9f0db817794c5f243b0de0e26

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY.
- Ramo: Anti-Pico de Dano.
- Camada: 4; função: Notable.
- Ranks: 1; custo 2 PP.
- Pré-requisitos: A0089 Couro Endurecido ≥3 + A0090 Têmpera ≥2 + Gateway VITALITY.

## Contrato congelado

Após **3 eventos de dano direto hostil elegível confirmados** dentro de uma janela deslizante de **4 s / 80 ticks**, ativa Casca Reativa por **6 s / 120 ticks**. Durante a janela ativa:

- +15% relativo sobre Armor existente;
- +8% relativo sobre Armor Toughness existente.

A semântica relativa deve ser a mesma já usada por A0089/A0090. Base Armor/Toughness zero continua zero. O terceiro hit apenas ativa a perk após seu `Post`; ele **não recebe retroativamente** os modifiers.

A recarga de **20 s** começa na ativação. Novos hits durante os 6 s não renovam duração nem cooldown.

## Provider, hook e authority

NeoForge `21.1.248` + RPG Skill Tree. Contagem em `LivingDamageEvent.Post` com perda real de vida >0 e atacante causal hostil pelo classificador compartilhado. Modifiers temporários usam o runtime canônico de atributos com IDs estáveis.

Epic Fight só fornece fontes de combate que chegam ao mesmo pipeline causal; não é owner dos modifiers Armor/Toughness.

## Causalidade, deduplicação e anti-abuso

- manter somente receipts/timestamps necessários dos últimos 80 ticks;
- um `DamageContainer` conta uma vez;
- se provider expuser `rootActionId` que agrupe múltiplas sequências em uma ação, deduplicar por esse root;
- sem root comprovado, **não** inventar dedup por mesmo tick/atacante/animação;
- periodic/environmental/self/resource costs e dano zero não contam;
- a ativação ocorre após confirmação do terceiro evento, sem benefício retroativo.

## Lifecycle e fail-closed

Aplicar/remover os dois modifiers de forma idempotente e reconciliável. Limpar/reconciliar estado em morte, logout, mudança de dimensão quando aplicável, rank loss, respec, rules reload e shutdown. Reentrada não pode duplicar modifier UUID/ID.

Sem consumer/state lifecycle completo, o node fica indisponível/não comprável e purchase deve falhar antes do gasto.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: runtime de atributos e ProgressionService são authorities; nenhum segundo ledger de atributo.
- Enshrouded/Volcanoes: hazards sem atacante hostil causal não contam.
- Black Arcana: Backlash/resource costs não contam.
- Nenhum projeto próprio fornece substitute de Armor/Toughness para esta perk.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS.
2. Integração global — PASS, Post + attribute runtime canônico.
3. Qualidade/identidade — PASS, resposta a pressão repetida com atraso causal.
4. Topologia — PASS, Anti-Pico VITALITY.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS.

Authority, causalidade, dedup, anti-abuso, cooldown, lifecycle, fallback e fail-closed estão definidos.

## Pendências para Chat 2

- `P-A0105-01`: implementar janela bounded de 3 hits/80 ticks em `LivingDamageEvent.Post`.
- `P-A0105-02`: aplicar/remover os modifiers temporários relativos com IDs estáveis, sem benefício retroativo ao terceiro hit.
- `P-A0105-03`: implementar cooldown/duração e lifecycle completo sem refresh por hits adicionais.
- `P-A0105-04`: availability/purchase fail-closed enquanto state + attribute consumer não estiverem completos.

## Testes exigidos ao Chat 3

Dois hits não ativam; terceiro em ≤80 ticks ativa após o hit; terceiro fora da janela não ativa; zero/environmental/self/resource não contam; root dedup; duração 120; cooldown 400; no refresh; zero-base; modifier uniqueness; morte/logout/dimensão/rank loss/respec/reload; multiplayer; GameTests, build, JAR e dedicated-server smoke.

## Atualização de implementação — Chat 2 (2026-09-02)

**Estado:** `CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

- `P-A0105-01` foi implementada com ledger bounded de receipts diretos hostis e janela de 80 ticks; o terceiro root distinto ativa somente após o `Post` confirmado.
- `P-A0105-02` foi implementada com IDs estáveis de modifiers transitórios e operações relativas: +15% Armor e +8% Armor Toughness; a ativação não retroage ao terceiro hit.
- `P-A0105-03` e `P-A0105-04` foram fechadas no runtime: duração 120 ticks, cooldown 400 ticks, sem refresh durante a janela e deadline persistido no attachment canônico v2. Active state/receipts são reconciliados em boundaries e modifiers são removidos idempotentemente.
- O Chat 3 ainda deve validar zero-base, uniqueness, restart/logout/death/dimensão/respec/reload e ausência de refresh/duplicação.
- Chat 2 não executou a bateria final, não declarou `IMPLEMENTAÇÃO CONFIRMADA` e não fez merge.
