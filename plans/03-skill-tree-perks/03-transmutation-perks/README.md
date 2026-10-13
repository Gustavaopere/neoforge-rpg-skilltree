# 03 — Transmutation Perks (trabalho 3, último, do Codex)

**Txxxx é a vaga da árvore, não um spell fixo.** Cada árvore possui o mesmo número de vagas distribuídas por tiers. O Codex cria inicialmente `Txxxx-reservado/` com sete arquivos e campos vazios, status `RESERVED-UNBOUND`.

Cada vaga tem os grupos:
- LEFT: `.11-.19` — escolher 1;
- RIGHT: `.21-.29` — escolher 1;
- METAMORPHOSIS: `.31-.39` — escolher 1.

Mínimo de 2 LEFT + 2 RIGHT + 3 META, até 9 por grupo, se aplica ao **perfil de Ability concluído**, não a uma vaga ainda vazia.

Depois de catalogar os providers, desenvolver um **perfil por Ability elegível** em `ability-profiles/<provider>/<ability>/` (pasta proposta, ainda não criada); avaliar também skills não mágicas. Uma vaga equipada mostra escolhas derivadas desse perfil, sem clonar o conteúdo em cada Txxxx. Vagas limitam quantas Abilities podem ser transmutadas na build; os perfis cobrem as que forem tecnicamente elegíveis.

Uma Ability de nível máximo 1 não é necessariamente inicial; tiers devem considerar raridade, requisitos e poder além do maxLevel.

A pasta existente `arcane/T5000-chain-lightning` é **piloto antigo de um perfil**, deverá ser migrada na etapa 3. Não é prova de que T5000 continuará fixamente associado a Chain Lightning.

[Regras de slots e tags](../ABILITY-SLOTS-AND-TAGS.md) · [Execução Codex](../CODEX-FOUR-STAGES.md)
