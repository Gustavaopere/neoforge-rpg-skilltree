# Hardening Plan — Third-Party Licenses & Provenance

**Goal:** garantir que o repositório público, seus sources, assets e artefatos distribuídos possuam proveniência auditável e respeitem as licenças/permissões de todo upstream usado como dependência, referência, base ou fonte de código/asset.

> Este plano é um gate técnico de compliance. Ele não substitui aconselhamento jurídico. Quando os termos forem ambíguos, a política do projeto é **FAIL-CLOSED**: não copiar/publicar o material até a permissão ser verificada.

## 1. Princípios obrigatórios

- [ ] Repositório público **não** é sinônimo de código reutilizável.
- [ ] Ausência de `LICENSE` **não** concede permissão para copiar/modificar/redistribuir.
- [ ] Código e assets devem ser auditados separadamente; um mod pode usar licença permissiva no código e All Rights Reserved em texturas/modelos/áudio.
- [ ] Ideias, comportamento observado e contratos públicos podem servir como referência, mas uma implementação clean-room não deve copiar expressão protegida, código ou assets proibidos.
- [ ] Toda cópia/adaptação substancial precisa apontar para upstream exato, commit/tag e arquivos/classes de origem.
- [ ] Avisos de copyright/licença exigidos pelo upstream devem permanecer nos locais exigidos.
- [ ] Código copyleft (GPL/LGPL e equivalentes) só pode ser incorporado depois de análise explícita das obrigações sobre o trabalho combinado/derivado.
- [ ] Licença custom/All Rights Reserved só permite o que o texto ou uma permissão escrita autorizar explicitamente.
- [ ] Nenhum artefato de release pode ser publicado com item `PERMISSION_REQUIRED`, `REVIEW_REQUIRED` ou `UNKNOWN` que tenha sido efetivamente copiado/incorporado.

## 2. Classificação de uso

Todo upstream listado em `SOURCES.md`/`THIRD_PARTY_NOTICES.md` deve possuir uma ou mais classificações:

- `REFERENCE_ONLY`: inspiração arquitetural/funcional; nenhum código/asset copiado.
- `DEPENDENCY_API`: integração escrita contra API/dependência permitida.
- `DERIVED_CODE`: código copiado/adaptado; exige licença compatível, provenance de arquivo/commit e notices.
- `DERIVED_ASSET`: asset copiado/adaptado; exige direito específico sobre assets.
- `BUNDLED_BINARY`: binário redistribuído; exige termos de redistribuição verificados.
- `PERMISSION_REQUIRED`: só pode prosseguir com autorização escrita verificável.
- `REVIEW_REQUIRED`: licença/compatibilidade ainda não determinada; nenhum material pode ser incorporado até resolução.

## 3. Registro mínimo por upstream

Para cada dependência/base/referência relevante registrar:

- projeto e autor/organização;
- URL canônica;
- branch/tag/commit auditado;
- versão do mod usada no pack quando aplicável;
- classificação de uso;
- licença do código;
- licença de assets, se distinta;
- arquivos/classes/recursos efetivamente derivados, se houver;
- arquivos locais que contêm derivação;
- obrigações de notice/source/copyleft;
- evidência de permissão adicional, se existir;
- data da verificação;
- status `VERIFIED`, `REFERENCE_ONLY`, `REVIEW_REQUIRED` ou `PERMISSION_REQUIRED`.

## 4. Auditoria retroativa obrigatória

O `SOURCES.md` histórico lista projetos usados durante o desenvolvimento, mas não prova se houve cópia literal. Fazer auditoria de código/recursos para distinguir referência, API e derivação real.

Auditar ao menos:

- Passive Skill Tree original;
- fork NeoForge 1.21.1 de Passive Skill Tree;
- Iron's Spells 'n Spellbooks;
- Ars Nouveau;
- Epic Fight;
- Create;
- Curios;
- addons de skill tree fornecidos pelo usuário;
- NeoSync (Stage 12);
- MapFrontiers, JourneyMap API e Compass to Map (Stage 13);
- qualquer outro upstream citado futuramente em plans/docs/commits como “copiado”, “portado”, “adaptado”, “baseado em” ou equivalente.

A auditoria deve procurar também headers de copyright, namespaces/classes muito semelhantes e assets binários que não aparecem em pesquisa textual.

## 5. Casos já verificados nesta rodada

### NeoSync

- upstream: `breakinblocks/NeoSync`;
- licença consultada: MIT;
- uso atual de planejamento: `REFERENCE_ONLY`;
- derivação futura de código é possível sob MIT, mantendo copyright + aviso de licença e provenance exata.

### MapFrontiers

- upstream: `alejandrocoria/MapFrontiers`;
- licença consultada: MIT;
- uso planejado: `REFERENCE_ONLY`, com possibilidade de `DERIVED_CODE` somente se houver ganho real;
- qualquer derivação deve preservar o aviso MIT e indicar arquivos/commit de origem.

### Compass to Map

- upstream: `KURONAMI333/compass-to-map`;
- licença consultada: All Rights Reserved/custom;
- termos consultados proíbem copiar código/partes para projeto público sem permissão escrita;
- status: `REFERENCE_ONLY` / `PERMISSION_REQUIRED` para qualquer cópia;
- Stage 13 deve implementar funcionalidade equivalente independentemente.

### JourneyMap API

- upstream: `TeamJM/journeymap-api`;
- termos custom TeamJM;
- uso planejado: `DEPENDENCY_API`;
- não copiar/embutir source ou class files da API fora das permissões do upstream;
- validar a versão exata da API usada pelo modpack na implementação.

### Iron's Spells 'n Spellbooks

- upstream: `iron431/irons-spells-n-spellbooks`;
- licença consultada no branch 1.21: All Rights Reserved/custom;
- termos permitem escrever código próprio que use o mod como dependência e addons/datapacks;
- não tratar como fonte de código público derivável;
- assets não podem ser reutilizados segundo os termos consultados;
- uso deve permanecer `DEPENDENCY_API`/`REFERENCE_ONLY`, salvo permissão escrita adicional.

### Ars Nouveau

- upstream: `baileyholl/Ars-Nouveau`;
- código: GNU LGPL v3 segundo `license.txt` consultado;
- assets/texturas/modelos: All Rights Reserved salvo declaração/permissão específica;
- integração via dependência é preferível;
- qualquer `DERIVED_CODE` exige cumprir LGPL; `DERIVED_ASSET` fica proibido sem permissão específica.

### Epic Fight

- upstream: `Antikythera-Studios/epicfight`;
- licença consultada: GNU GPL v3;
- integração/API pode ser separada da cópia;
- qualquer código adaptado/copied exige análise de copyleft antes de merge/release.

### Create

- upstream: `Creators-of-Create/Create`;
- branch 1.21.1 consultado: código sob MIT; `src/main/resources/assets/` All Rights Reserved;
- código derivado pode ser permitido sob MIT com notice; assets não devem ser copiados/adaptados sem permissão adicional.

### Curios

- upstream: `TheIllusiveC4/Curios`;
- licença consultada: GNU LGPL v3 ou posterior;
- uso normal recomendado: `DEPENDENCY_API`;
- derivação de código exige cumprimento das obrigações LGPL aplicáveis.

### Passive Skill Tree / port 1.21.1

- metadados consultados declaram `GNU GENERAL PUBLIC LICENSE`, mas a versão exata e a compatibilidade com qualquer código historicamente derivado ainda precisam ser fechadas;
- status para `DERIVED_CODE`: `REVIEW_REQUIRED` até auditoria formal;
- não adicionar novas cópias antes disso.

## 6. THIRD_PARTY_NOTICES

Manter `THIRD_PARTY_NOTICES.md` na raiz como ledger público. O arquivo deve distinguir:

- dependência/referência sem código copiado;
- código efetivamente derivado;
- assets efetivamente derivados;
- licenças verificadas;
- itens ainda pendentes.

Não afirmar que um upstream “está incluído” apenas porque serviu de inspiração.

## 7. SOURCES.md

`SOURCES.md` é índice de proveniência, não licença. Cada entrada deve apontar para `THIRD_PARTY_NOTICES.md` e declarar o tipo de uso conhecido. A frase histórica “private integration work” deve ser removida, pois o repositório é público.

## 8. CI / validator futuro

Adicionar validator que falha quando:

- arquivo marcado como derivado não possui upstream/commit/licença;
- provenance aponta para licença `UNKNOWN/REVIEW_REQUIRED/PERMISSION_REQUIRED` e material copiado está sendo distribuído;
- notice obrigatório está ausente;
- asset ARR aparece em lista de derivados sem evidência de permissão;
- nova entrada de upstream surge em metadata de derivação sem `THIRD_PARTY_NOTICES` correspondente.

O validator não substitui auditoria humana de compatibilidade de licença.

## 9. Licença do próprio projeto

No momento desta auditoria não foi encontrado `LICENSE` na raiz do RPG Skill Tree. **Não escolher automaticamente uma licença do projeto** antes de concluir a auditoria retroativa de código derivado/copy-left, porque obrigações de upstream podem restringir a licença possível para partes ou para o trabalho combinado.

A ausência de uma licença própria também não remove obrigações de copyright/licença dos upstreams.

## Acceptance

- `SOURCES.md` público e atualizado;
- `THIRD_PARTY_NOTICES.md` presente;
- auditoria retroativa concluída para todo código/asset efetivamente derivado;
- nenhum material ARR/sem licença incorporado sem permissão;
- notices/copyleft satisfeitos para derivados permitidos;
- release gate bloqueia publicação enquanto houver provenance material não resolvida.