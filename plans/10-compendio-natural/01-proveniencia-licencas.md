# 10.01 — Proveniência, referências e licenças

## Objetivo

Definir a fronteira legal e técnica antes de qualquer reaproveitamento. Biology Dictionary, Field Guide e Wildex são **referências de comportamento/UX**, não autorização automática para copiar código, assets, traduções ou bancos de dados.

## Escopo

- registrar upstream, versão observada, loader e licença;
- distinguir ideia, comportamento observável, API pública, código-fonte e asset;
- impedir entrada de conteúdo sem proveniência no repositório;
- manter atribuições quando uma licença exigir;
- preferir reimplementação limpa quando a combinação de licenças for incerta ou desnecessária.

## Plano

### A — Criar manifesto de proveniência

Arquivos previstos:

```text
docs/compendium/UPSTREAM.md
docs/compendium/PROVENANCE.md
docs/compendium/ASSET_SOURCES.md
```

- [ ] registrar Biology Dictionary com URL canônica, commit/tag analisado e licença verificada no momento da implementação;
- [ ] registrar Field Guide com URL canônica, commit/tag analisado e licença verificada;
- [ ] registrar Wildex com URL canônica, commit/tag analisado e licença verificada;
- [ ] registrar qualquer quarto projeto usado como referência;
- [ ] guardar SHA/tag, não apenas uma URL mutável;
- [ ] separar licença do código da licença dos assets quando forem diferentes.

### B — Classificar cada recurso desejado

Para cada recurso inspirado em upstream, registrar uma das categorias:

- `BEHAVIOR_REFERENCE` — comportamento reimplementado sem copiar expressão/código;
- `PUBLIC_API` — integração por API pública/documentada;
- `CODE_REUSE` — código reutilizado somente após compatibilidade de licença confirmada;
- `ASSET_REUSE` — asset reutilizado somente com licença/permissão explícita;
- `NO_REUSE` — inspiração apenas conceitual.

Recursos iniciais a auditar:

| Recurso | Referência principal | Política inicial |
| --- | --- | --- |
| informações técnicas de entidade | Biology Dictionary | `BEHAVIOR_REFERENCE` |
| descoberta/categorias/notas | Field Guide | `BEHAVIOR_REFERENCE` |
| modelo 3D/bestiário/progresso | Wildex | `BEHAVIOR_REFERENCE` |
| loot range/XP/fraquezas | Wildex | `BEHAVIOR_REFERENCE` |
| dados de reprodução/estado especial | Biology Dictionary | `BEHAVIOR_REFERENCE` |
| flora/árvores/blocos | Field Guide | `BEHAVIOR_REFERENCE` |

Nenhuma linha muda para `CODE_REUSE`/`ASSET_REUSE` sem evidência documental.

### C — Não importar corpus de texto sem origem

- [ ] não copiar descrições/lore dos mods de referência;
- [ ] não copiar traduções de terceiros sem licença compatível;
- [ ] preferir nomes localizados que já existam no próprio mod instalado;
- [ ] produzir texto editorial pt-BR original, baseado em dados verificáveis;
- [ ] quando usar documentação oficial de um mod como fonte factual, registrar a origem em metadata editorial sem reproduzir texto protegido extensivamente.

### D — CI de proveniência

Criar posteriormente:

```text
scripts/compendium/validate_provenance.py
src/test/java/dev/gustavopere/rpgskilltree/compendium/ProvenanceManifestTest.java
```

O validador deve falhar se:

- um asset novo em `assets/rpgskilltree/compendium/` não tiver origem/autoria declarada;
- um corpus importado não tiver licença/autor;
- um arquivo marcado como derivado não apontar para upstream congelado;
- uma entrada editorial exigir fonte e a metadata estiver vazia.

## Verificação

- [ ] manifestos podem ser parseados automaticamente;
- [ ] todas as referências externas usadas no código/asset/corpus têm status explícito;
- [ ] nenhum upstream é vendorizado por conveniência;
- [ ] build continua independente da disponibilidade online dos upstreams.

## Acceptance

O subplano fecha quando a política de proveniência estiver codificada, testada e integrada, e qualquer material externo existente no Compêndio tiver origem/licença auditável.
