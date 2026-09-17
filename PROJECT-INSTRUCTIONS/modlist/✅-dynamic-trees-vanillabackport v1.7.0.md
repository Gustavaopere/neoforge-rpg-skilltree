# Dynamic Trees–VanillaBackport

## Propriedades do registro

- **Mod:** Dynamic Trees–VanillaBackport
- **Arquivo JAR:** dtvanillabackport-1.21.1-1.7.0.jar
- **Versão 1.21.1:** 1.7.0
- **Categoria:** Compat; Worldgen
- **Função:** Bridge Dynamic Trees ↔ VanillaBackport: adapta o conteúdo arbóreo suportado do VanillaBackport à infraestrutura dinâmica de crescimento/worldgen do Dynamic Trees.
- **Dependências:** Dynamic Trees 1.7.2 + VanillaBackport 1.1.7.10 físicos. Dynamic Trees Plus 1.3.2 está presente e habilita a superfície opcional de Cactus Flower documentada pelo projeto.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Bridge de worldgen/content. Riscos: version drift entre Dynamic Trees/VanillaBackport/DT Plus, replacement duplicado, chunks mistos, datapacks concorrentes e optional integration de Cactus Flower. A build física 1.7.0 coincide com a distribuição oficial 1.21.1.
- **Fonte:** Modlist/JAR físico atual como autoridade de presença/versão + source público dannykim2011/DynamicTrees-VanillaBackport branch 1.21.1 apenas como contexto estrutural não exato.
- **Procedência:** modlist física de 17/09/2026 + distribuição/documentação oficial Dynamic Trees for VanillaBackport 1.7.0 para NeoForge 1.21.1 + stack físico Dynamic Trees 1.7.2 / VanillaBackport 1.1.7.10 / Dynamic Trees Plus 1.3.2.
- **Observações:** Runtime físico 1.7.0. A distribuição oficial atual sustenta Pale Oak, Creaking Heart e integração opcional de Cactus Flower com Dynamic Trees Plus. O arquivo 1.7.0 não traz changelog detalhado próprio; nenhum delta extra foi inventado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — bridge atualizado para 1.7.0; a antiga limitação de source posterior à build instalada foi encerrada para a release física atual; Pale Oak, Creaking Heart e Cactus Flower/DT Plus documentados sem inventar changelog.
- **Decisão:** Manter
- **Histórico da decisão:** Bridge mantido enquanto Dynamic Trees e VanillaBackport permanecerem ativos. Em 08/09/2026 a ficha foi reconstruída pela autoridade física 1.6.0 e a divergência com o source público 1.7.0 foi registrada sem inferência.
- **Sobreposição:** Sobreposição intencional com o conteúdo arbóreo/worldgen do VanillaBackport; pode conflitar com outros bridges ou datapacks que adaptem os mesmos alvos. Não substitui Dynamic Trees nem VanillaBackport.
- **Data da última decisão:** 2026-09-08

## 1. Resumo executivo
Dynamic Trees for VanillaBackport é o bridge entre Dynamic Trees e VanillaBackport. No pack ele deve ser tratado estritamente como camada de compatibilidade: VanillaBackport fornece o conteúdo backportado; Dynamic Trees fornece a infraestrutura de árvores dinâmicas; este addon adapta o conteúdo arbóreo suportado entre os dois.
> **Autoridade desta ficha:** o JAR físico instalado é `dtvanillabackport-1.21.1-1.7.0.jar`, versão 1.7.0. A distribuição oficial 1.7.0 para NeoForge 1.21.1 corresponde à build física. Branches/source de desenvolvimento posteriores não substituem essa autoridade.
## 2. Identidade técnica
- **Arquivo físico:** `dtvanillabackport-1.21.1-1.7.0.jar`
- **Versão instalada:** 1.7.0
- **Mod ID:** `dtvanillabackport`
- **Nome:** Dynamic Trees for VanillaBackport
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Projeto/source público:** dannykim2011/DynamicTrees-VanillaBackport
## 3. Papel no pack
O addon não substitui nenhuma das dependências. Sua função é manter o conteúdo arbóreo do VanillaBackport compatível com os modelos de crescimento, ramos, folhas, propagação e worldgen do Dynamic Trees.
A autoridade funcional é dividida: VanillaBackport define o conteúdo backportado; Dynamic Trees define o sistema dinâmico; o bridge faz a adaptação.
## 4. Dependências
A dependência funcional confirmada é **Dynamic Trees + VanillaBackport**. A modlist física atual usa **Dynamic Trees 1.7.2** e **VanillaBackport 1.1.7.10**.
A documentação oficial também registra **Dynamic Trees Plus** como integração opcional para Cactus Flower; o pack possui Dynamic Trees Plus 1.3.2, portanto essa superfície é relevante à matriz atual.
## 5. Conteúdo registrado — build 1.7.0
A documentação oficial da linha atual confirma:
- suporte a **Pale Oak** dentro do sistema Dynamic Trees;
- suporte completo ao **Creaking Heart** no contexto compatível;
- crescimento dinâmico, árvores que caem, seeds/saplings e integração com worldgen;
- suporte a **Cactus Flower** em Pillar/Pipe Cacti gerados no mundo quando Dynamic Trees Plus está presente.
Não se presume que qualquer feature de uma branch de desenvolvimento posterior esteja incluída além do que a distribuição 1.7.0/documentação atual sustentam.
## 6. Integração com Dynamic Trees
O bridge fornece definições que permitem ao Dynamic Trees representar o conteúdo arbóreo suportado do VanillaBackport dentro de seu pipeline. Crescimento, ramos, folhas, propagação e falling-tree behavior continuam pertencendo ao sistema Dynamic Trees; IDs/conteúdo backportado continuam pertencendo ao VanillaBackport.
## 7. Worldgen e replacement
A área mais sensível é a geração de mundo. Se VanillaBackport mudar IDs, features ou condições de placement, uma versão incompatível do bridge pode deixar geração estática residual, duplicar conteúdo ou deixar de substituir o alvo esperado.
Chunks já existentes não são prova suficiente de funcionamento atual. Toda validação de worldgen deve incluir chunks novos ou mundo novo. Pale Oak/Creaking Heart e Cactus Flower, quando aplicável, entram nessa regressão.
## 8. Configuração e data layer
Como bridge, sua superfície de configuração depende da composição entre Dynamic Trees, VanillaBackport, Dynamic Trees Plus e os dados do próprio addon. Datapacks externos que alterem as mesmas features podem mudar o resultado mesmo quando o JAR permanece igual.
Nenhum nome de arquivo/config não confirmado é inventado nesta ficha.
## 9. Cliente e servidor
Worldgen e crescimento de árvores são autoritativos no servidor, inclusive no servidor integrado do singleplayer. Assets necessários à representação aparecem no cliente. Em multiplayer, a composição de registries e mods deve ser compatível entre as partes.
## 10. Lifecycle
A integração precisa estar disponível durante inicialização/registro das dependências e carga de data/worldgen. Depois, o efeito persiste no mundo através dos blocos e chunks gerados. Alterações de versão podem produzir diferenças entre áreas antigas e novas sem necessariamente impedir o carregamento do save.
## 11. Multiplayer e persistência
O servidor decide worldgen e estados de árvore. Mismatch entre versões ou ausência do bridge em um lado pode provocar erro de conexão, registry mismatch ou comportamento inconsistente. Em saves persistentes, atualizações devem ser testadas antes de explorar novas áreas importantes.
## 12. Integrações e sobreposição
A sobreposição com VanillaBackport é proposital: o addon intercepta/adapta a camada arbórea suportada. Pode haver conflito com outros bridges do Dynamic Trees ou datapacks que tentem adaptar os mesmos conteúdos. Não deve existir uma segunda solução de replacement concorrente sem teste explícito.
Dynamic Trees Plus é integração opcional concreta no pack atual para o suporte de Cactus Flower; não deve ser confundido com hard dependency universal do bridge.
## 13. Riscos operacionais
- **Version drift:** Dynamic Trees/VanillaBackport/DT Plus podem avançar independentemente do bridge 1.7.0.
- **Atribuição indevida de conteúdo:** não assumir recursos de source de desenvolvimento posterior como presentes na 1.7.0.
- **Worldgen:** mudanças de IDs/features no VanillaBackport podem quebrar replacement.
- **Dynamic Trees:** mudanças de API/formato de dados podem invalidar o bridge.
- **Chunks mistos:** updates podem alterar apenas geração futura.
- **Datapacks concorrentes:** podem tocar os mesmos alvos de worldgen.
- **Optional DT Plus:** Cactus Flower precisa degradar corretamente se a integração opcional mudar.
## 14. Matriz mínima de testes
1. Boot com Dynamic Trees + VanillaBackport + bridge 1.7.0 sem erro de dependência/registry.
2. Criar mundo novo e localizar Pale Oak/conteúdo arbóreo suportado.
3. Verificar representação dinâmica e ausência de duplicação estática concorrente.
4. Plantar/crescer conteúdo suportado quando identificável no jogo.
5. Cortar e verificar drops/propagação/falling behavior sem duplicação.
6. Validar Creaking Heart no contexto suportado.
7. Com Dynamic Trees Plus 1.3.2, validar Cactus Flower em Pillar/Pipe Cacti gerados no mundo.
8. Reiniciar o save e repetir o ciclo.
9. Gerar chunks novos após atualização e comparar com chunks antigos.
10. Testar servidor dedicado/cliente com a mesma composição.
## 15. Evidência e limitações
A modlist física de 17/09/2026 confirma `dtvanillabackport-1.21.1-1.7.0.jar`, Dynamic Trees 1.7.2, VanillaBackport 1.1.7.10 e Dynamic Trees Plus 1.3.2.
A distribuição oficial 1.7.0 para NeoForge 1.21.1 corresponde à build instalada. A página oficial documenta Pale Oak, Creaking Heart, comportamento Dynamic Trees e Cactus Flower com DT Plus. O arquivo 1.7.0 não publica changelog detalhado próprio; por isso nenhum delta adicional é inventado.
## 16. Conclusão operacional
**Manter** enquanto Dynamic Trees e VanillaBackport permanecerem ativos, mas tratar qualquer atualização como alteração de integração/worldgen. Antes de promover uma versão futura, validar em mundo novo e atualizar a ficha apenas com evidência da build correspondente.
