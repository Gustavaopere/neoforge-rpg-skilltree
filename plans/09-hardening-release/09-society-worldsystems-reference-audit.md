# 09.09 — Auditoria de referências dos sistemas sociais e de mundo

## Objetivo

Impedir que os Stages 14–20 transformem inspiração em cópia não autorizada. Este arquivo complementa `08-third-party-licenses-provenance.md`; não substitui o ledger global.

## Regra canônica

Todo projeto citado durante design é inicialmente `REFERENCE_ONLY` até existir auditoria de uma revisão/artefato imutável. Ideia, regra econômica ou conceito de gameplay pode orientar uma implementação clean-room; código, assets, schematics, modelos, texturas, traduções e estruturas não podem ser copiados sem direito verificado.

## Referências discutidas que devem entrar na matriz

- War ’n Taxes;
- War ’n Nobility;
- Warring Nations;
- The Winter Rescue / Frosted Heart e projetos relacionados usados como referência de inverno;
- Frostpunk, somente como referência conceitual/UX de crise térmica e política;
- Claim My Land e projetos equivalentes de seleção territorial;
- WorldEdit, apenas para estudar UX/algoritmos públicos quando licença/API permitir;
- CraterTown Boiler ou referência equivalente de caldeira/infraestrutura;
- MineColonies e Structurize;
- Create e addons efetivamente integrados;
- JourneyMap/MapFrontiers já auditados pelo Stage 13 quando a integração distrital reutilizar suas APIs.

A presença nesta lista **não afirma** licença atual. A implementação deve registrar para cada upstream: URL, versão do modpack, commit/tag/file ID, data, licença de código, licença de assets, classificação de uso e obrigações.

## Classificação de uso

Aplicar o vocabulário do Stage 09.08:

- `REFERENCE_ONLY` — estudo sem cópia;
- `DEPENDENCY_API` — integração independente por API/contrato permitido;
- `DERIVED_CODE` — somente após licença compatível + notices;
- `DERIVED_ASSET` — somente após direito explícito sobre assets;
- `PERMISSION_REQUIRED` — nenhuma derivação sem autorização escrita;
- `REVIEW_REQUIRED` — evidência insuficiente; fail-closed.

## Regras específicas para construções

Um schematic/blueprint de terceiro é asset. Não copiar uma construção porque ela pode ser visualizada em jogo ou baixada publicamente. O Stage 14 deve gerar sua própria matriz voxel a partir de especificações próprias, referências permitidas e assets do modpack.

Quando a inspiração reproduzir apenas a função — por exemplo, uma central térmica com estágios — geometria, layout, paleta e detalhes devem ser próprios salvo licença autorizando derivação.

## Regras específicas para mecânicas

Conceitos como impostos, voto censitário, vassalagem, salários, racionamento e redes de calor são ideias funcionais gerais. A implementação deve definir modelos de dados, fórmulas, UX e código próprios. Não copiar tabelas, textos, nomes específicos, código ou assets de mods/jogos de referência.

## Gate pré-implementação

Antes de qualquer PR dos Stages 14–20 que use material externo:

1. identificar o upstream e revisão imutável;
2. atualizar `SOURCES.md`/`THIRD_PARTY_NOTICES.md` quando aplicável;
3. registrar `REFERENCE_ONLY`, `DEPENDENCY_API` ou derivação autorizada;
4. para ARR/custom/unknown, trabalhar clean-room ou obter permissão;
5. executar o validator de proveniência do Stage 09.

## Acceptance

Nenhum Stage 14–20 pode depender de código/asset de referência cuja licença permaneça desconhecida ou incompatível. A ausência de permissão reduz o uso para referência conceitual; nunca bloqueia uma implementação original.