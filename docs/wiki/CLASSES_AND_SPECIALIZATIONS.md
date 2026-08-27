# Classes, masteries e especializações

## Classes emergentes

O desenho unificado trabalha com identidades como Mage, Warrior, Ranger, Tank, Artificer e Summoner, além de híbridos como Spellblade, Battlemage, Arcane Archer e Technomancer. A fonte final de requisitos é o conteúdo carregado em `data/rpgskilltree/classes` e os serviços runtime correspondentes.

## Masteries

Mastery é ganho por uso real, não apenas por possuir item. Entre os domínios históricos/canônicos estão práticas de magia, projéteis, combate e sistemas externos. Cada adapter deve mapear eventos do mod para uma ação semântica única.

## Especializações

As definições vivem em `data/rpgskilltree/specializations`. Elas podem representar caminhos internos ou progressão relacionada a outros mods.

### Atenção a Create e AE2

Há definições/especificações de progressão e especialização associadas a Create e Applied Energistics 2, mas esta auditoria não comprovou um adapter runtime dedicado equivalente aos adapters de Iron's/Ars/Goety/Malum/Eidolon. Por isso o estado correto é `SPEC/DATA`, não `IMPLEMENTED`.

## Gateways híbridos

Uma feature externa pode exigir simultaneamente mastery e identidade de classe. Iron's, por exemplo, possui gating que considera progressão Arcana/mastery e identidade de Mage para autorização de certas ações de inscrição permanente.