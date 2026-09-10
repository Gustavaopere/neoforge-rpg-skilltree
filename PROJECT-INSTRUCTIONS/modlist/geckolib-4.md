# GeckoLib 4 — 4.9.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db818bbcb1c56f43b32dea  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** GeckoLib 4
- **Arquivo JAR:** `geckolib-neoforge-1.21.1-4.9.2.jar`
- **Versão 1.21.1:** `4.9.2`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/bernie-g/geckolib/tree/1.21.1
- **Função:** Engine/API de animação 3D por keyframes para entidades, itens, blocos e armaduras, com controllers, animações concorrentes, easings e keyframes de som/partículas/eventos.
- **Dependências:** NeoForge 1.21.1; runtime/API consumido por diversos mods. O branch oficial 1.21.1 confirma arquitetura da linha, enquanto a release física 4.9.2 é pinada pela distribuição oficial.
- **Compatibilidade/Riscos:** Riscos: asset/bone/animation-name drift em consumers, controllers conflitantes, keyframe side effects duplicados, stale render cache, client classloading em dedicated server e ABI drift. 4.9.2 corrige partialTick passado a GeoRenderLayer#preRender.
- **Sobreposição:** Runtime/API de animação de mods; não substitui resource-pack/model frameworks como EMF/ETF/Fresh Animations/Fusion. Conflito só é assumido quando duas camadas alteram concretamente o mesmo renderer/model.
- **Observações:** JAR físico `geckolib-neoforge-1.21.1-4.9.2.jar`. Não é equivalente a EMF/Fresh Animations/Fusion. Born in Chaos é consumer confirmado e o JAR físico do Epic Fight expõe compat GeckoLib.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial GeckoLib 4.9.2 para NeoForge 1.21.1 + source oficial bernie-g/geckolib branch 1.21.1 usado com limite de source-line drift.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — GeckoLib 4.9.2 release-pinned; engine/controllers/keyframes, assets, render lifecycle, client/server, consumers, riscos e matriz de testes catalogados; source-line drift registrado.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `geckolib-neoforge-1.21.1-4.9.2.jar`, mod id `geckolib`, versão `4.9.2`. A release 4.9.2 é confirmada pela distribuição oficial para NeoForge 1.21.1; o branch oficial `bernie-g/geckolib:1.21.1` é usado para arquitetura/API da linha, sem presumir que seu HEAD corresponda byte a byte ao JAR 4.9.2.

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

## 8. Client / server
A distribuição é marcada Client & Server porque consumers podem referenciar tipos/API em common/server code. Isso não significa que o servidor renderize modelos. Renderers, textures e interpolation pertencem ao cliente; state de gameplay e sincronização pertencem ao consumer/servidor.

## 9. Integrações concretas no pack
Há consumers reais no pack que declaram ou expõem compatibilidade com GeckoLib. Exemplos confirmados na auditoria incluem **Born in Chaos** como dependente e **Epic Fight** com mixin de compatibilidade GeckoLib no JAR físico. Isso não é uma lista exaustiva; cada consumer deve manter sua própria ficha e versão.

## 10. Sobreposição com o stack visual
GeckoLib não substitui Fresh Animations/EMF/ETF/Fusion. GeckoLib é runtime/API para animações implementadas por mods; EMF/ETF e resource packs alteram modelos/texturas em outra camada. Conflito surge apenas quando mais de uma camada tenta transformar/renderizar o mesmo objeto.

## 11. Riscos técnicos
- assets/modelos/animações inválidos em consumers;
- bone ou animation name drift após update;
- controller duplicado ou state machine conflitante;
- keyframe de evento produzindo side effect duplicado;
- render layer usando interpolation/partial tick incorreto;
- stale animation cache após resource reload ou entity replacement;
- consumer carregando classes exclusivamente client no dedicated server;
- incompatibilidade binária quando consumer foi compilado contra API diferente;
- conflito com outro renderer/model layer no mesmo tipo de entidade/item.

## 12. Matriz de testes obrigatória
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

## 13. Evidências e limites
- **Modlist física:** confirma JAR `geckolib-neoforge-1.21.1-4.9.2.jar` e versão 4.9.2.
- **Distribuição oficial:** confirma release 4.9.2 NeoForge 1.21.1 e correção de `GeoRenderLayer#preRender`.
- **Source oficial:** branch `1.21.1` confirma a arquitetura e descrição funcional da linha GeckoLib 4.
- **Limite:** o HEAD do branch 1.21.1 não foi promovido a pin byte-exato da release 4.9.2; detalhes version-sensitive não sustentados pela release foram evitados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
