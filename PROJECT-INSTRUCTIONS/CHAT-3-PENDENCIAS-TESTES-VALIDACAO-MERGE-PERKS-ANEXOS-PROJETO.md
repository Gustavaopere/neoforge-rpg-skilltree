# CHAT 3 — PENDÊNCIAS, TESTES, VALIDAÇÃO E MERGE DAS PERKS

Trabalhe no projeto:

https://github.com/Gustavaopere/neoforge-rpg-skilltree

Minecraft: NeoForge 1.21.1  
Java: 21

Sua responsabilidade neste chat é **REVISAR A IMPLEMENTAÇÃO PRODUZIDA PELO CHAT 2, RESOLVER PENDÊNCIAS TÉCNICAS, TESTAR, VALIDAR, CORRIGIR E FAZER O MERGE DO LOTE**.

Você **NÃO deve refazer a auditoria integral do Chat 1** nem redesenhar perks por iniciativa própria.

Trabalhe sempre em **LOTES EXATOS DE 10 perks consecutivas**.

Não fixe o intervalo manualmente neste prompt. Antes de iniciar cada ciclo, determine o **próximo lote de 10 perks com DESIGN APROVADO e CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO, mas ainda sem IMPLEMENTAÇÃO CONFIRMADA / MERGED**, usando `STATUS.md`, os dossiês individuais, a auditoria e a PR existente.

---

## LEITURA OBRIGATÓRIA

### 1. Critérios obrigatórios

Leia integralmente:

`CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`

Respeite especialmente:

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
- read-only real;
- PT-BR quando houver conteúdo player-facing.

### 2. Dossiês individuais

Leia integralmente os **10 dossiês do lote atual** em:

`plans/03-skill-tree-perks/perks/`

### 3. Auditoria do lote

Leia integralmente o `AUDITORIA-*.md` correspondente.

### 4. Status

Leia:

`plans/03-skill-tree-perks/perks/STATUS.md`

### 5. Código e PR do lote

Localize a **mesma branch/PR iniciada pelo Chat 1 e continuada pelo Chat 2**.

Leia o diff completo do lote antes de alterar código.

---

# SOBRE OS QUATRO GUIAS

Os guias completos anexados ao projeto são:

- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`

A auditoria integral e a descoberta de providers pertencem ao Chat 1.

O Chat 3 **não deve repetir automaticamente a leitura integral dos quatro guias em cada lote**. Consulte trechos pertinentes quando uma pendência técnica, falha de teste ou divergência de provider exigir confirmação adicional.

Se a evidência técnica mostrar que o contrato do dossiê está errado de forma que a correção alteraria identidade, efeito, provider, gate, dependência, topologia, autoridade ou semântica essencial da perk:

- NÃO redesenhe silenciosamente;
- preserve fail-closed;
- registre a evidência;
- marque a perk como bloqueada para merge;
- indique explicitamente que o ponto deve voltar ao Chat 1.

Se o problema puder ser corrigido **sem alterar o design aprovado**, corrija no Chat 3.

---

# REGRA DE LOTE

Cada ciclo trabalha **exatamente 10 perks consecutivas**.

Para determinar o lote:

1. leia `STATUS.md`;
2. encontre a primeira perk com **DESIGN APROVADO** e **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO**, mas sem **IMPLEMENTAÇÃO CONFIRMADA / MERGED**;
3. forme o intervalo dessa perk + as 9 seguintes;
4. confirme que as 10 pertencem ao mesmo lote produzido pelo Chat 1 e Chat 2;
5. confirme que a PR/branch do lote existe;
6. registre `INÍCIO` e `FIM` no começo da execução.

Não inicie o lote seguinte no mesmo ciclo.

---

# RESPONSABILIDADE DO CHAT 3

Para cada perk:

1. compare o código com o contrato do dossiê;
2. identifique implementação ausente, incorreta, incompleta ou divergente;
3. resolva pendências técnicas que não exijam redesign;
4. preserve provider-native first;
5. preserve gates, dependências e authority;
6. preserve fallback/fail-closed;
7. confirme deduplicação e ausência de double-processing;
8. confirme anti-abuso/Mastery;
9. confirme autoria causal;
10. confirme ausência de geração/duplicação acidental;
11. confirme que recursos e custos usam o pipeline canônico;
12. confirme que consultas read-only permanecem read-only;
13. crie ou complete os testes especificados pelo Chat 1;
14. acrescente testes adicionais quando uma falha ou risco real exigir;
15. corrija bugs encontrados pelos testes;
16. atualize dossiê/auditoria/status com evidência real.

---

# TESTES E VALIDAÇÕES

Execute, conforme aplicável ao lote:

- testes unitários;
- GameTests;
- testes de integração;
- testes específicos de adapters/providers;
- testes de deduplicação/idempotência;
- testes de fail-closed/fallback;
- testes anti-abuso/Mastery;
- testes de dedicated-server safety;
- build NeoForge;
- dedicated-server smoke;
- validações de datapack/reload quando aplicáveis;
- qualquer matriz específica registrada nos dossiês;
- CI da PR.

Não declare um teste como executado se ele não tiver sido realmente executado.

Se um teste não for aplicável, registre `N/A` com justificativa.

---

# REGRA DA PR ÚNICA DO LOTE

O Chat 3 deve continuar a **mesma branch/PR** usada pelos Chats 1 e 2.

Antes do merge:

1. revise todo o diff acumulado de design + implementação + testes;
2. resolva reviews válidos;
3. corrija falhas reais;
4. confirme que não há pendências técnicas bloqueantes;
5. confirme que não há perk que precise voltar ao Chat 1;
6. exija **CI GREEN quando aplicável**;
7. atualize dossiês/auditoria/`STATUS.md` com a evidência final;
8. marque as 10 perks como **IMPLEMENTAÇÃO CONFIRMADA** somente após validação real.

---

# CHECKLIST FINAL POR PERK

Use checklist explícita no dossiê, conforme aplicável:

- [x] Design aprovado pelo Chat 1
- [x] Código presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Provider-native confirmado
- [x] Gate/dependências confirmados
- [x] Fallback/fail-closed confirmado
- [x] Deduplicação confirmada
- [x] Anti-abuso/Mastery confirmado
- [x] Autoria causal confirmada
- [x] Testes unitários verdes
- [x] GameTests verdes
- [x] Testes de integração verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] CI GREEN
- [x] IMPLEMENTAÇÃO CONFIRMADA

Use `N/A — justificativa` quando um item realmente não se aplicar.

Nunca marque `[x]` sem evidência.

---

# BLOQUEIOS

Não faça merge se qualquer uma das 10 perks possuir:

- falha de teste não resolvida;
- erro de compilação/build;
- CI obrigatória vermelha;
- divergência de provider/API que altere o design aprovado;
- duplicação de pipeline;
- exploit de recurso/Mastery;
- falta de fail-closed onde obrigatório;
- regressão conhecida grave;
- pendência técnica que comprometa o contrato.

Quando o bloqueio exigir redesign, documente o retorno ao Chat 1 e **PARE sem merge**.

---

# FECHAMENTO DO CICLO

Quando as 10 perks estiverem validadas:

1. resolva todas as pendências técnicas solucionáveis;
2. complete e execute os testes aplicáveis;
3. execute GameTests e integrações aplicáveis;
4. faça build NeoForge;
5. faça dedicated-server smoke quando aplicável;
6. revise o diff completo;
7. resolva reviews/problemas reais;
8. obtenha CI GREEN quando aplicável;
9. atualize os 10 dossiês;
10. atualize a auditoria;
11. atualize `STATUS.md` para **IMPLEMENTAÇÃO CONFIRMADA**;
12. faça merge da PR na `main`;
13. faça fetch/checkout fresco da `main`;
14. confirme que a `main` contém o merge esperado;
15. registre o SHA final da `main`;
16. confirme que a CI pós-merge está saudável quando houver workflow aplicável;
17. **PARE**.

Não inicie o próximo lote.

Ao encerrar, informe:

- intervalo efetivamente validado;
- PR mergeada;
- SHA final da `main`;
- perks com IMPLEMENTAÇÃO CONFIRMADA;
- testes executados e resultado;
- CI;
- fallbacks/fail-closed finais;
- pendências não bloqueantes restantes, se existirem;
- qualquer ponto devolvido ao Chat 1;
- confirmação do estado da `main`.

**Somente o Chat 3 faz merge dos lotes de perks.**
