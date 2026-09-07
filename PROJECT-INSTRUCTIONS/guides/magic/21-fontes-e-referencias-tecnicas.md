[← Índice do guia](README.md)

# 21. Fontes e referências técnicas por mod

Este índice existe para acelerar auditoria e implementação. **Links diretos só são marcados como diretos quando foram verificados nesta revisão.** Para os demais mods, o guia oferece uma busca nominal no CurseForge e no Modrinth em vez de adivinhar slugs. Isso é deliberado: um link de busca confiável é melhor que um URL direto inventado para um projeto homônimo/fork.

Em implementação, a ordem recomendada é: **source/docs da versão instalada → página do projeto/release → modlist runtime → descrição deste guia**.

A tabela de **101 mods** foi dividida em quatro páginas para facilitar leitura e manutenção:

- [A–C — 37 mods](21a-fontes-a-c.md)
- [D–I — 30 mods](21b-fontes-d-i.md)
- [J–R — 17 mods](21c-fontes-j-r.md)
- [S–Z — 17 mods](21d-fontes-s-z.md)

## Fontes diretas prioritárias

- **Ars Nouveau:** site/wiki e source devem ser consultados para glyphs, recipes, rituals, Source e APIs; o guia não presume hooks por semelhança de nome.
- **Iron's Spells:** wiki/source são prioritários para schools, spells, mana/cooldown, atributos e eventos.
- **Goety:** wiki oficial é prioritária para Soul Energy, rituals, servants e brewing.
- **Apothic:** consultar o repositório do módulo específico; Attributes, Spawners e compats têm autoridades diferentes.
- **KubeJS bridges:** wiki/source da bridge decide o que é scriptável; presença do addon não implica acesso a toda API do mod-base.

## Manutenção

Quando um link direto for verificado, substituir o fallback `buscar` pelo projeto exato. Ao atualizar versão/JAR, verificar também se o repositório mudou de branch/maintainer. Não remover um source antigo apenas porque existe um fork sem provar que o JAR instalado veio do fork.
