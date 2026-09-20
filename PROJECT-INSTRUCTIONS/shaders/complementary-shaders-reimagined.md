# Complementary Shaders - Reimagined

## Propriedades do registro

- **Mod:** Complementary Shaders - Reimagined
- **Arquivo JAR:** ComplementaryReimagined_[r5.9.zip](http://r5.9.zip)
- **Versão 1.21.1:** r5.9
- **Tipo de conteúdo:** Shader
- **Categoria:** Visual
- **Função:** Shader pack para Minecraft Java que redefine iluminação, sombras, atmosfera, água, céu e pós-processamento preservando a estética vanilla como referência visual.
- **Dependências:** Pipeline de shaders compatível. Na modlist física de 16/09/2026: Iris 1.8.14-beta.1+mc1.21.1 e Euphoria Patcher 1.10.5-r5.9.3-neoforge. O Patcher atual indica compatibilidade com a linha r5.9.3, mas não substitui a prova física do ZIP Complementary instalado.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Shader client-side com custo de GPU e possível interação com mods de renderização, céu, água, partículas e efeitos. A modlist atual usa Iris 1.8.14-beta.1+mc1.21.1 e Euphoria Patcher 1.10.5-r5.9.3-neoforge; o alinhamento do Patcher à linha r5.9.3 não prova, por si só, qual ZIP Complementary está instalado. Exigir inventário físico atual do shaderpack antes de certificar.
- **Fonte:** https://www.curseforge.com/minecraft/shaders/complementary-reimagined
- **Procedência:** captura física Shaders de 08/09/2026 (Complementary Reimagined r5.9) + modlist física atual (Iris/Euphoria Patcher; Euphoria 1.10.5-r5.9.3-neoforge) + CurseForge oficial Complementary Reimagined r5.9.3, arquivo ID 8884654, publicado em 15/09/2026, com suporte a Minecraft 1.21.1/Iris + changelog oficial Complementary r5 revalidado em 20/09/2026. A versão instalada do ZIP shader permanece não confirmada após 08/09.
- **Observações:** Última captura física disponível: `ComplementaryReimagined_r5.9.zip` em 08/09/2026. Upstream oficial revalidado em 20/09/2026: r5.9.3, arquivo `ComplementaryReimagined_r5.9.3.zip`, release de 15/09/2026, com suporte declarado a Minecraft 1.21.1 e Iris. O stack físico atual de mods usa Euphoria Patcher 1.10.5-r5.9.3-neoforge, mas isso não substitui a prova do ZIP instalado.
- **Atualização/Status:** DOSSIÊ TÉCNICO ATUALIZADO EM 20/09/2026 — upstream oficial confirmado em Complementary Reimagined r5.9.3 (`ComplementaryReimagined_r5.9.3.zip`, publicado em 15/09/2026 e compatível com Minecraft 1.21.1/Iris). A última evidência física disponível do shaderpack continua sendo `ComplementaryReimagined_r5.9.zip`; portanto a documentação está atualizada, mas a certificação permanece fail-closed até existir inventário físico atual do diretório shaderpacks.
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
