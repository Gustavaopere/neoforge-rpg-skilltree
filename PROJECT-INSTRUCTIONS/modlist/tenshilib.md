# TenshiLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db817e9572ca1d11098886
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, runtime `2.3.0.b`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, TenshiLib 2.3.0.b está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** TenshiLib
- **Arquivo JAR:** `tenshilib-1.21.1-2.3.0.b-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-2.3.0.b-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Core library do ecossistema flemmli97 com animation system client/server, parsing de modelos/animações Bedrock, OBB hit detection, registro cross-loader, parser matemático e utilidades compartilhadas.
- **Dependências:** NeoForge 1.21.1; biblioteca consumida por outros projetos do autor. Consumidores causais específicos devem ser resolvidos antes de remoção.
- **Sobreposição:** Não compete com outras bibliotecas em termos de gameplay; fornece infraestrutura própria exigida pelos mods que a declaram.
- **Compatibilidade/Riscos:** Hidden dependency/API drift; animation sync e OBB server/client mismatch; parsing de assets Bedrock. A build 2.3.0.b corrige especificamente o TOML NeoForge da 2.3.0, tornando startup/mod discovery um regression gate.
- **Observações:** mod id `tenshilib`; runtime 2.3.0.b. Sozinha não adiciona gameplay. Não inferir que toda feature da library é usada por todos os consumers.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial TenshiLib 2.3.0.b File ID 8706217, ainda latest release 1.21.1. Dossiê de 09/09 preservado; consumer causal específico continua não resolvido nesta recatalogação.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/tenshilib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — TenshiLib 2.3.0.b permanece exatamente instalada; animation system, Bedrock parsing, OBB hit detection, cross-loader infrastructure, NeoForge TOML fix, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, versão `2.3.0.b`. TenshiLib é uma **biblioteca/core do ecossistema flemmli97**; isoladamente não adiciona gameplay ao jogador.

## 1. Identidade e versão
- **Mod:** TenshiLib.
- **JAR:** `tenshilib-1.21.1-2.3.0.b-neoforge.jar`.
- **Mod id:** `tenshilib`.
- **Versão:** `2.3.0.b`.
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
O runtime físico continua exatamente `tenshilib-1.21.1-2.3.0.b-neoforge.jar`, mod id `tenshilib`, versão `2.3.0.b`. A file list oficial mantém esta como latest release para NeoForge 1.21.1 e o delta exato continua sendo o fix do TOML NeoForge.

Nenhum consumer causal adicional foi afirmado sem evidência. Animation sync, OBB, parsing de assets e startup continuam pendentes de teste runtime.
