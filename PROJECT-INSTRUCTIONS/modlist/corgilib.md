# CorgiLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bfbdb4ee33198aff7d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** CorgiLib
- **Arquivo JAR:** `Corgilib-NeoForge-1.21.1-5.0.0.9.jar`
- **Versão 1.21.1:** 5.0.0.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada do ecossistema Corgi Taco com infraestrutura de serialização/config via Mojang codecs, JSON5 comentado, árvores baseadas em NBT, easing/blending registries, entity filters e codecs para villager trades.
- **Dependências:** Library consumer-driven do ecossistema Corgi Taco. Consumer físico confirmado no snapshot atual: Oh The Biomes We've Gone 2.6.0, cuja ficha auditada declara CorgiLib entre as dependências. Não substituir automaticamente por outras config/worldgen/data libraries.
- **Sobreposição:** Biblioteca técnica própria do ecossistema Corgi Taco. Coexistência com CodecUI, Cloth Config, worldgen/data libraries ou outras APIs não implica redundância; contracts e consumers são distintos.
- **Compatibilidade/Riscos:** Riscos em version drift de codecs/config schemas, JSON5 parsing/comments, datapack/worldgen reload, NBT tree generation, registry de easing/blending, entity-filter semantics e villager trade codecs. Atualização isolada pode quebrar consumers mesmo sem conteúdo próprio.
- **Observações:** mod id `corgilib`; runtime 5.0.0.9. Superfícies documentadas incluem configs `.json5` serializados por Mojang codecs, codecs comentados, tree generation via NBT, easing/blending functions, entity filters e villager trade codecs; nenhum gameplay autônomo é inferido.
- **Procedência:** modlist.txt física atual de 08/09/2026 + runtime CorgiLib 5.0.0.9 + CurseForge oficial CorgiLib NeoForge 1.21.1 + ficha auditada de Oh The Biomes We've Gone 2.6.0 + documentação/source do ecossistema Corgi Taco.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/corgilib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — CorgiLib 5.0.0.9, codecs/config/data/worldgen utilities, reload/lifecycle, side e version-drift risks confirmados no QC global #110. Consumer físico Oh The Biomes We've Gone 2.6.0 confirmado; runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CorgiLib 5.0.0.9 foi reconfirmado na modlist física e reconstruído como library consumer-driven. A presença necessária a possíveis consumers não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 📚 Versão física confirmada: `Corgilib-NeoForge-1.21.1-5.0.0.9.jar`, mod id `corgilib`, runtime `5.0.0.9`, NeoForge 1.21.1. CorgiLib é **infraestrutura compartilhada**, não um provider de gameplay autônomo.

## 1. Papel e authority
CorgiLib centraliza contracts reutilizados por mods do ecossistema Corgi Taco. O consumer continua authority de biomas, estruturas, configs, entidades, trades ou qualquer feature final que use a library.
Não atribuir conteúdo de worldgen ou gameplay à CorgiLib apenas porque ela fornece codecs/helpers.

## 2. Mojang Codec e serialização
A documentação do ecossistema expõe uso de **Mojang Codecs** para serializar estruturas/configs. O codec define parse/validation/encoding; uma integração externa não deve gravar formato paralelo e depois presumir equivalência.
Para código próprio, nomes concretos de classes/métodos precisam ser pinados no source/JAR correspondente antes de uso.

## 3. Configs JSON5 e comentários
CorgiLib suporta superfícies de configuração `.json5` e codecs comentados no ecossistema documentado. Comentários são parte da experiência/configuração, mas os valores efetivos continuam definidos pelo schema/consumer.
Parse failure deve falhar de forma explícita; não preencher silenciosamente campos inválidos com semântica inventada.

## 4. Tree generation via NBT
A documentação registra infraestrutura para **geração de árvores a partir de NBT**. A library fornece tooling; o mod consumidor decide espécies, placement, biome constraints e worldgen integration.
NBT/template reload deve preservar IDs/estrutura esperada e não gerar duas vezes por bridge externa.

## 5. Easing e blending registries
CorgiLib expõe registries/utilities de funções de **easing/blending** usados por consumers. Registry keys e semântica pertencem ao contract da library/consumer.
Não converter nomes como easing/blending em gameplay stats sem consumer específico que faça essa associação.

## 6. Entity filters
Entity filters permitem consumers descreverem conjuntos/condições de entidades. O filtro é infraestrutura; AI, spawn, damage ou outra ação continua pertencendo ao consumer que aplica o filtro.
Reload/config change deve invalidar caches quando necessário para não manter seleção stale.

## 7. Villager trade codecs
A documentação do ecossistema inclui codecs/utilities para **villager trades**. Isso pode permitir dados configuráveis por consumers, mas CorgiLib não deve ser catalogada como provider de economia/trades por si só.
Trades finais precisam ser validados no registry/data lifecycle do consumer.

## 8. Consumer-driven necessity
A library deve permanecer enquanto houver consumer que a exija. Similaridade com CodecUI, Cloth Config ou outras data libraries não cria substituição binária.
Antes de remover/atualizar, mapear dependentes físicos e testar o conjunto em cópia do perfil.

## 9. Client/server
Codecs/config/data/worldgen contracts são majoritariamente common/server quando afetam gameplay. Qualquer UI de config/render usada por consumer continua client-side.
Dedicated server não deve carregar screen/render classes apenas para parsear dados comuns.

## 10. Reload e lifecycle
Validar bootstrap, config parse, datapack reload, registry resolution, world join, chunk generation de consumers, server restart e atualização de arquivos config/data.
Codec-derived objects e caches não podem permanecer stale após reload quando o consumer espera re-resolução.

## 11. Version drift
Sintomas possíveis:
- config que deixa de parsear;
- codec field/schema incompatível;
- registry key ausente;
- worldgen consumer falhando no bootstrap/reload;
- entity filter/trade data divergente;
- linkage error em consumer compilado contra outra build.
Diagnóstico deve registrar CorgiLib `5.0.0.9` + consumer + arquivo/data responsável.

## 12. Riscos
1. Remover CorgiLib com consumer ativo.
2. Atualizar library isoladamente e quebrar schema/API.
3. JSON5 inválido ser interpretado como config válida.
4. NBT tree generation duplicada por integração externa.
5. Registry de easing/blending ficar stale.
6. Entity filter ser confundido com authority de AI/spawn.
7. Trade codec duplicar/injetar trade por dois providers.
8. Client-only screen vazar para dedicated server.

## 13. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join sem linkage errors.
3. Parse de configs `.json5` de consumers e restart.
4. Datapack/config reload sem stale state.
5. Consumer de worldgen/tree NBT em chunks novos, se presente.
6. Consumer de entity filters/trades, se presente.
7. Invalid config em cópia de teste: erro controlado, sem corrupção silenciosa.
8. Atualização futura: library + consumers testados em conjunto.

## 14. Evidência
- modlist física atual: CorgiLib 5.0.0.9;
- CurseForge oficial: library NeoForge 1.21.1;
- documentação/source do ecossistema: JSON5/Mojang codecs, codecs comentados, NBT trees, easing/blending, entity filters e villager-trade codecs;
- guia técnico atualizado do projeto corrobora essas superfícies sem atribuir gameplay próprio à library.

> 🔧 Boundary canônico: CorgiLib fornece **serialização e utilities compartilhadas**; conteúdo e decisões de gameplay permanecem nos mods consumidores.
