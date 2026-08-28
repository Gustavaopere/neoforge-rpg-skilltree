# Foundation Complete — Client/Server Boundaries

**Goal:** impedir classloading client-only no dedicated server e manter a camada comum limpa.

- [x] Isolar telas, keybinds, renderers e registro client.
- [x] Revisar inicializadores estáticos comuns por referências a classes client-only.
- [x] Garantir que packets comuns não importem UI/rendering.
- [x] Cobrir login, datapack reload e bootstrap do servidor sem inicialização de cliente.
- [x] Adicionar teste/smoke que falhe em referência client-only acidental.

## Runtime contract

- O código visual fica isolado em `runtime/client/`; keybindings e eventos de entrada são registrados com `Dist.CLIENT`.
- O bootstrap comum não depende de classes `net.minecraft.client.*` para carregar.
- Requests e syncs de progressão permanecem na camada de rede comum; a UI apenas envia intenção e consome snapshot.
- Login/reconciliação e reload de dados usam classes server/common.
- O dedicated-server smoke do CI é o gate contra regressões de classloading client-only durante bootstrap/reload.

## Verification

- Auditoria de fechamento: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.
- `ClientKeyMappings` e `RpgSkillTreeScreen` permanecem no pacote client; `ClientKeyMappings` é explicitamente `Dist.CLIENT`.
- CI `33132979048` / run #620: Core tests, validators, NeoForge build, JAR verification e dedicated-server smoke todos GREEN.

**Acceptance:** satisfied. O dedicated server inicia sem carregar a camada visual e o registro de cliente permanece separado.