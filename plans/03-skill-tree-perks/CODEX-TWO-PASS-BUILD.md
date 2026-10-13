# Codex — geração integral do catálogo em duas passagens

## Objetivo

O Codex deve **criar todos os arquivos editoriais de perks antecipadamente**, mesmo antes da catalogação completa de feitiços/skills. Depois ele retorna às vagas de Transmutation e associa Abilities reais, preenche os efeitos e executa a auditoria técnica.

**Criar arquivo não significa perk concluída.** É importante separar vagas reservadas, ideias, Abilities vinculadas, perks aprovadas e perks implementadas.

## Antes de gerar os arquivos

Registrar um orçamento editorial **provisório, igual nas 11 árvores**: `N_standard_provisional`, `N_transmutation_slots_provisional` e `N_specialization_provisional`. Propor os números após comparar a identidade das árvores, sem depender do inventário exaustivo de spells. Não usar automaticamente exemplos numéricos como 100/30/50.

Se o orçamento de alguma família não se sustentar, reajustá-lo para **todas** as árvores, nunca preencher com filler.

## Passagem 1 — geração em massa, sem aguardar todas as Abilities

**Standard Perks:** gerar `02-standard-perks/<tree>/Pxxxx-<slug>.md` nas 11 árvores. Preencher fantasia, efeito conceitual, owner e sinergias; deixar explicitamente não verificados os providers, registries e valores sem evidência. Estado `CONCEPT-DRAFT`.

**Especializações:** propor e criar `04-specialization-perks/<owner-tree>/<especializacao>/README.md` e as `Sxxxx-<slug>.md` respectivas. Puras e híbridas contam em um owner só. Preencher fantasia, requisitos conceituais, Mecânica Assinatura e ideias das perks; não fingir que o provider suporta a mecânica. Estado `CONCEPT-DRAFT`.

**Transmutation:** reservar **todas as vagas** necessárias nas 11 árvores com IDs editoriais T provisórios e **os sete arquivos mínimos já existentes fisicamente**, mas campos ainda desconhecidos vazios:

```text
03-transmutation-perks/arcane/
  T5001-reservado/
    T5001-reservado.md
    T5001.11-pendente.md    LEFT 1
    T5001.12-pendente.md    LEFT 2
    T5001.21-pendente.md    RIGHT 1
    T5001.22-pendente.md    RIGHT 2
    T5001.31-pendente.md    META 1
    T5001.32-pendente.md    META 2
    T5001.33-pendente.md    META 3
```

Cada arquivo reservado deve conter os **campos de seu dossiê** e `Status: RESERVED-UNBOUND`. Campos de Ability, provider, requisitos e efeitos específicos permanecem vazios. Não inventar uma skill, não inventar efeito e não usar um mod não confirmado.

**T5000 — Chain Lightning já existe como piloto conceitual. Preservar seu conteúdo e nunca sobrescrevê-lo com vagas em branco.** O gerador/roteiro deve ler o catálogo existente e recusar colisões.

`RESERVED-UNBOUND` significa **vaga numérica provisória, sem Ability associada**, e NÃO raiz de Transmutation concluída. O ID T editorial reservado não é um runtime ID e pode ser reassociado antes do congelamento.

## Passagem 2 — depois da catalogação suficiente/completa

1. Cruzar `modlist`, `datapacks`, vanilla/projetos próprios e o catálogo de providers do Black Arcana.
2. Comparar Abilities candidatas usando a mesma rubrica para todas as árvores, inclusive não mágicas.
3. Selecionar uma **Ability real e única** para cada vaga: não repetir a mesma Ability em dois `Txxxx`.
4. Renomear `Txxxx-reservado/` para `Txxxx-<ability>/`, preservando o número da vaga quando possível.
5. Preencher a raiz com Ability/provider reais, requisito de desbloqueio e os eixos de LEFT/RIGHT/METAMORPHOSIS.
6. Preencher os arquivos `.11/.12/.21/.22/.31/.32/.33` com opções reais e distintas; criar até 9 opções por grupo quando fizer sentido.
7. Confirmar que só uma opção de cada grupo fica ativa (1 LEFT + 1 RIGHT + 1 META); nunca permitir ativar todas juntas.
8. Revisar afinidades/escolas, alterações de comportamento, sinergias e mecânica da build, sem copiar Diablo automaticamente.
9. Auditar API, registry, hooks, persistência, server/client authority, dependências e fallback; só então promover aprovação e implementar.

O binding pode ser feito por lotes conforme providers individuais forem confirmados. Não é obrigatório esperar a catalogação de todos os outros providers para preencher um já comprovado; o **fechamento global** exige reconciliação completa.

## Contagens e validação

Exibir separadamente, por árvore:

- vagas T reservadas;
- Abilities reais vinculadas;
- Transmutations conceitualmente aprovadas;
- auditadas tecnicamente;
- implementadas.

**Não somar vagas vazias às perks concluídas.** A paridade final compara conteúdo aprovado, não pastas criadas. A validação editorial precisa oferecer modo **estrutura provisória** (aceita reservas com sete arquivos) e modo **catálogo final** (rejeita qualquer `RESERVED-UNBOUND`, ID repetido, Ability duplicada, provider ausente ou falta de opções 2/2/3).

## Trabalho que continua aberto

Números finais das quotas, roster de Abilities, especializações específicas e integração técnica serão definidos durante a execução, não inventados pelo template. Este documento decide **a ordem e a estratégia de geração pelo Codex**, não substitui os contratos de conteúdo.
