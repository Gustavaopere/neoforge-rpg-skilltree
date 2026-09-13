# Sky Aesthetics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ada5f4ee8fac8a8b85
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sky_aesthetics-neoforge-2.0.13-beta.jar`, mod id `sky_aesthetics`, runtime `2.0.13-beta`, mixin `sky_aesthetics.mixins.json`; Iris 1.8.14-beta.1 presente; Atmosphere não é top-level
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sky Aesthetics 2.0.13-beta e Iris 1.8.14-beta.1 estão presentes; Atmosphere não aparece como entrada top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sky Aesthetics
- **Arquivo JAR:** `sky_aesthetics-neoforge-2.0.13-beta.jar`
- **Versão 1.21.1:** 2.0.13-beta
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual
- **Função:** API client-side para skies configuráveis por resource pack: objetos celestes, sun/moon, stars, shooting stars, constellations, clouds, fog e sky/sunset colors, com condições por dimensão/bioma conforme schema da linha.
- **Dependências:** Client-side. O mod funciona como API/configuração por resource pack; eficácia depende de sky definitions fornecidas por resource pack/mod consumidor. Stack visual físico inclui Iris 1.8.14-beta.1; Atmosphere existe apenas embutido/JarJar, não como entrada top-level.
- **Sobreposição:** Cruza com shaders e outros sky renderers apenas quando há sky definitions ativas. Com shaders, custom objects/sun/moon/sky color podem funcionar, mas stars/clouds são normalmente assumidos pelo shader segundo o upstream.
- **Compatibilidade/Riscos:** Beta client/render API. 2.0.13 remove default skybox/constellation: sem resource pack/mod consumidor o projeto afirma que não muda o jogo. Riscos: múltiplas skies condicionais, shader ownership de stars/clouds, resource reload/cache, black-star regression, shooting-star crash e GPU/render overlap.
- **Observações:** 2.0.13-beta é Beta NeoForge 1.21.1. Delta exato: remove default skybox/default constellation, atualiza Exo Config 0.3.0, corrige black stars e crash de shooting stars. Config/resource packs efetivamente ativos não foram auditados.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sky Aesthetics 2.0.13-beta + documentação oficial do projeto/API.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sky-aesthetics/files/7949641
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sky Aesthetics 2.0.13-beta reconstruído: API/resource-pack sky model, celestial objects, stars/shooting stars/constellations, clouds/fog/colors, shader boundary, exact beta fixes, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em Manter. A Beta 2.0.13 possui fixes concretos de estrelas/shooting stars; risco visual de prerelease permanece registrado.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sky_aesthetics-neoforge-2.0.13-beta.jar`, mod id `sky_aesthetics`, versão `2.0.13-beta`, NeoForge 1.21.1. A build é **Beta client-side**. O próprio projeto define Sky Aesthetics como uma **API de skies por resource pack**: sem uma definição consumidora, instalar o mod sozinho não deve mudar o céu.

## 1. Identidade e maturidade
- **Mod:** Sky Aesthetics.
- **JAR:** `sky_aesthetics-neoforge-2.0.13-beta.jar`.
- **Mod id:** `sky_aesthetics`.
- **Versão:** `2.0.13-beta`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente:** Client.
- **Mixin físico:** `sky_aesthetics.mixins.json`.
- **Decisão vigente:** **Manter**, preservada com risco de prerelease explícito.

## 2. Modelo operacional
Sky Aesthetics não é, primariamente, um pacote visual fechado. Ele expõe um schema/API para **resource packs e outros consumidores configurarem o céu**.

A documentação atual responde explicitamente que, se apenas o mod for instalado e nada mudar, isso é normal: é necessário um mod/resource pack/datapack consumidor conforme a linha suportada.

## 3. Authority e ownership
- **Resource pack/sky definition:** declara quais elementos devem existir e seus parâmetros.
- **Sky Aesthetics:** interpreta essas definições e renderiza/integra os elementos suportados.
- **Shader/Iris:** pode assumir partes do sky pipeline e, quando isso ocorre, torna-se authority visual para stars/clouds específicos.
- **Minecraft/world:** continua authority de dimension/time/weather real; um céu visual não muda clima server-side.

## 4. Objetos no céu
O projeto permite adicionar objetos arbitrários ao sky renderer. A configuração pode controlar posição/rotação/comportamento conforme o schema da linha.

Objetos custom são puramente visuais: não criam entities, collision ou landmarks físicos no world.

## 5. Sol e lua
A API suporta alterar/remover textura do sol e da lua e alterar seus tamanhos. A aparência precisa seguir corretamente day/night cycle e weather fade quando aplicável.

Texture missing ou resource reload incompleto deve degradar sem travar o renderer inteiro.

## 6. Stars
O sistema pode alterar quantidade/aparência/cor e fornecer estrelas móveis. Em configurações compatíveis, stars podem usar escala, cor e movimento próprios.

Com shader ativo, o upstream avisa que **stars geralmente são tratadas pelo shader**, então essas features do mod podem não aparecer. Isso é boundary de ownership, não necessariamente bug.

## 7. Shooting Stars
Sky Aesthetics fornece shooting stars configuráveis. A 2.0.13-beta corrige explicitamente um **crash de shooting stars**, tornando essa mecânica regression gate direto.

Spawn/render de shooting star deve ser client-only e não criar gameplay state persistente.

## 8. Constellations
Constellations fazem parte do feature set da API, mas a 2.0.13-beta **remove o default constellation**. Portanto a build instalada não deve ser tratada como se fornecesse uma constelação própria automaticamente.

Uma constelação visível precisa vir de definição/configuração consumidora.

## 9. Clouds
A linha suporta remoção/configuração de clouds. Shaders normalmente possuem pipeline próprio de clouds, então a visibilidade do ajuste depende do shader em uso.

Não presumir que duas camadas de cloud estejam renderizando simultaneamente apenas porque Sky Aesthetics e Iris estão instalados.

## 10. Fog e sky/sunset colors
A linha suporta custom fog settings e sky/sunset colors. Alterações podem ser condicionadas pelo schema de sky utilizado.

Isso é render local; não altera visibility server-side, mob spawning ou weather mechanics.

## 11. End sky e sky types
A API pode aplicar um sky type semelhante ao End ou outros tipos/configurações visuais a dimensões alvo. Algumas opções de custom sky color com shaders exigem sky type específico segundo o upstream.

Dimension condition precisa ser deterministicamente resolvida e não vazar visualmente para outra world/dimension após disconnect.

## 12. Shader compatibility
O upstream descreve compatibilidade **parcial/majoritariamente funcional** com shaders:
- custom sky objects podem funcionar;
- sun/moon textures podem funcionar;
- sky color pode funcionar sob condições específicas;
- stars/clouds costumam ser controlados pelo shader e, portanto, features equivalentes do mod podem não funcionar.

No pack, Iris `1.8.14-beta.1` está presente. Esse é um regression surface concreto.

## 13. Default skybox removido na 2.0.13
O changelog exato remove o **default skybox**. Isso reforça que a Beta atual espera definitions externas e evita assumir conteúdo visual default que não existe mais.

Se o usuário percebe "nenhuma mudança", primeiro verificar resource pack/config consumidor antes de classificar como falha.

## 14. Default constellation removida na 2.0.13
Também foi removida a **default constellation**. Qualquer constelação vista em runtime deve ser atribuída à definição/resource pack correspondente, não à presença do JAR por si só.

## 15. Exo Config 0.3.0
A build atualiza Exo Config para `0.3.0`. Isso é detalhe de infraestrutura/config da release. Configs antigas podem exigir parsing/migration; não editar manualmente schema sem validar formato da versão instalada.

## 16. Black stars fix
A 2.0.13-beta corrige **black stars**. Regression gate: com sky definition que realmente usa stars e sem shader que substitua esse path, cores devem permanecer conforme definição e não aparecer pretas por falha de pipeline.

## 17. Resource reload e lifecycle
Validar:
- startup sem sky definitions;
- enable/disable de resource pack;
- resource reload;
- world join/leave;
- dimension change;
- day/night transition;
- rain/thunder;
- shader enable/disable;
- reconnect para world diferente.

State visual de uma world/pack não deve vazar para outra sessão.

## 18. Multiple definitions e condições
Skies podem ser condicionais por world/dimension/biome/tag conforme schema da linha. Se mais de uma definição casar com o mesmo contexto, resultado precisa seguir a resolução prevista pela build.

A ficha não presume qual pack/config está ativo porque esses assets locais não foram auditados neste lote.

## 19. Integrações concretas no pack
- **Iris 1.8.14-beta.1:** shader pipeline e ownership parcial de stars/clouds.
- **Distant Horizons:** horizon/fog/sky composition é surface visual relevante, mas não há integração específica atribuída sem evidência.
- **Atmosphere:** aparece apenas como JarJar/embedded em outro JAR, portanto não recebe top-level page nem é tratado como mod independente instalado neste lote.

## 20. Riscos técnicos
1. **No-op esperado:** mod sem resource pack consumidor parece não fazer nada.
2. **Shader ownership:** stars/clouds custom são ocultadas/substituídas.
3. **Multiple sky ambiguity:** duas definitions atingem mesmo contexto.
4. **Resource cache stale:** céu antigo persiste após reload/world change.
5. **Black stars regression:** fix 2.0.13 retorna.
6. **Shooting-star crash:** regression direta da build.
7. **Texture missing:** sun/moon/object asset inválido quebra render parcial.
8. **GPU/render overlap:** objetos custom + shader elevam custo.
9. **Prerelease drift:** Beta schema/config muda em update.

## 21. Matriz de testes
- [ ] Cliente inicia com Sky Aesthetics 2.0.13-beta sem crash.
- [ ] Sem resource pack consumidor, comportamento permanece no-op esperado.
- [ ] Sky definition válida é carregada após resource reload.
- [ ] Custom sky object aparece na dimensão/contexto correto.
- [ ] Sun/moon texture e size obedecem definition.
- [ ] Stars custom não ficam pretas — regression 2.0.13.
- [ ] Shooting stars não crasham — regression 2.0.13.
- [ ] Default skybox/constellation não aparecem indevidamente.
- [ ] Iris shader on/off respeita ownership de stars/clouds sem corrupção persistente.
- [ ] Troca de dimensão não mantém céu da dimensão anterior.
- [ ] Disconnect/reconnect não vaza sky state.
- [ ] Rain/day-night transitions mantêm blending coerente.
- [ ] Performance com custom objects + shader é medida em frame time/GPU memory.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
- Modlist física atual: Sky Aesthetics 2.0.13-beta e stack visual relevante.
- CurseForge oficial: projeto é API configurada por resource pack; features suportadas e compatibilidade parcial com shaders.
- Changelog exato 2.0.13-beta: remove default skybox/constellation, Exo Config 0.3.0, black-stars fix e shooting-stars crash fix.
- **Limite:** resource packs/configs que efetivamente consomem Sky Aesthetics não foram inventariados neste lote. Portanto a presença física é confirmada, mas quais efeitos de céu estão ativos localmente permanece não provado.
