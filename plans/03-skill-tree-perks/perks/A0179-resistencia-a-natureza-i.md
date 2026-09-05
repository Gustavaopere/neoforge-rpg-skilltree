# A0179 — Resistência a Natureza I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL, CONDICIONADO À EXISTÊNCIA DE CLASSIFIER NATURE ATIVO.**

Chat 1 não implementa runtime. O contrato está fechado para o Chat 2 porque `LivingDamageEvent.Pre` permite mitigação server-side e Iron's Spells 'n Spellbooks 3.16.3 fornece identidade NATURE explícita para `irons_spellbooks:nature_magic` no pack atual.

Porém Iron's é integração opcional do RPG Skill Tree. Portanto a família A0179/A0180 **não pode permanecer adquirível como no-op** quando Iron's estiver ausente ou quando o adapter rejeitar a versão instalada. O resolver deve derivar a disponibilidade a partir do mesmo conjunto de classifiers/adapters realmente ativos.

Notion corrigido e revalidado em 2026-09-02: `https://app.notion.com/p/3c569db9f0db81689d81fb4891784975`.

## Contrato

- VITALITY ↔ SURVIVAL ↔ ARCANE; camada 4; Ponte; 4 ranks; 1 PP/rank.
- Pré-requisito topológico: A0177 ≥1 **OU** Gateway VITALITY desbloqueado.
- Como A0177 está `UNAVAILABLE_NODE`, o caminho topológico adquirível no pack atual é Gateway VITALITY.
- **Gate de disponibilidade obrigatório:** pelo menos um classifier NATURE allowlisted/version-compatible deve estar ativo no servidor.
- Se nenhum classifier NATURE compatível estiver ativo: A0179 = `UNAVAILABLE_NODE`; a compra falha **antes** de gastar PP.
- Allocation legado de A0179 enquanto indisponível conta **0 PP** para gates/thresholds semânticos e permanece reembolsável/migrável.
- +4% de Resistência a Natureza por rank; máximo +16%.
- Bucket único: `RPG_NATURE_RESISTANCE`.
- 16% é o teto próprio de A0179, não cap defensivo global.

## Boundary e authority

Usar o mesmo `ElementalDamageMitigationResolver` da família defensiva elemental.

O resolver/registry de adapters deve fornecer o predicate de disponibilidade lógico:

`hasActiveNatureClassifier() == true`

Esse predicate **não é uma segunda authority**: ele é derivado do mesmo conjunto allowlisted/version-compatible que efetivamente classifica fontes NATURE no resolver. Não manter flag manual concorrente.

Classificação NATURE segura no snapshot atual:

1. Iron's 3.16.3 `irons_spellbooks:nature_magic` / tag provider `NATURE_MAGIC` por adapter exato;
2. outros providers somente por adapter versionado explícito que prove o `DamageSource` NATURE correspondente.

Não existe tag vanilla NATURE genérica aprovada. Portanto poison, thorn, dano físico de planta/fauna e ambiente **não** são fallback.

A classificação defensiva NATURE não prova magia DIRECT e não libera A0177/A0178.

## Pipeline canônico

### Aquisição / disponibilidade

`adapter registry reload/start -> computar se existe >=1 classifier NATURE ativo -> hasActiveNatureClassifier()`.

`purchase A0179 -> validar dependency/topologia -> validar hasActiveNatureClassifier() -> somente então debitar PP`.

Se o último classifier compatível desaparecer em reload/config/version mismatch, o node passa a `UNAVAILABLE_NODE`; não apagar silenciosamente allocation legado, mas tratá-lo como 0 PP para gates/thresholds e permitir respec/migração.

### Dano

`LivingDamageEvent.Pre -> classificar source NATURE uma vez -> calcular RPG_NATURE_RESISTANCE de A0179/A0180 -> clamp matemático seguro [0,1] -> setNewDamage(current * (1 - bucket)) uma vez`.

Adapters classificam; não aplicam redução por conta própria.

## Deduplicação

- um único `ElementalDamageMitigationResolver`;
- A0179 e A0180 somam no mesmo bucket;
- um root/evento recebe a família `RPG_NATURE_RESISTANCE` no máximo uma vez;
- não duplicar mitigação em listener Iron's + listener NeoForge;
- availability e classificação usam o mesmo registry/adapter set; não manter duas listas que possam divergir.

## Fallback e exclusões

### Família sem classifier ativo

- A0179/A0180 = `UNAVAILABLE_NODE`;
- compra fail-before-spend;
- allocation legado indisponível = 0 PP para gates/thresholds;
- allocation legado permanece reembolsável/migrável;
- não converter a perk em resistência genérica.

### Família com pelo menos um classifier ativo

Fonte desconhecida/não versionada é inelegível.

Proibido inferir NATURE por:

- poison/veneno genérico;
- planta, folha, espinho ou vine;
- fauna;
- fome/clima/bioma;
- cor/partícula;
- namespace/nome textual;
- magia genérica.

A topologia SURVIVAL pode conectar a ponte, mas não redefine o elemento nem a classificação de dano.

## Bridge PP

`PP_REGION: VITALITY_NATURE_BRIDGE/RESISTANCE`.

Contagem de bridge é unitária: o mesmo PP não pode satisfazer simultaneamente duas regiões como dois pontos distintos. Specialist só pode whitelistar por regra semântica explícita.

Allocation de A0179 em estado `UNAVAILABLE_NODE` não contribui PP até a família voltar a ter pelo menos um classifier ativo.

## Handoff Chat 2

Implementar A0179 no mesmo `ElementalDamageMitigationResolver`; adicionar classifier exato de Iron's `nature_magic`; derivar `hasActiveNatureClassifier()` do mesmo registry/adapter set e conectar esse predicate ao gate de aquisição/ativação da família A0179/A0180.

Não adicionar poison/ambiente como fallback. Não permitir compra se todos os classifiers NATURE estiverem ausentes/rejeitados.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16% quando a família estiver disponível;
2. Iron's 3.16.3 `nature_magic` positivo com provider/adapter exato;
3. source não-NATURE negativo;
4. poison/thorn/planta/fauna/ambiente negativos;
5. provider desconhecido/version mismatch de uma fonte individual fail-closed;
6. **Iron's ausente e nenhum outro classifier NATURE ativo -> A0179/A0180 `UNAVAILABLE_NODE`; compra A0179 falha antes do gasto**;
7. **adapter Iron's rejeita versão e nenhum outro classifier NATURE ativo -> mesmo fail-before-spend**;
8. se um segundo classifier NATURE allowlisted válido existir, ausência de Iron's não torna a família unavailable;
9. transição reload ativo -> nenhum classifier: allocation legado preservado, 0 PP para gates/thresholds e respec possível;
10. transição reload sem classifier -> classifier válido: família volta a ficar elegível sem duplicar allocation/efeito;
11. bucket aplicado exatamente uma vez;
12. composição A0179+A0180 sem double reducer;
13. bridge PP sem double-count e sem contar allocation unavailable;
14. A0177 unavailable não bloqueia rota via Gateway VITALITY **quando** `hasActiveNatureClassifier() == true`;
15. respec/reload/dedicated-server safety.
