# A0224 — Domínio Fluido

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO COM BLOQUEIO DE CAPABILITY.
- **Disponibilidade operacional:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0224 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db81c7bb02f10e55efa481).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** nenhuma dependência fora de A0200–A0299. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** WATER exige ação/componente explicitamente classificado. Não equivale a chuva, submersão, bloco de água, WET, frio, natação ou estética aquática.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0224 |
| Nome | Domínio Fluido |
| Domínio | ARCANE |
| Árvore | Principal — ARCANE ↔ AGILITY |
| Ramo | Água — Domínio Fluido |
| Camada | 6 |
| Função na Árvore | Keystone |
| Tier | Médio |
| Faixa de Poder | Transformativo |
| Ranks Máx. | 1 |
| Custo por Rank | 2 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | A0219 Dano de Água I ≥ 3 ranks + Water Mastery ≥ 30 + pelo menos um dos seguintes: A0220 = 1 rank, A0222 = 1 rank ou A0223 ≥ 1 rank. Exige uso real de WATER; ambiente aquático não substitui domínio. |
| Pré-requisitos | A0219 Dano de Água I ≥ 3 ranks + pelo menos um caminho secundário entre A0220/A0222/A0223. |
| Provider/Mods | RPG Skill Tree + Somake Spells 1.0.8-1.21.1-fix (Aqua School)/Iron's Spells runtime 1.21.1-3.16.3 e demais ações WATER somente por adapters explícitos + Water Mastery canônica + FUTURE_PROVIDER_CONTRACT RESOURCE_DEBIT_RECEIPT_V1. OWNER: provider do recurso/adapter versionado; CONSUMER: A0224; STATE: receipt imutável {action_id, resource_id, final_debited_amount, quantum, claimed}; EVENT: pós-débito confirmado; BEHAVIOR: claim único devolvendo fração quantizada ao exato resource_id debitado; VERSION-STATUS: design congelado, contrato não encontrado no runtime/main atual em 2026-08-29. |
| Efeito | Aplicar um water_state_id real por ação WATER elegível e depois concluir uma ação WATER direta distinta dentro de 120 ticks (6 s) arma RPG_FLUID_CURRENT por 160 ticks (8 s). A próxima conjuração WATER bem-sucedida só consome Corrente Fluida após o custo nativo final daquela própria ação ter sido efetivamente debitado. Com ResourceDebitReceipt causal e reclamável uma única vez, devolver refund = quantize_down(0,15 × final_debited_amount, unidade_mínima_do_provider) no MESMO recurso nativo, limitado ao valor realmente debitado. |
| Escalonamento | 1 rank. Janela de sequência: 120 ticks; RPG_FLUID_CURRENT: 160 ticks. Reembolso: 15% do débito final real da ação beneficiada, no mesmo recurso nativo, quantizado conforme o provider e nunca maior que o débito. Um receipt pode ser reclamado no máximo uma vez. |
| Gate | A0219 ≥3 + Water Mastery ≥30 + caminho secundário exigido + aplicação WATER real seguida de ação WATER direta distinta em até 120 ticks. Consumo exige cast WATER bem-sucedido com ResourceDebitReceipt pós-débito contendo recurso, amount final positivo e identidade da mesma ação. Chuva, imersão, automação, polling de barra, custo teórico, diferença de recurso ou receipt de outra ação são inelegíveis. |
| Hook | Registrar a sequência WATER e armar Corrente. Na próxima ação WATER, aguardar commit e receipt do custo final nativo; validar action_id, resource_id, amount e claim único; devolver 15% quantizado no mesmo resource_id, então consumir Corrente e marcar o receipt como reclamado. Se o cast for cancelado ou débito final for zero, não há reembolso nem consumo. |
| Fallback | FAIL-CLOSED: sem RESOURCE_DEBIT_RECEIPT_V1 implementado pelo provider/adapter com correlação estável da mesma ação e restituição do exato recurso debitado, A0224 não concede reembolso. Não inferir custo por diferença de barra, polling, custo teórico ou callback; não substituir por mana genérica, Source/Soul Energy/outro recurso, cura, duração ou desconto fictício. |
| Regra | PP_REGION: ARCANE/WATER. Keystone da Árvore Exterior, não Specialist e não terminal. Corrente Fluida nunca converte recursos: o receipt identifica um único resource_id e o reembolso só pode retornar à mesma unidade/provedor que efetivamente sofreu o débito. Claim duplicado, receipt de outra ação, débito zero/cancelado ou provider sem restituição segura devolvem zero e não inventam benefício. RESOURCE_DEBIT_RECEIPT_V1 é FUTURE_PROVIDER_CONTRACT, não API runtime existente; até implementação real, a parcela dependente permanece fail-closed. SPECIALIST_REGION:WATER usa PP semanticamente válidos; bridges/shared não contam por padrão. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

Aplicar um water_state_id real por ação WATER elegível e depois concluir uma ação WATER direta distinta dentro de 120 ticks (6 s) arma RPG_FLUID_CURRENT por 160 ticks (8 s). A próxima conjuração WATER bem-sucedida só consome Corrente Fluida após o custo nativo final daquela própria ação ter sido efetivamente debitado. Com ResourceDebitReceipt causal e reclamável uma única vez, devolver refund = quantize_down(0,15 × final_debited_amount, unidade_mínima_do_provider) no MESMO recurso nativo, limitado ao valor realmente debitado.

### Escalonamento aprovado

1 rank. Janela de sequência: 120 ticks; RPG_FLUID_CURRENT: 160 ticks. Reembolso: 15% do débito final real da ação beneficiada, no mesmo recurso nativo, quantizado conforme o provider e nunca maior que o débito. Um receipt pode ser reclamado no máximo uma vez.

### Gate de compra/ativação

A0219 ≥3 + Water Mastery ≥30 + caminho secundário exigido + aplicação WATER real seguida de ação WATER direta distinta em até 120 ticks. Consumo exige cast WATER bem-sucedido com ResourceDebitReceipt pós-débito contendo recurso, amount final positivo e identidade da mesma ação. Chuva, imersão, automação, polling de barra, custo teórico, diferença de recurso ou receipt de outra ação são inelegíveis.

### Hook e ordem de execução

Registrar a sequência WATER e armar Corrente. Na próxima ação WATER, aguardar commit e receipt do custo final nativo; validar action_id, resource_id, amount e claim único; devolver 15% quantizado no mesmo resource_id, então consumir Corrente e marcar o receipt como reclamado. Se o cast for cancelado ou débito final for zero, não há reembolso nem consumo.

### Fallback sem trocar a identidade

FAIL-CLOSED: sem RESOURCE_DEBIT_RECEIPT_V1 implementado pelo provider/adapter com correlação estável da mesma ação e restituição do exato recurso debitado, A0224 não concede reembolso. Não inferir custo por diferença de barra, polling, custo teórico ou callback; não substituir por mana genérica, Source/Soul Energy/outro recurso, cura, duração ou desconto fictício.

### Invariantes semânticos

- WATER exige ação/componente explicitamente classificado. Não equivale a chuva, submersão, bloco de água, WET, frio, natação ou estética aquática.
- Separa dano/resistência WATER, controle aquático e custo do recurso nativo.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Principal — ARCANE ↔ AGILITY / Água — Domínio Fluido |
| Camada e papel | Camada 6; Keystone |
| Pré-requisito visual/estrutural | A0219 Dano de Água I ≥ 3 ranks + pelo menos um caminho secundário entre A0220/A0222/A0223. |
| Dependência semântica completa | A0219 Dano de Água I ≥ 3 ranks + Water Mastery ≥ 30 + pelo menos um dos seguintes: A0220 = 1 rank, A0222 = 1 rank ou A0223 ≥ 1 rank. Exige uso real de WATER; ambiente aquático não substitui domínio. |
| Custo topológico | 2 PP por rank; 1 rank(s); extra 0 |
| Regra de região/PP | PP_REGION: ARCANE/WATER. Keystone da Árvore Exterior, não Specialist e não terminal. Corrente Fluida nunca converte recursos: o receipt identifica um único resource_id e o reembolso só pode retornar à mesma unidade/provedor que efetivamente sofreu o débito. Claim duplicado, receipt de outra ação, débito zero/cancelado ou provider sem restituição segura devolvem zero e não inventam benefício. RESOURCE_DEBIT_RECEIPT_V1 é FUTURE_PROVIDER_CONTRACT, não API runtime existente; até implementação real, a parcela dependente permanece fail-closed. SPECIALIST_REGION:WATER usa PP semanticamente válidos; bridges/shared não contam por padrão. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

RPG Skill Tree + Somake Spells 1.0.8-1.21.1-fix (Aqua School)/Iron's Spells runtime 1.21.1-3.16.3 e demais ações WATER somente por adapters explícitos + Water Mastery canônica + FUTURE_PROVIDER_CONTRACT RESOURCE_DEBIT_RECEIPT_V1. OWNER: provider do recurso/adapter versionado; CONSUMER: A0224; STATE: receipt imutável {action_id, resource_id, final_debited_amount, quantum, claimed}; EVENT: pós-débito confirmado; BEHAVIOR: claim único devolvendo fração quantizada ao exato resource_id debitado; VERSION-STATUS: design congelado, contrato não encontrado no runtime/main atual em 2026-08-29.

### Disposição por família

- **Providers/mods pertinentes:** Somake Aqua, Iron's, Ars Elemental e demais providers entram somente por adapters WATER explícitos; qualquer recurso reembolsado permanece no mesmo resource_id debitado.
- **Exclusões obrigatórias:** Cold Sweat, chuva, oceano e estado molhado não classificam magia WATER. Enshrouded/Volcanoes não fornecem outcome WATER para esta faixa.
- **Contratos/capabilities nomeados no registro:** <code>RESOURCE_DEBIT_RECEIPT_V1</code>.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0224 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c92304bb22c0c5cec3358a6cc6bc0dbb24cc15c9</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR — nenhuma capability do head auditado publica a assinatura/receipt exigida por esta perk. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>action_id</code>, <code>source_id</code>, <code>resource_id</code>, <code>receipt</code>, <code>claim</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>action_id</code>, <code>source_id</code>, <code>resource_id</code>, <code>receipt</code>, <code>claim</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0224.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 2 Passive Point(s) por rank, máximo de 1 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>MANA</code>, <code>RESOURCE_ID NATIVO</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Fallback normativo:** FAIL-CLOSED: sem RESOURCE_DEBIT_RECEIPT_V1 implementado pelo provider/adapter com correlação estável da mesma ação e restituição do exato recurso debitado, A0224 não concede reembolso. Não inferir custo por diferença de barra, polling, custo teórico ou callback; não substituir por mana genérica, Source/Soul Energy/outro recurso, cura, duração ou desconto fictício.
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
- Somake Aqua, Iron's, Ars Elemental e demais providers entram somente por adapters WATER explícitos; qualquer recurso reembolsado permanece no mesmo resource_id debitado.
- Cold Sweat, chuva, oceano e estado molhado não classificam magia WATER. Enshrouded/Volcanoes não fornecem outcome WATER para esta faixa.
- Relações somente temáticas foram deliberadamente recusadas; cada provider listado possui papel limitado ao subcontrato descrito.
- Provider não listado não é automaticamente incompatível: ele só entra futuramente após adapter versionado, classificação explícita, authority definida, testes e atualização do catálogo/dossiê.
- NeoVitae não é requisito, provider, fallback nem authority desta perk.

## 11. Plano obrigatório de testes para o Chat 2

1. **Compra válida:** provar ranks, PP, dependencies, mastery/gateway e provider/capability; então comprar exatamente até o máximo.
2. **Compra inválida:** faltar cada requisito isoladamente e comprovar recusa sem gasto, estado ou unlock residual.
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de 1 rank. Janela de sequência: 120 ticks; RPG_FLUID_CURRENT: 160 ticks. Reembolso: 15% do débito final real da ação beneficiada, no mesmo recurso nativo, quantizado conforme o provider e nunca maior que o débito. Um receipt pode ser reclamado no máximo uma vez..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>action_id</code>, <code>source_id</code>, <code>resource_id</code>, <code>receipt</code>, <code>claim</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
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
- **Capabilities/contracts a provar:** <code>RESOURCE_DEBIT_RECEIPT_V1</code>.
- **Dependências fora desta faixa:** nenhuma dependência fora de A0200–A0299.
- **Referências internas posteriores:** nenhuma.
- **Referência além do escopo:** nenhuma além das dependências listadas.
- Estas pendências não autorizam redesign silencioso. Se API/código real contradizer o contrato, implementar fail-closed e devolver o ponto ao Chat 1.

## 14. Auditoria dos nove eixos obrigatórios

| Eixo | Veredito e evidência |
|---|---|
| 1. Dependências e gates | **APROVADO NO DESIGN** — dependências, pré-requisitos, gate, PP/Mastery e comportamento na ausência estão explícitos. |
| 2. Integração global | **APROVADO NO DESIGN** — provider/modlist, authority, projetos próprios e exclusões foram dispostos; Cold Sweat, chuva, oceano e estado molhado não classificam magia WATER. Enshrouded/Volcanoes não fornecem outcome WATER para esta faixa. |
| 3. Qualidade e identidade | **APROVADO** — WATER exige ação/componente explicitamente classificado. Não equivale a chuva, submersão, bloco de água, WET, frio, natação ou estética aquática. |
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
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>action_id</code>, <code>source_id</code>, <code>resource_id</code>, <code>receipt</code>, <code>claim</code>. |
| 9 | Sem pipelines duplicados | **APROVADO** — um owner, um bucket/ledger/commit e composição única. |
| 10 | Custos/recursos reais | **APROVADO** — PP e recursos nativos usam débito/quantum/provider reais. |
| 11 | Sem geração gratuita | **APROVADO/NÃO APLICÁVEL** — não há recurso grátis; ganho/refund eventual exige receipt e mesmo resource_id. |
| 12 | Read-only verdadeiro | **APROVADO/NÃO APLICÁVEL** — queries/forecast são read-only; mutações só pela operação pública do owner. |
| 13 | Versões exatas | **APROVADO NO DESIGN** — versões externas permanecem no campo Provider/Mods; projetos próprios estão pinados por SHA. |
| 14 | Coerência estrutural | **APROVADO** — domínio, árvore, ramo, camada, função e custo são coerentes. |
| 15 | Dependências semânticas | **APROVADO** — closure e upstream/future refs estão explícitos e não presumidos. |
| 16 | Sem sobreposição/double-dip | **APROVADO** — Separa dano/resistência WATER, controle aquático e custo do recurso nativo. |
| 17 | Implementável | **APROVADO COMO CONTRATO** — hook, estado, owner, fallback e testes estão fechados; capability futura bloqueia runtime sem bloquear o design. |
| 18 | Pós-escrita relido | **APROVADO** — registro individual foi relido após Custo Extra=0; nenhum sucesso foi presumido. |

## 16. Evidência de persistência no Notion

- Página: [A0224 — Domínio Fluido](https://app.notion.com/3c569db9f0db81c7bb02f10e55efa481)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0224:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
