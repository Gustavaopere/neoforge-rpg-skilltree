# A0315 — Avatar Selvagem

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0315` — https://app.notion.com/3c569db9f0db8125ae5ac65103589d75
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

Enquanto `NATURAL_FORM_STATE_V1` estiver válido, exatamente **uma categoria primária explícita** determina o perfil adaptativo. O tier depende de Nature Mastery 90–119 / 120–149 / ≥150:

- `PREDATOR`: dano ×1,08 / ×1,10 / ×1,12;
- `AQUATIC`: velocidade aquática ×1,08 / ×1,10 / ×1,12;
- `ROBUST`: mitigador recebido ×0,94 / ×0,92 / ×0,90;
- `SWIFT`: movimento terrestre ×1,06 / ×1,08 / ×1,10.

Categorias não acumulam. A classificação é registry/data-driven e versionada; unknown/ambiguous = nenhum perfil.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0310 3/3, forma natural desbloqueada e Nature Mastery ≥90. A0310/A0183 estão indisponíveis; purchase fail-before-spend.

## Provider-native first

Morph provider continua authority de forma, stats, abilities, transformação e lifecycle. A0315 lê uma identidade/category explicitamente exposta e aplica somente o perfil RPG aprovado. Não reescreve base stats do provider, não cria forma e não escolhe categoria por maior stat, tamanho, tipo de mob ou aparência.

## Causalidade e deduplicação

Um único profile effect ID por player. Mudança de mastery tier ou primary category substitui/reconcilia o modifier anterior; nunca empilha versões antigas. Damage/mitigation entram uma única vez no pipeline canônico correspondente.

## Fallback

Sem mapping `NATURAL_FORM_STATE_V1`/categoria segura, efeito = 0. Provider absent/version mismatch não autoriza fallback genérico de dano ou velocidade.

## Testes obrigatórios para Chat 3

1. fail-before-spend;
2. cada categoria aplica exclusivamente sua tabela 90/120/150;
3. duas categorias candidatas não empilham — primary explícita única;
4. unknown/ambiguous mapping = 0;
5. mudança de tier/category reconcilia sem stacking;
6. PREDATOR e ROBUST entram uma vez no dano/mitigação canônicos;
7. AQUATIC/SWIFT não alteram outros modos de movimento;
8. provider base stats/abilities permanecem intocados;
9. provider absent/version mismatch e lifecycle form/respec/logout/reload fail-closed;
10. multiplayer/dedicated server.

## Handoff Chat 2

Não inferir primary category e não modificar storage interno do morph provider.