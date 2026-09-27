# Loot Integrations: Ice and Fire

## Propriedades do registro

- **Mod:** Loot Integrations: Ice and Fire
- **Arquivo JAR:** lootintegrations_iceandfire-1.2.jar
- **Versão 1.21.1:** 1.2
- **Categoria:** Compat, Worldgen, Exploração
- **Tipo de conteúdo:** Addon
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/loot-integrations-ice-and-fire
- **Função:** Addon de Loot Integrations que adiciona e diversifica loot modded nas estruturas e bosses de Ice and Fire.
- **Dependências:** Loot Integrations 4.7 + Ice and Fire Community Edition 2.1.2. Cupboard 4.1 é dependência do framework base.
- **Compatibilidade/Riscos:** Riscos: inflação de loot nas Ice/Fire/Lightning caves, double injection com LootJS/datapacks, drift de tabelas após update, reload stale e multiplicação econômica com Lootr.
- **Sobreposição:** Bridge de composição de loot; não substitui Ice and Fire nem Lootr. Pode sobrepor scripts/datapacks que alterem as mesmas tabelas.
- **Observações:** Release/runtime 1.2. Changelog 1.2 adiciona integração de loot para ice, fire e lightning caves.
- **Procedência:** modlist.txt física reconferida em 13/09/2026 + CurseForge oficial Loot Integrations: Ice and Fire confirmando `lootintegrations_iceandfire-1.2.jar` como release 1.21.1 atual + changelog 1.2 + Loot Integrations 4.7 já auditado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Loot Integrations: Ice and Fire 1.2/JAR físico reconfirmado; 1.2 permanece a release explícita para Minecraft 1.21.1 mais recente localizada. Caves elementais, framework 4.7, Lootr/LootJS e riscos econômicos preservados.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #382: JAR `lootintegrations_iceandfire-1.2.jar`, mod id `lootintegrations_iceandfire`, runtime `1.2`, SHA-1 `4760d3dfbe2bd81edb55c7745a1332db1eeaf849`.

<callout icon="🐉" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_iceandfire-1.2.jar`, versão `1.2`. É um **addon data-driven de Loot Integrations** para Ice and Fire Community Edition; amplia loot em estruturas/cavernas compatíveis sem assumir ownership de mobs, bosses ou containers.
</callout>
## 1. Identidade
A modlist física confirma `lootintegrations_iceandfire-1.2.jar` em NeoForge 1.21.1. A publicação oficial 1.2 é compatível com 1.21.1 e o changelog acrescenta loot para **ice, fire e lightning caves**.
## 2. Papel e dependências
Loot Integrations 4.7 fornece o motor server-side; Ice and Fire Community Edition 2.1.2 fornece criaturas, estruturas, tabelas e itens. Este addon define a ponte de loot entre esses providers. Cupboard 4.1 é dependência do framework base.
## 3. Composição de loot
A integração usa o modelo data-driven do Loot Integrations: resultados de tabelas fonte podem ser selecionados e inseridos em tabelas-alvo. Quantidade, peso e limites finais permanecem sujeitos aos dados carregados e à configuração do framework; não foram inventados valores específicos do addon sem seus JSONs completos.
## 4. Caves elementais
A 1.2 expande especificamente superfícies de loot de cavernas **Ice**, **Fire** e **Lightning**. Isso pode aumentar a disponibilidade de itens modded em conteúdo já associado a dragões/alto risco, exigindo balanceamento contra a progressão do pack.
## 5. Server authority e multiplayer
A composição é resolvida no servidor. Lootr, quando aplicado ao container, pode individualizar o resultado por jogador; portanto o impacto econômico total pode crescer com a quantidade de jogadores mesmo que a regra de injeção execute uma única vez por geração.
## 6. Interação com outros modifiers
LootJS, datapacks e outros loot modifiers podem tocar as mesmas tabelas. A validação deve detectar double injection, ordem inesperada e tabelas inexistentes após updates de Ice and Fire.
## 7. Riscos
1. Inflação de loot em caves de alto valor.
2. Double injection com scripts/datapacks.
3. Drift de IDs/tabelas entre versões de Ice and Fire CE.
4. Reload stale/duplicado.
5. Multiplicação econômica com Lootr em multiplayer.
6. Itens modded superponderados pelo framework alterarem power curve.
## 8. Matriz de testes
- [ ] Server inicia com addon + Loot Integrations 4.7 + Ice and Fire CE 2.1.2.
- [ ] Ice/Fire/Lightning caves resolvem suas tabelas sem erro.
- [ ] Cada geração aplica a ponte uma única vez.
- [ ] `/reload` não duplica modifiers.
- [ ] LootJS/datapacks não repetem a mesma integração.
- [ ] Lootr preserva personalização sem double fill.
- [ ] Recompensas permanecem proporcionais ao risco/progressão.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências e limites
Fontes: modlist física, publicação/changelog oficial 1.2 e implementação auditada do framework Loot Integrations 4.7. Não foram atribuídos IDs ou probabilidades não demonstrados pelas fontes.
