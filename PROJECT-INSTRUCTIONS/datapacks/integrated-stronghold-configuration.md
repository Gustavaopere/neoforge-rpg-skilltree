# Integrated Stronghold- Configuration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db817aa85bcd19d7e8248c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `integrated_stronghold_config_neoforge-1.1.4-1.21.1.zip`
- **Versão 1.21.1:** 1.1.4
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `integrated_stronghold_config_neoforge-1.1.4-1.21.1.zip` como fisicamente confirmado por captura do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `integrated_stronghold-1.1.4+1.21.1-neoforge.jar`, mod id `integrated_stronghold`, runtime `1.1.4+1.21.1-neoforge`.
- O projeto classifica o arquivo como configuration datapack; a seção Resource Packs do site não muda sua natureza operacional.
- O datapack configura structure set, biome tags e spawner data. Reload não implica regeneração retroativa de strongholds já materializados.

## Propriedades do banco

- **Mod:** Integrated Stronghold- Configuration
- **Arquivo JAR:** `integrated_stronghold_config_neoforge-1.1.4-1.21.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen
- **Função:** Datapack geral de configuração do Integrated Stronghold para ajustar frequência da estrutura, biomas elegíveis e mobs que aparecem ao longo do stronghold.
- **Dependências:** Integrated Stronghold 1.1.4+1.21.1-neoforge. O upstream declara que o config pack requer o mod principal e não faz nada sozinho.
- **Sobreposição:** Pode disputar structure set, biome tags e spawner data do Integrated Stronghold com outros datapacks/configs. Prioridade de dados define os JSONs efetivos.
- **Compatibilidade/Riscos:** Datapack de configuração/worldgen; alterações afetam novas tentativas de geração e podem conflitar com packs que sobrescrevam structure sets, biome tags ou integrated_structure_spawners. `separation` não pode ser maior que `spacing`.
- **Observações:** Arquivo físico `integrated_stronghold_config_neoforge-1.1.4-1.21.1.zip`, versão 1.1.4, release oficial 1.21.1. O projeto o classifica como configuration datapack apesar da seção Resource Packs do site.
- **Procedência:** CurseForge oficial Integrated Stronghold- Configuration 1.1.4 + captura do perfil em 08/09/2026 + modlist física Integrated Stronghold 1.1.4+1.21.1-neoforge.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/integrated-stronghold-config
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — config datapack Integrated Stronghold 1.1.4, spacing/separation, biome tags, spawners, worldgen lifecycle, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Datapack físico confirmado:** `integrated_stronghold_config_neoforge-1.1.4-1.21.1.zip`, versão `1.1.4`, release oficial para Minecraft 1.21.1. O alvo físico é Integrated Stronghold `1.1.4+1.21.1-neoforge`.

## 1. Papel e authority
Integrated Stronghold- Configuration é o datapack geral de configuração do mod. **Integrated Stronghold** continua authority da estrutura, salas, puzzles, loot, integrações e demais conteúdo; o datapack altera somente parâmetros/dados expostos.

## 2. Dependência obrigatória
O upstream declara explicitamente que o pack **requer o mod principal e não faz nada sozinho**. A dependência está satisfeita no perfil atual.

## 3. Cobertura de configuração
A documentação confirma ajuste de `spacing` e `separation` no structure set, edição de biomas em `tags/worldgen/biome/has structure` e alteração de mob spawns nos arquivos `integrated_structure_spawners`.

## 4. Regras de geração
`spacing` é a distância média entre tentativas vizinhas; `separation` é a distância mínima em chunks. Ambos aceitam 0–4096 e `separation` não pode exceder `spacing`.

## 5. Lifecycle de worldgen
Alterações devem ser verificadas em novas tentativas de geração/novos chunks. Um reload não deve ser tratado como regeneração retroativa de strongholds já materializados. Para servidor, o upstream recomenda distribuição via global datapack mod ou KubeJS.

## 6. Sobreposição e riscos
1. Outro datapack substituir o mesmo structure set.
2. `separation > spacing` invalidar a configuração.
3. Biome tags excluírem regiões pretendidas.
4. Spawner IDs ficarem obsoletos após update.
5. Testes em chunks antigos mascararem mudanças.

## 7. Matriz de testes
- [ ] Confirmar datapack habilitado no mundo.
- [ ] Validar `spacing`/`separation` e relação válida.
- [ ] Conferir biome tags.
- [ ] Conferir integrated_structure_spawners.
- [ ] Gerar novos chunks e localizar stronghold de QA.
- [ ] Verificar logs após reload/restart.
- [ ] Confirmar que remover o config não remove o mod principal.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma 1.1.4, dependência obrigatória e as três superfícies de configuração publicadas. Não se inventam knobs adicionais não documentados.

> Boundary canônico: **Integrated Stronghold controla a estrutura e gameplay; este datapack controla somente parâmetros de configuração/worldgen**.
