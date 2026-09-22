# T5000 — Chain Lightning

- **Família:** Transmutation Perk
- **Owner tree:** ARCANE
- **Ability alvo:** `irons_spellbooks:chain_lightning` (binding final a confirmar na implementação)
- **Provider:** Iron's Spells 'n Spellbooks
- **Requisito de compra:** Chain Lightning conhecida/desbloqueada
- **Não basta:** estar apenas equipada no spellbook/action bar
- **Status:** piloto de arquitetura

## Estrutura

### Ramo esquerdo — potência
- [T5000.1 — Cadeia Adicional](./T5000.1-cadeia-adicional.md)
- [T5000.2 — Primeiro Impacto](./T5000.2-primeiro-impacto.md)

### Ramo direito — forma/eficiência
- [T5000.3 — Alcance de Condução](./T5000.3-alcance-de-conducao.md)
- [T5000.4 — Condução Econômica](./T5000.4-conducao-economica.md)

### Metamorfoses
- [T5000.5 — Cadeia Frenética](./T5000.5-cadeia-frenetica.md)
- [T5000.6 — Fluxo de Retorno](./T5000.6-fluxo-de-retorno.md)
- [T5000.7 — Transmutação Elemental](./T5000.7-transmutacao-elemental.md)

## Referência de design

A versão atual de Diablo IV organiza Chain Lightning em grupos 2/2/3, incluindo Additional Chains, Damage Bonus e três alterações maiores: Chain Whipping, Power Flux e Chain of Cold. O nosso desenho usa a mesma ideia estrutural, mas adapta comportamento e números à Ability real do Iron's.

A referência correta é **Chain of Cold**: em Diablo IV Chain Lightning se torna Frost/Cold, não Fire.

## Integração

A checagem de conhecimento deve usar `AbilityKnowledgeProvider`. Spell Codex pode ser o adapter preferencial para spells persistentes descobertos; qualquer fallback direto no Iron's precisa ser auditado contra a versão instalada. A Action Bar não é fonte de verdade de conhecimento por si só.
