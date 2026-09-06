# A0083 — Vampirismo Mágico

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-8156-85ca-dc922918e483`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** `SustainResolver` possui a matemática, mas o bridge atual não produz receipt de dano mágico direto; portanto A0083 está **indisponível/não comprável** até existir ao menos um adapter causal seguro.

## Contrato canônico

- Gateway ARCANE + pelo menos um ramo de dano mágico direto + provider adapter disponível.
- 3 ranks: 0,6% / 1,2% / 1,8% do dano mágico direto pós-mitigação.
- Uma identidade causal resolve no máximo uma vez no `SustainResolver`; cap global 3% max health/20 ticks.
- Dano periódico, summon, ambiente, custo de vida/recurso e efeito derivado não entram.

## Binding provider-native exigido

A aquisição só é habilitada quando ao menos um provider prova, server-side, os quatro campos mínimos: `ownerPlayer`, classificação `DIRECT_MAGIC`, identidade causal do evento/root e dano pós-mitigação confirmado.

Para Iron's Spells 'n Spellbooks 3.16.3 existe uma superfície real candidata: `io.redspace.ironsspellbooks.damage.SpellDamageSource` carrega `spell()`, entidades direta/causadora, school/damage type e `getLifestealPercent()`. O Chat 2 pode usar essa classe/version contract para classificar roots do jogador sem heurística. `getLifestealPercent()>0` ainda exige correlação da cura nativa final antes de pagar a parcela Skill Tree.

Ars Nouveau 5.13.1 deve ser ligado somente por sua `SpellDamageSource`/contexto equivalente da versão instalada; o simples damage type `ars_nouveau:spell` não prova autoria/root sozinho. Addons só herdam a integração se preservarem a classificação causal do provider pai.

## Exclusões obrigatórias

- `BLOOD_MAGIC_COST` e Black Arcana `ARCANE_BACKLASH`: custos/hazards, nunca dano ofensivo elegível.
- Summons/familiars de Ars, Goety, Mobstein ou outros: não herdam lifesteal do owner.
- Enshrouded Shroud/Exposure e Volcanoes hazards: ambiente, não magia direta do jogador.
- Máquina/turret/fake player/contraption de mods tecnológicos: sem autoria direta do jogador.

## Evidência runtime

`A0081A0100CombatPolicy.sustainCoefficient(...)` contém A0083, porém `A0081A0100CombatEvents` só captura dano físico de melee/arrow e nunca chama o resolvedor com `directMagic=true`. Fórmula sem producer não habilita compra.

## Pendências para Chat 2

- **P-A0083-01 BLOQUEANTE:** unavailable-node invariant; A0083 não pode gastar pontos enquanto nenhum adapter `DIRECT_MAGIC` server-authoritative estiver disponível.
- **P-A0083-02:** implementar primeiro adapter versionado, preferencialmente Iron's `SpellDamageSource`, provando causing player + spell/root e excluindo summons/derived damage.
- **P-A0083-03:** adapter Ars 5.13.1 somente após validar a classe/contexto exato do artefato instalado; sem inferência por namespace isolado.
- **P-A0083-04:** native lifesteal de provider deve usar correlação exata; sinal ambíguo falha fechado por fonte.
- **P-A0083-05:** testes direct/indirect/periodic/summon/Backlash/cost, dedup cross-provider, cap e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | ARCANE + ramo direto + adapter causal. |
| Integração global | PASS | converge no SustainResolver. |
| Qualidade/identidade | PASS | sustain de magia direta, não de hazards/summons. |
| Topologia | PASS | ARCANE/SUSTAIN, Camada 4. |
| Especializações | PASS | PP por mapeamento semântico. |
| PT-BR | PASS | terminologia canônica. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Iron's/Ars somente por receipt real; addons por herança comprovada. |

Os 18 critérios passam **no design** com unavailable-node explícito.