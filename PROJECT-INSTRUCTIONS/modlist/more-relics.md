# More Relics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion histórica:** https://app.notion.com/p/3d469db9f0db81ed8169c4d8baa15151
- **Estado no pack antes desta atualização:** Removido/desativado — o snapshot físico canônico disponível não contém More Relics top-level
- **Autoridade física ainda verificada:** `modlist.txt` — Relics `relics-1.21.1-0.12.8.jar` está presente; More Relics está ausente do snapshot físico recebido
- **Artefato selecionado para reintrodução:** `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` — CurseForge file ID `8859015`, Beta, NeoForge 1.21.1, publicado em 11/09/2026
- **Versão de conteúdo upstream:** 1.7.7; **build de compatibilidade:** `forRelics-0.12.8-1.0`
- **Data da auditoria GitHub:** 2026-09-14

> **STATUS FAIL-CLOSED.** O bloqueio que motivou a remoção anterior mudou materialmente: o autor publicou uma build especial que **oficialmente suporta Relics 0.12.8**, exatamente a versão do Relics presente no snapshot físico do pack. A reintrodução é tecnicamente justificável, mas ainda não pode ser marcada como fisicamente instalada nem validada em runtime até recebermos a nova modlist/JAR após a atualização do CurseForge.

## Propriedades equivalentes do catálogo

- **Mod:** More Relics
- **Arquivo JAR alvo:** `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar`
- **Mod ID:** `morerelics` — identidade histórica do projeto; confirmar novamente no JAR alvo quando o binário físico for disponibilizado
- **Versão 1.21.1:** conteúdo 1.7.7 / build compat Relics 0.12.8 `1.0`
- **Minecraft / loader:** Minecraft 1.21.1 / NeoForge
- **Ambiente oficial:** Client & Server
- **Categoria:** RPG, Magia, Addon, Equipamentos
- **Função:** addon do framework Relics que adiciona 25+ relics próprios, incluindo linhas de evolução, efeitos passivos/ativos e loot distribuído por estruturas/biomas.
- **Dependências oficiais:** Relics e suas dependências Curios, Octo-Lib e Architectury. O pack físico confirma Relics 0.12.8; as versões físicas das demais dependências devem continuar sendo validadas pela modlist.
- **Estado no pack:** Reintrodução selecionada; instalação física pendente de re-fetch da modlist/JAR
- **Estado da pesquisa:** Verificado documentalmente; binário alvo ainda não auditado localmente
- **Decisão:** Reintroduzir usando exclusivamente a build oficial para Relics 0.12.8
- **Sobreposição:** expande o mesmo ecossistema do Relics; não substitui o mod-base e não deve assumir ownership do framework de XP/evolução compartilhado.
- **Compatibilidade/Riscos:** a build 8859015 é a primeira publicação desta linha que declara suporte oficial ao Relics 0.12.8. O autor adverte contra migração de mundos que ainda usam Relics 0.10.7.8 porque atualizar a dupla pode corromper dados. O pack já está em Relics 0.12.8 no snapshot físico auditado; ainda assim, save existente, dados por jogador e relic stacks precisam de regressão antes de considerar a reintrodução aprovada.
- **Fonte principal:** CurseForge oficial do projeto e changelog oficial do arquivo 8859015.

## 1. Identidade, papel e boundary de versão

More Relics é um **addon de Relics**, não um framework de acessórios independente. O projeto oficial o descreve como uma expansão de relics de alta qualidade e informa ambiente Client & Server.

A publicação está dividida em duas linhas relevantes para NeoForge 1.21.1:

1. `morerelics-1.7.7-1.21.1.jar` — release principal de 19/08/2026; a própria página oficial alerta que esta build não-beta exige Relics 0.10.7.8;
2. `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` — Beta de 11/09/2026, file ID 8859015, criada especificamente para Relics 0.12.8.

Para este pack, apenas a segunda linha é candidata válida. O autor declara que esta branch compatível permanecerá **congelada no conteúdo 1.7.7** e receberá apenas bug fixes, sem acompanhar automaticamente conteúdo futuro 1.7.8+.

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

Não há evidência nesta auditoria para declarar classes ou packets específicos da build 8859015; isso permanece pendente de inspeção do binário.

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

É a integração central e agora **oficialmente suportada pela build 8859015**. Esta é a razão técnica para reconsiderar a remoção.

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
9. **Unknown binary internals:** a build 8859015 ainda não foi entregue como JAR físico para inspeção de metadata, mixins, configs ou fingerprints.

## 12. Matriz de testes obrigatória

- [ ] Nova modlist física confirma `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` como top-level ativo.
- [ ] Extrair metadata do JAR e confirmar mod id/runtime declarado, mixin configs e dependências sem inferência.
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
- [ ] Backup de mundo restaurável criado antes do primeiro boot da reintrodução.

Nenhum item desta matriz foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites

**Evidências confirmadas:**

- `modlist.txt` recebida: Relics 0.12.8 presente; More Relics ausente do snapshot físico atual.
- Notion histórico: More Relics 1.7.7 havia sido removido por incompatibilidade declarada com Relics 0.12.x.
- CurseForge oficial: projeto Client & Server; dependencies via Relics/Curios/Octo-Lib/Architectury; lista pública de 25+ relics e rotas de obtenção.
- CurseForge file ID 8859015, 11/09/2026: build Beta `morerelics-1.7.7-forRelics-0.12.8-1.0-1.21.1.jar` declara suporte oficial a Relics 0.12.8 e congela conteúdo em 1.7.7 com manutenção por bug fixes.
- Changelog oficial 1.7.7: icon toggles, Mood Worm, Twin Fangs, Eject Button, Bionic Eye e Cyberpsychosis.

**Ainda não confirmado:**

- hash SHA-1/SHA-256 do novo JAR;
- metadata interna exata da build 8859015;
- mixin configs/classes/registry IDs;
- defaults numéricos das novas configs;
- resultado real de boot/multiplayer/save migration no pack.

## 14. Decisão operacional desta atualização

A incompatibilidade que sustentava a remoção **não deve mais ser tratada como atual** para o par Relics 0.12.8 + file 8859015. A build específica existe justamente para esse provider. Portanto, a decisão GitHub passa de “removido por incompatibilidade upstream” para **“reintrodução selecionada, validação física/runtime pendente”**.

Não atualizar o Notion nesta etapa, conforme instrução explícita do usuário. O GitHub passa a preservar a informação nova sem afirmar que o JAR já foi fisicamente instalado.