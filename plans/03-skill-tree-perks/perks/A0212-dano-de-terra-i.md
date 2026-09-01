# A0212 — Dano de Terra I

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO; IMPLEMENTAÇÃO NÃO CONFIRMADA.
- **Disponibilidade operacional:** Nenhum runtime foi alterado por este trabalho de Chat 1. A habilitação futura depende de prova dos adapters, gates e hooks descritos no registro canônico.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0212 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db81979c74ea194d5753f2).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** <code>A0144</code>, <code>A0148</code>, <code>A0149</code>, <code>A0150</code>, <code>A0151</code>, <code>A0152</code>, <code>A0153</code>, <code>A0154</code>, <code>A0155</code>. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** EARTH/GEO exige ação ou componente mágico explicitamente classificado. Não equivale a pedra, mineração, impacto, knockback, queda, terreno ou alvo no chão.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0212 |
| Nome | Dano de Terra I |
| Domínio | ARCANE |
| Árvore | Principal — ARCANE ↔ MINING |
| Ramo | Terra — Potência Geomântica |
| Camada | 4 |
| Função na Árvore | Ramo |
| Tier | Pequeno |
| Faixa de Poder | Médio |
| Ranks Máx. | 4 |
| Custo por Rank | 1 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | A0144 Poder Mágico ≥ 2 ranks + Gateway ARCANE ativo + pelo menos 1 node de técnica arcana com ≥1 rank entre A0148, A0149, A0150, A0151, A0152, A0153, A0154 e A0155. Investimento MINING pode sustentar rotas Geomancer posteriores, mas não substitui esta base arcana. |
| Pré-requisitos | A0144 Poder Mágico ≥2 + Gateway ARCANE + ≥1 técnica arcana A0148–A0155. |
| Provider/Mods | Iron's Spells 'n Spellbooks 3.16.3 + GTBC's Geomancy Plus runtime 1.1.0-1.21.1 como provider EARTH/Geomancy principal + Ars Elemental 0.7.10.1 e Somake Spells 1.0.8-1.21.1-fix somente quando a ação concreta estiver explicitamente mapeada EARTH/GEO + classificador canônico EARTH do RPG Skill Tree. MINING não classifica magia EARTH. |
| Efeito | +3% de dano EARTH por rank (máx. +12%) em componentes mágicos EARTH diretos elegíveis e atribuídos ao jogador. Terra é termicamente neutra por padrão; qualquer parcel térmico só existe quando a ação concreta o declarar explicitamente. |
| Escalonamento | Até 4 ranks; multiplicador da camada EARTH direta: ×1,03 / ×1,06 / ×1,09 / ×1,12. Teto próprio de A0212: +12%. |
| Gate | Gateway ARCANE + A0144 ≥2 + ≥1 técnica arcana + componente mágico direto classificado EARTH por adapter versionado. Mineração, bloco/arma de pedra, alvo no chão, dano físico pétreo, automação e derived_component sem autorização não são EARTH por inferência. |
| Hook | Pipeline canônico: Potência Mágica universal → camada EARTH A0212 uma única vez por componente direto → especializações posteriores. Preservar action_id/outcome_id; parcel térmico excepcional, se existir, é processado separadamente. |
| Fallback | Sem classificação EARTH explícita e ownership mágico direto confiável, A0212 fica inativa naquela fonte. Não inferir por bloco/pedra, mineração, ferramenta, alvo grounded, dano físico pétreo, impacto, Mowzie mob temático ou automação; não converter em dano físico/mining. |
| Regra | FUNDAMENTO_EXTERIOR: ARCANE/EARTH. PP_REGION: ARCANE/EARTH. Earth Mastery nasce somente de ações/resultados EARTH reais; mineração, colocação/quebra de blocos, AFK e automação não concedem mastery. EARTH é distinto de impacto, armadura, queda e terreno. A0212 pode compor Gate A da Specialist Terra; Gate B ≥100 PP válidos e terminal correto continuam obrigatórios. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

+3% de dano EARTH por rank (máx. +12%) em componentes mágicos EARTH diretos elegíveis e atribuídos ao jogador. Terra é termicamente neutra por padrão; qualquer parcel térmico só existe quando a ação concreta o declarar explicitamente.

### Escalonamento aprovado

Até 4 ranks; multiplicador da camada EARTH direta: ×1,03 / ×1,06 / ×1,09 / ×1,12. Teto próprio de A0212: +12%.

### Gate de compra/ativação

Gateway ARCANE + A0144 ≥2 + ≥1 técnica arcana + componente mágico direto classificado EARTH por adapter versionado. Mineração, bloco/arma de pedra, alvo no chão, dano físico pétreo, automação e derived_component sem autorização não são EARTH por inferência.

### Hook e ordem de execução

Pipeline canônico: Potência Mágica universal → camada EARTH A0212 uma única vez por componente direto → especializações posteriores. Preservar action_id/outcome_id; parcel térmico excepcional, se existir, é processado separadamente.

### Fallback sem trocar a identidade

Sem classificação EARTH explícita e ownership mágico direto confiável, A0212 fica inativa naquela fonte. Não inferir por bloco/pedra, mineração, ferramenta, alvo grounded, dano físico pétreo, impacto, Mowzie mob temático ou automação; não converter em dano físico/mining.

### Invariantes semânticos

- EARTH/GEO exige ação ou componente mágico explicitamente classificado. Não equivale a pedra, mineração, impacto, knockback, queda, terreno ou alvo no chão.
- Distingue potência geomântica, controle nativo, resistência EARTH e mineração/impacto.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Principal — ARCANE ↔ MINING / Terra — Potência Geomântica |
| Camada e papel | Camada 4; Ramo |
| Pré-requisito visual/estrutural | A0144 Poder Mágico ≥2 + Gateway ARCANE + ≥1 técnica arcana A0148–A0155. |
| Dependência semântica completa | A0144 Poder Mágico ≥ 2 ranks + Gateway ARCANE ativo + pelo menos 1 node de técnica arcana com ≥1 rank entre A0148, A0149, A0150, A0151, A0152, A0153, A0154 e A0155. Investimento MINING pode sustentar rotas Geomancer posteriores, mas não substitui esta base arcana. |
| Custo topológico | 1 PP por rank; 4 rank(s); extra 0 |
| Regra de região/PP | FUNDAMENTO_EXTERIOR: ARCANE/EARTH. PP_REGION: ARCANE/EARTH. Earth Mastery nasce somente de ações/resultados EARTH reais; mineração, colocação/quebra de blocos, AFK e automação não concedem mastery. EARTH é distinto de impacto, armadura, queda e terreno. A0212 pode compor Gate A da Specialist Terra; Gate B ≥100 PP válidos e terminal correto continuam obrigatórios. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

Iron's Spells 'n Spellbooks 3.16.3 + GTBC's Geomancy Plus runtime 1.1.0-1.21.1 como provider EARTH/Geomancy principal + Ars Elemental 0.7.10.1 e Somake Spells 1.0.8-1.21.1-fix somente quando a ação concreta estiver explicitamente mapeada EARTH/GEO + classificador canônico EARTH do RPG Skill Tree. MINING não classifica magia EARTH.

### Disposição por família

- **Providers/mods pertinentes:** GTBC's Geomancy Plus é o provider principal; Iron's, Ars Elemental e Somake entram somente por adapters EARTH/GEO explícitos. MINING é ponte semântica, não classificador.
- **Exclusões obrigatórias:** Mowzie/tema geológico, ferramentas, blocos, automação e dano físico pétreo não ativam a família. Volcanoes conserva autoridade de geologia/worldgen e não vira provider mágico.
- **Contratos/capabilities nomeados no registro:** nenhum contrato nomeado adicional; ainda é obrigatória a prova do adapter/hook real.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0212 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c1597a34787b602e85139d565b9c1e1eb3481cda</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR NESTE CONTRATO — geologia/vulcanismo/atmosfera continuam authority do Volcanoes; não inferir EARTH/FIRE mágico por tema, lava ou calor. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>action_id</code>, <code>outcome_id</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>action_id</code>, <code>outcome_id</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0212.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 1 Passive Point(s) por rank, máximo de 4 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>PARCEL TÉRMICO</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** Nenhum runtime foi alterado por este trabalho de Chat 1. A habilitação futura depende de prova dos adapters, gates e hooks descritos no registro canônico.
- **Fallback normativo:** Sem classificação EARTH explícita e ownership mágico direto confiável, A0212 fica inativa naquela fonte. Não inferir por bloco/pedra, mineração, ferramenta, alvo grounded, dano físico pétreo, impacto, Mowzie mob temático ou automação; não converter em dano físico/mining.
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
- GTBC's Geomancy Plus é o provider principal; Iron's, Ars Elemental e Somake entram somente por adapters EARTH/GEO explícitos. MINING é ponte semântica, não classificador.
- Mowzie/tema geológico, ferramentas, blocos, automação e dano físico pétreo não ativam a família. Volcanoes conserva autoridade de geologia/worldgen e não vira provider mágico.
- Relações somente temáticas foram deliberadamente recusadas; cada provider listado possui papel limitado ao subcontrato descrito.
- Provider não listado não é automaticamente incompatível: ele só entra futuramente após adapter versionado, classificação explícita, authority definida, testes e atualização do catálogo/dossiê.
- NeoVitae não é requisito, provider, fallback nem authority desta perk.

## 11. Plano obrigatório de testes para o Chat 2

1. **Compra válida:** provar ranks, PP, dependencies, mastery/gateway e provider/capability; então comprar exatamente até o máximo.
2. **Compra inválida:** faltar cada requisito isoladamente e comprovar recusa sem gasto, estado ou unlock residual.
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de Até 4 ranks; multiplicador da camada EARTH direta: ×1,03 / ×1,06 / ×1,09 / ×1,12. Teto próprio de A0212: +12%..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>action_id</code>, <code>outcome_id</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
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
- **Capabilities/contracts a provar:** nenhum contrato nomeado adicional; ainda é obrigatória a prova do adapter/hook real.
- **Dependências fora desta faixa:** <code>A0144</code>, <code>A0148</code>, <code>A0149</code>, <code>A0150</code>, <code>A0151</code>, <code>A0152</code>, <code>A0153</code>, <code>A0154</code>, <code>A0155</code>.
- **Referências internas posteriores:** nenhuma.
- **Referência além do escopo:** nenhuma além das dependências listadas.
- Estas pendências não autorizam redesign silencioso. Se API/código real contradizer o contrato, implementar fail-closed e devolver o ponto ao Chat 1.

## 14. Auditoria dos nove eixos obrigatórios

| Eixo | Veredito e evidência |
|---|---|
| 1. Dependências e gates | **APROVADO NO DESIGN** — dependências, pré-requisitos, gate, PP/Mastery e comportamento na ausência estão explícitos. |
| 2. Integração global | **APROVADO NO DESIGN** — provider/modlist, authority, projetos próprios e exclusões foram dispostos; Mowzie/tema geológico, ferramentas, blocos, automação e dano físico pétreo não ativam a família. Volcanoes conserva autoridade de geologia/worldgen e não vira provider mágico. |
| 3. Qualidade e identidade | **APROVADO** — EARTH/GEO exige ação ou componente mágico explicitamente classificado. Não equivale a pedra, mineração, impacto, knockback, queda, terreno ou alvo no chão. |
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
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>action_id</code>, <code>outcome_id</code>. |
| 9 | Sem pipelines duplicados | **APROVADO** — um owner, um bucket/ledger/commit e composição única. |
| 10 | Custos/recursos reais | **APROVADO** — PP e recursos nativos usam débito/quantum/provider reais. |
| 11 | Sem geração gratuita | **APROVADO/NÃO APLICÁVEL** — não há recurso grátis; ganho/refund eventual exige receipt e mesmo resource_id. |
| 12 | Read-only verdadeiro | **APROVADO/NÃO APLICÁVEL** — queries/forecast são read-only; mutações só pela operação pública do owner. |
| 13 | Versões exatas | **APROVADO NO DESIGN** — versões externas permanecem no campo Provider/Mods; projetos próprios estão pinados por SHA. |
| 14 | Coerência estrutural | **APROVADO** — domínio, árvore, ramo, camada, função e custo são coerentes. |
| 15 | Dependências semânticas | **APROVADO** — closure e upstream/future refs estão explícitos e não presumidos. |
| 16 | Sem sobreposição/double-dip | **APROVADO** — Distingue potência geomântica, controle nativo, resistência EARTH e mineração/impacto. |
| 17 | Implementável | **APROVADO COMO CONTRATO** — hook, estado, owner, fallback e testes estão fechados; capability futura bloqueia runtime sem bloquear o design. |
| 18 | Pós-escrita relido | **APROVADO** — registro individual foi relido após Custo Extra=0; nenhum sucesso foi presumido. |

## 16. Evidência de persistência no Notion

- Página: [A0212 — Dano de Terra I](https://app.notion.com/3c569db9f0db81979c74ea194d5753f2)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0212:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
