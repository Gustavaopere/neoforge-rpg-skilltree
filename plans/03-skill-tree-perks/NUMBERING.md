# Convenção de numeração

## Prefixos

- `Ixxxx` — Internal Attribute.
- `Pxxxx` — Standard Perk.
- `Txxxx` — raiz de Transmutation de uma Ability.
- `Txxxx.n` — modificador filho daquela mesma Ability.
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

`Txxxx` identifica **uma Ability única em todo o catálogo**, enquanto o sufixo identifica seus modificadores.

Exemplo:

- `T5000` — Chain Lightning;
- `T5000.1` — modificador 1 de Chain Lightning;
- `T5000.2` — modificador 2 de Chain Lightning;
- ...
- `T5000.7` — modificador 7 de Chain Lightning.

Se futuramente Chain Lightning ganhar mais metamorfoses ou outros modificadores, continuam sendo filhos da mesma raiz:

- `T5000.8`;
- `T5000.9`;
- `T5000.10`;
- etc.

Chain Lightning **não recebe outro `Txxxx` mais adiante**.

`T5001` é reservado para outra Ability de ARCANE.

Logo, efeitos como “+1 alvo”, “primeiro alvo recebe bônus” e “converter afinidade” pertencem a `T5000.n` e podem ser usados juntos quando compatíveis.

## Faixas editoriais dos filhos

A estrutura mínima inicial é:

- `.1`, `.2` — potência/impacto;
- `.3`, `.4` — forma/eficiência;
- a partir de `.5` — pelo menos 3 metamorfoses.

As metamorfoses não terminam obrigatoriamente em `.7`. `.8`, `.9`, `.10` e seguintes podem continuar a mesma raiz quando houver ideias distintas que mereçam existir.

A numeração organiza leitura e arquivos. Ela não cria exclusividade.

O sufixo após o ponto é um **sub-ID textual**, não ponto flutuante. `.10` nunca equivale a `.1`.

## Runtime IDs

Os códigos editoriais não precisam ser o `ResourceLocation` runtime. O binding final deve usar ID namespaced estável e seguro para save/datapack.

## Estabilidade

Durante a fase editorial os códigos podem ser reorganizados antes do freeze. Depois que um código entrar em save/runtime publicado, sua identidade não deve ser reciclada silenciosamente.
