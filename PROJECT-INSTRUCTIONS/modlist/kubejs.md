# KubeJS — 2101.7.2-build.374

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81a1b76aca006b216684  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** KubeJS
- **Arquivo JAR:** `kubejs-neoforge-2101.7.2-build.374.jar`
- **Versão 1.21.1:** `2101.7.2-build.374`
- **Categoria:** Automação; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs
- **Função:** Framework de scripting NeoForge para recipes, conteúdo custom e eventos em JavaScript, com domains startup/server/client e ecossistema amplo de addons.
- **Dependências:** Required content oficial da build.374: Rhino + Better Advanced Tooltips. Rhino 2101.2.8-build.91 está fisicamente presente. KubeJS 7.x não depende mais de Architectury API; Architectury 13.0.11 no pack é independente desta relação.
- **Compatibilidade/Riscos:** Infraestrutura crítica com muitos consumers. Riscos: script exceptions, duplicate handlers após reload, registry migration, client/server leakage e addon API drift. Em 09/09/2026 existe build.377, mas o runtime físico permanece build.374 até atualização explícita.
- **Sobreposição:** Pode alterar os mesmos dados/sistemas que datapacks e mods, conforme scripts. Não substitui providers; é glue/customization layer.
- **Observações:** JAR físico `kubejs-neoforge-2101.7.2-build.374.jar`, mod id `kubejs`, runtime 2101.7.2-build.374. Build.377 já existe externamente em 09/09/2026, mas não substitui a authority física.
- **Procedência:** modlist.txt física atual de 08/09/2026 + Modrinth/CurseForge oficiais KubeJS build.374 + KubeJS Wiki 1.21/7.x; disponibilidade externa rechecada em 09/09/2026.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; script phases, recipes/content/events, KubeJS 7.x lifecycle, dependencies, reload/persistence e risks catalogados para build.374.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🧰 **ESCOPO CANÔNICO.** Runtime físico: `kubejs-neoforge-2101.7.2-build.374.jar`, mod id `kubejs`, versão `2101.7.2-build.374`. KubeJS é a infraestrutura de scripting do modpack: altera recipes/dados, registra conteúdo custom e reage a eventos em JavaScript. O comportamento material depende dos scripts presentes.

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

## 6. Dependências físicas
A release 2101.7.2-build.374 declara Rhino e Better Advanced Tooltips como required content. Rhino 2101.2.8-build.91 está fisicamente presente. Vários addons KubeJS do pack dependem deste core, tornando-o load-bearing.

## 7. Versão física vs atualização disponível
Em 09/09/2026 já existe build 2101.7.2-build.377 publicada. A modlist física continua em build.374 e permanece authority. Não atualizar o catálogo/runtime por disponibilidade externa sem mudança explícita do JAR e regressão dos addons/scripts.

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
- [ ] Addons KubeJS atuais resolvem métodos/classes da build.374.
- [ ] Mundo existente mantém custom registry IDs após restart.
- [ ] Mudança futura para build.377 só ocorre após regressão do stack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limite
Modrinth/CurseForge confirmam build.374 Release para NeoForge 1.21–1.21.1 e dependencies Rhino/Better Advanced Tooltips. KubeJS Wiki confirma phases, eventos e mudanças 7.0. Scripts reais da instância ainda precisam de auditoria própria para afirmar quais customizações estão ativas.
