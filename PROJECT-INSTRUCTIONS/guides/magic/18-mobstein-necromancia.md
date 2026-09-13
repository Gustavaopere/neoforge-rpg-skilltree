<!-- Atualização incremental de 2026-08-30. Fonte canônica no Notion: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 -->

[← Índice do guia](README.md)

# 18. Mobstein — necromancia corporal e ressurreição

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

**Mobstein** entra no eixo de necromancia como um sistema próprio de **ressurreição corporal**, criação de experimentos e mobs ressuscitados domesticáveis/guarda-costas. A release `5.4.4` é a versão estável NeoForge 1.21.1 publicada em 04/05/2026; GeckoLib é requerida nessa linha.

O mod trabalha com:

- corpos completos, cabeças e partes anatômicas;
- órgãos e extração de órgãos;
- seringas de ressurreição e variantes funcionais;
- Clinical Stretch e Surgery Stretch;
- Subject Assembly Machine e criação de mannequins/experimentos;
- criaturas ressuscitadas com habilidades próprias;
- Dr. Mobstenio, Igor, Frankenstein/Witherstein e conteúdo de lore/estruturas.

## Separação de autoridade

Mobstein **não é Goety, Black Arcana, Malum ou Eidolon**. A semelhança temática de necromancia não cria shared resource nem bridge automática:

- ressuscitados Mobstein não consomem automaticamente Goety Soul Energy;
- corpos/órgãos Mobstein não viram Malum spirits;
- ressurreição Mobstein não é ritual Eidolon por inferência;
- ressuscitados/corpos Mobstein não representam Black Arcana Corruption;
- nenhuma dessas entidades deve ser tratada como Enshrouded Shroud/corrupted ecology apenas pela aparência undead.

Cada provider conserva seu pipeline e sua autoridade até existir adapter/contrato explícito.

## Relevância para o Chat 1

O Chat 1 deve auditar Mobstein como provider próprio quando a árvore tocar ressurreição, cadáveres, órgãos, experimentos e aliados ressuscitados. A decisão pode terminar em:

- perk própria;
- especialização necromântica compartilhada;
- bridge com uma perk existente;
- cobertura por sistema universal de companions/summons;
- progressão nativa autoritativa;
- `SEM HOOK SEGURO`;
- `NÃO DEVE SER INTEGRADO`.

As quatro "perks" internas de criação do Mobstein (`Attack`, `Health`, `Speed`, `Template`) pertencem ao próprio mod e **não são nodes do RPG Skill Tree**.

Milestones de ressurreição, montagem de experimento, estrutura ou boss só podem alimentar Mastery/progressão quando houver causalidade server-authoritative e identidade deduplicável. Manter um resurrected mob ativo, próximo ou domesticado não gera Mastery por tick.

## Fonte auditada

- CurseForge oficial: `Mobstein : Revive animals and necromancy!`, projeto 1193873.
- Release NeoForge 1.21.1: `mobstein-5.4.4-neoforge-1.21.1.jar`, publicada em 04/05/2026.
