# A0321 — Nadador

## Estado

- **Chat 1:** DESIGN APROVADO.
- **Disponibilidade atual:** IMPLEMENTÁVEL.
- **Fonte canônica:** Notion `A0321` — https://app.notion.com/3c569db9f0db81fb8933f9a6330309e0
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0321 concede **+4% de velocidade de natação por rank**, exclusivamente durante nado real server-side.

- rank 1: +4%;
- rank 2: +8%;
- rank 3: +12%;
- rank 4: +16%.

A perk modifica somente `NeoForgeMod.SWIM_SPEED`. Não altera movimento terrestre, respiração/air supply, temperatura, HYDRATION, buoyancy, correnteza, stamina ou metabolismo.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥2.

Gate runtime: rank A0321 > 0 + `player.isSwimming()` verdadeiro no servidor.

Estar apenas molhado, submerso sem estado de nado, dentro de veículo, sendo empurrado pela água, caindo sobre água ou possuir forma AQUATIC não satisfaz automaticamente o gate.

## Providers e authority

- Minecraft/NeoForge 1.21.1: authority de `LivingEntity.isSwimming()` e `NeoForgeMod.SWIM_SPEED`.
- RPG Skill Tree: owner do node, rank e lifecycle da contribuição.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0: não são necessários para a rota base e não podem substituir o estado real de nado.
- Cold Sweat e qualquer sistema HYDRATION permanecem authorities separadas.

A `main` auditada em 2026-09-05 **não contém `AttributeNodeEffectRuntime` nem helper genérico de `AttributeModifier`**. Chat 2 não deve depender desses nomes documentais antigos.

## Hook e pipeline canônico

No lifecycle server-side do node, reconciliar uma única contribuição transitória:

`rpgskilltree:agility_swim_speed`

- atributo: `NeoForgeMod.SWIM_SPEED`;
- amount: `0.04 × rank`;
- operação: `AttributeModifier.Operation.ADD_MULTIPLIED_BASE`;
- presente somente enquanto `player.isSwimming()`.

Adicionar, atualizar e remover idempotentemente. Não criar um ID por rank, não escrever velocidade absoluta por tick e não criar motor global paralelo apenas para esta perk.

## Lifecycle obrigatório

O modifier deve ser reconciliado/removido em:

- entrada/saída do estado de nado;
- rank 0;
- respec;
- perda de pré-requisito;
- morte/clone;
- logout/relogin;
- rules reload que invalide a perk.

Reload nunca pode duplicar modifier.

## Fallback / fail-closed

Se `SWIM_SPEED` ou o estado server-side de nado não puderem ser resolvidos por mismatch real, a contribuição falha fechado. Não substituir por MobEffect, Dolphins Grace, Depth Strider, velocity packet, respiração, hidratação ou física custom.

## Anti-abuso e deduplicação

- exatamente um modifier A0321 por jogador;
- mudança de rank substitui magnitude, não empilha IDs;
- entrar/sair rapidamente da água não acumula modifiers;
- nado não gera Mastery por permanência/tick;
- nenhuma bridge ParCool/Epic ParCool reaplica o mesmo bônus.

## Testes destinados ao Chat 3

1. ranks 1–4 produzem exatamente +4/+8/+12/+16% em `SWIM_SPEED`;
2. fora de `isSwimming()` o modifier está ausente;
3. molhado/submerso sem swimming não ativa;
4. rank change atualiza uma única instância;
5. rank 0/respec remove imediatamente;
6. morte/clone/logout/relogin/rules reload não duplicam estado;
7. movimento terrestre, air supply, temperatura e HYDRATION permanecem inalterados;
8. ParCool/Epic ParCool presentes não duplicam contribuição;
9. multiplayer: estado isolado por jogador;
10. dedicated-server smoke com provider NeoForge presente.

## Handoff Chat 2

Implementar exatamente pelo atributo provider-native `SWIM_SPEED` e `isSwimming()` server-side. A ausência do helper documental antigo não autoriza criar uma abstração global nova; basta integrar a contribuição ao lifecycle canônico do node de forma estável e idempotente.
