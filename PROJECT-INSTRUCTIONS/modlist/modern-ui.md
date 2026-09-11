# Modern UI

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bdba2cf71cada14346
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Modern UI `3.13.0.1`, mixins `mixins.modernui-textmc.json` + `mixins.modernui-neoforge.json` e JarJars internos Arc3D compiler `2026.2.0` + CommonMark `0.25.0` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Modern UI
- **Arquivo JAR:** `ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar`
- **Versão 1.21.1:** 3.13.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL, Biblioteca
- **Função:** Framework/UI client-side com Modern Text Engine, fontes avançadas, Unicode/emoji, animações, blur, themes e APIs de render/telas para Minecraft e mods.
- **Dependências:** Cliente NeoForge 1.21.1. O host embute `arc3d-compiler-2026.2.0.jar` e `commonmark-0.25.0.jar` em META-INF/jars; não catalogar como top-level.
- **Sobreposição:** Compartilha UI/text/render surface com outros mods client-side, mas atua também como framework. Não classificar como redundante sem conflito concreto.
- **Compatibilidade/Riscos:** Client UI/text/render. 3.13.0.1 corrige tooltip crash com ImmediatelyFast hud_batching e texto/shadow em Create EditBox. Riscos restantes: mixin overlap, font/resource reload, clipping/fallback, threading/ImageStore e GUI modded.
- **Observações:** Runtime 3.13.0.1. Mixins físicos: `mixins.modernui-textmc.json` e `mixins.modernui-neoforge.json`. JarJars internos Arc3D compiler 2026.2.0 e CommonMark 0.25.0 pertencem ao host.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da build 3.13.0.1 + sources oficiais ModernUI/ModernUI-MC.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/modern-ui | https://github.com/BloCamLimb/ModernUI-MC | https://github.com/BloCamLimb/ModernUI
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Modern UI 3.13.0.1 reconstruído com Modern Text Engine/UI framework, changelog 1.21.1, mixins, JarJars internos, Create/ImmediatelyFast, resource/font lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar`, mod id `modernui`, versão `3.13.0.1`. A build 1.21.1 usa os mixins `mixins.modernui-textmc.json` e `mixins.modernui-neoforge.json` e embute `arc3d-compiler-2026.2.0.jar` e `commonmark-0.25.0.jar` em `META-INF/jars`; esses dois artefatos são bibliotecas internas do host, não mods top-level desta modlist.

## 1. Identidade e papel
- **Mod:** Modern UI.
- **JAR físico:** `ModernUI-NeoForge-1.21.1-3.13.0.1-universal.jar`.
- **Mod id:** `modernui`.
- **Runtime:** `3.13.0.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Upstream:** BloCamLimb / ModernUI + ModernUI-MC.
- **Papel:** framework e overhaul client-side de UI/text rendering, tipografia, animação, blur, emoji, temas e APIs gráficas para telas Minecraft/modded.

## 2. Modern UI vs Modern Text Engine
A integração Minecraft combina o framework Modern UI com uma camada específica de texto/render. O projeto oferece layout de texto mais moderno, Unicode/fallback de fontes, suporte a TrueType/OpenType, anti-aliasing/hinting e APIs de UI. Isso significa que um bug de fonte/texto pode estar no subsistema Modern Text Engine, enquanto um bug de tela/animação/blur pode estar em outra camada do mesmo mod.

## 3. Mixins e rendering surface
A modlist física confirma dois conjuntos de mixins: um focado na integração NeoForge e outro em text rendering. Consequentemente, as superfícies prioritárias são:
- rendering de texto em HUD/telas/tooltips;
- EditBox e campos de texto de mods;
- font fallback/emoji/obfuscated text;
- rendering de telas ModernUI;
- resource reload e fonts;
- interação com batch rendering de outros mods.

## 4. Mudanças relevantes da 3.13.0.1
A publicação 3.13.0.1 para 1.21.1 registra correções/adições incluindo:
- correção de crash de modern tooltip com ImmediatelyFast `hud_batching` no HUD;
- correção de crash em datagen na linha Forge/NeoForge;
- remoção de restrição de modifier para movimento por setas em tooltip;
- API para adicionar resources ModernUI em runtime;
- correção de múltiplas flags de desabilitação de mixin usadas em conjunto;
- abertura de `SimpleScreen`/`MenuScreen` para custom screens;
- `ImageStore` a partir de resources Minecraft e ajustes de threading;
- opção global de Theme;
- opção de font Linear Metrics;
- melhorias de preferências/UI;
- correções no Modern Text Engine para EditBox do Create, hint text, espaços no fim de linha, tooltip positioning e color emoji/obfuscated style;
- APIs adicionais de camera distance/shadow e correções de animação/dirty-area no framework.

## 5. Compatibilidade com Create
A própria linha 3.13.0.1 inclui correção para text shadow em EditBox do Create. Como este pack é fortemente baseado em Create e addons, telas com campos de texto Create são um caso de regressão obrigatório após updates de Modern UI/Create.

Isso é compatibilidade explicitamente tratada pelo upstream; não significa que toda UI de addon Create seja automaticamente coberta.

## 6. Compatibilidade com ImmediatelyFast
A release corrige especificamente crash de modern tooltip quando `hud_batching` do ImmediatelyFast está ativo. Se ImmediatelyFast estiver habilitado no runtime final, o teste deve preservar esse cenário. A existência do fix reduz um problema conhecido, mas qualquer update de um dos lados exige nova verificação.

## 7. Fontes e recursos
Modern UI pode alterar significativamente a apresentação de texto:
- seleção/fallback de fontes;
- métricas lineares;
- Unicode e emoji colorido;
- hinting/anti-aliasing;
- recarga de fonts/resources.

Resource packs com fontes próprias e GUIs de mods são superfícies sensíveis. A authority do conteúdo textual continua sendo o mod/tela que fornece a string; Modern UI controla apresentação/layout.

## 8. Bibliotecas embarcadas
O JAR físico contém:
- `arc3d-compiler-2026.2.0.jar`;
- `commonmark-0.25.0.jar`.

Pelo protocolo do catálogo, essas entradas `META-INF/jars` ficam documentadas **sob Modern UI**. Não devem receber posição própria na ordem física nem ser confundidas com dependências instaladas separadamente.

## 9. Client/server e persistência
A funcionalidade principal é de cliente/UI/rendering. Configurações/preferences do cliente podem persistir, mas esta ficha não atribui formato específico sem arquivo/runtime auditado.

Não há base para tratar Modern UI como authority de gameplay ou estado server-side. Em dedicated server, o objetivo do teste é garantir ausência de classloading indevido no conjunto distribuído, quando aplicável.

## 10. Riscos
1. **Text/render mixin collision** com outros mods que alteram fonts, HUD, tooltip ou EditBox.
2. **Resource reload** pode deixar font/image state stale ou revelar race/threading.
3. **Create GUI regression** em campos de texto e shadows.
4. **ImmediatelyFast batching** deve ser retestado após update apesar do fix 3.13.0.1.
5. **Resource-pack fonts** podem produzir métricas diferentes, clipping ou fallback inesperado.
6. **Embedded libraries** não devem ser removidas/catalogadas isoladamente.
7. **Datagen/client boundary:** a release já corrigiu crash nessa superfície; smoke test de dev/datagen só é relevante para ambiente de desenvolvimento, não gameplay do pack.

## 11. Matriz de testes
- [ ] Cliente inicia com Modern UI 3.13.0.1 e stack gráfico atual.
- [ ] Menus vanilla e telas modded abrem sem crash/clipping.
- [ ] Tooltips no HUD funcionam com ImmediatelyFast/hud batching conforme configuração real.
- [ ] Create EditBox apresenta texto/shadow corretamente.
- [ ] Unicode, acentos, emoji e obfuscated text renderizam sem corrupção.
- [ ] Troca de idioma e resource reload reconstruem fonts sem estado stale.
- [ ] Resource pack principal com fontes customizadas não cria clipping/fallback incorreto.
- [ ] Theme/Linear Metrics, se ativados, persistem e não quebram telas.
- [ ] Alt-tab/resolução/UI scale diferentes não deixam ghosting/dirty regions.
- [ ] Dedicated server do pack inicia sem classloading client indevido causado pela distribuição do conjunto.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: JAR, versão, mod id, dois mixin configs e JarJars internos.
- CurseForge oficial: build Modern UI 3.13.0.1 para NeoForge 1.21.1 e changelog correspondente.
- Sources oficiais: `BloCamLimb/ModernUI` e `BloCamLimb/ModernUI-MC`.
- **Limite:** não foi usado source HEAD como equivalência byte-a-byte do JAR; mudanças específicas atribuídas à 3.13.0.1 vêm da publicação da release.
