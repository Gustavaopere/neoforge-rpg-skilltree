<!-- Guia temático canônico versionado no GitHub | reconciliado contra modlist.txt 2026-09-06 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **RECONCILIADO COM A MODLIST ATUAL — 2026-09-06:** a referência corrente possui **607 entradas top-level incluindo NeoForge**. O recorte Gameplay/Sistemas contém **342 JARs atuais** e a cobertura descritiva fecha em **342/342**. Presença, filename, mod ID, runtime name e runtime version são fixados por [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md). O snapshot remove Medieval Buildings: The Nether Edition, incorpora 22 módulos novos e fecha 21 lacunas documentais históricas.

## Stack atual de sobrevivência

- **Cold Sweat 2.4.2:** temperatura corporal.
- **Create: Cold Sweat 1.1.2:** bridge Create↔Cold Sweat, não segundo sistema térmico.
- **Ecliptic Seasons 0.15.0-rc-3:** clima/estações; não substitui temperatura corporal.
- **Thirst Was Reclaimed 3.0.4:** sede/hidratação.
- **Thirst Was Fixed 2.1.6:** compatibilidade/fixes do sistema de sede.
- **Nutritional Balance 1.21.1-7.0.3:** nutrição quando a métrica requerida puder ser lida com segurança.
- **AnimalHusbandry 0.4.1:** criação e manejo de animais com genética, saúde, reprodução, linhagem e gestação.

> Este guia reúne os **mods de gameplay e sistemas gerais** do pack que não pertencem principalmente aos eixos de Magia ou Tecnologia. Entram aqui combate, movimento, progressão, sobrevivência, alimentação, fauna, bosses, exploração, estruturas, colônias e utilidades que alteram diretamente a forma de jogar. Para que os três guias cubram integralmente a modlist top-level, o conjunto também mantém cobertura técnica para bibliotecas/APIs, interface, visual, áudio e performance que não pertençam principalmente aos outros dois guias. Esses componentes são descritos pelo papel técnico real e não tratados como sistemas de gameplay equivalentes aos capítulos principais.

## Bloqueios atuais de startup por mod ID duplicado

A decisão de provider foi fechada em 2026-09-06, mas a `modlist.txt` atual ainda contém os quatro JARs conflitantes; portanto o bloqueio físico de startup permanece até a remoção dos dois rejeitados.

- `alexscaves`: **manter Alex's Caves Continued 1.0.9** (`alexscaves-1.0.9-neoforge+1.21.1.jar`) e **retirar Alex's Caves 2.0.2** (`alexscaves-2.0.2.jar`).
- `alexsmobs`: **manter Alex's Mobs Continued 2.1.9** (`alexsmobs-2.1.9-neoforge+1.21.1.jar`) e **retirar Alex's Mobs 1.22.9** (`alexsmobs-1.22.9.jar`).
- **BLOQUEIO DE STARTUP:** NeoForge exige mod IDs únicos; enquanto os JARs rejeitados continuarem na pasta `mods`, o carregamento falha durante discovery, antes de `ModList.isLoaded(...)` ou de qualquer adapter/runtime do modpack. Depois da remoção física, a modlist e o Notion devem ser reconciliados novamente para registrar o estado efetivamente instalado.
