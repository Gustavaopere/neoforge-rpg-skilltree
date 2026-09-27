# SableMassView

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#495**: JAR `sablemassview-1.0.0.jar`, mod id `sablemassview`, runtime `1.0.0`, SHA-1 `e568ce1389bcb259483766cf35542dbc8cefb2b1`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** SableMassView
- **Arquivo JAR:** `sablemassview-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL, Compat
- **Função:** Addon client-side que mostra a massa física atribuída pelo Sable ao bloco/state nos advanced tooltips (F3+H), para inspeção e debug de contraptions.
- **Dependências:** Sable 2.0.5 é o provider físico. Sable: Physics Compat 1.3.0 é integração complementar de dados, não hard dependency do MassView.
- **Sobreposição:** Não modifica massa; apenas expõe informação do provider. Complementa Physics Compat e datapacks físicos.
- **Compatibilidade/Riscos:** Client-only e informacional. Riscos: massa stale após reload, wrong block-state lookup, Sable API drift, conflito de tooltip e interpretação incorreta do valor de bloco como massa total da estrutura.
- **Observações:** Release 1.0.0 é a única build pública localizada para NeoForge 1.21.1; o projeto não publica changelog adicional além da feature central.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sable: Mass view 1.0.0 + dossiê Sable 2.0.5 para authority de mass properties.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-sable-mass-view
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — SableMassView 1.0.0/JAR físico reconfirmado; 1.0.0 permanece a única release NeoForge 1.21.1 localizada. Client-only mass tooltip e boundary informacional preservados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sablemassview-1.0.0.jar`, mod id `sablemassview`, versão `1.0.0`, NeoForge 1.21.1. É um addon **client-only e informacional**: mostra nos advanced tooltips a massa que o physics engine Sable atribui ao bloco.

## 1. Identidade e papel
- **Mod:** Sable: Mass view / SableMassView.
- **JAR:** `sablemassview-1.0.0.jar`.
- **Mod id:** `sablemassview`.
- **Versão:** `1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Mixin físico:** `sablemassview.mixins.json`.
- **Papel:** inspeção da massa física de blocos Sable em tooltips avançados.

## 2. Interface de usuário
A documentação oficial define uma única feature central: com advanced tooltips habilitados (`F3+H`), o tooltip do bloco exibe sua massa no sistema físico do Sable.

Não há evidência oficial de menu próprio, alteração de recipes, commands ou gameplay adicional na 1.0.0.

## 3. Authority da massa
Sable continua authority dos physics properties, inclusive `sable:mass` e overrides data-driven por block state/datapack. SableMassView deve **ler/apresentar** esse valor; não manter tabela independente nem recalcular uma segunda massa.

Isso é importante porque Physics Compat e datapacks podem modificar propriedades do bloco sem alteração do addon visual.

## 4. Relação com Sable 2.0.5
O pack usa Sable 2.0.5, cujo sistema de massa pode variar por block state e dados carregados. O valor apresentado pelo tooltip precisa acompanhar o valor efetivo atual do provider.

Se o tooltip divergir da física observada, a triagem deve separar: data física incorreta, leitura client desatualizada ou cache/render do addon.

## 5. Relação com Sable: Physics Compat
O pack também contém `sablephysicscompat-1.3.0.jar`, que adiciona propriedades/tags físicas a blocos modded. MassView não duplica esse trabalho: Physics Compat fornece/ajusta dados; MassView torna a massa observável ao usuário.

Essa combinação é útil para validar visualmente se um bloco recebeu o perfil de massa esperado antes de montar contraptions.

## 6. Client-only boundary
Nenhuma informação mostrada no tooltip deve alterar body, center of mass, inertia ou resultado do solver. Dedicated server não deve depender de MassView para calcular física.

Em multiplayer, dois clientes podem decidir exibir ou não advanced tooltips sem mudar o resultado físico do servidor.

## 7. Reload e data-driven properties
Como a massa Sable pode vir de datapacks/tags/overrides, validar o comportamento depois de reload de dados quando suportado pelo provider e depois de restart.

O tooltip não deve conservar indefinidamente um valor anterior quando o block-state/property efetivo mudou.

## 8. Usos operacionais no pack
- inspeção de blocos pesados/leves durante design de aeronaves/sublevels;
- verificação rápida de compats de massa para blocos modded;
- auxílio a debug quando center-of-mass/comportamento físico parece inesperado;
- comparação entre estados de um mesmo bloco quando propriedades variam por state.

Esses usos são diagnósticos; o addon não substitui medição runtime do body inteiro.

## 9. Riscos técnicos
1. **Valor stale:** tooltip não acompanha reload/change de propriedade.
2. **Wrong block state:** leitura usa default state em vez do state mirado.
3. **Client/provider drift:** API de propriedades Sable muda.
4. **Tooltip conflict:** outro mod altera advanced tooltip no mesmo path.
5. **False authority:** usuário interpreta o texto como massa total da contraption, quando é massa do bloco/state.
6. **Localization/format:** valor correto é exibido de forma ambígua.

## 10. Matriz de testes
- [ ] Cliente inicia com Sable 2.0.5 + MassView 1.0.0.
- [ ] Sem `F3+H`, interface permanece conforme comportamento esperado.
- [ ] Com `F3+H`, bloco vanilla mostra massa Sable.
- [ ] Bloco modded coberto por Physics Compat mostra massa correspondente.
- [ ] Dois block states com propriedades distintas mostram valores distintos quando aplicável.
- [ ] Reload/restart após mudança de datapack não deixa valor stale.
- [ ] Multiplayer: cliente com/sem tooltip não altera física do servidor.
- [ ] Tooltip coexistente com outros mods não duplica ou quebra linhas.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
- Modlist física atual: MassView 1.0.0, Sable 2.0.5 e Physics Compat 1.3.0.
- CurseForge oficial: Release 1.0.0, client-only e feature de massa nos advanced tooltips F3+H.
- **Limite:** o projeto não publica changelog adicional para 1.0.0; nenhum comportamento além do recurso documentado foi inventado.
