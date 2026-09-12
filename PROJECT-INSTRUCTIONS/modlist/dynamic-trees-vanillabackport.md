# Dynamic Trees–VanillaBackport

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c469db9f0db8124ba9cce91561525c4  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees–VanillaBackport
- **Arquivo JAR:** `dtvanillabackport-1.21.1-1.6.0.jar`
- **Versão 1.21.1:** `1.6.0`
- **Categoria:** Compat; Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** Modlist/JAR físico atual como autoridade de presença/versão + source público dannykim2011/DynamicTrees-VanillaBackport branch 1.21.1 apenas como contexto estrutural não exato.
- **Função:** Bridge Dynamic Trees ↔ VanillaBackport: adapta o conteúdo arbóreo suportado do VanillaBackport à infraestrutura dinâmica de crescimento/worldgen do Dynamic Trees.
- **Dependências:** Runtime físico atual: Dynamic Trees 1.7.2 + VanillaBackport 1.1.7.10. Source/tag público exato da build bridge 1.6.0 não foi confirmado; a branch pública 1.21.1 consultada está em 1.7.0 e serve apenas como contexto estrutural, não como inventário do binário instalado.
- **Compatibilidade/Riscos:** Risco principal de version drift: bridge físico 1.6.0 enquanto a branch pública atual declara 1.7.0; runtime das bases é Dynamic Trees 1.7.2 + VanillaBackport 1.1.7.10. Não projetar classes/species/features da 1.7.0 sobre a instalação; validar worldgen, replacement e chunks novos.
- **Sobreposição:** Sobreposição intencional com o conteúdo arbóreo/worldgen do VanillaBackport; pode conflitar com outros bridges ou datapacks que adaptem os mesmos alvos. Não substitui Dynamic Trees nem VanillaBackport.
- **Observações:** Fail-closed aplicado: a build instalada é 1.6.0 e o filename físico atual é `dtvanillabackport-1.21.1-1.6.0.jar`. O source público 1.21.1 está em 1.7.0; conteúdo específico da 1.7.0 não é tratado como inventário da 1.6.0.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dtvanillabackport-1.21.1-1.6.0.jar`, mod id `dtvanillabackport`, versão 1.6.0, Dynamic Trees 1.7.2 e VanillaBackport 1.1.7.10. Source/tag público exato 1.6.0 não foi confirmado; branch pública consultada está em 1.7.0.
- **Histórico da decisão:** Bridge mantido enquanto Dynamic Trees e VanillaBackport permanecerem ativos. Em 08/09/2026 a ficha foi reconstruída pela autoridade física 1.6.0 e a divergência com o source público 1.7.0 foi registrada sem inferência.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — identidade física 1.6.0, autoridade, papel de bridge, lifecycle, worldgen, multiplayer, riscos, matriz de testes e limitação de source exato catalogados.
- **Data da última decisão:** 2026-09-08.

## 1. Resumo executivo
Dynamic Trees for VanillaBackport é o bridge entre Dynamic Trees e VanillaBackport. No pack ele deve ser tratado estritamente como camada de compatibilidade: VanillaBackport fornece o conteúdo backportado; Dynamic Trees fornece a infraestrutura de árvores dinâmicas; este addon adapta o conteúdo arbóreo suportado entre os dois.

> **Autoridade desta ficha:** o JAR físico instalado é `dtvanillabackport-1.21.1-1.6.0.jar`, versão 1.6.0. O source público atualmente disponível na branch 1.21.1 declara 1.7.0; portanto ele **não é uma fonte exata para o comportamento da build instalada 1.6.0** e só pode ser usado como contexto estrutural.

## 2. Identidade técnica
- **Arquivo físico:** `dtvanillabackport-1.21.1-1.6.0.jar`
- **Versão instalada:** 1.6.0
- **Mod ID estrutural no source público atual:** `dtvanillabackport`
- **Nome:** Dynamic Trees for VanillaBackport
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Projeto/source público atual:** dannykim2011/DynamicTrees-VanillaBackport

## 3. Papel no pack
O addon não substitui nenhuma das dependências. Sua função é manter o conteúdo arbóreo do VanillaBackport compatível com os modelos de crescimento, ramos, folhas, propagação e worldgen do Dynamic Trees onde a build instalada oferece suporte.

A autoridade funcional é dividida: VanillaBackport define o conteúdo backportado; Dynamic Trees define o sistema dinâmico; o bridge faz a adaptação.

## 4. Dependências
A dependência funcional confirmável pelo papel do projeto é **Dynamic Trees + VanillaBackport**. A modlist física atual usa **Dynamic Trees 1.7.2** e **VanillaBackport 1.1.7.10**. O source público atual do bridge está em 1.7.0 e referencia as mesmas bases apenas como contexto estrutural; números, classes e conteúdo dessa branch não devem ser atribuídos à build física 1.6.0 sem evidência do artefato correspondente.

## 5. Conteúdo registrado — limite de evidência
Não é seguro enumerar nesta ficha espécies, blocos, features ou classes específicas como pertencentes à build 1.6.0 com base apenas na branch pública 1.7.0. A catalogação mantém o escopo funcional do bridge e registra explicitamente essa lacuna, em vez de projetar conteúdo mais novo sobre o JAR instalado.

## 6. Integração com Dynamic Trees
O comportamento esperado de um bridge deste tipo é fornecer definições que permitam ao Dynamic Trees representar o conteúdo arbóreo suportado do VanillaBackport dentro de seu pipeline. Contudo, quais espécies e quais hooks existem **na 1.6.0** devem ser validados no JAR/source exato antes de serem tratados como inventário fechado.

## 7. Worldgen e replacement
A área mais sensível é a geração de mundo. Se VanillaBackport mudar IDs, features ou condições de placement, uma versão incompatível do bridge pode deixar geração estática residual, duplicar conteúdo ou simplesmente deixar de substituir o alvo esperado.

Chunks já existentes não são prova suficiente de funcionamento atual. Toda validação de worldgen deve incluir chunks novos ou mundo novo.

## 8. Configuração e data layer
Como bridge, sua superfície de configuração depende principalmente da composição entre Dynamic Trees, VanillaBackport e os dados do próprio addon. Datapacks externos que alterem as mesmas features podem mudar o resultado mesmo quando o JAR permanece igual.

Nenhum nome de arquivo/config específico da build 1.6.0 é afirmado sem confirmação direta.

## 9. Cliente e servidor
Worldgen e crescimento de árvores são autoritativos no servidor, inclusive no servidor integrado do singleplayer. Assets necessários à representação aparecem no cliente. Em multiplayer, a composição de registries e mods deve ser compatível entre as partes.

## 10. Lifecycle
A integração precisa estar disponível durante a inicialização/registro das dependências e durante a carga de dados/worldgen. Depois, o efeito persiste no mundo através dos blocos e chunks gerados. Alterações de versão podem produzir diferença entre áreas antigas e novas sem necessariamente quebrar o carregamento do save.

## 11. Multiplayer e persistência
O servidor decide worldgen e estados de árvore. Mismatch entre versões ou ausência do bridge em um lado pode provocar erro de conexão, registry mismatch ou comportamento inconsistente. Em saves persistentes, atualizações devem ser testadas antes de explorar novas áreas importantes.

## 12. Integrações e sobreposição
A sobreposição com VanillaBackport é proposital: o addon intercepta/adapta a camada arbórea suportada. Pode haver conflito com outros bridges do Dynamic Trees ou datapacks que tentem adaptar os mesmos conteúdos. Não deve existir uma segunda solução de replacement concorrente sem teste explícito.

## 13. Riscos operacionais
- **Version drift:** build instalada 1.6.0, source público atual 1.7.0.
- **Atribuição indevida de conteúdo:** não assumir recursos da 1.7.0 como presentes na 1.6.0.
- **Worldgen:** mudanças de IDs/features no VanillaBackport podem quebrar replacement.
- **Dynamic Trees:** mudanças de API/formato de dados podem invalidar o bridge.
- **Chunks mistos:** updates podem alterar apenas geração futura.
- **Datapacks concorrentes:** podem tocar os mesmos alvos de worldgen.

## 14. Matriz mínima de testes
1. Boot com Dynamic Trees + VanillaBackport + bridge sem erro de dependência/registry.
2. Criar mundo novo e localizar conteúdo arbóreo do VanillaBackport suportado pela build.
3. Verificar se a representação é dinâmica e se não há duplicação estática concorrente.
4. Plantar/crescer conteúdo suportado quando identificável no jogo.
5. Cortar e verificar drops/propagação sem duplicação ou ausência inesperada.
6. Reiniciar o save e repetir o ciclo.
7. Gerar chunks novos após atualização e comparar com chunks antigos.
8. Testar servidor dedicado/cliente com a mesma composição.

## 15. Evidência e limitações
A modlist/JAR físico atual confirma o arquivo `dtvanillabackport-1.21.1-1.6.0.jar` e a versão 1.6.0. O source público atual da branch 1.21.1 declara 1.7.0 e, portanto, não é considerado prova exata do inventário técnico da build instalada.

**Limitação registrada:** não foi confirmado source/tag público exato 1.6.0 nesta auditoria. Por isso esta ficha não atribui à instalação 1.6.0 mecânicas, classes, espécies ou requirements que só estejam verificáveis na 1.7.0.

## 16. Conclusão operacional
**Manter** enquanto Dynamic Trees e VanillaBackport permanecerem ativos, mas tratar qualquer atualização como alteração de integração/worldgen. Antes de promover uma versão futura, validar em mundo novo e atualizar a ficha apenas com evidência da build correspondente.
