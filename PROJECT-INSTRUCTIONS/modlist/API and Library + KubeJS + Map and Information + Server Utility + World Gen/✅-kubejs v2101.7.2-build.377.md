# KubeJS

## Propriedades do registro

- **Mod:** KubeJS
- **Arquivo JAR:** kubejs-neoforge-2101.7.2-build.377.jar
- **Versão 1.21.1:** 2101.7.2-build.377
- **Categoria:** Automação, QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs
- **Função:** Framework de scripting NeoForge para recipes, conteúdo custom e eventos em JavaScript, com domains startup/server/client e ecossistema amplo de addons.
- **Dependências:** Rhino 2101.2.8-build.91 está fisicamente presente. Better Advanced Tooltips está embarcado no JAR build.377; os demais componentes JarJar internos estão documentados no corpo. KubeJS 7.x não exige Architectury API.
- **Compatibilidade/Riscos:** Infraestrutura crítica com muitos consumers. Riscos: script exceptions, duplicate handlers após reload, registry migration, client/server leakage e addon API drift. Runtime físico atual: build.377; qualquer update posterior continua exigindo regressão do stack.
- **Sobreposição:** Pode alterar os mesmos dados/sistemas que datapacks e mods, conforme scripts. Não substitui providers; é glue/customization layer.
- **Observações:** Runtime físico atual é `2101.7.2-build.377`. O JAR build.377 embarca Better Advanced Tooltips 2101.1.0-build.1, Animated GIF Lib 1.7 e Tiny Java Server 1.0.0-build.33 via JarJar.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial KubeJS build.377 NeoForge 1.21.1 + inventário JarJar físico do host + documentação KubeJS 7.x já auditada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJS 2101.7.2-build.377/JAR físico reconfirmado; o antigo gap build.374→build.377 foi encerrado pela modlist atual. Corpo técnico e boundaries de scripting preservados.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #363: JAR `kubejs-neoforge-2101.7.2-build.377.jar`, mod id `kubejs`, runtime `2101.7.2-build.377`, SHA-1 `150c5d6efc09b969ac350ea205128dff832e0850`.

<callout icon="🧰" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `kubejs-neoforge-2101.7.2-build.377.jar`, mod id `kubejs`, versão `2101.7.2-build.377`. KubeJS é a infraestrutura de scripting do modpack: altera recipes/dados, registra conteúdo custom e reage a eventos em JavaScript. O comportamento material depende dos scripts presentes.
</callout>
## 1. Script domains
KubeJS 7 separa os principais lifecycles:
- `startup_scripts/`: registry e mudanças que exigem startup; executa no início;
- `server_scripts/`: recipes e eventos de gameplay, recarregáveis com `/reload`;
- `client_scripts/`: UI/render/client behavior.
Misturar phases é uma das principais fontes de registry errors e side bugs.
## 2. Recipes e data
Server scripts podem adicionar/remover/modificar recipes. Em 1.21, recipe types podem ser descritos por schemas JSON e integrações de mods são ampliadas por addons. KubeJS não se torna owner da máquina externa: ele modifica os dados consumidos pelo provider.
## 3. Conteúdo custom
Startup scripts podem registrar items, blocks, fluids e outros tipos expostos. Registry ID é contrato persistente; remover/renomear conteúdo usado em mundo existente exige migration ou pode produzir missing entries/data loss.
## 4. Eventos
Eventos Startup, Server e Client têm side/lifecycle próprios. Server events são a superfície correta para mutações autoritativas de mundo/player. Client events servem a apresentação/input e não devem conceder itens, mana ou progresso sem validação server-side.
## 5. Linha 7.x / Minecraft 1.21
A documentação oficial 7.0 registra mudanças estruturais: suporte principal 1.21 via NeoForge, remoção da dependência em Architectury API, configs em JSON, native events reloadable e mudanças de APIs para addons. Portanto Architectury físico no pack **não é hard dependency de KubeJS 7.2**.
## 6. Dependências físicas e JarJar
O runtime físico 2101.7.2-build.377 mantém Rhino 2101.2.8-build.91 como entrada top-level e contém, em `META-INF/jarjar/`, `better-advanced-tooltips-2101.1.0-build.1.jar`, `animated-gif-lib-for-java-animated-gif-lib-1.7.jar` e `tiny-java-server-1.0.0-build.33.jar`. Pelo protocolo do catálogo, esses três componentes são dependências internas do host e não recebem páginas top-level independentes. Vários addons KubeJS do pack dependem deste core, tornando-o load-bearing.
## 7. Versão física vs atualização disponível
A modlist física atual contém `kubejs-neoforge-2101.7.2-build.377.jar`, portanto o antigo gap build.374 → build.377 está encerrado. Qualquer atualização posterior continua sendo mudança operacional e exige regressão dos addons/scripts.
## 8. Reload e persistência
`/reload` recarrega server scripts/data; handlers não podem acumular em cada reload. Conteúdo de startup não deve ser recriado por reload. Erros precisam ser observáveis no log e não deixar metade das regras novas e metade das antigas.
## 9. Riscos
1. Script exception interrompe pipeline de recipes/eventos.
2. Duplicate listeners após reload.
3. Registry ID removido/renomeado quebra mundo.
4. Client code vaza para dedicated server.
5. Addon compilado contra API KubeJS diferente.
6. Scripts alteram o mesmo sistema em ordem conflitante.
7. Update core quebra dezenas de consumers simultaneamente.
## 10. Boundary para projects próprios
KubeJS pode servir como glue/configuration layer, mas state canônico de RPG Skill Tree, Black Arcana e providers externos deve continuar nos sistemas proprietários. Scripts devem reagir/transformar dados sem manter cópia paralela desnecessária.
## 11. Matriz de testes
- [ ] Dedicated server cold boot sem script errors.
- [ ] Startup registrations são únicas e estáveis.
- [ ] `/reload` duas vezes é idempotente.
- [ ] Server event mutations ocorrem exatamente uma vez.
- [ ] Client scripts não carregam em server path.
- [ ] Addons KubeJS atuais resolvem métodos/classes da build.377.
- [ ] Mundo existente mantém custom registry IDs após restart.
- [ ] Qualquer atualização posterior da build.377 só ocorre após regressão do stack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limite
CurseForge oficial confirma build.377 para NeoForge 1.21.1. A modlist física confirma Rhino 2101.2.8-build.91 top-level e os JarJars Better Advanced Tooltips 2101.1.0-build.1, Animated GIF Lib 1.7 e Tiny Java Server 1.0.0-build.33 dentro do host. KubeJS Wiki confirma phases, eventos e mudanças 7.0. Scripts reais da instância ainda precisam de auditoria própria para afirmar quais customizações estão ativas.

## 13. Auditoria incremental — 20/11/2026: publicação 2101.7.2-build.381 e build de desenvolvimento .382

**Autoridade física preservada:** `kubejs-neoforge-2101.7.2-build.377.jar`, posição top-level #363, SHA-1 físico histórico documentado acima. Nenhum JAR ou script do pack foi substituído nesta auditoria.

**Catálogo oficial para NeoForge / Minecraft 1.21.1:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs/files/all?page=1&pageSize=20&version=1.21.1) lista **build.377** (09/09/2026) seguida de **build.381** (01/11/2026), sem publicação intermediária build.378, build.379 ou build.380 nesse catálogo. O número do build, por si, não constitui evidência de release intermediária. [Arquivo .381](https://www.curseforge.com/minecraft/mc-mods/kubejs/files/9396059).

**Delta source 2101 (após 09/09/2026 e até 01/11/2026):** [comparação oficial dos quatro commits](https://github.com/kube-mods/kubejs/compare/60a6c8e53c32...2461c98c80ed). Como o changelog da página do JAR aponta para uma URL genérica, os commits foram verificados individualmente; a comparação de código delimita a linha upstream, mas não substitui prova binária do conteúdo exato dos JARs:

1. **18/09 — 9e93712:** criação de links de editor via `Path.toUri()`, corrigindo caminhos/URLs que antes podiam falhar com caracteres especiais ou espaços; impacto de UX em abrir fontes/erros.
2. **18/09 — 1b4e9b8:** comando client-side `/kubejs logs startup|server|client` para localizar e abrir logs de scripts. Não concede ações autoritativas de servidor.
3. **01/11 — 3ced9e1:** `FoodBuilder` passa a converter o valor final de saturation em modifier ao copiar `FoodProperties`; corrige comportamento de itens alimentícios customizados. Ícone/textura do botão do recipe book usa sprite do próprio `RecipeBookComponent`, reduzindo erro visual.
4. **01/11 — 2461c98:** correção da [issue #1155](https://github.com/kube-mods/kubejs/issues/1155), sobre overlay customizado de fluidos gerando textura ausente; fonte afetada `FluidTypeBuilder.java`. Exige teste de assets personalizados e resource reload. A comparação apresenta alterações amplas de fim-de-linha/normalização, portanto não se deve inferir outra API nova sem comparação binária.

**Desenvolvimento posterior:** o [catálogo oficial de dev builds](https://kubejs.com/downloads/dev-builds) anuncia **2101.7.2-build.382** como build de desenvolvimento, separada da última release estável **build.381**. O commit de 15/11, [#1207](https://github.com/kube-mods/kubejs/pull/1207), move helpers de codec para evitar inicialização prematura de classes como `DyeColor` antes de registries/datafixers, corrigindo [issue #1205](https://github.com/kube-mods/kubejs/issues/1205). A correspondência exata entre esse commit e a distribuição binária .382 requer prova do artefato; não classificá-la como release estável nem como instalada.

**Relevância documental:** novo comando de diagnóstico, correção de cópia de saturação de alimentos, overlays de fluidos e paths de editor; a build de desenvolvimento aponta correção adicional de inicialização de codec. Preservadas as seções de authority de scripts, JarJar, lifecycle, persistence, server/client e integrações físicas.

**Status:** auditoria documental da sequência de publicações oficiais pós-build.377 **concluída** até a release estável build.381 e build de desenvolvimento .382 identificada com suas limitações. **Instalação da atualização NÃO confirmada**; nenhuma promoção de versão no filename nem renomeação. Gates físicos pendentes: regressão dos scripts e addons KubeJS, cold boot servidor e cliente, `/reload` repetido, custom food saturation, textures/overlays de fluidos, receita UI, comando client-side de logs, arquivos de editor com espaços, registry/datapacks e compatibilidade do Rhino real. A certificação aqui é exclusivamente documental.
