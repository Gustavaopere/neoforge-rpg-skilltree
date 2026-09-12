# Loot Integrations: Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ae8efdc16cd8e1b112
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Loot Integrations: Cataclysm
- **Arquivo JAR:** `lootintegrations_cataclysm-1.2.jar`
- **Versão 1.21.1:** 1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen, Exploração
- **Função:** Addon de Loot Integrations que diversifica/injeta loot nas estruturas e tabelas ligadas a L_Ender's Cataclysm.
- **Dependências:** Loot Integrations 4.7 + L_Ender's Cataclysm 3.33. Cupboard 4.1 é dependência do framework base.
- **Sobreposição:** Bridge data-driven sobre o domínio de composição de loot. Não substitui Cataclysm nem Lootr; pode sobrepor LootJS/datapacks/outros modifiers sobre as mesmas tabelas.
- **Compatibilidade/Riscos:** Riscos: double injection com LootJS/datapacks, inflação econômica e power scaling em estruturas de Cataclysm, drift de loot-table IDs, reload stale/duplicado e multiplicação do impacto quando Lootr individualiza containers.
- **Observações:** Filename/publicação: 1.2. Metadata runtime canônica no JAR físico: 1; preservar as duas identidades separadamente. Changelog 1.2 adiciona abandoned, acropolis, amethyst nest e desert treasure loot.
- **Procedência:** modlist.txt física atual + publicação oficial `lootintegrations_cataclysm-1.2.jar` + changelog 1.2 + comportamento/source auditado do framework Loot Integrations 4.7.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/loot-integrations-cataclysm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — addon Cataclysm 1.2/publicação reconciliado com runtime metadata 1; alvos 1.2, framework 4.7, ownership, Lootr/LootJS, economia, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🏺 **ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_cataclysm-1.2.jar`, mod id `lootintegrations_cataclysm`. A metadata runtime do JAR registra versão `1`; o sufixo/publicação do arquivo é `1.2`. É um **addon data-driven de Loot Integrations** para conteúdo de L_Ender's Cataclysm, não um segundo sistema de loot.

## 1. Identidade e versionamento
- **JAR físico:** `lootintegrations_cataclysm-1.2.jar`.
- **Runtime metadata:** `1` — esta é a versão preservada na coluna canônica do pack.
- **Filename/publicação:** `1.2`.
- **Minecraft/loader:** 1.21.1 / NeoForge no runtime do pack.
A separação entre runtime `1` e publicação `1.2` é intencional; não normalizar um valor no outro.

## 2. Papel no modpack
O addon fornece regras de integração que fazem loot de tabelas compatíveis aparecer em estruturas/conteúdo de **L_Ender's Cataclysm** usando o framework Loot Integrations. Ele não registra o combat engine, bosses, estruturas ou itens de Cataclysm e não controla a persistência de containers.

## 3. Escopo 1.2 documentado
O changelog da publicação 1.2 acrescenta integrações para **abandoned loot**, **acropolis loot**, **amethyst nest loot** e **desert treasure loot**. A função é enriquecer essas superfícies com itens escolhidos a partir de tabelas de loot de referência/comparáveis. Nenhuma contagem fixa de itens por estrutura é presumida sem ler os JSONs efetivos carregados no runtime.

## 4. Dependências e ownership
- **Loot Integrations 4.7** é o framework que carrega/aplica os dados.
- **L_Ender's Cataclysm 3.33** está fisicamente presente e continua authority de suas estruturas, bosses, tabelas e itens.
- **Cupboard 4.1** é dependência do framework base, não uma feature deste addon.
O addon só define a ponte entre os providers.

## 5. Server-side e geração de loot
A aplicação efetiva ocorre no pipeline server-side de Loot Integrations. Cliente, JEI ou HUDs não decidem se um item será inserido. A tabela final gerada no servidor é a fonte de verdade para rewards e automações.

## 6. Interação com Lootr
Loot Integrations altera **composição** do loot; Lootr pode tornar o mesmo container individual por jogador. Em conjunto, uma estrutura de Cataclysm enriquecida pode render conjuntos individualizados para vários jogadores, multiplicando o impacto econômico sem constituir double injection por si só.

## 7. Interação com outros modifiers
LootJS, datapacks e outros global loot modifiers podem atuar nas mesmas tabelas. O risco relevante é aplicar duas integrações semanticamente equivalentes, produzir ordem inesperada ou elevar demais a densidade de itens modded. A presença conjunta exige inspeção do loot gerado, não apenas ausência de crash.

## 8. Riscos técnicos e de balanceamento
1. **Economy inflation** em estruturas de alto valor de Cataclysm.
2. **Double injection** com scripts/datapacks paralelos.
3. **Provider drift** se IDs/tabelas de Cataclysm mudarem em atualização.
4. **Reload duplication/stale data** se datapacks forem alterados sem regressão.
5. **Power scaling** inadequado ao inserir equipamentos/materiais modded em conteúdo de progressão avançada.
6. **Lootr multiplication** por recompensas individualizadas em multiplayer.

## 9. Matriz de testes
- [ ] Dedicated server inicia com addon + Loot Integrations 4.7 + Cataclysm 3.33.
- [ ] Abandoned, Acropolis, Amethyst Nest e Desert Treasure carregam sem missing loot-table IDs.
- [ ] Cada container recebe a integração apenas uma vez.
- [ ] `/reload` não duplica regras nem deixa integração antiga em memória.
- [ ] LootJS/datapacks do pack não repetem os mesmos injections.
- [ ] Lootr mantém personalização por jogador sem alterar a composição além do esperado.
- [ ] Densidade e raridade de recompensas permanecem coerentes com a progressão do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limites
Foram usados a modlist física atual, a publicação oficial `lootintegrations_cataclysm-1.2.jar`, o changelog 1.2 e o comportamento auditado do framework Loot Integrations 4.7. A ficha não inventa registry IDs ou quantidades de loot que não estejam demonstrados pelas fontes efetivamente consultadas.
