# GeckoLib 4

## Propriedades do registro

- **Mod:** GeckoLib 4
- **Arquivo JAR:** `geckolib-neoforge-1.21.1-4.9.2.jar`
- **Versão 1.21.1:** `4.9.2`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/bernie-g/geckolib/tree/1.21.1
- **Função:** Engine/API de animação 3D por keyframes para entidades, itens, blocos e armaduras, com controllers, animações concorrentes, easings e keyframes de som/partículas/eventos.
- **Dependências:** NeoForge 1.21.1; runtime/API consumido por diversos mods. O branch oficial 1.21.1 confirma arquitetura da linha, enquanto a release física 4.9.2 é pinada pela distribuição oficial.
- **Compatibilidade/Riscos:** Riscos: asset/bone/animation-name drift em consumers, controllers conflitantes, keyframe side effects duplicados, stale render cache, client classloading em dedicated server e ABI drift. 4.9.2 corrige partialTick passado a GeoRenderLayer#preRender; 4.9.3, não instalada, corrige amplo conjunto de Molang/easing/networking/rendering/serialization issues.
- **Sobreposição:** Runtime/API de animação de mods; não substitui resource-pack/model frameworks como EMF/ETF/Fresh Animations/Fusion. Conflito só é assumido quando duas camadas alteram concretamente o mesmo renderer/model.
- **Observações:** JAR físico `geckolib-neoforge-1.21.1-4.9.2.jar`. Não é equivalente a EMF/Fresh Animations/Fusion. Born in Chaos é consumer confirmado e o JAR físico do Epic Fight expõe compat GeckoLib. Upstream 4.9.3 está disponível desde 16/09/2026, mas não está instalado.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `geckolib-neoforge-1.21.1-4.9.2.jar`, mod id `geckolib`, runtime `4.9.2` e SHA-1 `14c64013cadee7d28f3685f94350f9a4d2ec6d86`. CurseForge oficial confirma 4.9.3 como release NeoForge 1.21.1 mais recente em 16/09/2026, não instalada.
- **Atualização/Status:** REAUDITADO EM 22/09/2026 — lote físico #301: GeckoLib 4.9.2 reconfirmado; upstream 4.9.3 (16/09/2026) registrado como atualização disponível, não instalada.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #301: JAR `geckolib-neoforge-1.21.1-4.9.2.jar`, mod id `geckolib`, runtime `4.9.2`, SHA-1 `14c64013cadee7d28f3685f94350f9a4d2ec6d86`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `geckolib-neoforge-1.21.1-4.9.2.jar`, mod id `geckolib`, versão `4.9.2`. A release 4.9.2 é confirmada pela distribuição oficial para NeoForge 1.21.1; o branch oficial `bernie-g/geckolib:1.21.1` é usado para arquitetura/API da linha, sem presumir que seu HEAD corresponda byte a byte ao JAR 4.9.2.
</callout>

## 1. Identidade e papel

GeckoLib 4 é a engine de animação usada por mods para entidades, itens, blocos, armaduras e outros objetos animáveis. O próprio source descreve suporte a animações 3D complexas baseadas em keyframes, mais de 30 easings, animações concorrentes e keyframes de som, partículas e eventos.

## 2. Authority

GeckoLib é authority da **execução/renderização do contrato de animação** fornecido pelo consumer. Ele não é authority do gameplay do mob, dano, IA, spell, item ou bloco animado. O consumer continua dono do state lógico; GeckoLib transforma esse state em animação e callbacks.

## 3. Assets e modelo de animação

Consumers fornecem modelos/geo, texturas e animation resources. Nomes de animação, bones, keyframes e controllers formam um contrato entre código e assets. Asset ausente, JSON inválido, bone renomeado ou animation name divergente pode quebrar apenas o consumer correspondente sem implicar defeito do GeckoLib.

## 4. Controllers, concorrência e transições

A engine suporta múltiplas animações/controllers e easings. Integrações próprias devem evitar dois owners tentando dirigir o mesmo bone/state sem uma regra explícita de prioridade. Transição visual não deve disparar novamente efeitos de gameplay que já foram liquidados pelo servidor.

## 5. Keyframes de evento, som e partículas

Sound, particle e event keyframes são superfícies de side effect. Som/partícula client-side podem ser reapresentados, mas dano, spawn, reward ou state persistente não devem depender exclusivamente de um callback visual. Se um consumer usa event keyframe para gameplay, validar side e exatamente-uma-vez.

## 6. Render lifecycle

Model/animation resources são carregados no ciclo de resources do cliente. Resource reload, troca de dimensão, despawn/respawn, entity replacement e reconnect precisam invalidar state visual derivado sem carregar animation state de uma instância antiga para outra.

## 7. Release física 4.9.2

A release oficial 4.9.2 para NeoForge 1.21.1 corrige o `partialTick` incorreto passado a `GeoRenderLayer#preRender`. Esse fix torna render layers que dependem de interpolação um regression gate direto para a versão instalada.

## 8. Upstream 4.9.3 — disponível, não instalado

A release oficial **4.9.3** para NeoForge 1.21.1 foi publicada em 16/09/2026. O pack físico permanece em **4.9.2**; portanto o delta abaixo é apenas referência de atualização/regression comparison, não comportamento atribuído ao runtime atual.
O changelog 4.9.3 corrige, entre outros pontos:
- início/fim de easing Catmull-Rom ignorado;
- parsing/precedência de Molang, ternários, variáveis/funções negativas e valores numéricos;
- operadores `OR`/`AND` avaliando eager o lado direito e aliases `&`/`|`;
- serialização de `LoopType.DEFAULT`;
- crashes raros em `ItemArmorGeoLayer` e durante carregamento concorrente de singleton animatables;
- networking opcional no Forge e serverbound packets em dedicated server;
- igualdade de `RawAnimation`, `KeyframeData` e subclasses;
- funções trigonométricas e randomização de MathFunctions;
- suporte a mais propriedades direction de BlockState em GeoBlock rendering;
- função Molang `math.min_angle`.

## 9. Client / server

A distribuição é marcada Client & Server porque consumers podem referenciar tipos/API em common/server code. Isso não significa que o servidor renderize modelos. Renderers, textures e interpolation pertencem ao cliente; state de gameplay e sincronização pertencem ao consumer/servidor.

## 10. Integrações concretas no pack

Há consumers reais no pack que declaram ou expõem compatibilidade com GeckoLib. Exemplos confirmados na auditoria incluem **Born in Chaos** como dependente e **Epic Fight** com mixin de compatibilidade GeckoLib no JAR físico. Isso não é uma lista exaustiva; cada consumer deve manter sua própria ficha e versão.

## 11. Sobreposição com o stack visual

GeckoLib não substitui Fresh Animations/EMF/ETF/Fusion. GeckoLib é runtime/API para animações implementadas por mods; EMF/ETF e resource packs alteram modelos/texturas em outra camada. Conflito surge apenas quando mais de uma camada tenta transformar/renderizar o mesmo objeto.

## 12. Riscos técnicos

- assets/modelos/animações inválidos em consumers;
- bone ou animation name drift após update;
- controller duplicado ou state machine conflitante;
- keyframe de evento produzindo side effect duplicado;
- render layer usando interpolation/partial tick incorreto;
- stale animation cache após resource reload ou entity replacement;
- consumer carregando classes exclusivamente client no dedicated server;
- incompatibilidade binária quando consumer foi compilado contra API diferente;
- conflito com outro renderer/model layer no mesmo tipo de entidade/item.

## 13. Matriz de testes obrigatória

- [ ] Dedicated server inicia com GeckoLib 4.9.2 e consumers atuais.
- [ ] Cliente conecta sem linkage/classloading error.
- [ ] Entidade consumer executa idle/walk/attack/death sem animation reset indevido.
- [ ] Animações concorrentes/transições não travam controllers.
- [ ] Sound/particle/event keyframes ocorrem uma vez por ciclo esperado.
- [ ] Render layer dependente de interpolation valida o fix de `partialTick` da 4.9.2.
- [ ] F3+T/resource reload reconstrói assets sem cache stale.
- [ ] Despawn/respawn e troca de dimensão não herdam state visual anterior.
- [ ] Epic Fight + consumer GeckoLib não produz dupla transformação/render.
- [ ] Logs de asset inválido identificam o consumer causal, não são atribuídos automaticamente ao GeckoLib.

## 14. Evidências e limites

- **Modlist física:** confirma JAR `geckolib-neoforge-1.21.1-4.9.2.jar` e versão 4.9.2.
- **Distribuição oficial:** confirma release instalada 4.9.2 NeoForge 1.21.1 e correção de `GeoRenderLayer#preRender`; confirma também 4.9.3 como release NeoForge 1.21.1 mais recente em 16/09/2026, não instalada.
- **Source oficial:** branch `1.21.1` confirma a arquitetura e descrição funcional da linha GeckoLib 4.
- **Limite:** o HEAD do branch 1.21.1 não foi promovido a pin byte-exato da release 4.9.2; detalhes version-sensitive não sustentados pela release foram evitados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
