# GUIA COMPLETO — Gameplay e Sistemas | NeoForge 1.21.1

Combate, movimento, progressão, sobrevivência, fauna, exploração, interface e infraestrutura geral do pack.

**Fonte canônica:** este diretório versionado no GitHub.

**Referência atual de presença/JAR/versão:** `modlist.txt` reconciliada em 2026-08-30, com **573 entradas top-level incluindo NeoForge**. Consulte primeiro [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md); ele prevalece quando um capítulo histórico ainda cita versão anterior.

## Como este guia está organizado

Cada arquivo abaixo contém uma **seção lógica completa**. Não existe continuação de parágrafo ou de capítulo em outro arquivo. Os arquivos podem ser lidos de forma independente; a ordem do índice apenas reproduz a organização canônica.

Os quatro projetos próprios do modpack possuem ainda uma coleção transversal obrigatória em [`../projects/`](../projects/README.md). O capítulo 13 abaixo é o recorte de Gameplay; o Chat 1 deve consultar também os dossiês completos, a matriz cruzada e a matriz de delta antes de fechar uma perk que possa interagir com RPG Skill Tree, Volcanoes, Enshrouded ou Black Arcana.

Para perks de combate/armas que possam tocar **Simply Swords, Simply More, Integrated Simply Swords, Simply Swords: Cataclysm, Simply Tooltips ou Epic Fight Compat**, o capítulo 15 é leitura obrigatória: ele documenta authority, implicits, Awakening, Runic Powers, sockets/gem powers, traits de addons, hooks públicos, deduplicação e fail-closed.

## Índice

- [Reconciliação atual da modlist — autoridade de presença/JAR/versão](CURRENT-MODLIST.md)
- [Visão geral e escopo](00-visao-geral.md)
- [1. Combate, movimento e ação](01-combate-movimento-e-acao.md)
- [2. Progressão RPG, identidades e atributos](02-progressao-rpg-identidades-e-atributos.md)
- [3. Sobrevivência e condições ambientais](03-sobrevivencia-e-condicoes-ambientais.md)
- [4. Alimentação, culinária e agricultura](04-alimentacao-culinaria-e-agricultura.md)
- [5. Ecossistema [Let's Do]](05-ecossistema-let-s-do.md)
- [6. Fauna, inimigos e bosses](06-fauna-inimigos-e-bosses.md)
- [7. Exploração, dimensões e worldgen](07-exploracao-dimensoes-e-worldgen.md)
- [8. Estruturas e dungeons](08-estruturas-e-dungeons.md)
- [9. MineColonies e civilização](09-minecolonies-e-civilizacao.md)
- [10. Utilidades de exploração e gameplay](10-utilidades-de-exploracao-e-gameplay.md)
- [11. Infraestrutura técnica, interface, visual e performance](11-infraestrutura-tecnica-interface-visual-e-performance.md)
- [12. Navegação entre os três guias](12-navegacao-entre-os-tres-guias.md)
- [13. Projetos próprios do modpack — integração canônica para perks](13-projetos-proprios-do-modpack.md)
- [14. Mobstein — fauna ressuscitada, experimentos, bosses e estruturas](14-mobstein.md)
- [15. Simply Swords e ecossistema — contratos para integração em perks](15-simply-swords-e-ecossistema.md)

## Regras de manutenção

- Nunca dividir um capítulo por quantidade de caracteres.
- Nunca deixar um `#`/`##` no meio de um parágrafo.
- Alterações futuras devem preservar uma seção inteira no mesmo arquivo.
- `CURRENT-MODLIST.md` é a autoridade de presença, JAR e versão do estado instalado atual.
- Não promover `PLANEJADO`, `PREPARATÓRIO / NÃO CANÔNICO` ou `BLOQUEADO / FAIL-CLOSED` dos projetos próprios a hook disponível sem nova evidência em `main`.
- Mods adicionados ou atualizados na modlist devem ser incorporados antes do próximo fechamento de lote do Chat 1.
- O GitHub é a fonte canônica deste guia; não manter cópia editorial concorrente no Notion.

[← Voltar aos guias](../README.md)