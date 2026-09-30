<!-- Guia temático canônico versionado no GitHub | snapshot histórico de presença/JAR/versão: 2026-09-06; authority atual: ../../modlist/modlist.md + dossier individual -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **SNAPSHOT HISTÓRICO — 2026-09-06:** naquele checkpoint havia **607 entradas top-level** e este eixo cobria **101 JARs mágicos/cross-domain**. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) preserva esse snapshot; presença/JAR/versão atuais vêm de `../../modlist/modlist.md`, e o dossier individual atual é authority para mecânicas/hooks por provider.

> Este guia é um **catálogo descritivo** dos mods ligados à magia documentados naquele snapshot. O foco é explicar o que cada um acrescenta ao jogo, como sua mecânica funciona e a qual ecossistema mágico ele pertence. Compatibilidade, biblioteca ou UI não são promovidas automaticamente a provider mecânico de perk.

---

## Padrão de descrição para integração

Para cada mod, o guia procura separar quatro coisas: **o que o jogador vivencia**, **qual recurso/estado o mod controla**, **quais dependências e bridges fazem parte do caminho real** e **onde confirmar a implementação**. Um addon visual, uma biblioteca ou uma camada KubeJS não deve ser tratado como provider de gameplay só porque está no eixo mágico.

Quando uma integração for implementada em projeto próprio, a documentação oficial e o código-fonte devem ser consultados para confirmar eventos, registries, data maps, tags, configs e APIs reais da versão instalada. O guia descreve a superfície conhecida, mas não autoriza inventar hooks.
