# A0104 — Segundo Vento

## Estado

- **Design:** APROVADO após hardening causal em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a3-b4cc-cdd738981bbf`; corrigido e verificado pós-escrita.
- **Runtime:** scheduler/state ainda precisam ser implementados.

## Contrato canônico

- Gateway VITALITY + A0096 Último Fôlego =3.
- Crossing estrito: vida imediatamente anterior >25% e vida após dano confirmado <25%.
- Ao ativar: cinco pulsos de 2,4% max HP em +20/+40/+60/+80/+100 ticks; total potencial 12%.
- Cada novo dano direto hostil elegível confirmado na janela cancela exatamente o próximo pulso não pago.
- Cooldown 60 s inicia na ativação.

## Boundary de implementação

`LivingDamageEvent.Post` é a authority do crossing porque já representa perda real de vida. Calcular `preHealth = healthAfter + event.getNewDamage()` e exigir `preRatio > 0,25 && postRatio < 0,25`. O mesmo classificador causal hostil de A0096/A0097 deve ser reutilizado.

O scheduler mantém pulsos por `gameTime`; cada novo Post válido cria no máximo um cancelamento e o próximo pulse consome esse cancelamento. Cura passa pelo pipeline canônico de healing received e não alimenta sustain recursivo.

## Cobertura de providers

- Minecraft/NeoForge: Post confirmado e healing pipeline.
- Epic Fight/modded: convergem pelo DamageSource causal; não precisam de regra paralela.
- Black Arcana self-cost/Backlash, ambiente e resource cost: inelegíveis.

## Pendências para Chat 2

- `P-A0104-01`: scheduler/cooldown/crossing estrito e classificador hostil compartilhado.
- `P-A0104-02`: cancelamento de próximo pulso, lifecycle/persistência e regressões de zero damage/self/environment.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0096=3 + VITALITY. |
| Integração global | PASS | healing pipeline único. |
| Qualidade/identidade | PASS | recuperação interrompível pós-burst. |
| Topologia | PASS | notable low-health. |
| Especializações | PASS | sem mastery paralela. |
| PT-BR | PASS | crossing/pulsos claros. |
| Notion | PASS | hardening persistido. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Post NeoForge real. |

Os 18 critérios passam; PRE não é usado para fingir hit confirmado.