# 11 — Itemização e Progressão de Equipamentos

O Stage 11 transforma equipamentos vanilla e modded em uma camada canônica de progressão do RPG. O domínio planejado usa `itemization` e cobre identidade persistente, rank, Poder do Item, Prefixos, Sufixos, Infixos, geração server-authoritative, efeitos, integração com sistemas externos, salvamento e apresentação integral em português do Brasil.

## Objetivos

- todo equipamento elegível deve poder receber identidade RPG independentemente do mod de origem;
- a primeira geração é permanente: rank, Poder do Item e modificadores não podem ser rerrolados;
- rank e quantidade de modificadores são dimensões independentes;
- cada família possui de 1 a 5 modificadores: Prefixos, Sufixos e Infixos;
- Prefixos concentram poder ofensivo/produtivo, Sufixos defesa/recursos/sustentação e Infixos comportamentos especiais/condicionais;
- itens craftados, loot, drops, equipamentos de mobs, rewards, trades e outputs externos devem convergir para o mesmo pipeline idempotente;
- equipamentos de mobs devem usar os próprios efeitos e preservar exatamente a mesma identidade se forem dropados;
- o sistema deve reutilizar capacidades úteis de Apotheosis/Apothic, Iron's Spellbooks, Ars Nouveau, Create, Curios e outros providers sem criar dois donos para a mesma decisão de itemização;
- todo texto próprio exibido ao jogador deve possuir `pt_br` completo e validado por CI;
- conteúdo modded desconhecido deve receber fallback seguro em vez de ficar silenciosamente fora do sistema.

## Invariantes canônicas

1. **Servidor é autoridade.** Cliente nunca sorteia rank, valores ou modificadores.
2. **Geração única.** Um `ItemStack` elegível recebe identidade uma única vez; save/load, drop/pickup, mudança de dimensão, container, mob pickup, reparo e reload não rerrolam.
3. **Rank != quantidade.** Um item Único pode ter 1/1/1 e um Comum pode ter 5/5/5.
4. **Famílias fixas.** Cada item itemizado possui entre 1 e 5 Prefixos, 1 e 5 Sufixos e 1 e 5 Infixos, salvo exceção futura explicitamente versionada.
5. **Sem reroll.** Reforging e qualquer mutação equivalente não podem alterar a identidade RPG persistida.
6. **Upgrades preservam identidade.** Smithing, reparos e upgrades compatíveis alteram o item-base sem gerar nova identidade.
7. **Data-driven.** Pools, pesos, curvas, caps, categorias, aliases e compatibilidade devem aceitar dados externos/reload onde isso for seguro.
8. **pt-BR first.** IDs técnicos ficam estáveis; apresentação própria do RPG é localizada e nenhuma chave crua deve vazar para o jogador.
9. **Fail-soft em mods opcionais.** Ausência de integração externa não impede startup.
10. **Performance bounded.** Nada de varrer todos os inventários/registries a cada tick; geração e reconciliação são orientadas a eventos/fronteiras.

## Modelo conceitual

```text
Equipamento
├── identidade persistente
│   ├── instanceId
│   ├── deterministicSeed
│   └── schemaVersion
├── Poder do Item
├── Rank
├── Prefixos [1..5]
├── Sufixos [1..5]
├── Infixos [1..5]
├── origem/contexto de geração
└── integrações mutáveis externas
    ├── encantamentos
    ├── gems/sockets
    ├── Ars Threads
    └── upgrades permitidos
```

Rank e Poder do Item controlam potência, mas não a quantidade de Prefixos/Sufixos/Infixos. Habilidades binárias não recebem multiplicação cega; cada modificador declara a própria estratégia de scaling.

## Ranks planejados

1. Comum
2. Incomum
3. Raro
4. Épico
5. Lendário
6. Mítico
7. Único

`Único` é rank, não unicidade global. Se o projeto desejar itens manualmente projetados e realmente exclusivos, reservar `Artefato` como conceito separado.

## Integrações prioritárias

- Apotheosis / Apothic Attributes / bridges compatíveis: reaproveitar atributos, gems/sockets, salvaging e efeitos seguros; RPG é autoridade de rank/quantidade/identidade.
- Iron's Spellbooks: mana, regeneração de mana, poder mágico/escolas, cooldown/cast e equipamentos mágicos.
- Ars Nouveau: mana, equipamentos, Threads e capacidades aplicáveis sem converter sistemas próprios em afixos RPG.
- Create e addons: jetpacks, equipamentos tecnológicos, ferramentas e outputs de máquinas.
- Curios: anéis, colares, amuletos e slots modded.
- Conteúdo desconhecido: fallback universal seguro com diagnóstico de cobertura.

## Ordem causal

1. `01-domain-invariants.md`
2. `02-equipment-classification.md`
3. `03-item-identity-persistence.md`
4. `04-ranks-item-power.md`
5. `05-prefix-suffix-infix-schema.md`
6. `06-generation-pipeline.md`
7. `07-modifier-runtime.md`
8. `08-loot-crafting-mobs.md`
9. `09-apotheosis-integration.md`
10. `10-irons-ars-integration.md`
11. `11-create-tech-curios.md`
12. `12-salvaging.md`
13. `13-ptbr-localization-ui.md`
14. `14-world-migration.md`
15. `15-testing-performance-hardening.md`

## Relação com os estágios existentes

- Stage 00: optional integrations, diagnostics e gates.
- Stage 01: atributos/modificadores, persistência e APIs canônicas.
- Stage 02: nível relevante, territorial e de entidade para contexto de Poder do Item.
- Stage 05: hooks de combate/magia necessários aos Infixos.
- Stage 06: adapters de integrações; não duplicar internals arbitrariamente.
- Stage 07: data/network/UI.
- Stage 08: quests/recompensas podem consumir queries de itemização.
- Stage 09: performance, migration e release gates finais.
- Stage 10: pode consumir metadados no Compêndio, mas não é autoridade da itemização.

## Definition of Done do Stage 11

- [ ] todos os 15 subplanos concluídos, testados e integrados;
- [ ] identidade persiste sem reroll em todos os lifecycles suportados;
- [ ] rank e contagem 1..5 por família comprovadamente desacoplados;
- [ ] craft/loot/mobs/rewards/trades/outputs suportados convergem para o mesmo pipeline idempotente;
- [ ] mob equipado usa os efeitos e preserva a mesma instância ao dropar;
- [ ] smithing/reparo/upgrades não regeneram identidade;
- [ ] Apotheosis não rerrola itens RPG e gems/sockets continuam utilizáveis;
- [ ] Iron's, Ars, Create/tech e Curios possuem adapters/fallbacks validados quando presentes;
- [ ] salvaging universal não duplica materiais;
- [ ] todo texto próprio do Stage 11 possui `pt_br` e validator de cobertura;
- [ ] saves antigos são migrados de forma versionada e idempotente;
- [ ] optional-mod matrix, testes, build, JAR e dedicated-server smoke passam;
- [ ] hot paths respeitam budgets derivados de medição real.
