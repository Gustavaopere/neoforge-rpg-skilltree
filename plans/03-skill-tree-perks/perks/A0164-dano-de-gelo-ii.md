# A0164 — Dano de Gelo II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A fantasia de rotação ICE permanece válida, mas o contrato atual depende de um receipt causal de controle de gelo/CHILL com buildup mutável e de `DIRECT_MAGIC_OUTCOME_V1`. Nenhum boundary completo equivalente foi comprovado para a build atual.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81938d92e48e56c26ccd`.

## Contrato

- ARCANE/ICE; camada 5; Notable; 1 rank; 2 PP.
- Dependências: A0163 rank ≥3 + Ice Mastery ≥20.
- Uma ação mágica ICE direta que aplique um estado de controle ICE nativo reconhecido arma uma janela por alvo de 100 ticks.
- Dentro da janela, **uma magia ICE diferente** contra o mesmo alvo recebe:
  - +15% apenas no componente ICE direto;
  - +20% apenas no buildup nativo de ICE_CONTROL/CHILL que essa segunda magia já produziria.
- Consumo bem-sucedido fecha a janela e inicia recarga interna de 120 ticks.
- A mesma `spell_key` que armou a janela não pode consumi-la.

## Evidência provider-native

Iron's Spells 'n Spellbooks 3.16.3 possui ICE school/DamageType. O `ConeOfColdProjectile` auditado no snapshot exato aplica dano via DamageSource do spell, mas não demonstra um estado genérico `CHILL`/buildup compartilhado. Logo, o nome/tema ICE do spell não autoriza fabricar esse estado.

Ars Nouveau/Ars Elemental possuem mecânicas elementais próprias, mas só podem participar quando um adapter da versão instalada expuser state identity, autoria, spell identity e buildup mutável com contrato estável.

## Capability ausente

Requer `ICE_CONTROL_RECEIPT_V1`, além de `DIRECT_MAGIC_OUTCOME_V1`.

Receipt mínimo:

- `root_action_id`/`action_id`;
- ator/owner;
- alvo;
- `spell_key` namespaced;
- state/control id provider-native;
- buildup nativo aplicado/pretendido;
- possibilidade segura de multiplicar somente esse buildup;
- deduplicação do receipt.

Slowness, temperatura, partículas ou estética não são receipts.

## Pipeline futuro

1. `DIRECT_MAGIC_OUTCOME_V1` ICE + `ICE_CONTROL_RECEIPT_V1` elegível arma `window[player,target]` com `source_action_id` e `source_spell_key`.
2. Segunda ação ICE direta em ≤100 ticks, `spell_key` diferente e fora da recarga consulta a janela.
3. A0164 multiplica o componente ICE direto ×1,15 no mesmo outcome.
4. A0164 multiplica somente o buildup ICE_CONTROL nativo daquele outcome ×1,20.
5. No commit do outcome, consumir janela e iniciar cooldown 120 ticks.
6. Cancelamento/no-op não deve deixar consumo parcial.

## Estado transitório

- bounded por jogador→alvo;
- limpar em morte, logout, mudança de dimensão e descarte do alvo quando aplicável;
- nenhum estado permanente por target UUID sem cleanup;
- uma janela/outcome só pode ser consumida uma vez;
- `spell_key` inclui namespace/provider para evitar colisão entre mods.

## Fail-closed

Enquanto A0163 estiver unavailable, Ice Mastery causal não existir ou faltar `DIRECT_MAGIC_OUTCOME_V1`/`ICE_CONTROL_RECEIPT_V1`:

- compra falha antes do gasto;
- rank legado unavailable vale 0 PP em gates e permanece reembolsável/migrável;
- não criar CHILL/freezing/buildup próprio;
- não inferir estado por Slowness, BODY temperature ou visual;
- provider sem buildup mutável seguro permanece inativo para A0164.

## Mastery e anti-abuso

A0164 não concede Ice Mastery. A aplicação/reaplicação do mesmo estado por tick, DoT, field, summon, turret ou automação não pode gerar progressão por este contrato.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não criar `CHILL` universal e não instalar listener independente que multiplique dano sem `DIRECT_MAGIC_OUTCOME_V1`.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend sem A0163/capabilities;
2. mesma spell não consome a própria janela;
3. spell diferente dentro de 100 ticks seria elegível quando capabilities existirem;
4. boundary 100/101 ticks;
5. cooldown 120 ticks e consumo atômico;
6. ×1,15 somente no ICE direto e ×1,20 somente no buildup nativo;
7. Slowness/temperatura/partícula negativos;
8. cleanup morte/logout/dimensão/target e dedup multiplayer.