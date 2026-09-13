# AdvancedCoreInfo

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c769db9f0db814abca9e4e5888e7b88
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AdvancedCoreInfo
- **Arquivo JAR:** `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar`
- **Versão 1.21.1:** 1.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Core/API compartilhado da família Advanced Info. Centraliza plugin discovery, tooltip trees hierárquicas, serialização/transferência servidor→cliente e infraestrutura genérica usada por Advanced Loot Info (ALI) e Advanced Worldgen Info (AWI). Não exibe loot/worldgen sozinho: o conteúdo final pertence aos consumers.
- **Dependências:** Nenhuma hard dependency externa adicional foi localizada além da plataforma compatível. Relações reversas: Advanced Loot Info (ALI) e Advanced Worldgen Info (AWI) requerem ACI. No pack físico atual, ALI 2.1.0 está instalado; AWI não aparece top-level. ACI 1.1.0 é o runtime atual.
- **Sobreposição:** Sobreposição apenas de infraestrutura com outras tooltip/network/plugin libraries. Não é outro recipe viewer, não altera loot tables e não altera worldgen. O conflito operacional possível é congestão de tooltip/UI quando muitos consumers apresentam dados no mesmo viewer, não duplicação funcional do core.
- **Compatibilidade/Riscos:** Risco principal é version coupling entre core e consumers: plugin API, tooltip nodes e payload/serialization podem mudar em conjunto. Como transporta dados servidor→cliente, mismatch pode causar falha de payload, desconexão ou UI vazia. Não substitui JEI/EMI/REI; estes são viewers consumidos por ALI/AWI. Não há incompatibilidade formal upstream específica localizada.
- **Observações:** Upstream define ACI como required por ALI/AWI e 'does nothing on its own'. Não atribuir a ACI categorias/loot/worldgen que pertencem aos consumers. Ausência física de AWI no pack deve ser preservada; não inventar consumer só porque a library suporta.
- **Procedência:** Modlist física 2026-09-07 + CurseForge oficial Advanced Core Info 1.1.0 + documentação consolidada do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/advanced-core-info
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de core/API, plugin discovery, tooltip trees, sync server→client, consumers, reload e version coupling confirmado no QC global #6.
- **Histórico da decisão:** Ficha original curta foi substituída em 07/09/2026 por dossiê operacional completo no padrão adotado após o feedback sobre Alex's Mobs. Sem mudança de decisão curatorial.
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar`. Advanced Core Info é o **core compartilhado** da família Advanced Info. Upstream define quatro responsabilidades: plugin discovery, tooltip trees, transferência servidor→cliente e infraestrutura genérica. Também afirma explicitamente que ACI é requerido por ALI/AWI e **não faz nada sozinho** no sentido de conteúdo final.

## 1. Identidade e papel arquitetural
Advanced Core Info (ACI) existe para impedir que **Advanced Loot Info (ALI)** e **Advanced Worldgen Info (AWI)** mantenham cópias próprias da mesma infraestrutura. O core concentra contratos compartilhados e deixa cada consumer implementar sua categoria de informação.

Portanto a separação correta é:
- **ACI:** infraestrutura, árvore de tooltip, descoberta de plugins e transporte de dados;
- **ALI:** loot tables, villager trades e categorias relacionadas a loot;
- **AWI:** informação de worldgen;
- **JEI/EMI/REI:** viewers nos quais os consumers podem apresentar dados.

Uma UI de loot funcionando não significa que ACI “mostra loot”. ACI apenas participa do pipeline que permite ao ALI fazê-lo.

## 2. Plugin discovery
Upstream lista **plugin discovery** como responsabilidade central. Isso significa que o ecossistema Advanced Info não precisa hardcodar todo provider no core: consumers e integrações podem descobrir implementações/plugins segundo o contrato exposto.

Para manutenção do pack, isso cria três classes de falha distintas:
1. plugin não é descoberto;
2. plugin é descoberto, mas usa contrato/API incompatível;
3. plugin funciona, mas o viewer downstream não consegue renderizar a informação.

Não tratar os três sintomas como “bug do JEI” sem separar as camadas.

## 3. Tooltip trees
ACI fornece **tooltip trees**: uma estrutura hierárquica para representar informação complexa em vez de despejar uma linha de texto plana.

Isso é adequado para dados como loot entries, conditions, functions, number providers, nested pools ou componentes equivalentes de worldgen. A árvore permite que consumers organizem subnós e tooltips compostos.

Consequências para compatibilidade:
- plugins podem acrescentar nós próprios;
- mudanças no tipo/serialização de um nó podem afetar todos os consumers;
- viewers diferentes podem apresentar a mesma árvore com layout distinto;
- tooltips muito profundas podem causar congestionamento visual sem haver erro de dados.

## 4. Transferência servidor → cliente
ACI centraliza a **server-to-client data transfer** usada pelos consumers. Isso é essencial porque parte da informação exibida por ALI/AWI pertence ao estado/data pack do servidor e não pode ser inferida com segurança só pelo cliente.

O pipeline conceitual é:
1. servidor possui/resolve os dados autoritativos;
2. consumer/core prepara informação serializável;
3. ACI participa do transporte;
4. cliente recebe os dados;
5. consumer constrói a apresentação no recipe/info viewer.

Em dedicated server, uma falha pode aparecer como:
- ausência de informação;
- payload registration incompatível;
- disconnect por packet inválido;
- dados desatualizados depois de reload;
- tooltip parcial.

Por isso ACI deve ser testado em cliente+servidor, não apenas singleplayer.

## 5. Generic functionality
A descrição upstream também menciona **other generic functionality**. Como o publisher não enumera publicamente cada helper interno na página do projeto, esta ficha não inventa classes, métodos ou serviços além dos quatro papéis explicitamente documentados.

Regra: se outro chat precisar de API exata, package, method signature ou plugin interface, deve consultar source/JAR da versão 1.1.0 antes de implementar.

## 6. Consumers
### Advanced Loot Info — instalado
O pack físico possui **Advanced Loot Info 2.1.0**. Este é o consumer real que justifica ACI nesta instância.

ALI usa a infraestrutura compartilhada para exibir informação avançada de loot e villager trades em viewers suportados. A compatibilidade ACI↔ALI deve ser testada como um par.

### Advanced Worldgen Info — não instalado top-level
AWI é consumer upstream oficial de ACI, mas **não aparece como JAR top-level na modlist física atual**.

Portanto:
- não listar AWI como instalado;
- não atribuir funcionalidades de worldgen ao pack por causa de ACI;
- se AWI entrar futuramente, adicionar sua própria ficha top-level e revalidar a versão de ACI.

## 7. Relação com JEI, EMI e REI
ACI **não substitui** recipe/info viewers.

O stack correto é:
- viewer fornece framework/UI de categorias/recipes/information;
- ALI/AWI convertem seus domínios para informação exibível;
- ACI fornece infraestrutura compartilhada usada pelos consumers.

Se JEI está presente e ALI não mostra uma categoria, o diagnóstico deve verificar:
1. ALI carregado;
2. ACI compatível;
3. plugin discovery;
4. dados sincronizados;
5. integração do viewer;
6. somente depois layout/render do JEI.

## 8. Dependências
### Diretas
Nenhuma hard dependency externa adicional foi identificada na documentação pública auditada além de Minecraft/loader compatíveis.

### Reversas confirmadas
- **Advanced Loot Info** requer ACI.
- **Advanced Worldgen Info** requer ACI.

Isso faz ACI ser uma **dependência estrutural** quando qualquer desses consumers está instalado.

## 9. Version coupling
Core e consumers compartilham contrato. Mesmo sem uma relação upstream marcada `Incompatible`, atualizar um lado isoladamente pode introduzir:
- `NoSuchMethodError` / linkage errors;
- plugin não descoberto;
- serialização incompatível;
- payload mismatch;
- tooltip tree inválida;
- dados simplesmente ausentes.

A release 1.1.0 foi publicada para 1.21.1 na mesma janela de releases modernas da família. Para este pack, não substituir ACI 1.1.0 por release de outra linha de Minecraft apenas porque o número é igual.

## 10. Lifecycle de reload
ALI/AWI consomem dados que podem mudar com datapacks/server state. Como ACI participa da infraestrutura de transferência, qualquer `/reload` relevante deve ser seguido de verificação de:
- reconstrução dos dados pelo consumer;
- reenvio ao cliente quando necessário;
- ausência de cache antigo;
- atualização das tooltips sem reconnect obrigatório, quando suportado.

Se um consumer exigir reconnect para refletir dados, registrar isso como comportamento da combinação consumer/core/viewer e não presumir bug de worldgen/loot.

## 11. Segurança e authority
ACI é **transport/infrastructure**, não authority de gameplay. Ele não deve:
- modificar loot table;
- conceder loot;
- alterar villager trade;
- alterar biome/worldgen;
- servir como trigger de perk só porque uma tooltip existe.

Outro chat deve sempre buscar o provider real da mecânica. ACI é observabilidade/apresentação compartilhada.

## 12. Sobreposição funcional
### Tooltip libraries
Outras libraries também podem construir tooltips, mas contracts diferentes não são intercambiáveis. ACI só é relevante para consumers que foram feitos para sua API.

### Recipe viewers
JEI/EMI/REI apresentam informação; ACI não compete com eles como viewer.

### Networking libraries
Muitos mods sincronizam dados server→client. Isso é sobreposição de superfície técnica, não duplicação: cada mod registra seus próprios payloads/contracts.

## 13. Riscos específicos do pack
1. **Mismatch ACI↔ALI:** principal risco atual.
2. **Viewer mismatch:** ALI pode estar correto, mas uma integração JEI/EMI/REI específica falhar.
3. **Reload/cache:** dados de loot alterados por datapack/LootJS podem ficar desatualizados se lifecycle falhar.
4. **Tooltip congestion:** muitos mods adicionam informação ao mesmo viewer.
5. **Network payload:** dedicated server pode revelar problemas inexistentes em singleplayer.
6. **Consumer ausente:** ACI sozinho não deve ser interpretado como feature quebrada; não há feature final para mostrar.
7. **AWI inexistente no pack:** não construir dependência/quest/integração em cima de uma instalação que não existe.

## 14. Matriz de validação
1. Startup cliente com ACI 1.1.0 + ALI instalado.
2. Startup dedicated server com o mesmo par.
3. Abrir viewer suportado e consultar uma loot table simples.
4. Consultar uma loot table profundamente aninhada e validar tooltip tree.
5. Consultar villager trade via ALI.
6. Testar plugin/custom provider suportado por ALI.
7. Alterar datapack/loot e executar `/reload`; confirmar atualização.
8. Entrar com cliente depois do servidor já estar rodando; confirmar sincronização inicial.
9. Relogar sem reiniciar servidor; confirmar cache consistente.
10. Trocar dimensão e repetir consulta para garantir que informação global não desapareça.
11. Verificar log por payload registration/codec/linkage errors.
12. Remover ALI em ambiente de teste: ACI deve carregar sem criar UI de loot própria.
13. Não exigir AWI enquanto ele não existir fisicamente.
14. Em update futuro, testar ACI e consumers como conjunto antes de promover versão.

## 15. O que outro chat pode inferir desta ficha
Pode inferir com segurança que:
- ACI é core/API compartilhado;
- ALI e AWI são os consumers oficiais nomeados pelo upstream;
- ALI está instalado e AWI não está top-level nesta instância;
- plugin discovery, tooltip trees e server→client transfer pertencem ao core;
- ACI não muda loot/worldgen por si;
- API exata deve ser confirmada no JAR/source antes de implementação.

Não pode inferir:
- nomes de classes/métodos não publicados aqui;
- que qualquer plugin externo é automaticamente compatível;
- que JEI/EMI/REI são dependências do core em si;
- que AWI está instalado.

## 16. Fontes e confiança
**Authority física:** modlist do projeto em 07/09/2026, com `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar` e ALI 2.1.0 como top-level.

**Upstream:** [CurseForge — Advanced Core Info](https://www.curseforge.com/minecraft/mc-mods/advanced-core-info). O publisher descreve explicitamente ACI como shared core de ALI/AWI, responsável por plugin discovery, tooltip trees, server→client data transfer e generic functionality, requerido pelos dois e sem função final isolada.

**Fonte interna:** guia completo de gameplay/sistemas, usado como referência histórica; onde ele ainda cita ACI 1.0.0, a modlist física e o Notion reconciliado prevalecem para runtime 1.1.0.

**Confiança:** alta para identidade, versão, consumers e quatro responsabilidades publicadas. Detalhes de API não expostos publicamente permanecem propositalmente não inventados.
