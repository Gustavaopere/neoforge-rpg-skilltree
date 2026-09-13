# CHAT 2 — IMPLEMENTAÇÃO DAS PERKS

Trabalhe no projeto:

https://github.com/Gustavaopere/neoforge-rpg-skilltree

Minecraft: NeoForge 1.21.1  
Java: 21

Sua responsabilidade neste chat é **IMPLEMENTAR o design já auditado e fechado pelo Chat 1**.

Você **NÃO deve redesenhar perks neste chat**.

Trabalhe sempre em **LOTES EXATOS DE 10 perks**.

Não fixe o intervalo manualmente neste prompt. Antes de iniciar cada ciclo, determine o **próximo lote de 10 perks cujo design esteja aprovado pelo Chat 1, mas que ainda não esteja marcado como CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO**, usando `STATUS.md`, os dossiês individuais e os arquivos de auditoria existentes.

---

## LEITURA OBRIGATÓRIA

### 1. Critérios obrigatórios — arquivo anexado ao projeto

Leia integralmente o arquivo anexado à **descrição do projeto**:

`CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`

Não é necessário buscar rotineiramente a cópia equivalente no GitHub. Se o arquivo anexado não estiver acessível no chat, **pare e informe o bloqueio**.

Mesmo com o design já auditado, respeite especialmente os critérios técnicos:

- provider-native first;
- versão exata;
- fail-closed;
- fallback;
- uma ação, um pipeline canônico;
- anti-duplicação;
- anti-abuso/Mastery;
- autoria causal;
- deduplicação;
- ausência de geração gratuita;
- testabilidade do contrato, sem executar a bateria de testes desta etapa;
- PT-BR quando houver conteúdo player-facing.

### 2. Dossiês individuais

https://github.com/Gustavaopere/neoforge-rpg-skilltree/tree/main/plans/03-skill-tree-perks/perks

Leia integralmente os **10 dossiês do lote atual**.

### 3. Status

https://github.com/Gustavaopere/neoforge-rpg-skilltree/blob/main/plans/03-skill-tree-perks/perks/STATUS.md

### 4. Auditoria correspondente ao lote

Procure em:

`plans/03-skill-tree-perks/perks/`

o arquivo `AUDITORIA-*.md` que cobre o intervalo atual.

Leia-o integralmente antes de implementar.

---

# SOBRE OS QUATRO GUIAS

Os quatro guias completos também estão anexados à **descrição do projeto**:

- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`

O **Chat 1 é responsável por ler integralmente esses quatro guias, fazer a auditoria completa e transformar as integrações relevantes em contrato nos dossiês**.

Portanto o Chat 2 **não deve refazer toda a auditoria dos quatro guias em cada lote** e não precisa abrir rotineiramente as versões partidas no GitHub. O guia de Projetos Próprios deve ser consultado pontualmente se uma divergência técnica envolver RPG Skill Tree, Volcanoes, Enshrouded ou Black Arcana.

Implemente o contrato registrado nos dossiês e na auditoria.

Exceção:

Se durante a implementação a API/código real do provider contradizer o dossiê:

- NÃO invente solução;
- NÃO substitua por bônus genérico;
- NÃO redesenhe silenciosamente;
- aplique FAIL-CLOSED quando necessário;
- registre a evidência técnica;
- marque a pendência;
- sinalize que o ponto precisa voltar ao Chat 1.

---

# REGRA DE LOTE

Cada ciclo trabalha **exatamente 10 perks consecutivas**.

Para determinar o lote:

1. leia `STATUS.md`;
2. encontre a primeira perk com **DESIGN APROVADO** mas sem **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO**;
3. forme o intervalo dessa perk + as 9 seguintes;
4. confirme que as 10 possuem design fechado pelo Chat 1;
5. se alguma não estiver aprovada pelo Chat 1, pare e informe o bloqueio;
6. registre `INÍCIO` e `FIM` no começo da execução.

Não inicie o lote seguinte no mesmo ciclo.

---

# IMPLEMENTAÇÃO

Para cada perk:

- implemente exatamente o contrato do dossiê;
- use os hooks definidos;
- respeite gates e dependências;
- use os recursos/pipelines canônicos;
- respeite fallback/fail-closed;
- implemente deduplicação;
- impeça double-processing;
- respeite anti-abuso/Mastery;
- preserve autoria causal;
- não crie geração gratuita;
- implemente integração provider-native;
- preserve os seams, hooks e pontos de observação necessários aos testes definidos pelo Chat 1;
- **não crie nem execute a bateria de testes, GameTests, testes de integração, build de validação ou dedicated-server smoke como responsabilidade deste chat**.

---

# CHECKLIST NO DOSSIÊ INDIVIDUAL

Depois de implementar cada perk, atualize seu arquivo individual em:

`plans/03-skill-tree-perks/perks/`

Use checklist explícita baseada no contrato real.

Exemplo:

- [x] Hook implementado
- [x] Gate implementado
- [x] Provider-native implementado
- [x] Fallback/fail-closed implementado
- [x] Deduplicação implementada
- [x] Anti-abuso implementado
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários
- [ ] **VALIDAÇÃO CHAT 3:** GameTests
- [ ] **VALIDAÇÃO CHAT 3:** testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

Se algo não puder ser implementado:

- [ ] **PENDÊNCIA:** descrição exata

Registre:

- o que falta;
- por que falta;
- provider/API envolvido;
- evidência técnica;
- risco;
- comportamento fail-closed atual;
- se precisa voltar ao Chat 1.

Nunca marque `[x]` em algo que não foi realmente implementado. Itens reservados ao Chat 3 devem permanecer pendentes até a validação real.

---

# AUDITORIA E STATUS

Atualize o `AUDITORIA-*.md` correspondente ao lote com o estado REAL da implementação.

Atualize também:

https://github.com/Gustavaopere/neoforge-rpg-skilltree/blob/main/plans/03-skill-tree-perks/perks/STATUS.md

Separe claramente:

- DESIGN APROVADO;
- CÓDIGO PRESENTE;
- CHAT 2 CONCLUÍDO;
- AGUARDANDO VALIDAÇÃO CHAT 3;
- IMPLEMENTAÇÃO CONFIRMADA — **reservado ao Chat 3**;
- FALLBACK;
- FAIL-CLOSED;
- PENDÊNCIA.

Ao terminar sua etapa, o estado normal é **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

Não transforme uma pendência técnica em “concluído” apenas porque existe um fallback. O Chat 2 **nunca** promove uma perk para `IMPLEMENTAÇÃO CONFIRMADA`.

---

# REGRA DA PR ÚNICA DO LOTE

O fluxo normal usa **uma única branch/PR para o lote inteiro**:

1. o Chat 1 cria/atualiza a branch e a PR com os dossiês e o design;
2. o Chat 2 deve continuar **essa mesma branch/PR** e adicionar a implementação;
3. o Chat 3 continua a mesma PR, resolve pendências, adiciona/ajusta testes, obtém CI verde e faz merge.

Não abra uma segunda PR para o mesmo lote se a PR do Chat 1 estiver disponível e utilizável. Se ela estiver ausente, fechada incorretamente ou tecnicamente inutilizável, registre a anomalia antes de criar substituta.

---

# FECHAMENTO DO CICLO

Quando o código das 10 perks estiver escrito:

1. confirme que o contrato dos 10 dossiês foi implementado na extensão tecnicamente possível;
2. registre toda divergência encontrada entre dossiê e API/código real do provider;
3. aplique fallback/fail-closed previsto quando necessário;
4. atualize os 10 dossiês com o estado real do código;
5. atualize a auditoria;
6. atualize `STATUS.md` para **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**;
7. revise o diff de implementação;
8. atualize a mesma branch/PR aberta pelo Chat 1;
9. registre pendências técnicas para o Chat 3;
10. registre separadamente qualquer ponto que exija retorno ao Chat 1 por envolver redesign;
11. **NÃO execute a bateria de testes, GameTests, testes de integração, build de validação ou dedicated-server smoke**;
12. **NÃO exija CI GREEN como condição de encerramento desta etapa**;
13. **NÃO faça merge**;
14. deixe a PR aberta e pronta para o Chat 3;
15. **PARE**.

Não inicie o próximo lote.

Ao encerrar, informe:

- intervalo efetivamente implementado;
- PR/branch que o Chat 3 deve continuar;
- perks com CÓDIGO PRESENTE;
- fallback/fail-closed implementados;
- pendências técnicas para o Chat 3;
- pontos que precisam voltar ao Chat 1;
- testes/validações ainda pendentes do Chat 3.

**O Chat 2 nunca declara `IMPLEMENTAÇÃO CONFIRMADA` e nunca faz merge.**
