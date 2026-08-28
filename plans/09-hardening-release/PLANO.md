# PLANO — 09 Hardening & Release

Estado: **EM ANDAMENTO contínuo**.

## Objetivo

Transformar o conjunto funcional em release confiável: performance medida, saves migráveis, compatibilidade reproduzível e documentação correspondente ao runtime.

## Dependências

Todos os estágios que entrarem no escopo da release.

## Etapas de implementação

### 1 — Matriz de testes
- [ ] unit tests e validadores;
- [ ] GameTests onde agregarem valor;
- [ ] build NeoForge;
- [ ] dedicated-server smoke;
- [ ] cliente + servidor multiplayer.

### 2 — Performance
- [ ] medir hot paths de spawn/scaling, combat hooks, mastery e sync;
- [ ] evitar scans globais/per-tick;
- [ ] definir budgets e regressão aceitável.

### 3 — Saves e upgrades
- [ ] versionar dados persistidos;
- [ ] testar mundo novo e mundo atualizado;
- [ ] migrar IDs/schema ou falhar de forma explícita e recuperável;
- [ ] nunca apagar progresso silenciosamente.

### 4 — Matriz de compatibilidade
- [ ] cada mod opcional ausente;
- [ ] integrações isoladas;
- [ ] combinações críticas Ars/Iron's/Epic Fight/etc.;
- [ ] identificar conflitos conhecidos.

### 5 — Segurança/robustez de rede e dados
- [ ] payload bounds;
- [ ] datapacks inválidos não deixam snapshot parcial;
- [ ] comandos/requests respeitam permissões e servidor autoridade.

### 6 — Documentação de release
- [ ] `wiki/` corresponde ao gameplay do artefato;
- [ ] `plans/STATUS.md` corresponde ao código;
- [ ] changelog registra migrações e breaking changes;
- [ ] versionamento de Minecraft/NeoForge/Java congelado.

### 7 — Gate final
- [ ] CI verde no commit candidato;
- [ ] smoke de dedicated server verde;
- [ ] artefato/JAR verificado;
- [ ] nenhum blocker conhecido de save/crash;
- [ ] checklist de release assinado no repositório.

## Definição de concluído

Para uma release específica, todos os gates acima fecham. Quando o estágio global deixar de ter pendências obrigatórias para o marco definido, renomear para `PLANO-✅.md`; novas releases podem reabrir o estágio conscientemente.