# Compatibilidade

RPG Skill Tree é construído para conviver com mods opcionais. A ausência de uma integração não deveria impedir o núcleo do RPG de carregar.

## Integrações com gameplay confirmado

Epic Fight, Iron's Spells 'n Spellbooks, Ars Nouveau, Goety, Malum e Eidolon: Repraised possuem comportamento de integração confirmado na auditoria atual. Identity2/morphs também participa de permissões de forma de Druid/Metamorph.

## Compatibilidade por atributos

Apothic Attributes fornece vários atributos usados por perks. Isso é diferente de uma bridge completa para todos os sistemas Apothic.

## Conteúdo preparado

Create, Applied Energistics 2 e Oritech possuem especializações/gateways em dados. Consulte [Interações com outros mods](MOD_INTERACTIONS.md) para saber o que está ou não comprovado em runtime.

## Se um bônus parecer não funcionar

1. confira se o mod que fornece o atributo está instalado;
2. confira se o nó realmente tem efeito numérico em [Estatísticas](EFFECT_CATALOG.md);
3. diferencie perk estrutural/gateway de perk de atributo;
4. confira se a ação usada é a que concede mastery;
5. em servidor, confirme que cliente e servidor usam a mesma versão/modpack.