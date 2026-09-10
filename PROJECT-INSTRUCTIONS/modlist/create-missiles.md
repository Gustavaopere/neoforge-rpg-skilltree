# Create Missiles

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b1b7f0ded188015e63
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Missiles
- **Arquivo JAR:** `createmissiles-1.0.3+neoforge-1.21.1.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, RPG, Automação
- **Função:** Adiciona mísseis físicos modulares com launch pads, controle/navegação, diferentes chassis, thrusters, warheads, drones e infraestrutura associada.
- **Dependências:** Create obrigatório; source matching 1.0.3 usa Create 6.0.10, exatamente a linha física do pack. JEI/Ponder são superfícies de apresentação. Create Big Cannons 5.11.7 é overlap bélico concreto, não dependência.
- **Sobreposição:** Overlap com Create Big Cannons é bélico/destrutivo, não equivalência: CBC fornece cannons/artilharia; Missiles fornece assemblies propulsados/guiados. Aeronautics/Sable são coexistência a testar, sem integração direta presumida.
- **Compatibilidade/Riscos:** Riscos: launch race; item/assembly duplication; target client-side inválido; missile dupe/loss em unloaded chunks/restart; double impact; async world mutation race; claims bypass; drone/structure loot duplication; dedicated-server regression; alto custo combinado com CBC.
- **Observações:** JAR/mod id/runtime 1.0.3 e source matching branch dev/neoforge-1.21.1 confirmados. A release 1.0.3 corrige suporte a dedicated server; o projeto publica mais de 30 peças, Launch Pad, drones, travel em chunks descarregados e explosões assíncronas.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/documentação oficiais 1.0.3 + source oficial Woukie/create-missiles branch dev/neoforge-1.21.1 matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-missiles
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.3 com missiles modulares, Launch Pad/panels, guidance, unloaded-chunk travel, drones, async explosions, structures/upgrade cores e dedicated-server fix catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🚀 **Identidade física e source matching confirmados:** `createmissiles-1.0.3+neoforge-1.21.1.jar`, mod id `createmissiles`, runtime `1.0.3`. A branch oficial `dev/neoforge-1.21.1` declara exatamente 1.0.3 e Create 6.0.10.

## 1. Papel e authority
Create Missiles adiciona mísseis fisicamente simulados e modulares integrados ao Create. O addon owns missile entities/assemblies, guidance, parts, launch infrastructure, warheads, drones e sua destruição; Create continua authority de componentes/processos cinéticos usados na fabricação.

## 2. Arquitetura modular
O projeto publica mais de 30 peças intercambiáveis. Missiles são compostos por categorias como **Warheads, Chassis e Thrusters**. Stats efetivos pertencem às peças/assembly da build; não devem ser derivados apenas do modelo ou tooltip client-side.

## 3. Launch Pad multiblock
O fluxo oficial usa um **Launch Pad** com Control Panel, Assembly Panel e Navigation Panel. Formação/desformação, inventory/assembly state e target configurado precisam permanecer consistentes após chunk unload, restart e alteração parcial da estrutura.

## 4. Assembly Panel
O Assembly Panel participa da montagem/customização do míssil. Inserção/remoção de peças deve conservar itens e impedir que a mesma peça exista simultaneamente no inventory e no assembly final.

## 5. Navigation Panel
O Navigation Panel permite definir alvo usando mapa e parâmetros de voo, incluindo burn duration. Coordenadas enviadas pelo cliente precisam ser validadas no servidor e persistidas como parte do launch state correto.

## 6. Control Panel
O Control Panel dispara o assembly preparado. Um comando de launch deve ser idempotente: uma interação válida gera um único míssil e consome/transiciona o assembly uma única vez.

## 7. Ballistic/physical simulation
A descrição oficial apresenta física balística desenvolvida para o projeto. Posição, velocidade, orientação, combustível/propulsão e colisão precisam ser server-authoritative; interpolation cliente não pode determinar impacto real.

## 8. Unloaded chunks
O projeto declara que missiles e drones podem viajar por chunks descarregados. Isso transforma ticketing/simulação offline ou carregamento transitório em boundary crítico: trajetória não pode duplicar entity, saltar impacto ou perder target após crossing de chunk.

## 9. Warheads
Warheads definem efeitos do impacto. A lista e potência exatas ficam sob registry/config da build. Explosion/destruction deve ocorrer uma única vez mesmo se entity cruza chunk, salva no tick de impacto ou é processada por dois observers.

## 10. Explosões assíncronas
A documentação oficial informa processamento de explosões de forma assíncrona/multithreaded e destruição que considera resistência/blocagem. Qualquer cálculo fora da main thread deve aplicar mudanças de mundo por mecanismo seguro e impedir race com unload, claims, bloco já removido ou segundo impacto.

## 11. Chassis e Thrusters
Chassis e thrusters alteram características do míssil. Combinações inválidas devem ser rejeitadas conforme regras da build; tooltip/JEI de stats é presentation, enquanto launch validation fica no servidor.

## 12. Upgrade Cores e Bunkers
Upgrade Cores são encontrados em **Bunkers** segundo a documentação e aumentam capacidades de missiles. Worldgen/loot ownership permanece no servidor; structure/loot generation não pode repetir recompensa por reload de chunk.

## 13. Depots
O projeto também publica depots com warhead assemblies. Conteúdo estrutural/loot deve seguir a versão instalada; esta ficha não inventa frequência, biome filters ou loot weights não pinados.

## 14. Drones
Drones podem voar até uma coordenada e produzir mapa da área. Target resolution, chunk traversal e item/map output precisam ser conservativos, especialmente se o drone é removido, descarregado ou destruído durante a missão.

## 15. JEI e Ponder
JEI e Ponder apresentam recipes, parts e informações de stats. O pack possui JEI 19.53.0.426. Essas interfaces ajudam descoberta, mas Recipe Manager/assembly state são authority. Viewer desatualizado após reload não altera recipe real.

## 16. Clonagem com paper
A documentação publica mecanismo para clonar assemblies usando paper. A operação deve copiar configuração prevista sem duplicar itens físicos que deveriam continuar consumíveis; clone de configuração e clone de inventory são conceitos distintos.

## 17. Dedicated server — delta 1.0.3
A página da 1.0.3 registra correção de suporte a dedicated server. Por isso startup headless, launch, travel e explosion sem classes client-only são regression gates centrais dessa build.

## 18. Create Big Cannons
Create Big Cannons 5.11.7 está instalado. O overlap é bélico/destrutivo, não funcionalmente idêntico: CBC fornece artilharia/cannons; Create Missiles fornece assemblies guiados/propulsados. O risco conjunto é performance/destruction balance, não duplicidade automática.

## 19. Aeronautics/Sable
O pack contém Aeronautics 1.3.2 e Sable 2.0.5. A coexistência é relevante porque vessels e missiles podem compartilhar espaço físico/chunks, mas nenhuma integração direta é presumida sem hook/source específico. Collision/impact em contraptions físicas é um teste de interoperabilidade, não feature confirmada.

## 20. Multiplayer e permissions
Launch/targeting/explosion são ações de alto impacto em servidor. Dois jogadores operando o mesmo pad não podem disparar duas vezes o mesmo assembly. Mods de claims/proteção continuam authority de permissões; missile logic não deve bypassá-los quando houver hooks compatíveis.

## 21. Lifecycle
Testar assembly parcial, launch, chunk crossing, dimension/target edge cases, server save/restart com missile in-flight, impact no limite de chunk, drone mission, structure generation e `/reload` de recipes/tags.

## 22. Riscos
1. Launch race cria dois missiles para um assembly.
2. Peças são consumidas e preservadas simultaneamente.
3. Target client-side não é validado pelo servidor.
4. Missile duplica/desaparece ao cruzar chunk descarregado.
5. Restart com entity in-flight reaplica launch ou perde state.
6. Explosion assíncrona modifica world state stale.
7. Impact é liquidado duas vezes.
8. Destruição ignora proteção/claim por ausência de integração.
9. Drone produz mapa/recompensa duas vezes.
10. Bunker/depot loot duplica após regeneration/reload.
11. Create/JEI API drift quebra assembly viewer ou recipes.
12. Dedicated-server regression reaparece.
13. Conjunto CBC + missiles gera spike severo de MSPT/destruction queue.

## 23. Matriz de testes
- [ ] Dedicated server inicia com Missiles 1.0.3 + Create 6.0.10.
- [ ] Launch Pad forma/desforma preservando inventory e target.
- [ ] Um launch gera exatamente um missile.
- [ ] Assembly custom conserva cada part uma vez.
- [ ] Navigation Panel valida target server-side.
- [ ] Missile cruza chunks descarregados sem dupe/loss.
- [ ] Save/restart com missile em voo preserva state corretamente.
- [ ] Impact/explosion ocorre uma única vez.
- [ ] Explosion async não causa concurrent world mutation/crash.
- [ ] Drone completa missão e gera um único map output.
- [ ] Bunker/depot worldgen não duplica loot indevidamente.
- [ ] JEI/Ponder refletem recipe/part stats sem controlar gameplay.
- [ ] Coexistência com CBC/Aeronautics/Sable não produz crash ou double-impact.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 24. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.0.3. O source matching confirma MC 1.21.1, NeoForge, Create 6.0.10 e JEI dev. A documentação oficial confirma missiles modulares, launch pad/panels, warheads/chassis/thrusters, upgrade cores, bunkers/depots, drones, travel por unloaded chunks, JEI/Ponder e explosões assíncronas. Valores de dano, alcance, recipes e algoritmos não inspecionados permanecem fail-closed.

> 🔒 **Boundary canônico:** Create Missiles controla assembly, simulação e impacto; o servidor controla target, world mutation e conservation. Travessia de chunk e explosão assíncrona jamais podem produzir replay ou dupla liquidação.
