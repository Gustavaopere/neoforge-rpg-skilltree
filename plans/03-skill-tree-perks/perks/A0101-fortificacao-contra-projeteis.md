# A0101 — Fortificação contra Projéteis

## Estado

- **Design:** APROVADO em 2026-08-31.
- **Notion:** `3c569db9-f0db-8131-ba86-dbd530a50b0e`; corrigido e verificado pós-escrita.
- **Runtime:** consumer A0101 ainda não existe na `main`; availability deve permanecer fail-closed até o Chat 2 materializar o resolver.

## Contrato canônico

- Gateway VITALITY + A0089 Couro Endurecido ≥1.
- 4 ranks, +2% por rank, teto próprio +8%.
- Elegível somente quando o mesmo root possui `delivery=PROJECTILE` e `nature=PHYSICAL` explicitamente comprovados.
- Fonte classificada como magia não recebe A0101, mesmo sendo projétil.
- Um `DamageContainer` recebe no máximo uma contribuição A0101.

## Boundary de implementação

NeoForge 1.21.1 expõe `LivingDamageEvent.Pre` depois das reduções vanilla anteriores à perda de vida. O Chat 2 deve criar/usar um `DamageMitigationResolver` RPG-owned nesse boundary, classificar a fonte uma única vez e multiplicar somente o dano elegível.

Adapters de Epic Fight ou outros providers só podem acrescentar identidade por DamageType/tag/capability versionada; animação, velocidade, item equipado e classe da entidade projectile não são prova.

## Cobertura de providers

- Minecraft/NeoForge 21.1.248: owner do pipeline e DamageTypes.
- Epic Fight 21.17.3.1: apenas fontes que convergem ao DamageSource/adapter causal.
- Projéteis mágicos de Iron's/Ars: pertencem ao eixo mágico e ficam fora daqui quando classificados como magia.
- Tecnologia/firearms: nenhuma família é presumida por namespace; exige mapping explícito.

## Pendências para Chat 2

- `P-A0101-01`: implementar classificador `PROJECTILE + PHYSICAL` e consumer em `LivingDamageEvent.Pre`.
- `P-A0101-02`: availability não comprável antes do consumer; dedup/root e testes positivos/negativos, incluindo magic projectile.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0089≥1 + VITALITY. |
| Integração global | PASS | eixo tipado, sem Armor duplicada. |
| Qualidade/identidade | PASS | defesa balística física específica. |
| Topologia | PASS | ramo VITALITY camada 2. |
| Especializações | PASS | não cria mastery paralela. |
| PT-BR | PASS | efeito e exclusões explícitos. |
| Notion | PASS | correção + verificação pós-escrita. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | NeoForge real + adapters explícitos. |

Os 18 critérios passam no design; a ausência atual do consumer é tratada por availability fail-closed, não por rank sem efeito.