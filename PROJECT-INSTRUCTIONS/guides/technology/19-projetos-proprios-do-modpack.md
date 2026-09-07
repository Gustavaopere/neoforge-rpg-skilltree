# 19. Projetos próprios do modpack — integração tecnológica canônica

A partir de 30/08/2026, os quatro projetos próprios fazem parte da auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo é somente o recorte de **Tecnologia, engenharia e automação**. Para decidir `Provider/Mods`, hook, status, autoridade e fail-closed, o Chat 1 deve ler os quatro dossiês completos e a matriz cruzada.

## Volcanoes — ambiente físico como provider para engenharia

[Dossiê completo](../projects/02-volcanoes.md)

Volcanoes é o projeto próprio com maior superfície tecnológica já disponível em runtime.

- **IMPLEMENTADO E CANÔNICO:** geologia/depósitos, tectônica, vulcanismo/geotermia, Atmosphere, respiração, pressão atmosférica e hidrostática, enclosed-environment SPI e equipment/protection.
- Create Diving Helmet + Backtank participam da oferta canônica de oxigênio sem criar segundo recurso ou segundo consumo.
- Sable `2.0.5` e Aeronautics `1.3.1` integram posições/sublevels à pressão física; na ausência de contrato confiável de cabine selada, o sistema deliberadamente usa a atmosfera/pressão externa e não inventa proteção.
- Cold Sweat `2.4.2` continua autoridade de temperatura corporal. Volcanoes fornece calor ambiental bounded de lava, piroclastos e geotermia.
- Destroy continua autoridade de seu domínio de poluição/acid rain; a bridge não autoriza feedback industrial inventado.
- MineColonies alimenta `ProtectedAreaService`; máquinas/perks não podem contornar proteção de área.
- **RNS PARCIAL/FAIL-CLOSED:** identidade hidrotermal Cu/Fe/Au é real, mas RNS continua autoridade de prospecção/worldgen mineral até Volcanoes provar placement físico dos metais correspondentes.

**Perks tecnológicas legítimas:** leitura geológica, diagnóstico ambiental, eficiência/proteção respiratória e de pressão, geotermia e engenharia em veículos quando houver boundary real. A perk não deve possuir um segundo scheduler de erupção, atmosphere state, body-temperature state, filtro ou sistema de pressão.

## RPG Skill Tree — progressão tecnológica e itemização futura

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- O RPG é autoridade de Level/XP/CPP/atributos, Skill Tree, node effects e world scaling já fechados.
- A subtree **Technomancer** é **IMPLEMENTADA E CANÔNICA**, com ramos de Create Kinetics, AE2 Networks e Oritech Power. Gates e perda/refund de requisito pertencem ao runtime canônico da subtree.
- Integração geral Create/AE2/Oritech do Stage 06 ainda é **PLANEJADA/ABERTA**; a existência da subtree não autoriza o Chat 1 a inventar qualquer API genérica desses providers.
- Stage 11 de itemização — Rank, Item Power, Prefixos/Sufixos/Infixos, geração universal e bridges com Create/tech/Curios — é **PLANEJADO**, não provider operacional atual.
- Stage 12 planeja **construção tecnológica de corpos**, Body Registry e troca transacional; também não pode ser usado como hook atual enquanto estiver aberto.
- Stage 13 de cartografia/regiões/POI/discovery é planejamento futuro, ainda que possa orientar perks de exploração/engenharia posteriores.

**Regra:** perks tecnológicas já implementáveis usam somente a subtree/boundaries realmente presentes. Sistemas de itemização, corpos e adapters ainda abertos devem permanecer `PENDENTE`/fail-closed no Chat 2 até prova em `main`.

## Enshrouded — sem subsistema tecnológico próprio canônico

[Dossiê completo](../projects/03-enshrouded.md)

Enshrouded possui Shroud, Terrain Corruption, Exposure, Corrupted Ecology e Flame Progression; ele **não é um provider tecnológico genérico**.

- Não converter automaticamente máquinas Create, pressão, energia ou tecnologia em proteção contra Shroud.
- `MutationAuthority`, `ShroudQuery`, `FlamePassageQuery` e demais boundaries continuam Enshrouded-owned.
- Stage 08 Integrations está aberto. Portanto uma futura bridge com sistemas tecnológicos precisa ser nomeada e comprovada antes de entrar em perks.
- Uma máquina/perk não pode purificar terreno, conceder Flame Passage ou criar Sanctuary por inferência temática.

No recorte tecnológico atual, a classificação normal é **NÃO APLICÁVEL**, salvo quando uma perk realmente cruza um boundary público do Enshrouded.

## Black Arcana — magia perigosa não vira tecnologia por associação

[Dossiê completo](../projects/04-black-arcana.md)

Black Arcana possui casting, World Safety e Arcane Danger. Nenhum desses sistemas deve ser convertido automaticamente em FE, stress, pressão, geotermia ou engenharia.

- Arcane Resistance/Corruption Resistance usam providers próprios; equipamentos tecnológicos não contribuem automaticamente.
- Volcanoes heat/respiration/toxicity/pressure são explicitamente excluídos da Arcane Resistance por default.
- A infraestrutura de equipment set bonuses já presente em `main` não transforma Create/AE2/Oritech em provider arcano sem registro explícito.
- Rituals, Spell Domains e Progression & Balance permanecem preparatórios/planejados.

No eixo tecnologia, Black Arcana é normalmente **NÃO APLICÁVEL**. Uma futura perk híbrida Technomancer/arcana precisa declarar dois providers e uma bridge explícita, preservando a autoridade de cada lado.

## Matriz cruzada obrigatória

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Antes de fechar uma perk tecnológica híbrida, registrar:

- qual sistema é autoridade;
- qual provider é apenas consumer/bridge;
- versão/hook realmente disponível;
- recurso consumido e quem o debita;
- identidade de deduplicação;
- fallback/fail-closed;
- comportamento com provider opcional ausente.

Sem esses elementos, a integração não está suficientemente especificada para implementação.
