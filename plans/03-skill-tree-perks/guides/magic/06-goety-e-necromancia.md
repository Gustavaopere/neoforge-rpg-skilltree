<!-- Reconciliado com modlist.txt atual em 2026-09-06. -->

[← Índice do guia](README.md)

# 6. Goety e necromancia

## Goety — 3.1.4

`goety-3.1.4.jar`

**Goety - The Dark Arts** é um sistema completo de magia sombria cuja economia central é **Soul Energy**. A própria documentação do projeto trata Soul Energy como fonte de energia para artifices e spells; portanto, integrações que alterem custo, geração ou armazenamento devem respeitar esse recurso em vez de substituir o fluxo por mana genérica. O jogador acessa o conteúdo por wands/staffs, focos, rituais, artefatos, brewing e progressão de conhecimento.

A identidade mais forte do mod é necromancia e comando de **servants/minions**. Invocações e servos são entidades reais do mod, com ciclo de criação/controle próprio, e não apenas efeitos visuais de spell. Há também mobs, bosses, estruturas e eventos ligados ao acúmulo de Soul Energy. Para integração, isso cria pelo menos quatro superfícies diferentes: casting, Soul Energy, ritual/crafting e lifecycle/controle de servants; elas não devem ser fundidas em um único “bônus de magia”.

Rituais usam Soul Energy, blocos/estrutura ao redor do altar e itens em pedestais para fabricar, transformar ou invocar conteúdo. O brewing possui sistema próprio e pode produzir efeitos no jogador ou no mundo. Qualquer perk ou bridge que tente acelerar rituais, baratear custos ou modificar summons precisa confirmar no código 3.1.4 qual etapa é autoridade e se a operação é server-side.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/goety) · [Wiki oficial](https://goety.wiki.gg/) · [Mecânicas/Soul Energy/Rituais](https://goety.wiki.gg/wiki/Mechanics) · links de source no projeto CurseForge.

## Goety Cataclysm — runtime 1.21.1-1.8.2

`goety_cataclysm-1.21.1-1.8.2.jar`

**Goety Cataclysm** é a bridge direta entre **Goety** e **L_Ender's Cataclysm**. O addon reaproveita criaturas, bosses e ataques do Cataclysm dentro da progressão sombria do Goety, convertendo parte desse conteúdo em spells, summons/servants, equipamentos e habilidades acessíveis pelo ecossistema de Soul Energy e focos.

Para o projeto integrado, o ponto importante é causalidade: quando um efeito vem de Goety Cataclysm, a identidade do provider continua sendo a bridge Goety↔Cataclysm. Não se deve duplicar o mesmo efeito por um listener genérico de dano do Cataclysm e outro de spell do Goety. A implementação deve escolher o hook que melhor representa a ação original e deduplicar caminhos indiretos.

A build instalada `1.21.1-1.8.2` é a versão registrada pela modlist atual para NeoForge 1.21.1. **Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/goety-cataclysm) · [Modrinth 1.21.1-1.8.2](https://modrinth.com/mod/goety-cataclysm/version/1.21.1-1.8.2) · source indicado pelas páginas oficiais.

## Leitura para integração

- **Soul Energy** é recurso canônico do Goety; não substituir por mana de Iron's/Ars.
- **Servants** têm lifecycle/ownership próprios; efeitos sobre summons precisam preservar owner, morte, despawn e autoridade do mod.
- **Rituais** não são equivalentes a crafting instantâneo; estrutura, pedestais e custo fazem parte da mecânica.
- **Goety Cataclysm** é bridge, não um segundo Goety. Deduplicar efeitos que também possam ser observados via Cataclysm.
- Antes de implementar hooks, consultar source da versão 3.1.4/1.8.2 e confirmar eventos/métodos disponíveis; este guia não autoriza inventar API.
