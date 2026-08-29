# 12.14 — Migração, recuperação e ferramentas administrativas

## Objetivo

Permitir adoção do Stage 12 em mundos existentes e oferecer ferramentas de diagnóstico/recuperação que não dependam de editar NBT manualmente.

## Bootstrap de mundo existente

Na primeira inicialização para um jogador sem `BodyRegistry`:

1. capturar o estado RPG/vanilla/providers atualmente ativo;
2. criar um `bodyId` novo;
3. registrar como **Corpo Original**;
4. marcar como `ACTIVE`;
5. persistir revision inicial;
6. somente então habilitar criação/troca de novos corpos.

O bootstrap não deve resetar nível, perks, inventário, classe ou qualquer estado suportado.

## Idempotência

Reexecutar bootstrap após crash não pode criar vários “Corpos Originais”. Usar marker/version e ownership verificáveis.

## Migrações de schema

Para cada `schemaVersion`:

- migration explícita;
- backup antes de migration destrutiva;
- dry validation antes de commit;
- logs com owner/body/revision sem dump de dados sensíveis desnecessários;
- rollback quando possível.

## Recovery automática

No login/world load:

- detectar journal incompleto;
- detectar `ACTIVE` duplicado;
- verificar referência a anchor inexistente;
- validar snapshots/providers;
- restaurar `lastKnownGood` quando decisão for inequívoca;
- marcar `RECOVERY_REQUIRED` quando houver ambiguidade.

## Ferramentas administrativas

Criar comandos server-side com permissões apropriadas. Namespace técnico pode permanecer `/rpg body`, com feedback integral em PT-BR.

Comandos previstos:

```text
/rpg body list [player]
/rpg body inspect <bodyId>
/rpg body validate <bodyId>
/rpg body recover <bodyId>
/rpg body set-active <bodyId> --force
/rpg body rename <bodyId> <nome>
/rpg body anchors <bodyId>
/rpg body export-diagnostic <bodyId>
```

`--force` deve exigir permissão elevada e nunca ignorar invariantes de duplicação de inventário sem criar backup/journal.

## Diagnóstico

Relatório deve incluir:

- owner UUID;
- bodyId;
- state;
- schema/revision;
- active marker;
- anchor(s);
- providers presentes/ausentes;
- validação de snapshot;
- transaction journal;
- lastKnownGood disponível;
- erros de migration.

Não imprimir inventário completo por padrão; permitir modo detalhado apenas quando necessário.

## Corpos órfãos

Um `BodyProfile` cujo anchor foi destruído não é apagado. Ele pode ficar:

- `STORED` sem anchor;
- `RECOVERY_REQUIRED` se a política exigir anchor para ativação.

Admin/player pode reconstruir/revincular uma âncora por fluxo seguro.

## Remoção de mods

Se provider externo desaparecer:

- preservar seu blob/version metadata;
- não aplicar/limpar silenciosamente;
- impedir switch somente quando aquele provider é `REQUIRED_FOR_SWITCH` e a ausência produziria corrupção;
- permitir recovery após reinstalar o mod/provider.

## Backups

Antes de operações administrativas destrutivas:

- criar snapshot de segurança;
- registrar revision e motivo;
- permitir inspeção do backup até política de retenção configurável.

## Critérios de aceite

- mundo antigo vira Corpo Original sem perda;
- bootstrap é idempotente;
- crash de migration não duplica corpo;
- corpo órfão não é apagado;
- provider removido preserva dados;
- ferramentas administrativas conseguem diagnosticar/reparar estados previstos sem edição manual de arquivos.