# Create: Cold Sweat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e8a5ccfa35309653f6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Cold Sweat
- **Arquivo JAR:** `create_cold_sweat-1.1.2.jar`
- **Versão 1.21.1:** 1.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Clima, Tecnologia
- **Função:** Integra Create com Cold Sweat, atribuindo temperaturas a fluidos e acrescentando interações térmicas específicas de máquinas, tubulações e estruturas do Create.
- **Dependências:** Create 6.0.10 + Cold Sweat 2.4.2 estão fisicamente presentes. O addon é bridge/configuração térmica e não substitui Cold Sweat como provider da temperatura corporal/ambiental.
- **Sobreposição:** Complementa Cold Sweat com fórmulas/interações Create. Não é segundo sistema de temperatura; Cold Sweat permanece authority térmica. O antigo contexto TFC é histórico, não stack físico atual.
- **Compatibilidade/Riscos:** Riscos: double-count térmico, unidade/conversão de temperatura, pipes/boilers excessivos, fan heating/cooling stale, block-effect tagging ampla e server boot regression. 1.1.2 corrige crash de dedicated server no boot.
- **Observações:** JAR físico `create_cold_sweat-1.1.2.jar`, mod id `create_cold_sweat`, runtime 1.1.2. Release NeoForge 1.21.1 de 12/11/2025, Client & Server. A arquitetura física atual é Create 6.0.10 ↔ Cold Sweat 2.4.2; referências históricas a TFC/TFC Cold Sweat não representam o runtime atual.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create: Cold Sweat 1.1.2 + documentação pública do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-cold-sweat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; fluid temperatures, Create formulas, boiler/pipes/fans, config/data, server patch 1.1.2 e thermal authority catalogados.
- **Histórico da decisão:** Escolhido como ponte do Create para o sistema térmico central Cold Sweat. A arquitetura definida foi TFC → TFC Cold Sweat → Cold Sweat → Create: Cold Sweat. Confirmado carregado em 22/08/2026 na versão 1.1.2.
- **Data da última decisão:** 2026-08-22

> 🌡️ **ESCOPO CANÔNICO.** Runtime físico `create_cold_sweat-1.1.2.jar`. A bridge conecta Create 6.0.10 a Cold Sweat 2.4.2 para tratar temperaturas de fluidos e interações térmicas de objetos Create que datapacks/KubeJS isolados não modelam completamente.

## 1. Authority térmica
Cold Sweat continua owner da temperatura corporal/ambiental e da aplicação dos efeitos térmicos ao jogador. Create continua owner de pipes, fluids, boilers, fans e máquinas. Create: Cold Sweat traduz essas superfícies; não deve manter uma segunda temperatura do jogador.

## 2. Fluid temperatures
O projeto documenta fluid temperatures configuráveis e cálculo default baseado na temperatura anexada ao próprio fluid, normalmente em Kelvin. Isso permite que fluids Create/modded atuem como fonte/sumidouro térmico sem hardcode por item específico.
Datapacks/config são authority dos valores configurados; qualquer integração externa deve consultar o provider em vez de duplicar tabelas térmicas.

## 3. Create-specific formulas
A bridge acrescenta fórmulas especiais para objetos Create e block effects que não cabem bem em simples tags. Pipes podem atuar como aquecimento de piso, boilers podem ser configurados de setups perigosamente quentes a fontes moderadas de calor, e blocos compatíveis podem receber efeitos térmicos específicos.

## 4. Fans
A linha 1.1.x adicionou comportamento de Encased Fan para resfriar jogadores em direção à temperatura ambiente e opção para espalhar a temperatura do bloco à frente. Range é configurável. Isso exige evitar double-count quando outro mod também modifica temperatura por airflow.

## 5. Config e data
O projeto oferece menu/config e suporte a temperaturas de fluidos via dados. Auto-tagging de blocos elegíveis a block effects existe na linha atual. Config real do pack não foi lida nesta ficha; portanto nenhum range, multiplier ou temperatura absoluta é tratado como política ativa sem leitura do arquivo runtime.

## 6. Build exata 1.1.2
A release NeoForge 1.21.1 1.1.2 é explicitamente um **Server Patch**: corrige crash do servidor ao iniciar, além de descrição/logo. Dedicated-server cold boot é, portanto, regression gate obrigatório desta versão.

## 7. Stack físico atual
- Create 6.0.10.
- Cold Sweat 2.4.2.

As referências históricas da decisão a TFC → TFC Cold Sweat pertencem a um estado antigo do pack. A bridge ativa hoje é **Create ↔ Cold Sweat**; a decisão `Manter` é preservada, mas o corpo não trata TFC como dependência atual.

## 8. Client/server e multiplayer
Particles/visual airflow podem ser client-facing; temperatura efetiva, range, fluid/block influence e consequências ao jogador são server-authoritative. Dois clients perto da mesma fonte devem receber state coerente do servidor e não acumular efeito extra por render/tick local.

## 9. Riscos
1. Temperatura é aplicada duas vezes por Cold Sweat + bridge/custom script.
2. Kelvin/escala custom é convertida incorretamente.
3. Pipe/boiler aquece área muito maior que a config pretendida.
4. Fan esfria abaixo do limite/ambient target por stacking.
5. Block auto-tagging captura bloco inadequado.
6. Reload deixa fluid temperature ou range stale.
7. Dedicated server reintroduz crash de boot corrigido em 1.1.2.

## 10. Boundary para perks/quests
Temperatura visual de máquina não é evento de progressão. Se um projeto próprio precisar reagir a exposição térmica, deve ler o state autoritativo de Cold Sweat/bridge e não inferir calor apenas pela proximidade de bloco Create.

## 11. Matriz de testes
- [ ] Dedicated server inicia com CCS 1.1.2 + Create 6.0.10 + Cold Sweat 2.4.2.
- [ ] Fluid quente/frio altera temperatura uma única vez e na direção correta.
- [ ] Pipe/floor warming respeita range/config.
- [ ] Boiler não produz efeito duplicado em overlaps.
- [ ] Fan converge para ambient e não ultrapassa o comportamento configurado.
- [ ] Fan spread usa corretamente a temperatura do bloco frontal.
- [ ] `/reload`/config reload não mantém valores stale.
- [ ] Multiplayer mantém o mesmo resultado térmico para estados equivalentes.
Nenhum teste foi marcado como aprovado.

## 12. Evidências e limite
CurseForge/Modrinth oficiais confirmam a build 1.1.2, Client & Server, o Server Patch e o escopo de fluid temperatures, fórmulas Create, pipes/boilers/fans e configuração. Valores efetivos da config do usuário não foram lidos e permanecem fail-closed.