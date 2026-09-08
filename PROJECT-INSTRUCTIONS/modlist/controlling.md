# Controlling

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81a5bb42ebfead6e7fb0  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Controlling
- **Arquivo JAR:** `Controlling-neoforge-1.21.1-19.0.5.jar`
- **Versão 1.21.1:** `19.0.5`
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/controlling
- **Função:** Mod client-side que melhora a tela de keybindings com busca, filtros de conflitos e inspeção de teclas disponíveis para administrar controles em instalações com muitos mods.
- **Dependências:** Searchables é dependência confirmada para a linha instalada e está presente no pack. Client-side; não altera o registro server-side de ações dos mods consumidores.
- **Compatibilidade/Riscos:** Baixo risco e focado em UI/input management. Riscos: conflitos com outros mods que substituem a Controls screen, modifier keybinds, UI scale/search cache e Searchables version drift. 19.0.5 corrige double modifier keybinds.
- **Sobreposição:** Pode coexistir com outras utilidades de keybind enquanto não substituírem a mesma Controls screen de forma incompatível. Não altera a semântica da ação registrada pelos mods, apenas sua gestão/apresentação.
- **Observações:** mod id `controlling`; runtime 19.0.5. Recursos: busca de keybinds, filtro de binds em conflito e visualização/gestão de teclas disponíveis; 19.0.5 corrige double modifier keybinds (#214).
- **Procedência:** Modlist física canônica de 07/09/2026 + runtime 19.0.5 + CurseForge oficial Controlling 19.0.5 NeoForge 1.21.1 + documentação oficial do projeto.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Controlling 19.0.5 foi reconfirmado fisicamente e reconstruído como utilitário client-side de gestão de keybinds. A presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — keybind-search/conflict authority, Searchables dependency, client lifecycle, screen interoperability e regressão 19.0.5 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ⌨️ Versão física confirmada: `Controlling-neoforge-1.21.1-19.0.5.jar`, mod id `controlling`, runtime `19.0.5`, NeoForge 1.21.1. É um utilitário **client-side** para administrar keybindings; Searchables é dependência confirmada.

## 1. Papel e authority
Controlling reorganiza a experiência da tela vanilla de controles para instalações com muitos binds. Ele não redefine a semântica da ação de cada mod: o mod que registra o keybind continua authority do que acontece quando a tecla é usada.

## 2. Busca
A UI oferece busca para localizar bindings por nome/contexto. Filtrar a lista é apresentação local; bind oculto pelo filtro continua registrado e ativo.

## 3. Conflitos
Controlling facilita identificar keybinds que disputam a mesma tecla/modifier. Marcar dois binds como conflitantes não decide automaticamente qual ação deve vencer em runtime; isso depende do input handling dos mods envolvidos.

## 4. Teclas disponíveis
A interface ajuda a identificar teclas não utilizadas para remapeamento. Disponibilidade é calculada a partir dos binds conhecidos pelo cliente naquele momento e pode mudar quando mods/configs são alterados.

## 5. Searchables
Searchables fornece infraestrutura de busca usada pela linha atual. A presença de Searchables é dependency contract, não uma feature duplicada de Controlling.

Atualizar/remover Searchables exige smoke-test do Controlling e de outros consumers.

## 6. Release 19.0.5
O changelog oficial registra correção de **double modifier keybinds** (#214). Combinações com Ctrl/Shift/Alt ou múltiplos modifiers são regression gate da build física.

Não atribuir outras mudanças de input à 19.0.5 sem changelog versionado.

## 7. Screen interoperability
Outros mods podem substituir/injetar componentes na Controls screen. Sintomas possíveis: barra de busca ausente, botões sobrepostos, filtros quebrados ou screen override completo.

Conflito deve ser provado pelo cliente real, não por mera coexistência de dois mods de QoL.

## 8. Client-only boundary
Controlling é client-side. O servidor não precisa da sua UI para processar ações registradas pelos mods.

Um bind mostrado como livre/conflitante no cliente não autoriza ação server-side; packets/actions continuam validados pelo mod consumidor.

## 9. Lifecycle
Validar menu principal/world pause → Controls, alteração de keybind, reset, mudança de UI scale/idioma, resource reload quando aplicável, restart do cliente e modlist change.

Search/filter state não deve esconder permanentemente bindings depois de limpar o filtro.

## 10. Riscos
1. Controls screen substituída por outro mod.
2. Double modifier regressar.
3. Searchables version drift.
4. Bind conflitante aparecer como resolvido apenas visualmente.
5. Cache/search stale depois de modlist/keybind change.
6. UI scale quebrar componentes.

## 11. Matriz de testes
1. Abrir Controls com a modlist completa.
2. Buscar bind por nome parcial/exato.
3. Filtrar conflitos e limpar filtro.
4. Identificar teclas disponíveis e remapear.
5. Ctrl/Shift/Alt e double modifiers — regressão 19.0.5.
6. Restart cliente e confirmar persistência do bind pelo Minecraft/mod consumidor.
7. UI scale/idioma diferentes.
8. Coexistência com outros mods que alteram Controls screen.
9. Confirmar dedicated server independente do Controlling.

## 12. Evidência
- modlist física atual: Controlling 19.0.5 NeoForge;
- projeto oficial: search/conflict management para keybindings;
- dependency atual: Searchables;
- changelog 19.0.5: fix de double modifier keybinds (#214).

> 🎛️ Boundary canônico: Controlling **organiza a interface de keybindings**; cada mod consumidor continua decidindo o significado e a validação de sua ação.
