# Skill Tree Plan — fechamento do novo catálogo e wiki

Este plano é downstream do [`00-EXECUTION-ROADMAP.md`](./00-EXECUTION-ROADMAP.md).

## Pré-requisitos

- [ ] Modlist física atual reconciliada.
- [ ] Matriz de capacidades do pack concluída, incluindo vanilla.
- [ ] Capacidades distribuídas nas 11 árvores.
- [ ] Especializações reconstruídas do zero.
- [ ] Catálogo conceitual de Standard, Transmutation e Specialization Perks revisado.

## Fechamento

- [ ] Congelar `N_standard_per_tree`.
- [ ] Congelar `N_transmutation_per_tree`.
- [ ] Congelar `K_specializations_per_tree`.
- [ ] Congelar `M_perks_per_specialization`.
- [ ] Auditar tecnicamente os providers das perks aprovadas.
- [ ] Completar os dossiês individuais.
- [ ] Definir binding editorial -> runtime ID.
- [ ] Migrar geradores/validators.
- [ ] Implementar e testar stacking/conflitos de `Txxxx.n`.
- [ ] Gerar wiki e drift gate.
- [ ] Só então desenhar a topologia visual final.

A infraestrutura de `✅-01` a `✅-05` deve ser reaproveitada onde continuar compatível. Contratos específicos da antiga malha 512/A-series precisam ser reavaliados.

## Acceptance

- uma única authority de modlist;
- quotas globais iguais entre as 11 árvores;
- especializações temáticas, não derivadas automaticamente de mods;
- modificadores de Transmutation acumuláveis por padrão;
- runtime IDs estáveis;
- documentação gerável;
- nenhuma perk ativa dependente de provider removido ou identidade editorial antiga.
