# Complementary Shaders - Reimagined — r5.10 (instalação declarada em 22/11/2026)

> **Estado vigente conforme declaração do usuário:** r5.9 → **r5.10**, shader ZIP oficial `ComplementaryReimagined_r5.10.zip` ([CurseForge file 9687811](https://www.curseforge.com/minecraft/shaders/complementary-reimagined/files/9687811), Release em 18/11/2026, inclui Minecraft 1.21.1/Iris). O antigo `ComplementaryReimagined_r5.9.zip` é apenas a última evidência física de 08/09/2026. A declaração confirma o inventário documental, mas nenhum ZIP novo, checksum ou teste GLSL foi inspecionado nesta sessão.

> **Auditoria:** r5.9.1, r5.9.2, r5.9.3 e r5.10 foram examinadas no histórico. r5.10 adiciona Dynamic Colored Lighting, melhora iluminação de itens segurados e traz opções/correções de render; mudanças exclusivas do Minecraft 26.x não se aplicam automaticamente a 1.21.1.

> **Compatibilidade:** Euphoria Patcher 1.11.2-r5.10 consta atualizado documentalmente em lote anterior. Não foram verificados Iris, shader options, driver/GPU nem execução real. O dossier continua sem selo ✅ por não possuir auditoria binária/QA do ZIP novo; a versão aparece no filename por solicitação expressa de versionamento.

> **Histórico:** afirmações antigas abaixo de que r5.9 é 'atual' ou de que r5.10 não está instalada descrevem o estado de pesquisas anteriores, não a declaração do usuário deste lote.


## Propriedades do registro

- **Mod:** Complementary Shaders - Reimagined
- **Arquivo ZIP instalado (declarado pelo usuário):** `ComplementaryReimagined_r5.10.zip` ([CurseForge](https://www.curseforge.com/minecraft/shaders/complementary-reimagined/files/9687811)); última evidência física `ComplementaryReimagined_r5.9.zip`.
- **Versão instalada declarada (Minecraft 1.21.1):** `r5.10` (snapshot anterior `r5.9`).
- **Tipo de conteúdo:** Shader
- **Categoria:** Visual
- **Função:** Shader pack para Minecraft Java que redefine iluminação, sombras, atmosfera, água, céu e pós-processamento preservando a estética vanilla como referência visual.
- **Dependências:** pipeline Iris, Euphoria Patcher 1.11.2-r5.10 sincronizado documentalmente e driver/GPU não verificados no perfil.
- **Estado no pack:** r5.10 declarado instalado pelo usuário em 22/11/2026; sem verificação de ZIP/GLSL.
- **Estado da pesquisa:** publicação oficial r5.10 confirmada, cadeia histórica auditada; QA e ZIP não inspecionados.
- **Compatibilidade/Riscos:** GLSL/driver, presets, dynamic colored lights, fog, clouds, particles, FPS e interoperabilidade com Euphoria. Sem homologação.
- **Fonte:** https://www.curseforge.com/minecraft/shaders/complementary-reimagined
- **Procedência:** declaração do usuário em 22/11/2026, [CurseForge 9687811](https://www.curseforge.com/minecraft/shaders/complementary-reimagined/files/9687811), último snapshot físico 08/09/2026.
- **Observações vigentes:** `ComplementaryReimagined_r5.10.zip` declarada instalada; r5.9/r5.9.3 históricos e sem novo SHA local.
- **Atualização/Status:** 22/11/2026 — r5.9 → r5.10 declarada instalada. Sem novo ZIP físico inspecionado, QA, benchmark ou selo ✅.
- **Decisão:** Sem decisão
- **Sobreposição:** Pode compor ou competir visualmente com mods/packs que alterem sky, clouds, fog, water, particles e iluminação. O shader pipeline tem prioridade visual própria; resource packs continuam fornecendo assets, não lógica do shader.

> 🌅 **ÚLTIMA CAPTURA FÍSICA DISPONÍVEL:** `ComplementaryReimagined_r5.9.zip`, versão `r5.9`, em 08/09/2026. A modlist física de 16/09/2026 usa Iris `1.8.14-beta.1+mc1.21.1` e Euphoria Patcher `1.10.5-r5.9.3-neoforge`. Como não há inventário físico posterior do diretório `shaderpacks`, a versão exata atualmente instalada do Complementary permanece **não confirmada**.

## 1. Papel e authority
Complementary Shaders - Reimagined é a camada de **shader pipeline/apresentação** do perfil. Ele altera iluminação, sombras, atmosfera, água, céu e pós-processamento; Minecraft e os mods continuam authorities de gameplay, world state, entidades, blocos e dados.

## 2. Release e drift de versão
A última captura física disponível coincide com `Complementary Reimagined r5.9`. O upstream oficial avançou na mesma linha para `r5.9.1` em 11/09/2026, `r5.9.2` em 14/09/2026 e `r5.9.3` em 15/09/2026. A release atual confirmada é `r5.9.3`, arquivo `ComplementaryReimagined_r5.9.3.zip`, File ID `8884654`, classificada como release e declarada compatível com Minecraft `1.21.1` e Iris.

A linha r5.9 é descrita oficialmente como uma atualização voltada a polimento geral, incluindo melhoria de frame rate, ajustes no céu diurno, suporte completo a reflexos volumétricos no End, maior controle de configurações, rebalanceamento de perfis de maior performance e correções diversas. As páginas públicas acessíveis das releases r5.9.1–r5.9.3 remetem ao changelog oficial da linha, mas não expõem de forma estática um delta granular confiável para cada hotfix; portanto não se inventa atribuição específica de correção a r5.9.1, r5.9.2 ou r5.9.3.

Como a versão física atual do ZIP não foi inventariada depois dessas publicações, `r5.9.3` permanece **upstream/current release**, não runtime físico confirmado. Não se atribuem ao shader mecânicas que pertencem ao jogo ou aos mods.

## 3. Pipeline físico
A modlist física de 16/09/2026 contém **Iris** `1.8.14-beta.1+mc1.21.1`, que fornece o runtime de shaders, e **Euphoria Patcher** `1.10.5-r5.9.3-neoforge`. Esse Patcher está alinhado à linha r5.9.3, mas isso não prova automaticamente que o ZIP Complementary instalado também foi atualizado para r5.9.3.

## 4. Client-side e performance
O efeito é client-side e dependente de GPU/configuração gráfica. Alterações de preset, resolução de sombras, volumetria e reflexos podem mudar carga de GPU e frame time, sem alterar tick logic ou estado do servidor.

## 5. Sobreposição visual
Mods/packs que alteram céu, clouds, fog, água, partículas ou iluminação podem compor de forma diferente sob o shader. A presença de um resource pack não transfere a ele controle sobre os passes do shader.

## 6. Riscos
1. Preset pesado reduzir FPS ou aumentar frame time.
2. Euphoria Patcher e ZIP Complementary ficarem desalinhados após atualização parcial do stack.
3. Mod de renderização produzir artefato em transparência, água ou partículas.
4. Sky/cloud/fog mods comporem de forma inesperada.
5. Cache/reload gráfico manter estado visual stale até reinicialização apropriada.

## 7. Matriz de testes
- [ ] Mundo aberto em áreas claras e escuras.
- [ ] Água, transparências e partículas.
- [ ] Nether e End.
- [ ] Ciclo dia/noite e clima.
- [ ] Confirmar fisicamente o nome/versão do ZIP Complementary atualmente instalado antes de testar alinhamento com Euphoria Patcher 1.10.5-r5.9.3.
- [ ] Após confirmar o ZIP, validar Euphoria Patcher ativo com a mesma linha Complementary.
- [ ] Comparar FPS/frame time em cenário representativo.
- [ ] Confirmar que shader on/off não altera gameplay ou estado persistente.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
Evidências confirmadas:
- captura física de 08/09/2026: `ComplementaryReimagined_r5.9.zip`;
- modlist física atual: Iris `1.8.14-beta.1+mc1.21.1` e Euphoria Patcher `1.10.5-r5.9.3-neoforge`;
- CurseForge oficial: `ComplementaryReimagined_r5.9.3.zip`, File ID `8884654`, publicado em 15/09/2026, com suporte a Minecraft `1.21.1` e Iris;
- changelog oficial: sequência r5.9 → r5.9.1 → r5.9.2 → r5.9.3 e escopo geral da linha r5.9.

O único dado que continua ausente é um inventário físico posterior a 08/09/2026 do diretório `shaderpacks`. O sufixo `r5.9.3` do Euphoria Patcher demonstra alinhamento de compatibilidade do patcher, mas não prova que o ZIP do Complementary foi substituído. Pelo protocolo fail-closed, sem essa prova o arquivo GitHub não recebe `✅-` nem versão r5.9.3 no filename.

> 🔒 Boundary canônico: **Complementary controla renderização visual; Minecraft e os mods controlam integralmente gameplay e estado do mundo**.

## Auditoria documental incremental — r5.10 (22/11/2026; shader ZIP não instalado nem inspecionado)

**Baseline física histórica preservada:** `ComplementaryReimagined_r5.9.zip` (captura do diretório `shaderpacks/` de 08/09/2026). Não existe nova listagem física confirmando a substituição; o rótulo `r5.9.3` de **Euphoria Patcher** registra compatibilidade do patcher e **não prova** o shader ZIP instalado. Este dossier não recebe `✅-` nem mudança de versão no filename.

**Distribuições intermediárias:** a pesquisa anterior já identificou e documentou `r5.9.1` (11/09/2026), `r5.9.2` (14/09/2026) e `r5.9.3` (15/09/2026), com registro histórico distinto da baseline r5.9. O [histórico oficial do autor](https://www.complementary.dev/changelogs/) confirma a publicação seguinte **r5.10 em 18/11/2026**, sem outra versão nomeada entre r5.9.3 e r5.10.

**r5.10, 18/11/2026, distribuição oficial:** [CurseForge file ID 9687811](https://www.curseforge.com/minecraft/shaders/complementary-reimagined/files/9687811), filename `ComplementaryReimagined_r5.10.zip`, **Release**, marcada explicitamente para **Minecraft 1.21.1** e pipeline **Iris/OptiFine**, entre diversas versões do jogo. O texto público específico anuncia:
- **Dynamic Colored Lighting**: itens largados e várias entidades especiais podem emitir iluminação colorida dinâmica; testar execução e performance sob Iris, independentemente de efeitos lógicos de gameplay.
- Revisão da iluminação de **itens segurados** (*held lighting*), integrações com mods, novas opções de configuração, correções e melhorias diversas **sem inventário técnico individualizado na descrição resumida**.
- Suporte a features recentes de **Minecraft 26.3 e 26.4**, com referência à atmosfera especial das **Ice Caves**. **Não atribuir mecânicas ou recursos exclusivos dessas versões modernas ao Minecraft 1.21.1**. O mesmo ZIP ser listado para 1.21.1 não prova que todos os passes, uniforms ou blocos novos se apliquem à plataforma histórica.

**Gates de interoperabilidade e QA:** não substituir ZIP até confirmar inventário físico atual, versão e checksums; validar versão Iris do perfil, combinações com **Euphoria Patcher 1.10.5-r5.9.3** instalado versus eventual Patcher **1.11.2-r5.10** apenas publicado, sem misturar stack. Executar compilação GLSL, preset migration, lighting/coloured lights, VFX, transparência, nuvens, chuva, partículas, dimensões e frame-time para GPU/driver do usuário, incluindo regressão em Iris on/off e shaders mods. Nada disso foi executado.

**Limite documental:** a página oficial e o histórico confirmam versão/compatibilidade e o escopo resumido; **não foram baixados, extraídos, comparados ou validados os ZIPs r5.9/r5.10**, e não houve confirmação de todas as mudanças internas nem benchmark. A pesquisa de publicações alcançou r5.10, mas **a homologação técnica do ZIP, compatibilidade real com o stack e presença física continuam bloqueadas**. **Sem alteração da pasta `Vanilla` ou concessão de certificado.**

