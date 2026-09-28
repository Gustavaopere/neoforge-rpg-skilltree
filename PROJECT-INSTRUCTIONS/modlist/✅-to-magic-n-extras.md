# T.O Magic n' Extras

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#550**: JAR `traveloptics-4.4.0.1-1.21.1.jar`, mod id `traveloptics`, runtime `4.4.0.1-1.21.1`, SHA-1 `7b74816e89cc15dd0b5a31d9ea1e456024e8fae4`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** T.O Magic n' Extras
- **Arquivo JAR:** `traveloptics-4.4.0.1-1.21.1.jar`
- **Versão 1.21.1:** 4.4.0.1-1.21.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Worldgen
- **Função:** Port Alpha parcial de T.O Magic n' Extras para 1.21.1: confirma Cataclysm-based spells e alguns itens Survival-obtainable, mas não representa o conteúdo completo de bosses/weapons/equipment/worldgen da linha principal 1.20.1.
- **Dependências:** Iron's Spells 3.16.3 é base atual. Cataclysm 3.33 está presente e é a integração mais concretamente ported. Alex's Caves Continued 1.0.10 está presente hoje, mas a build T.O 4.4.0.1 foi publicada com seu conteúdo Alex's Caves inteiramente ausente.
- **Sobreposição:** Somente o conteúdo efetivamente portado deve entrar no spell-pool atual. Não assumir o grande catálogo 1.20.1. Soma-se a outros addons Iron's e Cataclysm já instalados.
- **Compatibilidade/Riscos:** ALTO RISCO: o upstream renomeou a exata build para `DEPRECATED DONT USE`; nota oficial diz \~20% do conteúdo portado, muitos items/effects/mechanics incompletos e ausência total do conteúdo Alex's Caves. Risco alto de drift com Iron's/Cataclysm atuais e de quests assumirem conteúdo 1.20.1 inexistente.
- **Observações:** mod id `traveloptics`; runtime físico 4.4.0.1-1.21.1 Alpha. Decisão `Manter` de 22/08/2026 e estado `Integrado ao Github` preservados por escolha explícita. Upstream continua marcando esta build como DEPRECATED DONT USE e port parcial (\~20%); não assumir feature parity com 1.20.1.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge oficial do arquivo `traveloptics-4.4.0.1-1.21.1.jar`, ainda marcado `DEPRECATED DONT USE`, + stack físico Iron's Spells 3.16.3, Cataclysm 3.33 e Alex's Caves Continued 1.0.10. Dossiê histórico e decisão Manter preservados; nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/to-tweaks-irons-spells/files/6342780 ; https://www.curseforge.com/minecraft/mc-mods/to-tweaks-irons-spells
- **Atualização/Status:** RECONCILIADO EM 27/09/2026 — T.O Magic n' Extras 4.4.0.1-1.21.1 permanece instalado; upstream continua marcando a build `DEPRECATED DONT USE`, com port \~20%. Stack atual confirmado: Iron's Spells 3.16.3, Cataclysm 3.33 e Alex's Caves Continued 1.0.10; a presença de Alex's Caves não restaura automaticamente o conteúdo T.O ausente.
- **Histórico da decisão:** Uma auditoria anterior sugeriu remover T.O Magic n' Extras por ser uma megaexpansão antiga/alpha e por ampliar muito o spell pool/worldgen. Em 22/08/2026 o usuário esclareceu que já usava o mod na 1.20.1, gostava dele e quer mantê-lo porque oferece muito conteúdo. Portanto a decisão vigente é MANTER; a condição alpha/idade da linha 1.21.1 continua sendo ponto de acompanhamento, não motivo de remoção automática.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **STATUS UPSTREAM CRÍTICO.** Runtime físico: `traveloptics-4.4.0.1-1.21.1.jar`, mod id `traveloptics`, versão `4.4.0.1-1.21.1`. A página oficial renomeou esta exata build para **“DEPRECATED DONT USE Alpha-4.4.0.1-1.21.1”**. O próprio autor declara que cerca de **20% do conteúdo** havia sido portado e recomenda a linha 1.20.1 para a experiência completa/polida. A decisão histórica do pack continua **Manter**, por escolha explícita do usuário; esta ficha não a sobrescreve.
## 1. Identidade, versão e decisão
- **Mod:** T.O Magic n' Extras / T.O Magic 'n Extras.
- **JAR físico:** `traveloptics-4.4.0.1-1.21.1.jar`.
- **Mod id:** `traveloptics`.
- **Versão:** `4.4.0.1-1.21.1`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal da build:** Alpha.
- **Status atual do arquivo upstream:** **DEPRECATED DONT USE**.
- **Decisão vigente do pack:** **Manter**, registrada por escolha explícita em 22/08/2026.
## 2. Provenance e diferença entre projeto completo e build instalada
O projeto T.O completo é apresentado como grande addon de Iron's Spells com spells, bosses, weapons, equipment e integrações com L_Ender's Cataclysm e Alex's Caves.
**Isso não descreve integralmente a build instalada.** A nota oficial específica da 4.4.0.1 para 1.21.1 diz que aproximadamente 20% do conteúdo havia sido portado e grande parte da identidade/qualidade estava ausente.
Portanto esta ficha separa:
- **projeto completo:** escopo amplo conhecido do T.O;
- **runtime 1.21.1 instalado:** port parcial/deprecated.
## 3. Conteúdo explicitamente confirmado na Alpha 4.4.0.1
A nota oficial da build sustenta:
- **Cataclysm-based spells** portados;
- **alguns items** portados;
- spells obtíveis em Survival para experimentação;
- intenção, na época, de portar mais conteúdo relacionado a Cataclysm.
Não é seguro atribuir ao JAR 1.21.1 todo o roster de bosses, weapons, structures, effects ou mechanics da linha 1.20.1.
## 4. Conteúdo explicitamente ausente/incompleto
O autor registra para esta build:
- **most T.O content is not here yet**;
- muitos items, effects e mechanics ainda incompletos;
- **Alex's Caves content is entirely missing**;
- o port 1.21 não receberia manutenção constante até dependências relevantes estarem prontas.
Essas são limitações upstream explícitas, não inferências da auditoria.
## 5. Alex's Caves no pack atual
A modlist atual possui `alexscaves-1.0.10-neoforge+1.21.1.jar` — Alex's Caves Continued.
Isso **não prova que o conteúdo T.O/Alex's Caves ausente em 4.4.0.1 tenha reaparecido**. A build T.O foi publicada explicitamente sem esse conteúdo; instalar posteriormente um port de Alex's Caves não injeta automaticamente registries/recipes/spells que não foram incluídos no JAR T.O.
Qualquer integração atual precisa ser demonstrada no runtime, não presumida pela presença simultânea dos mods.
## 6. L_Ender's Cataclysm no pack atual
O pack possui L_Ender's Cataclysm `3.33` e vários addons relacionados. A Alpha T.O confirma Cataclysm-based spells e alguns itens, então esta é a integração mais concretamente sustentada para o runtime 1.21.1.
Regression tests devem verificar:
- spell registration;
- aquisição em Survival;
- targets/entities atuais do Cataclysm;
- efeitos visuais/damage;
- ausência de missing registry/resource references.
## 7. Iron's Spells 'n Spellbooks
T.O é addon de Iron's Spells; o pack instala Iron's Spells `3.16.3`.
A build T.O 4.4.0.1 é de março de 2025 e não foi mantida continuamente para a evolução posterior do stack. Isso cria alto risco de **API/registry drift** mesmo quando o jogo inicia.
Validar spell school, mana/cooldown, spell acquisition, scrolls/books e attributes sem assumir compatibilidade futura automática.
## 8. Survival obtainability
A nota da Alpha afirma que spells portados são **obtainable in survival** para poderem ser testados.
O mesmo aviso diz para não reportar missing survival-obtainable items que ainda não deveriam estar disponíveis. Assim, ausência de determinados items/recipes pode ser limitação intencional do port, não necessariamente bug local.
## 9. Worldgen, bosses e equipment — fail-closed
Embora o projeto completo anuncie boss, weapons, equipment e muito mais, a Alpha 1.21.1 é parcial.
Nesta ficha, nenhum boss/structure/worldgen específico é declarado como pertencente ao runtime 4.4.0.1 sem prova direta. A antiga classificação ampla de “grande addon com bosses/worldgen completos” não deve ser usada por outro chat para desenhar quests/progressão da instância atual.
## 10. Relação com a linha 1.20.1
O upstream mantém a linha 1.20.1 como experiência principal e publicou posteriormente Release `6.3.0` para 1.20.1.
O autor da Alpha 1.21.1 instrui explicitamente quem deseja experiência completa/polida a usar 1.20.1.
Isso **não é recomendação de mudar o Minecraft do pack**; é evidência de que a 4.4.0.1 não representa feature parity com a linha principal.
## 11. Decisão histórica do pack
Em 22/08/2026 ficou registrado que o usuário já usava T.O na 1.20.1, gostava do mod e desejava mantê-lo pelo volume/qualidade de conteúdo.
A auditoria atual preserva **Manter**, mas corrige a premissa técnica: o JAR 1.21.1 instalado **não contém o volume completo que motivou a preferência pela versão 1.20.1**.
Qualquer futura revisão da decisão deve partir desta diferença, não de uma descrição genérica do projeto completo.
## 12. Client / server e multiplayer
- Servidor: spell registration, damage/effects, acquisition e state de gameplay.
- Cliente: spell visuals, models, particles e UI.
Como port deprecated/incompleto, ambos os lados são regression surface. Missing resource no cliente e missing registry/class no servidor precisam ser diferenciados.
## 13. Lifecycle
Validar:
- dedicated server boot;
- client join;
- spell registry/load;
- obtenção Survival dos spells disponíveis;
- cast contra vanilla/Cataclysm targets;
- death/relog/restart;
- datapack/resource reload quando aplicável;
- world load contendo T.O items/spells antigos;
- update de Iron's/Cataclysm em cópia de teste.
## 14. Riscos técnicos
1. **Upstream deprecated:** o próprio arquivo está marcado “DEPRECATED DONT USE”.
2. **Port \~20%:** a maior parte do projeto completo não está no runtime instalado.
3. **No active 1.21 maintenance promised:** alto risco de drift com Iron's/Cataclysm atuais.
4. **Alex's Caves integration absent:** presença atual de Alex's Caves Continued não restaura conteúdo T.O ausente.
5. **Incomplete mechanics/items/effects:** recipes/progression podem ter lacunas intencionais.
6. **Quest design risk:** não construir quests assumindo conteúdo da linha 1.20.1.
7. **Save/data risk:** remover depois de usar items/spells pode deixar referências em inventários/world/player data.
8. **Addon spell-pool stacking:** mesmo o conteúdo parcial soma com muitos outros addons Iron's instalados.
## 15. Matriz de testes
- [ ] Dedicated server inicia com T.O Alpha 4.4.0.1 + Iron's 3.16.3.
- [ ] Cliente conecta sem missing class/registry crash.
- [ ] Cataclysm-based spells realmente registrados aparecem no runtime.
- [ ] Spells publicados como Survival-obtainable têm caminho funcional.
- [ ] Cast consome mana/cooldown e aplica efeito uma única vez.
- [ ] Cataclysm 3.33 targets funcionam sem entity/tag mismatch.
- [ ] Alex's Caves Continued presente não gera falsas recipes/entries T.O quebradas.
- [ ] JEI/recipe viewers não exibem grande volume de recipes impossíveis da linha 1.20.1.
- [ ] Relog/restart preserva spell/item state disponível.
- [ ] Quest designers usam somente registries confirmados no runtime 1.21.1.
- [ ] Backup de mundo existe antes de qualquer atualização/remoção do addon.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 16. Evidências
- Modlist física canônica 08/09/2026: JAR 4.4.0.1, Iron's Spells 3.16.3, Cataclysm 3.33 e Alex's Caves Continued 1.0.9 presentes.
- CurseForge oficial do arquivo `traveloptics-4.4.0.1-1.21.1.jar`: status atual “DEPRECATED DONT USE”, Alpha NeoForge 1.21.1, nota de port \~20%, Cataclysm spells/some items presentes, maior parte do conteúdo incompleta e Alex's Caves content inteiramente ausente.
- CurseForge principal T.O: escopo do projeto completo e linha Release 1.20.1 mantida separadamente.
- Histórico de decisão no Notion: `Manter` por escolha explícita do usuário em 22/08/2026; preservado.
## 17. Revalidação física — 11/09/2026
A modlist física continua contendo exatamente `traveloptics-4.4.0.1-1.21.1.jar`, mod id `traveloptics`, versão `4.4.0.1-1.21.1`. O upstream continua marcando esta exata build como **DEPRECATED DONT USE** e mantém a nota de port parcial, aproximadamente 20%, sem Alex's Caves content nessa Alpha.
A decisão histórica **Manter** e o estado **Integrado ao Github** foram preservados, sem converter a preferência pela linha completa 1.20.1 em alegação falsa de feature parity no runtime 1.21.1. Nenhum teste de registro, Survival obtainability, Cataclysm spell ou drift com Iron's 3.16.3 foi executado.
## 18. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `traveloptics-4.4.0.1-1.21.1.jar`, versão `4.4.0.1-1.21.1`. O upstream continua expondo esta exata build como **DEPRECATED DONT USE** e o port permanece documentado como aproximadamente 20%, sem feature parity com a linha 1.20.1 e sem conteúdo Alex's Caves nessa Alpha. A decisão histórica **Manter** de 22/08/2026 foi preservada por instrução do usuário; nenhum teste de registro, Survival obtainability, Cataclysm spells ou drift com Iron's `3.16.3` foi executado.
## 19. Reconciliação física — 27/09/2026
A autoridade física atual mantém `traveloptics-4.4.0.1-1.21.1.jar`, mod id `traveloptics`, runtime `4.4.0.1-1.21.1`. O stack atual contém Iron's Spells `3.16.3`, L_Ender's Cataclysm `3.33` e Alex's Caves Continued `1.0.10`. A presença atual de Alex's Caves Continued 1.0.10 **não altera** a limitação upstream desta Alpha: o conteúdo T.O/Alex's Caves que não foi portado para 4.4.0.1 continua ausente salvo evidência runtime específica. A decisão histórica **Manter** permanece preservada; nenhum teste de registro, Survival obtainability, Cataclysm spell ou compat Alex's Caves foi executado nesta reconciliação.
