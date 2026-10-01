# Sable: Stuff&Additions Compatibility

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#497**: JAR `SableStuffAdditionsCompat v1.0.3-1.21.1.jar`, mod id `sable_sa_compat`, runtime `1.0.3`, SHA-1 `4fc9e4d44986cd08884dc5b170753ec40dc54287`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sable: Stuff&Additions Compatibility
- **Arquivo JAR:** `SableStuffAdditionsCompat v1.0.3-1.21.1.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Bridge Stuff 'N Additions ↔ Sable para JetPack floor detection, Grapplin em sublevels, Block Picker cross-space e correções de crashes/estado em estruturas físicas.
- **Dependências:** Required upstream e satisfeitos: Sable >=1.2.2 (pack 2.0.5) e Create Stuff 'N Additions >=2.1.3 (pack filename 2.1.4b; metadata 2.1.4.). Aeronautics 1.3.2 também está presente, mas não é listado como required relation atual.
- **Sobreposição:** Não duplica Sable nem Stuff 'N Additions; adapta as features do provider a sublevels e remove fixes quando o upstream assume ownership.
- **Compatibilidade/Riscos:** Bridge de interação cross-space. Riscos: transform mismatch, double chain, stale hook, BE data loss via Block Picker, wrong-sublevel insertion, collision crash, provider drift e duplicate fixes absorvidos upstream.
- **Observações:** Common config pode bloquear seleção de block entities pelo Block Picker devido a risco documentado de perda de data; client config pode ocultar netherite jetpack/exoskeletons em primeira pessoa.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial do projeto 1.0.3 + changelogs 1.0.1/1.0.2 como lineage.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stuff-n-additions-x-sable-aeronautics
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 1.0.3. CurseForge publicou 1.0.4 para NeoForge 1.21.1 em 28/09/2026, alinhando dependências a Sable 2.0.5 / Create Stuff 'N Additions 2.1.4b e adicionando modelo de braço do Netherite Exoskeleton em primeira pessoa.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SableStuffAdditionsCompat v1.0.3-1.21.1.jar`, mod id `sable_sa_compat`, versão `1.0.3`, NeoForge 1.21.1. É uma bridge de compatibilidade entre **Create Stuff 'N Additions** e **Sable**, com foco em JetPack, Grapplin, Block Picker e crashes de interação com sublevels.

## 1. Identidade e papel
- **Mod:** Sable: Stuff&Additions Compatibility.
- **JAR:** `SableStuffAdditionsCompat v1.0.3-1.21.1.jar`.
- **Mod id:** `sable_sa_compat`.
- **Versão:** `1.0.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Mixin físico:** `sable_sa_compat.mixins.json`.
- **Papel:** corrigir features de Stuff 'N Additions quando usadas em Sable sublevels/moving structures.

## 2. Dependências reais
O projeto atual exige:
- **Sable >=1.2.2**;
- **Create Stuff 'N Additions >=2.1.3**.

O pack satisfaz com Sable 2.0.5 e `create-stuff-additions1.21.1_v2.1.4b.jar` (metadata runtime 2.1.4.). Create Aeronautics 1.3.2 também está presente e amplia os cenários de uso, mas a página de relações atual lista Sable + Stuff 'N Additions como required dependencies.

## 3. Authority e ownership
- **Stuff 'N Additions:** JetPack, Grapplin, Block Picker, exoskeletons e demais conteúdo próprio.
- **Sable:** sublevels, transforms e physics bodies.
- **Aeronautics/Create:** assembly/movement do ecossistema correspondente.
- **Compat:** adapta raycast/posição/interações e corrige crashes na boundary.

A bridge não deve copiar inventário, energia ou equipment state para um sistema paralelo.

## 4. JetPack — sublevel floor detection
O projeto adiciona **detecção de chão em sublevels** para JetPack. Sem compat, checks que assumem world-space fixo podem não reconhecer corretamente superfície móvel sob o player.

Regression gates: pouso/decolagem em piso parado e em movimento, rotação, bordas entre world/sublevel e transição assembly/disassembly.

## 5. Grapplin — hooking em sublevels
A bridge permite Grapplin ancorar corretamente em sublevels e corrige crash ao hookar essas estruturas.

A posição do anchor precisa seguir o transform do Sable. Se a estrutura mover/rotacionar, a corrente não deve permanecer em world coords antigas nem aplicar força em ponto inexistente.

## 6. Grapplin em offhand
O projeto documenta uso do Grapplin na offhand, com guard explícito para não criar duas chains simultaneamente.

Main-hand/offhand input deve convergir para um único state autoritativo; spam de ambos não pode criar dois anchors/chains para o mesmo item/action.

## 7. Block Picker entre spaces
O Block Picker pode retirar/colocar blocos entre main level e sublevels. Essa é uma boundary funcional de alto risco porque move block state e potencialmente data entre contexts.

O common config desabilita seleção de **block entities** por default/medida de segurança documentada, pois houve casos de perda de block data.

## 8. Block entity safety
Se Block Picker for configurado para permitir BEs, validar NBT/data components/inventory e lifecycle completos. A bridge não deve transferir apenas o block state e perder state persistente.

A regra segura é manter o bloqueio para BEs até teste específico dos providers desejados.

## 9. Fix histórico 1.0.2
A 1.0.2 corrigiu Block-Picked blocks sendo adicionados a sublevels em casos em que não deveriam. Esse bug é regression gate ainda relevante para a 1.0.3.

Um pick/place deve alterar exatamente um context e exatamente uma vez.

## 10. Fix histórico 1.0.1
A 1.0.1 atualizou suporte para Stuff 'N Additions 2.1.3, removeu fixes de tanks que o provider-base já havia absorvido e corrigiu Grapplin permanecendo hooked no inventário.

Isso demonstra uma boundary importante: fixes devem ser retirados quando upstream assume ownership, evitando patch duplicado.

## 11. Floating block collision
O projeto corrige crash de floating blocks ao colidir com sublevels. Collision response precisa usar transform/body válido e não acessar referência removida durante unload/disassembly.

Testar com velocidade baixa/alta e após chunk/sublevel reload.

## 12. Config client-side de primeira pessoa
Há opção client para ocultar **netherite jetpack / exoskeletons** na visão em primeira pessoa. É puramente visual e não deve alterar equipment state, armor attributes ou visibility para outros jogadores.

## 13. Client / Server
- **Servidor:** anchor funcional, Block Picker transfer, collision/lifecycle e permissões de state.
- **Cliente:** render da corrente, first-person equipment visibility e input/prediction.

Cliente não deve conseguir selecionar/mover BE proibida apenas removendo sua própria UI restriction; common config precisa ser respeitada no caminho funcional.

## 14. Lifecycle
Validar:
- equip/unequip JetPack/Grapplin;
- main-hand/offhand swap;
- hook/unhook;
- inventory movement enquanto hooked;
- sublevel assembly/disassembly;
- Block Picker world→sublevel e sublevel→world;
- chunk/sublevel unload/reload;
- break/removal do anchor;
- player death/relog;
- collision de floating block.

## 15. Riscos técnicos
1. **Transform mismatch:** Grapplin/JetPack consulta coordenada local como world.
2. **Double chain:** duas mãos criam state duplicado.
3. **Stale hook:** item fica anchored depois de entrar no inventory.
4. **BE data loss:** Block Picker move block sem data persistente completa.
5. **Wrong-sublevel insertion:** regression histórica 1.0.2.
6. **Collision crash:** floating block toca body removido/stale.
7. **Provider drift:** Stuff 'N Additions/Sable mudam internals.
8. **Duplicate fix:** upstream absorve correção e compat continua interceptando o mesmo path.
9. **Client/server policy mismatch:** cliente permite ação proibida pelo common config.

## 16. Matriz de testes
- [ ] Dedicated server inicia com compat 1.0.3 + Sable 2.0.5 + Stuff 'N Additions 2.1.4b.
- [ ] JetPack reconhece chão em sublevel parado e em movimento.
- [ ] Grapplin ancora em sublevel sem crash e acompanha movimento/rotação.
- [ ] Grapplin na offhand funciona e duas mãos não criam duas chains.
- [ ] Mover Grapplin para inventory encerra hook corretamente.
- [ ] Block Picker world→sublevel altera exatamente um bloco.
- [ ] Block Picker sublevel→world altera exatamente um bloco.
- [ ] Block entities permanecem bloqueadas conforme common config.
- [ ] Se BE selection for habilitada em teste, NBT/inventory permanece íntegro.
- [ ] Floating block collision não crasha após unload/reload.
- [ ] First-person hide config não muda state funcional do equipamento.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física atual: compat 1.0.3, Sable 2.0.5, Stuff 'N Additions filename 2.1.4b e Aeronautics 1.3.2.
- CurseForge oficial atual: JetPack/Grapplin/Block Picker, configs e crash fixes; requisitos Sable >=1.2.2 e Stuff 'N Additions >=2.1.3.
- Changelogs oficiais 1.0.1/1.0.2 usados como lineage de regressão.
- **Limite:** a página do arquivo 1.0.3 não expôs delta textual específico além do estado atual do projeto; não foram inventadas mudanças exclusivas dessa build.

## 18. Atualização upstream 1.0.4 — não instalada
O pack continua em **1.0.3**. A release **1.0.4** foi publicada em 28/09/2026 (file ID **9000433**).

Changelog publicado:
- dependências atualizadas explicitamente para **Sable 2.0.5** e **Create Stuff 'N Additions 2.1.4b**;
- adicionado **modelo de braço em primeira pessoa do Netherite Exoskeleton**, alinhado aos demais exoskeletons;
- nova opção de config para desabilitar esse modelo;
- o modelo também não é exibido quando a opção geral de esconder armaduras na mão/primeira pessoa está ativa.

O snapshot físico já possui Sable 2.0.5 e Create Stuff 'N Additions 2.1.4b, portanto os providers atuais satisfazem exatamente o alvo declarado pela 1.0.4. A atualização ainda não é atribuída ao runtime até o JAR da bridge ser substituído.

Gate de regressão: Netherite Exoskeleton em primeira pessoa, interação com `hide armor first person hand`, config específica da bridge, JetPack/Grapplin/Block Picker em sublevels e coexistência com Punchy/First-person Model/Epic Fight.

Fonte upstream: https://www.curseforge.com/minecraft/mc-mods/create-stuff-n-additions-x-sable-aeronautics/files/9000433
