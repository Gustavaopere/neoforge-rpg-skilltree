# Especializações — Mecânicas Assinatura e perks de categoria

## Identidade

Especialização é ramificação PURE ou HYBRID (pode unir várias árvores, mas conta para um owner). Não é sinônimo de mod. Cada uma precisa de uma **Mecânica Assinatura** que cria decisões de build, e perks S que aprofundam seu uso.

Padrões de inspiração: escolher espíritos/afinidades, papéis ou sacrifício de summons, captura/selamento, posturas, juramentos, contratos, disciplinas de combate ou engenharia. Não copiar Diablo literalmente nem limitar identidade apenas a magia.

## Fronteira obrigatória P × S × T

- P: sistemas, atributos, recursos e efeitos gerais sem ser uma metamorfose individual.
- S: efeitos **gerais, porém condicionais** a categorias semânticas; devem funcionar com **todas as Abilities realmente classificadas e compatíveis**, inclusive de diferentes providers.
- T: efeitos **específicos da Ability** (Fireball, Shield, weapon skill etc.) desenvolvidos após auditoria individual.

Exemplo de S conceitual: "Quando um acerto de um projétil de fogo de alvo único ocorre, parte do efeito pode atingir alvos próximos." O predicado de S deve usar facts verificadas `fire + projectile + single_target + direct_hit`. Alcance "5 blocos" é **somente exemplo**, não valor aprovado. Não presumir que um efeito de fogo em área, um DoT ou aura são alvo único.

## Mapeamento de providers

Os mods podem não ter tags universais. Criar adapter/catálogo de `NormalizedAbilityFacts` para school/affinity, delivery, targeting, effect, owner, attribution e origem causal **com evidência da versão instalada**. Classificação ausente não é autorização para aplicar efeito por nome de spell.

Quando T converte Fireball para outra afinidade, S deve avaliar os **facts efetivos** pós-transformação, nunca aplicar simultaneamente a classificação de escola antiga e nova sem regra explícita.

## Segurança dos efeitos generalistas

- Server authoritative; não inferir gameplay de partículas client.
- Toda propagação ou proc marca sua origem: o proc gerado **não pode se disparar recursivamente**.
- Preservar pipeline nativo de dano/cura, resistência, friendly-fire, PvP, summons e ownership; evitar segunda rolagem de crítico ou mana/cooldown.
- Limites/cooldowns/alvos e stack de sinergias são definidos na perk com power budget e hooks reais.
- Sem hook/classificação verificada no provider exato, a integração específica fica `TECH-BLOCKED` ou fail-closed, não vira +5% genérico.

## Dossiês e paridade

Pastas de especialização em `04-specialization-perks/<owner>/<slug>/`, com `README.md` contendo PURE/HYBRID, owners/requisitos, Mecânica Assinatura e providers; os `Sxxxx` detalham efeitos, custos, ranks e **mínimos de atributo**.

Mesmo total `N_specialization_per_tree` para cada owner. Uma híbrida não conta duas vezes; níveis/atributos/class gates podem criar builds distintas sem criar quotas desiguais.
