# Apothic Attributes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81718e36f5a538245c41
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apothic Attributes
- **Arquivo JAR:** `ApothicAttributes-1.21.1-2.10.1.jar`
- **Versão 1.21.1:** 2.10.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Biblioteca, RPG
- **Função:** Biblioteca e runtime de atributos do ecossistema Apothic: registra estatísticas de crítico, dodge, cura, life steal/overheal, armor/protection pierce/shred, projectile damage, cooldown reduction e outras superfícies; também fornece Attributes GUI e lógica associada ao damage pipeline.
- **Dependências:** Placebo 9.9.2 + ecossistema Apothic atual; consumidor estrutural principal Apotheosis 8.8.0. Integrações externas devem usar registry/operations do provider, não duplicar pipeline.
- **Sobreposição:** Sobreposição conceitual com Additional Attributes/Pufferfish's Attributes, mas contratos, registry ids e pipelines são distintos. Não substituir atributos Apothic por equivalentes nominais sem bridge real.
- **Compatibilidade/Riscos:** Grande área de double-processing com outros providers de atributos/combate. Não duplicar crit, armor/protection pierce/shred, life steal, overheal ou cooldown reduction em handlers paralelos. Curios só é fonte quando slots/modifiers estão registrados corretamente; respeitar tags atuais de dano físico/não físico.
- **Observações:** mod id: `apothic_attributes`; runtime 2.10.1. A linha 2.10.x inclui sistema unificado de cooldown/cooldown_reduction e JEI exclusion zones da Attributes GUI. O corpo da página registra mudanças relevantes, boundaries e testes.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/source/changelog oficiais Apothic Attributes 2.10.1 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ApothicAttributes-1.21.1-2.10.1.jar` / `2.10.1`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-attributes
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #34: `ApothicAttributes-1.21.1-2.10.1.jar` / `2.10.1` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita com changelog 1.21 da versão instalada. A presença de outros mods de atributos não torna Apothic Attributes redundante: ele é dependência estrutural e provider do pipeline Apothic.
- **Data da última decisão:** 2026-09-07

> 📚 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta página documenta o runtime físico `ApothicAttributes-1.21.1-2.10.1.jar`, mod id `apothic_attributes`, em NeoForge 1.21.1. O inventário abaixo foi reconstruído contra o registro/API e changelog da linha 1.21, distinguindo **atributos registrados, efeitos, damage types, GUI/API, integração e riscos de pipeline**. Não trate esta página como mera descrição de biblioteca.

## 1. Identidade, versão e authority
- **Mod:** Apothic Attributes.
- **Runtime instalado:** `2.10.1`.
- **JAR físico:** `ApothicAttributes-1.21.1-2.10.1.jar`.
- **Mod id:** `apothic_attributes`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** provider de atributos, efeitos e componentes do damage/cooldown pipeline usados pelo ecossistema Apothic/Apotheosis e por integrações externas.
- **Authority:** os registry ids e operações do próprio Apothic Attributes são canônicos. Uma integração do RPG Skill Tree não deve reconstruir crítico, penetração, life steal, overheal ou cooldown em handlers paralelos.
- **Decisão:** **Manter**.

## 2. Catálogo completo de atributos registrados
> Valores abaixo são os defaults/ranges declarados pelo registro da linha 1.21 auditada. Eles descrevem o contrato do atributo; configs/modifiers podem alterar o valor efetivo do jogador.

| Registry id | Base / faixa | Semântica operacional |
| --- | --- | --- |
| `armor_pierce` | 0 / 0–1000 | Quantidade **flat** de armor ignorada na resolução do ataque. |
| `armor_shred` | 0 / 0–2 | Redução **percentual** da armor efetiva do alvo. Não é sinônimo de pierce. |
| `arrow_damage` | 1 / 0–10 | Multiplicador específico de dano de flechas. |
| `arrow_velocity` | 1 / 0–10 | Multiplicador de velocidade de flechas; velocidade também participa do dano de flecha conforme o pipeline do mod. |
| `cold_damage` | 0 / 0–1000 | Dano mágico auxiliar de frio; a implementação também associa slow ao acerto correspondente. |
| `crit_chance` | 0.05 / 0–10 | Chance de critical strike do pipeline Apothic para ataques. É distinta do crit vanilla por salto. |
| `crit_damage` | 1.5 / 1–100 | Multiplicador de dano crítico; também afeta o crit vanilla quando ele ocorre. |
| `current_hp_damage` | 0 / 0–1 | Dano físico auxiliar proporcional à **vida atual** do alvo. |
| `dodge_chance` | 0 / 0–1 | Chance de esquivar de impactos melee/projéteis conforme o pipeline Apothic. |
| `draw_speed` | 1 / 0–4 | Multiplicador de velocidade de carregamento de armas ranged compatíveis. |
| `experience_gained` | 1 / 0–1000 | Multiplicador de XP obtida das superfícies suportadas, incluindo mob/ore XP no pipeline auditado. |
| `fire_damage` | 0 / 0–1000 | Dano mágico auxiliar de fogo; o pipeline também aplica burning associado. |
| `ghost_health` | 0 / 0–1000 | **Atributo registrado** pela API. A semântica detalhada de consumo/aplicação não foi tomada por inferência nesta auditoria; qualquer integração específica deve confirmar o uso no source/runtime antes de escrever nele. |
| `healing_received` | 1 / 0–1000 | Multiplicador de cura recebida. |
| `life_steal` | 0 / 0–10 | Converte uma fração do dano físico pós-mitigação em cura do atacante. |
| `mining_speed` | 1 / 0–10 | Multiplicador da velocidade de quebra de blocos. |
| `overheal` | 0 / 0–10 | Converte fração do dano físico em absorção/vida excedente conforme o pipeline do mod. |
| `projectile_damage` | 1 / 0–10 | Multiplicador genérico de dano de projéteis suportados. |
| `prot_pierce` | 0 / 0–34 | Quantidade **flat** de proteção de encantamentos ignorada. |
| `prot_shred` | 0 / 0–1 | Redução **percentual** da proteção de encantamentos. |
| `elytra_flight` | false / boolean | Habilita capacidade de voo de Elytra como atributo booleano. |
| `cooldown_reduction` | 0 / -10–0.95 | Redução do tempo de cooldown no sistema unificado. Ex.: `0.25` implica 75% da duração original; valores negativos aumentam duração. |

### Regra de integração dos pares pierce/shred
- **Pierce = redução flat/ignorada durante a resolução.**
- **Shred = redução percentual.**
- Armor e Protection são canais diferentes.
- Não converter um no outro nem aplicar ambos uma segunda vez em evento externo.

## 3. Catálogo completo de mob effects registrados
| Efeito | Semântica confirmada no source |
| --- | --- |
| **Bleeding** | Causa dano periódico que ignora armor. O dano cresce com o nível do efeito; a cadência do runtime é aproximadamente a cada 2 segundos. |
| **Detonation** | Ao expirar, consome o tempo de fogo restante do alvo e converte essa duração em dano mágico/armor-piercing conforme a implementação. |
| **Grievous Wounds** | Reduz `healing_received`; a implementação registrada trabalha com redução de 40% por nível. |
| **Ancient Knowledge** | Multiplica XP de mobs usando o multiplicador configurável `knowledgeMult`. |
| **Sundering** | Atua como inverso de Resistance: aumenta dano recebido em 20% por nível e primeiro neutraliza níveis de Resistance quando ambos coexistem. |
| **Bursting Vitality** | Aumenta `healing_received` em 20% por nível. |
| **Flying** | Concede capacidade de creative flight enquanto o efeito/condição está válido. |

## 4. Damage types e dano auxiliar
A linha auditada registra/usa canais próprios para:
- `bleeding`;
- `detonation`;
- `current_hp_damage`;
- `fire_damage`;
- `cold_damage`.

O branch 1.21 também introduziu tags para classificar dano e controlar interação com crítico, incluindo:
- `apothic_attributes:is_non_physical`;
- `apothic_attributes:cannot_critically_strike`.

**Regra:** uma bridge deve classificar o dano pelo contrato/tags do provider. Não hardcodar “todo magic damage crita” nem reconstruir a classificação por nome de classe.

## 5. Critical strikes — pipeline e distinções
- `crit_chance` pertence ao pipeline Apothic e pode produzir crit independentemente do crit vanilla por salto.
- `crit_damage` controla o multiplicador e também participa do crit vanilla suportado.
- Desde a linha 2.8, multi-crits acima de 100% foram alterados para **somar** em vez de multiplicar recursivamente o dano.
- Damage types marcados como `cannot_critically_strike` não devem ser forçados a critar por adapter externo.

### Risco no pack
Epic Fight, Additional Attributes, Pufferfish's Attributes, armas modded e outros providers podem possuir seu próprio conceito de crítico. **Uma ação precisa de um pipeline canônico.** O fato de dois mods terem “crit chance” não autoriza somar duas rolagens para o mesmo hit.

## 6. Armor / Protection pipeline
O mod separa quatro grandezas:
1. armor pierce;
2. armor shred;
3. protection pierce;
4. protection shred.

Na linha 2.9, armor efetiva negativa passou a poder **aumentar dano recebido**, com fator configurável. Portanto clamps externos como “armor nunca abaixo de zero” podem contradizer o runtime atual.

## 7. Life Steal, Overheal e cura
- **Life Steal:** cura derivada de dano físico pós-mitigação.
- **Overheal:** converte parte do dano físico em absorção/excedente.
- **Healing Received:** multiplica a cura que chega ao alvo.
- Grievous Wounds e Bursting Vitality modificam esse canal em sentidos opostos.
- A linha 2.6 recebeu correções específicas de Life Steal/Overheal após mudanças do damage pipeline NeoForge.

**Proibição:** perks não devem ouvir o mesmo hit e aplicar uma segunda cura “equivalente” ao Life Steal/Overheal do atributo.

## 8. Auxiliary damage
A linha 2.8 refez o sistema de auxiliary damage. `fire_damage`, `cold_damage` e `current_hp_damage` são superfícies registradas que alimentam esse pipeline. Em integração:
- preserve o tipo de dano;
- preserve causalidade do ataque original;
- não publicar XP/Mastery por cada sub-hit auxiliar como se fosse ação independente;
- deduplicar pela ação/attack context quando houver progressão.

## 9. Cooldown system — 2.10.x
A `2.10.0` introduziu:
- sistema unificado de cooldown;
- atributo `cooldown_reduction`.

A `2.10.1` instalada preserva esse contrato. Integrações que já recebem cooldown reduzido pelo provider **não devem diminuir novamente o valor** em outro event handler.

## 10. Attributes GUI e observabilidade
O mod fornece uma interface de atributos para mostrar:
- valor atual;
- modifiers/fontes relevantes;
- equipamento e outras origens reconhecidas.

Na linha 1.21:
- suporte a Curios foi restaurado/ajustado para mostrar modifiers quando o slot é exposto pela API de equipment slots;
- `EntityEquipmentSlot` foi introduzida como API experimental para unificar slots vanilla/modded;
- 2.10.1 adicionou **JEI Exclusion Zones** para a GUI.

A GUI é ferramenta de observabilidade. Não use texto da UI como authority de gameplay; consulte o atributo real.

## 11. Curios e equipment slots
A API `EntityEquipmentSlot` permite representar fontes de equipamento além dos slots vanilla. Isso é relevante para Curios, mas não significa que **todo slot de todo addon** seja automaticamente reconhecido. Ao integrar:
- confirme que o slot/provider publica o modifier esperado;
- não escaneie inventários arbitrários para “recriar” modifiers;
- remova modifiers pela identidade estável do sistema de atributos, não pelo texto exibido.

## 12. Integrações do pack
### Apotheosis / módulos Apothic
É o principal consumidor estrutural. Affixes, gems e equipamentos podem conceder atributos registrados aqui.

### Additional Attributes
Há sobreposição **conceitual** em algumas estatísticas, mas registry ids/semânticas são diferentes. Additional Attributes também cobre superfícies como Looting/Harvest/Iron's spell level; não é substituto do pipeline Apothic.

### Pufferfish's Attributes
Outro provider de atributos RPG. A coexistência exige decidir por atributo/ação qual contrato será usado. Não criar soma automática por nomes parecidos.

### Epic Fight
Combate e stamina permanecem sob as regras do Epic Fight onde aplicável. Crit/dodge/armor interactions devem ser validadas por hit real para impedir dupla resolução.

### Curios
Fonte de modifiers/equipment slots quando a integração está presente.

## 13. Mudanças relevantes da linha 2.6 → 2.10
- **2.6.x:** correções no Life Steal/Overheal após mudanças do NeoForge damage pipeline.
- **2.7.x:** API `EntityEquipmentSlot`; correções de Curios na Attributes GUI.
- **2.8.x:** auxiliary damage refeito; multi-crits >100% passam a somar; tags de dano físico/não físico/crit.
- **2.9.x:** armor negativa pode aumentar dano; correções de Sundering; suporte a wildcards/negações em atributos ocultos.
- **2.10.0:** cooldown system unificado + `cooldown_reduction`.
- **2.10.1:** JEI exclusion zones para Attributes GUI.

## 14. Regras para RPG Skill Tree / integrações próprias
1. Preferir `AttributeModifier`/API real ao invés de recalcular fórmulas.
2. `effectId`/modifier identity deve ser estável para impedir stacking em refresh/reload.
3. Rank muda valor, não identidade do modifier.
4. Crit, dodge, shred/pierce, life steal, overheal e cooldown são **single-pipeline**.
5. Nenhum bônus de atributo gera Mastery por tick.
6. Read-only queries não podem materializar estado, curar, causar dano ou consumir recurso.
7. Provider ausente/incompatível = parcela dependente **fail-closed**.

## 15. Riscos específicos
- double-crit com Epic Fight/atributos externos;
- armor/protection reduzidas duas vezes;
- life steal/overheal duplicados por listener externo;
- cooldown reduction aplicada duas vezes;
- Curios slot não registrado e modifier invisível;
- auxiliary damage contado como nova autoria/Mastery;
- documentação antiga que ignora mudanças 2.8–2.10;
- integração específica com `ghost_health` sem confirmar semântica real.

## 16. Matriz de validação
### Atributos
- testar cada um dos 22 registry ids com modifier controlado;
- confirmar clamp/range e remoção idempotente;
- testar armor/prot pierce e shred separadamente;
- testar armor efetiva negativa;
- testar crit chance/damage, inclusive >100%;
- testar dodge;
- testar life steal/overheal/healing received;
- testar projectile/arrow damage + velocity;
- testar mining/draw speed;
- testar cooldown positivo e negativo;
- testar elytra flight.

### Effects / damage
- Bleeding, Detonation, Grievous Wounds, Ancient Knowledge, Sundering, Bursting Vitality e Flying;
- tags de crit/non-physical;
- auxiliary fire/cold/current-HP damage sem double-processing.

### Integração
- Curios source display;
- Attributes GUI + JEI;
- Apotheosis affix/gem modifier;
- Epic Fight hit controlado;
- dedicated server sem carregamento de GUI client-only;
- save/load, death/respawn e modifier cleanup;
- reload de configs/datapacks quando aplicável.

## 17. Fontes
- Source oficial `Shadows-of-Fire/Apothic-Attributes`, branch `1.21`, especialmente `ALObjects.java` e pipeline correspondente.
- Changelog oficial da linha 1.21.
- CurseForge oficial do Apothic Attributes.
- Modlist física do projeto — authority de JAR/runtime instalado.
