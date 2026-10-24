# Fresh Animations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
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
- **Estado no pack:** Integrado ao Github
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

> **Resource pack físico confirmado:** `FreshAnimations_v1.10.4.zip`, versão `1.10.4`. A página oficial do arquivo inclui explicitamente Minecraft `1.21.1`.

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

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum arquivo físico do pack, filename documental certificado, posição de inventário, configuração ou save foi alterado.

- **Projeto solicitado:** Fresh Animations
- **Último candidato compatível localizado:** **Fresh Animations 1.11 - 1.20+ (Release, 19/10; suporta 1.21.1). A 1.11.1 - 26.1+ NÃO é candidata para este pack.**
- **Estado:** **CANDIDATO DE RESOURCE PACK**
- **Gate de promoção:** Testar EMF/ETF, packs de compat Fresh Animations, modelos de mobs/NPCs, resource reload e precedência da pilha; não usar a 1.11.1 destinada a 26.1+.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/7670377


## Revisão de releases intermediárias — 22/10/2026 (1.10.4 instalada → 1.11.1 - 1.20+)

**Correção material da revisão anterior:** o [CurseForge filtrado especificamente para Minecraft 1.21.1](https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/all?page=1&version=1.21.1) mostra **FA 1.11.1 - 1.20+ (Release, 19/10/2026)**. Essa publicação é diferente de **FA 1.11.1 - 26.1+ (20/10/2026)**. A frase anterior de que "1.11.1" não serve ao pack é, sem qualificar o **sufixo do arquivo**, falsa.

**Cadeia comprovada na linha 1.21.1:** `1.10.4` (ZIP histórico) → `FA 1.11 - 1.20+` (19/10) → `FA 1.11.1 - 1.20+` (19/10). A `1.10.5` listada no projeto é build para a linha **26.1+**, não um passo intermediário aplicável a Minecraft 1.21.1.

**Mudanças para documentação/QA:** a release 1.11 na outra **linha de artefato, 26.1+**, anuncia animações customizadas de **panda e urso-polar**, consolidação de arquivos de modelo/properties por famílias de mobs e diversos ajustes visuais, incluindo bat, aranha, tartaruga, Happy Ghast e modelos infantis. **Esse changelog de arquivo 26.1+ não comprova, sozinho, a inclusão de todos esses assets na build `1.20+`**; conferir notas e `assets/` específicos de ambas as publicações `1.11 - 1.20+` e `1.11.1 - 1.20+` antes de atribuir mudanças. As diferenças exatas de `1.11.1 - 1.20+` **não foram extraídas nem certificadas**, logo não são inventariadas como fatos.

**Gates:** obter o ZIP oficial `1.11.1 - 1.20+`, verificar filename real, `pack.mcmeta`, `pack_format`/ranges, assets, paths CEM e licença; fazer diff **1.10.4→1.11→1.11.1** por arquivos, especialmente modelos de mobs, materiais animados, nomes de bones e ordem de precedência. Testar EMF `3.3.5` + ETF `7.2.1` instalados, compats Refreshed, Excalibur, variantes de illagers, parrot shoulders, polar bear/panda, wolf, ghast, turtle e modelos customizados; `F3+T`/relogin e shaders sem invisibilidade ou perda de animação. **Não está aprovado substituir `FreshAnimations_v1.10.4.zip`**.

**Fontes:** [CurseForge — lista filtrada para 1.21.1](https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/all?page=1&version=1.21.1); [registro do changelog FA 1.11 **26.1+**, apenas para identificação das áreas afetadas e não como prova da variante 1.20+](https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/9254421); [CurseForge da versão instalada 1.10.4](https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/7670377).

## Notas específicas da 1.11.1 — variante 1.20+ (24/10/2026)

**Nova fonte primária exata:** a [publicação oficial Modrinth `aQjwvDfY`](https://modrinth.com/resourcepack/fresh-animations/version/aQjwvDfY), intitulada **Fresh Animations 1.11.1 - 1.20+**, classifica o ZIP como **Release**, compatível com **Minecraft 1.20.x e 1.21.x** e recomenda **Entity Model Features + Entity Texture Features**. Corresponde à mesma **linha 1.20+** descrita na [listagem filtrada de CurseForge para Minecraft 1.21.1](https://www.curseforge.com/minecraft/texture-packs/fresh-animations/files/all?page=1&version=1.21.1). **A release 1.11.1 para 26.1+ é outro artefato e não é candidata para este pack**.

### Changelog textual do próprio artefato 1.20+

| Publicação | Alteração anunciada | Interpretação restrita à instalação 1.21.1 |
|---|---|---|
| **1.11.1 — 1.20+** | **Removidos arquivos `.properties` desnecessários** | Mudança de organização de recursos com possível efeito de precedência e detecção CEM/ETF, sem presumir quais arquivos foram removidos nem se houve alteração visual sem diff dos ZIPs. |
| **1.11.1 — 1.20+** | **Atualizados arquivos de metadata dos packs** | Comparar `pack.mcmeta` do `1.10.4`, `1.11 - 1.20+` e `1.11.1 - 1.20+`; confirmar formatos declarados e compatibilidade com Minecraft 1.21.1. |
| **1.11.1 — nota explicitamente marcada `26.1+`** | Correções do modelo de **filhotes de porco** e **aldeões bebês** | Embora citadas no changelog cumulativo da página 1.20+, **não atribuir essas correções ao runtime 1.21.1** sem comparação de assets. O sufixo `26.1+` é uma restrição de escopo fornecida pelo próprio autor. |

**Cadeia de publicações compatíveis identificada desde a versão instalada:** `1.10.4 → 1.11 - 1.20+ → 1.11.1 - 1.20+`, ambas as últimas publicadas em **19/10/2026** segundo o CurseForge. O catálogo comprova **a existência** da `1.11`, mas este complemento **não obteve seu changelog do artefato 1.20+ de maneira suficiente** para isolar as novidades `1.10.4→1.11`; nem compara os caminhos do ZIP exato `1.11→1.11.1`. As notas descritas na seção anterior para o ramo `26.1+` permanecem **referência de investigação**, não afirmações sobre a variante 1.20+.

**Verificações ainda não executadas:** inventário de `assets/`, `.properties` e `pack.mcmeta` dos ZIPs 1.10.4/1.11/1.11.1; validação com EMF 3.3.5 e ETF 7.2.1 do snapshot, ordem/prioridade frente a Excalibur Fresh Animations Patch e compats Refreshed, F3+T, shaders, mobs em vanilla e com addons, relog e estabilidade visual. Nenhum ZIP físico, resource pack ativo ou configuração foi alterado.

**Decisão:** `1.11.1 - 1.20+` ganha **changelog oficial próprio**, mas o delta intermediário **1.11** e os diffs de assets ainda precisam ser verificados. **Resource Pack #2 continua no Bloco de Notas.**

## Changelog primário da intermediária exata 1.11.0 - 1.20+ — 24/10/2026

**Descoberta primária:** a API pública oficial do Modrinth confirma a distribuição **[Fresh Animations 1.11.0 - 1.20+ (ID `KYOqeb1P`)](https://modrinth.com/resourcepack/fresh-animations/version/KYOqeb1P)**, **Release**, publicada em **19/10/2026, 11:14:04 UTC** e expressamente compatível com **Minecraft 1.21.1**. O artefato traz hash **SHA-1 `faee9e43f1f2b4f23de9342e175f17503ce93ca0`** segundo a metadata Modrinth. Esta é a publicação **1.20+ exata**, não sua versão homônima `26.1+`. Fonte machine-readable: https://api.modrinth.com/v2/version/KYOqeb1P .

### Mudanças da seção "Release Changes" da versão intermediária 1.11.0

| Superfície | Changelog do autor | Recorte válido para MC 1.21.1 |
|---|---|---|
| Texturas de montarias | Adicionada textura antiga de cavalo zumbi para **1.20.1 até 1.21.10** | Inclui explicitamente 1.21.1; inspecionar seleção de textura e compatibilidade com packs de cavalos |
| Tartaruga e happy ghast | Melhoradas rotação/inclinação da cabeça da tartaruga, bem como rotação suave e inclinação do happy ghast | Conferir animações, versões de entidades e precedência de addons |
| Baby skins e armadura | Parte dos ajustes customizados de texturas de bebê migrou para **extension pack**; particularmente helmets customizados e variantes de villager/zombie villager | **Não presumir** que os assets removidos continuem incluídos no ZIP principal; conferir se há pack de extensão e se é relevante em 1.21.1 |
| Limpeza de modelos | Removido modelo sheep wool undercoat; removida textura baby wolf collar | Verificar compatibilidade com packs que sobrescrevem lã, lobos e armaduras |
| Animações adicionais | Ajustadas animações de morcego clássico, villager nadando e villager sentado | Morcego clássico é especialmente associado a versões antigas; testar o escopo do asset antes de aplicá-lo a 1.21.1 |
| **Correções com janela 1.21.1 explícita** | **Corrigidos filhotes de galinha e de tartaruga para MC 1.20.1 a 1.21.1** | **Diretamente aplicável à versão 1.21.1 deste perfil**, sem depender de interpolação de versões |
| Correções diversas | Posição de item da raposa, alguns modelos de cavalo quebrados, natação do aldeão bebê, salto de urso-polar bebê e UV dos chifres de vaca fria | São notas upstream do artefato 1.20+; verificar se cada entidade/modelo é carregado no jogo da faixa específica |

### Histórico cumulativo anunciado na mesma ficha (não atribuir integralmente à 1.11.0)

A seção anterior **"75 / 90 mobs complete (26.3)"** contém adições extensas para **urso-polar** (walk, attack, run, idle, jump/fall/land, turn, novelty, swim, hurt, eye e bebê) e **panda** (walk, sneeze, sit, eat, fear, roll, lie, upset, run, idle, jump/fall/land, turn, novelty, swim, hurt, eye e bebê). O autor também lista reduções/mesclagens de arquivos de modelos e animações compartilhados entre variantes de mobs, reajustes de texturas e correções visuais de animais. A própria seção menciona explicitamente **texturas de bebês especiais 26.1+**. **Esses itens são changelog cumulativo multiversão**, não comprovação de que todas as novidades ocorreram exclusivamente em `1.10.4→1.11.0` na execução 1.21.1. A separação entre **"Changes"** e **"Release Changes"** é mantida para impedir essa extrapolação.

**Cadeia documentada com fonte exata para as duas intermediárias:** `1.10.4` instalada → [`1.11.0 - 1.20+`](https://modrinth.com/resourcepack/fresh-animations/version/KYOqeb1P) → [`1.11.1 - 1.20+`](https://modrinth.com/resourcepack/fresh-animations/version/aQjwvDfY). A release `1.11.1` acrescenta limpeza de `.properties` não necessários e atualização de metadata; duas notas etiquetadas `26.1+` não foram promovidas para 1.21.1. Os changelogs agora existem para **ambas** as releases intermediárias da variante correta, mas a auditoria de **conteúdo exato** ainda exige diffs de `assets/`, `pack.mcmeta` e testes EMF/ETF. Não foi feita extração nem comparação binária dos ZIPs, alteração de ZIP físico ou QA visual.

**Decisão fail-closed:** a lacuna de changelog da `1.11.0 - 1.20+` foi resolvida por fonte primária, mas a verificação asset-a-asset e QA permanecem. O ZIP instalado é `FreshAnimations_v1.10.4.zip` e o filename documental permanece `✅-fresh-animations v1.10.4.md`. **Resource Pack #2 ainda não deve ser apagado da lista.**

## Inventário criptográfico das três releases 1.20+ — 24/10/2026

**Fonte primária machine-readable:** API de versões do projeto oficial **Fresh Animations**, Modrinth **50dA9Sha**. Nesta revisão foram consultados diretamente [1.10.4 / xN57JJts](https://api.modrinth.com/v2/version/xN57JJts), [1.11.0 - 1.20+ / KYOqeb1P](https://api.modrinth.com/v2/version/KYOqeb1P) e [1.11.1 - 1.20+ / aQjwvDfY](https://api.modrinth.com/v2/version/aQjwvDfY). Os três registros **incluem explicitamente Minecraft 1.21.1** na lista de compatibilidade. Os nomes abaixo são valores da propriedade `files[].filename`, não aproximações da interface web.

| Release distribuída | Arquivo Modrinth exato | ID da versão | Tamanho (bytes) | SHA-1 oficial |
|---|---|---|---:|---|
| **1.10.4** — base histórica instalada | `FreshAnimations_v1.10.4.zip` | `xN57JJts` | **850941** | `73db740a6868e043c5e615c036fca2c912615b6e` |
| **1.11.0 - 1.20+** — intermediária | `FreshAnimations_v1.11_1.20+.zip` | `KYOqeb1P` | **744174** | `faee9e43f1f2b4f23de9342e175f17503ce93ca0` |
| **1.11.1 - 1.20+** — candidata | `FreshAnimations_v1.11.1_1.20+.zip` | `aQjwvDfY` | **743793** | `eaf28f57dee43c469534953517501a021d28cf21` |

**SHA-512 oficial para validação mais forte do download:**

- `1.10.4`: `41258f9bea1a773d823f9a014d0c08206e9e7b339bc538e1538211fff28fadd06878a836d292cbb636ed6829cd2801a509368ae3eb3ad4bedf42190a0d5f7a90`
- `1.11.0 - 1.20+`: `604b4517c87dfc30180bb39370106cd304970ff43cbd6c1c0e8573bb71d25987f188256d6c89e0ace7540bbfd1d909869eeb6fd246fd05ef565889cfefc09ca5`
- `1.11.1 - 1.20+`: `641a03d55c36920cc9a70d891bcca3347b196968bcb5637781f21b417b1ff893ce60652abcf02be872872fc1130ccc6785155ea3b19a0ed484de714b6229c922`

**Datas oficiais UTC:** 1.10.4 em **24/02/2026 01:17:22Z**; 1.11.0 em **19/10/2026 11:14:04Z**; 1.11.1 em **19/10/2026 23:26:39Z**. **Canal Modrinth: `release` nas três publicações exatas**, inclusive a versão base 1.10.4 (ID `xN57JJts`), segundo `version_type` da API consultada. Este estado da publicação não altera a identidade nem certifica o SHA do ZIP físico do perfil.

**Importante sobre o alcance desta nova prova:** os checksums acima pertencem aos **arquivos oficiais disponíveis no Modrinth**. Não foram comparados ao SHA real do ZIP que está no dispositivo do usuário, nem aos ZIPs das respectivas publicações CurseForge (IDs **7670377**, **9254211**, **9261871**). Embora os nomes e a cadeia de versões coincidam com a publicação upstream, **não afirmar igualdade binária entre plataformas sem baixar e calcular hashes de ambas as cópias**. O tamanho da 1.10.4 também não pode ser usado isoladamente para diagnosticar a mudança de assets das candidatas.

**Estado da investigação binária:** as URLs de distribuição oficial foram identificadas, mas o ambiente desta rodada **não conseguiu recuperar os ZIPs para abrir seu conteúdo**. Portanto **nenhum inventário ZIP interno, diff de `assets/` ou `pack.mcmeta`, comparação de propriedades, teste EMF/ETF, teste visual ou relog foi executado**. Os hashes tornam viável atestar futuramente que os três ZIPs obtidos correspondem exatamente às releases da variante certa antes de realizar o diff, mas **não resolvem** por si só a auditoria de assets. **Resource Pack #2 permanece na lista; sem promoção do ZIP físico `FreshAnimations_v1.10.4.zip` e sem renomear o dossier certificado.**
