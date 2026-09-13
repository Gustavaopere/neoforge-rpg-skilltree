# Epic Fight x Iron's Spells add-ons compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8108b912efaae3dcbfd8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `epicfight_ironsspells_addons_compatibility_pack.zip`
- **Versão própria:** não publicada; `1.21.1` é a versão do jogo, não versão semântica do datapack
- **Data da exportação:** 2026-09-11

## Autoridade física e evidências na exportação

- O dossiê Notion registra captura da pasta Data Packs do perfil em 08/09/2026 com `epicfight_ironsspells_addons_compatibility_pack.zip` instalado.
- Evidência histórica adicional na Biblioteca: `minecraftinstance.json` registra o arquivo habilitado em `datapacks`, com gameVersion `1.21.1`; logs de agosto mostram o servidor detectando/carregando o pack.
- Esses logs também mostram warnings de empacotamento por namespace inválido `.DS_Store`/conteúdo `__MACOSX`. Isso é evidência histórica concreta de lixo de empacotamento sendo ignorado pelo loader; **não prova quebra dos mappings funcionais**.
- A modlist física JAR-centric de 08/09 serve para confirmar os mods-alvo, não para inventariar diretamente este ZIP.

## Propriedades do banco

- **Mod:** Epic Fight x Iron's Spells add-ons compat: Somake Spells, Magic From the East, Geomancy Plus, Deeper and Darker Spellbooks
- **Arquivo JAR:** `epicfight_ironsspells_addons_compatibility_pack.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia, RPG
- **Função:** Data pack que fornece compatibilidade Epic Fight para armas de Somake Spells, Magic From the East, Geomancy Plus e Deeper and Darker Spellbooks.
- **Dependências:** Epic Fight 21.17.3.1 + Iron's Spells 3.16.3 + Somake Spells 1.0.8 + Magic From The East 1.1.5 + GTBC's Geomancy Plus 1.1.0-1.21.1 + Deeper and Darker: Spellbooks 1.3.3-1.21.1.
- **Sobreposição:** Pode se sobrepor a outros datapacks/configs Epic Fight que atribuam weapon categories/styles aos mesmos item IDs. Mods alvo continuam authorities de spells, stats e conteúdo.
- **Compatibilidade/Riscos:** Compat depende das versões atuais de Epic Fight e dos quatro addons alvo. Mudanças em weapon IDs/categories podem quebrar mappings. Não altera spells ou stats por si só; datapack precisa recarregar no servidor/mundo.
- **Observações:** Arquivo instalado `epicfight_ironsspells_addons_compatibility_pack.zip`, release para Minecraft 1.21.1 sem versão semântica própria. O antigo valor `1.21.1` no campo de versão era a versão do jogo, não do datapack.
- **Procedência:** CurseForge oficial do datapack por Detron100 + captura Data Packs do perfil em 08/09/2026 + modlist física atual dos cinco mods envolvidos.
- **Fonte:** https://www.curseforge.com/minecraft/data-packs/epic-fight-x-irons-spells-add-ons-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — datapack 1.21.1 sem versão semântica, Epic Fight 21.17.3.1 + quatro addons Iron's, weapon-style authority, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Data pack físico confirmado no dossiê de origem:** `epicfight_ironsspells_addons_compatibility_pack.zip`, publicado para Minecraft 1.21.1 e sem versão semântica própria. O valor `1.21.1` não deve ser usado como versão do datapack.

## 1. Papel e authority
Este datapack fornece definições de compatibilidade do **Epic Fight** para armas adicionadas por quatro addons de Iron's Spells. Epic Fight continua authority do sistema de estilos/animações de combate; cada addon continua authority dos próprios itens, stats e conteúdo mágico.

## 2. Alvos confirmados
O projeto declara compatibilidade para armas de **Somake Spells**, **Magic From the East**, **Geomancy Plus** e **Deeper and Darker Spellbooks**. Todos os quatro alvos estão fisicamente presentes no perfil atual.

## 3. Stack físico
A cadeia atual é Epic Fight `21.17.3.1`, Iron's Spells `3.16.3`, Somake Spells `1.0.8`, Magic From The East `1.1.5`, GTBC's Geomancy Plus `1.1.0-1.21.1` e Deeper and Darker: Spellbooks `1.3.3-1.21.1`.

## 4. Boundary funcional
O datapack pode mapear armas para categories/styles/configurações consumidas pelo Epic Fight, mas **não cria nem redefine por si só spells, mana, damage base, recipes ou registry ownership** dos addons.

## 5. Lifecycle de datapack
É conteúdo de dados do mundo/servidor. Alterações exigem recarga apropriada de datapacks ou reentrada/restart conforme o ambiente. Falha de mapping deve degradar a compatibilidade de combate, não transferir authority dos itens ao datapack.

## 6. Sobreposição e riscos
1. Item IDs/categories mudarem em qualquer addon alvo.
2. Epic Fight alterar formato ou semântica das definições consumidas.
3. Outro compat datapack atribuir estilo diferente ao mesmo item.
4. Datapack não estar habilitado no mundo correto.
5. Reload parcial deixar definição antiga ativa até nova carga.

## 7. Matriz de testes
- [ ] Armas de Somake Spells em Battle Mode.
- [ ] Armas de Magic From The East.
- [ ] Armas de Geomancy Plus.
- [ ] Armas de Deeper and Darker Spellbooks.
- [ ] Conferir styles/animations esperados em primeira e terceira pessoa.
- [ ] Reload/relog/restart sem warnings de item ID ou datapack.
- [ ] Confirmar que remover o datapack não altera spell registry ou stats base dos itens.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma o arquivo 1.21.1 e os quatro addons-alvo. Não há versão semântica própria publicada, portanto o campo de versão foi limpo em vez de repetir a versão do jogo.

> Boundary canônico: **Epic Fight controla o sistema de combate; os addons controlam suas armas/spells; o datapack fornece apenas a camada de compatibilidade entre esses IDs e estilos**.

## Evidência histórica adicional de empacotamento
Logs de agosto do perfil registram warnings `Non [a-z0-9_.-] character in namespace .DS_Store` para este ZIP. O `minecraftinstance.json` também mostra um módulo `__MACOSX`. Esses artefatos são tratados como sujeira de empacotamento ignorada pelo loader até que runtime QA demonstre impacto funcional; não são convertidos em incompatibilidade presumida.
