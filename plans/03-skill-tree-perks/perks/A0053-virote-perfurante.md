# A0053 — Virote Perfurante

## Estado

- **Design:** APROVADO após correção de availability/provenance, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-811a-9656-f34ddd39f999`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL; caminho de penetration presente, mas nó estruturalmente indisponível enquanto A0052 não puder ser adquirido e o consumo atual ocorre cedo demais para lançamentos cancelados.

## Contrato canônico

- A0052 ≥1 + gateway `epic_crossbow`; availability de A0052 é obrigatória.
- Com 2 Cadências, disparo CROSSBOW totalmente carregado pode consumir 2 para +10%/+15% penetration e +15%/+25% impact.
- Exige launch receipt CROSSBOW server-authoritative e projectile/root correlacionado.
- O custo segue **reservation→commit**: tentativa pode reservar 2 Cadências, mas commit só ocorre quando criação do projectile/root correlacionado é confirmada. Cancelamento tardio, ausência de spawn, perda do rank/pré-requisito ou rules reload que invalide a ação libera a reserva sem consumo.
- Componentes são independentes; aplicar apenas os semanticamente seguros.
- Primeiro impacto elegível do mesmo projectile/root recebe o efeito uma vez.
- Ricochetes, perfurações posteriores, derivados, dano periódico, Backlash ou projectile de companion não reaplicam.
- Reservas pendentes não sobrevivem a rank loss/respec/rules reload que invalide A0053 ou seus pré-requisitos.

## Evidência runtime

`tryPiercingBolt(...)` exige duas cargas e ao menos penetration/impact disponível, porém é chamado dentro de `onArrowLoose(...)` e já consome Cadências antes de a criação do projétil ser confirmada. Como listener posterior pode cancelar `ArrowLooseEvent`, é possível perder as duas Cadências sem projectile/root materializado. O bridge precisa reservar no lançamento e commit/rollback em função da criação efetiva do projétil.

O caminho de penetration em primeiro impacto existe; impact permanece fail-closed quando não há provider semântico seguro. O segundo review também exige que projectile sem launch receipt real não possa ser promovido a root CROSSBOW elegível e que reservas sejam descartadas quando a progressão for reconciliada.

## Pendências para Chat 2

- **P-A0053-01:** propagar availability A0050→A0052→A0053 no catálogo/purchase path; não permitir rank no-op/bypass.
- **P-A0053-02:** transformar consumo de 2 Cadências em reservation→commit ligado à criação confirmada do projectile/root; cancelamento tardio/ausência de spawn deve rollback integralmente.
- **P-A0053-03:** exigir launch receipt CROSSBOW real antes de criar/consumir a ação especial; projectile derivado/reemitido sem correlação fica fail-closed.
- **P-A0053-04:** limpar qualquer reserva pendente em rank loss, respec ou rules reload que invalide A0053/pré-requisitos.
- Revalidar first-impact/dedup no GameTest real, incluindo multi-pierce/ricochet/derivado e cancelamento por listener posterior.
- Herdar blockers de aquisição CROSSBOW de A0049/A0050/A0052; não considerar perk alcançável até a cadeia inteira ser válida.

## Provider→árvore

Nenhum dos projetos próprios ou Mobstein fornece penetration/impact CROSSBOW alternativo. Stage 11 itemization continua authority separada e `SEM HOOK SEGURO` para projetar seus rolls nesta perk.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0052 ≥1 + `epic_crossbow`; availability herdada de A0050/A0052 e blockers CROSSBOW impedem bypass. |
| 2. Integração global | **PASS** | Consome somente Cadência própria; penetration/Impact usam providers canônicos quando seguros; magia, Shroud, hazards e companions não substituem componentes. |
| 3. Qualidade e identidade | **PASS** | Notable de gasto deliberado de Cadência para tiro de alto compromisso; muda decisão de combate e não é bônus plano permanente. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 após A0052 no ramo Cadência/Perfuração; custo e pré-requisito preservam progressão real. |
| 5. Especializações | **PASS** | Continua MARTIAL/BESTAS; não invade magia/tecnologia e não cria classe específica de mod. |
| 6. PT-BR | **PASS** | Texto de jogador em PT-BR; `penetration`, `Impact`, IDs e hooks técnicos aparecem apenas como termos de implementação quando necessário. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos; reservation→commit, launch provenance e lifecycle re-fetched após review. |
| 8. NeoVitae | **PASS** | Ausente de providers, gates e fallback. |
| 9. Cobertura modlist/providers | **PASS** | RPG/Epic Fight/Apothic/WoM quando aplicáveis e own-project/Mobstein boundaries foram dispostos; Stage 11 permanece `SEM HOOK SEGURO`. |

Os 18 critérios técnicos cumulativos passam **no design**; os gaps runtime permanecem catalogados e fail-closed, sem simular implementação.

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram reservation→commit, launch provenance e lifecycle de reconciliação; re-fetch pós-review PASS em 2026-08-30.
