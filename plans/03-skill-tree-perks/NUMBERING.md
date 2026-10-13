# IDs editoriais do catálogo

## Famílias

- `I0001..I0006`: seis atributos canônicos globais.
- `Pxxxx`: perk normal específica de árvore.
- `Sxxxx`: perk de especialização; uma árvore owner para contagem, inclusive híbrida.
- `Txxxx`: **vaga de Transmutation da árvore**, com tier/camada; não é ID de spell.
- `Txxxx.11..19`: slots de escolha LEFT.
- `Txxxx.21..29`: slots RIGHT.
- `Txxxx.31..39`: slots METAMORPHOSIS.

`.10`, `.20`, `.30` reservados; sufixos são textuais e não floating point. Slots editoriais T vazios são `RESERVED-UNBOUND`. Após vinculação, os subslots exibem opções do perfil real da Ability sem duplicar conteúdo.

## Blocos dos owners

| Owner | Bloco |
|---|---:|
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

Os prefixos tornam `P5000`, `S5000` e `T5000` distintos. Os blocos são reservas de espaço, **não** quantidade definitiva de perks.

## Identidade das Abilities

O **perfil de transformação** da Ability tem ID próprio, derivado do provider e registry ID canônico, não do slot T. Exemplo de chave conceitual: `irons_spellbooks:chain_lightning`. Uma Ability só tem um perfil global, mesmo que possa ser apresentada em posições elegíveis diferentes.

Durante a fase editorial os códigos dos slots podem ser reorganizados. Depois de expostos em save/runtime publicado, não reutilizar sem migração explícita e testada. O binding exato de ID editorial para `ResourceLocation` é definido na implementação.

O piloto editorial existente `T5000-chain-lightning` ainda associa T5000 a Chain Lightning; **não é contrato definitivo**. Na terceira etapa será convertido em perfil de Ability independente e T5000 será uma vaga normal.
