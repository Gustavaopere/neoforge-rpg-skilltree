# Euphoria Patcher — 1.10.5-r5.9.3-neoforge

> **Reauditoria física e de migração — 16/09/2026.** A autoridade física atual é `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, mod id `euphoria_patcher`, runtime `1.10.5-r5.9.3-neoforge`, SHA-1 `2b3f92878d6867fa0199937da25ba882b9db7902`, em NeoForge 1.21.1. O dossiê migrado do Notion foi preservado e o estado técnico foi promovido da antiga 1.10.0-r5.9 para a build efetivamente instalada. A publicação 1.10.5 é uma bug-fix release e atualiza a base compatível para Complementary Shaders r5.9.3.

## Propriedades do registro

- **Mod:** Euphoria Patcher
- **Arquivo JAR:** `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`
- **Versão 1.21.1 / runtime:** `1.10.5-r5.9.3-neoforge`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Categoria:** Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/euphoria-patches
- **Função:** Client-side patcher/installer que detecta Complementary Shaders Reimagined/Unbound compatível e gera/gerencia a variante Euphoria Patches com features e settings opcionais.
- **Dependências:** Requer um Complementary Shaders base compatível e loader de shaders no cliente. Iris `1.8.14-beta.1` está fisicamente presente; a modlist de mods não comprova que o shaderpack Complementary esteja instalado.
- **Compatibilidade/Riscos:** A revisão física agora é `r5.9.3`. Riscos: base Complementary de revisão errada, patched archive stale, Iris beta/render regressions, config/preset migration, modded-material properties ausentes, compatibilidade de features visuais intermediárias e custo das opções habilitadas. Não possui gameplay authority.
- **Sobreposição:** Complementa Complementary Shaders e Iris; não duplica EMF/ETF, EntityCulling ou gameplay lighting. Bugs precisam ser isolados entre shader base, patch Euphoria e loader Iris.
- **Observações:** A linha instalada incorpora a evolução 1.10.1→1.10.5. Entre os deltas públicos estão cache por dimensão/settings updater/wildcard do Iris na 1.10.1, WindLink/Distant Horizons e overlay de dano na 1.10.2, ajuste sazonal do Dappled Forest na 1.10.3 e atualização final para Complementary r5.9.3 na 1.10.5. Não foram inventados detalhes da 1.10.4 além de sua existência intermediária.
- **Procedência:** modlist física atual de 16/09/2026 + arquivo oficial CurseForge 8884680 + changelogs oficiais Euphoria. A presença de um Complementary shaderpack compatível continua não demonstrada pela lista de mods.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — migração preservada e estado físico atualizado de 1.10.0-r5.9 para 1.10.5-r5.9.3.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar` · mod id `euphoria_patcher` · versão `1.10.5-r5.9.3-neoforge` · NeoForge 1.21.1 · client-side. O pack possui **Iris 1.8.14-beta.1**; a presença de um Complementary shaderpack compatível não é inferida da modlist de mods.

## 1. Papel no modpack
Euphoria Patcher é o instalador/patcher runtime para **Euphoria Patches**, um add-on de Complementary Shaders Reimagined/Unbound. Ele não é um shaderpack independente: localiza uma base Complementary compatível e produz/gerencia a variante patchada.

## 2. Authority / ownership
- **Complementary shaderpack:** base shader e pipeline principal.
- **Euphoria Patches:** features/opções adicionais sobre a base.
- **Euphoria Patcher:** descoberta, verificação e aplicação do patch/arquivos resultantes.
- **Iris:** loader/runtime de shaders no cliente.

Não atribuir ao Patcher lógica de mundo, iluminação server-side ou gameplay.

## 3. Linha física 1.10.5 / r5.9.3
O JAR físico é **1.10.5-r5.9.3**. O sufixo `r5.9.3` faz parte do contrato de compatibilidade da base Complementary/Euphoria correspondente; não reutilizar um shader base de revisão diferente por suposição.

A evolução pública desde a antiga build do pack inclui:
- **1.10.1-r5.9.1:** melhora switching entre dimensões usando cache de shaders por dimensão, com custo de RAM configurável; melhora o settings updater quando os dois estilos são instalados; corrige wildcard de `dimensions.properties` que podia quebrar o catch-all `*` do Iris. A camada shader também publicou ajustes para reflective horses/banners, foliage shadows e Spyglass Astronomy.
- **1.10.2-r5.9.2:** acrescenta recursos WindLink como partículas flutuantes na água, snow layers ondulando em folhas, hanging signs e interação de foliage com jogador; evita que o overlay de dano da entidade seja afetado por darkness desaturation; introduz suporte inicial de textura para Distant Horizons; adiciona suporte à linha 26.3 do jogo.
- **1.10.3-r5.9.2:** corrige a aplicação de seasons sobre Dappled Forest.
- **1.10.4-r5.9.2:** release intermediária publicada; nenhum detalhe adicional é atribuído aqui sem evidência específica recuperada nesta auditoria.
- **1.10.5-r5.9.3:** bug-fix release atualmente instalada; a publicação oficial destaca a atualização para **Complementary Shaders r5.9.3**.

Esses deltas agora pertencem à linha efetivamente instalada; deixam de ser apenas “update candidate”.

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

Logo, instalar o mod não prova que Seasons, Darkness Desaturation, Soul Sand Valley Overhaul, WindLink ou qualquer outro efeito específico esteja ativo.

## 7. Mod support / properties
Euphoria Patches possui suporte extensivo a blocos/mods via properties. Isso é compat de shader/material; não significa compat de gameplay. Ao adicionar mod próprio, IDs de blocos/itens precisam ser reais e a property correspondente deve ser validada no shader gerado.

## 8. Config persistence
O projeto informa que configurações existentes de Complementary/Euphoria podem ser importadas entre atualizações. Regression gate: update não deve resetar perfil visual nem carregar opção removida com semântica antiga.

O settings updater foi explicitamente trabalhado na linha 1.10.1+, então a migração do perfil visual é parte do regression gate desta atualização.

## 9. Iris
Iris `1.8.14-beta.1` está instalado. Ele é o loader de shader efetivo. Falha de compile/link GLSL, shader reload ou pipeline incompatível deve ser diagnosticada separando Iris, Complementary base e Euphoria patch.

A correção de wildcard de `dimensions.properties` da linha 1.10.1 é particularmente relevante porque afeta o catch-all `*` do Iris.

## 10. Client-only boundary
Todo o sistema é visual/local. Dedicated server não usa Euphoria para iluminação autoritativa, mob detection, stealth ou qualquer regra de gameplay. Dois jogadores podem usar shaders diferentes sem divergir no world state.

## 11. Lifecycle
Validar: client boot, shaderpacks directory scan, primeira geração do patched pack, shader enable/disable, resource/shader reload, mudança Reimagined↔Unbound, atualização da base, atualização do Patcher, config watcher, troca de dimensão e restauração de perfil após restart.

## 12. Riscos
1. base Complementary incompatível com `r5.9.3`;
2. shaderpack ausente e Patcher instalado sem efeito;
3. arquivo patchado stale após update da base;
4. Iris beta expor regressão de compile/render;
5. config/preset antigo mudar de semântica;
6. duas cópias Euphoria concorrentes na pasta;
7. modded block property ausente produzir material incorreto;
8. filesystem watcher repatchar arquivo enquanto launcher/sync o modifica;
9. performance/RAM cair ao habilitar múltiplas features ou cache por dimensão;
10. Distant Horizons/WindLink dependerem de versões/recursos realmente compatíveis;
11. atribuir bug de shader ao mod de gameplay errado.

## 13. Matriz de testes
1. [ ] Iris atual + Complementary base compatível com r5.9.3.
2. [ ] Primeira geração do shader Euphoria.
3. [ ] Reimagined e Unbound separadamente, se ambos forem usados.
4. [ ] Shader reload e restart.
5. [ ] Preset default com opções extras desativadas.
6. [ ] Popular Settings/profile customizado.
7. [ ] Troca repetida de dimensão com cache configurado e observação de RAM.
8. [ ] Update da base Complementary em cópia de teste.
9. [ ] Update do Patcher preservando settings.
10. [ ] Cena com blocos modded do pack para material/emission.
11. [ ] WindLink/Distant Horizons apenas se essas features/providers forem usados.
12. [ ] Benchmark FPS/frame time do perfil visual real.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica de 16/09/2026: JAR/mod id/runtime/SHA-1 + Iris atual;
- arquivo oficial CurseForge 8884680: `EuphoriaPatcher-1.10.5-r5.9.3-neoforge.jar`, suporte explícito a 1.21.1 e atualização para Complementary r5.9.3;
- source oficial `EuphoriaPatches/EuphoriaPatcher`: detecção, patch binário, watcher e geração de variantes;
- site/CurseForge oficiais: Euphoria como add-on de Complementary, opções default-off, Popular Settings e suporte Reimagined/Unbound;
- changelogs oficiais 1.10.1/1.10.2/1.10.3/1.10.5; detalhes não recuperados da 1.10.4 não foram inventados.

> **Boundary canônico:** Euphoria Patcher administra a **transformação/configuração do shaderpack**. Iris carrega shaders; Complementary/Euphoria definem a apresentação; o servidor não recebe gameplay authority disso.