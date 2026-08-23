# 5. Auditoria técnica por área

## 5.1 Persistência, Data Attachments e Data Components

### Decisão correta

Data Attachment é a escolha adequada para progressão persistente vinculada ao jogador. NeoForge 1.21.1 documenta attachments para entities, chunks e block entities, incluindo serializer e `copyOnDeath`. [Data Attachments — NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/).

Não migrar isso para Data Components. Data Components são a solução para estado persistente associado a `ItemStack`, como um futuro item attuned. [Data Components — NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/items/datacomponents/).

### Problemas

- O codec binário tem proteção estrutural razoável, mas não tem estratégia de recuperação.
- `PassivePointSource` e `ProgressionDomain` são enums persistidos. Um valor desconhecido pode invalidar todo o save.
- Não há quarentena, backup lógico, diagnóstico administrável ou fallback.
- Versão binária e evolução semântica estão misturadas.
- O PR #5 introduz reclassificação de IDs na leitura ainda sob a mesma versão 4.
- Isso impede distinguir:
  - formato do byte stream;
  - versão do modelo;
  - versão de migração já aplicada.
- Não há fixture de save real por versão.
- Não há teste de morte/respawn e relog em runtime NeoForge.
- Não há política explícita para uninstall de addon.

### Recomendação

Persistir fatos, não interpretações deriváveis:

- XP;
- ledgers;
- ranks comprados;
- choices explicitamente irreversíveis;
- mastery;
- discoveries;
- boss credit;
- versão semântica de migração.

Classes ou especializações que sejam resultado da distribuição atual de pontos devem ser derivadas, não persistidas como autoridade paralela.

Separar:

- `diskFormatVersion`;
- `semanticSchemaVersion`;
- `networkProtocolVersion`.

IDs desconhecidos devem ser preservados ou colocados em uma coleção de órfãos; não devem invalidar o save inteiro.

## 5.2 Capability-equivalents em NeoForge 1.21.1

Capabilities continuam existindo no NeoForge 1.21.1. Não foram “substituídas” por attachments em todos os casos.

A divisão correta é:

| NecessidadeAPI                               |                                      |
| -------------------------------------------- | ------------------------------------ |
| Persistência pertencente ao jogador          | Data Attachment                      |
| Estado pertencente a ItemStack               | Data Component                       |
| Consulta de comportamento/serviço entre mods | Capability                           |
| Projeção somente para o cliente dono         | Payload ou sync definido pelo mod    |
| Dados estáticos/recarregáveis                | Data packs + codecs/reload listeners |

Se outro mod precisar consultar a progressão sem depender das classes internas, uma `EntityCapability` read-only é apropriada. O NeoForge 1.21.1 fornece `EntityCapability` e registro por `RegisterCapabilitiesEvent`. [Fonte oficial de ](https://github.com/NeoForged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/capabilities/EntityCapability.java)[`EntityCapability`](https://github.com/NeoForged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/capabilities/EntityCapability.java)[ no branch 1.21.1](https://github.com/NeoForged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/capabilities/EntityCapability.java).

Não criar capability apenas para duplicar o attachment.

## 5.3 Networking e segurança

### Pontos adequados

- Payloads são registrados por direção.
- Cliente envia IDs, não ranks, pontos ou atributos arbitrários.
- Servidor busca definições e valida requisitos.
- O snapshot S2C respeita o limite cliente-bound de 1 MiB.
- Os pacotes C2S são pequenos e compatíveis com o limite server-bound inferior a 32 KiB.

Esses limites e o modelo de `RegisterPayloadHandlersEvent` estão documentados para 1.21.1. [Networking — NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/networking/payload/).

### Problemas

- Não há rate limiting ou debounce.
- Uma sequência de pacotes inválidos pode provocar reconciliação e full sync repetido.
- Handlers capturam `IllegalArgumentException`, mas não todas as falhas geradas por reconciliação.
- O runtime faz full sync e reaplicação completa de atributos em eventos frequentes de XP/mastery.
- `ClientProgressionState` não é explicitamente limpo no disconnect.
- `enqueueWork` é redundante no registro padrão porque os handlers já rodam na main thread; não é um bug funcional, apenas ruído.
- Não existe protocolo explícito de versão.
- Definições efetivas não são sincronizadas; apenas estado é.

### Segurança recomendada

Centralizar uma política para:

- fake players;
- creative;
- spectator;
- cooldown por tipo de ação;
- máximo de awards por tick;
- deduplicação;
- confirmação de outcome;
- logging rate-limited de rejeições.

O servidor deve continuar sendo a única autoridade.

## 5.4 Reload, codecs e dados

Existem loaders separados para:

- node rules;
- node effects;
- classes;
- choices;
- boss rewards;
- tree architecture;
- morph categories.

Eles usam parsing manual, principalmente Gson, com contexto de erro limitado.

### Riscos

- Publicação parcial de catálogos.
- Cross-references não verificadas integralmente.
- IDs internos sem namespace permitem colisões entre datapacks.
- Falha em um catálogo pode deixar outro novo já publicado.
- Jogadores online não são reconciliados de forma transacional.
- Modifiers antigos podem sobreviver até outro evento.
- Cliente não recebe o novo contrato.

### Arquitetura recomendada

Criar uma etapa:

```text
prepare → decode → normalize IDs → cross-validate → compile bundle → atomic commit
```

O bundle deve conter:

- nós;
- grafo;
- requisitos;
- efeitos;
- classes;
- arquétipos;
- especializações;
- mastery lanes;
- gateways;
- moedas;
- projeção de UI;
- bindings de atributos.

Se qualquer elemento falhar, manter integralmente o bundle anterior.

Mojang `Codec`/`MapCodec` deve ser preferido para definições novas. Não é necessário reescrever todos os JSONs de uma vez; pode-se migrar catálogo por catálogo.

## 5.5 Registries

O mod quase não registra conteúdo vanilla próprio, portanto não há necessidade de criar registries customizados indiscriminadamente.

A exceção relevante é o contrato de definições extensíveis. Existem duas opções válidas:

1. reloadable bundle próprio com `ResourceLocation`;
2. datapack registries quando houver benefício claro de integração com o registry system.

A primeira é suficiente por enquanto. Converter tudo para registry customizado seria refatoração prematura.

Obrigatório:

- todo ID extensível deve ser `ResourceLocation`;
- referências internas devem conservar namespace;
- addons devem poder contribuir sem sobrescrever o core;
- colisões devem falhar com diagnóstico claro.

## 5.6 Atributos e efeitos

### Estado atual

- `CanonicalStat` e `CanonicalStatCatalog` existem.
- `AttributeNodeEffectRuntime` não os usa como autoridade.
- JSONs guardam atributos diretos.
- Modifiers são removidos/reaplicados por ID, o que é uma boa propriedade de idempotência.

### Problemas

- 34 efeitos usam IDs de 1.21.2.
- Registro inexistente é ignorado silenciosamente.
- Bindings de providers diferentes não são centralizados.
- Histórico de modifier IDs removíveis cresce.
- Full rebuild ocorre em eventos que não mudaram ranks.
- Não existe validação contra os registries efetivos do modpack.

### Modelo recomendado

```text
skill effect
  → canonical stat
  → binding ativo no modpack
  → Attribute ou API do provedor
```

Exemplo:

```text
rpgskilltree:spell_power
  → irons_spellbooks:spell_power se Iron's estiver presente
  → binding alternativo se outro provedor for escolhido
```

O conteúdo não deve decidir diretamente qual implementação externa é canônica.

## 5.7 Classes emergentes, híbridas e especializações

Há uma divergência entre design e implementação:

- os documentos falam em classes emergentes;
- o runtime persiste classes desbloqueadas;
- classes automáticas de custo zero podem ser removidas se os requisitos deixarem de existir;
- classes pagas permanecem mesmo após perder requisitos;
- `ArchetypeResolver` não é o mecanismo efetivo;
- a ordenação atual do resolver favorece prioridade antes de especificidade, contrariando o design;
- especializações são tratadas como estado persistido separado;
- algumas classes, como Industrialist/Logistician/Prospector, são reclassificadas como especializações no PR #5.

Isso não é apenas questão estética: existem duas fontes de verdade.

### Modelo recomendado

- Classe primária e secundária: derivadas de investimentos canônicos.
- Classe híbrida: rótulo derivado de combinação, não lock.
- Specialization escolhida: persistida somente se for uma escolha consciente e estável.
- Specialization concedida por nó: derivada do nó e removível/reconciliável.
- “Identidade do provedor”, como Mage/Sorcerer, deve ser gateway badge ou especialização, salvo decisão explícita de que é uma classe top-level.
- Requisitos de acesso e bônus devem consultar o mesmo snapshot derivado.

### DECISÃO PENDENTE

Definir formalmente:

- quais identidades são derivadas;
- quais são choices irreversíveis;
- se classes pagas continuam “sticky”;
- como empates entre primária/secundária são resolvidos;
- se especificidade sempre vence prioridade;
- como híbridos afetam acesso sem hard lock.

## 5.8 Mastery, moedas e progressão por uso

Mastery XP está separado do ledger passivo, o que é correto. Mas a arquitetura ainda não completa o objetivo.

Problemas:

- `CharacterXpAward.attributedDomains` é ignorado por `applyXp`;
- `MasteryAward.sourceId` é ignorado pelo serviço;
- não há diminishing returns ou anti-farm central;
- não há deduplicação geral de ações;
- subárvores ainda gastam pontos passivos gerais;
- gateways de Create/AE2/Oritech exigem mastery impossível de obter;
- várias integrações concedem XP na intenção, não na confirmação.

Recomendação:

- mastery lane: `ResourceLocation`;
- source ID: obrigatório e auditável;
- moeda específica por árvore/subárvore: ledger namespaced separado;
- mastery não é moeda de compra;
- mastery pode desbloquear gateway ou conceder pontos específicos;
- pontos gerais não devem financiar automaticamente conteúdo especializado;
- cada award deve registrar ação, origem e confirmação;
- regras de creative/fake player devem ser únicas para todo o mod.

## 5.9 Client/server e dedicated server

A separação física do cliente é razoável e o dedicated server mínimo inicializa.

Riscos restantes:

- nenhuma matriz com mods opcionais;
- mixin do Identity não é exercitado;
- nenhuma validação de classloading com apenas um provedor instalado;
- resources exclusivos do servidor não aparecem na UI;
- cliente mantém catálogo estático local;
- o estado do cliente pode sobreviver a uma troca de servidor na mesma sessão;
- textos de interação ainda são hardcoded em inglês em partes da UI.

O core dedicated server green deve ser preservado como gate permanente.

## 5.10 Performance

### Hot paths

- full sync em cada XP/mastery;
- reaplicação total de modifiers;
- UI reconstrói estado para aproximadamente 512 nós por frame;
- renderiza centenas de arestas sem culling;
- hit testing linear;
- exploração consulta periodicamente todos os jogadores;
- Eidolon pode serializar NBT repetidamente para observar ritual;
- ore provenance global cresce sem mecanismo robusto de limpeza.

### Riscos específicos

#### Mining

`PlayerPlacedOreData` mantém posições em `SavedData` por dimensão.

Pode ser contornado ou degradado por:

- fake players;
- máquinas;
- Create contraptions;
- pistões;
- reposicionamento;
- remoção sem break normal;
- explosões indiretas;
- chunks nunca limpos.

O conjunto pode crescer indefinidamente e aumentar RAM, save e tempo de serialização.

Preferir provenance por chunk attachment, com remoção por lifecycle e política explícita para blocos movidos.

#### UI

A escala atual ainda pode ser tolerável em hardware forte, mas o algoritmo cresce diretamente com nós e arestas. Antes de árvores maiores:

- cache de layout;
- culling por viewport;
- índice espacial para hit testing;
- rebuild apenas quando estado/catálogo mudar;
- métricas de frame.

## 5.11 Integrações opcionais

### Iron’s Spellbooks

Pontos positivos:

- pre-cast gating;
- evento de cast confirmado;
- filtro de fake player em parte do fluxo.

Riscos:

- política de creative inconsistente entre cast e inscription;
- IDs como `irons:*` precisam ser normalizados para namespace contratual;
- sem teste runtime com a versão configurada.

### Ars Nouveau

Pontos positivos:

- gate pre-cast;
- mastery por cast;
- políticas separadas para composição/mana/familiar.

Riscos:

- ausência de testes de integração;
- sem validação de lifecycle e duplicidade;
- dependência continua diretamente conhecida pelo entrypoint.

### Epic Fight

Riscos:

- item capability pode estar ausente dependendo do stack;
- mastery de skill pode ocorrer na intenção de consumo;
- confirmação da ação deve ser baseada no evento final do provedor.

### Malum

Há reflexão para acessar spirit data.

Riscos:

- falhas e `LinkageError` são engolidos;
- fallback concede magnitude padrão;
- integração quebrada pode continuar premiando;
- pode haver award sem harvest/kill confirmado;
- risco de evento duplicado.

Falha de integração deve desabilitar a feature com diagnóstico, não conceder recompensa aproximada.

### Goety

Existe boa tentativa de correlacionar intenção e perda de souls.

Risco:

- chave é jogador + janela de um tick;
- qualquer outra perda de soul no mesmo tick pode ser confundida;
- callbacks concorrentes sobrescrevem pending state;
- precisa correlacionar spell/action ID e sequência.

### Eidolon Ritual

Riscos:

- polling de NBT completo;
- janela longa;
- comparação de `ResourceKey` por identidade;
- mastery e discovery podem causar dois syncs.

### Eidolon Alchemy

Riscos:

- mapa estático de contributors sem limpeza confiável;
- “último contributor” como autoridade;
- spoof por item resultante próximo ao crucible;
- comparação por identidade de recipe object;
- associação deve ser baseada em lifecycle/ID estável do processo.

### Identity/Morph

O `@Pseudo` mixin mira um método específico e `remap = false`.

Isso é extremamente sensível à versão. O conceito do PR #5 — usar API pública quando disponível — é melhor, mas ainda precisa de:

- dependência/version range explícitos;
- teste com o mod presente;
- fallback seguro;
- nenhum crash quando ausente.

### Create, AE2 e Oritech

Atualmente não constituem integrações. Há dados e políticas, mas não há produtor de mastery. Os gateways resultantes são inalcançáveis em gameplay normal.

Não adicionar mais skills dessas árvores até que exista um vertical slice funcional.

## 5.12 Datagen

O `build.gradle` configura `runData` e `src/generated/resources`, mas não há providers Java registrados por `GatherDataEvent`.

Todo o conteúdo gerado real vem de scripts Python. Isso não é automaticamente errado, mas precisa ser tratado como pipeline oficial, e o CI deve exigir working tree limpo após execução.

O NeoForge 1.21.1 documenta datagen por `GatherDataEvent` e saída em `src/generated/resources`. [Resources/datagen — NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/resources/).

O `pack.mcmeta` próprio do mod também pode ser removido após verificação: o NeoForge gera metadados sintéticos para o pack do mod na linha 1.21.1. Não é prioridade.

## 5.13 Testes e CI

### O que o CI atual verifica

- geradores;
- 18 testes core;
- validadores Python;
- compilação NeoForge;
- presença de classes no JAR;
- dedicated server core por até 150 segundos.

### O que não verifica

- arquivos gerados correspondendo ao commit;
- `Gradle test`;
- auto-descoberta de novos testes;
- GameTests;
- persistência real;
- atributos em jogador;
- rede e login;
- reload;
- client start;
- mods opcionais;
- uninstall de addon;
- performance;
- datagen nativo;
- release.

O `test-core.sh` enumera manualmente as classes. Um novo teste pode existir e nunca ser executado.

GameTests são oficialmente suportados no 1.21.1 via `@GameTest`, `@GameTestHolder`/`RegisterGameTestsEvent` e `runGameTestServer`. [GameTests — NeoForge 1.21.1](https://docs.neoforged.net/docs/1.21.1/misc/gametest/).

### Reprodutibilidade

Não há `gradlew`, wrapper JAR ou `gradle-wrapper.properties`. O CI instala Gradle 8.14 globalmente.

Isso deve ser corrigido cedo. O wrapper é parte do contrato de build.

---

# 6. Arquitetura recomendada para o objetivo do mod

## 6.1 Núcleo

Manter um core Java puro com:

- value objects imutáveis;
- mutations retornando novo estado/result;
- sem imports de Minecraft em regras puras;
- IDs `ResourceLocation`;
- regras determinísticas;
- testes rápidos.

## 6.2 Estado persistido

Persistir somente fatos:

```text
ProgressionState
├── character XP
├── point ledgers por moeda
├── purchased node ranks
├── mastery XP por lane
├── discoveries
├── boss credit
├── choices explícitas
└── migration version
```

Derivar:

```text
investimentos → arquétipos → classe primária/secundária → híbridos
```

## 6.3 Bundle de definições

```text
ProgressionDefinitionBundle
├── node definitions
├── graph/topology
├── access requirements
├── effects
├── canonical stat bindings
├── archetypes/classes
├── specializations
├── mastery lanes
├── tree currencies
├── gateways
└── client projection
```

Recarregado de forma atômica.

## 6.4 Cliente

O cliente deve receber:

- snapshot do estado autorizado;
- projeção do bundle efetivo;
- revisão/hash do catálogo.

`assets` deve conter somente:

- traduções;
- texturas;
- estilos;
- layout visual que não determine acesso, se esse layout não for sincronizado.

## 6.5 Integrações

Criar um contrato pequeno:

```text
IntegrationDescriptor
ProgressionAction
ConfirmedOutcome
MasteryAwardPolicy
CanonicalStatBinding
```

Cada integração:

- fica em pacote ou módulo isolado;
- só é carregada se mod e versão compatíveis estiverem presentes;
- traduz eventos externos para ações canônicas;
- não muta `ProgressionState` diretamente;
- não decide regras de creative/fake player;
- não concede awards antes do outcome;
- tem teste com provedor presente e ausente.

Não é obrigatório migrar imediatamente para um build multi-module. Primeiro imponha as fronteiras por interfaces e classloading; se conflitos de dependência surgirem, mova adapters para subprojetos.

---
