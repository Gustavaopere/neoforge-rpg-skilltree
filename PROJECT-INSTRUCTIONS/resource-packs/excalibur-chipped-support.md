# Excalibur | Chipped Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81ec9873c5bd0880bba4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Chipped_0.3_1.21.1.zip`
- **Versão 1.21.1:** 0.3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Chipped_0.3_1.21.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma o provider-alvo `chipped-neoforge-1.21.1-4.0.2.jar`, mod id `chipped`, runtime `4.0.2`.

## Propriedades do banco

- **Mod:** Excalibur | Chipped Support
- **Arquivo JAR:** `Excalibur_Chipped_0.3_1.21.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Support pack 16x WIP que adapta a maior parte dos blocks e icons do Chipped ao estilo Excalibur, incluindo workstations exibidas pelo projeto.
- **Dependências:** Excalibur base + Chipped 4.0.2. Conteúdo client-side; recipes, workstations e block variants continuam sob authority do Chipped.
- **Sobreposição:** Deve ficar acima do Excalibur base. Outros retextures do Chipped podem substituir assets por prioridade; ausência de visual não altera recipes/gameplay.
- **Compatibilidade/Riscos:** WIP explícito: upstream fala em “most blocks” e “most icons”, não cobertura total. Riscos de variants novas do Chipped 4.0.2, animated metadata/model drift e load order.
- **Observações:** Arquivo instalado `Excalibur_Chipped_0.3_1.21.1.zip`, release 28/05/2026. Projeto explicitamente WIP; não registrar 100% de cobertura.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Chipped Support 0.3 para 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-chipped-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Chipped 4.0.2, v0.3 WIP, cobertura parcial, workstations, animações, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Chipped_0.3_1.21.1.zip`, versão `0.3`, Release de 28/05/2026 para Minecraft 1.21.1. O alvo físico atual é Chipped `4.0.2`.

## 1. Papel e authority
Excalibur | Chipped Support é um support pack visual WIP. **Chipped 4.0.2** continua authority das workstations, recipes, block variants e qualquer comportamento; o pack só altera apresentação.

## 2. Cobertura publicada
O upstream declara que **a maioria dos blocks** foi adaptada ao Excalibur e que **a maioria dos icons** foi harmonizada. Isso é explicitamente cobertura parcial, não total. A galeria confirma workstations como Glassblower, Loom Table, Alchemy Bench, Tinkering Table, Mason Table, Carpenter's Table e Botanist Workbench.

## 3. Build 0.3 e target físico
A 0.3 possui arquivo específico para 1.21.1. O alvo físico é Chipped 4.0.2; variantes adicionadas, removidas ou renomeadas após a criação do pack podem cair em fallback visual.

## 4. Assets animados e models
O projeto é categorizado como Animated. Qualquer `.mcmeta`, animation frame ou model dependente deve ser validado em runtime; ausência de animação não implica falha de gameplay.

## 5. Load order e reload
Deve ficar acima do Excalibur base. Outros Chipped retextures podem vencer paths individuais. Resource reload deve afetar somente textures/models/icons.

## 6. Riscos
1. Cobertura incompleta por definição WIP.
2. Variantes do Chipped 4.0.2 sem asset equivalente.
3. Workstation model novo/renomeado.
4. Animated texture com frames/metadata incompatíveis.
5. Outro pack disputar os mesmos assets.

## 7. Matriz de testes
- [ ] Conferir as sete workstations mostradas oficialmente.
- [ ] Amostrar variantes de madeira, pedra, vidro e outros materiais.
- [ ] Identificar blocks/icons sem cobertura.
- [ ] Testar assets animados, se presentes.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing model/texture.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge confirma 0.3 para 1.21.1, natureza WIP e cobertura de “most blocks”/“most icons”. O catálogo não converte esses termos em 100% nem inventa contagem de assets.

> Boundary canônico: **Chipped controla conteúdo e recipes; o support pack controla apenas a aparência das superfícies efetivamente cobertas**.
