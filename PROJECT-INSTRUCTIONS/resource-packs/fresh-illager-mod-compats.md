# Fresh Illager Mod Compats

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81299069f67aab26493e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `4.3 FA Illager Mod Compats.zip`
- **Versão 1.21.1:** 4.3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `4.3 FA Illager Mod Compats.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar` (EMF `3.3.5`), `entity_texture_features-7.2.1-1.21-neoforge.jar` (ETF `7.2.1`) e `goety-3.1.4.jar` (Goety `3.1.4`). Supplementaries `3.9.8` permanece confirmado pelo snapshot/dossiê físico já catalogado.

## Propriedades do banco

- **Mod:** Fresh Illager Mod Compats
- **Arquivo JAR:** `4.3 FA Illager Mod Compats.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 4.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Compatibility resource pack para Fresh Animations em illagers de múltiplos mods, com overrides de models/animations e regras compartilhadas de Evoker/Vindicator.
- **Dependências:** Fresh Animations 1.10+ + EMF + ETF. Stack físico contém EMF 3.3.5 e ETF 7.2.1; Goety 3.1.4 e Supplementaries 3.9.8 são alvos confirmados presentes.
- **Sobreposição:** Sobrepõe Fresh Animations nos models/rules necessários e pode colidir com outros illager model packs. Goety possui patch separado para Black Book; manter responsabilidades distintas.
- **Compatibilidade/Riscos:** Arquivo físico é 4.3, enquanto a página principal pode expor 4.1; delta 4.3 permanece fail-closed. Deve ficar acima do Fresh Animations. Overlap amplo em Evoker/Vindicator e integrações Goety.
- **Observações:** Arquivo instalado `4.3 FA Illager Mod Compats.zip`. Projeto suporta muitos mods; somente alvos fisicamente confirmados devem ser tratados como ativos no pack.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Fresh Illager Mod Compats + evidência distribuída da build 4.3.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; v4.3 física, requisitos FA/EMF/ETF, lista suportada vs presença real, Evoker/Vindicator, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `4.3 FA Illager Mod Compats.zip`, versão física `4.3`. O projeto fornece compatibilidades de Fresh Animations para illagers adicionados/modificados por vários mods.

## 1. Papel e authority
Fresh Illager Mod Compats é uma camada de models/animations/resources. Os mods suportados continuam authorities das entidades, AI, raids, spells, drops e spawn. Fresh Animations/EMF/ETF controlam a infraestrutura visual necessária para aplicar os modelos/regras.

## 2. Requisitos publicados
O upstream exige **Fresh Animations 1.10+**, **Entity Model Features (EMF)** e **Entity Texture Features (ETF)** e instrui colocar este pack **acima do Fresh Animations**. A authority física confirma EMF `3.3.5` e ETF `7.2.1`.

## 3. Cobertura suportada pelo projeto
A lista oficial inclui, entre outros: Savage and Ravage, Goety, Illager Invasion, The Graveyard (illagers), Blue Skies (Illager Boss/Gatekeeper), Traders in Disguise, Supplementaries (illager), Friends&Foes (illager), Frostiful, Guard Illager, Biome Makeover Cowboy Illager, The Conjurer, Difficult Raids e integrações adicionais de Goety. **Suporte upstream não significa presença no pack.**

## 4. Alvos fisicamente presentes confirmados
No perfil atual estão **Goety 3.1.4** e **Supplementaries 3.9.8**, portanto essas integrações são alvos reais de QA. Outros nomes da lista oficial só devem ser tratados como ativos depois de confirmação física própria.

## 5. Vindicator/Evoker e compat compartilhada
O autor altera animações de Vindicator e Evoker porque muitos mods reutilizam esses models. Isso cria ampla superfície de overlap: uma mudança em model/animation definition pode afetar mais de um mod, mesmo quando a entidade é visualmente semelhante à vanilla.

## 6. Boundary da versão 4.3
O arquivo físico é `4.3`. A página principal indexada atualmente pode expor `4.1` como main file, mas há evidência de distribuição do arquivo `4.3 FA Illager Mod Compats.zip`. Preservar `4.3`; changelog específico 4.3 permanece fail-closed sem publicação oficial suficientemente acessível.

## 7. Riscos e overlaps
1. Pack abaixo de Fresh Animations perder overrides.
2. EMF/ETF rule/model falhar após reload.
3. Goety possui patch separado para problema de shake do Black Book; não confundir as responsabilidades.
4. Outro illager model pack disputar Evoker/Vindicator.
5. Mod suportado upstream não estar instalado.

## 8. Matriz de testes
- [ ] Confirmar pack acima do Fresh Animations.
- [ ] Confirmar EMF 3.3.5 + ETF 7.2.1 ativos.
- [ ] Testar Evoker e Vindicator vanilla.
- [ ] Testar illagers do Goety presentes no perfil.
- [ ] Testar superfície de Supplementaries aplicável.
- [ ] Verificar Black Book/Goety com patch específico quando relevante.
- [ ] Resource reload/relog sem entidade invisível ou model quebrado.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma requisitos, load order e lista de mods suportados. A versão `4.3` é mantida pela evidência física/distribuída; o delta exato da 4.3 não é inventado.

> Boundary canônico: **o pack controla compatibilidade visual de illagers; cada mod continua controlando a entidade e seu gameplay**.
