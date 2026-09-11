# Simply Swords

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db817eb8bffc9046a74571
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `simplyswords-neoforge-1.70.2-1.21.1.jar`, mod id `simplyswords`, runtime `1.70.2-1.21.1`, mixins `simplyswords.mixins.json` e `simplyswords-common.mixins.json`; Architectury 13.0.11, Fzzy Config 0.7.6, Simply Tooltips 0.1.5, Epic Fight 21.17.3.1 e Lootr 1.11.38.125 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Simply Swords 1.70.2, suas required dependencies e os regression providers Epic Fight/Lootr estão presentes. Better Combat não aparece top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Simply Swords
- **Arquivo JAR:** `simplyswords-neoforge-1.70.2-1.21.1.jar`
- **Versão 1.21.1:** 1.70.2-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
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
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Simply Swords 1.70.2 reconstruído: weapon ecosystem, implicits, Runic Powers/Forge, Unique loot+pity, Awakening, gems, config/save boundary, addons, exact Epic Fight/Lootr fixes, lifecycle, riscos e testes.
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

## 11. Natural drop initialization
A documentação de API distingue Unique drop natural de stacks criados diretamente. Drops naturais recebem initialization de Awakening própria; stacks sem component armazenado podem ser tratados como fully awakened por compatibilidade, dependendo do path.

Isso é boundary importante para loot tables, commands, KubeJS e addons: criar um stack diretamente pode não reproduzir o mesmo state de um natural drop.

## 12. Gems
Runefused e Netherfused gems podem ser gerenciadas no Runic Forge. Config também pode admitir itens adicionais em sockets específicos.

Troca de gem deve preservar exatamente um item em cada origem/destino e recomputar stats/tooltip sem manter modifiers antigos depois do commit.

## 13. Unique loot e abilities
Unique Weapons combinam item próprio, state de Awakening e abilities específicas. Tooltips avançados podem mostrar informações adicionais via Simply Tooltips.

Uma Unique desabilitada/configurada não deve continuar entrando no loot apenas porque o registry ainda existe; loot config e item registration são boundaries distintas.

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
