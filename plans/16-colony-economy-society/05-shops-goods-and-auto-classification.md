# 16.05 — Lojas, mercadorias e classificação automática

## Objetivo

Vender conteúdo vanilla/modded sem manter manualmente milhares de IDs.

## GoodClassifier

Ordem:

1. override data-driven por item/tag;
2. adapter nominal quando o provider possui semântica inacessível genericamente;
3. tags/registries/components/recipe groups;
4. fallback `generic_good` com diagnóstico;
5. blacklist explícita para itens não comercializáveis.

Categorias possíveis: alimento, bebida, combustível, matéria-prima, ferramenta, arma, roupa/equipamento, medicina, componente tecnológico, componente arcano, luxo, construção e genérico. IDs devem ser namespaced/extensíveis.

## Shops

`ShopProfile` define categorias aceitas, estoque alvo, markup policy, owner/business account, distrito e building reference. Merchant/job do Stage 18 interage pela API, mas o ledger continua Stage 16.

## Modded content

Instalar novo mod deve fazer itens genericamente reconhecíveis entrarem no catálogo sem editar código. Adapter só é necessário para preço/semântica especial.

## Preço base

Pode vir de datapack, receita/custo derivável, tier/material ou fallback conservador. Valor derivado deve expor provenance; não fingir precisão para item desconhecido.

## Testes

- vanilla food/tool;
- item modded desconhecido;
- tag override;
- blacklist;
- provider ausente;
- shop compra/venda categorias corretas;
- reload do catálogo mantém transação anterior auditável.

## Acceptance

Mercado cresce com o modpack sem catálogo manual eterno e sem vender item claramente proibido por fallback irresponsável.