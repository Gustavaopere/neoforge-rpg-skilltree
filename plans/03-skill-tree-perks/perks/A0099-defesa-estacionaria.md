# A0099 — Defesa Estacionária

## Estado

- **Design:** APROVADO após hardening de lifecycle/forced transitions em 2026-08-31.
- **Notion:** `3c569db9-f0db-81e9-97e9-fd9303618c3a`; fetch fresco PASS.
- **Runtime observado:** reutiliza `StationaryStateService`, mas invalidações explícitas de mount/vehicle/forced movement ainda estão incompletas.

## Contrato canônico

- Gateway VITALITY + A0089 Couro Endurecido ≥2 + acesso real ao corredor MARTIAL.
- 3 ranks: após o detector confirmar 30 ticks estacionário, −4% de dano hostil elegível por rank, máximo −12%, enquanto o estado permanecer válido.
- Um único detector canônico: 30 ticks consecutivos e caminho 3D acumulado ≤0,10 bloco.
- Teleporte, dimensão, mount/vehicle e deslocamento forçado identificado invalidam imediatamente; nenhum segundo threshold local é permitido.

## Evidência runtime

`StationaryStateService` contém `REQUIRED_TICKS=30` e `MAX_PATH_LENGTH=0.10`. A0099 consulta exatamente o mesmo serviço usado por A0079. O bridge atual amostra posição com `forcedTransition=false`; lifecycle geral limpa em alguns eventos, porém não cobre todas as transições forçadas identificáveis.

## Cobertura de providers

- RPG Skill Tree é owner do detector estacionário; Minecraft/NeoForge fornece posição/lifecycle.
- Epic Fight só reutiliza/amostra o detector em seu bridge; não define segundo estado.
- Create belts/contraptions, mount/vehicle, knockback e teleporte devem invalidar quando identificados; delta de posição por si só não substitui receipt de forced transition quando um provider o disponibilizar.
- Magia, tecnologia e projetos próprios não recebem integração positiva sem produzir deslocamento causal relevante.

## Pendências para Chat 2

- **P-A0099-01:** propagar `forcedTransition=true`/invalidate para teleport, mount/vehicle e deslocamentos provider-identificados; reutilizar o mesmo serviço de A0079.
- **P-A0099-02:** testar 29/30 ticks, path 0,10/>0,10, microjitter, dimensão, relog, mount/vehicle, knockback e multiplayer.
- **P-A0099-03:** validar `BRIDGE_PP_POLICY` Stage 04.02; pontos não contam simultaneamente para VITALITY e MARTIAL.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0089≥2 + corredor MARTIAL. |
| Integração global | PASS | detector compartilhado A0079/A0099. |
| Qualidade/identidade | PASS | defesa plantada distinta da móvel. |
| Topologia | PASS | VITALITY↔MARTIAL bridge. |
| Especializações | PASS | no máximo um threshold whitelisted. |
| PT-BR | PASS | threshold canônico documentado. |
| Notion | PASS | fetch fresco, sem drift funcional. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | forced movement só por evidence segura. |

Os 18 critérios passam no design; o Chat 2 deve fechar as invalidações faltantes sem duplicar detector.