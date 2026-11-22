# Euphoria Patcher

## Propriedades do registro

- **Mod:** Euphoria Patcher
- **Arquivo JAR:** `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`
- **Versão 1.21.1:** `1.10.5-r5.9.3-neoforge`
- **Categoria:** Visual
- **Função:** Client-side patcher/installer que detecta Complementary Shaders Reimagined/Unbound compatível e gera/gerencia a variante Euphoria Patches com features e settings opcionais.
- **Dependências:** Requer um Complementary Shaders base compatível e loader de shaders no cliente. Iris 1.8.14-beta.1 está fisicamente presente; a modlist de mods não comprova que o shaderpack Complementary esteja instalado.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** O artefato físico atual é `1.10.5-r5.9.3-neoforge`. A página oficial do próprio File ID 8884680 é internamente inconsistente: o cabeçalho do changelog diz `Updated to Complementary Shaders r5.9.2 + Euphoria Patches 1.10.5`, enquanto a seção `Key Shader Changes` diz `Updated to Complementary Shaders r5.9.3`. Portanto nenhum desses dois textos, isoladamente, é usado como prova da revisão física do ZIP Complementary. Riscos: base incompatível, patched archive stale, Iris beta/render regressions, config/preset migration, modded-material properties ausentes, dimension-cache RAM e custo de features habilitadas. Não possui gameplay authority.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/euphoria-patches
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, mod id `euphoria_patcher`, runtime `1.10.5-r5.9.3-neoforge` e SHA-1 `2b3f92878d6867fa0199937da25ba882b9db7902`. Modrinth/CurseForge e changelog oficial revalidados em 21/09/2026 mantêm a mesma build; a presença/versão física do shaderpack Complementary continua sendo autoridade separada.
- **Observações:** Runtime físico 1.10.5-r5.9.3-neoforge. Deltas relevantes da linha permanecem documentados. Nuance revalidada em 21/09/2026: a página oficial da versão 1.10.5-r5.9.3-neoforge contém simultaneamente referências a Complementary Shaders r5.9.2 e r5.9.3; o catálogo preserva essa contradição upstream e não afirma a revisão do ZIP Complementary apenas a partir do sufixo do Patcher.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #265: `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` / runtime `1.10.5-r5.9.3-neoforge` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. A build continua atual; o File ID 8884680 contém referências contraditórias a Complementary r5.9.2 e r5.9.3, portanto a revisão física do shaderpack permanece autoridade separada.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa Complementary Shaders e Iris; não duplica EMF/ETF, EntityCulling ou gameplay lighting. Bugs precisam ser isolados entre shader base, patch Euphoria e loader Iris.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #266: JAR `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, mod id `euphoria_patcher`, runtime `1.10.5-r5.9.3-neoforge`, SHA-1 `2b3f92878d6867fa0199937da25ba882b9db7902`.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` · mod id `euphoria_patcher` · versão `1.10.5-r5.9.3-neoforge` · NeoForge 1.21.1 · client-side. O pack possui **Iris 1.8.14-beta.1**; a presença de um Complementary shaderpack compatível não é inferida da modlist de mods.
## 1. Papel no modpack
Euphoria Patcher é o instalador/patcher runtime para **Euphoria Patches**, um add-on de Complementary Shaders Reimagined/Unbound. Ele não é um shaderpack independente: localiza uma base Complementary compatível e produz/gerencia a variante patchada.
## 2. Authority / ownership
- **Complementary shaderpack:** base shader e pipeline principal.
- **Euphoria Patches:** features/opções adicionais sobre a base.
- **Euphoria Patcher:** descoberta, verificação e aplicação do patch/arquivos resultantes.
- **Iris:** loader/runtime de shaders no cliente.
Não atribuir ao Patcher lógica de mundo, iluminação server-side ou gameplay.
## 3. Histórico r5.9 e evolução até r5.9.3
Na auditoria anterior, o JAR físico era **1.10.0-r5.9**. Esse estado permanece como histórico. O runtime atual é **1.10.5-r5.9.3**. O sufixo do Patcher identifica a build instalada, mas o próprio File ID 8884680 é contraditório sobre a revisão Complementary (`r5.9.2` no cabeçalho e `r5.9.3` em `Key Shader Changes`); portanto não autoriza inferir qual ZIP Complementary está instalado.
A 1.10.1-r5.9.1, agora parte da linhagem herdada pelo runtime atual, melhora o switching entre dimensões usando cache de shaders por dimensão (com custo de RAM configurável), melhora o settings updater quando os dois estilos de shader são instalados e corrige o wildcard de `dimensions.properties` que quebrava o catch-all `*` do Iris. A camada shader também registra correções/ajustes visuais para horses/banners, foliage shadows e Spyglass Astronomy.
## 4. Operação do patcher
O source oficial descreve:
- detecção automática de Complementary base em ZIP ou diretório dentro de `shaderpacks/`;
- verificação de tamanho/assinatura prática da base para evitar falso positivo;
- aplicação de patch binário para gerar variantes Euphoria;
- criação de cópias com nomes configuráveis;
- watcher de configuração/filesystem para repatch quando novos shaderpacks aparecem.
Essas operações são filesystem/client-side.
## 5. Reimagined e Unbound
O projeto suporta as duas linhas principais do Complementary: **Reimagined** e **Unbound**. A variante gerada precisa corresponder à base detectada; não trocar recursos entre as duas linhas manualmente.
## 6. Features opcionais
A documentação oficial enfatiza que as opções Euphoria são **desativadas por padrão** e habilitadas individualmente. Há uma aba `Popular Settings` e presets para aplicar combinações comuns.
Logo, instalar o mod não prova que Seasons, Darkness Desaturation, Soul Sand Valley Overhaul ou qualquer outro efeito específico esteja ativo.
## 7. Mod support / properties
Euphoria Patches possui suporte extensivo a blocos/mods via properties. Isso é compat de shader/material; não significa compat de gameplay. Ao adicionar mod próprio, IDs de blocos/itens precisam ser reais e a property correspondente deve ser validada no shader gerado.
## 8. Config persistence
O projeto informa que configurações existentes de Complementary/Euphoria podem ser importadas entre atualizações. Regression gate: update não deve resetar perfil visual nem carregar opção removida com semântica antiga.
## 9. Iris
Iris 1.8.14-beta.1 está instalado. Ele é o loader de shader efetivo. Falha de compile/link GLSL, shader reload ou pipeline incompatível deve ser diagnosticada separando Iris, Complementary base e Euphoria patch.
## 10. Client-only boundary
Todo o sistema é visual/local. Dedicated server não usa Euphoria para iluminação autoritativa, mob detection, stealth ou qualquer regra de gameplay. Dois jogadores podem usar shaders diferentes sem divergir no world state.
## 11. Lifecycle
Validar: client boot, shaderpacks directory scan, primeira geração do patched pack, shader enable/disable, resource/shader reload, mudança Reimagined↔Unbound, atualização da base, atualização do Patcher, config watcher e restauração de perfil após restart.
## 12. Riscos
1. base Complementary de revisão incompatível com r5.9.3;
2. shaderpack ausente e Patcher instalado sem efeito;
3. arquivo patchado stale após update da base;
4. Iris beta expor regressão de compile/render;
5. config/preset antigo mudar de semântica;
6. duas cópias Euphoria concorrentes na pasta;
7. modded block property ausente produz material incorreto;
8. filesystem watcher repatchar arquivo enquanto launcher/sync o modifica;
9. performance cair ao habilitar múltiplas features pesadas;
10. atribuir bug de shader ao mod de gameplay errado.
## 13. Matriz de testes
1. Iris atual + Complementary base compatível com r5.9.3.
2. Primeira geração do shader Euphoria.
3. Reimagined e Unbound separadamente, se ambos forem usados.
4. Shader reload e restart.
5. Preset default com todas as opções extras desativadas.
6. Popular Settings/profile customizado.
7. Update da base Complementary em cópia de teste.
8. Update do Patcher preservando settings.
9. Cena com blocos modded do pack para material/emission.
10. Benchmark FPS/frame time do perfil visual real.
**Esta catalogação não afirma que esses testes foram executados.**
## 14. Evidências
- modlist física canônica: JAR/mod id/version/hash + Iris atual;
- source oficial `EuphoriaPatches/EuphoriaPatcher`: detecção, patch binário, watcher e geração de variantes;
- site/Modrinth/CurseForge oficiais: Euphoria como add-on de Complementary, opções default-off, Popular Settings e suporte Reimagined/Unbound.
> **Boundary canônico:** Euphoria Patcher administra a **transformação/configuração do shaderpack**. Iris carrega shaders; Complementary/Euphoria definem a apresentação; o servidor não recebe gameplay authority disso.
## 15. Atualização instalada — 1.10.5-r5.9.3
A build física atual `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` foi publicada em 15/09/2026 e suporta Minecraft 1.21.1.
Linha de atualização herdada:
- **1.10.1-r5.9.1:** cache de shaders por dimensão, settings updater para dois estilos e correção do wildcard de `dimensions.properties`; ajustes visuais para reflective horses/banners, foliage shadows e Spyglass Astronomy.
- **1.10.2-r5.9.2:** WindLink com partículas na água, snow layers em leaves, hanging signs e foliage interaction; damage overlay fora da darkness desaturation; suporte inicial a texturas de Distant Horizons.
- **1.10.3-r5.9.2:** seasons deixam de afetar o Dappled Forest.
- **1.10.4-r5.9.2:** release intermediária publicada; detalhes não são inventados sem evidência específica recuperada.
- **1.10.5-r5.9.3:** bug-fix release atual; atualização para **Complementary Shaders r5.9.3**.
Isso não comprova a versão física do ZIP Complementary em `shaderpacks/`; o bloqueio do catálogo de Complementary permanece separado.

## Auditoria incremental upstream — Euphoria Patches 1.11.0 → 1.11.1 → 1.11.2 (22/11/2026; não instaladas)

**Baseline física:** #266 `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, versão `1.10.5-r5.9.3-neoforge`. A plataforma é Minecraft 1.21.1/NeoForge, cliente, shaders Complementary Reimagined e Iris. O fabricante registra **suporte explícito a Minecraft 1.21.1** nos três binários NeoForge abaixo, mesmo quando a página principal os agrupa com Minecraft 26.x; essa indicação de distribuição **não equivale a teste de compatibilidade com o pack**. O [histórico oficial do autor](https://www.euphoriapatches.com/changelogs/) separa 1.11.0, 1.11.1 e 1.11.2.

- **1.11.0 — 18/11/2026**, [CurseForge NeoForge file 9688049](https://www.curseforge.com/minecraft/mc-mods/euphoria-patches/files/9688049): `EuphoriaPatcher-1.11.0-r5.10-neoforge.jar`, atualização da base **Complementary Shaders para r5.10**, com novas dimensões/céu e aspecto espacial em Ad Astra e Stellaris; **fog de mods** (Alex's Caves, Atmospherics), **LabPBR SSS**, **POM rain puddles**, novo uniform `euphoriaPatchesIsCurrentBiomeModded`, diagnóstico de erros de shader com link de suporte e transição para **shader patcher library**. Providers opcionais só afetam o perfil se de fato presentes.
- **1.11.1 — 18/11/2026**, [CurseForge NeoForge file 9691336](https://www.curseforge.com/minecraft/mc-mods/euphoria-patches/files/9691336): `EuphoriaPatcher-1.11.1-r5.10-neoforge.jar`. Changelog específico: correção de **terreno invisível em Minecraft 26.1.x no Linux** devido a bug de Iris. O autor não descreve esse bug como incidente de Minecraft 1.21.1; não atribuir benefício direto ao pack sem evidência.
- **1.11.2 — 20/11/2026**, [CurseForge NeoForge file 9708040](https://www.curseforge.com/minecraft/mc-mods/euphoria-patches/files/9708040): `EuphoriaPatcher-1.11.2-r5.10-neoforge.jar`. Adiciona opções de **reflection**; corrige a forma de aplicar a gamerule `advance time` às animações contínuas para evitar **cloud flicker** modded e saltos em vanilla; corrige compilação em **Mesa** e stutters de animações de nuvens com world day count elevado. Notas apresentadas sobre 1.11.0 na página 1.11.2 são **cumulativas**, não são novas funções da 1.11.2.

**Gates de atualização:** confirmar a versão real do shader Complementary Reimagined instalada e alinhamento com r5.10, Iris, driver/GPU e execução do shader patcher no cliente; testar compilação GLSL, fog de biomas modded, nuvens/reloj/gamerules, PBR/POM/reflexos, efeitos VFX do pack, resource reload e FPS em Mesa e no driver do usuário. Nenhum arquivo físico, shader ZIP, config ou build foi alterado; baseline e diretório oficial preservados. **Releases publicadas ≠ instaladas/testadas.**
