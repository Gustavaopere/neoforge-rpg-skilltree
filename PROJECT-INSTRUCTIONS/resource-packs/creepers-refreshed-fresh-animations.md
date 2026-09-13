# Creepers Refreshed + Fresh Animations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81b7b1c6d92936ff83b4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `creepers-refreshed-fa-v1.0.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `creepers-refreshed-fa-v1.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou esse `.zip` nem a captura física de 08/09; portanto a presença/versão do pack não foi revalidada diretamente nesta execução e é preservada conforme a procedência do dossiê.
- A modlist JAR-centric de 08/09 não inventaria presença de resource packs. Ela serve apenas para confirmar providers/mods de infraestrutura quando estes aparecem como JARs.

## Propriedades do banco

- **Mod:** Creepers Refreshed + Fresh Animations
- **Arquivo JAR:** `creepers-refreshed-fa-v1.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Compatibility resource pack que integra os 22 variants de Creepers Refreshed ao sistema visual/animações do Fresh Animations.
- **Dependências:** Fresh Animations é obrigatório. O upstream declara que o Creepers Refreshed base NÃO precisa estar carregado. EMF é recomendado para CEM; stack físico contém EMF 3.3.5.
- **Sobreposição:** Sobrepõe models/textures/rules de Creeper necessários ao Fresh Animations; outros creeper model packs podem vencer paths por prioridade. Não altera explosão, AI, spawn ou damage.
- **Compatibilidade/Riscos:** Riscos de overlap com outros creeper CEM/model packs, prioridade incorreta sobre Fresh Animations e rule/model cache stale. O pack base Creepers Refreshed não é requisito desta variante FA.
- **Observações:** Arquivo físico `creepers-refreshed-fa-v1.0.zip`; v1.0 suporta explicitamente Minecraft 1.21.1. O upstream recomenda Entity Model Features em vez de OptiFine.
- **Procedência:** CurseForge oficial Creepers Refreshed + Fresh Animations v1.0 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/creepers-refreshed-fresh-animations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.0, Fresh Animations obrigatório, base Creepers Refreshed dispensável, 22 variants, EMF, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `creepers-refreshed-fa-v1.0.zip`, versão `1.0`, com suporte explícito a Minecraft 1.21.1. Fresh Animations é requisito; o upstream declara que o Creepers Refreshed base não precisa estar carregado.

## 1. Papel e authority
Creepers Refreshed + Fresh Animations é uma camada visual de compatibilidade que leva os **22 variants** do projeto Creepers Refreshed ao pipeline de animação/modelo do Fresh Animations. Minecraft continua authority de AI, fuse, explosion radius/damage, spawn, drops e persistence do Creeper.

## 2. Requisitos confirmados
O upstream exige **Fresh Animations**. Para custom entity models, o autor recomenda **Entity Model Features (EMF)** em vez de OptiFine; o stack físico contém EMF `3.3.5`.

## 3. Boundary importante — base dispensável
Ao contrário de muitos addons de compatibilidade, a própria página declara que o resource pack **Creepers Refreshed base não precisa estar carregado**. Este arquivo já fornece a superfície necessária para os variants compatíveis com Fresh Animations.

## 4. Cobertura visual
A família Creepers Refreshed define 22 variants distribuídos por biomas, name tags e regras adicionais. Esta ficha trata tais variants como apresentação visual; nenhuma variante deve ser interpretada como entidade/gameplay novo sem outra evidência.

## 5. Load order e reload
O pack precisa prevalecer sobre as definições de Creeper do Fresh Animations onde fornece compatibilidade. Reordenar resource packs ou alterar EMF pode exigir resource reload/relog para reconstrução de models/rules.

## 6. Sobreposição e riscos
1. Outro Creeper CEM/model pack disputar os mesmos paths.
2. Prioridade incorreta fazer Fresh Animations vencer o compat.
3. EMF rule/model ficar stale após reload.
4. Variant cair em fallback visual por regra de biome/name tag.
5. Entidade invisível/model deformado em conflito de CEM.

## 7. Matriz de testes
- [ ] Confirmar Fresh Animations ativo.
- [ ] Confirmar EMF `3.3.5` ativo.
- [ ] Amostrar Creepers em múltiplos biomas.
- [ ] Testar variants por name tag quando aplicável.
- [ ] Confirmar animações do Fresh Animations nos variants.
- [ ] Resource reload/relog sem entidade invisível ou model quebrado.
- [ ] Confirmar que pack on/off não altera fuse, explosão, AI ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.0 para 1.21.1, Fresh Animations como requisito, Creepers Refreshed base como dispensável e recomendação de EMF. Não foi feito inventário interno do ZIP asset por asset.

> Boundary canônico: **Fresh Animations/EMF controlam a infraestrutura visual; Minecraft controla o Creeper como entidade e seu gameplay**.
