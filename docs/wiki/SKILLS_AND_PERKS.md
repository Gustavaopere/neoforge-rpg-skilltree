# Skills e perks

## Modelo data-driven

Os nós materializados ficam em `src/main/resources/data/rpgskilltree/skills/main/`. Cada JSON é a fonte canônica para o próprio ID, requisitos e payloads de efeito. Regras auxiliares ficam nos catálogos de node effects/rules.

## Famílias atuais

A auditoria encontrou 474 nós distribuídos entre Arcane, Martial, Vitality, Agility, Engineering, Healing, Logistics, Mining, Occult, Summoning, Survival, Core, Keystones e Bridges.

## Como ler uma perk

Ao documentar ou alterar um nó, verifique:

- ID estável;
- família/árvore;
- custo;
- pré-requisitos;
- requisitos de level/mastery/classe quando existirem;
- efeito e parâmetros;
- compatibilidade opcional;
- reversibilidade no respec.

## Perks cross-mod

Uma perk pode alterar um contrato genérico (por exemplo, dano mágico) ou uma integração nominal. A wiki só nomeia spells/ações específicas quando há handler/tag/adapter que prove essa relação. Isso evita anunciar que “o feitiço X recebe bônus Y” só porque ambos usam magia.

## Fonte canônica

Para os efeitos exatos de qualquer nó, consulte o JSON correspondente. O próximo passo de tooling é gerar automaticamente uma tabela legível desses JSON, preservando a wiki como espelho verificável e não como segunda fonte manual.