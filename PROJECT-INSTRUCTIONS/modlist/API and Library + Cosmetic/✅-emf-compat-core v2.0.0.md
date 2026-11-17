# EMF Compat: Core

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#247**: JAR `emf_compat_core_1.21.1_2.0.0.jar`, mod id `emf_compat_core`, runtime `2.0.0`, SHA-1 `e22256acaaabc43d4043b119a748e8f52e27a451`.

## Propriedades do registro

- **Mod:** EMF Compat: Core
- **Arquivo JAR:** `emf_compat_core_1.21.1_2.0.0.jar`
- **Versão 1.21.1:** `2.0.0`
- **Categoria:** Biblioteca, Visual, Compat
- **Função:** Biblioteca client-side compartilhada da família EMF Compat que captura/restaura poses de player para impedir que Entity Model Features sobrescreva animações de outros mods.
- **Dependências:** Entity Model Features 3.3.5 e Entity Texture Features 7.2.1 estão presentes; o Core 2.0.0 requer EMF 3.3.2+ segundo o changelog oficial, portanto o baseline é satisfeito. Consumers físicos: EMF Compat: Create 2.0.0 e EMF Compat: Iron's Spells 2.0.0.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Client-only e altamente sensível à ordem de render/pose. Riscos: capture/restore em fase errada, braços/corpo/cabeça aplicados duas vezes, primeira e terceira pessoa divergirem, consumer em versão diferente, EMF update alterar pose pipeline e outro player-animation mod competir pela mesma parte do corpo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-core
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `emf_compat_core_1.21.1_2.0.0.jar`, mod id `emf_compat_core`, runtime 2.0.0 e SHA-1 `e22256acaaabc43d4043b119a748e8f52e27a451`. Stack físico relevante permanece EMF 3.3.5 / ETF 7.2.1.
- **Observações:** A documentação oficial 2.0.0 define o Core como required shared library para todos os EMF Compat addons. Ele não adiciona gameplay visível; captura a pose produzida por outro mod antes da sobreposição EMF e a restaura depois, preservando a animação externa.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #246: `emf_compat_core_1.21.1_2.0.0.jar` / runtime `2.0.0` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader; sem delta físico de versão neste ciclo.
- **Decisão:** Dependência
- **Sobreposição:** Não substitui EMF/ETF nem os consumers específicos. É infraestrutura de pose precedence; qualquer outro core de player-animation que toque os mesmos transforms precisa coexistência deliberada.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `emf_compat_core_1.21.1_2.0.0.jar` · mod id `emf_compat_core` · versão `2.0.0` · NeoForge 1.21.1 · **Client-only**.
## 1. Papel no modpack
EMF Compat: Core é a biblioteca compartilhada da família EMF Compat. Sua função é impedir que animações de player produzidas por outros mods sejam apagadas pelo Entity Model Features quando um resource pack animado assume a pose do modelo.
## 2. Authority / ownership
- **EMF:** animation/model layer do resource pack.
- **Mod externo:** produz sua própria pose ou animation state.
- **EMF Compat Core:** infraestrutura de captura/restauração da pose.
- **Consumer EMF Compat:** decide quais partes/situações precisam de compatibilidade para um mod específico.
O Core não deve inventar uma animation de gameplay por conta própria.
## 3. Pose capture/restore
A documentação oficial descreve o fluxo central:
1. outro mod produz a pose de body/arms/head;
2. a compat captura essa pose antes de EMF sobrescrevê-la;
3. após a aplicação EMF, a pose necessária é restaurada;
4. as partes que não precisam ser preservadas continuam sob controle do resource-pack animation.
Esse fluxo é sensível à ordem do render pipeline.
## 4. Primeira e terceira pessoa
O Core declara manter poses de primeira e terceira pessoa coerentes. Isso é importante em um pack com várias camadas de player model/animation: uma pose correta em third person não prova que first person também esteja correta.
## 5. Consumers físicos atuais
Neste lote estão presentes:
- **EMF Compat: Create 2.0.0**;
- **EMF Compat: Iron's Spells 2.0.0**.
O pack também possui EMF 3.3.5 e ETF 7.2.1. Cada consumer deve permanecer alinhado ao Core para evitar API/pipeline drift.
## 6. Sem gameplay próprio
A página oficial é explícita: o Core não adiciona visible gameplay features. Ele é um framework client-side. Portanto não deve ser documentado como provider de combat, spells, physics, items ou movement.
## 7. Render precedence
Quando duas compatibilidades querem preservar partes diferentes do mesmo modelo, a precedence de capture/restore precisa continuar determinística. O risco não é só visual: uma pose restaurada no momento errado pode apagar a animation que outro consumer pretendia preservar.
## 8. Resource packs
Fresh Animations e outros packs baseados em EMF continuam donos das animações que não são temporariamente substituídas pelo mod externo. O Core existe justamente para permitir coexistência em vez de desligar EMF globalmente.
## 9. Client-only boundary
O projeto oficial classifica o Core como **Client**. Não há motivo para usar o Core como fonte de truth no servidor. Dedicated server não deve depender dessa biblioteca para validar actions, spells, movement ou physics.
## 10. Lifecycle
Validar:
- resource reload;
- troca de resource pack;
- world join/rejoin;
- first/third person toggle;
- player respawn;
- dimension change;
- consumer animation start/stop;
- update isolado de EMF/Core/consumer.
## 11. Multiplayer
Cada cliente renderiza a pose a partir do state sincronizado do jogo/mod externo. O Core não cria um state gameplay compartilhado. Outros jogadores precisam apenas receber o state normal do provider e renderizá-lo corretamente localmente.
## 12. Riscos
1. pose capture em fase errada;
2. restore apagar animation EMF desejada;
3. mesma parte do corpo restaurada duas vezes;
4. first/third person divergirem;
5. consumer compilado para Core diferente;
6. update EMF mudar hooks internos;
7. resource reload manter pose/cache stale;
8. animation permanecer presa após action terminar;
9. outro player-animation mod competir na mesma fase;
10. Core ser instalado no servidor como se fosse gameplay dependency.
## 13. Matriz de testes
1. Cliente com EMF 3.3.5 + ETF 7.2.1 + Core 2.0.0.
2. Resource pack com player animation EMF ativo.
3. Testar consumer Create separadamente.
4. Testar consumer Iron's Spells separadamente.
5. Testar ambos carregados na mesma sessão.
6. Alternar first/third person durante animation.
7. Resource reload durante sessão.
8. Relog/respawn/dimension change.
9. Observar outro jogador executando animação suportada.
10. Update smoke-test de EMF/Core/consumer em instância de teste.
**Esta catalogação não afirma que esses testes foram executados.**
## 14. Evidências
- modlist física canônica: JAR/mod id/version/SHA-1 e consumers atuais;
- CurseForge oficial: release 2.0.0 NeoForge 1.21.1, Environment Client;
- descrição oficial: shared library, pose capture/restore e consistência first/third person.
> **Boundary canônico:** EMF Compat Core é authority apenas do **mecanismo de compatibilidade de poses**, nunca do gameplay que originou a animação.

## Atualização upstream e gate de manutenção — 11/10/2026

**Estado upstream:** release **`2.0.0`** continua sendo a correspondente à NeoForge 1.21.1, file ID **8793794**, sem upgrade indicado para este loader/MC. **Changelog oficial já aplicado ao baseline:** usa hooks próprios do EMF, exige EMF **3.3.2+** e restaura pose uma vez por jogador renderizado, em vez de uma vez por parte do corpo. **Gate conjunto:** EMF 3.3.5, ETF 7.2.1 e consumers Create/Iron's Spells 2.0.0; testar concorrência de animation layers, resource reload e mudança de perspectiva. Não atribuir gameplay ao Core. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-core/files/8793794

> **Autoridade e limites:** revisão documental baseada no último inventário físico histórico, sem novo dump de JARs/SHA e sem testes in-game nesta execução. Não alterar versão instalada, filename ou modlist sem evidência física atual.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** EMF Compat: Core
- **Último candidato 1.21.1 / NeoForge localizado:** **2.3.2 (Release, 16/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Validar EMF/ETF, compat modules, model/resource reload e client-only boundaries com os packs visuais ativos.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-core

## Lote 21–30 — revisão incremental do Core 2.0.0 para 2.3.2 (22/10/2026)

**Baseline:** EMF Compat Core 2.0.0 NeoForge Minecraft 1.21.1 (file 8793794). **Releases posteriores NeoForge 1.21.1 confirmadas no CurseForge:** **2.3.0** (08/10), **2.3.1** (15/10) e **2.3.2** (16/10). O intervalo 2.0.0→2.3.0 não contém arquivo 2.1 ou 2.2 desse **Core** neste catálogo; versões 2.x de outros addons EMF Compat não são versões do Core.

| Release | Delta documentado e grau de evidência |
|---|---|
| 2.0.0 — instalada | Hooks oficiais do EMF e requisito Entity Model Features 3.3.2+; pose restaurada uma vez por jogador, não por parte. Este comportamento é **baseline**, não fix das 2.3.x. |
| 2.3.0 | Configurações reorganizadas em grupos dobráveis e rolagem; múltiplos valores além de on/off, addon abre direto sua aba; helpers compartilhados de reach mãos/pés; transição suave de poses, correção de crouch e compatibilidade com Player Animation Library para não congelar animações do resource pack enquanto outra animação toca. A nota de **nova build Fabric** é exclusiva daquele loader e não é feature NeoForge. |
| 2.3.1 | Arquivo NeoForge 1.21.1 **publicado em 15/10**, porém **changelog individual confiável não isolado** na pesquisa. Não atribuir correções conhecidas do 2.3.2 retroativamente ao 2.3.1. |
| 2.3.2 | Tratamento mais resiliente de falha ao aplicar pose: ao ocorrer exceção, a pose daquela frame é ignorada, o erro é registrado uma vez, e o EMF **não para de animar o jogador por toda a sessão**. |

**Interações:** EMF, ETF, Fresh Animations Player Extension quando presente, módulos EMF Compat Create/Iron's Spells, animadores concorrentes Epic Fight/Punchy, reloading F3+T, mudança first↔third person, crouch/armor e settings. Não atribuir efeito de gameplay ao Core; ambiente descrito pelo autor é essencialmente render do cliente. **BLOQUEADO para revisão cumulativa completamente concluída** até vincular changelog ou diff de 2.3.1; o JAR instalado e a pasta permanecem inalterados.

**Fontes oficiais:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-core/files/all?gameVersionTypeId=6&page=1&pageSize=20&showAlphaFiles=hide ; https://modrinth.com/mod/emf-compat-core/version/2.3.0 ; https://modrinth.com/mod/emf-compat-core/version/2.3.2 ; https://github.com/victorkozhokin/emf-compat/blob/main/core/README.md .

## Revisão documental conclusiva — Core 2.0.0 → 2.4.0 (17/11/2026)

**Autoridade física:** posição #247, `emf_compat_core_1.21.1_2.0.0.jar`, runtime 2.0.0. Projeto oficial [EMF Compat: Core, CurseForge 1605113](https://www.curseforge.com/minecraft/mc-mods/emf-compat-core), autor stradat, categorias temáticas atuais **API and Library + Cosmetic**, pasta já correta. **Último release localizado NeoForge 1.21.1: 2.4.0**, de **09/11/2026** (CurseForge file **9524632**); não instalado. O source oficial [`core/neoforge/1.21.1/gradle.properties`](https://github.com/victorkozhokin/emf-compat/blob/main/core/neoforge/1.21.1/gradle.properties) confirma MC 1.21.1 e `mod_version=2.4.0`.

### Cadeia individual de releases NeoForge 1.21.1 após 2.0.0

| Release | Data | Delta comprovado no changelog oficial do Core | Evidência |
|---|---|---|---|
| **2.3.0** | 08/10/2026 | Config refatorada: múltiplos valores, grupos dobráveis, scroll, aba de addon acessível pelo mod list; helpers de reach mão/pé; **transições suaves de poses**, corrigido crouch/sinking durante animações; o EMF mantém o resource pack animado durante animações de Player Animation Library. A criação de novas builds Fabric não significa mudança de loader NeoForge. | [CHANGELOG Core 2.3.0](https://github.com/victorkozhokin/emf-compat/blob/main/core/CHANGELOG.md) |
| **2.3.1** | 15/10/2026 | **Corrige a fix de crouch** para não deslocar indevidamente armaduras de resource packs com modelos próprios (ex.: **3D Armor**), evitando braços e armadura projetados para a frente ao agachar. **Pendência antiga de identificação do delta agora encerrada pelo changelog oficial source.** | [CHANGELOG Core 2.3.1](https://github.com/victorkozhokin/emf-compat/blob/main/core/CHANGELOG.md) |
| **2.3.2** | 16/10/2026 | Erro ao aplicar pose passa a pular apenas aquele frame e registrar uma vez, sem interromper a animação EMF do jogador até o fim da sessão. | [CurseForge 9205847](https://www.curseforge.com/minecraft/mc-mods/emf-compat-core/files/9205847) |
| **2.4.0** | 09/11/2026 | Adiciona **shared player states** para addons e resource packs, expostos em variáveis EMF `emfc_<state>` (0 se não definido); corrige movimento vertical falso em **sublevels móveis de Sable** (orientação em deck inclinado, landing false positive, apenas movimento próprio deve contar), mediante configuração *Sable: steady height on sub-levels*; corrige flicker quando animação de **Better Combat** ou outro mod termina, mantendo animação EMF até o fim do fade-out. | [CHANGELOG do autor, release 2.4.0](https://github.com/victorkozhokin/emf-compat/blob/main/core/CHANGELOG.md), [catálogo de builds](https://www.curseforge.com/minecraft/mc-mods/emf-compat-core/files/all) |

**Exaustividade:** a lista NeoForge 1.21.1 após o baseline contém **2.3.0 → 2.3.1 → 2.3.2 → 2.4.0** no catálogo consultado, sem build 2.1.x/2.2.x própria do Core. O [source changelog do autor](https://github.com/victorkozhokin/emf-compat/blob/main/core/CHANGELOG.md) discrimina nominalmente todas as etapas, inclusive a antes opaca 2.3.1; a redação mais nova sobre 2.4.0 está restrita ao core compat, não prova que cada addon instalado já implementa as variáveis de estado.

**Gates para o pack:** Sable 2.0.5, animações do recurso Fresh Animations, Better Combat/Epic Fight, Iron's Spells addon e Create addon (ambos **2.0.0 instalados**) precisam ser validados como conjunto. As APIs/states e configurações da 2.4.0 não podem ser descritas como ativas no runtime 2.0.0. Testar EMF/ETF, body parts, armadura 3D se instalada, crouch, first/third person, resource reload, sublevel em rotação, jumping/landing, fade-out de animação e tela de settings. Nenhum teste nem JAR foi alterado.

**Resultado:** auditoria documental completa até **2.4.0**; **instalação física e testes permanecem pendentes**, basename `✅-emf-compat-core v2.0.0.md` preservado.
