# A0140 — Adaptação do Deserto

## Estado
**DESIGN APROVADO — COMPONENTES PROVIDER-GATED.**

## Contrato
Keystone SURVIVAL/ACCLIMATION_HOT, 1 rank, 2 PP. Lê 0–5 cargas `ENVIRONMENTAL_HOT` sem consumi-las. Cada carga pode reduzir 3% somente surcharge HYDRATION ambiental quente real, até 15%; componente histórico fisiológico de 4%/carga permanece reservado até mapper numérico real.

## Boundary
Cold Sweat 2.4.2 fornece exposição ambiental read-only para o AcclimationLedger. TWR 3.0.4 só participa se adapter expuser receipt `HYDRATION_ENVIRONMENTAL_HOT_SURCHARGE`; physiology HOT exige FUTURE_PROVIDER_CONTRACT específico.

## Exclusões
Não alterar WORLD/CORE/BODY, thresholds, resistência a fogo ou dano. Volcanoes é apenas futuro input térmico indireto via Cold Sweat.

## Chat 2
Implementar apenas componentes comprovados; se nenhum mapper/receipt estiver disponível, aquisição deve permanecer desabilitada. Nunca converter ausência de physiology mapper em alteração de temperatura.