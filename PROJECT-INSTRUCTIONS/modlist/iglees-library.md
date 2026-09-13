# Iglee's Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817a8410d5d61dc04ace
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iglee's Library
- **Arquivo JAR:** `igleelib-1.21.1-1.2.7.jar`
- **Versão 1.21.1:** 1.21.1-1.2.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/API compartilhada dos mods de iglee42, com helpers de JSON, block entities/energy, recipes tickáveis, inventário, detecção de mods, eventos e utilitários comuns/client.
- **Dependências:** NeoForge 1.21.1. Source 1.2.7 foi desenvolvido contra NeoForge 21.1.113 e declara range `[21,)`; pack usa 21.1.248.
- **Sobreposição:** Biblioteca específica; não substitui outras APIs de energia/config/JSON sem alteração dos consumers. Helpers semelhantes em outras libs não são ABI-equivalentes.
- **Compatibilidade/Riscos:** Riscos: ABI drift com consumers, wrapper JSON custom incompatível, recipe tick duplicado, energy/inventory helper misuse, client/common classloading e mixin drift. Remoção exige mapear consumers reais primeiro.
- **Observações:** A release 1.2.7 adiciona sistema para custom wrappers de records a partir de JSON. Source exato expõe EnergyStorage, SecondBlockEntity, ITickableRecipe, InventoryUtil, JsonHelper, ModsUtils e eventos common/client.
- **Procedência:** modlist.txt física atual + CurseForge oficial file 7000755 + source oficial iglee42/IgleeLibrary branch 1.21 com mod_version 1.2.7 / Minecraft 1.21.1.
- **Fonte:** https://github.com/iglee42/IgleeLibrary/tree/1.21
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Iglee's Library 1.2.7 source-pinned à linha 1.21.1; JSON record wrappers, energy/block-entity/recipe/inventory utilities, events, mixin/lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `igleelib-1.21.1-1.2.7.jar`, mod id `igleelib`, versão reportada `1.21.1-1.2.7`. O source oficial `iglee42/IgleeLibrary:1.21` declara Minecraft 1.21.1 e `mod_version=1.2.7`; esta ficha é source-pinned à linha da release instalada.

## 1. Papel e authority
Iglee's Library é a biblioteca compartilhada para mods de iglee42. Ela fornece tipos-base e helpers; não é authority do gameplay final dos consumers. Cada mod consumidor continua owner de suas máquinas, recipes, inventories e efeitos, mesmo quando usa abstrações da library.

## 2. Build e loader
O source exato declara NeoForge 21.1.113 como build dependency, range `[21,)`, Minecraft 1.21.1 e loader range `[2,)`. O pack usa NeoForge 21.1.248. Isso está dentro da linha declarada, mas updates de loader/library continuam sujeitos a linkage tests dos consumers.

## 3. Superfícies API confirmadas
A árvore 1.21 contém APIs como `EnergyStorage`, `SecondBlockEntity`, `ITickableRecipe`, `InventoryUtil`, `JsonHelper`, `ModsUtils`, além de utilitários e eventos common/client. O JAR físico também declara `igleelib.mixins.json`, portanto mixin compatibility é parte do runtime.

## 4. JSON e release 1.2.7
O changelog exato da 1.2.7 adiciona um sistema para permitir **custom wrappers para records a partir de JSON**. Isso torna parsing/serialization de dados uma boundary real: wrapper registration e formato esperado devem corresponder ao consumer; JSON inválido não deve ser silenciosamente reinterpretado.

## 5. Block entities e energia
`EnergyStorage` e `SecondBlockEntity` são abstrações reutilizáveis. A library não deve manter uma segunda authority de energia quando o consumer já possui state próprio. Save/load, capability exposure, chunk unload e server restart precisam preservar um único valor authoritative por máquina.

## 6. Recipes tickáveis
`ITickableRecipe` indica suporte a processamento ao longo de ticks. Consumers devem garantir admission, progresso, consumo e output exactly-once. Pausa por chunk unload/restart não pode duplicar output nem perder inputs por executar settlement em dois hooks.

## 7. Inventário e helpers
`InventoryUtil` e utilitários comuns reduzem boilerplate, mas operações de insert/extract continuam sujeitas às regras do consumer. Automação externa deve validar simulate/execute quando aplicável e não assumir que helper genérico equivale ao contrato de um handler específico.

## 8. Mod detection e compat
`ModsUtils` fornece superfície para compatibilidade condicional. A presença detectada de um mod não significa que toda versão seja ABI-compatível; integrations devem continuar version-aware quando usam classes/IDs externos.

## 9. Client / server
A árvore possui eventos common e client. Código de mouse/render/input deve permanecer no cliente; recipes, inventories, energia e block-entity state são server-authoritative. Dedicated server não pode carregar classes gráficas por caminho comum/mixin indevido.

## 10. Lifecycle
Validar mod construction, mixin application, data/JSON load, registry setup dos consumers, block-entity load/unload, recipe ticking, login/reconnect e config/data reload quando o consumer usar essas superfícies. Atualização da library é mudança de ABI para todos os dependentes.

## 11. Riscos técnicos
- consumer compilado contra assinatura diferente;
- custom JSON wrapper não registrado ou com schema divergente;
- recipe tick executado duas vezes;
- energy/inventory state duplicado ou perdido;
- client event/class carregado no dedicated server;
- mixin target drift;
- compat condicional ativada apenas por presença, sem compatibilidade de versão;
- remoção da library mantendo consumer obrigatório.

## 12. Matriz de testes obrigatória
- [ ] Dedicated server boot com Iglee's Library 1.2.7 e consumers atuais.
- [ ] Cliente conecta sem linkage/mixin errors.
- [ ] JSON com custom record wrapper válido carrega; inválido falha de modo diagnosticável.
- [ ] Consumer com recipe tickável não duplica settlement após unload/restart.
- [ ] Energy/block-entity state persiste em save/restart.
- [ ] Helpers de inventário não duplicam itens sob automação concorrente.
- [ ] Paths client-only não são carregados no servidor.
- [ ] Atualização futura da library é bloqueada até smoke test de consumers.

## 13. Evidências e limites
- **Modlist física:** JAR, mod id, runtime version e `igleelib.mixins.json`.
- **Source oficial:** branch `1.21`, `mod_version=1.2.7`, Minecraft 1.21.1 e APIs citadas.
- **Release oficial:** file 7000755; changelog do sistema de custom record wrappers JSON.
- **Limite:** lista completa de consumers físicos não foi inferida apenas pelo nome; deve ser confirmada por metadata/loader antes de remoção.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
