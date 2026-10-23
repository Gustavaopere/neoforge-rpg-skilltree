# Cyclops Core

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#213**: JAR `cyclopscore-1.21.1-neoforge-1.30.0.jar`, mod id `cyclopscore`, runtime `1.30.0`, SHA-1 `1eab17f360da5c8523d984e51351ee35d7573699`.

## Propriedades do registro

- **Mod:** Cyclops Core
- **Arquivo JAR:** `cyclopscore-1.21.1-neoforge-1.30.0.jar`
- **Versão 1.21.1:** `1.30.0`
- **Categoria:** Biblioteca
- **Função:** Core/API do ecossistema CyclopsMC com infraestrutura compartilhada para mods consumidores, incluindo GUI/widget, config/network/data/registry utilities e outros helpers comuns.
- **Dependências:** NeoForge 1.21.1; necessidade determinada pelos consumers CyclopsMC instalados. Não remover ou substituir por outra core library enquanto dependentes ativos compilarem contra suas APIs.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de ABI/version drift em GUI/widgets, networking, data/NBT paths e helpers de consumers. O runtime atual 1.30.0 incorpora os fixes 1.29.4 (#237/#236) e novas otimizações de ingredient index/hash; validar consumers CyclopsMC, especialmente caminhos de ingredient lookup/index e IntegratedTerminals.
- **Fonte:** https://github.com/CyclopsMC/CyclopsCore/blob/master-26-lts/CHANGELOG-1.21.1.md
- **Procedência:** modlist física atual de 20/09/2026 confirma `cyclopscore-1.21.1-neoforge-1.30.0.jar` / runtime 1.30.0. Changelog oficial CyclopsMC e listagem oficial CurseForge revalidados em 20/09/2026 confirmam 1.30.0 como Release NeoForge 1.21.1 de 11/09/2026.
- **Observações:** mod id `cyclopscore`; runtime físico 1.30.0; JAR instalado `cyclopscore-1.21.1-neoforge-1.30.0.jar`. Changelog oficial 1.30.0: novo `compute` em IIngredientMapMutable para hash de ingredient key uma vez por index update; remoção de HashMap lookup redundante em IngredientMapWrappedAdapter.iterator(); melhorias de ItemStack hashing e classified ingredient lookup.
- **Atualização/Status:** REATUALIZADO EM 20/09/2026 — runtime físico cyclopscore-1.21.1-neoforge-1.30.0.jar / 1.30.0 confirmado e correspondente à latest Release NeoForge 1.21.1 de 11/09/2026. O delta 1.30.0 otimiza ingredient indexing/hashing: hash da ingredient key uma vez por index update, evita HashMap lookup redundante no IngredientMapWrappedAdapter.iterator() e melhora ItemStack hashing/classified ingredient lookup.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Cyclops Core 1.29.4-1137 havia sido reconciliado pela modlist física anterior; a modlist física atual de 20/09/2026 confirma a atualização instalada para `cyclopscore-1.21.1-neoforge-1.30.0.jar` / runtime 1.30.0.
- **Sobreposição:** Core específica CyclopsMC. Coexistência com outras GUI/config/network/core libraries não implica redundância; consumers dependem de contracts próprios.
- **Data da última decisão:** 2026-09-20

# Dossiê operacional — padrão Alex's Mobs
> ⚙️ Autoridade física atual: `cyclopscore-1.21.1-neoforge-1.30.0.jar`, mod id `cyclopscore`, runtime `1.30.0`. As referências 1.29.3/1.29.4 permanecem apenas como histórico e **não** sobrepõem a modlist física atual de 20/09/2026.
## 1. Papel e authority
Cyclops Core é a core library do ecossistema CyclopsMC. Consumers usam suas utilities de GUI/widgets, data/config, networking, registries e outros helpers; o consumer permanece authority de máquinas, energia, logic networks, mobs ou gameplay final.
## 2. Consumer-driven necessity
A necessidade do JAR é definida pelo dependency graph dos mods CyclopsMC instalados. Remover a library com consumer ativo pode impedir bootstrap ou produzir linkage errors.
Outra core library não é substituto automático, mesmo quando oferece GUI/network/config helpers semelhantes.
## 3. GUI e widgets
A linha 1.29.x possui correções concretas em GUI/widgets, comprovando que essa é uma superfície central da library.
A 1.29.3 corrigiu GUIs que não abriam quando o jogador era forçado a uma pose agachada (#233). Esse histórico continua relevante como regression context, mas a build atual é 1.29.4.
## 4. Infobook — fix 1.29.4
A 1.29.4 corrige **crash ao passar o mouse no canto superior esquerdo de recipe pages do infobook** (#237).
Regression gate: recipe pages precisam abrir, aceitar hover nos limites da interface e fechar/reabrir sem exception.
## 5. Scrolling container — fix 1.29.4
A release também corrige o scrolling container para alcançar a **última linha de grids multi-coluna** (#236).
Essa correção é UI/layout, mas pode afetar acesso funcional a entries que antes ficavam inacessíveis. Testar listas grandes em consumers reais.
## 6. Networking
Cyclops Core fornece infraestrutura de networking usada pelos consumers. Packet semantic e authority pertencem ao consumer.
Handlers devem validar contexto/side e não executar a mesma ação duas vezes em retry/reconnect. Atualizações de core podem afetar codec/dispatch mesmo sem alterar gameplay diretamente.
## 7. Data, config e registries
A library fornece utilities para dados/config/registration usados pelo ecossistema. Schema/defaults e conteúdo final pertencem ao consumer.
Reload ou version migration não deve deixar IDs/caches stale nem re-registrar entradas.
## 8. NBT/data expressions
Versões recentes da linha 1.21.1 adicionaram/ajustaram helpers de expressão/NBT path. Esta ficha usa esse histórico apenas para delimitar a superfície técnica da core; não atribui syntax/API concreta da 1.29.4 além do que o upstream publica.
Para código próprio, pin do source/JAR deve preceder uso de símbolos específicos.
## 9. Client/server
Cyclops Core é infraestrutura comum. GUI/render é client-facing; networking/data/config e gameplay-support podem operar em ambos os lados conforme consumer.
Dedicated server não deve carregar screens/widgets para inicializar common services.
## 10. Lifecycle
Validar construction/bootstrap, consumer registration, client join, GUI open/close, datapack/config reload quando aplicável, disconnect/reconnect e server restart.
Widget/container state não deve sobreviver de forma incorreta a screen recreation ou troca de mundo.
## 11. Runtime 1.30.0 e delta de performance
A versão fisicamente instalada é **1.30.0** no JAR `cyclopscore-1.21.1-neoforge-1.30.0.jar`, correspondente à Release NeoForge 1.21.1 de 11/09/2026.
O changelog oficial 1.30.0 registra três mudanças relacionadas a ingredient indexing/performance:
- hash da ingredient key apenas uma vez por index update, por meio de um novo `compute` em `IIngredientMapMutable` (#241);
- remoção de lookup redundante em `HashMap` no `IngredientMapWrappedAdapter.iterator()` (#232), com melhoria declarada para IntegratedTerminals;
- melhorias de performance em `ItemStack` hashing e classified ingredient lookup (#240).
Esses deltas fazem parte do runtime atual. O objetivo é performance/eficiência de lookup; a semântica final dos ingredientes e inventories continua pertencendo aos consumers.
Sintomas possíveis de incompatibilidade/version drift continuam incluindo:
- `NoSuchMethodError`/`NoClassDefFoundError`;
- GUI que não abre;
- infobook crash;
- scroll/container layout incorreto;
- packet/data mismatch;
- ingredient index/lookup inconsistente em consumer;
- consumer que inicia parcialmente.
Diagnóstico deve registrar **Cyclops Core instalado 1.30.0**, consumer e ação exata.
## 12. Reconciliação de versão
O banco chegou a conter referências 1.29.3 e depois 1.29.4-1137. A autoridade física atual resolve o runtime do pack: **a modlist de 20/09/2026 contém ****`cyclopscore-1.21.1-neoforge-1.30.0.jar`**** / runtime 1.30.0**.
A publicação oficial confirma **1.30.0 para NeoForge 1.21.1** em 11/09/2026. Portanto o catálogo atual fica alinhado: **instalado = 1.30.0; latest Release 1.21.1 = 1.30.0**.
## 13. Riscos
1. Remover core com consumer ativo.
2. Atualizar core isoladamente e quebrar ABI.
3. Infobook/recipe page regredir (#237).
4. Grid multi-coluna esconder última linha (#236).
5. Forced-crouch GUI regression histórica (#233).
6. Client GUI class carregar no dedicated server.
7. Packet/data handler duplicar state.
8. Guia desatualizado substituir equivocadamente a versão física.
9. Mudanças de ingredient hashing/index da 1.30.0 exporem regressão em consumers que dependem de lookup/classificação.
## 14. Matriz de testes
1. Dedicated server boot com consumers Cyclops atuais.
2. Client join sem linkage errors.
3. Abrir GUIs dos consumers em pose normal e forced crouching.
4. Infobook recipe pages: hover em bordas/canto superior esquerdo — regression #237.
5. Grid multi-coluna grande: alcançar última linha — regression #236.
6. Network actions dos consumers exactly once.
7. Config/data reload quando aplicável.
8. Disconnect/reconnect/restart sem duplicate registration.
9. Validar a 1.30.0 física com consumers CyclopsMC, incluindo ingredient indexing/lookup e IntegratedTerminals sob inventories representativos.
## 15. Evidência
- modlist física atual de 20/09/2026: `cyclopscore-1.21.1-neoforge-1.30.0.jar` / Cyclops Core runtime 1.30.0;
- changelog oficial CyclopsMC 1.30.0: `IIngredientMapMutable.compute`/hash-once (#241), remoção de lookup redundante no `IngredientMapWrappedAdapter.iterator()` (#232) e melhoria de `ItemStack` hashing/classified ingredient lookup (#240);
- CurseForge oficial: Release 1.30.0 para NeoForge 1.21.1 publicada em 11/09/2026;
- 1.29.4 preservada como lineage: fixes #237 e #236;
- changelog anterior 1.29.3: forced crouching GUI fix #233.
> 🔒 Boundary canônico: **Cyclops Core fornece infraestrutura; a modlist física define sua versão; consumers mantêm authority do gameplay final**.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Cyclops Core
- **Último candidato 1.21.1 / NeoForge localizado:** **1.30.0-1205 (Beta build, 18/10)**
- **Estado:** **BLOQUEADO COMO BETA**
- **Gate de promoção:** Não substituir baseline semanticamente 1.30.0 por build beta sem verificar consumers Cyclops, configs, networking e compatibilidade binária.
- **Fonte primária do dossier reconsultada:** https://github.com/CyclopsMC/CyclopsCore/blob/master-26-lts/CHANGELOG-1.21.1.md

## Auditoria cumulativa #45 — Cyclops Core Beta 1.30.0-1205 (22/10/2026)

**Baseline físico:** cyclopscore-1.21.1-neoforge-1.30.0.jar, runtime 1.30.0. **Último arquivo NeoForge 1.21.1:** cyclopscore-1.21.1-neoforge-1.30.0-1205.jar, **Beta** 18/10; o sufixo -1205 é identificador de build, não 1.30.1 Release.

**Evidência limitada:** o changelog oficial CHANGELOG-1.21.1.md em master-26-lts termina na Release 1.30.0 de 11/09: hash de ingredient key somente uma vez em IIngredientMapMutable.compute, redução de lookup redundante em IngredientMapWrappedAdapter.iterator e performance de ItemStack/ingredient classifications. Não há seção específica para Beta -1205. Alterações de 26.x não são atribuíveis automaticamente a NeoForge 1.21.1.

**Estado BLOQUEADO da auditoria integral:** sem changelog de Beta -1205 ou source diff inequivocamente associado ao binário, não existe evidência suficiente para descrever a diferença por build. Manter baseline e validar consumers EvilCraft, Integrated Dynamics/Terminals, hashing/collections, networking, GUI, infobook, server startup e reload somente depois de resolver a identificação do delta. Não promover uma Beta sem QA.

Fontes: https://www.curseforge.com/minecraft/mc-mods/cyclops-core/files/all?version=1.21.1 ; https://github.com/CyclopsMC/CyclopsCore/blob/master-26-lts/CHANGELOG-1.21.1.md .


## Delta Beta NeoForge 1.30.0-1205 resolvido por comparação upstream — 23/10/2026

> **A observação anterior "Estado BLOQUEADO da auditoria integral" fica superada quanto à identificação da mudança de código publicada.** O artefato `cyclopscore-1.21.1-neoforge-1.30.0-1205.jar` foi publicado no CurseForge como **Beta para Minecraft 1.21.1, NeoForge em 18/10/2026**. A última Release **estável** para esse ambiente permanece `cyclopscore-1.21.1-neoforge-1.30.0.jar` de 11/09/2026. O JAR físico do pack é a Release 1.30.0, não a Beta.

### Investigação source exata da janela Release → Beta
Foi comparado o branch upstream oficial `master-1.21-lts` entre os commits:
- baseline de versão `c0372426f` (11/09/2026, **Bump mod version**), associado à linha estável 1.30.0;
- ponta após o hotfix `3e43a02bc` (18/10/2026, **Fix incorrect key binding labels being shown in appendices**, issue #255).

**Comparação GitHub oficial:** https://github.com/CyclopsMC/CyclopsCore/compare/c0372426f...3e43a02bc . Ela mostra **um único commit e um único arquivo alterado**:
`loader-neoforge/src/main/java/org/cyclops/cyclopscore/infobook/pageelement/KeyBindingAppendix.java`.

O patch na renderização de informações de tecla substitui:
```java
String binding = L10NHelpers.localize(keyBinding.saveString());
```
por:
```java
String binding = keyBinding.getTranslatedKeyMessage().getString();
```

**Efeito funcional documentável:** exibição correta do nome/descrição traduzida da tecla efetivamente associada a um atalho nas páginas de apêndice de infobooks/GUI. Trata-se de correção de interface na camada **NeoForge**; **não** há evidência de nova API pública, mudança de contrato de ingredientes/receitas, networking, serialização, configs ou mecânica de EvilCraft/Integrated Dynamics nessa comparação.

**Papel de Beta:** a publicação Beta 1205 e o commit de mesmo dia/branch tornam a mudança source inequívoca no intervalo; entretanto nenhum arquivo binário foi disassemblado para confirmar seu SHA/classe. Não atribuir demais alterações de branches 26.x à linha Minecraft 1.21.1.

**QA sugerido para eventual promoção física:** abrir infobooks de Integrated Dynamics/EvilCraft com keybindings remapeados, layout de apêndices sob português/inglês, teclas modificadas por outros mods e fallback quando bindings não configurados; smoke de inicialização dedicado e cliente. Nenhum teste in-game nem troca de JAR foi executado.

**Resultado documental:** intervalo `1.30.0 → 1.30.0-1205 Beta` investigado com comparação de commits 1:1 e as restrições de confiança declaradas. A diferença técnica foi especificada; pode ser removido o **item #45 da lista de pesquisas documentais**, sem afirmar instalação ou certificação binária.
**Fontes:** https://www.curseforge.com/minecraft/mc-mods/cyclops-core/files/all?version=1.21.1 ; https://github.com/CyclopsMC/CyclopsCore/commit/3e43a02bc .
