# 10.12 — Ferramentas de operador e separação survival/admin

## Objetivo

Preservar as funções úteis de inspeção/manipulação associadas a um “biology dictionary” técnico sem misturá-las à experiência survival do Compêndio.

A enciclopédia comum é **read-only**. Alterar AI, invulnerabilidade, idade, persistência, ownership ou outros estados pertence a um painel de operador separado.

## Princípios

- desabilitado por padrão para jogadores survival;
- autorização sempre no servidor;
- permissões/op level configuráveis;
- nenhum botão admin é enviado/ativado só porque o cliente diz ser operador;
- mutações geram feedback/audit log suficiente;
- ações destrutivas ou de segurança elevada pedem confirmação;
- dedicated server pode desabilitar todo o módulo admin por config.

## Funções candidatas

Somente implementar se houver API segura e valor real:

- congelar/liberar crescimento;
- impedir/permitir reprodução;
- silenciar/des-silenciar entidade;
- togglar persistência/despawn;
- ativar/desativar AI;
- invulnerabilidade;
- reset/controlar portal cooldown;
- inspecionar/trocar owner de tameable quando tecnicamente válido;
- visualizar inventário de entidade/aldeão quando permitido;
- abrir informações especiais de aldeão/abelha/cavalo/etc.;
- obter spawn egg em contexto creative/op;
- localizar referência de job site/hive quando o servidor autorizar.

Não prometer suporte universal: cada ação precisa declarar capability por entidade.

## Plano

### A — Capability model

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminCapability.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminCapabilityResolver.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminAction.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminActionResult.java
```

A UI só mostra ação quando o servidor confirma capability.

### B — Permission gate

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/admin/CompendiumAdminPermissions.java
```

Config prevista:

```text
compendium.admin.enabled=false
compendium.admin.requiredPermissionLevel=2
compendium.admin.auditLog=true
```

Se o projeto adotar uma API de permissões externa, ela deve ser integração opcional.

### C — Packets de ação

Cada packet de mutação deve conter somente:

- target entity id/UUID validável;
- action id conhecido;
- argumento tipado mínimo;
- nonce/request id se o protocolo existente usar esse padrão.

Servidor revalida:

- permissão;
- entidade ainda existe;
- distância/dimensão;
- capability;
- argumento;
- estado atual.

Nunca aceitar NBT arbitrário do cliente.

### D — Auditoria

Para mutações relevantes, logar de forma estruturada:

```text
jogador/op
UUID
entidade alvo
entry id
ação
valor anterior quando barato/seguro
valor novo
resultado
```

Evitar spam de log para ações somente de leitura.

### E — UI separada

Arquivos client previstos:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/client/admin/CompendiumAdminPanel.java
```

- [ ] não aparece para usuário não autorizado;
- [ ] visualmente diferente das informações enciclopédicas;
- [ ] confirmação para ações potencialmente disruptivas;
- [ ] atualização de estado depois da resposta server-side;
- [ ] erro legível em pt-BR.

### F — Não transformar em cheat obrigatório

A ausência/disable do módulo admin não pode:

- quebrar páginas de entidade;
- bloquear descoberta;
- remover dados técnicos read-only;
- alterar save do Compêndio;
- afetar dedicated server smoke.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminPermissionTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminCapabilityResolverTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/admin/AdminActionValidationTest.java
```

Casos obrigatórios:

- [ ] jogador normal é rejeitado;
- [ ] op autorizado executa ação suportada;
- [ ] action id desconhecido é rejeitado;
- [ ] entidade fora de alcance/dimensão é rejeitada;
- [ ] argumento inválido é rejeitado;
- [ ] capability ausente é rejeitada sem crash;
- [ ] packet replay não produz efeito indevido quando a ação exigir idempotência;
- [ ] admin disabled remove superfície de mutação mas mantém Compêndio normal.

## Acceptance

O subplano fecha quando ferramentas administrativas estiverem isoladas, permissionadas e seguras, sem expor mutações na experiência survival comum.
