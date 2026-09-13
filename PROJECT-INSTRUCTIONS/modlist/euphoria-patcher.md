# Euphoria Patcher — 1.10.0-r5.9-neoforge

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db81eb92c9e96455eb3939  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Euphoria Patcher
- **Arquivo JAR:** `EuphoriaPatcher-1.10.0-r5.9-neoforge.jar`
- **Versão 1.21.1:** `1.10.0-r5.9-neoforge`
- **Categoria:** Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/euphoria-patches
- **Função:** Client-side patcher/installer que detecta Complementary Shaders Reimagined/Unbound compatível e gera/gerencia a variante Euphoria Patches com features e settings opcionais.
- **Dependências:** Requer um Complementary Shaders base compatível e loader de shaders no cliente. Iris 1.8.14-beta.1 está fisicamente presente; a modlist de mods não comprova que o shaderpack Complementary esteja instalado.
- **Compatibilidade/Riscos:** A revisão física é `r5.9`; referências antigas r5.8.1 foram removidas. Riscos: base Complementary de revisão errada, patched archive stale, Iris beta/render regressions, config/preset migration, modded-material properties ausentes e custo de features habilitadas. Não possui gameplay authority.
- **Sobreposição:** Complementa Complementary Shaders e Iris; não duplica EMF/ETF, EntityCulling ou gameplay lighting. Bugs precisam ser isolados entre shader base, patch Euphoria e loader Iris.
- **Observações:** Drift antigo corrigido: o runtime não é 1.9.3-r5.8.1. Source oficial descreve auto-detection de base Complementary, verificação, patch binário, geração de variantes Reimagined/Unbound e watchers. Opções Euphoria são desativadas por padrão; presença do mod não prova feature visual específica ativa.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `EuphoriaPatcher-1.10.0-r5.9-neoforge.jar`, mod id `euphoria_patcher`, versão 1.10.0-r5.9-neoforge e SHA-1 d728a26bd67b70dfc510fbb08def619ec015c998; Iris 1.8.14-beta.1 também está presente.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Euphoria Patcher 1.10.0-r5.9; patch pipeline, Complementary/Iris ownership, default-off settings, filesystem/config lifecycle, risks and tests cataloged.
- **Data da última decisão:** 2026-08-26

> **Runtime físico confirmado:** `EuphoriaPatcher-1.10.0-r5.9-neoforge.jar` · mod id `euphoria_patcher` · versão `1.10.0-r5.9-neoforge` · NeoForge 1.21.1 · client-side. O pack possui **Iris 1.8.14-beta.1**; a presença de um Complementary shaderpack compatível não é inferida da modlist de mods.

## 1. Papel no modpack
Euphoria Patcher é o instalador/patcher runtime para **Euphoria Patches**, um add-on de Complementary Shaders Reimagined/Unbound. Ele não é um shaderpack independente: localiza uma base Complementary compatível e produz/gerencia a variante patchada.

## 2. Authority / ownership
- **Complementary shaderpack:** base shader e pipeline principal.
- **Euphoria Patches:** features/opções adicionais sobre a base.
- **Euphoria Patcher:** descoberta, verificação e aplicação do patch/arquivos resultantes.
- **Iris:** loader/runtime de shaders no cliente.

Não atribuir ao Patcher lógica de mundo, iluminação server-side ou gameplay.

## 3. Linha compatível r5.9
O JAR físico é **1.10.0-r5.9**. Referências antigas `1.9.3-r5.8.1` foram descartadas. O sufixo `r5.9` é parte do contrato de compatibilidade com a revisão Complementary/Euphoria correspondente; não reutilizar um shader base de revisão diferente por suposição.

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
1. base Complementary de revisão incompatível com r5.9;
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
1. Iris atual + Complementary base compatível com r5.9.
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