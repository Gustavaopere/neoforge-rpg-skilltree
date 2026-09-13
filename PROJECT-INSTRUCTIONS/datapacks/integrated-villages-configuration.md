# Integrated Villages- Configuration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81198628c6e22112ec0b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Data Pack, Addon
- **Arquivo:** `integrated_villages_config-1.3.3-1.21.1.zip`
- **Versão 1.21.1:** 1.3.3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `integrated_villages_config-1.3.3-1.21.1.zip` como fisicamente confirmado por captura de Data Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `integrated_villages-1.3.3+1.21.1-neoforge.jar`, mod id `integrated_villages`, runtime `1.3.3+1.21.1-neoforge`.
- O datapack requer o mod principal e configura apenas parâmetros de worldgen expostos por JSON; não substitui a authority das estruturas e integrações do Integrated Villages.
- Mudanças em structure sets e biome tags afetam geração futura. Nenhum reload é tratado como regeneração retroativa de chunks/estruturas já materializados.

## Propriedades do banco

- **Mod:** Integrated Villages- Configuration
- **Arquivo JAR:** `integrated_villages_config-1.3.3-1.21.1.zip`
- **Tipo de conteúdo:** Data Pack, Addon
- **Versão 1.21.1:** 1.3.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen
- **Função:** Datapack oficial de configuração do Integrated Villages para ajustar frequência/afastamento de estruturas e biomas elegíveis para geração.
- **Dependências:** Integrated Villages 1.3.3+1.21.1-neoforge. O datapack é apenas configuração e não faz nada sozinho sem o mod principal.
- **Sobreposição:** Pode conflitar com outros datapacks que alterem os mesmos structure sets ou biome tags do Integrated Villages. O último/maior-prioridade de dados aplicável define os JSONs efetivos.
- **Compatibilidade/Riscos:** Mudanças em spacing/separation e biome tags afetam geração futura; não retroagem sobre chunks já gerados. Valores inválidos ou separation > spacing podem quebrar/invalidar a configuração. Requer o mod Integrated Villages.
- **Observações:** Arquivo instalado `integrated_villages_config-1.3.3-1.21.1.zip`, versão 1.3.3. Upstream documenta `spacing`, `separation` e tags `worldgen/biome/has structure`; requer o mod principal.
- **Procedência:** CurseForge oficial Integrated Villages - Configuration 1.3.3 + captura Data Packs do perfil em 08/09/2026 + modlist física Integrated Villages 1.3.3+1.21.1-neoforge.
- **Fonte:** https://www.curseforge.com/minecraft/data-packs/integrated-villages-configuration
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — config datapack 1.3.3, Integrated Villages 1.3.3, spacing/separation, biome tags, worldgen lifecycle, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Data pack físico confirmado:** `integrated_villages_config-1.3.3-1.21.1.zip`, versão `1.3.3`. O alvo físico atual é Integrated Villages `1.3.3+1.21.1-neoforge`.

## 1. Papel e authority
Integrated Villages- Configuration é o datapack oficial de configuração do mod. **Integrated Villages** continua authority das estruturas e integrações; o datapack define parâmetros de geração expostos por JSON.

## 2. Dependência obrigatória
O upstream declara explicitamente que este datapack **requer o mod Integrated Villages e não faz nada sozinho**. A dependência está satisfeita no perfil físico atual.

## 3. Spacing e separation
A documentação permite alterar `spacing` e `separation` no structure set. `spacing` controla a distância média entre tentativas de geração; `separation` define a distância mínima em chunks. O upstream admite valores de 0 a 4096 e exige que `separation` não ultrapasse `spacing`.

## 4. Biomas elegíveis
A lista de biomas pode ser ajustada pelas tags em `tags/worldgen/biome/has structure`. Isso muda onde novas estruturas podem ser selecionadas, sem alterar o conteúdo interno das estruturas do mod.

## 5. Lifecycle de worldgen
Mudanças afetam **geração futura**. Chunks/estruturas já materializados no mundo não devem ser tratados como regenerados retroativamente por simples reload. Testes devem usar novos chunks ou mundo de QA apropriado.

## 6. Sobreposição e riscos
1. Outro datapack substituir o mesmo structure set.
2. `separation > spacing` produzir configuração inválida.
3. Biome tags excluírem ambientes pretendidos.
4. Teste em chunks antigos mascarar a mudança.
5. Update do mod alterar paths/JSON schema esperados.

## 7. Matriz de testes
- [ ] Validar datapack habilitado junto ao Integrated Villages.
- [ ] Conferir JSON de structure set e valores efetivos.
- [ ] Validar `separation <= spacing`.
- [ ] Conferir tags de biomas elegíveis.
- [ ] Gerar novos chunks e comparar frequência/distribuição.
- [ ] Verificar logs por erros de datapack/worldgen.
- [ ] Confirmar que remover o config pack não remove o conteúdo do mod principal.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma a versão 1.3.3, a dependência no mod principal e a semântica de spacing/separation/biome tags. O catálogo não presume efeitos retroativos em chunks existentes.

> Boundary canônico: **Integrated Villages controla as estruturas; este datapack controla apenas os parâmetros de configuração/worldgen que substitui**.
