# Complementary Shaders - Reimagined

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db819c9610ca68897f62fb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Shader
- **Arquivo:** `ComplementaryReimagined_r5.9.zip`
- **Versão 1.21.1:** r5.9
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `ComplementaryReimagined_r5.9.zip` como fisicamente confirmado por captura da pasta Shaders do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do shader é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de shader packs.
- A modlist física de 08/09/2026 confirma Iris `1.8.14-beta.1+mc1.21.1` e Euphoria Patcher `1.10.0-r5.9-neoforge`, alinhado à linha Complementary r5.9.

## Propriedades do banco

- **Mod:** Complementary Shaders - Reimagined
- **Arquivo JAR:** `ComplementaryReimagined_r5.9.zip`
- **Tipo de conteúdo:** Shader
- **Versão 1.21.1:** r5.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Shader pack para Minecraft Java que redefine iluminação, sombras, atmosfera, água, céu e pós-processamento preservando a estética vanilla como referência visual.
- **Dependências:** Pipeline de shaders compatível. No perfil físico atual: Iris 1.8.14-beta.1+mc1.21.1. Euphoria Patcher 1.10.0-r5.9-neoforge está presente como extensão específica da linha Complementary r5.9.
- **Sobreposição:** Pode compor ou competir visualmente com mods/packs que alterem sky, clouds, fog, water, particles e iluminação. O shader pipeline tem prioridade visual própria; resource packs continuam fornecendo assets, não lógica do shader.
- **Compatibilidade/Riscos:** Shader client-side com custo de GPU e possível interação com mods de renderização, céu, água, partículas e efeitos. Euphoria Patcher precisa permanecer alinhado à linha r5.9; glitches devem ser validados com Iris e o stack visual atual.
- **Observações:** Arquivo físico `ComplementaryReimagined_r5.9.zip`, versão r5.9. A publicação oficial descreve o shader como otimizado e voltado a preservar a identidade visual do Minecraft; gameplay permanece fora de seu escopo.
- **Procedência:** CurseForge oficial Complementary Shaders - Reimagined r5.9 + captura Shaders do perfil em 08/09/2026 + modlist física Iris 1.8.14-beta.1 e Euphoria Patcher 1.10.0-r5.9.
- **Fonte:** https://www.curseforge.com/minecraft/shaders/complementary-reimagined
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Complementary Reimagined r5.9, pipeline Iris, Euphoria Patcher, escopo visual, performance, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Shader físico confirmado no dossiê de origem:** `ComplementaryReimagined_r5.9.zip`, versão `r5.9`. O stack físico usa Iris `1.8.14-beta.1+mc1.21.1` e Euphoria Patcher `1.10.0-r5.9-neoforge`.

## 1. Papel e authority
Complementary Shaders - Reimagined é a camada de **shader pipeline/apresentação** do perfil. Ele altera iluminação, sombras, atmosfera, água, céu e pós-processamento; Minecraft e os mods continuam authorities de gameplay, world state, entidades, blocos e dados.

## 2. Release r5.9
A publicação oficial identifica `Complementary Reimagined r5.9` como release atual da linha. O arquivo físico capturado coincide com essa versão. Não se atribuem ao shader mecânicas que pertencem ao jogo ou aos mods.

## 3. Pipeline físico
O perfil contém **Iris** `1.8.14-beta.1+mc1.21.1`, que fornece o runtime de shaders. Também contém **Euphoria Patcher** `1.10.0-r5.9-neoforge`, explicitamente alinhado à linha r5.9, formando uma extensão visual adicional sobre o Complementary.

## 4. Client-side e performance
O efeito é client-side e dependente de GPU/configuração gráfica. Alterações de preset, resolução de sombras, volumetria e reflexos podem mudar carga de GPU e frame time, sem alterar tick logic ou estado do servidor.

## 5. Sobreposição visual
Mods/packs que alteram céu, clouds, fog, água, partículas ou iluminação podem compor de forma diferente sob o shader. A presença de um resource pack não transfere a ele controle sobre os passes do shader.

## 6. Riscos
1. Preset pesado reduzir FPS ou aumentar frame time.
2. Euphoria Patcher ficar desalinhado após update do shader.
3. Mod de renderização produzir artefato em transparência, água ou partículas.
4. Sky/cloud/fog mods comporem de forma inesperada.
5. Cache/reload gráfico manter estado visual stale até reinicialização apropriada.

## 7. Matriz de testes
- [ ] Mundo aberto em áreas claras e escuras.
- [ ] Água, transparências e partículas.
- [ ] Nether e End.
- [ ] Ciclo dia/noite e clima.
- [ ] Euphoria Patcher ativo com r5.9.
- [ ] Comparar FPS/frame time em cenário representativo.
- [ ] Confirmar que shader on/off não altera gameplay ou estado persistente.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma Complementary Reimagined r5.9 e o objetivo de preservar a estética do Minecraft com qualidade e otimização. O catálogo usa a captura física para presença e versão e a modlist para o runtime Iris/Euphoria.

> Boundary canônico: **Complementary controla renderização visual; Minecraft e os mods controlam integralmente gameplay e estado do mundo**.
