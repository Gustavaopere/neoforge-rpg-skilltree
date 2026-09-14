# Asset Brief — NPC-0001 — Severin

## Estado editorial
RASCUNHO

## Estado de produção
NÃO É ASSET FINAL / NÃO INICIADO.

## Asset ID
`NPC-0001`

## Owner/repositório
`Gustavaopere/neoforge-rpg-skilltree`

## Função de gameplay/apresentação
NPC recorrente de pesquisa proibida/controversa. A leitura visual deve comunicar pesquisador funcional e reservado antes de qualquer associação automática a vilania, monstruosidade ou combate.

## Contextos de visualização
- conversa a curta/média distância;
- investigação e exploração;
- ambientes de pesquisa/oficina;
- portrait/documentação;
- skin/modelo em escala normal de gameplay.

## Referências project-owned/canônicas
- `NPC-0001-severin.md`;
- `NPC-0001-severin-autoria.md`;
- `QST-0001` apenas como contexto narrativo, sem revelar resultado/culpa.

## Silhueta em uma frase
Figura humana relativamente estreita, em roupas de trabalho em camadas, que parece pesquisador cauteloso antes de parecer combatente ou "necromante de fantasia".

## Shape language
Camadas funcionais, formas simples e legíveis, poucos elementos salientes, ausência de ornamento gratuito. A leitura deve vir da função e do material, não de símbolos agressivos.

## Paleta/materials
- grafite;
- preto quebrado;
- cinza frio;
- couro/tecido de trabalho;
- um acento dessaturado a definir após concept;
- sem dependência de emissive.

## Aparência
- adulto maduro, sem caricatura de velhice;
- altura/proporção percebida: **PENDENTE DE APROVAÇÃO VISUAL**; manter hipótese humana padrão até o concept justificar algo diferente;
- tom de pele: **PENDENTE DE APROVAÇÃO VISUAL**; não inferir a partir de profissão, necromancia, moralidade ou paleta de roupa;
- rosto humano e reservado;
- olheiras discretas/sinais de rotina irregular permitidos;
- cabelo escuro ou parcialmente grisalho;
- barba curta opcional;
- evitar aparência cadavérica automática.

## Roupa/acessórios
- roupa de trabalho/pesquisa adaptada ao mundo;
- sobretudo curto ou camada equivalente;
- luvas/proteção quando fizer sentido;
- caderno, bolsa, frascos ou componentes podem aparecer no concept;
- a skin/texture final deve sugerir função sem tentar representar todos os objetos.

## Modelo alvo
Modelo corporal Minecraft: `classic` como hipótese inicial da ficha de autoria. `slim` só entra se o concept aprovado justificar e o pipeline técnico validar.

## Resolução e formatos
A produção visual separa deliberadamente portrait/concept de skin técnica.

### Portrait/documentação — decisão de produção
- master aprovado: **2048×2048 px**, PNG, sRGB;
- manter o master sem recompressão lossy;
- derivados menores podem ser gerados para UI/documentação, mas não substituem o master;
- concept sheets não precisam ser quadradas, porém devem preservar pelo menos **2048 px no maior lado** para leitura de material/rosto;
- portrait/concept nunca é convertido diretamente em UV de skin.

### Skin técnica — evidência do provider
- alvo atual: **64×64 px RGBA PNG**, layout moderno de skin humanoide Minecraft;
- modelo `classic` enquanto a hipótese atual permanecer válida; qualquer mudança para `slim` exige ajuste explícito do layout/modelo;
- preservar alpha/segunda camada quando usada;
- não ampliar a skin para "HD" por conta própria: Easy NPC e o renderer humanoide atual consomem o formato de skin, enquanto 3D Skin Layers extruda a segunda camada sem transformar portrait em textura de runtime.

Evidência técnica/proveniência:
- `PROJECT-INSTRUCTIONS/modlist/easy-npc.md` — Easy NPC Core 7.11.0 é o provider físico dos NPCs e suporta skins de player/URL;
- upstream Easy NPC já trata skins modernas 64×64 e preservação de alpha antes da versão física 7.11.0;
- `PROJECT-INSTRUCTIONS/modlist/3d-skin-layers.md` — a skin base continua no renderer/player profile e o mod apenas apresenta a segunda camada em 3D.

A especificação `64×64` é do formato humanoide atual, não uma regra global de texel density para qualquer asset project-owned.

## Emissive/translucency/animated texture
Nenhum requisito atual. Não usar brilho ocular, aura ou glow como atalho de identidade.

## Bones/attachments necessários
Nenhum requisito adicional enquanto o alvo for uma skin/modelo humano padrão. Acessórios físicos próprios exigem contrato separado.

## Animações necessárias
Nenhuma animação project-owned exigida por este brief. Qualquer animação futura deve respeitar o runtime real e a pipeline vigente.

## Elementos proibidos
- caveira ornamental gratuita;
- olhos permanentemente brilhantes;
- sangue decorativo;
- coroa;
- armadura pesada sem contexto;
- iconografia que declare alinhamento moral;
- símbolo de facção/provider sem vínculo canônico/mecânico;
- preto puro uniforme que destrua leitura material.

## Variações autorizadas
- campo;
- laboratório/oficina;
- versão danificada somente após existir evento específico que a justifique.

Todas devem continuar reconhecíveis como o mesmo NPC.

## Proveniência/licença
Asset project-owned. Referências externas, se usadas em look-dev, servem apenas para linguagem visual e devem ser registradas; não copiar textura/modelo de terceiros.

## Evidência final exigida
- concept/portrait aprovado contra este brief em master 2048×2048;
- textura/skin técnica 64×64 separada do concept;
- validação estrutural no pipeline Blockbench/Factory;
- vistas úteis de frente, lateral, costas e três-quartos quando aplicável;
- verificação em escala real de gameplay;
- QA visual in-game antes de marcar `FINAL`.

## Pendências
- concept/portrait aprovado;
- altura/proporção percebida aprovada;
- tom de pele aprovado;
- escolha final entre classic/slim;
- skin/texture final;
- preview/QA in-game;
- eventual detalhe/acento visual principal aprovado.
