# True Dovah

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db819ca82edba906e1f996
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `True Dovah.zip`
- **Versão própria:** não publicada; não usar a versão do jogo como versão do resource pack
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `True Dovah.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Ice and Fire Community Edition `2.1.2`; essa é a authority do provider-alvo.

## Propriedades do banco

- **Mod:** True Dovah
- **Arquivo JAR:** `True Dovah.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, RPG
- **Função:** Resource pack 16x inspirado em Skyrim para Ice and Fire, retexturizando todos os dragões e conteúdo relacionado a dragon scales, com assets adicionais de skeletons/armors exibidos pelo projeto.
- **Dependências:** Requer Ice and Fire original ou Community Edition; stack físico usa Ice And Fire Community Edition 2.1.2. Frostbite & Brimstone é complemento citado, não requisito obrigatório.
- **Sobreposição:** Tabla's Dragon Retextures v6 altera os mesmos dragões; o pack com maior prioridade vence paths coincidentes. Não tratar os dois como cumulativos por padrão.
- **Compatibilidade/Riscos:** Conflito visual direto com Tabla's Dragon Retextures v6 nos dragões. Pack de 2025 pode não cobrir assets posteriores do I&F CE 2.1.2. Load order decide qual textura prevalece.
- **Observações:** Arquivo instalado `True Dovah.zip`; upstream não publica versão semântica própria, portanto `Versão 1.21.1` permanece vazia. Release oficial 1.21.1 de 14/07/2025.
- **Procedência:** CurseForge oficial True Dovah + captura do perfil RPG em 08/09/2026 + modlist física Ice and Fire Community Edition 2.1.2.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/true-dovah
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; I&F CE 2.1.2, sem versão semântica, dragons/scales/skeletons/armors, conflito Tabla v6, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `True Dovah.zip`, Release para Minecraft 1.21.1. O projeto não publica versão semântica própria; o campo de versão permanece vazio. O alvo físico é Ice And Fire Community Edition `2.1.2`.

## 1. Papel e authority
True Dovah é um resource pack 16x inspirado em Skyrim para Ice and Fire. Ele muda a apresentação de dragões e conteúdo relacionado; Ice and Fire CE continua authority de entidades, variants, combat, taming, loot e persistence.

## 2. Cobertura confirmada
O upstream declara mudanças em **todos os dragons** e em conteúdo relacionado a **dragon scales**, buscando maior detalhe e cores mais vibrantes. A galeria também mostra dragonsteel armors, dragon skeletons e diferentes dragon armors; essas superfícies são visuais.

## 3. Dependência e target físico
O projeto requer Ice and Fire original **ou Community Edition**. O pack atual usa Community Edition `2.1.2`, portanto a dependência-base está satisfeita. Frostbite & Brimstone é citado como complemento visual compatível, não requisito obrigatório.

## 4. Versão
`True Dovah.zip` não expõe versão semântica no filename e a página oficial publica o arquivo pelo nome simples. **Não inventar versão.** A release oficial para 1.21.1 é de 14/07/2025.

## 5. Conflito direto com Tabla's Dragon Retextures
O pack `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip` também substitui Fire/Ice/Lightning dragon textures. Nos paths coincidentes, **o resource pack de maior prioridade vence**. Os dois devem ser tratados como alternativas/overrides visuais parciais, não como duas camadas automaticamente cumulativas.

## 6. Load order e reload
Se True Dovah estiver acima de Tabla, suas dragon textures prevalecem onde os paths coincidirem; se estiver abaixo, Tabla prevalece. Resource reload/relog deve trocar somente aparência.

## 7. Riscos
1. Conflito visual direto True Dovah × Tabla v6.
2. Ice and Fire CE 2.1.2 possuir asset posterior ao pack de 2025.
3. Dragon armor/skeleton/scales ficar em estilo misto conforme prioridade.
4. Outro Ice and Fire support pack sobrescrever paths.
5. Resource reload manter entity texture cache stale.

## 8. Matriz de testes
- [ ] Fire/Ice/Lightning dragons em múltiplas variants/stages.
- [ ] Dragon scales e items relacionados.
- [ ] Dragon skeletons.
- [ ] Dragonsteel e outras dragon armors mostradas pelo projeto.
- [ ] Comparar True Dovah acima e abaixo de Tabla v6.
- [ ] Resource reload/relog sem missing texture/model.
- [ ] Confirmar que pack on/off não altera dragon behavior.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma suporte a Ice and Fire/Community Edition, todos os dragons e dragon-scales-related content. A galeria confirma superfícies adicionais de skeleton/armor. Sem versão semântica publicada, o catálogo preserva o campo vazio.

> Boundary canônico: **Ice and Fire CE controla gameplay; True Dovah e Tabla disputam apenas a aparência dos assets sobrepostos, resolvida por prioridade de resource pack**.
