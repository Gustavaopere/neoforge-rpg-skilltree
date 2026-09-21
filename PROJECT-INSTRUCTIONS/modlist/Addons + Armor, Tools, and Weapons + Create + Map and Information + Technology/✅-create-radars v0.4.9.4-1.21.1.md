# Create: Radars

## Propriedades do registro

- **Mod:** Create: Radars
- **Arquivo JAR:** create_radar-0.4.9.4-1.21.1.jar
- **Versão 1.21.1:** 0.4.9.4-1.21.1
- **Categoria:** Tecnologia, Automação
- **Função:** Sistema Create de vigilância e controle de armamento com radar multibloco configurável, monitor multibloco e cannon controller para detectar/rastrear alvos e orientar armas.
- **Dependências:** Create obrigatório. Integrações concretas do stack incluem Create Big Cannons e Create Aeronautics; Create Aero Radar consome este provider. Pack físico usa NeoForge 21.1.250 + Create 6.0.10.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** 0.4.9.4 melhora firing tolerance para alvos móveis, mas o próprio changelog mantém riscos conhecidos: cannon em Aeronautics pode apontar direção aleatória, pode disparar antes do alinhamento e há relatos com versões novas de CBC. Testar targeting/owner/contraption antes de automatizar fogo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-radars
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_radar` 0.4.9.4-1.21.1 + CurseForge oficial revalidado em 20/09/2026; 0.4.9.4 continua a release mais recente para NeoForge 1.21.1.
- **Observações:** mod id `create_radar`; runtime 0.4.9.4-1.21.1; release NeoForge 1.21.1 Client & Server. 0.4.9.4 adiciona configs de tolerância para alvo móvel e corrige ferramentas quebrando sem Aeronautics; problemas conhecidos de cannon/Aeronautics permanecem.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #153: create_radar-0.4.9.4-1.21.1.jar / 0.4.9.4-1.21.1 confirmados como latest NeoForge 1.21.1; radar/monitor/cannon-control, moving-target tolerance e known issues Aeronautics/CBC permanecem atuais.
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
