# Euphoria Patcher — 1.10.0-r5.9-neoforge

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db81eb92c9e96455eb3939  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist.txt`, 595 entradas totais incluindo o modloader  
> Reconciliado em: 2026-09-15

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
- **Observações:** Runtime físico 1.10.0-r5.9. Upstream 1.10.1-r5.9.1 é uma bug-fix release compatível com 1.21.1: corrige reflective horses/banners, adiciona opção de foliage shadows, melhora integração visual com Spyglass Astronomy; no patcher, melhora dimension switching com cache por dimensão (com custo de RAM), settings updater para dois shader styles e corrige wildcard de `dimensions.properties` que quebrava o catch-all `*` do Iris.
- **Procedência:** modlist.txt física atual confirma o artefato instalado Euphoria Patcher 1.10.0-r5.9. Revalidação oficial em 12/09/2026 confirma a existência da release 1.10.1-r5.9.1 de 11/09/2026 compatível com Minecraft 1.21.1.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — lote físico #268: pack permanece em Euphoria Patcher 1.10.0-r5.9; upstream publicou 1.10.1-r5.9.1 em 11/09/2026 com suporte explícito a 1.21.1. Update disponível registrado sem alterar a autoridade física instalada.
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

## 3. Linha física r5.9 e update upstream r5.9.1
O JAR físico é **1.10.0-r5.9**. Referências antigas `1.9.3-r5.8.1` foram descartadas. O sufixo `r5.9` é parte do contrato de compatibilidade com a revisão Complementary/Euphoria correspondente; não reutilizar um shader base de revisão diferente por suposição.

Em 11/09/2026 foi publicada **Euphoria Patcher 1.10.1-r5.9.1** para NeoForge, explicitamente compatível com Minecraft 1.21.1. A release melhora o switching entre dimensões usando cache de shaders por dimensão (com custo de RAM configurável), melhora o settings updater quando os dois estilos de shader são instalados e corrige o wildcard de `dimensions.properties` que quebrava o catch-all `*` do Iris. A camada shader também registra correções/ajustes visuais para horses/banners, foliage shadows e Spyglass Astronomy. Essas mudanças pertencem à 1.10.1-r5.9.1 e **não** são atribuídas ao artefato físico 1.10.0-r5.9.

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