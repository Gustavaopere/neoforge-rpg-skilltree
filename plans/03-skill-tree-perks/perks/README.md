# Dossiês Canônicos de Perks

Esta pasta substitui o checklist agregado `07-perk-implementation-checklist.md` por um dossiê individual por perk.

## Fontes de verdade

1. **Notion — Catálogo Mestre — Atributos e Passivos:** fonte canônica de design para código, nome, domínio, árvore, ramo, camada, função, tier, faixa de poder, ranks, custo, dependências, pré-requisitos, provider/mods, efeito, escalonamento, gate, hook, fallback e regra.
2. **Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree:** protocolo obrigatório de auditoria em nove eixos, provider-native first, fail-closed, pipeline canônico único, anti-duplicação e anti-abuso de Mastery.
3. **`main` do repositório:** fonte de verdade do estado técnico realmente implementado.

O arquivo individual **não substitui silenciosamente o Notion**. Quando código e especificação divergirem, o dossiê preserva a especificação canônica e registra a diferença em `Pendências técnicas`.

## Estrutura obrigatória de cada dossiê

Cada perk deve registrar:

- identificação e proveniência;
- todos os campos canônicos do Notion aplicáveis;
- resultado explícito dos nove eixos de auditoria;
- contrato técnico esperado;
- evidência concreta encontrada na `main`;
- comportamento fail-closed/fallback;
- pendências e lacunas conhecidas;
- testes existentes e testes ainda necessários;
- status semântico separado de status de implementação.

## Estados

- **DESIGN APROVADO:** passou pelo protocolo de design/auditoria; não implica implementação completa.
- **CÓDIGO PRESENTE:** existe implementação relevante em `main`; não implica que todo fallback ou provider esteja coberto.
- **PENDÊNCIA:** existe divergência, cobertura ausente ou prova técnica ainda necessária.
- **FAIL-CLOSED:** funcionalidade não é substituída por bônus genérico quando o hook seguro não existe.
- **IMPLEMENTAÇÃO CONFIRMADA:** só deve ser usada quando o comportamento relevante, testes e integração tiverem evidência verificável em `main`.

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
9. cobertura da modlist e integração entre mods.

## Lotes

A migração é feita em lotes pequenos para permitir re-fetch do Notion, auditoria e revisão técnica por perk.

- **Lote 1:** A0001–A0010.
- **Lote 2:** A0011–A0020.

Dossiês individualizados até o momento: **A0001–A0020**.

Fonte de critérios: https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
