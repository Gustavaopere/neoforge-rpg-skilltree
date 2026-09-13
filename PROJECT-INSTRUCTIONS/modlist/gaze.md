# Gaze — 1.1.7.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81c6b476f8c7e53cb009  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Gaze
- **Arquivo JAR:** `gaze-1.1.7.1.jar`
- **Versão 1.21.1:** `1.1.7.1`
- **Categoria:** Magia; RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/gaze
- **Função:** Addon de conteúdo/compatibilidade para Malum; estende o ecossistema Malum sem assumir autoridade sobre seus sistemas-base.
- **Dependências:** Malum (release 1.1.7.1 alinhada a Malum 1.8)
- **Compatibilidade/Riscos:** Acoplamento direto a Malum; pack físico usa Malum 1.8.2, enquanto a release Gaze 1.1.7.1 declara atualização para a linha Malum 1.8. Riscos: version drift, client/server mismatch, registry/data drift e double-processing em bridges futuras. Fafnir/Anima Bestiary é regression note upstream, não integração ativa confirmada do pack.
- **Sobreposição:** Malum permanece authority dos sistemas-base; Gaze é authority apenas do conteúdo/integrações que adiciona. Fafnir/Anima Bestiary aparecem como regression note da release 1.1.7.1, mas não foram localizados como mods top-level na modlist física atual.
- **Observações:** Dossiê revalidado em 09/09/2026; source exato da 1.1.7.1 não foi obtido para enumeração interna completa. Runtime não testado.
- **Procedência:** Modlist física/JAR + distribuição oficial pública + changelog/release pública
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Gaze 1.1.7.1; integração Malum 1.8, hotfix Fafnir/Anima Bestiary, escopo confirmado publicamente, riscos de version drift e matriz de testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔬 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta apenas o que pôde ser confirmado para o JAR físico `gaze-1.1.7.1.jar` e fontes públicas correspondentes. Onde o source exato da 1.1.7.1 não estava disponível para inspeção, não são inventados classes, registries, IDs ou números internos.

## 1. Identidade
- **Nome:** Gaze
- **Arquivo físico:** `gaze-1.1.7.1.jar`
- **Versão instalada:** `1.1.7.1`
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Natureza:** addon de conteúdo/compatibilidade para **Malum**.
- **Pin de release:** a distribuição pública da versão 1.1.7.1 é a referência funcional usada nesta ficha.

## 2. Papel no modpack
Gaze estende o ecossistema de **Malum**. A release 1.1.7.1 atualiza a integração para **Malum 1.8** e inclui correção relacionada a **Fafnir** e ao **Anima Bestiary**. Portanto, Gaze não deve ser tratado como autoridade independente sobre o sistema de spirits, progressão ou infraestrutura-base de Malum.

## 3. Autoridade / ownership
- **Malum** continua sendo autoridade sobre seus sistemas-base, registries centrais e contratos de gameplay.
- **Gaze** é autoridade somente sobre o conteúdo e as integrações que ele próprio injeta/adiciona ao ecossistema Malum.
- Integrações com outros mods devem preservar essa separação; não há base para assumir que Gaze substitui ou redefine internals de Malum.

## 4. Conteúdo confirmado
A evidência pública da 1.1.7.1 confirma o alvo de compatibilidade com **Malum 1.8** e um hotfix envolvendo **Fafnir / Anima Bestiary**.
A fonte pública disponível para esta auditoria não expõe um inventário confiável e versionado de todos os itens, blocos, entidades, recipes, efeitos ou registries internos da 1.1.7.1. Esses elementos não são enumerados por inferência.

## 5. Sistemas internos
O comportamento operacional confirmado é o de addon integrado a Malum. O hotfix da 1.1.7.1 indica que o mod participa de uma cadeia de compatibilidade em que objetos/conteúdo de Fafnir precisam ser reconhecidos corretamente pelo Anima Bestiary.
Sem source exato auditável da 1.1.7.1, não é seguro atribuir nomes de classes, mixins, eventos ou registries específicos a essa implementação.

## 6. Configuração e dados
Não foi confirmada nesta auditoria uma superfície de configuração própria estável e versionada da 1.1.7.1. Também não foi localizado material suficiente para afirmar formatos específicos de datapack, tags, codecs, SavedData, attachments ou data components próprios.
Regra de manutenção: qualquer configuração encontrada em runtime deve ser tratada como descoberta a validar contra o JAR físico antes de entrar como contrato da ficha.

## 7. Client / Server
Como addon de conteúdo de Malum, Gaze deve ser validado em ambos os lados quando instalado no servidor. Não há evidência suficiente para classificá-lo como client-only.
Não foram confirmados nesta auditoria renderers, keybinds ou pacotes de rede específicos da versão 1.1.7.1; esses detalhes permanecem fail-closed.

## 8. Lifecycle
Pontos que exigem atenção operacional:
- boot de dedicated server com Malum 1.8;
- registro de conteúdo durante startup;
- datapack/resource reload quando recipes/tags/visuals envolvidos forem recarregados;
- save/reload e relog quando conteúdo de bestiary/progressão depender de estado persistente do provider;
- atualização de versão do Malum, por ser dependência funcional direta.
Não se afirma que Gaze mantém estado próprio persistente sem evidência do JAR/source.

## 9. Multiplayer
Em multiplayer, a autoridade de estado deve permanecer com o servidor e com os providers originais do sistema Malum. O principal risco é divergência de conteúdo/registro entre cliente e servidor quando versões de Gaze ou Malum não coincidem.
Também deve ser validado que integrações com bestiary não concedam, processem ou exibam entradas de forma duplicada em cenários com múltiplos jogadores.

## 10. Integrações concretas com a modlist
- **Malum 1.8.2:** integração principal e dependência funcional presente fisicamente no pack. A release Gaze 1.1.7.1 foi atualizada para a linha Malum 1.8; o runtime físico está em Malum 1.8.2 e precisa ser smoke-tested nessa combinação.
- **Fafnir / Anima Bestiary:** a release 1.1.7.1 registra hotfix nessa interação, mas esses nomes **não foram localizados como mods top-level na modlist física atual**. Portanto são mantidos apenas como evidência histórica/regression note da release, não como integração ativa confirmada do pack.
Nenhuma outra integração é declarada como real apenas por coexistência temática.

## 11. Riscos técnicos
- **Version drift com Malum:** addon acoplado a uma versão relevante do provider; update unilateral pode quebrar registries, mixins ou chamadas internas.
- **Bestiary integration drift:** o hotfix Fafnir/Anima Bestiary demonstra que essa superfície já exigiu correção específica.
- **Client/server mismatch:** versões diferentes podem causar ausência de conteúdo, registries incompatíveis ou comportamento divergente.
- **Registry/data drift:** sem source exato público auditado, IDs e contratos internos não devem ser codificados em integração própria sem inspeção do JAR.
- **Double-processing:** qualquer futura bridge própria com Malum/Bestiary deve evitar processar a mesma descoberta/entrada duas vezes.

## 12. Matriz de testes
1. Dedicated server boot com `gaze-1.1.7.1.jar` + versão física de Malum.
2. Cliente entra em servidor dedicado sem registry mismatch.
3. Criar mundo novo e validar carregamento do conteúdo exposto por Gaze.
4. Abrir mundo existente após restart e verificar persistência do provider original.
5. Relog de jogador sem duplicação de estado/entrada de bestiary.
6. Troca de dimensão sem perda ou duplicação de estado relacionado.
7. Resource reload/datapack reload sem erro de referência.
8. Validar especificamente o fluxo **Fafnir ↔ Anima Bestiary** corrigido em 1.1.7.1.
9. Testar dois jogadores acionando o mesmo tipo de descoberta/evento e confirmar autoridade server-side.
10. Validar atualização isolada de Malum em ambiente de teste antes de qualquer upgrade do pack.
11. Verificar logs por missing registry, mixin failure, codec/data error ou linkage error.
12. Confirmar exatamente uma execução por evento em qualquer integração futura criada pelo pack.

## 13. Evidências e limites
- **Modlist física:** `gaze-1.1.7.1.jar` confirma presença, versão e posição do mod no lote.
- **Distribuição oficial pública:** confirma release 1.1.7.1 para a linha suportada e atualização para Malum 1.8.
- **Changelog/release pública:** registra hotfix envolvendo Fafnir e Anima Bestiary.
- **Limite:** não foi obtido nesta auditoria um source público exato da 1.1.7.1 que permitisse enumerar com segurança classes, registries e conteúdo interno completo. Por isso, esses detalhes não foram inventados.
- **Runtime:** nenhum dos testes acima foi executado nesta catalogação; a matriz descreve validação necessária, não resultados aprovados.
