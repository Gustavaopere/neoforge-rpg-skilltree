# Auditoria mecânica de provider — NPC-0004 — Liora

## Estado
AUDITORIA MECÂNICA / NÃO CRIA LORE, EVENTO OU REPERTÓRIO PESSOAL.

## Objetivo
Separar quatro coisas que não podem ser confundidas:

1. o que o **Ars Nouveau realmente oferece** no snapshot físico documentado do modpack;
2. o que o **RPG Skill Tree realmente integra** desse provider no runtime atual;
3. o que o registro narrativo de `NPC-0004` diz que Liora pratica/conhece;
4. o que ainda precisa de decisão editorial/estado de progressão e, para NPCs, de binding mecânico próprio antes de aparecer como ação concreta da personagem.

A existência de uma capability no provider **não concede essa capability automaticamente a Liora**. Da mesma forma, uma integração player-facing do RPG Skill Tree com Ars Nouveau **não constitui implementação mecânica de Liora como NPC**.

## Authority mecânica consultada
Authority de provider versionada: `PROJECT-INSTRUCTIONS/modlist/ars-nouveau.md`.

Snapshot documentado nessa authority:
- JAR: `ars_nouveau-1.21.1-5.13.1.jar`;
- versão: `5.13.1`;
- mod id: `ars_nouveau`;
- loader/jogo: NeoForge 1.21.1;
- exact release checkpoint upstream registrado: `112920ff774831f204031da75b4c4e73d3765157`;
- estado físico documentado: **Instalado — Dossiê completo**.

A ficha técnica registra a `modlist.txt` física como authority de instalação e mantém Source, spell grammar, rituals e demais registries sob ownership do Ars Nouveau.

Authority de integração do RPG Skill Tree consultada no mesmo repositório:
- `gradle.properties` — fixa `ars_nouveau_version=5.13.1` e `ars_nouveau_version_id=qEFs5RRw`;
- `gradle/ars-provider-gametest-runtime.init.gradle` — instala o provider exato apenas no lane opt-in de loaded-provider GameTest e adiciona GeckoLib, Curios e Patchouli necessários a esse runtime;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/ArsNouveauProgressionEvents.java` — adapter de eventos provider-native;
- `src/main/java/dev/gustavopere/rpgskilltree/core/ArsNativeProgressionPolicy.java` — política de mana/regen/familiar vinculada à árvore;
- `src/main/java/dev/gustavopere/rpgskilltree/core/ArsCompositionClassifier.java` e `MasteryPolicies.java` — classificação de composição e awards de Mastery;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/gametest/ArsProviderCausalityGameTests.java` — acceptance coverage carregando o provider 5.13.1 real;
- `.github/workflows/sonarqube.yml` — executa o loaded-provider lane e inclui sua cobertura JaCoCo/Sonar.

## Integração runtime confirmada no RPG Skill Tree

### Boundary de ator: player-facing, não NPC-binding
O adapter atual não transforma qualquer entidade Ars em ator da progressão própria do projeto.

- pre-cast, cast/resolution e familiar trabalham com `ServerPlayer`;
- mana/regen leem `ProgressionState` apenas para `Player` (server ou client), ignorando `FakePlayer`;
- `MagicAccessRuntime` consulta a progressão do jogador e exige o gate de acesso arcano, com exceção de creative;
- não existe, nesses arquivos, binding de `NPC-0004`, UUID do Grimoire, entidade custom de NPC ou estado narrativo de Liora ao adapter.

Consequência: esta integração é evidência de que **o sistema do jogador** conversa de forma concreta com Ars Nouveau. Ela não prova que Liora use `PlayerProgressionRuntime`, possua os mesmos nodes, receba Mastery ou seja bloqueada/liberada pelas mesmas regras.

### Casting e acesso arcano
`ArsNouveauProgressionEvents.onSpellPreCast` intercepta `SpellCastEvent` no servidor e cancela o cast de jogador sem acesso arcano. O gate compartilhado usa `MagicAccessRuntime`, que depende da política de acesso da árvore RPG.

Isso é um contrato real de integração e impede tratar o provider como totalmente independente da progressão do jogador.

**Não autoriza sobre Liora:** afirmar que ela possui `Despertar Arcano`, que passou pelo mesmo gate ou que seu spellcasting é implementado por esse caminho.

### Mana e regeneração
O adapter escuta `MaxManaCalcEvent` e `ManaRegenCalcEvent` e aplica `ArsNativeProgressionPolicy` ao valor nativo do provider.

A política atual registra efeitos concretos da árvore do jogador:
- `rpgskilltree:arcane_000` acrescenta mana máxima por rank;
- `rpgskilltree:arcane_037` acrescenta mana máxima por rank;
- identidade emergente `sorcerer` multiplica mana máxima por `1.10`;
- `rpgskilltree:arcane_002` aumenta regeneração em `3%` por rank;
- identidade `sorcerer` acrescenta `5%` ao multiplicador de regeneração.

Esses números pertencem ao runtime da progressão do jogador. Não devem ser convertidos em estatísticas pessoais de Liora sem um sistema NPC equivalente explicitamente implementado e materializado.

### Familiar
`FamiliarSummonEvent` é cancelado para `ServerPlayer` não-creative enquanto `rpgskilltree:summoning_000` não estiver aprendido.

Isso confirma um gate real entre árvore de Invocação e familiar do Ars Nouveau para jogadores. Não confirma que Liora tenha familiar, que consiga vinculá-lo ou que esteja sujeita ao mesmo node.

### Mastery causal por composição
No cast válido, o adapter serializa a recipe real do `Spell`, preserva os glyph IDs, usa o custo provider-native e classifica a composição em lanes semânticos. O classificador atual reconhece:
- `projectile`;
- `amplification`;
- `aoe`;
- `duration`;
- `summoning`;
- `control`.

O award só ocorre após `SpellResolveEvent.Post` e usa estado causal one-shot ligado ao `SpellContext`. `MasteryPolicies.forArs` concede progressão geral de casting Ars/magia e, quando aplicável, das lanes semânticas reconhecidas.

Os GameTests do loaded-provider lane verificam contra Ars Nouveau **5.13.1** que:
- uma composição real produz `SpellAction` com provider `ars`, discipline `composition`, glyph identity e custo nativo;
- contexto filho pode resolver a causalidade armada no contexto pai;
- o claim é one-shot e não pode ser consumido por UUID de jogador diferente;
- cast cancelado não arma causalidade;
- repetir `SpellResolveEvent.Post` não duplica o award;
- contexto reidratado sem causalidade comprovável falha fechado em vez de fabricar crédito.

Essa cobertura prova a boundary de Mastery do jogador. Não cria histórico de treino, lane, nível ou proficiência para Liora.

## Capabilities confirmadas no provider

### Spell grammar
A build documentada registra 85 spell parts de produção:
- 5 methods/forms;
- 13 augments;
- 67 effects.

Isso confirma que composição modular de glyphs/spells é uma mecânica real do provider e sustenta a caracterização metodológica já registrada para Liora.

**Não confirma:** quais parts Liora aprendeu, qual spellbook possui, quais combinações domina, qual tier de progressão alcançou ou qual composição usaria numa cena específica.

### Source
Source é recurso próprio da infraestrutura Ars:
- não é player mana;
- não é FE/AE energy;
- não é automaticamente equivalente a recurso de outro provider;
- possui armazenamento e roteamento provider-native, incluindo Source Jars e relays.

Isso confirma mecanicamente o invariant narrativo de que Liora não deve tratar Source como “energia mágica universal”.

### Sourcelinks e automação
A authority técnica confirma as cinco famílias core de Sourcelink e superfícies de automação como turrets, familiars/criaturas utilitárias, storage/crafting e Wixie Cauldron.

**Não confirma:** que Liora tenha construído, possua, opere ou ensine qualquer uma dessas superfícies.

### Rituals
A build registra 24 rituals core.

A existência do registry permite que um ritual venha a ser usado em conteúdo de campanha quando o ritual exato, seus requisitos e a progressão de Liora forem escolhidos e verificados.

**Não autoriza:** escolher ritual por conveniência narrativa nem atribuir domínio integral do registry à personagem.

### Scrying
O provider possui infraestrutura real de scrying e três tipos de scryer registrados no dossiê técnico.

Para narrativa/knowledge, isso significa apenas que existe uma superfície mecânica verificável que **pode** fundamentar uma futura aquisição de informação se uma cena/quest realmente usar o mecanismo correto.

Não transformar a existência de scrying em detecção global, conhecimento remoto automático ou onisciência de Liora.

### Apparatus, Imbuement e aprendizagem
O provider confirma superfícies distintas de progressão/crafting, incluindo:
- Enchanting Apparatus + Arcane Pedestal;
- Imbuement Chamber;
- Scribes Table/glyph learning;
- Storage/Crafting Lectern.

Essas superfícies podem servir como requisitos físicos reais para conteúdo futuro, mas cada uso precisa respeitar recipe/progression reais e o estado concreto da personagem.

### Portals / Warp
Ars Nouveau possui primitives de portal/warp e ritual correspondente; addons podem estender essa superfície.

Nenhuma rota, local de encontro, deslocamento ou acesso dimensional de Liora é criado por esta auditoria.

## Matriz de uso narrativo seguro
| Superfície | Estado mecânico verificado | Pode aparecer como fato sobre Liora agora? | Gate adicional |
| --- | --- | --- | --- |
| prática de glyph spellcraft | provider 5.13.1 confirmado | **SIM, em nível geral já registrado** | não listar repertório pessoal sem estado explícito |
| Source como recurso Ars | provider 5.13.1 confirmado | **SIM, em nível conceitual já registrado** | não equiparar a outros recursos |
| spell part específico | confirmado no provider quando listado na authority | **NÃO automaticamente** | decidir/registrar aprendizado ou uso de Liora |
| ritual específico | registry confirmado | **NÃO automaticamente** | verificar ritual + requisitos + progressão + cena |
| Source Jar/relay/Sourcelink | confirmado | **NÃO automaticamente** | registrar posse/acesso/uso concreto |
| familiar/turret/automation | confirmado no provider | **NÃO automaticamente** | registrar ownership/autoria e lifecycle |
| gate `Despertar Arcano` para cast | implementado no adapter para `ServerPlayer` | **NÃO** | criar/validar binding NPC próprio antes de aplicar a Liora |
| mana/regen da árvore RPG | implementados para `Player` | **NÃO** | não reutilizar estado player como estatística de NPC por inferência |
| familiar via `summoning_000` | implementado para `ServerPlayer` | **NÃO** | binding NPC + estado concreto de familiar/progressão |
| Mastery Ars por composição | implementada e GameTestada para jogador | **NÃO** | NPC precisa de modelo de progressão/runtime próprio se essa mecânica for desejada |
| scrying | confirmado | **NÃO automaticamente** | cena/quest precisa executar canal de descoberta rastreável |
| portal/warp | confirmado | **NÃO automaticamente** | local, target e progressão precisam ser definidos |
| apparatus/imbuement/scribes | confirmado | **NÃO automaticamente** | recipe/progressão/acesso precisam ser comprovados |

## Consequências para autoria
- A linguagem de Liora pode usar `glyph`, `composição`, `Source`, `efeito`, `método`, `teste` e `sequência` sem inventar sistema novo; esses conceitos têm base no provider e já constam do registro narrativo.
- Falas não devem citar um spell part, ritual, familiar ou dispositivo como parte do repertório pessoal dela até isso ser explicitamente materializado.
- O fato de o Skill Tree possuir nodes, identidade `sorcerer` e Mastery Ars player-facing não permite atribuir esses estados a Liora.
- Se Liora demonstrar uma capability em conteúdo futuro, o evento deve estabelecer o que foi observado; não generalizar para todo o registry.
- Se um mecanismo provider-native produzir informação, o knowledge resultante precisa registrar o canal/proveniência; “ela é maga” nunca é canal suficiente.
- Addons Ars podem ampliar registries e bridges, mas não devem ser atribuídos a Liora por associação temática. Cada addon exige audit própria quando entrar em cena.

## Relação com o dossiê narrativo
Esta auditoria **não altera**:
- `met_party=false`;
- discovery channels ainda não fixados;
- local de encontro;
- nível/tier de progressão;
- agenda concreta;
- relações/facções;
- conhecimento histórico;
- repertório de spells/glyphs/rituais;
- posse de item/bloco/familiar;
- qualquer evento ocorrido;
- tipo de entidade/runtime usado para representar Liora in-game.

Ela substitui duas perguntas vagas por boundaries verificáveis:
1. **“o pack suporta Ars Nouveau?”** — sim, o provider core 5.13.1 e suas superfícies principais estão documentados;
2. **“o Skill Tree integra Ars de verdade?”** — sim, existe adapter player-facing com loaded-provider GameTests contra 5.13.1.

Nenhuma dessas respostas decide o que Liora efetivamente aprendeu/possui/usou nem implementa automaticamente um NPC Ars-capable.

## Gate para futura promoção de uma capability de Liora
Antes de escrever uma capability concreta como fato:

1. identificar o elemento/provider exato;
2. confirmar que existe no snapshot físico relevante;
3. confirmar requisitos/recipe/progressão aplicáveis;
4. determinar se a ação será apenas fato narrativo ou também capability executável in-game;
5. se executável, identificar o tipo de ator/runtime de Liora e confirmar um binding compatível, sem reutilizar `PlayerProgressionRuntime` por suposição;
6. registrar por que Liora teria acesso/aprendizado naquele estado;
7. registrar a cena/evento/quest quando o uso produzir consequência persistente;
8. manter knowledge adquirido separado de inferências não observadas.

## Referências
- `historia/03-npcs/principais/NPC-0004-liora.md`;
- `historia/03-npcs/principais/NPC-0004-liora-autoria.md`;
- `PROJECT-INSTRUCTIONS/modlist/ars-nouveau.md`;
- `gradle.properties`;
- `gradle/ars-provider-gametest-runtime.init.gradle`;
- `.github/workflows/sonarqube.yml`;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/MagicAccessRuntime.java`;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/ArsNouveauProgressionEvents.java`;
- `src/main/java/dev/gustavopere/rpgskilltree/core/ArsNativeProgressionPolicy.java`;
- `src/main/java/dev/gustavopere/rpgskilltree/core/ArsCompositionClassifier.java`;
- `src/main/java/dev/gustavopere/rpgskilltree/core/MasteryPolicies.java`;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/ars/gametest/ArsProviderCausalityGameTests.java`;
- Grimoire entity UUID de Liora: `3cb5997c-a542-483c-9dba-9f34b51995b7`.