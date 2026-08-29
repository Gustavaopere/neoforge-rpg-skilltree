# 12.15 — Testes, performance e hardening

## Objetivo

Fechar o Stage 12 apenas quando múltiplos corpos funcionarem como progressões independentes, transacionais e recuperáveis no ambiente real NeoForge 1.21.1.

## Cenário canônico obrigatório

```text
Corpo A
- nível 300
- Piromante
- perks/atributos/masteries avançados
- inventário A

↓ troca para B

Corpo B
- nível inicial
- XP inicial
- 0 pontos gastos
- sem classe/mastery herdada
- inventário B independente

↓ mundo próximo do spawn

novos mobs usam contribuição baixa do Corpo B
+ baselines naturais continuam valendo

↓ evoluir B
↓ salvar/recarregar
↓ voltar para A

Corpo A retorna exatamente ao snapshot esperado
- nível 300
- Piromante
- progressão intacta
- inventário A intacto
```

## Testes unitários

Cobrir:

- state machine de `BodyState`;
- ownership;
- somente um ACTIVE;
- serialização/versionamento;
- fresh body defaults;
- scope registry;
- provider DAG/cycle detection;
- transaction journal;
- migration;
- rank/item identity não alterados pelo switch.

## Property/invariant tests

Gerar sequências de operações:

```text
create -> store -> switch -> save -> load -> switch -> death -> recover
```

Verificar sempre:

- um active body por owner;
- nenhum ItemStack logicamente duplicado por transação;
- revisions monotônicas;
- owner imutável;
- bodyId estável;
- progressões independentes;
- rollback converge para estado válido.

## GameTests / integração

Cobrir:

- criação tecnológica;
- construção interrompida por chunk unload/restart;
- troca normal A ↔ B;
- mudança de dimensão;
- inventário/armor/offhand;
- Curios quando presente;
- Stage 11 itemizado;
- morte/respawn;
- anchor destruído;
- ritual próprio;
- adapter Vampirism presente quando ambiente de teste permitir;
- ausência de Vampirism;
- provider falhando durante capture/apply;
- recovery pós-crash simulado.

## World scaling

Testes específicos:

1. Corpo A nível alto aumenta contribution de spawn conforme Stage 02;
2. Corpo B nível inicial reduz contribution no Overworld inicial;
3. Nether mantém baseline acima do início quando definido;
4. boss/encounter ativo não é nerfado por switch;
5. mobs persistentes/nomeados não são reescritos;
6. novos spawns usam nova revision sem restart;
7. nenhum sweep global de chunks ocorre.

## Duplication torture tests

Simular interrupção em cada fase do 12.05:

- antes de capture;
- durante capture;
- após source persistido;
- após unproject;
- durante target apply;
- após inventory apply;
- antes/depois de activeBodyId;
- antes do commit journal.

Após recovery, contar inventories, drops e containers relevantes. Nenhum item deve existir em dois lugares autoritativos por falha do Stage 12.

## Mods opcionais — matriz mínima

```text
RPG only
RPG + Curios
RPG + Vampirism
RPG + Ars Nouveau
RPG + Iron's Spellbooks
RPG + Epic Fight
RPG + Create/energia provider
stack integrada principal
```

Para cada combinação relevante:

- startup;
- create fresh;
- switch A→B→A;
- save/reload;
- dedicated-server smoke.

## Performance

Proibido:

- escanear todos os corpos a cada tick;
- serializar todos os providers a cada tick;
- varrer chunks do mundo ao trocar;
- recomputar árvore/classes continuamente por existir mais de um corpo.

Preferir:

- capture apenas em fronteiras/lifecycle;
- dirty flags/revision;
- cache do corpo ativo;
- lazy load de corpos armazenados;
- invalidation única no switch;
- refresh local bounded de scaling.

## Limites e proteção

Adicionar configs/data-driven para:

- máximo de corpos por owner;
- cooldown de switch;
- raio de refresh local;
- tamanho máximo de provider snapshot quando aplicável;
- retenção de backups/journals;
- logging de diagnóstico.

## Dedicated server

CI deve provar:

- nenhuma classe client-only no path server;
- BodyRegistry carrega;
- providers opcionais ausentes são fail-soft;
- network registration não exige tela;
- create/switch core inicializa sem cliente.

## PT-BR

Validator deve falhar se conteúdo próprio do Stage 12 introduzir chave game-facing sem `pt_br`.

## Gate final

Não marcar `✅` enquanto houver qualquer uma destas condições:

- dupe conhecido;
- perda de progressão;
- rollback incompleto;
- corpo ativo duplicado;
- world scaling preso no corpo anterior;
- provider obrigatório não restaurável;
- termos game-facing sem PT-BR;
- dedicated-server smoke vermelho;
- proveniência/licença NeoSync incompleta.

## Critérios de aceite

- cenário canônico passa integralmente;
- testes de crash/rollback passam;
- matriz de mods opcionais passa nas combinações suportadas;
- performance é bounded e orientada a eventos;
- CI e dedicated-server smoke ficam verdes;
- nenhuma pendência relevante é escondida antes de marcar o estágio concluído.