# Sophisticated Thirst Upgrade

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db817c9cd4d4035aacd324
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sophisticated-thirst-upgrade.jar`, mod id `sophisticatedthirst`, runtime `0.1.8`; Sophisticated Backpacks 3.26.2, Thirst Was Reclaimed 1.21.1-3.0.4 e Thirst Was Fixed 2.1.6 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o addon 0.1.8 e o stack Sophisticated/Thirst citado estão presentes. O upstream publica a integração com Thirst Was Taken, enquanto o pack usa Thirst Was Reclaimed; a compatibilidade funcional continua exigindo teste runtime. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sophisticated Thirst Upgrade
- **Arquivo JAR:** `sophisticated-thirst-upgrade.jar`
- **Versão 1.21.1:** 0.1.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Comida, QoL
- **Função:** Adiciona upgrades ao Sophisticated Backpacks para consumir automaticamente itens de hidratação armazenados na mochila; a versão Advanced adiciona filtragem semelhante ao Feeding Upgrade.
- **Dependências:** Sophisticated Backpacks 3.26.2 está presente. O projeto upstream é publicado para Thirst Was Taken; o stack físico atual do pack usa Thirst Was Reclaimed 1.21.1-3.0.4. A ficha antiga trata a integração como compatível, mas esta auditoria mantém teste runtime obrigatório em vez de presumir equivalência perfeita.
- **Sobreposição:** Não fornece barra/sistema de sede; apenas automatiza consumo a partir da mochila. Thirst Was Reclaimed continua provider da necessidade de hidratação.
- **Compatibilidade/Riscos:** Bridge de automação de hidratação. Riscos: consumir item errado/duas vezes, filtros divergentes, provider de sede incompatível, backpack sync, cooldown/threshold stale e death/relog state. Upstream publica contra Thirst Was Taken; o pack usa Thirst Was Reclaimed 3.0.4, portanto compatibilidade funcional atual deve ser validada em runtime.
- **Observações:** Runtime físico 0.1.8 no JAR `sophisticated-thirst-upgrade.jar`. A publicação oficial 1.21.1 confirma Thirst Upgrade + Advanced Thirst Upgrade; o provider atual do pack é Thirst Was Reclaimed, não o nome original Thirst Was Taken usado na página upstream.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sophisticated Thirst Upgrade 0.1.8 + páginas atuais de Sophisticated Backpacks e Thirst Was Reclaimed no catálogo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sophisticated Thirst Upgrade 0.1.8 reconstruído: auto-drink, Advanced Thirst Upgrade/filtering, backpack/provider boundaries, server authority, naming mismatch Thirst Was Taken↔Reclaimed, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticated-thirst-upgrade.jar`, mod id `sophisticatedthirst`, versão `0.1.8`, NeoForge 1.21.1. É uma bridge de automação: usa itens guardados em **Sophisticated Backpacks** para restaurar sede; não cria um sistema de sede próprio.

## 1. Identidade e papel
- **Mod:** Sophisticated Thirst Upgrade.
- **JAR:** `sophisticated-thirst-upgrade.jar`.
- **Mod id:** `sophisticatedthirst`.
- **Versão:** `0.1.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** automatizar consumo de itens hidratantes diretamente da mochila Sophisticated.

## 2. Providers e ownership
- **Sophisticated Backpacks:** inventory, upgrade slots/settings e armazenamento do item consumível.
- **Provider de sede:** valor de thirst, critérios de restauração e efeitos do consumível.
- **Sophisticated Thirst Upgrade:** detecta a necessidade e aciona consumo a partir da mochila conforme regras/config do upgrade.

A bridge não deve manter uma segunda barra de sede ou duplicar efeitos do provider.

## 3. Thirst Upgrade
O projeto publica um **Thirst Upgrade** equivalente conceitualmente ao Feeding Upgrade: quando as condições são satisfeitas, ele busca na mochila item capaz de restaurar sede e o utiliza automaticamente.

A seleção deve consumir exatamente um item por ação aceita e aplicar uma única restauração/effect chain.

## 4. Advanced Thirst Upgrade
Também existe um **Advanced Thirst Upgrade**, com filtragem semelhante ao sistema avançado de alimentação.

Filtros precisam ser server-authoritative e persistir com o upgrade/backpack. Um filtro client-only não pode permitir consumo funcional de item bloqueado pelo servidor.

## 5. Boundary com Sophisticated Backpacks
O pack contém Sophisticated Backpacks `3.26.2`. O upgrade depende do lifecycle de inventário/upgrades do backpack:
- equipar/retirar mochila;
- colocar como bloco quando suportado;
- abrir/fechar inventory;
- mover consumíveis entre slots;
- trocar/remover upgrade.

Remover o upgrade deve encerrar automação imediatamente, sem task/cache residual.

## 6. Naming mismatch do provider de sede
A publicação upstream descreve integração com **Thirst Was Taken**. O pack atual usa **Thirst Was Reclaimed 1.21.1-3.0.4**.

O catálogo anterior trata este addon como funcional no stack de sede atual, mas a identidade nominal diferente é material: não se deve afirmar compatibilidade perfeita apenas pelo conceito. Boot e consumo real precisam ser testados.

## 7. Authority da hidratação
O provider de sede continua responsável por:
- quantidade de thirst restaurada;
- efeitos secundários;
- regras de item válido;
- limites/cooldowns próprios.

O upgrade só deve acionar o mesmo consumo lógico que seria aceito pelo provider, sem hardcode paralelo de valores.

## 8. Item selection
Com múltiplos itens hidratantes na mochila, a escolha deve seguir o algoritmo/filter do upgrade e produzir resultado determinístico o suficiente para não alternar indefinidamente ou consumir item bloqueado.

O dossiê não inventa prioridade específica não publicada; esse comportamento deve ser observado em runtime.

## 9. Consumíveis com container remainder
Itens que devolvem garrafa/bowl/container após uso são regression surface. O output/remainder precisa retornar ao backpack/inventory/world exatamente uma vez conforme o contrato do item/provider.

Não pode haver perda ou duplicação por automação.

## 10. Efeitos e itens modded
Itens de outros mods podem restaurar sede e aplicar potion/effects adicionais. A bridge precisa delegar ao use action real do item/provider, não apenas reduzir stack e somar um valor fixo.

Testar itens com NBT/data components e remainders.

## 11. Threshold e frequência
A automação deve evitar consumo excessivo quando o jogador já está suficientemente hidratado ou quando a condição do upgrade não está satisfeita.

Tick checks muito frequentes em muitos backpacks podem gerar custo; medir apenas se profiler apontar o addon/upgrade path.

## 12. Client / server
Servidor deve decidir:
- necessidade de hidratação;
- item selecionado;
- stack decrement;
- restauração/effects;
- filtro funcional.

Cliente pode exibir GUI/settings. Preview visual divergente não pode executar consumo localmente.

## 13. Multiplayer
Cada jogador deve consultar apenas seu próprio backpack/upgrade state. Dois players com backpacks idênticos não podem compartilhar cooldown, filtro ou consumível por key global incorreta.

Inventory sync deve reconciliar imediatamente depois do auto-consumo.

## 14. Lifecycle e persistence
Validar:
- equip backpack;
- adicionar/remover upgrade;
- alterar filtro;
- mover itens hidratantes;
- atingir threshold de sede;
- death/relog;
- dimension change;
- backpack placement/pickup quando aplicável;
- save/restart.

Config/filtro do upgrade deve persistir; uma pending consumption não deve repetir após reconnect.

## 15. Integrações concretas no pack
- **Sophisticated Backpacks 3.26.2:** provider de storage/upgrades.
- **Thirst Was Reclaimed 3.0.4:** provider de sede atual do pack; compatibilidade requer teste por causa da nomenclatura/upstream lineage.
- **Thirst Was Fixed 2.1.6:** patch do stack de sede presente; qualquer mudança no consumo precisa ser triada entre provider/fix/upgrade.

## 16. Riscos técnicos
1. **Provider mismatch:** addon procura API/ID de Thirst Was Taken não exposta pelo Reclaimed.
2. **Double consumption:** tick/event duplica uso.
3. **Wrong item:** filtro/prioridade escolhe consumível indesejado.
4. **Remainder loss/dupe:** garrafa/container é liquidado incorretamente.
5. **Backpack sync:** cliente mostra stack antigo após consumo.
6. **Filter persistence:** Advanced Upgrade perde regras após restart.
7. **Threshold stale:** continua consumindo após thirst já restaurada.
8. **Item-use bypass:** automação ignora side effects/cooldowns do item.

## 17. Matriz de testes
- [ ] Dedicated server inicia com addon 0.1.8 + Sophisticated Backpacks 3.26.2 + Thirst Was Reclaimed 3.0.4.
- [ ] Thirst Upgrade reconhece consumível do provider atual.
- [ ] Consome exatamente um item por trigger.
- [ ] Restaura a quantidade/effects definidos pelo provider/item.
- [ ] Advanced Upgrade respeita whitelist/blacklist/filter configurado.
- [ ] Dois consumíveis diferentes seguem seleção coerente.
- [ ] Item com bottle/container remainder não duplica nem perde output.
- [ ] Remover upgrade encerra auto-consumo imediatamente.
- [ ] Backpack inventory sincroniza em multiplayer após consumo.
- [ ] Death/relog não repete uma pending action.
- [ ] Save/restart preserva filter/settings.
- [ ] Thirst Was Fixed coexistente não duplica consumo/effects.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física atual: addon 0.1.8, Sophisticated Backpacks 3.26.2, Thirst Was Reclaimed 3.0.4 e Thirst Was Fixed 2.1.6.
- CurseForge oficial: Thirst Upgrade automático e Advanced Thirst Upgrade com filtering para a linha Thirst Was Taken.
- Catálogo anterior: addon mantido no stack de sede atual.
- **Limite:** compatibilidade binária/funcional com Thirst Was Reclaimed não foi provada por runtime nesta auditoria; por isso permanece teste explícito e não uma equivalência assumida.
