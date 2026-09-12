# Integrated Dungeons and Structures- Configuration Datapack

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81369b40f7f226f43e5f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `idasconfig-1.13.7-1.21.1.zip`
- **Versão 1.21.1:** 1.13.7
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `idasconfig-1.13.7-1.21.1.zip` como fisicamente confirmado por captura do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `idas-1.13.7+1.21.1-neoforge.jar`, mod id `idas`, runtime `1.13.7+1.21.1-neoforge`.
- O upstream define este arquivo como datapack de configuração/worldgen e declara que ele requer o IDAS principal; a categoria Resource Packs do site não altera sua natureza operacional.
- Alterações de structure sets, biome tags, spawners e configured structure features afetam dados/worldgen; reload não implica regeneração retroativa de estruturas já materializadas.

## Propriedades do banco

- **Mod:** Integrated Dungeons and Structures- Configuration Datapack
- **Arquivo JAR:** `idasconfig-1.13.7-1.21.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.13.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen
- **Função:** Datapack opcional de configuração do IDAS para ajustar frequência de estruturas, biomas elegíveis, mob spawns e listas de structures a evitar para compatibilidade de worldgen.
- **Dependências:** Integrated Dungeons and Structures (IDAS) 1.13.7+1.21.1-neoforge. O upstream declara explicitamente que o config pack requer o mod principal e não faz nada sozinho.
- **Sobreposição:** Pode disputar JSONs de structure_set, tags/worldgen/biome, integrated_structure_spawner e configured_structure_feature com outros datapacks. Prioridade de data pack determina o dado efetivo.
- **Compatibilidade/Riscos:** Datapack de worldgen/configuração: alterações afetam geração futura e podem conflitar com outros packs que sobrescrevam os mesmos structure sets, biome tags, spawners ou configured structure features. Ordem de dados importa; upstream exige BOP integration acima do config quando ambos são globais.
- **Observações:** Arquivo físico `idasconfig-1.13.7-1.21.1.zip`, versão 1.13.7 para Minecraft 1.21.1. Embora apareça na categoria Resource Packs do CurseForge, o próprio projeto o define e instala como datapack de mundo.
- **Procedência:** CurseForge oficial IDAS Configuration 1.13.7 + captura do perfil em 08/09/2026 + modlist física IDAS 1.13.7+1.21.1-neoforge.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/idas-config
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — config datapack IDAS 1.13.7, structure sets, biome tags, spawners, avoid lists, data priority, worldgen lifecycle, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `idasconfig-1.13.7-1.21.1.zip`, versão `1.13.7`, para Minecraft 1.21.1. O alvo físico atual é Integrated Dungeons and Structures `1.13.7+1.21.1-neoforge`.

## 1. Papel e authority
Integrated Dungeons and Structures- Configuration Datapack é o pacote opcional de configuração do **IDAS**. O mod principal continua authority das estruturas, conteúdo e integrações; este datapack apenas substitui parâmetros e dados expostos pelo projeto.

## 2. Dependência obrigatória
O upstream declara explicitamente que o pacote **requer o mod principal e não faz nada sozinho**. A dependência está satisfeita pelo IDAS `1.13.7+1.21.1-neoforge` presente no perfil.

## 3. Cobertura de configuração confirmada
O projeto documenta quatro superfícies principais: `spacing` e `separation` dos structure sets para frequência/distância; tags em `tags/worldgen/biome/has structure` para biomas; arquivos `integrated_structure_spawner` para mob spawns; e listas de `structure set to avoid` em `worldgen/configured_structure_feature` para compatibilidade com outras estruturas.

## 4. Regras de spacing/separation
`spacing` representa a distância média entre tentativas vizinhas de geração e `separation` a distância mínima em chunks. O upstream admite valores de 0 a 4096 e exige que `separation` não seja maior que `spacing`.

## 5. Prioridade de dados
Ao usar global datapacks com integração Biomes O' Plenty e este config pack, a documentação exige que o datapack de integração BOP fique **acima** do config. Isso confirma que a ordem de prioridade dos dados é parte do contrato operacional.

## 6. Lifecycle de worldgen
Mudanças devem ser validadas em **novos chunks/novas tentativas de geração**. Estruturas já materializadas não devem ser tratadas como retroativamente regeneradas por simples reload. Em servidor, o upstream recomenda global datapack mod ou KubeJS como opções de distribuição.

## 7. Sobreposição e riscos
1. Outro datapack substituir os mesmos structure sets ou biome tags.
2. `separation > spacing` invalidar a configuração.
3. Spawner IDs ou schema mudarem em update do IDAS.
4. Lista de estruturas a evitar ficar incompleta e gerar colisões de worldgen.
5. Ordem incorreta contra integração BOP anular overrides desejados.

## 8. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo correto.
- [ ] Validar `spacing`/`separation` efetivos e relação válida.
- [ ] Conferir biome tags do IDAS.
- [ ] Conferir `integrated_structure_spawner` sem IDs ausentes.
- [ ] Conferir listas `structure set to avoid`.
- [ ] Gerar novos chunks e validar distribuição/colisões.
- [ ] Verificar logs após reload/restart.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
A publicação oficial confirma versão 1.13.7, dependência no mod principal, superfícies de configuração e regra de prioridade com BOP. O catálogo não presume conteúdo interno além do que o upstream documenta.

> Boundary canônico: **IDAS controla estruturas e conteúdo; este datapack controla somente os dados de configuração/worldgen que substitui**.
