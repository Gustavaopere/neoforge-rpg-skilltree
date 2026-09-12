# Integrated API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81aba022d54f6b94af55
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated API
- **Arquivo JAR:** `integrated_api-neoforge-1.21.1-1.8.0.jar`
- **Versão 1.21.1:** 1.8.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Worldgen
- **Função:** Biblioteca/worldgen API da série Integrated Structures, fornecendo abstrações e utilitários de estruturas, jigsaw/terrain adaptation, conditional block replacement, cartographer maps e opções data-driven compartilhadas.
- **Dependências:** NeoForge 1.21.1. Consumers físicos confirmados no pack incluem IDAS 1.13.7, Integrated Dungeons Arise 2.1.1, Integrated Mowzie's Mobs 1.3.0, Integrated Cataclysm 1.0.6, Integrated Stronghold 1.1.4 e Integrated Villages 1.3.3.
- **Sobreposição:** Não é mod de estruturas independente; centraliza infraestrutura usada por vários Integrated. Não duplicar jigsaw/terrain/data logic nos consumers sem necessidade, e não atribuir automaticamente a um consumer bugs que pertencem à API compartilhada.
- **Compatibilidade/Riscos:** Infraestrutura central do cluster Integrated. Riscos: ABI/data drift entre consumers, jigsaw/terrain-placement regressions, conditional replacement referenciando mod ausente, structure JSON incompatível, shared worldgen bugs e atualização unilateral da API.
- **Observações:** Release exata 1.8.0 de 13/08/2026 adiciona upside-down structure terrain adaptation. O source público `craisinlord/IntegratedAPI` já declara 1.8.1; portanto arquitetura pode ser consultada, mas internals version-sensitive permanecem release-pinned.
- **Procedência:** modlist.txt física atual + CurseForge oficial Integrated API 1.8.0 file 8636576 + documentação oficial do projeto + GitHub craisinlord/IntegratedAPI usado estruturalmente, com drift atual para 1.8.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/8636576
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Integrated API 1.8.0 release-pinned; structure utilities, conditional replacement, cartographer maps, 256-block structure limit, jigsaw manager, JSON/config, upside-down terrain adaptation, consumer boundaries, riscos e testes catalogados; source master já está em 1.8.1.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `integrated_api-neoforge-1.21.1-1.8.0.jar`, mod id `integrated_api`, versão `1.8.0`. A release oficial 1.21.1 é o file 8636576 de 13/08/2026. O `master` público já declara 1.8.1; por isso esta ficha é release-pinned para comportamento version-sensitive.

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

## 9. Terrain adaptation — 1.8.0
O changelog exato da build instalada adiciona **upside-down structure terrain adaptation**. Como o texto público não documenta schema ou consumer específico, esta ficha não inventa chave JSON ou estrutura que use o recurso; ele é tratado como capability da API 1.8.0 e regression gate.

## 10. Boundary de bugs compartilhados
Consumers podem manifestar defeitos cuja causa real está na API. O caso documentado de geração estranha de Umvuthana Grove em Integrated Mowzie's Mobs é atribuído pelo upstream ao Integrated API. Diagnóstico deve separar template consumer, API, biome/terrain provider e seed.

## 11. Client / server
A distribuição é Client & Server. Worldgen, structure placement, trades e state persistente são server-authoritative. Assets/menus/visualização podem exigir cliente, mas o cliente não decide placement ou loot.

## 12. Lifecycle e persistência
Validar datapack load, registry setup, `/reload`, world creation, geração de chunks novos, server restart e update conjunto API+consumers. Mudanças de JSON/jigsaw não reescrevem automaticamente estruturas já persistidas; mundos podem ficar híbridos entre versões.

## 13. Riscos técnicos
- consumer compilado contra ABI diferente;
- source 1.8.1 ser confundido com 1.8.0 exato;
- conditional replacement criar missing registry;
- jigsaw/terrain adaptation quebrar placement;
- structure JSON inválido ou schema drift;
- mapa/trade apontar para estrutura impossível de gerar;
- shared bug ser corrigido no consumer errado;
- update unilateral da API quebrar vários mods simultaneamente;
- estruturas grandes elevarem custo de geração.

## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com API 1.8.0 e todos os consumers físicos.
- [ ] Datapacks/structure JSONs carregam sem codec/registry errors.
- [ ] Consumer sem integração opcional disponível degrada sem hard-fail.
- [ ] Cartographer maps/trades apontam para estruturas válidas quando usados.
- [ ] Jigsaw structures geram sem peças órfãs/duplicadas.
- [ ] Terrain adaptation padrão e upside-down não corrompem terreno.
- [ ] `/reload` não quebra registries/config data.
- [ ] Restart preserva estruturas já geradas.
- [ ] Update futuro da API é bloqueado até regression test dos consumers.

## 15. Evidências e limites
- **Modlist física:** JAR/mod id/version 1.8.0.
- **CurseForge oficial:** file 8636576 e feature 1.8.0 de upside-down terrain adaptation.
- **Documentação oficial:** contraption compat, conditional replacement, cartographer maps, limite 256, jigsaw manager e opções JSON.
- **Source público:** `craisinlord/IntegratedAPI` já está em 1.8.1; não foi usado como byte pin da 1.8.0.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
