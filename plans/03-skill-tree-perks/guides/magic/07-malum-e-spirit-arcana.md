<!-- Reconciliado com modlist.txt atual em 2026-09-06. -->

[← Índice do guia](README.md)

# 7. Malum e spirit arcana

## Malum — 1.8.2

`malum-1.21.1-1.8.2.jar`

**Malum** é o provider de **Spirit Arcana** do pack. Sua progressão parte de espíritos obtidos do mundo e de criaturas e os transforma em matéria-prima para ritos, runas, equipamentos, ferramentas e processos arcanos. A Encyclopedia do mod funciona como guia de progressão; portanto, o sistema não deve ser reduzido a “mana alternativa”. O recurso importante é a identidade/tipo dos spirits e os processos que os produzem e consomem.

Para integração, há diferença entre **obter um spirit**, **armazenar/transportar um spirit**, **consumi-lo em crafting/rito** e **aplicar efeitos de equipamento/ritual**. Perks precisam escolher a etapa correta. Duplicar drops de spirit por um listener genérico de morte, por exemplo, pode quebrar a economia do mod se o próprio pipeline já tiver regras de extração, elegibilidade ou multiplicadores.

O mod também possui armas, scythes, runas, totens e Spirit Rites. Como `Apothic Compats` adiciona integração específica para Malum — inclusive affixes/gems e suporte a scythes/staves/curios — o eixo Malum deve ser analisado junto do capítulo Apothic quando a perk tocar gear ou loot.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malum) · [busca no Modrinth](https://modrinth.com/mods?q=Malum) · source/documentação vinculados pelo projeto oficial.

## Gaze — 1.1.7.1

`gaze-1.1.7.1.jar`

**Gaze (A Malum Addon)** prolonga a progressão de Malum com uma camada própria de desbloqueios e conteúdo. O addon adiciona tela de progressão, **Geas**, novos **Rites**, armas, runas, Curios/pouch e bestiário/registros relacionados a spirits. O objetivo é aprofundar o ciclo de coletar espíritos, registrar conhecimento e converter essa progressão em capacidades permanentes ou novas rotas de uso.

O projeto declara desenvolvimento ativo e possibilidade de grandes mudanças. Isso é relevante para integração: hooks internos do addon devem ser tratados como superfície mais volátil que as APIs do mod-base. Se uma perk depender de Gaze, documentar exatamente classe/evento/registry usado e falhar fechado quando a versão não fornecer o contrato esperado.

**Fontes:** [Modrinth](https://modrinth.com/mod/gaze-a-malum-addon) · [versão 1.1.7.1](https://modrinth.com/mod/gaze-a-malum-addon/version/1.1.7.1).

## Malum: Vestis — 1.1.0

`vestis-1.1.0.jar`

**Malum: Vestis** é uma expansão **cosmética** construída sobre Vanity. O conteúdo atual adiciona o design **Spirited Scythes**, com oito estilos visuais inspirados nos spirits de Malum e adquiridos pelas rotas de designs do ecossistema Vanity/Malum.

Ele não é provider de Spirit Arcana, não cria nova progressão de atributos e não deve ser usado como hook mecânico para perk. Sua relevância é visual/transmog. Requer Malum e Vanity: Core.

## Leitura para integração

- Provider mecânico principal: **Malum**.
- Extensão de progressão: **Gaze**, com maior volatilidade de API.
- Camada cosmética: **Vestis**; nunca promovê-la a causa de dano/spirit/progressão.
- Para loot/affix de gear Malum, cruzar com [`19-ecossistema-apothic-e-compats.md`](19-ecossistema-apothic-e-compats.md).
