# RunicLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818cbed8d00a748aed1f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `neoforge-runiclib-1.21.1-5.0.7.jar`, mod id `runiclib`, runtime `5.0.7`, mixins `runiclib.neoforge.mixins.json` e `runiclib.mixins.json`; Dungeon's Delight 1.5.0 presente como consumer causal
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, RunicLib 5.0.7 e Dungeon's Delight 1.5.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** RunicLib
- **Arquivo JAR:** `neoforge-runiclib-1.21.1-5.0.7.jar`
- **Versão 1.21.1:** 5.0.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Magia
- **Função:** Library multiloader AZURUNE com serviços/APIs compartilhados, attributes/effects e helpers; 5.0.7 adiciona RLTrade para registro comum de villager/wandering-trader trades.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado: Dungeon's Delight 1.5.0; linha recente exige RunicLib >=5.0.0. RunicLib 5.0.7 satisfaz o mínimo.
- **Sobreposição:** Não substituível por libraries genéricas; consumers dependem de APIs próprias. É dependency operacional enquanto Dungeon's Delight permanecer.
- **Compatibilidade/Riscos:** Dependency Client & Server de Dungeon's Delight. Riscos: API/major-line drift, attribute/effect stacking, RLTrade duplication, mixin overlap e consumer attribution. Dungeon's Delight recente exige RunicLib >=5.0.0; pack usa 5.0.7.
- **Observações:** Runtime 5.0.7. Mixins físicos `runiclib.mixins.json` e `runiclib.neoforge.mixins.json`. RLTrade pertence à API 5.0.7; uso efetivo por um consumer não é presumido sem source.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais RunicLib 5.0.7 + dependency oficial de Dungeon's Delight.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/runiclib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — RunicLib 5.0.7 reconstruído e reclassificado como Dependência: Dungeon's Delight é consumer físico confirmado; APIs 5.x, RLTrade, attributes/effects, mixins, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência após confirmação causal: Dungeon's Delight 1.5.0 está instalado e lista RunicLib como Required Dependency; a linha 1.4.4+ exige RunicLib 5.0.0+.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `neoforge-runiclib-1.21.1-5.0.7.jar`, mod id `runiclib`, versão `5.0.7`, NeoForge 1.21.1. RunicLib é uma library multiloader do ecossistema AZURUNE/Yirmiri. Neste pack ela deixa de ser apenas “biblioteca presente”: **Dungeon's Delight 1.5.0 é consumer confirmado** e a linha recente desse addon exige RunicLib 5.0.0+. Portanto a decisão correta é **Dependência**.

## 1. Identidade e papel
- **Mod:** RunicLib.
- **JAR físico:** `neoforge-runiclib-1.21.1-5.0.7.jar`.
- **Mod id:** `runiclib`.
- **Runtime:** `5.0.7`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/ecossistema:** Yirmiri / AZURUNE.
- **Ambiente:** Client & Server.
- **Papel:** biblioteca/utilitário multiloader para compartilhar APIs, atributos, efeitos e serviços entre mods consumidores.
- **Decisão:** Dependência.

## 2. Consumer confirmado: Dungeon's Delight
O pack contém `neoforge-dungeonsdelight-1.21.1-1.5.0.jar`. O projeto Dungeon's Delight lista **RunicLib como Required Dependency**, e a linha 1.4.4+ estabelece RunicLib **5.0.0+**.

O runtime atual usa RunicLib 5.0.7, portanto satisfaz esse mínimo.

Consequência:
- não remover RunicLib enquanto Dungeon's Delight permanecer;
- updates da library precisam de regressão do consumer;
- um crash em RunicLib não deve ser atribuído automaticamente à library sem verificar uso pelo consumer.

## 3. Escopo da library
A descrição oficial caracteriza RunicLib como conjunto de utilities multiloader e infraestrutura para modders, incluindo **novos attributes/effects** e helpers comuns.

Isso é importante para ownership:
- RunicLib pode registrar/fornecer tipos compartilhados;
- o consumer decide quando e como esses tipos entram no gameplay;
- items, foods, mobs e regras do Dungeon's Delight continuam pertencendo ao addon, não à library.

## 4. RLTrade — release 5.0.7
O changelog exato 5.0.7 adiciona **RLTrade**, permitindo registro comum de trades de villagers e wandering traders.

A documentação da release cita services como:
- `RLServices.REGISTRY.registerVillagerTrade()`;
- `RLServices.REGISTRY.registerWanderingTrade()`.

Esses nomes são evidência publicada da API 5.0.7. Não se presume que Dungeon's Delight use especificamente RLTrade sem source/manifest do consumer demonstrando isso.

## 5. Linha 5.x e compatibilidade
A linha 5.0 representa uma mudança de geração da library. O fato de Dungeon's Delight recente exigir 5.0.0+ demonstra que APIs antigas 4.x não devem ser tratadas como equivalentes drop-in.

Para atualizações:
- validar ranges dos consumers;
- evitar downgrade para 4.x apenas porque outro mod antigo menciona RunicLib;
- testar todos os consumers instalados após mudança de major/minor da library.

## 6. Mixins físicos
A modlist registra:
- `runiclib.mixins.json`;
- `runiclib.neoforge.mixins.json`.

Logo a distribuição instalada não é apenas um conjunto passivo de classes. Há transformação common/NeoForge. Sem pin de source byte-equivalente para cada alvo, esta ficha não inventa classes/métodos transformados.

## 7. Client/server e lifecycle
CurseForge classifica RunicLib como Client & Server. O side efetivo de cada utilidade depende do consumer.

Superfícies relevantes:
- registration lifecycle;
- attributes/effects compartilhados;
- event/services usados por consumers;
- trade registration;
- datapack/reload quando consumers ligam dados à API.

Qualquer estado de gameplay continua server-authoritative conforme o sistema consumidor.

## 8. Atributos e efeitos
Como a library publica novos attributes/effects para modders, há risco de composição com:
- AttributeFix;
- sistemas RPG/skills;
- efeitos de foods/combat;
- outros mods que mexem em caps/operations.

Não atribuir um modificador específico a RunicLib sem identificar o registry ID e consumer real que o aplica.

## 9. Trades
RLTrade cria uma superfície de integração com villagers/wandering traders. Riscos:
- trade duplicado por registro em mais de um lado/evento;
- pool alterado por outro mod/datapack;
- consumer esperando API antiga;
- reload/restart produzindo composição diferente.

Testes de trade só são obrigatórios quando algum consumer do pack usa essa API.

## 10. Dependências próprias
A página oficial de RunicLib não publica dependencies externas obrigatórias além da plataforma suportada. Isso não contradiz sua classificação como `Dependência`: o vínculo é inverso — **Dungeon's Delight depende de RunicLib**.

## 11. Sobreposição
RunicLib pode parecer conceitualmente semelhante a outras libraries, mas não é intercambiável com MonoLib, Moonlight, Architectury, Balm etc. Consumers compilam contra APIs/registries específicos.

“Há outra biblioteca no pack” nunca é justificativa suficiente para removê-la.

## 12. Riscos
1. **Consumer API drift:** Dungeon's Delight ou outro consumer espera assinatura/comportamento diferente.
2. **Major-line mismatch:** 4.x e 5.x não devem ser tratados como equivalentes.
3. **Attribute/effect stacking:** tipos compartilhados podem interagir com RPG/AttributeFix.
4. **Trade duplication:** consumers de RLTrade precisam registrar uma vez.
5. **Mixin overlap:** common/NeoForge mixins podem cruzar outros patches.
6. **Attribution error:** stacktrace na library pode ser consequência de consumer incompatível.
7. **Removal breakage:** remover RunicLib isoladamente pode impedir Dungeon's Delight de carregar.

## 13. Matriz de testes
- [ ] Dedicated server inicia com RunicLib 5.0.7 + Dungeon's Delight 1.5.0.
- [ ] Cliente conecta sem version/API mismatch.
- [ ] Dungeon's Delight registra items/effects/recipes sem missing class/service.
- [ ] Fluxos principais de Dungeon's Delight executam após restart e `/reload`.
- [ ] Attributes/effects compartilhados não são aplicados duas vezes após relog.
- [ ] Se houver consumer de RLTrade, villager/wandering trades registram uma vez e persistem coerentemente.
- [ ] Atualização futura da RunicLib é testada com todos os consumers identificados, não apenas boot isolado.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `neoforge-runiclib-1.21.1-5.0.7.jar`, mod id/runtime e dois mixin configs.
- CurseForge oficial: RunicLib 5.0.7, Client & Server, utilities/attributes/effects para modders.
- Changelog 5.0.7: RLTrade e APIs de registro de trades.
- Dungeon's Delight físico 1.5.0: relation oficial exige RunicLib; linha 1.4.4+ estabelece mínimo 5.0.0.
- **Limite:** não foi inferido que um consumer use RLTrade ou qualquer attribute específico sem evidência direta.
