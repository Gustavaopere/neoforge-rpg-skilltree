# A0157 — Dano de Fogo II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

A perk herda A0156 e também exige estado de combustão removível com ownership/provenance. O contador de fogo atual da entidade não preserva qual fonte adicionou cada parcela de duração.

## Contrato

- ARCANE; camada 5; Notable; 1 rank; 2 PP.
- Pré-requisitos: A0156 ≥3 + Fire Mastery ≥20 + Gateway ARCANE.
- O alvo precisa possuir **antes do impacto** ≥80 ticks de combustão FIRE reconhecida e removível pertencente ao pipeline elegível.
- Reservar consumo de 40 ticks e multiplicar o componente FIRE direto atual por ×1,18.
- CD interno: 80 ticks.
- Primeiro hit que cria a combustão não qualifica.

## Authority e evidência

Iron's aplica seus post-hit fire effects em `LivingDamageEvent.Post` por `target.igniteForTicks(...)`. Isso converge no contador de fogo da entidade e não mantém ownership da duração por origem. Remover 40 ticks diretamente poderia consumir fogo de vanilla, outro player, outro spell ou hazard.

Nenhum `OWNED_FIRE_STATE_V1` equivalente foi provado para Ars/NeoForge.

## Availability

Exige A0156 capability-eligible + `OWNED_FIRE_STATE_V1` com duração, ownership, provenance e mutação atômica. Sem isso, node não comprável.

## Transação

Snapshot/ownership são avaliados pré-impacto. Consumo de 40 ticks, ×1,18 e CD commitam juntos somente após outcome aceito. Cancelamento, dano 0 ou ownership ambíguo fazem rollback/no-op.

## Exclusões

- `isOnFire()` ou contador global como ownership;
- consumir duração criada pelo próprio hit;
- fogo ambiental, lava, Volcanoes heat ou combustão de outro actor;
- reduzir estado antes de o dano confirmar.

## Handoff Chat 2

Não criar side-ledger que reivindique fogo de terceiros. Só habilitar quando a origem elegível puder ser mantida causalmente.

## Testes Chat 3

1. unavailable e availability transitiva A0156;
2. pre-existing 79/80 ticks boundary;
3. primeiro ignition não qualifica;
4. consume 40 + ×1,18 + CD80 atômicos no commit;
5. mixed-source ownership fail-closed;
6. cancel/zero rollback, restart/lifecycle e multiplayer.