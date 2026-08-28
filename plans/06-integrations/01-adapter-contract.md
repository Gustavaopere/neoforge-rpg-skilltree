# Integrations Plan — Adapter Contract

**Goal:** padronizar como qualquer mod opcional entra no RPG Skill Tree.

- [ ] Detectar presença sem classloading prematuro.
- [ ] Definir interface/capability interna por ação semântica.
- [ ] Manter tipos externos confinados ao adapter.
- [ ] Fallback neutro quando ausente.
- [ ] Registrar uma única fonte de progressão por ação.
- [ ] Padronizar diagnóstico de adapter habilitado/desabilitado.

**Acceptance:** uma integração pode ser removida do modpack sem alterar o carregamento do core.