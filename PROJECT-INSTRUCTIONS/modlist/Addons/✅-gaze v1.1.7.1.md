# Gaze

## Propriedades do registro

- **Mod:** Gaze
- **Arquivo JAR:** `gaze-1.1.7.1.jar`
- **Versão 1.21.1:** `1.1.7.1`
- **Categoria:** Magia, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/gaze-a-malum-addon
- **Função:** Addon de conteúdo/compatibilidade para Malum; estende o ecossistema Malum sem assumir autoridade sobre seus sistemas-base.
- **Dependências:** Malum (release 1.1.7.1 alinhada a Malum 1.8)
- **Compatibilidade/Riscos:** Acoplamento direto a Malum; pack físico usa Malum 1.8.2, enquanto Gaze 1.1.7.1 permanece na linha Malum 1.8. Riscos: version drift, client/server mismatch, registry/data drift, regressões da Rune Fafnir e do Anima Bestiary e double-processing em bridges futuras.
- **Sobreposição:** Malum permanece authority dos sistemas-base; Gaze é authority apenas do conteúdo/integrações que adiciona. Rune Fafnir e Anima Bestiary são superfícies internas do addon e não entradas top-level da modlist.
- **Observações:** Release 1.1.7.1 permanece atual para 1.21.1. Changelog oficial: nova tradução, reorganização de entradas do Book, Rune Fafnir com bônus ao usar set completo de Malignant armour e correções do Anima Bestiary vazio/jittering. Source exato 1.1.7.1 não foi pinado para internals.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `gaze-1.1.7.1.jar`, mod id `gaze`, runtime `1.1.7.1` e SHA-1 `a8cb3190bde157f78160ce65c202ce2d47fb2041`.
- **Atualização/Status:** REAUDITADO EM 22/09/2026 — lote físico #300: Gaze 1.1.7.1 reconfirmado; nenhuma mudança de versão física nesta rodada.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #300: JAR `gaze-1.1.7.1.jar`, mod id `gaze`, runtime `1.1.7.1`, SHA-1 `a8cb3190bde157f78160ce65c202ce2d47fb2041`.

<callout icon="🔬">
	**PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta apenas o que pôde ser confirmado para o JAR físico `gaze-1.1.7.1.jar` e fontes públicas correspondentes. Onde o source exato da 1.1.7.1 não estava disponível para inspeção, não são inventados classes, registries, IDs ou números internos.
</callout>

## 1. Identidade

- **Nome:** Gaze
- **Arquivo físico:** `gaze-1.1.7.1.jar`
- **Versão instalada:** `1.1.7.1`
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Natureza:** addon de conteúdo/compatibilidade para **Malum**.
- **Pin de release:** a distribuição pública da versão 1.1.7.1 é a referência funcional usada nesta ficha.

## 2. Papel no modpack

Gaze estende o ecossistema de **Malum**. A release 1.1.7.1 permanece na linha **Malum 1.8** e traz mudanças internas do próprio addon: reorganização de entradas do Book, bônus da **Rune Fafnir** ao usar um set completo de Malignant armour e correções do **Anima Bestiary**. Gaze não deve ser tratado como autoridade independente sobre os sistemas-base de Malum.

## 3. Autoridade / ownership

- **Malum** continua sendo autoridade sobre seus sistemas-base, registries centrais e contratos de gameplay.
- **Gaze** é autoridade somente sobre o conteúdo e as integrações que ele próprio injeta/adiciona ao ecossistema Malum.
- Integrações com outros mods devem preservar essa separação; não há base para assumir que Gaze substitui ou redefine internals de Malum.

## 4. Conteúdo confirmado

A evidência pública da 1.1.7.1 confirma compatibilidade com **Malum 1.8** e mudanças internas específicas: nova tradução, entradas do Book movidas, **Rune Fafnir** com bônus ao equipar o set completo de Malignant armour e correções para **Anima Bestiary** vazio e entries jittering.
A fonte pública disponível para esta auditoria não expõe um inventário confiável e versionado de todos os itens, blocos, entidades, recipes, efeitos ou registries internos da 1.1.7.1. Esses elementos não são enumerados por inferência.

## 5. Sistemas internos

O comportamento operacional confirmado é o de addon integrado a Malum. A 1.1.7.1 demonstra superfícies próprias de equipamento/progressão e bestiary: a **Rune Fafnir** ganhou uma condição de bônus ligada ao set Malignant armour, enquanto o **Anima Bestiary** recebeu correções de conteúdo vazio e jitter visual.
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
- **Rune Fafnir / Anima Bestiary:** são superfícies internas do próprio Gaze. A 1.1.7.1 adiciona bônus da Rune Fafnir com set completo de Malignant armour e corrige Anima Bestiary vazio/jittering; não devem ser catalogados como mods top-level separados.

Nenhuma outra integração é declarada como real apenas por coexistência temática.

## 11. Riscos técnicos

- **Version drift com Malum:** addon acoplado a uma versão relevante do provider; update unilateral pode quebrar registries, mixins ou chamadas internas.
- **Rune/Bestiary regression:** a 1.1.7.1 mostra que a condição de equipamento da Rune Fafnir e o conteúdo/render do Anima Bestiary são superfícies sensíveis a regressão.
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
8. Validar a **Rune Fafnir** com e sem o set completo de Malignant armour e confirmar que o **Anima Bestiary** carrega conteúdo sem jitter.
9. Testar dois jogadores acionando o mesmo tipo de descoberta/evento e confirmar autoridade server-side.
10. Validar atualização isolada de Malum em ambiente de teste antes de qualquer upgrade do pack.
11. Verificar logs por missing registry, mixin failure, codec/data error ou linkage error.
12. Confirmar exatamente uma execução por evento em qualquer integração futura criada pelo pack.

## 13. Evidências e limites

- **Modlist física:** `gaze-1.1.7.1.jar` confirma presença, versão e posição do mod no lote.
- **Distribuição oficial pública:** confirma release 1.1.7.1 para a linha suportada e atualização para Malum 1.8.
- **Changelog/release pública:** registra reorganização do Book, bônus da Rune Fafnir com full Malignant armour e fixes do Anima Bestiary vazio/jittering.
- **Limite:** não foi obtido nesta auditoria um source público exato da 1.1.7.1 que permitisse enumerar com segurança classes, registries e conteúdo interno completo. Por isso, esses detalhes não foram inventados.
- **Runtime:** nenhum dos testes acima foi executado nesta catalogação; a matriz descreve validação necessária, não resultados aprovados.
