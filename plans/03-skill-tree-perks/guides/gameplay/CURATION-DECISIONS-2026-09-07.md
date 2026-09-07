# Decisões de curadoria da modlist — 2026-09-07

> **Escopo:** decisões revisadas após a reconciliação da fotografia física de 612 entradas top-level e o fechamento exaustivo `modVersion` ↔ Notion. Este arquivo **substitui somente as decisões explicitamente listadas aqui**. Para decisões não alteradas em 07/09, [`CURATION-DECISIONS-2026-09-06.md`](CURATION-DECISIONS-2026-09-06.md) permanece histórico válido.
>
> Presença física continua sendo autoridade de [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) + [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md).

## Decisões revisadas

| Mod | JAR atual | Decisão vigente | Regra / risco |
|---|---|---|---|
| Alex's Caves Continued | `alexscaves-1.0.9-neoforge+1.21.1.jar` | **Manter** | Única implementação `alexscaves` no snapshot atual. O antigo `alexscaves-2.0.2.jar` foi removido fisicamente. |
| Alex's Mobs Continued | `alexsmobs-2.1.10-neoforge+1.21.1.jar` | **Manter** | Única implementação `alexsmobs` no snapshot atual. O antigo `alexsmobs-1.22.9.jar` foi removido fisicamente. |
| Create: Bits 'n' Bobs | `bits_n_bobs-2.3.1.jar` | **Manter** | Substitui `Tirar` de 06/09. O conflito visual com Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 é aceito e deve continuar monitorado/render-testado. |
| More Relics | `morerelics-1.7.7-1.21.1.jar` | **Manter** | O upstream não declara suporte a Relics 0.12; o pack usa Relics 0.12.8. Risco aceito para presença física, mas hooks/perks provider-specific permanecem fail-closed até validação real. |
| Integrated Mowzie's Mobs | `IMM v1.1.0-1.21.1.jar` | **Manter** | Projeto oficial confirmado. Runtime local 1.1.0 é autoridade física; a publicação pública exata da build local 1.1.0 ainda não foi demonstrada e não deve ser inferida. |

## Remoções físicas confirmadas

A fotografia de 07/09 não contém mais:

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Essas remoções são estado físico, não novas linhas artificiais de curadoria. O Notion final foi consolidado para a fotografia vigente e não mantém quatro linhas físicas separadas somente para contabilizar artefatos ausentes.

## More Relics — sem falsa equivalência entre `Manter` e `compatível`

A decisão **Manter** responde à pergunta de curadoria do pack. Ela não invalida a advertência upstream. Antes de qualquer perk ou integração depender de More Relics, validar ao menos:

1. carregamento do jogo/servidor com Relics 0.12.8;
2. obtenção e persistência dos relics adicionados;
3. equip/unequip via Curios;
4. evolução/progressão nativa quando aplicável;
5. save/reload;
6. hook específico pretendido pela perk.

Sem essa evidência, o contrato provider-specific fica **FAIL-CLOSED**, mas o mod não é marcado `Tirar`.

## Bits 'n' Bobs — conflito aceito, não resolvido

Create: Sulfuric Resonance 0.4.1 continua documentando incompatibilidade visual dos Thermochemical Cogwheels com Bits 'n' Bobs. A decisão de 07/09 é manter ambos. Portanto:

- não declarar o conflito corrigido;
- não remover Bits 'n' Bobs automaticamente;
- não usar a antiga decisão `Tirar` de 06/09 como estado vigente;
- validar especificamente renderização dos Thermochemical Cogwheels após updates de qualquer lado.

## Fechamento de runtime/Notion

- fotografia física: **612 top-level**;
- Notion: **612 `Instalado`**, **612 JARs físicos distintos**, **613/613 `Verificado`**;
- **605/605** JARs que declaram `modVersion` correspondem exatamente ao campo `Versão 1.21.1`;
- **7/7** JARs sem `modVersion` físico mantêm `Versão 1.21.1` vazia, com filename/publicação registrados separadamente.

Este fechamento torna desnecessário inferir versão runtime pelo nome do arquivo.
