# AzureLib

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#61**: `azurelib-neo-1.21.1-3.1.11.jar`, mod id `azurelib`, runtime `3.1.11`, SHA-1 `9a168688466b3f924c09a20a2d99febe4588ffa5`.

> **Reauditoria física — 19/09/2026.** JAR top-level reconfirmado na modlist física atual: `azurelib-neo-1.21.1-3.1.11.jar`, versão `3.1.11`. Paridade Notion → GitHub revalidada; URL da própria página Notion removida.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 22/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AzureLib
- **Arquivo JAR:** azurelib-neo-1.21.1-3.1.11.jar
- **Versão 1.21.1:** 3.1.11
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual
- **Função:** Engine/biblioteca de modelos Bedrock e animações por keyframes para entidades, blocos e itens, com controllers, easing e eventos de som/partícula/custom.
- **Dependências:** Biblioteca estrutural; necessidade determinada pelos consumidores instalados que usam AzureLib.
- **Sobreposição:** Biblioteca técnica, não conteúdo jogável.
- **Compatibilidade/Riscos:** Não é intercambiável automaticamente com GeckoLib. Riscos em classloading client/server, keyframe gameplay sem authority, eventos duplicados, controllers concorrentes e caches após resource reload.
- **Observações:** AzureLib 3.1.11 NeoForge 1.21.1. Release fix: `q.x` queries e crash ao sobrescrever Bedrock easings. Engine derivada do ecossistema GeckoLib 4.x; não é intercambiável automaticamente com GeckoLib.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficial AzureLib 3.1.11 + source/documentação oficial já auditados no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `azurelib-neo-1.21.1-3.1.11.jar` / `3.1.11`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/azurelib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #61: `azurelib-neo-1.21.1-3.1.11.jar` / `3.1.11` conferidos contra a modlist atual; engine/model/controller/keyframe lifecycle e fixes específicos 3.1.11 preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou AzureLib 3.1.11 como biblioteca de modelos/animações e preservou seus contratos de side, controllers, keyframes e lifecycle. Em 09/09/2026, a release física foi revalidada e os dois fixes específicos de 3.1.11 foram incorporados sem converter presença em decisão curatorial.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `azurelib-neo-1.21.1-3.1.11.jar`, mod id `azurelib`, runtime `3.1.11`, NeoForge 1.21.1. AzureLib é uma biblioteca de animação/modelos derivada do ecossistema GeckoLib 4.x.

## 1. Papel e autoridade
AzureLib fornece engine e APIs para **modelos Bedrock e animações complexas** de entidades, blocos e itens. Ela é infraestrutura visual/animacional para consumidores; não define por si só dano, IA, inventário ou progressão.

O mod consumidor continua authority da lógica de gameplay. AzureLib controla avaliação de animação, keyframes e apresentação dos modelos que usam sua engine.

## 2. Superfícies funcionais oficiais
O projeto documenta suporte a:
- animações 3D por keyframes;
- múltiplas animações concorrentes;
- mais de 30 funções de easing;
- keyframes de som;
- keyframes de partículas;
- keyframes/eventos customizados;
- modelos Bedrock aplicados a entities, blocks e items.

Essas superfícies devem ser tratadas como engine de apresentação/event dispatch, não como um segundo sistema de combate.

### Delta específico da release 3.1.11
A release instalada 3.1.11 registra dois fixes explícitos: correção de consultas `q.x` que não funcionavam corretamente e correção de crash ao sobrescrever Bedrock easings com outros Bedrock easings. Esses fixes pertencem ao runtime físico atual e entram no escopo de regressão de animação/modelos.

## 3. Controllers e causalidade
Animações podem coexistir e ser disparadas por estado do consumidor. O contrato seguro é:
- gameplay decide o estado;
- o estado seleciona/avança animação;
- keyframe visual não deve criar dano/loot diretamente sem validação server-side do consumidor;
- eventos animacionais que tenham efeito de gameplay precisam preservar owner/entity e exactly-once semantics.

## 4. Modelos e recursos
AzureLib usa assets/modelos/animações fornecidos pelo consumidor. Resource reload deve reconstruir caches/model data sem reter referências stale. Model missing ou animação ausente deve falhar de forma diagnosticável, não alterar state de servidor.

## 5. Client/server
- render, pose interpolation e efeitos puramente visuais são cliente;
- IA, atributos, dano, inventário e transições autoritativas pertencem ao servidor/mod consumidor;
- packets de animação, quando usados pelo consumidor, devem refletir estado aprovado pelo servidor em vez de transformar o cliente em authority.

## 6. Concorrência de animações
Como a engine suporta animações simultâneas, consumidores precisam definir prioridades/transições de forma determinística. Riscos típicos: dois controllers disputarem o mesmo bone, animação de ataque reiniciar continuamente, ou evento de keyframe disparar mais de uma vez após reconnect/reload.

## 7. Relação com outras bibliotecas
AzureLib não é automaticamente intercambiável com GeckoLib ou outras engines mesmo tendo herança conceitual semelhante. Model classes, controller lifecycle e assets do consumidor devem usar a API para a qual foram escritos.

## 8. Lifecycle
Validar:
- entity spawn/despawn;
- chunk unload/reload;
- dimension change;
- item equip/unequip;
- block entity load/unload;
- resource reload;
- server reconnect;
- troca de animation state durante latência.

Controllers/caches associados a objetos destruídos devem ser liberados.

## 9. Riscos
1. Classloading de renderer/model no dedicated server.
2. Keyframe gameplay executado apenas no cliente.
3. Evento duplicado ao reiniciar animação.
4. Cache stale depois de F3+T/resource pack change.
5. Conflito de bones/controllers em animações concorrentes.
6. Consumidor compilado contra API incompatível.

## 10. Matriz de testes
1. Dedicated server boot com consumidores AzureLib.
2. Resource reload com entities/blocks/items já carregados.
3. Spawn/despawn e chunk unload sem controller órfão.
4. Ataque animado: dano exactly-once pelo provider de gameplay.
5. Som/partícula/event keyframes em multiplayer sem duplicação.
6. Dois controllers concorrentes com transição estável.

## 11. Evidência
- modlist física atual: AzureLib 3.1.11;
- CurseForge oficial da build NeoForge 1.21.1;
- source/documentação oficial do projeto AzureLib e descrição de suas capacidades de animação/keyframes.

> 🎞️ A ficha é exaustiva para o papel de uma engine: modelo, animação, keyframes, concorrência, side e lifecycle. Nenhum comportamento de gameplay foi atribuído à biblioteca sem evidência.

## 12. Histórico upstream 3.1.12 → 3.1.18 — 10/10/2026

**Última versão fisicamente verificada:** `azurelib-neo-1.21.1-3.1.11.jar`, runtime 3.1.11. O índice CurseForge para **NeoForge 1.21.1** mostra releases **3.1.12 → 3.1.13 → 3.1.14 → 3.1.15 → 3.1.16 → 3.1.17 → 3.1.18**. A **3.1.18**, publicada em 08/10/2026 (file ID **9095629**), é a última NeoForge desta linha identificada. **3.1.19 (09/10)** aparece para **Fabric 1.21.1**, mas não foi localizada como JAR NeoForge 1.21.1 no índice consultado; não a promover por analogia.

### Deltas intermediários de source/release
- **3.1.12 (25/09, NeoForge file 8969045):** registro de `AzArmorRendererRegistry`, `AzItemRendererRegistry` e `AzIdentityRegistry` fica **thread-safe**. Corrige falhas intermitentes de render, incluindo itens/armaduras roxos/missing textures, e trigger animations não disparando em setups paralelos. Para compatibilidade com builds antigas, o upstream recomenda `event.enqueueWork(...)` em eventos FML de setup.
- **3.1.13 (27/09):** introduz **`AzSequence`** com stages `play`, `loop`, `hold` e `then`, e **eventos temporizados** via `AzSequencePlayer`. APIs de sequência alteram orquestração de animação e keys de som/partícula; loops e holds precisam ter precedência bem definida.
- **3.1.14 (01/10):** adiciona **`AzWeightedPoolBehavior`**, selecionando animação seguinte por pesos após completar a anterior; relevante para idles/walk variety, com necessidade de testar determinismo/persistência de state client-side.
- **3.1.15 (05/10):** suporte à **reprodução reversa** de animações (por stage/controller), incluindo Molang `query.anim_time`, keyframe events e transições que misturam até o frame final apropriado. Mudança de ordem de eventos exige evitar som/partícula duplicados.
- **3.1.16 (06/10):** otimiza avaliação de **keyframes** com cache da posição por bone/controller, inclusive playback reverso/`PING_PONG`; ao saltar/looping faz busca binária nos timestamps carregados. Reduz trabalho em mobs animados de alta densidade.
- **3.1.17 (07/10, NeoForge file 9090376):** expõe `math.min_angle(value)` e hooks de **`AzProfiler`**; corrige resolução de `math.copy_sign`, `math.sign`, `math.inverse_lerp` e 30 `math.ease_*` no Molang. O registro padronizado passa a exigir nomes com prefixo `math.`, podendo **quebrar scripts/expressions sem prefixo**. Otimizações em glowmask, faces zero-area, rotations e loops/bones; `AzModelRenderer#renderCube` deixa de modificar o pose stack automaticamente, e subclasses que alteram o stack precisam push/pop explícitos.
- **3.1.18 (08/10, NeoForge file 9095629):** menos alocação render-thread por bone (pose/matrix reutilizadas), resolução única de animation bones por controller, LOD mais barato sem bones ocultos e texturas animadas sem reflection/exception per-render. Corrige transições que paravam todos os bones ao encontrar um bone sem snapshot/queue, passando a pular apenas o bone inválido. Os ganhos de GC descritos no changelog são do upstream e **não são benchmark deste pack**.

### Compatibilidade crítica
AzureLib é uma **engine de animação** consumida por outros mods; migração 3.1.11→3.1.18 não deve ser julgada só por versão maior. Conferir consumers de armaduras/items customizados, render pipelines, shaders, EMF/ETF, animação de entidades com GeckoLib coexistente e record destructuring no código de addons que estendem AzureLib. Testar cliente+dedi server, centenas de entidades com os mesmos controllers, setup paralelo, resource reload, reverse animation `PING_PONG`, Molang `math.` e sound/particle exactly-once, restart de mundo. Não afirmar que um teste de CodeQL ou CI documental executou essas regressões.

**Fontes oficiais:** https://www.curseforge.com/minecraft/mc-mods/azurelib/files/all?version=1.21.1 ; https://www.curseforge.com/minecraft/mc-mods/azurelib/files/8969045 ; https://www.curseforge.com/minecraft/mc-mods/azurelib/files/9090376 ; https://www.curseforge.com/minecraft/mc-mods/azurelib/files/9095629

**Decisão:** deixar 3.1.11 como estado instalado e 3.1.18 como candidato upstream; dependências/consumers e testes físicos pendentes.

