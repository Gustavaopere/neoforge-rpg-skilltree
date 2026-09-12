# Create: Blocks & Bogies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c6bcd1e5c56abd17b5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Blocks & Bogies
- **Arquivo JAR:** `create_bb-1.0.8-1.21.1.jar`
- **Versão 1.21.1:** 1.0.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Visual
- **Função:** Adiciona estilos de bogies/rodados ferroviários e opções de personalização para trens do Create.
- **Dependências:** Create 6.0.10 obrigatório. Steam 'n' Rails 0.3.0-beta.2 está fisicamente presente como integração/espaço ferroviário, mas não é requisito obrigatório da release. Flywheel é parte do stack Create e regression gate da 1.0.8.
- **Sobreposição:** Complementa o catálogo ferroviário do Create/Steam 'n' Rails com bogies configuráveis. Sobreposição é principalmente visual/rodados; ownership de train movement permanece no Create.
- **Compatibilidade/Riscos:** Riscos: bogie model/rotation/light divergence, serialization de estilo, assembly train, UI client vs server state, overlap visual com Steam 'n' Rails e performance/model complexity. Não confundir variedade visual com alteração de train physics.
- **Observações:** JAR físico `create_bb-1.0.8-1.21.1.jar`, mod id `create_bb`, runtime 1.0.8. Release NeoForge 1.21.1 de 24/08/2026, Client & Server. 1.0.8 corrige iluminação XL10S-Gearless e rotação L6S-Gearless com Flywheel backend em bogies montados.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create: Blocks & Bogies 1.0.8.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-blocks-bogies
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; bogie families, customization UI, train state, Flywheel regressions, Steam 'n' Rails boundary e tests catalogados para 1.0.8.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🚂 **ESCOPO CANÔNICO.** Runtime físico 1.0.8. Blocks & Bogies amplia os bogies/rodados configuráveis dos trens Create; não substitui o sistema de tracks, schedules ou física ferroviária do Create.

## 1. Authority
Create permanece owner de train assembly, movement, schedule e track interaction. Blocks & Bogies owns seus modelos/configurações de bogie e UI de personalização. Steam 'n' Rails pode coexistir como addon ferroviário separado.

## 2. Famílias publicadas
O projeto publica famílias Walschaerts Drivers, Piston-Only, Pistonless, Rodless e Scotch Yoke com variantes Large/Extra Large de múltiplos eixos, além de Standard Small e Trailing Small. A quantidade exata deve ser lida do catálogo da build quando necessária; esta ficha preserva as famílias e ranges publicamente documentados sem converter marketing em registry count.

## 3. Customization UI
A personalização é feita por interação direta com o bogie; o upstream documenta crouch + right-click com mão vazia para abrir a UI. Selection/render são client-facing, mas o estilo aplicado ao bogie/train deve persistir e sincronizar server-side.

## 4. Assembly e persistência
Style, orientation e axle configuration precisam sobreviver a train assembly/disassembly, chunk unload, restart e schematic workflows quando suportados. Trocar visual não deve reconstruir o train ou alterar inventário/schedule.

## 5. Flywheel e 1.0.8
A build exata corrige duas regressões em bogies montados usando Flywheel backend: iluminação do XL10S-Gearless e rotação do L6S-Gearless. Esses modelos são smoke gates obrigatórios da 1.0.8, especialmente em movimento e sob diferentes condições de luz.

## 6. Steam 'n' Rails
Steam 'n' Rails 0.3.0-beta.2 está fisicamente presente, mas não é hard dependency da release. A coexistência amplia o catálogo de bogies/tracks; conflito deve ser demonstrado por model ID, interaction ou assembly concreto, não inferido por ambos serem ferroviários.

## 7. Client/server e multiplayer
Model animation, lighting e UI são cliente; bogie selection/state e train assembly são server-authoritative. Dois jogadores editando o mesmo bogie precisam convergir para um único state sem visual stale.

## 8. Riscos
1. Estilo visual não persiste após restart.
2. Bogie model gira fora de fase quando montado.
3. Lighting diverge no Flywheel backend.
4. UI aplica state apenas localmente.
5. Schematic/train assembly perde variante.
6. Steam 'n' Rails e B&B registram interaction/model incompatível.
7. Grande variedade de modelos aumenta custo visual.

## 9. Matriz de testes
- [ ] Dedicated server inicia com 1.0.8 + Create 6.0.10.
- [ ] UI de bogie abre e selection persiste após relog.
- [ ] L6S-Gearless gira corretamente em train montado.
- [ ] XL10S-Gearless mantém iluminação correta com Flywheel backend.
- [ ] Assembly/disassembly preserva estilo.
- [ ] Steam 'n' Rails coexistente não altera selection indevidamente.
- [ ] Dois clientes recebem o mesmo bogie state.
Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
A release 1.0.8, as famílias de bogies, a UI e os fixes Flywheel são sustentados pelas páginas oficiais. O projeto mantém TODO de otimização de modelos/texturas; performance local não foi medida nesta auditoria.