# Ars Controle

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db813a8e26f0ea2e8c07c8  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Ars Controle
- **Arquivo JAR:** `ars_controle-1.21.1-1.6.15.jar`
- **Versão 1.21.1:** `1.21.1-1.6.15`
- **Categoria:** Magia; Automação
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/Vonr/Ars-Controle
- **Função:** Camada de lógica e automação de Ars Nouveau: 4 blocos funcionais, inspeção de estado/recursos, sinais, referências relacionais, comparadores, Scryer's Linkage e Warping Spell Prism para roteamento remoto de recursos e spells.
- **Dependências:** Ars Nouveau. Integrações opcionais são condicionais a Ars Additions, Alex's Caves e StarbuncleMania quando presentes; não carregar contratos opcionais sem o provider.
- **Compatibilidade/Riscos:** Riscos principais: loops de signal/poll, referências stale após unload/restart, double-processing em item/fluid/energy, perda de causalidade em spell redirection e compatibilidade espacial não comprovada automaticamente com sublevels Sable.
- **Sobreposição:** Cruza automação/remote I/O com Create, AE2 e outras redes, mas seu domínio é lógica mágica Ars. Não substituir APIs/capabilities dos providers remotos nem duplicar o processamento.
- **Observações:** mod id ars_controle. Build 1.6.15 registra 4 blocos + 4 block entities e 31 componentes principais de lógica/inspeção/referência; `test_scythe` é dev-only e não integra o catálogo normal.
- **Procedência:** Runtime/JAR: modlist física 07/09/2026. Registry e build: source oficial Vonr/Ars-Controle branch 1.21, mod_version 1.6.15. Contexto local: GUIA-COMPLETO-MODS-DE-MAGIA snapshot 07/09/2026.
- **Histórico da decisão:** Manter. Ficha reconstruída do zero em 07/09/2026 contra registry/source da build 1.6.15 e modlist física; addon é uma camada de controle real do Ars e não um provider mágico paralelo.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026. Runtime confirmado: ars_controle-1.21.1-1.6.15.jar. Dossiê exaustivo com 4 blocos/4 block entities, 31 componentes de lógica Ars, turret behaviors, Scryer's Linkage, Warping Spell Prism, integrações opcionais, authority, lifecycle e testes.
- **Data da última decisão:** 2026-09-07.

## Dossiê operacional — padrão Alex's Mobs

> 🧠 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_controle-1.21.1-1.6.15.jar`, mod id `ars_controle`, NeoForge 1.21.1. A build 1.6.15 registra **4 blocos + 4 block entities e 31 componentes Ars/logic principais**, além de integrações opcionais carregadas somente quando o provider existe. Ars Nouveau continua authority de Source, spell grammar e cast; Ars Controle adiciona lógica, inspeção, referência e roteamento remoto.

## 1. Identidade e versão
- **Mod:** Ars Controle.
- **JAR:** `ars_controle-1.21.1-1.6.15.jar`.
- **Runtime:** `1.21.1-1.6.15`.
- **Mod id:** `ars_controle`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** lógica, sensores, referências e automação remota no ecossistema Ars Nouveau.
- **Decisão:** **Manter**.

## 2. Blocos registrados — 4
1. `scroll_holder` — holder funcional para scrolls/controle associado.
2. `scryers_linkage` — representa/endereça um bloco remoto e permite interação remota com capacidades suportadas.
3. `temporal_stability_sensor` — sensor de estado temporal usado na camada lógica.
4. `warping_spell_prism` — prism que redireciona projéteis/spells para destino configurado.

Cada bloco possui **block entity própria** registrada. O `test_scythe` encontrado no source é condicionado a `ArsControle.isDev` e **não é contado como conteúdo normal da release**.

## 3. Catálogo de componentes Ars/logic — 31
### Arts — 2
- `LogicSignalProvider`
- `LogicSignalConsumer`

### Modifiers — 3
- `ModifierRandom`
- `ModifierNot`
- `ModifierUntil`

### Effects / inspeção — 8
- `EffectSetTime`
- `EffectSetWeather`
- `InspectRedstone`
- `InspectBlock`
- `InspectEntity`
- `InspectItem`
- `InspectFluid`
- `InspectPower`

### Forms / actions / references — 11
- `ActionSendSignal`
- `MethodState`
- `MethodPoll`
- `InspectPosition`
- `CasterRef`
- `TargetRef`
- `RelationHeard`
- `RelationParent`
- `RelationRoot`
- `RelationChild`
- `RelationSibling`

### Sigil — 1
- `SigilOfManipulation`

### Filters/comparators — 6
- `equals`
- `notequals`
- `lesser`
- `lesserequals`
- `greater`
- `greaterequals`

## 4. Turret behavior provider-native
A build registra comportamentos de turret para `RelationHeard`, `ActionSendSignal`, `InspectPosition`, `InspectItem`, `InspectFluid`, `InspectPower`, `MethodState` e `MethodPoll`. Isso significa que lógica em turret é implementação nativa do addon; não deve ser reexecutada por handler externo.

## 5. Scryer's Linkage
A Linkage funciona como boundary remota para outro bloco. O guia consolidado documenta interação com **itens, fluidos, energia e redstone** no destino suportado. Regras:
- não cachear inventário remoto como se fosse cópia local;
- validar target unload/dimension/removal;
- respeitar capability/ownership do provider real;
- falhar fechado se o destino não estiver disponível.

## 6. Warping Spell Prism
O prism permite redirecionar projéteis mágicos para coordenadas configuradas, desacoplando origem física do cast e local de aplicação. Em integração:
- manter caster/causalidade original;
- não conceder mana/cooldown uma segunda vez no ponto de saída;
- validar cross-dimension e target inválido;
- não presumir compatibilidade automática com sublevels Sable sem teste.

## 7. Integrações opcionais do source
O registry possui extensões condicionais:
- **Ars Additions:** `ActionLinkScryer`.
- **Alex's Caves:** `LookHologram`.
- **StarbuncleMania:** `LookSentinel`.

Essas entradas só existem quando o mod correspondente está carregado. Ausência do provider não deve causar fallback fictício.

## 8. Authority e sobreposição
- **Ars Nouveau:** Source, mana, cast e spell grammar.
- **Ars Controle:** inspeção, sinais, referências, comparadores e roteamento remoto.
- **Create/AE2/outros sistemas:** continuam donos de seus próprios inventories/fluids/energia quando acessados indiretamente.

Ars Controle não é um segundo sistema de rede universal; ele é uma camada de controle mágico sobre contratos reais.

## 9. Riscos
1. loops de sinais/polling;
2. referência remota stale após unload/restart;
3. double-processing de item/fluid/energy se outra bridge tocar o mesmo target;
4. telemetria tratada erroneamente como ação/autoria para Mastery;
5. redirecionamento de spell perdendo causalidade;
6. integrações opcionais carregadas sem provider.

## 10. Matriz de validação
- client + dedicated-server boot;
- testar os 4 blocos e persistência após restart;
- exercitar os 31 componentes por família;
- Scryer's Linkage com item/fluid/energy/redstone e target unload;
- Warping Spell Prism local, longa distância e dimensão quando permitido;
- turret behaviors sem duplicação de cast;
- integrações opcionais presentes/ausentes;
- loops de signal/poll sob carga;
- teste com Sable/sublevels antes de declarar compatibilidade espacial.

## 11. Fontes
- Runtime físico da modlist do projeto.
- Source oficial `Vonr/Ars-Controle`, branch 1.21, build `1.6.15`.
- Guia consolidado de Magia do projeto, snapshot 07/09/2026.
