# Integrated Villages- Configuration — v1.4.0

## Estado documental vigente — 27/11/2026

- **Arquivo ZIP instalado declarado:** `integrated_villages_config-1.4.0-1.21.1.zip` (basename reportado pelo catálogo secundário Modgrade; ficha individual do CurseForge não recuperada).
- **Versão instalada declarada:** `1.4.0` — Minecraft 1.21.1, no perfil NeoForge.
- **Estado no pack:** atualização declarada pelo usuário em 27/11/2026; ZIP local não inspecionado.
- **Estado da pesquisa:** identidade e categorias oficiais confirmadas; publicação recente corroborada por catálogo oficial filtrado e versão/filename por catálogo secundário. File ID, ficha individual, changelog, delta binário e metadata não confirmados. Não atribuir certificação integral do delta; selo herdado refere-se à auditoria histórica.
- **Atualização/Status:** 1.3.3 → 1.4.0. Catálogo oficial indexado em 27/11 indica atualização de 25/11/2026 para 1.21.1; Modgrade reporta release e filename da mesma data. A página direta do projeto ainda retorna a listagem antiga 1.3.3, divergência explícita. Registro declarativo, não inspeção física.
- **Dependências:** Integrated Villages obrigatório; índice de mods #336 já registra 1.4.0+1.21.1-neoforge. Piso exato de dependência do ZIP não confirmado; compatibilidade de runtime não demonstrada.
- **Compatibilidade/Riscos:** overrides de structure sets e tags de biomas, precedência de datapacks, schemas de worldgen e geração futura; não assumir regeneração retroativa nem homologação de saves.
- **Categorias CurseForge:** Adventure + Fantasy + Mod Support + Utility (classe técnica Data Packs excluída).
- **Procedência:** declaração do usuário; [projeto oficial 1042146](https://www.curseforge.com/minecraft/data-packs/integrated-villages-configuration), [catálogo oficial 1.21.1 ordenado por última atualização](https://www.curseforge.com/minecraft/search?class=data-packs&page=1&pageSize=50&sortBy=latest+update&version=1.21.1), [perfil oficial do autor](https://www.curseforge.com/members/CraisinLord/projects), [catálogo secundário atualizado](https://modgrade.com/minecraft/datapack/integrated-villages-configuration).

## Auditoria incremental — 1.3.3 → 1.4.0

A lista oficial histórica registra 1.3.3 para 1.21.1 em 28/05/2026. Nenhuma versão intermediária posterior da mesma linha foi localizada; a lista de arquivos recuperada está desatualizada, portanto a ausência de intermediárias não é certificada. O catálogo oficial atualizado registra mudança em 25/11/2026; o catálogo secundário identifica 1.4.0 para 1.21.1 e separadamente 1.4.0 para 1.20.1. Não confundir as duas variantes. Não se obteve a ficha de arquivo, changelog específico ou ZIP para comparação; não inventar file ID, hash, pack_format, conteúdo removido ou alterações individuais.

A descrição oficial mantém a função de configuração do mod principal: spacing e separation (0–4096, separation não superior a spacing) e tags de biomas. Essa é documentação de função, não prova de que cada JSON tenha sido mantido sem alteração na 1.4.0. Alterações do mod principal não são automaticamente atribuíveis ao datapack. A contagem de estruturas/loot/advancements do catálogo secundário não prova quais entradas mudaram desde 1.3.3. Para fechar o delta, comparar ZIPs, pack.mcmeta, JSONs/NBTs, tags, prioridades e schema contra o mod principal. Nenhum teste de runtime realizado; não é veto à sincronização declarativa.

## Histórico documental preservado — anterior à declaração de 27/11/2026

Os campos de instalação e snapshots abaixo pertencem à auditoria histórica de 1.3.3. Não representam a versão vigente. Mantidos integralmente para rastreabilidade.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
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
- **Estado no pack:** Integrado ao Github
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
