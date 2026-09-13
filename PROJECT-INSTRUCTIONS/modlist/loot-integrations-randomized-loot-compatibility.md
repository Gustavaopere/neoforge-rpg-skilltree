# Loot Integrations: Randomized Loot Compatibility

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816d9b62fd5a848bc686
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Loot Integrations: Randomized Loot Compatibility
- **Arquivo JAR:** `lootintegrations_vanilla-1.7.jar`
- **Versão 1.21.1:** 1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Exploração
- **Função:** Amplia variação das tabelas de loot vanilla/reutilizadas por estruturas, incorporando itens modded comparáveis através do Loot Integrations.
- **Dependências:** Loot Integrations 4.7; Cupboard 4.1 é dependência do framework base.
- **Sobreposição:** Addon de composição de loot. Não substitui Lootr nem mods de estrutura; pode sobrepor LootJS/datapacks/outros modifiers sobre as mesmas tabelas.
- **Compatibilidade/Riscos:** Riscos: double injection com LootJS/datapacks, inflação econômica por tabelas amplamente reutilizadas, progression leakage, drift de loot tables, reload stale/duplicado e multiplicação econômica com Lootr. A 1.7 remove loot para Trail Ruins.
- **Observações:** Filename/publicação: 1.7. Metadata runtime canônica no JAR físico: 1; preservar as duas identidades. Changelog 1.7 remove loot para Trail Ruins.
- **Procedência:** modlist.txt física atual + arquivo/publicação oficial `lootintegrations_vanilla-1.7.jar` + changelog 1.7 + comportamento do Loot Integrations 4.7 auditado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vanilla-loot-addon-for-loot-integrations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Randomized Loot Compatibility 1.7/publicação reconciliado com runtime metadata 1; server-side, Trail Ruins removido, ownership, Lootr/LootJS, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🎲 **ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_vanilla-1.7.jar`, mod id `lootintegrations_vanilla`, metadata runtime `1`; o filename/publicação é `1.7`. É um **addon server-side/data-driven de Loot Integrations** que aumenta a variação de loot em tabelas padrão de baús, inclusive tabelas reutilizadas por estruturas modded.

## 1. Identidade e versionamento
- **JAR físico:** `lootintegrations_vanilla-1.7.jar`.
- **Mod id:** `lootintegrations_vanilla`.
- **Runtime metadata canônica:** `1`.
- **Filename/publicação:** `1.7`.
- **Minecraft/loader do pack:** 1.21.1 / NeoForge.
A coluna de versão permanece `1`, conforme a metadata física; `1.7` é preservado como versão do arquivo/publicação.

## 2. Papel no modpack
A publicação descreve o addon como uma camada que aumenta a variedade em **standard chest loot tables**. Quando um baú é gerado, o sistema pode escolher itens a partir de tabelas de loot comparáveis; como muitas estruturas modded reutilizam tabelas vanilla/padrão, o efeito pode alcançar conteúdo de terceiros sem que o addon possua essas estruturas.

## 3. Autoridade e ownership
**Loot Integrations 4.7** continua authority do mecanismo que carrega/aplica as integrações. Minecraft e cada mod de estrutura continuam authority de suas próprias loot tables e estruturas. Este addon fornece somente dados/regras de composição; não cria um segundo sistema de containers, não controla ownership de baús e não individualiza recompensas por jogador.

## 4. Escopo da publicação 1.7
O changelog oficial da publicação `1.7` registra **remoção do loot para Trail Ruins**. Portanto Trail Ruins não deve ser presumido como target ativo desta build. As fontes públicas consultadas não enumeram integralmente cada loot table interna da 1.7; esta ficha não inventa IDs ou pesos ausentes.

## 5. Server-side, dados e lifecycle
A página oficial classifica o addon como **server-side only**. Seu comportamento é relevante na resolução/fill de loot e em reload de recursos/datapacks do servidor. O cliente não determina os itens gerados. Alterações de dados devem ser verificadas após `/reload` e restart para detectar regra duplicada ou estado stale.

## 6. Interação com estruturas modded
Como tabelas vanilla/padrão são frequentemente reutilizadas, uma única regra pode afetar várias estruturas do pack. O impacto real depende de quais loot tables cada estrutura referencia; coexistência temática não prova integração direta. O addon não passa a ser owner da estrutura apenas porque seu loot final foi enriquecido.

## 7. Interação com Lootr e LootJS
**Lootr** atua na individualização/persistência do container por jogador; este addon atua na **composição** do loot. Em conjunto, o loot enriquecido pode ser instanciado separadamente para vários jogadores, multiplicando o impacto econômico. **LootJS**, datapacks e outros modifiers podem atingir as mesmas tabelas, criando risco de double injection ou ordem de transformação inesperada.

## 8. Riscos técnicos e de balanceamento
1. **Double injection** com LootJS/datapacks/outros modifiers.
2. **Economy inflation** por propagação através de tabelas muito reutilizadas.
3. **Progression leakage** se itens modded fortes aparecerem cedo em tabelas comparáveis amplamente acessíveis.
4. **Loot-table drift** após updates de Minecraft/mods consumidores.
5. **Reload stale/duplicado** durante desenvolvimento de datapacks.
6. **Multiplicação multiplayer** quando Lootr individualiza containers enriquecidos.
7. Presumir Trail Ruins como target apesar de a publicação 1.7 ter removido essa integração.

## 9. Matriz de testes
- [ ] Dedicated server inicia com `lootintegrations_vanilla` + Loot Integrations 4.7.
- [ ] Baús vanilla/padrão relevantes recebem variedade sem missing loot-table IDs.
- [ ] Estruturas modded que reutilizam essas tabelas continuam carregando normalmente.
- [ ] Trail Ruins não recebe a integração removida na 1.7.
- [ ] Cada fill aplica a regra uma única vez.
- [ ] `/reload` e restart não duplicam nem preservam regras antigas.
- [ ] LootJS/datapacks do pack não repetem o mesmo enriquecimento.
- [ ] Lootr mantém personalização por jogador sem alterar a composição além do esperado.
- [ ] A disponibilidade de itens modded permanece coerente com a progressão.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limites
Evidências utilizadas: modlist física atual; metadata extraída no inventário físico; página e arquivo oficiais do CurseForge para `lootintegrations_vanilla-1.7.jar`; changelog 1.7; Loot Integrations 4.7 e Cupboard 4.1 presentes no pack. A descrição pública confirma o comportamento geral e server-side, mas não fornece nesta consulta uma enumeração integral dos JSONs/weights da build; esses detalhes permanecem não afirmados.
