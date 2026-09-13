# Create: Pattern Schematics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f2b1ffd12ea1d6de82
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Pattern Schematics
- **Arquivo JAR:** `create_pattern_schematics-2.0.10.jar`
- **Versão 1.21.1:** 2.0.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Automação, Tecnologia
- **Função:** Extensão de schematics do Create que permite gerar padrões repetidos a partir de schematics existentes e usar Pattern Schematics em Schematicannon ou em contraptions como trains/gantry carriages para construção repetitiva.
- **Dependências:** Create. Release 2.0.10 é NeoForge 1.21.1, Client & Server; changelog declara fix para compatibilidade com Create 6.0.7. O pack usa Create 6.0.10.
- **Sobreposição:** Relaciona-se ao Schematicannon e a outros construction/schematic tools, mas sua função específica é gerar/repetir padrões e construir iterações em contraptions. Não é substituto geral do sistema de schematics do Create.
- **Compatibilidade/Riscos:** Automação de construção em larga escala. Riscos: repetição infinita/offset incorreto, resource requirements, chunk boundaries, contraption lifecycle, block entity placement e incompatibilidade futura com Create schematic internals. 2.0.10 corrige compatibilidade com Create 6.0.7.
- **Observações:** mod id `create_pattern_schematics`; runtime 2.0.10. Features oficiais: Pattern Schematics para repetir schematics, printing para Schematicannon e Infinite Contraption Construction em trains, gantry carriages ou outras contraptions. NeoForge 1.21.1 é a linha principal de desenvolvimento.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_pattern_schematics` 2.0.10 + Modrinth/CurseForge oficiais da release 2.0.10 e descrição do projeto.
- **Fonte:** https://modrinth.com/mod/create-pattern-schematics/version/VSJhIkG2
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — pattern/printing authority, contraption infinite-construction, Schematicannon boundary, offsets/resources, chunk/lifecycle e compatibilidade 2.0.10 com Create 6.x catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Pattern Schematics 2.0.10 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 📐 Versão física confirmada: `create_pattern_schematics-2.0.10.jar`, mod id `create_pattern_schematics`, runtime `2.0.10`, NeoForge 1.21.1. A 2.0.10 foi publicada para funcionar com a linha Create 6.0.7; o pack usa **Create 6.0.10**.

## 1. Papel e authority
Create: Pattern Schematics estende o sistema de schematics do Create para **repetição de padrões**. Create continua authority do schematic format, Schematicannon e contraption primitives; o addon decide como um schematic-base é repetido/offsetado e aplicado como pattern.

## 2. Building Patterns
Pattern Schematics podem reutilizar um schematic existente para criar iterações repetidas — por exemplo, alongar uma ponte usando a mesma seção várias vezes. O pattern descreve repetição; cada world placement continua sendo uma mutação concreta que precisa ocorrer uma única vez.

## 3. Printing
A documentação oficial mostra Pattern Schematics sendo **impressos em creative** ou enviados para **Schematicannon**. Preview/print plan não é world state concluído: recursos, placement validity e blocks efetivamente colocados continuam seguindo o sistema Create/Minecraft.

## 4. Schematicannon
Quando o pattern é processado por Schematicannon, requisitos de materiais e placement pertencem ao workflow Create. O addon não deve criar uma segunda cobrança de material por bloco nem marcar uma iteração como concluída antes da colocação real.

## 5. Infinite Contraption Construction
O projeto documenta **Infinite Contraption Construction**: Pattern Schematics colocados em trains, gantry carriages ou outras contraptions podem construir sucessivas iterações enquanto se movem.
“Infinite” descreve repetição operacional, não autorização para ignorar limites de chunks, recursos ou performance do servidor.

## 6. Trains
Em trains, pattern placement cruza movimento, train graph e chunks carregados. Uma seção só deve ser construída quando sua posição final estiver resolvida e válida; reconnect/restart não pode repetir uma iteração já concluída por state stale.

## 7. Gantry carriages e outras contraptions
Gantry carriages ou outros assemblies podem carregar o pattern. Transformações locais→mundo precisam aplicar offset/rotação uma única vez; double-transform gera padrões deslocados ou sobrepostos.

## 8. Offset e orientação
O objetivo do mod depende de repetição espacial correta. Rotação, mirror, anchor e offset devem derivar do schematic/pattern state real da build.
Esta ficha não inventa valores máximos, teclas ou fórmulas de offset não publicados para 2.0.10.

## 9. Materiais e inventories
Construção survival deve preservar conservation de materiais. Se contraption/Schematicannon consome um stack, outro handler não pode consumi-lo novamente nem manter cópia no inventory anterior.
Mounted inventories devem invalidar referências quando assembly/disassembly muda ownership.

## 10. Block entities e NBT
Schematics podem incluir blocks com state complexo. Placement deve respeitar as regras de segurança/serialization do Create e do provider do block. Não copiar NBT arbitrário por bypass externo apenas porque o pattern repete a estrutura.

## 11. Chunks e performance
Patterns longos podem atravessar muitos chunks e gerar grande volume de block updates. Construção automática deve respeitar chunk lifecycle; não usar o addon como preloader infinito nem forçar chunks sem política explícita.
Performance QA deve medir número de placements/tick, updates de neighbors e interação com estruturas grandes.

## 12. Compatibilidade 2.0.10
O changelog exato da **2.0.10** registra fix para funcionar com o então Create 6.0.7. O pack está em 6.0.10, portanto o principal regression gate é assegurar que internals de schematic/contraption usados pelo addon continuam compatíveis nessa versão posterior.
Não assumir compat eterna com todo 6.x sem smoke-test.

## 13. Client/server
Pattern data que determina world placement deve ser validado common/server. Overlays, previews e menus são client-facing.
Cliente não pode colocar blocks ou avançar iteration counter sozinho sem confirmação do servidor.

## 14. Lifecycle
Validar criação/edição de pattern, print, Schematicannon, assembly em contraption, chunk unload, train unload/reload, disconnect/reconnect, server restart e resource/datapack reload quando aplicável.
State de progresso não pode voltar e reconstruir trecho já finalizado.

## 15. Riscos
1. Iteração construída duas vezes após restart/reload.
2. Offset/rotation aplicado incorretamente.
3. Material consumido duas vezes ou duplicado.
4. Block entity/NBT colocado de forma insegura.
5. Contraption atravessar chunk não carregado e perder/duplicar state.
6. Loop de construção causar carga/performance excessiva.
7. Create 6.0.10 alterar internals usados pelo fix 6.0.7.
8. Preview cliente divergir do placement server-side.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Criar pattern simples a partir de schematic-base.
3. Repetir ponte/linha com múltiplas iterações e verificar offsets.
4. Schematicannon em survival com conservation de materiais.
5. Train construindo pattern em movimento.
6. Gantry carriage e outra contraption suportada.
7. Chunk border e unload/reload no meio da construção.
8. Server restart entre duas iterações sem reconstruir a anterior.
9. Pattern contendo block entity simples em mundo de teste.
10. Pattern longo para observar performance e neighbor updates.

## 17. Evidência
- modlist física 08/09/2026: Pattern Schematics 2.0.10;
- Modrinth/CurseForge oficiais: repeating schematics, printing, Schematicannon e Infinite Contraption Construction em trains/gantries/contraptions;
- changelog 2.0.10: fix para Create 6.0.7;
- upstream: NeoForge 1.21.1 é a linha principal de desenvolvimento.

> 🔒 Boundary canônico: **Pattern Schematics decide repetição e transformação; Create decide schematic/contraption/placement primitives**. Cada bloco e cada iteração devem ser materializados exatamente uma vez.