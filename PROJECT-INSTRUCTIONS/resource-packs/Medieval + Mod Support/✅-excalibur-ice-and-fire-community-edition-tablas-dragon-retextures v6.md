# Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`
- **Versão 1.21.1:** v6
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Ice and Fire Community Edition `2.1.2`; essa é a authority do provider-alvo.

## Propriedades do banco

- **Mod:** Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures
- **Arquivo JAR:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** v6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Retextura visual dos Fire, Ice e Lightning dragons de Ice and Fire: Community Edition para a estética Excalibur/Tabla, sem alterar entidades ou combate.
- **Dependências:** Ice And Fire Community Edition 2.1.2 + stack visual Excalibur. Conteúdo client-side; AI, stages, attacks, taming, loot e persistence permanecem no mod.
- **Sobreposição:** True Dovah altera os mesmos dragões e conteúdo relacionado; nos paths coincidentes, o pack de maior prioridade vence. Conflito é visual, não funcional.
- **Compatibilidade/Riscos:** Arquivo físico é v6, mas a página CE indexada ainda pode expor v5; delta v5→v6 permanece fail-closed. Conflito visual direto com True Dovah nos assets de dragão; prioridade decide o resultado.
- **Observações:** Arquivo instalado `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`. Preservar `v6` como autoridade física; não inventar changelog CE v6 quando não publicado de forma suficiente.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física Ice and Fire CE 2.1.2 + projeto oficial Tabla's Dragon Retextures/Community Edition.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; I&F CE 2.1.2, v6 física, Fire/Ice/Lightning dragons, overlap True Dovah, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`, marcador físico `v6`, para Ice and Fire: Community Edition. O alvo físico atual é Ice And Fire Community Edition `2.1.2`.

## 1. Papel e authority
Este pack retexturiza os dragões de Ice and Fire: Community Edition para a estética Excalibur. **Ice and Fire CE** continua authority de spawn, AI, stages, sex/variants, breath attacks, damage, taming, riding, loot e persistence.

## 2. Cobertura confirmada
O projeto declara cobertura de **todos os Fire, Ice e Lightning dragons** para combinar com a estética do pack. As releases anteriores documentam mudanças por cor/sexo/stage, incluindo eyes, wings, horns e outros detalhes; isso confirma granularidade por variante, não mudança funcional.

## 3. Boundary de versão v6
O arquivo físico instalado é `v6`. A página pública indexada do projeto CE ainda pode expor `v5` como main file, enquanto a família Tabla's Dragon Retextures recebeu atualizações v6 em setembro de 2026. Portanto `v6` é preservado como autoridade física, mas o delta CE `v5→v6` permanece fail-closed onde não houver changelog oficial específico acessível.

## 4. Stack físico
Target: Ice and Fire CE `2.1.2`. O pack visual deve ser testado em Fire/Ice/Lightning dragons, stages e variants reais dessa build para detectar path drift.

## 5. Conflito direto com True Dovah
`True Dovah.zip` também altera **todos os dragões e conteúdo relacionado a dragon scales** de Ice and Fire. Nos assets de dragão sobrepostos, o resource pack de maior prioridade vence. Isso é conflito visual direto/alternativa estética, não conflito de gameplay.

## 6. Load order e reload
Para obter a estética Tabla/Excalibur, este pack precisa ficar acima da base visual e acima de qualquer retexture concorrente que se deseje substituir. Resource reload deve apenas trocar textures/models e nunca entity state.

## 7. Riscos
1. `v6` física sem changelog CE integral acessível.
2. True Dovah sobrescrever dragões por prioridade.
3. Paths/variants novos de Ice and Fire CE 2.1.2 sem asset correspondente.
4. Model/texture mismatch por stage/sex/color.
5. Resource reload deixar cache stale.

## 8. Matriz de testes
- [ ] Fire dragons: múltiplas cores, stages e sexos.
- [ ] Ice dragons: múltiplas cores, stages e sexos.
- [ ] Lightning dragons: múltiplas cores, stages e sexos.
- [ ] Eggs/young dragons onde houver assets.
- [ ] Comparar prioridade Tabla vs True Dovah.
- [ ] Resource reload sem missing textures/models.
- [ ] Confirmar que pack on/off não altera AI, damage ou drops.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o propósito e a cobertura Fire/Ice/Lightning do projeto CE. O arquivo físico confirma `v6`; sem changelog CE v6 suficiente, diferenças exatas da release não são inventadas.

> Boundary canônico: **Ice and Fire CE controla os dragões como entidades; Tabla's Dragon Retextures controla apenas sua aparência, sujeita à prioridade contra True Dovah**.

## 10. Divergência entre ZIP histórico v6 e CurseForge v5 — 10/10/2026

**Autoridade do perfil preservada conforme evidência histórica:** o dossiê registra captura da pasta de resource packs de 08/09/2026 com o ZIP **`1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`**, versão indicada **v6**. O ZIP/captura não foi recuperado neste ciclo e seu SHA/manifest interno **não foi comparado**.

**Verificação externa em 10/10:** o projeto CurseForge específico **Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures**, project **1553651**, apresenta **`1.21.1_G's_Dragons_Retextured_I&F-CE.v5.zip` de 14/07/2026 como última release pública para Minecraft 1.21.1**, com listagem de arquivos v3, v4 e v5, **sem v6 publicada nesse projeto**. Não confundir com outro projeto **Excalibur | Ice and Fire | Tabla's Dragon Retextures** para o mod não Community Edition; versões desse outro projeto não estabelecem uma v6 CE.

### Tratamento da divergência
- **Fato do catálogo histórico:** houve registro físico de ZIP cujo filename declarava v6. Sem o arquivo real nesta rodada, esse fato é evidência do documento de origem, **não verificação atual de conteúdo/versão interna**.
- **Fato do CurseForge do projeto correspondente:** a latest release **pública rastreável é v5**, não v6. Não há changelog público de v6 CE que permita concluir as diferenças para v5.
- **Hipóteses não verificadas:** ZIP local renomeado, edição privada, distribuição por outra fonte ou erro na captura/atribuição. **Nenhuma deve ser declarada verdadeira** sem recuperar ZIP ou URL de origem.
- **Decisão:** **não renomear o dossiê para v5 nem substituir o ZIP por v5 automaticamente**; manter filename histórico v6 enquanto fonte, hash e conteúdo não forem reconciliados. Tratar a origem/versão v6 como **PENDÊNCIA DE PROVENIÊNCIA**, sem afirmar v6 publicada publicamente no projeto CurseForge.

### Regressão/precedência
Este resource pack é camada puramente visual para dragões **Fire, Ice e Lightning** do Ice and Fire: Community Edition (físico 2.1.2). Revalidar modelos por estágio/cor/sexo, ovos e jovens quando houver assets, olhos/emissive se presentes nos arquivos, resource reload, compatibilidade com EMF/ETF/shaders e prioridade contra **True Dovah** e outros packs Excalibur que alterem os mesmos caminhos de textura. Comparar hash/árvore ZIP v6 contra o artefato público v5 em instância isolada antes de qualquer decisão.

**Fonte externa oficial:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas/files/all

**Estado:** discrepância comprovada entre **filename físico histórico v6** e **publicação oficial v5**; nenhuma instalação ou correção física nesta operação documental.

