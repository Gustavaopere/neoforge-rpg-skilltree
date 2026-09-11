# Create Ultimine

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81dfb0a9e30140cdf881
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Ultimine
- **Arquivo JAR:** `createultimine-1.21.1-neoforge-1.3.2.jar`
- **Versão 1.21.1:** 1.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Bridge Create + FTB Ultimine que registra handlers de right-click para aplicar Manual Application recipes e interações de Wrench do Create sobre a seleção de blocos do Ultimine.
- **Dependências:** Create 6.0.10 ou superior + FTB Ultimine 2101.1.14 ou superior obrigatórios segundo source matching 1.3.2; pack físico usa Create 6.0.10 e FTB Ultimine 2101.1.15. NeoForge mínimo matching: 21.1.219.
- **Sobreposição:** FTB Ultimine 2101.1.15 é provider base da seleção/shape; Create Ultimine é bridge e não duplicata. Create continua owner das Manual Application recipes e Wrench semantics.
- **Compatibilidade/Riscos:** Riscos: private-field/API drift do FTB Ultimine; `isPressed` não restaurado; right-click/event duplicado; Manual Application consumir/aplicar de forma não conservativa; item esgotado ainda processar posições; recipe cache stale; Wrench face/context incorreto; config/claims; drift 2101.1.15 vs 2101.1.14-dev.
- **Observações:** JAR/mod id/runtime 1.3.2 confirmados. Source matching registra `ManualApplication` e `WrenchUse`; config server-side possui `manual_application` e `right_click_wrench`, ambos default true. 1.3.2 corrige crash por acesso inválido a private field do Ultimine.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog oficial Create Ultimine 1.3.2 + source oficial ChAoSUnItY/Create-Ultimine branch `1.21-neoforge` matching 1.3.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-ultimine
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.3.2 com RightClickHandler API, ManualApplicationRecipe, Wrench delegation, `isPressed`, config server-side, mínimos Create/FTB Ultimine e regressão private-field catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔧 **Identidade física e source matching confirmados:** `createultimine-1.21.1-neoforge-1.3.2.jar`, mod id `createultimine`, runtime `1.3.2`. A branch oficial `1.21-neoforge` declara exatamente 1.3.2, Minecraft 1.21.1, Create **[6.0.10,)** e FTB Ultimine **[2101.1.14,)**. O pack usa Create 6.0.10 e FTB Ultimine 2101.1.15.

## 1. Papel e authority
Create Ultimine é uma **bridge** entre Create e FTB Ultimine. FTB Ultimine continua authority da seleção de blocos/shape e do estado da tecla Ultimine; Create continua authority de Wrench e `ManualApplicationRecipe`. A bridge registra handlers de right-click para aplicar essas ações Create sobre a coleção selecionada pelo Ultimine.

## 2. Registro de handlers
O source matching registra dois handlers no `RegisterRightClickHandlerEvent` do FTB Ultimine: `ManualApplication` e `WrenchUse`. Também registra config server-side via FTB Library. Isso confirma que o escopo é right-click em área, não um segundo sistema de vein mining.

## 3. Manual Application em área
`ManualApplication` procura recipes do tipo Create `ITEM_APPLICATION` e, para cada posição selecionada, verifica se o block state e o item segurado satisfazem uma `ManualApplicationRecipe`. Assim, operações Create normalmente feitas manualmente em um bloco podem ser repetidas sobre a seleção válida do Ultimine.

## 4. Recipe Manager como authority
A bridge consulta o Recipe Manager atual para encontrar `ManualApplicationRecipe`; não deve congelar recipes antigas. Datapack/KubeJS reload pode mudar aplicabilidade, input e output. A execução em área precisa refletir o conjunto carregado no servidor naquele momento.

## 5. Transformação de blocos
No source matching, a aplicação destrói o bloco sem drop, calcula `transformBlock`, recoloca o block state transformado e então processa os resultados rolados da recipe. Cada posição precisa resultar em uma única transformação lógica; falha intermediária não pode deixar bloco duplicado ou material liquidado duas vezes.

## 6. Consumo do item segurado
A bridge respeita `shouldKeepHeldItem`, Creative e `UNBREAKABLE`. Fora desses casos, item damageable sofre um ponto de dano; item consumível reduz stack em uma unidade. Em operações massivas, a sequência deve parar/agir corretamente quando o stack se esgota.

## 7. Wrench em área
`WrenchUse` só atua quando a feature está habilitada, o item é `WrenchItem` e também pertence à tag NeoForge `TOOLS_WRENCH`. Para cada posição, cria um `UseOnContext` e delega ao próprio `itemStack.useOn`, preservando a semântica do Wrench/Create em vez de reimplementar rotação/desmontagem.

## 8. Estado `isPressed` do FTB Ultimine
Durante Wrench em área, o source matching lê `FTBUltiminePlayerData.isPressed`, o coloca temporariamente em `false`, executa cada `useOn` e restaura o valor original. Esse state manipulation é um boundary delicado: exceção ou retorno inesperado não deve deixar o jogador permanentemente com estado de tecla divergente.

## 9. Ray trace e alvo
O handler obtém ray trace do jogador e deriva um `BlockHitResult` por posição selecionada. A seleção inicial vem do Ultimine; direção/face/contexto usado pelo Wrench precisa continuar coerente com a interação pretendida, especialmente em blocos Create com comportamento dependente da face clicada.

## 10. Config server-side
O source matching define `createultimine-server` com grupo `features` e dois booleans default `true`: `manual_application` e `right_click_wrench`. São controles administrativos server-side; desabilitar uma feature precisa tornar seu handler inerte sem remover o provider base.

## 11. Dependências mínimas exatas
A metadata matching exige NeoForge `[21.1.219,)`, Minecraft `[1.21.1,1.22)`, Create `[6.0.10,)` e FTB Ultimine `[2101.1.14,)`, todos side BOTH. O pack satisfaz Create no piso exato e usa FTB Ultimine 2101.1.15, uma revisão acima da build usada no desenvolvimento.

## 12. Delta 1.3.2
O changelog 1.3.2 atualiza FTB Ultimine para 2101.1.14 e corrige crash causado por acesso inválido a campo privado do Ultimine. Também eleva mínimos forçados de Create/Ultimine e adiciona traduções chinesas Simplified/Traditional. O acesso a internals de Ultimine é portanto um regression gate explícito.

## 13. Drift para FTB Ultimine 2101.1.15
O pack está em 2101.1.15. A dependency range permite essa versão, mas não prova ausência de mudança comportamental. Testar especialmente right-click API, player data/ray trace e restauração de `isPressed`, superfícies que a bridge usa diretamente.

## 14. Client/server e multiplayer
Seleção/visualização da área pode envolver cliente do Ultimine, mas transformação de blocos, recipe execution, consumo/dano de item e Wrench interaction precisam convergir no servidor. Dois packets/retries não podem aplicar a mesma seleção duas vezes.

## 15. Permissions e proteção
A bridge amplia uma interação manual para múltiplos blocos. Qualquer sistema de claims/proteção interceptado normalmente por `useOn`/block changes precisa continuar sendo respeitado. Não assumir bypass ou compatibilidade sem teste com o provider efetivo do servidor.

## 16. Lifecycle e reload
Validar login/reconnect, mudança de config server-side, `/reload` de recipes, alteração de shape do Ultimine, esgotamento de item no meio da seleção, blocos mudando entre seleção e execução e server restart.

## 17. Sobreposição
FTB Ultimine 2101.1.15 é o provider base e não é duplicata: ele seleciona/coordena a área; Create Ultimine acrescenta handlers para ações Create. Outros vein-mining mods só seriam overlap se atuassem sobre as mesmas ações/right-click, o que não é presumido aqui.

## 18. Riscos
1. FTB Ultimine muda field/API e reintroduz crash de acesso privado.
2. `isPressed` não é restaurado após erro e deixa state do player inconsistente.
3. A mesma posição é aplicada duas vezes por retry/event duplication.
4. Manual Application consome item sem aplicar bloco ou aplica sem consumir.
5. Stack se esgota e posições posteriores ainda são processadas.
6. Recipe cache fica stale após `/reload`.
7. Wrench usa face/contexto incorreto em posições derivadas.
8. `useOn` produz comportamento destrutivo em massa não esperado pelo operador.
9. Config client/server diverge ou handler permanece ativo após desabilitar feature.
10. FTB Ultimine 2101.1.15 altera semantics esperadas pela build 2101.1.14-dev.
11. Claims/proteções não interceptam uma das rotas de transformação.

## 19. Matriz de testes
- [ ] Dedicated server inicia com Create Ultimine 1.3.2 + Create 6.0.10 + FTB Ultimine 2101.1.15.
- [ ] Ultimine comum continua funcionando sem Create-specific action.
- [ ] Manual Application transforma somente posições válidas da seleção.
- [ ] Item consumível perde exatamente uma unidade por aplicação válida.
- [ ] Item damageable recebe exatamente o dano previsto por aplicação válida.
- [ ] `shouldKeepHeldItem`, Creative e Unbreakable não consomem indevidamente.
- [ ] Wrench em área delega `useOn` e produz uma ação por bloco válido.
- [ ] `isPressed` é restaurado após operação normal e após erro controlado.
- [ ] Desabilitar `manual_application` torna a feature inerte.
- [ ] Desabilitar `right_click_wrench` torna a feature inerte.
- [ ] `/reload` troca recipes de Manual Application sem cache stale.
- [ ] Esgotar item no meio da seleção não produz aplicações gratuitas.
- [ ] Dois jogadores/requests simultâneos não duplicam mutações.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma JAR/mod id/runtime e FTB Ultimine 2101.1.15. O source matching 1.3.2 confirma handlers, config, recipe lookup, transformação, consumo, Wrench delegation e manipulação de `isPressed`. `neoforge.mods.toml` confirma mínimos Create 6.0.10 e Ultimine 2101.1.14. O changelog exato confirma o crash de private-field access e atualização de mínimos. Comportamentos não demonstrados pelo source permanecem fail-closed.

> 🔒 **Boundary canônico:** FTB Ultimine escolhe a área; Create define a ação por bloco; Create Ultimine apenas aplica essa ação sobre a seleção. Cada bloco/item deve ser liquidado exatamente uma vez no servidor.
