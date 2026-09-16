# Cold Sweat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `ColdSweat-2.4.3.1.jar`
- **Versão 1.21.1:** `2.4.3.1`
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 2.4.2 para 2.4.3.1; dossiê reconciliado aos deltas 2.4.3 + hotfix 2.4.3.1. Certificação pendente de QC/re-fetch final.
- **Categoria:** Clima
- **Compatibilidade/Riscos:** Authority de temperatura corporal; evitar double-application por outros providers. Riscos em insulation, block temperature emitters, dimension/death lifecycle, Create/Sable moving objects, KubeJS configs e integrations. 2.4.3 altera block-temperature internals e corrige Create tanks/pipes, Sable contraptions, build-limit e outros casos; 2.4.3.1 corrige crash sem Create.
- **Decisão:** Manter
- **Dependências:** Provider térmico principal. Create: Cold Sweat e Thirst Was Reclaimed permanecem integrations relevantes; a modlist física atual contém Create 6.0.10 e Thirst Was Reclaimed 3.0.5. Suporte upstream antigo ao external heater do Immersive Engineering não implica integração ativa se IE estiver ausente.
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cold-sweat
- **Função:** Sistema principal de temperatura corporal do pack: calcula conforto térmico a partir de ambiente/player, insulation e dispositivos/itens como Hearth, Boiler, Icebox, Waterskin e Thermometer.
- **Histórico da decisão:** Mantido formalmente em 22/08/2026 como sistema principal de temperatura corporal. Compat antigo Cold Sweat and Aeronautics foi removido; Cold Sweat permanece provider térmico.
- **Observações:** mod id `cold_sweat`; runtime 2.4.3.1. O JAR 2.4.2 anteriormente auditado embarcava Sable Companion 1.4.2; o conteúdo jar-in-jar interno do **novo** JAR 2.4.3.1 não foi re-inspecionado nesta passagem, portanto essa versão interna não é promovida como fato atual.
- **Procedência:** modlist física de 16/09/2026 + CurseForge/changelog oficial Cold Sweat 2.4.3 e 2.4.3.1 + dossiê Notion anterior. Nenhum runtime test executado.
- **Sobreposição:** Cold Sweat controla temperatura corporal. Estações, clima visual, chuva/neve, roupas e fontes ambientais podem alimentar/integrar o provider, mas não devem liquidar segunda temperatura corporal concorrente.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> 🌡️ Runtime físico: `ColdSweat-2.4.3.1.jar`, mod id `cold_sweat`, runtime `2.4.3.1`, NeoForge 1.21.1. Cold Sweat continua **provider principal de temperatura corporal**, decisão `Manter`.

## 1. Papel e authority térmica
Cold Sweat calcula estado térmico a partir do ambiente e gameplay. Outros mods podem fornecer clima, estações, chuva, roupas ou calor/frio, mas temperatura corporal final não deve ser liquidada por providers paralelos sem bridge explícita.

## 2. Fontes ambientais e insulation
Biomas/clima, blocos e condições ambientais influenciam o provider. Integrações próprias devem observar configs/estado do Cold Sweat em vez de duplicar fórmula externa. Insulation altera resposta térmica; não deve ser automaticamente convertida em armor toughness, resistência elemental ou stamina. KubeJS insulator config permanece regression surface histórica da 2.4.2.

## 3. Hearth, Boiler, Icebox, Waterskin e Thermometer
Hearth/Boiler/Icebox fazem parte da infraestrutura térmica; Waterskin/Thermometer pertencem ao ecossistema de survival/feedback. UI/tooltip não substitui state do servidor. O suporte 2.4.2 ao external heater do Immersive Engineering é capacidade upstream; não tratá-lo como path ativo sem IE físico.

## 4. Create/Sable/Aeronautics
A 2.4.2 corrigiu emissão térmica em objetos Sable/Create: Aeronautics. A 2.4.3 volta a corrigir block temperatures em Sable contraptions e também casos Create: tanks/pipes com max temperature ilimitada e Hearth top-half quebrando em Create train. Fontes móveis continuam regression gate. A versão jar-in-jar Sable do antigo 2.4.2 é apenas evidência histórica; o novo host não foi aberto internamente nesta auditoria.

## 5. Sede
A linha recente usa **Thirst Was Reclaimed** como alvo de compat, não o antigo Thirst Was Taken. O pack físico agora usa Thirst Was Reclaimed `1.21.1-3.0.5`. Temperatura e thirst continuam authorities separadas.

## 6. Configuração, datapacks e lifecycle
Config do servidor é authority para gameplay. Ajustes de biome/insulation/emitter devem ser versionados com o pack. Death/respawn/dimension transitions permanecem gates para evitar cache térmico stale; cada player precisa de state próprio em multiplayer.

## 7. Atualização instalada — 2.4.3
O changelog oficial 2.4.3 registra:
- horários configuráveis de hottest/coldest time em `world.toml`;
- remoção de compat built-in com Aquamirae e Boatload;
- melhoria de performance quando block temperatures são consultadas no mesmo tick;
- fixes de advancements em 1.21, temperatura perto do build limit, emitters em Sable contraptions, Create tanks/pipes com max temperature ilimitada, Hearth top-half em Create train, crash de creative armorset, item requirements de insulation, partículas da Icebox e sincronização de filled Waterskin;
- migração técnica de campos estáticos de BlockTemp para métodos context-aware, mantendo métodos antigos deprecated/delegando.

Essa mudança de API interna aumenta risco para addons/scripts que acessavam campos diretamente.

## 8. Hotfix 2.4.3.1
A build física `2.4.3.1`, publicada em 15/09/2026, corrige **crash ao carregar Cold Sweat sem Create**. O pack possui Create 6.0.10, mas a correção continua relevante para robustez da dependência opcional e para perfis de teste.

## 9. Relação com o pack
- **Create: Cold Sweat:** bridge específica instalada no ecossistema.
- **Create 6.0.10 / Sable/Aeronautics:** moving objects/contraptions são regression surface.
- **Thirst Was Reclaimed 3.0.5:** integração de survival separada.
- **Immersive Engineering:** suporte upstream histórico pode permanecer dormente se IE estiver ausente.
- Mods de estações/clima: inputs, não second authority de body temperature.

## 10. Riscos
1. double temperature settlement;
2. insulation duplicada;
3. emitter móvel/cache stale;
4. KubeJS insulator drift;
5. death/dimension state stale;
6. thirst integration apontando provider incorreto;
7. client HUD divergente do server;
8. addon usando BlockTemp API/fields antigos de forma incompatível;
9. Create tank/pipe ou train regressions;
10. optional-dependency crash sem Create — corrigido em 2.4.3.1, mas regression gate.

## 11. Matriz de testes
- [ ] Dedicated server boot com Cold Sweat 2.4.3.1.
- [ ] Boot de perfil de teste sem Create não crasha.
- [ ] Transição biomas/altitudes/build-limit.
- [ ] Equip/unequip insulation e config reload.
- [ ] Hearth/Boiler/Icebox e Waterskin sync.
- [ ] Fonte térmica em Sable/Aeronautics/Create train.
- [ ] Create tanks/pipes respeitam limites térmicos.
- [ ] Death/respawn/dimension travel.
- [ ] Multiplayer com players em condições distintas.
- [ ] Thirst Was Reclaimed + bridge sem double effect.

**Nenhum teste foi executado nesta reauditoria documental.**

## 12. Evidências e limites
- modlist física de 16/09/2026: `ColdSweat-2.4.3.1.jar` / 2.4.3.1;
- CurseForge oficial 2.4.3 + 2.4.3.1;
- conteúdo técnico do Notion para authority, devices, insulation, Create/Sable, thirst e lifecycle preservado;
- **limite:** JarJar interno do novo 2.4.3.1 não foi re-inspecionado; não se reutiliza automaticamente a metadata Sable Companion 1.4.2 do antigo host.

> 🔥 Authority canônica: **Cold Sweat decide a temperatura corporal**. Outros sistemas ambientais devem alimentar/integrar esse provider, não criar saldo térmico concorrente.

## 13. Reauditoria física — 16/09/2026
Runtime atualizado de 2.4.2 para 2.4.3.1. Decisão `Manter` preservada. Nenhum teste térmico, Create/Sable, death/dimension, KubeJS ou multiplayer foi executado.