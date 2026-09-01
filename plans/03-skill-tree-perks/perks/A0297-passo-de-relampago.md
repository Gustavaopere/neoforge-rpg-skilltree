# A0297 — Passo de Relâmpago

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO COM BLOQUEIO DE CAPABILITY.
- **Disponibilidade operacional:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0297 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db8123b095dc96e4b53569).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** <code>A0176</code>. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** LIGHTNING exige ação/componente explícito. Não equivale a FE, energia tecnológica, Oritech/Create New Age, tempestade visual, WET, velocidade ou eletricidade estética.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0297 |
| Nome | Passo de Relâmpago |
| Domínio | AGILITY/LIGHTNING |
| Árvore | Especialista — Raio |
| Ramo | Eletromancia — Mobilidade |
| Camada | 5 |
| Função na Árvore | Notable |
| Tier | Médio |
| Faixa de Poder | Transformativo |
| Ranks Máx. | 1 |
| Custo por Rank | 3 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | SPECIALIST_UNLOCK:LIGHTNING confirmado por Gate A/B/C + A0288 Reflexo Carregado =1 + A0287 Impulso Elétrico =3 ranks + AGILITY ≥10 + DODGE_AVOID_RECEIPT_V1 disponível. Requisitos locais não substituem fundamentos LIGHTNING + ≥100 PP válidos em SPECIALIST_REGION:LIGHTNING + terminal A0176. |
| Pré-requisitos | Specialist Raio desbloqueada (SPECIALIST_UNLOCK:LIGHTNING) + A0288 + A0287 3/3 + AGILITY ≥10 + provider de esquiva com ataque realmente evitado. |
| Provider/Mods | RPG Skill Tree + SPECIALIST_GATE_RESOLVER_V1 + DODGE_AVOID_RECEIPT_V1 + TRANSIENT_ATTRIBUTE_MODIFIER_V1 + CHARGED_STATE_LEDGER_V1 + FUTURE_PROVIDER_CONTRACT opcional DODGE_CONTROL_MODIFIER_V1. Epic Fight 21.17.3.1 é provider principal de dodge quando adapter comprovar ameaça evitada; ParCool 4.0.0.2/Epic ParCool 21.0.0 só participam por receipt equivalente. Minecraft/NeoForge fornece movement_speed; dodge distance/control só existe se o provider expuser métrica mutável sem alterar i-frames. |
| Efeito | Após um direct_lightning_action_id elegível, A0297 abre uma preparação de 80 ticks (4 s). Se dentro dela ocorrer uma esquiva validada que realmente evite ataque hostil e a recarga estiver livre, ativa RPG_LIGHTNING_STEP por 60 ticks (3 s): +20% de velocidade de movimento e, quando o provider expuser uma métrica mutável sem tocar i-frames, +10% de distância/controle da esquiva. A primeira ação LIGHTNING direta durante esses 60 ticks consome uma preparação ofensiva separada e adiciona exatamente 1 stack CHARGED própria ao alvo, sujeita ao cap/TTL de A0284. Recarga de 200 ticks (10 s), iniciada na ativação. |
| Escalonamento | 1 rank. Janela ação→dodge: 80 ticks. Estado ativo: 60 ticks. Cooldown: 200 ticks desde a ativação. Movimento +20% durante os 60 ticks. Dodge distance/control ×1,10 somente se semanticamente suportado. A preparação ofensiva é claim-once; consumi-la não encerra o bônus de movimento restante. |
| Gate | SPECIALIST_UNLOCK:LIGHTNING válido + A0288=1 + A0287=3 + AGILITY≥10 + ação LIGHTNING DIRETA real abre arm de 80 ticks + DODGE_AVOID_RECEIPT_V1 prova ameaça hostil evitada dentro da janela + cooldown livre. Sprint, jump, knockback, teleporte, movimento forçado, block/parry, animação sem ameaça e efeitos LIGHTNING derivados não ativam. Ação direta durante estado ativo não rearma um segundo estado. |
| Hook | No commit da ação LIGHTNING direta, se A0297 não estiver ativa, registrar lightning_step_arm_expiry=now+80, deduplicado por action/root id. No dodge receipt elegível, consumir atomicamente o arm e criar uma única RPG_LIGHTNING_STEP {expires_at=now+60,offensive_charge=true} com cooldown_expiry=now+200; aplicar +20% movement por ID estável. Se DODGE_CONTROL_MODIFIER_V1 existir, aplicar ×1,10 somente à distância/controle da esquiva futura definida pelo provider, nunca aos i-frames. Na primeira ação LIGHTNING DIRETA durante os 60 ticks, claim atômico de offensive_charge; se CHARGED_STATE_LEDGER_V1 estiver disponível, inserir exatamente 1 stack própria no alvo, respeitando cap 3/TTL individual, sem RNG A0284. O claim ofensivo não encerra o movimento restante. |
| Fallback | FAIL-CLOSED integral se Specialist Raio não estiver desbloqueada ou se não houver receipt confiável de ataque evitado. Sem dodge-control mutável, omitir somente ×1,10 e preservar movimento/charge. Sem CHARGED_STATE_LEDGER_V1, omitir somente a preparação ofensiva e preservar a mobilidade se o dodge receipt e modificador de movimento continuarem seguros; não criar segundo recurso de carga. Sem movimento idempotente, A0297 fica integralmente inativa porque mobilidade é parte central da identidade. |
| Regra | COUNTS_AS_SPECIALIST_PERK:LIGHTNING=YES. Notable interna/AGILITY da Specialist Raio. Passo de Relâmpago não concede i-frames, não acumula movement e não gera mastery por deslocamento. A stack garantida é CHARGED real do mesmo owner, usa cap/TTL de A0284 e não executa a rolagem A0284. Hops/branches derivados não consomem offensive_charge. Lifecycle/respec remove arm/estado/modificadores sem criar stack pessoal fictícia. Gate A/B/C permanece obrigatório durante ownership/respec. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

Após um direct_lightning_action_id elegível, A0297 abre uma preparação de 80 ticks (4 s). Se dentro dela ocorrer uma esquiva validada que realmente evite ataque hostil e a recarga estiver livre, ativa RPG_LIGHTNING_STEP por 60 ticks (3 s): +20% de velocidade de movimento e, quando o provider expuser uma métrica mutável sem tocar i-frames, +10% de distância/controle da esquiva. A primeira ação LIGHTNING direta durante esses 60 ticks consome uma preparação ofensiva separada e adiciona exatamente 1 stack CHARGED própria ao alvo, sujeita ao cap/TTL de A0284. Recarga de 200 ticks (10 s), iniciada na ativação.

### Escalonamento aprovado

1 rank. Janela ação→dodge: 80 ticks. Estado ativo: 60 ticks. Cooldown: 200 ticks desde a ativação. Movimento +20% durante os 60 ticks. Dodge distance/control ×1,10 somente se semanticamente suportado. A preparação ofensiva é claim-once; consumi-la não encerra o bônus de movimento restante.

### Gate de compra/ativação

SPECIALIST_UNLOCK:LIGHTNING válido + A0288=1 + A0287=3 + AGILITY≥10 + ação LIGHTNING DIRETA real abre arm de 80 ticks + DODGE_AVOID_RECEIPT_V1 prova ameaça hostil evitada dentro da janela + cooldown livre. Sprint, jump, knockback, teleporte, movimento forçado, block/parry, animação sem ameaça e efeitos LIGHTNING derivados não ativam. Ação direta durante estado ativo não rearma um segundo estado.

### Hook e ordem de execução

No commit da ação LIGHTNING direta, se A0297 não estiver ativa, registrar lightning_step_arm_expiry=now+80, deduplicado por action/root id. No dodge receipt elegível, consumir atomicamente o arm e criar uma única RPG_LIGHTNING_STEP {expires_at=now+60,offensive_charge=true} com cooldown_expiry=now+200; aplicar +20% movement por ID estável. Se DODGE_CONTROL_MODIFIER_V1 existir, aplicar ×1,10 somente à distância/controle da esquiva futura definida pelo provider, nunca aos i-frames. Na primeira ação LIGHTNING DIRETA durante os 60 ticks, claim atômico de offensive_charge; se CHARGED_STATE_LEDGER_V1 estiver disponível, inserir exatamente 1 stack própria no alvo, respeitando cap 3/TTL individual, sem RNG A0284. O claim ofensivo não encerra o movimento restante.

### Fallback sem trocar a identidade

FAIL-CLOSED integral se Specialist Raio não estiver desbloqueada ou se não houver receipt confiável de ataque evitado. Sem dodge-control mutável, omitir somente ×1,10 e preservar movimento/charge. Sem CHARGED_STATE_LEDGER_V1, omitir somente a preparação ofensiva e preservar a mobilidade se o dodge receipt e modificador de movimento continuarem seguros; não criar segundo recurso de carga. Sem movimento idempotente, A0297 fica integralmente inativa porque mobilidade é parte central da identidade.

### Invariantes semânticos

- LIGHTNING exige ação/componente explícito. Não equivale a FE, energia tecnológica, Oritech/Create New Age, tempestade visual, WET, velocidade ou eletricidade estética.
- Separa root action, hops nativos/derivados, CHARGED, dodge, dano, resistência e tecnologia elétrica.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Especialista — Raio / Eletromancia — Mobilidade |
| Camada e papel | Camada 5; Notable |
| Pré-requisito visual/estrutural | Specialist Raio desbloqueada (SPECIALIST_UNLOCK:LIGHTNING) + A0288 + A0287 3/3 + AGILITY ≥10 + provider de esquiva com ataque realmente evitado. |
| Dependência semântica completa | SPECIALIST_UNLOCK:LIGHTNING confirmado por Gate A/B/C + A0288 Reflexo Carregado =1 + A0287 Impulso Elétrico =3 ranks + AGILITY ≥10 + DODGE_AVOID_RECEIPT_V1 disponível. Requisitos locais não substituem fundamentos LIGHTNING + ≥100 PP válidos em SPECIALIST_REGION:LIGHTNING + terminal A0176. |
| Custo topológico | 3 PP por rank; 1 rank(s); extra 0 |
| Regra de região/PP | COUNTS_AS_SPECIALIST_PERK:LIGHTNING=YES. Notable interna/AGILITY da Specialist Raio. Passo de Relâmpago não concede i-frames, não acumula movement e não gera mastery por deslocamento. A stack garantida é CHARGED real do mesmo owner, usa cap/TTL de A0284 e não executa a rolagem A0284. Hops/branches derivados não consomem offensive_charge. Lifecycle/respec remove arm/estado/modificadores sem criar stack pessoal fictícia. Gate A/B/C permanece obrigatório durante ownership/respec. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

RPG Skill Tree + SPECIALIST_GATE_RESOLVER_V1 + DODGE_AVOID_RECEIPT_V1 + TRANSIENT_ATTRIBUTE_MODIFIER_V1 + CHARGED_STATE_LEDGER_V1 + FUTURE_PROVIDER_CONTRACT opcional DODGE_CONTROL_MODIFIER_V1. Epic Fight 21.17.3.1 é provider principal de dodge quando adapter comprovar ameaça evitada; ParCool 4.0.0.2/Epic ParCool 21.0.0 só participam por receipt equivalente. Minecraft/NeoForge fornece movement_speed; dodge distance/control só existe se o provider expuser métrica mutável sem alterar i-frames.

### Disposição por família

- **Providers/mods pertinentes:** Iron's Spells e Ars Nouveau/Ars Elemental entram por classificadores LIGHTNING. Epic Fight/ParCool só fornecem receipts de dodge/estamina quando semanticamente equivalentes; Sable/Aeronautics apenas resolvem espaço.
- **Exclusões obrigatórias:** WET exige WET_STATE_V1; chain exige contexto/visited set; mana, stamina, FE e outros recursos não são intercambiáveis. Black Arcana não fornece LIGHTNING nesta faixa.
- **Contratos/capabilities nomeados no registro:** <code>DODGE_AVOID_RECEIPT_V1</code>, <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DODGE_CONTROL_MODIFIER_V1</code>.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0297 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c1597a34787b602e85139d565b9c1e1eb3481cda</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR — nenhuma capability do head auditado publica a assinatura/receipt exigida por esta perk. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>action_id</code>, <code>receipt</code>, <code>ledger</code>, <code>claim</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>action_id</code>, <code>receipt</code>, <code>ledger</code>, <code>claim</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0297.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 3 Passive Point(s) por rank, máximo de 1 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>RESOURCE_ID NATIVO</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Fallback normativo:** FAIL-CLOSED integral se Specialist Raio não estiver desbloqueada ou se não houver receipt confiável de ataque evitado. Sem dodge-control mutável, omitir somente ×1,10 e preservar movimento/charge. Sem CHARGED_STATE_LEDGER_V1, omitir somente a preparação ofensiva e preservar a mobilidade se o dodge receipt e modificador de movimento continuarem seguros; não criar segundo recurso de carga. Sem movimento idempotente, A0297 fica integralmente inativa porque mobilidade é parte central da identidade.
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
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de 1 rank. Janela ação→dodge: 80 ticks. Estado ativo: 60 ticks. Cooldown: 200 ticks desde a ativação. Movimento +20% durante os 60 ticks. Dodge distance/control ×1,10 somente se semanticamente suportado. A preparação ofensiva é claim-once; consumi-la não encerra o bônus de movimento restante..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>action_id</code>, <code>receipt</code>, <code>ledger</code>, <code>claim</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
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
- **Capabilities/contracts a provar:** <code>DODGE_AVOID_RECEIPT_V1</code>, <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DODGE_CONTROL_MODIFIER_V1</code>.
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
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>action_id</code>, <code>receipt</code>, <code>ledger</code>, <code>claim</code>. |
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

- Página: [A0297 — Passo de Relâmpago](https://app.notion.com/3c569db9f0db8123b095dc96e4b53569)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0297:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
