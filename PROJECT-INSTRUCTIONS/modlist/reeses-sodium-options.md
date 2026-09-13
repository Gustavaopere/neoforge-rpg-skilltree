# Reese's Sodium Options

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81428b9de368a35d8720
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`, mod id `reeses_sodium_options`, runtime `2.2.3+mc1.21.1`, mixin `reeses-sodium-options.mixins.json`; Sodium 0.8.13+mc1.21.1 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Reese's Sodium Options 2.2.3 e Sodium 0.8.13 estão presentes. A menção da página a Sodium Options API 1.0.10 não foi confirmada por busca literal no snapshot físico acessível nesta execução; o corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reese's Sodium Options
- **Arquivo JAR:** `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`
- **Versão 1.21.1:** 2.2.3+mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Visual, Performance
- **Função:** Frontend client-side alternativo para as opções do Sodium, com busca, navegação por tabs/grupos e controles de UI; não implementa o renderer nem otimização de FPS própria.
- **Dependências:** Sodium 0.8.13+mc1.21.1 é o provider-base presente. Sodium Options API 1.0.10 também está no pack, sem hook específico atribuído sem source pin.
- **Sobreposição:** Sobrepõe somente a interface de configuração do Sodium. Sodium continua authority de settings/render/performance; Reese's organiza a tela.
- **Compatibilidade/Riscos:** Client-only. Riscos: Sodium API drift, UI/provider mismatch, search/scroll state incorreto, conflito com outra screen injector e semântica stale de reset/undo. Não deve ser tratado como mod de performance independente.
- **Observações:** Build 2.2.3 para NeoForge 1.21.1. Delta confirmado inclui melhorias de search/highlight/scroll, navegação Enter/Shift+Enter, limite de resultados, Always Show Action Buttons e fix relacionado a Controlify.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + metadata do JAR + publicação/changelog oficial Reese's Sodium Options 2.2.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reeses-sodium-options
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Reese's Sodium Options 2.2.3 reconstruído: UI/search/tabs, delta exato 2.2.3, ownership Sodium, client lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`, mod id `reeses_sodium_options`, versão `2.2.3+mc1.21.1`. É um mod **client-side de interface** para a tela de opções do Sodium; não implementa o renderer nem adiciona otimizações gráficas próprias.

## 1. Identidade e papel
- **Mod:** Reese's Sodium Options.
- **JAR:** `reeses-sodium-options-neoforge-2.2.3+mc1.21.1.jar`.
- **Mod id:** `reeses_sodium_options`.
- **Versão instalada:** `2.2.3+mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente publicado:** Client.
- **Provider-base presente:** Sodium 0.8.13+mc1.21.1.

## 2. Papel no modpack
Substitui/reorganiza o frontend da tela de opções do Sodium para tornar um conjunto grande de opções mais navegável. Seu domínio é **UI/configuração gráfica**, não performance de renderização.

Remover Reese's não remove Sodium; apenas muda a forma de expor e navegar suas opções.

## 3. Autoridade e ownership
- **Sodium:** autoridade dos settings e do comportamento do renderer/performance.
- **Reese's Sodium Options:** autoridade da organização, pesquisa, navegação e apresentação da tela alternativa.
- **Minecraft/NeoForge:** continuam responsáveis por persistência e lifecycle base de options conforme as APIs utilizadas.

Uma opção exibida por Reese's não pertence funcionalmente ao Reese's só por aparecer em sua tela.

## 4. Superfície de UI confirmada
A documentação da linha 2.2.3 descreve:
- search bar;
- lista vertical rolável de tabs/pages;
- grupos de tabs de mods recolhíveis;
- grupos de opções recolhíveis;
- headers customizáveis com ícones/version labels.

A release 2.2.3 aprimora especificamente a experiência de busca e navegação em resultados.

## 5. Delta da versão 2.2.3
O changelog oficial da build instalada documenta:
- melhor highlight/selection border/tooltips nos resultados;
- limite configurável de resultados;
- Enter para próximo resultado e Shift+Enter para anterior;
- correção de resultado selecionado que não rolava para a área visível em páginas longas;
- opção **Always Show Action Buttons**;
- botões reset/undo visíveis mesmo quando desabilitados;
- correção de posição de cycling de tabs/headers relacionada ao Controlify.

Esses itens são regression gates objetivos da versão instalada.

## 6. Configuração e persistência
Reese's atua sobre opções gráficas existentes e sobre preferências de sua própria UI. O risco operacional principal é a tela apresentar um valor que não seja salvo/aplicado pelo provider correto.

Não foi inferido o arquivo/classe exato de configuração sem source pin da 2.2.3.

## 7. Client / Server
É **client-side**. Não deve registrar gameplay, alterar lógica de servidor ou ser requisito funcional de um dedicated server.

Um servidor sem Reese's continua compatível com clientes que o usam, desde que as dependências gráficas client-side estejam coerentes.

## 8. Lifecycle
Validar:
- abertura/fechamento repetido da options screen;
- alteração, Apply/Done e reabertura;
- restart completo do cliente;
- troca de resource pack/shader quando opções relacionadas são expostas;
- mudança de resolução/gui scale;
- entrada/saída de mundo sem perda de configuração;
- atualização de Sodium sem orphan tabs/options.

## 9. Integrações concretas no pack
- **Sodium 0.8.13:** provider-base das opções gráficas reorganizadas.
- **Sodium Options API 1.0.10:** está presente no pack; sua presença pode ampliar superfícies de opções de terceiros, mas esta ficha não atribui hooks específicos ao Reese's sem source correspondente.
- **Controlify:** a release 2.2.3 registra correção de navegação relacionada ao Controlify; isso é lineage da UI, não dependência obrigatória inferida.

## 10. Performance
O mod pode reduzir custo humano de encontrar/configurar opções, mas **não deve ser contabilizado como mod de otimização de FPS**. Ganhos ou perdas de FPS resultam principalmente do valor das opções do Sodium e do renderer, não da existência da tela alternativa.

## 11. Riscos técnicos
1. **UI/provider mismatch:** tela mostra opção que não aplica ou salva corretamente.
2. **Sodium API drift:** mudança na estrutura das pages/options quebra a UI.
3. **Search state bug:** highlight/scroll aponta para item errado.
4. **Third-party tab conflict:** dois mods tentam organizar/injetar a mesma screen.
5. **Reset/undo semantics:** botão visível mas ação incorreta ou stale.
6. **Misclassification:** tratar Reese's como otimização independente e remover Sodium por engano.

## 12. Matriz de testes
- [ ] Cliente NeoForge inicia com Reese's 2.2.3 + Sodium 0.8.13.
- [ ] Options screen abre sem crash.
- [ ] Search encontra uma opção conhecida e destaca o resultado correto.
- [ ] Enter e Shift+Enter percorrem resultados na direção documentada.
- [ ] Resultado em página longa é rolado para a área visível.
- [ ] Limite configurável de resultados é respeitado.
- [ ] Always Show Action Buttons altera somente apresentação esperada.
- [ ] Reset/undo executam sobre a opção correta e não alteram outras settings.
- [ ] Alteração gráfica persiste após restart do cliente.
- [ ] Dedicated server não exige o mod para iniciar.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR/mod id/runtime exatos.
- Publicação oficial da 2.2.3: NeoForge 1.21.1, ambiente Client e changelog específico.
- Documentação oficial do projeto: arquitetura de navegação/search/groups.
- **Limite:** não foi feita inspeção de source pin exato para inventariar classes ou mixins internos; nenhum ganho de performance foi atribuído ao mod sem benchmark.
