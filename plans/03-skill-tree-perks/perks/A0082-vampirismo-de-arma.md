# A0082 — Vampirismo de Arma

## Estado

- **Design:** APROVADO após hardening de dedup provider-native em 2026-08-31.
- **Notion:** `3c569db9-f0db-813d-bba3-c9abdd43e9a6`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** backend físico canônico presente para armas comuns; integração de lifesteal nativo de Ignitium ainda não está correlacionada e deve falhar fechado por fonte.

## Contrato canônico

- A0061 Força Aplicada ≥2 + ao menos um gateway de arma física.
- 3 ranks: 0,6% / 1,2% / 1,8% do dano direto pós-mitigação de arma/projétil físico elegível.
- Toda cura entra no `SustainResolver`: uma identidade causal resolve no máximo uma vez, maior coeficiente elegível vence e o teto compartilhado é 3% da vida máxima em janela móvel de 20 ticks.
- Overkill é cortado e missing health limita o pagamento final.

## Provider-native first e Ignitium

Simply Swords: Cataclysm 1.0.2 possui lifesteal provider-native em Ignitium/Blazing Brand. Esse heal não pode ser reexecutado nem somado integralmente ao A0082.

Para roots com lifesteal nativo, a integração exige correlação exata da **mesma root action** e da cura nativa final realmente aplicada. Enquanto o adapter não provar isso, roots de Ignitium com lifesteal ficam inelegíveis para a parcela Skill Tree. É proibido deixar `NativeCorrelation.NONE` e pagar uma segunda cura.

Demais armas comprovadas continuam utilizáveis; a falta de binding de uma fonte específica não torna A0082 globalmente indisponível.

## Cobertura de providers

- Minecraft/NeoForge: POST damage e cura.
- Epic Fight 21.17.3.1: classificação/preset quando aplicável, sem apropriar efeitos Simply.
- Simply Swords 1.70.2: weapon types/implicits/runic/uniques permanecem provider-native.
- Simply More 1.3.0 ALPHA: somente armas/efeitos concretamente provados; unique sem efeito real não ganha semântica inventada.
- Integrated Simply Swords 1.4.0: bridge material, coberta pela classificação universal quando a origem for comprovada.
- Simply Swords: Cataclysm 1.0.2: Ignitium exige native-heal dedup exato.
- Vampirism 1.10.12: só entra se houver heal/lifesteal concreto correlacionável; economia de sangue não é lifesteal presumido.
- Pufferfish's Attributes 0.8.3 não é provider genérico de sustain.
- Tech machines/turrets/fake players, summons/companions e hazards ambientais são excluídos.

## Evidência runtime

`SustainResolver` já implementa max-coefficient, cap móvel, native correlation, clipping e dedup por root. `A0081A0100CombatEvents` hoje captura melee direto ou `AbstractArrow` físico e chama o resolvedor com `NativeCorrelation.NONE` para todos os casos. Logo armas comuns têm caminho funcional, mas Ignitium precisa ser excluído até o adapter nativo existir.

## Pendências para Chat 2

- **P-A0082-01 BLOQUEANTE POR FONTE:** interceptar/correlacionar lifesteal nativo de Ignitium no mesmo root e alimentar `EXACT_INTERCEPTED`; até lá excluir essa fonte da parcela A0082.
- **P-A0082-02:** endurecer provenance de arma/projétil para não classificar abilities/procs de Simply/terceiros pela simples main hand.
- **P-A0082-03:** garantir uma única root entre Epic Fight/NeoForge/provider bridges e testar Multishot/projéteis derivados quando aplicável.
- **P-A0082-04:** testes provider-present/absent, native heal menor/maior que parcela Skill Tree, cap, missing health, overkill e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0061≥2 + gateway físico. |
| Integração global | PASS | um SustainResolver e um bucket. |
| Qualidade/identidade | PASS | sustain marcial leve, sem roubar identidade Simply. |
| Topologia | PASS | ponte MARTIAL/SUSTAIN. |
| Especializações | PASS | PP sem dupla contagem automática. |
| PT-BR | PASS | contrato em PT-BR. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | native-first e fail-closed por fonte ambígua. |

Os 18 critérios passam **no design**; a pendência de Ignitium é técnica e explicitamente fail-closed.