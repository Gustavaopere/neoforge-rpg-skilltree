# Asset Brief legado — Severin — antigo uso editorial de `NPC-0001`

## Estado editorial
OBSOLETO / LEGADO HISTÓRICO / NÃO TRANSFERÍVEL PARA AREN.

## Estado de produção
NÃO É ASSET FINAL / NÃO INICIADO / APOSENTADO PARA A IDENTIDADE ATIVA.

## Aviso de migração
Desde 2026-09-15, `NPC-0001` representa Aren em `NPC-0001-aren.md`. Este brief preserva somente a direção visual que havia sido proposta para Severin antes da reconciliação. Nenhuma característica visual abaixo deve ser usada para Aren sem source própria e nova aprovação.

Severin não foi transformado em alias, pseudônimo ou nome anterior de Aren. A decisão completa está em `historia/11-ia-e-autoria/15-migracao-npc-0001-severin-aren-2026-09-15.md`.

## Asset ID histórico
`NPC-0001` no estado editorial anterior à migração. Não usar este identificador para publicar asset novo de Aren a partir deste brief.

## Owner/repositório
`Gustavaopere/neoforge-rpg-skilltree`

## Função histórica de gameplay/apresentação
O brief descrevia Severin como NPC recorrente de pesquisa proibida/controversa. Essa função era parte do cenário editorial de Severin e não constitui descrição visual ou narrativa de Aren.

## Contextos de visualização históricos
- conversa a curta/média distância;
- investigação e exploração;
- ambientes de pesquisa/oficina;
- portrait/documentação;
- skin/modelo em escala normal de gameplay.

## Referências históricas
- `NPC-0001-severin.md` — registro legado;
- `NPC-0001-severin-autoria.md` — autoria legada;
- `QST-0001` — vínculo histórico do rascunho, não vínculo confirmado de Aren;
- `NPC-0001-aren.md` — entidade ativa atual, que **não** herda este brief.

## Silhueta histórica em uma frase
Figura humana relativamente estreita, em roupas de trabalho em camadas, que pareceria pesquisador cauteloso antes de combatente ou “necromante de fantasia”.

## Shape language histórico
Camadas funcionais, formas simples e legíveis, poucos elementos salientes, ausência de ornamento gratuito. A leitura viria da função e do material, não de símbolos agressivos.

## Paleta/materials históricos
- grafite;
- preto quebrado;
- cinza frio;
- couro/tecido de trabalho;
- um acento dessaturado a definir após concept;
- sem dependência de emissive.

## Aparência histórica
- adulto maduro, sem caricatura de velhice;
- altura/proporção percebida: **PENDENTE DE APROVAÇÃO VISUAL**;
- tom de pele: **PENDENTE DE APROVAÇÃO VISUAL**;
- rosto humano e reservado;
- olheiras discretas/sinais de rotina irregular permitidos;
- cabelo escuro ou parcialmente grisalho;
- barba curta opcional;
- evitar aparência cadavérica automática.

Nenhuma dessas características está aprovada para Aren.

## Roupa/acessórios históricos
- roupa de trabalho/pesquisa adaptada ao mundo;
- sobretudo curto ou camada equivalente;
- luvas/proteção quando fizesse sentido;
- caderno, bolsa, frascos ou componentes poderiam aparecer no concept;
- a skin/texture final deveria sugerir função sem tentar representar todos os objetos.

## Modelo alvo histórico
Modelo corporal Minecraft `classic` como hipótese inicial do rascunho de Severin. Essa escolha não vincula Aren a `classic` ou `slim`.

## Resolução e formatos — regras técnicas ainda úteis
A separação entre portrait/concept e skin técnica continua sendo uma regra de produção geral, mas este brief não autoriza produzir Aren.

### Portrait/documentação — referência técnica histórica
- master proposto: **2048×2048 px**, PNG, sRGB;
- manter o master sem recompressão lossy;
- derivados menores podem ser gerados para UI/documentação, mas não substituem o master;
- concept sheets não precisam ser quadradas, porém devem preservar pelo menos **2048 px no maior lado** para leitura de material/rosto;
- portrait/concept nunca é convertido diretamente em UV de skin.

### Skin técnica — evidência do provider
- alvo técnico humanoide atual: **64×64 px RGBA PNG**, layout moderno de skin humanoide Minecraft;
- preservar alpha/segunda camada quando usada;
- não ampliar a skin para “HD” por conta própria: Easy NPC consome skin de player/URL no renderer humanoide; portrait e skin são artefatos diferentes;
- **não presumir outer layer 3D para NPCs**: a presença de 3D Skin Layers comprova comportamento no renderer de player, não integração automática com humanoides do Easy NPC. Qualquer efeito 3D específico no NPC exige prova de provider/render path ou modelo próprio.

Evidência técnica/proveniência:
- `PROJECT-INSTRUCTIONS/modlist/easy-npc.md` — Easy NPC Core 7.11.0 é o provider físico dos NPCs e suporta skins de player/URL;
- upstream Easy NPC já trata skins modernas 64×64 e preservação de alpha antes da versão física 7.11.0;
- `PROJECT-INSTRUCTIONS/modlist/3d-skin-layers.md` — o mod atua no renderer de player e não deve ser promovido a capability do Easy NPC sem integração comprovada.

A especificação `64×64` é do formato humanoide atual, não uma regra global de texel density para qualquer asset project-owned.

## Emissive/translucency/animated texture — histórico
Nenhum requisito havia sido aprovado. Não usar brilho ocular, aura ou glow como atalho de identidade de Aren.

## Bones/attachments necessários — histórico
Nenhum requisito adicional havia sido estabelecido enquanto o alvo fosse uma skin/modelo humano padrão. Acessórios físicos próprios exigiriam contrato separado.

## Animações necessárias — histórico
Nenhuma animação project-owned havia sido exigida. Qualquer animação futura deve respeitar o runtime real e a pipeline vigente.

## Elementos proibidos no rascunho histórico
- caveira ornamental gratuita;
- olhos permanentemente brilhantes;
- sangue decorativo;
- coroa;
- armadura pesada sem contexto;
- iconografia que declare alinhamento moral;
- símbolo de facção/provider sem vínculo canônico/mecânico;
- preto puro uniforme que destrua leitura material.

Essa lista registra a antiga direção de Severin; não substitui um futuro brief de Aren.

## Variações históricas cogitadas
- campo;
- laboratório/oficina;
- versão danificada somente após existir evento específico que a justificasse.

## Proveniência/licença
Material project-owned de look-dev legado. Referências externas, se usadas em look-dev, servem apenas para linguagem visual e devem ser registradas; não copiar textura/modelo de terceiros.

## Evidência final histórica que seria exigida
- concept/portrait aprovado contra o brief;
- textura/skin técnica 64×64 separada do concept;
- validação estrutural no pipeline Blockbench/Factory;
- vistas úteis de frente, lateral, costas e três-quartos quando aplicável;
- verificação em escala real de gameplay;
- QA visual in-game antes de marcar `FINAL`.

Nenhuma dessas etapas foi concluída para Severin, e nenhuma pode ser reaproveitada como aprovação de Aren.

## Pendência atual correta
Criar um **novo** asset brief de Aren somente quando sua aparência/direção visual tiver source reconciliada e aprovação editorial. Este arquivo permanece apenas como provenance histórica.
