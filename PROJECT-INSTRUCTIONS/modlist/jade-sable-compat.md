# Jade Sable Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818ebdaae59248a3ec4e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — compat e dois providers confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Jade Sable Compat
- **Arquivo JAR:** `sablejade-1.3.0.jar`
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Bridge client-side que faz Jade selecionar o bloco real em Sable sublevels/moving constructions usando retrace consciente da pose do sublevel.
- **Dependências:** Providers físicos confirmados: Jade 15.10.6+neoforge e Sable 2.0.5. O compat 1.3.0 não substitui nenhum dos dois.
- **Sobreposição:** Não substitui Jade; corrige apenas a boundary de targeting Jade ↔ Sable.
- **Compatibilidade/Riscos:** Client-side targeting bridge. Riscos: transform mismatch, stale target de um frame, lookup no level errado, Jade/Sable API drift, providers Jade consultando contexto antigo e conflito com outros mods de raycast.
- **Observações:** A 1.3.0 é a Release NeoForge 1.21.1 atual localizada. Corrige título, ícone e selected block do Jade para coincidir com o bloco sob o crosshair em estruturas Sable.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Jade Sable Compat 1.3.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jade-sable-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Jade Sable Compat 1.3.0 reconstruído: Sable-aware retrace, targeting/tooltip ownership, movimento/rotação, client lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

> **Divergência documental registrada:** a procedência do Notion menciona “modlist.txt física atual de 11/09/2026”. A modlist física mais recente efetivamente acessível nesta execução é o snapshot de 08/09/2026 com 595 entradas; ele confirma `sablejade-1.3.0.jar`, Jade `15.10.6+neoforge` e Sable `2.0.5`. O texto-fonte foi preservado sem ser promovido a autoridade física inexistente.

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sablejade-1.3.0.jar`, mod id `sablejade`, versão `1.3.0`, NeoForge 1.21.1. É uma bridge **client-side** de targeting entre Jade e sublevels/estruturas móveis do Sable: corrige qual bloco o Jade considera estar sob o crosshair.

## 1. Identidade e papel
- **Mod:** Jade Sable Compat.
- **JAR:** `sablejade-1.3.0.jar`.
- **Mod id:** `sablejade`.
- **Versão:** `1.3.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Papel:** corrigir seleção/tooltip do Jade em Sable sublevels e moving constructions.

## 2. Stack físico
O pack contém:
- **Jade 15.10.6+neoforge**;
- **Sable 2.0.5**;
- **Jade Sable Compat 1.3.0**.
A bridge depende funcionalmente dos dois providers; não substitui nenhuma funcionalidade de Jade ou Sable fora da boundary de targeting.

## 3. Problema resolvido
Em Sable, blocos renderizados dentro de sublevels vivem em um espaço de coordenadas/pose próprio. Um raycast/retrace comum do world principal pode identificar um bloco diferente do que o jogador visualmente está mirando.
A consequência é Jade exibir título, ícone ou dados do bloco errado, apesar de o crosshair estar sobre a construção móvel correta.

## 4. Sable-aware client retrace
A documentação oficial da 1.3.0 descreve um caminho de **client retrace aware de Sable**. O compat refaz/ajusta a seleção usando a pose correta do sublevel/moving construction antes de entregar o alvo ao Jade.
Isso alinha três superfícies:
- bloco realmente sob o crosshair;
- título/ícone mostrado pelo Jade;
- linha/outline de seleção correspondente ao bloco correto.

## 5. Authority e ownership
- **Sable:** pose/transform e membership do bloco no sublevel.
- **Jade:** coleta e apresentação dos dados do alvo.
- **Jade Sable Compat:** resolve a conversão/seleção correta entre os dois espaços.
A bridge não deve manter cache funcional paralelo de block state nem alterar o bloco real para tornar o tooltip correto.

## 6. Client-only boundary
O projeto é publicado como Client. O servidor continua authority do block state, inventário e gameplay; esta bridge só corrige qual target o cliente entrega à camada de informação.
Um cliente sem o compat pode receber tooltips errados em construções móveis, mas isso não deve mudar state do servidor.

## 7. Movimento e rotação
O cenário relevante não é apenas sublevel parado. Validar targeting durante:
- translação contínua;
- rotação;
- mudança rápida de pose;
- câmera próxima a bordas entre world e sublevel;
- dois sublevels próximos/overlapping visualmente.
O retrace deve usar a pose atual, sem um frame/cache antigo apontando para outro bloco.

## 8. Tooltips e providers Jade
Depois que o bloco correto é selecionado, Jade e seus plugins/addons continuam responsáveis por conteúdo de tooltip. O compat não deve reimplementar providers de energia, inventory, crops ou outros dados.
O pack também contém addons do Jade; todos dependem de o target-base estar correto, portanto esta bridge melhora indiretamente a confiabilidade desses providers em sublevels.

## 9. Lifecycle client
Validar:
- login e primeiro render de sublevel;
- assembly/disassembly;
- chunk/sublevel load/unload;
- moving construction entrando/saindo de render distance;
- resource reload;
- dimensão/reconnect;
- Jade config toggle/reload quando aplicável.
Nenhum alvo antigo deve permanecer preso após a estrutura sumir ou mudar de pose.

## 10. Riscos técnicos
1. **Transform mismatch:** retrace usa pose local/world errada.
2. **One-frame stale target:** tooltip atrasa em relação ao movimento.
3. **Wrong-level lookup:** target é resolvido no main level em vez do sublevel.
4. **Jade API drift:** hooks/target pipeline mudam em atualização.
5. **Sable API drift:** projeção/retrace de sublevel muda.
6. **Provider mismatch:** target-base correto, mas addon Jade consulta level/pos original incorretamente.
7. **Client render conflict:** outro mod altera crosshair/raycast no mesmo path.

## 11. Matriz de testes
- [ ] Cliente inicia com Jade 15.10.6 + Sable 2.0.5 + compat 1.3.0.
- [ ] Bloco parado em sublevel mostra título/ícone corretos.
- [ ] Bloco em sublevel em movimento continua selecionado corretamente.
- [ ] Rotação rápida não troca tooltip para bloco do world atrás da estrutura.
- [ ] Outline/linha de seleção coincide com o bloco exibido no Jade.
- [ ] Block entity em sublevel mostra dados do alvo correto.
- [ ] Dois sublevels próximos não trocam targets.
- [ ] Assembly/disassembly remove imediatamente o targeting antigo.
- [ ] Reconnect/resource reload não deixa cache stale.
- [ ] Addons Jade presentes continuam recebendo o bloco/level corretos quando suportado.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física atual: Jade 15.10.6, Sable 2.0.5 e sablejade 1.3.0.
- CurseForge oficial 1.3.0: projeto client-only, correção de seleção do Jade, alinhamento de title/icon/selected block e Sable-aware retrace.
- **Limite:** não foi inferido o detalhe interno de raycast além do contrato publicado; compatibilidade de cada provider Jade em sublevel depende do provider correspondente.
