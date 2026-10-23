# Universal Material Chemistry — Provider Audit Dispositions

Every mod in the canonical modlist must receive exactly one audit disposition:

- `MATERIAL_PROVIDER`: adds or authoritatively defines eligible material identities.
- `MATERIAL_CONSUMER_ONLY`: consumes/material-processes provider resources but adds no eligible canonical material identity of its own.
- `NO_ELIGIBLE_MATERIALS`: reviewed and outside the material catalog scope.
- `BLOCKED_AUDIT`: source/data/identity evidence is insufficient to close the audit.

A `BLOCKED_AUDIT` entry counts as audited for disposition accounting but does not count as successfully inventoried material-provider coverage. The coverage report must show it separately.
