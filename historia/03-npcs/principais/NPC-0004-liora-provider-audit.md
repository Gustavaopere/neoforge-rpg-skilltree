# Auditoria mecânica de provider — NPC-0004 — Liora

## Estado
AUDITORIA MECÂNICA / NÃO CRIA LORE, EVENTO OU REPERTÓRIO PESSOAL.

## Objetivo
Separar três coisas que não podem ser confundidas:

1. o que o **Ars Nouveau realmente oferece** no snapshot físico documentado do modpack;
2. o que o registro narrativo de `NPC-0004` diz que Liora pratica/conhece;
3. o que ainda precisa de decisão editorial/estado de progressão antes de aparecer como ação concreta da personagem.

A existência de uma capability no provider **não concede essa capability automaticamente a Liora**.

## Authority mecânica consultada
Fonte versionada: `PROJECT-INSTRUCTIONS/modlist/ars-nouveau.md`.

Snapshot documentado nessa authority:
- JAR: `ars_nouveau-1.21.1-5.13.1.jar`;
- versão: `5.13.1`;
- mod id: `ars_nouveau`;
- loader/jogo: NeoForge 1.21.1;
- exact release checkpoint upstream registrado: `112920ff774831f204031da75b4c4e73d3765157`;
- estado físico documentado: **Instalado — Dossiê completo**.

A ficha técnica registra a `modlist.txt` física como authority de instalação e mantém Source, spell grammar, rituals e demais registries sob ownership do Ars Nouveau.

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
| Superfície | Provider 5.13.1 | Pode aparecer como fato sobre Liora agora? | Gate adicional |
| --- | --- | --- | --- |
| prática de glyph spellcraft | confirmada | **SIM, em nível geral já registrado** | não listar repertório pessoal sem estado explícito |
| Source como recurso Ars | confirmada | **SIM, em nível conceitual já registrado** | não equiparar a outros recursos |
| spell part específico | confirmado no provider quando listado na authority | **NÃO automaticamente** | decidir/registrar aprendizado ou uso de Liora |
| ritual específico | registry confirmado | **NÃO automaticamente** | verificar ritual + requisitos + progressão + cena |
| Source Jar/relay/Sourcelink | confirmado | **NÃO automaticamente** | registrar posse/acesso/uso concreto |
| familiar/turret/automation | confirmado | **NÃO automaticamente** | registrar ownership/autoria e lifecycle |
| scrying | confirmado | **NÃO automaticamente** | cena/quest precisa executar canal de descoberta rastreável |
| portal/warp | confirmado | **NÃO automaticamente** | local, target e progressão precisam ser definidos |
| apparatus/imbuement/scribes | confirmado | **NÃO automaticamente** | recipe/progressão/acesso precisam ser comprovados |

## Consequências para autoria
- A linguagem de Liora pode usar `glyph`, `composição`, `Source`, `efeito`, `método`, `teste` e `sequência` sem inventar sistema novo; esses conceitos têm base no provider e já constam do registro narrativo.
- Falas não devem citar um spell part, ritual, familiar ou dispositivo como parte do repertório pessoal dela até isso ser explicitamente materializado.
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
- qualquer evento ocorrido.

Ela apenas substitui a pergunta vaga “o pack suporta Ars Nouveau?” por uma boundary verificável: o provider core 5.13.1 e suas superfícies principais estão documentados; o que Liora efetivamente aprendeu/possui/usou continua sendo estado narrativo separado.

## Gate para futura promoção de uma capability de Liora
Antes de escrever uma capability concreta como fato:

1. identificar o elemento/provider exato;
2. confirmar que existe no snapshot físico relevante;
3. confirmar requisitos/recipe/progressão aplicáveis;
4. registrar por que Liora teria acesso/aprendizado naquele estado;
5. registrar a cena/evento/quest quando o uso produzir consequência persistente;
6. manter knowledge adquirido separado de inferências não observadas.

## Referências
- `historia/03-npcs/principais/NPC-0004-liora.md`;
- `historia/03-npcs/principais/NPC-0004-liora-autoria.md`;
- `PROJECT-INSTRUCTIONS/modlist/ars-nouveau.md`;
- Grimoire entity UUID de Liora: `3cb5997c-a542-483c-9dba-9f34b51995b7`.
