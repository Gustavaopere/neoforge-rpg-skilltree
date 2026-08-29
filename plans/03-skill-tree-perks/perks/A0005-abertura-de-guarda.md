# A0005 — Abertura de Guarda

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE, com divergência de fallback identificada.
- **Notion:** https://app.notion.com/p/3c569db9f0db816cb407cc16ebe41066
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0005
- **Nome:** Abertura de Guarda
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Duelista — Ímpeto
- **Camada:** 3
- **Função na Árvore:** Notable
- **Tier:** Médio
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 1
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0002 Treino com Espadas II ≥ 2 ranks + A0004 Ritmo do Duelista; rota lateral não substitui essas dependências.
- **Pré-requisitos:** A0002 + A0004.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree.
- **Efeito:** com pelo menos 3 de Ímpeto, um acerto direto de espada contra o mesmo alvo após sequência limpa pode consumir 2 de Ímpeto para criar Abertura: +12% de penetração física elegível e +8% de impacto/pressão de guarda. Recarga de 6 s por alvo.
- **Escalonamento:** 1 rank; benefício condicional e consumidor de recurso.
- **Gate:** Gateway `epic_sword` + A0002 ≥ 2 ranks + A0004 adquirido.
- **Hook:** acerto direto confirmado + registro canônico de Ímpeto + estado defensivo do alvo; quando disponíveis, usar `IMPACT`/`ARMOR_NEGATION` do Epic Fight apenas no golpe consumidor.
- **Fallback:** se guarda/postura nativa não estiver exposta, aplicar somente a penetração física canônica com cap; nunca simular quebra de guarda inexistente.
- **Regra:** não ativa em dano periódico/proc, não encadeia em si mesma e respeita caps globais de penetração. Gate deve reproduzir integralmente as dependências.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0002 e A0004 são obrigatórios e semanticamente coerentes.
2. **Integração global — PASS.** Consome Ímpeto canônico e usa penetração/impacto do provider; não cria postura paralela.
3. **Qualidade/identidade — PASS.** Converte execução acumulada em janela ofensiva condicional com custo e cooldown por alvo.
4. **Topologia — PASS.** Notable de camada 3 depende da progressão de ritmo + Ímpeto.
5. **Especializações — PASS.** Mantém identidade Duelista sem virar classe automática.
6. **PT-BR — PASS.** Texto de jogador em português.
7. **Notion completo — PASS.** Custo, requisito, alvo, cooldown, hooks e fallback definidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS COM DIVERGÊNCIA DE FALLBACK.** Epic Fight fornece armor negation/impact; o fallback sem guarda/postura precisa ser representado corretamente no core/adapter.

## Contrato técnico esperado

- Requer `momentum >= 3` antes do consumo.
- Consome exatamente 2 de Ímpeto.
- Mesmo alvo da sequência limpa.
- Cooldown individual de 6 s por alvo.
- Penetração: 12% quando o componente for elegível/seguro.
- Impacto/pressão: +8% somente quando o provider expuser o componente real.
- Sem guarda/postura exposta, a perk deve poder degradar para **somente penetração**, sem inventar quebra de guarda.
- Uma ação só pode consumir A0005 uma vez.

## Evidência encontrada na `main`

- `NotionCombatPerkRules` define `A0005_MIN_MOMENTUM=3`, custo 2, multiplicador 1.08, penetração 0.12 e cooldown 6 s.
- `A0001A0020CombatPolicy.beforeHit(...)` valida mesma sequência/alvo, momentum, cooldown, `claimOnce`, consome recurso e aplica componentes disponíveis.
- `A0001A0020EpicFightHooks.onDamagePre(...)` deriva defesa por `target.isBlocking()` ou `stunShield > 0`, declara impacto e penetração disponíveis e anexa modificadores nativos ao `EpicFightDamageSource`.

## Pendências técnicas

### P-A0005-01 — fallback de penetração sem guarda/postura não demonstrado

- **Severidade:** média.
- **Estado:** ABERTA.
- **Causa:** o policy atual exige `facts.relevantGuardOrPosture()` para entrar na ativação de A0005. Quando um adapter não consegue expor guarda/postura, essa condição é falsa e a perk não chega ao caminho de penetração-only previsto no Notion.
- **Impacto:** o caminho Epic Fight auditado funciona quando há estado defensivo detectável, mas a degradação canônica declarada não está representada de forma geral.
- **Correção esperada:** separar elegibilidade da Abertura da disponibilidade do componente de guarda/postura, permitindo penetração-only quando a sequência/Ímpeto/alvo forem válidos e `penetrationHookAvailable=true`, sem fabricar pressão de guarda.
- **Fail-closed:** nunca converter a parcela ausente em dano genérico.

## Testes obrigatórios

- [x] valores/cooldown representados no ruleset;
- [x] consumo, deduplicação e cooldown presentes no policy;
- [x] integração Epic Fight para armor negation/impact presente;
- [ ] RED/GREEN para fallback `penetration-only` sem guarda/postura;
- [ ] teste que garanta ausência de impacto/pressão quando o hook não existir;
- [ ] dedicated-server smoke após correção.
