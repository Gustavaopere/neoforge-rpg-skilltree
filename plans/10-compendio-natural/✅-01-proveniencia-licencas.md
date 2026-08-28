# 10.01 — Proveniência, referências e licenças

## Objetivo

Definir a fronteira legal e técnica antes de qualquer reaproveitamento. Biology Dictionary, Field Guide e Wildex são **referências de comportamento/UX**, não autorização automática para copiar código, assets, traduções ou bancos de dados.

## Escopo entregue

- [x] registrar upstream, versão observada, loader e licença;
- [x] distinguir ideia, comportamento observável, API pública, código-fonte e asset;
- [x] impedir entrada de conteúdo sem proveniência no repositório;
- [x] manter atribuições quando uma licença exigir;
- [x] preferir reimplementação limpa quando a combinação de licenças for incerta ou desnecessária.

## A — Manifestos de proveniência

Arquivos integrados:

```text
docs/compendium/UPSTREAM.md
docs/compendium/PROVENANCE.md
docs/compendium/ASSET_SOURCES.md
```

- [x] Biology Dictionary registrado com URL canônica, branch observada, SHA congelado, versão 1.2.1, Minecraft 1.21.1, loader e licença LGPL-3.0-or-later;
- [x] Field Guide registrado com URL canônica, branch observada, SHA congelado, versão 1.15.2, Minecraft 1.21.1, loader e licença MIT;
- [x] Wildex registrado com URL canônica, SHA congelado, versão 3.0.0, Minecraft 1.21.1, NeoForge e licença CC BY-NC 4.0;
- [x] nenhum quarto projeto externo foi usado como referência neste escopo; o manifesto é extensível para referências futuras;
- [x] todos os upstreams usam SHA Git de 40 caracteres, e o validador rejeita refs flutuantes no campo `source_sha`;
- [x] licença de código e de assets são campos distintos; quando a licença de assets não é inequívoca, a política é `NO_REUSE`.

### Upstreams congelados

| Projeto | SHA auditado | Código | Assets | Política |
| --- | --- | --- | --- | --- |
| Biology Dictionary | `5b70858371960d95a4ffba1ef4c1320aa94452e8` | LGPL-3.0-or-later | licença separada não confirmada | `BEHAVIOR_REFERENCE` / `NO_REUSE` |
| Field Guide | `a206cf81a4465e453b0663b0173066f30dcdc348` | MIT | All Rights Reserved salvo indicação específica | `BEHAVIOR_REFERENCE` / `NO_REUSE` |
| Wildex Bestiary | `b67267f6e664af58fe4ff430ba83c78a379029a5` | CC BY-NC 4.0 | tratado conservadoramente sob o mesmo trabalho/licença | `BEHAVIOR_REFERENCE` / `NO_REUSE` |

Nenhum código, asset ou corpus desses três projetos foi incorporado.

## B — Classificação dos recursos desejados

As categorias canônicas foram codificadas em `docs/compendium/PROVENANCE.md`:

- `BEHAVIOR_REFERENCE` — comportamento reimplementado sem copiar expressão/código;
- `PUBLIC_API` — integração por API pública/documentada;
- `CODE_REUSE` — reutilização permitida somente depois de compatibilidade/obrigações documentadas;
- `ASSET_REUSE` — reutilização permitida somente com licença/permissão explícita e registro;
- `NO_REUSE` — nenhuma reutilização.

Recursos iniciais:

| Recurso | Referência principal | Política efetiva |
| --- | --- | --- |
| informações técnicas de entidade | Biology Dictionary | `BEHAVIOR_REFERENCE` |
| descoberta/categorias/notas | Field Guide | `BEHAVIOR_REFERENCE` |
| modelo 3D/bestiário/progresso | Wildex | `BEHAVIOR_REFERENCE` |
| loot range/XP/fraquezas | Wildex | `BEHAVIOR_REFERENCE` |
| dados de reprodução/estado especial | Biology Dictionary | `BEHAVIOR_REFERENCE` |
| flora/árvores/blocos | Field Guide | `BEHAVIOR_REFERENCE` |

- [x] nenhuma linha foi promovida para `CODE_REUSE` ou `ASSET_REUSE`;
- [x] o validador exige obrigações explícitas caso uma política futura seja promovida para reutilização.

## C — Corpus e traduções

- [x] não foram copiadas descrições/lore dos mods de referência;
- [x] não foram copiadas traduções de terceiros;
- [x] a política registra preferência por localization do próprio mod instalado para nomes existentes;
- [x] o corpus editorial futuro deve ser redação original em pt-BR baseada em dados verificáveis;
- [x] conteúdo explicitamente importado exige autor, licença e origem; o teste negativo comprova que metadata ausente é rejeitada.

## D — CI de proveniência

Implementado:

```text
scripts/compendium/validate_provenance.py
scripts/compendium/test_provenance.py
```

O projeto optou por teste Python stdlib-only para o manifesto, em vez de criar um teste Java que apenas duplicaria a leitura dos mesmos arquivos. O gate é executado diretamente no workflow principal antes dos validators/build NeoForge.

O validador falha quando:

- [x] um asset novo em `src/main/resources/assets/rpgskilltree/compendium/` não está registrado;
- [x] um corpus explicitamente importado não possui autor/licença/origem;
- [x] um upstream usa `source_sha` flutuante ou malformado;
- [x] um asset `DERIVED` não aponta para SHA Git congelado;
- [x] um asset externo/derivado não possui os metadados mínimos exigidos;
- [x] manifests possuem schema/JSON inválido, IDs duplicados, política desconhecida ou referência para upstream inexistente.

## Verificação executada

- [x] manifests são parseados automaticamente a partir dos blocos JSON versionados;
- [x] as três referências externas usadas no design têm status e proveniência explícitos;
- [x] nenhum upstream foi vendorizado;
- [x] validator e testes são offline/stdlib-only, portanto o build não depende da disponibilidade dos upstreams;
- [x] NeoForge build e JAR verification passaram;
- [x] dedicated-server smoke passou depois da integração com a `main`.

### Evidência TDD

| Ciclo | RED | GREEN |
| --- | --- | --- |
| manifests obrigatórios | run `33185945525` / #741 | run `33186142544` / #744 |
| validator/fixtures negativos | run `33186347454` / #746 | run `33186476413` / #747 |
| SHA congelado para `DERIVED` | run `33186767226` / #749 | run `33186936920` / #751 |

### Evidência integrada

```text
Implementation head: 8981715ff5b359b2e2689cd72b344a8efc074411
Merged PR: #55
Merge commit: b4d84e9078b27349cc691ec2875574ff67246101
Post-merge main CI: 33187232908 / run #755
Compendium provenance tests: success
NeoForge build: success
Built JAR verification: success
Dedicated-server smoke: success
Acceptance: satisfied
```

## Acceptance

**Satisfied.** A política de proveniência está codificada, testada e integrada; os materiais externos atualmente usados pelo Compêndio são apenas referências documentadas de comportamento, sem reutilização de código/assets/corpus, e o CI impede a entrada silenciosa das classes de material externo cobertas pelo contrato deste estágio.
