# Skill Tree Plan — fechamento do novo catálogo e wiki

Este plano é downstream do [`00-EXECUTION-ROADMAP.md`](./00-EXECUTION-ROADMAP.md).

## Pré-requisitos

- [ ] Modlist física atual reconciliada.
- [ ] Matriz de capacidades do pack concluída, incluindo vanilla.
- [ ] Capacidades distribuídas nas 11 árvores.
- [ ] Especializações e suas mecânicas assinatura reconstruídas do zero.
- [ ] Catálogo conceitual de Standard, Transmutation e Specialization Perks revisado.

## Fechamento

- [ ] Congelar `N_standard_per_tree`.
- [ ] Congelar `N_transmutation_per_tree`.
- [ ] Congelar `N_specialization_per_tree`.
- [ ] Decidir e congelar `K_specializations_per_tree` e `M_perks_per_specialization` se a matriz confirmar a simetria sem filler.
- [ ] Auditar tecnicamente providers.
- [ ] Fechar matriz de Affinity/School para conversões.
- [ ] Completar dossiês individuais.
- [ ] Definir binding editorial -> runtime ID.
- [ ] Migrar geradores/validators.
- [ ] Implementar/testar exclusividade intragrupo e coexistência LEFT+RIGHT+METAMORPHOSIS.
- [ ] Implementar/testar mecânicas assinatura.
- [ ] Gerar wiki e drift gate.
- [ ] Só então desenhar topologia visual final.

A infraestrutura de `✅-01` a `✅-05` deve ser reaproveitada onde continuar compatível. Contratos específicos da antiga malha 512/A-series precisam ser reavaliados.

## Acceptance

- uma única authority de modlist;
- quotas globais iguais entre as 11 árvores;
- uma única raiz de Transmutation por Ability;
- exatamente uma escolha ativa por grupo de Transmutation;
- especializações temáticas com mecânicas assinatura;
- conversão de Affinity semanticamente completa ou fail-closed;
- runtime IDs estáveis;
- documentação gerável;
- nenhuma perk ativa dependente de provider removido ou identidade editorial antiga.
