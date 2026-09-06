# Reconciliação atual da modlist — Gameplay e Sistemas

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-06.** Esta reconciliação foi refeita contra a `modlist.txt` atual. Dependências `jarjar` embutidas não contam como mods top-level. Quando qualquer capítulo histórico divergir em filename ou versão, esta tabela prevalece.

A `modlist.txt` atual contém **607 entradas top-level**, incluindo o NeoForge modloader. O recorte Gameplay/Sistemas cobre **342 JARs únicos atuais**, com sobreposição cross-domain intencional entre os três guias.

## Delta do snapshot anterior

- Snapshot 30/08/2026: **321 JARs** no recorte Gameplay.
- **1 removido:** Medieval Buildings: The Nether Edition (`medieval_buildings_nether_edition-1.21.1-1.0.2-neoforge.jar`).
- **320** JARs do conjunto anterior continuam instalados.
- **22 módulos novos** foram incorporados em 06/09/2026.
- Recorte atual: **342 JARs**.
- As **21 entradas que já estavam no inventário antigo sem ficha descritiva própria** foram documentadas no capítulo 17; portanto a cobertura descritiva também fecha em **342/342**.

## Bloqueios de startup — mod IDs duplicados

- `alexscaves`: `alexscaves-1.0.9-neoforge+1.21.1.jar` (**Alex's Caves Continued 1.0.9**) e `alexscaves-2.0.2.jar` (**Alex's Caves 2.0.2**) declaram o mesmo mod ID.
- `alexsmobs`: `alexsmobs-1.22.9.jar` (**Alex's Mobs 1.22.9**) e `alexsmobs-2.1.9-neoforge+1.21.1.jar` (**Alex's Mobs Continued 2.1.9**) declaram o mesmo mod ID.
- **BLOQUEIO:** NeoForge exige mod IDs únicos; qualquer um desses pares com os dois JARs presentes impede o jogo de carregar durante discovery. Não existe fallback/fail-closed em runtime para este caso porque o runtime normal não chega a iniciar.
- **Remediação obrigatória:** manter exatamente uma implementação por mod ID ou usar um repack legítimo com ID realmente distinto; depois, regenerar/reconciliar a modlist e o Notion antes de considerar o pack inicializável.

## Tabela canônica exata — 342/342

A tabela foi dividida em sete páginas para manter leitura e revisão viáveis. O conjunto abaixo preserva exatamente `Arquivo JAR | Mod ID | Runtime name | Runtime version` da modlist atual.
- [Registros 001-050](CURRENT-MODLIST-001-050.md)
- [Registros 051-100](CURRENT-MODLIST-051-100.md)
- [Registros 101-150](CURRENT-MODLIST-101-150.md)
- [Registros 151-200](CURRENT-MODLIST-151-200.md)
- [Registros 201-250](CURRENT-MODLIST-201-250.md)
- [Registros 251-300](CURRENT-MODLIST-251-300.md)
- [Registros 301-342](CURRENT-MODLIST-301-342.md)

## Regra operacional

- Este arquivo é authority de presença, JAR e versão.
- Os capítulos são authority descritiva de função, integração, riscos e classificação provider/bridge/library/presentation.
- Biblioteca, UI, visual, compat ou scripting não deve ser promovido automaticamente a provider mecânico.
- As páginas 16 e 17 diferenciam respectivamente **mods novos** e **déficits documentais antigos**.
- O índice de fontes do capítulo 18 fornece navegação para CurseForge/Modrinth/código/wiki quando verificados e buscas seguras quando a origem direta ainda não foi confirmada.
