# Auditoria retroativa de integração — A0001–A0010

Data: 2026-08-30.

## Escopo

- **INÍCIO:** A0001.
- **FIM:** A0010.
- **Quantidade:** exatamente 10 perks consecutivas.
- **Providers auditados exclusivamente:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4.
- **A0011+:** fora do escopo deste ciclo e não alterado.
- **Objetivo:** revalidar a integração nos dois sentidos — perk → provider e provider → árvore — sem repetir a auditoria geral dos demais mods.

## Fontes e checkpoints

Foram usados os critérios consolidados, `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`, os três guias temáticos consolidados e o protocolo do Chat 1 anexados ao projeto, mais fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos próprios.

Baselines do guia anexado e heads observados durante a auditoria:

| Projeto | Baseline anexado | Head fresco auditado | Disposição do delta |
|---|---|---|---|
| RPG Skill Tree | `f448aa0b4f9df400011873e9ad26771209876ad4` | `main@710dfae72aaec30bd906ea89876f81dd396d10c5` | `SEM DELTA JOGÁVEL RELEVANTE PARA A0001–A0010`; mudanças posteriores ao snapshot foram gates/documentação e housekeeping, sem novo boundary marcial |
| Volcanoes | `602e0188c123ac8531d3413a5630daa22e3d761f` | `main@862318fbcebd9105c7504c652e75e6a961cf0ea7` | `SEM DELTA JOGÁVEL RELEVANTE PARA A0001–A0010`; novas mudanças observadas não criam hook marcial |
| Enshrouded | `77552a3d7f089a47908c109f5f8c19aff8a0f97d` | `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5` | 06.01 Story State formalizado/reconciliado; `NÃO DEVE SER INTEGRADO` às perks marciais deste lote |
| Black Arcana | `07263ae9bad12eba6ed500992991faa36ad598b2` | `main@73c14ce55ff918bb8a81daeb99a352607ef11064` | sem novo hook marcial; Arcane Danger/Backlash permanece boundary de exclusão relevante |

Mobstein `5.4.4` é o delta externo atual da modlist, JAR `mobstein-5.4.4-neoforge-1.21.1.jar`.

Nenhum baseline é usado para promover Stage planejado a runtime. Capacidades planejadas aparecem abaixo como `SEM HOOK SEGURO`/ciclo posterior quando pertinentes.

## Matriz provider → árvore

### RPG Skill Tree

| Capacidade | Estado | Disposição para A0001–A0010 |
|---|---|---|
| Core Progression / Skill Tree / NodeEffectRuntime | canônico | `COBERTA POR PERK EXISTENTE`; authority de rank/gate/efeitos derivados do próprio RPG |
| estados marciais Ímpeto/Fúria e serviço crítico A0001–A0020 | canônico/específico | `COBERTA POR PERK EXISTENTE`; uma ação causal, uma mutação/resolução |
| World Scaling | canônico | `COBERTO POR SISTEMA UNIVERSAL`; alvo escalado não muda a autoria nem duplica o cálculo das perks |
| Stage 11 itemização/equipment | planejado | `SEM HOOK SEGURO` para estas perks; não antecipar affix/Item Power como provider de família/crítico |
| Stage 12 bodies/clones/progression identities | planejado | `SEM HOOK SEGURO`; pode futuramente decidir roteamento da progressão ao corpo ativo, inclusive numa eventual integração corporal com Mobstein, mas não altera A0001–A0010 agora |
| Stage 13 cartografia/POI | planejado | `NÃO DEVE SER INTEGRADO` às perks marciais deste lote |

### Volcanoes

Geologia, tectônica, vulcanismo, Atmosphere, O₂/respiração, gases, poluição, pressão, protected volumes e protection equipment são capacidades legítimas do Volcanoes, mas nenhuma delas é semântica ou mecanicamente necessária para dano/cadência/crítico/Ímpeto/guarda/Riposta/Fúria de A0001–A0010.

**Classificação do lote:** `NÃO DEVE SER INTEGRADO`.

- não transformar O₂, SO₂, pressão, temperatura, hazard ou tectonic stress em dano/crítico/Fúria;
- não dar Mastery por tick/permanência em hazard;
- um mob/alvo relacionado ao projeto, se houver, é apenas alvo da ação marcial universal;
- futuras perks de geologia/Atmosphere/pressure pertencem a ciclos próprios, usando boundaries reais como `GeologicalDepositSource`/services ambientais.

### Enshrouded

Shroud, Exposure, Madness, Flame Passage, Sanctuary/Flame Ward, Story State e Corrupted Ecology permanecem Enshrouded-owned.

**Classificação do lote:** `NÃO DEVE SER INTEGRADO`, com uma regra de boundary importante:

- `MagicResistanceService` de mobs corrompidos é reducer próprio e **não é guarda física** para A0005;
- Shroud/Exposure/Flame/Story não geram Ímpeto/Fúria/Mastery marcial;
- atacar diretamente um mob corrompido continua sendo ação Epic Fight normal; isso é `COBERTO POR SISTEMA UNIVERSAL`, não bridge Enshrouded→perk;
- Stage 08 integrations continua planejado e não cria adapter RPG por inferência.

### Black Arcana

Black Arcana possui a integração legítima mais importante deste lote como **boundary negativo de causalidade**.

`ARCANE_BACKLASH` é terminal. Portanto nunca pode:

- herdar A0001/A0007 como multiplicador de dano;
- entrar nas resoluções críticas A0003/A0009;
- gerar ou consumir Ímpeto A0004;
- qualificar/consumir A0005;
- armar ou consumir Riposta A0006;
- gerar Fúria A0010;
- gerar Mastery/proc/sustain ofensivo do RPG.

`Arcane Resistance`, `Corruption Resistance` e `Arcane Strain` também não são guarda/postura/defesa física para A0005.

Ataques físicos diretos do jogador contra entidades Black Arcana continuam cobertos pelo pipeline Epic Fight: Black Arcana não vira `Provider/Mods` dessas perks apenas por ser o alvo.

A integração RPG ↔ Arcane Danger planejada para resistência/progressão futura é `SEM HOOK SEGURO` para o presente lote e não autoriza escrever Arcane Danger diretamente.

### Mobstein 5.4.4

Capacidades relevantes: ressurreição corporal, cadáveres/órgãos, experimentos, allies/bodyguards ressuscitados, estruturas, Witherstein e perks internas `Attack/Health/Speed/Template`.

Disposição:

- combate direto do jogador contra mobs/bosses Mobstein: `COBERTO POR SISTEMA UNIVERSAL` pelo Epic Fight;
- dano causado por ally/bodyguard ressuscitado: `PROGRESSÃO NATIVA AUTORITATIVA`/Mobstein-owned para a origem; não recebe autoria marcial do dono para crítico, Ímpeto, Riposta, Fúria ou Mastery;
- perks internas Attack/Health/Speed/Template: `PROGRESSÃO NATIVA AUTORITATIVA`; não mapear para A0001–A0010;
- ressurreição/corpos/órgãos/experimentos/estrutura/boss como possíveis milestones futuros: fora da identidade destes dez nodes; precisam ciclo/perk/especialização apropriada e identidade deduplicável;
- possível conexão futura com RPG Stage 12 bodies: `SEM HOOK SEGURO` enquanto o Stage 12 permanecer planejado;
- nenhuma bridge automática com Black Arcana/Enshrouded por necromancia/corpo.

## Matriz perk → providers auditados

| Perk | RPG Skill Tree | Volcanoes | Enshrouded | Black Arcana | Mobstein 5.4.4 | Notion |
|---|---|---|---|---|---|---|
| A0001 | provider interno do efeito/rank | não integrar | não integrar | alvo universal; Backlash inelegível | alvo universal; companion inelegível | sem mudança |
| A0002 | rank/gate; cadência Epic Fight | não integrar | não integrar | Backlash/cast não recebe cadência | companion não herda cadência | sem mudança |
| A0003 | crítico canônico/root action | não integrar | não integrar | Backlash fora do resolver | companion fora do resolver | **Hook/Fallback/Regra corrigidos** |
| A0004 | Ímpeto canônico | não integrar | não integrar | Backlash não gera/consome Ímpeto | companion não gera/consome Ímpeto | **Hook/Fallback/Regra corrigidos** |
| A0005 | Ímpeto/cooldown/dedup | não integrar | MagicResistance não é guarda | Arcane Danger não é guarda física | companion não consome recurso do dono | **Hook/Fallback/Regra corrigidos** |
| A0006 | Ímpeto/riposta/crítico canônicos | não integrar | não integrar | Backlash não arma/consome | companion não arma/consome | **Hook/Fallback/Regra corrigidos** |
| A0007 | provider interno do efeito/rank | não integrar | não integrar | alvo universal; Backlash inelegível | alvo universal; companion inelegível | sem mudança |
| A0008 | rank/gate; cadência Epic Fight | não integrar | não integrar | Backlash/cast não recebe cadência | companion não herda cadência | sem mudança |
| A0009 | crítico canônico/root action | não integrar | não integrar | Backlash fora do resolver | companion fora do resolver | **Hook/Fallback/Regra corrigidos** |
| A0010 | Fúria/target state/dedup | não integrar | não integrar | Backlash nunca gera Fúria/Mastery | companion nunca gera Fúria do dono | **Hook/Fallback/Regra corrigidos** |

## Alterações canônicas no Notion

Foram alteradas exatamente seis perks: **A0003, A0004, A0005, A0006, A0009 e A0010**.

Para elas, `Hook`, `Fallback` e `Regra` foram endurecidos para registrar:

- autoria direta/root action do jogador;
- `ARCANE_BACKLASH` terminal e inelegível;
- ally/bodyguard Mobstein como origem própria, não ataque do dono;
- em A0005, Arcane Resistance/Corruption Resistance/Strain/Backlash e Shroud/Exposure não são defesa física/guarda.

Re-fetch pós-escrita realizado em 2026-08-30: **6/6 PASS**, com persistência confirmada.

A0001, A0002, A0007 e A0008 não receberam mutação artificial: seus contratos já exigiam ação direta e classificação/evento provider-native suficiente para o boundary.

## Authority, causalidade e deduplicação

- **Authority principal das perks:** RPG Skill Tree para rank/estado/resolução própria; Epic Fight para categoria/receipts marciais.
- **Black Arcana:** conserva authority do Arcane Danger/Backlash; o RPG apenas rejeita provenance terminal para este lote.
- **Mobstein:** conserva authority do ataque dos próprios companions; ownership não converte automaticamente dano de companion em ação direta do jogador.
- **Enshrouded/Volcanoes:** nenhuma escrita ou conversão de seus estados ocorre neste lote.
- **Deduplicação:** manter `rootActionId`/claim canônico; bridges futuras não podem gerar segundo crítico, segundo ganho de Ímpeto/Fúria ou Mastery duplicada.

## Testes obrigatórios derivados para Chat 2/manutenção

O design retroativo não exige alteração runtime nesta PR, mas qualquer adapter futuro deve provar:

1. Backlash não entra em A0001/A0003/A0004/A0005/A0006/A0007/A0009/A0010;
2. dano de Mobstein companion não entra no root action do dono e não gera crítico/Ímpeto/Riposta/Fúria/Mastery do dono;
3. ataque direto do jogador contra alvo Black Arcana/Mobstein continua funcionando normalmente;
4. Arcane Resistance/Corruption Resistance/Strain, Enshrouded `MagicResistanceService`, Shroud/Exposure e Volcanoes environmental protection não qualificam o fallback físico de A0005;
5. ausência de provenance/receipt seguro permanece fail-closed.

## Resultado

**A0001–A0010: 10/10 permanecem APROVADAS/FECHADAS NO DESIGN após auditoria retroativa dos cinco providers.**

A retroauditoria encontrou integrações legítimas de **boundary e exclusão causal**, não novos bônus/bridges. Nenhuma perk foi redesenhada numericamente, nenhum provider temático foi adicionado artificialmente e nenhum runtime planejado foi promovido a disponível.

O fechamento operacional deste ciclo depende da PR documental ficar verde, ser mergeada em `main` e da confirmação do SHA pós-merge. O ciclo deve parar em A0010.
