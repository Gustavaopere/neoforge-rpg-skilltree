# A0082 — Vampirismo de Arma

## Estado

- **Design:** APROVADO após hardening de dedup provider-native em 2026-08-31.
- **Notion:** `3c569db9-f0db-813d-bba3-c9abdd43e9a6`; Hook/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- Ignitium/Blazing Brand permanece **FAIL-CLOSED por fonte** até existir receipt exato da cura provider-native final.

## Contrato canônico

- A0061 Força Aplicada ≥2 + ao menos um gateway de arma física.
- 3 ranks: 0,6% / 1,2% / 1,8% do dano direto pós-mitigação de arma/projétil físico elegível.
- Toda cura entra no `SustainResolver`: uma identidade causal resolve no máximo uma vez, maior coeficiente elegível vence e o teto compartilhado é 3% da vida máxima em janela móvel de 20 ticks.
- Overkill é cortado e missing health limita o pagamento final.

## Provider-native first e Ignitium

Simply Swords: Cataclysm 1.0.2 possui lifesteal provider-native em Ignitium/Blazing Brand. O upstream confirma que o efeito chama `attacker.heal(...)` diretamente dentro do callback de hit e que `simplycataclysm:ignitium_gear` lista as quinze famílias Ignitium do addon.

Não existe receipt final da mesma cura exposto ao RPG Skill Tree. Portanto o Chat 2 **não** usa `EXACT_INTERCEPTED` fictício: qualquer root cuja arma esteja no tag `simplycataclysm:ignitium_gear` entra como `NativeCorrelation.AMBIGUOUS` e recebe cura Skill Tree igual a zero. Armas físicas comprovadas fora dessa fonte continuam funcionando.

## Cobertura de providers

- Minecraft/NeoForge: POST damage e cura.
- Epic Fight 21.17.3.1: classificação/root provider-native quando aplicável.
- Simply Swords 1.70.2: weapon types/implicits/runic/uniques permanecem provider-native.
- Simply More 1.3.0 ALPHA: somente armas/efeitos concretamente provados; unique sem efeito real não ganha semântica inventada.
- Integrated Simply Swords 1.4.0: bridge material, coberta pela classificação universal quando a origem for comprovada.
- Simply Swords: Cataclysm 1.0.2: tag `simplycataclysm:ignitium_gear` é boundary de native-heal ambíguo.
- Vampirism 1.10.12: não recebe integração sem receipt concreto correlacionável; economia de sangue não é lifesteal presumido.
- Pufferfish's Attributes 0.8.3 não é provider genérico de sustain.
- Tech machines/turrets/fake players, summons/companions e hazards ambientais são excluídos.

## Implementação Chat 2 — 2026-09-01

- `A0081A0090SustainRuntime` centraliza A0082 no mesmo `SustainResolver` existente;
- Epic Fight PRE publica `PhysicalHitReceipt` com a root provider-native e `ItemStack` usado; a cura só é calculada no NeoForge POST com dano pós-mitigação final;
- fallback vanilla corpo a corpo aceita apenas `minecraft:player_attack` no `DamageSource` vanilla direto, impedindo que qualquer source custom/ability seja promovida só porque há uma arma na main hand;
- bow/crossbow exigem launch receipt e siblings da mesma janela de lançamento compartilham root, cobrindo Multishot sem multiplicar o pipeline;
- Ignitium usa o tag provider-native `simplycataclysm:ignitium_gear`; essas roots são enviadas ao `SustainResolver` como `AMBIGUOUS` e a parcela Skill Tree falha fechado;
- `SustainResolver` continua owner de max-coefficient, claim-once, cap 3%/20 ticks, overkill e missing-health clipping;
- nenhuma bridge de Vampirism, summon, companion, machine ou proc de terceiro foi inventada.

## Checklist Chat 2

- [x] Hook físico pós-mitigação implementado
- [x] Root Epic Fight provider-native preservada
- [x] Fallback vanilla estreito implementado
- [x] Projectile launch/Multishot root implementado
- [x] `SustainResolver` único preservado
- [x] Ignitium native lifesteal fail-closed por tag provider-native
- [x] Deduplicação por root preservada
- [x] Código presente
- [ ] **PENDÊNCIA NÃO BLOQUEANTE:** `EXACT_INTERCEPTED` de Ignitium continua ausente; fonte fica com Skill Tree=0
- [ ] **VALIDAÇÃO CHAT 3:** armas vanilla/Epic Fight/projectile/Multishot
- [ ] **VALIDAÇÃO CHAT 3:** Ignitium não recebe double-heal
- [ ] **VALIDAÇÃO CHAT 3:** cap/missing health/overkill/dedup/multiplayer
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Pendências para Chat 3

- validar que o tag Ignitium cobre as armas instaladas e que cada root recebe parcela Skill Tree exatamente zero;
- validar provider-present/absent e nenhum linkage obrigatório com SimplyCataclysm;
- validar que Epic Fight e NeoForge não resolvem a mesma root duas vezes;
- validar Multishot, projectile cancelado/zero damage e source custom/ability inelegível.

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

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.