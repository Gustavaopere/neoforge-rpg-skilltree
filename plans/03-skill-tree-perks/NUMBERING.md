# Convenção de numeração

## Prefixos

- `Ixxxx` — Internal Attribute.
- `Pxxxx` — Standard Perk.
- `Txxxx` — Transmutation Perk.
- `Txxxx.n` — modificador filho de uma Transmutation Perk.
- `Sxxxx` — Specialization Perk.

A antiga série `A####` não é reutilizada neste catálogo.

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

O mesmo bloco numérico é reutilizado entre famílias porque o prefixo faz parte da identidade. Ex.: `P5000`, `T5000` e `S5000` são três códigos distintos e todos pertencem a ARCANE.

## Filhos de transmutação

Exemplo:

- `T5000` — transmutação-base.
- `T5000.1`, `T5000.2` — opções do ramo esquerdo.
- `T5000.3`, `T5000.4` — opções do ramo direito.
- `T5000.5`, `T5000.6`, `T5000.7` — metamorfoses mínimas.

O sufixo após o ponto é um **sub-ID textual**, não um número de ponto flutuante. Portanto `.10` nunca deve ser interpretado como equivalente a `.1`.

## Runtime IDs

Os códigos editoriais não precisam ser o `ResourceLocation` runtime. O binding final deve usar um ID namespaced estável e seguro para save/datapack. A conversão código editorial → runtime ID será definida quando o novo catálogo entrar na implementação.

## Estabilidade

Durante a fase puramente editorial os códigos podem ser reorganizados antes do freeze. Depois que um código entrar em save/runtime publicado, sua identidade não deve ser reciclada silenciosamente.
