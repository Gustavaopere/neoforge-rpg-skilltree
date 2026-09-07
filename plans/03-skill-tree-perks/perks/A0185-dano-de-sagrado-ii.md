# A0185 — Dano de Sagrado II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0184 já está indisponível sem `DIRECT_MAGIC_OUTCOME_V1`, portanto A0185 também permanece fechada. O design de `RPG_JUDGMENT` é válido, mas não deve ser materializado por listeners ad hoc.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8147b948c320229759a6`.

## Contrato de gameplay

- ARCANE ↔ HEALING; camada 5; Notable; 1 rank; 2 PP.
- Pré-requisitos: A0184 ≥3 + Holy Mastery ≥20.
- Primeiro outcome HOLY direto elegível pode armar `RPG_JUDGMENT` por 120 ticks quando, **antes do impacto**:
  - o alvo possui um estado HOLY explicitamente allowlisted por adapter; **ou**
  - o alvo está em `EntityTypeTags.UNDEAD`.
- O primeiro impacto apenas prepara.
- Próxima magia HOLY direta com `spell_id` diferente, mesmo alvo e dentro da janela:
  - consome `RPG_JUDGMENT` atomicamente;
  - multiplica somente o componente HOLY direto do mesmo outcome por ×1,18;
  - inicia recarga interna de 140 ticks.

## Classificação undead

Minecraft/NeoForge 1.21.1 possui `EntityTypeTags.UNDEAD`; essa é a rota autoritativa aprovada para undead. Não inferir undead por nome, textura ou heurística.

Ser undead **não transforma dano comum em HOLY**. O outcome consumidor continua exigindo HOLY direto.

## Estado HOLY externo

Não existe um debuff HOLY universal inventado. A rota por estado só entra quando um adapter versionado comprovar:

- state id allowlisted;
- state pré-existente no alvo;
- identidade do alvo;
- possibilidade segura de consulta;
- nenhuma inferência por luz, cura, oração ou nome textual.

Ausência desse adapter desativa apenas essa rota futura; a rota `UNDEAD` pode continuar quando direct outcome existir.

## Blocker — `DIRECT_MAGIC_OUTCOME_V1`

Obrigatório para armar e consumir a janela com action/spell/outcome identity e autoria causal. A0185 não instala um producer local.

## Deduplicação

Registrar por jogador→alvo:

- source `action_id`;
- source `spell_id`;
- target UUID;
- expiry tick;
- cooldown.

Mesmo action/outcome não arma nem consome duas vezes. Mesma spell não consome. DoT, summon, automação, fake player e derived component são inelegíveis.

## Fail-closed

Enquanto A0184/direct outcome estiver fechado:

- compra falha antes do gasto;
- legacy rank unavailable =0 PP e refund/migration;
- não criar `RPG_JUDGMENT` por dano comum;
- não converter undead em vulnerabilidade HOLY genérica;
- não criar segundo dano para aplicar ×1,18.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não implementar janela por eventos locais sem o outcome canônico. A rota state adapter é opcional/fail-closed; nunca inventar estado Sagrado universal.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. A0184 unavailable fecha A0185;
3. `EntityTypeTags.UNDEAD` positivo e entidade não-undead negativa;
4. undead não converte dano comum em HOLY;
5. estado não allowlisted/luz/cura/religião não arma;
6. quando direct existir: primeiro hit arma e não recebe bônus;
7. segundo spell diferente em ≤120t recebe ×1,18 e consome;
8. mesma spell/expirado não consome;
9. cooldown 140t começa somente no consumo bem-sucedido;
10. dedup por action/outcome e lifecycle multiplayer/reload.