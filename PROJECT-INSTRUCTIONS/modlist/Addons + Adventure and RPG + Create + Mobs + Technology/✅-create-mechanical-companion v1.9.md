# Create Mechanical Companion

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#193**: JAR `createmechanicalcompanion-1.9-neoforge-1.21.1.jar`, mod id `createmechanicalcompanion`, runtime metadata **vazia**, SHA-1 `480d35a7f926c1a2b86521be1feb75710b1c838e`.

## Propriedades do registro

- **Mod:** Create Mechanical Companion
- **Arquivo JAR:** `createmechanicalcompanion-1.9-neoforge-1.21.1.jar`
- **Categoria:** Mobs, Tecnologia, RPG
- **Função:** Adiciona um companheiro mecânico equipável/modular integrado à progressão e estética do Create.
- **Dependências:** Pack físico: NeoForge 21.1.248 + Create 6.0.10 + Curios 9.5.1. Source matching 1.21.1-NeoForge usa Create 6.0.8 e Curios 9.5.1; o drift Create exige smoke-test, sem alterar a identidade física.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: duplicação de Mechanical Wolf quando UUID não resolve; perda/divergência de módulos; cooldown reset; duplicate Link; teleport inválido; damage hooks duplicados; ownership; dynamic light/animation drift; normalização indevida da runtime vazia.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-mechanical-companion
- **Procedência:** modlist física atual de 20/09/2026 — runtime version vazia preservada como authority local + CurseForge oficial revalidado em 20/09/2026, confirmando `createmechanicalcompanion-1.9-neoforge-1.21.1.jar` como latest Release NeoForge 1.21.1 de 24/04/2026 + source oficial branch 1.21.1-neoforge matching.
- **Observações:** JAR `createmechanicalcompanion-1.9-neoforge-1.21.1.jar`, mod id `createmechanicalcompanion`. A metadata runtime física está vazia. `1.9` é versão do artefato/publicação (latest Release NeoForge 1.21.1 de 24/04/2026) e será usada no filename certificado sem falsificar a metadata interna.
- **Atualização/Status:** REAUDITADO EM 20/09/2026 — artefato físico `createmechanicalcompanion-1.9-neoforge-1.21.1.jar` confirmado e CurseForge oficial confirma 1.9 como latest Release NeoForge 1.21.1. A metadata runtime física continua vazia e permanece vazia no dossiê; `1.9` é a versão do artefato/publicação usada no filename certificado do catálogo.
- **Decisão:** Sem decisão
- **Sobreposição:** Companion modular próprio; não é pet genérico substituível por tema. Curios e outros systems de pets/combat podem tocar equip/AI/damage, exigindo testes de lifecycle e ownership.
- **Data da última decisão:** 2026-09-20

# Dossiê operacional — padrão Alex's Mobs
> 🐺 **Identidade física confirmada:** `createmechanicalcompanion-1.9-neoforge-1.21.1.jar`, mod id `createmechanicalcompanion`. A coluna de versão runtime da modlist física está **vazia**; `1.9` é label do arquivo/publicação e não será normalizado como runtime. O source matching está na branch `1.21.1-neoforge` e declara a linha 1.9.
## 1. Papel e authority
Create Mechanical Companion adiciona um **Mechanical Wolf** modular ligado a um item de vínculo equipado via Curios. O addon owns entidade, módulos, summon/dismiss, inventário modular e conteúdo Illager associado; Create fornece estética/progressão material, e Curios controla o slot/equip lifecycle.
## 2. Mechanical Wolf Link
O companion é invocado pelo **Mechanical Wolf Link** equipado no slot Curios `head`. O source matching valida explicitamente esse identificador de slot e rejeita um segundo Link no mesmo handler, com aviso de duplicata.
## 3. Persistência por UUID
O source matching persiste `WolfUUID` no Custom Data do Link. A cada curio tick server-side, resolve a entity por UUID; se ausente/inválida, tenta reconstruir o wolf. Isso torna unload, death, restart e dimension change boundaries críticos contra duplicação de entity.
## 4. Dismiss ao desequipar
Quando o Link é realmente removido e substituído por ar, o source salva módulos, descarta o Mechanical Wolf e remove `WolfUUID`. Trocas de slot, morte e automações de inventory precisam produzir exatamente uma transição summon↔dismiss.
## 5. Respawn cooldown
Se o wolf morre, o Link grava `SpawnCooldown` e só volta a invocá-lo após a contagem configurada. Relog/restart não deve resetar indevidamente o cooldown nem gerar wolf adicional em paralelo.
## 6. Inventário de módulos
`WolfModules` é serializado no próprio Link e restaurado ao novo wolf. O source evita regravar quando a serialização não mudou. Item data precisa conservar módulo, count e componentes; wolf e Link não podem ficar simultaneamente como authorities divergentes do mesmo inventário.
## 7. Nome persistente
Quando o Mechanical Wolf possui custom name, o Link salva `Nametag` e o reaplica ao reconstruir a entity. Name persistence é parte do state serializado e precisa sobreviver a dismiss/resummon.
## 8. Módulos defensivos
A documentação oficial lista **Reinforced Plates** para saúde/armadura e **Netherite Plates** como upgrade com armadura e imunidade ao fogo. Valores numéricos efetivos permanecem authority da build/config; não são inventados aqui.
## 9. Módulos ofensivos
**Smelting Fangs** adicionam fire damage, **Tesla Tail** reage ao atacante e **Mounted Crossbow** usa targeting próprio para atingir o alvo. Cada hit/effect precisa ocorrer uma vez por evento e coexistir com combat mods sem duplicar damage callbacks.
## 10. Módulos de movimento
**Booster Rocket** fornece speed boost com cooldown curto; **Quantum Drive** adiciona teleport abilities. Movimento/teleport é server-authoritative e deve validar destino/chunk, não apenas animação client-side.
## 11. Módulos utilitários
A documentação permite dois utility modules e lista **Mob Radar**, **Mounted Light** e **Regenerative Casing**. Radar aplica glowing periodicamente a hostis próximos; Mounted Light tem limitações publicadas em água/folhagem; Regenerative Casing cura lentamente. Frequências/raios exatos ficam fail-closed sem config/source pin específico.
## 12. Interface e ownership
Right-click no wolf abre a interface de módulos. O projeto informa que outros jogadores não podem abrir o menu do wolf de outro dono. Em multiplayer, ownership deve ser validado no servidor antes de qualquer mutation de módulo.
## 13. Cura com wrench
Usar wrench no Mechanical Wolf o cura. A interação precisa consumir/aplicar apenas o efeito previsto e não duplicar healing por client prediction + server execution.
## 14. Illager Workshop
Desde a linha 1.4, há **Illager Workshop** com loot associado a peças do Mechanical Wolf/Create e conteúdo temático. Estrutura, loot table e respawn de loot devem seguir worldgen/server authority.
## 15. Illager Engineer e Supervisor
A documentação publica **Illager Engineer**, com combate de wrench, e **Illager Supervisor**, com Potato Cannon e munições variadas. Targeting, damage e drops precisam convergir com regras server-side do pack.
## 16. Mapas e trades
A localização de workshops pode ser obtida por mapas associados a cartographers/wandering traders segundo a documentação. Trade/map generation deve apontar para estruturas válidas e não criar marcador órfão após mudança de worldgen/datapack.
## 17. Blueprint Schematic
A linha 1.5 introduziu **Blueprint Schematic** decorativo em cinco tamanhos publicados: 1x1, 1x2, 2x1, 2x2 e 3x4. É conteúdo decorativo do addon, não um replacement do sistema real de schematics Create.
## 18. Curios no pack
O source matching usa Curios 9.5.1+1.21.1 e o pack físico possui Curios 9.5.1, alinhamento direto. Create do source era 6.0.8 e o pack usa 6.0.10; smoke-test de entity/equip hooks continua necessário.
## 19. Client/server e lifecycle
Summon, UUID resolution, module inventory, ownership, combat, teleport e cooldown são server-authoritative. Modelos, animations, tooltip e UI são client-facing. Testar equip/unequip, death, respawn, relog, dimension travel, chunk unload e server restart.
## 20. Riscos
1. Link cria dois wolves quando UUID não resolve temporariamente.
2. Wolf é descartado sem salvar módulos.
3. Link e entity divergem em `WolfModules`.
4. Respawn cooldown reseta em relog/restart.
5. Segundo Link contorna a proteção de duplicata por outro slot/handler.
6. Teleport coloca wolf em chunk/destino inválido.
7. Crossbow/Tesla/Fangs duplicam dano com outros hooks de combate.
8. Dynamic light do Mounted Light conflita com render/light providers.
9. Workshop loot/structure gera repetidamente em boundary de worldgen.
10. Outro jogador acessa module inventory sem ownership válido.
11. Runtime metadata vazia é incorretamente normalizada para 1.9.
## 21. Matriz de testes
- [ ] Dedicated server inicia com arquivo 1.9 e runtime metadata vazia preservada no catálogo.
- [ ] Equipar um Link no Curios head invoca exatamente um Mechanical Wolf.
- [ ] Segundo Link é rejeitado conforme o source.
- [ ] Unequip salva módulos e remove a entity.
- [ ] Relog/restart conserva módulos, nome e UUID sem duplicar wolf.
- [ ] Morte aplica e persiste respawn cooldown.
- [ ] Cada categoria de módulo produz apenas seu efeito esperado.
- [ ] Quantum Drive valida destino/chunk.
- [ ] UI de módulos rejeita jogador não proprietário.
- [ ] Workshop/trades/maps resolvem conteúdo válido.
- [ ] Create 6.0.10 + Curios 9.5.1 não quebram hooks da branch 1.9.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 22. Evidências e limites
A modlist física é authority da ausência de runtime version. A publicação oficial confirma o arquivo/release 1.9 e as features do companion. O source matching 1.21.1-NeoForge confirma mod line, Curios head slot, bloqueio de Link duplicado, UUID, cooldown, persistência de modules/name e summon/dismiss server-side. Valores numéricos e internals não inspecionados permanecem fail-closed.
> 🔒 **Boundary canônico:** o Link é a âncora persistente do companion. Entity, UUID e inventário modular devem representar uma única instância lógica, e a versão runtime vazia deve continuar vazia no catálogo.
