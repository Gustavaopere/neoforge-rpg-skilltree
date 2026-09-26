# Loot Integrations: Randomized Loot Compatibility

## Propriedades do registro

- **Mod:** Loot Integrations: Randomized Loot Compatibility
- **Arquivo JAR:** lootintegrations_vanilla-1.7.jar
- **Versão 1.21.1:** 1
- **Categoria:** Compat, Exploração
- **Tipo de conteúdo:** Addon
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vanilla-loot-addon-for-loot-integrations
- **Função:** Amplia variação das tabelas de loot vanilla/reutilizadas por estruturas, incorporando itens modded comparáveis através do Loot Integrations.
- **Dependências:** Loot Integrations 4.7; Cupboard 4.1 é dependência do framework base.
- **Compatibilidade/Riscos:** Riscos: mismatch entre publicação do JAR físico 1.7 e Minecraft 1.21.1; double injection com LootJS/datapacks; inflação econômica; progression leakage; loot-table drift; reload stale/duplicado e multiplicação com Lootr. A última release oficialmente marcada para 1.21.1 é 1.3.
- **Sobreposição:** Addon de composição de loot. Não substitui Lootr nem mods de estrutura; pode sobrepor LootJS/datapacks/outros modifiers sobre as mesmas tabelas.
- **Observações:** JAR físico `lootintegrations_vanilla-1.7.jar`, metadata runtime `1`. A publicação 1.7 está marcada para Minecraft 26.2; 1.3 é a última release explicitamente marcada para 1.21.1. A presença física de 1.7 é confirmada, mas a compatibilidade operacional com 1.21.1 não é presumida.
- **Procedência:** modlist.txt física reconferida em 13/09/2026 + listagem oficial CurseForge de Randomized Loot Compatibility, distinguindo 1.7/26.2 de 1.3/1.21.1 + Loot Integrations 4.7 já auditado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — JAR físico `lootintegrations_vanilla-1.7.jar` reconfirmado, metadata runtime `1`. CurseForge marca 1.7 para Minecraft 26.2; a última release explicitamente marcada para 1.21.1 é 1.3. Compatibilidade do JAR físico 1.7 com este pack 1.21.1 permanece pendente de teste de runtime.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #384: JAR `lootintegrations_vanilla-1.7.jar`, mod id `lootintegrations_vanilla`, metadata runtime `1`; filename/publicação `1.7`; SHA-1 `c545c5177ac6a7d49b88a80f2706da7085d7708d`.

<callout icon="🎲" color="orange_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_vanilla-1.7.jar`, mod id `lootintegrations_vanilla`, metadata runtime `1`; o filename é `1.7`. É um **addon server-side/data-driven de Loot Integrations**. **Divergência documentada:** a publicação oficial 1.7 está marcada para Minecraft 26.2, enquanto `1.3` é a última release explicitamente marcada para Minecraft 1.21.1. A presença física do 1.7 neste pack é confirmada; compatibilidade operacional 1.21.1 não é inferida como testada.
</callout>
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
## 4. Escopo e version gate da publicação 1.7
A publicação oficial `1.7` está marcada para Minecraft **26.2**, não 1.21.1; a última release explicitamente marcada para 1.21.1 é `1.3`. O changelog da publicação 1.7 registra **remoção do loot para Trail Ruins**, mas essa informação descreve a linhagem/publicação 1.7 e não prova, por si só, compatibilidade do artefato físico com o runtime 1.21.1. Trail Ruins não deve ser presumido como target ativo, e nenhum ID/peso interno não auditado é inventado.
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
