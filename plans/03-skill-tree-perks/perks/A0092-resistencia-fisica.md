# A0092 — Resistência Física

## Estado

- **Chat 1:** DESIGN APROVADO / CONTRATO FECHADO.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Implementação:** classifier/tag física e pipeline de redução estão presentes; **não é IMPLEMENTAÇÃO CONFIRMADA**.
- **Notion:** `3c569db9-f0db-8100-b1b3-da080ac98ef8`; corrigido e re-fetched no ciclo A0091–A0100.
- **Domínio:** VITALITY; Camada 2; Ramo Armadura e Mitigação.
- **Ranks:** 4; custo 1 PP/rank.
- **Dependência:** A0089 Couro Endurecido ≥2 + Gateway VITALITY.

## Contrato canônico

- +2% de redução de dano **físico elegível** por rank, máximo 8%.
- Authority de classificação: tag de `DamageType` `rpgskilltree:physical` + adapters semânticos explícitos.
- Seed inicial obrigatório da tag: `minecraft:mob_attack`, `mob_attack_no_aggro`, `player_attack`, `arrow`, `trident`, `mob_projectile`, `thrown`, `sting`, `mace_smash`, `fall`, `fly_into_wall`, `falling_block`, `falling_anvil`, `falling_stalactite`, `stalagmite`, `cactus`, `sweet_berry_bush`.
- Fire/magic/wither/sonic/explosion não entram por analogia; dano modded só entra por revisão explícita/adapter.
- A0092 não substitui Armor/Toughness; é contributor próprio no pipeline defensivo.

## Composição canônica

- A0092 aplica exatamente uma vez em `LivingIncomingDamageEvent` server-side.
- Se A0096 também estiver ativa: `dano × (1 − A0092) × (1 − A0096)`.
- Percentuais não são somados; não existe cap defensivo agregado implícito neste node.
- BLOOD_MAGIC_COST, dano verdadeiro/não mitigável e fontes não classificadas ficam fora.

## Provider / authority

- Minecraft/NeoForge: `DamageSource`, `DamageType`, `LivingIncomingDamageEvent`.
- RPG Skill Tree: owner da tag custom e do pipeline defensivo único.
- Epic Fight 21.17.3.1/outros mods só entram por adapter que classifique explicitamente a mesma fonte; namespace/arma/animação não bastam.
- Black Arcana `ARCANE_BACKLASH` é terminal e não vira dano físico elegível por aparência/causalidade ofensiva.
- Volcanoes hazards e Enshrouded Shroud/Exposure não são classificados como físicos sem contrato específico; nenhum está previsto neste lote.

## Fallback / fail-closed

- Fonte modded desconhecida = A0092 inativa somente para aquela fonte.
- Proibido fallback para resistência universal ou inferência por attacker/item/namespace.
- Tag/adapter inconclusivo não promove a fonte para físico.

## Evidência após Chat 2

- O recurso `rpgskilltree:physical` foi materializado com os **17 `DamageType`** fechados pelo design.
- `A0081A0100CombatEvents` consulta o classifier canônico uma única vez no pipeline incoming.
- `A0081A0100CombatPolicy.physicalDamageMultiplier` contém a matemática de A0092/A0096 e preserva composição multiplicativa.
- Adapters desconhecidos continuam fail-closed; o Chat 2 não adicionou inferência por namespace, arma ou animação.
- O Chat 2 **não executou** unit tests, GameTests, build NeoForge, dedicated-server smoke ou CI; dedup e reload ainda precisam de prova pelo Chat 3.

## Deduplicação / anti-abuso

- Uma resolução por evento causal de dano recebido.
- A tag classifica; não produz reward/Mastery.
- Adapters devem convergir no mesmo classifier, não reaplicar o multiplicador.

## Testes obrigatórios para o Chat 3

1. todos os 17 `DamageType` seed entram; fire/magic/wither/sonic/explosion ficam fora;
2. rank 1–4 aplica 2/4/6/8% exatamente uma vez;
3. A0092 + A0096 compõem multiplicativamente e na ordem documentada;
4. fonte modded sem adapter = sem efeito;
5. adapter explícito = efeito uma vez, sem double-processing;
6. BLOOD_MAGIC_COST/ARCANE_BACKLASH/hazards não são convertidos para físico;
7. gateway + A0089≥2 são exigidos na compra;
8. datapack reload preserva classifier válido; tag inválida não promove classificação inventada;
9. GameTest/dedicated-server com dano vanilla físico e não físico.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0089≥2 + VITALITY. |
| Integração global | PASS | Pipeline físico separado de Armor, magic/hazards. |
| Qualidade/identidade | PASS | Mitigação física especializada. |
| Topologia | PASS | Camada 2 após Couro Endurecido. |
| Especializações | PASS | `VITALITY/PHYSICAL_RESISTANCE`. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Corrigido e re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Classifier explícito e fail-closed. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [x] Tag física implementada
- [x] Pipeline/dedup estrutural reconciliado pelo Chat 2
- [x] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: unit/GameTests/adapters
- [ ] VALIDAÇÃO CHAT 3: build + dedicated server + CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA
