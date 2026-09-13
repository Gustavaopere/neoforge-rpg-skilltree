# Integrated Dungeons and Structures- Alex's Mobs Integration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81a5a921fa42e3096239
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `idasalexsmobs-1.13-1.21.1.zip`
- **Versão 1.21.1:** 1.13
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `idasalexsmobs-1.13-1.21.1.zip` como fisicamente confirmado por captura do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `idas-1.13.7+1.21.1-neoforge.jar`, mod id `idas`, runtime `1.13.7+1.21.1-neoforge`, e `alexsmobs-2.1.11-neoforge+1.21.1.jar`, mod id `alexsmobs`, runtime `2.1.11`, nome de runtime Alex's Mobs Continued.
- O upstream publica a integração para Alex's Mobs original. O fork Continued preserva o mod id `alexsmobs`, o que torna os data paths plausíveis, mas isso não equivale a validação upstream específica do fork.
- IDAS continua authority das estruturas; Alex's Mobs Continued continua authority das entidades/gameplay; este datapack controla apenas os dados de integração que efetivamente fornece.

## Propriedades do banco

- **Mod:** Integrated Dungeons and Structures- Alex's Mobs Integration
- **Arquivo JAR:** `idasalexsmobs-1.13-1.21.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.13
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, Mobs, Worldgen
- **Função:** Datapack de integração que adapta conteúdo de Alex's Mobs ao Integrated Dungeons and Structures, incluindo integrações publicadas em advancements, loot tables e presença de mobs nas estruturas.
- **Dependências:** Integrated Dungeons and Structures 1.13.7+1.21.1-neoforge + Alex's Mobs. No perfil, o alvo correspondente é Alex's Mobs Continued 2.1.11, que preserva o mod id `alexsmobs`; compatibilidade específica do fork requer QA.
- **Sobreposição:** Pode disputar advancements, loot tables e outros dados de integração do IDAS/Alex's Mobs com datapacks de maior prioridade. IDAS continua authority das estruturas; Alex's Mobs Continued continua authority das entidades/gameplay.
- **Compatibilidade/Riscos:** O upstream publica integração para Alex's Mobs original; o perfil usa Alex's Mobs Continued 2.1.11 com o mesmo mod id. Compatibilidade por IDs é plausível, mas não há validação upstream específica do fork. Mudanças em loot/advancement/entity IDs podem quebrar entradas do datapack.
- **Observações:** Arquivo físico `idasalexsmobs-1.13-1.21.1.zip`, versão 1.13. O projeto o identifica explicitamente como datapack; classificação corrigida de Resource Pack para Data Pack.
- **Procedência:** CurseForge oficial IDAS Alex's Mobs Integration 1.13 para 1.21.1 + captura do perfil em 08/09/2026 + modlist física IDAS 1.13.7 e Alex's Mobs Continued 2.1.11.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/idas-alexs-mobs
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — IDAS Alex's Mobs Integration 1.13, targets físicos IDAS 1.13.7/Alex's Mobs Continued 2.1.11, advancements/loot/structures, fork boundary, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `idasalexsmobs-1.13-1.21.1.zip`, versão `1.13`, publicado para Minecraft 1.21.1. Os targets físicos são IDAS `1.13.7+1.21.1-neoforge` e Alex's Mobs Continued `2.1.11`.

## 1. Papel e authority
Integrated Dungeons and Structures- Alex's Mobs Integration é o **datapack oficial de integração** entre IDAS e Alex's Mobs. IDAS continua authority das estruturas/worldgen-base; Alex's Mobs Continued continua authority das entidades, AI, stats, drops e comportamento.

## 2. Cobertura publicada
O projeto declara que a integração altera **advancements**, **loot tables** e adiciona conteúdo de Alex's Mobs ao longo de várias estruturas do IDAS. Isso comprova integração de dados, sem transferir ownership das estruturas ou mobs ao datapack.

## 3. Stack físico e boundary do fork
O upstream referencia Alex's Mobs original. O perfil usa **Alex's Mobs Continued 2.1.11**, preservando o mod id `alexsmobs`. Essa continuidade de ID torna a compatibilidade plausível, mas **não substitui validação upstream específica do fork**; o catálogo mantém esse ponto fail-closed.

## 4. Lifecycle de datapack/worldgen
Loot/advancements podem ser recarregados conforme o lifecycle de dados; efeitos associados à geração de estruturas devem ser validados em estruturas/chunks novos quando aplicável. Reload não deve ser confundido com regeneração retroativa de estruturas já materializadas.

## 5. Sobreposição
Outro datapack que substitua os mesmos advancements, loot tables ou dados de integração pode vencer por prioridade. O resultado efetivo depende do data pack stack do mundo.

## 6. Riscos
1. O fork Continued divergir em IDs/dados esperados pelo datapack.
2. IDAS 1.13.7 alterar paths em relação ao pacote 1.13.
3. Outro datapack sobrescrever loot/advancements coincidentes.
4. Teste em estrutura antiga mascarar integração de worldgen.
5. Assumir mobs específicos não publicados pelo upstream.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo.
- [ ] Verificar logs por IDs ausentes de `alexsmobs`.
- [ ] Validar advancements integrados.
- [ ] Validar loot tables em estruturas novas de QA.
- [ ] Inspecionar presença de integração de mobs sem atribuir AI ao datapack.
- [ ] Reload/restart sem erro de dados.
- [ ] Confirmar que remover o datapack não remove IDAS nem Alex's Mobs Continued.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma `idasalexsmobs-1.13-1.21.1.zip`, sua natureza de datapack e a integração de advancements, loot tables e estruturas. A equivalência comportamental com o fork Continued não é presumida além do mod id preservado.

> Boundary canônico: **IDAS controla estruturas; Alex's Mobs Continued controla entidades; este datapack controla somente os dados de integração que fornece**.
