# A0263 — Inferno Ambulante

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO COM BLOQUEIO DE CAPABILITY.
- **Disponibilidade operacional:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0263 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db8111a77dd0c5b8637755).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** <code>A0162</code>. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** FIRE exige ação/componente explícito. Não equivale a lava, calor corporal, temperatura ambiente, bloco em chamas, combustão visual, FE ou vulcanismo.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0263 |
| Nome | Inferno Ambulante |
| Domínio | ARCANE/FIRE |
| Árvore | Especialista — Fogo |
| Ramo | Piromancia — Capstone |
| Camada | 8 |
| Função na Árvore | Capstone |
| Tier | Grande |
| Faixa de Poder | Apex |
| Ranks Máx. | 1 |
| Custo por Rank | 5 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | SPECIALIST_UNLOCK:FIRE confirmado server-side por Gate A/B/C + A0262 Coração de Magma + pelo menos 2 Notables ofensivos entre A0253 Propagação de Chamas, A0254 Aura de Brasas, A0257 Marca Carbonizada e A0258 Combustão Súbita + ≥18 pontos gastos na árvore interna Especialista — Fogo + Fire Mastery ≥180. Os ≥18 PP internos e requisitos locais NÃO substituem Gate B global (≥100 PP válidos em SPECIALIST_REGION:FIRE) nem fundamentos/terminal A0162. |
| Pré-requisitos | Specialist Fire desbloqueada (SPECIALIST_UNLOCK:FIRE) + A0262 + pelo menos 2 entre A0253/A0254/A0257/A0258 + ≥18 PP internos em Especialista — Fogo + Fire Mastery ≥180. |
| Provider/Mods | RPG Skill Tree + pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01 + ledger RPG_FIRE_MOMENTUM de A0246/A0262 + classificador canônico de ações FIRE diretas + pipeline causal de outcomes derivados + consulta espacial/targetability server-side + movimento Minecraft/NeoForge. Iron's Spells 'n Spellbooks runtime 1.21.1-3.16.3, Ars Nouveau 5.13.1/Ars Elemental 0.7.10.1, Somake Spells 1.0.8-1.21.1-fix, Legendary Spellbooks 0.3.2 e demais providers só alimentam o Capstone quando a ação FIRE concreta estiver mapeada por adapter explícito; nenhum deles é owner do gate, do Ímpeto ou dos pulses. FUTURE_PROVIDER_CONTRACT DERIVED_COMBAT_OUTCOME_PIPELINE_V1: OWNER RPG combat core; CONSUMER A0263; BEHAVIOR criar pulses/brasas com causalidade, targetability, PvP/aliança e dedupe preservados, sem rerodar multiplicadores/procs do caster. VERSION-STATUS: contrato formal não encontrado como API runtime na main auditada em 2026-08-29. |
| Efeito | Fora da recarga, quando o jogador estiver com 5 cargas de RPG_FIRE_MOMENTUM e uma nova ação FIRE direta ofensiva elegível ocorrer tendo existido ao menos uma ação FIRE direta ofensiva nos últimos 60 ticks (3 s), A0263 consome atomicamente todas as 5 cargas e ativa RPG_WALKING_INFERNO por 160 ticks (8 s). Durante a janela: componentes FIRE diretos do jogador recebem ×1,15 uma única vez; velocidade de movimento recebe +12%; a cada 20 ticks uma aura em raio de 4 blocos emite derived_fire_capstone_pulse contra até 6 inimigos vivos, cada pulso causando 10% de activation_reference_fire_damage; e cada ação FIRE direta pode emitir brasas para até 2 outros inimigos em até 4 blocos do alvo principal, cada derived_fire_capstone_ember causando 12% da parcela FIRE direta da ação-fonte. activation_reference_fire_damage é a parcela FIRE direta pré-mitigação-do-alvo/pré-crítico da ação ofensiva que ativou o Capstone. |
| Escalonamento | 1 rank. Requisito de atividade ofensiva recente: ação FIRE direta nos últimos 60 ticks. Consumo: 5 cargas de A0246. Estado: 160 ticks. Dano FIRE direto: ×1,15. Movimento: +12%. Aura: pulso a cada 20 ticks, raio 4, até 6 alvos, 10% da referência de ativação por alvo. Brasas: até 2 alvos secundários por ação FIRE direta, raio 4 do alvo principal, 12% da parcela FIRE direta pré-mitigação/pré-crítico da ação. Recarga: 900 ticks (45 s), iniciada na ativação. |
| Gate | SPECIALIST_UNLOCK:FIRE válido + requisitos locais + ≥18 PP internos FIRE + Fire Mastery ≥180 + recarga livre + exatamente 5 cargas válidas de A0246 + nova ação FIRE direta ofensiva elegível do jogador + pelo menos uma ação FIRE direta ofensiva nos últimos 60 ticks. A ativação exige commit real da nova ação. Cargas defensivas de A0262 podem completar o total de 5, mas não satisfazem atividade ofensiva. DoT, Aura, Propagação, Marca, Combustão Súbita, pulses/brasas derivados, summons, automação, fake player, derived_component e callback duplicado não satisfazem o gatilho. |
| Hook | No commit da ação FIRE direta que satisfaz o gate, deduplicar action_id/outcome_id, snapshotar a parcela FIRE direta pré-mitigação-do-alvo/pré-crítico como activation_reference_fire_damage, consumir atomicamente exatamente 5 cargas do ledger A0246 e criar RPG_WALKING_INFERNO com expiry=now+160 e cooldown_expiry=now+900. Durante a janela, aplicar ×1,15 somente a componentes FIRE diretos futuros do jogador e +12% de movimento. A cada 20 ticks, consultar alvos vivos realmente atacáveis em raio 4 e emitir até 6 derived_fire_capstone_pulse; em cada ação FIRE direta, selecionar até 2 alvos secundários realmente atacáveis em raio 4 do alvo principal e emitir derived_fire_capstone_ember. Respeitar PvP, alianças/ownership e invulnerabilidade do pipeline canônico. Identidade: pulse=inferno_instance_id+pulse_index+target_uuid; ember=parent_action_id+parent_outcome_id+secondary_target_uuid. Outcomes derivados preservam causalidade, mas não reentram nos multiplicadores/procs do caster. |
| Fallback | FAIL-CLOSED se Specialist Fire não estiver desbloqueada, o ledger/consumo de Ímpeto não for atômico, a classificação FIRE direta não for segura ou o pipeline de aura/consulta espacial causal não puder preservar targetability e dedupe; a presença ofensiva de campo faz parte da identidade do Capstone. Se somente a emissão de brasas secundárias estiver indisponível mas aura, estado e targetability forem seguros, preservar ×1,15 + movimento + aura e omitir apenas as brasas. Não substituir por Ignição, cura, resistência, mana, mastery, dano genérico ou bônus permanente. |
| Regra | COUNTS_AS_SPECIALIST_PERK:FIRE=YES. Capstone INTERNO da Specialist Fire; não é a terminal exterior A0162. Inferno Ambulante é estado temporário de CONSUMO de Ímpeto, não passivo permanente. Aura e brasas são outcomes derivados: não possuem crítico próprio, não chamam FIRE_IGNITION_RESOLVER_V1, não geram Fire Mastery, Ímpeto, Mana Incandescente, Propagação, Marca, Combustão Súbita, sustain, drops ou recursos e não podem reacender o próprio Capstone. Seleção de alvos deve respeitar as mesmas regras canônicas de attackability/PvP/aliança. Chefes usam as mesmas fórmulas. Cargas podem acumular durante estado/recarga pelas regras normais, mas nova ativação exige nova ação FIRE direta após a recarga. Morte/logout/dimensão/respec encerra o estado; Gate A/B/C permanece obrigatório durante ownership/respec. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

Fora da recarga, quando o jogador estiver com 5 cargas de RPG_FIRE_MOMENTUM e uma nova ação FIRE direta ofensiva elegível ocorrer tendo existido ao menos uma ação FIRE direta ofensiva nos últimos 60 ticks (3 s), A0263 consome atomicamente todas as 5 cargas e ativa RPG_WALKING_INFERNO por 160 ticks (8 s). Durante a janela: componentes FIRE diretos do jogador recebem ×1,15 uma única vez; velocidade de movimento recebe +12%; a cada 20 ticks uma aura em raio de 4 blocos emite derived_fire_capstone_pulse contra até 6 inimigos vivos, cada pulso causando 10% de activation_reference_fire_damage; e cada ação FIRE direta pode emitir brasas para até 2 outros inimigos em até 4 blocos do alvo principal, cada derived_fire_capstone_ember causando 12% da parcela FIRE direta da ação-fonte. activation_reference_fire_damage é a parcela FIRE direta pré-mitigação-do-alvo/pré-crítico da ação ofensiva que ativou o Capstone.

### Escalonamento aprovado

1 rank. Requisito de atividade ofensiva recente: ação FIRE direta nos últimos 60 ticks. Consumo: 5 cargas de A0246. Estado: 160 ticks. Dano FIRE direto: ×1,15. Movimento: +12%. Aura: pulso a cada 20 ticks, raio 4, até 6 alvos, 10% da referência de ativação por alvo. Brasas: até 2 alvos secundários por ação FIRE direta, raio 4 do alvo principal, 12% da parcela FIRE direta pré-mitigação/pré-crítico da ação. Recarga: 900 ticks (45 s), iniciada na ativação.

### Gate de compra/ativação

SPECIALIST_UNLOCK:FIRE válido + requisitos locais + ≥18 PP internos FIRE + Fire Mastery ≥180 + recarga livre + exatamente 5 cargas válidas de A0246 + nova ação FIRE direta ofensiva elegível do jogador + pelo menos uma ação FIRE direta ofensiva nos últimos 60 ticks. A ativação exige commit real da nova ação. Cargas defensivas de A0262 podem completar o total de 5, mas não satisfazem atividade ofensiva. DoT, Aura, Propagação, Marca, Combustão Súbita, pulses/brasas derivados, summons, automação, fake player, derived_component e callback duplicado não satisfazem o gatilho.

### Hook e ordem de execução

No commit da ação FIRE direta que satisfaz o gate, deduplicar action_id/outcome_id, snapshotar a parcela FIRE direta pré-mitigação-do-alvo/pré-crítico como activation_reference_fire_damage, consumir atomicamente exatamente 5 cargas do ledger A0246 e criar RPG_WALKING_INFERNO com expiry=now+160 e cooldown_expiry=now+900. Durante a janela, aplicar ×1,15 somente a componentes FIRE diretos futuros do jogador e +12% de movimento. A cada 20 ticks, consultar alvos vivos realmente atacáveis em raio 4 e emitir até 6 derived_fire_capstone_pulse; em cada ação FIRE direta, selecionar até 2 alvos secundários realmente atacáveis em raio 4 do alvo principal e emitir derived_fire_capstone_ember. Respeitar PvP, alianças/ownership e invulnerabilidade do pipeline canônico. Identidade: pulse=inferno_instance_id+pulse_index+target_uuid; ember=parent_action_id+parent_outcome_id+secondary_target_uuid. Outcomes derivados preservam causalidade, mas não reentram nos multiplicadores/procs do caster.

### Fallback sem trocar a identidade

FAIL-CLOSED se Specialist Fire não estiver desbloqueada, o ledger/consumo de Ímpeto não for atômico, a classificação FIRE direta não for segura ou o pipeline de aura/consulta espacial causal não puder preservar targetability e dedupe; a presença ofensiva de campo faz parte da identidade do Capstone. Se somente a emissão de brasas secundárias estiver indisponível mas aura, estado e targetability forem seguros, preservar ×1,15 + movimento + aura e omitir apenas as brasas. Não substituir por Ignição, cura, resistência, mana, mastery, dano genérico ou bônus permanente.

### Invariantes semânticos

- FIRE exige ação/componente explícito. Não equivale a lava, calor corporal, temperatura ambiente, bloco em chamas, combustão visual, FE ou vulcanismo.
- Separa dano FIRE, burn state, lava, temperatura corporal, world mutation, mana e derived outcomes.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Especialista — Fogo / Piromancia — Capstone |
| Camada e papel | Camada 8; Capstone |
| Pré-requisito visual/estrutural | Specialist Fire desbloqueada (SPECIALIST_UNLOCK:FIRE) + A0262 + pelo menos 2 entre A0253/A0254/A0257/A0258 + ≥18 PP internos em Especialista — Fogo + Fire Mastery ≥180. |
| Dependência semântica completa | SPECIALIST_UNLOCK:FIRE confirmado server-side por Gate A/B/C + A0262 Coração de Magma + pelo menos 2 Notables ofensivos entre A0253 Propagação de Chamas, A0254 Aura de Brasas, A0257 Marca Carbonizada e A0258 Combustão Súbita + ≥18 pontos gastos na árvore interna Especialista — Fogo + Fire Mastery ≥180. Os ≥18 PP internos e requisitos locais NÃO substituem Gate B global (≥100 PP válidos em SPECIALIST_REGION:FIRE) nem fundamentos/terminal A0162. |
| Custo topológico | 5 PP por rank; 1 rank(s); extra 0 |
| Regra de região/PP | COUNTS_AS_SPECIALIST_PERK:FIRE=YES. Capstone INTERNO da Specialist Fire; não é a terminal exterior A0162. Inferno Ambulante é estado temporário de CONSUMO de Ímpeto, não passivo permanente. Aura e brasas são outcomes derivados: não possuem crítico próprio, não chamam FIRE_IGNITION_RESOLVER_V1, não geram Fire Mastery, Ímpeto, Mana Incandescente, Propagação, Marca, Combustão Súbita, sustain, drops ou recursos e não podem reacender o próprio Capstone. Seleção de alvos deve respeitar as mesmas regras canônicas de attackability/PvP/aliança. Chefes usam as mesmas fórmulas. Cargas podem acumular durante estado/recarga pelas regras normais, mas nova ativação exige nova ação FIRE direta após a recarga. Morte/logout/dimensão/respec encerra o estado; Gate A/B/C permanece obrigatório durante ownership/respec. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

RPG Skill Tree + pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01 + ledger RPG_FIRE_MOMENTUM de A0246/A0262 + classificador canônico de ações FIRE diretas + pipeline causal de outcomes derivados + consulta espacial/targetability server-side + movimento Minecraft/NeoForge. Iron's Spells 'n Spellbooks runtime 1.21.1-3.16.3, Ars Nouveau 5.13.1/Ars Elemental 0.7.10.1, Somake Spells 1.0.8-1.21.1-fix, Legendary Spellbooks 0.3.2 e demais providers só alimentam o Capstone quando a ação FIRE concreta estiver mapeada por adapter explícito; nenhum deles é owner do gate, do Ímpeto ou dos pulses. FUTURE_PROVIDER_CONTRACT DERIVED_COMBAT_OUTCOME_PIPELINE_V1: OWNER RPG combat core; CONSUMER A0263; BEHAVIOR criar pulses/brasas com causalidade, targetability, PvP/aliança e dedupe preservados, sem rerodar multiplicadores/procs do caster. VERSION-STATUS: contrato formal não encontrado como API runtime na main auditada em 2026-08-29.

### Disposição por família

- **Providers/mods pertinentes:** Iron's Spells, Ars Nouveau/Ars Elemental, Somake e demais providers FIRE entram por adapters exatos; Minecraft/NeoForge, Cold Sweat e outros owners só participam no subcontrato nativo explicitamente citado.
- **Exclusões obrigatórias:** Volcanoes conserva geologia, vulcanismo, atmosfera e pressão e não é classificador FIRE mágico. Black Arcana danger/black flame planejada não é provider atual; Enshrouded não entra.
- **Contratos/capabilities nomeados no registro:** <code>pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0263 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c92304bb22c0c5cec3358a6cc6bc0dbb24cc15c9</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR NESTE CONTRATO — geologia/vulcanismo/atmosfera continuam authority do Volcanoes; não inferir EARTH/FIRE mágico por tema, lava ou calor. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>action_id</code>, <code>outcome_id</code>, <code>target_uuid</code>, <code>ledger</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>action_id</code>, <code>outcome_id</code>, <code>target_uuid</code>, <code>ledger</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0263.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 5 Passive Point(s) por rank, máximo de 1 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>MANA</code>, <code>RESOURCE_ID NATIVO</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Fallback normativo:** FAIL-CLOSED se Specialist Fire não estiver desbloqueada, o ledger/consumo de Ímpeto não for atômico, a classificação FIRE direta não for segura ou o pipeline de aura/consulta espacial causal não puder preservar targetability e dedupe; a presença ofensiva de campo faz parte da identidade do Capstone. Se somente a emissão de brasas secundárias estiver indisponível mas aura, estado e targetability forem seguros, preservar ×1,15 + movimento + aura e omitir apenas as brasas. Não substituir por Ignição, cura, resistência, mana, mastery, dano genérico ou bônus permanente.
- Reavaliar gate/availability em login, load/save, datapack/rules reload, respec, mudança/remoção de provider, alteração de capability e migração de schema.
- Perda de provider ou dependência remove/desativa somente a parcela dependente e executa cleanup do estado próprio, sem tocar estado autoritativo de terceiros.
- Estado desconhecido ou erro de query nunca concede compra, unlock, proteção, dano, recurso, temperatura favorável ou progressão.
- Cliente, HUD, tooltip, forecast e animação são apresentação/read-only; o servidor confirma toda decisão jogável.

## 9. Mastery, anti-farm e anti-rebuild

- Mastery usada em qualquer gate vem apenas de ação discreta, atribuível e confirmada do lane/provider correto.
- Tick, AFK, equipamento vestido, aura passiva, permanência em área, temperatura, mineração/automação, throughput de máquina e callback visual concedem 0 Mastery.
- Um action_id/outcome_id/receipt concede no máximo um crédito no consumidor autorizado; procs derivados não voltam ao produtor de Mastery.
- Rebuild, relog, restart, chunk unload, troca de dimensão e respec não recriam stacks, refunds, one-time rewards ou cooldowns consumidos.
- Dependências/PP são recalculados a partir do estado autoritativo; caches não mantêm unlock órfão.
- Quando a ação não puder ser atribuída inequivocamente ao jogador, a contribuição é zero.

## 10. Integração global e cobertura da modlist

- A busca de providers considera a modlist e os guias completos de gameplay, magia, tecnologia e projetos próprios.
- Iron's Spells, Ars Nouveau/Ars Elemental, Somake e demais providers FIRE entram por adapters exatos; Minecraft/NeoForge, Cold Sweat e outros owners só participam no subcontrato nativo explicitamente citado.
- Volcanoes conserva geologia, vulcanismo, atmosfera e pressão e não é classificador FIRE mágico. Black Arcana danger/black flame planejada não é provider atual; Enshrouded não entra.
- Relações somente temáticas foram deliberadamente recusadas; cada provider listado possui papel limitado ao subcontrato descrito.
- Provider não listado não é automaticamente incompatível: ele só entra futuramente após adapter versionado, classificação explícita, authority definida, testes e atualização do catálogo/dossiê.
- NeoVitae não é requisito, provider, fallback nem authority desta perk.

## 11. Plano obrigatório de testes para o Chat 2

1. **Compra válida:** provar ranks, PP, dependencies, mastery/gateway e provider/capability; então comprar exatamente até o máximo.
2. **Compra inválida:** faltar cada requisito isoladamente e comprovar recusa sem gasto, estado ou unlock residual.
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de 1 rank. Requisito de atividade ofensiva recente: ação FIRE direta nos últimos 60 ticks. Consumo: 5 cargas de A0246. Estado: 160 ticks. Dano FIRE direto: ×1,15. Movimento: +12%. Aura: pulso a cada 20 ticks, raio 4, até 6 alvos, 10% da referência de ativação por alvo. Brasas: até 2 alvos secundários por ação FIRE direta, raio 4 do alvo principal, 12% da parcela FIRE direta pré-mitigação/pré-crítico da ação. Recarga: 900 ticks (45 s), iniciada na ativação..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>action_id</code>, <code>outcome_id</code>, <code>target_uuid</code>, <code>ledger</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
7. **Composição:** provar ordem com bônus/mitigadores distintos e ausência de bucket/crit/dano/estado duplicado.
8. **Rollback:** cancelar/falhar antes do commit e verificar ausência de custo, consumo, cooldown, stack ou benefício fantasma.
9. **Recursos:** quando aplicável, confirmar débito/regen/refund somente no mesmo recurso e quantum; provider ausente gera zero.
10. **Lifecycle:** save/load, relog, restart, respec, datapack reload e remoção/reentrada do provider.
11. **Exploit:** AFK, tick spam, equipamento, automação, multi-projectile, DoT, summon, rebuild e dupla emissão de evento.
12. **Projetos próprios:** confirmar que Volcanoes, Enshrouded e Black Arcana permanecem fora salvo boundary explicitamente aprovado; ausência não impede o restante independente.
13. **Topologia/Specialist:** validar PP regionais, terminal, fundamentals, bridges e refund order sem border hopping.
14. **Fallback:** remover cada capability indicada e comprovar exatamente o comportamento fail-closed descrito, nunca um bônus substituto.

## 12. Proibições de implementação

- Não inventar evento, capability, classificador, state id, resource id, metric ou API.
- Não duplicar dano, mitigação, resistência, crítico, freeze, temperatura, world mutation, resource ledger ou progressão owned por provider.
- Não usar client/tooltip/forecast/animação como authority.
- Não inferir provider por nome, namespace, dimensão, bioma, bloco, item, cor, VFX ou tema.
- Não trocar fallback por bônus vanilla/genérico parecido.
- Não conceder Mastery/proc/sustain a partir de Backlash, derived outcome não autorizado ou callback intermediário.
- Não escrever diretamente internals de Cold Sweat, Black Arcana, Enshrouded, Volcanoes, claims, recursos mágicos ou estados de outros mods.
- Não adicionar dependência de NeoVitae.

## 13. Pendências técnicas e dependências futuras

- **Implementação:** não confirmada neste trabalho; responsabilidade futura do Chat 2.
- **Capabilities/contracts a provar:** <code>pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>.
- **Dependências fora desta faixa:** <code>A0162</code>.
- **Referências internas posteriores:** nenhuma.
- **Referência além do escopo:** nenhuma além das dependências listadas.
- Estas pendências não autorizam redesign silencioso. Se API/código real contradizer o contrato, implementar fail-closed e devolver o ponto ao Chat 1.

## 14. Auditoria dos nove eixos obrigatórios

| Eixo | Veredito e evidência |
|---|---|
| 1. Dependências e gates | **APROVADO NO DESIGN** — dependências, pré-requisitos, gate, PP/Mastery e comportamento na ausência estão explícitos. |
| 2. Integração global | **APROVADO NO DESIGN** — provider/modlist, authority, projetos próprios e exclusões foram dispostos; Volcanoes conserva geologia, vulcanismo, atmosfera e pressão e não é classificador FIRE mágico. Black Arcana danger/black flame planejada não é provider atual; Enshrouded não entra. |
| 3. Qualidade e identidade | **APROVADO** — FIRE exige ação/componente explícito. Não equivale a lava, calor corporal, temperatura ambiente, bloco em chamas, combustão visual, FE ou vulcanismo. |
| 4. Topologia/distância/ramificação | **APROVADO** — árvore, ramo, camada, função, região de PP e border-hopping estão documentados. |
| 5. Especializações | **APROVADO** — terminal/fundamentals/PP regionais são preservados quando aplicáveis; nodes externos/bridges não viram perks internas. |
| 6. PT-BR | **APROVADO** — nome, efeito, gate, hook, fallback, testes e proibições estão em PT-BR. |
| 7. Campos completos no Notion | **APROVADO APÓS CORREÇÃO** — 21 propriedades materiais preenchidas; Custo Extra=0 relido após escrita. |
| 8. Ausência de NeoVitae | **APROVADO** — sem dependência, provider, fallback ou referência operacional. |
| 9. Providers e projetos próprios | **APROVADO NO DESIGN / FAIL-CLOSED NO RUNTIME** — cobertura perk→provider e provider→árvore explícita; capability ausente não é inventada. |

## 15. Auditoria dos 18 critérios técnicos

| # | Critério | Veredito |
|---:|---|---|
| 1 | Efeito real | **APROVADO NO DESIGN** — efeito mensurável, coeficiente/rank e alvo de composição definidos. |
| 2 | Provider-native first | **APROVADO** — authority permanece no provider; RPG consome boundary/receipt. |
| 3 | Sem mecânica inventada | **APROVADO** — contratos futuros estão nomeados e bloqueados, não declarados como API existente. |
| 4 | Fail-closed | **APROVADO** — ausência/unknown/erro produz zero ou node não comprável conforme o fallback. |
| 5 | Fallback preserva identidade | **APROVADO** — não converte a perk em bônus genérico ou outro eixo. |
| 6 | Mastery discreta/atribuível | **APROVADO** — somente ação confirmada do lane correto; passividade concede zero. |
| 7 | Anti-farm/anti-rebuild | **APROVADO** — dedup, lifecycle e persistência impedem AFK/relog/rebuild. |
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>action_id</code>, <code>outcome_id</code>, <code>target_uuid</code>, <code>ledger</code>. |
| 9 | Sem pipelines duplicados | **APROVADO** — um owner, um bucket/ledger/commit e composição única. |
| 10 | Custos/recursos reais | **APROVADO** — PP e recursos nativos usam débito/quantum/provider reais. |
| 11 | Sem geração gratuita | **APROVADO/NÃO APLICÁVEL** — não há recurso grátis; ganho/refund eventual exige receipt e mesmo resource_id. |
| 12 | Read-only verdadeiro | **APROVADO/NÃO APLICÁVEL** — queries/forecast são read-only; mutações só pela operação pública do owner. |
| 13 | Versões exatas | **APROVADO NO DESIGN** — versões externas permanecem no campo Provider/Mods; projetos próprios estão pinados por SHA. |
| 14 | Coerência estrutural | **APROVADO** — domínio, árvore, ramo, camada, função e custo são coerentes. |
| 15 | Dependências semânticas | **APROVADO** — closure e upstream/future refs estão explícitos e não presumidos. |
| 16 | Sem sobreposição/double-dip | **APROVADO** — Separa dano FIRE, burn state, lava, temperatura corporal, world mutation, mana e derived outcomes. |
| 17 | Implementável | **APROVADO COMO CONTRATO** — hook, estado, owner, fallback e testes estão fechados; capability futura bloqueia runtime sem bloquear o design. |
| 18 | Pós-escrita relido | **APROVADO** — registro individual foi relido após Custo Extra=0; nenhum sucesso foi presumido. |

## 16. Evidência de persistência no Notion

- Página: [A0263 — Inferno Ambulante](https://app.notion.com/3c569db9f0db8111a77dd0c5b8637755)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0263:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
