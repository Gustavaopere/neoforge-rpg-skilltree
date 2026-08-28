# Pendências abertas

- [ ] Gerar automaticamente páginas/catálogos de `wiki/` a partir dos JSON, localização e registries, incluindo nome, descrição, custo, pré-requisitos, ranks e efeito resolvido.
- [ ] Definir política de migração se IDs da Árvore Principal forem renomeados/removidos ou se o orçamento de 512 mudar.
- [ ] Revalidar qualquer bridge dedicada de bosses/Apothic antes de promover suporte nominal.
- [ ] Confirmar e implementar, se desejado, adapter runtime próprio para eventos/máquinas Create.
- [ ] Confirmar e implementar a mesma decisão para Applied Energistics 2 e Oritech.
- [ ] Fechar contrato único para magic damage/cast speed/mana/crit quando Ars e Iron's estiverem simultaneamente instalados.
- [ ] Cobrir ausência de cada mod opcional em dedicated server.
- [ ] Especificar hooks públicos para FTB Quests/quests futuras sem acoplar o core ao mod de quests.
- [ ] Definir versão/migração de dados persistidos antes de release estável.
- [ ] Automatizar verificação de drift entre `wiki/`, registries, localização, layout gerado e datapacks.
- [ ] Completar conteúdo mecânico dos nós da malha que ainda sejam apenas estruturais quando isso fizer parte do design final.

## Stage 10 — Compêndio Natural

- [ ] Antes do gate de conteúdo/release, executar o pipeline de inventário já integrado no Stage 10.02 sobre a instância canônica completa do pack e arquivar o snapshot/coverage correspondente; a fonte conhecida possui 553 entradas top-level, mas presença em runtime continua sendo a autoridade.
- [ ] Auditar e congelar upstream/tag/licença de qualquer nova referência além de Biology Dictionary, Field Guide e Wildex antes de reutilizar código, assets ou corpus; os três upstreams iniciais já foram auditados no Stage 10.01 e reimplementação comportamental continua sendo o default.
- [ ] Fechar no `10.13` se notas pessoais serão persistidas server-side por jogador ou client-side por mundo/servidor, com limites e migração explícitos.
- [ ] Medir o catálogo real do modpack antes de congelar budgets numéricos de startup, reload, memória e UI; não inventar budgets sem baseline.
