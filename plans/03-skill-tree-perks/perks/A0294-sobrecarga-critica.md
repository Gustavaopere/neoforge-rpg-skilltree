# A0294 — Sobrecarga Crítica

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO COM BLOQUEIO DE CAPABILITY.
- **Disponibilidade operacional:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0294 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db81e19412c05fefd82fb4).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** <code>A0176</code>. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** LIGHTNING exige ação/componente explícito. Não equivale a FE, energia tecnológica, Oritech/Create New Age, tempestade visual, WET, velocidade ou eletricidade estética.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0294 |
| Nome | Sobrecarga Crítica |
| Domínio | ARCANE/LIGHTNING |
| Árvore | Especialista — Raio |
| Ramo | Eletromancia — Carregado e Sobrecarga |
| Camada | 4 |
| Função na Árvore | Notable |
| Tier | Médio |
| Faixa de Poder | Transformativo |
| Ranks Máx. | 1 |
| Custo por Rank | 3 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | SPECIALIST_UNLOCK:LIGHTNING confirmado por Gate A/B/C + A0284 Carga =3 ranks + A0290 Crítico Condutor ≥1 rank. Requisitos locais não substituem fundamentos LIGHTNING + ≥100 PP válidos em SPECIALIST_REGION:LIGHTNING + terminal A0176. |
| Pré-requisitos | Specialist Raio desbloqueada (SPECIALIST_UNLOCK:LIGHTNING) + A0284 Carga 3/3 + A0290 Crítico Condutor ≥1. |
| Provider/Mods | RPG Skill Tree + SPECIALIST_GATE_RESOLVER_V1 + CHARGED_STATE_LEDGER_V1 + sistema crítico canônico + DERIVED_COMBAT_OUTCOME_PIPELINE_V1 + FUTURE_PROVIDER_CONTRACT opcional POSTURE_PRESSURE_V1. Epic Fight 21.17.3.1/outros providers só participam da parcela opcional de postura quando adapter expuser unidade, target, escala e commit point determinísticos; ausência disso não afeta o dano LIGHTNING principal. CHARGED permanece estado interno do RPG por owner/alvo. |
| Efeito | Quando um direct_lightning_outcome_id crítico confirmado atinge um alvo que possui pelo menos 3 stacks válidas de CHARGED atribuídas ao mesmo jogador, A0294 pode consumir atomicamente exatamente 3 dessas stacks e emitir um componente LIGHTNING derivado adicional com base igual a 24% do componente LIGHTNING direto canônico da ação após modificadores ofensivos e resolução crítica, mas antes da mitigação do alvo. O componente derivado passa pela mitigação LIGHTNING do alvo normalmente. Pressão de postura só existe se um adapter versionado expuser unidade/commit point determinísticos. |
| Escalonamento | 1 rank. Consumo obrigatório: 3 stacks CHARGED próprias e não expiradas. Dano derivado: 0,24 × direct_lightning_component_snapshot (=8% por cada uma das 3 stacks consumidas). criticalHit=false. Qualquer parcela de postura/interrupção é componente opcional independente e deve possuir valor/unidade definidos pelo adapter; ausência desse contrato omite apenas postura. |
| Gate | SPECIALIST_UNLOCK:LIGHTNING válido + A0284=3 + A0290≥1 + direct_lightning_outcome_id com criticalHit=true canônico + componente LIGHTNING direto positivo + alvo contendo ≥3 stacks CHARGED válidas do MESMO owner_uuid + claim A0294 livre para o root_action_id. Stacks expiradas/de outro owner, hop/branch derivado, DoT, aura/campo, summon, automação, fake player e callback duplicado não satisfazem. |
| Hook | No commit do crítico direto, reservar atomicamente as 3 stacks próprias mais antigas e snapshotar a parcela LIGHTNING direta após modificadores ofensivos/crítico e ANTES da mitigação do alvo. Preparar derived_lightning_overload com base snapshot×0,24, criticalHit=false, identidade root_action_id+A0294+target_uuid. Somente se a emissão/commit do outcome derivado puder prosseguir, consumir exatamente as 3 stacks reservadas e aplicar a mitigação LIGHTNING do alvo ao derivado. Falha antes do ponto irreversível libera a reserva sem consumo. POSTURE_PRESSURE_V1, quando presente, é operação separada após commit e nunca fabrica Stun. |
| Fallback | FAIL-CLOSED se Specialist Raio não estiver desbloqueada, CHARGED não possuir autoria/claim atômico ou o pipeline derivado não for seguro; nesse caso não consumir stacks. Se somente postura estiver indisponível, omitir postura e preservar o overload LIGHTNING. Não inferir stacks visualmente, não usar dano pós-mitigação como base, não criar Stun genérico e não aplicar payoff parcial sem consumo atômico. |
| Regra | COUNTS_AS_SPECIALIST_PERK:LIGHTNING=YES. Notable interna da Specialist Raio. A0294 consome exatamente 3 stacks CHARGED próprias por ativação; um root_action_id ativa no máximo uma vez. O derived overload não critica, não encadeia/ramifica, não rerrola A0284/A0294, não reaplica CHARGED, não gera Lightning Mastery, mana ou sustain. Reserva→commit→consumo deve impedir perda sem payoff e duplicação por callback. Gate A/B/C permanece obrigatório durante ownership/respec. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

Quando um direct_lightning_outcome_id crítico confirmado atinge um alvo que possui pelo menos 3 stacks válidas de CHARGED atribuídas ao mesmo jogador, A0294 pode consumir atomicamente exatamente 3 dessas stacks e emitir um componente LIGHTNING derivado adicional com base igual a 24% do componente LIGHTNING direto canônico da ação após modificadores ofensivos e resolução crítica, mas antes da mitigação do alvo. O componente derivado passa pela mitigação LIGHTNING do alvo normalmente. Pressão de postura só existe se um adapter versionado expuser unidade/commit point determinísticos.

### Escalonamento aprovado

1 rank. Consumo obrigatório: 3 stacks CHARGED próprias e não expiradas. Dano derivado: 0,24 × direct_lightning_component_snapshot (=8% por cada uma das 3 stacks consumidas). criticalHit=false. Qualquer parcela de postura/interrupção é componente opcional independente e deve possuir valor/unidade definidos pelo adapter; ausência desse contrato omite apenas postura.

### Gate de compra/ativação

SPECIALIST_UNLOCK:LIGHTNING válido + A0284=3 + A0290≥1 + direct_lightning_outcome_id com criticalHit=true canônico + componente LIGHTNING direto positivo + alvo contendo ≥3 stacks CHARGED válidas do MESMO owner_uuid + claim A0294 livre para o root_action_id. Stacks expiradas/de outro owner, hop/branch derivado, DoT, aura/campo, summon, automação, fake player e callback duplicado não satisfazem.

### Hook e ordem de execução

No commit do crítico direto, reservar atomicamente as 3 stacks próprias mais antigas e snapshotar a parcela LIGHTNING direta após modificadores ofensivos/crítico e ANTES da mitigação do alvo. Preparar derived_lightning_overload com base snapshot×0,24, criticalHit=false, identidade root_action_id+A0294+target_uuid. Somente se a emissão/commit do outcome derivado puder prosseguir, consumir exatamente as 3 stacks reservadas e aplicar a mitigação LIGHTNING do alvo ao derivado. Falha antes do ponto irreversível libera a reserva sem consumo. POSTURE_PRESSURE_V1, quando presente, é operação separada após commit e nunca fabrica Stun.

### Fallback sem trocar a identidade

FAIL-CLOSED se Specialist Raio não estiver desbloqueada, CHARGED não possuir autoria/claim atômico ou o pipeline derivado não for seguro; nesse caso não consumir stacks. Se somente postura estiver indisponível, omitir postura e preservar o overload LIGHTNING. Não inferir stacks visualmente, não usar dano pós-mitigação como base, não criar Stun genérico e não aplicar payoff parcial sem consumo atômico.

### Invariantes semânticos

- LIGHTNING exige ação/componente explícito. Não equivale a FE, energia tecnológica, Oritech/Create New Age, tempestade visual, WET, velocidade ou eletricidade estética.
- Separa root action, hops nativos/derivados, CHARGED, dodge, dano, resistência e tecnologia elétrica.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Especialista — Raio / Eletromancia — Carregado e Sobrecarga |
| Camada e papel | Camada 4; Notable |
| Pré-requisito visual/estrutural | Specialist Raio desbloqueada (SPECIALIST_UNLOCK:LIGHTNING) + A0284 Carga 3/3 + A0290 Crítico Condutor ≥1. |
| Dependência semântica completa | SPECIALIST_UNLOCK:LIGHTNING confirmado por Gate A/B/C + A0284 Carga =3 ranks + A0290 Crítico Condutor ≥1 rank. Requisitos locais não substituem fundamentos LIGHTNING + ≥100 PP válidos em SPECIALIST_REGION:LIGHTNING + terminal A0176. |
| Custo topológico | 3 PP por rank; 1 rank(s); extra 0 |
| Regra de região/PP | COUNTS_AS_SPECIALIST_PERK:LIGHTNING=YES. Notable interna da Specialist Raio. A0294 consome exatamente 3 stacks CHARGED próprias por ativação; um root_action_id ativa no máximo uma vez. O derived overload não critica, não encadeia/ramifica, não rerrola A0284/A0294, não reaplica CHARGED, não gera Lightning Mastery, mana ou sustain. Reserva→commit→consumo deve impedir perda sem payoff e duplicação por callback. Gate A/B/C permanece obrigatório durante ownership/respec. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

RPG Skill Tree + SPECIALIST_GATE_RESOLVER_V1 + CHARGED_STATE_LEDGER_V1 + sistema crítico canônico + DERIVED_COMBAT_OUTCOME_PIPELINE_V1 + FUTURE_PROVIDER_CONTRACT opcional POSTURE_PRESSURE_V1. Epic Fight 21.17.3.1/outros providers só participam da parcela opcional de postura quando adapter expuser unidade, target, escala e commit point determinísticos; ausência disso não afeta o dano LIGHTNING principal. CHARGED permanece estado interno do RPG por owner/alvo.

### Disposição por família

- **Providers/mods pertinentes:** Iron's Spells e Ars Nouveau/Ars Elemental entram por classificadores LIGHTNING. Epic Fight/ParCool só fornecem receipts de dodge/estamina quando semanticamente equivalentes; Sable/Aeronautics apenas resolvem espaço.
- **Exclusões obrigatórias:** WET exige WET_STATE_V1; chain exige contexto/visited set; mana, stamina, FE e outros recursos não são intercambiáveis. Black Arcana não fornece LIGHTNING nesta faixa.
- **Contratos/capabilities nomeados no registro:** <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>POSTURE_PRESSURE_V1</code>.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0294 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c92304bb22c0c5cec3358a6cc6bc0dbb24cc15c9</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR — nenhuma capability do head auditado publica a assinatura/receipt exigida por esta perk. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>action_id</code>, <code>outcome_id</code>, <code>root_action_id</code>, <code>target_uuid</code>, <code>ledger</code>, <code>claim</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>action_id</code>, <code>outcome_id</code>, <code>root_action_id</code>, <code>target_uuid</code>, <code>ledger</code>, <code>claim</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0294.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 3 Passive Point(s) por rank, máximo de 1 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>MANA</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Fallback normativo:** FAIL-CLOSED se Specialist Raio não estiver desbloqueada, CHARGED não possuir autoria/claim atômico ou o pipeline derivado não for seguro; nesse caso não consumir stacks. Se somente postura estiver indisponível, omitir postura e preservar o overload LIGHTNING. Não inferir stacks visualmente, não usar dano pós-mitigação como base, não criar Stun genérico e não aplicar payoff parcial sem consumo atômico.
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
- Iron's Spells e Ars Nouveau/Ars Elemental entram por classificadores LIGHTNING. Epic Fight/ParCool só fornecem receipts de dodge/estamina quando semanticamente equivalentes; Sable/Aeronautics apenas resolvem espaço.
- WET exige WET_STATE_V1; chain exige contexto/visited set; mana, stamina, FE e outros recursos não são intercambiáveis. Black Arcana não fornece LIGHTNING nesta faixa.
- Relações somente temáticas foram deliberadamente recusadas; cada provider listado possui papel limitado ao subcontrato descrito.
- Provider não listado não é automaticamente incompatível: ele só entra futuramente após adapter versionado, classificação explícita, authority definida, testes e atualização do catálogo/dossiê.
- NeoVitae não é requisito, provider, fallback nem authority desta perk.

## 11. Plano obrigatório de testes para o Chat 2

1. **Compra válida:** provar ranks, PP, dependencies, mastery/gateway e provider/capability; então comprar exatamente até o máximo.
2. **Compra inválida:** faltar cada requisito isoladamente e comprovar recusa sem gasto, estado ou unlock residual.
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de 1 rank. Consumo obrigatório: 3 stacks CHARGED próprias e não expiradas. Dano derivado: 0,24 × direct_lightning_component_snapshot (=8% por cada uma das 3 stacks consumidas). criticalHit=false. Qualquer parcela de postura/interrupção é componente opcional independente e deve possuir valor/unidade definidos pelo adapter; ausência desse contrato omite apenas postura..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>action_id</code>, <code>outcome_id</code>, <code>root_action_id</code>, <code>target_uuid</code>, <code>ledger</code>, <code>claim</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
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
- **Capabilities/contracts a provar:** <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>POSTURE_PRESSURE_V1</code>.
- **Dependências fora desta faixa:** <code>A0176</code>.
- **Referências internas posteriores:** nenhuma.
- **Referência além do escopo:** nenhuma além das dependências listadas.
- Estas pendências não autorizam redesign silencioso. Se API/código real contradizer o contrato, implementar fail-closed e devolver o ponto ao Chat 1.

## 14. Auditoria dos nove eixos obrigatórios

| Eixo | Veredito e evidência |
|---|---|
| 1. Dependências e gates | **APROVADO NO DESIGN** — dependências, pré-requisitos, gate, PP/Mastery e comportamento na ausência estão explícitos. |
| 2. Integração global | **APROVADO NO DESIGN** — provider/modlist, authority, projetos próprios e exclusões foram dispostos; WET exige WET_STATE_V1; chain exige contexto/visited set; mana, stamina, FE e outros recursos não são intercambiáveis. Black Arcana não fornece LIGHTNING nesta faixa. |
| 3. Qualidade e identidade | **APROVADO** — LIGHTNING exige ação/componente explícito. Não equivale a FE, energia tecnológica, Oritech/Create New Age, tempestade visual, WET, velocidade ou eletricidade estética. |
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
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>action_id</code>, <code>outcome_id</code>, <code>root_action_id</code>, <code>target_uuid</code>, <code>ledger</code>, <code>claim</code>. |
| 9 | Sem pipelines duplicados | **APROVADO** — um owner, um bucket/ledger/commit e composição única. |
| 10 | Custos/recursos reais | **APROVADO** — PP e recursos nativos usam débito/quantum/provider reais. |
| 11 | Sem geração gratuita | **APROVADO/NÃO APLICÁVEL** — não há recurso grátis; ganho/refund eventual exige receipt e mesmo resource_id. |
| 12 | Read-only verdadeiro | **APROVADO/NÃO APLICÁVEL** — queries/forecast são read-only; mutações só pela operação pública do owner. |
| 13 | Versões exatas | **APROVADO NO DESIGN** — versões externas permanecem no campo Provider/Mods; projetos próprios estão pinados por SHA. |
| 14 | Coerência estrutural | **APROVADO** — domínio, árvore, ramo, camada, função e custo são coerentes. |
| 15 | Dependências semânticas | **APROVADO** — closure e upstream/future refs estão explícitos e não presumidos. |
| 16 | Sem sobreposição/double-dip | **APROVADO** — Separa root action, hops nativos/derivados, CHARGED, dodge, dano, resistência e tecnologia elétrica. |
| 17 | Implementável | **APROVADO COMO CONTRATO** — hook, estado, owner, fallback e testes estão fechados; capability futura bloqueia runtime sem bloquear o design. |
| 18 | Pós-escrita relido | **APROVADO** — registro individual foi relido após Custo Extra=0; nenhum sucesso foi presumido. |

## 16. Evidência de persistência no Notion

- Página: [A0294 — Sobrecarga Crítica](https://app.notion.com/3c569db9f0db81e19412c05fefd82fb4)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0294:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
