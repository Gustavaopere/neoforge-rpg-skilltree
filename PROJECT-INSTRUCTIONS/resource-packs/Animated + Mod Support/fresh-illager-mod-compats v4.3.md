# Fresh Illager Mod Compats

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `4.3 FA Illager Mod Compats.zip`
- **Versão 1.21.1:** 4.3
- **Data da exportação:** 2026-09-11
- **Status de auditoria atual:** BLOQUEADO — a fonte canônica integral do Notion não está acessível para o re-fetch 1:1 atual; `✅-` removido até nova validação.

## Autoridade e limite físico na exportação

- O dossiê Notion registra `4.3 FA Illager Mod Compats.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física atual confirma `entity_model_features-3.3.5-1.21-neoforge.jar` (EMF `3.3.5`), `entity_texture_features-7.2.1-1.21-neoforge.jar` (ETF `7.2.1`) e `goety-3.1.4.jar` (Goety `3.1.4`). Supplementaries `3.9.9` permanece confirmado pela modlist física atual.

## Propriedades do banco

- **Mod:** Fresh Illager Mod Compats
- **Arquivo JAR:** `4.3 FA Illager Mod Compats.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 4.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Compatibility resource pack para Fresh Animations em illagers de múltiplos mods, com overrides de models/animations e regras compartilhadas de Evoker/Vindicator.
- **Dependências:** Fresh Animations 1.10+ + EMF + ETF. Stack físico contém EMF 3.3.5 e ETF 7.2.1; Goety 3.1.4 e Supplementaries 3.9.9 são alvos confirmados presentes.
- **Sobreposição:** Sobrepõe Fresh Animations nos models/rules necessários e pode colidir com outros illager model packs. Goety possui patch separado para Black Book; manter responsabilidades distintas.
- **Compatibilidade/Riscos:** Arquivo físico é 4.3, enquanto a página principal pode expor 4.1; delta 4.3 permanece fail-closed. Deve ficar acima do Fresh Animations. Overlap amplo em Evoker/Vindicator e integrações Goety.
- **Observações:** Arquivo instalado `4.3 FA Illager Mod Compats.zip`. Projeto suporta muitos mods; somente alvos fisicamente confirmados devem ser tratados como ativos no pack.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Fresh Illager Mod Compats + evidência distribuída da build 4.3.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats
- **Atualização/Status:** REAUDITADO EM 29/09/2026 — resource pack físico v4.3 preservado; alvo Supplementaries reconciliado de 3.9.8 para 3.9.9. Requisitos FA/EMF/ETF, lista suportada vs presença real, Evoker/Vindicator, load order, riscos e QA preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `4.3 FA Illager Mod Compats.zip`, versão física `4.3`. O projeto fornece compatibilidades de Fresh Animations para illagers adicionados/modificados por vários mods.

## 1. Papel e authority
Fresh Illager Mod Compats é uma camada de models/animations/resources. Os mods suportados continuam authorities das entidades, AI, raids, spells, drops e spawn. Fresh Animations/EMF/ETF controlam a infraestrutura visual necessária para aplicar os modelos/regras.

## 2. Requisitos publicados
O upstream exige **Fresh Animations 1.10+**, **Entity Model Features (EMF)** e **Entity Texture Features (ETF)** e instrui colocar este pack **acima do Fresh Animations**. No stack físico estão EMF `3.3.5` e ETF `7.2.1`.

## 3. Cobertura suportada pelo projeto
A lista oficial inclui, entre outros: Savage and Ravage, Goety, Illager Invasion, The Graveyard (illagers), Blue Skies (Illager Boss/Gatekeeper), Traders in Disguise, Supplementaries (illager), Friends&Foes (illager), Frostiful, Guard Illager, Biome Makeover Cowboy Illager, The Conjurer, Difficult Raids e integrações adicionais de Goety. **Suporte upstream não significa presença no pack.**

## 4. Alvos fisicamente presentes confirmados
No perfil atual estão **Goety 3.1.4** e **Supplementaries 3.9.9**, portanto essas integrações são alvos reais de QA. Outros nomes da lista oficial só devem ser tratados como ativos depois de confirmação física própria.

## 5. Vindicator/Evoker e compat compartilhada
O autor altera animações de Vindicator e Evoker porque muitos mods reutilizam esses models. Isso cria ampla superfície de overlap: uma mudança em model/animation definition pode afetar mais de um mod, mesmo quando a entidade é visualmente semelhante à vanilla.

## 6. Boundary da versão 4.3
O arquivo físico é `4.3`. A página principal indexada atualmente pode exibir `4.1` como main file, mas há evidência de distribuição do arquivo `4.3 FA Illager Mod Compats.zip`. Preservar `4.3`; changelog específico 4.3 permanece fail-closed sem publicação oficial suficientemente acessível.

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

## 10. Delta upstream 4.3 → 4.5 → 4.6 → 4.7 → 4.8 → 4.9 — não instalado (02/10/2026)

- **Instalado/documentado:** `4.3 FA Illager Mod Compats.zip` / versão `4.3`.
- **Upstream atual:** `4.9 FA Illager Mod Compats.zip`, publicado em 28/09/2026, CurseForge file ID `8999132`.
- A lista oficial de arquivos mostra a sequência posterior `4.5 → 4.6 → 4.7 → 4.8 → 4.9`; **não há release 4.4 publicada** entre 4.3 e 4.5.

### 4.5 — 16/09/2026 — file ID 8893691
- Adiciona **Fresh Expression para o Royal Guard do Goety**, incluindo a villager skin correspondente.
- É diretamente relevante ao pack porque **Goety 3.1.4** está fisicamente presente no snapshot atual.

### 4.6 — 16/09/2026 — file ID 8897878
- Corrige a **textura do horn do Royal Guard**.

### 4.7 — 18/09/2026 — file ID 8912450
- Remove a compatibilidade **Goety: Equipped**.
- Essa remoção altera a superfície de integração que não deve mais ser presumida em builds posteriores.

### 4.8 — 27/09/2026 — file ID 8987234
- A release está confirmada na lista oficial de arquivos e é compatível com 1.21.1.
- O changelog individual não ficou acessível pelos endpoints públicos consultados nesta auditoria; portanto **nenhuma alteração funcional da 4.8 é inferida ou inventada**.

### 4.9 — 28/09/2026 — file ID 8999132
- Corrige a **textura da boca do Hostile Royal Guard**.

### Decisão de instalação

- O conjunto 4.5–4.9 é material para o stack atual por afetar diretamente o Royal Guard/Goety.
- A versão física/documentada permanece `4.3`; **não promover para 4.9** sem nova evidência do ZIP instalado no perfil.

Fontes upstream:
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/8893691
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/8897878
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/8912450
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/8987234
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/8999132
- https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/all

## 11. Bloqueio de certificação Notion → GitHub — 02/10/2026

- A busca atual do workspace e a consulta estruturada da base não recuperaram uma ficha canônica **Fresh Illager Mod Compats** que possa ser fetched integralmente.
- O conteúdo técnico existente foi preservado, e a sequência upstream posterior à 4.3 foi percorrida release por release.
- Sem a origem canônica do Notion, a paridade 1:1 exigida pelo protocolo não pode ser demonstrada nesta execução.
- Pelo protocolo fail-closed, este arquivo permanece **SEM `✅-`** até a fonte canônica estar acessível e ser revalidada integralmente.

## 12. Atualização upstream 5.0 e certificação pendente — 10/10/2026

**Última autoridade física histórica do resource pack:** `4.3 FA Illager Mod Compats.zip` (captura da pasta do perfil de 08/09/2026); nesta rodada o ZIP não foi reobtido. **O dossiê permanece sem `✅-`** porque não existe re-fetch integral da ficha Notion originária para comprovar paridade 1:1, bloqueio explicitado no §11. O simples aparecimento de uma release nova não resolve essa pendência documental.

**Histórico desde o físico:** a seção 10 já registra separadamente as versões **4.5 → 4.6 → 4.7 → 4.8 → 4.9**, incluindo as correções do Hostile Royal Guard em Goety; não criar notas para versões intermediárias não publicadas que não apareçam no índice.

**Nova publicação oficial:** `5.0 FA Illager Mod Compats.zip`, **03/10/2026**, CurseForge file ID **9050536**, que **inclui explicitamente Minecraft 1.21.1** entre as versões suportadas (a listagem da página mostra desde 26.3 até Minecraft 1.14, incluindo 1.21.1). O changelog oficial da 5.0 informa apenas **“Fix Friends & Foes Illusioner doesn't have animations”**. Não atribuir animações adicionais de Goety, otimizações EMF/ETF ou alterações de textures não citadas a esta release.

**Boundary condicional:** a correção do Illusioner de **Friends & Foes** só é observável se o mod e a entidade correspondentes estiverem no pack. A cobertura anterior de **Goety**, Illager Invasion, Savage & Ravage, The Graveyard e outros continua pertencendo ao histórico, mas não equivale a teste visual de 5.0 em cada provider.

**Teste necessário para promoção:** recuperar o ZIP físico atualizado, comparar asset-tree 4.3 vs 5.0 e `pack.mcmeta`, checar priority vs Fresh Animations e Excalibur, spawn/visualização de Friends & Foes Illusioner se disponível, Royal Guard/Goety, model CEM EMF, ETF emissives/variants, dedicated-server irrelevância do overlay, troca de resource packs e F3+T. Revalidar `✅-` apenas quando a fonte canônica Notion estiver acessível e todas as demais exigências do protocolo forem satisfeitas.

**Fonte primária:** https://www.curseforge.com/minecraft/texture-packs/fresh-illager-mod-compats/files/9050536

**Estado:** upstream 5.0 registrada; físico 4.3 na última captura; **SEM CHECK por paridade Notion não certificada**, sem testes executados.

