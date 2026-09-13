# Fresh Animations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81c79ad9d387f31dac17
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `FreshAnimations_v1.10.4.zip`
- **Versão 1.21.1:** 1.10.4
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `FreshAnimations_v1.10.4.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar` (EMF `3.3.5`) e `entity_texture_features-7.2.1-1.21-neoforge.jar` (ETF `7.2.1`). Logs físicos de 08/09 também mostram EMF/ETF sendo carregados no runtime.

## Propriedades do banco

- **Mod:** Fresh Animations
- **Arquivo JAR:** `FreshAnimations_v1.10.4.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.10.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Resource pack de animações dinâmicas para entidades vanilla, usando modelos/animações customizados para aproximar a apresentação dos mobs do estilo dos trailers sem alterar gameplay.
- **Dependências:** Recomendado oficialmente com Entity Model Features e Entity Texture Features. O perfil físico contém EMF 3.3.5 e ETF 7.2.1, que fornecem a infraestrutura usada pelo stack visual.
- **Sobreposição:** Base central de animações de entidades; compats dedicados devem prevalecer nos assets que conciliam Fresh Animations com Mobs/Golems/Creepers Refreshed e outros packs. Excalibur Fresh Animations Patch é outra camada separada e possui seus próprios riscos.
- **Compatibilidade/Riscos:** Fresh Animations 1.10.4 suporta explicitamente 1.21.1. Forte overlap intencional com compats/overhauls de entidades; packs como Mobs Refreshed + FA, Golems Refreshed + FA e Creepers Refreshed + FA precisam de prioridade coerente. Não confundir conflitos de patches específicos com incompatibilidade global do Fresh Animations.
- **Observações:** Arquivo físico `FreshAnimations_v1.10.4.zip`, versão 1.10.4. A release oficial inclui 1.21.1 e corrige animações de illagers/witch, spears, horses, shoulder parrots, Happy Ghast e outros detalhes.
- **Procedência:** CurseForge oficial Fresh Animations 1.10.4 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF 3.3.5/ETF 7.2.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/7670377
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Fresh Animations 1.10.4, suporte 1.21.1, EMF/ETF, changelog 1.10.4, compat stack, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `FreshAnimations_v1.10.4.zip`, versão `1.10.4`. A página oficial do arquivo inclui explicitamente Minecraft `1.21.1`.

## 1. Papel e authority
Fresh Animations é a base de animações/modelos dinâmicos de entidades vanilla do perfil. Minecraft continua authority de entity registry, AI, spawn, stats, drops e persistence; o pack controla somente apresentação.

## 2. Infraestrutura EMF/ETF
A release 1.10.4 é oficialmente recomendada com **Entity Model Features** e **Entity Texture Features**. O stack físico contém EMF `3.3.5` e ETF `7.2.1`, fornecendo a infraestrutura usada pelos custom models/textures.

## 3. Release 1.10.4
O changelog oficial registra correções em swim animations de pillager/vindicator/evoker/witch, spear animations de piglin/zombified piglin/zombie villager/husk, zombie spears, horse armor textures, sheep mouth, zombie villager arms, shoulder parrots e Happy Ghast, além de ajustes de compatibilidade de modelos.

## 4. Ecossistema de compats
O perfil contém várias camadas dedicadas: Creepers Refreshed + FA, Golems Refreshed + FA e Mobs Refreshed + FA, além de extensões oficiais. Esses packs existem justamente para conciliar modelos/animações de bases diferentes e precisam de prioridade coerente acima das bases correspondentes.

## 5. Boundary com patches específicos
Conflitos declarados por um **patch específico** — por exemplo, Excalibur | Fresh Animations Patch — não devem ser generalizados automaticamente para o Fresh Animations base. Cada conflito é registrado no dossiê da camada que o declara.

## 6. Client e reload
É conteúdo visual client-side. Resource reload/relog deve reconstruir models/textures/animations sem alterar estado das entidades, AI, stats ou loot.

## 7. Riscos
1. Outro CEM/resource pack sobrescrever model/animation do mesmo mob.
2. Load order incorreta entre base e compat dedicado.
3. EMF/ETF cache manter model stale após reload.
4. Versão futura do jogo alterar model parts/paths.
5. Mistura parcial de models/textures quando apenas parte do namespace é sobrescrita.

## 8. Matriz de testes
- [ ] Amostrar famílias vanilla variadas de mobs.
- [ ] Illagers/witch em movimento e água.
- [ ] Mobs com spear animations quando aplicável.
- [ ] Horses, shoulder parrots e Happy Ghast.
- [ ] Validar compats Refreshed acima das bases correspondentes.
- [ ] Resource reload/relog sem entidade invisível/deformada.
- [ ] Confirmar que pack on/off não altera AI, spawn, stats ou drops.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma `FreshAnimations_v1.10.4.zip`, suporte a 1.21.1, recomendação de EMF/ETF e o changelog da release. O catálogo mantém escopo estritamente visual.

> Boundary canônico: **Fresh Animations/EMF/ETF controlam apresentação; Minecraft controla integralmente as entidades e seu gameplay**.
