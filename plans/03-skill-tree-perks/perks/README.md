# Dossiês Canônicos de Perks

Esta pasta contém um dossiê individual por perk e os documentos operacionais do sistema de auditoria.

## Organização

- `Axxxx-*.md` — dossiê canônico individual da perk.
- `STATUS.md` — índice resumido do estado de design/implementação e das pendências.
- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` — cópia versionada dos critérios canônicos.
- `CHAT-1-PROJETOS-PROPRIOS-REGRA.md` — suplemento obrigatório provider→árvore.
- `audits/` — **todas as auditorias históricas, de implementação e retroativas por lote**. Não criar novos `AUDITORIA-*.md` na raiz de `perks/`.

A separação `dossiê individual` + `audits/` evita que a raiz se torne uma lista de arquivos de auditoria e preserva diffs/revisões por lote sem concentrar todo o histórico em um único arquivo gigante.

## Fontes de verdade

1. **Notion — Catálogo Mestre — Atributos e Passivos:** fonte canônica de design para código, nome, domínio, árvore, ramo, camada, função, tier, faixa de poder, ranks, custo, dependências, pré-requisitos, provider/mods, efeito, escalonamento, gate, hook, fallback e regra.
2. **Notion — Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree:** protocolo canônico de auditoria em nove eixos, provider-native first, fail-closed, pipeline canônico único, anti-duplicação, anti-abuso de Mastery e delta obrigatório de capacidades dos projetos próprios.
3. **`CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`:** cópia versionada integral dos critérios para tornar a auditoria reproduzível no GitHub. O Notion continua canônico e prevalece em caso de divergência futura.
4. **`../guides/projects/`:** snapshot operacional obrigatório dos projetos próprios **RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana**, incluindo matriz de integração cruzada, reconciliação de snapshots, regras de authority, checklist de providers e matriz de cobertura/delta de capacidades. Estados planejados/preparatórios/bloqueados não podem ser promovidos a runtime disponível.
5. **os três guias completos e a modlist atual:** novos mods adicionados depois do snapshot precisam entrar nos guias pertinentes e na cobertura do Chat 1. Delta externo atualmente auditado: **Mobstein 5.4.4**.
6. **`main` do repositório:** fonte de verdade do estado técnico realmente implementado.

O arquivo individual **não substitui silenciosamente o Notion**. Quando código e especificação divergirem, a divergência deve ser corrigida no design canônico ou registrada tecnicamente; nunca é permitido inventar um fallback para fazer o código parecer completo.

## Gate de lote antes da auditoria individual

Antes de fechar qualquer lote, o Chat 1 deve executar `../guides/projects/12-capability-delta-coverage.md`:

1. fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos próprios;
2. comparação contra o baseline reconciliado;
3. identificação de toda capacidade jogável nova ou semanticamente alterada;
4. classificação da cobertura mesmo que nenhuma perk atual já cite a capacidade.

Esse gate responde **provider → árvore**. A classificação obrigatória é: `COBERTA POR PERK EXISTENTE`, `PERK PRÓPRIA`, `ESPECIALIZAÇÃO`, `BRIDGE`, `COBERTO POR SISTEMA UNIVERSAL`, `PROGRESSÃO NATIVA AUTORITATIVA`, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`.

Detectar lacuna não autoriza alterar o tamanho do lote. Chat 1 e Chat 2 trabalham em **lotes exatos de 10 perks consecutivas** e param após PR/CI/merge/main do lote atual.

## Estrutura obrigatória de cada dossiê

Cada perk deve registrar:

- identificação e proveniência;
- campos canônicos relevantes do Notion;
- resultado dos nove eixos de auditoria;
- contrato técnico esperado;
- evidência concreta da `main`/PR auditada;
- comportamento fail-closed/fallback;
- pendências e lacunas conhecidas;
- testes existentes e testes ainda necessários;
- status de design separado do status de implementação;
- quando pertinente, provider/consumer, authority, boundary/API/hook, causalidade, deduplicação e pipeline/estado que não pode ser duplicado.

## Estados

- **DESIGN APROVADO:** passou pelo protocolo de design/auditoria; não implica implementação completa.
- **CÓDIGO PRESENTE:** existe implementação relevante; não implica que todo fallback ou provider esteja coberto.
- **IMPLEMENTAÇÃO PARCIAL / NÃO CONFIRMADA:** existe gap funcional, de integração, causalidade, lifecycle ou teste explícito.
- **PENDÊNCIA:** divergência, cobertura ausente ou prova técnica ainda necessária.
- **FAIL-CLOSED:** funcionalidade não é substituída por bônus genérico quando o hook seguro não existe.
- **IMPLEMENTAÇÃO CONFIRMADA:** comportamento relevante, testes e integração possuem evidência verificável e passaram CI/merge na `main`.

## Protocolo dos nove eixos

Cada arquivo deve avaliar:

1. dependências, bloqueios e gates;
2. integração global com sistemas compartilhados do modpack;
3. qualidade e identidade;
4. ramificação, distância e topologia;
5. especializações;
6. tradução PT-BR;
7. preenchimento completo do Notion;
8. remoção total do NeoVitae;
9. cobertura da modlist e integração entre mods, incluindo delta dos projetos próprios e novos mods externos.

## Ciclos documentados

As auditorias detalhadas ficam em [`audits/`](audits/README.md). Atualmente existem registros para:

- A0001–A0010;
- A0011–A0020;
- A0021–A0030;
- A0031–A0040;
- A0041–A0050.

O intervalo nunca é fixado permanentemente: o próximo lote é determinado pelo estado real de `STATUS.md`, Notion e dossiês.

## Documentos de controle

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` — critérios locais versionados.
- `CHAT-1-PROJETOS-PROPRIOS-REGRA.md` — regra provider→árvore.
- `../guides/projects/README.md` — fontes dos projetos próprios.
- `../guides/projects/12-capability-delta-coverage.md` — gate de capability delta obrigatório.
- `audits/README.md` — índice de todas as auditorias.
- `audits/AUDITORIA-A0001-A0020.md` — matriz consolidada histórica.
- `STATUS.md` — índice técnico atual.

Fonte canônica dos critérios: https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
