# 11.15 — Testes, performance, compatibilidade e hardening

## Objetivo

Fechar o Stage 11 com evidência objetiva de correção, compatibilidade, performance e ausência de reroll/stacking/duplication.

## Matriz obrigatória

### Identidade e persistência

- [ ] save/load;
- [ ] logout/login;
- [ ] dimensão;
- [ ] drop/pickup;
- [ ] container;
- [ ] morte do jogador;
- [ ] mob pickup/equip/drop;
- [ ] reload de datapack;
- [ ] reparo/smithing/upgrade.

Em todos os casos, comparar snapshot completo da identidade.

### Geração

- [ ] 1..5 Prefixos;
- [ ] 1..5 Sufixos;
- [ ] 1..5 Infixos;
- [ ] rank independente das três contagens;
- [ ] deterministic seed/context;
- [ ] pools insuficientes falham visivelmente;
- [ ] nenhum modifier duplicado proibido/exclusive conflict.

### Runtime

- [ ] atributos sem stacking em re-equip/reload;
- [ ] Infixos sem recursão infinita;
- [ ] cooldowns/chances server-authoritative;
- [ ] mob usa efeitos suportados;
- [ ] mudança de loadout invalida cache corretamente.

### Integrações

Matrizes de presença/ausência para:

- Apotheosis/Apothic;
- Iron's Spellbooks;
- Ars Nouveau;
- Create/integrações tecnológicas;
- Curios.

Combinações críticas devem ser testadas em conjunto, não apenas cada mod isolado.

### PT-BR

- [ ] cobertura de todas as chaves próprias;
- [ ] nenhum ID/chave crua no tooltip normal;
- [ ] Rank/famílias/erros localizados;
- [ ] 5/5/5 continua legível;
- [ ] aliases externos conhecidos traduzidos quando exibidos pelo RPG.

### Salvaging/economia

- [ ] sem duplicação;
- [ ] preview não recompensa;
- [ ] materiais bounded por Rank/Poder;
- [ ] recipes/loops críticos analisados.

## Performance

Medir antes de congelar budgets. Perfis obrigatórios:

- geração de item;
- classificação de item desconhecido;
- resolução de loadout;
- dispatch de Infixos em combate intenso;
- 5/5/5 em todos os slots relevantes;
- mobs equipados em quantidade;
- reload de catálogo/pools;
- login com inventário legado.

Requisitos arquiteturais:

- nenhum scan global de inventários por tick;
- pools pré-compilados/indexados após reload;
- caches por revisão/loadout;
- diagnostics rate-limited;
- complexidade bounded nos hot paths.

## CI e gates

Adicionar testes JUnit/GameTests/validators específicos e integrar ao pipeline principal. Acceptance final exige, conforme aplicável:

- testes Core/JUnit GREEN;
- NeoForge GameTests GREEN;
- validators de dados/localização GREEN;
- build NeoForge GREEN;
- verificação do JAR;
- dedicated-server smoke GREEN;
- optional-mod matrices críticas GREEN;
- relatório de performance/coverage arquivado.

## Segurança de release

- [ ] migração versionada;
- [ ] release notes alertam mudança de itemização;
- [ ] nenhum dado legado é apagado silenciosamente;
- [ ] comandos admin exigem permissão;
- [ ] cliente não pode forjar Rank/roll;
- [ ] networking valida tamanho/versão do payload.

## Acceptance

O Stage 11 só pode ser declarado concluído quando os testes provarem geração única, persistência, ausência de reroll/stacking/duplication, interoperabilidade dos providers, cobertura pt-BR e performance aceitável medida na instância representativa do modpack.
