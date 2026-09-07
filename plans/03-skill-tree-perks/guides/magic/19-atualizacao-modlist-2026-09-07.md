# 19. Atualização da modlist mágica — 2026-09-07

[← Índice do guia](README.md)

> A autoridade exata de presença/JAR/runtime do pack é [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md). Este capítulo registra os efeitos do delta sobre o domínio mágico e RPG.

## Ironsable x Wind's Spellbooks — 1.0.0

`ironsable-wind-1.0.0.jar`

**Ironsable x Wind's Spellbooks** é uma bridge entre Wind's Spellbooks e IronSable/Sable. Ela faz spells de vento interagirem com blocos simulados e estruturas físicas do Sable. A documentação do projeto cita comportamentos para **Tornado, Almighty Push, Wind Blade** e interação de colisão aerópica.

Dependências declaradas: IronSable, Wind's Spellbooks, Sable e Iron's Spells 'n Spellbooks. A bridge não adiciona uma nova escola e não é provider autônomo de spells: a autoridade do spell continua no mod de magia; a bridge apenas materializa efeitos no domínio físico do Sable.

Para perks, validar causalidade server-authoritative, dupla aplicação, desync e destruição excessiva antes de aceitar qualquer hook relacionado a quebra/empurrão de blocos simulados.

## More Relics — 1.7.7 — ALTO RISCO

`morerelics-1.7.7-1.21.1.jar`

**More Relics** expande Relics com novos relics/acessórios e efeitos. A identidade do JAR `1.7.7` para NeoForge 1.21.1 é válida, porém existe um conflito de compatibilidade declarado pelo próprio projeto: a documentação upstream informa que, para NeoForge 1.21.1, **Relics 0.11 e 0.12 ainda não são suportados** e recomenda **Relics 0.10.7.8**.

O pack atual usa `relics-1.21.1-0.12.8.jar`. Portanto o estado correto é:

- More Relics está **fisicamente instalado**;
- a compatibilidade com o Relics atual **não está aprovada**;
- não usar More Relics como provider de perk nem assumir que seus relics/effects funcionam corretamente;
- aplicar **fail-closed** até haver teste/prova de compatibilidade ou ajuste explícito do stack.

Dependências declaradas pelo projeto incluem Relics, Curios API, Architectury API e bibliotecas da release como ShatterLib/OctoLib.

## Updates mágicos do snapshot

- Apotheosis `8.7.0 → 8.8.0`.
- Ars 'n' Spells `3.2.4 → 3.3.0`.
- GTBC's SpellLib `2.1.0 → 2.2.0` (`2.2.0-1.21.1` no runtime).
- Vampirism `1.10.12 → 1.10.13`.

Esses são updates de identidade física; não alteram por si sós contratos de provider já aprovados. Quando um hook/API tiver mudado, a perk correspondente deve ser revalidada antes de assumir compatibilidade.

## Boundary

`Ironsable x Wind's Spellbooks` é **bridge**, não escola. `More Relics` é conteúdo do ecossistema Relics, mas está fail-closed pelo desvio de versão declarado. Nenhum dos dois deve ser promovido silenciosamente a provider mecânico durante o fechamento de perks.
