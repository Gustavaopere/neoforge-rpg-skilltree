# Dossiês Canônicos de Perks

Esta pasta substitui o checklist agregado `07-perk-implementation-checklist.md` por um dossiê individual por perk.

## Fontes de verdade

1. **Notion — Catálogo Mestre — Atributos e Passivos:** fonte canônica de design para código, nome, domínio, árvore, ramo, camada, função, tier, faixa de poder, ranks, custo, dependências, pré-requisitos, provider/mods, efeito, escalonamento, gate, hook, fallback e regra.
2. **Notion — Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree:** protocolo canônico de auditoria em nove eixos, provider-native first, fail-closed, pipeline canônico único, anti-duplicação, anti-abuso de Mastery e delta obrigatório de capacidades dos projetos próprios.
3. **`CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`:** cópia versionada integral dos critérios para tornar a auditoria reproduzível no GitHub. O Notion continua canônico e prevalece em caso de divergência futura.
4. **`../guides/projects/`:** snapshot operacional obrigatório dos projetos próprios **RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana**, incluindo matriz de integração cruzada, reconciliação de snapshots, regras de authority, checklist de providers e **matriz de cobertura/delta de capacidades**. Estados planejados/preparatórios/bloqueados não podem ser promovidos a runtime disponível.
5. **os três guias completos e a modlist atual:** novos mods adicionados depois do snapshot precisam entrar nos guias pertinentes e na cobertura do Chat 1. Delta atual já registrado: **Mobstein 5.4.4** em Gameplay e Magia.
6. **`main` do repositório:** fonte de verdade do estado técnico realmente implementado.

O arquivo individual **não substitui silenciosamente o Notion**. Quando código e especificação divergirem, a divergência deve ser corrigida no design canônico ou registrada tecnicamente; nunca é permitido inventar um fallback para fazer o código parecer completo.

## Gate de lote antes da auditoria individual

Antes de fechar qualquer lote, o Chat 1 deve executar `../guides/projects/12-capability-delta-coverage.md`:

1. fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos próprios;
2. comparação contra o baseline reconciliado;
3. identificação de toda capacidade jogável nova ou semanticamente alterada;
4. classificação da cobertura mesmo que nenhuma perk atual já cite a capacidade.

Esse gate responde a pergunta **provider → árvore** e impede a lacuna circular “não existe perk, então não procurei a capacidade”.

A classificação obrigatória é: `COBERTA POR PERK EXISTENTE`, `PERK PRÓPRIA`, `ESPECIALIZAÇÃO`, `BRIDGE`, `COBERTO POR SISTEMA UNIVERSAL`, `PROGRESSÃO NATIVA AUTORITATIVA`, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`.

Detectar lacuna não autoriza alterar o tamanho do lote. O Chat 1 trabalha em **lotes exatos de 10**; necessidade fora do lote atual é registrada para ciclo posterior.

## Estrutura obrigatória de cada dossiê

Cada perk deve registrar:

- identificação e proveniência;
- todos os campos canônicos do Notion aplicáveis;
- resultado explícito dos nove eixos de auditoria;
- contrato técnico esperado;
- evidência concreta encontrada na `main` ou na PR de correção em auditoria;
- comportamento fail-closed/fallback;
- pendências e lacunas conhecidas;
- testes existentes e testes ainda necessários;
- status semântico separado de status de implementação.

Quando qualquer projeto próprio for pertinente, o dossiê também deve registrar provider/consumer, authority, boundary/API/hook concreto, versão/evidência, causalidade, deduplicação, fallback, fail-closed e qual pipeline/estado não pode ser duplicado ou escrito diretamente.

## Estados

- **DESIGN APROVADO:** passou pelo protocolo de design/auditoria; não implica implementação completa.
- **CÓDIGO PRESENTE:** existe implementação relevante; não implica que todo fallback ou provider esteja coberto.
- **PENDÊNCIA:** existe divergência, cobertura ausente ou prova técnica ainda necessária.
- **FAIL-CLOSED:** funcionalidade não é substituída por bônus genérico quando o hook seguro não existe.
- **IMPLEMENTAÇÃO CONFIRMADA:** comportamento relevante, testes e integração possuem evidência verificável e passaram CI antes do merge.

## Protocolo dos nove eixos

Cada arquivo deve avaliar explicitamente:

1. dependências, bloqueios e gates;
2. integração global com sistemas compartilhados do modpack;
3. qualidade e identidade;
4. ramificação, distância e topologia;
5. especializações;
6. tradução PT-BR;
7. preenchimento completo do Notion;
8. remoção total do NeoVitae;
9. cobertura da modlist e integração entre mods, incluindo delta dos projetos próprios e novos mods externos adicionados após o snapshot.

## Lotes

- **Lote 1:** A0001–A0010.
- **Lote 2:** A0011–A0020.

Dossiês individualizados e reauditoria obrigatória concluída neste recorte: **A0001–A0020**.

Documentos de controle:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` — cópia local dos critérios canônicos.
- `CHAT-1-PROJETOS-PROPRIOS-REGRA.md` — suplemento obrigatório para auditar os quatro projetos próprios e o delta de capacidades.
- `../guides/projects/README.md` — fonte operacional dos quatro dossiês e da matriz cruzada.
- `../guides/projects/12-capability-delta-coverage.md` — gate provider → árvore obrigatório por lote.
- `AUDITORIA-A0001-A0020.md` — matriz consolidada da reauditoria dos vinte nodes.
- `STATUS.md` — índice técnico resumido.

Fonte canônica dos critérios: https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
