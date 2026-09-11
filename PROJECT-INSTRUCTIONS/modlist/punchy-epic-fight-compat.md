# Punchy Epic Fight Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81938016e460e6e58ec4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `punchy_epicfight_neoforge.jar`, mod id `punchy_epicfight_compat`, runtime `1.0.0`; Punchy 2.7e presente e Epic Fight presente no snapshot físico
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Punchy Epic Fight Compat 1.0.0 e Punchy 2.7e estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Punchy Epic Fight Compat
- **Arquivo JAR:** `punchy_epicfight_neoforge.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, RPG
- **Função:** Compat que desativa/ajusta renderização do Punchy quando Epic Fight ou sistemas de combate compatíveis estão ativos, evitando animações concorrentes.
- **Dependências:** Punchy + Epic Fight. Pack físico contém Punchy 2.7e e Epic Fight 21.17.3.1; a bridge só tem função com ambos presentes.
- **Sobreposição:** Ponte específica Punchy↔Epic Fight; não substitui nenhum mod-base e não controla outros animation frameworks.
- **Compatibilidade/Riscos:** Bridge de presentation. Riscos: drift de hooks entre Punchy/Epic Fight, stale suppression após battle mode, overlap durante transição e falsa expectativa de arbitrar outros animation providers.
- **Observações:** Release NeoForge 1.21.1 de 21/03/2026; desabilita o rendering do Punchy durante Epic Fight battle mode para evitar animação concorrente.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Epic Fight X Punchy! Neo 1.0.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-fight-x-punchy-neo
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Punchy Epic Fight Compat 1.0.0 reconstruído: battle-mode render arbitration, dependency boundary, lifecycle, version drift, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `punchy_epicfight_neoforge.jar`, mod id `punchy_epicfight_compat`, versão `1.0.0`, NeoForge 1.21.1. É uma bridge mínima entre Punchy e Epic Fight: **desabilita o rendering do Punchy enquanto o jogador está em battle mode**, evitando duas camadas de animação concorrentes. Punchy 2.7e e Epic Fight 21.17.3.1 estão presentes.

## 1. Identidade e papel
- **Mod:** Punchy Epic Fight Compat / Epic Fight X Punchy! Neo.
- **JAR:** `punchy_epicfight_neoforge.jar`.
- **Mod id:** `punchy_epicfight_compat`.
- **Runtime:** `1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Licença:** All Rights Reserved.
- **Papel:** bridge de presentation entre Punchy e Epic Fight.
- **Decisão:** Sem decisão.

## 2. Dependências funcionais
O projeto informa que precisa de **Punchy + Epic Fight** para fazer qualquer coisa. Ambos estão presentes na modlist atual.

Sem um dos dois, a bridge perde o alvo funcional; isso não significa que ela seja authority de nenhuma das engines.

## 3. Battle-mode arbitration
A regra publicada é direta: quando Epic Fight entra em **battle mode**, o rendering do Punchy é desabilitado. O objetivo é evitar clutter/concorrência de animações.

Ownership:
- Epic Fight → battle mode, combate, armature/animations funcionais;
- Punchy → first-person presentation fora da arbitragem;
- bridge → decisão de suprimir Punchy no contexto de battle mode.

## 4. Fora do battle mode
Ao sair do battle mode, Punchy precisa retornar ao estado visual esperado. O ponto de maior risco é state stale: continuar suprimido quando não deveria, ou reativar antes da transição terminar.

Testar toggle repetido e mudança de dimensão/relog durante battle mode.

## 5. Presentation, não gameplay
A bridge não deve mudar dano, stamina, hitbox, cooldown ou combo do Epic Fight. Ela apenas controla rendering do Punchy conforme o estado de combate.

Se gameplay mudar ao instalar/remover a bridge, investigar efeitos colaterais concretos antes de atribuir causalidade.

## 6. Versão 1.0.0
A publicação oficial lista exatamente `punchy_epicfight_neoforge.jar` como Release NeoForge 1.21.1, publicada em 21/03/2026.

O projeto é descrito como forward port simples da integração, enquanto não houver integração oficial equivalente.

## 7. Drift entre bases
A bridge é pequena, mas depende de conseguir detectar o battle mode da versão Epic Fight instalada e controlar o rendering da versão Punchy instalada.

Atualizações de qualquer base podem quebrar:
- detecção do estado;
- hook de render;
- timing de reativação;
- assinatura/class location.

## 8. Relação com outras animações
First Person Model, Player Animator/PAL, CPM e outros providers podem continuar ativos. A bridge cobre especificamente **Punchy ↔ Epic Fight**; não é árbitro universal do stack de animações.

## 9. Lifecycle
Testar:
- entrar/sair de battle mode;
- death/respawn em battle mode;
- reconnect;
- dimension change;
- troca de arma;
- first/third person;
- Punchy resource reload;
- Epic Fight config/reload quando aplicável.

## 10. Riscos
1. **Version drift:** Punchy/Epic Fight mudam hooks.
2. **Stale suppression:** Punchy fica desligado após sair do battle mode.
3. **Late suppression:** frame/animation overlap ao entrar no battle mode.
4. **Other animation providers:** bridge não cobre conflitos externos.
5. **False authority:** bug de combat state atribuído à bridge sem prova.
6. **Redundância futura:** base pode incorporar integração nativa em release posterior.

## 11. Matriz de testes
- [ ] Cliente inicia com bridge 1.0.0 + Punchy 2.7e + Epic Fight atual.
- [ ] Fora do battle mode, Punchy funciona normalmente.
- [ ] Entrar em battle mode suprime Punchy sem flicker grave.
- [ ] Sair do battle mode restaura Punchy imediatamente/consistentemente.
- [ ] Toggle repetido não deixa state stale.
- [ ] Death/relog/dimension change em battle mode convergem ao estado correto.
- [ ] Troca de arma/combo do Epic Fight não é alterada funcionalmente.
- [ ] First Person Model/other animation layers não são indevidamente desabilitados pela bridge.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: bridge 1.0.0, Punchy 2.7e e Epic Fight presentes.
- CurseForge oficial: Epic Fight X Punchy! Neo, Release NeoForge 1.21.1, arquivo exato `punchy_epicfight_neoforge.jar`.
- Descrição oficial: desabilita Punchy rendering em Epic Fight battle mode.
- **Limite:** não foi presumida integração com outros animation mods; a bridge cobre apenas a interseção publicada.
