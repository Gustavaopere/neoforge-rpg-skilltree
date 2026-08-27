# Catálogo de perks

Este é o inventário exaustivo por família do diretório `data/rpgskilltree/skills/main` na base auditada. Ele cobre **todos os 474 nós materializados** sem duplicar manualmente o payload de cada JSON.

| Família | Nós materializados |
| --- | ---: |
| Arcane | 50 |
| Martial | 40 |
| Vitality | 38 |
| Agility | 36 |
| Engineering | 46 |
| Healing | 34 |
| Logistics | 30 |
| Mining | 36 |
| Occult | 38 |
| Summoning | 34 |
| Survival | 38 |
| Core | 28 |
| Keystones | 16 |
| Bridges | 48 |
| **Total** | **474** |

## IDs

Os arquivos usam IDs data-driven por família (por exemplo `arcane_000`, `martial_000` e equivalentes). O arquivo JSON de cada ID é a autoridade sobre nome de apresentação, requisitos e efeitos.

Este documento deliberadamente não inventa descrições para preencher o gap entre o blueprint histórico de 512 e os 474 nós existentes.

## Por que não copiar 474 efeitos manualmente?

Uma cópia manual ficaria desatualizada assim que um datapack mudasse. O objetivo de hardening é adicionar um gerador que leia os JSON e produza, em CI, um catálogo detalhado com ID, nome, família, custo, requisitos, efeito, parâmetros e integrações. Até isso existir, o inventário acima é completo quanto ao conjunto materializado e os JSON são completos quanto à semântica de cada perk.