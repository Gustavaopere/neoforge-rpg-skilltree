# Contrato — vagas T, perfis de Ability e classificação transversal

## Três identidades independentes

**Tree Slot:** `Txxxx` é a **vaga de progressão** na árvore e seu owner/tier, não uma Ability fixa. A estrutura mínima `Txxxx.11/.12/.21/.22/.31/.32/.33` é reservada editorialmente na primeira passagem. A descrição da vaga não contém nomes de spells.

**Ability Profile:** definição de transformação **única para cada Ability**, indexada pelo provider + registry ID real (ou chave canônica equivalente do sistema). Exemplo de estrutura proposta: `03-transmutation-perks/ability-profiles/irons_spellbooks/chain_lightning/README.md` e `left-1.md`, `right-1.md`, `metamorphosis-1.md` etc. O ID da opção do perfil é diferente do ID da vaga. Ao vincular uma Ability, a UI da vaga **projeta** as escolhas 1/2/3 daquele perfil, sem duplicar dossiers.

**Player Binding:** estado server-authoritative `player → slotId → abilityId → escolha LEFT/RIGHT/META`, persistido e sujeito a conhecimento da Ability, tier e owner/arquétipo. **Interface proposta, não código existente.**

O perfil é um para cada Ability; diferentes jogadores podem associá-la a slots elegíveis distintos sem duplicar o perfil global. Regras de repetição na mesma ficha de personagem e de reassociação são produto da fase de implementação.

## Vagas por progressão

A sequência `T5000, T5001, ...` representa posição editorial dentro de ARCANE; colocação em tier inicial/médio/final é definida por metadata explícita, **não inferida do número**. A menção a “vaga 30” descreve posição de progressão e não fixa a quantidade definitiva de slots.

**Não equiparar**:
- nível atual de uma habilidade;
- nível máximo da habilidade;
- raridade dela;
- nível de personagem;
- mastery;
- camada/tier da árvore.

A elegibilidade precisa mapear metadados de cada provider para um **tier normalizado e auditável**. Skills single-level lendárias podem pertencer ao fim da árvore; skills de nível 10 não precisam esperar todos os ranks para poder transmutar.

Sem metadata confiável, o perfil permanece `UNCLASSIFIED` e não pode ser vinculado automaticamente a uma vaga.

## Especializações operam sobre classificações, não spells nomeadas

Propor `NormalizedAbilityFacts` (metadata/adapter do RPG Skill Tree) com campos semânticos versionados a confirmar em cada provider:

- `origin`: mod/provider e habilidade/ação real;
- `school/affinity`: Fire, Ice, Holy, Ender, Blood etc. quando verdadeiramente aplicável;
- `delivery`: projectile, beam, instant, aura, melee, summon, channel, structure, process;
- `targeting`: single_target, multi_target, self, ground, area;
- `effect`: direct_damage, damage_over_time, heal, shield, mobility, control, utility, crafting;
- `causality`: caster/owner, direct vs induced event, source kind e filtros de alvo;
- `resources`: mana, stamina, cooldown, energy ou outros já presentes no provider.

São **tags normalizadas propostas**, não afirmação de que Iron's, Epic Fight e outros mods compartilham essas tags nativamente. Um adapter só pode atribuir uma tag quando há evidência do comportamento real daquela release.

### Exemplo de especialização

`Sxxxx — Propagação Incandescente`:
- aplica-se somente quando facts comprovados incluem `fire + projectile + single_target + direct_damage`;
- produz efeito secundário condicionado ao evento causal original, com distância ilustrativa de 5 blocos **não aprovada como balanceamento**;
- efeito gerado marca provenance de proc e **não desencadeia a mesma perk novamente**;
- preserva damage attribution, tags/PvP/friendly-fire e o pipeline do provider;
- sem facts suficientes ou hook seguro, efeito não se aplica (fail-closed).

O mesmo conceito deve cobrir abilities de vários providers **somente onde adapters realmente as classificarem**, inclusive técnicas não mágicas com dano de fogo; não fazer suposição por texto do nome do spell.

## Transmutation aplica-se à Ability em si

Exemplo conceitual de um perfil Fireball:
- LEFT: tamanho ou outra modificação menor de forma;
- RIGHT: quantidade/lançamento ou outro aspecto independente;
- META: conversão para fogo das almas, com comportamento de dano/status/afinidade/VFX coerente **quando suportada**.

Um perfil de Shield do Iron's 3.16.3 deve partir do fato documentado: barreira estacionária de HP que bloqueia projéteis e movimentação de criaturas; duração máxima, dimensões, movimento e ricochete **não estão verificados** no catálogo consultado. Ideias de barreira móvel ou refletora ficam `DESIGN-CANDIDATE/TECH-BLOCKED` até confirmar implementação segura. A perk não pode assumir que o escudo segue o jogador por padrão.

## Convivência entre Standard, Specialization e Transmutation

- P afeta categoria global/recurso/sistema, não cria segunda customização individual para a Ability.
- S é modificação de **família classificada**, com alcance amplo mas condições rigorosas e uma Mecânica Assinatura da build.
- T modifica **uma Ability específica**, mediada por seu perfil; seus efeitos se combinam com S quando legal e sem duplo processamento.
- Quando Transmutation muda a escola/afinidade da Ability, as facts efetivas para S devem refletir a escola **real após transformação**, evitando que um ataque convertido continue acionando bônus indevidos da escola original.
- A ordem `ability base → transformação T → facts efetivas → filtros S → efeito` é o modelo semântico alvo, **não prova de ordem de eventos disponível no provider**. A auditoria precisa validar ou bloquear caso não haja boundary apropriado.

## Gate de cobertura

Avaliar cada Ability do catálogo relevante, inclusive não mágicas:
- `ELIGIBLE`: mínimo 2/2/3 opções não redundantes + hooks viáveis;
- `DESIGN-CANDIDATE`: possibilidades interessantes, integração ainda não provada;
- `BLOCKED`: falta de boundary seguro/incompatibilidade;
- `UNSUPPORTED`: impossível ou sem espaço de transformação útil; motivo concreto.

“Cobrir todas” significa **examinar todas e oferecer perfil às elegíveis**, não garantir alterações impossíveis nem preencher com bônus falso.
