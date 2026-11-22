# More Relics — instalada 1.7.7-forRelics-0.12.8-1.0 | upstream auditada 1.8.0 (não instalada)

## Propriedades do registro

- **Mod:** More Relics
- **Arquivo JAR instalado:** `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`
- **Versão instalada (Minecraft 1.21.1):** `1.7.7-forRelics-0.12.8-1.0`
- **Versão upstream mais recente auditada:** `1.8.0` — **21/11/2026, Release regular, NÃO INSTALADA**; exige linha Relics não-Beta (`0.10.7.8`), diferente de Relics `0.12.8` instalado.
- **Última versão oficialmente compatível identificada para o pack:** `1.7.7-forRelics-0.12.8-1.0`, a build Beta especial atualmente instalada; **não há upgrade físico indicado**.
- **Categoria:** RPG, Magia
- **Tipo de conteúdo:** Mod, Addon
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado para a instalação `1.7.7-forRelics-0.12.8-1.0` e auditado documentalmente até `1.8.0`; **compatibilidade de 1.8.0 com Relics 0.12.8 não demonstrada**; testes em runtime não executados.
- **Estado no pack:** Instalado e documentado no GitHub como `1.7.7-forRelics-0.12.8-1.0`; a 1.8.0 está **somente documentada**, sem alteração de JAR, save ou versão física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/more-relics
- **Função:** Addon de Relics que adiciona novos relics/acessórios e efeitos próprios ao sistema de progressão de Relics.
- **Dependências:** Relics, Curios API, Architectury API e biblioteca(s) declaradas pela release, incluindo ShatterLib/OctoLib conforme a página oficial.
- **Compatibilidade/Riscos:** O par **instalado** More Relics `1.7.7-forRelics-0.12.8-1.0` + Relics `0.12.8` tem suporte oficial específico; **não substituir** pela 1.8.0 Release regular (orientada a Relics `0.10.7.8`). A 1.8.0 documentada acrescenta Homesick (End Cities), evolução Homeward, autoequip Curios, ajustes Made in Heaven/Charred, loot e correções; essas novidades **não estão comprovadas no JAR 1.7.7 instalado**. Permanecem riscos de migração de mundos antigos, save/evolução, combate, loot e integração Curios.
- **Sobreposição:** Expansão do mesmo ecossistema Relics; não substitui o mod-base. A incompatibilidade histórica com Relics 0.12.x foi resolvida pela build física especial para 0.12.8; os gates atuais são persistência/migração, evolução, combate e loot, não redundância.
- **Observações:** Runtime físico `1.7.7-forRelics-0.12.8-1.0`, JAR `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`, SHA-1 `bc220ed291187c97bd1896f1fdd29b2377879ae1`. CurseForge file ID 8859015, Beta NeoForge 1.21.1 de 11/09/2026; conteúdo congelado em 1.7.7 com manutenção por bug fixes.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial More Relics file 8859015 + relations oficiais (Relics, Curios API, Architectury API, ShatterLib/OctoLib).
- **Atualização/Status:** **22/11/2026 — upstream 1.8.0 auditada e incorporada neste `.md`** (Homesick→Homeward, ordem Curios na evolução, Charred e demais deltas). **Versão física permanece 1.7.7-forRelics-0.12.8-1.0**, escolhida por ser a variante oficial para Relics 0.12.8. A 1.8.0 regular não foi instalada e não é compatibilidade certificada para essa linha; manter a versão real no filename e no índice. Nenhum smoke test foi executado.
- **Histórico da decisão:** 2026-09-07 — incorporado como novo mod e decisão explícita do usuário: MANTER. 2026-09-08 — removido do snapshot físico então vigente por incompatibilidade upstream com Relics 0.12.x. 2026-09-11 — upstream publicou build especial com suporte oficial ao Relics 0.12.8. 2026-09-25 — modlist física atual confirma a reintrodução dessa build especial; decisão Manter preservada.
- **Data da última decisão:** 2026-09-07

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #408: JAR `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`, mod id `morerelics`, runtime `1.7.7-forRelics-0.12.8-1.0`, SHA-1 `bc220ed291187c97bd1896f1fdd29b2377879ae1`.
## 1. Identidade, papel e boundary de versão

More Relics é um **addon de Relics**, não um framework de acessórios independente. O projeto oficial o descreve como uma expansão de relics de alta qualidade e informa ambiente Client & Server.

A publicação está dividida em duas linhas relevantes para NeoForge 1.21.1:

1. `morerelics-1.7.7-1.21.1.jar` — release principal de 19/08/2026; a própria página oficial alerta que esta build não-beta exige Relics 0.10.7.8;
2. `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` — Beta de 11/09/2026, file ID 8859015, criada especificamente para Relics 0.12.8.

Para este pack, a segunda linha é a build fisicamente instalada e válida. O autor declara que esta branch compatível permanecerá **congelada no conteúdo 1.7.7** e receberá apenas bug fixes, sem acompanhar automaticamente conteúdo futuro 1.7.8+.

Isso cria dois version gates separados:

- **compatibilidade do provider:** Relics 0.12.8;
- **conteúdo do addon:** More Relics 1.7.7 + build compat 1.0.

Não substituir a build compatível pela release principal de mesmo conteúdo apenas porque ela aparece como “main file” no CurseForge.

## 2. Ownership / autoridade

A autoridade deve permanecer separada:

- **Relics:** framework-base de relics, progressão/estatísticas/evolução compartilhadas e contratos que More Relics consome;
- **Curios:** infraestrutura de slots/equipamento quando utilizada pela stack Relics;
- **Octo-Lib e Architectury:** infraestrutura requerida pela cadeia oficial do Relics/More Relics;
- **More Relics:** ownership exclusivo dos relics, efeitos, evoluções e regras de loot que ele próprio adiciona.

Uma integração própria do pack não deve copiar efeitos de More Relics, aplicar XP/evolução em paralelo ou criar uma segunda settlement path para o mesmo trigger. O provider deve continuar sendo More Relics/Relics conforme o contrato efetivo do artefato.

## 3. Conteúdo oficialmente enumerado para 1.21.1

A página oficial publica 25+ relics e suas rotas de obtenção. Os nomes abaixo são os relics confirmados documentalmente para a linha 1.21.1; IDs de registry **não são inferidos** sem o JAR.

| Relic | Obtenção documentada em 1.21.1 | Observação operacional |
|---|---|---|
| Axolotl Cream | categorias Aquatic e Tropic; chests em jungles, oceans, trial chambers etc. | Loot distribuído por categorias/estruturas; validar tags/loot real. |
| Crown of the Legend | Nether e Bastions | Conteúdo Nether. |
| Eject Button | everywhere com chance baixa | Changelog 1.7.7 adiciona configuração do health threshold. |
| Guts Orb | Bastions, Deserts, Ruined Portals | Loot multi-região. |
| Tyrant Mask | Bastions | Base da evolução para King Crimson. |
| King Crimson | somente por evolução do Tyrant Mask | Não deve aparecer como loot direto se a build seguir a documentação. |
| Slumbering Amulet | Buried Treasure | Base da cadeia de amulets. |
| Whispering Amulet | somente por evolução do Slumbering Amulet | Estágio intermediário. |
| Made in Heaven | somente por evolução do Whispering Amulet | Estágio evoluído. |
| Mass Gauntlet | everywhere com chance baixa | Validar distribuição efetiva para evitar inflação de loot. |
| Opal Necklace | Deserts e Snow/Ice biomes | Loot biome-dependent. |
| Sentient Rust | Mineshafts, Mountains, Swamps | Difere da rota 1.20.1; usar regra 1.21.1. |
| Shieldweave Cape | Caves, Taiga, Mountains e chance baixa no Overworld | Superfície ampla de loot. |
| Bionic Eye | Sculk: Ancient Cities / Deep Dark | Changelog 1.7.7 torna níveis de Vulnerability configuráveis. |
| Thermoseismic Heart | Desert | Loot regional. |
| Biojoint | Nether e Mineshafts | Loot cross-dimension. |
| Whims of Fate | Bastions e Desert | Loot cross-dimension. |
| Depleted Spool | Caves, Mineshafts, Mountains | Base de evolução. |
| Weavers Spool | somente por evolução do Depleted Spool | Não confundir com loot direto. |
| Mood Worm | everywhere com chance baixa | Changelog 1.7.7 altera status/icon/config de duração. |
| VertebraX | End | Loot dimensional. |
| Gravitum Glove | End, Stronghold, Sculk | Loot multi-contexto. |
| Epoch Apple | Dungeon chests | Loot de dungeon. |
| Converging Orb | Woodland Mansions | Base de evolução para Wonder of U. |
| Wonder of U | somente por evolução do Converging Orb | Conteúdo evoluído. |
| Gravitum Strider | End e Sculk | Loot dimensional. |
| Twin Fangs | Pillager Outposts e Mansions | 1.7.7 corrige condição que podia causar hits infinitos. |
| Swiftedge | Mountains | Loot regional. |
| Runic Plate | Mountains | Loot regional. |

A página oficial declara “25+” e atualmente lista mais de 25 nomes. Esta tabela registra todos os nomes e rotas que estavam explicitamente publicados na página oficial consultada em 14/09/2026; não transforma descrições de localização em registry IDs.

## 4. Linhas de evolução confirmadas

Há pelo menos quatro cadeias explicitamente publicadas:

- `Tyrant Mask → King Crimson`;
- `Slumbering Amulet → Whispering Amulet → Made in Heaven`;
- `Depleted Spool → Weavers Spool`;
- `Converging Orb → Wonder of U`.

A evolução pertence ao ecossistema Relics/More Relics. Sistemas próprios de perk/quest devem **observar**, não reproduzir, a transição. Gates importantes: preservar dados/estatísticas da relic conforme o provider, impedir grant duplicado do estágio evoluído e verificar persistência após logout/restart.

## 5. Mudanças funcionais da baseline 1.7.7

O changelog oficial da 1.7.7 documenta mudanças que continuam relevantes para a build compatível porque ela congela o conteúdo em 1.7.7:

### Indicadores e client config

Cada relic pode ter seu **icon indicator** desabilitado individualmente nas configs client-side. Isso é presentation/UI e não deve ser convertido em regra server-side.

### Mood Worm

- status atual passa a ser visível por ícone;
- recebeu buff menor para alinhar as versões Forge/NeoForge;
- correção de typo que fazia NeoForge iniciar com valores de stats incorretos;
- duração de mudança de mood passa a ser configurável em config common.

### Twin Fangs

A release corrige uma condição em que Twin Fangs podia atingir infinitamente sob determinadas circunstâncias. Para o pack, isso é um **regression gate obrigatório de combat settlement**: um trigger não pode reentrar/iterar indefinidamente nem multiplicar dano.

### Eject Button

O health threshold torna-se configurável em common config. A configuração efetiva da instância ainda não foi fornecida; valor/default não é inventado aqui.

### Bionic Eye

Os níveis de Vulnerability passam a ser configuráveis em common config. Validar interação com outros sistemas que também aplicam Vulnerability/equivalentes para evitar stacking não pretendido.

### Cyberpsychosis

O efeito visual também pode renderizar Iron Golems como Wardens. Como é uma superfície de representação, confirmar que nenhum script/IA trata a aparência como troca de entity type real.

## 6. Configuração e dados

Confirmado pela documentação 1.7.7:

- client config para indicadores por relic;
- common config para duração de mood do Mood Worm;
- common config para health threshold do Eject Button;
- common config para níveis de Vulnerability do Bionic Eye.

Não estão confirmados neste ciclo os nomes exatos das chaves, caminhos do arquivo, defaults numéricos nem codecs internos da build 8859015. Extraí-los do JAR/config físico após instalação, em vez de inferi-los.

Loot/obtenção é uma superfície data-driven relevante. Como o pack possui muitos mods de estruturas e loot, validar injeções efetivas em chests, não apenas a descrição do CurseForge.

## 7. Client / Server

O projeto é oficialmente **Client & Server**.

Server-authoritative, por responsabilidade funcional:

- ownership/equip de relics;
- stats e progressão consumidos do Relics;
- evoluções;
- triggers de efeitos com consequência de gameplay;
- geração/seleção de loot;
- configurações common que alteram thresholds/durações/níveis.

Client-side/presentation:

- icon indicators e sua client config;
- renderizações/representações visuais, incluindo o comportamento visual documentado de Cyberpsychosis;
- tooltips e feedback visual.

A modlist física atual confirma o artefato, o mod id, o runtime declarado e o fingerprint SHA-1 da build 8859015. Classes, packets, mixins, configs internas e registry IDs ainda não foram auditados byte a byte.

## 8. Lifecycle e persistência

A mudança histórica de compatibilidade torna lifecycle particularmente crítico. Validar:

- login/relogin com relics equipadas;
- equip/unequip via Curios;
- morte/respawn;
- mudança de dimensão;
- logout/login;
- server restart;
- evolução de relic e persistência do estágio/XP/stats;
- world save criado antes da reintrodução;
- inventário/Curios com relics obtidas após a reintrodução.

O autor alerta que migrar de Relics 0.10.7.8 para a linha 0.12.8 pode causar corrupção de dados em mundos já gerados. O snapshot auditado já possui Relics 0.12.8, o que remove o mismatch anterior, mas **não prova** que os saves não contêm dados históricos de uma instalação anterior. Fazer backup antes do primeiro boot com More Relics reintroduzido.

## 9. Multiplayer

Superfícies mínimas de multiplayer:

- state/XP/evolução deve permanecer por jogador/stack conforme Relics;
- efeitos de combate não podem disparar duas vezes por client prediction + server settlement;
- Twin Fangs é gate explícito contra hits infinitos/reentrância;
- loot/grant deve acontecer exatamente uma vez;
- mudanças common config devem ser coerentes para o servidor inteiro;
- visual de Cyberpsychosis não pode alterar identidade server-side da entidade.

## 10. Integrações concretas com a modlist

### Relics 0.12.8

É a integração central e agora **oficialmente suportada e fisicamente instalada pela build 8859015**. Esta é a razão técnica que sustentou a reintrodução confirmada no pack.

### Curios API

Dependência transitiva/oficial da stack Relics indicada pelo projeto. Validar slots e equip/unequip sem item ghost ou duplicação.

### Architectury / Octo-Lib

Dependências listadas pela página oficial. São infraestrutura; não atribuir gameplay próprio a elas.

### Sistemas de combate/RPG do pack

Epic Fight, attributes, perks e outros sistemas podem observar dano/stats, mas não devem duplicar settlement dos relics. Twin Fangs, Bionic Eye e efeitos de threshold merecem smoke tests cruzados.

### Mods de estruturas/worldgen/loot

A distribuição oficial cita Bastions, Mineshafts, Strongholds, Ancient Cities, Woodland Mansions, Pillager Outposts, Trial Chambers e categorias biome/dimensionais. Como o pack modifica worldgen/estruturas, validar loot tables finais após datapack/resource reload.

## 11. Riscos técnicos

1. **World-data migration:** alerta oficial de corrupção quando uma instalação antiga migra Relics 0.10.7.8 → 0.12.8; manter backup e não generalizar que “agora é seguro” para qualquer save.
2. **Branch drift:** a linha `forRelics-0.12.8` ficará congelada em conteúdo 1.7.7 e pode divergir da linha principal futura.
3. **Provider mismatch:** instalar acidentalmente `morerelics-1.7.7-1.21.1.jar` principal reintroduziria o mismatch com Relics 0.12.8.
4. **Combat reentrancy:** Twin Fangs teve bug de hits infinitos; regressão obrigatória.
5. **Attribute/effect stacking:** Bionic Eye/Vulnerability e outros relic effects podem cruzar sistemas de attributes/damage do pack.
6. **Loot inflation/duplication:** muitos injectors de loot/estruturas coexistem no pack.
7. **Evolution duplication:** scripts/quests que concedam formas evoluídas podem duplicar transições do provider.
8. **Client/server desync:** indicadores e visuais são client; gameplay deve continuar server-authoritative.
9. **Internals não decompilados:** o JAR físico da build 8859015 está confirmado, incluindo mod id/runtime e SHA-1 na modlist; mixins, classes, configs internas e registry IDs ainda exigem inspeção direta caso sejam necessários para uma integração.

## 12. Matriz de testes obrigatória

Confirmações documentais já concluídas nesta auditoria: presença top-level do JAR, mod id `morerelics`, runtime `1.7.7-forRelics-0.12.8-1.0` e SHA-1 físico. A matriz abaixo permanece dedicada aos gates de runtime/comportamento ainda não executados.

- [ ] Dedicated server inicia com Relics 0.12.8 e a build compat sem missing dependency/classloading error.
- [ ] Cliente entra no servidor e sincroniza relics sem registry/network mismatch.
- [ ] Equip/unequip Curios funciona e não duplica item/state.
- [ ] Logout/login preserva relic, progressão e stats.
- [ ] Morte/respawn preserva ou descarta estado exatamente conforme provider, sem ghost slot.
- [ ] Restart do servidor preserva dados de relic/evolução.
- [ ] Cada cadeia de evolução grant-a o estágio seguinte exatamente uma vez.
- [ ] Twin Fangs não produz loop/hits infinitos e cada evento de dano é settled uma vez.
- [ ] Mood Worm inicia com stats coerentes em NeoForge e respeita a duração configurada.
- [ ] Eject Button respeita threshold common configurado.
- [ ] Bionic Eye respeita níveis de Vulnerability configurados e não duplica stacking com outros providers.
- [ ] Cyberpsychosis altera apenas apresentação prevista; Iron Golem não se torna Warden server-side.
- [ ] Loot aparece nas superfícies documentadas sem duplicação por outros mods/datapacks.
- [ ] Client toggles dos icon indicators não alteram gameplay server-side.
- [ ] Backup de mundo restaurável confirmado antes de qualquer boot de validação/migração relevante com a build reintroduzida.

Nenhum item desta matriz foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites

**Evidências confirmadas:**

- `modlist(1).txt` atual: More Relics presente como top-level em `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`, mod id `morerelics`, runtime `1.7.7-forRelics-0.12.8-1.0`, SHA-1 `bc220ed291187c97bd1896f1fdd29b2377879ae1`; Relics 0.12.8 também está presente.
- Notion histórico: More Relics 1.7.7 havia sido removido por incompatibilidade declarada com Relics 0.12.x.
- CurseForge oficial: projeto Client & Server; dependencies via Relics/Curios/Octo-Lib/Architectury; lista pública de 25+ relics e rotas de obtenção.
- CurseForge file ID 8859015, 11/09/2026: build Beta `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` declara suporte oficial a Relics 0.12.8 e congela conteúdo em 1.7.7 com manutenção por bug fixes.
- Changelog oficial 1.7.7: icon toggles, Mood Worm, Twin Fangs, Eject Button, Bionic Eye e Cyberpsychosis.

**Ainda não confirmado:**

- SHA-256 e inspeção byte a byte do JAR;
- mixin configs/classes/registry IDs internos não expostos pela modlist;
- defaults numéricos das novas configs;
- resultado real de boot/multiplayer/save migration no pack.

## 14. Decisão operacional atual — 25/09/2026

A incompatibilidade que sustentava a remoção histórica **não é mais atual** para o par Relics 0.12.8 + file 8859015. A build específica existe justamente para esse provider e a modlist física atual confirma que ela foi reintroduzida.

O estado documental é **instalado / integrado ao catálogo**, com decisão **Manter** preservada. Isso não converte os testes de gameplay, persistência, multiplayer, evolução e migração em resultados aprovados: esses gates continuam explicitamente pendentes até execução própria.

## 15. Auditoria incremental upstream — More Relics 1.8.0 (22/11/2026; release de 21/11; não instalada)

**Identidade e estado físico:** a autoridade do pack permanece **#408**, JAR top-level `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`, versão `1.7.7-forRelics-0.12.8-1.0`, compilação Beta destinada a **Relics 0.12.8**; o pack também possui Relics **0.12.8**. A release posterior do mesmo projeto CurseForge **1269280**, `morerelics-1.8.0-1.21.1.jar` (**File ID 9735896**, 21/11/2026), está classificada como **Release para Minecraft 1.21.1 / NeoForge**, mas **não está instalada**. O catálogo consultado identificou esse salto depois da Beta específica 1.7.7, sem outra publicação intermediária NeoForge 1.21.1 posterior à Beta. O `morerelics-1.8.0-1.20.1.jar` (**9735906**) é um artefato Forge diferente e não pode ser confundido com o NeoForge.

### Changelog específico da 1.8.0 — atribuição por release

- **Novos relics e progressão:** **Homesick** é obtido em **baús de End Cities**; **Homeward** só é obtido por **evolução de Homesick**. O changelog da 1.8.0 confirma os dois nomes e a descrição atualizada do **próprio autor no Modrinth** determina essas rotas. IDs internos, condição de evolução, stats e chances numéricas de loot não foram publicados e não devem ser presumidos.
- **Evolução de relics:** o resultado tenta equipar primeiro diretamente em um slot **Curios**, antes de recorrer ao inventário ou ao drop por falta de espaço. A ordem de fallback, controle de slots e transação de inventário mudam, com risco de item perdido/duplicado sob inventário cheio, slot incompatível e reconexão.
- **Made in Heaven:** ajuste positivo nas duas habilidades ativas: **Charred** passa de **15 para 45 segundos** e acumula até **8 stacks** nas duas habilidades. São mudanças de duração/acúmulo, relevantes a dano, balanceamento, cooldown e persistência do efeito; o changelog não quantifica dano por stack.
- **Configuração de loot:** relics que não nascem pelo sistema de loot do Relics (ex.: **Slumbering Amulet**) passam a admitir ajustes via config. Verificar defaults/chaves no JAR candidato; não inferir efeito retroativo em chests já gerados.
- **Axolotl Cream:** não deve mais acumular consigo mesmo; o changelog não especifica como essa regra interage com efeitos antigos ou stacks armazenados.
- **Twin Fangs:** corrige estado permanentemente travado/bloqueado que podia inutilizar o relic.
- **Localização:** tradução espanhola incluída na distribuição NeoForge; não é funcionalidade de gameplay.

### Compatibilidade impeditiva e decisão fail-closed

**O aviso de compatibilidade do próprio autor é explícito:** a linha **não Beta** de More Relics para Minecraft 1.21.1 exige **Relics 0.10.7.8**, enquanto a instalação física usa **Relics 0.12.8**. Portanto a **1.8.0 Release regular** não é um alvo de upgrade aplicável ao pack atual. Para **Relics 0.12.11**, o autor orienta usar a variante Beta More Relics identificada como `for-0.12.8`; isso reforça que a 1.8.0 regular **não foi declarada compatível com Relics 0.12.8** do pack. O nome **1.8.0** não a torna substituta segura da variante instalada. **Promoção física bloqueada** até existir build para a linha Beta correta, ou prova binária/metadata e QA específicos que resolvam a incompatibilidade. Não alterar a certificação, filename físico, versão instalada, a pasta `Addons + Adventure and RPG + Armor, Tools, and Weapons + Magic` nem outras linhas da modlist.

**QA condicionado ao desbloqueio:** conferir manifests/versões de Relics, Curios, ShatterLib/OctoLib e Architectury; equipamentos de evolução e fallback com inventário cheio; persistência de Curios/relog; evolução/loot entre dimensões; acumulação/duração de Charred; alterações de config em Slumbering Amulet; Axolotl Cream e Twin Fangs; loot, redes, multiplayer e proteção contra dupes em saves pré-existentes. Não foi obtido/inspecionado o JAR 1.8.0 e nenhum teste de runtime foi executado. **Auditoria documental do changelog da release 1.8.0 concluída; upgrade físico não autorizado.**

**Resolução do alvo compatível para o pack atual:** a atualização **1.8.0 foi documentada como evolução upstream**, mas **o maior build oficialmente adequado à variante instalada Relics 0.12.8 continua sendo o 1.7.7-forRelics-0.12.8-1.0 (file 8859015)**. Isso **encerra a escolha da versão compatível** sem recomendar downgrade do Relics base ou troca indevida do arquivo JAR. Para ter Homesick/Homeward no pack, seria necessário que o autor distribuísse uma build específica da linha Beta ou que o conjunto Relics + addons inteiro tivesse uma rota de migração oficial verificada; nenhum desses cenários é provado aqui. **Não há ação de instalação indicada nesta revisão documental.**

**Complemento de fonte primária:** [descrição atualizada pelo autor no Modrinth — Homesick em End City e Homeward por evolução](https://modrinth.com/mod/more-relics).

**Fontes oficiais:** [More Relics 1.8.0 NeoForge 1.21.1 — CurseForge File 9735896](https://www.curseforge.com/minecraft/mc-mods/more-relics/files/9735896); [Beta instalada para Relics 0.12.8 — File 8859015](https://www.curseforge.com/minecraft/mc-mods/more-relics/files/8859015); [página oficial do projeto e aviso de linha não-Beta](https://www.curseforge.com/minecraft/mc-mods/more-relics).
