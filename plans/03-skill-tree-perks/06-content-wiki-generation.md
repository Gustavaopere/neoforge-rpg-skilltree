# Stage 03 — Fechamento do catálogo, runtime e wiki

Este documento segue o [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md) e a sequência [`CODEX-FOUR-STAGES.md`](./CODEX-FOUR-STAGES.md).

## Critérios de fechamento editorial

- [ ] Seis atributos I com seus papéis/custos reais canônicos, sem duplicação de rank.
- [ ] Todas as Standard Perks P das 11 árvores completas como design, com mesmo orçamento, custos em pontos/rank, requisitos mínimos de atributos e evidência dos mods/versões.
- [ ] Especializações PURE/HYBRID com owner único, Mecânicas Assinatura e S generalistas por facts/tags **verificadas**; mesmo orçamento S por árvore.
- [ ] Vagas T de cada owner com **mesmo total**, tiers e sete arquivos mínimos ainda sem Ability inicialmente.
- [ ] Cobertura de todas as Abilities relevantes dos providers/vanilla/projetos próprios, com disposição ELIGIBLE/DESIGN-CANDIDATE/BLOCKED/UNSUPPORTED.
- [ ] Um perfil de transformação por Ability elegível, ID de provider/registry próprio, mesmo que compatível com múltiplas vagas; mínimo 2 LEFT/2 RIGHT/3 META, máx. 9/grupo.
- [ ] Vagas vazias não contam como perfis concluídos; perfis de Ability não contam como vagas T.

## Critérios técnicos de fechamento

- [ ] Verificação de versão/JAR presente e fonte real de gameplay/API para cada provider/hook usado; source externo não pinado na versão não comprova integração.
- [ ] `NodeAccessRequirement`/resolvers suportam quando implementado requisitos de atributo sem consumir os ranks canônicos.
- [ ] Custo de pontos de perk e custo de atributo continuam economias distintas; respec coerente e seguro.
- [ ] `NormalizedAbilityFacts` (proposto) tem mapping auditável e server-authoritative; especialização não reaplica dano/cura/proc.
- [ ] Transformação T específica é resolvida por Ability e compatibilidade de slot/tier; skill conhecida/desbloqueada segundo provider real.
- [ ] Grupos T funcionam com exatamente uma opção ativa em cada grupo simultaneamente (1+1+1), respec e persistência.
- [ ] A migração do piloto `T5000-chain-lightning` para perfil independente ocorre sem renumerar um save publicado nem perder decisões úteis.
- [ ] Validator distingue modo editorial (reservas aceitas) e release (não declarar slot vazio como habilidade funcional), duplicidades de P/S/T/profile, contagem e owner.
- [ ] JUnit, validators, dedicated server, compat mod-presente/ausente, save/reload e multiplayer verificados com resultado registrado.

## Topologia, UX e wiki

Somente depois do catálogo funcional: layout, gateways e distâncias definitivas; híbridas entre árvores sem mudar o owner; UI mostra tier/elegibilidade de slots e opções da Ability vinculada. Gerar wiki e drift gate para dados, runtime e documentação.

Reutilizar `✅-01` a `✅-05` apenas nas partes compatíveis. Contratos herdados da malha 512/A-series precisam ser migrados explicitamente e não prevalecem sobre a nova arquitetura.
