# Euphoria Patcher

> **Reauditoria física — 18/09/2026.** Versão catalogada atual: `1.10.5-r5.9.3-neoforge`. O conteúdo abaixo preserva a página Notion reconciliada; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Euphoria Patcher
- **Arquivo JAR:** EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar
- **Versão 1.21.1:** 1.10.5-r5.9.3-neoforge
- **Categoria:** Visual
- **Função:** Client-side patcher/installer que detecta Complementary Shaders Reimagined/Unbound compatível e gera/gerencia a variante Euphoria Patches com features e settings opcionais.
- **Dependências:** Requer um Complementary Shaders base compatível e loader de shaders no cliente. Iris 1.8.14-beta.1 está fisicamente presente; a modlist de mods não comprova que o shaderpack Complementary esteja instalado.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** A revisão física atual é `r5.9.3`. Riscos: base Complementary de revisão errada, patched archive stale, Iris beta/render regressions, config/preset migration, modded-material properties ausentes, dimension-cache RAM e custo de features habilitadas. Não possui gameplay authority.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/euphoria-patches
- **Procedência:** modlist física atual + CurseForge oficial Euphoria Patcher 1.10.5-r5.9.3-neoforge, file ID 8884680, 15/09/2026, e changelogs oficiais 1.10.1–1.10.5. A presença física do shaderpack Complementary atual continua não demonstrada pela modlist de mods.
- **Observações:** Runtime físico 1.10.5-r5.9.3-neoforge. Deltas relevantes da linha: cache por dimensão/settings updater/wildcard Iris em 1.10.1; WindLink/Distant Horizons e damage overlay em 1.10.2; ajuste sazonal do Dappled Forest em 1.10.3; 1.10.4 intermediária; 1.10.5 atualiza para Complementary r5.9.3.
- **Atualização/Status:** READITADO EM 18/09/2026 — runtime físico atualizado de 1.10.0-r5.9 para 1.10.5-r5.9.3-neoforge. Linha 1.10.1→1.10.5 incorporada; 1.10.5 atualiza a compatibilidade para Complementary Shaders r5.9.3.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
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

## Complemento técnico preservado da reauditoria GitHub anterior

## 3. Linha física 1.10.5 / r5.9.3
O JAR físico é **1.10.5-r5.9.3**. O sufixo `r5.9.3` faz parte do contrato de compatibilidade da base Complementary/Euphoria correspondente; não reutilizar um shader base de revisão diferente por suposição.
A evolução pública desde a antiga build do pack inclui:
- **1.10.1-r5.9.1:** melhora switching entre dimensões usando cache de shaders por dimensão, com custo de RAM configurável; melhora o settings updater quando os dois estilos são instalados; corrige wildcard de `dimensions.properties` que podia quebrar o catch-all `*` do Iris. A camada shader também publicou ajustes para reflective horses/banners, foliage shadows e Spyglass Astronomy.
- **1.10.2-r5.9.2:** acrescenta recursos WindLink como partículas flutuantes na água, snow layers ondulando em folhas, hanging signs e interação de foliage com jogador; evita que o overlay de dano da entidade seja afetado por darkness desaturation; introduz suporte inicial de textura para Distant Horizons; adiciona suporte à linha 26.3 do jogo.
- **1.10.3-r5.9.2:** corrige a aplicação de seasons sobre Dappled Forest.
- **1.10.4-r5.9.2:** release intermediária publicada; nenhum detalhe adicional é atribuído aqui sem evidência específica recuperada nesta auditoria.
- **1.10.5-r5.9.3:** bug-fix release atualmente instalada; a publicação oficial destaca a atualização para **Complementary Shaders r5.9.3**.
Esses deltas agora pertencem à linha efetivamente instalada; deixam de ser apenas “update candidate”.
Logo, instalar o mod não prova que Seasons, Darkness Desaturation, Soul Sand Valley Overhaul, WindLink ou qualquer outro efeito específico esteja ativo.
O settings updater foi explicitamente trabalhado na linha 1.10.1+, então a migração do perfil visual é parte do regression gate desta atualização.
A correção de wildcard de `dimensions.properties` da linha 1.10.1 é particularmente relevante porque afeta o catch-all `*` do Iris.
Validar: client boot, shaderpacks directory scan, primeira geração do patched pack, shader enable/disable, resource/shader reload, mudança Reimagined↔Unbound, atualização da base, atualização do Patcher, config watcher, troca de dimensão e restauração de perfil após restart.
1. base Complementary incompatível com `r5.9.3`;
7. modded block property ausente produzir material incorreto;
9. performance/RAM cair ao habilitar múltiplas features ou cache por dimensão;
10. Distant Horizons/WindLink dependerem de versões/recursos realmente compatíveis;
5. [ ] Preset default com opções extras desativadas.
7. [ ] Troca repetida de dimensão com cache configurado e observação de RAM.
11. [ ] WindLink/Distant Horizons apenas se essas features/providers forem usados.
- modlist física canônica de 16/09/2026: JAR/mod id/runtime/SHA-1 + Iris atual;
- arquivo oficial CurseForge 8884680: `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, suporte explícito a 1.21.1 e atualização para Complementary r5.9.3;
- site/CurseForge oficiais: Euphoria como add-on de Complementary, opções default-off, Popular Settings e suporte Reimagined/Unbound;
- changelogs oficiais 1.10.1/1.10.2/1.10.3/1.10.5; detalhes não recuperados da 1.10.4 não foram inventados.
