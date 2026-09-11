# Create Slice & Dice

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d2bf50e82a9894dade
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create Slice & Dice
- **Arquivo JAR:** `sliceanddice-4.3.3-neoforge.jar`
- **Versão 1.21.1:** 4.3.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Compat, Comida
- **Função:** Addon de automação Create para culinária/Farmer's Delight: Slicer automático, conversão de Cooking Pot recipes em heated mixing, Sprinkler de fluidos/efeitos e API/data-driven sprinkle actions.
- **Dependências:** Required atuais e presentes: Create 6.0.10 e Kotlin for Forge 5.12.0. Optional presentes: Farmer's Delight 1.3.4 e Create: Enchantment Industry 2.5.3b. Overweight Farming é optional upstream e não foi encontrado top-level. Create: Coasters Simulated 0.1.5 está presente como physics-contraption integration documentada.
- **Sobreposição:** Complementa Create/Farmer's Delight; cruza workflows com Create Central Kitchen 2.6.0, Ratatouille 1.4.0 e Create: Factory, mas não é duplicata integral. Avaliar recipes/process paths e não apenas tema culinário.
- **Compatibilidade/Riscos:** Automation cross-mod stateful. Riscos: duplicate recipe conversion, tool durability accounting, fluid/action duplication, sprinkler contraption coordinates, potion effect stacking, datapack registry drift e overlap com outros food automation addons. 4.3.3 corrige potion sprinkler e expõe SprinklerProvider @JvmStatic.
- **Observações:** A ficha antiga tratava Farmer's Delight como hard dependency; relações atuais do projeto o listam como optional, embora seja o principal ecossistema funcional. 4.3.3 é Release NeoForge 1.21.1 de 31/07/2026.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Slice & Dice 4.3.3, relações de dependência e documentação oficial de Slicer/Cooking/Sprinkler.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/slice-and-dice/files/8547315
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Create Slice & Dice 4.3.3 reconstruído: Slicer/tool tags, heated Cooking Pot mixing, Sprinkler/fluid actions, contraption physics, datapack API, optional compats, exact 4.3.3 fix, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sliceanddice-4.3.3-neoforge.jar`, mod id `sliceanddice`, versão `4.3.3`, NeoForge 1.21.1. É uma camada de **automação culinária Create** centrada no Slicer, conversão de recipes de Cooking Pot e Sprinkler data-driven.

## 1. Identidade e papel
- **Mod:** Create Slice & Dice.
- **JAR:** `sliceanddice-4.3.3-neoforge.jar`.
- **Mod id:** `sliceanddice`.
- **Versão:** `4.3.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Mixin:** `sliceanddice.mixins.json`.

## 2. Dependências reais
Relações atuais do projeto:
- **Create** — required; pack `6.0.10`.
- **Kotlin for Forge** — required; pack `5.12.0`.
- **Farmer's Delight** — optional upstream; pack `1.3.4` presente.
- **Create: Enchantment Industry** — optional upstream; pack `2.5.3b` presente.
- **Overweight Farming** — optional upstream; não encontrado top-level no snapshot atual.

A funcionalidade principal do mod é desenhada em torno de Farmer's Delight, apesar da relação técnica atual ser optional.

## 3. Authority e ownership
- **Create:** rotação, belts, mixing, contraptions e transporte base.
- **Farmer's Delight:** Cutting Board/Cooking Pot e recipes culinários originais.
- **Slice & Dice:** automação desses workflows, Slicer, Sprinkler e registros próprios.
- **Mods de alimento:** continuam authority de ingredients/foods e efeitos próprios.

Automatizar recipe não transfere ownership do output ao addon.

## 4. Slicer
O **Slicer** funciona como máquina automática de Cutting Board. Recebe ferramenta adequada e processa recipes compatíveis sem interação manual.

A seleção de recipe precisa respeitar inputs/tool corretos e produzir exatamente o mesmo conjunto lógico esperado pelo recipe source, salvo diferenças explicitamente definidas pelo addon.

## 5. Allowed tools tag
Ferramentas permitidas são configuráveis via item tag `sliceanddice:allowed_tools`. Knives/axes formam o conjunto normal publicado, mas datapacks podem ampliar essa lista.

A tag controla elegibilidade; não deve tornar a ferramenta imune a durability se o recipe/path prevê desgaste.

## 6. Belt/hopper/mechanical-arm automation
A linha do projeto suporta alimentação/retirada automática do Slicer por sistemas Create e inventários. Histórico oficial inclui suporte a hoppers/mechanical arms e consumo de tool durability em belt processing.

Regression gate: automação não pode duplicar ferramenta, ignorar durability ou consumir input duas vezes sob high-speed transport.

## 7. Automatic Cooking
Todos os recipes de Farmer's Delight que usam **Cooking Pot** são convertidos automaticamente em **heated mixing recipes** segundo a documentação.

Essa conversão precisa evitar duplicatas quando outro addon também gera mixing recipe para o mesmo output. Recipe IDs/source devem ser auditáveis por JEI/recipe manager.

## 8. Heat requirement
A linha possui configuração para requisito de heat de cooking conversions. O valor local não foi lido neste lote.

Não presumir que qualquer Mixer sem heat execute recipe apenas porque o recipe convertido existe.

## 9. Sprinkler
O Sprinkler recebe fluido via pipe e distribui efeito em pequena área abaixo. O comportamento depende do fluido/action registrado.

É um sistema server-authoritative: consumo de fluido e aplicação do efeito precisam ocorrer exatamente uma vez por tick/event previsto.

## 10. Water
Água torna a região abaixo efetivamente “wet/raining” para sistemas que consultam esse contexto. Isso pode alterar agricultura/comportamentos modded sem existir chuva global.

O scope deve permanecer na área do Sprinkler e cessar quando fornecimento/condição termina.

## 11. Lava
Lava aplica pequena quantidade de fire damage a entities abaixo. O servidor deve calcular dano/cooldown de forma determinística; particles visuais não podem multiplicar dano.

## 12. Potion fluids
Poções aplicam seus effects por curta duração a entities sob o Sprinkler. A **4.3.3 corrige especificamente potion sprinkler behaviour**, portanto potion effects são regression gate direto.

Testar amplifier/duration, friendly entities, multiple sprinklers e effect refresh para evitar stacking não intencional.

## 13. Liquid Fertilizer
O mod adiciona **Liquid Fertilizer**, que aplica bonemeal-like effect a blocks. A documentação cita uso para plantas específicas e potencial compat com outros mods.

Bonemeal application deve usar hooks do block/provider e não forçar growth em targets que recusam bonemeal.

## 14. SprinklerProvider API
O changelog 4.3.3 marca `SprinklerProvider` como **`@JvmStatic`**. É mudança de API/interoperabilidade para integrations Kotlin/Java.

Esse delta não muda por si só o comportamento visual do Sprinkler; é boundary de developer/API e deve ser testado por addons/scripts que chamem o provider.

## 15. Data-driven sprinkle actions
A documentação expõe custom sprinkler actions por datapack/registry `sliceanddice:sprinkle_action`. Isso permite novos comportamentos por fluido sem alterar o core.

Registry/data reload precisa validar IDs e não conservar action antiga após datapack removal.

## 16. Recipes custom e sequenced assembly
O projeto suporta recipes customizados por datapack, incluindo integração com workflows Create como sequenced assembly em versões/linhas documentadas.

Qualquer customização local KubeJS/datapack deve ser auditada separadamente; o presente dossiê não presume scripts ativos.

## 17. Physics contraptions
A documentação atual informa que o Sprinkler funciona em **contraptions**, inclusive physics contraptions de Create Simulated, e aplica efeito quando movido para novo block position.

O pack contém `simulatedcoasters-0.1.5.jar`. Movement/rotation e mudança rápida de posição precisam evitar aplicar action múltiplas vezes no mesmo espaço por transform desatualizado.

## 18. Create: Enchantment Industry
A relação upstream lista Enchantment Industry como optional, e o pack contém `2.5.3b`. A integração específica depende do recurso usado; não atribuir recipe/effect concreto sem documentação correspondente.

É uma integration surface real para load-order/registries, não justificativa para inventar automação adicional.

## 19. Overweight Farming
O upstream oferece compat opcional, incluindo waxing com Deployer e exposição de axe-stripping de crops em JEI. **Overweight Farming não foi encontrado top-level** na modlist atual, então essa capacidade não é path ativo deste snapshot.

## 20. Central Kitchen/Ratatouille e sobreposição
O pack contém Create Central Kitchen `2.6.0` e Ratatouille `1.4.0`. Esses mods também expandem culinária/automação Create, mas operam em recipes, machines e integrations diferentes.

A decisão sobre redundância deve comparar IDs/process paths e outputs reais; “todos automatizam comida” não é evidência suficiente para remover Slice & Dice.

## 21. Sable Beyond lineage
Sable Beyond 0.5.0 removeu um fix temporário antigo relacionado a sprinkler de Slice & Dice porque ownership havia mudado/upstream absorvido. Isso reduz risco de double-patch, mas não elimina a necessidade de testar Sprinkler em sublevels/contraptions.

## 22. Client / server
Servidor decide recipes, tool consumption, fluid consumption, growth/damage/effects e contraption action. Cliente renderiza machine/particles/tooltips/JEI.

Um cliente não pode aplicar potion/growth localmente sem confirmação funcional do servidor.

## 23. Lifecycle
Validar:
- recipe reload/datapack reload;
- tool insert/remove/break;
- belt/hopper/arm transport;
- Mixer heat changes;
- Sprinkler start/stop/fluid swap;
- contraption assembly/disassembly/movement;
- chunk unload/reload;
- save/restart;
- recipe provider update.

## 24. Riscos técnicos
1. **Duplicate converted recipe:** outro addon gera o mesmo heated mixing.
2. **Tool durability bug:** Slicer/belt não consome ou consome duas vezes.
3. **Input/output dupe:** transport concorrente extrai durante process commit.
4. **Potion behavior regression:** fix 4.3.3 retorna.
5. **Fluid double-consumption/application:** Sprinkler action roda duas vezes.
6. **Contraption transform stale:** moving Sprinkler afeta posição errada.
7. **Datapack registry drift:** custom action antiga persiste.
8. **Growth incompatibility:** bonemeal-like action força provider não suportado.
9. **Recipe overlap:** Central Kitchen/Ratatouille introduzem path indistinguível no balanceamento.

## 25. Matriz de testes
- [ ] Dedicated server inicia com Slice & Dice 4.3.3 + Create 6.0.10 + KFF 5.12.0.
- [ ] Farmer's Delight presente: Slicer executa Cutting Board recipe equivalente.
- [ ] Ferramenta fora de `allowed_tools` é rejeitada.
- [ ] Tool durability é consumida corretamente em automação/belt.
- [ ] Hopper/Mechanical Arm não duplicam input/output.
- [ ] Cooking Pot recipe vira heated mixing uma única vez.
- [ ] Heat requirement local é respeitado.
- [ ] Water Sprinkler aplica wet/rain context apenas na área prevista.
- [ ] Lava Sprinkler aplica dano uma vez por evento previsto.
- [ ] Potion Sprinkler aplica duration/amplifier corretos — regression 4.3.3.
- [ ] Liquid Fertilizer respeita bonemeal hooks.
- [ ] Datapack custom `sprinkle_action` carrega/remove sem cache stale.
- [ ] Sprinkler montado em physics contraption acompanha posição sem double-apply.
- [ ] Save/restart preserva machine state sem recipe duplication.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 26. Evidências e limites
- Modlist física atual: Slice & Dice 4.3.3, Create 6.0.10, KFF 5.12.0, Farmer's Delight 1.3.4, Enchantment Industry 2.5.3b e Create Simulated presente.
- CurseForge 4.3.3: `SprinklerProvider @JvmStatic` e potion Sprinkler fix.
- CurseForge relações: Create/KFF required; Farmer's Delight/Enchantment Industry/Overweight Farming optional.
- Documentação oficial: Slicer, automatic Cooking Pot→heated mixing, Sprinkler effects, tags e compats.
- **Limite:** configs locais, recipes KubeJS/datapacks e recipe conflicts efetivos não foram inspecionados neste lote.
