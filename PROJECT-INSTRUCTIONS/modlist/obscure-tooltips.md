# Obscure Tooltips

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81799a34fedd4052d114
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `obscure_tooltips-neoforge-1.21.1-4.2.4.jar`, mod id `obscure_tooltips`, runtime `4.2.4`, mixins `obscure_tooltips.mixins.json` + `obscure_tooltips.neoforge.mixins.json` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Obscure Tooltips 4.2.4 e os principais mods usados nos regression gates estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Obscure Tooltips
- **Arquivo JAR:** `obscure_tooltips-neoforge-1.21.1-4.2.4.jar`
- **Versão 1.21.1:** 4.2.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Camada visual de tooltips com animações, bordas/ornamentos, partículas, modelos 3D, smooth scrolling, line wrapping e styling data-driven; não altera o gameplay dos itens.
- **Dependências:** NeoForge 1.21.1; nenhuma hard dependency externa adicional foi confirmada para a release física 4.2.4.
- **Sobreposição:** Sobreposição de presentation layer com Better Advanced Tooltips e Simply Tooltips; não é duplicação de atributos/gameplay. Avaliar por layout/renderer concreto.
- **Compatibilidade/Riscos:** Client-side visual por descrição oficial, apesar da metadata do projeto listar Client & Server. Alto potencial de composição com Better Advanced Tooltips/Simply Tooltips; testar renderers customizados, lore longa, GUI scale e `HIDE_TOOLTIP`.
- **Observações:** Runtime 4.2.4. A descrição oficial o trata como client-side; CurseForge marca Client & Server. 4.2.4 corrige injeção quando `HIDE_TOOLTIP` está presente.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 4.2.4 + documentação oficial de rendering/config/data-driven styling.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/obscure-tooltips
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Obscure Tooltips 4.2.4 revalidado: rendering/layout, modelos 3D, styling data-driven, config integrada, HIDE_TOOLTIP regression, composição com outros tooltip mods e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `obscure_tooltips-neoforge-1.21.1-4.2.4.jar`, mod id `obscure_tooltips`, versão `4.2.4`. O mod atua na **apresentação de tooltips**; não altera os atributos, efeitos ou regras de gameplay dos itens que exibe.

## 1. Identidade, versão e papel
- **Mod:** Obscure Tooltips.
- **JAR físico:** `obscure_tooltips-neoforge-1.21.1-4.2.4.jar`.
- **Mod id:** `obscure_tooltips`.
- **Versão instalada:** `4.2.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Release:** estável para 1.21.1.
- **Papel:** enriquecer tooltips com animações, molduras/ornamentos, partículas, modelos 3D integrados, scrolling e wrapping.

## 2. Authority e ownership
Obscure Tooltips é authority somente da **camada visual/composicional do tooltip**. O texto, atributos, enchantments, componentes e valores apresentados continuam pertencendo ao item e aos mods providers. Quando outros mods adicionam linhas ao tooltip, Obscure Tooltips deve renderizá-las/encaixá-las sem reinterpretar seu significado.

A ficha não modela tooltip como fonte de verdade de gameplay: qualquer discrepância entre tooltip e efeito real deve ser resolvida pelo provider do item/atributo.

## 3. Superfícies funcionais confirmadas
A documentação pública 1.21.1 confirma:
- tooltips animados;
- glowing borders e partículas sutis;
- ornamentos/estilos que podem variar por raridade e contexto;
- modelos 3D integrados de armaduras, ferramentas e armas dentro do tooltip;
- smooth scrolling automático quando o tooltip excede a altura da tela;
- line wrapping para nomes/lore/stats longos;
- design modular e data-driven para construir/mesclar estilos, definir conditions e customizar elementos;
- tela de configuração integrada em Minecraft 1.21.1 sem dependência extra.

A release 4.2.4 corrige especificamente a injeção de tooltip quando o flag `HIDE_TOOLTIP` está presente.

## 4. Configuração e data-driven styling
A linha 1.21.1 expõe configuração in-game. A documentação também mantém referência ao arquivo client config da família `config/obscuria/obscure_tooltips-client.toml`; esta auditoria **não leu a configuração local**, portanto nenhum estilo/efeito é declarado ativo ou desativado no pack.

O sistema data-driven permite estilos e conditions customizados. Alterações de resource/config devem ser tratadas como conteúdo de cliente e revalidadas após reload/restart.

## 5. Client / server
A descrição oficial define o mod como **client-side visual enhancement**, enquanto a metadata de projeto do CurseForge lista ambiente Client & Server. Não há mecânica server-authoritative publicada nesta ficha. Portanto:
- o rendering e interação visual são tratados como client-side;
- a presença no servidor não deve ser inferida como necessária para os efeitos visuais sem evidência adicional;
- nenhum estado de gameplay deve depender do mod.

## 6. Lifecycle
Validar:
- abrir inventário e containers diversos;
- hover em item normal, encantado, danificado, com lore e atributos extensos;
- tooltips maiores que a altura/largura da tela;
- alternar GUI scale e resolução;
- resource/config reload;
- troca de dimensão/relog não deve deixar renderer/model state stale;
- itens com `HIDE_TOOLTIP` devem permanecer ocultos sem injeção residual.

## 7. Integrações concretas no pack
- **Better Advanced Tooltips:** ambos mexem na apresentação/informação de tooltips; alto risco de composição visual e ordem de linhas.
- **Simply Tooltips:** outra camada de tooltip; testar bordas, layout e prioridades para evitar stacking incoerente.
- **JEI 19.53.0.426 / Advanced Loot Info / Sophisticated JEI Index:** podem gerar tooltips extensos e contextuais; Obscure Tooltips deve apenas apresentá-los.
- **Apothic Attributes/Enchanting e grandes mods de gear:** produzem lore/atributos/raridade complexos, importantes para stress-test de wrapping, scrolling e modelos 3D.
- **Iris/shaders:** UI geralmente é separada do world shader pipeline, mas testar glitches de framebuffer/model preview quando shaders estiverem ativos.

## 8. Riscos técnicos
1. **Tooltip stacking:** múltiplos mods podem injetar conteúdo/estilo no mesmo evento.
2. **Layout extremo:** textos enormes, GUI scale baixa e telas pequenas podem cortar componentes ou tornar scrolling inadequado.
3. **3D model preview:** itens com renderers customizados podem produzir modelo ausente, incorreto ou exceção no tooltip.
4. **HIDE_TOOLTIP:** 4.2.4 corrige injeção indevida; manter como regression gate.
5. **Data-driven conditions:** condições amplas ou conflitantes podem aplicar estilos errados a muitos itens.
6. **Performance de UI:** animações/partículas/modelos 3D em tooltips complexos podem aumentar custo de frame em inventários densos.

## 9. Multiplayer
Não há state de tooltip compartilhado. O servidor fornece os dados normais dos itens; cada cliente compõe o visual localmente. Testar itens com components/NBT sincronizados do servidor para garantir que o tooltip não fique stale após mudança de estado.

## 10. Matriz de testes
- [ ] Cliente inicia com 4.2.4 sem depender de mod de config adicional.
- [ ] Item vanilla simples e item modded complexo.
- [ ] Armor/tool/weapon com modelo 3D no tooltip.
- [ ] Tooltip maior que a tela: smooth scrolling.
- [ ] Lore muito larga: wrapping sem corte.
- [ ] Item com `HIDE_TOOLTIP`: nenhum conteúdo visual indevido.
- [ ] Better Advanced Tooltips + Simply Tooltips ativos em conjunto.
- [ ] JEI/Advanced Loot Info com tooltips extensos.
- [ ] GUI scale/resolução mínima e máxima usadas pelo pack.
- [ ] Iris/shader ativo durante preview de item.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 11. Evidências
- Modlist física canônica atual de 10/09/2026: JAR, mod id e versão.
- CurseForge oficial Obscure Tooltips 4.2.4: release NeoForge 1.21.1 e fix `HIDE_TOOLTIP`.
- Página oficial do projeto: animated tooltips, 3D models, smooth scrolling, line wrapping, styling/conditions data-driven e config integrada 1.21.1.
