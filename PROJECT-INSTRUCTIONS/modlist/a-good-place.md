# A Good Place

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db81e89d0ad1e6e9d9aab0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** A Good Place
- **Arquivo JAR:** `a_good_place-1.21-1.2.5-neoforge.jar`
- **Versão 1.21.1:** 1.21-1.2.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Categoria:** Visual, QoL
- **Função:** Mod client-side de animação de colocação de blocos, totalmente configurável por resource packs. Em vez do bloco simplesmente surgir, uma representação temporária é transformada por control points/curvas com translação, rotação, escala, pivô e seleção por block-state predicates. 1.2.5 melhora lógica de block entities e corrige bug específico; não altera placement autoritativo, inventário, recipe ou worldgen.
- **Dependências:** Nenhuma hard dependency adicional foi localizada para a release NeoForge 1.21.1-1.2.5 além de Minecraft/NeoForge compatíveis. Resource packs são entrada funcional/configurável do sistema, não dependência tradicional. O Sample Pack gerado pelo mod é a referência de schema para authoring.
- **Sobreposição:** Sobreposição apenas visual com frameworks/VFX/shaders. A Good Place é especificamente placement animation data-driven; não substitui partículas ambientais, worldgen, builder/schematic ou regras de colocação. Resource packs podem estender o próprio mod e são parte direta da auditoria.
- **Compatibilidade/Riscos:** Risco principal é render/resource composition. Definições de resource packs podem selecionar os mesmos blocos e produzir prioridade/efeito inesperado; block entities exigem teste específico porque 1.2.5 mexe nessa lógica. AAA Particles/World, Particular, Particle Rain e shaders podem aumentar densidade/custo visual, mas não executam a mesma mecânica. Placement automatizado rápido pode gerar grande quantidade de animações client-side sem alterar servidor.
- **Observações:** Para criar/editar animações, usar o Sample Pack/README gerado pela MESMA build 1.2.5 como authority de schema. Changelogs anteriores mostram mudanças de campos/predicates; não copiar JSON de versões antigas sem comparar. Mod é client-side: nunca usar a animação como trigger/authority de gameplay.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial A Good Place, changelogs da linha 1.2.x e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `a_good_place-1.21-1.2.5-neoforge.jar` / `1.21-1.2.5`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/a-good-place
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #2: `a_good_place-1.21-1.2.5-neoforge.jar` / `1.21-1.2.5` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `a_good_place-1.21-1.2.5-neoforge.jar`. A Good Place é um **renderer client-side de placement animation**, configurado por resource packs. O servidor continua sendo authority da colocação do bloco.

## 1. Função real
Ao colocar um bloco, A Good Place troca o “aparecimento instantâneo” visual por uma animação temporária que conduz a representação até a posição/estado final. A animação pode ser definida por dados e variar conforme o bloco/estado alvo.

A feature é puramente de apresentação: depois que o servidor aceita a colocação, o cliente representa esse evento com a animação configurada.

## 2. Configuração por Resource Pack
O publisher descreve o processo como **entirely customizable through Resource Packs**. Isso significa que o comportamento visual não é definido apenas pelo JAR: resource packs podem determinar:
- quais blocos/estados recebem animação;
- como o movimento ocorre;
- duração e progressão;
- transformações visuais;
- condições/predicates de seleção.

O mod gera/fornece um **Sample Pack** na área de resource packs para servir de exemplo de estrutura. Para authoring desta instância, esse Sample Pack da build 1.2.5 é a referência mais segura.

## 3. Control points e trajetória
A documentação pública destaca **control points** como mecanismo de desenho da animação. Em termos funcionais, eles permitem que o bloco passe por estados intermediários de transform antes de assentar no destino.

Isso permite efeitos como:
- cair/entrar de cima;
- deslizar lateralmente;
- crescer de escala reduzida;
- girar enquanto se posiciona;
- combinar translação, rotação e escala.

Não assumir uma interpolação/curve específica sem conferir o JSON da build atual.

## 4. Transformações documentadas na linha 1.2.x
Changelogs anteriores à 1.2.5 mostram evolução do schema e são relevantes porque a build instalada herda essas mudanças:
- rotation e translation passaram a trabalhar como vetores (`Vec3`) na linha moderna;
- foi adicionado conceito de **pivot point**;
- seleção passou a usar predicates mais estruturados;
- na 1.2.0 houve troca para `BlockStatePredicate` em vez do mecanismo anterior;
- correções específicas foram feitas para casos de estado como **Sea Pickle**.

### Regra de compatibilidade de dados
Não copiar JSON de 1.0/1.1 e presumir que campos ainda são válidos. Sempre comparar com Sample Pack/README de 1.2.5.

## 5. Block-state selection
A animação não precisa tratar todo item/bloco de forma uniforme. O sistema pode selecionar por estado de bloco/predicate, o que é necessário para blocks com múltiplas propriedades.

Isso é especialmente relevante para:
- stairs/slabs;
- doors/trapdoors;
- directional blocks;
- waterlogged states;
- multiface/multicount blocks;
- blocks modded com propriedades próprias.

Quando um block modded possui state complexo, testar cada state de interesse; “o bloco funciona” não garante que todas as variantes animem corretamente.

## 6. Block Entities
A release **1.2.5** registra explicitamente **improved logic for block entities** e uma correção adicional.

Block entities precisam de tratamento especial porque o bloco possui estado/dados/render além do `BlockState`. Exemplos no pack incluem máquinas, containers e blocos tecnológicos.

### Riscos
- renderer temporário perder orientação/estado visual;
- bloco aparecer duplicado durante a transição;
- conteúdo/render dinâmico piscar;
- block entity renderer começar antes/depois do placement animation em ordem inesperada.

Por isso pelo menos um container, uma máquina e um block entity com renderer custom devem fazer parte do smoke test.

## 7. Main hand / off hand
A linha de changelog anterior registra mudanças para considerar a **mão usada** no placement. Isso importa para mods que possuem comportamentos diferentes em main/off hand.

Teste ambos os caminhos; não assumir que a animação disparada pelo interaction hook é idêntica em todas as mãos.

## 8. Resource reload
Como as definições vêm de resource packs, o lifecycle de **resource reload** faz parte do sistema.

Testar:
- ativar/desativar pack;
- alterar pack order;
- reload sem restart;
- retorno ao default;
- config inválida/missing resource.

O comportamento esperado em caso de definição ausente deve ser fallback visual, nunca alterar o resultado server-side da colocação.

## 9. Prioridade entre packs
Quando mais de um resource pack define animação para o mesmo alvo, a ordem/prioridade de resources pode determinar qual definição prevalece ou como assets são resolvidos.

Isso é um problema de **resource-stack**, não “incompatibilidade entre mods”. Para troubleshooting, registrar:
1. packs ativos;
2. ordem;
3. arquivo que define o target;
4. resultado após reload.

## 10. Performance
Cada placement visível pode criar transforms/partículas/objetos temporários no cliente. O custo cresce em cenários como:
- construção manual muito rápida;
- schematic/builders imprimindo muitos blocos próximos;
- machines/contraptions expondo muitos placements percebidos pelo cliente;
- shaders pesados;
- vários particle/VFX mods simultâneos.

O mod não acelera nem desacelera a colocação lógica; apenas pode afetar FPS/frame time durante o feedback visual.

## 11. Integração com AAA Particles
AAA Particles é um runtime Effekseer; A Good Place possui sistema próprio de placement animation. Os dois podem aparecer no mesmo frame, mas não são substitutos.

Cenário de teste: colocar blocos enquanto efeitos Effekseer intensos estão ativos e observar state leakage, depth/transparency e frame time.

## 12. Particular Reforged / Particle Rain
Esses mods criam VFX ambientais/interativos. O risco é densidade visual e custo, principalmente em chuva/áreas com partículas, não conflito de regra de placement.

## 13. Shaders
Shaders podem alterar:
- depth;
- transparência;
- iluminação;
- shadow casting;
- bloom/emissive;
- motion visual do objeto temporário.

A ausência de incompatibilidade formal não elimina glitches. Testar Complementary/current shader stack ativo e desativado.

## 14. Builders, schematics e automação
A Good Place não é Schematicannon, world editor nem builder. Ele não decide quantos blocos podem ser colocados nem consome inventário.

Quando uma ferramenta automatizada coloca blocos:
- o servidor/tool define placement;
- A Good Place pode apenas representar visualmente placements observados pelo cliente se o hook se aplicar.

Portanto bug de inventário/duplicação/placement não deve ser atribuído ao mod sem evidência server-side.

## 15. Multiplayer
Como o mod é client-side, dois jogadores podem ter configurações/resource packs diferentes e enxergar animações diferentes para o mesmo placement.

Isso é esperado e reforça que a animação não deve ser usada como gameplay authority.

## 16. Dedicated server
A lógica do servidor não depende da animação client-side. O server pack não deve usar A Good Place como requisito para permitir placement.

Em auditoria de distribuição, confirmar o comportamento suportado pela release atual, mas funcionalmente nenhum sistema server-authoritative deve depender de seus efeitos.

## 17. Falhas comuns a distinguir
### Bloco não foi colocado
Provavelmente gameplay/server/interaction; A Good Place só deve ser suspeito se houver evidência de interceptação indevida além do visual.

### Bloco foi colocado mas animação faltou
Verificar target/predicate/resource-pack/config.

### Bloco pisca/duplica visualmente
Investigar block entity renderer, shader e timing do placement animation.

### Animação errada para uma variante
Investigar `BlockStatePredicate`/state selection.

### FPS cai durante construção
Medir quantidade de placements e composição com VFX/shader.

## 18. Matriz de validação
1. Stone/block simples.
2. Stairs em todas as orientações principais.
3. Slab top/bottom/double quando aplicável.
4. Waterlogged block.
5. Sea Pickle com vários counts.
6. Door/trapdoor/directional block.
7. Chest/container block entity.
8. Block entity tecnológica com renderer custom.
9. Main hand.
10. Off hand.
11. Animação com translation.
12. Animação com rotation.
13. Animação com scale.
14. Pivot custom.
15. Múltiplos control points.
16. Predicate específico de block state.
17. Dois resource packs mirando o mesmo bloco.
18. Resource reload.
19. Shader OFF/ON.
20. AAA Particles/Particular/Particle Rain simultâneos.
21. Placement em massa.
22. Multiplayer com um cliente sem o pack visual.
23. Confirmar que inventário/estado final é idêntico com mod ligado/desligado.

## 19. Regras para outros chats
- A Good Place = **presentation layer**.
- Resource pack é parte essencial da configuração; auditar junto.
- Para custom animation, usar schema do Sample Pack da build instalada.
- Não atribuir regras de placement/inventory/worldgen ao mod.
- Não chamar composição de shader/particles de incompatibilidade formal sem reprodução.
- Block entities merecem teste dedicado por causa da mudança específica 1.2.5.

## 20. Versão 1.2.5
Release física atual para 1.21.1. Changelog publicado: **improved logic for block entities** e fix adicional. A build herda as mudanças estruturais anteriores da linha 1.2.x, mas o schema final deve ser lido no Sample Pack correspondente.

## 21. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [A Good Place — CurseForge](https://www.curseforge.com/minecraft/mc-mods/a-good-place), files/changelogs 1.2.x e Sample Pack da própria instalação.

**Fonte interna:** guia gameplay corrigido.

**Confiança:** alta para client-side/Resource Pack/control-point architecture e 1.2.5 block-entity change. Nomes/campos exatos de JSON devem ser copiados da build instalada antes de authoring para evitar documentação obsoleta.
