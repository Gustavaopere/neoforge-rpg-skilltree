# Contrato de Especializações e Mecânicas Assinatura

## 1. Definição

Uma especialização é uma ramificação temática de uma das 11 árvores principais.

Ela possui duas camadas diferentes:

1. **Specialization Perks** — os nós `Sxxxx` que constroem e ampliam a build.
2. **Signature Mechanic / Mecânica Assinatura** — o sistema próprio de decisões que dá identidade à especialização.

Uma especialização não deve existir apenas porque um mod possui uma escola, arma, recurso ou subsystem.

## 2. Critério de aprovação

Uma candidata só vira especialização se:

- representar fantasia de build distinguível;
- tiver capacidades reais no pack que a sustentem;
- não for apenas duplicação de outra especialização;
- conseguir sustentar uma mecânica assinatura coerente;
- possuir espaço suficiente para perks relevantes sem filler;
- não exigir que um único mod opcional seja tratado como dono de toda a fantasia.

## 3. Inspiração de mecânicas

O projeto pode estudar sistemas externos para aprender padrões de decisão.

Padrões particularmente úteis:

### Afinidade
Escolher um espírito, patrono, escola, doutrina ou afinidade que altera tags, bonuses e comportamento de um conjunto de capacidades.

### Roster configurável
Cada summon/companion pode receber uma função. O jogador pode optar por manter a unidade, mudar sua função ou converter parte de sua presença em um benefício pessoal.

### Caça, captura ou selamento
Alvos especiais podem ser caçados, marcados, capturados, aprisionados ou selados para desbloquear escolhas persistentes, troféus funcionais, poderes ou relações de progressão.

### Juramento / postura / contrato
O jogador escolhe regras persistentes com vantagens e custos que alteram prioridades da build.

Esses padrões não são templates obrigatórios. Cada especialização deve ser construída a partir dos sistemas reais do modpack.

## 4. Relação com providers

Uma especialização pode usar vários providers.

Exemplo abstrato:

`Piromante = spells + armas + efeitos + itens + vanilla + sistemas próprios compatíveis com a fantasia de fogo`.

Não criar automaticamente:
- especialização por mod;
- especialização por addon;
- especialização por cada school;
- especialização por categoria técnica.

## 5. Paridade

Cada árvore terá o mesmo total `N_specialization_per_tree`.

O objetivo preferencial é também ter o mesmo número de especializações por árvore e o mesmo número de perks por especialização, mas isso é um parâmetro de design a validar após a matriz de capacidades.

Cada especialização deve possuir **uma mecânica assinatura principal** de profundidade comparável. Isso não significa interfaces idênticas ou o mesmo número de subopções.

## 6. Mecânica assinatura não é perk comum

A mecânica assinatura pode ter UI, estado persistente, seleção, ritual, roster ou outro modelo próprio.

As `Sxxxx` devem:
- desbloquear partes da mecânica;
- ampliar opções;
- especializar escolhas;
- melhorar sinergias;
- criar tradeoffs;
- ou ligar a mecânica a outras capacidades da build.

Evitar uma subárvore de passivos que não conversa com a mecânica assinatura.

## 7. Momento de implementação

A mecânica assinatura é desenhada conceitualmente junto da especialização, mas API, persistência, UI e hooks só são fechados depois da auditoria dos providers.

Se a fantasia for boa, mas o provider não oferecer boundary seguro, a integração específica fica fail-closed; a especialização não recebe um bônus genérico falso para esconder a limitação.
