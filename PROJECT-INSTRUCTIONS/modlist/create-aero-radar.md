# Create Aero Radar

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81748fdefef0a9432c88
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aero Radar
- **Arquivo JAR:** `create_aero_radar-0.1.1-1.21.1.jar`
- **Versão 1.21.1:** 0.1.1-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação, Compat
- **Função:** Integra Create Aeronautics com Create: Radars e Create Big Cannons para orientação e controle de armamentos em contraptions físicas, incluindo dados de radar, mira e convergência.
- **Dependências:** Publicação 0.1.1 requer Create Aeronautics 1.2.1+ e Create: Radars 0.4.9.2. Runtime físico: Aeronautics 1.3.2 + Create: Radars 0.4.9.4 + Create Big Cannons 5.11.7 como stack funcional de armamento.
- **Sobreposição:** Não duplica Create: Radars; consome dados/integração de radar para armamentos em veículos físicos.
- **Compatibilidade/Riscos:** Forte coupling Aeronautics/Radars/CBC. Riscos: coordinate-frame mismatch em sublevels, stale target, barrel/round detection drift, yaw jitter, redstone vs auto-aim e API drift. CurseForge chama 0.1.1 de Release; Modrinth a classifica Alpha.
- **Observações:** JAR físico `create_aero_radar-0.1.1-1.21.1.jar`, mod id `create_aero_radar`, runtime 0.1.1-1.21.1. 0.1.1 adiciona return-to-origin, left/right redstone yaw, yaw deadzone e detecção automática de barrel length/round type.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create Aero Radars 0.1.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aero-radars
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; radar guidance, swivel/yaw control, gun convergence, barrel/round detection, authority e Sable risks catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

> 📡 **ESCOPO CANÔNICO.** Runtime físico: `create_aero_radar-0.1.1-1.21.1.jar`, mod id `create_aero_radar`, versão `0.1.1-1.21.1`. A bridge conecta Create Aeronautics, Create: Radars e armamento Create Big Cannons para sistemas de defesa/mira em contraptions físicas.

## 1. Papel no stack
Create Aeronautics continua owner da física/Swivel Bearings; Create: Radars continua owner de detecção/radar; Create Big Cannons continua owner das armas/projéteis. Aero Radar usa esses providers para transformar tracking em controle de bearing/mira, sem criar um quarto sistema físico independente.

## 2. Dependências e runtime físico
A publicação 0.1.1 requer Create Aeronautics 1.2.1+ e Create: Radars 0.4.9.2. O pack possui Aeronautics 1.3.2 e Radars 0.4.9.4, ambos acima desses baselines. Create Big Cannons 5.11.7 está fisicamente presente e é a superfície funcional de armamento usada pelo projeto.

## 3. Mira e latência
O projeto descreve mira melhor que a integração comum de Create: Radars, com **1 tick de delay** em vez de 5 no caminho de aiming. Isso é característica upstream, não benchmark local de latência do servidor.
Também fornece guidance radar e gun convergence para compor baterias antiaéreas/self-propelled AA.

## 4. Mudanças exatas da 0.1.1
O changelog adiciona:
- retorno à posição original;
- giro esquerdo/direito via redstone no yaw controller;
- deadzone de yaw;
- detecção automática de barrel length e round type para melhorar cálculos.

Esses recursos tornam geometry/config do canhão parte do cálculo de targeting e devem ser testados com a build física CBC 5.11.7.

## 5. Authority e segurança
Radar target não autoriza dano por si. O firing/damage continua resolvido pelo provider do canhão. Controllers/redstone podem orientar a arma, mas integrações próprias não devem conceder hit/kill credit apenas por target lock ou bearing rotation.

## 6. Contraptions, Sable e multiplayer
Em navios/sublevels móveis, posição/orientação do radar, swivel e barrel precisam usar o frame correto. Testar movimento simultâneo do veículo e turret, chunk unload, reconnect e dois operadores alterando redstone/control state.

## 7. Canal de publicação
CurseForge publica 0.1.1 como Release; Modrinth a rotula Alpha. Esta ficha preserva a divergência de canal: build/versão são confirmadas, mas o rótulo de maturidade não é tratado como unanimidade entre plataformas.

## 8. Riscos
1. Coordinate-frame mismatch em sublevels móveis.
2. Radar lock stale após target despawn/unload.
3. Barrel length/round detection incompatível com munição addon.
4. Bearing oscila em torno da yaw deadzone.
5. Redstone left/right compete com auto-aim.
6. API drift Aeronautics/Radars/CBC.
7. Targeting client-visible diverge da resolução server-side.

## 9. Matriz de testes
- [ ] Dedicated server inicia com Aero Radar 0.1.1, Aeronautics 1.3.2, Radars 0.4.9.4 e CBC 5.11.7.
- [ ] Guidance mantém alvo com veículo parado e em movimento.
- [ ] Gun convergence converge múltiplos canhões no mesmo alvo.
- [ ] Return-to-origin funciona após perda do alvo.
- [ ] Redstone left/right e yaw deadzone respondem sem jitter.
- [ ] Barrel length/round type detectados corretamente em ao menos dois canhões.
- [ ] Unload/reload não deixa target ou bearing state stale.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
Modrinth/CurseForge oficiais confirmam a build 0.1.1, requisitos, escopo anti-air, guidance/convergence e changelog exato. Internals dos cálculos balísticos não foram pinados ao JAR; mods próprios não devem copiar fórmulas presumidas.
