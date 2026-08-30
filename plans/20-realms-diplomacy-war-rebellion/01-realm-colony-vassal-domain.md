# 20.01 — Domínio de realm, colônia e vassalo

## RealmRecord

Persistir:

- `realmId` namespaced/stable;
- display/localization name;
- capital reference;
- member colony IDs;
- ruler/governance reference Stage 17;
- treasury actor Stage 16;
- title hierarchy;
- vassal relations;
- diplomatic identity;
- revision/history.

## Membership

Colony ingressa por founding, treaty/vassalization, inheritance ou conquest outcome explícito. Nunca detectar member apenas porque o mesmo player UUID é owner de duas colonies.

## Capital

Uma capital é reference administrativa; mudança de capital não move chunks nem apaga Town Hall.

## Vassal realm

Vassalo mantém identidade/governo local conforme treaty e delega obrigações/diplomacy permissions específicas. Não fundir records.

## Save

Realm state em SavedData server-authoritative com migration/versioning. Colony ausente temporariamente não é removida; entra estado unresolved até adapter reconciliar.

## Testes

- create realm;
- add/remove colony;
- capital move;
- missing colony provider record;
- vassal relation cycle rejection;
- save/load/history.

## Acceptance

Realm é entidade política própria e não hack em ownership MineColonies.