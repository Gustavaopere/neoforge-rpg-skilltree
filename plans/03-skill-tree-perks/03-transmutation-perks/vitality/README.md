# VITALITY — Transmutation Perks

- **Range reservado:** `T3000–T3999`
- **Quota:** vagas T de progressão, iguais às demais árvores; não quantidade de Abilities catalogadas
- **Subslots `.11–.19 / .21–.29 / .31–.39`:** estrutura de escolha da vaga; opções concretas vêm do perfil da Ability vinculada
- **Escopo típico:** robustez corporal, mitigação, sustain físico e tolerância a risco
- **Estrutura editorial mínima por vaga:** 2 LEFT + 2 RIGHT + 3 META vazios até a etapa de auditoria de cada Ability

Abilities de mods diferentes devem passar por adapters; nenhuma árvore pode ficar estruturalmente desfavorecida por possuir menos hooks em um provider específico.

**Arquitetura atual:** `Txxxx` é uma vaga por tier, sem feitiço/skill fixa. Cada Ability tecnicamente elegível recebe **um único perfil** em `../ability-profiles/<provider>/<ability>/` (pasta futura), que a vaga consulta depois de vinculada. Não usar o número T para identificar permanentemente uma Ability. Auditar o provider e a versão instalada antes de criar opções reais.
