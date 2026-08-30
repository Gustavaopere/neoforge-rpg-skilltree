# Hardening Plan — Third-Party Licenses & Provenance

**Goal:** garantir que o repositório público, seus sources, assets e artefatos distribuídos possuam proveniência auditável e respeitem as licenças/permissões de todo upstream usado como dependência, referência, base ou fonte de código/asset.

> Este plano é um gate técnico de compliance, não aconselhamento jurídico. Quando termos/evidência forem ambíguos, a política do projeto é **FAIL-CLOSED**: não copiar/publicar o material até a permissão ser verificada.

## 1. Princípios obrigatórios

- [ ] Repositório público não significa código reutilizável.
- [ ] Ausência de `LICENSE` não concede permissão para copiar/modificar/redistribuir.
- [ ] Código e assets são auditados separadamente.
- [ ] Referência comportamental/arquitetural não autoriza copiar expressão protegida, código ou assets.
- [ ] Toda derivação substancial aponta para upstream, **revisão imutável** (commit/tag/artifact ID), arquivos de origem e arquivos locais.
- [ ] Toda afirmação de licença usada para autorizar derivação registra data de verificação e snapshot imutável do termo aplicável.
- [ ] Avisos de copyright/licença exigidos são preservados.
- [ ] GPL/LGPL e equivalentes exigem análise explícita das obrigações do trabalho combinado/derivado.
- [ ] Licença custom/All Rights Reserved permite somente o que o texto ou permissão escrita autoriza.
- [ ] Nenhum release distribui material derivado com `PERMISSION_REQUIRED`, `REVIEW_REQUIRED` ou `UNKNOWN`.

## 2. Classificação de uso

- `REFERENCE_ONLY`: inspiração/estudo; nenhum código/asset copiado.
- `DEPENDENCY_API`: integração própria contra dependência/API.
- `DERIVED_CODE`: código copiado/adaptado; exige licença compatível, revisão imutável e notices.
- `DERIVED_ASSET`: asset copiado/adaptado; exige direito específico.
- `BUNDLED_BINARY`: binário redistribuído; exige direito de redistribuição verificado.
- `PERMISSION_REQUIRED`: somente com autorização escrita verificável.
- `REVIEW_REQUIRED`: evidência insuficiente; nenhuma derivação pode entrar.

## 3. Registro mínimo por upstream

Registrar:

- projeto/autor/organização;
- URL canônica;
- versão do mod usada no pack, quando aplicável;
- **commit/tag/artifact/file ID imutável auditado**;
- data de verificação;
- classificação de uso;
- licença do código;
- licença de assets, se distinta;
- arquivos/classes/recursos efetivamente derivados;
- arquivos locais derivados;
- obrigações de notice/source/copyleft;
- evidência de permissão adicional;
- status final.

Uma URL de branch (`main`, `1.21.1`) sozinha nunca conta como evidência reprodutível de licença.

## 4. Auditoria retroativa obrigatória

O `SOURCES.md` histórico não prova se houve cópia literal. Auditar código e recursos para distinguir referência, API e derivação real.

Escopo mínimo:

- Passive Skill Tree original e fork NeoForge 1.21.1;
- Iron's Spells 'n Spellbooks;
- Ars Nouveau;
- Epic Fight;
- **Goety**;
- **Malum**;
- **Eidolon: Repraised**;
- **Identity2**;
- Create;
- Curios;
- addons de skill tree históricos;
- NeoSync (Stage 12);
- MapFrontiers, JourneyMap API e Compass to Map (Stage 13);
- qualquer outro provider declarado em `build.gradle`, `neoforge.mods.toml`, adapters/runtime ou citado como “copiado”, “portado”, “adaptado”, “baseado em” ou equivalente.

A auditoria procura também headers de copyright, namespaces/classes muito semelhantes e assets binários invisíveis a pesquisa textual.

## 5. Evidência capturada nesta rodada — 2026-08-30

### Fontes com snapshot de source imutável

- NeoSync — `breakinblocks/NeoSync@131709b52f1cf25c85f2cd02a3b4a93cb08979d0`; MIT observado; `REFERENCE_ONLY`, derivação futura somente com notice/proveniência.
- MapFrontiers — `alejandrocoria/MapFrontiers@dea25ae7e85b0b12c43dee89062b4199f6d361a9` na linha 1.21.1; MIT observado; `REFERENCE_ONLY`/derivação permitida apenas com notice/proveniência.
- Compass to Map — `KURONAMI333/compass-to-map@79d0aa8caeb025d2c8df3e4fb1dd87f2d3ab7d1e`; commit altera licença para All Rights Reserved; `REFERENCE_ONLY`, qualquer cópia é `PERMISSION_REQUIRED`.
- JourneyMap API — `TeamJM/journeymap-api@4a57dee370a0ae70660ae66d3dc5363e670fc1ee`, branch API 1.21.1/2.0.0; termos TeamJM; `DEPENDENCY_API`.

### Compatibilidades atuais com artifact ID imutável

- Goety — CurseForge project 586095 / file `8689429` / 3.1.4; metadata observado como MIT; `DEPENDENCY_API`; source derivation ainda `REVIEW_REQUIRED` até mapear source commit correspondente.
- Malum — project 484064 / file `7307339` / 1.8.2; metadata observado como LGPLv3; `DEPENDENCY_API`; derivação `REVIEW_REQUIRED` até mapear source commit/obrigações.
- Eidolon: Repraised — project 870250 / file `8064602` / 1.21.1-0.5.0.2; metadata observado como LGPLv3; `DEPENDENCY_API`; derivação `REVIEW_REQUIRED` até mapear source commit.
- Identity2 — project 1238155 / file `8439845` / 2.2.1; All Rights Reserved; `DEPENDENCY_API`; qualquer cópia/adaptação é `PERMISSION_REQUIRED`.
- Ars Nouveau — Modrinth version `ugLa4qlw`; `DEPENDENCY_API`; derivação continua `REVIEW_REQUIRED` até source snapshot exato.
- Epic Fight — Modrinth version `8HHhJt6i`; `DEPENDENCY_API`; derivação continua `REVIEW_REQUIRED` até source snapshot/copyleft review.

### Ainda não autorizados para derivação por esta rodada

Iron's, Create, Curios, Passive Skill Tree e seu port, além dos doadores históricos de skill tree, permanecem `REVIEW_REQUIRED` para qualquer cópia/adaptação até `THIRD_PARTY_NOTICES.md` receber revisão imutável adequada. Integrações independentes/API podem continuar somente dentro dos termos conhecidos do provider.

## 6. Ledger público

`THIRD_PARTY_NOTICES.md` é o ledger público e deve distinguir:

- dependência/referência sem código copiado;
- código/asset efetivamente derivado;
- snapshot imutável usado para a decisão;
- data de verificação;
- licença/termos observados;
- obrigações e pendências.

`SOURCES.md` é apenas o índice; não é licença.

## 7. CI / validator

Adicionar validator que falha quando:

- compatibility target novo aparece em build/mod metadata sem entrada no ledger;
- arquivo derivado não possui upstream/revisão imutável/licença;
- `DERIVED_CODE`/`DERIVED_ASSET` aponta para `UNKNOWN`, `REVIEW_REQUIRED` ou `PERMISSION_REQUIRED`;
- notice obrigatório está ausente;
- asset ARR aparece em derivados sem permissão;
- evidência de licença é somente URL mutável de branch;
- data de verificação está ausente para decisão `VERIFIED`/derivação autorizada.

O validator não substitui auditoria humana de compatibilidade de licença.

## 8. Licença do próprio projeto

O `gradle.properties` declara atualmente `mod_license=GNU GENERAL PUBLIC LICENSE`, mas não há `LICENSE` raiz capturado nesta auditoria. Não assumir automaticamente uma versão/escopo final da licença do projeto até reconciliar essa metadata com a auditoria retroativa e as obrigações de código historicamente derivado.

## Acceptance

- `SOURCES.md` cobre todos os providers de compile/runtime integration;
- `THIRD_PARTY_NOTICES.md` contém evidência reprodutível e data para toda decisão que autorize derivação;
- auditoria retroativa fecha todo código/asset efetivamente derivado;
- nenhum material ARR/sem licença entra sem permissão;
- notices/copyleft são satisfeitos;
- release gate bloqueia publicação enquanto houver proveniência material não resolvida.