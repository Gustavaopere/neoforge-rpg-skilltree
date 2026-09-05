# A0193 — Resistência a Sangue I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL.**

A classificação defensiva BLOOD possui boundary e identidade suficientemente seguros no snapshot atual. A0193 deve entrar na mesma pipeline compartilhada de mitigação elemental, sem listener/reducer paralelo.

Notion revalidado: `https://app.notion.com/p/3c569db9f0db81899788da98154cc197`.

## Contrato

- VITALITY; camada 4; Ponte; até 4 ranks; 1 PP/rank.
- Dependência: A0191 ≥1 **OU** Gateway VITALITY.
- Como A0191 permanece unavailable, o caminho comprável atual é Gateway VITALITY.
- Contribuição ao bucket único `RPG_BLOOD_RESISTANCE`:
  - rank 1: +4%;
  - rank 2: +8%;
  - rank 3: +12%;
  - rank 4: +16%.
- 16% é o teto próprio de A0193, não um cap defensivo global.

## Classifier aprovado

Iron's Spells 'n Spellbooks 3.16.3:

- `SchoolRegistry` possui school `blood`;
- `ISSDamageTypes.BLOOD_MAGIC` é a identidade de dano BLOOD do provider;
- adapter/classifier deve reconhecer exatamente a identidade/tag semântica correspondente do provider.

Outros providers somente entram por identidade/tag semântica ou adapter explicitamente versionado e auditado.

## Boundary / authority

Usar `LivingDamageEvent.Pre` server-side e o mesmo `ElementalDamageMitigationResolver` compartilhado pelas famílias FIRE/ICE/LIGHTNING/NATURE/HOLY.

Pipeline:

`DamageSource → classifier BLOOD exato → coletar contribuições RPG_BLOOD_RESISTANCE → compor uma vez → uma única mutação do dano no Pre`.

Adapters classificam; não aplicam mitigação por conta própria.

## Exclusões

Não são BLOOD mágico por associação temática:

- bleed físico;
- lifesteal;
- dano/efeito do Vampirism;
- blood meter/custo de sangue;
- self-damage ou sacrifício voluntário;
- dano físico de arma com estética sanguínea;
- nome, partícula, namespace ou lore.

Se uma fonte não puder ser classificada com segurança, A0193 simplesmente não se aplica àquela fonte.

## Deduplicação / composição

- um evento/root recebe o bucket uma única vez;
- A0193 não cria novo DamageSource;
- classifiers externos não adicionam reducers independentes;
- composição com outras resistências segue a pipeline defensiva global existente; o 16% local não redefine cap global.

## Handoff Chat 2

Implementar A0193 no `ElementalDamageMitigationResolver` compartilhado, bucket `RPG_BLOOD_RESISTANCE`, usando o classifier exato Iron's 3.16.3. Não criar listener ou atributo de resistência paralelo.

## Testes obrigatórios para Chat 3

1. dependência A0191≥1 OU Gateway VITALITY;
2. com A0191 unavailable, Gateway VITALITY permite o caminho de purchase aprovado;
3. ranks 0–4 = 0/4/8/12/16%;
4. Iron's `blood_magic` positivo;
5. magic não-BLOOD negativo;
6. generic bleed negativo;
7. Vampirism/lifesteal/blood resource negativo por padrão;
8. self-damage/custo voluntário não ganha mitigação por inferência;
9. uma única mutação por evento/root;
10. ausência/version mismatch do provider falha fechado sem crash;
11. 16% local não atua como cap global;
12. reload/login/dedicated server preservam o classifier/resolver único.