# Biolith

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8186996efaf07992a7de  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Biolith
- **Arquivo JAR:** `biolith-neoforge-3.0.14.jar`
- **Versão 1.21.1:** `3.0.14`
- **Categoria:** Biblioteca; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/biolith
- **Função:** API de worldgen para placement/removal/replacement/sub-biomes e surface rules, permitindo integração de biomas em Overworld, Nether, End e biome sources compatíveis.
- **Dependências:** Biblioteca estrutural de worldgen; necessidade determinada pelos consumidores. Pack também possui BCLib 21.0.26 e TerraBlender 4.1.0.8; coexistência é suportada em cenários documentados, sem equivalência de API.
- **Compatibilidade/Riscos:** Placement strategies podem mover biomas em mundos existentes após mudanças; riscos de chunk borders, datapack reload, conflitos de biome source/surface rules e interações com BCLib/TerraBlender. 3.0.14 muda o carregamento de datapacks na linha 1.21.1.
- **Sobreposição:** Infraestrutura de biome/worldgen; não substitui automaticamente BCLib, TerraBlender ou Lithostitched. Pode coexistir quando mods consumidores usam APIs distintas.
- **Observações:** v3.0.14 1.21.1: 'Load datapacks like we do in 1.21.4+'. API documenta add/remove/replace biomes, sub-biomes, End custom noise, custom surface rules/builders; compat TerraBlender e compat parcial BCLib.
- **Procedência:** Modlist física atual de 07/09/2026 + GitHub/Modrinth/CurseForge oficiais Biolith v3.0.14, release commit 996b7a4.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria reconfirmou Biolith 3.0.14 como infraestrutura de biome placement/surface rules e preservou os riscos de chunk seams, datapack lifecycle e coexistência com BCLib/TerraBlender. A dependência física não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — placement API, datapack/surface lifecycle, compatibilidade de biome sources e riscos de mundo existente catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `biolith-neoforge-3.0.14.jar`, mod id `biolith`, runtime `3.0.14`, NeoForge 1.21.1. Release oficial v3.0.14 corresponde ao commit `996b7a4` e altera o carregamento de datapacks da linha 1.21.1 para o modelo usado em 1.21.4+.

## 1. Papel e autoridade
Biolith é uma **API de biome placement/worldgen**, não um pacote de biomas. Ela permite que mods consumidores adicionem, removam, substituam ou componham biomas e surface rules sem cada consumidor implementar toda a infraestrutura de biome source.

O mod que registra o biome continua authority do conteúdo desse biome. Biolith é authority apenas da camada de placement/integration que oferece.

## 2. Placement de biomas
A documentação oficial expõe operações para colocar biomas em noise points de:
- Overworld;
- Nether;
- End.

A operação integra o biome à seleção do mundo; ela não cria automaticamente blocks, mobs, features ou climate rules que o biome consumidor não tenha registrado.

## 3. Remoção de biomas
Biolith permite remover um biome vanilla/datapack dos noise points correspondentes. Isso deve ser usado com cuidado porque remover placement em config/datapack não apaga chunks existentes e pode gerar fronteiras entre regiões antigas e novas.

## 4. Substituição parcial ou total
A API suporta substituir um biome totalmente ou parcialmente. Integrações próprias devem tratar replacement como regra de **geração futura** e não como conversão dinâmica de chunks já salvos.

Mudanças de porcentagem/estratégia em pack existente precisam de QA de chunk borders.

## 5. Sub-biomes
Outra superfície oficial é a inserção de sub-biomes dentro de biomes do Overworld, Nether, End e, quando suportado, biomes modded. Isso é útil para variação local sem criar uma segunda dimension pipeline.

O biome pai continua registrado pelo provider original; Biolith apenas participa da seleção.

## 6. End placement e custom noise
Biolith documenta suporte específico a **End biome placement com custom noise**. Como o pack possui BetterEnd/New Dawn e outras alterações do End, qualquer consumidor de Biolith precisa ser auditado junto do biome source real para evitar assumir que vanilla End source é o único ativo.

## 7. Surface rules
A API permite:
- registrar custom surface rules;
- sobrescrever superfícies vanilla por custom surface builders.

Surface rule decide materiais superficiais durante geração; não deve ser reaplicada em chunk load. Dois providers escrevendo a mesma faixa de surface rule podem produzir ordem-de-registro semanticamente relevante.

## 8. Datapack biome placement
Desde a linha 2.x, Biolith suporta biome placement/surface data via datapack. A 3.0.14 altera explicitamente o modo de carregamento dos datapacks no 1.21.1 para seguir o comportamento de versões posteriores.

Riscos:
- cache de placement anterior sobreviver ao reload;
- resource/registry order diferente entre startup e reload;
- datapack escrito para schema incompatível.

## 9. Compatibilidade com TerraBlender
O projeto documenta compatibilidade com biomas TerraBlender. No pack, TerraBlender 4.1.0.8 está presente. Isso significa coexistência possível, não equivalência:
- consumer TerraBlender continua usando TerraBlender;
- consumer Biolith continua usando Biolith;
- bridge/placement final precisa ser testado na combinação real.

## 10. Compatibilidade com BCLib
A documentação classifica a compatibilidade com BCLib como parcial/condicionada porque BCLib pode modificar placement. O pack usa BCLib 21.0.26 para BetterX.

Não assumir que um biome BetterEnd/BetterNether pode ser movido por Biolith sem validar o stack New Dawn específico.

## 11. Existing-world safety
A própria documentação alerta que placement strategies são sensíveis a mudanças e podem deslocar biomas em mundos existentes. Portanto:
- snapshots de config/datapack importam;
- atualizar Biolith/consumer pode alterar fronteiras de chunks novos;
- chunks antigos não devem ser usados como prova do layout atual;
- backups são necessários antes de alterar placement em mundo persistente.

## 12. Client/server
Worldgen/registry/placement são server/common. Biolith não deve depender de renderer para decidir biome. Cliente recebe o biome/world state resultante; quaisquer mapas/minimaps apenas observam esse resultado.

Dedicated server deve ser parte obrigatória do QA porque erros de bootstrap/registry podem não aparecer em menu singleplayer até criar/carregar mundo.

## 13. Lifecycle
Pontos críticos:
1. registry bootstrap;
2. datapack load inicial;
3. criação de world/dimension;
4. chunk generation;
5. datapack reload;
6. server restart;
7. update de config/consumer;
8. dimension travel;
9. geração de chunks novos junto de chunks antigos.

Holder/registry references devem ser resolvidas no lifecycle apropriado e descartadas quando o reload exige.

## 14. Relação com outras APIs no pack
- BCLib/New Dawn: foundation BetterX e placement próprio/compatível.
- TerraBlender: outra API de biome placement.
- Lithostitched: infraestrutura/data-driven worldgen de outro ecossistema.

Coexistência não justifica deduplicar bibliotecas. A dependência é determinada pelos mods consumidores.

## 15. Riscos
1. Chunk seams depois de mudar placement.
2. Dois frameworks disputarem o mesmo biome/noise range.
3. Surface rule order mudar materiais inesperadamente.
4. Datapack reload manter state stale.
5. Consumer assumir API de outra versão.
6. End custom noise conflitar com biome source modificado.
7. Remover Biolith enquanto consumidor ainda depende dela.

## 16. Matriz de testes
1. Dedicated server boot com todos os consumidores atuais.
2. Mundo novo: verificar biomes adicionados/substituídos pelos consumers reais.
3. Datapack reload sem registry error.
4. Gerar chunks antes/depois de reload e comparar bordas.
5. TerraBlender + Biolith simultâneos.
6. BCLib/New Dawn + Biolith em End/Nether.
7. Surface rules custom sem duplicação/ordem inesperada.
8. Atualizar placement em cópia de mundo e verificar seams antes de produção.

## 17. Evidência
- modlist física atual: Biolith 3.0.14;
- GitHub oficial Biolith: operações de add/remove/replace/sub-biome, End custom noise, surface rules e compatibilidade;
- release oficial v3.0.14 / commit `996b7a4`;
- changelog 3.0.14: carregamento de datapacks alinhado à linha 1.21.4+;
- modlist física confirma BCLib e TerraBlender para mapear interações do pack.

> 🗺️ Biolith é **placement infrastructure**, não conteúdo. O risco central em pack persistente é mudar a seleção de biomas para chunks futuros e criar fronteiras com regiões já geradas.
