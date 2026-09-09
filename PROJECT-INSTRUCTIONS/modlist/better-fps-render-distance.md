# Better Fps - Render Distance

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8135bd20f2bb29075214  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Better Fps - Render Distance
- **Arquivo JAR:** `betterfpsdist-1.21.1-6.1.jar`
- **Versão 1.21.1:** `6.1`
- **Categoria:** Performance; Visual; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/better-fps-render-distance
- **Função:** Otimização client-side de render distance que troca o volume vanilla por seleção 3D elipsoidal/circular configurável, reduzindo chunk sections renderizadas e podendo ajustar entity render distance.
- **Dependências:** Client-side NeoForge 1.21.1. O pack também possui Distant Horizons 3.2.0-b, EntityCulling 1.10.5 e ImmediatelyFast 1.6.13; são camadas distintas, não dependências diretas.
- **Compatibilidade/Riscos:** Pode causar pop-in/culling visual, divergência com LOD/render-distance mods e redução excessiva de entity range se configs forem combinadas. Não altera chunk generation nem authority de servidor.
- **Sobreposição:** Atua na seleção por render distance; Distant Horizons trata LOD distante, EntityCulling trata oclusão de entidades/block entities e ImmediatelyFast otimiza pipelines de render. Não são equivalentes.
- **Observações:** Build 6.1 NeoForge 1.21.1, client. Configura escalas horizontal/vertical, entity render range e debug de chunk sections ignoradas; documentação estima redução de ~10–35% de chunk sections em cenários apropriados.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge oficial Better Fps - Render Distance 6.1 e changelog da linha 6.x.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria reconfirmou Better Fps - Render Distance 6.1 como otimização client-side de seleção/render distance, com riscos de composição com outros render optimizers documentados. A instalação atual não foi tratada como decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — algoritmo de seleção visual, configs, relação com LOD/culling, side, lifecycle e riscos catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `betterfpsdist-1.21.1-6.1.jar`, mod id `betterfpsdist`, runtime `6.1`, NeoForge 1.21.1. É um mod **client-side de performance/renderização**, não um sistema de worldgen ou servidor.

## 1. Papel e autoridade
Better Fps - Render Distance otimiza a seleção de regiões renderizadas ao substituir a forma volumétrica usada pela distância de render vanilla por um volume 3D configurável mais próximo de esfera/elipsoide. O objetivo é deixar de renderizar chunk sections que ficam dentro do cubo/cilindro nominal, mas longe do jogador em distância espacial real.

Minecraft continua authority de chunks e mundo. O mod decide apenas **quais seções o cliente considera para render**, sem apagar ou regenerar chunks.

## 2. Modelo de distância
A documentação oficial descreve uma distância 3D circular/elipsoidal com escalas horizontal e vertical. A escala pode ser ajustada para reduzir, por exemplo, a quantidade de cavernas abaixo do jogador que continuam sendo processadas visualmente quando não contribuem para a cena.

Consequência: alterar a configuração muda o envelope visual, não o server view distance nem a existência física do mundo.

## 3. Escala horizontal e vertical
A linha 6.x expõe ajustes independentes de escala. Isso permite balancear:
- distância lateral percebida;
- profundidade vertical acima/abaixo;
- número de chunk sections mantidas no conjunto visual.

Config muito agressiva pode gerar pop-in ou seções desaparecendo perto dos limites da visão. Não usar valor de config como dado de gameplay.

## 4. Entity render distance
A linha 6.x possui opção para ajustar também o alcance de render de entidades em função da distância modificada. Esse é um gate estritamente visual:
- entidade continua existindo no servidor;
- AI, colisão, dano e targeting não devem depender de estar renderizada;
- outro mod de culling pode reduzir ainda mais a visibilidade sem mudar a entidade real.

## 5. Debug
O projeto inclui opção de debug para mostrar chunk sections ignoradas/estatísticas relacionadas. É ferramenta de diagnóstico; não deve ser mantida ativa como requirement de gameplay.

## 6. Ganho esperado e limites
A documentação do projeto menciona redução aproximada de **10–35% das chunk sections** em cenários adequados. Isso é uma estimativa do autor, não garantia de FPS equivalente. O benefício depende de render distance, terreno, GPU/CPU, shaders, LOD e outras otimizações.

Não registrar “+35% FPS” como resultado do pack sem benchmark real.

## 7. Relação com Distant Horizons
O pack possui Distant Horizons 3.2.0-b. As funções são diferentes:
- BetterFPSDist reduz/reshapeia o volume de render vanilla detalhado;
- Distant Horizons fornece representação LOD para distância maior.

Risco real é criar uma zona visual estranha entre render vanilla reduzido e LOD. Testar transição, fog e pop-in; não remover um apenas por ambos citarem distância.

## 8. Relação com EntityCulling e ImmediatelyFast
- EntityCulling 1.10.5: elimina render de entidades/block entities ocultas por oclusão.
- ImmediatelyFast 1.6.13: otimiza partes do pipeline de render.
- BetterFPSDist 6.1: seleciona geometricamente a região considerada pela render distance.

Podem coexistir porque atacam etapas diferentes, embora bugs de render possam compor efeitos.

## 9. Client/server
A funcionalidade catalogada é client-side. Regras:
- não usar classes/config do mod em common/server code próprio;
- servidor não deve confiar no que o jogador enxerga para validar alcance de ataque/interação;
- não sincronizar a configuração como requisito de mundo sem motivo explícito.

## 10. Lifecycle
Validar:
- entrar/sair de mundo;
- mudar render distance em Options;
- trocar escala horizontal/vertical;
- ativar/desativar entity adjustment;
- resource/shader reload;
- dimension change;
- conexão a servidores com view distance diferente.

A mudança deve reconstruir o conjunto visual sem deixar chunks invisíveis presos em cache.

## 11. Riscos
1. Pop-in em chunk sections nos limites do elipsoide.
2. Entidades sumirem visualmente cedo demais por combinação de ranges.
3. Transição ruim com Distant Horizons.
4. Fog/shader assumir geometria de render distance vanilla.
5. Debug/config ser confundido com estado de servidor.
6. Atribuir ganho de FPS garantido sem benchmark.

## 12. Matriz de testes
1. Benchmark baseline e mod ativo com mesma posição/render distance.
2. Overworld, Nether e End com grande variação vertical.
3. Render distance baixa/média/alta.
4. Distant Horizons ativo: verificar seam/pop-in de LOD.
5. EntityCulling e ImmediatelyFast ativos simultaneamente.
6. Shaders/resource packs atuais do pack.
7. Entity range: mobs/jogadores continuam funcionalmente presentes mesmo fora do render visual.
8. Alterar config sem restart quando suportado e verificar reconstrução correta.

## 13. Evidência
- modlist física atual: BetterFPSDist 6.1;
- CurseForge oficial da build 6.1 NeoForge 1.21.1, ambiente client;
- documentação oficial de render volume 3D, escalas horizontal/vertical, entity range e debug;
- presença física no pack de Distant Horizons, EntityCulling e ImmediatelyFast usada apenas para mapear sobreposição real de camada.

> 📐 Authority canônica: BetterFPSDist controla apenas seleção visual por distância no cliente. Ele não gera chunks, não altera AI e não substitui Distant Horizons ou EntityCulling.
