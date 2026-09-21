# Dynamic Trees Addon Lib

## Propriedades do registro

- **Mod:** Dynamic Trees Addon Lib
- **Arquivo JAR:** `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar`
- **Versão 1.21.1:** `0.2.0-BETA03`
- **Categoria:** Biblioteca, Compat
- **Função:** Biblioteca compartilhada do ecossistema Dynamic Trees para addons/treepacks, centralizando gen features, growth logic kits, cell kits, tipos customizados e modelos/utilidades reutilizáveis.
- **Dependências:** Dynamic Trees. Não adiciona gameplay independente; é infraestrutura consumida por addons/treepacks. Build física 0.2.0-BETA03 para NeoForge 1.21.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Biblioteca beta e altamente acoplada à API/data model do Dynamic Trees. Riscos: addon compilado contra versão diferente, registro de type/kit ausente, invalid packet em fruit/falling state, data pack referenciando type removido e remoção isolada quebrando consumidores.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamic-trees-addon-lib
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar`, mod id `dtaddon_lib` e runtime `0.2.0-BETA03`. Fonte oficial/changelog BETA03 permanecem a base técnica do dossiê.
- **Observações:** BETA03 permanece current para NeoForge 1.21.1. Continua sendo library de gen features/growth logic/cell kits/custom types; não deve ser tratada como provider independente de árvores.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #233: `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar` / runtime `0.2.0-BETA03` reconfirmados na modlist física atual de 587 mods incluindo o modloader; permanece a build NeoForge 1.21.1 catalogada.
- **Decisão:** Dependência
- **Histórico da decisão:** Mantido como infraestrutura do stack Dynamic Trees. Em 22/08/2026 o usuário confirmou a permanência de Dynamic Trees + addons; esta biblioteca não concorre com ArborFirmaCraft e deve ser tratada conforme os treepacks dependentes.
- **Sobreposição:** Não substitui Dynamic Trees core nem treepacks. Seu papel é reduzir código duplicado e oferecer contratos comuns aos addons.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `DynamicTrees-AddonLib-DTteam-neoforge-1.21.1-0.2.0-BETA03.jar` · mod id `dtaddon_lib` · versão `0.2.0-BETA03` · NeoForge 1.21.1.
## 1. Papel no modpack
Dynamic Trees Addon Lib é uma **biblioteca de desenvolvimento/runtime** para addons e treepacks do Dynamic Trees. Ela centraliza comportamentos reutilizáveis que, de outra forma, seriam copiados entre bridges de biomas/árvores.
Ela não deve ser catalogada como mod de árvores independente: sem consumidores, a library não representa um segundo engine de growth/worldgen.
## 2. Authority / ownership
- **Dynamic Trees:** engine principal de species/family/growth/worldgen/felling.
- **Addon Lib:** tipos e utilidades compartilhadas expostas aos addons.
- **Treepacks/addons consumidores:** suas species, adapters e dados concretos.
Uma integração externa deve consumir o tipo/provider correto; não duplicar a lógica da library em scripts paralelos.
## 3. Superfícies oficiais reutilizáveis
A documentação oficial apresenta a library como fonte de componentes como:
- **custom gen features**;
- **growth logic kits**;
- **cell kits**;
- **custom types**;
- modelos/utilidades genéricos para addons.
Exemplos de uso incluem gen features para vines/fruits, growth logic para formatos não triviais, cell kits para comportamento de folhas e tipos especiais para ambientes/comportamentos customizados.
## 4. Build BETA03
A build física é `0.2.0-BETA03`. O changelog oficial registra, entre outros pontos:
- atualização para a linha recente de Dynamic Trees;
- correção de invalid packet envolvendo falling fruit;
- modelos genéricos de fruit;
- remoção de `GenOnStone` species;
- remoção de cypress species;
- separação de Lament em comportamentos `GrowOnLava` e `PlaceAlternate`;
- limpeza interna.
Mudanças de type/kit são relevantes para addons que referenciam ResourceLocations antigos.
## 5. Gen features
Gen features são peças data/code-driven executadas dentro do pipeline Dynamic Trees para modificar geração/crescimento. Consumidores podem acrescentar decoração, vines, fruit ou outras transformações sem reimplementar o engine.
O provider que declara a feature continua responsável por seus IDs e dados; Addon Lib fornece o contrato compartilhado.
## 6. Growth logic kits
Growth logic kits alteram a tomada de decisão de crescimento de branches. Como afetam geometria e continuidade da árvore, version drift pode produzir formas inválidas ou incompatibilidade com species antigas.
Scripts externos não devem editar branches em paralelo a um growth kit sem entender o lifecycle do Dynamic Trees.
## 7. Cell kits
Cell kits participam da lógica de leaves/canopy ao redor de branches. Mudanças nessa camada podem gerar folhas que não persistem, canopy incorreto ou atualização excessiva. O estado final continua sob a engine Dynamic Trees.
## 8. Custom types
A library expõe custom types para casos que não cabem nos tipos base. A BETA03 remove/reorganiza alguns helpers, portanto addons compilados contra builds anteriores precisam ser tratados como version-sensitive.
## 9. Fruits e packets
A correção de falling fruit/invalid packet na BETA03 torna fruit state e networking uma regressão concreta. Fruit spawn/fall/collect deve ser liquidado uma vez no servidor e sincronizado ao cliente.
## 10. Client / Server
A library é Client & Server porque consumers podem usar seus tipos em worldgen/gameplay e seus assets/model helpers no cliente. Código server-side não deve carregar render-only classes; packets devem respeitar side/context.
## 11. Lifecycle
Validar:
- mod registry boot;
- carregamento de treepacks/datapacks;
- resolução de custom types;
- resource/data reload;
- world creation;
- chunk generation;
- growth/felling;
- fruit spawn/fall;
- save/restart;
- atualização da library sem atualizar todos os consumidores.
## 12. Multiplayer / idempotência
Qualquer state criado por gen feature/growth/fruit precisa ser autoritativo no servidor. Um mesmo consumer não pode registrar type/feature duas vezes nem processar fruit/felling em ambos os lados.
## 13. Riscos
1. consumer compilado contra API diferente;
2. type/kit removido ou renomeado;
3. datapack com ResourceLocation stale;
4. packet inválido de fruit/falling state;
5. duplicate registration;
6. custom type carregando classe client no servidor;
7. reload deixando registry/cache divergente;
8. remover a library isoladamente e quebrar dependentes;
9. confundir library com gameplay provider;
10. beta regression em consumers não testados.
## 14. Matriz de testes
1. Dedicated server boot com Dynamic Trees 1.7.2.
2. Carregar todos os treepacks/bridges atuais sem missing type.
3. Gerar chunks com consumers diferentes.
4. Testar species que usam growth logic customizada.
5. Testar leaves/cell kits.
6. Testar fruit spawn/fall/collect e reconnect.
7. Datapack reload.
8. Server restart com árvores parcialmente crescidas.
9. Smoke-test de todos os bridges após update de Addon Lib.
10. Remoção controlada em cópia de teste para identificar dependentes — nunca no save principal.
**Esta catalogação não afirma que esses testes foram executados.**
## 15. Evidências
- modlist física atual de 21/09/2026: JAR/mod id/versão;
- publicação oficial Dynamic Trees Addon Lib: escopo de gen features, growth logic kits, cell kits e custom types;
- changelog oficial 0.2.0-BETA03.
> **Boundary canônico:** Addon Lib fornece **contratos reutilizáveis**. Dynamic Trees continua sendo o engine e cada addon continua dono do conteúdo que registra.
