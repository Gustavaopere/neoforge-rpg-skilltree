# A0161 — Afinidade de Fogo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A identidade da perk foi preservada, mas ela não pode ser adquirida no estado atual porque a cadeia causal necessária para alterar somente a parcela térmica atribuível a uma magia FIRE ainda não existe na `main`.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81aea5a6ecad6d77e2b2`.

## Contrato de gameplay

- ARCANE/FIRE; camada 6; Keystone exterior; 1 rank; 2 PP.
- Dependências: A0156 rank ≥3 + Fire Mastery ≥30 + pelo menos um entre A0157=1, A0159≥2 ou A0160≥2.
- Conjuração FIRE própria elegível: reduzir em 25% **somente a parcela de deslocamento térmico para quente** causada por aquela ação.
- Exposição mágica FIRE externa explicitamente adaptada: reduzir em 10% somente a parcela térmica causal daquela exposição.
- Não reduz dano FIRE, não concede `RPG_FIRE_RESISTANCE`, não aumenta dano e não substitui aclimatação ambiental.

## Authority

- Cold Sweat 2.4.2 é a autoridade única da temperatura corporal.
- Iron's Spells 'n Spellbooks 3.16.3 e Ars Nouveau 5.13.1/Ars Elemental 0.7.10.1 podem fornecer identidade/autoria FIRE apenas por adapters versionados.
- RPG Skill Tree pode transformar a parcela causal antes de sua aplicação, mas não cria segunda temperatura e não grava BODY/CORE/RATE/BURNING_POINT diretamente.
- Volcanoes/Enshrouded/Black Arcana não são classifiers FIRE desta perk por semântica temática.

## Blocker canônico

Capacidade requerida: `MAGIC_THERMAL_PARCEL_V1`.

O receipt deve carregar, no mínimo:

- `action_id` estável;
- ator/origem causal;
- elemento FIRE explicitamente classificado;
- delta térmico assinado daquela ação/parcela;
- fase anterior à mutação canônica do Cold Sweat.

O `TemperatureChangedEvent` público do Cold Sweat expõe entidade, trait, valor anterior e valor novo, mas não identifica a fonte causal. Portanto diferenças globais de BODY não podem ser repartidas retroativamente com segurança.

Além disso, A0156 está indisponível enquanto `DIRECT_MAGIC_OUTCOME_V1` não existir; a dependency closure de A0161 já permanece fechada hoje.

## Pipeline futuro obrigatório

`provider FIRE causal -> MAGIC_THERMAL_PARCEL_V1 -> A0161 transforma apenas signed_delta elegível -> Cold Sweat aplica a parcela uma única vez`.

Para conjuração própria quente: `transformed_delta = signed_delta * 0.75`.

Para exposição mágica FIRE externa explicitamente adaptada: `transformed_delta = signed_delta * 0.90`.

Uma action/parcela pode receber A0161 no máximo uma vez.

## Fail-closed / disponibilidade

Enquanto faltar `MAGIC_THERMAL_PARCEL_V1` ou qualquer dependência obrigatória:

- compra deve falhar **antes** de consumir PP;
- rank legado indisponível contribui 0 PP para Gate B/thresholds semânticos;
- rank legado deve permanecer reembolsável/migrável;
- não inferir parcela por dano FIRE, school isolada, partícula, duração de fogo, temperatura atual ou diferença before/after de BODY;
- não degradar para `+dano`, resistência, redução térmica global ou outro bônus substituto.

## Mastery e anti-abuso

Fire Mastery só pode satisfazer o gate quando vier do serviço canônico com autoria causal e milestones/eventos discretos. Tick, tempo quente, duração de spell, equipamento vestido ou exposição contínua não concedem Mastery por esta perk.

## Handoff Chat 2

Implementar somente o estado de disponibilidade/fail-closed previsto neste dossiê. Não inventar `MAGIC_THERMAL_PARCEL_V1`, não escrever diretamente no estado do Cold Sweat e não substituir a perk por bônus genérico. Se uma capability causal real surgir no código/provider, a mudança de availability deve voltar ao Chat 1 antes de alterar a semântica aprovada.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto `MAGIC_THERMAL_PARCEL_V1` faltar;
2. rank legado indisponível vale 0 PP para gates e continua reembolsável/migrável;
3. ausência de inferência por `TemperatureChangedEvent` old/new global;
4. ausência de escrita direta BODY/CORE/RATE/BURNING_POINT;
5. ausência de efeito sobre dano FIRE/`RPG_FIRE_RESISTANCE`;
6. quando capability futura existir: 25% self FIRE e 10% external magic FIRE somente na parcela causal;
7. uma transformação por `action_id`/parcela, inclusive multiplayer/reload;
8. provider/version mismatch permanece fail-closed.