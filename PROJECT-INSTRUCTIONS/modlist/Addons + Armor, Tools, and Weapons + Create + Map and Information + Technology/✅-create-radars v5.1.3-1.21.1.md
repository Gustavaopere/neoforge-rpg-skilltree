# Create: Radars — 5.1.3

## Propriedades vigentes — 27/11/2026

- **Mod:** Create: Radars
- **Arquivo JAR instalado (declarado):** `create_radar-5.1.3-1.21.1.jar`
- **Versão instalada declarada:** `5.1.3` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #154 (preservada).
- **Categoria CurseForge / pasta:** Addons + Armor, Tools, and Weapons + Create + Map and Information + Technology.
- **Função:** Radar, sonar e integração de detecção/visualização em Create e sublevels.
- **Atualização/Status:** `5.1.2-1.21.1` → `5.1.3-1.21.1`.
- **Estado no pack:** instalação declarada pelo usuário, sem leitura física nova.
- **Estado da pesquisa:** publicações identificadas e deltas disponíveis auditados/reutilizados; limites de evidência explicitados, sem certificação de runtime.
- **Dependências / compatibilidade / riscos:** Create 6.0.10 e Sable 2.0.5 documentados; integrações CBC/Aeronautics/Aero Radar/Radiologistics precisam de QA, mínimos novos não demonstrados. Testar detecção e contornos em sublevels conectados, datalinks, persistência e multiplayer. Sem QA.
- **Fonte:** [publicação oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9785701).
- **Procedência:** declaração expressa do usuário e distribuição oficial; não inferir hashes nem metadata do JAR novo.

## Auditoria incremental e reconciliação

5.1.3 — 24/11/2026, Release, file 9785701, Main File atual: corrige detecção por RWR e Chaff em sublevels conectados e contorno da silhueta de grandes estruturas quando outro sublevel é conectado. Outro resultado indexado de mesmo basename/file 9783360 retorna 404; não atribuir delta ou distribuição alternativa não verificável. Sem outra versão numérica intermediária confirmada. Cadeia anterior 5.0–5.1.2 preservada; versão-base 5.1.3, sufixo 1.21.1 de distribuição mantido no filename documental; metadata não relida.

## Histórico documental preservado

> O conteúdo abaixo corresponde aos snapshots e auditorias anteriores. Menções a versão física, atual, candidata, não instalada, hashes e dependências são históricas; as propriedades vigentes prevalecem.

> **Estado vigente (declaração do usuário de 22/11/2026):** `0.4.9.4-1.21.1` → **`5.1.2`**, NeoForge 1.21.1, JAR exato oficialmente distribuído `create_radar-5.1.2-1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9758334)). A instalação foi declarada, e não aferida por leitura de JAR local. Posição física original #154 preservada.

> **Atualizações auditadas:** Versões 5.0–5.1.1 já individualizadas em auditoria anterior. A série 5.0 acrescentou Sky Radar, Sonar, Jammer e controladores; em 5.1.0, Pitch Gearbox foi removido e mudou a lógica de ligações; 5.1.1 corrigiu save/links/chunks. O changelog da 5.1.2 (22/11) confirma correção de varredura invertida do radar, sem prova de outros deltas próprios.

> **Dependências:** Create; integrações Create Big Cannons, Sable/Aeronautics, Create Aero Radar e Radiologistics requerem verificação de API/config e não bloqueiam a sincronização documental.

> **Riscos e QA:** Salto major de 0.4.9.4 para 5.1.2 implica risco de perda de datalinks/contraption e mudanças de ordem de montagem. Necessário backup/staging, monitorar orientação/targeting/fire controllers, CBC, sublevels, unload/reload e multiplayer.

> **Temporalidade:** as referências antigas à versão “atual” e os hashes físicos abaixo são do snapshot anterior à declaração de 22/11/2026. Nenhum teste de runtime, saves ou multiplayer foi realizado.

---


> **Snapshot físico histórico — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#154**: JAR `create_radar-0.4.9.4-1.21.1.jar`, mod id `create_radar`, runtime `0.4.9.4-1.21.1`, SHA-1 `9726fced4bf86c9e2c0ea09b35bc39ed3ac007b6`.

## Propriedades do registro

- **Mod:** Create: Radars
- **Arquivo JAR instalado (declarado):** `create_radar-5.1.2-1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9758334)); arquivo local não relido.
- **Versão instalada declarada:** `5.1.2` (anterior `0.4.9.4-1.21.1`, Minecraft 1.21.1/NeoForge; metadata local não relida).
- **Categoria:** Tecnologia, Automação
- **Função:** Sistema Create de vigilância e controle de armamento com radar multibloco configurável, monitor multibloco e cannon controller para detectar/rastrear alvos e orientar armas.
- **Dependências:** Create; integrações Create Big Cannons, Sable/Aeronautics, Create Aero Radar e Radiologistics requerem verificação de API/config e não bloqueiam a sincronização documental.
- **Estado no pack:** versão `5.1.2` declarada instalada pelo usuário para inventário documental; compatibilidade real não testada.
- **Estado da pesquisa:** versão-alvo e filename confirmados em fonte oficial; auditoria histórica de versões intermediárias preservada, com lacunas explicitadas quando aplicável.
- **Compatibilidade/Riscos:** Salto major de 0.4.9.4 para 5.1.2 implica risco de perda de datalinks/contraption e mudanças de ordem de montagem. Necessário backup/staging, monitorar orientação/targeting/fire controllers, CBC, sublevels, unload/reload e multiplayer.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9758334
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_radar` 0.4.9.4-1.21.1 + CurseForge oficial revalidado em 20/09/2026; 0.4.9.4 continua a release mais recente para NeoForge 1.21.1.
- **Observações:** mod id `create_radar`; runtime 0.4.9.4-1.21.1; release NeoForge 1.21.1 Client & Server. 0.4.9.4 adiciona configs de tolerância para alvo móvel e corrige ferramentas quebrando sem Aeronautics; problemas conhecidos de cannon/Aeronautics permanecem.
- **Atualização/Status:** 22/11/2026 — `0.4.9.4-1.21.1` → `5.1.2`. Versões 5.0–5.1.1 já individualizadas em auditoria anterior. A série 5.0 acrescentou Sky Radar, Sonar, Jammer e controladores; em 5.1.0, Pitch Gearbox foi removido e mudou a lógica de ligações; 5.1.1 corrigiu save/links/chunks. O changelog da 5.1.2 (22/11) confirma correção de varredura invertida do radar, sem prova de outros deltas próprios.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Radars 0.4.9.4-1.21.1 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença e uso como infraestrutura de Create Aero Radar não foram convertidos em decisão curatorial.
- **Sobreposição:** Radars é provider de detecção/visualização/controle; Create Aero Radar é integração que usa esse provider. Outros sensores/logic mods podem consumir os dados, mas não são duplicatas automáticas.

# Dossiê operacional — padrão Alex's Mobs
> 📡 Versão física confirmada: `create_radar-0.4.9.4-1.21.1.jar`, mod id `create_radar`, runtime `0.4.9.4-1.21.1`, NeoForge 1.21.1. É o provider-base de **detecção, monitoramento e direção de armas**; Create Aero Radar é integração consumidora, não substituto.

## 1. Papel e authority
Create: Radars adiciona tecnologia de vigilância inspirada em sistemas reais, mas simplificada para gameplay Create. O addon controla radar, detecção, monitor e cannon-control; Create controla suas primitives mecânicas, e o mod de arma controla projectile/damage final.
## 2. Radar bearing multiblock
O radar é um multibloco configurável: sua forma influencia capacidade. Assembly/configuração precisa produzir um único state server-side; modelos/rotações do cliente não são authority da detecção.
## 3. Monitor multiblock
O monitor escalável visualiza contatos detectados. A tela é apresentação do tracking state; integração externa deve consumir os dados lógicos, não interpretar pixels/render como fonte de verdade.
## 4. Cannon controller
O cannon controller orienta autocannons/armas suportadas em direção a contatos. Target selection e orientação pertencem ao sistema Radars; disparo, munição, balística e dano permanecem sob o provider da arma.
## 5. Alvos móveis — 0.4.9.4
A 0.4.9.4 torna a condição de fogo mais permissiva para alvos móveis e adiciona config para a tolerância adicional e para a velocidade mínima em que ela passa a valer. Esses valores devem vir da config física, não ser hardcoded por scripts externos.
## 6. Aeronautics
O changelog da build reconhece Create Aeronautics como superfície ativa. Há problema conhecido em que cannons em contraptions Aeronautics podem apontar em direção aleatória; carregar o cannon é citado pelo upstream como workaround. Tratar isso como risco conhecido, não como comportamento desejado.
## 7. Fire-before-alignment
Outro problema conhecido é o cannon disparar antes de estar corretamente alinhado; o upstream cita inserir repeater entre fire-control e mount como workaround. Automação própria não deve mascarar esse risco disparando por um segundo listener.
## 8. Create Big Cannons
O upstream também registra relatos ocasionais com versões mais novas de CBC. Version drift Radars↔CBC precisa de smoke-test antes de atualizar isoladamente. Ammo consumption, projectile creation e damage continuam autoridade de CBC.
## 9. Ferramentas e dependência condicional
0.4.9.4 registra correção para pickaxes/axes quebrando quando Aeronautics não estava instalado. Isso é regression gate para carregamento condicional: compat opcional não pode tornar gameplay-base dependente de classes do mod ausente.
## 10. Sodium/render
O changelog menciona redução de problemas com Sodium, mas sem garantia universal. Renderização de radar/monitor deve ser testada separadamente da lógica de detecção; falha visual não autoriza duplicar tracking server-side.
## 11. Integração com Radiologistics
Radiologistics pode extrair coordenadas do Create: Radars para seus algoritmos. Radars continua owner do contato/coordenada detectada; Radiologistics controla o processamento lógico/remote-control que consome esses dados.
## 12. Client/server e multiplayer
Detecção, target state, autorização de controle e decisões que causam fogo precisam convergir no servidor. Monitor/render/GUI são client-facing. Com vários players, o mesmo contato não deve produzir múltiplos comandos de fire por retransmissão/retry.
## 13. Lifecycle
Validar construção/desmontagem do multiblock, chunk unload/reload, contraption assembly/disassembly, target entering/leaving range, reconnect, server restart e alterações de config.
## 14. Riscos
1. Cannon apontar direção errada em Aeronautics.
2. Fire antes de alignment completo.
3. CBC version drift.
4. Tracking duplicado por bridge externa.
5. Config de moving-target tolerance aplicada duas vezes.
6. Monitor divergir do state real.
7. Compat opcional carregar classes de mod ausente.
8. Render pipeline interferir sem alterar lógica server-side.
## 15. Matriz de testes
1. Dedicated server boot.
2. Montar radar e monitor em configuração mínima e maior.
3. Alvo parado versus móvel, incluindo threshold/tolerance da config.
4. Cannon controller com arma suportada sem double-fire.
5. Aeronautics contraption: orientação, assembly e reacquisition.
6. Regression de fire-before-alignment.
7. CBC atual do pack: ammo/projectile/owner attribution.
8. Radiologistics lendo coordenadas de radar.
9. Chunk unload/reload e server restart.
10. Cliente com stack de render atual.
## 16. Evidência
- modlist física 08/09/2026: Radars 0.4.9.4-1.21.1;
- CurseForge oficial: radar bearing multiblock, scalable monitor e cannon controller;
- changelog 0.4.9.4: moving-target tolerance/config, Sodium mitigation e fix sem Aeronautics;
- changelog 0.4.9.4: known issues de cannon/Aeronautics, alignment e CBC.
> 🔒 Boundary canônico: **Radars decide detecção/track/orientação; o provider de arma decide munição, projectile e dano**. Integrações devem consumir o mesmo contato causal, não criar um segundo firing path.

## Auditoria incremental — 18/11/2026 — 5.0 a 5.1.1

Baseline físico #154: `create_radar-0.4.9.4-1.21.1.jar`, versão 0.4.9.4-1.21.1. Identidade e categorias oficiais preservadas. A [lista de arquivos](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/all) confirma seis publicações NeoForge 1.21.1 posteriores:

| Release | Delta material |
|---|---|
| [5.0 Beta](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9318048), 25/10 | Sky Radar, Sonar, Directional Jammer, Pitch Gearbox e Advanced Proximity Fuze; compatibilidade de Pitch/Yaw com Swivel Bearings, configurações de som/retenção de alvo e limites de rotação, compass vinculado ao alvo selecionado, interferência temporária de fireworks, melhorias Ponder/API/monitor, seleção e persistência em montagem/desmontagem Sable. Atualiza compatibilidade dos addons CBC:MW, CBC:AT e CBC:MS. |
| [5.0.1 Beta](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9326137), 25/10 | Remove a possibilidade de dois datalinks entre Network Controller e Pitch Controller; corrige monitores multiblocos, links múltiplos e crash de renderização. |
| [5.0.2 Beta](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9339115), 27/10 | Ajustes de binocular para velocidade de ships Sable, obstrução, atualização de opções da rede, consistência de alinhamento, disparo prematuro, suavização da API Pitch/Yaw e correções do controller desvinculado; receitas de RWR e Directional Jammer. |
| [5.0.3 Release](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9359310), 29/10 | Corrige render do Network Block Filterer, datalinks fantasma e alinhamento de Swivel; reformula configuração do jammer e remove obstrução do binocular exceto ships Sable. Autor alerta sobre quebra de links na migração de 4.9. |
| [5.1.0 Beta](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9566545), 11/11 | Controllers passam a rotacionar mounts via shafts; **Pitch Gearbox adicionado em 5.0 é removido**. Ordem de ligação mudou; múltiplos Fire Controllers por mount, rejeição de links em orientação inválida, correção de cálculos presos, espectadores não detectados, persistência de redes em assembly/disassembly, ajustes em identificação, swivels e sublevels aninhados (sublevels conectados tratados como um ship). |
| [5.1.1 Release](https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9632878), 15/11 | Corrige travamento ao salvar, ciclo de vida dos links em chunks descarregados, flexibiliza condições de disparo e corrige recipe do Directional Jammer. |

**Gate de migração:** backup integral e staging com saves reais, especialmente vindo de <5.1.0; verificar redes/datalinks existentes, blocos removidos, reconexão e chunks unload/reload, orientação e permissões, integração Create/Sable/Aeronautics/Create Aero Radar/Radiologistics e CBC. Não presumir compatibilidade de APIs/configs anteriores. Versão instalada preservada; nenhum JAR ou save modificado. Auditoria documental individual da cadeia concluída; QA físico pendente.


## Sincronização documental — 22/11/2026 — Create: Radars 5.1.2

**Autoridade atual:** declaração do usuário de instalação de `create_radar-5.1.2-1.21.1.jar` para NeoForge 1.21.1, publicada em https://www.curseforge.com/minecraft/mc-mods/create-radars/files/9758334. O JAR top-level local, SHA e metadata da nova versão não foram lidos; posição histórica #154 preservada.

**Cadeia auditada e diferenças:** Versões 5.0–5.1.1 já individualizadas em auditoria anterior. A série 5.0 acrescentou Sky Radar, Sonar, Jammer e controladores; em 5.1.0, Pitch Gearbox foi removido e mudou a lógica de ligações; 5.1.1 corrigiu save/links/chunks. O changelog da 5.1.2 (22/11) confirma correção de varredura invertida do radar, sem prova de outros deltas próprios.

**Dependências e pendências:** Create; integrações Create Big Cannons, Sable/Aeronautics, Create Aero Radar e Radiologistics requerem verificação de API/config e não bloqueiam a sincronização documental. Dependências não sincronizadas em outros lotes aguardam reconciliação documental; isso não autoriza inferir incompatibilidade do jogo nem impede esta atualização.

**Riscos:** Salto major de 0.4.9.4 para 5.1.2 implica risco de perda de datalinks/contraption e mudanças de ordem de montagem. Necessário backup/staging, monitorar orientação/targeting/fire controllers, CBC, sublevels, unload/reload e multiplayer. Testes de servidor/cliente, backups e worldgen/multiplayer, quando aplicáveis, não foram executados.
