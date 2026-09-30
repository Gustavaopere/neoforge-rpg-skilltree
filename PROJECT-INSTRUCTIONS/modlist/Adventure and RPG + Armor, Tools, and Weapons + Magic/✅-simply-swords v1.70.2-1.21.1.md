# Simply Swords

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#503**: JAR `simplyswords-neoforge-1.70.2-1.21.1.jar`, mod id `simplyswords`, runtime `1.70.2-1.21.1`, SHA-1 `05b074ff774467f1fe9fb5592151b7845c321cbc`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Simply Swords
- **Arquivo JAR:** `simplyswords-neoforge-1.70.2-1.21.1.jar`
- **Versão 1.21.1:** 1.70.2-1.21.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** RPG
- **Função:** Provider principal do ecossistema Simply Swords: amplia tipos de armas e Unique Weapons, implicits, Runic Powers/Tablets/Forge, gems, loot/pity e Awakening de uniques, com APIs/data/config para addons.
- **Dependências:** Required atuais confirmados e presentes: Architectury 13.0.11, Fzzy Config 0.7.6+1.21+neoforge e Simply Tooltips 0.1.5. Better Combat é opcional upstream e não foi encontrado top-level. Addons presentes: Simply More 1.3.0 Alpha 5, Simply Cataclysm 1.0.2 e Integrated Simply Swords.
- **Sobreposição:** É provider, não mero pacote de armas. Addons devem estender seus registries/abilities sem duplicar IDs ou assumir ownership do Runic/Awakening state.
- **Compatibilidade/Riscos:** Core RPG stateful e config/save-sensitive. 1.70.x foi anunciado como config-breaking/save-sensitive. Riscos: Runic/Awakening duplication, loot pity drift, gem/tablet state loss, stale config, addon API drift e combat-engine hooks. 1.70.2 corrige Epic Fight crash e Lootr injection/pity.
- **Observações:** A release 1.70.2 é a build física. Delta exato: fix de crash com Epic Fight e fix que impedia loot injection/pity do Simply Swords em baús Lootr. A arquitetura Runic/Awakening documentada no branch 1.21 é usada como contrato da linha, não atribuída integralmente como delta 1.70.2.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Simply Swords 1.70.2 + documentação oficial branch Architectury-1.21 para Runic Powers/Forge, Awakening, loot/pity e API.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/simply-swords/files/8746001
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Simply Swords 1.70.2-1.21.1/JAR físico reconfirmado; 1.70.2 permanece a release NeoForge 1.21.1 mais recente localizada. Fixes Epic Fight/Lootr e boundaries Runic/Awakening preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `simplyswords-neoforge-1.70.2-1.21.1.jar`, mod id `simplyswords`, versão `1.70.2-1.21.1`, NeoForge 1.21.1. É o **provider principal** do ecossistema Simply Swords no pack. A decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Simply Swords.
- **JAR:** `simplyswords-neoforge-1.70.2-1.21.1.jar`.
- **Mod id:** `simplyswords`.
- **Versão:** `1.70.2-1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Mixins físicos:** `simplyswords.mixins.json` e `simplyswords-common.mixins.json`.
- **Papel:** base de weapon types, Unique Weapons, implicits, Runic Powers, gems, loot/pity e Awakening usados também por addons.
## 2. Dependências físicas
A linha atual declara como required:
- Architectury — pack `13.0.11`;
- Fzzy Config — pack `0.7.6+1.21+neoforge`;
- Simply Tooltips — pack `0.1.5`.
Better Combat é optional upstream e não foi encontrado top-level no snapshot físico. Portanto não é tratado como integração ativa.
## 3. Authority e ownership
Simply Swords owns:
- registries/weapon classes do próprio ecossistema;
- implicits e abilities próprios;
- Unique Weapon state;
- Runic Powers/Tablets/Forge;
- Awakening progress/profile;
- gem sockets e loot/pity próprios.
Addons como Simply More, Simply Cataclysm e Integrated Simply Swords devem consumir/estender esses contratos, não manter state paralelo do mesmo sistema.
## 4. Weapon ecosystem
O projeto amplia fortemente o arsenal vanilla com famílias de melee weapons de velocidades, reach/attack patterns e identidade diferentes. A ficha não trata cada nome como um sistema isolado: o ponto operacional é que recipes, attributes, implicits, animação e integração de combate derivam do provider Simply Swords.
Alteração de material/tier por addon não transfere ownership da weapon class ao addon de material.
## 5. Weapon implicits
A linha 1.70 introduz/expande **weapon implicits**: comportamentos intrínsecos ligados ao tipo da arma, separados de Unique abilities e Runic Powers.
Implicits devem ser calculados uma vez por ação lógica e removidos quando a arma deixa de satisfazer o contrato. Addons da linha 1.70, como Simply More, dependem dessa infraestrutura.
### 5.1 Matriz de Implicits nativos relevante para perks

O guia temático canônico preserva uma matriz de **defaults do provider** para os Implicits da linha instalada. Esses valores são ponto de partida, não hardcode obrigatório: config/runtime efetivo continua sendo a autoridade antes de implementar qualquer perk.

| Família | Implicit nativo | Faixa default documentada | Risco de integração |
|---|---|---:|---|
| Rapier, Spear | chance de ignorar armor | 15–35% | Evitar double-dip com armor negation/penetration externa. |
| Cutlass | chance de loot extra on-hit | 1–3% | Tratar como capacidade econômica; deduplicar drops. |
| Glaive, Greataxe, Halberd | chance de aplicar Bleed | 10–60% | O proc/status pertence ao Simply Swords; não recriar Bleed em paralelo. |
| Sai, Dagger | bônus de dano por backstab | 20–40% | Depende da causalidade posicional real do provider. |
| Claymore, Longsword | chance de defletir dano recebido | 5–15% | É defesa/incoming-damage, não proc ofensivo comum. |
| Greathammer | armor sunder por hit | 2–10% | Exige ownership claro de stacking/debuff. |
| Hammer | armor sunder por hit | 2–6% | Mesma família mecânica com faixa menor. |
| Katana | chance de dano duplo | 5–15% | Multiplicador de alto risco; não empilhar outro double-damage sem regra explícita. |
| Chakram, Twinblade | chance de ganhar attack speed on-hit | 5–25% | Interage diretamente com cadência/Epic Fight. |
| Scythe | chance de executar alvo com vida baixa | 5–15% | Execute deve permanecer provider-native. |
| Warglaive | chance de atacar duas vezes | 5–15% | Alto risco de duplicar on-hit, enchant, gem e outros procs. |

### 5.2 API pública documentada para integração de Implicits

O capítulo de perks do ecossistema Simply Swords registra as seguintes superfícies públicas da linha 1.70:

- `SimplySwordsAPI.registerWeaponType(item, type)`;
- `SimplySwordsAPI.registerWeaponType(tag, type)`;
- `SimplySwordsAPI.getOrCreateWeaponImplicit(stack)`;
- `SimplySwordsAPI.appendWeaponImplicitTooltip(...)`;
- `SimplySwordsAPI.applyWeaponImplicitDamage(...)`;
- `SimplySwordsAPI.applyWeaponImplicitOnHit(...)`;
- `SimplySwordsAPI.registerWeaponImplicit(definition)`.

Esses nomes são evidência de que integração não precisa inferir tipo de arma pelo display name. Como a referência técnica do guia foi cruzada contra a linha 1.70, qualquer implementação deve confirmar assinatura/ABI no JAR físico `1.70.2` antes de compilar; não importar API exclusiva de linha posterior por semelhança.

### 5.3 Semântica de execução e persistência dos Implicits

A documentação técnica usada pelo guia distingue handlers de `WeaponImplicitDefinition`:

- `DamageHandler` — modifica o dano de saída;
- `HitHandler` — executa depois de um hit confirmado;
- `IncomingDamageHandler` — pode interferir ou cancelar dano recebido enquanto a arma está segurada;
- `TooltipFormatter` — apresenta o roll.

O **roll do Implicit é persistido no stack**. Armas comuns usam a faixa inteira; subclasses de `UniqueWeaponItem` rolam no **top 10%** da faixa. Socketing, Awakening, save/reload ou simples reconstrução de tooltip **não devem rerrolar** esse valor. Para perks, o Implicit deve ser observado como estado estável do provider, não recriado em um ledger paralelo.


## 6. Runic Powers
Runic Powers são modificadores de combate que podem ser passivos, trigger-based ou ativos. A documentação atual separa o power da arma base e permite que tablets/powers sejam identificados e gerenciados pelo sistema Runic.
Não confundir Runic Power com enchantment vanilla: persistence, reroll e sockets pertencem ao sistema do mod.
## 7. Runic weapons e tablets
Runic weapons ocupam progressão acima de Netherite e abaixo do topo das Unique weapons conforme o design publicado. A documentação descreve crafting via smithing com Runic Tablet + arma Netherite compatível + Diamond.
Um item runic não identificado pode revelar seu poder quando usado conforme o fluxo do projeto. Runic Tablets entram em loot e participam também do Awakening/Runic Forge.
## 8. Loot injection e pity
O projeto injeta Runic Tablets/Unique loot em chests elegíveis e possui mecanismo de **pity** para reduzir sequências longas sem recompensa. A documentação atual menciona hard pity default após uma quantidade configurável de misses elegíveis.
O contador precisa ser server-authoritative, persistente no escopo previsto e incrementado uma única vez por oportunidade elegível. Loot duplicado/reabertura de container não pode contar como novas tentativas indevidas.
## 9. Runic Forge
A documentação 1.21 define o **Runic Forge** como único caminho suportado para adicionar/remover Runic Tablets de Unique weapons e como workspace seguro para Runefused/Netherfused gems.
Fluxo publicado:
1. colocar weapon suportada no slot superior central;
2. Forge extrai tablets/gems para slots de edição;
3. mover até oito Runic Tablets e uma gem de cada tipo;
4. preview é mostrado no item;
5. retirar a arma ou fechar a tela faz commit seguro.
Fechar a screen não pode duplicar preview/input nem perder componentes.
## 10. Awakening
A arquitetura atual dá às Unique weapons **oito níveis de Awakening**. O Runic Forge representa esses níveis pelos oito slots inferiores.
Para `UniqueWeaponItem`, o profile default atual usa um estado dormente com multiplicadores reduzidos e desbloqueio de ability em nível intermediário, interpolando para stats completos no nível 8.
Esses detalhes pertencem ao contrato do branch 1.21 atual; não são tratados como changelog exclusivo da 1.70.2.

### 10.1 Scaling de Awakening — regra exactly-once

A API documentada expõe `AwakeningApi.isAwakeningSystemEnabled()`, `usesAwakeningProgression(stack)`, `getLevel(stack)`, `isAbilityUnlocked(stack)`, `getAbilityUnlockLevel(stack)`, multiplicadores de effect/attribute/attack speed e helpers como `scaleEffect(...)` / `scaleChance(...)`.

`SimplySwordsAPI.scaleAbilityDamage(...)` e `scaleAbilityValue(...)` **já aplicam o scaling apropriado de Awakening**. Não aplicar `scaleEffect` novamente ao resultado: isso produz double-scaling. Quando o sistema global de Awakening está desativado, Uniques comuns podem se comportar como nível 8 preservando o nível armazenado; consultar `usesAwakeningProgression(stack)` antes de interpretar o progresso persistido.

## 11. Natural drop initialization
A documentação de API distingue Unique drop natural de stacks criados diretamente. Drops naturais recebem initialization de Awakening própria; stacks sem component armazenado podem ser tratados como fully awakened por compatibilidade, dependendo do path.
Isso é boundary importante para loot tables, commands, KubeJS e addons: criar um stack diretamente pode não reproduzir o mesmo state de um natural drop.
## 12. Gems
Runefused e Netherfused gems podem ser gerenciadas no Runic Forge. Config também pode admitir itens adicionais em sockets específicos.
Troca de gem deve preservar exatamente um item em cada origem/destino e recomputar stats/tooltip sem manter modifiers antigos depois do commit.

Helpers de gem power como `gemPowerScaledDamage` / `gemPowerScaledValue` já escolhem o caminho físico/fixo versus spell scaling e aplicam o scaling de Awakening da gem **uma vez**. Uma perk não deve escalar novamente o resultado.

### 12.1 Contrato público de gem sockets para integrações

Além dos sockets nativos de Unique Weapons, a linha documentada expõe `AdditionalGemSocketApi` para conceder sockets a itens externos por item ID ou `#tag`. Para bases customizadas, os caminhos públicos de lifecycle são:

- `SimplySwordsAPI.inventoryTickGemSocketLogic(...)` — tick das gem powers equipadas;
- `SimplySwordsAPI.onClickedGemSocketLogic(...)` — inserção/substituição por inventory click;
- `SimplySwordsAPI.postHitGemSocketLogic(...)` — processamento pós-hit;
- `SimplySwordsAPI.appendTooltipGemSocketLogic(...)` — apresentação dos sockets/powers;
- `SimplySwordsAPI.onWeaponSwing(...)` — integração de swing quando a base customizada precisa do pipeline Simply Swords.

As powers públicas derivam de contratos como `RunicGemPower`, `RunefusedGemPower`, `NetherGemPower` e `GemPower`. O `GemPowerRegistry` usa **IDs namespaced sincronizados** entre servidor e cliente. Desde a linha 1.70, `GemPowerComponent` persiste **IDs**, e não identidade efêmera de registry entry; esse detalhe existe para manter stacks reconstruídos por storage/automation compatíveis e não deve ser substituído por estado paralelo da skill tree.

Para perks ou itens customizados, a regra é provider-native first: usar esses hooks para tick/click/post-hit/tooltip/swing e persistir o mesmo contrato de IDs. Não reimplementar socketing por NBT/attachment próprio nem armazenar uma segunda referência de gem power. O scaling continua exactly-once pelos helpers já documentados acima.

## 13. Unique loot e abilities
Unique Weapons combinam item próprio, state de Awakening e abilities específicas. Tooltips avançados podem mostrar informações adicionais via Simply Tooltips.
Uma Unique desabilitada/configurada não deve continuar entrando no loot apenas porque o registry ainda existe; loot config e item registration são boundaries distintas.

### 13.1 Pipeline de abilities/hits e deduplicação

O sistema público inclui `UniqueWeaponActiveAbility` e `SimplySwordsAPI.tryActivateWeaponAbility(...)`, que valida condições como unlock de Awakening e cooldown antes da ativação.

Para dano e hits delegados, os helpers preservam semânticas que uma perk não deve reproduzir por fora:

- `applyDelegatedWeaponHit(...)` / `applyEntityWeaponHit(...)` executam o pipeline de hit da arma com contexto, Implicit e post-hit controlados;
- `applyAbilityBoltDamage(...)` é server-side, valida o alvo, aplica enchantments pertinentes, pode bypassar iframes para o bolt e **suprime Implicits** nesse dano para evitar proc indevido;
- um hit adicional produzido por ability **não autoriza** reaplicar automaticamente todos os on-hit, Implicits, gems ou outros procs.

Consequência: perks que observem abilities/extra hits precisam deduplicar por evento causal do provider e respeitar o pipeline escolhido pelo Simply Swords.

## 14. Config e 1.70.x
A transição 1.70 foi anunciada pelo projeto como **config-breaking** e potencialmente sensível para saves/configs existentes. O novo sistema de configuração invalida customizações antigas.
Consequência operacional: não atualizar/downgradar a linha 1.70 automaticamente em mundo principal. Primeiro diffar configs, testar loot/unique state e fazer backup.
## 15. Delta exato da 1.70.2
O changelog da build física registra dois fixes de alto valor para este pack:
1. **crash com Epic Fight** corrigido;
2. **loot injection/pity com baús Lootr** corrigido.
O pack contém Epic Fight `21.17.3.1` e Lootr `1.21.1-1.11.38.125`, então ambos são regression gates concretos da 1.70.2.
## 16. Epic Fight boundary
Simply Swords continua decidindo weapon item/implicit/ability state; Epic Fight decide animation/combat-engine semantics de seus próprios hooks. A 1.70.2 corrige um crash entre os dois, mas isso não prova compatibilidade perfeita de todos os attack windows.
Testar hits únicos, multi-hit, charged/active abilities, dual/alternate animations e troca de weapon durante animation state.
## 17. Lootr boundary
Lootr fornece containers individualizados por jogador. O fix 1.70.2 garante que Simply Swords loot injection/pity volte a operar nesse contexto.
Regression gate: dois jogadores abrindo o mesmo Lootr chest devem receber seus loot contexts corretos sem compartilhar pity indevidamente ou duplicar loot do outro player.
## 18. Addons físicos presentes
- **Simply More 1.3.0 Alpha 5:** consome weapon/implicit infrastructure e adiciona uniques/types.
- **Simply Swords: Cataclysm 1.0.2:** adiciona famílias baseadas em materiais Cataclysm.
- **Integrated Simply Swords:** cria variantes com materiais de outros mods.
- **Simply Tooltips 0.1.5:** required dependency atual e renderer de cooldowns/implicits.
Esses addons ampliam o core; não são substitutos.
## 19. Client / server
Servidor deve ser authority de:
- loot/pity;
- Runic/Awakening/gem state;
- ability proc/damage/heal;
- crafting/smithing;
- item persistence.
Cliente apresenta models, particles, tooltip, screen/preview e input. Preview de Forge não pode ser aplicado funcionalmente antes do commit server-side.
## 20. Persistence e idempotência
State crítico:
- Awakening level;
- Runic Tablets;
- gems;
- Unique components;
- cooldowns/ability state quando persistente;
- pity/loot tracking.
Save/restart/relog não pode reaplicar modifiers, resetar progress sem regra explícita nem duplicar tablets/gems.
## 21. Riscos técnicos
1. **Config migration:** 1.70.x invalida config antiga.
2. **Awakening duplication:** modifiers reaplicados no load.
3. **Forge preview duplication:** fechar/retirar item duplica tablets/gems.
4. **Loot pity drift:** Lootr/containers especiais alteram contagem.
5. **Epic Fight hook conflict:** mesmo hit processado duas vezes ou crash.
6. **Addon API drift:** consumers esperam component/registry antigo.
7. **Direct-stack mismatch:** command/script cria Unique em state diferente do natural drop.
8. **Tooltip/state mismatch:** cliente mostra power/level diferente do servidor.
9. **Disabled content leakage:** item desabilitado ainda entra em loot.
## 22. Matriz de testes
- [ ] Dedicated server inicia com Simply Swords 1.70.2 e required deps físicas.
- [ ] Epic Fight: equipar/atacar/trocar weapon sem crash — regression 1.70.2.
- [ ] Lootr chest recebe Simply Swords loot injection — regression 1.70.2.
- [ ] Pity progride corretamente em Lootr e container comum.
- [ ] Dois players não compartilham pity/loot indevidamente.
- [ ] Weapon implicits aplicam/removem uma única vez.
- [ ] Runic weapon identifica/rerolla conforme fluxo publicado.
- [ ] Runic Forge add/remove dos 8 tablets sem dupe/loss.
- [ ] Runefused/Netherfused gems fazem commit seguro.
- [ ] Fechar Runic Forge com preview preserva state exatamente uma vez.
- [ ] Unique natural drop inicia Awakening no state esperado.
- [ ] Directly-created Unique via command/script é comparada ao natural drop.
- [ ] Save/restart preserva Awakening/tablets/gems.
- [ ] Simply More / Simply Cataclysm / Integrated Simply Swords carregam e resolvem registries.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 23. Evidências e limites
- Modlist física atual: Simply Swords 1.70.2, required dependencies, Epic Fight, Lootr e addons presentes.
- CurseForge 1.70.2: fixes exatos Epic Fight e Lootr.
- Documentação oficial branch Architectury-1.21: Runic Forge, Runic/Awakening architecture, loot/pity e API.
- **Limite:** documentação do branch 1.21 pode conter refinamentos posteriores à 1.70.2; arquitetura corrente é usada como contrato de linha, enquanto o delta exclusivo da 1.70.2 permanece restrito ao changelog publicado.
