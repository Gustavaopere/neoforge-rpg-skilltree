# CreativeCore

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81479e16f701d45e2dd5  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** CreativeCore
- **Arquivo JAR:** `CreativeCore_NEOFORGE_v2.13.44_mc1.21.1.jar`
- **Versão 1.21.1:** `2.13.44`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/creativecore
- **Função:** Core/API da CreativeMD com infraestrutura compartilhada usada por mods consumidores, incluindo GUI/config, networking e rendering/model utilities.
- **Dependências:** Biblioteca Client & Server. Necessidade determinada pelos consumers CreativeMD instalados; não remover ou atualizar isoladamente sem mapear dependentes e validar ABI/comportamento.
- **Compatibilidade/Riscos:** Riscos de version drift em GUI/config/network/render APIs e model loading. A 2.13.44 reworka modelos de bloco/item e corrige múltiplos filters não carregados; o upstream avisa impacto concreto em LittleTiles e LittleFrames.
- **Sobreposição:** Core específica da CreativeMD; coexistência com outras GUI/config/network/render libraries não implica redundância binária. Consumers compilam contra contratos próprios.
- **Observações:** mod id `creativecore`; runtime 2.13.44. Changelog 2.13.44: reworked models for block/item, afetando LittleTiles/LittleFrames, e fix de multiple filters não carregados corretamente.
- **Procedência:** Modlist física canônica de 08/09/2026, 600 top-levels + runtime 2.13.44 + CurseForge/Modrinth oficiais CreativeCore 2.13.44 NeoForge 1.21.1.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CreativeCore 2.13.44 foi reconfirmado como `Instalado` na modlist física de 600; o dossiê Alex já aplicado foi preservado e a presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — library/consumer authority, GUI/config/network/render surfaces, model/filter lifecycle, side e regressões 2.13.44 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🧱 Versão física confirmada: `CreativeCore_NEOFORGE_v2.13.44_mc1.21.1.jar`, mod id `creativecore`, runtime `2.13.44`, NeoForge 1.21.1. CreativeCore é uma **core library da CreativeMD**.

## 1. Papel e authority
CreativeCore fornece infraestrutura reutilizada por mods consumidores. O consumer continua authority de sua feature final — blocos, estruturas, GUI, rendering ou gameplay — mesmo quando utiliza componentes da library.

## 2. GUI e configuração
A library oferece infraestrutura comum para GUIs/configs no ecossistema CreativeMD. A tela pode editar/apresentar dados, mas validação, persistência e semântica de cada opção pertencem ao consumer.

Não transformar uma widget/client screen em authority de server state.

## 3. Networking
CreativeCore fornece utilities de comunicação usadas por consumers. O payload e a ação final pertencem ao consumer; packets devem validar side, contexto e permissões antes de alterar gameplay state.

Retry/reconnect não pode provocar double settlement de uma ação por handler duplicado.

## 4. Rendering e models
O changelog 2.13.44 registra **rework de models de block e item**, com impacto explícito em LittleTiles e LittleFrames. Isso torna model loading/baking uma superfície crítica da build.

Resultado visual continua client-side; block/entity gameplay state não deve depender de um model renderizado com sucesso.

## 5. Filters
A mesma release corrige **multiple filters not getting loaded properly**. Filters usados por consumers precisam ser revalidados após config/resource/data lifecycle para não manter seleção incompleta ou stale.

A ficha não inventa tipos/classes de filter sem source pin mais granular.

## 6. Consumer impact
O upstream cita LittleTiles e LittleFrames como afetados pelo rework de modelos. Isso comprova consumer impact, mas não autoriza assumir que todo mod CreativeMD usa a mesma superfície.

Atualizações devem ser testadas no conjunto físico real de consumers do pack.

## 7. Client/server
CreativeCore é Client & Server. Rendering/GUI é client-facing; common configs/networking e state de consumers podem operar em ambos os lados.

Dedicated server não deve carregar renderer ou screen apenas para inicializar utilities comuns.

## 8. Resource/config lifecycle
Validar resource reload, config reload quando suportado, model bake, world join, disconnect/reconnect e restart. Caches de model/filter/UI não devem sobreviver quando a fonte foi alterada.

## 9. Version drift
Sintomas possíveis:
- linkage error em consumer;
- GUI/config que deixa de abrir;
- packet/codec incompatível;
- model quebrado/missing;
- filters ausentes;
- client classloading no servidor.

Diagnóstico precisa registrar versão CreativeCore + consumer + fase do lifecycle.

## 10. Fail-closed de API
A documentação pública confirma as superfícies gerais da core library e o changelog da 2.13.44, mas não fornece um inventário estático de toda ABI. Para integração própria, pin de source/JAR deve preceder uso de classes/métodos concretos.

## 11. Riscos
1. Remover com consumer ativo.
2. Atualizar library isoladamente e quebrar consumer.
3. Model rework regredir LittleTiles/LittleFrames ou outro consumer.
4. Filters permanecerem ausentes/stale.
5. Packet handler duplicar settlement.
6. UI/config client divergir do state real.
7. Renderer class vazar para dedicated server.

## 12. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join sem linkage/classloading errors.
3. GUIs/configs de consumers selecionados.
4. Network actions exatamente uma vez.
5. Models de block/item dos consumers após resource reload.
6. LittleTiles/LittleFrames, se ativos no perfil, como regression gates 2.13.44.
7. Filters de consumers carregados após restart/reload.
8. Disconnect/reconnect sem handler/cache duplication.

## 13. Evidência
- modlist física atual: CreativeCore 2.13.44;
- CurseForge/Modrinth oficiais: core/API Client & Server para NeoForge 1.21.1;
- changelog 2.13.44: rework de block/item models e fix de multiple filters;
- LittleTiles/LittleFrames citados explicitamente pelo upstream como afetados.

> 🔧 Boundary canônico: CreativeCore fornece **infraestrutura comum**; cada consumer permanece authority de seu conteúdo e gameplay.
