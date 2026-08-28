# 10.15 — Gate de conteúdo e release do Compêndio

## Objetivo

Definir o critério final para declarar o Compêndio implementado. “A tela abre” não é suficiente: o gate precisa provar cobertura do modpack, qualidade pt-BR, integridade de dados, compatibilidade e performance.

## Gate 1 — Arquitetura

- [ ] catálogo usa identidade canônica `kind + ResourceLocation`;
- [ ] providers/adapters são isolados;
- [ ] reload publica snapshot atomicamente;
- [ ] discovery/reward é server-authoritative;
- [ ] admin tools estão separados da experiência survival;
- [ ] nenhum mod opcional virou hard dependency acidental.

## Gate 2 — Inventário do modpack

Executar o inventário contra a modlist/runtime atual usada para a release.

Artefatos obrigatórios:

```text
generated/compendium/modpack-inventory.json
generated/compendium/modpack-inventory.md
generated/compendium/coverage-report.json
generated/compendium/coverage-report.md
```

O relatório deve conter:

- hash/data da modlist;
- mods top-level considerados;
- namespaces encontrados;
- total de entidades;
- total de flora;
- total de árvores;
- total de cultivos;
- total de biomas;
- total de estruturas;
- total de dimensões;
- contagem `AUTO`;
- `CURATED`;
- `ADAPTER`;
- `IGNORED` com motivo;
- `ERROR`.

**Critério:** `ERROR = 0`.

## Gate 3 — Conteúdo pt-BR

- [ ] 100% das chaves próprias do Compêndio têm `pt_br`;
- [ ] UI não depende de texto hardcoded em inglês;
- [ ] corpus `CURATED` possui resumo e seções obrigatórias aplicáveis;
- [ ] nomes editoriais têm aliases técnicos quando necessário;
- [ ] ortografia/revisão linguística concluída;
- [ ] nenhuma entrada final contém placeholder;
- [ ] fatos mecânicos mutáveis não foram duplicados como números editoriais obsoletos;
- [ ] lore e mecânica estão distinguidos.

## Gate 4 — Fauna

Para cada entidade relevante do inventário:

- [ ] página base abre;
- [ ] origem/mod/ID é identificável;
- [ ] stats disponíveis aparecem com fonte/contexto;
- [ ] HP base não é confundido com HP escalado da instância;
- [ ] habitat/spawn aparece quando resolvível;
- [ ] loot aparece quando resolvível;
- [ ] reprodução/domesticação/alimentação aparecem somente quando confirmadas;
- [ ] variantes não causam crash/duplicação indevida;
- [ ] preview 3D tem fallback seguro;
- [ ] conteúdo prioritário possui descrição pt-BR curada.

## Gate 5 — Flora, árvores e cultivos

- [ ] plantas relevantes classificadas;
- [ ] árvores agrupam sapling/log/leaves/produtos corretamente;
- [ ] Dynamic Trees não duplica espécie sem razão;
- [ ] TFC e sistemas climáticos usam adapter/fonte correta;
- [ ] cultivos mostram crescimento/colheita sem virar recipe browser redundante;
- [ ] flora dimensional/modded aparece;
- [ ] conteúdo prioritário possui descrição completa pt-BR.

## Gate 6 — Biomas, estruturas e dimensões

- [ ] todos os registries relevantes aparecem no catálogo ou têm `IGNORED` explícito;
- [ ] YUNG/worldgen modded carregado é inventariado;
- [ ] estruturas vanilla alteradas não são duplicadas indevidamente;
- [ ] dimensões modded são fail-soft;
- [ ] relações fauna/flora/bioma/estrutura/dimensão são navegáveis;
- [ ] descoberta de biome/structure/dimension é validada pelo servidor.

## Gate 7 — Descoberta e RPG

- [ ] first discovery funciona;
- [ ] eventos duplicados são idempotentes;
- [ ] reward one-shot não duplica;
- [ ] relog/respawn/dimension change preservam estado;
- [ ] multiplayer mantém jogadores separados;
- [ ] integração RPG usa APIs canônicas;
- [ ] quest hooks não acoplam o core a um mod de quest obrigatório.

## Gate 8 — Save/rede

- [ ] versão de save definida;
- [ ] fixture antiga migra;
- [ ] conteúdo removido preserva record legado;
- [ ] reintrodução pelo mesmo ID restaura associação;
- [ ] protocol versionado;
- [ ] packets têm bounds;
- [ ] packet forjado/oversized é rejeitado;
- [ ] catálogo grande não é retransmitido integralmente a cada abertura de UI.

## Gate 9 — Optional-mod matrix

Para todo adapter nominal implementado:

- [ ] presença testada;
- [ ] ausência testada;
- [ ] versão incompatível falha com diagnóstico ou degradação segura;
- [ ] dedicated server sem o mod opcional inicia;
- [ ] remoção do mod de save existente não corrompe Compêndio.

## Gate 10 — Performance

Anexar relatório de medição com:

- tempo de catalog build;
- memória do snapshot;
- tempo de search index;
- query/search latency em catálogo real;
- comportamento de scroll;
- custo de discovery path comum;
- custo/limites de entity inspection;
- reload duration.

Os budgets finais aprovados no 10.14/Stage 09 precisam estar cumpridos ou ter waiver documentado.

## Gate 11 — Proveniência

- [ ] manifesto upstream atualizado;
- [ ] assets próprios/externos têm origem;
- [ ] nenhum código externo entrou sem auditoria de licença;
- [ ] nenhuma descrição foi copiada extensivamente de documentação/wiki externa;
- [ ] fontes factuais obrigatórias estão registradas;
- [ ] corpus editorial é redação própria.

## Gate 12 — CI

Obrigatório antes de renomear este arquivo para `✅-15-gate-conteudo-release.md`:

- [ ] unit tests green;
- [ ] validators green;
- [ ] build NeoForge green;
- [ ] resource/data generation sem drift inesperado;
- [ ] JAR verification green;
- [ ] dedicated-server smoke green;
- [ ] testes client/manuais definidos no 10.14 concluídos;
- [ ] cobertura do modpack real anexada ao release evidence.

## Evidência de fechamento

Ao concluir, registrar neste arquivo:

```text
Implementation commit:
Merged PR:
CI run:
Modlist hash:
Coverage report:
Performance report:
Compatibility matrix:
Acceptance: satisfied
```

Sem esses campos preenchidos com evidência real, o Stage 10 permanece aberto.

## Acceptance

O subplano fecha somente quando todos os gates acima estiverem satisfeitos e integrados na branch canônica. Nesse momento, o Stage 10 pode ser declarado concluído em `plans/STATUS.md`.
