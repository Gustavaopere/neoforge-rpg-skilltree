# Integrations Complete — Goety, Malum and Eidolon

**Goal:** consolidar integrações ocultas por ações confirmadas, não simples interações auxiliares.

## Goety
- [x] Confirmar casts/commands/servant actions usados para mastery.
- [x] Resolver efeitos ligados a Soul Energy sem duplicidade.

## Malum
- [x] Confirmar spirit harvesting/reaping como fontes de progressão.
- [x] Validar Spirit Spoils, Arcane Resonance, Soul Ward e Geas Limit.

## Eidolon
- [x] Conceder mastery/discovery apenas após receita de Crucible concluída.
- [x] Validar ritual completion quando aplicável.

## Runtime contract

- Goety registra casts como candidatos e só confirma mastery de spell quando o próprio mod confirma gasto de Soul Energy; commands só contam após o estado real do servant confirmar a ordem, e servant hostile kills preservam autoria.
- Malum usa eventos públicos de spirit reaping/collection; os efeitos Occult atuais cobrem Spirit Spoils, Arcane Resonance, Soul Ward Capacity e Geas Limit.
- Eidolon separa intent/tentativa de conclusão: Crucible só premia após recipe completion e o caminho ritual usa confirmação de ritual concluído.
- Os adapters descartam ações auxiliares/incompletas e usam os serviços centrais de mastery/discovery para publicar progressão.

## Verification

- `GoetyCommandPolicyTest`, `GoetyMasteryCoreTest` e `GoetySoulPolicyTest` cobrem os contratos de comando, mastery e Soul Energy.
- `MalumMasteryCoreTest` cobre a classificação/progressão de espíritos; `validate-node-effects.py` cobre os quatro atributos Malum declarados.
- `EidolonAlchemyPolicyTest` e `EidolonRitualPolicyTest` cobrem conclusão de alchemy/ritual sem premiar tentativa intermediária.
- Auditoria de fechamento: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.
- CI `33132979048` / run #620: testes, validators, NeoForge build e dedicated-server smoke GREEN.

**Acceptance:** satisfied. Ações incompletas/auxiliares não concedem progresso; as ações semanticamente concluídas usam confirmação do provider e concedem a progressão prevista uma vez.