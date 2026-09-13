# Dynamic Trees - Terralith

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81d59cbef5b54e51522e  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees - Terralith
- **Arquivo JAR:** `dtterralith-1.3.0.jar`
- **Versão 1.21.1:** `1.3.0`
- **Categoria:** Compat; Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** Modlist/JAR físico atual + source oficial DynamicTreesTeam/DynamicTrees-Terralith, branch 1.21.1.
- **Função:** Bridge Terralith ↔ Dynamic Trees: adapta o worldgen arbóreo suportado do Terralith para a infraestrutura de árvores dinâmicas do Dynamic Trees.
- **Dependências:** Runtime físico atual: Dynamic Trees 1.7.2 + Dynamic Trees Plus 1.3.2 + Terralith 2.6.2. Source oficial 1.3.0 baseline: Dynamic Trees 1.6.0, Dynamic Trees Plus 1.3.1-BETA01 e Terralith 2.5.8. As versões do source são referência de build; a modlist física é autoridade do runtime.
- **Compatibilidade/Riscos:** Bridge sensível a drift de IDs/biomas/features do Terralith e ao formato/worldgen do Dynamic Trees. Runtime atual DT 1.7.2 / DTP 1.3.2 / Terralith 2.6.2 difere do baseline 1.6.0 / 1.3.1-BETA01 / 2.5.8. Atualizações podem misturar árvores estáticas/dinâmicas e gerar fronteiras entre chunks antigos/novos.
- **Sobreposição:** Sobreposição intencional com a geração arbórea do Terralith; pode disputar os mesmos biomas/features com datapacks, outros worldgen mods ou addons de árvores. Validar cadeia final de replacement.
- **Observações:** Versão do bridge 1.3.0 corresponde entre artefato físico e source oficial. Runtime atual das bases: Dynamic Trees 1.7.2, Dynamic Trees Plus 1.3.2 e Terralith 2.6.2; source baseline: 1.6.0 / 1.3.1-BETA01 / 2.5.8. Dynamic Trees Plus aparece no build; não promover a requisito runtime além do que a metadata física confirmar.
- **Procedência:** `dtterralith-1.3.0.jar` confirmado fisicamente; source oficial 1.21.1 confirma mod_id `dtterralith`, versão 1.3.0, autores e baseline de desenvolvimento.
- **Histórico da decisão:** Bridge mantido enquanto Terralith e Dynamic Trees permanecerem ativos. Dossiê reconstruído em 08/09/2026 a partir do JAR físico 1.3.0 e source oficial 1.21.1 correspondente.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — identidade, autoridade, bridge de worldgen, baseline de dependências, data layer, lifecycle, multiplayer, riscos e matriz de regressão Terralith↔Dynamic Trees catalogados.
- **Data da última decisão:** 2026-09-08.

## 1. Resumo executivo
Dynamic Trees Terralith é o bridge de compatibilidade entre Terralith e Dynamic Trees. Ele existe para fazer o worldgen arbóreo relevante do Terralith participar do sistema de árvores dinâmicas, evitando que a paisagem use apenas árvores estáticas onde o addon possui integração.

> **Autoridade desta ficha:** o JAR físico instalado é `dtterralith-1.3.0.jar`, com versão física/runtime 1.3.0. O source oficial 1.21.1 também declara 1.3.0, portanto há correspondência direta de identidade entre modlist e source.

## 2. Identidade técnica
- **Arquivo físico:** `dtterralith-1.3.0.jar`
- **Mod ID:** `dtterralith`
- **Nome:** Dynamic Trees Terralith
- **Versão:** 1.3.0
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Autores declarados:** DJS, Max Hyper
- **Crédito declarado:** m3t4f1v3
- **Licença:** MIT
- **Tipo de release no source:** stable

## 3. Papel no pack
Terralith continua sendo a autoridade sobre biomas e desenho geral de worldgen; Dynamic Trees continua sendo a autoridade sobre crescimento e estrutura dinâmica de árvores. `dtterralith` é a camada de tradução entre esses sistemas. Não substitui Terralith, não substitui Dynamic Trees e não é um gerador de biomas independente.

## 4. Dependências e baseline de desenvolvimento
O source oficial 1.21.1 declara como referências de build **Dynamic Trees 1.6.0**, **Dynamic Trees Plus 1.3.1-BETA01** e **Terralith 2.5.8**, em NeoForge 21.1.208. A modlist física atual usa **Dynamic Trees 1.7.2**, **Dynamic Trees Plus 1.3.2** e **Terralith 2.6.2**. A presença de Dynamic Trees Plus no build é evidência de integração usada pelo projeto, mas não deve ser promovida a requisito runtime obrigatório além do que a metadata física confirmar.

O drift 1.6.0→1.7.2, 1.3.1-BETA01→1.3.2 e 2.5.8→2.6.2 é ponto de regressão de worldgen; não autoriza alterar a combinação física apenas para igualar o buildscript.

## 5. Superfície técnica
A arquitetura do addon é orientada a dados e integração de worldgen. Ele usa o ecossistema de registries/recursos do Dynamic Trees para associar árvores e regras de geração a biomas/features do Terralith. O resultado esperado é que as áreas suportadas do Terralith usem árvores dinâmicas compatíveis com crescimento, ramos e ciclo de vida do Dynamic Trees.

## 6. Worldgen e replacement
O risco principal é o mapeamento entre IDs/biomas/features do Terralith e as definições utilizadas pelo Dynamic Trees. Se Terralith renomear, remover ou reorganizar features, o addon pode deixar de substituir uma árvore, substituir um alvo incorreto ou produzir mistura entre árvores estáticas e dinâmicas.

Esse tipo de integração deve ser validado em chunks novos. Chunks já gerados não são evidência suficiente de que a integração atual continua funcionando.

## 7. Camada de dados
A integração depende de recursos de árvore/worldgen carregados junto aos datapacks. Alterações externas em biome modifiers, feature cancellers, tags ou recursos de árvores podem mudar o resultado final mesmo sem trocar o JAR do addon.

Por isso, a auditoria operacional deve considerar não apenas o trio Terralith/Dynamic Trees/dtterralith, mas também datapacks e mods que alterem as mesmas regiões do pipeline de worldgen.

## 8. Ciclo de vida
Na inicialização, o addon entra depois das dependências necessárias e disponibiliza seus tipos/recursos de compatibilidade. Na fase de carga de dados/worldgen, as regras do bridge são combinadas com Terralith e Dynamic Trees. Durante a exploração, a geração de chunks novos aplica a composição resultante.

## 9. Cliente e servidor
A geração de mundo é autoridade do servidor, inclusive em singleplayer integrado. Os clientes precisam da composição compatível de mods/registries para entrar em multiplayer. Diferenças apenas visuais não eliminam a necessidade de manter o conjunto correto quando há registros e worldgen associados.

## 10. Multiplayer e persistência de mundo
O efeito mais importante em multiplayer é determinístico no servidor: chunks novos devem ser gerados a partir da mesma combinação de versão/config/data. Atualizar Terralith, Dynamic Trees ou o bridge no meio de um mundo pode criar fronteiras visuais entre chunks antigos e novos.

## 11. Integrações e sobreposição
A sobreposição é deliberada com o worldgen arbóreo do Terralith. Outros mods de biomas, datapacks de worldgen ou addons do Dynamic Trees podem tocar os mesmos biomas/features. O objetivo não é eliminar toda sobreposição, mas garantir que exista apenas uma cadeia final coerente de geração por feature.

## 12. Configuração e tuning
Este addon não deve ser tratado como módulo isolado de configuração. Ajustes devem ser feitos considerando a combinação Terralith + Dynamic Trees + datapacks de árvore/worldgen. Antes de desabilitar uma regra de replacement, verificar se isso reintroduz a árvore estática correspondente.

## 13. Riscos operacionais
- **Drift de Terralith:** IDs ou composição de biomas/features podem mudar entre versões.
- **Drift de Dynamic Trees:** mudanças no formato de espécies/famílias/worldgen podem invalidar recursos do bridge.
- **Baseline diferente:** source 1.3.0 referencia Terralith 2.5.8; a versão física do pack deve ser testada independentemente.
- **Chunks mistos:** updates de worldgen criam diferenças entre áreas antigas e novas.
- **Interferência externa:** datapacks e outros worldgen mods podem alterar/cancelar os mesmos alvos.

## 14. Matriz mínima de testes
1. Boot sem erro de dependência, registry ou datapack.
2. Criar mundo novo com seed de teste e explorar múltiplos biomas Terralith.
3. Confirmar presença de árvores dinâmicas nas integrações suportadas.
4. Procurar duplicação de árvore estática + dinâmica no mesmo contexto.
5. Plantar/crescer árvores suportadas quando aplicável.
6. Reiniciar o mundo e repetir geração de novos chunks.
7. Testar reload de datapack se houver customização de dados.
8. Comparar chunk antigo e novo após qualquer atualização de Terralith/Dynamic Trees.
9. Em multiplayer, validar conexão, geração e ausência de registry mismatch.

## 15. Evidência e limitações
A identidade física `dtterralith-1.3.0.jar` e a versão 1.3.0 são confirmadas pela modlist/JAR atual. O source oficial da branch 1.21.1 confirma mod ID, versão, autores e baseline de desenvolvimento. A documentação técnica desta ficha evita enumerar espécies/features específicas que não tenham sido confirmadas de forma inequívoca no artefato/source correspondente.

**Limitação importante:** as versões de dependências declaradas no source são baseline de desenvolvimento; a compatibilidade efetiva com as versões físicas do pack só pode ser aprovada por teste.

## 16. Conclusão operacional
**Manter** enquanto Terralith e Dynamic Trees forem componentes ativos. Tratar qualquer atualização de Terralith ou Dynamic Trees como alteração de worldgen que exige regressão em mundo novo antes de promover a nova combinação.
