# Convenção de numeração

## Prefixos

- `Ixxxx` — Internal Attribute.
- `Pxxxx` — Standard Perk.
- `Txxxx` — raiz de Transmutation de uma Ability.
- `Txxxx.xx` — opção de um dos três grupos da mesma Ability.
- `Sxxxx` — Specialization Perk.

A antiga série `A####` não é reutilizada.

## Blocos por árvore

| Árvore | Bloco |
| --- | ---: |
| MARTIAL | 1000–1999 |
| AGILITY | 2000–2999 |
| VITALITY | 3000–3999 |
| HEALING | 4000–4999 |
| ARCANE | 5000–5999 |
| ENGINEERING | 6000–6999 |
| MINING | 7000–7999 |
| SURVIVAL | 8000–8999 |
| SUMMONING | 9000–9999 |
| OCCULT | 10000–10999 |
| LOGISTICS | 11000–11999 |

O prefixo faz parte da identidade. `P5000`, `T5000` e `S5000` são códigos distintos do domínio ARCANE.

## Regra fundamental das Transmutations

`Txxxx` identifica **uma Ability única em todo o catálogo**.

O sufixo de dois dígitos identifica grupo e opção:

- primeiro dígito = grupo;
- segundo dígito = índice da opção, de 1 a 9.

### Grupo 1 — esquerda

- `T5000.11`
- `T5000.12`
- ...
- `T5000.19`

### Grupo 2 — direita

- `T5000.21`
- `T5000.22`
- ...
- `T5000.29`

### Grupo 3 — metamorfose

- `T5000.31`
- `T5000.32`
- ...
- `T5000.39`

Os IDs `.10`, `.20` e `.30` não representam opções e ficam reservados.

Não existem opções acima de 9 dentro de um grupo. Portanto `.19`, `.29` e `.39` são os limites.

## Exemplo

```
T5000 — Chain Lightning

LEFT
T5000.11
T5000.12

RIGHT
T5000.21
T5000.22

METAMORPHOSIS
T5000.31
T5000.32
T5000.33
```

Uma configuração pode ter `T5000.11 + T5000.22 + T5000.33`, pois são grupos diferentes.

Não pode ter `T5000.11 + T5000.12` simultaneamente.

Se Chain Lightning ganhar mais opções no futuro, elas precisam ocupar slots livres do grupo correspondente. Chain Lightning **não recebe outro `Txxxx` mais adiante**.

`T5001` é reservado para outra Ability de ARCANE.

## Natureza textual do ID

O sufixo é **textual**, não um decimal numérico. `T5000.11` deve ser tratado como código estruturado, não como número de ponto flutuante.

## Runtime IDs

Os códigos editoriais não precisam ser o `ResourceLocation` runtime. O binding final deve usar ID namespaced estável e seguro para save/datapack.

## Estabilidade

Durante a fase editorial os códigos podem ser reorganizados antes do freeze. Depois que um código entrar em save/runtime publicado, sua identidade não deve ser reciclada silenciosamente.
