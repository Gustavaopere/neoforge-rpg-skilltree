# CHAT 1 — AUDITORIA, DESIGN E INTEGRAÇÃO DAS PERKS

Trabalhe no projeto:

https://github.com/Gustavaopere/neoforge-rpg-skilltree

Minecraft: NeoForge 1.21.1  
Java: 21

Sua responsabilidade neste chat é **AUDITAR, CORRIGIR E FECHAR O DESIGN das perks e MATERIALIZAR esse design em dossiês `.md` completos no GitHub antes da implementação de runtime pelo Chat 2**.

Trabalhe sempre em **LOTES EXATOS DE 10 perks**.

Não fixe o intervalo manualmente neste prompt. Antes de iniciar cada ciclo, determine o **próximo lote de 10 perks que ainda não esteja formalmente fechado pela auditoria**, usando `STATUS.md`, os dossiês individuais e os arquivos de auditoria existentes.

---

## FONTES OBRIGATÓRIAS

ANTES de auditar qualquer perk do lote, leia integralmente os arquivos consolidados anexados à **descrição do projeto**.

### 1. Critérios obrigatórios — arquivo anexado ao projeto

Leia integralmente:

`CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`

Este arquivo contém a cópia consolidada dos critérios obrigatórios. Os critérios devem ser aplicados integralmente. Não considere uma perk fechada apenas porque já foi marcada assim por outro chat.

### 2. Os quatro guias completos — arquivos anexados ao projeto

Leia integralmente, do início ao fim:

- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`

Esses quatro guias são as versões consolidadas dos capítulos que existem separados no GitHub. Para a auditoria normal do lote, **não é necessário reabrir todos os arquivos partidos do GitHub**.

Se qualquer um desses cinco arquivos anexados não estiver acessível no chat, **pare e informe o bloqueio** em vez de fingir que realizou a leitura.

### 2.1 Regra de atualização dos arquivos consolidados

Use os cinco arquivos anexados como referência operacional padrão para velocidade e consistência.

Só volte às versões separadas no GitHub/Notion quando:

- houver evidência de que foram atualizadas depois do snapshot anexado;
- o usuário pedir reconciliação/atualização;
- surgir contradição entre o dossiê, provider real e o conteúdo consolidado;
- for necessário verificar uma mudança recente não refletida nos anexos.

Nesse caso, reconcilie a diferença e registre qual fonte mais nova foi utilizada.

### 2.2 Regra obrigatória — projetos próprios do modpack

`GUIA-COMPLETO-PROJETOS-PROPRIOS.md` é leitura integral obrigatória, incluindo os dossiês de **RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana** e a **Matriz de Integração Cruzada**.

Para cada projeto próprio pertinente à perk, o Chat 1 deve registrar explicitamente:

- provider/consumer e **authority**;
- boundary/API/query/hook concreto;
- versão, SHA ou evidência que comprova o contrato;
- causalidade do evento;
- identidade de deduplicação;
- fallback;
- fail-closed;
- qual recurso/estado/pipeline a perk **não pode duplicar nem escrever diretamente**.

Estados `PLANEJADO`, `PREPARATÓRIO / NÃO CANÔNICO` e `BLOQUEADO / FAIL-CLOSED` não podem ser tratados como runtime disponível. Um subcomponente comprovado em `main` não promove automaticamente o Stage inteiro. Integração temática não cria bridge nem copropriedade.

### 2.3 Gate obrigatório — delta de capacidades dos projetos próprios

Como **RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana continuam sendo construídos**, trate-os como **quatro sistemas de primeira classe distribuídos atualmente em três fontes operacionais de repositório**: RPG Skill Tree e Volcanoes compartilham a `main` unificada do `neoforge-rpg-skilltree`; Enshrouded e Black Arcana permanecem em seus repositórios próprios enquanto separados.

Antes de auditar a primeira perk de cada lote o Chat 1 deve:

1. fazer fetch fresco das fontes operacionais: `neoforge-rpg-skilltree/main` + `plans/STATUS.md` para o RPG; a mesma `main`, filtrando as superfícies Volcanoes, + `docs/archive/volcanoes/STATUS.md` para Volcanoes; e `main` + `plans/STATUS.md` de Enshrouded e Black Arcana;
2. comparar os SHAs/heads atuais com o baseline mais recente registrado em `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
3. registrar `SEM DELTA RELEVANTE` quando nada pertinente mudou;
4. se houver avanço, inspecionar os planos/subsistemas alterados e, quando necessário, código/testes/CI;
5. identificar **toda capacidade jogável nova ou semanticamente alterada**, mesmo que nenhuma perk atual já a mencione;
6. classificar a cobertura como `COBERTA POR PERK EXISTENTE`, `PERK PRÓPRIA`, `ESPECIALIZAÇÃO`, `BRIDGE`, `COBERTO POR SISTEMA UNIVERSAL`, `PROGRESSÃO NATIVA AUTORITATIVA`, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`;
7. **não avançar o baseline do projeto enquanto qualquer capacidade detectada naquele delta estiver sem decisão, ação e fail-closed quando aplicável**.

O novo SHA só vira checkpoint depois que todas as linhas do delta tiverem disposição explícita.

A auditoria deve provar dois sentidos: **perk → provider** e **provider → árvore**. É proibido ignorar uma capacidade só porque o catálogo ainda não possui perk para ela.

Exemplos permanentes: O₂/respiração/pressão/proteção do Volcanoes; Arcane Resistance/Corruption Resistance/Strain do Black Arcana; Exposure/Flame/Sanctuary/Story do Enshrouded; qualquer nova superfície pública do RPG Skill Tree.

Detectar uma lacuna não altera a regra de **lotes exatos de 10**. Se a solução exigir uma perk fora do lote atual, documente a necessidade para ciclo posterior e não inicie uma décima primeira perk.

### 3. Catálogo Mestre — Notion

https://app.notion.com/p/145c547121ee43ecaaa6fdfb37161a9b?v=3c569db9f0db8155843e000c0761c61c

### 4. Dossiês das perks

https://github.com/Gustavaopere/neoforge-rpg-skilltree/tree/main/plans/03-skill-tree-perks/perks

### 5. Status

https://github.com/Gustavaopere/neoforge-rpg-skilltree/blob/main/plans/03-skill-tree-perks/perks/STATUS.md

### 6. Auditorias existentes

Procure em:

`plans/03-skill-tree-perks/perks/`

por arquivos `AUDITORIA-*.md`.

Use o arquivo que cobre o lote atual. Se ainda não existir, crie um.

### 7. Modlist atual

**Antes da primeira perk do lote**, confira:
1. a modlist mais recente na Biblioteca/arquivos do projeto;
2. a Auditoria Mestre da Modlist no Notion;
3. os `CURRENT-MODLIST.md`/deltas incorporados nos guias consolidados.

A modlist física é authority de presença/JAR/runtime. O Notion deve ser reconciliado quando divergir. Não contar dependências `jarjar` como mods top-level.

**Checkpoint reconciliado — 2026-09-07:** 612 entradas top-level, 21 updates, 9 adições e 4 remoções desde 06/09; 612 `Instalado`, 613/613 `Verificado`, 605/605 `modVersion` exatos e 7/7 JARs sem runtime version mantidos sem inferência.

Deltas de cobertura que merecem atenção:
- Bosses of Mass Destruction e Bosses'Rise: boss providers, mas só entram em perk por hook causal comprovado;
- Integrated Mowzie's Mobs 1.1.0: `Manter`; worldgen/placement integration, não inventar publicação pública exata;
- Ironsable x Wind's Spellbooks: bridge magia↔Sable, não nova escola;
- More Relics 1.7.7: `Manter`, porém provider-specific **FAIL-CLOSED** enquanto Relics 0.12.8 não tiver compatibilidade comprovada para o hook;
- Bits 'n' Bobs 2.3.1: `Manter`; conflito visual com Sulfuric Resonance é risco aceito;
- Mobstein 5.4.4 continua provider próprio de ressurreição corporal/experimentos/allies/estruturas/boss; perks internas do Mobstein não são nodes RPG;
- os antigos JARs concorrentes de Alex's Caves/Alex's Mobs e Spore/InfNexus listados no delta de 07/09 foram removidos fisicamente.

Se existir snapshot posterior a 07/09, **não reutilize estes números como estado atual**: reconcilie o novo delta antes de fechar o lote.

---

# RESPONSABILIDADE DO CHAT 1

Para **cada perk do lote**:

1. Faça fetch fresco da perk no Notion.
2. Aplique todos os critérios obrigatórios.
3. Cruze a perk com os quatro guias completos.
4. Identifique todos os mods/providers relevantes, incluindo os quatro projetos próprios e qualquer delta externo da modlist ainda não refletido no catálogo.
5. Para cada projeto próprio pertinente, defina provider/authority/boundary/evidência/causalidade/deduplicação/fallback/fail-closed e o pipeline que não pode ser duplicado.
6. Verifique bridges e integrações entre mods.
7. Verifique se mods menores/periféricos relevantes foram ignorados.
8. Consulte API, código-fonte ou documentação da **versão exata instalada** para NeoForge 1.21.1 quando necessário.
9. Valide:
   - dependências;
   - gates;
   - topologia;
   - função na árvore;
   - especialização;
   - PT-BR;
   - Mastery;
   - anti-abuso;
   - autoria causal;
   - deduplicação;
   - pipeline canônico único;
   - integração global;
   - ausência de NeoVitae;
   - cobertura da modlist.
10. Aplique `provider-native first`.
11. Defina Hook real.
12. Defina Gate.
13. Defina Fallback.
14. Defina FAIL-CLOSED quando não houver hook seguro.
15. Defina com precisão os testes que o Chat 3 deverá criar/completar e executar; **não execute a bateria de testes da implementação neste chat**.
16. Proíba double-count/double-processing.
17. Não invente mecânica de provider.

Se o design estiver errado, incompleto, contraditório ou impossível:

**CORRIJA NO NOTION.**

Depois faça novo fetch para confirmar que a alteração persistiu.

---

# ENTREGA OBRIGATÓRIA PARA O CHAT 2

Ao terminar a auditoria, cada arquivo individual de perk em:

`plans/03-skill-tree-perks/perks/`

deve estar suficientemente completo para o Chat 2 implementar **sem redesenhar**.

O dossiê precisa registrar claramente, conforme aplicável:

- comportamento final;
- providers;
- versões relevantes;
- hooks;
- gates;
- dependências;
- recursos consumidos;
- integrações com outros mods;
- fallback;
- fail-closed;
- anti-abuso;
- autoria;
- deduplicação;
- pipeline canônico;
- testes obrigatórios que deverão ser validados pelo Chat 3;
- o que NÃO deve ser feito;
- limitações conhecidas;
- pendências técnicas reais.

Não deixe decisões de design para o Chat 2 descobrir durante a implementação.

O resultado do Chat 1 deve ser uma **especificação executável como contrato**, não código de gameplay. Para cada perk, o arquivo `.md` deve permitir que o Chat 2 implemente sem consultar o Notion para reinterpretar intenção e sem refazer a auditoria dos guias.

## Limites do Chat 1

O Chat 1:

- **PODE** criar/editar dossiês, auditorias, `STATUS.md`, documentação e a PR do lote;
- **PODE** inspecionar código, documentação, testes e CI existentes de providers como evidência de auditoria;
- **NÃO implementa o runtime das perks**;
- **NÃO cria nem executa a bateria de testes da implementação das perks**;
- **NÃO faz merge**;
- deixa a **mesma branch/PR do lote aberta** para o Chat 2 continuar.


---

# AUDITORIA E STATUS

Atualize o arquivo `AUDITORIA-*.md` correspondente ao intervalo atual.

Se não existir um arquivo que cubra o lote, crie um com nomenclatura clara.

Atualize também:

https://github.com/Gustavaopere/neoforge-rpg-skilltree/blob/main/plans/03-skill-tree-perks/perks/STATUS.md

Separe claramente:

- PENDENTE;
- EM REVISÃO;
- BLOQUEADA;
- REPROVADA / REDESENHAR;
- APROVADA;
- LOTE FECHADO.

Fallback/fail-closed legítimo pode ser aprovado quando os critérios autorizarem, mas deve estar explicitamente documentado.

---

# REGRA DE LOTE

Cada ciclo trabalha **exatamente 10 perks consecutivas**.

Para determinar o lote:

1. leia `STATUS.md`;
2. identifique a primeira perk que ainda não esteja formalmente fechada pelo Chat 1;
3. forme o intervalo dessa perk + as 9 seguintes;
4. confirme que não está pulando códigos;
5. registre `INÍCIO` e `FIM` no começo da execução.

Não comece o lote seguinte no mesmo ciclo.

---

# FECHAMENTO DO CICLO

Quando as 10 perks estiverem completamente auditadas e especificadas:

1. finalize todas as correções de design;
2. atualize o Notion;
3. faça re-fetch das páginas alteradas;
4. atualize os 10 dossiês `.md` com o contrato completo;
5. registre os testes que o Chat 3 deverá validar;
6. atualize a auditoria;
7. atualize `STATUS.md` para refletir **DESIGN APROVADO / LOTE FECHADO PELO CHAT 1**;
8. revise o diff documental;
9. crie ou atualize a **branch/PR única do lote**;
10. resolva apenas reviews/problemas relativos ao design/documentação desta etapa;
11. **NÃO execute a bateria de testes da implementação**;
12. **NÃO faça merge**;
13. deixe a PR aberta e identificada como pronta para o Chat 2;
14. **PARE**.

Não inicie o próximo lote.

Ao encerrar, informe:

- intervalo efetivamente auditado;
- PR/branch que o Chat 2 deve continuar;
- perks com DESIGN APROVADO;
- perks bloqueadas/fail-closed;
- alterações feitas no Notion;
- integrações importantes encontradas;
- testes especificados para validação futura pelo Chat 3;
- pendências que o Chat 2 precisa respeitar.

**O Chat 1 nunca declara `IMPLEMENTAÇÃO CONFIRMADA` e nunca faz merge.**
