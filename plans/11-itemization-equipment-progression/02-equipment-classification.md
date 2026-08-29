# 11.02 — Classificação universal de equipamentos

## Objetivo

Descobrir se um item é elegível e quais categorias funcionais possui sem depender de uma lista eterna de classes ou mod IDs.

## Passo a passo

### A — Modelo de categorias

Planejar categorias composáveis, no mínimo:

- melee: espada, machado, lança, martelo/pesada e genérico;
- ranged: arco, besta, arma de projétil e genérico;
- magic: cajado, spellbook/focus e equipamento mágico;
- armor: cabeça, torso, pernas, pés e genérico;
- utility: escudo/offhand, ferramenta, mineração/agricultura;
- mobility: jetpack, planador, gancho;
- wearable: Curios/anel/colar/amuleto/outros slots;
- technology: equipamento energético/mecânico;
- fallback: `GENERIC_EQUIPMENT`.

Um item pode possuir múltiplas categorias.

### B — Pipeline de classificação

Ordem proposta:

1. overrides data-driven por item/tag;
2. adapter explícito de mod quando necessário;
3. tags/registries/capacidades e componentes do item;
4. atributos/equipment slot e sinais estruturais seguros;
5. fallback conservador.

Nunca carregar classe opcional apenas para classificar.

### C — Elegibilidade

- [ ] definir critérios para item durável/equipável/utilizável como equipamento;
- [ ] permitir whitelist/blacklist data-driven;
- [ ] excluir consumíveis/blocos comuns mesmo que possuam componentes incidentais;
- [ ] permitir adapters para itens não convencionais de mods.

### D — Diagnóstico de cobertura

Gerar relatório que mostre:

- itens classificados;
- categorias atribuídas;
- fallback genérico;
- itens potencialmente equipáveis ignorados;
- provider responsável pela classificação.

### E — Pools por categoria

A classificação alimenta o Stage 11.05. Um modificador declara categorias permitidas/proibidas; o classificador não conhece balanceamento de afixos.

## Testes previstos

- vanilla weapons/tools/armor/shield;
- item multi-categoria;
- Curios quando presente e ausência do mod;
- item desconhecido que cai no fallback;
- blacklist/whitelist por datapack;
- ausência de `ClassNotFoundException` com mods opcionais removidos.

## Acceptance

O subplano fecha quando qualquer equipamento conhecido ou desconhecido obtém classificação determinística, diagnosticável e extensível, sem hard dependency indevida e sem exigir manutenção manual para cada novo item do modpack.
