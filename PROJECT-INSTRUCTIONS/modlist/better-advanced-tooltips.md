# Better Advanced Tooltips

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db814c9a3ef368d8c594d8  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Better Advanced Tooltips
- **Arquivo JAR:** `better-advanced-tooltips-2101.1.0-build.5.jar`
- **Versão 1.21.1:** `2101.1.0-build.5`
- **Categoria:** QoL; Visual
- **Decisão:** Opcional
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/better-advanced-tooltips
- **Função:** Amplia Advanced Tooltips (F3+H) com tags, data components e outras informações técnicas de ItemStack para diagnóstico.
- **Dependências:** NeoForge/Minecraft 1.21.1; funcionalidade principal de tooltip é apresentação client-side.
- **Compatibilidade/Riscos:** Risco principal é composição visual: linhas duplicadas, ordem de callbacks, tooltip excessivo e cache stale após reload. Não altera atributos ou state de item.
- **Sobreposição:** Coexiste com Simply Tooltips e Tooltip Overhaul porque o foco é informação avançada/técnica; pode haver poluição visual, mas não foi encontrado conflito estrutural.
- **Observações:** Build 2101.1.0-build.5. Tags/components exibidos continuam pertencendo ao provider/Minecraft; o mod é viewer de diagnóstico.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge oficial Better Advanced Tooltips build.5.
- **Histórico da decisão:** Decisão formal já registrada como Opcional em 06/09/2026. O registro anterior não continha justificativa textual; em 08/09/2026 a auditoria preservou a decisão sem inventar motivo e confirmou o papel exclusivamente QoL/diagnóstico da build 2101.1.0-build.5.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — F3+H/tags/components, data freshness, tooltip composition e side safety catalogados.
- **Data da última decisão:** 2026-09-06.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `better-advanced-tooltips-2101.1.0-build.5.jar`, mod id `betteradvancedtooltips`, runtime `2101.1.0-build.5`, NeoForge 1.21.1. O projeto amplia o modo de tooltips avançados `F3+H` com informações técnicas de itens.

## 1. Papel e autoridade
Better Advanced Tooltips é **QoL/diagnóstico de interface**. Sua finalidade oficial é acrescentar informações como **tags, components e outros dados técnicos** aos tooltips de itens quando o modo avançado do Minecraft está habilitado.

Ele não cria atributos, não altera o item e não deve virar authority de qualquer dado mostrado: o tooltip apenas apresenta state fornecido pelo stack/registries/components reais.

## 2. Gate F3+H
A funcionalidade é associada ao modo Advanced Tooltips (`F3+H`). Consequências:
- com advanced tooltips desativado, não se deve presumir que o dado inexiste;
- habilitar/desabilitar é apresentação local;
- scripts/mods próprios nunca devem usar visibilidade do tooltip como condição de gameplay.

## 3. Tags
A exibição de tags é útil para debugging de recipes, compat, item groups e integração KubeJS/datapack. Tags são data-driven e podem mudar em reload. O tooltip deve consultar o estado atual e não manter lista stale após datapack/resource lifecycle pertinente.

## 4. Components
A exibição de components ajuda a inspecionar o state moderno de `ItemStack`. O mod não é owner desses components. Qualquer ferramenta própria deve editar components via APIs do provider/Minecraft e não tentar alterar texto renderizado pelo tooltip.

## 5. Composição com outros tooltip mods
O pack possui vários mods de UI/tooltips. A sobreposição real é de **renderização e densidade de informação**, não de cálculo de gameplay. Riscos:
- linhas duplicadas;
- ordem diferente de tooltip callbacks;
- tooltip excessivamente alto/largo;
- componentes técnicos repetidos por duas ferramentas.

Isso deve ser resolvido por config/UI, não removendo dados do item.

## 6. Client/server
A função principal é visual. A página oficial marca disponibilidade Client & Server, mas o comportamento catalogado é de tooltip. Mesmo quando o JAR pode carregar em servidor, rendering é client-side. Não introduzir dependência de classes de GUI em lógica dedicada.

## 7. Lifecycle
Testar:
- toggle F3+H em runtime;
- troca de item/stack;
- datapack/resource reload;
- login em servidores com tags/components diferentes;
- alteração dinâmica de component;
- coexistência com outros tooltip renderers.

Nenhum cache visual deve sobreviver quando a fonte de dados mudou.

## 8. Riscos
1. Poluição/duplicação visual.
2. Tooltip stale após reload.
3. Formatação de component/tag incompatível com outro renderer.
4. Classloading de render no dedicated server.
5. Usuário confundir valor exibido com valor calculado pelo mod de tooltip.

## 9. Matriz de testes
1. F3+H off/on.
2. Itens com muitas tags/components.
3. Datapack reload e mudança de tags.
4. Component alterado em runtime refletido no tooltip.
5. Coexistência com Simply Tooltips/Tooltip Overhaul ou outros presentes.
6. Dedicated server smoke sem dependência visual indevida.

## 10. Evidência
- modlist física atual: build.5;
- CurseForge oficial Better Advanced Tooltips para NeoForge 1.21.1;
- descrição oficial: tags, components e outras informações em Advanced Tooltips.

> 🔎 Exaustividade proporcional ao escopo: a ficha cobre as superfícies observáveis de diagnóstico, data freshness, composição de tooltip e side safety sem inventar gameplay.
