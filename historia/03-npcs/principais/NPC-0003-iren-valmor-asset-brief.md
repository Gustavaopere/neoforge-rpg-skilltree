# Asset Brief — NPC-0003 — Iren Valmor

## Estado editorial
RASCUNHO

## Estado de produção
NÃO É ASSET FINAL / NÃO INICIADO.

## Asset ID
`NPC-0003`

## Owner/repositório
`Gustavaopere/neoforge-rpg-skilltree`

## Função de gameplay/apresentação
NPC recorrente de investigação institucional. A leitura visual deve comunicar observação, método e função pública antes de combate ou espetáculo mágico.

## Contextos de visualização
- conversa a curta/média distância;
- circulação em `SET-0001`;
- audiência/instituição ligada a `FAC-0001`;
- investigação de campo;
- portrait/documentação;
- skin/modelo em escala normal de gameplay.

## Referências project-owned/canônicas
- `NPC-0003-iren-valmor.md`;
- `NPC-0003-iren-valmor-autoria.md`;
- `FAC-0001` e `SET-0001` apenas como contexto narrativo, sem importar símbolo visual ainda não aprovado.

## Silhueta em uma frase
Figura esguia e ereta, de aparência funcional e institucional, mais próxima de um investigador viajante do que de um feiticeiro de combate.

## Shape language
Linhas relativamente verticais e limpas; camadas de roupa funcionais; poucos elementos salientes; acessórios concentrados em documentação/medição em vez de ornamento arcano.

## Paleta/materials
- tecido azul-acinzentado;
- carvão/cinza escuro;
- couro castanho;
- pequenos detalhes de latão;
- contraste controlado, sem emissive como identidade principal.

## Aparência
- idade aparente: início/meados dos 40;
- altura/proporção percebida: **PENDENTE DE APROVAÇÃO VISUAL**; manter hipótese humana padrão até o concept justificar algo diferente;
- tom de pele: **PENDENTE DE APROVAÇÃO VISUAL**; não inferir de cargo, instituição, prática arcana, moralidade ou paleta de roupa;
- traços angulares;
- expressão concentrada;
- olheiras discretas;
- cabelo escuro com grisalho nas têmporas;
- barba curta opcional e cuidada.

## Roupa/acessórios
- casaco ou robe funcional de corte;
- roupa de viagem por baixo;
- bolsa/pasta de documentos;
- fechos discretos de latão;
- instrumentos pequenos de anotação/medição quando apropriados.

## Modelo alvo
Modelo corporal Minecraft: `classic`, conforme ficha de autoria atual. Deve ser revalidado no pipeline técnico antes de export final.

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
- modelo `classic` conforme a ficha atual; qualquer mudança futura para `slim` exige ajuste explícito do layout/modelo;
- preservar alpha/segunda camada quando usada;
- não ampliar a skin para "HD" por conta própria: Easy NPC e o renderer humanoide atual consomem o formato de skin, enquanto 3D Skin Layers extruda a segunda camada sem transformar portrait em textura de runtime.

Evidência técnica/proveniência:
- `PROJECT-INSTRUCTIONS/modlist/easy-npc.md` — Easy NPC Core 7.11.0 é o provider físico dos NPCs e suporta skins de player/URL;
- upstream Easy NPC já trata skins modernas 64×64 e preservação de alpha antes da versão física 7.11.0;
- `PROJECT-INSTRUCTIONS/modlist/3d-skin-layers.md` — a skin base continua no renderer/player profile e o mod apenas apresenta a segunda camada em 3D.

A especificação `64×64` é do formato humanoide atual, não uma regra global de texel density para qualquer asset project-owned.

## Emissive/translucency/animated texture
Nenhum requisito atual. Não adicionar brilho ocular, aura permanente ou emissive gratuito.

## Bones/attachments necessários
Nenhum requisito adicional definido enquanto o asset for uma skin/modelo humano padrão. Caso acessórios físicos exijam modelo próprio, abrir contrato separado.

## Animações necessárias
Nenhuma animação project-owned exigida por este brief. Animações de apresentação devem respeitar o runtime real e a pipeline visual vigente.

## Elementos proibidos
- olhos brilhantes gratuitos;
- aura permanente;
- caveiras decorativas;
- coroa;
- armadura pesada;
- símbolo de provider específico sem vínculo mecânico;
- brasão de `FAC-0001` enquanto a identidade visual da instituição não estiver aprovada;
- excesso de ruído/ornamento que apague a leitura da silhueta.

## Variações autorizadas
- audiência/formal;
- campo/investigação;
- sem casaco externo.

As três devem continuar reconhecíveis como o mesmo NPC.

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
- altura/proporção percebida aprovada;
- tom de pele aprovado;
- portrait/concept aprovado;
- skin/texture final;
- eventual identidade visual de `FAC-0001`;
- preview/QA in-game.
