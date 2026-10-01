# Loot Integrations: Integrated Structures

## Propriedades do registro

- **Mod:** Loot Integrations: Integrated Structures
- **Arquivo JAR:** lootintegrations_integrated-1.5.jar
- **Versão 1.21.1:** 1
- **Categoria:** Compat, Worldgen, Exploração
- **Tipo de conteúdo:** Addon
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/loot-integrations-integrated-dungeons-villages
- **Função:** Integra e enriquece loot de IDAS, Integrated Villages, Integrated Stronghold e Integrated Cataclysm por meio do Loot Integrations.
- **Dependências:** Loot Integrations 4.7 + stack Integrated presente; Cupboard 4.1 é dependência do framework base.
- **Compatibilidade/Riscos:** Riscos: double injection com LootJS/datapacks, inflação econômica pela densidade de estruturas Integrated, drift de loot-table IDs, reload stale/duplicado, target opcional ausente e multiplicação do impacto com Lootr. Upstream 1.6 adiciona especificamente loot do Pumpkin Cafe.
- **Sobreposição:** Bridge data-driven sobre loot de estruturas Integrated. Não substitui os mods Integrated nem Lootr; pode sobrepor LootJS/datapacks/outros modifiers nas mesmas tabelas.
- **Observações:** Filename/publicação física: 1.5; metadata runtime canônica no JAR físico: 1. Upstream publicou **1.6** para 1.21.1 em 19/09/2026; preservar metadata/runtime instalada separadamente da versão publicada.
- **Procedência:** modlist física atual + CurseForge oficial Loot Integrations: Integrated Dungeons, Villages & Strongholds & Cataclysm 1.5/1.6 + framework Loot Integrations 4.7 já auditado.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — JAR físico permanece `lootintegrations_integrated-1.5.jar` / metadata runtime `1`. A publicação **1.6** adiciona Pumpkin Cafe loot e não está instalada.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #383: JAR `lootintegrations_integrated-1.5.jar`, mod id `lootintegrations_integrated`, metadata runtime `1`; filename/publicação `1.5`; SHA-1 `12c262276385db1dc3188fbcb1aedff15550208b`.

<callout icon="🏰" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_integrated-1.5.jar`. A metadata runtime do JAR registra versão `1`; o filename/publicação é `1.5`. É um **addon data-driven de Loot Integrations** voltado à família Integrated de estruturas, sem assumir ownership dessas estruturas ou do framework de loot.
</callout>
## 1. Identidade e versionamento
- **JAR:** `lootintegrations_integrated-1.5.jar`.
- **Runtime metadata canônica:** `1`.
- **Filename/publicação:** `1.5`.
A coluna de versão permanece `1`, conforme o artefato físico; `1.5` fica documentado como versão de publicação/arquivo.
## 2. Escopo de integração
A publicação cobre a família **Integrated Dungeons and Structures**, **Integrated Stronghold**, **Integrated Villages** e **Integrated Cataclysm**, permitindo que tabelas dessas estruturas recebam conteúdo integrado pelo Loot Integrations. O addon não exige conceitualmente que todos os targets possíveis estejam instalados para ter identidade própria; só há efeito nos alvos disponíveis.
## 3. Stack físico relacionado
O pack contém a família Integrated, incluindo `integrated_api` 1.8.2, `integrated_cataclysm` 1.0.6, `integrated_stronghold` 1.1.4 e `integrated_villages` 1.3.3. Cada projeto continua authority de suas próprias estruturas, placements e loot tables.
## 4. Framework base
Loot Integrations 4.7 carrega os JSONs e aplica a composição server-side. Cupboard 4.1 é dependência do framework. O addon fornece dados/compatibilidade; não implementa um segundo loader de loot.
## 5. Ausência de hard dependency universal
Como a bridge atende vários targets, a ausência de um integrante específico da família deve resultar apenas na ausência daquela integração, não em atribuição de conteúdo ao addon. IDs opcionais precisam ser tratados conforme os dados efetivos da publicação.
## 6. Economia e densidade estrutural
O pack já possui grande densidade de estruturas. Enriquecer várias famílias simultaneamente pode aumentar a disponibilidade agregada de materiais/equipamentos muito mais do que uma única integração isolada. Balanceamento deve ser avaliado por hora de exploração e não apenas por baú individual.
## 7. Lootr e outros modifiers
Lootr pode individualizar containers enriquecidos por jogador. LootJS/datapacks podem alterar as mesmas tabelas. Portanto os gates são double injection, ordem, multiplicação multiplayer e distribuição excessiva de itens modded.
## 8. Riscos
1. IDs/tabelas de uma família Integrated mudarem sem atualização da bridge.
2. Double injection via LootJS/datapacks.
3. Economia inflada pela grande quantidade de estruturas-alvo.
4. Reload stale ou modifier duplicado.
5. Lootr multiplicar a disponibilidade global por jogador.
6. Ausência de target opcional gerar warnings/erros se os dados não forem devidamente condicionais.
## 9. Matriz de testes
- [ ] Server inicia com addon + Loot Integrations 4.7 e stack Integrated físico.
- [ ] Stronghold/Villages/Cataclysm e demais targets presentes resolvem tabelas válidas.
- [ ] Target ausente não causa crash de datapack.
- [ ] `/reload` não duplica integração.
- [ ] LootJS não repete o mesmo enriquecimento.
- [ ] Lootr mantém um fill coerente por jogador.
- [ ] Densidade global de recompensas continua compatível com a progressão.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências e limites
Fontes: modlist física, publicação `lootintegrations_integrated-1.5.jar`, descrição oficial dos targets e framework Loot Integrations 4.7 auditado. Não foram inventadas quantidades/probabilidades de JSON não lidos integralmente.


## 11. Atualização upstream 1.6 — não instalada
A autoridade física continua em **arquivo/publicação 1.5**, metadata runtime `1`.

O changelog oficial da **1.6** contém um único delta: **adiciona loot para Pumpkin Cafe**.

Impacto: trata-se de ampliação data-driven de target/table. Confirmar que o Pumpkin Cafe realmente existe no stack de estruturas correspondente antes de atribuir efeito local. Com Lootr, qualquer novo loot de estrutura pode ser instanciado por jogador e amplificar disponibilidade econômica.

Gate de promoção 1.5→1.6: datapack load; tabela/ID do Pumpkin Cafe resolve; target ausente não crasha; `/reload` não duplica injection; LootJS coexistence; Lootr com dois jogadores; estrutura nova vs chunks antigos.

Fonte upstream: CurseForge file ID 8922518, `lootintegrations_integrated-1.6.jar`.