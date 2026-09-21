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
- **Compatibilidade/Riscos:** O artefato físico atual é `1.10.5-r5.9.3-neoforge`. A página oficial da própria versão menciona Complementary Shaders r5.9.2 + Euphoria Patches 1.10.5, portanto o sufixo r5.9.3 não é usado sozinho como prova da revisão física do ZIP Complementary. Riscos: base incompatível, patched archive stale, Iris beta/render regressions, config/preset migration, modded-material properties ausentes, dimension-cache RAM e custo de features habilitadas. Não possui gameplay authority.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/euphoria-patches
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, mod id `euphoria_patcher`, runtime `1.10.5-r5.9.3-neoforge` e SHA-1 `2b3f92878d6867fa0199937da25ba882b9db7902`. Modrinth/CurseForge e changelog oficial revalidados em 21/09/2026 mantêm a mesma build; a presença/versão física do shaderpack Complementary continua sendo autoridade separada.
- **Observações:** Runtime físico 1.10.5-r5.9.3-neoforge. Deltas relevantes da linha permanecem documentados. Nuance revalidada em 21/09/2026: a página oficial da versão 1.10.5-r5.9.3-neoforge diz `Updated to Complementary Shaders r5.9.2 + Euphoria Patches 1.10.5`; por isso o catálogo não afirma a revisão do ZIP Complementary apenas a partir do sufixo do Patcher.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #265: `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` / runtime `1.10.5-r5.9.3-neoforge` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. A build continua atual; a página oficial detalhada da 1.10.5 confirma atualização do shader para Complementary r5.9.3.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa Complementary Shaders e Iris; não duplica EMF/ETF, EntityCulling ou gameplay lighting. Bugs precisam ser isolados entre shader base, patch Euphoria e loader Iris.
- **Data da última decisão:** 2026-08-26

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
Na auditoria anterior, o JAR físico era **1.10.0-r5.9**. Esse estado permanece como histórico. O runtime atual é **1.10.5-r5.9.3**; o sufixo `r5.9.3` passa a ser a referência de compatibilidade da base Complementary/Euphoria correspondente, sem autorizar inferir qual ZIP Complementary está instalado.
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
