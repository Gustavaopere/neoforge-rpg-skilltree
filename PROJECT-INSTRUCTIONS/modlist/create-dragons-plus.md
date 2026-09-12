# Create: Dragons Plus

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8175a2a3d6c2dba0eae0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Dragons Plus
- **Arquivo JAR:** `CreateDragonsPlus-1.11.8b.jar`
- **Versão 1.21.1:** 1.11.8b
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Addon amplo de utilidades e conteúdo complementar para Create, com conveniências para jogadores e recursos auxiliares/compatibilidades para outros addons.
- **Dependências:** Create mínimo 6.0.10; pack usa 6.0.10. Source matching integra opcionalmente Ars Nouveau, Sable, Garnished, Create DnD, Aether e dye ecosystems; conditional-mixin 0.6.4 é jar-in-jar, não top-level.
- **Sobreposição:** É biblioteca/addon transversal de bulk processing e compats, não substituto de Ars Nouveau, Garnished, DnD ou Sable. Integrações só são runtime ativas quando o provider físico correspondente está instalado.
- **Compatibilidade/Riscos:** Riscos: bulk-process dupe; container remainder; conditional mixin classloading; recipe/provider drift; Fluid Hatch stale tank; config/cache reload; Sable contraption air-current double-processing. Aether/dye providers ausentes permanecem inativos.
- **Observações:** Runtime corrigido para `1.11.8b` (a ficha antiga dizia 1.11.7b). Source matching branch 1.21.1/6.0.0-dev declara mod 1.11.8b, Create 6.0.10 e conditional-mixin 0.6.4.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial Create: Dragons Plus 1.11.8b + source oficial DragonsPlusMinecraft/CreateDragonsPlus matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-dragons-plus
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.11.8b com Bulk Coloring/Freezing/Ending/Sanding, Fluid Hatch, Simulated/Sable surfaces, conditional integrations e fixes Aether catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🐉 **Identidade física e source matching confirmados:** `CreateDragonsPlus-1.11.8b.jar`, mod id `create_dragons_plus`, runtime `1.11.8b`. O source 1.21.1 declara exatamente 1.11.8b e Create mínimo 6.0.10. O JAR hospeda `conditional-mixin` 0.6.4 como jar-in-jar; isso não é um mod top-level separado.

## 1. Papel e authority
Create: Dragons Plus é simultaneamente addon funcional e biblioteca para outros addons Create do ecossistema DragonsPlus. Ele owns seus bulk processes, Fluid Hatch, fragile tanks e bridges opcionais; Create continua owner de fans, fluids, contraptions e recipes base consumidos pelas integrações.

## 2. Bulk Coloring
**Bulk Coloring** usa Dye Fluids + Encased Fan e pode traduzir recipes de crafting baseadas em dyes para processamento em bulk. O addon deve consumir corante/fluid e input uma única vez e produzir exatamente o resultado que a recipe válida representa.

## 3. Bulk Freezing
**Bulk Freezing** usa Powder Snow + Encased Fan. O delta 1.11.8b corrige recipe display/container remainder para diferentes buckets na integração Aether. Viewer e Recipe Manager precisam convergir; recipiente restante errado é um risco de dupe/loss.

## 4. Bulk Ending
**Bulk Ending** usa Dragon Head ou Dragon's Breath fluid + Encased Fan. O pack contém Ars Nouveau e a integração oficial permite Containment Jar produzir Dragon's Breath; quando esse fluxo estiver ativo, a origem do fluido e o processamento final precisam manter conservation.

## 5. Bulk Sanding
**Bulk Sanding** usa Quicksand ou blocos tagueados com fan e suporta recipes de Sand Paper Polishing. Tags/recipe lookup são authority; não considerar todo bloco “arenoso” elegível sem recipe/tag correspondente.

## 6. Fluid Hatch
O **Fluid Hatch** é uma interface para retirar/depositar fluidos em Fluid Tanks. Transferência manual ou automatizada deve respeitar capacidade/tipo do tank e não manter referência stale quando o multiblock muda.

## 7. Fragile Fluid Tanks
A linha possui **Fragile Fluid Tank** e **Levitite Fragile Fluid Tank** ligados ao stack Create Simulated/Sable. O pack contém Sable 2.0.5 e Aeronautics 1.3.2; version drift em relação ao source-dev deve ser tratado como regression gate.

## 8. Simulated Contraption air currents
A integração com Simulated permite air currents em contraptions físicas. Fan processing em sublevels precisa seguir transform/posição do body correto; duplicar o mesmo air current em world + sublevel pode processar itens duas vezes.

## 9. Create: Garnished
O source 1.11.8b habilita integração com Create: Garnished; o pack contém Garnished 2.1.9.2. Bulk Coloring/Freezing possuem suporte publicado a recipes desse provider. Recipe identity continua sendo do provider Garnished/Create, não do viewer.

## 10. Create: Dreams n' Desires
O source habilita Create DnD; o pack contém Dreams n' Desires 2.3a-BETA. Bulk Ending/Sanding/Freezing suportam fan recipes/Industrial Fan dessa integração. Como o source-dev usa revisão anterior, smoke-test de recipe resolution é obrigatório.

## 11. Aether
A integração Aether inclui Bulk Enchanting, repair e Moa egg incubation, além de Bulk Freezing para Freezer recipes. **Aether não foi encontrado como JAR top-level na modlist atual**, portanto essa superfície é upstream disponível, não runtime ativa deste pack. O fix 1.11.8b continua documentado porque pertence ao artefato instalado.

## 12. Dye ecosystem
O source habilita Dye Depot, Dyenamics e Arts & Crafts. Esses providers não foram encontrados como top-level na modlist física atual; logo não são marcados como integrações ativas. Conditional mixins devem permanecer inert sem seus providers.

## 13. Ars Nouveau
Ars Nouveau 5.13.1 está instalado. A integração publicada com Containment Jar para Dragon's Breath é, portanto, um ponto concreto de teste cross-mod. Não presumir qualquer outro vínculo Ars além do explicitamente documentado.

## 14. Configuração
A documentação indica que as features podem ser configuradas individualmente, salvo exigências de dependências específicas. Config pode remover recipe/process surface; client viewer deve refletir a configuração efetiva do servidor.

## 15. Conditional Mixin
O JAR inclui `conditional-mixin` 0.6.4 embutido. Sua função é permitir hooks condicionais por provider. Update de um mod integrado pode quebrar um mixin mesmo sem mudar Dragons Plus; classloading deve falhar de forma controlada quando a integração não se aplica.

## 16. Delta 1.11.8b
A release corrige dois pontos Aether: container remainder de recipes JEI de Bulk Freezing e o tutorial Ponder de Bulk Enchanting, incluindo recipes válidas de Altar e apresentação de Moa babies coloridos. São deltas de recipe/UI tutorial, não novas authorities de gameplay.

## 17. Client/server e multiplayer
Recipe matching, fluid/item consumption e contraption processing são server-authoritative. Ponder, JEI e efeitos visuais são client-facing. Dois clientes observando o mesmo fan process não podem gerar dois resultados.

## 18. Lifecycle
Testar `/reload`, config change, provider add/remove, fan processing, fluid tank attach/detach, contraption assemble/disassemble, chunk unload/restart e optional-integration boot. Cache de recipes/tags deve ser invalidado corretamente.

## 19. Riscos
1. Bulk processing duplica input/output em contraption física.
2. Container remainder errado gera bucket dupe/loss.
3. Recipe viewer diverge do Recipe Manager.
4. Conditional mixin tenta carregar classe de mod ausente.
5. Provider update muda recipe type/tag esperado.
6. Fluid Hatch mantém tank reference stale.
7. Dragon's Breath integration cria loop econômico indevido.
8. Config desativa feature mas cache deixa recipe ativa.
9. Sable/Aeronautics drift quebra air-current/sublevel integration.
10. Tutorial Ponder descreve recipe inválida após provider update.

## 20. Matriz de testes
- [ ] Dedicated server inicia com Dragons Plus 1.11.8b + Create 6.0.10.
- [ ] Bulk Coloring consome dye/input uma vez.
- [ ] Bulk Freezing preserva container remainder correto.
- [ ] Bulk Ending e Bulk Sanding usam apenas recipes/tags válidos.
- [ ] Fluid Hatch transfere sem dupe/loss.
- [ ] Ars Nouveau Dragon's Breath flow funciona com provider atual.
- [ ] Garnished 2.1.9.2 recipes resolvem corretamente.
- [ ] DnD 2.3a-BETA fan recipes resolvem sem crash.
- [ ] Ausência de Aether/Dye Depot/Dyenamics/Arts & Crafts não causa classloading failure.
- [ ] Sable 2.0.5 contraption air currents processam uma única vez.
- [ ] `/reload` e config changes invalidam caches.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
A modlist confirma 1.11.8b e os mixins físicos. O source matching confirma versão, Create 6.0.10, integrations habilitadas e conditional-mixin 0.6.4. A documentação oficial confirma Bulk Coloring/Freezing/Ending/Sanding, Fluid Hatch e integrations. Ausências de providers foram verificadas na modlist top-level atual.

> 🔒 **Boundary canônico:** Dragons Plus traduz recipes/fluids/fan processing entre providers; o servidor e os mods donos dos recipes permanecem authority. Conditional integration nunca transforma provider ausente em dependência implícita.
