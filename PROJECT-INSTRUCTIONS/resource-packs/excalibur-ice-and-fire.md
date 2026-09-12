# Excalibur | Ice and Fire

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db816e8dadf5f408115f7e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_IAFCommunityEdition3.zip`
- **Versão 1.21.1:** `CommunityEditionVersion3`
- **Data da exportação:** 2026-09-11

## Autoridade e divergência documental na exportação

- O dossiê Notion registra `Excalibur_IAFCommunityEdition3.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma `iceandfire-2.1.2.jar`, mod id `iceandfire`, runtime `2.1.2`. Guias históricos ainda citam 2.1.1; **a authority física 2.1.2 vence** para presença/versão atual.
- O mesmo snapshot físico registra Dragon Care `1.3.1 - 1.21.1v` e outros addons separados; eles não são considerados parte do support pack visual sem evidência própria.

## Propriedades do banco

- **Mod:** Excalibur | Ice and Fire
- **Arquivo JAR:** `Excalibur_IAFCommunityEdition3.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** CommunityEditionVersion3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Remaster visual de Ice and Fire adaptado à estética medieval/fantasy do Excalibur, cobrindo múltiplas famílias de criaturas, armaduras, itens e equipamentos exibidos pelo projeto.
- **Dependências:** Excalibur base + Ice And Fire Community Edition 2.1.2. Conteúdo client-side; spawn, AI, dragon stages, breath attacks, taming, loot e persistence permanecem no mod.
- **Sobreposição:** Conflito visual direto com Tabla's Dragon Retextures v6 e True Dovah nos assets coincidentes de dragões/Ice and Fire. O pack de maior prioridade vence path a path.
- **Compatibilidade/Riscos:** O support pack toca ampla superfície visual de Ice and Fire e compete diretamente com Tabla's Dragon Retextures v6 e True Dovah em assets de dragões. Prioridade decide o resultado; não há composição automática garantida.
- **Observações:** Arquivo instalado `Excalibur_IAFCommunityEdition3.zip`; release oficial `CommunityEditionVersion3` para 1.21.1. Changelog registra unificação com a versão 1.20.1 para corrigir texturas ausentes sem packs duplicados.
- **Procedência:** CurseForge oficial Excalibur | Ice and Fire CommunityEditionVersion3 + captura Resource Packs do perfil em 08/09/2026 + modlist física Ice And Fire Community Edition 2.1.2.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — CommunityEditionVersion3, I&F CE 2.1.2, escopo visual, overlap Tabla/True Dovah, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_IAFCommunityEdition3.zip`, release `CommunityEditionVersion3` para Minecraft 1.21.1. O alvo físico atual é Ice And Fire Community Edition `2.1.2`.

## 1. Papel e authority
Excalibur | Ice and Fire remasteriza a apresentação de Ice and Fire para a linguagem medieval/fantasy do Excalibur. **Ice and Fire CE** continua authority de entidades, AI, dragon stages, attacks, damage, taming, riding, loot, recipes e persistence.

## 2. Cobertura confirmada
A publicação e galeria oficiais mostram cobertura ampla de assets de Ice and Fire, incluindo armaduras, itens, Dragonsteel armory, dragon armor, Cyclops, Pixies e dragões remodelados/retexturizados. A ficha não converte a galeria em promessa de cobertura integral de todo namespace.

## 3. Build CommunityEditionVersion3
A release `CommunityEditionVersion3` é específica para Minecraft 1.21.1. O changelog registra unificação com a linha 1.20.1 para corrigir texturas ausentes sem manter packs duplicados.

## 4. Stack físico
O target é Ice And Fire Community Edition `2.1.2`. Como o mod pode possuir assets posteriores à criação do support pack, criaturas, equipamentos e variants precisam de QA no runtime atual.

## 5. Conflitos visuais do stack
`Tabla's Dragon Retextures v6` e `True Dovah` também alteram dragões/assets de Ice and Fire. Nos paths coincidentes, **o resource pack de maior prioridade vence**. Esses packs não devem ser tratados como cumulativos por padrão.

## 6. Load order e reload
O support pack deve ficar acima do Excalibur base. A posição relativa contra Tabla/True Dovah define a aparência final dos assets compartilhados. Resource reload/relog deve mudar apenas apresentação.

## 7. Riscos
1. Conflito Excalibur I&F × Tabla v6 × True Dovah.
2. Asset novo de Ice and Fire CE 2.1.2 sem override correspondente.
3. Mistura visual entre armor/items/dragons por prioridade parcial.
4. Model/texture mismatch em variants ou equipment.
5. Resource reload manter entity/item asset stale.

## 8. Matriz de testes
- [ ] Fire, Ice e Lightning Dragons em múltiplas variants/stages.
- [ ] Cyclops e Pixies mostrados pelo projeto.
- [ ] Dragonsteel armory e dragon armor.
- [ ] Itens/armaduras representativos do mod.
- [ ] Comparar prioridade contra Tabla v6 e True Dovah.
- [ ] Resource reload/relog sem missing textures/models.
- [ ] Confirmar que pack on/off não altera AI, damage, taming ou loot.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma `CommunityEditionVersion3` para 1.21.1, o objetivo visual e o changelog de unificação. A cobertura detalhada é limitada ao que o projeto publica/exibe; não se inventa inventário completo de assets.

> Boundary canônico: **Ice and Fire CE controla gameplay; Excalibur, Tabla e True Dovah disputam somente a apresentação dos assets que substituem**.
