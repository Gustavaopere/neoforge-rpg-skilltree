# Create Metallurgy

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8120bb5ecead5d8ae6d9
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Metallurgy
- **Arquivo JAR:** `createmetallurgy-1.0.3-1.21.1.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Metalurgia, Tecnologia, Automação
- **Função:** Expande metalurgia no Create com crucible/faucet, fusão, ligas e processamento industrial de metais e materiais.
- **Dependências:** Create obrigatório; release 1.0.3 alinhada à linha Create 6.0.10, usada pelo pack. HeatJS é integração/fix upstream e não foi encontrado top-level. Jade/KubeJS/CreateJS presentes exigem smoke-test apenas nas superfícies realmente expostas pela build.
- **Sobreposição:** Overlap concreto com Create: Metalwork 2.0.0, Productive Metalworks 1.15.1 e Almost Unified 1.4.2 em molten materials/ore processing; Metallurgy distingue-se por machinery própria como Industrial Crucible/Faucet.
- **Compatibilidade/Riscos:** Riscos: Faucet/Crucible server crash; heat state stale após Blaze Burner change; HeatJS overlap; melting/casting dupe/loss; Electrum duplication; worldgen crash; molten/tag overlap com Metalwork/Productive Metalworks; source 1.0.4 confundido com a build 1.0.3.
- **Observações:** JAR/mod id/runtime 1.0.3 confirmados. Changelog exato 1.0.3 corrige Faucet e Crucible server-side, HeatJS melting/heat-source overlap, worldgen, Electrum duplication e refresh de Blaze Burner; source público 1.21.1 consultado já está em 1.0.4 e foi usado só como arquitetura.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog oficiais Create Metallurgy 1.0.3 + source Lucreeper74/Create-Metallurgy 1.21.1 usado apenas como arquitetura posterior.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-metallurgy
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.3 com Industrial Crucible, Faucet, melting/casting, heat refresh, Crushed Raw Tungsten, HeatJS boundary, worldgen e Electrum duplication fix catalogados.
- **Histórico da decisão:** Histórico não operacional: uma auditoria anterior centrada em TFC sugeriu remoção por sobreposição de fundição/processamento metálico. TFC não está na modlist atual, portanto esse motivo não sustenta decisão presente. O mod permanece instalado e sem decisão final.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> 🔥 **Identidade física confirmada:** `createmetallurgy-1.0.3-1.21.1.jar`, mod id `createmetallurgy`, runtime `1.0.3`, NeoForge 1.21.1. A release 1.0.3 é alinhada a Create 6.0.10. O branch oficial 1.21.1 consultado já está em 1.0.4, portanto serve apenas como evidência arquitetural além do changelog exato 1.0.3.

## 1. Papel e authority
Create Metallurgy expande ore processing com **fusão, fluidos metálicos, casting e automação metalúrgica** no estilo Create. O addon owns seus recipes, máquinas, molten materials e alloying; Create continua authority da cinética e dos processos base que ele reutiliza.

## 2. Industrial Crucible
O **Industrial Crucible** é uma máquina central da cadeia. Recebe materiais/recipes de melting e depende de heat state coerente. Inventory, fluid output e recipe progress são server-authoritative e devem ser conservativos em pause/restart.

## 3. Faucet
O **Faucet** transfere/verte material fundido para destinos compatíveis. A release 1.0.3 corrige crash de Faucet em servidor, então dedicated-server interaction e transfer são regression gates obrigatórios desta build.

## 4. Melting e molten metals
O addon transforma matérias-primas em fluidos/molten materials para etapas posteriores. Cada recipe deve consumir input uma vez e produzir o amount exato registrado; nomes semelhantes de molten metal em outros mods não equivalem a interoperabilidade sem tag/recipe explícito.

## 5. Casting e solidificação
O fluxo metalúrgico inclui transformação de material fundido em formas sólidas. Mold/container state, remainder e fluid amount precisam sobreviver a chunk unload e restart sem dupe/loss.

## 6. Alloying
Combinações de materiais podem formar ligas. Proporções e recipes reais pertencem ao Recipe Manager da build. Esta ficha não inventa composição numérica de alloys não pinada à 1.0.3.

## 7. Heat state e Blaze Burner
A 1.0.3 corrige o Crucible que não atualizava quando o **Blaze Burner mudava de estado** abaixo dele. Heat transitions — inclusive aquecimento, superheating ou perda de heat — precisam invalidar o estado cached e reavaliar recipe eligibility.

## 8. HeatJS
O changelog 1.0.3 corrige crash do Industrial Crucible com melting recipe usando **HeatJS** e overlap de custom heat source com Blaze Burner. HeatJS não foi encontrado como JAR top-level no pack atual; o fix pertence ao artefato instalado, mas a integração fica upstream/inativa salvo provider real.

## 9. Crushed Raw Tungsten
A 1.0.3 adiciona recipe de melting que faltava para **Crushed Raw Tungsten**. Isso confirma tungsten na superfície da linha instalada. A origem/worldgen concreta do minério deve ser validada no registry/worldgen efetivo, sem assumir provider externo.

## 10. World generation
A release corrige um **world generation crash**. O detalhe causal completo não é expandido na publicação; o gate operacional é criar/entrar em chunks novos e garantir que registries/placed features da build não falhem no servidor.

## 11. Electrum recipe duplication
A 1.0.3 corrige uma recipe de **duplicação de ore/electrum**. Isso é diretamente econômico: processamento de electrum deve respeitar uma única cadeia válida e não produzir ganho infinito por recipe cycle.

## 12. Create 6.0.10
A release exata foi publicada para a linha Create 6.0.10, que é exatamente a versão física do pack. O source 1.0.4 atual também declara Create 6.0.10 no ambiente de desenvolvimento, reforçando continuidade arquitetural, mas não equivalência binária entre 1.0.3 e 1.0.4.

## 13. KubeJS/CreateJS/Jade
O source posterior 1.21.1 possui dependências de desenvolvimento para KubeJS, CreateJS e Jade. O pack contém KubeJS 2101.7.2-build.374, KubeJS Create 2101.3.1-build.18 e Jade 15.10.6. Isso indica superfícies a smoke-test, mas qualquer API/feature específica só é considerada ativa se exposta pela build 1.0.3/runtime.

## 14. Sobreposição com Metalwork
Create: Metalwork 2.0.0 está instalado. Ambos tocam molten/crushed ore e rendimento, mas Metalwork é centrado em novas rotas de crushed ores/molten ores enquanto Metallurgy inclui machinery própria como Crucible/Faucet. Não são duplicatas automáticas.

## 15. Sobreposição com Productive Metalworks
Productive Metalworks 1.15.1 também está presente. O risco é múltipla representação de molten metals, tags comuns e recipes paralelas. Almost Unified 1.4.2 pode ajudar na unificação de variantes, mas não resolve diferenças semânticas de máquinas/quantidades automaticamente.

## 16. Client/server
Recipe selection, heat eligibility, fluid/item mutation, worldgen e machine state são server-authoritative. Render de molten material, particles, gauges e Jade são client-facing. Cliente não pode manter processo ativo quando o servidor invalida heat/recipe.

## 17. Lifecycle
Testar place/break, fill/drain, heat transition, chunk unload/reload, restart, `/reload`, worldgen de chunks novos e interação com automation. Crucible parcialmente processando e Faucet no momento de unload são casos críticos.

## 18. Riscos
1. Faucet reproduz crash dedicado corrigido em 1.0.3.
2. Industrial Crucible reproduz crash server-side.
3. Heat change do Blaze Burner não invalida recipe state.
4. Custom heat source sobrepõe Burner e aplica dois níveis de heat.
5. Melting consome item sem creditar fluido ou vice-versa.
6. Casting duplica material em break/unload.
7. Electrum mantém rota de duplicação corrigida.
8. Worldgen crash reaparece em chunk novo.
9. Molten metal/tag colide com Metalwork/Productive Metalworks.
10. Recipe unification altera rendimento e cria loop econômico.
11. Source 1.0.4 é confundido com implementação exata 1.0.3.

## 19. Matriz de testes
- [ ] Dedicated server inicia com Metallurgy 1.0.3 + Create 6.0.10.
- [ ] Faucet transfere material sem crash/dupe.
- [ ] Industrial Crucible processa recipe básica sem crash.
- [ ] Alterar estado do Blaze Burner atualiza imediatamente eligibility/progress.
- [ ] Crushed Raw Tungsten possui rota de melting esperada.
- [ ] Electrum não apresenta recipe cycle de duplicação.
- [ ] Chunk novo gera sem worldgen crash.
- [ ] Fill/drain/casting conservam fluid amount em restart.
- [ ] `/reload` não deixa recipe/heat cache stale.
- [ ] Coexistência com Metalwork/Productive Metalworks não cria molten/recipe loops involuntários.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.0.3. Release/changelog oficiais 1.0.3 confirmam tungsten melting e fixes de Faucet, Industrial Crucible, HeatJS, worldgen, Electrum duplication, Blaze Burner refresh e heat-source overlap. O branch público atual é 1.0.4; internals específicos que possam ter mudado permanecem fail-closed.

> 🔒 **Boundary canônico:** Metallurgy controla suas máquinas, molten materials e recipes; Create controla kinetics/heat providers base. Toda etapa sólido→molten→casting deve conservar massa/recursos e revalidar heat server-side.
