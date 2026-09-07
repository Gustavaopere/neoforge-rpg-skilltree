<!-- Guia temático canônico versionado no GitHub | referência atual de presença/JAR/versão: modlist.txt reconciliada em 2026-09-06 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **RECONCILIADO COM A MODLIST ATUAL — 2026-09-06:** a fonte canônica possui **607 entradas top-level** e este eixo cobre **101 JARs mágicos/cross-domain**. A reconciliação adicionou sete módulos que faltavam na cobertura, incorporou oito drifts de JAR/runtime e confirmou que nenhum dos 94 mod IDs anteriormente cobertos foi removido. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) é a autoridade de presença/versão; [`21-fontes-e-referencias-tecnicas.md`](21-fontes-e-referencias-tecnicas.md) é o índice de fontes externas.

> Este guia é um **catálogo descritivo** dos mods ligados à magia que aparecem na modlist atual. O foco é explicar o que cada um acrescenta ao jogo, como sua mecânica funciona e a qual ecossistema mágico ele pertence. Compatibilidade, biblioteca ou UI não são promovidas automaticamente a provider mecânico de perk.

---

## Padrão de descrição para integração

Para cada mod, o guia procura separar quatro coisas: **o que o jogador vivencia**, **qual recurso/estado o mod controla**, **quais dependências e bridges fazem parte do caminho real** e **onde confirmar a implementação**. Um addon visual, uma biblioteca ou uma camada KubeJS não deve ser tratado como provider de gameplay só porque está no eixo mágico.

Quando uma integração for implementada em projeto próprio, a documentação oficial e o código-fonte devem ser consultados para confirmar eventos, registries, data maps, tags, configs e APIs reais da versão instalada. O guia descreve a superfície conhecida, mas não autoriza inventar hooks.
