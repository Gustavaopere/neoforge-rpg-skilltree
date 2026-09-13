# AzureLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81febc53dab2496b58ee
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AzureLib
- **Arquivo JAR:** `azurelib-neo-1.21.1-3.1.11.jar`
- **Versão 1.21.1:** 3.1.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual
- **Função:** Engine/biblioteca de modelos Bedrock e animações por keyframes para entidades, blocos e itens, com controllers, easing e eventos de som/partícula/custom.
- **Dependências:** Biblioteca estrutural; necessidade determinada pelos consumidores instalados que usam AzureLib.
- **Sobreposição:** Biblioteca técnica, não conteúdo jogável.
- **Compatibilidade/Riscos:** Não é intercambiável automaticamente com GeckoLib. Riscos em classloading client/server, keyframe gameplay sem authority, eventos duplicados, controllers concorrentes e caches após resource reload.
- **Observações:** AzureLib 3.1.11 NeoForge 1.21.1. Release fix: `q.x` queries e crash ao sobrescrever Bedrock easings. Engine derivada do ecossistema GeckoLib 4.x; não é intercambiável automaticamente com GeckoLib.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficial AzureLib 3.1.11 + source/documentação oficial do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/azurelib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — AzureLib 3.1.11 físico/release confirmado; engine/model/controller/keyframe lifecycle preservado; fixes de `q.x` queries e Bedrock easing override incorporados no QC global #61. Runtime QA não executado.
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
