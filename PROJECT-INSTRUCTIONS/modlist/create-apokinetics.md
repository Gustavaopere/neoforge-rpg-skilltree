# Create: Apokinetics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818db39cc9864c9f4403
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Create: Apokinetics
- **Arquivo JAR:** `apokinetics-1.0.6.jar`
- **Versão 1.21.1:** 1.0.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação, Compat
- **Função:** Integra Create 6 e Apotheosis/Apothic por sockets em máquinas e 12 tipos de Machine Gems com purezas; adiciona Apokinetic Wrench, Apokinetic Table, Kinetic Pylon, Factory Locator e Factory Scanner. Machine Gems alteram speed/stress, input preservation, bonus output, force, range, heat/fuel, cold fan processing, fluid machinery, logistics, high-speed recipes/generation e breaking/contact damage.
- **Dependências:** Requer o ecossistema Create + Apotheosis/Apothic para sua função. O pack instala Create 6.0.10, Apotheosis 8.8.0, Apothic Attributes 2.10.1, Apothic Enchanting 1.6.2 e Apothic Spawners 1.4.0.
- **Sobreposição:** Diferente de Apotheotic Creation: Apokinetics altera máquinas com sockets/gems e buffs industriais; Apotheotic Creation apenas torna raridades/affixes reconhecíveis por Create Attribute Filters. Pode sobrepor outros upgrades de máquina Create, mas não é redundância automática.
- **Compatibilidade/Riscos:** Modifica máquinas Create e pode cruzar com addons que mixinam as mesmas classes. 1.0.6 endurece optional mixins contra fatal injection failures e corrige vários crashes. Precision/Yielding tiveram correção explícita contra resource loops em Mechanical Crafters; isso é superfície anti-dupe obrigatória de teste. Rotation/gem cases, Frostwork fan modes, Rupture Crushing Wheels, Mob Effect gems, enchanted Apotheosis arrows e scanner speed checks também receberam fixes em 1.0.6.
- **Observações:** Qualquer automação que combine Precision + Yielding deve ser testada como caso de segurança/dupe. A build 1.0.6 também faz o Apokinetic Wrench atuar como Create Wrench; Shift+Right-Click preserva ações normais Create em máquinas socketable.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/documentação oficiais Create: Apokinetics 1.0.6 + Create 6.0.10/Apothic stack físico + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apokinetics
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — 12 Machine Gems, Rotation, Table/Pylon/Scanner/Locator, Create 6.0.10 + Apothic stack and Precision/Yielding anti-dupe gates confirmed in global QC #29.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `apokinetics-1.0.6.jar`. Este addon transforma **gems do ecossistema Apotheosis em upgrades industriais para máquinas Create**. Não confundir com Apotheotic Creation, que é uma bridge de filtragem/Attribute Filters.

## 1. Arquitetura do sistema
Create: Apokinetics adiciona **sockets a máquinas Create**. O jogador usa o **Apokinetic Wrench** para abrir/gerenciar sockets e instala **Machine Gems** que modificam propriedades reais das máquinas.

O sistema possui progressão por **purity** e por **Rotation**, além de infraestrutura própria para carregar/aperfeiçoar gems e para traduzir estatísticas Apothic Enchanting em buffs industriais.

## 2. Pureza das Machine Gems
As gems aparecem em tiers de pureza documentados:
- Cracked
- Chipped
- Flawed
- Normal
- Flawless
- Perfect

Quanto maior a pureza, maior o potencial do efeito conforme config. Valores numéricos não devem ser copiados de exemplos sem conferir `apokinetics-common.toml` da instância.

## 3. Os 12 tipos de Machine Gems
### 1. Velocity
Aumenta **processing speed** da máquina. É upgrade direto de throughput e precisa respeitar limites de recipe/tick da máquina.

### 2. Equilibrium
Reduz **stress usage** da máquina, permitindo redes cinéticas mais eficientes.

### 3. Precision
Pode **preservar inputs** durante processamento. Por afetar consumo de matéria-prima, é uma das gems de maior risco econômico. A 1.0.6 corrigiu resource loops com Mechanical Crafters.

### 4. Yielding
Adiciona **bonus outputs**. A 1.0.6 mudou o stacking para expected bonus copies aditivas e adicionou limites mínimos/máximos configuráveis. Também participou da correção anti-loop em Mechanical Crafters.

### 5. Impact
Aumenta **mechanical force/impact** em máquinas onde o conceito é aplicável.

### 6. Extension
Aumenta **reach/range** de máquinas compatíveis.

### 7. Ignition
Afeta **heat/fuel efficiency** e máquinas/processos relacionados a calor.

### 8. Frostwork
Habilita/melhora **cold fan processing**. A 1.0.6 corrigiu prioridade sobre modos de fan concorrentes e evita bloquear fan processing comum quando não há Frostwork utilizável.

### 9. Flowing
Buff voltado a **fluid machinery**, aumentando propriedades de processamento/fluxo conforme máquinas compatíveis.

### 10. Conveyance
Melhora **logistics speed**, especialmente superfícies de transporte/manuseio.

### 11. Momentum
Relaciona-se a **high-speed belt recipes** e pode ampliar kinetic sources conforme a integração disponível. Precisa ser testada sob redes RPM altas para evitar throughput inesperado.

### 12. Rupture
Aumenta **breaking/contact damage/processing impact** em máquinas apropriadas. 1.0.6 corrigiu Rupture não aumentando corretamente a velocidade de processamento dos Crushing Wheels.

## 4. Apokinetic Wrench
A ferramenta gerencia sockets e, na 1.0.6, também funciona como **Create Wrench normal**.

A release esclarece:
- uso normal executa função Apokinetics quando aplicável;
- **Shift + Right Click** usa as ações normais do Create Wrench em máquinas socketable.

Isso evita obrigar o jogador a carregar duas ferramentas, mas altera input handling em blocos Create e deve ser testado com addons que também interceptam wrench actions.

## 5. Apokinetic Table
Bloco usado para desenvolver/aperfeiçoar gems por meio de **Rotation**. A Table é energizada por RPM/rotação Create e aumenta o estado da gem até seus limites.

Na 1.0.6 foram corrigidos:
- Table não dropando quando minerada com pickaxe;
- ausência de loot table;
- atualizações repetidas depois de atingir max Rotation.

Portanto mineração/drop e limite de progressão são casos obrigatórios de regressão.

## 6. Rotation
**Rotation** é progressão adicional aplicada às gems. Quanto mais trabalhada no Apokinetic Table, maior o benefício dentro das regras configuradas.

A 1.0.6 adiciona/garante comportamento importante com **Apotheosis Gem Cases**:
- Rotation é preservada ao armazenar gems;
- extração prioriza gems com maior Rotation;
- gems com Rotation diferente não são combinadas indevidamente na mesma stack extraída.

Isso é relevante para storage/logística: NBT/components de Rotation não podem ser descartados por automação.

## 7. Kinetic Pylon
O **Kinetic Pylon** traduz estatísticas do ambiente Apothic Enchanting em efeitos sobre uma área de máquinas.

### Eterna
Reduz **stress usage**.

### Arcana
Aumenta **stress capacity gerada** conforme a lógica do pylon.

### Quanta
Aumenta stats de gems de jogadores/máquinas próximas conforme a regra implementada.

Quando vários pylons sobrepõem área, a documentação indica que o **efeito mais forte** é considerado em vez de somar todos indiscriminadamente. Testar sobreposição e boundary.

## 8. Factory Locator
Ferramenta de diagnóstico capaz de rastrear/marcar fluxo de itens/máquinas para ajudar a encontrar gargalos ou localizar por onde determinado item está passando.

É utilitário de engenharia, não buff de máquina.

## 9. Factory Scanner
Inspeciona fábrica e relata informações como:
- RPM;
- stress;
- estado de máquinas;
- problemas de operação.

A 1.0.6 corrigiu crashes ligados a checks de velocidade de máquinas. Em packs com muitos addons Create, Scanner deve ser testado contra máquinas não-base.

## 10. Configuração
A documentação upstream descreve `config/apokinetics-common.toml` com grupos para:

### Socket system
- enable/disable;
- quantidade de sockets;
- máximo;
- comportamento de drop;
- blacklist de máquinas;
- gems aceitas.

### Machine Gem bonuses
- multiplicadores por purity;
- limites e parâmetros por stat.

### Yielding
- expected bonus copies;
- min/max de bônus.

### Frostwork / Momentum
- enable/disable de recipes especiais;
- blocos reconhecidos como cold source;
- minimum RPM e parâmetros relacionados.

### Factory Locator / Scanner
- ranges;
- limite de block entities escaneadas;
- frequência/custo de inspeção.

### Kinetic Pylon
- radius;
- rescan interval;
- máximo/limites;
- source tags reconhecidas.

### Rotation
- aplicação a Machine Gems e, conforme suporte, gems normais Apotheosis;
- bônus por level;
- custo/stress;
- máximo.

### Client integrations
- JEI/Jade/visualização quando habilitados.

Valores reais devem ser lidos da config do pack antes de balanceamento.

## 11. Correções críticas da versão 1.0.6
A release instalada corrige uma lista extensa de problemas relevantes para este pack:
1. Apokinetic Wrench funciona como Create Wrench.
2. Shift-right-click preserva ações Create em socketable machines.
3. crash de startup NeoForge por renderer duplicado do Wrench.
4. loot tables de Apokinetic Table e Kinetic Pylon.
5. Table drop com pickaxe.
6. enchanted Apotheosis arrows causando crash.
7. Mob Effect gems como **Evasion** e **Blighted** causando crash em servidor.
8. Leech Block usando cooldown incompatível de Apotheosis.
9. Factory Scanner com machine speed checks quebrados.
10. optional mixins endurecidos contra injection fatal.
11. Rupture + Crushing Wheels.
12. Yielding reequilibrado para expected bonus copies aditivas.
13. gems maxed com mesmo stat voltando a stackar.
14. Table não atualizando indefinidamente gems já max Rotation.
15. Rotation preservada em Apotheosis Gem Cases.
16. Gem Cases extraem maior Rotation primeiro.
17. não combinar Rotation diferente numa única stack.
18. prioridade correta do Frostwork.
19. fan processing comum não bloqueado sem Frostwork válido.
20. **resource loops Precision/Yielding em Mechanical Crafters corrigidos**.
21. descrições/Ponders atualizados.
22. Yielding min/max copies configuráveis.

## 12. Anti-dupe e economia
Precision + Yielding alteram conservação e criação de itens. A existência de um fix específico para **Mechanical Crafter resource loops** prova que essa superfície é sensível.

Para qualquer receita custom/perk:
- testar recipe com input único e múltiplo;
- testar container items;
- recipe que devolve parte do input;
- Mechanical Crafter;
- sequenced assembly;
- processing de addons;
- rollback/reload/server restart.

Se houver ganho líquido não intencional, bloquear integração até confirmar se é bug de recipe ou addon.

## 13. Dependências operacionais do pack
### Create
Pack atual: **Create 6.0.10**. É o provider de máquinas, stress/RPM e Wrench semantics.

### Apotheosis / Apothic
Pack atual:
- Apotheosis 8.8.0;
- Apothic Attributes 2.10.1;
- Apothic Enchanting 1.6.2;
- Apothic Spawners 1.4.0.

Apokinetics consome conceitos/gems/stats desse ecossistema; não registra uma segunda progressão de loot RPG independente.

## 14. Diferença para Apotheotic Creation
**Apokinetics:** socket + Machine Gems + buffs industriais + Table/Pylon/Scanner/Locator.

**Apotheotic Creation:** faz raridades/affixes do Apotheosis serem reconhecidos por **Create Attribute Filters** e, por consequência, usados em rotas logísticas.

Eles são complementares e não devem ser marcados como duplicados.

## 15. Sobreposição com outros Create addons
Qualquer addon que altere:
- stress;
- RPM;
- fan processing;
- Crushing Wheels;
- Mechanical Crafters;
- belts/logistics;
- fluid machinery;
- block breaking;

pode tocar a mesma superfície. A própria release 1.0.6 diz que compat com addons Create continuaria sendo expandida. Portanto não presumir cobertura universal.

## 16. Matriz de validação
1. Abrir/remover socket em máquinas base Create.
2. Confirmar Wrench normal e Shift+Right-Click.
3. Instalar cada uma das **12 Machine Gems** em pelo menos uma máquina suportada.
4. Repetir em várias purezas.
5. Precision: medir consumo real.
6. Yielding: medir expected bonus copies e limites config.
7. **Precision + Yielding + Mechanical Crafter:** teste anti-loop obrigatório.
8. Rupture + Crushing Wheels.
9. Frostwork com recipe válido, inválido e overlap com outro fan mode.
10. Momentum em RPM baixo/alto.
11. Flowing em máquinas de fluidos.
12. Extension/range boundary.
13. Equilibrium/stress network.
14. Velocity com throughput e recipe tick.
15. Apokinetic Table: charge por Rotation, max e drop.
16. Gem Case: preservation, sort por Rotation e stacks diferentes.
17. Kinetic Pylon: Eterna/Arcana/Quanta, radius e overlap.
18. Factory Locator em fábrica real.
19. Factory Scanner com máquinas Create base + addons.
20. Mob Effect gems no dedicated server.
21. enchanted Apotheosis arrows.
22. quebrar Table/Pylon e conferir loot.
23. chunk unload/reload e server restart; sockets/gems persistem.
24. recipe reload; nenhum dupe/loss.

## 17. Regras para outros chats
- Não aplicar buff genérico a máquinas sem conferir se Apokinetics já cobre o stat.
- Precision/Yielding são superfícies de economia/dupe; qualquer perk que também preserve inputs ou aumente outputs deve deduplicar.
- Rotation é dado persistente da gem; storage/copy não pode apagá-la.
- Addon Create não listado como compat não deve ser declarado suportado sem teste/source.
- Config real é authority para valores numéricos.

## 18. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [CurseForge — Create: Apokinetics](https://www.curseforge.com/minecraft/mc-mods/apokinetics) e release 1.0.6, além da documentação/config upstream.

**Confiança:** alta para lista de gems, componentes, changelog 1.0.6 e arquitetura. Valores/configs efetivos desta instância devem ser lidos antes de balanceamento ou implementação.
