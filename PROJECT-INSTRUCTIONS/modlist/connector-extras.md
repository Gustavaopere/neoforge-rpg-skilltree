# Connector Extras

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b3a369e7060001f434
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Connector Extras
- **Arquivo JAR:** `ConnectorExtras-1.12.1+1.21.1.jar`
- **Versão 1.21.1:** `1.12.1+1.21.1`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Connector Extras 1.12.1+1.21.1, bridges de energia/recipe viewers/config/TerraBlender, lifecycle e necessidade condicional confirmados no QC global #106. Decisão Opcional preservada; runtime QA não executado.
- **Categoria:** Compat; Biblioteca
- **Compatibilidade/Riscos:** Camada condicional e ampla de bridges. Riscos: dupla conversão FE/Energy, dois plugin detectors registrando o mesmo plugin, config screen duplicada, bridge sem consumer e version drift com Connector/API alvo. A bridge Team Reborn Energy usa razão default 1 E : 10 FE, configurável por direção; 0 desativa a direção.
- **Decisão:** Opcional
- **Dependências:** Required funcionalmente: Sinytra Connector. Os módulos só têm utilidade quando um mod Fabric carregado pelo Connector usa a API/integração correspondente; não inferir necessidade pela simples presença dos bridges.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/connector-extras/files/5618470
- **Função:** Pacote de bridges adicionais para Sinytra Connector que adapta integrações Fabric↔Forge/NeoForge em áreas como energia, recipe viewers, TerraBlender, Mod Menu/config screens e Forge Config API Port.
- **Histórico da decisão:** Em 06/09/2026 foi registrada decisão formal `Opcional`: Connector Extras deve permanecer apenas enquanto houver necessidade real do Sinytra Connector por consumidores Fabric. Se teste A/B provar o Connector dispensável, remover Extras junto. Essa decisão não autoriza remover Forgified Fabric API, que pode ter consumidores NeoForge nativos independentes.
- **Observações:** JAR físico `ConnectorExtras-1.12.1+1.21.1.jar`. O host embarca módulos 1.12.1+1.21.1 para TerraBlender, REI, Pehkui, Mod Menu, KubeJS, JEI, Energy e EMI, além de `extras-utils`; são componentes jar-in-jar e não entradas top-level. Contracts detalhados permanecem limitados às bridges sustentadas pelas fontes auditadas.
- **Procedência:** modlist.txt física atual de 08/09/2026 + runtime 1.12.1+1.21.1 + CurseForge oficial File ID 5618470 + source/documentação oficial Sinytra/ConnectorExtras para módulos e contracts.
- **Sobreposição:** Complementa Sinytra Connector com bridges específicas; não substitui o Connector nem as APIs-alvo. Uma bridge só deve processar a integração correspondente uma vez.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🔗 Versão física confirmada: `ConnectorExtras-1.12.1+1.21.1.jar`, runtime `1.12.1+1.21.1`, NeoForge 1.21.1. É um **pacote de bridges para Sinytra Connector** e possui decisão formal `Opcional` desde 06/09/2026.

## 1. Papel e authority
Connector Extras amplia Sinytra Connector com adaptações específicas entre APIs/ecossistemas Fabric e Forge/NeoForge. Ele não é um loader independente e não deve executar uma segunda tradução completa do mod Fabric.
Sinytra Connector continua authority da camada de carregamento/transformação; cada API/provider alvo continua authority de sua semântica final.

## 2. Necessidade condicional
A presença dos módulos internos não prova que o pack possua um consumer Fabric que realmente os use. A necessidade deve ser resolvida por dependency/runtime evidence:
- identificar mods carregados pelo Connector;
- identificar quais APIs Fabric eles requerem;
- manter somente a bridge necessária ao conjunto real.
A decisão `Opcional` registra exatamente essa condição.

## 3. Team Reborn Energy ↔ Forge Energy
O projeto implementa bridge entre Team Reborn Energy API e Forge Energy. A documentação publica razão default **1 E : 10 FE** e permite configurar os ratios por direção; valor `0` desabilita uma direção.
Conversão deve ocorrer exactly once. Outra bridge/script não pode converter o mesmo transfer novamente ou criar energia por round-trip/rounding.

## 4. REI plugin detector
Connector Extras fornece adaptação para detectar/carregar plugins Fabric de **Roughly Enough Items** no ambiente Forge/NeoForge quando aplicável.
Recipe viewer é apresentação/integração; recipes reais continuam sob authority do recipe manager/provider.

## 5. JEI plugin detector
Existe bridge equivalente para plugins Fabric de **JEI**. Não registrar o mesmo plugin duas vezes caso o mod já tenha implementação NeoForge nativa ou outro adapter ativo.
Duplicação costuma aparecer como categorias/recipes duplicados ou callbacks registrados duas vezes.

## 6. TerraBlender bridge
O pacote inclui bridge para integração de mods Fabric que esperam TerraBlender/contratos relacionados em ambiente Connector.
Worldgen final continua pertencendo ao provider/mod Fabric + stack de worldgen; Connector Extras não deve ser catalogado como biome provider próprio.

## 7. Mod Menu config screen factory
O projeto adapta config screen factories do ecossistema **Mod Menu** para superfícies equivalentes na lista de mods Forge/NeoForge.
Abrir uma screen é client presentation. Persistência/validação de config continua pertencendo ao consumer/config backend.

## 8. Forge Config API Port → NeoForge config
Connector Extras possui bridge para mods Fabric que usam **Forge Config API Port**, conectando-os à infraestrutura de configuração compatível do lado Forge/NeoForge.
Não assumir sync universal: cada consumer ainda decide client/common/server config e validation.

## 9. Inventário físico de módulos embarcados — 1.12.1+1.21.1
A inspeção da modlist física do host `ConnectorExtras-1.12.1+1.21.1.jar` expõe componentes jar-in-jar da mesma linha de versão para:
- TerraBlender bridge (`connectorextras_terrablender_bridge`);
- REI bridge (`connectorextras_rei_bridge`);
- Pehkui bridge (`connectorextras_pehkui_bridge`);
- Mod Menu bridge (`connectorextras_modmenu_bridge`);
- KubeJS bridge (`connectorextras_kubejs_bridge`);
- JEI bridge (`connectorextras_jei_bridge`);
- Energy bridge (`connectorextras_energy_bridge`);
- EMI bridge (`connectorextras_emi_bridge`);
- `extras-utils`, helper embarcado cuja linha física não expõe mod id próprio.
Esses componentes **não são top-level** e não recebem páginas independentes. A presença física confirma identidade/versionamento do módulo, mas não autoriza inventar o contract de Pehkui, KubeJS, EMI ou helpers sem documentação/source pin suficiente. As bridges de Energy, REI, JEI, TerraBlender, Mod Menu e Forge Config API Port permanecem descritas nas seções anteriores porque suas funções possuem suporte documental auditado.
Regra fail-closed: módulo fisicamente presente com contract não suficientemente sustentado é registrado como componente do host, sem promover comportamento inferido.

## 10. Relação com Forgified Fabric API
Forgified Fabric API pode ser necessária por mods Fabric, mas sua presença **não prova necessidade do Sinytra Connector** no pack: existem consumers NeoForge nativos que também podem usá-la.
A decisão de remover Connector/Extras nunca deve arrastar FFAPI sem mapear seus consumers próprios.

## 11. Client/server
A release é Client & Server. Alguns módulos são majoritariamente client-facing (recipe viewers/config screens); outros afetam common/server (energy/worldgen/config contracts).
Cada bridge precisa respeitar o side da API alvo e não carregar UI no dedicated server.

## 12. Lifecycle
Validar bootstrap do Connector, descoberta dos mods Fabric, registro dos adapters, recipe/resource/datapack reload conforme cada módulo, world join, reconnect e restart.
Adapters não podem registrar duas vezes após reload nem manter referências stale a plugin/config/world anterior.

## 13. Version drift
Connector Extras depende da evolução simultânea de Connector, NeoForge e APIs Fabric/Forge alvo. Sintomas podem incluir mixin/transform error, plugin não detectado, duplicate plugin, config screen ausente, worldgen bootstrap failure ou energy bridge inconsistente.
Atualização isolada deve ser testada contra o conjunto real de consumers.

## 14. Riscos
1. Bridge instalada sem consumer real.
2. Conversão E↔FE aplicada duas vezes.
3. Round-trip de energia gerar/perder saldo inesperadamente.
4. JEI/REI plugin duplicado.
5. Config screen registrada duas vezes.
6. TerraBlender/worldgen adapter incompatível.
7. API alvo atualizar e quebrar adapter.
8. Remover FFAPI apenas porque Connector saiu.

## 15. Matriz de testes
1. Dedicated server boot com Connector + Extras.
2. Enumerar quais mods Fabric foram realmente transformados/carregados.
3. Para cada módulo ativo, provar consumer correspondente.
4. Energy bridge: transfer E→FE e FE→E, inclusive ratio 0/config custom.
5. JEI/REI: plugins uma única vez, recipes sem duplicação.
6. TerraBlender consumer em chunks novos/datapack reload.
7. Mod Menu/config bridge: screen abre e salva via backend correto.
8. Restart/reconnect sem adapter duplication.
9. A/B em cópia do perfil sem Connector/Extras para validar necessidade.

## 16. Evidência
- modlist física: Connector Extras 1.12.1+1.21.1;
- inventário físico jar-in-jar: TerraBlender, REI, Pehkui, Mod Menu, KubeJS, JEI, Energy, EMI e `extras-utils` embarcados no host;
- CurseForge oficial File ID 5618470;
- source/documentação oficial: Energy bridge, REI/JEI detectors, TerraBlender, Mod Menu e Forge Config API Port bridges;
- decisão histórica de 06/09/2026: `Opcional`, condicionada à existência de consumers Fabric reais.

> 🧭 Boundary canônico: Connector Extras **adapta integrações específicas**; só deve existir por necessidade concreta do stack Connector/consumers, nunca apenas “por garantia”.
