# Create: Fast Schematic Cannon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813fa8fefcd3d803e52d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Fast Schematic Cannon
- **Arquivo JAR:** `CreateFastSchematicCannon-2.6.1-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.6.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Performance, Tecnologia
- **Função:** Acelera o Schematicannon e reduz atrasos de construção, incluindo ajustes voltados a desempenho/servidor.
- **Dependências:** Create obrigatório; pack físico usa Create 6.0.10. Build 2.6.1 é Beta NeoForge 1.21.1 Client & Server.
- **Sobreposição:** Patch específico do Schematicannon; não é equivalente a Pattern Schematics. Aceleração modifica throughput/lifecycle do cannon e deve preservar integralmente material requirements/placement do Create.
- **Compatibilidade/Riscos:** Riscos: batch accelerated consumir/duplicar material; recovery de fuel/material/target usar cursor stale; chunk unload; remote schematic crash regression; Blaze Burner issue; completion duplicada; spike de MSPT; Create internals drift.
- **Observações:** Runtime físico corrigido para `2.6.1-neoforge`; a observação antiga `1.4.1-neoforge` estava obsoleta. A 2.6.1 Beta garante no-progress boundary sem consumo/dupe e lazy recovery quando fuel/material/target retornam.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + CurseForge oficial do arquivo 2.6.1 Beta e histórico público da linha Fast Schematic Cannon. Source matching não localizado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fast-schematiccannon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê da build Beta 2.6.1-neoforge com accelerated batch, no-progress boundary, lazy recovery, material conservation, chunk/restart e regressões históricas catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 💨 **Identidade física confirmada:** `CreateFastSchematicCannon-2.6.1-neoforge-1.21.1.jar`, mod id `createfastschematiccannon`, runtime `2.6.1-neoforge`. A build física é a **2.6.1 Beta** para NeoForge 1.21.1, publicada em 01/09/2026; a observação antiga de runtime 1.4.1-neoforge estava obsoleta.

## 1. Papel e authority
Create: Fast Schematic Cannon modifica o comportamento do Schematicannon do Create para acelerar construção e reduzir delays, especialmente em servidor. Create continua authority de schematic, placement validity, material requirements e Schematicannon base; o addon owns a política de aceleração/patches que interceptam esse fluxo.

## 2. Build Beta
A versão 2.6.1 física é classificada como Beta, embora 1.4.1 continue aparecendo como release estável principal na página. Isso não invalida a presença, mas aumenta a prioridade de regression tests antes de considerar o patch confiável em mundo de produção.

## 3. Accelerated batch
O changelog 2.6.1 menciona explicitamente um **accelerated batch**. A implementação exata não foi source-pinada nesta auditoria; portanto tamanho do lote, tick budget e algoritmo permanecem fail-closed.
A invariant obrigatória é material/placement conservation.

## 4. No-progress boundary — 2.6.1
O principal delta da 2.6.1 completa um baseline de estados em que o cannon não pode progredir: se o estado necessário estiver indisponível, o batch acelerado deve parar **sem consumir nem duplicar material**.
Isso é um gate diretamente ligado a integridade de inventário.

## 5. Lazy recovery — 2.6.1
A release também restaura recuperação preguiçosa quando **fuel, material ou target availability** volta a existir. O cannon precisa retomar sem exigir recriação e sem repetir placements já liquidados.

## 6. Fuel unavailable
Ao ficar sem fuel durante um batch, nenhum material de bloco deve ser consumido antecipadamente para placements não executados. Reabastecer precisa retomar do cursor correto.

## 7. Material unavailable
Quando o próximo material não existe, o cannon deve pausar no boundary correto. Inserir o item depois deve permitir retomar uma única vez, sem pular bloco e sem consumir dois stacks.

## 8. Target unavailable
Posição de destino temporariamente inválida/indisponível deve interromper progresso do batch. Ao tornar-se válida, o addon deve revalidar o mundo atual, não assumir que a condição antiga continua verdadeira.

## 9. Remote schematic regression
O histórico 1.2 corrige crash de servidor em uso de schematic remoto. Como a 2.6.1 incorpora a linha posterior, remote schematic continua regression gate: nenhuma aceleração justifica dereference/client-only state no dedicated server.

## 10. Cannon delay patch
A linha 1.1 adicionou delay tick para corrigir o delay do próprio cannon Create. A 2.6.1 ainda é um patch comportamental dessa mesma superfície; update de Create 6.0.10 pode alterar ordering esperado e precisa ser testado.

## 11. Blaze Burner blacklist
O histórico 1.1 adicionou blacklist para evitar bug envolvendo Create Blaze Burner. Essa proteção deve permanecer efetiva se a 2.6.1 herdou o comportamento; a ficha não inventa a lista completa de blocos blacklisted.

## 12. Material conservation
Toda tentativa accelerated precisa ter semântica transacional por placement: validar target e recurso, consumir uma vez, colocar uma vez, atualizar cursor uma vez. Cancel/retry após failure não pode reaplicar etapa já confirmada.

## 13. Chunk boundaries
Schematicannon pode construir em múltiplos chunks. Chunk não carregado, unload durante batch e reload precisam cair no no-progress boundary, não em consumo antecipado ou placement em state stale.

## 14. Completion state
Ao finalizar schematic, o cannon deve terminar exatamente uma vez. Aceleração não pode emitir completion cedo enquanto ainda há blocks pendentes nem repetir finalização ao recuperar fuel/material após conclusão.

## 15. Client/server
Placement, material consumption, schematic progress e completion são server-authoritative. Render/particles/status UI são client-facing. Cliente não pode aumentar batch ou declarar target válido sem validação do servidor.

## 16. Performance
O objetivo declarado é reduzir delay/acelerar construção, mas aumento de throughput pode concentrar custo de tick. Performance deve ser avaliada por MSPT/tick spikes e não apenas por tempo total de schematic.

## 17. Riscos
1. Batch acelerado consome material sem placement.
2. Retry duplica block placement ou consumo.
3. Fuel recovery reinicia cursor antigo.
4. Material recovery pula/repete etapa.
5. Target volta válido e usa world state stale.
6. Chunk unload no meio do batch corrompe progress.
7. Remote schematic reproduz crash server-side.
8. Blaze Burner bug reaparece.
9. Create 6.0.10 muda internals esperados pelo patch.
10. Aceleração causa spike de MSPT.
11. Beta 2.6.1 possui regressão não coberta pela release estável 1.4.1.

## 18. Matriz de testes
- [ ] Dedicated server inicia com 2.6.1-neoforge + Create 6.0.10.
- [ ] Schematic pequeno conclui sem material mismatch.
- [ ] Remover fuel no meio do batch pausa sem consumir material extra.
- [ ] Repor fuel retoma do ponto correto.
- [ ] Remover material pausa e repor retoma sem dupe.
- [ ] Tornar target indisponível interrompe sem placement inválido.
- [ ] Chunk unload/reload não duplica nem perde progresso.
- [ ] Remote schematic não reproduz crash histórico.
- [ ] Blaze Burner permanece protegido pelo comportamento aplicável.
- [ ] Completion ocorre uma única vez.
- [ ] Profiling compara MSPT/tick spikes com cannon vanilla.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
A modlist física confirma runtime 2.6.1-neoforge. A página oficial confirma Beta NeoForge 1.21.1, função de aceleração e os deltas 2.6.1 de no-progress boundary/lazy recovery, além do histórico de remote schematic, delay e Blaze Burner. Source matching não foi localizado; algoritmo, mixins e batch size permanecem fail-closed.

> 🔒 **Boundary canônico:** o addon pode acelerar o Schematicannon, mas não pode relaxar as invariants do Create: target válido, material conservado, cursor monotônico e completion única.
