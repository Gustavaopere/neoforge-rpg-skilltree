# BCLib: New Dawn — 21.0.29 (versão instalada declarada em 22/11/2026)

> **Estado vigente — declaração do usuário de 22/11/2026:** `21.0.26` → **`21.0.29`**, NeoForge 1.21.1; JAR oficialmente publicado `bclib-21.0.29.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/bclib-neoforge/files/9511338)). Arquivo real local, SHA e metadata não relidos. Posição física histórica #066.

> **Auditoria:** Auditoria histórica cobre 21.0.27 (saplings com BehaviourSapling compostáveis), 21.0.28 (sincronização do anvil, VoxelShapes de plantas de parede, bounds de estruturas e arrays de random offsets) e 21.0.29 (corrige setas ausentes no anvil quando há múltiplas receitas com os mesmos ingredientes). Não atribuir a 21.0.29 mudanças da 21.0.28.

> **Dependências:** BCLib é base comum de BetterEnd/BetterNether; WorldWeaver e WunderLib possuem responsabilidades complementares. Requisitos exatos de consumidores devem ser conciliados com metadata do JAR distribuído.

> **Riscos:** Alterações em GUI/anvil, structures/worldgen e dados compartilhados BetterX; testar receitas concorrentes, mirrored/rotated structures, reload e antigos saves em cópia. Instalação declarada; não afirmar QA realizado.

> **Contexto histórico:** registros abaixo com “instalado”, “atual” ou “não instalado” anteriores a 22/11/2026 descrevem o snapshot de setembro ou auditorias antigas. Os campos vigentes e esta declaração prevalecem. Não foram realizados testes de runtime, save ou multiplayer.

---


> **Snapshot físico histórico — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#66**: `bclib-21.0.26.jar`, mod id `bclib`, runtime `21.0.26`, SHA-1 `06cc971fd149d7dae65c75792798b98be7439141`.

> **Reauditoria física histórica — 19/09/2026.** JAR top-level reconfirmado na modlist física atual: `bclib-21.0.26.jar`, versão `21.0.26`. Paridade Notion → GitHub revalidada; URL da própria página Notion removida.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física usada:** `modlist(1).txt` — 586 mods top-level
- **Auditoria de migração Notion → GitHub:** 2026-09-19

## Propriedades do banco

- **Mod:** BCLib: New Dawn
- **Arquivo JAR instalado (declarado):** `bclib-21.0.29.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/bclib-neoforge/files/9511338)), sem leitura do arquivo local.
- **Versão instalada declarada:** `21.0.29` (anterior `21.0.26`, NeoForge/Minecraft 1.21.1); metadata novo não lido.
- **Estado no pack:** `21.0.29` declarada instalada pelo usuário, como autoridade do inventário documental; JAR local e QA não verificados.
- **Estado da pesquisa:** alvo/JAR e cadeia histórica conferidos conforme seção vigente; lacunas específicas indicadas sem certificação indevida.
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Worldgen
- **Função:** Biblioteca foundation do ecossistema BetterX/New Dawn para registries, worldgen, materiais/blocos e compatibilidades compartilhadas de BetterEnd/BetterNether.
- **Dependências:** BCLib é base comum de BetterEnd/BetterNether; WorldWeaver e WunderLib possuem responsabilidades complementares. Requisitos exatos de consumidores devem ser conciliados com metadata do JAR distribuído.
- **Sobreposição:** Biblioteca de worldgen/infraestrutura; não é substituível automaticamente por Biolith, TerraBlender ou outras APIs.
- **Compatibilidade/Riscos:** Alterações em GUI/anvil, structures/worldgen e dados compartilhados BetterX; testar receitas concorrentes, mirrored/rotated structures, reload e antigos saves em cópia. Instalação declarada; não afirmar QA realizado.
- **Observações:** 21.0.26 adiciona suporte reutilizável a Chiseled Bookshelf para custom wood sets; 21.0.25 documenta compat OBE para BetterX chests e Voxy/distant-terrain.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/changelog oficial BCLib: New Dawn 21.0.26 + BetterEnd 21.0.34 e BetterNether 21.0.26 físicos e fontes já auditadas. Reconciliação final: JAR/runtime permanecem exatamente `bclib-21.0.26.jar` / `21.0.26`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bclib-neoforge/files/9511338
- **Atualização/Status:** 22/11/2026 — `21.0.26` → `21.0.29` por declaração de instalação. Auditoria histórica cobre 21.0.27 (saplings com BehaviourSapling compostáveis), 21.0.28 (sincronização do anvil, VoxelShapes de plantas de parede, bounds de estruturas e arrays de random offsets) e 21.0.29 (corrige setas ausentes no anvil quando há múltiplas receitas com os mesmos ingredientes). Não atribuir a 21.0.29 mudanças da 21.0.28.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou BCLib: New Dawn 21.0.26 como foundation do stack BetterX e registrou BetterEnd/BetterNether como consumidores físicos. Em 09/09/2026, as três versões foram revalidadas no snapshot atual, sem converter dependência estrutural em decisão curatorial.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `bclib-21.0.26.jar`, mod id `bclib`, runtime `21.0.26`, NeoForge 1.21.1. O pack também possui BetterEnd `21.0.34` e BetterNether `21.0.26`, consumidores diretos do stack BetterX/New Dawn.

## 1. Papel e autoridade
BCLib: New Dawn é a biblioteca compartilhada mantida para o ecossistema **BetterX/New Dawn**. Centraliza utilidades de registro, worldgen, blocos/materiais e infraestrutura usada por BetterEnd/BetterNether. Não é um worldgen provider independente que deva ser configurado como dimensão separada.

BetterEnd/BetterNether continuam authority do conteúdo e geração das respectivas dimensões; BCLib fornece as fundações compartilhadas.

## 2. Worldgen e registries
Como biblioteca de mods de dimensão, BCLib participa de lifecycle de registries/worldgen e de dados consumidos por BetterX. Integrações devem:
- preservar IDs/holders do provider;
- respeitar datapack/registry reload;
- não copiar biome/feature placement para um segundo pipeline;
- resolver referências depois do bootstrap apropriado, não em static init prematuro.

## 3. Materiais/blocos compartilhados
A linha 21.0.26 adicionou suporte reutilizável a **Chiseled Bookshelf para custom wood material sets**. Isso mostra que BCLib fornece componentes de bloco/material compartilhados para consumidores. O conteúdo final continua pertencendo ao mod que registra o material/bloco concreto.

## 4. Compatibilidade de block entities
O changelog 21.0.25 registra compatibilidade com **Optimised Block Entities** para chests BetterX. Para o pack, isso implica que block-entity behavior/render otimizado deve ser testado como integração do stack, sem criar mixin paralelo que bypassa BCLib/BetterX.

## 5. Fog e render de dimensão
A mesma linha documenta compatibilidade com **Voxy**, incluindo desativação do custom fog renderer quando necessário, e melhorias com distant-terrain renderers. Fog/render é apresentação client-side; não deve alterar biome/worldgen server-side.

## 6. BetterEnd e BetterNether no pack
Presenças físicas:
- BetterEnd 21.0.34;
- BetterNether 21.0.26.

BCLib 21.0.26 deve permanecer enquanto consumidores exigirem a API. A existência de outras libs como TerraBlender/Biolith não prova substituibilidade.

## 7. Client/server
- registry/worldgen/block state são server/common;
- fog/model/render compatibility é cliente;
- uma classe de renderer não pode vazar para dedicated server;
- mudanças de datapack precisam sincronizar worldgen/registry state sem depender de cache visual.

## 8. Lifecycle
Pontos críticos:
- server bootstrap de dimensions/registries;
- datapack reload;
- world create/load;
- chunk generation/load/unload;
- dimension travel;
- resource reload de assets;
- block entity load/unload;
- ativação de renderers de distância.

Worldgen já materializado não deve ser “regerado” por reload ou por bridge de compat.

## 9. Riscos
1. Registry/holder stale após datapack reload.
2. Dois frameworks de worldgen tentando registrar o mesmo conteúdo.
3. Client fog renderer conflitando com Voxy/distant terrain/shaders.
4. Block entity compat alterando tick/render duas vezes.
5. Remover BCLib por aparente redundância e quebrar BetterEnd/BetterNether.
6. Version drift entre BCLib e consumers BetterX.

## 10. Matriz de testes
1. Criar/carregar mundo com BetterEnd/BetterNether.
2. Dedicated server boot e dimension travel.
3. Datapack reload sem registry error.
4. Geração de chunks novos após reload sem duplicação de features.
5. Chests/BE BetterX com otimizações presentes.
6. Voxy/distant terrain/custom fog em cliente, se providers estiverem presentes.
7. Resource reload sem renderer/model stale.

## 11. Evidência
- modlist física atual: BCLib 21.0.26, BetterEnd 21.0.34, BetterNether 21.0.26;
- CurseForge oficial BCLib: New Dawn 21.0.26;
- changelog 21.0.26: Chiseled Bookshelf reutilizável para custom wood sets;
- changelog 21.0.25: OBE/BetterX chests, Voxy e distant-terrain compatibility.

> 🌍 BCLib é foundation do BetterX, não uma terceira dimensão/worldgen authority. O dossiê registra os contratos que importam para integração e troubleshooting.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** BCLib: New Dawn
- **Último candidato 1.21.1 / NeoForge localizado:** **21.0.28 (NeoForge 1.21.1)**
- **Estado:** **CANDIDATO COORDENADO NEW DAWN**
- **Gate de promoção:** Testar em conjunto com WorldWeaver, BetterNether, BetterEnd e WunderLib; preservar mundo existente e validar registries/worldgen/restart antes de promoção.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/bclib-new-dawn

## Revisão cumulativa auditada — 22/10/2026 — lote 01–10
**Autoridade física:** BCLib: New Dawn 21.0.26 (arquivo bclib-21.0.26.jar). **Releases NeoForge 1.21.1 após o instalado:** 21.0.27 (14/10) → 21.0.28 (19/10). O projeto source investigado é o fork oficial do port **Reijin2312/BCLib-New-Dawn**, não BCLib original de outra plataforma. Os commits são evidência do **source**, não autenticação byte-a-byte do JAR distribuído.

| Release | Evidência source da linha de versão | Alteração com impacto |
|---|---|---|
| **21.0.27** | Commit de configuração/versionamento **5000ccb76ef44840e9d96a8a04901e5ab4884348** fixa mod_version=21.0.27; commit de funcionalidade **feca624a11cc36be446b95bdd83e3f25ba9feffe** em FeatureSaplingBlock. | **FeatureSaplingBlock passa a implementar BehaviourSapling (compostagem de saplings custom)**. Ajustes de metadata de publicação e lista de módulos incluídos Wover, incluindo wover-pottable-api; há necessidade de comparar dependências efetivamente empacotadas com consumidores. Não transformar alteração de Gradle automaticamente em mudança de gameplay. |
| **21.0.28** | Commit **19f4af12161afbc3214fa3cceee01da254917aaa** fixa mod_version=21.0.28 e backporta “upstream generation and anvil fixes”. | BaseWallPlantBlock admite VoxelShapes parametrizadas; WallMushroomBlock usa SoundType.FUNGUS; **AnvilMenuMixin introduz flag sincronizada de recipe ativa, separa avaliação server/client** (evita estado stale); **MHelper.getOffsets e StructureErode usam cópias de arrays antes do shuffle**, evitando mutação global; StructureHelper usa bounding box nativa transformada por mirror/rotation. Acrescenta es_ar e es_mx no source. |

**Riscos novos:** compatibilidade de worldgen/estrutura quando bounds e random offsets mudam; receitas custom de anvil com clientes simultâneos; validadores e templates de saplings/materials; interoperabilidade BCLib, WorldWeaver, WunderLib, BetterEnd e BetterNether. Priorizar geração de chunks inéditos e loading de chunks antigos, structures rotacionadas/refletidas, anvil recipe swap com latência, compostagem e packets; backup de mundo antes de qualquer substituição. Compatibilidade de classes/registries e JarJar do artefato publicado **não foram inspecionadas**. Não atualizar uma única biblioteca isolada do conjunto BetterX.

Fontes primárias pinadas: https://github.com/Reijin2312/BCLib-New-Dawn/commit/5000ccb76ef44840e9d96a8a04901e5ab4884348 ; https://github.com/Reijin2312/BCLib-New-Dawn/commit/feca624a11cc36be446b95bdd83e3f25ba9feffe ; https://github.com/Reijin2312/BCLib-New-Dawn/commit/19f4af12161afbc3214fa3cceee01da254917aaa ; https://www.curseforge.com/minecraft/mc-mods/bclib-new-dawn/files/all?version=1.21.1.

## Auditoria complementar BCLib 21.0.29 — 17/11/2026 (somente upstream)

Instalado no snapshot #066: bclib-21.0.26.jar. As releases 21.0.27 e 21.0.28 já foram auditadas na revisão 22/10 (saplings compostáveis, recipe anvil/client-server, geração de estruturas e random offsets). O arquivo oficial seguinte para NeoForge Minecraft 1.21.1 é bclib-21.0.29.jar, de 08/11/2026: https://www.curseforge.com/minecraft/mc-mods/bclib-neoforge/files/9511338 .

Delta isolado do changelog 21.0.29: corrige setas ausentes no anvil ao haver múltiplas receitas usando os mesmos ingredientes. É relevante para seleção de receitas/GUI com BetterX e outros mods; não há nova mecânica de mundo/bioma anunciada nesta release. Testar recipes concorrentes de anvil, mudança de saída, datapack reload, coexistência com BetterEnd/BetterNether/WorldWeaver e integridade de inventário. Documentação completa 21.0.26→21.0.29; atualização física/QA pendentes e v21.0.26 preservada no dossier.


## Sincronização documental de 22/11/2026 — BCLib: New Dawn 21.0.29

**Estado atual declarado:** `21.0.26` → `21.0.29`; arquivo oficial `bclib-21.0.29.jar` (NeoForge 1.21.1), https://www.curseforge.com/minecraft/mc-mods/bclib-neoforge/files/9511338. Posição original #066. Nenhum novo JAR, hash ou metadata do PC foi consultado.

**Versões auditadas / lacunas:** Auditoria histórica cobre 21.0.27 (saplings com BehaviourSapling compostáveis), 21.0.28 (sincronização do anvil, VoxelShapes de plantas de parede, bounds de estruturas e arrays de random offsets) e 21.0.29 (corrige setas ausentes no anvil quando há múltiplas receitas com os mesmos ingredientes). Não atribuir a 21.0.29 mudanças da 21.0.28.

**Dependências aguardando sincronização e testes:** BCLib é base comum de BetterEnd/BetterNether; WorldWeaver e WunderLib possuem responsabilidades complementares. Requisitos exatos de consumidores devem ser conciliados com metadata do JAR distribuído. Divergências em outros dossiers ainda não atualizados são pendências documentais, não bloqueio automático nem prova de compatibilidade.

**Riscos e limites:** Alterações em GUI/anvil, structures/worldgen e dados compartilhados BetterX; testar receitas concorrentes, mirrored/rotated structures, reload e antigos saves em cópia. Instalação declarada; não afirmar QA realizado.
