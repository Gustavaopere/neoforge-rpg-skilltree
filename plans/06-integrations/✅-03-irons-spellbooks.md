# Integrations Complete — Iron's Spells 'n Spellbooks

**Goal:** conectar casts, mastery, identidade Mage, escolas e atributos do Iron's ao RPG.

- [x] Confirmar cast válido de spellbook/scroll e cancelamentos.
- [x] Alimentar `irons:casting` uma vez por cast válido.
- [x] Manter requisitos de Mage alinhados aos dados.
- [x] Resolver max mana, mana regen, spell power, cooldown e cast time.
- [x] Suportar atributos de escola usados por perks.
- [x] Não implementar candidatos históricos sem contrato atual comprovado.

## Runtime contract

- `IronsSpellbookProgressionEvents` usa o evento confirmado de cast e só contabiliza mastery para fontes reconhecidas de spellbook/scroll; gating anterior ao cast não concede progresso por tentativa cancelada.
- O lane canônico de prática é `irons:casting`; a identidade Mage continua definida por dados como `rpgskilltree:arcane_000` + mastery mínima 60.
- Os node-effect packs resolvem atributos reais do Iron's usados pelo RPG: max mana, mana regen, spell power, cooldown reduction, cast time reduction, summon damage e spell powers por escola quando declarados.
- A integração permanece data/attribute-driven: nomes históricos de perks/spells não são tratados como implementação sem handler ou efeito atual correspondente.

## Verification

- `ProviderIdentityClassTest` cobre a identidade Mage no threshold e a coexistência com Sorcerer sem transformar posse de item em mastery.
- Os validators de node effects verificam os efeitos Iron's atualmente materializados.
- O adapter compila contra Iron's 3.16.3 no alvo NeoForge 1.21.1.
- Auditoria de fechamento: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.
- CI `33132979048` / run #620: Core tests, node-effect validation, NeoForge build e dedicated-server smoke GREEN.

**Acceptance:** satisfied. Casts confirmados alimentam a progressão uma vez e os atributos do Iron's respondem aos efeitos atuais da build sem regras nominais inventadas.