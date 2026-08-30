# 14.06 — Style packs e upgrades MineColonies

## Objetivo

Transformar edifícios próprios em uma progressão de construção utilizável pelo MineColonies sem transformar MineColonies em autoridade do modelo econômico/social.

## Contrato

Cada prédio integrável possui:

- `buildingId` nosso;
- 1..5 níveis físicos quando o design declarar upgrade;
- anchors consistentes;
- markers/POIs exigidos pelo job/building adapter;
- style variant opcional;
- manifest de dependencies;
- BOM por nível e diff de upgrade.

## Style packs

Agrupar construções por identidade visual, não por regra econômica. Uma mesma função pode ter múltiplos styles usando o mesmo contrato funcional.

## Upgrade

- nível N+1 deve preservar acesso ao worker e pontos essenciais ou declarar remapeamento;
- containers de gameplay não podem ser apagados sem migração de inventário;
- máquinas Create operacionais são desligadas/isoladas durante transformação se necessário;
- builder recebe somente blocos válidos para aquela versão.

## Integração

MineColonies continua autoridade sobre Builder, construction progress e worker assignment quando API suportar. O RPG fornece blueprint, contrato funcional e bridge para seus sistemas.

## Testes

- níveis 1–5 importam;
- anchors não derivam;
- upgrade não duplica containers;
- rotação da colônia preserva markers;
- style sem provider requerido é indisponível, não corrompido.

## Acceptance

Uma cadeia completa de upgrade pode ser construída pelo MineColonies e manter os vínculos funcionais dos Stages 16–19.