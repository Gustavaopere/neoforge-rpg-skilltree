# Compêndio Natural — Política de proveniência

Este documento define como o Stage 10 diferencia inspiração, uso de API, reutilização de código, reutilização de assets e conteúdo editorial. A regra padrão é **reimplementação limpa**.

<!-- compendium-provenance:v1 -->
```json
{
  "schema": 1,
  "default_policy": "NO_REUSE",
  "allowed_categories": [
    "BEHAVIOR_REFERENCE",
    "PUBLIC_API",
    "CODE_REUSE",
    "ASSET_REUSE",
    "NO_REUSE"
  ],
  "feature_references": [
    {
      "feature": "entity_technical_information",
      "upstream_id": "biology_dictionary",
      "policy": "BEHAVIOR_REFERENCE"
    },
    {
      "feature": "discovery_categories_notes",
      "upstream_id": "field_guide",
      "policy": "BEHAVIOR_REFERENCE"
    },
    {
      "feature": "entity_3d_preview_bestiary_progress",
      "upstream_id": "wildex",
      "policy": "BEHAVIOR_REFERENCE"
    },
    {
      "feature": "loot_ranges_xp_weaknesses",
      "upstream_id": "wildex",
      "policy": "BEHAVIOR_REFERENCE"
    },
    {
      "feature": "breeding_and_special_entity_state",
      "upstream_id": "biology_dictionary",
      "policy": "BEHAVIOR_REFERENCE"
    },
    {
      "feature": "flora_trees_blocks",
      "upstream_id": "field_guide",
      "policy": "BEHAVIOR_REFERENCE"
    }
  ]
}
```

## Categorias

- `BEHAVIOR_REFERENCE`: comportamento/UX observado e reimplementado independentemente, sem copiar código, expressão textual ou assets.
- `PUBLIC_API`: integração por API pública/documentada do upstream ou do mod alvo.
- `CODE_REUSE`: código externo incorporado somente depois de compatibilidade de licença e obrigações documentadas no manifesto.
- `ASSET_REUSE`: asset externo incorporado somente depois de licença/permissão explícita, autoria e atribuição registradas.
- `NO_REUSE`: nenhuma reutilização; a referência pode servir apenas como contexto histórico ou comparação.

## Regras obrigatórias

1. `BEHAVIOR_REFERENCE` é o padrão para Biology Dictionary, Field Guide e Wildex.
2. Nenhuma entrada muda para `CODE_REUSE` ou `ASSET_REUSE` sem alteração explícita deste manifesto e evidência no mesmo PR.
3. Código, dados e assets não são vendorizados apenas para facilitar desenvolvimento.
4. Documentação oficial e código upstream podem ser usados para **confirmar fatos**, mas o texto editorial do Compêndio deve ser redação própria em pt-BR.
5. Traduções de terceiros não são copiadas automaticamente. Nomes localizados do próprio mod instalado podem ser usados via sistema de localization do jogo; corpus externo exige licença/proveniência própria.
6. Estatísticas e fatos mecânicos devem preferir registries/runtime/datapacks/APIs do próprio jogo/mod. Documentação externa é evidência suplementar, não substituto para dados verificáveis disponíveis em runtime.
7. Toda afirmação editorial que exija fonte deverá carregar metadata de origem quando o schema editorial correspondente existir.

## Conteúdo importado

Qualquer arquivo editorial explicitamente importado de terceiro deve declarar, no próprio JSON ou manifesto associado:

- `origin: "imported"`;
- autor/origem;
- licença;
- URL ou identificador da fonte;
- versão/commit quando aplicável.

Sem esses campos, o validador de proveniência deve rejeitar o conteúdo.

## Conteúdo original

Texto editorial criado especificamente para o Compêndio deve ser marcado como original do projeto no schema editorial futuro. Fatos técnicos permanecem associados a sua fonte (`REGISTRY`, `RUNTIME_ENTITY`, `DATAPACK`, `ADAPTER`, documentação oficial etc.) mesmo quando a redação é original.
