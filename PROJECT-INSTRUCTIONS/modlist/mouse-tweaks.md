# Mouse Tweaks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ca9b0dc23803af2983
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `MouseTweaks-neoforge-mc1.21-2.26.1.jar`, mod id `mousetweaks`, runtime `2.26.1`, `mousetweaks.mixins.json` e `mousetweaks-fabric.mixins.json` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Mouse Tweaks
- **Arquivo JAR:** `MouseTweaks-neoforge-mc1.21-2.26.1.jar`
- **Versão 1.21.1:** 2.26.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** QoL client-side de inventário que melhora drag, cliques e wheel transfer sobre containers/slots sem se tornar authority do armazenamento server-side.
- **Dependências:** Cliente NeoForge 1.21/1.21.1. Não possui gameplay dependency externa relevante na release auditada.
- **Sobreposição:** Pode disputar os mesmos gestos com outros inventory-QoL mods, mas não duplica storage/logística. Conflito deve ser reproduzido por GUI/gesto específico.
- **Compatibilidade/Riscos:** Client-only input/UI. Riscos: conflitos de wheel/drag/LMB/RMB em menus custom, slots especiais e desync visual sob latência. 2.26.1 corrige crash da config screen no NeoForge.
- **Observações:** Runtime 2.26.1, file ID 5637846, Release 17/08/2024. Mixins físicos incluem `mousetweaks.mixins.json`; a distribuição também contém config compartilhado nomeado Fabric, que não é mod top-level separado.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 2.26.1 + source oficial YaLTeR/MouseTweaks.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mouse-tweaks/files/5637846 | https://github.com/YaLTeR/MouseTweaks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Mouse Tweaks 2.26.1 reconstruído: input/container model, drag/wheel, GUI compatibility, client boundary, mixins, config-screen fix, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `MouseTweaks-neoforge-mc1.21-2.26.1.jar`, mod id `mousetweaks`, versão `2.26.1`. A release exata é o CurseForge file ID `5637846`, publicada em 17/08/2024 para NeoForge 1.21/1.21.1. O projeto é client-side e melhora gestos de inventário; não cria armazenamento, recipes ou autoridade server-side própria.

## 1. Identidade e papel
- **Mod:** Mouse Tweaks.
- **JAR físico:** `MouseTweaks-neoforge-mc1.21-2.26.1.jar`.
- **Mod id:** `mousetweaks`.
- **Runtime:** `2.26.1`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Autor:** YaLTeR.
- **CurseForge project ID:** 60089; file ID 5637846.
- **Licença:** BSD.
- **Environment:** Client.
- **Papel:** melhorar interação por mouse em inventories/containers, oferecendo drag e wheel transfer mais eficientes.

## 2. Princípio de funcionamento
Mouse Tweaks altera **input/UI**, não a lógica de armazenamento. O jogador continua interagindo com slots/containers; o mod facilita sequências de cliques/arrastos/scroll que seriam mais trabalhosas manualmente.

Consequência de ownership:
- o container/provider continua authority dos slots e itens;
- o servidor continua authority da transação válida quando há container server-backed;
- Mouse Tweaks controla a ergonomia do gesto no cliente.

## 3. Gestos de mouse
A documentação oficial descreve melhorias como:
- arrastar com botões do mouse para distribuir/mover stacks;
- uso da roda do mouse para transferir itens entre inventories;
- comportamento aprimorado de LMB/RMB sobre slots.

As combinações exatas dependem das configurações do mod. Não transformar um padrão de hotkey de outra versão em regra sem conferir o config real.

## 4. Compatibilidade com GUIs
O source/documentação trabalha principalmente com telas baseadas em `AbstractContainerScreen`. Há suporte/API para GUIs não padronizadas e compatibilidade adicional.

Isso significa que containers modded podem cair em três classes práticas:
- funcionam pela interface vanilla compatível;
- exigem adaptação específica;
- deliberadamente não devem responder a determinado gesto.

## 5. Creative e casos especiais
O projeto contém tratamento para telas/slots especiais, incluindo inventário criativo e estruturas modernas de inventory. Casos como bundles e slots customizados precisam ser testados porque possuem semântica diferente de um slot vanilla simples.

A ficha não presume que todo menu custom do pack é compatível apenas por herdar uma classe visual semelhante.

## 6. Mixins
A modlist física registra:
- `mousetweaks.mixins.json`;
- `mousetweaks-fabric.mixins.json` dentro da distribuição multiloader/compartilhada observada.

A presença de um config com nome Fabric no JAR NeoForge não autoriza classificá-lo como mod Fabric top-level; é parte do artefato host. O loader efetivo continua NeoForge conforme JAR/release.

## 7. Configuração e input ownership
Mudanças em sensibilidade/gestos devem ser feitas no config do Mouse Tweaks. Conflitos podem ocorrer quando outro mod atribui comportamento ao mesmo wheel/drag em menus.

Em caso de conflito:
1. identificar qual tela reproduz;
2. testar um gesto de cada vez;
3. verificar binds/config dos dois mods;
4. evitar desabilitar globalmente sem necessidade se apenas uma GUI é incompatível.

## 8. Client/server e persistência
CurseForge classifica Mouse Tweaks como Client. Não há gameplay content server-side próprio documentado.

A persistência relevante é configuração client-side. O mod não deve ser usado como authority para inventário persistente nem como prova de que uma transferência foi aceita pelo servidor.

## 9. Changelog exato 2.26.1
A release 2.26.1 foi atualizada para uma nova versão do NeoForge e corrige **crash da config screen** nessa plataforma. Forge/Fabric não precisaram da mesma mudança conforme o changelog.

Esse detalhe é importante para 1.21.1: abrir a tela de configuração é parte da matriz de regressão desta build.

## 10. Riscos
1. **Input collision:** outro mod intercepta wheel/LMB/RMB no mesmo menu.
2. **Custom slot semantics:** máquinas podem ter slots que rejeitam movimentos automatizados pelo gesto.
3. **Client desync perception:** UI pode aparentar movimento antes de server correction em conexão ruim; validar estado final.
4. **Creative edge cases:** regras diferentes de inventory normal.
5. **Bundles/custom inventories:** comportamento precisa ser testado na versão atual.
6. **Config screen:** 2.26.1 corrige crash NeoForge; retestar após updates.
7. **No gameplay ownership:** não atribuir dupe/storage bug ao mod sem reprodução controlada.

## 11. Matriz de testes
- [ ] Cliente NeoForge abre e entra no servidor com Mouse Tweaks 2.26.1.
- [ ] Abrir config screen sem crash.
- [ ] LMB drag em player inventory distribui/move conforme config.
- [ ] RMB drag funciona sem criar stack impossível.
- [ ] Wheel transfer entre player inventory e chest funciona e server confirma estado.
- [ ] Testar chest, barrel, crafting, furnace e menus de máquinas modded representativas.
- [ ] Testar Creative inventory.
- [ ] Testar bundle/slots especiais quando presentes.
- [ ] Repetir gesto rapidamente em multiplayer e confirmar ausência de dupe/desync persistente.
- [ ] Menu incompatível não perde item ao fechar/reabrir.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: JAR, id, runtime e mixin configs.
- CurseForge oficial: project 60089, file ID 5637846, Release NeoForge 1.21/1.21.1 de 17/08/2024, Environment Client.
- Source oficial: `YaLTeR/MouseTweaks`, multiloader com shared/NeoForge sources e testes client de input.
- Changelog exato: atualização NeoForge + fix de config screen crash.
- **Limite:** esta ficha não enumera cada algoritmo interno de click/slot sem necessidade; o contrato operacional é input client-side sobre containers reais.
