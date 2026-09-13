# Excalibur | Oh The Biomes We've Gone Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db819f86ecc0e469893624
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_BWG_0.1_1.21.1.zip`
- **Versão 1.21.1:** 0.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_BWG_0.1_1.21.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`, mod id `biomeswevegone`, runtime `2.6.0`.
- O upstream da build visual 0.1 declara explicitamente estado WIP e ausência dos wood types. Essa limitação é preservada sem converter blocos/ícones cobertos em claim de cobertura integral.
- Bridges como Dynamic Trees - BWG são projetos separados: worldgen/trees continuam sob seus providers próprios; este resource pack controla apenas apresentação dos assets que contém.

## Propriedades do banco

- **Mod:** Excalibur | Oh The Biomes We've Gone Support
- **Arquivo JAR:** `Excalibur_BWG_0.1_1.21.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Worldgen
- **Função:** Compatibility resource pack WIP que adapta blocos e ícones de Oh The Biomes We've Gone ao estilo medieval 16x do Excalibur; wood types ainda não estão cobertos segundo o upstream.
- **Dependências:** Uso visual pretendido: Excalibur base + Oh The Biomes We've Gone. Stack físico atual: Excalibur V26.1_01 + BWG 2.6.0. Não é dependência de gameplay/servidor.
- **Sobreposição:** Sobrepõe assets de BWG presentes no ZIP. Outros retextures do mesmo namespace podem prevalecer por prioridade; wood types ausentes permanecem sob o visual de outro provider/default.
- **Compatibilidade/Riscos:** WIP explícito: wood types ainda estão ausentes. Riscos de fallback visual com BWG 2.6.0, assets renomeados/adicionados, metadata de animação, load order incorreto e colisão com outros retextures do namespace BWG.
- **Observações:** Arquivo instalado `Excalibur_BWG_0.1_1.21.1.zip`, release 0.1 de 29/12/2025. Upstream declara blocos adaptados ao Excalibur, ícones integrados ao tema e wood types ainda faltando.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da build 0.1 para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-oh-the-biomes-weve-gone-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; BWG 2.6.0, escopo WIP 0.1, wood types ausentes, load order, resource reload, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_BWG_0.1_1.21.1.zip`, versão `0.1`, Release para Minecraft 1.21.1. O projeto é um support pack **WIP** para Oh The Biomes We've Gone.

## 1. Papel e authority
Excalibur | Oh The Biomes We've Gone Support altera somente assets visuais. **Oh The Biomes We've Gone 2.6.0** continua authority de biomas, blocos, madeira, worldgen, loot e comportamento. Excalibur continua o pack visual base.

## 2. Cobertura confirmada
O upstream afirma que os blocos foram adaptados ao estilo Excalibur e que os ícones foram ajustados para manter a mesma linguagem visual. Ao mesmo tempo, documenta explicitamente que **wood types ainda estão faltando**. Portanto a build 0.1 não deve ser descrita como cobertura integral.

## 3. Stack físico e drift
O alvo físico é `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`. Como a build visual 0.1 é WIP, conteúdo adicionado ou renomeado em BWG 2.6.0 pode cair no visual padrão mesmo fora dos wood types já conhecidos como ausentes.

## 4. Load order
O support pack precisa ficar acima do Excalibur base para seus overrides de BWG prevalecerem. Outro resource pack com prioridade maior pode substituir assets individualmente.

## 5. Client e resource reload
É conteúdo client-side. Ativar, remover ou reordenar provoca resource reload e deve alterar apenas textures/models/animations/icons. Nenhuma mudança visual deve ser interpretada como alteração de biome placement, block state ou geração de mundo.

## 6. Sobreposição e riscos
Riscos principais: mistura de estilo por wood types ausentes; asset path novo sem cobertura; animação/metadata incompatível; load order incorreto; outro retexture de BWG vencendo o mesmo caminho; cache visual stale após reload.

## 7. Matriz de testes
- [ ] Conferir blocos representativos de múltiplos biomas BWG.
- [ ] Identificar claramente wood types ainda sem suporte.
- [ ] Conferir ícones de itens/blocos cobertos.
- [ ] Testar assets animados sem warnings/missing frames.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing texture/model.
- [ ] Conferir conteúdo recente de BWG 2.6.0 para fallback visual.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
A build oficial 0.1 para 1.21.1 confirma o caráter WIP, cobertura de blocos/ícones e ausência de wood types. A modlist física confirma BWG `2.6.0`. O ZIP não foi inventariado internamente; contagem exata de assets permanece não confirmada.

> Boundary canônico: **BWG controla worldgen e conteúdo; este support pack controla apenas os assets visuais que efetivamente substitui**.
