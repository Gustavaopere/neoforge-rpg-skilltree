# A0110 — Conservação de Equipamento I

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; não existe hook público global pós-Unbreaking/pré-decremento final para ferramentas arbitrárias no NeoForge 1.21.1.  
**Notion:** https://app.notion.com/p/3c569db9f0db81a99900e883d1cfbb96

## Identidade e posição

- Domínio: `SURVIVAL`.
- Árvore: Principal — SURVIVAL.
- Ramo: Manutenção e Durabilidade.
- Camada: 1; função: Ramo.
- Ranks: 5; custo 1 PP/rank.
- Pré-requisito: Gateway SURVIVAL desbloqueado.

## Contrato congelado

Cada rank concede **1% de chance**, até **5%**, de um uso legítimo de **ferramenta manual elegível** que produziria decremento final confirmado de exatamente 1 ponto de durabilidade **não consumir esse ponto**.

Conservação não é reparo: não decrementa e devolve, não cria durabilidade e não atua depois do break. Armaduras, itens indestrutíveis, máquinas portáteis/energy items e equipamentos com maintenance loop próprio ficam fora.

## Boundary obrigatório futuro

A ordem necessária é:

1. o item/provider decide seu desgaste por `Item.damageItem`/equivalente;
2. prevenção nativa, incluindo `EnchantmentHelper.processDurabilityChange`/Unbreaking, resolve o decremento final;
3. somente se o decremento final confirmado for exatamente 1, A0110 realiza **no máximo uma rolagem server-side por uso causal**;
4. em sucesso, cancela exatamente esse decremento **antes** de `setDamageValue`/break final.

NeoForge `21.1.248` / Minecraft 1.21.1 não publica hoje um evento global nesse ponto para ItemStacks arbitrários. `IItemExtension#damageItem` ocorre antes da prevenção final/Unbreaking e não satisfaz o contrato; `ArmorHurtEvent` é específico de armadura e, além disso, armadura é explicitamente inelegível.

## Availability e P-0036

Enquanto P-0036 não provar um adapter pós-Unbreaking/pré-decremento real para ao menos uma família elegível, A0110 permanece **indisponível/não comprável** e purchase deve falhar antes do gasto de PP.

É proibido usar como fallback:

- `damageItem` como se fosse o boundary final;
- decrementar e reparar/devolver durabilidade depois;
- polling de `damageValue` e compensação posterior;
- somente armor hooks;
- `isDamageableItem` como elegibilidade universal.

## Provider, authority, causalidade e dedup

Minecraft/NeoForge continua owner da durabilidade base; providers de ferramentas/máquinas mantêm seus próprios maintenance loops. O RPG Skill Tree só poderá intervir quando houver seam causal antes da escrita final.

- uma rolagem por uso causal que efetivamente chegaria a decremento 1;
- se Unbreaking/provider já prevenir, A0110 não rola;
- multi-hit/area use não pode gerar múltiplas rolagens para o mesmo decremento final;
- provider desconhecido/máquina com manutenção própria falha fechado;
- client nunca decide conservação.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: ProgressionService pode representar availability, mas não inventa o hook de durabilidade.
- Volcanoes: equipamentos de pressão/maintenance próprios não são automaticamente ferramenta manual elegível.
- Enshrouded/Black Arcana: não fornecem seam global de durabilidade para esta perk.
- Nenhum projeto próprio fecha P-0036 no estado auditado.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS, Gateway SURVIVAL + availability externa explícita.
2. Integração global — PASS em design; runtime corretamente indisponível.
3. Qualidade/identidade — PASS, conservação pre-write distinta de reparo.
4. Topologia — PASS, fundamento SURVIVAL/maintenance.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS, maintenance loops preservados.

Authority, causalidade, dedup, RNG, eligibility, fallback, lifecycle e purchase fail-closed estão definidos sem hack pós-fato.

## Pendências para Chat 2

- `P-A0110-01`: materializar `UNAVAILABLE_NODE`/purchase fail-closed enquanto P-0036 não tiver seam real.
- `P-A0110-02`: não implementar conservação por `damageItem`, repair/refund, polling ou armor-only hook.
- `P-A0110-03`: se um adapter real for encontrado na API/código do provider, registrar versão/assinatura/ordem e implementar apenas se cumprir exatamente o boundary; divergência semântica volta ao Chat 1 para redesign.
- `P-A0110-04`: testes de availability e de ausência de gasto/rank fantasma no estado atual.

## Testes exigidos ao Chat 3

Estado atual: node indisponível sem gasto, Unbreaking não é interceptado incorretamente, nenhum repair/refund, armor/máquinas/indestrutíveis inelegíveis. Se P-0036 fechar: uma rolagem por decremento final 1, zero rolagem quando prevenção nativa já zerou desgaste, RNG determinístico/testável, break boundary, provider absent/present, respec/reload/multiplayer, GameTests, build, JAR e dedicated-server smoke.

## Atualização de implementação — Chat 2 (2026-09-05)

**Estado:** `CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

- `CombatPerkAvailabilityRuntime` materializa A0110 como indisponível enquanto P-0036 não possui seam pós-Unbreaking/pré-decremento final comprovado.
- Nenhum hook `damageItem`, repair/refund, polling de `damageValue` ou `ArmorHurtEvent` foi usado como substituto.
- A compra é bloqueada antes do gasto e ranks persistidos indisponíveis são mascarados para rank efetivo 0.
- Minecraft/NeoForge e providers de ferramentas continuam owners de durabilidade/maintenance; não foi criado segundo pipeline.
- Não há redesign a devolver ao Chat 1.

### Checklist Chat 2

- [x] `UNAVAILABLE_NODE` materializado
- [x] Purchase/effective-rank fail-closed implementado
- [x] Provider-native durability preservada
- [x] Repair/refund/polling/armor-only fallback rejeitado
- [x] Código presente no estado fail-closed aprovado
- [ ] **VALIDAÇÃO CHAT 3:** confirmar compra sem gasto/rank efetivo 0
- [ ] **VALIDAÇÃO CHAT 3:** confirmar ausência de interceptação pós-fato
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests/integração aplicáveis
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA
