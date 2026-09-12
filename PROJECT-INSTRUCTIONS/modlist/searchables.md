# Searchables

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811da515d777c7d5e818
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Searchables-neoforge-1.21.1-1.0.2.jar`, mod id `searchables`, runtime `1.0.2`, mixins `searchables.neoforge.mixins.json` e `searchables.mixins.json`; `Controlling-neoforge-1.21.1-19.0.5.jar` presente como consumer causal
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Searchables 1.0.2 e Controlling 19.0.5 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Searchables
- **Arquivo JAR:** `Searchables-neoforge-1.21.1-1.0.2.jar`
- **Versão 1.21.1:** 1.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, QoL
- **Função:** Biblioteca de helper methods para busca/filtro por components e autocomplete, incorporada por mods consumidores como Controlling.
- **Dependências:** Consumer causal confirmado e presente: Controlling 19.0.5. NeoForge 1.21.1. Outros consumers só são atribuídos quando comprovados.
- **Sobreposição:** Infraestrutura de search UI; não substitui JEI/EMI nem a semântica de busca/ações dos consumers.
- **Compatibilidade/Riscos:** Library de UI sem gameplay próprio. Riscos: consumer API drift, autocomplete/focus capture, stale suggestions, parser ambiguity, localization/UI scale e mixin conflicts. Remoção isolada quebraria consumer confirmado.
- **Observações:** O changelog exato do arquivo 1.21.1-1.0.2 contém essencialmente localizações nl_be/nl_nl; não foram atribuídas mudanças algorítmicas maiores à build sem evidência.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Searchables 1.0.2 + dossiê Controlling 19.0.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/searchables
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Searchables 1.0.2 reconstruído: component search/filter, autocomplete, Controlling dependency, UI lifecycle, exact changelog, riscos e testes.
- **Histórico da decisão:** 2026-08-27 — classificado como Dependência após confirmação de Controlling 19.0.5 instalado e vinculado explicitamente a Searchables no catálogo atual.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Searchables-neoforge-1.21.1-1.0.2.jar`, mod id `searchables`, versão `1.0.2`, NeoForge 1.21.1. É uma library de busca/filtro/autocomplete sem gameplay independente. No pack atual, **Controlling 19.0.5 é consumer causal confirmado**, portanto Searchables é dependência concreta.

## 1. Identidade e papel
- **Mod:** Searchables.
- **JAR:** `Searchables-neoforge-1.21.1-1.0.2.jar`.
- **Mod id:** `searchables`.
- **Versão:** `1.0.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Mixins físicos:** `searchables.neoforge.mixins.json` e `searchables.mixins.json`.
- **Papel:** infraestrutura reutilizável de search/filter/autocomplete para UIs de outros mods.

## 2. Contrato funcional
A descrição oficial define duas superfícies principais:
- helper methods para pesquisar/filtrar elementos por **components**, com exemplo de query composta como `shape:square color:red`;
- autocomplete embutido.

Searchables não precisa expor uma tela própria para ser funcional: consumers incorporam esses componentes em suas interfaces.

## 3. Consumer causal confirmado
A modlist contém `Controlling-neoforge-1.21.1-19.0.5.jar`. O dossiê reconciliado do Controlling confirma Searchables como dependência da linha instalada.

Consequência: remover Searchables isoladamente pode impedir Controlling de carregar ou quebrar sua search UI. A decisão **Dependência** é baseada nesse consumer concreto, não apenas no rótulo “library”.

## 4. Authority e ownership
- **Searchables:** parser/helpers, filtering e autocomplete UI/shared primitives.
- **Controlling:** keybind search/conflict screen e semântica da interface de controles.
- **Outros consumers:** continuam owners do dataset e da ação resultante.

Searchables não decide o significado do item encontrado nem altera state funcional do consumer.

## 5. Query por components
A biblioteca suporta search terms estruturados por componentes. Consumers podem expor chaves próprias ou combinar filtros.

Um termo inválido/desconhecido deve degradar de forma segura conforme a integração do consumer; não deve corromper o dataset nem executar ação funcional apenas por texto digitado.

## 6. Autocomplete
Autocomplete é uma conveniência de entrada. Sugestões precisam derivar dos termos/components disponíveis no contexto e permanecer sincronizadas com o texto atual.

O histórico posterior do projeto registra correções de autocomplete “stealing the mouse”; isso não é delta da 1.0.2 instalada, mas demonstra que input/focus é uma superfície de regressão relevante.

## 7. Delta exato da 1.0.2 para 1.21.1
O changelog do arquivo físico 1.21.1-1.0.2 contém apenas merge/localizações `nl_be` e `nl_nl` além do merge correspondente. Não atribuir mudanças maiores de algoritmo à 1.0.2 sem evidência.

A maturidade funcional da search library vem da linha do projeto; o delta específico desta build é pequeno.

## 8. Client / Server
O projeto não publica ambiente rígido na ficha geral, mas seu uso confirmado no pack é em UI client-side do Controlling. Search/filter da tela de keybinds é apresentação local.

Não inferir que Searchables seja necessário no dedicated server apenas pela presença do JAR; seguir os requirements do consumer e o empacotamento real.

## 9. Lifecycle de UI
Validar:
- abrir/fechar tela consumer;
- digitar/apagar rapidamente;
- autocomplete com mouse/teclado;
- limpar query;
- troca de idioma;
- UI scale;
- alteração do dataset após config/modlist change;
- restart do cliente.

Search state/cache não deve sobreviver indevidamente a um novo dataset/contexto.

## 10. Riscos técnicos
1. **Consumer API drift:** Controlling espera outra versão de helpers.
2. **Focus/input capture:** autocomplete intercepta mouse/tecla indevidamente.
3. **Stale suggestions:** dataset mudou e sugestões antigas persistem.
4. **Parser ambiguity:** component query é interpretada de forma inesperada.
5. **Localization:** display/query diverge em idioma diferente.
6. **Mixin conflict:** outra library/UI altera o mesmo text-field path.
7. **Removal breakage:** library removida por parecer “sem conteúdo próprio”.

## 11. Matriz de testes
- [ ] Cliente inicia com Searchables 1.0.2 + Controlling 19.0.5.
- [ ] Controls screen abre e search field funciona.
- [ ] Busca simples filtra resultados corretamente.
- [ ] Query por components funciona em consumer que a exponha.
- [ ] Autocomplete aparece sem capturar mouse/input indevidamente.
- [ ] Limpar query restaura dataset completo.
- [ ] Mudança de idioma/UI scale não quebra campo/sugestões.
- [ ] Restart não deixa search cache stale.
- [ ] Remoção controlada de Searchables em ambiente de teste confirma que Controlling é consumer antes de qualquer decisão de remoção real.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física atual: Searchables 1.0.2 e Controlling 19.0.5.
- CurseForge oficial Searchables: helpers de search/filter por components, autocomplete e ausência de conteúdo independente.
- Arquivo oficial 1.0.2: build Release NeoForge 1.21.1 e changelog de localizações.
- Dossiê Controlling: Searchables é dependência confirmada.
- **Limite:** outros consumers potenciais não foram atribuídos sem relação causal confirmada.
