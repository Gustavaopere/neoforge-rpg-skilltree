# TenshiLib

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#538**: JAR `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, runtime `1.21.1-2.3.0.b-neoforge`, SHA-1 `bfca9fc41cb68368173ddb57655af9a2f471d021`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** TenshiLib
- **Arquivo JAR:** `tenshilib-1.21.1-2.3.0.b-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-2.3.0.b-neoforge
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Core library do ecossistema flemmli97 com animation system client/server, parsing de modelos/animações Bedrock, OBB hit detection, registro cross-loader, parser matemático e utilidades compartilhadas.
- **Dependências:** NeoForge 1.21.1; biblioteca consumida por outros projetos do autor. Consumidores causais específicos devem ser resolvidos antes de remoção.
- **Sobreposição:** Não compete com outras bibliotecas em termos de gameplay; fornece infraestrutura própria exigida pelos mods que a declaram.
- **Compatibilidade/Riscos:** Hidden dependency/API drift; animation sync e OBB server/client mismatch; parsing de assets Bedrock. A build física 2.3.0.b corrige o TOML NeoForge da 2.3.0. A upstream 2.3.1 altera render/model hooks, FollowEntity, Molang e entity-data sync, portanto qualquer promoção precisa ser testada contra consumers reais do pack.
- **Observações:** mod id `tenshilib`; runtime metadata `1.21.1-2.3.0.b-neoforge`; release identifier físico `2.3.0.b`. Upstream publicou 2.3.1 para NeoForge 1.21.1 em 27/09/2026 com mudanças de render callback/model errors, FollowEntity, Molang, suggestion widgets, entity data sync e backport de MC-273361; deltas Fabric-only não são tratados como runtime NeoForge.
- **Procedência:** modlist física atual + CurseForge oficial TenshiLib 2.3.0.b instalada e 2.3.1 para NeoForge 1.21.1, revalidado em 01/10/2026. Consumer causal específico continua não resolvido; nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/tenshilib
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — JAR físico permanece `tenshilib-1.21.1-2.3.0.b-neoforge.jar`. A release 2.3.1 foi comparada e os deltas relevantes para NeoForge foram incorporados abaixo.
- **Histórico da decisão:** 
- **Data da última decisão:** 

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, runtime metadata `1.21.1-2.3.0.b-neoforge`, release identifier `2.3.0.b`. TenshiLib é uma **biblioteca/core do ecossistema flemmli97**; isoladamente não adiciona gameplay ao jogador.
## 1. Identidade e versão
- **Mod:** TenshiLib.
- **JAR:** `tenshilib-1.21.1-2.3.0.b-neoforge.jar`.
- **Mod id:** `tenshilib`.
- **Runtime metadata:** `1.21.1-2.3.0.b-neoforge`.
- **Release identifier:** `2.3.0.b`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Release:** oficial de 22/08/2026.
- **Changelog exato:** correção do TOML NeoForge.
## 2. Authority e ownership
TenshiLib é authority de suas APIs/helpers compartilhados. O conteúdo, entidades, animações concretas e regras de gameplay continuam pertencendo aos mods consumidores.
A biblioteca aparecer em stack trace não basta para atribuir a ela a semântica do comportamento do consumer.
## 3. Animation system
O projeto publica um sistema de animação usado **server e client side**. Isto implica que consumers podem usar a biblioteca não apenas para renderização, mas também para sincronizar/avaliar estados de animação ligados ao gameplay.
Testar mudanças de animation state em multiplayer e após relog para evitar visual/state drift.
## 4. Bedrock model e animation parsing
TenshiLib inclui parsing de arquivos de **Bedrock model + animation**. Consumers podem depender dessa camada para carregar geometria/animações externas.
Resource reload, arquivo ausente/malformado e diferenças entre cliente/servidor precisam falhar de forma controlada.
## 5. Oriented Bounding Box hit detection
A biblioteca fornece detecção por **oriented bounding boxes (OBB)**, útil para hitboxes que não permanecem alinhadas aos eixos do mundo.
Consumers que usam OBB devem validar:
- rotação;
- escala;
- movimento;
- hit detection server-authoritative;
- coincidência entre modelo visual e volume lógico.
## 6. Cross-loader code e registration
O upstream também lista infraestrutura de código/registro cross-loader. O propósito é permitir que projects do autor compartilhem maior parte da implementação entre Forge/NeoForge e outros targets suportados.
Isso torna version/API alignment entre library e consumer obrigatório; outra biblioteca de registro não é substituta automática.
## 7. Math expression parser
TenshiLib inclui parser de expressões matemáticas. Sem consumer pin não é seguro inferir quais fórmulas do pack usam esse parser; registrar apenas a disponibilidade da infraestrutura.
Entradas inválidas devem ser testadas no consumer que efetivamente expõe expressões/configs ao usuário.
## 8. Release 2.3.0.b
A build imediatamente anterior `2.3.0` foi publicada em 21/08/2026; `2.3.0.b`, um dia depois, corrige especificamente o **NeoForge TOML**. Portanto startup/discovery do modloader é regression gate direto desta build.
## 9. Client / server e multiplayer
A própria descrição confirma sistemas usados nos dois lados. Para consumers:
- servidor continua authority de hit/gameplay state;
- cliente renderiza modelo/animação;
- packets/state de animação não podem produzir hitbox divergente;
- dedicated server não deve tentar carregar renderer client-only indevidamente.
## 10. Lifecycle
Validar:
- startup NeoForge e mod discovery;
- dedicated server boot;
- resource/model load;
- animation start/stop/loop;
- entity spawn/despawn e chunk reload;
- OBB após rotação/movimento;
- relog/restart;
- atualização conjunta consumer + TenshiLib.
## 11. Riscos técnicos
1. **Hidden dependency:** remover library pode impedir consumer de carregar.
2. **TOML/load metadata:** 2.3.0.b existe para corrigir precisamente essa superfície.
3. **Animation sync:** client/server podem divergir em timing/state.
4. **OBB mismatch:** hitbox lógica pode não coincidir com modelo animado.
5. **Resource parsing:** Bedrock files inválidos ou incompatíveis podem quebrar consumer.
6. **API drift:** consumers do autor podem exigir versão específica.
## 12. Matriz de testes
- [ ] NeoForge detecta TenshiLib 2.3.0.b sem TOML warning/error impeditivo.
- [ ] Dedicated server inicia sem classes client-only carregadas incorretamente.
- [ ] Consumer real do pack é identificado antes de remoção.
- [ ] Model/animation Bedrock carrega e recarrega.
- [ ] Animation state sincroniza entre dois clientes.
- [ ] OBB acompanha rotação/movimento e hit detection do servidor.
- [ ] Arquivo de animação ausente/malformado falha de modo controlado.
- [ ] Upgrade da library em cópia de teste não quebra consumers.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 13. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- CurseForge oficial TenshiLib: core/library, animation system client/server, Bedrock parsing, OBB hit detection, cross-loader registration, math parser; 2.3.0.b corrige NeoForge TOML.
## 14. Revalidação física — 11/09/2026
O runtime físico continua exatamente `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, runtime metadata `1.21.1-2.3.0.b-neoforge`; `2.3.0.b` é o release identifier público. A file list oficial mantém esta como latest release para NeoForge 1.21.1 e o delta exato continua sendo o fix do TOML NeoForge.
Nenhum consumer causal adicional foi afirmado sem evidência. Animation sync, OBB, parsing de assets e startup continuam pendentes de teste runtime.
## 15. Revalidação física — 13/09/2026
O JAR físico permanece `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, com runtime metadata `1.21.1-2.3.0.b-neoforge`; a release NeoForge 1.21.1 localizada continua com identifier público `2.3.0.b`. O fix do TOML NeoForge permanece regression gate direto. Nenhum consumer causal novo foi afirmado e nenhum teste de animation sync, OBB, parsing de assets ou startup foi executado.

## 16. Atualização upstream 2.3.1 — não instalada

A autoridade física continua em **TenshiLib 2.3.0.b**. A release **2.3.1** para NeoForge 1.21.1 foi publicada em 27/09/2026.

### Deltas oficiais
Relevantes para a linha compartilhada/NeoForge:
- melhora o handling de custom vertex elements para torná-lo mais simples de usar;
- adiciona render callback, com exemplo upstream de tooltips para suggestion widget;
- melhora reporting/handling de erro ao carregar models e animations;
- adiciona comportamento custom **FollowEntity**;
- corrige valores incorretos de algumas variáveis Molang;
- corrige seleção do suggestion widget;
- corrige **entity data syncing** que às vezes transmitia valores errados;
- backporta o fix vanilla **MC-273361**, relacionado a entidades invisíveis durante teleport.

O changelog também cita tick event e fix de attachment loading específicos de Fabric; esses itens não são promovidos como comportamento NeoForge desta instância.

### Impacto para o pack
Como TenshiLib é biblioteca, o risco não está em gameplay próprio, mas em consumers que dependam de animation/model/render/network state. O fix de entity-data sync e o backport de invisibilidade em teleport merecem regressão multiplayer; FollowEntity pode alterar behavior de consumers que adotarem a API nova.

### Gate de promoção 2.3.0.b → 2.3.1
- [ ] NeoForge descobre a library sem regressão do TOML corrigido em 2.3.0.b.
- [ ] Consumer causal real é identificado antes da promoção.
- [ ] Models/animations válidos carregam; assets inválidos falham com erro controlado.
- [ ] Animation/entity state sincroniza entre dois clientes e servidor.
- [ ] Teleport de entidade não deixa invisibilidade stale — regressão MC-273361.
- [ ] FollowEntity, quando usado por consumer presente, não deixa target/path state stale após unload/dimension change.
- [ ] Molang variables usadas por models/animations produzem valores esperados.
- [ ] Suggestion widget selection/render callback não quebra screens de consumers.
- [ ] OBB/hit detection existente permanece alinhado ao servidor.

Fonte upstream: CurseForge TenshiLib 2.3.1, file ID 8990097, NeoForge 1.21.1. Nenhum teste acima foi executado nesta atualização documental.
