# Simply Tooltips

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db8113aee6ebb3e9971a95
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `SimplyTooltips-neoforge-0.1.5.jar`, mod id `simplytooltips`, runtime `0.1.5`, mixin `simplytooltips.mixins.json`; Simply Swords 1.70.2, Create 6.0.10, Better Advanced Tooltips 2101.1.0-build.5 e Obscure Tooltips 4.2.4 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Simply Tooltips 0.1.5, Simply Swords 1.70.2, Create 6.0.10, Better Advanced Tooltips e Obscure Tooltips estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Simply Tooltips
- **Arquivo JAR:** `SimplyTooltips-neoforge-0.1.5.jar`
- **Versão 1.21.1:** 0.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, QoL, Compat
- **Função:** Renderer client-side de tooltips data-driven com Theme Studio, themes/borders customizáveis, blacklists, captura GIF e compatibilidade para components custom; fornece suporte explícito a cooldowns/implicits do Simply Swords.
- **Dependências:** NeoForge 1.21.1. É required dependency do Simply Swords 1.70.2 no stack atual, além de ter função visual própria. Integrações físicas: Simply Swords 1.70.2, Create 6.0.10; Better Advanced Tooltips 2101.1.0-build.5 e Obscure Tooltips 4.2.4 coexistem em camadas visuais distintas.
- **Sobreposição:** Sobreposição principalmente de apresentação com Obscure Tooltips e Better Advanced Tooltips. Better Advanced Tooltips adiciona dados técnicos; Simply Tooltips controla tema/render/components e integração Simply Swords. Coexistência exige QA visual, não remoção automática.
- **Compatibilidade/Riscos:** Client-side renderer extensível. Riscos: conflito de composição com Obscure/Better Advanced Tooltips, JEI/custom-screen hover, legacy formatting, stale resource-pack theme, component blacklist excessiva e tooltip state visual divergente do servidor. 0.1.5 inclui fix potencial para crash em UI JEI.
- **Observações:** Simply Swords atual declara Simply Tooltips como required dependency, mas a decisão curatorial `Manter` foi preservada porque o mod também fornece UI própria. 0.1.5 é Release NeoForge 1.21.1 de 23/08/2026.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Simply Tooltips 0.1.5 + relação required do Simply Swords atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/simply-tooltips/files/8715141
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Simply Tooltips 0.1.5 reconstruído: data-driven renderer, Theme Studio, borders/themes, blacklists, GIF capture, Simply Swords cooldowns/implicits, Create/custom components, UI lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SimplyTooltips-neoforge-0.1.5.jar`, mod id `simplytooltips`, versão `0.1.5`, NeoForge 1.21.1. É um renderer **client-side e data-driven** de tooltips e, no stack atual, também é **required dependency de Simply Swords 1.70.2**. A decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Simply Tooltips.
- **JAR:** `SimplyTooltips-neoforge-0.1.5.jar`.
- **Mod id:** `simplytooltips`.
- **Versão:** `0.1.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client.
- **Mixin físico:** `simplytooltips.mixins.json`.
- **Papel:** tema/render de tooltip, components custom, Theme Studio e compat específica do ecossistema Simply.

## 2. Data-driven rendering
A proposta do mod é permitir que aparência e comportamento visual de tooltips sejam orientados por data/resources, em vez de hardcode para cada item. Themes e borders podem ser fornecidos por resource packs e associados a items/components.

Isso torna resource reload uma boundary funcional: mudança de theme precisa refletir sem manter cache visual antigo.

## 3. Escopo padrão
A documentação do projeto informa que, por default, o sistema se aplica a itens modded com theme válido. Configurações podem ampliar a aplicação para itens modded/vanilla de forma mais ampla.

A mera presença do mod não autoriza afirmar que todo item do pack usa um theme custom.

## 4. Theme Studio — 0.1.5
A 0.1.5 adiciona **Theme Studio**, ferramenta in-game para editar themes e atribuir items. É uma surface de autoria client-side; salvar theme/config não deve alterar stats, item components funcionais ou state server-side.

Validar open/edit/apply/cancel/reload e conflito com UI scale.

## 5. Borders data-driven
A build adiciona:
- criação data-driven de custom borders;
- API/registro para developers adicionarem borders próprios;
- uso de qualquer border com qualquer theme.

Border inválida/missing deve degradar sem quebrar tooltip inteira.

## 6. Blacklists
0.1.5 adiciona:
- blacklist por item/namespace;
- blacklist de tooltip components.

Blacklists são presentation policy. Ocultar component visual não deve remover o component real do item nem mudar efeito funcional no servidor.

## 7. Item-frame progress bar
A build introduz suporte opcional a progress bar em item frame do tooltip. Esse indicador é visual; qualquer cooldown/progress real continua sob authority do provider.

Se o valor vier de state server-synced, o cliente deve somente representar o valor recebido.

## 8. Simply Swords integration
A 0.1.5 adiciona suporte explícito para:
- **tooltip cooldowns** de Simply Swords;
- **weapon implicits** de Simply Swords;
- component-based tooltip themes usados pelo ecossistema.

Como Simply Swords 1.70.2 está presente e declara Simply Tooltips required, esta integração é path runtime concreto.

## 9. Create/custom tooltip data
A release declara suporte a Create e, de modo genérico, à maioria dos mods que adicionam custom tooltip data.

Isso significa que o renderer tenta preservar/renderizar components de terceiros, não que compreenda semanticamente toda informação de qualquer mod.

Create 6.0.10 está presente, portanto tooltips Create complexas são regression gate.

## 10. Legacy formatting codes
A build adiciona handling para legacy formatting codes. Strings que misturam formatação legada, components modernos, localization e themes precisam manter ordem/estilo sem vazar códigos literais para o usuário.

## 11. Captura de tooltip em GIF
O projeto oferece hotkey padrão `H` para captura de tooltip em GIF na pasta de screenshots. A 0.1.5 corrige acionamento indevido da captura enquanto o usuário digita em text fields.

Regression gate: H em chat/search/config text field não deve disparar captura; H sobre tooltip válido deve produzir somente uma captura por comando.

## 12. Novos themes da 0.1.5
A release inclui novos themes: Blood, Corrupted Eye, Spectral, Radiant, Candle, Amethyst e Tome.

Esses assets são apresentação. A existência do theme não implica que algum item específico do pack esteja associado a ele sem data/config correspondente.

## 13. JEI e custom screens
O changelog registra um **potential fix** para crash ao hover em elementos de UI introduzidos pelo JEI. Como a linguagem upstream é potential, o dossiê não trata o problema como definitivamente eliminado.

Testar inventory/JEI/config screens com tooltips de items simples e items com components custom.

## 14. Coexistência com outros tooltip mods
O pack contém:
- Better Advanced Tooltips `2101.1.0-build.5` — adiciona informação técnica/advanced tooltip;
- Obscure Tooltips `4.2.4` — altera apresentação visual;
- Simply Tooltips `0.1.5` — themes/borders/components e integrações Simply.

Há overlap de render path, mas funções não são equivalentes. Problemas devem ser comprovados por ordem, clipping, duplicate lines, border conflict ou crash real.

## 15. Authority e ownership
- **Provider do item/mod:** conteúdo funcional e valores reais.
- **Minecraft/item components:** texto/data base quando aplicável.
- **Simply Tooltips:** composição visual, border/theme, filtering e capture.
- **Simply Swords:** cooldown/implicit semantics exibidas.

O renderer não deve transformar texto mostrado em authority do gameplay.

## 16. Lifecycle client
Validar:
- startup/login;
- resource reload;
- language change;
- UI scale;
- item components mudando enquanto tooltip está aberta;
- config/theme edit via Theme Studio;
- JEI/recipe UI;
- creative/inventory/chest;
- restart após alterações de theme.

Caches precisam invalidar quando resource/theme muda.

## 17. Performance
Tooltips podem ser reconstruídas muitas vezes por segundo enquanto cursor passa por slots. GIF capture, border composition, component parsing e custom themes não devem gerar alocações/stalls excessivos.

A ficha não presume impacto alto: performance deve ser medida em screens densas com muitos items modded.

## 18. Riscos técnicos
1. **Render conflict:** outro tooltip mod altera mesma fase.
2. **Duplicate component:** linha aparece duas vezes após compat.
3. **JEI hover crash:** fix 0.1.5 não cobre todas custom screens.
4. **Stale resource cache:** theme antigo persiste após reload.
5. **Blacklist excessiva:** dado importante some visualmente.
6. **Legacy formatting leak:** códigos aparecem literais/quebram color.
7. **Input collision:** hotkey H captura enquanto player digita.
8. **Client/server divergence:** tooltip mostra cooldown/stat stale.
9. **UI scale clipping:** border/theme extrapola viewport.

## 19. Matriz de testes
- [ ] Cliente inicia com Simply Tooltips 0.1.5 + Simply Swords 1.70.2.
- [ ] Simply Swords implicits aparecem corretamente.
- [ ] Simply Swords cooldown tooltip acompanha state real.
- [ ] Theme Studio abre, edita, aplica e reverte theme.
- [ ] Resource reload atualiza theme/border sem restart quando suportado.
- [ ] Blacklist de item/namespace remove apenas apresentação desejada.
- [ ] Component blacklist não remove state funcional do item.
- [ ] Create custom tooltip data renderiza sem perda/clipping.
- [ ] Better Advanced Tooltips coexiste sem linhas/borders corrompidos.
- [ ] Obscure Tooltips coexistence é verificada visualmente.
- [ ] H em text field não dispara GIF — regression 0.1.5.
- [ ] H sobre tooltip válido captura uma vez.
- [ ] JEI/custom UI hover não crasha.
- [ ] UI scale e idiomas diferentes mantêm layout legível.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
- Modlist física atual: Simply Tooltips 0.1.5, Simply Swords 1.70.2, Create 6.0.10, Better Advanced Tooltips e Obscure Tooltips.
- CurseForge 0.1.5: Theme Studio, progress bar, data-driven/developer borders, blacklists, Simply Swords cooldown/implicits, Create/custom tooltip support, themes e fixes.
- Relações atuais do Simply Swords: Simply Tooltips é required dependency.
- **Limite:** themes/resource packs efetivamente ativos e config local do renderer não foram auditados; existência de recurso não equivale a uso local.
