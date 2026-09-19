# Integrated API

## Propriedades do registro

- **Mod:** Integrated API
- **Arquivo JAR:** integrated_api-neoforge-1.21.1-1.8.2.jar
- **Versão 1.21.1:** 1.8.2
- **Categoria:** Biblioteca; Worldgen
- **Função:** Biblioteca/worldgen API da série Integrated Structures, fornecendo abstrações e utilitários de estruturas, jigsaw/terrain adaptation, conditional block replacement, cartographer maps e opções data-driven compartilhadas.
- **Dependências:** NeoForge 1.21.1. Consumers físicos confirmados no pack incluem IDAS 1.13.7, Integrated Dungeons Arise 2.1.1, Integrated Mowzie's Mobs 1.3.0, Integrated Cataclysm 1.0.6, Integrated Stronghold 1.1.4 e Integrated Villages 1.3.3. Integrated Patches continua ausente fisicamente.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Infraestrutura central do cluster Integrated. Riscos: ABI/data drift entre consumers, jigsaw/terrain-placement regressions, conditional replacement com mod ausente, structure JSON incompatível, shared worldgen bugs e atualização unilateral. 1.8.2 adiciona ceiling structure type; 1.8.0 já havia adicionado upside-down terrain adaptation.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/8636576
- **Procedência:** modlist física de 17/09/2026 + release/changelog oficial Integrated API 1.8.2 para NeoForge 1.21.1 + documentação oficial já auditada do ecossistema Integrated.
- **Observações:** Runtime físico 1.8.2. Release 1.8.2 adiciona ceiling structure type. Upside-down terrain adaptation da 1.8.0 permanece capability herdada. Integrated Patches segue não instalado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — Integrated API atualizado para 1.8.2; ceiling structure type incorporado; upside-down terrain adaptation preservada; Integrated Patches continua ausente e não foi promovido a dependência.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
- **Sobreposição:** Não é mod de estruturas independente; centraliza infraestrutura usada por vários Integrated. Não duplicar jigsaw/terrain/data logic nos consumers sem necessidade, e não atribuir automaticamente a um consumer bugs que pertencem à API compartilhada.
- **Data da última decisão:** 2026-08-26

> **ESCOPO CANÔNICO.** Runtime físico: `integrated_api-neoforge-1.21.1-1.8.2.jar`, mod id `integrated_api`, versão `1.8.2`. A distribuição física corresponde à release oficial NeoForge 1.21.1. A ficha é release-pinned para comportamento version-sensitive.
## 1. Papel e authority
Integrated API é a biblioteca de infraestrutura da série Integrated Structures. Ela não é authority dos catálogos de estruturas dos consumers; fornece contratos comuns para worldgen, jigsaw, terrain adaptation, configuração e integrações reutilizáveis.
## 2. Consumers físicos relevantes
O pack atual contém vários consumers da família: IDAS 1.13.7, Integrated Dungeons Arise 2.1.1, Integrated Mowzie's Mobs 1.3.0, Integrated Cataclysm 1.0.6, Integrated Stronghold 1.1.4 e Integrated Villages 1.3.3. Uma atualização da API deve ser tratada como alteração transversal a todos eles.
## 3. Create contraption compatibility
A documentação oficial inclui utilidades para compatibilidade com contraptions do Create. Isso não torna a API owner da física/Create state; ela apenas fornece integração compartilhada. Consumers devem preservar block/entity ownership do provider original.
## 4. Conditional block replacement
A API suporta substituir blocos condicionalmente quando outro mod está instalado. Esse mecanismo deve degradar de forma segura quando o provider opcional estiver ausente e não pode produzir registry references inválidas durante data load.
## 5. Cartographer map trades
O projeto oferece suporte facilitado a mapas de cartógrafo associados a estruturas. Map/trade generation é uma ponte de descoberta; quests externas não devem tratar a obtenção do mapa e a descoberta da estrutura como o mesmo settlement sem regra explícita.
## 6. Limite expandido de estrutura
A documentação registra expansão do limite de Structure Block de 48 para 256. Templates muito maiores aumentam risco de custo de processamento, chunk boundaries e armazenamento; isso não significa que todos os consumers usem automaticamente o tamanho máximo.
## 7. Jigsaw manager
A API fornece um jigsaw manager próprio/melhorado para a série. O placement resultante deve permanecer deterministicamente compatível com registries e data da versão instalada. Outros worldgen mods não devem tentar reprocessar o mesmo jigsaw output.
## 8. Structure JSON e configuração
O upstream expõe opções adicionais em structure JSONs. Isso torna datapacks/config uma superfície de integração de primeira classe: codecs, defaults, tags, spacing/separation e parâmetros de terrain fit precisam ser validados por release e por seed.
## 9. Terrain adaptation — 1.8.0 → 1.8.2
A 1.8.0 adicionou **upside-down structure terrain adaptation**, capability preservada na linha atual.
A **1.8.2**, instalada atualmente, adiciona **ceiling structure type**. Como a documentação pública não fornece nesta auditoria um schema completo por consumer, a ficha não inventa chaves JSON nem atribui uso concreto a estruturas específicas sem evidência. Ambos os recursos são tratados como capabilities da API e regression gates de worldgen.
## 10. Boundary de bugs compartilhados
Consumers podem manifestar defeitos cuja causa real está na API. O caso documentado de geração estranha de Umvuthana Grove em Integrated Mowzie's Mobs é atribuído pelo upstream ao Integrated API. Diagnóstico deve separar template consumer, API, biome/terrain provider e seed.
Em 10/09/2026 foi publicado **Integrated Patches 1.0.0** para NeoForge 1.21.1, descrito como correção de problemas de worldgen do Integrated API via ajuste do `structure_type` genérico para melhor adaptação ao terreno. O changelog de Integrated Mowzie's Mobs 1.3.0 passa a declarar esse patch como dependência. A modlist física atual não contém Integrated Patches top-level; por isso esta ficha registra o patch como **companion corretivo externo relacionado**, não como dependência própria do Integrated API nem como feature embutida na 1.8.0.
## 11. Client / server
A distribuição é Client & Server. Worldgen, structure placement, trades e state persistente são server-authoritative. Assets/menus/visualização podem exigir cliente, mas o cliente não decide placement ou loot.
## 12. Lifecycle e persistência
Validar datapack load, registry setup, `/reload`, world creation, geração de chunks novos, server restart e update conjunto API+consumers. Mudanças de JSON/jigsaw não reescrevem automaticamente estruturas já persistidas; mundos podem ficar híbridos entre versões.
## 13. Riscos técnicos
- consumer compilado contra ABI diferente;
- source/branch de desenvolvimento ser confundido com a release física 1.8.2;
- conditional replacement criar missing registry;
- jigsaw/terrain adaptation quebrar placement;
- structure JSON inválido ou schema drift;
- mapa/trade apontar para estrutura impossível de gerar;
- shared bug ser corrigido no consumer errado;
- assumir que Integrated Patches está presente/embutido quando a modlist física atual não o contém;
- update unilateral da API quebrar vários mods simultaneamente;
- estruturas grandes elevarem custo de geração.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com API 1.8.2 e todos os consumers físicos.
- [ ] Datapacks/structure JSONs carregam sem codec/registry errors.
- [ ] Consumer sem integração opcional disponível degrada sem hard-fail.
- [ ] Cartographer maps/trades apontam para estruturas válidas quando usados.
- [ ] Jigsaw structures geram sem peças órfãs/duplicadas.
- [ ] Terrain adaptation padrão, upside-down e ceiling structure type não corrompem terreno.
- [ ] `/reload` não quebra registries/config data.
- [ ] Restart preserva estruturas já geradas.
- [ ] Update futuro da API é bloqueado até regression test dos consumers.
## 15. Evidências e limites
- **Modlist física de 17/09/2026:** `integrated_api-neoforge-1.21.1-1.8.2.jar`, mod id `integrated_api`, versão 1.8.2.
- **Release oficial 1.8.2:** adiciona ceiling structure type.
- **Histórico 1.8.0:** upside-down terrain adaptation permanece capability herdada da linha.
- **Documentação oficial:** contraption compat, conditional replacement, cartographer maps, limite 256, jigsaw manager e opções JSON.
- **Integrated Patches:** continua ausente da modlist física atual e não é tratado como feature embutida/dependência própria da API.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
